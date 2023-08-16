package jp.jast.spframework.csv;

import com.orangesignal.csv.CsvConfig;

/**
 * CsvConfigのカスタムクラス<br>
 * 区切り文字形式情報を提供します。<p>
 * このクラスは、区切り文字や囲み文字、エスケープ文字など CSV 形式に関する設定情報を管理します。
 * 
 * @author
 */
public class CustomCsvConfig extends CsvConfig {

	private static final long serialVersionUID = -7531286991159010295L;

	/**
	 * エンコード文字を保持します。
	 */
	private String encoding;

	// セッター/ゲッター

	/**
	 * エンコード文字を返します。
	 *
	 * @return エンコード文字
	 */
	public String getEncoding() { return encoding; }

	/**
	 * エンコード文字を設定します。
	 *
	 * @param encoding エンコード文字
	 */
	public void setEncoding(final String encoding) { this.encoding = encoding; }

}