<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 회사별게시판 리스트화면
//  2. 프로그램명: zu_CpCompany_L.jsp
//  3. 개      요: 외주관리 시스템 회사별게시판 리스트화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2004. 12. 18
//  7. 수      정: 이연정 2004. 12. 18
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

int v_seq = 0,v_readcnt = 0,i = 0;
String  v_classname1 = "";
String v_userid = "", v_name = "", v_position = "", v_indate = "", v_title = "", tLink = "", v_realmotion = "", v_savemotion = "";
String v_okyn1 = "", v_okyn2 = "", 	v_okuserid1 = "", v_okuserid2 = "",	v_okdate1 = "",	v_okdate2 = "", v_cpnm="";
String v_kind = "";
int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
ArrayList list = (ArrayList)request.getAttribute("selectPdsList");
   
String v_searchtext = box.getString("p_searchtext");   
String v_select 		= box.getString("p_select");
int    v_pageno 		= box.getInt("p_pageno"); 
String s_userid 		= box.getSession("userid");
String s_usernm 		= box.getSession("name");
String v_cpseq  		= box.getString("p_cpseq");
String s_gadmin 		= box.getSession("gadmin");
String s_cpseq  		= box.getSession("cpseq");

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>

<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript">
<!--
function go(index) {
     document.form1.p_pageno.value = index;
     document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
     document.form1.p_process.value = "selectList";     
     document.form1.submit();
}            
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
     document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
     document.form1.p_process.value = "selectList";     
     document.form1.submit();
}                
function selectList() {
    document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
    document.form1.p_process.value = "selectList";   
    document.form1.p_pageno.value = "1";       
    document.form1.submit();
}            
function insertPage() {
    document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
    document.form1.p_process.value = "insertPage";  
    document.form1.p_pageno.value = "<%= v_pageno %>";    
    document.form1.submit();
}

function select(num, upfilecnt, kind) {
    document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
    document.form1.p_process.value = "select";
    document.form1.p_seq.value = num;
    document.form1.p_upfilecnt.value = upfilecnt;
    document.form1.p_userid.value = "<%=v_userid%>";
    document.form1.p_pageno.value = "<%= v_pageno %>"; 
    document.form1.p_kind.value = kind;
    document.form1.submit();
}

function changeCategory() {
    document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
    document.form1.p_process.value    = "selectList";
    document.form1.submit();
}
//-->
</script>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/cp/cp_title09.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>
		<form name = "form1" method = "post">
		<input type = "hidden" name = "p_process"   value = "">
		<input type = "hidden" name = "p_pageno"    value = "<%=v_pageno %>">
		<input type = "hidden" name = "p_seq"       value = "">
		<input type = "hidden" name = "p_userid"    value = "">
		<input type = "hidden" name = "p_upfilecnt" value = "">
		<input type = "hidden" name = "p_kind"      value = "">
		<input type = "hidden" name = "p_type"        value = "AF">
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>회사별 리스트 조회</td>
		  </tr>
          <tr>
            <td height="8"></td>
          </tr>
        </table>

      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="20%">

			  <%if(s_gadmin.equals("A1") || s_gadmin.equals("A2")){%>
			  <p align="left">
			  <!--관리자모드셀렉트박스시작-->
			  업체 <%=CpCompanyBean.getCompSelecct("p_cpseq",v_cpseq,"onChange=\"javascript:changeCategory()\"",1,s_gadmin,s_userid)%>
			  <!--셀렉트박스끝--></p>
			  <%}%>
		  </td>
          <td width="70%" align="right" valign="middle">
		  
		  <select name="p_select" >
             <option value = "title" <% if (v_select.equals("title")) out.print("selected"); %>>제목</option>
             <option value = "name" <% if (v_select.equals("name")) out.print("selected"); %>>작성자</option>
             <option value = "content" <% if (v_select.equals("content")) out.print("selected"); %>>내용</option>
           </select>
 
		  <input name="p_searchtext" type="text" class="input" maxlength = "30" size="20" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" >
          </td>
          <td align="right" valign="middle"><a href = "javascript:selectList()"><img src="/images/admin/button/search3_butt.gif" border="0" valign="middle">
		  <%  if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) {    //    쓰기 여부 %>
				<a href = "javascript:insertPage()"><img src="/images/admin/button/btn_add.gif" border="0"></a>
		<%  }   %>
		</td>
        </tr>
        <tr>
          <td height=5 colspan="10"></td>
        </tr>
      </table>
        <!----------------- 과정기준조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="13" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="5%"><b>번호</b></td>
			<td class="table_title" width="25%"><b>제목</b></td>
			<td class="table_title" width="10%"><b>회사명</b></td>
            <td class="table_title" width="10%"><b>작성자</b></td>
            <td class="table_title" width="10%"><b>작성일</b></td>
            <td class="table_title" width="5%"><b>조회수</b></td>
            <td class="table_title" width="7%"><b>담당자1</b></td>
            <td class="table_title" width="8%"><b>확인일1</b></td>
            <td class="table_title" width="7%"><b>담당자2</b></td>
            <td class="table_title" width="8%"><b>확인일2</b></td>
            <td class="table_title" width="5%"><b>파일</b></td>
          </tr>
          
			<%if(list.size() != 0){%>
                <%for(i = 0; i < list.size(); i++) {
                    DataBox dbox = (DataBox)list.get(i);  
                    v_dispnum    = dbox.getInt("d_dispnum");
                    v_userid     = dbox.getString("d_userid");
                    v_name       = dbox.getString("d_name");
                    v_indate     = dbox.getString("d_indate");
                    v_title      = dbox.getString("d_title");
                    v_seq        = dbox.getInt("d_seq");
                    v_readcnt    = dbox.getInt("d_cnt");
                    v_upfilecnt   = dbox.getInt("d_filecnt");
                    v_realmotion = dbox.getString("d_realmotion");
                    v_savemotion = dbox.getString("d_savemotion");
                    v_upfilecnt  = dbox.getInt("d_filecnt");
                    v_totalpage  = dbox.getInt("d_totalpage");
                    v_rowcount   = dbox.getInt("d_rowcount"); 
					v_okyn1 	 = dbox.getString("d_okyn1");
					v_okyn2 	 = dbox.getString("d_okyn2");
					v_okuserid1  = dbox.getString("d_okuserid1");
					v_okuserid2  = dbox.getString("d_okuserid2");
					v_okdate1 	 = dbox.getString("d_okdate1");
					v_okdate2 	 = dbox.getString("d_okdate2");
					v_cpnm       = dbox.getString("d_cpnm");
					v_kind       = dbox.getString("d_kind");

					if (!v_searchtext.equals("")&&v_select.equals("title")) {
						v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
					} else if(!v_searchtext.equals("")&&v_select.equals("name")){
						v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
					}

                    %>
			<tr> 
                <td class="table_02_1"><%=v_dispnum%></td>
                <td class="table_02_2"><a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>', '<%=v_kind%>')" target="_self"><%=v_title%></a></td>
                <td class="table_02_1"><%=v_cpnm%></td> 
                <td class="table_02_1"><%=v_name%></td> 
                <td class="table_02_1"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                <td class="table_02_1"><%=v_readcnt%></td>
                <td class="table_02_1"><%=v_okuserid1%></td>
                <td class="table_02_1"><%=FormatDate.getFormatDate(v_okdate1,"yyyy/MM/dd")%></td>
                <td class="table_02_1"><%=v_okuserid2%></td>
                <td class="table_02_1"><%=FormatDate.getFormatDate(v_okdate2,"yyyy/MM/dd")%></td>

                <td class="table_02_1"><% if( v_upfilecnt>0){ %><img src="/images/admin/common/icon_file.gif" width="13" height="12"><%}else{%><%}%>
				</td>				
			</tr>
			<!--내용 끝나는곳-->
             <%}%>
		<%}else{%>
            <tr>
                <td class="table_02_1" colspan="13" align=center >
                    등록 된 내용이 없습니다.        
                </td>
            </tr>
		<%}%>       
        </table>
		</form>				
        <!----------------- total 시작 ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle"> 
			<%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %>
            </td>
          </tr>
        </table>
        <!----------------- total 끝 ----------------->

      </td>
  </tr>

</table>
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' 
ondragstart='return false' onselectstart='return false' 
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
