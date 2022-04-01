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

    ArrayList classifiedSubjectList = (ArrayList)request.getAttribute("classifiedSubjectList");
    ArrayList classifiedGoldClassList = (ArrayList)request.getAttribute("classifiedGoldClassList");
    ArrayList levelCodeList = (ArrayList)request.getAttribute("levelCodeList");
    ArrayList gubunNmList = (ArrayList)request.getAttribute("gubunNmList");

    DataBox dbox = null;
    DataBox gbBox = null;
    DataBox dvBox = null;

    String upperClsCd = box.getString("upperClsCd");
    String gubun1 = box.getString("gubun1");
    String gubun2 = box.getString("gubun2");
    String gubun3 = box.getString("gubun3"); 
    String hcd = box.getString("hcd"); 
    
    int topColspan = 2 + levelCodeList.size();
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
function fnSaveLevel() {
    $("#oClassifedSubject > option").attr("selected", true);
    $("#oClassifedGoldClass > option").attr("selected", true);

    if(confirm("저장하시겠습니까?")){
	    var param = $("#oSubjectResigerForm").serialize();
	
	    $.ajax({
	            type : "post"
	        ,   url : "/servlet/controller.course.SubjectClassifyServlet"
	        ,   dataType : "json"
	        ,   data : param
	        ,   success : function (data) {
	                if(data.result > 0) {
	                    alert("저장되었습니다.");
	                }
	            }
	        ,   complete : function(arg1, arg2) {
	            }
	        ,   error : function(arg1, arg2) {
	        }
	    });
    }   
}

function fnGotoList() {
        $("#oSubjectResigerForm").action = "/servlet/controller.course.SubjectClassifyServlet";

        $("#oProcess").val("listPage");
        $("#oSubjectResigerForm").submit();
}

function fnOnlineSubjPage( gubun1, gubun2, gubun3, lvcode ) {
    $("#oSubjectResigerForm").action = "/servlet/controller.course.SubjectClassifyServlet";
    $("#oProcess").val("manageSubjectPage");
    $("#gubun1").val(gubun1);
    $("#gubun2").val(gubun2);
    $("#gubun3").val(gubun3);
    $("#hcd").val(lvcode);
    $("#oSubjectResigerForm").submit();
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
	<form id="oSubjectResigerForm" name="subjectResigerForm" method="post">
	    <input type="hidden" id="oProcess" name="process"  value="saveCourseLevel" />
	    <input type="hidden" id="oUpperClsCd" name="upperClsCd"  value="<%= upperClsCd %>" />
	    <input type="hidden" id="gubun1" name="gubun1"  value="<%= gubun1 %>" />
	    <input type="hidden" id="gubun2" name="gubun2"  value="<%= gubun2 %>" />
	    <input type="hidden" id="gubun3" name="gubun3"  value="<%= gubun3 %>" />
	    <input type="hidden" id="hcd" name="hcd"  value="<%= hcd %>" />

		<table width="1200" border="0" cellspacing="0" cellpadding="0" height="663">
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
								<button id="oRegBtn" name="btn" class="btn_reg" onclick="fnSaveLevel(); return false;">저장</button>
								<button id="oSubjBtn" name="btn" class="btn_reg" onclick="fnOnlineSubjPage(); return false;">과정연결</button>
								<button id="oListBtn" name="btn" class="btn_reg" onclick="fnGotoList(); return false;">목록</button>								
							</td>
						</tr>
						<tr><td height="3"></td></tr>
					</table>
					<!-- 버튼 영역 e -->            		
            		<br/>
		            <!-- 과정유형 시작 //-->
		            <table width="97%" border="0" cellspacing="0" cellpadding="0">
		                <tr>
		                    <td style="font-size:12pt; font-weight:bold; width:50%;">
		                    	과정 난이도 설정 [
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
		                </tr>
		            </table>
		            <!-- 과정유형 끝 //-->

		            <!-- 등록 화면 시작 //-->
		            <table width="100%">
		            	<tr>
		            		<td style="width: 50%; display: ; position:relative">
								<table cellspacing="1" cellpadding="2" class="table_out" style="position:absolute; top:0;">
					                <colgroup>
					                    <col width="12%;" />
					                    <col width="" />
							<%
								if(levelCodeList != null && levelCodeList.size() > 0){
									for( int i = 0; i < levelCodeList.size(); i++ ){
							%>
										<col width="8%" />
							<%			
									}
								}
							%>	
					                </colgroup>
					                <tr>
					                    <td colspan="<%=topColspan%>" class="table_top_line"></td>
					                </tr>					                
					                <tr>
					                    <td class="table_title">과정코드</td>
					                    <td class="table_title">과정명</td>
							<%
								if(levelCodeList != null && levelCodeList.size() > 0){
									for( int i = 0; i < levelCodeList.size(); i++ ){
										dbox = (DataBox)levelCodeList.get(i);
							%>
										<td class="table_title"><%=dbox.getString("d_codenm") %></td>
							<%			
									}
								}
							%>						                    
					                </tr>				                
				<%
					if(classifiedSubjectList != null && classifiedSubjectList.size() > 0){
						for( int i = 0; i < classifiedSubjectList.size(); i++ ){
							dbox = (DataBox)classifiedSubjectList.get(i);
				%>
									<tr>	
										<td class="table_02_1">
											<%=dbox.getString("d_subj") %>
										</td>
										<td class="table_02_2">
											<%=dbox.getString("d_subjnm") %>
										</td>	
						<%																																																		
							if(levelCodeList != null && levelCodeList.size() > 0){
								for( int j = 0; j < levelCodeList.size(); j++ ){
									dvBox = (DataBox)levelCodeList.get(j);
									if(dbox.getString("d_lvcode").equals(dvBox.getString("d_code"))){
						%>
										<td class="table_02_1">
											<input type="radio" name="<%=dbox.getString("d_subj") %>_lv" checked="checked" value="<%=dvBox.getString("d_code") %>" />
										</td>
						<%		
									}else{
						%>
										<td class="table_02_1">
											<input type="radio" name="<%=dbox.getString("d_subj") %>_lv" value="<%=dvBox.getString("d_code") %>" />
										</td>
						<%												
									}
								}
							}
						%>	
										<input type="hidden" name="p_scd" value="<%=dbox.getString("d_subj") %>" />
									</tr>
				<%			
						}
					}
				%>						                
					            </table>
		            		</td>
		            		<td style="width: 50%; display: ;">
								<table cellspacing="1" cellpadding="2" class="table_out">
					                <colgroup>
					                    <col width="12%;" />
					                    <col width="" />
							<%
								if(levelCodeList != null && levelCodeList.size() > 0){
									for( int i = 0; i < levelCodeList.size(); i++ ){
							%>
										<col width="8%" />
							<%			
									}
								}
							%>	
					                </colgroup>
					                <tr>
					                    <td colspan="<%=topColspan%>" class="table_top_line"></td>
					                </tr>					                
					                <tr>
					                    <td class="table_title">과정코드</td>
					                    <td class="table_title">과정명</td>
							<%
								if(levelCodeList != null && levelCodeList.size() > 0){
									for( int i = 0; i < levelCodeList.size(); i++ ){
										dbox = (DataBox)levelCodeList.get(i);
							%>
										<td class="table_title"><%=dbox.getString("d_codenm") %></td>
							<%			
									}
								}
							%>						                    
					                </tr>				                
				<%
					if(classifiedGoldClassList != null && classifiedGoldClassList.size() > 0){
						for( int i = 0; i < classifiedGoldClassList.size(); i++ ){
							dbox = (DataBox)classifiedGoldClassList.get(i);
				%>
									<tr>	
										<td class="table_02_1">
											<%=dbox.getString("d_seq") %>
										</td>
										<td class="table_02_2">
											<%=dbox.getString("d_lecnm") %>
										</td>	
						<%																																																		
							if(levelCodeList != null && levelCodeList.size() > 0){
								for( int j = 0; j < levelCodeList.size(); j++ ){
									dvBox = (DataBox)levelCodeList.get(j);
									if(dbox.getString("d_lvcode").equals(dvBox.getString("d_code"))){
						%>
										<td class="table_02_1">
											<input type="radio" name="<%=dbox.getString("d_seq") %>_lv" checked="checked" value="<%=dvBox.getString("d_code") %>" />
										</td>
						<%		
									}else{
						%>
										<td class="table_02_1">
											<input type="radio" name="<%=dbox.getString("d_seq") %>_lv" value="<%=dvBox.getString("d_code") %>" />
										</td>
						<%												
									}
								}
							}
						%>	
										<input type="hidden" name="p_gcd" value="<%=dbox.getString("d_seq") %>" />
									</tr>
				<%			
						}
					}
				%>						                
					            </table>
		            		</td>		            		
		            	</tr>
		            </table>
        		</td>
    		</tr> 
    		<tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
		</table>
	</form> 
</body>
</html>
