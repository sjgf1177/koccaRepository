<%@ page contentType = "text/html;charset=euc-kr" %>
<%
/**
 * file name : za_DiplomaAdmin_E.jsp
 * date      :
 * programmer:
 * function  : 수료증 관리 조회화면
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.polity.*" %>

<%	
    response.setHeader("Content-Disposition", "attachment; filename=za_DiplomaAdmin_E.xls");
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
		<td align="center" height="30"><font size="3"><b>수료증 관리 리스트</b></font></td>
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
			<td width="10%"><b>과목</b></td>
			<td ><b>회원구분</b></td>
			<td width="10%"><b>ID</b></td>
			<td width="5%"><b>성명</b></td>
			<td width="10%"><b>차수</b></td>
			<td ><b>구분</b></td>
			<td ><b>교육기간</b></td>
			<td ><b>수료여부</a></td>
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
		String v_scyear		= "";
		String v_edustart	= "";
		String v_eduend 	= "";
		String v_isonoff	= "";
		String v_isgradu	= "";
		
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
			v_isonoff		= dbox.getString("d_isonoff");
			v_isgradu		= dbox.getString("d_isgraduated");
			v_membergubun	= dbox.getString("d_membergubun");

			v_dispnum		= dbox.getString("d_dispnum");

			if(v_isonoff.equals("ON")){     v_isonoff="사이버";   }
            else                      {     v_isonoff="집합";     }

			if(v_isgradu.equals("Y")) 
			{
				v_isgradu = "수료";
			}
			else 
			{
				v_isgradu = "미수료";
			}

			if(v_membergubun.equals("C")) {v_membergubun = "기업";}
			if(v_membergubun.equals("U")) {v_membergubun = "대학";}
			else {v_membergubun = "개인";}


%>
            <tr align="left">
				<td ><%=(i + 1) %></td>
                <td ><%=v_subjnm %></td>
                <td ><%=v_membergubun %></td>
                <td><%=v_userid%></td>
                <td ><%=v_uname  %></td>
                <td ><%=v_subjseq %></td>
                <td ><%=v_isonoff %></td>
                <td ><%=FormatDate.getFormatDate(v_edustart,"yyyy-MM-dd")%> ~<br><%=FormatDate.getFormatDate(v_eduend,"yyyy-MM-dd")%></td>
                <td><%=v_isgradu %></td>
                
            </tr>
<%          //}
        }
		if (i == 0)
			{ %>
			<tr>
				<td align="center" bgcolor="#FFFFFF" height="100" colspan="10">등록된 내용이 없습니다.</td>
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