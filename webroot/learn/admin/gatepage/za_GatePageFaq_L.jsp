<%
//**********************************************************
//  1. 제      목: GatePage FAQ
//  2. 프로그램명 : za_GatePageFaq_L.jsp
//  3. 개      요: GatePage FAQ 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 신선철 2005. 1. 1
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.gatepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	
    ArrayList list = (ArrayList)request.getAttribute("selectListGatePageFaq");

	int  v_fnum  = 0;
    String  v_title  = "";
	String  v_contents = "";
    String  v_indate   = "";
	String s_gubun = "";

	String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");
	
    String v_faqcategory = box.getString("p_faqcategory");
	s_gubun = box.getString("p_gubun");
	System.out.println("za_GatePageFa_L.jsp s_gubun ===============   = " + s_gubun);

%>

<html>
<head>
<title>GateSystemFAQ</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<SCRIPT LANGUAGE="javascript">
function changeCategory() {
        document.form1.action = "/servlet/controller.gatepage.GatePageFaqCategoryAdminServlet";

        document.form1.p_searchtext.value = "";
        document.form1.p_search.value     = "";
        document.form1.p_process.value    = "faqList";
        document.form1.submit();
    }

function faqListAction() {
        document.form1.action = "/servlet/controller.gatepage.GatePageFaqCategoryAdminServlet";
        document.form1.p_process.value = "faqList";
        document.form1.submit();
    }

function insertPage2Action() {
	    if(document.form1.p_faqcategory.value == ""){
			alert("FAQ 카테고리를 선택하셔야 등록하실 수 있습니다!");
			return;
		}
        document.form1.action = "/servlet/controller.gatepage.GatePageFaqCategoryAdminServlet";
        document.form1.p_process.value = "insertPage2";
        document.form1.submit();
    }

function updatePage2Action(fnum) {
        document.form1.action = "/servlet/controller.gatepage.GatePageFaqCategoryAdminServlet";
		document.form1.p_fnum.value = fnum;
        document.form1.p_process.value = "updatePage2";
        document.form1.submit();
    }

</script>

</head>

<body topmargin=0 leftmargin=0>
		<form name = "form1" method = "post">
		<input type = "hidden" name = "p_process" value = "">
		<input type = "hidden" name = "p_fnum" value = "">
		<input type = "hidden" name = "p_gubun" value = "<%=s_gubun%>">
		


<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/gatepage/cp_title07.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	   <br>     
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
		  <td width="154"  class=txt_input>FAQ 카테고리명 검색 : </td>
          <td width="378"><%=GatePageFaqCategoryAdminBean.getFaqCategorySelecct("p_faqcategory", v_faqcategory, "onChange=\"javascript:changeCategory()\"", 1,box)%></td>
		  <td align="right"> 
              <select name="p_search" >
                <option value='title'    <% if (v_search.equals("title")) out.println("selected"); %>>제목</option>
                <option value='contents' <% if (v_search.equals("contents")) out.println("selected"); %>>내용</option>
              </select>
			 
              <input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>">
			   <td width="10">&nbsp;</td>
            </td>

          <td width="35" align="right">
			  <a href='javascript:faqListAction()'>
			  <img src="/images/admin/button/btn_inquiry.gif" border = "0"></td>
          <td width="8">&nbsp;</td>
			  <td width="50" align="right">
			  <a href='javascript:insertPage2Action()'>
			  <img src="/images/admin/button/btn_apply.gif" border = "0"></td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
          <td height=6></td>
          <td align="right" height=6></td>
          <td width="10" height=6></td>
          <td align="right" height=6></td>
        </tr>
      </table>
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
        <tr> 
          <td class="table_title" width="14%">FAQ 번호</td>
          <td class="table_title"  width="80%">FAQ 제목</td>
		  <td class="table_title" width="6%">수정</td>
        </tr>
<%if(list.size() != 0){%>
<%
            for(int i = 0; i < list.size(); i++) {

				DataBox dbox = (DataBox)list.get(i);
				
				v_fnum = dbox.getInt("d_fnum");
				v_title = dbox.getString("d_title");
%>

        <tr> 
          <td class="table_01"><%=v_fnum%></td>
          <td class="table_02_2"><%=v_title%></td>
		  <td class="table_01"><a href="javascript:updatePage2Action('<%=v_fnum%>')"><u>
		  <font color='blue'>수정</font></u></td>
        </tr>
<%
        }
%>       
<%}else{%>
           

                                                    <tr>
                                                        <td colspan="13" align=center >
															등록 된 FAQ 없습니다.		
														</td>
													</tr>

<%}%>
      </table>
      
    </td>
  </tr>
</table>
</form>
</body>
</html>
