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
