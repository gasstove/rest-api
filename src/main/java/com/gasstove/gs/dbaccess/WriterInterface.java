package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.DBObject;

public interface WriterInterface {
    public int insert(DBObject obj) throws Exception;
    public void update(DBObject obj) throws Exception;
    public void delete(DBObject obj) throws Exception;
}
