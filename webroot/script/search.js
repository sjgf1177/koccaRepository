	function bestSearch(word){
		var fm = document.SEARCHFORM;
		fm.coll.value = "PORTAL";
		fm.logYN.value = "yes";		        	     		        	        
		document.SEARCHFORM.action = "/search/PortalSearch"
		fm.query.value = word;
		fm.submit();
	}

	function queryfocus(){
		document.SEARCHFORM.query.focus();
	}
	
	function MM_openBrWindow(theURL,winName,features) { 
		window.open(theURL,winName,features);
	}
	
	function view_content_KM(id, theURL) { 			
		var url = "http://autoway.hmc.co.kr/external_redirectkm.asp?refurl=elearning&returl=" + theURL + "&userid="+id;		
		
		window.open(url,'','resizable=yes,scrollbars=yes,width=880,height=500');
	}
	
		
	function goSearchMain(){
		var fm = document.SEARCHFORM;		
		fm.action = "/search/search_main";
		fm.submit();
	}
	
	
	function preview_content(coll, conId) { 		
		var query = document.SEARCHFORM.query.value;
		var reQuery = document.SEARCHFORM.reQuery.value;
		var url = "/search/PreviewDoc?coll=" + coll + "&query=" + query + "&reQuery=" + reQuery + "&key=" + conId;
		window.open(url,'','resizable=yes,scrollbars=yes,width=460,height=509');
	}
	
	
	function collection_search(collection){  				
		var fm = document.SEARCHFORM;

		fm.action = get_collection_url(collection);			

		if(collection == "TOT_CAFE" || collection == "KNOWLEDGE" || collection == "CONTENT" || collection == "EDUCATION")
			fm.disNum.value = 3;		        	        
		else if(collection == "PORTAL")
			fm.disNum.value = 3;		        	        
		else
			fm.disNum.value = 10;		        	        
		
		fm.logYN.value = "no";		        	     		        	        
		fm.page.value = 0;		        	        
		fm.coll.value = collection;		        	        		
		fm.sortType.value = "SIM";		        	        
		fm.power.value = "base";		        	        
       	fm.submit();
       	return;
	}

	/* 통합 검색에서 정렬이나, 더많은 검색결과 보기 클릭시 화면 이동 */
	function more_search_view(coll, sub_coll, sort){	
	
		document.SEARCHFORM.action = get_action_url(coll, sub_coll);
		
		document.SEARCHFORM.coll.value = coll;		        	        				
		document.SEARCHFORM.subColl.value = sub_coll;		        		
		document.SEARCHFORM.page.value = 0;		
		document.SEARCHFORM.sortType.value = sort;	
		
		if(coll == "CONTENT")
			document.SEARCHFORM.disNum.value = "10";
		else if(coll == "KNOWLEDGE")
			document.SEARCHFORM.disNum.value = "10";
		else if(coll == "TOT_CAFE"){
			if(sub_coll == "CAFE_ALBUM") document.SEARCHFORM.disNum.value = "10";
			else						document.SEARCHFORM.disNum.value = "10";	
		}else if(coll == "EDUCATION")
			document.SEARCHFORM.disNum.value = "10";
		else			        	        
			document.SEARCHFORM.disNum.value = "10";

		document.SEARCHFORM.power.value = "base";		        	        
		document.SEARCHFORM.logYN.value = "no";
        document.SEARCHFORM.submit();
		return;
	}
		
	function page_view(page_no){       
	        document.SEARCHFORM.page.value = page_no;
	        document.SEARCHFORM.logYN.value = "no";	        	
        	document.SEARCHFORM.submit();
        	return;
	}
	
	function sub_more_search_view(coll, sub_coll, sort){  
		document.SEARCHFORM.action = get_action_url(coll, sub_coll);
	    document.SEARCHFORM.coll.value = coll;	        
	    document.SEARCHFORM.disNum.value = 10;	
			        
	    if(coll == "CONTENT"){
	     	if(sub_coll == "KBANK")			document.SEARCHFORM.subColl[1].checked = true;		        	      
			else if(sub_coll == "BRIEF")	document.SEARCHFORM.subColl[2].checked = true;		
			else if(sub_coll == "NEWS")		document.SEARCHFORM.subColl[3].checked = true;	
			else if(sub_coll == "SEMINAR")	document.SEARCHFORM.subColl[4].checked = true;							
			else if(sub_coll == "ELEARNING")document.SEARCHFORM.subColl[5].checked = true;	
			else {
				document.SEARCHFORM.disNum.value = 3;		
				document.SEARCHFORM.subColl[0].checked = true;		        	        						
			}	
	    }else if(coll == "TOT_CAFE"){
			if(sub_coll == "CAFE_NAME")		document.SEARCHFORM.subColl[1].checked = true;				
			else if(sub_coll == "CAFE")		document.SEARCHFORM.subColl[2].checked = true;	
			else if(sub_coll == "CAFE_ALBUM")document.SEARCHFORM.subColl[3].checked = true;	
			else							document.SEARCHFORM.disNum.value = 3;		
		}else if(coll == "KNOWLEDGE"){
			if(sub_coll == "QNA")			document.SEARCHFORM.subColl[1].checked = true;				
			else if(sub_coll == "KNOWHOW")	document.SEARCHFORM.subColl[2].checked = true;						
			else							document.SEARCHFORM.disNum.value = 3;		
		}else if(coll == "EDUCATION"){
			if(sub_coll == "LEARN")			document.SEARCHFORM.subColl[1].checked = true;		        	      
			else if(sub_coll == "QNA")		document.SEARCHFORM.subColl[2].checked = true;		
			else if(sub_coll == "TORON")	document.SEARCHFORM.subColl[3].checked = true;	
			else if(sub_coll == "DIC")		document.SEARCHFORM.subColl[4].checked = true;							
			else if(sub_coll == "ADMIN_PDS")document.SEARCHFORM.subColl[5].checked = true;	
			else {
				document.SEARCHFORM.disNum.value = 3;		
				document.SEARCHFORM.subColl[0].checked = true;		        	        						
			}	
		}

	    document.SEARCHFORM.page.value = 0;			    
	    document.SEARCHFORM.logYN.value = "no";		
	    document.SEARCHFORM.sortType.value = sort;		
        document.SEARCHFORM.submit();
        return;
	}
		
	function searchCate(cate_id){       
	        document.SEARCHFORM.cateId.value = cate_id;		        
	        document.SEARCHFORM.power.value = "condition";	
	        document.SEARCHFORM.logYN.value = "no";	        
        	document.SEARCHFORM.submit();
        	return;
	}
	
	function searchReQuery(){  	
		document.SEARCHFORM.page.value = 0;		        	        
		document.SEARCHFORM.power.value = "base";
		document.SEARCHFORM.logYN.value = "yes";		        	     		        	        
        document.SEARCHFORM.submit();
        return;
	}
	
	function searchQuery(){ 

		if(document.SEARCHFORM.query.value == "" || document.SEARCHFORM.query.value == " "){
			alert("검색어를 입력해 주세요.");
			return;

		}else{

			var coll = document.SEARCHFORM.coll.value;	

			document.SEARCHFORM.action = get_collection_url(coll);			
		
			if(coll == "CONTENT" || coll == "TOT_CAFE" || coll == "KNOWLEDGE" || coll == "EDUCATION" || coll == "PORTAL"){	
				document.SEARCHFORM.disNum.value = "3";		        	        		
			}
	        	        
			document.SEARCHFORM.logYN.value = "yes";		        	        		
			document.SEARCHFORM.reQuery.value = "";		        	        		
			document.SEARCHFORM.page.value = 0;	
			document.SEARCHFORM.power.value = "base";	
       		document.SEARCHFORM.submit();
       		return;
		}
	}

	function searchReport(){      	        
		document.SEARCHFORM.logYN.value = "yes";		        	        		
		document.SEARCHFORM.reQuery.value = "";		        	        		
		document.SEARCHFORM.page.value = 0;	
		document.SEARCHFORM.power.value = "base";	
       	document.SEARCHFORM.submit();
       	return;
	}
		
	function get_action_url(coll, sub_coll){
		var actionUrl="";
				
		if(coll == "PORTAL"){
										actionUrl = "/search/PortalSearch";
		}
		else if(coll == "CONTENT"){				
			if(sub_coll != "")			actionUrl = "/search/ContentCollectionSearch";			
			else						actionUrl = "/search/ContentSearch";			
		}
		else if(coll == "TOT_CAFE"){
			if(sub_coll == "CAFE_NAME" || (document.SEARCHFORM.subColl[1] && document.SEARCHFORM.subColl[1].checked))	
										actionUrl = "/search/CafeNameSearch";
			else if(sub_coll == "CAFE"  || (document.SEARCHFORM.subColl[2] && document.SEARCHFORM.subColl[2].checked))	
										actionUrl = "/search/CafeSearch";	
			else if(sub_coll == "CAFE_ALBUM"  || (document.SEARCHFORM.subColl[3] && document.SEARCHFORM.subColl[3].checked))	
										actionUrl = "/search/CafeAlbumSearch";	
			else 						actionUrl = "/search/TotCafeSearch";			
		}
		else if(coll == "KNOWLEDGE"){
			if(sub_coll != "")			actionUrl = "/search/KnowledgeCollectionSearch";			
			else						actionUrl = "/search/KnowledgeSearch";				
		}
		else if(coll == "EDUCATION") {
			if(sub_coll != "")			actionUrl = "/search/EducationCollectionSearch";			
			else						actionUrl = "/search/EducationSearch";	
		}	
		
		return actionUrl;		
	}
	
	function get_collection_url(coll){
		var actionUrl = "";
		
		if(coll == "PORTAL")			actionUrl = "/search/PortalSearch";
		else if(coll == "CONTENT")		actionUrl = "/search/ContentSearch";
		else if(coll == "TOT_CAFE")		actionUrl = "/search/TotCafeSearch";						
		else if(coll == "EDUCATION")	actionUrl = "/search/EducationSearch";
		else if(coll == "KNOWLEDGE")	actionUrl = "/search/KnowledgeSearch";
		
		return actionUrl;		
	}
	
			
	// --------------------------------------------------------------------
	//오늘 날짜를 기준으로 며칠전 날짜 객체 구하기
	// day - 숫자( 7일전, 15일 전 등)
	// --------------------------------------------------------------------
	function getDate(day) {
		var oneDay = 86400000;
		var toDay	= new Date();
		
		var before = new Date();
		before.setTime(toDay.getTime() - (day*oneDay));
		return before;
	}

	// --------------------------------------------------------------------
	// 달력을 선택하지 않은 상태에서 처음/ 7일/15일 등의 이벤트 발생시
	// 상세 검색 창의 날짜 INPUT 값 설정
	// --------------------------------------------------------------------
	function setFormDate(day) {		
		if(day >= 0){
			var before = getDate(day);
			var now	   =  new Date();
					
			var formObj = SEARCHFORM;
			formObj.DETAIL_START_DAY.value = before.getYear()+"-"+padZeros(before.getMonth()+1,2)+"-"+padZeros(before.getDate(),2);
			formObj.DETAIL_END_DAY.value = now.getYear()+"-"+padZeros(now.getMonth()+1,2)+"-"+padZeros(now.getDate(),2);
			
			document.SEARCHFORM.startDay.value = getStringDate(formObj.DETAIL_START_DAY.value,"-");
			document.SEARCHFORM.endDay.value = getStringDate(formObj.DETAIL_END_DAY.value,"-");
		}else{
			var formObj = SEARCHFORM;			
			formObj.DETAIL_START_DAY.value = "";
			formObj.DETAIL_END_DAY.value = "";
			
			document.SEARCHFORM.startDay.value = "";
			document.SEARCHFORM.endDay.value = "";
		}
	}
	// --------------------------------------------------------------------
	// 숫자를 "0"으로 패팅처리(1 -> 01)
	// --------------------------------------------------------------------	
	function padZeros(num, size) {
		var str = num.toString();
		var numZeros = size - str.length;
		for (var i=0; i<numZeros; i++) str="0"+str;
		return str;
	}
	
	// --------------------------------------------------------------------
	// 날짜 문자열을 구분자를 제거하여 반환
	// 2003-01-02 -> 20030102
	// --------------------------------------------------------------------
	function getStringDate(str,delim) {
		var retStr =""
		var arr = str.split(delim);
		
		for(var i=0; i <arr.length; i++) {
			retStr = retStr + arr[i];
		}
		return retStr;
	}
		
	function viewContent(menu, id, cateId, path){
		var jspName = "";
	
		if(menu == 'KBANK'){
			jspName = path1 + "/portal/user/gold/hrdknowledge_view.jsp?DOC_ID="+id;
			window.open(jspName);		
		}else if(menu == 'BRIEF'){
			jspName = path1 + "/portal/user/gold/hrdbrief_view.jsp?DOC_ID="+id;
			window.open(jspName);		
		}else if(menu == 'NEWS'){
			jspName = path1 + "/portal/user/gold/hrdnews_view.jsp?DOC_ID="+id;
			window.open(jspName);		
		}
	}

	function viewBulletin(menu, id, cateId, path, seq){
		var jspName = "";
	
		if(menu == 'SEMINAR'){
			jspName = path1 + "/portal/user/gold/seminar_view.jsp?CONTENTID="+id+"&LINE_NUM="+seq;
			window.open(jspName);		
		}else if(menu == 'ELEARNIN'){
			jspName = path1 + "/portal/user/gold/e-learning_view.jsp?CONTENTID="+id+"&LINE_NUM="+seq;
			window.open(jspName);		
		}
	}
	
	
	function viewList(menu, id, cateId, path){
		var jspName = "";
	
		if(menu == 'KBANK'){
			jspName = path1 + "/portal/user/gold/hrdknowledge.jsp?cateId="+cateId;
			window.open(jspName);		
		}else if(menu == 'BRIEF'){
			jspName =path1 +  "/portal/user/gold/hrdbrief.jsp?cateId="+cateId;
			window.open(jspName);		
		}else if(menu == 'NEWS'){
			jspName = path1 + "/portal/user/gold/hrdnews.jsp?cateId="+cateId;
			window.open(jspName);		
		}else if(menu == 'SEMINAR'){
			jspName = path1 + "/portal/user/gold/seminar_general.jsp?cateId="+cateId;
			window.open(jspName);		
		}else if(menu == 'ELEARNIN'){
			jspName = path1 + "/portal/user/gold/e-learning.jsp?cateId="+cateId;
			window.open(jspName);		
		}
	}

	function viewQNA(subj, year, subjseq, lesson, seq){
		var jspName = "";
		jspName = path1 + "/servlet/controller.study.SubjQnaStudyServlet?p_process=SubjQnaDetail&p_subj="+subj+"&p_year="+year+"&p_subjseq="+subjseq+"&p_lesson="+lesson+"&p_seq="+seq+"&p_kind=0";

		window.open(jspName);			
	}

	function viewTORON(subj, year, subjseq, tpcode, seq){
		var jspName = "";
		jspName = path1 + "/servlet/controller.study.ToronServlet?p_process=ToronSelect&p_subj="+subj+"&p_year="+year+"&p_subjseq="+subjseq+"&p_tpcode="+tpcode+"&p_seq="+seq;

		window.open(jspName);			
	}

	function viewADMIN_PDS(subj, year, subjseq, lesson, seq){
		var jspName = "";
		jspName = path1 + "/servlet/controller.study.StudyDataServlet?p_process=select&p_subj="+subj+"&p_year="+year+"&p_subjseq="+subjseq+"&p_lesson="+lesson+"&p_seq="+seq+"&p_tabseq=11";

		window.open(jspName);			
	}

	function viewLEARN(contentid){
		var jspName = "";
		jspName = path1 + "/learn/admin/contents/scorm/contents.jsp?oid="+contentid;

		window.open(jspName);			
	}

	function viewKM(id){
		var jspName = "";
		jspName = path1 + "/portal/user/knowledge/km_view.jsp?qnaId="+id;

		window.open(jspName);			
	}

	function viewKNOWHOW(id){
		var jspName = "";
		jspName = path1 + "/portal/user/knowhow/knowhow_view.jsp?qnaId="+id;

		window.open(jspName);			
	}

	function viewCAFE(clubid, boardno, seq, writeno){
		var jspName = "";

		jspName = path2 +  "/community/home/bbs/BbsContent.dsc?ClubID="+clubid+"&boardno="+boardno+"&seq="+seq+"&writeno="+writeno;

		window.open(jspName);			
	}

	function viewALBUM(clubid, albumid, albumno, seq){
		var jspName = "";
		jspName = path2 + "/community/club/club/menu/AlbumCont.dsc?ClubID="+clubid+"&AlbumID="+albumid+"&AlbumNo="+albumno+"&seq="+seq+"&boardno="+albumid;

		window.open(jspName);			
	}

	function viewREPORT(){
		var jspName = "";
		jspName = "";

		window.open(jspName);			
	}

	function viewFile(filepath, filename, year, subj){
		var jspName = "";
		location.href = path1 + "/servlet/controller.library.DownloadServlet?p_savefile="+filepath+"&p_realfile="+filename+"&p_year="+year+"&p_subj="+subj;

		//window.open(jspName);			
	}

	function viewUser(userId) {
		var url = path1 + "/portal/include/user_view.jsp?USER_ID=" + userId;
						
		MM_openBrWindow(url,'USER_INFO','status=yes, resizable=no,scrollbars=no,width=360,height=289');
	}

	function writeKNOWLEDGE() {
		location.href = path1 + "/portal/user/knowledge/km_write.jsp??cateId=&title=" + document.SEARCHFORM.query.value;
	}

	function viewDIC(subj, seq) {
		
		var url = path1 + "/servlet/controller.study.DicSubjStudyServlet?p_process=wordPopUp&p_subj="+ subj +"&p_seq="+ seq +"&p_gubun=1";
		MM_openBrWindow(url,'DIC','scrollbars=yes,width=470,height=460,scrollbars=no,resizable=no');
	}

	function checkLogin() {
		
		var search = "userid=";
		var cookie_value;

		if (document.cookie.length > 0) { 
			offset = document.cookie.indexOf(search);
			if (offset != -1) {
				offset += search.length;
				end = document.cookie.indexOf(";", offset);
				if (end == -1) end = document.cookie.length;			
			}else{
				alert("로그인 후 이용해 주세요.");
				location.href = "/";
			}
		} else{
			alert("로그인 후 이용해 주세요.");
			location.href = "/";
		}
	}

	
	// 카페로 이동
	function goCafe(url) {
		window.open("http://cafe.hkhrd.com/" +url);
	}
	
	//카페 검색 대분류
	function goCafeSearch(FCODE) {
		window.open(path2+ "/community/home/clubhome/ClubSearch.dsh?FCODE"+FCODE);
	}

	// 카페 검색 대분류, 소분류
	function goCafeSearch(FCODE, SCODE) {
		window.open(path2 + "/community/home/clubhome/ClubSearch.dsh?FCODE"+FCODE+"&SCODE="+SCODE);
	}

	// 카페 게시판 으로 이동
	function goCafeBulletin(CID, BID, SEQ) {
		window.open(path2 + "/community/home/bbs/BbsList.dsc?ClubID=" + CID +"&boardno="+BID + "&seq=" + SEQ);
	}

	// 질문광장 이동
	function goKMCategory(CATE_ID, TYPE) {
		window.open(path1 + "/portal/user/knowledge/km_list.jsp?cateId="+ CATE_ID +"&type=" + TYPE);
	}
	
	// 노하우 이동
	function goKnowhow(CATE_ID) {
		window.open(path1 + "/portal/user/knowhow/knowhow_list.jsp?cateId="+ CATE_ID );
	}
