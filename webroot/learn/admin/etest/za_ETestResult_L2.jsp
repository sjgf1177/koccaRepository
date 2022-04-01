<%
/**
 * file name : za_ETestResult_L2.jsp
 * date      : 
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
    
    String  ss_grcode    = box.getString("s_grcode");        //교육그룹
    String  ss_gyear    = box.getString("s_gyear");       
	String  ss_upperclass= box.getStringDefault("s_upperclass", "ALL");
    String  ss_etestsubj    = box.getString("s_etestsubj");       
    String p_action = box.getString("p_action");    
    if(p_action.equals("go")) ss_etestsubj = box.getString("s_etestsubj");

    String  ss_etestcode    = box.getString("s_etestcode");

	String v_flag = box.getString("p_flag");    
	if(v_flag.equals("asc")) v_flag = "desc";
	else v_flag = "asc";

    ArrayList  list = (ArrayList)request.getAttribute("ETestResultList");
	ETestResultData	data  = null;

	DataBox dbox0 = (DataBox)request.getAttribute("ETestMasterData");

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
    document.form1.p_seltext.value = 'ALL';
  }
  whenSelection(p_action);
}  
function whenSelection(p_action) {
    var v_grcode, v_etestsubj, v_etestcode;

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
        if (v_etestcode=="----"){
            alert("테스트를 선택하세요");
            return;
        }
        top.ftop.setPam();
    }
  document.form1.p_process.value = 'ETestResultListPage';
  document.form1.p_action.value = p_action;
  document.form1.submit();
    
  
}

// 정렬
function whenOrder(column) {
	document.form1.target = "_self";
	document.form1.action='/servlet/controller.etest.ETestResultServlet';
	document.form1.p_process.value = 'ETestResultListPage';
	document.form1.p_orderColumn.value = column;
	document.form1.p_flag.value = '<%=v_flag%>';
	document.form1.p_action.value = "go";
	document.form1.submit();
}

// 개인별 결과보기 
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


// 분류별 결과보기 
function GubunResult(p_etesttext,p_etestsubj, p_year, p_etestcode) {

  window.self.name = "winGubunResult";
  farwindow = window.open("", "openGubunResult", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=800, height=600, top=0, left=0");
  document.form3.target = "openGubunResult";
  document.form3.action = "/servlet/controller.etest.ETestResultServlet";
  document.form3.p_process.value = "ETestGubunResult";

  document.form3.p_etesttext.value  = p_etesttext;   
  document.form3.p_etestsubj.value  = p_etestsubj; 
  document.form3.p_gyear.value      = p_year; 
  document.form3.p_etestcode.value  = p_etestcode; 
  document.form3.submit();

  farwindow.window.focus();
  document.form3.target = window.self.name;
}


// 조회 검색
function makeHistory() {

    var v_grcode, v_gyear, v_etestsubj, v_etestcode;

	v_grcode        = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;
    v_gyear        = document.form1.s_gyear.value;
	v_etestsubj        = document.form1.s_etestsubj.value;
    v_etestcode        = document.form1.s_etestcode.value;

	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (v_etestsubj=="0"){
            alert("테스트그룹을 선택하세요");
            return;
    }
    if (v_etestcode=="0"){
            alert("테스트명을 선택하세요");
            return;
    }
  document.form1.p_process.value = 'ETestUserResultHistory';
  document.form1.p_action.value = 'go';
  document.form1.action = "/servlet/controller.etest.ETestResultServlet";
  document.form1.submit();
 
}

function move_list(process){
  document.form1.p_process.value = process;
  document.form1.action = "/servlet/controller.etest.ETestResultServlet";
  document.form1.submit();  
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

		<!----------------- 탭 시작 ----------------->
		<table cellspacing="0" cellpadding="0" class="table1">
          <tr>
            <td width="240">
              <table cellspacing="0" cellpadding="0" class="s_table">
                <tr>
                  <td rowspan="3" class="blue_butt_left"></td>
                  <td class="blue_butt_top"></td>
                  <td rowspan="3" class="blue_butt_right"></td>
                </tr>
                <tr>
                  <td class="blue_butt_middle">테스트별응시처리 및 결과</td>
                </tr>
                <tr>
                  <td class="blue_butt_bottom"></td>
                </tr>
              </table>

            </td>
            <td align="right" valign="top">
                <table width="97%" border="0" cellpadding="0" cellspacing="0">
                  <tr> 
                    <td valign="bottom" height="20">&nbsp;</td>
                    <td align="right" width="65">&nbsp;</td>
                    <td align="right" width="55">
                    &nbsp;
                    </td>
                  </tr>
                </table>
         </td>
          </tr>
        </table>
		<!----------------- 탭 끝   ----------------->

		        
      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
      <form name="form1" method="post" action="/servlet/controller.etest.ETestResultServlet">
        <input type="hidden" name="p_process"    value="">
        <input type="hidden" name="p_action"     value="">
		<input type="hidden" name="p_orderColumn"     value="">
		<input type="hidden" name="p_flag"     value="">
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
                          <font color="red">★</font> <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
	                       <%= SelectEduBean.getGyear(box, true )%><!-- getGyear(RequestBox, isChange)   해당연도  -->
						   분류<%=CodeConfigBean.getCodeGubunSelect (ETestBean.ETSUBJ_CLASS, "", 1, "s_upperclass", ss_upperclass, "onchange=javascript:whenSelection('change')", 2)%>
                          <font color="red">★</font> 그룹<%=ETestQuestionBean.getGroupSelect (ss_grcode, ss_upperclass, "s_etestsubj", ss_etestsubj, "onchange=javascript:whenSelection('change')")%>
                          <font color="red">★</font> 테스트명<%= ETestMemberBean.getETestCode(ss_etestsubj, ss_gyear, "s_etestcode", ss_etestcode, "")%>&nbsp;<% //-- getSubjseq(RequestBox, isChange, isALL) ---%>
		                 <a href="javascript:makeHistory()"><img src = "/images/admin/button/b_go.gif" border = "0"></a>
		                 <% // include file="/learn/admin/include/za_GoButton.jsp" %>
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

           String v_ing = "";
		   int v_update = 0;

		   if(!dbox0.getString("d_etesttext").equals("")){

		   long v_start = Long.parseLong(dbox0.getString("d_startdt"));
           long v_end = Long.parseLong(dbox0.getString("d_enddt"));

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
					v_update = 3;
		   } else if (v_start <= v_now || v_now < v_end){
		            v_ing = "테스트가 진행 중입니다"; 
					v_update = 2;
		   } 
		   }
%>
      <!--table cellpadding="0" cellspacing="0" class="heed_table">
        <tr>
          <td align="right">
<%if(v_update > 1){%>
            <a href="javascript:makeHistory()"><img src="/images/admin/button/btn_resultsave.gif" border="0"></a>
<%}%>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table-->
<%

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

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>점수별 통계</td>
		  </tr>
          <tr>
            <td height="5"></td>
          </tr>
        </table>
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
			rate = (Integer.parseInt((String)v_average1.get(i)) * 100  /Integer.parseInt((String)v_average.get(1)));

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

<%
	}	
%>    

      <br>
	  <br>
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>개인별 통계</td>
		  </tr>
          <tr>
            <td height="5" align=right colspan=2><!--<a href="javascript:GubunResult('<%=dbox0.getString("d_etesttext")%>','<%=ss_etestsubj%>','<%=dbox0.getString("d_year")%>','<%=dbox0.getString("d_etestcode")%>');" class="e">분류별 통계</a>--></td>
          </tr>
        </table>

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
			  <input type="hidden" name="p_isadmin"  value="Y">
			</form>  
        <tr> 
          <td colspan="13" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="6%" class="table_title">NO</td>
  <!--        <td class="table_title">테스트명</td>
          <td class="table_title">테스트연도</td>
          <td class="table_title">문제지번호</td>-->
          
          <td class="table_title"><a href="javascript:whenOrder('companynm')" class="e">회원분류</a></td>          
		  <td class="table_title"><a href="javascript:whenOrder('userid')" class="e">ID</a></td>
          <td class="table_title"><a href="javascript:whenOrder('name')" class="e">성명</a></td>
<!--
          <td class="table_title"><a href="javascript:whenOrder('asgnnm')" class="e">부서</a></td>
          <td class="table_title"><a href="javascript:whenOrder('jikwinm')" class="e">직위</a></td>
-->
          <td class="table_title"><a href="javascript:whenOrder('issubmit')" class="e">상태</td>
          <td class="table_title">시작일시</td>
          <td class="table_title">완료일시</td>
          <td class="table_title"><a href="javascript:whenOrder('score')" class="e">점수</td>
        </tr>
<%  for (int i=0; i<list.size(); i++) {
        data  = (ETestResultData)list.get(i); %>
        <tr> 
          <td class="table_01"><%=(i+1)%></td>          
    <!--  <td class="table_02_1"><%//=data.getSubjnm()%></td>
          <td class="table_02_1"><%//=data.getYear()%></td>
          <td class="table_02_1"><%//=data.getPapernum()%></td>-->
          <td class="table_02_1"><%//=data.getCompanynm()%>일반회원</td>    
    	  <td class="table_02_1">
          <% if (data.getStatus().equals("완료")) { 
		            if(v_average.size() > 0){
		  %>
          <a href="javascript:IndividualResult('<%=data.getSubj()%>','<%=data.getYear()%>','<%=data.getSubjseq()%>','<%=data.getUserid()%>','<%=data.getSubjnm()%>','<%=(String)v_average.get(2)%>','<%=(String)v_average.get(1)%>')" class="e">
          <%    }  else { %>
          <a href="javascript:IndividualResult('<%=data.getSubj()%>','<%=data.getYear()%>','<%=data.getSubjseq()%>','<%=data.getUserid()%>','<%=data.getSubjnm()%>','0','0')" class="e">
          <% } }%>
          <%=data.getUserid()%>
          <% if (data.getStatus().equals("완료")) { %>
          </a>   
          <% } %>
          </td>
          <td class="table_02_1">
          <%=data.getName()%>
          </td>
<!--
          <td class="table_02_1"><%=data.getAsgnnm()%></td>
          <td class="table_02_1"><%=data.getJikwinm()%></td>
-->
          <td class="table_02_1"><%=data.getStatus()%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(data.getStarted(),"yyyy/MM/dd HH:mm")%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(data.getEnded(),"yyyy/MM/dd HH:mm")%></td>
          <td class="table_02_1"><%=data.getScore()%></td>
        </tr>
<%  } %>
      </table>
      <br>
	  <br>
      <!----------------- 평가점수 조회 끝 ----------------->


        

      <br>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
