function showReview(trainerNo) {
  const reviewDiv = document.querySelector(".trainerDetail_profile_about");

  $.ajax({
    type: "GET",
    url: `/review/show/${trainerNo}`,
    dataType: "json",
    success: function (response) {
      if (response == null) {
        reviewDiv.innerHTML =
          '<div class="trainerDetail_review_none"><p>등록된 리뷰가 없습니다.</p></div>';
        return false;
      }

      // 리뷰가 있는 경우
      let listHtml = "";

      //리뷰 평점 출력
      listHtml += `<div class="trainerDetail_review_item">
                        <div clas="trainerDetail_review_item_back">
                            <a href="javascript:void(0);" onclick="window.location.reload();">
                                <img class="trainerDetail_review_item_back_svg" src="/images/icons/left-long-solid.svg">
                            </a>
                        </div>
                <div class="trainerDetail_review_item_score">
                    <div class="trainerDetail_review_item_score_avg">
                     <div>AVG</div>
                     <div>${response.ratingAvg}점</div>
                    </div>
                    <div class="trainerDetail_review_item_score_cnt">
                        <div class="stars" data-count="5">
                            <div class="stars_icon">5
                                <img src="/images/icons/star-solid.svg">
                                <img src="/images/icons/star-solid.svg">
                                <img src="/images/icons/star-solid.svg">
                                <img src="/images/icons/star-solid.svg">
                                <img src="/images/icons/star-solid.svg">
                            </div>
                            ${response.ratingCountDto.rating5Count}
                        </div><br>
                        <div class="stars" data-count="4">
                            <div class="stars_icon">4
                                <img src="/images/icons/star-solid.svg">
                                <img src="/images/icons/star-solid.svg">
                                <img src="/images/icons/star-solid.svg">
                                <img src="/images/icons/star-solid.svg">
                            </div>
                            ${response.ratingCountDto.rating4Count}
                        </div><br>
                        <div class="stars" data-count="3">
                            <div class="stars_icon">3
                                <img src="/images/icons/star-solid.svg">
                                <img src="/images/icons/star-solid.svg">
                                <img src="/images/icons/star-solid.svg">
                            </div>
                            ${response.ratingCountDto.rating3Count}
                        </div><br>
                        <div class="stars" data-count="2">
                            <div class="stars_icon">2
                                <img src="/images/icons/star-solid.svg">
                                <img src="/images/icons/star-solid.svg">
                            </div>
                            ${response.ratingCountDto.rating2Count}
                        </div><br>
                        <div class="stars" data-count="1">
                            <div class="stars_icon">1
                                <img src="/images/icons/star-solid.svg">
                            </div>
                            ${response.ratingCountDto.rating1Count}
                        </div>
                    </div>
                </div>`;

      //리뷰 출력
      response.trainerReviewResponseDtoList.forEach((review) => {
        listHtml += `
                <div class="trainerDetail_review_item_memReview">
                    <div class="trainerDetail_review_item_memReview_line1">
                        <div class="trainerDetail_review_item_membeId">${review.userId}</div>
                        <div class="review_item_rating" data-rating="${review.rating}"></div>
                    </div>
                    <div class="trainerDetail_review_item_memReview_title">
                        레슨명 : ${review.reviewTitle}
                    </div>
                    <div class="trainerDetail_review_item_content">${review.reviewContent}</div>
                </div>


                </div>
                `;
      });

      reviewDiv.innerHTML = listHtml;
      applyStarRating();
    },
    error: function (request, status, error) {
      console.log(error);
    },
  });
}
