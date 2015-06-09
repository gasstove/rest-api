package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.DBObject;
import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by gomes on 6/9/15.
 */
public abstract class BaseIO <T> {

    protected Connection conn;

//    public String dbUserName;

    public static enum CrudFlag {CREATE,UPDATE,DELETE};

    // Oracle constants
//    public static final int DEFAULT_SRID = 8307;
//    public static final int DEF_FETCH_SIZE = 5000;
//    public static final String DEF_DATE_FORMAT = "DD-MON-YYYY HH24:MI:SS.FF6";

//    // The following 2 format strings MUST BE KEPT IN SYNC!
//    protected static final String timeFormat = "yyyy/MM/dd HH:mm:ss"; // FORMAT FOR DateTime string
//    protected static final String dbTimeFormat = "YYYY/MM/DD HH24:MI:SS"; // FORMAT FOR ORACLE TO_TIMESTAMP


    ///////////////////////////////////////////
    // CONSTRUCTION
    ///////////////////////////////////////////

    public BaseIO() {
        this(Configuration.dbConnect);
    }

    public BaseIO(String db) {
        try {
            conn = (new DBConnection()).getConnection(db);
        } catch (SQLException e) {
            System.err.print("Unable to connect to database. " + e.getMessage());
        }
    }

    public BaseIO(Connection conn) {
        this.conn = conn;
//        // try to get database username from connection
//        try {
//            this.dbUserName = this.dbConn.getMetaData().getUserName();
//        } catch (java.sql.SQLException exp) {
//            System.err.print("Error Getting Username from connection object. " + exp.getMessage());
//        }
    }

    ///////////////////////////////////////////
    // insert / update / delete / close
    ///////////////////////////////////////////

    /** insert single row into a table **/
    public final int insert(DBObject obj) throws Exception{
        String sql = "INSERT into " + get_table_name() + "(" + get_csv_fields() + ") " + "VALUES(" + get_csv_question_marks() + ")";
        PreparedStatement statement = conn.prepareStatement(sql);
        fill_prepared_statement(statement,obj);
        int r = statement.executeUpdate();
        if(r!=1)
            throw new Exception("Insert failed for event " + obj.getId());
        return getLargestId();
    };

    /** update single row in a table **/
    public final int update(DBObject obj) throws Exception{
        String sql = "UPDATE " + get_table_name() + " SET " + get_csv_fields_question_marks()+"WHERE id=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        int i = fill_prepared_statement(statement,obj);
        statement.setInt(i,obj.getId());
        int r = statement.executeUpdate();
        if(r!=1)
            throw new Exception("Update failed for " + get_table_name() + ", id = " + obj.getId());
        return obj.getId();
    }

    /** delete single row from a table **/
    public final boolean delete(int id) throws Exception{
        String sql = "DELETE from " + get_table_name() + " WHERE id=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1,id);
        return statement.executeUpdate()==1;
    }

    ///////////////////////////////////////////
    // basic read
    ///////////////////////////////////////////

    /**
     * all items in the table
     */
    public ArrayList<T> getAll() {
        ArrayList<T> objs = new ArrayList<T>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + get_table_name());
            while (rs.next())
                objs.add(generate_from_result_set(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objs;
    }

    /**
     * all item with given id
     */
    public T getWithId(int id) {
        T x = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + get_table_name() + " where id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                x = generate_from_result_set(rs);
        } catch (SQLException sq) {
            sq.printStackTrace();
            return null;
        }
        return x;
    }

    ///////////////////////////////////////////
    // public api
    ///////////////////////////////////////////

    public int getLargestId() throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT Max(id) from " + get_table_name());
        return rs.next() ? rs.getInt(1) : -1;
    }

    public final void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////
    // to be overridden
    ///////////////////////////////////////////

    protected T generate_from_result_set(ResultSet rs){ return null; };
    protected String get_table_name(){ return null; }
    protected ArrayList<String> get_fields(){ return null; }
    protected int fill_prepared_statement(PreparedStatement ps,DBObject obj) throws SQLException { return -1; }

    ///////////////////////////////////////////
    // private
    ///////////////////////////////////////////

    private String get_csv_fields(){
        StringBuilder result = new StringBuilder();
        for(String string : get_fields())
            result.append(string+",");
        return result.length() > 0 ? result.substring(0, result.length() - 1): "";
    }

    private String get_csv_question_marks(){
        StringBuilder result = new StringBuilder();
        for(String string : get_fields())
            result.append("?,");
        return result.length() > 0 ? result.substring(0, result.length() - 1): "";
    }

    private String get_csv_fields_question_marks(){
        StringBuilder result = new StringBuilder();
        for(String string : get_fields())
            result.append(string + "=?,");
        return result.length() > 0 ? result.substring(0, result.length() - 1): "";
    }

}
