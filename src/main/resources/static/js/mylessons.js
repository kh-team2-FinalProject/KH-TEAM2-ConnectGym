   window.onload = () => {
   console.log("js 실행 됐나요 - 별도파일");
     		findMyLesson();
  	}

  	function findMyLesson(){
  	$.ajax({
    type:"GET",
  	url : "/api/lessonlist",
  	dataType:"json",
  	async:"false",
  	success : function(response){
  	    if(!response.length){
                    document.querySelector('#lessonList').innerHTML=
                    '<div class="lesson_none"><p>등록된 댓글이 없습니다.</p></div>';
                    return false;
        }
        // 댓글이 있는 경우
        let listHtml = "";
        response.forEach(val =>{

        listHtml += `
        <div>
        <span>강좌1
        ${val.lesson.trainer.trainerName}
        </span>
        </div>
        `;

        });

        document.querySelector('#lessonList').innerHTML=listHtml;
  	},
  	error : function (request,status,error){
        console.log(error)
        }


  	});


  	}
