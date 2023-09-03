window.onload = function() {
    // findMyLesson 함수를 비동기적으로 호출
    setTimeout(findMyLesson, 0);

    setTimeout(function() {
        const element = document.getElementById("mylesson-list");
        const loadingElement = document.getElementById("mylessonlistloading_wrap");

        loadingElement.style.display = "none";
        element.style.display = "flex";
        element.style.flexDirection = "column";
    }, 2000); // 2초 후에 실행
}

  	function findMyLesson(){
  	$.ajax({
    type:"GET",
  	url : `/api/enrollList`,
  	dataType:"json",
  	//async:"false",
  	success : function(response){

  	    if(!response.length){
                    document.querySelector('#mylesson-list').innerHTML=
                    '<div class="mylesson_none"><div><p>아직 등록한 레슨이 없습니다!</p></div><div><a href="/lesson-list">💪레슨 둘러보기</a></div></div>';
                    return false;
        }
        // 강좌가 있는 경우
        let listHtml = "";
        response.forEach(enroll =>{

        listHtml += `
        <div class="mylesson-item">
            <div class="mylesson-info">
                <div class="myl-title">${enroll.lesson.title}</div>
                <div class="myl-trainer">트레이너 ${enroll.lesson.trainer.trainerName}</div>
            </div>
            <div class="enter-button">
                <button onclick="checkRoom('${enroll.lesson.titleCode}','${enroll.enrollKey}');">Connect GYM!</button>

            </div>
        </div>
        `;

        });

        document.querySelector('#mylesson-list').innerHTML=listHtml;
  	},
  	error : function (request,status,error){
        console.log(error)
        }


  	});


  	}
