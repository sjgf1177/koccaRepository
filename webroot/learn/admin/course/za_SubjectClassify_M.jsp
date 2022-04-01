<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="java.util.ArrayList" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    if (box == null) {
        box = RequestManager.getBox(request);
    }

    ArrayList allSubjectList = (ArrayList)request.getAttribute("allSubjectList");
    ArrayList classifiedSubjectList = (ArrayList)request.getAttribute("classifiedSubjectList");

    DataBox dbox = null;

    String upperClsCd = box.getString("upperClsCd");
    String clsCd = box.getString("clsCd");
    String clsNm = box.getString("clsNm");
    String tabName = ( upperClsCd.equals("1000") ) ? "분야별" : "직업별";
    tabName = clsNm + " | " + tabName;
%>

<html>
<head>
<title>과정 관리 | 과정분류 관리 | 과정 | 관리자 | 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/admin_style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">

<style type="text/css">
    button{display:inline-block; padding:2px 4px 0px 4px; margin-bottom:0; font-size:12px; font-weight:bold; text-align:center; white-space:nowrap; vertical-align:middle; border-style: solid 1px; }
    button.btn_reg {width:80px; height:24px; background-color:#efefef; color:#000000; border-color:#808080; border-width:1px;}
    button.btn_basic {width:80px; height:20px; background-color:#efefff; color:#000000; border-color:#808090; border-width:1px;}
</style>

<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript">
<!--

/**
 *
 */
function fnSendToClassify() {
    if ( ($("#oAllSubject option:selected").length) == 0) {
        alert("추가할 과정을 선택하세요.");
        return false;
    }

    $("#oAllSubject option:selected").each( function() {
        $("#oClassifedSubject").append($(this));
        // $("#oClassifedSubject").prepend("<option value=\"" + $(this).val() + "\">" +  $(this).text()  + "</option>");
        // $(this).remove();
    });

    $("#oAllSubject").css("width", "420px");
    $("#oClassifedSubject").css("width", "420px");
}

/**
 *
 */
function fnRemoveFromClassify() {
    if ( ($("#oClassifedSubject option:selected").length) ==0 ) {
        alert("제거할 과정을 선택하세요.");
        return false;
    }

    $("#oClassifedSubject option:selected").each( function() {
        $("#oAllSubject").append($(this));
    });
    $("#oAllSubject").css("width", "420px");
    $("#oClassifedSubject").css("width", "420px");
}

function fnSaveSubjectList() {
    $("#oClassifedSubject > option").attr("selected", true);

    var param = $("#oSubjectResigerForm").serialize();

    $.ajax({
            type : "post"
        ,   url : "/servlet/controller.course.SubjectClassifyServlet"
        ,   dataType : "json"
        ,   data : param
        ,   success : function (data) {
                if(data.resultCnt > 0) {
                    alert("등록되었습니다.");
                }
            }
        ,   complete : function(arg1, arg2) {
            }
        ,   error : function(arg1, arg2) {
        }
    });
}

function fnGotoList() {
        $("#oSubjectResigerForm").action = "/servlet/controller.course.SubjectClassifyServlet";

        $("#oProcess").val("listPage");
        $("#oSubjectResigerForm").submit();
}
//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form id="oSubjectResigerForm" name="subjectResigerForm" method="post">
    <input type="hidden" id="oProcess" name="process"  value="saveSubjectList" />
    <input type="hidden" id="oUpperClsCd" name="upperClsCd"  value="<%= upperClsCd %>" />
    <input type="hidden" id="oClsCd" name="clsCd"  value="<%= clsCd %>" />

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top" colspan="2">
            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td style="font-size:14pt; font-weight:bold; padding-top:16px;">과정 분류 관리</td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->
            <br/>

            <!-- tab 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="30%" style="font-size:12pt; font-weight:bold;"><%= tabName %></td>
                    <td width="70%">&nbsp;</td>
                </tr>
                <tr>
                    <td height="2" colspan="4" bgcolor="#6699CC"></td>
                </tr>
            </table>
            <!-- tab 끝 //-->


            <!-- 등록 화면 시작 //-->
            <table cellspacing="1" cellpadding="5" class="table_out">
                <colgroup>
                    <col width="45%;" />
                    <col width="10%;" />
                    <col width="*;" />
                </colgroup>
                <tr>
                    <td colspan="3" class="table_top_line"></td>
                </tr>
                <tr>
                    <td class="table_title">전체과정 (<%= allSubjectList.size() %> 과정) <br/>(기 등록된 과정 제외) </td>
                    <td class="table_title">보내기/<br/>제거하기</td>
                    <td class="table_title">등록된 과정 (<%= classifiedSubjectList.size() %> 과정)</td>
                </tr>
                <tr>
                    <td class="table_01">
                        <select multiple id="oAllSubject" name="allSubject" size="20" style="font-size:10pt; width:420px; background-color:#efefef;" ondblclick="fnSendToClassify();">
<%
        if ( allSubjectList.size() > 0 ) {
            for( int i = 0 ; i < allSubjectList.size() ; i++ ){
                dbox = (DataBox)allSubjectList.get(i);
%>
                            <option value="<%= dbox.getString("d_subj") %>"><%= dbox.getString("d_subjnm") %></option>
<%
            }
        } else {
%>
                            <option value="">선택할 수 있는 과정이 없습니다.</option>
<%
        }
%>
                        </select>
                    </td>
                    <td class="table_02_1"> 
                        <button id="oCancelBtn" name="cancelBtn" class="btn_basic" onclick="fnSendToClassify(); return false;" >&gt;&gt;</button><br/><br/>
                        <button id="oCancelBtn" name="cancelBtn" class="btn_basic" onclick="fnRemoveFromClassify(); return false;" >&lt;&lt;</button>
                    </td>
                    <td class="table_01">
                        <select multiple id="oClassifedSubject" name="classifedSubject" size="20" style="font-size:10pt; width:420px; background-color:#ffffb3;" ondblclick="fnRemoveFromClassify();">
<%
        if ( classifiedSubjectList.size() > 0 ) {
            for( int i = 0 ; i < classifiedSubjectList.size() ; i++ ){
                dbox = (DataBox)classifiedSubjectList.get(i);
%>
                            <option value="<%= dbox.getString("d_subj") %>"><%= dbox.getString("d_subjnm") %></option>
<%
            }
        }
%>
                        </select>
                    </td>
                </tr>
            </table>

            <br/>
            <br/>
            <button id="oRegisterBtn" name="registerBtn" class="btn_reg" onclick="fnSaveSubjectList(); return false;" >저장</button>&nbsp;&nbsp;
            <button id="oCancelBtn" name="cancelBtn" class="btn_reg" onclick="fnGotoList();" >목록</button>

        </td>
    </tr>
</table>
</form>

</body>
</html>
