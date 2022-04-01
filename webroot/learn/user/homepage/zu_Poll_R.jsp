<%
//**********************************************************
//  1. 제      목: Poll
//  2. 프로그램명 : zu_Poll_R.jsp
//  3. 개      요: Poll 결과 보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 01.13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    int    v_seq     = 0;            // 폴일련번호
    int    v_total   = 0;            // 응답수

    String v_title   = "";           // 타이틀
    String v_started = "";           // 시작일
    String v_ended   = "";           // 등록일


    PollData data1 = (PollData)request.getAttribute("selectPoll");

    v_seq     = data1.getSeq();
    v_total   = data1.getTotal();
    v_title   = data1.getTitle();
    v_started = data1.getStarted();
    v_started = FormatDate.getFormatDate(v_started,"yyyy/MM/dd");
    v_ended   = data1.getEnded();
    v_ended   = FormatDate.getFormatDate(v_ended,"yyyy/MM/dd");

    int v_maxcnt  = 0;
    int v_cnt     = 0;
    int v_percent = 0;
    int v_selnum  = 0;
    int v_graph   = 0;
	int v_graph_percent = 0;
    String v_seltxt = "";
	String v_class1 = "";
	String v_class2 = "";

    ArrayList list = (ArrayList)request.getAttribute("selectPollSel");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
  <table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="550">
    <tr>
        <td>
            <table cellpadding="0" cellspacing="1" bgcolor="#bebebe" class="table2" height="100%">
                <tr>
                    <td align="center" valign="top" bgcolor="#FFFFFF" class="body_color">
					<br>
<!-- 과정소개 table  -->				     
            <table width="504" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
              <tr> 
                <td valign="top"><img src="/images/user/common/st_cyberpoll.gif"> 
                </td>
              </tr>
              <tr> 
                <td height="20"></td>
              </tr>
              <tr valign="top"> 
                <td height="20"> <table width="500" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="25" valign="top"><img src="/images/user/common/poll_bl.gif"></td>
                      <td colspan="2"><%=v_title%></td>
                    </tr>
                    <tr > 
                      <td height="5" colspan="3" background="/images/user/common/search_line-1.gif"></td>
                    </tr>
                    <tr > 
                      <td height="20"></td>
                      <td width="379" height="20"></td>
                      <td width="96"><!--참여인원: 200명--></td>
                    </tr>
                  </table>
                  <!-- 그래프결과 table -->
                  <table width="500" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                    <tr  class="lcolor"> 
                      <td height="3" colspan="3" class="line_color_com"></td>
                    </tr>
                    <%

            // 최대값 구하기
            for(int i = 0; i < list.size(); i++) {
                 PollSelData data2  = (PollSelData)list.get(i);
                 v_cnt              = data2.getCnt();
                 if (v_maxcnt < v_cnt)  v_maxcnt = v_cnt;
            }

            for(int i = 0; i < list.size(); i++) {

                PollSelData data3   = (PollSelData)list.get(i);

                v_selnum = data3.getSelnum();
                v_seltxt = data3.getSeltxt();
                v_cnt    = data3.getCnt();

                v_percent = (int)((float)v_cnt /(float)v_total * 100);
				v_graph_percent = (int)((float)v_cnt /(float)v_maxcnt * 100);
                v_graph =  (i % 2) + 1;

				if ( ( i%2)==1){
					v_class1 = "poll_result_text1";
					v_class2 = "poll_result_text3";
				}else{
					v_class1 = "poll_result_text2";
					v_class2 = "poll_result_text4";
				}
%>
                    <tr>
                      <td width="137" class="tbl_gleft"><%=v_seltxt%> </td>
                      <td width="300" class="tbl_gleft">
                        <table width="100%" border="1" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#cccccc" >
                          <tr> 
                            <td>
                              <table width="<%=v_graph_percent+1%>%" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="7" background="/images/user/study/stu_graph0<%=(i%4)+1%>.gif"></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                      <td width="35" class="tbl_grc"><%=v_cnt%>명(<%=v_percent%>%)</td>
                    </tr>
<%}%>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="10"></td>
              </tr>
              <tr align="right"> 
                <td height="20"><a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif" border="0"></a></td>
              </tr>
              <tr> 
                <td height="10"></td>
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
