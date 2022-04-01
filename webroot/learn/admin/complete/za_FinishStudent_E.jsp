<%
/**
 * file name : za_FinishStudent_E.jsp
 * date      : 2005/05/23
 * programmer:
 * function  : 수료처리 수료자리스트 엑셀출력
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=FinishStudentList.xls");   
	response.setHeader("Content-Description", "JSP Generated Data");
	
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
    
    String  v_subj			= box.getString("p_subj");
    String  v_year			= box.getString("p_year");
    String  v_subjseq		= box.getString("p_subjseq");
    String  v_subjnm		= box.getString("p_subjnm");
    
	

    ArrayList list = (ArrayList)request.getAttribute("FinishStudentList");
    DataBox dbox2 = (DataBox)request.getAttribute("SubjseqData");
    
    
    
    //버튼 제어에 필요한 변수들
    String v_isclosed   = dbox2.getString("isclosed");
    String v_iscpresult = dbox2.getString("iscpresult");
    String v_iscpflag   = dbox2.getString("iscpflag");
    String v_isoutsrc   = dbox2.getString("isoutsourcing");
    String v_isapproval = dbox2.getString("d_isapproval");
    String v_isonoff	= dbox2.getString("isonoff");
    
    int    v_stoldcnt   = dbox2.getInt("d_stoldcnt");
    int    v_studentcnt   = dbox2.getInt("d_studentcnt");
    
    int    v_stoldycnt   = dbox2.getInt("d_stoldycnt");
    int    v_stoldncnt   = dbox2.getInt("d_stoldncnt");
    int    v_stoldn10cnt = dbox2.getInt("d_stoldn10cnt");//유계불참
    int    v_stoldn11cnt = dbox2.getInt("d_stoldn11cnt");//유계불참(해외근무)
    int    v_stoldn20cnt = dbox2.getInt("d_stoldn20cnt");//무단불참
    int    v_stoldn30cnt = dbox2.getInt("d_stoldn30cnt");//수료불참
    int    v_stoldn40cnt = dbox2.getInt("d_stoldn40cnt");//중도탈락
    int    v_stoldn50cnt = dbox2.getInt("d_stoldn50cnt");//퇴소
    int    v_stoldn04cnt = dbox2.getInt("d_stoldn04cnt");//학습불참
    int    v_stoldnotcnt   = dbox2.getInt("d_stoldnotcnt");
    
    String  v_subjseqgr	   = dbox2.getString("subjseqgr");
    
    //out.println(" v_isclosed = " + v_isclosed );
    //out.println(" v_iscpresult = " + v_iscpresult);
    //out.println(" v_iscpflag = " + v_iscpflag);
    //out.println(" v_isoutsrc = " + v_isoutsrc);
    //out.println(" v_isapproval = " + v_isapproval);
    //out.println("auth = " + box.getSession("gadmin"));
    //out.println("v_isonoff="+v_isonoff);
    //out.println("v_stoldcnt="+v_stoldcnt);
    //out.println("v_studentcnt="+v_studentcnt);
    
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
			<table border="1" cellspacing="1" cellpadding="5" class="table_out">
			  <tr> 
				<td rowspan="2" width="25" class="table_title">NO</td>
				<td rowspan="2" width="50" class="table_title">회사</td>
				<td rowspan="2" width="50" class="table_title">ID</td>
				<td rowspan="2" width="50" class="table_title">성명</td>
				<td rowspan="2" width="100" class="table_title">부서</td>
				<td rowspan="2" width="100" class="table_title">직위</td>
				<td colspan="<% if (v_isonoff.equals("OFF")) out.print(4); else out.print(7);%>" class="table_title" align="center">가 중 치</td>
				<td colspan="<% if (v_isonoff.equals("OFF")) out.print(4); else out.print(7);%>" class="table_title" align="center">취 득</td>
				<td rowspan="2" width="50" class="table_title">총점</td>
				<td rowspan="2" width="50" class="table_title">삼진<br>아웃</td>
				<td rowspan="2" width="50" class="table_title">수료</td>
				<td rowspan="2" width="50" class="table_title">미수료<br>사유</td>
			  </tr>
			  <tr> 
				<% if (v_isonoff.equals("OFF")) { %>
				<td width="50" class="table_title">출석률</td>
				<td width="50" class="table_title">평가</td>
				<td width="50" class="table_title">리포트</td>
				<td width="50" class="table_title">기타</td>
				<% } else  { %>
				<td width="50" class="table_title">진도율</td>
				<td width="50" class="table_title">중간평가</td>
				<td width="50" class="table_title">최종평가</td>
				<td width="50" class="table_title">형성평가</td>
				<td width="50" class="table_title">리포트</td>
				<td width="50" class="table_title">기타1</td>
				<td width="50" class="table_title">기타2</td>
				<% } %>
				<% if (v_isonoff.equals("OFF")) { %>
				<td width="50" class="table_title">출석률</td>
				<td width="50" class="table_title">평가</td>
				<td width="50" class="table_title">리포트</td>
				<td width="50" class="table_title">기타</td>
				<% } else  { %>
				<td width="50" class="table_title">진도율</td>
				<td width="50" class="table_title">중간평가</td>
				<td width="50" class="table_title">최종평가</td>
				<td width="50" class="table_title">형성평가</td>
				<td width="50" class="table_title">리포트</td>
				<td width="50" class="table_title">기타1</td>
				<td width="50" class="table_title">기타2</td>
				<% } %>
			  </tr>
			  <% if (list.size()==0) { %>
			  <!--tr> 
			  <td class="table_02_1" colspan="<% if (v_isonoff.equals("OFF")) out.print(14); else out.print(18);%>"> 수강생이 없습니다.</td>
			</tr-->
			  <%  } %>
			  <% 
			
				for (int i=0; i<list.size(); i++) {
					DataBox dbox = (DataBox)list.get(i); 
					
					String v_isgradated = dbox.getString("d_isgraduated");	
			  %>
			  <tr> 
				<td class="table_01"><%=(i+1)%></td>
            <td class="table_02_1"><%=dbox.getString("d_companynm")%></td>
            <td class="table_02_1"><%=dbox.getString("d_userid")%></td>
            <td class="table_02_1"><%=dbox.getString("d_name")%></td>
            <td class="table_02_1"><%=dbox.getString("d_compnm")%></td>
            <td class="table_02_1"><%=dbox.getString("d_jikwinm")%></td>				
				<!--  가중치 -->
				<% if (v_isonoff.equals("OFF")) { %>
				  <td bgcolor="#FFFFCC"><%=dbox.getDouble("d_avtstep")%></td>
				  <td bgcolor="#FFFFCC"><%=dbox.getDouble("d_avftest")%></td>
				  <td bgcolor="#FFFFCC"><%=dbox.getDouble("d_avreport")%></td>
				  <td bgcolor="#FFFFCC"><%=dbox.getDouble("d_avetc1")%></td>
				<% } else { %>
				<td bgcolor="#FFFFCC"><%=dbox.getDouble("d_avtstep")%></td>
				<td bgcolor="#FFFFCC"><%=dbox.getDouble("d_avmtest")%></td>
				<td bgcolor="#FFFFCC"><%=dbox.getDouble("d_avftest")%></td>
				<td bgcolor="#FFFFCC"><%=dbox.getDouble("d_avhtest")%></td>
				<td bgcolor="#FFFFCC"><%=dbox.getDouble("d_avreport")%></td>
				<td bgcolor="#FFFFCC"><%=dbox.getDouble("d_avetc1")%></td>
				<td bgcolor="#FFFFCC"><%=dbox.getDouble("d_avetc2")%></td>
				<% } %>
				<!--  취득 점수 -->
				<% if (v_isonoff.equals("OFF")) { %>
				  <td class="table_02_1"><%=dbox.getDouble("d_tstep")%></td>
				  <td class="table_02_1"><%=dbox.getDouble("d_ftest")%></td>
				  <td class="table_02_1"><%=dbox.getDouble("d_report")%></td>
				  <td class="table_02_1"><%=dbox.getDouble("d_etc1")%></td>

				<% } else { %>
				<td class="table_02_1"><%=dbox.getDouble("d_tstep")%></td>
				<td class="table_02_1"><%=dbox.getDouble("d_mtest")%></td>
				<td class="table_02_1"><%=dbox.getDouble("d_ftest")%></td>
				<td class="table_02_1"><%=dbox.getDouble("d_htest")%></td>
				<td class="table_02_1"><%=dbox.getDouble("d_report")%></td>
				<td class="table_02_1"><%=dbox.getDouble("d_etc1")%></td>
				<td class="table_02_1"><%=dbox.getDouble("d_etc2")%></td>
				<% } %>
				<td class="table_02_1">
				<% if (v_isclosed.equals("Y") || v_isoutsrc.equals("Y")) { %>
					<%=dbox.getDouble("d_score")%>
				<%   } else { out.print("-");} %>
				</td>
            <td  class="table_02_1"><%=dbox.getDouble("d_samtotal")%></td>   				
				<td class="table_02_1" >
				
					<% if (v_isclosed.equals("Y") || v_isoutsrc.equals("Y")) {
							if (v_isgradated.equals("Y"))  {

								if (v_isclosed.equals("Y"))
									out.print("수료");
								else
									out.print("미수료");//미처리

							} else { 

								if (v_isclosed.equals("Y"))
									out.print("미수료"); //미이수
								else
									out.print("미수료"); //미처리

							}	 
					   } else {out.print("미처리");} 
					 %>
				</td>
				<td class="table_02_1">
				<% //if (v_isclosed.equals("Y") || v_isoutsrc.equals("Y")) { %>
					<%//if (dbox.getString("d_notgraduetc").equals("")) {
						//if (!dbox.getString("d_notgraducddesc").equals(""))
						//	out.print(dbox.getString("d_notgraducddesc"));
					//} else {
					//	out.print(dbox.getString("d_notgraduetcdesc"));
					//} %> 
				<%   //} else {out.print("");} %>
	            <% if (v_isclosed.equals("Y") || v_isoutsrc.equals("Y")) {
	            		if (v_isgradated.equals("Y"))  {
		            			if (v_isoutsrc.equals("N") ) { // 위탁교육이 아닌경우 직무일때만.. %>
	            				&nbsp;
	            			<%	} else { 
	            					out.print("");
	            				}
	               		} else { 
	               			 if (v_isoutsrc.equals("N")) {   %>
	               			    <%=FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyy/MM/dd")%><br>
	               			    <%if(!dbox.getString("d_notgraduetcdesc").equals("")){%>[<%}%><%=dbox.getString("d_notgraduetcdesc")%><%if(!dbox.getString("d_notgraduetcdesc").equals("")){%>]<%}%>
	               		<%	} else { 
	               				out.print("");
	               			}
	               		}	 %>
	            <%   } else {out.print("");} %>				
				</td>
			  </tr>

			  <%  } 
			  
	 %>
			</table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
