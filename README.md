# CyfaLibrary
Cyfa library project is a wrapper library containing logics to sign up user using the 2-factor authentication mode(usual authentication + SMS authentication) and other various android Utility codes.

## Installation
CyfaLibrary is installed by adding the following dependency to your app’s build.gradle file: 

```groovy
dependencies {
    compile ('com.psyphertxt:cyfalibrary:1.1.4@aar'){
        transitive = true
    }
}
```

## Usage
### Basic

To begin using CyfaLibrary, have your `Application` and add code below to your `onCreate` method:

```java
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        
        //Initialise Cyfa library
        Cyfa.init(App.this);
    }
}
```


To validate a user
```java

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Method to validate user details
        SignUp signUp = new SignUp();
        signUp.setNumber("000000");
        SignUpUtils.validateUser(MainActivity.this, signUp, new CallbackListener.onCompletionListener() {
                    @Override
                    public void before(Context context) {
                        dialog = ProgressDialog.show(context, "Hey", "Ok I agree");
                    }
        
                    @Override
                    public void success(SignUp signUp) {
                        dialog.dismiss();
                        Log.d(TAG, signUp.toString());
                    }
                    
                    @Override
                    public void error(String error) {
                        Log.e(TAG, error);
                        dialog.dismiss();
                    }
               });
        }



    //Method to validate code after user has entered his/her Phone number
    private void validateCode(String code){
            SignUpUtils.validateCode(MainActivity.this, code, new CallbackListener.onUserCompletionListener() {
                @Override
                public void before(Context context) {
    
                }
    
                @Override
                public void success(User user) {
    
                }
    
                @Override
                public void error(String error) {
    
                }
            });
        }
    }
```
### AndroidManifest.xml
Add `<meta>` tag to `<application>` tag specifying your parse url, client key and application id
```xml
<application
        android:name=".App"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity
            android:name=".MainActivity"
            android:label="@string/app_name"
            android:screenOrientation="portrait">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <meta-data android:name="parse_url" android:value="@string/PARSE_APPLICATION_PARSE_URL" />
        <meta-data android:name="parse_client_id" android:value="@string/PARSE_APPLICATION_CLIENT_KEY" />
        <meta-data android:name="parse_app_id" android:value="@string/PARSE_APPLICATION_ID" />
    </application>
```

