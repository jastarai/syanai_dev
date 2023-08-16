$(function() {

	// 初期設定
	$("select[class*='cs-date-year']").each(function() {

		var targetnum = getTargetnumDateItem($(this));
		var monthObj = $(".cs-date-month_" + targetnum);
		var dayObj = $(".cs-date-day_" + targetnum);

		var yearVal = $(this).val();
		var monthVal = monthObj.val();
		var dayVal = dayObj.val();

		if (yearVal != null && yearVal != ""){
			getMonth($(this), selectMonthInfo);

			if (monthVal != null && monthVal != ""){
				monthObj.val(monthVal);
			}
		} else {
			monthObj.children().remove();
		}

		if (monthVal != null && monthVal != ""){
			getDay(monthObj, selectDayInfo);

			if (dayVal != null && dayVal != ""){
				dayObj.val(dayVal);
			}
		} else {
			dayObj.children().remove();
		}
	});

	/* 年選択時 */
	$("select[class*='cs-date-year']").change(function(){
		getMonth($(this), selectMonthInfo);
	});
	// 月選択時
	$("select[class*='cs-date-month']").change(function(){
		getDay($(this), selectDayInfo);
	});
	
});

function getMonth(obj, selectMonthInfo) {

	var targetnum = getTargetnumDateItem(obj);

	// 月・日 selectObj 取得
	var monthObj = $(".cs-date-month_" + targetnum);
	var dayObj = $(".cs-date-day_" + targetnum);

	// 月・日 option 削除
	monthObj.children().remove();
	dayObj.children().remove();

	// 先頭 option 追加 
	monthObj.append($('<option>').html("").val(""));

	// 選択した年 option値 取得
	var keyYear = obj.val();

	// ドロップダウン用月情報のKey配列
	var monthKeyArray = Object.keys(selectMonthInfo);

	if(isPartialMatchKey(monthKeyArray, keyYear)){
		for (var i in selectMonthInfo){
			// 同西暦内で和暦が跨る年場合、月取得
			if ( i.indexOf(keyYear) != -1) {
				monthObj.append($('<option>').html(selectMonthInfo[i]).val(selectMonthInfo[i]));
			}
	    }
	} else {
		for (var j = 1; j <= 12; j++) {
			var monthval = String(j);
			if(monthval.length < 2){
				monthval = "0" + monthval;
			}
			monthObj.append($('<option>').html(monthval).val(monthval));
		}
	}
}

function getDay(monthObj, selectDayInfo) {

	var targetnum = getTargetnumDateItem(monthObj);

	// 年・日 selectObj 取得
	var yearObj = $(".cs-date-year_" + targetnum);
	var dayObj = $(".cs-date-day_" + targetnum);

	// 日 option 削除
	dayObj.children().remove();

	// 先頭 option 追加 
	dayObj.append($('<option>').html("").val(""));

	// 選択した年、月 option値 取得
	var keyYear = yearObj.val();
	var keyMonth = monthObj.val();
	var key = keyYear + keyMonth;

	// ドロップダウン用月情報のKey配列
	var dayKeyArray = Object.keys(selectDayInfo);

	if(isPartialMatchKey(dayKeyArray, key)){	
		// 同西暦内で和暦が跨る年 かつ 和暦を複数持つ月の場合、日取得
		for (var i in selectDayInfo){
			if ( i.indexOf(key) != -1) {
				dayObj.append($('<option>').html(selectDayInfo[i]).val(selectDayInfo[i]));
			}
	    }
	} else {
		// 通常月の最終日取得
		var endDay = getEndDayInMonth(keyYear, keyMonth);

		for (var j = 1; j <= endDay; j++) {
			var dayval = String(j);
			if(dayval.length < 2){
				dayval = "0" + dayval;
			}
			dayObj.append($('<option>').html(dayval).val(dayval));
		}
	}
}

// 各月の最終日取得
function getEndDayInMonth(keyYear, keyMonth){
	var endDay = 31;
	switch (keyMonth){
		case '02':
			// 閏年判定
			if(isLeapYear(keyYear.substring(1,5))) {
				endDay = 29;
			} else {
				endDay = 28;
			}
			break;
		case '04':
			endDay = 30;
			break;
		case '06':
			endDay = 30;
			break;
		case '09':
			endDay = 30;
			break;
		case '11':
			endDay = 30;
			break;
	}
	return endDay;
}

function getTargetnumDateItem(obj) {
	var tar = obj.attr("class").split(" ");

	var target = "";
	for(var i = 0; i < tar.length; i++){
		// cs-date-yearを含む場合、targetを取得 要判定
		if(tar[i].match("cs-date-year") || tar[i].match("cs-date-month")){
			// cs-date-year 含むデータ を _でsplitした[1]がtargetNumとなる。
			target = tar[i].split("_")[1];
			return target;
		}
	}
}

//key 存在確認(部分一致)
function isPartialMatchKey(map, key) {

	for(var i = 0; i < map.length; i++){
		if(map[i].match(key)) {
			return true;
		}
	}
	return false;
}

// 閏年判定
function isLeapYear(year) {

	if(year % 4 == 0){
		if(year % 100 == 0){
			if(year % 400 == 0){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}else{
		return false;
	}
}

