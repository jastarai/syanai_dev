/**
 * 最終的にはwebjar化する必要があります
 */

function dialogalert(msg,form) {

	alert(msg);
	document.getElementById(form).submit();
	
}

function dialogconfirm(msg,form) {

	if (confirm(msg) == true){

		document.getElementById(form).submit();

	} else {
		
		return false;
	}
}

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
	$("#confirmBox").draggable();

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
	$("#confirmBox").draggable();
}
