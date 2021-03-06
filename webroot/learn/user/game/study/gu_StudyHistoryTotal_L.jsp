<%
//**********************************************************
//  1. 제      목: STUDY HISTORY LIST
//  2. 프로그램명: zu_StudyHistory_L.jsp
//  3. 개      요: 개인교육이력
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 8. 27
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "probean" class = "com.credu.propose.ProposeBean"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","02");                       // 메뉴 아이디 세팅

    String  v_user_id   = box.getSession("userid");
    String  v_process   = box.getString("p_process");
    String  v_selectcode= box.getStringDefault("p_selectcode", "ALL");
	String v_grcode = box.getSession("tem_grcode");
	String v_grtype = GetCodenm.get_grtype(box,v_grcode);

    String  v_upperclass=  "";
    String  v_isonoff   =  "";
    String  v_course    =  "";
    String  v_cyear     =  "";
    String  v_courseseq =  "";
    String  v_coursenm  =  "";
    String  v_subj      =  "";
    String  v_year      =  "";
    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_subjseqgr =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_edustart1 =  "";
    String  v_eduend1   =  "";
    String  v_place     =  "";
    String  v_edu_org_nm=  "";
    String  v_kind      =  "";
    String  v_isgraduated= "";
    String  v_isnewcourse= "";
    String  v_isonoff_value="";
    String  v_kind_value =  "";
    String  v_serno      = "";
    String  v_pkey       = "";
    String  v_gubun      = "";
    String  v_eduurl     = "";
    String  v_isoutsourcing = "";

	String  v_tmp_subj	= "";
	String  v_iscourseYn = "";

    String  v_upperclassnm=  "";
    String  v_middleclassnm=  "";
    String  v_edulist_value = "";

    double v_credit      = 0;
    double v_creditexam  = 0;
    
    int     v_total     =  0;
    int     i           =  0;
    int     l           =  0;
	int		v_subjcnt	= 0;

    ArrayList list      = null;
    //DEFINED class&variable END

    list = (ArrayList)request.getAttribute("StudyHistoryList");

    // 개인정보
    Hashtable outputData = new Hashtable();
    outputData = probean.getMeberInfo(v_user_id);

    String v_comp = (String)outputData.get("comp");
    String v_jikup = (String)outputData.get("jikup");
    String v_cono = (String)outputData.get("cono");
    String v_resno = (String)outputData.get("resno");
    String v_jikwi = (String)outputData.get("jikwi");
    String v_name = (String)outputData.get("name");
    String v_email = (String)outputData.get("email");
    String v_enterdt = (String)outputData.get("enterdt");
    String v_jikwinm = (String)outputData.get("jikwinm");
    String v_divinam = (String)outputData.get("divinam");
    String v_deptnam = (String)outputData.get("deptnam");
    String v_prmtdat = (String)outputData.get("prmtdat");
    String v_work_plc = (String)outputData.get("work_plc");
    String v_work_plcnm = (String)outputData.get("work_plcnm");
    String v_syear     = (String)outputData.get("syear");
    String v_prmtyy     = (String)outputData.get("prmtyy");
    String v_companynm  = (String)outputData.get("compnm");


%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topMystudy.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<script language="JavaScript" type="text/JavaScript">
<!--

// 학습현황 OPEN
function whenEdulist(subjnm, iseduend, url) {
    var ulink = url + "&p_subjnm=" + subjnm + "&p_iseduend=" + iseduend;
    var new_Open = window.open(ulink,"eduList",'scrollbars=no,width=350,height=240,scrollbars=yes,resizable=no');
}
/*
    //수료증 출력
    function suRoyJeung(subj,year,subjseq,userid,kind){
      window.self.name = "SuryoJeung";
      var new_Open = window.open("/servlet/controller.study.MyClassServlet","openSuryoJeungPrint",'scrollbars=no,width=667,height=600,scrollbars=no,resizable=yes');
      document.form1.target = "openSuryoJeungPrint";
      document.form1.action='/servlet/controller.study.MyClassServlet';
      document.form1.p_subj.value = subj;
      document.form1.p_year.value = year;
      document.form1.p_subjseq.value = subjseq;
      document.form1.p_userid.value = userid;
	  document.form1.p_kind.value = kind;
      document.form1.p_process.value = 'SuryoJeung';
      document.form1.submit();
    }
	*/
	//수료증 출력
	function suRoyJeung(subj,year,subjseq,userid, kind){
		window.self.name = "SuryoJeung";
		var new_Open = window.open("","openSuryoJeungPrint",'scrollbars=no,width=601,height=850,scrollbars=no,resizable=NO');
		
		document.form1.target = "openSuryoJeungPrint";
		document.form1.action = "/servlet/controller.polity.DiplomaAdminServlet";
		
		//document.form1.action = "/learn/admin/polity/RexViewer.jsp?pRptNames=__Diploma_Kocca&pRptParams=lee1^N000001^2006^cbu001^0001";
		document.form1.p_subj.value = subj;
		document.form1.p_year.value = year;
		document.form1.p_scsubjseq.value = subjseq;
		document.form1.p_userid.value = userid;
		document.form1.p_kind.value = kind;
		document.form1.p_process.value = "DiplomaPrint";
		document.form1.submit();

		
	}

    //엑셀 출력
    function whenExcel() {
        window.self.name = "StudentMemberList";
        open_window("openExcel","","10","10","900","660","no","no","no","no","yes");
        document.form1.target = "openExcel";
        document.form1.action='/servlet/controller.study.MyClassServlet';
        document.form1.p_process.value = 'studyHistoryExcel';
        document.form1.submit();
    }
//-->
</script>



<form name= "form1" method= "post">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_sel' value="<%=box.getString("p_sel")%>">
    <input type='hidden' name='p_subj' value="">
    <input type='hidden' name='s_grcode' value="<%=v_grcode%>">
    <input type='hidden' name='p_year' value="">
    <input type='hidden' name='s_subj' value="">
    <input type='hidden' name='p_scsubjseq' value="">
    <input type='hidden' name='p_subjseq' value="">
    <input type='hidden' name='p_userid' value="">
    <input type='hidden' name='p_kind' value="">
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/mystudy/<%=tem_subimgpath%>/tit_myedu_history.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 나의 공부방 > 나의교육이력</td>
  </tr>
  <tr> 
    <td height="20">
      <!-- 나의교육이력정보 -->
    </td>
  </tr>
</table>
<table width="675" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="474" valign="top"><img src="/images/user/game/mystudy/st_sub_myedu_history.gif" width="107" height="13"> 
    </td>
    <td width="201" align="right" valign="top">&nbsp;</td>
  </tr>
  <tr> 
    <td height="5" colspan="2"></td>
  </tr>
  <tr> 
    <td colspan="2" class="font_ex"><table width="720" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
        <tr  class="lcolor"> 
          <td height="3" colspan="9" class="linecolor_my"></td>
        </tr>
        <tr> 
          <td width="39" class="tbl_ptit2">구분</td>
          <td width="47" class="tbl_ptit">분류</td>
          <td width="135" class="tbl_ptit" colspan="2">과정명</td>
          <td width="129" class="tbl_ptit2">교육기간</td>
          <td width="129" class="tbl_ptit2">복습기간</td>
          <td width="53" class="tbl_ptit">성적보기</td>
          <td width="30"  class="tbl_ptit2">이수</td>
          <td width="45"  class="tbl_ptit2">수료증</td>
        </tr>
        
        <%    //double v_score = 0;
		for(i = 0; i < list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);

			v_isonoff     = dbox.getString("d_isonoff");
			v_course      = dbox.getString("d_course");
			v_cyear       = dbox.getString("d_cyear");
			v_courseseq   = dbox.getString("d_courseseq");
			v_coursenm    = dbox.getString("d_coursenm");
			v_subj        = dbox.getString("d_subj");
			v_subjseqgr   = dbox.getString("d_subjgr");
			v_year        = dbox.getString("d_year");
			v_subjseq     = dbox.getString("d_subjseq");
			v_subjnm      = dbox.getString("d_subjnm");
			v_edustart    = dbox.getString("d_edustart");
			v_eduend      = dbox.getString("d_eduend");
			v_credit      = dbox.getDouble("d_credit");
			v_creditexam  = dbox.getDouble("d_creditexam");
			v_place       = dbox.getString("d_place");
			v_isgraduated = dbox.getString("d_isgraduated");
			v_jikwinm     = dbox.getString("d_jikwinm");
			v_serno       = dbox.getString("d_serno");
			v_pkey        = dbox.getString("d_pkey");
			v_gubun       = dbox.getString("d_gubun");
			v_iscourseYn  = dbox.getString("d_isbelongcourse");
			v_isoutsourcing = dbox.getString("d_isoutsourcing");
			v_kind        = dbox.getString("d_kind");
			v_upperclassnm = dbox.getString("d_uclassnm");
			v_middleclassnm= dbox.getString("d_mclassnm");

			v_iscourseYn = dbox.getString("d_isbelongcourse");
			v_subjcnt	 = dbox.getInt("d_subjcnt");

			if (!v_eduend.equals("")) {
				v_edustart1    = FormatDate.getDateAdd(FormatDate.getFormatDate(v_eduend,"yyyyMMdd"), "yyyy/MM/dd", "date", 1);
				//v_eduend1      = FormatDate.getDateAdd(FormatDate.getFormatDate(v_eduend,"yyyyMMdd"), "yyyy/MM/dd", "date", 366);
				v_eduend1      = FormatDate.getDateAdd(FormatDate.getFormatDate(v_eduend,"yyyyMMdd"), "yyyy/MM/dd", "date", 28);
			} else {
				v_edustart1 = "";
				v_eduend1   = "";
			}
			v_edustart   = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
			v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");

			if(v_isonoff.equals("ON"))       v_kind_value =   "사이버";
			else if(v_isonoff.equals("OFF")) v_kind_value =   "집합";
			else                             v_kind_value =   "";

			if(v_isgraduated.equals("Y"))       v_isgraduated = "이수";
			else if(v_isgraduated.equals("N"))  v_isgraduated = "미이수";


			// 과거 자료이면
			if (v_kind.equals("2")) {
				v_edulist_value = "/servlet/controller.contents.EduStart?p_process=eduList&p_subj="+ v_pkey +"&p_year="+ v_year +"&p_subjseq="+v_subjseq+"&p_userid="+v_user_id+"&p_kind=2";
				// 과정코드 셋팅
				v_subj = v_pkey;
			} else {
				v_edulist_value = "/servlet/controller.contents.EduStart?p_process=eduList&p_subj="+ v_subj +"&p_year="+ v_year+"&p_subjseq="+v_subjseq+"&p_userid="+v_user_id;
			}
%>
			  <tr> 
				<td class="tbl_grc"><%=v_middleclassnm%></td>
<% if(v_iscourseYn.equals("Y"))
	{ 
		if(l == v_subjcnt) v_tmp_subj = "";
		if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
		{
			l = 1;
			%>
			<td class="tbl_grc" rowspan="<%=v_subjcnt%>"><%=v_kind_value%></td>
            <td class="tbl_bleft" rowspan="<%=v_subjcnt%>"><%=v_coursenm%><br><%=v_subjseq %>차</td>
			<td class="tbl_bleft"><%=v_subjnm%></td>
	<%	
		}else{ 
				l++;
		%>
			<td class="tbl_bleft"><%=v_subjnm%></td>
<%		}v_tmp_subj = v_course;
	} else { %>

			<td class="tbl_grc"><%=v_kind_value%></td>
            <td class="tbl_bleft" colspan="2"><%=v_subjnm%></td>
<% } %>
				<td class="tbl_bleft"><%=v_edustart%>~ <%=v_eduend%></td>
				<td class="tbl_bleft"><%=v_edustart1%>~ <%=v_eduend1%></td>
				<td class="tbl_grc">
				<%if (!v_subj.equals("")) {  %>
					<a href="javascript:whenEdulist('<%=v_subjnm%>','Y','<%=v_edulist_value%>')"><img src="/images/user/game/button/btn_mynum.gif" width="50"  border="0"></a>
				<%}else{%>
				  -
				<%}%>
				</td>
				<td class="tbl_grc"><font color="#006600"><strong><%=v_isgraduated%></strong></font></td>
				<td class="tbl_grc">
				<%if(!v_subj.equals("") && v_isgraduated.equals("이수")){%>
				  <a href="javascript:suRoyJeung('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>','<%=v_user_id%>','<%=v_kind%>')"><img src="/images/user/game/button/btn_print02.gif" width="50" height="19" border="0"></a>
				<%}else{%>
				  -
				<%}%>
				</td>
			  </tr>

<%
		}

		if(i == 0){
%>
			  <tr>
				<td height="12" colspan="9" class="tbl_grc">교육이력이 없습니다.</td>
			  </tr>
<%
		}
%>

       
      </table></td>
  </tr>
  <tr> 
    <td height="5" colspan="2"></td>
  </tr>
  <tr valign="top"> 
    <td colspan="2">&nbsp; </td>
  </tr>
  </form>
</table>






<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->