package jp.co.fukuya_k.system.form.components;

import lombok.Data;

/**
 * 
 * @author
 *
 */
@Data
public class FilterUList {

	private Object[] filterKeys;
	private String headerColumnNameJP;

	/**
	 * デフォルトコンストラクタ
	 */
	public FilterUList(){
	}
	/**
	 * コンストラクタ(ヘッダ日本語名称付き)
	 * @param headerColumnNameJP
	 */
	public FilterUList(String headerColumnNameJP){
		this.headerColumnNameJP = headerColumnNameJP;
	}
}
