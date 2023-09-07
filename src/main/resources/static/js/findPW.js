// 이메일 앞부분 가져오기
$("#email01").blur(function(){
    email();
});

// 이메일 뒷부분(도메인부분) 가져오기
$("#email02").blur(function(){
    email();
});

// 이메일 id 부분과 도메인 부분 합치기
function email(){
    const email = $("#email01").val();
    const middle = $("#email_middle").text();
    const address = $("#email02").val();

    if(email != "" && address != ""){
        $("#userEmail").val(email + middle + address);
    }
};

function findMemberPW(){
    var inputID = $('#InputID').val();
    var inputName = $('#InputName').val();
    var inputEmail = $('#userEmail').val();
    var inputPW = $('#InputPW').val();
    var checkPW = $('#CheckPW').val();

    if($('#join_auth_btn').val() == false){
        alert('이메일 인증을 먼저 진행해주세요.');
        return false;
    }

    if(!(inputPW === checkPW)){
        alert('비밀번호를 확인해주세요.');
        return false;
    }

    $.ajax({
        url: "/findPWProcess",
        type: "POST",
        data: {
            userId:inputID,
            name:inputName,
            email:inputEmail,
            password:inputPW
        },
        success: function(data){
            alert('비밀번호 변경에 성공하였습니다.');
        },
        error: function(error){
            alert('작성 정보가 잘못되었거나 존재하지 않는 정보입니다.');
            return false;
        }
    });
}

$('#InputPW').on("propertychange change keyup paste input", function(){
    // 실시간 input에 입력받은 내용
    var liveValue = $(this).val();
    var pwdCheck = /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,25}$/;

    if(!pwdCheck.test(liveValue)){
        $('#InputPW').css('border', '2px solid red');
        $('#pw_check_message').empty();
        $('#pw_check_message').css('color', 'red');
        $('#pw_check_message').text("영문 + 숫자 조합으로 6~25자리 입력해주세요.");
    } else {
        $('#InputPW').css('border', '2px solid green');
        $('#pw_check_message').empty();
        $('#pw_check_message').css('color', 'green');
        $('#pw_check_message').text("사용 가능한 비밀번호입니다.");
    }
});

// 비밀번호 확인 실시간 유효성검사
$('#CheckPW').on("propertychange change keyup paste input", function(){
    // 실시간 input에 입력받은 내용
    var liveValue = $(this).val();
    var pwdCheck = $('#InputPW').val();

    if(liveValue!=pwdCheck){
        $('#CheckPW').css('border', '2px solid red');
        $('#checkpw_check_message').empty();
        $('#checkpw_check_message').css('color', 'red');
        $('#checkpw_check_message').text("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
    } else {
        $('#CheckPW').css('border', '2px solid green');
        $('#checkpw_check_message').empty();
        $('#checkpw_check_message').css('color', 'green');
        $('#checkpw_check_message').text("비밀번호가 일치합니다.");
    }
});

$('#join_request_btn').click(function(){
    var email = $('#userEmail').val();
    console.log("------------email : " + email); // email 오는지 확인용
    var checkInput = $('#checkEmail');

    $.ajax({
        type: 'get',
        url: "/mailCheck",
        data: {
            email: email
        },
        success: function(data){
            checkInput.attr('readonly', false);
            code=data;
            alert('인증번호가 전송되었습니다.');
        }
    }); // ajax 끝

    // 인증번호 비교
	$('#join_auth_btn').click(function () {
		const inputCode = $('#checkEmail').val();
		const $resultMsg = $('#mail-check-warn');

		if(inputCode === code){
			$resultMsg.html('인증번호가 일치합니다.');
			$resultMsg.css('color','green');
			$('#email01').attr('readonly',true);
			$('#email02').attr('readonly',true);
			$('#email02').attr('onFocus', 'this.initialSelect = this.selectedIndex');
	        $('#email02').attr('onChange', 'this.selectedIndex = this.initialSelect');
            $('#checkEmail').attr('readonly', true);

	        $('#join_auth_btn').attr('value', true);
		}else{
			$resultMsg.html('인증번호가 불일치 합니다. 다시 확인해주세요!.');
			$resultMsg.css('color','red');

			$('#join_auth_btn').attr('value', false);
		}
    });
});
