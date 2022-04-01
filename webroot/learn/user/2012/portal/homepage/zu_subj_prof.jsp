<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = null;
box = (RequestBox)request.getAttribute("requestbox");
if (box == null) {
    box = RequestManager.getBox(request);
}
String area=box.get("p_area");
ArrayList<DataBox> subjectList = (ArrayList) request.getAttribute("SubjectList");
%>

<%@ include file="/learn/user/portal/include/site_info.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>한국콘텐츠아카데미</title>
<!-- <link rel="stylesheet" type="text/css" href="/css/portal/main.css" /> -->
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script type="text/javascript" src="/namo_cross/js/namo_scripteditor.js"></script> 
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/user_patch.js'></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>  
</head>
<body>
<!-- header -->
<script language="javascript">

	// 학습창 미리보기
    function whenViewEdu(url){
    	document.form1.lessonReurl.value = url;
		document.form1.lessonReiurl.value = "";
        window.open(url,'EduStart','width=800,height=600');
    }

    function studyOpenRe() {
    	document.form1.lessonRepopup.value = "1";
    	var rurl = document.form1.lessonReurl.value;
    	var riurl = document.form1.lessonReiurl.value;
    	setTimeout("whenViewEdu('"+rurl+"');" ,100);
    }

    function gotoChange()
    {
        document.frm1.submit();
    }
</script>

<%@ include file="/learn/user/portal/include/common.jsp" %>

<div id="header">
    <!-- Top Warp 시작 -->
	<%@ include file="/learn/user/portal/include/topWarp.jsp" %>
	<!-- Top Warp 종료 -->
	
	<!-- Top Menu 시작 -->
	<%@ include file="/learn/user/portal/include/topMenu.jsp" %>
    <!-- Top Menu 종료 -->	

    <!-- Top Visual 시작 -->
    <%@ include file="/learn/user/portal/include/topVisual.jsp" %>
    <!-- Top Visual 종료 -->
</div>

	<form name="form1" method="post" action="">
	<!-- 학습창 다시띄우기용 1:다시 안띄우기  2:다시띄우기(학습컨텐츠에서 닫았을경우) -->
	    <input type='hidden' name='lessonRepopup' value="1">
	    <input type='hidden' name='lessonReurl' value="">
	    <input type='hidden' name='lessonReiurl' value="">
	</form>

    <form action="/servlet/controller.homepage.MainServlet" method="post" name="frm1">
        <input type="hidden" name="gubun" value="0">
        <input type="hidden" name="p_auth" value="P101">
        <input type="hidden" name="p_process" value="authChange">
        
        <table border="0" cellpadding="0" cellspacing="0" width="1000px" style="margin:0 auto;margin-top:55px">
            <tr>
                <td align="right">구분 :
                    <select name="p_area" onchange="gotoChange()">
                        <option value="T" <% if (area.equals("T")) out.print("selected"); %>>전체</option>
                        <option value="B0" <% if (area.equals("B0")) out.print("selected"); %>>방송</option>
                        <option value="G0" <% if (area.equals("G0")) out.print("selected"); %>>게임</option>
                        <option value="K0" <% if (area.equals("K0")) out.print("selected"); %>>문콘</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
	<table class="row_list" style="width:1000px;margin:0 auto;">
			<thead>
			<tr>
				<th>NO</th>
                <th>구분</th>
				<th>
					과정명
				</th>
				<th>학습하기</th>
			</tr>
			</thead>
			<tbody>
            <% for(int i=0;i<subjectList.size();i++)
            {
                DataBox dbox =  subjectList.get(i);
                String year=dbox.get("d_scyear");
                String subj=dbox.get("d_subj");
                String subjseq=dbox.get("d_subjseq");
                String area_nm=dbox.get("d_area_nm");

                out.print("<tr>");
                out.print("<td>");
                out.print((i+1));
                out.print("</td>");
                out.print("<td>");
                out.print(area_nm);
                out.print("</td>");
                out.print("<td align='left'>");
                out.print(dbox.get("d_subjnm"));
                out.print("</td>");
                out.print("<td>");
                out.print("<a href=\"javascript:whenViewEdu('/servlet/controller.contents.EduStart?p_year="+year+"&p_subjseq="+subjseq+"&p_subj="+subj+"');\">보기</a>");
                out.print("</td>");
                out.print("</tr>");
            }                 %>
			</tbody>
	</table>

    <!-- Footer Menu 시작 -->
    <%@ include file="/learn/user/portal/include/footer_include.jsp" %>
    <!-- Footer Menu 종료 -->

</body>
</html>
<!--// footer -->


