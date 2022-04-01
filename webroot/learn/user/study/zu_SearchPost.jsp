<%
//**********************************************************
//  1. 제      목: SEARCH POSTCODE
//  2. 프로그램명: zu_SearchPost.jsp
//  3. 개      요: 우편번호 검색
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 8. 28
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");  
    String v_process    =  box.getString("p_process");
    String v_zipcode    = "";
    String v_sido       = "";
    String v_gugun      = "";
    String v_dong       = "";
    String v_bunji      = "";
    String v_post1      = "";
    String v_post2      = "";
    String v_addr       = "";
    int    idx          =  0;
    ArrayList list          =  null;
    
    System.out.println("v_process==========>"+v_process);
    if(v_process.equals("SearchPostAtOpenWin")){
        list = (ArrayList)request.getAttribute("SearchPost");    
    }    
%>    
<html>
<head>
<title>::: 우편번호찾기 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<script language="JavaScript">
<!--
    function text_enter(e)
    {
        if (e.keyCode =='13'){	whenInsetDong();  }
    } 
    function whenInsetDong(){
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.study.MyClassServlet";
        document.form1.p_process.value = "SearchPostAtOpenWin";
        document.form1.submit();	
    }    
    
    function whenSelectZipcode(post1,post2,addr){
        opener.document.form1.p_post1.value = post1;
        opener.document.form1.p_post2.value = post2;
        opener.document.form1.p_addr.value = addr;
        opener.document.form1.p_addr.focus();
        self.close();
    }    
    
    function dong_focus() {
      form1.p_dong.focus();
    }    
//-->
</script>    
</head>

<body bgcolor="#FFFFFF" topmargin="0" leftmargin="0" marginheight="0" marginwidth="0" onLoad='dong_focus()'> 
<form name = "form1" method = "post">
    <input type="hidden" name="p_process">
    <input type="hidden" name="p_zipcode">

<table width="350" border="0" cellspacing="0" cellpadding="0" height="100%">
  <tr> 
    <td height="3"> 

      <table cellspacing="0" cellpadding="0" class="table2">
        <tr> 
          <td background="/images/user/study/postsearch_top_line2.gif" height="8" width="27"><img src="/images/user/study/postsearch_top_line1.gif" width="27" height="8" border="0"></td>
          <td background="/images/user/study/postsearch_top_line2.gif" width="291"></td>
          <td align="right" background="/images/user/study/postsearch_top_line2.gif" width="32"><img src="/images/user/study/postsearch_top_line3.gif" width="32" height="8" border="0"></td>
        </tr>
      </table>

    </td>
  </tr>


  <tr> 
    <td background="/images/user/study/postsearch_top_bg.gif" height="34"><img src="/images/user/study/postsearch_top_bar1.gif"  alt="우편번호찾기" width="135" height="53" border="0"></td>
  </tr>
  <tr> 
    <td align="center" valign="top" class="body_color"> 

      <br>

      <!----------------- 우편번호찾기 시작 ----------------->
      <table width="334" border="0" cellspacing="1" cellpadding="2">
        <tr> 
          <td align="center"> ▣ 찾고자하는 동/읍/면 이름을 입력하세요.</td>
        </tr>
        <tr> 
          <td align="center">예) 칠괴동, 공도읍</td>
        </tr>
        <tr> 
          <td align="center"> 

            <table width="170" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td> 
                  <input type="text" name="p_dong" onkeypress="text_enter(event)" size="18" maxlength="25" class="input">
                </td>
                <td><a href="javascript:whenInsetDong()"><img src="/images/user/button/btn_j.gif"  alt="조회" border="0" style="cursor:hand"></a></td>
              </tr>
            </table>

          </td>
        </tr>
      </table>
      <!----------------- 우편번호찾기 끝 ----------------->


      <br>
<%  if(v_process.equals("SearchPostAtOpenWin")){  %>
      <!----------------- 우편번호 검색결과 시작 ----------------->
      <table cellspacing="1" cellpadding="0" class="postsearch_table1">
        <tr> 
          <td width="70" class="postsearch_title">우편번호</td>
          <td class="postsearch_title">주&nbsp;&nbsp; 소</td>
        </tr>
        <%
            for(int i = 0; i < list.size(); i++) {
                MyClassData data    = (MyClassData)list.get(i);	
                v_zipcode           =  data.getZipcode();
                v_sido              =  data.getSido();
                v_gugun             =  data.getGugun();
                v_dong              =  data.getDong();
                v_bunji             =  data.getBunji();      
                
                idx = v_zipcode.indexOf("-");
                v_post1             = v_zipcode.substring(0, idx);
                v_post2             = v_zipcode.substring(idx+1);
                v_addr              = v_sido+" "+v_gugun+" "+v_dong;
                             
%>                             
        <tr> 
          <td class="postsearch_text1"><a href="javascript:whenSelectZipcode('<%=v_post1%>','<%=v_post2%>','<%=v_addr%>')"><%=v_zipcode%></a></td>
          <td class="postsearch_text2">&nbsp;<%=v_sido%>&nbsp;<%=v_gugun%>&nbsp;<%=v_dong%>&nbsp;<%=v_bunji%></td>
        </tr>
    <% } %>
      </table>
      <!----------------- 우편번호 검색결과 끝 ----------------->    
<% } %>              
      <br>
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
