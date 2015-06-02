package com.gasstove.gs.util;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/**
 * This is the script to populate the test database
 */
public class DataGenerator {

    // db file
    private final String dbfile = "src/main/resources/gasstove.db";

    //set these values to manipulate test records
    private final int MIN_USER_PER_EVENT = 1;
    private final int MAX_USER_PER_EVENT = 30;

    private final int MIN_MEDIA_PER_EVENTUSER = 0;
    private final int MAX_MEDIA_PER_EVENTUSER = 10;

    // true if a media item can belong to more than one event, false otherwise
    private final boolean ALLOW_MULTIPLE_EVENTS_PER_MEDIA = true;

    // if above is true, average number of events a media item belongs to (can be 1.5)
    private final float AVG_EVENT_PER_MEDIA = 2f;

    private final int MIN_EVENT_YEAR = 2014;
    private final int MAX_EVENT_YEAR = 2016;
    private final int MIN_EVENT_DURATION = 5;   // hours
    private final int MAX_EVENT_DURATION = 10;  // hours

    private final int NUM_USERS = 50;
    private final int NUM_EVENTS = 10;

    private Connection connection = null;
    private PreparedStatement  statement = null;
    private Statement stmt = null;

    /**
     * Main method to load test data
     *
     * @param args
     */
    public static void main(String[] args){
        DataGenerator t = new DataGenerator();
        DataContainer data = t.generate_data();

        try {
            t.dropTables();
            t.getConnection();
            t.createDB();
            t.insert_db(data);
        }
        catch(Exception s){
            s.printStackTrace();
        }
    }

    /**
     * Establish connection to db
     *
     * @return Connection database connection
     */
    public void getConnection() throws SQLException {
        connection = new DBConnection().getConnection();
    }

    public void dropTables(){
        try {
            System.out.println("Dropping tables...");
            File f = new File(this.dbfile);
            f.delete();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("All tables dropped");
    }

    public void createDB() throws SQLException{
        System.out.println("Creating database and tables");

        stmt = connection.createStatement();
        String sql = "CREATE TABLE roles (" +
                "name varchar PRIMARY KEY NOT NULL, " +
                "modify_event_name boolean NOT NULL, " +
                "modify_event_date boolean NOT NULL, " +
                "delete_event boolean NOT NULL, " +
                "add_guest boolean NOT NULL " +
                ")";

        stmt.execute(sql);

        sql = "CREATE TABLE media_mapping (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "media_id int NOT NULL, " +
                "event_id int NOT NULL, " +
                "num_downloads int NOT NULL, " +
                "shared boolean NOT NULL, " +
                "comment varchar, " +
                "num_likes int, " +
                "num_dislikes int " +
                ")";
        stmt.execute(sql);

        sql =   "CREATE TABLE media ( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "type varchar NOT NULL, " +
                "file_name	varchar NOT NULL, " +
                "user_id INTEGER, " +
                "date_taken smalldatetime NOT NULL " +
                ")";
        stmt.execute(sql);

        sql =   "CREATE TABLE event (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name varchar NOT NULL, " +
                "open_date smalldatetime NOT NULL, " +
                "close_date smalldatetime NOT NULL, " +
                "join_invitation boolean NOT NULL, " +
                "join_allow_by_accept boolean NOT NULL, " +
                "join_allow_auto boolean NOT NULL " +
                ")";
        stmt.execute(sql);

         sql =  "CREATE TABLE user_event_mapping (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "event_id int NOT NULL, " +
                "user_id int NOT NULL, " +
                "role varchar NOT NULL" +
                ")";
         stmt.execute(sql);

         sql =  "CREATE TABLE user( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "first varchar NOT NULL, " +
                "last varchar NOT NULL " +
                ")";
         stmt.execute(sql);
         System.out.println("Database created");

    }

    /**
     * Removes all the records from each table so they can be populated
     */
    public void removeAllRecords() throws SQLException {

        String sql = "DELETE FROM roles";
        stmt = connection.createStatement();
        stmt.execute(sql);

        sql = "DELETE FROM user";
        stmt = connection.createStatement();
        stmt.execute(sql);

        sql = "DELETE FROM event";
        stmt = connection.createStatement();
        stmt.execute(sql);

        sql = "DELETE FROM media";
        stmt = connection.createStatement();
        stmt.execute(sql);

        sql = "DELETE FROM user_event_mapping";
        stmt = connection.createStatement();
        stmt.execute(sql);

        sql = "DELETE FROM media_mapping";
        stmt = connection.createStatement();
        stmt.execute(sql);

    }

    /**
     * Generate sets of users, events, and media
     */
    public DataContainer generate_data(){

        HashSet<User> all_users = new HashSet<User>();
        HashSet<Event> all_events = new HashSet<Event>();
        HashSet<Media> all_medias = new HashSet<Media>();

        // create users
        for (int i = 0; i < NUM_USERS; i++)
            all_users.add(new User(userNames[i]));

        // create events
        for (int i = 0; i < NUM_EVENTS; i++) {

            // choose an owner
            User owner = Util.sample(all_users);

            // create the event
            Event event = new Event(eventNames[i]);

            // add to owner and events list
            owner.add_event(event);
            all_events.add(event);
            event.add_owner(owner);
        }

        // invite random list of users
        for(Event event : all_events) {

            // guest pool is all users minus the event owner
//            HashSet<User> guest_pool = (HashSet<User>) all_users.clone();
//            guest_pool.remove(event.owner);
            event.invite_from(all_users);
        }

        // event users and owners take a bunch of photos
        for(User user : all_users )
            all_medias.addAll( user.generate_media() );

        // assign unique file extensions to medias
        int n = all_medias.size();
        ArrayList<Integer> fileext = Util.sample_without_replacement(Util.intset(1000, 1000 + n), n);
        int c = 0;
        for(Media media : all_medias)
            media.set_fileext(fileext.get(c++));

        return new DataContainer(all_users,all_events,all_medias);
    }

    /**
     * Insert data into db
     */
    public void insert_db(DataContainer data) throws SQLException {

        int i;

        // Roles and permissions .....................................
        // check whether the role has each of the permissions
        ArrayList<String> permStrings = new ArrayList<String>();
        ArrayList<String> qmStrings = new ArrayList<String>();
        for(Permissions.Type permission : Permissions.Type.values()){
            permStrings.add( permission.toString().toLowerCase() );
            qmStrings.add("?");
        }
        String insertStr = "INSERT into roles(name," + Util.joinToString(permStrings,",") + ") " +
                "VALUES(?," + Util.joinToString(qmStrings,",") + ")";

        // iterate through roles defined in PermissionList
        for (Map.Entry<Permissions.Role,ArrayList<Permissions.Type>> entry : Permissions.PermissionList.entrySet())
        {
            i=1;
            statement = connection.prepareStatement(insertStr);
            Permissions.Role role = entry.getKey();
            ArrayList<Permissions.Type> my_permissions = entry.getValue();

            // add name
            statement.setString(i++,role.toString().toLowerCase());

            // check each of the permissions
            for(Permissions.Type permission : Permissions.Type.values())
                statement.setBoolean(i++,my_permissions.contains(permission));
            statement.execute();
        }

        // insert users into db  .....................................
        System.out.println("Inserting "+data.all_users.size()+" users.");
        for(User user : data.all_users)
            user.insert_db();

        // insert events into db  .....................................
        System.out.println("Inserting "+data.all_events.size()+" events.");
        for(Event event : data.all_events)
            event.insert_db();

        // insert media into db .....................................
        System.out.println("Inserting "+data.all_medias.size()+" media items.");
        for(Media media : data.all_medias)
            media.insert_db();

        // insert guest lists into user_event_mapping
        for(Event event : data.all_events) {
            System.out.println("Inserting "+event.userroles.size()+" users for event " + event.name +".");
            event.insert_guests_db();
        }

        // insert media info into media_mapping
        System.out.println("Inserting media mapping for "+data.all_medias.size()+" media items.");
        for(Media media : data.all_medias)
            media.insert_event_mapping_db();

    }

    /* .. DATA HOLDING CLASSES ................................................... */
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
        Time date_taken;

        ArrayList<Event> events = new ArrayList<Event>();

        public Media(User owner){
            this.owner = owner;
            this.mediaType = mediaTypes[Util.randBetween(0,3)];
            this.date_taken = Time.randomDate(MIN_EVENT_YEAR,MAX_EVENT_YEAR);
        }

        public void set_fileext(Integer f){
            this.filename_ext = f;
        }

        public void add_to_event(Event event){
            this.events.add(event);
            event.add_media(this);
        }

        public void insert_db() throws SQLException {
            if(id!=null)
                throw new SQLException("Repeat insertion of media " + id);

            // insert the media
            String sql = "INSERT into media(type, file_name, user_id, date_taken) VALUES(?,?,?,?)";
            statement = connection.prepareStatement(sql);
            int i=1;
            statement.setString(i++,mediaType);
            statement.setString(i++, "media_" + filename_ext);
            statement.setString(i++, owner.id.toString() );
            statement.setDate(i++, this.date_taken.toSqlDate() );
            statement.execute();

            // get media id
            sql = "SELECT Max(id) from media";
            stmt = connection.createStatement();
            ResultSet r = stmt.executeQuery(sql);
            if (r.next())
                this.id = r.getInt(1);
        }

        public void insert_event_mapping_db() throws SQLException {
            for(Event event : events) {
                String sql = "INSERT into media_mapping(media_id, event_id,num_downloads,shared) VALUES(?,?,?,?)";
                statement = connection.prepareStatement(sql);
                int i=1;
                statement.setInt(i++, this.id);
                statement.setInt(i++, event.id );
                statement.setInt(i++, Util.randBetween(10, 10000));
                statement.setInt(i++, Util.randBetween(0, 2));
                statement.execute();
            }
        }

    }

    private class Event {

        Integer id;
        String name;
        Time open_date;
        Time close_date;
        ArrayList<UserRole> userroles = new ArrayList<UserRole>();
        ArrayList<Media> medias = new ArrayList<Media>();
//        HashMap<User,Integer> userEventId_for_user = new HashMap<User,Integer>();

        public Event(String name){
            this.name = name;
            this.open_date = Time.randomDate(MIN_EVENT_YEAR,MAX_EVENT_YEAR);
            this.close_date = this.open_date.add_hours( Util.randBetween((double) MIN_EVENT_DURATION,(double) MAX_EVENT_DURATION) );
        }

        public void invite_from(HashSet<User> user_pool){
            int num_guests = Util.randBetween(MIN_USER_PER_EVENT, MAX_USER_PER_EVENT);
            ArrayList<User> guests = Util.sample_without_replacement(user_pool, num_guests);
            this.add_guests(guests);
            // inform users
            for(User user : guests)
                user.add_event(this);
        }

//        public Integer get_usereventid_for_user(User user){
//            return userEventId_for_user.get(user);
//        }

        public void add_media(Media media){
            this.medias.add(media);
        }

        public void add_guests( ArrayList<User> users ){
            for(User user : users)
                this.userroles.add(new UserRole(user, Permissions.Role.GUEST));
        }

        public void add_owner( User user ){
            this.userroles.add(new UserRole(user, Permissions.Role.OWNER));
        }

        public void insert_db() throws SQLException{
            if(id!=null)
                throw new SQLException("Repeat insertion of event " + id);
            String sql = "INSERT into event(name," +
                                            "open_date," +
                                            "close_date," +
                                            "join_invitation," +
                                            "join_allow_by_accept," +
                                            "join_allow_auto) " +
                                            "VALUES(?,?,?,1,1,1)";      // TODO: SET BOOLEANS!!!
            statement = connection.prepareStatement(sql);
            int i=1;
            statement.setString( i++, this.name);
            statement.setDate(   i++, this.open_date.toSqlDate());
            statement.setDate(   i++, this.close_date.toSqlDate());
            statement.execute();

            // get event ids
            sql = "SELECT Max(id) from event";
            stmt = connection.createStatement();
            ResultSet r = stmt.executeQuery(sql);
            if (r.next())
                this.id = r.getInt(1);
        }

        public void insert_guests_db() throws SQLException {

            // add users to this event
            for(UserRole userrole : this.userroles) {
                String sql = "INSERT into user_event_mapping(event_id, user_id,role) VALUES(?,?,?)";
                statement = connection.prepareStatement(sql);
                int i=1;
                statement.setInt( i++, this.id);
                statement.setInt( i++, userrole.user.id);
                statement.setString(i++, userrole.role.toString().toLowerCase() );
                statement.execute();

//                // extract user_event ids
//                sql = "SELECT Max(id) from user_event_mapping";
//                stmt = connection.createStatement();
//                ResultSet r = stmt.executeQuery(sql);
//                if (r.next())
//                    userEventId_for_user.put(user, r.getInt(1));
            }
        }

    }

    private class User {
        Integer id;
        String first;
        String last;

        HashSet<Event> events = new HashSet<Event>();
        HashSet<Media> medias = new HashSet<Media>();
        public User(String fullname){
            String[] name = fullname.split(" ");
            this.first = name[0];
            this.last = name[1];
        }

        public void add_event(Event event){
            events.add(event);
        }

        public void insert_db() throws SQLException {

            if(id!=null)
                throw new SQLException("Repeat insertion of user " + id);
            String sql = "INSERT into user(first,last) VALUES(?,?)";
            statement = connection.prepareStatement(sql);
            int i=1;
            statement.setString(i++, this.first);
            statement.setString(i++, this.last);
            statement.execute();

            // extract user id
            sql = "SELECT Max(id) from user";
            stmt = connection.createStatement();
            ResultSet r = stmt.executeQuery(sql);
            if (r.next())
                this.id = r.getInt(1);

        }

        // generate a number of media items,
        // for each media item, randomly decide what events it belongs to from my list of events
        // add it to that event or events
        public HashSet<Media> generate_media(){
            // pick the number of media I generate as num_events * avg_media_per_event / avg_event_per_media
            float num_events = (float) events.size();
            float avg_media_per_event = (float) Util.randBetween(MIN_MEDIA_PER_EVENTUSER,MAX_MEDIA_PER_EVENTUSER);
            int num_media = ALLOW_MULTIPLE_EVENTS_PER_MEDIA ?
                            Math.round( num_events * avg_media_per_event / AVG_EVENT_PER_MEDIA ) :
                            Math.round( num_events * avg_media_per_event );

            // generate each media, assign it to an event(s)
            for(int i=0 ; i<num_media ; i++ ){
                Media media = new Media(this);

                medias.add(media);

                // how many events does it belong to?
                long num_event_for_media = ALLOW_MULTIPLE_EVENTS_PER_MEDIA ?
                                           Math.max(0,Math.round(AVG_EVENT_PER_MEDIA + Util.randBetween(-0.5d,0.5d))) : // cheap binomial
                                           1 ;

                // sample that many events, add media to each event
                ArrayList<Event> events_for_media = Util.sample_without_replacement(this.events, (int) num_event_for_media);
                for(Event event : events_for_media)
                    media.add_to_event(event);
            }
            return medias;
        }
    }

    private class UserRole {
        User user;
        Permissions.Role role;
        public UserRole(User user, Permissions.Role role){
            this.user = user;
            this.role = role;
        }
    }
    /* .. STATICS ....................................................... */

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
