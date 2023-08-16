package jp.jast.spframework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

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
		ModelAndView modelAndView = new ModelAndView();
		logger.error("user: " + ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()
				+ "\turl: " + request.getRequestURL());
		logger.error(ex.getMessage(), ex);
		
		modelAndView.setViewName("/system/error");
		return modelAndView;
	}
}
