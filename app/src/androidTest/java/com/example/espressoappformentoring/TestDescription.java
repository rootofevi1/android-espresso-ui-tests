package com.example.espressoappformentoring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Пользовательская аннотация,
 * содержащая читаемое назначение теста.
 * JUnit 4 сам по себе не имеет аналога JUnit 5 @DisplayName,
 * поэтому создана собственная аннотация. Используется над каждым тестовым методом.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestDescription {
    String value();
}
