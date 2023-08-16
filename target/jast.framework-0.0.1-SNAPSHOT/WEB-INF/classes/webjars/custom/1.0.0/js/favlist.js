window.name = 'modalwindow';
// 登録ボタン押下時.
$(function(){
	
	// 登録・更新ボタン押下時.
	$('button[name = save]').click(function(){
		// 「出力内容」に入っている項目を全選択する.
		$('select[name = addFavList] option').prop('selected', true);
		// submit
        document.getElementById('mainForm').submit();
     });
	
	// 削除ボタン押下時.
	$('button[name = delete]').click(function(){
		// 削除処理実施後、「閉じる」ボタン以外を押下不可にする.
		document.getElementById('mainForm').action = favListPath + '/04';
        document.getElementById('mainForm').submit();

	});
	
	// 連続作成ボタン押下時.
	$('button[name=favListInsertOpen]').click(function(){
		// 新規作成画面を表示する.
		// favNo,更新日時,お気に入り名称 を空白にする
		$('input[name=favListNo]').val(null);
		$('input[name=updateDate]').val(null);
		$('input[name=favListName]').val(null);
		document.getElementById('mainForm').action = favListPath + '/01-2';
        document.getElementById('mainForm').submit();
		
	});
	
	// 閉じるボタン押下時.
	$('button[name = close]').click(function(){
		setParentReturn();
		window.close();

	});
	
	
	
});

// 追加・解除ボタン押下時の制御
	function moveUp() {
		var selectbox = document.getElementById('outputSelected');
		var option_list = selectbox.getElementsByTagName('option');
		for (var i = 0; i < option_list.length; i++) {
			if (option_list[i].selected) {
				if (i > 0 && !option_list[i - 1].selected) {
					selectbox.insertBefore(option_list[i], option_list[i - 1]);
				}
			}
		}
		selectbox.focus();
	}

	function moveDown() {
		var selectbox = document.getElementById('outputSelected');
		var option_list = selectbox.getElementsByTagName('option');
		for (var i = option_list.length - 1; i >= 0; i--) {
			if (option_list[i].selected) {
				if (i < option_list.length - 1 && !option_list[i + 1].selected) {
					selectbox.insertBefore(option_list[i + 1], option_list[i]);
				}
			}
		}
		selectbox.focus();
	}
	
	function moveAdd() {
		var from = document.getElementById("outputItems");
		var to = document.getElementById("outputSelected");
		if (from.selectedIndex != -1) {
			to.appendChild(from.options[from.selectedIndex]);
		}
	}

	function moveDelete() {
		var from = document.getElementById("outputSelected");
		var to = document.getElementById("outputItems");

		if (from.selectedIndex != -1) {
			var o = from.options[from.selectedIndex];
			var n = o.value.substring(1);

			for (var i = 0, j = to.options.length; i < j; i++) {
				if (to.options[i].value.substring(1) > n) {
					to.insertBefore(o, to.options[i]);
					return;
				}
			}
			to.appendChild(o);
		}
	}

	/**
	 * メッセージが表示されている（更新処理実行後）場合、戻り値を設定する.
	 */
	function setParentReturn(){
		if ($('div[name = message]').html()) {
			var favListNo = $('input[name = favListNo]').val();
			favListNo = (favListNo == null || favListNo == '')? '0': favListNo;
			window.returnValue = favListNo;
		}
	}
	
	
	/**
	 * 画面ロード後
	 */
	$(window).on('load',function(){
		// 削除ボタン処理実施後は、「閉じる」ボタン以外を非活性にする.
		if($('input[name=delFlg]').val() == 'true'){
			$('button[name=save]').attr("disabled", true);
			$('button[name=delete]').attr("disabled", true);
		}
	});