<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    ArrayList list1 = null;
    ArrayList list2 = null;

    String  v_process  = box.getString("p_process");

    // 검색
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    
    String  v_selClsfCd   = box.getString("p_selClsfCd");
    String  v_selDtlCd   = box.getString("p_selDtlCd");
    
    int v_pageno    = box.getInt("p_pageno");
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount  = 0;
    int v_pagesize = box.getInt("p_pagesize");
	int	 v_totalrowcount =  0;
    
    int    v_seq     = 0;              // 일련번호
    int    v_cnt     = 0;              // 조회수

    String v_gubun           = "";
    String v_clsfcd          = "";
    String v_clsfnm          = "";
    String v_dtlcd           = "";
    String v_dtlnm           = "";
    String v_title           = "";
    String v_professor       = "";
    String v_usechk          = "";
    String v_userid          = "";
    String v_name            = "";
    String v_useyn           = "";
    String v_indate          = "";
    String v_fileyn          = "";
    
    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");
    
    list2 = (ArrayList)request.getAttribute("selectList");
   
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->

<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
        document.nform1.p_pageno.value = index;
        document.nform1.target  =   "_self";
        document.nform1.action = "/servlet/controller.infomation.SpecialCourseHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //페이지 변경
    function goPage(pageNum) {
        document.nform1.p_pageno.value = pageNum;
        document.nform1.target  =   "_self";
        document.nform1.action = "/servlet/controller.infomation.SpecialCourseHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //리스트 조회
    function select() {
        document.nform1.p_pageno.value = 0;
        document.nform1.target  =   "_self";
        document.nform1.action = "/servlet/controller.infomation.SpecialCourseHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //상세보기
    function selectView(seq) {
    	document.nform1.target  =   "_self";
        document.nform1.action = "/servlet/controller.infomation.SpecialCourseHomePageServlet";
        document.nform1.p_process.value = "selectView";
        document.nform1.p_seq.value= seq;
        document.nform1.submit();
    }

//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform1" method="post" onsubmit="javascript:select();">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "p_pageno"  value="<%=v_pageno%>"> 
    <input type = "hidden" name = "p_seq"    >
    
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
        <td><img src="/images/portal/homepage_renewal/offline/stitle_05.gif" alt="교육자료실" /></td>
        <td class="h_road">Home &gt; 오프라인 과정  &gt; <strong>교육자료실
</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <!--타이틀부분//-->

      <table width="672" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="15"></td>
        </tr>
        <tr>
          <td height="15"><img src="/images/portal/homepage_renewal/offline/bar_01.jpg" /></td>
        </tr>
        <tr>
          <td height="15"></td>
        </tr>
        <tr>
          <td>
          
          <!--검색부분-->
          <table width="672" border="0" cellpadding="0" cellspacing="1" bgcolor="ADBBD8">
            <tr>
              <td height="34" bgcolor="F0F0F0"><table border="0" align="center" cellpadding="3" cellspacing="0">
                <tr>
                  <td><span class="tit_table">
                    <select name="p_search">
                        <option value="title"   <%if("title".equals(v_search)) out.print(" selected");%>>제목</option>
                        <option value="content" <%if("content".equals(v_search)) out.print(" selected");%>>제목+내용</option>
                    </select>
                  </span></td>
                  <td><input name="p_searchtext" type="text" class="inbox" id="textfield4" size="30" value="<%=v_searchtext %>" onkeypress="javascript:fnKeyPressEnter(event, 'select');"/></td>
                  <td><a href="javascript:select();"><img src="/images/portal/homepage_renewal/common/btn_search_in.jpg" alt="검색" width="46" height="24" /></a></td>
                </tr>
                
              </table></td>
            </tr>
          </table>
          <!--검색부분//-->          </td>
        </tr>
        <tr>
          <td height="15"></td>
        </tr>
      </table>
      
      <!--테이블부분-->
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="tit_table">번호</td>
          <td class="tit_table" width="80"><kocca_select:select                                       
	                name="p_selClsfCd"                                         
	                sqlNum="0003"                                    
	                param="0087" param2="<%= v_selClsfCd %>" param3="1"              
	                onChange="select();"                         
	                attr= "분류"                                        
	                selectedValue="<%=v_selClsfCd%>"                         
	                isLoad="true"                                           
	                type="3"                                                
	                styleClass=""                                
	                all="true" /></td>
          <td class="tit_table"><kocca_select:select                                       
	                name="p_selDtlCd"                                         
	                sqlNum="0004"                                    
	                param="0087" param2="<%= v_selClsfCd %>" param3="2"               
	                onChange="select();"                         
	                attr= "세부분류"                                        
	                selectedValue="<%=v_selDtlCd%>"                         
	                isLoad="true"                                           
	                type="3"                                                
	                styleClass=""                                
	                all="true" /></td>
          <td class="tit_table">제목</td>
          <td class="tit_table">작성자</td>
          <td class="tit_table">작성일자</td>
          <td class="tit_table">첨부파일</td>
          <td class="tit_table_right">조회</td>
        </tr>
        <%
			if(list2.size() != 0){
			  for( int i = 0; i < list2.size(); i++) {
			
			    DataBox dbox     = (DataBox)list2.get(i);
			    
			    v_gubun           = dbox.getString("d_gubun");  
			    v_seq             = dbox.getInt   ("d_seq");            
			    v_clsfcd          = dbox.getString("d_clsfcd");       
			    v_clsfnm          = dbox.getString("d_clsfnm");
			    v_dtlcd           = dbox.getString("d_dtlcd");        
			    v_dtlnm           = dbox.getString("d_dtlnm");        
			    v_title           = dbox.getString("d_title");        
			    v_professor       = dbox.getString("d_professor");    
			    v_userid          = dbox.getString("d_userid");       
			    v_name            = dbox.getString("d_name");
			    v_useyn           = dbox.getString("d_useyn"); 
			    v_indate          = dbox.getString("d_indate");
			    v_cnt             = dbox.getInt   ("d_cnt");
			    v_fileyn          = dbox.getString("d_fileyn");
			    v_dispnum        = dbox.getInt("d_dispnum");
			    v_totalpage      = dbox.getInt("d_totalpage");
			    v_rowcount       = dbox.getInt("d_rowcount");
			    
			    //if (!v_searchtext.equals("")&&v_search.equals("title")) {
			    if (!v_searchtext.equals("")) {
			        v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
			    } 
				v_totalrowcount = dbox.getInt("d_totalrowcount");
				v_rowcount	  = dbox.getInt("d_rowcount");
			%>        
        <tr>
          <td class="ct_tit_table"><%=v_dispnum%></td>
          <td class="ct_tit_table"><%=v_clsfnm%></td>
          <td class="ct_tit_table"><p><%=v_dtlnm%>&#13;</p></td>
          <td class="ct_tit_table_left"><a href="javascript:selectView(<%=v_seq%>)"><%=v_title%></a></td>
          <td class="ct_tit_table"><%=v_professor%></td>
          <td class="ct_tit_table"><%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></td>
          <td class="ct_tit_table"><%if(v_fileyn.equals("Y")){%><img src="/images/portal/ico/ico_file.gif" alt="file"><%} else {%>-<%}%></td>
          <td class="ct_tit_table_right"><%=v_cnt%></td>
        </tr>
			<%      }
			    }else{%>

	      <tr> 
	        <td colspan ='8'>등록된 내용이 없습니다.</td>
	      </tr>
		  <%  }   %>
        
        <tr>
          <td height="1" colspan="8" bgcolor="CACACA"></td>
        </tr>
        <tr>
          <td height="4" colspan="8" bgcolor="F7F7F7"></td>
        </tr>
      </table>
      <!-- 타이틀 부분 -->
      <br/>
      <!-- total 시작 -->
		 <%= PageUtil.re_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
		<!-- total 끝 -->
		
		</td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
      
      			      
		      </tr>		
		</table>
    	
    	    
    
    
    	
    <%}else{ %>
            
            <h2><img src="/images/portal/information/h2_tit3.gif" alt="교육자료실" class="fl_l" /><p class="category">Home > 정보광장 > <strong>교육자료실</strong></p></h2>

            <p><img src="/images/portal/information/edu_borimg.gif" alt="교육자료실은 교육분야별로 필요한 자료를 모아놓은 공간입니다." /></p>
            
            <div class="mg_t30 ov_fl">
                <p class="fl_r board_serach pd_tn">
                    <select name="p_search">
                        <option value="title"   <%if("title".equals(v_search)) out.print(" selected");%>>제목</option>
                        <option value="content" <%if("content".equals(v_search)) out.print(" selected");%>>제목+내용</option>
                    </select>
                    <input name="p_searchtext" type="text" class="inbox" style="width:135px;" value="<%=v_searchtext %>" onkeypress="javascript:fnKeyPressEnter(event, 'select');"/>
                    <input type="image" class="btn" src="/images/portal/btn/btn_serbox_search.gif" alt="검색" onclick="javascript:select();"/>
                </p>
            </div>

            <table class="board_list">
            <colgroup><col width="48px" /><col width="75px" /><col width="78px" /><col width="328px" /><col width="62px" /><col width="77px" /><col width="63px" /><col width="44px" /></colgroup>
            <thead>
            <tr class="information">
                <th><p>번호</p></th>
                <th><p>
	                <kocca_select:select                                       
	                name="p_selClsfCd"                                         
	                sqlNum="0003"                                    
	                param="0087" param2="<%= v_selClsfCd %>" param3="1"              
	                onChange="select();"                         
	                attr= "분류"                                        
	                selectedValue="<%=v_selClsfCd%>"                         
	                isLoad="true"                                           
	                type="3"                                                
	                styleClass=""                                
	                all="true" />
                </p></th>
                <th><p>
                    <kocca_select:select                                       
	                name="p_selDtlCd"                                         
	                sqlNum="0004"                                    
	                param="0087" param2="<%= v_selClsfCd %>" param3="2"               
	                onChange="select();"                         
	                attr= "세부분류"                                        
	                selectedValue="<%=v_selDtlCd%>"                         
	                isLoad="true"                                           
	                type="3"                                                
	                styleClass=""                                
	                all="true" />
                </p></th>
                <th><p>제목</p></th>
                <th><p>작성자</p></th>
                <th><p>작성일자</p></th>
                <th><p>첨부파일</p></th>
                <th class="end"><p>조회</p></th>
            </tr>
            </thead>
            <tbody>            
			<%
			if(list2.size() != 0){
			  for( int i = 0; i < list2.size(); i++) {
			
			    DataBox dbox     = (DataBox)list2.get(i);
			    
			    v_gubun           = dbox.getString("d_gubun");  
			    v_seq             = dbox.getInt   ("d_seq");            
			    v_clsfcd          = dbox.getString("d_clsfcd");       
			    v_clsfnm          = dbox.getString("d_clsfnm");
			    v_dtlcd           = dbox.getString("d_dtlcd");        
			    v_dtlnm           = dbox.getString("d_dtlnm");        
			    v_title           = dbox.getString("d_title");        
			    v_professor       = dbox.getString("d_professor");    
			    v_userid          = dbox.getString("d_userid");       
			    v_name            = dbox.getString("d_name");
			    v_useyn           = dbox.getString("d_useyn"); 
			    v_indate          = dbox.getString("d_indate");
			    v_cnt             = dbox.getInt   ("d_cnt");
			    v_fileyn          = dbox.getString("d_fileyn");
			    v_dispnum        = dbox.getInt("d_dispnum");
			    v_totalpage      = dbox.getInt("d_totalpage");
			    v_rowcount       = dbox.getInt("d_rowcount");
			    
			    //if (!v_searchtext.equals("")&&v_search.equals("title")) {
			    if (!v_searchtext.equals("")) {
			        v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
			    } 
			%>
			<tr onmouseover="className='over'" onmouseout="className=' '">
                <td class="num"><%=v_dispnum%></td>
                <td><%=v_clsfnm%></td>
                <td><%=v_dtlnm%></td>
                <td class="ta_l"><a href="javascript:selectView(<%=v_seq%>)"><%=v_title%></a></td>
                <td><%=v_professor%></td>
                <td class="num"><%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></td>
                <td><%if(v_fileyn.equals("Y")){%><img src="/images/portal/ico/ico_file.gif" alt="file"><%} else {%>-<%}%></td>
                <td class="num"><%=v_cnt%></td>
            </tr>
			<%      }
			    }else{%>

	      <tr> 
	        <td colspan ='8'>등록된 내용이 없습니다.</td>
	      </tr>
		  <%  }   %>
            </tbody>
            </table>

        <!-- 페이징 영역 종료 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
        <!-- 페이징 영역 종료 -->
        
        <%} %>
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->