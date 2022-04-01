<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_Letter_R.jsp
//  3. 개      요: 공지사항 상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 7. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");
    
    String  s_userid = box.getSession("userid");
    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");
    String  v_selcomp  = box.getString("p_selcomp");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");

    int v_tabseq     = box.getInt("p_tabseq");
    int v_seq        = box.getInt("p_seq");
    int v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    int    v_cnt       = 0;            // 조회수
    String v_gubun     = "";           // 전체구분
    String v_startdate = "";           // 팝업공지시작일
    String v_enddate   = "";           // 팝업공지종료일
    String v_adtitle   = "";           // 제목
    String v_adcontent = "";           // 내용
    String v_addate    = "";           // 등록일
    String v_adname    = "";           // 등록자
    String v_aduserid  = "";           // userid

    String v_gubun_view = "";
    String v_loginyn    = "";			// 로그인유무
    String v_useyn   	= "";			// 사용유무
    String v_compcd	    = "";           // 대상회사
	int v_popwidth	 = 0;               // 팝업가로
    int v_popheight  = 0;               // 팝업세로
    int v_popxpos    = 0;               // 팝업x위치
    int v_popypos    = 0;               // 팝업y위치
    
    String v_popup   = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe= "";               // 프레임사용
    String v_isall   = "";
    String v_grcodecd = "";
    

    //NoticeData data = (NoticeData)request.getAttribute("selectNotice");
    
    DataBox dbox = (DataBox)request.getAttribute("selectNotice");
    
    if (dbox != null) {
        v_gubun     = dbox.getString("d_gubun");            
        v_startdate = dbox.getString("d_startdate"); 
        v_enddate   = dbox.getString("d_enddate");   
        v_adtitle   = dbox.getString("d_adtitle");  
        v_adcontent = dbox.getString("d_adcontent");
        v_addate    = dbox.getString("d_addate");   
        v_adname    = dbox.getString("d_adname");   
        v_cnt       = dbox.getInt("d_cnt");      
		v_loginyn   = dbox.getString("d_loginyn");   
		v_useyn   	= dbox.getString("d_useyn");     
		v_compcd	= dbox.getString("d_compcd");    
		v_popwidth	= dbox.getInt("d_popwidth");     
		v_popheight = dbox.getInt("d_popheight");    
		v_popxpos   = dbox.getInt("d_popxpos");      
		v_popypos   = dbox.getInt("d_popypos");      
		v_popup     = dbox.getString("d_popup");     
		v_uselist   = dbox.getString("d_uselist");   
		v_useframe  = dbox.getString("d_useframe");  
		v_isall     = dbox.getString("d_isall");  
		v_grcodecd  = dbox.getString("d_grcodecd");  
		v_aduserid  = dbox.getString("d_aduserid");
		
        v_addate     = FormatDate.getFormatDate(v_addate, "yyyy/MM/dd");
        //v_adcontent  = StringManager.replace(data.getAdcontent(),"\n","<br>");
     }  
     
    if (v_gubun.equals("Y")) v_gubun_view = "전체";
    else if (v_gubun.equals("N")) v_gubun_view = "일반";
    else if (v_gubun.equals("P")) v_gubun_view = "팝업";
    
    if (v_uselist.equals("")) v_uselist = "N";
    if (v_useframe.equals("")) v_useframe = "N";
    
    if(v_loginyn.equals("Y")) v_loginyn = "로그인후"; 
    else if(v_loginyn.equals("N")) v_loginyn = "로그인전";
    else v_loginyn = "전체";
    
    
    if(v_useyn.equals("Y")) v_useyn = "사용";
    else if(v_useyn.equals("N")) v_useyn = "사용안함";
     
//대상회사
	int tmplen= 0;
	String compcd="'1'";
	if(!v_compcd.equals("")) {
		compcd=v_compcd.substring(0, v_compcd.length()-1);
		compcd=StringManager.replace(compcd, ",", "','");
		compcd="'"+compcd+"'";
	}
	
	String grcodecd="'1'";
	if(!v_grcodecd.equals("")) {
		grcodecd=v_grcodecd.substring(0, v_grcodecd.length()-1);
		grcodecd=StringManager.replace(grcodecd, ",", "','");
		grcodecd="'"+grcodecd+"'";
	}
	
	//out.println("v_compcd===>>>>"+compcd);
	
	LetterAdminBean bean = new LetterAdminBean();
	ArrayList compList = bean.selectCompany(box, compcd);
	ArrayList grcodeList = bean.selectGrcode(box, grcodecd);
	
	
	String  s_gadmin    = box.getSession("gadmin");
    String v_gadmin ="";
    if(!s_gadmin.equals("")){
      v_gadmin = s_gadmin.substring(0,1);
    }
	
%>

<html>
<head>
<title>eIBIs</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function modify_notice() {
        document.mainForm1.action = "/servlet/controller.homepage.LetterAdminServlet";
        document.mainForm1.p_process.value = "updatePage";
        document.mainForm1.submit();
    }

    function delete_notice() {
        document.mainForm1.p_searchtext.value = "";
        document.mainForm1.p_search.value     = "";
        document.mainForm1.action = "/servlet/controller.homepage.LetterAdminServlet";
        document.mainForm1.p_process.value = "delete";
        document.mainForm1.submit();
    }

    function list_notice() {
        document.mainForm1.action = "/servlet/controller.homepage.LetterAdminServlet";
        document.mainForm1.p_process.value = "select";
        document.mainForm1.submit();
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="mainForm1" method="post">
    <input type="hidden" name="p_process"    value="<%= v_process %>"/>
    <input type="hidden" name="p_pageno"     value="<%=v_pageno %>"/>
    <input type="hidden" name="p_pagesize"   value= "<%=v_pagesize %>"/>
    <input type="hidden" name="p_search"     value="<%=v_search %>"/>
    <input type="hidden" name="p_searchtext" value="<%=v_searchtext %>"/>
    <input type="hidden" name="p_tabseq"     value="<%=v_tabseq %>"/>
    <input type="hidden" name="p_seq"        value="<%=v_seq %>"/>
    <input type="hidden" name="p_selcomp"    value="<%=v_selcomp%>"/>
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
            <td><img src="/images/admin/homepage/tit_news.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

        <br>
        <br>
        <!----------------- 공지사항 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>작성자</strong></td>
            <td height="25" class="table_02_2"><%= v_adname %></td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>등록일</strong></td>
            <td height="25" class="table_02_2"><%= v_addate %></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>전체공지여부</strong></td>
            <td height="25" class="table_02_2"><%= v_isall %></td>
          </tr>
          <%if(!v_gadmin.equals("K")){   //회사관리자가 아닐때%>
          <tr class="table_02_2"> 
            <td height="25" class="table_title" ><strong>로그인유무</strong></td>
            <td height="25" class="table_02_2"><%=v_loginyn%></td>
          </tr>
          <tr class="table_02_2"> 
            <td height="25" class="table_title" ><strong>대상교육그룹</strong></td>
            <td height="25" class="table_02_2">
            <% for(int i=0;i<grcodeList.size();i++){
	          DataBox dbox1   = (DataBox)grcodeList.get(i);
	          out.print(dbox1.getString("d_grcodenm"));
	          %>&nbsp;&nbsp;       
            <%}%>
            </td>
          </tr>
          <%}%>
          <!--tr> 
            <td width="15%" class="table_title"><strong>대상회사여부</strong></td>
            <td height="25" class="table_02_2"><%= v_gubun_view %></td>
          </tr>
          <tr class="table_02_2"> 
            <td height="25" class="table_title" ><strong>대상회사</strong></td>
            <td height="25" class="table_02_2">
            <% for(int i=0;i<compList.size();i++){
	          NoticeData compnm   = (NoticeData)compList.get(i);
	          out.print(compnm.getCompnm());
	          %>&nbsp;&nbsp;	          
            <%}%>
			</td>
          </tr-->
          <tr> 
            <td width="15%" class="table_title"><strong>팝업설정</strong></td>
            <td height="25" class="table_02_2"><%= v_popup %></td>
          </tr>
          <%if(v_popup.equals("Y")){%>
          <tr>
            <td height="25" class="table_title"><b>팝업설정</b></td>
            <td class="table_02_2"><%=FormatDate.getFormatDate(v_startdate,"yyyy/MM/dd")%>  ~<%=FormatDate.getFormatDate(v_enddate,"yyyy/MM/dd")%>
			<br>사이즈 : 가로&nbsp; <%=v_popwidth%> / 세로&nbsp; <%=v_popheight%>
			<br>위치   : x&nbsp;  <%=v_popxpos%> /  y&nbsp; <%=v_popypos%>
			<br>작성내용만보이기 : <%=v_useframe%> 홈페이지리스트사용 <%=v_uselist%>
            </td>
          </tr>
          <%}%>
          <tr> 
            <td width="15%" class="table_title"><strong>사용유무</strong></td>
            <td height="25" class="table_02_2"><%=v_useyn%></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>제목</strong></td>
            <td height="25" class="table_02_2"><%= v_adtitle %></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>내용</strong></td>
            <td height="50" class="table_02_2"><br><%= v_adcontent %><br></td>
          </tr>
		  <tr>
			<td class = "table_title" align = "center">첨부파일</td>
			<td class = "table_02_2"  align = "left" >
                  <%
                  Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                  Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                  %>
                  <%@ include file="/learn/admin/include/za_MultiAttach_R.jsp" %> 
			</td>
		  </tr>
        </table>
        <!----------------- 공지사항 관리 끝 ----------------->

        <br>
        <!----------------- 수정, 삭제, 리스트 버튼 시작 ----------------->
        <table width="16%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
          <%if(v_gadmin.equals("A") || v_gadmin.equals("B") || ( s_userid.equals(v_aduserid) && (v_gadmin.equals("H") || v_gadmin.equals("K"))) ){%>
            <td align="center"><a href="javascript:modify_notice()"><img src="/images/admin/button/btn_modify.gif" border="0"></a>&nbsp;</td>
            <td align="center"><a href="javascript:delete_notice()"><img src="/images/admin/button/btn_del.gif" border="0"></a>&nbsp;</td>
          <%}%>
            <td align="center"><a href="javascript:list_notice()"><img src="/images/admin/button/btn_list.gif" border="0"></a>&nbsp;</td>
          </tr>
        </table>
        <!----------------- 수정, 삭제, 리스트 버튼 끝 ----------------->


      </td>
  </tr>
</table>


<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
