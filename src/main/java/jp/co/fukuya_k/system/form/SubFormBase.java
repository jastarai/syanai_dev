package jp.co.fukuya_k.system.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
/**
 * 画面内を構成する各表示部分(サブフォーム)クラスが継承するスーパークラスです.
 * @author
 *
 */
@Data
public class SubFormBase implements Serializable {


	/** serialVersionUID(Eclipse生成) */
	private static final long serialVersionUID = -1410785010299428955L;
	/** フォームID(javascript用) */
	private String formId;
	/** 画面ID */
	private String gamenId;

	@NotBlank
	private String insUserId;
	@NotBlank
	private Date insDate;
	private String updUserId;
	private Date updDate;
	
	/** infoMsgs INFO(情報)メッセージリスト */
	private List<String> infoMsgs;
	// 
	/** errMsgs ERROR(エラー)メッセージリスト */
	private List<String> errMsgs;
}
