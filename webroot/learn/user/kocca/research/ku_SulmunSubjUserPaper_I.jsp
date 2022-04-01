<%
/**
 * file name : zu_SulmunSubjUserPaper_I.jsp
 * date      : 2005/08/12
 * programmer:
 * function  : 과정  설문 응시
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
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

    String v_userid = box.getSession("userid");
	String v_reloadlist = box.getString("p_reloadlist");   
	String v_end = box.getString("p_end");
	
	String p_sulpapernm = box.getString("p_sulpapernm");
	String p_subjnm = box.getString("p_subjnm");
			
    String p_grcode  = box.getString("p_grcode");
    String s_grcode  = box.getString("s_grcode");    
    String p_subj    = box.getString("s_subj");
    String p_subjseq = box.getString("p_subjseq");
    String p_gyear   = box.getString("p_gyear");
    String p_sulpapernum = box.getString("p_sulpapernum");
    String p_sulnums     = box.getString("p_sulnums");
    String p_isstudy     = box.getString("p_isstudy"); // 학습창임..
   
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

    /*DataBox    dbox0 = (DataBox)request.getAttribute("SulmunPaperData");
    String v_grcode     = dbox0.getString("d_grcode"); 
	String v_subj     = dbox0.getString("d_subj"); 
	String v_subjseq     = dbox0.getString("d_subjseq"); 
    String v_gyear     = dbox0.getString("d_year"); 
    String v_sulpapernum = dbox0.getString("d_sulpapernum"); 
    String v_sulpapernm = dbox0.getString("d_sulpapernm"); 
    String v_sulnums = dbox0.getString("d_sulnums"); 
    String v_subjnm = dbox0.getString("d_subjnm"); 
    */ //2005.08.22 by정은년 jsp에서 다 값 받아옴.
 
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

// 제출
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
         if(document.form3.elements[i].value == ""){
            alert("응답하지 않은 설문이 있습니다.");
            return;
	}else{
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
	} // value null check
     }
  }

  if (b_type=="text" || temp !="") {
    replycnt++;
  }
//  if (b_type=="textarea" || temp !="") {
//    replycnt++;
//  }
  answercnt++;
  if (answercnt==1) {
    result = temp;    
  } else if (eval(b_name.indexOf("|"))>0) {
    result = result + temp  ;  
  }else {
    result = result + ","+ temp;
  }

//alert(replycnt );
//alert(answercnt);

  if (replycnt < answercnt) {
    alert("응답하지 않은 설문이 있습니다.모든 설문에 응답해주시길 바랍니다.");
    return;
  }
  //alert(result);
  document.form2.p_answers.value = result;
  document.form2.p_process.value = "SulmunUserResultInsert";
  document.form2.p_reloadlist.value = 'true';
  document.form2.submit();
}


<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go',1);
<% } %>
//-->
</SCRIPT>
</head>
<body  leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0"  <%=v_onload%>>

<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="650" height="100%">
    <tr>
        <td>
            <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
                <tr>
                    <td align="center" valign="top" class="body_color">
<form name="form2" method="post" action="/servlet/controller.research.KSulmunSubjUserServlet">
    <input type="hidden" name="p_grcode"     value="<%=s_grcode%>">
    <input type="hidden" name="p_subj"       value="<%=p_subj%>">
    <input type="hidden" name="p_subjseq"    value="<%=p_subjseq%>">
    <input type="hidden" name="p_gyear"      value="<%=p_gyear%>">
    <input type="hidden" name="p_sulpapernum"      value="<%=p_sulpapernum%>">
    <input type="hidden" name="p_userid"       value="<%=v_userid%>">
    <input type="hidden" name="p_sulnums"      value="<%=p_sulnums%>">
    <input type="hidden" name="p_answers"      value="">
    <input type="hidden" name="p_process"      value="">
	<input type="hidden" name="p_reloadlist" value="">

    <!--분류코드-->
    <input type="hidden" name="p_distcodescore1"      value="">	
    <input type="hidden" name="p_distcodescore2"      value="">	
    <input type="hidden" name="p_distcodescore3"      value="">	
</form>
<form name="form3" method="post" action="/servlet/controller.research.SulmunSubjUserServlet">
              <br>
              <!----------------- 타이틀 시작 ----------------->
              <table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
    						<tr> 
      							<td><img src="/images/user/study/question_title2.gif"></td>
      							<td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
      							<td><img src="/images/user/study/gongi_tail.gif"></td>
    						</tr>
  						</table>
            		<!----------------- 타이틀 끝 ---------------->
<br>
              <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center" bgcolor="#ffffff">
                <tr> 
                  <td height="5" colspan="2"></td>
                </tr>
                <tr> 
                  <td width="69"><img src="/images/user/study/poll_leftimg.gif" width="69" height="51"></td>
                  <td><table width="540" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="72"><img src="/images/user/study/poll_pollname.gif"></td>
                        <td width="408" class="tblfont_sindotit"> <b>  [<%=p_subjnm%>]  >> <%=p_sulpapernm%>  </b></td>
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
      <table cellspacing="1" cellpadding="3" class="table2">
<% 
if(!v_end.equals("0")) {

    ArrayList    blist = (ArrayList)request.getAttribute("PaperQuestionExampleList"); 
	for (int i=0; i < blist.size(); i++) {

System.out.println("blist.size()"+blist.size());	
	    if(blist.size()>0){
	    
            ArrayList list = (ArrayList)blist.get(i);
    
            DataBox dbox = (DataBox)list.get(0);  
        
%>
        <tr> 
          <td class="board_title_bg0"><%="["+String.valueOf(i+1)+"]     " + dbox.getString("d_sultext")%>
          
		  <%if (dbox.getString("d_sultype").equals(SulmunSubjBean.MULTI_QUESTION)){%>(<%=dbox.getString("d_selmax")%>개까지 선택 가능)<%}%>
		  </td>
        </tr>
<%      if (dbox.getString("d_sultype").equals(SulmunSubjBean.OBJECT_QUESTION)) {   // 단일선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%              }
            }
        } else if (dbox.getString("d_sultype").equals(SulmunSubjBean.MULTI_QUESTION)) {  // 복수선택
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            &nbsp;&nbsp;<input type="checkbox" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%              } 
            }
        } else if (dbox.getString("d_sultype").equals(SulmunSubjBean.SUBJECT_QUESTION)) {  // 서술형  %>
        <tr> 
          <td class="board_text3"> 
            &nbsp;&nbsp;<textarea name="<%=dbox.getInt("d_sulnum")%>" cols="70" rows="5" class="input" maxlength="200"></textarea>
          </td>
        </tr>
<%
		} else if (dbox.getString("d_sultype").equals(SulmunSubjBean.COMPLEX_QUESTION)) {  // 복합형
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
          <td class="board_text4">&nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"> 기타의견</td>
        </tr>
        <tr> 
          <td class="board_text3">
            <textarea name="<%=dbox.getInt("d_sulnum")%>|C" cols="70" rows="5" class="input" maxlength="200"></textarea>
          </td>
        </tr>
<%				
				}
           } 
        } else if (dbox.getString("d_sultype").equals(SulmunSubjBean.FSCALE_QUESTION)) {  // 5점척도
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
        } else if (dbox.getString("d_sultype").equals(SulmunSubjBean.SSCALE_QUESTION)) {  // 7점척도
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

        
        } // end if        
	} //end for
	
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
<table  cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
    <tr>                
        <td align="right"><a href="javascript:whenSubmit()"><img src="/images/user/study/presentation_butt1.gif" width="54" height="20" border="0"></a></td>
        <td align="right" width="60"><a href="javascript:self.close()"><img src="/images/user/study/close_butt.gif" width="54" height="20" border="0"></a></td>
             
    </tr>
</table>
<!----------------- 제출 버튼 끝 ----------------->
<br></td>
</tr> </table> </td> </tr> </table> 
</body>
</html>
