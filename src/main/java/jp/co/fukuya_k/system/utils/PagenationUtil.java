package jp.co.fukuya_k.system.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jp.co.fukuya_k.system.constants.SystemConstants;
import jp.co.fukuya_k.system.form.UIPagingListForm;
import jp.co.fukuya_k.system.interfaces.FukuyaEntityInterface;
import jp.jast.spframework.dao.ParamBinder;

/**
 * ページング操作向けユーティリティクラスです.
 * @author
 *
 */
public class PagenationUtil {
	private static final Logger logger = LoggerFactory.getLogger(PagenationUtil.class);
	
	/**
	 * 
	 * @param listForm
	 * @return
	 */
	public static Pageable getPageable(UIPagingListForm listForm){
		return new PageRequest(listForm.getPagenationPage(), SystemConstants.MAX_LINE_LENGTH_BY_PAGE);
	}
	
	/**
	 * 
	 * @param pageable
	 * @param listForm
	 * @return
	 */
	synchronized public static<P extends FukuyaEntityInterface> ParamBinder<P> getPageableOnBinder(ParamBinder<P> binder, Pageable pageable, UIPagingListForm listForm){
		
		binder = CheckUtil.isNull(binder) ? new ParamBinder<P>() : binder;
		switch (listForm.getPagingCmd()) {
		case UIPagingListForm.PAGING_PREV: {
			logger.debug("PAGING_PREV");
			binder.setPageable(pageable.hasPrevious() ? pageable.previousOrFirst() : pageable.first());
			// 前回ソート情報をクリア
//			listForm.setSorted(null);
			break;
		}
		case UIPagingListForm.PAGING_NEXT: {
			logger.debug("PAGING_NEXT");
			binder.setPageable(pageable.next());
			// 前回ソート情報をクリア
//			listForm.setSorted(null);
			break;
		}
		case UIPagingListForm.PAGING_LAST: {
			logger.debug("PAGING_LAST");
			int lastPage = (int)Math.ceil((double)listForm.getTotalCount() / SystemConstants.MAX_LINE_LENGTH_BY_PAGE);
			// PageRequestが扱うページ開始位置は0始まり
			Pageable last = new PageRequest(
					  lastPage - SystemConstants.MIN_PAGENATION_NUMBER
					, SystemConstants.MAX_LINE_LENGTH_BY_PAGE);
			binder.setPageable(last);		
			// 前回ソート情報をクリア
//			listForm.setSorted(null);
			break;
		}
		case UIPagingListForm.PAGING_TOP: {
			logger.debug("PAGING_TOP");
			binder.setPageable(pageable.first());
			// 前回ソート情報をクリア
//			listForm.setSorted(null);
			break;
		}
		case UIPagingListForm.PAGING_KEEP:
		default: {
			logger.debug("PAGING_KEEP");
			binder.setPageable(pageable);
			break;
		}
		}
		return binder;
	}
	/**
	 * 画面上の先頭ページ/前ページ/次ページ/最終ページボタンに対して<br>
	 * ボタン活性制御を設定したListFormを返す.
	 * @param listForm
	 * @param binder
	 * @return
	 */
	public static UIPagingListForm getPagingButtonCtrl(UIPagingListForm listForm, ParamBinder<?> binder){
		//前ページ・先頭ページボタン制御
		if(binder.getPageable().hasPrevious()){
			// ボタンを有効化(disabled == false)
			listForm.setTopButtonDisabled(false);
			listForm.setPrevButtonDisabled(false);
		}
		else {
			// ボタンを無効化(disabled == true)
			listForm.setTopButtonDisabled(true);
			listForm.setPrevButtonDisabled(true);
		}
		//次ページ・最終ページボタン制御
		int lastPage = (int)Math.ceil((double)listForm.getTotalCount() / SystemConstants.MAX_LINE_LENGTH_BY_PAGE);
		if(binder.getPageable().next().getOffset()
				>= (lastPage * SystemConstants.MAX_LINE_LENGTH_BY_PAGE)){
			listForm.setNextButtonDisabled(true);
			listForm.setLastButtonDisabled(true);
		}
		else {
			listForm.setNextButtonDisabled(false);
			listForm.setLastButtonDisabled(false);
		}
		return listForm;
	}
	/**
	 * リクエスト時に使用するページング制御用のページ番号を算出して返します.
	 * @param currentPageNum
	 * @return
	 */
	public static int getControlledPagenationPage(int currentPageNum){
		return currentPageNum >= SystemConstants.MIN_PAGENATION_NUMBER
				? currentPageNum - SystemConstants.MIN_PAGENATION_NUMBER
				: SystemConstants.MIN_PAGENATION_NUMBER -1;
	}
	/**
	 * 
	 * @param totalPageSize
	 * @param pageable
	 * @return
	 */
	public static int getCurrentPageNumber(int totalPageSize,Pageable pageable){
		return totalPageSize > 0
				? pageable.getPageNumber() + SystemConstants.MIN_PAGENATION_NUMBER
				: pageable.getPageNumber();
	}
	
	
	/**
	 * 
	 * @param dataList
	 * @param pageable
	 * @return
	 */
	synchronized public static List<FukuyaEntityInterface> slicePagingListData(List<FukuyaEntityInterface> dataList, ParamBinder<?> binder){
		// インスタンス参照から切り離す
		List<FukuyaEntityInterface> inputEntityList = new ArrayList<FukuyaEntityInterface>(dataList);
		Pageable pageable = binder.getPageable();
		// データ数のページ境界越えから前ページ境界へ戻った際の切り出し位置(START)調整
		pageable = (pageable.getOffset() == inputEntityList.size()) ? pageable.previousOrFirst() : pageable;
		binder.setPageable(pageable);
		int rangeFrom = pageable.getOffset();
		int rangeTo = rangeFrom + pageable.getPageSize();
		// Listの切り出し位置(END)を合わせる
		rangeTo = rangeTo > inputEntityList.size() ? inputEntityList.size() : rangeTo;
		// 1ページあたりの明細リストをフォームBeanへセット
		return inputEntityList.subList(rangeFrom, rangeTo);
	}
}
