package jp.jast.spframework.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ログ出力抽象クラス.<br />
 * ※抽象クラスであるが、ログ出力処理は実装済み.<br />
 *   プロジェクト毎に異なるポイントカットの指定を具象クラスで行う.
 * @author
 *
 */
public abstract class AbsractAspectLogger {

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
	
	private static final Logger logger = LoggerFactory.getLogger(AbsractAspectLogger.class);

    public Object invoke(ProceedingJoinPoint joinPoint, Loglevel loglevel) throws Throwable {
        long methodStartTime = System.currentTimeMillis();

        Signature sig = joinPoint.getSignature();
        
        pringlog(loglevel, 
        		"***** Starting : " + 
    			sig.getDeclaringTypeName() + ":" + 
    			sig.getName() +
    			" *****");
        
        // 織り込み先(ジョインポイント)のメソッドを呼ぶ
        Object result = joinPoint.proceed();

        long execTime = System.currentTimeMillis() - methodStartTime;

        pringlog(loglevel, 
        		"***** Completed: " + 
    			sig.getDeclaringTypeName() + ":" + 
    			sig.getName() + ":" +
    			"completed in " + execTime + "ms" + 
    			" *****");

        // 織り込み先(ジョインポイント)のメソッドの戻り値を返却
        return result;
    }
    
    
    private void pringlog(Loglevel loglevel, String logmsg) {
    	
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