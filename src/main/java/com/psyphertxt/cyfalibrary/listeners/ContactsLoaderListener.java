package com.psyphertxt.cyfalibrary.listeners;

import java.util.List;

/**
 * Created by pie on 10/5/15.
 * Provides stubs for hooking up custom login into the contacts loading cycle
 */
public interface ContactsLoaderListener<T> {

    /**
     * Called before contacts loading begins
     * NOTE : All UI changes done in this callback should be done within a
     * runOnUiThread(new Runnable(){}.start()) block.
     */
    void onBeforeLoadingContacts();

    /**
     * Called when loading of contacts completes successfully
     * NOTE : All UI changes done in this callback should be done within a
     * runOnUiThread(new Runnable(){}.start()) block.
     */
    void onCompletedLoadingContacts(List<T> phoneContacts);


    /**
     * Called when there's an error during loading of contacts
     * NOTE : All UI changes done in this callback should be done within a
     * runOnUiThread(new Runnable(){}.start()) block.
     */
    void onErrorLoadingContacts(String error);
}
