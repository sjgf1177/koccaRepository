<%
//**********************************************************
//  1. ��      ��: PROJECT QUESTION ADMIN LIST
//  2. ���α׷���: za_ProjectQuestionsAdmin_L.jsp
//  3. ��      ��: ����Ʈ ���� ����
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��:
//  7. ��      ��:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_subj      = "";
    String  v_year      = "";
    String  v_subjseq   = "";
    String  v_subjseqgr   = "";
    String  v_isclosed  = "";
    String  v_subjnm    = "";
    String  v_isonoff   = "";
    String  v_isnewcourse ="";
    String  v_course    = "";
    String  v_cyear     = "";
    String  v_courseseq = "";
    String  v_coursenm  = "";
    String  v_closed_value = "";
    String  v_onoff_value  = "";
    String v_edustart   =   "";
    String v_eduend     =   "";

    String  v_tmp_subj  = "";
    String  v_iscourseYn = "";

    int     v_projseqcnt= 0;
    int     v_ordseqcnt = 0;
    int     v_rowspan   =  0;
    int     i           = 0;
    int     l           = 0;
    int     v_subjcnt   = 0;
    ArrayList list1     = null;

    //DEFINED class&variable END

    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");           //�����׷�
    String  ss_gyear     = box.getString("s_gyear");            //�⵵
    String  ss_grseq     = box.getString("s_grseq");            //��������
    String  ss_grseqnm   = box.getString("s_grseqnm");          //����������
    String  ss_upperclass = box.getString("s_upperclass");      //������з�
    String  ss_middleclass = box.getString("s_middleclass");    //�����ߺз�
    String  ss_lowerclass = box.getString("s_lowerclass");      //�����Һз�
    String  ss_subjcourse= box.getString("s_subjcourse");       //����&�ڽ�
    String  ss_subjseq   = box.getString("s_subjseq");          //���� ����
    String  ss_action    = box.getString("s_action");
    String  v_ongo      = "whenSelection('go')";

    String  ss_area=box.getString("s_area");					  // �������� [����/���/����]
    String ss_biyong=box.getString("s_biyong");					  // ��/����
    //DEFINED in relation to select END

    if(ss_action.equals("go")){    //go button ���ýø� list ���
        list1 = (ArrayList)request.getAttribute("projectQuestionsAList");
    }

    //����¡
    int v_pageno = box.getInt("p_pageno");
    if (v_pageno == 0)  v_pageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //���ļ���
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function whenSelection(ss_action) {
       if (ss_action=="go") {
    	   if (chkParam() == false) {
               return;
           }
           top.ftop.setPam();
      }
      document.form1.s_action.value = ss_action;
      document.form1.p_pageno.value = "1";
      document.form1.action='/servlet/controller.study.ProjectAdminServlet';
      document.form1.p_process.value = 'ProjectQuestionsAdmin';
      document.form1.submit();
    }

    // ����Ʈ ����Ʈ ����
    function projectlist_select(subj,subjnm,year,subjseq,subjseqgr,eduend) {
        document.form1.action='/servlet/controller.study.ProjectAdminServlet';
        document.form1.p_process.value = 'ProjectQuestionsList';
        document.form1.p_subj.value = subj;
        document.form1.p_subjnm.value = subjnm;
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_subjseqgr.value = subjseqgr;
        document.form1.p_eduend.value = eduend;
        document.form1.submit();
    }

    function whenOrder(column) {
	    if (document.form1.p_orderType.value == " asc") {
	        document.form1.p_orderType.value = " desc";
	    } else {
	        document.form1.p_orderType.value = " asc";
	    }
	
	    document.form1.s_action.value = "go";
	
	    document.form1.p_orderColumn.value = column;
	    whenSelection("go");
    }

	//�˻����� üũ
	function chkParam() {
	  if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
	    alert("�����׷��� �����ϼ���.");
	    return false;
	  }
	  if (document.form1.s_gyear.value == "" || document.form1.s_gyear.value == '----') {
	      alert("������ �����ϼ���.");
	      return false;
	    }
	  if (document.form1.s_grseq.value == "" || document.form1.s_grseq.value == '----') {
	    alert("���������� �����ϼ���.");
	    return false;
	  }
	}
  
	//������ �̵�
	function go(index) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = index;
		document.form1.action = "/servlet/controller.study.ProjectAdminServlet";
		document.form1.p_process.value = "ProjectQuestionsAdmin";
		document.form1.submit();
	}
	//������ �̵�
	function goPage(pageNum) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = pageNum;
		document.form1.action = "/servlet/controller.study.ProjectAdminServlet";
		document.form1.p_process.value = "ProjectQuestionsAdmin";
		document.form1.submit();
	}
	
	// select box ����
    $(function() {
        $("#oGrcode").bind("change", function(){
            var param = "type=sqlID&sqlID=selectBox.grYearList&param=" + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetGrYear
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

        $("#oGyear").bind("change", function(){
            var grcode = $("#oGrcode").val();
            var param = "type=sqlID&sqlID=selectBox.grSeqList&param=" + grcode + "," + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetGrSeq
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

        $("#oGrseq").bind("change", function(){
            var grcode = $("#oGrcode").val();
            var gyear = $("#oGyear").val();
            var subjSearchKey = $("#oSubjSearchKey").val();
            var param;
            subjSearchKey = (subjSearchKey == "") ? "" : subjSearchKey;
            if(subjSearchKey == "")
            	param = "type=sqlID&sqlID=selectBox.subjListAll&param=" + grcode + "," + gyear + "," + $(this).val();
            else 
            	param = "type=sqlID&sqlID=selectBox.subjList&param=" + grcode + "," + gyear + "," + $(this).val() + "," + subjSearchKey;
            
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "post"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetSubjList
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

        $("#oSubjcourse").bind("change", function(){

            if ( $(this).val() == "" ) {
                $("#oSubjseq").val("");
            } else {
                var grcode = $("#oGrcode").val();
                var gyear = $("#oGyear").val();
                var grseq = $("#oGrseq").val();
                var param = "type=sqlID&sqlID=selectBox.subjSeqList&param=" + grcode + "," + gyear + "," + grseq + "," + $(this).val();

                $.ajaxSetup({cache:false});
                $.ajax({
                        type : "get"
                    ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                    ,   dataType : "json"
                    ,   data : param
                    ,   success : fnSetSubjSeqList
                    ,   complete : function(arg1, arg2) {
                            // alert("complete : " + arg1);
                        }
                    ,   error :  function(arg1, arg2) {
                            // alert("error : " + arg1);
                        }
                });
            }
        });

        $("#oUpperclass").bind("change", function(){
            var param = "type=sqlID&sqlID=selectBox.subjAttMiddleList&param=" + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetMiddleClass
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

        $("#oMiddleclass").bind("change", function(){
            var param = "type=sqlID&sqlID=selectBox.subjAttLowerList&param=" + $("#oUpperclass").val() + "," + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetLowerClass
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

    });

    /**
     * ���� �׷캰 ���� selectbox ����
     */
    function fnSetGrYear( result ) {
        $("#oGyear").empty();
        $("#oGrseq").empty();
        $("#oSubjcourse").empty();
        $("#oSubjseq").empty();
        $("#oGyear").append("<option value=\"\">== ���� ==</option>");
        $("#oGrseq").append("<option value=\"\">== ���� ==</option>");
        $("#oSubjcourse").append("<option value=\"ALL\">== ��ü ==</option>");
        $("#oSubjseq").append("<option value=\"ALL\">== ��ü ==</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oGyear").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }

    /**
     * ���� �׷� ������ �������� selectbox ����
     */
    function fnSetGrSeq( result ) {
        $("#oGrseq").empty();
        $("#oSubjcourse").empty();
        $("#oSubjseq").empty();
        $("#oGrseq").append("<option value=\"\">== ���� ==</option>");
        $("#oSubjcourse").append("<option value=\"ALL\">== ��ü ==</option>");
        $("#oSubjseq").append("<option value=\"ALL\">== ��ü ==</option>");


        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oGrseq").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });
        }
    }

    /**
     * ���������� ���� selectbox ����
     */
    function fnSetSubjList( result ) {
        $("#oSubjcourse").empty();
        $("#oSubjseq").empty();
        $("#oSubjcourse").append("<option value=\"ALL\">== ��ü ==</option>");
        $("#oSubjseq").append("<option value=\"ALL\">== ��ü ==</option>");


        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oSubjcourse").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });
        }
    }

    /**
     * ������ �������� selectbox ����
     */
    function fnSetSubjSeqList( result ) {

        $("#oSubjseq").empty();
        $("#oSubjseq").append("<option value=\"ALL\">== ��ü ==</option>");
        
        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oSubjseq").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }

    /**
     * �˻����ǿ��� ��з� ���ý� callback function
     */
    function fnSetMiddleClass( result ) {
        $("#oMiddleclass").empty();
        $("#oMiddleclass").append("<option value=\"\">== ��ü ==</option>");
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"\">== ��ü ==</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oMiddleclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }

    /**
     * �˻����ǿ��� �ߺз� ���ý� callback function
     */
    function fnSetLowerClass( result ) {
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"\">== ��ü ==</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oLowerclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }
    /**
     * ?
     */
    function fnSubjSearchByName() {
        $("#oGrseq").trigger("change");
    }
//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
    <form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->
    <input type="hidden" name="p_subj">
    <input type="hidden" name="p_subjnm">
    <input type="hidden" name="p_year">
    <input type="hidden" name="p_subjseq">
    <input type="hidden" name="p_subjseqgr">
    <input type="hidden" name="p_eduend">
    <input type="hidden" name="p_pageno"  value="<%=v_pageno%>">
    <input type="hidden" name="p_action"  value="">  

    <input type="hidden" name="p_orderColumn">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title ���� ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
          <td><img src="/images/admin/course/co_title07.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title �� ----------------->
        <br>
            <!-- �����׷�, �⵵ ���� -->
        <table class="form_table_out" cellspacing="0" cellpadding="1">
          <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
                            <tr>
                                <td height="7"></td>
                            </tr>
                            <tr>
                                <td align="center">

                                    <!------------------- ���ǰ˻� ���� ------------------------->
                                    <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                                        <colgroup>
                                                    <col style="width:8%" />
                                                    <col style="width:20%" />
                                                    <col style="width:8%" />
                                                    <col style="width:10%" />
                                                    <col style="width:10%" />
                                                    <col style="width:20%" />
                                        </colgroup>
                                        <tr>
                                            <td align="left" valign="middle">
                                                <font color="red">��</font>�����׷� 
                                            </td>
                                            <td>
                                                <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- �����׷� --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= ss_grcode %>" isLoad="true" />
                                            </td>
                                            <td>
                                                <font color="red">��</font>���� </td>
                                            <td>
<%
    if ( !ss_gyear.equals("") ) {
%>
                                                <kocca:selectBox name="s_gyear" id="oGyear" optionTitle="== ���� ==" type="sqlID" param="<%= ss_grcode %>" sqlID="selectBox.grYearList" selectedValue="<%= ss_gyear %>" isLoad="true" />
<%
    } else {
%>
                                                <kocca:selectBox name="s_gyear" id="oGyear" optionTitle="== ���� ==" type="sqlID" isLoad="false" />
<%
    }
%>
                                            </td>
                                            <td>��������</td> <td>
<%
    if ( !ss_grseq.equals("") || !ss_gyear.equals("")) {
%>
                                                <kocca:selectBox name="s_grseq" id="oGrseq" optionTitle="== ���� ==" type="sqlID" param="<%= ss_grcode + ',' + ss_gyear %>" sqlID="selectBox.grSeqList" selectedValue="<%= ss_grseq %>" isLoad="true" />
<%
    } else {
%>
                                                <kocca:selectBox name="s_grseq" id="oGrseq" optionTitle="== ���� ==" type="sqlID" isLoad="false" />
<%
    }
%>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td> ��з� </td>
                                            <td>
                                                <kocca:selectBox name="s_upperclass" id="oUpperclass" optionTitle="== ��ü ==" type="sqlID" sqlID="selectBox.subjAttUpperList" selectedValue="<%= ss_upperclass %>" isLoad="true" />
                                            </td>
                                            <td>�ߺз� </td>
                                            <td>
<%
    if ( !ss_middleclass.equals("") ) {
%>
                                                <kocca:selectBox  name="s_middleclass" id="oMiddleclass" optionTitle="== ��ü ==" type="sqlID" sqlID="selectBox.subjAttMiddleList" param="<%= ss_upperclass %>" selectedValue="<%= ss_middleclass %>" isLoad="true" />
<%
    } else {
%>
                                                <select name="s_middleclass" id="oMiddleclass">
                                                    <option value="">== ��ü ==</option>
                                                </select>
<%
    }
%>
                                            </td>
                                            <td>�Һз� </td>
                                            <td>
<%
    if ( !ss_lowerclass.equals("") ) {
%>
                                                <kocca:selectBox name="s_lowerclass" id="oLowerclass" optionTitle="== ��ü ==" type="sqlID" sqlID="selectBox.subjAttLowerList" param="<%= ss_upperclass + ',' + ss_middleclass %>" selectedValue="<%= ss_lowerclass %>" isLoad="true" />
<%
    } else {
%>
                                                <select name="s_lowerclass" id="oLowerclass">
                                                    <option value="">== ��ü ==</option>
                                                </select>
<%
    }
%>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="left"><% String s_subjsearchkey = box.getString("s_subjsearchkey");  %>�����˻� </td>
                                            <td>
                                                <input type="text" id="oSubjSearchKey" name="s_subjsearchkey" id="oSubjSearchKey" size="17" onkeypress="if(event.keyCode=='13') fnSubjSearchByName();" value="<%=s_subjsearchkey%>">
                                                <a href="javascript:fnSubjSearchByName()" ><img src="/images/admin/button/search3_butt.gif" border="0" align="absmiddle"></a>                                                        </td>
                                            </td>
                                            <td> ���� </td>
                                            <td colspan='3'>
<%
    if ( !ss_subjcourse.equals("") ) {
        s_subjsearchkey = ( s_subjsearchkey.equals("") ) ? "%" : s_subjsearchkey;
%>
                                                <kocca:selectBox name="s_subjcourse" id="oSubjcourse" optionTitle="== ��ü ==" optionTitleValue="ALL" type="sqlID" param="<%= ss_grcode + ',' + ss_gyear + ',' + ss_grseq + ',' + s_subjsearchkey %>" sqlID="selectBox.subjList" selectedValue="<%= ss_subjcourse %>" isLoad="true" />
<%
    } else {
%>
                                                <kocca:selectBox name="s_subjcourse" id="oSubjcourse" optionTitle="== ��ü ==" optionTitleValue="ALL" type="sqlID" isLoad="false" />
<%
    }
%>
                                        </tr>
                                        <tr>

                                             <td>�������� </td>
                                             <td>
<%
    if ( !ss_subjseq.equals("") ) {
%>
                                                <kocca:selectBox name="s_subjseq" id="oSubjseq" optionTitle="== ��ü ==" optionTitleValue="ALL" type="sqlID" param="<%= ss_grcode + ',' + ss_gyear + ',' + ss_grseq + ',' + ss_subjcourse %>" sqlID="selectBox.subjSeqList" selectedValue="<%= ss_subjseq %>" isLoad="true" />
<%
    } else {
%>
                                                <select name="s_subjseq" id="oSubjseq">
                                                    <option value="">== ��ü ==</option>
                                                </select>
<%
    }
%>
                                            </td>    
                                            <td align="left">��������
                                                <select name="s_area">
                                                    <option value="T" <% if(ss_area.equals("T")) out.print("selected"); %> >��ü</option>
                                                    <option value="G0" <% if(ss_area.equals("G0")) out.print("selected"); %> >����</option>
                                                    <option value="B0" <% if(ss_area.equals("B0")) out.print("selected"); %> >���</option>
                                                    <option value="K0" <% if(ss_area.equals("K0")) out.print("selected"); %> >����</option>
                                                </select>
                                            </td>
                                            <td >������
                                                <select name="s_biyong">
                                                    <option value="T" <% if(ss_biyong.equals("T")) out.print("selected"); %> >��ü</option>
                                                    <option value="Z" <% if(ss_biyong.equals("Z")) out.print("selected"); %> >����</option>
                                                    <option value="P" <% if(ss_biyong.equals("P")) out.print("selected"); %> >����</option>
                                                </select>
                                            </td>
                                            <td align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
                                        </tr>
                                    </table>
                                    <!-------------------- ���ǰ˻� �� ---------------------------->
                                </td>
                            </tr>
                            <tr>
                                <td height="9"></td>
                            </tr>
                        </table>
          </td>
        </tr>
      </table>
      <!-- �����׷�, �⵵ �� -->
        <br>
        <br>

        <!----------------- Activity ������� ���� ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="9" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="5%" class="table_title""><b>NO</b></td>
            <!--td class="table_title" width='7%'><a href="javascript:whenOrder('isonoff')" class="e">����</a></td-->
            <td class="table_title" colspan="2"><a href="javascript:whenOrder('subjnm')" class="e">����</a></td>
            <td class="table_title" width="5%"><a href="javascript:whenOrder('subjseqgr')" class="e">����</a></td>
            <td class="table_title" width="15%"><a href="javascript:whenOrder('edustart')" class="e">�����Ⱓ</a></td>
            <td class="table_title" width="12%"><b>����Ʈ list ��ȸ</b></td>
            <td class="table_title" width="10%"><a href="javascript:whenOrder('projseqcnt')" class="e">����Ʈ<br>
              ��Ʈ ����</a></td>
            <td class="table_title" width="10%"><a href="javascript:whenOrder('ordseqcnt')" class="e">����Ʈ<br>
              ���� ����</a></td>
            <td class="table_title" width="10%"><a href="javascript:whenOrder('isclosed')" class="e">��������</a></td>
          </tr>
          <%
            if(ss_action.equals("go")){    //go button ���ýø� list ���
                for(i = 0; i < list1.size(); i++) {

                    DataBox dbox = (DataBox)list1.get(i);

                    if( dbox != null)
                    {
                        v_subj          = dbox.getString("d_subj");
                        v_course        = dbox.getString("d_course");
                        v_cyear         = dbox.getString("d_cyear");
                        v_courseseq     = dbox.getString("d_courseseq");
                        v_coursenm      = dbox.getString("d_coursenm");
                        v_year          = dbox.getString("d_year");
                        v_subjseq       = dbox.getString("d_subjseq");
                        v_subjseqgr     = dbox.getString("d_subjseqgr");
                        v_isclosed      = dbox.getString("d_isclosed");
                        v_subjnm        = dbox.getString("d_subjnm");
                        v_isonoff       = dbox.getString("d_isonoff");
                        v_edustart      = dbox.getString("d_edustart");
                        v_eduend        = dbox.getString("d_eduend");
                        v_iscourseYn    = dbox.getString("d_isbelongcourse");

                        v_projseqcnt    = dbox.getInt("d_projseqcnt");
                        v_ordseqcnt     = dbox.getInt("d_ordseqcnt");
                        v_subjcnt       = dbox.getInt("d_subjcnt");

                        //����¡
						v_dispnum       = dbox.getInt("d_dispnum");
		                v_totalpage     = dbox.getInt("d_totalpage");
		                v_rowcount      = dbox.getInt("d_rowcount");

                        //��¥ ó��
                        if(v_edustart.equals("")){v_edustart = "����";}
                        else{v_edustart     = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");}

                        if(v_eduend.equals("")){v_eduend = "����";}
                        else{v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");}

                        if(v_isclosed.equals("Y"))      {  v_closed_value = "�Ϸ�";   }
                        else                            {  v_closed_value = "������"; }
                        //if(v_isonoff.equals("ON"))      {  v_onoff_value  = "���̹�"; }
                        //else                            {  v_onoff_value  = "����";   }

                    }
               %>
          <tr>
            <td class="table_01"><%= v_dispnum %></td>
            <!--td class="table_02_1"><%//=v_onoff_value%></td-->
<% if(v_iscourseYn.equals("Y"))
    {
        if(l == v_subjcnt) v_tmp_subj = "";
        if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
        {
             l = 1;
%>
            <td class="table_02_2" rowspan="<%=v_subjcnt%>" ><%= v_coursenm %></td>
            <td class="table_02_2" ><a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>','<%=v_subjseqgr%>', '<%=v_eduend%>')"  class="b"><%=v_subjnm%></a></td>
<%
        }else{
             l++;
%>
            <td class="table_02_2" ><a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>','<%=v_subjseqgr%>', '<%=v_eduend%>')"  class="b"><%=v_subjnm%></a></td>
<%      }
        v_tmp_subj = v_course;
    } else { 
%>
            <td class="table_02_2" colspan="2"><a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>','<%=v_subjseqgr%>', '<%=v_eduend%>')"  class="b"><%=v_subjnm%></a></td>
<% } %>
            <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
            <td class="table_02_1">
            <%=v_edustart %>~<%=v_eduend %>
            </td>
            <td class="table_03_1"> <a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>','<%=v_subjseqgr%>', '<%=v_eduend%>')"><img src="/images/admin/button/b_reportlist.gif" border="0"></a>
            </td>
            <td class="table_02_1"><%=v_projseqcnt%></td>
            <td class="table_02_1"><%=v_ordseqcnt%></td>
            <td class="table_02_1"><%=v_closed_value%></td>
          </tr>
<%
              }
          }
          if(ss_action.equals("go") && i == 0){ 
%>
          <tr>
            <td class="table_02_1" colspan="9">��ϵ� ������ �����ϴ�</td>
          </tr>
<%        } %>
        </table>
        <!----------------- Activity ������� �� ----------------->
        <br>
        <!----------------- ����¡ ���� ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
              <%= PageUtil.printPageList(v_totalpage, v_pageno, row) %>
            </td>
          </tr>
        </table>
        <!----------------- ����¡ �� ----------------->
        
     </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>