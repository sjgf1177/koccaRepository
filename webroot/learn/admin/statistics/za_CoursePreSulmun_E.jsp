<%
//**********************************************************
//  1. 제      목: 과정운영 통계 - 차수별 사전설문 엑셀
//  2. 프로그램명 : za_CoursePreSulmun_E.jsp
//  3. 개      요: 학습현황
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.11
//  7. 수      정:
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%	
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=za_CoursePreSulmun_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String	ss_grcode			= box.getString("s_grcode");		//교육그룹
    String	ss_gyear			= box.getString("s_gyear");			//년도
    String	ss_grseq			= box.getString("s_grseq");			//교육차수
    String	ss_grseqnm			= box.getString("s_grseqnm");		//교육차수명
    String  ss_upperclass		= box.getString("s_upperclass");	//과정대분류
    String  ss_middleclass		= box.getString("s_middleclass");	//과정중분류
    String  ss_lowerclass		= box.getString("s_lowerclass");	//과정소분류
    String  ss_startdate		= box.getString("s_startdate");		//기간조회 시작
    String  ss_enddate			= box.getString("s_enddate");		//기간조회 끝
    String  ss_option1			= box.getString("s_option1");		//통계구분
    String  ss_option2			= box.getString("s_option2");		//통계별 구분2
    String  ss_option3			= box.getString("s_option3");		//통계별 구분3
    
    if (ss_enddate.equals("")) {
    	ss_startdate	= FormatDate.getDateAdd(FormatDate.getDate("yyyyMMdd"), "yyyy-MM-dd", "month", -1);
    	ss_enddate		= FormatDate.getFormatDate(FormatDate.getDate("yyyyMMdd"), "yyyy-MM-dd");
    }
    
    String  v_action	= box.getString("p_action");
	String  v_orderType	= box.getStringDefault("p_orderType"," asc"); //정렬순서
	
    int v_today = Integer.parseInt(FormatDate.getDate("yyyyMMdd"));  //오늘 날짜 일시 초기화
    int i_eduend   = 0;
    
    int     v_replycount  = box.getInt("p_replycount");
    int     v_studentcount= box.getInt("p_studentcount");
    double  v_replyrate   = 0;
    if (v_studentcount != 0) {
        v_replyrate = (double)Math.round((double)v_replycount/v_studentcount*100*100)/100;
    }

	DecimalFormat  df = new DecimalFormat("0.00");
    
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" height="30"><font size="3"><b>차수별 전체설문</b></font></td>
    </tr>
  <tr>
    <td align="center" valign="top">
      <!----------------- 통계조회 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5" border="1">		
<%  ArrayList    list = (ArrayList)request.getAttribute("selectList");
    SulmunQuestionExampleData data    = null;
    SulmunExampleData         subdata = null;

	int k = 0;
	int l = 0;

    for (int i=0; i < list.size(); i++) {
        data = (SulmunQuestionExampleData)list.get(i);
        if (data.getSultype().equals(SulmunSubjBean.OBJECT_QUESTION) || data.getSultype().equals(SulmunSubjBean.MULTI_QUESTION)) { %>
                    <tr>
                      <td>문제<%=i+1%></td>
                      <td><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                      <td>선택인원</td>
                      <td>점유율</td>
                    </tr>
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) { %>
                    <tr>
                      <td width="7%"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%"><%=subdata.getReplyrate()%>%</td>
                    </tr>
<%              }
            }
       } else if (data.getSultype().equals(SulmunSubjBean.SUBJECT_QUESTION)) {

%>
                    <tr>
                      <td>문제<%=i+1%></td>
                      <td><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                      <td>선택인원</td>
                      <td>점유율</td>
                    </tr>
<%        int m = 0;   //out.println(data.getSubjectAnswer().size());//out.println(v_replycount);
			for (int j=0; j < data.getSubjectAnswer().size(); j++) {
				if(((data.getSubjectAnswer().size() / v_replycount)*m + (k+1)) == (j+1)) {
			//	if((m + (k+1)) == (j+1)) {
					if(m < v_replycount) m++;
				//	if(m < data.getSubjectAnswer().size()) m++;
%>
					<tr>
                      <td width="7%"></td>
                      <td  colspan="4" style="padding-left=6" class="table_02_2"><%=(String)data.getSubjectAnswer().get(j)%></td>
                    </tr>

<%           }
		   }
           k++;
       } else if (data.getSultype().equals(SulmunSubjBean.COMPLEX_QUESTION)) {

%>
                    <tr>
                      <td>문제<%=i+1%></td>
                      <td><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                      <td>선택인원</td>
                      <td>점유율</td>
                    </tr>
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) { %>
                    <tr>
                      <td width="7%"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%"><%=subdata.getReplyrate()%>%</td>
                    </tr>
<%           }
           }
%>
<%       int n = 0;
			int etc = 0;
			for (int j=0; j < data.getComplexAnswer().size(); j++) {
				if(((data.getComplexAnswer().size() / v_replycount)*n + (l+1)) == (j+1)) {
					if(n < v_replycount) n++;
						if( !((String)data.getComplexAnswer().get(j)).equals("")) {
							etc++;
%>
					<tr>
                      <td width="7%"><%if(etc==1){%>기타<%}%></td>
                      <td style="padding-left=6" class="table_02_2"><%=(String)data.getComplexAnswer().get(j)%></td>
                      <td>선택인원</td>
                      <td>점유율</td>
                    </tr>
<%
					}
                }
		   }
           l++;
       } else if (data.getSultype().equals(SulmunSubjBean.FSCALE_QUESTION)) {

	          double d = 0;
			  int person = 0;
			  double v_point = 0;

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {

					d +=  (subdata.getReplycnt()) * subdata.getSelpoint();
					out.println("<!--subdata.getReplycnt() = " + subdata.getReplycnt() + "/-->");
					out.println("<!--subdata.getSelpoint() = " + subdata.getSelpoint() + "/-->");
					out.println("<!--d = " + d + "/-->");
					person += subdata.getReplycnt();
				}
            }

		v_point = d / person;
%>
<!-- here 4 -->
<!-- d=<%=d%> / person=<%=person%> / v_point=<%=v_point%> / data.getDistcodenm()=<%=data.getDistcodenm()%>-->
                    <tr>
                      <td>문제<%=i+1%></td>
                      <td><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                      <td>선택인원</td>
                      <td>점유율</td>
					  <!--td width="7%"><%if(v_point >=0){%><%=df.format(v_point)%>점<%}%></td-->
                    </tr>
<%

		   for (int j=1; j <= data.size(); j++) {

                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {
%>

					<tr>
                      <td width="7%"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%"><%=subdata.getReplyrate()%>%</td>
                    </tr>
<%           }
            }
       } else if (data.getSultype().equals(SulmunSubjBean.SSCALE_QUESTION)) {

	          double d = 0;
			  int person = 0;
			  double v_point = 0;

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {

					d +=  (subdata.getReplycnt()) * subdata.getSelpoint();
					person += subdata.getReplycnt();
				}
            }

		v_point = d / person;
%>
                    <tr>
                      <td>문제<%=i+1%></td>
                      <td><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                      <td>선택인원</td>
                      <td>점유율</td>
					  <!--td width="7%"><%if(v_point >=0){%><%=df.format(v_point)%>점<%}%></td-->
                    </tr>
<%

		   for (int j=1; j <= data.size(); j++) {

                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {
%>

					<tr>
                      <td width="7%"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%"><%=subdata.getReplyrate()%>%</td>
                    </tr>
<%           }
            }
	   }
    } %>
                  </table>
<%
			if (list.size() == 0 && "go".equals(v_action)) {
%>
      <table width="97%" cellspacing="1" cellpadding="5">
        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100">등록된 내용이 없습니다.</td>
        </tr>
      </table>
<%			}
%> 

    <!----------------- 통계조회 끝 ----------------->
      </td>
  </tr>
</table>
</body>
</html>