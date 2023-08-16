package jp.co.fukuya_k.system.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import jp.jast.spframework.auth.AbstractLogoutSuccessHandler;

@Component
public class UserLogoutSuccessHandler extends AbstractLogoutSuccessHandler {
	/**
	 * ログアウト時の後処理を実装します.
	 */
	@Override
	public void onLogoutSuccessExt(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
	}
}
