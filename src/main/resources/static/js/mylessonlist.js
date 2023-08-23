   window.onload = () => {
   console.log("js 실행 됐나요 - 별도파일");
     		findMyLesson();
  	}

  	function findMyLesson(){
  	$.ajax({
    type:"GET",
    /*${session.no}*/
  	url : `/api/lessonlist?memberId=1`,
  	dataType:"json",
  	async:"false",
  	success : function(response){

  	    if(!response.length){
                    document.querySelector('#mylesson-list').innerHTML=
                    '<div class="mylesson_none"><p>등록한 레슨이 없습니다.</p></div>';
                    return false;
        }
        // 댓글이 있는 경우
        let listHtml = "";
        response.forEach(val =>{

        listHtml += `
        <div class="mylesson-item">
            <div class="mylesson-info">
                <div class="myl-title">${val.lesson.title}</div>
                <div class="myl-trainer">트레이너 ${val.lesson.trainer.trainerName} / PT 진행 횟수 1/30 (넣으면 좋겠다)</div>
            </div>
            <div class="enter-button">
                <button onclick="location.href='/enterroom/${val.lesson.no}'">Connect GYM!</button>
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
