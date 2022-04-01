<%
/**
 * file name : za_SulmunTargetMailPreview.jsp
 * date      : 2003/09/12
 * programmer:
 * function  : 설문 메일 미리보기
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

    String  v_grcode    = box.getStringDefault("p_grcode",SulmunTargetBean.DEFAULT_GRCODE);
    String  v_subj      = box.getString("p_subj");
    String  v_gyear      = box.getString("p_gyear");
	String  v_subjseq = box.getString("p_subjseq");
    int     v_sulpapernum = box.getInt("p_sulpapernum");
    String  v_sulpapernm= box.getString("p_sulpapernm");
    String  v_sulstart= box.getString("p_sulstart");
    String  v_sulend= box.getString("p_sulend");
    String  v_title= box.getString("p_title");
    String  v_content= box.getString("p_content");
    int  v_mailnum = box.getInt("p_mailnum");

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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>설문지 메일 미리보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/mail_style.css">
<script language="JavaScript">
<!--
function sendMail(){
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = 'SulmunMailSend';
    document.form2.p_reloadlist.value = 'true';
    document.form2.p_action.value = "go";
    document.form2.action = "/servlet/controller.research.SulmunTargetMailServlet";
    document.form2.submit();
}
function sulmunRight(){
  document.form2.p_process.value = 'SulmunUserPaperListPage';
  document.form2.action = "/servlet/controller.research.SulmunTargetUserServlet";
  document.form2.submit();
}
<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage();
<% } %>
//-->
</script>
</head>

<body topmargin=0 leftmargin=0>
<form name="form2" method="post" action="/servlet/controller.research.SulmunTargetMailServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"    value="<%=v_subj%>">
        <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
        <input type="hidden" name="p_gyear"  value="<%=v_gyear%>">
        <input type="hidden" name="p_subjseq"  value="<%=v_subjseq%>">
        <input type="hidden" name="p_sulpapernum"  value="<%=v_sulpapernum%>">
        <input type="hidden" name="p_sulpapernm"  value="<%=v_sulpapernm%>">
        <input type="hidden" name="p_sulstart"  value="<%=v_sulstart%>">
        <input type="hidden" name="p_sulend"  value="<%=v_sulend%>">
        <input type="hidden" name="p_title"  value="<%=v_title%>">
        <input type="hidden" name="p_content"  value='<%=v_content%>'>
        <input type="hidden" name="p_mailnum"  value="<%=v_mailnum%>">
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
<table width="660" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><img src="/images/admin/research/Mform_top.gif" ></td>
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
            <td height="20" width="646" class="sulmun_num"><%="["+String.valueOf(i+1)+"]     " + dbox.getString("d_sultext")%> 
              <%if (dbox.getString("d_sultype").equals(SulmunTargetBean.MULTI_QUESTION)){%>
              (<%=dbox.getString("d_selmax")%>개까지 선택 가능)
              <%}%>
            </td>
          </tr>
          <%      if (dbox.getString("d_sultype").equals(SulmunTargetBean.OBJECT_QUESTION)) {   // 단일선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
		  <tr> 
            <td class="board_text4">
			  &nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>">
              <%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
          </tr>
          <%              }
            }
        } else if (dbox.getString("d_sultype").equals(SulmunTargetBean.MULTI_QUESTION)) {  // 복수선택
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
          <tr> 
            <td class="board_text4">
			&nbsp;&nbsp;<input type="checkbox" name="<%=dbox.getInt("d_sulnum")%>2" value="<%=dbox.getInt("d_selnum")%>">
              <%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
          </tr>
          <%              } 
            }
        } else if (dbox.getString("d_sultype").equals(SulmunTargetBean.SUBJECT_QUESTION)) {  // 서술형  %>
          <tr> 
            <td>
			&nbsp;&nbsp;<textarea name="textarea" cols="83" rows="5" class="input"></textarea> 
            </td>
          </tr>
          <%
		} else if (dbox.getString("d_sultype").equals(SulmunTargetBean.COMPLEX_QUESTION)) {  // 복합형
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
          <tr> 
            <td class="board_text4">
			&nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>">
              <%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
          </tr>
          <%          }
				if (j == (list.size()-1)) {    %>
          <tr><td height=10></td></tr>
		  <tr> 
            <td class="sulmun_num"><strong>기타의견</strong></td>
          </tr>
          <tr> 
            <td>
			&nbsp;&nbsp;<textarea name="textarea" cols="83" rows="5" class="input"></textarea> 
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
			&nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>">
              <%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
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
			&nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>">
              <%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
          </tr>
          <%          
                }
           } 
        } 
	}
%>
         <br><br>
          <!----------------- 제출 버튼 시작 ---------------->
          <tr> 
            <td align="right" style=padding-right:12px><img src="/images/admin/button/btn_presentation.gif" border="0"></td>
          </tr>
          <!----------------- 제출 버튼 끝 ----------------->
        </form>
      </table></td>
  </tr>
  <tr> 
    <td class=sulmun_bg>&nbsp;</td>
  </tr>
  <tr> 
    <td><img src="/images/admin/research/Mform_bottom.gif" ></td>
  </tr>
  <tr>
    <td><table cellspacing="0" cellpadding="0"  align="center">
        <tr> 
          <td><a href="javascript:history.go(-1)"><img src="/images/admin/button/btn_before.gif" border="0"></a></td>
		  <td width=8></td>
          <td><a href="javascript:sendMail()"><img src="/images/admin/button/btn_send.gif" border="0"></a></td>
		  <td width=8></td>
          <td><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
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
