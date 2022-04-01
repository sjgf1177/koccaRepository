<%
//**********************************************************
//  1. 제      목: 고객센타 > 1:1상담
//  2. 프로그램명 : ku_1vs1.jsp
//  3. 개      요: 1:1상담
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_process    = box.getString("p_process");
    String  v_title      = "";
    String content      = "";
    String width        = "650";
    String height       = "200";
    String s_userid      = box.getSession("userid");
    String s_name        =  box.getSession("name");

    ArrayList list       = (ArrayList)request.getAttribute("selectList");

%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
function insert(){
    if(document.all.use_editor[0].checked) {
        form1.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    }else {
        form1.content.value = document.all.txtDetail.value;
    }
    if (blankCheck(document.all.p_title.value)) {
        alert("제목을 입력하세요!");
        document.all.p_title.focus();
        return;
    }
    if (realsize(document.all.p_title.value) > 100) {
        alert("제목은 한글기준 50자를 초과하지 못합니다.");
        document.all.p_title.focus();
        return;
    }

    document.form1.action = "/servlet/controller.homepage.KHome1vs1Servlet";
    document.form1.p_process.value = "insertData";
    document.form1.submit();
}

//내용입력을 취소하고 리스트로 이동
function cancel() {
     document.form1.action = "/servlet/controller.homepage.MainServlet";
     //document.form1.p_process.value = "selectList";
     document.form1.submit();
}


//-->
</script>



<form name = "form1" method = "post">
      <input type = "hidden" name = "p_process"   value = "">
      <input type = "hidden" name = "p_userid"    value = "">


                       <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td  class="location" > HOME > 고객센타 &gt; 1:1 상담</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/customer/<%=tem_subimgpath%>/tit_1vs1.gif"></td>
                          </tr>
                          <tr>
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/customer/text_1vs1.gif"></td>
                          </tr>
                          <tr>
                            <td height="15"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="9"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2"><img src="/images/user/kocca/customer/bar_1vs1_top.gif" width="680" height="7" ></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="2"></td>
                          </tr>
                          <tr>
                            <td width="75" class="textcolor_customer01">질문제목</td>
                            <td width="605" class="tbl_gleft01"><input name="p_title" type="text" class="input3" style="width:300"></td>
                          </tr>
                          <tr>
                            <td colspan="2" height="1"></td>
                          </tr>
                          <tr>
                            <td colspan="2" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr>
                            <td width="75" class="textcolor_customer01">작성자</td>
                            <td width="605" class="tbl_gleft02"><%=s_name%></td>
                          </tr>
                          <tr>
                            <td colspan="2" height="1"></td>
                          </tr>
                          <tr>
                            <td colspan="2" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr>
                            <td colspan="2" height="1"></td>
                          </tr>
                          <tr>
                            <td class="textcolor_customer01">질문 작성일</td>
                            <td class="tbl_gleft02"><%= FormatDate.getDate("yyyy-MM-dd") %></td>
                          </tr>
                          <tr>
                            <td colspan="2" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr>
                            <td colspan="2">
                            <!-- DHTML Editor  -->
                            <%@ include file="/include/DhtmlEditor.jsp" %>
                            <!-- DHTML Editor  -->
                            </td>
                          </tr>
                          <tr>
                            <td height="7" colspan="2"></td>
                          </tr>
                          <tr>
                            <td colspan="2" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr>
                            <td colspan="2">&nbsp;</td>
                          </tr>
                          <tr>
                            <td colspan="2" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr>
                            <td height="3" colspan="2" class="linecolor_customer02"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="20"><table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td height="2"></td>
                                </tr>
                                <tr>
                                  <td height="27" class="linecolor_notice"><table width="52" border="0" align="right" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td><a href="javascript:insert()"><img src="/images/user/kocca/button/btn_save.gif" border="0"></a>&nbsp;<a href="javascript:cancel()"><img src="/images/user/game/button/btn_list.gif" border="0"></a>
                                        </td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr>
                                  <td height="1" bgcolor="#cccccc"></td>
                                </tr>
                              </table>
                              <table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td height="20">&nbsp;</td>
                                </tr>
                              </table></td>
                          </tr>
                        </table>
    </form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->