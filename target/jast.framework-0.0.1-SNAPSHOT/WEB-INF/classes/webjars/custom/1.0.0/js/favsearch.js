/* <![CDATA[ */ 
$(function(){
	// お気に入り機能（検索条件保持）.
    // Spring SecurityのCSRF対策によるトークンをajax通信で設定する 
    $.ajaxPrefilter(function(options, originalOptions, jqXHR) { 
      var token = $("meta[name='_csrf']").attr("content"); 
       var header = $("meta[name='_csrf_header']").attr("content"); 
       jqXHR.setRequestHeader(header, token); 
     }); 
	
	// 新規ウィンドウが開いてしまうのを回避するため、画面名（window.name）を設定する。
	// また、formタグには同じ名前の「target」属性を設定する。
	window.name = 'modalwindow';
	
	// 登録・更新ボタン押下時.
	$('button[name = save]').click(function(){
        document.getElementById('mainForm').submit();
     });

	// 削除ボタン押下時.
	$('button[name = delete]').click(function(){
		// 削除処理実施後、「閉じる」ボタン以外を押下不可にする.
		document.getElementById('mainForm').action = favSearchPath + '/06';
        document.getElementById('mainForm').submit();

	});

	
	// 閉じるボタン押下時.
	// 「beforeunload」の処理が問題なければ、window.close()を実行するだけにしたいところ。
	$('button[name = close]').click(function(){
		if(!$('input[name = favSearchNo]').val()){
			//　新規登録画面の場合
			deleteWork($(this));
		}else{
			// 更新画面の場合.
			setParentReaturn();
			window.close();
		}

	});
	
	/**
	 * ワークテーブルからデータを削除し、画面を閉じる.
	 * 
	 */
	var deleteWork = function(objButton){
		
        // 多重送信を防ぐため通信完了までボタンを非活性にする
		if(objButton){
			objButton.attr("disabled", true);			
		}
        var columnCnt = 0;
        var form = {"kinouId" : $('input[name = kinouId]').val()};
        // 通信実行
        $.ajax({
          type : "post",
          url : favSearchPath + '/02',
          data : JSON.stringify(form), // JSONデータ本体
          contentType : 'application/json', // リクエストの Content-Type
          dataType : "json", // レスポンスをJSONとしてパースする
        }).done(function(form) { // 200 OK時
            // 削除成功時.
        	// 特に何もしない.
        }).fail(
            function(XMLHttpRequest, textStatus, errorThrown) {
              alert("XMLHttpRequest : " + XMLHttpRequest.status
                  + "\ntextStatus : " + textStatus + "\nerrorThrown : "
                  + errorThrown.message);
        }).always(function() { // 成功・失敗に関わらず通信が終了した際の処理
            	if(objButton){
	            	objButton.attr("disabled", false); // ボタンを再び活性にする
	            	setParentReaturn();
	        		window.close();
            	}else{
            		setParentReaturn();
        			return;	
            	}
        });
	}
	
});

/**
 * メッセージが表示されている（更新処理実行後）場合、戻り値を設定する.
 */
var setParentReaturn = function(){
	if ($('div[name = message]').html()) {
		window.returnValue = $('input[name = favSearchNo]').val();
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

/*]]>*/