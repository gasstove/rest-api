package com.gasstove.gs.test.util;

import com.gasstove.gs.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This is the script to populate the test database
 */
public class TestData {

    /**
     * Main method to load test data
     *
     * @param args
     */
    public static void main(String[] args){
        TestData t = new TestData();
        DataContainer data = t.generate_data();
        t.getConnection();
        t.removeAllRecords();
        t.insert_db(data);
    }

    //set these values to manipulate test records
    private final int MIN_USER_PER_EVENT = 1;
    private final int MAX_USER_PER_EVENT = 30;

    private final int MIN_MEDIA_PER_EVENTUSER = 0;
    private final int MAX_MEDIA_PER_EVENTUSER = 10;

    // set this >1 for many-to-many event_user <-> media relationship
    // Set this =1 to impose each media belongs to only 1 event_user
    private final boolean ALLOW_MULTIPLE_EVENTS_PER_MEDIA = false;
    private final float AVG_EVENT_PER_MEDIA = 2f;

    private final int MIN_EVENT_YEAR = 2014;
    private final int MAX_EVENT_YEAR = 2016;
    private final int MIN_EVENT_DURATION = 5;   // hours
    private final int MAX_EVENT_DURATION = 10;  // hours

    private final int NUM_ACTORS = 50;
    private final int NUM_EVENTS = 10;

    private Connection connection = null;
    private PreparedStatement  statement = null;
    private Statement stmt = null;


    /**
     * Establish connection to db
     *
     * @return Connection database connection
     */
    public Connection getConnection(){
        try
        {
            connection = new DBConnection().getConnection();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Removes all the records from each table so they can be populated
     */
    public void removeAllRecords(){
        try {
            
            String sql = "DELETE FROM roles";
            stmt = connection.createStatement();
            stmt.execute(sql);

            sql = "DELETE FROM actor";
            stmt = connection.createStatement();
            stmt.execute(sql);

            sql = "DELETE FROM event";
            stmt = connection.createStatement();
            stmt.execute(sql);

            sql = "DELETE FROM media";
            stmt = connection.createStatement();
            stmt.execute(sql);

            sql = "DELETE FROM actor_event_mapping";
            stmt = connection.createStatement();
            stmt.execute(sql);

            sql = "DELETE FROM media_mapping";
            stmt = connection.createStatement();
            stmt.execute(sql);

        } catch(SQLException e){
               e.printStackTrace();
        }

    }

    /**
     * Generate sets of users, events, and media
     */
    public DataContainer generate_data(){

        HashSet<User> all_users = new HashSet<User>();
        HashSet<Event> all_events = new HashSet<Event>();
        HashSet<Media> all_medias = new HashSet<Media>();

        // create users
        for (int i = 0; i < NUM_ACTORS; i++)
            all_users.add(new User(userNames[i]));

        // create events
        for (int i = 0; i < NUM_EVENTS; i++)
            all_events.add(new Event(eventNames[i]));

        // invite random list of guests
        for(Event event : all_events)
            event.invite_from(all_users);

        // users take a bunch of photos
        for(User user : all_users )
            all_medias.addAll( user.generate_media() );

        // assign unique file extensions to medias
        int n = all_medias.size();
        ArrayList<Integer> fileext = sample_without_replacement(intset(1000,1000+n),n);
        int c = 0;
        for(Media media : all_medias)
            media.set_fileext(fileext.get(c++));

        return new DataContainer(all_users,all_events,all_medias);
    }

    /**
     * Insert data into db
     */
    public void insert_db(DataContainer data){

//        //add the roles you want defined
//        String sql = "INSERT into roles(id,type) VALUES(?,?)";
//        statement = connection.prepareStatement(sql);
//        statement.setInt(1, 1);
//        statement.setString(2, "owner");
//        statement.execute();
//
//        sql = "INSERT into roles(id,type) VALUES(?,?)";
//        statement = connection.prepareStatement(sql);
//        statement.setInt(1, 2);
//        statement.setString(2, "member");
//        statement.execute();

        // insert users into db
        for(User user : data.all_users)
            user.insert_db();

        // insert events into db
        for(Event event : data.all_events)
            event.insert_db();

        // insert media into db
        for(Media media : data.all_medias)
            media.insert_db();

        // insert guest lists into actor_event_mapping
        for(Event event : data.all_events)
            event.insert_guests_db();

        // insert media info into media_mapping
        for(Media media : data.all_medias)
            media.insert_userevent_mapping_db();
    }

    /**
     * Container class for all data
     */
    private class DataContainer {
        HashSet<User> all_users;
        HashSet<Event> all_events;
        HashSet<Media> all_medias;
        public DataContainer(HashSet<User> U,HashSet<Event> E,HashSet<Media> M){
            all_events = E;
            all_medias = M;
            all_users = U;
        }
    }

    private class Media {

        Integer id;
        User owner;
        String mediaType;
        Integer filename_ext;

        ArrayList<Event> events = new ArrayList<Event>();

        public Media(User owner){
            this.owner = owner;
            this.mediaType = mediaTypes[randBetween(0,3)];
        }

        public void set_fileext(Integer f){
            this.filename_ext = f;
        }

        public void add_to_event(Event event){
            this.events.add(event);
            event.add_media(this);
        }

        public void insert_db(){

            try{

                if(id!=null)
                    throw new Exception("Repeat insertion of media " + id);

                // insert the media
                String sql = "INSERT into media(type, file_name) VALUES(?,?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1,mediaType);
                statement.setString(2, "media_" + filename_ext);
                statement.execute();

                // get media id
                sql = "SELECT Max(id) from media";
                stmt = connection.createStatement();
                ResultSet r = stmt.executeQuery(sql);
                if (r.next())
                    this.id = r.getInt(1);

            }
            catch(Exception s){
                s.printStackTrace();
            }

        }

        public void insert_userevent_mapping_db(){
            try {
                for(Event event : events) {

                    String sql = "INSERT into media_mapping(media_id, actor_event_mapping_id,num_downloads,shared) VALUES(?,?,?,?)";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, this.id);
                    statement.setInt(2, event.get_usereventid_for_user(this.owner) );
                    statement.setInt(3, randBetween(10, 10000));
                    statement.setInt(4, randBetween(0, 2));
                    statement.execute();
                }
            }
            catch(Exception s){
                s.printStackTrace();
            }
        }

    }

    private class Event {

        Integer id;
        String name;
        long open_date;
        long close_date;
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<Media> medias = new ArrayList<Media>();
        HashMap<User,Integer> userEventId_for_user = new HashMap<User,Integer>();

        public Event(String name){
            this.name = name;
            this.open_date = randomDate(MIN_EVENT_YEAR,MAX_EVENT_YEAR).getTime();
            this.close_date = this.open_date + randBetween(MIN_EVENT_DURATION,MAX_EVENT_DURATION);
        }

        public void invite_from(HashSet<User> all_users){
            int num_guests = randBetween(MIN_USER_PER_EVENT, MAX_USER_PER_EVENT);
            this.users = sample_without_replacement(all_users,num_guests);
            // inform users
            for(User user : users)
                user.add_event(this);
        }

        public Integer get_usereventid_for_user(User user){
            return userEventId_for_user.get(user);
        }

        public void add_media(Media media){
            this.medias.add(media);
        }

        public void insert_db(){

            try{
                if(id!=null)
                    throw new Exception("Repeat insertion of event " + id);
                String sql = "INSERT into event(name,open_date,close_date,join_invitation,join_allow_by_accept,join_allow_auto) VALUES(?,?,?,1,1,1)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, this.name);
                statement.setDate(2, new java.sql.Date(this.open_date));
                statement.setDate(3, new java.sql.Date(this.close_date));
                statement.execute();

                // get event ids
                sql = "SELECT Max(id) from event";
                stmt = connection.createStatement();
                ResultSet r = stmt.executeQuery(sql);
                if (r.next())
                    this.id = r.getInt(1);
            }
            catch(Exception s){
                s.printStackTrace();
            }
        }

        public void insert_guests_db(){

            try{
                // add guests to this event
                for(User user : this.users) {
                    String sql = "INSERT into actor_event_mapping(event_id, actor_id,role_id,join_date) VALUES(?,?,?,?)";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, this.id);
                    statement.setInt(2, user.id);
                    statement.setInt(3, (int) (Math.random() * 2));
                    statement.setDate(4, new java.sql.Date(this.open_date));
                    statement.execute();

                    // extract actor_event ids
                    sql = "SELECT Max(id) from actor_event_mapping";
                    stmt = connection.createStatement();
                    ResultSet r = stmt.executeQuery(sql);
                    if (r.next())
                        userEventId_for_user.put(user, r.getInt(1));
                }
            }
            catch(Exception s){
                s.printStackTrace();
            }
        }

    }

    private class User {
        Integer id;
        String first;
        String last;
        boolean is_subscriber;

        HashSet<Event> events = new HashSet<Event>();
        HashSet<Media> medias = new HashSet<Media>();
        public User(String fullname){
            String[] name = fullname.split(" ");
            this.first = name[0];
            this.last = name[1];
            this.is_subscriber = Math.random() < 0.5;
        }
        public void add_event(Event event){
            events.add(event);
        }

        public void insert_db(){

            try{

                if(id!=null)
                    throw new Exception("Repeat insertion of user " + id);
                String sql = "INSERT into actor(first,last,is_subscriber,contact_method) VALUES(?,?,1,?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, this.first);
                statement.setString(2, this.last);
                statement.setInt(3, this.is_subscriber ? 1 : 0 );
                statement.execute();

                // extract actor id
                sql = "SELECT Max(id) from actor";
                stmt = connection.createStatement();
                ResultSet r = stmt.executeQuery(sql);
                if (r.next())
                    this.id = r.getInt(1);
            }
            catch(Exception s){
                s.printStackTrace();
            }

        }

        // generate a number of media items,
        // for each media item, randomly decide what events it belongs to from my list of events
        // add it to that event or events
        public HashSet<Media> generate_media(){
            // pick the number of media I generate as num_events * avg_media_per_event / avg_event_per_media
            float num_events = (float) events.size();
            float avg_media_per_event = (float) randBetween(MIN_MEDIA_PER_EVENTUSER,MAX_MEDIA_PER_EVENTUSER);
            int num_media = ALLOW_MULTIPLE_EVENTS_PER_MEDIA ?
                            Math.round( num_events * avg_media_per_event / AVG_EVENT_PER_MEDIA ) :
                            Math.round( num_events * avg_media_per_event );

            // generate each media, assign it to an event(s)
            for(int i=0 ; i<num_media ; i++ ){
                Media media = new Media(this);
                medias.add(media);

                // how many events does it belong to?
                long num_event_for_media = ALLOW_MULTIPLE_EVENTS_PER_MEDIA ?
                                           Math.max(0,Math.round(AVG_EVENT_PER_MEDIA + randBetween(-0.5d,0.5d))) : // cheap binomial
                                           1 ;

                // sample that many events, add media to each event
                ArrayList<Event> events_for_media = sample_without_replacement(this.events,(int) num_event_for_media);
                for(Event event : events_for_media)
                    media.add_to_event(event);
            }
            return medias;
        }
    }

    /**
     * Generate a random date base on min_year and max_year
     *
     * @return java.util.Date
     */
    private java.util.Date randomDate(int min_year,int max_year){
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(min_year,max_year);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        return gc.getTime();
    }

    /**
     *
     * Generate random number in range.
     *
     * @param start beginning of range, inclusive
     * @param end end of range, exclusive
     *
     * @return random number between start(inclusive) and end(exclusive)
     */
    private static int randBetween(int start, int end) {
        return start + (int) ( Math.random() * (end - start));
    }

    private static double randBetween(double start, double end) {
        return start + Math.random() * (end - start);
    }

    /**
     *
     * Sample n values from A without replacement.
     *
     * @param A Array of integers
     * @param n number of samples to take from A
     *
     * @return Sampled array of integers
     */
    private static <T> ArrayList<T> sample_without_replacement(final HashSet<T> A, int n) {
        if(n<0)
            return null;

        // create new array
        ArrayList<T> Acopy = new ArrayList(A);

        // shuffle it
        java.util.Collections.shuffle(Acopy);

        // pick out first n elements
        return new ArrayList(Acopy.subList(0,Math.min(n,A.size())));
    }

    /** Generate undordered list integers between a and b inclusive
     *
     * @param a
     * @param b
     * @return
     */
    private HashSet<Integer> intset(int a,int b){
        HashSet<Integer> A = new HashSet<Integer>();
        for(int x=a;x<=b;x++)
            A.add(x);
        return A;
    }

    static String[] userNames = {
        "Leila Longstreet",
        "Karyn Kluth",
        "Sung Sebesta",
        "Shantay Schaaf",
        "Florentina Foret",
        "Genia Gast",
        "Kareem Kai",
        "Emilie Eccles",
        "Mason Michalak",
        "Emery Ebron",
        "Cornelius Chau",
        "Deetta Daring",
        "Launa Loud",
        "Alan Artis",
        "Amalia Adames",
        "Nicol Nay",
        "Peggie Pyle",
        "Creola Cropp",
        "Georgia Grimsley",
        "September Sussman",
        "Teresa Torrain",
        "Maynard Magnusson",
        "Takako Troxel",
        "Nicola Narcisse",
        "Kate Knerr",
        "Cherrie Cotner",
        "Jeraldine Jolin",
        "Stepanie Salters",
        "Yon Yerby",
        "Cedrick Cacho",
        "Ceola Cue",
        "Catrice Chapple",
        "Beatrice Bernhardt",
        "Tien Tallmadge",
        "Dorcas Desch",
        "Adelaida Ariza",
        "Candida Cornell",
        "Cicely Cromer",
        "Sylvia Shehane",
        "Cleveland Chevere",
        "Bernardo Brigmond",
        "Earnestine Ecker",
        "Janice James",
        "Elroy Eldredge",
        "Ed Eggen",
        "Lincoln Lacourse",
        "Caroline Crooms",
        "Dirk Dejesus",
        "Hildegarde Hellman",
        "Herbert Homan"};

    static String[] eventNames = {
        "redisbursement",
        "theistic",
        "dahlgren",
        "dubiety",
        "donalda",
        "leakier",
        "heteropteran",
        "herolike",
        "holt",
        "obedientiary",
        "versatilely",
        "rubato",
        "mayer",
        "dueller",
        "incarnalized",
        "tern",
        "marrowish",
        "hoarily",
        "nonallegation",
        "uncovetous",
        "quill",
        "fro",
        "angulate",
        "consequently",
        "pedantism",
        "hipsters",
        "pretoken",
        "ormuz",
        "unacoustical",
        "disdain",
        "tasselled",
        "controverter",
        "pyrrha",
        "uninebriating",
        "paresis",
        "electrotaxis",
        "hanno",
        "hypesthesia",
        "retrogradation",
        "prejudge",
        "hydromantic",
        "belize",
        "hamheung",
        "shellacking",
        "clerklike",
        "literate",
        "confiscator",
        "shikar",
        "pausefully",
        "rolling",
        "unneutral",
        "undesignated",
        "unprofiting",
        "epical",
        "pen",
        "corrigibleness",
        "unheuristically",
        "exoterically",
        "intractable",
        "venereal",
        "creaturely",
        "loftiest",
        "preinscribe",
        "bibliophily",
        "birch",
        "archaeopteryx",
        "unvolubleness",
        "dysthymic",
        "papacy",
        "shanghai",
        "semiramis",
        "emerald",
        "unexclaiming",
        "statecraft",
        "vesicularly",
        "torsion",
        "oversoothing",
        "twp",
        "akasha",
        "behaviour",
        "nonimmanent",
        "undredged",
        "couchant",
        "isolda",
        "pacificistically",
        "pelvis",
        "unforecasted",
        "prolation",
        "reoxidize",
        "northeasterner",
        "rebut",
        "appealingness",
        "kneeler",
        "switcheroo",
        "europium",
        "alba",
        "enthronization",
        "specialism",
        "excusable"
    };

    static String[] mediaTypes = {"photo","video","audio"};
}
