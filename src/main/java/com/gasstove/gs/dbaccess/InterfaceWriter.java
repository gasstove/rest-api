package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.DBObject;

/**
 * Interface for database writers
 */
public interface InterfaceWriter {
    public int insert(DBObject obj) throws Exception;
    public int update(DBObject obj) throws Exception;
    public boolean delete(int id) throws Exception;
}
