/**
 * @fileOverview sidemenu.js
 *
 * @author 日本システム技術株式会社
 * @version 1.0.0
 * @supported　InternetExploer11
 * @license 2016 Copyright © Japan System Techniques Co., Ltd. All Rights Reserved.
 */

/**
 * サイドメニュープロパティ設定
 * @return {void}
 * @example
 * 
 */
$(document).ready(function() {

 	// サイドメニュー設定
 	$('#cs-left-menu').sidr({
 		name: 'left-sidr',
 		side: 'left'
 	});
 	$('#cs-side-menu-link').sidr({
 		name: 'left-sidr',
 		side: 'left'
 	});

 	// メニュートグル設定 
 	// アコーディオンのアイコン切替.
	$("a[data-name='menuAccordion']").on('click', function() {
		// <a>タグをクリックした瞬間のイベントから、次のパネル状態を判断する.
		if($(this).attr('aria-expanded') == 'true'){
			// 開いている場合(パネルは閉じるので、マイナスアイコンを設定する）
			$(this).find('span.glyphicon-minus').removeClass('glyphicon-minus').addClass('glyphicon-plus');
		}else{// 閉じている場合（パネルは開くので、プラスアイコンを設定する）.
			$(this).find('span.glyphicon-plus').removeClass('glyphicon-plus').addClass('glyphicon-minus');
		}
		
	});
	
	// ハッシュリンクキャンセル
	$("a[data-name='menuAccordion']").on('click', function(event) {
 		event.preventDefault();
 	}); 	
 });
/**
 * サイドメニュー操作
 * @return {void}
 * @example
 * 
 */
$(function() {
	$('#cs-side-menu').on('click',function(){
		if($('#cs-side-menu').hasClass('off')){
			$('#cs-side-menu').removeClass('off');
			$(this).animate({'marginLeft':'260px'},500).addClass('on');
			$(this).find('span.glyphicon-forward').removeClass('glyphicon-forward').addClass('glyphicon-backward');
		}else{
			$('#cs-side-menu').addClass('off');
			$(this).animate({'marginLeft':'0px'},300);
			$(this).find('span.glyphicon-backward').removeClass('glyphicon-backward').addClass('glyphicon-forward');
		}
	});
	
	$('#cs-left-menu').on('click',function(){
		if($('#cs-side-menu').hasClass('off')){
			$('#cs-side-menu').removeClass('off');
			$('#cs-side-menu').animate({'marginLeft':'260px'},500).addClass('on');
			$('#cs-side-menu').find('span.glyphicon-forward').removeClass('glyphicon-forward').addClass('glyphicon-backward');
		}else{
			$('#cs-side-menu').addClass('off');
			$('#cs-side-menu').animate({'marginLeft':'0px'},300);
			$('#cs-side-menu').find('span.glyphicon-backward').removeClass('glyphicon-backward').addClass('glyphicon-forward');
		}
	});
});
