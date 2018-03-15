package com.github.ltsopensource.core.logger.logback;

import com.github.ltsopensource.core.logger.Level;
import com.github.ltsopensource.core.logger.Logger;
import com.github.ltsopensource.core.logger.LoggerAdapter;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LogbackLoggerAdapter implements LoggerAdapter {

    public LogbackLoggerAdapter() {
        try {
            Class.forName("ch.qos.logback.classic.Logger");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("ch.qos.logback.classic.Logger not found");
        }
    }

    @Override
    public Logger getLogger(String key) {
        return new LogbackLogger((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(key));
    }

    @Override
    public Logger getLogger(Class<?> key) {
        return new LogbackLogger((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(key));
    }

    private Level level;

    private File file;

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }

}

