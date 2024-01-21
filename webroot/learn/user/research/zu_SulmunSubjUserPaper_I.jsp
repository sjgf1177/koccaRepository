<!DOCTYPE html>
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
    String p_gubun	 = box.getString("p_subj");   
    String p_subj    = box.getString("s_subj");
    String p_subjseq = box.getString("p_subjseq");
    String p_gyear   = box.getString("p_gyear");
    String p_sulpapernum = box.getString("p_sulpapernum");
    String p_sulnums     = box.getString("p_sulnums");
    String p_isstudy     = box.getString("p_isstudy"); // 학습창임..
   
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }
	
	ArrayList blist = (ArrayList)request.getAttribute("PaperQuestionExampleList"); 
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset="euc-kr">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>설문응시</title>

    <!-- Bootstrap / Jquery UI -->
    <link href="/common/js/jquery-ui-1.12.1/jquery-ui.min.css" rel="stylesheet">
    <!--<link href="/common/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css" rel="stylesheet">-->
    <link href="/common/js/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    
    <script src="/common/js/jquery-3.3.1.min.js"></script>
    <script src="/common/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <!--<script src="/common/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"></script>-->
    <script src="/common/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    
    
    <!-- Custom CSS -->
    <link href="/common/css/custom.css" rel="stylesheet">
    <link href="/common/css/header.css" rel="stylesheet">
    <link href="/common/css/footer.css" rel="stylesheet">
    
    <!-- Custom JS -->
    <script src="/common/js/custom.js"></script>
    
    <script type="text/javascript" src="/script/common.js"></script>
	<script type="text/javascript">
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
				if(document.form3.elements[i].type != 'hidden'){
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
							if($("#sulreturn").val() == 'Y'){
								alert("응답하지 않은 설문이 있습니다.");
								return;
							}
						}
						
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
							if(textarr != ""){
								for(var j=0; j<textarr.length; j++) {
									temp = temp + textarr[j];
								}
							}else{
								temp = " ";
							}
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
	</script>
</head>
<body id="popup">
<form name="form2" method="post" action="/servlet/controller.research.SulmunSubjUserServlet">
	<input type="hidden" name="p_grcode"     value="<%=s_grcode%>">
	<input type="hidden" name="s_subj"       value="<%=p_subj%>">
	<input type="hidden" name="p_subj"       value="<%=p_gubun%>">
	<input type="hidden" name="p_subjseq"    value="<%=p_subjseq%>">
	<input type="hidden" name="p_gyear"      value="<%=p_gyear%>">
	<input type="hidden" name="p_sulpapernum"      value="<%=p_sulpapernum%>">
	<input type="hidden" name="p_userid"       value="<%=v_userid%>">
	<input type="hidden" name="p_sulnums"      value="<%=p_sulnums%>">
	<input type="hidden" name="p_answers"      value="">
	<input type="hidden" name="p_process"      value="">
	<input type="hidden" name="p_reloadlist" value="">
	<input type="hidden" name="p_isalways" value="<%=box.getString("p_isalways")%>">
	
	<!--분류코드-->
	<input type="hidden" name="p_distcodescore1"      value="">	
	<input type="hidden" name="p_distcodescore2"      value="">	
	<input type="hidden" name="p_distcodescore3"      value="">	
</form>
<form name="form3" method="post" action="/servlet/controller.research.SulmunSubjUserServlet">
    <section>
        <div class="wrapper">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="subContainer_pop">
                            <div class="sub_section">
                                <div class="sub_contents_header">
                                    <span>설문조사</span>
                                </div>
                                <div class="sub_contents_body">
                                    <div class="sub_info_body">
                                        <div class="sub_course_alert_box">
                                            <p>[<%=p_subjnm%>]  >> <%=p_sulpapernm%></p>
                                        </div>
                                    </div>
                                    <div class="sub_info_body">
                                        <table class="write_table th_align_left1 surveyTable">
                                            <tbody>
<% 
if(!v_end.equals("0")) {

	for (int i=0; i < blist.size(); i++) {

		//System.out.println("blist.size()"+blist.size());	
	    if(blist.size()>0){
	    
            ArrayList list = (ArrayList)blist.get(i);
    
            DataBox dbox = (DataBox)list.get(0);  
        
%>
                                                <tr>
                                                    <th>
                                                        <span>[<%= String.valueOf(i+1) %>]</span><%= dbox.getString("d_sultext") %><%if (dbox.getString("d_sultype").equals(SulmunSubjBean.MULTI_QUESTION)){%>(<%=dbox.getString("d_selmax")%>개까지 선택 가능)<%}%>
                                                    </th>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <ul>
<%
			if (dbox.getString("d_sultype").equals(SulmunSubjBean.OBJECT_QUESTION)) {   // 단일선택 
	            for (int j=0; j < list.size(); j++) {
	                dbox  = (DataBox)list.get(j); 
	                if (dbox != null) { %>
                                                            <li>
                                                                <input type="radio" name="<%= dbox.getInt("d_sulnum") %>" id="<%= dbox.getInt("d_sulnum") %>_<%=j %>"  value="<%= dbox.getInt("d_selnum") %>">
                                                                <label for="<%= dbox.getInt("d_sulnum") %>_<%=j %>"><%= dbox.getInt("d_selnum") %>. <%= dbox.getString("d_seltext") %></label>
                                                            </li>
<%
					}
				}
			} else if (dbox.getString("d_sultype").equals(SulmunSubjBean.MULTI_QUESTION)) {  // 복수선택
				for (int j=0; j < list.size(); j++) {
					dbox  = (DataBox)list.get(j); 
					if (dbox != null) { %>
                                                            <li>
                                                                <input type="checkbox" name="<%= dbox.getInt("d_sulnum") %>" id="<%= dbox.getInt("d_sulnum") %>_<%=j %>"  value="<%= dbox.getInt("d_selnum") %>">
                                                                <label for="<%= dbox.getInt("d_sulnum") %>_<%=j %>"><%= dbox.getInt("d_selnum") %>. <%= dbox.getString("d_seltext") %></label>
                                                            </li>
<%
					} 
				}
			} else if (dbox.getString("d_sultype").equals(SulmunSubjBean.SUBJECT_QUESTION)) {  // 서술형  %>
                                                            <li>
                                                                <textarea name="<%= dbox.getInt("d_sulnum") %>"></textarea>
                                                                <input type="hidden" name="sulreturn" id="sulreturn"  value="<%= dbox.getString("d_sulreturn") %>">
                                                            </li>
<%
			} else if (dbox.getString("d_sultype").equals(SulmunSubjBean.COMPLEX_QUESTION)) {  // 복합형
				for (int j=0; j < list.size(); j++) {
					dbox  = (DataBox)list.get(j); 
					if (dbox != null) { %>
                                                            <li>
                                                                <input type="radio" name="<%= dbox.getInt("d_sulnum") %>" id="<%= dbox.getInt("d_sulnum") %>_<%=j %>"  value="<%= dbox.getInt("d_selnum") %>">
                                                                <label for="<%= dbox.getInt("d_sulnum") %>_<%=j %>"><%= dbox.getInt("d_selnum") %>. <%= dbox.getString("d_seltext") %></label>
                                                            </li>
<%
					}
					if (j == (list.size()-1)) {    
%>
															<li>
																기타의견<textarea name="<%= dbox.getInt("d_sulnum") %>|C" maxlength=""></textarea>
															</li>
<%				
					}
				} 
			} else if (dbox.getString("d_sultype").equals(SulmunSubjBean.FSCALE_QUESTION)) {  // 5점척도
				for (int j=0; j < list.size(); j++) {
					dbox  = (DataBox)list.get(j); 
					if (dbox != null) { %>
															<li>
                                                                <input type="radio" name="<%= dbox.getInt("d_sulnum") %>" id="<%= dbox.getInt("d_sulnum") %>_<%=j %>"  value="<%= dbox.getInt("d_selnum") %>">
                                                                <label for="<%= dbox.getInt("d_sulnum") %>_<%=j %>"><%= dbox.getInt("d_selnum") %>. <%= dbox.getString("d_seltext") %></label>
                                                            </li>
<%          
					}
				} 
			} else if (dbox.getString("d_sultype").equals(SulmunSubjBean.SSCALE_QUESTION)) {  // 7점척도
				for (int j=0; j < list.size(); j++) {
					dbox  = (DataBox)list.get(j); 
					if (dbox != null) { %>
															<li>
                                                                <input type="radio" name="<%= dbox.getInt("d_sulnum") %>" id="<%= dbox.getInt("d_sulnum") %>_<%=j %>"  value="<%= dbox.getInt("d_selnum") %>">
                                                                <label for="<%= dbox.getInt("d_sulnum") %>_<%=j %>"><%= dbox.getInt("d_selnum") %>. <%= dbox.getString("d_seltext") %></label>
                                                            </li>
<%          
					}
				} 
			}
%>
														</ul>
                                                    </td>
                                                </tr>
<%
		}
	}
}
%>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="popup_btnBox">
                                    <a href="javascript:whenSubmit();" class="btn_write">제출</a>
                                    <a href="javascript:self.close();" class="btn_courseCancel">닫기</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</form>
</body>
<style>
    .subContainer_pop .sub_info_body + .sub_info_body .write_table th{}
</style>
</html>

