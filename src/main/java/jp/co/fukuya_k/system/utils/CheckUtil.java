package jp.co.fukuya_k.system.utils;

import java.util.Arrays;

/**
 * 値チェック向けユーティリティクラス.
 * @author
 *
 */
public final class CheckUtil {

	/**
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNull(Object o){
		return o == null;
	}
	/**
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNotNull(Object o){
		return !isNull(o);
	}
	/**
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isEmpty(Object o){
		return ( isNull(o) || (o.toString().length() == 0 || o.equals("undefined")) );
	}
	/**
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNotEmpty(Object o){
		return isNotNull(o) && !isEmpty(o);
	}
	
	/**
	 * 
	 * @param target
	 * @param list
	 * @return
	 */
	public static boolean targetContains(Object target, Object[] list){
		return isNotNull(list) && isNotEmpty(target) && Arrays.asList(list).contains(target);
	}
}
