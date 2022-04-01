<%
/**
 * file name : za_DamunSubjPaperPreview.jsp
 * date      : 2003/09/12
 * programmer:
 * function  : 다면평가 미리보기
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
    int     v_damunpapernum = box.getInt("p_damunpapernum");
    String  v_damunpapernm= box.getString("p_damunpapernm");
    

%>
<html>
<head>
<title>다면평가지 미리보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_study_style1.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<link href="/css/user_style_study.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
<!--
function damunmunRight(){
  document.form2.target = "openWinQuestion";
  document.form2.action.value  = '/servlet/controller.multiestimate.DamunSubjRedamuntServlet';
  document.form2.p_process.value = 'DamunUserPage';
  document.form2.submit();
}
//-->
</script>
</head>
<body>
            		<!----------------- 타이틀 시작 ----------------->
  						<table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
    						<tr> 
      							<td><img src="/images/admin/multiestimate/paper_title.gif"></td>
      							<td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
      							<td><img src="/images/user/study/gongi_tail.gif"></td>
    						</tr>
  						</table>
            		<!----------------- 타이틀 끝 ---------------->
<br>
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
  <tr> 
    <td width="13"><img src="/images/user/research/icon2.gif" width="13" height="11" border="0"></td>
    <td><b>다면평가지명 : <%=v_damunpapernm%> / 다면평가지번호 : <%=v_damunpapernum%></b></td>
  </tr>
  <tr> 
    <td height="5"></td>
  </tr>
</table>
<!----------------- 다면평가조사 시작 ---------------->
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
  <tr> 
    <td class="board_color_line"></td>
  </tr>
</table>
<table cellspacing="0" cellpadding="0" class="open_board_table_out2" align="center">
<form name="form2" method="post" action="/servlet/controller.multiestimate.DamunSubjRedamuntServlet">
  <input type="hidden" name="p_process"     value="">
  <input type="hidden" name="p_grcode"      value="<%=v_grcode%>">
  <input type="hidden" name="p_subj"        value="<%=v_subj%>">
  <input type="hidden" name="p_damunpapernum" value="<%=v_damunpapernum%>">
  <input type="hidden" name="p_damunnums" value="">

</form>
  <tr> 
    <td> 
      <table cellspacing="1" cellpadding="3" class="table2">
      <form name="form3" method="post" action="/servlet/controller.multiestimate.DamunSubjPaperServlet">
<% 
        ArrayList    blist = (ArrayList)request.getAttribute("PaperQuestionExampleList");
    
	for (int i=0; i < blist.size(); i++) {
        ArrayList list = (ArrayList)blist.get(i);  
        DataBox dbox = (DataBox)list.get(0);  
%>
        <tr> 
          <td class="board_title_bg0"><%="["+String.valueOf(i+1)+"]"+ dbox.getString("d_damuntext")%></td>
        </tr>
<%      if (dbox.getString("d_damuntype").equals(DamunSubjBean.OBJECT_QUESTION)) {   // 단일선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="checkbox" name="<%=dbox.getInt("d_damunnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%              } 
            }
        } else if (dbox.getString("d_damuntype").equals(DamunSubjBean.SUBJECT_QUESTION)) {  // 서술형  %>
        <tr> 
          <td class="board_text3">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
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
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
            <input type="radio" name="<%=dbox.getInt("d_damunnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%          }
           } 
%>
        <tr> 
          <td class="board_text4">기타의견</td>
        </tr>
        <tr> 
          <td class="board_text3">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
            <textarea name="<%=dbox.getInt("d_damunnum")%>" cols="83" rows="5" class="input"></textarea>
          </td>
        </tr>
<%				
				

        } else if (dbox.getString("d_damuntype").equals(DamunSubjBean.FSCALE_QUESTION)) {  // 5점척도
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
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
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
            <input type="radio" name="<%=dbox.getInt("d_damunnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%          
                }
           } 
        } else if (dbox.getString("d_damuntype").equals(DamunSubjBean.GSCALE_QUESTION)) {  // GAP비교
		%>
		<tr>
		  <td class="board_text5">
		    <table class="table2">
		    	<tr>
		    		<td width="50%" class="board_text5">
		  				<table border="0" width="100%" height="100%">
		  <%
            for (int j=0; j < 5; j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
                if (j == 0) {
%>
		      				<tr>
                  				<td  width="30%" rowspan="6" class="board_text5">&nbsp;<%=dbox.getString("d_scalename")%></td>
              				</tr>
<%
                }
%>
		      				<tr> 
                  				<td class="board_text5" width="70%" >
                  				&nbsp;&nbsp;&nbsp;&nbsp;
                  				<input type="radio" name="<%=dbox.getInt("d_damunnum")%>,F" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
              				</tr>
<%
                }
           } 
%>
              			</table>
              		</td>
              		<td width="50%" class="board_text5">
              			<table  border="1" width="100%"  height="100%" >
<%
            for (int j=5; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) {
                if (j == 5) {
%>
              				<tr> 
                  				<td width="30%"  rowspan="6" class="board_text5"><%=dbox.getString("d_scalename")%></td>
              				</tr>
<%
                }
%>
              				<tr>
                				<td width="70%" class="board_text5">
                				&nbsp;&nbsp;&nbsp;&nbsp;
                				<input type="radio" name="<%=dbox.getInt("d_damunnum")%>,S" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")-5%>. <%=dbox.getString("d_seltext")%></td>
              				</tr>
<%          
                }
           } %>
           				</table>
           			</td>
           		</tr>
           	</table>
         </td>
     </tr>

<%
        } 
	}
%>
      </form>
      </table>
    </td>
  </tr>
</table>
<!----------------- 다면평가조사 끝 ---------------->
<br>
<!----------------- 제출 버튼 시작 ---------------->
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
  <tr>
    <td align="right"><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
  </tr>


  <tr>
    <td><%@ include file = "/learn/library/getJspName.jsp" %></td>
  </tr>


</table>
<!----------------- 제출 버튼 끝 ----------------->
<br>
</body>
</html>