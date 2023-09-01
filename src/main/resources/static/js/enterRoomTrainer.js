var OV;
var session;

/* OPENVIDU METHODS */
   window.onload = () => {
    joinSession();

   }

function joinSession(){

	OV = new OpenVidu();

	// --- 2) Init a session ---

	session = OV.initSession();

	// --- 3) Specify the actions when events take place in the session ---

	// On every new Stream received...
	// 카메라 배치
	session.on('streamCreated', event => {

		// Subscribe to the Stream to receive it. HTML video will be appended to element with 'video-container' id
		var subscriber = session.subscribe(event.stream, 'main-video');

		// When the HTML video has been appended to DOM...
		subscriber.on('videoElementCreated', event => {

			// Add a new <p> element for the user's nickname just below its video
			appendUserData(event.element, subscriber.stream.connection);
		});
	});

	// On every Stream destroyed...
	session.on('streamDestroyed', event => {

		// Delete the HTML element with the user's nickname. HTML videos are automatically removed from DOM
		removeUserData(event.stream.connection);
	});

	// On every asynchronous exception...
	session.on('exception', (exception) => {
		console.warn(exception);
	});


	// --- 4) Connect to the session with a valid user token ---
	// Get a token from the OpenVidu deployment
	getToken(myRoomCode).then(token => {
	    // 토큰 발급 확인용
	    console.log("Token received:", token);

		// 첫 번째 매개 변수는 OpenVidu 배포에서 가져온 토큰, 두 번째 매개 변수는 이벤트 시 모든 사용자가 검색할 수 있음
        // 'streamCreated'(PropertyStream.connection.data)이며 사용자 닉네임으로 DOM에 추가
		session.connect(token, { clientData: userName })
			.then(() => {

				// --- 5) Set page layout for active call ---
				/*document.getElementById('lesson-title').innerText = myRoomName;*/
//				document.getElementById('join').style.display = 'none';
				/*document.getElementById('lessonRoom').style.display = 'block';*/

				// --- 6) Get your own camera stream with the desired properties ---

				var publisher = OV.initPublisher('main-video', {
					audioSource: undefined, // The source of audio. If undefined default microphone
					videoSource: undefined, // The source of video. If undefined default webcam
					publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
					publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
					resolution: '640x480',  // The resolution of your video
					frameRate: 30,			// The frame rate of your video
					insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
					mirror: false       	// Whether to mirror your local video or not
				});

				// --- 7) Specify the actions when events take place in our publisher ---

				// When our HTML video has been added to DOM...
				publisher.on('videoElementCreated', function (event) {
					/*initMainVideo(event.element, userName);*/
					appendUserData(event.element, userName);
					event.element['muted'] = true;
				});

				// --- 8) Publish your stream ---

				session.publish(publisher);

			})
			.catch(error => {
				console.log('There was an error connecting to the session:', error.code, error.message);
			});
	});
}

function leaveSession() {

	// --- 9) Leave the session by calling 'disconnect' method over the Session object ---

	session.disconnect();

    // 사용자의 닉네임으로 모든 HTML 요소를 제거
    // 세션을 떠날 때 HTML 비디오가 자동으로 제거됨
	removeAllUserData();

	history.go(-1);

	// Back to 'Join session' page
//	document.getElementById('join').style.display = 'block';
//	document.getElementById('lessonRoom').style.display = 'none';
}

window.onbeforeunload = function () {
	if (session){
	session.disconnect();
	}
};


/* APPLICATION SPECIFIC METHODS */

// ==방이름 html에서 고정으로 넣을지 js에서 시작할 때 값 넣어지게 만들지는 고민==
/*window.addEventListener('load', function () {
	generateParticipantInfo();
});

function generateParticipantInfo() {
	document.getElementById("sessionId").value = myRoomName;
}*/

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
	videoElement.parentNode.insertBefore(dataNode, videoElement.nextSibling);
	addClickListener(videoElement, userData);
}

function removeUserData(connection) {
	var dataNode = document.getElementById("data-" + connection.connectionId);
	dataNode.parentNode.removeChild(dataNode);
}

function removeAllUserData() {
	var nicknameElements = document.getElementsByClassName('data-node');
	while (nicknameElements[0]) {
		nicknameElements[0].parentNode.removeChild(nicknameElements[0]);
	}
}

//비디오를 클릭했을 때 메인 비디오 영역에 해당 비디오 스트림을 크게 보여주는 부분을 처리


/*원본*/
function addClickListener(videoElement, userData) {
	videoElement.addEventListener('click', function () {
		var mainVideo = $('#main-video video').get(0);
		if (mainVideo.srcObject !== videoElement.srcObject) {
			$('#main-video').fadeOut("fast", () => {
				$('#main-video p').html(userData);
				mainVideo.srcObject = videoElement.srcObject;
				$('#main-video').fadeIn("fast");
			});
		}
	});
}

function initMainVideo(videoElement, userData) {
	document.querySelector('#main-video video').srcObject = videoElement.srcObject;
	document.querySelector('#main-video p').innerHTML = userData;
	document.querySelector('#main-video video')['muted'] = true;
}

function getToken(myRoomCode) {
	return createSession(myRoomCode).then(sessionId => createToken(sessionId));
}

function createSession(sessionId) {
	return new Promise((resolve, reject) => {
		$.ajax({
			type: "POST",
			url: "/room/enter/init",
			data: JSON.stringify({ customSessionId: sessionId }),
			headers: { "Content-Type": "application/json" },
			success: response => resolve(response),
			error: (error) => reject(error)
		});
	});
}

function createToken(sessionId) {
	return new Promise((resolve, reject) => {
		$.ajax({
			type: 'POST',
			url: '/room/enter/' + sessionId + '/connection',
			data: JSON.stringify({}),
			headers: { "Content-Type": "application/json" },
			success: (response) => resolve(response), // The token
			error: (error) => reject(error)
		});
	});
}
