package jp.co.fukuya_k.system.auth;

import lombok.Data;

@Data
public class AuthBean {
	private String userId;
	private String password;
	private String authCode;
}
