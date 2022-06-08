$("#loadBg").fadeOut(1000);

/* 포팅시 수정해야 할 함수 모음 */


// 백넥
function goPrevPage() {

	if(currentPage == 1){
		alert("차시의 첫 페이지 입니다.");
		return;
	}
	
	presentPageIndex = currentPage - 1;
	document.location = pages[presentPageIndex - 1].url;
}

function goNextPage() {
	if(currentPage == totalPage){
		alert("차시의 마지막 페이지 입니다.");
		return;
	}
	
	if(parent.isPageControl) {
		presentPageIndex = currentPage - 1;
		document.location = pages[presentPageIndex + 1].url;
	}else{
		alert("해당 페이지 학습이 완료되지 않았습니다.");
	}
}


// 네비 점프메뉴
function goPageJump(arg){
	//arg 는 페이지 번호를 받음
	//alert("페이지:"+pages[arg].url);	
	
	document.location =pages[arg].url;
}


// 의견입력
function kpc_write(){

	//alert("맛보기에서는 지원하지 않습니다.");
	//return;

	//alert($("#opinionInput").val());

	 if($("#opinionInput").val()!=defaultString){
			alert("맛보기에서는 지원하지 않습니다.");
			return;
		 }else{	
			alert("의견을 입력해야 합니다.");
			return;
		}
	}



function kpc_list(){
		alert("맛보기에서는 지원하지 않습니다.");
		return;
}