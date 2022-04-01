<%
/**
 * file name : gu_JindanUserList_L.jsp
 * date      : 2005/12/14
 * programmer:	lyh
 * function  : 진단테스트 list 출력
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

    ArrayList list = (ArrayList)request.getAttribute("JindanUserList");

	DataBox dbox = null;
    
%>
<link rel="stylesheet" type="text/css" href="/css/user_style1.css">
<body bgcolor="F6F6F6">
<table width="700" border="0" cellpadding="0" cellspacing="0" bgcolor="F6F6F6">
  <tr>
    <td><img src="/images/user/game/apply/pop_tit_jindan.gif"  ></td>
  </tr>
  <tr>
    <td><table width="700" border="0" cellspacing="0" cellpadding="0">
        <tr background="/images/user/game/apply/pop_box_topbg.gif">
          <td width="22" height="10" valign="top" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toph.gif" width="22" height="10"></td>
          <td width="657" height="10" background="/images/user/game/apply/pop_box_topbg.gif"></td>
          <td width="21" height="10" align="right" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toptail.gif" width="21" height="10"></td>
        </tr>
        <tr>
          <td valign="top" background="/images/user/game/apply/pop_box_lbg.gif">&nbsp;</td>
          <td align="center" valign="top" bgcolor="#FFFFFF">
		  <!-- 서브타이틀 -->
		    <table width="657" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td valign="bottom" ><img src="/images/user/game/apply/pop_st_1.gif"></td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              <tr> 
                <td style="padding-left:20px;"><strong><font color="#000000">[ 
                  초급 진단평가 결과 ]</font></strong></td>
              </tr>
              <tr>
                <td height="6" style="padding-left:40px;"></td>
              </tr>
            </table>
			<table width="620" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right"><font color="#000000"><strong>진단일</strong> : 
                  2005.12.21</font></td>
              </tr>
              <tr>
                <td height="5"></td>
              </tr>
            </table>
            <!-- 문제1 -->
            <table width="620" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
              <tr  class="linecolor_course"> 
                <td height="3" colspan="5" class="linecolor_app"></td>
              </tr>
              <tr> 
                <td width="138" class="tbl_gtit2">과목명<br></td>
                <td width="283" class="tbl_gtit">통계<br></td>
                <td width="49" class="tbl_gtit2">점수</td>
                <td width="27"  class="tbl_gtit">순위</td>
                <td width="79"  class="tbl_gtit">기능</td>
              </tr>
              <tr> 
                <td height="5" colspan="5" ></td>
              </tr>
              <tr> 
                <td class="tbl_bleft">프로그래밍(1)<br></td>
                <td class="tbl_bleft"><table width="280" border="1" cellspacing="0" 
                                            cellpadding="2"   style="border-collapse:collapse;" bordercolor="#cccccc" bgcolor="#ffffff">
                    <tr> 
                      <td width="266"> <table width="270" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="265" height="12"><img src="/images/user/game/apply/pop_numline.gif"></td>
                          </tr>
                          <tr> 
                            <td height="12" background="/images/user/game/apply/stu_graph03.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
                <td class="tbl_grc">20점</td>
                <td class="tbl_grc">1</td>
                <td class="tbl_grc"><img src="/images/user/game/button/b_viewdetail.gif"></td>
              </tr>
              <tr> 
                <td height="26" class="tbl_bleft">&nbsp;</td>
                <td class="tbl_bleft"><table width="280" border="1" cellspacing="0" 
                                            cellpadding="2"   style="border-collapse:collapse;" bordercolor="#cccccc" bgcolor="#ffffff">
                    <tr> 
                      <td width="266"> <table width="270" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="265" height="12"><img src="/images/user/game/apply/pop_numline.gif"></td>
                          </tr>
                          <tr> 
                            <td height="12" background="/images/user/game/apply/stu_graph03.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
                <td class="tbl_grc">&nbsp;</td>
                <td class="tbl_grc">&nbsp;</td>
                <td class="tbl_grc"><img src="/images/user/game/button/b_viewdetail.gif"></td>
              </tr>
            </table>
            <table width="620" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="10" colspan="2" align="right"></td>
              </tr>
              <tr> 
                <td colspan="2" align="right"><a href="javascript:self.close()"><img src="/images/user/game/button/btn_close.gif" border="0"></a></td>
              </tr>
            </table></td>
          <td background="/images/user/game/apply/pop_box_rbg.gif">&nbsp;</td>
        </tr>
        <tr>
          <td valign="bottom" background="/images/user/game/apply/pop_box_lbg.gif"><img src="/images/user/game/apply/pop_box_boh.gif" width="22" height="21"></td>
          <td background="/images/user/game/apply/pop_box_bobg.gif">&nbsp;</td>
          <td valign="bottom" background="/images/user/game/apply/pop_box_rbg.gif"><img src="/images/user/game/apply/pop_box_botail.gif" width="21" height="21"></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>