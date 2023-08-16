package jp.co.fukuya_k.system.form;

import jp.co.fukuya_k.system.interfaces.FukuyaEntityInterface;
import jp.jast.spframework.dao.ParamBinder;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 画面上で検索条件項目が存在する場合に使用するサブフォームです.
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UINormalSearchForm extends SubFormBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3024981567494772412L;
	private ParamBinder<? extends FukuyaEntityInterface> condition1;
	private ParamBinder<? extends FukuyaEntityInterface> condition2;
	private ParamBinder<? extends FukuyaEntityInterface> condition3;
	private ParamBinder<? extends FukuyaEntityInterface> condition4;
	private ParamBinder<? extends FukuyaEntityInterface> condition5;
	private ParamBinder<? extends FukuyaEntityInterface> condition6;
}
