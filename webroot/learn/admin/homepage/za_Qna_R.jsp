<%
//**********************************************************
//  1. 제      목: 운영자 Qna 리스트
//  2. 프로그램명  za_Qna_R.jsp
//  3. 개      요: Qna 상세보기화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2003. 7. 8 이연정
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
    String  v_searchtext     = box.getString("p_searchtext");      //  서버에 저장되있는 파일수
    String  v_searchStart = box.getString("p_searchStart");
    String  v_searchEnd   = box.getString("p_searchEnd");
    String  v_startdate   = box.getString("p_startdate");
    String  v_enddate     = box.getString("p_enddate");
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
    String v_tabseq     = dbox.getString("d_tabseq");
    v_seq      = dbox.getInt("d_seq");
    String v_contents       = dbox.getString("d_contents").replace("\r", "<BR>");;
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
    String v_repstatus   = dbox.getString("d_okyn1").equals("") ? "1" : dbox.getString("d_okyn1");
    int    v_filecount   = v_realfileVector.size();
    QnaAdminBean hpqbean = new QnaAdminBean();


    //out.println("v_type="+v_type);
    //out.println("v_seq="+v_seq);
%>

<html>
<head>
<title>통합QNA</title>
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

function changeRepStatus(){
	var bRtn   = confirm("답변 여부를 변경하시겠습니까?");
	if (bRtn) {
	    document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
	    document.form1.p_process.value = "updateRepStatus";
	    document.form1.p_action.value = "go";

	    document.form1.submit();
	}
}

//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" >
<input type = "hidden" name = "p_tabseq"      value = "<%= v_tabseq %>">
<input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
<input type = "hidden" name = "p_type"        value = "<%= ss_type%>">
<input type = "hidden" name = "p_types"       value = "<%=v_types%>">
<input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
<input type = "hidden" name = "p_select"      value = "<%= v_select %>">
<input type = "hidden" name = "p_searchStart" value = "<%=v_searchStart%>">
<input type = "hidden" name = "p_searchEnd"   value = "<%=v_searchEnd%>">
<input type = "hidden" name = "p_startdate" value = "<%=v_startdate %>">
<input type = "hidden" name = "p_enddate"   value = "<%=v_enddate %>">
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
            <!----------------- 내용보기 시작 ----------------->
     <table width="97%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
        <td  class=sub_title>Q&A 상세화면보기</td>
        <td align = "right">
            <!--a href="javascript:viewsave()"><img src="/images/admin/button/btn_save.gif" border="0"></a-->
            <a href="javascript:deleteAction('<%=v_seq%>','<%=v_type%>','<%=v_types%>')"><img src="/images/admin/button/btn_del.gif" border="0"></a>
            <a href="javascript:listpage()"><img src="/images/admin/button/btn_list.gif" border="0"></a>
            <a href="javascript:replyPage('<%=v_categorycd%>')"><img src="/images/admin/button/btn_answer.gif" border="0"></a>

        </td>
      </tr>
      <tr>
        <td height="3"></td>
      </tr>
    </table>
    <table cellspacing="1" cellpadding="5" class="table_out">
       <tr>
           <td width="13%" height="25" class="table_title"><b> 구분 </b></td>
           <td width="35%" class="table_02_2">
<%
                     if(v_type.equals("PQ")){
                        out.print("홈페이지");
                     }else if(v_type.equals("MM")){
                         out.print("1:1문의");
                     }else if(v_type.equals("BU")){
                         out.print("버그신고");
                     }else if(v_type.equals("CU")){
                         out.print("운영자에게");
                     }else if(v_type.equals("KB")){
                    	 out.print("지식팩토리");
                     }else{
                         out.print("과정질문방");
                     }
%>
            </td>
           <td width="13%" height="25" class="table_title""><b> 날짜</b></td>
           <td class="table_02_2"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
       </tr>
       <tr>
           <td width="13%" height="25" class="table_title">교육그룹</td>
           <td class="table_02_2"><%=v_grcodenm%></td>
           <td width="13%" height="25" class="table_title">답변여부</td>
           <td class="table_02_2">
             <!--select name="p_repstatus" onchange="changeRepStatus();">
               <option value= "1" <%=v_repstatus.equals("1") ? " selected " : "" %> >열람전</option>
               <option value= "2" <%=v_repstatus.equals("2") ? " selected " : "" %> >답변준비중</option>
               <option value= "3" <%=v_repstatus.equals("3") ? " selected " : "" %> >답변완료</option>
             </select-->
             <%
             if(v_repstatus.equals("1")){
            	 out.print("열람전");
             } else if(v_repstatus.equals("2")){
                 out.print("답변준비중");
             } else if(v_repstatus.equals("3")){
                 out.print("답변완료");
             } 
             %>
           </td>
       </tr>
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
           <td class="table_02_2" colspan="3"><%= v_inuserid %></td>

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


    <table width="97%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
        <td  class=sub_title>Q&A 답변보기</td>
        <td align="right">
            <%if(!(("Y".equals(v_okyn1))&&("Y".equals(v_okyn2)))){%>
            <a href="javascript:editRepPage('<%=v_seq%>','<%=v_type%>', '<%=v_types%>')"><img src="/images/admin/button/btn_modify.gif" border="0"></a>
            <a href="javascript:deleteRepAction('<%=v_seq%>','<%=v_type%>', '<%=v_types%>')"><img src="/images/admin/button/btn_del.gif" border="0"></a>
            <%}%>
        </td>
      </tr>
      <tr>
        <td height="3"></td>
      </tr>
    </table>


    <table cellspacing="1" cellpadding="5" class="table_out">
      <tr>
          <td width="13%" height="25" class="table_title"><b> 작성자 </b></td>
          <td width="35%" class="table_02_2" ><%=v_inuserid%> </td>
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

        <!----------------- 수정, 삭제, 리스트 버튼 끝 ----------------->

        <br>
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>