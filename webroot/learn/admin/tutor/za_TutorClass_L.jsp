<%
//**********************************************************
//  1. 제      목: 강사분반현황
//  2. 프로그램명: za_TutorClass_L.jsp 
//  3. 개      요: 강사분반현황 조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.tutor.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");

	String  v_tuuserid  =  "";
	String  v_tuusernm  =  "";
    int     i           =  0;
    int     v_stucnt	=  0;

    String  v_searchday		= box.getString("p_searchday");            //검색일
    String  ss_action		= box.getString("s_action");
    
	String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

    ArrayList list      = null;
    //DEFINED class&variable END

    if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("TutorClassList");
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "javascript">
$(document).ready(function(){
	$("#p_searchday").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--          
    function whenSelection(p_action) {
	    if (p_action=="go"){
            if(document.form1.p_searchday.value == '') {
                alert("검색기준일을 입력하세요.");
                return;
            }
		}

        document.form1.s_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.tutor.TutorAdminServlet';
        document.form1.p_process.value = 'TutorClassList';  
        document.form1.submit();
    }
    
   
    function whenOrder(column) {
		if (document.form1.p_orderType.value == " asc") {
			document.form1.p_orderType.value = " desc";
		} else {
			document.form1.p_orderType.value = " asc";
		}

        document.form1.target = "_self";
        document.form1.action='/servlet/controller.tutor.TutorAdminServlet';
        document.form1.p_process.value = 'TutorClassList';   
        document.form1.p_orderColumn.value = column;
        document.form1.submit();
    }    
-->    
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type="hidden" name="p_process" value="">
    <input type="hidden" name="p_pageno" value="">    
    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->   
    <input type="hidden" name="p_orderColumn">
	<input type="hidden" name="p_orderType" value="<%=v_orderType%>">

	
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 

	  <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
            <td><img src="/images/admin/tutor/tit_tutor_be.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

	  <table cellspacing="0" cellpadding="1" class="form_table_out">
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
                      <td>
						<font color="red">★</font> 검색기준일 : <input tpye="text" name="p_searchday" id="p_searchday" class="datepicker_input1" size=10 value="<%=v_searchday%>">
					  </td>
                      <td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
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
      <br>  

	  <!----------------- 분반현황조회 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
          <tr> 
            <td colspan="5" class="table_top_line"></td>
          </tr>
          <tr> 
            <td class="table_title">No</td>
            <td class="table_title"><a href="javascript:whenOrder('tuserid')" class="e">강사아이디</a></td>
            <td class="table_title"><a href="javascript:whenOrder('tusernm')" class="e">강사명</a></td>
            <td class="table_title"><a href="javascript:whenOrder('stucnt')" class="e">학생수</a></td>
          </tr>

<%                          
	if (ss_action.equals("go")) {    //go button 선택시만 list 출력
						  
		for( i = 0 ; i < list.size() ; i++ ){
			DataBox dbox = (DataBox)list.get(i);		  
					
			v_tuuserid	= dbox.getString("d_tuserid");
			v_tuusernm	= dbox.getString("d_tusernm");
			v_stucnt	= dbox.getInt("d_stucnt");					
%>
			        <tr> 
			            <td class="table_02_1"><%=list.size()-i%></td>
			            <td class="table_02_1"><%=v_tuuserid%></td>
			            <td class="table_02_1"><%=v_tuusernm%></td>
			            <td class="table_02_1"><%=v_stucnt%></td>
			         </tr>
<%					
		}   
	}
%>
<%if(i == 0 && ss_action.equals("go")){ %>
          <tr>
            <td class="table_02_1" colspan="4">등록된 내용이 없습니다</td>
          </tr>
<%}%>
      </table>      
      <!----------------- 입과명단 조회 끝 ----------------->
     <br>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>      
</table>
</form>

</body>
</html>
