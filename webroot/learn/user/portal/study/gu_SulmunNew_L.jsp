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

	String v_tab = box.getString("p_tab");
	if ("".equals(v_tab)) {
		v_tab = "1";
	}

    ArrayList list1     = null;
    ArrayList list2     = null;
    ArrayList list3     = null;
    ArrayList list4     = null;    

    list1 = (ArrayList)request.getAttribute("EducationSubjectList1");  //과정설문
    list2 = (ArrayList)request.getAttribute("SulmunCommon");			//일반설문
    list3 = (ArrayList)request.getAttribute("SulmunRegist");		 //가입경로설문내용
    list4 = (ArrayList)request.getAttribute("SulmunTarget");      //대상자 설문내용
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
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
    
    	<%if( box.getSession("tem_grcode").equals("N000001")) { %>
	<table>   
      <tr>
      	<td>
      	
      	<!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal//myclass/stitle_05.gif" alt="나의 설문" /></td>
        <td class="h_road">Home &gt; 나의 강의실  &gt; <strong>나의 설문</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal//myclass/bar_04.jpg" width="672" height="104" /></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
    <!--타이틀부분//-->
         
      
      <!--테이블부분-->
      <div id="tab_1" <%= !"1".equals(v_tab)?" style=\"display:none\"":"" %>>
       <!--탭부분-->
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>     
          <td><a href="javascript:showTab(1)" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image53','','/images/portal/homepage_renewal//myclass/tab_on_01.gif',1)"><img src="/images/portal/homepage_renewal/myclass/tab_on_01.gif" name="Image53" width="168" height="39" border="0" id="Image53" /></a></td>
          <td><a href="javascript:showTab(2)" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image54','','/images/portal/homepage_renewal//myclass/tab_on_02.gif',1)"><img src="/images/portal/homepage_renewal/myclass/tab_off_02.gif" name="Image54" width="168" height="39" border="0" id="Image54" /></a></td>        
        </tr>
      </table>
      <!--탭부분//-->
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="tit_table">번호</td>
          <td class="tit_table">분류</td>
          <td class="tit_table">과정명</td>
          <td class="tit_table">과정기간</td>
          <td class="tit_table">설문지명</td>
          <td class="tit_table">현재진도율</td>
          <td class="tit_table_right">응시여부</td>
        </tr>
        <%
	//System.out.println("## list1.size():"+list1.size());
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
        <tr>
          <td class="ct_tit_table"><%= i+ 1 %></td>
          <td class="ct_tit_table"><%= v_classname %></td>
          <td class="ct_tit_table_left"><%= v_subjnm %></td>
          <td class="ct_tit_table"><%= v_edustart %> ~<br /><%= v_eduend %></td>
          <td class="ct_tit_table"><%= v_sulpapernm %></td>
          <td class="ct_tit_table"><%= v_tstep %>/<%= v_progress %></td>
          <td class="ct_tit_table_right">
          <%
		if (v_eachcnt>0) {
%>				
					<a class="state" style="cursor:none"><span class="finish">완료</span></a>
<%
		} else {
			if(v_progress<=v_tstep){
%>
					<a href="javascript:contentsWright1('<%=v_subjnm%>', '<%=v_subj%>', '<%=v_grcode%>', '<%=v_year%>', '<%=v_subjseq%>', '<%=v_sulpapernum%>', '<%=v_sulpapernm%>', '', '','<%=v_sulnums%>')" class="state"><span class="ing">응시가능</span></a>
<%
			} else {
				%>
					<a href="javascript:alert('설문응시 제한진도율보다 진도율이 부족합니다.');" class="state"><span class="ing">응시가능</span></a>
<%
			}
		}
%>
          </td>
        </tr>
        <%} %>
        <%	if(list1.size() == 0 ){%>
       	    <tr>
		    	<td height="3" colspan="7" align="center">설문내용이 없습니다.</td>
		    </tr>
<%	}%>
        <tr>
          <td height="1" colspan="7" bgcolor="CACACA"></td>
        </tr>
        <tr>
          <td height="4" colspan="7" bgcolor="F7F7F7"></td>
        </tr>
      </table>
      </div>
      
      <div id="tab_2" <%= !"2".equals(v_tab)?" style=\"display:none\"":"" %>>
       <!--탭부분-->
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>     
          <td><a href="javascript:showTab(1)" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image55','','/images/portal/homepage_renewal//myclass/tab_on_01.gif',1)"><img src="/images/portal/homepage_renewal/myclass/tab_off_01.gif" name="Image55" width="168" height="39" border="0" id="Image55" /></a></td>
          <td><a href="javascript:showTab(2)" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image56','','/images/portal/homepage_renewal//myclass/tab_on_02.gif',1)"><img src="/images/portal/homepage_renewal/myclass/tab_on_02.gif" name="Image56" width="168" height="39" border="0" id="Image56" /></a></td>        
        </tr>
      </table>
      <!--탭부분//-->
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="tit_table">번호</td>
          <td class="tit_table">설문지명</td>
          <td class="tit_table">설문기간</td>
          <td class="tit_table">문제수</td>
          <td class="tit_table">응시여부</td>
        </tr>
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
          <td class="ct_tit_table"><%= i+ 1 %></td>
          <td class="ct_tit_table_left"><%= v_sulpapernm %></td>
          <td class="ct_tit_table"><%= v_sulstart %> ~ <%= v_sulend %></td>
          <td class="ct_tit_table"><%= v_totcnt %></td>
          <td class="ct_tit_table">
          <%
		if (v_issul.equals("0")) {
%>
				<a href="javascript:commonWright('COMMON', '<%=dbox2.getString("d_grcode")%>', '<%=v_sulpapernum%>')" class="state"><span class="ing">응시가능</span></a>
<%
		} else {
%>
				<a class="state" style="cursor:none"><span class="finish">완료</span></a>
<%
		}
%>
          </td>
        </tr>
        <%} %>
        <%	if(list2.size() == 0 ){%>
       	    <tr>
		    	<td height="3" colspan="5" align="center">설문내용이 없습니다.</td>
		    </tr>
<%	}%>
        <tr>
          <td height="1" colspan="7" bgcolor="CACACA"></td>
        </tr>
        <tr>
          <td height="4" colspan="7" bgcolor="F7F7F7"></td>
        </tr>
      </table>
      </div>

    </td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
      	
      	<%}else{ %>

			<h2><img src="/images/portal/myclass/h2_tit9.gif" alt="나의설문" class="fl_l" /><p class="category">Home > 나의강의실 > <strong>나의설문</strong></p></h2>
 
			<p><img src="/images/portal/myclass/mypoll_txt.gif" alt="본인이 참여한 설문 목록 및 응시해야하는 설문을 확인하실 수 있습니다." /></p>
			
			<div id="tab_1" <%= !"1".equals(v_tab)?" style=\"display:none\"":"" %>>

			<ul class="tabwrap mg_t30">
				<li><a href="javascript:showTab(1)" class="tab_myclass tab_myclass_on"><span>과정</span></a></li>
				<li><a href="javascript:showTab(2)" class="tab_myclass"><span>일반</span></a></li>
				<li><a href="javascript:showTab(3)" class="tab_myclass"><span>가입경로</span></a></li>
				<li><a href="javascript:showTab(4)" class="tab_myclass"><span>대상자</span></a></li>
			</ul>

			<table class="board_list">
			<colgroup><col width="52px" /><col width="90px" /><col width="197px" /><col width="90px" /><col width="104px" /><col width="87px" /><col width="70px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th><p>번호</p></th>
				<th><p>분류</p></th>
				<th><p>과정명</p></th>
				<th><p>교육기간</p></th>
				<th><p>설문지명</p></th>
				<th><p>진도율비교</p></th>
				<th class="end"><p>상태</p></th>
			</tr>
			</thead>
			<tbody>
<%
	//System.out.println("## list1.size():"+list1.size());
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
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td class="num"><%= i+ 1 %></td>
				<td><%= v_classname %></td>
				<td class="ta_l"><%= v_subjnm %></td>
				<td class="num ta_l"><%= v_edustart %> ~<br /><%= v_eduend %></td>
				<td><%= v_sulpapernm %></td>
				<td class="num"><%= v_tstep %>/<%= v_progress %></td>
				<td>
<%
		if (v_eachcnt>0) {
%>				
					<a class="state" style="cursor:none"><span class="finish">완료</span></a>
<%
		} else {
			if(v_progress<=v_tstep){
%>
					<a href="javascript:contentsWright1('<%=v_subjnm%>', '<%=v_subj%>', '<%=v_grcode%>', '<%=v_year%>', '<%=v_subjseq%>', '<%=v_sulpapernum%>', '<%=v_sulpapernm%>', '', '','<%=v_sulnums%>')" class="state"><span class="ing">응시가능</span></a>
<%
			} else {
				%>
					<a href="javascript:alert('설문응시 제한진도율보다 진도율이 부족합니다.');" class="state"><span class="ing">응시가능</span></a>
<%
			}
		}
%>
				</td>
			</tr>
<%
	}
%>
<%	if(list1.size() == 0 ){%>
       	    <tr>
		    	<td height="3" colspan="7" align="center">설문내용이 없습니다.</td>
		    </tr>
<%	}%>
			</tbody>
			</table>
			</div>
			<div id="tab_2" <%= !"2".equals(v_tab)?" style=\"display:none\"":"" %>>

			<ul class="tabwrap mg_t30">
				<li><a href="javascript:showTab(1)" class="tab_myclass"><span>과정</span></a></li>
				<li><a href="javascript:showTab(2)" class="tab_myclass tab_myclass_on"><span>일반</span></a></li>
				<li><a href="javascript:showTab(3)" class="tab_myclass"><span>가입경로</span></a></li>
				<li><a href="javascript:showTab(4)" class="tab_myclass"><span>대상자</span></a></li>
			</ul>

			<table class="board_list">
			<colgroup><col width="52px" /><col width="350px" /><col width="148px" /><col width="70px" /><col width="70px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th><p>번호</p></th>
				<th><p>설문지명</p></th>
				<th><p>설문기간</p></th>
				<th><p>문제수</p></th>
				<th class="end"><p>상태</p></th>
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
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td class="num"><%= i + 1 %></td>
				<td class="ta_l"><%= v_sulpapernm %></td>
				<td class="num"><%= v_sulstart %> ~ <%= v_sulend %></td>
				<td class="num"><%= v_totcnt %></td>
				<td>
<%
		if (v_issul.equals("0")) {
%>
				<a href="javascript:commonWright('COMMON', '<%=dbox2.getString("d_grcode")%>', '<%=v_sulpapernum%>')" class="state"><span class="ing">응시가능</span></a>
<%
		} else {
%>
				<a class="state" style="cursor:none"><span class="finish">완료</span></a>
<%
		}
%>
				</td>
			</tr>
<%
	}
%>
<%	if(list2.size() == 0 ){%>
       	    <tr>
		    	<td height="3" colspan="5" align="center">설문내용이 없습니다.</td>
		    </tr>
<%	}%>
			</tbody>
			</table>

			</div>
			<div id="tab_3" <%= !"3".equals(v_tab)?" style=\"display:none\"":"" %>>
			
			<ul class="tabwrap mg_t30">
				<li><a href="javascript:showTab(1)" class="tab_myclass"><span>과정</span></a></li>
				<li><a href="javascript:showTab(2)" class="tab_myclass"><span>일반</span></a></li>
				<li><a href="javascript:showTab(3)" class="tab_myclass tab_myclass_on"><span>가입경로</span></a></li>
				<li><a href="javascript:showTab(4)" class="tab_myclass"><span>대상자</span></a></li>
			</ul>

			<table class="board_list">
			<colgroup><col width="52px" /><col width="90px" /><col width="218px" /><col width="90px" /><col width="180px" /><col width="70px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th><p>번호</p></th>
				<th><p>분류</p></th>
				<th><p>과정명</p></th>
				<th><p>교육기간</p></th>
				<th><p>설문지명</p></th>
				<th class="end"><p>상태</p></th>
			</tr>
			</thead>
			<tbody>
<%
	for(int i = 0; i < list3.size(); i++) {
		
		DataBox dbox3 = (DataBox)list3.get(i);
	
		String v_isonoff       =  dbox3.getString("d_isonoff");
		String v_classname     =  dbox3.getString("d_classname");
		String v_subjnm        =  dbox3.getString("d_subjnm");
		String v_subj          =  dbox3.getString("p_subj");
		String v_grcode        =  dbox3.getString("d_grcode");
		String v_year          =  dbox3.getString("p_year");
		String v_subjseq       =  dbox3.getString("p_subjseq");
		int v_sulpapernum      =  dbox3.getInt("d_sulpapernum");
		String v_sulpapernm    =  dbox3.getString("d_sulpapernm");
		String v_sulstart      =  dbox3.getString("d_sulstart");
		String v_sulend        =  dbox3.getString("d_sulend");
		String v_edustart      =  dbox3.getString("d_edustart");
		String v_eduend        =  dbox3.getString("d_eduend");
		v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
		v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");
%>    
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td class="num"><%= i + 1 %></td>
				<td><%= v_classname %></td>
				<td class="ta_l"><%= v_subjnm %></td>
				<td class="num ta_l"><%= v_edustart %> ~<br /><%= v_eduend %></td>
				<td class="ta_l"><%= v_sulpapernm %></td>
				<td>
<%
		if(dbox3.getString("d_contentsdata").equals("0")) {
%>
				<a href="javascript:contentsWright('<%=v_subj%>', 'ALL', '<%=v_year%>', '<%=v_subjseq%>', '<%=v_sulpapernum%>', '<%=v_sulpapernm%>', '<%=v_sulstart%>', '<%=v_sulend%>')" class="state"><span class="ing">응시가능</span></a>
<%
		} else {
%>
				<a class="state" style="cursor:none"><span class="finish">완료</span></a>
<%
		}
%>
				</td>
			</tr>
<%
	}
%>
<%	if(list3.size() == 0 ){%>
       	    <tr>
		    	<td height="3" colspan="6" align="center">설문내용이 없습니다.</td>
		    </tr>
<%	}%>
			</tbody>
			</table>
			</div>
			<div id="tab_4" <%= !"4".equals(v_tab)?" style=\"display:none\"":"" %>>

			<ul class="tabwrap mg_t30">
				<li><a href="javascript:showTab(1)" class="tab_myclass"><span>과정</span></a></li>
				<li><a href="javascript:showTab(2)" class="tab_myclass"><span>일반</span></a></li>
				<li><a href="javascript:showTab(3)" class="tab_myclass"><span>가입경로</span></a></li>
				<li><a href="javascript:showTab(4)" class="tab_myclass tab_myclass_on"><span>대상자</span></a></li>
			</ul>

			<table class="board_list">
			<colgroup><col width="52px" /><col width="350px" /><col width="148px" /><col width="70px" /><col width="70px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th><p>번호</p></th>
				<th><p>설문지명</p></th>
				<th><p>설문기간</p></th>
				<th><p>문제수</p></th>
				<th class="end"><p>상태</p></th>
			</tr>
			</thead>
			<tbody>
<%
	for(int i = 0; i < list4.size(); i++) {
		DataBox dbox4 = (DataBox)list4.get(i);
		int v_sulpapernum      =  dbox4.getInt("d_sulpapernum");
		String v_sulpapernm    =  dbox4.getString("d_sulpapernm");
		String v_sulstart      =  dbox4.getString("d_sulstart");
		String v_sulend        =  dbox4.getString("d_sulend");
		int v_totcnt           =  dbox4.getInt("d_totcnt");
		v_sulstart      = FormatDate.getFormatDate(v_sulstart,"yyyy.MM.dd");
		v_sulend        = FormatDate.getFormatDate(v_sulend,"yyyy.MM.dd");
		int v_sulresult = dbox4.getInt("d_sulresult");       // 설문결과여부 (1, 0)
		String v_issul  = dbox4.getString("d_issul");        // 설문가능여부 (Y, N)                               
%>    
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td class="num"><%= i + 1 %></td>
				<td class="ta_l"><%= v_sulpapernm %></td>
				<td class="num"><%= v_sulstart %> ~ <%= v_sulend %></td>
				<td class="fs_s"><%= v_totcnt %></td>
				<td>
<%
		if(v_sulresult==0 && v_issul.equals("Y")) {
%>
				<a href="javascript:targetWright('TARGET', '<%=dbox4.getString("d_grcode")%>',  '<%=dbox4.getString("d_year")%>', '<%=dbox4.getString("d_subjseq")%>', '<%=v_sulpapernum%>', '<%=v_sulpapernm%>', '<%=v_sulstart%>', '<%=v_sulend%>')" class="state"><span class="ing">응시가능</span></a>
<%
		} else if (v_sulresult==0 && !v_issul.equals("Y")){
%>
				<a class="state" style="cursor:none"><span class="finish">설문종료</span></a>
<%
		} else {
%>
				<a class="state" style="cursor:none"><span class="finish">완료</span></a>
<%
		}
%>
				</td>
			</tr>
<%
	}
%>
<%	if(list4.size() == 0 ){%>
       	    <tr>
		    	<td height="3" colspan="5" align="center">설문내용이 없습니다.</td>
		    </tr>
<%	}%>
			</tbody>
			</table>

			</div>
			
			<%} %>
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->