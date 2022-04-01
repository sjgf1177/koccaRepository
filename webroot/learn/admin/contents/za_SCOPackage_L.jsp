<%
//**********************************************************
//  1. 제      목: 스콤 콘텐츠 리스트
//  2. 프로그램명 : za_SCOPackage_L.jsp
//  3. 개      요: 패키지 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 김기수 2004. 11. 24
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
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

	SCOBean bean = new SCOBean();
    
	ArrayList  list = bean.performScoPackageList(box);
	SCOData d = null;

	ErrorManager.systemOutPrintln(box);	
%>

    <html>
    <head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    <link rel="stylesheet" href="/css/admin_style.css" type="text/css">
    <script language = "javascript" src = "/script/cresys_lib.js"></script>
    <script language="JavaScript" src="/js/default.js"></script>
    </head>
    
    <script language="javascript">

	function whenDelete(scolocate){	
		if(confirm("콘텐츠를 삭제하면 과정을 사용할 수 없습니다. \n콘텐츠를 삭제하시겠습니까?")){
			if(confirm("확인을 누르면 물리적,논리적으로 삭제됩니다. \n콘텐츠를 삭제하시겠습니까?")){
				document.form1.p_process.value = "deletepackage";
				document.form1.p_scolocate.value = scolocate;	
				document.form1.submit();
			} else {
				return;
			}
		} else {
			return;
		}
	} 

	function whenContent(scolocate){			
			document.form1.p_process.value = "PackageContentList";
			document.form1.p_scolocate.value = scolocate;	
			document.form1.submit();		
	} 

	function whenSelection() {
            document.form1.p_process.value="listPage";                        
            document.form1.submit();
    }

    </script>
    
    <body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" >
    <table width="95%" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/c_title05.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	        <!----------------- title 끝 ----------------->
      <br>
	          <!-------------소제목 시작-------------------------->
 <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>콘텐츠리스트</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        <!--------------소제목 끝---------------------------->
      <!----------------- 박스 시작 ----------------->
      <table cellspacing="0" cellpadding="3" class="form_table_out">
        <FORM name="form1" method="post" action="/servlet/controller.contents.SCOServlet">
          <input type="hidden" name="p_process"  value="">
          <input type="hidden" name="p_action" value="">
          <input type="hidden" name="p_job"   value="">
          <input type="hidden" name="p_subj"   value="">
          <input type="hidden" name="p_oid"   value="">
		  <input type="hidden" name="p_scolocate"   value="">
          
        <tr>
          <td bgcolor="#C6C6C6" align="center">            
          </td>
        </tr>
        </FORM>
      </table>
      <!----------------- 박스 끝 ----------------->
      <br>
      <!----------------테이블 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="3" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" width="6%"><b>No</b></td>
          <td class="table_title" width="60%"><b>패키지 명</b></td> 
		  <td class="table_title" width="20%"><b>등록일</b></td> 
          <td class="table_title" width="14%"><b>기능</b></td>
        </tr>

		<%
        for (int i=0; i<list.size(); i++) {
			d = (SCOData)list.get(i);  
		%>

        <tr>
          <td class="table_01" align=center><%=(i+1)%></td>
          <td class="table_02_2" align=center><a href="javascript:whenContent('<%=d.getScolocate()%>')"><%=d.getScoTitle()%></a></td>
		  <td class="table_02_1" align=center><%=FormatDate.getFormatDate(d.getLdate(),"yyyy/MM/dd HH")%>시</td>
          <td class="table_02_1" align=center><a href="javascript:whenDelete('<%=d.getScolocate()%>')"><img src='/images/admin/button/b_delete.gif' border='0'></a></td>          
        </tr>

		<% 
		}
		%>
      </table>
      <!----------------- 테이블 끝 ----------------->
      <br>
    </td>
  </tr>
    </table>
	<table border=0 width=100%><tr><td align=center><a href="javascript:whenSelection()"><img src=/images/admin/button/btn_list.gif border=0></a></td></tr></table>
    </body>
    </html>
<%@ include file = "/learn/library/getJspName.jsp" %>