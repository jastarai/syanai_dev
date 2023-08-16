package jp.jast.spframework.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

/**
 * サロゲートペアを考慮した文字長チェック
 *
 * @author Jast
 */
public class LengthStrictValidator implements ConstraintValidator<LengthStrict, CharSequence> {

	private static final Log log = LoggerFactory.make();

	private long min;
	private long max;

	public void initialize(LengthStrict constraintAnnotation) {
		min = constraintAnnotation.min();
		max = constraintAnnotation.max();
		validateParameters();
	}

	public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
		if ( value == null ) {
			return true;
		}
		
		long length = value.codePoints().count();
		return length >= min && length <= max;
	}

	private void validateParameters() {
		if ( min < 0 ) {
			throw log.getMinCannotBeNegativeException();
		}
		if ( max < 0 ) {
			throw log.getMaxCannotBeNegativeException();
		}
		if ( max < min ) {
			throw log.getLengthCannotBeNegativeException();
		}
	}
}