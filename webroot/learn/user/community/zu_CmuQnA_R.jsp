<%
//**********************************************************
//  1. 제      목: 커뮤니티 QnA 
//  2. 프로그램명 : zu_CmuQnA_R.jsp
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
    int     v_faqno      = box.getInt("p_faqno");
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    int     v_pageno     = box.getInt("p_pageno");
    int     v_rowseq     = box.getInt("p_rowseq");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");

    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

    String ems_url = conf.getProperty("ems.url.value"); // 메일

    ArrayList list         = (ArrayList)request.getAttribute("list");
    ArrayList viewfaq      = new ArrayList();
    ArrayList viewfaq2     = new ArrayList();
    ArrayList viewfaqreplay= new ArrayList();
    
    if(list.size() != 0){
       viewfaq        = (ArrayList)list.get(0);
       viewfaq2       = (ArrayList)list.get(1);
       viewfaqreplay  = (ArrayList)list.get(2);  

    }
    // 본문글 정보를 담고 있는 DATABOX 
    DataBox dbox = (DataBox)viewfaq.get(0);
    
    // 본문글의 댓글의 갯수
    int v_replcnt    = viewfaqreplay.size();

%>  

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../css/user_style_community2.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
	//리스트로 이동
	function wf_listOK() {
		document.form1.action = "/servlet/controller.community.CommunityQnAServlet";
		document.form1.p_process.value = "selectlist";
		document.form1.submit();
	}

	//다음,이전글로이동
	function uf_viewOK(faqno,rowseq) {
		document.form1.p_faqno.value = faqno;
		document.form1.p_rowseq.value = rowseq;
		document.form1.action = "/servlet/controller.community.CommunityQnAServlet";
		document.form1.p_process.value = "viewPage";
		document.form1.submit();
	}

	//댓글등록하기
	function uf_insertMemoOK() {
		if(document.form1.p_rep_content.value ==''){
		   alert('댓글 내용을 입력하여야합니다.');document.form1.p_rep_content.focus();return;
		}

		document.form1.action = "/servlet/controller.community.CommunityQnAServlet";
		document.form1.p_process.value = "insertMemoData";
		document.form1.submit();
	}

	//댓글삭제하기
	function uf_deleteMemoOK(rplno) {
		document.form1.p_rplno.value = rplno;
		document.form1.action = "/servlet/controller.community.CommunityQnAServlet";
		document.form1.p_process.value = "deleteMemoData";
		document.form1.submit();
	}


	//글삭제하기
	function uf_deleteOK() {
		document.form1.action = "/servlet/controller.community.CommunityQnAServlet";
		document.form1.p_process.value = "deleteData";
		document.form1.submit();
	}


	//수정페이지로이동하기
	function uf_updateOK() {
		document.form1.action = "/servlet/controller.community.CommunityQnAServlet";
		document.form1.p_process.value = "updatePage";
		document.form1.submit();
	}

	//답변등록페이지로이동하기
	function uf_replyOK() {
		document.form1.action = "/servlet/controller.community.CommunityQnAServlet";
		document.form1.p_process.value = "replyPage";
		document.form1.submit();
	}


	//일반메일 발송
    function MailSend() {
      window.self.name = "ProposeMemberList";
      open_window("openFreeMail","","10","10","800","650");
      document.form1.target = "openFreeMail";
      document.form1.action='<%=ems_url%>/ems/MassMail.do';
      document.form1.p_process.value = 'SendFreeMail';
      document.form1.submit();
      //document.form2.target = window.self.name;      
    }

//-->
</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
<form name = "form1"    method = "post">
	<input type = "hidden" name = "p_process"     value="">
	<input type = "hidden" name = "p_faq_type"    value="<%=v_faq_type%>">
	<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
	<input type = "hidden" name = "p_searchtext"      value = "<%=v_searchtext%>">
	<input type = "hidden" name = "p_select"   value = "<%=v_select%>">
	<input type = "hidden" name = "p_userid"      value = "">
	<input type = "hidden" name = "p_upfilecnt"   value = "">
	<input type = "hidden" name = "p_rplno"   value = "">
	<input type = "hidden" name = "p_faqno"   value = "<%=v_faqno%>">
	<input type = "hidden" name = "p_lv"   value = "<%=dbox.getInt("d_lv")%>">
	<input type = "hidden" name = "p_position"   value = "<%=dbox.getInt("d_position")%>">
	<input type = "hidden" name = "p_parent"   value = "<%=dbox.getInt("d_parent")%>">
	<input type = "hidden" name = "p_root"   value = "<%=dbox.getInt("d_root")%>">
	<input type = "hidden" name = "p_grtype"       value = "<%=s_grtype %>">

	<input type = "hidden" name = "p_rowseq"   value = "<%=v_rowseq%>">

	<input type="hidden" name="p_checks" value="<%=dbox.getString("d_register_userid")%>,,,">
	<input type="hidden" name="userLoginID" value="<%=box.getSession("userid")%>">

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
                                    <!--Q&A보기 -->
                                    <table width="652" border="2" cellspacing="0" 
            cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
                                      <tr  class="lcolor"> 
                                        <td height="3" colspan="4" class="line_color_com"></td>
                                      </tr>
                                      <tr> 
                                        <td width="96" class="tbl_ctit1">작성자</td>
                                        <td class="tbl_gleft"><!--a href="javascript:uf_comSendMail('<%=dbox.getString("d_register_userid")%>');"--><%=dbox.getString("d_name")%>&nbsp; <%=dbox.getString("d_jikupnm")%>/<%=dbox.getString("d_jikwinm")%>&nbsp;<%=dbox.getString("d_deptnam")%></a></td>
                                        <td class="tbl_ctit1">번호</td>
                                        <td class="tbl_gleft"><%=dbox.getString("d_faqno")%></td>
                                      </tr>
                                      <tr> 
                                        <td width="96" class="tbl_ctit2">게시일</td>
                                        <td width="341" class="tbl_gleft"><%=FormatDate.getFormatDate(dbox.getString("d_register_dte"), "yyyy/MM/dd")%></td>
                                        <td width="49" class="tbl_ctit2">조회수</td>
                                        <td width="130" class="tbl_gleft"><%=dbox.getInt("d_read_cnt")%></td>
                                      </tr>
                                    <% if(((Vector)dbox.getObject("d_realfile")).size()>0){%>
                                      <tr> 
                                        <td class="tbl_ctit2" >첨부파일</td>
                                        <td colspan="3" valign="top" class="tbl_bdata" >
                                        <% 
								        Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
								        Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
								        String i_boardStyle        = "";                                          // 보드 스타일
								        %>
								        <%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %>

                                        </td>
                                      </tr>
                                    <%}%>
                                      <tr> 
                                        <td class="tbl_ctit1" >제목</td>
                                        <td colspan="3" valign="top" class="tbl_btit" ><%=dbox.getString("d_title")%></td>
                                      </tr>
                                      <tr> 
                                        <td colspan="4" valign="top" class="tbl_contents" ><%=dbox.getString("d_content")%></td>
                                      </tr>
                                    </table>
                                    <!-- 이전글다음글 테이블 -->
                                    <table width="652" border="2" cellspacing="0" 
            cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
									<%
									 	  for(int i = 0; i < viewfaq2.size(); i++) {
									          DataBox dbox2 = (DataBox)viewfaq2.get(i);
									%>
                                      <tr> 
                                        <td width="86" class="tbl_ctit2"><%=dbox2.getString("d_gubun").equals("PREV") ? "이전글" : "다음글" %></td>
                                        <td width="546" class="tbl_btit"><a href="javascript:uf_viewOK(<%=dbox2.getInt("d_faqno")%>,<%=dbox2.getInt("d_rowseq")%>)"><%=dbox2.getString("d_title")%></a></td>
                                      </tr>

                                        <%    }%>
                                    </table>
                                    <!-- 버튼 -->
                                    <table width="652" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="5"></td>
                                      </tr>
                                      <tr> 
                                        <td align="right"><%if(s_userid.equals(dbox.getString("d_userid"))){%><a href="javascript:uf_deleteOK();"><img src="../../images/user/button/btn_del.gif" border="0"></a>
                                        <a href="javascript:uf_updateOK();"><img src="../../images/user/button/btn_mod.gif" border="0"></a><%}%>
                                        <a href="javascript:uf_replyOK();"><img src="../../images/user/button/btn_answer.gif" width="45" height="21" border="0"></a> 
                                          <a href="javascript:wf_listOK();"><img src="../../images/user/button/btn_list.gif" border="0"></a></td>
                                      </tr>
                                    </table>
                                    <!-- 댓글달기table -->
                                    <table width="652" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td colspan="5"><img src="../../images/community/search_line.gif"></td>
                                      </tr>
                                      
<%
 	  for(int i = 0; i < viewfaqreplay.size(); i++) {
          DataBox dboxre = (DataBox)viewfaqreplay.get(i);
%>
                                      <tr> 
                                        <td width="90" class="tbl_br"><%=dboxre.getString("d_name")%></td>
                                        <td width="8" class="tbl_bc"><img src="../../images/community/c_vline.gif"></td>
                                        <td width="489" class="tbl_gleft"><%=dboxre.getString("d_content")%></td>
                                        <td width="6"><img src="../../images/community/c_vline.gif"></td>
                                        <td width="83" class="tbl_gleft"><%=FormatDate.getFormatDate(dboxre.getString("d_register_dte"), "yyyy/MM/dd")%><%if(s_userid.equals(dboxre.getString("d_userid"))){%><a href="javascript:uf_deleteMemoOK(<%=dboxre.getString("d_rplno")%>)">삭제</a><%}%></td>
                                      </tr>
                                      <tr> 
                                        <td colspan="5"><img src="../../images/community/search_line.gif"></td>
                                      </tr>
<% }%>
                                      <tr> 
                                        <td height="3" colspan="5"></td>
                                      </tr>
                                      <tr> 
                                        <td height="5" colspan="5"> 
										  <table width="652" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td width="588"><input name="p_rep_content" type="text" class="input2" size="92" maxlength=120></td>
                                              <td width="87" align="center"><a href="javascript:uf_insertMemoOK();"><img src="../../images/user/button/btn_wr.gif"></a></td>
                                            </tr>
                                          </table>
										</td>
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
