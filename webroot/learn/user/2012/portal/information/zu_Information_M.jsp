<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.dunet.common.util.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    int     v_seq       = 0;
    
    // 골드리스트 관련
    int     v_viewcnt   = 0;
    float   v_checkpoin = 0;
    
    String v_vodimg     = "";
    String v_lecnm      = "";
    String v_tutornm    = "";
    String v_lectime    = "";
    String v_creator    = "";
    String v_intro      = "";
    String v_width_s    = "";
    String v_height_s   = "";
    String v_vodurl     = "";
    
    ArrayList GoldClassList = (ArrayList)request.getAttribute("GoldClassList");         // 이달의골드클래스
    ArrayList PreGoldClassList = (ArrayList)request.getAttribute("PreGoldClassList");   // 지난 골드클래스
      
    // 이벤트 관련
    
    int    v_cnt     = 0;              // 조회수

    String v_title     = "";           // 제목
    String v_content   = "";           // 내용
    String v_indate    = "";           // 등록일
    String v_name      = "";           // 등록자
    String v_startdate = "";           // 이벤트 시작일
    String v_enddate   = "";           // 이벤트 시작일
    String v_winneryn  = "";           // 담청자 여부
    String v_isall     = "";
    
    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");
    

    List eventList = (ArrayList)request.getAttribute("eventList");
    
    // 워크샵 관련
    
    String v_code      = "";
    String v_codenm    = "";
    String v_seminargubun     = "";
    String v_seminargubunnm   = "";            
    String v_seminarnm = "";           // 제목
    String v_tname     = "";           // 담당교수명
    String v_propstart = "";           // 워크샵 모집 시작일
    String v_propend   = "";           // 워크샵 모집 종료일
    String v_passyn  = "";             // 선정자 공지 여부
    String v_bannerimg = "";          // 배너이미지
    
    List seminarList = (ArrayList)request.getAttribute("seminarList");
    
    //실무강좌 관련
    String v_professor  = "";
    ArrayList PracticalCourseList = (ArrayList)request.getAttribute("PracticalCourseList"); // 실무강좌
    
    // 랜덤 객체
    Random ran = new Random();
    
    DataBox GoldClassBox = null;
    
    if(GoldClassList != null &&  GoldClassList.size() > 0 ) {
    	GoldClassBox = (DataBox)GoldClassList.get(ran.nextInt(GoldClassList.size()));
    }
    
    DataBox SeminarBox = null;
    
    if(seminarList != null &&  seminarList.size() > 0 ) {
    	SeminarBox = (DataBox)seminarList.get(ran.nextInt(seminarList.size()));
    }
    
    DataBox EventBox = null;
    
    if(eventList != null &&  eventList.size() > 0 ) {
    	EventBox = (DataBox)eventList.get(ran.nextInt(eventList.size()));
    }
    
    // 메인페이지에서 각 메뉴로 이동시 메뉴 표시하기 위한 파라미터 - 추후 동적으로 불러오는 부분으로 변경(?)
    String goldclassMenuId          = "01";
    String practicalCourseMenuId    = "02";
    String specialCourseMenuId      = "03";
    String seminarMenuId            = "04";
    String eventMenuId              = "05";
    String galleryMenuId            = "06";
    String interviewMenuId          = "07";
    String eduReviewMenuId          = "08";
    String prArticlesMenuId         = "10";
    String expertMenuId             = "11";
    
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>

<!-- 스크립트영역 -->

<script language="JavaScript" type="text/JavaScript">
<!--

//지난골드클래스 더보기
function morePreGoldClass(){
	document.form1.menuid.value= "<%=goldclassMenuId%>";
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.target="_self";
    document.form1.p_process.value = "selectPreGoldClassList";
    document.form1.submit();
    }

//상세화면 페이지로 이동
function viewGoldClass(seq) {
	document.form1.menuid.value= "<%=goldclassMenuId%>";
	document.form1.p_prePage.value   = "Main";
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.target="_self";
    document.form1.p_process.value   = "selectView";
    document.form1.p_seq.value       = seq;
    document.form1.submit();
}

// 이달의 골드클래스 콘텐츠 화면 보기(팝업)
function openGoldClass(seq,w,h)
{
    
    if (w.length>0) {
        window.open("", "ViewVod", "top=0, left=0, width="+w+", height="+h+", status=no, resizable=no");
        
    }else{
        window.open("", "ViewVod", "top=0, left=0, width=1008, height=570, status=no, resizable=no");
    }
    document.form1.action="/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.p_process.value = "popUpVod";
    document.form1.p_seq.value = seq;
    document.form1.target="ViewVod";
    document.form1.submit();
    document.form1.target="_self";
    
}

// 이달의 골드클래스 의견 달기
function goReply(seq){

    window.open("about:blank", "replyList", "top=0, left=0, width=637, height=530, status=no, resizable=no");

    document.form1.action="/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.p_process.value = "insertReplyPage";
    document.form1.p_seq.value = seq;
    document.form1.target="replyList";
    document.form1.submit();

    document.form1.target="_self";    
}

//이벤트 더보기
function moreEvent() {
	document.form1.menuid.value= "<%=eventMenuId%>";
    document.form1.action = "/servlet/controller.homepage.EventHomePageServlet";
    document.form1.p_process.value = "selectList";
    document.form1.target="_self";
    document.form1.submit();
}

//이벤트 상세보기
function viewEvent(seq) {
	document.form1.menuid.value= "<%=eventMenuId%>";
    document.form1.action = "/servlet/controller.homepage.EventHomePageServlet";
    document.form1.p_process.value = "selectView";
    document.form1.p_seq.value= seq;
    document.form1.target="_self";
    document.form1.submit();
}

// 이벤트 당첨자 확인
function viewPassEvent(seq) {

    window.open("about:blank", "popUpView", "top=0, left=0, width=400, height=, status=no, resizable=no");

    document.form1.action="/servlet/controller.homepage.EventHomePageServlet";
    document.form1.p_process.value = "selectViewPass";
    document.form1.p_seq.value = seq;
    document.form1.target="popUpView";
    document.form1.submit();
    
    document.form1.target="_self";
}

//워크샵 더보기
function moreSeminar() {
	document.form1.menuid.value= "<%=seminarMenuId%>";
    document.form1.action = "/servlet/controller.homepage.SeminarHomePageServlet";
    document.form1.p_process.value = "selectList";
    document.form1.target="_self";
    document.form1.submit();
}

//워크샵 상세보기
function viewSeminar(seq) {
	document.form1.menuid.value= "<%=seminarMenuId%>";
    document.form1.action = "/servlet/controller.homepage.SeminarHomePageServlet";
    document.form1.p_process.value = "selectView";
    document.form1.p_seq.value= seq;
    document.form1.submit();
}
//워크샵 선정자 확인
function viewPassSeminar(seq) {

    window.open("about:blank", "popUpView", "top=0, left=0, width=400, height=, status=no, resizable=no");

    document.form1.action="/servlet/controller.homepage.SeminarHomePageServlet";
    document.form1.p_process.value = "selectViewPass";
    document.form1.p_seq.value = seq;
    document.form1.target="popUpView";
    document.form1.submit();
    
    document.form1.target="_self";
}

//실무강좌 상세보기
function selectView(seq) {
	document.form1.menuid.value= "<%=practicalCourseMenuId%>";
    document.form1.action = "/servlet/controller.infomation.PracticalCourseHomePageServlet";
    document.form1.p_process.value = "selectView";
    document.form1.target="_self";
    document.form1.p_seq.value= seq;
    document.form1.submit();
}

//실무강좌 더보기
function morePracticalCourse() {
	document.form1.menuid.value= "<%=practicalCourseMenuId%>";
    document.form1.action = "/servlet/controller.infomation.PracticalCourseHomePageServlet";
    document.form1.p_process.value = "selectList";
    document.form1.target="_self";
    document.form1.submit();
}

//교육자료실 가기
function goSpecialCourse(){
    document.form1.menuid.value= "<%=specialCourseMenuId%>";
    document.form1.action = "/servlet/controller.infomation.SpecialCourseHomePageServlet";
    document.form1.p_process.value = "selectList";
    document.form1.target="_self";
    document.form1.submit();
}

//교육자료실 가기
function goGallery(){
    document.form1.menuid.value= "<%=galleryMenuId%>";
    document.form1.action = "/servlet/controller.infomation.GalleryHomePageServlet";
    document.form1.p_process.value = "selectList";
    document.form1.target="_self";
    document.form1.submit();
}
	
// 인터뷰 가기
function goInterview(){
    document.form1.menuid.value= "<%=interviewMenuId%>";
    document.form1.action = "/servlet/controller.homepage.KOpenPowerHomePageServlet";
    document.form1.p_process.value = "selectList";
    document.form1.target="_self";
    document.form1.submit();
}
//교육후기 가기
function goEduReview(){
    document.form1.menuid.value= "<%=eduReviewMenuId%>";
    document.form1.action = "/servlet/controller.infomation.EduReviewHomePageServlet";
    document.form1.p_process.value = "selectList";
    document.form1.target="_self";
    document.form1.submit();
}
//전문가등록하기 가기
function goExpertRegist(){
    document.form1.menuid.value= "<%=expertMenuId%>";
    document.form1.action = "/servlet/controller.tutor.OffExpertHomePageServlet";
    document.form1.p_process.value = "viewPage";
    document.form1.target="_self";
    document.form1.submit();
}
//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<form name = "form1" method = "post" action="">
	<input type = "hidden" name = "p_process"      value = "">
	<input type = "hidden" name = "p_seq"          value = "">
	<input type = "hidden" name = "p_prePage"      value = "">
	<input type = "hidden" name = "gubun"          value = "<%=gubun %>">
	<input type = "hidden" name = "menuid">
	
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
        <td><img src="/images/portal/homepage_renewal/library/stitle_01.gif" alt="정보플러스 홈" /></td>
        <td class="h_road">Home &gt; 정보플러스  &gt; <strong>정보플러스 홈</strong></td>
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
        <td height="290" valign="top" background="/images/portal/homepage_renewal/library/bg_goldclass.jpg"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td height="116" colspan="2" valign="bottom">&nbsp;</td>
            </tr>
            <tr>
              <td width="60%" height="125" bgcolor="#FFFFFF">
              <table width="100%" border="0" cellspacing="0" cellpadding="10">
                <tr>
                <%  if(GoldClassBox != null) { 
                    	v_seq        = GoldClassBox.getInt("d_seq");
                        v_vodimg     = GoldClassBox.getString("d_vodimg");
                        v_lecnm      = GoldClassBox.getString("d_lecnm");
                        v_tutornm    = GoldClassBox.getString("d_tutornm");
                        v_lectime    = GoldClassBox.getString("d_lectime");
                        v_creator    = GoldClassBox.getString("d_creator");
                        v_intro      = GoldClassBox.getString("d_intro");
                        v_width_s    = GoldClassBox.getString("d_width_s");
                        v_height_s   = GoldClassBox.getString("d_height_s");
                        v_viewcnt    = GoldClassBox.getInt("d_viewcnt");
                        v_checkpoin  = GoldClassBox.getFloat("d_checkpoin");
                        v_vodurl     = GoldClassBox.getString("d_vodurl");
                    %>
                  <td width="45%"><a href="javascript:openGoldClass(<%=v_seq %>, '<%=v_width_s %>','<%=v_height_s %>')"><img src="/servlet/controller.library.DownloadServlet?p_savefile=<%=v_vodimg %>" width="156" height="118" /></a></td>
                  <td width="55%">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">                  
                    <tr>
                      <td><strong class="org">ㆍ</strong></td>
                      <td height="20"><a href="javascript:viewGoldClass(<%=v_seq%>);"><%=v_lecnm %></a></td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" bgcolor="eeeeee"></td>
                      </tr>
                    <tr>
                      <td><strong class="org">ㆍ</strong></td>
                      <td height="20">강사명 : <%=v_tutornm %></td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" bgcolor="eeeeee"></td>
                      </tr>
                    <tr>
                      <td><strong class="org">ㆍ</strong></td>
                      <td height="20">강의시간 : <%=v_lectime %></td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" bgcolor="eeeeee"></td>
                      </tr>
                    <tr>
                      <td><strong class="org">ㆍ</strong></td>
                      <td height="20"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="25%">평가 : </td>
                          <td width="75%"><%=StringUtil.getStarPoint(v_checkpoin) %></td>
                        </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" bgcolor="eeeeee"></td>
                      </tr>
                  </table></td>
                  <%  } else {%>
                    <td>등록된 글이 없습니다.</td>
                    <%  } %>
                </tr>
              </table>                </td>
              <td width="40%" bgcolor="#FFFFFF">
              <table width="95%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20" height="30"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg" width="10" height="10" /></td>
                  <td><img src="/images/portal/homepage_renewal/library/sstitle_08.gif" width="92" height="18" /></td>
                  <td class="h_road"><a href="javascript:morePreGoldClass();"><img src="/images/portal/homepage_renewal/common/btn_more.gif" width="33" height="10" alt="더보기" /></a></td>
                </tr>
              </table>
                <table width="95%" border="0" cellspacing="0" cellpadding="0">
                <%  if(PreGoldClassList.size() != 0){
                            for(int i = 0; i < PreGoldClassList.size(); i++) {
                                DataBox dbox = (DataBox)PreGoldClassList.get(i);  
                   
			                    v_seq        = dbox.getInt("d_seq");
			                    v_vodimg     = dbox.getString("d_vodimg");
			                    v_lecnm      = dbox.getString("d_lecnm");
			                    v_tutornm    = dbox.getString("d_tutornm");
			                    v_lectime    = dbox.getString("d_lectime");
			                    v_creator    = dbox.getString("d_creator");
			                    v_intro      = dbox.getString("d_intro");
			                    v_width_s    = dbox.getString("d_width_s");
			                    v_height_s   = dbox.getString("d_height_s");
			                    v_viewcnt    = dbox.getInt("d_viewcnt");
			                    v_checkpoin  = dbox.getFloat("d_checkpoin");
			                    
			                    v_lecnm      = StringManager.formatTitle(v_lecnm, 20);
                    %>
                  <tr>
                    <td><strong class="org">ㆍ</strong></td>
                    <td height="18"><a href="javascript:viewGoldClass(<%=v_seq%>);"><%=v_lecnm %> </a></td>
                  </tr>                  
                  <tr>
                    <td height="1" colspan="2" bgcolor="eeeeee"></td>
                  </tr>
                  <%      } 
                        } else { %>
                    <td>등록된 글이 없습니다.</td>
                    <%  } %>
                </table>
                </td>
            </tr>
            
        </table></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="10" colspan="3">&nbsp;</td>
      </tr>
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
        <td width="332"><img src="/images/portal/homepage_renewal/library/sstitle_06.gif"/></td>
        <td class="h_road" ><a href="javascript:morePracticalCourse();" class="fl_r"><img src="/images/portal/homepage_renewal/myclass/btn_more.gif" /></a></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="tit_table">제목</td>
        <td class="tit_table_right">강사명</td>
      </tr>
    <%  if(PracticalCourseList != null && PracticalCourseList.size() > 0){
                            for(int i = 0; i < PracticalCourseList.size(); i++) {
                            	if( i > 4 ) {
                            		break;
                            	}
                                DataBox dbox = (DataBox)PracticalCourseList.get(i); 
                                
                                v_seq            = dbox.getInt("d_num");
                                v_title          = dbox.getString("d_title");
                                v_professor      = dbox.getString("d_professor");
                    %>
      
      <tr>
        <td class="ct_tit_table_left"><a href="javascript:selectView(<%=v_seq%>);"><%=v_title%></a></td>
        <td class="ct_tit_table_right"><%=v_professor%></td>
      </tr>      
      		<%      }
                        } else {%>
                    <tr>
                        <td colspan="2">등록된 글이 없습니다.</td>
                    <%  } %>
                    </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="2" bgcolor="F7F7F7"></td>
      </tr>      
                    
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="10" colspan="3">&nbsp;</td>
      </tr>
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
        <td><img src="/images/portal/homepage_renewal/library/sstitle_07.gif"/></td>
        <td class="h_road"><img src="/images/portal/homepage_renewal/myclass/btn_more.gif" /></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" colspan="4" bgcolor="#7692CB"></td>
        </tr>
      <tr>
        <td colspan="4">&nbsp;</td>
        </tr>
      <tr>
        <td><div align="center">
          <table width="90%" border="0" cellspacing="0" cellpadding="5">
            <tr>
              <td><div align="center">
                <table width="140" border="0" cellpadding="0" cellspacing="1" bgcolor="eeeeee">
                  <tr>
                    <td height="130"><div align="center">img</div></td>
                  </tr>
                </table>
              </div></td>
            </tr>
            <tr>
              <td><div align="center">[방송]AVID<br />
              사이버콘텐츠아카데미<br />
              운영자</div></td>
            </tr>
          </table>
        </div></td>
        <td><div align="center">
          <table width="90%" border="0" cellspacing="0" cellpadding="5">
            <tr>
              <td><div align="center">
                  <table width="140" border="0" cellpadding="0" cellspacing="1" bgcolor="eeeeee">
                    <tr>
                      <td height="130"><div align="center">img</div></td>
                    </tr>
                  </table>
              </div></td>
            </tr>
            <tr>
              <td><div align="center">[방송]AVID<br />
                사이버콘텐츠아카데미<br />
                운영자</div></td>
            </tr>
          </table>
        </div></td>
        <td><div align="center">
          <table width="90%" border="0" cellspacing="0" cellpadding="5">
            <tr>
              <td><div align="center">
                  <table width="140" border="0" cellpadding="0" cellspacing="1" bgcolor="eeeeee">
                    <tr>
                      <td height="130"><div align="center">img</div></td>
                    </tr>
                  </table>
              </div></td>
            </tr>
            <tr>
              <td><div align="center">[방송]AVID<br />
                사이버콘텐츠아카데미<br />
                운영자</div></td>
            </tr>
          </table>
        </div></td>
        <td><div align="center">
          <table width="90%" border="0" cellspacing="0" cellpadding="5">
            <tr>
              <td><div align="center">
                  <table width="140" border="0" cellpadding="0" cellspacing="1" bgcolor="eeeeee">
                    <tr>
                      <td height="130"><div align="center">img</div></td>
                    </tr>
                  </table>
              </div></td>
            </tr>
            <tr>
              <td><div align="center">[방송]AVID<br />
                사이버콘텐츠아카데미<br />
                운영자</div></td>
            </tr>
          </table>
        </div></td>
      </tr>
      <tr>
        <td colspan="4">&nbsp;</td>
        </tr>
    </table>
    <!--타이틀부분//-->
    </td>
    <td width="20">&nbsp;</td>
    		      
		      </tr>		
		</table>
		
	<%}else{ %>

            <div class="info_visualbox">
                <p class="tit"><img src="/images/portal/information/main_tit.gif" alt="GOLD CLASS" /></p>
                <dl class="goldclass">
                    <%  if(GoldClassBox != null) { 
                    	v_seq        = GoldClassBox.getInt("d_seq");
                        v_vodimg     = GoldClassBox.getString("d_vodimg");
                        v_lecnm      = GoldClassBox.getString("d_lecnm");
                        v_tutornm    = GoldClassBox.getString("d_tutornm");
                        v_lectime    = GoldClassBox.getString("d_lectime");
                        v_creator    = GoldClassBox.getString("d_creator");
                        v_intro      = GoldClassBox.getString("d_intro");
                        v_width_s    = GoldClassBox.getString("d_width_s");
                        v_height_s   = GoldClassBox.getString("d_height_s");
                        v_viewcnt    = GoldClassBox.getInt("d_viewcnt");
                        v_checkpoin  = GoldClassBox.getFloat("d_checkpoin");
                        v_vodurl     = GoldClassBox.getString("d_vodurl");
                    %>
                    <dt><img src="/images/portal/information/main_txt.gif" alt="골드클래스는 각 분야의 최고 전문가 특강으로 이루어진 회원대상 동영상강좌입니다." /></dt>
                    <dd class="photo"><a href="javascript:openGoldClass(<%=v_seq %>, '<%=v_width_s %>','<%=v_height_s %>')"><img src="/servlet/controller.library.DownloadServlet?p_savefile=<%=v_vodimg %>" width="156" height="118" /></a></dd>
                    <dd class="tit"><a href="javascript:viewGoldClass(<%=v_seq%>);"><%=v_lecnm %></a></dd>
                    <dd class="con">강사명 : <%=v_tutornm %></dd>
                    <dd class="con">강의시간 : <%=v_lectime %></dd>
                    <dd class="star">평가 : <%=StringUtil.getStarPoint(v_checkpoin) %></dd>
                    <%  } else {%>
                    <dd>등록된 글이 없습니다.</dd>
                    <%  } %>
                </dl>
                <dl class="pre_goldclass">
                    <dt><img src="/images/portal/information/main_stit1.gif" alt="지난 골드클래스" class="fl_l" /><a href="javascript:morePreGoldClass();"><img src="/images/portal/btn/btn_txt_more.gif" alt="더보기" class="fl_r" /></a></dt>
                    <%  if(PreGoldClassList.size() != 0){
                            for(int i = 0; i < PreGoldClassList.size(); i++) {
                                DataBox dbox = (DataBox)PreGoldClassList.get(i);  
                   
			                    v_seq        = dbox.getInt("d_seq");
			                    v_vodimg     = dbox.getString("d_vodimg");
			                    v_lecnm      = dbox.getString("d_lecnm");
			                    v_tutornm    = dbox.getString("d_tutornm");
			                    v_lectime    = dbox.getString("d_lectime");
			                    v_creator    = dbox.getString("d_creator");
			                    v_intro      = dbox.getString("d_intro");
			                    v_width_s    = dbox.getString("d_width_s");
			                    v_height_s   = dbox.getString("d_height_s");
			                    v_viewcnt    = dbox.getInt("d_viewcnt");
			                    v_checkpoin  = dbox.getFloat("d_checkpoin");
			                    
			                    v_lecnm      = StringManager.formatTitle(v_lecnm, 20);
                    %>
                    <dd><a href="javascript:viewGoldClass(<%=v_seq%>);"><%=v_lecnm %> </a></dd>
                    <%      } 
                        } else { %>
                    <dd>등록된 글이 없습니다.</dd>
                    <%  } %>
                </dl>
            </div>

            <div class="main_warp">
                
                    <p class="main_stit"><img src="/images/portal/information/main_stit2.gif" alt="실무강좌" class="fl_l" /><a href="javascript:morePracticalCourse();" class="fl_r"><img src="/images/portal/btn/btn_txt_more.gif" alt="더보기" /></a></p>

                    <table class="main_list">
                    <!--[if ie]><colgroup><col width="315px" /><col width="90" /><![endif]-->
                    <colgroup><col width="520px" /><col width="130" /></colgroup>
                    <thead>
                    <tr class="info">
                        <th><img src="/images/portal/information/main_table_tit1.gif" alt="제목" /></th>
                        <th class="end"><img src="/images/portal/information/main_table_tit2.gif" alt="강사명" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <%  if(PracticalCourseList != null && PracticalCourseList.size() > 0){
                            for(int i = 0; i < PracticalCourseList.size(); i++) {
                            	if( i > 4 ) {
                            		break;
                            	}
                                DataBox dbox = (DataBox)PracticalCourseList.get(i); 
                                
                                v_seq            = dbox.getInt("d_num");
                                v_title          = dbox.getString("d_title");
                                v_professor      = dbox.getString("d_professor");
                    %>
                    <tr>
                        <td class="ta_l"><a href="javascript:selectView(<%=v_seq%>);"><%=v_title%></a> </td>
                        <td><%=v_professor%></td>
                    </tr>
                    <%      }
                        } else {%>
                    <tr>
                        <td colspan="2">등록된 글이 없습니다.</td>
                    <%  } %>
                    </tr>
                    </tbody>
                    </table>
                </div>


            <ul class="main_banner2">
                <li><a href="javascript:goSpecialCourse();"><img src="/images/portal/information/main_banner1.gif" alt="교육자료실" /></a></li>
                <li><a href="javascript:goGallery();"><img src="/images/portal/information/main_banner2.gif" alt="수강생갤러리" /></a></li>
                <li><a href="javascript:goInterview();"><img src="/images/portal/information/main_banner3.gif" alt="인터뷰" /></a></li>
                <li><a href="javascript:goEduReview();"><img src="/images/portal/information/main_banner4.gif" alt="교육후기" /></a></li>
                <li class="bgn"><a href="javascript:goExpertRegist();"><img src="/images/portal/information/main_banner5.gif" alt="전문가 등록하기" /></a></li>
            </ul>
            <%} %>
</form>
<!-- form 끝 -->


<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->