package com.github.doomsower;

import android.os.SystemClock;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RNStartupTimePackage implements ReactPackage {
    private static final long START_MARK = SystemClock.uptimeMillis();

    private boolean enforceSingleInvocation;

    public RNStartupTimePackage() {
        this(false);
    }

    /**
     * When used for collecting the performance analytics, redundant samples adulterate the data.
     * Redundant invocations may occur depending on the lifecycle event to which the sampling is attached,
     * e.g. bringing the app forward from the background. In such case, the app is not actually
     * started up but just shown, so the reported startup time may appear unrealistic. Such unrealistic
     * samples are going to adulterate your data, hence should be avoided.
     *
     * @param enforceSingleInvocation use {@code true} to prevent the redundant readings. In case of a redundant call,
     *                              a {@link IllegalStateException} is thrown which you can {@code catch} in your code.
     */
    public RNStartupTimePackage(boolean enforceSingleInvocation) {
        this.enforceSingleInvocation = enforceSingleInvocation;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(new RNStartupTimeModule(reactContext, START_MARK, enforceSingleInvocation));
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
