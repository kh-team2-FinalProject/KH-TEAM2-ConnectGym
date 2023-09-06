// document.querySelectorAll('.l_card').forEach(card => {
//     card.addEventListener('mouseenter', () => {
//         card.querySelector('.l_card-img').style.transform = 'rotateY(180deg)';
//     });
//
//     card.addEventListener('mouseleave', () => {
//         card.querySelector('.l_card-img').style.transform = 'rotateY(0deg)';
//     });
// });

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



