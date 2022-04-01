<%@ page contentType="text/html; charset=euc-kr" import="java.util.*, javax.mail.*, javax.mail.internet.*, com.credu.library.*" %>
<%
boolean success = false;
Mailing mailing = new Mailing();
success = mailing.send("218.232.93.13","jhcho75@hanmail.net","조재형","jhcho75@hanmail.net","TEST","TESTTTTTTTTTTT");
if(success) {
%>
<script>
alert('메일이 정상적으로 발송되었습니다.');
history.back();
</script>
<%
} else {
%>
<script>
alert('메일 발송이 실패하였습니다!');
//history.back();
</script>
<%
}
%>