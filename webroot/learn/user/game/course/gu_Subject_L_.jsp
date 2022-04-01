<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명: zu_Subject_L.jsp
//  3. 개      요: 과정조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.01.14
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","02");                       // 메뉴 아이디 세팅

    String  v_process     = box.getString("p_process");
    String  v_iscourseYn  = box.getStringDefault("p_iscourseYn","N");

    String  v_gubun       = box.getStringDefault("p_gubun","1");
	String  v_tabnum	  = box.getStringDefault("p_tabnum","1");

    String  v_code       = "";
    String  v_codenm     = "";
    String  v_tab_bg     = "";
    String  v_tab_class  = "";

    String v_subj           = "";
    String v_scupperclass   = "";
    String v_scmiddleclass  = "";
    String v_sclowerclass  = "";
    String v_year           = "";
    String v_subjseq        = "";
    String v_subjnm         = "";
    String v_proposetype    = "";
    String v_usebook        = "";
    String v_isnew          = "";
    String v_ishit          = "";
    String v_isrecom        = "";
    String v_uclassnm       = "";
    String v_mclassnm       = "";
	String v_isonoff		= "";

    String v_imgbook        = "";
    String v_imgNew         = "";
    String v_imgHit         = "";
    String v_imgRecom       = "";
    String v_Bscupperclass  = "";
	String v_Bscmiddleclass  = "";
	double v_sul_avg		= 0;	// 과정 만족도 

    ArrayList list1      = null;
    ArrayList list2      = null;

    list1 = (ArrayList)request.getAttribute("UpperClassList");      // 직무, 어학 구분 리스트
    list2 = (ArrayList)request.getAttribute("SubjectList");         // 과정 리스트
%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--

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

// 직무 , 어학 선택
function whenSelect(sel,gubun){
    document.form1.p_iscourseYn.value = sel;
    document.form1.p_gubun.value   = gubun;
    document.form1.p_tabnum.value  = gubun;
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.p_process.value = 'SubjectList';

    document.form1.submit();
}

// 과정 내용보기
function whenSubjInfo(subj,subjnm,courseyn,upperclass,upperclassnm){
    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value = subjnm;
    document.form1.p_iscourseYn.value = courseyn;
    document.form1.p_upperclass.value = upperclass;
    document.form1.p_upperclassnm.value = upperclassnm;
    document.form1.p_process.value = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'SubjectList';
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.target = "_self";
    document.form1.submit();
}

// 전체과정보기
function whenSubjectListAll() {
    document.form1.p_process.value = 'SubjectListAll';
    document.form1.p_rprocess.value = 'SubjectList';
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.submit();
}

// 상세버튼 클릭
function whenSubjSeqInfo(subj,year,subjseq,courseyn,subjnm) {
    var url = "/servlet/controller.propose.ProposeCourseServlet?p_process=SeqPreviewPage&p_subj="+subj+"&p_year="+year+"&p_subjseq="+subjseq+"&p_iscourseYn="+courseyn;
	//alert(url);
     var new_Open = window.open(url,"subjSeqInfoPopup",'scrollbars=yes,width=747,height=600,resizable=no');
     document.form1.target = "subjSeqInfoPopup";
}



//-->
</SCRIPT>

<form name="form1" method="post" enctype = "multipart/form-data">
    <input type='hidden' name='p_process' value="<%=v_process%>">
    <input type='hidden' name='p_iscourseYn' value="<%=v_iscourseYn%>">
    <input type='hidden' name='p_gubun'   value="<%=v_gubun%>">

    <input type='hidden' name='p_year'>
    <input type='hidden' name='p_subjseq'>
    <input type='hidden' name='p_proposestart'>
    <input type='hidden' name='p_proposeend'>
    <input type='hidden' name='p_proposeyn'>
    <input type='hidden' name='p_proposetype'>
    <input type='hidden' name='p_purl'>

    <input type='hidden' name='p_subj'>
    <input type='hidden' name='p_subjnm'>
    <input type='hidden' name='p_upperclass' value=''>
	<input type='hidden' name='p_upperclassnm' >
    <input type='hidden' name='p_actionurl' value='/servlet/controller.propose.ProposeCourseServlet'>
    <input type='hidden' name='p_rprocess' >
	
    <input type='hidden' name='p_tabnum' >

          <!-- center start -->
		  <!-- title -->
		<div  align="left" style="width:720px;height:35px;margin-bottom:20px;background-image:url('/images/user/game/apply/<%=tem_subimgpath%>/title02.gif');text-align:right;padding:19px 25px 0px 0px;font-size:11px;color:#000000;">
			<img src="/images/user/game/apply/type1/blt01.gif"> Home &gt; 수강신청 &gt; 수강신청
		</div>
		<div style="margin-left:27px;" align="left">
<% if(v_iscourseYn.equals("N"))
{ %>
			<img src="/images/user/game/apply/type1/apply02_tab01on.gif">
			<a href="javascript:whenSelect('Y','')"><img src="/images/user/game/apply/type1/apply02_tab02.gif"></a>
<% } else { %>
			<a href="javascript:whenSelect('N','1')"><img src="/images/user/game/apply/type1/apply02_tab01_tab01.gif"></a>
			<img src="/images/user/game/apply/type1/apply02_on.gif">
<% } %>
			<br>
			<img src="/images/user/game/apply/type1/apply02_bar.gif" vsapce="5"><br>
			
			<table cellpadding="0" cellspacing="0" width="720">
			<tr>
				<td align="right" style="padding-right:4;"><img src="/images/user/game/apply/type1/tab0<%=v_tabnum%>.gif" usemap="#TapMap"></td>
			</tr>
			<tr style="padding:1;">
				<td align="center"><img src="/images/user/game/apply/type1/tab0<%=v_tabnum%>_bn.gif"></td>
			</tr>
			</table>
			<br>
			<table cellpadding="0" cellspacing="0" width="720">
				<tr>
					<td align="center">
			<table cellpadding="0" cellspacing="0" width="693" align="center">
			<col width="90"><col width="327"><col width="150"><col width="113">			
				<tr><td colspan=4><img src="/images/user/game/apply/type1/tab_tbl_tit.gif"></td></tr>
<%
	//과정 리스트
	int v_upclass_seq = 0;
	int i = 0;
	int k = 0;
	double tmp_sul_avg = 0;
	String v_imgStart = "";

	for(i = 0; i < list2.size(); i++) 
	{
		DataBox dbox = (DataBox)list2.get(i);

		v_subj          = dbox.getString("d_subj");
		v_subjseq       = dbox.getString("d_subjseq");
		v_scupperclass  = dbox.getString("d_scupperclass");
		v_scmiddleclass = dbox.getString("d_scmiddleclass");
		v_sclowerclass  = dbox.getString("d_sclowerclass");
		v_isonoff       = dbox.getString("d_isonoff");
		v_subjnm        = dbox.getString("d_subjnm");
		v_usebook       = dbox.getString("d_usebook");
		v_isnew         = dbox.getString("d_isnew");
		v_ishit         = dbox.getString("d_ishit");
		v_isrecom       = dbox.getString("d_isrecom");
		v_uclassnm      = dbox.getString("d_uclassnm");
		v_mclassnm      = dbox.getString("d_mclassnm");
		v_year			= dbox.getString("d_scyear");
		v_sul_avg		= dbox.getDouble("d_sul_avg");

		if (v_usebook.equals("Y")) v_imgbook = "&nbsp;<img src='/images/user/button/btn_book.gif' align='absmiddle'>";
		else                       v_imgbook = "";
		//v_isnew = "Y";v_ishit="Y";v_isrecom="Y";
		if(v_isnew.equals("Y"))    v_imgNew = "&nbsp;<img src='/images/user/button/btn_snew.gif' align='absmiddle'>";
		else                       v_imgNew = "";
		if(v_ishit.equals("Y"))    v_imgHit = "&nbsp;<img src='/images/user/button/btn_shit.gif' align='absmiddle'>";
		else                       v_imgHit = "";
		if(v_isrecom.equals("Y"))  v_imgRecom = "&nbsp;<img src='/images/user/button/btn_srecom.gif' align='absmiddle'>";
		else                       v_imgRecom = "";

		//tmp_sul_avg = Double.parseDouble(v_sul_avg) * 10;
		v_imgStart = "";
		tmp_sul_avg = v_sul_avg * 10;

		for(k = 0; k < 5; k++)
		{
			if(tmp_sul_avg > 10) 
			{
				v_imgStart += "<img src='/images/user/game/course/i_star_on.gif'>";
			}
			else
			{
				if(tmp_sul_avg >= 5) 
				{
					v_imgStart += "<img src='/images/user/game/course/i_star_half.gif'>";
				}
				else
				{
					v_imgStart += "<img src='/images/user/game/course/i_star_off.gif'>";
				}
			}

			tmp_sul_avg = tmp_sul_avg - 10;
		}

		if(i != 0)
	{ %>
		<tr height="1"><td colspan="4" background="/images/user/game/apply/type1/tbl_dot.gif"></td></tr>
<%	}
%>
				<tr align="center" valign="bottom" style="padding:5px 0px 4px 0px;">
					<td>
					<% if (v_sclowerclass.equals("001")) { //초급 %>
						<img src="/images/user/game/apply/class01.gif">
					<% } else if (v_sclowerclass.equals("002")) { //중급 %>
						<img src="/images/user/game/apply/class02.gif">
					<% } else if (v_sclowerclass.equals("003")) { //고급 %>
						<img src="/images/user/game/apply/class03.gif">
					<% } %>
					</td>
					<td style="color:black;padding-left:10;" align="left"><%=v_subjnm%><%=v_imgbook%><%=v_imgNew%><%=v_imgHit%><%=v_imgRecom%></td>
					<td style="color:black;"><%//=v_sul_avg%><!--span style="width:30;"></span-->&nbsp;<span style="color:#e7bd0f;"><%=v_imgStart%></span></td>
					<td><img src="/images/user/game/button/b_viewdetail.gif" style="cursor:hand" onClick="javascript:whenSubjInfo('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>','<%=v_scupperclass%>','<%=v_uclassnm%>')"></td>
				</tr>
<% } 
					if(i == 0)
			{ %>
				<tr>
					<td class="tbl_grc" align="center" colspan="4">개설 과정이 없습니다.</td>
				</tr>
<%			}
				
				%>
				<tr height="4"><td colspan="4" bgcolor="#dce5f6"></td></tr>
			</table>
						</td>
					</tr>
				</table>
			<br>
		</div>

</form> 

<map name="TapMap" id="Map">
	<area shape="rect" coords="3,2,120,25"	 href="#" onclick="whenSelect('<%=v_iscourseYn%>','1');" />
	<area shape="rect" coords="122,2,232,27" href="#" onclick="whenSelect('<%=v_iscourseYn%>','2');" />
	<area shape="rect" coords="234,2,340,28" href="#" onclick="whenSelect('<%=v_iscourseYn%>','3');" />
	<area shape="rect" coords="345,2,450,27" href="#" onclick="whenSelect('<%=v_iscourseYn%>','4');" />
</map>
</div>
		<br>
		<br>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->