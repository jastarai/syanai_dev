package jp.co.fukuya_k.system.form.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CoMFavListColumnNameBean extends ListDataBean {

	private String scrnId;
	private Integer columnNo;
	private String columnId;
	private String columnName;
	private Integer columnOrder;
	
}
