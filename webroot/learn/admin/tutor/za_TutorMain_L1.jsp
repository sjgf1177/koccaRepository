<%
//**********************************************************
//  1. 제      목: 과정등록 화면
//  2. 프로그램명: za_TutorMain_L.jsp
//  3. 개      요: 과정등록 화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%

    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
//    int v_pageno        = box.getInt("p_pageno");  
	String  v_grcodenm  =  "";
    String  v_grseq     =  "";
    String  v_subj      =  "";
    String  v_year      =  "";
    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_propstart =  "";
    String  v_propend   =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_isclosed	=  "";
	String  v_grayntxt  =  "";
	int		v_grayncnt = 0;
	int     i           =  0;
	int     j           =  0;
    int     v_quecnt    =  0;
    int     v_noanscnt  =  0;
    int     v_reportcnt    =  0;
    int     v_noscorecnt  =  0;



    ArrayList list      = null;
    ArrayList list2     = null;

    //DEFINED class&variable END


    String  ss_edustart  = box.getString("p_edustart");            //교육시작일
    String  ss_eduend    = box.getString("p_eduend");              //교육종료일    
    
    String  ss_action    = box.getString("p_action");

    if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("qalist");
        list2 = (ArrayList)request.getAttribute("reportlist");
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript">
$(document).ready(function(){
	$("#p_edustart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_eduend").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
//-->
function whenSelection(p_action) {

  if (p_action == 'go') {
//    if (chkParam() == false) {
  //    return;
    //}
//    top.ftop.setPam();
  }
  document.form1.action='/servlet/controller.tutor.TutorAdminMainServlet';
  document.form1.target = "_self";
  document.form1.p_process.value = 'listPage';
  document.form1.p_action.value = p_action;
  document.form1.submit();
}

function projectlist_select(subj,subjnm,year,subjseq) {
	document.form1.action='/servlet/controller.study.ProjectAdminServlet';
	document.form1.p_process.value = 'ProjectSubmitList';
	document.form1.p_subj.value = subj;        
	document.form1.p_subjnm.value = subjnm;        
	document.form1.p_year.value = year;     
	document.form1.p_subjseq.value = subjseq;  
	document.form1.submit();
}
    
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
   <!----------------- title 시작 ----------------->
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td class=sub_title><img src="/images/admin/common/icon.gif"> 강사메인
          </td>
        </tr>
      </table>
      <!----------------- title 끝 --------------- -->
        <br>

<!------------------------------------- form 시작 ------------------------------------->
<form name = "form1" method = "post" action="/servlet/controller.tutor.TutorAdminMainServlet">
    <input type="hidden" name="p_subj">
    <input type="hidden" name="p_subjnm">
    <input type="hidden" name="p_year">
    <input type="hidden" name="p_subjseq">
    <input type="hidden" name="p_process" value="">
    <input type="hidden" name="p_action"  value="">

        <table class="form_table_out" cellspacing="0" cellpadding="1">
          <tr>
            <td bgcolor="#C6C6C6" align="center">
              <table class="form_table_bg" cellspacing="0" cellpadding="0" border='0'>
                <tr>
                  <td height="7"></td>
                </tr>
                <tr>
                  <td align="center">
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
				  <tr>
					  <td width="45%" colspan="2">학습기간
						<input name="p_edustart" id="p_edustart" type="text" class="datepicker_input1" size="10" value="<%=ss_edustart%>"> 일 ~ 
						<input name="p_eduend" id="p_eduend" type="text" class="datepicker_input1" size="10" value="<%=ss_eduend%>"> 일
					  </td>	
            	      <td width="10%" align="right"><a href="javascript:whenSelection('go')"><img src="/images/admin/button/b_go.gif"  border="0" align=absmiddle></a></td>
					</tr>
                  </table>
                  </td>
                </tr>
                <tr>
                  <td height="7"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <br>
<!------------------------------------- form 끝 ------------------------------------->

        <!----------------- 세부타이틀 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
		    <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>Q & A</td>
            <td align="right" height="20"></td>
          </tr>
          <tr>
            <td height="3"></td>
			<td height="3"></td>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 세부타이틀 끝 ----------------->

        <!----------------- 조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="11" class="table_top_line"></td>
          </tr>

	      <tr>
            <td class="table_title" width="5%">No</td>
            <td class="table_title" width="15%">교육그룹</td>
            <td class="table_title" width="10%">교육년도</td>
            <td class="table_title" width="10%">교육차수</td>ㅋ
            <td class="table_title" width="20%">과정명</td>
            <td class="table_title" width="10%">차수</td>
            <td class="table_title" width="10%">질문수</td>
            <td class="table_title" width="10%">미응답갯수</td>
            <td class="table_title" width="10%">교육상태</td>
          </tr>

<% 
if (ss_action.equals("go")) {    //go button 선택시만 list 출력
			  
	for( i = 0 ; i < list.size() ; i++ ){
		DataBox dbox = (DataBox)list.get(i);		  

		v_grcodenm = dbox.getString("d_grcodenm");  				
		v_subj = dbox.getString("d_subj");  
		v_year = dbox.getString("d_year");
		v_grseq = dbox.getString("d_grseq");
		v_subjseq = dbox.getString("d_subjseq");
		v_subjnm = dbox.getString("d_subjnm");
		v_isclosed  = dbox.getString("d_isclosed");
		v_quecnt  = dbox.getInt("d_quecnt");
		v_noanscnt  = dbox.getInt("d_noanscnt");
		
		if(v_isclosed.equals("Y")){
				v_grayntxt = String.valueOf(v_grayncnt);
		}else{
				v_grayntxt = "교육중";
		}					
%>
          <tr class="table_02_1">
			<td class="table_02_1"><%=i+1%></td>
			<td class="table_02_1"><%=v_grcodenm%></td>
			<td class="table_02_1"><%=v_year%></td>
			<td class="table_02_1"><%=v_grseq%></td>
			<td class="table_02_1"><%=v_subjnm%></td>
			<td class="table_02_1"><%=v_subjseq%></td>
			<td class="table_02_1"><%=v_quecnt%></td>
			<td class="table_02_1"><a href="#"><%=v_noanscnt%></a></td>
			<td class="table_02_1"><%=v_grayntxt%></td>
           </tr>
<%		
	}
		   
}
if(i == 0 && ss_action.equals("go")){ %>
              <tr> 
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="9">등록된 내용이 없습니다</td>
              </tr>
<% }%>
		
        </table>
        
   
        <!----------------- 조회 끝 ----------------->
        <br><br><br><br>
 

        <!----------------- 세부타이틀 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
		    <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>리포트</td>
            <td align="right" height="20"></td>
          </tr>
          <tr>
            <td height="3"></td>
			<td height="3"></td>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 세부타이틀 끝 ----------------->

        <!----------------- 조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="11" class="table_top_line"></td>
          </tr>

	      <tr>
            <td class="table_title" width="5%">No</td>
            <td class="table_title" width="12%">교육그룹</td>
            <td class="table_title" width="10%">교육년도</td>
            <td class="table_title" width="10%">교육차수</td>
            <td class="table_title" width="20%">과정명</td>
            <td class="table_title" width="10%">차수</td>
            <td class="table_title" width="7%">제출개수</td>
            <td class="table_title" width="7%">미채점</td>
            <td class="table_title" width="10%">리포트 조회</td>
            <td class="table_title" width="10%">교육상태</td>
          </tr>

<% 
if (ss_action.equals("go")) {    //go button 선택시만 list 출력
			  
	for( j = 0 ; j < list2.size() ; j++ ){
		DataBox dbox2 = (DataBox)list2.get(j);		  

		v_grcodenm = dbox2.getString("d_grcodenm");  				
		v_subj = dbox2.getString("d_subj");  
		v_year = dbox2.getString("d_year");
		v_grseq = dbox2.getString("d_grseq");
		v_subjseq = dbox2.getString("d_subjseq");
		v_subjnm = dbox2.getString("d_subjnm");
		v_isclosed  = dbox2.getString("d_isclosed");
		v_reportcnt  = dbox2.getInt("d_reportcnt");
		v_noscorecnt  = dbox2.getInt("d_noscorecnt");
		
		if(v_isclosed.equals("Y")){
				v_grayntxt = String.valueOf(v_grayncnt);
		}else{
				v_grayntxt = "교육중";
		}					
%>

          <tr class="table_02_1">
			<td class="table_02_1"><%=j+1%></td>
			<td class="table_02_1"><%=v_grcodenm%></td>
			<td class="table_02_1"><%=v_year%></td>
			<td class="table_02_1"><%=v_grseq%></td>
			<td class="table_02_1"><%=v_subjnm%></td>
			<td class="table_02_1"><%=v_subjseq%></td>
			<td class="table_02_1"><%=v_reportcnt%></td>
			<td class="table_02_1"><%=v_noscorecnt%></td>
            <td class="table_02_1"> <a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>')"><img src="/images/admin/button/b_reportlist.gif" border="0"></a> 
            </td>

			<td class="table_02_1"><%=v_grayntxt%></td>
           </tr>
<%		
	}
		   
}
if(j == 0 && ss_action.equals("go")){ %>
              <tr> 
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="10">등록된 내용이 없습니다</td>
              </tr>
<% }%>

        </table>
        
   
        <!----------------- 조회 끝 ----------------->
        </form>
        <br>
      </td>
  </tr>
</table>

<table>
    <tr><td>
    <%@ include file = "/learn/library/getJspName.jsp" %>
    </td></tr>
</table>

</body>
</html>
