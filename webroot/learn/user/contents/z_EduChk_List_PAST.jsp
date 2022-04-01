<%
//********************************************************************
//  1. 제      목: 마스터폼 -진도/목차화면 - Normal
//  2. 프로그램명 : z_EduChk_List.jsp
//  3. 개      요: 마스터폼 -진도/목차화면 - Normal
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 7
//  7. 수      정:
//********************************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    ArrayList blist1 = (ArrayList)request.getAttribute("pastlog");  
    DataBox dbox = null;
	if(blist1.size()>0) {
		dbox = (DataBox)blist1.get(0);

    }

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
</head>
<script language="javascript">
    function init() {
        this.moveTo(0,0);
        this.resizeTo(880,900);
    }

    function whenReport(pkey, userid) {
		var url='/servlet/controller.study.ProjectServlet?p_process=ProjectListOld&p_pkey='+pkey+'&p_userid='+userid;
		window.open(url,'REPORT','width=800,height=600');
	}
</script>
<body topmargin="0" leftmargin="0" onLoad='init();'>

<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="850" height="100%">
  <tr>
    <td>
      <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
        <tr>
          <td align="center" valign="top" class="body_color">
			<form name='form1' method='post'>
			<br>
						<!----------------- 타이틀 시작 ----------------->
			  <table border=0 cellspacing="0" cellpadding="0" class="open_table_out" align="center">
				<tr> 
				  <td><img src="/images/user/study/course_title.gif" alt="과정상세정보"></td>
				  <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
				  <td><img src="/images/user/study/gongi_tail.gif"></td>
				</tr>
			  </table> 

			</form>            

            <!----------------- 과정 시작 ----------------------------------->
            <table width="786" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="116" valign="top"><img src="/images/user/study/stu_leftimg.gif" width="116" height="79"></td>
                <td colspan="2">
				<table width="650" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="84"><img src="/images/user/study/stu_courname.gif" alt="과정명"width="81" height="14"></td>
                      <td width="566" class="tblfont_sindotit"><%=dbox.getString("d_subjnm")%> </td>
                    </tr>
                    <tr > 
                      <td background="/images/user/study/stu_box_vline2.gif" height="4" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/study/stu_gigan.gif" alt="교육기간" width="81" height="14"></td>
                      <td class="tblfont_gleft2"><%=FormatDate.getFormatDate(dbox.getString("d_edustart"),"yyyy/MM/dd")%> 
					  ~<%=FormatDate.getFormatDate(dbox.getString("d_eduend"),"yyyy/MM/dd")%> </td>
                    </tr>
                  </table></td>
              </tr>
            </table> 
            <!----------------- 과정 끝 ----------------->
           <!--subjnm,  edustart, eduend, processpoint, testpoint, finaltestpoint, score, isgraduated-->
						<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
                            <tr>
                                <td class="board_color_line"></td>
                            </tr>
                        </table>
                        <table cellspacing="1" cellpadding="3" class="open_board_table_out2" align="center">

                            <tr>
                                <td class="board_title_bg1" width="25%">진도</td>
                                <td class="board_title_bg3" width="25%">
								<%=dbox.getInt("d_processpoint")%>
								</td>
                                <td class="board_title_bg1" width="25%">최종평가</td>
                                <td class="board_title_bg3" width="25%">
								<%=dbox.getInt("d_finaltestpoint")%>점
								</td>
                            </tr>
                            <tr>
                                <td class="board_title_bg1" width="25%">Report</td>
                                <td class="board_title_bg3" width="25%">
								<a href="javascript:whenReport('<%=dbox.getString("d_pkey")%>' ,'<%=dbox.getString("d_userid")%>')"><%=dbox.getInt("d_reportpoint")%>점</a>
								</td>
                                <td class="board_title_bg1" width="25%">총취득점수</td>
                                <td class="board_title_bg3" width="25%">
								<%=dbox.getInt("d_score")%>점
								</td>
                            </tr>
                            <tr>
                                <td class="board_title_bg1" width="25%">수료</td>
                                <td class="board_title_bg3" width="25%">
								<b>[<%=dbox.getString("d_isgraduated")%>]</b> 
								</td>
                                <td class="board_title_bg1" width="25%"></td>
                                <td class="board_title_bg3" width="25%">
								
								</td>
                            </tr>
                        </table>
                    <!--------------------학습 진행 상황 종료----------------->
<% 
   if(blist1.size()<1){
%>
                        <table cellspacing="1" cellpadding="3" class="open_board_table_out2" align="center">

                            <tr>
                                <td class="board_title_bg3" width="25%" align=center>
								    데이타가 없습니다.
								</td>
                            </tr>
							</table>
	
<% } %>

            <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
              <tr>
                <td class="board_color_line"></td>
              </tr>
            </table>
 

            <!----------------- 닫기버튼 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td height="11"></td>
              </tr>
              <tr> 
                <td align="right">
                    <a href="javascript:window.close()"><img src="/images/user/study/close_butt.gif" alt="닫기" width="54" height="20" border="0"></a>
                </td>
              </tr>
            </table>
            <!----------------- 닫기버튼 끝 ---------------->
            <br>
			</td>
		  </tr>
		</table>
    </td>
  </tr>
</table>

</body>
</html>

