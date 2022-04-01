<%
//**********************************************************
//  1. 제      목: 커뮤니티 QnA 
//  2. 프로그램명 : zu_CmuQnA_L.jsp
//  3. 개      요: 커뮤니티 QnA.
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.07.01 
//  7. 수      정: 2005.07.01 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    String  v_faq_type   = box.getString("p_faq_type");
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

    int v_dispnum    = 0;
    int v_totalpage  = 0;
    int v_rowcount   = 1;
    int v_upfilecnt  = 0;
    int v_pageno             = box.getInt("p_pageno");
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));


    ArrayList list       = (ArrayList)request.getAttribute("list");
    ArrayList viewfaq    =new ArrayList();
    ArrayList viewfaqfile=new ArrayList();

%>  


<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../css/user_style_community2.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
//등록페이지로이동
function uf_moveInsertPage() {

    document.form1.action = "/servlet/controller.community.CommunityQnAServlet";
    document.form1.p_process.value = "insertPage";
    document.form1.submit();
}

//페이지이동
function goPage(pageNum) {
	document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.community.CommunityQnAServlet";
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}

//조회
function uf_searchOK(pageNum) {
    if(document.form1.p_searchtext.value ==''){
       alert('검색어를 입력하여야합니다.');document.form1.p_searchtext.focus();return;
    }

	document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.community.CommunityQnAServlet";
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}

//새로고침
function uf_refreshOK() {
    document.form1.action = "/servlet/controller.community.CommunityQnAServlet";
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}

//보기
function uf_viewOK(faqno,rowseq) {
    document.form1.p_faqno.value = faqno;
    document.form1.p_rowseq.value = rowseq;
    document.form1.action = "/servlet/controller.community.CommunityQnAServlet";
    document.form1.p_process.value = "viewPage";
    document.form1.submit();
}

//-->
</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_faq_type"    value="<%=v_faq_type%>">
<input type = "hidden" name = "p_pageno"      value = "">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_upfilecnt"   value = "">
<input type = "hidden" name = "p_faqno"       value = "">
<input type = "hidden" name = "p_rowseq"       value = "">
<input type = "hidden" name = "p_grtype"       value = "<%=s_grtype %>">

<table width="900" cellspacing="3" cellpadding="3">
  <tr>
    <td>
	  <table width="890" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="right"><a href="/servlet/controller.community.CommunityIndexServlet" onFocus="blur()"><img src="../../images/community/sm_home.gif" border="0"></a></td>
        </tr>
      </table>
      <!-- 맨바깥 테두리선 테이블 -->
	  <table width="890" border="1" cellspacing="0" 
                         cellpadding="5"   style="border-collapse:collapse;" bordercolor="#B7BEC1">
        <tr>
          <td valign="top">
		  <!-- 전체 백그라운드 넣는 테이블-->
		    <table width="876" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <%if(s_grtype.equals("KOCCA")){%>
					<td valign="top" background="/images/community/back_total.jpg">
				<%}else if(s_grtype.equals("KGDI")){%>
					<td valign="top" background="/images/user/game/community/back_total.jpg"><%}%>
				<!-- top table -->
				  <table width="876" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="168" valign="top"><a href="/servlet/controller.community.CommunityIndexServlet" onfocus="blur()">
							<%if(s_grtype.equals("KOCCA")){%><img src="/images/user/kocca/community/top_img01.jpg" border="0"></a></td><%}else if(s_grtype.equals("KGDI")){%><img src="/images/user/game/community/top_img01.jpg" border="0"></a></td><%}%>	
						<td width="708" valign="top">
							<%if(s_grtype.equals("KOCCA")){%><img src="/images/user/kocca/community/topimg.jpg"></td>
							<%}else if(s_grtype.equals("KGDI")){%><img src="/images/user/game/community/topimg.jpg"></td><%}%>	
					</tr>
				</table>
				  <!-- center table start -->
                  <table width="876" border="0" cellpadding="0" cellspacing="0">
                    <tr> 
                      <td valign="top" style="padding-left:5px">
					    <table width="860" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <!-- left -->
                            <td width="174"  valign="top" background="../../images/community/bg_left.gif" style="padding-left:5px">
                             <%@ include file="/learn/user/include/topCommunityA.jsp"%>
                            </td>
                            <!-- center -->
                            <td width="686" rowspan="2" valign="top" background="../../images/community/bg_center.gif" > 
                              <table width="686" border="0" cellspacing="0" cellpadding="0" >
                                <tr> 
                                  <td><img src="../../images/community/center_top.gif"></td>
                                </tr>
                              </table>
                              <table width="686" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td align="center" valign="top"> 
                                    <!-- 커뮤니티main center start-->
                                    <!-- 검색창 -->
                                    <table width="652" border="0" cellpadding="0" cellspacing="0" >
                                      <tr> 
                                        <td width="390">&nbsp;</td>
                                        <td width="88"><select name="p_select">
                                            <option value="title"   <%if("title".equals(v_select)) out.print(" selected");%>>제목</option>
                                            <option value="content" <%if("content".equals(v_select)) out.print(" selected");%>>내용</option>
                                            <option value="name"    <%if("name".equals(v_select)) out.print(" selected");%>>글쓴이</option>
                                          </select></td>
                                        <td width="134"><input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>" size="20"></td>
                                        <td width="40"><a href="javascript:uf_searchOK(1);"><img src="../../images/community/btn_search.gif"></a></td>
                                      </tr>
                                      <tr bgcolor="#FFFFFF"> 
                                        <td height="5" colspan="4"></td>
                                      </tr>
                                    </table>
                                    <!-- 전체타이틀 -->
                                    <table width="652" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="1" bgcolor="CCCCCC"></td>
                                      </tr>
                                      <tr> 
                                        <td>
										  <table width="243" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td valign="bottom" class="title">커뮤니티<img src="../../images/community/title_vline.gif" align="absbottom"><strong><%if("DIRECT".equals(v_faq_type)){out.print("공지사항");}else{out.print("Q&amp;A");}%></strong></td>
                                            </tr>
                                          </table>
										</td>
                                      </tr>
                                      <tr> 
                                        <td height="1" bgcolor="CCCCCC"></td>
                                      </tr>
                                      <tr> 
                                        <td height="10"></td>
                                      </tr>
                                    </table>
                                    <!-- 게시판 리스트 -->
                                    <table width="652" border="2" cellspacing="0"  cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                                      <tr  class="lcolor"> 
                                        <td height="3" colspan="6" class="line_color_com"></td>
                                      </tr>
                                      <tr> 
                                        <td width="13" class="tbl_ctit2">&nbsp;</td>
                                        <td width="24" class="tbl_ctit1">번호</td>
                                        <td width="301" class="tbl_ctit2">제목</td>
                                        <td width="154" class="tbl_ctit1">작성자</td>
                                        <td width="70" class="tbl_ctit2">게시일</td>
                                        <td width="38" class="tbl_ctit1">조회수</td>
                                      </tr>
<%  if(list.size() != 0){
 	  for(int i = 0; i < list.size(); i++) {
          DataBox dbox = (DataBox)list.get(i);
          v_totalpage = dbox.getInt("d_totalpage");
        String v_title=dbox.getString("d_title");
        String v_name=dbox.getString("d_name");
        if (!v_searchtext.equals("")&&v_select.equals("title")) {
            v_title = StringManager.replace(dbox.getString("d_title"), v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
        } else if(!v_searchtext.equals("")&&v_select.equals("name")){
            v_name = StringManager.replace(dbox.getString("d_name"), v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
        }

        int i_datediff= FormatDate.datediff("date",FormatDate.getFormatDate(dbox.getString("d_register_dte"), "yyyyMMdd"), FormatDate.getDate("yyyyMMdd"));
        String v_new_state="";

        if(i_datediff<4){
          v_new_state="<img src='../../images/community/ico_new.gif' align=absbottom>";
        }


%>
                                      <tr> 
                                        <td align="center" class="<%if(((i+1)%2)==0){out.print("tbl_grc");}else{out.print("tbl_gc_back");}%>"><%if(dbox.getInt("d_cnt")>0){%><img src="../../images/community/ico_kl.gif"><% } else {%>&nbsp;<%}%></td>
                                        <td class="<%if(((i+1)%2)==0){out.print("tbl_grc");}else{out.print("tbl_gc_back");}%>"><%=dbox.getInt("d_dispnum")%></td>
<% if(dbox.getInt("d_lv")>1){%>
                                        <td class="<%if(((i+1)%2)==0){out.print("tbl_gleft");}else{out.print("tbl_gleft_back");}%>"><a href="javascript:uf_viewOK(<%=dbox.getInt("d_faqno")%>,<%=dbox.getInt("d_rowseq")%>);"><%for(int j=1;j<dbox.getInt("d_lv");j++)out.print("&nbsp;&nbsp;");%><img src="../../images/community/ico_re.gif" align="absbottom"><%=v_title%><%=v_new_state%></a></td>
<% } else {%>
                                        <td class="<%if(((i+1)%2)==0){out.print("tbl_gleft");}else{out.print("tbl_gleft_back");}%>"><a href="javascript:uf_viewOK(<%=dbox.getInt("d_faqno")%>,<%=dbox.getInt("d_rowseq")%>);"><%for(int j=1;j<dbox.getInt("d_lv");j++)out.print("&nbsp;&nbsp;");%><%=v_title%><%=v_new_state%></a></td>
<% }%>
                                        <td class="<%if(((i+1)%2)==0){out.print("tbl_gleft");}else{out.print("tbl_gleft_back");}%>"><%=v_name%>&nbsp; <%=dbox.getString("d_jikupnm")%>/<%=dbox.getString("d_jikwinm")%>&nbsp;<%=dbox.getString("d_deptnam")%></td>
                                        <td class="<%if(((i+1)%2)==0){out.print("tbl_grc");}else{out.print("tbl_gc_back");}%>"><%=FormatDate.getFormatDate(dbox.getString("d_register_dte"), "yyyy/MM/dd")%></td>
                                        <td class="<%if(((i+1)%2)==0){out.print("tbl_grc");}else{out.print("tbl_gc_back");}%>"><%=dbox.getInt("d_read_cnt")%></td>
                                      </tr>
<%    }
    } else {%>
                                      <tr> 
                                        <td align="center" class="tbl_grc" colspan=6>등록 된 질문이 없습니다.</td>
                                      </tr>
<% }%>
                                    </table>
                                   
                                    <!-- 버튼 -->
                                    <table width="652" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="5" align="right" colspan=2></td>
                                      </tr>
                                      <tr> 
                                        <td align=center>
                                          <table width="130" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td><%System.out.println("........v_totalpage:"+v_totalpage+"   v_pageno:"+v_pageno);%>
                                                <%= CommunityQnABean.printPageList(v_totalpage, v_pageno, row) %>
                                                </td>
                                              </tr>
                                            </table>
                                        </td>
                                        <td align="right" width=150><a href="javascript:uf_refreshOK();"><img src="../../images/user/button/btn_newmodi.gif" width="65" height="21"></a> 
                                          <a href="javascript:uf_moveInsertPage();"><img src="../../images/user/button/btn_write.gif" border="0"></a></td>
                                      </tr>
                                    </table>
								  </td>
                                </tr>
                                <tr>
                                  <td height="10" align="center" valign="top"></td>
                                </tr>
                              </table>
							</td>
                          </tr>
                          <tr>
                            <td height="100" valign="bottom" background="../../images/community/bg_left2.gif" style="padding-left:5px">&nbsp; 
                            </td>
                          </tr>
                        </table>
						<!-- footer table -->
                        <table width="860" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/community/footer_top.gif"></td>
                          </tr>
                          <tr> 
                            <td height="28" align="center" bgcolor="F0F2F0">
								<%if(s_grtype.equals("KOCCA")){%><img src="/images/user/kocca/community/footer_copy.gif">
								<%}else if(s_grtype.equals("KGDI")){%><img src="/images/user/game/community/footer_copy.gif"><%}%>
							</td>
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
	</td>
  </tr>
</table>
</form>
</body>
</html>
