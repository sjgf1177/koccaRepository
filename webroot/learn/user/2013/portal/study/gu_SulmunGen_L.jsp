<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) box = RequestManager.getBox(request);
	
    ArrayList list2     = null;
    list2 = (ArrayList)request.getAttribute("SulmunCommon");			//일반설문

    box.put("title_str", "설문 - 온라인 강의실 - 개인메뉴");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--


// 일반 설문응시
function commonWright(p_subj, p_grcode, p_sulpapernum){
        document.form1.p_subj.value = p_subj;
        document.form1.p_grcode.value = p_grcode;
        document.form1.p_sulpapernum.value = p_sulpapernum;
     
        window.self.name = "winSelectView";
        farwindow = window.open("", "openSubjChange", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 680, height = 538, top=0, left=0");
        document.form1.target = "openSubjChange";
        document.form1.action = "/servlet/controller.research.SulmunCommonUserServlet";
        document.form1.p_process.value = "SulmunUserPaperListPage";
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
}

function ReloadPage(p_action, tab) {
  document.form1.p_process.value = 'SulmunNew';
  document.form1.p_action.value  = p_action;
  document.form1.action = "/servlet/controller.research.SulmunSubjUserServlet";
  document.form1.submit();
}

function change_sul(val){
	if(val == '1'){
		document.form1.p_process.value = 'SulmunNew';
	}else{
		document.form1.p_process.value = 'SulmunGen';
	}
	
	document.form1.action = '/servlet/controller.research.SulmunSubjUserServlet';
	document.form1.submit();
}
//-->
</script>
<!-- 스크립트영역종료 -->
<!-- container-new 시작 //-->
<div id="container-new">

    <!-- Form 영역 시작 -->
    <form name="form1" method="post" action="/servlet/controller.research.SulmunCommonUserServlet">
    <input type="hidden" name="p_process"      value="">
    <input type="hidden" name="p_action"       value="">
    <input type="hidden" name="p_subj"         value="">
    <input type="hidden" name="p_subjnm"       value="">            
    <input type="hidden" name="s_subj"         value="">    
    <input type="hidden" name="p_grcode"       value="">
    <input type="hidden" name="s_grcode"       value="">        
    <input type="hidden" name="p_gyear"        value="">
    <input type="hidden" name="p_subjseq"      value="">
    <input type="hidden" name="p_sulpapernum"  value="">
    <input type="hidden" name="p_sulpapernm"   value="">
    <input type="hidden" name="p_sulstart"     value="">
    <input type="hidden" name="p_sulend"       value="">
    <input type="hidden" name="p_sulnums"      value="">        
    <input type="hidden" name="p_listok"       value="">
	<div id="mainMenu">
		<ul class="location">
            <li>개인메뉴</li>
            <li>
                온라인 강의실 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
				<ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" class="active">온라인 강의실</a></li>
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage" >현장교육 강의실</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE" >내가작성한 글</a></li>
                    <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage">상담내역</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">정보관리</a></li>
                    
                    <!-- 2015-01-09 개편변경
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" class="active">나의 강의실</a></li>
					<li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">과정안내</a></li>
					<li><a href="#">커뮤니티</a></li>
                    //-->                    
				</ul>
			</li>
		</ul>
		
		<!-- 서브메인용 -->
		<div class="introCategory">
			<ul>
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" >나의 강의실</a></li>
                <li><a href="/servlet/controller.study.FavorOpenClassServlet?p_process=favorOpenClassList" >찜목록</a></li>
                <li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunNew" class="active"><strong>설문</strong></a></li>
                <li><a href="/servlet/controller.study.ProposeReviewServlet?p_process=selectReviewList" >과정후기</a></li>
                <!-- 2015-01-09 개편수정
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><strong><u>나의 강의실</u></strong></a></li>
				<li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">과정안내</a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
                //-->
			</ul>
		</div>
		<!-- //서브메인용 -->
		<!-- //온라인 교육 -->
	</div>
	
	
	<!-- 컨텐츠 영역 시작 -->
	<div id="contents">
		<h2 class="subTit">설문<!--<img src="/images/2013/online_edu/sulmun_title.gif" alt="나의 강의실" />//--></h2>
		
		<div id="subCont" class="myClass3">

            <!-- 2015-01-09 개편삭제
            <ul class="tabCus">
				<li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><img src="/images/2013/online_edu/myclass_tab_1_off.gif" alt="나의 강의실" /></a></li>
				<li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage"><img src="/images/2013/online_edu/myclass_tab_2_off.gif" alt="상담내역" /></a></li>
				<li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunNew"><img src="/images/2013/online_edu/myclass_tab_3_on.gif" alt="설문" /></a></li>
			</ul>

            //-->
            <h3 class="blind">설문 현재위치</h3>
			
			<div class="board2">
				<table summary="글번호, 분류, 제목, 과정, 작성자, 등록일 조회로 구성되어있습니다.">
					<colgroup>
						<col width="5%"/>
						<col width="8%"/>
						<col width="*"/>
						<col width="20%"/>
						<col width="8%"/>
						<col width="10%"/>
					</colgroup>
					<thead>
					<tr>
						<th scope="col"></th>
						<th scope="col">
							<select title="분류" onchange="change_sul(this.value);">
								<option value='1'>과정</option>
								<option selected value='2'>일반</option>
							</select>
						</th>
						<th scope="col">설문주제</th>
						<th scope="col">설문기간</th>
						<th scope="col">문제수</th>
						<th scope="col">응시여부</th>
					</tr>
					</thead>
					<tbody>
					<%
		       		for(int i = 0; i < list2.size(); i++) {
			    		DataBox dbox2 = (DataBox)list2.get(i);
			    		int v_sulpapernum      =  dbox2.getInt("d_sulpapernum");
			    		String v_sulpapernm    =  dbox2.getString("d_sulpapernm");
			    		String v_sulstart      =  dbox2.getString("d_sulstart");
			    		String v_sulend        =  dbox2.getString("d_sulend");
			    		int v_totcnt           =  dbox2.getInt("d_totcnt");
			    		v_sulstart      = FormatDate.getFormatDate(v_sulstart,"yyyy.MM.dd");
			    		v_sulend        = FormatDate.getFormatDate(v_sulend,"yyyy.MM.dd");
			    		int v_sulresult = dbox2.getInt("d_sulresult");       // 설문결과여부
			    		String v_issul  = dbox2.getString("d_issul");        // 설문가능여부      
					%>    
					<tr>
						<td class="first fs"><%= i+ 1 %></td>
						<td class="fs">일반</td>
						<td class="left"><%= v_sulpapernm %></td>
						<td><%= v_sulstart %> ~ <%= v_sulend %></td>
						<td><%= v_totcnt %></td>
						<td>
							<%if (v_issul.equals("0")) {%>
								<a href="javascript:commonWright('COMMON', '<%=dbox2.getString("d_grcode")%>', '<%=v_sulpapernum%>')" onfocus="txtlink('b',<%=i+1%>)" onblur="txtlink('b',<%=i+1%>)" tabindex="<%=i+200%>">응시가능</a>
							<%} else { %>
								완료
							<% } %>
						</td>
					</tr>
					<%} %>
					<%	if(list2.size() == 0 ){%>
			    	<tr>
			    		<td colspan="6" class="first">해당하는 설문지가 없습니다.</td>
					</tr>
					<% } %>
					</tbody>
				</table>
			</div>
			<!-- //board2 -->
		</div>
		<!-- //#subCont -->
	</div>
	<!-- //#contents -->
	<!-- 컨텐츠 영역 끝 -->
    </form>

</div>
<!--  //container-new 끝 //-->

 <%@ include file="/learn/user/2013/portal/include/footer.html"%>