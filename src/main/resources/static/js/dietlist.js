

/* html 따로 만들었는데 복잡해서 이건 일단 보류
function openPop11(){
    var popup = window.open("popup.html", "_blank", "width=500,height=300");
}
*/


/* 팝업 열리는 함수
function openPop() {
    var modal = document.getElementById("dietPopup");
    modal.style.display = "block";
}
*/

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


/*

document.getElementById("closeBtn").addEventListener("click", function() {
     window.close();




var openButton = document.querySelectorAll(".btn1");
openButton.forEach(function(button, index) {
    button.addEventListener("click", function() {
        var dietPops = document.querySelectorAll(".dietPops");
        var dietPop = dietPops[index];
        dietPop.style.display = "block";
    });
});

var closeButton = document.querySelectorAll(".closeBtn");
closeButton.forEach(function(button) {
    button.addEventListener("click", function() {
        var dietPop = this.closest(".dietPops");
        dietPop.style.display = "none";
    });
});
*/
