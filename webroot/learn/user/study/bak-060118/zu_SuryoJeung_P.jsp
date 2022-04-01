<%
/**
 * file name : za_SuryoJeung_P.jsp
 * date      : 2004/12/29
 * programmer: Lee Chang Hun
 * function  : 수료증
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.scorm.*" %>
<%@ page import = "com.credu.goyong.*" %>
<%@ page import = "com.credu.study.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "MyClass" class = "com.credu.study.MyClassBean" scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String v_subj	   = box.getString("p_subj");
    String v_subjseq   = box.getString("p_subjseq");
    String v_year	   = box.getString("p_year");

    DataBox dbox = (DataBox)request.getAttribute("SuryoInfo");
    
    String v_comp      = dbox.getString("d_comp");
    String v_name      = dbox.getString("d_name");
    String v_resno     = dbox.getString("d_resno");
    String v_serno     = dbox.getString("d_serno");
    String v_subjnm    = dbox.getString("d_subjnm");
    String v_subjseqgr = dbox.getString("d_subjseqgr");
    String v_edustart  = dbox.getString("d_edustart");
    String v_eduend    = dbox.getString("d_eduend");

	// 임시
	if (v_eduend.equals(""))  v_eduend = "00000000";

    String v_user_id   = box.getSession("userid");
    String v_isunum    = "";
    int    v_width1	   = 0;
    int    v_width2	   = 0;

    if(v_comp.length()>4){
      if(StringManager.substring(v_comp,0,4).equals("0101")){
        v_comp = "H";
      }else{
        v_comp = "K";
      }
    }
//    v_isunum = MyClass.getIsunum(v_subj, v_subjseq, v_year,v_user_id);
    if (v_comp.equals("K") && v_serno.equals("")) {
    	v_serno = v_isunum;
    }
    if (v_comp.equals("H")) {
        v_width1 = 130;
        v_width2 = 505;
    }
    else {
        v_width1 = 200;
        v_width2 = 435;
    }
%>
<html>
<head><title></title>
<object id=factory classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"
codebase="/script/ScriptX.cab#Version=6,1,429,14">
</object>

<style media="print">
.noprint     { display: none }
</style>

<script language="javascript">
var i = 0;
function window.onload()
{
  var temp_div;
  var units = document.body;

  temp_div ="<div id=idWait class=noprint style='visibility: hidden; position: absolute; ";
  temp_div+="left: 0px; top: 0px; width: 0px; height: 0px; background-color: lightyellow; ";
  temp_div+="color: darkred; font:bold 9pt Arial; border: thin inset threedface; padding: 40pt'>";
  temp_div+="<center><b>잠시만 기다리십시오... <br>Print 준비중입니다....</b></center>";
  temp_div+="</div>";

  units.innerHTML += temp_div;
}

function printFrame()
{

  function printExec()
  {
  	SpoolStatus(true);

    factory.printing.Print();

    switch ( typeof(onfinish) )
    {
      case "string":
        execScript(onfinish);
        break;
      case "function":
        onfinish();
    }

    SpoolStatus(false);

  }
  if ( printIsFiveO() ) printExec() // IE5 이상
  else factory.TimeOut(printExec);  // IE4 workaround

}

function printIsFiveO()
{
	var agent = window.navigator.userAgent;
	var i = agent.indexOf("MSIE ")+5;
	// MSIE 5 이상일때 true를 return
	return parseInt(agent.substring(i, i+1)) >= 5;
}

function SpoolStatus(start)
{
  if ( start ) {
    var width = document.body.clientWidth/3;
    var height = document.body.clientHeight/4;
    idWait.style.pixelWidth  = width;
    idWait.style.pixelHeight = height;
    idWait.style.pixelLeft   = document.body.scrollLeft + width;
    idWait.style.pixelTop    = document.body.scrollTop + height/2;
    idWait.style.visibility  = "visible";
  }
  else {

		alert("인쇄가 완료되었습니다.");
    parent.window.close();
  }

}

function putSet(head, foot, top, bottom, left, right)
{
  with(factory.printing)
  {
    header		   = head;
    footer		   = foot;
    portrait	   = 1;
    topMargin	   = top;
    bottomMargin = bottom;
    leftMargin	 = left;
    rightMargin	 = right;
  }

  printFrame();

}

function doPrint()
{
	if ( i == 0 ) {
			putSet("", "", 0, 0, 0, 0);
			i++;
	}
// 	setInterval('Print();',6000);
}


</script>
</head>

<style type="text/css">
<!--
	.text1  {
		 font-family:굴림;
		 font-size:10pt;
		 color: #000000;
		 line-height: 120%;
	}
	.text1  A:link {color:#3366cc; text-decoration: none}
	.text1  A:visited {color:#009999; text-decoration: none}
	.text1  A:hover {color:#33ccff; text-decoration: none}

	.text2  {
		 font-family:궁서;
		 font-size:12pt;
		 color: #000000;
		 line-height: 140%;
	}
	.text2  A:link {color:#000000; text-decoration: none}
	.text2  A:visited {color:#000000; text-decoration: none}
	.text2  A:hover {color:#33ccff; text-decoration: none}

  .blacktext {FONT-FAMILY:굴림,Arial; font-size: 11pt; font-weight:normal; color:#000000; line-height: 140%; text-decoration: bold}

-->
</style>
<body topmargin=0 leftmargin=0>
<table width="100%" border="2" cellspacing="0" cellpadding="0" bordercolor="#999999">
  <tr>
    <td align="center">
      <table width="700" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align=right><br></td>
        </tr>
        <tr>
          <td>
            <table border="0" width="700" >
		      <tr height="30">
		    	<td width="65" align="right"><font size="4" face="궁서">제</font></td>
		    	<td width="<%=v_width1%>" align="center"><font size="4" face="궁서"><%=v_serno%></font></td>
		        <td width="<%=v_width2%>" align="left"><font size="4" face="궁서">호</font></td>
		      </tr>
	        </table>
	      </td>
	    </tr>
		<tr>
		  <td align=center ><% if ( v_comp.equals("K") ) { %><img src=/images/admin/hrd/mark_kia.gif><% } else { %><img src=/images/admin/hrd/mark_hyundai.gif><% } %></td>
		</tr>
		<tr height="50">
			<td></td>
		</tr>
        <tr> 
          <td align=center colspan="3">
            <table border="0" cellspacing="0" cellpadding="0">
        		<tr>
          			<td><img src="/images/user/study/completion_title.gif" width="219" height="45"></td>
        		</tr>
      		</table>
            <br><br><br><br><br>
            <table border="0" width="700" height="120">
              <tr> 
                <td width="175" align="right" height="50"><font size=5 face="궁서">성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명 
                  :&nbsp;</font></td>
                <td width="515" colspan="2" align="left"><font size="5" face="궁서">&nbsp;<%=v_name %></font></td></font></td>
              </tr>
              <tr> 
                <td width="175" align="right" height="50"><font size="5" face="궁서">주민등록번호 
                  :&nbsp;</font></td>
                <td width="515" align="left"><font size="5" face="궁서">&nbsp;<%=StringUtil.juminno(v_resno)%></font></td>
              </tr>
              <tr> 
                <td width="175" align="right" height="50"><font size="5" face="궁서">교&nbsp;&nbsp;육&nbsp;&nbsp;과&nbsp;&nbsp;정 
                  :&nbsp;</font></td>
                <td width="515" align="left"><font size="5" face="궁서">&nbsp;<%=v_subjnm%>(<%=Integer.parseInt(v_subjseqgr) %>차)</font></td>
              </tr>
              <tr> 
                <td width="175" align="right" height="50"><font size="5" face="궁서">교&nbsp;&nbsp;육&nbsp;&nbsp;기&nbsp;&nbsp;간 
                  :&nbsp;</font></td>
                <td width="515" align="left"><font size="5" face="궁서">&nbsp;<%= FormatDate.getFormatDate(v_edustart,"yyyy. MM. dd") %>&nbsp;&nbsp;-&nbsp;&nbsp;<%= FormatDate.getFormatDate(v_eduend,"yyyy. MM. dd") %></font></td>
              </tr>
            </table>
            <table border="0" width="700" height="200" valign="bottom">
              <tr> 
                <td align=center><font size="5" face="궁서">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;위 
                  사람은 근로자직업훈련촉진법&nbsp;제 14 조의 규정에 의하여<br>
                  <br>
                  위의 직업능력개발훈련과정 &nbsp;<%=v_subjnm%>(<%=Integer.parseInt(v_subjseqgr) %>차)을<br>
                  <br>
                  수료하였으므로 이 증서를 수여합니다.</font></td>
              </tr>
            </table>
            <table border="0" width="700" height="90">
              <tr> 
                <td align=center valign="bottom"><font size="4" face="궁서"><%=StringManager.substring(v_eduend,0,4)%>&nbsp;&nbsp;년&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=StringManager.substring(v_eduend,4,6)%>&nbsp;&nbsp;월&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=StringManager.substring(v_eduend,6,8)%>&nbsp;&nbsp;일</font></td>
              </tr>
            </table>
            
            <table width="560" height="280" border="0" cellspacing="0" cellpadding="0">
        		<tr>
          			<td align="center">
          			<%
          				if (v_comp.equals("K")){
          			%>
          				<img src="/images/user/study/title_k.gif" width="456" height="31">
          			<%
          				}
          				else {
          			%>
          				<font size="7" face="궁서">現&nbsp;代&nbsp;自&nbsp;動&nbsp;車&nbsp;株&nbsp;式&nbsp;會&nbsp;社</font>
          			<%
          				}
          			%>
          			</td>
          			<td align="right" width="100"><img src="/images/user/study/jikin_k.jpg" width="73" height="73"></td>
        		</tr>
      		</table>
            
            
            </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
