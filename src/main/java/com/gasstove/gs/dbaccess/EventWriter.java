package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.DBObject;
import org.omg.CORBA_2_3.portable.OutputStream;

import java.sql.Connection;

public class EventWriter extends WriterBase {

    private static final String ROUTE_LINK_INSERT_SP = "PKG_ROUTE_LINKS.INS";
    private static final String ROUTE_LINK_UPDATE_SP = "PKG_ROUTE_LINKS.UPD";
    private static final String ROUTE_LINK_DELETE_SP = "PKG_ROUTE_LINKS.DEL";

    public EventWriter(Connection connection) {
        super(connection);
    }


    public Long insert(DBObject object) throws Exception {

        String qName = "routeIns";
        String[] params = new String[5] ;

//        params[0] = oraSPParams.intParam(1, oraSPEnums.spParamDir.OUT,0L);
//        params[1] = oraSPParams.intParam(2, oraSPEnums.spParamDir.IN,routeLink.getRouteId());
//        params[2] = oraSPParams.intParam(3, oraSPEnums.spParamDir.IN,routeLink.getLinkId());
//        params[3] = oraSPParams.intParam(4, oraSPEnums.spParamDir.IN, (long)routeLink.getLinkOrder());
//        params[4] = oraSPParams.intParam(5, oraSPEnums.spParamDir.OUT,0L);

        executeSP(qName, ROUTE_LINK_INSERT_SP, params);

        return null; //params[0].getIntValue() ;
    }

    public Long update(DBObject object) throws Exception{

        String qName = "routeUdt";
        String[] params = new String[6] ;


//        params[0] = oraSPParams.intParam(1, oraSPEnums.spParamDir.IN,routeLink.getId());
//        params[1] = oraSPParams.intParam(2, oraSPEnums.spParamDir.IN,routeLink.getRouteId());
//        params[2] = oraSPParams.intParam(3, oraSPEnums.spParamDir.IN,routeLink.getLinkId());
//        params[3] = oraSPParams.intParam(4, oraSPEnums.spParamDir.IN, (long) routeLink.getLinkOrder());
//        params[4] = oraSPParams.strParam(5, oraSPEnums.spParamDir.IN,routeLink.getModStamp());
//        params[5] = oraSPParams.intParam(6, oraSPEnums.spParamDir.OUT,0L);


        executeSP(qName, ROUTE_LINK_UPDATE_SP, params);

        return null; // params[5].getIntValue() ;
    }

    public Long delete(DBObject object) throws Exception {

        String qName = "routeDel";
        String[] params = new String[4] ;

//        params[0] = oraSPParams.intParam(1, oraSPEnums.spParamDir.IN,routeLink.getId());
//        params[1] = oraSPParams.intParam(2, oraSPEnums.spParamDir.IN,routeLink.getRouteId());
//        params[2] = oraSPParams.strParam(3, oraSPEnums.spParamDir.IN,routeLink.getModStamp());
//        params[3] = oraSPParams.intParam(4, oraSPEnums.spParamDir.OUT,0L);

        executeSP(qName, ROUTE_LINK_DELETE_SP, params);

        return null; //params[3].getIntValue() ;
    }


}
