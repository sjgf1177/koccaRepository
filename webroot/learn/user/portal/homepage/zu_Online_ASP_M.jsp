<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process  = box.getString("p_process");

    int     v_seq           = 0;            // 일련번호
    int     v_cnt           = 0; 
    String  v_title         = "";           // 제목
    String  v_date          = "";           // 등록일

    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");

    ArrayList<DataBox> middleClassList   = (ArrayList)request.getAttribute("middleClassList");
    ArrayList<DataBox> mainSubjectList   = (ArrayList)request.getAttribute("mainSubjectList");
    ArrayList<DataBox> eventList         = (ArrayList)request.getAttribute("eventList");
    ArrayList<DataBox> noticeList        = (ArrayList)request.getAttribute("noticeList");
    ArrayList<DataBox> letterList        = (ArrayList)request.getAttribute("letterList");
    ArrayList<DataBox> seminarList       = (ArrayList)request.getAttribute("seminarList");
    
    HashMap<String, String> upperMap = null;    // 대분류 별 메뉴ID 매핑 맵 HashMap<upperlcass, menuid>
    
    upperMap   = ClassifySubjectBean.getMenuId(box);

    String authority=box.getString("authority");
    
%>

<%@ include file="/learn/user/portal/include/topASPMain.jsp"%>

<script language="javascript">
//알림판
function selectBanner(idx) {
    var bannerArray = document.getElementsByName("offline_banner");
    var imgArray = document.getElementsByName("bannerImg");
    
    var len = bannerArray.length;

    for(var i = 0 ; i < len ; i++) {
        if(i == idx ) {
        	bannerArray[i].style.display = "block";
        	imgover(imgArray[i]);
        	imgArray[i].onmouseover = function(){return;} 
        	imgArray[i].onmouseout  = function(){return;} 
        } else {
            bannerArray[i].style.display = "none";
            imgout(imgArray[i]);
            imgArray[i].onmouseover = function(){imgover(this);}
            imgArray[i].onmouseout  = function(){imgout(this);}
        } 
    }
}

var nextIdx = 0;
var run;

function getNextIdx() {
    var imgCnt = document.getElementsByName("bannerImg").length -1;
    		
    if(nextIdx == imgCnt) {
        nextIdx = 0;
    } else {
        nextIdx++;
    }
    selectBanner(nextIdx);        
}

function clickBanner(idx){
    window.clearInterval(run);  // 자동 변환 종료
    selectBanner(idx);  
}

window.onload = function () {
    run = setInterval("getNextIdx()", 2000); //자동일때 이미지 변환시간간격(2초)
}

function changeTab(idx) {
    var tabMenu = document.getElementsByName("tabMenu");
    var moreTab = document.getElementById("moreTab");
    var tabImg  = document.getElementsByName("tabImg");

    var len = tabMenu.length;

    for(var i = 0 ; i < len ; i++) {
        if( i == parseInt(idx) ) {
            tabMenu[i].style.display  = "block";
            imgover(tabImg[i]);
            tabImg[i].onmouseover = function(){return;}
            tabImg[i].onmouseout  = function(){return;}
        } else {
            tabMenu[i].style.display  = "none";
            imgout(tabImg[i]);
            tabImg[i].onmouseover = function(){imgover(this);}
            tabImg[i].onmouseout  = function(){imgout(this);}
        }
    }

    switch(idx){
        case "0" :
            moreTab.href = "javascript:moreNotice();";    
            break;
        case "1" :  
            moreTab.href = "javascript:moreLetter();";
            break;
    } 
}

function moreNotice() {
    document.form1.action = "/servlet/controller.homepage.HomeNoticeServlet";
    document.form1.p_process.value = "List";
    document.form1.menuid.value = "07";
    document.form1.gubun.value = "4";
    document.form1.target = "_self";
    document.form1.submit();
}

function viewNotice(seq) {
    document.form1.action = "/servlet/controller.homepage.HomeNoticeServlet";
    document.form1.p_process.value = "selectView";
    document.form1.menuid.value = "07";
    document.form1.gubun.value = "4";
    document.form1.p_seq.value= seq;
    document.form1.target = "_self";
    document.form1.submit();
 }

function moreLetter() {
    document.form1.action = "/servlet/controller.homepage.HomeLetterServlet";
    document.form1.p_process.value = "List";
    document.form1.menuid.value = "08";
    document.form1.gubun.value = "4";
    document.form1.target = "_self";
    document.form1.submit();
}

function viewLetter(seq) {
    document.form1.action = "/servlet/controller.homepage.HomeLetterServlet";
    document.form1.p_process.value = "selectView";
    document.form1.menuid.value = "08";
    document.form1.gubun.value = "4";
    document.form1.p_seq.value= seq;
    document.form1.target = "_self";
    document.form1.submit();
}

function whenSubjInfo(subj,subjnm,courseyn,upperclass,upperclassnm, year, subjseq, menuid){
    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value = subjnm;
    document.form1.p_iscourseYn.value = courseyn;
    document.form1.p_upperclass.value = upperclass;
    document.form1.p_upperclassnm.value = upperclassnm;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.menuid.value = menuid;
    document.form1.p_process.value = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'SubjectList';
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.target = "_self";
    document.form1.submit();
}

function whenSubjList() {
    document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
    document.form1.p_process.value = "SubjectList";
//    document.form1.menuid.value = "07";
//    document.form1.gubun.value = "4";
//    document.form1.p_seq.value= seq;
    document.form1.target = "_self";
    document.form1.submit();
 }
</script>

<!-- container -->
<div id="container" class="asp_main">
	<div id="contentWrap">
	
	<%@ include file="/learn/user/portal/include/leftASPMain.jsp"%>
	
	<div id="ajaxDiv"></div>
	
	<form name="form1" method="post">
	    <input type = "hidden" name = "p_process">
	    <input type = "hidden" name = "p_seq">
	    <input type = "hidden" name = "faqcategory">
	    <input type = "hidden" name = "p_types">
	    <input type = "hidden" name = "gubun" value = "1">
	    <input type = "hidden" name = "menuid">
	    
	    <input type = "hidden" name = "s_mclassnm">
	    
	    <input type="hidden" name="p_rprocess"  value=""/>
	    <input type="hidden" name="p_year"      value=""/>
	    <input type="hidden" name="p_subj"      value=""/>
	    <input type="hidden" name="p_subjseq"   value=""/>
	    <input type="hidden" name="p_subjnm"    value=""/>
	    <input type="hidden" name="p_iscourseYn"    value=""/>
	    <input type="hidden" name="p_upperclass"    value=""/>
	    <input type="hidden" name="p_upperclassnm"  value=""/>
	<div id="main">
			<div class="visual">
				<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="625" height="260" id="top1" align="middle">
				<param name="allowScriptAccess" value="sameDomain" />
				<param name="movie" value="/images/portal/flash/aspmain.swf" />
				<param name="quality" value="high" />
				<param name="wmode" value="transparent" />
				<embed src="/images/portal/flash/aspmain.swf" wmode="transparent" quality="high" bgcolor="#ffffff" width="625" height="260" name="top1" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
				</object>
			</div>

			<div class="leftwarp">
				<!-- 공지사항,뉴스레터 -->
				<div class="offline_box">
					<ul class="tab">
						<li><a href="javascript:changeTab('0');"><img id="tabImg" name="tabImg" src="/images/portal/asp/new_menu1_on.gif" alt="공지사항"/></a></li>
						<li><a href="javascript:changeTab('1');"><img id="tabImg" name="tabImg" src="/images/portal/asp/new_menu2_off.gif" alt="뉴스레터" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></li>
						<li class="more"><a id="moreTab" href="javascript:moreNotice();"><img src="/images/portal/asp/btn_more.gif" alt="more" /></a></li>
					</ul>

					<ul id="tabMenu" name="tabMenu" class="list">
					<%  if(noticeList != null && noticeList.size() > 0) {
							int ncnt = noticeList.size();
							if(ncnt > 4) {ncnt = 4;}
                            for(int i = 0 ; i < ncnt; i ++) {
                                DataBox dbox = (DataBox)noticeList.get(i); %>
						<li><a href="javascript:viewNotice(<%=dbox.getString("d_seq") %>);"><%=StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_adtitle")), 25) %></a> <span class="data"><%=FormatDate.getFormatDate(dbox.getString("d_addate"), "yyyy.MM.dd")%></span></li>
					<%      }  
                    }%>	
					</ul>
					
					<ul id="tabMenu" name="tabMenu" class="list" style="display:none;">
					<%  if(letterList != null && letterList.size() > 0) {
							int lcnt = letterList.size();
							if(lcnt > 4) {lcnt = 4;}
                            for(int i = 0 ; i < lcnt; i ++) {
                                DataBox dbox = (DataBox)letterList.get(i); %> 
						<li><a href="javascript:viewLetter(<%=dbox.getString("d_seq") %>);"><%=StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_adtitle")), 25) %></a> <span class="data"><%=FormatDate.getFormatDate(dbox.getString("d_addate"), "yyyy.MM.dd")%></span></li>
					<%		}
					}%>
					</ul>
				</div>
				<!-- 공지사항,뉴스레터 끝 -->
				
				<p class="main_stit pd_t30">
					<img src="/images/portal/asp/class_tit.gif" alt="강좌 모음" class="fl_l" />
					<span class="fl_r">
						<a href="javascript:whenSubjList();"><img src="/images/portal/asp/btn_more.gif" alt="more" class="more" /></a>
					</span>
				</p>
				<div class="offline_photo">
				<%  if(mainSubjectList != null && mainSubjectList.size() > 0) {
                        String courseImgPath = "";
                        String divId         = "";
                        String alt           = "";   
                        int mcnt = mainSubjectList.size();
                        if(mcnt > 2) {mcnt=2;}
                	    for(int i = 0 ; i < mcnt ; i++) {
                	        DataBox dbox = mainSubjectList.get(i);
                	        if(dbox.get("d_isunit").equals("Y"))       { divId= "<img src=\"/images/portal/offline/main_photo_ico1.gif\" alt=\"인증\" />"; }
                	        else if(dbox.get("d_ishit").equals("Y"))   { divId= "<img src=\"/images/portal/offline/main_photo_ico2.gif\" alt=\"인기\" />"; }
                	        else if(dbox.get("d_isnew").equals("Y"))   { divId= "<img src=\"/images/portal/offline/main_photo_ico3.gif\" alt=\"추천\" />"; }
                	        else if(dbox.get("d_isrecom").equals("Y")) { divId= "<img src=\"/images/portal/offline/main_photo_ico4.gif\" alt=\"신규\" />"; }
                	        else { divId = "4"; alt = "없음"; }
                	        
                	        if(!dbox.get("d_submainfilenamenew").equals("")) 
                	        	courseImgPath = "/servlet/controller.library.DownloadServlet?p_savefile="+dbox.get("d_submainfilenamenew");
                	        else courseImgPath = "/images/portal/thumb/photo_professor.jpg"; %>
					<dl <%=i == 1 ? " class='mg_rn'" : ""%>>
						<dt><img src="<%=courseImgPath %>" width="159" height="70" alt="<%=dbox.getString("d_subjnm") %>"/>
							<div id="offphoto_ico<%=i+1 %>"><%=divId %></div>
						</dt>
						<dd><a href="javascript:whenSubjInfo('<%=dbox.get("d_subj")%>','<%=dbox.get("d_subjnm")%>','<%=dbox.get("d_isonoff")%>','<%=dbox.get("d_scupperclass")%>',
                                    '<%=dbox.get("d_uclassnm")%>','<%=dbox.get("d_scyear")%>','<%=dbox.get("d_subjseq")%>',
                                    '<%=upperMap !=null && upperMap.get(dbox.get("d_scupperclass")) != null ?  upperMap.get(dbox.get("d_scupperclass")) : "00" %>')"><%=dbox.getString("d_subjnm") %></a></dd>
					</dl>
				<%      }
                	}%>	
				</div>
			</div>

			<div class="rightwarp">
				<div class="offline_box wd_250">
					<p class="main_stit">
						<span class="fl_r">
							<a href="javascript:clickBanner(0);"><img id="bannerImg" name="bannerImg" src="/images/portal/asp/main_roll_num1_on.gif" alt="1" /></a>
							<a href="javascript:clickBanner(1);"><img id="bannerImg" name="bannerImg" src="/images/portal/asp/main_roll_num2_off.gif" alt="2" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
							<a href="javascript:clickBanner(2);"><img id="bannerImg" name="bannerImg" src="/images/portal/asp/main_roll_num3_off.gif" alt="3" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
							<a href="javascript:clickBanner(3);"><img id="bannerImg" name="bannerImg" src="/images/portal/asp/main_roll_num4_off.gif" alt="4" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
						</span>
					</p>

					<p id="offline_banner" name="offline_banner" class="offline_banner"><img src="/images/portal/offline/main_roll_banner1.jpg" /></p>
					<p id="offline_banner" name="offline_banner" class="offline_banner" style="display:none;"><img src="/images/portal/offline/main_roll_banner2.jpg" /></p>
                    <p id="offline_banner" name="offline_banner" class="offline_banner" style="display:none;"><img src="/images/portal/offline/main_roll_banner3.jpg" /></p>
                    <p id="offline_banner" name="offline_banner" class="offline_banner" style="display:none;"><img src="/images/portal/offline/main_roll_banner4.jpg" /></p>
				</div>
			
				<p class="main_stit pd_t30">
					<img src="/images/portal/asp/course_tit.gif" alt="강좌 모음" class="fl_l" />
					<span class="fl_r">
						<a href=""><img src="/images/portal/asp/btn_more.gif" alt="more" class="more" /></a>
					</span>
				</p>
				<dl class="course">
					<dt><a href=""><img src="/images/portal/asp/course_img.jpg" /></a></dt>
					<dd class="tit"><a href="">영상촬영의 이해 영상...</a></dd>
					<dd class="txt"><a href="">카메라, 렌즈, 화면구성, 노출 등에 대해 기본적인 사항들 학습본적인 사항들 학습...</a></dd>
				</dl>
			</div>
		</div>
		</form>
		
<!-- footer -->
<%@ include file="/learn/user/portal/include/footerNoLeftMenu.jsp"%>
<!--// footer -->