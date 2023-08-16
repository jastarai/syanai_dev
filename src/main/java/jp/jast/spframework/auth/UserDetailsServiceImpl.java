package jp.jast.spframework.auth;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired AbstractUserDetailsServiceUtil userDetailsUtil;
	@Autowired SqlSessionTemplate sqlSessionTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return userDetailsUtil.createUserDetailsInstance(getUser(username), getRoles(username));
	}

	private Object getUser(String username) {
		if (!userDetailsUtil.getUserStatement().isEmpty()) {
			return sqlSessionTemplate.selectOne(userDetailsUtil.getUserStatement(), username);
		}
		return null;
	}

	private List<String> getRoles(String username) {
		if (!userDetailsUtil.getRoleStatement().isEmpty()) {
			return sqlSessionTemplate.selectList(userDetailsUtil.getRoleStatement(), username);
		}
		return null;
	}
}
