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

function findMemberID(){
    var inputName = $('#InputName').val();
    var inputEmail = $('#userEmail').val();

    if(!$('#join_auth_btn').val()){
    Swal.fire({
          position: "center",
          width: "500px",
          background: "rgba(215, 214, 214, 0.761)",
          html: `<div style="font-size:14px;">이메일 인증을 먼저 진행해주세요.</div>`,
          showConfirmButton: true,
          showCancelButton: false,
          confirmButtonColor: "#eb4315"
        });

        /*alert('이메일 인증을 먼저 진행해주세요.');*/
        return false;
    }

    $.ajax({
        url: "/findIdProcess",
        type: "GET",
        data: {
            name:inputName,
            email:inputEmail
        },
        success: function(data){
        Swal.fire({
                  position: "center",
                  width: "500px",
                  background: "rgba(215, 214, 214, 0.761)",
                  html: `<div style="font-size:14px;">찾으시는 아이디는 '${data}'입니다.</div>`,
                  showConfirmButton: true,
                  showCancelButton: false,
                  confirmButtonColor: "#A3DC10"
                });

            /*alert('찾으시는 아이디는 ' + data + ' 입니다.');*/
            window.location.href = "/user/login";
        },
        error: function(error){
        Swal.fire({
                  position: "center",
                  width: "500px",
                  background: "rgba(215, 214, 214, 0.761)",
                  html: `<div style="font-size:14px;">작성된 정보가 잘못되었거나 존재하지 않는 정보입니다.</div>`,
                  showConfirmButton: true,
                  showCancelButton: false,
                  confirmButtonColor: "#eb4315"
                });
            /*alert('작성 정보가 잘못되었거나 존재하지 않는 정보입니다.');*/
            return false;
        }
    });
}
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
            Swal.fire({
                      position: "center",
                      width: "500px",
                      background: "rgba(215, 214, 214, 0.761)",
                      html: `<div style="font-size:14px;">인증번호가 전송되었습니다.</div>`,
                      showConfirmButton: true,
                      showCancelButton: false,
                      confirmButtonColor: "#A3DC10"
                    });

            /*alert('인증번호가 전송되었습니다.');*/
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
