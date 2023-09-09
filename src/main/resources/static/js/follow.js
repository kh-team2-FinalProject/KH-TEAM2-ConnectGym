window.onload = () => {
  const checkbox = document.getElementById("td_followCheckbox");
  let followed = checkbox.getAttribute("data-followed");

  applyStarRating();

  //초기상태
  updateCheckboxState(followed);

  //변경 시
  checkbox.addEventListener("change", function () {
    toggleFollow();
  });
};

function updateCheckboxState(followed) {
  const checkbox = document.getElementById("td_followCheckbox");

  if (followed === "true") {
    checkbox.checked = true;
  } else {
    checkbox.checked = false;
  }
}

function toggleFollow() {
  const checkbox = document.getElementById("td_followCheckbox");
  let toTrainerNo = checkbox.getAttribute("data-trainer-no");
  let followed = checkbox.getAttribute("data-followed");

  if (followed === "true") {
    $.ajax({
      type: "delete",
      url: `/follow/${toTrainerNo}`,
      dataType: "text",
    })
      .done((res) => {
        checkbox.setAttribute("data-followed", "false");
        checkbox.checked = false;
        $(".td_trainer_follow_count").text(
          parseInt($(".td_trainer_follow_count").text())-1
        );
        Swal.fire({
          position: 'center',
          width: '300px',
          height:'30px',
          background:'rgba(215, 214, 214, 0.761)',
          html: '<div style="font-size:16px;">팔로우가 취소되었습니다.</div>',
          showConfirmButton: false,
          timer: 800
        })
      })
      .fail((error) => {
        alert(error.responseText);
      });
  } else {
    $.ajax({
      type: "post",
      url: `/follow/${toTrainerNo}`,
      dataType: "text",
    })
      .done((res) => {
        checkbox.setAttribute("data-followed", "true");
        checkbox.checked = true;
        $(".td_trainer_follow_count").text(
          parseInt($(".td_trainer_follow_count").text())+1
        );
         Swal.fire({
                  position: 'center',
                  width: '300px',
                  height:'30px',
                  background:'rgba(215, 214, 214, 0.761)',
                  html: '<div style="font-size:16px;">팔로우 되었습니다.</div>',
                  showConfirmButton: false,
                  timer: 800
                })
      })
      .fail((error) => {
        alert(error.responseText);
      });
  }
}
