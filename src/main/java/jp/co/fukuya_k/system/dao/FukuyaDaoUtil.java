package jp.co.fukuya_k.system.dao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.jast.spframework.dao.DaoUtil;

/**
 * 福屋パッケージ用DaoUtilクラス.<br />
 * DaoUtilクラスのgetUpdateFunctionIdをオーバーライドします.
 * @author
 *
 */
@Component
public class FukuyaDaoUtil extends DaoUtil {

	/** 
	 * URIの区切り文字
	 */
	private final String URI_SLASH = "/";
	
	/** 
	 * リクエスト
	 */
	@Autowired
	private HttpServletRequest request;

	/**
	 * FunctionId取得<br />
	 * ※リクエストURIから機能IDを取得する.
	 * 
	 */
	@Override
	protected String getUpdateFunctionId() {
		
		String retVal = "";

		// リスエストの有無判定
		if (request != null) {
			// リクエストがnullではない場合、URIから機能IDを取得する
			retVal = request.getRequestURI();
			retVal = retVal.substring(0, retVal.lastIndexOf(URI_SLASH));
			retVal = retVal.substring(retVal.lastIndexOf(URI_SLASH) + 1);
		} else {
			// リクエストがnullの場合、application.propertiesで指定されている
			// デフォルト値を取得する
			retVal = super.getUpdateFunctionId();
		}

		return retVal;

	}

}
