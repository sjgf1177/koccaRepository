<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="java.util.ArrayList" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    ArrayList<DataBox> gubunList1 = (ArrayList)request.getAttribute("gubunList1");
    ArrayList<DataBox> lvCdList = (ArrayList)request.getAttribute("lvCdList");

    String upperClsCd = box.getStringDefault("upperClsCd", "0000");
%>

<html>
<head>
	<title>등록 | 과정분류 관리 | 과정 | 관리자 | 한국콘텐츠아카데미</title>
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
		//jQuery Ready
		$(document).ready(function(){
			//숫자 체크
			$(".gubunBox").change(function (){
				fnLvCodeList($(this));
			});
		});
		
		//하위 분류코드 목록
		function fnLvCodeList($this){
			var ordr = $this.attr("ordr");
			
			if(ordr > 2){
				return;
			}
			
 			$.ajax({
		        type: "POST",
		        dataType:"json",
		        url:"/servlet/controller.course.SubjectClassifyServlet",
		        data: {
		            "process": "levelCodeList",
		            "p_ordr": Number(ordr) + 1,
		            "p_hcd" : $this.val() 
		        },
		        success: function(data) {
		        	if(ordr == 1){
			        	$("#gubun2 > option").remove();
		        		$("#gubun2").append("<option value=\"\">선택하세요.</option>");
			        	$("#gubun3 > option").remove();	
			        	$("#gubun3").append("<option value=\"\">선택하세요.</option>");
		        		
			        	$.each(data, function(idx, obj) {
			        		$("#gubun2").append("<option value=\""+obj.d_code+"\">"+ obj.d_codenm +"</option>")
			        	});
		        	}else if(ordr == 2){
			        	$("#gubun3 > option").remove();
		        		$("#gubun3").append("<option value=\"\">선택하세요.</option>");
			        	$.each(data, function(idx, obj) {
			        		$("#gubun3").append("<option value=\""+obj.d_code+"\">"+ obj.d_codenm +"</option>")
			        	});		        		
		        	}
				},
				error:function (){
		        	if(ordr == 1){
			        	$("#gubun2 > option").remove();
		        		$("#gubun2").append("<option value=\"\">선택하세요.</option>");
			        	$("#gubun3 > option").remove();
		        		$("#gubun3").append("<option value=\"\">선택하세요.</option>");	
		        	}else if(ordr == 2){
			        	$("#gubun3 > option").remove();
		        		$("#gubun3").append("<option value=\"\">선택하세요.</option>");	        		
		        	}	        		
				}
		  	});
		}		
	
		function fnRegisterSubjectClassify() {
		    if ( $("#gubun1").val() == "" ) {
		        alert("장르를 선택하세요.");
		        $("#gubun1").focus();
		        return;
		    } else if ( $("#gubun2").val() == "" ) {
		        alert("대분류를 선택하세요.");
		        $("#gubun2").focus();
		        return;
		    } else if ( $("#gubun3").val() == "" ) {
		        alert("소분류를 선택하세요.");
		        $("#gubun3").focus();
		        return;
		    } else {
		        $("#oSubjClassifyRegisterForm").action = "/servlet/controller.course.SubjectClassifyServlet";
		
		        $("#oProcess").val("register");
		        $("#oSubjClassifyRegisterForm").submit();
		    }
		}
		
		//숫자체크
		function isNumber(textId){
			var text = "";
			$("." + textId).bind("keydown keyup focusout ",function(e){
				if ((e.which && e.keyCode) &&
					(e.which != 8) && (e.keyCode != 8) &&
					(e.which != 37) && (e.keyCode != 37) &&
					(e.which != 39) && (e.keyCode != 39) &&
					(e.which != 46) && (e.keyCode != 46)) {
	  			 	$(this).val( $(this).val().replace(/[^0-9]/gi,"") * 1 );
				}else{
					if($.trim($(this).val()) == ""){
						$(this).val("0");
					}
				}
	  		});
		}		
	</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
	<form id="oSubjClassifyRegisterForm" name="subjClassifyRegisterForm" method="post">
	    <input type="hidden" id="oProcess" name="process"  value="" />
	    <input type="hidden" id="oUpperClsCd" name="upperClsCd"  value="<%= upperClsCd %>" />

		<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
		    <tr>
		        <td align="center" valign="top">
		            <!-- title 시작 //-->
		            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="">
		                <tr>
		                    <td style="font-size:14pt; font-weight:bold; padding-top:16px;">과정 분류 관리</td>
		                    <td align="right"><!--  <img src="/images/admin/common/sub_title_tail.gif" ></td> -->
		                </tr>
		            </table>
		            <!-- title 끝 //-->
		            <br/>
		
		            <!-- tab 시작 //-->
		            <table width="97%" border="0" cellspacing="0" cellpadding="0">
		                <tr>
		                    <td width="12%">
		                        <table cellspacing="0" cellpadding="0" class="s_table">
		                            <tr>
		                                <td rowspan="3" class="blue_butt_left" id="oTb1Td1"></td>
		                                <td class="blue_butt_top" id="oTb1Td2"></td>
		                                <td rowspan="3" class="blue_butt_right" id="oTb1Td3"></td>
		                            </tr>
		                            <tr>
		                                <td class="blue_butt_middle" id="oTb1Td4">온라인 분류별</td>
		                            </tr>
		                            <tr>
		                                <td class="blue_butt_bottom" id="oTb1Td5"></td>
		                            </tr>
		                        </table>
		                    <td width="87%">&nbsp;</td>
		                </tr>
		                <tr>
		                    <td height="2" colspan="4" bgcolor="#6699CC"></td>
		                </tr>
		            </table>
		            <!-- tab 끝 //-->
		
		            <!-- 등록 화면 시작 //-->
		            <table class="table_out" cellspacing="1" cellpadding="5">
		                <colgroup>
		                    <col width="120px;" />
		                    <col width="200px;" />
		                    <col width="120px;" />
		                    <col width="*;" />
		                </colgroup>
		                <tr>
		                    <td colspan="4" class="table_top_line"></td>
		                </tr>
		                <tr>
		                    <td height="25" class="table_title"><strong>분류</strong></td>
		                    <td class="table_02_2" colspan="3">
		                        <select id="gubun1" name="gubun1" class="gubunBox" ordr="1">
		                        	<option value="">선택하세요.</option>
                       	<%
					    if (gubunList1.size() > 0) {
					        for ( DataBox dbox : gubunList1) {
					        	if(!"A".equals(dbox.getString("d_code"))){
					    %>
                       				<option value="<%=dbox.getString("d_code")%>"><%=dbox.getString("d_codenm")%></option>
					    <% 
					        	}
					        }
					    }
						%>
		                        </select>
		                        <select id="gubun2" name="gubun2" class="gubunBox" ordr="2">
		                        	<option value="">선택하세요.</option>
		                        </select>
		                        <select id="gubun3" name="gubun3" class="gubunBox" ordr="3">
		                        	<option value="">선택하세요.</option>
		                        </select>                      
		                    </td>
		                </tr>
		            </table>
		            <br/>
		            <br/>
		            <button id="oRegisterBtn" name="registerBtn" class="btn_reg" onclick="fnRegisterSubjectClassify();return false;" >등록</button>&nbsp;&nbsp;
		            <button id="oCancelBtn" name="cancelBtn" class="btn_reg" onclick="history.back(-1);" >취소</button>
		
		        </td>
		    </tr>
		</table>
	</form>
</body>
</html>
