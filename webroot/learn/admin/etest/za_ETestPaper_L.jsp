<%
/**
 * file name : za_ETestPaper_L.jsp
 * date      : 
 * programmer:
 * function  : 평가마스터 조회
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
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
    
    String  ss_grcode    = box.getString("s_grcode");        //교육그룹
    String  ss_gyear    = box.getString("s_gyear");       
	String  ss_upperclass= box.getStringDefault("s_upperclass", "ALL");
    String  ss_etestsubj    = box.getString("s_etestsubj");       
    String p_action = box.getString("p_action");    
    if(p_action.equals("go")) ss_etestsubj = box.getString("s_etestsubj");

    String  ss_etestcode    = box.getString("s_etestcode");

%>
<html>
<head>
<title>E-Test 시험지 조회</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
// 테스트지 추가 
function InsertPaperPage(p_etestsubj, p_gyear, p_etestcode) {
  document.form1.p_process.value = 'ETestPaperInsert';
  document.form1.p_etestsubj.value    = p_etestsubj;
  document.form1.p_gyear.value    = p_gyear;
  document.form1.p_etestcode.value = p_etestcode;
  document.form1.p_action.value = 'go';
  document.form1.submit();
}

// 미리보기
function PreviewPaper(p_etestsubj, p_gyear, p_etestcode, p_etestnum, p_etestsubjnm) {
  window.self.name = "winExamPreview";
  farwindow = window.open("", "openExamPreview", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=800, height=600, top=0, left=0");

  document.form1.target = "openExamPreview";
  document.form1.action = "/servlet/controller.etest.ETestPaperServlet";
  document.form1.p_process.value = "ETestPaperPreviewPage";
  document.form1.p_etestsubj.value    = p_etestsubj;
  document.form1.p_gyear.value    = p_gyear;
  document.form1.p_etestcode.value = p_etestcode;
  document.form1.p_etestnum.value  = p_etestnum;
  document.form1.p_etestsubjnm.value   = p_etestsubjnm;
 
  document.form1.submit();
  farwindow.window.focus();
  document.form1.target = window.self.name;
}

// 조회 검색
function whenSelection(p_action) {
    var v_grcode, v_subj, v_subjseq;

	v_grcode        = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;
    v_etestsubj        = document.form1.s_etestsubj.value;
    v_etestcode        = document.form1.s_etestcode.value;

	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (p_action=="go") {
        if (v_etestsubj=="0"){
            alert("테스트그룹을 선택하세요");
            return;
        }
        if (v_etestcode==0){
            alert("테스트를 선택하세요");
            return;
        }
        top.ftop.setPam();
    }
  document.form1.p_process.value = 'ETestPaperListPage';
  document.form1.p_action.value  = p_action;
  document.form1.action="/servlet/controller.etest.ETestPaperServlet";
  document.form1.submit();
}

function SelectMemberPage(p_subj, p_upperclass, p_year, p_subjseq) {
  document.form1.p_process.value = 'ETestMemberListPage';
  document.form1.p_action.value  = 'go';
  document.form1.s_subj.value    = p_subj;
  document.form1.s_upperclass.value    = p_upperclass;
  document.form1.s_year.value    = p_year;
  document.form1.s_subjseq.value = p_subjseq;
  document.form1.action="/servlet/controller.etest.ETestMemberServlet";
  document.form1.submit();
}
function ReloadPage(p_action) {
  document.form1.p_process.value = 'ETestMasterListPage';
  document.form1.p_action.value  = p_action;
  document.form1.submit();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name="form1" method="post">
    <input type="hidden" name="p_process" value="">
    <input type="hidden" name="p_action"  value="">
    <input type="hidden" name="p_etestsubj"    value="">
	<input type="hidden" name="p_gyear"    value="">
	<input type="hidden" name="p_etestcode"    value="">
	<input type="hidden" name="p_etestnum"    value="">
	<input type="hidden" name="p_etestsubjnm"    value="">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/etest/test_title05.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- form 시작 ----------------->
      <table class="form_table_out" cellspacing="0" cellpadding="1">
        <tr>
          <td align="center">
            <table class="form_table_bg" cellspacing="0" cellpadding="0">
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
						 분류<%=CodeConfigBean.getCodeGubunSelect (ETestBean.ETSUBJ_CLASS, "", 1, "s_upperclass", ss_upperclass, "onchange=javascript:whenSelection('change')", 2)%>
                          <font color="red">★</font> 그룹<%=ETestQuestionBean.getGroupSelect (ss_grcode, ss_upperclass, "s_etestsubj", ss_etestsubj, "onchange=javascript:whenSelection('change')")%>
                          <font color="red">★</font>  테스트명<%= ETestMemberBean.getETestCode(ss_etestsubj, ss_gyear, "s_etestcode", ss_etestcode, "")%>&nbsp;<% //-- getSubjseq(RequestBox, isChange, isALL) ---%>
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
      <!----------------- form 끝 ----------------->
      <br>
      <br>
      <!----------------- 평가마스터 조회 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="10" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="5%" class="table_title">NO</td>
          <td class="table_title">그룹</td>
		  <td width="20%"  class="table_title">테스트</td>
          <td class="table_title">난이도별 문항수</td>
          <td class="table_title">총점</td>
          <td class="table_title">시험시작시간</td>
          <td class="table_title">시험종료시간</td>
          <td class="table_title">시험시간</td>
		  <td class="table_title">진행</td>
          <td  width="10%" class="table_title">기능</td>
        </tr>
<%   ArrayList    blist = (ArrayList)request.getAttribute("ETestPaperList");

    if(blist.size() > 0) {			
        for (int i=0; i<blist.size(); i++) {
            ArrayList list = (ArrayList)blist.get(i);   %>
        <tr>
          <td rowspan="<%=list.size()%>" class="table_01"><%=i+1%></td>
          <td rowspan="<%=list.size()%>" class="table_02_1">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td align="center"><%=((DataBox)list.get(0)).getString("d_etestsubjnm")%></td>
              </tr>
            </table>
          </td>

<%    
    if(list.size() > 0) {	  
        for (int k=0; k<list.size(); k++) {
            DataBox dbox = (DataBox)list.get(k);    

		   long v_start = Long.parseLong(dbox.getString("d_startdt"));
           long v_end = Long.parseLong(dbox.getString("d_enddt"));
		   String v_ing = "";
		   int v_update = 0;

                 Date d_now = new Date();
                 String d_year = String.valueOf(d_now.getYear()+1900);
				 String d_month = String.valueOf(d_now.getMonth()+1);
				 String d_day = String.valueOf(d_now.getDate());
				 String d_hour = String.valueOf(d_now.getHours());
				 String d_minute = String.valueOf(d_now.getMinutes());

                 if(d_month.length() == 1){
				        d_month = "0" + d_month; 
				 }
				 if (d_day.length() == 1){
				        d_day = "0" + d_day; 				 
				 }
				 if (d_hour.length() == 1){
				        d_hour = "0" + d_hour; 				 
				 }
				 if (d_minute.length() == 1){
				        d_minute = "0" + d_minute; 				 
				 }
		         long v_now = Long.parseLong(d_year+d_month+d_day+d_hour+d_minute); 


		   if (v_start > v_now){
		            v_ing = "<font color='red'>테스트 진행 전입니다.</font>"; 
					v_update = 1;
		   } else if (v_now > v_end){
		            v_ing = "테스트 진행이 완료되었습니다.";
		   } else if (v_start <= v_now || v_now < v_end){
		            v_ing = "테스트가 진행 중입니다"; 
		   }
		   
%>
          <td class="table_02_1">
          <%=dbox.getString("d_etestnum")%>(<%=dbox.getString("d_etestcode")%>)  </td>
          <td class="table_02_1"><%=dbox.getInt("d_cntlevel1")%>/<%=dbox.getInt("d_cntlevel2")%>/<%=dbox.getInt("d_cntlevel3")%> : <%=dbox.getInt("d_etestcnt")%></td>
		  <td class="table_02_1"><%=dbox.getString("d_totalscore")%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_startdt"),"yyyy/MM/dd HH:mm")%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_enddt"),"yyyy/MM/dd HH:mm")%></td>
		  <td class="table_02_1"><%=dbox.getString("d_etesttime")%></td>    
		  <td class="table_02_1"><%=v_ing%></td>
          <td class="table_03_1">
          <a href="javascript:PreviewPaper('<%=dbox.getString("d_etestsubj")%>', '<%=dbox.getString("d_year")%>', '<%=dbox.getString("d_etestcode")%>','<%=dbox.getInt("d_etestnum")%>', '<%=dbox.getString("d_etestsubjnm")%>')" class="a"><img src="/images/admin/button/b_preview.gif" border="0">
          </a>
          </td>
		</tr>
<%      }  
      } else {
%>
          <td class="table_02_1"></td>
          <td class="table_02_1"></td>
          <td class="table_02_1"></td>
          <td class="table_02_1"></td>
          <td class="table_02_1"></td>
          <td class="table_02_1"></td>
          <td class="table_02_1"></td>
		</tr>
<%
		}
	  }
	 }  else {

		if (box.getString("p_action").equals("go")) {
%>
        <tr>
          <td  class="table_01">1</td>
          <td class="table_02_1"><%=box.getString("s_etestsubj")%></td>
		  <td  class="table_02_1">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr> 
                <td height="17"><%=box.getString("s_etestcode")%></td>
                <td align="right" width="74" height="17"><a href="javascript:InsertPaperPage('<%=box.getString("s_etestsubj")%>', '<%=box.getString("s_gyear")%>', '<%=box.getString("s_etestcode")%>')" class="a"><img src="/images/admin/button/b_add.gif" border="0"></a></td>
              </tr>
            </table>
          </td>
          <td class="table_02_1"></td>
          <td class="table_02_1"></td>
          <td class="table_02_1"></td>
          <td class="table_02_1"></td>
          <td class="table_02_1"></td>
          <td class="table_02_1"></td>
          <td class="table_02_1"></td>
		</tr>
<%
		}
  }
%>
      </table>
      <!----------------- 평가마스터 조회 끝 ----------------->
      <br>
    </td>
  </tr>
</table>

</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
