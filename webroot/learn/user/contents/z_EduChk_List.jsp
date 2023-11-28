<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/KoccaPageUtilTaglib" prefix="pu" %>
<%@ page isELIgnored="false" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  p_subj, p_year, p_subjseq, p_userid;
    String  v_edutime = "";  // 학습시간
    String  v_edudate = "";  // 최근학습일
    int     v_educount= 0;   // 강의실접근횟수

    // 추가
    DecimalFormat df = new DecimalFormat("###.00");
    double  v_totscore =0.0;
    String v_isgraduated = "-";


    String  p_lesson_link = box.getString("p_lesson_link");
    String  p_isFromLMS = box.getString("p_isFromLMS");
    if (p_isFromLMS.equals("Y")){
        p_subj      = box.getString("p_subj");
        p_year      = box.getString("p_year");
        p_subjseq   = box.getString("p_subjseq");
        p_userid    = box.getString("p_userid");
    }else{
        p_subj      = box.getSession("s_subj");
        p_year      = box.getSession("s_year");
        p_subjseq   = box.getSession("s_subjseq");
        p_userid    = box.getSession("userid");
    }


    p_subj      = box.getStringDefault("p_subj"    , box.getSession("s_subj"));
    p_year      = box.getStringDefault("p_year"    , box.getSession("s_year"));
    p_subjseq   = box.getStringDefault("p_subjseq" , box.getSession("s_subjseq"));
    p_userid    = box.getStringDefault("p_userid"  , box.getSession("userid"));


    String p_subjnm = box.getString("p_subjnm");  // 과정명
    String p_iurl   = box.getString("p_iurl");    // 외주url(외주:'1')
    String v_iseduend="";
    v_iseduend= box.getString("p_iseduend");    // 복습(복습:'Y')

    if(p_iurl.equals("")) p_iurl="0";
    String v_promotion  = (String)request.getAttribute("promotion");       //권장진도율
    String v_progress   = (String)request.getAttribute("progress");       // 자기진도율

    MasterFormData  md  = (MasterFormData)request.getAttribute("MasterFormData");
    ArrayList   brlist  = null;
    //if (md.getIsmfbranch().equals("Y")){        //분기식 과정이면 분기선택 링크 생성.
    //    brlist = (ArrayList)request.getAttribute("BranchList");
    //}

    ArrayList  list = (ArrayList)request.getAttribute("EduList");
    EduListData x = null;

    ArrayList  list2 = (ArrayList)request.getAttribute("EduTime");  // 학습시간
    EduListData edutime = null;
    if(list2.size()>0) {
        edutime = (EduListData)list2.get(0);
        v_edutime = edutime.getTotal_time();
        v_edudate = edutime.getFirst_edu();
        v_educount= edutime.getLesson_count();
    }

    EduScoreData    sd = (EduScoreData)request.getAttribute("EduScore");
    EduScoreDataSub sds;

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="euc-kr">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>한국콘텐츠아카데미</title>

    <!-- Bootstrap / Jquery UI -->
    <link href="/common/js/jquery-ui-1.12.1/jquery-ui.min.css" rel="stylesheet">
    <link href="/common/js/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="/common/js/jquery-3.3.1.min.js"></script>
    <script src="/common/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <script src="/common/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>


    <!-- Custom CSS -->
    <link href="/common/css/custom.css" rel="stylesheet">
    <link href="/common/css/header.css" rel="stylesheet">
    <link href="/common/css/footer.css" rel="stylesheet">

    <!-- Custom JS -->
    <script src="/common/js/custom.js"></script>

    <script type="text/javascript" src="/script/portal/common.js"></script>

    <script type="text/javascript">
        var v_subj    = '<%=p_subj%>';
        var v_year    = '<%=p_year%>';
        var v_subjseq = '<%=p_subjseq%>';
        function init() {
            this.moveTo(0,0);
            this.resizeTo(800,900);
        }

        function whenSelect(xx){
            opener.parent.etop.jumpTo(xx);
            window.close();
        }
        function whenBr(lesson,branch){
            opener.parent.etop.whenBranchFromProcessWin(lesson,branch);
            window.close();
        }
        function whenExamSelect(ptype,lesson){
            //var url = '/servlet/controller.contents.EduStart?p_process=exam&p_lesson='+lesson+'&p_type='+ptype;
            //opener.parent.ebody.location = url;
            opener.parent.etop.goExam(lesson,ptype);
            window.close();
        }
        function goReport(urls){
            window.open(urls,"Reports","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=700,height=500").focus();
        }

        function PopSulmun() {
            upWin('/servlet/controller.study.StudySulmunServlet?&p_subj='+v_subj+'&p_year='+v_year+'&p_subjseq='+v_subjseq,800,600);
        }
        function upWin(url,w,h) {
            window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width="+w+",height="+h+" ").focus();
        }
    </script>
</head>
<body id="pop_study" onLoad='init();'>
<%=p_isFromLMS%>
<section>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="subContainer_pop">
                        <div class="sub_section">
<%--                            <div class="sub_contents_header">--%>
<%--                                <span>과정 상세정보</span>--%>
<%--                            </div>--%>
                            <div class="sub_contents_body">
                                <div class="sub_info_body">
                                    <div class="sub_course_alert_box">
                                        <p><%= GetCodenm.get_subjnm(p_subj) %></p>
                                    </div>
                                    <div class="sub_course_view_wrap" style="margin-bottom:50px;">
                                        <ul class="myCourseDetail">
                                            <li><span>최근학습일</span> : <%=FormatDate.getFormatDate(v_edudate,"yyyy.MM.dd") %></li>
                                            <li><span>학습횟수</span> : <%=v_educount %>회</li>
                                            <li><span>남은 교육기간</span> : <%=FormatDate.getDate("yyyy.MM.dd") %> ~ <%= FormatDate.getFormatDate(sd.getEduend(),"yyyy.MM.dd") %></li>
                                        </ul>
                                        <ul class="myCourseDetail">
                                            <li><span>교육기간</span> : <%=FormatDate.getFormatDate(sd.getEdustart(),"yyyy.MM.dd") %> ~ <%=FormatDate.getFormatDate(sd.getEduend(),"yyyy.MM.dd") %></li>
                                            <li><span>나의 진도율</span> : <span class="progress_bar blue"><%= sd.getTstep() %>%</span></li>
                                            <li><span>권장 진도율</span> : <span class="progress_bar gray"><%= v_promotion %>%</span></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="sub_boarder_body">
                                    <p class="sub_course_view_title">학습진행상황</p>
                                    <table class="td_align_center">
                                        <colgroup>
                                            <col width="15%">
                                            <col width="auto">
                                            <col width="auto">
                                            <col width="auto">
                                            <col width="auto">
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th></th>
                                            <th>이수 기준점수</th>
                                            <th>가중치(%)</th>
                                            <th>취득점수</th>
                                            <th>환산점수</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <%
                                            Hashtable 	ht;
                                            int			htsize		= 0;
                                            String		v_datatype	= "";

                                            if(sd!=null) {
                                                htsize=sd.eduScoreList.size();
                                                for (int i=0; i<htsize; i++) {
                                                    if((Hashtable)sd.getEduScoreList() != null){
                                                        ht  = (Hashtable)sd.getEduScoreList();
                                                        sds = (EduScoreDataSub)ht.get(String.valueOf(i));
                                                        v_datatype=get_datatypenm(sds.getDatatype());
                                        %>
                                        <tr>
                                            <td><%= get_datatypenm(sds.getDatatype()) %></td>
                                            <td><%= sds.getGradscore() %> 점</td>
                                            <td><%= sds.getWeight() %> %</td>
                                            <td><%= new java.text.DecimalFormat("###,###,###.#").format(sds.getScore()) %> 점</td>
                                            <td><%= new java.text.DecimalFormat("###,###,###.#").format(sds.getAvscore()) %> 점</td>
                                        </tr>
                                        <%
                                                    v_totscore=v_totscore+sds.getAvscore();
                                                }
                                            }

                                            //복습
                                            if(v_iseduend.equals("Y")) {
                                                if(sd.getIsgraduated().equals("Y")) v_isgraduated="수료";
                                                else v_isgraduated="미수료";
                                            } else {
                                                v_isgraduated="-";
                                            }
                                        %>
                                        <tr>
                                            <td>취득점수</td>
                                            <td><%= sd.getGradscore() %> 점</td>
                                            <td>-</td>
                                            <td>-</td>
                                            <td><%= new java.text.DecimalFormat("###,###,###.#").format(v_totscore) %> 점</td>
                                        </tr>
                                        <tr>
                                            <td>수료여부</td>
                                            <td colspan="4"><%=v_isgraduated%></td>
                                        </tr>
                                        <%		}       %>
                                        </tbody>
                                    </table>


                                </div>
                                <%
                                    if(p_iurl.equals("0")){
                                %>

                                <%
                                    ArrayList    examdata = (ArrayList)request.getAttribute("ExamData");
                                    ArrayList    examresultdata = (ArrayList)request.getAttribute("ExamResultData");
                                %>
                                <!--
                                    <div class="sub_info_body">
                                        <p class="sub_course_view_title">평가</p>
                                        <table>
                                            <colgroup>
                                                <col width="15%">
                                                <col width="auto">
                                                <col width="15%">
                                                <col width="auto">
                                            </colgroup>
                                            <tbody>
                                                <tr>
                                                    <th>최종평가</th>
                                                    <td><%=examdata.get(2) %> 개중 <%=examresultdata.get(2) %> 개 응시</td>
                                                    <th>중간평가</th>
                                                    <td><%=examdata.get(0) %> 개중 <%=examresultdata.get(0) %> 개 응시</td>
                                                </tr>
                                                <tr>
                                                    <th>형성평가</th>
                                                    <td><%= examdata.get(1) %> 개중 <%= examresultdata.get(1) %> 개 응시</td>
                                                    <th>리포트</th>
                                                    <td><%= box.getString("p_report") %> 개중 <%= box.getString("p_reportdata") %> 개 제출</td>
                                                </tr>
                                                <tr>
                                                    <th>사전설문</th>
                                                    <td>
<%
	if(box.getString("p_ispaper2").equals("0")){
%>
														설문지 없음
<%
	} else {
		if(box.getString("p_suldata").equals("0")){
%>
														<a href="javascript:PopSulmun();" class="btn_record">응시하기</a>
<%
		} else if (box.getString("p_suldata").equals("1")) {
%>
														응시완료
<%
		}
	}
%>
                                                    </td>
                                                    <th>사후설문</th>
                                                    <td>
<%
	if(box.getString("p_ispaper").equals("0")){
%>
														설문지 없음
<%
	} else {
		if(box.getString("p_contentsdata").equals("0")){
%>
														<a href="javascript:PopSulmun();" class="btn_record">응시하기</a>
<%
		}else if(box.getString("p_contentsdata").equals("1")){%>
														<span>응시완료</span>
<%
		}
	}
%>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
-->
                                <div class="sub_boarder_body">
                                    <p class="sub_course_view_title" style="margin-top:50px;">설문</p>
                                    <table>
                                        <colgroup>
                                            <col width="20%">
                                            <col width="auto">
                                            <%--                                                 <col width="auto"> --%>
                                            <%--                                                 <col width="15%"> --%>
                                            <col width="20%">
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th>요소</th>
                                            <th>구분</th>
                                            <!--                                                     <th>응시시간</th> -->
                                            <!--                                                     <th>제출일</th> -->
                                            <th>참여여부</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <%
                                            ArrayList    ExamList1 = (ArrayList)request.getAttribute("ExamUserList");
                                            ArrayList    ExamList2 = (ArrayList)request.getAttribute("ExamUserResultList");
                                            int totCnt = 0;
                                            int rowCnt = ExamList1.size();

                                            for (int i=0; i<ExamList1.size(); i++) {
                                                DataBox dbox = (DataBox)ExamList1.get(i);
                                        %>
                                        <!--
												<tr>
<%			if (i==0 && rowCnt>1) {  %>
													<td rowspan="<%= rowCnt %>"><a href="javascript:upWin('/servlet/controller.study.StudyExamServlet?p_subj=<%=p_subj%>&p_year=<%=p_year%>&p_subjseq=<%=p_subjseq%>',800,600)"><span>시험</span></a></td>
<%			}else if (rowCnt==1) {  %>
													<td><a href="javascript:upWin('/servlet/controller.study.StudyExamServlet?p_subj=<%=p_subj%>&p_year=<%=p_year%>&p_subjseq=<%=p_subjseq%>',800,600)"><span>시험</span></a></td>
<%			}   %>
													<td><%= dbox.getString("d_examtypenm") %></td>
													<td><%= FormatDate.getFormatDate(dbox.getString("d_startdt"),"yyyy/MM/dd HH:mm") %> ~ <%= FormatDate.getFormatDate(dbox.getString("d_enddt"),"yyyy/MM/dd HH:mm") %></td>
													<td><%= FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyy/MM/dd HH:mm") %></td>
													<td class="end"><%= dbox.getString("d_score") %></td>
												</tr>
 -->
                                        <%
                                            }
                                            totCnt += rowCnt;

                                            ArrayList    ProjectList = (ArrayList)request.getAttribute("ProjectList");
                                            rowCnt = ProjectList.size();
                                            for (int i=0; i<ProjectList.size(); i++) {
                                                ProjectData data  = (ProjectData)ProjectList.get(i);
                                                String v_title  = data.getTitle();
                                                String v_expiredate        = data.getExpiredate();
                                                String v_deadlinesdate         = data.getDeadlinesdate();
                                                String v_deadlineedate       = data.getDeadlineedate();
                                                String v_ldate       = data.getLdate();

                                                if (v_deadlineedate.equals("")) {
                                                    v_deadlineedate = v_expiredate;
                                                }

                                                int v_score        = data.getScore2();
                                        %>
                                        <!--
                                                <tr>
<%      	if (i==0 && rowCnt>1) {  %>
												<td rowspan="<%= rowCnt %>"><a href="javascript:upWin('/servlet/controller.study.ProjectServlet?p_process=choicePage&p_subj=<%=p_subj%>&p_year=<%=p_year%>&p_subjseq=<%=p_subjseq%>',800,600)"><span>리포트</span></a></td>
<%      	}else if (rowCnt==1) {  %>
												<td><a href="javascript:upWin('/servlet/controller.study.ProjectServlet?p_process=choicePage&p_subj=<%=p_subj%>&p_year=<%=p_year%>&p_subjseq=<%=p_subjseq%>',800,600)"><span>리포트</span></a></td>
<%      	}   %>
												<td><%= v_title %></td>
												<td><%= FormatDate.getFormatDate(v_deadlinesdate,"yyyy/MM/dd") %> ~ <%= FormatDate.getFormatDate(v_deadlineedate,"yyyy/MM/dd") %></td>
												<td><%= FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd HH:mm") %></td>
												<td class="end"><%= v_score %></td>
												</tr>
-->
                                        <%
                                            }
                                            totCnt += rowCnt;

                                            ArrayList  TopicList = (ArrayList)request.getAttribute("TopicList");
                                            rowCnt = TopicList.size();
                                            for (int i=0; i<TopicList.size(); i++) {
                                                ToronData data  = (ToronData)TopicList.get(i);

                                                String v_title     = data.getTitle();
                                                String v_started   = data.getStarted();
                                                String v_ended     = data.getEnded();
                                                String v_addate    = data.getLdate();

                                                int v_score       = data.getPosition();
                                                String v_scoremsg = "참여";

                                                if(v_score == 0){v_scoremsg="미참여";}
                                        %>
                                        <!--
												<tr>
<%      if (i==0 && rowCnt>1) {  %>
													<td rowspan="<%= rowCnt %>"><a href="javascript:upWin('/servlet/controller.study.ToronServlet?p_subj=<%=p_subj%>&p_year=<%=p_year%>&p_subjseq=<%=p_subjseq%>',800,600)"><span>토론</span></a></td>
<%      }else if (rowCnt==1) {  %>
													<td><a href="javascript:upWin('/servlet/controller.study.ToronServlet?p_subj=<%=p_subj%>&p_year=<%=p_year%>&p_subjseq=<%=p_subjseq%>',800,600)"><span>토론</span></a></td>
<%      }   %>
													<td><%= v_title %></td>
													<td><%= FormatDate.getFormatDate(v_started,"yyyy/MM/dd HH") %>시 ~ <%= FormatDate.getFormatDate(v_ended,"yyyy/MM/dd HH") %>시</td>
													<td><%= FormatDate.getFormatDate(v_addate,"yyyy/MM/dd") %></td>
													<td class="end"><%= v_scoremsg %></td>
												</tr>
-->
                                        <%
                                            }
                                            totCnt += rowCnt;

                                            ArrayList  SulmunList = (ArrayList)request.getAttribute("SulmunSubjUserList");
                                            rowCnt = SulmunList.size();
                                            for (int i=0; i<SulmunList.size(); i++) {
                                                DataBox dbox1 = (DataBox)SulmunList.get(i);
                                        %>
                                        <tr>
                                            <%      	if (i==0 && rowCnt>1) {  %>
                                            <td rowspan="<%= rowCnt %>"><a href="javascript:upWin('/servlet/controller.study.StudySulmunServlet?p_subj=<%=p_subj%>&p_year=<%=p_year%>&p_subjseq=<%=p_subjseq%>',800,600)"><span>과정설문</span></a></td>
                                            <%      	} else if (rowCnt==1) {  %>
                                            <td><a href="javascript:upWin('/servlet/controller.study.StudySulmunServlet?p_subj=<%=p_subj%>&p_year=<%=p_year%>&p_subjseq=<%=p_subjseq%>',800,600)" style="text-align: center;"><span>과정설문</span></a></td>
                                            <%      	}   %>
                                            <td><%= dbox1.getString("d_sulpapernm") %></td>
                                            <td>
                                                <%      	if (dbox1.getString("d_eachcnt").equals("0") && (Double.parseDouble(v_progress)>=Double.parseDouble(dbox1.getString("d_progress")))){  %>
                                                <span>응시가능</span>
                                                <%      	} else if (dbox1.getString("d_eachcnt").equals("0") && (Double.parseDouble(v_progress)<Double.parseDouble(dbox1.getString("d_progress")))){  %>
                                                <span>진도미달</span>
                                                <%      	} else if (dbox1.getString("d_eachcnt").equals("1")){  %>
                                                <span>응시완료</span>
                                                <%      	}   %>
                                            </td>
                                        </tr>
                                        <%
                                            }
                                            totCnt += rowCnt;

                                            if (totCnt==0) {
                                        %>
                                        <tr>
                                            <td colspan="5">성적요소 데이터가 없습니다.</td>
                                        </tr>
                                        <%
                                            }
                                        %>
                                        </tbody>
                                    </table>

                                    <p class="sub_course_view_title" style="margin-top:50px;">차시정보</p>
                                    <table class="td_align_left1">
                                        <colgroup>
                                            <col width="auto">
                                            <col width="15%">
                                            <col width="20%">
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th>차시</th>
                                            <th>수강</th>
                                            <th>최종학습일</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <%
                                            String v_module="", v_brlink="", v_lesssonlink="";
                                            String today = FormatDate.getDate("yyyyMMdd");

                                            MfBranchData    bData;
                                            int n = 0;
                                            for (int i=0; i<list.size(); i++) {
                                                x = (EduListData)list.get(i);
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
                                                if(!v_module.equals(x.getModule())){
                                                    v_module = x.getModule();
                                                }

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
                                                    v_lesssonlink   = "<a href=\"javascript:whenSelect('" + x.getLesson() + "')\">"+ x.getLesson() + "-" + x.getSdesc() + "</a>";

                                                    if (Integer.parseInt(x.getTodate()) != 0) {
                                                        if ((Integer.parseInt(today) >= Integer.parseInt(fromdate)) && (Integer.parseInt(today) <= Integer.parseInt(todate))) {
                                                            if (p_lesson_link.equals("Y")) {
                                                                v_lesssonlink = "<a href=\"javascript:whenSelect('" + x.getLesson() + "')\">"+ x.getLesson() + "-" + x.getSdesc() + "</a>";
                                                            } else {
                                                                v_lesssonlink = x.getLesson() + "-" + x.getSdesc();
                                                            }
                                                        } else {
                                                            v_lesssonlink = x.getLesson() + "-" + x.getSdesc();
                                                        }
                                                    }
                                            %>
                                            <td><%= x.getLesson() + "-" + x.getSdesc() %></td>
                                            <td><%= get_isEducatedTxt(x.getIsEducated()) %></td>
                                            <td><%= FormatDate.getFormatDate(x.getLdate(),"yyyy/MM/dd-HH:mm") %></td>
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
                                </div>
                                <%
                                    }
                                %>
                            </div>
                            <!--                                 <div class="popup_btnBox"> -->
                            <!--                                     <a href="#" class="btn_courseCancel">닫기</a> -->
                            <!--                                 </div> -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
<style>
    @media all and (max-width: 768px){
        .sub_boarder_body table tr td{padding: 12px 0;}

    }
</style>
</html>
<%!
    public String   get_isEducatedTxt(String val){
        if(val.equals("Y"))
            return  "<img src=\"/images/portal/ico/ico_yes.gif\" alt=\"\" />";
        else if(val.equals("P"))
            return "";
        else
            return  "<img src=\"/images/portal/ico/ico_no.gif\" alt=\"\" />";
    }

    public String   get_examtypenm(String val){
        if(val.equals("M"))         return  "중간 평가";
        else if(val.equals("T"))    return  "최종 평가";
        else                        return  "QUIZ";
    }
    public String   get_datatypenm(String val){
        if      (val.equals("STEP"  ))      return  "진도";
        else if (val.equals("MTEST" ))      return  "중간 평가";
        else if (val.equals("FTEST" ))      return  "최종 평가";
        else if (val.equals("HTEST" ))      return  "형성 평가";
        else if (val.equals("REPORT"))      return  "Report";
        else if (val.equals("ACT"   ))      return  "Activity";
        else if (val.equals("ETC1"  ))      return  "참여도";
        else if (val.equals("ETC2"  ))      return  "기타";

        return " ";
    }
    public String   get_reason(String val){
        if  (val.equals("Y")){
            return "축하합니다. 수료기준을 통과하셨습니다";
        }else{
            return "수료기준 취득점수/진도율에 미달하여 미수료상태입니다";
        }
    }
%>
