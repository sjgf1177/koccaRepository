<%
//**********************************************************
//  1. 제      목: 과정 설문 리스트
//  2. 프로그램명: zu_StudySulmun_L.jsp
//  3. 개      요: 과정 설문 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.2.27
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String v_promotion  = (String)request.getAttribute("promotion");       //권장진도율
    String v_progress   = (String)request.getAttribute("progress");       // 자기진도율
%>
<html>
<head>
<title>::: 설문 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/hhi_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
function subjWright(){

    document.form1.p_process.value = 'SulmunUserPaperListPage';
    document.form1.action = "/servlet/controller.research.SulmunSubjUserServlet";
    document.form1.submit();

}
function contentsWright(){

    document.form2.p_process.value = 'SulmunUserPaperListPage';
    document.form2.action = "/servlet/controller.research.SulmunContentsUserServlet";
    document.form2.submit();
}

// 과정 설문응시
function contentsWright1(p_subjnm, p_subj, p_grcode, p_gyear, p_subjseq, p_sulpapernum, p_sulpapernm, p_sulstart, p_sulend, p_sulnums){
        document.form1.p_subj.value = "ALL";
        document.form1.s_subj.value = p_subj;   // tz_suleach 들어갈 과정코드
        document.form1.p_subjnm.value = p_subjnm;
        document.form1.p_grcode.value = "ALL";
        document.form1.s_grcode.value = p_grcode;    // tz_suleach 들어갈 교육그룹코드       
        document.form1.p_gyear.value = p_gyear;
        document.form1.p_subjseq.value = p_subjseq;
        document.form1.p_sulpapernum.value = p_sulpapernum;
        document.form1.p_sulpapernm.value = p_sulpapernm;
		document.form1.p_sulstart.value = p_sulstart;
        document.form1.p_sulend.value = p_sulend;
        document.form1.p_sulnums.value = p_sulnums;    // 문제번호    
        document.form1.p_listok.value = '0';

        //window.self.name = "winSelectView";
        //farwindow = window.open("", "openSubjChange", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 680, height = 538, top=0, left=0");
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.research.SulmunSubjUserServlet";
        document.form1.p_process.value = "SulmunUserPaperListPage";
        document.form1.submit();

        //farwindow.window.focus();
        //document.form1.target = window.self.name;
}

//-->
</SCRIPT>
</head>




<body topmargin="0" leftmargin="0">

<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="783" height="100%">
  <tr> 
    <td> 
      <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
        <tr> 
          <td align="center" valign="top" class="body_color"> 

 	 		        <br>
            		<!----------------- 타이틀 시작 ----------------->
  						<table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
    						<tr> 
      							<td><img src="/images/user/study/question_title.gif"></td>
      							<td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
      							<td><img src="/images/user/study/gongi_tail.gif"></td>
    						</tr>
  						</table>
            		<!----------------- 타이틀 끝 ---------------->
            <br>
               

            <!----------------- 게시판 리스트 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td class="board_color_line"></td>
              </tr>
            </table>

            <table cellspacing="0" cellpadding="0" class="open_board_table_out2">
<%
         ArrayList blist = (ArrayList)request.getAttribute("SulmunSubjUserList");
		 DataBox dbox01 = null;
		 if(blist.size() > 0){
		     dbox01 = (DataBox)blist.get(0);
		 }else{
		     dbox01 = new DataBox("resoponsebox");
		 }

		 int count = 0;
%>
 <form name="form1" method="post" action="/servlet/controller.research.SulmunSubjUserServlet">

        <input type="hidden" name="p_subjnm"    value="">            
        <input type="hidden" name="s_subj"    value="">    
        <input type="hidden" name="s_grcode"  value="">        
        <input type="hidden" name="p_sulstart"  value="">
        <input type="hidden" name="p_sulend"  value="">
        <input type="hidden" name="p_sulnums"  value="">        
        <input type="hidden" name="p_listok"  value="">      
         
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"    value="<%=dbox01.getString("d_subj")%>">
        <input type="hidden" name="p_grcode"  value="<%=dbox01.getString("d_grcode")%>">
        <input type="hidden" name="p_gyear"  value="<%=dbox01.getString("d_year")%>">
        <input type="hidden" name="p_subjseq"  value="<%=dbox01.getString("d_subjseq")%>">
        <input type="hidden" name="p_sulpapernum"  value="<%=dbox01.getString("d_sulpapernum")%>">
        <input type="hidden" name="p_sulpapernm"  value="<%=dbox01.getString("d_sulpapernm")%>">
        <input type="hidden" name="p_sulstart"  value="<%=dbox01.getString("d_sulstart")%>">
        <input type="hidden" name="p_sulend"  value="<%=dbox01.getString("d_sulend")%>">
     
</form>
			  <tr> 
                <td> 
                  <table cellspacing="1" cellpadding="3" class="table2">
                    <tr> 
                      <td class="board_title_bg1" width="5%">번호</td>
                      <td class="board_title_bg1" width="10%">설문유형</td>
					  					<td class="board_title_bg1"	width="55%">제 목</td>
					  					<td class="board_title_bg1" width="10%">상 태</td>
                      <td class="board_title_bg1" width="20%">현진도/응시진도</td>
                    </tr>
                    
<%

        for (int i=0; i<blist.size(); i++) {
            DataBox dbox = (DataBox)blist.get(i);   
    
				count ++; 
%>
                    <tr> 
				      				<td  class=board_text1><%=String.valueOf(count)%></td>
                      <td  class=board_text1>과정설문</td>
					  					<td  class=board_text3>
					  					<%if(box.getString("p_suldata").equals("0") && (Double.parseDouble(v_progress)>=Double.parseDouble(dbox.getString("d_progress")))){%>
					  					<!--<a href="javascript:subjWright()"><%=String.valueOf(dbox.getString("d_sulpapernm"))%></a>-->
					  
                                    <a href="javascript:contentsWright1('<%=dbox01.getString("d_subjnm")%>', '<%=dbox01.getString("d_subj")%>', '<%=dbox01.getString("d_grcode")%>', '<%=dbox01.getString("d_year")%>', '<%=dbox01.getString("d_subjseq")%>', '<%=dbox01.getInt("d_sulpapernum")%>', '<%=dbox01.getString("d_sulpapernm")%>', '', '','<%=dbox01.getString("d_sulnums")%>')">
                                    [클릭]&nbsp;<u><%=dbox01.getString("d_sulpapernm")%></u></a>  
                                    					  
					  <%}else{%>
					  <%=String.valueOf(dbox.getString("d_sulpapernm"))%>
					  <%}%>
					  </td>
					  <%if(box.getString("p_suldata").equals("0") && (Double.parseDouble(v_progress)>=Double.parseDouble(dbox.getString("d_progress")))){%>
                      <td  class=board_text1><font color="red">응시가능</font></td>
					  <%}else if(box.getString("p_suldata").equals("0") && (Double.parseDouble(v_progress)<Double.parseDouble(dbox.getString("d_progress")))){%>
                      <td  class=board_text1><font color="red">진도미달</font></td>
					  <%}else if(box.getString("p_suldata").equals("1")){%>
                      <td  class=board_text1><font color="red">응시완료</font></td>
					  <%}%>
				      <td  class=board_text1><%=v_progress%> / <%=dbox.getString("d_progress")%></td>
                    </tr>
<%    
        } 
%>
<%
         ArrayList blist1 = (ArrayList)request.getAttribute("SulmunContentsUserList");
		 DataBox dbox0 = null;
		 if(blist1.size() > 0){
		     dbox0 = (DataBox)blist1.get(0);
		 }else{
		     dbox0 = new DataBox("resoponsebox");
		 }
%>
 <form name="form2" method="post" action="/servlet/controller.research.SulmunContentsUserServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"    value="<%=dbox0.getString("d_subj")%>">
        <input type="hidden" name="s_subj"    value="<%=box.getString("p_subj")%>">
        <input type="hidden" name="p_grcode"  value="ALL">
        <input type="hidden" name="p_gyear"  value="<%=box.getString("p_year")%>">
        <input type="hidden" name="p_subjseq"  value="<%=box.getString("p_subjseq")%>">
        <input type="hidden" name="p_sulpapernum"  value="<%=dbox0.getString("d_sulpapernum")%>">
        <input type="hidden" name="p_sulpapernm"  value="<%=dbox0.getString("d_sulpapernm")%>">
        <input type="hidden" name="p_sulstart"  value="<%=dbox0.getString("d_sulstart")%>">
        <input type="hidden" name="p_sulend"  value="<%=dbox0.getString("d_sulend")%>">
</form>
<%
        for (int i=0; i<blist1.size(); i++) {
            DataBox dbox1 = (DataBox)blist1.get(i);   

			count ++; 
    
%>
                    <tr> 
				      <td  class=board_text1><%=String.valueOf(count)%></td>
                      <td  class=board_text1>콘텐츠평가</td>
					  <td  class=board_text3>
					  <%if(box.getString("p_contentsdata").equals("0")){%>
					  <a href="javascript:contentsWright()"><%=String.valueOf(dbox1.getString("d_sulpapernm"))%></a>
					  <%}else{%>
					  <%=String.valueOf(dbox1.getString("d_sulpapernm"))%>
					  <%}%>
					  </td>
					  <%if(box.getString("p_contentsdata").equals("0")){%>
                      <td  class=board_text1><font color="red">응시가능</font></td>
					  <%}else if(box.getString("p_contentsdata").equals("1")){%>
                      <td  class=board_text1><font color="red">응시완료</font></td>
					  <%}%>
				      <td  class=board_text1></td>
                    </tr>
<%    
        } 
%>
                  </table>

                </td>
              </tr>
            </table>
            <!----------------- 게시판 리스트 끝 ---------------->


            <!----------------- 작성, 닫기 버튼 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td height="11" colspan="3"></td>
              </tr>
              <tr> 
                <td align="center"></td>
                <td align="right" width="61">
                </td>
                <td align="right" width="65"><a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif"  border="0"></a></td>
              </tr>
            </table>
            <!----------------- 작성, 닫기 버튼 끝 ----------------->
            <br>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>


<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
