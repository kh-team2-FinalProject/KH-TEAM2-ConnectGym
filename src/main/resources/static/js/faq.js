$(document).ready(function () {



  $(".ctgy-btn").click(function () {
    var category = $(this).text();
    if (category == "전체") {
      category = "All";
    } else if (category == "가입/탈퇴") {
      category = "1";
    } else if (category == "결제") {
      category = "2";
    }

    if (!$(this).hasClass("active")) {
      $(".active").removeClass("active");
      $(this).addClass("active");
    }
    loadCategory(category, 1);
  }); // ctgy-btn 버튼 클릭 함수

}); // document.ready 끝

function changePage(element){
        var category = element.getAttribute("data-category");
        var page = element.getAttribute("data-page");

        loadCategory(category, page);
    }

  function loadCategory(category, page) {
    $.ajax({
      url: "/api/customer/faq",
      type: "GET",
      data: {
        category: category,
        page: page,
      },
      success: function (data) {
        var csList = data.csList;           // customer_service table List
        var currentPage = data.currentPage; // 현재 페이지
        var totalPages = data.totalPages;   // 총 페이지
        var ctgyList = data.ctgyList;       // category table List
        var category = data.category;       // String category

        var uiAccodion = $(".ui-accodion");
        uiAccodion.empty();

        if (csList.length > 0) {
          // FAQ 아이템이 존재할 경우
          csList.forEach(function (csItem) {
            var accodionItem = $('<div class="accodion-item">');

            var button = $('<button type="button" class="accodion-btn">');
            var mark = $('<span class="mark">Q</span>');
            var faqTitle = $('<span class="faq-title">').text(csItem.title);
            var arrowIcon = $('<i class="ico arrow md"></i>');

            var layer = $('<div class="layer">');
            var accodionContent = $('<div class="accodion-content">');

            var desc = $('<p class="desc">').text(csItem.content);

            button.append(mark, faqTitle, arrowIcon);

            accodionContent.append(desc);
            layer.append(accodionContent);

            accodionItem.append(button, layer);
            uiAccodion.append(accodionItem);
          });
        } else {
          // FAQ 아이템이 없을 경우 메시지 등을 표시할 수 있습니다.
          uiAccodion.append("<p>해당 카테고리에 FAQ 아이템이 없습니다.</p>");
        }

        // 페이지 번호 업데이트
        var pagination = $(".pagination");
        pagination.empty();

        if (totalPages > 1) {
          var ul = $("<ul>");

          // 이전 페이지 링크
          if (currentPage > 1) {
            var prevLink = $("<button>")
              .attr(
                "onclick",
                "loadCategory('" + category + "'," + (currentPage-1) + ")"
              )
              .text("이전");
            ul.append($("<li class='prev'>").append(prevLink));
          }

          // 페이지 번호 링크
          for (var i = 1; i <= totalPages; i++) {
            var pageLink = $("<button>")
              .attr(
                "onclick",
                "loadCategory('" + category + "'," + i + ")"
              )
              .text(i);
            var li = $("<li class='pageNumber'>").append(pageLink);
            if (i === currentPage) {
              li.addClass("active");
            }
            ul.append(li);
          }

          // 다음 페이지 링크
          if (currentPage < totalPages) {
            var nextLink = $("<button>")
              .attr(
                "onclick",
                "loadCategory('" + category + "'," + (currentPage+1) + ")"
              )
              .text("다음");
            ul.append($("<li class='next'>").append(nextLink));
          }
          pagination.append(ul);
        }
        $(".accodion-item").click(function () {
          if ($(this).hasClass("active")) {
            $(this).removeClass("active");
            $(this).children("div").slideUp(200);
          } else {
            $(this).addClass("active");
            $(this).children("div").slideDown(200);
          }
        });
      },
      error: function () {
        console.error("에러 발생");
      },
    });
    var menuItemDiv = $('.module').children('div');
    if($(menuItemDiv[0]).hasClass('active')){
    }else{
        $(menuItemDiv[0]).addClass('active');
    }
  }
