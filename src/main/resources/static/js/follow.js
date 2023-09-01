
window.onload = () => {
    const checkbox = document.getElementById("td_followCheckbox");
    let followed = checkbox.getAttribute("data-followed");

    //초기상태
	updateCheckboxState(followed);

    //변경 시
	checkbox.addEventListener("change", function() {
    		toggleFollow();
    });
}


function updateCheckboxState(followed) {
    const checkbox = document.getElementById("td_followCheckbox");

	if (followed=== "true") {
		checkbox.checked = true;
	} else {
	    checkbox.checked = false;
	}
}

function toggleFollow() {
       const checkbox = document.getElementById("td_followCheckbox");
       let toTrainerNo = checkbox.getAttribute("data-trainer-no");
       let followed = checkbox.getAttribute("data-followed");

	if (followed=== "true") {
		$.ajax({
			type: "delete",
			url: `/follow/${toTrainerNo}`,
			dataType: "text"
		}).done(res => {
			checkbox.setAttribute("data-followed", "false");
			checkbox.checked = false;
			$('.td_trainer_follow_count').text(parseInt($('.td_trainer_follow_count').text()) - 1);

		}).fail(error => {
			alert(error.responseText);
		});
	} else {
		$.ajax({
			type: "post",
			url: `/follow/${toTrainerNo}`,
			dataType: "text"
		}).done(res => {
			checkbox.setAttribute("data-followed", "true");
			checkbox.checked = true;
			$('.td_trainer_follow_count').text(parseInt($('.td_trainer_follow_count').text()) + 1);

		}).fail(error => {
			alert(error.responseText);
		});
	}
}



