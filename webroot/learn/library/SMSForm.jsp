<%
//**********************************************************
//  1. 제      목: SMS 작성
//  2. 프로그램명 : SMSForm.jsp
//  3. 개      요: SMS 작성
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    Vector v_checks_tmp  = box.getVector("p_checks");
    Vector v_checks  = box.getVector("tmp");;
    String v_schecks = "";
    String[] arrChecks;
    String chkValue="";

    for(int i=0;i<v_checks_tmp.size();i++) {              

      v_schecks = (String)v_checks_tmp.elementAt(i);
      chkValue+=v_schecks+"/";
      //2010.02.03 userid만 넘기는 로직을 subj,year,subjseq 도 넘기도록 수정
      //if(v_schecks.indexOf(',') > 0){
      //  v_schecks = StringManager.substring(v_schecks, 0, v_schecks.indexOf(','));
      //}
      
      if(v_schecks.indexOf(',') > 0){
		arrChecks = v_schecks.split(",");
		
		if(arrChecks.length > 3) {
			v_schecks = arrChecks[0];
		} else {
			v_schecks = StringManager.substring(v_schecks, 0, v_schecks.indexOf(','));
		}
		if(arrChecks.length > 4) {
			box.put("s_grcode", arrChecks[4]);
		}
      }
	  
      v_checks.add(v_schecks);
    }

	String v_touch    = box.getString("p_touch");
    String v_msubjnm  = box.getString("p_msubjnm");
    String v_mseqgrnm = box.getString("p_mseqgrnm");
    String v_msubj    = box.getString("p_msubj");
    String v_myear    = box.getString("p_myear");
    String v_msubjseq = box.getString("p_msubjseq");

    String v_companynm = "";
    String v_userid    = "";
    String v_name      = "";
    String v_jikwinm   = "";
    String v_handphone = "";

    int i = 0;
    SmsBean smsbean = new SmsBean();
    ArrayList list = smsbean.sendUserList(box);


%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<STYLE>

TEXTAREA
{
    BORDER-RIGHT: #2574b1 0px solid; BORDER-TOP: #2574b1 0px solid; FONT-SIZE: 9pt; OVERFLOW: hidden; BORDER-LEFT: #2574b1 0px solid; BORDER-BOTTOM: #2574b1 0px solid; FONT-FAMILY: 돋움체
}

.phone1
{
    FONT-SIZE: 9pt; WIDTH: 130px; COLOR: #000000; HEIGHT: 70px; BACKGROUND-COLOR: #D8F2FB}

.byte
{
    BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; MARGIN: 0px 0px 5px; VERTICAL-ALIGN: top; WIDTH: 1.3em; COLOR: #595959; HEIGHT: 1em; BACKGROUND-COLOR: #CCFFFF; TEXT-ALIGN: center; BORDER-RIGHT-WIDTH: 0px
}

.byte2
{
    BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; MARGIN: 0px 0px 5px; VERTICAL-ALIGN: top; WIDTH: 1.7em; COLOR: #595959; HEIGHT: 1em; BACKGROUND-COLOR: #F8F8F8; TEXT-ALIGN: center; BORDER-RIGHT-WIDTH: 0px
}

</STYLE>

<SCRIPT LANGUAGE="JavaScript">
<!--
    // 등록
    function insert() {
        var r=$("#smstel").val();

        if(r=="")
        {
            alert("보내는 사람의 전화번호는 필수 입니다.");
            $("#smstel").focus();
            return;
        }
         
        if (blankCheck(document.form1.p_title.value)) {
            alert("내용을 입력하세요!");
            document.form1.p_title.focus();
            return;
        }
        cal_pre();

        document.form1.action = "/servlet/controller.library.SMSServlet";
        document.form1.p_process.value = "insert";
        document.form1.submit();

    }

    // 메세지 지우기
    function clearmsg() {
    document.form1.p_title.value = "";
    document.form1.cbyte.value = 0;
    document.form1.emotorder.value = "";
    }

    // 문자이미지 넣기
    function calculate_image(data) {
        var query = document.form1.p_title.value;
        document.form1.p_title.value = query + data;
        document.form1.p_title.focus();

        cal_pre();
    }

    // 바이트 체크
    function cal_byte(aquery) {
       var tmpStr;
       var temp=0;
       var onechar;
       var tcount;
       tcount = 0;

       tmpStr = new String(aquery);
       temp = tmpStr.length;

       for (k=0;k<temp;k++) {
            onechar = tmpStr.charAt(k);

            if (escape(onechar).length > 4) {
                 tcount += 2;
            }
            else if (onechar!='\r') {
                 tcount++;
            }
       }
       document.form1.cbyte.value = tcount;

       if(tcount>80) {
            reserve = tcount-80;
            alert("메시지 내용은 80바이트 이상은 전송하실수 없습니다.\r\n 쓰신 메세지는 "+reserve+"바이트가 초과되었습니다.\r\n 초과된 부분은 자동으로 삭제됩니다.");
            cutText();
            return;
       }
    }

    // 문자 체크
    function cal_pre() {
        var tmpStr, tcount;
        tmpStr = document.form1.p_title.value;
        cal_byte(tmpStr);
        if(document.form1.cbyte.value > 80) {
            reserve = tcount-80;
            alert("메시지 내용은 80바이트 이상은 전송하실수 없습니다.\r\n 쓰신 메세지는 "+reserve+"바이트가 초과되었습니다.\r\n 초과된 부분은 자동으로 삭제됩니다.");
            cutText(3);
            return;
        }
    }
    
    //alert("현재 준비중입니다.");
    //self.close();
//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name = "form1" method = "post">

  <input type='hidden' name = 'p_process' value=''>
  <!-- tz_humantouc insert parameter start-->
  
  <input type="hidden" name="p_touch"     value='<%=v_touch%>'>
  <input type="hidden" name="p_msubjnm"   value='<%=v_msubjnm%>'>
  <input type="hidden" name="p_mseqgrnm"  value='<%=v_mseqgrnm%>'>
  <input type="hidden" name="p_msubj"     value='<%=v_msubj%>'>
  <input type="hidden" name="p_myear"     value='<%=v_myear%>'>
  <input type="hidden" name="p_msubjseq"  value='<%=v_msubjseq%>'>
  <input type="hidden" name="p_allChk_inja"  value='<%=chkValue%>'>
  <!-- tz_humantouc insert parameter end -->

<table width="600" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
            <td align="center" bgcolor="#FFFFFF"> 
              <!----------------- title 시작 ----------------->
              <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr> 
                  <td><img src="../../images/admin/library/L_title01.gif"></td>
                  <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
              </table>
              <!----------------- title 끝 ----------------->
           <br>

           <table width="600" border="0" cellspacing="0" cellpadding="0">
             <tr>
               <td width="350" valign="top" style="padding-top:10px" align="center">
                 <div style='width:400px;height:250px;overflow:auto;visibility:visible;border:1 '>
                    <table width="350"  cellspacing="1" cellpadding="5"   class="table_out">
                      <tr>
                        <td colspan="5" class="table_top_line"></td>
                      </tr>
                      <tr>
                        <!--td class="table_title" width="22">NO</td>
                        <td class="table_title" width="82">회사</td>
                        <td class="table_title" width="93">ID</td>
                        <td class="table_title" width="55">성명</td>
                        <td class="table_title" width="54">직위</td-->
                        <td class="table_title" width="22">NO</td>
                        <td class="table_title">ID</td>
                        <td class="table_title">성명</td>
                        <td class="table_title">전화번호</td>
                      </tr>
<%

            for (i=0; i<list.size();i++) {
                DataBox dbox   = (DataBox)list.get(i);

                v_companynm    = dbox.getString("d_companynm");
                v_userid     = dbox.getString("d_userid");
                v_name       = dbox.getString("d_name");
                v_jikwinm    = dbox.getString("d_jikwinm");
                v_handphone  = dbox.getString("d_handphone");
%>
                      <tr>
                        <td class="table_01"><%=i+1%></td>
                        <td class="table_02_1"><%=v_userid%></td>
                        <td class="table_02_1"><%=v_name%></td>
                        <td class="table_02_1"><%=v_handphone%></td>
                        <input type='hidden' name = 'p_checks' value='<%=v_userid%>'>
                        <input type='hidden' name = 'p_handphone' value='<%=v_handphone%>'>
                      </tr>
<%
            }
            if (i==0){
%>
                        <tr>
                          <td class="table_01" colspan="5">지정된 인원이 없습니다.</td>
                        </tr>
<%
            }
%>
                    </table>
                    <br>
                 </div>
               </td>
                <td width="250" valign="top" style="padding-top:10px;padding-bottom:10px;" align="center">

                    <!--핸드폰문자시작 -->
                    <table width="169" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td height="130" background="/images/admin/study/phone_screen1.gif" align="center" valign="bottom" colspan="2">
                                <table width="150" border="0" cellspacing="0" cellpadding="0">
                                  <tr align="center">
                                    <td colspan="2" height="80">
                                      <font size=2> <textarea class=phone1 onKeyUp=javascript:cal_pre(); name=p_title rows=5 cols=15></textarea></font>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td width="79" align="center" class="fs8_h15"><a href="javascript:clearmsg();">[지우기]</a></td>
                                    <td align="right" width="81">
                                      <table width="60" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td width="33" valign="bottom">
                                            <input class=byte value=0 name=cbyte size="3">
                                          </td>
                                          <td width="59" valign="top"><font face="Arial, Helvetica, sans-serif" size="-1">/80<font size="-2">byte</font></font></td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                            <tr>
                              <td style="padding-top:5px" colspan="2">
                                <table width="166" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td style="padding-top:1px" width="19" height="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('☆');">☆</SPAN>
                                        </td>
                                        <td width="2"><img src="/images/admin/button/trans.gif" width="2" height="2"></td>
                                        <td style="padding-top:1px" width="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('★');">★</SPAN>
                                        </td>
                                        <td width="2"><img src="/images/admin/button/trans.gif" width="2" height="2"></td>
                                        <td style="padding-top:1px" width="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('♡');">♡</SPAN>
                                        </td>
                                        <td width="2"><img src="/images/admin/button/trans.gif" width="2" height="2"></td>
                                        <td style="padding-top:1px" width="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('♥');">♥</SPAN>
                                        </td>
                                        <td width="2"><img src="/images/admin/button/trans.gif" width="2" height="2"></td>
                                        <td style="padding-top:1px" width="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('♧');">♧</SPAN>
                                        </td>
                                        <td width="2"><img src="/images/admin/button/trans.gif" width="2" height="2"></td>
                                        <td style="padding-top:1px" width="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('♣');">♣</SPAN>
                                        </td>
                                        <td width="2"><img src="/images/admin/button/trans.gif" width="2" height="2"></td>
                                        <td style="padding-top:1px" width="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('▶');">▶</SPAN>
                                        </td>
                                        <td width="2"><img src="/images/admin/button/trans.gif" width="2" height="2"></td>
                                        <td style="padding-top:1px" width="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('◀');">◀</SPAN>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="2" colspan="15"><img src="/images/admin/button/trans.gif" width="1" height="2"></td>
                                    </tr>
                                    <tr>
                                        <td style="padding-top:1px"  height="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('☜');">☜</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px"  background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('☞');">☞</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px"  background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('☎');">☎</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px"  background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('♨');">♨</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px"  background="/images/admin/button/sms_button.gif" align="center">
                                        <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('※');">※</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px"  background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('♩');">♩</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px"  background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('♪');">♪</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('♬');">♬</SPAN>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="2" colspan="15"><img src="/images/admin/button/trans.gif" width="1" height="2"></td>
                                    </tr>
                                    <tr>
                                        <td style="padding-top:1px" height="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('●');">●</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px" height="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('○');">○</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px" height="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('◇');">◇</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px" height="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('◆');">◆</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px" height="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('□');">□</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px" height="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('■');">■</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px" height="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('△');">△</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px" height="19" background="/images/admin/button/sms_button.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('▲');">▲</SPAN>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="2" colspan="15"><img src="/images/admin/button/trans.gif" width="1" height="2"></td>
                                    </tr>
<!--
                                    <tr>
                                        <td style="padding-top:1px" height="19" colspan="3" background="/images/admin/button/sms_button2.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('(^.^)');" title="ㅋㅋㅋ">(^.^)</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px" colspan="3" background="/images/admin/button/sms_button2.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('(^_^)');" title="흐뭇">(^_^)</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px" colspan="3" background="/images/admin/button/sms_button2.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('*^^*');" title="싱긋">*^^*</SPAN>
                                        </td>
                                        <td></td>
                                        <td colspan="3" background="/images/admin/button/sms_button2.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('^_^∂');" title="전화해~">^_^∂</SPAN>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="2" colspan="15"><img src="/images/admin/button/trans.gif" width="1" height="2"></td>
                                    </tr>
                                    <tr>
                                        <td style="padding-top:1px" height="19" colspan="3" background="/images/admin/button/sms_button2.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('-_-;;');" title="모냐?">-_-;;</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px" height="19" colspan="3" background="/images/admin/button/sms_button2.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('(-_-)');" title="썰렁~">(-_-)</SPAN>
                                        </td>
                                        <td></td>
                                        <td style="padding-top:1px" height="19" colspan="3" background="/images/admin/button/sms_button2.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('⊙.⊙');" title="깜짝이야">⊙.⊙</SPAN>
                                        </td>
                                        <td></td>                                                                                 s
                                        <td style="padding-top:1px" height="19" colspan="3" background="/images/admin/button/sms_button2.gif" align="center">
                                            <SPAN style="CURSOR: hand" onclick="javascript:calculate_image('(*_*)');" title="아이쿠">(*_*)</SPAN>
                                        </td>
                                    </tr>
-->
                                </table>
                              </td>
                            </tr>
                          </table>
                          <br>

                          <table width="173" border="0" cellspacing="0" cellpadding="0" dwcopytype="CopyTableRow">
                            <tr>
                              <td colspan=3>보내는 사람 &nbsp; <input type="text" id="smstel" name="from" value="<%= conf.getProperty("sms.admin.comptel") %>" size="15" style="width:95px"/></td>
                                <script type="text/javascript">
                                    var online="<%= conf.getProperty("sms.admin.comptel") %>";
                                    var offline="<%= conf.getProperty("sms.admin.offcomptel") %>";
                                    var r=opener.parent.frames["fmenu"].last_menu;
                                    if(r=="20000000")
                                        $("#smstel").val(offline);
                                    else
                                        $("#smstel").val(online);
                                </script>
                            </tr>

                            <tr>
                              <td style="padding:15 0 0 0" align="center" colspan="2">
                                <table width="127" border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                    <td width="61"><a href="javascript:insert()"><img src="/images/admin/button/btn_smsend.gif"  border="0"></a></td>
                                    <td width="5">&nbsp;</td>
                                    <td width="61"><a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif"  border="0"></a></td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                        </table>
                    <INPUT type="hidden" name="gn">
                    <INPUT type="hidden" name="emotorder" value="">
                    <!--핸드폰문자끝 -->
                </td>
              </tr>
            </table>
            <br>

          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
