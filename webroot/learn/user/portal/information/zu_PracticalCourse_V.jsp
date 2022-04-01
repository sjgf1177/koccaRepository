<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.dunet.common.util.*" %>  
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    
    String  v_pictureurl          = "";   // vod
    String  v_contenttype         = "";
    String  v_width               = "";
    String  v_height              = "";
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
        v_pictureurl            = dbox.getString("d_pictureurl");
        v_contenttype           = dbox.getString("d_contenttype");
        
        if (v_contenttype.equals("A1")) {
            v_width   = "480";
            v_height  = "360";
        } else {
            v_width   = "300";
            v_height  = "40";
        }
            
    }   
    
    String v_server   = conf.getProperty("kocca.url.value");
    
    v_server = v_server.replace("http://", "mms://");
    
%>

<html>
<body topmargin="0" leftmargin="0">
<table border=1 width="100%" border="0" cellspacing="0" cellpadding="0" background="black">
    <tr>
        <td>
          <OBJECT ID="MediaPlayer1" classid="CLSID:22D6F312-B0F6-11D0-94AB-0080C74C7E95" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701" standby="Loading Microsoft?Windows?Media Player components..." 
                type="application/x-oleobject" width="<%=v_width %>" height="<%=v_height %>">
        <PARAM NAME="FILENAME" VALUE="<%=v_pictureurl %>">
        <PARAM NAME="SHOWCONTROLS" VALUE="TRUE">
        <PARAM NAME="SHOWSTATUSBAR" VALUE="False">
        <PARAM NAME="SHOWDISPLAY" VALUE="False">
        <PARAM NAME="TRANSPARENTATSTART" VALUE="TRUE">
        <PARAM NAME="TRANSPARENTATSTOP" VALUE="FALSE">
        <PARAM NAME="ANIMATIONATSTART" VALUE="FALSE">
        <PARAM NAME="AUTOSTART" VALUE="FALSE">
        <PARAM NAME="AUTOREWIND" VALUE="TRUE">
        <PARAM NAME="SENDMOUSECLICKEVENTS" VALUE="TRUE">
        <PARAM NAME="DISPLAYSIZE" VALUE="0">
        <PARAM NAME="AUTOSIZE" VALUE="FALSE">
        <PARAM NAME="SHOWTRACKER" VALUE="TRUE">
        <PARAM NAME="ENABLED" VALUE="1">
        <PARAM NAME="ENABLECONTEXTMENU" VALUE="0">
        <PARAM NAME="ENABLEPOSITIONCONTROLS" VALUE="1">
        <PARAM NAME="ENABLEFULLSCREENCONTROLS" VALUE="1">
        <PARAM NAME="SHOWPOSITIONCONTROLS" VALUE="0">
        <PARAM NAME="MUTE" VALUE="0">
        <PARAM NAME="RATE" VALUE="1">
        <PARAM NAME="SAMILANG" VALUE="">
        <PARAM NAME="SAMISTYLE" VALUE="">
        <PARAM NAME="SAMIFILENAME" VALUE="">
        <PARAM NAME="CLICKTOPLAY" VALUE="0">
        <PARAM NAME="CURSORTYPE" VALUE="1">
        <PARAM NAME="LOOP" VALUE="TRUE">
    </OBJECT>
        </td>
    </tr>
</table>


</body>
</html>