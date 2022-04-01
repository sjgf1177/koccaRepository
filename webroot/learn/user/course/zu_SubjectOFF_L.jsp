<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명: zu_Subject_L.jsp
//  3. 개      요: 과정조회
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
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅

    String  v_process     = box.getString("p_process");
    String  v_select      = box.getStringDefault("p_select","OFF");
    String  v_gubun       = "";


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
    String v_Bmclassnm      = "";
    String v_totcnt         = "";
    int    v_total          = 0;
    int    v_possiblecnt    = 0;
    int    v_class_len      = 0;
    String v_isonoff_value = "";

    ProposeCourseBean bean = new ProposeCourseBean();
    String  s_userid = box.getSession("userid");
    String  s_jikwi  = box.getSession("jikwi");
    String  v_prop_check = "";
    ArrayList list      = null;
    ArrayList list1      = null;
    //ArrayList list2      = null;
    //DEFINED class&variable END

    list = (ArrayList)request.getAttribute("SubjectList");
    //list2 = (ArrayList)request.getAttribute("MiddleClassList");
    out.println("123333");

%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<script language="JavaScript" type="text/JavaScript">
<!--
    // 과정검색 엔터처리
    function search_enter(e)  {
        if (e.keyCode =='13'){  subjSearch();  }
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

function whenSelect(sel,gubun){
    document.form1.p_select.value = sel;
    document.form1.p_isonoff.value = sel;
    document.form1.p_gubun.value = gubun;
    //alert(document.form1.s_upperclass.value);
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.p_process.value = 'SubjectList';
    document.form1.submit();
}

function whenSubjInfo(subj,subjnm,isonoff,upperclass,upperclassnm,process){
    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value = subjnm;
    document.form1.p_isonoff.value = isonoff;
    document.form1.p_upperclass.value = upperclass;
    document.form1.p_upperclassnm.value = upperclassnm;
    document.form1.p_process.value = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'SubjectList';
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.submit();
}
//-->

</script>

<form name="form1" method="post" enctype = "multipart/form-data">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_isonoff' value="OFF">
    <input type='hidden' name='p_select'  value="<%=v_select%>">
    <input type='hidden' name='p_gubun'   value="<%=v_gubun%>">

    <input type='hidden' name='p_course'>
    <input type='hidden' name='p_cyear'>
    <input type='hidden' name='p_courseseq'>
    <input type='hidden' name='p_coursenm'>
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
    <input type='hidden' name='p_actionurl' value='/servlet/controller.propose.ProposeCourseServlet'>
    <input type='hidden' name='p_rprocess' >

          <!-- center start -->
            <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location"> 
                  HOME &gt; 수강신청/안내 &gt; 수강신청</td>
              </tr>
              <tr> 
                <td align="center" valign="top">
                <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="75"><img src="/images/user/support/search_img.gif" alt="과정검색"></td>
                      <td width="75">
                        <select name="p_lsearch">
                          <option value="isonoff">구분</option>
                          <option value="upperclass">분류</option>
                          <option value="subjnm">과정명</option>
                        </select>
                      </td>
                      <td width="132"><input type="text" class="input" name="p_lsearchtext" onkeypress="search_enter(event)" size="20"></td>
                      <td width="394"><a href="javascript:subjSearch()"><img src="/images/user/button/btn_search.gif" alt="검색"></a></td>
                    </tr>
                    <tr> 
                      <td colspan="4"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                  </table> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/apply/<%=tem_subimgpath%>/tit_apply.jpg"></td>
                    </tr>
                    <tr> 
                      <td height="20"></td>
                    </tr>
                  </table> .

                  <!-- 과정구분탭 -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0" bgcolor="EBF6EE">
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                    <tr> 
                      <td width="98" valign="top"><a href="javascript:whenSelect('ON','<%=v_gubun%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image28','','/images/user/apply/tab_cyber_on.gif',1)"><img src="/images/user/apply/tab_cyber.gif" alt="사이버과정" name="Image28" width="98" height="22" border="0"></a>
                      </td>
                      <td width="98" valign="top" ><img src="/images/user/apply/tab_jib_on.gif" alt="집합과정" width="98" height="22"></td>
                      <td width="98" >&nbsp;</td>
                      <td width="377" >&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="20" colspan="4"></td>
                    </tr>
                  </table>

                  <!-- 과정분류탭 -->
                  <table width="675" border="0" cellpadding="0" cellspacing="0" bgcolor="EBF6EE">
                    <tr> 
                      <td width="105"> <table width="105" border="0" cellpadding="0" cellspacing="0" background="/images/user/apply/tab_bg_on.gif">
                          <tr> 
                            <td width="9" height="25"><img src="/images/user/apply/tab_head_on.gif"></td>
                            <td width="87" class="tbl_gtabon">집합교육</td>
                            <td width="10" align="right"><img src="/images/user/apply/tab_tail_on.gif"></td>
                          </tr>
                        </table>
                      </td>
                      <td width="5">&nbsp;</td>
                      <td width="105">&nbsp; </td>
                      <td width="5">&nbsp;</td>
                      <td width="105">&nbsp; </td>
                      <td width="54">&nbsp;</td>
                      <td width="296" align="right"><!--<img src="/images/user/button/btnb_totalview.gif">--></td>
                    </tr>
                    <tr bgcolor="C8C8C8"> 
                      <td height="2" colspan="7"></td>
                    </tr>
                  </table>

                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="675" height="5" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td colspan="2"> 
                        <!-- 수강신청취소table  -->
                        <table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                          <tr  class="lcolor"> 
                            <td height="3" colspan="5" class="lcolor"></td>
                          </tr>
                          <tr> 
                            <td width="55" class="tbl_gtit2">구분</td>
                            <td width="88" class="tbl_gtit">분류</td>
                            <td colspan="2" class="tbl_gtit2">과정명</td>
                            <!--<td width="78"  class="tbl_gtit">신청가능여부</td>-->
                          </tr>

<%
                    int i = 0;
                    for(i = 0; i < list.size(); i++) {
                        DataBox dbox = (DataBox)list.get(i);
                        
                        v_subj          = dbox.getString("d_subj");
                        v_scupperclass  = dbox.getString("d_scupperclass");
                        v_scmiddleclass = dbox.getString("d_scmiddleclass");
                        v_isonoff       = dbox.getString("d_isonoff");
                        v_subjnm        = dbox.getString("d_subjnm");
                        v_isnew         = dbox.getString("d_isnew");
                        v_ishit         = dbox.getString("d_ishit");
                        v_isrecom       = dbox.getString("d_isrecom");
                        v_uclassnm      = dbox.getString("d_uclassnm");
                        v_mclassnm      = dbox.getString("d_mclassnm");
                        v_possiblecnt   = dbox.getInt("d_possiblecnt");
                        v_totcnt        = dbox.getString("d_totcnt");
                        
                        if(v_isonoff.equals("ON")){v_isonoff_value="사이버";}
                        else{v_isonoff_value="집합";}
%>
                          <tr> 
                            <td class="tbl_grc"><%=v_isonoff_value%></td>
                            <td class="tbl_grc"><%=v_uclassnm%></td>
                            <td colspan="2" class="tbl_bleft"><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>','<%=v_scupperclass%>','<%=v_uclassnm%>','SubjectPreviewPage')"><%=v_subjnm%></a></td>
                            <!--<td class="tbl_grc"><%if(v_possiblecnt>0){out.print("신청가능");}else{out.print("-");}%></td>-->
                          </tr>

<%                  }
                    if(i == 0){
%>
                          <tr> 
                            <td class="tbl_grc" colspan="5">현재운영중인 집합과정이 없습니다.</td>
                          </td>
<%
                    }
%>
                        </table>
					  </td>
                    </tr>
                    <tr> 
                      <td colspan="2">&nbsp;</td>
                    </tr>
                  </table>
				</td>
              </tr>
</form>

              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
              <tr> 
                <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
              </tr>
            </table>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->