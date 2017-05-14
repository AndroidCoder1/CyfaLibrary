package com.psyphertxt.cyfalibrary.utils;

import android.app.Activity;
import android.view.WindowManager;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.psyphertxt.cyfalibrary.Config;
import com.psyphertxt.cyfalibrary.backend.parse.User;
import com.psyphertxt.cyfalibrary.listeners.CallbackListener;
import com.scottyab.aescrypt.AESCrypt;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manage basic random string generation
 */
public class SecurityUtils {

    private static final String PUSH_CHARS = "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
    public static String PASSWORD = "punchy-tooth-3u3vievnpj3x11a32d3-18a9f52-1218f96-de30b9";

    public static String hash() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(25, random).toString(16);
    }

    public static String createHash() {
        return String.format("%s-%s-%s-%s", hash(), hash(), hash(), hash());
    }

  /*  public static String createSha(String string, String salt) {
        return new String(Hex.encodeHex(DigestUtils.sha(string + salt)));
    }

    public static String createUsername(String phoneNumber) {
        return createSha(phoneNumber, Config.CYFA_IO) + "@" + Config.CYFA_IO_URL;
    }*/

    public static String randomName() {
        NameUtils nameUtils = new NameBuilder()
                .setDelimiter("-")
                .setTokenLength(12)
                .setTokenHex(true)
                .build();

        return nameUtils.generate() + createHash();
    }

    public static HashMap<String, Object> createConversationParams() {

        HashMap<String, Object> hashMap = new HashMap<>();

        NameUtils nameUtils = new NameBuilder()
                .setDelimiter("-")
                .setTokenLength(6)
                .setTokenHex(true)
                .build();

        hashMap.put(Config.ID, nameUtils.generate() + String.format("%s-%s", hash(), hash()));

        //you can only get the name after calling generate()
        hashMap.put(Config.KEY_NAME, nameUtils.getName());
        return hashMap;
    }

    public static String createMediaId() {

        NameUtils nameUtils = new NameBuilder()
                .setDelimiter("-")
                .setTokenLength(6)
                .setTokenHex(true)
                .build();

        return nameUtils.generate() + hash();
    }

    public static String createQRCodeId() {

        NameUtils nameUtils = new NameBuilder()
                .setDelimiter("-")
                .setTokenLength(4)
                .setTokenHex(true)
                .build();

        return nameUtils.generate();
    }

    /**
     * connect to cloud code and bring back a randomly generated hash or token
     *
     * @param cloudCodeName      the cloud code function name
     * @param type               the type of result e.g "hash" || "token"
     * @param string             the string to generate the hash from
     * @param callbackForResults the results from the server
     */
    private static void generator(final String cloudCodeName, final String type, String string, final CallbackListener.callbackForResults callbackForResults) {
        HashMap<String, String> params = new HashMap<>();

        switch (type) {
            case Config.TOKEN:
                params.put(Config.KEY_USER_ID, User.getDeviceUserId());
                params.put(Config.KEY_USERNAME, User.getDeviceUser().getUsername());
                break;

            case Config.HASH:
                params.put(Config.STRING, string);
                break;
        }

        ParseCloud.callFunctionInBackground(cloudCodeName, params, new FunctionCallback<HashMap<String, Object>>() {
            @Override
            public void done(HashMap<String, Object> hashMap, ParseException e) {
                if (e == null) {
                    try {

                        int responseCode = (Integer) hashMap.get(NetworkUtils.KEY_RESPONSE_CODE);

                        if (responseCode == NetworkUtils.RESPONSE_OK) {

                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> data = (HashMap<String, Object>) hashMap.get(Config.DATA);

                            switch (type) {
                                case Config.TOKEN:
                                    if (data.get(Config.TOKEN) != null) {
                                        callbackForResults.success(data.get(Config.TOKEN));
                                    }
                                    break;

                                case Config.HASH:
                                    if (data.get(Config.HASH) != null) {
                                        callbackForResults.success(data.get(Config.HASH));
                                    }
                                    break;
                            }
                        } else {
                            callbackForResults.error(responseCode + Config.EMPTY_STRING);
                        }
                    } catch (Exception e1) {
                        callbackForResults.error(NetworkUtils.UNKNOWN_ERROR + Config.EMPTY_STRING);
                    }
                } else {
                    callbackForResults.error(e.getMessage());
                }
            }
        });
    }

    public static void generateHash(String string, CallbackListener.callbackForResults callbackForResults) {
        generator(Config.DEFINE_GENERATE_HASH, Config.HASH, string, callbackForResults);
    }

    public static void generateToken(CallbackListener.callbackForResults callbackForResults) {
        generator(Config.DEFINE_GENERATE_TOKEN, Config.TOKEN, Config.EMPTY_STRING, callbackForResults);
    }

    public static String encrypt(String Password, String plainText) throws GeneralSecurityException {
        return AESCrypt.encrypt(Password, plainText);
    }

    public static String decrypt(String Password, String cypherText) throws GeneralSecurityException {
        if (checkForEncode(cypherText)) {
            return AESCrypt.decrypt(Password, cypherText);
        }
        return cypherText;
    }

    public static Boolean checkForEncode(String string) {
        String pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(string);
        if (m.find()) {
            return true;
        }
        return false;
    }

    //takes message id
    //takes device user id
    //takes context user id
    public static String messagePassword(String conservationId, String deviceUserId, String contextUserId) {
        String[] strings = conservationId.split("-");
        return strings[2] + deviceUserId + contextUserId;
    }

    public static void setScreenCaptureAllowed(Activity activity, boolean allowed) {
        if (!allowed) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
    }

    public static String generatePushId() {
        long LAST_PUSH_TIME = 0;
        int[] LAST_RANDOM_CHAR_IDXS = new int[12];
        char[] ID = new char[20];
        long now = System.currentTimeMillis();

        boolean duplicateTime = now == LAST_PUSH_TIME;
        LAST_PUSH_TIME = now;

        if (!duplicateTime) {
            for (int i = 7; i >= 0; i--) {
                ID[i] = PUSH_CHARS.charAt((int) (now % 64));
                now = (long) Math.floor(now / 64);
            }
        }

        if (!duplicateTime) {
            for (int i = 0; i < 12; i++) {
                LAST_RANDOM_CHAR_IDXS[i] = (int) Math.floor(Math.random() * 64);
                ID[8 + i] = PUSH_CHARS.charAt(LAST_RANDOM_CHAR_IDXS[i]);
            }
        } else {
            int i = 11;
            for (; i >= 0 && LAST_RANDOM_CHAR_IDXS[i] == 63; i--) {
                LAST_RANDOM_CHAR_IDXS[i] = 0;
            }
            LAST_RANDOM_CHAR_IDXS[i]++;
        }
        for (int i = 0; i < 12; i++) {
            ID[8 + i] = PUSH_CHARS.charAt(LAST_RANDOM_CHAR_IDXS[i]);
        }
        return String.valueOf(ID);
    }
}
