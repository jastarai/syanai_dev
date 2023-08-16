(function($){
	
	$.confirm = function(params){
		
		if($('#confirmOverlay').length){
			// A confirm is already shown on the page:
			return false;
		}
		
		var buttonHTML = '';
		$.each(params.buttons,function(name,obj){
			
			// Generating the markup for the buttons:
			
			// 2016/04 custom button⇒btn
			//buttonHTML += '<a href="#" class="button '+obj['class']+'">'+name+'<span></span></a>';
			buttonHTML += '<a href="#" class="btn '+obj['class']+'">'+name+'<span></span></a>';
			
			if(!obj.action){
				obj.action = function(){};
			}
		});
		
		// 2016/04 custom
		var markup = [
			'<div id="confirmOverlay"></div>',
			'<div id="confirmBox">',
			'<h1>',params.title,'</h1>',
			'<p>',params.message,'</p>',
			'<div id="confirmButtons">',
			buttonHTML,
			'</div></div>'
		].join('');
		
		$(markup).hide().appendTo('body').fadeIn();
		
		// 2016/04 custom button⇒btn
		//var buttons = $('#confirmBox .button'),
		var buttons = $('#confirmBox .btn'),
			i = 0;

		$.each(params.buttons,function(name,obj){
			buttons.eq(i++).click(function(){
				
				// Calling the action attribute when a
				// click occurs, and hiding the confirm.
				
				//var submitFlg = obj.action();	// custom
				obj.action();

				$.confirm.hide();

				//return submitFlg;	// custom
				return false;

			});
		});
	
	}

	$.confirm.hide = function(){
		// 2016/04 custom add #confirmBox
		$('#confirmOverlay,#confirmBox').fadeOut(function(){
			$(this).remove();
		});
	}
	
})(jQuery);