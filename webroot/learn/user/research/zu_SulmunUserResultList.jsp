<%
/**
 * file name : zu_SulmunResultList.jsp
 * date      : 2003/09/12
 * programmer:
 * function  : 설문결과등록
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.research.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_grcode    = box.getStringDefault("p_grcode",SulmunBean.DEFAULT_GRCODE);
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    int     v_sulpapernum = box.getInt("p_sulpapernum");
    String  v_userid    = box.getSession("userid");
    String  v_subj_real = box.getString("p_subj_real");
    String  v_f_gubun   = box.getString("p_f_gubun");
    
    ArrayList    list = (ArrayList)request.getAttribute("PaperQuestionExampleList");
    PaperData    paperdata = (PaperData)request.getAttribute("PaperData");
    
    String  v_subjnm = paperdata.getSubjnm();

    if (v_subj.equals("COMMON")) {
		    v_subjnm = "공통설문";	    
    } else if (v_subj.equals("WHOLE")) {
		    v_subjnm = "전체설문";	    
    }

    String s_gadmin = box.getSession("gadmin");
%>
<html>
<head>
<title>eIBIs</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_research.css" type="text/css">
<script language="JavaScript">
<!--
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
      if (c_name != b_name) {
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
      for(var j=0; j<textarr.length; j++) {
        temp = temp + textarr[j];
      }
    }
  }
  if (b_type=="text" || temp !="") {
    replycnt++;
  }
  answercnt++;
  if (answercnt==1) {
    result = temp;    
  } else {
    result = result + ","+ temp;
  }

//alert(result);

  if (replycnt < answercnt) {
    alert("응답하지 않은 설문이 있습니다.모든 설문에 응답해주시길 바랍니다.");
    return;
  }
  document.form2.p_answers.value = result;
  document.form2.p_process.value = "UserResultInsert";
  document.form2.submit();
}
-->
</script>
</head>
<body>
<!----------------- 타이틀 시작 ----------------->
<table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
  <tr> 
    <td background="/images/user/research/b_title_dot.gif"><img src="/images/user/research/question_title2.gif" alt="설문조사" width="240" height="37" border="0"></td>
  </tr>
  <tr><td height="7"></td></tr>
</table>
<!----------------- 타이틀 끝 ---------------->
<br>
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
  <tr> 
    <td width="13"><img src="/images/user/research/icon2.gif" width="13" height="11" border="0"></td>
    <td><b>과정명 : <%=v_subjnm%>  / 연도 : <%=v_year%>  / 차수 : <%=v_subjseq%></b></td>
  </tr>
   <tr> 
    <td width="13">&nbsp;</td>
    <td><b>설문지명 : <%=paperdata.getSulpapername(0)%> / 설문지번호 : <%=v_sulpapernum%> / 문제수: <%=paperdata.getTotcnt(0)%></b></td>
  </tr>
  <tr> 
    <td height="5"></td>
  </tr>
</table>
<!----------------- 설문조사 시작 ---------------->
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
  <tr> 
    <td class="board_color_line"></td>
  </tr>
</table>
<table cellspacing="0" cellpadding="0" class="open_board_table_out2" align="center">
<form name="form2" method="post" action="/servlet/controller.research.SulmunServlet">
  <input type="hidden" name="p_process"     value="">
  <input type="hidden" name="p_grcode"      value="<%=v_grcode%>">
  <input type="hidden" name="p_subj"        value="<%=v_subj%>">
  <input type="hidden" name="p_year"        value="<%=v_year%>">
  <input type="hidden" name="p_subjseq"     value="<%=v_subjseq%>">
  <input type="hidden" name="p_sulpapernum" value="<%=v_sulpapernum%>">
  <input type="hidden" name="p_subj_real"   value="<%=v_subj_real%>">
  <input type="hidden" name="p_f_gubun"     value="<%=v_f_gubun%>">
  <input type="hidden" name="p_userid"      value="<%=v_userid%>">
  <input type="hidden" name="p_answers"     value="">
</form>
  <tr> 
    <td> 
      <table cellspacing="1" cellpadding="3" class="table2">
      <form name="form3" method="post" action="/servlet/controller.research.SulmunServlet">
<%  QuestionExampleData data    = null;
    ExampleData         subdata = null;
    for (int i=0; i < list.size(); i++) {
        data = (QuestionExampleData)list.get(i);%>
        <tr> 
          <td class="board_title_bg2"><%="["+String.valueOf(i+1)+"]["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
        </tr>
<%      if (data.getSultype().equals(SulmunBean.OBJECT_QUESTION)) {   // 객관식 
            for (int j=1; j <= data.size(); j++) {
                subdata  = (ExampleData)data.get(j); 
                if (subdata != null) { %>
        <tr> 
          <td class="board_text4"> 
            <input type="radio" name="<%=data.getSulnum()%>" value="<%=subdata.getSelnum()%>"><%=subdata.getSelnum()%>. <%=subdata.getSeltext()%></td>
        </tr>
<%              }
            }
        } else if (data.getSultype().equals(SulmunBean.MULTI_QUESTION)) {  // 다답식
            for (int j=1; j <= data.size(); j++) {
                subdata  = (ExampleData)data.get(j); 
                if (subdata != null) { %>
        <tr> 
          <td class="board_text4"> 
            <input type="checkbox" name="<%=data.getSulnum()%>" value="<%=subdata.getSelnum()%>"><%=subdata.getSelnum()%>. <%=subdata.getSeltext()%></td>
        </tr>
<%              } 
            }
        } else if (data.getSultype().equals(SulmunBean.SUBJECT_QUESTION)) {  // 주관식  %>
        <tr> 
          <td class="board_text3"> 
            <textarea name="<%=data.getSulnum()%>" cols="90" rows="15" class="input"></textarea>
          </td>
        </tr>
<%      }
    } %>
      </form>
      </table>
    </td>
  </tr>
</table>
<!----------------- 설문조사 끝 ---------------->
<br>
<!----------------- 제출 버튼 시작 ---------------->
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
  <tr>
    <td align="right">
<%  if (list == null || list.size() < 1) { 
        if (v_subj.equals("WHOLE")) { %> 
      <a href="javascript:window.close()"><img src="/images/user/study/presentation_butt1.gif" alt="제출" width="54" height="20" border="0"></a>
<%      } else { %>  
      <a href="javascript:javascript:top.etop.goExNext()"><img src="/images/user/study/presentation_butt1.gif" alt="제출" width="54" height="20" border="0"></a>
<%      }    
    } else { 
        if (s_gadmin.equals("ZZ")) { %>      
      <a href="javascript:whenSubmit()"><img src="/images/user/study/presentation_butt1.gif" alt="제출" width="54" height="20" border="0"></a>
<%      } else { 
            if (v_subj.equals("WHOLE")) { %> 
<!--      <a href="javascript:window.close()"><img src="/images/user/study/presentation_butt1.gif" width="54" height="20" border="0"></a> -->
      <a href="javascript:whenSubmit()"><img src="/images/user/study/presentation_butt1.gif" alt="제출" width="54" height="20" border="0"></a>
<%          } else { %>
      <a href="javascript:javascript:top.etop.goExNext()"><img src="/images/user/study/presentation_butt1.gif" alt="제출" width="54" height="20" border="0"></a>
<%          } 
        }
    }  %>  
    </td>
  </tr>
</table>
<!----------------- 제출 버튼 끝 ----------------->
<br>
</body>
</html>
