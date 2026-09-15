package com.example.espressoappformentoring;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.view.InputDevice;
import android.view.MotionEvent;

import androidx.test.platform.app.InstrumentationRegistry;

/**
 * Helper выполняет системный тап
 по заданным координатам экрана.
 Он нужен для ToolbarTest, потому
 что требуется нажать вне popup menu.
 */
public final class ScreenTestHelper {
    private ScreenTestHelper() {
    }

    public static void clickAt(int x, int y) {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        long downTime = SystemClock.uptimeMillis();
        MotionEvent down = MotionEvent.obtain(
                downTime,
                downTime,
                MotionEvent.ACTION_DOWN,
                x,
                y,
                0
        );
        down.setSource(InputDevice.SOURCE_TOUCHSCREEN);

        MotionEvent up = MotionEvent.obtain(
                downTime,
                SystemClock.uptimeMillis(),
                MotionEvent.ACTION_UP,
                x,
                y,
                0
        );
        up.setSource(InputDevice.SOURCE_TOUCHSCREEN);

        try {
            instrumentation.sendPointerSync(down);
            instrumentation.sendPointerSync(up);
            instrumentation.waitForIdleSync();
        } finally {
            down.recycle();
            up.recycle();
        }
    }
}
