# CyfaLibrary [ ![Download](https://api.bintray.com/packages/psyphertxt/Cyfa/CyfaLibrary/images/download.svg?version=v1.1.5) ](https://bintray.com/psyphertxt/Cyfa/CyfaLibrary/v1.1.5/link)
Cyfa library project is a wrapper library for Android, for signing up users using the 2-factor Authentication mode(usual authentication + SMS verification) and other various Android Utility codes.
## Installation
CyfaLibrary is installed by adding the following dependency to your app’s build.gradle file: 

```groovy
dependencies {
    compile ('com.psyphertxt:cyfalibrary:1.1.5@aar'){
        transitive = true
    }
}
```

## Usage
### Basic

To begin using CyfaLibrary, extend the  `Application` class and add code below to your `onCreate` method:

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

### App's build.gradle

In cases where you have different parse keys for different flavors, add this to the app's `build.gradle`
```groovy
android {
    compileSdkVersion 25
    buildToolsVersion "25.0.2"
    
    productFlavors {

        dev {
            resValue 'string', 'PARSE_APPLICATION_ID', '"YOUR_APP_ID"'
            resValue 'string', 'PARSE_APPLICATION_CLIENT_KEY', '"YOUR_CLIENT_KEY"'
            resValue 'string', 'PARSE_APPLICATION_PARSE_URL', '"YOUR_PARSE_URL"'
        }

        beta {
            resValue 'string', 'PARSE_APPLICATION_ID', '"YOUR_APP_ID"'
                        resValue 'string', 'PARSE_APPLICATION_CLIENT_KEY', '"YOUR_CLIENT_KEY"'
                        resValue 'string', 'PARSE_APPLICATION_PARSE_URL', '"YOUR_PARSE_URL"'
        }

        prod {
            resValue 'string', 'PARSE_APPLICATION_ID', '"YOUR_APP_ID"'
            resValue 'string', 'PARSE_APPLICATION_CLIENT_KEY', '"YOUR_CLIENT_KEY"'
            resValue 'string', 'PARSE_APPLICATION_PARSE_URL', '"YOUR_PARSE_URL"'
        }
    }
}
```

