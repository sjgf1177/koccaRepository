<%@ page contentType="text/html; charset=euc-kr" %>
<%
	String bunryu_code = request.getParameter("bunryu_code");
	String course_code = request.getParameter("course_code");
	String object_id = request.getParameter("object_id");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<title>MDG - header</title><link rel="STYLESHEET" type="text/css" href="style.css">
<script>
/************************************************************************
**
** Filename:  header.htm
**
** File Description: This is the header.htm page called by the frameset in 
**		start.html.  Has the ADL logo as well as the name of the Metadata
**		Generator and version.
**
**
** Author:  ADLI Project
**
** Company Name: Concurrent Technologies Corporation (CTC)
**
** Module/Package Name:
** Module/Package Description:
**
** Design Issues:
**
** Implementation Issues:
** Known Problems:
** Side Effects:
**
**
**************************************************************************
**
** Advanced Distributed Learning Co-Laboratory (ADL Co-Lab) grants you
** ("Licensee") a non-exclusive, royalty free, license to use, modify and
** redistribute this software in source and binary code form, provided
** that i) this copyright notice and license appear on all copies of the
** software; and ii) Licensee does not utilize the software in a manner
** which is disparaging to CTC.
**
** This software is provided "AS IS," without a warranty of any kind.  ALL
** EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
** INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
** PARTICULAR PURPOSE OR NON- INFRINGEMENT, ARE HEREBY EXCLUDED.  CTC AND
** ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
** AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE SOFTWARE OR ITS
** DERIVATIVES.  IN NO EVENT WILL CTC  OR ITS LICENSORS BE LIABLE FOR ANY
** LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL,
** CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND
** REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR
** INABILITY TO USE SOFTWARE, EVEN IF CTC  HAS BEEN ADVISED OF THE
** POSSIBILITY OF SUCH DAMAGES.
**
*************************************************************************
**
** Date Changed   Author of Change  Reason for Changes
** ------------   ----------------  -------------------------------------
** 3/12/2001      Tom Hopkins       Initial Final Version for MDG v1.1
**
************************************************************************/
</script>	
<script language="javascript">
<!--
function meta_call() {
	top.location.href = "/Manager/Course/CoObject/aco_Meta_List.jsp?bunryu_code=<%=bunryu_code%>&course_code=<%=course_code%>&r_bunryu_code=<%=bunryu_code%>&r_course_code=<%=course_code%>&r_object_id=<%=object_id%>";
}
//-->
</script>
</head>
<body bottommargin=0 leftmargin=0 marginheight=0 marginwidth=0 rightmargin=0 topmargin=0>
<table cellspacing="0" Cellpadding="0" Border="0" width="100%">
  <tr> 
    <td height="58" background="/images/metadata/hrdi_bg.gif" align="right"><!-- input type="button" value="기존 데이타불러오기" onClick="meta_call()" -->
    </td>
  </tr>
</table>
</body>
</html>
