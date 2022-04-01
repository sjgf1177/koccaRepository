<%
//**********************************************************
//  1. 제      목: HomePage FAQ Category 리스트화면
//  2. 프로그램명: za_HomePageFaqCategory_L.jsp
//  3. 개      요: HomePage FAQ Category 리스트화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 7. 1
//  7. 수      정: 이연정 2005. 7. 1
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.system.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    ArrayList list = (ArrayList)request.getAttribute("selectListHomePageFaqCategory");

    String  s_gadmin		 = box.getSession("gadmin");
    String  s_userid		 = box.getSession("userid");
    String  ss_grcode        = box.getString("s_grcode");            //교육그룹
    String  v_gadmin         = StringManager.substring(s_gadmin, 0, 1);
    String  isAll            = !v_gadmin.equals("A") ? "false" : "true";
    String  v_selGroup       = box.getStringDefault("p_selGroup", "ALL");

	String  v_faqcategory    = "";
    String  v_faqcategorynm  = "";
    String  v_orderType      = box.getStringDefault("p_orderType"," asc"); //정렬순서
			v_faqcategorynm	 = box.getString("p_faqcategorynm2");
    String  v_grcode		 = box.getStringDefault("p_grcode", "N000001");

	// 교육그룹
    ArrayList list1 = (ArrayList)request.getAttribute("GrcodeList");

%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<SCRIPT LANGUAGE="javascript"><!--
	function selectListAction() {
        document.form1.action = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
		document.form1.p_grcode.value = document.form1.s_grcode.value;
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function insertPageAction() {
        document.form1.action = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
		document.form1.p_grcode.value = document.form1.s_grcode.value;
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
	
	 function updatePageAction(faqcategory) {
        document.form1.action = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
        document.form1.p_faqcategory.value = faqcategory;
		document.form1.p_grcode.value = document.form1.s_grcode.value;
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }
	
	function faqListAction(faqcategory) {
        document.form1.action = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
        document.form1.p_faqcategory.value = faqcategory;
        document.form1.p_grcode.value = document.form1.s_grcode.value;
        document.form1.p_process.value = "faqList";

        if(document.form1.s_grcode.value == "ALL") {
            alert("교육 그룹을 선택해주세요");
            return;
        }

        document.form1.submit();
    }
	function search(){
		document.form1.p_grcode.value = document.form1.s_grcode.value;
		document.form1.p_process.value= "selectList";
		document.form1.action = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
		document.form1.submit();
	}
// 정렬
function ordering(column) {
    if (document.form1.p_orderType.value == " asc") {
        document.form1.p_orderType.value = " desc";
    } else {
        document.form1.p_orderType.value = " asc";
    }
	document.form1.p_grcode.value = document.form1.s_grcode.value;
    document.form1.p_order.value = column;
    selectListAction();
}

	function whenSelection(action){

		if(document.form1.s_grcode.value != "ALL") {
			document.form1.p_grcode.value = document.form1.s_grcode.value;
	        document.form1.p_process.value= "selectList";
	        document.form1.action = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
	        document.form1.submit();
        }
	}

--></SCRIPT>

</head>

<body topmargin="0" leftmargin="0" bgcolor="#FFFFFF">
		<form  name = "form1"  method = "post">
		<input type = "hidden" name = "p_process"     value = "">
		<input type = "hidden" name = "p_faqcategory" value = "">
		<input type = "hidden" name = "p_orderType"   value = "<%=v_orderType%>">
        <input type = "hidden" name = "p_order"       value = "">
        <input type = "hidden" name = "p_grcode" >
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/homepage/h_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	   <br>     
      <table width="970"  border="0" cellpadding="0" cellspacing="0">
        <tr>
		  <td width="250"> 
            <!-- 교육그룹 시작 -->                                                                                            
		    <%= !v_gadmin.equals("A") ? "<font color=\"red\">★</font>":"" %>                                                 
		                           교육그룹 <kocca_select:select name="s_grcode" sqlNum="course.0001"  param=" "              
		          onChange="whenSelection('go')" attr=" " selectedValue="<%= ss_grcode %>" isLoad="true" all="<%= isAll %>" />
		    <% //교육그룹 관리자는 ALL 검색이 안되게 설정 %>                                                                  
		    <%/*if (!v_gadmin.equals("A")) { %>                                                                               
		    <font color="red">★</font>                                                                                       
		        <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)    교육그룹  -->   
		    <% } else { %>                                                                                                    
		        <%= SelectEduBean.getGrcode(box, true, true)%><!-- getGrcode(RequestBox, isChange, isALL) 교육그룹  -->       
		    <% } */%>                                                                                                         
		    <input type="hidden" name="s_grseq" value="ALL">                                                                  
		    <!-- 교육그룹 종료 -->    
          </td>

		  <td width="722" align="right" class=txt_input>FAQ 카테고리명 검색 : </td>
          <td width="150">
			  <input name="p_faqcategorynm2" type="text" class="input" style="width:150" value="<%=v_faqcategorynm%>"></td>
          <td width="10">&nbsp;</td>
          <td width="32" align="right">
			  <a href="javascript:selectListAction()" onfocus=this.blur()>
			  <img src="/images/admin/button/btn_inquiry.gif" border = "0"></td>
          <td width="14">&nbsp;</td>
          <td width="32" align="right">
			  <a href="javascript:insertPageAction()" onfocus=this.blur()>
			  <img src="/images/admin/button/btn_add.gif" border = "0"></td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
          <td height=6></td>
          <td align="right" height=6></td>
          <td width="14" height=6></td>
          <td align="right" height=6></td>
        </tr>
      </table>
      <table width="970"  cellpadding="0" cellspacing="1" class="table_out">
        <tr> 
          <td colspan="3" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title" width="14%"><a href="javascript:ordering('faqcategory')" class="e">FAQ 카테고리코드</a></td>
          <td class="table_title" width="80%"><a href="javascript:ordering('faqcategorynm')" class="e">FAQ 카테고리명</a></td>
		  <td class="table_title" width="6%">수정</td>
        </tr>
<%if(list.size() != 0){%>
<%
            for(int i = 0; i < list.size(); i++) {

				DataBox dbox = (DataBox)list.get(i);
				
				v_faqcategory = dbox.getString("d_faqcategory");
				v_faqcategorynm = dbox.getString("d_faqcategorynm");
%>
        <tr> 
          <td class="table_01"><%=v_faqcategory%></td>
          <td class="table_02_2"><a href="javascript:faqListAction('<%=v_faqcategory%>')"><%=v_faqcategorynm%></a></td>
		  <td class="table_01"><a href="javascript:updatePageAction('<%=v_faqcategory%>')">
<img src="/images/admin/button/b_modify.gif" border = "0"></td>
        </tr>
<%
        }
%>
<%}else{%>
           

                                                    <tr>
                                                        <td class="table_01" colspan="3" align=center >
															등록된 FAQ카테고리가 없습니다.		
														</td>
													</tr>

<%}%>		
      </table></td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
