<%
//**********************************************************
//  1. 제      목: 메뉴 등록
//  2. 프로그램명: za_Menu_P.jsp
//  3. 개      요: 메뉴 등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2005. 06. 21
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.templet.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String v_process  = box.getString("p_process");
    String v_grcode   = box.getString("p_grcode");
    String v_grtype   = box.getString("p_grtype");
    String v_gubun    = box.getString("p_gubun");
    String v_position = box.getString("p_position");

    String v_m_gubun        = "";
    String v_m_menuid       = "";
    String v_m_menuname     = "";

    String v_s_gubun        = "";
    String v_s_menuid       = "";
    String v_s_menuname     = "";

    String v_s_order        = "";
    ArrayList  list1 = (ArrayList)request.getAttribute("MenuMaster");
    ArrayList  list2 = (ArrayList)request.getAttribute("MenuList");

%>

<html>
<title></title>
<head>
<link href="/css/admin_style.css" rel="stylesheet" type="text/css">
<script language="javascript">
    //메뉴 추가
    function ControlAdd()
    {
        // 변수 선언
        var order    = document.getElementById("p_order");
        var selLeft  = document.getElementById("p_allmenu");
        var selRight = document.getElementById("p_addmenu");
        var found = false;

        // List Order 초기화
        order.value = "";

        // 선택여부 검사
        if(selLeft.selectedIndex < 0)
        {
            alert("추가할 메뉴를 선택하세요.");
            return;
        }

        // 'OPTION' Element 생성
        var newOption = document.createElement("OPTION");
        newOption.text = selLeft.options[selLeft.selectedIndex].text;
        newOption.value = selLeft.options[selLeft.selectedIndex].value;

        // 선택여부 검사
        for(var i = 0; i < selRight.options.length; i++)
        {
            if (newOption.value == selRight.options[i].value)
            {
                found = true;
                break;
            }
        }

        // 추가한 메뉴리스트에 추가
        if(!found)
            selRight.options.add(newOption);
        else
            alert("이미 선택된 메뉴 입니다.");

        // List Order 작성
        for(i=0; i< selRight.options.length; i++)
        {
            order.value += selRight.options[i].value+"|";
        }
    }

    //컨트롤 제거
    function ControlDel()
    {
        var order = document.getElementById("p_order");
        var selRight = document.getElementById("p_addmenu");

        order.value = "";

        if (selRight.selectedIndex < 0)
        {
            alert("제거할 메뉴를 선택하세요.");
            return;
        }

        selRight.remove(selRight.selectedIndex);

        for(i=0; i< selRight.options.length; i++)
        {
            order.value += selRight.options[i].value+"|";
        }
    }

    //컨트롤 순서 상하 이동
    function moveList(type)
    {
        var order = document.getElementById("p_order");
        var selRight = document.getElementById("p_addmenu");
        var sel = document.getElementById("p_addmenu").selectedIndex;

        order.value = "";

        if (selRight.selectedIndex < 0)
        {
            alert("이동할 메뉴를 선택하세요.");
            return;
        }

        if (type == "U")
        {
            if(sel > 0)
            {
                var onetext = selRight.options[sel-1].text;
                var onevalue = selRight.options[sel-1].value;

                selRight.options[sel-1].text  = selRight.options[sel].text;
                selRight.options[sel-1].value = selRight.options[sel].value;

                selRight.options[sel].text = onetext;
                selRight.options[sel].value = onevalue;
                selRight.selectedIndex = sel-1;
            }
        }
        else if (type == "D")
        {
            if(sel < selRight.options.length - 1)
            {
                var onetext = selRight.options[sel+1].text;
                var onevalue = selRight.options[sel+1].value;

                selRight.options[sel+1].text  = selRight.options[sel].text;
                selRight.options[sel+1].value = selRight.options[sel].value;

                selRight.options[sel].text = onetext;
                selRight.options[sel].value = onevalue;
                selRight.selectedIndex = sel+1;
            }
        }

        for(i=0; i< selRight.options.length; i++)
        {
            order.value += selRight.options[i].value+"|";
        }

    }

    // 선택된 메뉴 선택 변경시
    function SelectedChange()
    {
        var selRight = null;
        var index = -1;
        var menuName = "";
        var menuValue = "E";
        var menuType = "I";

        if(document.getElementById("p_addmenu") != null)
        {
            selRight = document.getElementById("p_addmenu");
            index = selRight.selectedIndex;

            if(index > -1)
            {
                menuName = selRight.options[index].text;
                menuValue = selRight.options[index].value;
            }
        }
    }

    // 수정
    function update() {
        var order = document.getElementById("p_order");
        var selRight = document.getElementById("p_addmenu");
		order.value = "";

//        if (selRight.selectedIndex < 0)
        if (selRight.options.length < 0)
        {
            alert("저장할 메뉴를 선택하세요.");
            return;
        }

        for(i=0; i< selRight.options.length; i++)
        {
            order.value += selRight.options[i].value+"|";
        }

<%  if(v_gubun.equals("0")) {      %>
		document.form1.p_process.value= "contentsUpdate";
<%  } else {                        %>
		document.form1.p_process.value= "menuUpdate";
<%  }                               %>
        document.form1.action="/servlet/controller.templet.TempletServlet";
        document.form1.submit();
    }


</script>


</HEAD>
<body id="myBody" leftMargin="0" topMargin="0" onLoad="SelectedChange()">

<form name="form1" method="post" action="">
    <!--리스트 박스 순서 Hidden으로 넘겨준다.-->
    <input type="hidden" name="p_process"   value="<%=v_process%>">
    <input type="hidden" name="p_grcode"    value="<%=v_grcode%>">
    <input type="hidden" name="p_grtype"    value="<%=v_grtype%>">
    <input type="hidden" name="p_gubun"     value="<%=v_gubun%>">
    <input type="hidden" name="p_position"  value="<%=v_position%>">


  <table cellSpacing="0" cellPadding="4" width="420" bgColor="#ffffff" border="0">
    <tr>
      <td height="30" align="center"> <table cellSpacing="0" cellPadding="0" width="400" border="0">
          <tr>
            <td height="5"></td>
          </tr>
          <tr>
            <td><img src="/images/admin/templet/st_2.gif" ></td>
          </tr>
        </table></td>
    </tr>
    <tr id="htrMenuList">
      <td align="center"> <table id="htblList" cellspacing="0" cellpadding="0" width="400" bgcolor="#ffffff" border="0">
          <tr>
            <td valign="top" align="center" width="150">
            <table style="BORDER-COLLAPSE: collapse" borderColor="#ededed" height="150" cellSpacing="0" cellPadding="0" width="150" border="1">
                <tr>
                  <td class="table_tit_tam">전체메뉴</td>
                </tr>
                <tr>
                  <td>
                    <!-- 전체메뉴 리스트 -->
                    <select name="p_allmenu" size="4" id="p_allmenu" ondblclick="ControlAdd()" style="border-color:White;border-width:0px;height:130px;width:100%;">
<%
    for (int i=0; i<list1.size(); i++) {
        DataBox dbox1 = (DataBox)list1.get(i);

        v_m_gubun     = dbox1.getString("d_gubun");
        v_m_menuid    = dbox1.getString("d_menuid");
        v_m_menuname  = dbox1.getString("d_menuname");

%>
                      <option value="<%=v_m_menuid%>"><%=v_m_menuname%></option>
<% }   %>
                    </select>
                  </td>
                </tr>
              </table>
            </td>
            <td align="center" width="53"> 
              <table cellSpacing="0" cellPadding="0" width="47" border="0">
                <tr>
                  <td align="center" width="53"><A href="javascript:ControlAdd()"><IMG alt="추가" src="/images/admin/templet/b_aright.gif"  border="0"></A>
                  </td>
                </tr>
                <tr>
                  <td align="center"><A href="javascript:ControlDel()"><IMG  alt="제거" src="/images/admin/templet/b_aleft.gif"  border="0"></A>
                  </td>
                </tr>
              </table>
            </td>
            <td valign="top" align="center" width="175">
            <table style="BORDER-COLLAPSE: collapse" borderColor="#ededed" height="150" cellSpacing="0" cellPadding="0" width="175" border="1">
                <tr>
                  <td class="table_tit_tam" align="center" width="150">현재메뉴</td>
                  <td class="table_tit_sun" align="center" width="25" bgColor="#cccccc">순서</td>
                </tr>
                <tr>
                  <td width="150">
                    <!-- 추가된 메뉴 리스트 -->
                    <A href="javascript:moveList('D')">
                    <select name="p_addmenu" size="4" id="p_addmenu" onChange="javascript:SelectedChange()" ondblclick="ControlDel()" style="border-color:White;border-width:0px;height:130px;width:100%;">
<%
    for (int i=0; i<list2.size(); i++) {
        DataBox dbox2 = (DataBox)list2.get(i);

        v_s_gubun     = dbox2.getString("d_gubun");
        v_s_menuid    = dbox2.getString("d_menuid");
        v_s_menuname  = dbox2.getString("d_menuname");
        v_s_order    += v_s_menuid + "|";
%>
                      <option value="<%=v_s_menuid%>"><%=v_s_menuname%></option>
<% }   %>
                    </select>
                    </A> 
                    <input type="hidden" name="p_order" id="p_order" value="<%=v_s_order%>">
                  </td>
                  <td align="center" width="25">
                    <A href="javascript:moveList('U')"><IMG alt="위로" src="/images/admin/templet/b_atop.gif" align="absMiddle" vspace="10" border="0"></A>
                    <A href="javascript:moveList('D')"> <IMG alt="아래로" src="/images/admin/templet/b_adown.gif" align="absMiddle" vspace="10" border="0"></A>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <TD valign="top" align="center" colspan="3">
              <TABLE id="Table1" cellSpacing="0" cellPadding="0" width="100%" border="0">
                <TR>
                  <TD height="5" colspan="2"></TD>
                </TR>
                <TR>
                  <TD colspan="2"><img src="/images/admin/templet/line.gif" width="388" height="1"></TD>
                </TR>
                <TR>
                  <TD height="5" colspan="2"></TD>
                </TR>
                <TR>
                  <TD width="70%"><FONT face="굴림" color="#CC0000">* 추가 및 순서 수정 후 반드시 [수정] 버튼을 눌러주세요! </FONT> </TD>
                  <TD width="30%">
                    <a href="javascript:update()"><img src="/images/admin/button/btn_modify.gif" alt="" border="0"></a>
                    <a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" alt="" border="0"></a>
                    
                  </TD>
                </TR>
              </TABLE>
            </TD>
          </tr>
        </table></td>
    </tr>
  </table>
            </form>
    </body>
</HTML>

