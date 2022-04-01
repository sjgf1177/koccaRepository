<%
//**********************************************************
//  1. 제      목: 서비스 > 공지사항 상세보기
//  2. 프로그램명 : gu_Notice_R.jsp
//  3. 개      요: 공지사항 상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 05.12.19 이나연
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	box.put("leftmenu","01");  
 
    String  v_process    = box.getString("p_process");
    int     v_pageno     = box.getInt   ("p_pageno");
	String  s_userid     = box.getSession("userid");
	String  s_gadmin	 = box.getSession("gadmin");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");

    int v_tabseq   = box.getInt("p_tabseq");
    int v_seq      = box.getInt("p_seq");

    int    v_cnt        = 0;            // 조회수
    String v_gubun      = "";           // 전체구분
	String v_userid	    = "";           // 작성자

    String v_adtitle    = "";           // 제목
    String v_adcontent  = "";           // 내용
    String v_addate     = "";           // 등록일
    String v_adname     = "";           // 등록자
	String v_luserid    = "";
    String v_loginyn    = "";			// 로그인유무
    String v_useyn   	= "";			// 사용유무
    String v_grcodecd   = "";           // 대상 교육그룹
    
    String v_popup   = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe= "";               // 프레임사용
    
    String v_upfile    ="";
    //String v_realfile  ="";
    
    Vector v_realfileVector = null;
    Vector v_savefileVector = null;
    Vector v_fileseqVector  = null;

    DataBox dbox = (DataBox)request.getAttribute("selectNotice");
    
    if (dbox != null) {
        v_gubun     = dbox.getString("d_gubun");            
        v_adtitle   = dbox.getString("d_adtitle");  
        v_adcontent = dbox.getString("d_adcontent");
        v_addate    = dbox.getString("d_addate");   
        v_adname    = dbox.getString("d_adname"); 
		v_luserid	= dbox.getString("d_luserid");
        v_cnt       = dbox.getInt("d_cnt");      
		v_loginyn   = dbox.getString("d_loginyn");   
		v_useyn   	= dbox.getString("d_useyn");     
		v_grcodecd	= dbox.getString("d_grcodecd");     
		v_upfile    = dbox.getString("d_upfile");    

		v_uselist   = dbox.getString("d_uselist");   
		v_useframe  = dbox.getString("d_useframe");  
		v_userid	= dbox.getString("d_aduserid");
        v_addate     = FormatDate.getFormatDate(v_addate, "yyyy/MM/dd");
        //v_adcontent  = StringManager.replace(data.getAdcontent(),"\n","<br>");
        
        
        v_realfileVector    = (Vector)dbox.getObject("d_realfile");
        v_savefileVector    = (Vector)dbox.getObject("d_savefile");
        v_fileseqVector		= (Vector)dbox.getObject("d_fileseq");
     }  

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topOpenkocca.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">

	//내용 삭제
	function delete_HomePageBoard(seq) {
		if (confirm("정말로 삭제하시겠습니까?")) {
			document.nform2.p_userid.value = "<%= v_userid %>";
			document.nform2.p_seq.value = seq;
			document.nform2.action = "/servlet/controller.homepage.OpenForumNoticeServlet";
			document.nform2.p_process.value = "delete";
			document.nform2.submit();
		}
		else {
			return;
		}
	}

	//리스트 페이지로 이동
	function selectList() {
		document.nform2.action = "/servlet/controller.homepage.OpenForumNoticeServlet";
		document.nform2.p_process.value = "List";
		document.nform2.submit();
	}

	//수정 페이지로 이동
	function updatePage() {
		document.nform2.action = "/servlet/controller.homepage.OpenForumNoticeServlet";
		document.nform2.p_process.value = "updatePage";
		document.nform2.submit();
	}

</SCRIPT>

<form name="nform2" method="post">
	<input type = "hidden" name = "p_process">
	<input type = "hidden" name = "p_pageno"    value = "<%=v_pageno %>">
	<input type = "hidden" name = "p_userid"    value = "<%=v_userid %>">
	<input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
	<input type = "hidden" name = "p_seq"		value = "<%=v_seq %>">


<table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 열린마당 > 회원포럼</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/openkocca/<%=tem_subimgpath%>/tit_forum.gif"></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="88" rowspan="3"><img src="/images/user/kocca/openkocca/text_forum.gif"></td>
                          </tr>
                          <tr> 
                            <td height="12"></td>
                          </tr>
                          <tr > 
                            <td height="1" colspan="2" background="/images/user/kocca/common/dot_bg_9.gif"></td>
                          </tr>
                          <tr > 
                            <td height="7" colspan="2" ></td>
                          </tr>
                        </table>
                        <!--게시판목록 -->
                         <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/openkocca/st_forum_notice.gif"> </td>
                          </tr>                          
                        </table>
                        
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="5" colspan="6"></td>
                          </tr>
                          <tr> 
                            <td colspan="6"><img src="/images/user/kocca/openkocca/bar_forum_write_top.gif" ></td>
                          </tr>
                          <tr> 
                            <td height="2" colspan="6"></td>
                          </tr>
                          <tr> 
                            <td width="75" class="tbl_gleft03">제목</td>
                            <td colspan="5" class="tbl_gleft01"><%=v_adtitle%></td>
                          </tr>
                          <tr> 
                            <td colspan="6" height="1"></td>
                          </tr>
                          <tr > 
                            <td height="1" colspan="6" background="/images/user/kocca/common/dot_bg_9.gif"></td>
                          </tr>
                          <tr> 
                            <td colspan="6" height="1"></td>
                          </tr>
                          <tr> 
                            <td width="75" class="tbl_gleft03">날짜</td>
                            <td width="330" class="tbl_gleft01"><%=v_addate%></td>
                            <td width="66" class="tbl_gleft03">조회</td>
                            <td width="72" cospan=3  class="tbl_gleft01"><%=v_cnt%></td>

                          </tr>
                
                          <tr > 
                            <td height="1" colspan="6" background="/images/user/kocca/common/dot_bg_9.gif"></td>
                          </tr>
                          <tr valign="top"> 
                            <td colspan="6" class="tbl_contents"><%=v_adcontent%></td>
                          </tr>
                           <tr > 
                            <td height="1" colspan="6" background="/images/user/kocca/common/dot_bg_9.gif"></td>
                          </tr>
                          <tr> 
                            <td width="75" class="tbl_gleft03">첨부파일</td>
                            <td colspan="5" class="tbl_gleft01">                      <%		
								if( v_realfileVector != null ) { 
									for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
										String v_realfile = (String)v_realfileVector.elementAt(i);
										String v_savefile = (String)v_savefileVector.elementAt(i);  
										String v_fileseq = (String)v_fileseqVector.elementAt(i);  	
								
										if(v_realfile != null && !v_realfile.equals("")) {  
							%> 
						
								<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a> &nbsp;<br> 
							<%				
										}else{%>
								 첨부된 파일이 없습니다<br>
							<%	 
										}
								}
							}
							%> </td>
                          </tr>
                          
                          <tr> 
                            <td height="3" colspan="6" class="tbl_bgcolor_openkocca"></td>
                          </tr>
                          
                         
							
                        </table> 
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="2"></td>
                          </tr>
                          <tr>
                            <td height="27" class="linecolor_notice">
							  <table width="540" border="0" align="right" cellpadding="0" cellspacing="0">
                                <tr> 
                                  <td align="right">
									 <%if( StringManager.substring(s_gadmin,0,1).equals("A") || s_gadmin.equals("B1") || s_userid.equals(v_userid) ){%>
										<a href="javascript:updatePage()"><img src="/images/user/kocca/button/btn_mod.gif" border="0"></a>&nbsp;<a href="javascript:delete_HomePageBoard(<%=v_seq %>)"><img src="/images/user/kocca/button/btn_del.gif" border="0"></a>
									 <%}%>
										<a href="javascript:selectList()"><img src="/images/user/kocca/button/btn_list.gif" border="0"></a>&nbsp;&nbsp;</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td height="1" bgcolor="#cccccc"></td>
                          </tr>
                        </table>
                        
                        
                        
          </form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->