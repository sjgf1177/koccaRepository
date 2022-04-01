<%
//**********************************************************
//  1. 제      목: 설문
//  2. 프로그램명: za_SulmunAllPaperPreview.java
//  3. 개      요: 업체설문 - 미리보기
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 6. 30
//  7. 수      정: 
//**********************************************************
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
    
    String v_userid = box.getSession("userid");
    
    
    String  v_grcode    = box.getStringDefault("p_grcode",SulmunCpBean.DEFAULT_GRCODE);
    String  v_subj      = box.getString("p_subj");
    String  v_gyear      = box.getString("p_gyear");
    String  v_sulnums   = box.getString("p_sulnums");
    int     v_sulpapernum = box.getInt("p_sulpapernum");
    String  v_sulpapernm= box.getString("p_sulpapernm");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_cpseq     = box.getString("p_cpseq");
    int  v_replaycnt = box.getInt("p_replaycnt");
    String v_cpstatus = box.getString("p_cpstatus");
    
    String  v_cpsulnums   = box.getString("p_cpsulnums");
    
    String v_reloadlist = box.getString("p_reloadlist");   
	String v_end = box.getString("p_end");
    
    String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }
    
%>
<html>
<head>
<title>설문지 미리보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_study_style1.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
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
  
  //alert(result);
  
  document.form2.p_answers.value = result;
  document.form2.p_process.value = "SulmunCpResultInsert";
  document.form2.p_reloadlist.value = 'true';
  document.form2.submit();
}


function onload() {
  window.self.close();
}


<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage();
<% } %>
-->
</script>
<link href="/css/user_style_study.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" <%=v_onload%>>
<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="660" height="100%">
    <tr>
        <td>
            <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
                <tr>
                    <td align="center" valign="top" class="body_color">
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
<!--<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
  <tr> 
    <td width="13"><img src="/images/user/research/icon2.gif" width="13" height="11" border="0"></td>
    <td><b>설문지명 : <%=v_sulpapernm%> / 설문지번호 : <%=v_sulpapernum%></b></td>
  </tr>
  <tr> 
    <td height="5"></td>
  </tr>
</table>-->

              <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
                <tr> 
                  <td height="5" colspan="2"></td>
                </tr>
                <tr> 
                  <td width="69"><img src="/images/user/study/poll_leftimg.gif" width="69" height="51"></td>
                  <td><table width="540" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="72"><img src="/images/user/study/poll_pollname.gif"></td>
                        <td width="408" class="tblfont_sindotit"> <b>   <%=v_sulpapernm%>   </b></td>
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
<!----------------- 설문조사 시작 ---------------->
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
  <tr> 
    <td class="board_color_line"></td>
  </tr>
</table>
<table cellspacing="0" cellpadding="0" class="open_board_table_out2" align="center">
<form name="form2" method="post" action="/servlet/controller.cp.CPSulServlet">
  <input type="hidden" name="p_process"     value="">
  <input type="hidden" name="p_grcode"      value="<%=v_grcode%>">
  <input type="hidden" name="p_subj"        value="<%=v_subj%>">
  <input type="hidden" name="p_gyear"        value="<%=v_gyear%>">
  <input type="hidden" name="p_sulpapernum" value="<%=v_sulpapernum%>">
  <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
  <input type="hidden" name="p_userid"     value="<%=v_userid%>">
  <input type="hidden" name="p_sulnums"    value="<%=v_sulnums%>">
  <input type="hidden" name="p_cpseq"      value="<%=v_cpseq%>">
  <input type="hidden" name="p_cpsulnums"  value="<%=v_cpsulnums%>">
  <input type="hidden" name="p_replaycnt" value="<%=v_replaycnt%>">
  
  <input type="hidden" name="p_answers"      value="">
  <input type="hidden" name="p_reloadlist" value="">
  
</form>
  <tr> 
    <td> 
      <table cellspacing="1" cellpadding="3" class="table2">
      <form name="form3" method="post" action="/servlet/controller.research.SulmunCpPaperServlet">
<%
        SulmunCpuserPaperBean scpbean = new SulmunCpuserPaperBean();
        String v_answers = scpbean.getAnswer(box);
        //out.println("v_answers==>>"+v_answers);

        ArrayList    blist = (ArrayList)request.getAttribute("PaperQuestionExampleList");
        int k = 0;
        StringTokenizer st = null;
        
        if(!v_answers.equals("")){
          st = new StringTokenizer(v_answers, SulmunCpBean.SPLIT_COMMA);
        }
        
        String checked = "";
    
	for(int i=0; i < blist.size(); i++) {
        ArrayList list = (ArrayList)blist.get(i);  
        DataBox dbox = (DataBox)list.get(0);
        
        if( dbox.getString("d_apptarget").equals("1") || (dbox.getString("d_apptarget").equals("2")&& v_replaycnt==1) ){
        k++;
%>        
          <tr> 
            <td class="board_title_bg0"><%="["+String.valueOf(k)+"]["+dbox.getString("d_distcodenm")+"] " + dbox.getString("d_sultext")%></td>
          </tr>
<%        if (dbox.getString("d_sultype").equals(SulmunCpBean.OBJECT_QUESTION)) {   // 단일선택 
              int iscorrect = 0;
              if(!v_answers.equals("")){
                 iscorrect = Integer.parseInt(st.nextToken());
              }
              
              for (int j=0; j < list.size(); j++) {
                  dbox  = (DataBox)list.get(j); 
                  if (dbox != null) { 
                    if(dbox.getInt("d_selnum") == iscorrect){
				    	checked = "CHECKED"; 
				    } else {
				    	checked = ""; 				
				    }
%>
          <tr> 
            <td class="board_text4"> 
              <input type="radio" name="<%=dbox.getInt("d_sulnum")%>" 
                value="<%=dbox.getInt("d_selnum")%>"<%=checked%>><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%>
            </td>
          </tr>
<%                }
              }
          }
        }else{
%>
       <div id='aesexam' style="display:none">
         <input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="0" checked>
       </div>
<%      
        }
	}
%>
      </form>
      </table>
    </td>
  </tr>
</table>

<!----------------- 설문조사 끝 ---------------->
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
</tr>
</table> </td> </tr> </table> 
<%@ include file = "/learn/library/getJspName.jsp" %>     
</body>
</html>