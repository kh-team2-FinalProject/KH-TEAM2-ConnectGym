function checkedChatroom() {
    $.ajax({
        type: "POST",
        url: "/checkedChatroom",
        data: {
            memberNo: memberNo,
            trainerNo: trainerNo
        },
        success: function (chatroom) {
            window.open("/chat_test/" + chatroom.no, 'chatting-window', 'width=430, height=500, location=no, status=no, scrollbars=yes');
            $.ajax({
                    type: "POST",
                    url: "/chat_test/" + chatroom.no,
                    data: {
                        chatroom: chatroom

                    }
        },
        error: function (error) {
            alert("채팅방 연결에 실패하였습니다.")
        }
    });
}

