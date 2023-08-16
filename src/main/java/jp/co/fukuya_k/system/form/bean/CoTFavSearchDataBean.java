package jp.co.fukuya_k.system.form.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class CoTFavSearchDataBean extends ListDataBean {
	
	private String userId;
	private String scrnId;
	private Integer favSearchNo;
	private String favSearchName;
}
