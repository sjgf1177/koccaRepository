<%
//**********************************************************
//  1. 제      목: HomePageSuperiorityMember
//  2. 프로그램명 : gu_HomePageService_R.jsp
//  3. 개      요: 홈페이지 메인 우수회원 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 하경태 2005.12. 14
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");
    box.put("leftmenu","02");                       // 메뉴 아이디 세팅
    
    String  s_userid = box.getSession("userid");
    int     v_pageno   = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");

    int v_seq        = box.getInt("p_seq");

    int    v_cnt        = 0;            // 조회수
    String v_gubun      = "";           // 전체구분
    String v_title      = "";           // 제목
    String v_singleword = "";           // 한마디
    String v_content    = "";           // 내용
    String v_ldate      = "";           // 등록일
    String v_lname      = "";           // 등록자
    String v_luserid    = "";           // userid

    String v_useyn      = "";           // 사용유무
    String v_month      = "";           // 해당월
    String v_addate     = "";           // 등록일
    String v_savefile   = "";
    
    Vector v_realfileVector = null;
    Vector v_savefileVector = null;
    
    DataBox dbox = (DataBox)request.getAttribute("selectHomePageSuperiorityMember");
    
    if (dbox != null) {    
        v_cnt       = dbox.getInt("d_cnt");
        v_singleword  = dbox.getString("d_singleword"); 
        v_title     = dbox.getString("d_title"); 
        v_content   = dbox.getString("d_contents");
        v_ldate     = dbox.getString("d_ldate");   
        v_lname     = dbox.getString("d_lname");
        v_useyn     = dbox.getString("d_useyn");    
        v_gubun     = dbox.getString("d_gubun");    
        v_month     = dbox.getString("d_lmonth");       
        v_savefile  = dbox.getString("d_savefile");
        
        v_addate     = FormatDate.getFormatDate(v_ldate, "yyyy/MM/dd");
        //v_adcontent  = StringManager.replace(data.getAdcontent(),"\n","<br>");
     }  
         
    
    if(v_useyn.equals("Y")) v_useyn = "사용";
    else if(v_useyn.equals("N")) v_useyn = "사용안함";
        
    String  s_gadmin    = box.getSession("gadmin");
    String v_gadmin ="";
    if(!s_gadmin.equals("")){
      v_gadmin = s_gadmin.substring(0,1);
    }
    
%>

<SCRIPT LANGUAGE="JavaScript">
<!--
    function list_page() {
        document.form1.action = "/servlet/controller.homepage.HomePageSuperiorityMemberServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

//-->
</SCRIPT>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topService.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"    value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_seq"        value = "<%=v_seq %>">
    <input type = "hidden" name = "p_gubun"     value = "<%=v_gubun%>">
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/service/<%=tem_subimgpath%>/tit_monthmember.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > Service> 이달의 우수회원</td>
  </tr>
  <tr> 
    <td height="10"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="70" align="center"><img src="/images/user/game/service/text_mem_img.gif"></td>
  </tr>
  <tr> 
    <td><img src="/images/user/game/service/month_topline.jpg"></td>
  </tr>
</table>
<!-- 12월회원 -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="720" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="15" colspan="4" align="center"></td>
        </tr>
        <tr> 
          <td width="175" align="center"><table width="100" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="/images/user/game/service/month<%=v_month%>.gif"></td>
              </tr>
              <tr> 
                <td align="right"><table width="100" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="right">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="right"><%=v_title%></td>
                    </tr>
                    <tr> 
                      <td height="5" background="/images/user/game/service/month_nameline.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
          <td width="86" valign="top">
             <table width="78" border="1" cellpadding="0" cellspacing="1" bordercolor="#CCCCCC" bgcolor="#FFFFFF">
               <tr> 
                 <td>
                    <%if(v_savefile.equals("")){%>
                        <img src="/images/user/game/service/photo_blank.gif" width="78" height="60"></td>
                    <%}else{%>
                        <img src="<%=conf.getProperty("url.upload")+"bulletin/"%><%=v_savefile%>" width="78" height="60"></td>
                    <%}%>
              </tr>
            </table>
          </td>
          <td width="10"><img src="/images/user/game/service/month_vline.gif"></td>
          <td width="449" valign="top" bgcolor="#F4EBE8" class="month_word"><%=v_singleword%></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="8"></td>
  </tr>
  <tr>
    <td height="1" background="/images/user/game/service/dotline.gif"></td>
  </tr>
  <tr>
    <td class="tbl_contents"><%=v_content%></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="164" height="2" class="linecolor_service"></td>
    <td width="556" height="2" class="linecolor_ser2"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="2" class="lcolor"></td>
  </tr>
  <tr> 
    <td height="1" class="linecolor_board"></td>
  </tr>
  <tr> 
    <td height="5"></td>
  </tr>
  <tr> 
    <td align="right" valign="bottom"><a href="javascript:list_page()"><img src="/images/user/game/button/btn_list.gif" border="0"></a></td>
  </tr>
  <tr> 
    <td height="5" ></td>
  </tr>
  <tr> 
    <td height="2"class="linecolor_board"></td>
  </tr>
</table>
</form>
</body>
</html>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
