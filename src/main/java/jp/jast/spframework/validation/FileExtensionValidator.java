package jp.jast.spframework.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * ファイルの複数拡張子のチェック
 *
 * @author Jast
 */
public class FileExtensionValidator implements ConstraintValidator<FileExtension, MultipartFile> {

	private static final Log log = LoggerFactory.make();

	private String extensions;
	private boolean consistent;
	
	@Override
	public void initialize(FileExtension constraintAnnotation) {
		
		extensions = constraintAnnotation.extension();
		consistent = constraintAnnotation.consistent();
		
		validateParameters();
	}

	public boolean isValid(MultipartFile value, ConstraintValidatorContext constraintValidatorContext) {
		
		// ファイルが指定されていない場合はチェックしない
		if ( value == null || !StringUtils.hasLength(value.getOriginalFilename())) {
			return true;
		}

		String extension = getExtension(value.getOriginalFilename());
		
		boolean extensionMatch = false;

		for (String str : extensions.split(",")) {

			// 大文字と小文字を区別しない
			Pattern p = Pattern.compile(str, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(extension);

			if (m.find()) {
				// 拡張子が定数定義にある場合、true設定
				extensionMatch = true;
			}
		}

		if (consistent && !extensionMatch){
			 // ファイル拡張子が、指定した拡張子と一致しない場合、エラー返却
			 return false;

		 } else if (!consistent && extensionMatch) {
			 // ファイル拡張子が、指定した拡張子と一致する場合、エラー返却
			 return false;
		 }

		return true;	
	}

	private void validateParameters() {
		if (StringUtils.isEmpty(extensions)) {
			
			throw log.getUnexpectedParameterValueException();

		}
	}

	private String getExtension(String name) {
		
		int index = name.lastIndexOf('.');
		String extention = "";
		if (index >= 0) { //拡張子がある場合
			extention = name.substring(index + 1);
		}
		return extention;
	}
}