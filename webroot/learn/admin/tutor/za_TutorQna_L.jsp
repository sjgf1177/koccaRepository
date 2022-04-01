<%
//**********************************************************
//  1. 제      목: QNA DATA
//  2. 프로그램명: za_Qna_L.jsp
//  3. 개      요: 질문 admin bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 김수진 2003. 8. 18
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process   = box.getString("p_process");
    String  v_title="", v_inuserid="",v_inusernm="";
    String  v_indate="";
    String  v_name ="";
    String  v_replydate="";
    String  v_replyusernm = ""; //최초 응답자 이름
    String  v_replyindate = ""; //최초 응답일
    String  v_replyassgnnm = "";//최초 응답자 소속
    String  v_replyjikwinm = "";//최초 응답자 직위
    int     v_delayday = 0; //지체일
    int     v_seq = 0, v_anscnt = 0 ;
    int     v_qcnt     = 0;
    int     v_nanscnt   =0;
    int     i            = 0;
    ArrayList list1      = null;

    String  v_subj    = box.getString("p_subj");        //과정코드
    String  v_year    = box.getString("p_year");         //년도
    String  v_subjseq = box.getString("p_subjseq");       //과정 차수
    String  v_lesson  = box.getString("p_lesson");
    //DEFINED in relation to select END

    list1 = (ArrayList)request.getAttribute("qnaList");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    function qna_select(seq) {
        document.ff.target = "_self";
        document.ff.p_process.value = 'QnaDetail';
        document.ff.p_seq.value = seq;
        document.ff.submit();
    }

    function goQnaSubjseqListPage() {
        document.ff.p_process.value = 'QnaSubjseqList';
      document.ff.submit();
    }
-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="ff" method="post" action="/servlet/controller.tutor.TutorAdminMainServlet">
        <input type="hidden" name="p_process" value="QnaDetail">
        <input type="hidden" name="p_subj"   value="<%=v_subj%>">
        <input type="hidden" name="p_year"   value="<%=v_year%>">
        <input type="hidden" name="p_subjseq"   value="<%=v_subjseq%>">
        <input type="hidden" name="p_lesson"   value="<%=v_lesson%>">
        <input type="hidden" name="p_seq"   value="">


  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
    <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
          <td><img src="/images/admin/tutor/tit_new_quest.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
            <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 질문방 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="11" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="5%" height="25" class="table_title" width='3%'><b>NO</b></td>
          <td class="table_title"><b>질문제목</b></td>
          <td class="table_title" width='5%'><b>응답</b></td>
          <td class="table_title" width='7%'><b>ID</b></td>
          <td class="table_title" width='7%'><b>성명</b></td>
          <td class="table_title" width='7%'><b>작성일</b></td>
          <td class="table_title" width='7%'><b>지체일</b></td>
          <td class="table_title" width='7%'><b>응답일</b></td>
          <td class="table_title" width='7%'><b>응답자</b></td>
        </tr>
<%
            for(i = 0; i < list1.size(); i++) {
                QnaData data= (QnaData)list1.get(i);
                  v_seq     = data.getSeq();
                  v_title   = data.getTitle();
                  v_inuserid = data.getInuserid();
                  v_name= data.getName();
                  v_indate  = FormatDate.getFormatDate(data.getIndate(),"yyyy/MM/dd");
                  v_replyusernm =  data.getReplyusernm(); //최초 응답자 이름
                  v_replydate =  FormatDate.getFormatDate(data.getReplydate(),"yyyy/MM/dd"); //최초 응답일
                  v_delayday  = data.getDelayday();
%>
        <tr>
          <td class="table_01" height="25"><%= list1.size() - i %></td>
          <td class="table_02_1"><a href="javascript:qna_select(<%=v_seq%>);"><%= v_title %></a></td>
          <td class="table_02_1"><% if(v_replydate.equals("") ){ out.println("N"); } else { out.println("Y"); }%></td>
          <td class="table_02_1"><%= v_inuserid %></td>
          <td class="table_02_1"><%= v_name %></td>
          <td class="table_02_1"><%= v_indate %></td>
          <td class="table_02_1"><%= v_delayday %></td>
          <td class="table_02_1"><%= v_replydate %></td>
          <td class="table_02_1"><%= v_replyusernm %></td>
        </tr>
<%
            }
            if (i == 0) {
%>
            <td class="table_02_1" colspan='11'>등록된 내용이 없습니다.</td>
<%
            }
%>

      </table>
      <!----------------- 질문방 끝 ----------------->
      <br>
    </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
