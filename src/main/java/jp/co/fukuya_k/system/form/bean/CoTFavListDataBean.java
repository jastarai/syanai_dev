package jp.co.fukuya_k.system.form.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * お気に入り（検索結果情報）機能 お気に入り名称保持用.
 * @author partner-P00019
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class CoTFavListDataBean extends ListDataBean {
		
	private String userId;
	private String scrnId;
	private Integer favListNo;
	private String favListName;

}
