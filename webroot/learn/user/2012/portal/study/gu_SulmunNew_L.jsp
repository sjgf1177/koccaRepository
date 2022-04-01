<%
//**********************************************************
//  1. 제      목: EDUCATION SUBJECT PAGE
//  2. 프로그램명: gu_SulmunNew_L.jsp
//  3. 개      요: 설문
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//  7. 수      정:
//***********************************************************
%>
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
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) box = RequestManager.getBox(request);

	box.put("topmenu","2");
	box.put("submenu","6");

    ArrayList list1     = null;
    ArrayList list2     = null;
    ArrayList list3     = null;
    ArrayList list4     = null;    

    list1 = (ArrayList)request.getAttribute("EducationSubjectList1");  //과정설문
    list2 = (ArrayList)request.getAttribute("SulmunCommon");			//일반설문
    list3 = (ArrayList)request.getAttribute("SulmunRegist");		 //가입경로설문내용
    list4 = (ArrayList)request.getAttribute("SulmunTarget");      //대상자 설문내용
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
// Tab 선택시 동작
function showTab(clickTab)
{
	g_showTab(clickTab, 4);
}

function g_showTab(tabNo, count)
{

	var clickTabNo = new Number(tabNo);
	var tabCount   = new Number(count);

	if( clickTabNo == 0 || tabCount == 0 ) return;

	for(i=1; i<=tabCount; i++)
    {
		div = document.all["tab_"+i];

		if(!div) return;

		if( clickTabNo == i ) {
			// 활성화된 tab 클릭시 return
			if(div.style.display == "") return;
			div.style.display = ""
		}
		else {
			// 선택되지 않는 탭들은 깜빡임 방지.
			if(div.style.display != "none") {
				div.style.display = "none";
			}
		}
	}
}

// 콘텐츠 설문응시
function contentsWright(p_subj, p_grcode, p_gyear, p_subjseq, p_sulpapernum, p_sulpapernm, p_sulstart, p_sulend){
        document.form1.p_subj.value = 'CONTENTS';
        document.form1.s_subj.value = p_subj;
        document.form1.p_grcode.value = p_grcode;
        document.form1.p_gyear.value = p_gyear;
        document.form1.p_subjseq.value = p_subjseq;
        document.form1.p_sulpapernum.value = p_sulpapernum;
        document.form1.p_sulpapernm.value = p_sulpapernm;
		document.form1.p_sulstart.value = p_sulstart;
        document.form1.p_sulend.value = p_sulend;
        document.form1.p_listok.value = '0';

        window.self.name = "winSelectView";
        farwindow = window.open("", "openSubjChange", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 680, height = 538, top=0, left=0");
        document.form1.target = "openSubjChange";
        document.form1.action = "/servlet/controller.research.SulmunContentsUserServlet";
        document.form1.p_process.value = "SulmunUserPaperListPage";
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
}

// 과정 설문응시
function contentsWright1(p_subjnm, p_subj, p_grcode, p_gyear, p_subjseq, p_sulpapernum, p_sulpapernm, p_sulstart, p_sulend, p_sulnums){
        document.form1.p_subj.value = "ALL";
        document.form1.s_subj.value = p_subj;   // tz_suleach 들어갈 과정코드
        document.form1.p_subjnm.value = p_subjnm;
        document.form1.p_grcode.value = "ALL";
        document.form1.s_grcode.value = p_grcode;    // tz_suleach 들어갈 교육그룹코드       
        document.form1.p_gyear.value = p_gyear;
        document.form1.p_subjseq.value = p_subjseq;
        document.form1.p_sulpapernum.value = p_sulpapernum;
        document.form1.p_sulpapernm.value = p_sulpapernm;
		document.form1.p_sulstart.value = p_sulstart;
        document.form1.p_sulend.value = p_sulend;
        document.form1.p_sulnums.value = p_sulnums;    // 문제번호    
        document.form1.p_listok.value = '0';

        window.self.name = "winSelectView";
        farwindow = window.open("", "openSubjChange", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 680, height = 538, top=0, left=0");
        document.form1.target = "openSubjChange";
        document.form1.action = "/servlet/controller.research.SulmunSubjUserServlet";
        document.form1.p_process.value = "SulmunUserPaperListPage";
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
}

// 대상자 설문응시
function targetWright(p_subj, p_grcode, p_gyear, p_subjseq, p_sulpapernum, p_sulpapernm, p_sulstart, p_sulend){
        document.form1.p_subj.value = p_subj;
        document.form1.p_grcode.value = p_grcode;
        document.form1.p_gyear.value = p_gyear;
        document.form1.p_subjseq.value = p_subjseq;
        document.form1.p_sulpapernum.value = p_sulpapernum;
        document.form1.p_sulpapernm.value = p_sulpapernm;
		document.form1.p_sulstart.value = p_sulstart;
        document.form1.p_sulend.value = p_sulend;
        document.form1.p_listok.value = '0';

        window.self.name = "winInsertPage";
        farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 680, height = 538, top=0, left=0");
        document.form1.target = "openWinInsert";
        document.form1.action = "/servlet/controller.research.SulmunTargetUserServlet";
        document.form1.p_process.value = "SulmunUserPaperListPage";
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
}



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
  //document.form1.p_process.value = 'SulmunUserListPage';
  document.form1.p_process.value = 'SulmunNew';
  document.form1.p_action.value  = p_action;
  document.form1.action = "/servlet/controller.research.SulmunSubjUserServlet";
  document.form1.submit();
  showTab(tab);
}
//-->
</script>
<!-- 스크립트영역종료 -->

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
    
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 나의강의실 ><u>나의 설문</u></td>
			</tr>
		</table>
		<div class="concept a06">
			<div class="titlemsg"><img src="/images/2012/sub/page1/title/my_survey.gif" alt="나의 설문"></div>
			<ul class="pagetab">
				<li><img src="/images/2012/sub/page1/tabmenu/learning_on.png" alt="과정"></li>
				<li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunGen" tabindex="171" title="교육과정 외 내용에 대한 설문 내역을 확인할 수 있습니다"><img src="/images/2012/sub/page1/tabmenu/general_off.png" alt="일반"></a></li>
			</ul>
		</div>
		<div class="myclass">
			<div class="listbar">
				<table class="listrable" cellpadding="0" cellspacing="0">
					<colgroup><col width="50"><col width="80"><col width="*"><col width="150"><col width="250"><col width="100"><col width="80"></colgroup>
					<tr class="listhead">
						<th>번호</th>
						<th>분류</th>
						<th>과정명</th>
						<th>과정기간</th>
						<th>설문주제</th>
						<th>현재진도율</th>
						<th>응시여부</th>
					</tr>
					
					<%
						for(int i = 0; i < list1.size(); i++) {
					
							DataBox dbox1 = (DataBox)list1.get(i);
						
							String v_isonoff       =  dbox1.getString("d_isonoff");
							String v_classname     =  dbox1.getString("d_classname");
							String v_subjnm        =  dbox1.getString("d_subjnm");
							String v_edustart      =  dbox1.getString("d_edustart");
							String v_eduend        =  dbox1.getString("d_eduend");
						                    
							String v_grcode        =  dbox1.getString("d_grcode");
							String v_subj          =  dbox1.getString("d_subj");
							String v_year          =  dbox1.getString("d_year");
							String v_subjseq       =  dbox1.getString("d_subjseq");
							String v_userid        =  dbox1.getString("d_userid");
							int    v_tstep         =  dbox1.getInt("d_tstep");    // 진도율
							String v_sulnums       =  dbox1.getString("d_sulnums"); 
							
							int	v_sulpapernum      =  dbox1.getInt("d_sulpapernum");
							String v_sulpapernm    =  dbox1.getString("d_sulpapernm");
							int v_progress         =  dbox1.getInt("d_progress"); // 설문응시제한진도율
							int v_eachcnt          =  dbox1.getInt("d_eachcnt");  // 응시여부
							                                
							v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
							v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");
					%>    


					<tr class="listmiddle" onmouseover="this.style.background='#fbfbfb'" onmouseout="this.style.background=''">
						<td class="linefirst"><%= i+ 1 %></td>
						<td><%= v_classname %></td>
						<td class="title"><%= v_subjnm %></td>
						<td><%= v_edustart %> ~<%= v_eduend %></td>
						<td><%= v_sulpapernm %></td>
						<td><%= v_tstep %>/<%= v_progress %></td>
						<td id="btxtfocus<%=i+1%>">
							<% if (v_eachcnt>0) { %>				
								완료
							<% } else { if(v_progress<=v_tstep) { %>
								<a href="javascript:contentsWright1('<%=v_subjnm%>', '<%=v_subj%>', '<%=v_grcode%>', '<%=v_year%>', '<%=v_subjseq%>', '<%=v_sulpapernum%>', '<%=v_sulpapernm%>', '', '','<%=v_sulnums%>')" tabindex="<%="2"+i+"1"%>">응시가능</a>
								<% } else { %>
									<a href="javascript:alert('설문응시 제한진도율보다 진도율이 부족합니다.');"  tabindex="<%="2"+i+"1"%>">응시가능</a>
							<% } } %>
						</td>
					</tr>
					<% } %>
					
			    <%	if(list1.size() == 0 ){%>
			    <tr>
			    	<td height="3" colspan="7" align="center" class="linefirst">설문내용이 없습니다.</td>
					</tr>
					<% } %>
				</table>
			</div>			
		</div>
	</div>
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
