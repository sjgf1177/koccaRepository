<%
//**********************************************************
//  1. 제      목: SUBJECT PREVIEW PAGE
//  2. 프로그램명: gu_SubjectPreviewOn.jsp
//  3. 개      요: 과정안내
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 01.12
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.lang.String" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "memberBean" class = "com.credu.system.MemberAdminBean"  scope = "page" />
<jsp:useBean id = "code" class = "com.credu.system.CodeConfigBean"  scope = "page" />
<jsp:useBean id = "probean" class = "com.credu.propose.ProposeBean"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅
    String upload_url = conf.getProperty("url.upload");

    String  v_process      = box.getString("p_process");
    String  v_rprocess     = box.getString("p_rprocess");
    String  v_subj         = box.getString("p_subj");
    String  v_subjnm       = box.getString("p_subjnm");
    String  v_isonoff      = box.getString("p_isonoff");
    String  v_upperclassnm = box.getString("p_upperclassnm");
    String  v_upperclass   = box.getString("p_upperclass");
    String  v_select       = box.getString("p_select");
    String  v_actionurl    = box.getString("p_actionurl");
    String  v_lsearchsubject = box.getString("p_lsearchsubject");
    String  v_gubun       = box.getString("p_gubun");

    String  v_tabnum       = box.getStringDefault("p_tabnum","design");
    String  v_iscourseYn   = box.getString("p_iscourseYn");
	String  v_tem_grcode   = box.getSession("tem_grcode");

	String  v_isevent   = box.getSession("isevent");

    String  v_subjseq   =  "";
    String  v_year      =  "";
    String  v_preurl    =  "";
    String  v_sdesc     =  "";
    String  v_propstart =  "";
    String  v_propend   =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_place     =  "";
    String  v_tutor     =  "";
    String  v_muserid   =  "";
    String  v_mastermail=  "";
    String  v_mastertel =  "";
    String  v_isgoyong  =  "";
    String  v_subjtarget=  "";
    String  v_edumans   =  "";
    String  v_intro     =  "";
    String  v_explain   =  "";
    String  v_memo      =  "";
    String  v_isonoff_value="";
    String  v_prop_img  =  "";
    String  v_isgoyong_value="";
    String  v_edutype   =  "";
    String  v_masteruser=  "";
    String  v_env       =  "";
    String  v_owner     =  "";  //소유주코드
    String  v_ownerman  =  "";  //크래듀운영자명
    String  v_ownertel  =  "";  //담당자 전화번호
    String  v_isessential = ""; // 과정구분
    String  v_purl        = "";
    String  v_introducefilenamereal = "";
    String  v_introducefilenamenew  = "";
    String  v_introducefilenamenew_view  = "";
    String  v_contenttype  = "";
    String  v_studentlimitch="";

    String v_mname      = "";   // 강사명
    String v_career     = "";   // 강사경력
    String v_book       = "";   // 강사저서

    String v_isnew          = "";
    String v_ishit          = "";
    String v_isrecom        = "";
    String v_imgNew         = "";
    String v_imgHit         = "";
    String v_imgRecom       = "";

    int     v_studentlimit =0;
    int     v_biyong    =  0;
    int     v_wstep     =  0;
    int     v_wmtest    =  0;
    int     v_wftest    =  0;
    int     v_wreport   =  0;
    int     v_wact      =  0;
    int     v_wetc1     =  0;
    int     v_wetc2     =  0;
    int     v_Twetc     =  0;
    int     v_stepcnt   =  0;
    int     v_testcnt   =  0;
    int     v_reportcnt =  0;
    int     v_actcnt    =  0;
    int     v_gradscore =  0;
    int     v_gradstep  =  0;
    int     v_edudays   =  0;
    int     v_edutimes  =  0;
    int     v_ratewbt   =  0;
    int     v_ratevod   =  0;
    int     v_score     =  0;   // 학점배점
    int     v_propcnt   = 0;
    String  v_isessential_view = "";
    String  v_lesson    =  "";
    String  v_lecture   =  "";
    String  v_lecturedate= "";
    String  v_biyong_view = "";
    String  v_studentlimit_view  = "";
    String  v_isoutsourcing  = "";
    String  v_conturl        = "";
	String  v_newphoto		= "";
	String	v_isunit		= "";
	String v_newphoto_view = "";
    int     i   = 0;
    int		k	= 0;

    int		v_prewidth = 0; //맛보기 창 width
    int	 	v_preheight = 0; //맛보기 창 height

    v_preurl               =  EduEtc1Bean.make_previewURL(v_subj);
    String v_hasPreviewObj = EduEtc1Bean.hasPreviewObj(v_subj);

    String  s_userid = box.getSession("userid");
    String  s_jikwi  = box.getSession("jikwi");
    String  v_prop_check = "";

    String v_classname  = "";
    String v_eduperiod  = "";


    Hashtable outputData = new Hashtable();
    DataBox dbox = (DataBox)request.getAttribute("subjectPreview");

    v_edumans       = dbox.getString("d_edumans");
    v_subj          = dbox.getString("d_subj");
    v_subjtarget    = dbox.getString("d_subjtarget");
    v_intro         = dbox.getString("d_intro");
    v_explain       = dbox.getString("d_explain");
    v_memo          = dbox.getString("d_memo");
    v_owner         = dbox.getString("d_owner");
    v_ownerman      = dbox.getString("d_ownerman");
    v_ownertel      = dbox.getString("d_ownertel");
    v_muserid       = dbox.getString("d_muserid");
    v_preurl        = dbox.getString("d_preurl");
    v_mastertel     = dbox.getString("d_musertel");
    v_isoutsourcing = dbox.getString("d_isoutsourcing");
    v_conturl       = dbox.getString("d_conturl");
    v_contenttype   = dbox.getString("d_contenttype");
    v_isgoyong      = dbox.getString("d_isgoyong");
    v_isunit		= dbox.getString("d_isunit");
	v_newphoto		= dbox.getString("d_newphoto");

    v_mname         = dbox.getString("d_name");     //강사명
    v_career        = dbox.getString("d_career");
    v_book          = dbox.getString("d_book");
    v_classname     = dbox.getString("d_classname");
    v_eduperiod     = dbox.getString("d_eduperiod");

    v_prewidth		= dbox.getInt("d_prewidth");
    v_preheight		= dbox.getInt("d_preheight");

	v_isnew         = dbox.getString("d_isnew");
    v_ishit         = dbox.getString("d_ishit");
    v_isrecom       = dbox.getString("d_isrecom");

    if(v_isnew.equals("Y"))    v_imgNew = "&nbsp;<img src='/images/user/button/btn_snew.gif' align='absmiddle'>";
    else                       v_imgNew = "";
    if(v_ishit.equals("Y"))    v_imgHit = "&nbsp;<img src='/images/user/button/btn_shit.gif' align='absmiddle'>";
    else                       v_imgHit = "";
    if(v_isrecom.equals("Y"))  v_imgRecom = "&nbsp;<img src='/images/user/button/btn_srecom.gif' align='absmiddle'>";
    else                       v_imgRecom = "";

    v_isonoff_value  = (v_isonoff.equals("ON"))?"사이버":"집합";
    v_intro          =  StringManager.replace(v_intro,"\r\n","<br>");
    v_explain        =  StringManager.replace(v_explain,"\r\n","<br>");
    v_mastertel      =  StringManager.replace(v_mastertel,"\r\n","<br>");

    v_career      =  StringManager.replace(v_career,"\r\n","<br><img src='/images/user/game/apply/apply_bl.gif' width='9' height='8'>&nbsp;");
    v_book      =  StringManager.replace(v_book,"\r\n","<br><img src='/images/user/game/apply/apply_bl.gif' width='9' height='8'>&nbsp;");

    v_isgoyong        = dbox.getString("d_isgoyong");
    if (v_isgoyong.equals("Y")) v_isgoyong_value = "적용";
    else                        v_isgoyong_value = "미적용";
    v_introducefilenamereal = dbox.getString("d_introducefilenamereal");
    v_introducefilenamenew  = dbox.getString("d_introducefilenamenew");

	if (v_introducefilenamenew.equals("")) v_introducefilenamenew_view = "/images/user/apply/le_img.jpg";
    else                                   v_introducefilenamenew_view = upload_url + "bulletin/" + v_introducefilenamenew;

	if (v_newphoto.equals("")) v_newphoto_view = "/images/user/game/course/blank_tutor.gif";
    else                       v_newphoto_view = upload_url + "bulletin/" + v_newphoto;
	//v_owner = getCodenm.get_owner(v_subj);

    if (v_owner.equals("credu")) {                 //크레듀
       v_masteruser        = v_ownerman;
    } else {
       outputData = probean.getMeberInfo(v_muserid);
       v_masteruser = (String)outputData.get("name");
       v_mastermail = (String)outputData.get("email");
       //v_masteruser        = memberBean.getUserInfo(v_muserid);
    }


	ArrayList list = (ArrayList)request.getAttribute("subjseqList");
	//ArrayList list2 = (ArrayList)request.getAttribute("selectSubjChasiList");
	ArrayList list2 = (ArrayList)request.getAttribute("lessonList");

	String v_reviewdays = "";
    String v_reviewtype = "";
    String v_reviewtypedesc = "";

    v_reviewdays = dbox.getString("d_reviewdays");
    v_reviewtype = dbox.getString("d_reviewtype");

    if (v_reviewdays.equals("")) v_reviewdays = "0";

    if (v_reviewtype.equals("D")) v_reviewtypedesc = "일";
    else if (v_reviewtype.equals("W")) v_reviewtypedesc = "주";
    else if (v_reviewtype.equals("M")) v_reviewtypedesc = "월";
    else if (v_reviewtype.equals("Y")) v_reviewtypedesc = "년";
    else v_reviewtypedesc = "";



	// 이벤트 부분 start
	String currentDate = new java.text.SimpleDateFormat("yyyyMMdd", new java.util.Locale("en", "US")).format(new java.util.Date());
	int iCurrentDate = Integer.parseInt(currentDate);
	// 이벤트 부분 end
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<SCRIPT LANGUAGE="JavaScript">
<!--
	//LIST
	function whenList(){
	    document.form1.target = "_self";
	    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
	    document.form1.p_process.value = 'SubjectList';
	    document.form1.p_rprocess.value = '';
self.location.href = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList";
	    //document.form1.submit();
	}

    // 과정검색 엔터처리
    function search_enter(e)  {
        if (e.keyCode =='13'){  subjSearch();  }
    }

    // 과정검색
    function subjSearch() {
        if (document.form1.p_lsearchtext.value=="") {
            alert("검색어를 입력해주세요");
            return;
        }
        document.form1.action = "/servlet/controller.propose.MainSubjSearchServlet";   //
        document.form1.p_process.value = "SubjSearch";
        document.form1.submit();
    }


	// 인기과정
	function whenSubjBest(){
		document.form1.target = "_self";
		document.form1.p_process.value= "SubjectListBest";
		document.form1.action= "/servlet/controller.propose.ProposeCourseServlet";
		document.form1.submit();
	}



	// 전체과정보기 (이전화면)
	function whenSubjList(){

		document.form1.target = "_self";
		document.form1.p_process.value=document.form1.p_rprocess.value;
		document.form1.action=document.form1.p_actionurl.value;
		//alert(document.form1.p_actionurl.value);
		document.form1.submit();
	}

	// 차수정보조회
	function whenSelSubjInfo(subjseq,year)
	{
		var url = "/servlet/controller.propose.ProposeCourseServlet";

		var new_Open = window.open(url,"seqInfo",'scrollbars=yes,width=680,height=600,resizable=no');
		document.form1.target = "seqInfo";
		document.form1.p_process.value = "SeqPreviewPage";
		document.form1.p_subjseq.value = subjseq;
		document.form1.p_year.value = year;
		document.form1.submit();
	}


	//수강신청
	function whenSubjPropose(subj,year,subjseq, subjnm, billYn) {

	   if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")){
		 return;
	   }
	   if(billYn == "Y")
		{
			document.form1.p_process.value = "SubjectEduBill";
		}
		else
		{
			document.form1.p_process.value = "SubjectEduPropose";
		}

	   document.form1.target = "_self";
	   document.form1.p_subj.value = subj;
	   document.form1.p_year.value = year;
	   document.form1.p_subjseq.value = subjseq;
	   document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
	   document.form1.submit();
	}

	//장바구니 담기
	function whenSubjBasket(subj,year,subjseq, subjnm, billYn, price) {

	   if(!confirm(subjnm+" 과정을 장바구니에 담으시겠습니까?")){
		 return;
	   }

	   document.form1.target = "_self";
	   document.form1.p_subj.value = subj;
	   document.form1.p_year.value = year;
	   document.form1.p_subjseq.value = subjseq;
	   document.form1.p_price.value = price;
	   document.form1.p_process.value = "InsertBasketSubj";
	   document.form1.action = "/servlet/controller.account.AccountManagerServlet";
	   document.form1.submit();
	}

	//장바구니 담기(수강신청)
	function whenSubjBasket2(subj,year,subjseq, subjnm, billYn, price) {

	   if(!confirm(subjnm+" 과정을 수강신청 하시겠습니까?")){
		 return;
	   }

	   document.form1.target = "_self";
	   document.form1.p_subj.value = subj;
	   document.form1.p_year.value = year;
	   document.form1.p_subjseq.value = subjseq;
	   document.form1.p_price.value = price;
	   document.form1.p_process.value = "InsertBasketSubj";
	   document.form1.action = "/servlet/controller.account.AccountManagerServlet";
	   document.form1.submit();
	}

	//관심과정 등록
	function whenSubjPropose2(subj,year,subjseq) {

	   if(!confirm("관심과정으로 등록 하시겠습니까?")){
		 return;
	   }

	   document.form1.target = "_self";
	   document.form1.p_subj.value = subj;
	   document.form1.p_year.value = year;
	   document.form1.userid.value = "<%=s_userid%>";
	   document.form1.p_subjseq.value = subjseq;
	   document.form1.p_process.value = "InterestInsert";
	   document.form1.action = "/servlet/controller.study.InterestServlet";
	   document.form1.submit();
	}


	//콘텐츠 평가보기
	function ContentResearchInfo(){
	  //zu_ContentRearch_L.jsp
	  document.form1.target = "_self";
	  document.form1.p_process.value = "ContentResearch";
	  document.form1.action="/servlet/controller.propose.ProposeCourseServlet";
	  document.form1.submit();
	}

	//나의강의실
	function goPersonHistory(){
	  document.form1.target = "_self";
	  document.form1.p_process.value = "EducationSubjectPage";
	  document.form1.action="/servlet/controller.study.MyClassServlet";
	  document.form1.submit();
	}

	//강의목차보기
	function whenLectureList(isoutsourcing, conturl){
	   window.self.name = "LectureList";
	   if(isoutsourcing == "N"){
		 var url = "/servlet/controller.propose.ProposeCourseServlet";
		 var new_Open = window.open(url,"LectureList1",'scrollbars=yes,width=746,height=550,resizable=no');
		 document.form1.target="LectureList1";
		 document.form1.p_process.value="LectureList";
		 //document.form1.action="/servlet/controller.propose.ProposeCourseServlet";
		 document.form1.submit();
	   }else{
		  if (conturl == '') {
			alert("강의목차정보가 없습니다.");
		 }
		 url = conturl;
		 var new_Open = window.open(url,"LectureList1",'scrollbars=yes,width=800,height=600,resizable=no');
	   }
	}

	// 맛보기
	function whenPreShow(url, subj) {
		// 로그
		prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
		//open_window("openShow",prelog_url,"100","100","800","717","","","",true,true);

		open_window("openShow",url,"100","100","<%=v_prewidth%>","<%=v_preheight%>","","","",true,true);
	}


	// 맛보기
	function studyOpen(url,subj) {
		// 로그
		prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
		//open_window("openShow",prelog_url,"0","0","10","10","","","",true,true);

		open_window('openApp',url,0,0,<%=v_prewidth%>,<%=v_preheight%>,false,false,true,true,true);
	}
//-->
</SCRIPT>


<form name="form1" method="post" >
    <input type='hidden' name='p_process' value="<%=v_process%>" >
    <input type='hidden' name='p_select' value="<%=v_select%>">
    <input type='hidden' name='p_gubun' value="<%=v_gubun%>">
    <input type="hidden" name="p_billYn">

    <input type='hidden' name='p_subj' value="<%=v_subj%>">
    <input type='hidden' name='p_year'>
    <input type='hidden' name='userid'>
    <input type='hidden' name='p_subjseq'>
    <input type='hidden' name='p_price'>
    <input type='hidden' name='p_subjnm' value="<%=v_subjnm%>">
    <input type='hidden' name='p_rprocess' value="<%=v_rprocess%>">
    <input type='hidden' name='p_actionurl' value="<%=v_actionurl%>">
    <input type='hidden' name='p_purl' value="<%=v_purl%>">
    <input type='hidden' name='p_course' value="000000">
    <input type='hidden' name='p_isonoff' value="<%=v_isonoff%>">
    <input type='hidden' name='p_upperclass' value="<%=v_upperclass%>">
    <input type='hidden' name='p_upperclassnm' value="<%=v_upperclassnm%>">
    <input type='hidden' name='p_lsearchsubject' value="<%=v_lsearchsubject%>">
    <input type='hidden' name='p_returnurl' value="/servlet/controller.propose.ProposeCourseServlet">

    <input type='hidden' name='p_iscourseYn' value='<%=v_iscourseYn%>'>
    <input type='hidden' name='p_tabnum' value='<%=v_tabnum%>'>

    <!-- title -->
        <div style="width:720px;height:35px;margin-bottom:20px;background-image:url('/images/user/game/apply/<%=tem_subimgpath%>/title02.gif');text-align:right;padding:19px 25px 0px 0px;font-size:11px;color:#000000;">
            <img src="/images/user/game/apply/<%=tem_subimgpath%>/blt01.gif"> Home &gt; 수강신청 &gt; 수강신청
        </div>
        <!-- //title -->

        <!-- 수강신청확인/취소 -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="/images/user/game/apply/apply_tutortop.jpg"><!--img src="/images/user/game/apply/bg_intro_turto.gif"--></td>
  </tr>
  <tr>
    <td align="center" valign="top" background="/images/user/game/apply/apply_tutorback.jpg">
    <table width="680" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="122" valign="top">
          <!-- 강사사진 -->
          <!--table width="102" border="2" cellspacing="0" cellpadding="2" style="border-collapse:collapse;" bordercolor="#cccccc">
              <tr>
                <td bgcolor="#FFFFFF"><img src="<%=v_newphoto_view%>" width="96" height="94"><//--img src="/images/user/game/apply/photo_tutor01.jpg"--//></td>
              </tr>
          </table-->
          <table cellspacing="0" cellpadding="0" border="0">
              <tr>
                <td style="padding-left:13px;padding-bottom:7px;"><B>교수 : <%=v_mname%></b></td>
              </tr>
          </table>
          <!--/td>
          <td width="330" valign="top"-->
          <table width="420" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="56" valign="top" style="padding-left:7px"><img src="/images/user/game/apply/apply_thistory.gif"></td>
                <td width="364" style="padding-left:7px">
                <!-- 약력 -->
                <table width="620" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="620" class="line-height" style="padding-left:5"><img src="/images/user/game/apply/apply_bl.gif" width="9" height="8"> <%=v_career%></td>
                    </tr>
                    <tr>
                      <td class="font_apply">&nbsp;</td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            </td>
          <!--td width="228" valign="top">
          <table width="225" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="52" valign="top"><img src="/images/user/game/apply/apply_tbook.gif" width="46" height="16"></td>
                <td width="173">
                <//!-- 저서 --//>
                <table width="170" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="179" class="font_apply"><img src="/images/user/game/apply/apply_bl.gif" width="9" height="8"> <%=v_book%></td>
                    </tr>
                    <tr>
                      <td class="font_apply">&nbsp;</td>
                    </tr>
                  </table></td>
              </tr>
            </table></td-->
        </tr>
      </table></td>
  </tr>
  <tr>
    <td><img src="/images/user/game/apply/apply_tutorbo.gif"><!--img src="/images/user/game/apply/bar_intro_bo.gif"--></td>
  </tr>
</table>
            <table cellpadding="0" cellspacing="0" width="720">
                <tr>
                    <td><img src="/images/user/game/apply/apply02_st01.gif"></td>
                </tr>
                <tr height="10"><td></td></tr>
            </table>

            <table cellpadding="0" cellspacing="0" border=0 width="720">
            <col width="87"><col width="4"><col width="630">
            <tr height="30" valign="middle">
                <td width="76" style="padding:0 0 4 10;color:#000000;">과정명</td>
                <td width="1"><img src="/images/user/game/apply/tbl_bar.gif"></td>
                <!--td style="padding:0 0 4 10;color:#37669a;font-weight:bold;"></td-->

                <td width="620">
	                <TABLE cellpadding="0" cellspacing="0" width="620">
					<TR>
						<TD>
							<%=v_subjnm%> <%if(v_isunit.equals("Y")) { %><img src="/images/user/game/course/icon01.gif" width="38" height="15" align="absmiddle"> <% } %> <%if(v_isgoyong.equals("Y")) { %><img src="/images/user/game/course/icon02.gif" width="38" height="15" align="absmiddle"><% } %><%=v_imgNew%><%=v_imgHit%><%=v_imgRecom%>
						</TD>
						<TD align="right">
						<% if (v_hasPreviewObj.equals("Y")) { %><a href="javascript:studyOpen('/servlet/controller.contents.EduStart?p_subj=<%=v_subj%>&p_year=2000&p_subjseq=0001&p_ispreview=Y','<%=v_subj%>')" onfocus=this.blur()><img src="/images/user/kocca/button/b_course_pre.gif" border="0"></a><% } else { %><% if(!v_preurl.equals("")){ %><a href="javascript:whenPreShow('<%=v_preurl%>','<%=v_subj%>')" onfocus=this.blur()><img src="/images/user/kocca/button/b_course_pre.gif" border="0"></a><%}%><% } %>
						<!--&nbsp;
							<a href="javascript:whenSubjPropose2('<%=v_subj%>', '<%=v_subjnm%>')" onfocus=this.blur()><img src='/images/user/kocca/button/b_choise_course.gif' border=0></a>-->
						<span id="orderCart"></span>
						&nbsp;
							<a href="javascript:whenList();"><img src="/images/user/game/button/b_course_list.gif" border="0"></a>
						</TD>
					</TR>
					</TABLE>
				</td>

            </tr>
            <tr height="1"><td colspan="4" background="/images/user/game/apply/tbl_dot.gif"></td></tr>
            <tr height="30" valign="middle">
                <td style="padding:0 0 4 10;color:#000000;">과정분류명</td>
                <td><img src="/images/user/game/apply/tbl_bar.gif"></td>
                <td colspan=2 style="padding:0 0 4 10;"><%=v_classname%></td>
            </tr>
            <tr height="1"><td colspan="4" background="/images/user/game/apply/tbl_dot.gif"></td></tr>
            <tr height="30" valign="middle">
                <td style="padding:0 0 4 10;color:#000000;">교육구분</td>
                <td><img src="/images/user/game/apply/tbl_bar.gif"></td>
                <td colspan=2 style="padding:0 0 4 10;">사이버<%//=v_isonoff_value%></td>
            </tr>
            <tr height="1"><td colspan="4" background="/images/user/game/apply/tbl_dot.gif"></td></tr>
            <tr height="30" valign="middle">
                <td style="padding:0 0 4 10;color:#000000;">교육목적</td>
                <td><img src="/images/user/game/apply/tbl_bar.gif"></td>
                <td colspan=2 style="padding:8 0 4 10;"><%=v_intro%></td>
            </tr>
            <tr height="1"><td colspan="4" background="/images/user/game/apply/tbl_dot.gif"></td></tr>
            <tr height="30" valign="middle">
                <td style="padding:0 0 4 10;color:#000000;">교육내용</td>
                <td><img src="/images/user/game/apply/tbl_bar.gif"></td>
                <td colspan=2 style="padding:8 0 4 10;"><%=v_explain%></td>
            </tr>
            <tr height="1"><td colspan="3" background="/images/user/game/course/tbl_dot.gif"></td></tr>
  <tr height="30" valign="middle">
    <td style="padding:0 0 4 10;color:#000000;">선수과목</td>
    <td><img src="/images/user/game/course/vline03.gif" width="1" height="5"></td>
    <td class="tbl_bleft">
<%
	//선수과목
	ArrayList listPre = (ArrayList)request.getAttribute("subjectPre");
	//후수과목
	ArrayList listNext = (ArrayList)request.getAttribute("subjectNext");
	if (listPre == null || listPre.size()==0) {
    		out.println("선수과목이 없습니다.");
    	} else {
    		for(int p = 0; p < listPre.size(); p++) {
       			DataBox dboxp = (DataBox)listPre.get(p);
       			String v_subjnmp = dboxp.getString("d_subjnm");
       			out.println(v_subjnmp);
       			out.println("<br>");
        	}
        }
    %>
    </td>
  </tr>
  <tr height="1"><td colspan="3" background="/images/user/game/course/tbl_dot.gif"></td></tr>
  <tr height="30" valign="middle">
    <td style="padding:0 0 4 10;color:#000000;">후수과목</td>
    <td><img src="/images/user/game/course/vline03.gif" width="1" height="5"></td>
    <td class="tbl_bleft">
    <%  if (listNext == null || listNext.size()==0) {
    		out.println("후수과목이 없습니다.");
    	} else {
    		for(int n = 0; n < listNext.size(); n++) {
       			DataBox dboxn = (DataBox)listNext.get(n);
       			String v_subjnmn = dboxn.getString("d_subjnm");
       			out.println(v_subjnmn);
       			out.println("<br>");
        	}
        }
    %>

    </td>
  </tr>

            <tr height="1"><td colspan="4" background="/images/user/game/apply/tbl_dot.gif"></td></tr>
            <tr height="30" valign="middle">
                <td style="padding:0 0 4 10;color:#000000;">교육기간</td>
                <td><img src="/images/user/game/apply/tbl_bar.gif"></td>
                <td colspan=2 style="padding:0 0 4 10;"><%=v_eduperiod%>주 <% if (!v_reviewtypedesc.equals("")) { %>(복습기간 <%=v_reviewdays%><%=v_reviewtypedesc%>)<%}%></td>
            </tr>
            <tr height="1"><td colspan="4" background="/images/user/game/apply/tbl_dot.gif"></td></tr>
            <tr height="30" valign="middle">
                <td style="padding:0 0 4 10;color:#000000;">강의교재<br>및 <br>참고문헌</td>
                <td><img src="/images/user/game/apply/tbl_bar.gif"></td>
                <td colspan=2 style="padding:0 0 4 10;"><%=v_memo.replaceAll("\n","<br/>")%></td>
            </tr>
            <tr height="4"><td colspan="4" bgcolor="#d9e5f3"></td></tr>
            </table>

            <br>
            <table cellpadding="0" cellspacing="0" width="720">
                <tr>
                    <td><img src="/images/user/game/apply/apply02_st02.gif"></td>
                </tr>
            </table>
            <br>
<!-- 차시 시작-->
          <table width="720" border="2" cellspacing="0" cellpadding="3" style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
            <tr>
              <td height="3" colspan="5" class="linecolor_app"></td>
            </tr>
            <tr>
              <td width="40" class="tbl_gtit2">차시</td>
              <!--td width="121" class="tbl_gtit2">강의시작일</td>
              <td width="119" class="tbl_gtit">강의종료일</td-->
              <td width="293" class="tbl_gtit2">강의내용<br></td>
              <td width="103"  class="tbl_gtit">담당교수</td>
            </tr>
            <tr>
              <td height="5" colspan="5" ></td>
            </tr>
            <%
            String v_desc  = "";
            String v_fromdate  = "";
            String v_todate  = "";

            int l = 0;

			for(k = 0; k < list.size(); k++)
            {
                DataBox dbox1 = (DataBox)list.get(k);

                v_edustart           = dbox1.getString("d_edustart");
                v_eduend             = dbox1.getString("d_eduend");
			}

            for(l = 0; l < list2.size(); l++)
            {
                DataBox dbox2 = (DataBox)list2.get(l);

                v_subjseq   = dbox2.getString("d_lesson");
                v_desc      = dbox2.getString("d_sdesc");
                v_fromdate  = dbox2.getString("d_fromdate");
                v_todate    = dbox2.getString("d_todate");

				//날짜 처리
                if(v_fromdate.equals("0")){v_fromdate = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");}
                else{v_fromdate     = FormatDate.getFormatDate(v_fromdate,"yyyy/MM/dd");}

                if(v_todate.equals("0")){v_todate = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");}
                else{v_todate = FormatDate.getFormatDate(v_todate,"yyyy/MM/dd");}

                %>
            <tr>
              <td class="tbl_grc"><%=v_subjseq%></td>
              <!--td class="tbl_grc">학습시작일 <%=v_fromdate%> 후 부터</td>
              <td class="tbl_grc">학습시작일 <%=v_todate%> 전 부터</td-->
              <td class="tbl_bleft"><%=v_desc%></td>
              <td class="tbl_grc"><%=v_mname + "(" + v_muserid + ")"%></td>
            </tr>
<%          }
            if(l == 0)
            { %>
                <tr>
                    <td class="tbl_grc" align="center" colspan="5">해당 차시가 없습니다.</td>
                </tr>
<%          }

                %>
          </table>
          <br>
<!-- 차시 끝 -->
            <table cellpadding="0" cellspacing="0" width="720">
                <tr>
                    <td><img src="/images/user/game/apply/apply02_st03.gif"></td>
                </tr>
            </table>
          <br>
<!-- 차수 시작-->

          <table width="720" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
            <tr  class="lcolor">
              <td height="3" colspan="6" class="linecolor_app"></td>
            </tr>
            <tr>
              <td width="32" class="tbl_gtit2">차수</td>
              <td width="185" class="tbl_gtit2">수강신청기간</td>
              <td width="190" class="tbl_gtit">교육기간</td>
              <td width="74" class="tbl_gtit2">정원</td>
              <td width="79"  class="tbl_gtit">교육비</td>
              <td width="108"  class="tbl_gtit">신청</td>
            </tr>
            <tr>
              <td height="5" colspan="6" ></td>
            </tr>
            <%
            String v_ispropose  = "";
            String v_statusString = "";
            String v_subjseqgr = "";
            String v_today = "";
			String v_billYn	= "";
            int    v_iedustart = 0;

            String v_innerHtml = "";

            for(k = 0; k < list.size(); k++) {
                DataBox dbox1 = (DataBox)list.get(k);

                v_studentlimit       = dbox1.getInt("d_studentlimit");
                v_subjseq            = dbox1.getString("d_subjseq");
                v_year               = dbox1.getString("d_year");
                v_subjseqgr          = dbox1.getString("d_subjseqgr");
                v_propstart          = dbox1.getString("d_propstart");
                v_propend            = dbox1.getString("d_propend");
                v_edustart           = dbox1.getString("d_edustart");
                v_eduend             = dbox1.getString("d_eduend");
                v_ispropose          = dbox1.getString("d_ispropose");
                v_propcnt            = dbox1.getInt("d_propcnt");
                v_biyong             = dbox1.getInt("d_biyong");
                v_today = FormatDate.getDate("yyyyMMddhh");

                //50%할인 이벤트
                if(v_tem_grcode.equals("N000002") && (iCurrentDate >= 20090615 && iCurrentDate <= 20090816)) {
                	v_biyong = v_biyong - (v_biyong * 50 / 100);
                }

				if(v_biyong > 0) {
					v_billYn = "Y";
				}
				else {
					v_billYn = "N";
				}

                if(v_edustart.equals("")){v_iedustart = 0;}
                else{v_iedustart = Integer.parseInt(v_edustart);}

                if(v_iedustart >= Integer.parseInt(v_today)){
                    v_statusString = "<a href=\"javascript:whenSubjBasket('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"','" + v_billYn + "',"+v_biyong+")\" onfocus=this.blur()><img src='/images/user/button/btn_getcart.gif' border=0></a>";
                    v_innerHtml = "<a href=\"javascript:whenSubjBasket2('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"','" + v_billYn + "',"+v_biyong+")\" onfocus=this.blur()><img src='/images/user/kocca/button/btn_order.gif' border=0></a>";

                    if(v_propcnt > 0){
                        v_statusString = "<a href='javascript:goPersonHistory()'><img src='/images/user/button/btn_apply_ing.gif' border=0></a>";
                    }
                } else{
					v_statusString = "<a href=\"javascript:whenSubjBasket('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"','" + v_billYn + "',"+v_biyong+")\" onfocus=this.blur()><img src='/images/user/button/btn_getcart.gif' border=0></a>";
					v_innerHtml = "<a href=\"javascript:whenSubjBasket2('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"','" + v_billYn + "',"+v_biyong+")\" onfocus=this.blur()><img src='/images/user/kocca/button/btn_order.gif' border=0></a>";
                }
%>
				<span id=ordHtml style="display:none;"><textarea id="ordbtnHtml" readonly="readonly" cols="0" rows="0">&nbsp;<%=v_innerHtml%></textarea></span>
				<script>document.getElementById("orderCart").innerHTML = document.getElementById("ordbtnHtml").value;</script>
<%
                //날짜 처리
                if(v_propstart.equals("")){v_propstart = "미정";}
                else{v_propstart     = FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd");}

                if(v_propend.equals("")){v_propend = "미정";}
                else{v_propend = FormatDate.getFormatDate(v_propend,"yyyy/MM/dd");}

                if(v_edustart.equals("")){v_edustart = "미정";}
                else{v_edustart     = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");}

                if(v_eduend.equals("")){v_eduend = "미정";}
                else{v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");}

%>
            <tr>
              <td class="tbl_grc"><%=v_subjseq%></td>
              <td class="tbl_grc"><%=v_propstart%> ~ <%=v_propend%></td>
              <td class="tbl_grc"><%=v_edustart%> ~ <%=v_eduend%></td>
              <td class="tbl_grc"><% if (v_studentlimit == 0) { %>
                                -
                            <%  } else { %>
                                <%=v_studentlimit%>
                            <%  }   %></td>
              <%
              if(v_tem_grcode.equals("N000002") && v_isevent.equals("Y") && (s_userid.equals("gameacademy") || (iCurrentDate >= 20090605 && iCurrentDate <= 20090731))) {
              %>
              	<td class="tbl_grc">10000 <img src="/images/user/button/event_add.gif" border=0></td>
              <% } else if(v_tem_grcode.equals("N000002")){ %>
                <td class="tbl_grc"><%=v_biyong%> <img src="/images/user/button/event_add.gif" border=0></td>
              <% } else { %>
                <td class="tbl_grc"><%=v_biyong%></td>
              <% } %>
              <td class="tbl_grc">
              <%
              if(v_tem_grcode.equals("N000002")) {	//게임아카데미
              	out.print(v_statusString);
              } else {
                out.print(v_innerHtml);
              }
              %>
              </td>
            </tr>
<%      }
            if(k == 0)
            { %>
                <tr>
                    <td class="tbl_grc" align="center" colspan="6">해당 차수가 없습니다.</td>
                </tr>
<%          }
        %>
          </table>
<!-- 차수 끝 -->
</form>
        <br>
        <br>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->