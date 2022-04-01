<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String v_userid = box.getStringDefault("userid", "lee1");
    String v_name   = box.getStringDefault("name"  , "이정한");
    String v_email  = box.getStringDefault("email" , "");
    String v_gadmin = box.getStringDefault("p_auth", "A1");
    String v_comp   = box.getStringDefault("comp"  , "0101010900");
    String v_jikup  = box.getStringDefault("jikup" , "C");
    String v_cono   = box.getStringDefault("cono"  , "19953527");

    box.setSession("userid", v_userid);
    box.setSession("name",   v_name);
    box.setSession("email",  v_email);
    box.setSession("gadmin", v_gadmin);
    box.setSession("comp",   v_comp);
    box.setSession("jikup",  v_jikup);
    box.setSession("cono",   v_cono);

/*
	box.setSession("userid", "lee1");
    box.setSession("name",   "이정한");
    box.setSession("email",  "");
    box.setSession("gadmin", "A1");
    box.setSession("comp",   "0101010900");
    box.setSession("jikup",  "C");
    box.setSession("cono",   "19953527");

*/
    v_userid = StringManager.chkNull((String)box.getSession("userid"));
    v_name   = StringManager.chkNull((String)box.getSession("name"));
    v_email  = StringManager.chkNull((String)box.getSession("email"));
    v_gadmin = StringManager.chkNull((String)box.getSession("gadmin"));
    v_comp   = StringManager.chkNull((String)box.getSession("comp"));
    v_jikup  = StringManager.chkNull((String)box.getSession("jikup"));
    v_cono   = StringManager.chkNull((String)box.getSession("cono"));

%>


<HTML>
<HEAD>
<TITLE> [CREDU Z-PACK] </TITLE>
<!--link rel = "StyleSheet" href = "/css/sub_03.css"-->
<script language = "javascript">
    function adminOpenPage() {
        window.self.name = "xxxxxxxxxx";     //opener 객체에 name 을 지정한다

        window.open("", "openWinAdmin", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 1024, height = 768, top=0, left=0");
        document.form1.target = "openWinAdmin"
        document.form1.action = "/learn/admin/system/mScreenFset.jsp";
        document.form1.submit();

        document.form1.target = window.self.name;        
//      this.close();
    }

    function sampleBulletinPage() {
        document.form1.target="_blank";
        document.form1.action = "/servlet/controller.sample.BulletinServlet";
        document.form1.p_process.value = "";
        document.form1.submit();
    }


	function  selectChange() {
        document.form1.submit();
	}
</script>
</HEAD>
<BODY leftmargin=0 topmargin=0 marginwidth=0 marginheight=0 text="#000000" >

<form name = "form1" method = "post">
<input type="hidden" name="p_process">
<br><br><br><br><br>


<br>

<p align=center>
   <b>버튼을 누르면 관리자 화면으로 이동합니다...</b><br><br><br>
<select name = "p_auth" onChange="javascript:selectChange()">
	<option value='A1' <% if (v_gadmin.equals("A1")) {%> selected <%}%>>UltraVisor</option>
	<option value='A2' <% if (v_gadmin.equals("A2")) {%> selected <%}%>>SuperVisor</option>
	<option value='F1' <% if (v_gadmin.equals("F1")) {%> selected <%}%>>과정관리자</option>
	<option value='H1' <% if (v_gadmin.equals("H1")) {%> selected <%}%>>교육그룹관리자</option>
	<option value='K2' <% if (v_gadmin.equals("K1")) {%> selected <%}%>>회사담당자</option>
	<option value='P1' <% if (v_gadmin.equals("P1")) {%> selected <%}%>>강사</option>
	<option value='U1' <% if (v_gadmin.equals("U1")) {%> selected <%}%>>고용보험</option>
</select>

<!--
	<option value='K6' <% if (v_gadmin.equals("K2")) {%> selected <%}%>>부서관리자</option>
	<option value='K7' <% if (v_gadmin.equals("K7")) {%> selected <%}%>>부서장</option>


-->
<input type='hidden' name='userid' value='<%=v_userid%>'>
<input type='hidden' name='name' value='<%=v_name%>'>
<input type='hidden' name='email' value='<%=v_email%>'>
<input type='hidden' name='comp' value='<%=v_comp%>'>
<input type='hidden' name='jikup' value='<%=v_jikup%>'>
<input type='hidden' name='jikup value='<%=v_cono%>'>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <INPUT   TYPE="button"   NAME="운영자화면"   VALUE="운영자화면"   onClick="javascript:adminOpenPage()">
   <br><br>
</p>
<p align=center>
   <INPUT   TYPE="button"   NAME="샘플게시판"   VALUE="샘플게시판"   onClick="javascript:sampleBulletinPage()">
   <br><br>
</p>

</form>
<br><br>
<p align=center>
현재 설정되어있는 세션값
<br><br>

<pre>
    userid =  <%= v_userid%>
    name   =  <%= v_name%>
    email  =  <%= v_email%>
    gadmin =  <%= v_gadmin%>
    comp   =  <%= v_comp%>
    jikup  =  <%= v_jikup%>
    cono   =  <%= v_cono%>
</pre>

</p>
<SCRIPT LANGUAGE="JavaScript">
<!--
//adminOpenPage();
//-->
</SCRIPT>
</BODY>
</HTML>

