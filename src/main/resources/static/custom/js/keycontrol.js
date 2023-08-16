// キーコントロール（backspace）

// ブラウザの戻るボタンを無効にする.
history.pushState(null, null, null);

// backspaceを無効にする.
window.addEventListener("popstate", function() {
	history.pushState(null, null, null);
});

// ヘルプイベントの無効化.
window.onhelp = function() {
	return false;
}

// キーコントロール（function, Ctrl）
$(function(){
	
	// return値をfalseにし、キーをキャンセルする.
	$(document).keydown(function(event){
		// Ctrlキーがクリックされたか (true or false)
		var ctrlClick = event.ctrlKey;
		// クリックされたキーのコード.
		var keyCode = event.keyCode;
		// ファンクションキーの制御.
		if (keyCode == 112// F1
		 || keyCode == 113// F2
		 || keyCode == 114// F3
		 || keyCode == 115// F4
		 || keyCode == 116// F5
		 || keyCode == 117// F6
		 || keyCode == 118// F7
		 || keyCode == 119// F8
		 || keyCode == 120// F9
		 || keyCode == 121// F10
		 || keyCode == 122// F11
		 || keyCode == 123// F12
		 ) {
			return false;
		}
		
		// Ctrlボタン押下コマンドのうち、
		//  Ctrl + 0（倍率100%）
		//  Ctrl + C(コピー)
		//  Ctrl + V(ペースト)
		//  Ctrl + P(印刷) 
		// 以外を制御する.
		if (ctrlClick) {
			if(!(ctrlClick && keyCode == 48)
					&& !(ctrlClick && keyCode == 67)
					&& !(ctrlClick && keyCode == 86)
					&& !(ctrlClick && keyCode == 80)){
				return false;
			}
		}
	});
	
	/**
	 * キーダウンイベントハンドラ(input)
	 * 
	 */
	$(":input[type='text']").keydown(function(event){
		// Enterキー押下時
		if(event.keyCode == 13){
			// TODO Tab(keyCode == 9)でイベント送出したいが、イベント生成できない為、一旦保留
			var newKeyboardEvent;
			// イベント伝播停止させる
			event.stopPropagation();
			// input(text)フィールドにおけるEnterキー押下時のsubmitを抑止する
			return false;
		}
	});
});
//TODO Tab(keyCode == 9)でイベント送出したい
//			if(document.createEvent){
//			// IEの場合
//			newKeyboardEvent = document.createEvent("KeyboardEvent");
//			newKeyboardEvent.initKeyboardEvent(
//				  event.originalEvent.type               // in DOMString typeArg
//				, event.originalEvent.bubbles            // in boolean canBubbleArg
//				, event.originalEvent.cancelable         // in boolean cancelableArg
//				, event.originalEvent.view               // in views::AbstractView viewArg
//				, 'Tab'                                  // in DOMString keyArg
//				, event.originalEvent.location           // in unsigned number locationArg
//				, null                                   // in DOMString modifiersListArg
//				, event.originalEvent.repeat             // in boolean repeat
//				, event.originalEvent.locale             // in DOMString localeArg
//			);
//			Object.defineProperty(newKeyboardEvent, 'keyCode', {
//				get : function(){
//					return this.keyCodeValue
//				}
//			});
//			Object.defineProperty(newKeyboardEvent, 'which', {
//				get : function(){
//					return this.keyCodeValue
//				}
//			});
//			newKeyboardEvent.keyCodeValue = 9;
//		} else {
//		}
