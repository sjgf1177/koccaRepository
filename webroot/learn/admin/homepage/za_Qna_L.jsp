<%
//**********************************************************
//  1. 제      목: Qna 리스트 조회화면
//  2. 프로그램명:  za_Qna_L.jsp
//  3. 개      요: Qna 리스트 조회화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005. 7. 7 이연정
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

	RequestBox box        = (RequestBox)request.getAttribute("requestbox");
	String  v_process     = box.getString("p_process");
    String  v_type        = box.getStringDefault("p_type", "ALL");

    //out.println(v_type);
    String  v_action      = box.getString("p_action");
    String  s_gadmin      = box.getSession("gadmin");
    String  s_grtype      = box.getSession("grtype");
    String  ss_grcode   = box.getStringDefault("s_grcode","ALL");            //교육그룹
    String  v_gadmin    = StringManager.substring(s_gadmin, 0, 1);
    String  isAll       = !v_gadmin.equals("A") ? "false" : "true";
    String  v_selGroup  = box.getStringDefault("p_selGroup", "ALL");

    int v_pageno = box.getInt("p_pageno");
    if (v_pageno == 0)  v_pageno = 1;

    String startDate = FormatDate.getRelativeDate(-90);
    String endDate = FormatDate.getDate("yyyyMMdd");
    String searchStart   = "";
    String searchEnd     = "";
    String v_subj        ="";
    String v_year        ="";
    String v_subjseq     = "";
    int    v_replystate  = 0;
    String  v_inuserid   = "";
    String  v_name       = "";
    String  v_indate     = "";
    String  v_upfile     = "";
    String  v_title      = "";
    int     v_seq        = 0;
    String  v_types      = "";
    String  v_grcode     = "";
    String  v_repstatus  = "";
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    searchStart = FormatDate.getFormatDate(startDate,"yyyy-MM-dd");
    searchEnd   = FormatDate.getFormatDate(endDate,"yyyy-MM-dd");
//	String  v_searchStart = box.getStringDefault("p_searchStart",searchStart);
    String  v_searchStart = box.getStringDefault("p_searchStart",searchEnd);
    String  v_searchEnd   = box.getStringDefault("p_searchEnd",searchEnd);
    String  v_startdate   = box.getString("p_startdate");
    String  v_enddate     = box.getString("p_enddate");
    String  s_grcode      = box.getStringDefault("p_grcode","ALL");
    String  v_searchtext     = box.getString("p_searchtext");
    String  v_select         = box.getString("p_select");
    String  v_search         = box.getString("p_search");
    String  v_categorycd     = box.getStringDefault("p_categorycd","00");
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    String  v_order    = box.getString("p_order"); //정렬
    int v_cnt = 0;
    String s_userid = box.getSession("userid");
    String s_usernm = box.getSession("name");
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
    ArrayList list = (ArrayList)request.getAttribute("selectList");

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>

<script language="JavaScript">
$(document).ready(function(){
	$("#p_searchStart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_searchEnd").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--

//type값을 넘겨서 리스트 검색
function selectType(v_type){
    whenSelection('go', v_type );
}

//페이지 이동
function insert() { 	
     
     document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
     document.form1.p_process.value = "insertPage";
     document.form1.submit();
}

//검색
function whenSelection(p_action, v_type) {
//	if( document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '' || document.form1.s_grcode.value == '---' ) {
//		alert("교육그룹을 선택하세요.");
//		return;
//	}
	
    document.form1.p_action.value = p_action;
        if (p_action == 'go') {
        top.ftop.setPam();
    }
    document.form1.p_startdate.value = make_date(document.form1.p_searchStart.value);
    document.form1.p_enddate.value = make_date(document.form1.p_searchEnd.value);

	document.form1.p_pageno.value = 1;
    if(document.form1.p_type.value=='SUBJ'){//과정질문방 선택했을때
            document.form1.action='/servlet/controller.homepage.QnaAdminServlet';
            document.form1.p_process.value = 'selectListCourse';
            document.form1.submit();

//ALL검색 포함한 질문리스트를 선택했을때
    }else{
        document.form1.action='/servlet/controller.homepage.QnaAdminServlet';
        document.form1.p_process.value = 'selectList';
//      document.form1.p_type.value= v_type;
        document.form1.submit();
    }
}


//검색
function whenSelection2(p_action) {
//	if( document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '' || document.form1.s_grcode.value == '---' ) {
//		alert("교육그룹을 선택하세요.");
//		return;
//	}
	
    document.form1.p_action.value = p_action;
        if (p_action == 'go') {
        top.ftop.setPam();
    }
    document.form1.p_startdate.value = make_date(document.form1.p_searchStart.value);
    document.form1.p_enddate.value = make_date(document.form1.p_searchEnd.value);

    if(document.form1.p_type.value=='SUBJ'){//과정질문방 선택했을때
            document.form1.action='/servlet/controller.homepage.QnaAdminServlet';
            document.form1.p_process.value = 'selectListCourse';
            document.form1.submit();

//ALL검색 포함한 질문리스트를 선택했을때
    }else{
        document.form1.action='/servlet/controller.homepage.QnaAdminServlet';
        document.form1.p_process.value = 'selectList';
        document.form1.submit();
    }
}

//페이지 이동
function go(index) {
  document.form1.p_action.value = "go";
    document.form1.p_pageno.value = index;
    document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}
//페이지 이동
function goPage(pageNum) {
  document.form1.p_action.value = "go";
     document.form1.p_pageno.value = pageNum;
     document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
     document.form1.p_process.value = "selectList";
     document.form1.submit();
}

//상세화면 페이지로 이동
function select(seq, upfilecnt, userid, types, type, ctcd) {
    document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
    document.form1.p_process.value = "selectView";
    document.form1.p_seq.value = seq;
    document.form1.p_userid.value    = userid;
    document.form1.p_types.value    = types;
    document.form1.pp_type.value    = type;
    document.form1.p_categorycd.value    = ctcd;
    document.form1.p_upfilecnt.value = upfilecnt;

    document.form1.submit();
}

function exceldown()
{
    document.form1.p_action.value = "go";
    document.form1.p_startdate.value = make_date(document.form1.p_searchStart.value);
    document.form1.p_enddate.value = make_date(document.form1.p_searchEnd.value);
	document.form1.p_pageno.value = 1;
    document.form1.action='/servlet/controller.homepage.QnaAdminServlet';
    document.form1.p_process.value = 'excelList';
    document.form1.submit();
}

//상세화면 과정질문방 페이지로 이동
function selectCourse(seq,subj,year,subjseq) {
    document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
    document.form1.p_process.value = "selectViewCourse";
    document.form1.p_seq.value     = seq;
    document.form1.p_subj.value    = subj;
    document.form1.p_year.value    = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.submit();
}

// 정렬
function ordering(column) {
    if (document.form1.p_orderType.value == " asc") {
        document.form1.p_orderType.value = " desc";
    } else {
        document.form1.p_orderType.value = " asc";
    }
    document.form1.p_order.value = column;
    whenSelection("go");
}
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function community_go(){
	window.open("/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage","커뮤니티");
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
          <td><img src="/images/admin/homepage/homepage_title12.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>

        <!------------------------------------- form 시작 ------------------------------------->
        <form name  = "form1" method = "post">
        <input type = "hidden" name = "p_process"   value = "<%= v_process %>">
        <input type ="hidden"   name = "p_orderType" value = "<%=v_orderType%>">
        <input type = "hidden" name = "p_pageno"    value = "<%=v_pageno%>">
        <input type = "hidden" name = "p_seq"       value = "">
        <input type = "hidden" name = "p_userid"    value = "">
        <input type = "hidden" name = "p_types"     value = "">
        <input type = "hidden" name = "p_action"    value = "">
        <input type = "hidden" name = "p_startdate" value = "<%=v_startdate %>">
        <input type = "hidden" name = "p_enddate"   value = "<%=v_enddate %>">
        <input type = "hidden" name = "pp_type"     value = "">
        <input type = "hidden" name = "p_grcodenm"  value = "">
        <input type = "hidden" name = "p_upfilecnt" value = "">
        <input type = "hidden" name = "p_subj">
        <input type = "hidden" name = "p_year">
        <input type = "hidden" name = "p_subjseq">
    <input type = "hidden" name = "p_order"  value="<%=v_order%>">
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
              <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
<!-- 교육그룹 --->
                    <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                        <tr>
                          <td width="*" align="left"  valign="middle">
                            교육그룹 <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- 전체 --" optionTitleValue="ALL" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= ss_grcode %>" isLoad="true" />                                                                  
                            <!-- 교육그룹 종료 -->    
                          </td>
                          <td width="59%" >
                            <select name = "p_select" class = "input">
                                <option value="name" <% if (v_select.equals("name")) out.print("selected"); %>>작성자</option>
                                <option value="title" <% if (v_select.equals("title")) out.print("selected"); %>>제목</option>
                                <option value = "content" <% if (v_select.equals("content")) out.print("selected"); %>>내용</option>
                            </select>
                            <input type="text" class="input" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_searchtext" value = "<%=v_searchtext %>" size = "25" maxlength = "30">
                          </td>
                          <td width="10%" align="right"><a href = "javascript:whenSelection('go','ALL')"><img src = "/images/admin/button/b_go.gif" border = "0" align="absmiddle"></a></td>
                        </tr>
                      </table>
<!-- 교육그룹 --->
                </td>
              </tr>
              <tr>
                <td height="9"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">

            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
<!-- 구분 -->
                 <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td width="60%" colspan="2">
                        구분
                        <select name = "p_type" class = "input" onchange="whenSelection2('go')">
                            <option value="ALL" <%if(v_type.equals("ALL")){out.println("selected");}%> >ALL</option>
                            <option value="KB" <%if(v_type.equals("KB")){out.println("selected");}%>>지식팩토리</option>
                            <option value="PQ" <%if(v_type.equals("PQ")){out.println("selected");}%>>홈페이지</option>
                            <option value="MM" <%if(v_type.equals("MM")){out.println("selected");}%>>1:1문의</option>
                            <option value="SUBJ"  <%if(v_type.equals("SUBJ")){out.println("selected");}%>>과정질문방</option>
                            <!-- 
<%if( s_grtype.equals("KGDI") ){%>
                            <option value="PQ" <%if(v_type.equals("PQ")){out.println("selected");}%>>홈페이지</option>
                            <option value="BU" <%if(v_type.equals("BU")){out.println("selected");}%>>버그신고</option>
                            <option value="CU" <%if(v_type.equals("CU")){out.println("selected");}%>>운영자에게</option>
                            <option value="MM" <%if(v_type.equals("MM")){out.println("selected");}%>>1:1문의</option>
<%}else if( s_grtype.equals("KOCCA") ){%>
                            <option value="OO" <%if(v_type.equals("OO")){out.println("selected");}%>>1vs1상담</option>
                            <option value="MM" <%if(v_type.equals("MM")){out.println("selected");}%>>1:1문의</option>
<%}else{%>
                            <option value="PQ" <%if(v_type.equals("PQ")){out.println("selected");}%>>홈페이지</option>
                            <option value="BU" <%if(v_type.equals("BU")){out.println("selected");}%>>버그신고</option>
                            <option value="CU" <%if(v_type.equals("CU")){out.println("selected");}%>>운영자에게</option>
                            <option value="OO" <%if(v_type.equals("OO")){out.println("selected");}%>>1vs1상담</option>
                            <option value="MM" <%if(v_type.equals("MM")){out.println("selected");}%>>1:1문의</option>
<%}%>
                            <option value="SUBJ"  <%if(v_type.equals("SUBJ")){out.println("selected");}%>>과정질문방</option>
                             -->
                        </select>
                        
<% if(v_type.equals("PQ")) {%>   
                         분류 <kocca_select:select name="p_categorycd" sqlNum="0001"  param="0088"
                    onChange="whenSelection2('go')" attr=" " selectedValue="<%= v_categorycd %>" isLoad="true" all="Y" />
<% } else { %>
		<input type = "hidden" name = "p_categorycd">
<% } %>                     
                      </td>
                      <td width="40%" colspan="2">조회기간
                        <input name="p_searchStart" id="p_searchStart" type="text" class="datepicker_input1" size="10" value="<%=v_searchStart%>"> 일 ~
                        <input name="p_searchEnd" id="p_searchEnd" type="text" class="datepicker_input1" size="10" value="<%=v_searchEnd%>"> 일
                      </td>
                    </tr>
                 </table>
<!-- 구분 -->
                </td>
              </tr>
              <tr>
                <td height="9"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
     <br>

        </form>
        <!----------------- 모두승인, 모두승인취소, 확인 버튼 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td width="80px">Q&A 조회</td>
                        <td align="right">
                        <img src="/images/admin/button/btn_community_go.gif" alt="커뮤니티바로가기" style="CURSOR:pointer;" onclick="community_go();"/>
                        <img src="/images/admin/button/btn_write.gif" alt="쓰기" style="CURSOR:POINTER" onclick="insert()">
                        <img src="/images/admin/button/btn_excelprint.gif" alt="엑셀받기" style="CURSOR:POINTER" onclick="exceldown()">
                    </tr>
                </table>
            </td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 모두승인, 모두승인취소, 확인 버튼 끝 ----------------->

        <!----------------- 과정기준조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="13" class="table_top_line"></td>
          </tr>
          <FORM name="ff" method="post" >
            <input type="hidden" name="p_process"    value="">
          <tr>
            <td class="table_title" width="5%"><b>NO</b></td>
            <td class="table_title" width="10%"><b><a href="javascript:ordering('type')" class="e">구분</a></b></td>
            <td class="table_title" width="15%"><a href="javascript:ordering('title')" class="e">제목</a></td>
            <td class="table_title" width="10%"><b><a href="javascript:ordering('name')" class="e">성명</a></b></td>
            <td class="table_title" width="7%"><b><a href="javascript:ordering('indate')" class="e">등록일</a></b></td>
            <td class="table_title" width="5%"><b><a href="javascript:ordering('upfile')" class="e">첨부파일</a></b></td>
            <td class="table_title" width="5%"><b><a href="javascript:ordering('okyn1')" class="e">답변여부</a></b></td>
          </tr>

<%if(v_action.equals("go") && list.size() > 0){%>
<%
            for(int i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);
                v_dispnum       = dbox.getInt("d_dispnum");
                v_inuserid      = dbox.getString("d_inuserid");
                v_name          = dbox.getString("d_name");
                v_cnt           = dbox.getInt("d_cnt");
                v_indate        = dbox.getString("d_indate");
                v_upfile        = dbox.getString("d_upfile");
                v_title         = dbox.getString("d_title");
                v_seq           = dbox.getInt("d_seq");
                v_types         = dbox.getString("d_types");
                v_categorycd    = dbox.getString("d_categorycd");
                v_grcode        = dbox.getString("d_grcode");
                v_replystate    = Integer.parseInt(dbox.getString("d_replystate"));
                v_upfilecnt     = dbox.getInt("d_filecnt");

                v_totalpage     = dbox.getInt("d_totalpage");
                v_rowcount      = dbox.getInt("d_rowcount");
                v_type          = dbox.getString("d_type");
                v_subj          = dbox.getString("d_subj");
                v_year          = dbox.getString("d_year");
                v_subjseq       = dbox.getString("d_subjseq");
                
                v_repstatus     = dbox.getString("d_okyn1").equals("") ? "1" : dbox.getString("d_okyn1");
                
                if (v_repstatus.equals("1")){
                	v_repstatus    = "열람전";
                } else if (v_repstatus.equals("2")){
                    v_repstatus    = "답변준비중";
                } else if (v_repstatus.equals("3")){
                    v_repstatus    = "답변완료";
                } else if (v_repstatus.equals("4")){
                    v_repstatus    = "게시글";
                } 

                // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
                if (!v_searchtext.equals("")&&v_select.equals("title")) {
                    v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
                } else if(!v_searchtext.equals("")&&v_select.equals("name")){
                    v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
                }
%>
             <tr class="table_02_1">
                <td class="table_02_1"><%=v_dispnum%></td>
                <td class="table_02_1">
<%
                     if(v_type.equals("PQ")){
                        out.print("홈페이지");
                     }else if(v_type.equals("OO")){
                         out.print("1vs1 상담");
                     }else if(v_type.equals("BU")){
                         out.print("버그신고");
                     }else if(v_type.equals("CU")){
                         out.print("운영자에게");
                     }else if(v_type.equals("MM")){
                         out.print("1:1문의");
                     }else if(v_type.equals("KB")){
                         out.print("지식팩토리");                         
                     }else{
                         out.print("과정질문방");
                     }
%>
                 <input type = "hidden" name = "pp_type" value="<%=v_type%>">
                </td>
                <td class="table_02_2">
                <%if(v_type.equals("")){%>
                   <font class="text_color04"><a href="javascript:selectCourse('<%=v_seq%>','<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>')"><%=v_title%></font></a>
                <%}else{%>
                    <font class="text_color04"><a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_inuserid%>','<%=v_types%>','<%=v_type%>','<%=v_categorycd%>')"><%=v_title%></font></a>
                <%}%>
                </td>
                <td class="table_02_1"><%=v_name%></td>
                <td class="table_02_1"><%=FormatDate.getFormatDate(v_indate,"yyyy/MM/dd")%></td>
                <td class="table_02_1">
                    <%if(v_upfilecnt>0){%>
                        <img src="/images/user/support/ico_file.gif" width="13" height="12">
                    <%}else{
                     %>
                    <%}%>
                </td>
                <td class="table_02_1">
                  <%=v_repstatus %>
                </td>
            </tr>
                      <%}%>
                    <%}else{%>

                    <tr>
                      <td colspan='15' class="table_02_1">등록 된 질문이 없습니다.</td>
                    </tr>
                    <%}%>

            </FORM>
        </table>

        <!----------------- total 시작 ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
              <%= PageUtil.printPageList(v_totalpage, v_pageno, row) %>
            </td>
          </tr>
        </table>
        <!----------------- total 끝 ----------------->

      </td>
  </tr>

</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
