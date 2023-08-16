$(function(){
	// 住所検索ボタン 初期非活性制御
	$("[class*='cs-addressSearch']").each(function(){
		var target = getTargetnum($(this), "cs-addressSearch");
		$(this).prop("disabled", isDisableAddressbtn(target));
	});

	// 番号検索ボタン 初期非活性制御
	$("[class*='cs-postCodeSearch']").each(function(){
		var target = getTargetnum($(this), "cs-postCodeSearch");
		$(this).prop("disabled", isDisablePostCodebtn(target));
	});

	// ①住所検索ボタン押下時イベント
	$("[class*='cs-addressSearch']").click(function(){

		var target = getTargetnum($(this), "cs-addressSearch");

		var postCode = $(".cs-postCode" + target).val();// 郵便番号

		// モーダル画面呼出し.
		var url = encodeURI(addressUrl + "/addressSearch?searchtype=address&postCode=" + postCode);

		var result = modal(url, "large");

		// 各項目へ値設定　(0:postCode, 1:fuken, 2:shikuchoson, 3:chouiki
		$('.cs-fuken' + target).children().each(function(){
			if($(this).text() === result[1]) {
				$('.cs-fuken' + target).val($(this).val());
			}
		});
		$(".cs-shikuchoson" + target).val(result[2]);
		$(".cs-chouiki" + target).val(result[3]);

	});

	// ②番号検索ボタン押下時イベント
	$("[class*='cs-postCodeSearch']").click(function(){

		var target = getTargetnum($(this), "cs-postCodeSearch");
		var fuken = $(".cs-fuken" + target).find(":checked").text();// 府県
		var shikuchoson = $(".cs-shikuchoson" + target).val();// 市区町村
		var chouiki = $(".cs-chouiki" + target).val();// 町域

		// モーダル画面呼出し.
		var url = encodeURI(addressUrl + "/addressSearch?searchtype=postCode&fuken="
					+ fuken + "&shikuchoson=" + shikuchoson + "&chouiki=" + chouiki);
		var result = modal(url, "large");

		// 各項目へ値設定　(0:postCode, 1:fuken, 2:shikuchoson, 3:chouiki
		$(".cs-postCode" + target).val(result[0]);
		$(".cs-chouiki" + target).val(result[3]);
	});

	// 郵便番号 KeyUpイベント
	$("[class*='cs-postCode']").keyup(function(){
		var target = getTargetnum($(this), "cs-postCode");
		$('.cs-addressSearch' + target).prop("disabled",
				isDisableAddressbtn(target));
	});

	// 市区町村 KeyUpイベント
	$("[class*='cs-shikuchoson']").keyup(function(){
		var target = getTargetnum($(this), "cs-shikuchoson");
		$('.cs-postCodeSearch' + target).prop("disabled", 
				isDisablePostCodebtn(target));
	});
	// 府県changeイベント
	$("[class*='cs-fuken']").change(function(){
		var target = getTargetnum($(this), "cs-fuken");
		$('.cs-postCodeSearch' + target).prop("disabled", 
				isDisablePostCodebtn(target));
	});
	
});

function isDisableAddressbtn(target) {
	if($('.cs-postCode' + target).val().length == 7){
		return false;
	}
	return true;
}

function isDisablePostCodebtn(target) {
	if(!($('.cs-fuken' + target).val() == "")
			&& !($('.cs-shikuchoson' + target).val() == "")){
		return false;
	}
	return true;
}

function getTargetnum(obj, target) {
	var tar = obj.attr("class").split(" ");

	var target = "";
	for(var i = 0; i < tar.length; i++){
		if(tar[i].match(target+"_")){
			target = tar[i].split("_")[1];
			return "_" + target;
		}
	}
	return target;
}