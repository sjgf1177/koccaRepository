 <%
//**********************************************************
//  1. 제      목: 홈페이지 파워인터뷰
//  2. 프로그램명 : Power_interview.jsp
//  3. 개      요: 홈페이지 파워인터뷰
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
<%@ page import = "com.credu.homepage.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	ConfigSet conf = new ConfigSet();

    if (box == null) box = RequestManager.getBox(request);
	box.put("p_process","main");
	String  v_process  = box.getString("p_process");
	
    box.put("tem_grcode",box.getSession("tem_grcode"));

    String  s_userid = box.getSession("userid");
    int     v_pageno   = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");

    int v_seq			= 0;
    int    v_cnt		= 0;            // 조회수
    String v_gubun		= "";           // 전체구분
    String v_title		= "";           // 제목
    String v_singleword = "";        // 한마디
    String v_content = "";           // 내용
    String v_date    = "";           // 등록일
    String v_name    = "";           // 등록자
    String v_userid  = "";           // userid
	String v_comptext = "";			 // 소속

    String v_useyn   	= "";			// 사용유무
    String v_month		= "";			// 해당월
    
    String v_realfile	= "";
    String v_savefile	= "";

	KOpenPowerBean bean = new KOpenPowerBean();
	ArrayList list =  bean.selectBoardList(box);

	for( int i = 0; i < list.size(); i++) {
		DataBox dbox	= (DataBox)list.get(i);
		v_seq   = dbox.getInt("d_seq");
		v_cnt			= dbox.getInt("d_cnt");
        v_singleword	= dbox.getString("d_singleword");		// 제목
        v_title			= dbox.getString("d_title");			// 튜터명
		v_comptext		= dbox.getString("d_comptext"); 
        v_content		= dbox.getString("d_contents");
        v_date			= dbox.getString("d_ldate");   
        v_name			= dbox.getString("d_lname");
		v_useyn   		= dbox.getString("d_useyn"); 	
        v_gubun			= dbox.getString("d_gubun");	
        v_month			= dbox.getString("d_lmonth");

		v_realfile		= dbox.getString("d_realfile");
        v_savefile		= dbox.getString("d_savefile");
		
        v_date     = FormatDate.getFormatDate(v_date, "yyyy/MM/dd");
        //v_content  = StringManager.replace(data.getAdcontent(),"\n","<br>");	
	}

%>	

<SCRIPT LANGUAGE="JavaScript">

     function view(seq) {
        pform1.action = "/servlet/controller.homepage.KOpenPowerServlet";
        pform1.p_seq.value = seq;
        pform1.p_process.value = "OpenPowerDetail";
        pform1.submit();
    }

</SCRIPT>	


<form name="pform1" method="post">
	<input type="hidden" name="p_seq">
	<input type="hidden" name="p_process">

<table width="223" border="0" cellspacing="0" cellpadding="0">
	<tr> 
	  <td><img src="/images/user/kocca/homepage/type1/st_power_interview.gif"></td>
	</tr>
	<tr> 
	  <td><img src="/images/user/kocca/homepage/type1/st_sub_power_inter.gif" width="223" height="9"></td>
	</tr>
	<tr> 
	  <td background="/images/user/kocca/homepage/type1/bg_power_inter.gif"><table width="223" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td colspan="3" rowspan="2"><table width="93" border="0" cellspacing="0" cellpadding="0">
				<tr> 
				  <td width="9" rowspan="3">&nbsp;</td>
				  <td height="4"></td>
				  <td width="6" rowspan="3">&nbsp;</td>
				</tr>
				<tr> 
				  <td><table width="78" border="1" cellpadding="0" cellspacing="1" bordercolor="#CCCCCC" bgcolor="#FFFFFF">
					  <tr> 
						<td>
							<%if( !v_savefile.equals("") ){
								v_savefile = conf.getProperty("url.upload") + "bulletin/" + v_savefile;%>
							<a href="javascript:view(<%=v_seq%>)"><img src="<%=v_savefile%>" width="74" height="56"></a></td>
							<%}else{
								v_savefile = "/images/user/kocca/homepage/type1/blank_photo1.gif"; %>
							<img src="<%=v_savefile%>" width="74" height="56"></td>
							<%}%>
							<!--img src="/images/user/kocca/homepage/type1/photo_power_inter.gif" width="74" height="56"-->
					  </tr>
					</table></td>
				</tr>
				<tr> 
				  <td height="3"></td>
				</tr>
			  </table></td>
			<td><img src="/images/user/kocca/homepage/type1/text_power_inter.gif" width="128" height="33"></td>
		  </tr>
		  <tr> 
			<td class="tbl_gleft_small">				
				<%if(v_singleword.length() < 20){
						  v_singleword = v_singleword;
				  }else{  v_singleword = (v_singleword).substring(0,20)+"...";
				  }%>
				<a href="javascript:view(<%=v_seq%>)"><%=v_singleword%></a>
			</td>
		  </tr>
		</table></td>
	</tr>
	<tr> 
	  <td><img src="/images/user/kocca/homepage/type1/st_sub_power_inter02.gif" width="223" height="8"></td>
	</tr>
	<tr> 
	  <td height="20">&nbsp;</td>
	</tr>
  </table>

 </form>