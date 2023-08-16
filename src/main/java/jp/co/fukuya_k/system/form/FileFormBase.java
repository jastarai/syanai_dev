package jp.co.fukuya_k.system.form;

import java.io.Serializable;
import java.util.List;

import jp.co.fukuya_k.system.form.bean.FileDataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 画面上でファイルのアップロード・ダウンロード項目が存在する場合に<BR>
 * 使用するサブフォームです.
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FileFormBase extends FileDataBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4129333530337247988L;
	/* (非 Javadoc) HTML(Thymeleaf/javascript)向けボタンコントロール定数 */
	/** ファイルアップロード・ダウンロードボタン */
	public static final String BTN_FILE_UPLOAD   = "btnFileUp";
	public static final String BTN_FILE_DOWNLOAD = "btnFileDown";
	public static final String BTN_FILE_CLEAR    = "btnFileClear";
	// フォームのID(javascript用)
	private String formId;
	// 画面ID
	private String gamenId;
	
	// INFO(情報)メッセージリスト
	private List<String> infoMsgs;
	// ERROR(エラー)メッセージリスト
	private List<String> errMsgs;
}
