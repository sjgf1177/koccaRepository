<%
//**********************************************************
//  1. 제      목: PROJECT DETAIL LIST
//  2. 프로그램명: za_ProjectDetail_L.jsp
//  3. 개      요: 리포트 제출자 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 29
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");    
    String  v_subj      = box.getString("p_subj");
    String  v_subjnm    = box.getString("p_subjnm");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_ordseq    = box.getString("p_ordseq");
    String  v_lesson    = box.getString("p_lesson");
    String  v_reptype   = box.getString("p_reptype");
    String  v_projgrp   = "";
    String  v_projname  = "";
    String  v_title     = "";
    String  v_upfile    = "";
    String  v_upfile_value="";
    String  v_indate    = "";
    String  v_score_value="";
	String  v_isfinal   = "";
    int     v_seq       =  0;
    int     v_score_mas =  0;      
    int     i           =  0;
    ArrayList list      = null;
    //DEFINED class&variable END
    
    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");        //교육그룹
    String  ss_gyear     = box.getString("s_gyear");         //년도
    String  ss_grseq     = box.getString("s_grseq");         //교육차수
    String  ss_uclass    = box.getString("s_uclass");        //과정분류
    String  ss_subjcourse= box.getString("s_subjcourse");    //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");       //과정 차수
    String  ss_action    = box.getString("s_action");   
    //DEFINED in relation to select END
        
    list = (ArrayList)request.getAttribute("projectDetailList");  
    
    //String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    function open_window(name, url, left, top, width, height, scrollbar, toolbar, menubar, statusbar, resizable)
    {
        toolbar_str = toolbar ? 'yes' : 'no';
        menubar_str = menubar ? 'yes' : 'no';
        statusbar_str = statusbar ? 'yes' : 'no';
        scrollbar_str = scrollbar ? 'yes' : 'no';
        resizable_str = resizable ? 'yes' : 'no';
        window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
    }
    function projectList() {
		document.form1.target="_self";
        document.form1.action='/servlet/controller.study.ProjectAdminServlet';
        document.form1.p_process.value = 'ProjectSubmitList';
        document.form1.submit();
    }    
    function projectDetailList() {
		document.form1.target="_self";
        document.form1.action='/servlet/controller.study.ProjectAdminServlet';
        document.form1.p_process.value = 'ProjectDetailList';
        document.form1.submit();
    }
    function open_projectSubmit(seq,projgrp) {
        window.self.name = "projectDetail";     //opener 객체에 name 을 지정한다
        open_window("openProjectSubmit","","100","100","548","550","1");      
        document.form1.target = "openProjectSubmit";
        document.form1.action = "/servlet/controller.study.ProjectAdminServlet";
        document.form1.p_process.value = "ProjectSubmitOpenPage";
        document.form1.p_seq.value = seq;
        document.form1.p_projgrp.value = projgrp;
        document.form1.submit();
        document.form1.target = window.self.name;        
    }
	
	function open_projectSearch() {
        window.self.name = "projectSearch";     //opener 객체에 name 을 지정한다
        open_window("openProjectSearch","","100","100","1024","768","1");      
        document.form1.target = "openProjectSearch";
        document.form1.action = "/search/ReportSearch";
        document.form1.p_process.value = "";
        document.form1.submit();
        document.form1.target = window.self.name;        
    }         
    
    function whenOrder(column) {
	if (document.form1.p_orderType.value == " asc") {
		document.form1.p_orderType.value = " desc";
	} else {
		document.form1.p_orderType.value = " asc";
	}

    document.form1.p_orderColumn.value = column;

    projectDetailList();
}

-->    	
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
   <form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="s_action"    value="<%=ss_action%>">     <!--in relation to select-->
    <input type="hidden" name="s_grcode"    value="<%=ss_grcode%>">     <!--in relation to select-->
    <input type="hidden" name="s_gyear"     value="<%=ss_gyear%>">      <!--in relation to select-->
    <input type="hidden" name="s_grseq"     value="<%=ss_grseq%>">      <!--in relation to select-->
    <input type="hidden" name="s_uclass"    value="<%=ss_uclass%>">     <!--in relation to select-->
    <input type="hidden" name="s_subjcourse"value="<%=ss_subjcourse%>"> <!--in relation to select-->
    <input type="hidden" name="s_subjseq"   value="<%=ss_subjseq%>">    <!--in relation to select-->  
    <input type="hidden" name="p_subj"      value="<%=v_subj%>">
    <input type="hidden" name="p_subjnm"    value="<%=v_subjnm%>">
    <input type="hidden" name="p_year"      value="<%=v_year%>">
    <input type="hidden" name="p_subjseq"   value="<%=v_subjseq%>">
    <input type="hidden" name="p_ordseq"    value="<%=v_ordseq%>">
    <input type="hidden" name="p_lesson"    value="<%=v_lesson%>">     
    <input type="hidden" name="p_reptype"   value="<%=v_reptype%>">  
    <input type="hidden" name="logChk"      value="1"> 
    <input type="hidden" name="p_projgrp">   
    <input type="hidden" name="p_seq">
    
    <input type="hidden" name="p_orderColumn">
	<input type="hidden" name="p_orderType" value="">


  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title08.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>  
	          <!----------------- title 끝 ----------------->
        <br>
		        <!-------------소제목 시작-------------------------->
 <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>리포트 제출자 리스트</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        <!--------------소제목 끝---------------------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td></td>
          </tr>
        </table>

        <br>
        <!----------------- 리스트 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr> 
		    <td width="305"><img src="/images/admin/study/icon1.gif" width="14" height="10" border="0">
            <%=v_subjnm%> 과정 &gt; <%=StringManager.cutZero(v_subjseq)%>차 &gt; <%=v_lesson%>일차</td>
            <td width="588" height="20" align="right" style=padding-right:8px>모사답안 검색 : <input type="text" class="input" name="query" size=20> </td>
            <td width="34"><a href="javascript:open_projectSearch();"><img src="/images/admin/button/b_go.gif" border=0></a></td>
			<td width=10></td>
            <td width="33"><a href="javascript:projectList()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
          </tr>
          <tr> 
            <td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
          </tr>
        </table>
        <!----------------- 추가, 리스트 버튼 끝 ----------------->

        <!----------------- 리포트 출제리스트 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="7" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="5%" class="table_title"><b>NO</b></td>
            <td class="table_title" width="11%"><a href="javascript:whenOrder('projid')" class="e"><b>사번</b></a></td>
            <td class="table_title" width="10%"><a href="javascript:whenOrder('projname')" class="e"><b>성명</b></a></td>
            <td width="41%" class="table_title"><a href="javascript:whenOrder('title')" class="e"><b>내용제목</b></a></td>
            <td class="table_title" width="7%"><a href="javascript:whenOrder('upfile')" class="e"><b>첨부파일</b></a></td>
            <td class="table_title" width="9%"><a href="javascript:whenOrder('indate')" class="e"><b>제출일</b></a></td>
            <td class="table_title" width="7%"><a href="javascript:whenOrder('score_mas')" class="e"><b>평가점수</b></a></td>
          </tr>
          <% 
            for(i = 0; i < list.size(); i++) {
                ProjectData data= (ProjectData)list.get(i);
                v_seq       = data.getSeq();
                v_projgrp   = data.getProjgrp();
                v_projname  = data.getProjname();
                v_title     = data.getTitle();
                v_score_mas = data.getScore_mas();
				v_isfinal   = data.getIsfinal();
                v_upfile    = data.getUpfile();
                v_indate    = data.getIndate();                                     
                //if(v_score_mas == 0){       v_score_value = "<font color='#FF0000'>미평가</font>";  }
				if(v_isfinal.equals("N")){       v_score_value = "<font color='#FF0000'>미평가</font>";  }
                else                {       v_score_value = v_score_mas+"점";                       }
                v_indate    = FormatDate.getFormatDate(v_indate,"yyyy/MM/dd");
                if(v_upfile.length() == 0){    v_upfile   = "";    
                }else{  
					//v_upfile_value = v_projgrp + v_upfile.substring(v_upfile.lastIndexOf("."));    
					v_upfile_value = v_upfile;    
				}                
           %>
          <tr> 
            <td class="table_01"><%= list.size() - i %></td>
            <td class="table_02_1" align="center"><%=v_projgrp%></td>
            <td class="table_02_1" align="center"><%=v_projname%></td>
            <td class="table_02_2"> <a href="javascript:open_projectSubmit('<%=v_seq%>','<%=v_projgrp%>')"><%=v_title%></a> </td>
            <td class="table_02_1" align="center"> <% if(v_upfile.length() > 0){ %>
              <a href = '/servlet/controller.library.DownloadServlet?p_year=<%=v_year%>&p_subj=<%=v_subj%>&p_savefile=<%= v_upfile %>&p_realfile=<%= v_upfile_value %>'><img src="/images/admin/button/icon_file.gif" border=0></a> 
              <% } %> </td>
            <td class="table_02_1" align="center"><%=v_indate%></td>
            <td class="table_02_1" align="center"><%=v_score_value%></td>
          </tr>
          <%}  
          if(i == 0){ %>
          <tr> 
            <td align="center" class="table_02_1" height="50" colspan="7">등록된 
              내용이 없습니다</td>
          </tr>
          <%  } %>
        </table>
        <!----------------- Activity 리스트 끝 ----------------->
      </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>    
</table>
</form>
</body>
</html>
