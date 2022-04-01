<%
//**********************************************************
//  1. 제      목: 접속통계
//  2. 프로그램명 : za_MenuCount_L.jsp
//  3. 개      요: 접속통계 - 메뉴별통계 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_process = box.getString("p_process");
    String v_grcode		= "";
    String v_gubun		= "";
	String v_gubunname	= "";
    String v_menuid		= "";
	String v_menuname	= "";
	String v_subjnm     = "";
	String v_gubun1 = "";
	int j=1;
    int    v_cnt		= 0;
	int	   v_gubuncnt	= 0;
	int	   v_gubuntot	= 0;

    ArrayList list1 = (ArrayList)request.getAttribute("selectList1");

    String v_tab_color1 = "blue";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
	String v_tab_color4 = "black";
	String v_tab_color5 = "black";
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
	//탭이동
	function changeTabpage(p_gubun) {
        document.form1.p_process.value = "selectMonthDay";
		if (p_gubun == "HOME") {
		  document.form1.action = "/servlet/controller.system.CountServlet";

		} else if (p_gubun == "SUBJ") {
		  document.form1.action = "/servlet/controller.system.SubjCountServlet";
		
		} else if (p_gubun == "MENU") {
		  document.form1.action = "/servlet/controller.system.MenuCountServlet";
		} else if(p_gubun == "PREVIEW"){
		  document.form1.action = "/servlet/controller.system.MenuCountServlet";		
		  document.form1.p_process.value = "selectPreviewCount";
		}
		  document.form1.p_gubun.value = p_gubun;
		  document.form1.submit();
	}
	
    function whenSelection(p_action) {
        if (p_action=="go"){
            if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
                alert("교육그룹을 선택하세요.");
                return ;
            }
            top.ftop.setPam();
        }
        document.form1.target = "_self";
        document.form1.s_action.value = p_action;
        document.form1.action = "/servlet/controller.system.MenuCountServlet";
        document.form1.p_process.value = "selectPreviewCount";
        document.form1.submit();
	}

-->
</SCRIPT>

</head>






<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"    value = "<%= v_process %>">
    <input type = "hidden" name = "p_gubun"   value = "">
    <input type = "hidden" name = "p_grcode"   value = "">
    <input type = "hidden" name = "s_action"   value = "">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td background="/images/admin/system/title_bg.gif" class="page_title"><img src="/images/admin/system/system_title6.gif"  border="0"></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <table width="50%" cellspacing="0" cellpadding="0" class="table1" border="0">
        <tr>
          <td width="120" height="23" align="left" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_left"></td>
                <td class="<%=v_tab_color3%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_middle"><a href="javascript:changeTabpage('HOME')" class="c">접속통계</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="120" height="23" align="left" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_left"></td>
                <td class="<%=v_tab_color3%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_middle"><a href="javascript:changeTabpage('SUBJ')" class="c">과정별 접속통계</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="120" height="23" align="left" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_left"></td>
                <td class="<%=v_tab_color3%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_middle"><a href="javascript:changeTabpage('MENU')" class="c">메뉴별 접속통계</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="120" height="23" align="left" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color1%>_butt_left"></td>
                <td class="<%=v_tab_color1%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color1%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color1%>_butt_middle">맛보기카운트</td>
              </tr>
              <tr>
                <td class="<%=v_tab_color1%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="55%"></td>
		</table>
		<br>

      <!----------------- 검색조건 form 끝 ----------------->
	  <table cellspacing="0" cellpadding="1" class="form_table_out">
		<tr>
		  <td bgcolor="#C6C6C6" align="center">
			<table cellspacing="0" cellpadding="0" class="form_table_bg" >
			  <tr>
				<td height="7"></td>
			  </tr>
			  <tr>
				<td align="center">
				  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
					<tr>
					  <td width="97%" colspan="2" align="left" valign="middle">
						<!------------------- 조건검색 시작 ------------------------->
						<font color="red">★</font> <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
					  </td>
                      <td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
					</tr>
				  </table>
				</td>
			  </tr>
			  <tr>
				<td height="9"></td>
			  </tr>
			</table>
		  </td>
		</tr>
	  </table>
      <!----------------- 검색조건 form 끝 ----------------->
	  <br>

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><font face="굴림" size="3.5">◈<b>맛보기 접속통계</font></b></td>
          </tr>
        </table>
        <br>
        <!----------------- 시간대별 접속자수 끝 ----------------->
        <table width="97%" border="1" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="3" height="3" bgcolor="#0075AD"></td>
          </tr>
          <tr>
            <td class="table_title" width="30%" height="25"><b>과정</b></td>
            <td class="table_title" width="30%" height="25"><b>접속횟수</b></td>
	      </tr>


<%
      int v_tot = 0;
      //out.println(list1.size());
      
      for(int i = 0; i < list1.size(); i++) {
		DataBox dbox = (DataBox)list1.get(i);
		
		v_subjnm = dbox.getString("d_subjnm");
		v_cnt = dbox.getInt("d_cnt");
		v_tot += v_cnt;
%>
          <tr>
            <td class="table_02_1" align="left"><%=v_subjnm%>1</td>
			<td class='table_02_1'><%=v_cnt%></td>
          </tr>
<%
}	// form end
%>
        </table>
       <br>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
