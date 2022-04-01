<%
/**
 * file name : gu_Specialroom_L.jsp
 * date      : 2005/12/16
 * programmer:	lyh
 * function  : 특강실 list 출력
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
	box.put("leftmenu","08"); 
	String v_userid = box.getSession("userid");
    int     v_pageno    =  box.getInt("p_pageno");	
    int     v_totalpage = 0;
    int     v_rowcount  = 0;

    ArrayList list = (ArrayList)request.getAttribute("SpecialroomList");

	DataBox dbox = null;

    
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topMystudy.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
// 상세내용
function detailGo(seq,grcode){
	document.frm3.seq.value = seq ;
	document.frm3.grcode.value = grcode ;
	document.frm3.p_process.value = "SpecialroomDetail";
	document.frm3.action="/servlet/controller.study.SpecialroomServlet";
	document.frm3.submit();

}

// 특강실 검색
function searchGo(){

	document.frm3.p_process.value = "SpecialroomSearch";
	document.frm3.action="/servlet/controller.study.SpecialroomServlet";
	document.frm3.submit();

}

// 페이지이동
function go(index) {
	document.frm3.target = "_self";
	 document.frm3.p_pageno.value = index;
	 document.frm3.action = '/servlet/controller.propose.ProposeStatusAdminServlet';
	 document.frm3.p_process.value = "ProposeMemberList";
	 document.frm3.submit();
}

// 페이지이동
function goPage(pageNum) {
	document.frm3.target = "_self";
	 document.frm3.p_pageno.value = pageNum;
	 document.frm3.action = '/servlet/controller.propose.ProposeStatusAdminServlet';
	 document.frm3.p_process.value = "ProposeMemberList";
	 document.frm3.submit();
}


//-->
</SCRIPT>
<!-- title -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/mystudy/<%=tem_subimgpath%>/tit_specialroom.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 나의 공부방 > 특강실</td>
  </tr>
  <tr> 
    <td height="20">
      <!-- page sub -->
    </td>
  </tr>
</table>
<table width="720" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="720"><img src="/images/user/game/mystudy/text_img_offline.gif" ></td>
  </tr>
  <tr> 
    <td height="12"></td>
  </tr>
</table>
<!-- 과정조회 -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
	<form name="frm3" method="post">
		<input type="hidden" name="p_process" >
		<input type="hidden" name="seq" >
		<input type="hidden" name="grcode" >
		<input type="hidden" name="p_pageno" value="<%=v_pageno%>">
  <tr> 
    <td><table width="320" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="60"><img src="/images/user/game/mystudy/open_courname.gif" width="56" height="15"></td>
          <td width="213"> <select name="select" class="font_apply" >
              <option value="all">::전체</option>
              <option value="title">제목</option>
              <option value="content">내용</option>
            </select>
            <input name="search" type="text" class="input"> 
          </td>
          <td width="52"><A HREF="javascript:searchGo();"><img src="/images/user/game/button/btn_j.gif"></A></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td height="5"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="4" height="27" valign="top" background="/images/user/game/mystudy/ba_bg02.gif"><img src="/images/user/game/mystudy/ba_f.gif"></td>
    <td width="712" align="center" valign="bottom" background="/images/user/game/mystudy/ba_bg02.gif"> 
      <table width="710" height="23" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="35" align="center"  ><img src="/images/user/game/mystudy/text_t_num02.gif"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="100" align="center" ><img src="/images/user/game/mystudy/text_t_m_fraction.gif" width="28" height="11"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td align="center"  ><img src="/images/user/game/mystudy/text_t_subject.gif"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="107" align="center"  ><img src="/images/user/game/mystudy/text_t_openday.gif"></td>
          <td width="1" align="center" valign="bottom"  ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="50" align="center"  ><img src="/images/user/game/mystudy/text_t_view.gif"></td>
        </tr>
      </table></td>
    <td width="4" align="right" valign="top" background="/images/user/game/mystudy/ba_bg02.gif"><img src="/images/user/game/mystudy/ba_tail.gif" ></td>
  </tr>
  <tr> 
    <td height="5" colspan="3"></td>
  </tr>
  <tr class="linecolor_board3"> 
    <td height="1" colspan="3"></td>
  </tr>
</table>

<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="10" colspan="6"></td>
  </tr>

  <%

if(  list.size()  != 0 ){		//내용이 있다면 

	String lDate = ""; 
	int	 diffTime= 0;	//new 버튼
	int totalCnt = list.size(); 

	for(int i=0; i < list.size() ; i++){

	dbox = (DataBox)list.get(i);

	lDate = FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyy/MM/dd");
	diffTime =  FormatDate.getMinDifference(FormatDate.getDate("yyyyMMdd"),FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyyMMdd"));

	v_totalpage     = dbox.getInt("d_totalpage");
	v_rowcount      = dbox.getInt("d_rowcount");
	System.out.println("v_totalpage:"+v_totalpage);
	System.out.println("v_rowcount:"+v_rowcount);

  %>

  <tr> 
    <td width="40" class="tbl_grc"><%=totalCnt--%></td>
    <td width="100" class="tbl_grc"><%=dbox.getString("d_codenm")%></td>
    <td class="tbl_gleft" align="center" ><A HREF="javascript:detailGo('<%=dbox.getString("d_seq")%>','<%=dbox.getString("d_grcode")%>')"      class="tbl_bleft"><%=dbox.getString("d_subjnm")%></A>
	<%
	if(diffTime < 3600 ){
	%>		 
		<img src="/images/user/game/common/ico_new.gif" width="22" height="11">
	<%
	}
	%>	
	</td>
    <td width="110" class="tbl_grc"><%=lDate%></td>
    <td width="55" class="tbl_grc"><%=dbox.getString("d_readcnt")%></td>
  </tr>
  <tr> 
    <td height="1" colspan="6" class="linecolor_board4"></td>
  </tr>

<%
	}

}else{	//내용이 없다면
%>

  <tr> 
    <td class="tbl_grc" colspan="5" height="40" valign="middle" align="center">검색된 내용이 없습니다.</td>
  </tr>

<%
}	
%>


</table>

<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="2" class="lcolor"></td>
  </tr>
  <tr> 
    <td height="2" class="linecolor_board3"></td>
  </tr>
  <tr> 
    <td height="5"></td>
  </tr>
  <tr> 
    <td align="center" valign="bottom"> 
      <!-- page -->

      <table width="300" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="center"><%=PageUtil.printPageListGame(v_totalpage, v_pageno, v_rowcount) %></td>
        </tr>
      </table>

	</td>
  </tr>
  <tr> 
    <td height="5" ></td>
  </tr>
  <tr> 
    <td height="2"class="linecolor_board3"></td>
  </tr>
  </form>
</table>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
