package jp.co.fukuya_k.system.form;

import jp.co.fukuya_k.system.form.bean.ListDataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 画面上で表示のみの一覧(明細)項目が存在する場合に使用するサブフォームです.
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UIPagingListForm extends ListFormBase<ListDataBean> {
	/** serialVersionUID(Eclipse生成) */
	private static final long serialVersionUID = -8095465725439746340L;
	/** HTML(Thymeleaf/javascript)向けボタンコントロール定数(ボタン識別子) pagingTop 先頭ページ */
	public static final String PAGING_TOP  = "pagingTop";
	/** HTML(Thymeleaf/javascript)向けボタンコントロール定数(ボタン識別子) pagingPrev 前ページ */
	public static final String PAGING_PREV = "pagingPrev";
	/** HTML(Thymeleaf/javascript)向けボタンコントロール定数(ボタン識別子) pagingNext 次ページ */
	public static final String PAGING_NEXT = "pagingNext";
	/** HTML(Thymeleaf/javascript)向けボタンコントロール定数(ボタン識別子) pagingLast 最終ページ */
	public static final String PAGING_LAST = "pagingLast";
	/** ページング制御コマンド pagingKeep ページング状態の維持 */
	public static final String PAGING_KEEP = "pagingKeep";
	/**
	 * コンストラクタ
	 */
	public UIPagingListForm(){
		super();
	}
	
	/**
	 * コンストラクタ
	 * @param pagingCmd
	 */
	public UIPagingListForm(String pagingCmd){
		super();
		this.pagingCmd = pagingCmd;
	}
	
	// ページナビボタン活性制御
	/** topButtonDisabled 先頭ページ活性制御 */
	private boolean topButtonDisabled;
	/** topButtonDisabled 前ページ活性制御 */
	private boolean prevButtonDisabled;
	/** topButtonDisabled 次ページ活性制御 */
	private boolean nextButtonDisabled;
	/** topButtonDisabled 最終ページ活性制御 */
	private boolean lastButtonDisabled;
	// ページング(ページナビ)情報
	/** totalPageSize 総ページ数 */
	private int totalPageSize;
	/** currentPage 現在表示中のページ番号 */
	private int currentPage;
	/** pagenationPage ページング制御用のページ番号 */
	private int pagenationPage;
	/** rangeFrom 表示件数範囲(FROM) */
	private int rangeFrom;
	/** rangeTo 表示件数範囲(TO) */
	private int rangeTo;
	/** pagingCmd サーバリクエスト時のページングコマンド */
	private String pagingCmd;
	
	// ソート用(ソート項目)
	/** sorted ソート対象カラム@ソート方向 */
	private String sorted;
	/** nextSortKey サーバリクエスト時のソート対象カラム */
	private String nextSortKey;
}
