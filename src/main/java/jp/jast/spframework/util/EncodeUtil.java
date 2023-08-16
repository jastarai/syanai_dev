package jp.jast.spframework.util;

import java.io.UnsupportedEncodingException;

public class EncodeUtil {

	/**
	 * URLエンコード
	 * 
	 * @param	plainText 	エンコード対象の文字列
	 * @param	enc         文字エンコーディングの名前
	 * @return	String      エンコード後の文字列
	 * @throws UnsupportedEncodingException 
	 */
    public static String getUrlEncodeString(final String plainText, final String enc) throws UnsupportedEncodingException {
    	
    	String encStr;
		
    	encStr = java.net.URLEncoder.encode(plainText, enc);

    	// javaのURLEncodeでは、半角スペースが"+"に変換されるため、ここで"%20"へ変換する
    	encStr = encStr.replace("+", "%20");
    	
        return encStr;
        
    } 
	
}
