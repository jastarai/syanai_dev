package jp.co.fukuya_k.system.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
	
	/**
	 * ログレベル定数
	 */
	public static enum Loglevel {
		INFO,
		DEBUG,
		ERROR,
		WARN,
		TRACE,
	    ;

	}
	
    public static void logPrint(Loglevel loglevel, Class<?> clazz, String msg, Exception ex) {

    	Logger logger = LoggerFactory.getLogger(clazz);
    	
    	pringlogWithExeption(logger, loglevel, msg, ex);
    	
    	logger = null;

    }
    
    public static void logPrint(Loglevel loglevel, Class<?> clazz, String msg) {

    	Logger logger = LoggerFactory.getLogger(clazz);
    	
    	pringlog(logger, loglevel, msg);
    	
    	logger = null;

    }
    
    private static void pringlogWithExeption(Logger logger, Loglevel loglevel, String logmsg, Exception ex) {
    	
    	switch(loglevel) {
    	case INFO:
    		logger.info(logmsg, ex);
    		break;
    	case DEBUG:
    		logger.debug(logmsg, ex);
    		break;
    	case ERROR:
    		logger.error(logmsg, ex);
    		break;
    	case WARN:
    		logger.warn(logmsg, ex);
    		break;
    	case TRACE:
    		logger.trace(logmsg, ex);
    		break;
    	}

    }
    
    private static void pringlog(Logger logger, Loglevel loglevel, String logmsg) {
    	
    	switch(loglevel) {
    	case INFO:
    		logger.info(logmsg);
    		break;
    	case DEBUG:
    		logger.debug(logmsg);
    		break;
    	case ERROR:
    		logger.error(logmsg);
    		break;
    	case WARN:
    		logger.warn(logmsg);
    		break;
    	case TRACE:
    		logger.trace(logmsg);
    		break;
    	}

    }
	
}
