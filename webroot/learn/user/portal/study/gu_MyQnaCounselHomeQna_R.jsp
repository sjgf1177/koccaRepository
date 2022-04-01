<%
//**********************************************************
//  1. 제      목: 나의상담내역 - 과정 QnA 상세보기
//  2. 프로그램명 : gu_MyQnaCounselSangdam_R.jsp
//  3. 개      요: 나의상담내역 
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.26
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.study.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	
	int    v_seq        = box.getInt("p_seq");
	int    v_pageno     = box.getInt("p_pageno");
	String v_searchtext = box.getString("p_searchtext");             
	String v_select     = box.getString("p_select");
	String v_selCategory = box.getString("p_selCategory");
	
    String  v_categorycd  = box.getStringDefault("p_categorycd","00");
	String	v_process	  = box.getString("p_process");
	String  v_subj        = box.getString("p_subj");
	String  v_year        = box.getString("p_year");
	String  v_subjseq     = box.getString("p_subjseq");
	String  v_type        = box.getString("pp_type");
	String  v_repkind     = "";
	int     v_repseq      = 0;
    int		v_pagesize	  = box.getInt("p_pagesize");

    String  v_action      = box.getString("p_action");
    int     v_upfilecnt   = box.getInt("p_upfilecnt");          //  서버에 저장되있는 파일수
	
	String s_userid     = box.getSession("userid");
	
	String v_title      = "";
	String v_contents   = "";
	String v_inuserid   = "";
	String v_indate     = "";
	String v_types      = "";
	String v_name       = "";
	String v_categorynm = "";
	String v_isopen     = "";
	String v_repstatus  = "";
	int    v_cnt        = 0;
	
	DataBox dbox        = (DataBox)request.getAttribute("selectQna");
	
	if(dbox != null) {
	    v_inuserid          = dbox.getString("d_inuserid");
	    v_name              = dbox.getString("d_name");
	    v_types             = dbox.getString("d_types");
	    v_indate            = dbox.getString("d_indate");
	    v_title             = dbox.getString("d_title");
	    v_seq               = dbox.getInt("d_seq");
	    v_cnt               = dbox.getInt("d_cnt");
	    v_contents          = dbox.getString("d_contents");
	    v_categorycd        = dbox.getString("d_categorycd");
	    v_categorynm        = dbox.getString("d_categorynm");
	    v_isopen            = dbox.getString("d_isopen");
	    v_repstatus         = dbox.getString("d_okyn1");
	}
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
// 목록으로
function listPage() {
    document.form1.target = "_self";
	document.form1.p_process.value='MyQnaCounselListPage';
	document.form1.action='/servlet/controller.study.MyQnaServlet';
	document.form1.submit();
}

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="form1" method="post" action="/servlet/controller.study.MyQnaServlet">
    <input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
    <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
    <input type = "hidden" name = "p_select"      value = "<%= v_select %>">
    <input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
    <input type = "hidden" name = "p_selCategory" value = "<%= v_selCategory %>">
    <input type = "hidden" name = "p_process"     value = "">
    <input type = "hidden" name = "p_userid"      value = "<%= v_inuserid %>">
    <input type = "hidden" name = "p_types"       value = "<%=v_types%>">
    <input type = "hidden" name = "pp_type"       value = "<%=v_type%>">
    
    	<%if( box.getSession("tem_grcode").equals("N000001")) { %>
	<table>   
      <tr>
      	<td>
      	 <!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/myclass/stitle_04.gif" alt="나의 상담내역" /></td>
        <td class="h_road">Home &gt; 나의 강의실  &gt; <strong>나의 상담내역</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/myclass/bar_03.jpg" width="672" height="104" /></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
    <!--타이틀부분//-->
    
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
            
            <tr>
              <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
              <td>
              	
              	<%
	if (v_type.equals("PQ")) {
%>
			<img src="/images/portal/homepage_renewal/myclass/stit_qna.gif" alt="Q&A문의내용" />
<%
	} else if (v_type.equals("BU") || v_type.equals("CU") || v_type.equals("OO") || v_type.equals("MM")) {
%>
			<img src="/images/portal/homepage_renewal/myclass/sstitle_03.gif" alt="1:1문의내용"/>
<%
	}
%>	
              </td>
        </tr>
          </table>
      <!--테이블부분-->
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="1" colspan="8" bgcolor="#7692CB"></td>
        </tr>
        <tr>
          <td class="tit_table" width="7%">제목</td>
          <td colspan="7" class="ct_tit_table_lright"><strong><%= v_title %></strong></td>
        </tr>
        
        <tr>
          <td class="tit_table">분류</td>
          <td class="ct_tit_table_left"><%=v_categorynm %>&nbsp;</td>
          <td class="tit_table">작성자</td>
          <td class="ct_tit_table"><%=v_name%></td>
          <td class="tit_table">작성일자</td>
          <td class="ct_tit_table"><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></td>
          <td class="tit_table">조회</td>
          <td class="ct_tit_table_right"><%=v_cnt%></td>
        </tr>
        <tr>
          <td colspan="8" class="ct_tit_table_lright"><%=v_contents%></td>
        </tr>
        <tr>
          <td height="1" colspan="8" bgcolor="CACACA"></td>
        </tr>
        <tr>
          <td height="4" colspan="8" bgcolor="F7F7F7"></td>
        </tr>
        		<!-- 파일첨부 시작 -->
            <%            
                Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                String i_boardStyle        = "myclass_view";                                // 보드 스타일
            %>
            <%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %>
            <!-- 파일첨부 종료-->
            
<%    if(v_repstatus.equals("3")){ %>
        <%@ include file="/learn/user/portal/helpdesk/zu_HomePageQna_A.jsp"%> 
<%    } %>
      </table>
      
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td class="h_road"><a href="javascript:listPage();"><img src="/images/portal/homepage_renewal/myclass/btn_list.jpg" alt="목록" width="46" height="25" /></a></td>
        </tr>
      </table></td>
    <td width="20">&nbsp;</td>
    
   
  </tr>
</table>
      	
      	<%}else{ %>
			<h2><img src="/images/portal/myclass/h2_tit7.gif" alt="나의상담내역" class="fl_l" /><p class="category">Home > 나의강의실 > <strong>나의상담내역</strong></p></h2>
 
			<p><img src="/images/portal/myclass/myconsult_txt.gif" alt="과정질문방은 수강생의 학습창에서 과정학습시 궁금한 점을 질문한 내용입니다. Q&A는 학습지원센터에서 질문한 내용 및 사이트 이용 전반에 관한 질문하는 공간입니다. 1:1문의는 학습지원센터에서 질문한 내용 및 건의, 버그/오류 등을 신고하는 공간입니다. " /></p>

<%
	if (v_type.equals("PQ")) {
%>
			<h3 class="mg_t30"><img src="/images/portal/myclass/stit_qna.gif" alt="Q&A문의내용" /></h3>
<%
	} else if (v_type.equals("BU") || v_type.equals("CU") || v_type.equals("OO") || v_type.equals("MM")) {
%>
			<h3 class=" mg_t30"><img src="/images/portal/myclass/stit_1_1.gif" alt="1:1문의내용" /></h3>
<%
	}
%>		
            <table class="myclass_view">
			<colgroup><col width="70px" /><col width="205px" /><col width="70px" /><col width="80px" /><col width="70px" /><col width="80px" /><col width="45px" /><col width="70px" /></colgroup>
			<tr>
                <th>제목</th>
                <td colspan="8" class="tit"><%= v_title %></td>
            </tr>
            <tr class="bo">
                <th>분류</th>
                <td><%=v_categorynm %></td>
                <th>작성자</th>
                <td><%=v_name%></td>
                <th>작성일자</th>
                <td class="num"><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></td>
                <th>조회</th>
                <td class="fs_s"><%=v_cnt%></td>
            </tr>
            <tr>
                <td colspan="8" class="con"><%=v_contents%></td>
            </tr>
            </table>
            
            <!-- 파일첨부 시작 -->
            <%
                Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                String i_boardStyle        = "myclass_view";                                // 보드 스타일
            %>
            <%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %>
            <!-- 파일첨부 종료-->
            
<%    if(v_repstatus.equals("3")){ %>
        <%@ include file="/learn/user/portal/helpdesk/zu_HomePageQna_A.jsp"%> 
<%    } %>
          
          <p class="board_btn">
          <a href="javascript:listPage()" class="board_blue"><span>목록</span></a>
          </p>
          
          <%} %>
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->
