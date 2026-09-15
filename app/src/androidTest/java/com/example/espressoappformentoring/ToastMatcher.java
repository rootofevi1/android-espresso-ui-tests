package com.example.espressoappformentoring;

import android.os.IBinder;
import android.view.WindowManager;

import androidx.test.espresso.Root;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 Помогает Espresso найти именно Toast, а не обычный элемент Activity.
 Используется в ToastTest.
 */
public final class ToastMatcher extends TypeSafeMatcher<Root> {
    @Override
    protected boolean matchesSafely(Root root) {
        int windowType = root.getWindowLayoutParams().get().type;
        if (windowType != WindowManager.LayoutParams.TYPE_TOAST) {
            return false;
        }

        IBinder windowToken = root.getDecorView().getWindowToken();
        IBinder applicationToken = root.getDecorView().getApplicationWindowToken();
        return windowToken == applicationToken;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("это всплывающее окно");
    }
}
