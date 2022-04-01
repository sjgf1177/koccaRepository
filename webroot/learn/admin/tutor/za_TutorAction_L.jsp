<%
//**********************************************************
//  1. 제      목: 강사활동관리
//  2. 프로그램명: za_TutorAction_L.jsp 
//  3. 개      요: 강사평가관리 조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.tutor.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    
    int     v_pageno        = box.getInt("p_pageno");
    int     v_pagesize      = box.getInt("p_pagesize");
    int     v_totalrowcount =  0;
    int     v_dispnum       = 0;           // 총게시물수
    int     v_totalpage     = 0;           // 게시물총페이지수
    
    String ems_url = conf.getProperty("ems.url.value");
	
    String  v_subj      =  "";
    String  v_year      =  "";
    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_tuuserid = "";
	String  v_name		= "";
    String	v_isclosed = "";
    int     v_stucnt	=  0;
    int		v_grayncnt	= 0;
    int     i           =  0;


    ArrayList list      = null;
                         
    //DEFINED in relation to select START
    String  ss_grcode		= box.getString("s_grcode");        //교육그룹
    String  ss_gyear		= box.getString("s_gyear");         //년도
    String  ss_grseq		= box.getString("s_grseq");         //교육차수
    String  ss_upperclass	= box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass	= box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass	= box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse	= box.getString("s_subjcourse");    //과정&코스
    String  ss_subjseq		= box.getString("s_subjseq");       //과정 차수
    String  ss_edustart		= box.getString("s_start");            //교육시작일
    String  ss_eduend		= box.getString("s_end");              //교육종료일

    //오프라인 검색조건
    String  ss_year = box.getString("s_year");	  //연도
    String  ss_subj = box.getString("s_subjcode");
	String s_subjsearchkey = box.getString("s_subjsearchkey");


    String ss_onoffline = box.getString("p_onoffline");
    if(ss_onoffline.equals(""))
        ss_onoffline="on";
    
    String ss_idname = box.getString("p_idname");
    String ss_textbox = box.getString("p_textbox");

    String  ss_action		= box.getString("s_action");
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    String  v_orderColumn    = box.getStringDefault("p_orderColumn",""); 
    
    if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("actionlist");
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script> 
<SCRIPT LANGUAGE="JavaScript">

    function whenSelection(p_action) {
	    if (p_action=="go"){
/*            if(document.form1.s_grcode.value == '----') {
                alert("선택된 교육그룹이 없습니다.");
                return;
            }

            if(document.form1.s_grseq.value == '----') {
                alert("선택된 교육차수가 없습니다.");
                return;
            }*/
	        top.ftop.setPam();
	    }    

        document.form1.s_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.tutor.TutorActionAdminServlet';
        document.form1.p_process.value = 'listPage';  
        document.form1.submit();
    }
    
    function whenSelection2(p_action, p_type) {
	    if (p_action=="go"){
            if(document.form1.s_grcode.value == '----') {
                alert("선택된 교육그룹이 없습니다.");
                return;
            }

     //       var st_date = make_date(form1.s_start.value);
     //       var ed_date = make_date(form1.s_end.value);
            
     //       if((st_date.length > 0 && ed_date.length > 0) && st_date > ed_date) {
     //           alert("교육시작일이 교육종료일보다 큽니다.");
     //           return;
     //       }
     //       document.form1.s_edustart.value = st_date;
     //       document.form1.s_eduend.value   = ed_date;
	        top.ftop.setPam();
	    }
//        if (p_type == 'selgubun')
        document.form1.s_seltext.value = 'ALL';
        document.form1.s_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.tutor.TutorActionAdminServlet';
        document.form1.p_process.value = 'listPage';  
        document.form1.submit();
    }
    
    function whenOrder(column) {
		if (document.form1.p_orderType.value == " asc") {
			document.form1.p_orderType.value = " desc";
		} else {
			document.form1.p_orderType.value = " asc";
		}

        document.form1.target = "_self";
        document.form1.action='/servlet/controller.tutor.TutorActionAdminServlet';
        document.form1.p_process.value = 'listPage';   
        document.form1.p_orderColumn.value = column;
        document.form1.submit();
    }    
    
    function whenExcel() {    
        if(document.form1.s_action.value != "go"){
            alert('먼저 조회해 주십시오');
            return;
        }
        
       // v_param = "";        
        window.self.name = "List";     
        //open_window("openExcel","","100","100","900","400","yes","yes","yes","yes","yes");  
        //document.form1.target = "openExcel";               
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.tutor.TutorActionAdminServlet';
        document.form1.p_process.value = 'ListExcel';   
           
        document.form1.submit();
    }    

    function chkSelected() {
      var selectedcnt = 0;
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            if (document.form1.p_checks[i].checked == true) {
    	      selectedcnt++;
    	    }
          }
        } else {
          if (document.form1.p_checks.checked == true) {
    	    selectedcnt++;
          }
        }
      }
      return selectedcnt; 
    }

    function whenAllSelect() {
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            document.form1.p_checks[i].checked = true;
          }
        } else {
          document.form1.p_checks.checked = true;
        }
      } 
    }
    
    function whenAllSelectCancel() {
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            document.form1.p_checks[i].checked = false;
          }
        } else {
          document.form1.p_checks.checked = false;
        }
      }
    }


    function go(index) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = '/servlet/controller.tutor.TutorActionAdminServlet';
         document.form1.p_process.value = "listPage";     
         document.form1.submit();
    }  
	
    function whenTutorDetail(subj, year, subjseq, userid, name, subjnm, isclosed){
    	window.self.name = "TutorActionDetail";     
        open_window("openTutor","","100","0","730","700","no","no","no","yes","yes");  
        document.form1.target = "openTutor";               
        document.form1.action = '/servlet/controller.tutor.TutorValuationAdminServlet';
        document.form1.p_process.value = 'detailView';
        document.form1.p_subj.value = subj;
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_userid.value = userid;
        document.form1.p_name.value = name;
        document.form1.p_subjnm.value = subjnm;
        document.form1.p_isclosed.value = isclosed;
        document.form1.submit();
    }

    function whenTutorHistory(subj, year, subjseq, userid){
    	window.self.name = "TutorActionDetail";
        open_window("openTutor1","","100","0","700","400","no","no","no","yes","yes");  
        document.form1.target = "openTutor1";
        document.form1.action = '/servlet/controller.tutor.TutorActionAdminServlet';
        document.form1.p_process.value = 'teachDetail';
        document.form1.p_subj.value = subj;
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_userid.value = userid;
        document.form1.p_name.value = "";
        document.form1.p_subjnm.value = "";
        document.form1.p_isclosed.value = "";
        document.form1.submit();
    }

	function whenQnaList(subj, year, subjseq) {    	  
		window.self.name = "open";     
		open_window("opensel","","100","0","1000","600","N","yes","yes","yes","yes");  
		document.form1.target = "opensel";               
				   
		document.form1.action='/servlet/controller.tutor.TutorAdminMainServlet';	
		document.form1.p_process.value = 'QnaListPage';
		document.form1.p_subj.value = subj;
		document.form1.p_year.value = year;
		document.form1.p_subjseq.value = subjseq;
//		document.form1.p_seq.value = seq;
		document.form1.submit();
	}

    function chkSelected() {
      var selectedcnt = 0;
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            if (document.form1.p_checks[i].checked == true) {
              selectedcnt++;
            }
          }
        } else {
          if (document.form1.p_checks.checked == true) {
            selectedcnt++;
          }
        }
      }
      return selectedcnt;
    }

    function whenCareer()
    {
          if (chkSelected() < 1) {
            alert('강사이력을 프린트할 데이타를 선택하세요');
            return;
          }

        $("[name='p_checks']").each(function(i){
            var r=$(this).attr("checked");
            var r1=$(this).val();
            if(r)
            {
                var tmp=r1.split(",");
                $("[name='p_userid']").val(tmp[0]);
                open_window("careerWin","","10","10","800","650");
                document.form1.target = "careerWin";
                document.form1.p_process.value = 'CareerPrint';
                document.form1.action='/servlet/controller.tutor.TutorActionAdminServlet';
                document.form1.submit();
            }
        });
    }

    function whenFreeMail() {

      if (chkSelected() < 1) {
        alert('메일을 발송할 학습자를 선택하세요');
        return;
      }

      open_window("openFreeMail","","10","10","800","650");
      document.form1.target = "openFreeMail";
      //document.form1.action='<%=ems_url%>/ems/MassMail.do';
      
      ff =document.form1;

      ff.p_msubjnm.value=ff.s_subjcourse.options[ff.s_subjcourse.selectedIndex].text;
      ff.p_mseqgrnm.value=ff.s_subjseq.options[ff.s_subjseq.selectedIndex].text;
      ff.p_msubj.value=ff.s_subjcourse.value;
      ff.p_myear.value=ff.s_gyear.value;
      ff.p_msubjseq.value=ff.s_subjseq.value;
      ff.p_touch.value = "12";
      
      document.form1.p_process.value = 'SendFreeMail';
      document.form1.action='/servlet/controller.tutor.TutorActionAdminServlet';
      document.form1.submit();

    }

    function go(index) {
        document.form1.p_pageno.value = index;
        document.form1.action = "/servlet/controller.tutor.TutorActionAdminServlet";
        document.form1.p_process.value = "listPage";
        document.form1.submit();
   }
   function goPage(pageNum) {
        document.form1.p_pageno.value = pageNum;
        document.form1.action = "/servlet/controller.tutor.TutorActionAdminServlet";
        document.form1.p_process.value = "listPage";
        document.form1.submit();
   }

    function pagesize(pageSize) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_pagesize.value = pageSize;
        document.form1.action = '/servlet/controller.tutor.TutorActionAdminServlet';
        document.form1.p_process.value = "listPage";
        document.form1.submit();
    }

    function subjSelected(){
	if (document.all.s_subjcode.value.length > 0) {
		 $('#insertButton').show();
	}
	else $('#insertButton').hide();
    }

	function self_reload()
    {
        document.form1.target = "_self";
        $("[name='p_onlyonoff_sw']").val("on");
        document.form1.action='/servlet/controller.tutor.TutorActionAdminServlet';
        document.form1.p_process.value = 'listPage';
        document.form1.submit();
    }
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type="hidden" name="p_process" value="">
    <input type="hidden" name="p_pageno" value="<%=v_pageno %>">    
    <input type="hidden" name="p_pagesize" value="<%=v_pagesize %>"> 
    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->   
    <input type="hidden" name="p_subj">
	<input type="hidden" name="p_year">
	<input type="hidden" name="p_subjseq">
	<input type="hidden" name="p_userid">
	<input type="hidden" name="p_subjnm">
    <input type="hidden" name="p_onlyonoff_sw">
	<input type="hidden" name="p_name">    
    <input type="hidden" name="p_orderColumn" value="<%=v_orderColumn%>">
	<input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <input type="hidden" name="userLoginID" value="<%=box.getSession("userid")%>">
	<input type="hidden" name="p_isclosed">
    <!-- tz_humantouc insert parameter start-->
    <input type="hidden" name="p_touch" value=''>
    <input type="hidden" name="p_msubjnm" value=''>
    <input type="hidden" name="p_mseqgrnm" value=''>
    <input type="hidden" name="p_msubj" value=''>
    <input type="hidden" name="p_myear" value=''>
    <input type="hidden" name="p_msubjseq" value=''>
    <!-- tz_humantouc insert parameter end -->
	
	
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 

   <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
            <td><img src="/images/admin/tutor/tit_new_04.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
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
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                        <td align="left" valign="middle" colspan="3">
                            구분 <select name="p_onoffline" onchange="self_reload();">
                                <option value="on" <%= ss_onoffline.equals("on") ? "selected":"" %>>온라인</option>
                                <option value="off" <%= ss_onoffline.equals("off") ? "selected":"" %>>오프라인</option>
                            </select>
                            <select name="p_idname">
                                <option value="name" <%= ss_onoffline.equals("name") ? "selected":"" %>>이름</option>
                                <option value="id" <%= ss_onoffline.equals("id") ? "selected":"" %>>ID</option>
                            </select>
                            <input type="text" name="p_textbox" size="20" value="<%=ss_textbox%>">
                        </td>
                    </tr>
                    <tr>
                        <td height="5" colspan="3"></td>
                    </tr>
                    <tr>
                        <td align="left" valign="middle" colspan="3">
                            <% if(ss_onoffline.equals("on")) {%>
                            <script type="text/javascript">
                            var startup = 0;
                            function Main_s_subjseq(){
                            if (startup > 1)
                            changes_grseq(document.all.s_grcode.value, document.all.s_gyear.value);
                            }
                            function Main_subjcourse(){
                            if(startup > 1)
                            changes_subjcourse(document.all.s_grcode.value, document.all.s_upperclass.value, document.all.s_middleclass.value, document.all.s_lowerclass.value, document.all.s_grseq.value,'' );
                            startup++;
                            }
                            </script>
                        <!------------------- 조건검색 시작 ------------------------->
                                     교육그룹 <kocca_select:select name="s_grcode" sqlNum="course.0001"  param=" "
                            onChange="changes_gyear(this.value);" attr=" " selectedValue="<%= ss_grcode %>" isLoad="true" all="false" />
                            연도 <kocca_select:select name="s_gyear" sqlNum="course.year"  param="<%= ss_grcode %>"
                            onChange="changes_grseq(s_grcode.value, this.value);" afterScript="Main_subjcourse" attr=" " selectedValue="<%= ss_gyear %>" isLoad="true" all="false" />
                            교육차수 <kocca_select:select name="s_grseq" sqlNum="course.subjseq"
                            param="<%=ss_grcode%>" param2="<%=ss_gyear%>"
                            attr=" style='width:300px;'" onChange="" afterScript="Main_subjcourse" selectedValue="<%= ss_grseq %>" isLoad="true" all="true" />
                            대분류 <kocca_select:select name="s_upperclass" sqlNum="course.0002"  param=" "
                            onChange="changes_middleclass(this.value);Main_subjcourse();" attr=" "  selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
                            <%}else {%>
                                <script type="text/javascript">
                                var isFirst = 0;
                                function fnSearchBox() {
                                    if(isFirst>1)
                                    changes_subjcode(
                                            $("#s_year").val(),
                                            $("#s_upperclass").val(),
                                            $("#s_middleclass").val(),
                                            $("#s_lowerclass").val(),
                                            $("#s_year").val()
                                            );
                                    isFirst++;
                                }
                                function fnSearchBoxs_middleclass() {
                                    if(isFirst>1)
                                    changes_lowerclass($("#s_upperclass").val(), $("#s_middleclass").val());
                                    isFirst++;
                                }
                                </script>
                                <font color="red">★</font>연도 <kocca_select:select name="s_year" sqlNum="off.year"  param=" "
                                    onChange="fnSearchBox();" attr=" " selectedValue="<%= ss_year %>" isLoad="true" all="none" />
                                대분류 <kocca_select:select name="s_upperclass" sqlNum="off.0002"  param=" "
                                    onChange="changes_middleclass(this.value);" attr=" " selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
                                중분류 <kocca_select:select name="s_middleclass" sqlNum="off.0003"  param="<%= ss_upperclass %>"
                                    onChange="changes_lowerclass(s_upperclass.value, this.value);"
                                    afterScript="fnSearchBoxs_middleclass"
                                    attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" />
                                소분류 <kocca_select:select name="s_lowerclass" sqlNum="off.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>"
                                    afterScript="fnSearchBox"
                                    onChange="fnSearchBox()" attr=" " selectedValue="<%=ss_lowerclass %>" isLoad="true" all="true" />
                            <%}%>
                        </td>
                    </tr>
                    <tr>
                        <td height="5" colspan="3"></td>
                    </tr>
                    <tr>
                        <td align="left">
                            <% if(ss_onoffline.equals("on")) {%>
                                중분류 <kocca_select:select name="s_middleclass" sqlNum="course.0003"  param="<%= ss_upperclass %>"
                                onChange="changes_lowerclass(s_upperclass.value, this.value);Main_subjcourse();" attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" />
                                소분류 <kocca_select:select name="s_lowerclass" sqlNum="course.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>"
                                onChange="whenSelection('go')" attr=" " selectedValue="<%= ss_lowerclass %>" isLoad="true" all="true" />
                                과정 <kocca_select:select name="s_subjcourse" sqlNum="subjlist1|subjlist2|subjlist3|subjlist4|subjlist5|subjlist6|subjlist7|subjlist8|subjlist9"
                                param="<%=ss_grcode%>" param4="<%=ss_upperclass%>" param5="<%=ss_middleclass%>" param6="<%=ss_lowerclass%>"
                                param7="<%=ss_grseq%>" param9=""
                                onChange="changes_subjseq(s_grcode.value, s_grseq.value, s_subjcourse.value, s_gyear.value)" attr=" " selectedValue="<%= ss_subjcourse %>" isLoad="true" all='<%= box.getSession("isSubjAll") %>' />
                                차수 <kocca_select:select name="s_subjseq" sqlNum="subjectSeq1|subjectSeq2|subjectSeq3|subjectSeq4"
                                param="<%=ss_grcode%>" param2="<%=ss_grseq %>" param3="<%=ss_subjcourse%>" param4="<%=ss_gyear%>"
                                onChange="whenSelection('go')" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="true" />
                            <%} else { %>
                                <%--과정년도 <kocca_select:select name="s_subjyear" sqlNum="off.year.subj"  param=" "--%>
                                    <%--onChange="fnSearchBox()" attr=" " selectedValue="<%= ss_subjyear %>" isLoad="true" all="true" />--%>
                                <font color="red">★</font>과정명 <kocca_select:select name="s_subjcode" sqlNum="off.subj"
                                    param="<%= ss_year %>"  param2="<%= ss_upperclass %>"  param3="<%= ss_middleclass %>"  param4="<%= ss_lowerclass %>" param5="<%= ss_year %>"
                                    onChange="subjSelected();changes_subjseq($('#s_year').val(),this.value);setTimeout('change_subjseq()', 400);" attr=" " selectedValue="<%= ss_subj %>" isLoad="true" all="no"/>
                                차수 <kocca_select:select name="s_subjseq" sqlNum="off.subjseq" param="<%= ss_year %>"  param2="<%= ss_subj %>"
                                    onChange="" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="true" />
                            <%}%>
                        <!-------------------- 조건검색 끝 ---------------------------->
                        <td width="10%" align="right"  colspan="2"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
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

      <!----------------- 엑셀출력 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td align="right">&nbsp;</td>
			<td align="right" valign="top" width="75">
			<a href="javascript:whenFreeMail()"><img src="/images/admin/button/btn_mail.gif" border="0"></a>
			</td>            
			<td align="right" valign="top" width="70">
			<a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
			</td>
            <td align="right" valign="top" width="88">
			<a href="javascript:whenCareer()"><img src="/images/admin/button/btn_career.gif" border="0"></a>
			</td>
        </tr>
      </table>
      <!----------------- 엑셀출력 버튼 끝 ----------------->
      <!----------------- 입과명단조회 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
          <tr> 
            <td colspan="19" class="table_top_line"></td>
          </tr>
          <tr> 
            <td class="table_title" rowspan="2">No</td>
            <td class="table_title" rowspan="2"><a href="javascript:whenOrder('c.grcode')" class="e">교육그룹</a></td>
            <td class="table_title" rowspan="2"><a href="javascript:whenOrder('c.subj')" class="e">과정코드</a></td>
            <td class="table_title" rowspan="2"><a href="javascript:whenOrder('c.subjnm')" class="e"><font class="text_color04">과정명</font></a></td>
            <td class="table_title" rowspan="2"><a href="javascript:whenOrder('c.subjseq')" class="e">과정<br>차수</a></td>
            <td class="table_title" rowspan="2"><a href="javascript:whenOrder('ctutorcnt')" class="e">총강사수</a></td>
			<td class="table_title" rowspan="2"><a href="javascript:whenOrder('name')" class="e">강사명</a></td>
			<td class="table_title" rowspan="2"><a href="javascript:whenOrder('x.stucnt')" class="e">입과<br>인원</a></td>
			<td class="table_title" rowspan="2"><a href="javascript:whenOrder('x.grayncnt')" class="e">수료<br>인원</a></td>
			<td class="table_title" rowspan="2"><a href="javascript:whenOrder('logincnt')" class="e">강사<br>접속횟수</a></td>
			<td class="table_title" rowspan="2">강사게시건수<br>(공지/자료/토론)</td>
			<td class="table_title" colspan="2">리포트</td>
			<td class="table_title" colspan="2">Q & A</td>
            <td class="table_title" rowspan="2"><%@ include file="/learn/admin/include/za_checkboxall.jsp" %></td>
          </tr>
          <tr> 
            <td class="table_title"><a href="javascript:whenOrder('repcnt')" class="e">제출</a></td>
            <td class="table_title"><a href="javascript:whenOrder('noscorecnt')" class="e">미평가</a></td>            
            <td class="table_title"><a href="javascript:whenOrder('qcnt')" class="e">질의</a></td>
            <td class="table_title"><a href="javascript:whenOrder('noanscnt')" class="e">미응답</a></td>
          </tr>

<%                          
            if (ss_action.equals("go")) {    //go button 선택시만 list 출력
		
                          
	         	for( i = 0 ; i < list.size() ; i++ ){
					DataBox dbox = (DataBox)list.get(i);		  
							
					v_subj		= dbox.getString("d_subj");  
					v_year		= dbox.getString("d_year");
					v_subjseq	= dbox.getString("d_subjseq");
					v_subjnm	= dbox.getString("d_subjnm");
					v_tuuserid	= dbox.getString("d_userid");
					v_name		= dbox.getString("d_name");
					v_stucnt	= dbox.getInt("d_stucnt");
					v_grayncnt  = dbox.getInt("d_grayncnt");
					v_isclosed  = dbox.getString("d_isclosed");			
					
					v_dispnum   = dbox.getInt("d_dispnum");
	                v_totalpage = dbox.getInt("d_totalpage");
	                v_totalrowcount = dbox.getInt("d_totalrowcount");
	%>
			        <tr> 
			            <td class="table_02_1"><%=v_dispnum%></td>
			            <td class="table_02_1"><%=GetCodenm.get_grcodenm(dbox.getString("d_grcode"))%></td>
			            <td class="table_02_1"><%=v_subj%></td>
			            <td class="table_02_2"><a href="javascript:whenTutorHistory('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>','<%=v_tuuserid%>');"   class="e"><%=v_subjnm%></a></td>
			            <td class="table_02_1"><%=v_subjseq%></td>
			            <td class="table_02_1"><%=dbox.getString("d_ctutorcnt")%></td>
						<td class="table_02_1"><a href="javascript:whenTutorDetail('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>','<%=v_tuuserid%>','<%=v_name%>','<%=v_subjnm%>','<%=v_isclosed%>');"   class="e"><%=dbox.getString("d_name")%></a></td>
			            <td class="table_02_1"><%=v_stucnt%></td>
			            <td class="table_02_1"><%=v_grayncnt%></td>
			            <td class="table_02_1"><%=dbox.getString("d_logincnt")%></td>
			            <td class="table_02_1"><%=dbox.getInt("d_gongcnt")%>/<%=dbox.getInt("d_datacnt")%>/<%=dbox.getInt("d_toroncnt")%><%//=dbox.getInt("d_torontpcnt")%></td>
			            <td class="table_02_1"><%=dbox.getString("d_repcnt")%></td>
			            <td class="table_02_1"><%=dbox.getString("d_noscorecnt")%></td>
			            <!--td class="table_02_1"><%=dbox.getString("d_examcnt")%></td-->
			            <td class="table_02_1"><%=dbox.getString("d_qcnt")%></td>
			            <td class="table_02_1"><a href="javascript:whenQnaList('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>');"  class="e"><%=dbox.getString("d_noanscnt")%></a></td>
			            <td class="table_02_1"><input type="checkbox" name="p_checks" value="<%=v_tuuserid%>,<%=v_subj%>,<%=v_year%>,<%=v_subjseq%>"></td>
			         </tr>
<%					
	            }		   
         }
         if(i == 0 && ss_action.equals("go")){ 
%>
              <tr> 
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="19">등록된 내용이 없습니다</td>
              </tr>
<% } else{ %>
			</table>
<% } %>
      <!----------------- 입과명단 조회 끝 ----------------->
      <%  if(i > 0){ %>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
              <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
            </td>
          </tr>
        </table>
        <% } %> 
     <br>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>      
</table>
</form>
</body>
</html>
