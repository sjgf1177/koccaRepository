<%
//**********************************************************
//  1. 제      목: 나의공부방 > 나의질문방 > 학습관련
//  2. 프로그램명 : gu_MyQnaStudy_L.jsp
//  3. 개      요: 나의질문방
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
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
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
	box.put("leftmenu","03"); 
    
    String  ss_userid     = box.getSession("userid");    
    String  v_indate      = "";
    String  v_types       = ""; 
    String  v_title       = "";
    String  v_searchtext  = box.getString("p_searchtext");   
    String  v_select      = box.getString("p_select");   
    String  v_edustart    = "";
    String  v_eduend      = "";
    
    String  v_subj      = "";
    String  v_subjseq   = "";
    String  v_year      = "";


     
    int v_dispnum = 0,  v_rowcount = 1, v_upfilecnt = 0;
    
    // 페이징관련
    int v_totalpage = 0;
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");   
        
    ArrayList blist1 = (ArrayList)request.getAttribute("MyQnaStudyListPage");  // 학습관련
      
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topMyStudy.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
function press_enter(e) { 
	if (e.keyCode =='13'){  selectList();  }
}

//검색내용에 맞는 리스트 페이지로 이동
function selectList() {
	if (form1.p_searchtext.value == ""){
		alert("검색 할 내용을 입력해 주세요");
	}else{
		var url =  "/servlet/controller.study.MyQnaServlet?p_process=MyQnaStudyListPage&p_pageno=1";
    	document.form1.action = url;
    	document.form1.submit();
	}
}

//상세화면 페이지로 이동
function select(seq,  userid, types, subj, year, subjseq) {
	document.form1.action = "/servlet/controller.study.MyQnaServlet";
	document.form1.p_process.value   = "MyQnaStudyViewPage";
	document.form1.p_seq.value       = seq;
	document.form1.p_userid.value    = userid;
	document.form1.p_types.value     = types;
	document.form1.p_subj.value      = subj;
	document.form1.p_year.value      = year;
	document.form1.p_subjseq.value   = subjseq;
	document.form1.submit();
}


//페이지 이동
function goPage(pageNum) {

	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.study.MyQnaServlet";
	document.form1.p_process.value = "MyQnaStudyListPage";
	document.form1.submit();

}

// Tab 선택시 이동
function showTab()
{
	var url = "/servlet/controller.study.MyQnaServlet?p_process=MyQnaSiteListPage&p_pageno=1";
	document.form1.action = url;
	document.form1.submit();
}
//-->
</script>


<form name="form1" method="post" action="/servlet/controller.study.MyQnaServlet">
	  <input type = "hidden" name = "p_process"		value = "">
	  <input type = "hidden" name = "p_seq"			value = "">
	  <input type = "hidden" name = "p_userid"		value = "">
	  <input type = "hidden" name = "p_upfilecnt"	value = "">
	  <input type = "hidden" name = "p_types"		value = "">
	  <input type = "hidden" name = "p_subj"		value = "">
	  <input type = "hidden" name = "p_subjseq"		value = "">
	  <input type = "hidden" name = "p_year"		value = "">
<!-- title -->
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/mystudy/<%=tem_subimgpath%>/tit_myqurestion.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 나의 공부방 > 나의 질문방</td>
  </tr>
  <tr> 
    <td height="20">
      <!-- page sub -->
    </td>
  </tr>
</table>
<!-- 과정조회 -->
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td width="101"><img src="/images/user/game/mystudy/tab_quest02_on.gif"></td>
	<td width="3"></td>
    <td width="101" onClick="showTab();" style="cursor:hand;" ><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image37','','/images/user/game/mystudy/tab_quest01_on.gif',1)" ><img src="/images/user/game/mystudy/tab_quest01.gif" name="Image37"></td>
    <td width="515"></td>
  </tr>
  <tr> 
    <td height="5" colspan="4"></td>
  </tr>
  <tr>
	<Td colspan="4"><img src="/images/user/game/mystudy/g_back.jpg"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td width="4" height="27" valign="top" background="/images/user/game/mystudy/ba_bg02.gif"><img src="/images/user/game/mystudy/ba_f.gif"></td>
    <td width="712" align="center" valign="bottom" background="/images/user/game/mystudy/ba_bg02.gif"> 
      <table width="710" height="23" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="35" align="center"  ><img src="/images/user/game/mystudy/text_t_num02.gif"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="112" align="center"  ><img src="/images/user/game/mystudy/text_t_course.gif"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="134" align="center" ><img src="/images/user/game/mystudy/text_t_edu_term.gif"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="295" align="center"  ><img src="/images/user/game/mystudy/text_t_subject.gif"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="82" align="center"  ><img src="/images/user/game/mystudy/text_t_openday.gif"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="46" align="center"  ><img src="/images/user/game/mystudy/text_add_file.gif"></td>
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
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td height="10" colspan="6"></td>
  </tr>
 <%
        for (int i=0; i<blist1.size(); i++) {
			
            DataBox dbox = (DataBox)blist1.get(i);  
            v_subj      = dbox.getString("d_subj"); 
            v_subjseq   = dbox.getString("d_subjseq"); 
            v_year      = dbox.getString("d_year"); 
            
            v_dispnum   = dbox.getInt("d_dispnum"); 
            v_indate    = dbox.getString("d_indate");
            v_types     = dbox.getString("d_kind");
            v_totalpage = dbox.getInt("d_totalpage");
            v_title     = dbox.getString("d_title");
            v_edustart  = dbox.getString("d_edustart");
            v_eduend    = dbox.getString("d_eduend");
            v_upfilecnt = dbox.getInt("d_upfilecnt");
                                            
			if (!v_searchtext.equals("")&&v_select.equals("title")) {
				v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
			}      
%>    
  <tr> 
    <td width="40" class="tbl_grc"><%=v_dispnum%></td>
    <td width="113" class="tbl_grc"><%=dbox.getString("d_scsubjnm")%></td>
    <td width="135" class="tbl_bleft"><%=FormatDate.getFormatDate(v_edustart, "yyyy/MM/dd")%>~<%=FormatDate.getFormatDate(v_eduend, "yyyy/MM/dd")%></td>
    <td width="296" class="tbl_bleft">
		<%if (Integer.parseInt(v_types) > 0) {%>
			&nbsp;&nbsp;&nbsp;&nbsp;<IMG src="/images/user/support/ico_re.gif" border="0"> 
		<% } %>
		<a href="javascript:select('<%=dbox.getInt("d_seq")%>', '<%=ss_userid%>','<%=v_types%>', '<%=v_subj%>', '<%=v_year%>', '<%=v_subjseq%>');"><%=v_title%></a> 
		<%if (FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){%>
			<IMG src="/images/user/support/ico_new.gif" border="0">
		<% } %>
	</td>
    <td width="83" class="tbl_grc"><%=FormatDate.getFormatDate(v_indate, "yyyy-MM-dd")%></td>
    <td width="50" class="tbl_grc">
		<% if(v_upfilecnt>0){ %>
			<img src="/images/user/support/ico_file.gif" width="13" height="12">
	    <%}else{%>-<%}%>
	</td>
  </tr>
  <tr> 
    <td height="1" colspan="6" class="linecolor_board4"></td>
  </tr>
		<%
				}
				if(blist1.size()==0){
		%>
			<tr height="25"> 
			  <td colspan="6" align=center valign=center>데이타가 없습니다.</td>
			</tr>
		<%
				}
		%>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
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
    <td align="center" valign="bottom"><table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
        <tr> 
          <td align="center">
		  <table width="630" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td>
			<%= PageUtil.printPageListGame(v_totalpage, v_pageno, 10) %>
			</td>
		  </tr>
		</table>
		  </td>
		</tr>
      </table> 
      <!-- page -->
    </td>
  </tr>
  <tr> 
    <td height="5" ></td>
  </tr>
  <tr> 
    <td height="2"class="linecolor_board3"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center" align="center">
  <tr> 
    <td height="10" colspan="5">&nbsp;</td>
  </tr>
  <tr> 
    <td width="238">&nbsp;</td>
    <td width="90"> <div align="center">
        <select name = "p_select" class = "input">
			<option value="title" <% if (v_select.equals("title")) out.print("selected"); %>>제목</option>
			<option value = "content" <% if (v_select.equals("content")) out.print("selected"); %>>내용</option>
		 </select>
      </div></td>
    <td width="105"> <div align="left">
        <input name = "p_searchtext" type="text" class="input2" size="15" maxlength="30"  value = "<%=v_searchtext %>" onkeypress="press_enter(event)">
      </div></td>
    <td width="48"><a href="javascript:selectList()"><img src="/images/user/game/button/btn_j.gif" width="48" height="21"></td>
    <td width="273">&nbsp;</td>
  </tr>
</table>
</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->