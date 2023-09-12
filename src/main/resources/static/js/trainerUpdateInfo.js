const selectElement = document.getElementById('select');
const email01Input = document.getElementById('email01');
const email02Input = document.getElementById('email02');
const trinaerEmailInput = document.getElementById('trainerEmail');
const emailMiddleElement = document.getElementById('email_middle');

document.addEventListener("DOMContentLoaded", function () {
    function email_split(trainerEmail) {
        var email = trainerEmail.split("@");
        var email01 = email[0];
        var email02 = email[1];
        document.getElementById('email01').value = email01;

        document.getElementById('email02').value = email02;

    }

    email_split(trainerEmail);
});


// select 요소의 change 이벤트 처리
selectElement.addEventListener('change', function () {
    const selectedOption = selectElement.options[selectElement.selectedIndex];

    if (selectedOption.value === '1') {
        email02Input.value = '';
        email02Input.disabled = false;
    } else { // 직접입력이 아닐 경우
        email02Input.value = selectedOption.textContent;
        email02Input.disabled = true;
        trainerEmail();
    }
});
email01Input.addEventListener('blur', email);

// email02 입력 필드의 blur 이벤트 처리
email02Input.addEventListener('blur', email);

function updateform_check() {
    var InputName = document.getElementById("InputName");
    var InputTel = document.getElementById("InputTel");

    if (InputName.value === "") {
        alert("이름을 입력하세요.");
        InputName.focus();
        return false;
    }
    // 숫자만 입력하는 정규식
    var phoneRule = /^(010)[0-9]{4}[0-9]{4}$/;

    if (!phoneRule.test(InputTel.value)) {
        alert("전화번호를 확인해주세요.");
        InputTel.focus();
        return false;
    }

    if (InputTel.value === "") {
        alert("전화번호를 입력해주세요.");
        InputTel.focus();
        return false;
    }

    function trainerEmail() {
        const email01 = document.getElementById('email01').value;
        const middle = document.getElementById('email_middle').textContent;
        const email02 = document.getElementById('email02').value;
        const trainerEmail = document.getElementById('trainerEmail');

        if (email01 !== '' && email02 !== '') {
            trainerEmail.value = email01 + middle + email02;
        }
    }

    trainerEmail();


    document.trainer_update_form.submit();
}









