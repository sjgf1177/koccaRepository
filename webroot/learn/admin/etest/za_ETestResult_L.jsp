<%
/**
 * file name : za_ExamResult.jsp
 * date      : 2003/09/07
 * programmer:
 * function  : 평가문제지 평가점수 조회
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
    
    String  ss_grcode    = box.getString("s_grcode");        //교육주관
    String  ss_gyear    = box.getString("s_gyear");       
	String  ss_upperclass= box.getStringDefault("s_upperclass", "ALL");
    String  ss_etestsubj    = box.getString("s_etestsubj");       
    String p_action = box.getString("p_action");    
    if(p_action.equals("go")) ss_etestsubj = box.getString("s_etestsubj");

    String  ss_etestcode    = box.getString("s_etestcode");

    ArrayList  list = (ArrayList)request.getAttribute("ETestResultList");
	ETestResultData	data  = null;
%>
<html>
<head>
<title>평가점수 조회</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
function whenSelection2(p_action, p_type) {
  if (p_type == 'selgubun') {
    document.form2.p_seltext.value = 'ALL';
  }
  whenSelection(p_action);
}  
function whenSelection(p_action) {
    var v_grcode, v_etestsubj, v_etestcode;

	v_grcode        = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
    v_etestsubj        = document.form2.s_etestsubj.value;
    v_etestcode        = document.form2.s_etestcode.value;

	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
    if (p_action=="go") {
        if (v_etestsubj=="0"){
            alert("테스트그룹을 선택하세요");
            return;
        }
        if (v_etestcode=="----"){
            alert("테스트를 선택하세요");
            return;
        }
        //top.ftop.setPam();
    }
  document.form2.p_process.value = 'ETestResultListPage';
  document.form2.p_action.value = p_action;
  document.form2.submit();
}
function IndividualResult(p_etestsubj, p_year, p_etestcode, p_userid, p_etesttext, p_average, p_personcnt) {

  window.self.name = "winIndividualResult";
  farwindow = window.open("", "openIndividualResult", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=800, height=600, top=0, left=0");
  document.form3.target = "openIndividualResult";
  document.form3.action = "/servlet/controller.etest.ETestUserServlet";
  document.form3.p_process.value = "ETestUserPaperResult2";
  
  document.form3.p_etestsubj.value    = p_etestsubj; 
  document.form3.p_gyear.value    = p_year; 
  document.form3.p_etestcode.value = p_etestcode; 
  document.form3.p_etesttext.value  = p_etesttext; 
  document.form3.p_userid.value  = p_userid;

  document.form3.p_average.value  = p_average; 
  document.form3.p_personcnt.value  = p_personcnt; 
 
  document.form3.submit();

  farwindow.window.focus();
  document.form3.target = window.self.name;
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/etest/test_title06.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
      <form name="form2" method="post" action="/servlet/controller.etest.ETestResultServlet">
        <input type="hidden" name="p_process"    value="">
        <input type="hidden" name="p_action"     value="">
        <tr> 
          <td bgcolor="#C6C6C6" align="center"> 
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr> 
                <td height="7"></td>
              </tr>
              <tr> 
                <td align="center"> 
                  <table cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr> 
                      <td>
                         <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
	                     <%= SelectEduBean.getGyear(box, true )%><!-- getGyear(RequestBox, isChange)   해당연도  -->
						 테스트분류<%=CodeConfigBean.getCodeGubunSelect (ETestBean.ETSUBJ_CLASS, "", 1, "s_upperclass", ss_upperclass, "onchange=javascript:whenSelection('change')", 2)%>
                         테스트그룹<%=ETestQuestionBean.getGroupSelect (ss_grcode, ss_upperclass, "s_etestsubj", ss_etestsubj, "onchange=javascript:whenSelection('change')")%>
                         테스트명<%= ETestMemberBean.getETestCode(ss_etestsubj, ss_gyear, "s_etestcode", ss_etestcode, "")%>&nbsp;<% //-- getSubjseq(RequestBox, isChange, isALL) ---%>
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
      </form>  
      </table>
      <!----------------- form 끝 ----------------->
	  <br>
      <br>
<%
    Vector  v_average = (Vector)request.getAttribute("ETestResultAverage");     
	if(v_average.size() > 0){
%>
	  <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="13" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="10%" class="table_title">문제수</td>
          <td class="table_title">총점</td>
          <td class="table_title">응시자수</td>
          <td class="table_title">전체평균</td>
          <td class="table_title">최고점수</td>
          <td class="table_title">최저점수</td>
        </tr>
		<tr> 
          <td class="table_02_1"><%=(String)v_average.get(0)%></td>
          <td class="table_02_1">100점</td>
          <td class="table_02_1"><%=(String)v_average.get(1)%></td>
          <td class="table_02_1"><%=(String)v_average.get(2)%></td>
          <td class="table_02_1"><%=(String)v_average.get(3)%></td>
          <td class="table_02_1"><%=(String)v_average.get(4)%></td>
        </tr>
      </table>
	  <br>
      <br>
<%
    }	
%>
<%
    Vector  v_average1 = (Vector)request.getAttribute("ETestResultAverage");     
	int start = 0;
	int end = 10;
	int rate = 0;
	if(v_average1.size() > 0){
%>
	  <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="13" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="10%" class="table_title">점수분포</td>
          <td colspan="2" class="table_title">인원</td>
        </tr>
<%
		for (int i = 5; i < v_average1.size(); i++){
			rate = (Integer.parseInt((String)v_average1.get(i))/Integer.parseInt((String)v_average.get(1))) * 100;

%>
		<tr> 
          <td class="table_02_1" width = "10%"><%=start%>점 ~ <%=end%>점</td>
          <td class="table_02_1" width="10%"><%=(String)v_average1.get(i)%>명</td>
          <td class="table_02_2" align="left"><img src="/images/admin/research/statistics_line.gif" height="14" width="<%=rate*5%>"><%=rate%>%</td>
        </tr>
<%
   			if(i==5){
	          start += 11;
            }else{
			  start += 10; 
			} 
			end += 10;
        }
%>
      </table>
	  <br>
	  <br>
<%
	}	
%>    
      <!----------------- 평가점수 조회 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form3" method="post" action="/servlet/controller.etest.ETestUserServlet">
        <input type="hidden" name="p_process"  value="">
        <input type="hidden" name="p_action"   value="">
  			<input type="hidden" name="p_etestsubj"     value="">
			  <input type="hidden" name="p_gyear"     value="">
			  <input type="hidden" name="p_etestcode"  value="">
			  <input type="hidden" name="p_userid"   value="">
  			  <input type="hidden" name="p_etesttext"   value="">
			  <input type="hidden" name="p_average"   value="">
			  <input type="hidden" name="p_personcnt"   value="">
			  <input type="hidden" name="p_examtypenm"  value="">
			</form>  
        <tr> 
          <td colspan="13" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="6%" class="table_title">NO</td>
          <td class="table_title">테스트명</td>
          <td class="table_title">테스트연도</td>
          <td class="table_title">테스트코드</td>
          <td class="table_title">소속</td>
          <td class="table_title">직위</td>
          <td class="table_title">사번</td>
          <td class="table_title">성명</td>
          <td class="table_title">상태</td>
          <td class="table_title">완료일시</td>
          <td class="table_title">점수</td>
        </tr>
<%  for (int i=0; i<list.size(); i++) {
        data  = (ETestResultData)list.get(i); %>
        <tr> 
          <td class="table_01"><%=(i+1)%></td>
          <td class="table_02_1"><%=data.getSubjnm()%></td>
          <td class="table_02_1"><%=data.getYear()%></td>
          <td class="table_02_1"><%=data.getSubjseq()%></td>
          <td class="table_02_1"><%=data.getAsgnnm()%></td>
          <td class="table_02_1"><%=data.getJikwinm()%></td>
          <td class="table_02_1">
          <% if (data.getStatus().equals("완료")) { 
		            if(v_average.size() > 0){
		  %>
          <a href="javascript:IndividualResult('<%=data.getSubj()%>','<%=data.getYear()%>','<%=data.getSubjseq()%>','<%=data.getUserid()%>','<%=data.getSubjnm()%>','<%=(String)v_average.get(2)%>','<%=(String)v_average.get(1)%>')" class="e">
          <%    }  else { %>
          <a href="javascript:IndividualResult('<%=data.getSubj()%>','<%=data.getYear()%>','<%=data.getSubjseq()%>','<%=data.getUserid()%>','<%=data.getSubjnm()%>','0','0')" class="e">
          <% } }%>
          <%=data.getCono()%>
          <% if (data.getStatus().equals("완료")) { %>
          </a>   
          <% } %>
          </td>
          <td class="table_02_1"><%=data.getName()%></td>
          <td class="table_02_1"><%=data.getStatus()%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(data.getEnded(),"yyyy/MM/dd HH:mm")%></td>
          <td class="table_02_1"><%=data.getScore()%></td>
        </tr>
<%  } %>
      </table>
      <!----------------- 평가점수 조회 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
