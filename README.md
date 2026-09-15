# Android UI-тесты на Espresso
Android UI automation with Espresso, Java 11 and JUnit 4.

[![Build Android app and test APKs](https://github.com/rootofevi1/android-espresso-ui-tests/actions/workflows/build.yml/badge.svg)](https://github.com/rootofevi1/android-espresso-ui-tests/actions/workflows/build.yml)

Пять инструментальных сценариев для учебного Android-приложения. Проект демонстрирует работу с Activity и Fragment, пользовательским вводом, поворотом экрана, Toast и меню Toolbar.

## Сценарии

| Класс | Проверка |
|---|---|
| ExampleInstrumentedTest | Ввод текста, нажатие UNEDITED, проверка изменённой надписи, поворот и явное восстановление ориентации |
| IntentTest | Переход из MainActivity в SecondActivity, проверка Intent и отображаемого текста |
| TransitionTest | Навигация между экранами, включая системную кнопку «Назад» |
| ToastTest | Нажатие кнопки письма и проверка текста в Toast-окне |
| ToolbarTest | Открытие Settings в меню и закрытие по нажатию вне меню |

Исходные сценарии: [app/src/androidTest/java/com/example/espressoappformentoring](app/src/androidTest/java/com/example/espressoappformentoring).

## Стек и устройство проекта

- Тесты: Java 11, JUnit 4, AndroidJUnit4.
- Espresso Core/Intents 3.5.1, AndroidX Test.
- ActivityScenarioRule, ActivityTestRule, пользовательский ToastMatcher.
- OrientationIdlingResource ожидает нужную конфигурацию экрана.
- Приложение: Kotlin 1.8.21, AndroidX Navigation, ViewBinding.
- Gradle 7.5, Android Gradle Plugin 7.4.2, compileSdk/targetSdk 33, minSdk 24.

Тестовые вспомогательные классы находятся рядом со сценариями в androidTest и не входят в APK приложения.

## Подготовка

1. Установите JDK 11 и Android Studio.
2. В настройках Gradle JDK выберите JDK 11.
3. Установите Android SDK Platform 33 и создайте телефонный эмулятор Android 13 / API 33.
4. Запустите эмулятор. Команда adb devices должна показывать устройство со статусом device.
5. Настройте ANDROID_HOME или создайте локальный local.properties с sdk.dir. Файл local.properties не публикуется.

## Запуск

```bash
git clone https://github.com/rootofevi1/android-espresso-ui-tests.git
cd android-espresso-ui-tests
./gradlew :app:assembleDebug :app:assembleDebugAndroidTest
./gradlew :app:connectedDebugAndroidTest
```

Windows: используйте .\gradlew.bat.

Один сценарий:

```powershell
.\gradlew.bat "-Pandroid.testInstrumentationRunnerArguments.class=com.example.espressoappformentoring.ToastTest" :app:connectedDebugAndroidTest
```

HTML-отчёт: app/build/reports/androidTests/connected/index.html.

## Особенности окружения

- UTP отключён свойством android.experimental.androidTest.useUnifiedTestPlatform=false: в локальном окружении AGP 7.4.2 возникала ошибка запуска gRPC-слушателя результатов.
- После connectedDebugAndroidTest запускается installDebug. Это возвращает приложение после очистки тестового запуска, но не гарантирует сохранение ярлыка на домашнем экране.
- Используется кастомный Toast с контрастным фоном. ToastMatcher проверяет окно TYPE_TOAST; поведение на других версиях Android требует отдельной проверки.
- CI собирает APK приложения и тестов. Эмуляторные тесты запускаются локально; успешная сборка APK не означает их прохождения.

## Происхождение

Тесты и доработки выполнены Александром на основе учебного Android-приложения. Исходный каркас приложения на Kotlin не выдаётся за разработанный с нуля. В репозиторий не включены задания, материалы курса и история учебного репозитория.

Александр · Junior QA/AQA Engineer · [Email](mailto:a@samoylov-qa.ru) · [Telegram](https://t.me/samoylov_av)
