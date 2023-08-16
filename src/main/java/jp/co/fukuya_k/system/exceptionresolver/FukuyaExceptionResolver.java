package jp.co.fukuya_k.system.exceptionresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import jp.co.fukuya_k.system.logger.LoggerUtil;

@Component
public class FukuyaExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

//		if (ex instanceof Exception) {
//			// ......................
//		}

		// セッション除去
		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
		}

		// エラーログ出力
		LoggerUtil.logPrint(LoggerUtil.Loglevel.INFO, FukuyaExceptionResolver.class, ex.getMessage(), ex);

		// システムエラー画面表示
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("errmsg", ex.getMessage());
		modelAndView.setViewName("/system/handled_error");
		
		return modelAndView;
		
	}
	
}
