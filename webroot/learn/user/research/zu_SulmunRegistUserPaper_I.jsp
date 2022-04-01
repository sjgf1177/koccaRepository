<%
//**********************************************************
//  1. 제      목: 콘텐츠 설문
//  2. 프로그램명: SulmunContentsUserServlet.jsp
//  3. 개      요: 콘텐츠 설문 하기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 7. 21
//  7. 수      정:
//***********************************************************
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

	String v_reloadlist = box.getString("p_reloadlist");   
%>
<html>
<head>
<title>설문 응시</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
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
			if(temp.length>0){
			temp = ":" + temp + ":";
			}
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

  if (replycnt < answercnt) {
    alert("응답하지 않은 설문이 있습니다.모든 설문에 응답해주시길 바랍니다.");
    return;
  }
  document.form2.p_answers.value = result;
  document.form2.p_process.value = "SulmunUserResultInsert";
  document.form2.p_reloadlist.value = 'true';
  document.form2.submit();
}

<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go', 4);
<% } %>
-->
</SCRIPT>

<link href="/css/user_style_study.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" <%=v_onload%>>
<%
    DataBox    dbox0 = (DataBox)request.getAttribute("SulmunPaperData");

    String v_grcode		= dbox0.getString("d_grcode"); 
    String v_subj     	= dbox0.getString("d_subj"); 
    String v_sulpapernum = dbox0.getString("d_sulpapernum"); 
    String v_sulpapernm = dbox0.getString("d_sulpapernm"); 
    String v_sulnums 	= dbox0.getString("d_sulnums"); 

	String s_subj     	= box.getString("s_subj"); 
	String v_subjseq    = box.getString("p_subjseq"); 
    String v_gyear     	= box.getString("p_gyear"); 
	String v_userid 	= box.getSession("userid");
%>

<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="660" height="100%">
    <tr>
        <td>
            <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
                <tr>
                    <td align="center" valign="top" class="body_color">

 	 		        <br>
<form name="form2" method="post" action="/servlet/controller.research.SulmunRegistUserServlet">
    <input type="hidden" name="p_grcode"    value="<%=v_grcode%>">
    <input type="hidden" name="s_subj"    value="<%=s_subj%>">
    <input type="hidden" name="p_subj"    value="<%=v_subj%>">
    <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
    <input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
    <input type="hidden" name="p_sulpapernum"      value="<%=v_sulpapernum%>">
    <input type="hidden" name="p_userid"      value="<%=v_userid%>">
    <input type="hidden" name="p_sulnums"      value="<%=v_sulnums%>">
    <input type="hidden" name="p_answers"      value="">
    <input type="hidden" name="p_process"      value="">
	<input type="hidden" name="p_reloadlist" value="">
</form>
 	 		     
<form name="form3" method="post" action="/servlet/controller.research.SulmunTargetMailResultServlet">
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
		<td align="center">콘텐츠 평가설문은 수강을 마친 여러분들이 해당과정의 품질에 대한 평가를 하여 종합하기 위함입니다.<br>
보다 나은 콘텐츠를 제공하기 위하여 본 설문결과에 귀기울이겠습니다.</td>
	</tr>
  <tr> 
    <td height="5"></td>
  </tr>
</table>
<br>
              <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
                <tr> 
                  <td height="5" colspan="2"></td>
                </tr>
                <tr> 
                  <td width="69"><img src="/images/user/study/poll_leftimg.gif" width="69" height="51"></td>
                  <td><table width="480" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="72"><img src="/images/user/study/poll_pollname.gif"></td>
                        <td width="408" class="tblfont_sindotit"> <b>  <%=v_sulpapernm%> </b></td>
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
            
<!----------------- 학습평가 시작 ---------------->              
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
  <tr> 
    <td class="board_color_line"></td>
  </tr>
</table>
<table cellspacing="0" cellpadding="0" class="open_board_table_out2" align="center">
  <tr> 
    <td> 
      <table border=0 cellspacing="1" cellpadding="0" class="table2">
<% 
    ArrayList  blist = (ArrayList)request.getAttribute("PaperQuestionExampleList");
    System.out.println("blist.size() = " + blist.size());
	for (int i=0; i < blist.size(); i++) 
	{
        ArrayList list = (ArrayList)blist.get(i);
        DataBox dbox = (DataBox)list.get(0);  

%>
        <tr> 
          <td height="20" class="board_title_bg0">
          <%="["+String.valueOf(i+1)+"]     " + dbox.getString("d_sultext")%>
          <td align="right">
          </td>
        </tr>

<%      if (dbox.getString("d_sultype").equals(SulmunCommonBean.OBJECT_QUESTION)) {   // 단일선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
%>
                
        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="radio" class="inputradio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%>
          </td>
        </tr>
<%              }
            }
        }
             
        if (dbox.getString("d_sultype").equals(SulmunCommonBean.MULTI_QUESTION)) {  // 복수선택
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="checkbox" class="inputradio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>">
            <%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%>
          </td>
        </tr>
<%              } 
            }
        }

		if (dbox.getString("d_sultype").equals(SulmunCommonBean.COMPLEX_QUESTION)) {  // 복합형
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
%>

        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="radio" class="inputradio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>">
            <%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%>
          </td>
        </tr>
<%              }
				if (j == (list.size()-1)) {    
%>
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
        } 

        if (dbox.getString("d_sultype").equals(SulmunCommonBean.FSCALE_QUESTION)) {  // 5점척도
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
%>
        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="radio" class="inputradio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%          
                }
            } 
        } 
               
        if (dbox.getString("d_sultype").equals(SulmunCommonBean.SSCALE_QUESTION)) {  // 7점척도
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="radio" class="inputradio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%          
                }
            } 
        }
    } // for end     
%>
      </table>
    </td>
  </tr>
</table>

<!----------------- 학습평가 끝 ---------------->
<br>
<!----------------- 제출 버튼 시작 ---------------->
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
    <tr>                
        <td align="right"><a href="javascript:whenSubmit()"><img src="/images/user/button/btn_m_p.gif"  alt="제출" border="0"></a></td>
        <td align="right" width="60"><a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif" alt="닫기" border="0"></a></td>
    </tr>
</table>
<!----------------- 제출 버튼 끝 ----------------->
</form>
<br></td>
</tr> </table> </td> </tr> </table> 
</body>
</html>
