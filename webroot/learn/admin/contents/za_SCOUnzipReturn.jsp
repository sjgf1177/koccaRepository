<%
//**********************************************************
//  1. 제      목: 스콤 콘텐츠 리스트
//  2. 프로그램명 : za_SCOUnzipReturn.jsp
//  3. 개      요: 콘텐츠 zip파일 압축 풀기 화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 김기수 2004. 11. 24
//  7. 수      정:
//***********************************************************
%>

<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%@ page contentType="text/html; charset=euc-kr" %>
<%
	String v_flag			= (String)request.getAttribute("UNZIP_FLAG");
	String v_nextpage_url	= (String)request.getAttribute("NextPage_URL");
	String v_scolocateno_1	= (String)request.getAttribute("v_scolocateno_1");
	String v_unzip_filename	= (String)request.getAttribute("v_unzip_filename");
	String p_scolocate	= (String)request.getAttribute("p_scolocate");
	String p_content_sync	= (String)request.getAttribute("p_content_sync");
	String p_cpseq	= (String)request.getAttribute("p_cpseq");

	String v_alertmessage		= "";
	String v_actiontype		= "";
	String v_nextactionfunc		= "";
	String v_manifestfilename = "";
	

	if(v_nextpage_url == null || v_nextpage_url.equals("")) v_nextpage_url = "/learn/library/error.jsp";
	if(v_flag == null || v_flag.equals("") || v_flag.equals("0")){
		v_alertmessage		= (String)request.getAttribute("Error_MSG");
		v_actiontype		= "Fail";
	}else{
		v_alertmessage		= "업로드되었습니다.";
		v_actiontype		= "Success";		
		v_manifestfilename = "imsmanifest.xml";
	}
	v_nextactionfunc		= "Result_Message('"+ v_alertmessage +"', '"+ v_actiontype +"', '"+ v_nextpage_url +"')";
%>

<html>
<head>
	<link href="/common/Basic_Style.css" rel="stylesheet" type="text/css">
	<SCRIPT language=javascript src="/common/Func_Validate_CHK.js"></SCRIPT>
	<script language="javascript">
	function Result_Message(msg, action_type, page_name){	// Background(등록/수정/삭제)에서 처리 결과 메세지 출력후 Hidden과 함께 목록 페이지로 이동
		alert(msg);
		if(action_type == "Fail"){
			history.back();
		}else{
			var frm = document.frm;
			frm.target = "";
			frm.action = page_name;
			frm.submit();
		}
	}
	</script>
</head>
<body class="bodystyle" onload="<%=v_nextactionfunc%>">
<form name="frm" method="post">
<input type=hidden name="v_scolocateno_1" value=<%=v_scolocateno_1%>>
<input type=hidden name="v_manifestfilename" value=<%=v_manifestfilename%>>
<input type=hidden name="v_unzip_flag" value=<%=v_flag%>>
<input type=hidden name="v_unzip_filename" value=<%=v_unzip_filename%>>
<input type=hidden name="p_scolocate" value=<%=p_scolocate%>>
<input type=hidden name="p_content_sync" value=<%=p_content_sync%>>
<input type=hidden name="p_cpseq" value=<%=p_cpseq%>>


</form>
</body>
</html>