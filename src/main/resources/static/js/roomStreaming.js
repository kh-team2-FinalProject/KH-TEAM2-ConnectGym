var OV;
var session;

/* OPENVIDU METHODS */
window.onload = () => {
  setTimeout(joinSession, 0);

  setTimeout(function () {
    const element = document.getElementById("lessonroom_content");
    const loadingElement = document.getElementById("mylessonlistloading_wrap");
    /*const videoElement = document.getElementById("video-container");*/

    loadingElement.style.display = "none";
    element.style.display = "flex";
  }, 3000);
};

function joinSession() {
  OV = new OpenVidu();

  session = OV.initSession();

  // 카메라 배치
  session.on("streamCreated", (event) => {
    var subscriber = session.subscribe(event.stream, "video-container");

    subscriber.on("videoElementCreated", (event) => {
      appendUserData(event.element, subscriber.stream.connection);
    });
  });

  session.on("streamDestroyed", (event) => {
    // Delete the HTML element with the user's nickname. HTML videos are automatically removed from DOM
    removeUserData(event.stream.connection);
  });

  // On every asynchronous exception...
  session.on("exception", (exception) => {
    console.warn(exception);
  });

  // --- 4) Connect to the session with a valid user token ---
  // Get a token from the OpenVidu deployment
  getToken(myRoomCode).then((token) => {
    // 토큰 발급 확인용
    console.log("Token received:", token);

    // 첫 번째 매개 변수는 OpenVidu 배포에서 가져온 토큰, 두 번째 매개 변수는 이벤트 시 모든 사용자가 검색할 수 있음
    // 'streamCreated'(PropertyStream.connection.data)이며 사용자 닉네임으로 DOM에 추가
    session
      .connect(token, { clientData: userName })
      .then(() => {
        var publisher = OV.initPublisher("video-container", {
          audioSource: undefined, // The source of audio. If undefined default microphone
          videoSource: undefined, // The source of video. If undefined default webcam
          publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
          publishVideo: true, // Whether you want to start publishing with your video enabled or not
          resolution: "720x480", // The resolution of your video
          frameRate: 30, // The frame rate of your video
          insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
          mirror: false, // Whether to mirror your local video or not
        });

        // When our HTML video has been added to DOM...
        publisher.on("videoElementCreated", function (event) {
          appendUserData(event.element, userName);
          event.element["muted"] = true;
        });

        session.publish(publisher);
      })
      .catch((error) => {
        console.log(
          "There was an error connecting to the session:",
          error.code,
          error.message
        );
      });
  });
}

function leaveSession(myRoomCode, userType) {
  $.ajax({
    type: "GET",
    url: "/room/exit/" + myRoomCode + "?userType=" + userType,
    contentType: "application/json;charset=UTF-8",
    success: function (response) {
      if (response == 1) {
        console.log("트레이너 exit");
      }

      session.disconnect();
      removeAllUserData();

      Swal.fire({
        title: "<b>오늘의 운동 완료</b>",
        html: '<div style="font-size:16px;">오늘의 명언</div><br>실패는 언제나 찾아오는 친구이며 성공은 어쩌다 찾아오는 손님이다. - 미르 임란',
        width: 600,
        padding: "3em",
        confirmButtonColor: "#fff",
        color: "#2f79a6",
        //background:
        //  "url(https://connectgym-bucket.s3.ap-northeast-2.amazonaws.com/commonData/o_woon_wan3.gif) center center / cover no-repeat",
      }).then((result) => {
        if (result.isConfirmed) {
          history.go(-1);
          //location.href="/mypage/myLessonList";
        }
      });
    },
    error: function (error) {
      // 요청이 실패했을 때 실행할 코드
      console.error("Ajax 오류: ", error);
    },
  });

  /*	session.disconnect();

    // 사용자의 닉네임으로 모든 HTML 요소를 제거
    // 세션을 떠날 때 HTML 비디오가 자동으로 제거됨
	removeAllUserData();

	history.go(-1);*/
}

window.onbeforeunload = function () {
  if (session) {
    leaveSession(myRoomCode, userType);
  }
};

function appendUserData(videoElement, connection) {
  let userData;
  let nodeId;

  if (typeof connection === "string") {
    userData = connection;
    nodeId = connection;
  } else {
    userData = JSON.parse(connection.data).clientData;
    nodeId = connection.connectionId;
  }

  // 새로운 div 요소를 생성하여 videoElement와 dataNode를 감싸기
  var containerDiv = document.createElement("div");
  containerDiv.className = "enterRoom_video_container";

  // videoElement를 containerDiv 안에 추가
  videoElement.id=`video_${userType}`;

  containerDiv.appendChild(videoElement);

  // dataNode 생성
  var dataNode = document.createElement("div");
  dataNode.className = "data-node";
  dataNode.id = "data-" + nodeId;
  dataNode.innerHTML = "<p>" + userData + "</p>";

  // dataNode를 containerDiv 안에 추가
  containerDiv.appendChild(dataNode);

  // videoElement와 dataNode가 포함된 containerDiv를 video-container에 추가
  var videoContainer = document.getElementById("video-container");
  videoContainer.appendChild(containerDiv);

  // addClickListener 함수를 호출
/*  addClickListener(videoElement, userData);*/
}



/*원본
function appendUserData(videoElement, connection) {
	var userData;
	var nodeId;
	if (typeof connection === "string") {
		userData = connection;
		nodeId = connection;
	} else {
		userData = JSON.parse(connection.data).clientData;
		nodeId = connection.connectionId;
	}
	var dataNode = document.createElement('div');
	dataNode.className = "data-node";
	dataNode.id = "data-" + nodeId;
	dataNode.innerHTML = "<p>" + userData + "</p>";
	videoElement.id=`video_${userType}`;
	videoElement.parentNode.insertBefore(dataNode, videoElement.nextSibling);
	addClickListener(videoElement, userData);
}*/



function removeUserData(connection) {
  var dataNode = document.getElementById("data-" + connection.connectionId);
  dataNode.parentNode.removeChild(dataNode);
}

function removeAllUserData() {
  var nicknameElements = document.getElementsByClassName("data-node");
  while (nicknameElements[0]) {
    nicknameElements[0].parentNode.removeChild(nicknameElements[0]);
  }
}

//비디오를 클릭했을 때 메인 비디오 영역에 해당 비디오 스트림을 크게 보여주는 부분을 처리

/*원본*/
/*
function addClickListener(videoElement, userData) {
  videoElement.addEventListener("click", function () {
    var mainVideo = $("#main-video video").get(0);
    if (mainVideo.srcObject !== videoElement.srcObject) {
      $("#main-video").fadeOut("fast", () => {
        $("#main-video p").html(userData);
        mainVideo.srcObject = videoElement.srcObject;
        $("#main-video").fadeIn("fast");
      });
    }
  });
}
*/

function getToken(myRoomCode) {
  return createSession(myRoomCode).then((sessionId) => createToken(sessionId));
}

function createSession(sessionId) {
  return new Promise((resolve, reject) => {
    $.ajax({
      type: "POST",
      url: "/room/enter/init",
      data: JSON.stringify({ customSessionId: sessionId }),
      headers: { "Content-Type": "application/json" },
      success: (response) => resolve(response),
      error: (error) => reject(error),
    });
  });
}

function createToken(sessionId) {
  return new Promise((resolve, reject) => {
    $.ajax({
      type: "POST",
      url: "/room/enter/" + sessionId + "/connection",
      data: JSON.stringify({}),
      headers: { "Content-Type": "application/json" },
      success: (response) => resolve(response), // The token
      error: (error) => reject(error),
    });
  });
}
