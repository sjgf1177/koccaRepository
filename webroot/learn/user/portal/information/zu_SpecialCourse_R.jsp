<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    
    String  v_process    = box.getString("p_process");
    int     v_pageno     = box.getInt   ("p_pageno");
    String  v_userid     = box.getSession("userid");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    String  v_selClsfCd   = box.getString("p_selClsfCd");
    String  v_selDtlCd    = box.getString("p_selDtlCd");

    int v_seq      = box.getInt("p_seq");

    int v_cnt               = 0;
    String v_gubun          = "";
    String v_clsfcd         = "";
    String v_dtlcd          = "";
    String v_clsfnm         = "";
    String v_dtlnm          = "";
    String v_title          = "";
    String v_content        = "";
    String v_professor      = "";
    String v_pictureurl     = "";
    String v_useyn          = "";
    String v_indate         = "";
    String v_usernm         = "";


    DataBox dbox = (DataBox)request.getAttribute("selectView");

    if (dbox != null) {
    	v_gubun          = dbox.getString("d_gubun");  
        v_clsfcd         = dbox.getString("d_clsfcd");      
        v_dtlcd          = dbox.getString("d_dtlcd");       
        v_clsfnm         = dbox.getString("d_clsfnm");      
        v_dtlnm          = dbox.getString("d_dtlnm");
        v_title          = dbox.getString("d_title");       
        v_content        = dbox.getString("d_content");     
        v_professor      = dbox.getString("d_professor");   
        v_pictureurl     = dbox.getString("d_pictureurl"); 
        v_indate         = dbox.getString("d_indate");
        v_usernm         = dbox.getString("d_name");
        v_userid         = dbox.getString("d_userid");
        v_useyn          = dbox.getString("d_useyn");
        v_cnt            = dbox.getInt   ("d_cnt");  

     }
%>
<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">

    function listnotice(){
    	document.nform2.p_process.value = "selectList";
        document.nform2.action = "/servlet/controller.infomation.SpecialCourseHomePageServlet";
        document.nform2.submit();
    }

</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform2" method="post">
<input type = "hidden" name = "p_process" >
<input type = "hidden" name = "p_seq" value="<%=v_seq%>">
<input type = "hidden" name = "p_pageno" value = "<%=v_pageno %>">
<input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
<input type = "hidden" name = "p_search" value = "<%=v_search %>">
<input type = "hidden" name = "p_selClsfCd" value = "<%=v_selClsfCd %>">
<input type = "hidden" name = "p_selDtlCd" value = "<%=v_selDtlCd %>">

<%if( box.getSession("tem_grcode").equals("N000001")) { %>

			 <!--타이틀부분-->
	<table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/offline/stitle_05.gif" alt="교육자료실" /></td>
        <td class="h_road">Home &gt; 오프라인 과정  &gt; <strong>교육자료실</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>

	<table width="672" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="15"></td>
        </tr>
        <tr>
          <td height="15"><img src="/images/portal/homepage_renewal/offline/bar_01.jpg" /></td>
        </tr>
        <tr>
          <td height="15"></td>
        </tr>
        <tr>
          <td>
			<table width="672" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td class="tit_table">제목</td>
				<td class="tit_table"><%=v_title%></td>
				<td class="tit_table">조회<%=v_cnt %></td>
				<td class="tit_table_right"><%=v_cnt %></td>
				</tr>
			  <tr>
				<td height="1" colspan="4" bgcolor="CACACA"></td>
			  </tr>
			  <tr>
				<td height="4" colspan="4" bgcolor="F7F7F7"></td>
			  </tr>
			  <tr>
				<td height="4" colspan="4"></td>
			  </tr>
			  <tr>
				<td colspan="4"><%=v_content%></td>
			  </tr>
			  
            </table>
            
			  <%
            Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
            Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
            String i_boardStyle        = "information_view";                                // 보드 스타일
            %>
            <%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %> 
			  

				</td>
			</tr>
			<tr>
	          <td height="10"></td>
		    </tr>
			<tr>
				<td align="right"><a href="javascript:listnotice();" ><img src="/images/portal/homepage_renewal/offline/btn_list.jpg" alt"목록" /></a></td>
			</tr>
		</table>
		

<%}else{ %>

            <h2><img src="/images/portal/information/h2_tit3.gif" alt="교육자료실" class="fl_l" /><p class="category">Home > 정보광장 > <strong>교육자료실</strong></p></h2>

            <p><img src="/images/portal/information/edu_borimg.gif" alt="교육자료실은 교육분야별로 필요한 자료를 모아놓은 공간입니다." /></p>

            <table class="information_view mg_t30">
            <!--[if ie]><colgroup><col width="25px" /><col width="150px" /><col width="40px" /><col width="80px" /><col width="50px" /><col width="80px" /><col width="25px" /><col width="80px" /></colgroup><![endif]-->
            <colgroup><col width="45px" /><col width="150px" /><col width="60px" /><col width="80px" /><col width="70px" /><col width="80px" /><col width="45px" /><col width="80px" /></colgroup>
            <tr>
                <th>제목</th>
                <td colspan="7" class="tit"><%=v_title%></td>
            </tr>
            <tr class="bo">
                <th>분류</th>
                <td>방송영상</td>
                <th>작성자</th>
                <td><%=v_usernm%></td>
                <th>작성일자</th>
                <td class="num"><%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></td>
                <th>조회</th>
                <td class="num">1234</td>
            </tr>
            <tr>
                <td colspan="8" class="con"><%=v_content%></td>
            </tr>
            </table>

            <%
            Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
            Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
            String i_boardStyle        = "information_view";                                // 보드 스타일
            %>
            <%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %> 

            <p class="board_btn"><a href="javascript:listnotice();" class="board_violet"><span>목록</span></a></p>
            
            <%} %>

</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->