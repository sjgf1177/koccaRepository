<%
//**********************************************************
//  1. 제      목: 나의강의실 > 나의 관심과정
//  2. 프로그램명 : gu_MyInterest_L.jsp
//  3. 개      요: 나의 관심과정
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.30
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_userid = box.getSession("userid");
	
	String	v_biyong		= "";
	String	v_today			= "";
	String	v_subj			= "";
	String	v_subjnm		= "";
    String	v_statusString	= "";
    String	v_subjseqgr		= "";
    int		v_iedustart		= 0;
    int		v_studentlimit	= 0;
	String	v_subjseq		= "";
	String	v_year			= "";
	String	v_propstart		= "";
	String	v_propend		= "";
	String	v_edustart		= "";
	String	v_eduend		= "";           
	String	v_ispropose		= "";           
	String	v_classname		= "";           
	String	v_sphere		= ""; 
	int		v_propcnt		= 0;
	String	v_preurl		= "";
	String	v_issampleString= "";
	String	v_isonoff		= "";
	String	v_upperclass	= "";

    Date today1 = new Date();
    Calendar nowCalendar = Calendar.getInstance();
    nowCalendar.setTime(today1);// 오늘로 설정.
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    String today=format.format(today1);

    ArrayList list1 = (ArrayList)request.getAttribute("InterestList");
    
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript">
<!--
//관심과정 삭제
function deleteGo( subj, year, subjseq ){

	var ok= confirm("관심과정에서 삭제하시겠습니까?");

	if(ok){

	document.frm3.p_subj.value=subj;
	document.frm3.action ="/servlet/controller.study.InterestServlet?p_process=InterestDelete";
	document.frm3.submit();

	}else{
		return;	
	}

}

// 과정 내용보기
function whenSubjInfo_old(subj,tabnum){
    document.frm3.p_subj.value     = subj;
    document.frm3.p_tabnum.value   = tabnum;
    document.frm3.p_process.value  = 'SubjectPreviewPage';
    document.frm3.p_rprocess.value = 'SubjectList';
    document.frm3.action='/servlet/controller.course.CourseIntroServlet';
    document.frm3.target = "_self";
    document.frm3.submit();
}


//과정 내용보기
function whenSubjInfo(subj,subjnm,courseyn,upperclass,upperclassnm, year, subjseq){
document.frm3.p_subj.value = subj;
document.frm3.p_subjnm.value = subjnm;
document.frm3.p_iscourseYn.value = courseyn;
document.frm3.p_upperclass.value = upperclass;
document.frm3.p_upperclassnm.value = upperclassnm;
document.frm3.p_year.value = year;
document.frm3.p_subjseq.value = subjseq;
document.frm3.p_process.value = 'SubjectPreviewPage';
document.frm3.p_rprocess.value = 'SubjectList';
document.frm3.action='/servlet/controller.propose.ProposeCourseServlet';
document.frm3.target = "_self";
document.frm3.submit();
}

//수강신청
function whenSubjPropose(subj,year,subjseq, subjnm) {

	if(<%= box.getSession("userid").length()==0 %>) {
		alert("로그인이 필요합니다.");
		return;
	}
	if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")){
	 return;
	}
	var new_Open = window.open("","proposeWindow",'scrollbars=yes,width=800,height=600,resizable=no');
	document.form1.p_process.value = "SubjectEduProposePage";
	document.form1.target = "proposeWindow";
	document.form1.p_subj.value = subj;
	document.form1.p_year.value = year;
	document.form1.p_subjseq.value = subjseq;
	document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
	document.form1.submit();
	document.form1.target = "_self";
}

//-->
</script>
<form name="form1" method="post">
	<input type="hidden" name="p_process"	value="SubjectList"/>
	<input type="hidden" name="p_rprocess"	value=""/>
	<input type="hidden" name="p_year"	value=""/>
	<input type="hidden" name="p_subj"	value=""/>
	<input type="hidden" name="p_subjseq"	value=""/>
	<input type="hidden" name="p_subjnm"	value=""/>
	<input type="hidden" name="p_order"	value=""/>
	<input type="hidden" name="p_iscourseYn"	value=""/>
	<input type="hidden" name="p_upperclass"	value="C01"/>
	<input type="hidden" name="p_upperclassnm"	value=""/>
	<input type="hidden" name="p_action"	value=""/>
	<input type="hidden" name="p_pageno"	value="1"/>
	<input type="hidden" name="p_pagesize"  value="0"/>
	<input type="hidden" name="p_area" id="p_area"	value=""/>
</form>

<!-- Form 영역 시작 -->
	<form name="frm3" method="post" >
		<input type="hidden" name="p_subj"/>
		<input type="hidden" name="p_subjnm"/>
		<input type="hidden" name="p_iscourseYn"/>
		<input type="hidden" name="p_upperclass"/>
		<input type="hidden" name="p_upperclassnm"/>
		<input type="hidden" name="p_year"/>
		<input type="hidden" name="p_subjseq"/>
		<input type="hidden" name="p_tabnum"/>
		<input type="hidden" name="p_process"/>
		<input type="hidden" name="p_rprocess"/>
		<input type="hidden" name="p_frominterestyn" value="Y"/>

			<h2><img src="/images/portal/myclass/h2_tit11.gif" alt="나의관심과정" class="fl_l" /><p class="category">Home > 나의강의실 > <strong>나의관심과정</strong></p></h2>
 
			<p><img src="/images/portal/myclass/myinterest_txt.gif" alt="관심과정으로 저장한 목록입니다. 저장한 목록에서 바로 수강신청하실 수 있으며, 과정 수강신청시 관심과정 목록에서 삭제됩니다." /></p>
			
			<table class="board_list mg_t30">
			<colgroup><col width="90px" /><col width="282px" /><!--col width="50px" /--><col width="90px" /><col width="90px" /><col width="138px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th><p>분류</p></th>
				<th><p>과정명</p></th>
				<!--th><p>차수</p></th-->
				<th><p>교육기간</p></th>
				<th><p>수강료</p></th>
				<th class="end"><p>과정삭제</p></th>
			</tr>
			</thead>
			<tbody>
<%
	NumberFormat s_fmt = NumberFormat.getInstance();				//통화단위 ,표시
	int totalCnt = list1.size();
	//DataBox dbox = null;
    String v_propstart1;
    String v_propend1;
    
	for (int i=0; i<list1.size(); i++) {
        DataBox dbox = (DataBox)list1.get(i);  
			v_biyong		= s_fmt.format(dbox.getInt("d_biyong"));
			v_today			= FormatDate.getDate("yyyyMMddhh");
			v_classname		= dbox.getString("d_classname");
			v_subj			= dbox.getString("d_subj");
			v_subjnm		= dbox.getString("d_subjnm");
			v_studentlimit	= dbox.getInt("d_studentlimit");
			v_subjseq		= dbox.getString("d_subjseq");
			v_year			= dbox.getString("d_year");
			v_subjseqgr		= dbox.getString("d_subjseqgr");
			v_propstart		= dbox.getString("d_propstart");
			v_propend		= dbox.getString("d_propend");
            if (v_propstart.equals("")) {
                v_propstart1    = "";
            } else {
                v_propstart1    = dbox.getString("d_propstart").substring(0,8);
            }
            if (v_propend.equals("")) {
                v_propend1    = "";            
            } else {
                v_propend1		= dbox.getString("d_propend").substring(0,8);
            }
			v_edustart		= dbox.getString("d_edustart");
			v_eduend		= dbox.getString("d_eduend");
			v_ispropose		= dbox.getString("d_ispropose");
			v_propcnt		= dbox.getInt("d_propcnt");
			v_today			= FormatDate.getDate("yyyyMMddhh");
			v_preurl        = dbox.getString("d_preurl");
			v_sphere        = dbox.getString("d_sphere");
			v_upperclass    = dbox.getString("d_upperclass");
			v_isonoff       = dbox.getString("d_isonoff");

			if(!v_preurl.equals(""))
			{ 
				v_issampleString = "<a href=\"javascript:whenPreShow('" + v_preurl + "','" + v_subj +"')\" focus=\"this.blur()\"><img src=\"/images/user/portal/button/b_samplecourse.gif\" border=\"0\"></a>";
			}else {
				v_issampleString = "-";
			}

            if(v_edustart.equals("")){v_iedustart = 0;}
            else{v_iedustart = Integer.parseInt(v_edustart);}

            if(v_iedustart >= Integer.parseInt(v_today)){
                if(v_propcnt > 0){
                    v_statusString = "";
                }
                else
                	v_statusString = "<a href=\"javascript:whenSubjPropose('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"')\" class=\"board_btn2\"><span>수강신청</span></a>";
            }

            //날짜 처리
            if(v_propstart.equals("")){v_propstart = "미정";}
            else{v_propstart     = FormatDate.getFormatDate(v_propstart,"yyyy.MM.dd");}

            if(v_propend.equals("")){v_propend = "미정";}
            else{v_propend = FormatDate.getFormatDate(v_propend,"yyyy.MM.dd");}

            if(v_edustart.equals("")){v_edustart = "미정";}
            else{v_edustart     = FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");}

            if(v_eduend.equals("")){v_eduend = "미정";}
            else{v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");}

%>
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td class="ta_l"><%= v_classname %></td>
				<td class="ta_l">
                    <a href="javascript:whenSubjInfo('<%= v_subj %>','<%= v_subjnm %>','<%= v_isonoff %>','<%= v_upperclass %>','<%= v_classname %>','<%= v_year %>','<%= v_subjseq %>')"><%= v_subjnm %></a>
                </td>
				<!--td><%//= StringManager.cutZero(v_subjseq) %>차</td-->
				<td class="num ta_l"><%= v_edustart %> ~<br /><%= v_eduend %></td>
				<td class="num ta_r"><strong><%= v_biyong %></strong></td>
				<td><% if(v_propstart1.compareTo(today)>=0 && v_propend1.compareTo(today)<=0) { out.print(v_statusString); }%>
                    <a href="javascript:deleteGo('<%=dbox.getString("d_subj")%>','<%=dbox.getString("d_year")%>','<%=dbox.getString("d_subjseq")%>')" class="board_btn2"><span>삭제</span></a></td>
			</tr>
<%
	}
%>
<%
		if(list1.size()==0){
%>
			<tr height="25"> 
			  <td colspan="6" align="center" valign="middle">검색된 내용이 없습니다.</td>
			</tr>
<%
		}
%>
			</tbody>
			</table>
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->