$(function(){
	
	// datetimepicker設定（日時形式）
	$('.datetime').datetimepicker({
		locale: 'ja',
		format : 'YYYY/MM/DD HH:mm:ss'
	});
	
});

function modalOpen() {
	
//	// 同一行のシーケンス番号とユーザIDを取得する.
//	var $tr = $(this).parent().parent();// 選択ボタン行のTR要素.
//	var seq = $tr.children().eq(1).html();// シーケンス番号
//	var userId = $tr.children().eq(3).html();// ユーザID

	// モーダル画面呼出し.
//	var url = encodeURI("pod010v012.html?seq=" + seq + "&userId=" + userId);
	var url = encodeURI("pod010v012.html");
	var result = modal(url, "middle");

	// モーダルの閉じるボタン押下後
	//document.getElementById(strForm).action='/kinou01';
	// モーダル画面からの戻り値がある（更新処理が実施後）の場合.
	if (result == "true") {
		// 親画面のリロード(スクロール位置の固定).
		hiddenAppend('scroll_x', $(window).scrollLeft());
		hiddenAppend('scroll_y', $(window).scrollTop());
		document.getElementById('mainForm').submit();
	}

}
