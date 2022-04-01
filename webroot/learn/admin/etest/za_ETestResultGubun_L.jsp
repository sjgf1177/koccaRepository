<%
//**********************************************************
//  1. 제      목: 나의강의실 - etest화면 뷰 & 이테스트결과분석 id별 결과 뷰
//  2. 프로그램명: zu_ETestPaperResult_L2.jsp
//  3. 개      요: etest 뷰
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 7. 20
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.etest.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

        ArrayList    blist = (ArrayList)request.getAttribute("UserPaperResult");

        ArrayList    blist2 = (ArrayList)request.getAttribute("UserPaperResult2");
		DataBox v_box = (DataBox)blist2.get(0);
		Vector v_answer = (Vector)blist2.get(1);
		Vector v_corrected = (Vector)blist2.get(2);

        Vector  v_averageV = (Vector)request.getAttribute("ETestResultAverage");    
		int v_personcnt = box.getInt("p_personcnt");
		String v_isadmin = box.getString("p_isadmin");
		
		String v_isopenanswer = v_box.getString("d_isopenanswer");
		String v_isopenexp = v_box.getString("d_isopenexp");
		
		double v_average =0.0;
		if("Y".equals(v_isadmin)){
			v_average = box.getDouble("p_average");
			v_isopenanswer = "Y";
			v_isopenexp ="Y";
		}else{
			v_average = Double.parseDouble((String)v_averageV.get(2));
			v_personcnt = Integer.parseInt((String)v_averageV.get(1));
		}	

		int v_position = v_personcnt - v_box.getInt("d_overman");
		double v_percent = (v_position / v_personcnt ) * 100 ;
		
		
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_study_style1.css" rel="stylesheet" type="text/css">
<script src="/script/cafe_select.js" language="JavaScript"></script>
<link href="/css/user_style_study.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="670" height="100%">
    <tr>
        <td>
            <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
                <tr>
                    <td align="center" valign="top" class="body_color">


<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
                <td>
				    <br>  
                  <!----------------- 타이틀 시작 ----------------->
                  <table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
                    <tr> 
                      <td><img src="/images/user/etest/result_title.gif"></td>
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
                      <td valign="top">
			<table width="630" border="0" cellpadding="0" cellspacing="0" background="/images/user/etest/test_box_bg.gif">
                          <tr>
                            <td><img src="/images/user/etest/test_box_top.gif" ></td>
                          </tr>
                          <tr>
                            <td align="center">
							<table width="600" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="87" rowspan="3"><img src="/images/user/etest/test_img.jpg"></td>
                                  <td width="73" height="33"><img src="/images/user/etest/text_name.gif"></td>
                                  <td width="226" class="tblfont_sindotit"><%=v_box.getString("d_etesttext")%></td>
                                  <td width="61"><img src="/images/user/etest/text_year.gif"></td>
                                  <td width="153" class="tblfont_gleft2"><%=v_box.getString("d_year")%></td>
                                </tr>
                                <tr> 
                                  <td height="5" colspan="4" background="/images/user/etest/stu_box_vline2.gif"></td>
                                </tr>
                                <tr> 
                                  <td><img src="/images/user/etest/text_time.gif"></td>
                                  <td colspan="3" class="tblfont_gleft2">
                                     <%=FormatDate.getFormatDate( v_box.getString("d_started"),"yyyy-MM-dd HH:mm:ss")%> ~ <%=FormatDate.getFormatDate( v_box.getString("d_ended"),"yyyy-MM-dd HH:mm:ss")%>
                                  </td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td><img src="/images/user/etest/test_box_bo.gif" ></td>
                          </tr>
                        </table></td>
        </tr>
        <tr> 
          <td height=8></td>
        </tr>
        <tr> 
          <td height=8><table width="100%" border="0" cellspacing="1" cellpadding="0" class=boardskin1_out>
              <tr> 
                <td width="17%" class=boardskin1_textb>문제 수</td>
                <td width="18%" class=boardskin1_textb>정답 수</td>
                <td width="20%" class=boardskin1_textb>득점</td>
                <td width="24%" class=boardskin1_textb>석차/응시자</td>
                <!--td width="19%" class=boardskin1_textb>백분위(%)</td-->                
                <td width="21%" class=boardskin1_textb>전체 평균</td>
              </tr>
              <tr> 
                <td class=boardskin1_textn><%=v_box.getInt("d_etestcnt")%></td>
                <td class=boardskin1_textn><%=v_box.getInt("d_answercnt")%></td>
                <td class=boardskin1_textn><%=v_box.getInt("d_score")%></td>
                <td class=boardskin1_textn><%=1+v_box.getInt("d_overman")%>/<%=v_personcnt%></td>
                <!--td class=boardskin1_textn><%=v_percent%></td-->
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
                <td colspan="2" class=boardskin1_textb_q><font color="blue">
                <% if("Y".equals(v_isopenexp)){ %><% if(((String)v_corrected.get(i)).equals("1")){ %>(O)<% }else{ %>(X)<% } %><% } %></font>&nbsp;<%="["+String.valueOf(i+1)+"]" + dbox.getString("d_etesttext")%>
                </td>
                <td width="55" class=boardskin1_textb>문제분류</td>                
                <td width="49" class=boardskin1_textb>난이도</td>
                <td width="41" class=boardskin1_textb>득점</td>
              </tr>
<%      if (dbox.getString("d_etesttype").equals(ETestBean.OBJECT_QUESTION)) {   // 단일선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
%>

              <tr> 
                <td  class=boardskin1_textn>
				<%if(dbox.getString("d_isanswer").equals("Y") && "Y".equals(v_isopenexp)){
				      if(dbox.getInt("d_selnum") < 10){
                %>
				<img src="/images/user/etest/Qnum0<%=dbox.getInt("d_selnum")%>_check.gif">정답					     
				<%
					  }else{       
				%>
				<img src="/images/user/etest/Qnum<%=dbox.getInt("d_selnum")%>_check.gif">		
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
				    if(!((String)v_answer.get(i)).equals("")){
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
                <td rowspan="<%=list.size()%>" class=skin_tb1><%=dbox.getString("d_gubunnm")%></td>
                <td rowspan="<%=list.size()%>" class=skin_tb1><%=dbox.getString("d_levelsnm")%></td>
                <td rowspan="<%=list.size()%>" class=skin_tb1>
                	
					<%
					//System.out.println("v_corrected.size()"+v_corrected.size()+"=="+list.size());
					if(v_corrected.size() >= blist.size() && "Y".equals(v_isopenexp)){
						if(((String)v_corrected.get(i)).equals("1")){
								
						%>
							<%=v_box.getInt("d_etestpoint")%>점
						<%}else{%>
							0점
						<%}
					}
					%>
				</td>
                <%}%> 
              </tr>
<%              } 
            }
        } else if (dbox.getString("d_etesttype").equals(ETestBean.SUBJECT_QUESTION)) {  // 주관식  %>
              <tr> 
                <td  class=boardskin1_textn>본인답</td>
                <td width="497" class=boardskin1_textn><font color='red'><%=(String)v_answer.get(i)%></font></td>
                <td rowspan="<%=list.size()+1%>" class=skin_tb1><%=dbox.getString("d_gubunnm")%></td>
                <td rowspan="<%=list.size()+1%>" class=skin_tb1><%=dbox.getString("d_levelsnm")%></td>
                <td rowspan="<%=list.size()+1%>" class=skin_tb1>
               <% if("Y".equals(v_isopenexp)){ %>
				<%if(((String)v_corrected.get(i)).equals("1")){%>
				<%=v_box.getInt("d_etestpoint")%>점
				<%}else{%>0점<%}%>
			   <% } %>
				</td>
              </tr>
<%
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
%>
              <tr> 
                <td  class=boardskin1_textn>정답<%=j+1%></td>
                <td width="497" class=boardskin1_textn>
                <% if("Y".equals(v_isopenexp)){ %>
                	<%=dbox.getString("d_seltext")%>
                 <% } %>
                </td>
				</td>
              </tr>
<%
				}
		   }
%>
<%      
	 }
%>

	<% if("Y".equals(v_isopenexp)){ %>
              <tr> 
                <td  class=skin_tb1   width="49">해설</td>
                <td colspan="4" class=skin_tb1 align="left">
				<%/*
				if(v_corrected.size() >= blist.size()){
					if(((String)v_corrected.get(i)).equals("1")){%>
					<font color="red">맞았습니다.</font><br>
					<%}else{%><font color="red">틀렸습니다.</font><br>
					<%}
				}else{ %>
					<font color="red">틀렸습니다.</font><br>
				<% } */%>
				<%=dbox.getString("d_exptext")%></td>
              </tr>
	<% } %>
            </table></td>
        </tr>
		<tr> 
          <td height=6></td>
        </tr>
<%
	//-------------for end
	}
%>
<%  if("Y".equals(v_isopenexp)){ %>
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
<% } %>        
        <tr> 
          <td class=dotline height=1></td>
        </tr>
		<tr> 
          <td height=15></td>
        </tr>
        <tr> 
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                <td><a href="javascript:window.close();"><img src="/images/user/study/close_butt.gif" border="0"></a></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
                    </td>
                </tr>
            </table>

        </td>
    </tr>
</table>
</body>
</html>

