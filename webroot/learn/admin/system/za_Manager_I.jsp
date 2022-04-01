<%
//**********************************************************
//  1. 제      목: 관리자 관리
//  2. 프로그램명 : za_Manager_I.jsp
//  3. 개      요: 관리자관리 등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 10
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_pageno    = box.getString("p_pageno");

    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");
    String gadmin       = box.getSession("gadmin");  // 권한
    String grtype       = box.getSession("grtype");       //grtype
    String ss_gadmin    = box.getStringDefault("s_gadmin","ALL");       //gadmin

    //out.println("v_search="+v_search);

%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
$(document).ready(function(){
	$("#p_sdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_ldate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--

    function insert_check() {
        var st_date = make_date(document.form1.p_sdate.value);
        var ed_date = make_date(document.form1.p_ldate.value);
        var ga = document.form1.p_gadminview.options[document.form1.p_gadminview.selectedIndex].value;
        arrStr = ga.split(",");

        if (document.form1.p_userid.value == "") {
            alert("관리자를 선택하세요");
            return;
        }

        if (document.form1.p_gadminview.value == "" ) {
            alert("권한을 선택하세요");
            return;
        }

        if(blankCheck(document.form1.p_sdate.value)){
        alert("시작일을 입력하세요.");
        return;
        }

        if(blankCheck(document.form1.p_ldate.value)){
        alert("마감일을 입력하세요.");
        return;
        }

        if(st_date > ed_date){
        alert("시작일이 마감일보다 큽니다.");
        return;
        }

        for(var i=0;i<document.form1.p_grcode.length;i++){
            document.form1.p_grcode.options[i].selected = true;
        }
        for(var i=0;i<document.form1.p_subj.length;i++){
            document.form1.p_subj.options[i].selected = true;
        }
        //for(var i=0;i<document.form1.p_company.length;i++){
        //    document.form1.p_company.options[i].selected = true;
        //}
        //for(var i=0;i<document.form1.p_outcompany.length;i++){
        //    document.form1.p_outcompany.options[i].selected = true;
        //}        
        //for(var i=0;i<document.form1.p_dept.length;i++){
        //    document.form1.p_dept.options[i].selected = true;
        //}
        
        document.form1.p_fmon.value   = st_date;
        document.form1.p_tmon.value   = ed_date;
        document.form1.p_gadmin.value = arrStr[0];

        document.form1.p_searchtext.value = "";
        document.form1.p_search.value     = "";
        document.form1.action = "/servlet/controller.system.ManagerAdminServlet";
        document.form1.p_process.value = "insert";
        document.form1.submit();

    }

    function list() {
        document.form1.action = "/servlet/controller.system.ManagerAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }


    //권한 설정에 따른 그룹/과정/회사/부서 등록 화면 디스플레이
    function changeGadmin() {
        var ga = document.form1.p_gadminview.options[document.form1.p_gadminview.selectedIndex].value;
        arrStr = ga.split(",");
        
        //alert(arrStr);

        if (arrStr[1]=="Y") {
            document.all.grcode.style.display = '';
        } else {
            document.all.grcode.style.display = 'none';
        }

        if (arrStr[2]=="Y") {
            document.all.subj.style.display = '';
        } else {
            document.all.subj.style.display = 'none';
        }

        if (arrStr[3]=="Y") {
//            document.all.company.style.display = '';
        } else {
//            document.all.company.style.display = 'none';
        }

        if (arrStr[4]=="Y") {
//           document.all.dept.style.display = '';
        } else {
//            document.all.dept.style.display = 'none';
        }
        
        if (arrStr[5]=="Y") {
//            document.all.outcompany.style.display = '';
        } else {
//            document.all.outcompany.style.display = 'none';
        }
    }


    // 멤버 검색
    function searchMember() {

        if (document.form1.p_gubun.value == "") {
            alert("ID/이름이 없습니다.");
            return;
        }

        if (document.form1.p_key1.value == "") {
            alert("검색어를 입력해주세요");
            return;
        }

        var url = "/servlet/controller.library.SearchServlet?p_process=member&p_gubun="+ document.form1.p_gubun.value +"&p_key1="+ document.form1.p_key1.value +"&p_key2=";
        open_window("",url,"0","0","100","100");
    }

    // 멤버 검색 후 처리
    function receiveMember(userid, name, resno, email, cono, jikwi, jikwinm, comp, compnm, tmp1, tmp2, tmp3){
      document.form1.p_comp.value   = comp;
//      document.form1.p_jikwi.value  = jikwi;
      document.form1.p_userid.value = userid;

      //document.all.t_compnm.innerText  = compnm;
      //document.all.t_jikwinm.innerText  = jikwinm;
      document.all.t_userid.innerText  = userid;
      //document.all.t_cono.innerText  = cono;
      document.all.t_name.innerText  = name;
    }

    // 그룹 검색
    function searchGrcode() {
        var url = "/servlet/controller.library.SearchServlet?p_process=grcode&p_gubun=grcodenm&p_key1=&p_key2=";
        open_window("",url,"0","0","100","100");
    }

    // 그룹 검색 후 처리
    function receiveGrcode(grcode , grcodenm, tmp1, tmp2, tmp3){
        var j = document.form1.p_grcode.length;
        var f_exist = "";
        for(var i=0;i<j;i++){
            if(document.form1.p_grcode.options[i].value==grcode){
                f_exist = "Y";
                alert(grcodenm+"는(은) 이미 선정되어 있습니다");
            }        
        }
        if (f_exist != "Y"){
            document.form1.p_grcode.options[j] = new Option(grcodenm,grcode);
            document.form1.p_grcode.options[j].selected = true;
        }
    }
    
    // 선택 그룹 삭제
    function delGrcode(){
        for(var i = 0 ;i<document.form1.p_grcode.length;i++){
            if(document.form1.p_grcode.options[i].selected==true)
                document.form1.p_grcode.options[i] = null;
            }
    }

    // 과정 검색
    function searchSubj() {
        var url = "/servlet/controller.library.SearchServlet?p_process=subj&p_gubun=subjnm&p_key1=&p_key2=";
        open_window("",url,"0","0","100","100");
    }

    // 과정 검색 후 처리
    function receiveSubj(subj,subjnm, tmp1, tmp2, tmp3){
        var j = document.form1.p_subj.length;
        var f_exist = "";
        for(var i=0;i<j;i++){
            if(document.form1.p_subj.options[i].value==subj){
                f_exist = "Y";
                alert(subjnm+"는(은) 이미 선정되어 있습니다");
            }        
        }
        if (f_exist != "Y"){
            document.form1.p_subj.options[j] = new Option(subjnm,subj);
            document.form1.p_subj.options[j].selected = true;
        }
    }
    
    // 선택 과정 삭제
    function delSubj(){
        for(var i = 0 ;i<document.form1.p_subj.length;i++){
            if(document.form1.p_subj.options[i].selected==true)
                document.form1.p_subj.options[i] = null;
            }
    }

    // 회사 검색
    //function searchCompany() {
    //    var url = "/servlet/controller.library.SearchServlet?p_process=grpcomp&p_gubun=companynm&p_key1=&p_key2=";
    //    open_window("",url,"0","0","100","100");
    //}

    // 회사 검색 후 처리
    //function receiveGrpComp(comp , compnm, comptype, groupnm, companynm, tmp1, tmp2, tmp3){
    //    var compnm = companynm;
    //    var j = document.form1.p_company.length;
    //    var f_exist = "";
    //    for(var i=0;i<j;i++){
    //        if(document.form1.p_company.options[i].value==comp){
    //            f_exist = "Y";
    //            alert(compnm+"는(은) 이미 선정되어 있습니다");
    //        }        
    //    }
    //    if (f_exist != "Y"){
    //        document.form1.p_company.options[j] = new Option(compnm,comp);
    //        document.form1.p_company.options[j].selected = true;
    //    }
    //}
    
    // 회사 부서 삭제
    //function delCompany(){
    //    for(var i = 0 ;i<document.form1.p_company.length;i++){
    //        if(document.form1.p_company.options[i].selected==true)
    //            document.form1.p_company.options[i] = null;
    //        }
    //}
    
    // 외주업체 검색
    //function searchOutCompany() {
    //  var ga = document.form1.p_gadminview.options[document.form1.p_gadminview.selectedIndex].value;
    //  arrStr = ga.split(",");
        
    //  if(arrStr[0] == 'M1') // cp+bp
    //  {var url = "/servlet/controller.library.SearchServlet?p_process=grpoutcomp&p_gubun=companynm&p_key1=&p_key2=p_cpbpinfo";}
    //  if(arrStr[0] == 'S1') // cp
    //  {var url = "/servlet/controller.library.SearchServlet?p_process=grpoutcomp&p_gubun=companynm&p_key1=&p_key2=p_cpinfo";}
    //  if(arrStr[0] == 'T1') // bp
    //  {var url = "/servlet/controller.library.SearchServlet?p_process=grpoutcomp&p_gubun=companynm&p_key1=&p_key2=p_bpinfo";}
    //  open_window("",url,"0","0","100","100");
    //}

    // 외주업체 검색 후 처리
    //function receiveGrpOutComp(comp , compnm, comptype, groupnm, companynm, tmp1, tmp2, tmp3){
    //    var compnm = companynm;
    //    var j = document.form1.p_outcompany.length;
    //    var f_exist = "";
    //    for(var i=0;i<j;i++){
    //        if(document.form1.p_outcompany.options[i].value==comp){
    //            f_exist = "Y";
    //            alert(compnm+"는(은) 이미 선정되어 있습니다");
    //        }        
    //    }
    //    if (f_exist != "Y"){
    //        document.form1.p_outcompany.options[j] = new Option(compnm,comp);
    //        document.form1.p_outcompany.options[j].selected = true;
    //    }
    //}
    //
    //// 외주업체 삭제
    //function delOutCompany(){
    //    for(var i = 0 ;i<document.form1.p_outcompany.length;i++){
    //        if(document.form1.p_outcompany.options[i].selected==true)
    //            document.form1.p_outcompany.options[i] = null;
    //        }
    //}
    
    
    

    // 부서 검색
    //function searchDept() {
    //    var url = "/servlet/controller.library.SearchServlet?p_process=dept&p_gubun=deptnm&p_key1=&p_key2=";
    //    open_window("",url,"0","0","100","100");
    //}

    // 부서 검색 후 처리
    //function receiveDept(comp , compnm, comptype, groupnm, companynm, gpmnm, deptnm, partnm, tmp1, tmp2, tmp3){
    //    var compnm = companynm+"/"+gpmnm+"/"+deptnm;
    //    var j = document.form1.p_dept.length;
    //    var f_exist = "";
    //    for(var i=0;i<j;i++){
    //        if(document.form1.p_dept.options[i].value==comp){
    //            f_exist = "Y";
    //            alert(compnm+"는(은) 이미 선정되어 있습니다");
    //        }        
    //   }
    //    if (f_exist != "Y"){
    //        document.form1.p_dept.options[j] = new Option(compnm,comp);
    //        document.form1.p_dept.options[j].selected = true;
    //    }
    //}
    
    // 선택 부서 삭제
    //function delDept(){
    //    for(var i = 0 ;i<document.form1.p_dept.length;i++){
    //        if(document.form1.p_dept.options[i].selected==true)
    //            document.form1.p_dept.options[i] = null;
    //        }
    //}
//-->
</SCRIPT>
</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name="form1" method="post">
    <input type = "hidden" name = "p_process"    value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno%>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext%>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search%>">
    <input type = "hidden" name = "p_fmon"       value = "">
    <input type = "hidden" name = "p_tmon"       value = "">
    <input type = "hidden" name = "p_gadmin"     value = "">
    <input type = "hidden" name = "s_gadmin"     value = "<%=ss_gadmin%>">
					<input type="hidden" name="p_grtype" value="KOCCA">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/unite_title05.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
        <br>
        <!----------------- 멤버 검색 시작 ---------------------------------------->
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center"> 
              <select name="p_gubun">
                <option value='name' <%if(v_search.equals("name")){ out.println("selected");}%>>이름</option>
                <option value='userid' <%if(v_search.equals("userid")){ out.println("selected");}%>>ID</option>
              </select>
              <input name="p_key1" type="text" class="input" value='<%=v_searchtext%>' onkeypress="javascript:enterKey_Event('searchMember()', event)">
              <input type = "hidden" name = "p_comp"   value = "">
              <input type = "hidden" name = "p_userid" value = "">
              <a href='javascript:searchMember()'><img src="/images/admin/button/btn_inquiry.gif" align="absmiddle" border="0" valign="top"></a>
            </td>
          </tr>
        </table>
        <!----------------- 멤버 검색 끝   ---------------------------------------->

        <br>
        <!----------------- 운영자등록 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
<!--
          <tr> 
            <td width="15%" height="25" class="table_title"><strong>소속</strong></td>
            <td class="table_02_2"><span id='t_compnm'></span></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>직위</strong></td>
            <td height="25" class="table_02_2"><span id='t_jikwinm'></span></td>
          </tr>
-->
<!--
          <tr> 
            <td width="15%" class="table_title"><strong>사번</strong></td>
            <td height="25" class="table_02_2"><span id='t_cono'></td>
          </tr>
-->
          <tr> 
            <td width="15%" class="table_title"><strong>ID</strong></td>
            <td height="25" class="table_02_2"><span id='t_userid'></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>성명</strong></td>
            <td height="25" class="table_02_2"><span id='t_name'></span></td>
          </tr>
          <!------------------------  권한분류 시작 ---------------------->
          <tr>
            <td class="table_title"><strong>권한</strong></td>
            <td height="25" class="table_02_2">
               <%= GadminAdminBean.getGadminSelectNopGrtype("p_gadminview","","onChange=\"changeGadmin()\"",gadmin,0) %>
            </td>
          </tr>
          <!------------------------  권한분류 끝   ---------------------->
          
          <!------------------------  그룹검색 시작 ---------------------->
          <tr>
            <td class="table_title"><strong>교육그룹</strong></td>
            <td height="25" class="table_02_2">
              <div id="grcode" style="display:none">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 	
                  <td> 
                    <select name="p_grcode" size=4  multiple></select>                    
                    <a href="javascript:searchGrcode()"><img src="/images/admin/button/btn_groupsearch.gif" border="0"></a>
                    <a href="javascript:delGrcode()"><img src="/images/admin/button/btn_groupdelete.gif" border="0"></a>
                  </td>
                </tr>
              </table>
              </div>
            </td>
          </tr>

        <!------------------------  그룹검색 끝   ---------------------->

        <!------------------------  과정검색 시작 ---------------------->
          <tr>
            <td class="table_title"><strong>과정</strong></td>
            <td height="25" class="table_02_2">
              <div id="subj" style="display:none">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td> 
                    <select name="p_subj" size=4  multiple></select>                    
                    <a href="javascript:searchSubj()"><img src="/images/admin/button/btn_csearch.gif" border="0"></a>
                    <a href="javascript:delSubj()"><img src="/images/admin/button/btn_cdelete.gif" border="0"></a>
                  </td>
                </tr>
              </table>
              </div>
            </td>
          </tr>
        <!------------------------  과정검색 끝   ---------------------->

        <!------------------------  회사검색 시작 ---------------------->
          <!--
          <tr>
            <td class="table_title"><strong>회사</strong></td>
            <td height="25" class="table_02_2">
              <div id="company" style="display:none">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td>
                    <select name="p_company" size=4  multiple></select>                    
                    <a href="javascript:searchCompany()"><img src="/images/admin/button/btn_comsearch.gif" border="0"></a>
                    <a href="javascript:delCompany()"><img src="/images/admin/button/btn_comdelete.gif" border="0"></a>
                  </td>
                </tr>
              </table>
              </div>
            </td>
          </tr>
          -->
        <!------------------------  회사검색 끝   ---------------------->
        
        
        <!------------------------  외주베타 업체시작 ---------------------->
          <!--tr>
            <td class="table_title"><strong>외주업체</strong></td>
            <td height="25" class="table_02_2">
              <div id="outcompany" style="display:none">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td>
                    <select name="p_outcompany" size=4  multiple></select>                    
                    <a href="javascript:searchOutCompany()"><img src="/images/admin/button/btn_comsearch.gif" border="0"></a>
                    <a href="javascript:delOutCompany()"><img src="/images/admin/button/btn_comdelete.gif" border="0"></a>
                  </td>
                </tr>
              </table>
              </div>
            </td>
          </tr-->
        <!------------------------  외주베타 끝   ---------------------->
        
        

        <!------------------------  부서검색 시작 ---------------------->
        <!-- 
          <tr>
            <td class="table_title"><strong>부서</strong></td>
            <td height="25" class="table_02_2">
              <div id="dept" style="display:none">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td> 
                    <select name="p_dept" size=4  multiple></select>                    
                    <a href="javascript:searchDept()"><img src="/images/admin/button/btn_partsearch.gif" border="0"></a>
                    <a href="javascript:delDept()"><img src="/images/admin/button/btn_partdelete.gif" border="0"></a>
                  </td>
                </tr>
              </table>
              </div>
            </td>
          </tr>
          -->
        <!------------------------  부서검색 끝   ---------------------->
          <tr>
            <td height="25" class="table_title"><b>권한사용기간</b></td>
            <td class="table_02_2">
              <input name="p_sdate" id="p_sdate" class="datepicker_input1" type="text" size="10" value=''>
              ~
              <input name="p_ldate" id="p_ldate" class="datepicker_input1" type="text" size="10" value=''>
            </td>
          </tr>
          <tr>
            <td height="25" class="table_title"><b>권한사용용도</b></td>
            <td class="table_02_2">
              <input name="p_commented" class="input" type="text" size="100" value=''>
            </td>
          </tr>
        </table>
        <!----------------- 운영자등록 끝 ----------------->


        <br>
        <!----------------- 저장, 취소 버튼 시작 ----------------->
        <table border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center"><a href='javascript:insert_check()'><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
			<td width=8></td>
            <td align="center"><a href='javascript:list()'><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->





    </td>
  </tr>
</table>







<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
