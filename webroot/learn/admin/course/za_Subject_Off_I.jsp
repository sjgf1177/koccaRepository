<%
//**********************************************************
//  1. 제      목: 집합과정등록 화면
//  2. 프로그램명: za_Subject_Off_I.jsp
//  3. 개      요: 집합과정등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 12
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "code" class = "com.credu.system.CodeConfigBean"  scope = "page" />
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse= box.getString("s_subjcourse");       //과정
    String  ss_subjseq   = box.getString("s_subjseq");          //과정 차수
    //DEFINED in relation to select END
    String  p_grcode        = box.getString("p_grcode");                    //교육그룹
    String  v_sgkind        = box.getStringDefault("p_proposetype","");    //수강신청방식
%>
<html>
<head>
<title>집합과정등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--


function whenRndjijung() {
    if (document.form2.p_rndjijung.checked) {
        //지정과정인 경우
        document.form2.p_rndcreditadd.disabled = false;
        document.form2.p_rndcreditdeduct.disabled = false;
        document.form2.p_rndcreditreq.disabled = true;
        document.form2.p_rndcreditchoice.disabled = true;
        document.form2.p_rndcreditreq.value = 0;
        document.form2.p_rndcreditchoice.value = 0;
    } else {
        //지정과정이 아닌 경우
        document.form2.p_rndcreditadd.value = 0;
        document.form2.p_rndcreditdeduct.value = 0;
        document.form2.p_rndcreditadd.disabled = true;
        document.form2.p_rndcreditdeduct.disabled = true;
        document.form2.p_rndcreditreq.disabled = false;
        document.form2.p_rndcreditchoice.disabled = false;
    }
}

function whenIsuse() {
    //과정사용여부
    if (document.form2.p_isuse.value=="Y") {
        document.form2.p_isvisible.disabled = false;
    } else {
        document.form2.p_isvisible.disabled = true;
        document.form2.p_isvisible.value = "N";
    }
}

function InsertSubject() {
    if(!chkData()) {
      return;
    }
    document.form2.s_action.value = "go";
    document.form2.p_process.value = 'insertOff';
    document.form2.p_specials.value = document.form2.p_new.value + document.form2.p_hit.value + document.form2.p_recom.value;
    document.form2.submit();
}
function goSubjectListPage() {
    document.form2.s_action.value = "go";
    document.form2.p_process.value  = 'listPage';
    document.form2.submit();
}
function chkData() {
    ff = document.form2;

  if (document.form2.p_subj.value=='ALL' || document.form2.p_subj.value=='') { 
    alert('과정코드를 선택하세요.');
    return false;
  }

  if (document.form2.p_upperclass.value=='ALL' || document.form2.p_upperclass.value=='') { 
    alert('대분류를 선택하세요.');
    return false;
  }
  
  if (document.form2.p_middleclass.value=='ALL' || document.form2.p_middleclass.value=='') { 
    alert('중분류를 선택하세요.');
    return false;
  }
  if (document.form2.p_lowerclass.value=='ALL' || document.form2.p_middleclass.value=='') { 
    document.form2.p_lowerclass.value='000';
  }
/*
  if (document.form2.p_lowerclass.value=='ALL' || document.form2.p_lowerclass.value=='') { 
    alert('소분류를 선택하세요.');
    return false;
  }
*/


  if (!number_chk_noalert(document.form2.p_biyong.value)) {
    alert('수강료가 잘못입력되었습니다.');
    return false;
  }
  
  if (!number_chk_noalert(document.form2.p_edudays.value)) {
    alert('교육기간이 잘못입력되었습니다.');
    return false;
  }

  if (!number_chk_noalert(document.form2.p_edutimes.value)) {
    alert('교육시간이 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_studentlimit.value)) {
    alert('정원이 잘못입력되었습니다.');
    return false;
  }

  if (!number_chk_noalert(document.form2.p_point.value)) {
    alert('이수점수가 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_gradscore.value)) {
    alert('이수기준-점수가 잘못입력되었습니다.');
    return false;
  }

  if (!number_chk_noalert(document.form2.p_gradstep.value)) {
    alert('이수기준-출석률이 잘못입력되었습니다.');
    return false;
  }
  
  if (!number_chk_noalert(ff.p_gradexam.value)) {
          alert('이수기준-평가가 잘못입력되었습니다.');
          return false;
        }

        
        if (!number_chk_noalert(ff.p_gradreport.value)) {
          alert('이수기준-리포트가 잘못입력되었습니다.');
          return false;
        }
        if (ff.p_gradscore.value > 100) {
            alert('이수기준-점수가 100 이상입니다.');
            return false;
        }
        if (ff.p_gradstep.value > 100) {
            alert('이수기준-진도율이 100 이상입니다.');
            return false;
        }
        if (ff.p_gradexam.value > 100) {
            alert('이수기준-평가가 100 이상입니다.');
            return false;
        }
        if (ff.p_gradreport.value > 100) {
            alert('이수기준-리포트가 100 이상입니다.');
            return false;
        }
        
        
  if (!number_chk_noalert(document.form2.p_wstep.value)) {
    alert('가중치-출석률(%)이 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_wftest.value)) {
    alert('가중치-평가(%)가 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_wreport.value)) {
    alert('가중치-리포트(%)가 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_wetc1.value)) {
    alert('가중치-기타(%)가 잘못입력되었습니다.');
    return false;
  }
  var weight = parseFloat(document.form2.p_wstep.value,10) + parseFloat(document.form2.p_wftest.value,10) + parseFloat(document.form2.p_wreport.value,10) + parseFloat(document.form2.p_wetc1.value,10);
  if (weight < 0) {
    alert('가중치가 0% 보다 적습니다');
    return false;
  } 
  
  if (weight > 100) {
    alert('가중치가 100% 보다 많습니다.');
    return false;
  }
  if (blankCheck(document.form2.p_subjnm.value)) {
    alert('과정명을 입력하십시요.');
    document.form2.p_subjnm.focus();
    return false;
  }
  return true;
}
function searchMuser() {
  var p_gubun = 'name';
  var p_key1  = document.form2.p_museridnm.value;
  var url = "/servlet/controller.library.SearchServlet?p_process=member&p_gubun="+ p_gubun +"&p_key1="+ p_key1 +"&p_key2=";
  open_window("",url,"0","0","100","100");
}
// 멤버 검색 후 처리
function receiveMember(userid, name, resno, email, cono, jikwi, jikwinm, comp, compnm, tmp1, tmp2, tmp3){
  document.form2.p_museridnm.value = name;
  document.form2.p_muserid.value   = userid;
  document.form2.p_musertel.value   = tmp3;
}

function whenSelection() {
    document.form2.p_process.value = 'insertOffPage';
    //대분류 선택시 중분류를 ALL로 초기화
    document.form2.s_middleclass.value = '';
    document.form2.submit();
}

function whenIsgoyong() {
    //고용보험 사용여부체크
    if (document.form2.p_isgoyong.value=="Y") {
        document.form2.p_goyongprice_major.disabled = false;
//        document.form2.p_goyongprice_minor.disabled = false;
    } else {
        document.form2.p_goyongprice_major.disabled = true;
//		document.form2.p_goyongprice_minor.disabled = true;
        document.form2.p_goyongprice_major.value = 0;
//        document.form2.p_goyongprice_minor.value = 0;
    }
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>

<form name="form2" method="post" action="/servlet/controller.course.SubjectServlet"  enctype="multipart/form-data">
    <input type="hidden" name="p_process"     value="">
    <input type="hidden" name="s_action">
    
    <input type="hidden" name="s_grcode"        value="<%=ss_grcode%>">     <!--in relation to select-->
    <input type="hidden" name="s_gyear"         value="<%=ss_gyear%>">      <!--in relation to select-->
    <input type="hidden" name="s_upperclass"    value="<%=ss_upperclass%>"> <!--in relation to select-->
    <input type="hidden" name="s_middleclass"   value="<%=ss_middleclass%>"><!--in relation to select-->
    <input type="hidden" name="s_lowerclass"    value="<%=ss_lowerclass%>"><!--in relation to select-->
    <input type="hidden" name="s_subjcourse"    value="<%=ss_subjcourse%>"> <!--in relation to select-->
    <input type="hidden" name="s_subjseq"       value="<%=ss_subjseq%>">    <!--in relation to select-->

    <input type="hidden" name="p_grcode"      value="<%=p_grcode%>">
    <input type="hidden" name="p_isonoff"     value="OFF">
    <input type="hidden" name="p_specials"    value="">
    <input type="hidden" name="p_owner"       value="0101000000">
    <input type="hidden" name="p_producer"    value="0101000000">

<table width="1024" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
        <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/c_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>      <!----------------- title 끝 ----------------->
      <%=box.getString("p_warnmsg")%><br>
      <br>
        <!----------------- 과정 등록/수정/삭제/상세보기 (집합) 시작 ----------------->
        <table cellspacing="1" cellpadding="5" class="table_out" width=970>
          <tr> 
            <td colspan="7" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="7%"  rowspan="29" class="table_title"><b>과정정보</b></td>
            <td colspan="6" class="dir_txt02" >(*) 표시는 필수 입력입니다.</td>
          </tr>
          <tr> 
            <td class="table_01"  width="13%">교육구분</td>
            <td colspan="5" class="table_02_2" width="30%">집합 / 과정코드 : <input type="text" class="input" name="p_subj" size="10" maxlength="10" value="" >
              <input type="hidden" name="p_point" value="100"></td>
          </tr>
          <tr> 
            <td class="table_01">과정명<font color="red">(*)</font></td>
            <td width="30%" class="table_02_2"  ><input type="text" class="input" name="p_subjnm" size="38" maxlength="100" value="" tabindex=1></td>
            <td width="7%"  rowspan="9" class="table_title"><b>점수정보</b></td>
            <td width="3%" rowspan="4" class="table_01">이<br>수<br>기<br>준 </td>
            <td width="10%"   class="table_01">총 점</td>
            <td width="30%"   class="table_02_2"  ><input type="text" class="input" name="p_gradscore" size="10" maxlength="5" value="0" tabindex=30></td>
          </tr>
          <tr> 
            <td class="table_01">과정분류<font color="red">(*)</font></td>
            <td class="table_02_2"> <iframe name="isubjclass" width="250" height="75"   frameborder="0"  src="/servlet/controller.library.SearchServlet?p_process=subjclass&p_formname=form2"></iframe> 
              <input type="hidden" name="p_upperclass"> <input type="hidden" name="p_middleclass"> <input type="hidden" name="p_lowerclass"> 
              <!--수강신청방식 Default 일반신청(1)-->
              <input type="hidden" name="p_proposetype" value="1"> </td>
            <td class="table_01">출석률</td>
            <td class="table_02_2"  ><input type="text" class="input" name="p_gradstep" size="10" maxlength="5" value="0" tabindex=31></td>
          </tr>

          <tr> 
            <td class="table_01">과정특성</td>
            <td class="table_02_2">신규 
              <select name="p_new"  class="inputpsearch" tabindex=14>
                <option value='Y'>Y</option>
                <option value='N' selected>N</option>
              </select>
              HIT
              <select name="p_hit"  class="inputpsearch" tabindex=15>
                <option value='Y'>Y</option>
                <option value='N' selected>N</option>
              </select> 
              추천 
              <select name="p_recom"  class="inputpsearch" tabindex=15>
                <option value='Y'>Y</option>
                <option value='N' selected>N</option>
              </select> </td>

            <td class="table_01">평&nbsp; 가</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_gradexam" size="10" maxlength="5" value="0" tabindex=32></td>
          </tr>
          <tr> 
            <td rowspan="3" class="table_01">담당교수<font color="red">(*)</font></td>
            <td    height="34" class="table_02_2" > I&nbsp;&nbsp;&nbsp;D: 
              <input name="p_muserid" type="text" class="input" size="14" maxlength=13 value=""  tabindex=16></td>

            <td class="table_01">리포트</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_gradreport" size="10" maxlength="5" value="0" tabindex=33></td>
          </tr>
          <tr> 
            <td class="table_02_2"  > <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="51%">이&nbsp;&nbsp;&nbsp;름:&nbsp; <input name="p_museridnm" type="text" class="input" size="14" maxlength=13 value="" tabindex=17></td>
                  <td width="49%"><a href="javascript:searchMuser()"><img src="/images/admin/button/b_resarchbutt.gif"  border="0" tabindex=18></a></td>
                </tr>
              </table></td>

            <td rowspan="5" class="table_01">가<br>중<br>치<br>(%)<br> </td>
            <td class="table_01">출석률</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_wstep" size="10" maxlength="5" value="0" tabindex=34></td>
          </tr>
          <tr> 
            <td class="table_02_2"  >연락처: 
              <input name="p_musertel" type="text" class="input" size="22" maxlength=40 value="<%=box.getSession("comptel")%>" tabindex=19> 
            </td>
            <td class="table_01">평 가</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_wftest" size="10" maxlength="5" value="0" tabindex=35></td>
          </tr>
          <tr> 
            <td height="31" class="table_01">과정사용여부</td>
            <td class="table_02_2"  > <select name="p_isuse"  class="inputpsearch" onChange="whenIsuse()" tabindex=20>
                <option value='Y' selected>Y</option>
                <option value='N'>N</option>
              </select> </tr>
            <td class="table_01">리포트</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_wreport" size="10" maxlength="5" value="0" tabindex=36></td>
          </tr>
          <tr> 
            <td class="table_01">학습자에게 보여주기</td>
            <td class="table_02_2" ><select name="p_isvisible"  class="inputpsearch" tabindex=21>
                <option value='Y' selected>Y</option>
                <option value='N'>N</option>
              </select></td>
            <td class="table_01">기 타</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_wetc1" size="10" maxlength="5" value="0" tabindex=37></td>
          </tr>
          <tr> 
            <td class="table_01">과정소개사용여부</td>
            <td class="table_02_2"> 
              <select name="p_isintroduction"  class="inputpsearch" tabindex=28>
                <option value='Y' selected>Y</option>
                <option value='N'>N</option>
              </select> 
            </td>
            <td colspan="2" class="table_01">가중치(%)의 합은 100이어야 합니다.</td>
          </tr>
          <tr> 
            <td class="table_01">수강료</td>
            <td class="table_02_2"><input type="text" class="input" name="p_biyong" size="10" maxlength="8" value="0" tabindex=22>
              원</td>
            <td colspan="4" rowspan="11" class="table_title">&nbsp;</td>
          </tr>
          <tr> 
            <td class="table_01">교육기간</td>
            <td class="table_02_2"><input type="text" class="input" name="p_edudays" size="5" maxlength="5" value="0" tabindex=22>
              일</td>
          </tr>
          <tr> 
            <td class="table_01">교육시간</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_edutimes" size="5" maxlength="5" value="0" tabindex=23>
              시간</td>
          </tr>
          <tr>   
            <td class="table_01">집합장소</td>
            <td class="table_02_2"><input type="text" class="input" name="p_placejh" size="38" maxlength="100" value="" tabindex=24></td>
          </tr>
          <tr> 
            <td class="table_01">교육형태</td>
            <td class="table_02_2" ><%= code.getCodeSelect("edutype","p_edutype"," tabindex=25","") %></td>
          </tr>
          <tr> 
            <td  class="table_01">차수당 정원</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_studentlimit" size="10" maxlength="5" value="0" tabindex=26></td>
          </tr>
          <tr> 
            <td  class="table_01">교재/첨부파일</td>
            <td class="table_02_2" ><input type="hidden" class="input" name="p_bookname" size="38" maxlength="100" value=""> 
              <input type="file" class="input" name="p_file" size="30" maxlength="100" value="" tabindex=27> 
            </td>
          </tr>
          <tr>
            <td class="table_01">고용보험여부</td>
            <td class="table_02_2"> 
              <select name="p_isgoyong"  class="inputpsearch" onChange="whenIsgoyong()" tabindex=28>
                <option value='Y' >Y</option>
                <option value='N' selected>N</option>
              </select> 
            </td>
          </tr>
          <tr> 
            <td class="table_01">환급금액</td>
            <td class="table_02_2" >
              <input type="text" class="input" name="p_goyongprice_major" size="15" maxlength="10" value="" disabled tabindex=36> 원<br>
<!--             중소기업 <input type="text" class="input" name="p_goyongprice_minor" size="15" maxlength="10" value="" disabled tabindex=36> 원 -->
            </td>
          </tr>
<!--
          <tr> 
            <td class="table_01">전사교육여부</td>
            <td class="table_02_2" ><select name="p_isalledu"  class="inputpsearch" tabindex=28>
                <option value='Y'>Y</option>
                <option value='N' selected>N</option>
              </select> &nbsp;(R&D 분류에 표시)</td>
          </tr>
-->
        </table>
        <br>
        <!----------------- 과정맛보기 등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td width="16%" class="table_title"><b>소개이미지</b></td>
          <td width="81%" class="table_02_2">
            <input name="p_introducefile" type="file" class="input" size="118" maxlength="100" tabindex=65>
          </td>
        </tr>
        <tr>
          <td width="16%" class="table_title"><b>파일</b></td>
          <td width="81%" class="table_02_2">
            <input name="p_informationfile" type="file" class="input" size="118" maxlength="100" tabindex=65>
          </td>
        </tr>
        <tr>
          <td width="16%" class="table_title"><b>교육대상</b></td>
          <td width="81%" class="table_02_2">
            <textarea name="p_edumans" cols="100" rows="3" tabindex=40></textarea>
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>교육목적</b></td>
          <td class="table_02_2">
            <textarea name="p_intro" cols="100" rows="3" tabindex=41></textarea>
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>교육내용</b></td>
          <td class="table_02_2">
            <textarea name="p_explain" cols="100" rows="5" tabindex=42></textarea>
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>강의교재 및 참고문헌</b></td>
          <td class="table_02_2">
            <textarea name="p_memo" cols="100" rows="5" tabindex=67></textarea>
          </td>
        </tr>
      </table>
      <!----------------- 과정맛보기 등록 끝 ----------------->
      <br>
      <!----------------- 등록, 리스트 버튼 시작 ----------------->
      <table  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><a href="javascript:InsertSubject()"><img src="/images/admin/button/btn_copen.gif" border="0" tabindex=43></a></td>
          <td width=8></td>
          <td><a href="javascript:goSubjectListPage()"><img src="/images/admin/button/btn_list.gif" border="0" tabindex=44></a></td>
        </tr>
      </table>
      <!----------------- 등록, 리스트 버튼 끝 ----------------->
      <br>
        </td>
    </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
