<%
//**********************************************************
//  1. 제      목: HomePage FAQ
//  2. 프로그램명 : za_HomePageFaq_L.jsp
//  3. 개      요: HomePage FAQ 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 신선철 2005. 1. 1
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	
    ArrayList list = (ArrayList)request.getAttribute("selectListHomePageFaq");

	int  v_fnum  = 0;
    String  v_title  = "";
	String  v_contents = "";
    String  v_indate   = "";

	String  v_grcode	  = box.getString("p_grcode");
	String  v_grcodenm	  = GetCodenm.get_grcodenm(box, v_grcode);
	String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");	
    String  v_faqcategory = box.getString("p_faqcategory");
    String  v_orderType   = box.getStringDefault("p_orderType"," asc"); //정렬순서
    String  v_order       = box.getString("p_order"); //정렬순서
    
	//String v_grcode = box.getSession("grcode");
    
%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<SCRIPT LANGUAGE="javascript">
function changeCategory() {
        document.form1.action = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";

        document.form1.p_searchtext.value = "";
        document.form1.p_search.value     = "";
        document.form1.p_process.value    = "faqList";
        document.form1.submit();
    }

function faqListAction() {
        document.form1.action = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
        document.form1.p_process.value = "faqList";
        document.form1.submit();
    }

function insertPage2Action() {
	    if(document.form1.p_faqcategory.value == ""){
			alert("FAQ 카테고리를 선택하셔야 등록하실 수 있습니다!");
			return;
		}
        document.form1.action = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
        document.form1.p_process.value = "insertPage2";
        document.form1.submit();
    }

function updatePage2Action(fnum) {
        document.form1.action = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
		document.form1.p_fnum.value = fnum;
        document.form1.p_process.value = "updatePage2";
        document.form1.submit();
    }
// 정렬
function ordering(column) {

    if (document.form1.p_orderType.value == " asc") {
        document.form1.p_orderType.value = " desc";
    } else {
        document.form1.p_orderType.value = " asc";
    }
    document.form1.p_order.value = column;
    faqListAction();
}
//첫목록으로
function goCategoryList() {
        document.form1.action = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.p_searchtext.value = "";
        document.form1.p_search.value     = "";
        document.form1.p_process.value    = "selectList";
        document.form1.submit();
    }
</script>

</head>

<body topmargin="0" leftmargin="0" bgcolor="#FFFFFF">
		<form  name = "form1"  method = "post">
		<input type = "hidden" name   = "p_process"   value = "">
		<input type = "hidden" name   = "p_fnum"      value = "">
		<input type = "hidden" name   = "p_orderType" value = "<%=v_orderType%>">
        <input type = "hidden" name   = "p_order"     value = "<%=v_order %>">
        <input type = "hidden" name = "p_grcode" value="<%=v_grcode%>">
        <input type = "hidden" name = "s_grcode" value="<%=v_grcode%>">
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
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr>
		  <td width="70"  class=txt_input align="left">교육그룹 : </td>
          <td width="100"><%=v_grcodenm%></td>
		  <td width="150"  class=txt_input>FAQ 카테고리명 검색 : </td>
          <td ><%=HomePageFaqCategoryAdminBean.getFaqCategorySelecctNew(v_grcode,"p_faqcategory", v_faqcategory, "onChange=\"javascript:changeCategory()\"", 1)%></td>
		  <td width="100" align="right"> 
              <select name="p_search" >
                <option value='title'    <% if (v_search.equals("title")) out.println("selected"); %>>제목</option>
                <option value='contents' <% if (v_search.equals("contents")) out.println("selected"); %>>내용</option>
              </select>
		  </td>
          <td width="35" align="right">	
              <input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>">
			   <td width="10">&nbsp;</td>
            </td>
          <td width="35" align="right">
			  <a href='javascript:faqListAction()'>
			  <img src="/images/admin/button/btn_inquiry.gif" border = "0"></td>
          <td width="4">&nbsp;</td>
		  <td width="50" align="right" style="padding-top:4px;">
			<a href='javascript:insertPage2Action()'>
			<img src="/images/admin/button/btn_add.gif" border="0">
		  </td>
          <td width="4">&nbsp;</td>
		  <td  style="padding-top:4px;">
			<a href="javascript:goCategoryList()"><img src="/images/admin/button/btn_first_list.gif" border="0"></a>
		  </td>
        </tr>
        <tr> 
          <td colspan="11" height=6></td>
        </tr>
      </table>
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
        <tr> 
          <td class="table_title" width="14%"><a href="javascript:ordering('fnum')" class="e">FAQ 번호</a></td>
          <td class="table_title"  width="80%"><a href="javascript:ordering('title')" class="e">FAQ 제목</a></td>
		  <td class="table_title" width="6%">수정</td>
        </tr>
<%if(list.size() > 0){%>
<%
            for(int i = 0; i < list.size(); i++) {

				DataBox dbox = (DataBox)list.get(i);
				
				v_fnum = dbox.getInt("d_fnum");
				v_title = dbox.getString("d_title");

				if (!v_searchtext.equals("")&&v_search.equals("title")) {
					v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				} 
%>

        <tr> 
          <td class="table_01"><%=v_fnum%></td>
          <td class="table_02_2"><%=v_title%></td>
		  <td class="table_01"><a href="javascript:updatePage2Action('<%=v_fnum%>')"><img src="/images/admin/button/b_modify.gif" border = "0"></td>
        </tr>
<%
        }
%>       
<%}else{%>
           

        <tr>
            <td class="table_01" colspan="13" align=center >
				등록 된 FAQ가 없습니다.		
			</td>
		</tr>

<%}%>
      </table>
      
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
