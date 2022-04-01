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

    ArrayList subjAllList = (ArrayList)request.getAttribute("subjAllList");
    ArrayList subjSelectedList = (ArrayList)request.getAttribute("subjSelectedList");

    DataBox dbox = null;

    String gubun = box.getStringDefault("gubun", "S");
%>

<html>
<head>
<title>한국콘텐츠아카데미</title>
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

$(document).ready(function(){
	fnSelectTab('<%=gubun%>');
});

/**
 * 탭 선택. 인덱스 값을 설정한다.
 */
function fnSelectTab( gubun ) {
	
	var tab = $('#'+gubun+'AreaTab');
	if(tab){
		$(tab).parent().parent().find('table').each(function(){
			$(this).find('td:eq(0)').removeClass("blue_butt_left").addClass("black_butt_left");
	        $(this).find('td:eq(1)').removeClass("blue_butt_top").addClass("black_butt_top");
	        $(this).find('td:eq(2)').removeClass("blue_butt_right").addClass("black_butt_right");
	        $(this).find('td:eq(3)').removeClass("blue_butt_middle").addClass("black_butt_middle");
	        $(this).find('td:eq(4)').removeClass("blue_butt_bottom").addClass("black_butt_bottom");
		});
        
        
		$(tab).find('#Td1').removeClass("black_butt_left").addClass("blue_butt_left");
        $(tab).find('#Td2').removeClass("black_butt_top").addClass("blue_butt_top");
        $(tab).find('#Td3').removeClass("black_butt_right").addClass("blue_butt_right");
        $(tab).find('#Td4').removeClass("black_butt_middle").addClass("blue_butt_middle");
        $(tab).find('#Td5').removeClass("black_butt_bottom").addClass("blue_butt_bottom");
        
        
	}
}

function fnGotoList(gubun) {
    $("#oSubjectResigerForm").action = "/servlet/controller.course.SubjectRandomServlet";
	$("#gubun").val(gubun);
    $("#oProcess").val("listPage");
    $("#oSubjectResigerForm").submit();
}

/**
*
*/
function fnSendToClassify() {
   if ( ($("#allSubject option:selected").length) == 0) {
       alert("추가할 과정을 선택하세요.");
       return false;
   }

   $("#allSubject option:selected").each( function() {
       $("#randomSubject").append($(this));
       // $("#randomSubject").prepend("<option value=\"" + $(this).val() + "\">" +  $(this).text()  + "</option>");
       // $(this).remove();
   });

   $("#allSubject").css("width", "420px");
   $("#randomSubject").css("width", "420px");
}

/**
*
*/
function fnRemoveFromClassify() {
   if ( ($("#randomSubject option:selected").length) ==0 ) {
       alert("제거할 과정을 선택하세요.");
       return false;
   }

   $("#randomSubject option:selected").each( function() {
       $("#allSubject").append($(this));
   });
   $("#allSubject").css("width", "420px");
   $("#randomSubject").css("width", "420px");
}

function fnSaveSubjectList() {
   $("#randomSubject > option").attr("selected", true);

   var param = $("#oSubjectResigerForm").serialize();

   $.ajax({
           type : "post"
       ,   url : "/servlet/controller.course.SubjectRandomServlet"
       ,   dataType : "json"
       ,   data : param
       ,   success : function (data) {
               if(data.resultCnt > 0) {
                   alert("등록되었습니다.");
                   fnGotoList($('#gubun').val());
               }else{
                   alert("등록을 실패하였습니다.");
               }
           }
       ,   complete : function(arg1, arg2) {
           }
       ,   error : function(arg1, arg2) {
       }
   });
}
//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form id="oSubjectResigerForm" name="subjectResigerForm" method="post">
    <input type="hidden" id="oProcess" name="process"  value="insert" />
    <input type="hidden" id="gubun" name="gubun"  value="<%= gubun %>" />

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
							<td width="12%">
								<table cellspacing="0" cellpadding="0" class="s_table" id="SAreaTab">
									<tr>
										<td rowspan="3" class="blue_butt_left" id="Td1"></td>
										<td class="blue_butt_top" id="Td2"></td>
										<td rowspan="3" class="blue_butt_right" id="Td3"></td>
									</tr>
									<tr>
										<td class="blue_butt_middle" id="Td4"><a href="javascript:fnGotoList('S')" class="c">정규과정</a></td>
									</tr>
									<tr>
										<td class="blue_butt_bottom" id="Td5"></td>
									</tr>
								</table>
							</td>
							<td width="12%">
								<table cellspacing="0" cellpadding="0" class="s_table" id="OAreaTab">
									<tr>
										<td rowspan="3" class="black_butt_left" id="Td1"></td>
										<td class="black_butt_top" id="Td2"></td>
										<td rowspan="3" class="black_butt_right" id="Td3"></td>
									</tr>
									<tr>
										<td class="black_butt_middle" id="Td4"><a href="javascript:fnGotoList('O')" class="c">열린강좌</a></td>
									</tr>
									<tr>
										<td class="black_butt_bottom" id="Td5"></td>
									</tr>
								</table>
							</td>
							<td width="*">&nbsp;</td>
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
                    <td class="table_title">전체과정 (<%= subjAllList.size() %> 과정) <br/>(기 등록된 과정 제외) </td>
                    <td class="table_title">보내기/<br/>제거하기</td>
                    <td class="table_title">등록된 과정 (<%= subjSelectedList.size() %> 과정)</td>
                </tr>
                <tr>
                    <td class="table_01">
                        <select multiple id="allSubject" name="allSubject" size="20" style="font-size:10pt; width:420px; background-color:#efefef;" ondblclick="fnSendToClassify();">
<%
        if ( subjAllList.size() > 0 ) {
            for( int i = 0 ; i < subjAllList.size() ; i++ ){
                dbox = (DataBox)subjAllList.get(i);
%>
                            <option value="<%= dbox.getString("d_course") %>"><%= dbox.getString("d_coursenm") %></option>
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
                        <select multiple id="randomSubject" name="randomSubject" size="20" style="font-size:10pt; width:420px; background-color:#ffffb3;" ondblclick="fnRemoveFromClassify();">
<%
        if ( subjSelectedList.size() > 0 ) {
            for( int i = 0 ; i < subjSelectedList.size() ; i++ ){
                dbox = (DataBox)subjSelectedList.get(i);
%>
                            <option value="<%= dbox.getString("d_course") %>"><%= dbox.getString("d_coursenm") %></option>
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
        </td>
    </tr>
</table>
</form>

</body>
</html>
