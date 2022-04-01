<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "com.credu.library.*,com.credu.library.RequestBox" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>2012년 한국콘텐츠아카데미 교육생 모집 안내</TITLE>
<script language="javascript">
	function popup(num){
		window.open('http://edu.kocca.or.kr/contents/temp/academy/'+num+'.html','','width=705, height=700, resizable=no, toolbar=no, scrollbars=yes, menubar=no');
	}

	function popup2(){
		//window.open('/servlet/controller.homepage.HomeNoticeServlet?p_process=selectView&menuid=07&gubun=4&p_seq=1438&tem_grcode=N000001'
		
		opener.name = "form1";

		parent.opener.document.form1.target = opener.name;
		parent.opener.document.form1.action = "/servlet/controller.homepage.HomeNoticeServlet";

		parent.opener.document.form1.gubun.value = "4";
		parent.opener.document.form1.menuid.value = "07";
		parent.opener.document.form1.p_process.value   = "selectView";
		parent.opener.document.form1.p_seq.value       = "1438";
		//parent.opener.document.form1.tem_grcode.value       = "N000001";
		parent.opener.document.form1.submit();
		self.close();

	}

</script>
</HEAD>

<BODY leftmargin="0" topmargin="0" style="overflow-x:hidden;overflow-y:hidden">
<img src="http://edu.kocca.or.kr/contents/temp/academy/images/main.jpg" width="400" height="500" border="0" usemap="#Map">
<map name="Map">
	<area shape="rect" coords="35,210,185,230" href="javascript:popup(1)">
	<area shape="rect" coords="35,230,185,250" href="javascript:popup(2)">
	<area shape="rect" coords="35,250,185,270" href="javascript:popup(3)">
	<area shape="rect" coords="35,270,185,290" href="javascript:popup(4)">
	<area shape="rect" coords="205,210,355,230" href="javascript:popup(5)">
	<area shape="rect" coords="205,230,355,250" href="javascript:popup(6)">
	<area shape="rect" coords="205,250,355,270" href="javascript:popup(7)">
	<area shape="rect" coords="205,270,355,290" href="javascript:popup(8)">
	<area shape="rect" coords="35,354,185,374" href="javascript:popup(9)">
	<area shape="rect" coords="35,374,185,394" href="javascript:popup(10)">
	<area shape="rect" coords="35,394,185,414" href="javascript:popup(11)">
	<area shape="rect" coords="35,414,185,434" href="javascript:popup(12)">
	<area shape="rect" coords="205,350,355,370" href="javascript:popup(13)">
	<area shape="rect" coords="205,420,355,440" href="javascript:popup(14)">
	<area shape="rect" coords="235,460,365,485" href="javascript:popup('else')">
	<area shape="rect" coords="145,325,185,345" href="javascript:popup2()">
</map>
</BODY>
</HTML>

