<%
//**********************************************************
//  1. 제      목: 홈페이지 학습진행중 과정
//  2. 프로그램명 : MyCourse.jsp
//  3. 개      요: 홈페이지 학습진행중 과정
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: kim.j.s 05.12.22
//  7. 수      정:
//***********************************************************
%>



<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.propose.*" %>

<% 

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    if (session.getAttribute("userid") == null) {

%>

<% } else {         %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.exam.*" %>
<%@ page import = "com.credu.course.*" %>

<SCRIPT LANGUAGE="JavaScript">
<!--
// 학습창 OPEN
function studyOpen(url, iurl) {
//    alert(url);
    
<%  if (box.getSession("gadmin").equals("ZZ")) {  %>
    var s_url = url+"&p_iurl="+iurl;
       if (iurl == 0)
        open_window('openApp',s_url,0,0,0,0,false,false,true,true,true);
       else 
        open_window('openApp',s_url,0,0,1024,768,false,false,'yes','yes','yes');
<%  } else {                                      %>
    alert ("학습자 권한이 아닙니다.");
<%  }                                             %>
}

// 학습시간제한
function controlstudy() {
    alert("지금은 학습가능 시간이 아닙니다.");
    return; 
}

//-->
</SCRIPT>

<%
    String v_my_upperclass    = "";
    String v_my_isonoff       = "";
    String v_my_subj          = "";
    String v_my_year          = "";
    String v_my_subjnm        = "";
    String v_my_subjseq       = "";
    String v_my_subjseqgr     = "";
    String v_my_edustart      = "";
    String v_my_eduend        = "";
    String v_my_eduurl        = "";
    String v_my_isoutsourcing = "";
    String v_my_cpsubj        = "";
    String v_my_cpsubjseq     = "";
    String v_my_company       = "";
    String v_my_chkfinal      = "";
    String v_my_middleclassnm = "";
    String v_my_isonoff_value = "";
    String v_my_edustart_value = "";
    String v_my_eduend_value   = "";
    String v_my_user_id       = box.getSession("userid");
    int    v_my_ieduurl = 0;
    String v_my_contenttype   = "";

    Vector vec_date  = new Vector();

    String study_img  = "";
    String report_img = "";
    String exam_img   = "";
    String sul_img    = "";
	String image_url = "/images/user/game/homepage/";

    MyClassBean myclassbean = new MyClassBean();
    SubjGongAdminBean subjgongbean = new SubjGongAdminBean();
    int v_controlstudy = 0;

    //ArrayList myclasslist = myclassbean.selectEducationSubjectList(box);
	ArrayList myclasslist = new ArrayList();
%>

<table cellpadding="0" cellspacing="0" width="536" style="table-layout:fixed;">
		<tr>
		<td><img src="/images/user/game/homepage/type1/tit_myprogram.gif"></td>
		</tr>
		<tr height="5">
		<td><img src="/images/user/game/homepage/type1/myprogram_top.gif"></td>
		</tr>
		<tr height="100" bgcolor="#f3f8e9">
		<td style="padding-left:8px;">
			<div style="width:525px;height:100px;background-color:#f3f8e9;overflow:auto;display:inline;" class="sshape">
			
			
<%
    int ss = 0;
    for(ss = 0; ss < myclasslist.size(); ss++) {
        DataBox mydbox    = (DataBox)myclasslist.get(ss);

        v_my_upperclass    =  mydbox.getString("d_upperclass");
        v_my_isonoff       =  mydbox.getString("d_isonoff");
        v_my_subj          =  mydbox.getString("d_subj");
        v_my_year          =  mydbox.getString("d_year");
        v_my_subjnm        =  mydbox.getString("d_subjnm");
        v_my_subjseq       =  mydbox.getString("d_subjseq");
        v_my_subjseqgr     =  mydbox.getString("d_subjseqgr");
        v_my_edustart      =  mydbox.getString("d_edustart");
        v_my_eduend        =  mydbox.getString("d_eduend");
        v_my_eduurl        =  mydbox.getString("d_eduurl");
        v_my_isoutsourcing =  mydbox.getString("d_isoutsourcing");
        v_my_cpsubj        =  mydbox.getString("d_cpsubj");
        v_my_cpsubjseq     =  mydbox.getString("d_cpsubjseq");
        v_my_company       =  mydbox.getString("d_company");
        v_my_chkfinal      =  mydbox.getString("d_chkfinal");
        v_my_middleclassnm =  mydbox.getString("d_middleclassnm");
        v_my_contenttype   =  mydbox.getString("d_contenttype");

        v_my_edustart_value = FormatDate.getFormatDate(v_my_edustart,"yyyy/MM/dd");
        v_my_eduend_value   = FormatDate.getFormatDate(v_my_eduend,"yyyy/MM/dd");

        if(v_my_isonoff.equals("ON"))      {   v_my_isonoff_value =   "사이버";
        }else if(v_my_isonoff.equals("OFF")){  v_my_isonoff_value =   "집합";     }

        // 학습url=외주인 경우..
        if(v_my_eduurl.equals("")) { v_my_ieduurl = 0; }else{ v_my_ieduurl = 1; }

        //학습URL?FIELD1=사번&FIELD2=교육년도&FIELD3=외주업체과정코드&FIELD4=외주업체과정차수
        //FIELD1="+v_userid+"&FIELD2="+v_gyear+"&FIELD3="+v_cpsubj+"&FIELD4="+v_cpsubjseq;
        if (v_my_isoutsourcing.equals("Y")) {                 //위탁과정일때
            //v_base64resno = StringManager.BASE64Encode(box.getSession("resno"));
            //v_eduurl = "http://www.credu.com/pls/cyber/zasp3.smotor_jumpjsp?p_subj=" +v_subj+ "&p_resno=" + v_base64resno;  //외부과정이 아닌경우
            //v_my_eduurl = //v_my_eduurl+"?FIELD1="+v_my_user_id+"&FIELD2="+v_my_year+"&FIELD3="+v_my_cpsubj+"&FIELD4="+v_my_cpsubjseq+"&FIELD99="+v_my_company+"&FIELD100=N";
            v_my_eduurl = "/servlet/controller.contents.EduStart";
            v_my_eduurl += "?FIELD1=" + v_my_user_id + "&FIELD2=" + v_my_year + "&FIELD3=" + v_my_cpsubj + "&FIELD4=" + v_my_cpsubjseq + "&FIELD99=" + v_my_company+"&FIELD100=N"+ "&contenttype=" + v_my_contenttype+ "&p_subj=" + v_my_subj+"&p_year="+ v_my_year +"&p_subjseq="+v_my_subjseq;
        } else {
                v_my_eduurl = EduEtc1Bean.make_eduURL(v_my_subj,v_my_year,v_my_subjseq);  //외부과정이 아닌경우
        }

        if(v_my_isonoff.equals("OFF")){
            v_my_eduurl = "/servlet/controller.study.OffEduStartServlet?p_process=selectFrame&p_subj="+v_my_subj+"&p_year="+v_my_year+"&p_subjseq="+v_my_subjseq;
        }


        // 현황에 보여질 날자
        //vec_date = (Vector)myclassbean.getDateInfo(v_my_edustart, v_my_eduend);

        /*==========   권장진도율, 자기진도율 시작  ==========*/
        box.put("p_subj",v_my_subj);
        box.put("p_year",v_my_year);
        box.put("p_subjseq",v_my_subjseq);
        String promotion  = subjgongbean.getPromotion(box);         // 권장진도율
        //String average    = subjgongbean.getAverage(box);           // 평균진도율
        String progress   = subjgongbean.getProgress2(box);          // 자기진도율
        /*==========   권장진도율, 자기진도율 끝  ==========*/
        if ((Float.parseFloat(promotion+5)) < Float.parseFloat(progress)) {
            study_img = "stu_top.gif";
        } else if ((Float.parseFloat(promotion)-5) > Float.parseFloat(progress)) {
            study_img = "stu_low.gif";
//      } else if ((promotion+5) >= progress && (promotion-5) <= progress) {
        } else {
            study_img = "stu_mid.gif";
        }

        /*==========   과정설문 응시여부 =====SulmunSubjUserBean=====*/
        SulmunSubjUserBean sulbean = new SulmunSubjUserBean();
        int suldata = sulbean.getUserData(box);
       /*==========   과정설문 응시여부 ==========*/

        if(suldata == 0) {        // 미응시
            sul_img = "myprogram_no.gif";
        }else if(suldata == 1) {  // 응시완료
            sul_img = "myprogram_yes.gif";
        }

        /*==========   레포트 제출개수 ==============*/
        ProjectAdminBean report = new ProjectAdminBean();
        int reportadmin = report.getAdminData(box);
        /*==========   레포트 제출개수 끝 ===========*/

        /*==========   레포트 제출여부 ==============*/
        int reportdata  = report.getUserData(box);
        /*==========   레포트 제출여부 끝 ===========*/

        /*==========   레포트 반려갯수 ==============*/
        int isRet  = report.getIsRet(box);
        /*==========   레포트 반려갯수 끝 ===========*/
        
         if (( reportadmin - reportdata + isRet) == 0 ) {
            report_img = "myprogram_yes.gif";
         } else {
            report_img = "myprogram_no.gif";
         }

        /*==========   평가 갯수 ==========*/
        ExamUserBean exambean = new ExamUserBean();
        ArrayList examdata = exambean.getUserData(box);
        /*==========   평가 갯수 ==========*/
        /*==========   평가 응시여부 ==========*/
        ArrayList examresultdata = exambean.getUserResultData(box);
        /*==========   평가 응시여부 ==========*/
        if ((Integer.parseInt((String)examdata.get(2)) -Integer.parseInt((String)examresultdata.get(2))) == 0 ) {
            exam_img = "myprogram_yes.gif";
         } else {
            exam_img = "myprogram_no.gif";
         }

        // 학습시간제한
        //v_controlstudy = subjgongbean.allowStudy(box, v_my_subj, v_my_year, v_my_subjseq);
%>			
			
			<table cellpadding="0" cellspacing="0" width="505" height="50" style="table-layout:fixed;">
			<col width="90"><col width="45"><col width="12"><col width="260"><col width="64">
			<tr height="49">
			<td style="font-size:11px;color:#000000;">
				<img src="/images/user/game/homepage/type1/myprogram_blt.gif"> <%=v_my_subjnm%><br>
				<% if(v_my_isonoff.equals("ON")){ %>
		              <a href="javascript:studyOpen('<%=v_my_eduurl%>','<%=v_my_ieduurl%>')"><img src="/images/user/game/homepage/type1/myprogram_learn.gif" hspace="12" border=0></a>
		        <%}else{%>-<%}%><br>
                                              
			</td>
			<td align="right"><img src ="<%=image_url+study_img%>"></td>
			<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td align="right" style="padding-right:40px;">
				<img src="/images/user/game/homepage/type1/myprogram_grid.gif"><br>
				<div style="width:260px;height:34px;background-color:#e3e5d8;text-align:left;">
					<img src="/images/user/game/homepage/type1/graph_b.gif" height="7" width="<%=promotion%>%" vspace="6"><br>
					<img src="/images/user/game/homepage/type1/graph_r.gif" height="7" width="<%=progress%>%"><br>
				</div>
			</td>
			<td>
				<img src="/images/user/game/homepage/type1/myprogram_01.gif" align="absmiddle" vspace="1"><img src="/images/user/game/homepage/type1/<%=exam_img%>" align="absmiddle"><br>
				<img src="/images/user/game/homepage/type1/myprogram_02.gif" align="absmiddle" vspace="1"><img src="/images/user/game/homepage/type1/<%=report_img%>" align="absmiddle"><br>
				<img src="/images/user/game/homepage/type1/myprogram_03.gif" align="absmiddle" vspace="1"><img src="/images/user/game/homepage/type1/<%=sul_img%>" align="absmiddle"><br>
			</td>
			</tr>
			<tr height="1">
			<td></td>
			<td colspan="4" background="/images/user/game/homepage/type1/dot.gif"></td>
			</tr>
			</table>
			
			
<%
    }

    if (ss == 0) {
%>
      <img src="/images/user/homepage/blank_study.jpg" width="406" height="126">
<%
    }
%>			
			
			</div>
		</td>
		</tr>
		<tr height="5">
		<td><img src="/images/user/game/homepage/type1/myprogram_bot.gif"></td>
		</tr>
		</tr>
		<tr height="20"><td></td></tr>
		</table>
		

<% }                %>