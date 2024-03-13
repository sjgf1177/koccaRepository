<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />
<script type="text/javascript">
function viewNotice(seq) {
    document.form1.action = "/servlet/controller.homepage.HomeNoticeServlet";
    document.form1.p_process.value = "selectView";
    document.form1.menuid.value = "07";
    document.form1.gubun.value = "4";
    document.form1.p_seq.value= seq;
    document.form1.target = "_self";
    document.form1.submit();
 }

//실무강좌 상세보기
function practicalView(seq) {

    document.form1.action = "/servlet/controller.infomation.PracticalCourseHomePageServlet";
    document.form1.p_process.value = "selectView";
    document.form1.menuid.value = "02";
    document.form1.gubun.value = "5";
    document.form1.p_seq.value= seq;
    document.form1.target = "_self";
    document.form1.submit();
}

//열린강좌 상세화면 페이지로 이동
function viewContent(seq, lecture_cls) {
    document.form1.p_prePage.value = "Main";
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.target="_self";
    document.form1.p_process.value = "selectView";
    document.form1.menuid.value = "00";
    document.form1.gubun.value = "5";
    document.form1.p_seq.value = seq;
    document.form1.p_lecture_cls.value = lecture_cls;
    document.form1.submit();
}

//정규강좌 상세화면 페이지 이동
function viewSubject(subj,subjnm,courseyn,upperclass,upperclassnm, year, subjseq, wj_classkey){
	document.form1.p_subj.value = subj;
	document.form1.p_subjnm.value = subjnm;
	document.form1.p_iscourseYn.value = courseyn;
	document.form1.p_upperclass.value = upperclass;
	document.form1.p_upperclassnm.value = upperclassnm;
	document.form1.p_year.value = year;
	document.form1.p_subjseq.value = subjseq;
	document.form1.p_wj_classkey.value = wj_classkey;
	document.form1.p_process.value = 'SubjectPreviewPage';
	document.form1.p_rprocess.value = 'SubjectList';
	document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
	document.form1.submit();
}

function toggleSubMenu(menuId, flag) {
    var objId = "gnb_depth" + menuId
    var obj = document.getElementById( objId );

    if (obj != null) {
        obj.style.display = (flag) ? "block" : "none";
    }

    var imgId = "oImg" + menuId;
    var imgObj = document.getElementById( imgId );
    imgObj.src = (flag) ? "/images/asp/type1/gnb_m0" + menuId + "_0.jpg" : "/images/asp/type1/gnb_m0" + menuId + ".jpg";
}
</script>

<form name="form1" method="post" id="form1">
    <input type="hidden" name="p_process" value="" />
    <input type="hidden" name="p_rprocess" value="" />
    <input type="hidden" name="menuid" value="" />
    <input type="hidden" name="gubun" value="" />
    <input type="hidden" name="p_seq" value="" />

    <input type="hidden" name="p_subj" value="" />
    <input type="hidden" name="p_subjnm" value="" />
    <input type="hidden" name="p_iscourseYn" value="" />
    <input type="hidden" name="p_upperclass" value="" />
    <input type="hidden" name="p_upperclassnm" value="" />
    <input type="hidden" name="p_year" value="" />
    <input type="hidden" name="p_subjseq" value="" />
    
    <input type="hidden" name="p_wj_classkey" value="" />
    
    <input type="hidden" name="p_lecture_cls" value="" />
    <input type="hidden" name="p_prePage"   value="" />
    <input type="hidden" name="aaa"   value="<c:out value='${param.p_id}'/>" />
</form>
<!-- body -->
	<c:choose>
		<c:when test="${param.gubun eq '1'}"><jsp:include page="/learn/user/typeB/homepage/zu_Online_ASP_login.jsp" /></c:when>
		<c:when test="${param.gubun eq '2'}"><jsp:include page="/learn/user/typeB/homepage/zu_Online_ASP_login1.jsp" /></c:when>
		<c:when test="${param.gubun eq '3'}"><jsp:include page="/learn/user/typeB/homepage/zu_Online_ASP_login2.jsp" /></c:when>
		<c:when test="${param.gubun eq '4'}"><jsp:include page="/learn/user/typeB/homepage/zu_Online_ASP_login3.jsp" /></c:when>
		<c:when test="${requestbox.gubun eq '4'}"><jsp:include page="/learn/user/typeB/homepage/zu_Online_ASP_login3.jsp" /></c:when>
		<c:when test="${param.gubun eq '10'}"><jsp:include page="/learn/user/typeB/homepage/zu_Online_ASP_IdPw.jsp" /></c:when>
		<c:when test="${param.gubun eq '70'}"><jsp:include page="/learn/user/typeB/homepage/zu_Online_ASP_Edit_Login.jsp" /></c:when>
		<c:when test="${param.gubun eq '80'}"><jsp:include page="/learn/user/typeB/homepage/zu_Online_ASP_pwChange.jsp" /></c:when>
		<c:when test="${param.gubun eq '88'}"><jsp:include page="/learn/user/typeB/homepage/zu_Online_ASP_Agree.jsp" /></c:when>
		<c:otherwise><jsp:include page="/learn/user/typeB/homepage/zu_Online_ASP_typeB.jsp" /></c:otherwise>
	</c:choose>
<!-- body -->
<!-- footer -->
<jsp:include page="/learn/user/typeB/include/newFooterB.jsp" />
<!-- footer -->
</body>
</html>
