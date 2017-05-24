package com.psyphertxt.cyfalibrary.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.psyphertxt.cyfalibrary.CyfaConfig;
import com.psyphertxt.cyfalibrary.listeners.CallbackListener;
import com.psyphertxt.cyfalibrary.listeners.ContactsLoaderListener;
import com.psyphertxt.cyfalibrary.models.Country;
import com.psyphertxt.cyfalibrary.models.PhoneContact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ContactUtils {

    //list of phone contacts
    public static List<PhoneContact> DEVICE_CONTACTS = null;

    //TODO check for valid phone numbers
    // @Deprecated
    public static void loadContacts(final Context context, final String callingCode, final ContactsLoaderListener contactLoaderListener) {

        final List<PhoneContact> phoneContacts = new ArrayList<>();
        final Uri PHONE_CONTACTS_URI = ContactsContract.Contacts.CONTENT_URI;
        final Uri PHONE_NUMBERS_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //    final Uri CONTACTS_EMAIL_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        //  final String EMAIL_ID_COLUMN_NAME = ContactsContract.CommonDataKinds.Email.CONTACT_ID;

        final String CONTACT_ID_COLUMN_NAME = ContactsContract.Contacts._ID;
        final String CONTACT_DISPLAYNAME_COLUMN_NAME = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME;
        final String CONTACT_HAS_PHONENUMBER_COLUMN_NAME = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    contactLoaderListener.onBeforeLoadingContacts();

                    CountryUtils.init(context);

                    //set a projection for the query
                    final String[] PROJECTION = new String[]{
                            ContactsContract.CommonDataKinds.Phone._ID,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
                    };

                    //set the sort order for the query
                    final String SORT_ORDER = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";

                    Cursor cursor = context.getContentResolver().query(PHONE_CONTACTS_URI, PROJECTION, null, null, SORT_ORDER);
                    if (cursor.getCount() > 0) {
                        String id, displayName;
                        //imageUri = null;
                        int phoneNumbersCount = 0;
                        while (cursor.moveToNext()) {
                            phoneNumbersCount = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CONTACT_HAS_PHONENUMBER_COLUMN_NAME)));
                            if (phoneNumbersCount > 0) {

                                PhoneContact.Builder builder = new PhoneContact.Builder();
                                id = cursor.getString(cursor.getColumnIndex(CONTACT_ID_COLUMN_NAME));
                                displayName = cursor.getString(cursor.getColumnIndex(CONTACT_DISPLAYNAME_COLUMN_NAME));
                                //  imageUri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));

                                //  builder.setId(id).setDisplayName(displayName).setThumbnailUri(imageUri);
                                builder.setId(id).setDisplayName(displayName);

                                Cursor numbersCursor = context.getContentResolver().query(PHONE_NUMBERS_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + "?", new String[]{id}, null);

                                if (numbersCursor != null && numbersCursor.getCount() > 0) {
                                    String phoneNumber = null;
                                    while (numbersCursor.moveToNext()) {
                                        phoneNumber = numbersCursor.getString(numbersCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                        String number = ContactUtils.normalizeNumber(phoneNumber);
                                        String[] formatted = ContactUtils.splitPhoneNumber(number, callingCode);
                                        builder.addPhoneNumber(formatted[0] + formatted[1]);
                                    }
                                    numbersCursor.close();
                                }

                              /*  Cursor emailsCursor = context.getContentResolver().query(CONTACTS_EMAIL_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + "?", new String[]{id}, null);
                                if (emailsCursor != null && emailsCursor.getCount() > 0) {
                                    String emailAddress = null;
                                    while (emailsCursor.moveToNext()) {
                                        emailAddress = emailsCursor.getString(emailsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                        Log.i("EMAIL : ",emailAddress);
                                        builder.addEmailAddress(emailAddress);
                                    }
                                    emailsCursor.close();
                                }*/
                                phoneContacts.add(builder.build());
                            }

                        }
                        cursor.close();

                        contactLoaderListener.onCompletedLoadingContacts(phoneContacts);
                    }

                } catch (Exception e) {
                    contactLoaderListener.onErrorLoadingContacts(e.getMessage());
                }
            }
        }).start();
    }


    public static void getServerContacts(HashMap<String, Object> payload, final CallbackListener.callbackForResults callbackForResults) {
        ParseCloud.callFunctionInBackground(CyfaConfig.DEFINE_GET_CONTACTS, payload, new FunctionCallback<HashMap<String, Object>>() {

            @Override
            public void done(HashMap<String, Object> hashMap, ParseException e) {
                if (e == null) {
                    callbackForResults.success(hashMap);

                } else {
                    callbackForResults.error(e.getMessage());
                }
            }
        });
    }

    public static String getGoogleUsername(Context context) {

        AccountManager manager = AccountManager.get(context);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<>();

        for (Account account : accounts) {
            // account.name as an email address only for certain account.type values.
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");

            if (parts.length > 1)
                return parts[0];
        }
        return null;
    }

    public static String[] splitPhoneNumber(String number, String defaultCallingCode) {
        if (number.startsWith("0")) {
            number = Long.valueOf(number).toString();
        }
        if (!number.startsWith("+")) {
            number = "+" + number;
        }

        for (Country c : CountryUtils.getCountriesSortedByCallingCode()) {
            if (number.startsWith(c.getCallingCode()) && (number.length() - c.getCallingCode().length()) >= 8) {
                return new String[]{c.getCallingCode(), number.substring(c.getCallingCode().length())};
            }
        }
        return new String[]{defaultCallingCode, number.substring(1)};
    }

    public static String normalizeNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return CyfaConfig.EMPTY_STRING;
        }

        StringBuilder sb = new StringBuilder();
        int len = phoneNumber.length();
        for (int i = 0; i < len; i++) {
            char c = phoneNumber.charAt(i);
            // Character.digit() supports ASCII and Unicode digits (fullwidth, Arabic-Indic, etc.)
            int digit = Character.digit(c, 10);
            if (digit != -1) {
                sb.append(digit);
            } else if (i == 0 && c == '+') {
                sb.append(c);
            } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                return normalizeNumber(PhoneNumberUtils.convertKeypadLettersToDigits(phoneNumber));
            }
        }
        return sb.toString();
    }
}
