$(function(){
	// POZ600V003：承認者選択画面（モーダル）

	// 戻り値設定
	window.returnValue = "";

	// 新規ウィンドウが開いてしまうのを回避するため、画面名（window.name）を設定する。
	// また、formタグには同じ名前の「target」属性を設定する。
	window.name = 'modalwindow';
	
	// 閉じるボタン押下時
	$('button[name = close]').click(function(){
		window.close();
	});
	
});

// 選択ボタン押下時
function clickSelect (id, name) {
	var retObj = {};
	retObj.id = id;
	retObj.name = name;
	window.returnValue = JSON.stringify(retObj);
	window.close();
}
