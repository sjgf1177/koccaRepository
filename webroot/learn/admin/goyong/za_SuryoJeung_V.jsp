<%
/**
 * file name : za_SuryoJeung_V.jsp
 * date      : 2004/12/29
 * programmer: Lee Chang Hun
 * function  : 수료증발급대장
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
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  ss_subjseq    = box.getString("s_subjseq");          //과정 차수
    String  ss_subjcourse = box.getString("s_subjcourse");
    String  ss_companynm   = box.getString("p_companynm");
    
    String v_scsubjnm = "";
    String v_edustart = "";
    String v_eduend   = "";
    String v_place    = "";
    String v_subjseqgr= "";
    
    

	GoYongManageBean bean = new GoYongManageBean();
    DataBox titleDBox = bean.selectSuryoTitle(box, ss_subjcourse);
    
    
    if(titleDBox != null){
       v_scsubjnm  = titleDBox.getString("d_scsubjnm");
       v_edustart  = titleDBox.getString("d_edustart");
       v_eduend    = titleDBox.getString("d_eduend");
       v_place     = titleDBox.getString("d_place");
       v_subjseqgr = titleDBox.getString("d_subjseqgr");
    }

    ArrayList list = (ArrayList)bean.selectSuryoStudentList(box);
   	int listsize = list.size();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "javascript" src = "/script/user_patch.js"></script>
<script language='javascript'>object_ScriptX();</script>
<!--object id=factory classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"
codebase="/script/ScriptX.cab#Version=6,1,429,14">
</object-->

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
			putSet("", "", 30, 25, 0, 0);
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
</head>
<%
	for ( int i=0; i<listsize; i++ ) {
		DataBox dbox = (DataBox)list.get(i);
		if ( i%30 == 0 ) {
%>
<body topmargin=0 leftmargin=0>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center"><table width="680" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align=right><br></td>
        </tr>
        <tr> <td>&nbsp;</td>
        </tr>
        <tr> 
          <td align=center><font size="3"><strong>수료증 발급대장</strong></font></td>
        </tr>
        <tr> 
          <td height=25></td>
        </tr>
        <tr> 
          <td><table width="680" border="1" cellspacing="0" cellpadding="0">
              <tr bgcolor="EAF4FD"> 
                <td width="55" height=24 align="center">과정명</td>
                <td height=24 colspan="3" class=finishList>&nbsp;<%= v_scsubjnm %>(<%=StringManager.cutZero(v_subjseqgr) %>차)</td>
                <td width="100" height=24 align="center">소속사업장명</td>
                <td width="120" height=24 class=finishList>&nbsp;<%=ss_companynm%></td>
                <td width="35" height=24 align="center">인원</td>
                <td width="108" height=24 align="center">&nbsp;<%=listsize%>명</td>
              </tr>
              <tr bgcolor="EAF4FD"> 
                <td height=24 align="center">교육기간</td>
                <td height=24 colspan="3" class=finishList>&nbsp;<%= FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd") %> - <%= FormatDate.getFormatDate(v_eduend ,"yyyy/MM/dd") %></td>
                <td height=24 align="center">훈련장소</td>
                <td height=24 colspan="3" class=finishList>&nbsp;<%=v_place%></td>
              </tr>
              <tr bgcolor="F7F7F7"> 
                <td height=24 align="center">일련번호</td>
                <td width="70" height=24 align="center">성명</td>
                <td width="106" height=24 align="center">주민등록번호</td>
                <td width="68" height=24 align="center">사번</td>
                <td height=24 colspan="2" align="center">부서명</td>
                <td height=24 colspan="2" align="center">수료증번호</td>
              </tr>
<%
		}
%>
              <tr> 
                <td height=24 align="center"><%= (i+1) %></td>
                <td height=24 align="center"><%= dbox.getString("d_name") %></td>
                <td height=24 align="center"><%= StringUtil.juminno(dbox.getString("d_resno")) %></td>
                <td height=24 align="center"><%= dbox.getString("d_cono") %></td>
                <td height=24 colspan="2" class=finishList><%= dbox.getString("d_orga_ename") %></td>
                <td height=24 colspan="2" align="center"><%= dbox.getString("d_serno") %></td>
              </tr>
<%
		if ( i%30 == 29 ) {
%>
            </table></td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
<%
		}
	}
%>
</html>
