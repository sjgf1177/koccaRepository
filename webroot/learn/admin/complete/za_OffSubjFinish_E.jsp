<%
/**
 * file name : za_OffSubjFinish_L.jsp
 * date      : 2004/01/29
 * programmer:
 * function  : 집합과정 개인별 수료처리
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>


<%
	response.setHeader("Content-Disposition", "inline; filename=OffStudentList.xls");   
	//response.setHeader("Content-Description", "JSP Generated Data");
	
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getStringDefault("s_grseq","ALL");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수
	String  ss_action    = box.getString("s_action");
	
    String  v_subj    = box.getString("p_subj");
    String  v_year    = box.getString("p_year");
    String  v_subjseq = box.getString("p_subjseq");
    
    String  v_subjnm		= box.getString("p_subjnm");
	
	

    DataBox dbox2 = (DataBox)request.getAttribute("SubjseqData");
    ArrayList list = (ArrayList)request.getAttribute("OffCompleteList");
    StoldData data = null;
    
    //버튼 제어에 필요한 변수들
    String v_isclosed   = dbox2.getString("isclosed");
    String v_iscpresult = dbox2.getString("iscpresult");
    String v_iscpflag   = dbox2.getString("iscpflag");
    String v_isoutsrc   = dbox2.getString("isoutsourcing");
    String v_isapproval = dbox2.getString("d_isapproval");
    String v_isonoff	= dbox2.getString("isonoff");
    String  v_subjseqgr		= dbox2.getString("subjseqgr");
    
    int    v_stoldcnt   = dbox2.getInt("d_stoldcnt");
    
    //out.println(" v_isclosed = " + v_isclosed );
    //out.println(" v_iscpresult = " + v_iscpresult);
    //out.println(" v_iscpflag = " + v_iscpflag);
    //out.println(" v_isoutsrc = " + v_isoutsrc);
    //out.println(" v_isapproval = " + v_isapproval);
    //out.println("auth = " + box.getSession("gadmin"));
    //out.println("v_isonoff="+v_isonoff);
    //out.println("v_stoldcnt="+v_stoldcnt);
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    
    

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td>
      <table border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" bgcolor="#FFFFFF">

            <!----------------- 집합과정 개인별 수료처리 시작 ----------------->
            <table cellspacing="1" cellpadding="5" class="open_table_out">
              <tr> 
                <td class="table_title">사번</td>
                <td class="table_title">차수</td>
                <td class="table_title">출석률점수</td>
                <td class="table_title">평가점수</td>
                <td class="table_title">리포트점수</td>
                <td class="table_title">기타점수</td>
              </tr>
            <%  for (int i=0; i<list.size(); i++) {
        		data  = (StoldData)list.get(i); 
        		String v_isgradated = data.getIsgraduated();
        	%>
                    
              <tr> 
                <td class="table_02_1" style='mso-number-format:"\@";'><%=data.getUserid()%></td>
				<td class="table_02_1"><%=v_subjseqgr%></td>
				<td class="table_02_1"><%=data.getTstep()%></td>
				<td class="table_02_1"><%=data.getFtest()%></td>
				<td class="table_02_1"><%=data.getReport()%></td>
				<td class="table_02_1"><%=data.getEtc1()%></td>
              </tr>
                    <%  } %>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
