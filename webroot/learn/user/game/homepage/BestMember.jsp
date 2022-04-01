<%
//**********************************************************
//  1. 제      목: 홈페이지 이달의우수회원 
//  2. 프로그램명 : BestMember.jsp
//  3. 개      요: 홈페이지 이달의우수회원  
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.19
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.lang.String.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	
	int v_seqBest		= 0;
    int    v_cnt		= 0;            // 조회수
    String v_gubun		= "";           // 전체구분
    String v_titlebest	= "";           // 제목
    String v_singleword = "";			// 한마디
    String v_content	= "";           // 내용
    String v_ldate		= "";           // 등록일
    String v_lname		= "";           // 등록자
    String v_luserid	= "";           // userid

    String v_useyn   	= "";			// 사용유무
    String v_month		= "";			// 해당월
    String v_addate		= "";           // 등록일
	String v_savefile	= "";

	HomePageSuperiorityMemberBean bestBean = new HomePageSuperiorityMemberBean();
	DataBox dbox3 = bestBean.selectBoardHome(box); 

	box.put("p_process","selectList");
    
    if (dbox3 != null) {
		v_seqBest       = dbox3.getInt("d_seq");
        v_cnt			= dbox3.getInt("d_cnt");
        v_singleword	= dbox3.getString("d_singleword"); 
        v_titlebest		= dbox3.getString("d_title"); 
        v_content		= dbox3.getString("d_contents");
        v_ldate			= dbox3.getString("d_ldate");   
        v_lname			= dbox3.getString("d_lname");
		v_useyn   		= dbox3.getString("d_useyn"); 	
        v_gubun			= dbox3.getString("d_gubun");	
        v_month			= dbox3.getString("d_lmonth");		
		v_savefile		= dbox3.getString("d_savefile");
		
        v_addate		= FormatDate.getFormatDate(v_ldate, "yyyy/MM/dd");
     }  
	     
    if(v_useyn.equals("Y")) v_useyn = "사용";
    else if(v_useyn.equals("N")) v_useyn = "사용안함";
     	
%>

<SCRIPT LANGUAGE="JavaScript">

	//상세보기
 //   function selectList(seq) {
 //       mainList.action = "/servlet/controller.homepage.HomePageSuperiorityMemberServlet";
 //       mainList.p_process.value = "select";
 //       mainList.p_seq.value		 = seq;
 //       mainList.submit();
 //   }

     function view(seq) {
        mainList.action = "/servlet/controller.homepage.HomePageSuperiorityMemberServlet";
        mainList.p_seq.value = seq;
        mainList.p_process.value = "select";
        mainList.submit();
    }



</SCRIPT>	


<form name="form1" method="post">
	<input type="hidden" name="p_seq">
			
			<table cellpadding="0" cellspacing="0" width="190">
			<tr><td colspan="2"><img src="/images/user/game/homepage/type1/tit_best.gif"></td></tr>
   <%if( dbox3 != null ){%>
			<tr height="1"><td colspan="2" background="/images/user/game/homepage/type1/dot.gif"></td></tr>
			<tr valign="top">

				<td style="padding:12px 9px 11px 3px;">

					<table width="78" border="1" cellpadding="0" cellspacing="1" bordercolor="#CCCCCC" bgcolor="#FFFFFF">
					  <tr> 
						<td>
							<%if(v_savefile.equals("")){%>
								<img src="/images/user/game/service/photo_blank.gif" width="78" height="60"></td>
							<%}else{%>
								<img src="<%=conf.getProperty("url.upload")+"bulletin/"%><%=v_savefile%>" width="78" height="60"></td>
							<%}%>
					  </tr>
					</table>
				</td>
				<td name="p_contents" style="font-size:11px;padding:14px 3px 11px 0px;" value="">
					<a href="javascript:view(<%=v_seqBest%>)">
						<%if( v_singleword.length() > 23 ){
						String v_singleword2 = (v_singleword).substring(0,23)+"...";
						%><%=v_singleword2%>
						<%}else{%><%=v_singleword%><%}%></a>
				</td>
			</tr>
	<%}else{%>
			<tr height="5"><td></td>
			<tr>	
				<td> - 등록된 우수회원이 없습니다 </td></tr>
			<tr height="3"><td></td>

	<%}%>
			
			<tr height="1"><td colspan="2" background="/images/user/game/homepage/type1/dot.gif"></td></tr>
			<tr height="10"><td></td></tr>
			</table>

</form>