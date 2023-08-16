$(function(){
	// kinou01モーダル画面.
	
	// 新規ウィンドウが開いてしまうのを回避するため、画面名（window.name）を設定する。
	// また、formタグには同じ名前の「target」属性を設定する。
	window.name = 'modalwindow';
	
	// 更新ボタン押下時.
	$('button[name = save]').click(function(){
		changeDisabledBeforeSubmit(kinou0102Path, 'mainForm');
     });
	
	// 閉じるボタン押下時.
	$('button[name = close]').click(function(){
		setParenReatur();
		window.close();
	});
	
	// ブラウザの閉じるボタン押下時.
	$(window).on("beforeunload", function() {
		setParenReatur();
		return;
	});

	/**
	 * メッセージが表示されている（更新処理実行後）場合、戻り値を設定する.
	 */
	var setParenReatur = function(){
		if ($('div[data-name = message]').val() != null) {
			window.returnValue = "true";
		}
	}
	
});
