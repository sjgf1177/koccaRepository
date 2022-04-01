<%@ page contentType="text/html; charset=euc-kr" %>
<%! 
	public String makeKor(String s) throws java.io.UnsupportedEncodingException{ 
/*		
		String kor=""; 
		
		if (s==null) 
			kor=null; 
		else 
			kor=new String(s.getBytes("ISO-8859-1"),"EUC-KR"); 
		
		return kor; 
*/
		return s;
	} 
%>
<%
		String strURL = makeKor(request.getParameter("url"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<title>:::: 검색 결과 ::::</title>
</head>

<body topmargin=0 leftmargin=0>
<table width="900" height="680" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="143"><img src="images/search_top.gif" width="900" height="143"></td>
  </tr>
  <tr>
    <td height="474" background="images/newwin_back.gif"><table width="900" height="474" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="73">&nbsp;</td>
        <td width="755" colspan="2"><table width="755" height="474" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><iframe src="/contents/00113<%= strURL %>" name="rsview"
                         width="755" height="450" border="0" frameborder="0" ALLOWTRANSPARENCY="true" framespacing="0" marginheight="0" marginwidth="0" scrolling="auto"
                         vspace="0"></iframe></td>
          </tr>
        </table></td>
        <td width="73">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><img src="images/newwin_bottom.gif" width="900" height="63"></td>
  </tr>
</table>
</body>
</html>

