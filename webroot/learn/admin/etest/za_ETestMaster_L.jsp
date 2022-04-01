<%
/**
 * file name : za_ETestMaster_L.jsp
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

%>
<html>
<head>
<title>평가마스터조회</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
// 등록
function InsertMasterPage(p_grcode, p_gyear, p_etestsubj) {

    	if (document.form1.s_grcode.value == '----') {
    			alert("교육그룹을 선택하세요.");
    			return ;
    	}
   	
        if (document.form1.s_etestsubj.value=="0"){
                alert("그룹을 선택하세요");
                return;
        }

    window.self.name = "winSelectView";
    farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 667, height = 900, top=0, left=0");
    document.form1.target = "openWinQuestion";
	document.form1.action = "/servlet/controller.etest.ETestMasterServlet";
    document.form1.p_process.value = 'ETestMasterInsertPage';
    document.form1.p_etestsubj.value    = p_etestsubj;
    document.form1.p_gyear.value    = p_gyear;
	document.form1.submit();

    farwindow.window.focus();
    document.form1.target = window.self.name;
}

// 상세내용 보기
function UpdateMasterPage(p_etestsubj, p_gyear, p_etestcode,p_ingstate) {
        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 667, height = 900, top=0, left=0");
        document.form1.target = "openWinQuestion";
		document.form1.action = "/servlet/controller.etest.ETestMasterServlet";
        document.form1.p_process.value = 'ETestMasterUpdatePage';
        document.form1.p_etestsubj.value    = p_etestsubj;
        document.form1.p_gyear.value    = p_gyear;
        document.form1.p_etestcode.value    = p_etestcode;
        document.form1.p_ingstate.value    = p_ingstate;
		document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;

}

// 조회 검색
function whenSelection(p_action) {
    if (p_action == "go"){ 
    	if (document.form1.s_grcode.value == '----') {
    			alert("교육그룹을 선택하세요.");
    			return ;
    	}
        if (document.form1.s_etestsubj.value=="0"){
                alert("그룹을 선택하세요");
                return;
        }
    }
    top.ftop.setPam();
    document.form1.p_process.value = 'ETestMasterListPage';
    document.form1.p_action.value  = p_action;
    document.form1.action="/servlet/controller.etest.ETestMasterServlet";
    document.form1.submit();
}

// 대상자 링크
function SelectMemberPage(p_etestsubj, p_upperclass, p_year, p_etestcode) {
  document.form1.p_process.value = 'ETestMemberListPage';
  document.form1.p_action.value  = 'go';
//  document.form1.s_etestsubj.value    = p_etestsubj;
//  document.form1.s_upperclass.value    = p_upperclass;
//  document.form1.s_gyear.value    = p_year;
  document.form1.s_etestcode.value = p_etestcode;
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
	<input type="hidden" name="s_etestcode"    value="">
	<input type="hidden" name="p_ingstate"    value="">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/etest/test_title03.gif" ></td>
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
                          <font color="red">★</font> <%=ETestQuestionBean.getGroupSelect (ss_grcode, ss_upperclass, "s_etestsubj", ss_etestsubj, "")%>
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
      <table cellpadding="0" cellspacing="0" class="heed_table">
        <tr>
          <td align="right">
		   <a href="javascript:InsertMasterPage('<%=ss_grcode%>', '<%=ss_gyear%>','<%=ss_etestsubj%>')" class="c"><img src="/images/admin/button/btn_add.gif" border="0"></a>
		  </td>
        </tr>
      </table>
      <!----------------- 평가마스터 조회 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="12" class="table_top_line"></td>
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
          <td class="table_title">시험지수</td>
          <td class="table_title">정원</td>
          <td class="table_title">대상자</td>
          <td  width="10%" class="table_title">진행</td>
        </tr>
<%   ArrayList    blist = (ArrayList)request.getAttribute("ETestMasterList");
            
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
<%    for (int k=0; k<list.size(); k++) {
            DataBox dbox = (DataBox)list.get(k);  
			
           long v_start = Long.parseLong(dbox.getString("d_startdt"));
           long v_end = Long.parseLong(dbox.getString("d_enddt"));

                 java.util.Date d_now = new java.util.Date();
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

           String v_ing = "";
		   int v_update = 0;

		   if (v_start > v_now){
		            v_ing = "<font color='red'>진행전</font>"; 
					v_update = 1;
		   } else if (v_now > v_end){
		            v_ing = "진행완료";
		            v_update = 3;
		   } else if (v_start <= v_now || v_now < v_end){
		            v_ing = "진행중"; 
					v_update = 2;
		   } 

%>
          <td class="table_02_2">
		  <%if(v_update>0){%><a href="javascript:UpdateMasterPage('<%=dbox.getString("d_etestsubj")%>', '<%=dbox.getString("d_year")%>', '<%=dbox.getString("d_etestcode")%>','<%=v_update+""%>')" class="e"><%}%><%=dbox.getString("d_etesttext")%><%if(v_update>0){%></a><%}%></td>
          <td class="table_02_1"><%=dbox.getInt("d_cntlevel1")%>/<%=dbox.getInt("d_cntlevel2")%>/<%=dbox.getInt("d_cntlevel3")%> : <%=dbox.getInt("d_etestcnt")%></td>
		  <td class="table_02_1"><%=dbox.getString("d_totalscore")%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_startdt"),"yyyy/MM/dd HH:mm")%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_enddt"),"yyyy/MM/dd HH:mm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_etesttime")%></td>
		  <td class="table_02_1"><%=dbox.getString("d_papercnt")%></td>
          <td class="table_02_1"><%=dbox.getString("d_etestlimit")%></td>
          <td class="table_02_1">
          <%if(v_update>0 && v_update < 3){%>
		  <a href="javascript:SelectMemberPage('<%=((DataBox)list.get(0)).getString("d_etestsubj")%>', '<%=((DataBox)list.get(0)).getString("d_upperclass")%>', '<%=dbox.getString("d_year")%>', '<%=dbox.getString("d_etestcode")%>')" class="e">
		  <%}%>
		  <%=dbox.getInt("d_membercnt")%>
		  <%if(v_update>0){%></a><%}%>
          </td>
          <td class="table_02_1"><%=v_ing%></td>
		</tr>
<%      }   
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
