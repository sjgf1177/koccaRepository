<%
//**********************************************************
//  1. 제      목: 학습제약조건
//  2. 프로그램명: za_StudyControl_L.jsp
//  3. 개      요: 학습제약조건
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: S.W.Kang 2005. 02. 22
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
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    
	//DataBox dbox = (DataBox)request.getAttribute("StudyControl");

    ArrayList list1 = (ArrayList)request.getAttribute("StudyControlExp"); 		//학습제약정보

    String  ss_gubun     = box.getString("p_gubun");            //구분
	
	int		v_seq	= 0;

%>
<html>	
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="Javascript">
<!--

	//학습제약조건 리스트 화면 조회시
	function whenSubmit() {
		document.form1.p_process.value="ExpListPage";
		document.form1.submit();
	}

	//학습제약조건 예외자를 등록할때
	function whenSave(sc) {

		  if(document.form1.s_company.value == "ALL"){
			alert("회사를 선택하여 주십시오.");
			document.form1.s_company.focus();
			return;
		  }

		  if(document.form1.p_gubun.value == "ALL"){
			alert("구분을 선택하여 주십시오.");
			document.form1.p_gubun.focus();
			return;
		  }

		  if( blankCheck(document.form1.p_userid.value) ){
				alert("아이디를 입력하십시오.");
				return;
		  }

		  document.form1.p_process.value= "ExpInsert";
		  document.form1.submit();
	}

	//학습제약조건 예외자를 수정할때
	function whenUpdate(seq) {
		document.form1.p_seq.value		 = seq;
		document.form1.p_process.value = "ExpUpdate";
		document.form1.submit();
	}

	//학습제약조건 예외자를 삭제할때
	function whenDelete(seq) {
		if (confirm("삭제하시겠습니까?")) {
			document.form1.p_seq.value 		= seq;
			document.form1.p_process.value 	= "ExpDelete";
			document.form1.submit();
		}
	}

//운영자담당 검색
function searchMuser() {
  var p_gubun = 'name';
  var p_key1  = '';
  var p_key2  = 'p_muser';
  var url = "/servlet/controller.library.SearchServlet?p_process=member";
  open_window("",url,"0","0","100","100");
}

// 멤버 검색 후 처리
function receiveMember(userid, name, resno, email, cono, jikwi, jikwinm, comp, compnm, tmp1, tmp2, tmp3){
    document.form1.p_userid.value   = userid;
}
-->
</script>
</head>
<body topmargin=0 leftmargin=0>
<form name="form1" method="post" action="/servlet/controller.system.StudyControlServlet">
<input type="hidden" name="p_process" 	value="">
<input type="hidden" name="p_seq" 		value="">

<table width="780" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td align="center">
			<table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        		<tr> 
          			<td><img src="/images/admin/system/unite_title22.gif" ></td>
          			<td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        		</tr>
      		</table>
		   	<br>
	   		<table width="97%" cellpadding="1" cellspacing="0" class="form_table_out">
        		<tr> 
          			<td align="center">
          				<table width="97%" cellpadding="0" cellspacing="0" class="form_table_bg">
              				<tr> 
                				<td height="7"></td>
              				</tr>
              				<tr> 
                			  <td>
                				<table  border="0" cellspacing="0" cellpadding="0">
                    			  <tr> 
                      				<td> &nbsp; 
										<%= SelectCompanyBean.getCompany(box, false, true)%><!-- getCompany(RequestBox, isChange, isALL)    회사 -->
									</td>
                      				<td width=10></td>
                      				<td align="right"> 구분 </td>
                      				<td>&nbsp; <%=CodeConfigBean.getCodeGubunSelect("0049","",1,"p_gubun",ss_gubun,"",2)%>
									</td>
                      				<td width=10></td>
                      				<td><a href="javascript:whenSubmit();"><img src="/images/admin/button/b_go.gif"  border="0"></a></td>
                      				<td width=10></td>
                    			  </tr>
                  				</table>
                  			  </td>
              				</tr>
              				<tr> 
                			  <td height="7"></td>
              				</tr>
            			</table>
            		</td>
        		</tr>
      		</table>
	  		<br>
			<!-------------소제목 시작-------------------------->
 			<table width="97%" border="0" cellspacing="0" cellpadding="0">
        		<tr> 
          			<td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          			<td class=sub_title>학습 제약조건 예외자 등록</td>
            		<td align="right"><a href="javascript:whenSave()"><img src="/images/admin/button/btn_add.gif" border="0" tabindex=73></a></td>
					<td width=8></td>
        		</tr>
        		<tr> 
          			<td height=6></td>
          			<td height=6></td>
        		</tr>
      		</table> 
        	<!--------------소제목 끝---------------------------->
        	<table width="97%"  cellpadding="0" cellspacing="1" class="table_out">
          		<tr>
            		<td width="80" height="50" class="table_title">아이디</td>
            		<td width="200" class="table_02_1"><input type="text" name="p_userid" class="input" size="10" maxlength="20" readonly> <a href="javascript:searchMuser()" tabindex="21"><img src="/images/admin/button/btn_imsearch.gif" border="0" valign="middle"></a></td>
            		<td width="80" height="50" class="table_title">예외사유</td>
            		<td width="200" class="table_02_2"><input type="text" name="p_reason" class="input" size="50" maxlength="40">
          		</tr>
        	</table>
      		<br>
	  		<br>
			<!--------------학습제약 조건 리스트------------------>
			<table width="97%" cellpadding="0" cellspacing="1" class="table_out">
				<tr>
					<td class="table_title" width="20%" height="20">회사</td>
					<td class="table_title" width="10%">구분</td>
					<td class="table_title" width="10%">아이디</td>
					<td class="table_title" width="10%">성명</td>
					<td class="table_title" width="40%">예외사유</td>
					<td class="table_title" width="10%">기능</td>
				</tr>
				<%
				if (list1.size() != 0) {

					for (int i=0 ; i < list1.size() ; i++) {
						DataBox dbox = (DataBox)list1.get(i);
						v_seq	= dbox.getInt("d_seq");						
				%>
				<tr>
					<td class="table_02_1" width="20%"><%= dbox.getString("d_companynm")%></td>
					<td class="table_02_1" width="10%"><%=CodeConfigBean.getCodeName("0049",dbox.getString("d_gubun")) %></td>
					<td class="table_02_1"><%= dbox.getString("d_userid")%></td>
					<td class="table_02_1"><%= dbox.getString("d_usernm")%></td>
					<td class="table_02_2"><%= dbox.getString("d_reason")%></td>
					<td class="table_02_1">
						<!--a href="javascript:whenUpdate('<%=v_seq%>','0')"><img src="/images/admin/button/btn_save.gif" border="0"></a-->
						<a href="javascript:whenDelete('<%=v_seq%>')"><img src="/images/admin/button/btn_del.gif" border="0"></a>
					</td>
				<%
					}
				}else {
				%>
				<tr>
                	<td class="table_02_1" colspan="7">등록된 내용이 없습니다</td>
              	</tr>
              	<%
              	}
              	%>
			</table>
      		<br>
    	</td>
  	</tr>
  	<tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>