/**
 * @fileOverview changestrong.js
 *
 * @author 日本システム技術株式会社
 * @version 1.0.0
 * @supported　InternetExploer11
 * @license 2016 Copyright © Japan System Techniques Co., Ltd. All Rights Reserved.
 */

/**
 * ファイルコントロール部品のクリアボタン押下処理
 * @return {void}
 * 
 */
$(function(){
    $('[id$=\\.fileClearBtn]').click(function(){
    	var parent = $($(this).parent('div')).parent('[id$=\\.fileCtrlParts]');
    	/* ファイルコントロール部品内のクリックイベントであることを判定 */
    	if (parent[0]) {
    		/* 登録済みデータが表示されているかをfileSeqの値の有無で判定 */
	    	if (parent.find('[id$=\\.fileSeq]').val() != "") {
	    		/* 前回表示データをクリア  */
	    		parent.find('[id$=\\.fileLink]').hide();
	    		parent.find('[id$=\\.fileSeq]').val("");
	    		parent.find('[id$=\\.fileName]').val("");
	    		/* 新規入力用のコントロール表示 */
	    		parent.find('[id$=\\.fileSelect]').show();
	    	}
	    	/* クリア（インプット側） */
	    	parent.find('[id$=\\.file]').val("");
    	}
    });
});

/**
 * ファイルコントロール部品のファイル選択ボタン押下処理
 * @return {void}
 * 
 */
$(function(){
    $('[id$=\\.fileSelectBtn]').click(function(){
    	var parent = $($(this).parent('span')).parent('[id$=\\.fileSelect]');
    	/* ファイルコントロール部品内のクリックイベントであることを判定(ここではspanの親が.fileSelectであることで判定) */
    	if (parent[0]) {
	    	parent.find('[id$=\\.file]').click();
    	}
    });
});

/**
 * ファイルコントロール部品のファイル選択内容反映処理
 * @return {void}
 * 
 */
$(function(){
    $('[id$=\\.file]').change(function(){
    	var parent = $(this).parent('[id$=\\.fileSelect]');
    	/* ファイルコントロール部品内のクリックイベントであることを判定(ここでは直上の親が.fileSelectであることで判定) */
    	if (parent[0]) {
    		
    		var file_path = $(this).val();

    		/* ファイルパスから、ファイル名のみを取得して表示用項目に設定します */
    		if (file_path.length > 0) {
    			$($(this).siblings('div')).find('[id$=\\.fileDummy]').val(file_path.substring(file_path.lastIndexOf('\\')+1, file_path.length));
    		} else {
    			$($(this).siblings('div')).find('[id$=\\.fileDummy]').val(file_path);
    		}

    	}
    });
});
