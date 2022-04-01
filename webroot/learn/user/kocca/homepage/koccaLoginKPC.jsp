<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>

<%@ page import com.credu.homepage.* %>
<%@ page import com.credu.common.* %>
<%@ page import com.credu.system.* %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

	//RequestBox box = (RequestBox)request.getAttribute("requestbox");
    //if (box == null) box = RequestManager.getBox(request);
	RequestBox box = RequestManager.getBox(request);

    String pUserID = box.getString("id");
    String pCtype = box.getString("ctype");
	String pCtype2 = box.getString("bizcd" );

	//String pUserID = getParam(request, "id");
	//String pCtype = getParam(request, "ctype");

	if((pUserID==null) || pUserID.equals("")) {
		out.println("<script language='javascript'>location.href='http://contents.connect.or.kr';</script>");
		return;
	}
%>

<%
	long lnValueC1 = 52845L;
	long lnValueC2 = 28317L;
	long lnMYKEY = 15234L;

	Long lnUserID = new Long( pUserID );

	long lnResult = 0L;

	//decoding SK
	lnResult = ( lnUserID.longValue() - lnValueC2 ) / lnValueC1 - lnMYKEY;

	if( pCtype == null || pCtype.equals("" ) ) {
		pCtype = pCtype2;
	}

	String strUserId = pCtype + lnResult;

	//if( strUserId.equals("TUM10176") ) {
	//	strUserId = "filmmovie";
	//}

	if( strUserId.equals("SKT1107692") ) {
		strUserId = "kama";
	}

	if( strUserId.equals("SKT1103702") ) {
		strUserId = "syg710";
	}

	if( strUserId.equals("TUM10194") ) {
		strUserId = "filmvoice";
	}

	if( strUserId.equals("SKT1108481") ) {
		strUserId = "funkylife";
	}

	if( strUserId.equals("TUM10062") ) {
		strUserId = "10062";
	}

	if( strUserId.equals("SKT1101873") ) {
		strUserId = "bskim9";
	}

	if( strUserId.equals("TUM10066") ) {
		strUserId = "iwillbee";
	}

	if( strUserId.equals("TUM10200") ) {
		strUserId = "91022";
	}

	if( strUserId.equals("SKT1107770") ) {
		strUserId = "coolsongss";
	}

	if( strUserId.equals("SEOULCT1100004916") ) {
		strUserId = "ssongpi";
	}

	if( strUserId.equals("TUM10016") ) {
		strUserId = "yyyykkkk";
	}

	if( strUserId.equals("SEOULCT1100007480") ) {
		strUserId = "musicinema";
	}

	if( strUserId.equals("TUM10110") ) {
		strUserId = "pepper07";
	}

	String strPW = getPwd( strUserId );

	if( strPW == null || strPW.equals("") ) {
		strUserId = "" + lnResult;
		strPW = getPwd( strUserId );
	}

	if( strPW == null || strPW.equals("") ) {
		%> <script language="javascript"> location.href = "http://contents.connect.or.kr" </script> <%
	}


%>

<html>
<head>
<script language="javascript">
	function goNext() {

		var frm = document.form2;
		frm.action = "http://contents.connect.or.kr/servlet/controller.homepage.KLoginServlet"
		frm.submit();
	}
</script>
</head>
<body onload="goNext();">
<form name="form2" method="post" action="http://contents.connect.or.kr/servlet/controller.homepage.KLoginServlet">
	<input type="hidden" name="p_process" value="login">
	<input type="hidden" name="p_userid" value="<%=strUserId%>">
	<input type="hidden" name="p_pwd" value="<%=strPW%>">
</form>
</body>
</html>

<%!

    public String getPwd(String p_userid)
        throws Exception
    {
        DBConnectionManager dbconnectionmanager = null;
        ListSet listset = null;
        String s = "";
        String s1 = p_userid;
        String s7 = "";
        try
        {
            dbconnectionmanager = new DBConnectionManager();
            s = " select pwd      ";
            s = s + " from TZ_MEMBER  ";
            s = s + " where userid = " + StringManager.makeSQL(s1);
            s = s + "\tand state  = 'Y' ";
            listset = dbconnectionmanager.executeQuery(s);
            if(listset.next())
                s7 = listset.getString("pwd");
        }
        catch(Exception exception1)
        {
            //ErrorManager.getErrorStackTrace(exception1, requestbox, s);
            throw new Exception("sql = " + s + "\r\n" + exception1.getMessage());
        }
        finally
        {
            if(listset != null)
                try
                {
                    listset.close();
                }
                catch(Exception _ex) { }
            if(dbconnectionmanager != null)
                try
                {
                    dbconnectionmanager.freeConnection();
                }
                catch(Exception _ex) { }
        }
        return s7;
    }


%>