<%
//**********************************************************
//  1. 제      목: 마스터폼 정보수정
//  2. 프로그램명 : za_MasterForm_U.jsp
//  3. 개      요: 마스터폼 정보수정화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 24
//  7. 수      정:
//*********************************************************** 
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.beta.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }
    
    String  p_subj   	= box.getString("p_subj");
    String	c_gadmin	= box.getSession("s_gadmin");
    if(c_gadmin==null || c_gadmin.equals(""))	c_gadmin="A1";
    
    String	p_process	= box.getString("p_process");
    String  ss_contenttype = box.getString("s_contenttype");
    String  ss_upperclass  = box.getString("s_upperclass");
    String  ss_middleclass = box.getString("s_middleclass");
    String  ss_lowerclass  = box.getString("s_lowerclass");
	
	int     v_colspan  	   = 0;
    
	BetaMasterFormData	da	=(BetaMasterFormData)request.getAttribute("BetaMasterFormData");
	String 	v_subjnm		= da.getSubjnm();
	String	v_dir			= da.getDir();
	String	v_contenttypetxt= da.getContenttypetxt();
	String  v_eduprocess    = da.getEduprocess();
	String  v_otbgcolor		= da.getOtbgcolor();
	
	ArrayList		list1 = (ArrayList)request.getAttribute("BetaMfMenuData");	
	ArrayList		list2 = (ArrayList)request.getAttribute("BetaMfSubjData");
	DataBox			dbox1 = null;
	BetaMfSubjData		data2 = null;	
	SelectParam param = new SelectParam("p_mftype", "", false, 1, box.getHttpSession());
	
	if(ss_contenttype.equals("S")){
		v_colspan=5;
	}
	else{
		v_colspan=3;
	}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
</head>


<script language="javascript">
	function openWin(url){
window.open(url,"win","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=550,height=500").focus();
	}

	function  moveTo(valn){
		var seled = document.ff.result.selectedIndex;
		var goidx = seled + valn;
		if(seled==0 && valn == -1){
			alert("cannot go up..");
			return;
		}else if(seled==document.ff.result.length-1 && valn == 1){
			alert("cannot go down..");
			return;
		}else{
			var sel_text  = document.ff.result.options[seled].text;
			var sel_value = document.ff.result.options[seled].value;
			document.ff.result.options[seled].value = document.ff.result.options[goidx].value;
			document.ff.result.options[seled].text  = document.ff.result.options[goidx].text;
			document.ff.result.options[goidx].value = sel_value;
			document.ff.result.options[goidx].text  = sel_text;
			document.ff.result.options[goidx].selected=true;

		}

	}
	function dels(){
		if(document.ff.result.selectedIndex==null){
			alert("삭제할 메뉴를 선택하십시오");
			return;
		}
		var i = document.ff.result.selectedIndex;

		for(var j=0; j<document.ff.result.length; j++){
			if (i==j)
				document.ff.result.options[j] = null;
		}

	}
	function whenSel(){
		var f_exist = "N";
		var i = document.ff.sel.selectedIndex;
		var selTxt = document.ff.sel.options[i].value;
		var arrStr = selTxt.split(",");

		for(var j=0; j<document.ff.result.length; j++){
			if( document.ff.result.options[j].value == arrStr[0]) {
				f_exist = "Y";
				alert("["+arrStr[1]+"]은 이미 선택되어 있습니다");
				break;
			}
		}
		if (f_exist=="N"){
			document.ff.result.options[j] = new Option(arrStr[1],arrStr[0]);
			document.ff.result.options[j].selected = true;
			document.f2.p_menus.value += arrStr[0];
		}
	}

	function whenResult(){
		if(document.ff.result.selectedIndex==null){
			alert("삭제할 메뉴를 선택하십시오.");
			return;
		}
		var k = 0;
		var f_exist = "N";
		var sss = "";
	 	var i = document.ff.result.selectedIndex;

		for(var j=0; j<document.ff.result.length; j++){
			if (i==j)	document.ff.result.options[j] = null;
			else		sss += document.ff.result.options[j].value;
		}
		document.f2.p_menus.value = sss;
	}

	function whenSubmit(){
<%	if(!da.isCanModify())	{ %>
		alert("학습이 시작되었으므로 수정할 수 없습니다");
		return;
<%	}	%>	

		var arrStr;
		var j = 0;
		var f_exist = "N";

		for(var i=0; i<document.ff.sel.length;i++){
			arrStr = document.ff.sel.options[i].text.split("]");
			if (arrStr[0] == "[Required"){
				f_exist = "N";
				for(j=0; j<document.ff.result.length;j++){
					if ( document.ff.sel.options[i].text == document.ff.result.options[j].text)
						f_exist = "Y";
				}
				if (f_exist == "N"){
					alert("필수과정["+document.ff.sel.options[i].text+"]은 반드시 선택하셔야 합니다");
					return;
				}
			}
		}

		document.f2.p_menus.value="";

		for(i=0;i<document.ff.result.length;i++){
			document.f2.p_menus.value = document.f2.p_menus.value+document.ff.result.options[i].value;
		}
		if(confirm("저장하시겠습니까?")){
			document.f2.p_process.value="updateSave";
			document.f2.submit();
		}
	}
	function explainWin(){
		var url = document.f2.action+"?p_process=menuListPage&p_subj=<%=p_subj%>";
		window.open(url,"menuList","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=800,height=600").focus();
	}
	function setFullScreen(){
		document.f2.p_width.value=99999;
		document.f2.p_height.value=99999;
	}

</script>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
     <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/beta/tit_masterform.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  <!----------------- title 끝 ----------------->
        <br>
<%@ include file="/beta/admin/include_BetaMasterFormAdmin.jsp" %> 

        <!----------------- 마스터폼 정보수정 시작 ----------------->
        <table class="table_out" cellspacing="0" cellpadding="0">
          <tr> 
            <td bgcolor="#636563"> 
				<FORM name = "f2" method = "post" action="/servlet/controller.beta.BetaMasterFormServlet">
					<input type=hidden name=p_process  	  value="">
					<input type=hidden name=p_action	  value="">
					<input type=hidden name=p_subj		  value="<%=p_subj%>">
					<input type=hidden name=p_menus		  value="">
					<input type=hidden name=s_contenttype value="<%=ss_contenttype%>">
					<input type=hidden name=s_upperclass  value="<%=ss_upperclass%>">
					<input type=hidden name=s_middleclass value="<%=ss_middleclass%>">
					<input type=hidden name=s_lowerclass  value="<%=ss_lowerclass%>">
              <table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td bgcolor="#FFFFFF" align="center" valign="top"> 
                    <table width="99%" class="table_out" cellspacing="1" cellpadding="5">						
                      <tr> 
                        <td bgcolor="#FFFFFF" height="5" colspan="<%=v_colspan+1%>"></td>
                      </tr>
                      <tr> 
                        <td colspan="<%=v_colspan+1%>" class="table_top_line"></td>
                      </tr>
                      <tr> 
                        <td width="15%" height="25" class="table_title"><b>메뉴프레임 위치</b></td>
                        <td bgcolor="#F7F7F7" colspan="<%=v_colspan%>"> 
                          <%=SelectionUtil.getLevel1Code("0005", da.getMftype(), param)%>
                        </td>
                      </tr>
                      <tr> 
                        <td class="table_title" height="25"><b>창 크기</b></td>
                        <td bgcolor="#F7F7F7" colspan="<%=v_colspan%>">
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr> 
                              <td width="125"> WIDTH : 
                                <input name="p_width" type="text" class="input" size="5" value="<%=da.getWidth()%>">
                              </td>
                              <td width="125">HEIGHT : 
                                <input name="p_height" type="text" class="input" size="5" value="<%=da.getHeight()%>">
                              </td>
                              <td><a href="javascript:setFullScreen()"><img src="/images/admin/button/b_allscreen.gif" border="0"></a></td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <tr> 
	                    <td class="table_title" height="25"><b>콘텐츠 중앙정렬</b></td>
                        <td bgcolor="#F7F7F7" width="30%"> 
                          <% param.setSelectname("p_iscentered"); param.setOnchange("");%><%=SelectionUtil.getYesNoCode(da.getIscentered(), param)%>
                        </td>
                        <td class="table_title">학습진행방식</td>
                        <td bgcolor="#F7F7F7">
                        	<select name="p_eduprocess">
                        		<option value="Y" <% if (v_eduprocess.equals("Y")) { out.println("selected"); }%>>순차방식</option>
                        		<option value="N" <% if (v_eduprocess.equals("N")) { out.println("selected"); }%>>비순차방식</option>
                        	</select>
                        </td>
<%						if(ss_contenttype.equals("S")){	%>                        
                        <td class="table_title">트리메뉴배경색</td>
                        <td bgcolor="#F7F7F7">
                        	<input type="text" name="p_otbgcolor" size="10" maxlength="10" value="<%=v_otbgcolor%>">
                        </td>
<%						}								%>                        
                      </tr>
                      <!--
                      <tr>
                          <td height="25" class="table_title"><b>MenuImage FileUpload</b></td>
                          <td class="table_02_2" colspan="<%=v_colspan%>">
          	                <input type="FILE" class="input" name="p_file1" size="50">&nbsp;&nbsp;
                          </td>
                      </tr>
                      -->
                    </FORM>
                      <tr> 
                        <td class="table_title" height="25"> 
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td align="center"><b>메뉴선택</b></td>
                            </tr>
                            <tr>
                              <td height="6"></td>
                            </tr>
                            <tr>
                              <td align="center"><a href="javascript:explainWin()"><img src="/images/admin/button/b_menuex.gif" border="0"></a></td>
                            </tr>
                          </table>
                        </td>
					<FORM name="ff">
                        <td bgcolor="#F7F7F7" colspan="<%=v_colspan%>"> 
            				<select name="sel" size=8 style="width:550;" onchange="whenSel();">
<%  						for (int i=0; i<list1.size(); i++) {
        						dbox1  = (DataBox)list1.get(i); %>
                				<option value="<%=dbox1.getString("d_menu")%>,<%=dbox1.getString("d_isrequiredTxt")%><%=dbox1.getString("d_menunm")%>"><%=dbox1.getString("d_isrequiredTxt")%><%=dbox1.getString("d_menunm")%>
<%  						}  %>
							</select>
                        </td>
                      </tr>
                      <tr> 
                        <td height="25" class="table_title"><b>메뉴삭제<br>
                          (메뉴 출력 순서와 동일하게 구성)</b></td>
                        <td bgcolor="#F7F7F7" colspan="<%=v_colspan%>"> 
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr> 
                              <td width="67%"> 
            					<select multiple name="result" size=8 style="width:550;">
<%  							for (int i=0; i<list2.size(); i++) {
        						data2  = (BetaMfSubjData)list2.get(i);   %>
                				<option value="<%=data2.getMenu()%>"><%=data2.getIsrequiredTxt()%><%=data2.getMenunm()%></option>
<%  							}  %>
								</select>
                              </td>
					</FORM>
                              <td  align="center"> 
                                <table width="100" border="0" cellspacing="0" cellpadding="0">
                                  <tr> 
                                    <td><a href="javascript:moveTo(-1)"><img src="/images/admin/button/up_b.gif"  border="0"></a></td>
                                  </tr>
                                  <tr> 
                                    <td height="5"></td>
                                  </tr>
                                  <tr> 
                                    <td><a href="javascript:moveTo(1)"><img src="/images/admin/button/down_b.gif"  border="0"></a></td>
                                  </tr>
                                  <tr> 
                                    <td height="5"></td>
                                  </tr>
                                  <tr> 
                                    <td><a href="javascript:dels()"><img src="/images/admin/button/btn_del.gif"  border="0"></a></td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                    <!----------------- 마스터폼 정보수정 끝 ----------------->
                    <br>

                    <!----------------- 저장버튼 시작 ----------------->
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td>
						<td><a href="javascript:whenSubmit()"><img src="/images/admin/button/btn_save.gif"  border="0"></a></td>
						<td width="8"></td>
                        <td><a href="javascript:whenList()"><img src="/images/admin/button/btn_list.gif"  border="0"></a></td>	
                        </td>
                      </tr>
                      <tr> 
					   <td height="8"></td>
					   <td height="8"></td>
					   <td height="8"></td>
					   <td height="8"></td>
                       <td height="8"></td>
                      </tr>
                    </table>
                    <!----------------- 저장버튼 끝 ----------------->
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <br>
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>

