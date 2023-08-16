/**
 * @fileOverview thumbnail.js
 *
 * @author 日本システム技術株式会社
 * @version 1.0.0
 * @supported　InternetExploer11
 * @license 2016 Copyright © Japan System Techniques Co., Ltd. All Rights Reserved.
 */

/**
 * ファイル操作
 * @return {void}
 * @example
 * 
 */
// アップロードするファイルを選択
$("input[type='file'][name^='thumbnail']").change(function(event) {

	var eventTarget = $(event.target);
	var file = eventTarget.prop('files')[0];
	var targetDiv = eventTarget.closest("div[class='row']"); // イベント発生元のdiv(row)
	var selector = "img[name^='" + eventTarget.attr('name') + "']";// img要素選択のセレクタ
	var imgElement = targetDiv.find(selector); // div(row)配下のimg要素

	// 画像以外は処理を停止
	if (!file || !file.type.match('image.*')) {
		// クリア処理
		imgElement.removeAttr('src');
		return;
	}
	// 画像表示
	var reader = new FileReader();
	reader.onload = function(readEvent) {
		imgElement.attr('src', readEvent.target.result);
		// ファイルのパス設定はfilecontrol.js参照
	}
	reader.readAsDataURL(file);
});
/**
 * サムネイル操作
 * @return {false} false サムネイル押下時
 * @example
 * 
 */
$("a[class^='cs-thumbnail']").click(function(event){
	var currentTarget = $(event.currentTarget);
	var eventTarget = $(event.target);
	// 差し替えるimgの要素を直近から探す
	var replacer = currentTarget.closest("div[class='row']").find("figure img");
	// エラーイベントを受けた場合(HTMLで指定した実画像ファイルが存在しないなど)
	replacer.one('error', function(readEvent){
		var errorImg = eventTarget.attr('src'); // 画面でエラー画像をクリックした場合のsrcは「エラー画像(no_image)」
		replacer.attr("src", errorImg);
		return false;
	});
	// a要素のhref属性を取得
	replacer.attr("src", currentTarget.attr("href"));
	return false;
});
