<%
//**********************************************************
//  1. 제      목: 교육그룹 리스트 조회화면
//  2. 프로그램명: za_EduGroup_L.jsp
//  3. 개      요: 교육그룹 리스트 조회화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 11
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    if (box == null) {
        box = RequestManager.getBox(request);
    }

    int resultCnt = box.getInt("resultCnt");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
var isChecked = false;

function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}

// 등록화면으로 이동
function insertGrcode(){
    document.form2.p_process.value="insertPage";
    document.form2.submit();
}

//수정화면으로 이동
function updateGrcode(grcode){
    document.form2.p_process.value="updatePage";
    document.form2.p_grcode.value = grcode;
    document.form2.submit();
}

//과정연결
function assignSubjCourse(grcode){

        // window.self.name = "winSelectGrsubj";
        farwindow = window.open("", "openWinGrsubj", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no, width = 1000, height = 640, top=0, left=0");
        document.form2.target = "openWinGrsubj";
        document.form2.action = "/servlet/controller.course.EduGroupServlet";
        document.form2.p_process.value="assignSubjCourse";
        document.form2.p_grcode.value=grcode;
        document.form2.submit();

        farwindow.window.focus();
        //document.form2.target = window.self.name;

}

//과정연결
function assignSubj(grcode){

        // window.self.name = "winSelectGrsubj2";
        farwindow = window.open("", "openWinGrsubj2", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 730, height = 640, top=0, left=0");
        document.form2.target = "openWinGrsubj2";
        document.form2.action = "/servlet/controller.course.EduGroupServlet";
        document.form2.p_process.value="assignSubj";
        document.form2.p_grcode.value=grcode;
        document.form2.submit();

        farwindow.window.focus();
        //document.form2.target = window.self.name;

}

    //과정연결
    function recomSubj(grcode){
        // window.self.name = "winSelectGrsubj3";
        farwindow = window.open("", "openWinGrsubj3", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 730, height = 640, top=0, left=0");
        document.form2.target = "openWinGrsubj3";
        document.form2.action = "/servlet/controller.course.EduGroupServlet";
        document.form2.p_process.value="recomSubj";
        document.form2.p_grcode.value=grcode;
        document.form2.submit();

        farwindow.window.focus();
        //document.form2.target = window.self.name;
    }

    //연도별과정등록화면으로 이동
    function whenGrseq(grcode){
        var url = "/servlet/controller.course.GrseqServlet?p_process=listPage&s_grcode="+grcode;
        document.location=url;
    }

    /**
     * 정렬순서 저장
     */
    function fnUpdateCodeOrder() {
        var docForm = document.form2;
        var i = 0;
        var orgValue = "", currValue = "";
        var grCode = "";
        var updateList = "";

        docForm.p_process.value="updateCodeOrder";

        var grListLength = document.getElementsByName("codeOrder").length;

        for( i = 0; i < grListLength; i++ ) {
            orgValue = document.getElementById("oOrgCodeOrder" + i).value;
            currValue = document.getElementById("oCodeOrder" + i).value;
            if ( orgValue != currValue ) {
                grCode = document.getElementById("oGrCode" + i).value;
                updateList += grCode + "_" + currValue +  ",";
            }
        }

        updateList = updateList.substring(0, updateList.length - 1);

        docForm.tempElem.value = updateList;
        document.form2.submit();
    }
//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form2" method = "post" action="/servlet/controller.course.EduGroupServlet">
    <input type="hidden" name="p_process" value="">
    <input type="hidden" name="p_grcode"  value="">
    <input type="hidden" name="tempElem"  value="">
</form>

<form name = "form1" method = "post">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">

            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td><img src="/images/admin/system/unite_title08.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->

            <br />
            <!-- 교육그룹추가, 모두선택, 메일전송 버튼 시작 //-->
            <table width="97%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="700" align="right"></td>
                    <td width="8"></td>
                    <td  align="right">
                        <a href="javascript:fnUpdateCodeOrder()">정렬순서 저장</a>
                        <a href="javascript:insertGrcode()"><img src="/images/admin/button/btn_edugroupplus.gif" border="0"></a>
                    </td>
                </tr>
                <tr>
                    <td height="3"></td>
                    <td height="3"></td>
                    <td height="3"></td>
                </tr>
            </table>
            <!-- 교육그룹추가, 모두선택, 메일전송 버튼 끝 //-->

            <!-- 교육그룹 시작 //-->
            <table class="table_out" cellspacing="1" cellpadding="6">
                <colgroup>
                    <col width="5%">
                    <col width="5%">
                    <col width="10%">
                    <col width="21%">
                    <col width="19%">
                    <col width="10%">
                    <col width="25%">
                    <col width="*">
                </colgroup>
                <tr>
                    <td colspan="8" class="table_top_line"></td>
                </tr>
                <tr>
                    <th height="25" class="table_title"><b>NO</b></th>
                    <th height="25" class="table_title"><b>사용여부</b></th>
                    <th class="table_title"><b>교육구분</b></th>
                    <th class="table_title"><b>교육그룹</b></th>
                    <th class="table_title"><b>관련기관</b></th>
                    <th class="table_title"><b>담당자</b></th>
                    <th class="table_title"><b>기능</b></th>
                    <td class="table_title"><b>정렬순서</b></td>
                </tr>
<%
    ArrayList  list = (ArrayList)request.getAttribute("EduGroupList");

    for (int i=0; i<list.size(); i++) {
        DataBox dbox = (DataBox)list.get(i);
 %>

                <tr>
                    <td align="center" bgcolor="#EEEEEE" height="25"><%=(i+1)%></td>
                    <td class="table_02_1" align="center" height="25"><%=dbox.getString("d_useyn")%></td>
                    <td class="table_02_1" align="center"><%=dbox.getString("d_grgubunnm")%></td>
                    <td class="table_02_1" align="center"><b><a href="javascript:updateGrcode('<%=dbox.getString("d_grcode")%>')"><%=dbox.getString("d_grcodenm")%></a></b></td>
                    <td class="table_02_1" align="center"><%=dbox.getString("d_grtypenm")%></td>
                    <td class="table_02_1" align="center"><%=dbox.getString("d_mastername")%></td>
                    <td class="table_03_1" align="center">
                        <table width="68%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td align="center"><a href="javascript:whenGrseq('<%=dbox.getString("d_grcode")%>')"><img src="/images/admin/button/b_year.gif" border="0"></a></td>
                                <!--td align="center"><a href="javascript:assignSubjCourse('<%=dbox.getString("grcode")%>')"><img src="/images/admin/button/b_courseapply.gif" border="0"></a></td-->
                                <td align="center"><a href="javascript:assignSubj('<%=dbox.getString("d_grcode")%>')"><img src="/images/admin/button/b_courseapply.gif" border="0"></a></td>
                                <td align="center"><a href="javascript:recomSubj('<%=dbox.getString("d_grcode")%>')"><img src="/images/admin/button/b_recom.gif" border="0"></a></td>
                            </tr>
                        </table>
                    </td>
                    <td class="table_03_1" align="center">
                        <input type="text" id="oCodeOrder<%= i %>" name="codeOrder" size="4" maxlength="2" value="<%= dbox.getString("d_code_order") %>" />
                        <input type="hidden" id="oOrgCodeOrder<%= i %>" name="orgCodeOrder" value="<%= dbox.getString("d_code_order") %>" />
                        <input type="hidden" id="oGrCode<%= i %>" name="grCode" value="<%= dbox.getString("d_grcode") %>" />
                    </td>
                </tr>
<%  } %>

            </table>
        <!-- 교육그룹 끝 //-->
            <br />
        </td>
    </tr>

    <tr>
        <td><%@ include file = "/learn/library/getJspName.jsp" %></td>
    </tr>

</table>

</form>
</body>
</html>
