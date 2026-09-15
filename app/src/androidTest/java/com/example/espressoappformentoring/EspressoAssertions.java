package com.example.espressoappformentoring;

import androidx.test.espresso.ViewAssertion;

/**
 * Helper. Добавляет понятные сообщения к ошибкам Espresso-проверок.
 * Помогает легче понять причину падения теста.
 */
public final class EspressoAssertions {
    private EspressoAssertions() {
    }

    public static ViewAssertion withFailureMessage(String message, ViewAssertion assertion) {
        return (view, noViewFoundException) -> {
            try {
                assertion.check(view, noViewFoundException);
            } catch (AssertionError | RuntimeException error) {
                throw new AssertionError(message + ". Espresso details: " + error.getMessage(), error);
            }
        };
    }
}
