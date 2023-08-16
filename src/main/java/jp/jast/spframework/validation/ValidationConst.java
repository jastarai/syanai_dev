package jp.jast.spframework.validation;

public class ValidationConst {

	// 全角チェックの正規表現（半角文字以外として判定）
	public static final String REGEXP_ZENKAKU = "^[^ -~｡-ﾟ]*$";
	
	// 全角ひらがなチェックの正規表現
	public static final String REGEXP_ZENKAKU_HIRAGANA = "[ぁ-ん]*$";
	
	// 全角カタカナチェックの正規表現
	public static final String REGEXP_ZENKAKU_KATAKANA = "[ァ-ー]*$";
	
	// 全角英字チェックの正規表現
	public static final String REGEXP_ZENKAKU_ALPHABET = "[Ａ-ｚ]*$";
	
	// 全角数字チェックの正規表現
	public static final String REGEXP_ZENKAKU_NUMBER = "[０-９]*$";
	
	// 全角英数字チェックの正規表現
	public static final String REGEXP_ZENKAKU_NUMBER_ALPAHBET = "[０-９|Ａ-ｚ]*$";
	
	// 半角チェックの正規表現
	public static final String REGEXP_HANKAKU = "[ -~｡-ﾟ]*$";

	// 半角カタカナチェックの正規表現
	public static final String REGEXP_HANKAKU_KATAKANA = "[ｦ-ﾟ]*$";

	// 半角英字チェックの正規表現
	public static final String REGEXP_HANKAKU_ALPHABET = "[A-z]*$";

	// 半角数字チェックの正規表現
	public static final String REGEXP_HANKAKU_NUMBER = "[0-9]*$";

	// 半角英数字チェックの正規表現
	public static final String REGEXP_HANKAKU_NUMBER_ALPAHBET = "[0-9|A-z]*$";

	// 半角英字記号チェックの正規表現
	public static final String REGEXP_HANKAKU_ALPHABET_KIGOU = "[A-z!-/:-@\\[-`{-~]*$";

	// 半角数字記号チェックの正規表現
	public static final String REGEXP_HANKAKU_NUMBER_KIGOU = "[0-9!-/:-@\\[-`{-~]*$";

	// 半角英数記号チェックの正規表現
	public static final String REGEXP_HANKAKU_NUMBER_ALPAHBET_KIGOU = "[0-9|A-z!-/:-@\\[-`{-~]*$";
	
	// 日付入力形式チェックの正規表現
	public static final String REGEXP_DATE = "([0-9]{4}/[0-9]{2}/[0-9]{2})*$";

	// 電話番号入力形式チェックの正規表現
	public static final String REGEXP_TELNO = "([0-9]{2,4}-[0-9]{2,4}-[0-9]{4})*$";

	// 郵便番号入力形式チェックの正規表現
	public static final String REGEXP_ZIP = "([0-9]{3}-[0-9]{4}|[0-9]{7})*$";

}
