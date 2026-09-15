package com.example.espressoappformentoring;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.util.Log;

/**
 Содержит общие методы переходов между экранами и их проверок.
 Убирает дублирование кода в тестах.
 */
public final class NavigationTestHelper {
    private static final String TAG = "EspressoNavigation";

    private NavigationTestHelper() {
    }

    public static void openSecondFragment() {
        Log.i(TAG, "Открытие второго фрагмента");
        onView(withId(R.id.button_first)).perform(click());
        assertSecondFragmentDisplayed();
    }

    public static void openSecondActivity() {
        Log.i(TAG, "Открытие второй активности");
        onView(withId(R.id.switchActivityButton))
                .check(EspressoAssertions.withFailureMessage(
                        "Кнопка переключения режимов должна быть видна до начала навигации",
                        matches(isDisplayed())))
                .perform(click());
    }

    public static void assertFirstFragmentDisplayed() {
        onView(withId(R.id.textview_first)).check(EspressoAssertions.withFailureMessage(
                "Первый фрагмент должен быть виден",
                matches(withText(R.string.hello_first_fragment))));
        onView(withId(R.id.button_first)).check(EspressoAssertions.withFailureMessage(
                "Кнопка «Next button» должна быть видна в первом фрагменте",
                matches(isDisplayed())));
    }

    public static void assertSecondFragmentDisplayed() {
        onView(withId(R.id.textForButton)).check(EspressoAssertions.withFailureMessage(
                "Поле ввода текста должно быть видно во втором фрагменте",
                matches(isDisplayed())));
        onView(withId(R.id.button_second)).check(EspressoAssertions.withFailureMessage(
                "Кнопка «Previous button» должна быть видна во втором фрагменте",
                matches(withText(R.string.previous))));
    }

    public static void assertSecondActivityDisplayed() {
        onView(withId(R.id.secondActText)).check(EspressoAssertions.withFailureMessage(
                "Вторая страница должна содержать ожидаемый текст.",
                matches(allOf(
                        withText(R.string.hello_second_activity),
                        isDisplayed()
                ))));
    }
}
