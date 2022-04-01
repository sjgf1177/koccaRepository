<%
// leftmenu 에 따라. 색깔 토글
	String leftmenu = box.getString("leftmenu");
    String left_image1 = "lm_01.gif";
    String left_image2 = "lm_02.gif";
    String left_image3 = "lm_03.gif";
    String left_image4 = "lm_04.gif";
    String left_image5 = "lm_05.gif";
    String left_image6 = "lm_06.gif";

	if (leftmenu.equals("01"))      left_image1 = "lm_01_on.gif";
	else if (leftmenu.equals("02")) left_image2 = "lm_02_on.gif";
	else if (leftmenu.equals("03")) left_image3 = "lm_03_on.gif";
	else if (leftmenu.equals("04")) left_image4 = "lm_04_on.gif";
	else if (leftmenu.equals("05")) left_image5 = "lm_05_on.gif";
	else if (leftmenu.equals("06")) left_image6 = "lm_06_on.gif";

%>
          <td width="152" rowspan="2" valign="top" >
            <img src="/images/user/<%=site%>/lm_tit.gif" >

            <a href="/servlet/controller.outclass.OutClassServlet?p_process=SubjectList" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image20','','/images/user/<%=site%>/lm_01_on.gif',1)"><img src="/images/user/<%=site%>/<%=left_image1%>" name="Image20" border="0"></a>

            <a href="/servlet/controller.outclass.OutClassServlet?p_process=ProposeCancelPage" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image21','','/images/user/<%=site%>/lm_02_on.gif',1)"><img src="/images/user/<%=site%>/<%=left_image2%>" name="Image21" border="0"></a>

            <a href="/servlet/controller.outclass.OutClassServlet?p_process=EducationSubjectPage" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image22','','/images/user/<%=site%>/lm_03_on.gif',1)"><img src="/images/user/<%=site%>/<%=left_image3%>" name="Image22" border="0"></a>

            <a href="/servlet/controller.outclass.OutClassServlet?p_process=StudyHistoryList" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image23','','/images/user/<%=site%>/lm_04_on.gif',1)"><img src="/images/user/<%=site%>/<%=left_image4%>" name="Image23" border="0"></a>

            <a href="/servlet/controller.outclass.OutClassServlet?p_process=ETestUserListPage" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image24','','/images/user/<%=site%>/lm_05_on.gif',1)"><img src="/images/user/<%=site%>/<%=left_image5%>" name="Image24" border="0"></a>

            <a href="/servlet/controller.outclass.OutClassServlet?p_process=SulmunNew" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image25','','/images/user/<%=site%>/lm_06_on.gif',1)"><img src="/images/user/<%=site%>/<%=left_image6%>" name="Image25" border="0"></a>

          </td>