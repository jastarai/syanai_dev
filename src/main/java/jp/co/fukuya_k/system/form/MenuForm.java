package jp.co.fukuya_k.system.form;

import java.util.List;

import jp.co.fukuya_k.system.form.components.MenuItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * メニューツリーに使用するサブフォームです.
 * 
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MenuForm extends SubFormBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4249645138558985972L;
	/* MenuItemクラスによりメニュー要素を構築済みのリスト */
	private List<MenuItem> menuTree;

}
