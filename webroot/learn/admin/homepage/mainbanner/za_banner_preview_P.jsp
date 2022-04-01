<%@ page contentType="text/html;charset=utf-8" %>
<%@page errorPage= "/learn/library/error.jsp" %>
<%@ page import= "java.util.*" %>
<%@ page import= "com.credu.homepage.*" %>
<%@ page import= "com.credu.library.*" %>
<%@ page import= "com.credu.common.*" %>
<%@ page import= "com.credu.course.*" %>
<%@ page import= "com.credu.system.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    ArrayList viewBannerList = (ArrayList)request.getAttribute("viewBannerList");

    DataBox dbox = null;
    DataBox sdbox = null;
    String process = box.getString("p_process");
    int seq = 0;
    String title = "";
    String onoff_flag = "";
    String use_yn = "";
    int sort_order = 0;
    String start_dt = "";
    String end_dt = "";
    String img_nm = "";
    String save_img_nm = "";
    String img_size = "";
    String url = "";
    String url_target = "";
    String explain = "";
    String period_yn       = "";
    String fixed_flag      = "";
    int img_width = 0;



%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="subject" content="한국콘텐츠아카데미" />
<meta name="title" content="한국콘텐츠아카데미" />
<meta name="description" content="한국콘텐츠아카데미" />
<meta name="keywords" content="kocca, 한국콘텐츠아카데미" />
<meta name="robots" content="index,follow" />
<!--<title>kocca 한국콘텐츠아카데미(84)</title>-->
<title>한국콘텐츠아카데미  - edu.kocca.or.kr</title>

<style type="text/css" media="screen">@import "/css/2013/common.css";</style>
<style type="text/css" media="screen">@import "/css/2013/layout.css";</style>
<style type="text/css" media="screen">@import "/css/2013/main.css";</style>

<script type="text/javascript" src="/js/2013/caption.js"></script>
<script type="text/javascript" src="/js/2013/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/js/2013/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="/js/2013/jquery.selectbox-0.6.1.js"></script>
<script type="text/javascript" src="/js/2013/jquery.cycle2.min.js"></script>
<script type="text/javascript" src="/js/2013/common.js"></script>
<script type="text/javascript" src="/js/2013/main.js"></script>
<script type="text/javascript" src="/js/2013/lib.js"></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/mainscript.js"></script>

<script type="text/javascript">
    /**
     * 패스워드 엔터키 체크
     */
    function password_enter(e) {
        if (e.keyCode == "13") {
            login_main();
        }
    }
</script>
</head>
<body>
<form name="topdefaultForm" method="post" action="/" id="topdefaultForm">
    
</form>
    <div id="shortcutmenu">
        <a href="#mainMenu">주메뉴로 바로가기</a>
        <a href="#contents">본문으로 바로가기</a>
    </div>
    <!-- //#shortcutmenu -->

    <div id="header">
        <h1><a href="/servlet/controller.homepage.MainServlet"><img src="/images/2013/common/logo.png" alt="kocca 한국콘텐츠아카데미" /></a></h1>
        <ul class="util">
            
            <li class="name">운영자 님</li>
            
        </ul>
    </div>
    <!-- //#header -->

    <hr />

    <div id="sideMenu">
        <div class="search">
            <fieldset>
                <legend>빠른 검색</legend>
                <input type="text" name="topWarp_searchText"  id="topWarp_searchText" title="검색어" class="keyword" />
                <img src="/images/2013/btn/btn_src.gif" alt="검색" class="btn" />
            </fieldset>
        </div>
        <div class="eduSup">
            <h2 style="margin-bottom:5px;"><img src="/images/2013/common/sidemenu_tit2.gif" alt="아카데미 소개" /></h2>
            <h2><img src="/images/2013/common/sidemenu_tit.gif" alt="학습지원" /></h2>
            <ul>
                <li><img src="/images/2013/common/sidemenu_1.gif" alt="원격지원서비스" /></li>
                <li><img src="/images/2013/common/sidemenu_2.gif" alt="학습환경설정" /></li>
                <li><img src="/images/2013/common/sidemenu_4.gif" alt="교육문의" /></li>
            </ul>
        </div>
        <div class="sns">
            <ul>
                <li><img src="/images/2013/main/btn_sns_fb.gif" alt="페이스북"/></li>
                <li><img src="/images/2013/main/btn_sns_tw.gif" alt="트위터"/></li>
                <li><img src="/images/2013/main/btn_sns_nv.gif" alt="블로그"/></li>
                <li><img src="/images/2013/main/btn_sns_yt.gif" alt="유튜브"/></li>
            </ul>
        </div>

<script>
    function poplar_search(t){
        $("#topWarp_searchText").val(t);
        totalSubjSearch1();
    }

</script>
        <div style="margin-bottom: 55px;"></div>


    </div>
    <!-- //#sidemenu -->

    <hr />

    <div id="quickEduMenu">
        <ul class="tab">
            <li><a href=""><img src="/images/2013/common/quick_tab_1.gif" alt="온라인 교육" /></a></li>
            <li><a href=""><img src="/images/2013/common/quick_tab_2.gif" alt="현장 교육" /></a></li>
            <li><a href=""><img src="/images/2013/common/quick_tab_3.gif" alt="열린 강좌" /></a></li>
        </ul>
        <p class="viewAll"><a href=""><img src="/images/2013/btn/btn_all.gif" alt="전체보기" /></a></p>

        

    </div>
    <!-- //#quickEduMenu -->

    <hr />

    <div class="mainbnrControl">
        <div class="nav">
            <div class="prev"><img src="/images/2013/main/btn_mainbnr_prev.png" alt="이전" /></div>
            <div class="pg"></div>
            <div class="next"><img src="/images/2013/main/btn_mainbnr_next.png" alt="다음" /></div>
        </div>

        <div class="control">
            <a href="#" class="play"><img src="/images/2013/main/btn_mainbnr_play_on.png" alt="재생" /></a>
            <a href="#" class="stop"><img src="/images/2013/main/btn_mainbnr_pause.png" alt="정지" /></a>
        </div>
    </div>

    <div id="container">


<style type="text/css">
    /* * {margin:0; padding:0;} */
    /* img {border:0; vertical-align:top;} */

    #divPopup { position: absolute; left: 0px; top: 0px; width: 100%; height: 100%; margin:0; auto; z-index:9999; vertical-align:middle; text-align: center; display: none; }
    #noticePopup {position: absolute; left: 190px; top:150px; width:500px; height:460px;  z-index:9999; }
    #checkPopup {position: absolute; left: 190px; top:610px; width:500px; height:25px; background-color:#303030; font:12px Dotum; color:white; text-align:center; padding-top:10px; z-index:9999; } 
</style>

<script type="text/javascript">

    /* 패스워드 엔터키 체크 */
    function password_enter(e) {
      if (e.keyCode =='13'){  login_main();  }
  }

    /*
    function searchtotal_enter(e)  {
        if (e.keyCode == "13") {
            totalSubjSearch1();
        }
    }
    */

  // 로그인
  function login_main() {
      if (input_chk()){
          document.login.p_process.value   = "login";
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
          alert( "아이디를 입력하십시요.");
          document.login.p_userid.focus();
          return(false);
      }
      if (document.getElementById('p_pwd').value == "" || document.getElementById('p_pwd').value == null )
      {
          document.login.p_pwd.value="";  //폼창에 NULL값 리턴
          alert( "비밀번호를 입력하십시요.");
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


    /**
     * 전체 과정검색
     */
    /*
    function totalSubjSearch1() {
        var searchText = $("#topWarp_searchText").val();
        var gubun = "1";
        var url = "/servlet/controller.propose.MainSubjSearchServlet?p_process=SubjSearch&p_lsearchtext="+searchText;
        menuMainForward(gubun, url);
    }
    */

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

    function closeNoticePopup() {
        var bCheckOpen = $("input:checkbox[name=checkOpen]").eq(0).is(":checked");
        if ( bCheckOpen ) {
            setCookie("noiceIDC", "Y", 1);
        }
        $("#divPopup, #noticePopup, #checkPopup").hide();
    }

    function setCookie(name, value, expiredays) {
        var todayDate = new Date();
        todayDate.setDate( todayDate.getDate() + expiredays );
        document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
    }

    $(document).ready(function() {
        /*
        var cookiedata = document.cookie;
        if(cookiedata.indexOf("noiceIDC=Y") < 0) {
            $("#divPopup, #noticePopup, #checkPopup").show();
            $("#oCloseLink").focus();
        } else {
            $("#divPopup, #noticePopup, #checkPopup").hide();
        }
        */
    });
</script>

<form name="form1" method="post" id="form1" action="/">
 
 </form>

 <form name="pr_form" method="post" action="/servlet/controller.infomation.BriefingHomePageServlet">
    <input type="hidden" name="p_seq"/>
    <input type="hidden" name="p_process"/>
    <input type="hidden" name="p_tabseq"/>
 </form>
        <!-- 메인 -->
        <div class="mainContent">
            <!-- mainbnr -->
            <!--
 class 별 이미지 사이즈
 1. w1 : 280 * 275 or 280 * 276
 2. w2 : 570 * 275 or 570 * 276
 3. w3 : 860 * 275 or 860 * 276

 배너 구성 가능 조합
 1. w1
 2. w2 + w3
 3. w3 + w2
 4. w3 + w3 + w3
 -->
                       <!-- mainBannerSection -->
            <div class="mainBannerSection">
                <!-- //mainbnr -->
                <div class="mainbnr">
                    <h2 class="blind">메인배너</h2>
                    <div class="viewport">

<%

        if ( viewBannerList == null || viewBannerList.size() == 0 ) {
%>
                
                등록된 내용이 없습니다.
                
<%
        } else {

            for(int i = 0; i < viewBannerList.size(); i++) {

                dbox = (DataBox)viewBannerList.get(i);
            

                seq             = dbox.getInt("d_seq");
                title           = dbox.getString("d_title");
                sort_order      = dbox.getInt("d_sort_order");
                save_img_nm     = dbox.getString("d_save_img_nm").replace("\\", "/");
                img_size        = dbox.getStringDefault("d_img_size", "W2");
                url             = dbox.getString("d_url");
                url_target      = dbox.getString("d_url_target");
                explain         = dbox.getString("d_explain");
                period_yn       = dbox.getString("d_period_yn");
                fixed_flag      = dbox.getString("d_fixed_flag");

                if(img_size.equals("W3")){
                    img_width = 860;
                }else if(img_size.equals("W2")){
                    img_width = 570;
                }else if(img_size.equals("W1")){
                    img_width = 280;
                }

                if(period_yn.equals("Y") && fixed_flag.equals("N") ){

%>

                        <ul class="group">
                            <li class="<%=img_size%>">
                                <a href="<%=url%>" target="<%=url_target%>">
                                <img src="<%=save_img_nm%>" alt="<%=explain%>" width="<%=img_width%>" height="276" />
                                </a>
                            </li>
                        </ul>

<%
                }else if(fixed_flag.equals("Y")){
                    sdbox = (DataBox)viewBannerList.get(i);
                }
                
            }

%>
            </div>
                </div>
                <!-- //mainbnr -->

                <!-- mainbnrSide -->
                <div class="mainbnrSide">

                        <ul>
                            <li class="<%=sdbox.getString("d_img_size")%>">
                                <a href="<%=sdbox.getString("d_url")%>" target="<%=sdbox.getString("d_url_target")%>">
                                <img src="<%=sdbox.getString("d_save_img_nm")%>" alt="<%=sdbox.getString("d_explain")%>" width="280" height="276" />
                                </a>
                            </li>
                        </ul>

                </div>
                <!-- /mainbnrSide -->
            </div>
            <!-- /mainBannerSection -->
<%
        }

%>
        <!-- //mainContent -->
        <!-- //메인 -->

    <!-- //#container -->

  

        </div>
    <hr />

    
<!-- //#wrap -->
</body>
</html>

