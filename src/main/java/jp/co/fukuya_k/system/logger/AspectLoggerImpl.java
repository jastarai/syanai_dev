package jp.co.fukuya_k.system.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import jp.jast.spframework.logger.AbsractAspectLogger;

/**
 * ログ出力実装クラス.
 * @author
 *
 */
@Aspect
@Component
public class AspectLoggerImpl extends AbsractAspectLogger{

	/**
	 * AbsractAspectLoggerを継承し、開始～終了ログを出力する.<br />
	 * ※ポイントカットを@Aroundにより指定する必要があるが、この指定はプロジェクト毎に<br />
	 *   異なるため、ログ出力の実装自体はAbsractAspectLoggerで行われているが、<br />
	 *   継承することにより、具象クラス側で@Aroundを指定する.<br />
	 * 
	 * @param point
	 * @return Object
	 * @throws Throwable
	 */
	@Around("execution(* jp.co.fukuya_k..*Controller.*(..)) || execution(* jp.co.fukuya_k..*Service.*(..))")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
    	
		return super.invoke(joinPoint, Loglevel.INFO);
    	
    }
	
}