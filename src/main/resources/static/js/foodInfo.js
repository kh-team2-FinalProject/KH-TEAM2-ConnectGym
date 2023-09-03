

/* 열림 */
function openPop() {
    var modal = document.getElementById("dietPopup");
    modal.style.display= "block";
}

document.getElementById("submit").addEventListener("click", function () {
    // 입력 필드 값 가져오기
    var foodNm = document.querySelector("#foodNm").value;
    var foodSize = document.querySelector("#foodSize").value;
    var choc = document.querySelector("#choc").value;
    var prot = document.querySelector("#prot").value;
    var fat = document.querySelector("#fat").value;

    e.preventDefault();

if (!foodNm || !foodSize || !choc || !prot || !fat) {
    Swal.fire({
      icon: 'error',
      title: '필수 입력 항목이 비었습니다.',
      confirmButtonColor: '#3085d6'
    })
    return;
} else {
    document.querySelector("form").submit();
    }
});


/* 버튼 클릭시 닫힘 */
document.getElementById("closeBtn").addEventListener("click", function() {
    var modal = document.getElementById("dietPopup");
    modal.style.display = "none";
});




/*
 유효성
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
            alert("필수 입력 필드를 모두 작성하세요.");
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

<script src="sweetalert2.all.min.js"></script>
<script src="sweetalert2.min.js"></script>
<link rel="stylesheet" href="sweetalert2.min.css">
