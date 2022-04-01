<%
//**********************************************************
//  1. 제      목: SUBJECT PREVIEW PAGE
//  2. 프로그램명: ku_ProposeBill_I.jsp
//  3. 개      요: 과정안내
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2006. 01.06
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
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "memberBean" class = "com.credu.system.MemberAdminBean"  scope = "page" />
<jsp:useBean id = "code" class = "com.credu.system.CodeConfigBean"  scope = "page" />
<jsp:useBean id = "probean" class = "com.credu.propose.KProposeCourseBean"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅

    String upload_url = conf.getProperty("url.upload");

    String  v_process      = box.getString("p_process");
    String  v_rprocess     = box.getString("p_rprocess");
    String  v_subj         = box.getString("p_subj");
    String  v_subjnm       = box.getString("p_subjnm");
    String  v_isonoff      = box.getString("p_isonoff");
    String  v_upperclassnm = box.getString("p_upperclassnm");
    String  v_upperclass   = box.getString("p_upperclass");
    String  v_select       = box.getString("p_select");
    String  v_actionurl    = box.getString("p_actionurl");
    String  v_lsearchsubject = box.getString("p_lsearchsubject");
    String  v_gubun         = box.getString("p_gubun");
    String  v_year          = box.getString("p_year");
    String  v_subjseq       = box.getString("p_subjseq");


    String  v_preurl    =  "";
    String  v_sdesc     =  "";
    String  v_propstart =  "";
    String  v_propend   =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_place     =  "";
    String  v_tutor     =  "";
    String  v_muserid   =  "";
    String  v_mastermail=  "";
    String  v_mastertel =  "";
    String  v_isgoyong  =  "";
    String  v_subjtarget=  "";
    String  v_edumans   =  "";
    String  v_intro     =  "";
    String  v_explain   =  "";
    String  v_memo      =  "";
    String  v_prop_img  =  "";
    String  v_edutype   =  "";
    String  v_masteruser=  "";
    String  v_env       =  "";
    String  v_owner     =  "";  //소유주코드
    String  v_ownerman  =  "";  //크래듀운영자명
    String  v_ownertel  =  "";  //담당자 전화번호
    String  v_isessential = ""; // 과정구분
    String  v_purl        = "";
    String  v_isgoyong_value="";
    String  v_isonoff_value ="";
    String  v_introducefilenamereal = "";
    String  v_introducefilenamenew  = "";
    String  v_introducefilenamenew_view  = "";
    String  v_contenttype   = "";
    String  v_studentlimitch= "";
    String  v_musername     = "";
    String  v_course        = "";
    String  v_courseseq     = "";
    String  v_coursenm      = "";
    String  v_tmp_subj      = "";
    String  v_iscourseYn    = "";

    int     v_studentlimit =0;
    int     v_biyong    =  0;
    int     v_scbiyong    =  0;
    int     v_edudays   =  0;
    int     v_edutimes  =  0;
    int     v_ratewbt   =  0;
    int     v_ratevod   =  0;
    int     v_score     =  0;   // 학점배점
    int     v_propcnt   = 0;
    int     v_bookprice   = 0;
    int     v_realpay   =   0;
    int     v_subjcnt   = 0;


    String  v_isessential_view = "";
    String  v_lesson    =  "";
    String  v_lecture   =  "";
    String  v_lecturedate= "";
    String  v_biyong_view = "";
    String  v_studentlimit_view  = "";
    String  v_isoutsourcing  ="";
    String  v_conturl        = "";
    int     v_discount = box.getInt("p_discount");

    int     i           =  0;

    int v_totalmil = 0;
    v_totalmil = HomePageMileageBean.TotalMileage(box);

    v_preurl               =  EduEtc1Bean.make_previewURL(v_subj);
    String v_hasPreviewObj = EduEtc1Bean.hasPreviewObj(v_subj);

    String  s_userid = box.getSession("userid");
    String  s_jikwi  = box.getSession("jikwi");
    String  v_prop_check = "";

    String v_classname  = "";
    String v_eduperiod  = "";


    Hashtable outputData = new Hashtable();

    DataBox dbox = (DataBox)request.getAttribute("selectEduBill");

    if(dbox != null)
    {
        v_subjseq       = dbox.getString("d_subjseq");
        v_subjnm        = dbox.getString("d_subjnm");
        v_studentlimit  = dbox.getInt("d_studentlimit");
        v_propstart     = dbox.getString("d_propstart");
        v_propend       = dbox.getString("d_propend");
        v_edustart      = dbox.getString("d_edustart");
        v_eduend        = dbox.getString("d_eduend");
        v_biyong        = dbox.getInt("d_biyong");
        v_scbiyong      = dbox.getInt("d_scbiyong");
        v_musername     = dbox.getString("d_name");
        v_bookprice     = dbox.getInt("d_bookprice");

        v_course        = dbox.getString("d_course");
        v_courseseq     = dbox.getString("d_courseseq");
        v_coursenm      = dbox.getString("d_coursenm");
        v_iscourseYn    = dbox.getString("d_isbelongcourse");

        if(v_iscourseYn.equals("Y"))
        {
            v_classname = v_coursenm;

            DataBox dbox2 = KProposeCourseBean.getBill(v_course, v_courseseq, v_year);

            if(dbox2 != null)
            {
                v_biyong        = dbox2.getInt("d_scbiyong");
                v_bookprice     = dbox2.getInt("d_bookprice");
            }
        }
        else
        {
            v_classname = v_subjnm;
        }

        if(v_discount == 0)
        {
            v_realpay = v_biyong ;
        }
        else
        {
            v_realpay = v_biyong - (v_biyong* v_discount / 100);
        }
    }


%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->
<script>

    //금액계산
    function rbo_change(oTarget)
    {
        var frm = document.form1;

        if(oTarget.value == "Y")
        {
            document.getElementById("v_realpay").innerHTML = <%=v_realpay%> + <%=v_bookprice %>;
            frm.p_realpay.value = <%=v_realpay%> + <%=v_bookprice %>;
        }
        else
        {
            document.getElementById("v_realpay").innerHTML = <%=v_realpay%>;
            frm.p_realpay.value = <%=v_realpay%>;
        }
    }

    // 등록체크 화면으로
    function input_bill(oFlag)
    {
        //var mil = document.form1.p_usemileage;
        //if(mil.value.length < 1)
        //{
           // mil.value = 0;
//        }
   //     if(mil.value > <%=v_totalmil%>)
      //  {
         //  alert("최대 사용 마일리지를 초과 하셨습니다");
            //mil.focus();
//            mil.select();
   //         return;
      //  }
   //     if(document.getElementById("rbo_mileage").value == "Y" && mil.value.length >= 1)
     //   {
      //      document.form1.p_realpay.value = document.form1.p_realpay.value - mil.value;
       // }
/*
        if(oFlag == 'D')
        {
            url = "/servlet/controller.propose.ProposeCourseServlet";
            var new_Open = window.open("","EduInputBill","scrollbars=auto,width=330,height=430,resizable=no");
            document.form1.target = "EduInputBill";
            document.form1.p_process.value = "SubjectEduInputCredit";
        }
        else if(oFlag == 'C')
        {
            url = "/servlet/controller.propose.KProposeCourseServlet";
            var new_Open = window.open("","EduInputBill","scrollbars=auto,width=700,height=300,resizable=no");
            document.form1.target = "EduInputBill";
            document.form1.p_process.value = "SubjectEduInputBill";
        }
*/
    	var farwindow = window.open("", "winInicis", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 920, height = 640, top=0, left=0");
    	document.form1.p_gubun.value = oFlag;
    	document.form1.target = "winInicis";
        document.form1.action="/inicis/INIsecurepayform_kocca.jsp";
        document.form1.submit();

        farwindow.window.focus();
    }

    //숫자만 입력 가능
    function numcheck(oTarget)
    {
        if(((event.keyCode < 48) || (event.keyCode > 57)) && ((event.keyCode < 96) || (event.keyCode > 105)))
            if((event.keyCode == 9) || (event.keyCode == 8) || (event.keyCode == 46))
                event.returnValue = true;
            else
                event.returnValue = false;
    }

    // 마일리지 사용여부
    function mileageView(oTarget)
    {
        if(oTarget.value == "Y")
        {
            document.getElementById("tr_dot").style.display = "";
            document.getElementById("tr_mileage").style.display = "";
        }
        else
        {
            document.getElementById("tr_dot").style.display = "none";
            document.getElementById("tr_mileage").style.display = "none";
        }
    }

    //결제할인 구분
    function dispriceChk(price) {
    	var disPrice = 0;
    	if(form1.discount[0].checked == true) {
    		disPrice = price;
    		document.getElementById("sp_disprice").innerHTML = price;
    	} else if(form1.discount[1].checked == true) {
    		disPrice = price - (price * 20 / 100);
    		document.getElementById("sp_disprice").innerHTML = disPrice;
    	}
    	form1.p_biyong.value = disPrice;
    	form1.p_realpay.value = disPrice;
    }
//-->
</SCRIPT>


<form name="form1" method="post" >
    <input type='hidden' name='p_process' value="<%=v_process%>" >
    <input type='hidden' name='p_select' value="<%=v_select%>">
    <input type='hidden' name='p_gubun' value="<%=v_gubun%>">
    <input type='hidden' name='p_biyong' value="<%=v_biyong%>">
    <input type='hidden' name='p_bookprice' value="<%=v_bookprice%>">
    <input type='hidden' name='p_discount' value="<%=v_discount%>">
    <input type='hidden' name='p_realpay' value="<%=v_realpay%>">

    <input type='hidden' name='p_subj' value="<%=v_subj%>">

    <input type='hidden' name='p_course' value="<%=v_course %>">
    <input type='hidden' name='p_courseseq' value="<%=v_courseseq %>">
    <input type='hidden' name='p_coursenm' value="<%=v_coursenm %>">
    <input type='hidden' name='p_iscourseYn' value="<%=v_iscourseYn%>">

    <input type='hidden' name='p_year' value="<%=v_year%>">
    <input type='hidden' name='userid'>
    <input type='hidden' name='p_subjseq' value="<%=v_subjseq%>">
    <input type='hidden' name='p_subjnm' value="<%=v_subjnm%>">
    <input type='hidden' name='p_rprocess' value="<%=v_rprocess%>">
    <input type='hidden' name='p_actionurl' value="<%=v_actionurl%>">
    <input type='hidden' name='p_purl' value="<%=v_purl%>">
    <input type='hidden' name='p_isonoff' value="<%=v_isonoff%>">
    <input type='hidden' name='p_upperclass' value="<%=v_upperclass%>">
    <input type='hidden' name='p_upperclassnm' value="<%=v_upperclassnm%>">
    <input type='hidden' name='p_lsearchsubject' value="<%=v_lsearchsubject%>">
    <input type='hidden' name='p_returnurl' value="/servlet/controller.propose.ProposeCourseServlet">

    <table width="680" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td  class="location" > HOME > 수강신청 &gt; 수강신청</td>
          </tr>
        </table>
        <table width="680" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="/images/user/kocca/apply/<%=tem_subimgpath%>/tit_definite.gif"></td>
          </tr>
          <tr>
            <td height="20"></td>
          </tr>
        </table>
        <table width="680" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="88" rowspan="3"><img src="/images/user/kocca/apply/ico_apply_pay.gif" width="88" height="40"></td>
            <td width="592" height="10"></td>
          </tr>
          <tr>
            <td height="11"><img src="/images/user/kocca/apply/text_apply_pay.gif" width="216" height="11"></td>
          </tr>
          <tr>
            <td height="12"></td>
          </tr>
          <tr >
            <td height="1" colspan="2" background="/images/user/kocca/apply/dot_bg_9.gif"></td>
          </tr>
          <tr >
            <td height="16" colspan="2" ></td>
          </tr>
        </table>
        <table width="680" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><table width="680" border="2" cellspacing="0" cellpadding="3" style="border-collapse:collapse;" bordercolor="#ededed"     >
                <tr>
                  <td height="4" class="linecolor_app"></td>
                  <td height="4" class="linecolor_app2"></td>
                  <td height="4" class="linecolor_app"></td>
                  <td height="4" class="linecolor_app2"></td>
                </tr>
                <tr>
                  <td class="tbl_otit">과정명</td>
                  <td class="tbl_gleft"><%=v_classname%></td>
                  <td class="tbl_otit2">차수</td>
                  <td class="tbl_gleft"><%=v_subjseq%></td>
                </tr>
                <tr>
                  <td width="102" class="tbl_otit">수강신청시작일</td>
                  <td width="230" class="tbl_gleft"><%=FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd")%></td>
                  <td width="123" class="tbl_otit2">수강신청종료일</td>
                  <td width="229" class="tbl_gleft"><%=FormatDate.getFormatDate(v_propend,"yyyy/MM/dd")%></td>
                </tr>
                <tr>
                  <td height="26" class="tbl_otit">교육시작일</td>
                  <td class="tbl_gleft"><%=FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")%></td>
                  <td class="tbl_otit2">교육종료일</td>
                  <td class="tbl_gleft"><%=FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")%></td>
                </tr>
                <tr>
                  <td class="tbl_otit">교육정원</td>
                  <td class="tbl_gleft"><%=v_studentlimit%>명</td>
                  <td class="tbl_otit2">주담당강사</td>
                  <td class="tbl_gleft"><%=v_musername%></td>
                </tr>
                <tr>
                  <td class="tbl_otit">할인구분</td>
                  <td class="tbl_gleft">
                  <input type=radio name=discount value="N" checked onClick="javascript:dispriceChk('<%=v_biyong%>');">일반회원
                  &nbsp;
                  <input type=radio name=discount value="Y" onClick="javascript:dispriceChk('<%=v_biyong%>')">할인회원
                  </td>
                  <td class="tbl_otit2">교육비</td>
                  <td class="tbl_gleft"><span id="sp_disprice"><%=v_biyong%></span></td>
                  <!--td class="tbl_otit2">교재비</td>
                  <td class="tbl_gleft"><%=v_bookprice%></td-->
                </tr>
                <tr>
                  <td class="tbl_gleft" colspan=4>
<font color=red>※ 수강료 입금 전 기업회원 및 기타 할인적용 여부를 확인해주시기 바랍니다.</font><br>
※ 교육비 할인혜택 대상자<br>
1. 기업회원:  20% 할인<br>
기업회원 등록방법: 홈페이지 회원가입 후 [기업회원 수강신청서 양식]을 작성하여 메일 및 팩스접수<br>
(사업자등록증 사본 첨부)<br>
[기업회원 수강신청서 양식 <a href="http://contents.connect.or.kr/servlet/controller.library.DownloadServlet?p_savefile=NoticeAdmin_file1_200906251826571_lee1.hwp&p_realfile=기업회원-가입신청서.hwp"><b>다운로드</b></a>]<br>
2. 기존 전문가 과정 및 워크숍 과정 수강생  : 20% 할인
<br>
<br>
자세한 사항은 운영자에게 문의주세요.<br>
■ 교육문의  Tel: 02.3219.6132,6133 /Fax : 02.3219.6501 / E-mail: cyber01@kocca.or.kr<br>
                  </td>
                </tr>
              </table>
             </td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
          </tr>
          <tr>
            <td>

			<!--table width="680" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/apply/card_box_bg.gif">
                <tr>
                  <td valign="top"><img src="/images/user/kocca/apply/bar_analysis_top.gif"></td>
                </tr>
                <tr>
                  <td align="center" background="/images/user/kocca/course/bg_box.gif"><table width="619" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="196"><img src="/images/user/kocca/apply/card_img.jpg"></td>
                        <td width="484" align="right" valign="middle">
                        <table width="409" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="100"><img src="/images/user/kocca/apply/card_text_02.gif"></td>
                                <td width="376" class="tbl_bleft">
                                    <input type="radio" name="rbo_1" value="Y" onClick="javascript:rbo_change(this)">
                                  포함
                                  <input type="radio" name="rbo_1" value="N" onClick="javascript:rbo_change(this)" checked>
                                  제외 </td>
                              </tr>
                             <tr >
                              <td height="1" colspan="2" background="/images/user/game/apply/card_line.gif"></td>
                            </tr>
                            <tr>
                              <td width="111"><img src="/images/user/kocca/apply/card_text_01.gif"></td>
                              <td width="298" class="tbl_bleft"><%=v_biyong%></td>
                            </tr>
                            <tr >
                              <td height="1" colspan="2" background="/images/user/game/apply/card_line.gif"></td>
                            </tr>
                            <!--tr>
                              <td><img src="/images/user/kocca/apply/card_text_03.gif"></td>
                              <td class="tbl_bleft"><%=v_discount %>%</td>
                            </tr>
                            <tr >
                              <td height="1" colspan="2" background="/images/user/game/apply/card_line.gif"></td>
                            </tr-->
                            <!--tr>
                                <td><img src="/images/user/kocca/apply/card_text_05.gif"></td>
                                <td class="tbl_bleft">
                                    <input type="radio" name="rbo_mileage" id="rbo_mileage" value="Y" onClick="javascript:mileageView(this)">사용
                                    <input type="radio" name="rbo_mileage" id="rbo_mileage" value="N" checked onClick="javascript:mileageView(this)">미사용
                                </td>
                              </tr>
                              <tr id="tr_dot" style="display:none;">
                                <td height="1" colspan="2" background="/images/user/game/apply/card_line.gif"></td>
                            </tr>
                              <tr id="tr_mileage" style="display:none;">
                                <td><img src="/images/user/kocca/apply/card_text_06.gif"></td>
                                <td class="tbl_bleft">
                                    <input type="text" name="p_usemileage" class="input2" onkeydown="javascript:return numcheck(this)"><br>사용가능한 최대 마일리지 : <%=v_totalmil %>
                                </td>
                              </tr>
                            <tr >
                              <td height="1" colspan="2" background="/images/user/game/apply/card_line.gif"></td>
                            </tr>
                            <tr>
                              <td><img src="/images/user/kocca/apply/card_text_04.gif"></td>
                              <td class="tbl_bleft"><div id="v_realpay"><%=v_realpay%></div></td>
                            </tr>
                          </table></td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
                  <td valign="bottom"><img src="/images/user/kocca/apply/bar_analysis_bottom.gif" width="680" height="11"></td>
                </tr>
              </table--></td>
          </tr>
          <tr>
            <td height="10"></td>
          </tr>
          <tr>
            <td height="10"><table width="166" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="56"><a href="javascript:input_bill('<%=v_gubun%>')"><img src="/images/user/kocca/button/btn_disprice.gif" border="0"></a></td>
                  <td width="6">&nbsp;</td>
                  <!--td width="56"><a href="javascript:input_bill('D')"><img src="/images/user/kocca/button/btn_card.gif" border="0"></a></td>
                  <td width="6">&nbsp;</td-->
                  <td><a href="javascript:history.back();"><img src="/images/user/kocca/button/btn_list.gif" border="0"></a></td>
                </tr>
              </table></td>
          </tr>
        </table>
                   <!-- 차수 끝 -->
</form>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->