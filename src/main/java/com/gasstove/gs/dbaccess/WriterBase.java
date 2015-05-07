package com.gasstove.gs.dbaccess;

//import org.joda.time.DateTime;
//import core.oraExecuteSP;
//import core.oraSPParams;

//import com.gasstove.gs.models.DBObject;

/**
 * Base class for database writers
 */
public abstract class WriterBase implements WriterInterface {

    public static enum CrudFlag {CREATE,UPDATE,DELETE};

    public java.sql.Connection dbConn = null;
//    public String dbSchema;
    public String dbUserName;

    // Oracle constants
//    public static final int DEFAULT_SRID = 8307;
//    public static final int DEF_FETCH_SIZE = 5000;
//    public static final String DEF_DATE_FORMAT = "DD-MON-YYYY HH24:MI:SS.FF6";

//    // The following 2 format strings MUST BE KEPT IN SYNC!
//    protected static final String timeFormat = "yyyy/MM/dd HH:mm:ss"; // FORMAT FOR DateTime string
//    protected static final String dbTimeFormat = "YYYY/MM/DD HH24:MI:SS"; // FORMAT FOR ORACLE TO_TIMESTAMP

    public WriterBase(java.sql.Connection dbConn) {
//        this.setDBSchema();
        this.dbConn = dbConn;

        // try to get database username from connection
        try {
            this.dbUserName = this.dbConn.getMetaData().getUserName();
        } catch (java.sql.SQLException exp) {
            System.err.print("Error Getting Username from connection object. " + exp.getMessage());
        }
    }

//    public Long processTransAction(DBObject object,CrudFlag crudFlag) throws Exception {
//        Long retVal = 0L;
//        switch (crudFlag){
//            case CREATE:
//                insert(object) ;
//                break;
//            case UPDATE:
//                update(object);
//                break;
//            case DELETE:
//                delete(object.getId());
//                break;
//            default:
//                throw new Exception("Invalid CRUD Flag");
//        }
//        return retVal ;
//    }

//    /**
//     * Set Database schema to write out to value of dbSchema property,
//     * Defaults to VIA schema.
//     */
//    private void setDBSchema() {
//        this.dbSchema = System.getenv("dbSchema");
//        if ( dbSchema == null ) {
//            this.dbSchema = System.getProperty("dbSchema");
//            if ( dbSchema == null ) {
//                this.dbSchema = "VIA";
//            }
//        }
//    }

//    /**
//     * Return Connection associated with DB Base
//     */
//    public java.sql.Connection getConnection() {
//        return this.dbConn;
//    }

//    /**
//     * Create unique Query, Resultset, or SP Name
//     *
//     * @param prefaceName String to preface query, resultset or SP name
//     * @return String unique string name
//     */
//    public String createUniqueName(String prefaceName) {
//        String unique = prefaceName + "_" + this.dbUserName + "_" + new DateTime().getMillis();
//        return unique;
//    }
//
//    /**
//     * Wrapper Function to execute all Stored Procedure using oraExecuteSP library
//     * Assumes, last parameter in SP always holds result code which is 0 if successful
//     *
//     * @param spName a unique name that will be used to reference this sp in the list of sp's
//     * @param spStr This is the name of the SP to call (WITHOUT SCHEMA NAME)
//     * @param params Array of oraSPParams to execute a particular stored procedure
//     */
//    void executeSP(String spName, String spStr, oraSPParams[] params) throws MODatabaseException {
//        try {
//            //  Create SP with given parameters
//            if(!oraExecuteSP.spCreate(this.dbConn, spName, this.dbSchema + "." + spStr , params)) {
//                throw new MODatabaseException(null, "Error Creating SP for " + this.dbSchema + "." + spStr , spName);
//            }
//
//            // Bind parameters to SP
//            if (!oraExecuteSP.spBind(spName)) {
//                throw new MODatabaseException(null, "Error Binding SP for " + this.dbSchema + "." + spStr , spName);
//            }
//
//            // Call/Execute SP
//            if (!oraExecuteSP.callSP(spName)) {
//                throw new MODatabaseException(null, "Error Executing SP for " + this.dbSchema + "." + spStr , spName);
//            }
//
//            // Check if result is not successful
//            if (params[params.length-1].getIntValue() != 0) {
//                throw new MODatabaseException(null, "Internal Error [ code " + params[params.length-1].getIntValue() +
//                        "] Executing SP " + this.dbSchema + "." + spStr , spName);
//            }
//
//        } finally {
//            oraExecuteSP.spClose(spName);
//        }
//    }
}
