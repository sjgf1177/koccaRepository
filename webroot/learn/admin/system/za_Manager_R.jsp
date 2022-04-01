<%
//**********************************************************
//  1. 제      목: 관리자관리
//  2. 프로그램명 : za_Manager_R.jsp
//  3. 개      요: 관리자관리 등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 10
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_pageno    = box.getString("p_pageno");
    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");
    String ss_gadmin    = box.getStringDefault("s_gadmin","ALL");       //gadmin

    String v_userid     = box.getString("p_userid");
    String v_gadmin     = box.getString("p_gadmin");

   // 권한코드 + "," + 교육그룹필요여부  + "," + 과정코드필요여부 + "," + 회사코드필요여부 +  "," + 부서코드필요여부
    String v_gadminview = GadminAdminBean.getGadminIsNeed(v_gadmin);
    String v_isneedgrcode  = "";
    String v_isneedsubj    = "";
    String v_isneedcomp    = "";
    String v_isneedoutcomp = "";
    String v_isneeddept    = "";
    StringTokenizer st = new StringTokenizer(v_gadminview,",");

    // 그룹/과정/부서 필요여부
    if (st.hasMoreElements()) {
        v_gadmin        = (String)st.nextToken();
        v_isneedgrcode  = (String)st.nextToken();
        v_isneedsubj    = (String)st.nextToken();
        v_isneedcomp    = (String)st.nextToken();
        v_isneeddept    = (String)st.nextToken();
        //v_isneedoutcomp = (String)st.nextToken();
    }

    // 관리자 기본정보
    String v_gadminnm   = "";
    String v_grtype     = "";
    String v_comp       = "";
    String v_isdeleted  = "";
    String v_fmon       = "";
    String v_tmon       = "";
    String v_fmonview   = "";
    String v_tmonview   = "";
    String v_commented  = "";
    String v_luserid    = "";
    String v_ldate      = "";

    String v_cono       = "";
    String v_name       = "";
    String v_jikwi      = "";
    String v_jikwinm    = "";
    String v_compnm     = "";

    //관리교육그룹정보
    String v_grcode     = "";
    String v_grcodenm   = "";

    //관리과목정보
    String v_subj       = "";
    String v_subjnm     = "";

    //관리회사정보
    String v_company    = "";
    String v_companynm  = "";
    
    //관리회사정보
    String v_outcompany = "";
    String v_cpnm       = "";

    //관리부서정보
    String v_subcomp       = "";
    String v_subcompanynm  = "";
    String v_subgpmnm      = "";
    String v_subdeptnm     = "";

    DataBox dbox = (DataBox)request.getAttribute("selectManager");

    if (dbox != null) {
        v_gadmin     = dbox.getString("d_gadmin");
        v_gadminnm   = dbox.getString("d_gadminnm");
        v_grtype     = dbox.getString("d_grtype");
        v_comp       = dbox.getString("d_comp");
        v_isdeleted  = dbox.getString("d_isdeleted");
        v_fmon       = dbox.getString("d_fmon");
        v_tmon       = dbox.getString("d_tmon");
        v_fmonview   = FormatDate.getFormatDate(v_fmon,"yyyy/MM/dd");
        v_tmonview   = FormatDate.getFormatDate(v_tmon,"yyyy/MM/dd");
        v_commented  = dbox.getString("d_commented");
        v_luserid    = dbox.getString("d_luserid");
        v_ldate      = dbox.getString("d_ldate");

        v_cono       = dbox.getString("d_cono");
        v_name       = dbox.getString("d_name");
        v_jikwi      = dbox.getString("d_jikwi");
        v_jikwinm    = dbox.getString("d_jikwinm");
        v_compnm     = dbox.getString("d_compnm");
    }

    ArrayList list1 = (ArrayList)request.getAttribute("selectManagerGrcode");     //관리교육그룹정보
    ArrayList list2 = (ArrayList)request.getAttribute("selectManagerSubj");       //관리과목정보
    ArrayList list3 = (ArrayList)request.getAttribute("selectManagerComp");       //관리회사정보
    ArrayList list4 = (ArrayList)request.getAttribute("selectManagerDept");       //관리부서정보
    ArrayList list5 = (ArrayList)request.getAttribute("selectManagerOutComp");       //외주업체정보


%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 운영자 수정
    function modify_manager() {
        if (document.form1.p_gadmin.value=="P1") {
             alert("강사관리에서 수정해주십시요");
             return;
        }

        document.form1.action = "/servlet/controller.system.ManagerAdminServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }
    // 운영자 삭제
    function delete_manager() {
        if (document.form1.p_gadmin.value=="P1") {
             alert("강사관리에서 삭제해주십시요");
             return;
        }

        document.form1.p_searchtext.value = "";
        document.form1.p_search.value     = "";
        document.form1.action = "/servlet/controller.system.ManagerAdminServlet";
        document.form1.p_process.value = "delete";
        document.form1.submit();
    }
    // 리스트
    function list_manager() {
        document.form1.action = "/servlet/controller.system.ManagerAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"    value = "<%=v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno%>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext%>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search%>">
    <input type = "hidden" name = "p_userid"     value = "<%=v_userid%>">
    <input type = "hidden" name = "p_gadmin"     value = "<%=v_gadmin%>">
    <input type = "hidden" name = "s_gadmin"     value = "<%=ss_gadmin%>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr> 
          <td><img src="/images/admin/system/unite_title05.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
        <br>
        <!----------------- 운영자정보 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>ID</strong></td>
            <td height="25" class="table_02_2"><%=v_userid%></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>성명</strong></td>
            <td height="25" class="table_02_2"><%=v_name%></td>
          </tr>
          <!------------------------  권한분류 시작 ---------------------->
          <tr>
            <td class="table_title"><strong>권한</strong></td>
            <td height="25" class="table_02_2"><%=v_gadminnm%></td>
          </tr>
          <!------------------------  권한분류 끝   ---------------------->
          <tr>
            <td height="25" class="table_title"><b>담당기관</b></td>
            <td class="table_02_2"><%=GetCodenm.get_codenm("0060",v_grtype)%></td>
          </tr>
        <!------------------------  그룹검색 시작 ---------------------->
          <tr>
            <td class="table_title"><strong>교육그룹</strong></td>
            <td height="25" class="table_02_2">
<%
         if (list1 != null && v_isneedgrcode.equals("Y")) {
            for(int i = 0; i < list1.size(); i++) {
                DataBox dbox1 = (DataBox)list1.get(i);
                v_grcode    = dbox1.getString("d_grcode");
                v_grcodenm  = dbox1.getString("d_grcodenm");
                out.println (v_grcodenm + "<br>");
            }
         }
%>
            </td>
          </tr>

        <!------------------------  그룹검색 끝   ---------------------->

        <!------------------------  과정검색 시작 ---------------------->
          <tr>
            <td class="table_title"><strong>과정</strong></td>
            <td height="25" class="table_02_2">
<%
         if (list2 != null && v_isneedsubj.equals("Y")) {
            for(int i = 0; i < list2.size(); i++) {
                DataBox dbox2 = (DataBox)list2.get(i);
                v_subj    = dbox2.getString("d_subj");
                v_subjnm  = dbox2.getString("d_subjnm");
                out.println (v_subjnm + "<br>");
            }
         }
%>
            </td>
          </tr>
        <!------------------------  과정검색 끝   ---------------------->

        <!------------------------  회사검색 시작 ---------------------->
          <!--tr>
            <td class="table_title"><strong>회사</strong></td>
            <td height="25" class="table_02_2">
<%
         if (list3 != null && v_isneedcomp.equals("Y")) {
            for(int i = 0; i < list3.size(); i++) {
                DataBox dbox3 = (DataBox)list3.get(i);
                v_company    = dbox3.getString("d_comp");
                v_companynm  = dbox3.getString("d_compnm");
                out.println (v_companynm + "<br>");
            }
         }
%>
            </td>
          </tr-->
        <!------------------------  회사검색 끝   ---------------------->
        
        
        <!------------------------  외주업체명 시작 ---------------------->
          <!--tr>
            <td class="table_title"><strong>외주업체명</strong></td>
            <td height="25" class="table_02_2">
<%
         if (list5 != null && v_isneedoutcomp.equals("Y")) {
            for(int i = 0; i < list5.size(); i++) {
                DataBox dbox5 = (DataBox)list5.get(i);
                v_outcompany = dbox5.getString("d_comp");
                v_cpnm       = dbox5.getString("d_cpnm");
                out.println (v_cpnm + "<br>");
            }
         }
%>
            </td>
          </tr-->
        <!------------------------  외주업체명 끝   ---------------------->
        

        <!------------------------  부서검색 시작 ---------------------->
          <!--tr>
            <td class="table_title"><strong>부서</strong></td>
            <td height="25" class="table_02_2">
<%
         if (list4 != null && v_isneeddept.equals("Y")) {
            for(int i = 0; i < list4.size(); i++) {
                DataBox dbox4 = (DataBox)list4.get(i);
                v_subcomp       = dbox4.getString("d_comp");
                v_subcompanynm  = dbox4.getString("d_companynm");
                v_subgpmnm  = dbox4.getString("d_gpmnm");
                v_subdeptnm  = dbox4.getString("d_deptnm");
                out.println (v_subcompanynm + "/" + v_subgpmnm + "/" + v_subdeptnm + "<br>");
            }
         }
%>
            </td>
          </tr-->
        <!------------------------  부서검색 끝   ---------------------->
          <!--tr>
            <td width="15%" class="table_title"><strong>직위</strong></td>
            <td height="25" class="table_02_2"><%=v_jikwinm%></span></td>
          </tr-->
          <tr>
            <td height="25" class="table_title"><b>권한사용기간</b></td>
            <td class="table_02_2"><%=v_fmonview%> ~ <%=v_tmonview%></td>
          </tr>
          <tr>
            <td height="25" class="table_title"><b>권한사용용도</b></td>
            <td class="table_02_2"><%=v_commented%></td>
          </tr>
        </table>
        <!----------------- 운영자정보 끝 ----------------->


        <br>
        <!----------------- 수정, 삭제, 리스트 버튼 시작 ----------------->
        <table border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center"><a href="javascript:modify_manager()"><img src="/images/admin/button/btn_modify.gif" border="0"></a></td>
            <td width=8></td>
            <td align="center"><a href="javascript:delete_manager()"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
            <td width=8></td>
            <td align="center"><a href="javascript:list_manager()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 수정, 삭제, 리스트 버튼 끝 ----------------->


    </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
