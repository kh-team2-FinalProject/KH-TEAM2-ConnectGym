$(document).ready(function () {

//    loadCategory('All', 1);

    $('.ctgy-btn').click(function(){
        var category = $(this).text();
        if(category == '전체'){
            category = "All";
        }else if(category == '가입/탈퇴'){
            category = "1";
        }else if(category == '결제'){
            category = "2";
        }
        loadCategory(category, 1);
    });

    function loadCategory(category, page){
        $.ajax({
            url: "/api/customer_service",
            type: "GET",
            data:{
                "category": category,
                "page":page
            },
            success: function(data){
                var csList = data.csList;
                var currentPage = data.currentPage;
                var totalPages = data.totalPages;
                var ctgyList = data.ctgyList;
                var category = data.category;

                var faqAccordionWrap1 = $('#faq-accodion-wrap1');
                faqAccordionWrap1.empty(); // 이전 내용 삭제

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

                    // FAQ 아이템을 faq-accodion-wrap1에 추가
                    faqAccordionWrap1.append(accodionItem);
                });
            } else {
                // FAQ 아이템이 없을 경우 메시지 등을 표시할 수 있습니다.
                faqAccordionWrap1.append('<p>해당 카테고리에 FAQ 아이템이 없습니다.</p>');
            }

            // 페이지 번호 업데이트
            var pagination = $('.pagination');
            pagination.empty(); // 이전 페이지 번호 삭제

            if (totalPages > 1) {
                var ul = $('<ul>');

                // 이전 페이지 링크
                if (currentPage > 1) {
                    var prevLink = $('<a>').attr('href', '/customer_service?category=' + category + '&page=' + (currentPage - 1)).text('이전');
                    ul.append($('<li>').append(prevLink));
                }

                // 페이지 번호 링크
                for (var i = 1; i <= totalPages; i++) {
                    var pageLink = $('<a>').attr('href', '/customer_service?category=' + category + '&page=' + i).text(i);
                    var li = $('<li>').append(pageLink);
                    if (i === currentPage) {
                        li.addClass('active');
                    }
                    ul.append(li);
                }

                // 다음 페이지 링크
                if (currentPage < totalPages) {
                    var nextLink = $('<a>').attr('href', '/customer_service?category=' + category + '&page=' + (currentPage + 1)).text('다음');
                    ul.append($('<li>').append(nextLink));
                }

                pagination.append(ul);
            }
            },
            error:function(){
            console.error('에러 발생');
            }
        });
    }

  // 카테고리 버튼 클릭시 class 추가/삭제
  $(".ctgyitem").click(function () {

    if (!$(this).hasClass("active")) {
      $(".active").removeClass("active");
      $(this).addClass("active");
    }
  });

  // div 토글
  $(".accodion-item").click(function () {
    if ($(this).hasClass("active")) {
      $(this).removeClass("active");
      $(this).children("div").slideUp(200);
      console.log('active가 있음');
    } else {
      $(this).addClass("active");
      $(this).children("div").slideDown(200);
      console.log('active가 없음');
    }
  });

});

