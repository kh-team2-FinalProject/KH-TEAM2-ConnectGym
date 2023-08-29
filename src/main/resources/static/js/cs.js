$(document).ready(function () {
  // 카테고리 버튼 클릭시 class 추가/삭제
  $(".ctgyitem").click(function () {
    if (!$(this).hasClass("active")) {
      $(".active").removeClass("active");
      $(this).addClass("active");
    }
  });

  // 자주묻는질문 클릭시 div display 설정
  $(".accodion-item").click(function () {
    // $(this).children("div").slideToggle(200);
    if ($(this).hasClass("active")) {
      $(this).removeClass("active");
      $(this).children("div").slideUp(200);
    } else {
      $(this).addClass("active");
      $(this).children("div").slideDown(200);
    }
  });
});
