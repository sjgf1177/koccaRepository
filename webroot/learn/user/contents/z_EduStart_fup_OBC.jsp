<%
/**
 * file name : z_EduStart_fup_OBC.jsp
 * date      : 2003/10/24
 * programmer: LeeSuMin
 * function  : OBC MasterForm Upper Frame
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/website/common/error.jsp" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
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
   

//	MasterFormData	da	=(MasterFormData)request.getAttribute("MasterFormData");

//	String	v_subj			= da.getSubj();
	String	v_subj			= s_subj;
	String	v_ismfbranch 	= box.getSession("s_ismfbranch");
	String	v_contenttype	= box.getSession("s_contenttype");
	String	v_isconfirmed	= "N";
//	int		v_mybranch		= EduEtc1Bean.get_mybranch(s_subj, s_year, s_subjseq, s_userid);
//	if (v_mybranch != 99)	v_isconfirmed = "Y";
	
	String	v_base_url		= "/servlet/controller.contents.EduStart?p_process=";
	String	v_eduChkURL	= v_base_url + "eduCheck&p_lesson=";	//진도체크URL
	String	v_examURL	= "/servlet/controller.exam.ExamServlet?p_process=UserResultListPage&p_lesson=";		//평가URL
	String	v_sulURL	= "/servlet/controller.research.SulmunServlet?p_process=UserResultListPage&p_f_gubun=C";//설문URL
	String	v_brCntlURL = v_base_url + "branchControl";			//분기제어URL
	String	v_eduMemURL = v_base_url + "eduMemList";			//동료학습자보기URL
	
	String	v_url_gong		= v_base_url+"fmain&p_subj="+s_subj+"&p_year="+s_year+"&p_subjseq="+s_subjseq;
	//String	v_url_gong	= "/servlet/controller.study.SubjGongStudyServlet?p_process=select&p_subj="
	//					+ s_subj+"&p_year="+s_year+"&p_subjseq="+s_subjseq;
	
	String	v_imgURL	= "/contents/" + v_subj + "/docs/menuimg";
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
		function goPrev(){
			top.etree_fset.etree.goPrev();
		}
		function jumpTo(mod,les,oid){
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

		/*
		   2006/10/31 Chung Jin-pil : Live Share 관련 Script
		    1. 라이브세어 콘텐츠를 띄우기 위한 함수
		    2. 라이브세어 저작도구를 사용하는 콘텐츠의 HTML에서 이 스크립트를 호출한다.
		    3. open_liveshare Servlet을 호출한다.
		 */
		function whenOpenLiveShare(){
			curmodule = document.curr.mod.value;
			curlesson = document.curr.les.value;
			curobject = document.curr.obj.value;

			var url = "/servlet/controller.contents.EduStart?p_process=liveshare&p_subj=<%=s_subj%>&p_year=<%=s_year%>&p_subjseq=<%=s_subjseq%>"+
						"&p_module="+curmodule+"&p_lesson="+curlesson+"&p_object="+curobject+"&p_type=V";
						
			uWin(url);
		}
		
		/*
		   2006/10/31 Chung Jin-pil : Live Share 관련 Script
		    1. 라이브세어 콘텐츠에서 학습종료 후에 호출하는 함수
		    2.  p_completed	     : 학습완료여부
			p_studyduration      : 학습한 시간(초단위) 라이브셰어 타이머의 학습시간
			p_realstudyduration  : 학습한 시간(초단위) 라이브셰어의 학습시간에 상관없이 실제로 머문시간.
		    3.	진도체크 하는 페이지로 넘긴다.
		 */
		function whenCheckLiveShare(p_completed,p_studyduration,p_realstudyduration){
			/*
			alert("p_completed:"+p_completed);
			alert("p_studyduration:"+p_studyduration);
			alert("p_realstudyduration:"+p_realstudyduration);

			curmodule = document.curr.mod.value;
			curlesson = document.curr.les.value;
			curobject = document.curr.obj.value;
			curidx	  = document.curr.idx.value;
			var  url = "/pls/cyber/zeduobj_chk_ica.chknout1?p_subj=G10078&p_year=2005&p_subjseq=0021&p_module="+curmodule;
			url+="&p_lesson="+curlesson+"&p_object="+curobject+"&p_curidx="+curidx+"&p_from=normal";
			url+="&p_gubun=NEXT&p_vodstudycheck=Y&p_vodstudytime="+p_realstudyduration+"&p_vodstudycomplete="+p_completed;


			if(!top.etop.getChecked()){
				chkCnt++;
				if (chkCnt < 10 ) {
					setTimeout("whenCheckLiveShare("+p_completed+","+p_studyduration+")",300);
				}else{
					alert("[Ending] 네트웍에 오류가 있습니다. 학습창을 닫고 다시 실행하여 주시기 바랍니다");
				}
			}else{
				chkCnt = 0;
				top.etop.setChecked(false);
				top.echk1.location = url;
				//2006.02.23 by LeeMinHo 학습이 완료일 경우에는, 다음 Object로 자동으로 넘어간다.
				if(p_completed == "Y"){
					afterEndChk();
				}
			}
			*/
		}
		
	</script>
	</head>
	
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table width="1020" border="0" cellspacing="0" cellpadding="0" height="100%">	
		<TR>
			<TD style="background-image: url(<%=v_imgURL%>/title.gif);"></TD>
			<!--TD height=29 align="center" valign=middle>&#160;</TD>
			<TD height=29 width=80 valign=middle align="right"></TD-->
		</TR>
	</TABLE>
	<form name="sel">		
	</form>
		<form name="curr">
			<input type=hidden  name="mod"  value="00">
			<input type=hidden  name="les"  value="00">
			<input type=hidden  name="obj"  value="00">
			<input type=hidden  name="idx"  value="0">
		</form>
	</body>
</html>
