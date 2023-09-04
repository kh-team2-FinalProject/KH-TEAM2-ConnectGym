function checkedChatroom() {

    //아작스 통신으로 채팅룸 검사 함수가 있는 컨트롤러로 보낸후 리턴값으로 채팅룸을 반환 chatroomNO
    $.ajax({
        type: "POST",
        url: "/checkedChatroom",
        data: {
            memberNo: memberNo,
            trainerNo: trainerNo
        },
        success: function (chatroom) {
            var chatroomNo = chatroom.no;
            console.log(chatroom.no);
            window.open('/chat_test/' + chatroomNo, 'chatting-window',
                'width=430, height=500, location=no, status=no, scrollbars=yes');

        },
        error: function (error) {
            alert("채팅방 연결에 실패하였습니다.")
        }
    });
}


function sendMessage() {

    var content = document.getElementById('message').value;
    stompClient.send(`/app/chat/${chatroomNo}`, {}, JSON.stringify({
        'chatroomNo': chatroomNo,
        // 'chatroom': {
        //     chatroom: chatroom
        // },
        'content': content,
        'sender': sender
    }));
    document.getElementById('message').value = '';
}

function showMessage(message) {
    var chatBox = document.getElementById('chat-box');
    var messageBody = document.createElement('div');
    messageBody.textContent = message.sender + ': ' + message.content; // 보낸 사람 정보 표시
    chatBox.appendChild(messageBody);
    // document.querySelector("#messages").appendChild(messageDiv);

}
