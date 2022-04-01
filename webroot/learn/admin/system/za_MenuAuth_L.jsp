<%
//**********************************************************
//  1. 제      목: 메뉴권한설정
//  2. 프로그램명 : za_MenuAuth_L.jsp
//  3. 개      요: 메뉴권한설정 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 8
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");
    String  v_grcode   = box.getString("p_grcode");
    String  v_systemgubun = box.getString("p_systemgubun");

    int  v_gadmincnt  = StringManager.toInt(box.getString("p_gadmincnt"));  // 권한갯수
    ArrayList list1   = (ArrayList)request.getAttribute("selectList1");       // 권한리스트
    ArrayList list2   = (ArrayList)request.getAttribute("selectList2");       // 메뉴별권한 리스트

    String v_gadminnm  = "";                       	 // 권한명
    String[] v_gadmin  = new String[v_gadmincnt];    // 권한아이디

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
    int    j=0,k=0,v_width=1000;

    for (j=9; j<=v_gadmincnt; j++){  //권한의 증가에 따른 테이블 width설정
    	k++;
    	v_width=v_width+k*100;
    }
%>

<html>
<head>
<title>▒▒▒▒▒ 크레듀 교육시스템 ▒▒▒▒▒</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function insert() {

        document.form1.action = "/servlet/controller.system.MenuAuthAdminServlet";
        document.form1.p_process.value = "update";
        document.form1.submit();
    }
    
    function Search() {
        document.form1.action ="/servlet/controller.system.MenuAuthAdminServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">


<form name="form1" method="post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_grcode"  value = "<%= v_grcode %>">

<table width="<%=v_width%>" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="<%=v_width%>" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/unite_title04.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>

        <!----------------- title 끝 ----------------->

        <br>
        <!----------------- 저장, 메뉴코드리스트 버튼 시작 ----------------->
        <table width="<%=v_width%>" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <!--td width="7%" align="left" class=txt_input>시스템명</td>
            <td width="15%" align="left">
                <select name="p_systemgubun" onChange=Search()>
                    <option value="1" <% if(v_systemgubun.equals("1")) { out.println("selected");}%>>통합교육시스템</option>
                    <option value="2" <% if(v_systemgubun.equals("2")) { out.println("selected");}%>>게이트시스템</option>
                </select>
            </td-->
            <td>&nbsp;</td>
            <td align="left"><a href='javascript:insert()'><img src="/images/admin/button/btn_save.gif" border="0"></a>
            <a href='/servlet/controller.system.MenuAdminServlet?p_process=select&p_grcode=0000001'><img src="/images/admin/button/btn_menucodelist.gif" border="0"></a></td>
          </tr>
          <tr> 
            <td height="3"></td>
          </tr>
        </table> 
        <!----------------- 저장, 메뉴코드리스트 버튼 끝 ----------------->

        <!----------------- 운영메뉴관리 시작 ----------------->
        <table width="<%=v_width%>" border="0" class="table_out" cellspacing="1" cellpadding="0">
          <tr>
            <td colspan="<%=(v_gadmincnt + 2)%>" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="40" height="50" class="table_title" rowspan="2"><b>구분</b></td>
            <td width="160" class="table_title" rowspan="2"><b>메뉴코드명</b></td>
            <td width="<%=v_gadmincnt*v_width/10%>" class="table_title" colspan="<%=v_gadmincnt%>" height="25"><b>권한</b></td>
          </tr>
          <tr>
<%
      // 권한 리스트
       for(int i = 0; i < list1.size(); i++) {

                GadminData data1  = (GadminData)list1.get(i);
                v_gadmin[i]   = data1.getGadmin();
                v_gadminnm    = data1.getGadminnm();
%>
            <td width="<%=v_width/10%>" class="table_title" height="25"><b><%=v_gadminnm%></b></td>
<%
        }
%>
          </tr>
          <tr>
<%= StringManager.menuListFetch( list2,   v_gadmincnt,  v_gadmin,v_control) %>
<% /*
       //메뉴 리스트
        for(int i = 0; i < list2.size(); i++) {

            MenuAuthData data2  = (MenuAuthData)list2.get(i);
            v_levels  = data2.getLevels();
            v_seq     = data2.getSeq();
            v_menu    = data2.getMenu();
            v_menunm  = data2.getMenunm();

            

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
%>
            <td width="40" height="25" class="<%=v_classcenter%>"><%=v_gubun%></td>
            <td width="160" class="<%=v_classcenter%>"><%=v_menunm%></td>
<%
           // 권한 리스트
           if(v_gadmincnt > 0) {
               v_control_org = data2.getControl();
               StringTokenizer st = new StringTokenizer(v_control_org,"/");
               j = 0;

               // 어드민권한에 대한 각각의 읽기/쓰기권한값 배열에 저장
               while (st.hasMoreElements()) {
                   v_control[j] = StringManager.chkNull((String)st.nextToken());
                   j++;

               }

               // 위치 지정을 위한 구분값 (맨앞숫자 한자리) 제거
               for (j = 0;j<v_gadmincnt;j++){
                  v_control[j] = StringManager.rightstring(v_control[j], v_control[j].length()-1);
               }
               
            	
               // 해당 권한 체크박스에 세팅
               for (k=0; k < v_gadmincnt ; k++) {

               	
                  v_control[k] = StringManager.chkNull(v_control[k]);

                   if (v_control[k].equals("r") || v_control[k].equals("rw") ) v_rchecked = "checked";
                   else v_rchecked = "";
                   if (v_control[k].equals("w") || v_control[k].equals("rw") ) v_wchecked = "checked";
                   else v_wchecked = "";
                   
%>				
                <td width="<%=v_width/10%>" class="<%=v_classcenter%>">
                        <input type="hidden" name = "p_menu<%=v_gadmin[k]%>" value="<%=v_menu%>">
                        <input type="hidden" name = "p_menusubseq<%=v_gadmin[k]%>" value="<%=v_seq%>">
                        <input type="hidden" name = "p_gadmin<%=v_gadmin[k]%>" value="<%=v_gadmin[k]%>">
                  <table width='100'>
<%            if (v_seq == 0) {                              %>

                    <tr>
                      <td class="<%=v_classright%>"> View </td>
                      <td class="<%=v_classright%>">  
                        <input type="checkbox" name="p_<%=v_gadmin[k]%>R<%=i%><%=k%>" value="r" <%=v_rchecked%>>
                        <input type="hidden" name="p_<%=v_gadmin[k]%>W<%=i%><%=k%>" value="w" <%=v_wchecked%>>
                      </td>
<%            } else {                                       %>
                    <tr>
                      <td class="<%=v_classcenter%>"> R </td>
                      <td class="<%=v_classright%>"> 
                        <input type="checkbox" name="p_<%=v_gadmin[k]%>R<%=i%><%=k%>" value="r" <%=v_rchecked%>>
                      <td class="<%=v_classcenter%>"> W </td>
                      <td class="<%=v_classright%>"> 
                        <input type="checkbox" name="p_<%=v_gadmin[k]%>W<%=i%><%=k%>" value="w" <%=v_wchecked%>>
                      </td>
<%            }                                              %>
                  </table>
                </td>
<%
                }
            }
%>
          </tr>
<%
        }*/
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
