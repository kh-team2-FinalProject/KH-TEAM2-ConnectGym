
function findChatRoom(){
    const trainerNo = ${trainer.trainerNo};
    const memberNo = ${session.session_login_member_no};




    const data = {
            type: 'openChat',
            trainerNo: trainerNo,
            memberNo: memberNo
    };






}























function setConnected(connected) {
    var connectButton = $('#connect');
    var disconnectButton = $('#disconnect');
    var messageInput = $('#message');
    var sendButton = $('#send');

    if (connected) {
        connectButton.prop('disabled', true);
        disconnectButton.prop('disabled', false);
        messageInput.prop('disabled', false);
        sendButton.prop('disabled', false);
    } else {
        connectButton.prop('disabled', false);
        disconnectButton.prop('disabled', true);
        messageInput.prop('disabled', true);
        sendButton.prop('disabled', true);
    }
}

function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.send("/app/register", {}, JSON.stringify({ 'username': username }));

        stompClient.subscribe('/topic/chat', function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    var messageInput = $('#message');
    var message = messageInput.val().trim();

    if (message && stompClient) {
        var chatMessage = {
            'from': username,
            'text': message
        };
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        messageInput.val('');
    }
}

function showMessage(message) {
    var chatDiv = $('#chat');
    var messageElem = $('<div>').addClass('message').html('<strong>' + message.from + ':</strong> ' + message.text);
    chatDiv.append(messageElem);
}

$(function () {
    username = prompt('Please enter your username:');
    connect();

    $('#disconnect').click(function () {
        disconnect();
    });

    $('#send').click(function () {
        sendMessage();
    });
});
