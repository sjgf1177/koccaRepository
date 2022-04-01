<%
//**********************************************************
//	1. 제	   목: TUTOR HISTORY SELECT
//	2. 프로그램명: za_TutorHistory_R.jsp
//	3. 개	   요: 강사	이력 조회
//	4. 환	   경: JDK 1.3
//	5. 버	   젼: 1.0
//	6. 작	   성: 
//	7. 수	   정:
//***********************************************************
%>
<%@	page contentType = "text/html;charset=MS949" %>
<%@page	errorPage =	"/learn/library/error.jsp" %>
<%@	page import	= "java.util.*"	%>
<%@	page import	= "com.credu.tutor.*" %>
<%@	page import	= "com.credu.library.*"	%>
<%@	page import	= "com.credu.system.*" %>
<jsp:useBean id	= "conf" class = "com.credu.library.ConfigSet"	scope =	"page" />
<%
	//DEFINED class&variable START
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	String	v_process		= box.getString("p_process");
	String v_search     	= box.getString("p_search");	    //전문분야검색
	String v_select			= box.getString("p_select");		//검색항목(과정명1,강사명2)
	String v_selectvalue	= box.getString("p_selectvalue");	//검색어
	String v_userid			= "";
	String v_name			= "";
	String v_comp			= "";
	String v_dept			= "";
	String v_jik			= "";
	String v_academic		= "";
	String v_major			= "";
	String v_isgubun		= "";
	String v_isgubuntype	= "";
	String v_subjnm         = "";
	String v_photo          = "";
	String v_userid_value   = "";
	String v_isgubun_value	= "";
	String v_isgubuntype_value="";	
	String v_subj           = "";
	String v_year           = "";
	String v_subjseq        = "";
	String v_sdesc          = "";
	String v_lectdate       = "";
	String v_lectsttime     = "";
	String v_lecttime       = "";
	String v_lectlevel      = "";
	int    v_lecture        = 0;
	int    v_lectscore	    = 0;
	int	   v_careeryear		= 0;		
    int     i               = 0;	
	//DEFINED class&variable END

	TutorData data			= (TutorData)request.getAttribute("tutorSelect");
	v_userid				= data.getUserid();
	v_name					= data.getName();
	v_comp					= data.getComp();
	v_dept					= data.getDept();
	v_jik					= data.getJik();
	v_academic				= data.getAcademic();
	v_major					= data.getMajor();
	v_isgubun				= data.getIsgubun();
	v_isgubuntype			= data.getIsgubuntype();
	v_careeryear			= data.getCareeryear();
	v_photo                 = data.getPhoto();
	if( v_isgubun.equals("2"))  {	v_userid_value = v_userid.substring(0, 6)+"-"+v_userid.substring(6);  }
	else                        {   v_userid_value = v_userid;                                            }
	if(v_isgubun.equals("1"))       {   v_isgubun_value = "사내강사";   }
	else if(v_isgubun.equals("2"))  {   v_isgubun_value = "사외강사";   }
	
	if(v_isgubuntype.equals("1"))   {   v_isgubuntype_value = "전임";   }
	else if(v_isgubuntype.equals("2")){ v_isgubuntype_value = "Part Time";}
	else if(v_isgubuntype.equals("3")){ v_isgubuntype_value = "프리랜서";}
	else if(v_isgubuntype.equals("4")){ v_isgubuntype_value = "기타";   }
     	
	ArrayList list1 = (ArrayList)request.getAttribute("tutorSubjList");	
	ArrayList list2 = (ArrayList)request.getAttribute("tutorLectureList");	
%>
<html>
<head>
<meta http-equiv="Content-Type"	content="text/html;	charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
    <script language = "javascript" src = "/script/cresys_lib.js"></script>
    <script	language="JavaScript" type="text/JavaScript">
    <!--
        function r_update() {          
            document.form1.action =	"/servlet/controller.tutor.TutorAdminServlet";
            document.form1.p_process.value = "TutorJudge";
            document.form1.submit();            
        }
	    function cancel() {
            document.form1.action = "/servlet/controller.tutor.TutorAdminServlet";
            document.form1.p_process.value = "TutorHistoryList";      
            document.form1.submit();	
        }
    -->			
    </script>
</head>

<body bgcolor="#FFFFFF"	text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method	= "post">
<input type="hidden" name="p_process">
<input type="hidden" name="p_userid"	    value="<%=v_userid%>">
<input type="hidden" name="p_search"	    value="<%=v_search%>">
<input type="hidden" name="p_select"	    value="<%=v_select%>">
<input type="hidden" name="p_selectvalue"   value="<%=v_selectvalue%>">
<input type="hidden" name="p_userid"        value="<%=v_userid%>">

<table width="1000"	border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
	<td	align="center" valign="top"> 
	
	  <!----------------- title	시작 ----------------->
	  <table width="97%" border="0"	cellspacing="0"	cellpadding="0">
		<tr>
			<td	background="/images/admin/tutor/title_bg.gif"><img src="/images/admin/tutor/tutor_title3.gif" width="206" height="36" border="0"></td>
		</tr>
	  </table>
		<!-----------------	title 끝 ----------------->
		<br>
		<br>
		<!-----------------	강사정보보기 시작 ----------------->
		<table class="table_out" cellspacing="1" cellpadding="5">
		  <tr> 
			<td	colspan="3" class="table_top_line"></td>
		  </tr>
		  <tr> 
			<td	class="table_title" width="18%"><b>강사명</b></td>
			<td	class="table_02_2"><%=v_name%></td>
			<td	align="center" class="table_02_2" rowspan="4" width="15%">

			  <table width="135" border="0"	cellspacing="0"	cellpadding="0">
				<tr> 
				  <td align="center" bgcolor="#C9C9C9">	
					<table width="100%"	border="0" cellspacing="1" cellpadding="1">
					  <tr> 
						<td	height="139" bgcolor="#FFFFFF" align="center" width="105">
						<% if(v_photo != null){  %>
						<img width="135" height="179"	border="0" src="<%=v_photo%>">
						<% } %></td>
					  </tr>
					</table>
				  </td>
				</tr>
			  </table>
			  
			</td>
		  </tr>		 
		  <tr> 
			<td	class="table_title"><strong>주민등록번호/ID</strong></td>
			<td	class="table_02_2">&nbsp;<%=v_userid_value%></td>
		  </tr>
		  <tr> 
			<td	class="table_title"><b>소속기관</b></td>
			<td	class="table_02_2">&nbsp;<%=v_comp%>/<%=v_dept%>/<%=v_jik%></td>
		  </tr>
		  <tr> 
			<td	class="table_title"><b>강사구분</b></td>
			<td	class="table_02_2">&nbsp;<%=v_isgubun_value%>&nbsp;/&nbsp;<%=v_isgubuntype_value%></td>
		  </tr>
          <tr> 
			<td	class="table_title" rowspan="<%=list1.size()%>"><b>강의과정</b></td> 				           
          <%
            for(int j = 0; j < list1.size(); j++) {
                TutorData data2  = (TutorData)list1.get(j);                
                v_subjnm         = data2.getSubjnm();  		             
           %>  
           <% if(j > 1){ %> <tr> <% } %>
			<td	class="table_02_2" colspan="2"> &nbsp;<%=v_subjnm%></td>		
		   </tr>	
		  <%  
		    }  
		  %>		  

		  <tr> 
			<td	class="table_title"><b>최종학력</b></td>
			<td	class="table_02_2" colspan="2">&nbsp;<%=v_academic%></td>
		  </tr>
		  <tr> 
			<td	class="table_title"><b>강사경력</b></td>
			<td	class="table_02_2" colspan="2">&nbsp;<%=v_careeryear%>년</td>
		  </tr>
		</table>
		<!-----------------	강사정보보기 끝	----------------->


		<br>
        <table class="table_out" cellspacing="1" cellpadding="0">
          <tr> 
            <td colspan="8" class="table_top_line"></td>
          </tr>
          <tr> 
            <td class="table_title"><b>No</b></td>
            <td class="table_title"><b>과정명</b></td>
            <td class="table_title"><b>차수</b></td>
            <td class="table_title"><b>강좌명</b></td>
            <td class="table_title"><b>강의일자</b></td>
            <td class="table_title"><b>강의시간</b></td>
            <td class="table_title"><b>강의점수</b></td>
            <td class="table_title"><b>등급</b></td>
          </tr>
        <%                                                                                                                                             
            for(i = 0; i < list2.size(); i++) {
                TutorData data3 = (TutorData)list2.get(i);     
                v_subj          = data3.getSubj();         
                v_subjnm        = data3.getSubjnm();       
                v_sdesc         = data3.getSdesc();          
                v_year          = data3.getYear();         
                v_subjseq       = data3.getSubjseq();      
                v_lectdate      = data3.getLectdate();     
                v_lectsttime    = data3.getLectsttime();   
                v_lecttime      = data3.getLecttime();     
                v_lectlevel     = data3.getLectlevel();    
                v_lecture       = data3.getLecture();      
                v_lectscore	    = data3.getLectscore();                  
                v_lectdate       = FormatDate.getFormatDate(v_lectdate,"yyyy/MM/dd");
                v_lectsttime     = FormatDate.getFormatTime(v_lectsttime,"HH:mm");                           
        %>
                    
          <tr> 
            <td class="table_01"><%= list2.size() - i %></td>
            <td class="table_02_1"><%=v_subjnm%></td>
            <td class="table_02_1"><%=v_subjseq%></td>
            <td class="table_02_1"><%=v_sdesc%></td>
            <td class="table_02_1"><%=v_lectdate%> <%=v_lectsttime%></td>
            <td class="table_02_1">총 <%=v_lecttime%> 분</td>
            <td class="table_02_1"><input type="text" name="p_lectscore" value="<%=v_lectscore%>" size="5" class="input"></td>            
            <td class="table_02_1"><%=v_lectlevel%></td>
          </tr>
            <input type="hidden" name="p_subj" value="<%=v_subj%>">
            <input type="hidden" name="p_year" value="<%=v_year%>">
            <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
            <input type="hidden" name="p_lecture" value="<%=v_lecture%>">
          <%}%>
        <% if(i == 0){ %>
          <tr> 
            <td class="table_02_1" colspan="8">등록된 내용이 없습니다</td>
          </tr>
          <% } %>              
        </table>
        <!----------------- 강사조회 끝 ----------------->
        <br>

		<!-----------------	저장,취소 버튼 시작----------------->
		<table width="10%" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td	align="center" class="ms">
			<a href="javascript:r_update()"><img src="/images/admin/tutor/save_butt.gif" border="0"></a>
			</td>
			<td	align="center" class="ms">
			<a href="javascript:cancel()"><img src="/images/admin/tutor/cancel2_butt.gif" width="37" height="18" border="0"></a>
			</td>
		  </tr>
		</table>
		<!-----------------	수정, 삭제,	취소 버튼 끝----------------->

		<br>
		<br>

	  </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
