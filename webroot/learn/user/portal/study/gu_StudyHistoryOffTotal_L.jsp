<%
//**********************************************************
//  1. 제      목: STUDY HISTORY LIST
//  2. 프로그램명: zu_StudyHistoryOffTotal_L.jsp
//  3. 개      요: 개인교육이력 off-line
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.25
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_user_id   		= box.getSession("userid");
    String  v_process   		= box.getString("p_process");
    String  v_selectcode		= box.getStringDefault("p_selectcode", "ALL");
	String	v_grcode			= box.getSession("tem_grcode");

    String  v_upperclass		= "";
    String  v_upperclassnm		= "";
    String  v_subj      		= "";
    String  v_year      		= "";
    String  v_subjnm    		= "";
    String  v_subjseq   		= "";
    String  v_edustart  		= "";
    String  v_eduend    		= "";
    String  v_kind      		= "1";
    String  v_isgraduated		= "";
    String  v_isgraduated_txt	= "";
    String  v_stustatus 		= "";
    String  v_stustatusnm		= "";
    double	v_score				= 0d;
	String  v_isterm			= "";

    int     i           		= 0;

    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0, v_tabseq = 0;

    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");   

    ArrayList list      = null;
    //DEFINED class&variable END

    list = (ArrayList)request.getAttribute("StudyHistoryOffList");
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--

// 학습현황 OPEN
function whenEdulist(subjnm, iseduend, url) {
    var ulink = url + "&p_subjnm=" + subjnm + "&p_iseduend=" + iseduend;
    var new_Open = window.open(ulink,"eduList",'scrollbars=no,width=350,height=240,scrollbars=yes,resizable=no');
}

//과정상세정보 POPUP
function whenSubjInfoPopup(subj,subjnm,isonoff){
    window.self.name = "SubjList";
    window.open("", "openSubjInfo", "scrollbars=no,width=720,height=350,scrollbars=yes,resizable=yes'");
    document.form1.target = "openSubjInfo"

    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value = subjnm;
    document.form1.p_isonoff.value = isonoff;
    document.form1.p_process.value = 'SubjectPreviewPopup';
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.submit();

    document.form1.target = window.self.name;
}

//학기별점수 POPUP
function whenTermScorePopup(subj,year,subjseq){
    window.self.name = "scoreList";
    window.open("", "opentermScore", "scrollbars=no,width=400,height=277,scrollbars=auto,resizable=yes'");
    document.form1.target = "opentermScore"

    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.p_process.value = 'EducationStudyingOffTermScorePopup';
    document.form1.action='/servlet/controller.study.MyClassServlet';
    document.form1.submit();

    document.form1.target = window.self.name;
}

//점수 POPUP
function whenScorePopup(subj,year,subjseq){
    window.self.name = "scoreList";
    window.open("", "openScore", "scrollbars=no,width=550,height=277,scrollbars=no,resizable=no'");
    document.form1.target = "openScore"

    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.p_process.value = 'EducationStudyingOffScorePopup';
    document.form1.action='/servlet/controller.study.MyClassServlet';
    document.form1.submit();

    document.form1.target = window.self.name;
}

//수료증 출력
function suRoyJeung(subj,year,subjseq,userid){
	window.self.name = "SuryoJeung";
	var new_Open = window.open("","openSuryoJeungPrint",'scrollbars=no,width=830,height=800,scrollbars=yes,resizable=NO');
	
	document.form1.target = "openSuryoJeungPrint";
	document.form1.action = "/servlet/controller.polity.DiplomaAdminServlet";
	
	//document.form1.action = "/learn/admin/polity/RexViewer.jsp?pRptNames=__Diploma_Kocca&pRptParams=lee1^N000001^2006^cbu001^0001";
	document.form1.p_subj.value = subj;
	document.form1.p_year.value = year;
	document.form1.p_scsubjseq.value = subjseq;
	document.form1.p_userid.value = userid;
	document.form1.p_process.value = "DiplomaOffPrint";
	document.form1.submit();
    document.form1.target = window.self.name;
}

// 학습창 OPEN
function studyOpen(url, iurl) {
<%      if (box.getSession("gadmin").equals("ZZ")) {  %>
            var s_url = url+"&p_iurl="+iurl;
//	    alert(s_url);
           if (url == '') {
               alert("정보가 없습니다");
           } else {
               if (iurl == 0)
                open_window('openApp',s_url,0,0,0,0,false,false,true,true,true);
               else
                open_window('openApp',s_url,0,0,1024,768,false,false,'yes','yes','yes');
           }
<%      } else {                                      %>
            alert ("학습자 권한이 아닙니다.");
<%      }                                             %>
}

//검색내용에 맞는 리스트 페이지로 이동
function selectList() {
	document.form1.action = "/servlet/controller.study.MyClassServlet";
	document.form1.p_pageno.value = "1";
	document.form1.p_process.value = "StudyHistoryOffList";
	document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {

	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.study.MyClassServlet";
	document.form1.p_process.value = "StudyHistoryOffList";
	document.form1.submit();

}
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name= "form1" method= "post">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_sel'       value="<%=box.getString("p_sel")%>">
    <input type='hidden' name='p_subj'      value="">
    <input type='hidden' name='p_subjnm'    value ="">
    <input type='hidden' name='s_grcode'    value="<%=v_grcode%>">
    <input type='hidden' name='p_year'      value="">
    <input type='hidden' name='s_subj'      value="">
    <input type='hidden' name='p_scsubjseq' value="">
    <input type='hidden' name='p_subjseq'   value="">
    <input type='hidden' name='p_userid'    value="">
    <input type='hidden' name='p_kind'      value="">
    <input type='hidden' name='p_isonoff'   value ="">
    <input type = "hidden" name = "p_pageno"		value = "<%= v_pageno %>">
    
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
        <td><img src="/images/portal/homepage_renewal/myclass/stitle_03.gif" alt="나의 교육이력" /></td>
        <td class="h_road">Home &gt; 나의 강의실  &gt; <strong>나의 교육이력</strong></td>
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
        <td><img src="/images/portal/homepage_renewal/myclass/bar_02.jpg" width="672" height="104" /></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
    <!--타이틀부분//-->
    
      <!--탭부분-->
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><a href="/servlet/controller.study.MyClassServlet?p_process=StudyHistoryList" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image55','','/images/portal/homepage_renewal/myclass/tab_on_001.gif',1)"><img src="/images/portal/homepage_renewal/myclass/tab_off_001.gif" name="Image55" id="Image55" /></a></td>
          <td><a href="/servlet/controller.study.MyClassServlet?p_process=StudyHistoryOffList" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image56','','/images/portal/homepage_renewal/myclass/tab_on_002.gif',1)"><img src="/images/portal/homepage_renewal/myclass/tab_on_002.gif" name="Image56" id="Image56" /></a></td>
        </tr>
      </table>
      <!--탭부분//-->
      
      <!--테이블부분-->
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="tit_table">
          	<kocca_select:select name="p_upperclass" sqlNum="upperclass"  param=" "
	              onChange="selectList();" attr="분류" selectedValue="<%= v_upperclass %>" isLoad="true" all="none" type="3"/>
          </td>
          <td class="tit_table"> 과정명  </td>
          <td class="tit_table">교육기간</td>
          <td class="tit_table">성적보기</td>
          <td class="tit_table">상태</td>
          <td class="tit_table_right">수료증</td>
        </tr>
        
        <%
		for(i = 0; i < list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);

			v_subj        	= dbox.getString("d_subj");
			v_year        	= dbox.getString("d_year");
			v_subjseq     	= dbox.getString("d_subjseq");
			v_subjnm      	= dbox.getString("d_subjnm");
			v_edustart    	= dbox.getString("d_edustart");
			v_eduend      	= dbox.getString("d_eduend");
			v_isgraduated 	= dbox.getString("d_isgraduated");
			v_stustatus 	= dbox.getString("d_stustatus");
			v_stustatusnm 	= dbox.getString("d_stustatusnm");
			v_upperclassnm 	= dbox.getString("d_upperclassnm");
            v_score			= dbox.getDouble("d_score");
			v_isterm 	    = dbox.getString("d_isterm");

            v_dispnum	= dbox.getInt("d_dispnum");
            v_totalpage	= dbox.getInt("d_totalpage");
            
			v_edustart		= FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
			v_eduend		= FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");

			if ("Y".equals(v_isterm)) {
				v_isgraduated_txt = v_stustatusnm;
			} else {
				if(v_isgraduated.equals("Y"))       v_isgraduated_txt = "수료";
				else if(v_isgraduated.equals("N"))  v_isgraduated_txt = "미수료";
			}
%>
        
        <tr>
          <td class="ct_tit_table"><%= v_upperclassnm %></td>
          <td class="ct_tit_table_left"><%= v_subjnm %></td>
          <td class="ct_tit_table"><%= v_edustart %>~ <%= v_eduend %></td>
          <td class="ct_tit_table"><%if ("N".equals(v_isterm)) {  %>
					<a href="javascript:whenScorePopup('<%= v_subj %>','<%= v_year %>', '<%= v_subjseq %>')" class="board_btn2"><span class="fw_b"><%= v_score %></span></a>
				<%}else{%>
					<a href="javascript:whenTermScorePopup('<%= v_subj %>','<%= v_year %>', '<%= v_subjseq %>')" class="board_btn2"><span>학기별</span></a>
				<%}%>
		  </td>
          <td class="ct_tit_table">
          	<%= v_isgraduated_txt %>
          	<%if(v_isgraduated_txt.equals("")){%>
          		&nbsp;
          	<%} %>
		  </td>
          <td class="ct_tit_table">
          	<%if("Y".equals(v_isgraduated)){%>
				  <a href="javascript:suRoyJeung('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>','<%=v_user_id%>')" class="board_btn2"><span>인쇄</span></a>
				<%}else{%>
				  -
				<%}%>
          </td>
        </tr>
<%
		}
%>
<%
		if(i == 0){
%>
            <tr>
                <td colspan="6">교육이력이 없습니다.</td>
            </tr>
              <%
        }
%>
        <tr>
          <td height="1" colspan="8" bgcolor="CACACA"></td>
        </tr>
        <tr>
          <td height="4" colspan="8" bgcolor="F7F7F7"></td>
        </tr>
      </table>
      
      	<!-- total 시작 -->
		 <%= PageUtil.re_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
		<!-- total 끝 -->

    </td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>

<%}else{ %>
    
			<h2><img src="/images/portal/myclass/h2_tit2.gif" alt="나의교육이력" class="fl_l" /><p class="category">Home > 온라인과정 > <strong>방송영상</strong></p></h2>
 
			<%--<p class="myclass_tab cltxt_2"><a href="/servlet/controller.study.MyClassServlet?p_process=StudyHistoryList"><img src="/images/portal/myclass/myclass_online_tab1_off.gif" alt="온라인과정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a><img src="/images/portal/myclass/myclass_online_tab2_on.gif" alt="오프라인과정" /></p>--%>
            <img src="/images/portal/myclass/sletter_borimg.gif" alt="" style="width:681px;background-position: left 18px;background-repeat: no-repeat;">
            <p style="line-height:7px">&nbsp;</p>
            <a href="/servlet/controller.study.MyClassServlet?p_process=StudyHistoryLis"><img src="/images/portal/btn/btn_online_off.gif" alt="온라인과정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a><img src="/images/portal/btn/btn_offline_on.gif" alt="온프라인과정">
			<table class="board_list">
			<colgroup><col width="90px" /><col width="200px" /><col width="120px" /><col width="78px" /><col width="62px" /><col width="62px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th><p>
				  <kocca_select:select name="p_upperclass" sqlNum="offupperclass"  param=" "
	              onChange="selectList();" attr="분류" selectedValue="<%= v_upperclass %>" isLoad="true" all="none" type="3" styleClass="table_title"/>
                </p></th>
				<th><p>과정명</p></th>
				<th><p>교육기간</p></th>
				<th><p>성적보기</p></th>
				<th><p>상태</p></th>
				<th class="end"><p>수료증</p></th>
			</tr>
			</thead>
			<tbody>
        <%
		for(i = 0; i < list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);

			v_subj        	= dbox.getString("d_subj");
			v_year        	= dbox.getString("d_year");
			v_subjseq     	= dbox.getString("d_subjseq");
			v_subjnm      	= dbox.getString("d_subjnm");
			v_edustart    	= dbox.getString("d_edustart");
			v_eduend      	= dbox.getString("d_eduend");
			v_isgraduated 	= dbox.getString("d_isgraduated");
			v_stustatus 	= dbox.getString("d_stustatus");
			v_stustatusnm 	= dbox.getString("d_stustatusnm");
			v_upperclassnm 	= dbox.getString("d_upperclassnm");
            v_score			= dbox.getDouble("d_score");
			v_isterm 	    = dbox.getString("d_isterm");

            v_dispnum	= dbox.getInt("d_dispnum");
            v_totalpage	= dbox.getInt("d_totalpage");
            
			v_edustart		= FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
			v_eduend		= FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");

			if ("Y".equals(v_isterm)) {
				v_isgraduated_txt = v_stustatusnm;
			} else {
				if(v_isgraduated.equals("Y"))       v_isgraduated_txt = "수료";
				else if(v_isgraduated.equals("N"))  v_isgraduated_txt = "미수료";
			}
%>
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td><%= v_upperclassnm %></td>
				<td class="ta_l"><%= v_subjnm %></td>
				<td class="num"><%= v_edustart %>~ <%= v_eduend %></td>
				<td class="num">
				<%if ("N".equals(v_isterm)) {  %>
					<a href="javascript:whenScorePopup('<%= v_subj %>','<%= v_year %>', '<%= v_subjseq %>')" class="board_btn2"><span class="fw_b"><%= v_score %></span></a>
				<%}else{%>
					<a href="javascript:whenTermScorePopup('<%= v_subj %>','<%= v_year %>', '<%= v_subjseq %>')" class="board_btn2"><span>학기별</span></a>
				<%}%>
				</td>
				<td><%= v_isgraduated_txt %></td>
				<td>
				<%if("Y".equals(v_isgraduated)){%>
				  <a href="javascript:suRoyJeung('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>','<%=v_user_id%>')" class="board_btn2"><span>인쇄</span></a>
				<%}else{%>
				  -
				<%}%>
                </td>
			</tr>
<%
		}
%>
<%
		if(i == 0){
%>
            <tr>
                <td colspan="6">교육이력이 없습니다.</td>
            </tr>
              <%
        }
%>
			</tbody>
			</table>
        <!-- 페이징 영역 종료 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
        <!-- 페이징 영역 종료 -->
        <%} %>

</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->