<%
//**********************************************************
//  1. 제      목: 홈페이지 교육문의
//  2. 프로그램명 : HelperB.jsp
//  3. 개      요: 홈페이지 교육문의
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.22
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String tem_grcode        = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
    EduGroupBean helpbean = new EduGroupBean();
    box.put("p_grcode", tem_grcode);

    DataBox dbox = helpbean.SelectEduGroupData(box);
%>
                        <table width="166" border="0" cellpadding="0" cellspacing="0" background="/images/user/kocca/homepage/type2/a_bg.gif">
                          <tr> 
                            <td><img src="/images/user/kocca/homepage/type2/a_tit.gif"></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/user/kocca/homepage/type2/a_text.gif"></td>
                          </tr>
                          <tr>
                            <td height="5"></td>
                          </tr>
                          <tr> 
                            <td align="center"><strong>TEL :</strong> 11<%=dbox.getString("d_mastercomptel")%></td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/kocca/homepage/type2/a_bo.gif" width="166" height="16"></td>
                          </tr>
                          <tr> 
                            <td height="8" bgcolor="#FFFFFF"></td>
                          </tr>
                        </table>