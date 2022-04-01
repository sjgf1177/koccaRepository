<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");    
    String  v_process   = box.getString("p_process");
    String  v_userid     = box.getString("p_userid");
    
    
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

    String v_subj         = "";
    String v_subjnm       = "";
    String v_year         = "";
    String v_subjseq      = "";
    String v_name         = "";
    String v_studentno    = "";
    String v_state        = "";
    String v_statenm      = "";
    String v_changereason = "";
    String v_changedate   = "";
    String v_isterm       = "";
    String v_statecode    = "";
    
    Vector stateHistory   = null;
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
    	v_subj            = dbox.getString("d_subj");
    	v_subjnm          = dbox.getString("d_subjnm");
    	v_year            = dbox.getString("d_year");
    	v_name            = dbox.getString("d_name");
    	v_subjseq         = dbox.getString("d_subjseq");
    	v_studentno       = dbox.getString("d_studentno");
    	v_state           = dbox.getString("d_stustatus");
    	v_isterm          = dbox.getString("d_isterm");      
    	
    	v_statecode       = v_isterm.equals("Y") ? "0089" : "0100";
    	
    	stateHistory      = (Vector)dbox.getObject("d_stateHistory");  
    	
    }
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/hhi_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">  
$(document).ready(function(){
	$("#p_date").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});

function update() {
    
    var changedate = make_date(document.form1.p_date.value);

    if (blankCheck(document.form1.p_changereason.value)) {
        alert("변경사유를 입력하세요");
        document.form1.p_changereason.focus();
        return;
    }

    if (blankCheck(document.form1.p_date.value)) {
        alert("변경일자를 입력하세요");
        document.form1.p_date.focus();
        return;
    }

    if(document.form1.p_stustate.value == "ALL" || document.form1.p_stustate.value == ""){
        alert("학적을 선택하세요");
        document.form1.p_stustate.focus();
        return;
    }

    if(document.form1.p_stustate.value == "<%=v_state%>"){
    	 alert("학적이 변경되어있지 않습니다.");
         document.form1.p_stustate.focus();
         return;
    }        

    document.form1.p_changedate.value  = changedate;
    document.form1.action = "/servlet/controller.off.OffSchRegAdminServlet";
    document.form1.p_process.value = "update";
    document.form1.submit();
}

</SCRIPT>                  

</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1">
	<input type="hidden" name="p_process"/>
	<input type="hidden" name="p_subj"      id="p_subj"         value="<%=v_subj %>">
	<input type="hidden" name="p_changedate"id="p_changedate"   value="">
    <input type="hidden" name="p_subjnm"    value="">
    <input type="hidden" name="p_year"      id="p_year"         value="<%=v_year %>">
    <input type="hidden" name="p_subjseq"   id="p_subjseq"      value="<%=v_subjseq %>">
    <input type="hidden" name="p_userid"    id="p_userid"       value="<%=v_userid %>">
    <input type="hidden" name="p_oldstate"  id="p_oldstate"     value="<%=v_state %>">
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
    
<table width="600" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr> 
    <td align="left"> 
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
          <tr> 
            
          <td align="center" valign="top" bgcolor="#FFFFFF"> 
    <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/portal/s.1_off08.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
            <br>

            <table cellspacing="1" cellpadding="5" class="open_table_out">
              <tr>
                <td colspan="2" class="table_top_line"></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >과정명</td>
                <td class="table_02_2"  width="80%" ><%=v_subjnm %></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >차수</td>
                <td class="table_02_2"  width="80%" ><%=Integer.parseInt(v_subjseq) %>기</td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >성명</td>
                <td class="table_02_2"  width="80%" ><%=v_name %></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >학번</td>
                <td class="table_02_2"  width="80%" ><%=v_studentno %></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >학적상태</td>
                <td class="table_02_2"  width="80%" >
                  <kocca_select:select                                       
                  name="p_stustate"                                         
                  sqlNum="0001"                                    
                  param="<%=v_statecode %>"             
                  onChange=""                         
                  attr= "학적"                                        
                  selectedValue="<%=v_state%>"                         
                  isLoad="true"                                           
                  type="1"                                                
                  styleClass="table_02_2"                                
                  all="true" />     
                </td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >변경일자</td>
                <td class="table_02_2"  width="80%" >
                  <input name="p_date" id="p_date" class="datepicker_input1" type="text" size="10" value='<%=FormatDate.getFormatDate(v_changedate, "yyyy/MM/dd") %>'>
                </td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >변경사유</td>
                <td class="table_02_2"  width="80%" >
                  <input type="text" name = "p_changereason" class="input" style="width:400;" />
                </td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >학적변동기록</td>
                <td class="table_02_2"  width="80%" >
                  <table width="98%" cellspacing="1" cellpadding="5" class="table_out">
                    <tr>
                      <td class="table_title" width="33.3%">학적상태</td>
                      <td class="table_title" width="33.3%">변경일자</td>
                      <td class="table_title" width="33.3%">변경사유</td>
                    </tr>
                    <%
                      if(stateHistory != null && stateHistory.size() > 0) {
                    	  for(int i = 0 ; i < stateHistory.size() ; i++){
                    		  DataBox sbox = (DataBox)stateHistory.get(i);
                    		  
                    		  v_statenm      = sbox.getString("d_statenm");
                    		  v_changedate   = sbox.getString("d_changedate");
                    		  v_changereason = sbox.getString("d_changereason");
                    		  
                    		  v_changedate = FormatDate.getFormatDate(v_changedate, "yyyy-MM-dd");
                    %>
                    <tr>
                      <td class="table_02_1"><%=v_statenm %></td>
                      <td class="table_02_1"><%=v_changedate %></td>
                      <td class="table_02_1"><%=v_changereason %></td>
                    </tr>
                    <%
                    	  }
                      }
                    %>
                  </table>
                </td>
              </tr>
            </table>

            <!----------------- 닫기 버튼 시작 ----------------->
            <table cellspacing="0" cellpadding="0" class="open_table1">
              <tr> 
                <td align="right" style="padding-top=10">
                <a href="javascript:update()"><img src="/images/admin/button/btn_add.gif" border="0"></a>
                <a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a>
                </td>
              </tr>
            </table>
            <!----------------- 닫기 버튼 끝 ----------------->
              <br>




          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>

</body>
</html>
