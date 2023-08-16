/**

 * @fileOverview resizeimage.js
 *
 * @author 日本システム技術株式会社
 * @version 1.0.0
 * @supported　InternetExploer11
 * @license 2016 Copyright c Japan System Techniques Co., Ltd. All Rights Reserved.
 */

//==================================================================== Common Functions Start //
////// ▼▽▼▽▼▽▼▽ 共通部 ▽▼▽▼▽▼▽▼▽▼ //////
//==================================================================== Global Members //
var _originalImageFile; // グローバル(元画像ファイル)
// ★各種イベントハンドラ ============================================ Some EventHandler Function Start //
/**
 * 変更イベントハンドラ(input(file))
 * 
 */
$("input[type='file']").change(function(event){
	loadImage(event);
});
/**
 * ボタンイベントハンドラ
 * 
 */
$("button").click(function(event){
	if(isMatched(event.target.name, '^doResize.*')){
		imageResize(event);
		event.stopPropagation();
	}
	else if(isMatched(event.target.name, '^doSave.*')){
		resizeSave(event);
		event.stopPropagation();
	}
	else if(isMatched(event.target.name, '^close.*')){
		// 「タブは表示中のWebページにより～～はい、いいえ」メッセージを出さずに閉じる。
		window.open('about:blank','_self').close();
		event.stopPropagation();
	}
});
//==================================================================== Some Utility Function Start //
/**
 * 正規表現マッチング
 * 
 */
function isMatched(value, pattern){
	var regexp = new RegExp(pattern);
	return regexp.test(String(value));
}
////// ▲△▲△▲△▲△ 共通部 ▲△▲△▲△▲△▲△ //////
//==================================================================== Common Functions End //
/**
 * ファイル選択された画像ファイルを読み込み、画面(HTML)へ反映する
 * 
 */
function loadImage(event){
	// ファイルを読み込む
	readFile(event);
	if (!_originalImageFile || _originalImageFile.length == 0){
		// ファイルが指定されていなければ処理を終える
		return;
	}

	var imgElement = getImageElement(event);
	// img要素へ画像が読み込まれたら実行される(注意：oneにしないとイベントハンドラが何度も追加され何度もコールされる)
	$(imgElement).one('load', function(readEvent){
		var sizeElements = $(imgElement).closest("div[class*='cs-work']").find("td[data-name^='file_size']");
		// base64変換後のデータサイズを指定の要素へ表示
		showFileSize(imgElement, sizeElements);
	});
	// 画像ファイルをimg要素へ設定し、添付欄にbase64基準におけるデータサイズを表示する
	attachImageFile(imgElement);
}
/**
 * ファイルの読み込み
 * 
 */
function readFile(event){
////// ===▽▼ 初期処理 ================= //////
	// サイズ情報をクリア
	var imgElement = getImageElement(event);
	imgElement.removeAttr('src');
	imgElement.removeData('filename');
	var sizeElements = imgElement.closest("div[class*='cs-work']").find("td[data-name^='file_size'],[data-name^='resize']");
	showFileSize(imgElement, sizeElements);
////// ===△▲ 初期処理 ================= //////

	var files = $(event.target).prop('files');
	// ファイルが指定されていなければ画面から画像をクリアして処理を終える
	if (files.length == 0) {
		_originalImageFile = undefined;
		return;
	}
	// 画像ファイルのみの配列
	_originalImageFile = new Array();
	$.each(files, function(index, file){
		if(isMatched(file.type, 'image.*')){
			// イメージ形式のファイルのみグローバルで保持しておく
			_originalImageFile.push(file);
		}
	});
}
/**
 * 画像ファイルのimgタグへの適用
 * 
 */
function attachImageFile(imgElement){

	var readIn = new FileReader();
	// FileReaderのonloadイベントハンドラ
	readIn.onload = function(readEvent){
		var fileName = _originalImageFile[0].name;
		// class属性が'resize-manual'のimg要素へ読み込んだ結果を適用する。
		// img要素へ画像と元ファイル名を設定する。
		var targetElement = $(imgElement);
		targetElement.attr('src', readEvent.target.result);
		targetElement.attr('data-filename', fileName);
	};
	// 画像ファイルを読み込みonloadコールバックを実行させる
	readIn.readAsDataURL(_originalImageFile[0]);
}
/**
 * ファイルサイズ表示
 * base64時のデータサイズを求め、引数指定の要素へ表示します.
 */
function showFileSize(imgElement, sizeElements){
	var srcElement = $(imgElement);
	$.each(sizeElements, function(index, target){
		var sizeElement = $(target);
		// srcがundef(クリア後)の場合
		if(!srcElement.attr('src')){
			sizeElement.text('');
			return true; // continueの代用
		}
		var dataSize = 0;
		var byteSize = new BigNumber(srcElement.attr('src').length);
		var bUnit = '';
		// ファイルサイズ(KB)
		if(isMatched(sizeElement.attr('data-name'), '.+kb$')){
			dataSize = byteSize.div(1024);
			bUnit = 'KB';
		}
		// ファイルサイズ(MB)
		else if(isMatched(sizeElement.attr('data-name'), '.+mb$')){
			dataSize = byteSize.div(Math.pow(1024,2));
			bUnit = 'MB';
		}
		// ファイルサイズ(Byte)
		else {
			dataSize = byteSize;
			bUnit = 'byte';
		}
		sizeElement.text(dataSize.round(2, BigNumber.ROUND_CEIL).toNumber());
		//element.text(dataSize.round(2, BigNumber.ROUND_CEIL).toNumber() + ' (' + bUnit + ')');
	});
}
/**
 * 画像リサイズ
 * 
 */
function imageResize(event){
	// 縮小率を取得
	var rateInput = $(event.target).closest("tr:has(input[type='text'])").find("input[type='text']");
	if($.isNumeric(rateInput.val()) && (rateInput.val() <= 100)){
		// img要素(class:resize-manual)を取得
		var imgElement = getImageElement(event);
		
		// ファイルが指定されていなければ処理を終える
		if(!_originalImageFile || _originalImageFile.length == 0){ return; }
		// 添付指定した元画像をキャンバスへ転記する際のテンポラリ
		var tmpImg = new Image();
		// tmpImgへ画像が読み込まれたら実行される(注意：oneにしないとイベントハンドラが何度も追加され何度もコールされる)
		$(tmpImg).one('load', function(readEvent){
			// 元画像のサイズを基準に、画面入力された縮小率でデータをリサイズして画面へ反映する
			var sizeElements = $(imgElement).closest("div[class*='cs-work']").find("td[data-name^='resize']");
			var resizeRate = (rateInput.val() <= 0) ? 1 : rateInput.val(); // 縮小率に0以下が指定された場合は1%とする 
			imgElement.attr('src', getShrinkBase64(tmpImg, resizeRate));
			// base64変換後のデータサイズを指定の要素へ表示
			showFileSize(imgElement, sizeElements);
		});
		attachImageFile(tmpImg);
	}
}

/**
 * img要素のsrcを縮小率に沿ってbase64へ変換
 * 
 */
function getShrinkBase64(imgElement, resize_percent){
	// 縮小率が不正なら処理しない
	if(!jQuery.isNumeric(resize_percent)) return;
	// 出力形式はJPEGに固定
	var imageType = "image/jpeg";
//	var imageType = imgElement.dataset.mimetype;

	// canvas要素を生成
	var canvas = document.createElement('canvas');

	// canvas要素のコンテキスト取得
	var canvas_ctx = canvas.getContext('2d');
	canvas_ctx.clearRect(0, 0, imgElement.width, imgElement.height);

	// 元画像のサイズに縮小率を適用する
	canvas.width  = imgElement.width * resize_percent / 100;
	canvas.height = imgElement.height * resize_percent / 100;

	// キャンバスに描画する
	canvas_ctx.drawImage(imgElement, 0, 0, canvas.width, canvas.height);
	// 0.0 ～ 1.0(品質レベル(低←→高))
	var base64Data = canvas.toDataURL(imageType, 1.0);

	// キャンバスに描画した画像のbase64データを返却する。
	return base64Data;
}

/**
 * リサイズ後画像の保存
 * 
 */
function resizeSave(event){
	// ファイルが指定されていなければ処理を終える
	if(!_originalImageFile || _originalImageFile.length == 0){ return; }
	// img要素(class:resize-manual)を取得
	var imgElement = getImageElement(event);
	// ファイル添付時の処理でimg要素へセットしたファイル名を取得
	var fileName = imgElement.attr('data-filename');
	var fileExt  = ".jpg";

	var dataSrc = imgElement.attr('src');
	if(!isMatched(dataSrc, '^data.+;')){
		// base64でないsrc(初期表示時)はbase64へ変換
		var dataSrc = getShrinkBase64(imgElement, 100);
	}
	var imageType = getTypeFromBase64(dataSrc);
	// base64のヘッダ部を除去
	var base64Data = dataSrc.replace(/^.*,/, '');

	// base64データをデコードしてバイナリ変換する(これをしないと保存ファイルは破損状態となる)
	var decodedData = window.atob(base64Data);
	// バイトバッファへ代入
	var bf = new Uint8Array(decodedData.length);
	for (var i = 0; i < decodedData.length; i++) {
		bf[i] = decodedData.charCodeAt(i);
	}

	// ファイル名の生成
	if(!fileName){
		// ファイル名が無い場合
		fileName = $.now().toString() + ".jpg";
	}
	else {
		// ミリ秒を付与
		fileName = fileName.substring(0, fileName.lastIndexOf('.')) + '_' + $.now() + fileExt;
	}
	// バイトバッファでBlobを生成
	var blob = new Blob([bf.buffer], imageType);
	if(window.navigator.msSaveBlob){
		// IEの場合
		window.navigator.msSaveBlob(blob, fileName);
	}
	else{
		var elm_a = document.createElement("a");
		elm_a.href = URL.createObjectURL(blob);
		elm_a.target = '_blank';
		elm_a.download = fileName;
		elm_a.click();
		URL.revokeObjectURL();
	}
}
/**
 * mimeTypeの取得
 * 
 */
function getTypeFromBase64(base64src){
	var imageType = base64src.match(/[^data:].*(?=;)/);
	return imageType;
}
/**
 * image要素の取得
 * 
 */
function getImageElement(event){
	var divElement = $(event.target).closest("div[class*='cs-work']");
	// img要素(class:resize-manual)を取得
	return divElement.find("img[name='resize-manual']");
}
