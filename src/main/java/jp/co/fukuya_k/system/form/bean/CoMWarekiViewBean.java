package jp.co.fukuya_k.system.form.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CoMWarekiViewBean extends ListDataBean{
	private String groupCd;
	private String code;
	private String gengouCode;
	private String warekiYear;
	private String codeName;
	private int viewOrder;
}
