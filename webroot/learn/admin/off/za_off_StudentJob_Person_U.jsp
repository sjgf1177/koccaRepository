<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    
    String  v_process   = box.getString("p_process");
    int     v_pageno    = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");

    //DEFINED in relation to select START
    String  ss_year         = box.getString("s_year");                      //연도
    String  ss_subj         = box.getString("s_subjcode");                  //과정
    String  ss_grseq        = box.getString("s_grseq");                     //차수
    String  ss_upperclass   = box.getStringDefault("s_upperclass", "ALL");  //과정대분류
    String  ss_middleclass  = box.getStringDefault("s_middleclass", "ALL"); //과정중분류
    String  ss_lowerclass   = box.getStringDefault("s_lowerclass", "ALL");  //과정소분류
    String  ss_action       = box.getString("s_action");
    String  s_subjsearchkey = box.getString("s_subjsearchkey");
    String  v_gyear         = box.getStringDefault("p_gyear", FormatDate.getDate("yyyy"));
    String  v_searchtext    = box.getString("p_searchtext");
    //DEFINED in relation to select END
    
    
    
    int    v_seq            = 0;
    String v_comp           = "";
    String v_compnm         = "";
    String v_employgubun    = "";
    String v_employgubunnm  = "";
    String v_employdate     = "";
    String v_subj           = "";
    String v_year           = "";
    String v_subjseq        = "";
    String v_userid         = "";
    String v_position		= "";
    String v_depart			= "";
    String v_type 			= "";
    
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_totalrowcount =  0;


    ArrayList list = (ArrayList)request.getAttribute("selectListPerPerson");
    
    DataBox subjBox = (DataBox)request.getAttribute("selectView");
    
    String v_subjnm     = "";
    String v_name       = "";
    String v_studentno  = "";
    String v_state      = "";
    String v_statenm    = "";
    
    if(subjBox !=null){
        v_subj            = subjBox.getString("d_subj");
        v_subjnm          = subjBox.getString("d_subjnm");
        v_year            = subjBox.getString("d_year");
        v_subjseq         = subjBox.getString("d_subjseq");
        v_name            = subjBox.getString("d_name");
        v_studentno       = subjBox.getString("d_studentno");
        v_state           = subjBox.getString("d_stustatus");
        v_statenm         = subjBox.getString("d_statenm");
        v_userid          = subjBox.getString("d_userid");  
       
    }
    
    DataBox employBox = (DataBox)request.getAttribute("selectView2");
   
    String v_remarks        = "";
    
    if(employBox !=null){
    	v_seq            = employBox.getInt("d_seq");  
    	v_comp           = employBox.getString("d_comp");       
    	v_compnm         = employBox.getString("d_compnm");     
    	v_employgubun    = employBox.getString("d_employgubun");
    	v_name           = employBox.getString("d_name");       
    	
    	v_employgubun    = employBox.getString("d_employgubun");
    	v_employdate     = employBox.getString("d_employdate");
    	v_remarks        = employBox.getString("d_remarks"); 
    	v_depart		 = employBox.getString("d_depart");  
        v_position		 = employBox.getString("d_position");  
        v_type			 = employBox.getString("d_type");  
    }
    
    
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
	$("#p_fdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
    function go(index) {
         document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
         document.form1.p_process.value = "selectListPerPerson";
         document.form1.submit();
    }

    function goPage(pageNum) {
         document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
         document.form1.p_process.value = "selectListPerPerson";
         document.form1.submit();
    }

    function update(){
        var frm = document.form1;
       
        var radioObj = document.getElementsByName("p_employgubun");
        var objLen   = radioObj.length;
        var cnt      = 0;
        var tempText = "";

        for(var i = 0 ; i < objLen ; i++){
            if(radioObj[i].checked){
                cnt++;
                tempText = radioObj[i].value;
                break;
            }
        }

        if(cnt == 0){
            alert("취업구분을 선택하세요.");
            return;
        }

        if(frm.p_compnm.value=="" && tempText == "A"){
            alert("회사를 입력해주세요.");
            frm.p_compnm.focus();
            return;
        }

        var fdate = make_date(document.form1.p_fdate.value);
        
        document.form1.p_employdate.value = fdate;

         document.form1.target = "_self";
         document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
         document.form1.p_process.value = "update";
         document.form1.submit();
    }
    //회사 검색
    function searchComp() {
      var url = "/servlet/controller.library.SearchServlet?p_process=searchComp";
      open_window("",url,"0","0","100","100");
    }
    //회사 검색 후 처리
    function receiveComp(comp , compnm, telno, faxno, name, email){
      var compObj = document.form1.p_comp;
      var compnmObj = document.form1.p_compnm;
      var divTelno = document.getElementById("div_tel");
      var divFaxno = document.getElementById("div_fax");
      var divName  = document.getElementById("div_name");
      var divEmail = document.getElementById("div_email");
      
      compObj.value = comp;
      compnmObj.value = compnm;
      divTelno.innerHTML = telno;
      divFaxno.innerHTML = faxno;
      divName.innerHTML = name; 
      divEmail.innerHTML = email;

    }

    function insertPage(){
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
   }

   function updatePage(seq){
       document.form1.p_seq.value = seq;
       document.form1.target = "_self";
       document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
       document.form1.p_process.value = "updatePage";
       document.form1.submit();
  }

   function listPage(){
       document.form1.target = "_self";
       document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
       document.form1.p_process.value = "selectListPerPerson";
       document.form1.submit();
  }
//-->
</SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name = "form1" method = "post">
    <input type="hidden" name="p_process"/>
    <input type="hidden" name="p_pageno"  value = "<%=v_pageno%>">
    <input type="hidden" name="p_subj"    value="<%=v_subj %>">
    <input type="hidden" name="p_subjnm"  value="">
    <input type="hidden" name="p_year"    value="<%=v_year %>">
    <input type="hidden" name="p_subjseq" value="<%=v_subjseq %>">
    <input type="hidden" name="p_userid"  value="<%=v_userid %>">
    <input type="hidden" name="p_comp"    value="<%=v_comp %>">
    <input type="hidden" name="p_employdate"    value="<%=v_employdate %>">
    <input type="hidden" name="p_seq"     value="<%=v_seq %>">
    
    <input type = "hidden" name="s_year"          value="<%=ss_year        %>"/>
    <input type = "hidden" name="s_subj"          value="<%=ss_subj        %>"/>
    <input type = "hidden" name="s_grseq"         value="<%=ss_grseq       %>"/>
    <input type = "hidden" name="s_upperclass"    value="<%=ss_upperclass  %>"/>
    <input type = "hidden" name="s_middleclass"   value="<%=ss_middleclass %>"/>
    <input type = "hidden" name="s_lowerclass"    value="<%=ss_lowerclass  %>"/>
    <input type = "hidden" name="s_action"        value="<%=ss_action      %>"/>
    <input type = "hidden" name="s_subjsearchkey" value="<%=s_subjsearchkey%>"/>
    <input type = "hidden" name="p_gyear"         value="<%=v_gyear        %>"/>
    <input type = "hidden" name="p_searchtext"    value="<%=v_searchtext   %>"/>

<table width="522" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td><table width="520" height="337" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" valign='top' bgcolor="#FFFFFF">
          <!----------------- title 시작 ----------------->
          <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
            <tr>
              <td><img src="/images/admin/portal/s.1_off09.gif" ></td>
              <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
            </tr>
          </table>
          
          <br/>
          <table width="515" cellspacing="1" cellpadding="0" class="table_out">
              <tr>
                <td colspan="4" class="table_top_line"></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >과정명</td>
                <td class="table_02_2"  width="80%" colspan=3><%=v_subjnm %></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >성명</td>
                <td class="table_02_2"  width="30%" ><%=v_name %></td>
                <td class="table_title" width="20%" >학번</td>
                <td class="table_02_2"  width="30%" ><%=v_studentno %></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >차수</td>
                <td class="table_02_2"  width="30%" ><%=Integer.parseInt(v_subjseq) %>기</td>
                <td class="table_title" width="20%" >학적상태</td>
                <td class="table_02_2"  width="30%" ><%=v_statenm%></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >취업일자</td>
                <td class="table_02_2"  width="80%" colspan=3>
                  <input name="p_fdate" id="p_fdate" class="datepicker_input1" type="text" size="10" value='<%=FormatDate.getFormatDate(v_employdate,"yyyy-MM-dd") %>'>
                </td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >취업구분</td>
                <td class="table_02_2"  width="80%" colspan=3>
                <% 
                List codeList = CodeAdminBean.selectListCode("0099");
               
                if(codeList != null && codeList.size() > 0 ){
                    String sCode   = "";
                    String sCodeNm = "";
                    for(int i = 0 ; i < codeList.size() ; i++){
                        
                        DataBox codeBox = (DataBox)codeList.get(i);
                        
                        sCode   = codeBox.getString("d_code");
                        sCodeNm = codeBox.getString("d_codenm");
                        
                %>
                    <input type="radio" name="p_employgubun" value="<%=sCode %>" <%=sCode.equals(v_employgubun) ? "checked" : ""%>><%=sCodeNm %>
                <%
                    }
                }
                %>
                </td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >회사명</td>
                <td class="table_02_2"  width="80%" colspan="3">
                    <input type="text" name="p_compnm" class="input" style="width:200;" value="<%=v_compnm %>"/>
                   <!--  <a href='javascript:searchComp()'><img src="/images/admin/button/search3_butt.gif" border=0 align="absmiddle"></a> -->
                </td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >부서명</td>
                <td class="table_02_2"  width="80%" colspan=3>
                <input type="text" name="p_depart" class="input" style="width:200;" value="<%=v_depart %>" />
                </td>
               
              </tr>
              
               <tr>
                <td class="table_title" width="20%" >직급</td>
                <td class="table_02_2"  width="80%" colspan=3>
                <input type="text" name="p_position" class="input" style="width:200;" value="<%=v_position %>" />
                </td>
               
              </tr>
              <tr>
                <td class="table_title" width="20%" >고용형태</td>
                <td class="table_02_2"  width="80%" colspan=3 > 
                	<input type="radio" name="p_type" value="1" <%= v_type.equals("1") ? "checked" : ""%> >정규직                
                    <input type="radio" name="p_type" value="2" <%= v_type.equals("2") ? "checked" : ""%>>비정규직                
				</td>               
              </tr>
            	<tr>
                <td class="table_title" width="20%" >상담노트</td>
                <td class="table_02_2"  width="80%" colspan=3>
<textarea name="p_remarks" rows="3" cols="200">
<%=v_remarks %>
</textarea>
                    <!-- <input type="text" name="p_remarks" class="input" style="width:400px;" value=""/> -->
                </td>
              </tr>
            </table>
          <br>
            <table width="515" class="table_out" cellspacing="1" cellpadding="0">
                <tr>
                  <td colspan="4" class="table_top_line"></td>
                </tr>
                <tr>
                  <td width="5%"  class="table_title">NO</td>
                  <td width="65%" class="table_title"><b>회사명</b></td>
                  <td width="15%" class="table_title"><b>취업구분</b></td>
                  <td width="15%" class="table_title"><b>취업일</b></td>
                </tr>
              <!--회원구분 ID 성명 -->
            <%
            if(list != null && list.size()>0) {
                for(int i = 0; i < list.size(); i++) {
    
                    DataBox dbox     = (DataBox)list.get(i);
                    v_seq            = dbox.getInt   ("d_seq");
                    v_comp           = dbox.getString("d_comp");
                    v_compnm         = dbox.getString("d_compnm");
                    v_employgubun    = dbox.getString("d_employgubun");
                    v_employgubunnm  = dbox.getString("d_employgubunnm");
                    v_employdate     = dbox.getString("d_employdate");
                    v_subj           = dbox.getString("d_subj");
                    v_year           = dbox.getString("d_year");
                    v_subjseq        = dbox.getString("d_subjseq");
                    v_userid         = dbox.getString("d_userid");
                    
                    v_dispnum        = dbox.getInt("d_dispnum");
                    v_totalpage      = dbox.getInt("d_totalpage");
                    v_totalrowcount  = dbox.getInt("d_totalrowcount");
                %>
                <tr>
                  <td class="table_01"><%=v_dispnum %></td>
                  <td class="table_02_1"><a href="javascript:updatePage(<%=v_seq %>);"><%=v_compnm%></a></td>
                  <td class="table_02_1"><%=v_employgubunnm%></td>
                  <td class="table_02_1"><%=FormatDate.getFormatDate(v_employdate,"yyyy/MM/dd")%></td>
                </tr>
                <%
                }
            }else{
%>
                <tr>
                  <td class="table_02_1" colspan="6">검색된 내용이 없습니다.</td>
                </tr>
                <% } %>
              </table>

            <!----------------- total 시작 ----------------->
            <table width="496" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="right" valign="absmiddle">
                  <%= PageUtil.printPageList(v_totalpage, v_pageno, 10) %>
                </td>
              </tr>
            </table>
            <!----------------- total 끝 ----------------->

            <table width="496" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right" style="padding-top=10">
                <a href='javascript:update()'><img src="/images/admin/button/btn_modify.gif" border=0></a>
                <a href='javascript:listPage()'><img src="/images/admin/button/btn_cancel.gif" border=0></a>
                <a href='javascript:self.close()'><img src="/images/admin/button/btn_close.gif" border=0></a>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
