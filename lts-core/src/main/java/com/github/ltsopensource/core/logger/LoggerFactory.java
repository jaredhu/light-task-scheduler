package com.github.ltsopensource.core.logger;


import com.github.ltsopensource.core.logger.jcl.JclLoggerAdapter;
import com.github.ltsopensource.core.logger.jdk.JdkLoggerAdapter;
import com.github.ltsopensource.core.logger.log4j.Log4jLoggerAdapter;
import com.github.ltsopensource.core.logger.logback.LogbackLoggerAdapter;
import com.github.ltsopensource.core.logger.slf4j.Slf4jLoggerAdapter;
import com.github.ltsopensource.core.logger.support.FailsafeLogger;
import com.github.ltsopensource.core.spi.ServiceLoader;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 日志输出器工厂
 */
public class LoggerFactory {

    private LoggerFactory() {
    }

    private static volatile LoggerAdapter logger_ADAPTER;

    private static final ConcurrentMap<String, FailsafeLogger> loggerS = new ConcurrentHashMap<String, FailsafeLogger>();

    // 查找常用的日志框架
    static {
        String logger = System.getProperty("lts.logger");
        if ("logback".equals(logger)) {
            setLoggerAdapter(new LogbackLoggerAdapter());
        } else if ("slf4j".equals(logger)) {
            setLoggerAdapter(new Slf4jLoggerAdapter());
        } else if ("jcl".equals(logger)) {
            setLoggerAdapter(new JclLoggerAdapter());
        } else if ("log4j".equals(logger)) {
            setLoggerAdapter(new Log4jLoggerAdapter());
        } else if ("jdk".equals(logger)) {
            setLoggerAdapter(new JdkLoggerAdapter());
        } else {
            try {
                setLoggerAdapter(new LogbackLoggerAdapter());
            } catch (Throwable e0) {
                try {
                    setLoggerAdapter(new Slf4jLoggerAdapter());
                } catch (Throwable e1) {
                    try {
                        setLoggerAdapter(new Log4jLoggerAdapter());
                    } catch (Throwable e2) {
                        try {
                            setLoggerAdapter(new JclLoggerAdapter());
                        } catch (Throwable e3) {
                            setLoggerAdapter(new JdkLoggerAdapter());
                        }
                    }
                }
            }
        }
    }

    public static void setLoggerAdapter(String loggerAdapter) {
        if (loggerAdapter != null && loggerAdapter.length() > 0) {
            setLoggerAdapter(ServiceLoader.load(LoggerAdapter.class, loggerAdapter));
        }
    }

    /**
     * 设置日志输出器供给器
     *
     * @param loggerAdapter 日志输出器供给器
     */
    public static void setLoggerAdapter(LoggerAdapter loggerAdapter) {
        if (loggerAdapter != null) {
            Logger logger = loggerAdapter.getLogger(LoggerFactory.class.getName());
            logger.info("using logger: " + loggerAdapter.getClass().getName());
            LoggerFactory.logger_ADAPTER = loggerAdapter;
            for (Map.Entry<String, FailsafeLogger> entry : loggerS.entrySet()) {
                entry.getValue().setLogger(logger_ADAPTER.getLogger(entry.getKey()));
            }
        }
    }

    /**
     * 获取日志输出器
     *
     * @param key 分类键
     * @return 日志输出器, 后验条件: 不返回null.
     */
    public static Logger getLogger(Class<?> key) {
        FailsafeLogger logger = loggerS.get(key.getName());
        if (logger == null) {
            loggerS.putIfAbsent(key.getName(), new FailsafeLogger(logger_ADAPTER.getLogger(key)));
            logger = loggerS.get(key.getName());
        }
        return logger;
    }

    /**
     * 获取日志输出器
     *
     * @param key 分类键
     * @return 日志输出器, 后验条件: 不返回null.
     */
    public static Logger getLogger(String key) {
        FailsafeLogger logger = loggerS.get(key);
        if (logger == null) {
            loggerS.putIfAbsent(key, new FailsafeLogger(logger_ADAPTER.getLogger(key)));
            logger = loggerS.get(key);
        }
        return logger;
    }

    /**
     * 动态设置输出日志级别
     *
     * @param level 日志级别
     */
    public static void setLevel(Level level) {
        logger_ADAPTER.setLevel(level);
    }

    /**
     * 获取日志级别
     *
     * @return 日志级别
     */
    public static Level getLevel() {
        return logger_ADAPTER.getLevel();
    }

    /**
     * 获取日志文件
     *
     * @return 日志文件
     */
    public static File getFile() {
        return logger_ADAPTER.getFile();
    }

}
