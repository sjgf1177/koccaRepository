<%
//**********************************************************
//  1. 제      목: 학습창 메인화면 - 과정공지사항 예스런
//  2. 프로그램명: zu_SubjGongStudy_L.jsp
//  3. 개      요: 학습창 메인화면 - 과정 공지 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 08
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_contenttype   = box.getString("p_contenttype");

    int     v_seq       = 0;
    String  v_typesnm   = "";
    String  v_title     = "";
    String  v_addate    = "";
    int     i = 0;
    String v_promotion  = (String)request.getAttribute("promotion");        //권장진도율
    String v_progress   = (String)request.getAttribute("progress");         // 자기진도율

    ArrayList listall   = (ArrayList)request.getAttribute("selectListAll");    // 젠체공지리스트
    ArrayList list      = (ArrayList)request.getAttribute("selectList");       // 공지리스트

    String v_classname1 = "";
    String v_classname2 = "";

    String s_userid     = box.getSession("userid");
    String v_oid = "";
    String v_lesson = "";
    String v_corelessonlocation = "";
    String v_starting = "";
    String v_whole_starting = "";
    //DataBox dbox        =(DataBox)request.getAttribute("mainList");

    SCOBean sco = new SCOBean();

    String v_ispreview = box.getString("p_ispreview"); //스콤 맛보기 여부

    ArrayList list2 ;

    if (v_ispreview.equals("Y")) {
        list2 = sco.BookmarkInfoPreview(v_subj, v_year, v_subjseq, s_userid);
    } else {
        list2 = sco.BookmarkInfo(v_subj, v_year, v_subjseq, s_userid);
    }

    String  s_scorealpath = conf.getProperty("dir.scorealpath");

    for(i = 0; i < list2.size(); i++) {
        SCOData data= (SCOData)list2.get(i);
        v_oid = data.getOid();
        v_lesson = data.getLesson();

        v_starting = data.getStarting();
        v_whole_starting = s_scorealpath + v_starting;  
    }

    SubjGongAdminBean control = new SubjGongAdminBean();
    
    int v_controlstudy = control.allowStudy(box);
    
//  box.setSession("subj",v_subj);
//  box.setSession("subjseq",v_subjseq);
//  box.setSession("year",v_year);

    session.setAttribute("subjseq", v_subjseq);
    session.setAttribute("user_id",s_userid );
    session.setAttribute("subj",v_subj );
    session.setAttribute("year", v_year);
    
    String t_subjseq = (String)session.getAttribute("subjseq");
    String t_user_id = (String)session.getAttribute("user_id");
    String t_subj = (String)session.getAttribute("subj");
    String t_year = (String)session.getAttribute("year");

//  out.println("t_subjseq="+t_subjseq);
//  out.println("t_user_id="+t_user_id);
//  out.println("t_subj="+t_subj);
//  out.println("t_year="+t_year);

    EduStartBean bean = new EduStartBean();

%>
<html>
<head>
<title>::: 공지사항 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 상세보기
    function view(seq) {
        document.form1.action = "/servlet/controller.study.SubjGongStudyServlet";
        document.form1.p_seq.value      = seq;
        document.form1.p_process.value = "yesview";
        document.form1.submit();
    }

    // 설문 팝업 
    function goSulpaper() {
        //document.form2.submit();
        var subj = document.form1.p_subj.value;        
        var year = document.form1.p_year.value;        
        var subjseq = document.form1.p_subjseq.value;
        var url = "/servlet/controller.study.StudySulmunServlet?&p_subj="+subj+"&p_year="+year+"&p_subjseq="+subjseq;
        window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width=800,height=600 ").focus();        
    }
    
    // 고용보험안내
    function notice_goyong() {
<% if(v_ispreview.equals("Y") || v_year.equals("2000")) {   %>
		alert("사용할 수 없는 메뉴입니다.");
<% } else {                        %>
        window.self.name = "winNoticeView";
        window.open("", "openWinNotice", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 580, height = 550, top=0, left=0");
        document.form1.target = "openWinNotice";
        document.form1.action = "/servlet/controller.study.SubjGongStudyServlet";
        document.form1.p_process.value = 'NoticeGoyong';
        document.form1.submit();    
<% }                              %>
    }
    
    //나의진도보기 팝업
    function upWin(){
<% if(v_ispreview.equals("Y") || v_year.equals("2000")) {   %>
		alert("사용할 수 없는 메뉴입니다.");
<% } else {                        %>
        var url = "/servlet/controller.contents.EduStart?p_process=eduList";
        window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width=800,height=600 ").focus();
<% }                              %>
    }
    
    //학습제약 알림
    function controlStudy(){
        alert("지금은 학습가능 시간이 아닙니다.\n\n*학습시간 안내\n\n *월~금: ~19:00 까지 학습가능\n\n *토요일,일요일,공휴일:학습불가");
        return; 
    }


	
// 정보동의
function openInfomation() {
    window.open('/learn/user/homepage/zu_student_notice.jsp?p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>','openTong','width=550,height=630,scrollbars=no,resizable=no');
}
<%

   // 처음로긴시 : 0, 동의후 : 1
       int isOk = bean.firstCheck(box);
       if (isOk == 0) {
%>
           openInfomation();
<%
   }
%>
//-->
</SCRIPT>

</head>

<body topmargin="0" leftmargin="0">
<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="783" height="100%">
  <tr> 
    <td> 
      <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
        <tr> 
          <td align="center" valign="top" class="body_color"> 


<form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_subj"    value="<%=v_subj%>">
    <input type="hidden" name="p_year"    value="<%=v_year%>">
    <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
    <input type="hidden" name="p_seq"     value="">

<!----------------- 타이틀 시작 ---------------->
  <table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
    <tr> 
      <td><img src="/images/user/study/gongi_title.gif"></td>
      <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
      <td><img src="/images/user/study/gongi_tail.gif"></td>
    </tr>
  </table>
<!----------------- 타이틀 끝 ---------------->
<br>

<!----------------------점선 시작---------------------------->
<!--<table cellspacing="0" cellpadding="0"  border="0" class="open_board_table_out1" align="center">
    <tr>
      <td class=study_dotline align="right"><a href="javascript:notice_goyong()"><img src="/images/user/button/btn_goyoung.gif" border="0"></a>
      <a href="javascript:upWin()"><img src="/images/user/button/mystudy_btn.gif" border="0"></td>
    </tr>
</table>-->
  <!---------------------점선 끝----------------------------->
  
<!----------------- 공지사항 리스트 시작 ---------------->
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
  <tr> 
    <td class="board_color_line"></td>
  </tr>
</table>
<table cellspacing="0" cellpadding="0" class="open_board_table_out2" align="center">
  <tr> 
    <td> 
      <table cellspacing="1" cellpadding="3" class="table2">
        <tr> 
          <td class="board_title_bg1" width="5%">번호</td>
          <td class="board_title_bg1">제 목</td>
          <td class="board_title_bg1" width="13%">등록일</td>
        </tr>
<%
            // 전체
            for(i = 0; i < listall.size(); i++) {
                SubjGongData data= (SubjGongData)listall.get(i);
                v_seq       = data.getSeq();
                v_typesnm   = data.getTypesnm();
                v_title     = data.getTitle();
                v_addate    = data.getAddate();
%>
        <tr> 
          <td class="board_text2">전체</td>
          <td align=left class="board_text4"><a href="javascript:view('<%= v_seq%>')"><%= v_title%></a></td>
          <td class="board_text2"><%= FormatDate.getFormatDate(v_addate,"yyyy/MM/dd") %></td>
        </tr>
<%
            }
                    
            v_classname1 = "board_text1";
            v_classname2 = "board_text3";

            for(i = 0; i < list.size(); i++) {
                SubjGongData data= (SubjGongData)list.get(i);

                v_seq       = data.getSeq();
                v_typesnm   = data.getTypesnm();
                v_title     = data.getTitle();
                v_addate    = data.getAddate();

                if ((i % 2) == 0) {
                    v_classname1 = "board_text1";
                    v_classname2 = "board_text3";
                } else {
                    v_classname1 = "board_text2";
                    v_classname2 = "board_text4";
                }

%>


        <tr> 
          <td class="<%=v_classname1%>"><%= list.size() - i %></td>
          <td class="<%=v_classname2%>"><a href="javascript:view('<%= v_seq%>')"><%= v_title%></a></td>
          <td class="<%=v_classname1%>"><%= FormatDate.getFormatDate(v_addate,"yyyy/MM/dd") %></td>
        </tr>

<%
            }

          if(v_process.equals("select") && i == 0){ %>
              <tr>
                <td class="<%=v_classname1%>" height="50" colspan="4">등록된 내용이 없습니다</td>
              </tr>
<%  } %>


      </table>
    </td>
  </tr>
</table>

            <!----------------- 게시판 리스트 끝 ---------------->
<br>
            <!----------------- 작성, 닫기 버튼 시작 ---------------->
            <table cellspacing="0" cellpadding="0"  class="open_board_table_out1">
              <tr> 
                <td width=8% align=right><a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif"  border="0"></a></td>
              </tr>
            </table>
            <!----------------- 작성, 닫기 버튼 끝 ----------------->
</form>

            <br>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<!----------------- 공지사항 리스트 끝---------------->

<%@ include file = "/learn/library/getJspName.jsp" %>


</body>
</html>
