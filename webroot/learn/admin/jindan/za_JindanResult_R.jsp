<%
/**
 * file name : za_JindanResult_R.jsp
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
    if (box == null) box = RequestManager.getBox(request);

	DataBox dbox = null;
    ArrayList list = (ArrayList)request.getAttribute("JindanResultAdminDetail");
	String v_upperclass = (String)request.getAttribute("p_upperclass");
	String v_middleclass = (String)request.getAttribute("p_middleclass");
	String v_lowerclass = (String)request.getAttribute("p_lowerclass");
	String v_jindanDate = (String)request.getAttribute("p_jindanDate");
    
%>
<TITLE> :::: 사이버 게임 아카데미 :::: </TITLE>
<link rel="stylesheet" type="text/css" href="/css/user_style1.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
// 과정 내용보기
function whenSubjInfo(subj,tabnum){

    document.frm3.pp_subj.value     = subj;
    document.frm3.pp_tabnum.value   = tabnum;
    document.frm3.pp_process.value  = 'SubjectPreviewPage';
    document.frm3.pp_rprocess.value = 'SubjectList';
    document.frm3.p_process.value  = 'CourseIntroMove';
    document.frm3.action='/servlet/controller.jindan.JindanUserServlet';
    //document.frm3.target = "_self";
    document.frm3.submit();




}
//-->
</SCRIPT>

<body bgcolor="F6F6F6">
<table width="700" border="0" cellpadding="0" cellspacing="0" bgcolor="F6F6F6">
  <tr>
    <td><img src="/images/user/game/apply/pop_tit_self.gif" ></td>
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
                <td valign="bottom" >
<%		//타이틀이미지
		if(v_middleclass.equals("001")){ %> <img src="/images/user/game/apply/pop_st_1.gif">
<%		}else if(v_middleclass.equals("002")){ %> 	<img src="/images/user/game/apply/pop_st_2.gif">
<%		}else if(v_middleclass.equals("003")){%> 	<img src="/images/user/game/apply/pop_st_3.gif">
<%		}else if(v_middleclass.equals("004")){%> 	<img src="/images/user/game/apply/pop_st_4.gif">
<%		}else if(v_middleclass.equals("005")){%> 	<img src="/images/user/game/apply/pop_st_5.gif">
<%		}else if(v_middleclass.equals("006")){%> 	<img src="/images/user/game/apply/pop_st_6.gif"> <%}%>
				
				</td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              <tr> 
                <td style="padding-left:20px;"><strong><font color="#000000">[ 
<%				if(v_lowerclass.equals("001")){ %>초급
<%				}else if(v_lowerclass.equals("002")){ %>중급
<%				}else if(v_lowerclass.equals("002")){ %>고급 <%}%> 진단평가 결과 ]</font></strong></td>
              </tr>
              <tr>
                <td height="6" style="padding-left:40px;"></td>
              </tr>
            </table>
			<table width="620" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right"><font color="#000000"><strong>진단일</strong> : 
                  <%=v_jindanDate%></font></td>
              </tr>
              <tr>
                <td height="5"></td>
              </tr>
            </table>
            <!--  -->
            <table width="620" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
			<FORM METHOD=POST ACTION="" name="frm3">
				<INPUT TYPE="hidden" NAME="pp_subj">
				<INPUT TYPE="hidden" NAME="pp_year">
				<INPUT TYPE="hidden" NAME="pp_tabnum">
				<INPUT TYPE="hidden" NAME="pp_process">
				<INPUT TYPE="hidden" NAME="pp_rprocess">
				<INPUT TYPE="hidden" NAME="p_process">
			</FORM>
              <tr  class="linecolor_course"> 
                <td height="3" colspan="4" class="linecolor_app"></td>
              </tr>
              <tr> 
                <td width="138" class="tbl_gtit2">과목명<br></td>
                <td width="283" class="tbl_gtit">통계<br></td>
                <td width="99" class="tbl_gtit2">점수</td>
                <td width="77"  class="tbl_gtit">추천순위</td>
              </tr>
              <tr> 
                <td height="5" colspan="4" ></td>
              </tr>
<%
			String lDate="";
			int rate=0;
			String subjgubun="";

	for(int i=0; i < list.size(); i++){
		dbox = (DataBox)list.get(i);
			rate = ( 274 * dbox.getInt("d_score") ) / 100 ;
			lDate = FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyy/MM/dd");
			subjgubun = conf.getProperty("game.middleclass."+dbox.getString("d_middleclass"));
%>

              <tr> 
                <td class="tbl_bleft"><%=dbox.getString("d_subjnm")%><br></td>
                <td class="tbl_bleft"><table width="280" border="1" cellspacing="0" 
                                            cellpadding="2"   style="border-collapse:collapse;" bordercolor="#cccccc" bgcolor="#ffffff">
                    <tr> 
                      <td width="266"> <table width="270" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="265" height="12"><img src="/images/user/game/apply/pop_numline.gif"></td>
                          </tr>
                          <tr> 
                            <td height="12">
								<TABLE border="0" cellspacing="0" cellpadding="0" height="12">
									  <TR><TD width="<%=rate%>"  background="/images/user/game/apply/stu_graph03.gif" ></TD></TR>
								</TABLE>	  
												</td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
                <td class="tbl_grc"><%=dbox.getInt("d_score")%>점</td>
                <td class="tbl_grc"><%=i+1%></td>
              </tr>
<%
	}												
%>

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