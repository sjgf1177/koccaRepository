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
<%@ page import = "com.credu.beta.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    String v_process2 = box.getString("p_process");    
    String p_scolocate = box.getString("p_scolocate");   
    String p_subj = box.getString("p_subj");
	String p_content = box.getString("p_content");
	
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

		var checkflag = "false";
		function check(field) {

			if (checkflag == "false") {
					for (i = 0; i < field.length; i++) {
					field[i].checked = true;}
				checkflag = "true";
				return "전체해제"; 
			}
			else {
					for (i = 0; i < field.length; i++) {
					field[i].checked = false; }
				checkflag = "false";
				return "전체선택"; 
			}
		}

        function whenSelection(p_action) {			
            document.form1.p_process.value="scoContentListPage";
            document.form1.p_action.value = p_action;
            if (p_action=="go") {
	            document.form1.submit();
			}
        }

		function whenPackageInsert(p_action) {			
		    document.form1.p_process.value="scoContentInsertPage";		
			if (p_action=="go") {
				parent.fdown.delay(2);
				document.form1.submit();
			}
        }

		function whenLoInsert(p_action, field ) {			
		    document.form1.p_process.value="scoLOContentInsertPage";		
            
		    j = 0;			
			var X=eval("document.form1."+field)             			
            if (X == undefined)
			{
				alert("선택된 LO가 없습니다");
				return;
			}

			for (c=0;c<X.length;c++) 
			 if (X[c].checked == true){
				j++;
			}

			if ( j == 0 ) {
				alert("등록할 LO를 선택해 주세요");
				return;
			}

			if (p_action=="go") {
				parent.fdown.delay(3);
	            document.form1.submit();
			}
        }
		
	function Sco_View(oid){		
		window.open("/learn/admin/contents/scorm/za_SCO_Contents_V.jsp?oid="+oid+"&p_content=1", "SCOVIEW", "width=670,height=730,scrollbars=yes,toolbars=no,resizable=yes");	
    }
    </script>
    
    <body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" >
    <table width="95%" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/beta/tit_masterform.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 박스 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <FORM name="form1" method="post" action="/servlet/controller.beta.BetaMasterFormServlet">
          <input type="hidden" name="p_process"  value="<%=v_process2%>">
          <input type="hidden" name="p_action" value="">
          <input type="hidden" name="p_job"   value="">
          <input type="hidden" name="p_subj"   value="<%=p_subj%>">
          <input type="hidden" name="p_oid"   value="">		 
          
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg">
              <tr>
                <td height="7" width="99%"></td>
              </tr>
              <tr>
                <td align="center" width="99%" valign="middle">
                  <table width="99%" cellspacing="0" cellpadding="0" class="form_table">
                    <tr>
					  <td width=30%>
					  <input type=radio value=1 name=p_content <% if (p_content.equals("1") || ( p_content == "")) {%> checked <% 		} %>>패키지 가져오기                       
					  <input type=radio value=2 name=p_content <% if (p_content.equals("2")){%> checked <% }%>>LO 가져오기 </td>
					 
                      <td align=left >
                      패키지명 
                      <%=MasterFormBean.makeSCOPackageSelect(box,p_scolocate)%>&nbsp;&nbsp; 
					  <a href="javascript:whenSelection('go')"><img src="/images/admin/button/b_go.gif" border="0" align=absmiddle></a>
                      </td>
                      <td>
					  <% if ( p_scolocate != "" ) { %>
						   <% if (p_content.equals("2")) {%> 
							<a href="javascript:whenLoInsert('go', 'locontent' )"><img src="/images/admin/button/btn_LO.gif" border=0></a>						
						   <% } else { %>
							<a href="javascript:whenPackageInsert('go')"><img src="/images/admin/button/btn_package.gif" border=0></a>					 
						   <% } %>
					  <% } %>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="7" width="99%"></td>
              </tr>
            </table>
          </td>
        </tr>
        
      </table>

      <!----------------- 박스 끝 ----------------->
      <br>
      <!----------------테이블 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="8" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" width="6%"><b>No</b></td>
          <td class="table_title" width="12%"><b>SCO ID</b></td>
          <td class="table_title" width="18%"><b>패키지명</b></td>
          <td class="table_title" width="56%"><b>SCO 명</b></td>
		  <td class="table_title" width="8%">
		  <% if (box.getString("p_action").equals("go")){ %>
			<% if (p_content.equals("2")  ) { %>		   
				
					 <b><input type=button value="전체선택" onClick="this.value=check(this.form.locontent)"></b>		 
			<!--<a href="#" onClick="check(this.form.locontent)"><img src="/images/admin/button/b_allselect.gif" border=0 > </a> -->			
			<% } %>
          <% } %>
		  </td>
        </tr>
<%
    if (box.getString("p_action").equals("go")){

		 ArrayList  list = (ArrayList)request.getAttribute("ObjectList");
		 BetaSCOData d = null;
		 Hashtable   ht;
		 String v_masteryscore = "";
		 String v_depthimg ="";
		 String v_option = "";
		
		if ( list.size() > 0 ) { 
        
				for (int i=0; i<list.size(); i++) {
					d = (BetaSCOData)list.get(i);  
					
					if (d.getMasteryscore() == 0 ) {
						v_masteryscore = "-";
					} else {
						v_masteryscore = String.valueOf(d.getMasteryscore())+"점";
					}

					if(d.getThelevel() == 1){
						v_depthimg	= "&nbsp;<img src='/images/admin/system/folderopen.gif' border='0' width='16' height='14'  align='absmiddle'> &nbsp;";
					}else{
						v_depthimg	= "<img src='/images/admin/system/blanknode.gif' border='0' width='"+ (20 * (d.getThelevel() -1)) +"' height='2' align='absmiddle'><img src='/images/admin/system/lastnode.gif' border='0' align='absmiddle' ><img src='/images/admin/system/folderopen.gif' border='0' width='16' height='14' align='absmiddle'> ";
					}

					if((d.getThelevel() == 1)  && (p_content.equals("2"))){ 
						v_option = "<input type=checkbox name='locontent' value="+d.getOid()+">";			
					} else {
						v_option = "";			
					}
		%>

				<tr>
				  <td class="table_01"><%=(i+1)%></td>
				  <td class="table_02_1"><%=d.getOid()%></td>
				  <td class="table_02_1"><%=d.getScoTitle()%></td>
				 <% if (d.getStarting().equals("")){ %>
				 <td class="table_02_2"><%=v_depthimg%><%=d.getSdesc()%></td>
				 <% } else  { %>
				 <td class="table_02_2"><%=v_depthimg%><a href="javascript:Sco_View('<%=d.getOid()%>')"><%=d.getSdesc()%></a></td>
				 <% } %>
				 <td class="table_02_1"><%=v_option%></td>

				</tr>

		<%      }
		} else { %>
		<tr> <td class="table_02_1" colspan=5>객체가 없거나 이미 등록되어 있습니다.</td></tr>
    <%  }
    }
%>
      </table>
      <!----------------- 테이블 끝 ----------------->
      <br>
    </td>
  </tr>
    </table>
	</FORM>
    </body>
    </html>
<%@ include file = "/learn/library/getJspName.jsp" %>