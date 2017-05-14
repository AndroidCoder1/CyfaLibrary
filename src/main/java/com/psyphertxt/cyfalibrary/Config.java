package com.psyphertxt.cyfalibrary;

import android.content.Context;
import android.content.res.Resources;

import com.facebook.stetho.Stetho;
import com.parse.Parse;
import com.parse.ParseObject;
import com.psyphertxt.cyfalibrary.backend.parse.User;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;

/**
 * This class contains application configuration and setup methods and constants
 */
public class Config {

    public static final Boolean IS_PROTO = false;
    public static final String HAS_CAMERA_PERMISSION = "has_camera_permission";
    public static final int ITEMS_REMAINING_TO_PAGINATION = 1;
    public static final String KEY_GROUP_PROFILE = "groupProfile";
    public static final String KEY_GROUP_MEMBERS = "groupMembers";
    public static final String KEY_LIVE_GROUP_MEMBERS = "liveGroupMembers";
    public static final String CYFA_IO = "-cyfa-io";
    public static final String CYFA_IO_URL = "cyfa.io";
    public static final String PRIVACY_POLICY = "http://www.cyfa.io/privacy-policy/";
    //theme color names
    public static final String THEME_NAME_AWESOME = "Awesome";
    public static final String THEME_NAME_VIOLET_RED = "Violet Red";
    public static final String THEME_NAME_SAFETY_ORANGE = "Safety Orange";
    public static final String THEME_NAME_CITRINE = "Citrine";
    public static final String THEME_NAME_IRIS_BLUE = "Iris Blue";
    public static final String THEME_NAME_BRANDEIS_BLUE = "Brandeis Blue";
    public static final String THEME_NAME_VIOLET = "Violet (Purple)";
    public static final String THEME_NAME_LIMA = "Lima";
    public static final String THEME_NAME_DEFAULT = "Base";
    public static final String KEY_COLOR_PRIMARY = "colorPrimary";
    public static final String KEY_COLOR_ACCENT = "colorAccent";
    //application constants
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_MESSAGE_ID = "messageId";
    public static final String KEY_CONVERSATION_ID = "conversationId";
    public static final String KEY_CREATED_DATE = "createdDate";
    public static final String KEY_UPDATED_DATE = "updatedDate";
    public static final String KEY_PUSH_ID = "pushId";
    public static final String KEY_GROUP_ID = "groupId";
    public static final String KEY_IS_BLOCKED = "isBlocked";
    public static final String KEY_IS_ONLINE_STATUS = "isOnlineStatus";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_ANSWER = "answer";
    public static final String KEY_CONTACTS = "contacts";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TIMER = "timer";
    public static final String KEY_USER = "user";
    public static final String KEY_PROFILE = "profile";
    public static final String KEY_NOTIFICATION = "notification";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_BADGE_ID = "badgeId";
    public static final String KEY_NETWORK = "network";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_CALLING_CODE = "callingCode";
    public static final String KEY_THEME = "theme";
    public static final String KEY_IMAGE_NAME = "imageName";
    public static final String KEY_PROFILE_SETUP = "profileSetup";
    public static final String KEY_PROFILE_IMAGE = "profileImage";
    public static final String KEY_PASSCODE = "passcode";
    public static final String KEY_DISPLAY_NAME = "displayName";
    public static final String KEY_TYPE = "type";
    public static final String KEY_MEMBERS = "members";
    public static final String KEY_OWNER_ID = "ownerId";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_CONTENT_TYPE = "contentType";
    public static final String KEY_GROUP_NAME = "groupName";
    public static final String KEY_NAME = "name";
    public static final String KEY_DEFAULT = "default";
    public static final String KEY_TITLE_ME = "Me";
    public static final String KEY_TITLE = "title";
    public static final String KEY_STAGE = "stage";
    public static final String KEY_SESSION_TOKEN = "sessionToken";
    public static final String KEY_SESSION = "session";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CODE = "code";
    public static final String KEY_IS_EXISTING_USER = "isExistingUser";
    public static final String KEY_TEXT = "text";
    public static final String KEY_LAST_MESSAGE = "lastMessage";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_DELIVERED_AT = "deliveredAt";
    public static final String KEY_READ_AT = "readAt";
    public static final String KEY_STATUS = "status";
    public static final String KEY_STATUS_MESSAGE = "statusMessage";
    public static final String KEY_ONLINE = "online";
    public static final String KEY_DEVICE_NAME = "deviceName";
    public static final String KEY_COUNT = "count";
    public static final String KEY_ACTIVE = "active";
    public static final String KEY_ACCEPTED = "accepted";
    public static final String KEY_TYPING = "typing";
    public static final String KEY_RECENT = "recent";
    public static final String KEY_LAST_SEEN = "lastSeen";
    public static final String KEY_LINK = "link";
    public static final String KEY_CAPTION = "caption";
    public static final String KEY_REFERENCE = "reference";
    public static final String KEY_EVENT = "event";
    public static final String KEY_DELETED = "deleted";
    public static final String KEY_LIVE_TYPING = "live_typing";
    public static final String KEY_HIDE_REGULAR = "hide_regular";
    public static final String KEY_SHOW_TIMER = "show_timer";
    public static final String KEY_TIMER_INDEX = "timer_index";
    public static final String KEY_TIMER_VALUE = "timer_value";
    public static final String KEY_POINTS = "points";
    public static final String KEY_MESSAGE_COUNT = "messageCount";
    public static final String KEY_BADGE_COUNT = "badgeCount";
    public static final String KEY_BADGES = "badges";
    public static final String TYPE_IMAGE = "image";
    public static final String KEY_NOTIFICATION_BROADCAST_INTENT = "notification_broadcast_intent";
    public static final String TYPE_VIDEO = "video";
    public static final String GROUP_PUBLIC = "Public";
    public static final String GROUP_PRIVATE = "Private";
    public static final String KEY = "key";
    public static final String ID = "id";
    public static final String VALUE = "value";
    public static final String NUMBER = "number";
    public static final String STRING = "string";
    public static final String TOKEN = "token";
    public static final String DATA = "data";
    public static final String HASH = "hash";
    public static final String REWARDS = "rewards";
    //convenient constants
    public static final int NUMBER_LENGTH = 8;
    public static final int NEGATIVE_ONE = -1;
    public static final String TIMER_DEFAULT = "20";
    public static final String FORWARD_SLASH = "/";
    public static final String DELETED_MESSAGE = "Message was deleted";
    public static final int CODE_LENGTH = 4;
    public static final int DISPLAY_NAME_MIN_LENGTH = 4;
    public static final int DISPLAY_NAME_MAX_LENGTH = 14;
    public static final int STATUS_TEXT_MAX_LENGTH = 70;
    public static final int DELETE_TEXT_MAX_LENGTH = 150;
    public static final int MESSAGE_LIMIT = 100;
    public static final int ZERO_LENGTH = 0;
    public static final int NUMBER_ONE = 1;
    public static final int DEFAULT_ANIMATION_DURATION = 1000;
    public static final int NUMBER_ZERO = 0;
    public static final int SECURITY_QUESTION_LENGTH = 15;
    public static final int SECURITY_ANSWER_LENGTH = 4;
    public static final int ONE_HUNDRED = 100;
    public static final String EMPTY_STRING = "";
    public static final String ELLIPSIS = "...";
    public static final String LAST_SEEN_STRING = "LAST SEEN ";
    public static final String[] EMPTY_ARRAY = new String[0];
    public static final int DEFAULT_MODE = 0;
    public static final int EDIT_MODE = 1;
    public static final int DELETE_MODE = 2;
    //intent ids
    public static final int THEME_COLOR_CHANGE = 501;
    public static final int STATUS_MESSAGE_CHANGE = 502;
    public static final int REQUEST_INVITE = 503;
    //real time database references
    public static final String REF_MESSAGES = "ancient-pine-3010-messages";
    public static final String REF_CONVERSATIONS = "autumn-firefly-4096-conversations";
    public static final String REF_PROFILES = "nameless-smoke-3728-profiles";

    //firebase constants
    //all firebase root references should be an auto generated name from
    //this URL http://kevinmlawson.com/herokuname/
    //so the expected format is [generated-name]-[firebase-reference-name]
    //example young-resonance-4834-games
    public static final String REF_SESSION = "damp-cloud-5318-session";
    public static final String REF_GROUPS = "ancient-pond-8420-groups";
    public static final String REF_STATUS = "crimson-haze-7489-status";
    public static final String REF_ACTIVITY = "solitary-river-9756-activity";
    public static final String REF_POLICY = "bitter-leaf-1890-policy";
    public static final String REF_USERS = "throbbing-surf-8484-users";
    public static final String REF_PRESENCE = "hidden-bush-8192-presence";
    public static final String REF_SPEC = "lingering-lake-6221-spec";
    public static final String REF_CHILD_MEMBERS = "members";
    public static final String REF_CHILD_REWARDS = "rewards";
    public static final String REF_CHILD_SESSION = "session";
    public static final String REF_CHILD_CONNECTIONS = "connections";
    public static final String REF_CONNECTION_STATUS = ".info/connected";
    public static final String REF_SERVER_TIME_OFFSET = ".info/serverTimeOffset";
    public static final String DB_NAME_CONTEXT_USERS = "CONTEXT_USERS";
    //parse cloud code function names
    public static final String DEFINE_USER_VALIDATION = "userValidation";
    public static final String DEFINE_NOTIFY_USER = "notifyUser";
    public static final String DEFINE_GET_BADGES = "getBadges";
    public static final String DEFINE_GET_BADGE = "getBadge";
    public static final String DEFINE_REWARD = "reward";
    public static final String DEFINE_GET_CONTACTS = "getContacts";
    public static final String DEFINE_GET_CONTACT = "getContact";
    public static final String DEFINE_GENERATE_HASH = "generateHash";
    public static final String DEFINE_GENERATE_TOKEN = "generateToken";
    public static final String KEY_USER_PROFILE = "userProfile";
    public static final String KEY_ID = "id";
    public static final String KEY_SIGN_UP = "key_sign_up";
    public static Boolean IS_PASSCODE = false;
    public static boolean THEME_CHANGED = false;
    public static int MODE = 0;

    /**
     * this method should be called in a class which extends Application (entry point of main
     * application)
     * the reason being it contains most of the startup methods needed
     * for the application to work well
     *
     * @param context the application context
     */
    public static void setup(Context context) {

        Realm.init(context);

        //set up subclasses for parse
        ParseObject.registerSubclass(User.class);
        // ParseObject.registerSubclass(ContextUser.class);

        //get application resources
        Resources resources = context.getResources();

        //initialize parse server
        Parse.initialize(new Parse.Configuration.Builder(context)
                .applicationId(resources.getString(0))
                .clientKey(resources.getString(0))
                .server(resources.getString(0))
                .build());
    }

    public static void debug(Context context) {

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

    public static final class NotificationType {
        public static final int TABBED = 0;
        public static final int NOTIFICATION = 1;
        public static final int PUSH_NOTIFICATION = 2;
        public static final int STATUS_NOTIFICATION = 3;
    }

}
