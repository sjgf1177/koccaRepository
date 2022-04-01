<%
/**
 * file name : zu_SulmunTargetUserPaper_I.jsp
 * date      : 2003/09/12
 * programmer:
 * function  : 설문 응시
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

    // Request 데이타 
    RequestBox    boxreq = (RequestBox)request.getAttribute("SulmunRequestData");
    String p_sulpapernm = boxreq.getString("p_sulpapernm"); 

    // 문제 데이타 
    DataBox    dbox0 = (DataBox)request.getAttribute("SulmunPaperData");	
    String v_grcode     = dbox0.getString("d_grcode"); 
	String v_subj       = dbox0.getString("d_subj"); 
	String v_subjseq    = dbox0.getString("d_subjseq"); 
    String v_gyear      = dbox0.getString("d_year"); 
    String v_sulpapernum = dbox0.getString("d_sulpapernum"); 
    String v_sulpapernm = dbox0.getString("d_sulpapernm"); 
    String v_sulnums    = dbox0.getString("d_sulnums"); 

    String v_userid = box.getString("p_userid");

	String v_reloadlist = box.getString("p_reloadlist");  // System.out.println("v_reloadlist" + v_reloadlist)

%>
<html>
<head>
<title>설문 응시</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style_study.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function onload() {
  window.self.close();
}
function whenSubmit(){
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

    for(i=0;i<document.form3.length;i++) {
		c_name  = document.form3.elements[i].name;
		c_value = document.form3.elements[i].value;
		if (i == 0) {
		  b_name=c_name;
		} else {
		  if (c_name != b_name && eval(c_name.indexOf("|")) == -1) {
			answercnt++;
			//alert("temp" + b_type + temp.length);
			if (b_type=="text" && temp.length > 1) {
			  replycnt++;//alert("replycnt1" + replycnt);
			}
			else if(b_type!="text" && temp.length > 0) {
				replycnt++;//alert("replycnt2" + replycnt);
			}
			if (answercnt==1) {
			  result = temp;
			} else {
			  result = result + ","+ temp;
			}
			b_name = c_name;
			temp = "";
		  }

		}
	    if (document.form3.elements[i].type=="checkbox") {
		  b_type="checkbox";
		  if (document.form3.elements[i].checked==true) {
			temp = temp+c_value+':';
		  }
		} else if (document.form3.elements[i].type=="radio") {
		  b_type="radio";
		  if (document.form3.elements[i].checked==true) {
		  
			  //if (eval(document.form3.elements[i+1].name.indexOf("|")) > 0) {
			  if (eval(document.form3.elements[i].name.indexOf("|")) > 0) {
				  temp  = "";
				  //textarr = document.form3.elements[i+1].value.split(",");
				  textarr = document.form3.elements[i].value.split(",");
				  for(var j=0; j<textarr.length; j++) {
					temp = temp + textarr[j];
				  }
				  temp = c_value+':'+temp+" ";//alert("temp1" + temp);
			  }
			  else {
				  temp = c_value;//alert("temp2" + temp);
			  }
		  }
		} else if (document.form3.elements[i].type=="text"||document.form3.elements[i].type=="textarea") {
			if(eval(document.form3.elements[i].name.indexOf("|")) == -1) {
				b_type="text";
				temp  = "";
				textarr = c_value.split(",");
				for(var j=0; j<textarr.length; j++) {
					temp = temp + textarr[j];
				}
				temp = temp + " ";
			}
		}
  } 
  //alert("temp2" + b_type + temp.length);
	if (b_type=="text" && temp.length > 1) {
	  replycnt++;//alert("replycnt1" + replycnt);
	}
	else if(b_type!="text" && temp.length > 0) {
		replycnt++;//alert("replycnt2" + replycnt);
	}
  answercnt++;
  if (answercnt==1) {
    result = temp;    
  } else {
    result = result + ","+ temp + ",";
  }  
 
  //alert("result" + result);
  //alert("replycnt" + replycnt);
  //alert("answercnt" + answercnt);
  if (replycnt < answercnt) {
    alert("응답하지 않은 문제가 있습니다.모든 문제에 응답해주시길 바랍니다.");
    return;
  }
  document.form2.p_answers.value = result;
  document.form2.p_process.value = "SulmunUserResultInsert2";
  document.form2.submit();
}
<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage();
<% } %>
-->
</SCRIPT>
</head>
<body  leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0"  <%=v_onload%>>

<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="650" height="100%">
    <tr>
        <td>
            <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
                <tr>
                    <td align="center" valign="top" class="body_color">
<form name="form2" method="post" action="/servlet/controller.research.SulmunTargetMailingServlet">
    <input type="hidden" name="p_grcode"    value="<%=v_grcode%>">
    <input type="hidden" name="p_subj"    value="<%=v_subj%>">
    <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
    <input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
    <input type="hidden" name="p_sulpapernum"      value="<%=v_sulpapernum%>">
    <input type="hidden" name="p_userid"      value="<%=v_userid%>">
    <input type="hidden" name="p_sulnums"      value="<%=v_sulnums%>">
	<input type="hidden" name="p_reloadlist"      value="true">
    <input type="hidden" name="p_answers"      value="">
    <input type="hidden" name="p_process"      value="">
</form>
<form name="form3" method="post" action="/servlet/controller.research.SulmunTargetMailingServlet">
              <br>
              <!----------------- 타이틀 시작 ----------------->
              <table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
    						<tr> 
      							<td><img src="/images/user/study/question_title2.gif" alt="설문조사"></td>
      							<td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
      							<td><img src="/images/user/study/gongi_tail.gif"></td>
    						</tr>
  						</table>
            		<!----------------- 타이틀 끝 ---------------->
<br>
              <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
                <tr> 
                  <td height="5" colspan="2"></td>
                </tr>
                <tr> 
                  <td width="69"><img src="/images/user/study/poll_leftimg.gif" width="69" height="51"></td>
                  <td><table width="540" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="72"><img src="/images/user/study/poll_pollname.gif"></td>
                        <td width="408" class="tblfont_sindotit"> <b>   <%=p_sulpapernm%>  </b></td>
                      </tr>
                      <tr > 
                        <td height="5" colspan="2" background="/images/user/study/stu_box_vline2.gif"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr> 
                  <td height="5" colspan="2"></td>
                </tr>
              </table>
<br>
<!----------------- 학습평가 시작 ---------------->
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
  <tr> 
    <td class="board_color_line"></td>
  </tr>
</table>
<table cellspacing="0" cellpadding="0" class="open_board_table_out2" align="center">
  <tr> 
    <td> 
      <table cellspacing="1" cellpadding="3" class="table2">
<% 
        ArrayList    blist = (ArrayList)request.getAttribute("PaperQuestionExampleList");
    
	for (int i=0; i < blist.size(); i++) {
        ArrayList list = (ArrayList)blist.get(i);
        DataBox dbox = (DataBox)list.get(0);  

%>
        <tr> 
          <td height="20" class="board_title_bg0"><%="["+String.valueOf(i+1)+"]     " + dbox.getString("d_sultext")%>
		  <%if (dbox.getString("d_sultype").equals(SulmunTargetBean.MULTI_QUESTION)){%>(<%=dbox.getString("d_selmax")%>개까지 선택 가능)<%}%>
		  </td>
        </tr>
<%      if (dbox.getString("d_sultype").equals(SulmunTargetBean.OBJECT_QUESTION)) {   // 단일선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%              }
            }
        } else if (dbox.getString("d_sultype").equals(SulmunTargetBean.MULTI_QUESTION)) {  // 복수선택
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="checkbox" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%              } 
            }
        } else if (dbox.getString("d_sultype").equals(SulmunTargetBean.SUBJECT_QUESTION)) {  // 서술형  %>
        <tr> 
          <td class="board_text3" height="100"> 
            &nbsp;&nbsp;<textarea name="<%=dbox.getInt("d_sulnum")%>" cols="90" rows="5" class="input" maxlength="200"></textarea>
          </td>
        </tr>
<%
		} else if (dbox.getString("d_sultype").equals(SulmunTargetBean.COMPLEX_QUESTION)) {  // 복합형
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%          }
				if (j == (list.size()-1)) {    %>
        <tr> 
          <td class="board_text4">기타의견</td>
        </tr>
        <tr> 
          <td class="board_text3"> 
            &nbsp;&nbsp;<textarea name="<%=dbox.getInt("d_sulnum")%>|C" cols="90" rows="5" class="input" maxlength="200"></textarea>
          </td>
        </tr>
<%				
				}
           } 
        } else if (dbox.getString("d_sultype").equals(SulmunTargetBean.FSCALE_QUESTION)) {  // 5점척도
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%          
                }
           } 
        } else if (dbox.getString("d_sultype").equals(SulmunTargetBean.SSCALE_QUESTION)) {  // 7점척도
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%          
                }
           } 
        } 
	}
%>
      </form>
      </table>
    </td>
  </tr>
</table>
<!----------------- 학습평가 끝 ---------------->
<br>
<!----------------- 제출 버튼 시작 ---------------->
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
    <tr>                
        <td align="right"><a href="javascript:whenSubmit()"><img src="/images/user/study/presentation_butt1.gif" alt="제출" width="54" height="20" border="0"></a></td>
        <td align="right" width="65"><a href="javascript:self.close()"><img src="/images/user/study/close_butt.gif" alt="닫기" width="54" height="20" border="0"></a></td>
    </tr>
</table>
<br></td>
</tr> </table> </td> </tr> </table> 
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
