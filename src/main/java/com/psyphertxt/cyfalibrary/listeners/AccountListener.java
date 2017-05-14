package com.psyphertxt.cyfalibrary.listeners;


/**
 * Created by Codebendr on 18/05/2016.
 */
public class AccountListener {

    public interface onUser {

        void loggedIn(Object result);

        void notLoggedIn(String error);

    }


}
