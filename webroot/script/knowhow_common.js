/* common start */

function logout(){
	fm = document.LOGOUT;
	fm.action = '/learn/mm/UMemberLogoutCud.jsp';
	fm.submit();
}

//목록가기
function showList(cateId, cateName) { 				
	var url = "/portal/user/knowhow/knowhow_list.jsp?cateId=" + cateId;		
			
	location.href = url;
}

//지식 전달하기
function sendEmail(id, menu, imgTitle){
	fm = document.KNOWFORM;
	
	fm.qnaId.value = id;

	var url = "/portal/user/knowledge/km_write_email.jsp?menu="+menu+"&imgTitle="+imgTitle;
	MM_openBrWindow(url,'SEND_EMAIL','width=775,height=565');
}


function MM_openBrWindow(theURL,winName,features) { //v2.0
	 	obj = window.open(theURL,winName,features);
	 	obj.focus();	  
}

function goSearchMain(gp){
	var fm = document.GOSEARCH;
	
	fm.action = "/search/search_main";
	fm.group.value = gp;
	fm.submit();	
}

//카테고리 팝업창 열기
function categoryOpen(menu, write) { 	
	MM_openBrWindow('/portal/include/user/popup_category.jsp?menu='+menu+'&write='+write, 'CATEGORY', 'width=350,height=510');	
}

function doSubmit() {
    document.KNOWFORM.submit();    
}

//노하우 등록하기
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

/* common end */

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


/* knowhow_list.jsp start */
function page_view(page){
	document.KNOWFORM.page.value = page;
	document.KNOWFORM.submit();	
}

//노하우 조회
function viewContent(docId){
	document.KNOWFORM.action = "/portal/user/knowhow/knowhow_view.jsp";	
	document.KNOWFORM.qnaId.value = docId;	
	document.KNOWFORM.submit();	
}

//노하우 목록가기
function viewKnowhowList(){		
	//document.KNOWFORM.action = "/portal/user/knowhow/knowhow_list.jsp";		
	document.KNOWFORM.action = document.KNOWFORM.backJsp.value;		
	document.KNOWFORM.submit();	
}

/* knowhow_list.jsp end */

/* knowhow_view.jsp start */
//코멘트 쓰기
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

//나의 스크랩 폴더 보기
function viewMyFolder(id, menu){	
	var url = "/learn/user/study/my_knowledge_scrap.jsp?qnaId="+id+"&menu="+menu;
	
	MM_openBrWindow(url,'MY_FOLDER','width=350,height=215');
}

//불량지식 신고
function reportKnowledge(id){
	fm = document.KNOWFORM;
	if(false == confirm("불량지식으로 신고하시겠습니까?"))
		return;
	fm.qnaId.value = id;
	fm.action = "/portal/user/knowledge/km_report_insert.jsp";		
	fm.target = "ZEROFRAME";
	fm.submit();
}

//지식 추천하기
function recommendHitKnowledge(id, menu){
	fm = document.KNOWFORM;
	
	fm.qnaId.value = id;
	fm.action = "/portal/user/knowledge/km_recommend_hit_insert.jsp?menu="+menu;		
	fm.target = "_self";
	fm.submit();
}


//노하우 수정
function editKnowhow(id){	
	fm = document.KNOWFORM;
	
	fm.qnaId.value = id;
	fm.action = "/portal/user/knowledge/km_write.jsp";
	fm.submit();
}

function delKnowhow(id){
	if(false == confirm("해당 코멘트까지 삭제됩니다. 정말 삭제하시겠습니까?"))
		return;
	fm = document.KNOWFORM;
	
	fm.qnaId.value = id;
	fm.action = "/portal/user/knowhow/knowhow_write_delete.jsp";
	fm.submit();
}
/* knowhow_view.jsp end */

/* hunting_list.jsp start */
//지식헌팅 조회
function viewHuntingContent(docId){
	document.KNOWFORM.action = "/portal/user/knowhow/hunting_view.jsp";	
	document.KNOWFORM.qnaId.value = docId;	
	document.KNOWFORM.submit();	
}

//지식헌팅 답변등록하기
function huntingWrite(docId){
	var fm = document.KNOWFORM;

	if(document.all.use_editor[0].checked) {        
            fm.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    }else {
            fm.content.value = document.all.txtDetail.value;
    }
	
	fm.qnaId.value = docId;
	fm.action = "/portal/user/knowhow/hunting_insert.jsp";	
	fm.submit();	
}

//지난 지식헌팅 보기(지식헌팅 목록)
function viewPassedHunting(){
	var fm = document.KNOWFORM;
	
	fm.backJsp.value = "/portal/user/knowhow/hunting_view.jsp";
	fm.action = "/portal/user/knowhow/hunting_list.jsp";	
	fm.submit();	
}

//헌팅 상세조회
function viewHunting(){
	var fm = document.KNOWFORM;
	
	fm.backJsp.value = "/portal/user/knowhow/hunting_list.jsp";
	fm.action = "/portal/user/knowhow/hunting_view.jsp";	
	fm.submit();	
}
/* hunting_list.jsp end */


function searchAction(logCheck, logType){  				
	var fm = document.SEARCHFORM;
	var coll = fm.coll.value;	
	
	if(logType == "out"){
		alert("외부사용자는 통합검색을 이용하실수 없습니다.");		
		return;
	}
	
	if(logCheck == 0){
		alert("로그인을 먼저 하셔야 통합검색을 이용하실수 있습니다.");
		document.LOGINFORM.id.focus();
		return;
	}
	if(fm.query.value.length < 1){
		alert("검색어를 입력 하세요.");
		fm.query.focus();
		return;
	}	
	if(coll == "HELPER" || coll=="KM" || coll == "E=LEARNING") fm.disNum.value = 10;
	else fm.disNum.value = 3;
	fm.action = get_collection_url(coll, "");				        	        
	fm.target = "_self";	
	
	fm.submit();	        	
	return;	
}

function get_collection_url(coll){
		var actionUrl="";
		
		
		if(coll == "PORTAL")		actionUrl = "/search/PortalSearch";
		else if(coll == "CONTENT")	actionUrl = "/search/ContentSearch";
		else if(coll == "TOT_CAFE")	actionUrl = "/search/TotCafeSearch";						
		else if(coll == "HELPER")	actionUrl = "/search/HelperSearch";
		else if(coll == "KM")		actionUrl = "/search/KmSearch";
		else if(coll == "E-LEARNING")	actionUrl = "/search/ELearningSearch";
		else if(coll == "KNOWLEDGE")	actionUrl = "/search/KnowledgeSearch";
		
		return actionUrl;		
}

//사용자 정보보기(팝업)
function viewUser(userId) {
		var url = "/portal/include/user_view.jsp?USER_ID=" + userId;
						
		MM_openBrWindow(url,'USER_INFO','status=yes, resizable=no,scrollbars=no,width=360,height=315');
}

//노하우 목록가기
function goKnowhowList(){
	var fm = document.QNAFORM;
	
	fm.action = "/portal/user/knowhow/knowhow_list.jsp";	
	fm.submit();	
}

//노하우 수정
function modiKnowhow(id){
	var fm = document.KNOWFORM;
	
	fm.qnaId.value = id;
	fm.action = "/portal/user/knowhow/knowhow_write.jsp";	
	fm.submit();	
}

//파일 다운로드
function fileDownload(name){
	location.target = "ZEROFRAME";
	
	location.href = "../include/download.jsp?delFile=no&fileName="+name;
}

//목록 내 검색하기
function searchList(){
	fm = document.KNOWFORM;
	
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