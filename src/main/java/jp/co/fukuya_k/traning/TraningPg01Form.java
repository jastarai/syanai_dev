package jp.co.fukuya_k.traning;

import java.util.List;
import jp.co.fukuya_k.traning.TraningPg01FormEmp;
import jp.jast.spframework.form.AbstractMainForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 社員検索画面フォームクラス
 * 
 * @author 日本システム技術株式会社
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TraningPg01Form extends AbstractMainForm {

	/** 社員番号 */
	private String empno;

	/** 社員一覧情報 */
	private List<TraningPg01FormEmp> listRow;

}
