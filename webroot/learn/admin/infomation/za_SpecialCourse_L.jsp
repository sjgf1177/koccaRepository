<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_Notice_L.jsp
//  3. 개      요: 공지사항 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.system.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box      = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");

    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");
    int     v_totalrowcount =  0;
    
    String  v_selClsfCd   = box.getString("p_selClsfCd");
    String  v_selDtlCd    = box.getString("p_selDtlCd");
    
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");


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
    int    seq               =  0;

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    ArrayList list     = (ArrayList)request.getAttribute("selectList");
    
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.infomation.SpecialCourseAdminServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.infomation.SpecialCourseAdminServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }

    function view(seq) {
        document.form1.action = "/servlet/controller.infomation.SpecialCourseAdminServlet";
        document.form1.p_seq.value = seq;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

    function insert() {
      document.form1.action = "/servlet/controller.infomation.SpecialCourseAdminServlet";
      document.form1.p_process.value = "insertPage";
      document.form1.submit();
  }

    function search() {
        document.form1.action = "/servlet/controller.infomation.SpecialCourseAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

// 조건 검색
    function whenSelection() {
        document.form1.p_selClsfCd.value   = document.form1.p_selClsfCd1.value;
        document.form1.p_selDtlCd.value    = document.form1.p_selDtlCd1.value;
        document.form1.action = "/servlet/controller.infomation.SpecialCourseAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function whenSelection2() {
        document.form1.p_selClsfCd.value   = document.form1.p_selClsfCd2.value;
        document.form1.p_selDtlCd.value    = "";
        document.form1.action = "/servlet/controller.infomation.SpecialCourseAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function pagesize(pageSize) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_pagesize.value = pageSize;
        document.form1.action = '/servlet/controller.infomation.SpecialCourseAdminServlet';
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }
    
 // select box 설정
	$(function() {
		$("#oSelClsfCd2").bind("change", function(){
            var param = "type=sqlID&sqlID=code.list.0004&param=0087," + $(this).val()+",2";
            
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetMiddleClass
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });
		$("#oSelClsfCd1").bind("change", function(){
			whenSelection();
        });
	});
	
	/**
     * 검색조건에서 대분류 선택시 callback function
     */
    function fnSetMiddleClass( result ) {
        $("#oSelClsfCd1").empty();
        $("#oSelClsfCd1").append("<option value=\"\">== 전체 ==</option>");
        alert(result.selectBoxList.length )
        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oSelClsfCd1").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
        document.form1.p_selClsfCd.value   = document.form1.p_selClsfCd2.value;
    }
	

//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "get">
    <input type = "hidden" name = "p_process"    value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_pagesize"   value = "<%=v_pagesize%>">
    <input type = "hidden" name = "p_selClsfCd"   value = "<%=v_selClsfCd%>">
    <input type = "hidden" name = "p_selDtlCd"   value = "<%=v_selDtlCd%>">
    <input type = "hidden" name = "p_seq"        value = "">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
      <td align="center" valign="top">
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr>
            <td><img src="/images/admin/portal/s.1_21.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->
<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
        <br>
        <!----------------- form 시작 ----------------->
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="450">
                                       분류
              <%-- <kocca_select:select name="p_selClsfCd2" sqlNum="0003"  param="0087" param2="<%= v_selClsfCd %>" param3="1"
                onChange="whenSelection2()" attr=" " selectedValue="<%= v_selClsfCd %>" isLoad="true" all="Y" /> --%>
                <kocca:selectBox name="p_selClsfCd2" id="oSelClsfCd2" optionTitle="== 전체 ==" type="sqlID" sqlID="code.list.0003" param="0087,1" selectedValue="<%= v_selClsfCd %>" isLoad="true" />
                                     세부 분류
              <%-- <kocca_select:select name="p_selDtlCd1" sqlNum="0004"  param="0087" param2="<%= v_selClsfCd %>" param3="2"            
                onChange="whenSelection()" attr=" " selectedValue="<%= v_selClsfCd %>" isLoad="true" all="Y" /> --%>
<%
    if ( !v_selClsfCd.equals("") ) {
%>
									<kocca:selectBox name="p_selDtlCd1" id="oSelClsfCd1" optionTitle="== 전체 ==" type="sqlID" sqlID="code.list.0004" param="0087,'<%= v_selClsfCd %>',2" selectedValue="<%= v_selClsfCd %>" isLoad="true" />11111111
									
<%
    } else {
%>
                                    <select name="p_selDtlCd1" id="oSelClsfCd1">
                                    	<option value="">== 전체 ==</option>
                                    </select>
<%
    }
%>
                
            </td>
            <td width="470" align="right">
              <select name="p_search" >
                <option value='title'   <% if (v_search.equals("title")) out.println("selected"); %>>강의명</option>
                <option value='content' <% if (v_search.equals("content")) out.println("selected"); %>>강의내용</option>
              </select>
              <input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>">
            </td>

            <td width="100" align="right" valign="middle"> 
              <a href='javascript:search()'><img src="/images/admin/button/btn_inquiry.gif" border="0"></a>
              <a href='javascript:insert()'><img src="/images/admin/button/btn_add.gif" border="0"></a>
            </td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- form 끝 ----------------->


        <!----------------- 공지사항 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="9" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="5%" height="25"><b>NO</b></td>
            <td class="table_title" width="10%"><b>
            <kocca_select:select                    
                name="p_selClsfCd1"             
                sqlNum="0003"
                param="0087"      
                param2="<%= v_selClsfCd %>"
                param3="1"              
                onChange="whenSelection(this.value);"             
                attr= "분류"                
                selectedValue="<%= v_selClsfCd %>"             
                isLoad="true"               
                type="3"                
                styleClass="table_title"                
                all="true" />   
            </b></td>
            <td class="table_title" width="10%"><b>세부분류</b></td>
            <td class="table_title"><b>강의명</b></td>
            <td class="table_title" width="10%"><b>강사명</b></td>
            <td class="table_title" width="10%"><b>작성자</b></td>
            <td class="table_title" width="10%"><b>등록일</b></td>
            <td class="table_title" width="10%"><b>사용여부</b></td>
            <td class="table_title" width="5%" ><b>조회수</b></td>
          </tr>
          <!-- 전체공지사항 끝 -->

<%

            for(int i = 0; i < list.size(); i++) {

                DataBox dbox = (DataBox)list.get(i);
                
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
                v_useyn          = dbox.getString("d_useyn"); 
                v_indate          = dbox.getString("d_indate");
                v_cnt             = dbox.getInt   ("d_cnt");

                
                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");
                v_totalrowcount = dbox.getInt("d_totalrowcount");
                
                if(v_dtlnm.equals("")){
                	v_dtlnm = "--";
                }

%>
          <tr>
            <td class="table_01"><%= v_dispnum%></td>
            <td class='table_02_1'><%=v_clsfnm%></td>
            <td class='table_02_1'><%=v_dtlnm%></td>
            <td class='table_02_2'><a href="javascript:view(<%=v_seq%>)"><%=v_title%></a></td>
            <td class='table_02_1'><%=v_professor%></td>
            <td class='table_02_1'><%=v_name%></td>
            <td class='table_02_1'><%=FormatDate.getFormatDate(v_indate,"yyyy/MM/dd")%></td>
            <td class='table_02_1'><%=v_useyn%></td>
            <td class='table_02_1'><%=v_cnt%></td>
          </tr>
<%
        }
%>

        </table>
        <!----------------- 공지사항 관리 끝 ----------------->

        <!----------------- total 시작 ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
              <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
            </td>
          </tr>
        </table>
        <!----------------- total 끝 ----------------->

        <br>
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
