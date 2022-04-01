<%
/**
 * file name : za_DamunSubjResult_L.jsp
 * date      : 2003/08/26
 * programmer:
 * function  : 객관식 설문결과조회
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_subjnm    = box.getString("p_subjnm");

    String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

    String  ss_grcode       = box.getString("s_grcode");
	String  ss_gyear       = box.getString("s_gyear");
	String  ss_grseq       = box.getString("s_grseq");
	int  ss_damunpapernum       = box.getInt("s_damunpapernum");
    String  ss_subjseq    = box.getStringDefault("s_subjseq","ALL");

    String  v_company        = box.getString("s_company");
    String  v_jikwi       = box.getString("s_jikwi");
    String  v_jikun       = box.getString("s_jikun");
    String  v_workplc       = box.getString("s_workplc");
    
    int     v_replycount  = box.getInt("p_replycount");
    int     v_studentcount= box.getInt("p_studentcount");

    int     v_replyobcount  = box.getInt("p_replyobcount");
    int     v_studentobcount= box.getInt("p_studentobcount");

    double  v_replyrate   = 0;
    if (v_studentcount != 0) {
        v_replyrate = (double)Math.round((double)v_replycount/v_studentcount*100*100)/100;
    }

    int     v_replycount2  = box.getInt("p_replycount2");
    int     v_studentcount2= box.getInt("p_studentcount2");

    int     v_replyobcount2  = box.getInt("p_replyobcount2");
    int     v_studentobcount2= box.getInt("p_studentobcount2");

    DataBox    dbox = (DataBox)request.getAttribute("DamunPaperData");
    
	String  s_gadmin = box.getSession("gadmin");

    String v_tab_color1 = "blue";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
	String v_tab_color4 = "black";

	DecimalFormat  df = new DecimalFormat("0.00");

	DecimalFormat  df2 = new DecimalFormat("0.0");
%>
<html>
<head>
<title>설문분석-객관식</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
// 검색 
function whenSelection(p_action) {
	
  document.form1.p_process.value = 'DamunResultPage';
  document.form1.p_action.value  = p_action;
  if (p_action == "go"){
		if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	    }
        if (document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
            return;
        }
        //if (document.form1.s_subjseq.value=="----"){
        //    alert("차수를 선택하세요");
        //    return;
        //}
        if (document.form1.s_damunpapernum.value=='0'){
            alert("다면평가지를 선택하세요");
            return;
        }
  document.form1.target = "_self";            
  document.form1.action="/servlet/controller.multiestimate.DamunSubjResultServlet"
  document.form1.p_damunpapernum.value = document.form1.s_damunpapernum.options[document.form1.s_damunpapernum.selectedIndex].value;
  document.form1.p_grcode.value = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;
  document.form1.p_subj.value = document.form1.s_subjcourse.options[document.form1.s_subjcourse.selectedIndex].value;
  document.form1.p_gyear.value = document.form1.s_gyear.options[document.form1.s_gyear.selectedIndex].value;
 top.ftop.setPam();
  }
  document.form1.submit();
}

// 탭이동
function changeTabpage(p_gubun) {

    if (p_gubun == "COMMON") {
      document.form1.action = "/servlet/controller.multiestimate.DamunCommonResultServlet";
	
	} 
	document.form1.p_action.value = 'change';
	document.form1.p_gubun.value = p_gubun;
    document.form1.s_grcode.value = "";
	document.form1.p_process.value = 'DamunResultPage';
	document.form1.submit();
}

function setSubj(p_subj) {
  var v_test; 
  v_test = getArrayTest(document.form1.all.p_subj.length);
  if (v_test == -1) {
    document.form1.p_subj.value = p_subj;
  } else if (v_test == 0) {
  } else {
    document.form1.p_subj.options[document.form1.p_subj.selectedIndex].value = p_subj;
  }
}
function getArrayTest(p_object) {
  var cnt;
  if (typeof(p_object) == 'undefined') {
    cnt = -1;
  } else {
    if (typeof(p_object) == 'number') {
        cnt = p_object;
    } else {
      cnt = -1;
    }
  }
  return cnt;
}


// 엑셀출력
function goExcel(p_action) {
	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (p_action=="go") {
        if (document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
            return;
        }
        //if (document.form1.s_subjseq.value=="----"){
        //    alert("차수를 선택하세요");
        //    return;
        //}
        if (document.form1.s_damunpapernum.value=='0'){
            alert("다면평가지를 선택하세요");
            return;
        }
    }

    var v_grcode   = document.form1.s_grcode.value;
    var v_grseq    = document.form1.s_grseq.value;         
    var v_subj     = document.form1.s_subjcourse.value;
    var v_subjseq  = document.form1.s_subjseq.value;
    var v_gyear    = document.form1.s_gyear.value;    
    var v_damunpapernum    = document.form1.s_damunpapernum.value;        
    var v_param  = '?s_grcode='+v_grcode+'&s_gyear='+v_gyear+'&s_grseq='+v_grseq+'&s_subjcourse='+v_subj+'&s_subjseq='+v_subjseq+'&s_damunpapernum='+v_damunpapernum;
    
    window.self.name = "reporting";
    document.form1.target = "openExcel1";    
    open_window("openExcel1","","100","100","800","600","yes","yes","yes","yes","yes");                 
    document.form1.action='/learn/admin/multiestimate/SubjResult_E.jsp'+v_param;                 
    document.form1.submit();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/multiestimate/m_title05.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
	  <br>
     <table cellspacing="0" cellpadding="0" class="table1">
        <tr> 
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color1%>_butt_left"></td>
                <td class="<%=v_tab_color1%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color1%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color1%>_butt_middle">과정평가</td>
              </tr>
              <tr>
                <td class="<%=v_tab_color1%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_left"></td>
                <td class="<%=v_tab_color3%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_middle"><a href="javascript:changeTabpage('COMMON')" class="c">일반평가</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td>&nbsp;</td>
        </tr>
      </table>
      <table cellspacing="0" cellpadding="0" class="table_out">
        <tr>
          <td bgcolor="#636563">
            <table cellspacing="1" cellpadding="0" class="s_table">
              <tr>
                <td bgcolor="#FFFFFF" align="center" valign="top">
                  <br>
                  <!----------------- form 시작 ----------------->
                  <table cellspacing="0" cellpadding="1" class="form_table_out">
                  <form name="form1" method="post" >
                    <input type="hidden" name="p_process"  value="">
                    <input type="hidden" name="p_action"   value="">
                	<input type="hidden" name="p_grcode"      value="<%=ss_grcode%>">
                	<input type="hidden" name="p_gyear"      value="<%=ss_gyear%>">
					<input type="hidden" name="p_grseq"      value="<%=ss_grseq%>">
	                <input type="hidden" name="p_subj"      value="<%=ss_subjcourse%>">
	                <input type="hidden" name="p_subjseq"      value="<%=ss_subjseq%>">
	                <input type="hidden" name="s_subjseq"      value="<%=ss_subjseq%>">
                    <input type="hidden" name="p_damunpapernum"       value="<%=ss_damunpapernum%>">
	                <input type="hidden" name="p_company"      value="<%=v_company%>">
	                <input type="hidden" name="p_jikwi"      value="<%=v_jikwi%>">
	                <input type="hidden" name="p_jikun"      value="<%=v_jikun%>">
	                <input type="hidden" name="p_workplc"      value="<%=v_workplc%>">
                	<input type="hidden" name="p_gubun"       value="">

  
					<tr>
                      <td bgcolor="#C6C6C6" align="center">
                        <table cellspacing="0" cellpadding="1" class="form_table_bg" >
                          <tr>
                            <td height="7"></td>
                          </tr>
                          <tr>
                            <td align="center">
                              <table cellspacing="0" cellpadding="0" width="99%" class="form_table">
								<tr>
								  <td width="27%" colspan="2" align="left" valign="middle">
									<!------------------- 조건검색 시작 ------------------------->
									<font color="red">★</font> <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
								  </td>
								  <td width="73%" colspan="2">
									<%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도  -->
									<font color="red">★</font> <%= SelectEduBean.getGrseq(box, true, false)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
									<!--input type= "hidden" name="s_grseq" value="0001"-->
									<%= SelectSubjBean.getUpperClass(box, true, true, true)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
									<%= SelectSubjBean.getMiddleClass(box, true, true, true)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
									<%= SelectSubjBean.getLowerClass(box, true, true, true)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
								  </td>
								</tr>
								<tr>
									<td height="5"></td>
								</tr>
								<tr>
								  <td align="left" colspan="3">
									<font color="red">★</font> <%= SelectSubjBean.getSubj(box, true, true)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
									<!--<font color="red">★</font> <%= SelectSubjBean.getSubjseq(box, true, true)%> getSubjseq(RequestBox, isChange, isALL)    과정차수  -->
                                   <font color="red">★</font>다면평가지<%= DamunSubjMemberBean.getDamunpaperSelect (ss_grcode, ss_gyear, ss_subjcourse, "s_damunpapernum", ss_damunpapernum, "", ss_subjseq)%>
								  </td>
								  <td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
				  <br>
                  <!--<table cellspacing="0" cellpadding="1" class="form_table_out">
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
                                  <td width="19%">
                      회사 <%= SelectCompBean.getCompany(box, true, true)%>&nbsp;
                      직위 <%= SelectCompBean.getJikwi(box, false, true)%>&nbsp;
                                  <a href = "javascript:whenSelection('go')"><img src = "/images/admin/button/b_go.gif" border = "0"></a>
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
                  
                  </table>--></form>
                  <!----------------- form 끝 ----------------->
                  
                  <!----------------- 엑셀출력 버튼 시작 ----------------->
                  <table cellpadding="0" cellspacing="0" class="table1">
                    <tr>
                       <td align="right">
					  <a href="javascript:goExcel('go')" class="c"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a></td>
                    </tr>
                    <tr>
             <!--        <td height="3"><a href="javascript:goDetailExcel('go')" class="ㅁ">사전다면평가엑셀보기</a></td>-->
                    </tr>
                  </table>
                  <!----------------- 엑셀출력 버튼 끝 ----------------->
<%  
  ArrayList    list = (ArrayList)request.getAttribute("DamunResultList");
  ArrayList    list2 = (ArrayList)request.getAttribute("DamunResultList2");
    DamunQuestionExampleData data    = null;
    DamunExampleData         subdata = null;

	DamunQuestionExampleData data2    = null;
    DamunExampleData         subdata2 = null;



  if((dbox.getString("d_d_gubun")).equals("1")){
%>
                  <!----------------- 설문분석 시작 ----------------->
                 <table cellspacing="1" cellpadding="0" class="box_table_out">
                    <tr>
                     <td colspan="4" class="table_top_line"></td>
                    </tr>
                    <tr>
                      <td class="table_title">관찰자 총인원</td>
                      <td class="table_title">관찰자 참여인원</td>
                      <td class="table_title">대상자 총인원</td>
                      <td class="table_title">대상자 참여인원</td>
                    </tr>
                    <tr>
                      <td class="table_02_1"><%=v_studentobcount%></td>
                      <td class="table_02_1"><%=v_replyobcount%></td>
                      <td class="table_02_1"><%=v_studentcount%></td>
                      <td class="table_02_1"><%=v_replycount%></td>
                    </tr>
                </table>
				<br>

<%
System.out.println(">>"+list.size());
    for (int i=0; i < list.size(); i++) {
        data = (DamunQuestionExampleData)list.get(i);
	//	data2 = (DamunQuestionExampleData)list2.get(i);

		if (data.getSultype().equals(DamunSubjBean.OBJECT_QUESTION) || data.getSultype().equals(DamunSubjBean.MULTI_QUESTION)) { %>        
                 <table cellspacing="1" cellpadding="0" class="box_table_out">
					<tr>
                      <td width="5%" class="table_title">문제<%=i+1%></td>
                      <td colspan="5" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">              
                    <tr>
                      <td width="5%" class="table_01">No.</td>
                      <td width="29%" style="padding-left=6" class="table_01">보기</td>
                      <td width="34%" class="table_01">그래프</td>
                      <td width="5%" class="table_01">%</td>
					  <td  width="5%" class="table_01">집계</td>
					  <td  width="7%" class="table_01">대상자본인</td>
					  <td  width="5%" class="table_01">상사</td>
					  <td  width="5%" class="table_01">동료</td>
					  <td  width="5%" class="table_01">부하</td>
					</tr>  
<%    
        if(data!=null) {
          for (int j=1; j <= data.size(); j++) {
                subdata  = (DamunExampleData)data.get(j); 
		//		subdata2  = (DamunExampleData)data2.get(j); 

                if (subdata != null) { %>
                    <tr>
                      <td width="5%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td width="34%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%               
					if ((int)subdata.getReplyrate()==0) {
					%>
                          <td>&nbsp;</td>
<%                  } else { %>
						  <td width="<%=(int)subdata.getReplyrate()%>%" class="graph">&nbsp;</td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
					  <td width="5%" class="table_02_1"><%=(int)subdata.getReplyrate()%>%</td>
					  <td  width="5%" class="table_02_1"><%=subdata.getReplycnt()%></td>
					  <td  width="7%" class="table_02_1"><%=subdata.getSubjectcnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation1cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation2cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation3cnt()%></td>
                    </tr>
<%              }
            }
         }  //  data.size()!=null         
%>
                 </table> 
				 <br>
<%       } else if (data.getSultype().equals(DamunSubjBean.SUBJECT_QUESTION)) {
          
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">
                    <tr>
                      <td width="5%" class="table_title">문제<%=i+1%></td>
                      <td colspan="5" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">    
<%          for (int j=0; j < data.getSubjectAnswer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getSubjectAnswer().get(j)%></td>
                    </tr>

<%         }
%>
<%          for (int j=0; j < data.getR1Answer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getR1Answer().get(j)%></td>
                    </tr>

<%         }
%>
<%          for (int j=0; j < data.getR2Answer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getR2Answer().get(j)%></td>
                    </tr>

<%         }
%>
<%          for (int j=0; j < data.getR3Answer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getR3Answer().get(j)%></td>
                    </tr>

<%         }
%>
                 </table> 
				 <br>
<%
           	
       } else if (data.getSultype().equals(DamunSubjBean.COMPLEX_QUESTION)) {
          
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">
                    <tr>
                      <td width="5%" class="table_title">문제<%=i+1%></td>
                      <td colspan="5" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">              
                    <tr>
                      <td width="5%" class="table_01">No.</td>
                      <td width="29%" style="padding-left=6" class="table_01">보기</td>
                      <td width="34%" class="table_01">그래프</td>
                      <td width="5%" class="table_01">%</td>
					  <td  width="5%" class="table_01">집계</td>
					  <td  width="7%" class="table_01">대상자본인</td>
					  <td  width="5%" class="table_01">상사</td>
					  <td  width="5%" class="table_01">동료</td>
					  <td  width="5%" class="table_01">부하</td>
					</tr>  
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { %>
                    <tr>
                      <td width="5%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td width="34%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>%" class="graph">&nbsp;</td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
                      <td width="5%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
					  <td  width="5%" class="table_02_1"><%=subdata.getReplycnt()%></td>
					  <td  width="7%" class="table_02_1"><%=subdata.getSubjectcnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation1cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation2cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation3cnt()%></td>
                    </tr>
<%           } 
           }	
%>
<%          for (int j=0; j < data.getComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
<%          for (int j=0; j < data.getR1ComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getR1ComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
<%          for (int j=0; j < data.getR2ComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getR2ComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
<%          for (int j=0; j < data.getR3ComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getR3ComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
                 </table> 
				 <br>
<%
       } else if (data.getSultype().equals(DamunSubjBean.FSCALE_QUESTION)) {
          
	          double d1 = 0; 
	          double d2 = 0; 
	          double d3 = 0; 
	          double d4 = 0; 
			  int person1 = 0;
			  int person2 = 0;
			  int person3 = 0;
			  int person4 = 0;
			  double v_point1 = 0;
			  double v_point2 = 0;
			  double v_point3 = 0;
			  double v_point4 = 0;
			  double v_point5 = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d1 += (double) (subdata.getSubjectcnt()) * subdata.getSelpoint();
					person1 += subdata.getSubjectcnt();
				}
            }	
        if(person1>0)		v_point1 = d1 / person1;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d2 += (double) (subdata.getRelation1cnt()) * subdata.getSelpoint();
					person2 += subdata.getRelation1cnt();
				}
            }	
               
        if(person2>0)		v_point2 = d2 / person2;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d3 += (double) (subdata.getRelation2cnt()) * subdata.getSelpoint();
					person3 += subdata.getRelation2cnt();
				}
            }	
               
        if(person3>0)		v_point3 = d3 / person3;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d4 += (double) (subdata.getRelation3cnt()) * subdata.getSelpoint();
					person4 += subdata.getRelation3cnt();
				}
            }	
               
        if(person4>0)		v_point4 = d4 / person4;	  

 
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">
                     <tr>
                      <td width="5%" class="table_title">문제<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">              
                    <tr>
                      <td width="40%" style="padding-left=6" class="table_01"></td>
                      <td width="12%" class="table_01">상사진단</td>
                      <td width="12%" class="table_01">동료진단</td>
					  <td  width="12%" class="table_01">부하진단</td>
					  <td  width="12%" class="table_01">관찰자평균</td>
					  <td  width="12%" class="table_01">대상자본인</td>
					</tr>  
                    <tr>   
					  </td>
                      <td width="40%" class="table_02_1"><%=subdata.getScalename()%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point1)%></td>
                    </tr>
                 </table> 
				 <br>
<%
       } else if (data.getSultype().equals(DamunSubjBean.SSCALE_QUESTION)) {
          
	          double d1 = 0; 
	          double d2 = 0; 
	          double d3 = 0; 
	          double d4 = 0; 
			  int person1 = 0;
			  int person2 = 0;
			  int person3 = 0;
			  int person4 = 0;
			  double v_point1 = 0;
			  double v_point2 = 0;
			  double v_point3 = 0;
			  double v_point4 = 0;
			  double v_point5 = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d1 += (double) (subdata.getSubjectcnt()) * subdata.getSelpoint();
					person1 += subdata.getSubjectcnt();
				}
            }	
        if(person1>0)		v_point1 = d1 / person1;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d2 += (double) (subdata.getRelation1cnt()) * subdata.getSelpoint();
					person2 += subdata.getRelation1cnt();
				}
            }	
               
        if(person2>0)		v_point2 = d2 / person2;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d3 += (double) (subdata.getRelation2cnt()) * subdata.getSelpoint();
					person3 += subdata.getRelation2cnt();
				}
            }	
               
        if(person3>0)		v_point3 = d3 / person3;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d4 += (double) (subdata.getRelation3cnt()) * subdata.getSelpoint();
					person4 += subdata.getRelation3cnt();
				}
            }	
               
        if(person4>0)		v_point4 = d4 / person4;	  

 
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">
                     <tr>
                      <td width="5%" class="table_title">문제<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">              
                    <tr>
                      <td width="40%" style="padding-left=6" class="table_01"></td>
                      <td width="12%" class="table_01">상사진단</td>
                      <td width="12%" class="table_01">동료진단</td>
					  <td  width="12%" class="table_01">부하진단</td>
					  <td  width="12%" class="table_01">관찰자평균</td>
					  <td  width="12%" class="table_01">대상자본인</td>
					</tr>  
                    <tr>   
					  </td>
                      <td width="40%" class="table_02_1"><%=subdata.getScalename()%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point1)%></td>
                    </tr>
                 </table> 
				 <br>
<%
       } else if (data.getSultype().equals(DamunSubjBean.GSCALE_QUESTION)) {
               
              String before = "";
			  String after = "";
	          double d1 = 0; 
	          double d2 = 0; 
	          double d3 = 0; 
	          double d4 = 0; 
			  int person1 = 0;
			  int person2 = 0;
			  int person3 = 0;
			  int person4 = 0;
			  double v_point1 = 0;
			  double v_point2 = 0;
			  double v_point3 = 0;
			  double v_point4 = 0;
			  double v_point5 = 0;

	          double ad1 = 0; 
	          double ad2 = 0; 
	          double ad3 = 0; 
	          double ad4 = 0; 
			  int aperson1 = 0;
			  int aperson2 = 0;
			  int aperson3 = 0;
			  int aperson4 = 0;
			  double v_apoint1 = 0;
			  double v_apoint2 = 0;
			  double v_apoint3 = 0;
			  double v_apoint4 = 0;
			  double v_apoint5 = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
						before = subdata.getScalename();
					d1 += (double) (subdata.getSubjectcnt()) * subdata.getSelpoint();
					person1 += subdata.getSubjectcnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
						after = subdata.getScalename();            
					ad1 += (double) (subdata.getGSubjectcnt()) * subdata.getSelpoint();
					aperson1 += subdata.getGSubjectcnt();						
					} 
				}
            }	

        if(person1>0)		v_point1 = d1 / person1;	  
        if(aperson1>0)		v_apoint1 = ad1 / aperson1;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
					d2 += (double) (subdata.getRelation1cnt()) * subdata.getSelpoint();
					person2 += subdata.getRelation1cnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
					ad2 += (double) (subdata.getGRelation1cnt()) * subdata.getSelpoint();
					aperson2 += subdata.getGRelation1cnt();						
					} 
				}
            }	
               
        if(person2>0)		v_point2 = d2 / person2;	  
        if(aperson2>0)		v_apoint2 = ad2 / aperson2;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
					d3 += (double) (subdata.getRelation2cnt()) * subdata.getSelpoint();
					person3 += subdata.getRelation2cnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
					ad3 += (double) (subdata.getGRelation2cnt()) * subdata.getSelpoint();
					aperson3 += subdata.getGRelation2cnt();						
					} 
				}
            }	

        if(person3>0)		v_point3 = d3 / person3;	  
        if(aperson3>0)		v_apoint3 = ad3 / aperson3;	  
               
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
					d4 += (double) (subdata.getRelation3cnt()) * subdata.getSelpoint();
					person4 += subdata.getRelation3cnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
					ad4 += (double) (subdata.getGRelation3cnt()) * subdata.getSelpoint();
					aperson4 += subdata.getGRelation3cnt();						
					} 
				}
            }	
               
        if(person4>0)		v_point4 = d4 / person4;	  
        if(aperson4>0)		v_apoint4 = ad4 / aperson4;	  

 
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">
                     <tr>
                      <td width="5%" class="table_title">문제<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">              
                    <tr>
                      <td width="40%" style="padding-left=6" class="table_01"></td>
                      <td width="12%" class="table_01">상사진단</td>
                      <td width="12%" class="table_01">동료진단</td>
					  <td  width="12%" class="table_01">부하진단</td>
					  <td  width="12%" class="table_01">관찰자평균</td>
					  <td  width="12%" class="table_01">대상자본인</td>
					</tr>  
                    <tr>   
					  </td>
                      <td width="40%" class="table_01"><%=before%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point1)%></td>
                    </tr>
                    <tr>   
					  </td>
                      <td width="40%" class="table_01"><%=after%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((ad2+ad3+ad4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint1)%></td>
                    </tr>
                    <tr>   
					  </td>
                      <td width="40%" class="table_01">GAP비교</td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint2-v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint3-v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint4-v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format((v_replyobcount > 0 ? ((ad2+ad3+ad4)/v_replyobcount) : 0)-(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0))%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint1-v_point1)%></td>
                    </tr>
                 </table> 
				 <br>

<%    
 	   } 
    } 
  
  }  else if ((dbox.getString("d_d_gubun")).equals("2")) {
%>

                  <!----------------- 설문분석 시작 ----------------->
                 <table cellspacing="1" cellpadding="0" class="box_table_out">
                    <tr>
                     <td colspan="5" class="table_top_line"></td>
                    </tr>
                    <tr>
                      <td class="table_title"></td>
					  <td class="table_title">관찰자 총인원</td>
                      <td class="table_title">관찰자 참여인원</td>
                      <td class="table_title">대상자 총인원</td>
                      <td class="table_title">대상자 참여인원</td>
                    </tr>
                    <tr>
                      <td class="table_02_1">사전평가</td>
                      <td class="table_02_1"><%=v_studentobcount%></td>
                      <td class="table_02_1"><%=v_replyobcount%></td>
                      <td class="table_02_1"><%=v_studentcount%></td>
                      <td class="table_02_1"><%=v_replycount%></td>
                    </tr>
                    <tr>
                      <td class="table_02_1">사후평가</td>
                      <td class="table_02_1"><%=v_studentobcount2%></td>
                      <td class="table_02_1"><%=v_replyobcount2%></td>
                      <td class="table_02_1"><%=v_studentcount2%></td>
                      <td class="table_02_1"><%=v_replycount2%></td>
                    </tr>
                </table>
				<br>

<%

    for (int i=0; i < list.size(); i++) {
        data = (DamunQuestionExampleData)list.get(i);
        if (data.getSultype().equals(DamunSubjBean.OBJECT_QUESTION) || data.getSultype().equals(DamunSubjBean.MULTI_QUESTION)) { %>        
                 <table cellspacing="1" cellpadding="0" class="box_table_out">
					<tr>
                      <td width="5%" class="table_title">문제<%=i+1%></td>
                      <td colspan="5" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">              
                    <tr>
                      <td width="5%" class="table_01">No.</td>
                      <td width="29%" style="padding-left=6" class="table_01">보기</td>
                      <td width="34%" class="table_01">그래프</td>
                      <td width="5%" class="table_01">%</td>
					  <td  width="5%" class="table_01">집계</td>
					  <td  width="7%" class="table_01">대상자본인</td>
					  <td  width="5%" class="table_01">상사</td>
					  <td  width="5%" class="table_01">동료</td>
					  <td  width="5%" class="table_01">부하</td>
					</tr>  
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { %>
                    <tr>
                      <td width="5%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td width="34%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>%" class="graph">&nbsp;</td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
                      <td width="5%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
					  <td  width="5%" class="table_02_1"><%=subdata.getReplycnt()%></td>
					  <td  width="7%" class="table_02_1"><%=subdata.getSubjectcnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation1cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation2cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation3cnt()%></td>
                    </tr>
<%              }
            }
%>
                 </table> 
				 <br>
<%       } else if (data.getSultype().equals(DamunSubjBean.SUBJECT_QUESTION)) {
          
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">
                    <tr>
                      <td width="5%" class="table_title">문제<%=i+1%></td>
                      <td colspan="5" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">    
<%          for (int j=0; j < data.getSubjectAnswer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getSubjectAnswer().get(j)%></td>
                    </tr>

<%         }
%>
<%          for (int j=0; j < data.getR1Answer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getR1Answer().get(j)%></td>
                    </tr>

<%         }
%>
<%          for (int j=0; j < data.getR2Answer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getR2Answer().get(j)%></td>
                    </tr>

<%         }
%>
<%          for (int j=0; j < data.getR3Answer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getR3Answer().get(j)%></td>
                    </tr>

<%         }
%>
                 </table> 
				 <br>
<%
           	
       } else if (data.getSultype().equals(DamunSubjBean.COMPLEX_QUESTION)) {
          
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">
                    <tr>
                      <td width="5%" class="table_title">문제<%=i+1%></td>
                      <td colspan="5" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">              
                    <tr>
                      <td width="5%" class="table_01">No.</td>
                      <td width="29%" style="padding-left=6" class="table_01">보기</td>
                      <td width="34%" class="table_01">그래프</td>
                      <td width="5%" class="table_01">%</td>
					  <td  width="5%" class="table_01">집계</td>
					  <td  width="7%" class="table_01">대상자본인</td>
					  <td  width="5%" class="table_01">상사</td>
					  <td  width="5%" class="table_01">동료</td>
					  <td  width="5%" class="table_01">부하</td>
					</tr>  
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { %>
                    <tr>
                      <td width="5%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td width="34%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>%" class="graph">&nbsp;</td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
                      <td width="5%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
					  <td  width="5%" class="table_02_1"><%=subdata.getReplycnt()%></td>
					  <td  width="7%" class="table_02_1"><%=subdata.getSubjectcnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation1cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation2cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation3cnt()%></td>
                    </tr>
<%           } 
           }	
%>
<%          for (int j=0; j < data.getComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
<%          for (int j=0; j < data.getR1ComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getR1ComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
<%          for (int j=0; j < data.getR2ComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getR2ComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
<%          for (int j=0; j < data.getR3ComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getR3ComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
                 </table> 
				 <br>
<%
       } else if (data.getSultype().equals(DamunSubjBean.FSCALE_QUESTION)) {
          
	          double d1 = 0; 
	          double d2 = 0; 
	          double d3 = 0; 
	          double d4 = 0; 
			  int person1 = 0;
			  int person2 = 0;
			  int person3 = 0;
			  int person4 = 0;
			  double v_point1 = 0;
			  double v_point2 = 0;
			  double v_point3 = 0;
			  double v_point4 = 0;
			  double v_point5 = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d1 += (double) (subdata.getSubjectcnt()) * subdata.getSelpoint();
					person1 += subdata.getSubjectcnt();
				}
            }	
        if(person1>0)		v_point1 = d1 / person1;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d2 += (double) (subdata.getRelation1cnt()) * subdata.getSelpoint();
					person2 += subdata.getRelation1cnt();
				}
            }	
               
        if(person2>0)		v_point2 = d2 / person2;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d3 += (double) (subdata.getRelation2cnt()) * subdata.getSelpoint();
					person3 += subdata.getRelation2cnt();
				}
            }	
               
        if(person3>0)		v_point3 = d3 / person3;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d4 += (double) (subdata.getRelation3cnt()) * subdata.getSelpoint();
					person4 += subdata.getRelation3cnt();
				}
            }	
               
        if(person4>0)		v_point4 = d4 / person4;	  

 
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">
                     <tr>
                      <td width="5%" class="table_title">문제<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">              
                    <tr>
                      <td width="40%" style="padding-left=6" class="table_01"></td>
                      <td width="12%" class="table_01">상사진단</td>
                      <td width="12%" class="table_01">동료진단</td>
					  <td  width="12%" class="table_01">부하진단</td>
					  <td  width="12%" class="table_01">관찰자평균</td>
					  <td  width="12%" class="table_01">대상자본인</td>
					</tr>  
                    <tr>   
					  </td>
                      <td width="40%" class="table_02_1"><%=subdata.getScalename()%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point1)%></td>
                    </tr>
                 </table> 
				 <br>
<%
       } else if (data.getSultype().equals(DamunSubjBean.SSCALE_QUESTION)) {
          
	          double d1 = 0; 
	          double d2 = 0; 
	          double d3 = 0; 
	          double d4 = 0; 
			  int person1 = 0;
			  int person2 = 0;
			  int person3 = 0;
			  int person4 = 0;
			  double v_point1 = 0;
			  double v_point2 = 0;
			  double v_point3 = 0;
			  double v_point4 = 0;
			  double v_point5 = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d1 += (double) (subdata.getSubjectcnt()) * subdata.getSelpoint();
					person1 += subdata.getSubjectcnt();
				}
            }	
        if(person1>0)		v_point1 = d1 / person1;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d2 += (double) (subdata.getRelation1cnt()) * subdata.getSelpoint();
					person2 += subdata.getRelation1cnt();
				}
            }	
               
        if(person2>0)		v_point2 = d2 / person2;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d3 += (double) (subdata.getRelation2cnt()) * subdata.getSelpoint();
					person3 += subdata.getRelation2cnt();
				}
            }	
               
        if(person3>0)		v_point3 = d3 / person3;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d4 += (double) (subdata.getRelation3cnt()) * subdata.getSelpoint();
					person4 += subdata.getRelation3cnt();
				}
            }	
               
        if(person4>0)		v_point4 = d4 / person4;	  

 
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">
                     <tr>
                      <td width="5%" class="table_title">문제<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">              
                    <tr>
                      <td width="40%" style="padding-left=6" class="table_01"></td>
                      <td width="12%" class="table_01">상사진단</td>
                      <td width="12%" class="table_01">동료진단</td>
					  <td  width="12%" class="table_01">부하진단</td>
					  <td  width="12%" class="table_01">관찰자평균</td>
					  <td  width="12%" class="table_01">대상자본인</td>
					</tr>  
                    <tr>   
					  </td>
                      <td width="40%" class="table_02_1"><%=subdata.getScalename()%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point1)%></td>
                    </tr>
                 </table> 
				 <br>
<%
       } else if (data.getSultype().equals(DamunSubjBean.GSCALE_QUESTION)) {
               
              String before = "";
			  String after = "";
	          double d1 = 0; 
	          double d2 = 0; 
	          double d3 = 0; 
	          double d4 = 0; 
			  int person1 = 0;
			  int person2 = 0;
			  int person3 = 0;
			  int person4 = 0;
			  double v_point1 = 0;
			  double v_point2 = 0;
			  double v_point3 = 0;
			  double v_point4 = 0;
			  double v_point5 = 0;

	          double ad1 = 0; 
	          double ad2 = 0; 
	          double ad3 = 0; 
	          double ad4 = 0; 
			  int aperson1 = 0;
			  int aperson2 = 0;
			  int aperson3 = 0;
			  int aperson4 = 0;
			  double v_apoint1 = 0;
			  double v_apoint2 = 0;
			  double v_apoint3 = 0;
			  double v_apoint4 = 0;
			  double v_apoint5 = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
						before = subdata.getScalename();
					d1 += (double) (subdata.getSubjectcnt()) * subdata.getSelpoint();
					person1 += subdata.getSubjectcnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
						after = subdata.getScalename();            
					ad1 += (double) (subdata.getGSubjectcnt()) * subdata.getSelpoint();
					aperson1 += subdata.getGSubjectcnt();						
					} 
				}
            }	

        if(person1>0)		v_point1 = d1 / person1;	  
        if(aperson1>0)		v_apoint1 = ad1 / aperson1;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
					d2 += (double) (subdata.getRelation1cnt()) * subdata.getSelpoint();
					person2 += subdata.getRelation1cnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
					ad2 += (double) (subdata.getGRelation1cnt()) * subdata.getSelpoint();
					aperson2 += subdata.getGRelation1cnt();						
					} 
				}
            }	
               
        if(person2>0)		v_point2 = d2 / person2;	  
        if(aperson2>0)		v_apoint2 = ad2 / aperson2;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
					d3 += (double) (subdata.getRelation2cnt()) * subdata.getSelpoint();
					person3 += subdata.getRelation2cnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
					ad3 += (double) (subdata.getGRelation2cnt()) * subdata.getSelpoint();
					aperson3 += subdata.getGRelation2cnt();						
					} 
				}
            }	

        if(person3>0)		v_point3 = d3 / person3;	  
        if(aperson3>0)		v_apoint3 = ad3 / aperson3;	  
               
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
					d4 += (double) (subdata.getRelation3cnt()) * subdata.getSelpoint();
					person4 += subdata.getRelation3cnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
					ad4 += (double) (subdata.getGRelation3cnt()) * subdata.getSelpoint();
					aperson4 += subdata.getGRelation3cnt();						
					} 
				}
            }	
               
        if(person4>0)		v_point4 = d4 / person4;	  
        if(aperson4>0)		v_apoint4 = ad4 / aperson4;	  

 
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">
                     <tr>
                      <td width="5%" class="table_title">문제<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>
                 <table cellspacing="1" cellpadding="0" class="box_table_out">              
                    <tr>
                      <td width="40%" style="padding-left=6" class="table_01"></td>
                      <td width="12%" class="table_01">상사진단</td>
                      <td width="12%" class="table_01">동료진단</td>
					  <td  width="12%" class="table_01">부하진단</td>
					  <td  width="12%" class="table_01">관찰자평균</td>
					  <td  width="12%" class="table_01">대상자본인</td>
					</tr>  
                    <tr>   
					  </td>
                      <td width="40%" class="table_01"><%=before%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point1)%></td>
                    </tr>
                    <tr>   
					  </td>
                      <td width="40%" class="table_01"><%=after%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((ad2+ad3+ad4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint1)%></td>
                    </tr>
                    <tr>   
					  </td>
                      <td width="40%" class="table_01">GAP비교</td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint2-v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint3-v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint4-v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format((v_replyobcount > 0 ? ((ad2+ad3+ad4)/v_replyobcount) : 0)-(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0))%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint1-v_point1)%></td>
                    </tr>
                 </table> 
				 <br>

<%    
 	   } 
    }   
  
  }
%>

                  <!----------------- 설문분석 끝 ----------------->
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>


  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

</table>
</body>
</html>