<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import="com.credu.homepage.LinkSiteBean" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process  = box.getString("p_process");

    HashMap<String, String> upperMap = null;    // 대분류 별 메뉴ID 매핑 맵 HashMap<upperlcass, menuid>

    upperMap   = ClassifySubjectBean.getMenuId(box);

    String authority=box.getString("authority");

    //관련부서 사이트
    LinkSiteBean site = new LinkSiteBean();
    ArrayList bolist = site.SelectList(box);

    String naviType=box.getSession("tem_menu_type");  //메뉴 네비게이션 타입
    String mainType=box.getSession("tem_main_type");  //메인 화면 타입

    String menuid1=box.getString("menuid");
    
%>
<%@ include file="/learn/user/portal/include/topMainAsp.jsp"%>
<%if(!newAsp){%>
<%@ include file="zu_Online_ASP_Script.jsp"%>
<%}%>


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
    
    <input type="hidden" name="p_lecture_cls" value="" />
    <input type="hidden" name="p_prePage"   value="" />
    
    
    
    <%if(newAsp){%>
    	<!-- body -->
    	<% if(box.getString("gubun").equals("1")) {%>
            <jsp:include page='<%="/learn/user/portal/homepage/zu_Online_ASP_login.jsp"%>' />
        <% } else if(box.getString("gubun").equals("2")) {%>
            <jsp:include page='<%="/learn/user/portal/homepage/zu_Online_ASP_login1.jsp"%>' />
        <% } else if(box.getString("gubun").equals("3")) {%>
            <jsp:include page='<%="/learn/user/portal/homepage/zu_Online_ASP_login2.jsp"%>' />
        <% } else if(box.getString("gubun").equals("4")) {%>
            <jsp:include page='<%="/learn/user/portal/homepage/zu_Online_ASP_login3.jsp"%>' />
        <% } else if(box.getString("gubun").equals("10")) {%>
            <jsp:include page='<%="/learn/user/portal/homepage/zu_Online_ASP_IdPw.jsp"%>' />
        <% } else if(box.getString("gubun").equals("70")) {%>
            <jsp:include page='<%="/learn/user/portal/homepage/zu_Online_ASP_Edit_Login.jsp"%>' />
        <% } else { %>
		    <jsp:include page='<%="/learn/user/portal/homepage/zu_Online_ASP_type"+mainType+".jsp"%>' flush="true">
				<jsp:param name="ser_nm" value="<%= ser_nm %>" />
				<jsp:param name="rejectResult" value="<%= rejectResult %>" />
			</jsp:include>
		<% }  %>
	    <!-- body -->
	    
	    <!-- footer -->
	    <jsp:include page='<%="/learn/user/portal/include/newFooter"+mainType+".jsp"%>' />
	    <!-- footer -->
    <%}else{%>
    
		<%@ include file="/learn/user/portal/homepage/zu_Online_ASP_Old.jsp" %>
    <%}%>

    
</form>
</body>
</html>
