window.onload = function() {
    var modal = document.getElementById("dietPopup");

    // 현재 페이지의 URL을 가져옵니다.
    var currentPageURL = window.location.href;
    if (currentPageURL.includes("/fooddiary/dietlist?key=")) {
        modal.style.display = "block";
    } else {
    modal.style.display = "none";
    }



}


/* 모달 내 시간대별 이름 바뀜 */
document.addEventListener("DOMContentLoaded", function(){
    var popbtns = document.querySelectorAll(".btn1");
    popbtns.forEach(function(button){
    button.addEventListener("click", function(){
        var meal = button.closest(".Diet1").querySelector("h3").textContent;
        openPop(meal);
    });
    });


});

/* 열림 */
function openPop(meal) {
    var mealName = meal + " MENU";
    var modal = document.getElementById("dietPopup");
    var mealNameElement = modal.querySelector(".pop-content h1");
    mealNameElement.innerText = mealName;
    modal.style.display= "block"

}

/* 버튼 클릭시 닫힘 */
document.getElementById("closeBtn").addEventListener("click", function() {
    var modal = document.getElementById("dietPopup");
    modal.style.display = "none";
});


    // 검색 버튼 클릭 시
function searchDiet() {
    var foodName = document.getElementById("popInput").value;
    var form = document.getElementById("foodSearchForm");
    form.action = "/fooddiary/dietlist?key=" + foodName;
    if (foodName.trim() !== "") {
       var modal = document.getElementById("dietPopup");
       modal.style.display = "block";
       }
    form.submit();
    }




