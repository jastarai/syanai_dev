package jp.jast.spframework.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * ファイル名必須入力チェック.
 *
 * @author Jast
 */
public class NotBlankFileValidator implements ConstraintValidator<NotBlankFile, MultipartFile> {
	
	@Override
	public void initialize(NotBlankFile constraintAnnotation) {

	}

	public boolean isValid(MultipartFile value, ConstraintValidatorContext constraintValidatorContext) {

		// ファイルが指定されていない場合はエラー
		if ( value == null || !StringUtils.hasLength(value.getOriginalFilename())) {
			return false;
		}

		return true;		
	}

}