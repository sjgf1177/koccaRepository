<%
/**
 * file name : gu_JindanUserPaper_I.jsp
 * date      : 
 * programmer:
 * function  : 진단테스트응시
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.jindan.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	String v_upperclass = (String)request.getAttribute("p_upperclass"); 
	String v_middleclass = (String)request.getAttribute("p_middleclass"); 
	String v_lowerclass = (String)request.getAttribute("p_lowerclass"); 
	String v_jindancnt = (String)request.getAttribute("p_jindancnt");	//과목당 가져오는 문제수

	int v_jindanpoint = 100 / Integer.parseInt(v_jindancnt);

		String v_subj = "";
	    String v_userid = box.getSession("userid");
	    String v_urldir = "/dp/exam/";

	//String v_urldir = conf.getProperty("dir.upload.exam");
%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="Pragma" content="no-cache">
<TITLE> :::: 사이버 게임 아카데미 :::: </TITLE>
<link rel="stylesheet" type="text/css" href="/css/user_style1.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function init() {
        this.moveTo(0,0);
        this.resizeTo(screen.availWidth,screen.availHeight);
    }

	function onload() {
	  window.self.close();
	}

function whenSubmit(){
  var exam ="";
  var a_name ="";
  var examnum = "";
  var i=0;
  var b_name="";
  var c_name="";
  var c_value="";
  var b_type=""; 
  var result="";
  var temp=""; 
  var textarr ="";
  var answercnt=0;
  var replycnt =0;

  var v_current = new Date();
  var v_ended = "";
  var v_temp = "";

  for(i=0;i<document.form3.length;i++) {
	//  alert(document.form3.p_jindan.length);
		c_name  = document.form3.elements[i].name;
		c_value = document.form3.elements[i].value;
		if (i == 0) {
		  b_name=c_name;
		  c_name = document.form3.elements[i].name + ",";
		  examnum += c_name;
		} else {
		  if (c_name != b_name) {
			answercnt++;
	//		alert("temp" + temp.length);
			if (b_type=="text" && temp.length > 1) {
			  replycnt++;//alert("replycnt1" + replycnt);
			}
			else if(b_type!="text" && temp.length > 0) {
				replycnt++;//alert("replycnt1" + replycnt);
			}
			if (answercnt==1) {
			  result = temp;
			} else {
			  result = result + ","+ temp;
			}
			b_name = c_name;
			temp = "";
			c_name = document.form3.elements[i].name + ",";
	    	examnum += c_name;
		  }

		}
	//	alert(result);
		
		if (document.form3.elements[i].type=="radio") {
		  b_type="radio";
		  if (document.form3.elements[i].checked==true) {
			temp = c_value;
		  }
		} else if (document.form3.elements[i].type=="text"||document.form3.elements[i].type=="textarea") {
		  b_type="text";
		  temp  = "";
		  textarr = c_value.split(",");
		  for(var j=0; j<textarr.length; j++) {
			temp = temp + textarr[j];
		  }
		  temp = temp + " ";
		}
  } 
//  alert("temp" + temp.length);
	if (b_type=="text" && temp.length > 1) {
	  replycnt++;//alert("replycnt1" + replycnt);
	}
	else if(b_type!="text" && temp.length > 0) {
		replycnt++;//alert("replycnt1" + replycnt);
	}
  answercnt++;
  if (answercnt==1) {
    result = temp;    
  } else {
    result = result + ","+ temp + ",";
  }  
  
  if (replycnt < answercnt) {
    alert("응답하지 않은 문제가 있습니다.모든 문제에 응답해주시길 바랍니다.");
    return;
  }
  document.form2.p_answer.value = result;
  document.form2.p_ended.value = v_ended;
  document.form2.p_process.value = 'JindanUserResultInsert';
  document.form2.p_jindan.value = examnum;
  document.form2.submit();
}



-->
</SCRIPT>
</head>
<body topmargin="0" leftmargin="0" marginheight="0" marginwidth="0" >

<table width="700" border="0" cellpadding="0" cellspacing="0" bgcolor="F6F6F6">
  <tr>
    <td><img src="/images/user/game/apply/pop_tit_self.gif" ></td>
  </tr>
  <tr>
    <td>

		<table width="700" border="0" cellspacing="0" cellpadding="0">
        <tr background="/images/user/game/apply/pop_box_topbg.gif">
          <td width="22" height="10" valign="top" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toph.gif" width="22" height="10"></td>
          <td width="657" height="10" background="/images/user/game/apply/pop_box_topbg.gif"></td>
          <td width="21" height="10" align="right" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toptail.gif" width="21" height="10"></td>
        </tr>
        <tr>
          <td valign="top" background="/images/user/game/apply/pop_box_lbg.gif">&nbsp;</td>
          <td align="center" valign="top" bgcolor="#FFFFFF">
		  <!-- 서브타이틀 -->
		    <table width="657" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td valign="bottom" ><img src="/images/user/game/apply/pop_st_1.gif"></td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              <tr> 
                <td style="padding-left:20px;"><strong><font color="#000000">[ 
                  초급 ]</font></strong></td>
              </tr>
              <tr>
                <td height="6" style="padding-left:40px;"></td>
              </tr>
            </table>

			<!-- 문제 -->

       <table width="620" border="0"  bordercolor="red" cellspacing="0" cellpadding="0">
			<form name="form2" method="post" action="/servlet/controller.jindan.JindanUserServlet">
				<input type="hidden" name="p_subj"   >
				<input type="hidden" name="p_upperclass"      value="<%=v_upperclass%>">
				<input type="hidden" name="p_middleclass"      value="<%=v_middleclass%>">
				<input type="hidden" name="p_lowerclass"      value="<%=v_lowerclass%>">
				<input type="hidden" name="p_userid"      value="<%=v_userid%>">
				<input type="hidden" name="p_jindan"      value="">
				<input type="hidden" name="p_started"      value="<%//=v_started%>">
				<input type="hidden" name="p_jindancnt"      value="<%=v_jindancnt%>">
				<input type="hidden" name="p_jindanpoint"      value="<%=v_jindanpoint%>">
				<input type="hidden" name="p_ended"      value="">
				<input type="hidden" name="p_answer"      value="">
				<input type="hidden" name="p_process"      value="">
				<input type="hidden" name="p_isopenanswer"      value="<%//=v_isopenanswer%>">
			</form>
		<form name="form3" method="post" action="/servlet/controller.exam.JindanUserServlet">

<%

ArrayList    blist = (ArrayList)request.getAttribute("PaperQuestionJindanList");

if(blist.size() != 0 ){			//문제가 있다면

			for (int i=0; i < blist.size(); i++) {
				ArrayList list = (ArrayList)blist.get(i);  
				//System.out.println("list 사이즈:"+list.size());
				DataBox dbox = (DataBox)list.get(0);  
					v_subj += dbox.getString("d_subj")+",";

%>

              <tr> 
                <td width="7" class="quest_ba" ></td>
                <td width="25" class="quest_ba" ><img src="/images/user/game/apply/bl_quest.gif"></td>
                <td width="583" class="quest_ba" ><%="["+String.valueOf(i+1)+"]" + dbox.getString("d_jindantext")%>
			  </td>
              </tr>
              <tr> 
                <td height="10" colspan="3"></td>
              </tr>
<!-- 문제관련 이미지, 음성, 동영상 파일 시작 -->
<%      

        if (!dbox.getString("d_saveimage").equals("")) { %> 
        <tr> 
          <td bgcolor="#FFFFFF" colspan="3"> 
            <table cellspacing="0" cellpadding="0" class="img_table_out">
              <tr> 
                <td> 
                  <table cellspacing="2" cellpadding="2">
                    <tr> 
                      <td class="img_table" height="26"><img src='<%=v_urldir+dbox.getString("d_saveimage")%>'></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
<%      }  %>  

<%      if (!dbox.getString("d_saveaudio").equals("")) { %> 
        <tr> 
          <td class="board_text4"> <img src="/images/user/study/voice_icon1.gif" width="20" height="17" border="0"><a href="<%=v_urldir+dbox.getString("d_saveaudio")%>"><%=dbox.getString("d_realaudio")%></a></td>
        </tr>
<%      }  %>  

<%      if (!dbox.getString("d_savemovie").equals("")) { %> 
        <tr> 
          <td class="board_text3"> <img src="/images/user/study/meida_icon1.gif" width="20" height="17" border="0"><a href="<%=v_urldir+dbox.getString("d_savemovie")%>"><%=dbox.getString("d_realmovie")%></a></td>
        </tr>
<%      }  %>        

<!-- 문제관련 이미지, 음성, 동영상 파일 끝 -->
<!-- 보기시작 -->
<%      if (dbox.getString("d_jindantype").equals(JindanBean.OBJECT_QUESTION)) {   // 단일선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
		  <td></td>
		  <td valign="top"><%if(j == 0){%><img src="/images/user/game/apply/bl_view.gif" width="20" height="20"><%}%></td>
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="radio" name="<%=dbox.getString("d_subj")%>_<%=dbox.getInt("d_jindannum")%>" value="<%=dbox.getInt("d_selnum")%>" class='inputradio'><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%              } 
            }

        } else if (dbox.getString("d_jindantype").equals(JindanBean.OX_QUESTION)) {   // ox선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
		  <td></td>
		  <td valign="top"><%if(j == 0){%><img src="/images/user/game/apply/bl_view.gif" width="20" height="20"><%}%></td>
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="radio" name="<%=dbox.getString("d_subj")%>_<%=dbox.getInt("d_jindannum")%>" value="<%=dbox.getInt("d_selnum")%>" class='inputradio'><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%              } 
            }            
        } else if (dbox.getString("d_jindantype").equals(JindanBean.SUBJECT_QUESTION)) {  // 주관식  %>
        <tr> 
		  <td></td>
		  <td valign="top"><img src="/images/user/game/apply/bl_view.gif" width="20" height="20"></td>
          <td class="board_text3"> 
            &nbsp;&nbsp;<textarea name="<%=dbox.getString("d_subj")%>_<%=dbox.getInt("d_jindannum")%>" cols="90" rows="5" class="input"></textarea>
          </td>
        </tr>
<%             
            
        } else if (dbox.getString("d_jindantype").equals(JindanBean.MULTI_QUESTION)) {  // 다답식
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
		  <td></td>
		  <td valign="top"><%if(j == 0){%><img src="/images/user/game/apply/bl_view.gif" width="20" height="20"><%}%></td>
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="checkbox" name="<%=dbox.getString("d_subj")%>_<%=dbox.getInt("d_jindannum")%>" value="<%=dbox.getInt("d_selnum")%>" class='inputradio'><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>

<%            }
		   }
	    }
	}
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
		//출제된 과정을 콤마로구분해서 셋팅
		document.form2.p_subj.value = "<%=v_subj%>";
//-->
</SCRIPT>

<!-- ---------- -->

<%
}else{			//문제가 없다면
%>	
              <tr> 
                <td class="quest_ba" colspan="3" align="center"> 등록된 진단문제가 없습니다.</td>
              </tr>
<%
}	
%>
			  
			  </form>

		  </tr>
              <tr> 
                <td height="15" colspan="3" valign="top"></td>
              </tr>
      </table>

	<table cellspacing="0" cellpadding="0"  align="center">
		<tr>                
			<td align="right">
<%			if(blist.size() != 0 ){ //문제가 있다면 %>
			<a href="javascript:whenSubmit()"><img src="/images/user/button/btn_m_p.gif" border="0"></a><% } %>
			</td>
			<td align="right" width="65"><a href="javascript:self.close()"><img src="/images/user/button/btn_cancel.gif" border="0"></a></td>
		</tr>
	</table>


		  </td>
		</tr>
        <tr>
          <td valign="bottom" background="/images/user/game/apply/pop_box_lbg.gif"><img src="/images/user/game/apply/pop_box_boh.gif" width="22" height="21"></td>
          <td background="/images/user/game/apply/pop_box_bobg.gif">&nbsp;</td>
          <td valign="bottom" background="/images/user/game/apply/pop_box_rbg.gif"><img src="/images/user/game/apply/pop_box_botail.gif" width="21" height="21"></td>
        </tr>
      </table>
			  
			</td>
		  </tr>
		</table>
<%@ include file = "/learn/library/getJspName.jsp" %>

</BODY>
</HTML>



