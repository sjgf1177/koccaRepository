
function logout(){
	fm = document.LOGOUT;
	fm.action = '/learn/mm/UMemberLogoutCud.jsp';
	fm.submit();
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
	 	obj = window.open(theURL,winName,features);
	 	obj.focus();
	 	
	  
}

function huntingView(id){
	document.KMFORM.qnaId.value = "";
	document.KMFORM.backJsp.value = "/portal/user/knowhow/hunting_list.jsp";
	document.KMFORM.action = "/portal/user/knowhow/hunting_view.jsp";
	document.KMFORM.submit();	
}

function goSearchMain(gp){
	var fm = document.GOSEARCH;
	
	fm.action = "/search/search_main";
	fm.group.value = gp;
	fm.submit();	
}

function viewQna(id){
	document.KMFORM.qnaId.value = id;
	document.KMFORM.backJsp.value = "/portal/user/knowledge/km_list.jsp";
	document.KMFORM.action = "/portal/user/knowledge/km_view.jsp?imgTitle=km_list";
	document.KMFORM.submit();	
}

function viewKnowhow(id){
	document.KMFORM.qnaId.value = id;
	document.KMFORM.backJsp.value = "/portal/user/knowhow/knowhow_list.jsp";
	document.KMFORM.action = "/portal/user/knowhow/knowhow_view.jsp?imgTitle=knowhow_list";
	document.KMFORM.submit();	
}

function viewRecommend(id){
	document.KMFORM.qnaId.value = id;
	document.KMFORM.backJsp.value = "/portal/user/knowledge/km_recommend_list.jsp";
	document.KMFORM.action = "/portal/user/knowledge/km_view.jsp?imgTitle=km_recommend_list";
	document.KMFORM.submit();	
}

function viewPopularKnowhow(id){
	document.KMFORM.qnaId.value = id;
	document.KMFORM.backJsp.value = "/portal/user/knowhow/knowhow_popular_list.jsp";
	document.KMFORM.action = "/portal/user/knowhow/knowhow_view.jsp?imgTitle=knowhow_popular_list";
	document.KMFORM.submit();	
}

function viewPopularQna(id){
	document.KMFORM.qnaId.value = id;
	document.KMFORM.backJsp.value = "/portal/user/knowledge/km_popular_list.jsp";
	document.KMFORM.action = "/portal/user/knowledge/km_view.jsp?imgTitle=km_popular_list";
	document.KMFORM.submit();	
}

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


// 상세 보기 함수
function doView(contentId, lineNum, userId){
	var fm = document.VIEW_FORM;
		
	fm.CONTENT_ID.value = contentId;
	fm.LINE_NUM.value = lineNum;
	fm.USER_ID.value = userId;
	fm.action = "./notice/km_notice_view.jsp";
	fm.submit();
}

function viewUser(userId) {
		var url = "/portal/include/user_view.jsp?USER_ID=" + userId;
						
		MM_openBrWindow(url,'USER_INFO','status=yes, resizable=no,scrollbars=no,width=360,height=315');
}
