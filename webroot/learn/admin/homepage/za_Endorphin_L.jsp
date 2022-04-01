<%
//**********************************************************
//  1. 제      목: Endorphin
//  2. 프로그램명: za_Endorphin_L.jsp
//  3. 개      요: 엔돌핀 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정경진 2005. 8. 03
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");    
    String  v_process   = box.getString("p_process");    
    String  v_grcode	= "";  
    String  v_usestartdt= "";  
    String  v_useenddt	= "";  
    String  v_content	= "";  
	String  v_isuse		= "";
    String  v_ldate     = "";    

    int     v_seq		=  0;
    int     i           =  0;    

    String  ss_action   = box.getString("s_action");    

	ArrayList list      = null;

 	if (ss_action.equals("go"))  {
		list = (ArrayList)request.getAttribute("endorphinList");
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
<SCRIPT LANGUAGE="JavaScript">
$(document).ready(function(){
	$("#p_usestartdt").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_useenddt").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});

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
		document.form1.p_process.value = "selectList";
		document.form1.submit();
	}

	function endorphin_insert() {  	  	
		
	    if (chkSelected() < 1) {
			alert('등록할 엔돌핀을 선택하세요.');
			return;
	    }

		if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
		}

		if (document.form1.p_usestartdt.value == "") {
			alert("사용시작일을 입력하세요.");
			document.form1.p_usestartdt.focus();
			return;
		}

		if (document.form1.p_useenddt.value == "") {
			alert("사용시작일을 입력하세요.");
			document.form1.p_useenddt.focus();
			return;
		}

		if(!confirm("등록하시겠습니까?")) {
			 return;
		}  
		
		document.form1.target = "_self";
		document.form1.p_process.value = 'selectInsert';        
		document.form1.submit();
	}

	function endorphin_view(){
		window.self.name = "EndolphinView";     
		open_window("openPool","","100","0","800","600","N","yes","yes","yes","yes");  
		document.form1.target = "openPool";               
		document.form1.p_process.value = 'List';
		document.form1.submit();
	}

    function chkSelected() {
      var selectedcnt = 0;
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            if (document.form1.p_checks[i].checked == true) {
    	      selectedcnt++;
    	    }
          }
        } else {
          if (document.form1.p_checks.checked == true) {
    	    selectedcnt++;
          }
        }
      }
      return selectedcnt; 
    }

    function whenAllSelect() {
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            document.form1.p_checks[i].checked = true;
          }
        } else {
          document.form1.p_checks.checked = true;
        }
      } 
    }
    
    function whenAllSelectCancel() {
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            document.form1.p_checks[i].checked = false;
          }
        } else {
          document.form1.p_checks.checked = false;
        }
      }
    }


</SCRIPT>    
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post" action="/servlet/controller.homepage.EndorphinServlet">
    <input type="hidden" name="p_process" value="">
    <input type="hidden" name="s_action" value="">
    <input type="hidden" name="p_seq" value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="/images/admin/homepage/tit_endol.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->
      <br>

	   <table width="97%" cellpadding="1" cellspacing="0" class="form_table_out">
        <tr> 
          <td bgcolor="#C6C6C6" align="center">
		    <table width="97%" cellpadding="0" cellspacing="0" class="form_table_bg" border="0">
              <tr> 
                <td height="7"></td>
              </tr>
              <tr> 
                <td width="90%" align="left"> 
				  &nbsp;<font color="red">★</font>&nbsp;<%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->					
				</td>
				<td width="10%" align="center"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->

              </tr> 
              <tr> 
                <td height="7"></td>
              </tr>
            </table>
		  </td>
        </tr>
      </table>
	  <br>

     <!-------------소제목 시작-------------------------->
	  <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>엔돌핀 목록</td>
        </tr>
       <tr> 
         <td height=6></td>
         <td height=6></td>
       </tr>
      </table> 
      <!--------------소제목 끝---------------------------->
      <!----------------- 엑셀출력 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td align="right">&nbsp;</td>
		  <td align="right"valign="top">
		    <a href="javascript:endorphin_view()"><img src="/images/admin/button/btn_poolplus.gif" border="0" tabindex=73></a>
		    <a href="javascript:endorphin_insert()"><img src="/images/admin/button/btn_add.gif" border="0" tabindex=73></a>
		  </td>
        </tr>
      </table>
      <!----------------- 엑셀출력 버튼 끝 ----------------->
	  <!----------------- 추가 버튼 끝 ----------------->
      <table width="97%" border="0" cellspacing="1" cellpadding="0" class="table_out">
        <tr> 
          <td width="5%"  class="table_title">No</td>
          <td width="18%" class="table_title">교육그룹</td>
          <td class="table_title">내용</td>
          <td width="17%" class="table_title">사용기간</td>
          <td width="5%" class="table_title"><%@ include file="/learn/admin/include/za_checkboxall.jsp" %></td>
        </tr>

<%
if (ss_action.equals("go"))  {
	for( i = 0 ; i < list.size() ; i++ ){
		DataBox dbox = (DataBox)list.get(i);		 
		

		v_grcode	= dbox.getString("d_grcode");  
		v_seq		= dbox.getInt("d_seq");  
		v_usestartdt= FormatDate.getFormatDate(dbox.getString("d_usestartdt"),"yyyy-MM-dd");  
		v_useenddt	= FormatDate.getFormatDate(dbox.getString("d_useenddt"),"yyyy-MM-dd");  
		v_content	= dbox.getString("d_content");  
		v_ldate		= dbox.getString("d_ldate");
%> 
		<tr> 
		  <td class="table_title"><%=list.size()-i%></td>
		  <td class="table_02_1"><%=v_grcode.equals("")?"교육그룹없음":GetCodenm.get_grcodenm(v_grcode)%></td>
		  <td class="table_02_2"><%=v_content%></td>
<%if (i==0){%>
		  <td class="table_02_1" valign="top" rowspan="<%=list.size()%>"><br><input tpye="text" name="p_usestartdt" id="p_usestartdt" class="datepicker_input1" size=10 value="<%=v_usestartdt%>"> ~ 
		  <input tpye="text" name="p_useenddt" id="p_useenddt" class="datepicker_input1" size=10 value="<%=v_useenddt%>"></td>
<%}%>
          <td class="table_02_1"><input type="checkbox" name="p_checks" value="<%=v_seq%>" <%=v_grcode.equals("")?"":"checked"%>></td>
		</tr>
<%		
	}
}
%>
	  </table>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>

</body>
</html>
