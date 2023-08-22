//$(document).ready(function() {
//    adjustLayout(); // 페이지 로드 시에 초기 설정
//
//    $(window).resize(adjustLayout); // 창 크기 변경 시에도 설정
//
//    function adjustLayout() {
//        const headerHeight = $('header').outerHeight();
//        const contentBody = $('.contet-body');
//        const footerHeight = $('footer').outerHeight();
//
//        const totalHeight = headerHeight + footerHeight;
//
//        if (totalHeight < $(window).height()) {
//            const spaceToFill = $(window).height() - totalHeight;
//            contentBody.css('padding-top', headerHeight + 'px');
//            contentBody.css('padding-bottom', footerHeight + spaceToFill + 'px');
//        } else {
//            contentBody.css('padding-top', headerHeight + 'px');
//            contentBody.css('padding-bottom', '0');
//        }
//    }
//});
