package com.gasstove.gs.util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class defines permissions for a user with respect to an event.
 */
public class Permissions {

    // roles
    public static enum Role { OWNER , GUEST };

    public static enum Type {
        MODIFY_EVENT_NAME ,
        MODIFY_EVENT_DATE ,
        DELETE_EVENT ,
        ADD_GUEST
    }

    public final static HashMap<Role,ArrayList<Type>> PermissionList;

    static{

        PermissionList = new HashMap<Role,ArrayList<Type>>();

        // define the OWNER role
        ArrayList<Type> owner_permissions = new ArrayList<Type>();
        owner_permissions.add( Type.MODIFY_EVENT_NAME );
        owner_permissions.add( Type.MODIFY_EVENT_DATE );
        owner_permissions.add( Type.DELETE_EVENT );
        owner_permissions.add( Type.ADD_GUEST );
        PermissionList.put( Role.OWNER , owner_permissions );

        // define the GUEST role
        ArrayList<Type> guest_permissions = new ArrayList<Type>();
        PermissionList.put( Role.GUEST , guest_permissions );

    }
}
