<%
//**********************************************************
//  1. 제      목: manager screen -display MenuApplet
//  2. 프로그램명 : za_Menu_L.jsp
//  3. 개      요: 메뉴 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 8
//  7. 수      정:
//*********************************************************** 
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id="myMenuBean" class="com.credu.system.MenuBean" scope="page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }
/*============ 테스트용 ======================*/
//    box.setSession("userid","lee1");
//    box.setSession("gadmin","A1");
/*============ 테스트용 ======================*/
    String  v_grcode    = box.getSession("s_grcode");    
    String  v_gadmin    = box.getSession("gadmin");
    String  v_systemgubun = box.getString("p_systemgubun");

    if (v_grcode.equals(""))     v_grcode 	 = "N000001";
    if (v_gadmin.equals(""))     v_grcode 	 = "ZZ";
    if (v_systemgubun.equals("") || v_systemgubun == null) v_systemgubun = "1";
    
%>
<html><head><title>Management.<%=v_grcode%>.<%=v_gadmin%>.</title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=KS_C_5601">
</head>
<script language="javascript">
	var vals = "";

    function SystemChange(){
        document.form1.submit();
    }
	function openWin(url){
  			window.open(url,"win","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=800,height=600").focus();
	}
	
	function whenMenuClicked(url){
		parent.fbody.location="loading.jsp";
		
	}
	function servletCall(vals){
		var aS = "";
		for(var i=0;i<document.fb.length;i++){
			aS = document.fb.elements[i].value.split(',');
			if(aS[2]==vals){
				whenMenu(aS[0],aS[2],aS[3],aS[4],aS[5],aS[6],aS[7],aS[8],aS[9],aS[10],aS[11],aS[12],aS[13],aS[14]);
				return;
			}
		}
	}
	function whenMenu(menu,pgm,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12){
		var url = pgm;
		if (p1.length != 0){
			vals=top.ftop.getVal(p1);
			//url = url + "?" + p1 + "="+ vals;
			url = url + "&" + p1 + "="+ vals;
		}
		if (p2.length != 0){
			vals=top.ftop.getVal(p2);
			url = url + "&" + p2 + "="+ vals;
		}
		if (p3.length != 0){
			vals=top.ftop.getVal(p3);
			url = url + "&" + p3 + "="+ vals;
		}
		if (p4.length != 0){
			vals=top.ftop.getVal(p4);
			url = url + "&" + p4 + "="+ vals;
		}
		if (p5.length != 0){
			vals=top.ftop.getVal(p5);
			url = url + "&" + p5 + "="+ vals;
		}
		if (p6.length != 0){
			vals=top.ftop.getVal(p6);
			url = url + "&" + p6 + "="+ vals;
		}
		if (p7.length != 0){
			vals=top.ftop.getVal(p7);
			url = url + "&" + p7 + "="+ vals;
		}
		if (p8.length != 0){
			vals=top.ftop.getVal(p8);
			url = url + "&" + p8 + "="+ vals;
		}
		if (p9.length != 0){
			vals=top.ftop.getVal(p9);
			url = url + "&" + p9 + "="+ vals;
		}
		if (p10.length != 0){
			vals=top.ftop.getVal(p10);
			url = url + "&" + p10 + "="+ vals;
		}
		if (p11.length != 0){
			vals=top.ftop.getVal(p11);
			url = url + "&" + p11 + "="+ vals;
		}
		if (p12.length != 0){
			vals=top.ftop.getVal(p12);
			url = url + "&" + p12 + "="+ vals;
		}

		parent.fbody.location=url;
	}
	
/*	function getFtopVal(name){
		var f_exist = "N";
		for(var i=0; i < top.ftop.ff.length;i++){
			if (top.ftop.ff.elements[i].name==name){
				vals = top.ftop.ff.elements[i].value;
				f_exist = "Y";
				break;
			}
		}
		if (f_exist == "N"){
			alert(name+"에 해당하는 값이 없습니다");		//에 해당하는 값이 없습니다
			return "N";
		}
	}
*/

</script>
    <script language = "javascript" src = "/script/user_patch.js"></script>
<body leftmargin=0 topmargin=0 marginwidth=0 marginheight=0 text="#000000">
<table width="100%" height="74" border="0" cellpadding="0" cellspacing="0" background="/images/admin/system/admin_main_top_bg.gif">
  <form name="form1" method="post" action="mScreenFmenu.jsp">
  <tr> 
    <td><img src="/images/admin/system/admin_main_top_01.gif" width="260" height="74"></td>
    <!--td>
        <select name="p_systemgubun" onChange="SystemChange();">
            <option value="1" <%if(v_systemgubun.equals("1")) {out.println("selected");}%>>메인시스템</option>
            <option value="2" <%if(v_systemgubun.equals("2")) {out.println("selected");}%>>게이트시스템</option>
        </select>
    </td-->
    <td align="right"><img src="/images/admin/system/admin_main_top_02.gif" width="274" height="74"></td>
  </tr>
  </form>
  <tr>
        <td colspan = "3" bgcolor="4D95DE">
        <!--<applet code="MenuDemo" codebase="/webroot/edu/learn/admin/" align="baseline"  width="100%" height="27" name="MenuDemo"  MAYSCRIPT>-->
     <script language='javascript'>object_applet();</script>
	 <!--applet code="MenuDemo" codebase="../" align="baseline"  width="100%" height="27" name="MenuDemo"  MAYSCRIPT-->

<%
    ArrayList list = (ArrayList)myMenuBean.SelectList(box);
    MenuData data  = null;
    int		v_menu_cnt = 0, v_maxmenuItem_cnt=0;
    int		i=0, v_menuNum=0, v_menuItemNum=0;
    String	v_pa="";

	for (i=0; i<list.size(); i++) {
		data  = (MenuData)list.get(i); 
		if (data.getLevels()==1) {
			v_menu_cnt++;
			if (data.getRowspannum() > v_maxmenuItem_cnt)	v_maxmenuItem_cnt = data.getRowspannum();
		}

	}
%>
			<param name=menu_cnt value="<%=v_menu_cnt%>">
	    	<param name=max_menuItem_cnt value="<%=v_maxmenuItem_cnt%>">
<%
	for (i=0;i<list.size(); i++){
		data  = (MenuData)list.get(i);
		if (data.getLevels()==1){ 
			v_menuNum++;
			v_menuItemNum=0;	%>
			<param name="menuItem_cnt<%=v_menuNum%>" value="<%=data.getRowspannum()%>">
			<param name="menuPath<%=v_menuNum+"|"%><%=v_menuItemNum%>" value="<%=data.getPgm()%>">
			<param name="menu<%=v_menuNum%>" value="<%=data.getMenunm()%>"> <%
		}else{
			v_menuItemNum++;	%>
			<param name="menuItem<%=v_menuNum+"|"%><%=v_menuItemNum%>"  value="<%=data.getMenunm()%>">  
	    	<param name="menuPath<%=v_menuNum+"|"%><%=v_menuItemNum%>"  value="<%=data.getPgm()%>">	<%
		}
	} 	%>
		</applet>
	</td><td width=0>
		<form name="fb">
<%
	for (i=0;i<list.size(); i++){
		data  = (MenuData)list.get(i);
		if (data.getLevels() > 1) {
			v_pa = data.getMenu()+","+data.getMenunm() +","+ data.getPgm() +","+ data.getPara1() +","+ data.getPara2()
				 + ","+ data.getPara3() +","+ data.getPara4() +","+ data.getPara5()
				 + ","+ data.getPara6() +","+ data.getPara7() +","+ data.getPara8()
				 + ","+ data.getPara9() +","+ data.getPara10() +","+ data.getPara11() +","+ data.getPara12(); %>
			<input type=hidden name="<%=data.getMenu()%>" value="<%=v_pa%>">
<%		}
	} 	%>
		</form>
	</td>
	</tr>
</table>
</body>
</html>
