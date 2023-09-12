////////////////////////////////////////////////////////
// 카카오 로그인을 통하여 db에 정보가 없어 넘어올 경우
$(document).ready(function () {
    if (kakaoEmail != null) {
        var kakaoEmail_split = kakaoEmail.split("@");
        var kakaoEmail01 = kakaoEmail_split[0];
        var kakaoEmail02 = kakaoEmail_split[1];

        $('#email01').val(kakaoEmail01);
        $('#email02').val(kakaoEmail02);
        $('#email01').attr('readonly', true);
        $('#email02').attr('readonly', true);
    }
});

// 주소 api(다음)
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("sample6_extraAddress").value = extraAddr;

            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById("sample6_address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("sample6_detailAddress").focus();
        }
    }).open();
};

////////////////////////////////////////////////////////
// 이메일 도메인 선택
$('#select').change(function () {
    $('#select option:selected').each(function () {
        if ($(this).val() == '1') { // 직접입력
            $("#email02").val('');                  // 값 초기화
            $("#email02").attr("disabled", false);  // 활성화
        } else { // 직접입력이 아닐 경우
            $("#email02").val($(this).text());       // 선택값 입력
            $("#email02").attr("disabled", true);    // 비활성화
            email();
        }
    });
});

// 이메일 앞부분 가져오기
$("#email01").blur(function () {
    email();
});

// 이메일 뒷부분(도메인부분) 가져오기
$("#email02").blur(function () {
    email();
});

// 이메일 id 부분과 도메인 부분 합치기
function email() {
    const email = $("#email01").val();
    x``
    const middle = $("#email_middle").text();
    const address = $("#email02").val();

    if (email != "" && address != "") {
        $("#userEmail").val(email + middle + address);
    }
};

////////////////////////////////////////////////////////
// 주소 DB에 저장하기
// 주소 가져오기
$('#sample6_postcode').blur(function () {
    address();
});

$("#sample6_address").blur(function () {
    address();
});

// 상세주소 가져오기
$("#sample6_detailAddress").blur(function () {
    address();
});

// 참고사항 가져오기
$("#sample6_extraAddress").blur(function () {
    address();
});

// 가져온 주소 합치기
function address() {
    const sample6_postcode = $('#sample6_postcode').val();
    const sample6_address = $("#sample6_address").val();
    const sample6_detailAddress = $("#sample6_detailAddress").val();
    const sample6_extraAddress = $("#sample6_extraAddress").val();

    if (sample6_postcode != "" && sample6_address != "" && sample6_detailAddress != "" && sample6_extraAddress != "") {
        $("#userAddress").val(sample6_postcode + "." + sample6_address + "." + sample6_extraAddress + "." + sample6_detailAddress);
    }
};

////////////////////////////////////////////////////////
// 실시간 ID 유효성검사(중복 ID 체크)
$('#InputID').on("propertychange change keyup paste input", function () {
    // 실시간 input에 입력받은 내용
    var liveValue = $(this).val();
    var idCheck = /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,16}$/;

    // ajax 실행하여 실시간 id 중복검사
    $.ajax({
        url: "/api/check_overlap_id",
        type: "POST",
        data: {
            user_id: liveValue
        },
        success: function (check_overlap_id) {
            if (check_overlap_id.boolean) {
                $('#InputID').css('border', '2px solid red');
                $('#id_check_message').empty();
                $('#id_check_message').css('color', 'red');
                $('#id_check_message').text("중복된 아이디입니다.");
            } else {
                if (!idCheck.test(liveValue)) {
                    $('#InputID').css('border', '2px solid red');
                    $('#id_check_message').empty();
                    $('#id_check_message').css('color', 'red');
                    $('#id_check_message').text("영문 + 숫자 조합으로 6~16자리 입력해주세요.");
                } else {
                    $('#InputID').css('border', '2px solid green');
                    $('#id_check_message').empty();
                    $('#id_check_message').css('color', 'green');
                    $('#id_check_message').text("사용 가능한 아이디입니다.");
                }
            }
        },
        error: function (error) {
        Swal.fire({
                  position: "center",
                  width: "500px",
                  background: "rgba(215, 214, 214, 0.761)",
                  html: `<div style="font-size:14px;">에러가 발생하였습니다.</div>`,
                  showConfirmButton: true,
                  showCancelButton: false,
                  confirmButtonColor: "#eb4315"
                });
            /*alert('에러가 발생하였습니다.');*/
            return false;
        }
    });
});

////////////////////////////////////////////////////////
//// email 인증코드 구현
$('#join_request_btn').click(function () {
    var email = $('#userEmail').val();
    console.log("------------email : " + email); // email 오는지 확인용
    var checkInput = $('#checkEmail');

    $.ajax({
        type: 'get',
        url: "/mailCheck",
        data: {
            email: email
        },
        success: function (data) {
            checkInput.attr('readonly', false);
            code = data;
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

        if (inputCode === code) {
            $resultMsg.html('인증번호가 일치합니다.');
            $resultMsg.css('color', 'green');
            $('#email01').attr('readonly', true);
            $('#email02').attr('readonly', true);
            $('#email02').attr('onFocus', 'this.initialSelect = this.selectedIndex');
            $('#email02').attr('onChange', 'this.selectedIndex = this.initialSelect');
            $('#checkEmail').attr('readonly', true);

            $('#join_auth_btn').attr('value', true);
        } else {
            $resultMsg.html('인증번호가 불일치 합니다. 다시 확인해주세요!.');
            $resultMsg.css('color', 'red');

            $('#join_auth_btn').attr('value', false);
        }
    });
});

////////////////////////////////////////////////////////
// 회원가입 버튼 클릭 시 유효성검사
function joinform_check() {
    var InputID = document.getElementById("InputID");
    var InputPW = document.getElementById("InputPW");
    var CheckPW = document.getElementById("CheckPW");
    var InputName = document.getElementById("InputName");
    var InputTel = document.getElementById("InputTel");
    var userAddress = document.getElementById("userAddress");
    var userEmail = document.getElementById("userEmail");
    var check1 = document.getElementById("check1");
    var check2 = document.getElementById("check2");

    var returnEmail = document.getElementById("email01");

    if (InputID.value == "") {
        Swal.fire({
                  position: "center",
                  width: "500px",
                  background: "rgba(215, 214, 214, 0.761)",
                  html: `<div style="font-size:14px;">아이디를 입력하세요.</div>`,
                  showConfirmButton: true,
                  showCancelButton: false,
                  confirmButtonColor: "#eb4315"
                });

        /*alert("아이디를 입력하세요.");*/
        InputID.focus();
        return false;
    }
    ;

    if (InputID.value.length < 6 || InputID.value.length > 16) {
    Swal.fire({
              position: "center",
              width: "500px",
              background: "rgba(215, 214, 214, 0.761)",
              html: `<div style="font-size:14px;">영문 + 숫자 조합으로 <br>6~16자리 입력해주세요.</div>`,
              showConfirmButton: true,
              showCancelButton: false,
              confirmButtonColor: "#eb4315"
            });
        /*alert("영문 + 숫자 조합으로 6~16자리 입력해주세요.");*/
        InputID.focus();
        return false;
    }
    ;

    if (InputPW.value == "") {
    Swal.fire({
              position: "center",
              width: "500px",
              background: "rgba(215, 214, 214, 0.761)",
              html: `<div style="font-size:14px;">비밀번호를 입력하세요.</div>`,
              showConfirmButton: true,
              showCancelButton: false,
              confirmButtonColor: "#eb4315"
            });
        /*alert("비밀번호를 입력하세요.");*/
        InputPW.focus();
        return false;
    }
    ;

    // 비밀번호 영문자 + 숫자 조합 (6~25자리 입력) 정규식
    var pwdCheck = /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,25}$/;

    if (!pwdCheck.test(CheckPW.value)) {
        Swal.fire({
                  position: "center",
                  width: "500px",
                  background: "rgba(215, 214, 214, 0.761)",
                  html: `<div style="font-size:14px;">비밀번호는 영문자 + 숫자 조합으로 <br>6~25자리 사용해야합니다.</div>`,
                  showConfirmButton: true,
                  showCancelButton: false,
                  confirmButtonColor: "#eb4315"
                });

        /*alert("비밀번호는 영문자 + 숫자 조합으로 6~25자리 사용해야합니다.");*/
        CheckPW.focus();
        return false;
    }
    ;

    if (InputPW.value != CheckPW.value) {
Swal.fire({
          position: "center",
          width: "500px",
          background: "rgba(215, 214, 214, 0.761)",
          html: `<div style="font-size:14px;">비밀번호가 일치하지 않습니다.</div>`,
          showConfirmButton: true,
          showCancelButton: false,
          confirmButtonColor: "#eb4315"
        });

       /* alert("비밀번호가 일치하지 않습니다.");*/
        CheckPW.focus();
        return false;
    }
    ;

    if (InputName.value == "") {
    Swal.fire({
              position: "center",
              width: "500px",
              background: "rgba(215, 214, 214, 0.761)",
              html: `<div style="font-size:14px;">이름을 입력하세요.</div>`,
              showConfirmButton: true,
              showCancelButton: false,
              confirmButtonColor: "#eb4315"
            });
        /*alert("이름을 입력하세요.");*/
        InputName.focus();
        return false;
    }
    ;

    // 숫자만 입력하는 정규식
    var phoneRule = /^(010)[0-9]{4}[0-9]{4}$/;

    if (!phoneRule.test(InputTel.value)) {
    Swal.fire({
              position: "center",
              width: "500px",
              background: "rgba(215, 214, 214, 0.761)",
              html: `<div style="font-size:14px;">전화번호를 확인해주세요.</div>`,
              showConfirmButton: true,
              showCancelButton: false,
              confirmButtonColor: "#eb4315"
            });
        /*alert("전화번호를 확인해주세요.");*/
        InputTel.focus();
        return false;
    }
    ;

    if (InputTel.value == "") {
    Swal.fire({
              position: "center",
              width: "500px",
              background: "rgba(215, 214, 214, 0.761)",
              html: `<div style="font-size:14px;">전화번호를 입력해주세요.</div>`,
              showConfirmButton: true,
              showCancelButton: false,
              confirmButtonColor: "#eb4315"
            });

        /*alert("전화번호를 입력해주세요.");*/
        InputTel.focus();
        return false;
    }

//    if(userAddress.value==""){
//        alert("주소를 입력해주세요.");
//        userAddress.focus();
//        return false;
//    };

    // 이메일 인증 후 안되어있으면 진행하라 메시지로 수정할 것
    if (!$('#join_auth_btn')) {
    Swal.fire({
              position: "center",
              width: "500px",
              background: "rgba(215, 214, 214, 0.761)",
              html: `<div style="font-size:14px;">이메일 인증을 완료해주세요.</div>`,
              showConfirmButton: true,
              showCancelButton: false,
              confirmButtonColor: "#eb4315"
            });

        /*alert("이메일 인증을 완료해주세요.");*/
        returnEmail.focus();
        return false;
    }
    ;

    if (!check1.checked) {
Swal.fire({
          position: "center",
          width: "500px",
          background: "rgba(215, 214, 214, 0.761)",
          html: `<div style="font-size:14px;">필수 약관 동의를 체크해주세요.</div>`,
          showConfirmButton: true,
          showCancelButton: false,
          confirmButtonColor: "#eb4315"
        });

        /*alert("약관 동의를 체크해주세요.");*/
        check.focus();
        return false;
    }
    ;

    if (!check2.checked) {
    Swal.fire({
              position: "center",
              width: "500px",
              background: "rgba(215, 214, 214, 0.761)",
              html: `<div style="font-size:14px;">필수 약관 동의를 체크해주세요.</div>`,
              showConfirmButton: true,
              showCancelButton: false,
              confirmButtonColor: "#eb4315"
            });
        /*alert("약관 동의를 체크해주세요.");*/
        check.focus();
        return false;
    }
    ;

    document.join_form.submit();
};

////////////////////////////////////////////////////////
// 비밀번호 실시간 유효성검사
$('#InputPW').on("propertychange change keyup paste input", function () {
    // 실시간 input에 입력받은 내용
    var liveValue = $(this).val();
    var pwdCheck = /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,25}$/;

    if (!pwdCheck.test(liveValue)) {
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

////////////////////////////////////////////////////////
// 비밀번호 확인 실시간 유효성검사
$('#CheckPW').on("propertychange change keyup paste input", function () {
    // 실시간 input에 입력받은 내용
    var liveValue = $(this).val();
    var pwdCheck = $('#InputPW').val();

    if (liveValue != pwdCheck) {
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

///////////////////////////
// blur 효과
$('#InputName').on("propertychange change keyup paste input", function () {
    if (!$(this).val() == "") {
        $('#InputName').css('border', '2px solid green');
    } else {
        $('#InputName').css('border', 'none');
    }
});

$('#InputTel').blur(function () {
    var phoneRule = /^(010)[0-9]{4}[0-9]{4}$/;

    if (phoneRule.test($(this).val())) {
        $('#InputTel').css('border', '2px solid green');
    } else if ($(this).val() == "") {
        $('#InputTel').css('border', 'none');
    } else {
        $('#InputTel').css('border', '2px solid red');
    }
});












