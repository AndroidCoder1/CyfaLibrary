package com.psyphertxt.cyfalibrary.models;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.psyphertxt.cyfalibrary.listeners.CallbackListener;
import com.psyphertxt.cyfalibrary.listeners.TokenListener;
import com.psyphertxt.cyfalibrary.utils.SecurityUtils;

public class TokenValidator {

    public static FirebaseAuth.AuthStateListener AuthStateListener;
    private static Boolean IS_TOKEN = false;

    public static void onTokenChange(final TokenListener.onTokenChange onTokenChange) {

        AuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    IS_TOKEN = true;
                    onTokenChange.valid();

                } else {
                    if (!IS_TOKEN) {
                        onTokenChange.invalid();

                        //generate a JWT token
                        SecurityUtils.generateToken(new CallbackListener.callbackForResults() {

                            @Override
                            public void success(Object result) {

                                //validate the token on the messaging platform
                                validate((String) result);
                            }

                            @Override
                            public void error(String error) {

                            }
                        });
                    }
                }

            }
        };

        FirebaseAuth.getInstance().addAuthStateListener(AuthStateListener);
    }

    public static void invalidate() {
        FirebaseAuth.getInstance().signOut();
        if (AuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(AuthStateListener);
        }
    }

    public static void stop() {
        if (AuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(AuthStateListener);
        }
    }

    public static void validate(final String token) {
        FirebaseAuth.getInstance().signInWithCustomToken(token).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    IS_TOKEN = false;
                } else {
                    IS_TOKEN = true;
                }
            }
        });
    }
}

