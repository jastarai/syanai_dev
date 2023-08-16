package jp.jast.spframework.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

public abstract class AbstractLogoutHandler extends SecurityContextLogoutHandler {

	abstract protected void logoutExt(HttpServletRequest request, HttpServletResponse response, Authentication authentication);
	@Override
	public 	void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		logoutExt(request, response, authentication);
	}
}
