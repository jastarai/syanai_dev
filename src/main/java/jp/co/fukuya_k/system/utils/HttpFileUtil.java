package jp.co.fukuya_k.system.utils;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import jp.co.fukuya_k.system.constants.SystemConstants;
import jp.co.fukuya_k.system.interfaces.FileUsableInterface;
/**
 * 主にファイルに関するHTTPコンテンツ向けのユーティリティクラスです.
 * @author
 *
 */
public class HttpFileUtil {
	/**
	 * ファイルダウンロード用(仮)
	 */
	// TODO 外部ファイル参照(HTTP_URI_HOST = hogehoge.properties.hostのような)が望ましい
	public static final String HTTP_CONTEXT_ROOT = "/";
	public static final String HTTP_CONTEXT_URI_HOST = "localhost";
	public static final int HTTP_CONTEXT_URI_PORT = 8080;
	/**
	 * 
	 * @param state
	 * @param contLength
	 * @return
	 */
	public static HttpHeaders getBaseHttpHead(int contLength){
		// HTTPヘッダを生成する
		HttpHeaders head = new HttpHeaders();
		// 遷移先URIを生成
		URI location = UriComponentsBuilder
				.fromPath(HTTP_CONTEXT_ROOT)
				.host(HTTP_CONTEXT_URI_HOST)
				.port(HTTP_CONTEXT_URI_PORT).build().toUri();
		
		head.setLocation(location);
		// コンテンツサイズをセット
		head.setContentLength(contLength);
		return head;
	}
	/**
	 * ファイル拡張子を元に使用可能なコンテンツタイプを取得します
	 * @return
	 */
	public static MediaType getFileContentType(String fileExtension){
		// デフォルト
		MediaType res = SystemConstants.HTTP_CONTENT_TYPE_ANY;
		// 可能な拡張子の一覧
		List<String> availableList = getAvailableFileExtentions();
		
		// 対象外の場合
		if(!CheckUtil.targetContains(fileExtension, (String[])availableList.toArray(new String[availableList.size()]))){
			return res;
		}
		else {
			// TODO 近い将来、拡張子毎に判定が必要
			res = SystemConstants.HTTP_CONTENT_TYPE_ANY;
		}
		return res;
	}
	public static List<String> getAvailableFileExtentions(){
		List<String> availableList = new ArrayList<String>(Arrays.asList(SystemConstants.UPLOADABLE_EXCEL_GROUP));
		availableList.add("csv");
		availableList.add("pdf");
		return availableList;
	}
	/**
	 * ファイルデータを含むエンティティを元にファイルダウンロードレスポンスを生成します
	 * 
	 * @param data
	 * @return
	 */
	synchronized public static ResponseEntity<byte[]> getHttpFileResponse(FileUsableInterface data) throws Exception {
		byte[] responseData = CheckUtil.isNotNull(data) ? data.getFileData() : new byte[0];
		HttpHeaders head = getBaseHttpHead(responseData.length);
		HttpStatus state = HttpStatus.NO_CONTENT;
		if(CheckUtil.isNotNull(data)){
			String fileName = data.getFileName();
/*
			// ファイル名の拡張子からHTTPヘッダのコンテンツタイプを取得する
			List<String> availableList = getAvailableFileExtentions();
			for(String suffix : availableList){
				if(fileName.toLowerCase().endsWith(suffix)){
					MediaType mediaType = getFileContentType(suffix);
					head.setContentType(mediaType);
					break;
				}
			}
*/
// TODO テストコード(本来やりたい事は上のコメントを外す)
			MediaType mediaType = getFileContentType("any");
			head.setContentType(mediaType);

			try{
				// ファイル名をUTF8へURLエンコードする
				head.add("Content-disposition"
						, new StringBuilder("attachment; filename=").append(URLEncoder.encode(fileName, "utf-8")).toString());
// 以下、不適コード(「※汎用のファイルダウンロード機能では、ファイルの種類によらず必ず保存させる」を満たせない)
//				head.setContentDispositionFormData("filename", URLEncoder.encode(fileName, "utf-8"));
//				head.add("Content-disposition", "filename=" + URLEncoder.encode(fileName, "utf-8"));
			} catch(Exception e){
				e.printStackTrace();
			}
			state = HttpStatus.OK;
		}
		else {
			head.setContentLength(0);
			head.setContentType(SystemConstants.HTTP_CONTENT_TYPE_TEXT_HTML);
			state = HttpStatus.NO_CONTENT;
		}
		return new ResponseEntity<byte[]>(responseData, head, state);
	}
}
