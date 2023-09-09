

function applyStarRating() {
    const starRatingElements = document.querySelectorAll('.review_item_rating');

    starRatingElements.forEach(starRating => {
        const rating = parseInt(starRating.getAttribute('data-rating'));

        // SVG 요소를 생성하고 클래스를 설정하여 별점을 표시합니다.
        for (let i = 1; i <= 5; i++) {
            const star = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            star.classList.add('star');

            star.setAttribute('viewBox', '0 0 576 512');
            star.setAttribute('width', '13px');
            star.setAttribute('height', '13px');

            // 별 아이콘의 색상을 설정합니다.
            if (i <= rating) {
                star.classList.add('filled');
            }

            // 별 아이콘을 SVG 요소에 추가합니다.
            star.innerHTML = '<path class="review_item_rating_star"'
            +'d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"/>';

            starRating.appendChild(star);
        }
    });
}

/* 로컬 svg 이모티콘 사용 시
function applyStarRating() {
      const starRatingElements = document.querySelectorAll('.review_item_rating');

      starRatingElements.forEach(starRating => {
          const rating = parseInt(starRating.getAttribute('data-rating'));

          for (let i = 1; i <= 5; i++) {
              // <img> 태그 생성
              const starImg = document.createElement("img");
              starImg.classList.add('star');
              starImg.setAttribute('src', '/images/icons/star-solid.svg'); // 이미지 파일 경로 설정

              // 별 아이콘의 색상을 설정합니다.
              if (i <= rating) {
                  starImg.classList.add('filled');
              }

              // <img> 태그를 요소에 추가
              starRating.appendChild(starImg);
          }
      });
  }
*/
