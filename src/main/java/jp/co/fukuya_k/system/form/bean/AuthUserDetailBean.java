package jp.co.fukuya_k.system.form.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AuthUserDetailBean extends MAuthUserBean {

	private String approval;
	private String authName;
}
