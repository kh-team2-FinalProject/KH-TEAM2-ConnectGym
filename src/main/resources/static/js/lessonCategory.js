// document.querySelectorAll('.l_card').forEach(card => {
//     card.addEventListener('mouseenter', () => {
//         card.querySelector('.l_card-inner').style.transform = 'rotateY(180deg)';
//     });
//
//     card.addEventListener('mouseleave', () => {
//         card.querySelector('.l_card-inner').style.transform = 'rotateY(0deg)';
//     });
// });

function truncateText() {
    const cardBackElements = document.querySelectorAll('.l_card-back');

    cardBackElements.forEach(cardBack => {
        const titleElement = cardBack.querySelector('.l_card_title');
        const originalText = titleElement.textContent;
        const maxLength = 50; // 원하는 최대 길이

        if (originalText.length > maxLength) {
            const truncatedText = originalText.substring(0, maxLength) + '...';
            titleElement.textContent = truncatedText;
        }
    });
}

window.addEventListener('DOMContentLoaded', truncateText);

document.addEventListener("DOMContentLoaded", function () {
    const menuItems = document.querySelectorAll(".side_menu ul li a");

    menuItems.forEach(function (item) {
        item.addEventListener("click", function (event) {
            // 모든 메뉴 항목에서 "selected" 클래스를 제거합니다.
            menuItems.forEach(function (menuItem) {
                menuItem.classList.remove("selected");
            });

            // 클릭한 메뉴 항목에 "selected" 클래스를 추가합니다.
            item.classList.add("selected");
        });
    });
});
