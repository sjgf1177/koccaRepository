<%
/**
 * file name : za_ETestSubjMember_I.jsp
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
<%@ page import = "com.credu.etest.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    
    String  ss_upperclass= box.getString("s_upperclass");
    String  ss_etestsubj      = box.getString("s_etestsubj");
    String  ss_gyear       = box.getString("s_gyear");
    String  ss_etestcode    = box.getString("s_etestcode");
    String  v_etestsubjnm = box.getString("p_etestsubjnm");
    String  v_etesttext = box.getString("p_etesttext");
    String  v_action     = box.getString("p_action");
    String  v_process = box.getString("p_process");
    int  v_maxjoin = box.getInt("p_maxjoin");
  
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
  document.form2.p_process.value = 'SubjMemberTargetListPage';
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
function InsertETestMember(p_maxjoin) {
  var maxjoin = p_maxjoin;
  var chkselected = chkSelected();
  if (chkselected < 1) {
    alert('테스트에 추가할 학습자를 선택하세요');
    return;
  }
  if (chkselected > maxjoin) {
    alert('현재 정원초과입니다. 추가가능인원에 맞춰 대상자를 추가하십시요');
    return;
  }
  document.form2.p_process.value = 'ETestMemberInsert';
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
        if (v_subj=="----"){
            alert("과정을 선택하세요");
            return;
        }
        if (v_subjseq=="----"){
            alert("차수를 선택하세요");
            return;
        }
    }
  document.form2.p_process.value = 'SubjMemberTargetListPage';
  document.form2.p_action.value  = p_action;
  document.form2.submit();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <form name="form2" method="post" action="/servlet/controller.etest.ETestMemberServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="s_etestsubj"       value="<%=ss_etestsubj%>">
        <input type="hidden" name="p_etestsubjnm"       value="<%=v_etestsubjnm%>">
        <input type="hidden" name="p_etesttext"       value="<%=v_etesttext%>">
        <input type="hidden" name="s_gyear"       value="<%=ss_gyear%>">
        <input type="hidden" name="s_etestcode"    value="<%=ss_etestcode%>">
        <input type="hidden" name="s_upperclass"       value="<%=ss_upperclass%>">
        <input type="hidden" name="p_maxjoin"       value="<%=v_maxjoin%>">
	    <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/etest/test_title04.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	    <!----------------- title 끝 ----------------->
	    <br>
	    <!----------------- 과정, 연도, 시작시간, 차수, 소요시간 시작 ----------------->
      <table class="form_table_out" cellspacing="0" cellpadding="1">
        <tr>
          <td align="center">
            <table class="form_table_bg" cellspacing="0" cellpadding="0">
              <tr>
                <td height="7"></td>
              </tr>
              <tr> 
                <td align="center">
                  <table width="98%" cellspacing="0" cellpadding="0" class="form_table">
                    <tr>
                      <td width="15%" height="25">◈<b> 테스트그룹</b></td>
                      <td align="left">: &nbsp;&nbsp;<%=v_etestsubjnm%> (<%=ss_etestsubj%>)</td>
                      <td width="15%">◈<b> 연도</b></td>
                      <td align="left">: &nbsp;&nbsp;<%=ss_gyear%></td>
                    </tr>
                    <tr>
                      <td width="15%">◈<b> 테스트명</b></td>
                      <td align="left">: &nbsp;&nbsp;<%=v_etesttext%></td>
                      <td width="15%">◈<b> 추가가능 인원수</b></td>
                      <td align="left">: &nbsp;&nbsp;<%=v_maxjoin%></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="7"></td>
              </tr>
              <tr> 
                <td align="center"> 
                  <table class="form_table" cellspacing="0" cellpadding="0" width="99%">
                    <tr> 
                      <td>
                          <font color="red">★</font> <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
	                     <%= SelectEduBean.getGyear(box, true )%><!-- getGyear(RequestBox, isChange)   해당연도  -->
                         <%= SelectSubjBean.getUpperClass(box, true, true, false)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
        				 <%= SelectSubjBean.getMiddleClass(box, true, true, false)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
				         <%= SelectSubjBean.getLowerClass(box, true, true, false)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
                      </td>
                    </tr>
                    <tr> 
                      <td>
          				  <font color="red">★</font> <%= SelectSubjBean.getSubj(box, true, false)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
				          <font color="red">★</font> <%= SelectSubjBean.getSubjseq(box, true, false)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                          &nbsp; <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
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
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="6" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title">회사</td>
          <td class="table_title">사번(ID)</td>
          <td class="table_title">성명</td>
          <td class="table_title">부서</td>
          <td class="table_title">직위</td>          
          <td class="table_title">등록</td>
        </tr>
<%  
     if(!v_process.equals("ETestSubjMemberInsertPage")) {
         ArrayList list = (ArrayList)request.getAttribute("SubjMemberTargetList");
            for (int i=0; i<list.size(); i++) {
                DataBox dbox  = (DataBox)list.get(i); %>
        <tr>
          <td class="table_02_1"><%=dbox.getString("d_companynm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_cono")%>(<b><%=dbox.getString("d_userid")%></b>)</td>
          <td class="table_02_1"><%=dbox.getString("d_name")%></td>
          <td class="table_02_1"><%=dbox.getString("d_deptnam")%></td>
          <td class="table_02_1"><%=dbox.getString("d_jikwinm")%></td>          
          <td class="table_02_1">
            <input type="checkbox" name="p_checks" value="<%=dbox.getString("d_userid")%>" checked>
          </td>
        </tr>
<%      }
      }  %>
      </form>   
      </table>
	  <br><br>
      <!----------------- 개인별 등록 끝 ----------------->
      <!----------------- 등록, 닫기 버튼 시작 ----------------->
      <table cellspacing="0" cellpadding="0">
        <tr> 
          <td><a href="javascript:InsertETestMember('<%=v_maxjoin%>')"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
          <td width="5"></td>
		  <td><a href='javascript:self.close()'><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
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
