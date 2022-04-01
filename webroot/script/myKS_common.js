/* common start */
function MM_openBrWindow(theURL,winName,features) { //v2.0
	 	obj = window.open(theURL,winName,features);
	 	obj.focus();
	 	
	  
}

 //Trim 함수
	//===================================
	function trim(arg,func) {
	//===================================
		var trimvalue = "";
		arglen = arg.length;
		if (arglen < 1) return trimvalue;
		if (func == "left" || func== "both") {
			i = 0;
			pos = -1;
			while (i < arglen) {
				if (arg.charCodeAt(i) != 32 && !isNaN(arg.charCodeAt(i))) {
					pos = i;
					break;
				}
				i++;
			}
		}
		if (func == "right" || func== "both") {
			var lastpos = -1;
			i = arglen;
			while (i >= 0) {
				if (arg.charCodeAt(i) != 32 && !isNaN(arg.charCodeAt(i))) {
					lastpos = i;
					break;
				}
				i--;
			}
		}
		if (func == "left") {
				trimvalue = arg.substring(pos,arglen-1);
		}
		if (func == "right") {
			trimvalue = arg.substring(0,lastpos+1);
		}
		if (func == "both") {
			trimvalue = arg.substring(pos,lastpos + 1);
		}
		return trimvalue;
	}
	//////////////////////////////////////////////////////////////////

function logout(){
	fm = document.LOGOUT;
	fm.action = '/learn/mm/UMemberLogoutCud.jsp';
	fm.submit();
}

function goSearchMain(gp){
	var fm = document.GOSEARCH;
	
	fm.action = "/search/search_main";
	fm.group.value = gp;
	fm.submit();	
}

function doSubmit() {
	var form = document.MYFORM;
    	form.submit();    
}

/* common end */
//나의 질문답변 조회
function viewMainKnowledge(docId){	
	var form = document.MYFORM;
	
	form.backJsp.value = "/learn/user/study/my_list.jsp";		
	form.action = "/portal/user/knowledge/km_view.jsp?imgTitle=km";		
	form.qnaId.value = docId;	
	form.submit();	
}

//나의 노하우 조회
function viewMainKnowhow(docId){	
	var form = document.MYFORM;
	
	form.backJsp.value = "/learn/user/study/my_knowhow_list.jsp";		
	form.action = "/portal/user/knowhow/knowhow_view.jsp?imgTitle=knowhow";		
	form.qnaId.value = docId;	
	form.submit();	
}

//나의 스크랩 조회
function viewKnowledgeScrap(docId){	
	var form = document.MYFORM;
	
	form.backJsp.value = "/learn/user/study/my_scrap_list.jsp";		
	form.action = "/portal/user/knowledge/km_view.jsp?imgTitle=scrap";	
	form.qnaId.value = docId;	
	form.submit();	
}

//스크랩한 노하우 보기
function viewKnowhowScrap(docId){	
	var form = document.MYFORM;
	
	form.backJsp.value = "/learn/user/study/my_scrap_list.jsp";		
	form.action = "/portal/user/knowhow/knowhow_view.jsp?imgTitle=scrap";		
	form.qnaId.value = docId;	
	form.submit();	
}


/* myKS_list.jsp start */
//질문조회
function viewKnowledge(docId, title){	
	var form = document.MYFORM;
	if(title == "km")	form.action = "/portal/user/knowledge/km_view.jsp?imgTitle=km";		
	else if(title == "scrap") form.action = "/portal/user/knowledge/km_view.jsp?imgTitle=scrap";
	else if(title == "service") form.action = "/portal/user/knowledge/km_view.jsp?imgTitle=service";
	form.qnaId.value = docId;	
	form.submit();	
}

//노하우 조회
function viewKnowhow(docId){	
	var form = document.MYFORM;
	
	//form.backJsp.value = "/learn/user/study/my_knowhow_list.jsp";		
	form.action = "/portal/user/knowhow/knowhow_view.jsp?imgTitle=knowhow";		
	form.qnaId.value = docId;	
	form.submit();	
}

//해당 페이지 목록보기
function page_view(page){
	var form = document.MYFORM;
	
	form.page.value = page;
	form.submit();	
}

//나의 질문목록
function showQuestion(){
	var form = document.MYFORM;
	
	form.searchWord.value = "";
	form.type.value = 0;
	form.page.value = 1;
	form.submit();	
}

//나의 답변목록
function showAnswer(){
	var form = document.MYFORM;
	
	form.searchWord.value = "";
	form.type.value = 1;
	form.page.value = 1;
	form.submit();	
}

/* myKS_list.jsp end */
//카테고리 목록
function showCateList(){
	MM_openBrWindow('/learn/user/study/my_cate_list.jsp','','width=404,height=327');
}

//스크랩 폴더 추가하기
function myFolderAdd(){
	var form = document.MYFORM;	
	
	if(form.newFolder.value.length < 1){
		alert("폴더명을 입력해 주세요.");
		form.newFolder.focus()
		return;	
	}
	
	form.submit();
}

//스크랩 폴더 수정하기
function myFolderMod(tot, seq){
	
	var folder = document.getElementsByName("folderName" +seq);
	
	if(folder[0].value.length < 1) {
		alert("수정할 이름을 입력하세요");
		folder[0].focus();
		return;
	}
	
	var form = document.MYFORM;
	form.action = "/learn/user/study/my_folder_update.jsp";			
	form.loopSeq.value = seq;		
	form.submit();
}

//스크랩 폴더 삭제
function myFolderDel(tot, seq, count){
	if(false == confirm("폴더를 삭제하시면 폴더안의 스크랩한 지식도 삭제됩니다. 삭제하시겠습니까?"))
		return;
		
	var form = document.MYFORM;
	form.action = "/learn/user/study/my_folder_delete.jsp";		
	form.loopSeq.value = seq;		
	form.submit();
}

function selectCate(chFm, formName) {
	var obj =document.getElementsByName(formName);
	var option;
	
	if(obj[0].checked == true){
		option = false;
		chFm.checked = false;
	}else{
		option = true;
		chFm.checked = true;
	}
	
	for(var i=0; i < obj.length; i++) {
		obj[i].checked = option;
	}
}

//스크랩 지식 전체선택
function selectAll(chFm, formName) {
	var obj =document.getElementsByName(formName);
	var option;
	
	if(document.MYFORM.scrapId == null) {
		alert("스크랩한 지식이 없습니다.");
		return;
	}

	if(obj[0].checked == true){
		option = false;
		chFm.checked = false;
	}else{
		option = true;
		chFm.checked = true;
	}
	
	for(var i=0; i < obj.length; i++) {
		obj[i].checked = option;
	}
}

//스크랩 삭제
function scrapDelete(){		
	var form = document.MYFORM;
	
	var obj =document.getElementsByName("scrapId");
	var checkedValue = "";
	for(var i=0; i < obj.length; i++) {
		if(obj[i].checked){
			checkedValue += "scrapId=";
			checkedValue += obj[i].value;
			checkedValue += "&";
		}
	}
	if(checkedValue.length < 1){
		alert("삭제할 지식을 선택해 주세요.");
		return;
	}
	
	if(false == confirm("삭제하시겠습니까?"))
		return;
	
	form.action = "/learn/user/study/my_scrap_delete.jsp";			
	form.submit();
}

//스크랩하기
function scrapAdd(){
	var form = document.MYFORM;
		
	if(form.folderId.value == ""){
		alert("스크랩할 폴더를 선택해 주세요.");
		return;
	}
	
	form.action = "/learn/user/study/my_knowledge_insert.jsp";			
	form.submit();
}

//나의 스크랩 폴더보기
function goMyScrap(menu){
	var url = "/learn/user/study/my_folder_list.jsp?menu="+menu;
	MM_openBrWindow(url,'GO_FOLDER','status=yes, resizable=no,scrollbars=no,width=357,height=375');	
}

//관심 카테고리 설정하기
function serviceCateAdd(){
	var form = document.MYFORM;
	
	var obj =document.getElementsByName("serviceCate");
	var checkedValue = "";
	for(var i=0; i < obj.length; i++) {
		if(obj[i].checked){
			checkedValue += "serviceCate=";
			checkedValue += obj[i].value;
			checkedValue += "&";
		}
	}	
	form.action = "/learn/user/study/my_service_insert.jsp";			
	form.submit();
}


//사용자 정보보기
function viewUser(userId) {
		var url = "/portal/include/user_view.jsp?USER_ID=" + userId;
						
		MM_openBrWindow(url,'USER_INFO','status=yes, resizable=no,scrollbars=no,width=360,height=315');
}

//목록 내 검색하기
function searchList(){
	fm = document.MYFORM;
	
	var sWord = trim(fm.searchWord.value,"both");
	if(sWord.length < 1){
		alert("검색할 단어를 입력하세요.");
		fm.searchWord.value ="";
		fm.searchWord.focus();
		return false;
	}
	fm.searchWord.value = sWord;
	fm.submit();
}


/* km_myview.jsp start */
//답변달기
function writeAnswer(){
	fm = document.QNAFORM;
	fm.action = "/portal/user/knowledge/km_re_write.jsp";
	fm.ansSeq.value = "";		
	fm.target = "_self";
	fm.submit();
}

//질문목록가기
function viewKmList(){		
	document.QNAFORM.action = document.QNAFORM.backJsp.value;		
	document.QNAFORM.target = "_self";
	document.QNAFORM.submit();	
}

//코멘트 쓰기
function writeComment(seq){	
	var fm = document.QNAFORM;
		
	var comment = eval("fm.comment"+seq +".value");
	if(trim(comment, "both") == "") {
		alert("코멘트를 입력하세요.");
		return false;
	}
	/*if(fm.comment.value.length < 1){
		alert("코멘트를 입력하세요.");
		fm.comment.focus();
		return false;
	}*/
	fm.ansSeq.value = seq;		
	fm.action = "/portal/user/knowledge/km_comment_insert.jsp";		
	fm.target = "_self";
	fm.submit();	
}


//코멘트 삭제
function commentDelete(delId){
	if(false == confirm("삭제하시겠습니까?"))
		return;
			
	document.QNAFORM.commentId.value = delId;
	document.QNAFORM.action = "/portal/user/knowledge/km_comment_delete.jsp";		
	document.QNAFORM.target = "_self";
	document.QNAFORM.submit();	
}

//답변 삭제
function deleteAnswer(seq){
	if(false == confirm("답변 및 해당 코멘트가 삭제됩니다. 정말 삭제하시겠습니까?"))
		return;
	
	document.QNAFORM.ansSeq.value = seq;	
	document.QNAFORM.action = "/portal/user/knowledge/km_re_write_delete.jsp";		
	document.QNAFORM.target = "_self";
	document.QNAFORM.submit();
}

//답변수정
function updateAnswer(seq){
	document.QNAFORM.ansSeq.value = seq;		
	document.QNAFORM.action = "/portal/user/knowledge/km_re_write.jsp";		
	document.QNAFORM.target = "_self";
	document.QNAFORM.submit();
}

 
//불량지식으로 신고하기
function reportKnowledge(id){
	fm = document.QNAFORM;
	if(false == confirm("불량지식으로 신고하시겠습니까?"))
		return;
	
	fm.qnaId.value = id;
	fm.action = "/portal/user/knowledge/km_report_insert.jsp";		
	fm.target = "ZEROFRAME";
	fm.submit();
}

//추천하기
function recommendHitKnowledge(id){
	fm = document.QNAFORM;
	
	fm.qnaId.value = id;
	fm.action = "/portal/user/knowledge/km_recommend_hit_insert.jsp";		
	fm.target = "_self";
	fm.submit();
}

//나의 스크랩 폴더 보기
function viewMyFolder(id, menu){	
	var url = "/learn/user/study/my_knowledge_scrap.jsp?qnaId="+id+"&menu="+menu;
	
	MM_openBrWindow(url,'MY_FOLDER','width=350,height=215');
}

//전달하기 폼 팝업
function sendEmail(id, menu, homeExeSrcFile){
	fm = document.QNAFORM;
	
	fm.qnaId.value = id;	
	var url = "/portal/user/knowledge/km_write_email.jsp?&menu="+menu+"&homeExeSrcFile="+homeExeSrcFile+"&qnaId="+id;
	MM_openBrWindow(url,'SEND_EMAIL','width=775,height=565');
}

//지식 전달하기
function sendKnowhowEmail(id, menu, homeExeSrcFile){
	fm = document.KNOWFORM;
	
	fm.qnaId.value = id;
	//fm.backJsp.value = "/portal/user/knowhow/knowhow_view.jsp";
	var url = "/portal/user/knowledge/km_write_email.jsp?&menu="+menu+"&homeExeSrcFile="+homeExeSrcFile;
	MM_openBrWindow(url,'SEND_EMAIL','width=775,height=565');
}

//사용자 찾기(전달하기 받는 이)
function searchUser(menu)
{
	fm = document.QNAFORM;
	var name = "";

	if(fm.searchName.value.length < 1){
			alert("질의어를 입력하세요.");
			fm.searchName.focus();
			return;
	}

	fm.searchName.value = trim(fm.searchName.value,"both");
	name = fm.searchName.value;
	
	var url = "/portal/user/knowledge/km_search_user.jsp?keyword="+name+"&menu="+menu;
						
	MM_openBrWindow(url,'SEND_USER','status=yes, resizable=no,scrollbars=no,width=750,height=492');
}

//지식 전달 처리
function send(){
	fm = document.QNAFORM;

	if(fm.sendUserId.value.length < 1){
		alert("받는 사람을 선택하세요.");
		return;
	}
	
	if(fm.title.value.length < 1){
		alert("제목을 입력하세요.");
		fm.title.focus();
		return;
	}
	if(document.all.use_editor[0].checked) {        
            fm.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    }else {
            fm.content.value = document.all.txtDetail.value;
    }
	fm.domainname.value = "http://" + document.domain;
		
	fm.action = "/portal/user/knowledge/km_send_email.jsp";
	fm.submit();
}

//질문 수정
function editKm(id){	
	fm = document.QNAFORM;
	
	fm.qnaId.value = id;
	fm.action = "/portal/user/knowledge/km_write.jsp";
	fm.submit();
}

//질문 삭제
function delKm(id){	

	if(false == confirm("삭제하시겠습니까?"))
		return;	
	fm = document.QNAFORM;
	fm.qnaId.value = id;
	fm.action = "/portal/user/knowledge/km_write_delete.jsp";
	fm.submit();
}

//답변 채택하기
function doAdopt(){
	var fm = document.QNAFORM;
	var adopt = getCheckedRadio("adopt");
	if(adopt == null) {
		alert("채택할 답변을 선택해 주세요.");
		return;
	}
	fm.action = "/portal/user/knowledge/km_re_adopt_insert.jsp";
	fm.submit();
}


//선택된 라디어 버튼 객체 가져오기
function getCheckedRadio(name) {
	var radioList =	document.getElementsByName(name);
	for(var	i=0; i < radioList.length; i++)	{
		if(radioList[i].checked) {
			return radioList[i].value;
		}
	}
	return null;
}
/* km_view.jsp end */


/* knowhow_view.jsp start */
//노하우 코멘트 쓰기
function writeKnowhowComment(){		
	var fm = document.KNOWFORM;

	var comment = eval("fm.comment.value");
	if(trim(comment, "both") == "") {
		alert("코멘트를 입력하세요.");
		return false;
	}
	fm.action = "/portal/user/knowhow/knowhow_comment_insert.jsp";		
	fm.submit();	
}

//노하우 코멘트 삭제
function knowhowCommentDelete(delId){
	if(false == confirm("삭제하시겠습니까?"))
		return;
			
	document.KNOWFORM.commentId.value = delId;
	document.KNOWFORM.action = "/portal/user/knowhow/knowhow_comment_delete.jsp";		
	document.KNOWFORM.submit();	
}

//노하우 수정
function modiKnowhow(id){
	var fm = document.KNOWFORM;
	
	fm.qnaId.value = id;
	fm.action = "/portal/user/knowhow/knowhow_write.jsp";	
	fm.submit();	
}

//노하우 삭제
function delKnowhow(id){
	if(false == confirm("정말 삭제하시겠습니까?"))
		return;
	fm = document.KNOWFORM;
	
	fm.qnaId.value = id;
	fm.action = "/portal/user/knowhow/knowhow_write_delete.jsp";
	fm.submit();
}

//노하우 리스트 보기
function viewKnowhowList(){		
	//document.KNOWFORM.action = "/portal/user/knowhow/knowhow_list.jsp";		
	document.KNOWFORM.action = document.KNOWFORM.backJsp.value;		
	document.KNOWFORM.submit();	
}

/* knowhow_view.jsp end */

//노하우 등록
function knowhowReg(id) {
	var flist;
	form = document.QNAFORM;
	
	
	if(form.title.value.length < 1){
		alert("제목을 입력하세요.");
		form.title.focus();
		return;
	}
	
	if(form.catePath.value.length < 1){
		alert("카테고리를 선택하세요.");
		form.catePath.focus();
		return;
	}

	form = document.QNAFORM;
	if(document.all.use_editor[0].checked) {        
            form.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    }else {
            form.content.value = document.all.txtDetail.value;
    }
	
	for (var i = 0; i < form.attach_list.length; i++) {
		flist += form.attach_list[i].value + ";";
	}
	
	form.attachParam.value = flist;
	
	if(id != "-1") {
		form.qnaId.value = id;			
		form.action = "/portal/user/knowhow/knowhow_write_update.jsp";		
	}else{
		form.action = "/portal/user/knowhow/knowhow_write_insert.jsp";
	}
	form.encoding = "application/x-www-form-urlencoded";
	form.target = "_self";	
	form.submit();    
} 


/* km_write.jsp start */
//질문등록/수정
function qnaWrite(id) {
	fm = document.QNAFORM;
	if(fm.title.value.length < 1){
		alert("제목을 입력하세요.");
		fm.title.focus();
		return;
	}
	
	if(fm.catePath.value.length < 1){
		alert("카테고리를 선택하세요.");
		fm.catePath.focus();
		return;
	}
	
	/* 수정일 경우 */
	if(id != "-1"){	
		fm.qnaId.value = id;
		fm.action = "/portal/user/knowledge/km_write_update.jsp";
	}
	
	if(document.all.use_editor[0].checked) {        
            fm.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    }else {
            fm.content.value = document.all.txtDetail.value;
    }
	fm.target = "_self";
    	fm.submit();    
}

function imgUpload(writeno) {
        window.open('/portal/include/imgUpload.jsp?qnaId='+writeno,'img','width=400, height=360, resizable=no, scrolling=auto');
}

function PlayClick()	{
        window.open('/portal/include/movie.jsp','help','width=400, height=335, resizable=no, scrollbars=no');
}
/* km_write.jsp end */

/* km_re_write.jsp start */  
//답변 등록
function answerWrite(ans_id){

	form = document.QNAFORM;
	if(document.all.use_editor[0].checked) {        
            form.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    }else {
            form.content.value = document.all.txtDetail.value;
    }

	var flist;
		
	/*if(form.answer.value.length <= 0){
		alert("답변을 작성 하세요.");
		form.answer.focus();
		return;
	}*/
	
	for (var i = 0; i < form.attach_list.length; i++) {
		flist += form.attach_list[i].value + ";";
	}
	
	if(ans_id != "-1") {
		form.ansId.value = ans_id;			
		form.action = "/portal/user/knowledge/km_re_write_update.jsp";		
	}else{
		form.action = "/portal/user/knowledge/km_re_write_insert.jsp";	
	}
	form.attachParam.value = flist;
	form.encoding = "application/x-www-form-urlencoded";
	form.attachCount.value = form.attach_list.length-1;	
	form.target = "_self";
	
	
	document.QNAFORM.submit();
}
/* km_re_write.jsp end */



function categoryOpen(menu, write) { 	
	MM_openBrWindow('/portal/include/user/popup_category.jsp?menu='+menu+'&write='+write, 'CATEGORY', 'width=350,height=510');	
}


//추천지식 카테고리별로 보기
function showCategoryList(cateId) {	
	document.MYFORM.cateId.value = cateId;
	document.MYFORM.searchWord.value = "";
	document.MYFORM.submit();    
}
