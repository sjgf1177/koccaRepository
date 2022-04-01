<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
	response.setHeader("Content-Disposition", "attachment; filename=za_off_Bill_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");
	
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	String  v_selBillStatus = box.getString("p_selBillStatus");
	String  v_selBillMethod = box.getString("p_selBillMethod");
	
	String  v_billstart = box.getString("p_billstart");
    String  v_billend   = box.getString("p_billend");

    String  v_selMemberType = box.getString("p_selMemberType");
    
    String  v_process   = box.getString("p_process");
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_hsubjnm   = box.getString("p_hsubjnm");


    //DEFINED in relation to select START
    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getString("s_grseq");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수
//    String  v_ongo			= "whenSelection('selectAll')";
    //DEFINED in relation to select END

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body>
    <table border=1>
          <tr>
            <td><b>NO</b></td>
            <td><b>과정명</b></td>
            <td><b>ID</b></td>
            <td><b>성명</b></td>
            <td><b>회원구분</b></td>
            <td><b>결제상태</b></td>
            <td><b>결재금액</b></td>
            <td><b>결제방법</b></td>
            <td><b>결재자</b></td>
            <td><b>결재일</b></td>
            <td><b>결재취소</b></td>
          </tr>
          <tr>
            <td colspan="11">등록된 내용이 없습니다</td>
          </tr>
        </table>
</body>
</html>
