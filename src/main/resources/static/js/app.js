var OV;
var session;


/* OPENVIDU METHODS */

function check() {

    //내가 수강한 레슨의 네임으로 고정
	var myRoomName = document.getElementById("roomName").value;
	//레슨 구매 후 발급받은 키
	var myRoomKey = document.getElementById("roomKey").value;

	const roominfo ={
	    roomName : myRoomName,
	    roomKey : myRoomKey
	}

    $.ajax({
        type: "POST",
    	url: "/room/checkEnroll",
    	contentType:'application/json; charset=utf-8',
        dataType : 'json',
        data : JSON.stringify(roominfo),
        async : false,
    	success: response => {
                         if (response) {
                             // 방 이름과 키가 일치할 때
                             joinSession(myRoomName,myRoomKey);
                         } else {
                             // 일치하지 않을 때
                             alert("키값이 잘못 입력되었거나 현재 수강시간이 아닙니다.");
                         }
                     },
    	error: (error) => reject(error)

    });
}

function joinSession(myRoomName, myRoomKey){

	OV = new OpenVidu();

	// --- 2) Init a session ---

	session = OV.initSession();

	// --- 3) Specify the actions when events take place in the session ---

	// On every new Stream received...
	session.on('streamCreated', event => {

		// Subscribe to the Stream to receive it. HTML video will be appended to element with 'video-container' id
		var subscriber = session.subscribe(event.stream, 'video-container');

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
	getToken(myRoomName).then(token => {
	    //sh - 토큰 발급 확인용
	    console.log("Token received:", token);

		// First param is the token got from the OpenVidu deployment. Second param can be retrieved by every user on event
		// 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
		session.connect(token, { clientData: myRoomKey })
			.then(() => {

				// --- 5) Set page layout for active call ---

				document.getElementById('session-title').innerText = myRoomName;
				document.getElementById('join').style.display = 'none';
				document.getElementById('session').style.display = 'block';

				// --- 6) Get your own camera stream with the desired properties ---

				var publisher = OV.initPublisher('video-container', {
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
					initMainVideo(event.element, myRoomKey);
					appendUserData(event.element, myRoomKey);
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

	// Removing all HTML elements with user's nicknames.
	// HTML videos are automatically removed when leaving a Session
	removeAllUserData();

	// Back to 'Join session' page
	document.getElementById('join').style.display = 'block';
	document.getElementById('session').style.display = 'none';
}

window.onbeforeunload = function () {
	if (session) session.disconnect();
};


/* APPLICATION SPECIFIC METHODS */

window.addEventListener('load', function () {
//	generateParticipantInfo();
});

//function generateParticipantInfo() {
//	document.getElementById("sessionId").value = "SessionA";
//	document.getElementById("userName").value = "Participant" + Math.floor(Math.random() * 100);
//}

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

function getToken(myRoomName) {
	return createSession(myRoomName).then(sessionId => createToken(sessionId));
}

function createSession(roomName) {
	return new Promise((resolve, reject) => {
		$.ajax({
			type: "POST",
			url: "/room/enter/id",
			data: JSON.stringify({ lessonRoomName: roomName }),
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
