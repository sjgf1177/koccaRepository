<%
//**********************************************************
//  1. 제      목: 마이 액티비티 PAGE
//  2. 프로그램 명: zu_Activity_Study.jsp
//  3. 개      요: MY Activity - 학습과정별 접속통계
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정경진 2005. 7. 25
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  s_subj          = box.getString("subj");
    String  s_subjnm        = "";
    String  s_year          = box.getString("year");
    String  s_subjseq       = box.getString("subjseq");
    String  v_user_id       = box.getSession("userid");

    String  v_subj          = "";
    String  v_year          = "";
    String  v_subjnm        = "";
    String  v_subjseq       = "";
    String  v_eduurl        = "";
    String  v_cpsubj        = "";
    String  v_cpsubjseq     = "";
    String  v_isgraduated   = "";
    String  v_subjtarget    = "";
    String  v_isonoff_value = "";
    String  v_isonoff       = "";
    String  v_isoutsourcing = "";
    String  v_company       = "";

    int     v_total_time_int    = 0;
    int     v_total_minute_int  = 0;
    int     v_total_sec_int     = 0;
    String  v_total_time    = "";
    String  v_total_minute  = "";
    String  v_total_sec     = "";
    String  v_total_study_time = "";
    String  v_first_end     = "";
    String  v_lesson_count  = "0";
    String  v_tstep         = "0";

    String  v_menu          = "";
    String  v_menunm        = "";
    int     v_cnt           = 0;

    int     i               = 0;

    int     v_boardcnt      = 0;
    int     v_datacnt       = 0;
    int     v_qnacnt        = 0;
    int     v_examcnt       = 0;
    int     v_sulcnt        = 0;
    String  v_reportindate  = "";

    ArrayList list1     = null;
    ArrayList list2     = null;
    ArrayList list3     = null;
    ArrayList list4     = null;
    //DEFINED class&variable END

    list1 = (ArrayList)request.getAttribute("EducationSubjectList");   
    list2 = (ArrayList)request.getAttribute("selectActivity");   
    list3 = (ArrayList)request.getAttribute("selectLearningTime");   
    list4 = (ArrayList)request.getAttribute("selectBoard");   
    // 최근학습일
    String date  = (String)request.getAttribute("studyDate");   
    // 강의실 접근정보
    String count  = (String)request.getAttribute("studyCount") + "회";   
    // 강의실 접근정보
    String step  = (String)request.getAttribute("studyStep") + "%";   



    if(list3 != null && list3.size() > 0) {
        DataBox dbox3      = (DataBox)list3.get(0);
        v_total_time_int   = dbox3.getInt("d_total_time");
        v_total_minute_int = dbox3.getInt("d_total_minute");
        v_total_sec_int    = dbox3.getInt("d_total_sec");
        v_first_end        = dbox3.getString("d_first_end");
        v_lesson_count     = dbox3.getString("d_lesson_count");
        v_tstep            = dbox3.getString("d_tstep");
        
        v_total_time   = String.valueOf(v_total_time_int + 100).substring(1,3); 
        v_total_minute = String.valueOf(v_total_minute_int + 100).substring(1,3); 
        v_total_sec    = String.valueOf(v_total_sec_int + 100).substring(1,3); 
    }

    v_total_study_time = (v_total_time.equals("")?"00":v_total_time) + " : " + (v_total_minute.equals("")?"00":v_total_minute) + " : " + (v_total_sec.equals("")?"00":v_total_sec);

    if(list4 != null && list4.size() > 0) {

        DataBox dbox4   = (DataBox)list4.get(0);
        v_boardcnt          =  dbox4.getInt("d_boardcnt");
        v_datacnt           =  dbox4.getInt("d_datacnt");
        v_qnacnt            =  dbox4.getInt("d_qnacnt");
        v_examcnt           =  dbox4.getInt("d_examcnt");
        v_sulcnt            =  dbox4.getInt("d_sulcnt");
        v_reportindate      =  dbox4.getString("d_reportindate");
    }
    
    
	//과정 select box
    String s_selectString = "";
    String s_selectDefault = "";
    for(i = 0; i < list1.size(); i++) {
        DataBox dbox    = (DataBox)list1.get(i);

        v_subj          =  dbox.getString("d_subj");
        v_year          =  dbox.getString("d_year");
        v_subjnm        =  dbox.getString("d_subjnm");
        v_subjseq       =  dbox.getString("d_subjseq");

        s_selectDefault = "";
        if(s_subj.equals(v_subj) && s_year.equals(v_year) && s_subjseq.equals(v_subjseq)){

            v_eduurl        =  dbox.getString("d_eduurl");
            v_cpsubj        =  dbox.getString("d_cpsubj");
            v_cpsubjseq     =  dbox.getString("d_cpsubjseq");
            v_company       =  dbox.getString("d_company");
            v_isonoff       =  dbox.getString("d_isonoff");
            v_isoutsourcing =  dbox.getString("d_isoutsourcing");
            s_subjnm        =   v_subjnm;
            s_selectDefault = "selected";

            if (v_isoutsourcing.equals("Y")) {                 //위탁과정일때
                v_eduurl = v_eduurl+"?FIELD1="+v_user_id+"&FIELD2="+v_year+"&FIELD3="+v_cpsubj+"&FIELD4="+v_cpsubjseq+"&FIELD99="+v_company+"&FIELD100=N";
            } else {
                v_eduurl = EduEtc1Bean.make_eduURL(v_subj,v_year,v_subjseq);  //외부과정이 아닌경우
            }

            if(v_isonoff.equals("OFF")){
                v_eduurl = "/servlet/controller.study.OffEduStartServlet?p_process=selectFrame&p_subj="+v_subj+"&p_year="+v_year+"&p_subjseq="+v_subjseq;
            }
        }
        
        s_selectString += "<option value='"+v_subj+"||"+v_year+"||"+v_subjseq+"||'  "+s_selectDefault+" >"+v_subjnm+"</option>";
        
    }
    
    if (i == 0) {
    	s_subjnm       = "수강중인 과정이 없습니다";
    	s_selectString = "<option value=''>수강중인 과정이 없습니다</option>";

    	v_total_study_time = "-";
    	count = "-";
    	date  = "-";
    	step  = "-";
    }
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<Script language="javascript">

function whenSelectSubj(subj,year,subjseq) {
    document.form1.subj.value = subj;
    document.form1.year.value = year;
    document.form1.subjseq.value = subjseq;
    document.form1.action='/servlet/controller.system.StudyCountServlet';
    document.form1.p_process.value = 'myActivity';
    document.form1.submit();
}

function whenSelectSubj2( value) {
    var a_value = value.split("||");
   
    document.form1.subj.value = a_value[0] ;
    document.form1.year.value = a_value[1] ;
    document.form1.subjseq.value = a_value[2];
    document.form1.action='/servlet/controller.system.StudyCountServlet';
    document.form1.p_process.value = 'myActivity';
    document.form1.submit();
}

function studyOpen(url) {
<%  if (box.getSession("gadmin").equals("ZZ")) {  %>
    open_window('openApp',url,0,0,0,0,false,false,true,true,true);
<%  } else {                                      %>
    alert ("학습자 권한이 아닙니다.");
<%  }                                             %>
}
//-->
</Script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="form1" method="post" action="">
<input type="hidden" name="p_process" value="">
<input type="hidden" name="subj" value="<%=s_subj%>">
<input type="hidden" name="year" value="<%=s_year%>">
<input type="hidden" name="subjseq" value="<%=s_subjseq%>">
</form>

			<h2><img src="/images/portal/myclass/h2_tit8.gif" alt="나의학습활동" class="fl_l" /><p class="category">Home > 나의강의실 > <strong>나의학습활동</strong></p></h2>
 
			<p><img src="/images/portal/myclass/mystudy_txt.gif" alt="사이트 접속 기록으로 메뉴별 접근횟수, 최초/최종 로그인 일자, 총 로그인 횟수 등을 확인하실 수 있습니다." /></p>
						
			<ul class="tabwrap mg_t30">
				<li><a href="/servlet/controller.system.MenuCountServlet?p_process=myActivity" class="tab_myclass"><span>사이트활동</span></a></li>
				<li><a class="tab_myclass tab_myclass_on"><span>학습활동</span></a></li>
				<li class="tab_search">
				<img src="/images/portal/myclass/s_search.gif" alt="조회과정">
					<select style="width:200px" onchange="javascript:whenSelectSubj2(this.value);" >
						<%= s_selectString%>
					</select>
				</li>
			</ul>
			
			<table class="myclass_mystudy_view">
			<colgroup><col width="85px" /><col width="260px" /><col width="110px" /><col width="235px" /></colgroup>
			<tbody>
			<tr>
				<th>과정명</th>
				<td colspan="3"><strong><%=s_subjnm%></strong></td>
			</tr>
			<tr class="bg_green">
				<th>총 학습시간</th>
				<td class="fs_s"><%= v_total_study_time %></td>
				<th>강의실 접근횟수</th>
				<td><%=count%></td>
			</tr>
			<tr class="bg_green">
				<th>최근 학습일</th>
				<td class="fs_s"><%=date%></td>
				<th>진도(%)</th>
				<td><%=step%></td>
			</tr>
			</tbody>
			</table>
<%
	if (i != 0) {
%>
			<div class="myclass_mystudy_datawrap mg_t35">
				<div class="left full">
					<ul>
<%
		for(i = 0; i < list2.size(); i++) {
	    DataBox dbox2   = (DataBox)list2.get(i);
	
	    v_menu          =  dbox2.getString("d_menu");
	    v_menunm        =  dbox2.getString("d_menunm");
	    v_cnt           =  dbox2.getInt("d_cnt");
%>

						<li><%= v_menunm %><span><%= v_cnt %> 회</span></li>
<%
		}
%>
					</ul>
				</div>
			</div>
<%
	}
%>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->