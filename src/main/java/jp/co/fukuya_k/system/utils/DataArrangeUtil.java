package jp.co.fukuya_k.system.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import jp.co.fukuya_k.system.constants.SystemConstants;
import jp.co.fukuya_k.system.form.UIPagingListForm;
import jp.co.fukuya_k.system.form.bean.ListDataBean;
import jp.co.fukuya_k.system.form.components.CheckBox;
import jp.co.fukuya_k.system.interfaces.FileUsableInterface;
import jp.co.fukuya_k.system.interfaces.FukuyaEntityInterface;
import jp.jast.spframework.form.FileForm;
/**
 * データ操作向けユーティリティクラスです.
 * 
 * @author
 *
 */
public class DataArrangeUtil {
	/**
	 * 
	 * @param propertyName
	 * @param bean
	 * @param order
	 * @return
	 */
	public static EntityComparator<FukuyaEntityInterface> getComparator(String propertyName, FukuyaEntityInterface bean, String order){
		return new EntityComparator<FukuyaEntityInterface>(propertyName, bean, order);
	}
	/**
	 * 
	 * @param dataFrom
	 * @param beanTo
	 * @return
	 */
	public static FileUsableInterface ifExistsPropertyCopyForFile(FukuyaEntityInterface dataFrom, FileUsableInterface beanTo){
		if(CheckUtil.isNull(beanTo.getFileForm())){
			FileForm fileForm = new FileForm();
			// EntityとBeanで一致する項目のみ
			BeanUtils.copyProperties(dataFrom, fileForm);
			beanTo.setFileForm(fileForm);
		}
		return beanTo;
	}
	/**
	 * 
	 * @param from
	 * @param listBeanClass
	 * @return
	 * @throws Exception
	 */
	synchronized public static <T extends ListDataBean> List<ListDataBean> entityListPropertyCopy(
			List<FukuyaEntityInterface> from, Class<T> listBeanClass) throws Exception {
		if(CheckUtil.isNull(from)){
			return new ArrayList<ListDataBean>();
		}
		final List<ListDataBean> list = new ArrayList<ListDataBean>();
		for(FukuyaEntityInterface dataFrom: from){
			ListDataBean beanTo = listBeanClass.newInstance();
			BeanUtils.copyProperties(dataFrom, beanTo);
			if(CheckUtil.isNull(beanTo.getCheckBox())){
				beanTo.setCheckBox(new CheckBox());
			}
			DataArrangeUtil.ifExistsPropertyCopyForFile(dataFrom, beanTo);
			list.add(beanTo);
		}
		return list;
	}
	/**
	 * リクエスト時のソートコマンドに基づきエンティティのリストをソートします.
	 * @param listForm
	 * @param entityList
	 * @return
	 * @throws Exception
	 */
	synchronized public static List<FukuyaEntityInterface> entityListSort(
			UIPagingListForm listForm, List<FukuyaEntityInterface> entityList) throws Exception {
		//TODO コーディング規約にインスタンス参照の切り離しと参照渡し不可について記述要
		// インスタンス参照から切り離す
		List<FukuyaEntityInterface> inputEntityList = new ArrayList<FukuyaEntityInterface>(entityList);
		// 画面で列ソート指定がある場合
		String sortProp = listForm.getNextSortKey();
		String sorted = listForm.getSorted();
		String direction = SystemConstants.SORT_ASC;
		if(CheckUtil.isNotEmpty(sortProp)) {
			
			// 初回ソートの場合
			if(CheckUtil.isNull(sorted) || CheckUtil.isEmpty(sorted)) {
				sorted = new StringBuilder(sortProp).append(SystemConstants.SORT_DELIMITER).append(SystemConstants.SORT_ASC).toString();
				
			}
			// 同一見出しソートの場合
			else if(sorted.indexOf(sortProp) == 0){
				// sortedに対してデリミタ(@)で分割
				String[] sortProps = sorted.split(SystemConstants.SORT_DELIMITER);
				
				// ソート順のリセットでない場合
				if(sortProps.length > 1 && !SystemConstants.SORT_RESET.equals(sortProps[1])){
					// 昇順 -> 降順 -> リセットの順に推移させる
					direction = SystemConstants.SORT_ASC.equals(sortProps[1]) ? SystemConstants.SORT_DESC : 
						SystemConstants.SORT_DESC.equals(sortProps[1]) ? SystemConstants.SORT_RESET : SystemConstants.SORT_ASC; 
					sorted = new StringBuilder(sortProp).append(SystemConstants.SORT_DELIMITER).append(direction).toString();
				}
				else {
					// ソート項目を昇順としてレスポンスする
					sorted = new StringBuilder(sortProp).append(SystemConstants.SORT_DELIMITER).append(SystemConstants.SORT_ASC).toString();
				}
			}
			// 他の見出しソートの場合
			else {
				sorted = new StringBuilder(sortProp).append(SystemConstants.SORT_DELIMITER).append(SystemConstants.SORT_ASC).toString();
			}
		}
		// 前回ソート情報のみPOST送信された場合は、ソート順を維持
		else {
			sorted = listForm.getSorted();
			if(CheckUtil.isNotEmpty(sorted)){
				// sortedに対してデリミタ(@)で分割
				String[] sortProps = sorted.split(SystemConstants.SORT_DELIMITER);
				sortProp = sortProps[0];
				direction = sortProps[1];
				sorted = new StringBuilder(sortProp).append(SystemConstants.SORT_DELIMITER).append(direction).toString();
			}
			// ソート情報が存在しない場合
			else {
				// 今回のソート方向をカレントへセット
				listForm.setSorted(sorted);
				return inputEntityList;
			}
		}
		// 今回のソート内容をカレントへセット
		listForm.setSorted(sorted);
		// ソート順が昇順または降順の場合
		if(!SystemConstants.SORT_RESET.equals(direction)){
			Comparator<FukuyaEntityInterface> comp = 
					DataArrangeUtil.getComparator(sortProp, inputEntityList.get(0).getClass().newInstance(), direction);
			Collections.sort(inputEntityList, comp);
		}
		// ソート順のリセットの場合(ソートしない)
		else {
		}

		return inputEntityList;
	}
	/**
	 * BeanのList、エンティティのList等からgetter毎の値のリストを生成
	 * @param beanList
	 * @return
	 * @throws Exception
	 */
	public static <T extends ListDataBean> HashMap<String, List<Object>> getBeansDataVertical(List<T> beanList)
	throws Exception {
		final HashMap<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
		// 引数がnullか空の場合は、空のMapを返す
		if(CheckUtil.isNull(beanList) || beanList.isEmpty()){
			return resultMap;
		}

		// Beanからgetterとsetterを取得する
		Class<?> BeanCls = beanList.get(0).getClass();
		PropertyDescriptor[] props =
				BeanUtils.getPropertyDescriptors(BeanCls);

		int propLength = props.length;
		// List件数分ループ
		for(T elm : beanList){
			for(PropertyDescriptor prop : props){
				// getterを取得する
				Method getter = prop.getReadMethod();
				// Mapのキーにする際の名称(プロパティ名)
				String mapKeyName = prop.getName();
				Class<?> typeClass = (Class<?>)getter.getReturnType();
				// List要素からgetterで値を取得する
				Object getValue = getter.invoke(elm);
				if(resultMap.size() < propLength){
					// 初期のリストを生成する
					List<Object> values = new ArrayList<Object>();
					// Mapのキー(プロパティ名)に対応するListへ値を追加
					if(typeClass.isInstance(getValue)){
						values.add(typeClass.cast(getValue));
					}
					resultMap.put(mapKeyName, values);
				} else {
					// Mapのキーに一致するListへ値を追加していく
					if(typeClass.isInstance(getValue)){
						resultMap.get(mapKeyName).add(typeClass.cast(getValue));
					}
				}
			}
		}
		return resultMap;
	}
}
/**
 * 内部クラス
 * DataEntityタイプのBeanをソートするComparator.<br>
 * ソート対象のbean内変数名(property名)と、ソート方向(昇順/降順)を指定する
 * 1つ分のカラムソートに対応.
 * 
 * @author 
 *
 * @param <T>
 */
class EntityComparator<T> implements Comparator<FukuyaEntityInterface> {
	private static final Logger logger = LoggerFactory.getLogger(EntityComparator.class);
	private Method getter;
	private String order;

	/**
	 * コンストラクタ
	 * @param propertyName ソート対象のBean内変数名(property名)
	 * @param bean ソート対象のBean
	 * @param order ソートの方向(ASC/DESC)
	 */
	public EntityComparator(String propertyName, FukuyaEntityInterface bean, String order){
		logger.debug("propertyName = [" + propertyName.toString() + "], bean = [" + bean.toString() + "], order = [" + order.toString() + "]");
		try{
			PropertyDescriptor propAccessor = new PropertyDescriptor(propertyName, bean.getClass());
			this.getter = propAccessor.getReadMethod();
			this.order  = order;
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * {@inheritDoc}
	 */
	public int compare(FukuyaEntityInterface d1, FukuyaEntityInterface d2){
		int result = 0;
		try {
			Object value1 = (Object)this.getter.invoke(d1, (Object[])null);
			Object value2 = (Object)this.getter.invoke(d2, (Object[])null);

			if(CheckUtil.isNotNull(value1)) {
				result = SystemConstants.SORT_ASC.equals(this.order) ?
						String.valueOf(value1).compareTo(String.valueOf(value2)) : 
							(String.valueOf(value1).compareTo(String.valueOf(value2))) * -1 ;
				return result;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
