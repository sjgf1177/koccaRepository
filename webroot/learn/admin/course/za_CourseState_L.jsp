<%
//**********************************************************
//  1. 제      목: 과정운영정보
//  2. 프로그램명:  za_CourseState_L.jsp
//  3. 개      요: 과정운영정보 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" /><%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");

    String  v_grcode    = "";
    String  v_grcodenm  = "";
    String  v_gyear     = "";
    String  v_grseq     = "";
    String  v_course    = "";
    String  v_cyear     = "";
    String  v_courseseq = "";
    String  v_coursenm  = "";
    String  v_subj      = "";
    String  v_year      = "";
    String  v_subjseq   = "";
    String  v_subjseqgr = "";
    String  v_subjnm    = "";
    String  v_isonoff   = "";
    String  v_edustart  = "";
    String  v_eduend    = "";
    String  v_isgoyong  = "";
    String  v_biyong_view       = "";
    String  v_goyongprice_view  = "";
	String	v_iscourseYn = "";
	String	v_tmp_subj	= "";

    int     v_wstep     = 0;
    int     v_wmtest    = 0;
    int     v_wftest    = 0;
    int     v_whtest    = 0;
    int     v_wreport   = 0;
    int     v_wact      = 0;
    int     v_gradstep  = 0;
    int     v_gradscore = 0;
    int     v_biyong      = 0;
    int     v_goyongprice = 0;

    int     v_cnt_mexam = 0;
    int     v_cnt_texam = 0;
    int     v_cnt_hexam = 0;
    int     v_cnt_proj  = 0;
    int     v_cnt_act   = 0;
    int     v_cnt_ord   = 0;

    String  v_scsubjseq   = "";
    String  v_subjtypenm  = "";
    String  v_isnewcourse = ""; 
    int     v_subjcnt     =  0;
    int     i = 0;
    int     l = 0;

    ArrayList list      = null;

    //DEFINED in relation to select START
    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getStringDefault("s_grseq","ALL");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수

    String  ss_action    = box.getString("s_action");

    //DEFINED in relation to select END

    list = (ArrayList)request.getAttribute("selectList");
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 검색
    function whenSelection(ss_action) {
      var process = 'selectPre';            //검색전
      
      if (ss_action=="go")  {
            if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
                alert("교육그룹을 선택하세요.");
                return ;
            }
            top.ftop.setPam();
            process='select';   //검색후
      }
      document.form1.target = "_self";  
      document.form1.action = "/servlet/controller.course.CourseStateAdminServlet";
      document.form1.p_process.value = process;
      document.form1.submit();
    }

    // 소팅
    function whenOrder(column) {
        if (document.form1.p_orderType.value == " asc") {
            document.form1.p_orderType.value = " desc";
        } else {
            document.form1.p_orderType.value = " asc";
        }
    
        document.form1.p_orderColumn.value = column;
        whenSelection("go");
    }

    // 엑셀보기
    function whenExcel() {
        if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return ;
        }
        window.self.name = "CourseState";
        open_window("openExcel1","","100","100","800","600","yes","yes","yes","yes","yes");
        document.form1.target = "openExcel1";
        document.form1.action='/servlet/controller.course.CourseStateAdminServlet';
        document.form1.p_process.value = 'CourseStateExcel';
        document.form1.submit();
    }
-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
    <form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    
    <input type="hidden" name="p_orderColumn">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">

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
            <!-- 교육그룹, 년도 시작 -->
        <table class="form_table_out" cellspacing="0" cellpadding="1">
          <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg">
              <tr>
                <td height="7" width="99%"></td>
              </tr>
              <tr>
                <td align="center" width="99%" valign="middle">
                  <table width="99%" cellspacing="0" cellpadding="0" class="form_table">
                    <tr>
                      <td>
                      
                        <!------------------- 조건검색 시작 ------------------------->
                         <table cellspacing="0" cellpadding="0" width="99%">
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0" width="99%">
                                        <tr>
                                            <td width="25%"><font color="red">★</font> 
                                            <!-- 교육그룹 시작 -->
                                                <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
                                            <!-- 교육그룹 종료 -->
                                            </td>
                                            <td width="75%">
                                            <!-- 교육년도, 분류 시작 -->
                                                <%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도  -->
                                                <%= SelectEduBean.getGrseq(box, true, true)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
                                                <!--input type="hidden" name="s_grseq"  value="0001"-->
                                                &nbsp;<%= SelectSubjBean.getUpperClass(box, true, true, true)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
                                                &nbsp;<%= SelectSubjBean.getMiddleClass(box, true, true, true)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
                                                &nbsp;<%= SelectSubjBean.getLowerClass(box, true, true, true)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
                                            <!-- 교육년도, 분류 끝 -->
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td height="3"></td>
                            </tr>
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0" width="99%">
                                        <tr>
                                            <td width="65%">&nbsp;&nbsp;&nbsp;
                                            <!-- 과정,차수,회사 시작 -->
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                <%= SelectSubjBean.getSubj(box, true, true)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                                                &nbsp;<%= SelectSubjBean.getSubjseq(box, false, true)%><!-- getSubjseq(RequestBox, isChange, isALL)    과정차수  -->
                                            <!-- 과정,차수,회사 종료 -->
                                            </td>
                                            <td  width="10%" align="right"> 
                                                <%@ include file="/learn/admin/include/za_GoButton.jsp" %><!-- whenSelection('go') -->
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <!-------------------- 조건검색 끝 ---------------------------->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="7" width="99%"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!-- 교육그룹, 년도 끝 -->
        <br>

      <!----------------- 독려메일, 메일발송, 엑셀출력, 모두선택, 모두선택취소  버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="right" height="20">&nbsp;</td>
            <td align="right">
            <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a>
            </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>           
        <!----------------- 과정운영정보 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="18" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="3%" height="50" rowspan="2" class="table_title"><b>NO</b></td>
            <td width="20%" colspan="2" rowspan="2" class="table_title"><a href="javascript:whenOrder('subjnm')" class="e">과정</a></td>
            <td width="5%" rowspan="2" class="table_title"><a href="javascript:whenOrder('a.scsubjseq')" class="e">절대<br>차수</a></td>            
            <td width="5%" rowspan="2" class="table_title"><a href="javascript:whenOrder('subjseqgr')" class="e">차수</a></td>
            <td width="11%" rowspan="2" class="table_title"><a href="javascript:whenOrder('edustart')" class="e">교육기간</a></td>
            <td width="5%" rowspan="2" class="table_title"><a href="javascript:whenOrder('wstep')" class="e">진도율<br>
              가중치<br>
              (%)</a></td>
            <td class="table_title" colspan="2" height="50"><b>중간평가</b></td>
            <td class="table_title" colspan="2"><b>최종평가</b></td>
            <td colspan="2" class="table_title"><b>형성평가</b></td>
            <td colspan="2" class="table_title"><b>리포트</b></td>
            <td width="5%" rowspan="2" class="table_title"><a href="javascript:whenOrder('gradstep')" class="e">이수<br>
              진도율<br>
              (%)</a></td>
            <td width="6%" rowspan="2" class="table_title"><a href="javascript:whenOrder('sgradscore')" class="e">이수기준<br>
              점수</a></td>
          </tr>
          <tr> 
            <td width="5%" height="50" class="table_title"><a href="javascript:whenOrder('wmtest')" class="e">가중치(%)</a></td>
            <td width="3%" class="table_title"><a href="javascript:whenOrder('cnt_mexam')" class="e">개수</a></td>
            <td width="5%" class="table_title"><a href="javascript:whenOrder('wftest')" class="e">가중치(%)</a></td>
            <td width="3%" class="table_title"><a href="javascript:whenOrder('cnt_texam')" class="e">개수</a></td>
            <td width="5%" class="table_title"><a href="javascript:whenOrder('whtest')" class="e">가중치(%)</a></td>
            <td width="3%" class="table_title"><a href="javascript:whenOrder('cnt_hexam')" class="e">개수</a></td>
            <td width="4%" class="table_title"><a href="javascript:whenOrder('wreport')" class="e">가중치(%)</a></td>
            <td width="3%" class="table_title"><a href="javascript:whenOrder('cnt_proj')" class="e">개수</a></td>
          </tr>
          <%
            for(i = 0; i < list.size(); i++) {
					
				DataBox dbox = (DataBox)list.get(i);

                v_scsubjseq = dbox.getString("d_scsubjseq"); // 절대차수
                v_grcode    = dbox.getString("d_grcode");
                v_grcodenm  = dbox.getString("d_grcodenm");
                v_gyear     = dbox.getString("d_gyear");
                v_grseq     = dbox.getString("d_grseq");
                v_course    = dbox.getString("d_course");
                v_cyear     = dbox.getString("d_cyear");
                v_courseseq = dbox.getString("d_courseseq");
                v_coursenm  = dbox.getString("d_coursenm");
                v_subj      = dbox.getString("d_subj");
                v_year      = dbox.getString("d_year");
                v_subjseq   = dbox.getString("d_subjseq");
                v_subjseqgr = dbox.getString("d_subjseqgr");
                v_subjnm    = dbox.getString("d_subjnm");
                v_isonoff   = dbox.getString("d_isonoff");
                v_edustart  = dbox.getString("d_edustart");
                v_eduend    = dbox.getString("d_eduend");
                v_isgoyong  = dbox.getString("d_isgoyong");
                v_wstep     = dbox.getInt("d_wstep");
                v_wmtest    = dbox.getInt("d_wmtest");;
                v_wftest    = dbox.getInt("d_wftest");
                v_whtest    = dbox.getInt("d_whtest");
                v_wreport   = dbox.getInt("d_wreport");;
                v_wact      = dbox.getInt("d_wact");
                v_gradstep  = dbox.getInt("d_gradstep");
                v_gradscore = dbox.getInt("d_gradscore");
                v_cnt_mexam = dbox.getInt("d_cnt_mexam");
                v_cnt_texam = dbox.getInt("d_cnt_texam");
                v_cnt_hexam = dbox.getInt("d_cnt_hexam");
                v_cnt_proj  = dbox.getInt("d_cnt_proj");
                v_cnt_ord   = dbox.getInt("d_cnt_ord");//문제수
                
                v_biyong      = dbox.getInt("d_biyong");
                v_goyongprice = dbox.getInt("d_goyongprice");

                v_subjtypenm  = dbox.getString("d_subjtypenm");
                v_subjcnt     = dbox.getInt("d_subjcnt");
                v_iscourseYn = dbox.getString("d_isbelongcourse");

                v_biyong_view       = NumberFormat.getInstance().format(v_biyong);
                v_goyongprice_view  = NumberFormat.getInstance().format(v_goyongprice);
               %>
          <tr> 
            <td class="table_01"><%= i+1 %></td>
<% if(v_iscourseYn.equals("Y"))
	{ 
		if(l == v_subjcnt) v_tmp_subj = "";
		if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
		{
			 l = 1;
			%>				
            <td class="table_02_2" rowspan="<%=v_subjcnt%>" ><%= v_coursenm %></td>
			<td class="table_02_2" ><%=v_subjnm%></td>
	<%	
		}else{ 
			 l++;
		%>
			<td class="table_02_2" ><%=v_subjnm%></td>
<%		}
		v_tmp_subj = v_course;
	} else { %>

            <td class="table_02_2" colspan="2"><%=v_subjnm%></td>
<% } %>
            <td class="table_02_1"><font color=red><%=StringManager.cutZero(v_scsubjseq)%></font></td>
            <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
            <td class="table_02_1"><%= FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd") %> ~ <%= FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd") %></td>
            <td class="table_02_1"><%= v_wstep %></td>
            <td class="table_02_1"><%= v_wmtest %></td>
            <td class="table_02_1"><%= v_cnt_mexam %></td>
            <td class="table_02_1"><%= v_wftest %></td>
            <td class="table_02_1"><%= v_cnt_texam %></td>
            <td class="table_02_1"><%= v_whtest %></td>
            <td class="table_02_1"><%= v_cnt_hexam %></td>
            <td class="table_02_1"><%= v_wreport %>
            </td>
            <td class="table_02_1"><%= v_cnt_proj %>/<%=v_cnt_ord%></td>
            <td class="table_02_1"><%= v_gradstep %></td>
            <td class="table_02_1"><%= v_gradscore %></td>
          </tr>
          <%
          }
          if(v_process.equals("select") && i == 0){ %>
          <tr> 
            <td class="table_02_1" colspan="18">등록된 내용이 없습니다</td>
          </tr>
          <%  } %>
        </table>
        <!----------------- 과정운영정보 끝 ----------------->
        <br>
     </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
