<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_Notice_L.jsp
//  3. 개      요: 공지사항 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 13
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

    NoticeAdminBean selectData = new NoticeAdminBean();

    RequestBox box    = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","4");    
    String  v_process = box.getString("p_process");
  
    //out.println(v_selcomp);
    
    int v_tabseq          = box.getInt("p_tabseq");
    int  v_pageno         = box.getInt("p_pageno");
    if (v_pageno == 0) {
        v_pageno = 1;
    }
    int row = 7;
    
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");
    
    ///out.println(v_search);
    ///out.println(v_searchtext);


    int    v_seq     = 0;            // 일련번호
    int    v_cnt     = 0;            // 조회수

    String v_adtitle   = "";           // 제목
    String v_adcontent = "";           // 내용
    String v_addate    = "";           // 등록일
    String v_adname    = "";            // 등록자
    String v_compnm    = ""; 			// 회사명
    int    v_upfilecnt = 0;

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    ArrayList list1 = (ArrayList)request.getAttribute("selectNoticeListAll");
    ArrayList list2 = (ArrayList)request.getAttribute("selectNoticeList");
    //out.println(list2.size());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>index</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../homepage/inc/style01.css" rel="stylesheet" type="text/css">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}
//-->

//검색창에서 enter key쳤을때
function press_enter(e) { 
	if (e.keyCode =='13'){  whenSelection();  }
}

//검색
function whenSelection() {
	document.form1.action = "/servlet/controller.homepage.MainServlet";
	document.form1.p_process.value = "selectNoticeList";
	document.form1.submit();
}

//페이지 이동
function go(index) {
	document.form1.p_pageno.value = index;
	document.form1.action = "/servlet/controller.homepage.MainServlet";
	document.form1.p_process.value = "selectNoticeList";
	document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {
	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.homepage.MainServlet";
	document.form1.p_process.value = "selectNoticeList";
	document.form1.submit();
}

function goSelectView(seq, filecnt){
        document.form1.p_upfilecnt.value = filecnt;
        document.form1.action = "controller.homepage.MainServlet";
        document.form1.p_process.value = "selectNoticeView";
        document.form1.p_seq.value = seq;
        document.form1.submit();
    }
</script>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topHome.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

		  <!-- center start -->
<form name="form1" method = "post">
<input type="hidden" name="p_pageno" value="<%=v_pageno%>">
<input type="hidden" name="p_process" value="">
<input type="hidden" name="p_seq" value="">
<input type="hidden" name="p_upfilecnt" value="">

		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; 학습지원센터 &gt; 공지사항</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/common/<%=tem_subimgpath%>/tit_notice.jpg"></td>
                    </tr>
                    <tr>
                      <td height="20"></td>
                    </tr>
                  </table>
                  <!-- notice table -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr valign="top"> 
                      <td colspan="6"> <table width="675" border="0" cellpadding="0" cellspacing="0" background="/images/user/common/com_tb_bg.gif">
                          <tr> 
                            <td width="6"><img src="/images/user/common/com_tb_head.gif"></td>
                            <td width="39" align="center" valign="bottom"><img src="/images/user/common/com_tb_num.gif"></td>
                            <td width="327" align="center" valign="bottom"><img src="/images/user/common/com_tb_title.gif"></td>
                            <td width="136" align="center" valign="bottom"><img src="/images/user/common/com_tb_pe.gif"></td>
                            <td width="78" align="center" valign="bottom"><img src="/images/user/common/com_tb_day.gif"></td>
                            <td width="51" align="center" valign="bottom"><img src="/images/user/common/com_tb_addfile.gif"></td>
                            <td width="28" align="center" valign="bottom"><img src="/images/user/common/com_tb_view.gif"></td>
                            <td width="10" align="right"><img src="/images/user/common/com_tb_tail.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td colspan="6"><img src="/images/user/common/com_tb_tline.gif"></td>
                    </tr>
                    <tr> 
                      <td height="3" colspan="6"></td>
                    </tr>
                    
                    
                    
            <%
            String  v_addate1 = "";
            int i = 0;
            for(i = 0; i < list1.size(); i++) {

                DataBox dbox = (DataBox)list1.get(i);
                v_seq      = dbox.getInt("d_seq");
                v_cnt      = dbox.getInt("d_cnt");
                v_adtitle  = dbox.getString("d_adtitle");
                v_addate   = dbox.getString("d_addate");
                v_adname   = dbox.getString("d_adname");
                v_upfilecnt = dbox.getInt("d_filecnt");
                v_addate1   = FormatDate.getFormatDate(v_addate,"yyyy/MM/dd");
                
                if (!v_searchtext.equals("")&&v_search.equals("adtitle")) {
					v_adtitle = StringManager.replace(v_adtitle, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				} 
				else if(!v_searchtext.equals("")&&v_search.equals("adname")){
						v_adname = StringManager.replace(v_adname, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				}
                
            %>
                    <%if(i != 0){%>
                    <tr> 
                      <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <%}%>

                    <tr> 
                      <td width="45" class="tbl_br">*</td>
                      <td width="327" class="tbl_bleft"><a href="javascript:goSelectView('<%=v_seq%>', '<%=v_upfilecnt%>')"><%=v_adtitle%></a> <%if (FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_addate, "yyyyMMdd"))){%>&nbsp;<img src="/images/user/support/ico_new.gif"><%}%></td>
                      <td width="135" class="tbl_br"><%=v_adname%></td>
                      <td width="79" class="tbl_br"><%=v_addate1%></td>
                      <td width="52" align="center"><%if(v_upfilecnt>0){%><img src="/images/user/support/ico_file.gif"><%} else {%>-<%}%></td>
                      <td width="37" class="tbl_br"><%=v_cnt%></td>
                    </tr>
             <%}%>
             
             <%

            int j = 0;
            for(j = 0; j < list2.size(); j++) {

                DataBox dbox1 = (DataBox)list2.get(j);
                
                v_seq      = dbox1.getInt("d_seq");
                v_cnt      = dbox1.getInt("d_cnt");
                v_adtitle  = dbox1.getString("d_adtitle");
                v_addate   = dbox1.getString("d_addate");
                v_adname   = dbox1.getString("d_adname");
                v_addate1   = FormatDate.getFormatDate(v_addate,"yyyy/MM/dd");
                v_dispnum   = dbox1.getInt("d_dispnum");
                v_totalpage = dbox1.getInt("d_totalpage");
                v_upfilecnt = dbox1.getInt("d_filecnt");
                
                
                if (!v_searchtext.equals("")&&v_search.equals("adtitle")) {
					v_adtitle = StringManager.replace(v_adtitle, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				} 
				else if(!v_searchtext.equals("")&&v_search.equals("adname")){
						v_adname = StringManager.replace(v_adname, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				}
				
				
            %>
            
                    <tr> 
                      <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
            
                    <tr> 
                      <td width="45" class="tbl_br"><%=v_dispnum%></td>
                      <td width="327" class="tbl_bleft"><a href="javascript:goSelectView('<%=v_seq%>', '<%=v_upfilecnt%>')"><%=v_adtitle%></a> <%if (FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_addate, "yyyyMMdd"))){%>&nbsp;<img src="/images/user/support/ico_new.gif"><%}%></td>
                      <td width="135" class="tbl_br"><%=v_adname%></td>
                      <td width="79" class="tbl_br"><%=v_addate1%></td>
                      <td width="52" align="center"><%if(v_upfilecnt>0){%><img src="/images/user/support/ico_file.gif"><%} else {%>-<%}%></td>
                      <td width="37" class="tbl_br"><%=v_cnt%></td>
                    </tr>
            <%}%>
            <%if(i == 0 && j == 0){%>
                    <tr> 
                      <td colspan="8" class="tbl_br">조회된 내용이 없습니다.</td>
                    </tr>
            <%}%>

                    <tr> 
                      <td height="3" colspan="6"></td>
                    </tr>
                    <tr bgcolor="B8D5E5"> 
                      <td height="5" colspan="6"><img src="/images/user/common/com_tb_bo.gif"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="10" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td width="418" align="right">
					  <table width="130" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td>
                            <%= HomePageQnaBean.printPageList(v_totalpage, v_pageno, row) %>
                            </td>
                          </tr>
                        </table></td>
                      <td width="257" align="right">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                    <tr> 
                      <td width="227">&nbsp;</td>
                      <td width="56">
                      <select name="p_search">
                          <option value="adtitle"    <% if (v_search.equals("adtitle")) out.print("selected"); %>>제목</option>
                          <option value="adname" <% if (v_search.equals("adname")) out.print("selected"); %>>작성자</option>
                          <option value="adcontents" <% if (v_search.equals("adcontents")) out.print("selected"); %>>내용</option>
                      </select> 
                      </td>
                      <td width="132"><input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>" size="20" onkeypress="press_enter(event)"></td>
                      <td width="260"><a href="javascript:whenSelection()"><img src="/images/user/button/btn_search.gif" border="0"></a></td>
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

