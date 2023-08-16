package jp.jast.spframework.validation;

import java.text.DateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

/**
 * 日付妥当性チェック（存在）.
 *
 * @author Jast
 */
public class ExistDateValidator implements ConstraintValidator<ExistDate, CharSequence> {
	
	@Override
	public void initialize(ExistDate constraintAnnotation) {
	}

	public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
		
		// 日付が入力されていない場合はチェックしない
		if (StringUtils.isEmpty(value)) {
			return true;
		}

		DateFormat df = DateFormat.getDateInstance();
		try {

			df.setLenient(false);
			// 日付として妥当ではない場合はException
			df.parse(value.toString());
			return true;
	
		} catch (Exception e) {
			return false;
		} finally {
			df = null;
		}
	}
}