<%
/**
 * file name : za_HuryunExe_E.jsp
 * date      : 2004/12/29
 * programmer: Lee Chang Hun
 * function  : 훈련실시신고 인쇄
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.scorm.*" %>
<%@ page import = "com.credu.goyong.*" %>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>
 
<%
	response.setHeader("Content-Disposition", "inline; filename=StudentHuryunList.xls");   
	response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	
	String ss_subjcourse	= box.getString("s_subjcourse");
	
	String v_scsubjnm	= "", v_edustart = "", v_eduend = "", v_subjseqgr = "", v_place = "";
	
	GoYongManageBean bean = new GoYongManageBean();
    DataBox titleDBox = bean.selectSuryoTitle(box, ss_subjcourse);
    
    if(titleDBox != null){
       v_scsubjnm  = titleDBox.getString("d_scsubjnm");
       v_edustart  = titleDBox.getString("d_edustart");
       v_eduend    = titleDBox.getString("d_eduend");
       v_place     = titleDBox.getString("d_place");
       v_subjseqgr = titleDBox.getString("d_subjseqgr");
    }
	
	ArrayList list = (ArrayList)request.getAttribute("StudentList2");
   	int listsize = list.size();
%>
<html>
<head>
<title>입과인원 Excel보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
      <table cellspacing="1" cellpadding="5" border="1">
      	  <tr align="center">
      	  	<td>과정명</td>
      	  	<td>&nbsp;<%=v_scsubjnm%></td>
      	  	<td>과정차수</td>
      	  	<td>&nbsp;<%=StringManager.cutZero(v_subjseqgr) %>차</td>
      	  	<td>교육기간</td>
      	  	<td>&nbsp;<%= FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd") %> - <%= FormatDate.getFormatDate(v_eduend ,"yyyy/MM/dd") %></td>
      	  </tr>
          <tr> 
            <td align=center>성명</font></td>
            <td align=center>주민등록번호</font></td>
            <td align=center>구분</font></td>
            <td align=center>학력</font></td>
            <td align=center>전공</font></td>
            <td align=center>종사직종</font></td>
            <td align=center>직급</font></td>
            <td align=center>근로시간</font></td>
            <td align=center>건설근로자</font></td>
            <td align=center>파견근로자</font></td>
          </tr>
<%                          
        for(int i = 0; i < list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);
%>
        <tr> 
            <td alig=center><%= dbox.getString("d_name") %></font></td>
            <td alig=center><%= StringUtil.juminno(dbox.getString("d_resno")) %></font></td>
            <td style='mso-number-format:"\@";'>01</td>
            <td><%= dbox.getString("d_acceace") %></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td><%= dbox.getString("d_jikup") %></td>
            <td style='mso-number-format:"\@";'>02</td>
            <td>N</td>
            <td>N</td>
        </tr>
<% } %>
        </table>
      <!----------------- 입과인원 조회 끝 ----------------->
     <br>
    </td>
  </tr> 
</table>
</body>
</html>
