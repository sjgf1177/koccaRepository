<%
//**********************************************************
//  1. 제      목: SCORM MasterForm Upper Frame
//  2. 프로그램명: z_EduStart_fup_OBC.jsp
//  3. 개      요: SCORM MasterForm Upper Frame
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 06
//  7. 수      정:
//*********************************************************** 
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }

    String	s_userid	= box.getSession("userid");    
    String	s_name		= box.getSession("name");
    String  s_subj   	= box.getSession("s_subj");
    String  s_year   	= box.getSession("s_year");
    String  s_subjseq  	= box.getSession("s_subjseq");
	String	s_gadmin	= box.getSession("gadmin");
   

	MasterFormData	da	=(MasterFormData)request.getAttribute("MasterFormData");
	String	v_ismfbranch 	= da.getIsmfbranch();
	String 	v_dir			= da.getDir();
	String	v_contenttype	= da.getContenttype();
	String	v_isconfirmed	= "N";
	int		v_mybranch		= EduEtc1Bean.get_mybranch(s_subj, s_year, s_subjseq, s_userid);
	if (v_mybranch!=99)	v_isconfirmed = "Y";
	
	if(v_mybranch != 99)	v_isconfirmed = "Y";
	
	String	v_base_url		= "/servlet/controller.contents.EduStart?p_process=";
	String	v_eduChkURL	= v_base_url + "eduCheck&p_lesson=";	//진도체크URL
	String	v_examURL	= "/servlet/controller.exam.ExamServlet?p_process=UserResultListPage&p_lesson=";		//평가URL
	String	v_sulURL	= "/servlet/controller.research.SulmunServlet?p_process=UserResultListPage&p_f_gubun=C";//설문URL
	String	v_brCntlURL = v_base_url + "branchControl";			//분기제어URL
	String	v_eduMemURL = v_base_url + "eduMemList";			//동료학습자보기URL
	String	v_url_gong	= "/servlet/controller.study.SubjGongStudyServlet?p_process=select&p_subj="
						+ s_subj+"&p_year="+s_year+"&p_subjseq="+s_subjseq;
	
	String	v_imgURL	= EduEtc1Bean.make_eduURL(s_subj,"DOC")+"docs/menuimg";	//image Base URL
	
%>

<html>
<head>
<title>Education...</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<!-- CopyRight. Credu.Corp.  written by LeeSuMin -->

	<script language=javascript>
		var curmodule ="";
		var curlesson ="";
		var curobject ="";
		var v_starting  ="";
		var curidx = 0;
		var optStr = "";
		var arrStr = "";
		var npage = 0;

		function out(){
			top.out();
		}
		function starting(m,l,o){
			jumpTo(m,l,o);
		}
		function eduStart(m,l,o){
			jumpTo(m,l,o);
		}
		function uWin(url){
			window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=700,height=600").focus();
		}
		function gotoGong(){
			top.etree_fset.ebody.location="<%=v_url_gong%>";
		}
		function goExNext(){
			top.etree_fset.etree.goNext();
		}
		function goNext(){
			top.etree_fset.etree.goNext();
		}
		function goBack(){			
			top.etree_fset.etree.goBack();
		}
			
		function goPrev(){
			top.etree_fset.etree.goPrev();
		}
		function jumpTo(mod,les,oid){
		//alert(mod+","+les+","+oid);
		//return;
			top.etree_fset.etree.whenStart(mod,les,oid);
		}
		function setVals(m,l,o,i){
			document.curr.mod.value=m;
			document.curr.les.value=l;
			document.curr.obj.value=o;
			document.curr.idx.value=i;
		}
		/*
		function jumpTo(les){
			top.etree_fset.etree.goNext();
		}
		*/
		function memList(){
			alert("NULL>>>");
			//	uWin("zedu_stlist.list");
		}
		function reflect(){
			alert("NULL>>>");
			//uWin("zedu_reflect.list?p_subj='||p_subj||'&'||'p_year='||p_year||'&'||'p_subjseq='||p_subjseq||'");
		}

	</script>
	</head>
	
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <table width='100%' border=0 cellpadding=0 cellspacing=0 background='<%=v_imgURL%>/top02.gif'>
        <tr>
            <td width='397'><img src='<%=v_imgURL%>/top01.gif' width=397 height=60></td>
            <td>&nbsp;</td>
            <td width='300'><img src='<%=v_imgURL%>/top03.gif' width=300 height=60></td>
        </tr>

<!--
	<table  border="0" cellpadding="0" cellspacing="0" width="100%" align="LEFT" valign="TOP" marginwidth="0" marginheight="0" leftmargin="0" topmargin="0"  background="<%=v_imgURL%>/title2.gif">
		

		<TR>

			<TD align="left" valign=top>
				<img src="<%=v_imgURL%>/title1.gif" valign=top border=0>
			</TD>
			<TD valign=top align="right">
			<img src="<%=v_imgURL%>/title3.gif" valign=top border=0>
			</TD>

			<TD align="left" valign=top>
				<img src="<%=v_imgURL%>/title.gif" valign=top border=0>
			</TD>


		</TR>
-->
        <form name="sel">
        </form>
		<form name="curr">
			<input type=hidden  name="mod"  value="00">
			<input type=hidden  name="les"  value="00">
			<input type=hidden  name="obj"  value="00">
			<input type=hidden  name="idx"  value="0">
		</form>
	</TABLE>
	</body>
</html>
