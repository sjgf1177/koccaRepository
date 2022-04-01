<%
/**
 * file name : za_MasterFormSubjObjectBranch_U.jsp
 * date      : 2003/10/23
 * programmer: LeeSuMin
 * function  : OBC마스터폼 Lesson-Branch별 Object 지정
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }
    
    String  p_subj   	= box.getString("p_subj");
    String  p_lesson   	= box.getString("p_lesson");
    int		p_branch   	= Integer.parseInt(box.getString("p_branch"));
    String	c_gadmin	= box.getSession("s_gadmin");
    
    String	p_process	= box.getString("p_process");

    
	BetaMasterFormData	da	=(BetaMasterFormData)request.getAttribute("BetaMasterFormData");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript" src="/script/default.js"></script>
<script language="JavaScript">
	function whenSubmit(){
		for(var i=0; i<document.form1.p_broid.length;i++){
			if(document.form1.p_broid[i].value.length != 10){
				alert("Object-ID가 10자리가 아닙니다.["+document.form1.p_broid[i].value.length+"]. 다시 확인하여 주십시오");
				return;
			}
		}
		document.form1.submit();
	}
</script>

</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="95%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/images/admin/community/title_bg.gif"><img src="/images/admin/contents/contents_title14.gif" width="162" height="36" border="0"></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <br>
      <!----------------- 과정 관리 시작 ----------------->
      <table width="500" cellspacing="0" cellpadding="0" border="0">
      	<tr>
      		<td>
			      <table cellspacing="1" cellpadding="5" class="table_out">
			        <tr> 
			          <td colspan="11" class="table_top_line"></td>
			        </tr>
			        <tr>
			          <td class="table_title" width="18%"><b>과 정</b></td>
			          <td class="table_02_2"><%=da.getSubjnm()%></td>
			        </tr>
			        <tr>
			          <td class="table_title" width="18%"><b>Lessons</b></td>
			          <td class="table_02_2"><%=box.getString("p_lesson")%> - <%=GetCodenm.get_lessonnm(p_subj,p_lesson)%></td>
			        </tr>
			        <tr>
			          <td class="table_title" width="18%"><b>Branch</b></td>
			          <td class="table_02_2"><%=p_branch%> - <%=GetCodenm.get_branchname(p_subj,p_branch)%></td>
			        </tr>
			
			
			      </table>
				</td>
			</tr>
		</table>
      <!----------------- 과정 관리 끝 ----------------->
      <br>
      <!----------------테이블 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="11" class="table_top_line"></td>
        </tr>
        <FORM name=form1 method="post" action="/servlet/controller.beta.BetaMasterFormServlet">
        	<input type=hidden name="p_subj" value="<%=p_subj%>">
        	<input type=hidden name="p_lesson" value="<%=p_lesson%>">
        	<input type=hidden name="p_branch" value="<%=p_branch%>">
        	<input type=hidden name="p_process" value="saveObjectBranch">
        	
        <tr>
          <td class="table_title" width="6%"><b>Ordering</b></td>
          <td class="table_title"><b>Base Object<br>(object Name)</b></td>
          <td class="table_title"><b>Object ID<br>(assign to branch [1])</b></td>
          <td class="table_title"><b>Object Name</b></td>
        </tr>
<%
	ArrayList		list1 = (ArrayList)request.getAttribute("MfSubjObjectBranch");
	MfLessonDataSub	ds = null;
	for(int i=0;i<list1.size();i++){
		ds = (MfLessonDataSub)list1.get(i);
%>		
        <tr>
          <td class="table_01"><%=ds.getOrdering()%></td>
          <td class="table_02_2"><%=ds.getOid()%> - <%=ds.getSdesc()%></td>
          <td class="table_02_2"><input type="text" class="input" name="p_broid" size="10" maxlength="10" value="<%=ds.getBroid()%>"></td>
          <td class="table_02_2"><%=ds.getBranchnm()%></td>
        </tr>			

<%	}

%>
		</FORM>
      </table>
      <!----------------- 테이블 끝 ----------------->
      <br>
      <!----------------- 저장 버튼 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td align="center" height="20"> 
          	<a href='javascript:whenSubmit()'><img src="/images/admin/contents/save1_butt.gif" width="37" height="18" border="0"></a> 
          	<a href='javascript:window.close()'><img src="/images/admin/contents/cancel1_butt.gif" width="37" height="18" border="0"></a></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 저장 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
</body>
</html>
<%@ include file = "/learn/library/getJspName.jsp" %>
