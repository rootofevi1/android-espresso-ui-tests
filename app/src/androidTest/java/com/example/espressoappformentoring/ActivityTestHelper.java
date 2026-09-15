package com.example.espressoappformentoring;

import static org.junit.Assert.assertEquals;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Тестовый helper для определения текущей Activity.
 * Он позволяет проверить, какая Activity сейчас отображается пользователю.
 * Используется в IntentTest и TransitionTest.
 */
public final class ActivityTestHelper {
    private ActivityTestHelper() {
    }

    public static void assertCurrentActivity(
            String failureMessage,
            Class<? extends Activity> expectedActivityClass
    ) {
        AtomicReference<Activity> resumedActivity = new AtomicReference<>();
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Collection<Activity> activities = ActivityLifecycleMonitorRegistry.getInstance()
                    .getActivitiesInStage(Stage.RESUMED);
            if (!activities.isEmpty()) {
                resumedActivity.set(activities.iterator().next());
            }
        });

        Activity activity = resumedActivity.get();
        assertEquals(
                failureMessage,
                expectedActivityClass,
                activity == null ? null : activity.getClass()
        );
    }
}
