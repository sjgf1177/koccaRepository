<%
//**********************************************************
//  1. 제      목: 운영자별 메뉴 권한
//  2. 프로그램명 : za_AdminAuth_L.jsp
//  3. 개      요: 운영자별 메뉴 권한을 설정
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 16
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  		= box.getString("p_process");
    String  v_grcode   		= box.getString("p_grcode");
    String  v_userid   		= box.getString("p_userid");
    String  v_gadminview 	= box.getString("p_gadminview");
    int  	v_gadmincnt  	= StringManager.toInt(box.getString("p_gadmincnt"));  	// 권한갯수
    String 	v_gadminnms		= box.getString("p_gadminnms");							// 운영자별 권한명
    ArrayList list1   		= (ArrayList)request.getAttribute("selectList1");     	// 권한리스트
    ArrayList list2   		= (ArrayList)request.getAttribute("selectList2");     	// 메뉴별권한 리스트

    String 	 v_gadminnm    = box.getString("p_gadminnm"); 							// 권한명
    String[] v_gadmin    = new String[v_gadmincnt];       							// 권한아이디

	if (v_gadminnm.equals("")) v_gadminnm = "운영자메뉴권한";						//운영자 메뉴 권한 선택시 보이게 될 필드명

    int    v_levels      = 0;                        // 레벨
    int    v_seq         = 0;                        // seq
    String v_gubun       = "";                       // 구분 (대메뉴, 소메뉴, 모듈)
    String v_menu        = "";                       // 메뉴
    String v_menunm      = "";                       // 메뉴명
    String v_control_org = "";                       // 권한(읽기/쓰기)
    String[] v_control   = new String[v_gadmincnt];  // 권한(읽기/쓰기)

    String v_rchecked    = "";                       // 읽기권한 체크
    String v_wchecked    = "";                       // 쓰기권한 체크

    String v_classcenter = "";                       // td class 지정(중앙정렬)
    String v_classleft   = "";                       // td class 지정(왼쪽정렬)
    String v_classright  = "";                       // td class 지정(오른쪽정렬)
    int    v_menucount   = 0; 
    
    String v_cono		 = "";						 // 사번
    String v_name		 = "";						 // 성명
    String v_jikwinm	 = "";						 // 직위 이름
    String v_compnm		 = "";						 // 회사명
    
    // 관리자 기본정보
    MenuAuthAdminData data = (MenuAuthAdminData)request.getAttribute("selectManager");

    v_cono       = data.getCono();
    v_name       = data.getName();
    v_jikwinm    = data.getJikwinm();
    v_compnm     = data.getCompnm();
    

    StringTokenizer st = new StringTokenizer(v_gadminnms,",");

    int kk = st.countTokens();
    String v_adminnm[] = new String[kk+1];
    int ttm = 0;
    while (st.hasMoreTokens()) {
		v_adminnm[ttm++]  = st.nextToken();
	}
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
	//운영자별 메뉴권한 설정 저장
    function insert() {
        document.form1.action = "/servlet/controller.system.MenuAuthAdminEachServlet";
        document.form1.p_process.value = "update";
        document.form1.submit();
    }

	//권한 설정에 따른 그룹/과정/회사/부서 등록 화면 디스플레이
    function changeGadmin() {
		document.form1.action = "/servlet/controller.system.MenuAuthAdminEachServlet";
		document.form1.p_process.value = "updatePage";
		document.form1.submit();
    }
    //운영자별 메뉴권한설정 리스트 화면으로 이동
    function list() {
        document.form1.action = "/servlet/controller.system.MenuAuthAdminEachServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }
    
//-->
</SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name="form1" method="post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_grcode"  value = "<%= v_grcode %>">
    <input type = "hidden" name = "p_userid"  value = "<%= v_userid %>">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/unite_title06.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
    </td>
  </tr>
  <tr>
  	<td height="20">&nbsp;</td>
  </tr>
  <!-------------------운영자 신상정보 소속,직위,권한,사번,성명 보기------------------------->
  <tr>
  	  <td width="500" align="center" valign="top">
  	      <table border="0" cellpadding="0" cellspacing="0">
  	      	  <tr height="30"><td>&nbsp;</td></tr>
      		  <tr>
      		  	 <td align="center">
      			     <table border="0" class="table_out" cellpadding="0" cellspacing="1">
      			         <tr>
      			     	     <td width="80" class="table_title">소속</td>
      			     		 <td width="420" class="table_02_8"><%=v_compnm%></td>
      			     	 </tr>
      			     	 <tr>
      			     		 <td class="table_title">직위</td>
      			     		 <td class="table_02_8"><%=v_jikwinm%></td>
      			     	 </tr>
      			     	 <tr>
      			     	  	 <td class="table_title">권한</td>
      			     	  	 <td class="table_02_8"><%=v_gadminnms%></td>
      			     	 </tr>
      			     	 <tr>
      			     	  	 <td class="table_title">사번</td>
      			     	  	 <td class="table_02_8"><%=v_cono%></td>
      			     	 </tr>
      			     	 <tr>
      			     	  	 <td class="table_title">성명</td>
      			     	  	 <td class="table_02_8"><%=v_name%></td>
      			     	 </tr>
      			     </table>
      			 </td>
      		</tr>
      	</table>
    </td>

  <!----------------------운영자 신상정보 보기 여기까지 --------------------->

  	<td valign="top" align="center">
        <!----------------- 저장, 메뉴코드리스트 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
            <tr>
          		<td width="50%" align="right"><strong>권한템플릿</strong></td>
          		<td width="30%" align="center" height="25">
             	<%= GadminAdminBean.getGadminSelectOnly("p_gadminview",v_gadminview,"onChange=\"changeGadmin()\"") %>
          		</td>
        		<td width="10%" align="center"><a href='javascript:insert()'><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
        		<!--td width="10%" align="right" width="99"><a href='/servlet/controller.system.MenuAdminServlet?p_process=select&p_grcode=0000001'><img src="/images/admin/system/menu_code1_butt.gif" width="93" height="18" border="0"></a></td-->
        		<td width="10%" align="center"><a href='javascript:list()'><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
        	</tr>
        	<tr>
          		<td height="3"></td>
        	</tr>
      	</table>
      	<!----------------- 저장, 메뉴코드리스트 버튼 끝 ----------------->

      	<!----------------- 운영메뉴관리 시작 ----------------->
      	<table class="table_out" cellspacing="1" cellpadding="0">
        	<tr>
          		<td colspan="<%=(v_gadmincnt + 2)%>" class="table_top_line"></td>
        	</tr>
        	<tr>
          		<td height="50" class="table_title" rowspan="2"><b>구분</b></td>
          		<td class="table_title" rowspan="2"><b>메뉴코드명</b></td>
          		<td class="table_title" colspan="<%=v_gadmincnt%>" height="25"><b>권한</b></td>
        	</tr>
        	<tr>
				<td class="table_title" height="25"><b><%=v_gadminnm%></b></td>
		  	</tr>
		  	<tr>
<%

       //메뉴 리스트
        for(int i = 0; i < list2.size(); i++) {
			DataBox dbox = (DataBox)list2.get(i);
            //MenuAuthAdminData data2  = (MenuAuthAdminData)list2.get(i);
            v_levels  = dbox.getInt("d_levels");
            v_seq     = dbox.getInt("d_seq");
            v_menu    = dbox.getString("d_menu");
            v_menunm  = dbox.getString("d_menunm");
            if (v_levels == 1 && v_seq == 0)      {
                v_menucount++;
                v_gubun = "<b>" + String.valueOf(v_menucount) + "</b>";

                v_classcenter = "table_02_8";
                v_classleft   = "table_02_9";
                v_classright  = "table_02_10";
            } else if (v_levels == 2 && v_seq == 0) {
                v_gubun = "메뉴";
                v_classcenter = "table_02_5";
                v_classleft   = "table_02_6";
                v_classright  = "table_02_7";
            } else {
                v_gubun = "모듈";
                v_classcenter = "table_02_1";
                v_classleft   = "table_02_2";
                v_classright  = "table_02_4";
            }

/*
            if (v_levels == 1) v_bgcolor = "#E1E1E1";
            else               v_bgcolor = "#F7F7F7";

            if (v_seq == 0) {
                v_classcenter = "table_02_5";
                v_classleft   = "table_02_6";
                v_classright  = "table_02_7";
            } else {
                v_classcenter = "table_02_1";
                v_classleft   = "table_02_2";
                v_classright  = "table_02_4";
            }
*/
%>
            	<td height="25" class="<%=v_classcenter%>"><%=v_gubun%></td>
            	<td class="<%=v_classcenter%>"><%=v_menunm%></td>
<%
           // 권한 리스트
               v_control_org = dbox.getString("d_control");
               int j = 0;

               // 해당 권한 체크박스에 세팅

				  if (v_control_org.equals("r") || v_control_org.equals("rw") ) v_rchecked = "checked";
                  else v_rchecked = "";
                  if (v_control_org.equals("w") || v_control_org.equals("rw") ) v_wchecked = "checked";
                  else v_wchecked = "";	
%>
                <td class="<%=v_classcenter%>">
                    <input type="hidden" name = "p_menu" value="<%=v_menu%>">
                    <input type="hidden" name = "p_menusubseq" value="<%=v_seq%>">
                    <input type="hidden" name = "p_userid" value="<%=v_userid%>">
                  	<table width ='100%'>
<%            if (v_seq == 0) {                              %>

                    	<tr>
                      		<td width ='50%' class="<%=v_classright%>"> View </td>
                      		<td width ='50%' class="<%=v_classright%>">  
                        	<input type="checkbox" name="p_<%=v_userid%>R<%=i%>" value="r" <%=v_rchecked%>>&nbsp
                        	<input type="hidden" name="p_<%=v_userid%>W<%=i%>" value="w" <%=v_wchecked%>>
                      		</td>
<%            } else {                                       %>
                    	<tr>
                      		<td width ='25%' class="<%=v_classcenter%>"> R </td>
                      		<td width ='25%' class="<%=v_classright%>"> 
                        	<input type="checkbox" name="p_<%=v_userid%>R<%=i%>" value="r" <%=v_rchecked%>>&nbsp
                      		<td width ='25%' class="<%=v_classcenter%>"> W </td>
                      		<td width ='25%' class="<%=v_classright%>"> 
                        	<input type="checkbox" name="p_<%=v_userid%>W<%=i%>" value="w" <%=v_wchecked%>>&nbsp
                      		</td>
<%            }                                              %>
                  	</table>
                </td>
          	</tr>
<%
        }
%>
        </table>
        <!----------------- 운영메뉴관리 끝 ----------------->
      </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %> 
</form>
</body>
</html>
