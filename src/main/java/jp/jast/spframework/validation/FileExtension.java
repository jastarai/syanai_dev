package jp.jast.spframework.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


/**
 * ファイルの拡張子のチェック.
 *
 * @author jast
 */
@Documented
@Constraint(validatedBy = {FileExtensionValidator.class})
@Target({ FIELD })
@Retention(RUNTIME)
public @interface FileExtension {
	
	String extension() default "";
	boolean consistent() default true;

	String message() default "FileExtension";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * Defines several {@code @Length} annotations on the same element.
	 */
	@Target({ FIELD })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		FileExtension[] value();
	}
}
