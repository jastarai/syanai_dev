package jp.jast.spframework.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import jp.jast.spframework.config.SpFrameworkProperties;

public abstract class AbstractLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Autowired
	SpFrameworkProperties properties;

	abstract protected void onLogoutSuccessExt(HttpServletRequest request, HttpServletResponse response, Authentication authentication);

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		setDefaultTargetUrl(properties.getSecurity().getLogoutSuccessUrl());
		super.onLogoutSuccess(request, response, authentication);
		onLogoutSuccessExt(request, response, authentication);
	}
}
