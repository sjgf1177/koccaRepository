function changeFileDir(box) {
        var fileName = box.value;
        var arr = fileName.split("\\");
        fileName = arr[arr.length-1];
        document.QNAFORM.hidAttach.value = fileName;
}    

//파일 첨부하기 처리
function doAttachFile(id) {
	var form = document.QNAFORM;
	
	if (form.attach.value == "") {
		alert("\"찾아보기\" 버튼을 눌러서 첨부할 파일을 선택하여 주십시오.");
		return;
	}
	
	if (form.attach_list.length-1 >= 3) {
		alert("첨부 파일은 3개 까지만 가능합니다.");
		return;
	}
    
     for (var i = 1; i < form.attach_list.length; i++) {
            if(form.attach_list.options[i].text == form.hidAttach.value){
                alert("같은 이름의 파일은 두번 첨부할 수 없습니다.");
                return;
            }
    }  


	if(id != "-1") form.ansId.value = id;	
	
	form.target = "ZEROFRAME";	
	form.action = "/portal/include/user/ks_file_attach.jsp";	
	show_waiting();
	form.submit();
}



//첨부파일 삭제 처리
function doDeleteFile(id, tp) {
	var form = document.QNAFORM;
	var flist = "";
	
	if (form.attach_list.selectedIndex <= 0) {
		alert("삭제할 첨부 파일을 선택하여 주십시오.");
		return;
	}
	
	for (var i = 1; i < form.attach_list.length; i++) {
		if (form.attach_list.selectedIndex != i) {
			flist += form.attach_list[i].value + ";";
		}
		else {
			form.deleteFile.value = form.attach_list[i].value;
			form.deleteIdx.value = i;
		}
	}
	

	if(id != "-1") form.ansId.value = id;			

	form.type.value = tp;
	form.target = "ZEROFRAME";	
	form.action = "/portal/include/user/ks_file_delete.jsp";	
		
	show_waiting();
	form.submit();
}


//파일첨부/삭제 시 waiting이미지
function show_waiting()
{
	var _x = document.body.clientWidth/2 + document.body.scrollLeft - 60;
	var _y = document.body.clientHeight/2 + document.body.scrollTop - 74;
	waiting.style.posLeft=_x;
	waiting.style.posTop=_y;
	waiting.style.visibility='visible';	
}

//이미지 숨기기
function hide_waiting(){
	waiting.style.visibility='hidden';
}


/********* 게시판에서의 첨부파일 처리 ****************/
//파일 첨부 처리
function doBulletinAttachFile(frm) {
	var form = frm;
	
	if (form.attach.value == "") {
		alert("\"찾아보기\" 버튼을 눌러서 첨부할 파일을 선택하여 주십시오.");
		return;
	}
	
	if (form.attach_list.length-1 >= 3) {
		alert("첨부 파일은 3개 까지만 가능합니다.");
		return;
	}
    
     for (var i = 1; i < form.attach_list.length; i++) {
            if(form.attach_list.options[i].text == form.hidAttach.value){
                alert("같은 이름의 파일은 두번 첨부할 수 없습니다.");
                return;
            }
    }  
	
	form.target = "ZEROFRAME";	
	form.action = "/portal/include/bulletin_file_attach.jsp";	
	show_waiting();
	form.submit();
}


//첨부파일 삭제처리
function doBulletinDeleteFile(frm) {
	var form = frm;
	var flist = "";
	
	if (form.attach_list.selectedIndex <= 0) {
		alert("삭제할 첨부 파일을 선택하여 주십시오.");
		return;
	}
	
	for (var i = 1; i < form.attach_list.length; i++) {
		if (form.attach_list.selectedIndex != i) {
			flist += form.attach_list[i].value + ";";
		}
		else {
			form.deleteFile.value = form.attach_list[i].value;
			form.deleteIdx.value = i;
		}
	}
	
	form.target = "ZEROFRAME";	
	form.action = "/portal/include/bulletin_file_delete.jsp";	
		
	show_waiting();
	form.submit();
}


function BulletinChangeFileDir(box, frm) {
        var fileName = box.value;
        var arr = fileName.split("\\");
        fileName = arr[arr.length-1];
        frm.hidAttach.value = fileName;
}    