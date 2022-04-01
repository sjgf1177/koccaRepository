<%
/**
 * file name : za_SuperiorityMember_E.jsp
 * date      :
 * programmer:
 * function  : 우수 회원 대장사 리스트 엑셀 화면
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=za_SuperiorityMember_E.xls");
	  response.setHeader("Content-Description", "JSP Generated Data");
	  
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    int     v_pageno    = box.getInt("p_pageno");
	String v_membergubun = box.getStringDefault("p_membergubun","P");
	String ss_userid	= box.getString("p_userid");
	String ss_username	= box.getString("p_username");
	String v_isunum		= box.getString("p_isunum");

    String  v_userid    =  "";
    String  v_name      =  "";
    String  v_email     =  "";

	String v_dispnum	= "";

    int     v_totalpage =  0;
    int     v_rowcount  =  0;
    int     v_rowspan   =  0;
    int     v_total     =  0;
    int     i           =  0;

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    String  v_orderColumn  = box.getString("p_orderColumn");           //정렬할 컬럼명

    ArrayList list      = null;

    String  ss_action    = box.getString("s_action");
    String  v_ongo      = "whenSelection('go')";


    if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("selectList");
    }

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
	<tr>
		<td align="center" height="30"><font size="3"><b>우수회원 리스트</b></font></td>
	</tr>
  <tr>
    <td align="center" valign="top">
        <!----------------- 과정기준조회 시작 ----------------->
        <table cellspacing="1" cellpadding="5" border="1">
          <tr>
            <td></td>
          </tr>
          <tr>
            <td width="10%"><b>No</b></td>
            <td width="20%"><b>회원구분</b></td>
            <td width="25%"><b>ID</b></td>
            <td width="35%"><b>성명</b></td> 
          </tr>
        <%
            if (ss_action.equals("go")) {    //go button 선택시만 list 출력
                for(i = 0; i < list.size(); i++) 
				{
					DataBox dbox = (DataBox)list.get(i);

                    v_userid        = dbox.getString("d_userid");
                    v_name          = dbox.getString("d_name");
                    v_email         = dbox.getString("d_email");
                    //v_ismailing     = dbox.getString("d_ismailing");
                    v_membergubun	= dbox.getString("d_membergubun");

					v_dispnum		= dbox.getString("d_dispnum");
                    v_totalpage     = dbox.getInt("d_totalpage");
                    v_rowcount      = dbox.getInt("d_rowcount");

					//회원구분
					if(v_membergubun.equals("C")) {v_membergubun = "기업";}
					else if(v_membergubun.equals("U")) {v_membergubun = "대학";}
					else  {v_membergubun = "개인";}

               %>
        <tr>
            <td ><%= (i + 1) %></td>
            <td ><%= v_membergubun %></td>
            <td ><%= v_userid %></a></td>
            <td ><%= v_name %></td>
        </tr>
         <%
               }
            }
         if(i == 0 && ss_action.equals("go")){ %>
              <tr>
                <td class="table_02_1" colspan="4">등록된 내용이 없습니다</td>
              </tr>
        <% } %>
			 </table>
      </td>
  </tr>
</table>
</body>
</html>