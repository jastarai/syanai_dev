/**
 * @fileOverview headersize.js
 *
 * @author 日本システム技術株式会社
 * @version 1.0.0
 * @supported　InternetExploer11
 * @license 2016 Copyright © Japan System Techniques Co., Ltd. All Rights Reserved.
 */

/**
 * ヘッダ固定・サイズ設定
 * @return {void}
 * @example
 * 
 */
$(document).ready(function() {

	// ヘッダ固定、サイズ設定 
 	var width = $(".cs-work").outerWidth();
 	$(".cs-header").css("width",width);
 	$(window).resize(function(){
 		var width = $(".cs-work").outerWidth();
 		$(".cs-header").css("width",width);
 	});

});