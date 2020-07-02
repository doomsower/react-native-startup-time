package com.github.doomsower;

import android.app.Activity;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = "RNStartupTime")
public class RNStartupTimeModule extends ReactContextBaseJavaModule {
    public static final String NAME = "RNStartupTime";

    private final long startMark;
    private final boolean enforceSingleInvocation;

    private boolean alreadyInvoked;

    public RNStartupTimeModule(ReactApplicationContext reactContext, long startMark, boolean enforceSingleInvocation) {
        super(reactContext);
        this.startMark = startMark;
        this.enforceSingleInvocation = enforceSingleInvocation;

        alreadyInvoked = false;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void getTimeSinceStartup(Promise promise) {
        try {
            if (enforceSingleInvocation && alreadyInvoked) {
                throw new IllegalStateException("Redundant invocation of `getTimeSinceStartup`. " +
                        "To prevent adulteration of your analytics data, this request was aborted");
            }

            alreadyInvoked = true;

            int ms = (int) (SystemClock.uptimeMillis() - startMark);
            Activity activity = getCurrentActivity();
            if (activity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.reportFullyDrawn();
            }
            promise.resolve(ms);

        } catch (Exception e) {
            promise.reject(e);
        }
    }
}
