function checkedChatroom() {
    $.ajax({
        type: "POST",
        url: "/checkedChatroom",
        data: {
            memberNo: memberNo,
            trainerNo: trainerNo
        },
        success: function (roomId) {
            window.open("/chat_test/" + roomId, 'chatting-window', 'width=430, height=500, location=no, status=no, scrollbars=yes');

        },
        error: function (error) {
            alert("채팅방 연결에 실패하였습니다.")
        }
    });
}

function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        console.log('Connected: ');
        stompClient.subscribe('/queue/qqq', function (message) {
            showMessage(JSON.parse(message.body));
        });

    });
}
