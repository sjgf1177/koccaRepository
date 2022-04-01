<%
//**********************************************************
//  1. 제      목: 홈페이지 FAQ 리스트화면
//  2. 프로그램명: zu_HomePageFaq_L.jsp
//  3. 개      요: 홈페이지 FAQ 리스트화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 7. 1
//  7. 수      정: 이연정 2005. 7. 1
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_process     = box.getString("p_process");
    String  v_faqcategory = "";
    v_faqcategory = box.getString("faqcategory");

    int     v_fnum       = 0, v_totalpage = 0, v_rowcount = 1;
    int     v_pageno     = box.getInt("p_pageno"); 
    String  v_title      = "";
    String  v_contents   = "";
    String  v_indate     = "";
	String  v_searchtext = box.getString("p_searchtxt");   
    ArrayList list       = (ArrayList)request.getAttribute("selectList");
    String faqcategory   ="";

box.put("leftmenu","01");                       // 메뉴 아이디 세팅
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topSupport.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript">
<!--
// faq 조회
function Search()
{
    document.form1.p_faqcodecont.value=document.form1.p_faqcode.options[document.form1.p_faqcode.selectedIndex].text;
    document.form1.action="/Controller?cmd=home&act=homefaq";
    document.form1.submit();
}

// 과정검색 엔터처리
    function search_enter(e)  {
        if (e.keyCode =='13'){  searchTxt();  }
    }

//카테고리에 맞는 리스트 검색
function changeCategory(aa) {
        document.form1.faqcategory.value=aa;
        document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
        document.form1.p_process.value    = "selectList";
        document.form1.submit();
}

//검색내용에 맞는 리스트 페이지로 이동
function searchTxt() {
	if(document.form1.p_searchtxt.value ==""){
		alert("내용을 입력하세요");
		return;
		
	}
		document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
		document.form1.p_process.value = "selectList";
		document.form1.submit();
}

function clickHandler() {
  var targetId, srcElement, targetElement, srcCss;
  srcElement = window.event.srcElement;
//alert(srcElement.className);
//if (srcElement.className=="") srcElement.className="tbl_bfaq";
  if (srcElement.className == "tbl_bfaq") {
     targetId = srcElement.id + "details";
     targetElement = document.all(targetId);
     srcCss = document.all(srcElement.id);

     if (targetElement.style.display == "none") {
        targetElement.style.display = "";
        srcCss.style.color = "#666666";
        srcCss.style.font = "normal 9pt \"돋움\"";
     } else {
        targetElement.style.display = "none";
        srcCss.style.color = "";
        srcCss.style.font = "normal 9pt \"돋움\"";
     }
  }
}

document.onclick = clickHandler;

//-->
</script>

    <!-- center start -->dddd
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="20" align="center" valign="top" class="location">HOME 
            &gt; 학습지원센터 &gt; FAQ</td>
        </tr>
        <tr> 
          <td align="center" valign="top"> <img src="/images/user/support/<%=tem_subimgpath%>/tit_faq.jpg"> 
          <form name = "form1" method = "post">
          <input type = "hidden" name = "p_process"   value = "">
          <input type = "hidden" name = "p_pageno"    value = "">
          <input type = "hidden" name = "p_seq"       value = "">
          <input type = "hidden" name = "p_userid"    value = "">
          <input type = "hidden" name = "faqcategory" value = "<%=v_faqcategory%>">

            <table width="675" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="92" valign="top" background="/images/user/support/faq_img.jpg" style="padding-top:54px">
                <table width="201" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="46"><img src="/images/user/support/faq_se.gif"></td>
                      <td width="101"><input name="p_searchtxt" type="text" class="input" value="<%=v_searchtext%>" onkeypress="search_enter(event)" size="15" ></td>
                      <td width="54"><a href="javascript:searchTxt()"><img src="/images/user/button/btn_search.gif"></a></td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td>

<%=HomePageFAQBean.homepageGetFaqCategoryTable()%>

              <!-- 카테고리 테이블 -->


              <table width="675" border="0" cellspacing="0" cellpadding="0">
              <tr>
              <td>&nbsp;</td>
               </tr>
                  <tr>
                    <td width="540" valign="top">
                    <table width="670" border="0" cellspacing="0" cellpadding="0">
                    <!--for문 돌기-->
                    <%if(list.size() != 0){%>
                    <%
                        int i =0;
                        for( i = 0; i < list.size(); i++) {

                            DataBox dbox = (DataBox)list.get(i);  
                            
                            v_title     = dbox.getString("d_title");
                            v_contents  = dbox.getString("d_contents");
                            v_totalpage = dbox.getInt("d_totalpage");
                            v_rowcount  = dbox.getInt("d_rowcount");

							if (!v_searchtext.equals("")) {
								v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
								v_contents = StringManager.replace(v_contents, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
							}


                    %>
                        <tr> 
                          <td width="22"><img src="/images/user/support/bl_q.gif"></td>
                          <td width="648" id="Out<%=i%>" class="tbl_bfaq" style="cursor:hand"><%=v_title%></td>
                        </tr>
                        
                        <tr> 
                          <td colspan="2" >
                          
                          <div id="Out<%=i%>details" style="display:none; position:relative; padding-top:4px; visibility: visible;" class=FAQ_a> 
                              <TABLE cellpadding="0" cellspacing="0" border="0" width="670">
                                <tr valign="top"> 
                                  <td width="30" class=tbl_bfaqt style="padding-top:2px"><img src="/images/user/support/bl_a.gif" border="0" ></td>
                                  <td style="padding-left:5" class=tbl_bfaqt><%=v_contents %></td>
                                </tr>
                              </TABLE>
                            </div></td>
                        </tr>
                        <!--<tr> 
                          <td colspan="2" ><img src="/images/user/support/faq_line.gif"></td>
                        </tr>-->
                        <%}%>
                        <%}else{%>
                        <tr>
                            <td colspan="13" align=center valign=top>
                                등록 된 FAQ가 없습니다.     
                            </td>
                        </tr>
                        <!--<tr> 
                            <td colspan="2" ><img src="/images/user/support/faq_line.gif"></td>
                        </tr>-->
                        <%}%>
                      </table>
                    </td>
                    <!--<td width="135" valign="bottom"><img src="/images/user/support/faq_q.gif" width="111" height="160"></td>-->
                  </tr>
                </table></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table> </td>
      </tr>
      <tr>
        <td align="center" valign="top">&nbsp;</td>
      </tr>
      <tr> 
        <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
      </tr>
    </table>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->