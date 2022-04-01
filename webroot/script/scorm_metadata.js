var frm;
var Basic_Path			= "/Manager/Course/CoObject/";
var ListPage_Name		= "aco_Object_List.jsp";

function Search_OK(){		// 검색하기 (고정)
	frm = document.frm;
	if(frm.search_item.value==""){
		alert("검색조건을 입력하세요.");
		return false;		
	}
	if(IsSpace(frm.search_value)){
		alert("검색어를 입력하세요.");
		return false;
	}
	frm.page.value= 1;
	frm.target = "";
	frm.action = Basic_Path + ListPage_Name
	frm.submit();
}
function All_List(){	// 전체목록(검색해제) -- (고정)
	frm = document.frm;
	frm.search_item.value	= "";
	frm.search_value.value 	= "";
	frm.page.value		= 1;
	frm.target = "";
	frm.action = Basic_Path + ListPage_Name;
	frm.submit();
}
function Move_Page(page){	 // 페이지 이동 (고정)
	frm = document.frm;
	frm.page.value = page;
	frm.target = "";
	frm.action = Basic_Path + ListPage_Name;
	frm.submit();
}
function Go_List(){	 // 목록페이지로 이동 (고정)
	frm = document.frm;
	frm.target = "";
	frm.action = Basic_Path + ListPage_Name;
	frm.submit();
}
function Change_Size(){	// 페이지 사이즈 변경 (셀렉트 박스 사용시 Onchange 이벤트에서 호출)
	frm = document.frm;
	frm.target = "";
	frm.action =  Basic_Path + ListPage_Name;
	frm.submit();
}

function Change_CBunryu(){
	frm = document.frm;
	frm.COURSE_CODE.value = "";
	frm.OBJECT_ID.value = "0";
	frm.target = "";
	frm.action = Basic_Path + ListPage_Name;
	frm.submit();
}
function Change_Course(){
	frm = document.frm;
	frm.OBJECT_ID.value = "0";
	frm.target = "";
	frm.action = Basic_Path + ListPage_Name;
	frm.submit();
}

function Object_Add(){
	frm = document.frm;

	if(frm.BUNRYU_CODE.value == "" || frm.BUNRYU_CODE.value == "0"){
		alert("카테고리를 먼저 선택하세요.");
		return false;
	}
	if(frm.COURSE_CODE.value == "" || frm.COURSE_CODE.value == "0"){
		alert("과목을 먼저 선택하세요.");
		return false;
	}
	frm.mode.value = "Insert";
	frm.OBJECT_ID.value = "0";
	frm.target = "";
	frm.action = Basic_Path + "aco_Object_Reg.jsp";
	frm.submit();
}

function Object_Edit(flag, type, code){
	var action_url = "";
	if(flag == "1"){
		if(type == "1"){			
			action_url = "aco_Object_OType1_Mod.jsp";
		}else{
			action_url = "aco_Object_OType23_Mod.jsp";
		}
	}else{
		if(type == "1"){		
			action_url = "aco_Object_OType1_Mod.jsp";
		}else{
			action_url = "aco_Object_OType23_Mod.jsp";
		}
	}
	frm = document.frm;
	frm.mode.value = "Modify";
	frm.OBJECT_ID.value = code;
	frm.target = "";
	frm.action = Basic_Path + action_url;
	frm.submit();
}

function Del_Object(code){	
	if(confirm("학습자료를 삭제하시겠습니까?")){
		frm = document.frm;
		frm.mode.value = "Delete";
		frm.OBJECT_ID.value = code;
		frm.target = "";
		frm.action = Basic_Path + "aco_Object_DelExe.jsp";
		frm.submit();
	}else{
		return;
	}
}

function SuppleData_Manage(code){
	frm = document.frm;
	frm.mode.value = "View";
	frm.OBJECT_ID.value = code;
	frm.target = "";
	frm.action = Basic_Path + "aco_Object_SuppleData_View.jsp";
	frm.submit();
}

function SuppleData_AddPop(code){
	window.open("", "SUPPLEDATA", "width=300,height=300,scrollbars=no,toolbars=no");

	frm = document.frm;
	frm.mode.value = "Insert";
	frm.SUPPLE_CODE.value = code;
	frm.SUPPLE_NO.value = "0";
	frm.target = "SUPPLEDATA";
	frm.action = Basic_Path + "aco_Object_SuppleData_Reg.jsp";
	frm.submit();
}

function SuppleData_EditPop(code, no){
	window.open("", "SUPPLEDATA", "width=300,height=300,scrollbars=no,toolbars=no");

	frm = document.frm;
	frm.mode.value = "Modify";
	frm.SUPPLE_CODE.value = code;
	frm.SUPPLE_NO.value = no;
	frm.target = "SUPPLEDATA";
	frm.action = Basic_Path + "aco_Object_SuppleData_Reg.jsp";
	frm.submit();
}

function SuppleData_Del(code, no){
	if(confirm("학습자료를 삭제하시겠습니까?")){
		frm = document.frm;
		frm.mode.value = "Delete";
		frm.SUPPLE_CODE.value = code;
		frm.SUPPLE_NO.value = no;
		frm.target = "";
		frm.action = Basic_Path + "aco_Object_SuppleData_DelExe.jsp";
		frm.submit();
	}
	return;
}

function Save_SuppleData(){
	frm = document.frm;
	var msg	= "";
	if(IsSpace(frm.SUPPLE_DATANAME)){
		alert("학습자료명을 입력하세요.");
		return;
	}else if(CharacterStr_CHK(frm.SUPPLE_DATANAME,"\"'")){
		alert("학습자료명에 따옴표를 사용하실 수 없습니다.");
		return false;
	}
	if(frm.mode.value == "Modify" && frm.old_filename.value != "" && frm.old_fileopt[2].checked == true){
		msg	= "파일을 삭제하시면 보조학습자료 정보 자체가 함께 삭제됩니다.\n\n삭제하시겠습니까?";
	}else{
		msg	= "저장하시겠습니까?";
	}
	if(confirm(msg)){
		frm.target = "";
		frm.encoding = "multipart/form-data";
		frm.action = Basic_Path + "aco_Object_SuppleData_RegExe.jsp";
		frm.submit();
	}
	return;
}

function Meta_Add(oid, metalocation, scolocate){
	URL="/servlet/controller.contents.MetaDataServlet?p_process=insertSave&p_oid="+oid+"&p_metalocation="+metalocation+"&p_scolocate="+scolocate;
	window.open(URL,"","toolbar=no,menubar=no,scrollbars=yes,resizable=yes,width=950,height=600,left=0,top=0");
}

function Meta_Edit(bunryu_code, course_code, object_id){

	url = "/Manager/Course/MetaGenerator/ModifyFrame.jsp?bunryu_code="+bunryu_code+"&course_code="+course_code+"&object_id="+object_id;
	window.open(url,"","toolbar=no,menubar=no,scrollbars=yes,resizable=yes,width=950,height=600,left=0,top=0");
}

function Meta_View(bunryu_code, course_code, object_id){

	url = "/Manager/Course/MetaGenerator/MetadataView.jsp?bunryu_code="+bunryu_code+"&course_code="+course_code+"&object_id="+object_id;
	window.open(url,"","toolbar=no,menubar=no,scrollbars=yes,resizable=yes,width=950,height=600,left=0,top=0");
}

function Result_Message(msg, action_type){	// Background(등록/수정/삭제)에서 처리 결과 메세지 출력후 Hidden과 함께 목록 페이지로 이동
	alert(msg);
	if(action_type == "Fail"){
		history.back();
	}else{
		frm = document.frm;
		frm.target = "";
		frm.action = Basic_Path + ListPage_Name;
		frm.submit();
	}
}
function Result_Message2(msg, action_type, page_name){	// Background(등록/수정/삭제)에서 처리 결과 메세지 출력후 Hidden과 함께 목록 페이지로 이동
	alert(msg);
	if(action_type == "Fail"){
		history.back();
	}else{
		frm = opener.document.frm;
		frm.target = "";
		frm.action = Basic_Path + page_name;
		frm.submit();
		self.close();
	}
}
function Result_Message3(msg, action_type, page_name){	// Background(등록/수정/삭제)에서 처리 결과 메세지 출력후 Hidden과 함께 목록 페이지로 이동
	alert(msg);
	if(action_type == "Fail"){
		history.back();
	}else{
		frm = document.frm;
		frm.target = "";
		frm.action = Basic_Path + page_name;
		frm.submit();
	}
}