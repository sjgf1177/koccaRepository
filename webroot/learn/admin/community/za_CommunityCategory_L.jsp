<%
//**********************************************************
//  1. 제      목: 학습동호회 공지사항
//  2. 프로그램명: za_CommunityNotice_L.jsp
//  3. 개      요: 공지사항 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: mscho 2004. 02. 17
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box  = (RequestBox)request.getAttribute("requestbox");
    int v_commid    = 0;

    String s_username = box.getSession("name");

	String v_code		= "";
	String v_codenm		= "";
	String v_parent_code = "";
	String v_parent_name = "";
	String v_ldate		= "";
	String v_levels		= "";

	String arr_tmp1		= "";
	String arr_tmp2		= "";
    
    ArrayList list = (ArrayList)request.getAttribute("selectCommunityCategoryList");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/cresys_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
//alert("<%=arr_tmp1%>");

//alert("<%=arr_tmp2%>");
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.community.CommunityAdminCategoryServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.community.CommunityAdminCategoryServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }
    function selectList() {
        document.form1.action = "/servlet/controller.community.CommunityAdminCategoryServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }
    function insertPage() {
		//if (chkParam() == false) {
		//  return;
		//}
        document.form1.action = "/servlet/controller.community.CommunityAdminCategoryServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
    function select(code, levels) {
        document.form1.action = "/servlet/controller.community.CommunityAdminCategoryServlet";
        document.form1.p_process.value = "select";
        document.form1.p_code.value = code;
        document.form1.p_levels.value = levels;
        document.form1.submit();
    }

	//검색
	function whenGrtype(p_action) {
	  document.form1.action='/servlet/controller.community.CommunityAdminCategoryServlet';
	  document.form1.p_process.value = 'selectList';    
	  document.form1.submit();
	}

	// 검색조건 체크
	function chkParam() {
	  if (document.form1.p_grtype.value == '' || document.form1.p_grtype.value == '----' || document.form1.p_grtype.value == '') {
		alert("교육그룹을 선택하세요.");
		return false;
	  }
	}

	function openCategory(p) {
	    var cateObj = document.getElementsByName(p);
	    var objLen  = cateObj.length;

	    for(var i = 0; i < objLen ; i++) {
	        if(cateObj[i].style.display == "none") {
	        	cateObj[i].style.display = "block";
	        } else {
	        	cateObj[i].style.display = "none";
	        }
	    }
	    
	}

//-->
</SCRIPT>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="">
	<input type="hidden" name="p_process">
	<input type="hidden" name="p_commId"	value='0'>
	<input type="hidden" name="p_action"	value="">
	<input type="hidden" name="p_codenm"    value = "<%=v_codenm %>">
	<input type="hidden" name="p_code"      value = "">
	<input type="hidden" name="p_levels"    value = "">
	<input type="hidden" name="p_gubun"		value = "0052">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
      <td align="center" valign="top">
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="../../../images/admin/community/tit_comm_06.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->
        <br>
        <br>

        <!----------------- 리스트, 추가 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr> 
			<td align="left"><!-- 
			<% if (StringManager.substring(box.getSession("gadmin"), 0, 1).equals("A")) { %> 
				<%=CodeConfigBean.getCodeGubunSelect("0060","",1,"p_grtype",box.getString("p_grtype"),"onChange=\"whenGrtype()\"",3) %>
			<% } else  {                                                   %>
					<%//=CodeConfigBean.getCodeName("grtype",box.getSession("grtype")) %>
					<input type="hidden" name="p_grtype" value="<%=box.getSession("grtype")%>">
			 <% } %> -->
			</td>
            <td align="right" width="100%">
				<a href = "javascript:insertPage();"><img src="/images/admin/button/btn_addplus.gif"  border="0"></a>
            </td>
          </tr>
          <tr> 
            <td colspan="2" height="3"></td>
          </tr>
        </table>
        <!----------------- 리스트, 추가 버튼 끝 ----------------->

        <!----------------- 과정 자료실  리스트 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="5" class="table_top_line"></td>
          </tr>
          <tr> 
            <td class="table_title" width="5%"><b>NO</b></td>
            <td class="table_title"><b>코드명</b></td>
            <td class="table_title" width="15%"><b>Levels</b></td>
            <td class="table_title" width="15%"><b>등록일</b></td>
          </tr>
		  
	       <%
			
			String temp_parent = "";
	       
	       int num = 1;
			
			for(int i = 0; i < list.size(); i++) {
			    DataBox dbox = (DataBox)list.get(i); 
			
			    v_code          = dbox.getString("d_code");
			    v_codenm        = dbox.getString("d_codenm");
			    v_levels        = dbox.getString("d_levels");       
			    v_ldate         = dbox.getString("d_ldate");;       
			    v_parent_code   = dbox.getString("d_parent_code");
			    v_parent_name   = dbox.getString("d_parent_name");
			
			    if(i == 0 || !temp_parent.equals(v_parent_code)) {
			    	num = 1;
			%>
			<tr style="cursor:pointer;" onclick="openCategory('<%=v_parent_code %>')"> 
            <td class="table_01" width="5%"><b> * </b></td>
            <td class="table_02_2"><b><%=v_parent_name %></b></td>
            <td class="table_02_1" align="center" width="15%"><b> 대분류</b></td>
            <td class="table_02_1" align="center" width="15%"><b>-</b></td>
          </tr>
             <%    }   %>
		   	
          <tr id="<%=v_parent_code %>" style="display:none;"> 
            <td class="table_01"><%=num%></td>
            <td class="table_02_2"><a href="javascript:select('<%=v_code%>', '<%=v_levels%>')"><%=v_codenm%></a>
              <% if(FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_ldate, "yyyyMMdd"))){ %>
                 &nbsp;&nbsp;<img src = "/images/community/ico_new.gif" align = "absmiddle" border = "0">
              <%   }    %>            
            </td>
			<td class="table_02_1" align="center"><%= v_levels%></td>
            <td class="table_02_1" align="center"><%= FormatDate.getFormatDate(v_ldate, "yyyy/MM/dd") %></td>
          </tr>
          <% if( (i+1) == list.size() || !(((DataBox)list.get(i+1)).getString("d_parent_code")).equals(v_parent_code)) {  %>  
          
		<%
          } else num++;
		temp_parent = v_parent_code;
		
			}%>
        </table>
        <!----------------- 과정 자료실  리스트 끝 ----------------->
        <br>
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
