	
			   
<%
//**********************************************************
//  1. 제      목: 홈페이지 추천강좌
//  2. 프로그램명 : RecoCourse.jsp
//  3. 개      요: 홈페이지 추천강좌
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.22
//  7. 수      정:
//***********************************************************
%>
	   
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.propose.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	ConfigSet conf = new ConfigSet();

	box.put("p_process","main");

	MainSubjSearchBean subjbean = new MainSubjSearchBean();
	ArrayList recolist = subjbean.selectSubjRecomList(box);

	String v_subjnm		= "";
	String v_subj		= "";
	String v_intro		= "";
	String v_sphere		= "";
	String v_savefile   = "";
	String v_realfile   = "";
%>	
<SCRIPT LANGUAGE="JavaScript">
<!--
 
 // 과정 내용보기
    function whenSubjInfo(subj,sphere){
        document.subjform.p_subj.value     = subj;
        document.subjform.p_sphere.value   = sphere;
        document.subjform.p_process.value  = 'SubjectPreviewPage';
        document.subjform.p_rprocess.value = 'SubjectListAlway';
        document.subjform.action='/servlet/controller.course.KCourseIntroServlet';
        document.subjform.target = "_self";
        document.subjform.submit();
    }
//-->
</SCRIPT>


<form name="subjform" method="post" >
	<input type="hidden" name="p_process">
	<input type="hidden" name="p_subj">
    <input type='hidden' name='p_sphere' >
	<input type='hidden' name='p_rprocess' value="">

<!-- 이달의 추천강좌 -->
<table width="223" border="0" cellspacing="0" cellpadding="0">
	<tr> 
	  <td><img src="/images/user/kocca/homepage/type1/st_monthly_class.gif"></td>
	</tr>
	<tr> 

	  <td>
		<img src="/images/user/kocca/homepage/type1/st_sub_monthly_class.gif" width="223" height="10"></td>
	</tr>
<%  int resize = (recolist.size());
    if( resize != 0 ){
	    for(int i = 0; i < resize; i++) {
          DataBox dbox = (DataBox)recolist.get(i);
          v_subj				  = dbox.getString("d_subj");
          v_sphere				  = dbox.getString("d_sphere");
          v_subjnm				  = dbox.getString("d_subjnm");
		  v_intro				  = dbox.getString("d_intro");
		  v_realfile = dbox.getString("d_introducefilenamereal");
		  v_savefile = dbox.getString("d_introducefilenamenew");%>
	<tr> 
	  <%if((i%2)==0){%>
		<td height="56" background="/images/user/kocca/homepage/type1/bg_monthly_01.gif">
	  <%}else if((i%2)==1){%>
		<td height="70" background="/images/user/kocca/homepage/type1/bg_monthly_02.gif">
	  <%}%>
	    <table width="223" height="65" border="0" cellpadding="0" cellspacing="0">
		  <tr> 
			<td width="8" rowspan="2"></td>
			<td width="81" rowspan="2">
			  <table width="75" border="0" cellpadding="0" cellspacing="3">
				<tr> 
				  <td>
					<table width="75" border="2" cellpadding="0" cellspacing="3" bordercolor="#CCCCCC">
					  <tr> 
						<td>
							<%if( !v_savefile.equals("") ){
								v_savefile = conf.getProperty("url.upload") + "bulletin/" + v_savefile;%>
							<img src="<%=v_savefile%>" width="70" height="56"></td>
							<%}else{
								v_savefile = "/images/user/kocca/homepage/type1/blank_photo1.gif"; %>
							<img src="<%=v_savefile%>" width="70" height="56"></td>
							<%}%>
					  </tr>
					</table>
				  </td>
				</tr>
			  </table></td>
			<td width="134" height="20" class="main_contents_title_text">
				<strong><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_sphere%>')">
				<%=v_subjnm%></a></strong></td>
		  </tr>
		  <tr> 
			<td class="main_contents02">
				<%if(v_intro.length() < 25){
						  v_intro = v_intro;
				  }else{  v_intro = (v_intro).substring(0,25)+"...";
				  }%>
			  <%=v_intro%>
			</td>
		  </tr>
		</table>
	  </td>
	</tr>
	<tr> 
	  <td>
<%if( resize == i+1 ){%>		
		<img src="/images/user/kocca/homepage/type1/st_sub_monthly_class04.gif" width="223" height="13"></td>
	</tr>
<%}else{%>
		<%if( i==2 ){%>
			<img src="/images/user/kocca/homepage/type1/st_sub_monthly_class04.gif" width="223" height="9"></td>
		</tr>
		<%}else{%>
		<img src="/images/user/kocca/homepage/type1/st_sub_monthly_class02.gif" width="223" height="15"></td>
		</tr>
		<%}%>
<%}%>



<%	}	%>
<%}%>

<%if (recolist.size() == 0) { %>
		<tr> 
		  <td height="1" colspan="3"> 추천된 강좌가 없습니다.</td>
		</tr>
<%	}	%>

	<tr> 
	  <td height="20">&nbsp;</td>
	</tr>
</table>
</form>
<!-- 이달의 추천강좌 -->