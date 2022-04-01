window.onkeydown = function() {
    var kcode = event.keyCode;
    if(kcode == 8){
    	//alert("학습 중 뒤로가기 클릭 시 진도율 반영에 문제가 발생할 수 있습니다.");
    	event.returnValue = false;
    }
}