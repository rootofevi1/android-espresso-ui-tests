package com.example.espressoappformentoring;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.test.espresso.IdlingResource;

/**
 Ждёт, пока экран действительно повернётся.
 Нужен, чтобы не использовать Thread.sleep().
 */
public final class OrientationIdlingResource implements IdlingResource, ComponentCallbacks {
    private final Context context;
    private final int expectedOrientation;
    private volatile ResourceCallback callback;

    public OrientationIdlingResource(Context context, int expectedOrientation) {
        this.context = context.getApplicationContext();
        this.expectedOrientation = expectedOrientation;
        this.context.registerComponentCallbacks(this);
    }

    @Override
    public String getName() {
        return OrientationIdlingResource.class.getSimpleName()
                + "[expectedOrientation=" + expectedOrientation + "]";
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = currentOrientation() == expectedOrientation;
        if (idle) {
            notifyIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
        if (currentOrientation() == expectedOrientation) {
            notifyIdle();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        if (newConfig.orientation == expectedOrientation) {
            notifyIdle();
        }
    }

    @Override
    public void onLowMemory() {
        // No resources need to be released while the test is running.
    }

    public void unregister() {
        context.unregisterComponentCallbacks(this);
    }

    private int currentOrientation() {
        return context.getResources().getConfiguration().orientation;
    }

    private void notifyIdle() {
        ResourceCallback currentCallback = callback;
        if (currentCallback != null) {
            currentCallback.onTransitionToIdle();
        }
    }
}
