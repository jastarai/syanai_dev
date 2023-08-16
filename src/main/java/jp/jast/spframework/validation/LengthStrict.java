package jp.jast.spframework.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


/**
 * サロゲートペアを考慮した文字長チェック
 *
 * @author jast
 */
@Documented
@Constraint(validatedBy = {LengthStrictValidator.class})
@Target({ FIELD })
@Retention(RUNTIME)
public @interface LengthStrict {
	long min() default 0;

	long max() default Long.MAX_VALUE;

	String message() default "LengthStrict";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * Defines several {@code @Length} annotations on the same element.
	 */
	@Target({ FIELD })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		LengthStrict[] value();
	}
}
