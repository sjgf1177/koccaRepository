<%
//**********************************************************
//  1. 제      목: LinkSite
//  2. 프로그램명: za_LinkSite_L.jsp
//  3. 개      요: 관련사이트 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정경진 2005. 8. 03
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");    
    String  v_process   = box.getString("p_process");    
    String  v_grcode	= box.getStringDefault("s_grcode",box.getSession("tem_grcode"));  
    String  ss_grcode   = box.getString("s_grcode");            //교육그룹
    String  s_gadmin    = box.getSession("gadmin");
    String  v_gadmin    = StringManager.substring(s_gadmin, 0, 1);
    String  isAll       = !v_gadmin.equals("A") ? "false" : "true";
    String  v_selGroup  = box.getStringDefault("p_selGroup", "ALL");
    String  v_sitenm	= "";  
    String  v_url		= "";  
	String  v_isuse		= "";
	String  v_islogin	= "";
    String  v_ldate     = "";    

    int     v_seq		=  0;
    int     v_sort		=  0;
    int     i           =  0;    

	ArrayList list      = null;

	if (box.getString("p_action").equals("go"))  {
	    list = (ArrayList)request.getAttribute("selectList");
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<SCRIPT LANGUAGE="JavaScript">

	function whenSelection(p_action) {
		
        if (document.form1.s_grcode.value == "ALL") {
            alert("교육그룹을 선택하세요.!");
            document.form1.s_grcode.focus();
            return;
        }

		top.ftop.setPam();
			
		document.form1.p_action.value = p_action;
		document.form1.target = "_self";
		document.form1.p_process.value = 'selectList';  
		document.form1.submit();
	}

	function linksite_insert() { 		
        document.form1.target = "_self";
        document.form1.p_process.value = 'insertPage';        
        document.form1.submit();
	}

	function linksite_view(p_grcode, p_seq) { 		
		document.form1.p_grcode.value = p_grcode;
		document.form1.p_seq.value = p_seq;
        document.form1.target = "_self";
        document.form1.p_process.value = 'viewPage';        
        document.form1.submit();
	}


	// 정렬 순서 체크
	function doSortSave(){
		document.form1.target = "_self";
        document.form1.p_process.value = 'updateSort';        
		document.form1.submit();
	}


	// 자동 번호 변환
	var StartNo = "";

	function setStartNo(No) {
		StartNo = No;
	}

	function noSort(No, id) {

		var frm = document.form1;

		for(var i=0; i < frm.p_sort.length; i++) {
			if (i != id ) {
				if (frm.p_sort[i].value == No) {
					frm.p_sort[i].value = StartNo;
				}
			}
		}

		return;
	}

</SCRIPT>    
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post" action="/servlet/controller.homepage.LinkSiteServlet">
    <input type="hidden" name="p_process" value="">
    <input type="hidden" name="p_action"  value="">
	<input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
	<input type="hidden" name="p_seq"	  value="<%=v_seq%>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="/images/admin/homepage/tit_site.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->
      <br>
	   <table width="970" cellpadding="1" cellspacing="0" class="form_table_out">
        <tr> 
          <td bgcolor="#C6C6C6" align="center">
		    <table width="970" cellpadding="0" cellspacing="0" class="form_table_bg" border="0">
              <tr> 
                <td height="7"></td>
              </tr>
              <tr> 
                <td width="90%" align="left"> 
				  &nbsp;<font color="red">★</font>&nbsp;
				  <!-- 교육그룹 시작 -->                                                                                            
                  <%= !v_gadmin.equals("A") ? "<font color=\"red\">★</font>":"" %>                                                 
                                                교육그룹 <kocca_select:select name="s_grcode" sqlNum="course.0001"  param=" "              
                        onChange="whenSelection('go')" attr=" " selectedValue="<%= ss_grcode %>" isLoad="true" all="<%= isAll %>" />
                  <!-- 교육그룹 종료 -->					
				</td>
                <td width="10%" align="center"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
              </tr> 
              <tr> 
                <td height="7"></td>
              </tr>
            </table>
		  </td>
        </tr>
      </table>
	  <br>

     <!-------------소제목 시작-------------------------->
	  <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td></td>
          <td><a href="/servlet/controller.library.DownloadServlet?p_savefile=banner.psd&p_realfile=banner.psd">[샘플배너다운로드]</a></td>
          <td align="right"><a href="javascript:linksite_insert();"><img src="/images/admin/button/btn_add.gif"  border="0"></a> <a href="javascript:doSortSave();"><img src="/images/admin/button/btn_ranking.gif"  border="0"></a></td>
        </tr>
       <tr> 
         <td height=3></td>
         <td height=3></td>
       </tr>
      </table> 
      <!--------------소제목 끝---------------------------->

	  <!----------------- 추가 버튼 끝 ----------------->
      <table width="575" border="0" cellspacing="1" cellpadding="0" class="table_out">
        <tr> 
          <td width="5%" height="20" class="table_title">No</td>
          <td width="10%" class="table_title">순서</td>
          <td width="20%" class="table_title">사이트명</td>
          <td class="table_title">사이트 URL</td>
          <td width="10%" class="table_title">사용여부</td>
          <td width="10%" class="table_title">로그인여부</td>
        </tr>

<%
if (box.getString("p_action").equals("go"))  {

	for( i = 0 ; i < list.size() ; i++ ){
		DataBox dbox = (DataBox)list.get(i);		 
		
		v_grcode	= dbox.getString("d_grcode");  
		v_seq		= dbox.getInt("d_seq");  
		v_sort		= dbox.getInt("d_sort");  
		v_sitenm	= dbox.getString("d_sitenm");  
		v_url		= dbox.getString("d_url");  
		v_isuse		= dbox.getString("d_isuse");  
		v_islogin	= dbox.getString("d_islogin");  
		v_ldate		= dbox.getString("d_ldate");		
%> 
		<input type="hidden" name="grcode" value="<%=v_grcode%>">
		<input type="hidden" name="seq" value="<%=v_seq%>">
		<tr> 
		  <td class="table_title"><%=i+1%></td>
		  <td class="table_02_1">
		  	<select name="p_sort" id=<%=i%> onClick="setStartNo(this.value);" onChange="noSort(this.value, this.id);">
			<% for(int j = 1; j <= list.size(); j++){%>
			  <option value="<%=j%>" <%=(v_sort==j)?"selected":""%>><%=j%></option>
			<%}%>
			</select>
		  </td>
		  <td class="table_02_1"><a href="javascript:linksite_view('<%=v_grcode%>','<%=v_seq%>')"><%=v_sitenm%></a></td>
		  <td class="table_02_2">
				<%if( !v_url.equals("") ){%><a href="<%=v_url%>">
				<%}else{}%><%=v_url%></a></td>
		  <td class="table_02_1"><%=v_isuse%></td>
		  <td class="table_02_1"><%=v_islogin%></td>
		</tr>
<%		
	}
}
%>
	  </table>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
