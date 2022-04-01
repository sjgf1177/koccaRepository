<%
/**
 * file name : za_DiplomaAdmin_P.jsp
 * date      :
 * programmer:
 * function  : 수료증 출력
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.polity.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    int     i           =  0;

    String v_name   = "";
    String v_resno  = "";
    String v_subjTitle = "";
    String v_subjnm = "";
    String v_edustart   = "";
    String v_eduend = "";
    String v_serno  = "";
    String v_today  = "";
    String v_backimg = "";
    String v_grcode = "";
    String v_grseq = "";
    String v_isunit = "";
    String v_degree = "";
    String s_grcode = box.getString("s_grcode");
    String v_yeunsuno="";
	String v_userid = "";
	String v_deptnam = "";
    
    if(s_grcode.equals(""))
    {
        v_grcode = box.getSession("tem_grcode");
    }
    else
    {
        v_grcode = s_grcode;
    }

    String v_grtype = GetCodenm.get_grtype(box,box.getSession("tem_grcode"));

    DataBox dbox = (DataBox)request.getAttribute("diplomaPrint");

    if (dbox != null) {
        v_name      = dbox.getString("d_name");
        v_resno     = dbox.getString("d_resno");
        if(v_resno.equals("-"))
            v_resno="";
        v_subjnm    = dbox.getString("d_subjnm");
        v_edustart  = dbox.getString("d_edustart");
        v_eduend    = dbox.getString("d_eduend");
        v_serno     = dbox.getString("d_serno");
        v_yeunsuno  = dbox.getString("d_yeunsuno");
        v_grseq     = dbox.getString("d_grseq");
        v_isunit    = dbox.getString("d_isunit");
        v_degree    = dbox.getString("d_degree");
        v_today     = FormatDate.getDate("yyyy년 MM월 dd일");
      //v_grcode     = dbox.getString("d_grcode");
        v_userid     = dbox.getString("d_userid");
		if(!"".equals(dbox.getString("d_grcode"))){
			v_grcode     = dbox.getString("d_grcode");
		}
    }

    if(v_grtype.equals("KOCCA"))
    {
        v_backimg = "s_back.gif";
    }
    else
    {
        v_backimg = "cetback_ko.gif";
    }

    String userAuthorityValue = box.getSession("gadmin");
    
    int busiNoLen = v_userid.indexOf("_");
    
    if(v_grcode.equals("N000113")){
        v_subjTitle = v_subjnm;
        v_subjnm = "대중문화예술기획업 등록업체 법정교육";
        
	    if(v_userid.length() == 18 ){
	        v_name = v_name + " </br/> ( " + StringManager.substring(v_userid, busiNoLen +1 , busiNoLen +7) + "-"+ StringManager.substring(v_userid, busiNoLen +7) + " )" ;
	    } else {
	        v_name = v_name + " </br/> ( " + StringManager.substring(v_userid, busiNoLen +1 , busiNoLen +4) + "-"+ StringManager.substring(v_userid, busiNoLen +4, busiNoLen +6)+ "-" + StringManager.substring(v_userid, busiNoLen +6) + " )";
	    }
	    
    }
    
            

%>
<html>
<head>
<title>수료증 출력 | 한국콘텐츠아카데미 (<%= userAuthorityValue %>)</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
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
<link rel="stylesheet" href="/css/type.css" type="text/css">
<!--link rel="stylesheet" href="/css/admin_style.css" type="text/css"-->
</HEAD>
<BODY topmargin="0" leftmargin="0"  onload="this.print()">

<!-- LEFT LINE -->
<DIV STYLE='position:absolute; top:10px; left:4px;   width:39px; height:30px; z-index:1;'> <img src="/images/admin/polity/cert_BgLeftLine01.gif" border=0 width=39 height=30></DIV>
<DIV STYLE='position:absolute; top:73px; left:4px; width:39px; height:1005px; z-index:3;'> <img src="/images/admin/polity/cert_BgLeftLine02.gif" border=0 width=39 height=1005></DIV>

<%
    if ( userAuthorityValue.equals("ZZ") ) {
%>

<!-- CENTER IMAGE (백그라운드 로고)-->
<DIV STYLE='position:absolute; top:430; left:220px; width:420px; z-index:4;'>
	<% if(v_grcode.equals("N000210")){%>
	<img src="/images/admin/polity/N000210_cert_BgLogo.jpg" border=0 width=420>
	<%}else{ %>
	<img src="/images/admin/polity/cert_BgLogo.png" border=0 width=420>
	<%} %>
</DIV>
<!-- CENTER LINE -->
<DIV STYLE='position:absolute; top:458px; left:332px; width:3px; height:366px; z-index:5;'> <img src="/images/admin/polity/cert_BgcenterLine.gif" border=0 width=3 height=366> </DIV>
<%
    }
%>
<table width="798" border="0" cellspacing="0" cellpadding="0" height="1022" >
  <tr>
    <td width="10" rowspan="14">&nbsp;</td>
    <td width="20" height="30">&nbsp;</td>
    <td height="30" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td height="29">&nbsp;</td>
    <td height="29" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3" height="34" valign="top">
      <table width="765" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="6" height="34" valign="top">&nbsp;</td>
          <td width="759" height="34" valign="middle">&nbsp; <DIV STYLE='position:absolute; top:47px; left:19px; width:460px; height:26px; z-index:300;'>
              제

              <%=v_serno %>

              호 </DIV></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td rowspan="10">&nbsp;</td>
    <td height="150" colspan="2">
      <table width="728" border="0" cellspacing="0" cellpadding="0" height="150">
        <tr>
          <td width="15">&nbsp;</td>
          <td class="certi_title">&nbsp;
          <DIV STYLE='position:absolute; top:136px; left:59px; width:367px; height:61px; z-index:301;'>
          수 료 증
          </DIV></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="20" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td height="30" colspan="2">
      <table width="728" border="0" cellspacing="0" cellpadding="0" height="30">
        <tr>
          <td width="15">&nbsp;</td>
          <td class="certi_sbj2">&nbsp;
          <DIV STYLE='position:absolute; top:245px; left:63px; width:691px; height:24px; z-index:301;'>

            <!-- KOCCA · Avid Korea 공동인증  -->

          </DIV>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="35" colspan="2">
      <table width="728" border="0" cellspacing="0" cellpadding="0" height="35">
        <tr>
          <td width="15">&nbsp;</td>
          <td class="certi_sbj">&nbsp;
          <DIV STYLE='position:absolute; top:277px; left:63px; width:686px; height:37px; z-index:301;'>
          <%=v_subjnm%>
          </DIV>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="35" colspan="2">
      <table width="728" border="0" cellspacing="0" cellpadding="0" height="35">
        <tr>
          <td width="15">&nbsp;</td>
          <td class="certi_day">&nbsp;
          <DIV STYLE='position:absolute; top:318px; left:62px; width:514px; height:25px; z-index:301;'>
          <%=v_edustart%>&nbsp;~&nbsp;
            <%=v_eduend%>
            &nbsp; 
            
            <% if(v_grcode.equals("N000210")){%>
            	(1시간)
            <% }else if(!v_grcode.equals("N000113") ){ %>
            	<%= "".equals(v_grseq) ? "" : "(" + v_grseq + "시간)" %>
            <% }else{ %>
	            <%= "".equals(v_grseq) ? "" : "(" + v_grseq + "차시)" %>
            <% }%>
            <% if(!v_yeunsuno.equals("") && v_isunit.equals("Y")){//학점인증과정일 경우
                    int grtime = Integer.parseInt(v_grseq) * 2 + 1;
            %>
                (<%= grtime %>시간)(<%=v_degree %>학점)
            <% } %>
          </DIV>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="100" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td height="400" colspan="2">
      <table border="0" cellspacing="0" cellpadding="0" height="400" width="728">
        <tr>
          <td width="275" height="400">&nbsp;</td>
          <td width="453" height="400">
            <table width="431" border="0" cellspacing="0" cellpadding="0" height="400">
              <tr>
                <td height="50" width="4">&nbsp;</td>
                <td colspan="3" class="certi_name" height="50">&nbsp;
                <DIV STYLE='position:absolute; top:434px; left:342px; width:400px; height:34px; z-index:301;'>
                <%=v_name%>
                </DIV>
                </td>
              </tr>
              <tr>
                <td height="30">&nbsp;</td>
                <td colspan="3" class="certi_JUno" height="30">&nbsp;
                <DIV STYLE='position:absolute; top:498px; left:342px; width:350px; height:17px; z-index:301;'>
                <!-- <%=v_resno%><br>  -->
                  <span style="font-size:10pt"><%=v_yeunsuno%></span>
                </DIV>
                    <br>
                </td>
              </tr>
              <tr>
                <td height="40" colspan="4">&nbsp;</td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td colspan="3" class="certi_con" valign="top" height="140"> &nbsp;
                 <DIV STYLE='position:absolute; top:544px; left:342px; width:407px; height:50px; z-index:301;'>
			<% if(v_grcode.equals("N000113")){ %> 
                    
		                위 사업자는 대중문화예술산업발전법 제 29조, <br/>
				같은 법 시행령 제 9조 및 시행규칙 제 3조에 따른 <br/>
				대중문화예술기획업자 교육과정을 이수하였으므로 <br/>
				이 증서를 수여합니다. <br/>
				*수료과정명 : <%=v_subjTitle %><br/>

            
            <% } else { %>
		            위 사람은 한국콘텐츠진흥원<br>
		                  한국콘텐츠아카데미의 상기 온라인교육과정을<br>
		                  이수하였으므로 이 증서를 수여합니다.
            <% } %>

                  


                 </DIV>
                 </td>
              </tr>
              <tr align="center">
                <td valign="middle" colspan="4">&nbsp;
                <DIV STYLE='position:absolute; top:754px; left:428px; width:133px; height:27px; z-index:301;'>
                <%=v_today%>
                </DIV>
                </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td colspan="3" class="certi">&nbsp;

                <!-- 직인 //-->
<%
    if ( userAuthorityValue.equals("ZZ") ) {
%>
                <DIV STYLE='position:absolute; top:799px; left:342px; width:406px; height:46px; z-index:301;'>
                &nbsp;&nbsp;&nbsp;&nbsp;한국콘텐츠진흥원 원장 &nbsp;(인)
                </DIV>
                <DIV STYLE='position:absolute; top:758px; left:600px; width:50px; height:50px; z-index:301;'>
                    <img src="/images/admin/polity/kbistamp.gif" border=0 width=108 height=106>
                </DIV>
<%
    } else {
%>
                <DIV STYLE='position:absolute; top:849px; left:342px; width:406px; height:46px; z-index:301;'>
                &nbsp;&nbsp;&nbsp;&nbsp;한국콘텐츠진흥원 원장 &nbsp;(인)
                </DIV>
                <DIV STYLE='position:absolute; top:808px; left:600px; width:50px; height:50px; z-index:301;'>
                    <img src="/images/admin/polity/kbistamp.gif" border=0 width=108 height=106>
                </DIV>

<%
    }
%>

                </td>
              </tr>
              <tr>
                <td height="1"></td>
                <td width="9"></td>
                <td></td>
                <td></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="80." colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td height="100" width="404">&nbsp;
    <DIV STYLE='position:absolute; top:950px; left:81px; width:284px; height:82px; z-index:301;'>
    <!-- FPT01 프로툴즈 , FAV01 아비드 -->

        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--img src="/images/admin/polity/avid.bmp" border=0-->

    </DIV>
    </td>
    <td height="100" width="364">&nbsp;
    <DIV STYLE='position:absolute; top:935px; left:423px; width:50px; height:50px; z-index:301;'>

        <img src="/images/admin/polity/kocca_logo.png" style="width:160px;">

      </DIV>
    </td>
  </tr>
  <tr>
    <td height="20" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td height="10">&nbsp;</td>
    <td height="10" colspan="2">&nbsp;</td>
  </tr>
</table>
</body>
</html>

