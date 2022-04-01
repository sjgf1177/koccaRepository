<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    int i = 0;
	
    String v_areaname = "";
    String v_cate = "";
    String v_subjnm = "";
    String v_edustart = "";
    String v_eduend = "";
    
    String v_name = "";		
    String v_gender		= "";		
    String v_isgraduated = "";		 
    String v_job	= "";	
    String v_subjseq	= "";
    
    String v_subj	= "";
    String v_year	= "";
    String v_userid	= "";
    
    String v_gender_icon = "";
    String v_isgraduated_icon = "";
    
    int v_user_cnt = 0;
    int v_grad_cnt = 0;
    int v_ngrad_cnt = 0;
    int v_man_cnt = 0;
    int v_woman_cnt = 0;
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd"); // 오늘 날짜
    String today = formatter.format(new java.util.Date());
    
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
function whenExcel() {   
	 document.form1.target = 'mainFrame';
     $("#isExcel").val("true");
     document.form1.submit();
     $("#isExcel").val("false");

}

function viewStuStatus(subj, year, subjseq, userid){
    
    window.open("about:blank", "listPagePerPerson", "top=0, left=0, width=600, height=570, status=no, scrollbars=yes");
    document.form1.target = "listPagePerPerson";
    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.p_userid.value = userid;
    document.form1.action = '/servlet/controller.off.OffStudentJobAdminServlet';
    document.form1.p_process.value = "selectListPerPerson";
    document.form1.submit();

    document.form1.target="_self";   
}
 
//-->
</SCRIPT>  
       <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" height="20">&nbsp;<b>상세보기</b></td>
          <td align="right">
         
          </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>           
      <!----------------- 조회 시작 ----------------->
      
      <form name="form1" method="post" action="">
	    <input type="hidden" name="p_process" >
	    <input type="hidden" name="p_action">
	    <input type="hidden" name="p_subj"      id="p_subj"         value="">
	    <input type="hidden" name="p_subjnm"    value="">
	    <input type="hidden" name="p_orderColumn">
	    <input type="hidden" name="p_year"      id="p_year"         value="">
	    <input type="hidden" name="p_subjseq"   id="p_subjseq"      value="">
	    <input type="hidden" name="p_userid"    id="p_userid"      value="">
	    <input type="hidden" name="p_orderColumn">
	   </form>
    
      
      <table class="table_out" cellspacing="1" cellpadding="5">		
        <tr>
          <td colspan="8" class="table_top_line"></td>
        </tr>
        <tr>
          
          <td class="table_title"><b>No</b></td>
          <td class="table_title"><b>과정명</b></td>  
          <td class="table_title"><b>기수</b></td>       
          <td class="table_title"><b>이름</b></td>
          <td class="table_title"><b>성별</b></td>
          <td class="table_title"><b>수료</b></td>
          <td class="table_title" ><b>취업</b></td>
         </tr>
         
 <%  	
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");
 			DecimalFormat df = new DecimalFormat("###,##0.00");
        	for (i=0; i<list.size(); i++) {
        		
				DataBox dbox = (DataBox)list.get(i);
				v_subj			= dbox.getString("d_subj");
				v_year			= dbox.getString("d_year");
				v_userid		= dbox.getString("d_userid");
				v_subjnm		= dbox.getString("d_subjnm");
				v_subjseq		= dbox.getString("d_subjseq");
				v_edustart		= dbox.getString("d_edustart");
				v_eduend		= dbox.getString("d_eduend").substring(0,8);
		        v_name			= dbox.getString("d_name");
		        v_gender		= dbox.getString("d_gender");
		        v_isgraduated	= dbox.getString("d_isgraduated");
		        v_job			= dbox.getString("d_compnm");  
		        
		        if (v_gender.equals("남")) {
		        	v_gender_icon = "<img src=\"/images/2012/common/icon/icon_man.gif\" alt=\"남자\">";
		        } else if (v_gender.equals("여")) {
		        	v_gender_icon = "<img src=\"/images/2012/common/icon/icon_woman.gif\" alt=\"여자\">";
		        }
		        
		        if ( Integer.parseInt(v_eduend) >= Integer.parseInt(today)){
		        	v_isgraduated_icon = "-";
		        }
		        else {
		        	if (v_isgraduated.equals("수료")) {
		        		v_isgraduated_icon = "<img src=\"/images/2012/common/icon/icon_graduated.gif\" alt=\"수료\">";
		        	} else  {
		        		v_isgraduated_icon = "<img src=\"/images/2012/common/icon/icon_fail.gif\" alt=\"미수료\">";
		        	}
		        }
		%>
		 <tr class="table_02_1">
		 <td class="table_02_1"><%= i+1 %></td>
          <td class="table_02_1"><%= v_subjnm %></td>
          <td class="table_02_1"><%= v_subjseq.equals("") ? "" : Integer.parseInt(v_subjseq)+"기" %></td>
          <td class="table_02_1"><%= v_name %></td>
          <td class="table_02_1"><%= v_gender_icon %></td>
          <td class="table_02_1"><%= v_isgraduated_icon %></td>
          <td class="table_02_1"><%= v_job %></td>
         <%--  <td class="table_02_1"><a href="javascript:viewStuStatus('<%=v_subj %>','<%=v_year %>','<%=v_subjseq %>','<%=v_userid %>');"><img src="/images/2012/common/icon/icon_modify.gif" border=0 alt="수정"></a></td> --%>
        </tr> 
		<% } %>
     
      </table>
      <br/> 
      
<%@ include file = "/learn/library/getJspName.jsp" %>      
   