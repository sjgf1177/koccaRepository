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
    String  v_repstatus     = "";           // 답변상태(qna)
    String  v_types         = "";           // QNA 글 타입
    String  v_faqcategory   = "";           // FAQ 카테고리
    String  v_winneryn      = "";           // 이벤트 담청자 여부
    String  v_passyn        = "";           // 워크샵 선정 여부

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
    
    Calendar cal=Calendar.getInstance();
    String month=Integer.toString(cal.get(Calendar.MONTH)+2);
    month= month.length()==1 ? "0"+month:month;

//    System.out.println("upperMap : "+upperMap); 
    
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>

<!-- 스크립트영역 -->

<SCRIPT LANGUAGE="JavaScript">

  //과정 내용보기
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

    function goSubjectList(upper, menuid, middle) {
        document.form1.p_upperclass.value = upper;
        if(middle == null) document.form1.s_mclassnm.value = "";
        else document.form1.s_mclassnm.value = middle;
        document.form1.menuid.value = menuid;
        document.form1.p_process.value = "SubjectList";
        document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
        document.form1.submit();
    }

//-->
</SCRIPT>

<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
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
    
            <div class="online_visualbox">
                <p class="tit"><img src="/images/portal/online/main_tit.gif" alt="ONLINE CURICULUM" /></p>
                
                <dl>
                    <dt><a href="javascript:goSubjectList('C01', '<%=upperMap !=null && upperMap.get("C01") != null ?  upperMap.get("C01") : "00" %>');"><img src="/images/portal/online/main_menu1.gif" alt="기획" /></a></dt>
                    <dd>
                    <%  if(middleClassList != null && middleClassList.size() > 0 ) {
                    	    int commaCnt = 0;
                    	    for(int i = 0 ; i < middleClassList.size() ; i++ ) {
                    	        DataBox dbox = middleClassList.get(i);
                    	        if(dbox.getString("d_upperclass").equals("C01")) { %>
                    <a href="javascript:goSubjectList('C01', '<%=upperMap !=null && upperMap.get("C01") != null ?  upperMap.get("C01") : "00" %>', '<%=dbox.getString("d_middleclass") %>')"><%= (commaCnt != 0) ? " , " : ""%><%=dbox.getString("d_middleclassname") %></a>
                    <%          commaCnt++;
                                }
                            }
                        } %>
                    </dd>
                </dl>

                <dl>
                    <dt><a href="javascript:goSubjectList('C02', '<%=upperMap !=null && upperMap.get("C02") != null ?  upperMap.get("C02") : "00" %>');"><img src="/images/portal/online/main_menu2.gif" alt="제작" /></a></dt>
                    <dd>
                    <%  if(middleClassList != null && middleClassList.size() > 0 ) {
                    	    int commaCnt = 0;
                            for(int i = 0 ; i < middleClassList.size() ; i++ ) {
                                DataBox dbox = middleClassList.get(i);
                                if(dbox.getString("d_upperclass").equals("C02")) { %>
                    <a href="javascript:goSubjectList('C02', '<%=upperMap !=null && upperMap.get("C02") != null ?  upperMap.get("C02") : "00" %>', '<%=dbox.getString("d_middleclass") %>')"><%= (commaCnt != 0) ? " , " : ""%><%=dbox.getString("d_middleclassname") %></a>
                    <%          commaCnt++;
                                }
                            }
                        } %>
                    </dd>
                </dl>

                <dl>
                    <dt><a href="javascript:goSubjectList('C03', '<%=upperMap !=null && upperMap.get("C03") != null ?  upperMap.get("C03") : "00" %>');"><img src="/images/portal/online/main_menu3.gif" alt="유통" /></a></dt>
                    <dd>
                    <%  if(middleClassList != null && middleClassList.size() > 0 ) {
                    	    int commaCnt = 0;
                            for(int i = 0 ; i < middleClassList.size() ; i++ ) {
                                DataBox dbox = middleClassList.get(i);
                                if(dbox.getString("d_upperclass").equals("C03")) { %>
                    <a href="javascript:goSubjectList('C03', '<%=upperMap !=null && upperMap.get("C03") != null ?  upperMap.get("C03") : "00" %>', '<%=dbox.getString("d_middleclass") %>')"><%= (commaCnt != 0) ? " , " : ""%><%=dbox.getString("d_middleclassname") %></a>
                    <%          commaCnt++;
                                }
                            }
                        } %>
                    </dd>
                </dl>

                <dl>
                    <dt><a href="javascript:goSubjectList('C04', '<%=upperMap !=null && upperMap.get("C04") != null ?  upperMap.get("C04") : "00" %>');"><img src="/images/portal/online/main_menu4.gif" alt="경영지원" /></a></dt>
                    <dd>
                    <%  if(middleClassList != null && middleClassList.size() > 0 ) {
                    	    int commaCnt = 0;
                            for(int i = 0 ; i < middleClassList.size() ; i++ ) {
                                DataBox dbox = middleClassList.get(i);
                                if(dbox.getString("d_upperclass").equals("C04")) { %>
                    <a href="javascript:goSubjectList('C04', '<%=upperMap !=null && upperMap.get("C04") != null ?  upperMap.get("C04") : "00" %>', '<%=dbox.getString("d_middleclass") %>')"><%= (commaCnt != 0) ? " , " : ""%><%=dbox.getString("d_middleclassname") %></a>
                    <%          commaCnt++;
                                }
                            }
                        } %>
                    </dd>
                </dl>

                <dl>
                    <dt><a href="javascript:goSubjectList('C05', '<%=upperMap !=null && upperMap.get("C05") != null ?  upperMap.get("C05") : "00" %>');"><img src="/images/portal/online/main_menu5.gif" alt="특강" /></a></dt>
                    <dd>
                    <%  if(middleClassList != null && middleClassList.size() > 0 ) {
                    	    int commaCnt = 0;
                            for(int i = 0 ; i < middleClassList.size() ; i++ ) {
                                DataBox dbox = middleClassList.get(i);
                                if(dbox.getString("d_upperclass").equals("C05")) { %>
                    <a href="javascript:goSubjectList('C05', '<%=upperMap !=null && upperMap.get("C05") != null ?  upperMap.get("C05") : "00" %>', '<%=dbox.getString("d_middleclass") %>')"><%= (commaCnt != 0) ? " , " : ""%><%=dbox.getString("d_middleclassname") %></a>
                    <%          commaCnt++;
                                }
                            }
                        } %>
                    </dd>
                </dl>
            </div>

            <p class="main_stit mg_t30"><img src="/images/portal/offline/main_stit1.gif" alt="강좌 모음" /></p>
            <div class="offline_photo">
                <%  if(mainSubjectList != null && mainSubjectList.size() > 0) {
                        String courseImgPath = "";
                        String divId         = "";
                        String alt           = "";                        
                	    for(int i = 0 ; i < mainSubjectList.size() ; i++) { 
                	        DataBox dbox = mainSubjectList.get(i);
                	        if(dbox.get("d_isunit").equals("Y"))       { divId= "<img src=\"/images/portal/offline/main_photo_ico1.gif\" alt=\"인증\" />"; }
                	        else if(dbox.get("d_ishit").equals("Y"))   { divId= "<img src=\"/images/portal/offline/main_photo_ico2.gif\" alt=\"인기\" />"; }
                	        else if(dbox.get("d_isnew").equals("Y"))   { divId= "<img src=\"/images/portal/offline/main_photo_ico3.gif\" alt=\"추천\" />"; }
                	        else if(dbox.get("d_isrecom").equals("Y")) { divId= "<img src=\"/images/portal/offline/main_photo_ico4.gif\" alt=\"신규\" />"; }
                	        else { divId = "4"; alt = "없음"; }
                	        

                	        if( !dbox.get("d_introducefilenamenew").equals("") ) {
                	        	if( dbox.get("d_introducefilenamenew").indexOf("upload") < 0 ) {
                	        		courseImgPath = "/servlet/controller.library.DownloadServlet?p_savefile=upload/bulletin/"+dbox.get("d_introducefilenamenew");
                	        	} else {
                	        		courseImgPath = "/servlet/controller.library.DownloadServlet?p_savefile="+dbox.get("d_introducefilenamenew");
                	        	}
                	        } else  {
                	        	courseImgPath = "/images/portal/thumb/photo_professor.jpg";
                	        }
            	        	%>
                <dl<%=i ==   mainSubjectList.size()-1 ? " class='mg_rn'" : ""%>>
                    <dt><img src="<%=courseImgPath %>"  height="70" alt="<%=dbox.getString("d_subjnm") %>" />
                        <div id="offphoto_ico<%=(i+1) > 4 ? 4%(i+1) : i+1%>"><%=divId %></div>
                    </dt>
                    <dd><a href="javascript:whenSubjInfo(
                                    '<%=dbox.get("d_subj")%>',
                                    '<%=dbox.get("d_subjnm")%>',
                                    '<%=dbox.get("d_isonoff")%>',
                                    '<%=dbox.get("d_scupperclass")%>',
                                    '<%=dbox.get("d_uclassnm")%>',
                                    '<%=dbox.get("d_scyear")%>',
                                    '<%=dbox.get("d_subjseq")%>',
                                    '<%=upperMap !=null && upperMap.get(dbox.get("d_scupperclass")) != null ?  upperMap.get(dbox.get("d_scupperclass")) : "00" %>')"><%=dbox.getString("d_subjnm") %></a></dd>
                </dl>
                <%      }
                	}%>
            </div>
            
            <div class="main_warp">
                <!-- 공통 공지, 이벤, 워크, 뉴스레터 시작 -->
	            <%@ include file="/learn/user/portal/include/subMain_include.jsp"%>
	            <!-- 공통 공지, 이벤, 워크, 뉴스레터 종료 -->

                <div class="offline_box wd_250">
                    <p class="main_stit">
                        <img src="/images/portal/offline/main_stit2.gif" alt="알림판" class="fl_l" />
                        <span class="fl_r">
                            <a href="javascript:clickBanner(0);"><img id="bannerImg" name="bannerImg" src="/images/portal/btn/main_roll_num1_on.gif" alt="1" /></a>
                           <!-- <a href="javascript:clickBanner(1);"><img id="bannerImg" name="bannerImg" src="/images/portal/btn/main_roll_num2_off.gif" alt="2" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
                            <a href="javascript:clickBanner(2);"><img id="bannerImg" name="bannerImg" src="/images/portal/btn/main_roll_num3_off.gif" alt="3" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
                            <a href="javascript:clickBanner(3);"><img id="bannerImg" name="bannerImg" src="/images/portal/btn/main_roll_num4_off.gif" alt="4" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a> -->
                        </span>
                    </p>

                    <p id="offline_banner" name="offline_banner" class="offline_banner"><a href="http://edu.kocca.or.kr/servlet/controller.homepage.MainServlet?menuid=11&gubun=1&p_month=<%=month%>"><img src="/images/portal/online/banner/100420-banner.gif" /></a></p>
                <!--     <p id="offline_banner" name="offline_banner" class="offline_banner"><a href="http://www.kocca.or.kr/sub/kocca/nn/NNNotice2.srt?method=view&rnum=608&nm_seq=20620"><img src="/images/portal/online/banner/100409-banner.gif" /></a></p> -->
                  <!--   <p id="offline_banner" name="offline_banner" class="offline_banner" style="display:none;"><img src="/images/portal/offline/main_roll_banner2.jpg" /></p>
                    <p id="offline_banner" name="offline_banner" class="offline_banner" style="display:none;"><img src="/images/portal/offline/main_roll_banner3.jpg" /></p>
                    <p id="offline_banner" name="offline_banner" class="offline_banner" style="display:none;"><img src="/images/portal/offline/main_roll_banner4.jpg" /></p> -->
                </div>
            </div>

            <div class="main_warp">
                <div class="offline_box wd_400 mg_r30">
                    <ul class="main_banner1 mg_tn">
                        <li><a href="javascript:menuForward('1', '01');"><img src="/images/portal/offline/main_banner1.gif" alt="교육과정 안내" /></a></li>
                        <li><a href="javascript:menuForward('1', '11');"><img src="/images/portal/offline/main_banner2.gif" alt="월간교육일정" /></a></li>
                        <li class="bgn"><a href="javascript:menuForward('1', '13');"><img src="/images/portal/offline/main_banner3.gif" alt="수강신청 안내" /></a></li>
                    </ul>
                </div>

                <div class="offline_box wd_250">
                    <p class="main_stit"><img src="/images/portal/offline/main_stit3.gif" alt="교육문의" /></p>

                    <p class="main_eduinquiry"><img src="/images/portal/common/online_eduservice.gif" alt="" /></p>
                    <!--dl class="offline_phone">
                        <dt><img src="/images/portal/online/main_txt1.gif" alt="방송영상" /></dt>
                        <dd><img src="/images/portal/online/main_txt11.gif" alt="02 - 3219 - 6519 / 6133" /></dd>
                        <dt><img src="/images/portal/online/main_txt2.gif" alt="게임개발" /></dt>
                        <dd><img src="/images/portal/online/main_txt22.gif" alt="02 - 3219 - 5483 / 6538" /></dd>
                        <dt class="bo_bn"><img src="/images/portal/online/main_txt3.gif" alt="문화콘텐츠" /></dt>
                        <dd class="bo_bn"><img src="/images/portal/online/main_txt33.gif" alt="02 - 3219 - 6534 / 5720" /></dd>
                    </dl-->
                </div>
            </div>

</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->

<% System.out.println("zu_Online_Course_M.jsp 처리 종료"); %>
