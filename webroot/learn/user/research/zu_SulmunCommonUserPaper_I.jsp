<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: 나의 설문
//  2. 프로그램명: zu_SulmunCommonUserPaper_I.jsp
//  3. 개      요: 일반 설문 응시
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2010.01.28
//  7. 수      정:
//***********************************************************
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

    String v_userid = box.getSession("userid");
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>설문응시</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/common.js"></script>
<script language="JavaScript">
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
</script>
</head>
<body id="popup"><!-- popup size : 600* -->
<form name="form2" method="post" action="/servlet/controller.research.SulmunCommonUserServlet">
    <input type="hidden" name="p_grcode"      value="<%=v_grcode%>"/>
    <input type="hidden" name="p_subj"        value="<%=v_subj%>"/>
    <input type="hidden" name="p_subjseq"     value="<%=v_subjseq%>"/>
    <input type="hidden" name="p_gyear"       value="<%=v_gyear%>"/>
    <input type="hidden" name="p_sulpapernum" value="<%=v_sulpapernum%>"/>
    <input type="hidden" name="p_userid"      value="<%=v_userid%>"/>
    <input type="hidden" name="p_sulnums"     value="<%=v_sulnums%>"/>
    <input type="hidden" name="p_answers"     value=""/>
    <input type="hidden" name="p_process"     value=""/>
</form>
<form name="form3" method="post" action="/servlet/controller.research.SulmunCommonUserServlet">

	<div id="pop_header">
		<h1><img src="/images/portal/myclass/pop_h1_tit8.gif" alt="설문응시" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap" class="message_top">
			<p class="tit_h2">설문지명 : <%=v_sulpapernm%> / 연도 : <%=v_gyear%></p>
<% 
	ArrayList    blist = (ArrayList)request.getAttribute("PaperQuestionExampleList");
    
	for (int i=0; i < blist.size(); i++) {
        ArrayList list = (ArrayList)blist.get(i);
        DataBox dbox = (DataBox)list.get(0);  

%>
			<table class="write <%= (i==0)?"mg_t18":"mg_t10" %>">
			<tbody>
			<tr>
				<th class="th end ta_l"><strong><%= String.valueOf(i+1) %>. <%= dbox.getString("d_sultext") %><%if (dbox.getString("d_sultype").equals(SulmunCommonBean.MULTI_QUESTION)){%>(<%=dbox.getString("d_selmax")%>개까지 선택 가능)<%}%></strong></th>
			</tr>
			<tr>
				<td class="td examine">
<%      if (dbox.getString("d_sultype").equals(SulmunCommonBean.OBJECT_QUESTION)) {   // 단일선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
					<dl class="examine_list <%= (j==0)?"first":"" %>ov_fl">
						<dt><input name="<%= dbox.getInt("d_sulnum") %>" type="radio" value="<%= dbox.getInt("d_selnum") %>" /></dt>
                        <%--<dd><%= dbox.getInt("d_selnum") %>. <%= dbox.getString("d_seltext") %></dd>--%>
						<dd><%= dbox.getString("d_seltext") %></dd>
					</dl>
<%              }
            }
        } else if (dbox.getString("d_sultype").equals(SulmunCommonBean.MULTI_QUESTION)) {  // 복수선택
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
					<dl class="examine_list <%= (j==0)?"first":"" %>ov_fl">
						<dt><input name="<%= dbox.getInt("d_sulnum") %>" type="checkbox" value="<%= dbox.getInt("d_selnum") %>" /></dt>
                        <%--<dd><%= dbox.getInt("d_selnum") %>. <%= dbox.getString("d_seltext") %></dd>--%>
						<dd><%= dbox.getString("d_seltext") %></dd>
					</dl>
<%              } 
            }
        } else if (dbox.getString("d_sultype").equals(SulmunCommonBean.SUBJECT_QUESTION)) {  // 서술형  %>
					<dl class="examine_list first ov_fl">
						<dt></dt>
						<dd><textarea name="<%= dbox.getInt("d_sulnum") %>" cols="90" rows="5" class="input"></textarea></dd>				
					</dl>
<%
		} else if (dbox.getString("d_sultype").equals(SulmunCommonBean.COMPLEX_QUESTION)) {  // 복합형
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
					<dl class="examine_list <%= (j==0)?"first":"" %>ov_fl">
						<dt><input name="<%= dbox.getInt("d_sulnum") %>" type="radio" value="<%= dbox.getInt("d_selnum") %>" /></dt>
                        <%--<dd><%= dbox.getInt("d_selnum") %>. <%= dbox.getString("d_seltext") %></dd>--%>
						<dd><%= dbox.getString("d_seltext") %></dd>
					</dl>
<%              }
				if (j == (list.size()-1)) {    
%>
					<dl class="examine_list first ov_fl" >
						<dt>기타의견</dt>
						<dd><textarea name="<%= dbox.getInt("d_sulnum") %>|C" cols="90" rows="5" class="input" maxlength="200"></textarea></dd>				
					</dl>
<%				
				}
           } 
        } 
        
        else if (dbox.getString("d_sultype").equals(SulmunCommonBean.FSCALE_QUESTION)) {  // 5점척도
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
					<dl class="examine_list <%= (j==0)?"first":"" %>ov_fl">
						<dt><input name="<%= dbox.getInt("d_sulnum") %>" type="radio" value="<%= dbox.getInt("d_selnum") %>" /></dt>
                        <%--<dd><%= dbox.getInt("d_selnum") %>. <%= dbox.getString("d_seltext") %></dd>--%>
						<dd><%= dbox.getString("d_seltext") %></dd>
					</dl>
<%          
                }
           } 
        } else if (dbox.getString("d_sultype").equals(SulmunCommonBean.SSCALE_QUESTION)) {  // 7점척도
			for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
					<dl class="examine_list <%= (j==0)?"first":"" %>ov_fl">
						<dt><input name="<%= dbox.getInt("d_sulnum") %>" type="radio" value="<%= dbox.getInt("d_selnum") %>" /></dt>
                        <%--<dd><%= dbox.getInt("d_selnum") %>. <%= dbox.getString("d_seltext") %></dd>--%>
						<dd><%= dbox.getString("d_seltext") %></dd>
					</dl>
<%          
                }
			} 
		}
%>
				</td>
			</tr>
			</tbody>
			</table>
<%
	}
%>
		</div>
	</div>
	<div id="pop_footer">
		<p class="f_btn"><a href="javascript:whenSubmit()" class="btn_gr"><span>제출</span></a><a href="javascript:self.close()" class="btn_gr"><span>닫기</span></a></p>
	</div>
</form>
</body>
</html>