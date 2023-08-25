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

// 주소 DB에 저장하기
// 주소 가져오기
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
    const sample6_address = $("#sample6_address").val();
    const sample6_detailAddress = $("#sample6_detailAddress").val();
    const sample6_extraAddress = $("#sample6_extraAddress").val();

    if(sample6_address != "" && sample6_detailAddress != "" && sample6_extraAddress != ""){
        $("#userAddress").val(sample6_address + " " + sample6_detailAddress + " " + sample6_extraAddress);
    }
};

function joinform_check(){
    var InputID = document.getElementById("InputID");
    var InputPW = document.getElementById("InputPW");
    var CheckPW = document.getElementById("CheckPW");
    var InputName = document.getElementById("InputName");
    var InputTel = document.getElementById("InputTel");
    var userAddress = document.getElementById("userAddress");
    var userEmail = document.getElementById("userEmail");
    var check = document.getElementById("check");

    var returnEmail = document.getElementById("email01");

    if(InputID.value == ""){
        alert("아이디를 입력하세요.");
        InputID.focus();
        return false;
    };

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
    var reg = /^[0-9]+/g;

    if(!reg.test(InputTel.value) && InputTel.value != ""){
        alert("전화번호는 숫자만 입력할 수 있습니다.");
        InputTel.focus();
        return false;
    };

    if(InputTel.value == ""){
        alert("전화번호를 입력해주세요.");
        InputTel.focus();
        return false;
    }

    if(InputTel.value.length != 11){
        alert("전화번호를 확인해주세요.");
        InputTel.focus();
        return false;
    }

    if(userAddress.value==""){
        alert("주소를 입력해주세요.");
        userAddress.focus();
        return false;
    };

    if(userEmail.value==""){
        alert("이메일 주소를 입력하세요.");
        returnEmail.focus();
        return false;
    };

    if(!check.checked){
        alert("약관 동의를 체크하세요.");
        check.focus();
        return false;
    };

    document.join_form.submit();
};
























































