/* 열림 */
function openPop() {
    var modal = document.getElementById("dietPopup");
    modal.style.display = "block";
}

/* 버튼 클릭시 닫힘 */
document.getElementById("closeBtn").addEventListener("click", function () {
    var modal = document.getElementById("dietPopup");
    modal.style.display = "none";
});

/* 검색 */
function searchFood() {
    var foodName = document.getElementById("foodNameInput").value;
    var form = document.getElementById("foodSearchForm");
    form.action = "/fooddiary/foodinfo?search=" + foodName;
    form.submit();
}

/* 검색 기능 */
// $(document).ready(function () {
//     // Enter
//     $("#popInput").keypress(function (e) {
//         if (e.which === 13) {
//             const searchValue = $("#popInput").val();
//             if (searchValue.trim() !== "") {
//                 searchFoods(searchValue);
//             }
//         }
//     });
// });

// window.onload = function () {
//     var modal = document.getElementById("dietPopup");
//     modal.style.display = "none";
// };

function movePage(page) {
    const params = new URLSearchParams(location.search);
    params.set("page", page);
    location.href = location.pathname + "?" + params;
}
