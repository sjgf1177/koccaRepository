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
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","02");                       // 메뉴 아이디 세팅

    String  v_process		= box.getString("p_process");
    String  v_select		= box.getString("p_select");
    String	v_iscourseYn	= box.getString("p_iscourseYn");
    String  v_gubun			= "";

    String v_subj           = "";
    String v_scupperclass   = "";
    String v_scmiddleclass  = "";
    String v_isonoff        = "";
    String v_year           = "";
    String v_subjseq        = "";
    String v_courseseq      = "";
    String v_subjnm         = "";
    String v_proposetype    = "";
    String v_isnew          = "";
    String v_ishit          = "";
    String v_isrecom        = "";
    String v_uclassnm       = "";
    String v_mclassnm       = "";
    String v_Bmclassnm      = "";
    String v_totcnt         = "";
	String v_propstart		="";
	String v_propend		="";
	String v_edustart		="";
	String v_eduend 		="";

    String v_course		= "";
    String v_coursenm   = "";
    String v_tmp_course	= "";
    String v_biyong		= "";
    String v_scbiyong		= "";
	String v_billYn		= "";

    int    v_total          = 0;
    int    v_possiblecnt    = 0;
    int    v_class_len      = 0;
    int    v_subjcnt		= 0;
    int    l		= 0;

    String v_isonoff_value = "";

    ProposeCourseBean bean = new ProposeCourseBean();
    String  s_userid = box.getSession("userid");
    String  s_jikwi  = box.getSession("jikwi");
    String  v_prop_check = "";
    ArrayList list      = null;
    ArrayList list1      = null;
    //ArrayList list2      = null;
    //DEFINED class&variable END

    list = (ArrayList)request.getAttribute("SubjectList");
    //list2 = (ArrayList)request.getAttribute("MiddleClassList");

%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<script language="JavaScript" type="text/JavaScript">
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

	function whenSelect(sel,gubun){
		document.form1.p_iscourseYn.value = sel;
		document.form1.p_gubun.value = gubun;
		//alert(document.form1.s_upperclass.value);
		document.form1.target = "_self";
		document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
		document.form1.p_process.value = 'SubjectList';
		document.form1.submit();
	}

	function whenSubjInfo(subj,subjnm,isonoff,upperclass,upperclassnm,process){
		document.form1.p_subj.value = subj;
		document.form1.p_subjnm.value = subjnm;
		document.form1.p_isonoff.value = isonoff;
		document.form1.p_upperclass.value = upperclass;
		document.form1.p_upperclassnm.value = upperclassnm;
		document.form1.p_process.value = 'SubjectPreviewPage';
		document.form1.p_rprocess.value = 'SubjectList';
		document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
		document.form1.submit();
	}

	//수강신청
	function whenSubjPropose(subj,year,subjseq, subjnm, billYn,course,coursenm) {

		var msg = "";

		if(course != '000000')
		{
			msg = coursenm;
			document.form1.p_iscourseYn.value = "Y";
		}
		else
		{
			msg = subjnm;
		}
		if(!confirm(msg+" 과정을 수강신청하시겠습니까?")){
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
		document.form1.p_billYn.value = billYn;
		document.form1.p_subjseq.value = subjseq;
		document.form1.p_course.value = course;
		document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
		//alert(document.form1.p_iscourseYn.value);
		document.form1.submit();
	}
	//나의강의실
	function goPersonHistory(){
	  document.form1.target = "_self";
	  document.form1.p_process.value = "EducationSubjectPage";
	  document.form1.action="/servlet/controller.study.MyClassServlet";
	  document.form1.submit();
	}

	//장바구니 담기
	function whenSubjBasket(course,courseyear,courseseq, coursenm, price) {

	   if(!confirm(coursenm+" 과정을 장바구니에 담으시겠습니까?")){
		 return;
	   }

	   document.form1.target = "_self";
	   document.form1.p_course.value = course;
	   document.form1.p_cyear.value = courseyear;
	   document.form1.p_courseseq.value = courseseq;
	   document.form1.p_price.value = price;
	   document.form1.p_process.value = "InsertBasketPackage";
	   document.form1.action = "/servlet/controller.account.AccountManagerServlet";
	   document.form1.submit();
	}
//-->

</script>

<form name="form1" method="post" enctype = "multipart/form-data">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_isonoff' value="OFF">
    <input type='hidden' name='p_select'  value="<%=v_select%>">
    <input type='hidden' name='p_iscourseYn'>
    <input type='hidden' name='p_gubun'   value="<%=v_gubun%>">

    <input type='hidden' name='p_course'>
    <input type='hidden' name='p_billYn'>
    <input type='hidden' name='p_cyear'>
    <input type='hidden' name='p_courseseq'>
    <input type='hidden' name='p_coursenm'>
    <input type='hidden' name='p_year'>
    <input type='hidden' name='p_subjseq'>
    <input type='hidden' name='p_proposestart'>
    <input type='hidden' name='p_proposeend'>
    <input type='hidden' name='p_proposeyn'>
    <input type='hidden' name='p_proposetype'>
    <input type='hidden' name='p_purl'>

    <input type='hidden' name='p_subj'>
    <input type='hidden' name='p_subjnm'>

    <input type='hidden' name='p_price'>

    <input type='hidden' name='p_upperclass' value=''>
    <input type='hidden' name='p_upperclassnm' >
    <input type='hidden' name='p_actionurl' value='/servlet/controller.propose.ProposeCourseServlet'>
    <input type='hidden' name='p_rprocess' >

		<table width="720" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td height="35" colspan="3" align="right"  background="/images/user/game/apply/<%=tem_subimgpath%>/title02.gif" class="location" ><img src="/images/user/game/common/location_bl.gif">
			  HOME > 수강신청 > 수강신청 </td>
		  </tr>
		  <tr>
			<td height="20" colspan="3"></td>
		  </tr>
		  <tr>
			<td colspan="3">

				<table border="0" cellpadding="0" cellspacing="0" width="720">
					<tr>
						<td>
						<% if(v_iscourseYn.equals("N"))
{ %>
			<img src="/images/user/game/apply/type1/apply02_tab01on.gif">
			<a href="javascript:whenSelect('Y','')"><img src="/images/user/game/apply/type1/apply02_tab02.gif"></a>
<% } else { %>
			<a href="javascript:whenSelect('N','1')"><img src="/images/user/game/apply/type1/apply02_tab01.gif"></a>
			<img src="/images/user/game/apply/type1/apply02_tab02on.gif">
<% } %></td>
						<td align="right" style="padding-right:5px"><a href="/learn/user/game/course/01.jsp"><img src="/images/user/game/apply/type1/apply02_tab03.gif" border="0"></a></td>
					</tr>
				</table>
			</td>
		  </tr>
		<tr>
			<td width="9" height="16" valign="top"><img src="/images/user/game/course/g_box_left.gif" width="9" height="16"></td>
			<td width="702" valign="bottom" background="/images/user/game/course/bg_g_box.gif"><img src="/images/user/game/mystudy/type1/blank.gif" width="20" height="10"></td>
			<td width="9" valign="top"><div align="right"><img src="/images/user/game/course/g_box_right.gif" width="9" height="16"></div></td>
	  </tr>
	  <tr>
		<td height="10" colspan="3"><img src="/images/user/game/apply/tab05_bn.gif"></td>
	  </tr>
	  <tr>
		<td height="10" colspan="3"></td>
	  </tr>
	</table>

		<table width="690" border="2" cellspacing="0" cellpadding="3" style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
		  <tr  class="lcolor">
			<td height="3" colspan="6" class="linecolor_app"></td>
		  </tr>
		  <tr>
			<td width="5%" class="tbl_gtit2">No</td>
			<td width="40%" class="tbl_gtit2" colspan="2">과정명</td>
			<td width="20%" class="tbl_gtit2">수강신청기간</td>
			<td width="20%" class="tbl_gtit2">교육기간<br></td>
			<td width="10%" class="tbl_gtit2">신청</td>
		  </tr>
		  <%
			int i = 0;
			int j = 0;
			int	v_iedustart = 0;
			int	v_propcnt = 0;
			String	v_today		= "";
			String v_statusString = "";

			for(i = 0; i < list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);

				v_subj          = dbox.getString("d_subj");
				v_scupperclass  = dbox.getString("d_scupperclass");
				v_scmiddleclass = dbox.getString("d_scmiddleclass");
				v_isonoff       = dbox.getString("d_isonoff");
				v_subjnm        = dbox.getString("d_subjnm");
				v_isnew         = dbox.getString("d_isnew");
				v_ishit         = dbox.getString("d_ishit");
				v_isrecom       = dbox.getString("d_isrecom");
				v_uclassnm      = dbox.getString("d_uclassnm");
				v_mclassnm      = dbox.getString("d_mclassnm");
				v_totcnt        = dbox.getString("d_totcnt");
				v_biyong        = dbox.getString("d_biyong");
				v_scbiyong        = dbox.getString("d_scbiyong");

				v_course		= dbox.getString("d_course");
				v_coursenm		= dbox.getString("d_coursenm");
				v_propstart     = dbox.getString("d_propstart");
				v_propend		= dbox.getString("d_propend");
				v_edustart      = dbox.getString("d_edustart");
				v_eduend		= dbox.getString("d_eduend");
				v_year			= dbox.getString("d_year");
				v_subjseq		= dbox.getString("d_subjseq");
				v_courseseq		= dbox.getString("d_courseseq");

				v_subjcnt		= dbox.getInt("d_subjcnt");
				v_possiblecnt   = dbox.getInt("d_possiblecnt");
				v_propcnt		= dbox.getInt("d_procnt");

				v_today			= FormatDate.getDate("yyyyMMddhh");

				if(v_isonoff.equals("ON")){v_isonoff_value="사이버";}
				else{v_isonoff_value="집합";}

				//  코스 과정일 경우 비용 계산.
				if(v_iscourseYn.equals("Y"))
				{
					DataBox dbox2 = KProposeCourseBean.getBill(v_course, v_subjseq, v_year);

					if(dbox2 != null)
					{
						v_biyong        = dbox2.getString("d_biyong");
						System.out.println("v_biyong 1 = " + v_biyong);
					}
				}
				if(v_biyong.equals("") || v_biyong.equals("0"))
				{
					v_billYn = "N";
				}
				else
				{
					v_billYn = "Y";
				}

				System.out.println("v_biyong = " + v_biyong);
				System.out.println("v_scbiyong = " + v_scbiyong);

				System.out.println("v_courseseq = " + v_courseseq);

				if(v_edustart.equals("")){v_iedustart = 0;}
				else{v_iedustart = Integer.parseInt(v_edustart);}

				if(v_iedustart >= Integer.parseInt(v_today)){
					//2006-08-24
					////v_statusString = "<a href=\"javascript:whenSubjPropose('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"','" + v_billYn + "','"+v_course+"','" + v_coursenm + "')\" onfocus='this.blur()'><img src='/images/user/game/button/b_apply.gif' border=0></a>";

					v_statusString = "<a href=\"javascript:whenSubjBasket('"+v_course+"','"+v_year+"','"+v_courseseq+"','"+v_coursenm+"'," + v_scbiyong + ")\" onfocus='this.blur()'><img src='/images/user/button/btn_getcart.gif' border=0></a>";

					//out.println("v_ispropose"+v_ispropose);
					if(v_propcnt > 0){
						v_statusString = "<a href='javascript:goPersonHistory()'><img src='/images/user/game/button/b_apply_ing.gif' border='0'></a>";
					}
				} else{
					//2006-08-24
					////v_statusString = "<a href=\"javascript:whenSubjPropose('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"','" + v_billYn + "','"+v_course+"','" + v_coursenm + "')\" onfocus='this.blur()'><img src='/images/user/game/button/b_apply.gif' border=0></a>";
					v_statusString = "<a href=\"javascript:whenSubjBasket('"+v_course+"','"+v_year+"','"+v_courseseq+"','"+v_coursenm+"'," + v_scbiyong + ")\" onfocus='this.blur()'><img src='/images/user/button/btn_getcart.gif' border=0></a>";
				}

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

<%

			if(l == v_subjcnt) v_tmp_course = "";

			if(!v_tmp_course.equals(v_course) && !v_tmp_course.equals("000000"))
			{
				j++;
				l = 1;
				v_tmp_course = v_course;
				%>
					<td class="tbl_grc" rowspan="<%=v_subjcnt %>"><%=j%></td>
					<td class="tbl_bleft" rowspan="<%=v_subjcnt %>"><%= v_coursenm %><br><%=v_subjseq %>차</td>
					<td class="tbl_bleft" ><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>','<%=v_scupperclass%>','<%=v_uclassnm%>','SubjectPreviewPage')"><%=v_subjnm%></a></td>
					<td class="tbl_grc" rowspan="<%=v_subjcnt %>"><%=v_propstart %> ~<br><%=v_propend %></td>
					<td class="tbl_grc" ><%=v_edustart %> ~<br><%=v_eduend %></td>
					<td class="tbl_grc" rowspan="<%=v_subjcnt %>"><%=v_statusString%></td>

	<%		} else {
			 l++;%>
					<td class="tbl_bleft" ><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>','<%=v_scupperclass%>','<%=v_uclassnm%>','SubjectPreviewPage')"><%=v_subjnm%></a></td>
					<td class="tbl_grc" ><%=v_edustart %> ~<br><%=v_eduend %></td>

<%                 }  %>

				  </tr>

<%				}
			if(i == 0){
%>
				  <tr>
					<td class="tbl_grc" colspan="6">현재운영중인 전문가 과정이 없습니다.</td>
				  </td>
<%
			}
%>
				</table>

</form>

		<br>
		<br>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->