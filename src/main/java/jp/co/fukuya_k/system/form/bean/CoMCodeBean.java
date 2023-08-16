package jp.co.fukuya_k.system.form.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CoMCodeBean extends ListDataBean {

	private String grCd;
	private String cd;
	private String grNm;
	private String cdNm;
	private String dispVal;
	private Integer dispSeq;
	private String biko;
	
}
