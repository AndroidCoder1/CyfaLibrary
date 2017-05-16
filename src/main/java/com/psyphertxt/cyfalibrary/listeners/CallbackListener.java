package com.psyphertxt.cyfalibrary.listeners;

import android.content.Context;

import com.psyphertxt.cyfalibrary.backend.parse.User;
import com.psyphertxt.cyfalibrary.models.SignUp;

import java.io.File;

/**
 * A simple Callback interface to aid in network calls
 * when using third party libraries
 */

public class CallbackListener {

    public interface callbackForResults {

        void success(Object result);

        void error(String error);

    }

    public interface onFileCompletionListener {

        void before(Context context);

        void success(File file);

        void error(String error);

    }

    public interface onSignUpCompletionListener {

        void before(Context context);

        void success(SignUp signUp);

        void error(String error);

    }

    public interface onUserCompletionListener {

        void before(Context context);

        void success(User user);

        void error(String error);

    }

    public interface onCompletionListener {

        void before(Context context);

        void success();

        void error(String error);

    }

    public interface callback {

        void success();

        void error(String error);

    }

    public interface completion {

        void done();

    }

    public interface formatDate {

        void today();

        void otherDays();

    }

    public interface onBeforeLoad {

        void before(Context context);

    }

    public interface onCompleteLoad {

        void complete(Object result);

    }

    public interface onCompletion {

        void complete(Object result);

    }

    public interface onSuccess {

        void success();

    }

    public interface onError {

        void error(String e);

    }

    public interface timer {

        void done();

        void running(long counter);

    }

    public interface task {

        void start();

    }
}
