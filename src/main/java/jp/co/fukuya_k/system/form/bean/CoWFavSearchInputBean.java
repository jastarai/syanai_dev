package jp.co.fukuya_k.system.form.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class CoWFavSearchInputBean extends ListDataBean {
	
	private String userId;
	private String scrnId;
	private Integer columnNo;
	private String inputValue;
	
	private String inputDispName;
}
