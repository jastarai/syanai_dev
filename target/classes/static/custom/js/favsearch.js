// お気に入り機能（検索条件）の親画面側の処理Javascript.
// お気に入り一覧の制御と、お気に入り画面の表示処理制御を行う.
$(function(){
    // Spring SecurityのCSRF対策によるトークンをajax通信で設定する 
    $.ajaxPrefilter(function(options, originalOptions, jqXHR) { 
      var token = $("meta[name='_csrf']").attr("content"); 
       var header = $("meta[name='_csrf_header']").attr("content"); 
       jqXHR.setRequestHeader(header, token); 
     }); 
    
	// お気に入り一覧からの選択時
	$('a[class = cs-fav-search-href]').on('click', function(event){
		// ハッシュリンクキャンセル
		event.preventDefault();
		// 入力値のクリア.
		$(this).parents('.panel-primary').find("textarea, :text, select").val("").end().find(":checked").prop("checked", false);

		var selectFavSearchNo = $(this).attr('id').replace( /cs-fav-search-no-/g , '');
		// 選択されたお気に入り情報に紐づくお気に入り登録値情報をhidden項目から取得する.
		$(document).find('div[data-name = favSearchHidden]').each(function(j, favElement){
			// お気に入りの値をhidden項目の名前と一致する入力エリアに反映する.
			$(favElement).find('input').each(function(i, favInput) {
				if($(favInput).attr('class') == selectFavSearchNo){
					var hiddenInputName = $(favInput).attr('name');
					var inputName = hiddenInputName.replace(/hidden_/g, '');
					
					var targetInput = '[name = ' + inputName + ']';
					var target = $('div[id=cs-fav-search-input]').find(targetInput);

					// 値設定.
					if($(target).attr('type') == 'radio'){
						// ラジオボタンの場合の値設定
						$(target).val([$(favInput).val()]);
					}else if($(target).attr('type') == 'checkbox'){
						// チェックボックスの場合、該当の値を保持しているinputにチェックを入れる					
						var favInput = $(favInput).val().split("、");
						for(var j =0; j < favInput.length; j++){
							$('[name = ' + inputName + '][value = '+ favInput[j] +']').prop('checked', true);
						}
						
					}else{
						$(target).val($(favInput).val());
					}
				}
			});
		});
		
		// テキストボックスにお気に入り名称を設定する.
		// 活性⇒値の設定⇒非活性　にする.
		$('input[name = favSearchNameSelect]').prop('disabled', false);
		$('input[name = favSearchNameSelect]').val($(this).children('span').html());
		$('input[name = favSearchNameSelect]').prop('disabled', true);
		// hidden項目に選択されたfavIDを設定する.
		$('input[type="hidden"][name="favSearchNo"]').val(selectFavSearchNo);
		
		// [お気に入り]お気に入り名称が選択されていない場合は編集ボタンを非活性にする.		
		if($(this).html()){
			$('button[name = favSearchUpdateButton]').prop('disabled', false);			
		}else{
			$('button[name = favSearchUpdateButton]').prop('disabled', true);			
		}
		
	});
	
	
	// お気に入り更新ボタンが押下された場合
	/**
	 * お気に入りの編集ボタン押下時.
	 * 一覧から選択されたお気に入り番号をパラメータに設定し、モーダル画面を呼び出す。
	 */
	$('button[name = favSearchUpdateButton]').click(function(){
		// お気に入りプルダウンで選択されているお気に入りのID.
		var favSearchNo = $('form').find('input[type=hidden][name=favSearchNo]').val();
		var gamenId = $('form').find('input[type=hidden][name=gamenId]').val();
		// モーダル画面呼出し.
		var url = encodeURI(favSearchPath + '/init002?favSearchNo=' + favSearchNo + '&gamenId=' + gamenId);
		var returnFavSearchNo = modal(url, "large");

		// モーダル画面閉じるボタン押下後(各画面に定義されているfunction呼び出し)
        favUpdateModalClose(returnFavSearchNo);
		
	});

	// 引数：機能ID、　モーダルを閉じたあとの処理となるFUNCTIONをこのJS内で定義する
	/**
	 * お気に入りの保存ボタン押下時.
	 * 入力値を設定して一時テーブルに保存後、モーダル画面を呼び出す。
	 */
	$('button[name = favSearchInsertButton]').click(function(){
		
        // 多重送信を防ぐため通信完了までボタンをdisableにする
        var button = $(this);
        button.attr("disabled", true);
        
        var columnCnt = 0;
        // 入力されている項目の項目連番とvalue値を取得する.
        var arrColumnNo = [];
        var arrInputVal = [];
        var returnFavSearchNo;// モーダル画面呼出し後の戻り値.
        var beforeColumnNo;
        $('div[id=cs-fav-search-input] [name^=favSearch]').each(function(){
        	if($(this).val()){// テキストボックス、プルダウンが入力されていた場合.
        		var inputName = $(this).attr('name');
        		// 末尾の連番（カラム番号）を取得する.
        		// 「_」の表示位置を判定し、そこを開始位置として文字列の切り出しを行う.
        		var indexStart = inputName.indexOf("_");
        		var indexEnd = inputName.length;
        		
        		var columnNo = inputName.slice(indexStart+1, indexEnd);// カラムの番号.

        		if($(this).attr('type') == 'radio'){// ラジオボタンの場合.
        			if($(this).prop('checked')){// チェックされているラジオのvalue値のみを取得する.
        				arrColumnNo.push(columnNo);
        				arrInputVal.push($(this).val());

        			}
        		}else if($(this).attr('type') == 'checkbox'){// チェックボックスの場合
        			if(beforeColumnNo != columnNo){
        				
        			var checkFlg = false;
        			var checkVal = "";
        			$('input[name=' + inputName + ']').each(function(i, chkBox){
        				if($(chkBox).prop('checked')){
        					checkFlg = true;
        					if(!checkVal){
        						checkVal = $(chkBox).val();
        					}else{
        						checkVal = checkVal + '、' + $(chkBox).val();
        					}
        					
        				}
        			});
	        			if(checkFlg){
	        				arrColumnNo.push(columnNo);
	        				arrInputVal.push(checkVal);
	        				beforeColumnNo = columnNo;
	        			}
        			}
        			
        		}else{// テキストボックス、セレクトの場合.
        			arrColumnNo.push(columnNo);
        			arrInputVal.push($(this).val());// 入力値の設定.

        		}
        	}
        });

        var form = {
        		"gamenId" : $('input[name = gamenId]').val(),// お気に入りの呼出し元画面の機能IDを設定する.
        		"arrColumnNo" : arrColumnNo,// 検索条件が入力されたカラムの番号
        		"arrInputVal" : arrInputVal// 検索条件に入力されたvalue値
              };
        // 通信実行
        $.ajax({
          type : "post",
          url : favSearchPath + '/regist001',
          data : JSON.stringify(form), // JSONデータ本体
          contentType : 'application/json', // リクエストの Content-Type
          dataType : "json", // レスポンスをJSONとしてパースする
        }).done(function(form) { // 200 OK時
        	
            // モーダル画面呼出し処理.
        	var gamenId = $('input[type=hidden][name=gamenId]').val();
    		var url = encodeURI(favSearchPath + "/init001?gamenId=" + gamenId);
    		returnFavSearchNo = modal(url, "large");
    		// モーダル画面閉じるボタン押下後(各画面に定義されているfunction呼び出し)
            favInsertModalClose(returnFavSearchNo);

        }).fail(
            function(XMLHttpRequest, textStatus, errorThrown) {
              alert("XMLHttpRequest : " + XMLHttpRequest.status
                  + "\ntextStatus : " + textStatus + "\nerrorThrown : "
                  + errorThrown.message);
       }).always(function() { // 成功・失敗に関わらず通信が終了した際の処理
    	   button.attr("disabled", false); // ボタンを再び enableにする       

       });
        
        
	});
	
	// クリアボタン押下後、お気に入り編集ボタンを非活性にする.
	$("button[name = clearButton]").click(function(){
		$("button[name = favSearchUpdateButton]").attr("disabled", true);
	});
	
});

// ダイアログからの戻り.
function dialogSubmit(form){
	button.attr("disabled", false);
}

//【お気に入り機能】画面ロード後.お気にいり番号を選択する.
$(window).on('load',function(){
	var favSearchNo = $('input[name = favSearchNo]').val();
	if(favSearchNo){
		$('a[class = cs-fav-search-href][id = cs-fav-search-no-' + favSearchNo +']').click();
	}
});