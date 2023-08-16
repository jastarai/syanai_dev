package jp.jast.spframework.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


/**
 * ファイル必須入力のチェック.
 *
 * @author jast
 */
@Documented
@Constraint(validatedBy = {NotBlankFileValidator.class})
@Target({ FIELD })
@Retention(RUNTIME)
public @interface NotBlankFile {
	
	String message() default "NotBlankFile";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * Defines several {@code @Length} annotations on the same element.
	 */
	@Target({ FIELD })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		NotBlankFile[] value();
	}
}
