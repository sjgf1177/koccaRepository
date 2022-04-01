<%
/**
 * file name : za_SettelementAdmin_E.jsp
 * date      :
 * programmer:
 * function  : 결제관리 리스트 조회화면
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.polity.*" %>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>

<%	response.setHeader("Content-Disposition", "inline; filename=za_SettelementAdmin_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

	String  v_process  = "selectList";

    String  ss_grcode			= box.getString("s_grcode");		//교육그룹
    String  ss_gyear			= box.getString("s_gyear");			//년도
    String  ss_grseq			= box.getString("s_grseq");			//교육차수
    String  ss_grseqnm			= box.getString("s_grseqnm");		//교육차수명
    String  ss_mastercd			= box.getString("s_mastercd");		//과정&코스
    String  ss_upperclass		= box.getString("s_upperclass");	//과정대분류
    String  ss_middleclass		= box.getString("s_middleclass");	//과정중분류
    String  ss_lowerclass		= box.getString("s_lowerclass");	//과정소분류
    String  ss_subjcourse		= box.getString("s_subjcourse");	//과정&코스
    String  ss_subjseq			= box.getString("s_subjseq");		//과정 차수
    String  ss_accountstart		= box.getString("s_accountstart");	//신청시작일
    String  ss_accountend		= box.getString("s_accountend");	//신청종료일
    String  ss_accountstat		= box.getString("s_accountstat");	//입금상태
	
    String  v_action    = box.getString("p_action");

	String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
	String v_dispnum	= "";

	int     v_totalpage =  0;
    int     v_rowcount  =  0;
    int     v_rowspan   =  0;
    int     v_total     =  0;
    int     i           =  0;

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="1" cellspacing="0" cellpadding="0" height="663">
	<tr>
		<td align="center" height="30"><font size="3"><b>카드 결제 관리 리스트</b></font></td>
	</tr>
  <tr>
    <td align="center" valign="top">
        <%
        String v_ispropose          ="";
        String v_proposetype        ="";
        String v_isedutarget        ="";
        String v_educnt             ="";
        String v_mastercd           ="";
        String v_masternm           ="";

        %>

        <!----------------- 과정기준조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5" border="1">	
          <tr>
			<td ><b>No</b></td>
            <td ><b>신청인<br>(ID)</b></td>
            <td ><b>신청<br>과목</b></td>
            <td ><b>차수</b></td>
            <td ><b>교육기간</b></td>
            <td ><b>신청일</b></td>
            <td ><b>기본<br>수강료</b></td>
            <td ><b>할인<br>구분</td>
            <td ><b>포인트</b></td>
            <td ><b>결제일</b></td>
            <td ><b>결제번호</b></td>
            <td ><b>승인번호</b></td>
            <td ><b>결제금액</b></td>
            <td ><b>승인<br>구분</b></td>
          </tr>

<%  
        if (v_action.equals("go")) {
        ArrayList  list = (ArrayList)request.getAttribute("selectList");

        int v_today = Integer.parseInt(FormatDate.getDate("yyyyMMddHH"));  //오늘 날짜 일시 초기화

		String v_subj		= "";
		String v_subjnm		= "";
		String v_membergubun= "";
		String v_userid		= "";
		String v_uname		= "";
		String v_subjseq	= "";		
		String v_chkfinal	= "";
		String v_scyear		= "";
		String v_edustart	= "";
		String v_eduend 	= "";
		String v_appdate	= "";
		String v_subjprice	= "";
		String v_discountrate	= "";
		String v_usepoint	= "";
		String v_paymoney	= "";
		String v_accountname= "";
		String v_paydate	= "";
		String v_paystat	= "";
		String v_cardno		= "";
		String v_cardapprovalno		= "";
		String v_realpaymoney = "";
		String v_cardyn		= "";

        for (i=0; i<list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);

			v_subj			= dbox.getString("d_subj");
			v_subjnm		= dbox.getString("d_subjnm");
			v_userid		= dbox.getString("d_userid");
			v_uname			= dbox.getString("d_name");
			v_subjseq		= dbox.getString("d_subjseq");
			v_scyear		= dbox.getString("d_year");
			v_edustart		= dbox.getString("d_edustart");
			v_eduend		= dbox.getString("d_eduend");
			v_appdate		= dbox.getString("d_appdate");
			v_subjprice		= dbox.getString("d_subjprice");
			v_discountrate	= dbox.getString("d_discountrate");
			v_usepoint		= dbox.getString("d_usepoint");
			v_realpaymoney	= dbox.getString("d_realpaymoney");
			v_paymoney		= dbox.getString("d_paymoney");
			v_accountname	= dbox.getString("d_accountname");
			v_paydate		= dbox.getString("d_paydate");
			v_paystat		= dbox.getString("d_paystat");
			v_cardno		= dbox.getString("d_cardno");
			v_cardyn		= dbox.getString("d_cardyn");
			v_cardapprovalno = dbox.getString("d_cardapprovalno");


			if(v_cardyn.equals("Y")) {v_cardyn = "승인";}
			else {v_cardyn = "미승인";}

%>
            <tr align="left">
                <td ><%=(i+1) %></td>
                <td ><%=v_uname %><br>(<%=v_userid%>)</a></td>
                <td ><%=v_subjnm %></td>
                <td ><%=v_subjseq %></td>
                <td ><%=FormatDate.getFormatDate(v_edustart,"yyyy-MM-dd")%> ~<br><%=FormatDate.getFormatDate(v_eduend,"yyyy-MM-dd")%></td>
				<td ><%=FormatDate.getFormatDate(v_appdate,"yyyy-MM-dd HH:mm:ss")%></td>	
                <td ><%=v_subjprice %></td>
                <td ><%=v_discountrate %></td>
                <td ><%=v_usepoint %></td>
                <td ><%=v_paydate %></td>
                <td ><%=v_cardno %></td>
                <td ><%=v_cardapprovalno %></td>
                <td ><%=v_paymoney %></td>
                <td ><%=v_cardyn %></td>
            </tr>
<%          //}
        }
		if (i == 0)
			{ %>
			<tr>
				<td align="center" bgcolor="#FFFFFF" height="100" colspan="14">등록된 내용이 없습니다.</td>
			  </tr>
			<%}
    }

%>
           
        </table>
      </td>
  </tr>
</table>
</body>
</html>