// document.addEventListener("DOMContentLoaded", function () {
//     const menuItems = document.querySelectorAll(".side_menu ul li");
//
//     menuItems.forEach(function (item) {
//         item.addEventListener("click", function () {
//             menuItems.forEach(function (menuItem) {
//                 menuItem.querySelector("a").classList.remove("selected");
//             });
//
//             // 클릭한 메뉴 항목에 "selected" 클래스를 추가합니다.
//             item.querySelector("a").classList.add("selected");
//
//             // 줄 효과를 나타내기 위해 width를 100%로 설정합니다.
//             item.querySelector("a").style.width = "100%";
//         });
//     });
// });
// document.addEventListener("DOMContentLoaded", function () {
//     const menuItems = document.querySelectorAll(".side_menu ul li a");
//
//     // 페이지 URL에서 카테고리 쿼리 매개변수 값을 읽어옵니다.
//     const urlParams = new URLSearchParams(window.location.search);
//     const selectedCategory = urlParams.get("category");
//
//     // 페이지 로드 시 URL에서 선택한 카테고리에 해당하는 메뉴 항목에 'selected' 클래스를 추가합니다.
//     if (selectedCategory) {
//         menuItems.forEach(function (item) {
//             const category = item.getAttribute("data-category");
//             if (category === selectedCategory) {
//                 item.classList.add("selected");
//             }
//         });
//     }
//
//     // 메뉴 항목을 클릭할 때 URL을 업데이트하여 선택한 카테고리를 반영합니다.
//     menuItems.forEach(function (item) {
//         item.addEventListener("click", function () {
//             const category = item.getAttribute("data-category");
//             const newUrl = updateQueryStringParameter(window.location.href, "category", category);
//             window.history.pushState({path: newUrl}, "", newUrl);
//
//             // 모든 메뉴 항목에서 'selected' 클래스를 제거합니다.
//             menuItems.forEach(function (menuItem) {
//                 menuItem.classList.remove("selected");
//             });
//
//             // 클릭한 메뉴 항목에 'selected' 클래스를 추가합니다.
//             item.classList.add("selected");
//         });
//     });
//
//     // URL의 쿼리 매개변수를 업데이트하는 함수
//     function updateQueryStringParameter(uri, key, value) {
//         const re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
//         const separator = uri.indexOf("?") !== -1 ? "&" : "?";
//         if (uri.match(re)) {
//             return uri.replace(re, "$1" + key + "=" + value + "$2");
//         } else {
//             return uri + separator + key + "=" + value;
//         }
//     }
// });
//
//

