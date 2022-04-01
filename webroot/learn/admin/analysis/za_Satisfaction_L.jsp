<%
//**********************************************************
//  1. 제      목: 분석 > 만족도
//  2. 프로그램명: za_JobInclination_L.java
//  3. 개      요: 만족도 분석
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: msc 
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_tem_grcode = box.getSession("tem_grcode");
    String  v_grcode    = box.getStringDefault("s_grcode",v_tem_grcode);        //교육그룹
    
    // 셀렉트 검색 관련 (09.12.10)
    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getString("s_grseq");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수
    String  ss_sulpapernum  = box.getString("s_sulpapernum");     //설문지
    
    

//    String  v_subj        = box.getStringDefault("p_subj",  SulmunSubjBean.DEFAULT_SUBJ);
    String  v_subj			= box.getString("s_subjcourse");
    String  v_gyear			= box.getString("s_gyear");
    String  v_subjseq       = box.getString("s_subjseq");
	int		v_sulpapernum	= box.getInt("s_sulpapernum");

    String  v_company       = box.getString("s_company");
    String  v_jikwi			= box.getString("s_jikwi");
    String  v_jikun			= box.getString("s_jikun");
    String  v_workplc       = box.getString("s_workplc");
    
    int     v_replycount	= box.getInt("p_replycount");
    int     v_studentcount	= box.getInt("p_studentcount");
	int		v_replycnt		= 0;
    double  v_replyrate		= 0;

    if (v_studentcount != 0) {
        v_replyrate = (double)Math.round((double)v_replycount/v_studentcount*100*100)/100;
    }
    
	String  s_gadmin = box.getSession("gadmin");

    String v_tab_color1 = "black";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
	String v_tab_color4 = "black";
	String v_tab_color5 = "blue";
	DecimalFormat  df = new DecimalFormat("0.00");
%>

<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script> 
<script language="JavaScript">
<!--
// 조회 검색
function whenSelection(p_action) {
    var v_grcode   = document.form1.s_grcode.value;
    var v_subj     = document.form1.s_subjcourse.value;
    var v_subjseq  = document.form1.s_subjseq.value;

    if (p_action=="go") {
		if (document.form1.s_grcode.value == 'ALL') {
			alert("교육그룹을 선택하세요.");
			return ;
		}
		if (document.form1.s_gyear.value=="ALL"){
            alert("년도를 선택하세요");
            return;
        }
		if (document.form1.s_grseq.value=="ALL"){
            alert("교육 차수를 선택하세요");
            return;
        }
		/*if (document.form1.s_subjcourse.value=="ALL"){
            alert("과정을 선택하세요");
            return;
        }
		if (document.form1.s_subjseq.value=="ALL"){
            alert("과정 차수를 선택하세요");
            return;
        }*/

    }

  document.form1.target = "_self";
  document.form1.p_action.value  = p_action;
  document.form1.p_process.value = 'satisfactionList';
  document.form1.submit();
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
    }

    var v_grcode   = document.form1.s_grcode.value;
    var v_subj     = document.form1.s_subjcourse.value;
    var v_subjseq  = document.form1.s_subjseq.value;
	var v_param  = '?p_grcode='+v_grcode+'&p_subj='+v_subj+'&p_subjseq='+v_subjseq; 

    window.self.name = "reporting";
    document.form1.target = "openExcel1";    
    open_window("openExcel1","","100","100","900","600","yes","yes","yes","yes","yes");                 
    document.form1.action='/learn/admin/research/SubjResult_E.jsp'+v_param;                 
    document.form1.submit();
}


// 개인별 보기
function UserPage(p_grcode, p_subj, p_sulpapernum, name, userid, answers) {
  window.self.name = "winSelectView";
  farwindow =   window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=678, height=600, top=0, left=0");
  document.form1.target = "openWinQuestion";
  document.form1.p_process.value = "SulmunUserPage";
  document.form1.p_grcode.value  = p_grcode;
  document.form1.p_subj.value    = p_subj; 
  document.form1.p_sulpapernum.value = p_sulpapernum; 
  document.form1.p_userid.value = userid;
  document.form1.p_name.value = name;  
  document.form1.p_answers.value = answers;  
  document.form1.submit();
 
  //farwindow.window.focus();
  //document.form1.target = window.self.name;
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


// 엑셀결과분석출력
function goDetailExcel(p_action) {
	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (p_action=="go") {
        if (document.form1.s_grseq.value=="----"){
            alert("교육차수를 선택하세요");
            return;
        }    
        if (document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
            return;
        }
        if (document.form1.s_subjseq.value=="----"){
            alert("차수를 선택하세요");
            return;
        }
        if (document.form1.s_sulpapernum.value=='0'){
            alert("설문지를 선택하세요");
            return;
        }
// top.ftop.setPam();
    }

  window.self.name = "SulmunResultPage";     
  farwindow = open_window("openDetailExcel","","100","100","700","400","yes","yes","yes","yes","yes");  
  document.form1.p_action.value  = 'go';
  document.form1.target = "openDetailExcel";               
  document.form1.p_process.value = 'SulmunDetailResultExcelPage';   
  document.form1.p_sulpapernum.value = document.form1.s_sulpapernum.options[document.form1.s_sulpapernum.selectedIndex].value;
  document.form1.p_grcode.value = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;
  document.form1.p_subj.value = document.form1.s_subjcourse.options[document.form1.s_subjcourse.selectedIndex].value;
  document.form1.p_gyear.value = document.form1.s_gyear.options[document.form1.s_gyear.selectedIndex].value;
  document.form1.submit(); 
  document.form1.target = window.self.name;
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
  <form name="form1" method="post" action="/servlet/controller.analysis.InclinationAdminServlet">
	<input type="hidden" name="p_process"		value="">
	<input type="hidden" name="p_action"		value="">
	<input type="hidden" name="p_grcode"		value="<%=v_grcode%>">
	<input type="hidden" name="p_gyear"			value="<%=v_gyear%>">
	<input type="hidden" name="p_subj"			value="<%=v_subj%>">
	<input type="hidden" name="p_subjsel"		value="<%=v_subj%>">
	<input type="hidden" name="p_subjseq"		value="<%=v_subjseq%>">
	<input type="hidden" name="p_sulpapernum"   value="<%=v_sulpapernum%>">
	<input type="hidden" name="p_gubun"			value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/analysis/tit_analysis_stu3.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

	  <table cellspacing="0" cellpadding="1" class="form_table_out">
		<tr>
		  <td bgcolor="#C6C6C6" align="center">
			<table cellspacing="0" cellpadding="0" class="form_table_bg" >
			  <tr>
				<td height="7"></td>
			  </tr>
			  <tr>
				<td align="center" valign="middle">
				  <table cellspacing="0" cellpadding="0" width="99%" class="form_table">
					<tr valign="top">
					  <td>
					    <script type="text/javascript">
                        var startup = 0;
                        function Main_s_subjseq(){
                            if (startup > 1)
                                changes_grseq(document.form1.s_grcode.value, document.form1.s_gyear.value);
                        }
                        function Main_subjcourse(){
                            if(startup > 1) 
                                changes_subjcourse(document.form1.s_grcode.value, document.form1.s_upperclass.value, document.form1.s_middleclass.value, document.form1.s_lowerclass.value, document.form1.s_grseq.value,'' );
                            startup++;
                        }
                        </script>
					    <font color="red">★</font> 
						교육그룹 <kocca_select:select name="s_grcode" sqlNum="course.0001"  param=" "
                        onChange="changes_gyear(this.value);" attr=" " selectedValue="<%= ss_grcode %>" isLoad="true" all="false" />
						<font color="red">★</font> 
						연도 <kocca_select:select name="s_gyear" sqlNum="course.year"  param="<%= ss_grcode %>"
                                onChange="changes_grseq(s_grcode.value, this.value);" afterScript="Main_subjcourse" attr=" " selectedValue="<%= ss_gyear %>" isLoad="true" all="false" />
                        <font color="red">★</font>                                         
                                                                교육차수 <kocca_select:select name="s_grseq" sqlNum="course.subjseq"
                                param="<%=ss_grcode%>" param2="<%=ss_gyear%>"
                                attr=" style='width:130px;'" onChange="Main_subjcourse();" afterScript="" selectedValue="<%= ss_grseq %>" isLoad="true" all="true" />
                                                                대분류 <kocca_select:select name="s_upperclass" sqlNum="course.0002"  param=" "
                                onChange="changes_middleclass(this.value);Main_subjcourse();" attr=" "  selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
                                                                중분류 <kocca_select:select name="s_middleclass" sqlNum="course.0003"  param="<%= ss_upperclass %>"
                                onChange="changes_lowerclass(s_upperclass.value, this.value);Main_subjcourse();" attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" />
                                                                소분류 <kocca_select:select name="s_lowerclass" sqlNum="course.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>"
                                onChange="Main_subjcourse();" attr=" " selectedValue="<%= ss_lowerclass %>" isLoad="true" all="true" />
					  </td>
					</tr>
					<tr height="10"><td></td></tr>
					<tr valign="bottom">
					  <td>
					   <font color="red">★</font> 
					           과정 <kocca_select:select name="s_subjcourse" sqlNum="subjlist1|subjlist2|subjlist3|subjlist4|subjlist5|subjlist6|subjlist7|subjlist8|subjlist9"
                                        param="<%=ss_grcode%>"param4="<%=ss_upperclass%>"param5="<%=ss_middleclass%>"param6="<%=ss_lowerclass%>"
                                        param7="<%=ss_grseq%>" param9=""
                                        onChange="changes_subjseq(s_grcode.value, s_grseq.value, s_subjcourse.value, s_gyear.value)" attr=" " selectedValue="<%= ss_subjcourse %>" isLoad="true" all='<%= box.getSession("isSubjAll") %>' />
                      <font color="red">★</font>                                 
                                                             차수 <kocca_select:select name="s_subjseq" sqlNum="subjectSeq1|subjectSeq2|subjectSeq3|subjectSeq4"
                                        param="<%=ss_grcode%>" param2="<%=ss_grseq %>" param3="<%=ss_subjcourse%>" param4="<%=ss_gyear%>"
                                        onChange="changes_sulpapernum(s_grcode.value,s_gyear.value, s_subjcourse.value,this.value)" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="true" />
					   &nbsp;<font color="red">★</font> 
					        설문지<kocca_select:select name="s_sulpapernum" sqlNum="satisfaction01|satisfaction02|satisfaction03"
                                        param="<%=ss_grcode%>" param2="<%=ss_gyear %>" param3="<%=ss_subjcourse%>" param4="<%=ss_subjseq%>"
                                        onChange="" attr=" " selectedValue="<%= ss_sulpapernum %>" isLoad="true" all="true" />
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
	  <br>
	  <br>

      <table cellspacing="1" cellpadding="5" class="table_out" border="0" bordercolor="red">
        <tr>
          <td colspan="23" class="table_top_line"></td>
        </tr>
		<tr class="table_title_01">
			<td width="7%"> 항목 </td><td width="10"></td><td width="10%"> 척도 </td><td width="10%"> 인원 </td><td width="39%"> 통계 </td>
		</tr>
		<tr height="2" bgcolor=white>
			<td colspan="5"></td>
		</tr>

<%  ArrayList    list = (ArrayList)request.getAttribute("SulmunResultList");
    SulmunQuestionExampleData data    = null;
    SulmunExampleData         subdata = null;

	int k = 0;
	int l = 0;

    for (int i=0; i < list.size(); i++) {
        data = (SulmunQuestionExampleData)list.get(i);
	  if(data.getSultype().equals("5") || data.getSultype().equals("6")){

        if (data.getSultype().equals(SulmunSubjBean.OBJECT_QUESTION) || data.getSultype().equals(SulmunSubjBean.MULTI_QUESTION)) { %>        
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] "%><%=" 1 : "+data.getSultype()%></td>
                    </tr>
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { %>
                    <tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                      <td width="39%" class="table_02_2">
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
                    </tr>
<%              }
            }
       } else if (data.getSultype().equals(SulmunSubjBean.SUBJECT_QUESTION)) {
          
%>
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] "%><%=" 2 : "+data.getSultype()%></td>
                    </tr>
<%        int m = 0;   //out.println(data.getSubjectAnswer().size());//out.println(v_replycount);
			for (int j=0; j < data.getSubjectAnswer().size(); j++) {
				if(((data.getSubjectAnswer().size() / v_replycount)*m + (k+1)) == (j+1)) {
					if(m < v_replycount) m++;
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
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] "%><%=" 3 : "+data.getSultype()%></td>
                    </tr>
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { %>
                    <tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                      <td width="39%" class="table_02_2">
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
                      <td  colspan="4" style="padding-left=6" class="table_02_2"><%=(String)data.getComplexAnswer().get(j)%></td>
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
					person += subdata.getReplycnt();
				}
            }	
               
		v_point = d / person;	  
%>
                    <tr>
                      <td colspan="5" bgcolor="#C6EDF9" style="padding-left=70"><b><%=data.getSultext()%><%//=" 4 : "+data.getSultype()%></b></td>
                    </tr>
<%

		   for (int j=1; j <= data.size(); j++) {

                subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { 
					 v_replycnt += subdata.getReplycnt();
					 System.out.println("seq : " + j);
					 System.out.println("v_replycnt : "+v_replycnt);
					 System.out.println("subdata.getReplycnt() : "+subdata.getReplycnt());
					 System.out.println("================================================================" + j);
%>

					<tr>
                      <td width="7%" class="table_01"><%=j%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%="( "+subdata.getSelpoint()+" 점)"%></td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="39%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>%" class="graph">&nbsp;&nbsp;&nbsp;<b>(<%=subdata.getReplyrate()%>%)</b></td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
                    </tr>
<%           }    
            }%>	
					<tr>
                      <td width="40%" class="table_01" colspan="3">계</td>
                      <td width="11%" class="table_01"><%=v_replycnt%>명</td>
                      <td width="39%" class="table_01"><%if(v_point >=0){%><%=df.format(v_point)%>점<%}%></td>
                    </tr>
<%       } else if (data.getSultype().equals(SulmunSubjBean.SSCALE_QUESTION)) {
			  v_replycnt = 0;
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
                      <td colspan="5" bgcolor="#C6EDF9" style="padding-left=70"><b><%=data.getSultext()%><%//=" 5 : "+data.getSultype()%></b></td>
                    </tr>
<%

		   for (int j=1; j <= data.size(); j++) {

                subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { 
					 v_replycnt += subdata.getReplycnt();
%>

					<tr>
                      <td width="7%"   class="table_01"><%=j%></td>
                      <td width="29%"  style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td width="11%"  class="table_02_1"><%="( "+subdata.getSelpoint()+" 점)"%></td>
                      <td width="11%"  class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="39%"  class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>%" class="graph">&nbsp;&nbsp;&nbsp;<b>(<%=subdata.getReplyrate()%>%)</b></td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                      </table>
                      </td>
                    </tr>
<%				 }
			  }	%>
				  	<tr>
                      <td width="40%" class="table_01" colspan="3">계</td>
                      <td width="11%" class="table_01"><%=v_replycnt%>명</td>
                      <td width="39%" class="table_01"><%if(v_point >=0){%><%=df.format(v_point)%>점<%}%></td>
                    </tr>
<%	   } 
        v_replycnt = 0;
}else{%>

<%}
}%>
<%if( data == null ){%>
				<tr class="table_01" height="35">
					<td colspan="5">과정만족도에 대한 평가가 없습니다.</td>
				</tr>
<%}%>

	  </table>


  </tr>


  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

</table>
</form>                  


