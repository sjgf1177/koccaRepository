<%
//**********************************************************
//  1. 제      목: 교육과정 입과인원조회
//  2. 프로그램명: za_cpeduPropose_l.jsp
//  3. 개      요: 수강신청자명단 리스트화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004.12.27
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%//@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

int i = 0, v_dispnum = 0, v_totalpage = 0, v_rowcount = 0, v_usercnt = 0;
String v_subj = "", v_subjnm = "", v_cpnm = "", v_cpsubjseq = "", v_appuser = "", v_year = "";
String v_subjseq = "", v_subjseqgr = "", v_propstart = "", v_propend = "", v_edustart = "", v_eduend = "";
   
String v_searchtext = box.getString("p_searchtext");
int v_pageno = box.getInt("p_pageno"); 
String v_gyear = box.getString("s_gyear");
String v_grcode = box.getString("s_grcode");
String v_process = box.getString("p_process");


//String s_userid = box.getSession("userid");
//String s_usernm = box.getSession("username");


%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language="javascript">
<!--//
function whenSelection(ss_action){
    document.form1.p_action.value = ss_action;
    
	if (ss_action=="go") {
		if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
		    alert("교육그룹을 선택하세요.");
		    return ;
		}
		top.ftop.setPam();
	}
	
	document.form1.action = "/servlet/controller.cp.CPEduStudentServlet";
	document.form1.p_process.value = "<%=v_process%>";
	document.form1.p_pageno.value = "1";
	document.form1.submit();
}

function go(index) {
     document.form1.p_pageno.value = index;
     document.form1.p_action.value = "go";
     document.form1.action = "/servlet/controller.cp.CPEduStudentServlet";
     document.form1.p_process.value = "<%=v_process%>";     
     document.form1.submit();
}  

function goPage(pageNum) {
	document.form1.p_pageno.value = pageNum;
	document.form1.p_action.value = "go";
	document.form1.action = "/servlet/controller.cp.CPEduStudentServlet";
	document.form1.p_process.value = "<%=v_process%>";  
	document.form1.submit();
}  

function selectList() {
	document.form1.action = "/servlet/controller.cp.CPEduStudentServlet";
	document.form1.p_process.value = "<%=v_process%>"; 
	document.form1.p_pageno.value = "1";
	document.form1.submit();
}

function MoveLink(act,year,subj,subjseq) {
	if(act == "1"){
		//수강확정자 명단
		document.form1.p_process.value   = "";
	    document.form1.action            = "/servlet/controller.cp.CPEduStudentServlet";
		document.form1.p_pageno.value = "1";
	    document.form1.submit();	
	}
	else if(act == "2"){
		//수강취소자 명단
		document.form1.p_process.value   = "cancel";
	    document.form1.action            = "/servlet/controller.cp.CPEduStudentServlet";
		document.form1.p_pageno.value = "1";
	    document.form1.submit();
	}
	else if(act == "3"){
		//수강신청자 명단
	    document.form1.p_process.value   = "propose";
	    document.form1.action            = "/servlet/controller.cp.CPEduStudentServlet";
		document.form1.p_pageno.value = "1";
	    document.form1.submit();
	}
	else if(act == "4"){
		//수강신청자 명단리스트
	    document.form1.p_process.value   = "proposeList";
	    document.form1.action            = "/servlet/controller.cp.CPEduStudentServlet";
		document.form1.p_year.value = year;
		document.form1.p_subj.value = subj;
		document.form1.p_subjseq.value = subjseq;
	    document.form1.submit();
	}
	else if(act == "5"){
		//엑셀 다운로드
        window.self.name = "CPEduStudent";
        window.open("","DownExcel","width=538,height=285,status=no,scrollbars=no");
        document.form1.target = "DownExcel";
        document.form1.action = "/servlet/controller.cp.CPEduStudentServlet";
        document.form1.p_process.value = "studentExcel";
        document.form1.submit();
	}
}
//-->
</script>
</head>

<body topmargin=0 leftmargin=0>

<form name="form1" action="" method="post">
<input type="hidden" name="p_process" value="<%=v_process%>">
<input type="hidden" name="p_action" value="">
<input type="hidden" name="p_pageno" value="<%=v_pageno%>">
<input type="hidden" name="p_grcode" value="<%=v_grcode%>">
<input type="hidden" name="p_gyear" value="<%=v_gyear%>">
<input type="hidden" name="p_year" value="">
<input type="hidden" name="p_subj" value="">
<input type="hidden" name="p_subjseq" value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/cp/cp_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	   
      <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td><b>
        <a href="javascript:MoveLink(1)"><img src="/images/admin/button/btn_deciname.gif" border="0"></a> 
		<a href="javascript:MoveLink(2)"><img src="/images/admin/button/btn_cancelname.gif" border="0"></a> 
		<a href="javascript:MoveLink(3)"><img src="/images/admin/button/btn_applyname_on.gif" border="0"></a>
		<br></td>
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table>  
      <table width="970" cellpadding="1" cellspacing="0" class="form_table_out">
        <tr> 
          <td align="center"><table width="970" cellpadding="0" cellspacing="0" class="form_table_bg">
              <tr> 
                <td height="7"></td>
              </tr>
              <tr> 
                <td align="center"> <table  border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  --></td>
                      <td>&nbsp; <%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도  -->&nbsp; </td>
                      <td>&nbsp;</td>
                      <td>교육업체</td>
                      <td> &nbsp; <%= CPCommonBean.getCP(box, true, true)%><!-- getCompany(RequestBox, isChange, isALL)    교육기관  -->&nbsp; </td>
                      <td width=20></td>
                      <td>과정명</td>
                      <td width=6></td>
                      <td><input name="p_searchtext" type="text" class="input" style="width:180" value="<%=v_searchtext %>"></td>
                      <td width=6></td>
                      <td><a href="javascript:whenSelection('go');"><img src="/images/admin/button/btn_search.gif"  border="0"></a>
                        <a href="javascript:MoveLink(5)"><img src="/images/admin/button/btn_exceldown.gif" border="0"></a>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td height="7"></td>
              </tr>
            </table></td>
        </tr>
      </table> 
      <br>
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
        <tr> 
          <td colspan="7" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title" width="4%">번호</td>
          <td class="table_title"  width="27%">과정명</td>
          <td class="table_title"  width="21%">현대.기아 업체차수</td>
          <td class="table_title"  width="16%">업체명</td>
          <td class="table_title"  width="17%">신청기간</td>
          <td class="table_title"  width="7%">인원</td>
          <td class="table_title"  width="8%">명단</td>
        </tr>

<%   
if (box.getString("p_action").equals("go")) {
	ArrayList list = (ArrayList)request.getAttribute("selectProposeList");
	                                                                                                                                            
	for(i = 0; i < list.size(); i++) {
        DataBox dbox = (DataBox)list.get(i);  

		//sql = "select a.subj, a.subjnm, b.subjseq, b.propstart, b.propend, b.edustart, b.eduend, c.cpnm, count(d.userid) as usercnt ";
        
        v_dispnum = dbox.getInt("d_dispnum");
        v_subj   = dbox.getString("d_subj");
        v_subjnm   = dbox.getString("d_subjnm");
        v_subjseq    = dbox.getString("d_subjseq");
        v_subjseqgr    = dbox.getString("d_subjseqgr");
        v_cpsubjseq    = dbox.getString("d_cpsubjseq");
        v_propstart    = dbox.getString("d_propstart");
        v_propend    = dbox.getString("d_propend");
        v_edustart    = dbox.getString("d_edustart");
        v_eduend    = dbox.getString("d_eduend");
        v_cpnm = dbox.getString("d_cpnm");
        v_usercnt = dbox.getInt("d_usercnt");
        v_totalpage = dbox.getInt("d_totalpage");
        v_rowcount = dbox.getInt("d_rowcount");
        v_year    = dbox.getString("d_year");
        	
        if (!v_searchtext.equals("")) {
            v_subjnm = StringManager.replace(v_subjnm, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            // 제목검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
        }
        
        if(v_cpsubjseq.equals("")){
        	v_cpsubjseq = "<span class=text_color04>미등록</span>";
        }
        
        if(v_usercnt > 0)
        	v_appuser = "<a href=\"javascript:MoveLink(4,'" + v_year + "','" + v_subj + "','" + v_subjseq + "')\"><img src=\"/images/admin/button/b_name.gif\"  border=\"0\"></a>";
        else
        	v_appuser = "-";
%>

        <tr> 
          <td class="table_02_1" width="4%"><%=v_dispnum%></td>
          <td class="table_02_1"  width="27%"><%=v_subjnm%></td>
          <td class="table_02_1"  width="21%"><%=StringManager.toInt(v_subjseqgr)%> / <%=v_cpsubjseq%></td>
          <td class="table_02_1"  width="16%"><%=v_cpnm%></td>
          <td class="table_02_1"  width="17%"><%=FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd")%> - <%=FormatDate.getFormatDate(v_propend,"yyyy/MM/dd")%></td>
          <td class="table_02_1"  width="7%"><%=v_usercnt%></td>
          <td class="table_02_1"  width="8%"><%=v_appuser%></td>
        </tr>
<%
	}
	if(list.size() <= 0){
%>
        <tr> 
          <td class="table_02_1" colspan="7" height="28" bgcolor="#ffffff">조회된 데이터가 없습니다.</td>
        </tr>
<%
	}
}
%>
      </table> 
    </td>
  </tr>
</table>
<br>
<table width="980" border="0" cellpadding="5" cellspacing="1">
  <tr>
    <td><%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %></td>
  </tr>
  <tr height="100">
    <td>
      <%@ include file = "/learn/library/getJspName.jsp" %> 
    </td>
  </tr>
</table>
<br>
</form>
</body>
</html>
