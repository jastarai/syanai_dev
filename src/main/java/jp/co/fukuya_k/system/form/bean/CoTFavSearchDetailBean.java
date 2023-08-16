package jp.co.fukuya_k.system.form.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class CoTFavSearchDetailBean extends ListDataBean {
	
	private String userId;
	private String scrnId;
	private Integer favSearchNo;
	private Integer seq;
	private Integer columnNo;
	private String inputName;
	private String inputDispName;
	private String inputValue;
	
	private String grCd;
	private String dispVal;
	
}
