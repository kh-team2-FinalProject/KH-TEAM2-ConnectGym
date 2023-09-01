//document.addEventListener('DOMContentLoaded', function() {
//    const form = document.getElementsByName('edit_form')[0];
//    form.addEventListener('submit', function(e) {
//        e.preventDefault();  // 기본 제출 동작을 막습니다.
//        alert("정보수정이 완료 되었습니다");
//
//        // http://localhost:5000 으로 이동
//        window.location.href = 'http://localhost:5000';
//    });
//});
$(document).ready(function(){
    $.ajax({
        url: "/mypage/update/myInfo",
        type: "GET",
        data: {
            user_no: loginMemberNo
        },
        success: function(getSession_Info){
            $('#InputID').val(getSession_Info.user_id);
            $('#InputID').attr('readonly', true);
            $('#InputName').val(getSession_Info.user_name);
            if(getSession_Info.user_tel != null){
                $('#InputTel').val(getSession_Info.user_tel);
            }
            if(getSession_Info.user_address != null){
                $('#userAddress').val(getSession_Info.user_address);
                var userAddress = getSession_Info.user_address;
                address_split(userAddress);
            }
            $('#userEmail').val(getSession_Info.user_email);
            var userEmail = getSession_Info.user_email;

            email_split(userEmail);
        },
        error: function(error){
            alert('에러가 발생하였습니다.');
            return false;
        }
    });
    function email_split(userEmail){
        var email = userEmail.split("@");
        var email01 = email[0];
        var email02 = email[1];
        $('#email01').val(email01);
        $('#email02').val(email02);

        $('#email01').attr('readonly', true);
        $('#email02').attr('readonly', true);
    }
    function address_split(userAddress){
        var address = userAddress.split(".");

        var postcode = address[0];
        var address01 = address[1];
        var address02 = address[2];
        var address03 = address[3];

        $('#sample6_postcode').val(postcode);
        $('#sample6_address').val(address01);
        $('#sample6_extraAddress').val(address02);
        $('#sample6_detailAddress').val(address03);
    }
});


// 주소 api(다음)
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
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
        if(data.userSelectedType === 'R'){
        // 법정동명이 있을 경우 추가한다. (법정리는 제외)
        // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
        if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
            extraAddr += data.bname;
        }
        // 건물명이 있고, 공동주택일 경우 추가한다.
        if(data.buildingName !== '' && data.apartment === 'Y'){
            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
        }
        // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
        if(extraAddr !== ''){
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

$('#sample6_postcode').blur(function(){
    address();
});

$("#sample6_address").blur(function(){
    address();
});

// 상세주소 가져오기
$("#sample6_detailAddress").blur(function(){
    address();
});

// 참고사항 가져오기
$("#sample6_extraAddress").blur(function(){
    address();
});

// 가져온 주소 합치기
function address(){
    const sample6_postcode = $('#sample6_postcode').val();
    const sample6_address = $("#sample6_address").val();
    const sample6_detailAddress = $("#sample6_detailAddress").val();
    const sample6_extraAddress = $("#sample6_extraAddress").val();

    if(sample6_postcode != "" && sample6_address != "" && sample6_detailAddress != "" && sample6_extraAddress != ""){
        $("#userAddress").val(sample6_postcode + "." + sample6_address + "." + sample6_extraAddress + "." + sample6_detailAddress);
    }
};

// 이메일 도메인 선택
$('#select').change(function(){
    $('#select option:selected').each(function(){
        if($(this).val() == '1'){ // 직접입력
            $("#email02").val('');                  // 값 초기화
            $("#email02").attr("disabled", false);  // 활성화
        }else{ // 직접입력이 아닐 경우
            $("#email02").val($(this).text());       // 선택값 입력
            $("#email02").attr("disabled", true);    // 비활성화
            email();
        }
    });
});

// 회원정보수정 버튼 클릭 시 유효성검사
function joinform_check(){
    var InputPW = document.getElementById("InputPW");
    var CheckPW = document.getElementById("CheckPW");
    var InputName = document.getElementById("InputName");
    var InputTel = document.getElementById("InputTel");

    if(InputPW.value == ""){
        alert("비밀번호를 입력하세요.");
        InputPW.focus();
        return false;
    };

    // 비밀번호 영문자 + 숫자 조합 (6~25자리 입력) 정규식
    var pwdCheck = /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,25}$/;

    if(!pwdCheck.test(CheckPW.value)){
        alert("비밀번호는 영문자 + 숫자 조합으로 6~25자리 사용해야합니다.");
        CheckPW.focus();
        return false;
    };

    if(InputPW.value != CheckPW.value){
        alert("비밀번호가 일치하지 않습니다.");
        CheckPW.focus();
        return false;
    };

    if(InputName.value == ""){
        alert("이름을 입력하세요.");
        InputName.focus();
        return false;
    };

    // 숫자만 입력하는 정규식
    var phoneRule = /^(010)[0-9]{4}[0-9]{4}$/;

    if(!phoneRule.test(InputTel.value)){
        alert("전화번호를 확인해주세요.");
        InputTel.focus();
        return false;
    };

    if(InputTel.value == ""){
        alert("전화번호를 입력해주세요.");
        InputTel.focus();
        return false;
    }

    document.join_form.submit();
};

// 비밀번호 실시간 유효성검사
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

////////////////////////////////////////////////////////
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

$('#InputTel').blur(function(){
    var phoneRule = /^(010)[0-9]{4}[0-9]{4}$/;

    if(phoneRule.test($(this).val())){
        $('#InputTel').css('border', '2px solid green');
    }else if($(this).val()==""){
        $('#InputTel').css('border', 'none');
    }else{
        $('#InputTel').css('border', '2px solid red');
    }
});

$('#InputName').on("propertychange change keyup paste input", function(){
    if(!$(this).val()==""){
        $('#InputName').css('border', '2px solid green');
    }else{
        $('#InputName').css('border', 'none');
    }
});
