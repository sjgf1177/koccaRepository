<%
//**********************************************************
//  1. 제      목: SUBJ QNA READ
//  2. 프로그램명: zu_SubjQna_R.jsp
//  3. 개      요: 과정 질문 읽기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 8. 15
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
    String  v_process    = box.getString("p_process");  
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");   
    String  v_gadminYn   = box.getString("p_gadminYn");   //관리자여부

    String  v_subj       = box.getString("p_subj");        //과정코드
    String  v_year       = box.getString("p_year");         //년도
    String  v_subjseq    = box.getString("p_subjseq");       //과정 차수
    String  v_lesson     = box.getString("p_lesson");
    String  v_lessonnm   = box.getString("p_lessonnm");
    String  s_userid     = box.getSession("userid");

    String  v_title="",  v_jikwinm="",  v_asgnnm="" ,v_cono="", v_inuserid="",v_inusernm="";
    String  v_indate="", v_contents="", v_replygubun = "", v_replygubun_view = "";
    String  v_isopen="", v_isopen_value="";
    int  v_kind = 0;
    int  v_seq = 0, v_anscnt = 0 ;
    int  v_qcnt     = 0;
    int  v_nanscnt  =0;
    int     i            = 0;
    ArrayList list1      = null;

    list1 = (ArrayList)request.getAttribute("SubjqnaDetail");

%>
<html>
<head>
<title>::: 질문방 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 답변하기
    function reply_qna(seq, kind) {
        document.ff.action = "/servlet/controller.study.SubjQnaStudyServlet";
        document.ff.p_seq.value = seq;
        document.ff.p_kind.value = kind;
        document.ff.p_process.value = "SubjQnaReplyPage";
        document.ff.submit();
    }

    // 수정하기 
    function modify_qna(seq, kind) {
        document.ff.action = "/servlet/controller.study.SubjQnaStudyServlet";
        document.ff.p_seq.value = seq;
        document.ff.p_kind.value = kind;
        document.ff.p_process.value = "SubjQnaUpdatePage";
        document.ff.submit();
    }

    // 삭제
    function delete_qna(seq, kind) {
        if (confirm("정말로 삭제하시겠습니까?")) {
            document.ff.action = "/servlet/controller.study.SubjQnaStudyServlet";
            document.ff.p_seq.value = seq;
            document.ff.p_kind.value = kind;
            document.ff.p_process.value = "SubjQnaDelete";
            document.ff.submit();
        }
        else {
            return;
        }
    }

    function list_qna() {
        document.ff.action = "/servlet/controller.study.SubjQnaStudyServlet";
        document.ff.p_process.value = "SubjQnaList";
        document.ff.submit();
    }

//-->
</SCRIPT>
</head>

<body topmargin="0" leftmargin="0">
<form name="ff" method="post" action="/servlet/controller.study.SubjQnaStudyServlet">
        <input type="hidden" name="p_process"    value="SubjQnaList">
        <input type="hidden" name="p_search"     value = "<%=v_search %>">
        <input type="hidden" name="p_searchtext" value = "<%=v_searchtext %>">
        <input type="hidden" name="p_subj"       value="<%=v_subj%>">
        <input type="hidden" name="p_year"       value="<%=v_year%>">
        <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
        <input type="hidden" name="p_lesson"     value="<%=v_lesson%>">
        <input type="hidden" name="p_seq"        value="<%=v_seq%>">
        <input type="hidden" name="p_job"        value="">
        <input type="hidden" name="p_kind"      value="">
        <input type="hidden" name="p_lessonnm"   value="<%=v_lessonnm%>">
        <input type="hidden" name="p_gadminYn"   value="<%=v_gadminYn%>">

		<br>
            <!----------------- 타이틀 시작 ----------------->
  				<table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
    				<tr> 
      					<td><img src="/images/user/study/inquiry_title.gif"></td>
      					<td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
      					<td><img src="/images/user/study/gongi_tail.gif"></td>
    				</tr>
  				</table>
            <!----------------- 타이틀 끝 ---------------->
<%

            for(i = 0; i < list1.size(); i++) {
                QnaData data = (QnaData)list1.get(i);
                v_seq        = data.getSeq();
                v_kind       = data.getKind();
                v_title      = data.getTitle();
                v_inuserid   = data.getInuserid();
                v_inusernm   = data.getInusernm();
                v_contents   = data.getContents();
                v_indate     = FormatDate.getFormatDate(data.getIndate(),"yyyy/MM/dd");;
                v_replygubun = data.getReplygubun();
                v_seq        = data.getSeq();
                v_isopen     = StringManager.chkNull(data.getIsOpen());
                String v_togubun = data.getTogubun();
                
                //v_contents   = StringManager.replace(StringManager.replace(v_contents,"\r\n","<br>")," ","&nbsp;");
                if(v_isopen.equals("Y")){ v_isopen_value="공개";   }
                else                    { v_isopen_value="비공개"; }
                if (v_replygubun.equals("1"))      v_replygubun_view = "<font color='#0036D9'>■</font>";
                else if (v_replygubun.equals("2")) v_replygubun_view = "<font color='#0036D9'>◆</font>";
                else if (v_replygubun.equals("3")) v_replygubun_view = "<font color='#0036D9'>★</font>";

            if(v_kind == 0 ){    
%>  
            <!----------------- 질문방 내용 시작 ---------------->
          
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
                <tr> 
                    <td colspan="2" height="25" align="left"><b>질문 [ <%if(v_togubun.equals("1")){out.println("운영자에게");}else{out.println("강사에게");}%>]</b></td>
                </tr>
            </table>    
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
              <tr> 
                <td class="board_color_line"></td>
              </tr>
            </table>
            <table cellspacing="0" cellpadding="0" class="open_board_table_out2" align="center">
              <tr> 
                <td> 
                  <table cellspacing="1" cellpadding="3" class="table2">
                    <tr> 
                      <td class="board_title_bg1" width="15%">제 목</td>
                      <td class="board_title_bg3" width="35%"><%= v_title %></td>
                      <td class="board_title_bg1" width="15%">공개여부</td>
                      <td class="board_title_bg3" width="35%"><%= v_isopen_value %></td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg1">등록일</td>
                      <td class="board_title_bg3"><%=v_indate%></td>
                      <td class="board_title_bg1">작성자</td>
                      <td class="board_title_bg3"><%=v_inusernm%></td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg1">첨부파일</td>
                      <td class="board_title_bg3" colspan=3>
                      <%=SubjQnaBean.selectQnaFileList(v_subj, v_year, v_subjseq, v_seq, v_kind)%>
                      </td>
                    </tr>

                    
                    <tr> 
                      <td colspan="4" class="board_text1" align="center"> 
                        <table width="98%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td><%=v_contents%></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>

            <!----------------- 질문방 내용 끝 ---------------->
            <br>
              
            <!----------------- 삭제, 수정, 목록 버튼 시작 ---------------->
            
<table width="230" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 

    <!--<td align="center"><a href="javascript:reply_qna('<%=v_seq%>','0')"><img src="/images/user/button/btn_answer.gif"  border="0"></a></td>-->
<%
    if (s_userid.equals(v_inuserid) ) {
%>
    <td align="center"><a href="javascript:delete_qna('<%=v_seq%>','<%=v_kind%>')" ><img src="/images/user/button/btn_del.gif"  border="0"></a>
    									 <a href="javascript:modify_qna('<%=v_seq%>','<%=v_kind%>')" ><img src="/images/user/button/btn_mod.gif" border="0"></a>
<%
      }
%>
    									 <a href="javascript:list_qna()" ><img src="/images/user/button/btn_list.gif"  border="0"></a></td>

  </tr>
</table>
            <!----------------- 삭제, 수정, 목록 버튼 끝 ----------------->
                        
                        
            <!----------------- 답변 내용 시작 ----------------->


<%          }else{ %> 
            
            <br>
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
                <tr> 
                    <td colspan="2" height="25" align="left"><b>답변 <%=v_kind%></b></td>
                </tr>
            </table>    
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
              <tr> 
                <td class="board_color_line"></td>
              </tr>
            </table>
            <table cellspacing="0" cellpadding="0" class="open_board_table_out2" align="center">
              <tr> 
                <td> 
                  <table cellspacing="1" cellpadding="3" class="table2">
                    <tr> 
                      <td class="board_title_bg1" width="15%">제 목</td>
                      <td class="board_title_bg3" width="85%" colspan='3'><%= v_title %></td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg1" width="15%">등록일</td>
                      <td class="board_title_bg3" width="35%"><%=v_indate%></td>
                      <td class="board_title_bg1" width="15%">작성자</td>
                      <td class="board_title_bg3" width="35%"><%=v_inusernm%>&nbsp;&nbsp;<%=v_replygubun_view%></td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg1">첨부파일</td>
                      <td class="board_title_bg3" colspan=3>
                      <%=SubjQnaBean.selectQnaFileList(v_subj, v_year, v_subjseq, v_seq, v_kind)%>
                      </td>
                    </tr>                    
                    <tr> 
                      <td colspan="4" class="board_text1" align="center"> 
                        <table width="98%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td><%=v_contents%></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>

            <!----------------- 질문방 내용 끝 ---------------->
            <br>
              
            <!----------------- 삭제, 수정, 목록 버튼 시작 ---------------->
            
<table width="210" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 

<%
    if (v_gadminYn.equals("1") || s_userid.equals(v_inuserid) ) {
%>
    <td align="center"><a href="javascript:delete_qna('<%=v_seq%>','<%=v_kind%>')" ><img src="/images/user/button/btn_del.gif"  border="0"></td>
    <td align="center"><a href="javascript:modify_qna('<%=v_seq%>','<%=v_kind%>')" ><img src="/images/user/button/btn_mod.gif" border="0"></a></td>
<%  
    }
%>
    <td align="center"><a href="javascript:list_qna()" ><img src="/images/user/button/btn_list.gif" border="0"></a></td>

  </tr>
</table>
            <!----------------- 삭제, 수정, 목록 버튼 끝 ----------------->
<%          }      %>
            <!-----------------답변 내용  끝 ----------------->
<%          }      %>

            <br>
            

</form>
</body>
</html>
