package jp.jast.spframework.util;

import java.util.Properties;
import java.util.ResourceBundle;

import org.springframework.util.PropertyPlaceholderHelper;

public class MessageUtil {

	private static final String MSGBASENAME_DEFAULT = "messages";
	
	/**
	 * 置換対象の括弧の種類
	 * 
	 * Braces   中カッコ ｛｝
	 * Brackets 大カッコ []
	 */
	private static enum BracketType {
		Braces,
		Brackets
	}
	
	private static PropertyPlaceholderHelper placeholderHelperForBraces = new PropertyPlaceholderHelper("{","}"); 
	
	private static PropertyPlaceholderHelper placeholderHelperForBrackets = new PropertyPlaceholderHelper("[","]"); 
	
	/**
	 * メッセージ取得
	 * 
	 * @param	msgId 	メッセージID
	 * @param	strDB   メッセージ中のプレースホルダ[]に対応する置換文字列
	 * @param   args    メッセージ中のプレースホルダ{}に対応する置換文字列
	 * @return	String	メッセージ
	 */
    public static String getMsg(final String msgId, final String[] strDB, final String...args) {
    	
        return getMsgWithBaseName(MSGBASENAME_DEFAULT, msgId, strDB, args);
        
    } 
	
	/**
	 * メッセージ取得
	 * 
	 * @param	msgId 	メッセージID
	 * @param	strDB   メッセージ中のプレースホルダ[]に対応する置換文字列
	 * @param   args    メッセージ中のプレースホルダ{}に対応する置換文字列
	 * @return	String	メッセージ
	 */
    public static String getMsgWithId(final String msgId, final String[] strDB, final String...args) {
    	
        return getMsgWithBaseNameWithId(MSGBASENAME_DEFAULT, msgId, strDB, args);
        
    } 
    
	/**
	 * メッセージ取得（メッセージプロパティファイル指定あり）
	 * 
	 * @param	baseName    メッセージプロパティファイル名
	 * @param	msgId       メッセージID
	 * @param	strDB       メッセージ中のプレースホルダ[]に対応する置換文字列
	 * @param   args        メッセージ中のプレースホルダ{}に対応する置換文字列
	 * @return	String      メッセージ
	 */
    public static String getMsgWithBaseName(final String baseName, final String msgId, final String[] strDB, final String...args) {
        
    	ResourceBundle bundle = ResourceBundle.getBundle(baseName, ResourceBundleWithUtf8.UTF8_ENCODING_CONTROL);
    	
        String strRtn = bundle.getString(msgId);

        strRtn = getReplacedString(BracketType.Braces, strRtn, args);

        strRtn = getReplacedString(BracketType.Brackets, strRtn, strDB);
    	
        return strRtn;
        
    }
    
	/**
	 * メッセージ取得（メッセージプロパティファイル指定あり）（メッセージID表示あり）
	 * 
	 * @param	baseName    メッセージプロパティファイル名
	 * @param	msgId       メッセージID
	 * @param	strDB       メッセージ中のプレースホルダ[]に対応する置換文字列
	 * @param   args        メッセージ中のプレースホルダ{}に対応する置換文字列
	 * @return	String      メッセージ
	 */
    public static String getMsgWithBaseNameWithId(final String baseName, final String msgId, final String[] strDB, final String...args) {
    	
    	/* メッセージIDを先頭に付与した文字列を返却します */
        return msgId + "：" + getMsgWithBaseName(baseName,msgId,strDB,args);
        
    }

	/**
	 * メッセージ中のプレースホルダ置換処理
	 * 
	 * @param	bracketType 置換対象の括弧の種類
	 * @param	msg         置換前のメッセージ
	 * @param   args        置換対象の文字列
	 * @return	String      置換後のメッセージ
	 */
    private static String getReplacedString(final BracketType bracketType, final String msg, final String[] args) {
    	
    	PropertyPlaceholderHelper propertyPlaceholderHelper = null;
    	
    	if (bracketType == BracketType.Braces) {
    		propertyPlaceholderHelper = placeholderHelperForBraces;
    		
    	} else if (bracketType == BracketType.Brackets) {
    		propertyPlaceholderHelper = placeholderHelperForBrackets;
    	}
        
        Properties properties = new Properties();
        
        if (args != null) {

            for (int count = 0; count < args.length; count++) {
            	
            	properties.putIfAbsent(String.valueOf(count), args[count]) ;
            	
            }
            
            return propertyPlaceholderHelper.replacePlaceholders(msg, properties);
        	
        } else {
        	
        	return msg;
        	
        }
    	
    }
    
}
