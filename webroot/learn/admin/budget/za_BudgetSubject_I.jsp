<%
//**********************************************************
//  1. ��      ��: ������� ȭ��
//  2. ���α׷���: za_Subject_I.jsp
//  3. ��      ��: ������� ȭ��
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ���� 2004. 12. 15
//  7. ��      ��: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    
    //�ο�����
    ArrayList  list1 = (ArrayList)request.getAttribute("budgetmemSubjList");
    //��������
    ArrayList  list2 = (ArrayList)request.getAttribute("budgetSubjList");
    
    String  v_year		 = box.getStringDefault("s_year","");    		//�⵵	
    String  v_edugubun   = box.getStringDefault("s_edugubun","ALL");    //��������
    String  v_company    = box.getStringDefault("s_company","ALL");    	//ȸ��
    String  v_edudept    = box.getStringDefault("s_edudept","ALL");    	//�����ְ��μ�
    String  v_srchsubjnm = box.getStringDefault("p_srchsubjnm","");		//������
    
    String  ss_year		 = box.getStringDefault("s_year","");    		//�⵵	
    String  ss_edugubun   = box.getStringDefault("s_edugubun","ALL");    //��������
    String  ss_company    = box.getStringDefault("s_company","ALL");    	//ȸ��
    String  ss_edudept    = box.getStringDefault("s_edudept","ALL");    	//�����ְ��μ�
    String  ss_srchsubjnm = box.getStringDefault("p_srchsubjnm","");		//������
    String  ss_me		 = box.getString("p_me"); //�ڱⰡ ����� ����

	
%>
<html>
<head>
<title>���̹��������</title>
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
<script language = "javascript" src = "/script/tutor.js"></script>
<SCRIPT language="JavaScript">
$(document).ready(function(){
	$("#p_edustarta").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_eduenda").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--


function goSubjectListPage() {
	document.form2.target = "_self";
	document.form2.action="/servlet/controller.budget.BudgetAdminServlet"
	document.form2.p_process.value  = 'SubjList';
    document.form2.submit();
}


//��� ��ũ��Ʈ ����//
function InsertSubject() {
	ff = document.form2;
	
	
	if (ff.p_subjnm.value==""){
		alert("�������� �Է��ϼ���.");
		ff.p_subjnm.focus();
		return;
	}

	if (ff.p_edugubun.value=="ALL"){
		alert("���������� �����ϼ���.");
		return;
	}

	if (ff.p_edustarta.value==""){
		alert("������������ �Է��ϼ���.");
		ff.p_edustarta.focus();
		return; 
	}
	
	if (ff.p_eduenda.value==""){
		alert("���������� �Է��ϼ���.");
		ff.p_eduenda.focus();
		return;
	}
	
	if (ff.p_edustarta.value > ff.p_eduenda.value) {
		alert("������������ �����Ϻ��� �����Դϴ�.");
		return;
	}
	

	if (ff.p_edutmema.value==""){
		alert("�����ο������� �߸� �ԷµǾ� �ֽ��ϴ�.");
		return;
	}
	
	if (ff.p_edutseqa.value==""){
		alert("������������ �Է��ϼ���.");
		ff.p_edutseqa.focus();
		return;
	}
	
	
	if (!chk_num(ff.p_seqpermema.value)){
		alert("�����ο������� �߸� �ԷµǾ� �ֽ��ϴ�.");
		return;
	}
	
	if (ff.p_seqpermema.value=='0'){
		alert("�����ο��� �Է��ϼ���.");
		return;
	}
	
	if (!chk_num(ff.p_gyamt.value)){
		alert("���뺸��ȯ�޿���ݾ��� �߸� �ԷµǾ� �ֽ��ϴ�.");
		ff.p_gyamt.focus();
		return;
	}
	
	//���α׷� ����Ḧ ���ؼ� ���� ������ ��

	var is_ok = true; //���� ��ȿ�� �˻�
	var chk_value = "";
	var total = 0; //���α׷� �� �����
	
	for (var i=1 ;  i<=15 ; i++) {
		//���� ��ȿ�� �˻� �� �� ����� ���
		chk_value = eval('ff.p_eduprice' + i + ".value");
		if (chk_value!='') {
			if (chk_num(chk_value)) {
				total += parseInt(chk_value);
			} else {
				is_ok = false;
			}
		}
	}
	
	
	if (!is_ok) {
		alert('�ð��� ������ ���ڷθ� �Է°����մϴ�.');
		return;
	}
	
	if (total!=0) {
		if (ff.p_budget01a.value + ff.p_budget02a.value == 0) {
			alert("�系���� �Ǵ� ��ܰ���Ḧ �Է��ϼ���");
			ff.p_budget01a.focus();
			return;
		}
	}
	
	if (confirm("����Ͻðڽ��ϱ�?")) {
  		document.form2.p_process.value = 'SubjInsert';
  		document.form2.action="/servlet/controller.budget.BudgetAdminServlet"
 		document.form2.submit();
 	}

}

function cal_total(p_inputname) {
	ff = document.form2;
	var is_ok = true; //���� ��ȿ�� �˻�
	var total = 0; //�������ο�
	var chk_value = "";
	
	var cnt = 0;
	
	if (p_inputname=="ff.p_edumem")
		cnt = <%=list1.size()%>;
	else 
		cnt = <%=list2.size()%>;
	
	//�ο��Է� üũ
	for (var i=1 ;  i<=cnt ; i++) {
		if (i<10) {
			//���� ��ȿ�� �˻� �� ���ο� ���
			chk_value = eval(p_inputname + "0" + i + "a.value");
			if (chk_num(chk_value)) {
				total += parseInt(chk_value);
			} else {
				is_ok = false;
			}
		} else {
			//���� ��ȿ�� �˻� �� ���ο� ���
			chk_value = eval(p_inputname + i + "a.value");
			if (chk_num(chk_value)) {
				total += parseInt(chk_value);
			} else {
				is_ok = false;
			}
		}
	}

	if (is_ok) {
		if (p_inputname=="ff.p_edumem") {
			ff.p_edutmema.value = total;
		} else {
			ff.p_plancodetot.value = total;
		}
	} else {
		if (p_inputname=="ff.p_edumem") {
			ff.p_edutmema.value = "";
			ff.p_seqpermema.value = "";
		} else {
			ff.p_plancodetot.value = "";
		}
		return;
	}
	
	
	if (p_inputname=="ff.p_edumem") {
		//���������� üũ
		if (!chk_num(ff.p_edutseqa.value)) {
			ff.p_edutseqa.value = "";
			ff.p_seqpermema.value = "";
			return;
		}
		//������ �ο� ���
		ff.p_seqpermema.value = parseInt(ff.p_edutmema.value / ff.p_edutseqa.value);
	} else {
		//���� �ѱݾ� ���
	}

}

function chk_num(v_value) {
  if (v_value=="") return false;
  
  for (var i = 0 ;i < v_value.length ;i++ )  {
    if ((v_value.substring(i,i+1) < "0" || v_value.substring(i,i+1) > "9" )) {
      return false;
    }
  }
  return true;
}
//��� ��ũ��Ʈ ����//

function searchTutor(p_id) {
  //var url = "/servlet/controller.budget.BudgetAdminServlet?p_process=searchTutor&p_id="+p_id;
  //open_window("",url,"0","0","200","200");
  
	window.self.name = "winSearchTutor";
    farwindow = window.open("", "openWinSearchTutor", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 620, height = 550, top=0, left=0");
    document.form2.target = "openWinSearchTutor";
    document.form2.action = "/servlet/controller.budget.BudgetAdminServlet";
    document.form2.p_process.value="searchTutor";
    document.form2.p_id.value = p_id;
    document.form2.submit();
    
    farwindow.window.focus();
    document.form2.target = window.self.name;

}



function receiveTutor(p_id, tutorGubun , tutorName, tutorID) {
	var v_id = eval("document.form2.p_tutorid" + p_id);
	var v_name = eval("document.form2.p_tutorname" + p_id);
	var v_tutorgubun = eval("document.form2.p_tutorgubun" + p_id);
	var v_tutorgubunnm = eval("document.form2.p_tutorgubunnm" + p_id);
	var v_eduprice     = eval("document.form2.p_eduprice" + p_id);
	
	v_id.value=tutorID;
	v_name.value = tutorName;
	v_tutorgubun.value = tutorGubun;
	
	if (tutorGubun == 'I') {
		v_tutorgubunnm.value = '�系����';
		
		if (v_eduprice.value == '') {
			v_eduprice.value = '15000';
		}		
	} else if (tutorGubun == 'O') {
		v_tutorgubunnm.value = '��ܰ���';
	} else {
		v_tutorgubunnm.value = '�������';
	}	
}

function tutorCancel(p_id) {
	var v_id = eval("document.form2.p_tutorid" + p_id);
	var v_name = eval("document.form2.p_tutorname" + p_id);
	var v_tutorgubun = eval("document.form2.p_tutorgubun" + p_id);
	var v_tutorgubunnm = eval("document.form2.p_tutorgubunnm" + p_id);
	
	v_id.value = "";
	v_name.value = "";
	v_tutorgubun.value = "";
	v_tutorgubunnm.value = "";
}
-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name="form2" method="post" action="/servlet/controller.budget.BudgetAdminServlet">
    <input type="hidden" name="p_process"     value="">
    <input type="hidden" name="p_id"     value="">
    
    <input type="hidden" name="s_year"     		value="<%=ss_year%>">		<!-- �⵵ -->
    <input type="hidden" name="s_edugubun"     	value="<%=ss_edugubun%>">	<!-- �������� -->
    <input type="hidden" name="s_company"     	value="<%=ss_company%>">	<!-- ȸ�� -->
    <input type="hidden" name="s_edudept"     	value="<%=ss_edudept%>">	<!-- �����ְ��μ� -->
    <input type="hidden" name="p_srchsubjnm"    value="<%=ss_srchsubjnm%>">	<!-- ������ -->
    <input type="hidden" name="p_me"     		value="<%=ss_me%>">			<!-- �ڱⰡ ����� ���� -->
    
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
      <!----------------- title ���� ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/tutor/t_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title �� ----------------->
	   <br>
	           <!-------------������ ����-------------------------->
 <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>����/��������</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        
      <!--------------������ ��---------------------------->
        <table width="970"  cellpadding="0" cellspacing="1" class="table_out">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title_bold">������</td>
            <td width="68%" class="table_02_2"><input name="p_subjnm" type="text" class="input" size="118" maxlength="60"></td>
          </tr>
          <tr> 
            <td class="table_title_bold"><b>��������</b></td>
            <td class="table_02_2"><%=CodeConfigBean.getCodeGubunSelect("0024", "", 1, "p_edugubun", "", "", 2)%></td>
          </tr>
          <tr> 
            <td class="table_title_bold"><b>�������</b></td>
            <td class="table_02_2"><input name="p_edutargeta" type="text" class="input" size="118" maxlength="100"></td>
          </tr>
          <tr> 
            <td class="table_title_bold"><b>�������</b></td>
            <td class="table_02_2"><input name="p_eduplacea" type="text" class="input" size="118" maxlength="100"></td>
          </tr>
          <tr> 
            <td class="table_title_bold"><b>����������</b></td>
            <td class="table_02_2"><input name="p_edustarta" id="p_edustarta" type="text" class="datepicker_input1" size="10" value=""></td>
          </tr>
          <tr> 
            <td class="table_title_bold"><b>����������</b></td>
            <td class="table_02_2"><input name="p_eduenda" id="p_eduenda" type="text" class="datepicker_input1" size="10" maxlength=10  value=""></td>
          </tr>
          <tr align="center"> 
            <td colspan="2" bgcolor="#FFFFFF" style=padding-top:5;padding-bottom:5;><table width="98%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="50%" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="1" >
                      <tr> 
                        <td height="14" colspan="2" class="table_title_bold"><b>�����ο�</b></td>
                      </tr>
                      <!-- �ο����� ���� -->
                      <%  for (int i=0; i<list1.size(); i++) {
				        DataBox dbox = (DataBox)list1.get(i); %>
                      <tr> 
                        <td width="37%" class="table_title_l" style="padding-left:30px;"><%=dbox.getString("d_codenm")%></td>
                        <td width="63%" class="table_01"> <input name="p_edumem<%=dbox.getString("d_code")%>a" type="text" class="input_r" size="20" maxlength="5" value="<%=dbox.getInt("d_plandata")%>" onkeyup="cal_total('ff.p_edumem');"> <!--formatTextfield(this);-->
                          ��</td>
                      </tr>
                      <%  } %>
                      <input type="hidden" name="p_edumemcnt" value="<%=list1.size()%>">
                      <!-- �����ο����� �� -->
                    </table></td>
                  <td width="1%">&nbsp;</td>
                  <td width="49%"><table width="100%" border="0" cellpadding="0" cellspacing="1" >
                      <tr> 
                        <td colspan="2" class="table_title_bold"><b>����</b></td>
                      </tr>
					  	<!-- �������� ���� -->
				<% for (int i=0; i<list2.size(); i++) {
				        DataBox dbox = (DataBox)list2.get(i); %>
                <tr> 
                  <td width="29%" class="table_title_l" style="padding-left:40px;"><%=dbox.getString("d_codenm")%></td>
                  <td width="34%" class="table_01"> <input name="p_budget<%=dbox.getString("d_code")%>a" type="text" class="input_r" size="20" maxlength="8" value="<%=dbox.getInt("d_plandata")%>" onkeyup="cal_total('ff.p_budget')">
                    �� </td>
                </tr>
				<%  } %>
				<tr> 
                  <td width="29%" class="table_title">��</td>
                  <td width="34%" class="table_01"> <input name="p_plancodetot" type="text" class="input_r" size="20" maxlength="12" value="0" readonly>
                    �� </td>
                </tr>
                <input type="hidden" name="p_budgetcnt" value="<%=list2.size()%>">
                <!-- �������� �� -->
                    </table></td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td class="table_title">�������ο�</td>
            <td width="81%" class="table_02_2"><input name="p_edutmema" type="text" class="input_r" size="20" readonly value="0"> ��</td>
          </tr>
          <tr> 
            <td class="table_title">����������</td>
            <td class="table_02_2"><input name="p_edutseqa" type="text" class="input_r" size="20" maxlength="2" onkeyup="cal_total('ff.p_edumem')"></td>
          </tr>
          <tr> 
            <td class="table_title">�������ο�</td>
            <td class="table_02_2"><input name="p_seqpermema" type="text" class="input_r" size="20" readonly> ��</td>
          </tr>
          <tr> 
            <td class="table_title">���뺸��ȯ�޿���ݾ�</td>
            <td class="table_02_2"><input name="p_gyamt" type="text" class="input_r" size="20" maxlength="10" value="0">
              ��</td>
          </tr>
        </table>
        <br>
		<!-------------������ ����-------------------------->
 <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>���α׷�</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        <!--------------������ ��---------------------------->

        <table cellspacing="1" cellpadding="5" class="table_out">
      <tr>
          <td colspan="7" class="table_top_line"></td>
        </tr>
          <tr> 
            <td width="3%" class="table_title">��ȣ</td>
            <td width="30%" class="table_title">������</td>
            <td width="22%" class="table_title">�����/���������</td>
            <td width="16%" class="table_title">���/����ڹ�ȣ</td>
            <td width="8%" class="table_title">���籸��</td>
            <td width="7%" class="table_title">������<br>�����ð�(hr)</td>
            <td width="14%" class="table_title">�ð��簭���(��)</td>
          </tr>
          <% for (int i=1;i<16;i++) { %>
          <tr> 
            <td class="table_02_2"><%=i%></td>
            <td class="table_02_2"><input name="p_lessonnm<%=i%>" type="text" class="input" size="45" maxlength="100"></td>
            <td class="table_02_2"> <input name="p_tutorname<%=i%>" type="text" class="input" size="20" maxlength="30" readonly onclick="searchTutor('<%=i%>')"> 
              <a href="javascript:searchTutor('<%=i%>');">�˻�</a> <a href="javascript:tutorCancel('<%=i%>');">���</a></td>
            <td class="table_02_1"><input name="p_tutorid<%=i%>" type="text" class="input" size="20" maxlength="20" readonly></td>
            <td class="table_02_1"><input name="p_tutorgubun<%=i%>" type="hidden">
			<input name="p_tutorgubunnm<%=i%>" type="text" class="input" size="8" readonly></td>
            <td class="table_02_1"><input name="p_eduhr<%=i%>" type="text" class="input_r" size="4" maxlength="3"></td>
            <td class="table_02_1"><input name="p_eduprice<%=i%>" type="text" class="input_r" size="10" maxlength="8"></td>
          </tr>
          <% } %>
          <input type="hidden" name="p_lessoncnt" value="15">
          <!--input type="text" name="txtcurr" size="50" style="width:90%;" class="input_write" value="1��">
          <div id="sw_file_add_form"></div-->
        </table> 
        <!----------------- ���������� ��� �� ----------------->
        <br>
      <!----------------- ���, ����Ʈ ��ư ���� ----------------->
      <table  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center"><a href="javascript:InsertSubject()"><img src="/images/admin/button/btn_apply.gif"  border="0"></a></td>
		  <td width=8></td>
          <td align="center"><a href="javascript:goSubjectListPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- ���, ����Ʈ ��ư �� ----------------->
      <br>
      </td>
  </tr>
</table>


<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>