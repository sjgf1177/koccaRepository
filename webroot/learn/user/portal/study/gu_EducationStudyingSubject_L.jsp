<%
//**********************************************************
//  1. 제      목: EDUCATION STUDYING SUBJECT PAGE
//  2. 프로그램명: zu_EducationStudyingSubject_L.jsp
//  3. 개      요: 수강중인 과정
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009/11/23
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
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	String server = conf.getProperty("game.url.value");

    String  v_process   = box.getString("p_process");
    String  v_user_id   = box.getSession("userid");
    String  v_grcode    = box.getSession("tem_grcode");

    String  v_gyear      =  "";
    String  v_grseq      =  "";
    String  v_upperclass =  "";
    String  v_isonoff    =  "";
    String  v_course     =  "";
    String  v_cyear      =  "";
    String  v_courseseq  =  "";
    String  v_coursenm   =  "";
    String  v_subj       =  "";
    String  v_subjseqgr  =  "";
    String  v_year       =  "";
    String  v_subjnm     =  "";
    String  v_subjseq    =  "";
    String  v_propend    =  "";
    String  v_edustart   =  "";
    String  v_eduend     =  "";
    String  v_eduurl     =  "";
    int     v_ieduurl    = 0;
    String  v_isnewcourse= "";
    String  v_chkfirst   =  "";
    String  v_chkfinal   =  "";
    String  v_cancelkind =  "";
    String  v_cpsubj     = "";
    String  v_cpsubjseq  = "";
    String  v_upperclassnm = "";
    String  v_middleclassnm = "";
    String  v_preurl    =  "";
    String v_hasPreviewObj = "";
    
    String  v_wj_classkey = "";
    String  v_edustartdt = "";
    
    String  v_isgraduated  = "";
    String  v_subjtarget   = "";
    String  v_isonoff_value= "";
    String  v_isgraduated_value="";
    String  v_today        =  FormatDate.getDate("yyyyMMdd");
    String  v_propenddate  = "";
    String  v_edustartdate = "";
    String  v_eduenddate   = "";
    String  v_reviewend    = "";
    String  v_cancelcheck  = "";
    String  v_proposetype  = "";
    String  v_isoutsourcing = "";
    String  v_isablereview = "";
    int	    v_reviewdays   = 0;
    String  v_reviewtype   = "";
    String  v_stuchkfinal  = "";
    String  v_company      = "";
    String  v_edulist_value= "";
    String  v_contenttype   ="";
    String  v_kind          = "";
    String  v_gubun         = "";
    String  v_serno         = "";
    String  v_pkey          = "";
    String  v_biyong        = "";
    String  v_isuseYn       = "";
    String  v_taxString     = "";
    String  v_isstudyyn     = "";

    String  v_tmp_subj  = "";
    String  v_iscourseYn = "";
    

	int v_pagesize = box.getInt("p_pagesize");
	int	 v_totalrowcount =  0;

    String  v_base64resno = "";
    int     v_total     = 0;
    int     i           = 0;
    int     l           = 0;
    int     v_subjcnt   = 0;

    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0, v_tabseq = 0;

    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");   

    ArrayList list1     = null;
    //DEFINED class&variable END

    list1 = (ArrayList)request.getAttribute("EducationStudyingSubjectList");   //study.MyClassBean.selectEducationStudyingSubjectList(box)
    //out.println(list3.size());
    
//    SubjGongAdminBean control = new SubjGongAdminBean();

    int v_controlstudy = 0;
    String grcode=box.getSession("tem_grcode");
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
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
// rsg20180129
function studyOpen(url, iurl, wid, hei) {
<% if (box.getSession("gadmin").equals("ZZ")) {  %>
        document.form1.lessonReurl.value = url;
        document.form1.lessonReiurl.value = iurl;
        var s_url = url+"&p_iurl="+iurl;
 //alert(s_url);
        if (url == '') {
            alert("정보가 없습니다");
        } else {
        	open_window('openApp',s_url,0,0,wid,hei,false,false,false,'yes','yes');
        	/*
            if (iurl == 0)
                open_window('openApp',s_url,0,0,0,0,false,false,false,true,true);
            else
                open_window('openApp',s_url,0,0,1024,768,false,false,false,'yes','yes');
        	*/
        }
<%  } else {                                      %>
        alert ("학습자 권한이 아닙니다.");
<%  }                                             %>
}
<%-- function studyOpen(url, iurl) {
<% if (box.getSession("gadmin").equals("ZZ")) {  %>
        document.form1.lessonReurl.value = url;
        document.form1.lessonReiurl.value = iurl;
        var s_url = url+"&p_iurl="+iurl;
 //alert(s_url);
        if (url == '') {
            alert("정보가 없습니다");
        } else {
            if (iurl == 0)
                open_window('openApp',s_url,0,0,0,0,false,false,false,true,true);
            else
                open_window('openApp',s_url,0,0,1024,768,false,false,false,'yes','yes');
        }
<%  } else {                                      %>
        alert ("학습자 권한이 아닙니다.");
<%  }                                             %>
} --%>

// 학습창 OPEN (외부강좌 : 웅진패스원)
function studyOpen2(url, iurl, wj_classkey, edustart) {
<%  if (box.getSession("gadmin").equals("ZZ")) {  %>
        document.form1.lessonReurl.value = url;
        document.form1.lessonReiurl.value = iurl;
        var s_url = url+"&p_iurl="+iurl;

        //document.form1.wj_classkey.value = wj_classkey;

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
    var ulink = url + "&p_subjnm=" + subjnm + "&p_iurl=" + iurl + "&p_iseduend=" + iseduend;
//alert( ulink);
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

//검색내용에 맞는 리스트 페이지로 이동
function selectList() {
	document.form1.action = "/servlet/controller.study.MyClassServlet";
	document.form1.p_pageno.value = "1";
	document.form1.p_process.value = "EducationStudyingSubjectPage";
	document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {

	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.study.MyClassServlet";
	document.form1.p_process.value = "EducationStudyingSubjectPage";
	document.form1.submit();

}
function pagesize(pageSize) {
	document.form1.target = "_self";
	document.form1.p_pageno.value = 1;
	document.form1.action = "/servlet/controller.study.MyClassServlet";
	document.form1.p_pagesize.value = pageSize;
	document.form1.p_process.value = "EducationStudyingSubjectPage";
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
    <input type="hidden" name="p_pageno" value="<%= v_pageno %>">
	<input type="hidden" name="p_pagesize"  value="<%=v_pagesize%>"/>
    <!-- 학습창 다시띄우기용 1:다시 안띄우기  2:다시띄우기(학습컨텐츠에서 닫았을경우) -->
    <input type='hidden' name='lessonRepopup' value="1">
    <input type='hidden' name='lessonReurl' value="">
    <input type='hidden' name='lessonReiurl' value="">
   <!-- <input type='hidden' name='wj_classkey' value="">-->
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
        <td><img src="/images/portal/homepage_renewal/myclass/stitle_02.gif" alt="수강중인 과정" /></td>
        <td class="h_road">Home &gt; 나의 강의실  &gt; <strong>수강중인 과정</strong></td>
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
        <td><img src="/images/portal/homepage_renewal/myclass/bar_01.jpg" width="672" height="104" /></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
    <!--타이틀부분//-->  
      
      <!--탭부분-->
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image55','','/images/portal/homepage_renewal/myclass/tab_on_001.gif',1)"><img src="/images/portal/homepage_renewal/myclass/tab_on_001.gif" name="Image55" id="Image55" /></a></td>
          <td><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image56','','/images/portal/homepage_renewal/myclass/tab_on_002.gif',1)"><img src="/images/portal/homepage_renewal/myclass/tab_off_002.gif" name="Image56" id="Image56" /></a></td>
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
          <td class="tit_table">과정명</td>
          <td class="tit_table">교육기간</td>
          <td class="tit_table">학습현황</td>
          <td class="tit_table_right">학습하기</td>
        </tr>
        
        <%
	v_tmp_subj = "";
	l = 0;
	for(i = 0; i < list1.size(); i++) {
		DataBox dbox    = (DataBox)list1.get(i);

		v_upperclass    =  dbox.getString("d_upperclass");
        v_isonoff       =  dbox.getString("d_isonoff");
		v_course        =  dbox.getString("d_course");
		v_cyear         =  dbox.getString("d_cyear");
		v_courseseq     =  dbox.getString("d_courseseq");
		v_coursenm      =  dbox.getString("d_coursenm");
		v_subj          =  dbox.getString("d_subj");
		v_year          =  dbox.getString("d_year");
		v_subjnm        =  dbox.getString("d_subjnm");
		v_subjseq       =  dbox.getString("d_subjseq");
		v_subjseqgr     =  dbox.getString("d_subjseqgr");
		v_edustart      =  dbox.getString("d_edustart");
		v_eduend        =  dbox.getString("d_eduend");
		v_eduurl        =  dbox.getString("d_eduurl");
		v_subjtarget    =  dbox.getString("d_subjtarget");
		v_isnewcourse   =  dbox.getString("d_isnewcourse");
		v_isoutsourcing =  dbox.getString("d_isoutsourcing");
		v_cpsubj        =  dbox.getString("d_cpsubj");
		v_cpsubjseq     =  dbox.getString("d_cpsubjseq");
		v_stuchkfinal   =  dbox.getString("d_stuchkfinal");
		v_company       =  dbox.getString("d_company");
		v_chkfinal      =  dbox.getString("d_chkfinal");
		v_upperclassnm  =  dbox.getString("d_upperclassnm");
		v_middleclassnm =  dbox.getString("d_middleclassnm");
		v_contenttype   =  dbox.getString("d_contenttype");
		v_biyong        =  dbox.getString("d_biyong");
		v_isuseYn       =  dbox.getString("d_isuseyn");
		v_isstudyyn     =  dbox.getString("d_isstudyyn");
        v_wj_classkey   =  dbox.getString("d_wj_classkey");

        v_dispnum	= dbox.getInt("d_dispnum");
        v_totalpage	= dbox.getInt("d_totalpage");
        		
		v_iscourseYn    = dbox.getString("d_isbelongcourse");
		v_subjcnt       = dbox.getInt("d_subjcnt");

        v_edustartdt   =  v_edustart.substring(0,8);
		v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
		v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");
		
		v_totalrowcount = dbox.getInt("d_totalrowcount");

		if(v_subjtarget.equals(""))     {   v_subjtarget    =   "전사원";   }

        // 학습url=외주인 경우..
        if(v_eduurl.equals("")) { v_ieduurl = 0; }else{ v_ieduurl = 1; }

        // 학습현황
        v_edulist_value = "/servlet/controller.contents.EduStart?p_process=eduList&p_subj="+ v_subj +"&p_year="+ v_year +"&p_subjseq="+v_subjseq+"&p_userid="+v_user_id;
        //학습URL?FIELD1=사번&FIELD2=교육년도&FIELD3=외주업체과정코드&FIELD4=외주업체과정차수
        //FIELD1="+v_userid+"&FIELD2="+v_gyear+"&FIELD3="+v_cpsubj+"&FIELD4="+v_cpsubjseq;
        //out.println(v_isoutsourcing.equals("Y"));

        if (v_isoutsourcing.equals("Y")) {                 //위탁과정일때

            v_eduurl = "/servlet/controller.contents.EduStart";
            v_eduurl += "?FIELD1=" + v_user_id + "&FIELD2=" + v_year + "&FIELD3=" + v_cpsubj + "&FIELD4=" + v_cpsubjseq + "&FIELD99=" + v_company+"&FIELD100=N"+ "&contenttype=" + v_contenttype+ "&p_subj=" + v_subj +"&p_year="+ v_year +"&p_subjseq="+v_subjseq;
        } else {//외부과정이 아닌경우
			 //직접 로직 흡수.. v_eduurl = EduEtc1Bean.make_eduURL(v_subj,v_year,v_subjseq);
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
//		v_controlstudy = control.allowStudy(box, v_subj, v_year, v_subjseq);
//		v_controlstudy = control.allowStudy(box);
%>
        
        <tr>
          <td class="ct_tit_table"><%= v_upperclassnm %></td>
          <td class="ct_tit_table_left"><a href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')"><%=v_subjnm%></a></td>
          <td class="ct_tit_table"><%= v_edustart %>~<%= v_eduend %></td>
          <td class="ct_tit_table">
	          <a href="javascript:whenEdulist('<%=v_subjnm%>','<%=v_ieduurl%>','','<%=v_edulist_value%>')" class="board_btn2">
	          	<img src="/images/portal/homepage_renewal/myclass/btn_view.jpg" alt="보기" />
	          </a>
          </td>
          <td class="ct_tit_table_right">
          	<% if (v_controlstudy >0) {         %>
                  <a href="javascript:controlstudy()" class="board_btn2"><span><img src="/images/portal/homepage_renewal/myclass/btn_study.jpg" alt="학습하기" /></span></a>
                <% } else {  
                		if ("Y".equals(v_isstudyyn)) {
                            if ("N000031".equals(v_grcode) && !v_wj_classkey.equals("")) {  %>
                                <a href="javascript:studyOpen2('<%=v_eduurl%>','<%=v_ieduurl%>', '<%=v_wj_classkey%>', '<%=v_edustartdt%>')" class="board_btn2"><span><img src="/images/portal/homepage_renewal/myclass/btn_study.jpg" alt="학습하기" /></span></a>
                     <%     } else {                        %>
                                <a href="javascript:studyOpen('<%=v_eduurl%>','<%=v_ieduurl%>')" class="board_btn2"><span><img src="/images/portal/homepage_renewal/myclass/btn_study.jpg" alt="학습하기" /></span></a>
                    <%
                            }
                		}
                   }%>
          </td>
        </tr>
        <%} %>
        <%
		if(i == 0){
%>
            <tr>
                <td colspan="5" align="center">학습중인 과정이 없습니다.</td>
            </tr>
              <%
        }
%>
        <tr>
          <td height="1" colspan="5" bgcolor="CACACA"></td>
        </tr>
        <tr>
          <td height="4" colspan="5" bgcolor="F7F7F7"></td>
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

			<h2><img src="/images/portal/myclass/h2_tit1.gif" alt="수강중인과정" class="fl_l" /><p class="category">Home > 나의강의실 > <strong>수강중인과정</strong></p></h2>
    
			<%--<p class="myclass_tab cltxt_1"><img src="/images/portal/myclass/myclass_online_tab1_on.gif" alt="온라인과정" /><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage"><img src="/images/portal/myclass/myclass_online_tab2_off.gif" alt="오프라인과정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></p>--%>
            <img src="/images/portal/myclass/classing_borimg.gif" alt="" style="width:681px;background-position: left 18px;background-repeat: no-repeat;">

			<ul class="tabwrap">
                <li style="color: red">※ 과정 학습 시에는 인터넷 익스플로러 사용을 권장합니다.</li>
            </ul>
            <p style="line-height:7px">&nbsp;</p>
    <% if(grcode.equals("N000001")) {%>
            <img src="/images/portal/btn/btn_online_on.gif" alt="온라인과정"><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage"><img src="/images/portal/btn/btn_offline_off.gif" alt="온프라인과정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
    <%}%>
			<table class="board_list">
			<colgroup><!-- <col width="90px" /> --><col width="310px" /><col width="140px" /><col width="70px" /><col width="85px" /></colgroup>
			<thead>
			<tr class="myclass">
				<!-- 숨김처리 rsg20180130 -->
				<!-- 
				<th><p><kocca_select:select name="p_upperclass" sqlNum="upperclass"  param=" "
	              onChange="selectList();" attr="분류" selectedValue="<%= v_upperclass %>" isLoad="true" all="none" type="3"/>
                </p></th>
                 -->
				<th><p>과정명</p></th>
				<th><p>교육기간</p></th>
				<th><p>학습현황</p></th>
				<th class="end"><p>학습하기</p></th>
			</tr>
			</thead>
			<tbody>
<%
	v_tmp_subj = "";
	l = 0;
	for(i = 0; i < list1.size(); i++) {
		DataBox dbox    = (DataBox)list1.get(i);

		v_upperclass    =  dbox.getString("d_upperclass");
        v_isonoff       =  dbox.getString("d_isonoff");
		v_course        =  dbox.getString("d_course");
		v_cyear         =  dbox.getString("d_cyear");
		v_courseseq     =  dbox.getString("d_courseseq");
		v_coursenm      =  dbox.getString("d_coursenm");
		v_subj          =  dbox.getString("d_subj");
		v_year          =  dbox.getString("d_year");
		v_subjnm        =  dbox.getString("d_subjnm");
		v_subjseq       =  dbox.getString("d_subjseq");
		v_subjseqgr     =  dbox.getString("d_subjseqgr");
		v_edustart      =  dbox.getString("d_edustart");
		v_eduend        =  dbox.getString("d_eduend");
		v_eduurl        =  dbox.getString("d_eduurl");
		v_subjtarget    =  dbox.getString("d_subjtarget");
		v_isnewcourse   =  dbox.getString("d_isnewcourse");
		v_isoutsourcing =  dbox.getString("d_isoutsourcing");
		v_cpsubj        =  dbox.getString("d_cpsubj");
		v_cpsubjseq     =  dbox.getString("d_cpsubjseq");
		v_stuchkfinal   =  dbox.getString("d_stuchkfinal");
		v_company       =  dbox.getString("d_company");
		v_chkfinal      =  dbox.getString("d_chkfinal");
		v_upperclassnm  =  dbox.getString("d_upperclassnm");
		v_middleclassnm =  dbox.getString("d_middleclassnm");
		v_contenttype   =  dbox.getString("d_contenttype");
		v_biyong        =  dbox.getString("d_biyong");
		v_isuseYn       =  dbox.getString("d_isuseyn");
		v_isstudyyn     =  dbox.getString("d_isstudyyn");
        v_wj_classkey   =  dbox.getString("d_wj_classkey");

        v_dispnum	= dbox.getInt("d_dispnum");
        v_totalpage	= dbox.getInt("d_totalpage");
        		
		v_iscourseYn    = dbox.getString("d_isbelongcourse");
		v_subjcnt       = dbox.getInt("d_subjcnt");

        v_edustartdt   =  v_edustart.substring(0,8);
		v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
		v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");

		if(v_subjtarget.equals(""))     {   v_subjtarget    =   "전사원";   }

        // 학습url=외주인 경우..
        if(v_eduurl.equals("")) { v_ieduurl = 0; }else{ v_ieduurl = 1; }

        // 학습현황
        v_edulist_value = "/servlet/controller.contents.EduStart?p_process=eduList&p_subj="+ v_subj +"&p_year="+ v_year +"&p_subjseq="+v_subjseq+"&p_userid="+v_user_id;
        //학습URL?FIELD1=사번&FIELD2=교육년도&FIELD3=외주업체과정코드&FIELD4=외주업체과정차수
        //FIELD1="+v_userid+"&FIELD2="+v_gyear+"&FIELD3="+v_cpsubj+"&FIELD4="+v_cpsubjseq;
        //out.println(v_isoutsourcing.equals("Y"));

        if (v_isoutsourcing.equals("Y")) {                 //위탁과정일때

            v_eduurl = "/servlet/controller.contents.EduStart";
            v_eduurl += "?FIELD1=" + v_user_id + "&FIELD2=" + v_year + "&FIELD3=" + v_cpsubj + "&FIELD4=" + v_cpsubjseq + "&FIELD99=" + v_company+"&FIELD100=N"+ "&contenttype=" + v_contenttype+ "&p_subj=" + v_subj +"&p_year="+ v_year +"&p_subjseq="+v_subjseq;
        } else {//외부과정이 아닌경우
			 //직접 로직 흡수.. v_eduurl = EduEtc1Bean.make_eduURL(v_subj,v_year,v_subjseq);
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
//		v_controlstudy = control.allowStudy(box, v_subj, v_year, v_subjseq);
//		v_controlstudy = control.allowStudy(box);
%>
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<!-- 
				<td><%= v_upperclassnm %></td>
				 -->
				<td class="ta_l"><a style="padding-left:30px;" href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')"><%=v_subjnm%></a></td>
				<td class="num"><%= v_edustart %>~<%= v_eduend %></td>
				<td><a href="javascript:whenEdulist('<%=v_subjnm%>','<%=v_ieduurl%>','','<%=v_edulist_value%>')" class="board_btn2"><span>보기</span></a></td>
				<td>
                <% if (v_controlstudy >0) {         %>
                  <a href="javascript:controlstudy()" class="board_btn2"><span>학습하기</span></a>
                <% } else {  
                		if ("Y".equals(v_isstudyyn)) {
                            if ("N000031".equals(v_grcode) && !v_wj_classkey.equals("")) {  %>
                                <a href="javascript:studyOpen2('<%=v_eduurl%>','<%=v_ieduurl%>', '<%=v_wj_classkey%>', '<%=v_edustartdt%>')" class="board_btn2"><span>학습하기</span></a>
                     <%     } else {                        %>
                                <a href="javascript:studyOpen('<%=v_eduurl%>','<%=v_ieduurl%>', <%= dbox.getString("d_prewidth") %>, <%= dbox.getString("d_preheight") %> )" class="board_btn2"><span>학습하기</span></a>
                    <%
                            }
                		}
                   }%>
                </td>
			</tr>
<%
	}
%>
<%
		if(i == 0){
%>
            <tr>
                <td colspan="5">학습중인 과정이 없습니다.</td>
            </tr>
              <%
        }
%>
            <input  type='hidden' name='p_totCount1' value='<%=(i-1)%>'>

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