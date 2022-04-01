<%
/**
 * file name : za_DamunSubjMailPreview.jsp
 * date      : 2003/09/12
 * programmer:
 * function  : 다면평가 메일 미리보기
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_grcode    = box.getStringDefault("p_grcode",DamunSubjBean.DEFAULT_GRCODE);
    String  v_subj      = box.getString("p_subj");
    String  v_gyear      = box.getString("p_gyear");
	String  v_subjseq = box.getString("p_subjseq");
    int     v_damunpapernum = box.getInt("p_damunpapernum");
    String  v_damunpapernm= box.getString("p_damunpapernm");
    String  v_sulstart= box.getString("p_sulstart");
    String  v_sulend= box.getString("p_sulend");
    String  v_damunnums= box.getString("p_damunnums");
    String  v_title= box.getString("p_title");
    String  v_content= box.getString("p_content");
    int  v_mailnum = box.getInt("p_mailnum");

	String  v_subjuserid = box.getString("p_subjuserid");
	String  v_mailgubun = box.getString("p_mailgubun");

    String v_schecks = "";
	String v_userids = "";
	String v_names = "";
	Vector  v_checks = box.getVector("p_checks");
    StringTokenizer st = null;

    String  v_fromEmail = box.getSession("email");
    String  v_fromName = box.getSession("name");
    String  v_comptel = box.getSession("comptel");

    String v_reloadlist = box.getString("p_reloadlist");
%>
<html>
<head>
<title>다면평가지 메일 미리보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/mail_style.css">
<script language="JavaScript">
<!--
function sendMail(){
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = 'DamunMailSend';
    document.form2.p_reloadlist.value = 'true';
    document.form2.p_action.value = "go";
    document.form2.action = "/servlet/controller.multiestimate.DamunSubjMailServlet";
    document.form2.submit();
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
        if (b_type=="text" || temp !="" || b_type=="radio") {
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
		  if (eval(c_name.indexOf("|"))>0){
			temp = ":" + temp+ c_value+':';
          }else{
			temp = c_value;
          }
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
  if (b_type=="text" || temp !="" ) {
    replycnt++;
  }
  answercnt++;
  if (answercnt==1) {
    result = temp;    
  } else if (eval(b_name.indexOf("|"))>0 ) {
    result = result + temp   ;  
  } else {
    result = result + ","+ temp;
  }

//alert(result);

  if (replycnt < answercnt) {
    alert("응답하지 않은 설문이 있습니다.모든 설문에 응답해주시길 바랍니다.");
    return;
  }
  document.form2.p_answers.value = result;
  document.form2.p_process.value = "DamunUserResultInsert";
  document.form2.submit();
}
<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage();
<% } %>
//-->
</script>
</head>

<body topmargin=0 leftmargin=0>
<form name="form2" method="post" action="/servlet/controller.multiestimate.DamunSubjResultServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"    value="<%=v_subj%>">
        <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
        <input type="hidden" name="p_gyear"  value="<%=v_gyear%>">
        <input type="hidden" name="p_subjseq"  value="<%=v_subjseq%>">
        <input type="hidden" name="p_damunpapernum"  value="<%=v_damunpapernum%>">
        <input type="hidden" name="p_damunpapernm"  value="<%=v_damunpapernm%>">
        <input type="hidden" name="p_sulstart"  value="<%=v_sulstart%>">
        <input type="hidden" name="p_sulend"  value="<%=v_sulend%>">
        <input type="hidden" name="p_damunnums"  value="<%=v_damunnums%>">
        <input type="hidden" name="p_title"  value="<%=v_title%>">
        <input type="hidden" name="p_content"  value='<%=v_content%>'>
        <input type="hidden" name="p_subjuserid"  value="<%=v_subjuserid%>">
        <input type="hidden" name="p_mailnum"  value="<%=v_mailnum%>">
        <input type="hidden" name="p_mailgubun"     value="<%=v_mailgubun%>">
    <input type="hidden" name="p_answers"      value="">
        <input type="hidden" name="p_reloadlist"  value="">
<%
    for(int i=0;i<v_checks.size();i++) {              
      v_schecks = (String)v_checks.elementAt(i);
      st = new StringTokenizer(v_schecks,"|");
        v_userids = (String)st.nextToken();
        v_names = (String)st.nextToken();
%>
  <input type='hidden' name = 'p_userids' value='<%=v_userids%>'>
  <input type='hidden' name = 'p_names' value='<%=v_names%>'>
<%
   }
%>
</form>
<table width="640" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><img src="/images/admin/research/Mform_top.gif" ></td>
  </tr>
  <tr> 
    <td class=sulmun_intro>안녕하세요<br>
      HKMC e-learning systems 운영진입니다.</td>
  </tr>
  <tr> 
    <td class=sulmun_bg height=8>&nbsp;</td>
  </tr>
  <tr> 
    <td align="center" class=sulmun_bg><table width="600" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td class=sulmun_con><%=v_content%></td>
        </tr>
        <tr>
          <td height=8></td>
        </tr>
      </table>
      <table width="600" align="center" cellpadding="2" cellspacing="1" >
            <form name="form3" method="post" action="/servlet/controller.research.SulmunTargetPaperServlet">
<% 
        ArrayList    blist = (ArrayList)request.getAttribute("PaperQuestionExampleList");
    
	for (int i=0; i < blist.size(); i++) {
        ArrayList list = (ArrayList)blist.get(i);  
        DataBox dbox = (DataBox)list.get(0);  
%>
        <tr> 
          <td width="646" class="sulmun_num"><%="["+String.valueOf(i+1)+"]"+ dbox.getString("d_damuntext")%>
              <%if (dbox.getString("d_damuntype").equals(DamunSubjBean.MULTI_QUESTION)){%>
              (<%=dbox.getString("d_selmax")%>개까지 선택 가능)
              <%}%>		  
		  </td>
        </tr>
<%      if (dbox.getString("d_damuntype").equals(DamunSubjBean.OBJECT_QUESTION)) {   // 단일선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            <input type="radio" name="<%=dbox.getInt("d_damunnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%              }
            }
        } else if (dbox.getString("d_damuntype").equals(DamunSubjBean.MULTI_QUESTION)) {  // 복수선택
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            <input type="checkbox" name="<%=dbox.getInt("d_damunnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%              } 
            }
        } else if (dbox.getString("d_damuntype").equals(DamunSubjBean.SUBJECT_QUESTION)) {  // 서술형  %>
        <tr> 
          <td class="board_text3"> 
            <textarea name="<%=dbox.getInt("d_damunnum")%>" cols="83" rows="5" class="input"></textarea>
          </td>
        </tr>
<%
		} else if (dbox.getString("d_damuntype").equals(DamunSubjBean.COMPLEX_QUESTION)) {  // 복합형
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            <input type="radio" name="<%=dbox.getInt("d_damunnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%          }
           } 
%>
        <tr> 
          <td class="sulmun_num"><strong>기타의견</strong></td>
        </tr>
        <tr> 
          <td class="board_text3"> 
            <textarea name="<%=dbox.getInt("d_damunnum")%>|C" cols="83" rows="5" class="input"></textarea>
          </td>
        </tr>
<%				
				

        } else if (dbox.getString("d_damuntype").equals(DamunSubjBean.FSCALE_QUESTION)) {  // 5점척도
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            <input type="radio" name="<%=dbox.getInt("d_damunnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%          
                }
           } 
        } else if (dbox.getString("d_damuntype").equals(DamunSubjBean.SSCALE_QUESTION)) {  // 7점척도
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            <input type="radio" name="<%=dbox.getInt("d_damunnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%          
                }
           } 
        } else if (dbox.getString("d_damuntype").equals(DamunSubjBean.GSCALE_QUESTION)) {  // GAP비교
            for (int j=0; j < 5; j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
                if (j == 0) {
%>
        <tr> 
          <td class="board_text3"><%=dbox.getString("d_scalename")%></td>
        </tr>
<%
                }	
%>
		<tr> 
          <td class="board_text4"> 
            <input type="radio" name="<%=dbox.getInt("d_damunnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%          
                }
           } 
            for (int j=5; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) {
                if (j == 5) {
%>
        <tr> 
          <td class="board_text3"><%=dbox.getString("d_scalename")%></td>
        </tr>
<%
                }	
%>
        <tr> 
          <td class="board_text4"> 
            <input type="radio" name="<%=dbox.getInt("d_damunnum")%>|S" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")-5%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%          
                }
           } 
        } 
	}
%>
<!----------------- 제출 버튼 시작 ---------------->
          <tr> 
            <td align="right" style=padding-right:12px><img src="/images/admin/button/btn_presentation.gif" alt="제출" border="0"></td>
          </tr>
<!----------------- 제출 버튼 끝 ----------------->
            </form>
		   </table>		  

		  </td>
        </tr>
  <tr> 
    <td class=sulmun_bg>&nbsp;</td>
  </tr>
    <tr> 
    <td align="center" class=sulmun_bg><span class="webmaster2">문의사항이 있으시면</span><span class="webmaster1"><%=v_fromEmail%>, 
            Tel:<%=v_comptel%></span><span class="webmaster2"> 으로 문의하시기 바랍니다.</span></td>
  </tr>
  <tr> 
    <td><img src="/images/admin/research/Mform_bottom.gif" ></td>
  </tr>
  <tr>
    <td><table cellspacing="0" cellpadding="0"  align="center">
        <tr> 
          <td><a href="javascript:history.go(-1)"><img src="/images/admin/button/btn_before.gif" alt="이전"border="0"></a></td>
		  <td width=8></td>
          <td><a href="javascript:whenSubmit()"><img src="/images/admin/button/btn_send.gif" alt="발송" border="0"></a></td>
		  <td width=8></td>
          <td><a href="javascript:window.close()"><img src="/images/admin/button/btn_close02.gif" border="0"></a></td>
        </tr>
        <tr>
           <td></td>
           <td>&nbsp;</td> 
		   <td>&nbsp;</td> 
		   <td>&nbsp;</td> 
          <td>&nbsp;</td> 
        </tr>
      </table><%@ include file = "/learn/library/getJspName.jsp" %></td>
  </tr>
</table>
</body>
</html>