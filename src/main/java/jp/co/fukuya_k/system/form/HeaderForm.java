package jp.co.fukuya_k.system.form;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 1画面内の最上段部(ヘッダー)で使用します.
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class HeaderForm extends SubFormBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull
	private String loginUser;
}
