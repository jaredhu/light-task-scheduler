package com.github.ltsopensource.core.logger.jcl;

import com.github.ltsopensource.core.logger.Logger;
import com.github.ltsopensource.core.logger.support.AbstractLogger;
import org.apache.commons.logging.Log;

import java.io.Serializable;

/**
 * 适配CommonsLogging，依赖于commons-logging.jar
 */
public class JclLogger extends AbstractLogger implements Logger, Serializable {

	private static final long serialVersionUID = 1L;

	private final Log logger;

	public JclLogger(Log logger) {
		this.logger = logger;
	}

	@Override
    public void trace(String msg) {
        logger.trace(msg);
    }

    @Override
    public void trace(Throwable e) {
        logger.trace(e);
    }

    @Override
    public void trace(String msg, Throwable e) {
        logger.trace(msg, e);
    }

	@Override
    public void debug(String msg) {
		logger.debug(msg);
	}

    @Override
    public void debug(Throwable e) {
        logger.debug(e);
    }

	@Override
    public void debug(String msg, Throwable e) {
		logger.debug(msg, e);
	}

	@Override
    public void info(String msg) {
		logger.info(msg);
	}

    @Override
    public void info(Throwable e) {
        logger.info(e);
    }

	@Override
    public void info(String msg, Throwable e) {
		logger.info(msg, e);
	}

	@Override
    public void warn(String msg) {
		logger.warn(msg);
	}

    @Override
    public void warn(Throwable e) {
        logger.warn(e);
    }

	@Override
    public void warn(String msg, Throwable e) {
		logger.warn(msg, e);
	}

	@Override
    public void error(String msg) {
		logger.error(msg);
	}

    @Override
    public void error(Throwable e) {
        logger.error(e);
    }

	@Override
    public void error(String msg, Throwable e) {
		logger.error(msg, e);
	}

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

	@Override
    public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
    public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
    public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	@Override
    public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

}
