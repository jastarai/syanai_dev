package jp.jast.spframework.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * ファイルサイズのチェック
 *
 * @author Jast
 */
public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

	private static final Log log = LoggerFactory.make();

	private String size;
	
	@Override
	public void initialize(FileSize constraintAnnotation) {
		
		size = constraintAnnotation.size();
		validateParameters();
	}

	public boolean isValid(MultipartFile value, ConstraintValidatorContext constraintValidatorContext) {
		
		// ファイルが指定されていない場合はチェックしない
		if (value == null || !StringUtils.hasLength(value.getOriginalFilename())) {
			return true;
		}
		
		if (Long.valueOf(size).longValue() < value.getSize()) {

			return false;
		}
			
		return true;
		
	}

	private void validateParameters() {
		if (StringUtils.isEmpty(size)) {
			throw log.getUnexpectedParameterValueException();
		}

	}
}