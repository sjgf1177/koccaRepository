<%
//**********************************************************
//  1. 제      목: 서비스 > 포럼게시판 
//  2. 프로그램명 : ku_ForumList_L.jsp
//  3. 개      요: 서비스 > 포럼게시판
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%

    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	box.put("leftmenu","01");  

	ArrayList list1 = null;
	ArrayList list2 = null;

    int		v_tabseq   = box.getInt("p_tabseq");
    int		v_seq      = box.getInt("p_seq");     
    String  v_process  = box.getString("p_process");

	String  s_gadmin   = box.getSession("gadmin");
	String  s_userid   = box.getSession("userid");
    String  s_username = box.getSession("name");
    
    //수정해야 함.
    //TODO 
    String  v_subjectseq = "1";
    //주제포럼인지 자율포럼인지 체크
    String v_types = "A";
    if ("SubjectFreeList".equals(v_process) )
    {
         v_types = "B";
    }else if( "SubjectReview".equals(v_process) ){
		 v_types = "C";
	}

	row = 5;
	if( "C".equals(v_types) ) row = 7;  // 운영자 추천리뷰의 경우 리스트만 있으므로 row = 7;

    // 주제어 가져오기   
    ArrayList listTheme = (ArrayList)request.getAttribute("monthTheme");
    //최다조회수 주제글글 가져오기
    ArrayList listSubject = (ArrayList)request.getAttribute("SubjectMax");     
    //최다덧글 가져오기
    ArrayList listComment = (ArrayList)request.getAttribute("SubjectCommentMax"); 
	// 운영자 리뷰 리스트
    //ArrayList listReview = (ArrayList)request.getAttribute("SubjectReview"); 
	
	// 지난주제 select 
	OpenForumUserBean bean = new OpenForumUserBean();
	ArrayList theme = bean.SelectAllTheme(box);

	System.out.println("theme "+theme.size());


	// 검색
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_select");

    int    v_cnt       = 0;            // 조회수
    String v_gubun     = "";           // 전체구분
	String v_startdate = "";           // 팝업공지시작일
    String v_enddate   = "";           // 팝업공지종료일
    String v_adtitle   = "";           // 제목
    String v_adcontent = "";           // 내용
    String v_addate    = "";           // 등록일
    String v_adname    = "";           // 등록자
	String v_luserid   = "";
    String v_gubun_view = "";
    String v_loginyn    = "";			// 로그인유무
    String v_useyn   	= "";			// 사용유무
    String v_grcodecd   = "";           // 대상 교육그룹
	int v_popwidth	 = 0;               // 팝업가로
    int v_popheight  = 0;               // 팝업세로
    int v_popxpos    = 0;               // 팝업x위치
    int v_popypos    = 0;               // 팝업y위치
    
    String v_popup   = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe= "";               // 프레임사용
    
    String v_upfile    ="";
    //String v_realfile  ="";
    
    Vector v_realfileVector = null;
    Vector v_savefileVector = null;
    Vector v_fileseqVector  = null;

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
	int v_rowcount	= 0;
    int v_upfilecnt = 0;
    int v_pageno    = box.getInt("p_pageno");

    list2 = (ArrayList)request.getAttribute("ListPage");

	String subject_s    = "";
	String subjectseq_s = "";
   
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topOpenkocca.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">

	//포럼 상세보기
    function selectForumView(seq, types) {

		v_url = "/servlet/controller.homepage.OpenForumUserServlet?p_process=selectView";
		document.Forumform1.p_seq.value = seq;
		document.Forumform1.p_types.value = types;
        document.Forumform1.action = v_url;
        document.Forumform1.submit();
    }

	// 포럼 글쓰기
     function selectForumInsert() {

		v_url = "/servlet/controller.homepage.OpenForumUserServlet?p_process=InsertPage";
        document.Forumform1.action = v_url;
        document.Forumform1.submit();
    }

	// 관리자 주제어 등록
	function insertsubjseq(){
		
		var url = "/servlet/controller.homepage.OpenForumUserServlet?p_process=insertThemePage";
		var v_win = window.open(url, "selfTest1"," left=20, top=20, width=396, height=343, menubar=no, scrollbars=no, resizable=yes");	
	}

	//포럼 상세보기
    function selectTheme() {
		document.Forumform1.p_process.value = "SubjectList";
        document.Forumform1.action = "/servlet/controller.homepage.OpenForumUserServlet";
        document.Forumform1.submit();
    }
    

</SCRIPT>

<form name="Forumform1" method="post">
          <input type=hidden name=p_seq value="">
          <input type=hidden name=p_process value="">
          <input type=hidden name=p_types value="<%=v_types%>">
          <input type=hidden name=p_subjectseq >           
         

<table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 열린마당 > 오프라인강좌</td>
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
                            <td height="7"></td>
                          </tr>                          
                          <tr> 
                            <td height="8"></td>
                          </tr>
                        </table>
<!-- 가장 많이본 시작 -->
						<table width="680" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="3">
								<% if ("A".equals(v_types)) { %>
									<img src="/images/user/kocca/openkocca/st_write.gif" >
                               	<%}else if("B".equals(v_types)){%>	
									<img src="/images/user/kocca/openkocca/st_freewrite.gif" >
                               	<%}else{%>
									<img src="/images/user/kocca/openkocca/st_master_view.gif" >
								<%}%>
							</td>
						  </tr>
                          <tr> 
                            <td height="3" colspan="3"></td>
                          </tr>
						  <tr>
							<td>
							  <%if( !"C".equals(v_types) ){%>
								<table width="330" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="5"><img src="/images/user/kocca/openkocca/st_see_review.gif" ></td>
									</tr>
									<tr>
										<td background="/images/user/kocca/openkocca/bg_st_forum.gif" align="center">
										<table width="310" border="0" cellspacing="0" cellpadding="0">											
											<tr>
												<td height="10"></td>
											</tr>
											
											 <% //조회수 많은 주제포럼
											 int i =0;
											  if (listSubject != null) {
												  
												  for(i = 0; i <listSubject.size(); i++) {
													        DataBox dbox    = (DataBox)listSubject.get(i);
													        String v_seq1         =   dbox.getString("d_seq");
                                                            String v_types1         =   dbox.getString("d_types");   
															String v_title         =   dbox.getString("d_title");
													        String v_indate      =  dbox.getString("d_indate");
													        String v_inuserid          =  dbox.getString("d_inuserid");
   													        String v_subjectnm    =  dbox.getString("d_subject");
													        
													 %>  
													 
																 
														<tr>
															<td width="12"><img src="/images/user/kocca/openkocca/ico_<%=i+1%>.gif" ></td>
															<td width="190" class="tbl_gleft04"><a href="javascript:selectForumView('<%=v_seq1%>','<%=v_types1%>');" ><%=StringManager.formatTitle(v_title,15)%></a></td>
															<td width="77" class="tbl_gleft05"><%=StringManager.formatTitle(v_subjectnm,8)%></td>
															<td width="1"><img src="/images/user/kocca/openkocca/vline.gif" ></td>
															<td width="50" class="tbl_gleft01"><%=v_inuserid%></td>
														</tr>		 
													 	<tr > 
															<td height="1" colspan="5" background="/images/user/kocca/common/dot_bg_9.gif"></td>
														</tr>
													 
													      
											 <%}
                                            }%>			 

											<% for(int j = i; j < 5 ; j++){%>			
														<tr>
															<td colspan="5" height=25 >&nbsp;</td>
														</tr>
														<tr > 
															<td height="1" colspan="5" background="/images/user/kocca/common/dot_bg_9.gif"></td>
														</tr>
         										<%}%>
										
											
											<tr>
												<td height="25" colspan="5" class="tbl_gleft06"><%=FormatDate.getFormatDate(FormatDate.getRelativeDate( FormatDate.getDate("yyyyMMdd") , -30),"yyyy.MM.dd") %> ~ <%=FormatDate.getDate("yyyy.MM.dd")%> 기준</td>
											</tr>
										</table>
										</td>
									</tr>
									
									<tr>
										<td colspan="5"><img src="/images/user/kocca/openkocca/st_forum_bo.gif" ></td>
									</tr>
								</table>								
							</td>
							<td width="20"></td>
<!-- 최다 덧글 시작 -->
							<td>
								<table width="330" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td ><img src="/images/user/kocca/openkocca/st_re_review.gif" ></td>
									</tr>
									<tr>
										<td background="/images/user/kocca/openkocca/bg_st_forum.gif" align="center">
										<table width="310" border="0" cellspacing="0" cellpadding="0">											
											<tr>
												<td height="10" colspan="5" ></td>
											</tr>
											 <% //조회수 많은 주제포럼
											  i =0;
											  if (listComment != null) {
												  
												  for(i = 0; i <listComment.size(); i++) {
													    DataBox dbox			= (DataBox)listComment.get(i);
													        String v_seq1		=   dbox.getString("d_seq");
                                                            String v_types1		=   dbox.getString("d_types");   
															String v_title		=   dbox.getString("d_title");
													        String v_indate		=  dbox.getString("d_indate");
													        String v_inuserid   =  dbox.getString("d_inuserid");
   													        String v_subjectnm  =  dbox.getString("d_subject");
													        
													 %>  
													 
																 
														<tr>
															<td width="12"><img src="/images/user/kocca/openkocca/ico_<%=i+1%>.gif" ></td>
															<td width="190" class="tbl_gleft04"><a href="javascript:selectForumView('<%=v_seq1%>','<%=v_types1%>');" ><%=StringManager.formatTitle(v_title,15)%></a></td>
															<td width="77" class="tbl_gleft05"><%=StringManager.formatTitle(v_subjectnm,8)%></td>
															<td width="1"><img src="/images/user/kocca/openkocca/vline.gif" ></td>
															<td width="50" class="tbl_gleft01"><%=v_inuserid%></td>
														</tr>		 
													 	<tr > 
															<td height="1" colspan="5" background="/images/user/kocca/common/dot_bg_9.gif"></td>
														</tr>
													 
													      
											 <%}
                                            }%>			 

											<% for(int j = i; j < 5 ; j++){%>			
														<tr>
															<td colspan="5" height=25 >&nbsp;</td>
														</tr>
														<tr > 
															<td height="1" colspan="5" background="/images/user/kocca/common/dot_bg_9.gif"></td>
														</tr>
         										<%}%>
											<tr>
												<td height="25" colspan="5" class="tbl_gleft06"><%=FormatDate.getFormatDate(FormatDate.getRelativeDate( FormatDate.getDate("yyyyMMdd") , -30),"yyyy.MM.dd") %> ~ <%=FormatDate.getDate("yyyy.MM.dd")%> 기준</td>
											</tr>
										</table>
										</td>
									</tr>
									<tr>
										<td colspan="2" ><img src="/images/user/kocca/openkocca/st_forum_bo.gif" ></td>
									</tr>
								</table>
							  </td>
						    </tr>						  
							<tr>
								<td colspan="2" height="30">
							<%}%></td> <!-- 운영자 추천리뷰일때는 회원 리뷰 안보이게 -->
							</tr>
						</table>

                        <!--게시판목록 -->
                        
                        <% if ( ("B".equals(v_types)) || ("C".equals(v_types)) ) { %>
   						<%}else{%>	
	   						 <table width="680" border="0" cellspacing="0" cellpadding="0" background="/images/user/kocca/openkocca/tit_monthly_subject.jpg">
								<tr height="52">
									<td width="100">&nbsp;</td>
									<td width="400" class="tbl_gleft01">
										<%
											if( listTheme != null ){
												for(int i = 0; i <listTheme.size(); i++) {
												DataBox dbox =(DataBox)listTheme.get(i);
												String v_theme = dbox.getString("d_subject");%>
										<%=v_theme%>
										<%		}	
											}%>
									</td>
										<%if( StringManager.substring(s_gadmin,0,1).equals("A") || s_gadmin.equals("B2") ){%>
									<td width="180" align="right">
										<a href="javascript:insertsubjseq();"><img src="/images/user/kocca/button/btn_j_register.gif"></a>&nbsp;&nbsp;</td>
										<%}%>
								</tr>
							</table>
   						<%}%>
                               						
                       
						<table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="5"><img src="/images/user/kocca/openkocca/board_first.gif" width="5" height="33"></td>
                            <td width="669" class="tbl_bgcolor_openkocca">
							<table width="669" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="30" class="tbl_grc"><img src="/images/user/kocca/openkocca/text_no.gif"></td>
								  <%if( "C".equals(v_types) ){%>
									<td width="70" class="tbl_grc"><img src="/images/user/kocca/openkocca/text_subject02.gif"></td>
									<td width="310" class="tbl_grc"><img src="/images/user/kocca/openkocca/text_subject02.gif"></td>
								  <%}else{%>
									<td width="364" class="tbl_grc"><img src="/images/user/kocca/openkocca/text_subject02.gif"></td>
								  <%}%>
                                    <td width="80" class="tbl_grc"><img src="/images/user/kocca/openkocca/text_id.gif"></td>
                                    <td width="80" class="tbl_grc"><img src="/images/user/kocca/openkocca/text_date.gif"></td>
                                    <td width="50" class="tbl_grc"><img src="/images/user/kocca/openkocca/text_recomm.gif"></td>
                                    <td width="50" class="tbl_grc"><img src="/images/user/kocca/openkocca/text_search.gif"></td>
                                </tr>
                              </table></td>
                            <td width="6"><img src="/images/user/kocca/openkocca/board_tail.gif" width="6" height="33"></td>
                          </tr>
                          <tr> 
                            <td height="3" colspan="4"></td>
                          </tr>
                          <tr> 
                            <td height="1" colspan="4" class="tbl_bgcolor_openkocca"></td>
                          </tr>
                        </table> 						

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                        
	                        
	                        <%
									int forumsize = 5;
									if( "C".equals(v_types) ) forumsize = 7;  // 운영자 추천리뷰의 경우 리스트만 있으므로 row = 7;

									int i = 0;	
										for(i = 0; i < list2.size(); i++) {
										
											DataBox dbox = (DataBox)list2.get(i);
											//v_gubun	   = dbox.getString("d_gubun");											
											v_seq      = dbox.getInt("d_seq");
											String v_types1      = dbox.getString("d_types");
											String v_types_nm	 ="";
												if( v_types1.equals("A") )      v_types_nm = "주제포럼";
												else if( v_types1.equals("B") ) v_types_nm = "자율포럼";
											v_cnt      = dbox.getInt("d_cnt");
											
											v_adtitle  = dbox.getString("d_title");
											v_addate   = dbox.getString("d_indate");
											String v_inuserid   = dbox.getString("d_inuserid");
											String v_inusernm   = dbox.getString("d_inusernm");
     										String v_recommend   = dbox.getString("d_recommend");
               										

											v_dispnum   = dbox.getInt("d_dispnum");
										    v_totalpage = dbox.getInt("d_totalpage");
										    v_rowcount  = dbox.getInt("d_rowcount");
										
								%>
									        <tr> 
									            <td width="40" class="tbl_grc" ><%=v_dispnum%></td>
									          <%if("C".equals(v_types)){%>
											    <td width="70"  class="tbl_gleft"><%=v_types_nm%></td>
											    <td name="p_seq" width="295"  class="tbl_gleft" value="<%=v_seq%>"><a href="javascript:selectForumView('<%=v_seq%>','<%=v_types1%>')"><%=v_adtitle%></a></td>
											  <%}else{%>
											    <td name="p_seq" width="365"  class="tbl_gleft" value="<%=v_seq%>"><a href="javascript:selectForumView('<%=v_seq%>','<%=v_types1%>')"><%=v_adtitle%></a></td>
											  <%}%>
									            <td width="100" class="tbl_grc" ><%=v_inuserid%></td>
									            <td  width="80" class="tbl_grc" ><%=FormatDate.getFormatDate(v_addate, "yyyy-MM-dd")%></td>
                           			            <td  width="50"class="tbl_grc" ><%=v_recommend%></td>									          
									            <td  width="50"class="tbl_grc" ><%=v_cnt%></td>
									        </tr>
									            <tr> 
													<td height="1" colspan="7" class="linecolor_main01"></td>
												  </tr>
									<%}%>
									
									<%  if (i == 0 ) {                         %>
									                        <tr> 
				                            <td colspan="6" class="tbl_grc">내용이 없습니다.</td>
				                          </tr>
				                          
												<tr> 
													<td height="1" colspan="7" class="linecolor_main01"></td>
												  </tr>
												  
									<%   }   %>
									<%
									for(int j = i; j < forumsize; j++){%>			
												      <tr> 
						                            <td colspan="7" class="tbl_grc">&nbsp;</td>
						                          </tr>
				                          
												<tr> 
													<td height="1" colspan="7" class="linecolor_main01"></td>
												  </tr>
         							<%}%>
         										
                                    
                          <tr> 
                            <td height="1" colspan="7" class="linecolor_main01"></td>
                          </tr>
                        </table>
						<table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td class="tbl_bgcolor_openkocca" height="3" colspan="2" class="linecolor_notice"></td>
                          </tr>
                          <tr> 
                            <td width="630" height="27" class="linecolor_notice">
							<!-- 페이지 -->
								<%= PageUtil.printPageListKocca(v_totalpage, v_pageno, row) %>
							<!-- 페이지 -->
							</td>
							  <td width="50" class="linecolor_notice">
							  <%if(!"C".equals(v_types)){%>
								<a href="javascript:selectForumInsert();"><img src="/images/user/kocca/button/btn_write.gif" border="0"></a>
							  <%}%>
							  </td>
                          </tr>
                          <tr> 
                            <td height="1" class="linecolor_main01" colspan="2" ></td>
                          </tr>
						  <tr> 
                            <td height="10" colspan="2" ></td>
                          </tr>
						  <tr> 
                            <td colspan="2" align="center">
							<%if("A".equals(v_types)){%>
							<table border="0" width="250" align="center" cellpadding="0" cellspacing="0">
                                <tr> 
                                  <td width="60" align="center">지난 주제</td>
                                  <td width="100"><select name="p_select" class="input2">
                                      <option value="">:: 선택하세요</option>
<%if(theme != null){
	for(i = 0; i < theme.size()-1; i++) {
	DataBox dbox = (DataBox)theme.get(i);
		 subject_s = dbox.getString("d_subject");
		 subjectseq_s = dbox.getString("d_subjectseq");%>
                                      <option value="<%=subjectseq_s%>"><%=subject_s%></option>
                                    
<%	}%>
								</select>
								  </td>
                                  <td width="40" width="50"><a href="javascript:selectTheme()"><img src="/images/user/kocca/button/btn_search.gif"  border="0"></a></td>
                                </tr>
<%}
%>	
                              </table>
							  <%}%>
							  </td>
                          </tr>
                        </table>
</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
