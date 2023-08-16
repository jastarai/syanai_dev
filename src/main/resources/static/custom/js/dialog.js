/**
 * 警告ダイアログ
 *  メソッド内でSubmitを実施する。
 */
function dialogalertInSubmit(msg,form,url,disableflg) {

	$.confirm({
		'title'     : '',
		'message'   : msg,
		'buttons'   : {
			'OK': {
				'class' : 'btn-info',
				'action': function() {
					if (disableflg) {
						changeDisabledBeforeSubmit(url, form);
					} else {
						document.getElementById(form).submit();
					}
				}
			}
		},
	});

}

/**
 * 確認ダイアログ
 *  メソッド内でSubmitを実施する。
 */
function dialogconfirmInSubmit(msg, form, url, disableflg) {

	$.confirm({
		'title'     : '',
		'message'   : msg,
		'buttons'   : {
			'OK': {
				'class' : 'btn-info btn-lg',
				'action': function() {
					if(disableflg) {
						changeDisabledBeforeSubmit(url, form);
					} else {
						document.getElementById(form).submit();
					}
				}
			},
			'キャンセル': {
				'class' : 'btn-danger btn-lg',
				'action': function() {}
			}
		},
	});
}
