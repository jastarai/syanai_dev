package jp.co.fukuya_k.system.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import jp.jast.spframework.auth.AbstractLogoutHandler;

@Component
public class UserLogoutHandler extends AbstractLogoutHandler {

	private static final Logger logger = LoggerFactory.getLogger(UserLogoutHandler.class);

	/**
	 * ログアウト処理を拡張します.
	 */
	@Override
	public void logoutExt(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		logger.debug("ログアウトしました。");
	}
}
