package jp.co.fukuya_k.system.form.components;

import lombok.Data;
/**
 * 
 * @author
 *
 */
@Data
public class CheckBox {

	private String labelText;
	private String name;
	private boolean value;
	// 選択チェックボックス用
	private boolean choose;
	// 削除チェックボックス用
	private boolean delete;
}
