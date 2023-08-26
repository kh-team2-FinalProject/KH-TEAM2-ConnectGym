
//lesson 정보는 어떻게 받아오면 좋을지 더 생각해보기
function checkRoom(lessonNo,enrollNo) {

	// 사용자No를 컨트롤러에서 room key<->enroll key 비교
    $.ajax({
        type: 'get',
    	url: `/room/checkEnroll/${enrollNo}`,
    	contentType:'application/json; charset=utf-8',
        dataType : 'json',
        //async : false,
    	success: response => {
                         if (response) {
                             // 키가 일치할 때 redirect enterRoom
                             window.location.href=`/enterroom/${lessonNo}`;
                         } else {
                             // 일치하지 않을 때
                             alert("현재 수강 가능한 시간이 아닙니다.");
                         }
                     },
    	error: (error) => reject(error)

    });
}
