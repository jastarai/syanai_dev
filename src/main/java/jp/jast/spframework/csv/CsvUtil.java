package jp.jast.spframework.csv;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.CsvReader;
import com.orangesignal.csv.manager.CsvColumnPositionMappingBeanLoader;
import com.orangesignal.csv.manager.CsvColumnPositionMappingBeanManager;
import com.orangesignal.csv.manager.CsvColumnPositionMappingBeanSaver;

public class CsvUtil  {

	/**
	 * 
	 * メインのCustomCsvConfig生成用共通関数
	 * 
	 * @param	quote 			囲い文字
	 * @param	quoteDisabled 	囲い文字の有効(false)・無効(true)
	 * @param	escape 			エスケープ文字
	 * @param	escapeDisabled	エスケープ文字の有効(false)・無効(true)
	 * @param	trimWhiteSpaces	ホワイトスペース除去の有効(true)・無効(false)
	 * @param	lineSeparator	データ出力時の改行文字列
	 * @param	skipLines		先頭から読飛ばす論理行数
	 * @param	utf8bomPolicy	UTF-8 エンコーディングでの出力時に BOM (Byte Order Mark) を付与(true)　	
	 * @param	variableColumns	可変項目数を許可(true)・禁止(false)
	 * @param	encoding		エンコード文字
	 * @return	CustomCsvConfig
	 */
	public static CustomCsvConfig getCustomCsvConfig(
			char quote,
			boolean quoteDisabled,
			char escape,
			boolean escapeDisabled,
			boolean trimWhiteSpaces,
			String lineSeparator,
			int skipLines,
			boolean utf8bomPolicy,
			boolean variableColumns,
			String encoding) {

		// CustomCsvConfig 生成
		CustomCsvConfig cfg = new CustomCsvConfig();

		// CustomCsvConfig項目設定
		cfg.setQuote(quote);
		cfg.setQuoteDisabled(quoteDisabled);
		cfg.setEscape(escape);
		cfg.setEscapeDisabled(escapeDisabled);
		cfg.setIgnoreLeadingWhitespaces(trimWhiteSpaces);
		cfg.setIgnoreTrailingWhitespaces(trimWhiteSpaces);
		cfg.setIgnoreEmptyLines(true);
		cfg.setLineSeparator(lineSeparator);
		cfg.setSkipLines(skipLines);
		cfg.setUtf8bomPolicy(utf8bomPolicy);
		cfg.setVariableColumns(variableColumns);
		cfg.setEncoding(encoding);

		return cfg;
	}

	/**
	 * CSVファイル情報よりList<List<String>>形式の入力情報を取得する　共通関数
	 * 
	 * @param cfg
	 * @param sr
	 * @return
	 * @throws IOException
	 */
	public static List<List<String>> getCsvData(CustomCsvConfig cfg, InputStream sr) throws IOException {
		
   		CsvReader reader = new CsvReader(new InputStreamReader(sr),1024,cfg);
   		
   		List<List<String>> csvData = new ArrayList<List<String>>();
   		
			List<String> values;

			while ((values = reader.readValues()) != null) {
				
				csvData.add(values);

			}
   		
			reader.close();
			reader = null;
			
		return csvData;
			
	}

	/**
	 * CSVファイル情報よりList(Bean)形式の入力情報を取得する（項目位置指定）
	 * 
	 * @param cfg
	 * @param sr
	 * @param beanClass
	 * @param mappingColumn
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> getCsvDataToBean(CustomCsvConfig cfg, InputStream sr, Class<T> beanClass, List<CsvPositionMappingColumn> mappingColumn) throws IOException {

		CsvColumnPositionMappingBeanManager cbmng = new CsvColumnPositionMappingBeanManager(cfg);
		
		CsvColumnPositionMappingBeanLoader<T> ld = cbmng.load(beanClass);

		for (CsvPositionMappingColumn value : mappingColumn) {
			ld.column(value.getPosition(), value.getField());
		}

		return ld.from(sr, cfg.getEncoding());

	}

	/**
	 * CsvByteデータ取得　（項目位置指定）
	 * 
	 * @param cfg
	 * @param headerList
	 * @param dataList
	 * @param beanClass
	 * @param mappingColumn
	 * @param encoding
	 * @return byte[]
	 * @throws IOException
	 */
	public static <T> byte[] getCsvBytesFromBean(CustomCsvConfig cfg, List<T> headerList, List<T> dataList, Class<T> beanClass, List<CsvPositionMappingColumn> mappingColumn) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		createPositionMappingBeanSaver(cfg, headerList, dataList, beanClass, mappingColumn).to(baos, cfg.getEncoding());

		byte[] returnByte = baos.toByteArray(); 

		baos.close();
		baos = null;

		return returnByte;

	}

	/**
	 * Csvファイル出力　共通関数　（項目位置指定）
	 * 
	 * @param cfg
	 * @param headerList
	 * @param dataList
	 * @param beanClass
	 * @param mappingColumn
	 * @param strfile
	 * @param encoding
	 * @throws IOException
	 */
	public static <T> void saveCsvFileFromBean(CustomCsvConfig cfg, List<T> headerList , List<T> dataList, Class<T> beanClass, List<CsvPositionMappingColumn> mappingColumn, String filePath) throws IOException {

		File file = new File(filePath); 
		createPositionMappingBeanSaver(cfg, headerList, dataList, beanClass, mappingColumn).to(file, cfg.getEncoding());	

	}

	/**
	 * CsvColumnPositionMappingBeanSaver 生成
	 * 
	 * @param cfg				
	 * @param headerList		
	 * @param dataList			
	 * @param beanClass			
	 * @param mappingColumn		
	 * @return
	 */
	private static <T> CsvColumnPositionMappingBeanSaver<T> createPositionMappingBeanSaver(CsvConfig cfg, List<T> headerList, List<T> dataList, Class<T> beanClass, List<CsvPositionMappingColumn> mappingColumn){
		
		// List統合
		List<T> beanList = getBeanList(headerList, dataList);

		CsvColumnPositionMappingBeanManager cbmng = new CsvColumnPositionMappingBeanManager(cfg);	
		CsvColumnPositionMappingBeanSaver<T> sv = cbmng.save(beanList, beanClass);

		for (CsvPositionMappingColumn value : mappingColumn) {
			sv.column(value.getPosition(), value.getField());
		}

   		// ライブラリのヘッダ行制御は使用しない。
		sv.header(false);

		return sv;
	}
	/**
	 * CsvDataList統合
	 * 
	 * @param headerList ヘッダー情報リスト
	 * @param dataList　データ情報リスト
	 * @return List<T>
	 */
	private static <T> List<T> getBeanList(List<T> headerList, List<T> dataList){
		
		List<T> beanList = new ArrayList<T>(); 

		// HeaderList　追加
		if (headerList != null && !headerList.isEmpty()) {
			beanList.addAll(headerList);
		}
		// DataList 追加
		beanList.addAll(dataList);

		return beanList;
	}
	
}
