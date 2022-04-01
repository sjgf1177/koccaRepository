<%
//**********************************************************
//  1. 제      목: 나의공부방 > 나의 상담내역
//  2. 프로그램명 : ku_MyQnaSite_L.jsp
//  3. 개      요: 나의질문방
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 김학진 06.05.24
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","03"); 
    
    String  ss_userid     = box.getSession("userid");
    String  v_indate      = "";
    String  v_types       = ""; 
    String  v_title       = "";
    String  v_searchtext  = box.getString("p_searchtext");   
    String  v_select      = box.getString("p_select");   
    String  v_edustart    = "";
    String  v_eduend      = "";

     
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0, v_tabseq = 0;
    
    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");
    if (v_pageno==0) v_pageno=1;
        
    ArrayList blist2 = (ArrayList)request.getAttribute("MyQnaSiteListPage");  
      
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topMyclass.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
function press_enter(e) { 
	if (e.keyCode =='13'){  selectList();  }
}
//검색내용에 맞는 리스트 페이지로 이동
function selectList() {
	if (form1.p_searchtext.value == ""){
		alert("검색 할 내용을 입력해 주세요");
	}else{
    	document.form1.action = "/servlet/controller.study.KMyClassServlet";
    	document.form1.p_process.value = "MyQnaSiteListPage";
        document.form1.p_pageno.value = "1";
    	document.form1.submit();
	}
}

//상세화면 페이지로 이동
function select(tabseq, seq,  userid, types) {
	document.form1.action = "/servlet/controller.study.KMyClassServlet";
	document.form1.p_process.value   = "MyQnaSiteViewPage";
	document.form1.p_tabseq.value       = tabseq;
	document.form1.p_seq.value       = seq;
	document.form1.p_userid.value    = userid;
	document.form1.p_types.value     = types;
    //document.form1.p_upfilecnt.value = upfilecnt;
	document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {

	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.study.KMyClassServlet";
	document.form1.p_process.value = "MyQnaSiteListPage";
	document.form1.submit();
}

// Tab 선택시 이동
function showTab()
{
	document.form1.action = "/servlet/controller.study.KMyClassServlet";
	document.form1.p_process.value = "MyQnaStudyListPage";
    document.form1.p_pageno.value = "1";
    document.form1.p_searchtext.value = "";        
	document.form1.submit();
}
function changeView(type){
	var url = "/servlet/controller.study.KMyClassServlet?p_process=";
	url += type;
	
	document.location = url;
}
//-->
</script>


 <form name="form1" method="post" action="/servlet/controller.study.KMyQnaServlet">
 		  <input type = "hidden" name = "p_process" value = "">
		  <input type = "hidden" name = "p_pageno"  value = "<%=v_pageno%>">
		  <input type = "hidden" name = "p_seq"     value = "">
		  <input type = "hidden" name = "p_userid"  value = "">
		  <input type = "hidden" name = "p_upfilecnt" value = "">
		  <input type = "hidden" name = "p_types" value = "">
		  <input type = "hidden" name = "p_tabseq" value = "">
		  
		  		<table width="680" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td  class="location" > HOME > 마이클래스&gt; 나의상담내역</td>
                  </tr>
                </table>
                <table width="680" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><img src="/images/user/kocca/myclass/<%=tem_subimgpath%>/tit_myconsult.gif"></td>
                  </tr>
                  <tr>
                    <td height="20"></td>
                  </tr>
                </table>
                <table width="680" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="117"><img src="/images/user/kocca/myclass/ico_tab.gif"></td>
  	                <td width="10"><img src="/images/user/kocca/myclass/tab_first.gif"></td>
                      
                      	
					<td width="67"><a href="javascript:changeView('MyConsult')"> <img src="/images/user/kocca/myclass/tab_myconsult01_off.gif" name="Image31" border="0" id="Image31"></a></td>
					<td width="68"><a href="javascript:changeView('MyQnaSiteListPage')"><img src="/images/user/kocca/myclass/tab_myconsult02_on.gif" name="Image32" border="0" id="Image32"></a></td>
					<td width="418"><img src="/images/user/kocca/myclass/tab_myconsult_end.gif"></td>
                        </tr>
				  <tr>
					<td colspan="5"><img src="/images/user/kocca/myclass/st_myconsult02.gif"></td>
				  </tr>
                </table>
                <table width="680" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="9"><table width="680" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td height="7"></td>
                        </tr>
                        <tr>
                          <td><table border="0" align="right" cellpadding="0" cellspacing="0">
                              <tr>
                                <td>&nbsp;</td>
                                <td width="10">
                                <select name = "p_select" class = "input">
									  <option value="title" <% if (v_select.equals("title")) out.print("selected"); %>>제목</option>
									  <option value = "content" <% if (v_select.equals("content")) out.print("selected"); %>>내용</option>
									</select></td>
                                <td width="6">&nbsp;</td>
                                <td width="135"> <input name="p_searchtext" type="text" class="input4" value="<%=v_searchtext %>">
                                </td>
                                <td width="4"><div align="right"></div></td>
                                <td width="37"><a href="javascript:selectList()"><img src="/images/user/kocca/button/btn_search.gif"  border="0"></a></td>
                              </tr>
                            </table></td>
                        </tr>
                        <tr>
                          <td height="8"></td>
                        </tr>
                      </table></td>
                  </tr>
                </table>
                        
                <table width="679" border="0" cellspacing="0" cellpadding="0" bgcolor="#88BD95">
                  <tr>
                    <td width="5"><img src="/images/user/kocca/myclass/myconsult_bar_fir.gif" width="5" height="33"></td>
                    <td width="669" class="tbl_bgcolor_myconsult"><table width="669" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="45" class="tbl_grc"><img src="/images/user/kocca/myclass/text_t_no.gif" width="14" height="10"></td>
                          <td width="529" class="tbl_grc"><img src="/images/user/kocca/myclass/text_t_subject.gif" width="21" height="12"></td>
                          <td width="85" class="tbl_grc"><img src="/images/user/kocca/myclass/text_t_day.gif" width="21" height="12"></td>
                        </tr>
                      </table></td>
                    <td width="6"><img src="/images/user/kocca/myclass/myconsult_bar_tail.gif" width="5" height="33"></td>
                  </tr>
                  <tr>
                    <td height="3" colspan="3"></td>
                  </tr>
                  <tr>
                    <td height="1" colspan="3" class="tbl_bgcolor_notice"></td>
                  </tr>
                </table>
                <table width="680" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="20"><table width="680" border="0" cellspacing="0" cellpadding="0">
<%
        for (int i=0; i<blist2.size(); i++) {
            DataBox dbox = (DataBox)blist2.get(i);  
            v_tabseq		= dbox.getInt("d_tabseq"); 
            v_dispnum   = dbox.getInt("d_dispnum"); 
            v_indate    = dbox.getString("d_indate");
            v_types     = dbox.getString("d_types");
            v_totalpage = dbox.getInt("d_totalpage");
            v_title     = dbox.getString("d_title");
            
            int i_upfilecnt = dbox.getInt("d_upfilecnt");
            
			if (!v_searchtext.equals("")&&v_select.equals("title")) {
				v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
			}
%>                    
						<tr>
                                  <td width="45" class="tbl_grc"><%=v_dispnum%></td>
                                  <td width="529" class="tbl_gleft">
									<%if (Integer.parseInt(v_types) > 0) {%>
									&nbsp;&nbsp;<IMG src="/images/user/support/ico_re.gif" border="0"> 
			           				<% } %>
		        	                <a href="javascript:select('<%=dbox.getInt("d_tabseq")%>','<%=dbox.getInt("d_seq")%>', '<%=ss_userid%>','<%=v_types%>');"><%=v_title%></a> 
									<%if (FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){%>
										<IMG src="/images/user/support/ico_new.gif" border="0">
									<% } %> 
                                  </td>
                                  <td width="85" class="tbl_grc"><%=FormatDate.getFormatDate(v_indate, "yyyy-MM-dd")%></td>
                                 
                                </tr>
                                <tr>
                                  <td height="1" colspan="3" class="linecolor_main01"></td>
                                </tr>
<%
        }
        if(blist2.size()==0){
%>
		                  <tr height="26"> 
		                    <td colspan="3" class="tbl_grc">데이타가 없습니다.</td>
		                  </tr>
<%
        }
%>
		                  </table>
                              <table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td class="tbl_bgcolor_myconsult" height="3"></td>
                                </tr>
                                <tr>
                                  <td height="27" class="linecolor_notice">
                                    <!-- 페이지 -->
                                    <table width="540" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td>
                                        <%= PageUtil.printPageListKocca(v_totalpage, v_pageno, 10) %>
                                        </td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                                <tr>
                                  <td height="1" class="linecolor_main01"></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->