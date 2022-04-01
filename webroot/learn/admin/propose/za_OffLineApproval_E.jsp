<%
/**
 * file name : za_Approval_E.jsp
 * date      :
 * programmer:
 * function  : 수강신청 승인 리스트 조회화면
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>

<%	response.setHeader("Content-Disposition", "inline; filename=za_OffLineApproval_E.xls");
	  response.setHeader("Content-Description", "JSP Generated Data");
	  
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  v_process  = "OffLinelistPage";

    String  ss_grcode          = box.getString("s_grcode");           //교육그룹
    String  ss_gyear           = box.getString("s_gyear");            //년도
    String  ss_grseq           = box.getString("s_grseq");            //교육차수
    String  ss_grseqnm         = box.getString("s_grseqnm");          //교육차수명
    String  ss_mastercd        = box.getString("s_mastercd");         //과정&코스
    String  ss_upperclass      = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass     = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass      = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse      = box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq         = box.getString("s_subjseq");          //과정 차수
    String  ss_company         = box.getString("s_company");          //회사
    String  ss_selgubun        = box.getString("s_selgubun");
    String  ss_seltext         = box.getString("s_seltext");
    String  ss_seldept         = box.getString("s_seldept");
    String  ss_edustart        = box.getString("s_start");            //교육시작일
    String  ss_eduend          = box.getString("s_end");              //교육종료일
    String  v_isManagerPropose = box.getString("isManagerPropose");              //교육종료일

    int     v_biyong            = 0;           // 금액
    String  v_biyong_view       = "";          // 화면출력용 금액(,추가)
    int     v_total_biyong      = 0;           // 총금액
    String  v_total_biyong_view = "";           // 화면출력용 총금액(,추가)
    String  v_step      = box.getString("p_step");
    String  v_appstatus = box.getString("p_appstatus");

    String  v_gadmin    = box.getString("s_gadmin");

    String []   stat_k  = {"ALL","B","Y","N"};
    String []   stat_v  = {"전체","미처리","승인","반려"};


    String  v_finalString = ""; //최종승인 select box 태그

    int i=0;

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
	<td align="center" height="30"><font size="3"><b>OffLine 수강신청 리스트</b></font></td>
  </tr>
  <tr>
    <td align="center" valign="top">
        <!----------------- 과정기준조회 시작 ----------------->
        <table cellspacing="1" cellpadding="5" border="1">
          <tr>
            <td><b>분류</b></td>
            <td><b>강의명</b></td>
            <td><b>수강신청 기간</b></td>
            <td><b>강좌 일시</b></td>
            <td><b>시간</b></td>
            <td><b>회원분류</b></td>
            <td><b>ID</b></td>
            <td><b>성명</b></td>
            <td><b>EMAIL</b></td>
            <td><b>이동전화</b></td>
            <td><b>최종승인</b></td>
            <td><b>신청일시</b></td>
            <td><b>주민번호</b></td>
            <td><b>회사명</b></td>
            <td><b>최종학력</b></td>
            <td><b>종사분야</b></td>
          </tr>

<%  

		ArrayList  list = (ArrayList)request.getAttribute("OffLineApprovalList");

        
		int v_today = Integer.parseInt(FormatDate.getDate("yyyyMMddHH"));  //오늘 날짜 일시 초기화

		String v_gubunNm	= "";
		String v_subjnm		= "";
		String v_propstart	= "";
		String v_propend	= "";
		String v_dday		= "";
		String v_starttime	= "";
		String v_endtime	= "";
		String v_membergubun = "";
		String v_userid		= "";
		String v_superyn	= "";
		String v_status		= "";
		String v_name		= "";
		String v_email		= "";
		String v_handphone  = "";
		String v_ldate		= "";
		String v_seq		= "";
		
		String v_resno		= "";
		String v_comptext   = "";
		String v_jikupnm	= "";
		String v_degreenm   = "";

		for (i=0; i<list.size(); i++) 
		{
            DataBox dbox = (DataBox)list.get(i);

            v_seq			= dbox.getString("d_seq");
			v_gubunNm		= dbox.getString("d_gubunnm");
			v_subjnm		= dbox.getString("d_subjnm");
			v_propstart		= dbox.getString("d_propstart");
			v_propend		= dbox.getString("d_propend");
			v_dday			= dbox.getString("d_dday");
			v_starttime		= dbox.getString("d_starttime");
			v_endtime		= dbox.getString("d_endtime");
			v_membergubun	= dbox.getString("d_membergubun");
			v_userid		= dbox.getString("d_userid");
			v_superyn		= dbox.getString("d_superyn");
			v_status		= dbox.getString("d_status");
			v_name			= dbox.getString("d_name");
			v_email			= dbox.getString("d_email");
			v_handphone		= dbox.getString("d_handphone");
			v_ldate			= dbox.getString("d_ldate");

			//날짜 처리
			if(v_dday.equals("")){v_dday = "미정";}
			else{v_dday     = FormatDate.getFormatDate(v_dday,"yyyy/MM/dd");}
			if(v_propstart.equals("")){v_propstart = "미정";}
			else{v_propstart     = FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd");}
			if(v_propend.equals("")){v_propend = "미정";}
			else{v_propend     = FormatDate.getFormatDate(v_propend,"yyyy/MM/dd");}
			
			v_resno			= dbox.getString("d_resno");
			v_comptext  	= dbox.getString("d_comptext");
			v_jikupnm		= dbox.getString("d_jikupnm");
			v_degreenm		= dbox.getString("d_degreenm");
			
			if (!v_resno.equals("")) { 
			 if (v_resno.length()==13) {
			 	v_resno = v_resno.substring(0,6) + "-" + v_resno.substring(6,7) + "******";
			 } else {
			 	v_resno = "";
			 }
		    }
%>
            <tr align="left">
                <td ><%=v_gubunNm%></td>
                <td ><%=v_subjnm%></td>
                <td ><%=v_propstart%> ~ <br> <%=v_propend%></td>
                <td ><%=v_dday%></td>
                <td ><%=v_starttime%> 시 ~ <br> <%=v_starttime%>시</td>
<%
				if(v_membergubun.equals("C"))
				{
					v_membergubun = "기업";
				}
				else if(v_membergubun.equals("U"))
				{
					v_membergubun = "대학";
				}
				else
				{
					v_membergubun = "개인";
				}

				// 승인 구분
				if(v_status.equals("Y"))
				{
					v_status = "승인";
				}
				else if(v_status.equals("W"))
				{
					v_status = "미처리";
				}
				else
				{
					v_status = "삭제";
				}
%>
                <td ><%=v_membergubun%></td>
                <td style='mso-number-format:"\@";'><%=v_userid%></td>
				<td ><%=v_name%></td>
				<td ><%=v_email%></td>
				<td style='mso-number-format:"\@";'><%=v_handphone%></td>
                <td ><%=v_status %></td>
                <td ><%=FormatDate.getFormatDate(v_ldate,"yyyy-MM-dd HH:mm:ss")%></td>                
                <td style='mso-number-format:"\@";'><%=v_resno		%></td>
				<td ><%=v_comptext  %></td>
				<td ><%=v_degreenm  %></td>
				<td ><%=v_jikupnm	%></td>
            </tr>
<%
		}
%>
        </table>
      </td>
  </tr>
</table>
</body>
</html>