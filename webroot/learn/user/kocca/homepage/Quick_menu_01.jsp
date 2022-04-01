
<%
//**********************************************************
//  1. 제      목: 홈페이지 과정로드맵
//  2. 프로그램명 : Quick_menu_01.jsp
//  3. 개      요: 홈페이지 과정로드맵
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.22
//  7. 수      정:
//***********************************************************
%>


<td height="21"><a href="javascript:viewLoad()"><img src="/images/user/kocca/homepage/type1/quick_menu_01.gif" width="166" height="20" border="0"></a><br></td>




<SCRIPT LANGUAGE="JavaScript">
<!--

     function viewLoad() {

        window.self.name = "winSelectView";
        farwindow = window.open("", "openWin", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 762, height = 750, top=0, left=0");
        document.pform1.target = "openWin"

        document.pform1.p_process.value = "select";
        document.pform1.action = "/servlet/controller.course.SubjLoadMapServlet";
        document.pform1.submit();

        farwindow.window.focus();
        document.pform1.target = window.self.name;
     }

//-->
</SCRIPT>