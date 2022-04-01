<%
//**********************************************************
//  1. 제      목: 스콤 콘텐츠 리스트
//  2. 프로그램명 : za_SCO_L.jsp
//  3. 개      요: 스콤 콘텐츠 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 김기수 2004. 11. 24
//  7. 수      정:
//***********************************************************
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

	MasterFormBean bean = new MasterFormBean();

	String p_job = box.getString("p_job");
	String p_subj = box.getString("p_subj");
	String p_subjnm = bean.SelectSubjNm(p_subj);

	int locount = 0;
	ArrayList	list=null, list1=null; 

%>

    <html>
    <head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    <link rel="stylesheet" href="/css/admin_style.css" type="text/css">
    <script language = "javascript" src = "/script/cresys_lib.js"></script>
	<script language = "javascript" src = "/script/scorm_metadata.js"></script>
    <script language="JavaScript" src="/js/default.js"></script>
    </head>
    
    <script language="javascript">

    function start(p_action) {			
		document.form1.p_process.value="scoSCOMappingContentListPage";
		document.form1.p_action.value = p_action;
		if (p_action=="go") {
			document.form1.submit();
		}
    }

	function delay(remainSec) {         
		if ( remainSec==0) {
         	document.form1.p_process.value="scoSCOMappingContentListPage";
			document.form1.p_action.value = "go";
			document.form1.submit();
		} else {
			setTimeout("delay("+(remainSec-1)+")",1000);
		}
	}

	function delsco(p_action) {	
		parent.opener.delay(3);
        document.form1.p_process.value="scoDeleteContentListPage";
		document.form1.p_action.value = p_action;
		if (p_action=="go") {
			document.form1.submit();
		}	
	}

	function delOneSco(p_lesson, onescoid) {	
		parent.opener.delay(3);
        document.form1.p_process.value="scoDeleteOneContentListPage";
		document.form1.p_action.value = "go";
		document.form1.p_onescoid.value = onescoid;
		document.form1.p_lesson.value = p_lesson;		
		document.form1.submit();

	}

	function gohigh(ordering,onescoid ) {	
        document.form1.p_process.value="scogoHighContentListPage";
		document.form1.p_action.value = "go";
		document.form1.p_onescoid.value = onescoid;		
		document.form1.p_ordering.value = ordering;		
		document.form1.submit();
	}

	function golow(ordering, onescoid) {	
        document.form1.p_process.value="scogoLowContentListPage";
		document.form1.p_action.value = "go";
		document.form1.p_onescoid.value = onescoid;
		document.form1.p_ordering.value = ordering;
		document.form1.submit();		
	}

	function closeall(locount) {				
		parent.close();
	}

	function saveall() {
		parent.opener.delay(3);
		document.form1.p_process.value="scoSCOSaveContentListPage";
		document.form1.p_action.value = "go";		
		document.form1.submit();
	}


	
    </script>
    <% if ( p_job.equals("")) { %>
    <body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload=start('go');>		
	<% } else { %>
	<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" >
	<% } %>
    <table width="95%" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/c_title08.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>		
      </table>
	  <br>
	  <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>과정명: <%=p_subjnm%></td>
        </tr>
        
      </table> 

	
      <!----------------- 박스 시작 ----------------->
      <table cellspacing="0" cellpadding="3" class="form_table_out">
        <FORM name="form1" method="post" action="/servlet/controller.contents.MasterFormServlet">
          <input type="hidden" name="p_process"  value="">
          <input type="hidden" name="p_action" value="">
          <input type="hidden" name="p_job"   value="">
          <input type="hidden" name="p_subj"   value="<%=p_subj%>">
          <input type="hidden" name="p_oid"   value="">
		  <input type="hidden" name="p_onescoid"   value="">	
		  <input type="hidden" name="p_ordering"   value="">
		  <input type="hidden" name="p_lesson"   value="">
        
      </table>
      <!----------------- 박스 끝 ----------------->
      <br>
      <!----------------테이블 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="8" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" width="4%"><b>No</b></td>
          <td class="table_title" width="8%"><b>SCO ID</b></td>
          <td class="table_title" width="16%"><b>패키지명</b></td>
          <td class="table_title" width="45%"><b>SCO 명</b></td>		  
		  <td class="table_title" width="15%"><b>순서</b></td>
		  <td class="table_title" width="12%"><b>
	  <input type=button value="전체삭제" onClick="javascript:delsco('go');"></b>
		<!--	  	<a href="#" onClick="check(this.form.locontent)"><img src="/images/admin/button/b_alldelete.gif" border=0 > </a> -->			
		</td>
        </tr>
<%
	  if (p_job.equals("mappingall")){
		list1 =  bean.SelectMfModuleList(box);
	  } else {
		list1 = bean.SelectMfModuleList(box);
	  }

    if (box.getString("p_action").equals("go")){
		if (p_job.equals("mappingall")){
	        list = bean.SelectMappingLOList(box);
		} else { 
			list = bean.SelectMappingLOList(box);
		}

        SCOData d = null;
        Hashtable   ht;		
            
			locount = list.size();// LO 갯수 지정

			for (int i=0; i<list.size(); i++) {
					d = (SCOData)list.get(i);  
		%>

				<tr>
				  <td class="table_01"><%=(i+1)%></td>
				  <td class="table_02_1"><%=d.getOid()%></td>
				  <td class="table_02_1"><%=d.getScoTitle()%></td>		 
				 <td class="table_02_2"><img src='/images/admin/system/folderopen.gif' border='0' width='16' height='14'  align='absmiddle'>&nbsp;&nbsp;<%=d.getSdesc()%></td>
				 
				<%  if (list.size()>1) { %>
				<td class="table_02_1"><a href="javascript:gohigh('<%=d.getOrdering()%>', '<%=d.getOid()%>')"><img src='/images/admin/button/up_b.gif' border='0' align='absmiddle'></a>&nbsp;&nbsp;<a href="javascript:golow('<%=d.getOrdering()%>', '<%=d.getOid()%>')"><img src='/images/admin/button/down_b.gif' border='0' align='absmiddle'></a></td>
				<% } else { %>
				<td class="table_02_1"></td>
				<% } %>
				 <td class="table_02_1"><a href="javascript:delOneSco('<%=d.getLesson()%>','<%=d.getOid()%>');"><img src='/images/admin/button/b_delete.gif' border='0'></td>
				</tr>
				<input type=hidden name="p_scoid" value="<%=d.getOid()%>">
		<%
			}        
		}
%>
      </table>
      <!----------------- 테이블 끝 ----------------->
      <br>
    </td>
  </tr>
    </table>
	
	<table width="95%"><tr><td align=center> <a href="javascript:saveall('<%=locount%>')"><img src="/images/admin/button/btn_save.gif" border="0"></a>&nbsp;&nbsp; <a href="javascript:closeall()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td></tr></table>
	</FORM>
    </body>
    </html>
<%@ include file = "/learn/library/getJspName.jsp" %>