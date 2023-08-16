$(function() {
    var topBtn = $('#cs-page-top');
    topBtn.hide();

    //500px以上スクロールでページトップアイコン表示
    $(window).scroll(function () {
        if ($(this).scrollTop() > 500) {
            topBtn.fadeIn();
        } else {
            topBtn.fadeOut();
        }
    });
//    $(window).scroll(function () {
//    	if ($(this).scrollTop() > -1) {
//        topBtn.fadeIn();
//    	}
//    });
    
    //トップアイコン押下イベント
    topBtn.click(function () {
        $('body,html').animate({
            scrollTop: 0
        }, 500);
        return false;
    });
});

$(function() {
    var downBtn = $('#cs-page-down');
    //トップアイコン押下イベント
    downBtn.click(function () {
        $('body,html').animate({
            scrollTop: $(document).height()
        }, 650);
        return false;
    });
});