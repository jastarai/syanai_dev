package jp.co.fukuya_k.system.auth;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import jp.co.fukuya_k.system.constants.MapperConstants;
import jp.jast.spframework.auth.AbstractUserDetailsServiceUtil;


@Component
public class AuthUserDetailsServiceUtil extends AbstractUserDetailsServiceUtil {

	@Override
	protected String getUserStatement() {
		return MapperConstants.M_AUTH_USER_LOGIN;
	}

	@Override
	protected String getRoleStatement() {
		return "";
	}

	@Override
	protected UserDetails createUserDetailsInstance(Object user, List<String> roleList) {
		AuthBean userBean = (AuthBean) user;
		AuthUserDetails userDetails = new AuthUserDetails(
				userBean.getUserId(), userBean.getPassword(), createAuthoritiyList(userBean.getAuthCode()));
		return userDetails;
	}
}
