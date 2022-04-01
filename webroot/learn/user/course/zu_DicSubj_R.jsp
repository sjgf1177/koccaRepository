<%
//**********************************************************
//  1. 제      목: 용어사전(일반)
//  2. 프로그램명 : zu_DicSubj_R.jsp
//  3. 개      요: 용어사전(일반) 상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 8
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process        = box.getString("p_process");

    int     v_seq        = 0;
    String  v_subjcode   = "";
    String  v_subjcodenm = "";
    String  v_words      = "";
    String  v_descs      = "";
    String  v_groups     = "";

    DicData data = (DicData)request.getAttribute("selectDic");
    if (data != null) {
        v_seq        = data.getSeq();
        v_words      = data.getWords();
        v_descs      = data.getDescs();
        v_groups     = data.getGroups();
        v_subjcode   = data.getSubj();
        v_subjcodenm = data.getSubjnm();
        v_descs = StringManager.replace(v_descs,"\n","<br>");
    }

    
%>
<html>
<head>
<title>::: 용어사전 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_course.css" type="text/css">
</head>


<body class="body_color" text="#000000" topmargin="0" leftmargin="0" marginheight="0">
<div align="center">


<table cellspacing="0" cellpadding="0" class="box_board_color_line_out1">
  <tr> 
    <td height="17"></td>
  </tr>
  <tr> 
    <td class="box_board_color_line"></td>
  </tr>
</table>

<table cellspacing="0" cellpadding="0" class="box_board_table_out1">
  <tr> 
    <td> 
        <table cellspacing="1" cellpadding="3" class="table2" height="100%">
          <tr> 
            <td class="layer_board_title_bg2">=&gt;<%=v_words%></td>
        </tr>
        <tr> 
            <td class="board_text3" align="center" valign="top"> <%=v_descs%></td>
        </tr>
      </table>
    </td>
  </tr>
</table>

  <table cellspacing="0" cellpadding="0" class="box_board_color_line_out1">
    <tr>
      <td height="5"></td>
    </tr>
    <tr>
      <td align="right"><a href='javascript:parent.close()'><img src="/images/user/course/close_butt.gif" width="54" height="20" border="0"></a></td>
    </tr>
  </table>
</div>
</body>
</html>