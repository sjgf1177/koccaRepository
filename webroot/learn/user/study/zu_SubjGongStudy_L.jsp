<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_contenttype   = box.getString("p_contenttype");
    String  v_mftype   = box.getString("p_mftype");

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

    session.setAttribute("subjseq", v_subjseq);
    session.setAttribute("user_id",s_userid );
    session.setAttribute("subj",v_subj );
    session.setAttribute("year", v_year);
    session.setAttribute("mftype", v_mftype);

    String t_subjseq = (String)session.getAttribute("subjseq");
    String t_user_id = (String)session.getAttribute("user_id");
    String t_subj = (String)session.getAttribute("subj");
    String t_year = (String)session.getAttribute("year");
    String t_contenttype = "";

    SubjectBean subjbean = new SubjectBean();
    SubjectData subjdata = null;
    subjdata = subjbean.SelectSubjectData(box);
    t_contenttype = subjdata.getContenttype();

    EduStartBean    bean        = new EduStartBean();
    EduScoreData    scoredata   = bean.SelectEduScore(box);

    ArrayList       edutimelist= null;

    String  v_edutime = "";  // 학습시간
    String  v_edudate = "";  // 최근학습일
    int     v_educount= 0;   // 강의실접근횟수
    edutimelist= bean.SelectEduTimeCountOBC(box);
    EduListData edutime = null;
    if(edutimelist.size()>0) {
        edutime = (EduListData)edutimelist.get(0);
        v_edutime = edutime.getTotal_time();
        v_edudate = edutime.getFirst_edu();
        v_educount= edutime.getLesson_count();
    }

    SulmunAllPaperBean sulbean = new SulmunAllPaperBean();
    int sulprogress = sulbean.getSulProgress(v_subj, v_year, v_subjseq);
    int sulresult = sulbean.getSulResult(v_subj, v_year, v_subjseq);
    String  v_today         = FormatDate.getDate("yyyyMMdd");
    String v_reviewend = null;

    try {
    if (scoredata == null) {
        v_reviewend = v_today;
    }
    else if (StringManager.toInt(v_today+"00") < StringManager.toInt(scoredata.getEduend())) {
        v_reviewend = scoredata.getEduend();
    }
    else {
        String v_reviewtype = scoredata.getReviewtype();
        int v_reviewdays = scoredata.getReviewdays();
        String v_reviewstart = scoredata.getEduend();
        if (v_reviewtype.equals("D")) {
            v_reviewend = getAddDay(v_reviewstart, v_reviewdays, "date");
        } else if (v_reviewtype.equals("W")) {
            v_reviewend = getAddDay(v_reviewstart, v_reviewdays* 7, "date");
        } else if (v_reviewtype.equals("M")) {
            v_reviewend = getAddDay(v_reviewstart, v_reviewdays, "month");
        } else if (v_reviewtype.equals("Y")) {
            v_reviewend = getAddDay(v_reviewstart, v_reviewdays, "year");
        }
    }
    }
    catch( Exception e) {
        System.out.println(e);
    }
%>
<%!
public String get_isEducatedTxt(String val){
    if(val.equals("Y")) {
        // return "<img src=\"/images/portal/ico/ico_yes.gif\" alt=\"학습완료\" />";
        return "학습완료";
    } else if(val.equals("P")) {
        return "";
    } else {
        // return "<img src=\"/images/portal/ico/ico_no.gif\" alt=\"미학습\" />";
        return "미학습";
    }
}
public String   getAddDay(String v_reviewstart, int v_reviewdays, String type) throws Exception{
    String result = FormatDate.getFormatDate(FormatDate.getDateAdd(FormatDate.getFormatDate(v_reviewstart,"yyyyMMdd"),"yyyyMMdd", type, v_reviewdays), "yyyy.MM.dd");
        return  result;
}
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<title>학습하기</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<style>
body { overflow-x:none; }
</style>
<script type="text/javascript" src="/script/portal/common.js"></script>
<script type="text/javascript" src="/script/portal/backspaceN.js"></script>
<script type="text/javascript" src="/js/jquery/1.7.2/jquery.min.js"></script>
<script>
$(document).ready(function(){
    $('td.ta_l').find('a').click(function(){
        parent.configs.location = "/learn/user/contents/overconfig.jsp";
    });

<%
    if ( v_subj.equals("CK15002") ) {
%>
    alert("※ 본 과정은 [한국양성평등교육진흥원] 콘텐츠를 지원받아 운영되는 과정으로 수료증이 발급되지 않습니다.\n\n 교육시간으로 인정되는 과정을 원하는 경우에는 [한국양성평등교육진흥원(egenedu.kigepe.or.kr/)]으로 신청하시기 바랍니다.");
<%
    }
%>
});
</script>
<script language="JavaScript">
function whenBr(lesson,branch){
    top.etop.whenBranchFromProcessWin(lesson,branch);
}

// 상세보기
function view(seq) {
    window.self.name = "SubjGongStudy";
    window.open("", "openGongList", "scrollbars=no,width=930,height=600,scrollbars=yes,resizable=yes'");
    document.form1.target = "openGongList"

    document.form1.action          = "/servlet/controller.study.SubjGongStudyServlet";
    document.form1.p_seq.value     = seq;
    document.form1.p_process.value = "selectView";
    document.form1.submit();

    document.form1.target = window.self.name;
}

<%
    if(!v_subjseq.equals("0000")){
%>
// 설문 팝업
function goSulpaper() {
    //document.form2.submit();
    var subj = document.form1.p_subj.value;
    var year = document.form1.p_year.value;
    var subjseq = document.form1.p_subjseq.value;
    var url = "/servlet/controller.study.StudySulmunServlet?&p_subj="+subj+"&p_year="+year+"&p_subjseq="+subjseq;
    window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width=800,height=600 ").focus();
}
<%
    }
%>

function upWin(url,w,h){
    window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width="+w+",height="+h+" ").focus();
}

window.onload = function() {
//  window.parent.opener.document.form1.lessonRepopup.value = "1";
    top.document.title = "<%= subjdata.getSubjnm() %> - 한국콘텐츠아카데미";
}
//-->
</script>
</head>
<%
    if(!v_subjseq.equals("0000")){
%>
<body id="pop_study" <%if(Double.parseDouble(v_progress)>=sulprogress && sulprogress > 0 && sulresult == 0){%>onLoad='goSulpaper()'<%}%>>
<%
    } else {
%>
<body id="pop_study"><!-- popup size : 890* -->
<%
    }
%>
<form name="form2" method="post" action="/servlet/controller.research.SulmunServlet">
  <input type="hidden" name="p_process"     value="UserResultListPage"/>
  <input type="hidden" name="p_f_gubun"     value="C"/>
</form>

<form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="<%=v_process%>"/>
    <input type="hidden" name="p_subj"    value="<%=v_subj%>"/>
    <input type="hidden" name="p_year"    value="<%=v_year%>"/>
    <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>"/>
    <input type="hidden" name="p_seq"     value=""/>
</form>

    <div id="pop_header">
        <img src="/images/portal/myclass/study/pop_h1_tit1.gif" alt="학습하기" />
        <!--  <div id="btn_close"><a href="javascript:top.window.close()"><img src="/images/portal/btn/btn_close.gif" alt="닫기" /></a></div>-->
    </div>
    <div id="pop_container">
        <div id="contentwrap">
            <div id="con_scoll">

                <div class="study_tltle"><div class="study_tltle_left"><div class="study_tltle_right" style="height:161px;">
                    <dl class="class_info">
                        <dt><span>과정명</span></dt>
                        <dd><%= subjdata.getSubjnm() %></dd>
                    </dl>
                    <% if (scoredata!=null) { %>
                    <dl class="class_info" >
                        <dt><span>교육기간</span></dt>
                        <dd class="class_day"><%= (scoredata==null) ? "" : FormatDate.getFormatDate(scoredata.getEdustart(),"yyyy.MM.dd") %> ~<%= (scoredata==null) ? "" : FormatDate.getFormatDate(scoredata.getEduend(),"yyyy.MM.dd") %></dd>
                    </dl>
                    <%}  %>
                    <div class="study_info" style="padding-top:12px;">
                        <table class="study_info_board">
                        <colgroup><col width="70px" /><col width="300px" /><col width="90px" /><col width="*" /></colgroup>
                        <tr>
                            <th>최근학습</th>
                            <td><%= FormatDate.getFormatDate(v_edudate,"yyyy.MM.dd") %></td>
                            <th>나의 진도율</th>
                            <td>
                                <table border="0" width="100%" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td><span class="rate_me" style="width:50px"><%= v_progress %>%</span></td>
                                        <td><span class="bar_yellow"><span style="width:<%= v_progress %>%;"></span></span></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <th>남은기간</th>
                            <td><% if (scoredata!=null) { %><%= FormatDate.getDate("yyyy.MM.dd") %>~<%= (scoredata==null) ? "" : FormatDate.getFormatDate(v_reviewend,"yyyy.MM.dd") %><%}    %></td>
                            <th>권장 진도율</th>
                            <td>
                                <table border="0" width="100%" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td><span class="rate" style="width:50px"><%= v_promotion %>%</span></td>
                                        <td><span class="bar_gray"><span  style="width:<%= v_promotion %>%;"></span></span></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
<!--                        <tr>-->
<!--                            <th>학습시간</th>-->
<!--                            <td><%= v_edutime %></td>-->
<!--                            <th>학습횟수</th>-->
<!--                            <td><%= v_educount %>회</td>-->
<!--                        </tr>-->
                        <%
                            ArrayList listCompleteBasic = (ArrayList)request.getAttribute("selectCompleteBasic");    //수료기준
                            if(listCompleteBasic.size()!=0)
                            {
                                out.print("<tr height='25px'>");
                                out.print("<th>이수기준</th>");
                                out.print("<td style='vertical-align:top;'>");
                                out.print("<table width='95%' border='1' cellpadding='0' cellspacing='0' bgcolor='#4b6aa7' bordercolor='#4b6aa7' borderColorDark='#FFFFFF'>");

                                DataBox dbox=(DataBox)listCompleteBasic.get(0);

                                //String [] isuList={"d_gradscore","d_gradstep","d_gradreport","d_gradftest","d_gradexam","d_gradhtest"};
                                String [] isuList={"d_gradscore","d_gradstep","d_gradexam","d_gradftest","d_gradhtest","d_gradreport"};
                                String [] isuHangul={"점수","진도율","중간평가","최종평가","형성평가","리포트"};

                                for(i=0;i<2;i++)
                                {
                                    out.print("<tr align='center'>");
                                    for(int j=0;j<isuList.length;j++)
                                    {
                                        String val=dbox.getString(isuList[j]);
                                        if(i==0)
                                        {
                                            out.print("<td height='20' bgcolor='#4b6aa7' style='color:#000000;'><font color='#FFFFFF'>");
                                            out.print(isuHangul[j]);
                                            out.print("</font>");
                                        }
                                        else
                                        {
                                            out.print("<td height='20' bgcolor='#cbd8fb' style='color:#000000;'>");
                                            out.print(val);
                                        }
                                        out.print("</td>");
                                    }
                                    out.print("</tr>");
                                }
                                out.print("</table>");
                                out.print("</td>");
                                out.print("<th>가중치(%)</th>");
                                out.print("<td style='vertical-align:top;'>");
                                out.print("<table border='1' cellpadding='0' cellspacing='0' bgcolor='#4b6aa7' bordercolor='#4b6aa7' borderColorDark='#FFFFFF'>");

                                dbox=(DataBox)listCompleteBasic.get(0);
                                String [] weight={"d_wstep","d_wmtest","d_wftest","d_whtest","d_wreport","d_wetc1"};
                                String [] weightHangul={"진도율","중간평가","최종평가","형성평가","리포트","참여도"};

                                for(i=0;i<2;i++)
                                {
                                    out.print("<tr align='center'>");
                                    for(int j=0;j<weight.length;j++)
                                    {

                                        if(i==0)
                                        {
                                            out.print("<td height='20' width='16%' bgcolor='#4b6aa7' style='color:#000000;'><font color='#FFFFFF'>");
                                            out.print(weightHangul[j]);
                                            out.print("</font>");
                                        }
                                        else
                                        {
                                            out.print("<td height='20' bgcolor='#cbd8fb' style='color:#000000;'>");
                                            out.print(dbox.getString(weight[j]));
                                        }
                                        out.print("</td>");
                                    }
                                    out.print("</tr>");
                                }
                                out.print("</table>");
                                out.print("</td>");
                                out.print("</tr>");
                        }%>
                    </table>
                    </div>
                </div></div></div>

                <div class="content">
                    <h3><img src="/images/portal/myclass/study/pop_stit1.gif" alt="공지사항" /></h3>
                    <table class="board_list" style="width:960px; margin-left:10px;">
                    <colgroup><col width="12%" /><col width="72%" /><col width="16%" /></colgroup>
                    <thead>
                    <tr class="gray">
                        <th><p>번호 </p></th>
                        <th><p>제목</p></th>
                        <th class="end"><p>등록일</p></th>
                    </tr>
                    </thead>
                    <tbody>
<%
            // 전체
            for(i = 0; i < listall.size(); i++) {
                SubjGongData data= (SubjGongData)listall.get(i);
                v_seq       = data.getSeq();
                v_typesnm   = data.getTypesnm();
                v_title     = data.getTitle();
                v_addate    = data.getAddate();
%>
                    <tr onmouseover="className='over'" onmouseout="className=' '">
                        <td class="num">전체</td>
                        <td class="ta_l"><a href="javascript:view('<%= v_seq %>')"><%= v_title %></a></td>
                        <td class="num end"><%= FormatDate.getFormatDate(v_addate,"yyyy.MM.dd") %></td>
                    </tr>
<%
            }

            for(i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);

                v_seq       = dbox.getInt("d_seq");
                v_typesnm   = dbox.getString("d_typesnm");
                v_title     = dbox.getString("d_title");
                v_addate    = dbox.getString("d_addate");
%>
                    <tr onmouseover="className='over'" onmouseout="className=' '">
                        <td class="num"><%= list.size() - i %></td>
                        <td class="ta_l"><a href="javascript:view('<%= v_seq %>')"><%= v_title %></a></td>
                        <td class="num end"><%= FormatDate.getFormatDate(v_addate,"yyyy.MM.dd") %></td>
                    </tr>
<%
            }
            if(v_process.equals("select") && i == 0){
%>
                    <tr>
                        <td height="50" colspan="3">등록된 내용이 없습니다</td>
                    </tr>
<%          } %>
                    </tbody>
                    </table>

<%
        ArrayList  TopicList = (ArrayList)request.getAttribute("TopicList");

        if(!v_mftype.equals("ND")) {   %>
            <% if (v_ispreview.equals("Y")) { %>
            <%  } else { %>
                    <ul class="study_pop_tab">
                        <li><a href="javascript:upWin('/servlet/controller.contents.EduStart?p_process=eduList&p_lesson_link=Y',800,600)"><img src="/images/portal/btn/btn_pop2_off.gif" alt="과정 상세정보" onmouseover="imgover(this);" onfocus="imgover(this);" onmouseout="imgout(this);" onblur="imgout(this);" /></a></li>
                        <li><a href="javascript:upWin('/servlet/controller.study.StudyBoardServlet?&p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>',920,650);"><img src="/images/portal/btn/btn_pop6_off.gif" alt="게시판" onmouseover="imgover(this);" onfocus="imgover(this);" onmouseout="imgout(this);" onblur="imgout(this);" /></a></li>
                        <li><a href="javascript:upWin('/servlet/controller.study.SubjQnaStudyServlet?p_process=SubjQnaFrame&p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>',930,600)"><img src="/images/portal/btn/btn_pop4_off.gif" alt="과정 질문방" onmouseover="imgover(this);" onfocus="imgover(this);" onmouseout="imgout(this);" onblur="imgout(this);" /></a></li>
                        <li><a href="javascript:upWin('/servlet/controller.study.ProjectServlet?p_process=choicePage&p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>',930,600)"><img src="/images/portal/btn/btn_pop8_off.gif" alt="레포트" onmouseover="imgover(this);" onfocus="imgover(this);" onmouseout="imgout(this);" onblur="imgout(this);" /></a></li>
                        <% if( TopicList.size() > 0 ) { %>
                        <li><a href="javascript:upWin('/servlet/controller.study.ToronServlet?p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>',800,600)"><img src="/images/portal/btn/btn_pop11_off.gif" alt="토론" onmouseover="imgover(this);" onfocus="imgover(this);" onmouseout="imgout(this);" onblur="imgout(this);" /></a></li>
                        <% } %>
                        <li><a href="javascript:upWin('/servlet/controller.study.StudyDataServlet?&p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>',930,600)"><img src="/images/portal/btn/btn_pop5_off.gif" alt="자료실" onmouseover="imgover(this);" onfocus="imgover(this);" onmouseout="imgout(this);" onblur="imgout(this);" /></a></li>
                        <li><a href="javascript:upWin('/servlet/controller.study.StudyExamServlet?&p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>',930,600)"><img src="/images/portal/btn/btn_pop7_off.gif" alt="평가" onmouseover="imgover(this);" onfocus="imgover(this);" onmouseout="imgout(this);" onblur="imgout(this);" /></a></li>


                        <li class="mg_rn"><a href="javascript:upWin('/servlet/controller.study.StudySulmunServlet?&p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>',930,600)"><img src="/images/portal/btn/btn_pop10_off.gif" alt="설문" onmouseover="imgover(this);" onfocus="imgover(this);" onmouseout="imgout(this);" onblur="imgout(this);" /></a></li>
                    </ul>
             <% } %>
<%  }   %>



<!-- 학습 목차 시작 -->
<%  if (t_contenttype.equals("X")) { %>
                    <h3 class="mg_t30"><img src="/images/portal/myclass/study/pop_stit2.gif" alt="차시정보" /></h3>
                    <table class="list" style="width:960px; margin-left:10px;">
                    <colgroup><col width="55%" /><col width="12%" /><col width="18%" /><col width="15%" /></colgroup>
                    <thead>
                    <tr>
                        <th>Object</th>
                        <th>학습여부</th>
                        <th class="end">최종학습일</th>
                        <%--<th class="end">총 학습시간</th>--%>
                    </tr>
                    </thead>
                    <tbody>
<%
        String v_module="", v_brlink="", v_lesssonlink="";

        list = (ArrayList)request.getAttribute("EduList");

        MfBranchData    bData;
        EduListData x = null;
        ArrayList   brlist  = null;
        String today = FormatDate.getDate("yyyyMMdd");

        boolean f_span = false;

        for (i=0; i<list.size(); i++) {
            x  = (EduListData)list.get(i); %>
                    <tr>
<%
            if(x.getRecordType().equals("STEP")){
                if(x.getIsbranch().equals("Y")){
                    v_brlink = "<br>";
                    for(int j=0;j<brlist.size();j++){
                        bData = (MfBranchData)brlist.get(j);
                        v_brlink=v_brlink + "<a href=\"javascript:whenBr('"+x.getLesson()+"','"+bData.getBranch()+"')\">"+bData.getSdesc()+"</a>&nbsp;&nbsp";
                    }
                }

                String fromdate = FormatDate.getRelativeDate(x.getEdustart(), Integer.parseInt(x.getFromdate()));
                String todate   = FormatDate.getRelativeDate(x.getEdustart(), Integer.parseInt(x.getTodate()));
                v_lesssonlink   = "<a href=\"javascript:top.etop.starting('" + x.getLesson() + "')\">"+ x.getOidnm() + "1</a>";

                if (Integer.parseInt(x.getTodate()) != 0 && !"".equals(fromdate) && !"".equals(todate)) {
                    if ((Integer.parseInt(today) >= Integer.parseInt(fromdate)) && (Integer.parseInt(today) <= Integer.parseInt(todate))) {
                        v_lesssonlink = "<a href=\"javascript:top.etop.starting('" + x.getLesson() + "')\">"+ x.getLesson() + "-" + x.getSdesc() + "2</a>";
                    } else {
                        v_lesssonlink = x.getLesson() + "-" + x.getSdesc();
                    }
                }
%>
                        <td class="ta_l"><%= v_lesssonlink %><%= v_brlink %></td>
<%              if ((x.getIsEducated()).equals("P")){ %>
                        <td></td>
                        <td class="end"></td>
                        <%--<td class="end"></td>--%>
<%              } else {%>
                        <td><%= get_isEducatedTxt(x.getIsEducated()) %></td>
                        <td class="end"><%= FormatDate.getFormatDate(x.getLdate(),"yyyy.MM.dd-HH:mm") %></td>
                        <%--<td class="end"><%= x.getTotal_time() %></td>--%>
<%              }
            }
%>
                    </tr>
<%      }         %>
                    </tbody>
                    </table>

<%  } else if (t_contenttype.equals("N") || t_contenttype.equals("M")) { %>
                    <h3 class="mg_t30"><img src="/images/portal/myclass/study/pop_stit2.gif" alt="차시정보" /></h3>
                    <table class="list" style="width:960px; margin-left:10px;">
                    <colgroup><col width="55%" /><col width="12%" /><col width="18%" /><col width="15%" /></colgroup>
                    <thead>
                    <tr>
                        <th>차시</th>
                        <th>학습여부</th>
                        <th class="end">최종학습일</th>
                        <%--<th class="end">총 학습시간</th>--%>
                    </tr>
                    </thead>
                    <tbody>
<%
        String v_module="", v_brlink="", v_lesssonlink="";

        list = (ArrayList)request.getAttribute("EduList");

        MfBranchData    bData;
        EduListData x = null;
        ArrayList   brlist  = null;
        String today = FormatDate.getDate("yyyyMMdd");

        int n = 0;

        for (i=0; i<list.size(); i++) {
            x  = (EduListData)list.get(i);

            int m=0;
            for(int k=n;k < list.size();k++) {
                EduListData y = (EduListData)list.get(k);
                if (y.getRecordType().equals("STEP")) {
                    if(y.getModule().equals(x.getModule()) ) {
                        m++;
                    } else {
                        n=k;
                        break;
                    }
                }
            }
            if(x.getRecordType().equals("STEP")){
%>
                    <tr>
<%
                if(x.getRecordType().equals("STEP")){
                    if(x.getIsbranch().equals("Y")){
                        v_brlink = "<br>";
                        for(int j=0;j<brlist.size();j++){
                            bData = (MfBranchData)brlist.get(j);
                            v_brlink=v_brlink + "<a href=\"javascript:whenBr('"+x.getLesson()+"','"+bData.getBranch()+"')\">"+bData.getSdesc()+"</a>&nbsp;&nbsp";
                        }
                    }

                    String fromdate = FormatDate.getRelativeDate(x.getEdustart(), Integer.parseInt(x.getFromdate()));
                    String todate   = FormatDate.getRelativeDate(x.getEdustart(), Integer.parseInt(x.getTodate()));

                    //if (v_subj.equals("CK12009") &&  (x.getLesson().equals("013") || x.getLesson().equals("014") || x.getLesson().equals("015") || x.getLesson().equals("011") || x.getLesson().equals("012") ) )  {    // HTJ 임시
                    //  v_lesssonlink   =  x.getLesson() + "-" + x.getSdesc() ;  // HTJ 임시
                    //} else {
                        v_lesssonlink   = "<a href=\"javascript:top.etop.starting('" + x.getLesson() + "')\">"+ x.getLesson() + "-" + x.getSdesc() + "</a>";
                    //}
                    if (Integer.parseInt(x.getTodate()) != 0 && !"".equals(fromdate) && !"".equals(todate)) {
                        if ((Integer.parseInt(today) >= Integer.parseInt(fromdate)) && (Integer.parseInt(today) <= Integer.parseInt(todate))) {
                            v_lesssonlink = "<a href=\"javascript:top.etop.starting('" + x.getLesson() + "')\">"+ x.getLesson() + "-" + x.getSdesc() + "</a>";
                        } else {
                            v_lesssonlink = x.getLesson() + "-" + x.getSdesc();
                        }
                    }
%>
                        <td class="ta_l"><%= v_lesssonlink %><%= v_brlink %></td>
                        <td><%= get_isEducatedTxt(x.getIsEducated()) %></td>
                        <td class="end"><%= FormatDate.getFormatDate(x.getLdate(),"yyyy/MM/dd-HH:mm") %></td>
                        <%--<td class="end"><%= x.getTotal_time() %></td>--%>
<%
                }
%>
                    </tr>
<%
            }
        }
%>
                    </tbody>
                    </table>
<%  } //학습목차 끝 %>
                </div>
            </div>
        </div>
    </div>
</body>
</html>