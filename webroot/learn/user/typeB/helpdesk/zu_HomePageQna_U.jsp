<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />

<script type="text/javascript">
	//데이터를 입력한다.
	function update() {
	
	    var frm = document.form1;
	
	    if (blankCheck(frm.p_title.value)) {
	        alert("제목을 입력하세요!");
	        frm.p_title.focus();
	        return;
	    }
	
	    if (realsize(frm.p_title.value) > 100) {
	        alert("제목은 한글기준 50자를 초과하지 못합니다.");
	        frmp_title.focus();
	        return;
	    }
	
	    if (frm.p_categorycd.options[frm.p_categorycd.selectedIndex].value == "") {
	        alert("분류를 선택하세요!");
	        frm.p_categorycd.focus();
	        return;
	    }
	
	    if ( document.getElementById("p_content").value == "" ) {
	        alert("내용을 입력해 주세요.");
	        document.getElementById("p_content").focus();
	        return;
	    }
	
	    frm.p_searchtext.value    = "";
	    frm.p_select.value        = "";
	    frm.p_selCategory.value   = "";
	    frm.p_pageno.value        =  1;
	
	    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	    document.form1.p_process.value = "update";
	    document.form1.submit();
	}
	
	//입력 내용의 공백을 체크
	function blankCheck( msg ) {
	    var mleng = msg.length;
	    chk=0;
	
	    for (i=0; i<mleng; i++) {
	        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
	    }
	    if ( chk == 0 ) return (true);
	
	    return (false);
	}
	function realsize( value ) {//글자수를 체크
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
</script>
<section>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <jsp:include page="/learn/user/typeB/include_left/left_4.jsp">
                    	<jsp:param value="${param.menuid }" name="left_active"/>
                    </jsp:include>
                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_header">
                                <span>QNA</span>
                                <div class="linemap_wrap">
                                    <ul>
                                        <li>
                                            <a href="#">
                                                <span>
                                                    <img src="/common/image/home_icon.png" alt="메인">
                                                </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#"><span>학습지원센터</span></a>
                                        </li>
                                        <li>
                                            <a href="#"><span>QNA</span></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="sub_contents_body">
                                <div class="sub_info_body">
                                    <p class="sub_course_view_title">과정소개</p>
                                    <div class="sub_course_view_wrap">
                                        <div class="info_box">
                                        	<form name="form1" enctype="multipart/form-data" method="post">
											    <input type="hidden" name="p_seq"           value="<c:out value="${selectQna.d_seq }"/>">
											    <input type="hidden" name="p_types"       	value="<c:out value="${selectQna.d_types }"/>">
    											<input type="hidden" name="p_userid"      	value="<c:out value="${sessionScope.userid }"/>">
    											
											    <input type="hidden" name="p_searchtext"    value="<c:out value="${param.p_searchtext }"/>">
											    <input type="hidden" name="p_select"        value="<c:out value="${param.p_select }"/>">
											    <input type="hidden" name="p_selCategory"   value="<c:out value="${param.p_sselCategory }"/>">
											    
											    <input type="hidden" name="p_pageno"        value="<c:out value="${param.p_pageno }"/>">
											    <input type="hidden" name="p_process"       value="">
											    
											    <input type="hidden" name="gubun" value="${param.gubun }" />
    											<input type="hidden" name="menuid" value="${param.menuid }" />
	                                            <table class="write_table th_align_left1">
	                                                <colgroup>
	                                                    <col width="25%">
	                                                    <col width="auto">
	                                                </colgroup>
	                                                <tbody>
	                                                    <tr>
	                                                        <th><span>*</span>작성자</th>
	                                                        <td><c:out value="${sessionScope.userid }" /></td>
	                                                    </tr>
	                                                    <tr>
	                                                        <th><span>*</span>공개여부</th>
	                                                        <td>
	                                                            <input type="radio" name="p_isopen" id="open_y" value="N" <c:if test="${selectQna.d_isopen eq 'N'}" > checked="checked"</c:if>>
	                                                            <label for="open_y">공개</label>
	                                                            <input type="radio" name="p_isopen" id="open_n" value="Y" <c:if test="${selectQna.d_isopen eq 'Y'}" > checked="checked"</c:if>>
	                                                            <label for="open_n">비공개</label>
	                                                        </td>
	                                                    </tr>
	                                                    <tr>
	                                                        <th><span>*</span>질문분류</th>
	                                                        <td>
	                                                            <select name="p_categorycd" id="p_categorycd">
	                                                            	<option value="">선택</option>
	                                                            	<c:forEach items="${categoryList }" var="list" varStatus="status">
	                                                                	<option value="<c:out value="${list.d_code }" />" <c:if test="${selectQna.d_categorycd eq list.d_code }" > selected</c:if> ><c:out value="${list.d_codenm }" /></option>
	                                                            	</c:forEach>
	                                                            </select>
	                                                        </td>
	                                                    </tr>
	                                                    <tr>
	                                                        <th><span>*</span>제목</th>
	                                                        <td><input type="text" name="p_title" id="p_title" style="width:100%" value="<c:out value="${selectQna.d_title }" />"></td>
	                                                    </tr>
	                                                    <tr>
	                                                        <th><span>*</span>내용</th>
	                                                        <td>
	                                                        	<textarea name="p_content" id="p_content">${selectQna.d_contents }</textarea>
	                                                        </td>
	                                                    </tr>
	                                                    <tr>
	                                                        <th>첨부파일</th>
	                                                        <td>
	                                                        	<%
	                                                        	DataBox dbox = (DataBox)request.getAttribute("selectQna");
	                                                        	Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
	                                                        	Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
	                                                        	Vector i_fileseqVector     = (Vector)dbox.getObject("d_fileseq");         // 파일 일련번호
	                                                        	
	                                                        	int    i_fileLimit         = HomePageQnaBean.getFILE_LIMIT();             // 제한 첨부 파일수
	                                                        	
	                                                        	String i_formName          = "form1";                                     // Form 이름
	                                                        	String i_ServletName       = "HomePageQNAServlet";                        // 저장 서블릿 이름
	                                                        	%>
	                                                        	<jsp:include page="/learn/user/typeB/include/multiAttach_include_QnA_U.jsp" />
	                                                        </td>
	                                                    </tr>
	                                                </tbody>
	                                            </table>
	                                        </form>
                                            <div class="course_button">
                                                <a href="javascript:history.back(-1);" class="list_btn">취소</a>
                                                <a href="javascript:update();" class="apply_btn">수정</a>
                                            </div>
                                        </div>
                                    </div>
                                 </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>


<!-- footer -->
<jsp:include page="/learn/user/typeB/include/newFooterB.jsp" />
<!-- footer -->
</body>
</html>