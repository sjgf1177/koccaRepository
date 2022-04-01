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
    box.put("leftmenu","04"); 

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

    String  v_total_time    = "";
    String  v_total_minute  = "";
    String  v_total_sec     = "";
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
    String count  = (String)request.getAttribute("studyCount");   
    // 강의실 접근정보
    String step  = (String)request.getAttribute("studyStep");   



    if(list3 != null && list3.size() > 0) {
        DataBox dbox3    = (DataBox)list3.get(0);
        v_total_time    = dbox3.getString("d_total_time");
        v_total_minute  = dbox3.getString("d_total_minute");
        v_total_sec     = dbox3.getString("d_total_sec");
        v_first_end     = dbox3.getString("d_first_end");
        v_lesson_count  = dbox3.getString("d_lesson_count");
        v_tstep         = dbox3.getString("d_tstep");
    }


    if(list4 != null && list4.size() > 0) {

        DataBox dbox4   = (DataBox)list4.get(0);
        v_boardcnt          =  dbox4.getInt("d_boardcnt");
        v_datacnt           =  dbox4.getInt("d_datacnt");
        v_qnacnt            =  dbox4.getInt("d_qnacnt");
        v_examcnt           =  dbox4.getInt("d_examcnt");
        v_sulcnt            =  dbox4.getInt("d_sulcnt");
        v_reportindate      =  dbox4.getString("d_reportindate");
    }
%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topMystudy.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

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

    //엑셀 출력
    function whenExcel() {
        window.self.name = "ActivityStudy";
        open_window("openExcel","","10","10","900","660","no","no","no","no","yes");
        document.form1.target = "openExcel";
        document.form1.action='/servlet/controller.system.StudyCountServlet';
        document.form1.p_process.value = 'myActivityExcel';
        document.form1.submit();
    }
//-->
</Script>

<form name="form1" method="post" action="">
<input type="hidden" name="p_process" value="">
<input type="hidden" name="subj" value="<%=s_subj%>">
<input type="hidden" name="year" value="<%=s_year%>">
<input type="hidden" name="subjseq" value="<%=s_subjseq%>">
</form>

          <!-- center start -->

<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/mystudy/<%=tem_subimgpath%>/tit_studyact.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 나의 공부방 > 나의 학습활동</td>
  </tr>
  <tr> 
    <td height="20">
      <!-- page sub -->
    </td>
  </tr>
</table>
<table width="720" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="3"><a href="controller.system.MenuCountServlet?p_process=myActivity"><img src="/images/user/game/mystudy/st_site_act_off.gif" name="Image1" width="101" height="31" border="0" id="Image1" onMouseOver="MM_swapImage('Image1','','/images/user/game/mystudy/st_site_act_on.gif','Image3','','/images/user/game/mystudy/st_learn_act_off.gif',1)" onMouseOut="MM_swapImgRestore()"></a><img src="/images/user/game/mystudy/<%=tem_subimgpath%>/blank.gif" name="Image2" width="3" height="10" id="Image2"><img src="/images/user/game/mystudy/st_learn_act_on.gif" name="Image3" width="101" height="31" border="0" id="Image3"></td>
  </tr>
  <tr>
    <td width="9" height="64"><img src="/images/user/game/mystudy/st_studyact03.gif"></td>
    <!--과정선택-->    
    <td width="702" valign="bottom"   style="padding-bottom:2px;" background="/images/user/game/mystudy/bg_studyact01.gif"><img src="/images/user/game/mystudy/st_learnact_chois.gif" ><img src="/images/user/game/mystudy/<%=tem_subimgpath%>/blank.gif" width="20" height="10">
<%
 String s_selectString = "";
    String s_selectDefaule = "";
    for(i = 0; i < list1.size(); i++) {
        DataBox dbox    = (DataBox)list1.get(i);

        v_subj          =  dbox.getString("d_subj");
        v_year          =  dbox.getString("d_year");
        v_subjnm        =  dbox.getString("d_subjnm");
        v_subjseq       =  dbox.getString("d_subjseq");

         s_selectDefaule = "";
        if(s_subj.equals(v_subj) && s_year.equals(v_year) && s_subjseq.equals(v_subjseq)){

            v_eduurl        =  dbox.getString("d_eduurl");
            v_cpsubj        =  dbox.getString("d_cpsubj");
            v_cpsubjseq     =  dbox.getString("d_cpsubjseq");
            v_company       =  dbox.getString("d_company");
            v_isonoff       =  dbox.getString("d_isonoff");
            v_isoutsourcing =  dbox.getString("d_isoutsourcing");
            s_subjnm        =   v_subjnm;
             s_selectDefaule = "selected";
            if (v_isoutsourcing.equals("Y")) {                 //위탁과정일때
                v_eduurl = v_eduurl+"?FIELD1="+v_user_id+"&FIELD2="+v_year+"&FIELD3="+v_cpsubj+"&FIELD4="+v_cpsubjseq+"&FIELD99="+v_company+"&FIELD100=N";


            } else {
                v_eduurl = EduEtc1Bean.make_eduURL(v_subj,v_year,v_subjseq);  //외부과정이 아닌경우
            }

            if(v_isonoff.equals("OFF")){
                v_eduurl = "/servlet/controller.study.OffEduStartServlet?p_process=selectFrame&p_subj="+v_subj+"&p_year="+v_year+"&p_subjseq="+v_subjseq;
            }
           

        }
        
        s_selectString += "<option value='"+v_subj+"||"+v_year+"||"+v_subjseq+"||'  "+s_selectDefaule+" >"+v_subjnm+"</option>";

%>
                     <!--   <a href="javascript:whenSelectSubj('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>')">
                        <img src="/images/user/game/mystudy/num_studyact<%=(i<8)? "0": ""%><%=i+1%>.gif" alt="<%=v_subjnm%>" border="0" ></a> -->
<%}%>
<%if (i==0){%>수강중인 과정이 없습니다.<%}%>   
						<select  name="select2"   onchange="javascript:whenSelectSubj2(this.value);" >
                                          <%= s_selectString%>
                                          </select>
    <td width="9"><div align="right"><img src="/images/user/game/mystudy/st_studyact04.gif" ></div></td>
  </tr>
  <tr>
    <td height="12" colspan="3"></td>
  </tr>
  <tr>
    <td colspan="3"><table width="720" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="106"><img src="/images/user/game/mystudy/<%=tem_subimgpath%>/blank.gif" width="9" height="10"><img src="/images/user/game/mystudy/st_info_basic.gif" width="77" height="59"><img src="/images/user/game/mystudy/st_studyact07.gif" width="20" height="59"></td>
        <td bgcolor="#E0EBE4"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="92" height="28"><img src="/images/user/game/mystudy/st_learnact_info01.gif"></td><!--학습시간-->
            <td width="180">: &nbsp;<%=v_total_time.equals("")?"00":v_total_time%> : <%=v_total_minute.equals("")?"00":v_total_minute%> : <%=v_total_sec.equals("")?"00":v_total_sec%></td>
            <td width="75"><img src="/images/user/game/mystudy/st_learnact_info02.gif"></td><!--최근학습일-->
            <td>: &nbsp;<%=date%></td>
          </tr>
          <tr>
            <td height="2" colspan="4" bgcolor="#FFFFFF"></td>
            </tr>
          <tr>
            <td height="29"><img src="/images/user/game/mystudy/st_learnact_info03.gif" ></td><!--강의실 접근횟수-->
            <td>: &nbsp;<%=count%></td>
            <td><img src="/images/user/game/mystudy/st_learnact_info04.gif"></td><!--진도-->
            <td>: &nbsp;<%=step%> %</td>
          </tr>
        </table></td>
        <td width="23" bgcolor="#E0EBE4"><div align="right"><img src="/images/user/game/mystudy/st_studyact08.gif" width="23" height="59"></div></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="20" colspan="3">&nbsp;</td>
  </tr>
</table>
<table width="720"  border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="4"><img src="/images/user/game/mystudy/st_studyact14.gif" width="4" height="35"></td>
    <td width="107" background="/images/user/game/mystudy/bg_studyact02.gif"><img src="/images/user/game/mystudy/st_studyact_course01.gif" width="95" height="19"><img src="/images/user/game/mystudy/<%=tem_subimgpath%>/blank.gif" width="7" height="10"></td>
    <td width="592" background="/images/user/game/mystudy/bg_studyact02.gif" class="font_specialroom_list"><%=s_subjnm%></td>
    <td width="17"><img src="/images/user/game/mystudy/st_studyact17.gif" width="15" height="35"></td>
  </tr>
</table>
<!-- 과정명 테이블 타이틀 -->
<table width="720" height="27" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="720" height="27" background="/images/user/game/mystudy/ba_bg.gif"><table width="720" height="28" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="125" align="center"  ><img src="/images/user/game/mystudy/text_t_menu.gif"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="92" align="center"  ><img src="/images/user/game/mystudy/text_t_num.gif" ></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="501" align="center" ><img src="/images/user/game/mystudy/text_t_list.gif" ></td>
        </tr>
      </table> </td>
  </tr>
  <tr>
    <td height="5"></td>
  </tr>
</table>
<!-- 관심과정table  -->
<table width="720" border="1" cellspacing="0"    cellpadding="0"  style="border-collapse:collapse;" frame="hsides" rules="rows" >
    
<%
for(i = 0; i < list2.size(); i++) {
    DataBox dbox2   = (DataBox)list2.get(i);

    v_menu          =  dbox2.getString("d_menu");
    v_menunm        =  dbox2.getString("d_menunm");
    v_cnt           =  dbox2.getInt("d_cnt");
%>

<%if(i==0 || i%2==0){
%>
  								<tr class="studyact_ba">
    							  <td>&nbsp;</td>
                                  <td width="135" class="tbl_gleft"><%=v_menunm%></td>
                                  <td width="105" class="tbl_grc"><%=v_cnt%></td>
                                  <td width="360" class="tbl_grc">
                    <%if(v_menu.equals("91")){%>
                        <%=(v_examcnt==0)?"평가에 참여하지 않으셨습니다.":v_examcnt+""%> 
                        &nbsp; &nbsp; &nbsp; &nbsp      
                        <% if(v_isonoff.equals("ON")){ %><a href="javascript:studyOpen('<%=v_eduurl%>')"><img src="/images/user/button/btn_m_myclassgo.gif" border="0"></a><%}else{%><%}%>                                
                    <%}else if(v_menu.equals("95")){%>  
                        <%=(v_sulcnt==0)?"설문에 참여하지 않으셨습니다.":v_sulcnt+""%> 
                        &nbsp; &nbsp; &nbsp;
                        <% if(v_isonoff.equals("ON")){ %><a href="javascript:studyOpen('<%=v_eduurl%>')"><img src="/images/user/button/btn_m_myclassgo.gif" border="0"></a><%}else{%><%}%>                                        
                    <%}else if(v_menu.equals("34")){%>  
                        제출일 : <%=v_reportindate.equals("")?"":FormatDate.getFormatDate(v_reportindate,"yyyy-MM-dd HH:mm")%>
                    <%}else if(v_menu.equals("21")){%>  
                                    <%=v_datacnt%>                              
                    <%}else if(v_menu.equals("22")){%>  
                                    <%=v_boardcnt%> 
                    <%}else if(v_menu.equals("3")){%>   
                                    <%=v_qnacnt%>   
                    <%}%>
                                  </td>
    							  <td class="tbl_bleft_none_bg">&nbsp;</td>                                  
                                </tr>

<%}else {%>
 								<tr>
    							  <td class="tbl_grc">&nbsp;</td>
                                  <td width="135" class="tbl_gleft"><%=v_menunm%></td>
                                  <td width="105" class="tbl_grc"><%=v_cnt%></td>
                                  <td width="360" class="tbl_grc">
                    <%if(v_menu.equals("91")){%>
                        <%=(v_examcnt==0)?"평가에 참여하지 않으셨습니다.":v_examcnt+""%> 
                        &nbsp; &nbsp; &nbsp; &nbsp      
                        <% if(v_isonoff.equals("ON")){ %><a href="javascript:studyOpen('<%=v_eduurl%>')"><img src="/images/user/button/btn_m_myclassgo.gif" border="0"></a><%}else{%><%}%>                                
                    <%}else if(v_menu.equals("95")){%>  
                        <%=(v_sulcnt==0)?"설문에 참여하지 않으셨습니다.":v_sulcnt+""%> 
                        &nbsp; &nbsp; &nbsp; &nbsp;
                        <% if(v_isonoff.equals("ON")){ %><a href="javascript:studyOpen('<%=v_eduurl%>')"><img src="/images/user/button/btn_m_myclassgo.gif" border="0"></a><%}else{%><%}%>                                        
                    <%}else if(v_menu.equals("34")){%>  
                        제출일 : <%=v_reportindate.equals("")?"":FormatDate.getFormatDate(v_reportindate,"yyyy-MM-dd HH:mm")%>
                    <%}else if(v_menu.equals("21")){%>  
                                    <%=v_datacnt%>                              
                    <%}else if(v_menu.equals("22")){%>  
                                    <%=v_boardcnt%> 
                    <%}else if(v_menu.equals("3")){%>   
                                    <%=v_qnacnt%>   
                    <%}%>
                                  </td>
    							  <td class="tbl_grc">&nbsp;</td>                                  
                                </tr>
<%
	}
%>                                
<%}//for%>    

 <!--tr class="studyact_ba"> 
    <td width="10" height="23" >&nbsp;</td>
    <td width="115" class="tbl_gleft">메인</td>
    <td width="93" class="tbl_grc">20</td>
    <td width="181" class="tbl_bleft_none_bg">&nbsp;</td>
    <td width="321" class="tbl_bleft_none_bg">&nbsp;</td>
  </tr>
  <tr> 
    <td height="26" class="tbl_grc">&nbsp;</td>
    <td height="26" class="tbl_gleft">공지사항</td>
    <td height="26" class="tbl_grc">23</td>
    <td class="tbl_grc">&nbsp;</td>
    <td class="tbl_grc">&nbsp;</td>
  </tr-->

  <tr> 
    <td height="5" colspan="5" ></td>
  </tr>
</table>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->