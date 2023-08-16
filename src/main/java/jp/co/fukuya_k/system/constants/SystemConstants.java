package jp.co.fukuya_k.system.constants;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;

/**
 * 
 * @author
 *
 */
public final class SystemConstants {
	/** SYSTEM_URL_DOWNLOAD (システムで使用するファイルダウンロード用URL。主にFileDownloadControllerで使用) */
	public static final String SYSTEM_URL_DOWNLOAD = "/download";
	/** SYSTEM_URL_DISPLAY_IMAGE (システムで使用する画像表示用URL。主にFileDownloadControllerで使用) */
	public static final String SYSTEM_URL_DISPLAY_IMAGE = "/dispImage";

	/** ENABLE_IMAGE_TYPE (システムで表示可能な画像のコンテントタイプ。主にFileDownloadServiceで使用) */
	public static final String[] ENABLE_IMAGE_TYPE = new String[]{
			"image/jpeg","image/gif","image/ief","image/g3fax","image/tiff",
			"image/cgm","image/naplps","image/vnd.dwg","image/vnd.svf",
			"image/vnd.dxf","image/png","image/vnd.fpx","image/vnd.net-fpx",
			"image/vnd.xiff","image/prs.btif","image/vnd.fastbidsheet",
			"image/vnd.wap.wbmp","image/prs.pti","image/vnd.cns.inf2",
			"image/vnd.mix","image/vnd.fujixerox.edmics-rlc","image/vnd.fujixerox.edmics-mmr",
			"image/vnd.fst"};

	/** MAX_ONE_FILE_SIZE (アップロード可能な1ファイルのbyteサイズ上限を設定する。主にUIFileControlServiceで使用) */
	// TODO 例として10000byteを設定
	public static final long ONE_FILE_MAX_BYTES_UPLOAD_SIZE = 10000L;



	/** アップロード可能なファイルの拡張子リスト(Excel系) */
	public static final String[] UPLOADABLE_EXCEL_GROUP = 
				  {"xla", "xlam", "xls", "xlsb", "xlsm", "xlsx", "xlt", "xltm", "xltx", "xlw", "xml"};
	/** アップロード可能なファイルの拡張子リスト(Excel系以外) */
	public static final String[] UPLOADABLE_OTHER_GROUP = {"csv", "pdf"};

	/**
	 * HTTPヘッダコンテンツタイプ
	 */
	public static final MediaType HTTP_CONTENT_TYPE_ANY = new MediaType("application", "force-download");
	public static final MediaType HTTP_CONTENT_TYPE_PDF = new MediaType("application", "pdf");
	public static final MediaType HTTP_CONTENT_TYPE_EXCEL = new MediaType("application", "vnd.ms-excel");
	public static final MediaType HTTP_CONTENT_TYPE_CSV = new MediaType("text", "csv");
	public static final MediaType HTTP_CONTENT_TYPE_TEXT_HTML = MediaType.TEXT_HTML;
	/**
	 * Pageableインスタンス生成時定数
	 */
	
	// デフォルトの最小ページ数
	public static final int MIN_PAGENATION_NUMBER = 1;
	// デフォルトの1ページあたりの行数
	public static final int MAX_LINE_LENGTH_BY_PAGE = 7;

	/**
	 * ソート順
	 */
	/* 昇順 */
	public static final String SORT_ASC = Direction.ASC.toString();
	/* 降順 */
	public static final String SORT_DESC = Direction.DESC.toString();
	/* リセット */
	public static final String SORT_RESET = "RESET";
	/* ソート情報に使用するデリミタ*/
	public static final String SORT_DELIMITER = "@";
}
