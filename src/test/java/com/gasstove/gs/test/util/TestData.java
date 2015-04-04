package com.gasstove.gs.test.util;

import com.gasstove.gs.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

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
        t.getConnection();
        t.removeAllRecords();
        t.addData();
    }

    //set these values to manipulate test records
    private final int NUM_EVENTS_USER = 5;
    private final int NUM_MEDIA_PER_EVENT_PER_USER = 5;
    private final int RANDOM_YEAR_START = 2014;
    private final int RANDOM_YEAR_END = 2016;
    private final int NUM_ACTORS = 10;
    private final int NUM_EVENTS = 90;



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
        }catch(SQLException e){
               e.printStackTrace();
        }

    }

    /**
     * Add all test data to tables. See final attributes defined to
     * manipulate number of records and such.
     *
     * The process for adding a records:
     *      - add an actor
     *      - add a bunch of events for the actor
     *      - for every event add record to actor_event_mapping
     *      - for every event add media
     *      - for every media add record to media_mapping using media id and actor_event_mapping id
     *      - repeat steps above for number of actors
     */
    public void addData(){
        try {
            int eventIndex = 0;
            int actorIndex = 0;
            int currentActorIndex = -1;
            int currentEventIndex = -1;
            int actorIdIndex = 0;
            int currentActorEventMappingIndex = -1;
            int currentMediaIndex = -1;
            ArrayList<Integer> actorIds = new ArrayList<Integer>();

            //add the roles you want defined
            String sql = "INSERT into roles(id,type) VALUES(?,?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, 1);
            statement.setString(2, "owner");
            statement.execute();

            sql = "INSERT into roles(id,type) VALUES(?,?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, 2);
            statement.setString(2, "member");
            statement.execute();
            ResultSet r = null;
            //this will loop for the number of actors defined
            for(int f = 0; f < NUM_ACTORS; f++, actorIndex++) {

                //insert an actor
                sql = "INSERT into actor(first,last,is_subscriber,contact_method) VALUES(?,?,1,?)";
                statement = connection.prepareStatement(sql);
                String[] name = rNames[actorIndex].split(" ");
                statement.setString(1, name[0]);
                statement.setString(2, name[1]);
                statement.setInt(3, (int) (Math.random() * 2));
                statement.execute();

                //get actor id
                sql = "SELECT Max(id) from actor";
                stmt = connection.createStatement();
                r = stmt.executeQuery(sql);
                if (r.next()) {
                    actorIds.add(r.getInt(1));
                }
            }

                //insert events for actor
                for (int x = 0; x < NUM_EVENTS; x++, eventIndex++) {
                    sql = "INSERT into event(name,open_date,close_date,join_invitation,join_allow_by_accept,join_allow_auto) VALUES(?,?,?,1,1,1)";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, events[eventIndex]);
                    statement.setDate(2, new java.sql.Date(randomDate().getTime()));
                    statement.setDate(3, new java.sql.Date(randomDate().getTime() + randBetween(5, 10)));
                    statement.execute();

                    //get event id
                    sql = "SELECT Max(id) from event";
                    stmt = connection.createStatement();
                    r = stmt.executeQuery(sql);
                    if (r.next()) {
                        currentEventIndex = r.getInt(1);
                    }

                    for(int d = 0; d < NUM_EVENTS_USER; d++) {
                        //add mapping for actor and event
                        sql = "INSERT into actor_event_mapping(event_id, actor_id,role_id,join_date) VALUES(?,?,?,?)";
                        statement = connection.prepareStatement(sql);
                        statement.setInt(1, currentEventIndex);
                        statement.setInt(2, actorIds.get(actorIdIndex++ % actorIds.size()));

                        statement.setInt(3, (int) (Math.random() * 2));
                        statement.setDate(4, new java.sql.Date(randomDate().getTime()));
                        statement.execute();

                        //get actor_event mapping id for media_mapping table
                        sql = "SELECT Max(id) from actor_event_mapping";
                        stmt = connection.createStatement();
                        r = stmt.executeQuery(sql);
                        if (r.next()) {
                            currentActorEventMappingIndex = r.getInt(1);
                        }

                        //add media for this event and actor
                        for (int k = 0; k < NUM_MEDIA_PER_EVENT_PER_USER; k++) {
                            //insert the media
                            sql = "INSERT into media(type, file_name) VALUES(?,?)";
                            statement = connection.prepareStatement(sql);
                            statement.setString(1, mediaTypes[(int) (Math.random() * 3)]);
                            statement.setString(2, "media_" + (int) (Math.random() * 10000));
                            statement.execute();

                            //get media id
                            sql = "SELECT Max(id) from media";
                            stmt = connection.createStatement();
                            r = stmt.executeQuery(sql);
                            if (r.next()) {
                                currentMediaIndex = r.getInt(1);
                            }

                            //add the media mapping using media id and actor_event_mapping id
                            sql = "INSERT into media_mapping(media_id, actor_event_mapping_id,num_downloads,shared) VALUES(?,?,?,?)";
                            statement = connection.prepareStatement(sql);
                            statement.setInt(1, currentMediaIndex);
                            statement.setInt(2, currentActorEventMappingIndex);
                            statement.setInt(3, randBetween(10, 10000));
                            statement.setInt(4, randBetween(0, 2));
                            statement.execute();
                        }
                    }
                }


        }
        catch(SQLException s){
            s.printStackTrace();
        }

    }

    /**
     * Generate a random date base on RANDOM_YEAR_START and RANDOM_YEAR_END
     *
     * @return java.util.Date
     */
    private java.util.Date randomDate(){
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(RANDOM_YEAR_START,RANDOM_YEAR_END);
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
        return start + (int)Math.round(Math.random() * (end - start));
    }

    static String[] rNames= {
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

    static String[] events = {
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
