<%
//**********************************************************
//  1. 제     목: 과정 조회화면
//  2. 프로그램명: za_Subject_L.jsp
//  3. 개     요: 과정 조회화면
//  4. 환     경: JDK 1.4
//  5. 버     젼: 1.0
//  6. 작     성: 노희성 2004. 11. 12
//  7. 수     정:
//**********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="com.credu.library.StringManager" %>
<%@ page import="com.credu.course.SubjectData" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    if ( box == null ) {
        box = RequestManager.getBox(request);
    }

    String ss_grcode = box.getString("s_grcode");            //교육그룹
    String ss_gyear = box.getString("s_gyear");            //년도
    String ss_upperclass = box.getString("s_upperclass");    //과정대분류
    String ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String ss_lowerclass = box.getString("s_lowerclass");    //과정소분류
    String ss_subjcourse= box.getString("s_subjcourse");       //과정&코스
    String ss_subjseq = box.getString("s_subjseq");         //과정 차수
    String ss_action = box.getString("s_action");
    String ss_specials = box.getString("s_specials");

    String ss_area = box.getString("s_area");

    ArrayList list    = null;
    if(ss_action.equals("go")){ //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("SubjectList");
    }
    String s_gadmin = box.getSession("gadmin");
    String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
    String isAll = !v_gadmin.equals("A") ? "false" : "true";

    String v_orderType = box.getStringDefault("p_orderType"," asc"); //정렬순서

%>
<!doctype html>
<html>
<head>
<title>과정개설 - 과정 - 관리자 - 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
<!--
//사이버 과정 등록 페이지로
function InsertSubjPage() {
    //교육그룹관리자,관리자(총괄제외)는 교육그룹을 선택해야함
<%
    if (!v_gadmin.equals("A")) {
%>
      if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----' || document.form1.s_grcode.value == '' ) {
            alert("교육그룹을 선택하세요.");
            return ;
        }

<%
    }
%>
    document.form1.p_process.value = 'insertPage';
    document.form1.submit();
}

//집합과정 등록페이지로
function InsertOffSubjPage() {
    //교육그룹관리자,관리자(총괄제외)는 교육그룹을 선택해야함
    <%if (!v_gadmin.equals("A")) { %>
      if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----' || document.form1.s_grcode.value == '' ) {
            alert("교육그룹을 선택하세요.");
            return ;
        }

    <% } %>
    document.form1.p_process.value = 'insertOffPage';
    document.form1.submit();
}

//과정정보 수정
function UpdateSubjPage(subj, isonoff) {
    if (isonoff == 'ON') {
    document.form1.p_process.value = 'updatePage';
  } else {
    document.form1.p_process.value = 'updateOffPage';
  }
  document.form1.p_subj.value  = subj;
  document.form1.submit();
}

//과정소개
function Preview(subj, subjnm) {
    document.form1.p_process.value = 'previewPage';
    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value  = subjnm;
    document.form1.submit();
}

//연관과정
function RelatedSubj(subj, subjnm) {
    document.form1.p_process.value = 'relatedSubjPage';
    document.form1.p_subj.value  = subj;
    document.form1.p_subjnm.value  = subjnm;
    document.form1.submit();
}

//교육그룹 연결
function RelatedGrcode(subj, subjnm) {
    document.form1.p_process.value = 'relatedGrcodePage';
    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value  = subjnm;
    document.form1.submit();
}


// 셀렉트 선택후 검색
function fnSearchSubjList(ss_action) {
    if (ss_action=="go")    {
        //교육그룹관리자는 교육그룹을 선택해야함
<%
    if (!v_gadmin.equals("A")) {
%>
          if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----' || document.form1.s_grcode.value == '' ) {
                alert("교육그룹을 선택하세요.");
                return ;
            }

<%
    }
%>
        top.ftop.setPam();
    }
    document.form1.s_action.value = ss_action;
    document.form1.p_process.value = 'listPage';
    document.form1.submit();
}

//정렬
function whenOrder(column) {
    if (document.form1.p_orderType.value == " asc") {
        document.form1.p_orderType.value = " desc";
    } else {
        document.form1.p_orderType.value = " asc";
    }

    document.form1.s_action.value = "go";
    document.form1.p_process.value = 'listPage';
    document.form1.p_orderColumn.value = column;

    document.form1.submit();
}

    $(function() {
        $("#oGrcode").bind("change", function(){
            fnSearchSubjList("go");
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

        $("#oLowerclass").bind("change", function(){
            fnSearchSubjList("go");
        });

    });

    /**
     * 검색조건에서 대분류 선택시 callback function
     */
    function fnSetMiddleClass( result ) {
        $("#oMiddleclass").empty();
        $("#oMiddleclass").append("<option value=\"\">-- 전체 --</option>");
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"\">-- 전체 --</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oMiddleclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }

    /**
     * 검색조건에서 중분류 선택시 callback function
     */
    function fnSetLowerClass( result ) {
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"\">-- 전체 --</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oLowerclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }


//-->
</script>

</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">
            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr>
                    <td style="text-align:left;"><img src="/images/admin/course/c_title01.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->
            <br />
            <!-- form 시작 //-->
            <table cellspacing="0" cellpadding="1" class="form_table_out">
                <form name="form1" method="post" action="/servlet/controller.course.SubjectServlet">
                    <input type="hidden" name="p_process"  value="">
                    <input type="hidden" name="p_subj"     value="">
                    <input type="hidden" name="p_subjnm"  value="">
                    <input type="hidden" name="s_action"  value="">
                    <input type="hidden" name="p_orderColumn">
                    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
                    <input type="hidden" name="s_grseq" value="ALL">

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
                                                <!--  조건검색 시작 //-->
                                                <table cellspacing="0" cellpadding="0" width="99%">
                                                    <colgroup>
                                                        <col style="width:9%;" />
                                                        <col style="width:*;" />
                                                        <col style="width:9%;" />
                                                        <col style="width:16%;" />
                                                        <col style="width:9%;" />
                                                        <col style="width:16%;" />
                                                        <col style="width:8%;" />
                                                    </colgroup>
                                                    <tr>
                                                        <!-- 교육그룹 시작 -->
                                                        <td class="_tdT"><%= !v_gadmin.equals("A") ? "<font color=\"red\">★</font>":"" %> 교육그룹</td>
                                                        <td class="_tdS" style="text-align:left; padding-left:4px;">
                                                            <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- 전체 --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= ss_grcode %>" isLoad="true" />
                                                        </td>

                                                        <td class="_tdT">교육구분</td>
                                                        <td class="_tdS" style="text-align:left; padding-left:4px;">
                                                            <kocca:selectBox id="oArea" name="s_area" type="code" isLoad="true" codeValue="0101" optionTitle="-- 전체 --" selectedValue="<%= ss_area %>" />
                                                        </td>

                                                        <td class="_tdT">과정특성</td>
                                                        <td class="_tdS" style="text-align:left; padding-left:4px;">
                                                            <kocca:selectBox name="s_specials" id="oSpecials" optionTitle="-- 전체 --" type="sqlID" sqlID="code.list.specials" selectedValue="<%= ss_specials %>" isLoad="true" />
                                                        </td>
                                                        <td rowspan="2">
                                                            <a href="javascript:fnSearchSubjList('go');"><img src="/images/admin/button/b_go.gif" border="0" align="absmiddle"></a>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="_tdT">대분류</td>
                                                        <td class="_tdS" style="text-align:left; padding-left:4px;">
                                                            <kocca:selectBox name="s_upperclass" id="oUpperclass" optionTitle="-- 전체 --" type="sqlID" sqlID="selectBox.subjAttUpperList" selectedValue="<%= ss_upperclass %>" isLoad="true" />
                                                        </td>
                                                        <td class="_tdT">중분류</td>
                                                        <td class="_tdS" style="text-align:left; padding-left:4px;">
<%
    if ( !ss_middleclass.equals("") ) {
%>
                                                            <kocca:selectBox  name="s_middleclass" id="oMiddleclass" optionTitle="-- 전체 --" type="sqlID" sqlID="selectBox.subjAttMiddleList" param="<%= ss_upperclass %>" selectedValue="<%= ss_middleclass %>" isLoad="true" />
<%
    } else {
%>
                                                            <select name="s_middleclass" id="oMiddleclass">
                                                                <option value="">-- 전체 --</option>
                                                            </select>
<%
    }
%>
                                                        </td>
                                                        <td class="_tdT">소분류</td>
                                                        <td class="_tdS" style="text-align:left; padding-left:4px;">
<%
    if ( !ss_lowerclass.equals("") ) {
%>
                                                            <kocca:selectBox name="s_lowerclass" id="oLowerclass" optionTitle="-- 전체 --" type="sqlID" sqlID="selectBox.subjAttLowerList" param="<%= ss_upperclass + ',' + ss_middleclass %>" selectedValue="<%= ss_lowerclass %>" isLoad="true" />
<%
    } else {
%>
                                                            <select name="s_lowerclass" id="oLowerclass">
                                                                <option value="">-- 전체 --</option>
                                                            </select>
<%
    }
%>
                                                        </td>
                                                    </tr>

                                                </table>
                                                <!-- - 조건검색 끝 //-->
                                            </td>
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
            <br />
            <!-- 추가 버튼 시작 //-->
            <table cellpadding="0" cellspacing="0" class="table1">
                <tr>
                    <td align="right" height="20">
                        <!-- 집합과정추가 버튼제거 <a href="javascript:InsertOffSubjPage()"><img src="/images/admin/button/btn_ziphabplus.gif" border="0"></a>  -->
                        <a href="javascript:InsertSubjPage()"><img src="/images/admin/button/btn_cyberplus.gif" border="0"></a>
                    </td>
                </tr>
                <tr>
                    <td height="3"></td>
                </tr>
            </table>
            <!-- 추가 버튼 끝 //-->

            <!-- 과정 관리 시작 //-->
            <table cellspacing="1" cellpadding="5" class="table_out">
                <tr>
                    <td colspan="<%if (v_gadmin.equals("H")) out.print("9"); else out.print("12");%>" class="table_top_line"></td>
                </tr>
                <tr>
                    <td class="table_title" width="34"><b>NO</b></td>
                    <td class="table_title" width="62"><a href="javascript:whenOrder('upperclass')" class="e">과정분류</a></td>
                    <td class="table_title" width="68"><a href="javascript:whenOrder('area')" class="e">교육구분</a></td>
                    <td class="table_title" width="60"><a href="javascript:whenOrder('subj')" class="e">과정코드</a></td>
                    <td class="table_title" width="120"><a href="javascript:whenOrder('subjnm')" class="e">과정명</a></td>
                    <td class="table_title" width="52">소개이미지</td>
                    <td class="table_title" width="31">파일</td>
                    <td class="table_title" width="36"><a href="javascript:whenOrder('isintroduction')" class="e">소개여부</a></td>
                    <td class="table_title" width="36"><a href="javascript:whenOrder('isuse')" class="e">사용여부</a></td>
                <% //교육그룹 관리자에게는 숨김 %>
                <%if (!v_gadmin.equals("H")) { %>
                    <td class="table_title" width="94"><b>교육그룹연결</b></td>
                    <td class="table_title" width="94"><b>연관과정</b></td>
                    <td class="table_title" width="94"><b>과정소개</b></td>
                <% } %>
                </tr>
<%
if(ss_action.equals("go")){ //go button 선택시만 list 출력

    String v_introducefile_view = "";
    String v_informationfile_view = "";

    SubjectData data = null;
    for (int i=0; i<list.size(); i++) {
        data = (SubjectData)list.get(i);

        if (!data.getIntroducefilenamereal().equals("")) {
            v_introducefile_view    = "<a href = '/servlet/controller.library.DownloadServlet?p_savefile=" + data.getIntroducefilenamenew() + "&p_realfile=" + data.getIntroducefilenamereal()+"'><img src='/images/admin/course/ico_img.gif' border='0'></a>";
        } else {
             v_introducefile_view   = "";
        }
        if (!data.getInformationfilenamereal().equals("")) {
            v_informationfile_view = "<a href = '/servlet/controller.library.DownloadServlet?p_savefile=" + data.getInformationfilenamenew() + "&p_realfile=" + data.getInformationfilenamereal()+ "'><img src='/images/admin/course/ico_file.gif' border='0'></a>";
        } else {
            v_informationfile_view = "";
        }


%>
                <tr>
                    <td class="table_01"><%=String.valueOf(i+1)%></td>
                    <td class="table_02_1"><%=data.getClassname()%></td>
                    <td class="table_02_1"><%=data.getArea()%></td>
                    <td class="table_02_1"><%=data.getSubj()%></td>
                    <td class="table_02_2" id="<%=data.getSubjnm()%>">
                        <a href="javascript:UpdateSubjPage('<%=data.getSubj()%>', '<%=data.getIsonoff()%>')" class='b'><%=data.getSubjnm()%></a>
                        <%= data.getMobileUseYn().equals("Y") ? "<br/>(모바일)" : "" %>
                    </td>
                    <td class="table_02_1"><%=v_introducefile_view%></td>
                    <td class="table_02_1"><%=v_informationfile_view%></td>
                    <td class="table_02_1"><%=data.getIsintroduction()%></td>
                    <td class="table_02_1"><%=data.getIsuse()%></td>
                <% //교육그룹 관리자에게는 숨김 %>
                <%if (!v_gadmin.equals("H")) { %>
                    <td class="table_03_1"><%//if (data.getIsapproval().equals("Y")) {%><a href="javascript:RelatedGrcode('<%=data.getSubj()%>','<%=data.getSubjnm()%>')"><img src="/images/admin/button/b_edumanag.gif" border="0"></a><%//}%></td>
                    <td class="table_03_1"><%//if (data.getIsapproval().equals("Y")) {%><a href="javascript:RelatedSubj('<%=data.getSubj()%>','<%=data.getSubjnm()%>')"><img src="/images/admin/button/b_connectcourse.gif"  border="0"></a><%//}%></td>
                    <td class="table_03_1"><%//if (data.getIsapproval().equals("Y")) {%><a href="javascript:Preview('<%=data.getSubj()%>','<%=data.getSubjnm()%>')"><img src="/images/admin/button/b_coursesample.gif" border="0"></a><%//}%></td>
                <% } %>
                </tr>
<%
    }

    if (list.size()==0) {
        if (!v_gadmin.equals("H")) { %>
                <tr>
                    <td colspan=12 class="table_02_1">등록된 과정이 없습니다.</td>
                </tr>
<%
        } else {
%>
                <tr>
                    <td colspan=9 class="table_02_1">등록된 과정이 없습니다.</td>
                </tr>
<%
        }
    }
}
%>
            </table>
            <!-- 과정 관리 끝 //-->
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
