package jp.jast.spframework.auth;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.stereotype.Component;

@Component
public class MyWebExpressionVoter extends WebExpressionVoter {
	@Override
	public int vote(Authentication authentication, FilterInvocation fi, Collection<ConfigAttribute> attributes) {
		System.out.println("KKKKKKKOOOOOOOOOOO");
		return super.vote(authentication, fi, attributes);
	}
}
