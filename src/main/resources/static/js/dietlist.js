

/* 열림 */
function openPop(meal) {
    var mealName = meal + " MENU";
    var modal = document.getElementById("dietPopup");
    var mealNameElement = modal.querySelector(".pop-content h1");
    mealNameElement.innerText = mealName;
    modal.style.display= "block"

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


/* 버튼 클릭시 닫힘 */
document.getElementById("closeBtn").addEventListener("click", function() {
    var modal = document.getElementById("dietPopup");
    modal.style.display = "none";
});



/* 검색 기능 */
$(document).ready(function(){

    // Enter
    $("#popInput").keypress(function (e){
        if(e.which === 13){
            const searchValue = $("#popInput").val();
            if(searchValue.trim() !== ""){
                searchFoods(searchValue);
            }
        }
    })
})





