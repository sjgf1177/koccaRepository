<%
//**********************************************************
//  1. 제      목: 과정운영 통계 - 차수별 사전설문
//  2. 프로그램명 : za_CoursePreSulmun_L.jsp
//  3. 개      요: 학습현황
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.11
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String	ss_grcode			= box.getString("s_grcode");		//교육그룹
    String	ss_gyear			= box.getString("s_gyear");			//년도
    String	ss_grseq			= box.getString("s_grseq");			//교육차수
    String	ss_grseqnm			= box.getString("s_grseqnm");		//교육차수명
    String  ss_upperclass		= box.getString("s_upperclass");	//과정대분류
    String  ss_middleclass		= box.getString("s_middleclass");	//과정중분류
    String  ss_lowerclass		= box.getString("s_lowerclass");	//과정소분류
    String  ss_startdate		= box.getString("s_startdate");		//기간조회 시작
    String  ss_enddate			= box.getString("s_enddate");		//기간조회 끝
    String  ss_option1			= box.getString("s_option1");		//통계구분
    String  ss_option2			= box.getString("s_option2");		//통계별 구분2
    String  ss_option3			= box.getString("s_option3");		//통계별 구분3
    
    if (ss_enddate.equals("")) {
    	ss_startdate	= FormatDate.getDateAdd(FormatDate.getDate("yyyyMMdd"), "yyyy-MM-dd", "month", -1);
    	ss_enddate		= FormatDate.getFormatDate(FormatDate.getDate("yyyyMMdd"), "yyyy-MM-dd");
    }
    
    String  v_action	= box.getString("p_action");
	String  v_orderType	= box.getStringDefault("p_orderType"," asc"); //정렬순서
	
    int v_today = Integer.parseInt(FormatDate.getDate("yyyyMMdd"));  //오늘 날짜 일시 초기화
    int i_eduend   = 0;
    
    int     v_replycount  = box.getInt("p_replycount");
    int     v_studentcount= box.getInt("p_studentcount");
    double  v_replyrate   = 0;
    if (v_studentcount != 0) {
        v_replyrate = (double)Math.round((double)v_replycount/v_studentcount*100*100)/100;
    }

	DecimalFormat  df = new DecimalFormat("0.00");
    
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript">
$(document).ready(function(){
	$("#s_startdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#s_enddate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
// 검색
function whenSelection(ss_action) {
	var process = 'selectPreSulmunListPre';            //검색전
  
	if (ss_action=="go")  {
		if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return;
		}
		if (document.form1.s_grseq.value == 'ALL' || document.form1.s_grseq.value == '') {
		   alert("교육차수를 선택하세요.");
		   return;
		}
		top.ftop.setPam();
		process='selectPreSulmunList';   //검색후
	}
	document.form1.target = "_self";  
	document.form1.action = "/servlet/controller.course.CourseStateAdminServlet";
	document.form1.p_action.value = ss_action;
	document.form1.p_process.value = process;
	document.form1.submit();
}

// 정렬
function ordering(column) {
	if (document.form1.p_orderType.value == " asc") {
		document.form1.p_orderType.value = " desc";
	} else {
		document.form1.p_orderType.value = " asc";
	}

	document.form1.p_order.value = column;
	whenSelection("go");
}

// 엑셀보기
function whenExcel() {
	if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
        alert("교육그룹을 선택하세요.");
        return ;
    }
    //window.self.name = "CourseSale";
    //open_window("openExcel1","","100","100","800","600","yes","yes","yes","yes","yes");
    //document.form1.target = "openExcel1";
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.course.CourseStateAdminServlet';
    document.form1.p_process.value = 'selectPreSulmunListExcel';
	document.form1.p_action.value = "go";
    document.form1.submit();
}

// 날짜 삭제(달력)
function whenclick(caldate) {
	caldate.value = "";
}

// 통계변경1
function selectOption1(p) {
	//alert(p);
	document.form1.target = "_self";  
	document.form1.action = "/servlet/controller.course.CourseStateAdminServlet";
	document.form1.p_action.value = "change";
	document.form1.p_process.value = p;
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
          <td><img src="/images/admin/course/co_title04.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!------------------------------------- form 시작 ------------------------------------->
      <form name = "form1" method = "post">
        <input type="hidden" name="p_process">
        <input type="hidden" name="p_action">
        <input type="hidden" name="p_order"     value="">
        <input type="hidden" name="p_orderType" value="">
        <input type="hidden" name="p_grcode" >
        <input type="hidden" name="p_grseq" >
        <input type="hidden" name="p_gyear">
        <input type="hidden" name="p_subj" >
        <input type="hidden" name="p_subjseq" >
        <input type="hidden" name="p_option3" value="">
      
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td align="left" valign="middle">
                        <!------------------- 조건검색 시작 ------------------------->
                        <font color="red">★</font> <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
                      </td>
                      <td align="left" valign="middle">
                        <font color="red">★</font> <%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도  -->
                      </td>
                      <td align="left" valign="middle">  
                        <font color="red">★</font> <%= SelectEduBean.getGrseq(box, true, true)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
                      </td>
                    </tr>
                    <tr>
                      <td height="5" colspan="4"></td>
                    </tr>
                    <tr>
                      <td align="left" valign="middle">
                        <%= SelectSubjBean.getUpperClass(box, true, true, true)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
                      </td>
                      <td align="left" valign="middle">
                        <%= SelectSubjBean.getMiddleClass(box, true, true, true)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
                      </td>
                      <td align="left" valign="middle">
                        <%= SelectSubjBean.getLowerClass(box, true, true, true)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
                      </td>
  		  		      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="5" colspan="4"></td>
                    </tr>
  		  		    <tr>
                      <td align="left" valign="middle">
                                                          조회기간&nbsp;<input name="s_startdate" id="s_startdate" value="<%=ss_startdate%>" type="text" class="datepicker_input1" size="10">&nbsp;
		  				  			~&nbsp;<input name="s_enddate" id="s_enddate" value="<%=ss_enddate%>" type="text" class="datepicker_input1" size="10">&nbsp;
                      </td>
                      <td align="left" valign="middle">
                                                         통계구분
                        <kocca_select:select name="s_option1" sqlNum="coursestatistics1"  param=" " onChange="selectOption1(this.value);" attr="통계구분1" selectedValue="selectPreSulmunListPre" isLoad="true" all="none" type="1" styleClass=""/>
                      </td>
                      <td align="left" valign="middle">
                      </td>
                      <td width="20%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></div><!-- whenSelection('go') --></td>
			        </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="9"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table><br>
      <!----------------- 엑셀출력 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" height="20">&nbsp;<b>차수별 사전설문</b></td>
          <td align="right">
            <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a>
          </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </form> 
      </table>          
      <!----------------- 통계조회 시작 ----------------->
      <table width="97%" cellspacing="1" cellpadding="5" class="table_out">
        <tr>
         <td colspan="5" class="table_top_line"></td>
        </tr>
<%  ArrayList    list = (ArrayList)request.getAttribute("selectList");
    SulmunQuestionExampleData data    = null;
    SulmunExampleData         subdata = null;

	int k = 0;
	int l = 0;

    for (int i=0; i < list.size(); i++) {
        data = (SulmunQuestionExampleData)list.get(i);
        if (data.getSultype().equals(SulmunSubjBean.OBJECT_QUESTION) || data.getSultype().equals(SulmunSubjBean.MULTI_QUESTION)) { %>
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                      <td class="table_title_01">선택인원</td>
                      <td class="table_title_01">점유율</td>
                    </tr>
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) { %>
                    <tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                    </tr>
<%              }
            }
       } else if (data.getSultype().equals(SulmunSubjBean.SUBJECT_QUESTION)) {

%>
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                      <td class="table_title_01">선택인원</td>
                      <td class="table_title_01">점유율</td>
                    </tr>
<%        int m = 0;   //out.println(data.getSubjectAnswer().size());//out.println(v_replycount);
			for (int j=0; j < data.getSubjectAnswer().size(); j++) {
				if(((data.getSubjectAnswer().size() / v_replycount)*m + (k+1)) == (j+1)) {
			//	if((m + (k+1)) == (j+1)) {
					if(m < v_replycount) m++;
				//	if(m < data.getSubjectAnswer().size()) m++;
%>
					<tr>
                      <td width="7%" class="table_01"></td>
                      <td  colspan="4" style="padding-left=6" class="table_02_2"><%=(String)data.getSubjectAnswer().get(j)%></td>
                    </tr>

<%           }
		   }
           k++;
       } else if (data.getSultype().equals(SulmunSubjBean.COMPLEX_QUESTION)) {

%>
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                      <td class="table_title_01">선택인원</td>
                      <td class="table_title_01">점유율</td>
                    </tr>
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) { %>
                    <tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                    </tr>
<%           }
           }
%>
<%       int n = 0;
			int etc = 0;
			for (int j=0; j < data.getComplexAnswer().size(); j++) {
				if(((data.getComplexAnswer().size() / v_replycount)*n + (l+1)) == (j+1)) {
					if(n < v_replycount) n++;
						if( !((String)data.getComplexAnswer().get(j)).equals("")) {
							etc++;
%>
					<tr>
                      <td width="7%" class="table_01"><%if(etc==1){%>기타<%}%></td>
                      <td style="padding-left=6" class="table_02_2"><%=(String)data.getComplexAnswer().get(j)%></td>
                      <td class="table_title_01">선택인원</td>
                      <td class="table_title_01">점유율</td>
                    </tr>
<%
					}
                }
		   }
           l++;
       } else if (data.getSultype().equals(SulmunSubjBean.FSCALE_QUESTION)) {

	          double d = 0;
			  int person = 0;
			  double v_point = 0;

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {

					d +=  (subdata.getReplycnt()) * subdata.getSelpoint();
					out.println("<!--subdata.getReplycnt() = " + subdata.getReplycnt() + "/-->");
					out.println("<!--subdata.getSelpoint() = " + subdata.getSelpoint() + "/-->");
					out.println("<!--d = " + d + "/-->");
					person += subdata.getReplycnt();
				}
            }

		v_point = d / person;
%>
<!-- here 4 -->
<!-- d=<%=d%> / person=<%=person%> / v_point=<%=v_point%> / data.getDistcodenm()=<%=data.getDistcodenm()%>-->
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                      <td class="table_title_01">선택인원</td>
                      <td class="table_title_01">점유율</td>
					  <!--td width="7%" class="table_01"><%if(v_point >=0){%><%=df.format(v_point)%>점<%}%></td-->
                    </tr>
<%

		   for (int j=1; j <= data.size(); j++) {

                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {
%>

					<tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                    </tr>
<%           }
            }
       } else if (data.getSultype().equals(SulmunSubjBean.SSCALE_QUESTION)) {

	          double d = 0;
			  int person = 0;
			  double v_point = 0;

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {

					d +=  (subdata.getReplycnt()) * subdata.getSelpoint();
					person += subdata.getReplycnt();
				}
            }

		v_point = d / person;
%>
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                      <td class="table_title_01">선택인원</td>
                      <td class="table_title_01">점유율</td>
					  <!--td width="7%" class="table_01"><%if(v_point >=0){%><%=df.format(v_point)%>점<%}%></td-->
                    </tr>
<%

		   for (int j=1; j <= data.size(); j++) {

                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {
%>

					<tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                    </tr>
<%           }
            }
	   }
    } %>
                  </table>
<%
			if (list.size() == 0 && "go".equals(v_action)) {
%>
      <table width="97%" cellspacing="1" cellpadding="5" class="box_table_out">
        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100">등록된 내용이 없습니다.</td>
        </tr>
      </table>
<%			}
%> 

    <!----------------- 통계조회 끝 ----------------->
    <br>
    </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>