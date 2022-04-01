<%
/**
 * file name : zu_ExamUserPaper_I.jsp
 * date      : 2003/09/12
 * programmer: ??? 쓰는지 안쓰는지..일단 ox식 엄씀.(2005.8.20)
 * function  : 평가 응시
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

        ArrayList    blist = (ArrayList)request.getAttribute("UserPaperResult");

        ArrayList    blist2 = (ArrayList)request.getAttribute("UserPaperResult2");
		DataBox v_box = (DataBox)blist2.get(0);
		Vector v_answer = (Vector)blist2.get(1);
		Vector v_corrected = (Vector)blist2.get(2);

        Vector  v_averageV = (Vector)request.getAttribute("ExamResultAverage");    

//		double v_average = box.getDouble("p_average");
//		int v_personcnt = box.getInt("p_personcnt");
		double v_average = Double.parseDouble((String)v_averageV.get(2));
		int v_personcnt = Integer.parseInt((String)v_averageV.get(1));

		int v_position = v_personcnt - v_box.getInt("d_overman");
		double v_percent = (v_position / v_personcnt ) * 100 ;
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/style_myhome.css">
<script src="/script/cafe_select.js" language="JavaScript"></script>

</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin=0 leftmargin=0>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td >
      <!----------------- 타이틀 시작 ----------------->
      <table cellspacing="0" cellpadding="0" class="open_table_out">
        <tr> 
          <td><img src="/images/user/etest/result_title.gif" alt="E-test결과"></td>
          <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
          <td><img src="/images/user/study/gongi_tail.gif"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="center"> <table border="0" cellpadding="0" cellspacing="0" width="630">
        <tr> 
          <td><table width="100%" border="0" cellspacing="1" cellpadding="0" class=testbox_out>
              <tr> 
                <td class=testbox_con>[과정명] : <%=v_box.getString("d_subjnm")%> / [연도] : <%=v_box.getString("d_year")%> / [차수] : <%=v_box.getString("d_subjseq")%> / [일차] : <%=v_box.getString("d_lesson")%> / [평가타입] : <%=v_box.getString("d_examtypenm")%><br>
                    응시시간 : <%=FormatDate.getFormatDate( v_box.getString("d_started"),"yyyy-MM-dd HH:mm:ss")%> ~ <%=FormatDate.getFormatDate( v_box.getString("d_ended"),"yyyy-MM-dd HH:mm:ss")%></td>
              </tr>
            </table></td>
        </tr>
        <tr> 
          <td height=8></td>
        </tr>
		<tr> 
          <td height=8><table width="100%" border="0" cellspacing="1" cellpadding="0" class=boardskin1_out>
              <tr>
                <td width="14%" class=boardskin1_textb>문제 수</td>
                <td width="14%" class=boardskin1_textb>정답 수</td>
                <td width="16%" class=boardskin1_textb>득점</td>
                <td width="20%" class=boardskin1_textb>석차/응시자</td>
        <!--       <td width="19%" class=boardskin1_textb>백분위(%)</td>-->
                <td width="17%" class=boardskin1_textb>전체 평균</td>
              </tr>
              <tr>
                <td class=boardskin1_textn><%=v_box.getInt("d_examcnt")%></td>
                <td class=boardskin1_textn><%=v_box.getInt("d_answercnt")%></td>
                <td class=boardskin1_textn><%=v_box.getInt("d_score")%></td>
                <td class=boardskin1_textn><%=v_personcnt-(v_personcnt-v_box.getInt("d_overman"))+1%>/<%=v_personcnt%></td>
         <!--        <td class=boardskin1_textn><%//=v_percent%></td>-->
                <td class=boardskin1_textn><%=v_average%></td>
              </tr>
            </table></td>
        </tr>
		<tr> 
          <td height=8></td>
        </tr>
		<tr> 
          <td height=8></td>
        </tr>
<% 
	for (int i=0; i < blist.size(); i++) {
        ArrayList list = (ArrayList)blist.get(i);  
        DataBox dbox = (DataBox)list.get(0);  
%>
        <tr> 
          <td><table width="630" border="0" cellspacing="1" cellpadding="0" class=boardskin1_out>
              <tr> 
                <td colspan="2" class=boardskin1_textb_q><font color="blue"><b><%if(((String)v_corrected.get(i)).equals("1")){%>(O)<%}else{%>(X)<%}%></b></font>&nbsp;<%="["+String.valueOf(i+1)+"]" + dbox.getString("d_examtext")%></td>
                <td width="49" class=boardskin1_textb>난이도</td>
                <td width="41" class=boardskin1_textb>득점</td>
              </tr>
<%      if (dbox.getString("d_examtype").equals(ExamBean.OBJECT_QUESTION)) {   // 단일선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>

              <tr> 
                <td  class=boardskin1_textn>
				<%if(dbox.getString("d_isanswer").equals("Y")){
				      if(dbox.getInt("d_selnum") < 10){
                %>
				<img src="/images/user/etest/Qnum0<%=dbox.getInt("d_selnum")%>_check.gif"><font color="blue">정답</font>				     
				<%
					  }else{       
				%>
				<img src="/images/user/etest/Qnum<%=dbox.getInt("d_selnum")%>_check.gif"><font color="blue">정답</font>				
				<%}%>
				<%}else{
				      if(dbox.getInt("d_selnum") < 10){
                %>
				<img src="/images/user/etest/Qnum0<%=dbox.getInt("d_selnum")%>.gif">					     
				<%
					  }else{       
				%>
				<img src="/images/user/etest/Qnum<%=dbox.getInt("d_selnum")%>.gif">		
				<%}%>
				<%}%>
				</td>
                <td width="497" class=boardskin1_textn>
				<%
					int v_answersel = 0;
					if(StringManager.trim((String)v_answer.get(i)).length() == 0){
						v_answersel = 0;
					}
				    else if(!((String)v_answer.get(i)).equals("") && !((String)v_answer.get(i)).equals("-")){
				      v_answersel = Integer.parseInt((String)v_answer.get(i));
				    }
					if(dbox.getInt("d_selnum") == v_answersel) {
				%>
				<font color = 'red'><%=dbox.getString("d_seltext")%></font>
				<%}else{%>
				<%=dbox.getString("d_seltext")%>
				<%}%>
				</td>
                <%if(j==0){%>
                <td rowspan="<%=list.size()%>" class=skin_tb1><%=dbox.getString("d_levelsnm")%></td>
                <td rowspan="<%=list.size()%>" class=skin_tb1>
				<%if(((String)v_corrected.get(i)).equals("1")){%>
				<%=v_box.getInt("d_exampoint")%>점
				<%}else{%>0점<%}%>
				</td>
                <%}%> 
              </tr>
<%              } 
            }
        } else if (dbox.getString("d_examtype").equals(ExamBean.SUBJECT_QUESTION)) {  // 주관식  %>
              <tr> 
                <td  class=boardskin1_textn>본인답</td>
                <td width="497" class=boardskin1_textn><font color='red'><%=(String)v_answer.get(i)%></font></td>
                <td rowspan="<%=list.size()+1%>" class=skin_tb1><%=dbox.getString("d_levelsnm")%></td>
                <td rowspan="<%=list.size()+1%>" class=skin_tb1>
				<%if(((String)v_corrected.get(i)).equals("1")){%>
				<%=v_box.getInt("d_exampoint")%>점
				<%}else{%>0점<%}%>
				</td>
              </tr>
<%
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
%>
              <tr> 
                <td  class=boardskin1_textn><font color="blue">정답<%=j+1%></font></td>
                <td width="497" class=boardskin1_textn><%=dbox.getString("d_seltext")%></td>
				</td>
              </tr>
<%
				}
		   }
%>
<%             
            
        } else if (dbox.getString("d_examtype").equals(ExamBean.MULTI_QUESTION)) {  // 다답식
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
              <tr> 
                <td  class=boardskin1_textn></td>
                <td width="497" class=boardskin1_textn></td>
                <td rowspan="4" class=skin_tb1></td>
                <td rowspan="4" class=skin_tb1></td>
              </tr>

<%         }
		}
	 }
%>
              <tr> 
                <td  class=skin_tb1 width="49">해설</td>
                <td colspan="4" class=skin_tb2 align="left">&nbsp;&nbsp;
			<!--	<%//if(((String)v_corrected.get(i)).equals("1")){%>
				<font color="red">맞았습니다.</font><br>
				<%//}else{%><font color="red">틀렸습니다.</font><br><%//}%>-->
				<%=dbox.getString("d_exptext")%></td>
              </tr>
            </table></td>
        </tr>
		<tr> 
          <td height=6></td>
        </tr>
<%
	}
%>

		<tr> 
          <td height=15><table width="630" border="0" cellspacing="1" cellpadding="0" class=boardskin1_out>
              <tr> 
                <td width="532" class=boardskin1_textb_r>계</td>
                <td width="96" class=apoint_ok><%=v_box.getInt("d_score")%></td>
              </tr>
            </table></td>
        </tr>
		<tr> 
          <td height=15></td>
        </tr>
        <tr> 
          <td class=dotline height=1></td>
        </tr>
		<tr> 
          <td height=15></td>
        </tr>
        <tr> 
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                <td><a href="javascript:self.close()"><img src="/images/board/b_close.gif" border="0" ></a></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
