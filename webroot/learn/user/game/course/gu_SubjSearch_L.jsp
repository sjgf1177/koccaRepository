<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명: gu_SubjSearch_L.jsp
//  3. 개      요: 과정조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.01.12
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","03");                       // 메뉴 아이디 세팅
    String  v_process         = box.getString("p_process");

    String  v_lsearch         = box.getString("p_lsearch");
    String  v_lsearchtext     = box.getString("p_lsearchtext");

    String  v_upperclass      =  "";
    String  v_middleclass     =  "";
    String  v_middleclassnm   =  "";
    String  v_isonoff         =  "";
    String  v_subj            =  "";
    String  v_subjnm          =  "";
    String  v_subjseq         =  "";
    String  v_propend         =  "";
    String  v_edustart        =  "";
    String  v_eduend          =  "";

    String  v_isonoff_value   =  "";
    String  v_subjnm_value    =  "";
    String  v_middleclassnm_value =  "";
    double v_sul_avg = 0; // 과정 만족도

    double tmp_sul_avg = 0;
    String v_imgStart = "";

    int     v_total           =  0;
    int     i                 =  0;
    int		v_ptsct_cnt       =  0;

    ArrayList list   = (ArrayList)request.getAttribute("SubjectList");
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topCourse.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--

    // 과정검색 엔터처리
    function search_enter(e)  {
        if (e.keyCode =='13'){  subjSearch();}
    }

    // 과정검색
    function subjSearch() {
        if (document.form1.p_lsearchtext.value=="") {
            alert("검색어를 입력해주세요");
            return;
        }

        document.form1.action = "/servlet/controller.propose.MainSubjSearchServlet";   //
        document.form1.p_process.value = "SubjSearch";
        document.form1.submit();
    }

    // 과정 상세보기
    function whenSubjInfo(subj,isonoff){
      document.form1.p_subj.value = subj;
      document.form1.p_select.value = isonoff;
      document.form1.p_process.value  = 'SubjectPreviewPage';
      document.form1.p_rprocess.value = 'SubjSearch';
      document.form1.action='/servlet/controller.course.CourseIntroServlet';
      document.form1.target = "_self";
      document.form1.submit();
    }

    // 과정 후기 팝업
    function whenFeedback(subj, subjnm) {
        window.self.name = "PersonalSelect";
        open_window("Feedback","","100","100","720","600","","","","yes","");

        document.form1.target = "Feedback";
        document.form1.action='/servlet/controller.course.PostScriptServlet';
        document.form1.p_subj.value   = subj;
        document.form1.p_subjnm.value = subjnm;
        document.form1.p_process.value = 'list';
        document.form1.submit();

        document.form1.target = window.self.name;
    }

    function whenPreShow(url) {
        open_window("openShow",url,"100","100","800","717","","","",true,true);
    }

//-->
</script>

<form name="form1" method="post" >
    <input type='hidden' name='p_process'  value="<%=v_process%>">
    <input type='hidden' name='p_rprocess' value="">
    <input type='hidden' name='p_select'   value="">
    <input type='hidden' name='p_subj'     value="">
    <input type='hidden' name='p_subjnm'     value="">

    <!-- title -->
    <table width="720" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="35" align="right"  background="/images/user/game/course/<%=tem_subimgpath%>/tit_all_search.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> HOME > 과정안내 > 전체과정검색</td>
      </tr>
      <tr>
        <td height="20"></td>
      </tr>
    </table>

    <!-- 전체과정검색 -->
    <table width="720" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><table width="353" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="87">
                <select name="p_lsearch">
                    <option value="middleclass" <% if (v_lsearch.equals("middleclass")) out.print("selected");%>>분류</option>
                    <option value="subjnm" <% if(v_lsearch.equals("subjnm")) out.print("selected");%>>과정명</option>
                </select></td>
              <td width="148">
                <input type="text" class="input" name="p_lsearchtext" value="<%=v_lsearchtext%>" onkeypress="search_enter(event)" size="20">
               </td>
              <td width="118"><a href="javascript:subjSearch()"><img src="/images/user/game/button/btn_seatch.gif" width="48" height="21"></a></td>
            </tr>
          </table></td>
      </tr>
      <tr>
        <td height="5"></td>
      </tr>
    </table>

    <table width="720" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
      <tr>
        <td height="3" colspan="4" class="linecolor_board6"></td>
      </tr>
      <tr>
        <td width="100" class="tbl_gtit3">분류</td>
        <td class="tbl_gtit3">과정명</td>
        <!--
        <td width="150" class="tbl_gtit3">만족도<br></td>
        -->
        <td width="150"  class="tbl_gtit3">상세보기</td>
      </tr>
      <tr>
        <td height="5" colspan="5" ></td>
      </tr>
<%
        v_total = list.size();
        for(i = 0; i < v_total; i++) {
            DataBox dbox  = (DataBox)list.get(i);
v_ptsct_cnt = dbox.getInt("d_ptsct_cnt");
            v_isonoff      = dbox.getString("d_isonoff");
            v_subj         = dbox.getString("d_subj");
            v_upperclass   = dbox.getString("d_upperclass");
            v_middleclass   = dbox.getString("d_middleclass");
            v_middleclassnm = dbox.getString("d_classname");
            v_subjnm       = dbox.getString("d_subjnm");
            v_sul_avg    = dbox.getDouble("d_sul_avg");

            v_imgStart = "";
            tmp_sul_avg = v_sul_avg * 10;

            for(int k = 0; k < 5; k++) {
                if(tmp_sul_avg > 10) {
                    v_imgStart += "<img src='/images/user/game/course/i_star_on.gif'>";
                } else {
                    if(tmp_sul_avg >= 5) {
                        v_imgStart += "<img src='/images/user/game/course/i_star_half.gif'>";
                    } else {
                        v_imgStart += "<img src='/images/user/game/course/i_star_off.gif'>";
                    }
                }

                tmp_sul_avg = tmp_sul_avg - 10;
            }

            if(v_isonoff.equals("ON")) v_isonoff_value = "사이버";
            else                       v_isonoff_value = "집합";
            v_subjnm_value        = v_subjnm;
            v_middleclassnm_value = v_middleclassnm;
            if (!v_lsearchtext.equals("")&&v_lsearch.equals("isonoff")) {
                v_isonoff_value = StringManager.replace(v_isonoff_value, v_lsearchtext, "<font color=\"red\">" + v_lsearchtext + "</font>");
            } else if(!v_lsearchtext.equals("")&&v_lsearch.equals("upperclass")){
                v_middleclassnm_value = StringManager.replace(v_middleclassnm, v_lsearchtext, "<font color=\"red\">" + v_lsearchtext + "</font>");
            } else if(!v_lsearchtext.equals("")&&v_lsearch.equals("subjnm")){
                v_subjnm_value = StringManager.replace(v_subjnm, v_lsearchtext, "<font color=\"red\">" + v_lsearchtext + "</font>");
                v_subjnm_value = StringManager.replace(v_subjnm, v_lsearchtext.toLowerCase(), "<font color=\"red\">" + v_lsearchtext.toLowerCase() + "</font>");
                v_subjnm_value = StringManager.replace(v_subjnm, v_lsearchtext.toUpperCase(), "<font color=\"red\">" + v_lsearchtext.toUpperCase() + "</font>");
            }
%>

      <tr>
        <td class="tbl_grc"><%=v_middleclassnm_value%></td>
        <td class="tbl_bleft"><%=v_subjnm_value%></td>
        <!--
        <td class="tbl_bleft">
          <table width="100%">
            <tr>
              <td width="40" align="right"><%=v_sul_avg%></td>
              <td><%=v_imgStart%></td>
            </tr>
          </table>
        </td>
        -->
        <td class="tbl_grc"><img src="/images/user/game/course/blank.gif" width="3" height="9"><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_isonoff%>')"><img src="/images/user/game/button/btn_into_view.gif" width="67" height="19"></a></td>
      </tr>
<%
        }

        if (v_total == 0 ) {
%>
      <tr>
        <td colspan="5" class="tbl_grc">검색된 과정이 없습니다.</td>
      </tr>
<%
        }
%>
    </table>

</form>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->