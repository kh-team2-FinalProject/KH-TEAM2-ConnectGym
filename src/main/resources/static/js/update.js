document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementsByName('edit_form')[0];
    form.addEventListener('submit', function(e) {
        e.preventDefault();  // 기본 제출 동작을 막습니다.
        alert("정보수정이 완료 되었습니다");

        // http://localhost:5000 으로 이동
        window.location.href = 'http://localhost:5000';
    });
});
