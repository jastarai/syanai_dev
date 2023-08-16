package jp.co.fukuya_k.system.form;

import java.io.Serializable;
import java.util.Date;

import jp.jast.spframework.form.AbstractMainForm;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 
 * 1画面に1つの単位で存在する、各業務画面の親フォームが継承するスーパークラスです.
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MainFormBase extends AbstractMainForm implements Serializable {

	/** serialVersionUID(Eclipse生成) */
	private static final long serialVersionUID = 7799040566458514591L;
	/** HTML(Thymeleaf/javascript)向けボタンコントロール定数(ボタン識別子) btnRegist 登録ボタン */
	public static final String BTN_REGIST = "btnRegist";
	/** HTML(Thymeleaf/javascript)向けボタンコントロール定数(ボタン識別子) btnUpdate 更新ボタン */
	public static final String BTN_UPDATE = "btnUpdate";
	/** HTML(Thymeleaf/javascript)向けボタンコントロール定数(ボタン識別子) btnDelete 削除ボタン */
	public static final String BTN_DELETE = "btnDelete";
	/** HTML(Thymeleaf/javascript)向けボタンコントロール定数(ボタン識別子) btnLogout ログアウトボタン */
	public static final String BTN_LOGOUT = "btnLogout";
	/** HTML(Thymeleaf/javascript)向けSUBMIT送信時コントロール定数  regist 登録 */
	public static final String SUBMIT_COMMAND_REGIST = "regist";
	/** HTML(Thymeleaf/javascript)向けSUBMIT送信時コントロール定数  paging ページング */
	public static final String SUBMIT_COMMAND_PAGING = "paging";
	/** HTML(Thymeleaf/javascript)向けSUBMIT送信時コントロール定数  sort ソート */
	public static final String SUBMIT_COMMAND_SORT = "sort";

	/* 画面ID*/
	private String gamenId;
	// 機能ID
	private String kinouId;
	/* ユーザID */
	private String userId;
	/* 更新日 */
	private Date updateDate;
	
	/* submit時のコマンド (登録("regist")など)*/
	private String onSubmitCmd;

	/** ブラウザの初期スクロール位置(x) scroll_x */
	private int scroll_x;
	/** ブラウザの初期スクロール位置(y) scroll_y */
	private int scroll_y;
}
