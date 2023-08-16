$(function(){
	// 検索ボタン押下時
	$('button[name=searchBtn]').click(function(){
		document.getElementById('mainForm').action = kinou11Path + '/favSearch';
		document.getElementById('mainForm').submit();
	});
	
});

// モーダル画面閉じた後に呼び出されるメソッド.
function favListInsertModalClose(retFavListNo){
	// 画面の再表示を行う.
	if(retFavListNo){
		$('input[name = favListNo]').val(retFavListNo);
		document.getElementById('mainForm').submit();
	}
}


//モーダル画面閉じた後に呼び出されるメソッド.
function favListUpdateModalClose(retFavListNo){
	// 画面の再表示を行う.
	if(retFavListNo){	
		$('input[name = favListNo]').val(retFavListNo);
		document.getElementById('mainForm').submit();
	}
}