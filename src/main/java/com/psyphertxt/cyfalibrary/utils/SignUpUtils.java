package com.psyphertxt.cyfalibrary.utils;


import android.content.Context;
import android.util.Log;

import com.psyphertxt.cyfalibrary.CyfaConfig;
import com.psyphertxt.cyfalibrary.Prefs;
import com.psyphertxt.cyfalibrary.backend.parse.User;
import com.psyphertxt.cyfalibrary.backend.parse.UserAccount;
import com.psyphertxt.cyfalibrary.listeners.CallbackListener;
import com.psyphertxt.cyfalibrary.models.Country;
import com.psyphertxt.cyfalibrary.models.ErrorCodes;
import com.psyphertxt.cyfalibrary.models.SignUp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.psyphertxt.cyfalibrary.CyfaConfig.TAG;

/**
 * Class with helper methods for signing up.
 * ps: because of rapid prototyping and faster iteration
 * it makes sense to abstract listener methods this way
 */

public class SignUpUtils {

    public static void validateUser(final Context context, final SignUp signUp, final CallbackListener.onSignUpCompletionListener listener) {

        //lets create a new instance of the user account
        //to access user features
        final UserAccount userAccount = new UserAccount();
        final Prefs prefs = new Prefs(context);
        listener.before(context);
        //TODO check the type of network 4G, 3G, H
        if (NetworkUtils.isAvailable(context)) {

            //send user number to cloud code to verify
            //show loading progress
            //load next Verify Code Activity

            //lets create a new country instance
            CountryUtils.init(context);

            //retrieve and set the users country and zip code
            Country country = CountryUtils.getCountry(context);
            signUp.setCallingCode(country.getCallingCode());

            //check if the user exist in our data browser
            userAccount.userValidation(signUp.getPhoneNumber(), new CallbackListener.callbackForResults() {

                @Override
                public void success(Object result) {

                    try {

                        //suppress the hash map check because cloud code should always return
                        //key as string and value as any object
                        @SuppressWarnings("unchecked")
                        HashMap<String, Object> _result = (HashMap<String, Object>) result;

                        Log.d(TAG, result.toString());

                        int responseCode = (Integer) _result.get(NetworkUtils.KEY_RESPONSE_CODE);

                        if (responseCode == NetworkUtils.RESPONSE_OK) {

                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> _data = (HashMap<String, Object>) _result.get(CyfaConfig.DATA);

                            //any cloud code call should return a status message
                            signUp.isExisting((Boolean) _data.get(CyfaConfig.KEY_IS_EXISTING_USER));

                            //lets save the code from the server for the next activity
                            signUp.setCode((Integer) _data.get(CyfaConfig.KEY_CODE));

                            //if we detect an existing user, lets add these extra values
                            //to be sent to the next intent
                            if (signUp.isExisting()) {
                                signUp.setSessionToken((String) _data.get(CyfaConfig.KEY_SESSION_TOKEN));
                                signUp.setUsername((String) _data.get(CyfaConfig.KEY_USERNAME));
                            }

                            //update sign up class
                            prefs.storeObject(CyfaConfig.KEY_SIGN_UP, signUp);

                        } else {
                            listener.error(ErrorCodes.RESPONSE_CODE_EXCEPTION_ERROR.toString());
                        }

                        listener.success(signUp);

                    } catch (Exception e) {
                        listener.error(ErrorCodes.JSON_EXCEPTION_ERROR.toString());
                    }
                }

                @Override
                public void error(String error) {
                    listener.error(ErrorCodes.API_FAILED_ERROR.toString());
                }
            });

        } else {

            listener.error(ErrorCodes.NETWORK_ERROR.toString());
        }
    }

    public static void validateCode(final Context context, String verifyCode, final CallbackListener.onUserCompletionListener listener) {

        listener.before(context);

        if (NetworkUtils.isAvailable(context)) {

            //get user data from event bus
            final Prefs prefs = new Prefs(context);
            final SignUp signUp = (SignUp) prefs.getStoredSignUpObject();

            if (signUp != null) {

                //lets create a new instance of the user account class
                //to access user account sign up features
                final UserAccount userAccount = new UserAccount();

                //compare verification code if it matches create a new user
                //and show next activity
                if (verifyCode.length() == CyfaConfig.CODE_LENGTH && Integer.parseInt(verifyCode) == signUp.getCode()) {

                    //Alerts.progress(context);

                    //store users phone number and calling code locally
                    Prefs settings = new Prefs(context);
                    settings.setPhoneNumber(signUp.getPhoneNumber());
                    settings.setCallingCode(signUp.getCallingCode());

                    if (!signUp.isExisting()) {

                        SecurityUtils.generateHash(signUp.getPhoneNumber(), new CallbackListener.callbackForResults() {

                            @Override
                            public void success(Object result) {

                                userAccount.newUser((String) result, new CallbackListener.callbackForResults() {

                                    @Override
                                    public void success(Object result) {
                                        listener.success((User)result);

                                    }

                                    @Override
                                    public void error(String error) {
                                        listener.error(ErrorCodes.API_FAILED_ERROR.toString());
                                    }
                                });
                            }

                            @Override
                            public void error(String error) {
                                listener.error(ErrorCodes.API_FAILED_ERROR.toString());
                            }
                        });

                    } else {

                        JSONObject user = new JSONObject();
                        try {
                            user.put(CyfaConfig.KEY_USERNAME, signUp.getUsername());
                            user.put(CyfaConfig.KEY_PASSWORD, signUp.getSessionToken());
                            userAccount.existingUser(user, new CallbackListener.callbackForResults() {
                                @Override
                                public void success(Object result) {
                                    prefs.storeObject(CyfaConfig.KEY_USER, result);
                                    listener.success((User)result);
                                }

                                @Override
                                public void error(String error) {
                                    listener.error(ErrorCodes.API_FAILED_ERROR.toString());
                                }
                            });
                        } catch (JSONException e) {
                            listener.error(ErrorCodes.JSON_EXCEPTION_ERROR.toString());
                        }
                    }

                } else {
                    listener.error(ErrorCodes.WRONG_VALIDATION_CODE_ERROR.toString());
                }

            } else {
                listener.error(ErrorCodes.UNKNOWN_ERROR.toString());
            }

        } else {
            listener.error(ErrorCodes.NETWORK_ERROR.toString());
        }
    }
}
