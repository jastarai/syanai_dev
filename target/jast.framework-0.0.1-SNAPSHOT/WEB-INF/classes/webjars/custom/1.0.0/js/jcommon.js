/**
 * @fileOverview jcommon.js
 *
 * @author 日本システム技術株式会社
 * @version 1.0.0
 * @supported　InternetExploer11
 * @license 2016 Copyright © Japan System Techniques Co., Ltd. All Rights Reserved.
 */


/**
 * アコーディオン開閉時のアイコン切替
 * @return {void}
 * @example
 * 
 */
$(function(){
	$("a[data-name='accordion']").on('click', function(event) {
		// ハッシュリンクキャンセル.
		event.preventDefault();
		
		// <a>タグをクリックした瞬間のイベントから、次のパネル状態を判断する.
		if($(this).attr('aria-expanded') == 'true'){
			// 開いている場合(パネルは閉じるので、マイナスアイコンを設定する）
			$(this).find('span.glyphicon-minus-sign')
			.removeClass('glyphicon-minus-sign').addClass('glyphicon-plus-sign');
		}else{// 閉じている場合（パネルは開くので、プラスアイコンを設定する）.
			$(this).find('span.glyphicon-plus-sign')
			.removeClass('glyphicon-plus-sign').addClass('glyphicon-minus-sign');
		}
	});
	
});


/**
 * ページトップボタン操作
 * @return {boolean} false：ボタン押下しない場合
 * @example
 * 
 */
$(function() {
    var topBtn = $('#cs-page-top');
    topBtn.hide();
    // 500px以上スクロールでページトップボタン表示
    $(window).scroll(function () {
        if ($(this).scrollTop() > 500) {
            topBtn.fadeIn();
        } else {
            topBtn.fadeOut();
        }
    });
    // ページトップボタン押下時
    topBtn.click(function () {
        $('body,html').animate({
            scrollTop: 0
        }, 500);
        return false;
    });
});

/**
 * ページダウンボタン操作
 * @return {boolean} false:ページダウンボタン押下時
 * @example
 * 
 */
$(function() {
    var downBtn = $('#cs-page-down');
    var h = window.innerHeight;
    downBtn.hide();
    // ウィンドウ最下部でページダウンボタン表示
    $(window).scroll(function () {
    	var scrollHeight = $(document).height(); 
    	var scrollPosition = $(window).height() + $(this).scrollTop(); 
        if ((scrollHeight - scrollPosition) / scrollHeight === 0 ) {
        	downBtn.fadeOut();
        }
        else if($(this).scrollTop()  > 0) {
        	downBtn.fadeIn();
        }
    });
    // ページダウンボタン押下時
    downBtn.click(function () {
        $('body,html').animate({
            scrollTop: $(document).height()
        }, 650);
        return false;
    });
});


/**
 * クリアボタン操作
 * @return {void}
 * @example
 * 
 */
$(function(){
	$("button[name = clearButton]").click(function(){
		// 親要素「panel-primary」で囲まれている入力エリアの内容が完全クリアされる.
		$(this).parents('.panel-primary').find("textarea, :text, select").val("").end().find(":checked").prop("checked", false);

	});
});

/**
 * datetimepickerプロパティ設定
 * @return {void}
 * @example
 * 
 */
$(function () {
	// 以下のクラス指定により、カレンダーの入力形式を「yy」に設定
	$('.cs-YY').datetimepicker({
		locale: 'ja',
		format : 'YY'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「yyyy」に設定
	$('.cs-YYYY').datetimepicker({
		locale: 'ja',
		format : 'YYYY'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「yyyy/MM」に設定
	$('.cs-YYYY-MM').datetimepicker({
		locale: 'ja',
		format : 'YYYY/MM'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「yyyyMM」に設定
	$('.cs-YYYYMM').datetimepicker({
		locale: 'ja',
		format : 'YYYYMM'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「MM/dd」に設定
	$('.cs-MM-DD').datetimepicker({
		locale: 'ja',
		format : 'MM/DD'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「MMdd」に設定
	$('.cs-MMDD').datetimepicker({
		locale: 'ja',
		format : 'MMDD'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「yyyy/MM/dd」に設定
	$('.cs-YYYY-MM-DD').datetimepicker({
		locale: 'ja',
		format : 'YYYY/MM/DD'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「yyyy/MM/dd」に設定
	$('.cs-YYYYMMDD').datetimepicker({
		locale: 'ja',
		format : 'YYYYMMDD'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「yyyy/MM/dd HH:mm:ss」に設定
	$('.cs-YYYY-MM-DDHHmmss').datetimepicker({
		locale: 'ja',
		format : 'YYYY/MM/DD HH:mm:ss'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「HH:mm」に設定
//	$('#cs-HHmm').datetimepicker({
//		locale: 'ja',
//		format : 'HH:mm'
//	});
	$('.cs-HHmm').datetimepicker({
		locale: 'ja',
		format : 'HH:mm'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「yy年」に設定
	$('.cs-Ja-YY').datetimepicker({
		locale: 'ja',
		format : 'YY年'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「yyyy年」に設定
	$('.cs-Ja-YYYY').datetimepicker({
		locale: 'ja',
		format : 'YYYY年'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「yyyy年MM月」に設定
	$('.cs-Ja-YYYY-MM').datetimepicker({
		locale: 'ja',
		format : 'YYYY年MM月'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「MM月dd日」に設定
	$('.cs-Ja-MM-DD').datetimepicker({
		locale: 'ja',
		format : 'MM月DD日'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「yyyy年MM月dd日」に設定
	$('.cs-Ja-YYYY-MM-DD').datetimepicker({
		locale: 'ja',
		format : 'YYYY年MM月DD日'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「yyyy年MM月dd日 HH時mm分ss秒」に設定
	$('.cs-Ja-YYYY-MM-DDHHmmss').datetimepicker({
		locale: 'ja',
		format : 'YYYY年MM月DD日 HH時mm分ss秒'
	});
	// 以下のクラス指定により、カレンダーの入力形式を「HH時mm分」に設定
	$('.cs-Ja-HHmm').datetimepicker({
		locale: 'ja',
		format : 'HH時mm分'
	});
});

/**
 * モーダルダイアログ表示
 * @return {void}
 * @param {string} url URL
 * @param {string} size サイズ文字列（large, middle, small)
 * @param {obg} value 画面情報（未設定の場合はwindowオブジェクト）
 * @example
 * 
 */
function modal(url, size, value){
	
	var width;
	var height;
	var obj = this;
	switch (size){
		case 'large':
			width = '900px';
			height = '700px';
			break;
		case 'middle':
			width = '750px';
			height = '550px';
			break;
		case 'small':
			width = '600px';
			height = '350px';
			break;
		default:
			width = '450px';
			height = '300px';
			break;
	}
	
	if (value != null && value != '') {
		obj = value;
	}
	// オーバーレイ要素の表示
	var overlayOpacity = 0.7;// 背景の透過度.
	var fadeTime = 0;// フェード時間 ※モーダル画面表示までの時間よりも大きな値で設定すると、モーダル画面表示までにオーバーレイが表示できなくなります.
	var wdHeight = $(window).height();// ウィンドウの高さ
	$('body').append('<div id="cs-modal-Overlay"></div>');// ページ末尾にオーバーレイ要素DIVを追加する.
	$('#cs-modal-Overlay').css({display:'block', opacity:'0'});// オーバーレイのスタイルを有効にし、透過率を0設定.
	$('#cs-modal-Overlay').css({height:wdHeight}).stop().animate({opacity:overlayOpacity}, fadeTime);// オーバーレイ要素を表示.
	$(window).on('resize', function(){
		var adjHeight = $(window).height();
		$('#cs-modal-Overlay').css({height: adjHeight});
	});
	
	// モーダル起動
	var retModal = window.showModalDialog(
		url,   //移動先
		obj,  //ダイアログに渡すパラメータ（この例では、自分自身のwindowオブジェクト）
		"dialogWidth:" + width + ";"
		+ "dialogHeight:" + height + ";"
		+ "resizable:0;"
		+ "scroll:1;"
	);
	
	// オーバーレイ要素を削除する(モーダル画面を閉じた後).
	$('#cs-modal-Overlay').stop().animate({opacity:'0'}, fadeTime, function(){
		$('#cs-modal-Overlay').remove();
	});
	
	return retModal;
}

/**
 * NowLoading表示
 * @return {void}
 * @example
 * 
 */
jQuery(window).load(function(){
	jQuery("#cs-nowloading").hide();
});

/**
 * スムーススクロール操作
 * @return {false} false:ナビゲーション押下時
 * @example
 * 
 */
$(function() {
	//対象：[cs-smooth-]から始まるもの
	//対象外：「#」のみ
	$('a[href*="#cs-smooth-"]:not([href="#"])').click(function(){
		//移動先のコンテンツの位置を取得
		var target = $($(this).attr('href')).offset().top;
		// ヘッダ位置を考慮（プロジェクト別に値は異なる）
		target -= 70;
		// コンテンツへスクロール(固定値はスクロール速度)
		$('html,body').animate({scrollTop: target},500);
		return false;
	});
	
});

/**
 * ツールチップ表示
 * @return {void} 
 * @example
 * 
 */
$(function () {
	// 前提事項:ツールチップを表示させたい要素のタグ内にdata-toggle="tooltip"を設定が必要
	$('[data-toggle="tooltip"]').tooltip();
})

/**
 * リッチテキスト編集ボタン操作
 * @return {void}
 * @param {obg} editButton windowオブジェクト
 * @example
 * 
 */
var richEdit = function(editButton) {
	$('div[data-name = ' + editButton.name + ']').summernote({focus: true});
};

/**
 * リッチテキスト確定ボタン操作
 * @return {void}
 * @param {obg} saveButton windowオブジェクト
 * @example
 * 
 */
var richSave = function(saveButton) {
	var markup = $('div[data-name = ' + saveButton.name + ']').summernote('code');
	$('div[data-name = ' + saveButton.name + ']').summernote('destroy');
};

/**
 * summernoteプロパティ設定
 * @return {void}
 * @example
 * 
 */
$(function () {
	// 日本語化
	$.summernote.options.lang = "ja-JP";
});

/**
 * 入力形式補助（カンマ、スラッシュ）制御
 * @return {void}
 * @example
 */
jQuery(window).load(function(){
	// 初期処理
	// カンマ編集(input)
	$('.cs-comma').each(function() {
		var num = $(this).val();
		var obj = $(this);
		setComma(num, obj, "typeVal");
	});

	// カンマ編集(text)
	$('.cs-comma-text').each(function() {
		var num = $(this).text();
		var obj = $(this);
		setComma(num, obj, "typeText");
	});

	// 日付編集
	$('.cs-slash').each(function() {
		var date = $(this).val();
		var obj = $(this);
		setSlash(date, obj, "typeVal");
	});

	// 日付編集(text)
	$('.cs-slash-text').each(function() {
		var date = $(this).text();
		var obj = $(this);
		setSlash(date, obj, "typeText");
	});

	// .comma　フォーカスアウト
	$('.cs-comma').on('blur', function(){
		var num = $(this).val();
		var obj = $(this);
		setComma(num, obj, "typeVal");
	});

	// .comma　フォーカス
	$('.cs-comma').on('focus', function(){
		var num = $(this).val();
		num = num.replace(/,/g, '');
		setVal(num, $(this));
	});

	// .cs-comma-text 変更時
	$('.cs-comma-text').on('DOMSubtreeModified propertychange', function() {
		var num = $(this).text();
		var obj = $(this);

		if ($.isNumeric(num) && parseInt(num).toString().length >= 4) {
			setComma(num, obj, "typeText");
		}
	});

	// .slash　フォーカスアウト
	$('.cs-slash').on('blur', function(){
		var date = $(this).val();
		var obj = $(this);
		setSlash(date, obj, "typeVal");
	});

	// .slash　フォーカス
	$('.cs-slash').on('focus', function(){
		var date = $(this).val();
		date = date.replace(/\//g, '');
		setVal(date, $(this));
	});

	// .cs-slash-text 変更時
	$('.cs-slash-text').on('DOMSubtreeModified propertychange', function() {
		var date = $(this).text();
		var obj = $(this);
		if ($.isNumeric(date)) {
			if (date.length == 8 || date.length == 6) {
				setSlash(date, obj, "typeText");
			}
		}
	});

	function setComma(num, obj, type){
		num = num.replace(/^(-?[0-9]+)(?=\.|$)/, function(s){ return s.replace(/([0-9]+?)(?=(?:[0-9]{3})+$)/g, '$1,');});
		if(type == "typeVal") {
			setVal(num, obj);
		} else if (type == "typeText") {
			setText(num, obj);
		}
	}

	function setSlash(date, obj, type){
		if(date.length == 8){
			date = date.substr(0,4) +'/'+ date.substr(4,2) + '/' +date.substr(6,2);
		} else if (date.length == 6) {
			date = date.substr(0,4) +'/'+ date.substr(4,2)
		}

		if(type == "typeVal") {
			setVal(date, obj);
		} else if (type == "typeText") {
			setText(date, obj);
		}
	}

	function setVal(val, obj) {
		$(obj).val(val);
	}

	function setText(val, obj) {
		$(obj).text(val);
	}
});

/**
 * モードレスウィンドウ表示
 * @return {void}
 * @param {string} url URL
 * @param {string} size サイズ文字列（large, middle, small)
 * @param {string} targetName ウィンドウターゲット名(''の場合は、_blankになる)
 * @example
 */
function modeless(url, size, targetName) {
	var width;
	var height;
	var target;

	switch (size){
		case 'large':
			width = '900px';
			height = '700px';
			break;
		case 'middle':
			width = '750px';
			height = '550px';
			break;
		case 'small':
			width = '600px';
			height = '350px';
			break;
		default:
			width = '450px';
			height = '300px';
			break;
	}
	
	if (targetName == null || targetName == '') {
		targetName = 'default'
	}
	// ターゲット指定で特殊な指定をできなくする.
	switch (targetName){
		case '_self':
			target = 'default'
			break;
		case '_blank':
			target = 'default'
			break;
		case '_top':
			target = 'default'
			break;
		case '_parent':
			target = 'default'
			break;
		default:
			target = targetName;
			break;
	}
	
	var newWin = window.open(
			url,
			target,
			'width=' + width + ', height=' + height + ''
	);
	newWin.Focus();
}

/**
 * 非活性⇒活性変更後Submit
 * @return {void}
 * @param {string} url URL
 * @param {string} strForm Form名
 * @example
 */
var changeDisabledBeforeSubmit = function(url, strForm) {
	try {
		$("*").each(function(){
			if ($(this).is(':disabled') === true ) {
				$(this).prop("disabled", false);
			}
		});

	} catch (e) {

	} finally {
		document.getElementById(strForm).action=url;
		document.getElementById(strForm).submit();
	}
}

/**
 * カルーセルの初期表示設定.
 * @return {void}
 * @param {string} carouselId カルーセルのID
 */
var carouselInit = function(carouselId){
	// カルーセルの1ページ目の要素に「active」クラスを追加する（初期表示処理）.
	$(carouselId + ' > .carousel-indicators').children('li').eq(0).attr('class', 'active');// リンク
	$(carouselId + ' > .carousel-inner').children('div').eq(0).attr('class', 'item active');// ページ
	
	// カルーセルのリンク表示箇所に連番を埋め込む.
		var liCnt = $(carouselId + ' > .carousel-indicators').children('li').length;

		for(var i = 0; i < liCnt; i++){
			// data-slide-to属性（○のリンク設定）に連番（0～）を設定する.
			$(carouselId + ' > .carousel-indicators').children('li').eq(i).attr('data-slide-to', i);
		}
}

/**
 * カルーセルのリンク押下時.
 * @return {void}
 * @example
 */
$('a[class = cs-carousel-link]').click(function(){
	var wdHeight = $(window).height();
	
	if($(this).children('img').eq(0).attr('name') != ''){// 画像情報がない場合は何もしない.
		var overlayOpacity = 0.7;
		var fadeTime =500;
		$('body').append('<div id="cs-modal-Overlay"></div><div id="cs-carousel-mdWindow"><div class="cs-carousel-mdClose">&times;</div><div id="cs-carousel-contWrap"></div></div>');
		
		// クリックした画像のSRCをモーダルのimgタグに設定する.
		$('#cs-carousel-contWrap').append('<h2 id="cs-carousel-mdTitle"></h2><img id="cs-carousel-img-md"></img>');
		$('#cs-carousel-mdTitle').append($(this).children('img').eq(0).attr('name'));
		$('#cs-carousel-img-md').attr('src', $(this).children('img').eq(0).attr('src'));
		
		$('#cs-modal-Overlay, #cs-carousel-mdWindow').css({display: 'block', opacity: '0'});
		$('#cs-modal-Overlay').css({height:wdHeight}).stop().animate({opacity: overlayOpacity}, fadeTime);
		$('#cs-carousel-mdWindow').stop().animate({opacity:'1'}, fadeTime);
		// 画面リサイズ時、オーバーレイの高さを再設定.
		$(window).on('resize', function(){
			var adjHeight = $(window).height();
			$('#cs-modal-Overlay').css({height: adjHeight});
		});
		
		// カルーセルのモーダルウィンドウ閉じるボタン押下時
		$('.cs-carousel-mdClose').on('click', function(){
			$('#cs-carousel-mdWindow, #cs-modal-Overlay').stop().animate({opacity: '0'}, fadeTime, function(){
				$('#cs-carousel-mdWindow, #cs-modal-Overlay').remove();
			});
		});
	}
	
});


/**
 * アコーディオン「全て開く」ボタン押下時.
 * @return {void}
 */
var allPanelOpen = function(){
	// すでに閉じているアコーディオンのリンクをクリック.
	$('a[data-name = accordion]a[aria-expanded = false]').click();
	
}

/**
 * アコーディオン「全て閉じる」ボタン押下時.
 * @return{void}
 */
var allPanelClose = function(){
	// すでに開いているアコーディオンのリンクをクリック.
	$('a[data-name = accordion]a[aria-expanded = true]').click();
	
}