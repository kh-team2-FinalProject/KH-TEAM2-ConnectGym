 //DOM 생성 후
		var originalID, cloneID; //인터벌 포인터
        window.addEventListener('DOMContentLoaded', function(){

			//롤링 배너 복제본 생성
            let roller = document.querySelector('.trainerList_all_content_detail_roller');
            roller.id = 'roller1';

            let clone = roller.cloneNode(true);
            clone.id = 'roller2';
            document.querySelector('.trainerList_all_content_detail_wrap').appendChild(clone); //부착

			//원본, 복제본 배너 위치 지정
            document.querySelector('#roller1').style.left = '0px';
            document.querySelector('#roller2').style.left = document.querySelector('.trainerList_all_content_detail_roller ul').offsetWidth+'px';

			//클래스 할당
            roller.classList.add('original');
            clone.classList.add('clone');

			//인터벌 메서드로 애니메이션 생성
            let rollerWidth = document.querySelector('.trainerList_all_content_detail_roller ul').offsetWidth;//회전 배너 너비값
			let betweenDistance = 1;//이동 크기 - 정수여야 함

			//롤링 시작
			function startRoller(){
				originalID = window.setInterval(betweenRollCallback, parseInt(1000/100), betweenDistance, document.querySelector('#roller1'));
				cloneID = window.setInterval(betweenRollCallback, parseInt(1000/100), betweenDistance, document.querySelector('#roller2'));
			}

			//롤링 정지
			function stopRoller(){
				clearInterval(originalID);
				clearInterval(cloneID);
			}

            //마우스 호버시 롤링이 멈추었다 벗어나면 다시 롤링이 되도록 처리
			document.getElementById('roller1').addEventListener('mouseover',()=>{stopRoller()});
			document.getElementById('roller2').addEventListener('mouseover',()=>{stopRoller()});
			document.getElementById('roller1').addEventListener('mouseout',()=>{startRoller()});
			document.getElementById('roller2').addEventListener('mouseout',()=>{startRoller()});

			//인터벌 애니메이션 함수(공용)
			function betweenRollCallback(d, roller){
				let left = parseInt(roller.style.left);
				roller.style.left = (left - d)+'px';//이동
				//조건부 위치 리셋
				if(rollerWidth + (left - d) <= 0){
					roller.style.left = rollerWidth+'px';
				}
			}

			startRoller();//롤링 초기화
        });
