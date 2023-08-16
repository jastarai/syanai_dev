package jp.jast.spframework.thymeleaf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.thymeleaf.Configuration;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.spring4.util.DetailedError;
import org.thymeleaf.spring4.util.FieldUtils;

import jp.jast.spframework.util.MessageUtil;

public final class ExtFields { 

    private final Configuration configuration; 
    private final IProcessingContext processingContext; 

    /** 
     * 拡張のため定数追加
     */ 
    private final String MSGBASENAME = "messages";
    private final String MSGBASENAME_VALIDATION = "messages";
    
    /** 
     * 
     * @return the result 
     * @since 2.1.0 
     */ 
    public boolean hasAnyErrors() { 
        return FieldUtils.hasAnyErrors(this.configuration, this.processingContext); 
    } 
     
    /** 
     * 
     * @return the result 
     * @since 2.1.0 
     */ 
    public boolean hasErrors() { 
        return FieldUtils.hasAnyErrors(this.configuration, this.processingContext); 
    } 
     
    public boolean hasErrors(final String field) { 
        return FieldUtils.hasErrors(this.configuration, this.processingContext, field); 
    } 
     
    /** 
     *  
     * @return the result 
     * @since 2.1.0 
     */ 
    public boolean hasGlobalErrors() { 
        return FieldUtils.hasGlobalErrors(this.configuration, this.processingContext); 
    } 


    /** 
     *  
     * @return the result 
     * @since 2.1.0 
     */ 
    public List<String> allErrors() { 
        return FieldUtils.errors(this.configuration, this.processingContext); 
    } 


    /** 
     *  
     * @return the result 
     * @since 2.1.0 
     */ 
    public List<String> errors() { 
        return FieldUtils.errors(this.configuration, this.processingContext); 
    } 


    public List<String> errors(final String field) { 
        return FieldUtils.errors(this.configuration, this.processingContext, field); 
    } 
     
    /** 
     *  
     * @return the result 
     * @since 2.1.0 
     */ 
    public List<String> globalErrors() { 
        return FieldUtils.globalErrors(this.configuration, this.processingContext); 
    } 
     
    public String idFromName(final String fieldName) { 
        return FieldUtils.idFromName(fieldName); 
    } 

    /** 
     *  
     * @return the result 
     * @since 2.1.2 
     */ 
    public List<DetailedError> detailedErrors() { 
        return FieldUtils.detailedErrors(this.configuration, this.processingContext); 
    } 
     
    public ExtFields(final Configuration configuration, final IProcessingContext processingContext) { 
        super(); 
        this.configuration = configuration; 
        this.processingContext = processingContext; 
    } 
   
    // 以下、拡張につき追加
    
    /** 
     * 拡張のため追加
     * フィールドとチェックＩＤを指定して、対象のエラーが存在したかを返す
     * @return the result 
     * @since 2.1.0 
     */ 
    public boolean hasErrorsExt(final String field, final String checkId) {
    	
    	boolean blnRtln = FieldUtils.errors(this.configuration, this.processingContext, field).contains(checkId);
    	
    	if (!blnRtln) {
    		
    		// errorsの中に対象チェックが含まれている場合（DBメッセージ置換時）への対応
    		
    		List<String> tmpList = FieldUtils.errors(this.configuration, this.processingContext, field);

        	for (String strInList : tmpList) {
        		
        		if (strInList.indexOf("|") >= 0) {
        			
        			// splitの引数は正規表現のため、パイプはエスケープする必要あり。エスケープ文字列自体もエスケープする必要があるため、
        			// 下記のように"\\|"となる
        			String[] str = strInList.split("\\|",-1);
        			
        			if (checkId.equals(str[0])) {

        				blnRtln = true;
        				
        				break;
        				
        			}
        			
        		}
        		
        	}
    		
    	}
    	
    	return blnRtln;

    } 
    
    /** 
     * 拡張のため追加
     * エラーメッセージIDと置換文字列を渡し、置換後のメッセージを返します
     * @return the result 
     * @since 2.1.0 
     */ 
    public String getValidationMsg(final String msgId, final String...args) { 
    	
    	return MessageUtil.getMsgWithBaseName(MSGBASENAME_VALIDATION, msgId, null, args);
    	
    } 
    
    /** 
     * 拡張のため追加
     * エラーメッセージIDと置換文字列を渡し、置換後のメッセージを返します（メッセージID表示有）
     * @return the result 
     * @since 2.1.0 
     */ 
    public String getValidationMsgWithId(final String msgId, final String...args) { 
    	
    	return MessageUtil.getMsgWithBaseNameWithId(MSGBASENAME_VALIDATION, msgId, null, args);
    	
    } 
    
    /** 
     * 拡張のため追加
     * エラーメッセージIDと置換文字列を渡し、置換後のメッセージを返します。サーバ側で取得した値も置換できます。
     * @return the result 
     * @since 2.1.0 
     */ 
    public String getValidationMsgWithServerVal(final String field, final String checkId, final String msgId,  final String...args) { 
    	
    	return MessageUtil.getMsgWithBaseName(MSGBASENAME_VALIDATION, msgId, getServerValString(field,checkId), args);
    	
    } 
    
    /** 
     * 拡張のため追加
     * エラーメッセージIDと置換文字列を渡し、置換後のメッセージを返します。サーバ側で取得した値も置換できます。（メッセージID表示あり）
     * @return the result 
     * @since 2.1.0 
     */ 
    public String getValidationMsgWithServerValWithId(final String field, final String checkId, final String msgId,  final String...args) { 
    	
    	return MessageUtil.getMsgWithBaseNameWithId(MSGBASENAME_VALIDATION, msgId, getServerValString(field,checkId), args);
    	
    } 
    
    /** 
     * 拡張のため追加
     * サーバ側で取得した値を置換するための文字列編集。
     * @return the result 
     * @since 2.1.0 
     */ 
    private String[] getServerValString(final String field, final String checkId) {
    	
    	List<String> tmpList = FieldUtils.errors(this.configuration, this.processingContext, field);
    	
    	String[] strOnServerVal = null;
    	
    	for (String strInList : tmpList) {
    		
    		if (strInList.indexOf("|") >= 0) {
    			
    			// splitの引数は正規表現のため、パイプはエスケープする必要あり。エスケープ文字列自体もエスケープする必要があるため、
    			// 下記のように"\\|"となる
    			String[] str = strInList.split("\\|",-1);
    			
    			if (checkId.equals(str[0])) {

    				// ※右辺をArrays.asList(str)のままにしていないのは、あとでremoveを使うため
    				List<String> tmpInList = new ArrayList<String>(Arrays.asList(str));
    				
    				tmpInList.remove(0);
    				
    				strOnServerVal = (String[]) tmpInList.toArray(new String[tmpInList.size()]);
    				
    				break;
    				
    			}
    			
    		}
    		
    	}
    	
    	return strOnServerVal;
    	
    }
    
    /** 
     * 拡張のため追加
     * エラーメッセージIDと置換文字列を渡し、置換後のメッセージを返します
     * ※行データ用
     * @return the result 
     * @since 2.1.0 
     */ 
    public String getValidationMsgForRowData(final String msgId, final String rownum, final String...args) { 

    	String preMsg = "";
  		preMsg = String.valueOf(Integer.valueOf(rownum) + 1) + "行目：";
    	
    	return preMsg + MessageUtil.getMsgWithBaseName(MSGBASENAME_VALIDATION, msgId, null, args);
    	
    } 

    /** 
     * 拡張のため追加
     * エラーメッセージIDと置換文字列を渡し、置換後のメッセージを返します（メッセージID表示あり）
     * ※行データ用
     * @return the result 
     * @since 2.1.0 
     */ 
    public String getValidationMsgForRowDataWithId(final String msgId, final String rownum, final String...args) { 

    	String preMsg = "";
  		preMsg = String.valueOf(Integer.valueOf(rownum) + 1) + "行目：";
    	
    	return preMsg + MessageUtil.getMsgWithBaseNameWithId(MSGBASENAME_VALIDATION, msgId, null, args);
    	
    } 
    
    /** 
     * 拡張のため追加
     * エラーメッセージIDと置換文字列を渡し、置換後のメッセージを返します
     * @return the result 
     * @since 2.1.0 
     */ 
    public String getMsg(final String msgId, final String...args) { 
    	
    	return MessageUtil.getMsgWithBaseName(MSGBASENAME, msgId, null, args);
    	
    } 
    
} 
