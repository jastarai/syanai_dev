package jp.co.fukuya_k.system.form.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MAuthUserBean extends ListDataBean {
	private String userId;
	private String password;
	private String authCd;
	private String updFuncId;
}
