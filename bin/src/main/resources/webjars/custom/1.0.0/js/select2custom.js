/**
 * @fileOverview changestrong.js
 *
 * @author 日本システム技術株式会社
 * @version 1.0.0
 * @supported　InternetExploer11
 * @license 2016 Copyright © Japan System Techniques Co., Ltd. All Rights Reserved.
 */

/**
 * コンボボックスの前方一致処理
 * @return {boolean}
 * 
 */
function matchStart (term, text) {
	if (text.toUpperCase().indexOf(term.toUpperCase()) == 0) {
	return true;
	}

	return false;
}

$.fn.select2.amd.require(['select2/compat/matcher'], function (oldMatcher) {
	$(".cs-select2").select2({
	matcher: oldMatcher(matchStart),
	language: {"noResults": function(){ return "一致しません";}},
	escapeMarkup: function (markup) { return markup; }
	})
});