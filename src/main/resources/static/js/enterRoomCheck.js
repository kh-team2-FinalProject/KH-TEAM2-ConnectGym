

function checkRoom(titleCode, enrollKey) {

  $.ajax({
    type: "get",
    url: `/room/checkEnroll?titleCode=${titleCode}&enrollKey=${enrollKey}`,
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    async : false,
    success: (response) => {
        //룸 입장 (response는 roomNo)
        // -1 : 룸 생성 전
        // 0 : 트레이너 입장 전
        // 1 : 입장 가능
      if (response > 0) {
        window.location.href = `/enterRoom/${response}`;

        //룸 생성 전
      } else if (response < 0){
            alert("아직 레슨이 오픈되지 않았어요! 조금만 기다려 주세요.🥹");

        //룸 비활성화
      } else {
        alert("현재는 수업 시간이 아니거나 트레이너 입장 전입니다.😅");
      }
    },
    error: (error) => reject(error),
  });
}
