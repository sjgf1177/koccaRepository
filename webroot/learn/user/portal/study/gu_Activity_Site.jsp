<%
//**********************************************************
//  1. 제      목: EDUCATION SUBJECT PAGE
//  2. 프로그램명: zu_Activity_Site.jsp
//  3. 개      요: My activity
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정경진 2005. 7. 25
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String v_process = box.getString("p_process");
    String v_grcode     = "";
    String v_gubun      = "";
    String v_gubunname  = "";
    String v_menuid     = "";
    String v_menuname   = "";
    String v_gubun1 = "";
    int    v_cnt        = 0;
    int    v_gubuncnt   = 0;
    int    v_gubuntot   = 0;

    ArrayList list1 = (ArrayList)request.getAttribute("selectActivity");
    //ArrayList list2 = (ArrayList)request.getAttribute("memberView");
    DataBox dbox2 = (DataBox)request.getAttribute("memberView");
    ArrayList list3 = (ArrayList)request.getAttribute("boardCnt");

    DataBox dbox3 = (DataBox)list3.get(0);
    //DataBox dbox2 = (DataBox)list2.get(0);

%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="form1" method="post" action="">
<input type="hidden" name="p_process" value="">
</form>

			<h2><img src="/images/portal/myclass/h2_tit8.gif" alt="나의학습활동" class="fl_l" /><p class="category">Home > 나의강의실 > <strong>나의학습활동</strong></p></h2>
 
			<p><img src="/images/portal/myclass/mystudy_txt.gif" alt="사이트 접속 기록으로 메뉴별 접근횟수, 최초/최종 로그인 일자, 총 로그인 횟수 등을 확인하실 수 있습니다." /></p>
						
			<ul class="tabwrap mg_t30">
				<li><a class="tab_myclass tab_myclass_on"><span>사이트활동</span></a></li>
				<li><a href="/servlet/controller.system.StudyCountServlet?p_process=myActivity" class="tab_myclass"><span>학습활동</span></a></li>
			</ul>
			
			<table class="myclass_mystudy_view">
			<colgroup><col width="85px" /><col width="180px" /><col width="85px" /><col width="180px" /><col width="85px" /><col width="75px" /></colgroup>
			<tbody>
			<tr class="bg_green">
				<th>최초 로그인</th>
				<td class="num"><%= FormatDate.getFormatDate(dbox2.getString("d_lgfirst"),"yyyy.MM.dd HH:mm") %></td>
				<th>최종 로그인</th>
				<td class="num"><%= FormatDate.getFormatDate(dbox2.getString("d_lglast"),"yyyy.MM.dd HH:mm") %></td>
				<th>총 로그인수</th>
				<td class="fs_s"><%= new java.text.DecimalFormat("###,###,##0").format(dbox2.getInt("d_lgcnt")) %></td>
			</tr>
			</tbody>
			</table>
			
			<div class="myclass_mystudy_datawrap mg_t35">
				<div class="left">
					<h3><img src="/images/portal/myclass/stit_online.gif" alt="온라인과정" /></h3>
					<ul>
<%
	int  j = 0;
	j = 0;
	for(int i = 0; i < list1.size(); i++) {
		DataBox dbox = (DataBox)list1.get(i);

		if(dbox.getString("d_gubun").equals("1")){

			v_gubun = dbox.getString("d_gubun");
			v_menuid = dbox.getString("d_menuid");
			v_menuname = dbox.getString("d_menuname");
			v_cnt = dbox.getInt("d_cnt");
			v_gubuncnt = dbox.getInt("d_gubuncnt");
			v_gubuntot += v_cnt;
%>
						<li><%= v_menuname %><span><%= new java.text.DecimalFormat("###,###,##0").format(v_cnt) %>회</span></li>
<%
      }
    }
%>
					</ul>
				</div>
				<div class="right">
					<h3><img src="/images/portal/myclass/stit_offline.gif" alt="오프라인과정" /></h3>
					<ul>
<%
	j = 0;
	for(int i = 0; i < list1.size(); i++) {
		DataBox dbox = (DataBox)list1.get(i);

		if(dbox.getString("d_gubun").equals("2")){

			v_gubun = dbox.getString("d_gubun");
			v_menuid = dbox.getString("d_menuid");
			v_menuname = dbox.getString("d_menuname");
			v_cnt = dbox.getInt("d_cnt");
			v_gubuncnt = dbox.getInt("d_gubuncnt");
			v_gubuntot += v_cnt;
%>
						<li><%= v_menuname %><span><%= new java.text.DecimalFormat("###,###,##0").format(v_cnt) %>회</span></li>
<%
      }
    }
%>
					</ul>
				</div>
			</div>
			
			<div class="myclass_mystudy_datawrap mg_t40">
				<div class="left">
					<h3><img src="/images/portal/myclass/stit_myclass.gif" alt="나의강의실" /></h3>
					<ul>
<%
	j = 0;
	for(int i = 0; i < list1.size(); i++) {
		DataBox dbox = (DataBox)list1.get(i);

		if(dbox.getString("d_gubun").equals("3")){

			v_gubun = dbox.getString("d_gubun");
			v_menuid = dbox.getString("d_menuid");
			v_menuname = dbox.getString("d_menuname");
			v_cnt = dbox.getInt("d_cnt");
			v_gubuncnt = dbox.getInt("d_gubuncnt");
			v_gubuntot += v_cnt;
%>
						<li><%= v_menuname %><span><%= new java.text.DecimalFormat("###,###,##0").format(v_cnt) %>회</span></li>
<%
      }
    }
%>
					</ul>
					
				</div>
				<div class="right">
					<h3><img src="/images/portal/myclass/stit_styudying.gif" alt="학습지원센터" /></h3>
					<ul>
<%
	j = 0;
	for(int i = 0; i < list1.size(); i++) {
		DataBox dbox = (DataBox)list1.get(i);

		if(dbox.getString("d_gubun").equals("4")){

			v_gubun = dbox.getString("d_gubun");
			v_menuid = dbox.getString("d_menuid");
			v_menuname = dbox.getString("d_menuname");
			v_cnt = dbox.getInt("d_cnt");
			v_gubuncnt = dbox.getInt("d_gubuncnt");
			v_gubuntot += v_cnt;
%>
						<li><%= v_menuname %><span><%= new java.text.DecimalFormat("###,###,##0").format(v_cnt) %>회</span></li>
<%
      }
    }
%>
					</ul>
				</div>
			</div>
			
			<div class="myclass_mystudy_datawrap mg_t35">
				<div class="left">
					<h3><img src="/images/portal/myclass/stit_information.gif" alt="정보광장" /></h3>
					<ul>
<%
	j = 0;
	for(int i = 0; i < list1.size(); i++) {
		DataBox dbox = (DataBox)list1.get(i);

		if(dbox.getString("d_gubun").equals("5")){

			v_gubun = dbox.getString("d_gubun");
			v_menuid = dbox.getString("d_menuid");
			v_menuname = dbox.getString("d_menuname");
			v_cnt = dbox.getInt("d_cnt");
			v_gubuncnt = dbox.getInt("d_gubuncnt");
			v_gubuntot += v_cnt;
%>
						<li><%= v_menuname %><span><%= new java.text.DecimalFormat("###,###,##0").format(v_cnt) %>회</span></li>
<%
      }
    }
%>
					</ul>
					
				</div>
				<div class="right">
					<h3><img src="/images/portal/myclass/stit_academy.gif" alt="아카데미소개" /></h3>
					<ul>
<%
	j = 0;
	for(int i = 0; i < list1.size(); i++) {
		DataBox dbox = (DataBox)list1.get(i);

		if(dbox.getString("d_gubun").equals("7")){

			v_gubun = dbox.getString("d_gubun");
			v_menuid = dbox.getString("d_menuid");
			v_menuname = dbox.getString("d_menuname");
			v_cnt = dbox.getInt("d_cnt");
			v_gubuncnt = dbox.getInt("d_gubuncnt");
			v_gubuntot += v_cnt;
%>
						<li><%= v_menuname %><span><%= new java.text.DecimalFormat("###,###,##0").format(v_cnt) %>회</span></li>
<%
      }
    }
%>
					</ul>
				</div>
			</div>
								
		</div>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->