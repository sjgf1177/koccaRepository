<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.lang.String" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<%

    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    box.setSession("tem_grcode","N000041");
    box.setSession("gadmin","ZZ");
//    box.setSession("userid",box.getString("p_userid"));

%>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
<!--

// userid 존재 여부 체크
function kocscUseridCheck(userid){
alert("test");
	document.form1.p_userid.value = userid;
	document.form1.action = "/servlet/controller.contents.EduStart";
	document.form1.p_process.value = "KOCSC_Userid_Check";
	document.form1.submit();
}

// 학습창 OPEN
function studyOpen(userid, subj, year, subjseq, contenttype, aspgubun, iurl) {
	var url = '/servlet/controller.contents.EduStart?p_subj='+subj+'&p_year='+year+'&p_subjseq='+subjseq+'&contenttype='+contenttype+'&p_aspgubun='+aspgubun+"&p_userid="+userid;
//	alert("url : "+url+"/ iurl : "+iurl);

	if( userid == "" || userid == null ){
		alert("잘못된 접근 입니다.");
//		location.href="http://edu.kocca.or.kr/kocsc";
	} else {

		// userid 체크..
//		kocscUseridCheck(userid);
			
		<% if (box.getSession("gadmin").equals("ZZ")) {  %>
		        document.form1.lessonReurl.value = url;
		        document.form1.lessonReiurl.value = iurl;
		        var s_url = url+"&p_iurl="+iurl;
		//alert(s_url);
		        if (url == '') {
		            alert("정보가 없습니다");
		        } else {
		            if (iurl == 0){
		                open_window('openApp',s_url,0,0,0,0,false,false,true,true,true);
		                //setTimeout("win_close()", 1000);
		            	//win_close();
		                //self.close();
		            } else {
		                open_window('openApp',s_url,0,0,1024,768,false,false,'yes','yes','yes');
		                //win_close();
		                //self.close();
		            }
		        }
		<%  } else {                                      %>
		        alert ("학습자 권한이 아닙니다.");
		<%  }                                             %>
	}
}

function win_close(){
	top.window.opener = top;
	top.window.open('','_parent','');
	top.window.close();
}

//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<body onload="studyOpen('<%=box.getString("p_userid") %>','<%=box.getString("p_subj") %>','<%=box.getString("p_year") %>','<%=box.getString("p_subjseq") %>','<%=box.getString("contenttype") %>','<%=box.getString("p_aspgubun") %>','0')">
<form name="form1"  action="" method="post">
    <input type='hidden' name='p_process' value="">
    <input type='hidden' name='p_userid' value="">
    <!-- 학습창 다시띄우기용 1:다시 안띄우기  2:다시띄우기(학습컨텐츠에서 닫았을경우) -->
    <input type='hidden' name='lessonRepopup' value="1">
	<input type='hidden' name='lessonReurl' value="">
    <input type='hidden' name='lessonReiurl' value="">

		<table>
			<tr>
			    <td></td>
			</tr>
		</table>

</form>
</body>
</html>


