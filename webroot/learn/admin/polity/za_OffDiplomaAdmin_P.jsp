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
    String v_edudays = "";
    String v_edudaystype = "";
    String v_middleclass = "";
    String s_grcode = box.getString("s_grcode");

    String v_year ="";
    String v_subj ="";
    String v_subjseq="";
    String v_time ="";

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
        v_subjnm    = dbox.getString("d_subjnm");
        v_edustart  = dbox.getString("d_edustart");
        v_eduend    = dbox.getString("d_eduend");
        v_serno     = dbox.getString("d_serno");

        v_grseq     = dbox.getString("d_grseq");
        v_today     = v_eduend.substring(0,4)+"년 " + v_eduend.substring(5,7)+ "월 " + v_eduend.substring(8,10)+ "일";
//      v_today     = FormatDate.getDate("yyyy년 MM월 dd일");

        v_isunit    = dbox.getString("d_isunit");
        v_degree    = dbox.getString("d_degree");

        v_edudays   = dbox.getString("d_edudays");
        v_edudaystype   = dbox.getString("d_edudaystype");
        v_middleclass   = dbox.getString("D_middleclass");

        v_year  = dbox.getString("d_year");
        v_subj  = dbox.getString("d_subj");
        v_subjseq   = dbox.getString("d_subjseq");
        if (v_year.equals("2010") && v_subj.equals("SB10042") && v_subjseq.equals("1")) v_time ="19시간";
    }

    if(v_grtype.equals("KOCCA"))
    {
        v_backimg = "s_back.gif";
    }
    else
    {
        v_backimg = "cetback_ko.gif";
    }

    if( v_edudaystype.equals("Y") ) {
        v_edudaystype = "년";
    } else if( v_edudaystype.equals("M") ) {
        v_edudaystype = "개월";
    } else if( v_edudaystype.equals("W") ) {
        v_edudaystype = "주";
    } else if( v_edudaystype.equals("D") ) {
        v_edudaystype = "일";
    }

    if( v_middleclass.equals("001") ) { // 단기
        v_middleclass = "산업계 직무재교육";
    } else if( v_middleclass.equals("002") ) { // 장기
        v_middleclass = "차세대 인재 양성 교육";
    } else if( v_middleclass.equals("003") ) { // 수탁
        v_middleclass = "수탁교육";
    } else if( v_middleclass.equals("004") ) { // 3D
        v_middleclass = "3D 입체콘텐츠 전문인력양성교육";
    }

    String userAuthorityValue = box.getSession("gadmin");

%>
<html>
<head>
<title>수료증 출력 | 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
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
<script type="text/javascript">
    $(document).ready(function(){
        var r=ScriptEngineMinorVersion();
        if(r==6) //ie8에서는 정상 출력 되나 6에서는 두장이 출력되어 여백을 제거함
            $(".a1a").remove();
    });
    function ScriptEngineMinorVersion(){
        if( navigator.appName.indexOf("Microsoft") > -1 ){         // 인터넷 익스플로러인지 확인
           if( navigator.appVersion.indexOf("MSIE 6") > -1)       // 익스플로러이면 버전 6인지 확인
               return 6
           else if(navigator.appVersion.indexOf("MSIE 7") > -1)  // 익스 플로러이면 버전 7인지 확인
               return 7
            else if(navigator.appVersion.indexOf("MSIE 8") > -1)  // 익스 플로러이면 버전 7인지 확인
               return 8
       }else                                                      // 익스플로러가 아닐 경우
          return 0;
     }
</script>

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

<!-- CENTER IMAGE -->
<DIV STYLE='position:absolute; top:380px; left:182px; width:394px; height:346px; z-index:4;'> <img src="/images/admin/polity/cert_BgLogo.gif" border=0 width=394 height=346></DIV>
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
          <td width="759" height="34" valign="middle">&nbsp; <DIV STYLE='position:absolute; top:47px; left:19px; width:419px; height:26px; z-index:300;'>
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
          <DIV STYLE='position:absolute; top:170px; left:59px; width:367px; height:61px; z-index:301;'>
          <font style="font-size:36pt; font-weight: bold;">수 료 증</font>
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
          <td class="certi_JUno">&nbsp;
          <DIV STYLE='position:absolute; top:307px; left:63px; width:686px; height:37px; z-index:301;'>
          <font style="font-size:14pt;">한국콘텐츠아카데미</font>
          </DIV>
          </td>
        </tr>
        <tr>
          <td width="15">&nbsp;</td>
          <td class="certi_sbj">&nbsp;
          <DIV STYLE='position:absolute; top:344px; left:63px; width:686px; height:37px; z-index:301;'>
          <font style="font-size:23pt;"><%= v_subjnm %></font>
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
          <DIV STYLE='position:absolute; top:400px; left:62px; width:514px; height:25px; z-index:301;'>
          <br/><font style="font-size:13pt;"><%=v_edustart%>&nbsp;~&nbsp;<%=v_eduend%></font>

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
                <DIV STYLE='position:absolute; top:554px; left:362px; width:227px; height:34px; z-index:301;'>
                <font style="font-size:29pt; font-weight:bold;"><%=v_name%></font>
                </DIV>
                </td>
              </tr>
              <tr>
                <td height="30">&nbsp;</td>
                <td colspan="3" class="certi_JUno" height="30">&nbsp;
                <DIV STYLE='position:absolute; top:568px; left:362px; width:100%; height:17px; z-index:301;'>
                <!--<%=v_resno%>-->
                </DIV>
                </td>
              </tr>
              <tr>
                <td height="40" colspan="4">&nbsp;</td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td colspan="3" class="certi_con" valign="top" height="140"> &nbsp;
                 <DIV STYLE='position:absolute; top:682px; left:362px; width:407px; height:50px; z-index:301;'>

                    <font style="font-size:15pt; line-height:34pt;">
                    위 사람은 한국콘텐츠진흥원아카데미<br>
                    <%=v_middleclass %>에 참여하여<br>
                    상기 교육과정을 이수하였으므로<br>
                    이 증서를 수여합니다.
                    </font>


                 </DIV>
                 </td>
              </tr>
              <tr align="center">
                <td valign="middle" colspan="4" class="certi_con">
<%
    if ( userAuthorityValue.equals("ZZ") ) {
%>
                <DIV STYLE='position:absolute; top:947px; left:300px; width:100%; height:27px; z-index:301;text-align:center;'>
                <font style="font-size:14pt;"><%=v_today%></DIV>
<%
    } else {
%>
                <DIV STYLE='position:absolute; top:947px; left:300px; width:100%; height:27px; z-index:301;text-align:center;'>
                <font style="font-size:14pt;"><%=v_today%></DIV>
                </DIV>
<%
    }
%>
                </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td colspan="3" class="certi">&nbsp;

<%
    if ( userAuthorityValue.equals("ZZ") ) {
%>
                <DIV STYLE='position:absolute; top:992px; left:330px; width:406px; height:46px; font-size: 18pt; z-index:301;'>
                <font style="font-size:21pt;">한국콘텐츠진흥원 원장 &nbsp;(인)</font>
                </DIV>

                  <DIV STYLE='position:absolute; top:951px; left:640px; width:50px; height:50px; z-index:301;font-size: 18pt;'>
                    <img src="/images/admin/polity/kbistamp.gif" border="0" width="108" height="106">
                  </DIV>
<%
    } else {
%>
                <DIV STYLE='position:absolute; top:992px; left:330px; width:406px; height:46px; font-size: 18pt; z-index:301;'>
                <font style="font-size:21pt;">한국콘텐츠진흥원 원장 &nbsp;(인)</font>
                </DIV>

                <DIV STYLE='position:absolute; top:951px; left:640px; width:50px; height:50px; z-index:301;'>
                    <img src="/images/admin/polity/kbistamp.gif" border="0" width="108" height="106">
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
  <tr class="a1a">
    <td height="80" colspan="2">&nbsp;</td>
  </tr>
  <tr class="a1a">
    <td height="100" width="404">&nbsp;
    <DIV STYLE='position:absolute; top:950px; left:81px; width:284px; height:82px; z-index:301;'>
    <!-- FPT01 프로툴즈 , FAV01 아비드 -->

        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--img src="/images/admin/polity/avid.bmp" border=0-->

    </DIV>
    </td>
    <td height="100" width="364" class="a1a">&nbsp;
    <DIV STYLE='position:absolute; top:935px; left:423px; width:50px; height:50px; z-index:301;'>
        <!--
        <img src="/images/admin/polity/kbi_newlogo.jpg">
         -->
      </DIV>
    </td>
  </tr>
  <tr class="a1a">
    <td height="20" colspan="2">&nbsp;</td>
  </tr>
  <tr class="a1a">
    <td height="10">&nbsp;</td>
    <td height="10" colspan="2">&nbsp;</td>
  </tr>
</table>
</body>
</html>

