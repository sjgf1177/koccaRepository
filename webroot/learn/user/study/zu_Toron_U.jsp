<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: TORON UPDATE
//  2. 프로그램명: zu_Toron_U.jsp
//  3. 개      요: 토론방 의견수정
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 9. 03
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
     //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_user_id   = box.getSession("userid");
    String  v_user_name = box.getSession("name");
    String  v_process   = box.getString("p_process");
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");              
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_tpcode    = box.getString("p_tpcode");   
    String  v_title     = "";      
    String  v_adcontent = "";      
    String  v_userid    = "";      
    String  v_addate    = "";       
    String  v_name      = ""; 
    int     v_seq       = box.getInt("p_seq");
    int     v_cnt       =  0;      
    ToronData data      = null;    
	//DEFINED class&variable END         	   

    // editor
    String content      = "";
    String width        = "600";
    String height       = "300";	      
    
            
	data = (ToronData)request.getAttribute("toronSelect");     
	v_seq       = data.getSeq();
	v_title     = data.getTitle();         
	v_adcontent = data.getAdcontent();
	v_userid    = data.getAduserid();
	v_addate    = data.getAddate();  	
	v_name      = data.getName();  		
	v_cnt       = data.getCnt();  		
	v_addate    = FormatDate.getFormatDate(v_addate,"yyyy.MM.dd");   
    //content     = StringManager.replace(v_adcontent,"\n;","<br>");   	

	SubjectBean		subjbean = new SubjectBean();
	SubjectData		subjdata = new SubjectData();
					subjdata = subjbean.SelectSubjectData(box);
	
	EduStartBean	bean		= new EduStartBean();
	EduScoreData    scoredata	= new EduScoreData();
					scoredata	= bean.SelectEduScore(box);

%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>토론방</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script type="text/javascript" src="/namo_cross/js/namo_scripteditor.js"></script> 
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/user_patch.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script	language="JavaScript" type="text/JavaScript">
<!--	   
function whenToronUpdate() {    
    if (blankCheck(document.form1.p_title.value)) {
        alert("제목을 입력하세요!");
        document.form1.p_title.focus();
        return;
    }

    if (realsize(document.form1.p_title.value) > 100) {
        alert("제목은 한글기준 100자를 초과하지 못합니다.");
        document.form1.p_title.focus();
        return;
    }

    //if (blankCheck(document.form1.p_adcontent.value)) {
    //    alert("내용을 입력하세요!");
    //    document.form1.p_adcontent.focus();
    //    return;
    //}
    
    document.getElementById("p_adcontent").value = CrossEditor.GetBodyValue();
    document.form1.target = "_self";

    document.form1.action = "/servlet/controller.study.ToronServlet";
    document.form1.p_process.value = "ToronUpdate";
    document.form1.submit();
}    

function cancel() {
	history.back(-1);
}
-->			
 </script>        
</head>
<body id="pop_study"><!-- popup size : 890* -->
<form name = "form1" method = "post">
    <input type="hidden" name="p_aduserid" value="<%=v_user_id%>"/>
    <input type="hidden" name="p_process"/>
    <input type="hidden" name="p_subj" value="<%=v_subj%>"/>
    <input type="hidden" name="p_year" value="<%=v_year%>"/>
    <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>"/>       
    <input type="hidden" name="p_tpcode" value="<%=v_tpcode%>"/>
    <input type="hidden" name="p_seq" value="<%=v_seq%>"/>

	<div id="pop_header">
		<h1><img src="/images/portal/myclass/study/pop_h1_tit11.gif" alt="토론방" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap">
			<div id="con_scoll">
			
<%	if (scoredata != null) { %>
				<div id="btn_close"><a href="javascript:top.window.close()"><img src="/images/portal/btn/btn_close.gif" alt="닫기" /></a></div>
				<div class="study_tltle study_tltle_sub"><div class="study_tltle_left"><div class="study_tltle_right">
					<dl class="class_info"> 
						<dt><span>과정명</span></dt>
						<dd><%= subjdata.getSubjnm() %></dd>
					</dl>
					<dl class="class_info">
						<dt><span>교육기간</span></dt>
						<dd class="class_day"><%= FormatDate.getFormatDate(scoredata.getEdustart(),"yyyy.MM.dd") %> ~<%= FormatDate.getFormatDate(scoredata.getEduend(),"yyyy.MM.dd") %></dd>
					</dl>
				</div></div></div>
<%	} %>
				
				<div class="content">
					<h3><img src="/images/portal/myclass/study/pop_stit16.gif" alt="의견작성" /></h3>
					<table class="study_write">
					<!--[if ie]><colgroup><col width="60px" /><col width="125px" /><col width="50px" /><col width="570px" /></colgroup><![endif]-->
					<colgroup><col width="60px" /><col width="125px" /><col width="70px" /><col width="570px" /></colgroup>
					<tr class="bo">
						<th class="th">참여자</th>
						<td class="td txt"><%= v_name %></td>
						<th class="th">작성일</th>
						<td class="td stxt ff_t"><%= v_addate %></td>
					</tr>
					<tr>
						<th class="th">제목</th>
						<td class="td" colspan="3"><input type="text" class="inbox" style="width:590px;" name="p_title" value="<%= StringManager.convertHtmlchars(v_title) %>" /></td>
					</tr>
					<tr>
						<td colspan="4" class="td con">
		                    <textarea id="p_adcontent" name="p_adcontent" style="display:none;"><%=StringManager.replace(v_adcontent, "&", "&amp") %></textarea>
							<script type="text/javascript">
							    var CrossEditor = new NamoSE("contents");
							    var contentValue = document.getElementById("p_adcontent").value; // Hidden 값 참조
							    CrossEditor.editorStart();
							    CrossEditor.SetUISize("820","500");
							    CrossEditor.SetBodyValue(contentValue); // 콘텐츠 내용 에디터 삽입
							</script>
						</td>
					</tr>
					</table>

					<p class="list_btn"><a href="javascript:whenToronUpdate()" class="btn_gr"><span>의견수정</span></a><a href="javascript:cancel()" class="btn_gr"><span>취소</span></a></p>
					
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>