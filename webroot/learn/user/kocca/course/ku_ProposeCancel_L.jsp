<%
//**********************************************************
//  1. 제      목: EDUCATION SUBJECT PAGE
//  2. 프로그램명: zu_EducationSubject_L.jsp
//  3. 개      요: 수강과정
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
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "MyClassBean" class = "com.credu.study.MyClassBean"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","03");                       // 메뉴 아이디 세팅

    String  v_process   = box.getString("p_process");
    String  v_user_id   = box.getSession("userid");
    String  v_grcode    =  "";
    String  v_gyear     =  "";
    String  v_grseq     =  "";
    String  v_upperclass=  "";
    String  v_isonoff   =  "";
    String  v_course    =  "";
    String  v_cyear     =  "";
    String  v_courseseq =  "";
    String  v_coursenm  =  "";
    String  v_subj      =  "";
    String  v_subjseqgr =  "";
    String  v_year      =  "";
    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_propstart=  "";
    String  v_propend   =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_eduurl    =  "";
    String  v_isnewcourse=  "";
    String  v_chkfirst  =  "";
    String  v_chkfinal  =  "";
    String  v_cancelkind=  "";
    String  v_proposestatus=  "";
    String  v_userid = "";
	
    String  v_isgraduated= "";
    String  v_subjtarget=  "";
    String  v_isonoff_value="";
    String  v_isgraduated_value="";
    String  v_today     =  FormatDate.getDate("yyyyMMdd");
    String  v_propstart_date = "";
    String  v_propend_date = "";
    String  v_edustart_date = "";
    String  v_eduend_date = "";
    String  v_billstat	= "";
	String	v_paymoney	= "";
	String	v_paytype = "";

    int     i           =  0;
    int     l           =  0;
	int		v_canceldays = 0;

    ArrayList list1     = null;
    //DEFINED class&variable END

    list1 = (ArrayList)request.getAttribute("CancelPossibleList");   //study.MyClassBean.selectEducationSubjectList(box)
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="javascript">
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

	function whenProposeCancel(userid,subj,year,subjseq, polityYn, course, iscourseyn) {
		if(confirm('수강신청을 취소 하시겠습니까?')){
			if(polityYn == "N")
			{
				document.form1.p_subj.value = subj;
				document.form1.p_year.value = year;
				document.form1.p_subjseq.value = subjseq;
				document.form1.p_course.value = course;
				document.form1.p_iscourseyn.value = iscourseyn;

				document.form1.action='/servlet/controller.study.KMyClassServlet';
				document.form1.p_process.value = 'ProposeCancel';
				document.form1.submit();
			}
			else
			{
				var url = "/servlet/controller.polity.KRefundmentAdminServlet";
				var new_Open = window.open("","RefundmentInsert","scrollbars=auto,width=673,height=275,resizable=no");
				
				document.form1.p_subj.value = subj;
				document.form1.p_year.value = year;
				document.form1.p_subjseq.value = subjseq;
				document.form1.p_course.value = course;
				document.form1.p_iscourseyn.value = iscourseyn;

				document.form1.target = "RefundmentInsert";
				document.form1.action=url;
				document.form1.p_process.value = "RefundmentInsertPage";
				document.form1.submit();
			}
		}
	}
//-->
</script>


<form name= "form1" method= "post">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_userid'>
    <input type='hidden' name='p_subj'>
    <input type='hidden' name='p_year'>
    <input type='hidden' name='p_subjseq'>
    <input type='hidden' name='p_subjnm'>
    <input type='hidden' name='p_course'>
    <input type='hidden' name='p_iscourseyn'>

		<!-- title -->
		<table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td  class="location" > HOME > 수강신청 &gt; 수강신청 확인/취소</td>
		  </tr>
		</table>
		<table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td><img src="/images/user/kocca/apply/<%=tem_subimgpath%>/tit_definite02.gif"></td>
		  </tr>
		  <tr> 
			<td height="20"></td>
		  </tr>
		</table>
		<table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td height="12" valign="top"><img src="/images/user/kocca/apply/text_apply_con.gif"></td>
		  </tr>
		  <tr > 
			<td height="1" background="/images/user/kocca/apply/dot_bg_9.gif"></td>
		  </tr>
		  <tr > 
			<td height="16" ></td>
		  </tr>
		</table>
		<table width="680" border="2" cellspacing="0" 
		 cellpadding="3" style="border-collapse:collapse;" bordercolor="#DFDFE0"  frame="hsides">
		  <tr  class="lcolor"> 
			<td  colspan="8" class="linecolor_app"></td>
		  </tr>
		  <tr class="tbl_otit"> 
			<td width="143" class="tbl_otit" colspan="2">과정명</td>
			<td width="121" class="tbl_otit2">수강신청취소기간</td>
			<td width="122" class="tbl_otit">교육기간</td>
			<td width="44" class="tbl_otit2">금액</td>
			<td width="48" class="tbl_otit">결재구분</td>
			<td width="52" class="tbl_otit2">입금구분</td>
			<td width="70" class="tbl_otit">구분</td>
		  </tr>
		  <tr> 
			<td height="5" colspan="8" ></td>
		  </tr>
		 <%

		String v_FinalStatusImg = "";
		String v_PolityYn = "";
		String v_refundmentday = "";
		String v_tmp_course	= "";
		String v_iscourseYn	= "";
		int		v_subjcnt = 0;

		if(list1 != null)
		{
			for(i = 0; i < list1.size(); i++) 
			{
			   DataBox dbox = (DataBox)list1.get(i);
			   v_subj               = dbox.getString("d_subj");
			   v_subjseq            = dbox.getString("d_subjseq");
			   v_subjseqgr          = dbox.getString("d_subjseqgr");
			   v_year               = dbox.getString("d_year");
			   v_subjnm             = dbox.getString("d_subjnm");
			   v_propstart          = dbox.getString("d_propstart");
			   v_propend            = dbox.getString("d_propend");
			   v_edustart           = dbox.getString("d_edustart");
			   v_eduend             = dbox.getString("d_eduend");
			   v_chkfinal           = dbox.getString("d_chkfinal");
			   v_isonoff            = dbox.getString("d_isonoff");
			   v_userid             = dbox.getString("d_userid");
			   v_cancelkind         = dbox.getString("d_cancelkind");
			   v_canceldays			= dbox.getInt("d_canceldays");
			   v_billstat			= dbox.getString("d_paystat");
			   v_paymoney			= dbox.getString("d_paymoney");
			   v_paytype			= dbox.getString("d_paytype");
			   v_refundmentday		= dbox.getString("d_refundmentday");
			   
			   v_course				= dbox.getString("d_course");
			   v_coursenm			= dbox.getString("d_coursenm");
			   v_iscourseYn			= dbox.getString("d_isbelongcourse");
			   v_subjcnt			= dbox.getInt("d_subjcnt");
			   
				v_today			= FormatDate.getDate("yyyyMMdd");						   
			   
				if(v_billstat.equals("Y") && !v_paymoney.equals("0"))
				{
					v_PolityYn = "Y";
				}
				else
				{
					v_PolityYn = "N";
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

				if(v_isonoff.equals("ON"))      {   v_isonoff_value =   "사이버";
				}else if(v_isonoff.equals("OFF")){  v_isonoff_value =   "집합"; }
			   
				if(v_cancelkind.equals("")){

					if(v_chkfinal.equals("Y") && (Integer.parseInt(v_refundmentday) >= Integer.parseInt(v_today)))
					{
						v_FinalStatusImg = "<a href=\"javascript:whenProposeCancel('"+v_userid+"','"+v_subj+"','"+v_year+"','"+v_subjseq+"','" + v_PolityYn + "','"+ v_course +"','"+ v_iscourseYn + "')\"><img src='/images/user/kocca/button/b_cancel.gif' border='0'></a>";
					}else if (v_chkfinal.equals("Y")){
						v_FinalStatusImg = "<img src='/images/user/kocca/button/b_cancel.gif' >";
					}else if (v_chkfinal.equals("N")){
						v_FinalStatusImg = "<img src='/images/user/kocca/button/btn_ban.gif' >";
					}else if (v_chkfinal.equals("B")){
						v_FinalStatusImg = "<a href=\"javascript:whenProposeCancel('"+v_userid+"','"+v_subj+"','"+v_year+"','"+v_subjseq+"','" + v_PolityYn + "','"+ v_course +"','"+ v_iscourseYn + "')\"><img src='/images/user/kocca/button/b_cancel.gif' border='0'></a>";
					}else if (v_chkfinal.equals("M")){
						v_FinalStatusImg = "-";
					}
				}else{
					v_FinalStatusImg  = "-";
				}

				if(v_billstat.equals("W"))
				{
					v_billstat = "입금<br>확인중";
				}else if(v_billstat.equals("Y"))
				{
					v_billstat = "입금<br>확인";
				}else if(v_billstat.equals("CW"))
				{
					v_billstat = "환불<br>처리중";
					v_FinalStatusImg = "<img src='/images/user/kocca/button/b_aplly_ing.gif'>";
				}else if(v_billstat.equals("CY"))
				{
					v_billstat = "환불완료";
					v_FinalStatusImg = "-";
				}else
				{
					v_billstat = "미입금";
				}

				if(v_paytype.equals("D"))
				{
					v_paytype = "카드";
				}else
				{
					v_paytype = "무통장";
				}
%>
	
			  <tr>
<%
				if(v_iscourseYn.equals("Y"))  
				{
					if(l == v_subjcnt) v_tmp_course = "";

					if(!v_tmp_course.equals(v_course) && !v_tmp_course.equals("000000"))
					{ 
						l = 1;
						v_tmp_course = v_course;
%>
				<td class="tbl_bleft" rowspan="<%=v_subjcnt %>"><%=v_coursenm%><br><%=v_subjseq%>차</td>
				<td class="tbl_bleft" ><%=v_subjnm%></td>
				<td class="tbl_grc" rowspan="<%=v_subjcnt %>">학습시작일로부터 <strong><%=v_canceldays%>일</td>
				<td class="tbl_grc" rowspan="<%=v_subjcnt %>"><%= v_edustart %> ~<br><%= v_eduend %></td>
			<!-- 비용을 계산해서 비용이 무료인 경우는 colspan 처리 해야함 -->
				<td class="tbl_grc" rowspan="<%=v_subjcnt %>"><%= v_paymoney%></td>
				<td class="tbl_grc" rowspan="<%=v_subjcnt %>"><%=v_paytype%></td>
				<td class="tbl_grc" rowspan="<%=v_subjcnt %>"><font color="#3366CC"><%=v_billstat%></font></td>
			<!-- 비용을 계산해서 비용이 무료인 경우는 colspan 처리 해야함 -->
				<td class="tbl_grc" rowspan="<%=v_subjcnt %>"><%=v_FinalStatusImg%></td>
<%				}else{ 
					l++;%>
				<td class="tbl_bleft"><%=v_subjnm%></td>
<%				}
			} else { 
			%>
				<td class="tbl_bleft" colspan="2"><%=v_subjnm%></td>
				<td class="tbl_grc">학습시작일로부터 <strong><%=v_canceldays%>일</td>
				<td class="tbl_grc"><%= v_edustart %> ~<br><%= v_eduend %></td>
			<!-- 비용을 계산해서 비용이 무료인 경우는 colspan 처리 해야함 -->
				<td class="tbl_grc"><%= v_paymoney%></td>
				<td class="tbl_grc"><%=v_paytype%></td>
				<td class="tbl_grc"><font color="#3366CC"><%=v_billstat%></font></td>
			<!-- 비용을 계산해서 비용이 무료인 경우는 colspan 처리 해야함 -->
				<td class="tbl_grc"><%=v_FinalStatusImg%></td>
<%			}
		}
	} %>
		
			  </tr>
<%
			   if(i == 0){	%>
				  <tr> 
					<td bgcolor="FFFFFF" align="center" colspan="8" height="35">취소가능한 과정이 없습니다.</td>
				  </tr>
<%
			   }
%>
		  
		  <tr> 
			<td height="5" colspan="8"></td>
		  </tr>
		</table>
		<!--게시판목록 -->
</form>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->