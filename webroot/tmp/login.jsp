<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import="java.security.*" %>
<%@ page import="com.credu.scorm.multi.Base64Decoder" %>
<%@ page import="com.initech.util.Base64Util" %>
<%@ page import="com.credu.scorm.multi.Base64Encoder" %>
<%
    String gubun = request.getParameter("gubun_inja");
    String [] startgubun={""};  //현재 사용중인 설문
    boolean sw=true;

    if(gubun==null)
        gubun="A0001";

    MessageDigest md = MessageDigest.getInstance("MD5");

    String strENCData = "";
    byte[] bytData = gubun.getBytes();
    md.update(bytData);
    byte[] digest = md.digest();
    for(int i =0;i<digest.length;i++)
    {
        strENCData = strENCData + Integer.toHexString(digest[i] & 0xFF).toUpperCase();
    }
    
    for(int i=0;i<startgubun.length;i++)
    {
        if(startgubun[i].equals(strENCData))
        {
            sw=false;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<title>한국콘텐츠진흥원 사이버콘텐츠아카데미</title>
<link href="http://www.wecon.kr/sub/kocca/css/style.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<style type="text/css">
<!--
.style4 {font-size: 11px}
.style5 {
	color: #FF0000;
	font-weight: bold;
}
-->
</style></head>
<script type="text/javascript">
<!--
	function sendOK() {
        var id=$("[name='id']").val();
        var pwd=$("[name='pwd']").val();

		if(id == "" || pwd == "") {
			alert("아이디와 비밀번호를 입력하여 주세요");
			return;
		}

		document.mapForm.target = "_top";
		document.mapForm.submit();
	}
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
</head>

<body>
<div style="height:200px"></div>
<table width="330" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td height="30"><img src="http://www.wecon.kr/sub/kocca/member/img/title_02.gif" width="230" height="14" /></td>
    </tr>
    <tr>
        <td align="left">
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td><img src="http://www.wecon.kr/sub/kocca/member/img/login_top.gif" height="14" /></td>
                </tr>
                <tr>
                    <td background="http://www.wecon.kr/sub/kocca/member/img/login_bg.gif" height="5"></td>
                </tr>
                    <form name="mapForm" method="post" action="/servlet/controller.community.CommunityRiskServlet" onsubmit="sendOK()">
                    <input type="hidden" name="p_process" value="login">
                    <input type="hidden" name="p_gubun_inja" value="<%=gubun%>">

                <tr>
                    <td background="http://www.wecon.kr/sub/kocca/member/img/login_bg.gif">
                        <table width="262" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="186">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="45%" ><img src="http://www.wecon.kr/sub/kocca/member/img/title_id.gif" alt="통합아이디" width="74" height="16" /></td>
                                            <td width="55%" >
                                                <div align="center">
                                                    <input name="id" type="text" style = "width: 84px"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td height="5"></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td><img src="http://www.wecon.kr/sub/kocca/member/img/title_pw.gif" alt="비밀번호" width="74" height="16" /></td>
                                            <td>
                                                <div align="center">
                                                    <input name="pwd" type="password" style = "width: 84px;height: 15px" onKeyUp="javascript: if (event.keyCode == 13) {sendOK();} "/>
                                                </div>
                                            </td>
                                        </tr>

                                    </table>
                                </td>
                                <td width="76"><a href="javascript:sendOK()"><img src="http://www.wecon.kr/sub/kocca/member/img/btn_login_b.gif" alt="확인" width="73" height="52" /></a></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </form>
                <tr>
                    <td height="30" background="http://www.wecon.kr/sub/kocca/member/img/login_bg.gif" align="center" ><b>이벤트 참여를 위해 로그인을 해주시기 바랍니다.</b></td>
                </tr>
                <tr>
                    <td><img src="http://www.wecon.kr/sub/kocca/member/img/login_bottom.gif" height="14" /></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td height="20px"></td>
    </tr>
    <tr>
        <td align="center"><span style="color:blue"><b>통합아이디를 발급받으셔야 <br>이벤트에 참여하실 수 있습니다.</b></span></td>
    </tr>
    <tr>
        <td height="20px"></td>
    </tr>
    <tr>
        <td align="center">
            <a href="http://www.wecon.kr/sub/kocca/member/join_2.jsp?login=&rtnURL=http://edu.kocca.or.kr/tmp/login.jsp">
                <img src="/tmp/images/btn_id_go.gif" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image11','','/tmp/images/btn_id_go_o.gif',1)" id="Image11">
            </a>
        </td>
    </tr>
</table>
</body>
</html>
<%
        break;
    }
}
    if(sw)
        out.print("<script> alert('설문이 완료되었습니다. 참여해주셔서 감사합니다.') </script>");
%>