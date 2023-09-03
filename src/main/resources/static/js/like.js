
window.onload = () => {
    const checkbox = document.getElementById("ld_likeCheckbox");
    let liked = checkbox.getAttribute("data-liked");

    //초기상태
	updateCheckboxState(liked);

    //변경 시
	checkbox.addEventListener("change", function() {
    		toggleLike();
    });
}


function updateCheckboxState(liked) {
    const checkbox = document.getElementById("ld_likeCheckbox");

	if (liked === "true") {
		checkbox.checked = true;
	} else {
	    checkbox.checked = false;
	}
}

function toggleLike() {
       const checkbox = document.getElementById("ld_likeCheckbox");
       let lessonNo = checkbox.getAttribute("data-lesson-no");
       let liked = checkbox.getAttribute("data-liked");

	if (liked=== "true") {
		$.ajax({
			type: "delete",
			url: `/like/${lessonNo}`,
			dataType: "text"
		}).done(res => {
			checkbox.setAttribute("data-liked", "false");
			checkbox.checked = false;
			$('.ld_like_count').text(parseInt($('.ld_like_count').text()) - 1);

		}).fail(error => {
			alert(error.responseText);
		});
	} else {
		$.ajax({
			type: "post",
			url: `/like/${lessonNo}`,
			dataType: "text"
		}).done(res => {
			checkbox.setAttribute("data-liked", "true");
			checkbox.checked = true;
			$('.ld_like_count').text(parseInt($('.ld_like_count').text()) + 1);

		}).fail(error => {
			alert(error.responseText);
		});
	}
}



