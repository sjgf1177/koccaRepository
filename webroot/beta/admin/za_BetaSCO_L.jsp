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
<%@ page contentType = "text/html;charset=euc-kr" %>
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
        box = RequestManager.getBox(request);
    }
    String v_process2 = box.getString("p_process");
    
    String s_subj = box.getString("s_subj");
    String v_subjnm="";
    
	ErrorManager.systemOutPrintln(box);
	
	String v_cpseq = box.getSession("cpseq");
	String s_gadmin = box.getSession("gadmin");
	String v_gadmin = StringManager.substring(s_gadmin, 0,1);
	
	String compcd = conf.getProperty("autoever.compcd");

	if(v_cpseq == null || v_cpseq.equals("") || v_gadmin.equals("A") || v_gadmin.equals("H") ){
	  v_cpseq = compcd;
	}
	//out.println(v_cpseq);
	
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

<%  if (v_process2.equals("listPage")){ %>
        function whenSelection(p_action) {
            document.form1.p_process.value="listPage";
            document.form1.p_action.value = p_action;
            if (p_action=="go") top.ftop.setPam();
            document.form1.submit();
        }

		function whenContentSelection() {
            document.form1.p_process.value="ContentlistPage";         
            document.form1.submit();
        }

        function whenInsert(){
            document.form1.p_process.value = "insertPage";
            document.form1.p_job.value = "insert";            
            document.form1.submit();
        }
        
        function whenUpdate(oid,scolocate){
            document.form1.p_process.value = "updatePage";
            document.form1.p_job.value = "update";
            document.form1.p_oid.value = oid;  
    		document.form1.p_scolocate.value = scolocate;	
            document.form1.submit();           	
        }

	    function Sco_View(oid){		
			window.open("/learn/admin/contents/scorm/za_SCO_Contents_V.jsp?oid="+oid+"&p_content=1", "SCOVIEW", "width=670,height=730,scrollbars=yes,toolbars=no,resizable=yes");	
		}
<%  }else { %>  
        function whenSelection(p_action) {
            document.form1.p_process.value="searchList";
            document.form1.p_action.value = p_action;
            document.form1.submit();
        }
        function whenSelect(oid,sdesc){
            top.fdown.receive_obj(oid,sdesc);
        }
        function whenView(oid){
            document.form1.p_process.value = "viewPage";
            document.form1.p_job.value = "view";
            document.form1.p_oid.value = oid;
            
            document.form1.submit();
        }   
<%  }   %>

/*        function init(){
            if(document.form1.p_subj.value=="NO-SUBJ")
                document.form1.s_subj.options[document.form1.s_subj.length] = new Option("==과정미연결==","NO-SUBJ",true,true);
            else
                document.form1.s_subj.options[document.form1.s_subj.length] = new Option("==과정미연결==","NO-SUBJ");
        }
 */
function viewMETADATA(url) { window.open(url,'popup','width=800,height=600,top=50,left=50,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
}
    </script>
    
    <body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
    <table width="95%" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td align="center" valign="top">

<!--------타이틀백업 시작 2003.12.21
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
<%  if (v_process2.equals("listPage")){ %>         
          <td background="/images/admin/community/title_bg.gif"><img src="/images/admin/system/system_sco1.gif" width="138" height="36" border="0"></td>
<%  } else {    %>  
          <td background="/images/admin/community/title_bg.gif"><img src="/images/admin/system/system_sco1.gif" width="138" height="36" border="0"></td>
<%  }   %>  
        </tr>
      </table>
   타이틀 백업 끝------------------->

      <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/beta/tit_sco.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 박스 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <FORM name="form1" method="post" action="/servlet/controller.beta.BetaSCOServlet">
          <input type="hidden" name="p_process"  value="<%=v_process2%>">
          <input type="hidden" name="p_action" value="">
          <input type="hidden" name="p_job"   value="">
          <input type="hidden" name="p_subj"   value="<%=s_subj%>">
          <input type="hidden" name="p_oid"   value="">
		  <input type="hidden" name="p_scolocate"   value="">
		  <input type="hidden" name="p_cpseq"   value="<%=v_cpseq%>">
		  
          
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg">
              <tr>
                <td height="7" width="99%"></td>
              </tr>
              <tr>
                <td align="center" width="99%" valign="middle">
                  <table width="99%" cellspacing="0" cellpadding="0" class=""form_table">
                    <tr>
                      <td align=center>
                      사용과정 
                      <%=BetaSCOBean.makeObcSubjSelect(box,s_subj,"")%>  
                      &nbsp;&nbsp;
                      검색어
                      <input type=text name="s_gubun" value="<%=box.getString("s_gubun")%>">
                      </td>
                      <td>
                      <a href="javascript:whenSelection('go')"><img src="/images/admin/button/b_go.gif"  border="0" align=absmiddle></a>
					<%  if (v_process2.equals("listPage")){ %>                      
                      &nbsp;
                      <a href="javascript:whenInsert()"><img src="/images/admin/button/btn_add.gif"  border="0" align=absmiddle></a>
	         &nbsp; 
                      <a href="javascript:whenContentSelection()"><img src=/images/admin/button/btn_contentslist.gif border=0 align=absmiddle></a>
					<%  }   %>
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
        </FORM>
      </table>
      <!----------------- 박스 끝 ----------------->
      <br>
      <!----------------테이블 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="8" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" width="5%"><b>No</b></td>
          <td class="table_title" width="12%"><b>SCO ID</b></td>
          <td class="table_title" width="18%"><b>패키지명</b></td>
          <td class="table_title" width="35%"><b>SCO 명</b></td>
          <td class="table_title" width="20%"><b>사용과정</b></td>
          <td class="table_title" width="10%"><b>메타데이타</b></td>          
        </tr>
<%
    if (box.getString("p_action").equals("go")){
        ArrayList  list = (ArrayList)request.getAttribute("ObjectList");
        BetaSCOData d = null;
        Hashtable   ht;
		String v_masteryscore = "";
		String v_depthimg ="";
        
        for (int i=0; i<list.size(); i++) {
            d = (BetaSCOData)list.get(i);  
			
			if (d.getMasteryscore() == 0 ) {
				v_masteryscore = "-";
			} else {
				v_masteryscore = String.valueOf(d.getMasteryscore())+"점";
			}

			if(d.getThelevel() == 1){
				v_depthimg	= "&nbsp;<img src='/images/admin/system/folderopen.gif' border='0' width='16' height='14'  align='absmiddle'> ";
			}else{
				v_depthimg	= "<img src='/images/admin/system/blanknode.gif' border='0' width='"+ (20 * (d.getThelevel() -1)) +"' height='2' align='absmiddle'><img src='/images/admin/system/lastnode.gif' border='0' align='absmiddle' ><img src='/images/admin/system/folderopen.gif' border='0' width='16' height='14' align='absmiddle'> ";
			}
%>

        <tr>
          <td class="table_01"><%=(i+1)%></td>
          <td class="table_02_1"><%=d.getOid()%></td>
          <td class="table_02_2"><%=d.getScoTitle()%></td>
		 <% if (d.getStarting().equals("")){ %>
		 <td class="table_02_2"><%=v_depthimg%><%=d.getSdesc()%></td>
		 <% } else  { %>
		 <td class="table_02_2"><%=v_depthimg%><a href="javascript:Sco_View('<%=d.getOid()%>')"><%=d.getSdesc()%></a></td>
		 <% } %>
          
          <td class="table_02_1">
<%          
            if((Hashtable)d.getSubjList() != null){
                ht  = (Hashtable)d.getSubjList();
                for(int j=0;j<ht.size();j++)    {
                    v_subjnm = (String)ht.get(String.valueOf(j));
%>                  <%=v_subjnm%><br>   <%
                }
            }
%>
          </td>
          
<% if ((d.getHighoid() != "" ) && (d.getMetalocation() != "")) { %>
           <td class="table_02_1">
		   <% if( d.getMetadata() != 0 ) {  %>
           <a href="javascript:Meta_Add('<%=d.getOid()%>','<%=d.getMetalocation()%>','<%=d.getScolocate()%>')">보기</a></td>
		   <% } else { %>
		   <a href="javascript:Meta_Add('<%=d.getOid()%>','<%=d.getMetalocation()%>','<%=d.getScolocate()%>')">등록</a></td>
		   <% } %>
<% } else { %>
           <td class="table_02_1"> - </td>         
<% } %>
        </tr>

<%      }
    }
%>


      </table>
      <!----------------- 테이블 끝 ----------------->
      <br>
    </td>
  </tr>
    </table>
    </body>
    </html>
<%@ include file = "/learn/library/getJspName.jsp" %>
