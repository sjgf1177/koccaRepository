<%
//**********************************************************
//  1. 제      목: 열린마당 > 파워인터뷰
//  2. 프로그램명 : ku_HomePageFaq_L.jsp
//  3. 개      요: 파워인터뷰
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정: 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
	box.put("leftmenu","03");  

	box.put("tem_grcode",box.getSession("tem_grcode"));

	String  v_process = box.getString("p_process");
	String  v_grcode  = box.getString("tem_grcode");  
    
    int v_pageno         = box.getInt("p_pageno");
	int row = 2;
    if (v_pageno == 0) {
        v_pageno = 1;
    }
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");
	String  v_gubun		  = box.getString("p_gubun");

	int i = 0;
    int    v_seq     = 0;           // 일련번호
	int    v_cnt     = 0;           // 조회수	

    String v_title   = "";          // 튜터명
    String v_content = "";          // 내용
    String v_ldate   = "";          // 등록일
    String v_name    = "";          // 등록자
	String v_useyn   = "";			// 메인사용여부
	String v_savefile = "";			// 저장파일명
	String v_realfile = "";			// 실제파일명
	String v_select_seq = "";		// 메인 게시선택된 seq
	String v_month   = "";			// 해당월
	String v_singleword   = "";		// 제목
	String v_comptext	= "";		// 소속

	String v_content_text = "";		// 나모에서 text 만 저장	

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

	ArrayList list = (ArrayList)request.getAttribute("selectList");
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topOpenkocca.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">

     function view(seq) {
        form1.action = "/servlet/controller.homepage.KOpenPowerServlet";
        form1.p_seq.value = seq;
        form1.p_process.value = "OpenPowerDetail";
        form1.submit();
    }

	function selectView(seq) {
        form1.action = "/servlet/controller.homepage.KOpenPowerServlet";
        form1.p_seq.value = seq;
        form1.p_process.value = "OpenPowerDetail";
        form1.submit();
    }

	//페이지 이동
	function goPage(pageNum) {
		 document.form1.p_pageno.value = pageNum;
		 document.form1.action = "/servlet/controller.homepage.KOpenPowerServlet";
		 document.form1.p_process.value = "OpenPowerList";
		 document.form1.submit();
	}

</SCRIPT>	
				
<form name="form1" method="post">
	<input type="hidden" name="p_seq">
	<input type="hidden" name="p_process">
	<input type = "hidden" name = "p_pageno"    value = "<%=v_pageno %>">
	
					   <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 열린마당 > 파워인터뷰</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/openkocca/<%=tem_subimgpath%>/tit_power.gif"></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="88" rowspan="3"><img src="/images/user/kocca/openkocca/text_power.gif"></td>
                          </tr>
                          <tr> 
                            <td height="12"></td>
                          </tr>
                          <tr > 
                            <td height="1" colspan="2" background="/images/user/kocca/common/dot_bg_9.gif"></td>
                          </tr>
                          <tr > 
                            <td height="7" colspan="2" ></td>
                          </tr>
                        </table>
                        <!--게시판목록 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="5" colspan="6"></td>
                          </tr>
                          <tr> 
                            <td colspan="6"><img src="/images/user/kocca/openkocca/bar_forum_write_top.gif" ></td>
                          </tr>
                          <tr> 
                            <td height="15" colspan="6"></td>
                          </tr>
                        </table>
						<!-- 인터뷰1 --> 
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
<% String contents_value = null;
if( !list.equals("")){
            for( i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);
                v_seq   = dbox.getInt("d_seq");
                v_cnt	= dbox.getInt("d_cnt");
                v_title = dbox.getString("d_title");			// 튜터명
                v_ldate = dbox.getString("d_ldate");
                v_name  = dbox.getString("d_lname");
                v_useyn = dbox.getString("d_useyn");
                v_month = dbox.getString("d_lmonth");
                v_singleword = dbox.getString("d_singleword");  // 제목
				v_content = dbox.getString("d_contents");
				v_content_text = dbox.getString("d_content_text");
				v_comptext = dbox.getString("d_comptext");
				
                v_savefile = dbox.getString("d_savefile");
                v_realfile = dbox.getString("d_realfile");
				v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");

				if( v_content_text.length() > 130) contents_value = v_content_text.substring(0,130)+"...";
				else  contents_value = v_content_text;

                v_ldate   = FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd");
%>
                          <tr> 

                            <td width="105" style="padding-left:5px"> 
								<!-- 첨부사진 -->
								<%if( !v_savefile.equals("") ){
								v_savefile = conf.getProperty("url.upload") + "bulletin/" + v_savefile;%>
								<img src="<%=v_savefile%>"></td>
								<%}else{
								v_savefile = "/images/user/kocca/homepage/"+tem_subimgpath+"/blank_photo1.gif"; %>
								<img src="<%=v_savefile%>"></td>
								<%}%>
								<!-- 첨부사진 -->
                            <td align="right" valign="top"> 
							  <table width="560" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td colspan="8" height="0" class="linecolor_main01"></td>
                                </tr>
                                <tr> 
                                  <td class="tbl_gleft04"><%=v_month%>월 <%=v_singleword%></td>
                                  <td><a href="javascript:selectView(<%=v_seq%>)"><img src="/images/user/kocca/button/btn_more.gif" border="0"></a></td>
                                  <td width="1" valign="bottom"><img src="/images/user/kocca/openkocca/vline.gif"></td>
                                  <td width="80" align="center"><%=v_ldate%></td>
                                </tr>
                                <tr> 
                                  <td colspan="8" height="1" class="linecolor_main01"></td>
                                </tr>
                                <tr> 
                                  <td width="499" colspan="2" valign="top" class="tbl_gleft" >
									<a href="javascript:view(<%=v_seq%>)"><%=contents_value%></a>
									</td>
                                  <td width="1" ><img src="/images/user/kocca/openkocca/vline_l.gif"></td>
                                  <td class="tbl_gleft" width="90"><b><%=v_title%></b><br><%=v_comptext%></td>
                                </tr>
                              </table></td>
                          </tr>
						  <tr> 
                            <td height="17" colspan="2"></td>
                          </tr>
<%		}
	}
if(list.size() == 0){%>	  
						  <tr> 	
							<td colspan="8" height="25" valign="center" align="center"> 등록된 내용이 없습니다.</td> 
						  </tr>
						 <tr> 
						    <td colspan="8" height="1" class="linecolor_main01"></td>
						 </tr>
<%}%>                     
						  <tr> 
                            <td height="10" colspan="2"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td class="tbl_bgcolor_openkocca" height="3" colspan="2" ></td>
                          </tr>
                          <tr> 
                            <td width="630" height="27" class="linecolor_notice"> 
                              <!-- 페이지 -->
							   <table width="540" border="0" align="center" cellpadding="0" cellspacing="0">
								  <tr> 
									<td>
										<%= PageUtil.printPageListKocca(v_totalpage, v_pageno, row) %>
									</td>
								  </tr>
								</table>  
							</td>
                            <td width="50" class="linecolor_notice">&nbsp;</td>
                          </tr>
                        </table>
</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
