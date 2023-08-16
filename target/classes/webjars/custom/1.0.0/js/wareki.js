/**
 * @fileOverview wareki.js
 *
 * @author 日本システム技術株式会社
 * @version 1.0.0
 * @supported　InternetExploer11
 * @license 2016 Copyright © Japan System Techniques Co., Ltd. All Rights Reserved.
 */

/**
 * 元号設定
 * @return {void}
 * @example
 * 
 */
function setEraOption() {
	//元号のセレクトボックスに<option>を追加
	//サーバーサイドのJavaプログラムでフォームへの入力値を受け取ることを想定し、
	//valueにはJavaの和暦クラスでERAが返す定数（0～4）を設定
	var era = $("#cs-birth-era");
	era.append($('<option>').html('---').val(0));
	era.append($('<option>').html('明治').val(1));
	era.append($('<option>').html('大正').val(2));
	era.append($('<option>').html('昭和').val(3));
	era.append($('<option>').html('平成').val(4));
}

/**
 * 年設定
 * @return {void}
 * @example
 * 
 */
function setYearOption() {
	// 年のセレクトボックスに<option>を追加
	// 元号のvalueに対応した年数をセット。
	// 平成については現在日付を基準にして最大値を割り出しています
	var year = $("#cs-birth-year");
	var selectEra = $("#cs-birth-era").val();

	year.children().remove();
	var maxYear = 0;
	switch (selectEra) {
	case "1":
		maxYear = 45;
		break;
	case "2":
		maxYear = 15;
		break;
	case "3":
		maxYear = 64;
		break;
	case "4":
		maxYear = new Date().getFullYear() - 1988;
		break;
	default:
		maxYear = 0;
		break;
	}
	year.append($('<option>').html('---').val(0));
	for (var i = 1; i <= maxYear; i++) {
		year.append($('<option>').html(i).val(i));
	}
}

/**
 * 月設定
 * @return {void}
 * @example
 * 
 */
function setMonthOption() {
	// 月のセレクトボックスに<option>を追加
	var month = $("#cs-birth-month");

	month.append($('<option>').html('---').val(0));
	for (i = 1; i <= 12; i++) {
		month.append($('<option>').html(i).val(i));
	}
}

/**
 * 日設定
 * @return {void}
 * @example
 * 
 */
function setDayOption() {
	// 日のセレクトボックスに<option>を追加
	// 2月の日数は年によって異なるため、年&月に対応した日数の<option>を追加
	var day = $("#cs-birth-day");
	var selectYear = $("#cs-birth-year").val();
	var selectMonth = $("#cs-birth-month").val();
	var selectDay = day.val();

	// 閏年に対応するため和暦から西暦に変換
	var seirekiYear = warekiToSeireki($("#cs-birth-era").val(), selectYear);
	var dateobj = new Date(seirekiYear, selectMonth, 0);

	day.children().remove();
	$("#cs-birth-day").append($('<option>').html('---').val(0));
	for (var i = 1; i <= dateobj.getDate(); i++) {
		day.append($('<option>').html(i).val(i));
	}
}

/**
 * 西暦変換
 * @return {void}
 * @example
 * 
 */
function warekiToSeireki(era, year) {
	// 和暦から西暦に変換
	if (era == 1) {
		return parseInt(year) + 1867;
	} else if (era == 2) {
		return parseInt(year) + 1911;
	} else if (era == 3) {
		return parseInt(year) + 1925;
	} else if (era == 4) {
		return parseInt(year) + 1988;
	}
}

/**
 * 日再設定
 * @return {void}
 * @example
 * 
 */
function changeDayOption() {
	// 他のセレクトボックスに連動して日のセレクトボックスの<option>を調整
	// 年や月のセレクトボックスを変更した際に不整合とならないよう、
	// 日のセレクトボックスの<option>を再設定
	var day = $("#cs-birth-day");
	var selectDay = day.val();

	setDayOption();
	// 日が選択済みの場合、再度選択状態にする
	(selectDay == null) ? day.val(day.val()[0]) : day.val(selectDay);
}

/**
 * 初期処理
 * @return {void}
 * @example
 * 
 */
$(document).ready(function() {
	// イベントに応じて上記の関数を呼び出す
	// DOM要素の構築完了時にセレクトボックスを初期化する
	// alert("ready");
	setEraOption();
	setYearOption();
	setMonthOption();
	setDayOption();
});

/**
 * 年設定処理
 * @return {void}
 * @example
 * 
 */
$(function() {
	// 元号を変更したら年のセレクトボックスの<option>をセットする
	$("#cs-birth-era").on("change", function() {
		setYearOption();
	});
});

/**
 * 閏年設定
 * @return {void}
 * @example
 * 
 */
$(function() {
	// 年を変更し、月が選択済みかつ2の場合、日のセレクトボックスの<option>を調整する（閏年対応）
	$("#cs-birth-year").on(
			"change",
			function() {

				var seirekiYear = warekiToSeireki($("#cs-birth-era").val(), $(
						"#cs-birth-year").val());

				$("#seireki").val(seirekiYear);

				if ($("#cs-birth-month").val() == 2) {
					changeDayOption();
				}
			});
});

/**
 * 日設定処理
 * @return {void}
 * @example
 * 
 */
$(function() {
	//月を変更したら日のセレクトボックスの<option>を調整する
	$("#cs-birth-month").on("change", function() {
		changeDayOption();
	});
});