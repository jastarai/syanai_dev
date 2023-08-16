/**
 * 表示・非表示　制御
 * @return {void}
 * @example
 */
$(function() {

	// 初期制御
	displayControl();

	// ラジオボタン・チェックボックス 変更時制御
	$(".displaycon").change(function() {
		displayControl();
	});

	// Tab項目の親要素(li)判定
	function isTabParent(obj) {

		if (obj.children().data("toggle") == "tab") {
			return true;
		}
		return false;
	}

	// 非表示制御 コントロール
	function displayControl() {
		// 制御契機項目 ループ
		$(".displaycon").each(function(){
			// 表示()・非表示()フラグ
			var disFlg = false;
			if ($(this).is(':checked')){
				// ラジオ・チェックボックスがチェックされている場合、非表示とする。
				disFlg = true;
			}
			displayExe($(this).prop("id"), disFlg);
		});
	}

	// 非表示制御 実行
	function displayExe(disId, disFlg) {
		// 制御対象項目 ループ
		$("[class*='displaycon_']").each(function(){
			// 制御対象項目か判定
			if($(this).hasClass("displaycon_" + disId)) {
				if (disFlg) {
					// 非表示制御
					$(this).hide();
				} else {
					// 表示制御
					$(this).show();
				}
			}
		});
	}
});
