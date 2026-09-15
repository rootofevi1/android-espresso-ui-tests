package com.example.espressoappformentoring;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import android.util.Log;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 Проверяет переход из MainActivity во SecondActivity.
 Также проверяет, что на втором экране отображается нужный текст.
 */
@SuppressWarnings("deprecation")
@RunWith(AndroidJUnit4.class)
public class IntentTest {
    private static final String TAG = "IntentTest";

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initializeIntents() {
        Intents.init();
    }

    @After
    public void releaseIntents() {
        Intents.release();
    }

    @Test
    @TestDescription("Основное окно запускает второе окно с ожидаемым текстом")
    public void secondActivityIsOpenedAndDisplaysExpectedText() {
        Log.i(TAG, "Проверка того, что основная активность возобновилась в исходном состоянии");
        ActivityTestHelper.assertCurrentActivity(
                "Основная деятельность должна быть начальной деятельностью",
                MainActivity.class
        );
        NavigationTestHelper.assertFirstFragmentDisplayed();

        NavigationTestHelper.openSecondActivity();
        intended(hasComponent(SecondActivity.class.getName()));
        ActivityTestHelper.assertCurrentActivity(
                "После нажатия кнопки «Переключиться» необходимо открыть второе окно",
                SecondActivity.class
        );
        NavigationTestHelper.assertSecondActivityDisplayed();
    }
}
