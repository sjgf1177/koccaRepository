<%
//**********************************************************
//  1. 제      목: 지식공유 최근지식
//  2. 프로그램명 : gu_KnowBoard_L.jsp
//  3. 개      요: 지식공유 최근지식 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 06.01.03
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.dunet.common.util.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />


<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

String v_title = "", v_contents = "", v_inuserid = "", v_indate = "" , v_types = "";
Vector v_realfileVector = null;
Vector v_savefileVector = null;
int    v_seq        = box.getInt("p_seq");
int    v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
int    v_pageno     = box.getInt("p_pageno");
String v_searchtext = box.getString("p_searchtext");            //  서버에 저장되있는 파일수 
String v_select     = box.getString("p_select"); 
String v_name = "";
String v_repstatus  = "";
String v_subjnm = "";
String tabimg1 ="tab01.gif";
String tabimg2 ="tab02.gif";
String tabimg3 ="tab03.gif";
String tabimg4 ="tab04.gif";
String tabimg5 ="tab05.gif";

DataBox dbox        = (DataBox)request.getAttribute("SelectView");

int v_commentseq    = 0;
 

v_inuserid          = dbox.getString("d_inuserid");
v_name              = dbox.getString("d_name");
v_types             = dbox.getString("d_types");
v_indate            = dbox.getString("d_indate");
v_title             = dbox.getString("d_title");
v_subjnm             = dbox.getString("d_subjnm");
v_seq               = dbox.getInt("d_seq");
v_contents          = dbox.getString("d_contents");
v_realfileVector    = (Vector)dbox.getObject("d_realfile");
v_savefileVector    = (Vector)dbox.getObject("d_savefile");
String v_categorynm = dbox.getString("d_categorynm");
int v_recommend = dbox.getInt("d_recommend");
int v_cnt       = dbox.getInt("d_cnt");
v_repstatus         = dbox.getString("d_okyn1");

String  v_categorycd= dbox.getString("d_categorycd").substring(0,2);
String s_userid     = box.getSession("userid");

int    v_filecount = v_realfileVector.size();

String p_categorycd = box.getString("p_categorycd");            // request 카테고리

/*
if(p_categorycd.equals("")){
    p_categorycd=v_categorycd;
}
*/
String v_process= box.getString("p_process");

if (p_categorycd.equals("CB")){
	tabimg2 ="tab02over.gif";
} else if (p_categorycd.equals("CG")){
	tabimg3 ="tab03over.gif";
} else if (p_categorycd.equals("CK")){
	tabimg4 ="tab04over.gif";
}else if (p_categorycd.equals("MINE")){
	tabimg5 ="tab05over.gif";
} else {
	tabimg1 ="tab01over.gif";
}
	
	

%>


<script language="JavaScript" type="text/JavaScript">
<!--

//리스트페이지로 이동
function selectList() {
   	document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
	document.form1.p_process.value = "ListPage";     
	document.form1.submit();	
}

//상세화면 페이지로 이동
function select(seq, upfilecnt, userid, types) {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value   = "selectView";
    document.form1.p_seq.value       = seq;
    document.form1.p_userid.value    = userid;
    document.form1.p_types.value     = types;
    document.form1.p_upfilecnt.value = upfilecnt;
    document.form1.submit();
}

//수정페이지로 이동
function updatePage() {
	document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
	document.form1.p_process.value = "updatePage";
	document.form1.submit();
}

//답변 등록 페이지로 이동
function replyPage() {
	document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
	document.form1.p_process.value = "replyPage";
	document.form1.submit();
}   

//꼬릿말 등록
function insertcomment() {
	if(document.form2.commentqna.value==""){
		alert("꼬릿말을 등록하세요");
		return;
	}
	document.form2.p_categorycd.value = document.form1.p_categorycd.value; // 카테고리
	document.form2.action = "/servlet/controller.homepage.KnowBoardUserServlet";
	document.form2.p_process.value = "commentInsert";
	document.form2.submit();
}

//뷰 화면 삭제
function deleteAction() {
	if (confirm("정말로 삭제하시겠습니까?")) {
		
		document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
		document.form1.p_process.value = "delete";
		document.form1.submit();
	}
	else {
		return;
	}
}

//꼬릿말 삭제
function deleteComment(v_commentseq) {
	if (confirm("정말로 삭제하시겠습니까?")) {
		document.form2.p_categorycd.value = document.form1.p_categorycd.value; // 카테고리
		document.form2.p_commentseq.value = v_commentseq;
		document.form2.action = "/servlet/controller.homepage.KnowBoardUserServlet";
		document.form2.p_process.value = "deleteComment";
		document.form2.submit();
	}
	else {
		return;
	}
}

// 추천하기
function InsertRecommend(seq) {
//alert(document.form1.p_categorycd.value);
	if (confirm("추천하시겠습니까?")) {
		document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
		document.form1.p_process.value = "insertRecommend";
		document.form1.submit();
	}
	else {
		return;
	}
}
//-->
</script>
<%@ include file="/learn/user/portal/include/topfactory.jsp"%>
	<!-- 상단 -->
	<div id="container">
		<div class="contentL">
		
			<!-- 좌측 콘텐츠 시작 -->
			<div><img src="/images/factory/show.gif"></div>
			<ul class="tabmenu">
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage"><img src="/images/factory/<%=tabimg1%>" id="tab01" alt="종합"></a></li>				
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=CB"><img src="/images/factory/<%=tabimg2%>" id="tab02"  alt="방송영상"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=CG"><img src="/images/factory/<%=tabimg3%>" id="tab03"  alt="게임개발"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=CK"><img src="/images/factory/<%=tabimg4%>" id="tab04"  alt="문화컨텐츠"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=MINE"><img src="/images/factory/<%=tabimg5%>" id="tab05"  alt="내가 작성한 글"></a></li>
			
			
				
				<!-- 
				<li><img src="/images/factory/tab01over.gif" id="tab01" onclick="postchg('1');" alt="종합"></li>
				<li><img src="/images/factory/tab02.gif" id="tab02" onclick="postchg('2');" alt="방송영상"></li>
				<li><img src="/images/factory/tab03.gif" id="tab03" onclick="postchg('3');" alt="게임개발"></li>
				<li><img src="/images/factory/tab04.gif" id="tab04" onclick="postchg('4');" alt="문화컨텐츠"></li>
				<li><img src="/images/factory/tab05.gif" id="tab05" onclick="postchg('5');" alt="내가 작성한 글"></li>
				 -->
			</ul>
			<form name = "form1" method = "post">
				
			<!-- 쓰기 폼 시작 -->
			<table class="viewform" cellpadding="0" cellspacing="0">
				
				<colgroup><col width="150"><col width="*"></colgroup>
				<tr>
					<th class="colorA">과정명</th>
					<td class="colorA noline"><p><%=v_subjnm%></p></td>
				</tr>
				<tr>
					<th class="colorA">제목</th>
					<td class="colorA noline"><strong><%=v_title%></strong></td>
				</tr>
				
				<tr>
		           <th class="colorA">첨부파일 </td>
		           <td class="colorA noline">
		            <%
		                if( v_realfileVector != null ) {
		                    for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
		
		                        String v_realfile = (String)v_realfileVector.elementAt(i);
		                        String v_savefile = (String)v_savefileVector.elementAt(i);
		
		                        if(v_realfile != null && !v_realfile.equals("")) {  %>
		                            &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>
		
		                            <%= v_realfile%></a><br>
		                            <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
		                <%      }   %>
		        <%          }
		                }
		        %>
		           </td>
		       </tr>
       
       
				<tr>
					<td colspan="2" class="colorC noline">
						<b>글쓴이</b> : <%=v_name%> &nbsp; &nbsp; <span>l</span> &nbsp; &nbsp;
						<b>조회수</b> : <%=v_cnt%> &nbsp; &nbsp; <span>l</span> &nbsp; &nbsp;
						<b>날짜</b> : <%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="colorB noline">
						<div class="question">
							<%=v_contents%>
						</div>
					</td>
				</tr>
				
				<input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
				<input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
				<input type = "hidden" name = "p_select"      value = "<%= v_select %>">
				<input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
				<input type = "hidden" name = "p_process"     value = "">
				<input type = "hidden" name = "p_userid"      value = "<%= v_inuserid %>">
				<input type = "hidden" name = "p_types"       value = "<%=v_types%>">
				<input type = "hidden" name = "p_categorycd"  value = "<%=p_categorycd%>">				
				<input type = "hidden" name = "p_upfilecnt"   value = "<%= v_upfilecnt %>">
	
	
	
<%    if(v_repstatus.equals("3")){ 

	  DataBox Ansbox = (DataBox)request.getAttribute("selectAns");
	    
		String v_anstitle      = "";
		String v_anscontents   = "";
		String v_ansinuserid   = "";
		String v_ansindate     = "";
		String v_ansname       = "";
		String v_anscategorynm = "";		
		
		if(Ansbox != null) {
		    v_ansinuserid          = Ansbox.getString("d_inuserid");
		    v_ansname              = Ansbox.getString("d_name");
		    v_ansindate            = Ansbox.getString("d_indate");
		    v_anstitle             = Ansbox.getString("d_title");
		    v_anscontents          = Ansbox.getString("d_contents");
		    v_anscategorynm        = Ansbox.getString("d_categorynm");
		    
		}

%>				
				<tr>
					<th class="colorA">답변</th>
					<td class="colorA noline">
						<div class="answer">
							<B><%=v_anstitle %></B><BR><BR> 
							<%=v_anscontents%>
							<table class="recom" cellpadding="0" cellspacing="0">
								<colgroup><col width="*"><col width="81"></colgroup>
								<tr>
									<td><a href="javascript:InsertRecommend('<%=v_seq%>');"><img src="/images/factory/btn_recom.gif" alt="추천하기"></a></td>
									<td><div class="recomtotal"><p><%=v_recommend%></p></div></td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				 <!-- 파일첨부 시작 -->
            <%
            Vector i_realfileVector    = (Vector)Ansbox.getObject("d_realfile");        // 실제 파일명
            Vector i_savefileVector    = (Vector)Ansbox.getObject("d_savefile");        // 저장 파일명
            String i_boardStyle        = "board_view";                                // 보드 스타일
            
             int i_fileCnt = i_realfileVector != null ? i_realfileVector.size() : 0 ; %>

            <%  if( i_realfileVector != null && i_realfileVector.size() > 0) { %>
            <%if( box.getSession("tem_grcode").equals("N000001")) { %>
         
            	<%if(i_realfileVector != null && !i_realfileVector.equals("") ){ %>
            		<tr>
						<th class="colorA">첨부파일</td>
						<td class="colorA noline">
							 <%      for(int i_i = 0; i_i < i_realfileVector.size(); i_i++) {      //     첨부파일 갯수에 따라
            
                        String v_realfile = (String)i_realfileVector.elementAt(i_i);
                        String v_savefile = (String)i_savefileVector.elementAt(i_i);      

                        if(v_realfile != null && !v_realfile.equals("")) {  %>
                        
	                    <img src="/images/portal/ico/ico_file.gif" alt="file" class="mg_r6 va_t" />
	                    <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a>
	                    <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
	                    
	                    <%              }   
                        }  %>
						</td>
					  </tr>
					 
					  <%} %>
					
            <%}else{ %>
           
           
            <tr >
                <th class="colorA">첨부파일</th>
                <td class="colorA noline">
            <%      for(int i_i = 0; i_i < i_realfileVector.size(); i_i++) {      //     첨부파일 갯수에 따라
            
                        String v_realfile = (String)i_realfileVector.elementAt(i_i);
                        String v_savefile = (String)i_savefileVector.elementAt(i_i);      

                        if(v_realfile != null && !v_realfile.equals("")) {  %>
                    <p>
                    <img src="/images/portal/ico/ico_file.gif" alt="file" class="mg_r6 va_t" />
                    <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a>
                    <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
                    </p>
            <%              }   
                        }  %>
            
                </td>
            </tr>
        
            <%} %>
            <input type = "hidden" name="p_upfilecnt"  value = "<%=i_fileCnt%>">
 <%        } }%>
 
 			<!-- 
            <!--%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %-
            --><!-- 파일첨부 종료-->
			
			</table>
			</form>
			
			<div class="viewfooter">
			
			<%if(s_userid.equals(v_inuserid)){	%>	
				<a href="javascript:updatePage()" target="_self" onfocus=this.blur()><img src="/images/factory/btn_update.gif"></a> 
				<a href="javascript:deleteAction()" target="_self" onfocus=this.blur()><img src="/images/factory/btn_del.gif"></a> 
			 <%
				}
			 %>  
 
			<a href="javascript:selectList()" target="_self" onfocus=this.blur()><img src="/images/factory/btn_list.gif" alt="목록" border=0></a>
			</div>
			
			<% ArrayList list = (ArrayList)request.getAttribute("selecCommentList"); %>
			<!-- 댓글달기 table -->
				  <form name = "form2" method = "post">
				  <input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
				  <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
				  <input type = "hidden" name = "p_select"      value = "<%= v_select %>">
				  <input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
				  <input type = "hidden" name = "p_process"     value = "">
				  <input type = "hidden" name = "p_userid"      value = "<%= v_inuserid %>">
				  <input type = "hidden" name = "p_types"       value = "<%=v_types%>">
				  <input type = "hidden" name = "p_commentseq" value ="">
				  <input type = "hidden" name = "p_categorycd" value ="">
			

			<table class="replylist" cellpadding="0" cellspacing="0">
				<colgroup><col width="16"><col width="*"><col width="80"></colgroup>
				<!-- 댓글 반본 구간 시작 -->
				<%if(list.size() != 0){
 
				for(int i = 0; i < list.size(); i++) {
                   dbox = (DataBox)list.get(i);  
                   v_inuserid      = dbox.getString("d_inuserid");
                   String c_name   = dbox.getString("d_name");
                   v_indate        = dbox.getString("d_cdate");
                   String v_commentqna    = dbox.getString("d_commentqna");
				   v_commentseq    = dbox.getInt("d_commentseq");
				%>

				<tr>
					<td class="meminfo"><img src="/images/factory/icon_reply.gif" align="absmiddle"></td>
					<td class="meminfo alignL"><b><%=v_inuserid%></b></td>
					<td class="meminfo alignC"><span><%=FormatDate.getFormatDate(v_indate, "yyyy-MM-dd")%></span></td>
				</tr>
				<tr>
					<td colspan="3" class="replytext"><%=v_commentqna%></td>
				</tr>
				<%}}%>
				<!-- 댓글 반복 구간 끝 -->
			</table>
			<div class="replywrite">
				<table class="inwrite" cellpadding="0" cellspacing="0">
					<colgroup><col width="*"><col width="96"></colgroup>
					<tr>
						<td>
							<textarea name="commentqna" onfocus="this.style.background='#ffffff'"></textarea>
							<p><b>0</b> / 300 byte</p>
						</td>
						<td><a href="javascript:insertcomment()"><img src="/images/factory/btn_replywrite.gif"></a></td>
					</tr>
				</table>
			</div>
			<!-- 쓰기 폼 끝 -->
		<!-- 좌측 콘텐츠 끝 -->
		</div>
		<%@ include file="/learn/user/portal/include/rightmenu.jsp"%>	
		<div class="clearL"><!-- no cut --></div>
	</div>
	<!-- 하단 시작 -->
	<div id="footer">
		<%@ include file="/learn/user/portal/include/footerfactory.jsp"%>	
	</div>
	<!-- 하단 끝 -->
</div>
</body>
</html>
