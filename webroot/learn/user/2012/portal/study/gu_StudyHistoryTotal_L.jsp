<%
//**********************************************************
//  1. 제      목: STUDY HISTORY LIST
//  2. 프로그램명: zu_StudyHistoryTotal_L.jsp
//  3. 개      요: 개인교육이력
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 8. 27
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
    
    box.put("topmenu","2");
	box.put("submenu","2");

    String  v_user_id   = box.getSession("userid");
    String  v_process   = box.getString("p_process");
    String  v_selectcode= box.getStringDefault("p_selectcode", "ALL");
	String	v_grcode	= box.getSession("tem_grcode");

    String  v_wj_classkey = "";
    String  v_edustartdt = "";

    String  v_upperclass	= "";
    String  v_isonoff   	= "";
    String  v_course    	= "";
    String  v_cyear     	= "";
    String  v_courseseq 	= "";
    String  v_coursenm  	= "";
    String  v_subj      	= "";
    String  v_year      	= "";
    String  v_subjnm    	= "";
    String  v_subjseq   	= "";
    String  v_subjseqgr 	= "";
    String  v_edustart  	= "";
    String  v_eduend    	= "";
    String  v_edustart1 	= "";
    String  v_eduend1   	= "";
    String  v_place     	= "";
    String  v_edu_org_nm	= "";
    String  v_kind      	= "";
    String  v_isgraduated	= "";
    String  v_isnewcourse	= "";
    String  v_isonoff_value	= "";
    String  v_kind_value 	= "";
    String  v_serno      	= "";
    String  v_pkey       	= "";
    String  v_gubun      	= "";
    String  v_eduurl     	= "";
    int     v_ieduurl    	= 0;
    String  v_cpsubj     	= "";
    String  v_cpsubjseq  	= "";
    String  v_isoutsourcing = "";
    String  v_reviewtype	= "";
    int	    v_reviewdays	= 0;
    String  v_reviewstart	= "";
    String  v_reviewend		= "";
    String	v_isablereview	= "";
    String  v_today			= FormatDate.getDate("yyyyMMdd");
    double	v_score			= 0d;

	String  v_tmp_subj		= "";
	String  v_iscourseYn	= "";

    String  v_upperclassnm	= "";
    String  v_middleclassnm	= "";
    String  v_edulist_value = "";
    String  v_company		= "";
    String  v_contenttype   = "";

    String  v_subjgrcode   = "";
    
    double v_credit     	= 0;
    double v_creditexam  	= 0;
    
    int     v_total     	= 0;
    int     i           	= 0;
    int     l           	= 0;
	int		v_subjcnt		= 0;

    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0, v_tabseq = 0;

    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");   

	ArrayList list      = null;
    //DEFINED class&variable END

    list = (ArrayList)request.getAttribute("StudyHistoryList");
    String grcode=box.getSession("tem_grcode");
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
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

//수료증 출력
function suRoyJeung(subj,year,subjseq,userid, kind,subjgrcode){
	window.self.name = "SuryoJeung";
	var new_Open = window.open("","openSuryoJeungPrint",'scrollbars=no,width=830,height=800,scrollbars=yes,resizable=NO');
	
	document.form1.target = "openSuryoJeungPrint";
	document.form1.action = "/servlet/controller.polity.DiplomaAdminServlet";
	
	//document.form1.action = "/learn/admin/polity/RexViewer.jsp?pRptNames=__Diploma_Kocca&pRptParams=lee1^N000001^2006^cbu001^0001";
	document.form1.p_subj.value = subj;
	document.form1.p_year.value = year;
	document.form1.p_scsubjseq.value = subjseq;
	document.form1.p_userid.value = userid;
	document.form1.p_kind.value = kind;
	document.form1.p_grcode.value = subjgrcode;
	document.form1.p_process.value = "DiplomaPrint";
	document.form1.submit();
    document.form1.target = window.self.name;
}

function studyOpenRe() {
	document.form1.lessonRepopup.value = "1";
	var rurl = document.form1.lessonReurl.value;
	var riurl = document.form1.lessonReiurl.value;
	setTimeout("studyOpen('"+rurl+"','"+riurl+"');" ,100);
}

// 학습창 OPEN
function studyOpen(url, iurl) {
<%      if (box.getSession("gadmin").equals("ZZ")) {  %>
			document.form1.lessonReurl.value = url;
			document.form1.lessonReiurl.value = iurl;
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

// 학습창 OPEN (외부강좌 : 웅진패스원)
function studyOpen2(url, iurl, wj_classkey, edustart) {
<%  if (box.getSession("gadmin").equals("ZZ")) {  %>
        document.form1.lessonReurl.value = url;
        document.form1.lessonReiurl.value = iurl;
        var s_url = url+"&p_iurl="+iurl;

        if (url == '') {
            alert("정보가 없습니다");
        } else {
            s_url ="http://kocca.campus21.co.kr/enterclass.asp?userid="+'<%=v_user_id%>'+"&classkey="+wj_classkey+"&edustart="+ edustart ;

          //  if (iurl == 0)
          //      open_window('openApp',s_url,0,0,0,0,false,false,true,true,true);
          //  else
                open_window('openApp',s_url,0,0,1024,768,false,false,'yes','yes','yes');
        }
<% } else {                                      %>
        alert ("학습자 권한이 아닙니다.");
<% }                                             %>
}

//검색내용에 맞는 리스트 페이지로 이동
function selectList() {
	document.form1.action = "/servlet/controller.study.MyClassServlet";
	document.form1.p_pageno.value = "1";
	document.form1.p_process.value = "StudyHistoryList";
	document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {

	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.study.MyClassServlet";
	document.form1.p_process.value = "StudyHistoryList";
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
    <input type='hidden' name='p_grcode'    value ="">
    <input type='hidden' name='s_grcode'    value="<%=v_grcode%>">
    <input type='hidden' name='p_year'      value="">
    <input type='hidden' name='s_subj'      value="">
    <input type='hidden' name='p_scsubjseq' value="">
    <input type='hidden' name='p_subjseq'   value="">
    <input type='hidden' name='p_userid'    value="">
    <input type='hidden' name='p_kind'      value="">
    <input type='hidden' name='p_isonoff'   value ="">
    <input type = "hidden" name = "p_pageno"		value = "<%= v_pageno %>">
    <!-- 학습창 다시띄우기용 1:다시 안띄우기  2:다시띄우기(학습컨텐츠에서 닫았을경우) -->
    <input type='hidden' name='lessonRepopup' value="1">
    <input type='hidden' name='lessonReurl' value="">
    <input type='hidden' name='lessonReiurl' value="">
    
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 나의강의실 ><u>나의 교육이력</u></td>
			</tr>
		</table>
		<div class="concept a02">
			<div class="titlemsg"><img src="/images/2012/sub/page1/title/learning_finish.gif" alt="나의 교육이력"></div>
			<ul class="pagetab">
				<li><img src="/images/2012/sub/page1/tabmenu/online_on.png" alt="온라인과정"></li>
				<li><a href="/servlet/controller.study.MyClassServlet?p_process=StudyHistoryOffList" tabindex="171" title="회원님의 오프라인과정 교육이력을 확인할 수 있습니다"><img src="/images/2012/sub/page1/tabmenu/offline_off.png" alt="오프라인과정"></a></li>
			</ul>
		</div>
		<div class="myclass">
			<div class="listbar">
				<table class="listrable" cellpadding="0" cellspacing="0">
					<colgroup><col width="90"><col width="*"><col width="140"><col width="140"><col width="80"><col width="60"><col width="60"><col width="80"></colgroup>
					<tr class="listhead">
						<th>카테고리
							<!-- <select class="classification">
								<option>전체</option>
								<option>기획</option>
								<option>제작</option>
								<option>유통</option>
								<option>경영지원</option>
								<option>특강</option>
								<option>경영직무</option>
								<option>폐지과정</option>
							</select> -->
						</th>
						<th>과정명</th>
						<th>교육기간</th>
						<th>복습기간</th>
						<th>복습</th>
						<th>성적</th>
						<th>수료여부</th>
						<th>수료증</th>
					</tr>
					<%    
					for(i = 0; i < list.size(); i++) {
						DataBox dbox = (DataBox)list.get(i);
			
						v_isonoff     	= dbox.getString("d_isonoff");
						v_course      	= dbox.getString("d_course");
						v_cyear       	= dbox.getString("d_cyear");
						v_courseseq   	= dbox.getString("d_courseseq");
						v_coursenm    	= dbox.getString("d_coursenm");
						v_subj        	= dbox.getString("d_subj");
						v_subjseqgr   	= dbox.getString("d_subjgr");
						v_year        	= dbox.getString("d_year");
						v_subjseq     	= dbox.getString("d_subjseq");
						v_subjnm      	= dbox.getString("d_subjnm");
						v_edustart    	= dbox.getString("d_edustart");
						v_eduend      	= dbox.getString("d_eduend");
						v_credit      	= dbox.getDouble("d_credit");
						v_creditexam  	= dbox.getDouble("d_creditexam");
						v_place       	= dbox.getString("d_place");
						v_isgraduated 	= dbox.getString("d_isgraduated");
						v_serno       	= dbox.getString("d_serno");
						v_pkey        	= dbox.getString("d_pkey");
						v_gubun       	= dbox.getString("d_gubun");
						v_iscourseYn  	= dbox.getString("d_isbelongcourse");
						v_isoutsourcing	= dbox.getString("d_isoutsourcing");
			            v_cpsubj        = dbox.getString("d_cpsubj");
			            v_cpsubjseq     = dbox.getString("d_cpsubjseq");
						v_kind        	= dbox.getString("d_kind");
						v_upperclassnm 	= dbox.getString("d_uclassnm");
						v_middleclassnm	= dbox.getString("d_mclassnm");
						v_reviewtype	= dbox.getString("d_reviewtype");
						v_reviewdays	= dbox.getInt("d_reviewdays");
						v_reviewstart	= dbox.getString("d_reviewstart");
						v_isablereview	= dbox.getString("d_isablereview");
			            v_company       = dbox.getString("d_company");
			            v_contenttype   = dbox.getString("d_contenttype");
			            v_score			= dbox.getDouble("d_score");
			            v_eduurl			= dbox.getString("d_eduurl");
			            v_dispnum	= dbox.getInt("d_dispnum");
			            v_totalpage	= dbox.getInt("d_totalpage");
			
						v_iscourseYn 	= dbox.getString("d_isbelongcourse");
						v_subjcnt		= dbox.getInt("d_subjcnt");
			
						v_subjgrcode 	= dbox.getString("d_grcode");
			
			            v_wj_classkey   =  dbox.getString("d_wj_classkey");
			            v_edustartdt   =  v_edustart.substring(0,8);
			
						if (v_reviewtype.equals("D")) {
							v_reviewend = FormatDate.getDateAdd(FormatDate.getFormatDate(v_reviewstart,"yyyyMMdd"),"yyyyMMdd", "date", v_reviewdays - 1);
						} else if (v_reviewtype.equals("W")) {
							v_reviewend = FormatDate.getDateAdd(FormatDate.getFormatDate(v_reviewstart,"yyyyMMdd"),"yyyyMMdd", "date", v_reviewdays * 7 - 1);
						} else if (v_reviewtype.equals("M")) {
							v_reviewend = FormatDate.getDateAdd(FormatDate.getFormatDate(v_reviewstart,"yyyyMMdd"),"yyyyMMdd", "month", v_reviewdays);
						} else if (v_reviewtype.equals("Y")) {
							v_reviewend = FormatDate.getDateAdd(FormatDate.getFormatDate(v_reviewstart,"yyyyMMdd"),"yyyyMMdd", "year", v_reviewdays);
						}
			
			            int reviewPlusDay=3; //수료처리 기간이 최대 3일이라서 3일을 더해줌
			            v_reviewend=FormatDate.getFormatDate(FormatDate.getDateAdd(FormatDate.getFormatDate(v_reviewend,"yyyyMMdd"),"yyyyMMdd", "date", v_reviewdays +reviewPlusDay), "yyyy.MM.dd");
			            String convertReviewEnd=FormatDate.getFormatDate(v_reviewend,"yyyyMMdd");
			
			            if(StringManager.toInt(v_today) >StringManager.toInt(convertReviewEnd))
			                v_isablereview = "N";
			
						if (v_reviewend.equals("")) {
							v_isablereview = "N";
						}
			
						v_reviewstart	= FormatDate.getFormatDate(v_reviewstart,"yyyy.MM.dd");
						v_edustart		= FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
						v_eduend		= FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");
			
						if(v_isgraduated.equals("A"))       v_isgraduated = "-";
			            else if(v_isgraduated.equals("B"))  v_isgraduated = "처리중"; 
						else if(v_isgraduated.equals("Y"))  v_isgraduated = "수료";
			            else if(v_isgraduated.equals("N"))  v_isgraduated = "미수료";
			
			
						// 과거 자료이면
						if (v_kind.equals("2")) {
							v_edulist_value = "/servlet/controller.contents.EduStart?p_process=eduList&p_subj="+ v_pkey +"&p_year="+ v_year +"&p_subjseq="+v_subjseq+"&p_userid="+v_user_id+"&p_kind=2";
							// 과정코드 셋팅
							v_subj = v_pkey;
						} else {
							v_edulist_value = "/servlet/controller.contents.EduStart?p_process=eduList&p_subj="+ v_subj +"&p_year="+ v_year+"&p_subjseq="+v_subjseq+"&p_userid="+v_user_id;
						}
						
			            // 과거 자료이면
			            if (v_kind.equals("2")) {
			
			            } else {
			                if (v_isoutsourcing.equals("Y")) {                 //크레듀
			                    v_eduurl = v_eduurl+"?FIELD1="+v_user_id+"&FIELD2="+ v_year+"&FIELD3="+v_cpsubj+"&FIELD4=" +v_cpsubjseq+ "&FIELD99="+ v_company+ "&FIELD100=Y";
			                    v_ieduurl = 1;
			                } else {//외부과정이 아닌경우
			        			// 직접 로직 흡수.. v_eduurl = EduEtc1Bean.make_eduURL(v_subj,v_year,v_subjseq);
			        			if (v_eduurl.equals("")){
			        				v_eduurl = "/servlet/controller.contents.EduStart?p_subj="+v_subj;
			        			}
			
			        			if (!v_year.equals(""))
			        				v_eduurl = v_eduurl+"&p_year="+v_year+"&p_subjseq=";
			        			else 
			        				v_eduurl = v_eduurl+"&p_year=2000&p_subjseq=";
			        			if(!v_subjseq.equals(""))
			        				v_eduurl = v_eduurl+v_subjseq;
			        			else v_eduurl = v_eduurl+"0001";
			                    v_eduurl +=  "&contenttype=" + v_contenttype+"&p_review=Y";
			                    v_ieduurl = 0;
			                }
			            }
					%>
					
					<tr class="listmiddle" onmouseover="this.style.background='#fbfbfb'" onmouseout="this.style.background=''">
						<td class="linefirst"><%= v_upperclassnm %></td>
						<td class="title" id="bboardfocus<%=i+1%>"><a href="javascript:whenSubjInfoPopup('<%= v_subj %>','<%= v_subjnm %>','<%= v_isonoff %>')" onfocus="boardfocus('b',<%=i+1%>)"  onblur="boardfocus('b',<%=i+1%>)" tabindex="<%="2"+i+"1"%>" title="<%= v_subjnm %>"><%= v_subjnm %></a></td>
						<td><%= v_edustart %>~<%= v_eduend %></td>
						<td>
							<%if(v_isablereview.equals("Y") && v_isgraduated.equals("수료")){%>
								<%= v_reviewstart %>~<%= v_reviewend %>
							<%}else{ %>
								-
							<%} %>
						</td>
						<td>
							<%//if(v_isablereview.equals("Y") && (v_isgraduated.equals("수료") || v_isgraduated.equals("미수료")) ) {
							if(v_isablereview.equals("Y") && v_isgraduated.equals("수료")){%>
	           		<button type="button" tabindex="<%="2"+i+"2"%>" onclick="javascript:studyOpen('<%= v_eduurl %>','<%= v_ieduurl %>')" class="btn_small_gray" title="이 과정을 복습할 수 있습니다">복습</button>
	            <% }%>
						</td>
						
						<td id="btxtfocus<%=i+1%>">
						
						<% if ((!v_subj.equals("")) && (!v_isgraduated.equals("처리중"))) {  %>
							<a href="javascript:whenEdulist('<%= v_subjnm %>','Y','<%= v_edulist_value %>')" onfocus="txtlink('b',<%=i+1%>)"  onblur="txtlink('b',<%=i+1%>)" tabindex="<%="2"+i+"3"%>" class="board_btn2"><%= v_score %></a>
						<%}else{%>
						  	-
						<%}%>
						
						
						</td>
						<td><%= v_isgraduated %></td>
						<td>
							<%if(!v_subj.equals("") && v_isgraduated.equals("수료")){%>
							 <button type="button" tabindex="<%="2"+i+"4"%>" onclick="javascript:suRoyJeung('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_user_id %>','<%= v_kind %>','<%= v_subjgrcode %>')" class="btn_small_gray" title="클릭하시면 수료증을 인쇄할 수 있습니다">인쇄</button>
							<%}else{%>
							  -
							<%}%>
						</td>
					</tr>
					 <% } %>
					 <%	if(i == 0){	 %>
				     <tr><td colspan="5" align="center" class="linefirst">학습중인 과정이 없습니다.</td></tr>
				     <% } %>
				</table>
			</div>
			<div class="pageset">
				<%=PageUtil.re2012_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
			</div>
		</div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
