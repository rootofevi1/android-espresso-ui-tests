package com.example.espressoappformentoring;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Тест навигации между всеми экранами.
 */
@SuppressWarnings("deprecation")
@RunWith(AndroidJUnit4.class)
public class TransitionTest {
    private static final String TAG = "TransitionTest";

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    /**
     * Проверяет все основные маршруты:
     * FirstFragment → SecondFragment;
     * SecondFragment → FirstFragment кнопкой;
     * SecondFragment → FirstFragment системной Back;
     * FirstFragment → SecondActivity;
     * SecondActivity → MainActivity системной Back.
     */
    @Test
    @TestDescription("Доступ ко всем экранам приложения осуществляется через элементы " +
            "управления пользовательского интерфейса и кнопку «Назад» в системе.")
    public void navigationWorksBetweenAllApplicationScreens() {
        NavigationTestHelper.assertFirstFragmentDisplayed();

        NavigationTestHelper.openSecondFragment();
        Log.i(TAG, "Возвращаемся к первому фрагменту с кнопкой «Previous button»");
        onView(withId(R.id.button_second)).perform(click());
        NavigationTestHelper.assertFirstFragmentDisplayed();

        NavigationTestHelper.openSecondFragment();
        Log.i(TAG, "Возвращаясь к первому фрагменту с помощью system Back");
        pressBack();
        NavigationTestHelper.assertFirstFragmentDisplayed();

        NavigationTestHelper.openSecondActivity();
        NavigationTestHelper.assertSecondActivityDisplayed();
        ActivityTestHelper.assertCurrentActivity(
                "Перед нажатием кнопки system Back в системе необходимо возобновить выполнение SecondActivity.",
                SecondActivity.class
        );

        Log.i(TAG, "Возвращение из second activity с system Back");
        pressBack();
        ActivityTestHelper.assertCurrentActivity(
                "System Back должен вернуть из SecondActivity в MainActivity",
                MainActivity.class
        );
        NavigationTestHelper.assertFirstFragmentDisplayed();
    }
}
