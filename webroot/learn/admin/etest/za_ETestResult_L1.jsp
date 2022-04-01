<%
/**
 * file name : za_ETestResult_L1.jsp
 * date      : 
 * programmer:
 * function  : 평가문제지 결과분석 
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

%>
<html>
<head>
<title>평가점수 조회</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
// 셀렉트 선택
function whenSelection(p_action) {
    var v_grcode, v_etestsubj;
	v_grcode        = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;
    v_etestsubj     = document.form1.s_etestsubj.value;

	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (p_action=="go") {
        if (v_etestsubj=="0"){
            alert("테스트그룹을 선택하세요");
            return;
        }
    }

  document.form1.target = "_self";  
  document.form1.p_process.value = 'ETestResult';      
  document.form1.p_action.value = p_action;
  document.form1.action = "/servlet/controller.etest.ETestResultServlet";  
  document.form1.submit();

}


// 검색
function makeHistory() {

    var v_grcode, v_gyear, v_etestsubj, v_etestcode;

	v_grcode        = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;
    v_gyear        = document.form1.s_gyear.value;
	v_etestsubj        = document.form1.s_etestsubj.value;
    //v_etestcode        = document.form1.s_etestcode.value;

	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (v_etestsubj=="0"){
            alert("테스트그룹을 선택하세요");
            return;
    }
    
  document.form1.target = "_self";    
  document.form1.p_process.value = 'Reporting';
  document.form1.p_action.value = 'go';
  document.form1.action = "/servlet/controller.etest.ETestResultServlet";
  document.form1.submit();  
}

// 탭이동
function move_list(process){
  document.form1.target = "_self";  
  document.form1.p_process.value = process;
  document.form1.action = "/servlet/controller.etest.ETestResultServlet";
  document.form1.submit();  
}

// 엑셀출력
function goExcel() {
    var v_grcode, v_gyear, v_etestsubj, v_etestcode;

	v_grcode        = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;
    v_gyear         = document.form1.s_gyear.value;
	v_etestsubj     = document.form1.s_etestsubj.value;
    //v_etestcode   = document.form1.s_etestcode.value;

	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (v_etestsubj=="0"){
            alert("테스트그룹을 선택하세요");
            return;
    }
    
    var v_param  = '?s_grcode='+v_grcode+'&s_gyear='+v_gyear+'&s_etestsubj='+v_etestsubj; 

    window.self.name = "reporting";
    open_window("openExcel1","","100","100","800","600","yes","yes","yes","yes","yes");        
    document.form1.target = "openExcel1";                 
    document.form1.action='/learn/admin/etest/za_ETestResultList_E.jsp'+v_param;                 
    document.form1.submit();
}


    // 보고서 엑셀보기
    function whenExcel(gubun) {
        v_gyear         = document.form1.s_gyear.value;
    	v_etestsubj     = document.form1.s_etestsubj.value;  
        var v_param  = '?p_etestsubj='+v_etestsubj+'&p_year='+v_gyear; 

        window.self.name = "reporting";
        document.form1.target = "openExcel1";
                            
        if(gubun==1){
            open_window("openExcel1","","100","100","800","600","yes","yes","yes","yes","yes");                
            document.form1.action='/learn/admin/etest/za_Report1_E.jsp'+v_param;                                        
        }
        if(gubun==2){
            open_window("openExcel1","","100","100","900","600","yes","yes","yes","yes","yes");                
            document.form1.action='/learn/admin/etest/za_Report2_E.jsp'+v_param;                                        
        }        
        if(gubun==3){
            open_window("openExcel1","","100","100","900","600","yes","yes","yes","yes","yes");                
            document.form1.action='/learn/admin/etest/za_Report3_E.jsp'+v_param;                                        
        }
        if(gubun==4){
            open_window("openExcel1","","100","100","900","600","yes","yes","yes","yes","yes");                
            document.form1.action='/learn/admin/etest/za_Report4_E.jsp'+v_param;                                        
        }     
        if(gubun==5){
            open_window("openExcel1","","100","100","800","600","yes","yes","yes","yes","yes");                
            document.form1.action='/learn/admin/etest/za_Report5_E.jsp'+v_param;                                        
        }   
        if(gubun==6){
            open_window("openExcel1","","100","100","900","600","yes","yes","yes","yes","yes");                
            document.form1.action='/learn/admin/etest/za_Report6_E.jsp'+v_param;                                        
        }                     
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
      <form name="form1" method="post">
        <input type="hidden" name="p_process"    value="">
        <input type="hidden" name="p_action"     value="">
		<input type="hidden" name="p_orderColumn"     value="">
		<input type="hidden" name="p_flag"     value="">
		
		<!----------------- 탭 시작 ----------------->
		<table cellspacing="0" cellpadding="0" class="table1">
          <tr>
            <td width="125" height="23" align="center" valign="middle">

              <table cellspacing="0" cellpadding="0" class="s_table">
                <tr>
                  <td rowspan="3" class="blue_butt_left"></td>
                  <td class="blue_butt_top"></td>
                  <td rowspan="3" class="blue_butt_right"></td>
                </tr>
                <tr>
                  <td class="blue_butt_middle"><a href="javascript:move_list('ETestResult')" class="c">결과 분석</a></td>
                </tr>
                <tr>
                  <td class="blue_butt_bottom"></td>
                </tr>
              </table>

            </td>
            <td width="2"></td>
            <td width="240">
              <table cellspacing="0" cellpadding="0" class="s_table">
                <tr>
                  <td rowspan="3" class="black_butt_left"></td>
                  <td class="black_butt_top"></td>
                  <td rowspan="3" class="black_butt_right"></td>
                </tr>
                <tr>
                  <td class="black_butt_middle"><a href="javascript:move_list('ETestResultListPage')" class="c">테스트별응시처리 및 결과</a></td>
                </tr>
                <tr>
                  <td class="black_butt_bottom"></td>
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

      </table>
	  <br>
        <!--<table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>개인별 성적세부내역</td>
		  </tr>
        </table>

      <table width="97%" cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td align="right">
          &nbsp;&nbsp;<a href="javascript:goExcel()"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>-->


        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>레포트</td>
		  </tr>
          <tr>
            <td height="3"></td>
          </tr>
        </table>
        <table class="form_table_out" cellspacing="0" cellpadding="1">
          <tr>
            <td bgcolor="#C6C6C6" align="center">
              <table class="form_table_bg" cellspacing="0" cellpadding="0" border='0'>
                <tr>
                  <td height="7"></td>
                </tr>
                <tr>
                  <td align="center">
                  <table border="0" cellspacing="3" cellpadding="3" width="99%" class="form_table">              
        	      <tr>
        	        <td width='10%'></td>
                    <td width="40%"><a href="javascript:whenExcel(1);">요약(1)</a> </td>        	        
        	        <td width='10%'></td>            
                    <td width="40%"> <a href="javascript:whenExcel(2);">E-test 개인별통계(2)</a> </td>        	        
                  </tr>

        	      <tr>
        	        <td width='10%'></td>
                    <td width="40%"><a href="javascript:whenExcel(3);">개인별성적세부내역(3)</a></td>        	        
        	        <td width='10%'></td>            
                    <td width="40%"> <a href="javascript:whenExcel(4);">개인별 성적보고서(4)</a> </td>        	        
                  </tr>
        	      <tr>
        	        <td width='10%'></td>            
                    <td width="40%"><a href="javascript:whenExcel(6);">미응시자 현황 보고서(6)</a> </td>        	        
        	        <td width='10%'></td>	      
                    <td width="40%"></td>        	        
                  </tr>                                                                               
                <tr>
                  <td height="7"></td>
                </tr>
              </table>
                             
                </td>
              </tr>
            </table>
            </td>
          </tr>
        </table>                
        <!-----------------  끝 ----------------->	   



      </form> 
    </td>
  </tr>
</table>



<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
