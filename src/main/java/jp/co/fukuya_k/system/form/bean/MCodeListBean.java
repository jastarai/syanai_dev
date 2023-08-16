package jp.co.fukuya_k.system.form.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MCodeListBean extends ListDataBean{
	private String groupCode;
	private String groupName;
	private String code;
	private String codeName;
	private String viewItem;
	private String viewOrder;
	private String remarks;
}
