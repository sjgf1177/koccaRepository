
<%
//**********************************************************
//  1. 제      목: 열린강좌 일괄관리
//  2. 프로그램명 : za_GoldClassGroup_L.jsp
//  3. 개      요: 열린강좌 일괄관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 김기종 2016.12.07
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr"%>
<%@page errorPage="/learn/library/error.jsp"%>
<%@ page import="java.util.ArrayList"%>

<%@ page import="com.credu.library.DataBox"%>
<%@ page import="com.credu.library.FormatDate"%>
<%@ page import="com.credu.library.RequestBox"%>
<%@ page import="com.credu.library.PageUtil"%>
<%@ page import="com.credu.library.StringManager"%>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process = box.getString("p_process");
    String  v_grcode = box.getString("p_grcode");

    String v_grcodenm = "";
    String v_domain = "";

    DataBox dbox = null;

    /* 선택 교육그룹정보 조회 */
	DataBox groupInfo = (DataBox)request.getAttribute("groupInfo");
    if(groupInfo != null){
    	v_grcodenm = groupInfo.getString("d_grcodenm");
    	v_domain = groupInfo.getString("d_domain");
    }
	
 	/* 등록 열린강좌 제외 목록 */
    ArrayList<DataBox> goldclassList = (ArrayList<DataBox>)request.getAttribute("goldclassList");
 	
    /* 등록 열린강좌 목록 */
    ArrayList<DataBox> groupList = (ArrayList<DataBox>)request.getAttribute("groupList");

%>
<html>
<head>
<title>열린강좌 운영관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/CSS" href="/css/jquery-ui-1.10.4.css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/script/jquery-ui-1.10.4.js"></script>
<script type="text/javascript">
    $(document).ready( function() {

    });
    
    
    /**
    * 목록 페이지로 이동
    */
    function fnListPage(){
    	$('#p_process').val('list');
    	$("#form1").attr({
    		action : '/servlet/controller.infomation.GoldClassGroupServlet',
    		target : '_self'
    	}).submit();
    }
    
    
    function fnSendToClassify() {
        if ( ($("#p_seq option:selected").length) == 0) {
            alert("추가할 과정을 선택하세요.");
            return false;
        }

        $("#p_seq option:selected").each( function() {
            $("#p_seqs").append($(this));
            // $("#p_seqs").prepend("<option value=\"" + $(this).val() + "\">" +  $(this).text()  + "</option>");
            // $(this).remove();
        });

        $("#p_seq").css("width", "420px");
        $("#p_seqs").css("width", "420px");
    }

    /**
     *
     */
    function fnRemoveFromClassify() {
        if ( ($("#p_seqs option:selected").length) ==0 ) {
            alert("제거할 과정을 선택하세요.");
            return false;
        }

        $("#p_seqs option:selected").each( function() {
            $("#p_seq").append($(this));
        });
        $("#p_seq").css("width", "420px");
        $("#p_seqs").css("width", "420px");
    }

    function fnInsert() {
        $("#p_seqs > option").attr("selected", true);

        $('#p_process').val('insert');
        var param = $("#form1").serialize();
        $.ajax({
                type : "post"
            ,   url : "/servlet/controller.infomation.GoldClassGroupServlet"
            ,   dataType : "json"
            ,   data : param
            ,   success : function (data) {
                    if(data.isOk > 0) {
                        alert("등록되었습니다.");
                    }
                }
            ,   complete : function(arg1, arg2) {
                }
            ,   error : function(arg1, arg2) {
            }
        });
    }

</script>
</head>

<body bgcolor="#ffffff" text="#000000" topmargin="0" leftmargin="0">
	<form id="form1" name="form1">
		<input type="hidden" id="p_process" name="p_process" value="<%= v_process %>" /> <input type="hidden" id="p_grcode" name="p_grcode" value="<%=v_grcode%>" />


		<table width="1000" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" valign="top">
					<!-- title 시작 //-->
					<table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
						<tr>
							<td><img src="/images/admin/portal/s.1_19.gif" /></td>
							<td align="right"><img src="/images/admin/common/sub_title_tail.gif" /></td>
						</tr>
					</table> <!-- title 끝 //--> <br />


					<table class="table_out" cellspacing="1" cellpadding="5">
						<tr>
							<td colspan="2" class="table_top_line"></td>
						</tr>
						<tr>
							<td width="16%" height="25" class="table_title"><b>교육그룹</b></td>
							<td class="table_02_2" width="81%"><b>[<%=v_grcode%>] <%=v_grcodenm%></b></td>
						</tr>
						<tr>
							<td class="table_title" height="25"><b>도메인</b></td>
							<td class="table_02_2"><a href="<%=v_domain%>" target="blank"><%=v_domain%></a></td>
						</tr>
					</table> 
					<br/>
					<br/>
					
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
							<td class="table_title">전체강좌 (<%= goldclassList.size() %> 강좌) <br />(기 등록된 강좌 제외)
							</td>
							<td class="table_title">보내기/<br />제거하기
							</td>
							<td class="table_title">등록된 강좌 (<%= groupList.size() %> 강좌)
							</td>
						</tr>
						<tr>
							<td class="table_01"><select multiple id="p_seq" size="20" style="font-size: 10pt; width: 420px; background-color: #efefef;" ondblclick="fnSendToClassify();">
								<%
						        if ( goldclassList.size() > 0 ) {
						            for( int i = 0 ; i < goldclassList.size() ; i++ ){
						                dbox = (DataBox)goldclassList.get(i);
								%>
									<option value="<%= dbox.getString("d_seq") %>">[<%= dbox.getString("d_seq") %>]
										<%= dbox.getString("d_lecnm") %></option>
								<%
						            }
						        } else {
								%>
									<option value="">선택할 수 있는 강좌가 없습니다.</option>
								<%
						        }
								%>
							</select></td>
							<td class="table_02_1">
								<button id="oCancelBtn" name="cancelBtn" class="btn_basic" onclick="fnSendToClassify(); return false;">&gt;&gt;</button>
								<br />
							<br />
								<button id="oCancelBtn" name="cancelBtn" class="btn_basic" onclick="fnRemoveFromClassify(); return false;">&lt;&lt;</button>
							</td>
							<td class="table_01"><select multiple id="p_seqs" name="p_seqs" size="20" style="font-size: 10pt; width: 420px; background-color: #ffffb3;" ondblclick="fnRemoveFromClassify();">
								<%
						        if ( groupList.size() > 0 ) {
						            for( int i = 0 ; i < groupList.size() ; i++ ){
						                dbox = (DataBox)groupList.get(i);
								%>
									<option value="<%= dbox.getString("d_seq") %>">[<%= dbox.getString("d_seq") %>]
										<%= dbox.getString("d_lecnm") %></option>
								<%
						            }
						        } else {
								%>
									<option value="">등록된 강좌가 없습니다.</option>
								<%
						        }
								%>
							</select></td>
						</tr>
					</table> <br /> <br />
					
					<a href="#none" onclick="fnInsert();"><img src="/images/admin/button/btn_register.gif" border="0"></a>
		        	<a href="#none" onclick="fnListPage();"><img src="/images/admin/button/btn_list.gif" border="0"></a>
					
				</td>
			</tr>
		</table>
	</form>

	<%@ include file="/learn/library/getJspName.jsp"%>
</body>
</html>
