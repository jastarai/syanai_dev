package jp.jast.spframework.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
/**
 * 型キャストを取り扱うユーティリティクラスです.
 * 
 *
 */
@Component
public class TypeCastingUtil {
	/**
	 * Listのジェネリクスタイプ「S」を指定のクラス型(T)へダウンキャストした新しいリストを返します.
	 * 
	 * @param fromList ジェネリクスタイプが引数指定のクラス型「T」のスーパータイプで宣言され、要素がそのサブクラスのインスタンスで構築されたList<br>
	 * @param clsType Listの要素をダウンキャストする際の対象のクラス型「T」<br>
	 * @return Listのジェネリクスタイプを「S」⇒「T」へ変換後の新しいList&lt;T&gt;<br>引数のいずれかがnullの場合、サイズ０(ゼロ)のリスト
	 * @throws IllegalArgumentException リストの型変数「S」と代入互換関係にないクラス型「T」が引数指定された場合
	 */
	public static <S, T extends S> List<T> getDownCastedTypeList(List<S> fromList, Class<T> clsType)
	throws IllegalArgumentException {
		final List<T> castedList = new ArrayList<T>();
		if(fromList == null || clsType == null){
			return castedList;
		}
		for(S fromElm : fromList){
			Class<?> elmCls = fromElm.getClass();
			// List要素がTと代入互換関係にある場合
			if(clsType.isInstance(fromElm) || clsType == elmCls){
				castedList.add(clsType.cast(fromElm));
			}
			// Listの要素タイプ宣言がNumberのサブタイプ且つ、変換先クラスもNumberのサブタイプの場合
			else if(Number.class.isAssignableFrom(elmCls) && Number.class.isAssignableFrom(clsType)){
				if(fromElm instanceof Number){
					castedList.add(
						// org.springframework.util.NumberUtils 参照
						(T)clsType.cast(NumberUtils.convertNumberToTargetClass((Number)fromElm, Number.class.cast(clsType).getClass()))
					);
				}
				else {
					castedList.add(
						// org.springframework.util.NumberUtils 参照
						(T)clsType.cast(NumberUtils.parseNumber(fromElm.toString(), Number.class.cast(clsType).getClass()))
					);
				}
			}
			else {
				throw new IllegalArgumentException(
						new StringBuilder("引数に指定されたListの要素<")
						.append(elmCls.getName())
						.append(">は、変換先として指定されたクラス<")
						.append(clsType.getName())
						.append(">と代入互換の関係にない為、変換できません")
						.toString());
			}
		}
		return castedList;
	}
}
