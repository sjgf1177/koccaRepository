<%
//**********************************************************
//  1. 제      목: HomePageSuperiorityMember
//  2. 프로그램명 : gu_HomePageService_L.jsp
//  3. 개      요: 홈페이지 메인 우수회원 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 하경태 2005.12. 14
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    HomePageSuperiorityMemberBean selectData = new HomePageSuperiorityMemberBean();
    RequestBox box    = (RequestBox)request.getAttribute("requestbox");
	box.put("leftmenu","02");                       // 메뉴 아이디 세팅
    String  v_process = box.getString("p_process");
    
    int v_pageno         = box.getInt("p_pageno");

    if (v_pageno == 0) {
        v_pageno = 1;
    }
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");
	String  v_gubun		  = box.getString("p_gubun");

	int i = 0;
    int    v_seq     = 0;           // 일련번호
	int    v_cnt     = 0;           // 조회수	

    String v_title   = "";          // 제목
    String v_content = "";          // 내용
    String v_ldate   = "";          // 등록일
    String v_name    = "";          // 등록자
	String v_useyn   = "";			// 메인사용여부
	String v_savefile = "";			// 저장파일명
	String v_realfile = "";			// 실제파일명
	String v_select_seq = "";		// 메인 게시선택된 seq
	String v_month   = "";			// 개월
	String v_singleword   = "";		// 한마디


    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    ArrayList list1 = (ArrayList)request.getAttribute("selectHomePageSuperiorityMemberList");
   
%>

<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.homepage.HomePageSuperiorityMemberServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.homepage.HomePageSuperiorityMemberServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }

	//상세보기
    function view(seq) {
        document.form1.action = "/servlet/controller.homepage.HomePageSuperiorityMemberServlet";
        document.form1.p_seq.value = seq;
        document.form1.p_process.value = "select";
        document.form1.submit();
    }
	// 조회
    function search() {
        document.form1.action = "/servlet/controller.homepage.HomePageSuperiorityMemberServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

//-->
</SCRIPT>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topService.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<!-- title -->
<div align="center">
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="720" height="35" background="/images/user/game/service/<%=tem_subimgpath%>/tit_monthmember.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > Service> 이달의 우수회원</td>
  </tr>
  <tr> 
    <td height="20"></td>
  </tr>
</table>
<form name = "form1" method = "post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"  value = "<%=v_pageno %>"> 
    <input type = "hidden" name = "s_grcode"  value = "<%=box.getSession("tem_grcode") %>"> 
    <input type = "hidden" name = "p_gubun"  value = "<%=v_gubun %>"> 
    <input type = "hidden" name = "p_select_seq"  value = "<%=v_select_seq %>"> 
    <input type = "hidden" name = "p_seq"     value = "">
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="55" align="center"><img src="/images/user/game/service/text_mem_img.gif"></td>
  </tr>
</table>
<!----------------- Search 시작 ----------------->
<table width="720" border="0" cellpadding="2" cellspacing="0">
  <tr> 
	<td width="670" align="right"> 
	  <select name="p_search" >
		<option value='title'     <% if (v_search.equals("title")) out.println("selected"); %>>대상자</option>
		<option value='contents' <% if (v_search.equals("contents")) out.println("selected"); %>>내용</option>
	  </select>
	  <input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>">
	</td>
	<td align="left" style="padding-top:5px;"> 
		<a href='javascript:search()'><img src="/images/user/game/button/btn_j.gif" border="0"></a> 
	</td>
  </tr>
  <tr> 
	<td height="3" colspan="2"></td>
  </tr>  
  <tr> 
    <td colspan="2"><img src="/images/user/game/service/month_topline.jpg"></td>
  </tr>
</table>
<!----------------- Search 끝 ----------------->
<!-- 내용 시작 -->
<%

	for( i = 0; i < list1.size(); i++) 
	{
		
		DataBox dbox = (DataBox)list1.get(i);
		v_seq   = dbox.getInt("d_seq");
		v_cnt	= dbox.getInt("d_cnt");
		v_title = dbox.getString("d_title");
		v_ldate = dbox.getString("d_ldate");
		v_name  = dbox.getString("d_lname");
		v_useyn = dbox.getString("d_useyn");
		v_month = dbox.getString("d_lmonth");
		v_singleword = dbox.getString("d_singleword");

		
		v_savefile = dbox.getString("d_savefile");
		v_realfile = dbox.getString("d_realfile");
		v_dispnum   = dbox.getInt("d_dispnum");				
		v_totalpage = dbox.getInt("d_totalpage");

		v_ldate   = FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd");
                              
%>          
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
	<table width="720" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="15" colspan="4" align="center"></td>
        </tr>
        <tr> 
          <td width="175" align="center">
		  <table width="100" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="/images/user/game/service/month<%=v_month%>.gif"></td>
              </tr>
              <tr> 
                <td align="right">
				<table width="100" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="right">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="right"><%=v_title%> 님</td>
                    </tr>
                    <tr> 
                      <td height="5" background="/images/user/game/service/month_nameline.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
          <td width="86" valign="top">
			  <table width="78" border="1" cellpadding="0" cellspacing="1" bordercolor="#CCCCCC" bgcolor="#FFFFFF">
			    <tr> 
				  <td>
					<%if(v_savefile.equals("")){%>
						<img src="/images/user/game/service/photo_blank.gif" width="78" height="60"></td>
					<%}else{%>
						<img src="<%=conf.getProperty("url.upload")+"bulletin/"%><%=v_savefile%>" width="78" height="60"></td>
					<%}%>
			    </tr>
			  </table>
		  </td>
          <td width="10"><img src="/images/user/game/service/month_vline.gif"></td>
          <td width="449" valign="top"><a href="javascript:view('<%=v_seq%>')"><%=v_singleword%></a></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="8"></td>
  </tr>
  <tr>
    <td height="1" background="/images/user/game/service/dotline.gif"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<%	       
}
%>
<%
	if(i == 0 )
	{
	%>
		<table width="720" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="table_02_1" height="100" align="center"> 등록된 데이터가 없습니다. </td>
			</tr>
		</table>
<%
	}
%>
<!-- 내용 끝 -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="164" height="2" class="linecolor_service"></td>
	<td width="556" height="2" class="linecolor_ser2"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="2" class="lcolor"></td>
  </tr>
  <tr> 
    <td height="1" class="linecolor_board"></td>
  </tr>
  <tr> 
    <td height="5"></td>
  </tr>
  <tr> 
    <td align="center" valign="bottom"> 
<!----------------- 페이지 시작 ----------------->
	  <table width="540" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td>
			<%= PageUtil.printPageListGame(v_totalpage, v_pageno, 10) %>
			</td>
		  </tr>
		</table>
<!----------------- 페이지 끝 ----------------->
	  </td>
  </tr>
  <tr> 
    <td height="5" ></td>
  </tr>
  <tr> 
    <td height="2"class="linecolor_board"></td>
  </tr>
</table>
</div>
</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
