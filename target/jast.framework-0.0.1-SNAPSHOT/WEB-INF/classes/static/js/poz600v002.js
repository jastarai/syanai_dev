
// 「グループマスタ」選択行の情報
var selectGroupMasterId = null;	// グループマスタID 
var selectGroupMasterName = '';	// グループマスタ名

$(function(){
	
	// datetimepicker設定
	$('.date').datetimepicker({
		locale: 'ja',
		format : 'YYYY/MM/DD'
	});
	
	// 「←追加」ボタン押下時イベント
	$('button[name = addrow]').click(function(){
		if (selectGroupMasterId === null || selectGroupMasterName === '') {
			return;
		}
		var addRowHtml = '';
		
		if (selectGroupMasterId === 0) {
			// 個人指定
			addRowHtml += '<tr class="height-sm approvalflow-row">';
			addRowHtml += '<td class="text-center table-bordered approvalflow-no" style="vertical-align: middle;" onclick="javascript:clickApprovalflow(this);"></td>';
			addRowHtml += '<td class="table-bordered border-top-none" style="vertical-align: middle;" onclick="javascript:clickApprovalflow(this);"></td>';
			addRowHtml += '<td class="sw-1 text-center table-bordered border-top-none " style="vertical-align: middle;">';
			addRowHtml += '<button type="button" class="height-sm sw-1 btn btn-black btn-sm" onclick="javascript:clickApprovalSelect(this);">選択</button>';
			addRowHtml += '</td>';
			addRowHtml += '<td class="sw-2 text-center table-bordered border-top-none " style="vertical-align: middle;">';
			addRowHtml += '<select id="selection" class="form-control full-height">';
			addRowHtml += '<option></option>';
			addRowHtml += '<option selected="selected">全員</option>';
			addRowHtml += '<option>誰か一人</option>';
			addRowHtml += '<option>回覧</option>';
			addRowHtml += '<option>合議</option>';
			addRowHtml += '</select>';
			addRowHtml += '</td>';
			addRowHtml += '</tr>';
		} else {
			// 個人指定以外
			addRowHtml += '<tr class="height-sm approvalflow-row">';
			addRowHtml += '<td class="text-center table-bordered approvalflow-no" style="vertical-align: middle;" onclick="javascript:clickApprovalflow(this);"></td>';
			addRowHtml += '<td colspan="2" class="table-bordered border-top-none" style="vertical-align: middle;" onclick="javascript:clickApprovalflow(this);">' + selectGroupMasterName + '</td>';
			addRowHtml += '<td class="sw-2 text-center table-bordered border-top-none " style="vertical-align: middle;">';
			addRowHtml += '<select id="selection" class="form-control full-height">';
			addRowHtml += '<option></option>';
			addRowHtml += '<option selected="selected">全員</option>';
			addRowHtml += '<option>誰か一人</option>';
			addRowHtml += '<option>回覧</option>';
			addRowHtml += '<option>合議</option>';
			addRowHtml += '</select>';
			addRowHtml += '</td>';
			addRowHtml += '</tr>';
		}
		
		// 「承認フロー」の未設定行の前に挿入
		$('#tableApprovalflow tbody > tr:last').before(addRowHtml)
		// 承認順を再設定
		resetApprovalflowNo();

		// 「グループマスタ」行選択解除
		selectGroupMasterId = null;
		selectGroupMasterName = '';
		$('.groupmaster').removeClass('active');
	
	});
	
	// 「↑」ボタン押下時イベント
	$('button[name = approvalflowUp]').click(function(){
		var targetRows = $('.approvalflow-row');
		var index = -1;
		for (var i = 0; i < targetRows.length; i++) {
			if ($(targetRows[i]).hasClass('active')) {
				index = i;
				break;
			}
		}
		// 未選択→処理終了
		if (index === -1) {
			return;
		}
		// 先頭行→処理終了
		if (index === 0) {
			return;
		}
		// 行を一つ上に移動させる
		$(targetRows[index]).insertBefore($(targetRows[index]).prev("tr")[0]);
		// 承認順を再設定
		resetApprovalflowNo();
		return;
	});

	// 「↓」ボタン押下時イベント
	$('button[name = approvalflowDown]').click(function(){
		var targetRows = $('.approvalflow-row');
		var index = -1;
		for (var i = 0; i < targetRows.length; i++) {
			if ($(targetRows[i]).hasClass('active')) {
				index = i;
				break;
			}
		}
		// 未選択→処理終了
		if (index === -1) {
			return;
		}
		// 最終行→処理終了
		if (index === targetRows.length -1) {
			return;
		}
		// 行を一つ下に移動させる
		$(targetRows[index]).insertAfter($(targetRows[index]).next("tr")[0]);
		// 承認順を再設定
		resetApprovalflowNo();
		return;
	});
	
	// 「→削除」ボタン押下時イベント
	$('button[name = deleteRow]').click(function(){
		$('.approvalflow-row.active').remove();
		// 承認順を再設定
		resetApprovalflowNo();
	});
	
});

// 承認順の再設定
function resetApprovalflowNo () {
	var targetCols = $('.approvalflow-no');
	var count = 1;
	targetCols.each(function(){
		$(this).html(count++);
	});
}

// 「承認フロー」選択ボタンクリック時イベント
function clickApprovalSelect (target) {
	// モーダル画面呼出し.
	var url = encodeURI("./poz600v003.html");
	var result = modal(url, "large");
	// モーダル画面からの戻り値がある（承認者を選択した）の場合
	if (result !== null && result !== "") {
		var retObj = JSON.parse(result);
		// 選択ボタンの左隣のセルの名前を変更
		$($(target).parent()).prev().text(retObj.name)
	}
}

// 「承認フロー」行クリック時イベント
function clickApprovalflow (target){
	$('.approvalflow-row').removeClass('active');
	$($(target).parents('tr')[0]).addClass('active');
}

// 「グループマスタ」行クリック時イベント
function clickGroupmaster (target, id, name){
	$('.groupmaster').removeClass('active');
	$(target).addClass('active');
	selectGroupMasterId = id;
	selectGroupMasterName = name;
}
