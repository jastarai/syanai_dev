package jp.co.fukuya_k.system.form;

import java.util.Date;
import java.util.List;

import jp.co.fukuya_k.system.form.bean.ListDataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 画面上で一覧項目が存在する場合に使用するサブフォームです.
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ListFormBase<T extends ListDataBean> extends SubFormBase {
	/** */
	private static final long serialVersionUID = -4427852211889748101L;
	/* HTML(Thymeleaf/javascript)向けタグname属性 */
	public static final String FILTER_NAME_SINGLE = "filter";
	public static final String FILTER_NAME_STAGE = "stageFilter";
	public static final String UNFILTER_NAME = "unfilter";
	
	// 表示部タイトル用
	private String title;
	private String detailsName;
	// 全件数
	private int totalCount;
	// 明細レコードデータ
	private List<T> records;
	/* (必要な場合)親テーブルの更新日付 */
	private Date parentTableUpdDate;
}
