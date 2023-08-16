/**
 * @fileOverview changestrong.js
 *
 * @author 日本システム技術株式会社
 * @version 1.0.0
 * @supported　InternetExploer11
 * @license 2016 Copyright © Japan System Techniques Co., Ltd. All Rights Reserved.
 */

/**
 * 変更箇所強調
 * @return {void}
 * @example
 * 
 */
$(function() {

	// テキストフィールド
	$('input[type="text"]').change(function() {
		$(this).css("border-color","#ff0000");
	});
 
	// テキストエリア
	$('textarea').change(function() {
		$(this).css("border-color","#ff0000");
	});
	
	// パスワード
	$('input[type="password"]').change(function() {
		$(this).css("border-color","#ff0000");
	});
 
	// ラジオボタン
	$('input[type="radio"]').change(function() {
		$("input:checked").parent().css("border-bottom","0.5px solid red");
		$("input:not(':checked')").parent().css("border-bottom","0px");		
	});
 
	// チェックボックス
	$('input[type="checkbox"]').click(function() {
		$("input:checked").parent().css("border-bottom","0.5px solid red");
// 		$("input:not(':checked')").parent().css("border-bottom","0px");
	});
 
	// セレクトボックス
	$('select').change(function() {
		$(this).css("border-color","#ff0000");
	});
	
	// ファイル
	$('input[type="file"]').change(function() {
		$(this).css("border-color","#ff0000");
	});
	
	// カレンダー
	$('.cs-calender').blur(function() {
		$(this).css("border-color","#ff0000");
	});

	// 和暦（年号）
	$('#cs-birth-era').change(function() {
		$(this).css("border-right","0px");
	});
	
	// 和暦（年）
	$('#cs-birth-year').change(function() {
		$(this).css("border-left","0px");
	});
	
});
