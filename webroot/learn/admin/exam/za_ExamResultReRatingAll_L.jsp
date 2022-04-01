<%
/**
 * file name : za_ExamResultReRatingAll_L.jsp
 * date      : 2005/08/03
 * programmer:
 * function  : 과정 재채점    
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String v_reloadlist = box.getString("p_reloadlist");  // opener reload
	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

    ArrayList blist = (ArrayList)request.getAttribute("PaperResult");
    
Log.info.println(blist.size()+"blist.size()");    
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/style_myhome.css">
<script src="/script/cafe_select.js" language="JavaScript"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function onload() {
  window.self.close();
}

<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go');
<% } %>

// 재채점 등록
function fnReRating(){
  if(confirm('재채점 하시겠습니까?')){
  var exam ="";
  var a_name ="";
  var examnum = "";
  var i=0;
  var b_name="";
  var c_name="";
  var c_value="";
  var b_type=""; 
  var result="";
  var result2="";  
  var temp=""; 
  var textarr ="";
  var answercnt=0;
  var replycnt =0;

  var v_current = new Date();
  var v_ended = "";
  var v_temp = "";

  v_ended += String(v_current.getFullYear());

  v_temp = String((v_current.getMonth()+1));
  if(v_temp.length == 1){
      v_ended += "0" + v_temp;       
  }else{
	  v_ended += v_temp;
  }
  v_temp = String(v_current.getDate());
  if(v_temp.length == 1){
      v_ended += "0" + v_temp;       
  }else{
	  v_ended += v_temp;
  }
  v_temp = String(v_current.getHours());
  if(v_temp.length == 1){
      v_ended += "0" + v_temp;       
  }else{
	  v_ended += v_temp;
  }
  v_temp = String(v_current.getMinutes());
  if(v_temp.length == 1){
      v_ended += "0" + v_temp;       
  }else{
	  v_ended += v_temp;
  }
  v_temp = String(v_current.getSeconds());
  if(v_temp.length == 1){
      v_ended += "0" + v_temp;       
  }else{
	  v_ended += v_temp;
  }

  
  var ichk = 0; // 주관식 인정정답
  for(i=0;i<document.form3.length;i++) {
		c_name  = document.form3.elements[i].name;
		c_value = document.form3.elements[i].value;
	
		if (document.form3.elements[i].type=="radio") {
		  b_type="radio";
		  if (document.form3.elements[i].checked==true) {
			temp = c_name+"^"+c_value; 
			result = result + temp + ",";
		  }
		} else if (document.form3.elements[i].type=="text") {
		  b_type="text";
		  if(ichk%10==0){
    		//alert(c_name);
    		temp = c_name+"^"+c_value; 
    	  }else if(ichk%10==9){
    	    temp = ",";
          }else{
    		//alert(c_name+"-"+c_value);
    		if(c_value!='') temp = "^"+c_value;
    		else            temp = "";
          }
          
    	  result2 = result2 + temp;          
          ichk++;
	    }
  }  

  //alert("result>>"+result);
  //alert("result2>>"+result2);

  document.form2.p_change1.value = result; // 바꾸는 정보(객관식)
  document.form2.p_change2.value = result2; // 바꾸는 정보(주관식)  
  document.form2.p_ended.value  = v_ended;
  document.form2.p_process.value = 'ExamUserReRatingAllInsert';
  document.form2.p_exam.value = examnum;
  document.form2.submit();
  
  }
}
-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin=0 leftmargin=0 <%=v_onload%>>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td >

<form name="form2" method="post" action="/servlet/controller.exam.ExamUserServlet">
    <input type="hidden" name="p_subj"         value="<%=box.getString("p_subj")%>">
    <input type="hidden" name="p_gyear"        value="<%=box.getString("p_gyear")%>">
    <input type="hidden" name="p_subjseq"      value="<%=box.getString("p_subjseq")%>">
    <input type="hidden" name="p_lesson"       value="<%=box.getString("p_lesson")%>">
    <input type="hidden" name="p_examtype"     value="<%=box.getString("p_examtype")%>">
    <input type="hidden" name="p_papernum"     value="<%=box.getString("p_papernum")%>">
    <input type="hidden" name="p_userid"       value="<%=box.getString("p_userid")%>">
    <input type="hidden" name="p_exam"         value="">
    <input type="hidden" name="p_answer"       value="">    
    <input type="hidden" name="p_process"      value="">   
    <input type="hidden" name="p_ended"        value="">   
    <input type="hidden" name="p_change1"       value=""><!--객관식-->      
    <input type="hidden" name="p_change2"       value=""><!--주관식-->  
</form>
<form name="form3" method="post" action="/servlet/controller.exam.ExamUserServlet">    
      <!----------------- 타이틀 시작 ----------------->
      <table cellspacing="0" cellpadding="0" class="open_table_out">
        <tr> 
          <td><img src="/images/admin/exam/result_title.gif"></td>
          <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
          <td><img src="/images/user/study/gongi_tail.gif"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="center"> 
    
    <table border="0" cellpadding="0" cellspacing="0" width="630">
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="1" cellpadding="0" class=testbox_out>
              <tr> 
                <td class=testbox_con>[과정명] : <%=box.getString("p_subj")%> / [연도] : <%=box.getString("p_gyear")%> / [차수] : <%=box.getString("p_subjseq")%> / [시험지번호] : <%=box.getString("p_papernum")%> <br>
                </td>
              </tr>
            </table>
            
         </td>
        </tr>
		<tr> 
          <td height=8></td>
        </tr>
<% 
	for (int i=0; i < blist.size(); i++) {
        ArrayList list = (ArrayList)blist.get(i);  
        DataBox dbox = (DataBox)list.get(0); 
%>
        <tr> 
          <td><table width="630" border="0" cellspacing="1" cellpadding="0" class=boardskin1_out>
              <tr> 
                <td colspan="2" class=boardskin1_textb_q>&nbsp;<%="["+dbox.getString("d_examnum")+"]" + dbox.getString("d_examtext")%></td>
                <td width="49" class=boardskin1_textb>난이도</td>
                <td width="41" class=boardskin1_textb>재채점</td>                
              </tr>
<%      if (dbox.getString("d_examtype").equals(ExamBean.OBJECT_QUESTION)) {   // 단일선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>

              <tr> 
                <td  class=boardskin1_textn>
				<%if(dbox.getString("d_isanswer").equals("Y")){
				      if(dbox.getInt("d_selnum") < 10){
                %>
				<img src="/images/user/etest/Qnum0<%=dbox.getInt("d_selnum")%>_check.gif"><font color="blue">정답</font>				     
				<%
					  }else{       
				%>
				<img src="/images/user/etest/Qnum<%=dbox.getInt("d_selnum")%>_check.gif"><font color="blue">정답</font>				
				    <%}%>
				<%}else{
				      if(dbox.getInt("d_selnum") < 10){
                %>
				<img src="/images/user/etest/Qnum0<%=dbox.getInt("d_selnum")%>.gif">					     
				<%
					  }else{       
				%>
				<img src="/images/user/etest/Qnum<%=dbox.getInt("d_selnum")%>.gif">		
				    <%}%>
				<%}%>
				</td>
                <td width="497" class=boardskin1_textn>
				<%=dbox.getString("d_seltext")%>
				</td>
                <%if(j==0){%>
                <td rowspan="<%=list.size()%>" class=skin_tb1><%=dbox.getString("d_levelsnm")%></td>
                <%}%> 
                <td class=skin_tb1>
				    <input type="radio" name='<%=dbox.getInt("d_examnum")%>' value='<%=dbox.getInt("d_selnum")%>' class='inputradio'>
				</td>               
              </tr>
              
<%              } 
            }            

        }else if (dbox.getString("d_examtype").equals(ExamBean.OX_QUESTION)) {   // ox식 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>

              <tr> 
                <td  class=boardskin1_textn>
				<%if(dbox.getString("d_isanswer").equals("Y")){
				      if(dbox.getInt("d_selnum") < 10){
                %>
				<img src="/images/user/etest/Qnum0<%=dbox.getInt("d_selnum")%>_check.gif"><font color="blue">정답</font>				     
				<%
					  }else{       
				%>
				<img src="/images/user/etest/Qnum<%=dbox.getInt("d_selnum")%>_check.gif"><font color="blue">정답</font>				
				    <%}%>
				<%}else{
				      if(dbox.getInt("d_selnum") < 10){
                %>
				<img src="/images/user/etest/Qnum0<%=dbox.getInt("d_selnum")%>.gif">					     
				<%
					  }else{       
				%>
				<img src="/images/user/etest/Qnum<%=dbox.getInt("d_selnum")%>.gif">		
				    <%}%>
				<%}%>
				</td>
                <td width="497" class=boardskin1_textn>
				<%=dbox.getString("d_seltext")%>
				</td>
                <%if(j==0){%>
                <td rowspan="<%=list.size()%>" class=skin_tb1><%=dbox.getString("d_levelsnm")%></td>
                <%}%> 
                <td class=skin_tb1>
				    <input type="radio" name='<%=dbox.getInt("d_examnum")%>' value='<%=dbox.getInt("d_selnum")%>' class='inputradio'>
				</td>               
              </tr>
          
<%              } 
            }

                           
        } else if (dbox.getString("d_examtype").equals(ExamBean.SUBJECT_QUESTION)) {  // 주관식  %>
              <tr> 
                <td  class=boardskin1_textn>&nbsp;</td>
                <td width="497" class=boardskin1_textn>&nbsp;</td>
                <td rowspan="<%=list.size()+1+(10-list.size())%>" class=skin_tb1><%=dbox.getString("d_levelsnm")%></td>
                <td class=skin_tb1 rowspan="<%=list.size()+1+(10-list.size())%>" >
				    &nbsp;
				</td>  				
              </tr>            
<%
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
%>
              <tr> 
                <td  class=boardskin1_textn><font color="blue">인정<br>답안<%=j+1%></font></td>
                <td width="497" class=boardskin1_textn><input type="text" name='<%=dbox.getInt("d_examnum")%>' class='input' size='50' value='<%=dbox.getString("d_seltext")%>'></td>
				</td>			
              </tr>
<%
				}
		   }
            for (int j=list.size(); j < 10; j++) {
%>		   
              <tr> 
                <td  class=skin_tb1 width="49">인정<br>답안<%=j+1%></td>
                <td width="497" class=skin_tb1><input type="text" name='<%=dbox.getInt("d_examnum")%>' class='input' size='50' ></td>
				</td>
              </tr>  
<%             
            }          
            
        } else if (dbox.getString("d_examtype").equals(ExamBean.MULTI_QUESTION)) {  // 다답식
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
              <tr> 
                <td  class=boardskin1_textn></td>
                <td width="497" class=boardskin1_textn></td>
                <td rowspan="4" class=skin_tb1></td>
                <td rowspan="4" class=skin_tb1></td>
              </tr>

<%         }
		}
	 }
%>
              <tr> 
                <td  class=skin_tb1 width="49">해설</td>
                <td colspan="4" class=skin_tb2 align="left">&nbsp;&nbsp;
				<%=dbox.getString("d_exptext")%></td>
              </tr>
            </table></td>
        </tr>
		<tr> 
          <td height=6></td>
        </tr>
<%
	}
%>


		<tr> 
          <td height=15></td>
        </tr>
        <tr> 
          <td class=dotline height=1></td>
        </tr>
		<tr> 
          <td height=15></td>
        </tr>
        <tr> 
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                <td>
                <a href="javascript:fnReRating()"><img src="/images/admin/button/btn_apply.gif" border="0" ></a>&nbsp; 
                <a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0" ></a>
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
      
      </form><!--폼-->
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
