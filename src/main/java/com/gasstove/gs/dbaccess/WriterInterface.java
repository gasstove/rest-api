package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.DBObject;

public interface WriterInterface {
    public Long insert(DBObject obj) throws Exception;
    public Long update(DBObject obj) throws Exception;
    public Long delete(DBObject obj) throws Exception;
}
