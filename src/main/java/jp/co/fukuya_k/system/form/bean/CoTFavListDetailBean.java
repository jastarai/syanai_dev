package jp.co.fukuya_k.system.form.bean;

import lombok.Data;

/**
 * お気に入り（検索結果）用
 */
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class CoTFavListDetailBean extends ListDataBean {
	
	private String userId;
	private String scrnId;
	private Integer favListNo;
	private Integer seq;
	private Integer columnNo;
	private Integer dispOrder;
	private String columnId;
	private String columnName;
	private String inputValue;
	
}
