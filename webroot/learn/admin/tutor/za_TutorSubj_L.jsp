<%
//**********************************************************
//  1. 제      목: 강사 입과현황 LIST
//  2. 프로그램명: za_TutorSubj_L.jsp
//  3. 개      요: 강사 입과현황리스트
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
    String v_process		= box.getString("p_process");
    String ems_url = conf.getProperty("ems.url.value");

	String ss_action		= box.getString("s_action");
    String v_edustart		= box.getString("p_edustart");
    String v_eduend			= box.getString("p_eduend");
	
	String  v_orderColumn   = box.getString("p_orderColumn");       //정렬컬럼
	String v_orderType     = box.getStringDefault("p_orderType"," asc"); //정렬순서
	
    String v_subj			= "";
    String v_subjnm         = "";
    String v_year           = "";
    String v_subjseq		= "";
    String v_class          = "";
    String v_tuserid		= "";
    String v_tusernm        = "";
    String v_stucnt			= "";
    String v_tucnt			= "";
    String v_fmon		    = "";
    String v_tmon		    = "";
	String temtuserid		= "";

    int     i               =  0;
    //DEFINED class&variable END

	ArrayList list      = null;

	if (ss_action.equals("go")) {    //go button 선택시만 list 출력
		list = (ArrayList)request.getAttribute("TutorSubjList");
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
	$("#p_edustart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_eduend").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
		//go버튼 실행시
    function whenSelection(p_action) {
	    if (p_action=="go"){
            if(document.form1.p_edustart.value == '') {
                alert("교육시작일을 입력하세요.");
                return;
            }
            if(document.form1.p_eduend.value == '') {
                alert("교육종료일을 입력하세요.");
                return;
            }
		}

        document.form1.s_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.tutor.TutorAdminServlet';
        document.form1.p_process.value = 'TutorSubjList';  
        document.form1.submit();
    }
		
  //내용 정렬  
	function whenOrder(column) {
		if (document.form1.p_orderType.value == " asc") {
			document.form1.p_orderType.value = " desc";
		} else {
			document.form1.p_orderType.value = " asc";
		}

		 document.form1.p_orderColumn.value = column;
		 document.form1.target = "_self";
		 document.form1.action = "/servlet/controller.tutor.TutorAdminServlet";
		 document.form1.p_process.value = "TutorSubjList";
		 document.form1.submit();
	}

		
		//체크 선택된 것
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
		//전체선택
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
    
    //전체해제
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

		//개인메일		
    function whenFreeMail() {

      if (chkSelected() < 1) {
        alert('메일을 발송할 학습자를 선택하세요');
        return;
      }

      open_window("openFreeMail","","10","10","800","650");
      document.form1.target = "openFreeMail";
      document.form1.action='<%=ems_url%>/ems/MassMail.do';
      document.form1.p_process.value = 'SendFreeMail';
      document.form1.submit();

    }
	
	//수강생 리스트
	function whenStudentList(subj, year, subjseq, class1) {    	  
		window.self.name = "open";     
		open_window("opensel","","100","0","800","600","N","yes","yes","yes","yes");  
		document.form1.target = "opensel";               
		   
		document.form1.action = '/servlet/controller.tutor.TutorAdminServlet';
		document.form1.p_process.value = "TutorClassStudentList";		
		document.form1.p_subj.value = subj;
		document.form1.p_year.value = year;
		document.form1.p_subjseq.value =  subjseq;        
		document.form1.p_class.value =  class1;        
		document.form1.submit();
	}

		
		//엑셀출력
    function whenExcel() {
      if (document.form1.s_action.value == "") {
        alert('먼저 검색을 해주세요.');
        return;
      }
      
      //alert(document.form1.p_edustart.value);
      //alert(document.form1.p_eduend.value);

        window.self.name = "StudentList";     
        open_window("openExcel","","100","0","600","400","yes","yes","yes","yes","yes");  
        document.form1.target = "openExcel";               
		document.form1.action = '/servlet/controller.tutor.TutorAdminServlet';
		document.form1.p_process.value = "StudentList";		
		document.form1.submit();
    }  

-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type="hidden" name="p_process" value="<%=v_process %>">
    <input type="hidden" name="s_action" value="<%=ss_action%>">
    <input type="hidden" name="p_subj">
    <input type="hidden" name="p_year">
    <input type="hidden" name="p_subjseq">
    <input type="hidden" name="p_class">
    <input type="hidden" name="p_orderColumn" value="<%=v_orderColumn%>">
	<input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <input type="hidden" name="userLoginID" value="<%=box.getSession("userid")%>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/tutor/tit_tutor_i.gif"></td>
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
						<font color="red">★</font> 학습기간 : <input tpye="text" name="p_edustart" id="p_edustart" class="datepicker_input1" size=10 value="<%=v_edustart%>"> ~ 
						  <input tpye="text" name="p_eduend" id="p_eduend" class="datepicker_input1" size=10 value="<%=v_eduend%>">
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
		
      <!----------------- 메일발송 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td align="right">&nbsp;</td>
		  <td align="right" valign="top">
			<a href="javascript:whenFreeMail()"><img src="/images/admin/button/btn_mail.gif" border="0"></a> 
			<a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a> 
		  </td>			
        </tr>
      </table>
      <!----------------- 메일발송 버튼 끝 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="0">
          <tr>
            <td colspan="8" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="5%" class="table_title"><b>No</b></td>
            <td width="10%" class="table_title"><b>강사명</b></td>
            <td class="table_title"><a href="javascript:whenOrder('subjnm')" class="e"><b>과정</b></a></td>
            <td width="10%" class="table_title"><a href="javascript:whenOrder('year')" class="e"><b>년도</b></a></td>
            <td width="10%" class="table_title"><a href="javascript:whenOrder('subjseq')" class="e"><b>과정차수</b></a></td>
            <td width="10%" class="table_title"><a href="javascript:whenOrder('class')" class="e"><b>클래스</b></a></td>
            <td width="10%" class="table_title"><a href="javascript:whenOrder('ismanager')" class="e"><a href="javascript:whenOrder('stucnt')" class="e"><b>수강인원</b></a></td>
			<td width="5%" class="table_title"><%@ include file="/learn/admin/include/za_checkboxall.jsp" %></td>
          </tr>
<% 
	if (ss_action.equals("go")) {    //go button 선택시만 list 출력

            for(i = 0; i < list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);
				
                v_subj			= dbox.getString("d_subj");
                v_subjnm	    = dbox.getString("d_subjnm");
                v_year          = dbox.getString("d_year");
                v_subjseq		= dbox.getString("d_subjseq");
                v_class			= dbox.getString("d_class");
                v_tuserid       = dbox.getString("d_tuserid");
                v_tusernm		= dbox.getString("d_tusernm");
                v_stucnt        = dbox.getString("d_stucnt");
%>
          <tr>
            <td class="table_01"><%= list.size() - i %></td>
            <td class="table_02_1"><%=v_tusernm%></td>
            <td class="table_02_2"><%=v_subjnm%></td>
            <td class="table_02_1"><%=v_year%></a></td>
            <td class="table_02_1"><%=v_subjseq%></td>
            <td class="table_02_1"><%=v_class%></td>
            <td class="table_02_1"><a href="javascript:whenStudentList('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>','<%=v_class%>');"  class="e"><%=v_stucnt%></a></td>
            <td class="table_02_1">
<% if(!v_tuserid.equals(temtuserid)){%>
				<input type="checkbox" name="p_checks" value="<%=v_tuserid%>,<%=v_subj%>,<%=v_year%>,<%=v_subjseq%>">
<%}%>
			</td>
          </tr>
<%
			if(!temtuserid.equals(v_tuserid)) temtuserid	= v_tuserid;
		}
	}
%>
        <% if(i == 0){ %>
          <tr>
            <td class="table_02_1" colspan="8">등록된 내용이 없습니다</td>
          </tr>
          <% } %>
        </table>
        <!----------------- 강사조회 끝 ----------------->
      </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>

</body>
</html>
