$(document).ready(function () {
  // 카테고리 버튼 클릭시 class 추가/삭제
  $(".ctgyitem").click(function () {
    if (!$(this).hasClass("active")) {
      $(".active").removeClass("active");
      $(this).addClass("active");
    }
    if($(this).text().includes('전체')){
        $('#faq-accodion-wrap1').removeClass('none');
        $('#faq-accodion-wrap2').addClass('none');
        $('#faq-accodion-wrap3').addClass('none');
    }else if($(this).text().includes('가입/탈퇴')){
        $('#faq-accodion-wrap1').addClass('none');
        $('#faq-accodion-wrap2').removeClass('none');
        $('#faq-accodion-wrap3').addClass('none');
    }else if($(this).text().includes('결제')){
        $('#faq-accodion-wrap1').addClass('none');
        $('#faq-accodion-wrap2').addClass('none');
        $('#faq-accodion-wrap3').removeClass('none');
    }
  });

  // 자주묻는질문 클릭시 div display 설정
  $(".accodion-item").click(function () {
    if ($(this).hasClass("active")) {
      $(this).removeClass("active");
      $(this).children("div").slideUp(200);
    } else {
      $(this).addClass("active");
      $(this).children("div").slideDown(200);
    }
  });
});
