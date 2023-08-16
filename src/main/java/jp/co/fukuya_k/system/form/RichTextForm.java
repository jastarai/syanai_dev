package jp.co.fukuya_k.system.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 画面上でリッチテキスト項目が存在する場合に使用するサブフォームです.
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RichTextForm extends SubFormBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6135588817188399161L;
	/* HTML(Thymeleaf/javascript)向けボタンコントロール定数 */
	public static final String BTN_EDIT  = "btnEdit";
	public static final String BTN_SAVE  = "btnSave";
	private String richTextData;
}
