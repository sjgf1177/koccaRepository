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

    ArrayList list1     = null;

    list1 = (ArrayList)request.getAttribute("EducationSubjectList1");  //과정설문

    box.put("title_str", "설문 - 온라인 강의실 - 개인메뉴");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--

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

function ReloadPage(p_action, tab) {
  document.form1.p_process.value = 'SulmunNew';
  document.form1.p_action.value  = p_action;
  document.form1.action = "/servlet/controller.research.SulmunSubjUserServlet";
  document.form1.submit();
}

function change_sul(val){
    if(val == '1'){
        document.form1.p_process.value = 'SulmunNew';
    }else{
        document.form1.p_process.value = 'SulmunGen';
    }

    document.form1.action = '/servlet/controller.research.SulmunSubjUserServlet';
    document.form1.submit();
}
//-->
</script>
<!-- 스크립트영역종료 -->
<!-- container-new 시작 //-->
<div id="container-new">

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

    <div id="mainMenu">
        <ul class="location">
            <li>개인메뉴</li>
            <li>
                온라인 강의실 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" class="active">온라인 강의실</a></li>
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage" >현장교육 강의실</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE" >내가작성한 글</a></li>
                    <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage">상담내역</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">정보관리</a></li>
                    
                    <!-- 2015-01-12 개편 변경
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" >나의 강의실</a></li>
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" >찜목록</a></li>
                    <li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunNew" class="active">설문</a></li>
                    <li><a href="#" >과정후기</a></li>
                    //-->
                </ul>
            </li>
        </ul>

        <!-- 서브메인용 -->
        <div class="introCategory">
            <ul>
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" >나의 강의실</a></li>
                <li><a href="/servlet/controller.study.FavorOpenClassServlet?p_process=favorOpenClassList" >찜목록</a></li>
                <li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunNew" ><strong>설문</strong></a></li>
                <li><a href="/servlet/controller.study.ProposeReviewServlet?p_process=selectReviewList" >과정후기</a></li>
            </ul>
        </div>
        <!-- //서브메인용 -->
        <!-- //온라인 교육 -->
    </div>


    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">
        <h2 class="subTit">설문<!--<img src="/images/2013/online_edu/sulmun_title.gif" alt="설문" />//--></h2>

        <div id="subCont" class="myClass3">
            <!--2015-01-09 개편삭제
            <ul class="tabCus">
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><img src="/images/2013/online_edu/myclass_tab_1_off.gif" alt="나의 강의실" /></a></li>
                <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage"><img src="/images/2013/online_edu/myclass_tab_2_off.gif" alt="상담내역" /></a></li>
                <li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunNew"><img src="/images/2013/online_edu/myclass_tab_3_on.gif" alt="설문" /></a></li>
                <li><a href="/servlet/controller.study.FavorOpenClassServlet?p_process=favorOpenClassList"><img src="/images/2013/online_edu/myclass_tab_4_off.gif" alt="찜한강좌" /></a></li>
            </ul>
            //-->
            <h3 class="blind">설문 현재위치</h3>

            <div class="board2">
                <table summary="글번호, 분류, 제목, 과정, 작성자, 등록일 조회로 구성되어있습니다.">
                    <colgroup>
                        <col width="5%"/>
                        <col width="8%"/>
                        <col width="*"/>
                        <col width="19%"/>
                        <col width="28%"/>
                        <col width="6%"/>
                        <col width="8%"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">
                            <select title="분류" onchange="change_sul(this.value);">
                                <option selected value='1'>과정</option>
                                <option value='2'>일반</option>
                            </select>
                        </th>
                        <th scope="col">과정명</th>
                        <th scope="col">과정기간</th>
                        <th scope="col">설문주제</th>
                        <th scope="col">진도율</th>
                        <th scope="col">비고</th>
                    </tr>
                    </thead>
                    <tbody>
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

                            int v_sulpapernum      =  dbox1.getInt("d_sulpapernum");
                            String v_sulpapernm    =  dbox1.getString("d_sulpapernm");
                            int v_progress         =  dbox1.getInt("d_progress"); // 설문응시제한진도율
                            int v_eachcnt          =  dbox1.getInt("d_eachcnt");  // 응시여부

                            v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
                            v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");
                    %>
                    <tr>
                        <td class="first fs"><%= i+ 1 %></td>
                        <td class="fs">과정</td>
                        <td class="left"><%= v_subjnm %></td>
                        <td><%= v_edustart %> ~<%= v_eduend %></td>
                        <td class="left"><%= v_sulpapernm %></td>
                        <td><%= v_tstep %>/<%= v_progress %></td>
                        <td>
                            <%
                            if (v_eachcnt>0) { %>
                                완료
                            <% } else {
                                if(v_progress<=v_tstep) { %>
                                    <a href="javascript:contentsWright1('<%=v_subjnm%>', '<%=v_subj%>', '<%=v_grcode%>', '<%=v_year%>', '<%=v_subjseq%>', '<%=v_sulpapernum%>', '<%=v_sulpapernm%>', '', '','<%=v_sulnums%>')" tabindex="<%="2"+i+"1"%>">응시가능</a>
                                <% } else { %>
                                    <a href="javascript:alert('설문응시 제한진도율보다 진도율이 부족합니다.');"  tabindex="<%="2"+i+"1"%>">응시가능</a>
                            <%  }
                            }
                            %>
                        </td>
                    </tr>
                    <%} %>
                    <%  if(list1.size() == 0 ){%>
                    <tr>
                        <td colspan="7" class="first">설문내용이 없습니다.</td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
            <!-- //board2 -->
        </div>
        <!-- //#subCont -->
    </div>
    <!-- //#contents -->
    <!-- 컨텐츠 영역 끝 -->
    </form>
</div>
<!--  //container-new 끝 //-->

 <%@ include file="/learn/user/2013/portal/include/footer.html"%>