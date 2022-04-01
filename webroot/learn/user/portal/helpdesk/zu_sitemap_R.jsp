<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_process     = box.getString("p_process");
    String  v_tem_grcode   = box.getSession("tem_grcode");
    
    String v_content    = "";
    String v_code       = "";
    String v_title      = "";
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
    	v_content    = dbox.getString("d_content");
        v_code       = dbox.getString("d_code");
        v_title      = dbox.getString("d_title");
     }
    
    
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT>
var old_menu = ''; var old_cell = '';
function comment( submenu, cellbar) {

	if( old_menu != submenu ) {

		if( old_menu !='' ) {
			old_menu.style.display = 'none';
		}
		submenu.style.display = 'block';
		old_menu = submenu;
		old_cell = cellbar;

	} else {
		submenu.style.display = 'none';
		old_menu = '';
		old_cell = '';
	}
}

</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1" enctype = "multipart/form-data" method = "post">
		<input type = "hidden" name = "p_process"   value = "">
		<input type = "hidden" name = "p_tab"		value = "">
		<input type = "hidden" name = "p_code"      value = "">
		
		<%if( box.getSession("tem_grcode").equals("N000001")) { %>
		
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td>
		
		<table width="672" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="30" colspan="2" class="h_road">&nbsp;</td>
        </tr>
        <tr>
          <td><img src="/images/portal/homepage_renewal//member/stitle_11.gif" alt="사이트맵" /></td>
          <td class="h_road">Home &gt; 이용안내 &gt; <strong>사이트맵</strong></td>
        </tr>
        <tr>
          <td height="12" colspan="2"></td>
        </tr>
        <tr>
          <td height="1" colspan="2" bgcolor="E5E5E5"></td>
        </tr>
      </table>
		
		<!--타이틀부분//-->
      <table width="672" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/portal/homepage_renewal/member/sitemap.jpg" border="0" usemap="#Map" /> </td>
        </tr>
        <tr>
          <td></td>
        </tr>
    </table>

<map name="Map" id="Map">

<!-- 나의 강의실 -->
  <area shape="rect" coords="23,51,104,72" href="javascript:menuMainForward('3','/servlet/controller.study.MyClassServlet?p_process=EducationSubjectPage');" alt="나의 강의실 홈" />
  <area shape="rect" coords="23,75,104,96" href="javascript:menuForward('3','11');" alt="수강중인 과정" />
  <area shape="rect" coords="23,100,144,121" href="javascript:menuForward('3','02');" alt="나의교육이력" />
  <area shape="rect" coords="23,125,144,146" href="javascript:menuForward('3','18');" alt="장바구니" />
  <area shape="rect" coords="24,151,144,172" href="javascript:menuForward('3','17');" alt="교육신청 확인/취소" />
  <area shape="rect" coords="24,176,144,197" href="javascript:menuForward('3','19');" alt="수강료 결재 조회/납부" />
  <area shape="rect" coords="25,201,144,222" href="javascript:menuForward('3','12');" alt="나의 상담내역" />
  <area shape="rect" coords="24,226,144,247" href="javascript:menuForward('3','07');" alt="나의 설문" />


<!-- 온라인 과정 -->
  <area shape="rect" coords="203,52,284,73" href="javascript:menuMainForward('1','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList');" alt="온라인 전체 교육과정" />
  <area shape="rect" coords="202,76,283,97" href="javascript:menuForward('1','14');" alt="기획" />
  <area shape="rect" coords="202,101,283,122" href="javascript:menuForward('1','15');" alt="제작" />
  <area shape="rect" coords="202,126,283,147" href="javascript:menuForward('1','16');" alt="유통" />
  <area shape="rect" coords="202,151,283,172" href="javascript:menuForward('1','17');" alt="경영지원" />
  <area shape="rect" coords="203,177,284,198" href="javascript:menuForward('1','13');" alt="수강신청 안내" />


<!-- 오프라인 과정 -->
  <area shape="rect" coords="380,52,461,73" href="javascript:menuMainForward('2','/servlet/controller.off.ProposeOffServlet?p_process=SubjectList');" alt="오프라인 전체교육과정" />
  <area shape="rect" coords="380,76,461,97" href="javascript:menuForward('2','01');" alt="방송영상" />
  <area shape="rect" coords="380,101,461,122" href="javascript:menuForward('2','02');" alt="게임제작" />
  <area shape="rect" coords="380,126,461,147" href="javascript:menuForward('2','03');" alt="기획창작" />
  <area shape="rect" coords="380,152,461,173" href="javascript:menuForward('2','04');" alt="교육자료실" />
  <area shape="rect" coords="381,177,462,198" href="javascript:menuForward('2','05');" alt="연간교육일정" />
  <area shape="rect" coords="381,201,462,222" href="javascript:menuForward('2','06');" alt="월별교육일정" />
  <area shape="rect" coords="381,227,462,248" href="javascript:menuForward('2','07');" alt="수강신청 안내" />


<!-- 학습지원안내 -->
  <area shape="rect" coords="558,51,650,72" href="javascript:menuForward('4','07');" alt="공지사항" />
  <area shape="rect" coords="558,75,639,96" href="javascript:menuForward('4','01');" alt="FAQ" />
  <area shape="rect" coords="558,100,639,121" href="javascript:menuForward('4','02');" alt="Q/A" />
  <area shape="rect" coords="558,125,639,146" href="javascript:menuForward('4','03');" alt="S/W다운로드" />
  <area shape="rect" coords="558,151,639,172" href="javascript:menuForward('4','05');" alt="학습환경 도우미" />
  <area shape="rect" coords="559,176,640,197" href="javascript:menuForward('4','09');" alt="1:1문의" />
  <area shape="rect" coords="558,200,646,222" href="javascript:menuForward('4','10');" alt="교재안내" />
  <area shape="rect" coords="558,226,639,247" href="javascript:menuForward('4','12');" alt="이벤트 존" />


<!-- 골드클래스 -->
  <area shape="rect" coords="25,342,106,363" href="javascript:menuMainForward('5','/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage');" alt="전체교육과정" />
  <area shape="rect" coords="25,368,106,389" href="javascript:menuForward('5','12');" alt="방송영상" />
  <area shape="rect" coords="27,393,108,414" href="javascript:menuForward('5','13');" alt="영화" />
  <area shape="rect" coords="26,417,114,439" href="javascript:menuForward('5','14');" alt="다큐멘터리" />
  <area shape="rect" coords="26,442,107,463" href="javascript:menuForward('5','15');" alt="문화예술" />
  <area shape="rect" coords="26,466,107,485" href="javascript:menuForward('5','16');" alt="기타" />


<!-- 아카데미소개 -->
  <area shape="rect" coords="203,343,284,364" href="javascript:menuForward('7','01');" alt="원장 인사말" />
  <area shape="rect" coords="203,369,284,390" href="javascript:menuForward('7','02');" alt="설립목적/비전" />
  <area shape="rect" coords="205,394,286,415" href="javascript:menuForward('7','05');" alt="교육사업 안내" />
  <area shape="rect" coords="204,418,286,440" href="javascript:menuForward('7','03');" alt="교육시설 안내" />
  <area shape="rect" coords="204,443,303,465" href="javascript:menuForward('7','06');" alt="교육협력기관 안내" />
  <area shape="rect" coords="204,468,286,490" href="javascript:menuForward('7','04');" alt="오시는 길" />



<!-- 이용안내 -->
  <area shape="rect" coords="379,342,460,363" href="javascript:menuForward('90','01');" alt="회원가입" />
  <area shape="rect" coords="379,368,497,389" href="javascript:menuForward('90','02');" alt="아이디/패스워드 찾기" />
  <area shape="rect" coords="380,393,461,414" href="javascript:menuForward('90','03');" alt="회원정보 수정" />
  <area shape="rect" coords="380,416,462,438" href="javascript:menuForward('90','07');" alt="비밀번호 변경" />
  <area shape="rect" coords="380,442,462,464" href="javascript:menuForward('90','04');" alt="회원탈퇴" />
  <area shape="rect" coords="380,466,481,488" href="javascript:menuForward('90','05');" alt="이용약관" />
  <area shape="rect" coords="380,491,481,513" href="javascript:menuForward('90','06');" alt="개인정보 처리방침" />

</map>

</td></tr></table>
		
		<%}else{ %>

    <h2><img src="/images/portal/studying/h2_tit10.gif" alt="사이트맵" class="fl_l" /><p class="category">Home > 학습지원센터 > <strong>사이트맵</strong></p></h2>

    <% if(mainMenuList != null || mainMenuList.size() > 0 ) {
           for(int top_i = 0 ; top_i <  mainMenuList.size() ; top_i++){
               DataBox mainBbox = (DataBox)mainMenuList.get(top_i);
           
               mMenuGrtype  = mainBbox.getString("d_grtype");
               mMenuGubun   = mainBbox.getString("d_gubun");
               mMenuId      = mainBbox.getString("d_menuid");
               mMenuName    = mainBbox.getString("d_menuname");
               mMenuUrl     = mainBbox.getString("d_menuurl");
               mMenuImg     = mainBbox.getString("d_menuimg");
               mMenuOverImg = mainBbox.getString("d_menuoverimg");
        %>
        <dl class="sitemap">
        <dt><img src="/images/portal/studying/sitemap_tit<%=mMenuGubun %>.gif" alt="<%=mMenuName %>" /></dt>
        <dd><a href="javascript:menuMainForward('<%=mMenuGubun%>','<%=mMenuUrl %>');"><%=mMenuName %> 홈</a></dd>
        <%      
                for(int top_j = 0 ; top_j < subMenuList.size(); top_j++) {
                    DataBox subBox = (DataBox)subMenuList.get(top_j);

                    sMenuGubun   = subBox.getString("d_gubun");
                    sMenuId      = subBox.getString("d_menuid");
                    sMenuName    = subBox.getString("d_menuname");
                    sMenuUrl     = subBox.getString("d_menuurl");
                    sMenuImg     = subBox.getString("d_menuimg");
                    sMenuOverImg = subBox.getString("d_menuoverimg");
                    sSubImg       = subBox.getString("d_subimg");
                    sSubOverImg   = subBox.getString("d_suboverimg");
                    sMenuImgPath  = StringUtil.replaceAll(subBox.getString("d_imgpath"), '\\', '/');
                    
                   if(sMenuGubun.equals(mMenuGubun)) {
        %>
                    <dd><a href="javascript:menuForward('<%=sMenuGubun %>', '<%=sMenuId %>');"><%=sMenuName %></a></dd>
        <%        
                   }
               }%>
        </dl>
        <%
                }
           }
        %>
        
        <%} %>

</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->