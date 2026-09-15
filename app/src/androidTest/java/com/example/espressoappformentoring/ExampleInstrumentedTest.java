package com.example.espressoappformentoring;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicInteger;

/**
 Проверяет ввод текста, смену ориентации и восстановление исходного положения экрана.
 Также убеждается, что состояние интерфейса после поворота изменилось ожидаемо.
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumented";
    private static final String ENTERED_TEXT = "Espresso rotation test";
    private static final String INITIAL_BUTTON_TEXT = "Unedited";

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    @TestDescription("Введенный текст удаляется после восстановления поворота и ориентации.")
    public void enteredTextDisappearsAfterRotationAndOrientationIsRestored() {
        ActivityScenario<MainActivity> scenario = activityScenarioRule.getScenario();
        AtomicInteger initialOrientation = new AtomicInteger();
        scenario.onActivity(activity -> initialOrientation.set(
                activity.getResources().getConfiguration().orientation
        ));

        NavigationTestHelper.assertFirstFragmentDisplayed();
        NavigationTestHelper.openSecondFragment();

        Log.i(TAG, "Ввод текста во второй фрагмент поля ввода");
        onView(withId(R.id.textForButton)).perform(
                click(),
                typeText(ENTERED_TEXT),
                closeSoftKeyboard()
        );
        onView(withId(R.id.textForButton)).check(EspressoAssertions.withFailureMessage(
                "Поле ввода должно содержать введенный текст до поворота",
                matches(withText(ENTERED_TEXT))));

        Log.i(TAG, "Применение введенного текста с помощью кнопки \"Unedited\"");
        onView(withId(R.id.textChange)).check(EspressoAssertions.withFailureMessage(
                "Кнопка \"Unedited\" должна быть видна до нажатия",
                matches(isDisplayed())));
        onView(withId(R.id.textChange)).perform(click());
        onView(withId(R.id.textChange)).check(EspressoAssertions.withFailureMessage(
                "После нажатия кнопка должна отображать введенный текст",
                matches(withText(ENTERED_TEXT))));

        try {
            Log.i(TAG, "Поворот экрана");
            rotateScreen(scenario, initialOrientation.get());
            onView(withId(R.id.textChange)).check(EspressoAssertions.withFailureMessage(
                    "Измененный текст на кнопке должен исчезнуть после поворота",
                    matches(withText(INITIAL_BUTTON_TEXT))));
        } finally {
            Log.i(TAG, "Восстановление исходной ориентации экрана");
            setScreenOrientationAndWait(scenario, initialOrientation.get());
            onView(withId(R.id.textForButton)).check(EspressoAssertions.withFailureMessage(
                    "Вводимые данные должны оставаться видимыми после восстановления исходной ориентации",
                    matches(isDisplayed())));
            scenario.onActivity(activity -> assertEquals(
                    "После проведения теста необходимо восстановить ориентацию эмулятора.",
                    initialOrientation.get(),
                    activity.getResources().getConfiguration().orientation
            ));
        }
    }

    /**
     * Метод определяет противоположную ориентацию
     */
    private void rotateScreen(ActivityScenario<MainActivity> scenario, int currentOrientation) {
        int targetOrientation = currentOrientation == Configuration.ORIENTATION_LANDSCAPE
                ? Configuration.ORIENTATION_PORTRAIT
                : Configuration.ORIENTATION_LANDSCAPE;
        setScreenOrientationAndWait(scenario, targetOrientation);
    }

    /**
     * Метод устанавливает нужную ориентацию
     * и синхронизирует ожидание через IdlingResource.
     */
    private void setScreenOrientationAndWait(
            ActivityScenario<MainActivity> scenario,
            int orientation
    ) {
        OrientationIdlingResource orientationIdlingResource = new OrientationIdlingResource(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                orientation
        );
        IdlingRegistry.getInstance().register(orientationIdlingResource);

        int requestedOrientation = orientation == Configuration.ORIENTATION_LANDSCAPE
                ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        try {
            scenario.onActivity(activity ->
                    activity.setRequestedOrientation(requestedOrientation));
            onView(isRoot()).check(matches(isDisplayed()));
        } finally {
            IdlingRegistry.getInstance().unregister(orientationIdlingResource);
            orientationIdlingResource.unregister();
        }
    }
}
