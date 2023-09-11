document.addEventListener("DOMContentLoaded", function () {
    function email_split(trainerEmail) {
        var email = trainerEmail.split("@");
        var email01 = email[0];
        var email02 = email[1];
        var em1 = document.getElementById('email001');
        em1.value = email01;
        document.getElementById('email002').value = email02;

        document.getElementById('email001').setAttribute('readonly', true);
        document.getElementById('email002').setAttribute('readonly', true);
    }


    email_split(trainerEmail);

});


