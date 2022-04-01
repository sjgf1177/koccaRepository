<%
//**********************************************************
//  1. 제      목: 과정자료실
//  2. 프로그램명: za_StudyData_R.jsp
//  3. 개      요: 과정자료실 보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_subjnm = box.getString("p_subjnm");
    int v_tabseq    = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    int     v_orgseq     = v_seq;
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    int v_levels = 0;
    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;

    int    i         = 0;
    int    v_cnt     = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_title   = "";
    String v_content = "";
    
    String v_class1     = "";
    String v_class2     = "";
        
	Vector v_realfileVector = null;
    Vector v_savefileVector = null;

	DataBox dbox = (DataBox)request.getAttribute("selectStudyData");

    if (dbox != null ) {
        v_seq           = dbox.getInt("d_seq");
        v_userid        = dbox.getString("d_userid");
        v_name          = dbox.getString("d_name");
        v_title         = dbox.getString("d_title");
        v_content       = dbox.getString("d_content");
        v_cnt           = dbox.getInt("d_cnt");
        v_indate        = dbox.getString("d_indate");
        v_realfileVector = (Vector)dbox.getObject("d_realfile");
        v_savefileVector = (Vector)dbox.getObject("d_savefile");
        //v_content = StringManager.replace(v_content,"\r\n","<br>");


        // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
        if (!v_searchtext.equals("")) {
            if (v_search.equals("name")) {
                v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            } else if (v_search.equals("title")) {
                v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }  else if (v_search.equals("content")) {
                v_content = StringManager.replace(v_content, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }
        }
    }

    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");

    // 리스트
    ArrayList list = (ArrayList)request.getAttribute("selectStudyDataList");
    
    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");        //교육그룹
    String  ss_gyear     = box.getString("s_gyear");         //년도
    String  ss_grseq     = box.getString("s_grseq");         //교육차수
    String  ss_upperclass    = box.getString("s_upperclass");        //과정분류
	String  ss_middleclass    = box.getString("s_middleclass");        //과정분류
	String  ss_lowerclass    = box.getString("s_lowerclass");        //과정분류
    String  ss_subjcourse= box.getString("s_subjcourse");    //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");       //과정 차수
    String  ss_action    = box.getString("s_action");   
    //DEFINED in relation to select END        

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 전체목록
	function goListPage() {
        document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
        document.form1.p_process.value = "adminList";
        document.form1.s_action.value = "go";
        document.form1.submit();
	}
	
	
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
         document.form1.p_process.value = "list";
         document.form1.submit();
    }
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
         document.form1.p_process.value = "list";
         document.form1.submit();
    }
    function reply_StudyData() {
        document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
        document.form1.p_process.value = "replyPage";
        document.form1.submit();
    }

    function modify_StudyData() {
        document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }

    function delete_StudyData() {
        if (confirm("정말로 삭제하시겠습니까?")) {
            document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
            document.form1.p_process.value = "delete";
            document.form1.p_pageno.value = "1";
            document.form1.submit();
        }
        else {
            return;
        }
    }

    function list_StudyData() {
        document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
        document.form1.p_process.value = "list";
        document.form1.submit();
    }


    function selectList() {
        document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
        document.form1.p_process.value = "list";
        document.form1.p_pageno.value = "1";
        document.form1.submit();
    }

    function select(seq, upfilecnt, userid) {
        document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
        document.form1.p_process.value = "select";
        document.form1.p_seq.value = seq;
        document.form1.p_upfilecnt.value = upfilecnt;
        document.form1.p_userid.value    = userid;
        document.form1.submit();
    }
    function insertPage() {
        document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }    
//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type="hidden" name="s_action"    value="<%=ss_action%>">     <!--in relation to select-->
    <input type="hidden" name="s_grcode"    value="<%=ss_grcode%>">     <!--in relation to select-->
    <input type="hidden" name="s_gyear"     value="<%=ss_gyear%>">      <!--in relation to select-->
    <input type="hidden" name="s_grseq"     value="<%=ss_grseq%>">      <!--in relation to select-->
    <input type="hidden" name="s_upperclass"    value="<%=ss_upperclass%>">     <!--in relation to select-->
	<input type="hidden" name="s_middleclass"    value="<%=ss_middleclass%>">    
	<input type="hidden" name="s_lowerclass"    value="<%=ss_lowerclass%>">    
    <input type="hidden" name="s_subjcourse"value="<%=ss_subjcourse%>"> <!--in relation to select-->
    <input type="hidden" name="s_subjseq"   value="<%=ss_subjseq%>">    <!--in relation to select-->   
    <input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
    <input type = "hidden" name = "p_subjnm"    value = "<%=v_subjnm %>">
    <input type = "hidden" name = "p_process"    value = "<%=v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_seq"        value = "<%= v_seq %>">
    <input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>">
    <input type = "hidden" name = "p_userid"     value = "<%= v_userid %>">



  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title09.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>       
      <!----------------- title 끝 ----------------->
        <br>
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
		    <td width="1%"><img src="/images/admin/common/icon.gif" ></td> 
            <td><%=v_subjnm%>과정</td>
          </tr>
		  <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
        </table>
        <!----------------- 자료등록 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="0">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="20%" height="25" class="table_title"><b>작성자</b></td>
            <td class="table_02_2" ><%=v_name%></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>제목</b></td>
            <td class="table_02_2"><%= v_title %></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>등록일</b></td>
            <td class="table_02_2"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
          </tr>       
          <tr> 
            <td class="table_title" height="25"><b>조회수</b></td>
            <td class="table_02_2"><%=v_cnt%></td>
          </tr>                  
          <tr> 
            <td class="table_title" height="25"><b>내용</b></td>
            <td class="table_02_2"><%= v_content %></td>
          </tr>

          <tr> 
            <td height="25" class="table_title"><b>첨부파일</b></td>
            <td class="table_02_2">
	              <%
                  Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                  Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                  %>
                  <%@ include file="/learn/admin/include/za_MultiAttach_R.jsp" %> 
			</td>
		  </tr>
        </table>
        <!----------------- 자료등록 끝 ----------------->
        <br>

        <!----------------- 등록, 취소 버튼 시작 ----------------->
        <table border="0" cellspacing="0" cellpadding="0">
          <tr> 
<%  if(BulletinManager.isAuthority(box, box.getString("p_canModify"))) {    //    수정 여부 %>             
            <td>
            <a href="javascript:modify_StudyData()"><img src="/images/admin/button/btn_modify.gif"  border="0"></a>
            </td>
<%  
    }

    if(BulletinManager.isAuthority(box, box.getString("p_canDelete"))) {    //    삭제 여부 
%>            
            <td width=8></td>
			<td>
            <a href="javascript:delete_StudyData()"><img src="/images/admin/button/btn_del.gif" border="0"></a>
            </td>
<%  }  %>               
          </tr>
        </table>
        <!----------------- 등록, 취소 버튼 끝 ----------------->






        <br>
        <!----------------- 리스트, 추가 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr>                       
            <td align="right" width="100%">
                <%  if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) {    //    쓰기 여부 %>
                    <a href = "javascript:insertPage();"><img src="/images/admin/button/btn_add.gif" border="0"></a>
                <%  }   %>            
                <a href="javascript:goListPage();"><img src="/images/admin/button/btn_list.gif" border="0"></a>
            </td>
          </tr>
          <tr> 
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 리스트, 추가 버튼 끝 ----------------->





        <!----------------- 과정 자료실  리스트 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="6" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="5%" height="25" class="table_title"><b>NO</b></td>
            <td class="table_title"><b>제목</b></td>
            <td  width="10%" class="table_title"><b>첨부파일</b></td>            
            <td class="table_title"><b>등록일자</b></td>
            <td class="table_title"><b>작성자</b></td>
            <td class="table_title"><b>조회수</b></td>
          </tr>
<%
    for(i = 0; i < list.size(); i++) {
		DataBox dbox2 = (DataBox)list.get(i); 
		v_userid    = dbox2.getString("d_userid");
		v_name      = dbox2.getString("d_name");
		v_indate    = dbox2.getString("d_indate");
		v_title     = dbox2.getString("d_title");
		v_seq       = dbox2.getInt("d_seq");
		v_cnt       = dbox2.getInt("d_cnt");
		v_levels    = dbox2.getInt("d_levels");
		v_upfilecnt = dbox2.getInt("d_upfilecnt");
		v_dispnum   = dbox2.getInt("d_dispnum");
		v_totalpage = dbox2.getInt("d_totalpagecount");
		v_rowcount  = dbox2.getInt("d_rowcount");

        // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
        if (!v_searchtext.equals("")) {
            if (v_search.equals("name")) {
                v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            } else if (v_search.equals("title")) {
                v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }
        }
        if(i%2 == 0){   
            v_class1 = "board_text1";
            v_class2 = "board_text3";
        }else{
            v_class1 = "board_text2";
            v_class2 = "board_text4";
        }          
%>          


          <tr> 
            <td align="center" bgcolor="#EEEEEE" height="25"><%=v_dispnum%></td>
            <td class="table_02_2"><a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_userid%>')"><%=v_title%></a>
              <% if(FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){ %>
                 &nbsp;&nbsp;<img src = "/images/user/support/ico_new.gif" align = "absmiddle" border = "0">
              <%  } %>            
            </td>
            <td class="table_02_1" align="center">
            <%if(v_upfilecnt>0){%>
            <img src='/images/user/support/ico_file.gif' border=0>
            <%}%>
            </td>            
            <td class="table_02_1" align="center"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
            <td class="table_02_1" align="center"><%= v_name %></td>
            <td class="table_02_1" align="center"><%=v_cnt%></td>
          </tr>
<%  } if(i == 0){   %>    
                    <tr> 
                      <td class="table_02_1" align="center" colspan="6">등록된 내용이 없습니다.</td>
                    </tr>    
<%    } %>
        </table>
        <!----------------- 과정 자료실  리스트 끝 ----------------->
        <br>





        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center" class="ms"><%= PageUtil.printPageList(v_totalpage, v_pageno, row) %></td>
          </tr>
          <tr>
            <td height="15"></td>
          </tr>
          <tr>
            <td align="center" class="ms">




              <!----------------- 검색 시작 ----------------->
              <table width="26%" height="26" border="0" cellpadding="0" cellspacing="0">
                <tr> 
                  <td align="center"> 
                    <select name="p_search">
                            <option value='name' <% if (v_search.equals("name")) out.print("selected"); %>>작성자</option>
                            <option value='title' <% if (v_search.equals("title")) out.print("selected"); %>>제목</option>
                            <option value='content' <% if (v_search.equals("content")) out.print("selected"); %>>내용</option>
                    </select>
                     <input size="18" name="p_searchtext" class="input" value='<%=v_searchtext %>'>
                  </td>
                  <td align="center">
                  <a href = "javascript:selectList();"><img src="/images/admin/button/search3_butt.gif" border="0"></a></td>
                </tr>
              </table>
              <!----------------- 검색 끝 ----------------->






      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
