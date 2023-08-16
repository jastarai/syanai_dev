// お気に入り機能（検索結果）の親画面側の処理Javascript.
// お気に入り一覧の制御と、お気に入り画面の表示処理制御を行う.
$(function(){
	
	// お気に入り保存ボタン押下時.
	$('button[name = favListInsertButton]').on('click', function(){
        // モーダル画面呼出し処理.
    	var kinouId = $('input[type=hidden][name=kinouId]').val();
		var url = encodeURI(favListPath + "/01?kinouId=" + kinouId);
		returnFavNo = modal(url, "large");
		// モーダル画面閉じるボタン押下後(各画面に定義されているfunction呼び出し)
        favListInsertModalClose(returnFavNo);
	});
	
	// お気に入り編集ボタン押下時.
	$('button[name = favListUpdateButton]').on('click', function(){
        // モーダル画面呼出し処理.
		var favNo = $('input[type="hidden"][name=favListNo]').val();
    	var kinouId = $('input[type=hidden][name=kinouId]').val();

		var url = encodeURI(favListPath + "/02?favListNo=" + favNo + "&kinouId=" + kinouId);
		returnFavNo = modal(url, "large");
		// モーダル画面閉じるボタン押下後(各画面に定義されているfunction呼び出し)
        favListInsertModalClose(returnFavNo);
	});
	
	// お気に入り一覧の選択時
	$('a[class = cs-fav-list-href]').on('click', function(event){
		// ハッシュリンクキャンセル
		event.preventDefault();

		var selectFavName = $(this).attr('name');
		
		// テキストボックスにお気に入り名称を設定する.
		// 活性⇒値の設定⇒非活性　にする.
		$('input[name = favListNameSelect]').prop('disabled', false);
		$('input[name = favListNameSelect]').val($(this).children('span').html());
		$('input[name = favListNameSelect]').prop('disabled', true);
		// hidden項目に選択されたfavIDを設定する.
		$('input[type="hidden"][name="favListNo"]').val(selectFavName);
		
		// [お気に入り]お気に入り名称が選択されていない場合は編集ボタンを非活性にする.		
		if($(this).html()){
			$('button[name = favListUpdateButton]').prop('disabled', false);			
		}else{
			$('button[name = favListUpdateButton]').prop('disabled', true);			
		}
		
	});

});

//【お気に入り機能】画面ロード後.お気にいり番号を選択する.
$(window).on('load',function(){
	var favNo = $('input[name = favListNo]').val();
	if(favNo){
		$('a[class = cs-fav-list-href][name=' + favNo + ']').click();
	}
});