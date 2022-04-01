<%
/**
 * file name : za_DamunCommonTargetMember_I.jsp
 * date      : 2003/09/25
 * programmer:
 * function  : 온라인테스트 응시자 과정별 등록
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }
    
    String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

    String  ss_grcode       = box.getString("s_grcode");
	String  ss_gyear       = box.getString("s_gyear");
	int  ss_damunpapernum       = box.getInt("s_damunpapernum");
    String  ss_subjseq    = box.getString("s_subjseq");

	String  v_action     = box.getString("p_action");
    String  v_process = box.getString("p_process");
    String  v_mailgubun = box.getString("p_mailgubun");

	int  v_flag       = box.getInt("p_flag");

    String  v_upperclass  = "";
    String  v_middleclass = "";
    String  v_lowerclass  = "";
    String  v_subjcourse  = "";

    String  v_grcode       = "";
	String  v_gyear       = "";
	int  v_damunpapernum       = 0;
    String  v_subjseq    = "";

if(v_flag==1){
      v_upperclass  = box.getStringDefault("s_upperclass","ALL");
      v_middleclass = box.getStringDefault("s_middleclass","ALL");
      v_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
      v_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

      v_grcode       = box.getString("s_grcode");
	  v_gyear       = box.getString("s_gyear");
	  v_damunpapernum       = box.getInt("s_damunpapernum");
      v_subjseq    = box.getString("s_subjseq");
 }else{
      v_upperclass  = box.getStringDefault("p_upperclass","ALL");
      v_middleclass = box.getStringDefault("p_middleclass","ALL");
      v_lowerclass  = box.getStringDefault("p_lowerclass","ALL");
      v_subjcourse  = box.getStringDefault("p_subj","ALL");

      v_grcode       = box.getString("p_grcode");
	  v_gyear       = box.getString("p_gyear");
	  v_damunpapernum       = box.getInt("p_damunpapernum");
      v_subjseq    = box.getString("p_subjseq"); 
 }	
  
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
function MemberSearch(p_action) {
  if (p_action == 'go') {
    if (chkData() == false) {
      return; 
    }
  }
  document.form2.p_process.value = 'CommonMemberTargetListPage';
  document.form2.p_action.value  = p_action;
  document.form2.submit();
}
function chkData() {
  if (blankCheck(document.form2.s_searchtext.value)) {
    alert('검색어를 입력하십시요.');
    document.form2.s_searchtext.focus();
    return false;
  }
}
function InsertDamunMember() {

  var chkselected = chkSelected();
  if (chkselected < 1) {
    alert('대상자를 선택하세요');
    return;
  }
  document.form2.p_process.value = 'DamunMemberInsert';
  document.form2.p_action.value  = 'insert';
  document.form2.submit();
}
function chkSelected() {
  var selectedcnt = 0;
  if(document.form2.all['p_checks'] == '[object]') {
    if (document.form2.p_checks.length > 0) {
      for (i=0; i<document.form2.p_checks.length; i++) {
        if (document.form2.p_checks[i].checked == true) {
	      selectedcnt++;
	    }  
      }
    } else {
      if (document.form2.p_checks.checked == true) {
	    selectedcnt++;
      }
    }
  }
  return selectedcnt; 
}
function whenSelection(p_action) {
    var v_grcode, v_subj, v_subjseq;

	v_grcode        = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
    v_subj        = document.form2.s_subjcourse.value;
    v_subjseq        = document.form2.s_subjseq.value;

	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
    if (p_action=="go") {
        if (v_subj=="ALL"){
            alert("과정을 선택하세요");
            return;
        }
        if (v_subjseq=="ALL"){
            alert("차수를 선택하세요");
            return;
        }
    }
  document.form2.p_process.value = 'SubjMemberTargetListPage';
  document.form2.p_action.value  = p_action;
  document.form2.submit();
}
function onload() {
  window.self.close();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" <%=v_onload%>>
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

	    <!----------------- title 시작 ----------------->
      <table width="95%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/multiestimate/m_pop01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  <!----------------- title 끝 ----------------->
	    <br>
	    <!----------------- 과정, 연도, 시작시간, 차수, 소요시간 시작 ----------------->
	    <table cellspacing="0" cellpadding="1" class="open_form_table_out">
      <form name="form2" method="post" action="/servlet/controller.multiestimate.DamunCommonMemberServlet";>
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="s_damunpapernum"       value="<%=v_damunpapernum%>">
        <input type="hidden" name="p_grcode"       value="<%=v_grcode%>">
        <input type="hidden" name="p_subj"       value="<%=v_subjcourse%>">
        <input type="hidden" name="p_gyear"       value="<%=v_gyear%>">
        <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
        <input type="hidden" name="p_upperclass"       value="<%=v_upperclass%>">
        <input type="hidden" name="p_damunpapernum"       value="<%=v_damunpapernum%>">
        <input type="hidden" name="p_mailgubun"       value="<%=v_mailgubun%>">

	      <tr> 
	        <td bgcolor="#C6C6C6" align="center"> 
	          <table cellspacing="0" cellpadding="0" class="form_table_bg">

              <tr> 
                <td height="7"></td>
              </tr>
              <tr> 
                <td align="center"> 
                  <table class="form_table" cellspacing="0" cellpadding="0" width="99%">
                    <tr> 
                      <td>
			                       <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
	                               <%= SelectEduBean.getGyear(box, true )%><!-- getGyear(RequestBox, isChange)   해당연도  -->
                                   <%= SelectSubjBean.getUpperClass(box, true, true, false)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
                				   <%= SelectSubjBean.getMiddleClass(box, true, true, false)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
				                   <%= SelectSubjBean.getLowerClass(box, true, true, false)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
				                   <%= SelectSubjBean.getSubj(box, true, false)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
				                   <%= SelectSubjBean.getSubjseq(box, true, false)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                         <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="7"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!----------------- 과정, 연도, 시작시간, 차수, 소요시간 끝 ----------------->
      <br>
      <!----------------- 분류 시작 ----------------->
      <table width="98%" cellpadding="0" cellspacing="0" class="table1">

        <tr> 
          <td height="7"></td>
        </tr>
      </table>
      <!----------------- 분류 끝 ----------------->
      <!----------------- 개인별 등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="open_table_out">
        <tr> 
          <td colspan="6" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title">소속</td>
          <td class="table_title">직위</td>
          <td class="table_title">ID</td>
          <td class="table_title">사번</td>
          <td class="table_title">성명</td>
          <td class="table_title">등록</td>
        </tr>
<%  
     if(!v_process.equals("DamunSubjMemberInsertPage")) {
         ArrayList list = (ArrayList)request.getAttribute("SubjMemberTargetList");
            for (int i=0; i<list.size(); i++) {
                DataBox dbox  = (DataBox)list.get(i); %>
        <tr>
          <td class="table_02_1"><%=dbox.getString("d_asgnnm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_jikwinm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_cono")%></td>
          <td class="table_02_1"><%=dbox.getString("d_userid")%></td>
          <td class="table_02_1"><%=dbox.getString("d_name")%></td>
          <td class="table_02_1">
            <input type="checkbox" name="p_checks" value="<%=dbox.getString("d_userid")%>">
          </td>
        </tr>
<%      }
      }  %>
      </form>   
      </table>
      <!----------------- 개인별 등록 끝 ----------------->
      <!----------------- 등록, 닫기 버튼 시작 ----------------->
      <table width=95% cellspacing="0" cellpadding="0" class="table1">
        <tr> 
          <td align="right"><a href="javascript:InsertDamunMember('')"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록, 닫기 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
