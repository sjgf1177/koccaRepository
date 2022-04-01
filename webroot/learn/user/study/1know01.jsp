<%
//**********************************************************
//  1. 제      목: EDUCATION SUBJECT PAGE
//  2. 프로그램명: zu_MyQna_L.jsp
//  3. 개      요: 설문
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 7. 20
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","04"); 
    
    String  ss_userid     = box.getSession("userid");    
    String  v_indate      = "";
    String  v_types       = ""; 
    String  v_title       = "";
    String  v_searchtext  = box.getString("p_searchtext");   
    String  v_select      = box.getString("p_select");   
    String  v_edustart    = "";
    String  v_eduend      = "";

     
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
    
    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");   
        
    ArrayList blist1 = (ArrayList)request.getAttribute("MyQnaListPage1");  // 학습관련
    ArrayList blist2 = (ArrayList)request.getAttribute("MyQnaListPage2");  // 사이트관련
      
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topMyclass.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
//검색내용에 맞는 리스트 페이지로 이동
function selectList(tab) {
	if (form1.p_searchtext.value == ""){
		alert("검색 할 내용을 입력해 주세요");
	}else{
	document.form1.action = "/servlet/controller.study.MyQnaServlet";
	document.form1.p_process.value = "MyQnaListPage";
    document.form1.p_pageno.value = "1";
	document.form1.submit();
	showTab(tab);	
	}
}

//페이지 이동
function goPage(pageNum, tab) {

	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.study.MyQnaServlet";
	document.form1.p_process.value = "MyQnaListPage";
	document.form1.submit();
	g_showTab(tab, 2);
}

// Tab 선택시 동작
function showTab(clickTab)
{
   //alert(clickTab);
	g_showTab(clickTab, 2);
}

function g_showTab(tabNo, count)
{

	var clickTabNo = new Number(tabNo);
	var tabCount   = new Number(count);

	if( clickTabNo == 0 || tabCount == 0 ) return;

	for(i=1; i<=tabCount; i++)
    {
		div = document.all["tab_"+i];

		if(!div) return;

		if( clickTabNo == i ) {
			// 활성화된 tab 클릭시 return
			if(div.style.display == "") return;
			div.style.display = ""
		}
		else {
			// 선택되지 않는 탭들은 깜빡임 방지.
			if(div.style.display != "none") {
				div.style.display = "none";
			}
		}
	}
}
//-->
</script>

		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location"> 
                  HOME &gt; 나의 강의실 &gt; 지식공유 &gt; 최근지식</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/myclass/<%=tem_subimgpath%>/tit_knowledge.jpg"></td>
                    </tr>
                    <tr> 
                      <td height="20"></td>
                    </tr>
                  </table>
                  <!-- 소제목 -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="675" height="5" colspan="4"><img src="/images/user/myclass/st_know_re.gif"></td>
                    </tr>
                    <tr> 
                      <td height="10" colspan="4"></td>
                    </tr>
                  </table>
                  <!-- 최근지식 table -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr valign="top"> 
                      <td colspan="7"> <table width="675" border="0" cellpadding="0" cellspacing="0" background="/images/user/myclass/class_tb_bg.gif">
                          <tr> 
                            <td width="6"><img src="/images/user/myclass/class_tb_head.gif"></td>
                            <td width="39" align="center" valign="bottom"><img src="/images/user/myclass/class_tb_num.gif"></td>
                            <td width="86" align="center" valign="bottom"><img src="/images/user/myclass/class_tb_group.gif" ></td>
                            <td width="274" align="center" valign="bottom"><img src="/images/user/myclass/class_tb_title.gif" ></td>
                            <td width="91" align="center" valign="bottom"><img src="/images/user/myclass/class_tb_name.gif"></td>
                            <td width="77" align="center" valign="bottom"><img src="/images/user/myclass/class_tb_day.gif"></td>
                            <td width="52" align="center" valign="bottom"><img src="/images/user/myclass/class_tb_file.gif"></td>
                            <td width="40" align="center" valign="bottom"><img src="/images/user/myclass/class_tb_view.gif"></td>
                            <td width="10" align="right"><img src="/images/user/myclass/class_tb_tail.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td colspan="7"><img src="/images/user/myclass/class_tb_tline.gif"></td>
                    </tr>
                    <tr> 
                      <td height="3" colspan="7"></td>
                    </tr>
                    <tr> 
                      <td width="45" class="tbl_br">5</td>
                      <td width="86" class="tbl_bleft">직무</td>
                      <td width="274" class="tbl_bleft"> <a href="1know01_view.htm">같이 
                        먹으면 해로운 음식</a> <img src="/images/user/support/ico_new.gif"></td>
                      <td width="91" class="tbl_br">김창섭</td>
                      <td width="78" class="tbl_br">2005/06/27</td>
                      <td width="51" align="center"><img src="/images/user/support/ico_file.gif" width="14" height="14"></td>
                      <td width="50" class="tbl_br">12</td>
                    </tr>
                    <tr> 
                      <td colspan="7"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td class="tbl_br">4</td>
                      <td class="tbl_bleft">어학과정</td>
                      <td class="tbl_bleft">패러다임쉬프트와 개선활동에 대한 총괄 자료 </td>
                      <td class="tbl_br">운영자</td>
                      <td class="tbl_br">&nbsp;</td>
                      <td align="center">&nbsp;</td>
                      <td class="tbl_br">23</td>
                    </tr>
                    <tr> 
                      <td colspan="7"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td class="tbl_br">3</td>
                      <td class="tbl_bleft">&nbsp;</td>
                      <td class="tbl_bleft">[HRD] 조직활성화 위한 교육의 역할</td>
                      <td class="tbl_br">김창섭</td>
                      <td class="tbl_br">&nbsp;</td>
                      <td align="center">&nbsp;</td>
                      <td class="tbl_br">56</td>
                    </tr>
                    <tr> 
                      <td colspan="7"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td class="tbl_br">2</td>
                      <td class="tbl_bleft">&nbsp;</td>
                      <td class="tbl_bleft">2002년도 일본 능력개발대회 발표자료.</td>
                      <td class="tbl_br">최귀현</td>
                      <td class="tbl_br">&nbsp;</td>
                      <td align="center"><img src="/images/user/support/ico_file.gif" width="14" height="14"></td>
                      <td class="tbl_br">102</td>
                    </tr>
                    <tr> 
                      <td colspan="7"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td class="tbl_br">1</td>
                      <td class="tbl_bleft">&nbsp;</td>
                      <td class="tbl_bleft">설문 조사 누락에 대한 질문 </td>
                      <td class="tbl_br">최귀현</td>
                      <td class="tbl_br">&nbsp;</td>
                      <td align="center">&nbsp;</td>
                      <td class="tbl_br">3</td>
                    </tr>
                    <tr> 
                      <td height="3" colspan="7"></td>
                    </tr>
                    <tr bgcolor="FCE1D0"> 
                      <td height="5" colspan="7"><img src="/images/user/myclass/class_tb_bo.gif"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="10" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td width="418" align="right"> <table width="130" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="19"><img src="/images/user/button/pre.gif" width="40" height="16"></td>
                            <td width="90" align="center"><strong>1</strong> <a href="#">2</a> 
                              <a href="#">3</a> <a href="#">4</a></td>
                            <td width="21"><a href="#"><img src="/images/user/button/next.gif" width="40" height="16" border="0"></a></td>
                          </tr>
                        </table></td>
                      <td width="257" align="right"><a href="1know01_write.htm"><img src="/images/user/button/btn_know_resi.gif" border="0"></a></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                    <tr> 
                      <td width="227">&nbsp;</td>
                      <td width="56"> <select name="menu2" onChange="MM_jumpMenu('parent',this,0)">
                          <option selected>제목</option>
                          <option>내용</option>
                        </select> </td>
                      <td width="132"><input name="textfield2" type="text" class="input" value="" size="20"></td>
                      <td width="260"><img src="/images/user/button/btn_search.gif"></td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                  </table></td>
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