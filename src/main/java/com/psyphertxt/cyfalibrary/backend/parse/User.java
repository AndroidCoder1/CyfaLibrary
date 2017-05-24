package com.psyphertxt.cyfalibrary.backend.parse;

import com.parse.ParseACL;
import com.parse.ParseClassName;
import com.parse.ParseUser;
import com.psyphertxt.cyfalibrary.CyfaConfig;

import org.json.JSONArray;

import java.lang.reflect.Array;

/**
 * Class for managing users.
 * example, getting or setting the name of a user
 */

@ParseClassName("_User")
public class User extends ParseUser {

    public static User getDeviceUser() {
        return (User) ParseUser.getCurrentUser();
    }

    public static String getDeviceUserId() {
        return getDeviceUser().getObjectId();
    }

    public static ParseACL publicReadOnly() {
        ParseACL parseACL = new ParseACL();
        parseACL.setPublicReadAccess(true);
        parseACL.setWriteAccess(User.getDeviceUser(), true);
        return parseACL;
    }

    public void setUsername(String username) {
        put(CyfaConfig.KEY_USERNAME, username);
    }

    public JSONArray getContacts() {
        return getJSONArray(CyfaConfig.KEY_CONTACTS);
    }

    public void setContacts(Array contacts) {
        put(CyfaConfig.KEY_CONTACTS, contacts);
    }

    public int getType() {
        return getInt(CyfaConfig.KEY_TYPE);
    }

    public void setType(int type) {
        put(CyfaConfig.KEY_TYPE, type);
    }

    public class Type {
        public static final int USER = 0;
        public static final int COMPANY = 1;
        public static final int CUSTOMER_SERVICE = 2;
        public static final int BOTS = 3;

    }


}
