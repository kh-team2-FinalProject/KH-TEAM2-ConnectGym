document.getElementById("InputPW").addEventListener("keyup", function () {
    // 실시간 input에 입력받은 내용

    var liveValue = document.getElementById("InputPW").value;
    var pwdCheck = /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,25}$/;
    var pwCheckMessage = document.getElementById("pw_check_message");

    if (!pwdCheck.test(liveValue)) {
        this.style.border = "2px solid red";
        pwCheckMessage.innerText = "영문 + 숫자 조합으로 6~25자리 입력해주세요.";
        pwCheckMessage.style.color = "red";
    } else {
        this.style.border = "2px solid green";
        pwCheckMessage.innerText = "사용 가능한 비밀번호입니다.";
        pwCheckMessage.style.color = "green";
    }
});


document.getElementById("CheckPW").addEventListener("keyup", function () {
    // 실시간 input에 입력받은 내용
    var liveValue = document.getElementById("CheckPW").value;
    var pwdCheck = document.getElementById("InputPW").value;
    var checkpwMessage = document.getElementById("checkpw_check_message");

    if (liveValue !== pwdCheck) {
        this.style.border = "2px solid red";
        checkpwMessage.innerText = "비밀번호가 일치하지 않습니다. 다시 확인해주세요.";
        checkpwMessage.style.color = "red";
    } else {
        this.style.border = "2px solid green";
        checkpwMessage.innerText = "비밀번호가 일치합니다.";
        checkpwMessage.style.color = "green";
    }
});


function password_form_check() {
    var InputPW = document.getElementById("InputPW");
    var CheckPW = document.getElementById("CheckPW");

    if (InputPW.value === "") {
        alert("비밀번호를 입력하세요.");
        InputPW.focus();
        return false;
    }

    // 비밀번호 영문자 + 숫자 조합 (6~25자리 입력) 정규식
    var pwdCheck = /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,25}$/;

    if (!pwdCheck.test(CheckPW.value)) {
        alert("비밀번호는 영문자 + 숫자 조합으로 6~25자리 사용해야합니다.");
        CheckPW.focus();
        return false;
    }

    if (InputPW.value !== CheckPW.value) {
        alert("비밀번호가 일치하지 않습니다.");
        CheckPW.focus();
        return false;
    }


    document.updatePassword_form.submit();
}
