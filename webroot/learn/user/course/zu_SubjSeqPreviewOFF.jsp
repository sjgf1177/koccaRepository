<%
//**********************************************************
//  1. 제      목: SUBJECT PREVIEW PAGE
//  2. 프로그램명: zu_SubjectPreview.jsp
//  3. 개      요: 과정안내
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 01.12
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "memberBean" class = "com.credu.system.MemberAdminBean"  scope = "page" />
<jsp:useBean id = "code" class = "com.credu.system.CodeConfigBean"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process      = box.getString("p_process");
    String  v_subj         = box.getString("p_subj");
    String  v_subjseq      = box.getString("p_subjseq");
    String  v_isonoff      = box.getString("p_isonoff");
    String  v_upperclassnm = box.getString("p_upperclassnm");
    String  v_upperclass   = box.getString("p_upperclass");
    String  v_select       = box.getString("p_select");
    String  v_ispossible   = box.getString("p_ispossible");
    String  gyear          = box.getString("gyear");
    

    String  v_preurl    =  "";
    String  v_sdesc     =  "";
    String  v_propstart =  "";
    String  v_propend   =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_proposetype = "";
    String  v_useproposeapproval = "";
    String  v_subjnm       = "";
    String  v_place     =  "";
    String  v_placejh = "";
    String  v_tutor     =  "";
    String  v_muserid   =  "";
    String  v_isgoyong  =  "";
    String  v_subjtarget=  "";
    String  v_edumans   =  "";
    String  v_intro     =  "";
    String  v_explain   =  "";
    String  v_isonoff_value="";
    String  v_prop_img  =  "";
    String  v_isgoyong_value="";
    String  v_edutype   =  "";
    String  v_masteruser=  "";
    String  v_env       =  "";
    String  v_owner     =  "";  //소유주코드
    String  v_ownerman  =  "";  //크래듀운영자명
    String  v_ownertel  =  "";  //담당자 전화번호
    String  v_isessential = ""; // 과정구분
    String  v_purl        = ""; 
    String  v_lectdate   = "";
    
    int     v_studentlimit =0;
    int     v_biyong    =  0;
    int     v_wstep     =  0;
    int     v_wmtest    =  0;
    int     v_wftest    =  0;
    int     v_wreport   =  0;
    int     v_wact      =  0;
    int     v_wetc1     =  0;
    int     v_wetc2     =  0;
    int     v_Twetc     =  0;
    int     v_stepcnt   =  0;
    int     v_testcnt   =  0;
    int     v_reportcnt =  0;
    int     v_actcnt    =  0;
    int     v_gradscore =  0;
    int     v_gradftest =  0;
    int     v_gradreport= 0;
    int     v_gradstep  =  0;
    int     v_gradexam  =  0;
    int     v_edudays   =  0;
    //int     v_edutimes  =  0;
    int     v_ratewbt   =  0;
    int     v_ratevod   =  0;
    int     v_score     =  0;   // 학점배점
    
    String  v_edutimes = "";
    String  v_isessential_view = "";
    String  v_lesson    =  "";
    String  v_lecture   =  "";
    String  v_lecturedate= "";
    String  v_biyong_view = "";
    String  v_studentlimit_view  = "";
    int     i           =  0;
    String  s_userid = box.getSession("userid");
    String  s_jikwi  = box.getSession("jikwi");
    String  v_prop_check = "";
    String v_subjseqgr = "";
    
    
    DataBox dbox = (DataBox)request.getAttribute("subjseqPreview");
    
    v_subjseqgr          = dbox.getString("d_subjseqgr");
    v_subjnm             = dbox.getString("d_subjnm");
    v_studentlimit       = dbox.getInt("d_studentlimit");
    v_propstart          = dbox.getString("d_propstart");
    v_propend            = dbox.getString("d_propend");
    v_edustart           = dbox.getString("d_edustart");
    v_eduend             = dbox.getString("d_eduend");
    v_biyong             = dbox.getInt("d_biyong");
    v_edutimes           = dbox.getString("d_edutimes");
    v_proposetype        = dbox.getString("d_proposetype");
    v_useproposeapproval = dbox.getString("d_useproposeapproval");
    v_gradscore          = dbox.getInt("d_gradscore");
    v_gradftest          = dbox.getInt("d_gradftest");
    v_gradstep           = dbox.getInt("d_gradstep");
    v_gradreport         = dbox.getInt("d_gradreprot");
    v_wstep              = dbox.getInt("d_wstep");
    v_wmtest             = dbox.getInt("d_wmtest");
    v_wftest             = dbox.getInt("d_wftest");
    //v_whtest             = dbox.getInt("d_whtest");
    v_wreport            = dbox.getInt("d_wreport");
    v_wetc1              = dbox.getInt("d_wetc1");
    v_wetc2              = dbox.getInt("d_wetc2");
    v_gradexam           = dbox.getInt("d_gradexam");
    v_isgoyong           = dbox.getString("d_isgoyong");
    v_place              = dbox.getString("d_place");
    v_placejh            = dbox.getString("d_placejh");
    
    v_Twetc = v_wetc1 + v_wetc2;
    v_biyong_view = NumberFormat.getInstance().format(v_biyong);
    
    if(v_isgoyong.equals("Y"))      {   v_isgoyong_value      =   "환급";  }
    else                           {   v_isgoyong_value      =   "미환급"; }
    
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/user_style1.css">
</head>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >

<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="730" height="100%">
  <tr>
    <td>
      <table cellpadding="0" cellspacing="1" bgcolor="#bebebe" class="table2" height="100%">
        <tr>
          <td align="center" valign="top" bgcolor="#FFFFFF" class="body_color">
            <br>
            <!-- 과정소개 table  -->
            <table width="675" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
              <tr>
                <td width="323" valign="top"><img src="/images/user/apply/st_numinfo.gif" width="333" height="16"></td>
                <td width="352" align="right" valign="top">&nbsp; </td>
              </tr>
              <tr>
                <td height="10" colspan="2"></td>
              </tr>
              <tr>
                <td height="5" colspan="2"><img src="/images/user/apply/sst_edudate.gif" alt="교육일정"></td>
              </tr>
              <tr>
                <td height="5" colspan="2"></td>
              </tr>
              <tr valign="top">
                <td height="20" colspan="2">
                  <!-- 강의목차 table -->
                  <table width="675" border="2" cellspacing="0"
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                    <tr  class="lcolor">
                      <td height="3" colspan="4" class="lcolor"></td>
                    </tr>
                      <td width="97" class="tbl_gtit2">과정명</td>
                      <td width="280" class="tbl_gleft"><%=v_subjnm%></td>
                      <td width="100" class="tbl_gtit">차수</td>
                      <td width="162" class="tbl_gleft"><%=Integer.parseInt(v_subjseqgr)%>차</td>
                    </tr>
                    <tr>
                      <td class="tbl_gtit2">수강신청시작일</td>
                      <td class="tbl_gleft"><%=FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd")%></td>
                      <td class="tbl_gtit">수강신청종료일</td>
                      <td class="tbl_gleft"><%=FormatDate.getFormatDate(v_propend,"yyyy/MM/dd")%></td>
                    </tr>
                    <tr>
                      <td height="26" class="tbl_gtit2">교육시작일</td>
                      <td class="tbl_gleft"><%=FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")%></td>
                      <td class="tbl_gtit">교육종료일</td>
                      <td class="tbl_gleft"><%=FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")%></td>
                    </tr>
                    <tr>
                      <td class="tbl_gtit2">교육정원</td>
                      <td class="tbl_gleft"><%=v_studentlimit%>명</td>
                      <td class="tbl_gtit">교육시간</td>
                      <td class="tbl_gleft"><%=v_edutimes%>시간</td>
                    </tr>
                    <tr>
                      <td class="tbl_gtit2">교육장소</td>
                      <td class="tbl_gleft"><%=v_place%></td>
                      <td class="tbl_gtit">집합장소</td>
                      <td class="tbl_gleft"><%=v_placejh%></td>
                    </tr>
                  </table>
                </td>
              </tr>

              <tr>
                <td height="20" colspan="2"></td>
              </tr>
              <tr>
                <td height="10" colspan="2"><img src="/images/user/apply/sst_gijun.gif" alt="수료기준"></td>
              </tr>
              <tr>
                <td height="5" colspan="2"></td>
              </tr>
              <tr>
                <td height="10" colspan="2"><table width="675" border="2" cellspacing="0"
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                    <tr  class="lcolor">
                      <td height="3" colspan="3" class="lcolor"></td>
                    </tr>
                    <tr>
                      <td width="147" class="tbl_gtit">평가기준</td>
                      <td width="232" class="tbl_gtit2">가중치</td>
                      <td width="268" class="tbl_gtit2">차수항목별수료기준</td>
                    </tr>
                    <tr>
                      <td class="tbl_gtit">출석률</td>
                      <td class="tbl_grc"><%if(v_wstep==0){out.print("-");}else{out.print(v_wstep+"%");}%></td>
                      <td class="tbl_grc"><%if(v_gradstep==0){out.print("-");}else{out.print(v_gradstep+"%");}%></td>
                    </tr>
                    <tr>
                      <td height="26" class="tbl_gtit">평가</td>
                      <td class="tbl_grc"><%if(v_wftest==0){out.print("-");}else{out.print(v_wftest+"%");}%></td>
                      <td class="tbl_grc"><%if(v_gradftest==0){out.print("-");}else{out.print(v_gradftest+"점");}%></td>
                    </tr>
                    <tr>
                      <td class="tbl_gtit">레포트</td>
                      <td class="tbl_grc"><%if(v_wreport==0){out.print("-");}else{out.print(v_wreport+"%");}%></td>
                      <td class="tbl_grc"><%if(v_gradreport==0){out.print("-");}else{out.print(v_gradreport+"점");}%></td>
                    </tr>
                    <tr>
                      <td class="tbl_gtit">기타가중치</td>
                      <td class="tbl_grc"><%if(v_Twetc==0){out.print("-");}else{out.print(v_Twetc+"%");}%></td>
                      <td class="tbl_grc">-</td>
                    </tr>
                    <tr>
                      <td bgcolor="#F5F5F5" class="tbl_grc">수료기준점수(총점)</td>
                      <td colspan="2" bgcolor="#F5F5F5" class="tbl_grc">총점 <%=v_gradscore%>점 이상</td>
                    </tr>
                  </table></td>
              </tr>

              <tr>
                <td height="20" colspan="2"></td>
              </tr>
              <tr>
                <td height="10" colspan="2"><img src="/images/user/apply/sst_lecdate.gif" alt="강좌일정"></td>
              </tr>
              <tr>
                <td height="5" colspan="2"></td>
              </tr>
              <tr>
                <td height="10" colspan="2"><table width="675" border="2" cellspacing="0"
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                    <tr  class="lcolor">
                      <td height="3" colspan="4" class="lcolor"></td>
                    </tr>
                    <tr>
                      <td width="97" class="tbl_gtit2">No</td>
                      <td width="280" class="tbl_gtit">일정 및 시간</td>
                      <td width="186" class="tbl_gtit2">강좌명</td>
                      <td width="76" class="tbl_gtit">담당강사</td>
                    </tr>
<%
        ArrayList list1 = (ArrayList)request.getAttribute("lessonList");
        if(list1 != null){
            for(i = 0; i<list1.size(); i++) {
                ProposeCourseData data2  = (ProposeCourseData)list1.get(i);
                v_lecture   =   data2.getLecture();                       
                v_sdesc     =   data2.getSdesc();                         
                v_lecturedate=  data2.getLecturedate();                   
                v_tutor     =   data2.getTutor();                         
%>
                    <tr>
                      <td class="tbl_gtit2"><%= list1.size() - i %></td>
                      <td class="tbl_gleft"><%= v_lecturedate %></td>
                      <td class="tbl_gleft"><%= v_sdesc %></td>
                      <td class="tbl_grc"><%= v_tutor %></td>
                    </tr>
<%}
        }
        if(i==0){
%>
                    <tr>
                      <td class="tbl_grc" colspan="5">등록된 강좌일정이 없습니다.</td>
                    </tr>
<%
        }
%>

                  </table>
                </td>
              </tr>
              <tr>
                <td height="10" colspan="2"></td>
              </tr>
              <tr align="right">
                <td height="20" colspan="2"><a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif" alt="닫기"  border="0"></a></td>
              </tr>
              <tr>
                <td height="20" colspan="2"></td>
              </tr>
            </table>
        <!-- 과정소개 table 끝 -->
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>

