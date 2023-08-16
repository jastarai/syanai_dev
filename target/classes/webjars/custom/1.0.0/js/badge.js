/**
 * @fileOverview jcommon.js
 *
 * @author 日本システム技術株式会社
 * @version 1.0.0
 * @supported　InternetExploer11
 * @license 2016 Copyright © Japan System Techniques Co., Ltd. All Rights Reserved.
 */

/**
 * 情報ありバッチ表示制御
 * @return {false} false:情報存在時
 * @example
 * 
 */
// 情報ありバッジ表示制御.
$(document).ready(function(){
  // イベントハンドラの登録などのコード
	var appendStr = '<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>';// 情報ありバッジ.
	$(document).each(function(i, element){
		// フォーム単位で入力エリアの状態をチェックする.
		// ■パネルにバッジを表示する場合■
		$(element).find('[data-name = panelBadge]').each(function(j, badgeElement){
			var inputCheck = false;
			// チェック系入力のチェック
			$(badgeElement).find(':checked').each(function(i, checkedElement) {
				if ($(checkedElement).prop('checked')) {
					inputCheck = true;
					return false;
				}
			});
			if(!inputCheck) {
				// テキスト系の入力状態チェック
				$(badgeElement).find('textarea, :text, select').each(function(i, textElement) {
					if ($(textElement).val() != null && $(textElement).val() != '') {
						inputCheck = true;
						return false;
					}
				});
			}
			if(!inputCheck){
				// ラベル系のチェック（一旦、<p>タグに出力することを想定している）
				$(badgeElement).find('p').each(function(i, elem) {
					if ($(elem).text() != null && $(elem).text() != '') {
						inputCheck = true;
						return false;
					}
				});
			}
			if(!inputCheck){
				// リッチテキストのチェック
				$(badgeElement).find('div[data-name^=richText]').each(function(i, richTextElement) {
					if ($(richTextElement).text() != null && $(richTextElement).text() != '') {
						inputCheck = true;
						return false;
					}
				});
			}
			if(inputCheck) {
				// 対象のpanel-titleに、バッジを追加する.
				$(badgeElement).find('.panel-heading').children('.panel-title').append(appendStr);
			}
		});
		
		// ■タブにバッジを表示する場合■
		$(element).find('[data-name = tabBadge]').each(function(j, badgeElement){
			
			var inputCheck = false;
			// テキスト系の入力状態チェック（テキストボックス、テキストエリア、セレクトボックスの入力状態を確認する）
			$(badgeElement).find("textarea, :text, select").each(function(i1, textElement) {
				if ($(textElement).val() != null && $(textElement).val() != '') {
					inputCheck = true;
					var $tab_name = $(badgeElement).attr('id');
					$(element).find('.nav-tabs [data-name=' + $tab_name + ']').find('a').append(appendStr);
					return false;
				}
			});
			// チェック系入力のチェック（ラジオボタン・チェックボックスのチェック状態を確認する）
			if(!inputCheck) {
			  	$(badgeElement).find(":checked").each(function(i, checkedElement) {
					if ($(checkedElement).prop("checked") != '') {
						inputCheck = true;
						var $tab_name = $(badgeElement).attr('id');
						$(element).find('.nav-tabs [data-name=' + $tab_name + ']').find('a').append(appendStr);
						return false;
					}
				});
			}
			if(!inputCheck){
				// リッチテキストのチェック
				$(badgeElement).find('div[data-name^=richText]').each(function(i, richTextElement) {
					if ($(richTextElement).text() != null && $(richTextElement).text() != '') {
						inputCheck = true;
						var $tab_name = $(badgeElement).attr('id');
						$(element).find('.nav-tabs [data-name=' + $tab_name + ']').find('a').append(appendStr);
						return false;
					}
				});
			}
			// ラベル系のチェック（一旦、<p>タグに出力することを想定している）
			if(!inputCheck) {
				$(badgeElement).find("p").each(function(i, elem) {
				if ($(elem).text() != null && $(elem).text() != '') {
					inputCheck = true;
					// タブコンテンツのDIV\IDを取得する
					var $tab_name = $(badgeElement).attr('id');
					// タブ側の状態を変更する.
					$(element).find('.nav-tabs [data-name=' + $tab_name + ']').find('a').append(appendStr);
					return false;
				}
			});
			}
		});	
	});
});