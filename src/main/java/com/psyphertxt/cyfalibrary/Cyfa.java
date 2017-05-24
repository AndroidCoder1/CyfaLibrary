package com.psyphertxt.cyfalibrary;

import android.content.Context;

import com.facebook.stetho.BuildConfig;
import com.facebook.stetho.Stetho;
import com.parse.ParseObject;
import com.psyphertxt.cyfalibrary.backend.parse.User;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;

/**
 * Created by Lisa on 5/15/17.
 **/

public class Cyfa {


    public Cyfa(){

    }
    /**
     * this method should be called in a class which extends Application (entry point of main
     * application)
     * the reason being it contains most of the startup methods needed
     * for the application to work well
     *
     * @param context the application context
     */


    public static void init(Context context) {

        Realm.init(context);

        CyfaConfig.initilaizeParseConfigs(context);
        //set up subclasses for parse
        ParseObject.registerSubclass(User.class);

        debug(context);
    }

    private static void debug(Context context) {

        if (BuildConfig.DEBUG) {

            // Create an InitializerBuilder
            // Enable Chrome DevTools
            // Use the InitializerBuilder to generate an Initializer
            // Initialize Stetho with the Initializer
            Stetho.initialize(
                    Stetho.newInitializerBuilder(context)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(context).build())
                            .build());

        }
    }
}
