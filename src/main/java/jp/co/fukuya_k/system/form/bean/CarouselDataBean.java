package jp.co.fukuya_k.system.form.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class CarouselDataBean extends ListDataBean {
	
	private String fileId;
	private String fileName;
	private String filePath;
}
