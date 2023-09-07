// document.querySelectorAll('.l_card').forEach(card => {
//     card.addEventListener('mouseenter', () => {
//         card.querySelector('.l_card-img').style.transform = 'rotateY(180deg)';
//     });
//
//     card.addEventListener('mouseleave', () => {
//         card.querySelector('.l_card-img').style.transform = 'rotateY(0deg)';
//     });
// });


//글자수 조정
function truncateText() {
    const cardBackElements = document.querySelectorAll('.l_card-content');

    cardBackElements.forEach(cardBack => {
        const titleElement = cardBack.querySelector('#l_lesson-title');
        const originalText = titleElement.textContent;
        const maxLength = 14; // 원하는 최대 길이

        if (originalText.length > maxLength) {
            const truncatedText = originalText.substring(0, maxLength) + '...';
            titleElement.textContent = truncatedText;
        }
    });
}

window.addEventListener('DOMContentLoaded', truncateText);


//카드효과
const cards = document.querySelectorAll('.l_card');

function showCards() {
    cards.forEach((card, index) => {
        setTimeout(() => {
            card.classList.add('scroll-visible');
        }, index * 200); // 200ms 간격으로 카드 표시
    });
}

window.addEventListener('load', showCards);

// 스크롤 이벤트 핸들러
function handleScroll() {
    cards.forEach((card) => {
        const cardTop = card.getBoundingClientRect().top;
        if (cardTop < window.innerHeight) {
            card.classList.add('scroll-visible');
        }
    });
}

window.addEventListener('scroll', handleScroll);
