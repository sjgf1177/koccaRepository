<%
    String leftmenu  = box.getString("leftmenu");
    String orders    = box.getString("orders");
%>

<SCRIPT LANGUAGE="JavaScript">
<!--
function menuForward(topmenu, leftmenu, orders){

    document.topdefaultForm.orders.value     = orders;
    document.topdefaultForm.topmenu.value    = topmenu;
    document.topdefaultForm.leftmenu.value   = leftmenu;
    document.topdefaultForm.p_process.value  = "writeLog";
    document.topdefaultForm.action = "/servlet/controller.system.MenuCountServlet";

    document.topdefaultForm.submit();
}
//-->
</SCRIPT>

<form name="topdefaultForm" method="post" action="javascript:return;">
  <input type='hidden' name='p_process'  value=''>
  <input type='hidden' name='topmenu'    value='<%=topmenu%>'>
  <input type='hidden' name='leftmenu'   value='<%=leftmenu%>'>
  <input type='hidden' name='orders'     value='<%=orders%>'>
</form>