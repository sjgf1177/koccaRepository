<%
//**********************************************************
//  1. 제      목: EDUCATION SUBJECT PAGE
//  2. 프로그램명: gu_EducationSubject_L.jsp
//  3. 개      요: 나의강의실
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.30
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "MyClassBean" class = "com.credu.study.MyClassBean"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","2");
	box.put("submenu","0");

    String server = conf.getProperty("game.url.value");

    String  v_process   = box.getString("p_process");
    String  v_user_id   = box.getSession("userid");
    String  v_name      = box.getSession("name");
    String  v_grcode    = box.getSession("tem_grcode");
    String  v_lglast    = box.getSession("lglast");
    String  v_wj_classkey = "";
    String  v_edustartdt = "";


    String  v_gubun      =  "";
    String  v_gubun_value=  "";
    double  v_price      =  0;
    String  v_tid           = "";
    String  v_goodname      = "";
    String  v_resultcode    = "";
    String  v_pgauthdate    = "";
    String  v_cancelyn      = "";
    String  v_paystatus     = "";
    String  v_pay_status_text = "";
    String  v_ldate         = "";
    String  v_seq           = "";
    String  v_sphere        = "";
    String  v_listgubun     = "";
    
    String  v_isonoff    =  "";
    String  v_subj       =  "";
    String  v_year       =  "";
    String  v_subjnm     =  "";
    String  v_subjseq    =  "";
    String  v_edustart   =  "";
    String  v_eduend     =  "";
    String  v_eduurl     =  "";
    int     v_ieduurl    = 0;
    String  v_cpsubj     = "";
    String  v_cpsubjseq  = "";

    String  v_isoutsourcing = "";
    String  v_company       = "";
    String  v_edulist_value = "";
    String  v_contenttype   ="";

    int     i           =  0;
    
    //데이터
    ArrayList	listStudyingList	= (ArrayList)request.getAttribute("selectStudyingList");
    ArrayList	listBillList		= (ArrayList)request.getAttribute("selectBillList");
    ArrayList	listInterestList	= (ArrayList)request.getAttribute("selectInterestList");

    //화면에 보여줄 데이터
    String  v_studyhistorycnt	= box.getString("p_studyhistorycnt");	//교육이력 건수
    double  v_mypoint			= box.getDouble("p_mypoint");			//나의 포인트
    String  v_onstudycnt		= box.getString("p_onstudycnt");		//나의 온라인 수강 건수
    String  v_offstudycnt		= box.getString("p_offstudycnt");		//나의 오프라인 수강 건수
    String  v_message			= box.getString("p_message");			//오늘의 명언
    String  v_subjqnacnt		= box.getString("p_subjqnacnt");		//과정질문방
    String  v_qnacnt			= box.getString("p_qnacnt");			//Q&A
    String  v_counselcnt		= box.getString("p_counselcnt");		//1vs1
    String  v_myeventcnt		= box.getString("p_myeventcnt");		//나의이벤트
    String  v_mywineventcnt		= box.getString("p_mywineventcnt");		//나의당첨이벤트
    String  v_myworkshopcnt		= box.getString("p_myworkshopcnt");		//나의워크샵
    String	v_iproposehistoryycnt = box.getString("p_iProposeHistoryYCnt"); //나의 수강신청 이력건수(온라인/최종승인)
    String	v_iproposehistorybcnt = box.getString("p_iProposeHistoryBCnt"); //나의 수강신청 이력건수(온라인/승인대기)
    String	v_ioffproposehistoryucnt = box.getString("p_iOffProposeHistoryUCnt"); //나의 수강신청 이력건수(오프라인/승인대기)
    String	v_ioffproposehistoryycnt = box.getString("p_iOffProposeHistoryYCnt"); //나의 수강신청 이력건수(오프라인/최종승인)

    // 학습제약조건
    int v_controlstudy = 0;
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->

<SCRIPT LANGUAGE="JavaScript">
<!--

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
//    alert(s_url);
           if (url == '') {
               alert("정보가 없습니다");
           } else {
               if (iurl == 0)
                open_window('openApp',s_url,0,0,0,0,false,false,false,true,true);
               else
                open_window('openApp',s_url,0,0,1024,768,false,false,false,'yes','yes');
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
// 학습시간제한
function controlstudy() {
    alert("지금은 학습가능 시간이 아닙니다.");
    return;
}

// 학습현황 OPEN
function whenEdulist(subjnm, iurl, iseduend, url) {
//alert(url);
    var ulink = url + "&p_subjnm=" + subjnm + "&p_iurl=" + iurl + "&p_iseduend=" + iseduend;
    var new_Open = window.open(ulink,"eduList",'scrollbars=no,width=350,height=240,scrollbars=yes,resizable=no');
}

// 과정상세정보 POPUP
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

//온라인 결제세부정보 조회
function fnOnPayInfo(p_tid) {
	window.self.name = "winProposePage";
	open_window("openPayInfo","","100","100","550","450","no","no","no","yes","yes");
	document.form1.target = "openPayInfo";
	document.form1.p_tid.value = p_tid;
	document.form1.action='/servlet/controller.study.MyClassBillServlet';
	document.form1.p_process.value = 'PayInfoPage';
	document.form1.submit();
    document.form1.target = window.self.name;
}

//오프라인 결제세부정보 조회
function fnOffPayInfo(p_listgubun, p_tid, p_subj, p_year, p_subjseq, p_seq) {
	window.self.name = "winProposePage";
	open_window("openPayInfo","","100","100","550","450","no","no","no","yes","yes");
	document.form1.target = "openPayInfo";
	document.form1.p_tid.value = p_tid;
	document.form1.p_listgubun.value = p_listgubun;
	document.form1.p_subj.value = p_subj;
	document.form1.p_year.value = p_year;
	document.form1.p_subjseq.value = p_subjseq;
	document.form1.p_seq.value = p_seq;
	document.form1.action='/servlet/controller.study.MyClassBillServlet';
	document.form1.p_process.value = 'OffPayInfoPage';
	document.form1.submit();
    document.form1.target = window.self.name;
}

//과정 내용보기
function whenSubjInfo(subj,tabnum){
 document.form1.p_subj.value     = subj;
 document.form1.p_tabnum.value   = tabnum;
 document.form1.p_process.value  = 'SubjectPreviewPage';
 document.form1.p_rprocess.value = 'SubjectList';
 document.form1.action='/servlet/controller.course.CourseIntroServlet';
 document.form1.target = "_self";
 document.form1.submit();
}

//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name= "form1" method= "post">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_subj'      value ="">
    <input type='hidden' name='p_year'      value ="">
    <input type='hidden' name='p_subjseq'   value ="">
    <input type='hidden' name='p_scsubjseq'   value ="">
    <input type='hidden' name='p_subjnm'    value ="">
    <input type='hidden' name='p_userid'    value ="">
    <input type='hidden' name='p_gubun'     value ="">
    <input type='hidden' name='p_rejectedreason'     value ="">
    <input type='hidden' name='p_isonoff'   value ="">
    <input type='hidden' name='p_kind' value="">
    <input type='hidden' name='p_grcode' value="<%=v_grcode %>">
    <input type='hidden' name='s_grcode' value="<%=v_grcode %>">
    <input type='hidden' name='p_tid'       value ="">
    <input type='hidden' name='p_seq'       value ="">
    <input type='hidden' name='p_tabnum'    value ="">
    <input type='hidden' name='p_rprocess'  value ="">
    <input type='hidden' name='p_listgubun'  value ="">
    <!-- 학습창 다시띄우기용 1:다시 안띄우기  2:다시띄우기(학습컨텐츠에서 닫았을경우) -->
    <input type='hidden' name='lessonRepopup' value="1">
    <input type='hidden' name='lessonReurl' value="">
    <input type='hidden' name='lessonReiurl' value="">
    
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > <u>나의 강의실</u></td>
			</tr>
		</table>
		<div class="myclassheader">
			<table class="userinfo" cellpadding="0" cellspacing="0">
				<colgroup><col width="120"><col width="*"><col width="120"><col width="216"></colgroup>
				<tr>
					<td colspan="4" class="username"><h2><span><%= v_name %></span>님의 강의실입니다.</h2></td>
				</tr>
				<tr>
					<th>최종 로그인</th>
					<td><%= FormatDate.getFormatDate(v_lglast,"yyyy.MM.dd HH:mm") %></td>
					<th>나의 교육이력</th>
					<td><b><%= v_studyhistorycnt %></b> 회</td>
				</tr>
				<tr>
					<th>수강중인 과정</th>
					<td colspan="3">온라인 <b><%= v_onstudycnt %></b> 건 / 오프라인 <b><%= v_offstudycnt %></b> 건 </td>
				</tr>
			</table>
		</div>
		<div class="myclassmiddle">
			<div class="playing"><img src="/images/2012/sub/page1/subject_playing_course.png" alt="수강중인 과정"></div>
			<div class="listbar">
				<table class="listrable" cellpadding="0" cellspacing="0">
					<colgroup><col width="90"><col width="*"><col width="160"><col width="120"></colgroup>
					<tr class="listhead">
						<th>구분</th>
						<th>과정명</th>
						<th>교육기간</th>
						<th>학습</th>
					</tr>
					<%
          for(i = 0; i < listStudyingList.size(); i++) {
              DataBox dbox    = (DataBox)listStudyingList.get(i);
              
              v_gubun         =  dbox.getString("d_gubun");
              v_subj          =  dbox.getString("d_subj");
              v_year          =  dbox.getString("d_year");
              v_subjseq       =  dbox.getString("d_subjseq");
              v_subjnm        =  dbox.getString("d_subjnm");
              v_edustart      =  dbox.getString("d_edustart");
              v_eduend        =  dbox.getString("d_eduend");
              v_contenttype   =  dbox.getString("d_contenttype");
              v_eduurl		=  dbox.getString("d_eduurl");

              v_wj_classkey   =  dbox.getString("d_wj_classkey");
              v_edustartdt    =  v_edustart.substring(0,8);

              v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
              v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");

              if(v_gubun.equals("ON"))      {   v_gubun_value =   "온라인";
              }else if(v_gubun.equals("OFF")){  v_gubun_value =   "오프라인";     }

              // 학습url=외주인 경우..
              if(v_eduurl.equals("")) { v_ieduurl = 0; }else{ v_ieduurl = 1; }

              // 학습현황
              v_edulist_value = "/servlet/controller.contents.EduStart?p_process=eduList&p_subj="+ v_subj +"&p_year="+ v_year +"&p_subjseq="+v_subjseq+"&p_userid="+v_user_id;

              if (v_isoutsourcing.equals("Y")) {                 //위탁과정일때

                  v_eduurl = "/servlet/controller.contents.EduStart";
                  v_eduurl += "?FIELD1=" + v_user_id + "&FIELD2=" + v_year + "&FIELD3=" + v_cpsubj + "&FIELD4=" + v_cpsubjseq + "&FIELD99=" + v_company+"&FIELD100=N"+ "&contenttype=" + v_contenttype+ "&p_subj=" + v_subj +"&p_year="+ v_year +"&p_subjseq="+v_subjseq;
              } else {
//                	v_eduurl = EduEtc1Bean.make_eduURL(v_subj,v_year,v_subjseq);  //외부과정이 아닌경우
//                    v_eduurl +=  "&contenttype=" + v_contenttype;
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
                  v_eduurl +=  "&contenttype=" + v_contenttype;
              }

              if(v_isonoff.equals("OFF")){
                  v_eduurl = "/servlet/controller.study.OffEduStartServlet?p_process=selectFrame&p_subj="+v_subj+"&p_year="+v_year+"&p_subjseq="+v_subjseq;
              }
              // 학습시간제한
      		// v_controlstudy = control.allowStudy(box, v_subj, v_year, v_subjseq);
					%>      
					<tr class="listmiddle" onmouseover="this.style.background='#fbfbfb'" onmouseout="this.style.background=''">
						<td class="linefirst"><%= v_gubun_value %></td>
						<td class="title"><%=v_subjnm%></td>
						<td><%= v_edustart %>~<%= v_eduend %></td>
						<td class="learnactive">
						<%
						if ("ON".equals(v_gubun)) { 
							if (v_controlstudy >0) {  %>                        	        
          	  	<button type="button" tabindex="<%=i+170%>" onClick="javscript:controlstudy()" class="btn_nomal_darkmint" title="이 과정의 강의를 시작합니다">학습하기</button>
          		<% } else { %>
          			<button type="button" tabindex="<%=i+170%>" onClick="javscript:studyOpen('<%=v_eduurl%>','<%=v_ieduurl%>')" class="btn_nomal_darkmint" title="이 과정의 강의를 시작합니다">학습하기</button>
          		<%
          			}
             	}else{
          		%>
             &nbsp;
             <%} %>
						</td>
					</tr>
				<% } %>	
				</table>
			</div>
		</div>
		<ul class="myclassbottom">
			<li>
				<img src="/images/2012/sub/page1/subject_mycounsel.gif" alt="나의 상담내역">
				<table class="postcounsel" cellspacing="0" cellpadding="0">
					<tr>
						<th id="ktxtfocus1"><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&p_type=SUBJ" onfocus="txtlink('k',1)" onblur="txtlink('k',1)" tabindex="201" title="과정질문방으로 이동합니다">과정질문방</a></th>
						<td><b><%= v_subjqnacnt %></b> 건</td>
					</tr>
					<tr>
						<th id="ktxtfocus2"><a href="http://172.16.80.83:7001/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&p_type=PQ" onfocus="txtlink('k',2)" onblur="txtlink('k',2)" tabindex="202" title="홈페이지 이용 문의로 이동합니다">홈페이지 이용 문의</a></th>
						<td><b><%= v_qnacnt %></b> 건</td>
					</tr>
					<tr>
						<th id="ktxtfocus3"><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&p_type=MM" onfocus="txtlink('k',3)" onblur="txtlink('k',3)" tabindex="203" title="1:1상담으로 이동합니다">1:1상담</a></th>
						<td><b><%= v_counselcnt %></b> 건</td>
					</tr>
				</table>
			</li>
			<li class="btmcenter">
				<img src="/images/2012/sub/page1/subject_request.gif" alt="수강신청 확인/취소">
				<table class="postrequest" cellspacing="0" cellpadding="0">
					<tr>
						<th rowspan="2" id="ktxtfocus4"><a href="/servlet/controller.study.MyClassServlet?p_process=ProposeHistoryPage" onfocus="txtlink('k',4)" onblur="txtlink('k',4)" tabindex="204" title="온라인 교육신청 목록으로 이동합니다">온라인</a></th>
						<td class="requestconfirm">승인대기</td>
						<td><b><%=v_iproposehistorybcnt %></b> 건</td>
					</tr>
					<tr>
						<td class="requestconfirm">최종승인</td>
						<td><b><%=v_iproposehistoryycnt %></b> 건</td>
					</tr>
					<tr>
						<th rowspan="2" id="ktxtfocus5"><a href="/servlet/controller.study.MyClassServlet?p_process=ProposeOffHistoryPage" onfocus="txtlink('k',5)" onblur="txtlink('k',5)" tabindex="205" title="오프라인 교육신청 목록으로 이동합니다">오프라인</a></th>
						<td class="requestconfirm">승인대기</td>
						<td><b><%=v_ioffproposehistoryucnt %></b> 건</td>
					</tr>
					<tr>
						<td class="requestconfirm">최종승인</td>
						<td><b><%=v_ioffproposehistoryycnt %></b> 건</td>
					</tr>
				</table>
			</li>
			<li class="btmright">
				<a href="http://kocca.anyhelp.net" target="remote" tabindex="206"><img src="/images/2012/sub/page1/myclass_banner_01.gif" alt="원격지원서비스"></a><br>
				<a href="javascript:menuForward('4','05');" tabindex="207"><img src="/images/2012/sub/page1/myclass_banner_02.gif" alt="교육환경설정하기"></a>
			</li>
		</ul>
	</div>
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
