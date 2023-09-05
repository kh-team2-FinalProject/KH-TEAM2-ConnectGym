function checkedChatroom() {
    //아작스 통신으로 채팅룸 검사 함수가 있는 컨트롤러로 보낸후 리턴값으로 채팅룸을 반환 chatroom
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
    if (content.trim() !== "") {
//공백이 아니면 STOMP이용해ㅓㅅ 해당주소로 JSON타입으로 메시지 발송
        stompClient.send(`/app/chat/${chatroomNo}`, {}, JSON.stringify({
            'chatroomNo': chatroomNo,
            'content': content,
            'sender': sender

        }));
        document.getElementById('message').value = '';
    }
    endtoscrollbar();
}

//내가 보낸 메시지와 다른사람이 보낸 메시지 구별해서 화면에 출력
function showMessage(message) {

    var chatBox = document.getElementById("chat-box");      //채팅이 들어갈 전체영역
    if (message.sender !== sender) {

        var chatDiv = document.createElement("div");          //채팅메시지 요소들이 묶이는 영역
        chatDiv.className = "partnerMessageBox chat"; //chat  ch1

        var messageBody = document.createElement("div");       //채팅 메시지
        messageBody.className = "messageBody";   //textbox
        messageBody.textContent = message.content;

        // var dateSpan = document.createElement('span');                               //보낸시간
        // dateSpan.className = 'tx-small';
        // dateSpan.textContent = date;

        chatDiv.appendChild(messageBody);
        // chatDiv.appendChild(dateSpan);

        chatBox.appendChild(chatDiv);
    } else {
        var chatDiv = document.createElement("div");
        chatDiv.className = "myMessageBox chat";    //chat  ch2

        var messageBody = document.createElement("div");
        messageBody.className = "messageBody";     //textbox
        messageBody.textContent = message.content;

        // var dateSpan = document.createElement('span');
        // dateSpan.className = 'tx-small';
        // dateSpan.textContent = date;

        chatDiv.appendChild(messageBody);
        // chatDiv.appendChild(dateSpan);

        chatBox.appendChild(chatDiv);

    }
    const scrollTop = chatBox.scrollTop;
    const scrollHeight = chatBox.scrollHeight;
    const clientHeight = chatBox.clientHeight;
    console.log(scrollTop + clientHeight);
    console.log(scrollHeight);


    // 스크롤 위치가 가장 하단에 있을 때만 하단고정
    if (scrollTop + clientHeight + 100 >= scrollHeight) {
        endtoscrollbar();
    }
}

//스크롤바 하단고정 함수
function endtoscrollbar() {

    const chatcontainer = document.getElementById("chat-box");

    requestAnimationFrame(() => {
        chatcontainer.scrollTo(0, chatcontainer.scrollHeight);
    });

    // chatcontainer.scrollIntoView({block: 'end', behavior: 'smooth'});
    // }
}
