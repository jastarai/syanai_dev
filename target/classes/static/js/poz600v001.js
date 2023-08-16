$(function(){
	
	// datetimepicker設定
	$('.date').datetimepicker({
		locale: 'ja',
		format : 'YYYY/MM/DD'
	});
	
	// 「新規登録」ボタン押下時イベント
	$('button[name = add]').click(function(){
		// ワークフローマスタ登録画面に遷移
		location.href = './poz600v002.html';
	});
	
	// 「編集」ボタン押下時イベント
	$('button[name = edit]').click(function(){
		// 同一行のワークフローIDを取得する.
		var $tr = $(this).parent().parent();// 選択ボタン行のTR要素.
		var workflowId = $tr.children().eq(2).html();// ワークフローID
		// ワークフローマスタ登録画面に遷移
		location.href = encodeURI("./poz600v002.html?workflowId=" + workflowId);
	});
	
});
