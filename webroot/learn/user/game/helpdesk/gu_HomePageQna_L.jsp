<%
//**********************************************************
//  1. 제      목: HelpDesk > QNA
//  2. 프로그램명 : gu_HomePageQna_L.jsp
//  3. 개      요: QNA 리스트화면
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
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) box = RequestManager.getBox(request);
	int v_tabseq         = box.getInt("p_tabseq");
	int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
	int v_seq = 0,v_readcnt = 0,i = 0,v_cnt = 0;

	String v_inuserid    = "", v_position = "", v_indate = "", v_title = "", tLink = "",  v_types = "" ,v_name="";

	int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
	ArrayList list = (ArrayList)request.getAttribute("selectList");
	  
	String  v_searchtext     = box.getString("p_searchtext");   
	String  v_select         = box.getString("p_select");

	String  v_categorycd     = box.getString("p_categorycd");
	int v_pageno             = box.getInt("p_pageno"); 
	String s_userid          = box.getSession("userid");
	String s_usernm          = box.getSession("name");
	String s_grcode          = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

	String v_classname1      = "";
	String v_classname2      = "";
	box.put("leftmenu","02");                       // 메뉴 아이디 세팅
                  
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<script language="JavaScript" type="text/JavaScript">
<!--

//입력페이지로 이동
function insertPage() {
	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form1.p_process.value = "insertPage";  
	document.form1.p_pageno.value = "<%= v_pageno %>";    
	document.form1.submit();
}

//검색내용에 맞는 리스트 페이지로 이동
function selectList() {
	if (form1.p_searchtext.value == ""){
		alert("검색 할 내용을 입력해 주세요");
	}else{
	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form1.p_process.value = "";
	document.form1.submit();
	}
}

//상세화면 페이지로 이동
function select(seq, upfilecnt, userid, types) {
	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form1.p_process.value   = "selectView";
	document.form1.p_seq.value       = seq;
	document.form1.p_userid.value    = userid;
	document.form1.p_types.value     = types;
    document.form1.p_upfilecnt.value = upfilecnt;
	document.form1.submit();
}

//카테고리에 맞는 리스트 검색
function changeCategory(aa) {
	document.form1.p_categorycd.value=aa;
	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form1.p_process.value    = "";
	document.form1.submit();
}

//등록 페이지로 이동
function insertPage() {
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "insertPage";
    document.form1.submit();
}

//페이지 이동
function go(index) {
	document.form1.p_pageno.value = index;
	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form1.p_process.value = "";
	document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {
	 document.form1.p_pageno.value = pageNum;
	 document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	 document.form1.p_process.value = "";
	 document.form1.submit();
}
//-->
</script>


<form name = "form1" method = "post">
	  <input type = "hidden" name = "p_process"     value = "">
	  <input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
	  <input type = "hidden" name = "p_seq"         value = "">
	  <input type = "hidden" name = "p_userid"      value = "">
	  <input type = "hidden" name = "p_upfilecnt"   value = "">
	  <input type = "hidden" name = "p_types"       value = "<%=v_types%>">
	  <input type = "hidden" name = "p_categorycd"  value = "<%=v_categorycd%>">


<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/helpdesk/<%=tem_subimgpath%>/tit_qna.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > Help Desk > Q&amp;A</td>
  </tr>
  <tr> 
    <td height="20"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><img src="/images/user/game/helpdesk/text_qna.gif"></td>
  </tr>
  <tr> 
    <td height="15">&nbsp;</td>
  </tr>
</table>
<!-- 과정조회 -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="4" height="27" valign="top" background="/images/user/game/course/ba_bg.gif"><img src="/images/user/game/helpdesk/pdslist_left.gif" width="3" height="27"></td>
    <td width="712" align="center" valign="bottom" background="/images/user/game/helpdesk/pdslist_bg.gif">
	<table width="710" height="23" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="35" align="center"  ><img src="/images/user/game/helpdesk/text_num.gif" width="12" height="12"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/helpdesk/vline.gif"></td>
          <td align="center"  ><img src="/images/user/game/helpdesk/text_sujbj.gif" width="22" height="12"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/helpdesk/vline.gif"></td>
          <td width="129" align="center" ><img src="/images/user/game/helpdesk/text_writer_l.gif" width="39" height="12"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/helpdesk/vline.gif"></td>
          <td width="107" align="center"  ><img src="/images/user/game/helpdesk/text_date.gif" width="39" height="12"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/helpdesk/vline.gif" width="1" height="8"></td>
          <td width="35" align="center"  ><img src="/images/user/game/helpdesk/text_sea.gif" width="18" height="12"></td>
        </tr>
      </table> </td>
    <td width="4" align="right" valign="top" background="/images/user/game/course/ba_bg.gif"><img src="/images/user/game/helpdesk/pdslist_right.gif" width="3" height="27" ></td>
  </tr>
  <tr> 
    <td height="5" colspan="3"></td>
  </tr>
  <tr>
    <td height="1" colspan="3" class="linecolor_helpdesk02"></td>
  </tr>
</table>
<table width="720" border="2" cellspacing="0" 
    cellpadding="3"  style="border-collapse:collapse;" bordercolor="#A9B2CD" frame="hsides" rules="rows" >

<%if(list.size() != 0){%>
<% 
			for(i = 0; i < list.size(); i++) {
                   DataBox dbox = (DataBox)list.get(i);  
                   v_dispnum	= dbox.getInt("d_dispnum");
                   v_inuserid   = dbox.getString("d_inuserid");
                   v_name		= dbox.getString("d_name");
				   v_cnt		= dbox.getInt("d_cnt");
                   v_indate		= dbox.getString("d_indate");
                   v_title		= dbox.getString("d_title");
                   v_seq		= dbox.getInt("d_seq");
				   v_types      = dbox.getString("d_types");
                   v_readcnt	= dbox.getInt("d_readcnt");
                   v_upfilecnt  = dbox.getInt("d_filecnt");
                   v_totalpage	= dbox.getInt("d_totalpage");
                   v_rowcount	= dbox.getInt("d_rowcount");  
				   v_categorycd = dbox.getString("d_categorycd");

					// 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.

					if (!v_searchtext.equals("")&&v_select.equals("title")) {
						v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
					} else if(!v_searchtext.equals("")&&v_select.equals("name")){
						v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
					}

%>
  <tr> 
    <td width="29" class="tbl_grc"><%=v_dispnum%></td>
    <td class="tbl_bleft">
		 <%if (Integer.parseInt(v_types) > 0) {%>
								&nbsp;&nbsp;&nbsp;&nbsp;<IMG src="/images/user/support/ico_re.gif" border="0"> 
         <% } %>
			<a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_inuserid%>','<%=v_types%>')"><%=v_title%></a>

		 <%if (FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){%>
			<IMG src="/images/user/support/ico_new.gif" border="0">
		 <% } %>
    </td>
    <td width="121" class="tbl_grc"><%=v_name%>(<%=v_inuserid%>)</td>
    <td width="101" class="tbl_grc"><%=FormatDate.getFormatDate(v_indate, "yyyy-MM-dd")%></td>
    <td width="32" class="tbl_grc"><%=v_cnt%></td>
  </tr>
<%		}
	}else{%>

	<tr height="35">
		<td colspan="13" align=center valign=center>
			등록 된 질문이 없습니다.		
		</td>
	</tr>
<%}%>
 
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" >
  <tr> 
    <td height="2" class="lcolor" colspan="2"></td>
  </tr>
  <tr> 
    <td height="2" class="linecolor_helpdesk01" colspan="2"></td>
  </tr>
  <tr> 
    <td height="5"></td>
  </tr>
  <tr> 
    <td align="center" valign="bottom"> 
      <!-- page -->
	  <table width="540" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td>
			<%= PageUtil.printPageListGame(v_totalpage, v_pageno, 10) %>
			</td>
		  </tr>
		</table>
	</td>
	<td align="right" valign="bottom"> 
		<a href="javascript:insertPage()"><img src="/images/user/game/button/btn_register.gif" border="0">
	</td>
  </tr>
  <tr> 
    <td height="5" ></td>
  </tr>
  <tr> 
    <td height="2"class="linecolor_helpdesk01" colspan="2"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="100">
			<select name = "p_select">
				<option value="title" <% if (v_select.equals("title")) out.print("selected"); %>>제목</option>
				<option value="name" <% if (v_select.equals("name")) out.print("selected"); %>>작성자</option>
				<option value = "content" <% if (v_select.equals("content")) out.print("selected"); %>>내용</option>
			 </select>
		  </td>
          <td width="132"> <input name="p_searchtext" type="text" class="input" value="<%=v_searchtext %>"> 
          </td>
          <td width="100"><div align="center"><A HREF="javascript:selectList()"><img src="/images/user/game/button/btn_j.gif"></div></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td height="5"></td>
  </tr>
</table>  
</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
