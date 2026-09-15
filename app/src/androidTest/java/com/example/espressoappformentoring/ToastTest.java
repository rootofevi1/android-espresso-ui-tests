package com.example.espressoappformentoring;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Проверяет, что после нажатия на кнопку с письмом появляется Toast с правильным текстом.
 */
@SuppressWarnings("deprecation")
@RunWith(AndroidJUnit4.class)
public class ToastTest {
    private static final String TAG = "ToastTest";

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    @TestDescription("При нажатии кнопки FAB в электронном письме отображается ожидаемый " +
            "текст всплывающего уведомления (Toast).")
    public void emailButtonDisplaysExpectedToast() {
        Log.i(TAG, "Нажатие плавающей кнопки действия электронной почты");
        onView(withId(R.id.fab)).perform(click());

        onView(withText(R.string.email_toast_text))
                .inRoot(new ToastMatcher())
                .check(EspressoAssertions.withFailureMessage(
                        "Кнопка отправки электронного письма должна отображать " +
                                "ожидаемое всплывающее сообщение (Toast).",
                        matches(isDisplayed())));
    }
}
