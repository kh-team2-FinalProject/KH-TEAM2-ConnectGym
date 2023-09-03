

/*
function enterRoom(titleCode,enrollKey) {

  $.ajax({
    type: "get",
    url: `/trainer/checkRoom?titleCode=${titleCode}&enrollKey=${enrollKey}`,
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    async : false,
    success: (response) => {
        window.location.href = `/enterRoom/${response.no}`;
    },
    error: (error) => reject(error),
  });
}
*/

function enterRoom(titleCode, enrollKey) {
  $.ajax({
    type: "POST",
    url: "/trainer/checkRoom",
    data: JSON.stringify({ titleCode: titleCode, enrollKey: enrollKey }), // 데이터를 JSON 형식으로 전달
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    async: false,
    success: (response) => {
      window.location.href = `/enterRoom/${response.no}`;
    },
    error: (error) => {
      console.error(error);
    },
  });
}
