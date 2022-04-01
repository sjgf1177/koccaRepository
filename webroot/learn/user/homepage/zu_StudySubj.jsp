
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

<%
    EndorphinBean edpbean = new EndorphinBean();
    ArrayList list = edpbean.selectMain(box);
    String content = "";

    if(list.size() > 0){
        DataBox dbox = (DataBox)list.get(0);
        content =  dbox.getString("d_content");
    }
%>
                        <table width="415" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" height="10"></td>
                          </tr>
                          <tr>
                            <td width="374"><img src="/images/user/homepage/endol_tit.gif" width="106" height="20"></td>
                            <td width="41">&nbsp;</td>
                          </tr>
                          <tr>
                            <td colspan="2"></td>
                          </tr>
                          <tr valign="top" >
                            <td height="135" colspan="2" class="padd_endol" background="/images/user/homepage/endol_back1.jpg">
                            <div style='width:200px;height:100px;overflow:auto;visibility:visible;border:1; scrollbar-3dlight-color:#B5B5B5;scrollbar-arrow-color:#B5B5B5;scrollbar-darkshadow-color:#FFFFFF;scrollbar-face-color:#FFFFFF;scrollbar-highlight-color:#FFFFFFc;scrollbar-shadow-color:#B5B5B5;'>
                            <table width="180" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td height="100">
                                    <font color="#11638E"><%=content%></font>
                                    </td>
                                </tr>
                              </table>
                              </div>
                             </td>
                          </tr>
                          <tr>
                            <td height="15" colspan="2"></td>
                          </tr>
                        </table>
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
    String v_my_aesseq        = "";

    Vector vec_date  = new Vector();

    String study_img  = "";
    String report_img = "";
    String exam_img   = "";
    String sul_img    = "";

    MyClassBean myclassbean = new MyClassBean();
    SubjGongAdminBean subjgongbean = new SubjGongAdminBean();
    int v_controlstudy = 0;

    ArrayList myclasslist = myclassbean.selectEducationSubjectList(box);
%>

                        <!-- 학습중인 과정현황 table start -->
                        <table width="415" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="2" height="10"></td>
                          </tr>
                          <tr>
                            <td width="249"><img src="/images/user/homepage/type1/study_tit.gif"></td>
                            <td width="166"><img src="/images/user/homepage/stu_teximg.gif"></td>
                          </tr>
                          <tr>
                            <td colspan="2"></td>
                          </tr>
                          <tr>
                            <td height="7" colspan="2"></td>
                          </tr>
                          <tr valign="top">
                            <td height="20" colspan="2">
                            <div style='width:410px;height:130px;overflow:auto;scrollbar-3dlight-color:#B5B5B5;scrollbar-arrow-color:#B5B5B5;scrollbar-darkshadow-color:#FFFFFF;scrollbar-face-color:#FFFFFF;scrollbar-highlight-color:#FFFFFFc;scrollbar-shadow-color:#B5B5B5;'>

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
        v_my_aesseq        =  mydbox.getString("d_aesseq");

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
            // 예스런일때
            if (v_my_contenttype.equals("Y")) {
                v_my_eduurl = "/servlet/controller.contents.EduStart";
                v_my_eduurl += "?FIELD1=" + v_my_user_id + "&FIELD2=" + v_my_year + "&FIELD3=" + v_my_subj + "&FIELD4=" + v_my_subjseq + "&FIELD5=" + v_my_aesseq + "&FIELD99=" + v_my_company + "&FIELD100=N"+ "&contenttype=" + v_my_contenttype+ "&p_subj=" + v_my_subj+"&p_year="+ v_my_year +"&p_subjseq="+v_my_subjseq;
            } else {
                v_my_eduurl = EduEtc1Bean.make_eduURL(v_my_subj,v_my_year,v_my_subjseq);  //외부과정이 아닌경우
            }
        }

        if(v_my_isonoff.equals("OFF")){
            v_my_eduurl = "/servlet/controller.study.OffEduStartServlet?p_process=selectFrame&p_subj="+v_my_subj+"&p_year="+v_my_year+"&p_subjseq="+v_my_subjseq;
        }


        // 현황에 보여질 날자
        vec_date = (Vector)myclassbean.getDateInfo(v_my_edustart, v_my_eduend);

        /*==========   권장진도율, 자기진도율 시작  ==========*/
        box.put("p_subj",v_my_subj);
        box.put("p_year",v_my_year);
        box.put("p_subjseq",v_my_subjseq);
        String promotion  = subjgongbean.getPromotion(box);         // 권장진도율
        String average    = subjgongbean.getAverage(box);           // 평균진도율
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
            sul_img = "stu_no.gif";
        }else if(suldata == 1) {  // 응시완료
            sul_img = "stu_yes.gif";
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
            report_img = "stu_yes.gif";
         } else {
            report_img = "stu_no.gif";
         }

        /*==========   평가 갯수 ==========*/
        ExamUserBean exambean = new ExamUserBean();
        ArrayList examdata = exambean.getUserData(box);
        /*==========   평가 갯수 ==========*/
        /*==========   평가 응시여부 ==========*/
        ArrayList examresultdata = exambean.getUserResultData(box);
        /*==========   평가 응시여부 ==========*/
        if ((Integer.parseInt((String)examdata.get(2)) -Integer.parseInt((String)examresultdata.get(2))) == 0 ) {
            exam_img = "stu_yes.gif";
         } else {
            exam_img = "stu_no.gif";
         }


        // 학습시간제한
        v_controlstudy = subjgongbean.allowStudy(box, v_my_subj, v_my_year, v_my_subjseq);
%>

                                <table width="390" border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                    <td width="80" rowspan="2" valign="top"> <table width="80" height="60" border="0" cellpadding="0" cellspacing="0">
                                        <tr>
                                          <td class="font04"><%=v_my_subjnm%></td>
                                        </tr>
                                        <tr>
                                          <td height="3"></td>
                                        </tr>
                                        <tr>
                                          <td><% if(v_my_isonoff.equals("ON")){ %>
                                                <% if (v_controlstudy >0) {         %>
                                                      <a href="javascript:controlstudy()"><img src="/images/user/button/btn_m_studydo.gif" width="60" height="17" border="0"></a>
                                                <% } else {                         %>
                                                      <a href="javascript:studyOpen('<%=v_my_eduurl%>','<%=v_my_ieduurl%>')"><img src="/images/user/button/btn_studydo.gif" border="0"></a>
                                                <% }                                %>
                                              <%}else{%>-<%}%>
                                            </td>
                                        </tr>
                                      </table></td>
                                    <td width="43" height="16" align="center" valign="top">&nbsp;</td>
                                    <td width="191" valign="top">
                                      <table width="220" border="0" cellspacing="0" cellpadding="0">
                                        <tr class="font04">
                                          <td width="51" class="font04"><%=(String)vec_date.elementAt(0)%></td>
                                          <td width="58" class="font04"><%=(String)vec_date.elementAt(1)%></td>
                                          <td width="52" class="font04"><%=(String)vec_date.elementAt(2)%></td>
                                          <td width="59" class="font04"><%=(String)vec_date.elementAt(3)%></td>
                                        </tr>
                                      </table></td>
                                    <td width="76">&nbsp;</td>
                                  </tr>
                                  <tr>
                                    <td align="center" valign="top"><img src="/images/user/homepage/<%=study_img%>"></td>
                                    <td width="191" valign="top">
                                      <!-- 그래프 -->
                                      <table width="220" border="1" cellspacing="0"
                                            cellpadding="2"   style="border-collapse:collapse;" bordercolor="#cccccc" bgcolor="#ffffff">
                                        <tr>
                                          <td> 
                                            <table width="<%=promotion%>%" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td width="100%" height="12" background="/images/user/homepage/stu_graph01.gif"></td>
                                              </tr>
                                            </table>
                                            <table width="<%=average+1%>%" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td width="100%" height="12" background="/images/user/homepage/stu_graph02.gif"></td>
                                              </tr>
                                            </table>
                                            <table width="<%=progress+1%>%" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td width="100%" height="12" background="/images/user/homepage/stu_graph03.gif"></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table></td>
                                    <td width="76" valign="top"><table width="50" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td width="37" align="right" class="font04">평가</td>
                                          <td width="13" align="right"><img src="/images/user/homepage/<%=exam_img%>" width="10" height="10"></td>
                                        </tr>
                                        <tr>
                                          <td align="right" class="font04">과제물</td>
                                          <td align="right"><img src="/images/user/homepage/<%=report_img%>" width="10" height="10"></td>
                                        </tr>
                                        <tr>
                                          <td align="right" class="font04">설문</td>
                                          <td align="right"><img src="/images/user/homepage/<%=sul_img%>" width="10" height="10"></td>
                                        </tr>
                                      </table></td>
                                  </tr>
                                  <tr>
                                    <td height="8" colspan="4"></td>
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
                          <tr>
                            <td height="15" colspan="2"></td>
                          </tr>
                        </table>

<% }                %>

