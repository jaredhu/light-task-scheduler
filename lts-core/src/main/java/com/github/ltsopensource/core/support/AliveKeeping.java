package com.github.ltsopensource.core.support;

import com.github.ltsopensource.core.logger.Logger;
import com.github.ltsopensource.core.logger.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 非守护线程, 保持节点存活
 *
 * @author Robert HG (254963746@qq.com) on 3/11/16.
 */
public class AliveKeeping {

    private static final Logger logger = LoggerFactory.getLogger(AliveKeeping.class);

    private static Timer timer;

    private static AtomicBoolean start = new AtomicBoolean(false);

    private AliveKeeping() {
        throw new IllegalStateException("Utility class");
    }

    public static void start() {
        if (start.compareAndSet(false, true)) {
            timer = new Timer("AliveKeepingService");
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (logger.isDebugEnabled()) {
                        logger.debug("I'm alive");
                    }
                }
            }, 1000 * 60 * 10, 1000 * 60 * 10);
        }
    }

    public static void stop() {
        if (start.compareAndSet(true, false) && timer != null) {
            timer.cancel();
        }
    }
}
