<%
//**********************************************************
//  1. 제      목: PROJECT HANDLING PAGE
//  2. 프로그램명: zu_Project_U.jsp
//  3. 개      요: 리포트 등록 & 수정
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 8. 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");    
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");    
    String  v_lesson    = box.getString("p_lesson");   
    String  v_reptype   = box.getString("p_reptype");   
    String  v_projgrp   = box.getString("p_projgrp");   
    String  v_status    = box.getStringDefault("p_status","Y");       
    int     v_ordseq    = box.getInt("p_ordseq");       
    int     v_projseq   = box.getInt("p_projseq");      
    int     v_getcnt    = box.getInt("p_getcnt");      
    String  v_title     = "";
    String  v_contents  = "";
    String  v_tucontents= "";
    String  v_title2    = "";
    String  v_contents2 = "";
    String  v_expiredate= "";
    String  v_upfile    = "";    
    String  v_upfile1   = "";
    String  v_upfile2   = "";
		String  v_ordrealfile1 = ""; //과제제출 실제파일
		String  v_ordrealfile2 = ""; //정답실제 파일
	
		String  v_realfile  = ""; //학생이 제출한 실제 파일
		String 	v_upfilesize = "";
    String  v_upfile_value = "";
    String  v_upfile1_value= "";
    String  v_upfile2_value= "";
		String  v_isret     = "";
		String  v_retreason = "";
		String  v_issubmit = "";
		String  v_ansyn = "";
		String  v_eduend = "";
	
    int     v_score     =  0;
    int     v_score_mas =  0;
    int     i           =  0;
    ProjectData  data1  =null;
    ProjectData  data2  =null;
    //DEFINED class&variable END       
    // editor
    String content      = "";
    String width        = "600";
    String height       = "300";
    
       
    data1 = (ProjectData)request.getAttribute("selectProjectOrd");
    if(data1 != null){
        v_title     		= data1.getTitle();      
        v_contents  		= data1.getContents();   
        v_expiredate		= data1.getExpiredate(); 
        v_upfile    		= data1.getUpfile();     
        v_upfile2   		= data1.getUpfile2();
				v_ordrealfile1 	= data1.getRealfile();
 	    	v_ordrealfile2 	= data1.getRealfile2();
				v_ansyn        	= data1.getAnsyn();
				v_eduend       	= data1.getEduend();

        if(v_title.length() == 0)  v_title = "";
        if(v_eduend.length() == 0){ v_eduend = "";
        }else{
          if(Integer.parseInt(v_eduend) < Integer.parseInt(FormatDate.getDate("yyyyMMddHH"))){ v_status="N";  }
        }                            
        if(v_upfile.length() == 0){    v_upfile   = "";         //첨부파일
        }else{  
			//v_upfile_value =  v_upfile.substring(v_upfile.indexOf("_") + 1 );    
			v_upfile_value =  v_ordrealfile1;
		}
		
		
        if(v_upfile2.length() == 0){    v_upfile2   = "";       //답안파일
        }else{  
			//v_upfile2_value = v_upfile2.substring(v_upfile2.indexOf("_") + 1);    
			v_upfile2_value = v_ordrealfile2;
		
		}
        if(v_contents.length() == 0){ v_contents = "";
        }else{  v_contents = StringManager.replace(v_contents,"\r\n","<br>");      }  
    }    
    
    data2 = (ProjectData)request.getAttribute("selectProjectRep");
    if(data2 != null){
        v_title2    = data2.getTitle();      
        content     = StringManager.replace(data2.getContents(),"\n;","<br>");
        v_score     = data2.getScore();      
        v_upfile1   = data2.getUpfile();     
		v_realfile  = data2.getRealfile();    
		v_upfilesize = data2.getUpfilesize(); 
        v_tucontents= data2.getTucontents(); 
        v_score_mas = data2.getScore_mas();
		v_isret     = data2.getIsret();
		v_retreason = data2.getRetreason();
       
        if(v_title2.length() == 0)     v_title2    = "";        
        if(v_tucontents.length() == 0) v_tucontents= ""; 
        if(v_upfile1.length() == 0){    v_upfile1   = "";    
        }else{  
					v_upfile1_value = v_realfile;
		}
        if(v_score_mas > 0){ v_status = "N";    }
    }
    
    ProjectBean pjbean = new ProjectBean();
    if(pjbean.selectProjectRepCount(box) >0){
      v_issubmit = "Y";
    }else{
      v_issubmit = "N";
    }
    
    
    //진도율 가져오기
    EduStartBean 	bean = new EduStartBean();

	//EduScoreData   dataStep  =null;
	EduScoreData   dataStep = bean.SelectEduScore(box);

%>    

<html>
<head>
<title>::: REPORT :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<!--<link href="/css/user_style1.css" rel="stylesheet" type="text/css">오토에버 용-->
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--


    // 중간저장
    function halfregist() {               
        if (blankCheck(document.form1.p_title.value)) {
            alert("제목을 입력하세요!");
            document.form1.p_title.focus();
            return;
        }                
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }                     

    	if(document.all.use_editor[0].checked) {        
            document.form1.p_content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    	}else {
		    document.form1.p_content.value = document.all.txtDetail.value;
	    }
	      
	    //파일 확장자 필터링  
	    var islimit = true;
	    var file = document.form1.p_file1.value;  
	    if(file!="") {
	        islimit = limitFile(file);
	    }
	    
	    if(islimit) {
	          document.form1.p_contentsbyte.value = realsize(document.form1.p_content.value);
            document.form1.action = "/servlet/controller.study.ProjectServlet?g_subj=<%=v_subj%>&g_year=<%=v_year%>";
            document.form1.p_process.value = "ProjectHalfSave";                
            document.form1.submit();
	    }else{
	        return;
	    }
    }
    
    // 제출등록
    function whenProjectRep() {               
        if (blankCheck(document.form1.p_title.value)) {
            alert("제목을 입력하세요!");
            document.form1.p_title.focus();
            return;
        }                
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }                     

    	if(document.all.use_editor[0].checked) {        
            document.form1.p_content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    	}else {
		    document.form1.p_content.value = document.all.txtDetail.value;
	    }
	     
	      
	    //파일 확장자 필터링  
	    var islimit = true;
	    var file = document.form1.p_file1.value;  
	    if(file!="") {
	        islimit = limitFile(file);
	    }
	    
	    if(islimit) {
	        document.form1.p_contentsbyte.value = realsize(document.form1.p_content.value);  
            document.form1.action = "/servlet/controller.study.ProjectServlet?g_subj=<%=v_subj%>&g_year=<%=v_year%>";
            document.form1.p_process.value = "ProjectHandling";                
            document.form1.submit();
	    }else{
	        return;
	    }
    }

<%if(v_status.equals("Y")){ %>
    // 사용자보기선택
    function SelectAnsyn(ansyn) {      
		if( ansyn == "TEXT" ) {
			document.all["tabtext_1"].style.display = "";
			document.all["tabfile_2"].style.display = "none";
		}
		if( ansyn == "FILE" ){
		    document.all["tabtext_1"].style.display = "none";		
			document.all["tabfile_2"].style.display = "";		        
		}
		if( ansyn == "ALL" ){
		    document.all["tabtext_1"].style.display = "";		
			document.all["tabfile_2"].style.display = "";		        
		}
    }
<%}%>    
-->
</script>   

</head>

<!-- /////////////////////////////////////////////////////////-->
<!--
  파일확장자체크 INCLUDE
  
  1. page import = "com.credu.system.*" 추가
  2. 자바스크립트 호출부분 추가
    예제)
    //파일 확장자 필터링  
	var islimit = true;
	var file = document.form1.p_file1.value;  
	if(file!="") {
	    islimit = limitFile(file);
	}
	
	if(islimit) {
        document.form1.submit();
	}else{
	    return;
	}

-->
<%@ include file = "/learn/library/fileFilter.jsp" %>
<!-- /////////////////////////////////////////////////////////-->

<body topmargin="0" leftmargin="0">
<form name="form1" method="post" enctype = "multipart/form-data">
<input type="hidden" name="p_process">
<input type="hidden" name="p_subj" value="<%=v_subj%>">
<input type="hidden" name="p_year" value="<%=v_year%>">
<input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
<input type="hidden" name="p_ordseq" value="<%=v_ordseq%>">
<input type="hidden" name="p_projseq" value="<%=v_projseq%>">
<input type="hidden" name="p_lesson" value="<%=v_lesson%>">
<input type="hidden" name="p_reptype" value="<%=v_reptype%>">
<input type="hidden" name="p_projgrp" value="<%=v_projgrp%>">
<input type="hidden" name="p_upfile1" value="<%=v_upfile1%>">
<input type="hidden" name="p_contentsbyte" value="">
<input type="hidden" name="p_content"      value="">
<input type="hidden" name="p_getcnt"       value="<%=v_getcnt%>">

    
<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="783" height="100%">
  <tr> 
    <td> 
      <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
        <tr> 
            <td align="center" valign="top" class="body_color">
			<br>
              <!----------------- 타이틀 시작 ----------------->
              <table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
                <tr> 
                  <td><img src="/images/user/study/report_title.gif"></td>
                  <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
                  <td><img src="/images/user/study/gongi_tail.gif"></td>
                </tr>
              </table>
              <!----------------- 타이틀 끝 ---------------->
            <br>

            <table width="94%" border="0" cellpadding="0" cellspacing="10" bgcolor="#EEEEEE">
              <tr> 
                <td> 
                  <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2">
                    <tr> 
                      <td align="center" bgcolor="#FFFFFF">


                        <br>


                        <table cellspacing="0" cellpadding="0" align="center" width="98%">
                          <tr> 
                            <td colspan="2">※<b> 과제물 제출요령</b></td>
                          </tr>
                          <tr> 
                            <td height="6"></td>
                          </tr>
                          <tr> 
                            <td width="2%">&nbsp;</td>
                            <%
                            	if(v_ansyn.equals("U")) {
                            %>
                            <td>과제물 제출시 첨부파일 (워드,엑셀 등)로 제출할 경우에는 첨부파일 
                              옆의 [찾아보기]를 클릭 후 제출할 파일을 지정 한 후, 제목과 내용을 간략하게 입력 
                              후, 아래의 저장 버튼을 클릭합니다.<br>
                              <br>
                              첨부파일 없이 과제물을 제출 시는 제목,내용만을 입력 후 저장버튼을 클릭합니다.<br>
                                단, 내용은 2,000자 미만으로 제한되므로 <font color="red">내용이 
                                많을 경우 첨부파일로 제출해 주십시요.<br>
                                <b>첨부파일명은 본인의 이름으로 작성 제출하세요.</b> </font><br>
                                <br>
							  								<font color="#CC3300"><b>※ 주의 : 타인의 과제를 도용할 경우(모사답안)  "0"점(미수료) 처리됩니다.</font>
													  </td>
													  <%
													  	} else if(v_ansyn.equals("T")) {
													  %>
													  <td>아래의 과제물을 과제물제출란에 작성하시기 바랍니다.
														과제물 제출후 채점완료가 되기전까지는 수정이 가능합니다.<br><br>
														<font color="red"><b>[주의사항]</b></font><br>
														과제물 작성시 너무 오랫동안 컴퓨터를 켜놓으면 작성된 파일이 제출이 되지 않을 수도 있습니다.
														파일로 작성하셔서 옮기시거나 중간에 저장을 한번씩 하시기 바랍니다.
														과제물은 <font color=blue><b>A4장분량</b></font>으로 제출해주시기 바랍니다.
														평가 답안 작성은 반드시 본인이 직접 작성 하셔야만 되며, 만일 타인에게 답안 작성을 의뢰하는 등 모사제출, 부정한 방법으로 답안을 제출하시면 수료할 수 없습니다.
														모사답안이거나 0점을 받으면 미수료가 됩니다. 성실하게 작성해주시기 바랍니다.
														</td>
													  <%
													  	} else if(v_ansyn.equals("F")) {
													  %>
													  <td>과제물을 작성하셔서 파일로 제출하시기 바랍니다.
														과제물 제출후 채점완료가 되기전까지는 수정이 가능합니다.<br><br>
														<font color=red><b>[주의사항]</b></font><br>
														평가 답안 작성은 반드시 본인이 직접 작성 하셔야만 되며, 만일 타인에게 답안 작성을 의뢰하는 등 모사제출, 부정한 방법으로 답안을 제출하시면 수료할 수 없습니다.
														모사답안이거나 0점을 받으면 미수료가 됩니다. 성실하게 작성해주시기 바랍니다.</td>
													  <%
													  	}
													  %>
                          </tr>
                        </table>
                        <br>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <br>
            <br>
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td width="13"><img src="/images/user/study/icon2.gif" width="13" height="11" border="0"></td>
                <td><b>출제과제정보</b></td>
              </tr>
              <tr> 
                <td height="5"></td>
              </tr>
            </table>
            <!----------------- 출제과제정보 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td class="board_color_line"></td>
              </tr>
            </table>
            <table cellspacing="0" cellpadding="0" class="open_board_table_out2">
              <tr> 
                <td> 
                  <table cellspacing="1" cellpadding="3" class="table2">                
                    <tr> 
                      <td class="board_title_bg1" width="15%">제 목</td>
                      <td class="board_title_bg3" width="28%"><%=v_title%></td>
                      <td class="board_title_bg1" width="15%">첨부파일</td>
                      <td class="board_title_bg3" width="27%">
                      <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_upfile %>&p_realfile=<%= v_upfile%>'><%= v_upfile_value %></a>
                      </td>
                    </tr>
<% if(v_status.equals("N")){        //과제물 제출이 마감된 경우
%>                 
                    <tr> 
                      <td class="board_title_bg1" width="15%">답안파일</td>
                      <td class="board_title_bg3" colspan="3">
                      <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_upfile2 %>&p_realfile=<%= v_upfile2_value %>'><%= v_upfile2_value %></a>
                      </td>
                    </tr>
<% } %>                    
                    <tr> 
                      <td colspan="4" class="board_text1" align="center"> 
                        <table width="98%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td><%= v_contents %></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 출제과제정보 끝 ---------------->
            <br>
            <br>
<% if(v_status.equals("Y")){        //과제물 제출이 가능한 경우   
%>

            <!----------------- 제출, 취소 버튼 시작 ---------------->

            <table border=0 cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td width="13" valign="bottom"><img src="/images/user/study/icon2.gif" width="13" height="11" border="0"></td>
                <td valign="bottom"><b>과제물 제출 정보</b></td>
                <td align="right" width="220">

                <%if(v_issubmit.equals("N")){%>
                    <a href="javascript:halfregist();"><img src="/images/user/button/btn_midsave.gif" border="0"></a>&nbsp;
                <%}%>
                <% if (dataStep.getTstep()<0.00) { %> 
                	<a href="javascript:alert('현재 진도는 <%=dataStep.getTstep()%>%입니다.\n80% 이상시 리포트 제출이 가능합니다.')"><img src="/images/user/button/btn_register.gif"border="0"></a>
                <% } else {%>
	                <a href="javascript:whenProjectRep()"><img src="/images/user/button/btn_register.gif" border="0"></a>
                <% } %>
                &nbsp;<a href="javascript:history.back()"><img src="/images/user/button/btn_cancel.gif"  border="0"></a>
                </td>
              </tr>                      
                                    
              <tr> 
                <td height="5"></td>
              </tr>
            </table>
            <!----------------- 제출, 취소 버튼 끝 ---------------->

            <!----------------- 과제물 제출 정보 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_vertical_table_out">
              <tr> 
                <td width="3" class="board_color_line"></td>
                <td> 
                  <table cellspacing="1" cellpadding="1" class="table2">
                    <tr> 
                      <td class="board_title_bg2" width="150"> </td>
                      <td class="table_text3"> 
                      <%if(v_ansyn.equals("U")){%><!--사용자직접선택-->
                        &nbsp;<input type=radio name='p_ansyn'  value='<%=v_ansyn%>' checked OnClick="SelectAnsyn('ALL');">ALL 
                        &nbsp; <input type=radio name='p_ansyn' value='<%=v_ansyn%>'  OnClick="SelectAnsyn('TEXT');">TEXT입력방식    
                        &nbsp; <input type=radio name='p_ansyn' value='<%=v_ansyn%>'  OnClick="SelectAnsyn('FILE');">파일업로드                                              
                        
                      <%}else if(v_ansyn.equals("T")){%><!--TEXT입력방식-->
                        <!--&nbsp;<input type=radio name='p_ansyn'  value='<%=v_ansyn%>'  OnClick="SelectAnsyn('ALL');">ALL -->
                        &nbsp; <input type=radio name='p_ansyn' value='<%=v_ansyn%>' checked OnClick="SelectAnsyn('TEXT');">TEXT입력방식    
                        <!--&nbsp; <input type=radio name='p_ansyn' value='<%=v_ansyn%>'  OnClick="SelectAnsyn('FILE');">파일업로드  -->
                      
                      <%}else if(v_ansyn.equals("F")){%> <!--파일업로드-->
                        <!--&nbsp;<input type=radio name='p_ansyn'  value='<%=v_ansyn%>'  OnClick="SelectAnsyn('ALL');">ALL -->
                        <!--&nbsp; <input type=radio name='p_ansyn' value='<%=v_ansyn%>'  OnClick="SelectAnsyn('TEXT');">TEXT입력방식    -->
                        &nbsp; <input type=radio name='p_ansyn' value='<%=v_ansyn%>' checked OnClick="SelectAnsyn('FILE');">파일업로드  

                      <%}%> 
                      </td>
                    </tr>                  
                    <tr> 
                      <td class="board_title_bg2" width="150">제목 *</td>
                      <td class="table_text3"> 
                        <input size="90" maxlength="50" name="p_title" class="input" value="<%=v_title2%>">
                      </td>
                    </tr>
                    <tr id="tabtext_1" > 
                      <td class="board_title_bg2">내용</td>
                      <td class="table_text3"> 
                        <!--<textarea name="p_contents" cols="85" rows="15" class="input"><%=v_contents2 %></textarea>-->
						<!-- DHTML Editor  -->
			            <%//@ include file="/portal/include/DhtmlEditor.jsp" %>
    		            <%@ include file="/include/DhtmlEditor.jsp" %>
                        <!-- DHTML Editor  -->                          
                      </td>
                    </tr>
                    <tr id="tabfile_2" > 
                      <td class="board_title_bg2" width="140">첨부파일</td>
                      <td class="table_text3"> 
                        <table cellspacing="0" cellpadding="0" class="table2">
                          <tr> 
                            <td width="100%"> 
                              <input type="file" name="p_file1" size="70" class="input"> 
                              <% if(v_upfile1.length() > 0){ %> 
                              <br>제출한 파일 : <a href = '/servlet/controller.library.DownloadServlet?p_year=<%=v_year%>&p_subj=<%=v_subj%>&p_savefile=<%= v_upfile1 %>&p_realfile=<%= v_upfile1_value %>'><%=v_upfile1_value%></a> (<%=v_upfilesize%>Byte)
							  <input type="checkbox" name="p_check" value="Y"> 기존파일 삭제
							  
                              <% } %>
                            </td>
                            <td></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg2" width="150">점수</td>
                      <td class="table_text3"><%=v_score%></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 과제물 제출 정보 끝 ---------------->
<% }else if(v_status.equals("N")){ %>
            <!----------------- 제출, 취소 버튼 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td width="13" valign="bottom"><img src="/images/user/study/icon2.gif" width="13" height="11" border="0"></td>
                <td valign="bottom"><b>과제물 제출 정보</b></td>
                <td align="right" width="61">
                <a href="javascript:history.back()"><img src="/images/user/button/btn_cancel.gif" border="0"></a>
                </td>
              </tr>
              <tr> 
                <td height="5"></td>
              </tr>
            </table>
            <!----------------- 제출, 취소 버튼 끝 ---------------->
            <!----------------- 과제물 제출 정보 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_vertical_table_out">
              <tr> 
                <td width="3" class="board_color_line"></td>
                <td> 
                  <table cellspacing="1" cellpadding="1" class="table2">
                    <tr> 
                      <td class="board_title_bg2" width="150">제 목 *</td>
                      <td class="table_text3"><%=v_title2%></td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg2">내 용</td>
                      <td class="table_text3">
                      <% if(v_contents2.length() > 0){ %>
                      <%=  v_contents2 = StringManager.replace(v_contents2,"\r\n","<br>") %>     
                      <% } %>                 
                       </td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg2" width="150">첨부파일</td>
                      <td class="table_text3"> 
                        <table cellspacing="0" cellpadding="0" class="table2">
                          <tr> 
                            <td width="100%">
                              <% if(v_upfile1.length() > 0){ %> 
                              <a href = '/servlet/controller.library.DownloadServlet?p_year=<%=v_year%>&p_subj=<%=v_subj%>&p_savefile=<%= v_upfile1 %>&p_realfile=<%= v_upfile1_value %>'><%=v_upfile1_value%></a>
                              <% } %>
                            </td>
                            <td></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg2" width="150">점 수</td>
                      <td class="table_text3"><%=v_score%></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 과제물 제출 정보 끝 ---------------->
<% } %>            

            <br>
            <br>




            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td width="13"><img src="/images/user/study/icon2.gif" width="13" height="11" border="0"></td>
                <td><b>강사 첨삭내용</b></td>
              </tr>
              <tr> 
                <td height="5"></td>
              </tr>
            </table>      


		<% System.out.println("isret="+v_isret); %>
     
            <!----------------- 강사 첨삭내용 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td class="board_color_line"></td>
              </tr>
            </table>
            <table cellspacing="0" cellpadding="0" class="open_board_table_out2">
              <tr> 
                <td> 
                  <table cellspacing="1" cellpadding="3" class="table2">
                    <tr> 
                      <td class="board_title_bg1">내용</td>
                    </tr>
                    <tr> 
                      <td class="board_text1" height="27"><%=v_tucontents%></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 강사 첨삭내용 끝 ---------------->
            <br>
            <br>   
			
			<% if (v_isret.equals("Y")) { %>
			<table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td width="13"><img src="/images/user/study/icon2.gif" width="13" height="11" border="0"></td>
                <td><b>강사 반려내용</b></td>
              </tr>
              <tr> 
                <td height="5"></td>
              </tr>
            </table>      

            <!----------------- 강사 반려내용 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td class="board_color_line"></td>
              </tr>
            </table>
            <table cellspacing="0" cellpadding="0" class="open_board_table_out2">
              <tr> 
                <td> 
                  <table cellspacing="1" cellpadding="3" class="table2">
                    
                    <tr> 
                      <td class="board_text1" height="27"><%=v_retreason%></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 강사 반려내용 끝 ---------------->
            <br>
            <br>   
			<% } %>

<%
    if(v_reptype.equals("C")){        //COP의 경우 동료평가점수 리스트
      
        String v_couserid   = "";
        String v_coname     = "";
        String v_ldate      = "";
        int    v_score1     =  0;          
        ArrayList list = (ArrayList)request.getAttribute("selectCoprepList");
%>
     
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td width="13"><img src="/images/user/study/icon2.gif" width="13" height="11" border="0"></td>
                <td><b>동료들의 평가보기</b></td>
              </tr>
              <tr> 
                <td height="5"></td>
              </tr>
            </table>        
            <!----------------- 동료들의 평가보기 내용 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td class="board_color_line"></td>
              </tr>
            </table>
            <table cellspacing="0" cellpadding="0" class="open_board_table_out2">
              <tr> 
                <td> 
                  <table cellspacing="1" cellpadding="3" class="table2">
                    <tr> 
                      <td class="board_title_bg1" width="7%">번호</td>
                      <td class="board_title_bg1" width="15%">작성자</td>
                      <td class="board_title_bg1" width="15%">작성일</td>
                      <td class="board_title_bg1" width="10%">점수</td>
                    </tr>
     <% 
            for(i = 0; i < list.size(); i++) {        
                ProjectData data  = (ProjectData)list.get(i);
                v_couserid   = data.getCouserid();
                v_coname     = data.getConame();    
                v_ldate      = data.getLdate();     
                v_score1     = data.getScore();
                if(v_ldate != null){ v_ldate    = FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd"); }
      %>          
                    <tr> 
                      <td class="board_text1" height="27"><%= list.size() - i %></td>
                      <td class="board_text1" height="27"><%= v_coname %></td>
                      <td class="board_text1" height="27"><%= v_ldate %></td>
                      <td class="board_text1" height="27"><%= v_score1 %></td>
                    </tr>
        <% } if(i == 0){ %>  
                      <td class="board_text1" height="27" colspan="4">동료 평가가 없습니다.</td>
                    <% } %>                                    
                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 동료들의 평가보기 내용 끝 ---------------->
<% } %>




            <br>





          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>

<%if(v_status.equals("Y")){ %>
<SCRIPT LANGUAGE="JavaScript">
<!--
<%if(v_ansyn.equals("U")){%><!--사용자직접선택-->
		    document.all["tabtext_1"].style.display = "";
		    document.all["tabfile_2"].style.display = "";                                       
<%}else if(v_ansyn.equals("T")){%><!--TEXT입력방식-->
			document.all["tabtext_1"].style.display = ""
			document.all["tabfile_2"].style.display = "none";
<%}else if(v_ansyn.equals("F")){%> <!--파일업로드-->
		    document.all["tabtext_1"].style.display = "none";
			document.all["tabfile_2"].style.display = ""
<%}%> 
//-->
</SCRIPT>  
<%}%> 
</body>
</html>
