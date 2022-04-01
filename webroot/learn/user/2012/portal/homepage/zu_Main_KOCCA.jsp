<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import = "com.credu.homepage.LinkSiteBean" %>
<%@ page import = "com.credu.homepage.LoginBean" %>
<%   
  RequestBox box = (RequestBox)request.getAttribute("requestbox");
  box.put("topmenu","0");
  String v_ismain ="Y";
%>
<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<%
  //RequestBox box = (RequestBox)request.getAttribute("requestbox");    
  ArrayList<DataBox> noticeListTop = (ArrayList) request.getAttribute("noticeListTop"); // 공지사항    
  ArrayList<DataBox> noticeListPopup = (ArrayList) request.getAttribute("noticePopup");// 팝업 공지사항
  
  ArrayList<DataBox> onLineSubjList  = (ArrayList)request.getAttribute("onLineSubjList");// 온라인 인기과정    
  ArrayList<DataBox> onLineGameSubjList  = (ArrayList)request.getAttribute("onLineGameSubjList");// 게임
  ArrayList<DataBox> onLineBroadSubjList  = (ArrayList)request.getAttribute("onLineBroadSubjList");// 방송
  ArrayList<DataBox> onLineCultureSubjList  = (ArrayList)request.getAttribute("onLineCultureSubjList");// 문화
  
  ArrayList<DataBox> DimensionOffSubjectList  = (ArrayList)request.getAttribute("DimensionOffSubjectList");// 3D 입체콘텐츠
  ArrayList<DataBox> IndustryOffSubjectList  = (ArrayList)request.getAttribute("IndustryOffSubjectList");// 산업계
  ArrayList<DataBox> NextOffSubjectList  = (ArrayList)request.getAttribute("NextOffSubjectList");// 차세대
  ArrayList<DataBox> SuTakOffSubjectList  = (ArrayList)request.getAttribute("SuTakOffSubjectList");// 수탁교육
  
  List<DataBox> AcademyPr = (List)request.getAttribute("academyPr");
  
 /*  String topWarp_userid = box.getSession("userid");
  String topWarp_usernm = box.getSession("name");
  String topWarp_gadmin = box.getSession("gadmin"); */
  
  HashMap<String, String> upperMap = null;    // 대분류 별 메뉴ID 매핑 맵 HashMap<upperlcass, menuid>

  upperMap   = ClassifySubjectBean.getMenuId(box);
  
  String v_status = "";
  
%>    
<script type="text/javascript">

/*여기부터 팝업관련 스크립트 소스 */
	function open_window(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable) {
	    toolbar_str = toolbar ? 'yes' : 'no';
	    menubar_str = menubar ? 'yes' : 'no';
	    statusbar_str = statusbar ? 'yes' : 'no';
	    scrollbar_str = scrollbar ? 'yes' : 'no';
	    resizable_str = resizable ? 'yes' : 'no';
	    window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar=no,menubar=no,status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
	}

    function popupView() {
	    var  width  = 0;
	    var  height = 0;
	    var  left   = 0;
	    var  top    = 0;
	
	    <%  for(int i=0; i < noticeListPopup.size(); i++){
	            DataBox dbox =    noticeListPopup.get(i);  %>
	      width  = <%=dbox.getString("d_popwidth")%>;
	      height = <%=dbox.getString("d_popheight")%>;
	      left   = <%=dbox.getString("d_popxpos")%>;
	      top    = <%=dbox.getString("d_popypos")%>;
	      url    = "/servlet/controller.homepage.MainServlet?p_process=popupview&p_seq=<%=dbox.getString("d_seq")%>&p_useframe=<%=dbox.getString("d_useframe")%>&p_popUpPreview=popUpPreview<%=dbox.getString("d_seq")%>";
	
	        if ( notice_getCookie( "popUpPreview<%=dbox.getString("d_seq")%>" ) != "done" ){
	          open_window("popUpPreview<%=dbox.getString("d_seq")%>",url,left,top,width,height, "no","no","no","yes","no");
	        }
	    <%  }  %>
    }

    popupView();

    /*여기부터 팝업관련 스크립트 소스 끝*/
    
	/* 패스워드 엔터키 체크 */
	function password_enter(e) {
      if (e.keyCode =='13'){  login_main();  }
  }
  
	 function searchtotal_enter(e)  {		
		if (e.keyCode =='13'){ totalSubjSearch1();  }
	}

  // 로그인
  function login_main() {    	
      if (input_chk()){       
      	//alert('test');
          document.login.p_process.value   = "login";
          //form1.p_topuserid.value = form1.p_userid.value;
          //form1.p_toppwd.value =  form1.p_pwd.value;

          document.login.action            = https_host() + "/servlet/controller.homepage.LoginServlet";
          document.login.submit();
      } else {
      	return;
      }
  }
  
  function input_chk()  {    	    	
      if (document.getElementById('p_userid').value == "" || document.getElementById('p_userid').value == null )
      {
          document.login.p_userid.value="";  //폼창에 NULL값 리턴
          alert( "아이디을 입력하십시요.");
          document.login.p_userid.focus();
          return(false);
      }
      if (document.getElementById('p_pwd').value == "" || document.getElementById('p_pwd').value == null )
      {
          document.login.p_pwd.value="";  //폼창에 NULL값 리턴
          alert( "비밀번호 입력하십시요.");
          document.login.p_pwd.focus();
          return(false);
      }
      return(true);
  }
  
  
// 공지사항 상세보기
  function viewNotice(seq) {
     document.form1.action = "/servlet/controller.homepage.HomeNoticeServlet";
     document.form1.p_process.value = "selectView";
     document.form1.menuid.value = "07";
     document.form1.gubun.value = "4";
     document.form1.p_seq.value= seq;
     document.form1.target = "_self";
     document.form1.submit();
  }
  
  function view_On_Subj(subj,subjnm,courseyn,upperclass,upperclassnm, year, subjseq, menuid){
      var p_process="";
      var action="";
      var gubun="";
      if(courseyn=="ON")
      {
          p_process="SubjectPreviewPage";
          action="/servlet/controller.propose.ProposeCourseServlet";
          gubun="1";
      }
      else
      {
          p_process="SubjectPreviewPage";
          action="/servlet/controller.off.ProposeOffServlet";
          gubun="2";
      }

    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value = subjnm;
    document.form1.p_iscourseYn.value = courseyn;
    document.form1.p_upperclass.value = upperclass;
    document.form1.p_upperclassnm.value = upperclassnm;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
      $("[name='p_seq']").val(upperclassnm);
    document.form1.menuid.value = menuid;
    document.form1.gubun.value = gubun;
    document.form1.p_process.value = "SubjectPreviewPage";
    document.form1.p_rprocess.value = 'SubjectList';
    document.form1.action=action;
    document.form1.target = "_self";
    document.form1.submit();
  }
  
  
//전체 과정검색
  function totalSubjSearch1() {
      var searchText = $("#topWarp_searchText").val();
       var gubun   = "1";
       var url     = "/servlet/controller.propose.MainSubjSearchServlet?p_process=SubjSearch&p_lsearchtext="+searchText;
       menuMainForward(gubun, url);
     

  }

// 관리페이지
  function topAdminOpenPage() {
      window.self.name = "winSelectView";
      farwindow = window.open("", "openWinAdmin", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 1100, height = 768, top=0, left=0");
      document.topdefaultForm.target = "openWinAdmin"
      document.topdefaultForm.action = "/learn/admin/system/mScreenFset.jsp";
      document.topdefaultForm.submit();

      farwindow.window.focus();
      document.topdefaultForm.target = window.self.name;
  }

  // 권한변경
  function topAuthChange() {
      var sAuth   = document.getElementById("p_topAuth").value;

      document.topdefaultForm.p_auth.value    = sAuth;
      document.topdefaultForm.p_process.value = "authChange";
      document.topdefaultForm.action          = "/servlet/controller.homepage.MainServlet";
      document.topdefaultForm.submit();
  }
  
//커뮤니티 새창
  function goSubIndex() {
      
      if(document.getElementById('p_project').value == ""){
          alert("년도를 선택해주세요");
          return;
      }
          
      window.open("/servlet/controller.community.CommunityIndexServlet?p_process=selectmyindex&p_cmuno="+document.getElementById('p_project').value, "openWinCommunity", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,resizable=no, width = 920, height = 660, top=0, left=0");
  }  

  //홍보관 상세보기
  function pr_selectView(seq) {
      document.pr_form.action = "/servlet/controller.infomation.BriefingHomePageServlet";
      document.pr_form.p_process.value = "selectView";
      document.pr_form.p_tabseq.value = "2262";
      document.pr_form.p_seq.value= seq;
      document.pr_form.submit();
  }

  function fn_onmouse(target, tf){
	if(tf){
		target.style.cursor='pointer';
	}else{
		target.style.corsor='auto';
	}
  }
  
</script>
<form name="form1" method="post" id="form1" action="/">
  <input type="hidden" name="p_process" value="" >
  <input type="hidden" name="p_rprocess" value="" >
  <input type="hidden" name="menuid" value="" >
  <input type="hidden" name="gubun" value="" >
  <input type="hidden" name="p_seq" value="" >
  <input type="hidden" name="p_subj" value="" >
  <input type="hidden" name="p_subjnm" value="" >
  <input type="hidden" name="p_iscourseYn" value="" >
  <input type="hidden" name="p_upperclass" value="" >
  <input type="hidden" name="p_upperclassnm" value="" >
  <input type="hidden" name="p_year" value="" >
  <input type="hidden" name="p_subjseq" value="" >
  <input type="hidden" name="p_topuserid"	value="">
  <input type="hidden" name="p_toppwd"	value="">
  <input type="hidden" name="p_edusulUserid" value="">
  <input type="hidden" name = "p_prePage"     value = "">
  <input type="submit" class="close" title="실행">
 </form>
 
 <form name="pr_form" method="post" action="/servlet/controller.infomation.BriefingHomePageServlet">
 	<input type="hidden" name="p_seq"/>
 	<input type="hidden" name="p_process"/>
 	<input type="hidden" name="p_tabseq"/>
 </form>
	<div class="topcontent">
		<ul class="contentA">
			<li class="leftspace">
				<a href="javascript:menuForward('90','02');" tabindex="201" onfocus="document.idpasson.src='/images/2012/main/btn_idpass_over.png'" onblur="document.idpasson.src='/images/2012/main/btn_idpass.png'" title="아이디 또는 비밀번호를 잊었을 때 클릭하세요."><img src="/images/2012/main/btn_idpass.png" name="idpasson" class="idpass" alt="아이디/비밀번호 찾기"></a>
				<a href="javascript:mainmenu('991');" tabindex="202" onfocus="document.joinon.src='/images/2012/main/btn_join_over.png'" onblur="document.joinon.src='/images/2012/main/btn_join.png'" title="신규회원가입을 진행합니다."><img src="/images/2012/main/btn_join.png" name="joinon" class="join" alt="회원가입"></a>
				<% if(topWarp_userid.equals("")) {%>     <%--로그인이 안되었는 경우--%>	
				<div class="loginput">
				<form method="post" name="login" action=""> 
					<input type='hidden' name='p_process'   value=''>			
					<input name="p_userid" id="p_userid" type="text" class="id" tabindex="203" onfocus="this.style.background='#ffffff';" title="아이디를 입력해주세요.">
					<input name="p_pwd" id="p_pwd" type="password" onkeypress="password_enter(event)" class="pass" tabindex="204" onfocus="this.style.background='#ffffff';" title="비밀번호를 입력해주세요.">
					<input type="image"  onClick="return login_main();" src="/images/2012/main/btn_login.png" class="login" tabindex="205" alt="로그인" title="회원 로그인을 실행합니다.">
				</form> 
				</div>
				<% } else  { %>
					<dl class="logafter">
						<dd><a href="javascript:menuForward('90','03');" tabindex="206"><img src="/images/2012/main/member_modify.png"></a></dd>
						<dd><a href="javascript:mainmenu('998');" tabindex="207"><img src="/images/2012/main/logout.png"></a></dd>
						<dd><b><%=topWarp_usernm%></b>님 &nbsp;</dd>
					</dl>
				<%  }  %>
				<dl class="titlenewspost">
					<dt><img src="/images/2012/main/subject_news.png" alt="아카데미 새소식"></dt>
					<dt class="more"><a href="javascript:menuForward('4','07');" tabindex="211" onfocus="document.noticemore.src='/images/2012/main/btn_more_on.png'" onblur="document.noticemore.src='/images/2012/main/btn_more.png'" title="공지사항으로 이동합니다."><img src="/images/2012/main/btn_more.png" name="noticemore" alt="전체보기"></a></dt>
				</dl>
				<table class="newspost" cellpadding="0" cellspacing="0" summary="아카데미 새소식">
					<colgroup><col width="8"><col width="*"></colgroup>
					<tbody>
					<%  // 공지사항
          if(noticeListTop.size()!=0)
          {
              for(int i=0;i<noticeListTop.size();i++)
              {   
              	if (i > 5) break;
                  DataBox dbox =  noticeListTop.get(i);
                  String t_addate = dbox.getString("d_addate").substring(0,8);
                  String t_agodate = dbox.getString("d_agodate").substring(0,8);
                  int c_addate = Integer.parseInt(t_addate);
                  int c_agodate = Integer.parseInt(t_agodate);
                  boolean new_che = false;
                  String new_tag = "";
                  if(c_addate-c_agodate > 0){
                	  new_che = true;
                	  new_tag = "<img src=\"/images/2012/main/icon/icon_newnotice.gif\" alt=\"new\">";
               	  }
          %>
					<tr>
						<td><img src="/images/2012/main/icon_dot.png" alt="구분점"></td>
						<td><a href="javascript:viewNotice('<%=dbox.getString("d_seq")%>');" tabindex="<%=i+211%>" title="<%=StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_adtitle")), 100)%>"><%=StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_adtitle")), 32)%><%if(new_che)%> <%=new_tag %></a></td>
						<td class="date"><%=dbox.getString("d_addate").substring(4,6)%>-<%=dbox.getString("d_addate").substring(6,8)%></td>
					</tr>
					<%
              }
          }
          else
          {
            out.print("<tr><td><img src='/images/2012/main/icon_dot.png' alt='구분점'></td>");
            out.print("<td colspan=2>새 소식이 없습니다.</td>");                           
            out.print("</tr>");
          }                                      
					%>                      
					</tbody>
				</table>
			</li>
			<%@ include file="/learn/user/2012/portal/include/mainbanner.jsp"%> 
		</ul>
		<ul class="contentB">
			<li class="online">
				<dl class="titleonline">
					<dd><img src="/images/2012/main/subject_onlinecourse.png" alt="온라인 과정"></dd>
					<dd class="more"><a href="javascript:menuMainForward('1', '/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList');" tabindex="250" onfocus="document.onlineon.src='/images/2012/main/btn_more_on.png'" onblur="document.onlineon.src='/images/2012/main/btn_more.png'" title="전체보기"><img src="/images/2012/main/btn_more.png" name="onlineon" alt="온라인 과정 전체보기"></a></dd>
				</dl>
				<dl class="onlinetab">
					<dd><button type="button" tabindex="251" onfocus="onlinetab('1')" onmouseover="onlinetab('1')" onclick="document.location='#'" class="over" id="online1" title="온라인 추천과정">온라인 추천과정</button></dd>
					<dd><button type="button" tabindex="261" onfocus="onlinetab('2')" onmouseover="onlinetab('2')" onclick="document.location='#'" class="out" id="online2" title="게임제작">게임제작</button></dd>
					<dd><button type="button" tabindex="271" onfocus="onlinetab('3')" onmouseover="onlinetab('3')" onclick="document.location='#'" class="out" id="online3" title="방송영상">방송영상</button></dd>
					<dd><button type="button" tabindex="281" onfocus="onlinetab('4')" onmouseover="onlinetab('4')" onclick="document.location='#'" class="out" id="online4" title="문화콘텐츠">문화콘텐츠</button></dd>
				</dl>
				<table id="onlinelist1" class="courselist" cellpadding="0" cellspacing="0" summary="온라인 추천과정 목록">
					<colgroup><col width="67"><col width="263"><col width="150"></colgroup>
					<tbody>
					<%  // 온라인 추천과정
				  if(onLineSubjList.size()!=0)
				  {
			      for(int i=0;i<onLineSubjList.size();i++)
			      {
		          DataBox dbox =  onLineSubjList.get(i);
		          String biyong="";
		          String aHref_SubjValues="";
		          DecimalFormat df = new DecimalFormat("#,###");
		
		          String tmp=upperMap !=null && upperMap.get(dbox.get("d_scupperclass")) != null ?  upperMap.get(dbox.get("d_scupperclass")).toString() : "00";
		          aHref_SubjValues="'"+dbox.get("d_subj")+"','"+dbox.get("d_subjnm")+"','"+dbox.get("d_isonoff")+"','"+dbox.get("d_scupperclass")+"','"+dbox.get("d_uclassnm")+"','"+dbox.get("d_scyear")+"','"+dbox.get("d_subjseq")+"','";
		          aHref_SubjValues+=tmp+"'";
					%>
          <tr>
						<td>
							
						<% if (dbox.get("d_mobile_preurl").equals("Y")) {%>
							<img src="/images/2012/common/icon/icon_pcmobile.gif" alt="PC 모바일학습">
						<% } else { %>
							<img src="/images/2012/common/icon/icon_pc.gif" alt="PC학습">
						<% } %>
						</td>
						<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" tabindex="<%=i+251%>" title="[<%=dbox.getString("d_EDUSTART")%> ~ <%=dbox.getString("d_EDUEND")%>] <%=StringManager.formatTitle(dbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(dbox.get("d_subjnm"), 26)%></a></td>
						<td class="date"><%=dbox.getString("d_EDUSTART")%> ~ <%=dbox.getString("d_EDUEND")%></td>
					</tr>
					<%
				    }
				  }
				  else
				  {
			      out.print("<tr>");
			      out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
			      out.print("</tr>");
				  }                                      
					%>						
					</tbody>
				</table>
				<table id="onlinelist2" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="게임제작분야 과정 목록">
					<colgroup><col width="67"><col width="263"><col width="150"></colgroup>
					<tbody>
					<%  // 게임제작분야
					for(int i=0;i<onLineGameSubjList.size();i++)
					{                               
						DataBox gamedbox =  onLineGameSubjList.get(i);
						String biyong="";
						String aHref_SubjValues="";
						DecimalFormat df = new DecimalFormat("#,###");
						
						String tmp=upperMap !=null && upperMap.get(gamedbox.get("d_scupperclass")) != null ?  upperMap.get(gamedbox.get("d_scupperclass")).toString() : "00";
						aHref_SubjValues="'"+gamedbox.get("d_subj")+"','"+gamedbox.get("d_subjnm")+"','"+gamedbox.get("d_isonoff")+"','"+gamedbox.get("d_scupperclass")+"','"+gamedbox.get("d_uclassnm")+"','"+gamedbox.get("d_scyear")+"','"+gamedbox.get("d_subjseq")+"','";
						aHref_SubjValues+=tmp+"'";
						
						String v_edustart = FormatDate.getFormatDate(gamedbox.getString("d_EDUSTART"),"yyyy.MM.dd");
						String v_eduend = FormatDate.getFormatDate(gamedbox.getString("d_EDUEND"),"yyyy.MM.dd");
					%>
					<tr>
						<td>
						<% if (gamedbox.get("d_mobile_preurl").equals("Y")) {%>
							<img src="/images/2012/common/icon/icon_pcmobile.gif" alt="PC 모바일학습">
						<% } else { %>
							<img src="/images/2012/common/icon/icon_pc.gif" alt="PC학습">
						<% } %>
						</td>
						<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" tabindex="<%=i+261%>" title="[<%=v_edustart%> ~ <%=v_eduend%>] <%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%></a></td>
						<td class="date"><%=v_edustart%> ~ <%=v_eduend%></td>
					</tr>
					<%
					}
					if (onLineGameSubjList.size() == 0)
					{
				    out.print("<tr>");
				    out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
				    out.print("</tr>");
					}                                      
					%>
					</tbody>
				</table>
				<table id="onlinelist3" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="방송영상분야 과정 목록">
					<colgroup><col width="67"><col width="263"><col width="150"></colgroup>
					<tbody>
					<%  // 방송영상
					for(int i=0;i<onLineBroadSubjList.size();i++)
					{                               
						DataBox gamedbox =  onLineBroadSubjList.get(i);
						String biyong="";
						String aHref_SubjValues="";
						DecimalFormat df = new DecimalFormat("#,###");
						
						String tmp=upperMap !=null && upperMap.get(gamedbox.get("d_scupperclass")) != null ?  upperMap.get(gamedbox.get("d_scupperclass")).toString() : "00";
						aHref_SubjValues="'"+gamedbox.get("d_subj")+"','"+gamedbox.get("d_subjnm")+"','"+gamedbox.get("d_isonoff")+"','"+gamedbox.get("d_scupperclass")+"','"+gamedbox.get("d_uclassnm")+"','"+gamedbox.get("d_scyear")+"','"+gamedbox.get("d_subjseq")+"','";
						aHref_SubjValues+=tmp+"'";
						
						String v_edustart = FormatDate.getFormatDate(gamedbox.getString("d_EDUSTART"),"yyyy.MM.dd");
						String v_eduend = FormatDate.getFormatDate(gamedbox.getString("d_EDUEND"),"yyyy.MM.dd");
					%>
					<tr>
						<td>
						<% if (gamedbox.get("d_mobile_preurl").equals("Y")) {%>
							<img src="/images/2012/common/icon/icon_pcmobile.gif" alt="PC 모바일학습">
						<% } else { %>
							<img src="/images/2012/common/icon/icon_pc.gif" alt="PC학습">
						<% } %>
						</td>
						<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" tabindex="<%=i+271%>" title="[<%=v_edustart%> ~ <%=v_eduend%>] <%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%></a></td>
						<td class="date"><%=v_edustart%> ~ <%=v_eduend%></td>
					</tr>
					<%
					}
					if (onLineBroadSubjList.size() == 0)
					{
						out.print("<tr>");
						out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
						out.print("</tr>");
					}                                      
					%>
					</tbody>
				</table>
				<table id="onlinelist4" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="문화콘텐츠분야 과정 목록">
					<colgroup><col width="67"><col width="263"><col width="150"></colgroup>
					<tbody>
					<%  // 문화콘텐츠분야
					for(int i=0;i<onLineCultureSubjList.size();i++)
					{                               
						DataBox gamedbox =  onLineCultureSubjList.get(i);
						String biyong="";
						String aHref_SubjValues="";
						DecimalFormat df = new DecimalFormat("#,###");
						
						String tmp=upperMap !=null && upperMap.get(gamedbox.get("d_scupperclass")) != null ?  upperMap.get(gamedbox.get("d_scupperclass")).toString() : "00";
						aHref_SubjValues="'"+gamedbox.get("d_subj")+"','"+gamedbox.get("d_subjnm")+"','"+gamedbox.get("d_isonoff")+"','"+gamedbox.get("d_scupperclass")+"','"+gamedbox.get("d_uclassnm")+"','"+gamedbox.get("d_scyear")+"','"+gamedbox.get("d_subjseq")+"','";
						aHref_SubjValues+=tmp+"'";
						
						String v_edustart = FormatDate.getFormatDate(gamedbox.getString("d_EDUSTART"),"yyyy.MM.dd");
						String v_eduend = FormatDate.getFormatDate(gamedbox.getString("d_EDUEND"),"yyyy.MM.dd");
					%>
					<tr>
						<td>
						<% if (gamedbox.get("d_mobile_preurl").equals("Y")) {%>
							<img src="/images/2012/common/icon/icon_pcmobile.gif" alt="PC 모바일학습">
						<% } else { %>
							<img src="/images/2012/common/icon/icon_pc.gif" alt="PC학습">
						<% } %>
						</td>
						<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" tabindex="<%=i+281%>" title="[<%=v_edustart%> ~ <%=v_eduend%>] <%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%></a></td>
						<td class="date"><%=v_edustart%> ~ <%=v_eduend%></td>
					</tr>
					<%
					}
					
					if (onLineCultureSubjList.size() == 0)
					{
						out.print("<tr>");
						out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
						out.print("</tr>");
					}                                      
					%>
					</tbody>
				</table>
			</li>
			<li class="offline">
				<dl class="titleonline">
					<dd><img src="/images/2012/main/subject_offlinecourse.png" alt="오프라인 과정"></dd>
					<dd class="more"><a href="javascript:menuMainForward('2', '/servlet/controller.off.ProposeOffServlet?p_process=SubjectList');" tabindex="290" onfocus="document.offlineon.src='/images/2012/main/btn_more_on.png'"  onblur="document.offlineon.src='/images/2012/main/btn_more.png'" title="전체보기"><img src="/images/2012/main/btn_more.png" name="offlineon" alt="오프라인 과정 전체보기"></a></dd>
				</dl>
				<dl class="onlinetab">
					<dd><button type="button" onfocus="offlinetab('1')" onmouseover="offlinetab('1')" tabindex="291" onclick="document.location='#'" class="over" id="offline1" title="3D 입체콘텐츠">3D 입체콘텐츠</button></dd>
					<dd><button type="button" onfocus="offlinetab('2')" onmouseover="offlinetab('2')" tabindex="301" onclick="document.location='#'" class="out" id="offline2" title="현업인 직무재교육">현업인 직무재교육</button></dd>
					<dd><button type="button" onfocus="offlinetab('3')" onmouseover="offlinetab('3')" tabindex="311" onclick="document.location='#'" class="out" id="offline3" title="차세대 인재양성">차세대 인재양성</button></dd>
					<!--dd><button type="button" onfocus="offlinetab('4')" onmouseover="offlinetab('4')" tabindex="321" onclick="document.location='#'" class="out" id="offline4" title="수탁교육">수탁교육</button></dd-->
				</dl>
				<table id="offlinelist1" class="courselist" cellpadding="0" cellspacing="0" summary="3D 입체콘텐츠 목록">
					<colgroup><col width="67"><col width="263"><col width="150"></colgroup>
					<tbody>
					<%  // 3D 입체콘텐츠
					for(int i=0;i<DimensionOffSubjectList.size();i++)
					{                               
						DataBox gamedbox =  DimensionOffSubjectList.get(i);
						String biyong="";
						String aHref_SubjValues="";
						DecimalFormat df = new DecimalFormat("#,###");                               
						
						aHref_SubjValues="'"+gamedbox.get("d_subj")+"','"+gamedbox.get("d_subjnm")+"','"+gamedbox.get("d_isonoff")+"','"+gamedbox.get("d_scupperclass")+"','"+gamedbox.get("d_seq")+"','"+gamedbox.get("d_year")+"','"+gamedbox.get("d_subjseq")+"','";
						aHref_SubjValues+=gamedbox.get("d_scupperclass")+"'";
						
						String v_edustart = FormatDate.getFormatDate(gamedbox.getString("d_EDUSTART"),"yyyy.MM.dd");
						String v_eduend = FormatDate.getFormatDate(gamedbox.getString("d_EDUEND"),"yyyy.MM.dd");
						
						v_status = gamedbox.get("d_status");
					%>
					<tr>
						<td>
						<% if (v_status.equals("Y")) { %>
							<img src="/images/2012/main/icon/icon_recruitment2.gif" alt="모집중">
						<% } else if (v_status.equals("W")){ %>
							<img src="/images/2012/main/icon/icon_standby.gif" alt="신청대기">
						<% } else { %>
							<img src="/images/2012/main/icon/icon_close2.gif" alt="마감">
						<% } %>
						</td>
						<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" tabindex="<%=i+291%>" title="[<%=v_edustart%> ~ <%=v_eduend%>] <%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%></a></td>
						<td class="date"><%=v_edustart%> ~ <%=v_eduend%></td>
					</tr>
					<%
					}
					
					if (DimensionOffSubjectList.size() == 0)
					{
						out.print("<tr>");
						out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
						out.print("</tr>");
					}                                      
					%>
					</tbody>
				</table>
				<table id="offlinelist2" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="산업계 직무재교육 목록">
					<colgroup><col width="67"><col width="263"><col width="150"></colgroup>
					<tbody>
					<%  // 산업계
					for(int i=0;i<IndustryOffSubjectList.size();i++)
					{                               
						DataBox gamedbox =  IndustryOffSubjectList.get(i);
						String biyong="";
						String aHref_SubjValues="";
						DecimalFormat df = new DecimalFormat("#,###");                               
						
						aHref_SubjValues="'"+gamedbox.get("d_subj")+"','"+gamedbox.get("d_subjnm")+"','"+gamedbox.get("d_isonoff")+"','"+gamedbox.get("d_scupperclass")+"','"+gamedbox.get("d_seq")+"','"+gamedbox.get("d_year")+"','"+gamedbox.get("d_subjseq")+"','";
						aHref_SubjValues+=gamedbox.get("d_scupperclass")+"'";
						
						String v_edustart = FormatDate.getFormatDate(gamedbox.getString("d_EDUSTART"),"yyyy.MM.dd");
						String v_eduend = FormatDate.getFormatDate(gamedbox.getString("d_EDUEND"),"yyyy.MM.dd");
						v_status = gamedbox.get("d_status");
					%>
					<tr>
						<td>
						<% if (v_status.equals("Y")) { %>
							<img src="/images/2012/main/icon/icon_recruitment2.gif" alt="모집중">
						<% } else if (v_status.equals("W")){ %>
							<img src="/images/2012/main/icon/icon_standby.gif" alt="신청대기">
						<% } else { %>
							<img src="/images/2012/main/icon/icon_close2.gif" alt="마감">
						<% } %>
						</td>
						<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" tabindex="<%=i+301%>" title="[<%=v_edustart%> ~ <%=v_eduend%>] <%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%></a></td>
						<td class="date"><%=v_edustart%> ~ <%=v_eduend%></td>
					</tr>
					<%
					}
					
					if (IndustryOffSubjectList.size() == 0)
					{
						out.print("<tr>");
						out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
						out.print("</tr>");
					}                                      
					%>
					</tbody>
				</table>
				<table id="offlinelist3" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="차세대 인재양성 목록">
					<colgroup><col width="67"><col width="263"><col width="150"></colgroup>
					<tbody>
					<%  // 차세대
					for(int i=0;i<NextOffSubjectList.size();i++)
					{                               
						DataBox gamedbox =  NextOffSubjectList.get(i);
						String biyong="";
						String aHref_SubjValues="";
						DecimalFormat df = new DecimalFormat("#,###");                               
						
						aHref_SubjValues="'"+gamedbox.get("d_subj")+"','"+gamedbox.get("d_subjnm")+"','"+gamedbox.get("d_isonoff")+"','"+gamedbox.get("d_scupperclass")+"','"+gamedbox.get("d_seq")+"','"+gamedbox.get("d_year")+"','"+gamedbox.get("d_subjseq")+"','";
						aHref_SubjValues+=gamedbox.get("d_scupperclass")+"'";
						
						String v_edustart = FormatDate.getFormatDate(gamedbox.getString("d_EDUSTART"),"yyyy.MM.dd");
						String v_eduend = FormatDate.getFormatDate(gamedbox.getString("d_EDUEND"),"yyyy.MM.dd");
						v_status = gamedbox.get("d_status");
					%>
					<tr>
						<td>
						<% if (v_status.equals("Y")) { %>
							<img src="/images/2012/main/icon/icon_recruitment2.gif" alt="모집중">
						<% } else if (v_status.equals("W")){ %>
							<img src="/images/2012/main/icon/icon_standby.gif" alt="신청대기">
						<% } else { %>
							<img src="/images/2012/main/icon/icon_close2.gif" alt="마감">
						<% } %>
						</td>
						<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" tabindex="<%=i+311%>" title="[<%=v_edustart%> ~ <%=v_eduend%>] <%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%></a></td>
						<td class="date"><%=v_edustart%> ~ <%=v_eduend%></td>
					</tr>
					<%
					}
					
					if (NextOffSubjectList.size() == 0)
					{
					out.print("<tr>");
					out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
					out.print("</tr>");
					}                                      
					%>
					</tbody>
				</table>
				<table id="offlinelist4" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="수탁교육 목록">
					<colgroup><col width="67"><col width="263"><col width="150"></colgroup>
					<tbody>
					<%  // 차세대
					for(int i=0;i<SuTakOffSubjectList.size();i++)
					{                               
						DataBox gamedbox =  SuTakOffSubjectList.get(i);
						String biyong="";
						String aHref_SubjValues="";
						DecimalFormat df = new DecimalFormat("#,###");                               
						
						aHref_SubjValues="'"+gamedbox.get("d_subj")+"','"+gamedbox.get("d_subjnm")+"','"+gamedbox.get("d_isonoff")+"','"+gamedbox.get("d_scupperclass")+"','"+gamedbox.get("d_seq")+"','"+gamedbox.get("d_year")+"','"+gamedbox.get("d_subjseq")+"','";
						aHref_SubjValues+=gamedbox.get("d_scupperclass")+"'";
						
						String v_edustart = FormatDate.getFormatDate(gamedbox.getString("d_EDUSTART"),"yyyy.MM.dd");
						String v_eduend = FormatDate.getFormatDate(gamedbox.getString("d_EDUEND"),"yyyy.MM.dd");
						v_status = gamedbox.get("d_status");
					%>
					<tr>
						<td>
							<% if (v_status.equals("Y")) { %>
								<img src="/images/2012/main/icon/icon_recruitment2.gif" alt="모집중">
							<% } else if (v_status.equals("W")){ %>
								<img src="/images/2012/main/icon/icon_standby.gif" alt="신청대기">
							<% } else { %>
								<img src="/images/2012/main/icon/icon_close2.gif" alt="마감">
							<% } %>
						</td>
						<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" tabindex="<%=i+321%>" title="[<%=v_edustart%> ~ <%=v_eduend%>] <%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%></a></td>
						<td class="date"><%=v_edustart%> ~ <%=v_eduend%></td>
					</tr>
					<%
					}
					
					if (SuTakOffSubjectList.size() == 0)
					{
						out.print("<tr>");
						out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
						out.print("</tr>");
					}                                      
					%>
					</tbody>
				</table>
			</li>
		</ul>
		<div style="clear:left;"></div>
	</div>
	<div class="bottomcontent">
		<div class="contentbox">
			<ul class="content">
				<li class="leftspace">
					<div class="detail">
						<a href="javascript:menuForward('5','12');" tabindex="331" title="성공을 말하다! <열린강의> 바로가기"><img src="/images/2012/main/subject_opencast.png" alt="성공을 말하다! 열린강의"></a>
						<div class="opencastment">방송, 영상, 영화, 다큐멘터리, 문화, 예술, 게임, 유통 등 다양한 분야의 유명인사들이 한자리에 모여 말하다.</div>
						<!-- div class="talent"><img src="/images/2012/main/subject_talent.png" alt="차세대 인재를 위한 전문화된 교육시설"></div-->
						<div class="talent"><img src="/images/2012/main/subject_pr.png" alt="차세대 인재를 위한 전문화된 교육시설"></div>
						<div id="learningroom">
							<div class="clsBannerScreen">
							<%if(AcademyPr.size() == 0){ %>
								<div class="images"><img src="/images/2012/main/sample_learningroom.png" alt="샘플교육실"></div>
							<%
							}else {
								DataBox academyprbox =  AcademyPr.get(0);
								String pr_seq		= academyprbox.getString("d_seq");
								String pr_title 	= StringManager.formatTitle(StringManager.removeHTML(academyprbox.getString("d_title")),20);
								String pr_content 	= StringManager.formatTitle(StringManager.removeHTML(academyprbox.getString("d_content")), 80);
									   pr_content 	= pr_content.replaceAll("(!/|\r|\n|&nbsp;|<br>|◆|▲|□)", "");
								String pr_savefile 	= StringManager.replace(academyprbox.getString("d_savefile"),"\\","/");
							%>
								<div class="images"><img src="<%=pr_savefile%>" width="135px" height="76px" alt="<%=academyprbox.getString("d_title") %> 썸네일"></div>
								<div class="pr_content">
									<a class="pr_title" tabindex="332" onclick="javascript:pr_selectView(<%=pr_seq %>);" onmouseover="fn_onmouse(this,true);" onmouseout="fn_onmouse(this,true);" title="<%=academyprbox.getString("d_title") %>"><strong><%=pr_title %></strong><br/>
									<%=pr_content %></a>
								</div>
							<%}%>
							</div>
						</div>
						<div class="banner"><a href="javascript:pop_view('/contents/temp/portfolio/portfolio.html',1000,750);" tabindex="333" title="2012년 방송영상 분야 교육생 포트폴리오 열람하기"><img src="/images/2012/main/banner_portfolio.png" alt="2012년 방송영상 분야 교육생 포트폴리오 열람하기"></a></div>
					</div>
				</li>
				<li class="rightspace">
					<dl class="business">
						<dd class="learning">
							<table class="businessfield" cellpadding="0" cellspacing="0" summary="한국콘텐츠아카데미 교육사업 분야 안내">
								<thead>
									<tr>
										<th colspan="2"><img src="/images/2012/main/subject_business.png" alt="한국콘텐츠아카데미 교육사업"></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&amp;p_gubun=1" tabindex="341" title="차세대 인재 양성교육사업 안내로 이동합니다.">차세대 인재 양성교육</a></td>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&amp;p_gubun=2" tabindex="342" title="현업인 직무 재교육사업 안내로 이동합니다.">현업인 직무 재교육</a></td>
									</tr>
									<tr>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&amp;p_gubun=6" tabindex="343" title="창의인재 양성사업 안내로 이동합니다.">창의인재 양성사업</a></td>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&amp;p_gubun=3" tabindex="344" title="온라인 교육사업 안내로 이동합니다.">온라인 교육</a></td>
									</tr>
										<!-- td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&amp;p_gubun=7" tabindex="356" title="드라마 프로듀서 스쿨사업 안내로 이동합니다.">드라마 프로듀서 스쿨</a></td>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&amp;p_gubun=4" tabindex="357" title="국제 인증과정사업 안내로 이동합니다.">국제 인증과정</a></td>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&amp;p_gubun=8" tabindex="357" title="게임 국가기술 자격 검정사업 안내로 이동합니다.">게임 국가기술 자격 검정</a></td>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&amp;p_gubun=5" tabindex="352" title="3D입체 콘텐츠 전문인력 양성사업 안내로 이동합니다.">3D입체 콘텐츠 전문인력 양성</a></td-->
								</tbody>
							</table>
						</dd>
						<dd class="another">
<!--							<a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=Curriculum')" tabindex="359" title="온라인 전체 교육과정을 확인할 수 있습니다"><img src="/images/2012/main/banner_curriculum.gif" alt="온라인과정 커리큘럼"></a><br>-->
<!--							<a href="http://blog.naver.com/kocca01" target="_blank" tabindex="360" title="한국콘텐츠아카데미 네이버 블로그로 이동합니다"><img src="/images/2012/main/sns_01.gif" alt="네이버블로그"></a><a href="http://twitter.com/edukocca" target="_blank" tabindex="361" title="한국콘텐츠아카데미 트위터로 이동합니다"><img src="/images/2012/main/sns_02.gif" alt="네이버블로그"></a>-->
							<a href="http://blog.naver.com/kocca01" target="_blank" tabindex="351" title="한국콘텐츠아카데미 네이버 블로그로 이동합니다"><img src="/images/2012/main/btn_hongbo_blog.png" alt="네이버블로그"></a><a href="http://twitter.com/edukocca" target="_blank" tabindex="352" title="한국콘텐츠아카데미 트위터로 이동합니다"><img src="/images/2012/main/btn_hongbo_tw.png" alt="트위터"></a><a href="http://www.facebook.com/edukocca" target="_blank" tabindex="353" title="한국콘텐츠아카데미 페이스북으로 이동합니다"><img src="/images/2012/main/btn_hongbo_fb.png" alt="페이스북"></a>
						</dd>
					</dl>
					<dl class="steprable">
						<dd class="msg"><img src="/images/2012/main/step_msg.png" alt="한국콘텐츠아카데미의 처음오셨나요? 전문교육을 무료로 받아보세요."></dd>
						<dd class="step1"><a href="javascript:menuForward('90','01');" tabindex="361" title="회원가입 바로가기"><img src="/images/2012/main/step1.png" alt="1단계 회원가입"></a></dd>
						<dd class="step2"><a href="javascript:menuMainForward('90','/servlet/controller.homepage.MainServlet?p_process=MainLogin');" tabindex="362" title="로그인 바로가기"><img src="/images/2012/main/step2.png" alt="2단계 로그인"></a></dd>
						<dd class="step3"><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList')" tabindex="363" title="과정선택 바로가기"><img src="/images/2012/main/step3.png" alt="3단계 과정선택"></a></dd>
						<dd class="step4"><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList')" tabindex="364" title="수강신청 바로가기"><img src="/images/2012/main/step4.png" alt="4단계 수강신청"></a></dd>
					</dl>
					<dl class="support">
<!--						<dd><a href="http://kocca.anyhelp.net" target="_blank" tabindex="337" title="원격지원 서비스로 이동합니다."><img src="/images/2012/main/banner_support.gif" alt="학습 중 PC문제가 발생하셨나요? 원격지원서비스 이용하기"></a></dd>-->
						<dd><a href="javascript:pop_view('/contents/temp/remote_support/remote_support.html',780,800);" target="_self" tabindex="371" title="원격지원 서비스로 이동합니다."><img src="/images/2012/main/banner_support.gif" alt="학습 중 PC문제가 발생하셨나요? 원격지원서비스 이용하기"></a></dd>
						<dd><a href="javascript:menuForward('4','05');" tabindex="372" title="온라인과정 수업 전 교육환경 설정하러 바로가기"><img src="/images/2012/main/banner_setup.gif" alt="온라인과정 수업 전 교육환경 설정하기"></a></dd>
					</dl>
				</li>
			</ul>
			<div class="clearscript"></div>
		</div>
	<% if (topmenu.equals("0")) { %><% } else { %></div><% } %>
	<%@ include file="/learn/user/2012/portal/include/footer.jsp"%> 