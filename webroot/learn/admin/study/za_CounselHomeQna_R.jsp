<%
//**********************************************************
//  1. 제      목: 개인별 학습현황 홈페이지 Qna 상세보기
//  2. 프로그램명  za_CounselHomeQna_R.jsp
//  3. 개      요: Qna 상세보기화면
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.10.30
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box           = (RequestBox)request.getAttribute("requestbox");
    int v_pageno             = box.getInt("p_pageno");
    int v_seq                = box.getInt("p_seq");
    String  v_categorycd     = box.getStringDefault("p_categorycd","00");


    //out.println("v_categorycd="+v_categorycd);
    String  v_action         = box.getString("p_action");
    String  v_searchtext     = box.getString("p_searchtext");
    int     v_upfilecnt      = box.getInt("p_upfilecnt");          //  서버에 저장되있는 파일수
    String  v_select         = box.getString("p_select");
    String  v_content        = "";

    String  ss_type         = box.getString("p_type");
    if(ss_type.equals("ALL")) {
    	ss_type = box.getString("pp_type");
    }

    DataBox dbox = (DataBox)request.getAttribute("selectQna");
    String v_inuserid   = dbox.getString("d_inuserid");
    String v_name       = dbox.getString("d_name");
    String v_types      = dbox.getString("d_types");
    String v_indate     = dbox.getString("d_indate");
    String v_title      = dbox.getString("d_title");
    v_seq      = dbox.getInt("d_seq");
    String v_contents       = dbox.getString("d_contents");
    Vector v_realfileVector = (Vector)dbox.getObject("d_realfile");
    Vector v_savefileVector = (Vector)dbox.getObject("d_savefile");
    String v_realmotion  = dbox.getString("d_realmotion");
    String v_savemotion  = dbox.getString("d_savemotion");
    String v_grcodenm    = dbox.getString("d_grcodenm");
    String v_okyn1       = dbox.getString("d_okyn1");
    String v_okuserid1   = dbox.getString("d_okuserid1");
    String v_okdate1     = dbox.getString("d_okdate1");
    String v_okyn2       = dbox.getString("d_okyn2");
    String v_okuserid2   = dbox.getString("d_okuserid2");
    String v_okdate2     = dbox.getString("d_okdate2");
    String v_type        = dbox.getString("d_type");
    String v_grcode      = dbox.getString("d_grcode");
    int    v_filecount   = v_realfileVector.size();
    QnaAdminBean hpqbean = new QnaAdminBean();


    //out.println("v_type="+v_type);
    //out.println("v_seq="+v_seq);
%>

<html>
<head>
<title>상담내역</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--

//리스트 페이지로 이동
function listpage() {
    document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
    document.form1.p_process.value = "selectList";
    document.form1.p_action.value = "go";

    document.form1.submit();
}

//답변 페이지로 이동
function replyPage(v_categorycd) {

    document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
    document.form1.p_process.value = "replyPage";

    <% if (ss_type.equals("ALL")) { %>
    document.form1.p_type.value = "<%=v_type%>";
    <% } %>
    document.form1.submit();

}

//뷰 화면 삭제
function deleteAction(seq,type, types) {
    if (confirm("정말로 삭제하시겠습니까?")) {

        document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
        document.form1.p_process.value = "delete";
        document.form1.submit();
    }
    else {
        return;
    }
}


//뷰 화면 삭제
function deleteRepAction(seq,type, types) {
    if (confirm("정말로 삭제하시겠습니까?")) {

        document.form1.p_repseq.value = seq;
        document.form1.p_reptype.value = type;
        document.form1.p_reptypes.value = types;

        document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
        document.form1.p_process.value = "repdelete";
        document.form1.submit();
    }
    else {
        return;
    }
}


//답변수정
function editRepPage(seq, type, types){
  document.form1.p_repseq.value = seq;
  document.form1.p_reptype.value = type;
  document.form1.p_reptypes.value = types;

  document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
  document.form1.p_process.value = "repupdate";
  document.form1.submit();
}

//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" >
<input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
<input type = "hidden" name = "p_type"        value = "<%= ss_type%>">
<input type = "hidden" name = "p_types"       value = "<%=v_types%>">
<input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
<input type = "hidden" name = "p_select"      value = "<%= v_select %>">
<input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
<input type = "hidden" name = "p_process"     value = "">
<input type = "hidden" name = "p_userid"      value = "<%= v_inuserid %>">
<input type = "hidden" name = "p_action"      value = "">
<input type = "hidden" name = "p_grcode"      value = "<%=v_grcode%>">
<input type = "hidden" name = "p_upfilecnt"   value = "<%= v_upfilecnt %>">
<input type = "hidden" name = "s_grcode"      value = "<%=box.getString("s_grcode")%>">

<input type = "hidden" name = "p_repseq"         value = "">
<input type = "hidden" name = "p_reptype"        value = "">
<input type = "hidden" name = "p_reptypes"       value = "">

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="../../../images/admin/study/tit_counsel.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 내용보기 시작 ----------------->
      <table border="0" cellspacing="0" cellpadding="0" width="97%">
        <tr>
          <td align="left"><b>[질문보기]</b></td>
        </tr>
      </table>
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td width="13%" height="25" class="table_title"><b>상담구분 </b></td>
          <td width="35%" class="table_02_2">
<%
                     if(v_type.equals("PQ")){
                        out.print("홈페이지 Q&A");
                     }else if(v_type.equals("OO") || v_type.equals("BU") || v_type.equals("CU") || v_type.equals("MM")){
                         out.print("1vs1상담");
                     }else{
                         out.print("과정질문방");
                     }
%>
          </td>
          <td width="13%" height="25" class="table_title""><b> 날짜</b></td>
          <td class="table_02_2"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
        </tr>
        <!--tr>
          <td width="13%" height="25" class="table_title">교육그룹</td>
          <td class="table_02_2" colspan="3"><%//=v_grcodenm%></td>
        </tr-->
        <tr>
          <td class="table_title" class="table_title">첨부파일 </td>
          <td class="table_02_2" colspan="3">
        <%
                if( v_realfileVector != null ) {
                    for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라

                        String v_realfile = (String)v_realfileVector.elementAt(i);
                        String v_savefile = (String)v_savefileVector.elementAt(i);

                        if(v_realfile != null && !v_realfile.equals("")) {  %>
                            &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>

                            <%= v_realfile%></a><br>
                            <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
                <%      }   %>
        <%          }
                }
        %>
          </td>
        </tr>
        <tr>
          <td class="table_title" class="table_title">작성자 </td>
          <td class="table_02_2" colspan="3"><%= v_name %>(<%= v_inuserid %>)</td>
        </tr>
        <tr>
          <td class="table_title" class="table_title">제목 </td>
          <td class="table_02_2" colspan="3"><%= v_title %></td>
        </tr>
        <tr>
          <td class="table_title" class="table_title">내용 </td>
          <td class="table_02_2" colspan="3"><%=v_contents%></td>
        </tr>
      </table>
      <br>
        <!----------------- 답변내용보기 시작 ----------------->
<%
    ArrayList list = (ArrayList)request.getAttribute("selectQnaListA");
    ArrayList list1 = new ArrayList();
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_cnt = 0;
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1; //v_upfilecnt = 0;
        if(list.size() != 0) {

            for(int i = 0; i < list.size(); i++) {
                DataBox dboxA = (DataBox)list.get(i);
                v_dispnum   = dboxA.getInt("d_dispnum");
                v_inuserid  = dboxA.getString("d_inuserid");
                v_types     = dboxA.getString("d_types");
                v_name      = dboxA.getString("d_name");
                v_cnt       = dboxA.getInt("d_cnt");
                v_indate    = dboxA.getString("d_indate");
                v_title     = dboxA.getString("d_title");
                v_seq       = dboxA.getInt("d_seq");
                v_okyn1     = dboxA.getString("d_okyn1");
                v_okyn2     = dboxA.getString("d_okyn2");
                v_totalpage = dboxA.getInt("d_totalpage");
                v_rowcount  = dboxA.getInt("d_rowcount");
                v_content   = dboxA.getString("d_contents");

%>
      <br/>
      <table border="0" cellspacing="0" cellpadding="0" width="97%">
        <tr>
          <td align="left"><b>[답변보기]</b></td>
        </tr>
      </table>
      <table cellspacing="1" cellpadding="5" class="table_out">
      <tr>
        <td width="13%" height="25" class="table_title"><b> 작성자 </b></td>
        <td width="35%" class="table_02_2" ><%= v_name %>(<%=v_inuserid%>) </td>
        <td width="13%" height="25" class="table_title""><b> 날짜</b></td>
        <td class="table_02_2"><%=FormatDate.getFormatDate(v_indate, "yyyy/MM/dd")%></td>
      </tr>
      <tr>
        <td class="table_title" class="table_title">첨부파일 </td>
        <td class="table_02_2" colspan=3>
<%
       list1 = hpqbean.fileList(dboxA.getInt("d_tabseq"),  v_seq, dboxA.getString("d_types"));
       if(list1 != null){
       for(int ai= 0 ; ai<list1.size(); ai++){
           DataBox dboxf = (DataBox)list1.get(ai);
           String v_realfile = dboxf.getString("d_realfile");
           String v_savefile = dboxf.getString("d_savefile");
                        if(v_realfile != null && !v_realfile.equals("")) {  %>
                            &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%=v_savefile%>&p_realfile=<%=v_realfile%>'>

                            <%= v_realfile%></a><br>
                            <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
                <%      }

       }
            }
%>
        </td>
      </tr>
      <tr>
        <td class="table_title" class="table_title"> 제목</td>
        <td class="table_02_2" colspan="3"><%=v_title%></td>
      </tr>
      <tr>
        <td class="table_title" class="table_title">내용 </td>
        <td class="table_02_2" colspan="3"><%=v_content%></td>
      </tr>
    </table>
    <br>
<%}}%>
    <br>
    <table width="50%" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="30%">&nbsp;</td>
        <td align="center"><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
        <td width="30%">&nbsp;</td>
      </tr>
    </table>
    <br/>
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>