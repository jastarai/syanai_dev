package jp.jast.spframework.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Payload;


/**
 * Csvファイルチェック.
 *
 *
 * @author jast
 */
@Documented
@Target({ FIELD })
@Retention(RUNTIME)
public @interface CsvFileCheckOnServer {

	String message() default "";
	boolean columnSizeCheck() default false;
	String columnSize() default "";

	// CustomCsvConfig プロパティ
	char quote() default '"';
	boolean quoteDisabled() default true;
	char escape() default '\\';
	boolean escapeDisabled() default true;
	boolean trimWhiteSpaces() default false;
	String lineSeparator() default "\r\n";
	String skipLines() default "0";
	boolean utf8bomPolicy() default false;
	boolean variableColumns() default true;
	String encoding() default "";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * Defines several {@code @Length} annotations on the same element.
	 */
	@Target({ FIELD })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		CsvFileCheckOnServer[] value();
	}
}
