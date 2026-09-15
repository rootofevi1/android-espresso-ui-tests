package com.example.espressoappformentoring;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.util.Log;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Проверяет открытие меню с пунктом Settings. Затем проверяет, что меню закрывается
 * после клика вне него.
 */
@RunWith(AndroidJUnit4.class)
public class ToolbarTest {
    private static final String TAG = "ToolbarTest";

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    @TestDescription("Дополнительное меню отображает «Settings» и закрывается после щелчка вне его области.")
    public void overflowMenuAppearsAndDisappearsAfterOutsideClick() {
        int[] outsidePopupPoint = new int[2];
        activityScenarioRule.getScenario().onActivity(activity -> {
            int[] location = new int[2];
            activity.findViewById(R.id.toolbar).getLocationOnScreen(location);
            outsidePopupPoint[0] =
                    location[0] + activity.findViewById(R.id.toolbar).getWidth() / 4;
            outsidePopupPoint[1] =
                    location[1] + activity.findViewById(R.id.toolbar).getHeight() / 2;
        });

        Log.i(TAG, "Открытие дополнительного меню панели инструментов");
        openActionBarOverflowOrOptionsMenu(
                InstrumentationRegistry.getInstrumentation().getTargetContext()
        );
        onView(withText(R.string.action_settings)).check(EspressoAssertions.withFailureMessage(
                "В дополнительном меню должен быть видимый пункт «Settings»",
                matches(isDisplayed())));

        Log.i(TAG, "Нажатие вне всплывающего меню");
        ScreenTestHelper.clickAt(outsidePopupPoint[0], outsidePopupPoint[1]);
        onView(withText(R.string.action_settings)).check(EspressoAssertions.withFailureMessage(
                "Всплывающее окно «Settings» должно исчезнуть после щелчка вне его контекста.",
                doesNotExist()));
    }
}
