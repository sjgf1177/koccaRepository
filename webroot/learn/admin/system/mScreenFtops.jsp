<%
/**
 * file name : mScreenFset.jsp
 * date      : 2003/7/11
 * programmer: LeeSuMin
 * function  : manager screen FrameSet
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }
/*============ 테스트용 ======================*/
//    box.setSession("s_grcode","N000001");
//    box.setSession("s_gadmin","A1");
/*============ 테스트용 ======================*/
    String  v_grcode    = box.getSession("s_grcode");    
    String  v_gadmin    = box.getSession("gadmin");

    if (v_grcode.equals(""))     v_grcode 	 = "N000001";
    if (v_gadmin.equals(""))     v_grcode 	 = "ZZ";
%>
<html><head><title></title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=KS_C_5601">
</head>
<script language="javascript">
	apam = new Array(30);
	
	var	sizei = 0;
	function init(){
		setVal("s_grcode","");
	}
	
	function openWin(url){
		window.open(url,"win","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=800,height=600").focus();
	}
	
	function getVal(name){
		for (var i=0; i<sizei;i++){
			cpam = apam[i];
			if(cpam.name==name)	return cpam.val;	
		}
		return "";
	}
	function setVal(name,val){
		var f_exist = false;

		for (var i=0; i<sizei;i++){
			cpam = apam[i];
			if(cpam.name==name)	{
				apam[i].val = val;
				return;	
			}
		}
		
		if (!f_exist){
			cpama = new Object();
			cpama.name = name;
			cpama.val  = val;
			apam[sizei] = cpama;
			sizei++;
		}
	}
	function getAll(){
		for (var i=0; i<apam.length;i++){
			cpam = apam[i];
			alert("cpam["+i+"].name="+apam[i].name+", val="+apam[i].val);			
		}	
	}
	function setPam(){
		fm = parent.fbody.document.form1;
		for(var i=0;i<fm.length;i++){
			if (fm.elements[i].name.substring(0,2)=="s_" && fm.elements[i].type=="select-one"){
				if (fm.elements[i].length > 0) {
					vals = fm.elements[i].options[fm.elements[i].selectedIndex].value;
					setVal(fm.elements[i].name, vals);
				}
			}
		}
	}
	init();
		// 강사 접속 로그아웃 처리
	function goLoginout() {
		<% if(v_gadmin.equals("P1")){%>
				if (confirm("운영자 화면을 닫으시겠습니까?")) {
					document.form1.p_process.value   = "tutorlogout";
					document.form1.action            = "/servlet/controller.homepage.LoginServlet";
					document.form1.submit();
				} else {
					return;
				}
		<%}%>
	}
</script>
</head>
<body onbeforeunload="goLoginout();">
<form name="form1" method="post">
<input type='hidden' name='p_process'>
</form>
</body></html>
