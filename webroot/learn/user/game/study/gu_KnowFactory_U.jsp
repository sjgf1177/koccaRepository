<%
//**********************************************************
//  1. 제      목: 지식공유 최근지식
//  2. 프로그램명: gu_KnowBoard_U.jsp
//  3. 개      요: 지식공유 최근지식 수정
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 06.01.03
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.dunet.common.util.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    int     v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");
    String p_categorycd = box.getString("p_categorycd");            // request 카테고리
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    int    v_cnt     = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_title   = "";
    String v_content = "";
    String v_categorycd = "";
    String v_categorynm = "";
    String v_types = "";

    String content = "";
    String width = "650";
    String height = "200";

    Vector v_realfileVector = null;          //      원파일명 배열
    Vector v_savefileVector = null;          //      서버에 저장되있는 파일명 배열
    Vector v_fileseqVector  = null;          //      저장된 파일번호 배열

    DataBox dbox = (DataBox)request.getAttribute("SelectView");
    if (dbox != null ) {
        v_seq         = dbox.getInt("d_seq");
        v_userid      = dbox.getString("d_userid");
        v_name        = dbox.getString("d_name");
        v_title       = dbox.getString("d_title");
        v_content     = dbox.getString("d_contents");
        v_cnt         = dbox.getInt("d_cnt");
        v_indate      = dbox.getString("d_indate");
        v_realfileVector = (Vector)dbox.getObject("d_realfile");
        v_savefileVector = (Vector)dbox.getObject("d_savefile");
        v_fileseqVector  = (Vector)dbox.getObject("d_fileseq");
        
        content = StringManager.replace(dbox.getString("d_contents"),"\n;","<br>");
        v_title = StringManager.replace(v_title, "`", "'");

        v_types      = dbox.getString("d_types");
        v_categorycd = dbox.getString("d_categorycd");
        v_categorynm = dbox.getString("d_categorynm");
    }

    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");
    int v_filecnt       = box.getInt("fielcnt"); // 최대 파일 첨부 갯수
%>

<script language="JavaScript" type="text/JavaScript">
<!--

//변경된 내용을 수정
function update() {
    if(document.all.use_editor[0].checked) {
        form1.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    }else {
        form1.content.value = document.all.txtDetail.value;
    }
    if (blankCheck(document.form1.p_title.value)) {
        alert("제목을 입력하세요!");
        document.form1.p_title.focus();
        return;
    }
    if (realsize(document.form1.p_title.value) > 200) {
        alert("제목은 한글기준 100자를 초과하지 못합니다.");
        document.form1.p_title.focus();
        return;
    }
    if (blankCheck(document.form1.content.value)) {
        alert("내용을 입력하세요!");
        document.form1.p_content.focus();
        return;
    }

            //파일 확장자 필터링
        var islimit = true;

        for(var i=1; i<=1; i++){
            var file = eval("document.form1.p_file1.value");

            if(file!="") {
                islimit = limitFile(file);

                if(!islimit) break;
            }
        }

        if(islimit) {
            document.form1.p_search.value     = "";
            document.form1.p_searchtext.value = "";
            document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
            document.form1.p_process.value = "update";
            document.form1.submit();
        }else{
            return;
        }

}

//리스트 화면으로 이동
function cancel() {
     document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
     document.form1.p_process.value = "ListPage";
     document.form1.submit();
}

//빈내용 체크
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;

    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);

    return (false);
}

//글자 사이즈 체크
function realsize( value ) {
    var len = 0;
    if ( value == null ) return 0;
    for(var i=0;i<value.length;i++){
        var c = escape(value.charAt(i));
        if ( c.length == 1 ) len ++;
        else if ( c.indexOf("%u") != -1 ) len += 2;
        else if ( c.indexOf("%") != -1 ) len += c.length/3;
    }
    return len;
}

//파일첨부에 쓰기 방지
function File_CancelKeyInput()
{
    if(event.keyCode == 9){ // Tab key만 허용
        return true;
    }else{
        alert('[찾아보기] 버튼을 이용해 첨부하실 파일을 선택하세요.\n\n경로를 수동으로 잘못 입력하실 경우 업로드가 제대로 이루어지지 않습니다.');
        return false;
    }
}


// 카테고리 선택
function openCategory(){
    window.self.name = "winSelectList";
    open_window("openwinSelectList","","100","100","390","400","","","","yes","");
    document.form1.target = "openwinSelectList";
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "OpenCategoryPage";
    document.form1.submit();
    document.form1.target = window.self.name;

}
//-->
</script>
          <!-- center start -->
 
<%@ include file="/learn/user/portal/include/topfactory.jsp"%>

	<div id="container">
		<div class="contentL">
			<!-- 좌측 콘텐츠 시작 -->
			<div><img src="/images/factory/show.gif"></div>
			<ul class="tabmenu">
				<li><img src="/images/factory/tab01over.gif" id="tab01" onclick="postchg('1');" alt="종합"></li>
				<li><img src="/images/factory/tab02.gif" id="tab02" onclick="postchg('2');" alt="방송영상"></li>
				<li><img src="/images/factory/tab03.gif" id="tab03" onclick="postchg('3');" alt="게임개발"></li>
				<li><img src="/images/factory/tab04.gif" id="tab04" onclick="postchg('4');" alt="문화컨텐츠"></li>
				<li><img src="/images/factory/tab05.gif" id="tab05" onclick="postchg('5');" alt="내가 작성한 글"></li>
			</ul>
			<!-- 쓰기 폼 시작 -->
			<form name = "form1" enctype = "multipart/form-data" method = "post">
		    <input type = "hidden" name = "p_process"     value="">			
			<input type = "hidden" name = "p_pageno"      value = "">
			<input type = "hidden" name = "p_userid"      value = "">
			<input type = "hidden" name = "p_brdno"       value = "<%=box.getInt("p_brdno") %>">
			<input type = "hidden" name = "p_rowseq"      value = "">
			<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
			<input type = "hidden" name = "p_searchtext"  value = "<%=v_searchtext%>">
			<input type = "hidden" name = "p_select"      value = "">
			<input type = "hidden" name = "p_brd_fg"       value = "">
    
			<table class="writeform" cellpadding="0" cellspacing="0">
				<colgroup><col width="150"><col width="*"></colgroup>
				
				<tr>
					<th class="colorA">제목</th>
					<td class="colorA noline"><input name="p_title" type="text" size="60" value="<%=dbox.getString("d_title") %>"></td>
				</tr>
				 <tr>
	             <td colspan="8" class="td con">
	                    <textarea id="p_content" name="p_content" style="display:none;"><%=StringManager.replace(dbox.getString("d_content"), "&", "&amp") %></textarea>
						<script type="text/javascript">
						    var CrossEditor = new NamoSE("contents");
						    var contentValue = document.getElementById("p_content").value; // Hidden 값 참조
						    CrossEditor.editorStart();
						    CrossEditor.SetUISize("650","400");
						    CrossEditor.SetBodyValue(contentValue); // 콘텐츠 내용 에디터 삽입
						</script>
	                </td>
	            </tr>            
							
			</table>
			 <!-- 파일첨부 시작 -->
            <%
            Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
            Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
            Vector i_fileseqVector     = (Vector)dbox.getObject("d_fileno");          // 파일 일련번호
            
            int    i_fileLimit         = v_filecnt;             // 제한 첨부 파일수
            
            String i_formName          = "form1";                                     // Form 이름
            String i_ServletName       = "CommunityFrBoardServlet";                   // 저장 서블릿 이름
            String i_boardStyle        = "study_write";                               // 입력 테이블 Class 명
            %>
            <%@ include file="/learn/user/portal/include/multiAttach_include.jsp" %>
            <!-- 파일첨부 종료 -->
            <div class="writefooter">
            <a href="javascript:update();" class="btn_gr"><span>수정</span></a><a href="javascript:cancel();" class="btn_gr"><span>취소</span></a>			
			</div>
			</form>	
			<!-- 쓰기 폼 끝 -->
			<!-- 좌측 콘텐츠 끝 -->
		</div>	
		
	<%@ include file="/learn/user/portal/include/rightmenu.jsp"%>
		<div class="clearL"><!-- no cut --></div>
	</div>
		
	<!-- 하단 시작 -->
	<div id="footer">
		<div class="about">
			<table class="inabout" cellpadding="0" cellspacing="0">
				<colgroup><col width="112"><col width="66"><col width="111"><col width="65"><col width="*"><col width="180"></colgroup>
				<tr>
					<td><a href="#"><img src="/images/common/btn_btm_01.gif" alt="개인정보처리방침"></a></td>
					<td><a href="#"><img src="/images/common/btn_btm_02.gif" alt="이용약관"></a></td>
					<td><a href="#"><img src="/images/common/btn_btm_03.gif" alt="이메일무단수집거부"></a></td>
					<td><a href="#"><img src="/images/common/btn_btm_04.gif" alt="사이트맵"></a></td>
					<td class="alignR"><img src="/images/common/txt_familysite.gif" alt="사이트맵"></td>
					<td class="alignL">
						<select>
              <option>관련사이트바로가기</option>
              <option value='N000001:7:N'>문화체육관광부</option>
              <option value='N000001:25:N'>한국콘텐츠진흥원</option>
              <option value='N000001:38:N'>콘텐츠 도서관</option>
              <option value='N000001:40:N'>글로벌게임허브센터</option>
              <option value='N000001:41:N'>문화콘텐츠 닷컴</option>
              <option value='N000001:43:N'>콘텐츠이용보호센터</option>
              <option value='N000001:44:N'>저작권위탁관리시스템</option>
              <option value='N000001:46:N'>콘텐츠식별체계(UCI)</option>
              <option value='N000001:47:N'>게임국가기술자격검증</option>
              <option value='N000001:48:N'>종합인력정보시스템</option>
              <option value='N000001:49:N'>음악아카이브</option>
              <option value='N000001:52:N'>보조사업비카드관리시스템</option>
            </select>
					</td>
				</tr>
			</table>
		</div>
		<table class="copyright" cellpadding="0" cellspacing="0">
			<colgroup><col width="*"><col width="64"><col width="64"><col width="59"><col width="63"></colgroup>
			<tr>
				<td class="alignL"><img src="/images/common/copyright.gif" alt="copyright"></td>
				<td class="alignR"><a href="#"><img src="/images/common/i_safe_mark.gif" alt="i-Safe"></a></td>
				<td class="alignR"><a href="#"><img src="/images/common/eprivacy_mark.gif" alt="ePRIVACY"></a></td>
				<td class="alignR"><a href="#"><img src="/images/common/gsc_mark.gif" alt="good Content Service"></a></td>
				<td class="alignR"><a href="#"><img src="/images/common/mark_circle_blue_s.gif" alt="클릭하시면 이니시스의 유효성을 확인 하실 수 있습니다."></a></td>
			</tr>
		</table>
	</div>
	<!-- 하단 끝 -->
</div>
</body>
</html>


