$(function(){
	// 住所検索ボタン 初期非活性制御
	var addbtnflg = isDisableAddessbtn();
	$('.cs-addressSearch').prop("disabled", addbtnflg);

	// 番号検索ボタン 初期非活性制御
	var postCodebtnflg = isDisablePostCodebtn();
	$('.cs-postCodeSearch').prop("disabled", postCodebtnflg);

	// ①住所検索ボタン押下時イベント
	$('.cs-addressSearch').click(function(){

		var postCode = $(".cs-postCode").val();// 郵便番号

		// モーダル画面呼出し.
		var url = encodeURI("kinou08/addressSearch?searchtype=address&postCode=" + postCode);
		var result = modal(url, "large");

		// 各項目へ値設定　(0:postCode, 1:fuken, 2:shikuchoson, 3:chouiki
		$('.cs-fuken option').each(function(){
			if($(this).text() === result[1]) {
				$('.cs-fuken').val($(this).val());
			}
		});
		$(".cs-shikuchoson").val(result[2]);
		$(".cs-chouiki").val(result[3]);

	});

	// ②番号検索ボタン押下時イベント
	$('.cs-postCodeSearch').click(function(){
		var fuken = $(".cs-fuken").find(":checked").text();// 府県
		var shikuchoson = $(".cs-shikuchoson").val();// 市区町村
		var chouiki = $(".cs-chouiki").val();// 町域

		// モーダル画面呼出し.
		var url = encodeURI("kinou08/addressSearch?searchtype=postCode&fuken="
					+ fuken + "&shikuchoson=" + shikuchoson + "&chouiki=" + chouiki);
		var result = modal(url, "large");

		// 各項目へ値設定　(0:postCode, 1:fuken, 2:shikuchoson, 3:chouiki
		$(".cs-postCode").val(result[0]);
		$(".cs-chouiki").val(result[3]);
	});

	// 郵便番号 KeyUpイベント
	$('.cs-postCode').keyup(function(){
		$('.cs-addressSearch').prop("disabled",
				isDisableAddessbtn());
	});

	// 市区町村 KeyUpイベント
	$('.cs-shikuchoson').keyup(function(){
		$('.cs-postCodeSearch').prop("disabled", 
				isDisablePostCodebtn());
	});
	// 府県changeイベント
	$('.cs-fuken').change(function(){
		$('.cs-postCodeSearch').prop("disabled", 
				isDisablePostCodebtn());
	});
	
});

function isDisableAddessbtn() {
	if($('.cs-postCode').val().length == 7){
		return false;
	}
	return true;
}

function isDisablePostCodebtn() {
	if(!($('.cs-fuken').val() == "")
			&& !($('.cs-shikuchoson').val() == "")){
		return false;
	}
	return true;
}
