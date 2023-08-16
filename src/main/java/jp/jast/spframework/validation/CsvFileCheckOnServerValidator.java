package jp.jast.spframework.validation;

import java.io.IOException;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jp.jast.spframework.csv.CsvUtil;
import jp.jast.spframework.csv.CustomCsvConfig;

/**
 * CSVファイルチェック
 *
 * @author Jast
 */
public class CsvFileCheckOnServerValidator implements ConstraintValidator<CsvFileCheckOnServer, MultipartFile> {

	private static final Log log = LoggerFactory.make();

	private String columnSize;
	private boolean columnSizeCheck;

	private char quote;
	private boolean quoteDisabled;
	private char escape;
	private boolean escapeDisabled;
	private boolean trimWhiteSpaces;
	private String lineSeparator;
	private String skipLines;
	private boolean utf8bomPolicy;
	private boolean variableColumns;
	private String encoding;

	@Override
	public void initialize(CsvFileCheckOnServer constraintAnnotation) {

		columnSizeCheck = constraintAnnotation.columnSizeCheck();
		columnSize = constraintAnnotation.columnSize();

		// CustomCsvConfig プロパティ
		quote = constraintAnnotation.quote();
		quoteDisabled = constraintAnnotation.quoteDisabled();
		escape = constraintAnnotation.escape();
		escapeDisabled = constraintAnnotation.escapeDisabled();
		trimWhiteSpaces = constraintAnnotation.trimWhiteSpaces();
		lineSeparator = constraintAnnotation.lineSeparator();
		skipLines = constraintAnnotation.skipLines();
		utf8bomPolicy = constraintAnnotation.utf8bomPolicy();
		variableColumns = constraintAnnotation.variableColumns();
		encoding = constraintAnnotation.encoding();

		// アノテーション引数を定義後、それにあわせて初期化処理を記述すること、
		validateParameters();
	}

	public boolean isValid(MultipartFile value, ConstraintValidatorContext constraintValidatorContext) {
		
		// ファイルが指定されていない場合はチェックしない
		if (value == null || !StringUtils.hasLength(value.getOriginalFilename())) {
			return true;
		}
		
		//　★デフォルトのメッセージを使わないための設定。
		constraintValidatorContext.disableDefaultConstraintViolation();
		
		CustomCsvConfig cfg = CsvUtil.getCustomCsvConfig(quote, quoteDisabled, escape, escapeDisabled, trimWhiteSpaces, lineSeparator, Integer.parseInt(skipLines), utf8bomPolicy, variableColumns, encoding);

		try {
			
			cfg.setIgnoreEmptyLines(true);
			List<List<String>> listOmitEmptyLine = CsvUtil.getCsvData(cfg, value.getInputStream());
			cfg.setIgnoreEmptyLines(false);
			List<List<String>> listWithEmptyLine = CsvUtil.getCsvData(cfg, value.getInputStream());
		
			// 空行有無チェック
			if (listWithEmptyLine.size() - listOmitEmptyLine.size() > 1 ) {
				// 空行を除去した取り込み結果と、空行も含めた取り込み結果の行数の差が1以上の場合はエラー（途中に空行ありと判断）
				// ※末尾の空行1行は自動除去対象とするため、差の判定について1行はOKとする
				
				// 空行の行数
				int emptyLine = listOmitEmptyLine.size() + 1;

				// メッセージ詰めて処理終了する
				constraintValidatorContext.buildConstraintViolationWithTemplate(this.getClass().getSimpleName().replace("Validator", "") + "|" + emptyLine + "行目に空行が存在します。").addConstraintViolation();
				return false;

			} 

			// カラム数チェック　実施判定
			// 実施有無は、アノテーション引数で受け取る。
			if (columnSizeCheck) {
		
				int count = 1;
				for (List<String> list : listOmitEmptyLine) {

					// カラム数チェック
					// 比較対象のカラム数は、アノテーション引数で受け取る。
					if (list.size() != Integer.parseInt(columnSize)) {

						// カラム数チェック エラー
						constraintValidatorContext.buildConstraintViolationWithTemplate(this.getClass().getSimpleName().replace("Validator", "") + "|" + count + "行目の項目数に誤りがあります。").addConstraintViolation();
						return false;
					}
					count ++;
				}
			}

		} catch (IOException e) {

			constraintValidatorContext.buildConstraintViolationWithTemplate(this.getClass().getSimpleName().replace("Validator", "") + "|" + "ファイルの読み込みに失敗しました").addConstraintViolation();
			return false;
		}

		return true;
		
	}

	private void validateParameters() {

		if (columnSizeCheck) {
			if (StringUtils.isEmpty(columnSize)) {
				throw log.getUnexpectedParameterValueException();
			}
		}

	}
}