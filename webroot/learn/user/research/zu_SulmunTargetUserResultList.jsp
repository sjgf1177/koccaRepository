2006-01-25<%
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
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

    DataBox    dbox0 = (DataBox)request.getAttribute("SulmunPaperData");

    String v_grcode     = dbox0.getString("d_grcode"); 
	String v_subj     = dbox0.getString("d_subj"); 
	String v_subjseq     = dbox0.getString("d_subjseq"); 
    String v_gyear     = dbox0.getString("d_year"); 
    String v_sulpapernum = dbox0.getString("d_sulpapernum"); 
    String v_sulpapernm = dbox0.getString("d_sulpapernm"); 
    String v_sulnums = dbox0.getString("d_sulnums"); 

    DataBox    dbox1 = (DataBox)request.getAttribute("SulmunUserData");

    String v_userid = dbox1.getString("d_userid");
    String v_cono = dbox1.getString("d_cono");
    String v_asgnnm = dbox1.getString("d_asgnnm");
    String v_jikupnm = dbox1.getString("d_jikupnm");
    String v_name = dbox1.getString("d_name");

%>
<html>
<head>
<title>설문 응시</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_research.css" type="text/css">
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
      if (eval(b_name.indexOf("|")) > 0) {
        answercnt++;
        if (b_type=="text" || temp !="") {
          replycnt++;
        }
        if (answercnt==1) {
          result = temp;
        } else {
          result = result + temp  ;
        }
        b_name = c_name;
        temp = "";	  
	  } else if (c_name != b_name ) {
        answercnt++;
        if (b_type=="text" || temp !="") {
          replycnt++;
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
        temp = c_value;
      }
    } else if (document.form3.elements[i].type=="text"||document.form3.elements[i].type=="textarea") {
            b_type="text";
            temp  = "";
            textarr = c_value.split(",");
		 if (eval(c_name.indexOf("|"))>0){
            for(var j=0; j<textarr.length; j++) {
               temp =  temp + textarr[j];
            }
			temp = ":" + temp + ":";
		 } else {
             for(var j=0; j<textarr.length; j++) {
                 temp = temp + textarr[j];
             }
         }
	}
  }
  if (b_type=="text" || temp !="") {
    replycnt++;
  }
  answercnt++;
  if (answercnt==1) {
    result = temp;    
  } else if (eval(b_name.indexOf("|"))>0) {
    result = result + temp  ;  
  }else {
    result = result + ","+ temp;
  }

//alert(result);

  if (replycnt < answercnt) {
    alert("응답하지 않은 설문이 있습니다.모든 설문에 응답해주시길 바랍니다.");
    return;
  }
  document.form2.p_answers.value = result;
  document.form2.p_process.value = "SulmunUserResultInsert";
  document.form2.submit();
}
-->
</SCRIPT>
</head>
<body <%=v_onload%>>
<form name="form2" method="post" action="/servlet/controller.research.SulmunTargetUserServlet">
    <input type="hidden" name="p_grcode"    value="<%=v_grcode%>">
    <input type="hidden" name="p_subj"    value="<%=v_subj%>">
    <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
    <input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
    <input type="hidden" name="p_sulpapernum"      value="<%=v_sulpapernum%>">
    <input type="hidden" name="p_userid"      value="<%=v_userid%>">
    <input type="hidden" name="p_sulnums"      value="<%=v_sulnums%>">
    <input type="hidden" name="p_answers"      value="">
    <input type="hidden" name="p_process"      value="">
</form>
<form name="form3" method="post" action="/servlet/controller.research.SulmunTargetUserServlet">
<!----------------- 타이틀 시작 ----------------->
<table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
  <tr>                 
		<td background="/images/user/research/b_title_dot.gif"><img src="/images/user/research/question_title2.gif" alt="설문조사" width="240" height="37" border="0"></td>
		<td background="/images/user/research/b_title_dot.gif">&nbsp;</td>
		<td background="/images/user/research/b_title_dot.gif" width= 100 align='right'><b><span id=time></span></b></td>
  </tr>
  <tr><td height="7"></td></tr>
</table>
<!----------------- 타이틀 끝 ---------------->
<br>
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
	<tr> 
		<td width="13"><img src="/images/user/research/icon2.gif" width="13" height="11" border="0"></td>
		<td><b>설문지명 : <%=v_sulpapernm%></b></td>
     </tr>
	<tr> 
		<td width="13"><img src="/images/user/research/icon2.gif" width="13" height="11" border="0"></td>
		<td><b>연도 : <%=v_gyear%></b></td>
    </tr>
	<tr> 
		<td width="13"><img src="/images/user/research/icon2.gif" width="13" height="11" border="0"></td>
		<td><b>대상 : <%=v_asgnnm%>/<%=v_jikupnm%>/<%=v_cono%>/<%=v_name%></b></td>
	</tr>
  <tr> 
    <td height="5"></td>
  </tr>
</table>
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
        DataBox    dbox2 = (DataBox)request.getAttribute("UserPaperResult");
		String v_answers = dbox2.get("d_answers");
        StringTokenizer st = new StringTokenizer(v_answers, ",");
		String checked = "";
    
	for (int i=0; i < blist.size(); i++) {
        ArrayList list = (ArrayList)blist.get(i);
        DataBox dbox = (DataBox)list.get(0);  

%>
        <tr>
          <td class="board_title_bg0"><%="["+String.valueOf(i+1)+"]     " + dbox.getString("d_sultext")%>
		  <%if (dbox.getString("d_sultype").equals(SulmunTargetBean.MULTI_QUESTION)){%>(<%=dbox.getString("d_selmax")%>개까지 선택 가능)<%}%>
		  </td>
        </tr>
<%      if (dbox.getString("d_sultype").equals(SulmunTargetBean.OBJECT_QUESTION)) {   // 단일선택 
            String iscorrect = st.nextToken();
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
				if(dbox.getInt("d_selnum") == Integer.parseInt(iscorrect)){ 
					checked = "CHECKED"; 
				} else {
					checked = ""; 				
				}
%>
		<tr> 
          <td class="board_text4">
            &nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>" <%=checked%>><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%              }
            }
        } else if (dbox.getString("d_sultype").equals(SulmunTargetBean.MULTI_QUESTION)) {  // 복수선택
            String iscorrect = st.nextToken();
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) {
               StringTokenizer st2 = new StringTokenizer(iscorrect, ":");
			    while(st2.hasMoreElements()) {

                String temp = st2.nextToken();
				if(dbox.getInt("d_selnum") == Integer.parseInt(temp)){ 
					checked = "CHECKED"; 
					break;
				}else{
					checked = ""; 				
				}
				}
 %>		
		<tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="checkbox" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>" <%=checked%>><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%              } 
            }
        } else if (dbox.getString("d_sultype").equals(SulmunTargetBean.SUBJECT_QUESTION)) {  // 서술형  
            String iscorrect = st.nextToken();
%>
        <tr> 
          <td class="board_text3"> 
            &nbsp;&nbsp;<textarea name="<%=dbox.getInt("d_sulnum")%>" cols="90" rows="5" class="input" maxlength="200"><%=iscorrect%></textarea>
          </td>
        </tr>
<%
		} else if (dbox.getString("d_sultype").equals(SulmunTargetBean.COMPLEX_QUESTION)) {  // 복합형
            String iscorrect = st.nextToken();
			StringTokenizer st2 = new StringTokenizer(iscorrect, ":");
            String tempNum = st2.nextToken();
            String tempText = st2.nextToken();
			for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) {

			      if(dbox.getInt("d_selnum") == Integer.parseInt(tempNum)){ 
				    	checked = "CHECKED"; 
				   }else{
					checked = ""; 				
				   }

%>
        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>" <%=checked%>><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%          }
				if (j == (list.size()-1)) {    %>
        <tr> 
          <td class="board_text4">기타의견</td>
        </tr>
        <tr> 
          <td class="board_text3"> 
            <textarea name="<%=dbox.getInt("d_sulnum")%>|C" cols="90" rows="5" class="input" maxlength="200"><%=tempText%></textarea>
          </td>
        </tr>
<%				
				}
           } 
        } else if (dbox.getString("d_sultype").equals(SulmunTargetBean.FSCALE_QUESTION)) {  // 5점척도
            String iscorrect = st.nextToken();
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
				if(dbox.getInt("d_selnum") == Integer.parseInt(iscorrect)){ 
					checked = "CHECKED"; 
				} else {
					checked = ""; 				
				}
%>
        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>" <%=checked%>><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%          
                }
           } 
        } else if (dbox.getString("d_sultype").equals(SulmunTargetBean.SSCALE_QUESTION)) {  // 7점척도
            String iscorrect = st.nextToken();
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
				if(dbox.getInt("d_selnum") == Integer.parseInt(iscorrect)){ 
					checked = "CHECKED"; 
				} else {
					checked = ""; 				
				}
%>
        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>" <%=checked%>><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
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
     <td align="right"><a href="javascript:window.close()"><img src="/images/admin/research/close1_butt.gif" alt="닫기" width="37" height="18" border="0"></a></td>
    </tr>
</table>
<!----------------- 제출 버튼 끝 ----------------->
<br>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
