<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	String  v_process  = box.getString("p_process");
	String  v_selcomp  = box.getString("p_selcomp");
	
	int     v_pageno   = box.getInt("p_pageno");
	int     v_pagesize = box.getInt("p_pagesize");
	
	String  v_searchtext = box.getString("p_searchtext");
	String  v_search     = box.getString("p_search");
	
	String  v_selDtlCd   = box.getString("p_selDtlCd");
	String  v_selContentType   = box.getString("p_selContentType");
	
	int     v_seq          = box.getInt("p_seq");
	
	String v_clsfcd         = "";
	String v_dtlcd          = "";
	String v_contenttype    = "";
	String v_title          = "";
	String v_content        = "";
	String v_professor      = "";
	String v_pictureurl     = "";
	String v_width    		= "";
	String v_height		    = "";
	String v_gubun          = "";
	String v_genre          = "";
	String v_inspector      = "";
	String v_runningtime    = "";
	String v_production     = "";
	String v_analyze        = "";
	String v_usechk         = "";
	String v_splecturenm    = "";
	String v_spcontent      = "";
	String v_mainyn         = "";
	String v_fileDelYn      = "";
	String v_imageurl       = "";
	String v_professorimg   = "";
	String v_upfile         = "";
	String v_svrfile        = "";
	String v_indate         = "";
	
	DataBox dbox = (DataBox)request.getAttribute("selectView");
	if (dbox != null) {
		v_clsfcd         = dbox.getString("d_clsfcd");      
	    v_dtlcd          = dbox.getString("d_dtlcd");       
	    v_contenttype    = dbox.getString("d_contenttype"); 
	    v_title          = dbox.getString("d_title");       
	    v_content        = dbox.getString("d_content");     
	    v_professor      = dbox.getString("d_professor");   
	    v_pictureurl     = dbox.getString("d_pictureurl");  
	    v_width		     = dbox.getString("d_width");  
	    v_height	     = dbox.getString("d_height");  
	    v_gubun          = dbox.getString("d_gubun");       
	    v_genre          = dbox.getString("d_genre");       
	    v_inspector      = dbox.getString("d_inspector");   
	    v_runningtime    = dbox.getString("d_runningtime"); 
	    v_production     = dbox.getString("d_production");  
	    v_analyze        = dbox.getString("d_analyze");     
	    v_usechk         = dbox.getString("d_usechk");      
	    v_splecturenm    = dbox.getString("d_splecturenm"); 
	    v_spcontent      = dbox.getString("d_spcontent");   
	    v_mainyn         = dbox.getString("d_mainyn");      
	    v_fileDelYn      = dbox.getString("d_fileDelYn");   
	    v_imageurl       = dbox.getString("d_imageurl");    
	    v_professorimg   = dbox.getString("d_professorimg");
	    v_upfile         = dbox.getString("d_file");        
	    v_svrfile        = dbox.getString("d_file");
	    v_indate         = dbox.getString("d_indate");
	}
	
	String  s_gadmin    = box.getSession("gadmin");
	String v_gadmin ="";
	if(!s_gadmin.equals("")){
	  v_gadmin = s_gadmin.substring(0,1);
	}

%>
<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--

//리스트페이지로 이동
function selectList() {

   	document.form1.action = "/servlet/controller.infomation.PracticalCourseHomePageServlet";
	document.form1.p_process.value = "selectList";     
	document.form1.target  =   "_self";
	document.form1.submit();
}      

function openPracticalCourse(contenttype)
{
    
    if(contenttype == "A1"){
        window.open("about:blank", "ViewVod", "top=0, left=0, width=480, height=360, status=no, resizable=no");
    } else if(contenttype == "C1") {
    	window.open('<%= v_pictureurl %>', "ViewVod", "top=0, left=0, width=<%=v_width%>, height=<%=v_height%>, status=no, resizable=no");
    	return;
    } else {
        window.open("about:blank", "ViewVod", "top=0, left=0, width=300, height=20, status=no, resizable=no");
    }

    document.form1.action="/servlet/controller.infomation.PracticalCourseHomePageServlet";
    document.form1.p_process.value = "popUpVod";
    document.form1.target="ViewVod";
    document.form1.submit();  
    document.form1.target  =   "_self";
}


//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1" method = "post">
		<input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
		<input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
		<input type = "hidden" name = "p_select"      value = "<%= v_search %>">
		<input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
		<input type = "hidden" name = "p_selDtlCd"        value = "<%= v_selDtlCd %>">
        <input type = "hidden" name = "p_selContentType"  value = "<%= v_selContentType %>">
		<input type = "hidden" name = "p_process"     value = "">
		
		<%if( box.getSession("tem_grcode").equals("N000001")) { %>
		
		<table>
			<tr>
				<td>
		
			<!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/library/stitle_03.gif" alt="실무강좌" /></td>
        <td class="h_road">Home &gt; 정보플러스  &gt; <strong>실무강좌</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="2" colspan="3" bgcolor="#7692CB"></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
      <tr>
        <td width="32%"><div align="center">
            <table border="0" cellpadding="0" cellspacing="1" bgcolor="#D9DFEB">
              <tr>
                <td><div align="center">
                    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="eeeeee">
                      <tr>
                        <td width="180" height="150"><img src="/servlet/controller.library.DownloadServlet?p_savefile=<%=v_imageurl %>" width="152" height="114" alt="" /><div align="center"></div></td>
                      </tr>
                    </table>
                </div></td>
              </tr>
            </table>
        </div></td>
        <td width="68%"><div align="center">
            <table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
              <tr>
                <td width="4%" height="25"><strong class="org">ㆍ</strong></td>
                <td height="25" colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="65%"><div align="left"><strong><%=v_title %></strong></div></td>
                      <td width="18%"><a href="javascript:openPracticalCourse('<%=v_contenttype %>')"><span class="h_road"><img src="/images/portal/homepage_renewal/library/btn_book_view.jpg" width="87" height="25" /></span></a></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td height="1" colspan="3" bgcolor="eeeeee"></td>
              </tr>
              <tr>
                <td height="25"><strong class="org">ㆍ</strong></td>
                <td width="18%" height="25"><div align="left">교육과정명 </div></td>
                <td width="78%" height="30"><div align="left"><%=v_production %></div></td>
                </tr>
              <tr>
                <td height="1" colspan="3" bgcolor="eeeeee"></td>
              </tr>
              <tr>
                <td height="25"><strong class="org">ㆍ</strong></td>
                <td height="25"><div align="left">제작일 </div></td>
                <td height="30"><div align="left"><%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></div></td>
                </tr>
              <tr>
                <td height="1" colspan="3" bgcolor="eeeeee"></td>
              </tr>
              <tr>
                <td height="25"><strong class="org">ㆍ</strong></td>
                <td height="25"><div align="left">장르</div></td>
                <td><div align="left"><%=v_genre %></div></td>
              </tr>
              <tr>
                <td height="1" colspan="3" bgcolor="eeeeee"></td>
              </tr>
              <tr>
                <td height="25"><strong class="org">ㆍ</strong></td>
                <td height="25"><div align="left">제작</div></td>
                <td height="30"><div align="left"><%=v_inspector %></div></td>
                </tr>
              <tr>
                <td height="1" colspan="3" bgcolor="eeeeee"></td>
              </tr>
              <tr>
                <td height="25"><strong class="org">ㆍ</strong></td>
                <td height="25"><div align="left">러닝타임</div></td>
                <td height="30"><div align="left"><%=v_runningtime %></div></td>
                </tr>
              <tr>
                <td height="1" colspan="3" bgcolor="eeeeee"></td>
              </tr>
            </table>
        </div></td>
        <td></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" colspan="2" bgcolor="#7692CB"></td>
      </tr>
      <tr>
        <td width="22%" class="tit_table">강좌명</td>
        <td width="78%" class="ct_tit_table_lright"><strong><%=v_professor%></strong></td>
      </tr>
      <tr>
        <td class="tit_table">작품해설</td>
        <td class="ct_tit_table_lright"><%=v_analyze.replace("\r\n","<br>") %></td>
      </tr>
      <tr>
        <td class="tit_table">내용</td>
        <td class="ct_tit_table_lright"><p><%=v_content.replace("\r\n","<br>") %></p></td>
      </tr>

      <tr>
        <td height="1" colspan="2" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="2" bgcolor="F7F7F7"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td class="h_road"><a href="javascript:selectList();"><img src="/images/portal/homepage_renewal/myclass/btn_list.jpg" alt="목록" width="46" height="25" /></a></td>
      </tr>
    </table>
    <!--타이틀부분//-->
    </td>
    <td width="20">&nbsp;</td>
    <td valign="top">
    
   
  </tr>
</table>
		
		<%}else{ %>
                  
            <h2><img src="/images/portal/information/h2_tit2.gif" alt="실무강좌" class="fl_l" /><p class="category">Home > 정보광장 > <strong>실무강좌</strong></p></h2>

            <p><img src="/images/portal/information/class_borimg.gif" alt="실무강좌는 무료로 제공되는 공개강좌로 분야별로 다향한 주제의 심도있는 강좌를 수강하실 수 있습니다. " /></p>
            
            
            <p class="mg_t30 board_btn"><a href="javascript:openPracticalCourse('<%=v_contenttype %>')" class="btn_violet"><span>강좌보기</span></a></p>

            <div class="info_photo_list mg_t10">
                <dl class="photo_list bo_bn">
                    <dt><img src="/servlet/controller.library.DownloadServlet?p_savefile=<%=v_imageurl %>" width="152" height="114" alt="" /></dt>
                    <dd class="tit"><%=v_title %></dd>
                    <dd><span class="tit">교육과정명</span> <span class="con"><%=v_production %></span></dd>
                    <dd><span class="tit">제작일</span> <span class="con"><%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></span></dd>
                    <dd><span class="tit">장  르</span> <span class="con"><%=v_genre %></span></dd>
                    <dd><span class="tit">제작</span> <span class="con"><%=v_inspector %></span></dd>
                    <dd><span class="tit">러닝타임</span> <span class="con"><%=v_runningtime %></span></dd>
                </dl>

                <table class="list">
                <colgroup><col width="110px" /><col width="580px" /></colgroup>
                <thead>
                <tr>
                    <th class="ta_l">강사명</th>
                    <td class="ta_l end"><%=v_professor%></td>
                </tr>
                <tr>
                    <th class="ta_l">작품해설</th>
                    <td class="ta_l end"><%=v_analyze.replace("\r\n","<br>") %></td>
                </tr>
                <tr>
                    <th class="ta_l">내용</th>
                    <td class="ta_l end"><%=v_content.replace("\r\n","<br>") %></td>
                </tr>
                </thead>
                </table>

                <p class="board_btn"><a href="javascript:selectList();" class="board_violet"><span>목록</span></a></p>            
            </div>
            <%} %>
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->
