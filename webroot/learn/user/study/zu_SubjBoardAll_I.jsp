<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. ��      ��: �����Խ���
//  2. ���α׷���: zu_StudyBoard_I.jsp
//  3. ��      ��: �����Խ��� ���
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 
//  7. ��      ��:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    int     v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  ������ ������ִ� ���ϼ�
    String  v_subj		 = box.getString("p_subj");        //�����ڵ�
    String  v_year		 = box.getString("p_year");        //�⵵
    String  v_subjseq	 = box.getString("p_subjseq");     //���� ����

    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");

    // editor
    String content      = "";
    String width        = "600";
    String height       = "300";

%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>�Խ���</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script type="text/javascript" src="/namo_cross/js/namo_scripteditor.js"></script> 
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/user_patch.js'></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>  
<script language="JavaScript">
<!--
// ���
function insert() {
    if (blankCheck(document.form1.p_title.value)) {
        alert("������ �Է��ϼ���!");
        document.form1.p_title.focus();
        return;
    }
    if (realsize(document.form1.p_title.value) > 200) {
        alert("������ �ѱ۱��� 100�ڸ� �ʰ����� ���մϴ�.");
        document.form1.p_title.focus();
        return;
    }

	if(!CrossEditor.IsDirty()){ // ũ�ν������� ���� ������ �Է� Ȯ�� 
        alert("������ �Է��� �ּ��� !!"); 
        CrossEditor.SetFocusEditor();// ũ�ν������� Focus �̵� 
        return; 
    } 

    //if(!limitFile()){
    //    alert("���ε� �������� ���� ������ �����մϴ�.");
    //    return;
    //}
  	    	    	    
    document.getElementById("p_content").value = CrossEditor.GetBodyValue();
    document.form1.target = "_self";
    document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
    document.form1.p_search.value     = "";
    document.form1.p_searchtext.value = "";
    document.form1.p_process.value = "insert";
    document.form1.submit();
}

// ��������̵�
function list() {
    document.form1.target = "_self";
    document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
    document.form1.p_process.value = "";
    document.form1.submit();
}

//-->
</script>
</head>
<body id="pop_study"><!-- popup size : 890* -->
<form name="form1" method="post" enctype = "multipart/form-data">
    <input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>"/>
    <input type = "hidden" name = "p_process"    value = "<%=v_process %>"/>
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>"/>
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>"/>
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>"/>
    <input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>"/>
    <input type = "hidden" name = "p_isedu"      value = "0"/>
    <input type = "hidden" name = "p_subj"       value = "<%= v_subj %>"/>
    <input type = "hidden" name = "p_year"       value = "<%= v_year %>"/>
    <input type = "hidden" name = "p_subjseq"    value = "<%= v_subjseq %>"/>
	<!-- �˻����� ������ -->
    <input type = "hidden" name = "s_grcode" value="<%= box.get("s_grcode") %>"/>
    <input type = "hidden" name = "s_gyear" value="<%= box.get("s_gyear") %>"/>
    <input type = "hidden" name = "s_grseq" value="<%= box.get("s_grseq") %>"/>
    <input type = "hidden" name = "s_upperclass" value="<%= box.get("s_upperclass") %>"/>
    <input type = "hidden" name = "s_middleclass" value="<%= box.get("s_middleclass") %>"/>
    <input type = "hidden" name = "s_lowerclass" value="<%= box.get("s_lowerclass") %>"/>
    <input type = "hidden" name = "s_subjcourse" value="<%= box.get("s_subjcourse") %>"/>
    <input type = "hidden" name = "s_subjseq" value="<%= box.get("s_subjseq") %>"/>
    <input type = "hidden" name = "s_subjsearchkey" value="<%= box.get("s_subjsearchkey") %>"/>

	<div id="pop_header">
		<h1><img src="/images/portal/myclass/study/pop_h1_tit6.gif" alt="�Խ���" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap">
			<div id="con_scoll">
<% if (box.get("d_subjnm").length()>0) { %>
				<div class="study_tltle study_tltle_sub"><div class="study_tltle_left"><div class="study_tltle_right">
					<dl class="class_info"> 
						<dt><span>������</span></dt>
						<dd><%= box.get("d_subjnm") %></dd>
					</dl>
					<dl class="class_info">
						<dt><span>�����Ⱓ</span></dt>
						<dd class="class_day"><%= FormatDate.getFormatDate(box.get("d_edustart"),"yyyy.MM.dd") %> ~<%= FormatDate.getFormatDate(box.get("d_eduend"),"yyyy.MM.dd") %></dd>
					</dl>
				</div></div></div>
<%	} %>
				<div class="content">
					<div class="h3_wrap">
						<h3><img src="/images/portal/myclass/study/pop_stit8.gif" alt="�Խ���" /></h3>
					</div>
					
					<table class="study_write">
					<!--[if ie]><colgroup><col width="45px" /><col width="100px" /><col width="30px" /><col width="" /></colgroup><![endif]-->
					<colgroup><col width="65px" /><col width="100px" /><col width="50px" /><col width="" /></colgroup>
					<tr class="bo">
						<th class="th">����</th>
						<td colspan="7" class="td"><input type="text" name="p_title" class="inbox" style="width:590px;" /></td>
					</tr>
					<tr>
						<th class="th">�ۼ���</th>
						<td class="td txt"><%= s_username %></td>
						<th class="th">�ۼ���</th>
						<td class="td stxt ff_t"><%= FormatDate.getDate("yyyy.MM.dd") %></td>
					</tr>
					<tr>
						<td colspan="8" class="td con">
		                    <textarea  id="p_content" name="p_content" style="display:none;"></textarea>
							<script type="text/javascript">
							    var CrossEditor = new NamoSE("contents");
							    CrossEditor.editorStart();
							    CrossEditor.SetUISize("820","500");
							</script>
						</td>
					</tr>
					</table>

		            <!-- ����÷�� ���� -->
                    <%
                    int    i_fileLimit         = HomePageQnaBean.getFILE_LIMIT();             // ���� ÷�� ���ϼ�
                    %>
                    
                    <%//@ include file="/learn/user/portal/include/multiAttach_include.jsp" %>
                    <%@ include file="/learn/admin/include/za_MultiAttach_I.jsp" %>
                    <!-- ����÷�� ���� -->
					
					<p class="list_btn"><a href="javascript:insert()" class="btn_gr"><span>���</span></a><a href="javascript:list()" class="btn_gr"><span>���</span></a></p>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>