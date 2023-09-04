

/* 열림 */
function openPop() {
    var modal = document.getElementById("dietPopup");
    modal.style.display= "block";
}

/* 버튼 클릭시 닫힘 */
document.getElementById("closeBtn").addEventListener("click", function() {
    var modal = document.getElementById("dietPopup");
    modal.style.display = "none";
});



/* 검색 */
function searchFood() {
        var foodName = document.getElementById("foodNameInput").value;
        var form = document.getElementById("foodSearchForm");
        form.action = "/fooddiary/foodinfo?key=" + foodName;
        form.submit();
    }




/*
document.getElementById("submit").addEventListener("click", function(e) {
        e.preventDefault(); // 기본 이벤트 (폼 제출) 방지



        // 입력 필드 값 가져오기
        var foodNm = document.querySelector("#foodNm").value;
        var foodSize = document.querySelector("#foodSize").value;
        var choc = document.querySelector("#choc").value;
        var prot = document.querySelector("#prot").value;
        var fat = document.querySelector("#fat").value;

        // 필수 입력 필드가 비어 있는지 확인
        if (!foodNm || !foodSize || !choc || !prot || !fat) {
            */
/*alert("필수 입력 필드를 모두 작성하세요.");*//*

            Swal.fire({
                  icon: 'error',
                  title: '필수 입력 항목이 비었습니다.'
                })
            return;
        }

        // 폼 제출
        document.querySelector("form").submit();
    });
*/






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

