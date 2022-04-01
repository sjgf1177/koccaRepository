<%
//**********************************************************
//  1. 제      목: 스콤 콘텐츠 리스트
//  2. 프로그램명 : za_SCOContent_L.jsp
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
<%@ page import = "com.credu.beta.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%   
    RequestBox box = null;	
    box = (RequestBox)request.getAttribute("requestbox");
	
    if (box == null) {
    	box = RequestManager.getBox(request);    }
	
    String	p_scolocate	= box.getString("p_scolocate");

	ArrayList  list = (ArrayList)request.getAttribute("ScoObjectList");  
	BetaSCOData d = null;
	Hashtable   ht;
	String v_depthimg	= "";
	String v_masteryscore = "";
	String v_objdelbutton = "";
	String v_object_title = "";

	ErrorManager.systemOutPrintln(box);

	d = (BetaSCOData)list.get(0);
	
   %>

    <html>
    <head>
    <title></title>
	<META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    <link rel="stylesheet" href="/css/admin_style.css" type="text/css">
    <script language = "javascript" src = "/script/cresys_lib.js"></script>	
	<script language = "javascript" src = "/script/scorm_metadata.js"></script>
    <script language="JavaScript" src="/js/default.js"></script>

	<script language="javascript">
       
	function whenUpdate(oid,scolocate){		
		document.form1.p_process.value = "updatePage";
		document.form1.p_job.value = "update";		
		document.form1.p_oid.value = oid;
		document.form1.p_scolocate.value = scolocate;
		document.form1.submit();
	} 

	function whenDelete(oid,scolocate){	
		
		if(confirm("콘텐츠를 삭제하시겠습니까?")){
			document.form1.p_process.value = "deletescocontent";
			document.form1.p_job.value = "delete";
			document.form1.p_oid.value = oid;	
			document.form1.p_scolocate.value = scolocate;	
			document.form1.submit();
		} else {
			return;
		}
	} 

	function whenContentSelection() {
            document.form1.p_process.value="ContentlistPage";         
            document.form1.submit();
    }
	
	function Sco_View(oid){		
		window.open("/learn/admin/contents/scorm/za_SCO_Contents_V.jsp?oid="+oid+"&p_content=1", "SCOVIEW", "width=670,height=730,scrollbars=yes,toolbars=no,resizable=yes");	
    }

    </script>

    </head>
    
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
      </table>      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 박스 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <FORM name="form1" method="post" action="/servlet/controller.contents.SCOServlet">
          <input type="hidden" name="p_process"  value="">
          <input type="hidden" name="p_action" value="">
          <input type="hidden" name="p_job"   value="">
          <input type="hidden" name="p_subj"   value="">
		  <input type="hidden" name="s_gubun"   value="">
          <input type="hidden" name="p_oid"   value="">
		  <input type="hidden" name="p_scolocate" value="">
         
      
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg">
              <tr>
                <td height="7" width="99%"></td>
              </tr>
			  
              <tr>
                <td align="left" width="99%" >&nbsp;&nbsp;
                  <b>콘텐츠제목:</b>  <b><font color=blue><%= d.getScoTitle()%></font></b>&nbsp;&nbsp;&nbsp;&nbsp;

				    <OBJECT ID="ETNG_FTP" CLASSID="CLSID:526A654F-760C-49DD-B971-5517ECB98B12"
						CODEBASE="/myclasses/ETNG_FTP.CAB#version=1,0,0,2" width="52" height="22">
								  <param name="FtpAddr"   value="<%= conf.getProperty("ftp.server.ip")%>">
								  <param name="UserID"   value="<%= conf.getProperty("ftp.server.id")%>">
								  <param name="UserPwd"   value="<%= conf.getProperty("ftp.server.pw")%>">
								  <param name="MainDir"     value="/attach/edu/dp/contents/scobject/<%= d.getScolocate()%>">
				   </OBJECT>

                </td>
              </tr>
              <tr>
                <td height="7" width="99%"></td>
              </tr>
            </table>
          </td>
        </tr>
        </FORM>
      </table>
      <!----------------- 박스 끝 ----------------->
      <br>
      <!----------------테이블 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="6" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" width="6%"><b>No</b></td>
          <td class="table_title" width="16%"><b>Sco ID</b></td>
          <td class="table_title" width="43%"><b>SCO 제목</b></td>
          <td class="table_title"><b>통과점수</b></td>         
        <!--  <td class="table_title" width="10%"><b>메타데이타</b></td>-->
          <td class="table_title" width="12%"><b>기능</b></td>
        </tr>

<% 

	for (int i=0; i<list.size(); i++) {
            d = (BetaSCOData)list.get(i);   
		
			if(d.getThelevel() == 1){
				v_depthimg	= "&nbsp;<img src='/images/admin/system/folderopen.gif' border='0' width='16' height='14'  align='absmiddle'> ";
			}else{
				v_depthimg	= "<img src='/images/admin/system/blanknode.gif' border='0' width='"+ (20 * (d.getThelevel() -1)) +"' height='2' align='absmiddle'><img src='/images/admin/system/lastnode.gif' border='0' align='absmiddle' ><img src='/images/admin/system/folderopen.gif' border='0' width='16' height='14' align='absmiddle'> ";
			}

			if (d.getMasteryscore() == 0 ) {
				v_masteryscore = "-";
			} else {
				v_masteryscore = String.valueOf(d.getMasteryscore())+"점";
			}
            			
				v_objdelbutton		= "<a href=javascript:whenUpdate('"+d.getOid()+"','"+d.getScolocate()+"')><img src='/images/admin/button/b_modify.gif'  border='0'></a> &nbsp <a href=javascript:whenDelete('"+d.getOid()+"','"+d.getScolocate()+"')> <img src='/images/admin/button/b_delete.gif' border='0'></a>";
		
			if (d.getStarting().equals("")){
				v_object_title = d.getSdesc();
			} else {
				v_object_title = "<a href=javascript:Sco_View('"+ d.getOid() +"')>"+ d.getSdesc() +"</a>";
			}
%>
        <tr>
          <td class="table_01"><%=(i+1)%></td>
          <td class="table_02_1"><%=d.getOid()%></td>
          <td class="table_02_2"><%=v_depthimg%><%=v_object_title%></td>
          <td class="table_02_1"><%=v_masteryscore%></td>


          <td class="table_02_1"><%=v_objdelbutton%></td>
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

	<table border=0 width=100%><tr><td align=center><a href="javascript:whenContentSelection()"><img src=/images/admin/button/btn_list.gif border=0></a></td></tr></table>

    </body>
    </html>

<%@ include file = "/learn/library/getJspName.jsp" %>
    