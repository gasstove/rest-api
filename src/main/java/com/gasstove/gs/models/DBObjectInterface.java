package com.gasstove.gs.models;

/**
 * Created by gomes on 6/10/15.
 */
public interface DBObjectInterface {

    /** compare with another DBObject in terms of table fields
     * @param o
     * @return
     */
    public boolean shallowEquals(DBObject o);

    /** Compare with another DBObject in terms of table and cross-table fields
     *
     * @param o
     * @return
     */
    public boolean deepEquals(DBObject o);
}
