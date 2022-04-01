<%
//**********************************************************
//  1. 제      목: 개설예정과정
//  2. 프로그램명 : PreCourse.jsp
//  3. 개      요: 개설예정과정
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.15
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    String s_userid  = box.getSession("userid");
    int v_seq = 0;

    HomePagePreCourseBean beanPreCourse= new HomePagePreCourseBean();
    ArrayList mainList = beanPreCourse.selectDirectList(box); 

    String v_title  = "";
    String v_redate = "";
%>

<SCRIPT LANGUAGE="JavaScript">

    //상세보기
    function selectView(seq,userid) {
        mainList.action = "/servlet/controller.course.HomePagePreCourseServlet";
        mainList.p_process.value = "viewPage";
        mainList.p_seq.value         = seq;
        mainList.p_userid.value      = userid;
        mainList.submit();
    }



</SCRIPT>


<form name="mainList" method="post" >
  <input type="hidden" name="p_seq"        value="">
  <input type="hidden" name="p_process"    value="">
  <input type="hidden" name="p_userid"     value="">


        <table cellpadding="0" cellspacing="0" width="256" style="table-layout:fixed;">
            <col width="143"><col width="113">
            <tr height="28"><td colspan="2"><img src="/images/user/game/homepage/type1/tit_approve.gif"></td></tr>
            <tr height="6"><td colspan="2"></td></tr>
<%  
    for(int i = 0; i < mainList.size(); i++) {
          DataBox dbox = (DataBox)mainList.get(i);
          v_title   = dbox.getString("d_title");
          v_seq     = dbox.getInt("d_seq");
%>
    <%if((i%2)==0){%>
            <tr bgcolor="#f5f5f5" height="21">
            <td style="padding-left:5px;"><a href="javascript:selectView('<%=v_seq%>','<%=s_userid%>')"><%=v_title%></td>
            <td align="center" style="color:#333333;font-size:11px;">| <%=FormatDate.getFormatDate(dbox.getString("d_redate"),"yyyy-MM-dd")%></td>
            </tr>
    <%}else if((i%2)==1){%>
            <tr height="23">
            <td style="padding-left:5px;"><a href="javascript:selectView('<%=v_seq%>','<%=s_userid%>')"><%=v_title%></td>
            <td align="center" style="color:#333333;font-size:11px;">| <%=FormatDate.getFormatDate(dbox.getString("d_redate"),"yyyy-MM-dd")%></td>
            </tr>
<%    }else{}%>
<%}%>
            </table>
    </form>

