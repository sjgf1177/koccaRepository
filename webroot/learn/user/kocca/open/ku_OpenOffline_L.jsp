<%
/**
 * file name : ku_OpenOffline_L.jsp
 * date      : 2005/12/16
 * programmer:	lyh
 * function  : 관심과정 list 출력
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
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
	box.put("leftmenu","02");  

	int row = 7;
	String v_userid = box.getSession("userid");

	int v_totalpage = 0;

	int v_pageno         = box.getInt("p_pageno");
    if (v_pageno == 0) {
        v_pageno = 1;
    }
    int     v_rowcount  = 0;
    int     v_dispnum  = 0;

    ArrayList list = (ArrayList)request.getAttribute("OpenOfflineList");

	DataBox dbox = null;
    
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topOpenkocca.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
// 오프라인강좌 상세
function detailGo(seq, grcode){
	document.frm3.seq.value = seq ;
	document.frm3.grcode.value = grcode;
	document.frm3.p_process.value = "OpenOfflineDetail";
	document.frm3.action="/servlet/controller.homepage.KOpenOfflineServlet";
	document.frm3.submit();

}

// 오프라인강좌 검색
function searchGo(){

	document.frm3.p_process.value = "OpenOfflineSearch";
	document.frm3.action="/servlet/controller.homepage.KOpenOfflineServlet";
	document.frm3.submit();

}

    //페이지 이동
    function goPage(pageNum) {
        document.frm3.action="/servlet/controller.homepage.KOpenOfflineServlet";
        document.frm3.p_process.value= "OpenOfflineList";
        document.frm3.p_pageno.value= pageNum;
        document.frm3.submit();
    }


//-->
</SCRIPT>
<!-- title -->
<table width="680" border="0" cellpadding="0" cellspacing="0" bgcolor="#ffffff">
	<tr> 
	  <td width="20">&nbsp;</td>
	  <td valign="top"><table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td  class="location" > HOME > 열린마당 > 오프라인강좌</td>
		  </tr>
   </table>
   <table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td><img src="/images/user/kocca/openkocca/<%=tem_subimgpath%>/tit_offline.gif"></td>
		  </tr>
		  <tr> 
			<td height="20"></td>
		  </tr>
		</table>
		<table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td width="88" rowspan="3"><img src="/images/user/kocca/openkocca/text_offline.gif"></td>
		  </tr>
		  <tr> 
			<td height="12"></td>
		  </tr>
		  <tr > 
			<td height="1" colspan="2" background="/images/user/kocca/common/dot_bg_9.gif"></td>
		  </tr>
		  <tr > 
			<td height="7" colspan="2" ></td>
		  </tr>
</table>
<!--게시판목록 -->
	<table width="680" border="0" cellspacing="0" cellpadding="0">
	<form name="frm3" method="post">
		<input type="hidden" name="p_process" >
		<input type="hidden" name="seq" >
		<input type="hidden" name="grcode" >
		<input type="hidden" name="p_pageno" >
		  <tr> 
			<td height="7"></td>
		  </tr>
		  <tr> 
			<td><table border="0" align="right" cellpadding="0" cellspacing="0">
				<tr> 
				  <td>&nbsp;</td>
				  <td width="10"><select name="select" class="input2">
					  <option value="all">::전체</option>
					  <option value="title">제목</option>
					  <option value="content">내용</option>
					</select></td>
				  <td width="6">&nbsp;</td>
				  <td width="135"> <input name="search" type="text" class="input4"> 
				  </td>
				  <td width="4"><div align="right"></div></td>
				  <td width="37"><A HREF="javascript:searchGo();"><img src="/images/user/kocca/button/btn_search.gif"  border="0"></a></td>
				</tr>
			  </table></td>
		  </tr>
		  <tr> 
			<td height="8"></td>
		  </tr>
		</table>
		<!--게시판목록 -->
		<table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td width="5"><img src="/images/user/kocca/openkocca/board_first.gif" width="5" height="33"></td>
			<td width="669" class="tbl_bgcolor_openkocca">
			<table width="669" border="0" cellspacing="0" cellpadding="0">
				<tr> 
				  <td width="30" class="tbl_grc"><img src="/images/user/kocca/openkocca/text_no.gif"></td>
				  <td width="504" class="tbl_grc"><img src="/images/user/kocca/openkocca/text_subject02.gif"></td>
				  <td width="75" class="tbl_grc"><img src="/images/user/kocca/openkocca/text_course_date.gif"></td>
				  <td width="60" class="tbl_grc"><img src="/images/user/kocca/openkocca/text_search.gif"></td>
				</tr>
			  </table></td>
			<td width="6"><img src="/images/user/kocca/openkocca/board_tail.gif" width="6" height="33"></td>
		  </tr>
		  <tr> 
			<td height="3" colspan="3"></td>
		  </tr>
		  <tr> 
			<td height="1" colspan="3" class="tbl_bgcolor_openkocca"></td>
		  </tr>
		</table>
		
		<table width="680" border="0" cellspacing="0" cellpadding="0">
	  <tr> 
		<td height="10" colspan="4"></td>
	  </tr>

  <%
int totalCnt = list.size(); 
String lDate = "";
String v_dDay = "";
int	 diffTime= 0;	//new 버튼

if(list.size() != 0){		// 내용이 있다면


	for(int i=0; i < list.size() ; i++){

	dbox = (DataBox)list.get(i);

	lDate = FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyy/MM/dd");

	diffTime =  FormatDate.getMinDifference(FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyyMMdd"),FormatDate.getDate("yyyyMMdd"));
    v_totalpage = dbox.getInt("d_totalpage");
	v_rowcount      = dbox.getInt("d_rowcount");
v_dispnum = dbox.getInt("d_dispnum");
  %>

	  <tr> 
		<td width="35" class="tbl_grc"><%=v_dispnum%></td>
		<td width="500" class="tbl_gleft"><A HREF="javascript:detailGo('<%=dbox.getString("d_seq")%>','<%=dbox.getString("d_grcode")%>')"     class="tbl_bleft"><%=dbox.getString("d_subjnm")%></A>  
<%
if(diffTime < 3600 ){
%>		 
		  <img src="/images/user/kocca/common/ico_new.gif" width="22" height="11">
<%}%>	
</td>
		<td width="75" class="tbl_grc"><%=lDate%></td>
		<td width="70" class="tbl_grc"><%=dbox.getString("d_readcnt")%></td>
	  </tr>
	  <tr> 
		<td height="1" colspan="4" class="linecolor_main01"></td>
	  </tr>

<%
	}
}else{
%>
	  <tr> 
		<td class="tbl_grc" colspan="4" valign="middle" align="center"> 검색된 내용이 없습니다. </td>
	  </tr>
	  <tr> 
		<td height="1" colspan="4" class="linecolor_main01"></td>
	  </tr>
<%
}
%>
</table>

	<table width="680" border="0" cellspacing="0" cellpadding="0">
	  <tr> 
		<td class="tbl_bgcolor_notice" height="3"></td>
	  </tr>
	  <tr> 
		<td height="27" class="linecolor_notice">
		<!-- 페이지 -->
			<table width="540" border="0" align="center" cellpadding="0" cellspacing="0">
			  <tr> 
				<td>
					<%= PageUtil.printPageListKocca(v_totalpage, v_pageno, row) %>
				</td>
			  </tr>
			</table>  
		</td>
	  </tr>
	  <tr> 
		<td height="1" class="linecolor_main01"></td>
	  </tr>
  </form>
	</table>
<!--  -->
	</td>
  </tr>
  <tr>
	<td align="center" valign="top">&nbsp;</td>
  </tr>
</table>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->