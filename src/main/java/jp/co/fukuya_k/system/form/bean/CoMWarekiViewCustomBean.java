package jp.co.fukuya_k.system.form.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CoMWarekiViewCustomBean extends CoMWarekiViewBean{

	private String gengou;
	private String startDate;
	private String endDate;
	private String view;

}
