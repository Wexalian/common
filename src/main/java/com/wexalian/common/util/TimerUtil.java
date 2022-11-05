package com.wexalian.common.util;

import java.util.Timer;
import java.util.TimerTask;

public final class TimerUtil {
    private static final Timer TIMER = new Timer("TimerUtil");
    public static final String TIMER_SUFFIX = "Timer";
    
    public static void delay(String name, long delay, Runnable runnable) {
        delay(new Timer(name + TIMER_SUFFIX), delay, runnable);
    }
    
    public static void delay(long delay, Runnable runnable) {
        delay(TIMER, delay, runnable);
    }
    
    public static void delay(Timer timer, long delay, Runnable runnable) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        };
        TIMER.schedule(task, delay);
    }
}
