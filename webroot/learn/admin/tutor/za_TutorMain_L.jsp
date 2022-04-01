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

    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  v_process   = box.getString("p_process");
//    int v_pageno        = box.getInt("p_pageno");  
    String  v_subj      =  "";
    String  v_subjnm    =  "";
    String  v_year	    =  "";
    String  v_subjseq   =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_title		=  "";
	String  v_indate	=  "";
	String	v_inuserid  =  "";
	int     i           =	0;
	int     j           =	0;
	int     k           =	0;
	int     v_seq       =	0;
    int     v_wreport   =	0;
    int     v_totavg	=	0;
    int     v_myavg		=	0;
    int     v_reportcnt =	0;
    int     v_noscorecnt=	0;
	int		v_stucnt	=	0;
	int		v_grayncnt	=	0;
	int		v_price		=	0;
	int		v_addprice	=	0;

//	String  v_title		= "";
	String	v_name		= "";
//	String	v_indate	= "";
	int		v_cnt		= 0;

//    String  ss_edustart  = box.getString("p_edustart");            //교육시작일
//    String  ss_eduend    = box.getString("p_eduend");              //교육종료일    
    
    String  ss_action    = box.getString("p_action");
    //DEFINED class&variable END

    ArrayList list      = null;
    ArrayList list2     = null;
    ArrayList list3     = null;

    if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("qalist");
        list2 = (ArrayList)request.getAttribute("reportlist");
        list3 = (ArrayList)request.getAttribute("noticelist");
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
//-->
function whenSelection(p_action) {

  document.form1.action='/servlet/controller.tutor.TutorAdminMainServlet';
  document.form1.target = "_self";
  document.form1.p_process.value = 'listPage';
  document.form1.p_action.value = p_action;
  document.form1.submit();
}

function projectlist_select(subj,subjnm,year,subjseq) {
	document.form1.action='/servlet/controller.tutor.TutorAdminMainServlet';
    document.form1.target = "_self";
	document.form1.p_process.value = 'ProjectDetailList';
	document.form1.p_subj.value = subj;        
	document.form1.p_subjnm.value = subjnm;        
	document.form1.p_year.value = year;     
	document.form1.p_subjseq.value = subjseq;  
	document.form1.submit();
}

function qna_select(subj,year,subjseq,seq) {    
	document.form1.action='/servlet/controller.tutor.TutorAdminMainServlet';	
	document.form1.target = "_self";        
	document.form1.p_process.value = 'QnaDetail';
	document.form1.p_subj.value = subj;
	document.form1.p_year.value = year;
	document.form1.p_subjseq.value = subjseq;
	document.form1.p_seq.value = seq;
	document.form1.submit();
}

function notice_select(tabseq,seq) {    
	document.form1.action='/servlet/controller.tutor.NoticeAdminServlet';	
	document.form1.target = "_self";        
	document.form1.p_process.value = '';
	document.form1.p_tabseq.value = tabseq;
	document.form1.p_seq.value = seq;
	document.form1.submit();
}
    
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' 
ondragstart='return false' onselectstart='return false' 
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>

<!------------------------------------- form 시작 ------------------------------------->
<form name = "form1" method = "post" action="/servlet/controller.tutor.TutorAdminMainServlet">
		<input type="hidden" name="p_subj" value="">
		<input type="hidden" name="p_subjnm" value="">
		<input type="hidden" name="p_year" value="">
		<input type="hidden" name="p_subjseq" value="">
		<input type="hidden" name="p_seq" value="">
		<input type="hidden" name="p_tabseq" value="">
	    <input type="hidden" name="p_process" value="">
		<input type="hidden" name="p_action" value="">

  <table width="1000"  border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="/images/admin/tutor/tit_new_tutormain.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 --------------- -->
      <br>
		<!------------------------------------- form 시작 ------------------------------------->
		<!--table class="form_table_out" cellspacing="0" cellpadding="1">
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
						<input name="p_edustart" type="text" class="input" size="10" maxlength=8  value="<%//=ss_edustart%>" OnClick="MiniCal(this)" readonly> 일 ~ 
						<input name="p_eduend" type="text" class="input" size="10" maxlength=8  value="<%//=ss_eduend%>" OnClick="MiniCal(this)" readonly> 일
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
            <td  class=sub_title>공지사항</td>
            <td align="right" height="20"><a href="/servlet/controller.tutor.NoticeAdminServlet">more...</a></td>
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
            <td class="table_title">제목</td>
            <td class="table_title" width="10%">작성일</td>
            <td class="table_title" width="10%">작성자</td>
          </tr>
<% 
if (ss_action.equals("go")) {    //go button 선택시만 list 출력
			  
	for( k = 0 ; k < list3.size() ; k++ ){
		DataBox dbox3 = (DataBox)list3.get(k);		  
		
		v_seq		= dbox3.getInt("d_seq");  		
		v_title		= dbox3.getString("d_title");  
		v_name		= dbox3.getString("d_name");
		v_indate	= dbox3.getString("d_indate");
		v_cnt		= dbox3.getInt("d_cnt");
%>

          <tr class="table_02_1">
			<td class="table_02_1"><%=list3.size()-k%></td>
			<td class="table_02_2"><a href="javascript:notice_select('56','<%=v_seq%>')"><%=v_title%></a></td>
			<td class="table_02_1"><%=FormatDate.getFormatDate(v_indate,"yyyy/MM/dd")%></td>
			<td class="table_02_1"><%=v_name%></td>
           </tr>
<%		
	}
		   
}
if(k == 0){ %>
              <tr> 
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="10">등록된 내용이 없습니다.</td>
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
				<td class="table_title" width="3%">No</td>
				<td class="table_title" width="30%">과정명</td>
				<td class="table_title" width="17%">학습기간</td>
				<td class="table_title" width="7%">입과인원</td>
				<td class="table_title" width="7%">제출개수</td>
				<td class="table_title" width="6%">미채점</td>
				<td class="table_title" width="6%">가중치</td>
				<td class="table_title" width="7%">내평균</td>
				<td class="table_title" width="7%">전체평균</td>
				<td class="table_title" width="18%">리포트 조회</td>
			  </tr>
<% 
if (ss_action.equals("go")) {    //go button 선택시만 list 출력
			  
	for( j = 0 ; j < list2.size() ; j++ ){
		DataBox dbox2 = (DataBox)list2.get(j);		  

		v_subj = dbox2.getString("d_subj");  
		v_subjnm = dbox2.getString("d_subjnm");
		v_year = dbox2.getString("d_year");
		v_subjseq = dbox2.getString("d_subjseq");
		v_edustart = dbox2.getString("d_edustart");
		v_eduend = dbox2.getString("d_eduend");
		v_wreport  = dbox2.getInt("d_wreport");
		v_stucnt  = dbox2.getInt("d_stucnt");
		v_totavg  = dbox2.getInt("d_totavg");
		v_myavg  = dbox2.getInt("d_myavg");
		v_reportcnt  = dbox2.getInt("d_reportcnt");
		v_noscorecnt  = dbox2.getInt("d_noscorecnt");

						
%>
			  <tr class="table_02_1">
				<td class="table_02_1"><%=list2.size()-j%></td>
				<td class="table_02_2"><%=v_subjnm%></td>
				<td class="table_02_1"><%=FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")+" ~ "+FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")%></td>
				<td class="table_02_1"><%=v_stucnt%></td>
				<td class="table_02_1"><%=v_reportcnt%></td>
				<td class="table_02_1"><a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>')"><%=v_noscorecnt>0?"<font color=red><b>"+v_noscorecnt+"</b></font>":v_noscorecnt+""%></a></td>
				<td class="table_02_1"><%=v_wreport%> %</td>
				<td class="table_02_1"><%=v_myavg%> 점</td>
				<td class="table_02_1"><%=v_totavg%> 점</td>
				<td class="table_02_1"> <a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>')"><img src="/images/admin/button/b_reportlist.gif" border="0"></a> 
				</td>
			   </tr>
<%		
	}
		   
}
if(j == 0){ %>
              <tr> 
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="10">등록된 내용이 없습니다.</td>
              </tr>
<% }%>

			</table>
			<!----------------- 조회 끝 ----------------->
			<br><br><br><br>

			<!----------------- 세부타이틀 시작 ----------------->
			<table width="97%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="1%"><img src="/images/admin/common/icon.gif" ></td>
				<td  class=sub_title>Q & A</td>
				<td align="right" height="20"><!--a href="/servlet/controller.tutor.TutorAdminMainServlet?p_process=QnaListPage">more...</a--></td>
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
				<td class="table_title" width="20%">과정명</td>
				<td class="table_title" width="50%">질문제목</td>
				<td class="table_title" width="10%">작성일</td>
				<td class="table_title" width="10%">작성자</td>
			  </tr>
<% 
if (ss_action.equals("go")) {    //go button 선택시만 list 출력
			  
	for( i = 0 ; i < list.size() ; i++ ){
		DataBox dbox = (DataBox)list.get(i);		  

		v_subj		= dbox.getString("d_subj");  
		v_subjnm	= dbox.getString("d_subjnm");
		v_year		= dbox.getString("d_year");
		v_subjseq	= dbox.getString("d_subjseq");
		v_title		= dbox.getString("d_title");
		v_seq		= dbox.getInt("d_seq");
		v_indate	= dbox.getString("d_indate");
		v_inuserid  = dbox.getString("d_inuserid");				
%>
			  <tr class="table_02_1">
				<td class="table_02_1"><%=list.size()-i%></td>
				<td class="table_02_2"><%=v_subjnm%></td>
				<td class="table_02_2"><a href="javascript:qna_select('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>','<%=v_seq%>');"><%=v_title%><a></td>
				<td class="table_02_1"><%=FormatDate.getFormatDate(v_indate,"yyyy/MM/dd")%></a></td>
				<td class="table_02_1"><%=v_inuserid%></td>
			   </tr>
<%		
	}
		   
}
if(i == 0){ %>
              <tr> 
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="9">등록된 내용이 없습니다</td>
              </tr>
<% }%>
			</table>
			<!----------------- 조회 끝 ----------------->
			<br><br><br><br>
 
			<!----------------- 세부타이틀 시작 ----------------
			<table width="97%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="1%"><img src="/images/admin/common/icon.gif" ></td>
				<td  class=sub_title>지급 예상 금액</td>
				<td align="right" height="20"></td>
			  </tr>
			  <tr>
				<td height="3"></td>
				<td height="3"></td>
				<td height="3"></td>
			  </tr>
			</table>
			<!----------------- 세부타이틀 끝 ----------------->

			<!----------------- 조회 시작 --------------------
			<table class="table_out" cellspacing="1" cellpadding="5">
			  <tr>
				<td colspan="11" class="table_top_line"></td>
			  </tr>
			  <tr>
				<td class="table_title" width="5%">No</td>
				<td class="table_title" width="30%">과정명</td>
				<td class="table_title" width="20%">학습기간</td>
				<td class="table_title" width="10%">입과인원</td>
				<td class="table_title" width="10%">수료인원</td>
				<td class="table_title" width="10%">인당단가</td>
				<td class="table_title" width="10%">지급금액</td>
			  </tr>
<%
/* 
if (ss_action.equals("go")) {    //go button 선택시만 list 출력
			  
	for( k = 0 ; k < list3.size() ; k++ ){
		DataBox dbox3 = (DataBox)list3.get(k);		  

		
		v_subj = dbox3.getString("d_subj");  
		v_subjnm = dbox3.getString("d_subjnm");
		//v_year = dbox3.getString("d_year");
		//v_subjseq = dbox3.getString("d_subjseq");
		v_edustart = dbox3.getString("d_edustart");
		v_eduend = dbox3.getString("d_eduend");
		v_stucnt  = dbox3.getInt("d_stucnt");
		v_grayncnt  = dbox3.getInt("d_grayncnt");
		v_price  = dbox3.getInt("d_price");
		v_addprice  = dbox3.getInt("d_addprice");
*/
%>

			  <tr class="table_02_1">
				<td class="table_02_1"><%//=list3.size()-k%></td>
				<td class="table_02_2"><%//=v_subjnm%></td>
				<td class="table_02_1"><%//=FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")+" ~ "+FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")%></td>
				<td class="table_02_1"><%//=v_stucnt%></td>
				<td class="table_02_1"><%//=v_grayncnt%></td>
				<td class="table_02_1"><%//=v_addprice>0?v_addprice+" + ":""%><%=v_price%></td>
				<td class="table_02_1"><%//=(v_price*v_stucnt)+v_addprice%></td>
			   </tr>
<%/*		
	}
		   
}
if(k == 0){ */%>
			  <tr> 
				<td align="center" bgcolor="#F7F7F7" height="50" colspan="10">등록된 내용이 없습니다.</td>
			  </tr>
<%// }%>
			</table>
        <!----------------- 조회 끝 ----------------->
      </td>
  </tr>
</table>
<table>
  <tr>
	<td>
	    <%@ include file = "/learn/library/getJspName.jsp" %>
    </td>
  </tr>
</table>
</form>
</body>
</html>
