<%
//**********************************************************
//  1. 제      목: PROJECT DETAIL LIST
//  2. 프로그램명: za_ProjectDetail_L1.jsp
//  3. 개      요: 리포트 제출자 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 7. 30
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
		String  v_isret     = "";    // 반려여뷰
		String  v_isret_value = "";	
		String  v_upfile2   = "";    // 답안파일
		String  v_realfile2 = "";    // 답안파일실네임
    String  v_ldate     = "";    // 수정일
    String  v_tudate		= "";
    String  v_realfile     = "";                            
    String  v_upfilesize   = "";     
    String  v_contentsbyte = "";   
                	
    int     v_seq       =  0;
    int     v_score     =  0;      
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

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 새창크기 조절
    function open_window(name, url, left, top, width, height, scrollbar, toolbar, menubar, statusbar, resizable)
    {
        toolbar_str = toolbar ? 'yes' : 'no';
        menubar_str = menubar ? 'yes' : 'no';
        statusbar_str = statusbar ? 'yes' : 'no';
        scrollbar_str = scrollbar ? 'yes' : 'no';
        resizable_str = resizable ? 'yes' : 'no';
        window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
    }         
    
    // 평가리스트 이동
    function projectList() {
		document.form1.target="_self";
        document.form1.action='/servlet/controller.study.ProjectAdminServlet';
        document.form1.p_process.value = 'ProjectSubmitList';
        document.form1.submit();
    }
        
    function projectDetailList(subj,subjnm,year,subjseq,ordseq,lesson) {
		document.form1.target="_self";
        document.form1.action='/servlet/controller.study.ProjectAdminServlet';
        document.form1.p_process.value = 'ProjectDetailList';
        document.form1.p_subj.value = subj;
        document.form1.p_subjnm.value = subjnm;
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_ordseq.value = ordseq;
        document.form1.p_lesson.value = lesson;
        document.form1.submit();
    }
    
    // 과제물 점수 평가
    function open_projectSubmit(seq,projgrp) {
        window.self.name = "projectDetail";     //opener 객체에 name 을 지정한다
        open_window("openProjectSubmit","","50","50","750","700","1");      
        document.form1.target = "openProjectSubmit";
        document.form1.action = "/servlet/controller.study.ProjectAdminServlet";
        document.form1.p_process.value = "ProjectSubmitOpenPage";
        document.form1.p_seq.value = seq;
        document.form1.p_projgrp.value = projgrp;
        document.form1.submit();
        document.form1.target = window.self.name;        
    }
    
	// 모사답안 검색
	/*function open_projectSearch() {
        window.self.name = "projectSearch";     //opener 객체에 name 을 지정한다
        open_window("openProjectSearch","","100","100","1024","768","1");      
        document.form1.target = "openProjectSearch";
        document.form1.action = "/servlet/controller.study.ProjectAdminServlet";
        document.form1.p_process.value = "ProjectSearch";
        document.form1.submit();
        document.form1.target = window.self.name;        
    }*/         
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
            <%=v_subjnm%> 과정 &gt; <%=StringManager.cutZero(v_subjseq)%>차수  </td>
			<td width="300" >
			<b>[답안파일]</b>
			<% 
			if(list.size()>0){
				ProjectData data= (ProjectData)list.get(0);
				v_upfile2   = data.getUpfile2();	
				v_realfile2 = data.getRealfile2();	

				if(!v_upfile2.equals("")){
			%>
			<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_upfile2 %>&p_realfile=<%= v_realfile2 %>' target='_new'><u><%=v_realfile2%></u></a> 
			<%	}else{ %>
				등록된 답안파일이 없습니다.
            <%
				}
			}
			%>
			</td>
            <!--<td width="400" height="20" align="right" style=padding-right:8px><b>모사답안 검색 :</b> <input type="text" class="input" name="query" size=20> </td>
            <td width="34"><a href="javascript:open_projectSearch();"><img src="/images/admin/button/b_go.gif" border=0></a></td>
			<td width=10></td>-->
            <td align=right><a href="javascript:projectList()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
          </tr>
        </table>

		<br>
        <!----------------- 추가, 리스트 버튼 끝 ----------------->

        <!----------------- 리포트 출제리스트 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="12" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="5%" class="table_title"><b>NO</b></td>
            <td class="table_title" width="8%"><b>ID</b></td>
            <td class="table_title" width="8%"><b>성명</b></td>
            <td class="table_title"><b>리포트 제목</b></td>
            <td class="table_title" width="5%"><b>첨부<br>파일</b></td>
            <td class="table_title" width="10%"><b>파일명</b></td>
            <td class="table_title" width="8%"><b>파일<br>사이즈</b></td>
            <td class="table_title" width="8%"><b>내용<br>바이트수</b></td>            
            <td class="table_title" width="8%"><b>제출일</b></td>
            <td class="table_title" width="6%"><b>평가<br>점수</b></td>
            <td class="table_title" width="6%"><b>반려<br>여부</b></td>            
            <td class="table_title" width="8%"><b>채점일<br>(반려일)</b></td>            
          </tr>
          <% 
            for(i = 0; i < list.size(); i++) {
                ProjectData data= (ProjectData)list.get(i);
                v_seq       = data.getSeq();
                v_projgrp   = data.getProjgrp();
                v_projname  = data.getProjname();
                v_title     = data.getTitle();
                v_score     = data.getScore();
								v_isfinal   = data.getIsfinal();
                v_upfile    = data.getUpfile();
                v_realfile = data.getRealfile();                            
                v_upfilesize = data.getUpfilesize();     
                v_contentsbyte = data.getContentsbyte();                                                 
                v_indate    = data.getIndate();           
                v_ldate     = data.getLdate();  // 수정일           
                v_tudate		= data.getTudate();	// 강사채점일
                v_isret     = data.getIsret();  // 반려여부           

				if(v_isfinal.equals("N")){  v_score_value = "<font color='#FF0000'>미평가</font>";  }
                else                     {  v_score_value = v_score+"점";                       }
                
                if(v_isret.equals("Y")){       
                    v_isret_value = "<font color='#FF0000'>반려</font>"; 
                    v_ldate = FormatDate.getFormatDate(data.getRetdate(),"yyyy/MM/dd"); // 반려일
                }else{
                    v_isret_value = "";                       
                    if(!v_tudate.equals("")) v_tudate = FormatDate.getFormatDate(v_tudate,"yyyy/MM/dd");  // 수정일
                }
                                
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
            <td class="table_02_2"> <a href="javascript:open_projectSubmit('<%=v_seq%>','<%=v_projgrp%>')" class="b"><%=v_title%></a> </td>
            <td class="table_02_1" align="center"> <% if(v_upfile.length() > 0){ %>
              <a href = '/servlet/controller.library.DownloadServlet?p_year=<%=v_year%>&p_subj=<%=v_subj%>&p_savefile=<%= v_upfile %>&p_realfile=<%= v_upfile_value %>'><img src="/images/admin/button/icon_file.gif" border=0></a> 
              <% } %> </td>
            <td class="table_02_1" align="center"><%=v_realfile %></td>
            <td class="table_02_1" align="center"><%=v_upfilesize%></td>                     
            <td class="table_02_1" align="center"><%=v_contentsbyte%></td>            
            <td class="table_02_1" align="center"><%=v_indate%></td>
            <td class="table_02_1" align="center"><%=v_score_value%></td>
            <td class="table_02_1" align="center"><%=v_isret_value%></td>            
            <td class="table_02_1" align="center"><%=v_tudate%></td>            
          </tr>
          <%}  
          if(i == 0){ %>
          <tr> 
            <td align="center" class="table_02_1" height="50" colspan="12">등록된 내용이 없습니다</td>
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
