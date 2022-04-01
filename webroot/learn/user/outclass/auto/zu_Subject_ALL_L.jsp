<%
//**********************************************************
//  1. 제      목: SUBJECT ALL LIST
//  2. 프로그램명: zu_Subject_ALL_L.jsp
//  3. 개      요: 전체과정조회 (온라인)
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.01.14
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅
    String yeslearn_url = conf.getProperty("yeslearn.url.value");
    String  v_process     = box.getString("p_process");
    String  v_select      = box.getStringDefault("p_select","ON");


    String v_subj           = "";
    String v_scupperclass   = "";
    String v_scmiddleclass  = "";
    String v_isonoff        = "";
    String v_year           = "";
    String v_subjseq        = "";
    String v_subjnm         = "";
    String v_proposetype    = "";
    String v_isnew          = "";
    String v_ishit          = "";
    String v_isrecom        = "";
    String v_uclassnm       = "";
    String v_mclassnm       = "";
    String v_subjseqgr      = "";
    String v_preurl         = "";
    String v_propstart      = "";
    String v_propend        = "";
    String v_edustart       = "";
    String v_eduend         = "";
    int    v_studentlimit   = 0;
    String v_ispropose      = "";
    String  v_contenttype   = "";
    String v_aescontentid   = "";

	String v_statusString   = "";
    String v_hasPreviewObj  = "";
    String v_today          = "";
    String v_isonoff_value  = "";
    String v_imgNew         = "";
    String v_imgHit         = "";
    String v_imgRecom       = "";
    int    v_iedustart = 0;

    ArrayList list1      = null;

    list1 = (ArrayList)request.getAttribute("SubjectList");         // 과정 리스트
%>
<!------- 메뉴시작 ------------>
<%@ include file="/learn/user/include/topOutClass.jsp"%>
<!------- 메뉴끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--

// 과정상세정보
function whenSubjInfo(subj,subjnm,isonoff,upperclass,upperclassnm){
    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value = subjnm;
    document.form1.p_isonoff.value = isonoff;
    document.form1.p_upperclass.value = upperclass;
    document.form1.p_upperclassnm.value = upperclassnm;
    document.form1.p_process.value = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'SubjectListAll';
    document.form1.action='/servlet/controller.outclass.OutClassServlet';
    document.form1.submit();
}


//나의강의실
function goPersonHistory(){
  document.form1.target = "_self";
  document.form1.p_process.value = "EducationSubjectPage";
  document.form1.action="/servlet/controller.outclass.OutClassServlet";
  document.form1.submit();
}

//수강신청
function whenSubjPropose(subj,year,subjseq, subjnm) {

   if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")){
     return;
   }

   document.form1.target = "_self";
   document.form1.p_subj.value = subj;
   document.form1.p_year.value = year;
   document.form1.p_subjseq.value = subjseq;
   document.form1.p_process.value = "SubjectEduPropose";
   document.form1.action = "/servlet/controller.outclass.OutClassServlet";
   document.form1.submit();
}
// 맛보기
function whenPreShow(url,subj) {
    // 로그
    prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
    open_window("openShow",prelog_url,"0","0","10","10","","","",true,true);

    open_window("openShow",url,"100","100","800","717","","","",true,true);
}

// 맛보기
function studyOpen(url, subj) {
    // 로그
    prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
    open_window("openShow",prelog_url,"0","0","10","10","","","",true,true);

    open_window('openApp',url,0,0,0,0,false,false,true,true,true);
}
//-->
</SCRIPT>

<form name="form1" method="post" enctype = "multipart/form-data">
    <input type='hidden' name='p_process' value="<%=v_process%>">
    <input type='hidden' name='p_isonoff' value="ON">
    <input type='hidden' name='p_select'  value="<%=v_select%>">


    <input type='hidden' name='p_year'>
    <input type='hidden' name='p_subjseq'>
    <input type='hidden' name='p_proposestart'>
    <input type='hidden' name='p_proposeend'>
    <input type='hidden' name='p_proposeyn'>
    <input type='hidden' name='p_proposetype'>
    <input type='hidden' name='p_purl'>


    <input type='hidden' name='p_subj'>
    <input type='hidden' name='p_subjnm'>
    <input type='hidden' name='p_upperclass' value=''>
    <input type='hidden' name='p_upperclassnm' >
    <input type='hidden' name='p_actionurl' value='/servlet/controller.outclass.OutClassServlet'>
    <input type='hidden' name='p_rprocess' >

          <!-- center start -->
            <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td align="center" valign="top">
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/<%=site%>/tit_apply.jpg" ></td>
                    </tr>
                    <tr> 
                      <td height="20"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/<%=site%>/st_totallec.gif"></td>
                    </tr>
                    <tr> 
                      <td height="5"></td>
                    </tr>
                    <tr> 
                      <td> 
                        <!-- 전체과정 리스트 TABLE  -->
                        <table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                          <tr  class="lcolor"> 
                            <td height="3" colspan="6" class="lcolor"></td>
                          </tr>
                          <tr> 
                            <td width="55" class="tbl_gtit2">구분</td>
                            <td width="90" class="tbl_gtit">분류</td>
                            <td width="335"  class="tbl_gtit2">과정명</td>
                            <td width="80" class="tbl_gtit">교육기간</td>
                            <td width="60" class="tbl_gtit2">과정맛보기</td>
                            <td width="55" class="tbl_gtit">수강신청</td>
                          </tr>
<%
                  //과정 리스트
                  int i = 0;
                  for(i = 0; i < list1.size(); i++) {
                      DataBox dbox = (DataBox)list1.get(i);

                      v_subj          = dbox.getString("d_subj");
                      v_preurl        =  EduEtc1Bean.make_previewURL(v_subj);
                      v_hasPreviewObj = EduEtc1Bean.hasPreviewObj(v_subj);

					  v_preurl        = dbox.getString("d_preurl");
                      v_scupperclass  = dbox.getString("d_scupperclass");
                      v_scmiddleclass = dbox.getString("d_scmiddleclass");
                      v_isonoff       = dbox.getString("d_isonoff");
                      v_subjnm        = dbox.getString("d_subjnm");
                      v_isnew         = dbox.getString("d_isnew");
                      v_ishit         = dbox.getString("d_ishit");
                      v_isrecom       = dbox.getString("d_isrecom");
                      v_uclassnm      = dbox.getString("d_uclassnm");
                      v_mclassnm      = dbox.getString("d_mclassnm");
                      v_studentlimit  = dbox.getInt("d_studentlimit");
                      v_subjseq       = dbox.getString("d_subjseq");
                      v_year          = dbox.getString("d_year");
                      v_subjseqgr     = dbox.getString("d_subjseqgr");
                      v_propstart     = dbox.getString("d_propstart");
                      v_propend       = dbox.getString("d_propend");
                      v_edustart      = dbox.getString("d_edustart");
                      v_eduend        = dbox.getString("d_eduend");
                      v_ispropose     = dbox.getString("d_ispropose");
                      v_contenttype   = dbox.getString("d_contenttype");
                      v_aescontentid  = dbox.getString("d_aescontentid");
                      v_today = FormatDate.getDate("yyyyMMddhh");
					  // 예스런 타입이면
                      if (v_contenttype.equals("Y")) {
                          v_preurl = yeslearn_url + "/anytime/contents/"+v_aescontentid+"/guest/default.htm";
                      }

                      v_isonoff_value  = (v_isonoff.equals("ON"))?"사이버":"집합";

//v_isnew = "Y";v_ishit="Y";v_isrecom="Y";
                      if(v_isnew.equals("Y"))    v_imgNew = "&nbsp;<img src='/images/user/"+site+"/btn_snew.gif'>";
                      else                       v_imgNew = "";
                      if(v_ishit.equals("Y"))    v_imgHit = "&nbsp;<img src='/images/user/"+site+"/btn_shit.gif'>";
                      else                       v_imgHit = "";
                      if(v_isrecom.equals("Y"))  v_imgRecom = "&nbsp;<img src='/images/user/"+site+"/btn_srecom.gif'>";
                      else                       v_imgRecom = "";

                    if(v_edustart.equals("")){v_iedustart = 0;}
                    else{v_iedustart = Integer.parseInt(v_edustart);}

                    if(v_iedustart >= Integer.parseInt(v_today)){
                        v_statusString = "<a href=\"javascript:whenSubjPropose('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"')\" onfocus=this.blur()><img src='/images/user/"+site+"/btn_apply.gif' border=0></a>";

                        //out.println("v_ispropose"+v_ispropose);
                        if(v_ispropose.substring(0,1).equals("Y")){
                            v_statusString = "<a href='javascript:goPersonHistory()'><img src='/images/user/"+site+"/btn_apply_ing.gif' border=0></a>";
                        }
                    } else{
                        v_statusString = "-";
                    }

                      if(v_edustart.equals("")){v_edustart = "미정";}
                      else{v_edustart     = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");}

                      if(v_eduend.equals("")){v_eduend = "미정";}
                      else{v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");}
%>
                          <tr> 
                            <td class="tbl_grc"><%=v_isonoff_value%></td>
                            <td class="tbl_grc"><%=v_mclassnm%></td>
                            <td class="tbl_bleft"><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>','<%=v_scupperclass%>','<%=v_uclassnm%>')" title="<%=v_subjnm%>"><%=v_subjnm%></a></td>
                            <td class="tbl_bleft"><%=v_edustart%>- <%=v_eduend%></td>
                            <td class="tbl_grc"><% if (v_hasPreviewObj.equals("Y")) { %><a href="javascript:studyOpen('/servlet/controller.contents.EduStart?p_subj=<%=v_subj%>&p_year=2000&p_subjseq=0001&p_ispreview=Y', '<%=v_subj%>')" onfocus=this.blur()><img src="/images/user/<%=site%>/btn_c_preview.gif" border="0"></a><% } else { %><% if(!v_preurl.equals("")){ %><a href="javascript:whenPreShow('<%=v_preurl%>', '<%=v_subj%>')" onfocus=this.blur()><img src="/images/user/<%=site%>/btn_c_preview.gif" border="0"></a><%}%><% } %></td>
                            <td class="tbl_grc"><%=v_statusString%></td>
                          </tr>
<%
                }   // END FOR

                if (i == 0 ) {

%>
                          <tr> 
                            <td class="tbl_grc" colspan="6">현재신청중인 과정이 없습니다.</td>
                          </tr>
<%
                }
%>
                        </table>

                      </td>
                    </tr>
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
</form>

            </table>


          </td>
        </tr>
        <tr>
          <td class="mlcolor2">&nbsp;</td>
        </tr>
      </table>
     </td>
  </tr>
</table>
<!-- footer -->
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
