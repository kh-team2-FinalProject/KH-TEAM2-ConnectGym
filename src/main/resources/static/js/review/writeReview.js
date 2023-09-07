window.onload = () => {
// 별점
const ratingInputs = document.querySelectorAll('.myReview_write_rating_grp input[type="radio"]');
const ratingLabels = document.querySelectorAll('.myReview_write_rating_grp label');

ratingInputs.forEach(function (input, index) {
  input.addEventListener('click', function () {
    // 선택한 별의 인덱스
    const selectedIndex = Array.from(ratingInputs).indexOf(input);

    // 이전 별들을 모두 체크 상태로 변경
    for (let i = 0; i <= selectedIndex; i++) {
      ratingInputs[i].checked = true;
       ratingLabels[i].querySelector('svg path').style.fill = 'gold';; // 선택한 별까지 색상 변경
    }

    // 이후 별들의 체크 상태 초기화
    for (let i = selectedIndex + 1; i < ratingInputs.length; i++) {
      ratingInputs[i].checked = false;
       ratingLabels[i].querySelector('svg path').style.fill = '#ccc'; // 이후 별들 색상 초기화
    }
  });
});
}


// 댓글 글자수 카운팅
function countingLength(reviewContent) {
if (reviewContent.value.length > 500) {
alert('댓글을 300자 이하로 입력해 주세요.');
reviewContent.value = reviewContent.value.substring(0, 500);
reviewContent.focus();
}
document.getElementById('myReview_write_content_counter').innerText = reviewContent.value.length + '/500자';
}

// 뒤로가기 시 팝업창
function back(){
    Swal.fire({
        title: '뒤로가기',
        text: '작성한 내용이 초기화됩니다. 진행하시겠습니까?',
        icon: 'warning',

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: 'YES', // confirm 버튼 텍스트 지정
        cancelButtonText: 'CANCLE', // cancel 버튼 텍스트 지정

        //reverseButtons: true, // 버튼 순서 거꾸로

        }).then(result => {
            if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
                    history.go(-1);
            //재호출도 가능 : Swal.fire('승인이 완료되었습니다.', '내용~!', 'success');-->
        }
    });
}
