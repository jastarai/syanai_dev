package jp.co.fukuya_k.system.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

public class AuthUserDetails extends User {

	private static final long serialVersionUID = 1L;

	@Getter
	private String funcId;
	public void setFuncId(String funcId){
		this.funcId = funcId.replaceFirst("/", "");
	}

	public AuthUserDetails(String userId, String password, Collection<? extends GrantedAuthority> authorities) {
		super(userId, password, authorities);
	}
}
