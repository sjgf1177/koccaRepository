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
    ArrayList allGoldClassList = (ArrayList)request.getAttribute("allGoldClassList");
    ArrayList classifiedSubjectList = (ArrayList)request.getAttribute("classifiedSubjectList");
    ArrayList classifiedGoldClassList = (ArrayList)request.getAttribute("classifiedGoldClassList");
    ArrayList gubunNmList = (ArrayList)request.getAttribute("gubunNmList");

    DataBox dbox = null;
    DataBox gbBox = null;

    String upperClsCd = box.getString("upperClsCd");
    String gubun1 = box.getString("gubun1");
    String gubun2 = box.getString("gubun2");
    String gubun3 = box.getString("gubun3");
    String hcd = box.getString("hcd");
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
/**
 *
 */
function fnSendToClassify() {
    if ( ($("#oAllSubject option:selected").length) == 0) {
        alert("추가할 정규과정을 선택하세요.");
        return false;
    }

    $("#oAllSubject option:selected").each( function() {
        $("#oClassifedSubject").append($(this));
        // $("#oClassifedSubject").prepend("<option value=\"" + $(this).val() + "\">" +  $(this).text()  + "</option>");
        // $(this).remove();
    });

    $("#oAllSubject").css("width", "600px");
    $("#oClassifedSubject").css("width", "600px");
}

/**
*
*/
function fnSendToClassifyGold() {
   if ( ($("#oAllGoldClass option:selected").length) == 0) {
       alert("추가할 열린강좌를 선택하세요.");
       return false;
   }

   $("#oAllGoldClass option:selected").each( function() {
       $("#oClassifedGoldClass").append($(this));
       // $("#oClassifedSubject").prepend("<option value=\"" + $(this).val() + "\">" +  $(this).text()  + "</option>");
       // $(this).remove();
   });

   $("#oAllGoldClass").css("width", "600px");
   $("#oClassifedGoldClass").css("width", "600px");
}

/**
 *
 */
function fnRemoveFromClassify() {
    if ( ($("#oClassifedSubject option:selected").length) ==0 ) {
        alert("제거할 정규과정을 선택하세요.");
        return false;
    }

    $("#oClassifedSubject option:selected").each( function() {
        $("#oAllSubject").append($(this));
    });
    $("#oAllSubject").css("width", "600px");
    $("#oClassifedSubject").css("width", "600px");
}

/**
*
*/
function fnRemoveFromClassifyGold() {
   if ( ($("#oClassifedGoldClass option:selected").length) ==0 ) {
       alert("제거할 열린강좌를 선택하세요.");
       return false;
   }

   $("#oClassifedGoldClass option:selected").each( function() {
       $("#oAllGoldClass").append($(this));
   });
   $("#oAllGoldClass").css("width", "600px");
   $("#oClassifedGoldClass").css("width", "600px");
}

function fnSaveSubjectList() {
    $("#oClassifedSubject > option").attr("selected", true);
    $("#oClassifedGoldClass > option").attr("selected", true);

    var param = $("#oSubjectResigerForm").serialize();

    $.ajax({
            type : "post"
        ,   url : "/servlet/controller.course.SubjectClassifyServlet"
        ,   dataType : "json"
        ,   data : param
        ,   success : function (data) {
                if(data.resultCnt > 0) {
                    alert("저장되었습니다.");
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

function fnGotoLevel() {
    $("#oSubjectResigerForm").action = "/servlet/controller.course.SubjectClassifyServlet";
    $("#oProcess").val("courseLevelPage");
    $("#gubun1").val('<%= gubun1 %>');
    $("#gubun2").val('<%= gubun2 %>');
    $("#gubun3").val('<%= gubun3 %>');
    $("#hcd").val('<%= hcd %>');
    $("#oSubjectResigerForm").submit();
}

</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form id="oSubjectResigerForm" name="subjectResigerForm" method="post">
    <input type="hidden" id="oProcess" name="process"  value="saveSubjectList" />
    <input type="hidden" id="oUpperClsCd" name="upperClsCd"  value="<%= upperClsCd %>" />
    <input type="hidden" id="gubun1" name="gubun1"  value="<%= gubun1 %>" />
    <input type="hidden" id="gubun2" name="gubun2"  value="<%= gubun2 %>" />
    <input type="hidden" id="gubun3" name="gubun3"  value="<%= gubun3 %>" />
    <input type="hidden" id="hcd" name="hcd"  value="<%= hcd %>" />

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top" colspan="2">
            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
					<td style="text-align: left;">
						<img src="/images/admin/course/c_title12.gif">
					</td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->
			<!-- 버튼 영역 s -->
			<table cellpadding="0" cellspacing="0" class="table1">
				<tr>
					<td align="right" height="20">
						<button id="oRegisterBtn" name="registerBtn" class="btn_reg" onclick="fnSaveSubjectList(); return false;" >저장</button>
						<button id="oLevelBtn" name="levelBtn" class="btn_reg" onclick="fnGotoLevel(); return false;" >난이도 설정</button>
            			<button id="oCancelBtn" name="cancelBtn" class="btn_reg" onclick="fnGotoList();" >목록</button>
					</td>
				</tr>
				<tr><td height="3"></td></tr>
			</table>
			<!-- 버튼 영역 e -->              
            <br/>

            <!-- tab 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="50%" style="font-size:12pt; font-weight:bold;">
                    	온라인 분류별 [
			   	<%
			        if ( gubunNmList.size() > 0 ) {
			            for( int i = 0 ; i < gubunNmList.size() ; i++ ){
			            	gbBox = (DataBox)gubunNmList.get(i);
				%>
							<%= gbBox.getString("d_codenm") %> 
				<%
							if(gubunNmList.size() - 1 > i){
				%>
								>
				<%				
							}
			            }
			        }
				%>
                    	]
                    </td>
                    <td width="70%">&nbsp;</td>
                </tr>
            </table>
            <!-- tab 끝 //-->


            <!-- 등록 화면 시작 //-->
            <table cellspacing="1" cellpadding="2" class="table_out">
                <colgroup>
                    <col width="45%;" />
                    <col width="10%;" />
                    <col width="*;" />
                </colgroup>
                <tr>
                    <td colspan="3" class="table_top_line"></td>
                </tr>
                <tr>
                    <td class="table_title">미등록 정규과정 (<%= allSubjectList.size() %> 과정) </td>
                    <td class="table_title">보내기/<br/>제거하기</td>
                    <td class="table_title">등록된 정규과정 (<%= classifiedSubjectList.size() %> 과정)</td>
                </tr>
                <tr>
                    <td class="table_01">
                        <select multiple id="oAllSubject" name="allSubject" size="15" style="font-size:10pt; width:600px; background-color:#efefef;" ondblclick="fnSendToClassify();">
<%
        if ( allSubjectList.size() > 0 ) {
            for( int i = 0 ; i < allSubjectList.size() ; i++ ){
                dbox = (DataBox)allSubjectList.get(i);
%>
                            <option value="<%= dbox.getString("d_subj") %>">[<%= dbox.getString("d_subj") %>] <%= dbox.getString("d_subjnm") %></option>
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
                        <select multiple id="oClassifedSubject" name="classifedSubject" size="15" style="font-size:10pt; width:600px; background-color:#ffffb3;" ondblclick="fnRemoveFromClassify();">
<%
        if ( classifiedSubjectList.size() > 0 ) {
            for( int i = 0 ; i < classifiedSubjectList.size() ; i++ ){
                dbox = (DataBox)classifiedSubjectList.get(i);
%>
                            <option value="<%= dbox.getString("d_subj") %>">[<%= dbox.getString("d_subj") %>] <%= dbox.getString("d_subjnm") %></option>
<%
            }
        }
%>
                        </select>
                    </td>
                </tr>
            </table>
			<br/>
			<table cellspacing="1" cellpadding="2" class="table_out">
                <colgroup>
                    <col width="45%;" />
                    <col width="10%;" />
                    <col width="*;" />
                </colgroup>
                <tr>
                    <td colspan="3" class="table_top_line"></td>
                </tr>
                <tr>
                    <td class="table_title">미등록 열린강좌 (<%= allGoldClassList.size() %> 과정) </td>
                    <td class="table_title">보내기/<br/>제거하기</td>
                    <td class="table_title">등록된 열린강좌 (<%= classifiedGoldClassList.size() %> 과정)</td>
                </tr>
                <tr>
                    <td class="table_01">
                        <select multiple id="oAllGoldClass" name="allGoldClass" size="15" style="font-size:10pt; width:600px; background-color:#efefef;" ondblclick="fnSendToClassifyGold();">
<%
        if ( allGoldClassList.size() > 0 ) {
            for( int i = 0 ; i < allGoldClassList.size() ; i++ ){
                dbox = (DataBox)allGoldClassList.get(i);
%>
                            <option value="<%= dbox.getInt("d_seq") %>">[<%= dbox.getInt("d_seq") %>] <%= dbox.getString("d_lecnm") %></option>
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
                        <button id="oCancelBtn" name="cancelBtn" class="btn_basic" onclick="fnSendToClassifyGold(); return false;" >&gt;&gt;</button><br/><br/>
                        <button id="oCancelBtn" name="cancelBtn" class="btn_basic" onclick="fnRemoveFromClassifyGold(); return false;" >&lt;&lt;</button>
                    </td>
                    <td class="table_01">
                        <select multiple id="oClassifedGoldClass" name="classifedGoldClass" size="15" style="font-size:10pt; width:600px; background-color:#ffffb3;" ondblclick="fnRemoveFromClassifyGold();">
<%
        if ( classifiedGoldClassList.size() > 0 ) {
            for( int i = 0 ; i < classifiedGoldClassList.size() ; i++ ){
                dbox = (DataBox)classifiedGoldClassList.get(i);
%>
                            <option value="<%= dbox.getInt("d_seq") %>">[<%= dbox.getInt("d_seq") %>] <%= dbox.getString("d_lecnm") %></option>
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
        </td>
    </tr>
    <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>

</body>
</html>
