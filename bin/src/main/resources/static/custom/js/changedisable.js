/**
 * 活性・非活性 表示・非表示　制御
 * @return {void}
 * @example
 */
$(function() {

	// 初期制御
	disableControl();

	// ラジオボタン・チェックボックス 変更時制御
	$(".disablecon").change(function() {
		disableControl();
	});

	// 非活性制御 コントロール
	function disableControl() {

		// 制御契機項目 ループ
		$(".disablecon").each(function(){
			// 活性(false)・非活性(true)フラグ
			var disFlg = false;
			if ($(this).is(':checked')){
				// ラジオ・チェックボックスがチェックされている場合、非活性とする。
				disFlg = true;
			}
			disableExe($(this).prop("id"), disFlg);
		});
	}

	// 非活性制御 実行
	function disableExe(disId, disFlg) {

		// 制御対象項目 ループ
		$("[class*='disablecon_']").each(function(){

			// 制御対象項目か判定
			if($(this).hasClass("disablecon_" + disId)) {

				if (isTabParent($(this)) && disFlg) {
					// tab項目の親要素(li)　非活性制御
					$(this).addClass("disabled");

				} else if(isTabParent($(this)) && !disFlg){ 
					// tab項目の親要素(li)　活性制御
					$(this).removeClass("disabled");

				} else {
					// tab項目の親要素(li)以外　制御
					$(this).prop("disabled", disFlg);
				}
			}
		});
	}

	// Tab項目の親要素(li)判定
	function isTabParent(obj) {

		if (obj.children().data("toggle") == "tab") {
			return true;
		}
		return false;
	}
});
