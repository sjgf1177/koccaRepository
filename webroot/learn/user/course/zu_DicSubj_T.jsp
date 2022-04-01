<%
//**********************************************************
//  1. 제      목: 용어사전(일반)
//  2. 프로그램명 : zu_DicSubj_T.jsp
//  3. 개      요: 용어사전(일반) 프레임탑
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 8
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process       = box.getString("p_process");
    String  v_searchtext    = box.getString("p_searchtext");
    String  v_group         = box.getString("p_group");

    String upperclass = box.getString("upperclass");
    String subj       = box.getString("subj");


    String  v_search         = "";
    if (v_searchtext.equals(""))  v_search = v_group;
    else                          v_search = v_searchtext;
%>
<html>
<head>
<title>::: 용어사전 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_course.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    function goGroup(group) {
        window.self.name = "winSelect";
        document.form1.p_upperclass.value = document.form1.upperclass.value;
        document.form1.p_subj.value       = document.form1.subj.value;
        document.form1.p_group.value      = group;
        document.form1.p_searchtext.value = "";

        if (document.form1.p_subj.value == '' || document.form1.p_subj.value == 'ALL'){
          alert("과정를 먼저 선택하시고 조회하세요");
          return;
        }

        document.form1.target = "leftFrame"
        document.form1.p_process.value   = "selectList";
        document.form1.action            = "/servlet/controller.course.DicSubjServlet";
        document.form1.submit();

        document.form1.target = "mainFrame"
        document.form1.p_process.value   = "selectViewPre";
        document.form1.action            = "/servlet/controller.course.DicSubjServlet";
        document.form1.submit();

        document.form1.target = "_self"
        document.form1.p_process.value   = "selectTop";
        document.form1.action            = "/servlet/controller.course.DicSubjServlet";
        document.form1.submit();

        document.form1.target = window.self.name;

    }

    function goSearch() {
        window.self.name = "winSelect";
        document.form1.p_upperclass.value = document.form1.upperclass.value;
        document.form1.p_subj.value       = document.form1.subj.value;


        if (document.form1.p_subj.value == '' || document.form1.p_subj.value == 'ALL'){
          alert("과정를 먼저 선택하시고 조회하세요");
          return;
        }

        document.form1.target = "leftFrame"
        document.form1.p_process.value   = "selectList";
        document.form1.action            = "/servlet/controller.course.DicSubjServlet";
        document.form1.submit();

        document.form1.target = "mainFrame"
        document.form1.p_process.value   = "selectViewPre";
        document.form1.action            = "/servlet/controller.course.DicSubjServlet";
        document.form1.submit();

        document.form1.target = "_self"
        document.form1.p_process.value   = "selectTop";
        document.form1.action            = "/servlet/controller.course.DicSubjServlet";
        document.form1.submit();

        document.form1.target = window.self.name;
    }


    function changeUpperclass() {
         document.form1.action = "/servlet/controller.course.DicSubjServlet";
         document.form1.p_process.value = "selectTop";
         document.form1.subj.value = 'ALL';
         document.form1.submit();
    }
    function changeSubj() {
    }

    function dicComp() {
        document.form1.target = "_parent";
        document.form1.action = "/servlet/controller.course.DicCompServlet";
        document.form1.p_process.value = "selectFrame";
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>

<body class="body_color" text="#000000" topmargin="0" leftmargin="0" marginheight="0">
<form name ='form1' method='post' action='javascript:goSearch();'>
    <input type='hidden' name = 'p_process'    value = ''>
    <input type='hidden' name = 'p_upperclass' value = ''>
    <input type='hidden' name = 'p_subj'       value = ''>
    <input type='hidden' name = 'p_group'      value = ''>

<div align="center">

  <table cellspacing="0" cellpadding="0" class="table2">
    <tr>
      <td height="8"></td>
    </tr>
  </table>

  <!----------------- 타이틀 시작 ----------------->
  <table cellspacing="0" cellpadding="0" class="b_title_table">
    <tr> 
      <td><img src="/images/user/course/community_title_b1.gif" border="0"></td>
    </tr>
  </table>
  <!----------------- 타이틀 끝 ----------------->

  <br>
  <table cellspacing="0" cellpadding="0" class="s_title_table">
    <tr> 
      <td width="118"><a href="javascript:dicComp()" target="_self"><img src="/images/user/course/dictionary_s_menu1_2.gif" width="118" height="23" border="0"></a></td>
      <td width="116"><img src="/images/user/course/dictionary_s_menu2_1.gif" width="116" height="23" border="0"></td>
      <td align="right">&nbsp;</td>
    </tr>
  </table>

  <table cellspacing="0" cellpadding="0" width="94%" border="0">
    <tr> 
      <td height="6" background="/images/user/community/community_bar2.gif"><img src="/images/user/community/community_bar1.gif" width="7" height="6" border="0"></td>
      <td align="right" width="7" background="/images/user/community/community_bar2.gif"><img src="/images/user/community/community_bar3.gif" width="7" height="6" border="0"></td>
    </tr>
  </table>

  <!----------------- 검색 시작 ----------------->          
  <table cellspacing="0" cellpadding="0" class="table3">
    <tr> 
      <td bgcolor="9A9A9A"> 

        <table cellspacing="1" cellpadding="1" class="table2">
          <tr> 
            <td align="center" bgcolor="#FFFFFF"> 

              <table cellspacing="0" cellpadding="0" class="table2">
                <tr> 
                  <td height="10"></td>
                </tr>
              </table>

              <!-------------------- 분류 시작 ----------------------------->
              <table width="98%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    =&gt;<b>과정분류</b> 
                    <% SelectParam param = new SelectParam("upperclass", "changeUpperclass()", true, 24, box.getHttpSession()); %>
                    <%=SelectionUtil.getUpperClass(upperclass, param)%>
                    =&gt;<b>과정</b> 
                    <% param = new SelectParam("subj", "changeSubj()", true, 24, box.getHttpSession()); %>
                    <%=SelectionUtil.getUpperClassSubjCode(upperclass, subj, param)%>
                  </td>
                </tr>
                <tr><td height="17"></td></tr>
              </table>
              <!-------------------- 분류 끝 ----------------------------->

              <table width="578" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td align="center"><a href="javascript:goGroup('A')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("A")){%>over_<%}%>butt1.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('B')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("B")){%>over_<%}%>butt2.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('C')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("C")){%>over_<%}%>butt3.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('D')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("D")){%>over_<%}%>butt4.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('E')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("E")){%>over_<%}%>butt5.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('F')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("F")){%>over_<%}%>butt6.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('G')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("G")){%>over_<%}%>butt7.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('H')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("H")){%>over_<%}%>butt8.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('I')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("I")){%>over_<%}%>butt9.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('J')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("J")){%>over_<%}%>butt10.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('K')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("K")){%>over_<%}%>butt11.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('L')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("L")){%>over_<%}%>butt12.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('M')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("M")){%>over_<%}%>butt13.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('N')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("N")){%>over_<%}%>butt14.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('O')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("O")){%>over_<%}%>butt15.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('P')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("P")){%>over_<%}%>butt16.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('Q')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("Q")){%>over_<%}%>butt17.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('R')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("R")){%>over_<%}%>butt18.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('S')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("S")){%>over_<%}%>butt19.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('T')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("T")){%>over_<%}%>butt20.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('U')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("U")){%>over_<%}%>butt21.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('V')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("V")){%>over_<%}%>butt22.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('W')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("W")){%>over_<%}%>butt23.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('X')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("X")){%>over_<%}%>butt24.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('Y')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("Y")){%>over_<%}%>butt25.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('Z')"><img src="/images/user/course/dictionary_eng_<% if (v_group.equals("Z")){%>over_<%}%>butt26.gif" width="19" height="19" border="0"></a></td>
                </tr>
                <tr> 
                  <td height="8"></td>
                </tr>
              </table>

              <table width="342" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td align="center"><a href="javascript:goGroup('ㄱ')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("ㄱ")){%>over_<%}%>butt1.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('ㄴ')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("ㄴ")){%>over_<%}%>butt2.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('ㄷ')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("ㄷ")){%>over_<%}%>butt3.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('ㄹ')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("ㄹ")){%>over_<%}%>butt4.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('ㅁ')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("ㅁ")){%>over_<%}%>butt5.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('ㅂ')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("ㅂ")){%>over_<%}%>butt6.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('ㅅ')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("ㅅ")){%>over_<%}%>butt7.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('ㅇ')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("ㅇ")){%>over_<%}%>butt8.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('ㅈ')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("ㅈ")){%>over_<%}%>butt9.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('ㅊ')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("ㅊ")){%>over_<%}%>butt10.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('ㅋ')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("ㅋ")){%>over_<%}%>butt11.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('ㅍ')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("ㅍ")){%>over_<%}%>butt12.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('ㅌ')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("ㅌ")){%>over_<%}%>butt13.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('ㅎ')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("ㅎ")){%>over_<%}%>butt14.gif" width="19" height="19" border="0"></a></td>
                  <td align="center"><a href="javascript:goGroup('기타')"><img src="/images/user/course/dictionary_kor_<% if (v_group.equals("기타")){%>over_<%}%>butt15.gif" width="31" height="19" border="0"></a></td>
                </tr>
              </table>



              <br>



              <!----------------- 검색어 시작 ----------------->
              <table cellspacing="0" cellpadding="0" class="form_table_out">
                <tr> 
                  <td> 
                    <table cellspacing="1" cellpadding="0" class="table2">
                      <tr> 
                        <td> 
                          <table cellspacing="1" cellpadding="0" class="form_table1">
                            <tr> 
                              <td align="center" bgcolor="#E4E4E4" height="28"> 
                                <table cellspacing="1" cellpadding="0" width="300" border="0">
                                  <tr> 
                                    <td width="18" align="right"><img src="/images/user/course/search_icon1.gif" width="15" height="11" border="0"></td>
                                    <td width="45" class="form_title">검색어</td>
                                    <td width="42%"> 
                                      <input size="18" name="p_searchtext"  class="input" value='<%=v_searchtext%>'>
                                    </td>
                                    <td><a href='javascript:goSearch()'><img src="/images/user/course/search_butt1.gif" width="42" height="19" border="0"></a></td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
              <!----------------- 검색어 끝 ----------------->



              <table cellspacing="0" cellpadding="0" class="table2">
                <tr> 
                  <td height="13"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr> 
      <td height="4" bgcolor="#F1F1F1"></td>
    </tr>
  </table>              
  <!----------------- 검색 끝 ----------------->
  





  <!----------------- 도트 시작 ----------------->
  <table width="98%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td height="13"></td>
    </tr>
    <tr> 
      <td height="3" background="/images/user/course/course_dot1.gif"></td>
    </tr>
    <tr> 
      <td height="13"></td>
    </tr>
  </table>
  <!----------------- 도트 끝 ----------------->

</div>
</form>
</body>
</html>
