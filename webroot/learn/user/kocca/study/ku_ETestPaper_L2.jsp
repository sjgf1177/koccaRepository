
<%
/**
 * file name : ku_EtestPaper_L2.jsp
 * date      : 2005/12/14
 * programmer:	lyh
 * function  : e-Test list 출력
 */
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.etest.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    String  ss_userid    = box.getSession("userid");
    box.put("leftmenu","06"); 
    
    String v_updir = conf.getProperty("dir.etest.paperpath");  

	String today = FormatDate.getDate("yyyyMMddHHmmss");
    
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topMyclass.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--


// 팝업
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

// e-test 응시 뷰
function etestWright(p_etestsubj, p_year, p_etestcode,p_startdt,p_enddt,p_etestnum,p_trycnt){
    var now = document.form1.p_servertime.value;
	//alert(now);
    if (now < p_startdt) {
        alert("아직 시험시간이 아닙니다.");
        return;
    }else if(now > p_enddt) {
        alert("시험이 이미 종료됐습니다.");
        return;
    }
    
	if(p_trycnt> 0){
		if (confirm("기존에 응시하셨던 시험점수는 삭제되고 재응시하신 성적이 반영됩니다.\r\n그래도 응시하시겠습니까?")){
			p_trycnt = p_trycnt; 
		} else {return;} 	
	}	
      
      window.self.name = "winIndividualInsertPage";     //      opener 객체에 name 을 지정한다
	  farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=690, height=600, top=0, left=0");
	  document.form1.target = "openWinInsert"
	  document.form1.p_process.value = 'ETestUserPaperListPage';
      document.form1.p_etestsubj.value  = p_etestsubj; 
	  document.form1.p_gyear.value		= p_year; 
	  document.form1.p_etestcode.value	= p_etestcode;
	  document.form1.p_startdt.value	= p_startdt;
	  document.form1.p_enddt.value		= p_enddt;
     document.form1.p_etestnum.value = p_etestnum;
     document.form1.p_userid.value = "<%=ss_userid%>";
    
    document.form1.action = "<%=v_updir%>" + p_year + "_" + p_etestsubj + "/" + p_etestsubj + p_year + p_etestcode + p_etestnum + ".jsp";
    //alert(document.form1.action);
    //alert(p_enddt);
    //document.form1.action = "/servlet/controller.etest.ETestUserServlet";
    document.form1.submit();

    farwindow.window.focus();
    document.form1.target = window.self.name;
    
}

// e-test 완료 뷰
function IndividualResult(p_etestsubj, p_year, p_etestcode, p_userid, p_etesttext, p_average, p_personcnt,p_isopenexp) {

  //if(p_isopenexp == 'Y'){
	  window.self.name = "winIndividualResult";
	  farwindow = window.open("", "openIndividualResult", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=690, height=600, top=0, left=0");
	  document.form2.target = "openIndividualResult";
	  document.form2.action = "/servlet/controller.etest.KETestUserServlet";
	  document.form2.p_process.value = "ETestUserPaperResult2";
	  
	  document.form2.p_etestsubj.value  = p_etestsubj; 
	  document.form2.p_gyear.value		= p_year; 
	  document.form2.p_etestcode.value	= p_etestcode; 
	  document.form2.s_etestsubj.value  = p_etestsubj; 
	  document.form2.s_gyear.value		= p_year; 
	  document.form2.s_etestcode.value	= p_etestcode; 
	  document.form2.p_etesttext.value  = p_etesttext; 
	  document.form2.p_userid.value		= p_userid;
	  document.form2.submit();
	
	  farwindow.window.focus();
	  document.form2.target = window.self.name;
	//}else{
	 //alert("해설및 정답이 공개되지 않습니다");
	//}	  
}


// 분류별 결과보기 
function GubunResult(p_etesttext,p_etestsubj, p_year, p_etestcode) {

  window.self.name = "winGubunResult";
  farwindow = window.open("", "openGubunResult", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=800, height=600, top=0, left=0");
  document.form2.target = "openGubunResult";
  document.form2.action = "/servlet/controller.etest.KETestResultServlet";
  document.form2.p_process.value = "ETestGubunResult";

  document.form2.p_etesttext.value  = p_etesttext;   
  document.form2.p_etestsubj.value  = p_etestsubj; 
  document.form2.p_gyear.value      = p_year; 
  document.form2.p_etestcode.value  = p_etestcode; 
  document.form2.p_etestcode.value  = p_etestcode; 
  document.form2.submit();

  farwindow.window.focus();
  document.form2.target = window.self.name;
}


function EtestReload(){
	self.location="/servlet/controller.etest.KETestUserServlet?p_process=ETestUserListPage";
}
//-->
</script>
<table width="680" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
<tr> 
  <td width="20">&nbsp;</td>
  <td valign="top"><table width="680" border="0" cellspacing="0" cellpadding="0">
	  <tr> 
		<td  class="location" > HOME > 마이클래스&gt; e-test</td>
	  </tr>
	</table>
	<table width="680" border="0" cellspacing="0" cellpadding="0">
	  <tr> 
		<td><img src="/images/user/kocca/myclass/<%=tem_subimgpath%>/tit_etest.gif"></td>
	  </tr>
	  <tr> 
		<td height="20"></td>
	  </tr>
	</table>
	<table width="680" border="0" cellspacing="0" cellpadding="0">
	  <tr> 
		<td><img src="/images/user/kocca/myclass/text_etest.gif" width="680" height="33"></td>
	  </tr>
	  <tr>
		<td height="15"></td>
	  </tr>
	</table>
	<table width="680" border="0" cellspacing="0" cellpadding="0">
	  <tr> 
		<td><img src="/images/user/kocca/myclass/st_etest.gif" width="198" height="14"></td>
	  </tr>
	  <tr> 
		<td height="15"></td>
	  </tr>
	  <tr> 
		<td class="font_ex">학습하시고자 하는 과정의 학습하기 버튼을 클릭하시면 학습창으로 
		  이동합니다. </td>
	  </tr>
	  <tr> 
		<td height="9"></td>
	  </tr>
	</table>

<table width="675" border="0" cellspacing="0" cellpadding="0">
  <tr valign="top"> 
    <td colspan="2"> 
      <!--e-test평가-->

<%
         ArrayList blist = (ArrayList)request.getAttribute("ETestUserList");
		 DataBox dbox01 = null;
		 if(blist.size() > 0){
		     dbox01 = (DataBox)blist.get(0);
		 }else{
		     dbox01 = new DataBox("resoponsebox");
		 }

		 int count = 0;
		 int count2=0;
%>
 <form name="form1" method="post" action="/servlet/controller.etest.KETestUserServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_etestsubj"    value="<%=dbox01.getString("d_etestsubj")%>">
        <input type="hidden" name="p_gyear"  value="<%=dbox01.getString("d_year")%>">
        <input type="hidden" name="p_etestcode"  value="<%=dbox01.getString("d_etestcode")%>">
        <input type="hidden" name="p_startdt"  value="<%=dbox01.getString("d_startdt")%>">
        <input type="hidden" name="p_enddt"  value="<%=dbox01.getString("d_enddt")%>">
        <input type="hidden" name="p_etestnum"  value="">
        <input type="hidden" name="p_userid"  value="">
        <input type="hidden" name="p_servertime" value="<%=today%>">
</form>



      <table width="" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
        <tr  class="lcolor"> 
          <td height="3" colspan="8" class="linecolor_my"></td>
        </tr>
        <tr> 
			<td width="24" class="tbl_ptit2">번호</td>
			<td width="201" class="tbl_ptit">테스트명</td>
			<td width="29" class="tbl_ptit2">연도</td>
			<td width="36" class="tbl_ptit">문제수</td>
			<td width="110" class="tbl_ptit2">접속시작시간</td>
			<td width="110" class="tbl_ptit">접속종료시간</td>
			<td width="60"  class="tbl_ptit2">시험시간</td>
			<td width="60"  class="tbl_ptit2">응시횟수</td>                            
        </tr>
<%
		int v_trycnt =0;
		int v_retrycnt =0;
        for (int i=0; i<blist.size(); i++) {
            count ++; 
            DataBox dbox = (DataBox)blist.get(i);   
			//System.out.println("d_papercnt=="+dbox.getInt("d_papercnt"));
			Random ran = new Random();
      		int random = 0;
			//while(random==0){
				random = ran.nextInt(dbox.getInt("d_papercnt"))+1;
				//System.out.println("random=="+random);
			//	 break;
			//}
			
			v_trycnt = dbox.getInt("d_trycnt");
			v_retrycnt = dbox.getInt("d_retrynum");
    
%>
                          <tr> 
                            <td class="tbl_grc"><%=String.valueOf(i+1)%></td>
                            <td class="tbl_bleft">                                     
							<% if(v_trycnt < v_retrycnt){ %><a href="javascript:etestWright('<%=dbox.getString("d_etestsubj")%>','<%=dbox.getInt("d_year")%>','<%=dbox.getString("d_etestcode")%>','<%=dbox.getString("d_startdt")%>','<%=dbox.getString("d_enddt")%>','<%=random%>',<%=v_trycnt%>)"><% }else{ %>
                                              <a href="javascript:alert('이미 응시 하셨습니다.')"><% } %>
                                              <%=String.valueOf(dbox.getString("d_etesttext"))%>
                                              </a>
							</td>
                            <td class="tbl_grc"><%=dbox.getInt("d_year")%></td>
                            <td class="tbl_grc"><%=dbox.getInt("d_etestcnt")%></td>
                            <td class="tbl_grc"><%=FormatDate.getFormatDate(dbox.getString("d_startdt"),"yyyy-MM-dd HH:mm")%></td>
                            <td class="tbl_grc"><%=FormatDate.getFormatDate(dbox.getString("d_enddt"),"yyyy-MM-dd HH:mm")%></td>
                            <td class="tbl_grc"><%=dbox.getInt("d_etesttime")%>분</td>
                            <td class="tbl_grc"><%=v_trycnt%>/(총 <%=v_retrycnt%>회)</td>                            
                          </tr>
<%    
        } 

%>

		  <% if(count == 0){ %>
		  <tr>
			<td align=center class=boardskin1_textn colspan="8">해당하는 테스트가 없습니다.</td>
		  </tr>
		  <% } %>

        </tr>
      </table>

		<table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td height=22>&nbsp;</td>
		  </tr>
		  <tr> 
			<td><img src="/images/user/kocca/myclass/st_etest_result.gif" width="198" height="14"></td>
		  </tr>
		  <tr> 
			<td height="15"></td>
		  </tr>
		  <tr> 
			<td class="font_ex">테스트명을 클릭하시면 테스트 결과를 조회 하실 수 있습니다. 
			  운영자가 채점을 해야 결과가 조회됩니다.</td>
		  </tr>
		  <tr> 
			<td height="9"></td>
		  </tr>
		</table>

      <!--e-test결과-->

<%
         ArrayList blist1 = (ArrayList)request.getAttribute("ETestUserHistoryList");
		 DataBox dbox0 = null;
		 if(blist1.size() > 0){
		     dbox0 = (DataBox)blist1.get(0);
		 }else{
		     dbox0 = new DataBox("resoponsebox");
		 }

%>
 <form name="form2" method="post" action="/servlet/controller.etest.ETestUserServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_etestsubj"    value="<%=dbox01.getString("d_etestsubj")%>">
        <input type="hidden" name="p_gyear"  value="<%=dbox01.getString("d_year")%>">
        <input type="hidden" name="p_etestcode"  value="<%=dbox01.getString("d_etestcode")%>">
        <input type="hidden" name="s_etestsubj"    value="<%=dbox01.getString("d_etestsubj")%>">
        <input type="hidden" name="s_gyear"  value="<%=dbox01.getString("d_year")%>">
        <input type="hidden" name="s_etestcode"  value="<%=dbox01.getString("d_etestcode")%>">
        <input type="hidden" name="p_startdt"  value="<%=dbox01.getString("d_startdt")%>">
        <input type="hidden" name="p_enddt"  value="<%=dbox01.getString("d_enddt")%>">
        <input type="hidden" name="p_userid"  value="">
        <input type="hidden" name="p_etesttext"  value="">
  	    <input type="hidden" name="p_personcnt"   value="1">
		<input type="hidden" name="p_average"  value="1">
		<input type="hidden" name="p_print"  value="1">
</form>
<!--분류별 통계 인쇄--><!-- 학습예정과정 -->

      <table width="680" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
        <tr  class="lcolor"> 
          <td height="3" colspan="5" class="linecolor_my"></td>
        </tr>
        <tr> 
          <td width="30" class="tbl_ptit2">번호</td>
          <td width="40" class="tbl_ptit">연도</td>
          <td width="320" class="tbl_ptit2">테스트명</td>
          <td width="130" class="tbl_ptit2">응시일</td>
          <td width="130" class="tbl_ptit">점수</td>
        </tr>

<%
		String v_isopenanswer =""; //점수공개
		String v_isopenexp ="";		//해설공개
		String v_ended ="";
		String v_isDo ="N";

		//jkh 0313
        int v_isnotended = 0;
        String v_score= "0";
        for (int i=0; i<blist1.size(); i++) {
            count2 ++; 
            DataBox dbox1 = (DataBox)blist1.get(i);   
            v_isopenanswer = dbox1.getString("d_isopenanswer");
            v_isopenexp = dbox1.getString("d_isopenexp");
            v_ended =dbox1.getString("d_ended");
             v_isDo ="N"; 
            if(v_ended.length() >0){ v_isDo ="Y"; }
            
			//jkh 0313	
			 v_trycnt = dbox1.getInt("d_trycnt");
			 v_isnotended = dbox1.getInt("d_isnotended");
			 
			 if(v_trycnt > 0 || v_isnotended < 0){
			     v_score = dbox1.getInt("d_score")+"";
%>

                          <tr> 
                            <td class="tbl_grc"><%=String.valueOf(i+1)%></td>
                            <td class="tbl_grc"><%=dbox1.getInt("d_year")%></td>
                            <td class="tbl_bleft">
								  <% if("Y".equals(v_isDo)) { %>
									<a href="javascript:IndividualResult('<%=dbox1.getString("d_etestsubj")%>','<%=dbox1.getString("d_year")%>','<%=dbox1.getString("d_etestcode")%>','<%=ss_userid%>','<%=dbox1.getString("d_etesttext")%>','1','1','<%=v_isopenexp%>')" class="e"><%=String.valueOf(dbox1.getString("d_etesttext"))%></a></td>
								  <% }else{ %>
									<%=String.valueOf(dbox1.getString("d_etesttext"))%>
								  <% } %>
							</td>

                            <td class="tbl_grc">
								  <% if("Y".equals(v_isDo)) { %>
								  <%=FormatDate.getFormatDate(dbox1.getString("d_ended"),"yyyy-MM-dd HH:mm:ss")%>
								  <% }else{ %>
									<% if(v_trycnt > 0) { v_score ="" ; %>
										결과반영중
									<% }else{ %>
										미응시
									<% } %>
								  <% } %>							
							</td>
                            <td class="tbl_grc">
								  <% if("Y".equals(v_isopenanswer)){ %>
								  <%=v_score%>
								  <% }else{ %>
								  비공개
								  <% }%>							
							</td>
                            <!--td class="tbl_grc">
								  <% if("Y".equals(v_isDo)) { %>
<a href="javascript:GubunResult('<%=dbox1.getString("d_etesttext")%>','<%=dbox1.getString("d_etestsubj")%>','<%=dbox1.getString("d_year")%>','<%=dbox1.getString("d_etestcode")%>');" class="e"><u>통계</u></a>
								  <% }	%>						
							</td-->
                          </tr>
<%    
        	}
        } // for end
%>
						  <% if(count2 == 0){ %>
						  <tr>
							<td class=boardskin1_textn colspan="5" align=center>테스트 이력이 없습니다.</td>
						  </tr>
						  <% }%>

                        </table>
        <tr> 
          <td height="26" class="tbl_grc">&nbsp;</td>
          <td height="26" class="tbl_grc">&nbsp;</td>
          <td height="26" class="tbl_grc">&nbsp;</td>
          <td class="tbl_bleft">&nbsp;</td>
          <td class="tbl_grc">&nbsp;</td>
          <td class="tbl_grc">&nbsp;</td>
        </tr>
      </table>
	  </td>
  </tr>
</table>
            
<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->