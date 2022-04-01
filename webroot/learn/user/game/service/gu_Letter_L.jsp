<%
//**********************************************************
//  1. 제      목: 서비스 > 공지사항 게시판
//  2. 프로그램명 : gu_Letter_L.jsp
//  3. 개      요: 서비스 > 공지사항 게시판
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	box.put("leftmenu","00");                       // 메뉴 아이디 세팅

	ArrayList list1 = null;
	ArrayList list2 = null;

    int		v_tabseq   = box.getInt("p_tabseq");
    int		v_seq      = box.getInt("p_seq");     
    String  v_process  = box.getString("p_process");

	// 검색
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_select");

    int    v_cnt       = 0;            // 조회수
    String v_gubun     = "";           // 전체구분
	String v_startdate = "";           // 팝업공지시작일
    String v_enddate   = "";           // 팝업공지종료일
    String v_adtitle   = "";           // 제목
    String v_adcontent = "";           // 내용
    String v_addate    = "";           // 등록일
    String v_adname    = "";           // 등록자
	String v_luserid   = "";
    String v_gubun_view = "";
    String v_loginyn    = "";			// 로그인유무
    String v_useyn   	= "";			// 사용유무
    String v_grcodecd   = "";           // 대상 교육그룹
	int v_popwidth	 = 0;               // 팝업가로
    int v_popheight  = 0;               // 팝업세로
    int v_popxpos    = 0;               // 팝업x위치
    int v_popypos    = 0;               // 팝업y위치
    
    String v_popup   = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe= "";               // 프레임사용
    
    String v_upfile    ="";
    //String v_realfile  ="";
    
    Vector v_realfileVector = null;
    Vector v_savefileVector = null;
    Vector v_fileseqVector  = null;

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
	int v_rowcount	= 0;
    int v_upfilecnt = 0;
    int v_pageno    = box.getInt("p_pageno");

    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");

    list2 = (ArrayList)request.getAttribute("selectLetterList");
   
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
    //리스트 조회
    function select() {
        document.nform1.action = "/servlet/controller.homepage.HomeLetterServlet";
        document.nform1.p_process.value = "List";
        document.nform1.submit();
    }

	//상세보기
    function selectView(seq) {

        document.nform1.action = "/servlet/controller.homepage.HomeLetterServlet";
		document.nform1.p_process.value = "selectView";
		document.nform1.p_seq.value= seq;
        document.nform1.submit();
    }

//-->
</SCRIPT>

<form name="nform1" method="post">
	<input type = "hidden" name = "p_process">
	<input type = "hidden" name = "p_seq"	 >

<!-- title -->
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/service/<%=tem_subimgpath%>/tit_letter.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 서비스 > 뉴스레터</td>
  </tr>
  <tr> 
    <td height="20">
      <!-- page sub -->
    </td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td><table border="0" align="right" cellpadding="0" cellspacing="0" align="center">
        <tr> 
          <td width="62">
			<select name="p_select" class="input">
			  <option value="adtitle"   <%if("adtitle".equals(v_search)) out.print(" selected");%>>::제목</option>
              <option value="adcontent" <%if("adcontent".equals(v_search)) out.print(" selected");%>>내용</option>
              <option value="adname"	<%if("ALL".equals(v_search)) out.print(" selected");%>>작성자</option>
            </select></td>
          <td width="135"> 
            <input name="p_searchtext" type="text" class="input"> 
          </td>
          <td width="48"><a href="javascript:select()"><img src="/images/user/game/button/btn_seatch.gif" width="48" height="21"></td>
        </tr>
      </table>
      <div align="right"></div></td>
  </tr>
  <tr> 
    <td height="5"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td width="4" height="27" valign="top" background="/images/user/game/mystudy/ba_bg02.gif"><img src="/images/user/game/service/ba_f.gif" width="3" height="27"></td>
    <td width="712" align="center" valign="bottom" background="/images/user/game/service/ba_bg.gif"> 
      <table width="710" height="23" border="0" cellpadding="0" cellspacing="0" align="center">
        <tr> 
          <td width="40" align="center"  ><img src="/images/user/game/service/text_num.gif" width="12" height="10"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/service/vline.gif" width="1" height="8"></td>
          <td width="358" align="center"  ><img src="/images/user/game/service/text_subject.gif" width="19" height="11"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/service/vline.gif" width="1" height="8"></td>
          <td width="92" align="center" ><img src="/images/user/game/service/text_wirter.gif" width="30" height="11"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/service/vline.gif" width="1" height="8"></td>
          <td width="64" align="center"  ><img src="/images/user/game/service/text_search.gif" width="18" height="11"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/service/vline.gif" width="1" height="8"></td>
          <td width="65" align="center"  ><img src="/images/user/game/service/text_add.gif" width="19" height="11"></td>
          <td width="1" align="center" valign="bottom"  ><img src="/images/user/game/service/vline.gif" width="1" height="8"></td>
          <td width="91" align="center"  ><img src="/images/user/game/service/text_write_day.gif" width="28" height="11"></td>
        </tr>
      </table></td>
    <td width="4" align="right" valign="top" background="/images/user/game/mystudy/ba_bg02.gif"><img src="/images/user/game/service/ba_tail.gif" width="3" height="27" ></td>
  </tr>
  <tr> 
    <td height="5" colspan="3"></td>
  </tr>
  <tr > 
    <td height="1" colspan="3" class="linecolor_ser3"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td width="3">&nbsp;</td>
    <td><table width="714" border="0" cellspacing="0" cellpadding="0" align="center">
<%

if(list2.size() != 0){
  for( int i = 0; i < list2.size(); i++) {

	DataBox dbox = (DataBox)list2.get(i);
	v_seq      = dbox.getInt("d_seq");
	v_cnt      = dbox.getInt("d_cnt");
	v_adtitle  = dbox.getString("d_adtitle");
	v_addate   = dbox.getString("d_addate");
	v_adname   = dbox.getString("d_adname");
	v_upfilecnt = dbox.getInt("d_filecnt");
	v_dispnum   = dbox.getInt("d_dispnum");
    v_totalpage = dbox.getInt("d_totalpage");
    v_rowcount  = dbox.getInt("d_rowcount");
	
	if (!v_searchtext.equals("")&&v_search.equals("adtitle")) {
		v_adtitle = StringManager.replace(v_adtitle, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
	} 
	else if(!v_searchtext.equals("")&&v_search.equals("adname")){
		v_adname = StringManager.replace(v_adname, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
	}
%>
        <tr> 
          <td width="42" class="tbl_grc" ><%=v_dispnum%></td>
          <td name="p_seq" width="354" class="tbl_gleft" value="<%=v_seq%>"><a href="javascript:selectView(<%=v_seq%>)"><%=v_adtitle%></td>
          <td width="93" class="tbl_grc" ><%=v_adname%></td>
          <td width="65" class="tbl_grc" ><%=v_cnt%></td>
          <td width="64" class="tbl_grc" ><%if(v_upfilecnt>0){%><img src="/images/user/game/service/i_add.gif" width="13" height="13"><%} else {%>-<%}%></td>
          <td width="96" class="tbl_grc" ><%=FormatDate.getFormatDate(v_addate, "yyyy-MM-dd")%></td>
        </tr>
<%		}
	}else{%>

	  <tr> 
		<td class="board_text1" colspan ='5' align='center' height="25">등록된 내용이 없습니다.</td>
	  </tr>
<%  }   %>
        
      </table></td>
    <td width="3">&nbsp;</td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td height="2" class="lcolor"></td>
  </tr>
  <tr> 
    <td height="2" class="linecolor_ser4"></td>
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
  </tr>
  <tr> 
    <td height="5" ></td>
  </tr>
  <tr> 
    <td height="2"class="linecolor_ser4"></td>
  </tr>
</table>

</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
