// お気に入り機能（検索結果）の親画面側の処理Javascript.
// お気に入り一覧の制御と、お気に入り画面の表示処理制御を行う.
$(function(){
	
	// お気に入り保存ボタン押下時.
	$('button[name = favListInsertButton]').on('click', function(){
        // モーダル画面呼出し処理.
    	var gamenId = $('input[type=hidden][name=gamenId]').val();
		var url = encodeURI(favListPath + "/init001?gamenId=" + gamenId);
		returnFavNo = modal(url, "large");
		// モーダル画面閉じるボタン押下後(各画面に定義されているfunction呼び出し)
        favListInsertModalClose(returnFavNo);
	});
	
	// お気に入り編集ボタン押下時.
	$('button[name = favListUpdateButton]').on('click', function(){
        // モーダル画面呼出し処理.
		var favNo = $('input[type="hidden"][name=favListNo]').val();
    	var gamenId = $('input[type=hidden][name=gamenId]').val();

		var url = encodeURI(favListPath + "/init003?favListNo=" + favNo + "&gamenId=" + gamenId);
		returnFavNo = modal(url, "large");
		// モーダル画面閉じるボタン押下後(各画面に定義されているfunction呼び出し)
        favListInsertModalClose(returnFavNo);
	});
	
	// お気に入り一覧の選択時
	$('a[class = cs-fav-list-href]').on('click', function(event){
		// ハッシュリンクキャンセル
		event.preventDefault();

		var selectFavListNo = $(this).attr('id').replace( /cs-fav-list-no-/g , '');
		
		// テキストボックスにお気に入り名称を設定する.
		// 活性⇒値の設定⇒非活性　にする.
		$('input[name = favListNameSelect]').prop('disabled', false);
		$('input[name = favListNameSelect]').val($(this).children('span').html());
		$('input[name = favListNameSelect]').prop('disabled', true);
		// hidden項目に選択されたfavIDを設定する.
		$('input[type="hidden"][name="favListNo"]').val(selectFavListNo);
		
		// [お気に入り]お気に入り名称が選択されていない場合は編集ボタンを非活性にする.		
		if($(this).html()){
			$('button[name = favListUpdateButton]').prop('disabled', false);			
		}else{
			$('button[name = favListUpdateButton]').prop('disabled', true);			
		}
		
	});
	
	// クリアボタン押下後、お気に入り編集ボタンを非活性にする.
	$("button[name = clearButton]").click(function(){
		$("button[name = favListUpdateButton]").attr("disabled", true);
	});

});

//【お気に入り機能】画面ロード後.お気にいり番号を選択する.
$(window).on('load',function(){
	var favListNo = $('input[name = favListNo]').val();
	if(favListNo){
		$('a[class = cs-fav-list-href][id = cs-fav-list-no-' + favListNo + ']').click();
	}
});