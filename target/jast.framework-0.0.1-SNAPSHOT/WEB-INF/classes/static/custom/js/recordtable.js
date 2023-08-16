/**
 * @fileOverview recordtable.js
 *
 * @author 日本システム技術株式会社
 * @version 1.0.0
 * @supported　InternetExploer11
 * @license 2016 Copyright c Japan System Techniques Co., Ltd. All Rights Reserved.
 */

//==================================================================== Common Functions Start //
////// ▼▽▼▽▼▽▼▽ 共通部 ▽▼▽▼▽▼▽▼▽▼ //////
//==================================================================== Global Members //
var _originalTableDataRow; // グローバル(フィルター用 - table(tr)単位)
var _filterKeysDictionary; // グローバル(フィルター用 - table(ul)単位)
var _currentFilterKeys;    // グローバル(フィルター用 - table(th)単位)
//==================================================================== Load, Submit Function Start //
/**
 * js読み込みタイミングで実行する処理
 */
$(function(){
	// テーブル縦集計
	calcAllVertical();
	// テーブル横集計
	calcAllHorizontal();
});
/**
 * DOMツリー構築時点で実行する処理
 */
window.addEventListener('DOMContentLoaded', function(event) {
	// 画面スクロール(画面側へ必ず_scroll_yの定義とTymeleafコードが必要)
	$(window).scrollTop(_scroll_y);
	// 初期表示時のフィルタ項目件数表示(filterテーブル対象)
	doFilterMain();
}, false);
/**
 * 表示データのすべての読み込み完了時点で実行する処理
 */
$(window).load(function(){
	showSortIcon();
});
/**
 * Submit送信時に実行
 */
$(window).submit(function(){
	// ブラウザのスクロール位置をhiddenへ設定
	hiddenAppend('scroll_x', $(window).scrollLeft());
	hiddenAppend('scroll_y', $(window).scrollTop());
});
/**
 * ページング用コマンド送信
 * 
 */
function sendPagecommand(formId, targetName) {
	var pageCmd = formId + '.pagingCmd';
	hiddenAppend(pageCmd, targetName);
	var forms = $('form');
	if(forms.length > 0){
		$(forms[0]).submit();
	}
}
/**
 * hidden要素の追加
 * 
 */
function hiddenAppend(name, value) {
	var elm  = document.createElement('input');
	elm.type = 'hidden';
	elm.name = name;
	elm.value= value;
	var forms = $('form');
	if(forms.length > 0){
		$(forms[0]).append(elm);
	}
}
// ★各種イベントハンドラ ============================================ Some EventHandler Function Start //
/**
 * マウススクロールイベントハンドラ
 *
 *
 */
$(function(){
	var isWheelScroll = [false, false];
	var wheelEvent = 'wheel';
	if(!window.WheelEvent){
		wheelEvent = !window.MouseWheelEvent ? 'mousewheel' : 'DOMMouseScroll';
	}
	$("div[id*='foot'],[id*='record']").on(wheelEvent, function(event){
		isWheelScroll = [true, true];
		var selectorAppendant = new Array();

		// スクロール可能かどうか(スライダーが表示されているか)の判定
		var isScrollEnable = event.currentTarget.scrollHeight - event.currentTarget.clientHeight > 0;
		if(isScrollEnable){
			// スライダーが表示されているdiv内においてはウィンドウのスクロールをしない
			event.preventDefault();// イベントキャンセル
		}

		// wheelDeltaは方向が逆
		var eventPositionY = (event.type == 'wheel') ? event.originalEvent.deltaY : (event.type == 'mousewheel') ? -event.originalEvent.wheelDelta : (event.originalEvent.axis == 2) ? event.originalEvent.detail : 0;
		var positionY = event.currentTarget.scrollTop + eventPositionY;

		// 縦スクロールの場合
		if(positionY != event.currentTarget.scrollTop){
			selectorAppendant.push("[id*='record']");
		}

		// wheelDeltaは上下方向のみ
		var eventPositionX = (event.type == 'wheel') ? event.originalEvent.deltaX : (event.type == 'mousewheel') ? 0 : (event.originalEvent.axis == 1) ? event.originalEvent.detail : 0;
		var positionX = event.currentTarget.scrollLeft + eventPositionX;
		if(positionX != event.currentTarget.scrollLeft){
			selectorAppendant.push("[id*='left']");
			selectorAppendant.push("[id*='right']");
		}

		var divSelector = (selectorAppendant.length > 0) ? "div" + selectorAppendant.join(',') : "div[id]";
		// スクロール位置(Y,X座標)の設定(自div要素以外としてはいけない)
		setTableScrollPositionYX($(event.currentTarget).closest("div[id*='scroll']").children(divSelector), positionY, positionX);
	});
	/**
	 * 行データの縦スクロールイベントハンドラ(div)
	 * ヘッダ固定テーブルの縦スクロール同期を行う
	 * 
	 */
	$("div[id*='record']").scroll(function(event){
		event.preventDefault(); // イベントキャンセル
		if(isWheelScroll[0]){
			isWheelScroll[0] = false;
			return;
		}
		var currentTarget = $(event.currentTarget);
		// 自div要素以外のスクロール対象div
		var targetDiv = currentTarget.closest("div[id*='scroll']").children("div[id*='record']").not(currentTarget);
		targetDiv.each(function(){
			if(this.scrollTop != event.currentTarget.scrollTop){ setTableScrollPositionYX(this, event.currentTarget.scrollTop); } // スクロール位置(Y座標)の設定
		});
	});
	/**
	 * 行データの横スクロールイベントハンドラ(div)
	 * ヘッダ固定テーブルの横スクロール同期を行う
	 * ※行データ内のカーソル移動スクロールが存在する為、footer/record共にハンドルする
	 * 
	 */
	$("div[id*='left'],[id*='right']").scroll(function(event){
		event.preventDefault(); // イベントキャンセル
		if(isWheelScroll[1]){
			isWheelScroll[1] = false;
			return;
		}
		var currentTarget = $(event.currentTarget);
		var selectorAppendant = currentTarget.attr('id').match("left|right");
		var divSelector = selectorAppendant != null ? "div[id*=" + selectorAppendant + "]" : "div[id]";
		// 自div要素以外のスクロール対象div
		var targetDiv = currentTarget.closest("div[id*='scroll']").children(divSelector).not(currentTarget);
		targetDiv.each(function(){
			if(this.scrollLeft != event.currentTarget.scrollLeft){ setTableScrollPositionYX(this, null, event.currentTarget.scrollLeft); } // スクロール位置(X座標)の設定
		});
	});
});


/**
 * クリックイベントハンドラ(table)
 * 
 */
$("table[data-name^='listForm'] [data-name^='sort']").click(function(event){
	columnSort(event);
	event.stopPropagation();
	return false; // 処理内でsubmitする為、ソート対象外の場合の動作を抑止し、またソート時も重複submitされないようにする
});
/**
 * ボタンイベントハンドラ
 * 
 */
$('button[name]').click(function(event){
	// 行追加
	if(isMatched(event.target.name, '^listForm([\d+]|)')){
		addRow(event);
		event.stopPropagation();
		return false; // buttonのtype指定漏れ時にsubmitされないようにする
	}
	// ページング
	else if(isMatched(event.target.name, '^paging.+$')){
		sendPagecommand(event.target.value, event.target.name);
		event.stopPropagation();
		return false; // 処理内でsubmitする為、buttonのtype指定漏れ時に重複submitされないようにする
	}
	// 上記以外の場合はイベント伝播させる
});
/**
 * checkboxクリックイベントハンドラ(checkAll)
 * 
 */
$("input[type='checkbox'][name='checkAll']").click(function(event){
	checkAll(event);
	event.stopPropagation();
});
/**
 * checkboxクリックイベントハンドラ(delete)
 * 
 */
$("input[type='checkbox'][name$='delete']").change(function(event){
	changeRowColor(event, 'background-color', '#c6efff');
	event.stopPropagation();
});
/**
 * checkboxクリックイベントハンドラ(choose)
 * 
 */
$("input[type='checkbox'][name$='choose']").change(function(event){
	changeRowColor(event, 'background-color', '#c6efff');
	event.stopPropagation();
});
/**
 * テキストボックスフォーカスアウトイベントハンドラ(input(text))
 * 
 */
$("input[type='text']").blur(function(event){
	// inputフォーカスアウトで再計算
	calcOneColumn(event);
	calcOneRecord(event);
	event.stopPropagation();
});
/**
 * キーダウンイベントハンドラ(input(text) & Enterキー)
 * 
 */
$("input[type='text']").keydown(function(event){
	// input入力中Enter押下で再計算
	if(event.keyCode == 13){
		calcOneColumn(event);
		calcOneRecord(event);
		event.stopPropagation();
		return false;
	}
});
/**
 * クリックイベントハンドラ(li)
 * ※列フィルタ項目クリック
 * 
 */
$("ul[data-name^='filter'],[data-name^='stageFilter'] li").click(function(event){
	doFilterMain(event);
	// 注）イベントをバブリング(伝播)させるのでstopPropagationしない
});
/**
 * クリックイベントハンドラ(ul)
 * ※列フィルタ項目クリック
 * 
 */
$("ul[data-name^='filter'],[data-name^='stageFilter']").click(function(event){
	caretColorChange(event);
	event.stopPropagation();
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
/**
 * デリミタによる分解
 * @return {Array} デリミタ'@'で分割後の値で構成された配列
 * @param {string} value デリミタが含まれる文字列
 * 
 */
function symbolAtSplit(value){
	if(!value){ return [];}
	return value.split('@');
}
/**
 * 全角数字の半角数字変換(全角ピリオドも含む)
 * @return {string} 半角数字へ変換後の数字文字列
 * @param {string} value 全角数字の数字文字列
 * 
 */
function convNumericZenToHan(value){
	// 全角数値のU+FF10～U+FF19を0xFEE0分だけマイナスシフト
	value = value.replace(/[．０-９]/g, function(arg){
		return String.fromCharCode(arg.charCodeAt(0) - 0xFEE0);
	});
	return value;
}

/**
 * カンマ除去
 * ※縦・横合計算出中に使用
 *
 */
function removeComma(value){
	return String(value).replace( /,/g, '');
}
/**
 * 空白除去
 */
function removeSpace(text){
	if(!text || text.length == 0){ return text; }
	return text.replace(/\s/gi, '');
}
/**
 * 不等号比較
 * @return {boolean} 式が成り立つ場合はtrue
 * @param {string} leftValue  左オペランド
 * @param {string} rightValue 右オペランド
 * @param {string} operator   比較演算子(>, >=, <, <=, =)
 */
function isConsists(leftValue, rightValue, operator){
	if(!$.isNumeric(leftValue) || !$.isNumeric(rightValue)){return false;}
	
	if(operator == '>') {
		return Number(leftValue) > Number(rightValue);
	}
	else if(operator == '>=') {
		return Number(leftValue) >= Number(rightValue);
	}
	else if(operator == '<') {
		return Number(leftValue) < Number(rightValue);
	}
	else if(operator == '<=') {
		return Number(leftValue) <= Number(rightValue);
	}
	else if(operator == '=') {
		return Number(leftValue) == Number(rightValue);
	}
}
/**
 * テーブル縦スクロール位置のセット
 * (ヘッダ固定テーブルのスクロール同期にも使用する)
 * 
 */
function setTableScrollPositionYX(target, y_pos, x_pos){
	var jTarget = $(target);
	// null(undef)・数値チェック
	y_pos = $.isNumeric(y_pos) ? y_pos : jTarget.scrollTop();
	x_pos = $.isNumeric(x_pos) ? x_pos : jTarget.scrollLeft();
	if(jTarget.is("[id*='record']")){
		jTarget.scrollTop(y_pos);
	}
	if(jTarget.is("[id*='head'],[id*='record'],[id*='foot']")){
		jTarget.scrollLeft(x_pos);
	}
}
/**
 * ソート後の再表示時にソート方向アイコンを追加します
 * 
 */
function showSortIcon(){
	// 1ページ内からソート用のhidden情報を取得
	var hiddenElements = $("input[type='hidden'][name$='sorted']");

	hiddenElements.each(function(index, hiddenElement){
		var hiddenName = hiddenElement.name;
		var hiddenValue = hiddenElement.value;

		if(!isMatched(hiddenName, '^.+\..+')){ return true; } // continueの代用
		if(!hiddenValue || hiddenValue.length == 0){ return true; } // continueの代用
		hiddenName = hiddenName.split('.')[0]; // 例)'listFormXX.sorted'の'listFormXX'部分を取得してセレクタに使用する
		
		var hiddenValues = symbolAtSplit(hiddenValue);
		var column = hiddenValues[0];
		var nowBoundFor = hiddenValues[1];

		var elementSelector = "[data-name^='sort@" + column + "']";
		var targetElement = $("table[data-name='" + hiddenName + "']:has(" + elementSelector + ")").find(elementSelector);

		var editClassName;
		if(nowBoundFor == 'ASC'){
			// 昇順
			editClassName = 'glyphicon-sort-by-attributes';
		} else if(nowBoundFor == 'DESC'){
			// 降順
			editClassName = 'glyphicon-sort-by-attributes-alt';
		} else {
			// リセット
			editClassName ='';
		}
		// リセット以外の場合(画面ロード時点では、ソートアイコンは元々存在しない)
		if(editClassName.length > 0){
			var spanElement = $('<span>');
			spanElement.prop('aria-hidden', 'true');
			spanElement.addClass('glyphicon');
			spanElement.addClass(editClassName);
			if(targetElement.is("th,td")){
				// 子要素として追加
				targetElement.append(spanElement);
			}
			else {
				// 兄弟要素として直後へ追加
				targetElement.after(spanElement);
			}
		}
	});
}
/**
 * 指定要素の色を変更します.
 * @return {DOM} 指定スタイル適用後の要素
 * @param {string} targetElement スタイル変更対象の要素
 * @param {string} appendStyleAttr 変更または追加対象のスタイル名(スタイル属性に指定可能な名称)
 * @param {string} styleColor 変更対象の色コード、または色名
 * 
 */
function colorChange(targetElement, appendStyleAttr, styleColor){
	if(!targetElement){ return; }
	var element = $(targetElement);
	var currentStyle = element.attr('style');
	// 新規色指定が存在する場合
	if(styleColor != undefined && styleColor.length > 0){
		var rgbColorNow;
		// 要素にstyle属性が存在する場合
		if(currentStyle != undefined){
			// 要素のスタイル属性より、引数に指定されたappendStyleAttrの取得を試みる(上書き防止措置)
			rgbColorNow = element.css(appendStyleAttr);
		}
		var rgbColor = $('<style>').css(appendStyleAttr, styleColor).css(appendStyleAttr);
		if(rgbColor == rgbColorNow){ return; }
		newStyle = appendStyleAttr + ':' + rgbColor;
		if(currentStyle != undefined){
			// 要素にstyle属性が存在し、且つ、引数に指定されたappendStyleAttrの設定が存在する場合
			if(rgbColorNow != undefined){
				// appendStyleAttrに対応する元々の設定を要素のdataプロパティへ退避
				element.data('currentRgbColor', rgbColorNow);
				element.css(appendStyleAttr, rgbColor); // appendStyleAttrに指定されたスタイル設定のみを変更する
				return; // 処理を抜ける
			}

			// 要素タグにstyle属性が存在するが、appendStyleAttrに対する設定が存在しない場合
			searchIndex = currentStyle.lastIndexOf(';');
			// セミコロンで終わっていない場合はセミコロンを付加
			if(searchIndex == -1){
				currentStyle = currentStyle + ';';
			}
			else {
				newStyle = currentStyle + newStyle;
			}
		}
	}
	else {
		// 元の色へ戻す
		if(currentStyle == undefined){ return; }// スタイル属性が除去後の為、処理を終える

		// dataプロパティからappendStyleAttrに対応する設定の取得を試みる(無ければundef)
		rgbColorNow = element.data('currentRgbColor');
		if(rgbColorNow != undefined){
			element.css(appendStyleAttr, rgbColorNow);
			return; // 処理を終える
		}
		// スタイル属性が存在する場合
		if(currentStyle != undefined){
			// 前回のスタイル変更時に、付加したスタイル(背景色)のみを除去する
			searchIndex = currentStyle.lastIndexOf(appendStyleAttr);
			if(searchIndex != -1){
				newStyle = currentStyle.substring(0, searchIndex);
			}
			else {
				// 前回のスタイル変更時に、付加したスタイルが除去後の為、処理を終える
				return;
			}
		}
	}
	element.attr('style', newStyle);
	return element;
}
//==================================================================== For Filter Common Functions Start //
/**
 * ネストした子要素(深層)からテキストノードのみを抽出してtrimしたテキストの配列を返します
 * @return {Array{string}} 深層のテキストノードから抽出したtrim後の文字列配列
 * @param {Array{DOM}} contentsList 抽出対象のDOM要素配列
 * @param {DOM} escapeNode 抽出から除外する要素名(例:'ul','li'などを指定するとul要素またはli要素を除外して評価する)
 * 
 */
function getTrimmedDeepTextNode(contentsList, escapeNode){
	var escape = !escapeNode ? undefined : $(escapeNode);
	var trimedTextArray = new Array();
	$(contentsList).map(function(index, content){
			// 除外対象の要素の場合はスキップ
			if(escape != undefined && escape.is(content)){
				return true; // continueの代用
			}
			if(content.nodeType == 3){
				// テキストノードの場合
				content.data = content.data.trim();
				if(content.data.length > 0){
					trimedTextArray.push(content.data);
				}
			}
			else {
				var childContents = $(content).contents();
				if(childContents.length > 0){
					// 再帰呼び出し
					var deepTextArray = getTrimmedDeepTextNode(childContents, escape);
					// 再帰から返された配列に内容が存在する場合
					if(deepTextArray != undefined && deepTextArray.length > 0){
						if(!trimedTextArray || trimedTextArray.length == 0){
							// 取得できた最初の値(配列)の場合
							trimedTextArray = deepTextArray;
						} else {
							// 以降は配列に追加(多次元配列にならないよう連結)
							trimedTextArray = trimedTextArray.concat(deepTextArray);
						}
					}
				}
			}
		});
	return trimedTextArray;
}
/**
 * 引数の要素とその下位要素から引数指定の属性(例:data-name等)に設定された値を配列で返します
 * @return {Array{string}} 深層ノードが持つ属性値の配列
 * @param {Array{DOM}} elemList 抽出対象のDOM要素配列
 * @param {string} attrName 取得対象の属性名
 * @param {Array{string}} escapeValues 属性値から除外する値の配列(例:['xxx','yyy']と指定した場合、返却する結果配列には'xxx','yyy'が含まれなくなる)
 * 
 */
function getArrayAttributes(elemList, attrName, escapeValues){
	var attrName = !attrName ? 'data-name' : attrName; // attrNameの指定が無ければname属性とする
	escapeValues = !escapeValues ? [] : escapeValues;
	var attrArray = new Array();
	$(elemList).map(function(index, elem){
		var element = $(elem);
		var attrValue = element.attr(attrName);
		attrValue = !attrValue ? undefined : attrValue;
		// 属性値が取得でき、且つ除外対象の値でない場合
		if(attrValue != undefined && $.inArray(attrValue, escapeValues) == -1){
			// 配列に追加(全角数字の半角変換含む)
			attrArray.push(convNumericZenToHan(attrValue.trim()));
		}
		// 引き続き下位ノードを見る
		var childList = element.children();
		if(childList.length > 0){
			// 再帰呼び出し
			var deepNodeArray = getArrayAttributes(childList, attrName, escapeValues);
			// 再帰から返された配列に内容が存在する場合
			if(deepNodeArray != undefined && deepNodeArray.length > 0){
				if(attrArray.length == 0){
					// 取得できた最初の値(配列)の場合
					attrArray = deepNodeArray;
				}
				else {
					// 以降は配列に追加(多次元配列にならないよう連結)
					attrArray = attrArray.concat(deepNodeArray);
				}
			}
		}
	});
	return attrArray;
}
/**
 * 要素特定に使用する一意なidを取得または生成付与して
 * 要素のdataプロパティへ格納し、そのid値を返します
 * 
 */
function useUniqueId(element){
	var targetElement = $(element);
	// 引数のelementからdata-uniqueidプロパティを取得(取得できなければ生成)
	var uniqueId = targetElement.attr('data-uniqueid');
	if(!uniqueId || uniqueId.length == 0){
		// urlコンテキストとタイムスタンプ&乱数(重複防止)から生成
		var baseId = $.now();
		uniqueId = String(baseId + parseInt(String(Math.random()).split('.')[1])).substring(0, baseId.toString().length);
		// element(要素)のdataプロパティへ生成したuniqueIdを格納する
		targetElement.attr('data-uniqueid', uniqueId);
	}
	// uniqueIdを返す
	return uniqueId;
}
////// ▲△▲△▲△▲△ 共通部 ▲△▲△▲△▲△▲△ //////
//==================================================================== Common Functions End //

/**
 * 一覧表の列ソート
 * 
 */
function columnSort(event){
	var eventTarget = $(event.target);
	var sortKey = symbolAtSplit(eventTarget.data('name'));
	// ソート対象のキー(※)が設定されていなければ処理を終える
	// ※Beanのプロパティ名を「sort@xxxxx]の形式で設定する
	if(!sortKey || sortKey.length == 0 || !sortKey[1] || sortKey[1].length == 0){ return; }
	var targetTable = eventTarget.closest("table[data-name^='listForm']");
	

	// イベントの発生したカラムの位置インデックスを取得
	var cellIndex = eventTarget.closest('th,td').index();

	// input要素が存在する列の場合は処理しない。
	var tdSelector = "td:nth-of-type(" + (cellIndex + 1)+ "):has(input,select,textarea)";
	var isInput = getTrDataRecords(targetTable, getDataTBodyUniqueId(targetTable)).find(tdSelector).length > 0;// nth-of-typeはインデックス1始まりで指定
	if(isInput){ return; }

	// クリックイベントの発生した列の位置インデックスを送信
	var tableName = targetTable.data('name');
	hiddenAppend(tableName + '.nextSortKey', sortKey[1]);
	if(targetTable.closest('form').length > 0){
		$('form').submit();
	}
}
/**
 * チェックボックスのON/OFFに応じて対象行の背景色を変更します.
 * @param {Event} event 要素から送出されたイベントオブジェクト
 * @param {string} appendStyleAttr 変更または追加対象のスタイル名(スタイル属性に指定可能な名称)
 * @param {string} styleColor 変更対象の色コード、または色名
 * 
 */
function changeRowColor(event, appendStyleAttr, styleColor){
////// ===▽▼ 初期処理 ================= //////
	var eventTarget = $(event.target);
	var isChecked = eventTarget.prop('checked');
	var divSelector = "div[id]:not([id*=head],[id*=foot])"; // headerとfooterを除くidを持ったdiv要素
	var siblingTBody = eventTarget.closest("div").siblings(divSelector).andSelf().find("tbody:not(:has('th'))");
	var targetRowIndex = eventTarget.closest("tr").index();
	var targetRow = siblingTBody.find("tr:nth-of-type(" + (targetRowIndex +1) + ")");// nth-of-typeはインデックス1始まりで指定
////// ===△▲ 初期処理 ================= //////

	// 1行内のtd、input(text)、button要素に対して処理する
	targetRow.find("td,input,select,textarea,[type='button']").each(function(){

////// ===▽▼ 主処理 ================= //////
		// チェックONの場合
		if(isChecked){
			// 引数指定の背景色へ変更
			colorChange(this, appendStyleAttr, styleColor);
		}
		// チェックOFFの場合
		else {
			// 元の色へ戻す
			colorChange(this, appendStyleAttr, '');
		}
////// ===△▲ 主処理 ================= //////
	});
}

/**
 * 全てチェック(チェックボックス)
 * 
 */
function checkAll(event){
	var eventTarget = $(event.target);
	var colIndex = eventTarget.closest('td,th').index(); // 列インデックス
	var isChecked = eventTarget.prop('checked');
	var eventTable = eventTarget.closest("table");

	// tbody(行データ側)を取得
	var tBodySelector = "tbody:not(:has(input[type='checkbox'][name='checkAll'])):has(input[type='checkbox'])"; // 全てチェックのチェックボックス要素を持たないtbody要素
	var tBody = eventTable.children(tBodySelector);
	// ヘッダ固定一覧表の場合
	if(tBody.length == 0) {
		var divSelectorFrom = "div[id]:not([id*=head],[id*=foot])"; // headerとfooterを除くidを持ったdiv要素
		var eventTableDivId = eventTable.closest("div[id*='head'],[id*='foot']").attr('id');
		var selectorAppendant = eventTableDivId.match("left|right");
		var divSelectorTo = selectorAppendant != null ? divSelectorFrom.replace("[id]", "[id*=" + selectorAppendant + "]") : divSelectorFrom;
		// 自divは除く
		divSelectorTo = divSelectorTo + ":not([id=' + eventTableDivId + '])";
		// 行データ部のtbodyを取得(※フィルタ条件要素(ul)の親要素の内、headerとfooterを除くdiv配下より、ul要素を持たないtbodyを取得)
		tBody = eventTable.closest(divSelectorFrom).find(divSelectorTo).find(tBodySelector);
	}
	// ヘッダ固定(テーブル分離)スクロールテーブル
	var checkBoxes = tBody.find("td:nth-of-type(" + (colIndex + 1)  + ")").find("input[type='checkbox']");// nth-of-typeはインデックス1始まりで指定
	checkBoxes.each(function(){
		$(this).prop('checked', isChecked).change();
	});
}
/**
 * 列単位の合計(縦)算出(対象table全て)
 * ※onloadイベントでコール
 * 
 */
function calcAllVertical(){
	// ページ内に存在する明細部(divのdata-nameが「calc」で始まる)のテーブルを取得する。
	var targetTables = $("div[data-name^='calc']").find("table");
	// 計算対象のtable毎の処理
	targetTables.each(function(){
		var targetTable = $(this);
		if(console){ console.log("[" + targetTable.attr('name') + "]を処理中(集計対象テーブル数[" + targetTables.length + "])"); }
		// 明細部の行データを取得
		var targetRows = targetTable.find('tr');
		var tdLength = $(targetRows.get(0)).find('td').length;
		var mathResults = new Array();
////// ===▽▼ 各列の縦合計算出 =========================== //////
		// 列毎に繰り返す
		for(var cellIndex = 0;cellIndex < tdLength; cellIndex++){
			// 対象セル位置のINPUT要素を取得
			var inputElements = targetRows.find("td:eq(" + cellIndex + ")").find("input[type='text']");
			// input(テキスト)項目でなければスキップ
			if(inputElements.length == 0){ continue; }
			var mathResult = new BigNumber(0);
			// 行データ分繰り返す
			inputElements.each(function(rowIndex, inputElm){
				var nValue = removeComma(inputElm.value);
				// 入力セル内のinput要素のvalueを取得し、数値でなければスキップ
				if(!$.isNumeric(nValue)) return true; // continueの代用
				mathResult = mathResult.plus(nValue);
			});
////// ===△▲ 各列の縦合計算出 =========================== //////
////// ===▽▼ 各列の縦合計表示反映 ======================= //////
			setTotalVertical(cellIndex, mathResult, targetTable);
////// ===△▲ 各列の縦合計表示反映 ======================= //////
		}
	});
}
/**
 * 1列単位の合計(縦)算出
 * 
 */
function calcOneColumn(event){
	var eventTarget = $(event.target);
	var cellIndex  = eventTarget.closest('td').index();
	var targetTable = eventTarget.closest("div[data-name^='calc']").find("table");
	var targetRows = targetTable.find('tr');
	var inputElements = targetRows.find("td:eq(" + cellIndex + ")").find("input[type='text']");
////// ===▽▼ 対象列の縦合計算出 ========================= //////
	// input(テキスト)項目でなければ処理を終える
	if(inputElements.length == 0){ return ; }
	var mathResult = new BigNumber(0);
	// 行データ分繰り返す
	inputElements.each(function(rowIndex, inputElm){
		var nValue = removeComma(inputElm.value);
		// 入力セル内のinput要素のvalueを取得し、数値でなければスキップ
		if(!$.isNumeric(nValue)) return true; // continueの代用
		mathResult = mathResult.plus(nValue);
	});
////// ===△▲ 対象列の縦合計算出 ========================= //////
////// ===▽▼ 対象列の縦合計表示反映 ===================== //////
	// 計算結果を対象tableのfooterで指定された位置に表示する。
	setTotalVertical(cellIndex, mathResult, targetTable);
////// ===△▲ 対象列の縦合計表示反映 ===================== //////
}
/**
 * 計算結果の表示反映(縦合計)
 * 
 */
function setTotalVertical(cellIndex, mathResult, calcTable){
	// 合計算出に使用したtableが属しているdivと同階層でdata-name「mathTotal」を持つtableを取得する。
	var totalTables = $(calcTable).closest("div[data-name^='calc']").siblings("div:has(td[data-name^='math'])").find("table");
////// ===▽▼ 合計テーブルへ表示反映(footer) ============= //////
	$.each(totalTables, function(index, totalTable){
		// 計算結果表示用のtableからtrを取得し、行毎に処理
		$(totalTable).find('tr').each(function(rowIndex, record){
			// 「math」から始まるname属性のセルが存在する場合
			var cells = $(record).children("td[data-name^='math']");
			cells.each(function(index, element){
				var cell = $(element);
				var propName = cell.attr('data-name');
				// セルのname属性が、以下の正規表現に一致する位置に対して計算結果を表示する。
				var pattern = '^math([^\d]+|)Total\@' + (cellIndex + 1);
				// 正規表現に一致した場合は値をセット
				if(isMatched(propName, pattern)){
					cell.text(mathResult.toString());
				}
			})
		});
	});
////// ===△▲ 合計テーブル(footer)へ表示反映 ============= //////
}
/**
 * 処理対象一覧表の行合計(横)
 * 
 * 
 */
function calcAllHorizontal(){
	// ページ内に存在する明細部(divのdata-nameが「calc」で始まる)のテーブルを取得する。
	var targetTables = $("div[data-name^='calc']").find("table");
	// 計算対象のtable毎の処理
	targetTables.each(function(){
		var targetTable = $(this);
////// ===▽▼ 各行の横合計算出 =========================== //////
		// 明細部の行データ分繰り返す
		targetTable.find('tr').each(function(){
			var rowData = $(this);
			var rowIndex = rowData.index();
			var mathResult = new BigNumber(0);
			// 対象行のINPUT(text)要素を全て取得
			rowData.find("input[type='text']").each(function(){
				var inputElm = $(this);
				var nValue = removeComma(inputElm.val());
				// 入力セル内のinput要素のvalueを取得し、数値でなければスキップ
				if(!$.isNumeric(nValue)) return true; // continueの代用
				mathResult = mathResult.plus(nValue);
			});
			setHorizontalTotal(rowIndex, mathResult, targetTable);
		});
////// ===△▲ 各行の横合計算出 =========================== //////
	});
}
/**
 * 1行単位の合計(横)算出
 * 
 */
function calcOneRecord(event){
	var eventTarget = $(event.target);
	var targetTable = eventTarget.closest("div[data-name^='calc']").find("table");
	var rowIndex  = eventTarget.closest('tr').index();
	var targetRows = targetTable.find("tr:nth-of-type(" + (rowIndex + 1) + ")");
	var mathResult = new BigNumber(0);
////// ===▽▼ 対象行の横合計算出 ========================= //////
	// 対象行のINPUT(text)要素を全て取得
	targetRows.find("input[type='text']").each(function(){
		var inputElm = $(this);
		var nValue = removeComma(inputElm.val());
		// 入力セル内のinput要素のvalueを取得し、数値でなければスキップ
		if(!$.isNumeric(nValue)) return true; // continueの代用
		mathResult = mathResult.plus(nValue);
	});
	setHorizontalTotal(rowIndex, mathResult, targetTable);
////// ===△▲ 対象行の横合計算出 ========================= //////
}
/**
 * 計算結果の表示反映(横合計)
 * 
 */
function setHorizontalTotal(rowIndex, mathResult, calcTable){
	// 合計算出に使用したtableが属しているdivと同階層でdata-name「subTotal」を持つtableを取得する。
	var totalTables = $(calcTable).closest("div[data-name^='calc']").siblings("div:has(td[data-name^='sub'])").find("table");;
////// ===▽▼ 合計テーブル(record-fixed)へ表示反映 ======= //////
	// 計算対象のtable毎の処理
	$.each(totalTables, function(index, totalTable){
		var dataRows = $(totalTable).find('tr');
		// 算出結果表示用テーブルに対して引数の行インデックス位置で「sub」から始まるtdにテキストとして反映する
		var cell = $(dataRows[rowIndex]).children("td[data-name^='sub']");
		// 引数の添え字位置に計算結果をテキスト出力する
		cell.text(mathResult.toString());
	});
////// ===△▲ 合計テーブル(record-fixed)へ表示反映 ======= //////
}
/**
 * 一覧明細行の追加
 * 
 */
function addRow(event){
	var eventTarget = $(event.target);
	var splitValue = symbolAtSplit(eventTarget.attr('name'));
	var targetName = splitValue[0];
	var limit = !splitValue[1] ? -1 : parseInt(splitValue[1]);;
	var lastAdditional = !splitValue[2] ? 0 : parseInt(splitValue[2]); // 新規追加した行数
	if(limit != -1 && limit <= lastAdditional){ return; } // 行追加リミットに達したら追加しない

	// 行追加対象のテーブルを取得
	var baseDiv = eventTarget.closest("div[class='row']");
	var targetTables = baseDiv.find("table[data-name='" + targetName + "']");
	if(!targetTables || targetTables.length == 0){
		// ヘッダ固定の場合
		// 行追加ボタンのdiv(row)部品から直近且つ、同一data-nameのテーブルを有するdivを取得
		baseDiv = baseDiv.siblings("div[class='row']:has(table[data-name='" + targetName + "'])");
		targetTables = baseDiv.find("div[id^='cs-record']").find("table[data-name^='" + targetName + "']");
//		targetTables = $("div[id^='cs-record']").find("table[class^='cs-scroll'][data-name^='" + targetName + "']"); // ドキュメント内全体から探すので重い
	}
	if(console){ console.log("処理対象テーブル名[" + targetName + "], テーブル数[" + targetTables.length + "]"); }

	var eventCaller = new Array();
	// 注)縦(行)固定ヘッダの場合はtargetTablesが複数となる
	// コピー元のtr要素(1行分)を先頭行から取得
	targetTables.find('tbody > tr:first').each(function(){
		var baseElement = $(this);
		if(!baseElement || baseElement.length == 0) return false; // breakの代用

		// 行(オブジェクト)をコピーして不可視にする
		var last_row_clone = baseElement.clone(true).hide();
		// イベントを送出する部品群を取得する※ラジオボタンは後続で追加
		// (分離したテーブルの背景色変更などを同時にイベントトリガで状態変更する為)
		var callerSelector = "input[type='checkbox'],input[type='button'][id$='\\.fileClearBtn']";
		if(eventCaller.length == 0){
			eventCaller = last_row_clone.find(callerSelector);
		}
		else {
			$.merge(eventCaller, last_row_clone.find(callerSelector));
		}

		// コピーした行(オブジェクト)を先頭行の前へ追加してスクロール
		last_row_clone.insertBefore(baseElement);
		var targetDiv = baseElement.closest("div");
		if(targetDiv.scrollTop() > 0){
			setTableScrollPositionYX(targetDiv, 0, 0);
		}
		// eventTarget(追加ボタン)のname属性へ、行追加リミットと新規追加した行数 + 1を保持させる
		eventTarget.attr('name', targetName + '@' + limit + '@' + (lastAdditional + 1));
////// ===▽▼ コピー行の表示用テキストクリア ============= //////
		// labelはラジオボタン等のラベルをクリアしないよう
		// テキストノード以外の子要素を持たないlabelを対象とする
		var nodes = last_row_clone.find("td,th,label:not(:has(*))").andSelf().contents();
		nodes.each(function(index, node){
			if(node.nodeType != 3){ return true; }// continueの代用
			node.data = '';
//				node.data = $.isNumeric(removeComma(node.data)) ? '0' : ''; // (一旦保留のためコメント)
		});
////// ===△▲ コピー行の表示用テキストクリア ============= //////

////// ===▽▼ コピー行のINPUT内容クリア(text,checkbox) === //////
		last_row_clone.find("input[type='text'],[type='hidden'],[type='password'],input[type='radio'],select,textarea").each(function(){

			/////▽▼ value値のクリアとチェックボックスのクリア /////
			var cellInput = $(this);
			if(cellInput.is("[id$=\\.fileSeq]")){
				// ファイル制御部品のクリアを実施するために設定
				cellInput.val('-1');
			}
			// ラジオボタンは、name属性変更でクリアする
			else if(!cellInput.is("input[type='radio']")){
				cellInput.val('');
				// コピー元の行(input)において、値が数値なら０(ゼロ)をinput要素の初期値として設定(一旦保留のためコメント)
				// cellInput.val($.isNumeric(cellInput.val()) ? '0' : '');
			}
			cellInput.css('border-color', '');
			///// △▲ value値のクリアとチェックボックスのクリア /////

			// INPUT要素のname属性を取得する。
			var input_name = cellInput.attr('name');
			// 取得できなければスキップ
			if(!input_name) return true; // continueの代用

			// name属性へ与える添え字番号をtrの末尾(最終行)より取得する
			var next_rowno = baseElement.closest('table').find('tr:last').index();
			// name属性(submit時のデータキー)の添え字番号をセット
			cellInput.attr('name', input_name.replace(/\[\\d+\]/, '[' + next_rowno + ']'));
			if(cellInput.is("input[type='radio']")){
				var baseSelector;
				if(cellInput.is("input[type='radio'][name]")){
					// コピー元がラジオOFFとなる
					cellInput.attr('name', input_name.replace(/\d+/, next_rowno));
					// 変更前のidでセレクタ文字列を作成
					baseSelector = "[id='" + cellInput.attr('id') + "']";
					cellInput.attr('id', cellInput.attr('name') + cellInput.parent().index());
					if(cellInput.parent("[for]").length == 1){
						cellInput.parent().attr('for', cellInput.attr('id'));
					}
					// name変更後の新規行側のラジオをeventCallerへ追加
					eventCaller.push(cellInput);
				}
				// チェック状態を取得
				var isChecked = cellInput.prop('checked');
				if(isChecked){
					// コピー元の同一idのラジオを取得してチェックON
					var base = baseElement.find(baseSelector);
					base.prop('checked', true);
				}
			}
		});
////// ===△▲ コピー行のINPUT内容クリア(text,checkbox) === //////
		// 追加した行を可視状態にする
		last_row_clone.show();
	});
////// ===▽▼ コピー行のファイル制御部品クリア(クリアボタンクリックイベント送出) ============= //////
	$.each(eventCaller, function(){
		var cellInput = $(this);
		// チェックボックスの変更イベント送出の為
		if(cellInput.is(':checkbox') || cellInput.is("input[type='radio']")){
			cellInput.removeData('bs.tooltip');// bootstrapのtooltipを再生成する
			var isChecked = cellInput.prop('checked');
			if(isChecked){
				if(cellInput.is(':checkbox')){
					// チェックOFFにする(イベント送出する)
					cellInput.prop('checked', false).change();
				}
				else {
					// チェックOFFにする(イベント送出しない)
					cellInput.prop('checked', false);
				}
			}
			cellInput.parent().css('border-bottom', '');
		}
		// ファイル制御部品のクリアボタンクリックイベント送出の為
		else if(cellInput.is("input[type='button'][id$='\\.fileClearBtn']")){
			cellInput.click();
		}
	});
////// ===△▲ コピー行のファイル制御部品クリア(クリアボタンクリックイベント送出) ============= //////
}
////// ▼▽▼▽▼▽▼▽ === tableフィルタ処理部 === ▽▼▽▼▽▼▽▼▽▼ //////
/**
 * ★★★ フィルタリング処理(メイン処理) ★★★
 *
 *
 */
function doFilterMain(event){
	var tbodyUniqueId;
	var filterTable;
	if(!event){
		// 1ページ内からフィルタ系のul要素を検索
		var selector = "ul[data-name^='filter'],[data-name^='stageFilter']";
		// 初期表示時はフィルタ項目のある全ての一覧表を件数カウントの対象とする
		filterTable = $("table:has(" + selector + ")");
	}
	else {
		// フィルタ操作時
		var filterElement = $(event.target);
		// 処理対象のフィルタ項目かどうかをチェック
		if(isMatched(removeSpace(filterElement.text()), /.+\(0\)$/)){ return; } // ※0件項目が選択された場合は処理しない

		tbodyUniqueId = getDataTBodyUniqueIdByFilterEvent(event);

		// イベント発生元の直近でtbodyUniqueIdを持ったtable(ヘッダ固定の場合は行データ側)
		filterTable = filterElement.closest("table:has(tbody[data-uniqueid='" + tbodyUniqueId + "'])");
		if(filterTable.length == 0){
			// ヘッダ固定一覧表の場合
			filterTable = filterElement.closest("div[class='row']").find("table:has(tbody[data-uniqueid='" + tbodyUniqueId + "'])");
		}
		// 全てのフィルタ条件が解除されている場合は初期表示状態へ戻して処理を終える
		if(!hasFilterKey(_currentFilterKeys[tbodyUniqueId])){
			getTrDataRecords(filterTable, tbodyUniqueId).show();
			doFilterCountAndMatching(filterTable, tbodyUniqueId); // カウントのみ実施
			return;
		}
	}
////// ===▽▼ 主処理(対象テーブル分) ================= //////
	// フィルタのあるテーブル分繰り返す
	filterTable.each(function(idx, filterTable){

		var matchCounters;
		var records;
		// 初期表示の場合
		if(!event){
			// グローバル変数を初期化し、処理対象のtbodyに対するuniqueIdを取得する
			tbodyUniqueId = setGlobalFilterMembers(filterTable);
			// フィルタ対象件数をカウント・反映する
			doFilterCountAndMatching(filterTable, tbodyUniqueId); // カウントのみ実施
		}
		// フィルタ操作後の場合
		else {
			// フィルタ処理およびフィルタ後の対象件数をカウント・反映する
			doFilterCountAndMatching(filterTable, tbodyUniqueId, true);
		}
	});
////// ===△▲ 主処理(対象テーブル分) ================= //////
}
/**
 * フィルタ処理およびフィルタ件数カウント結果取得
 * ※初期表示時は、フィルタ件数カウント結果取得のみ
 *
 * @param {DOM} filterTable フィルタ操作が発生したテーブル要素、または、フィルタ条件の件数カウント対象のテーブル要素
 * @param {String} tbodyUniqueId targetTableでul要素を持たないtbody要素から取得または付与したuniqueId
 * @param {Array{DOM}} records 被カウント対象の行データ配列
 * @param {boolean} フィルタ処理の場合はtrue, カウントの場合はundefまたはfalse
 * 
 */
function doFilterCountAndMatching(filterTable, tbodyUniqueId, isFiltering){
	var records = getTrDataRecords(filterTable, tbodyUniqueId);
	isFiltering = !isFiltering ? false : isFiltering; // undefチェック
	var currentFilters;
	// フィルタ処理の場合
	if(isFiltering){
		currentFilters = _currentFilterKeys[tbodyUniqueId];
	}
	var dictFilterKeyArray = _filterKeysDictionary[tbodyUniqueId];
	var matchCounter = isFiltering ? undefined : new Array(dictFilterKeyArray.length); // 列分のカウンタを用意
	// 行データ(レコードデータ)毎に繰り返す
	$.each(records, function(rowIndex, currentRecord){
		var holdingRecord = $(currentRecord);// マッチング中の保持対象行データ
		// フィルタキーの配列毎に繰り返す
		$.each(dictFilterKeyArray, function(colIndex, filters){

			// filtersがundefの場合はスキップ
			if(!filters){ return true; } // continueの代用
			// holdingRecordがundefの場合はスキップ
			if(isFiltering && !holdingRecord){ return true; } // continueの代用
			// 一致件数カウンタの初期化 - 連想配列を用意する(xx列目のフィルタリスト用)
			if(matchCounter != undefined && !matchCounter[colIndex]){ matchCounter[colIndex] = new Object(); }

			// 行データのセル内の値を取得(変数を初期化)
			var textNodeArray = getTrimmedDeepTextNode(holdingRecord.children("td:nth-of-type(" + (colIndex + 1) + ")"));// nth-of-typeはインデックス1始まりで指定

			$.each(filters, function(stageIndex, filterKeys){
				// filterKeysが配列でない場合(uniqueId等のオブジェクトキーの場合)はスキップ
				if(!$.isArray(filterKeys) || filterKeys.length == 0){ return true; } // continueの代用

				if(matchCounter != undefined && !matchCounter[colIndex][stageIndex]){
					matchCounter[colIndex][stageIndex] = new Array(filterKeys.length); // 1ドロップダウンに対するフィルタ条件項目数分の配列を用意
				}
				// strFilterKeyに一致する行データは、カウンタの加算対象として扱う
				$.each(filterKeys, function(itemIndex, strFilterKey){
					// カウンタを初期化
					if(matchCounter != undefined && !$.isNumeric(matchCounter[colIndex][stageIndex][itemIndex])){
						matchCounter[colIndex][stageIndex][itemIndex] = 0;
					}
					// フィルター操作後の場合
					if(isFiltering){
						// undefチェック
						if(currentFilters[colIndex] != undefined 
						&& currentFilters[colIndex][stageIndex] != undefined){

							// 列内多段フィルタかどうかを判定
							var isStageFilter = (currentFilters[colIndex][stageIndex]['filterKey'] != undefined) ? true : false;
							var strCurrentFilterKey;
							// 列内多段フィルタの場合
							if(isStageFilter){
								strCurrentFilterKey = currentFilters[colIndex][stageIndex]['filterKey'];
							}
							// シングルフィルタの場合
							else {
								strCurrentFilterKey = currentFilters[colIndex];
							}
							// ループ中のフィルタキーが画面選択されたフィルタキーと一致する場合
							if(strFilterKey == strCurrentFilterKey){
								var isRecordHold;
// 複数条件選択可能のフィルタ(ORのフィルタ)は、操作上の混乱を生む可能性があるため、保留(コメントアウト)
//								// 多段フィルタ且つ、データ行のセル内にテキストが1つしか存在しない場合
//								if(isStageFilter && textNodeArray.length == 1){
//									// 同列内で選択中のいずれかのフィルタ条件に一致する行データを表示対象とする(ORのフィルタ)
//									for(var key in currentFilters[colIndex]){
//										strFilterKey = currentFilters[colIndex][key]['filterKey'];
//										isRecordHold = isFilterMatched(strFilterKey, textNodeArray, stageIndex);
//										// マッチングしたらfor文を抜ける
//										if(isRecordHold){
//											break;
//										}
//									}
//								}
//								else {
//									// 選択中の全てのフィルタに一致する行データを表示対象とする(ANDのフィルタ)
//									isRecordHold = isFilterMatched(strFilterKey, textNodeArray, stageIndex);
//								}

								// 選択中の全てのフィルタに一致する行データを表示対象とする(AND[且つ]のフィルタ)
								// ※注意:上記のORのフィルタを有効化する場合は、下記「isRecordHold = isFilterMatched～」の行は不要(処理重複)の為、削除すること。
								// また、0件項目を選択不可としているif文(「※0件項目が選択された場合は処理しない」と記載している2ヶ所)をコメントアウトすること。
								
								isRecordHold = isFilterMatched(strFilterKey, textNodeArray, stageIndex);
								// 行データが保持対象ではなくなった場合
								if(!isRecordHold){
									// ※注意:isRecordHoldのフラグをレコード保持の代用とすると
									// ドリルダウンにならない為してはならない(⇒undefinedにし、実レコードはhideにして表示対象から消去する)。
									holdingRecord = undefined;
									$(currentRecord).hide();
								}
							}
						}
					}
					else {
						// マッチング処理を実行し、一致すればカウンタをインクリメント
						if(!holdingRecord.is(":hidden") && isFilterMatched(strFilterKey, textNodeArray, stageIndex)){
							matchCounter[colIndex][stageIndex][itemIndex]++;
						}
					}
				});
			});
		});
		// フィルタ選択中の全ての条件に一致したレコードのみ保持対象とする
		if(isFiltering && holdingRecord != undefined){
			$(currentRecord).show();
		}
	});
	// フィルタ後のレコードについて表示反映とカウントを取得
	if(isFiltering){
		// 再帰呼び出し
		doFilterCountAndMatching(filterTable, tbodyUniqueId); // カウントのみ実施
	}
	else {
		// フィルター条件項目へカウント結果を反映する
		renewFilterLabel(filterTable, tbodyUniqueId, matchCounter);
	}
}
/**
 * ★★★ フィルタ件数カウント結果反映処理 ★★★
 * ※件数をフィルタキー要素のテキストへ反映
 */
function renewFilterLabel(targetTable, tbodyUniqueId, matchCounter){
	// フィルタ項目について、書式の正規表現を生成する
	var regexp = new RegExp(/.+(?=\()|[^(?=\()]+$/);
	// 取得したul要素分繰り返す
	getUListArray(targetTable).each(function(arrayIndex, ulList){
		// name属性が'unfilter'以外のname属性を持つ要素毎に繰り返す
		$(ulList).find("[data-name!='unfilter'][data-name]").each(function(itemIndex, filterKey){
			var strFilterKey = getArrayAttributes(filterKey)[0];
			var filterIndexes = getStageIndexByDictionary(strFilterKey, ulList, tbodyUniqueId);
			var colIndex = filterIndexes[0];
			var stageIndex = filterIndexes[1];
			var result;
			// 主処理で一度もマッチせずbreakとなった項目の場合
			if(!matchCounter[colIndex] || !matchCounter[colIndex][stageIndex] ||!matchCounter[colIndex][stageIndex][itemIndex]){
				result = 0;
			}
			else {
				result = matchCounter[colIndex][stageIndex][itemIndex];
			}
			var elm = $(filterKey);
			var tdText = removeSpace(elm.text()).match(regexp);
			elm.text(tdText + '(' + result + ')');
		});
	});
}
/**
 * フィルタ条件マッチング処理
 * 
 */
function isFilterMatched(strCurrentFilterKey, textNodeArray, stageIndex){
	// 値が取得できなければ不一致として処理を終える
	var matchResult = false;
	if(!strCurrentFilterKey){ return matchResult; }
	if(!textNodeArray || textNodeArray.length == 0 || !textNodeArray[0]) { return matchResult; }

	stageIndex = (stageIndex == undefined) ? 0 : stageIndex;
	// セル内にテキストノードが1つのみの場合
	if(textNodeArray.length == 1){
		// データ行を段数1つとして処理
		stageIndex = 0;
	}
	var regexpBeginSymbol = '^';
	var unit = '';
	// strCurrentFilterKeyが部分一致条件の場合は、正規表現の先頭一致を変更する
	if(isMatched(strCurrentFilterKey, '^\\*@.*')){
		var splitResult = symbolAtSplit(strCurrentFilterKey);
		splitResult.shift(); // 先頭の*@を除去
		strCurrentFilterKey = splitResult.join('@');
		regexpBeginSymbol = '^.*';
	}
	// 単位付与の場合
	if(isMatched(strCurrentFilterKey, '^.+@[^\\d]+$')){
		var splitResult = symbolAtSplit(strCurrentFilterKey);
		unit = splitResult.pop();
		strCurrentFilterKey = splitResult.join('@');
	}
	// セルの値(文字列)に対してフィルタ条件のマッチングを試みる。
	var cellValue = textNodeArray[stageIndex];
	matchResult = isMatched(cellValue, regexpBeginSymbol + strCurrentFilterKey + '.*' + unit);
	if(!matchResult){
		// 不等号比較の場合
		if(isMatched(strCurrentFilterKey, '^.+@\\d+')){
			cellValue = removeComma(cellValue);
			// セルの値について全角数字の半角変換
			cellValue = convNumericZenToHan(cellValue);
			// フィルタキーを'@'でデリミタ分解する
			var operator = symbolAtSplit(strCurrentFilterKey);
			if(unit != undefined && unit.length > 0){
				var matcher = '\\d+.*' + unit;
				if(!isMatched(cellValue, matcher)){
					return matchResult;
				}
			}
			// セルの値が数値を含む文字列の場合は、数値部分を取り出してマッチングを実施する。
			if(cellValue != undefined && !$.isNumeric(cellValue)){
				// 小数と整数のいずれか(全角ハイフンは、ハイフンとして使用することを考慮し、マイナスと見なさないことにする)
				var matcher = cellValue.match(/-?\d+\.\d+|-?\d+/);
				if(matcher){
					cellValue = matcher[0];
				}
				else {
					return matchResult;
				}
			}
			if(operator.length == 2){
				matchResult = isConsists(cellValue, operator[1], operator[0]);
			}
			// xx以上yy未満等の範囲条件
			else if(operator.length == 4){
				matchResult = isConsists(cellValue, operator[1], operator[0]) && isConsists(cellValue, operator[3], operator[2]);
			}
		}
	}
	return matchResult;
}
/**
 * フィルタ項目クリックイベント要素が持つフィルタキーを
 * 現在の選択済みフィルタ配列に追加し、処理対象のtbodyUniqueIdを返します.
 *
 */
function getDataTBodyUniqueIdByFilterEvent(event){
	var eventTarget = $(event.target);
	// イベント要素が持つフィルタキーを取得
	var strFilterKey = getArrayAttributes(eventTarget)[0];

	if(!_currentFilterKeys){_currentFilterKeys = new Object() };
	// 処理対象のtbody(行データ側)に対してIDを取得または付与する
	var filterTable = eventTarget.closest('table');
	var tbodyUniqueId = getDataTBodyUniqueId(filterTable);
	var currentFilterkeys = _currentFilterKeys[tbodyUniqueId]; // 現在適用中のフィルタ条件配列
	// 初回フィルタ操作時
	if(!currentFilterkeys){
		// フィルター対象のtable行データを取得
		var arrayLength = getTrDataRecords(filterTable, tbodyUniqueId).eq(0).children().length;
		// 列数分の配列
		currentFilterkeys = new Array(arrayLength);
	}

	// 今回のフィルタ位置インデックスを求める
	var filterIndexes = getStageIndexByDictionary(strFilterKey, eventTarget, tbodyUniqueId);
	var colIndex = filterIndexes[0];
	var stageIndex = filterIndexes[1];
	// 選択済みフィルタ配列へ今回選択のフィルタ情報を設定する
	// フィルタ項目を列インデックス毎に保持(解除時はundefinedをセット)
	// 列内多段フィルタの場合
	if(filterIndexes[2]){
		if(!currentFilterkeys[colIndex]){ currentFilterkeys[colIndex] = new Object(); }
		var elmUniqueId = useUniqueId(eventTarget.closest('ul'));
		if(strFilterKey == 'unfilter'){
			for(var stageindex in currentFilterkeys[colIndex]){
				// フィルタ解除操作時はどの配列内の情報であるかを特定する
				if(currentFilterkeys[colIndex][stageindex]['ulUniqueId'] == elmUniqueId){
					// 配列内のフィルタ条件情報を削除
					delete currentFilterkeys[colIndex][stageindex];
					break;
				}
			}
		}
		else {
			// 列＆段目＆フィルター条件と、フィルタ解除時のフィルタキーを特定する為のuniqueIdを保持する
			if(!currentFilterkeys[colIndex][stageIndex]){ currentFilterkeys[colIndex][stageIndex] = new Object(); }
			currentFilterkeys[colIndex][stageIndex]['filterKey'] = strFilterKey;
			currentFilterkeys[colIndex][stageIndex]['ulUniqueId'] = elmUniqueId;
		}
	}
	// シングルフィルタの場合
	else {
		if(strFilterKey == 'unfilter'){
			currentFilterkeys[colIndex] = undefined;
		}
		else {
			currentFilterkeys[colIndex] = strFilterKey;
		}
	}
	_currentFilterKeys[tbodyUniqueId] = currentFilterkeys;
	return tbodyUniqueId;
}
/**
 * フィルタ対象テーブルの初期表示レコードと
 * 対象ヘッダの全てのフィルタキーを保持(※)し
 * 紐づくtbodyUniqueId(※)を返します.
 * ※useUniqueId()で一意なidを付与または取得
 *
 */
function setGlobalFilterMembers(targetTable){
	var filterTable = $(targetTable);
	// 処理対象のtbody(行データ側)に対してIDを取得または付与する
	var tbodyUniqueId = getDataTBodyUniqueId(filterTable);

	// グローバル変数を連想配列として初期化
	if(!_filterKeysDictionary){_filterKeysDictionary = new Object()};
	
	var filterKeyArray = _filterKeysDictionary[tbodyUniqueId];
	if(filterKeyArray != undefined){ return tbodyUniqueId; } // tbodyUniqueIdを返して処理を終了
	
	/** ▽▼ 初回処理時 ▼▽ */
	// フィルターの存在するtableヘッダ内のul要素配列を取得
	var ulListArray = getUListArray(filterTable);
	// 取得したul要素分繰り返す
	var offsetIndex = 0;
	ulListArray.each(function(ulIndex, ulList){
		var filterList = $(ulList);
		var headCell = filterList.closest("td,th");
		var filterIndexes = scanStageIndex(filterList, headCell, 'ul');
		var stageIndex = 0;
		var colIndex = 0;
		// 列内多段フィルタの場合
		if(filterIndexes[2]){
			colIndex = filterIndexes[0]; // 列インデックスはフィルタのあるth(td)の列インデックス
			stageIndex = filterIndexes[1];
		}
		else {
			if(!filterIndexes[0]){
				// キャレット[▼]と表示文字列が別セルの場合
				colIndex = ulIndex;
			}
			else {
				colIndex = filterIndexes[0]; // 列インデックスはフィルタのあるth(td)の列インデックス
			}
		}

		// フィルタ配列を生成する
		if(!filterKeyArray){
			filterKeyArray = new Array(ulListArray.length);
		}
		if(!filterKeyArray[colIndex]){
			// 連想配列とする
			filterKeyArray[colIndex] = new Object();
		}
		filterKeyArray[colIndex][stageIndex] = getArrayAttributes(ulList, 'data-name', ['unfilter','filter','stageFilter']);
		// ヘッダのセル(th or td)と同一のuniqueIdを連想配列へ追加
		var colUniqueId = useUniqueId(headCell);
		filterKeyArray[colIndex]['colUniqueId'] = colUniqueId;
	});
	// グローバル配列へフィルターのキー配列を登録する
	_filterKeysDictionary[tbodyUniqueId] = filterKeyArray;

	return tbodyUniqueId;
}

/**
 * グローバル変数のカレントフィルタ条件配列が
 * フィルタ条件を保持している場合はtrueを返します.
 * @return 一つでもフィルタ条件を保持している場合はtrue
 * @param {Array{string}} filterArray 現在画面選択中のフィルタ条件配列
 * 
 */
function hasFilterKey(filterArray){
	var result = false;
	$.each(filterArray, function(index, obj){
		if($.type(obj) == "object"){
			for(var key in obj){
				if(obj[key] != undefined){
					result = true;
					break; // for文に対するbreak
				}
			}
			return result ? false : true; // break or continueの代用
		}
		else if($.isArray(obj)){
			result = hasFilterKey(obj);
			return result ? false : true; // break or continueの代用
		}
		else if(obj != null){
			result = true;
			return false; // breakの代用
		}
	});
	return result;
}
/**
 * 引数のセル内より引数指定ul要素の位置インデックスを返します.
 * ※列内多段フィルタを含む初期カウント時にのみ使用
 * 
 * @return {Array}列インデックス(配列要素[0]へ格納)および段数位置インデックス(配列要素[1]へ格納)、多段フィルタの場合、配列要素[2]はtrue
 * @param {Array{DOM}} targetUlList 第二引数のセル内に存在する、位置インデックスを求める対象のDOM要素
 * @param {Array{DOM}} oneCell 第一引数の要素を含むth(td)要素
 * @param {string} escapeNode 抽出から除外する要素名(例:'ul','li'などを指定するとul要素またはli要素を除外して評価する)
 *
 */
function scanStageIndex(targetUlList, oneCell, escapeNode){
	var result = [$(oneCell).index(), 0, false];
	var isStageFilter = ($(oneCell).find("ul[data-name^='stageFilter']").length > 1) ? true : false;
	// シングルフィルタの場合、「何段目」を0(ゼロ)として処理し
	// 自セル内にul要素を除きテキストノードが見つからなければ
	// colspan対象の列として扱う(列インデックスを使用しない)
	if(!isStageFilter){
		var oneCellText = getTrimmedDeepTextNode(targetUlList.closest('td,th'), 'ul');
		if(oneCellText.length == 0){
			result[0] = undefined;
		}
		return result;
	}
	var headTextNodeArray = getTrimmedDeepTextNode(oneCell, escapeNode);
	var ulStageText = searchPreviousClosestTextNodeText(targetUlList);
	if(!ulStageText || ulStageText.length == 0) { return result; } // テキストが見つからなければ段の位置は0として返し処理を終える
	// 列内の何段目であるかをインデックスとして取得
	var stageIndex = $.inArray(ulStageText, headTextNodeArray);
	result[1] = stageIndex;
	result[2] = true; // 多段フィルタの場合はtrue
	return result;
}
/**
 * tbodyUniqueIdに一致するグローバル変数内のフィルタキー配列から
 * 対象ul要素が属する列インデックスと列内の段数位置インデックスを求めて返します.
 * 
 * @return {Array}列インデックス(配列要素[0]へ格納)および段数位置インデックス(配列要素[1]へ格納)、多段フィルタの場合、配列要素[2]はtrue
 * @param {string} strFilterKey フィルタ条件
 * @param {Array{DOM}} targetChild th(td)配下の子・子孫要素(ul,li,label等)となるイベント発生元、あるいはul要素
 * @param {String} tbodyUniqueId targetTableでul要素を持たないtbody要素から取得または付与したuniqueId
 *
 */
function getStageIndexByDictionary(strFilterKey, targetChild, tbodyUniqueId){
	var oneCell = $(targetChild).closest('th,td');
	var result = [0, 0, false];
	var colUniqueId = useUniqueId(oneCell);
	var isStageFilter = (oneCell.find("ul[data-name^='stageFilter']").length > 1) ? true : false;
	result[2] = isStageFilter;
	for(var key in _filterKeysDictionary[tbodyUniqueId]){
		// ディクショナリとイベント発生元のuniqueIdを元に列インデックスを求める
		if(_filterKeysDictionary[tbodyUniqueId][key]['colUniqueId'] == colUniqueId){
			result[0] = parseInt(key); // keyの位置が列インデックス

			// 多段フィルタの場合、列内の何段目かを求める
			if(isStageFilter){
				// 何段目であるかのインデックスを取得
				for(var objectKey in _filterKeysDictionary[tbodyUniqueId][key]){
					// 配列且つフィルタキーが存在する位置
					if($.isArray(_filterKeysDictionary[tbodyUniqueId][key][objectKey])
					&& $.inArray(strFilterKey, _filterKeysDictionary[tbodyUniqueId][key][objectKey]) != -1){
						result[1] = parseInt(objectKey);
						break;
					}
				}
			}
			break;
		}
	}
	return result;
}
/**
 * 引数のテーブル内よりul要素を持たないtbody要素に対してuniqueIdを取得または付与し
 * 対象のuniqueIdを返します。
 * 
 * @return {String} targetTableでul要素を持たないtbody要素から取得または付与したuniqueId
 * @param {DOM} targetTable ul要素を持たないtbody要素を持つtable要素
 *
 */
function getDataTBodyUniqueId(targetTable){
	var filterTable = $(targetTable);
	// tbody(行データ側)を取得
	var tBodySelector = "tbody:not(:has('ul'))"; // ul要素を持たないtbody要素
	var tBody = filterTable.children(tBodySelector);
	// ヘッダ固定一覧表の場合
	if(tBody.length == 0) {
		var divSelectorFrom = "div[id]:not([id*=head],[id*=foot])"; // headerとfooterを除くidを持ったdiv要素
		var filterTableDivId = filterTable.closest("div[id*='head'],[id*='foot']").attr('id');
		var selectorAppendant = filterTableDivId.match("left|right");
		var divSelectorTo = selectorAppendant != null ? divSelectorFrom.replace("[id]", "[id*=" + selectorAppendant + "]") : divSelectorFrom;
		// 自divは除く
		divSelectorTo = divSelectorTo + ":not([id=' + filterTableDivId + '])";
		
		// 行データ部のtbodyを取得(※フィルタ条件要素(ul)の親要素の内、headerとfooterを除くdiv配下より、ul要素を持たないtbodyを取得)
		tBody = filterTable.closest(divSelectorFrom).find(divSelectorTo).find(tBodySelector);
	}
	return useUniqueId(tBody); // tBodyからuniqueIdを取得(無ければ付与)して返す
}
/**
 * 引数のテーブル内でul要素を持たないtbody要素のdata-uniqueid配下に存在する
 * tr要素の配列を返します.
 * 
 * @return {Array{DOM}} tbodyUniqueIdに一致するtbody配下のtr要素
 * @param {DOM} targetTable ul要素を持たないtbody要素を持つtable要素
 * @param {String} tbodyUniqueId targetTableでul要素を持たないtbody要素から取得または付与したuniqueId
 *
 */
function getTrDataRecords(targetTable, tbodyUniqueId){
	var filterTable = $(targetTable);
	var tBody = filterTable.children("tbody[data-uniqueid='" + tbodyUniqueId + "']")
	// ヘッダ固定一覧表の場合
	if(tBody.length == 0){
		tBody = filterTable.closest("div[class='row']").find("tbody[data-uniqueid='" + tbodyUniqueId + "']");
	}
	var records;
	// tbody(行データ側)を取得する際のセレクタ
	var trSelector = "tr:not(:has('th'))";
	// フィルターメモリがON(true)の場合
	if(isMatched(filterTable.data('use-filter-memory'), /true/i)){
		if(!_originalTableDataRow){
			_originalTableDataRow = new Object();
		}
		// グローバル配列へ初期表示状態のレコード配列を登録する
		if(!_originalTableDataRow[tbodyUniqueId]){
			_originalTableDataRow[tbodyUniqueId] = tBody.children(trSelector);
		}
		records = _originalTableDataRow[tbodyUniqueId];
	}
	else {
		records = tBody.children(trSelector);
	}
	return records;
}
/**
 * 引数のテーブル内に存在するul要素の配列を返します.
 * 
 * @return {Array{DOM}} targetTable配下に存在する全てのul要素
 * @param {DOM} targetTable ul要素を持つtable要素
 *
 */
function getUListArray(targetTable){
	// ulを取得
	var filterTable = $(targetTable);
	var ulListArray = filterTable.find("tr:has('ul')").find("ul[data-name^='filter'],[data-name^='stageFilter']");
	// 見つからなかった場合
	if(ulListArray.length == 0){
		ulListArray = filterTable.closest("div[class='row']").find("tr:has('ul')").find("ul[data-name^='filter'],[data-name^='stageFilter']");
	}
	return ulListArray;
}
/**
 * 引数の要素と同階層、且つ、直近の前方方向よりテキストノードを含む要素を検索し
 * 最初に見つかったテキストノードより取得したテキストの配列を返します
 * @return {string} 直近で最初に見つかったテキストノードより抽出したテキスト文字列
 * @param {Array{DOM}} contentsList 抽出対象のDOM要素配列
 * 
 */
function searchPreviousClosestTextNodeText(currentNode, escapeNode){
	var prevNode = currentNode.prev();
	var prevNodeText = getTrimmedDeepTextNode(prevNode, escapeNode);
	if(!prevNodeText || prevNodeText.length == 0 || !prevNodeText[0]) {
		// 再帰呼び出し(同階層の要素を前方方向へ向かって見つかるまで再検索)
		prevNodeText = searchPreviousClosestTextNodeText(prevNode);
	}
	return $.isArray(prevNodeText) ? prevNodeText[0] : prevNodeText;
}
/**
 * フィルタ選択時のキャレット色を変更します
 * @param {Event} event 要素から送出されたイベントオブジェクト
 */
function caretColorChange(event){
	var eventTarget = $(event.target);
	// 処理対象のフィルタ項目かどうかをチェック
	if(isMatched(removeSpace(eventTarget.text()), /.+\(0\)$/)){ return; } // ※0件項目が選択された場合は処理しない
	// イベント要素が持つフィルタキーを取得
	var strFilterKey = getArrayAttributes(eventTarget)[0];
	var ulElement = $(event.currentTarget); // バブリング(伝播)で到達した要素(ul)
	var caret = ulElement.parent('*').find(":has([class='caret'],strong)"); // caretまたはstrong
	if(strFilterKey == 'unfilter'){
		colorChange(caret, 'color', '');
	}
	else {
		colorChange(caret, 'color', 'red');
	}
}
////// ▲△▲△▲△▲△ === tableフィルタ処理部 === ▲△▲△▲△▲△▲△ //////
