$(function(){
	
    // Spring SecurityのCSRF対策によるトークンをajax通信で設定する 
    $.ajaxPrefilter(function(options, originalOptions, jqXHR) { 
      var token = $("meta[name='_csrf']").attr("content"); 
       var header = $("meta[name='_csrf_header']").attr("content"); 
       jqXHR.setRequestHeader(header, token); 
     }); 
	
	// モーダルボタン表示用「詳細」ボタン押下時イベント
	$('button[name = modalOpen]').click(function(){
		
		// 同一行のシーケンス番号とユーザIDを取得する.
		var $tr = $(this).parent().parent();// 選択ボタン行のTR要素.
		var seq = $tr.children().eq(1).html();// シーケンス番号
		var userId = $tr.children().eq(3).html();// ユーザID

		// モーダル画面呼出し.
		var url = encodeURI("kinou01/m01?seq=" + seq + "&userId=" + userId);
		var result = modal(url, "large");

		// モーダルの閉じるボタン押下後
		//document.getElementById(strForm).action='/kinou01';
		// モーダル画面からの戻り値がある（更新処理が実施後）の場合.
		if (result == "true") {
			// 親画面のリロード(スクロール位置の固定).
			hiddenAppend('scroll_x', $(window).scrollLeft());
			hiddenAppend('scroll_y', $(window).scrollTop());
			document.getElementById('mainForm').submit();
		}
	});
	

	
});

/**
 * お気に入り登録画面を閉じた後に呼び出されるfunction.
 * @param retFavSearchNo
 */
function favInsertModalClose(retFavSearchNo){
	if (retFavSearchNo) {
		// 画面の再表示を行う.
		$('input[name = favSearchNo]').val(retFavSearchNo);
		document.getElementById('mainForm').submit();
	}
}

/**
 * お気に入り更新画面を閉じた後に呼び出されるfunction.
 * @param retFavSearchNo
 */
function favUpdateModalClose(retFavSearchNo){
	if(retFavSearchNo){
		// 画面の再表示を行う.
		$('input[name=favSearchNo]').val(retFavSearchNo);
		document.getElementById('mainForm').submit();
	}
	
}

// カルーセルの初期表示設定呼出し.	
carouselInit('#sampleCarousel');