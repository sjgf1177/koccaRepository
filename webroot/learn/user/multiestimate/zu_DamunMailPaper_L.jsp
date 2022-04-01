<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    int v_cnt = 0;
	String v_msg = "";
	int v_msggbn = 0;
	String v_start = "";
	String v_end = "";
	
    DataBox dbox0 = (DataBox)request.getAttribute("DamunPaperData");
			
			String v_grcode     = dbox0.getString("d_grcode"); 
	        String v_subj     = dbox0.getString("d_subj"); 
	        String v_subjseq     =box.getString("p_subjseq");
            String v_gyear     = dbox0.getString("d_year"); 
            int v_damunpapernum = dbox0.getInt("d_damunpapernum"); 
            String v_damunpapernm = dbox0.getString("d_damunpapernm"); 
            String v_damunnums = dbox0.getString("d_damunnums"); 

			String v_subjuserid = box.getString("p_subjuserid");
			String v_obuserid = box.getString("p_obuserid");
            String v_name = box.getString("p_name");
            String v_relation = box.getString("p_relation");
			String v_mailgubun = box.getString("p_mailgubun");
            int v_isobserver = box.getInt("p_isobserver"); 

			String v_relationnm = "";
			if (v_relation.equals("1")) {
				v_relationnm = "본인";
			} else if (v_relation.equals("2")) {
				v_relationnm = "부하";
			} else if (v_relation.equals("3")) {
				v_relationnm = "동료";
			} else if (v_relation.equals("4")) {
				v_relationnm = "상사";
			} 

			String v_content1 = "";
			if (v_mailgubun.equals("1")) {
				v_content1 = "이수한";
			} else if (v_mailgubun.equals("2")) {
				v_content1 = "이수하기 전";
			} else if (v_mailgubun.equals("3")) {
				v_content1 = "이수한 이후";
			} 
			String v_content2 = "";
			if (v_relation.equals("1")) {
				v_content2 = "본인";
			} else  {
				v_content2 = "조사대상자";
			} 
			
		     java.util.Date d_now = new java.util.Date();
			String d_year = String.valueOf(d_now.getYear()+1900);
			String d_month = String.valueOf(d_now.getMonth()+1);
			String d_day = String.valueOf(d_now.getDate());

			if(d_month.length() == 1){
			d_month = "0" + d_month; 
			}
			if (d_day.length() == 1){
			d_day = "0" + d_day; 				 
			}
			long v_now = Long.parseLong(d_year+d_month+d_day); 

		    
              
            if (v_mailgubun.equals("1") || v_mailgubun.equals("2")){
				long v_fstart = Long.parseLong(dbox0.getString("d_fdamunstart"));
				long v_fend = Long.parseLong(dbox0.getString("d_fdamunend"));
out.println(v_fstart);
out.println(v_now);
		         if (v_fstart > v_now){
		            v_msg = "다면평가 기간 이전입니다. 다면평가기간내에 오셔서 응답하여주시면 감사하겠습니다."; 
					v_msggbn = 1;
		         }
				 else if (v_fend < v_now){
		            v_msg = "다면평가 기간이 완료되었습니다."; 
					v_msggbn = 2;
		         }
				
				v_start = Long.toString(v_fstart);
				v_end = Long.toString(v_fend);

	         } else if (v_mailgubun.equals("3")){
			     long v_sstart = Long.parseLong(dbox0.getString("d_sdamunstart"));
                 long v_send = Long.parseLong(dbox0.getString("d_sdamunend"));		 

		         if (v_sstart > v_now){
		            v_msg = "다면평가 기간 이전입니다. 다면평가기간내에 오셔서 응답하여주시면 감사하겠습니다."; 
					v_msggbn = 3;
		         }
				 else if (v_send < v_now){
		            v_msg = "다면평가 기간이 완료되었습니다."; 
					v_msggbn = 4;
		         }

				 v_start = Long.toString(v_sstart);
				 v_end = Long.toString(v_send);

			 }			 
	ArrayList mlist = (ArrayList)request.getAttribute("DamunMemberData");
         DataBox mdbox = (DataBox)mlist.get(0);

%>
<HTML>
<HEAD>
<TITLE></TITLE>
<link rel="stylesheet" href="/css/style_myhome.css" type="text/css">
<script src="/script/cafe_select.js" language="JavaScript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
function whenSubmit1(){
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
  var count = 0;
  count = document.form2.p_count.value;
  for(i=0;i<document.form2.length;i++) {
    c_name  = document.form2.elements[i].name;
    c_value = document.form2.elements[i].value;
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
    if (document.form2.elements[i].type=="checkbox") {
      b_type="checkbox";
      if (document.form2.elements[i].checked==true) {
        temp = temp+c_value+':';
      }
    } else if (document.form2.elements[i].type=="radio") {
      b_type="radio";
      if (document.form2.elements[i].checked==true) {
        temp = c_value;
      }
    } else if (document.form2.elements[i].type=="text"||document.form2.elements[i].type=="textarea") {
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

  if (replycnt < count) {
    alert("응답하지 않은 다면평가이 있습니다.모든 다면평가에 응답해주시길 바랍니다.");
    return;
  }
  document.form2.p_answers.value = result;
  document.form2.submit();
}
//-->
</script>
</HEAD>

<BODY topmargin="0" leftmargin="0">
<TABLE cellpadding="0" cellspacing="0" border="0" width="640">

   	<td align="center" class=sulmun_bg>
<!----------------- 타이틀 시작 ----------------->
	<table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
		<tr> 
			<td><img src="/images/admin/multiestimate/paper_title.gif" alt="다면평가"></td>
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

		<table width="600" align="center" cellpadding="2" cellspacing="1" >
		<tr> 
          <td height=8 colspan="2"></td>
        </tr>
        <tr> 
          <td height=8 colspan="2"></td>
        </tr>        
        <tr> 
          <td height=15 align="center" colspan="2">
            <table width="600" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width=10 style=padding-left:6><img src="/images/user/myhome/bullet_03.gif" ></td>
                <td  class=guidetitle>조사 대상자 정보 ( 대상자와의 관계 : <%=v_relationnm%>)</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td colspan="2" align="center">
            <table border="0" cellpadding="0" cellspacing="0" width="600">
              <tr> 
                <td class="boardskin1_color" height="3" colspan="2"></td>
              </tr>
              <tr> 
                <td height="2" colspan="2"></td>
              </tr>
              <tr> 
                <td width="70"></td>
                <td>
                  <table width="600" border="0" cellspacing="1" cellpadding="0" class=boardskin1_out align="center">
                    <tr> 
                      <td width="100"  class=boardskin1_textb>사번</td>
                      <td width="100"  class=boardskin1_textb>성명</td>
                      <td width="100"  class=boardskin1_textb>직위</td>
                      <td width="300"  class=boardskin1_textb>소속</td>
                    </tr>
                    <tr> 
						<td  class=boardskin1_textn><%=mdbox.getString("d_userid")%></td>
						<td  class=boardskin1_textn><%=mdbox.getString("d_name")%></td>
						<td  class=boardskin1_textn><%=mdbox.getString("d_jikwinm")%></td>
						<td  class=boardskin1_textn><%=mdbox.getString("d_asgnnm")%></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
		<tr> 
          <td height=8 colspan="2"></td>
        </tr>
        <tr> 
          <td height=8 colspan="2"></td>
        </tr>        
		<tr> 
          <td height=15 align="center" colspan="2">
            <table width="600" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width=10 style=padding-left:6><img src="/images/user/myhome/bullet_03.gif" ></td>
                <td  class=guidetitle>다면평가기간 : (<%=FormatDate.getFormatDate(v_start,"yyyy-MM-dd")%> ~ <%=FormatDate.getFormatDate(v_end,"yyyy-MM-dd")%>)</td>
              </tr>
            </table>
<% 
		if(v_msggbn > 0) {	%>
			<table width="600" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td style=padding-left:6><font size=2 color="red"><%=v_msg%></font></td>
              </tr>
            </table>
<%	}	%>
          </td>
        </tr>
        <tr> 
          <td height=30 colspan="2"></td>
        </tr>
        <tr> 
          <td align="center" colspan="2">
						<table cellspacing="0" cellpadding="0" class="open_board_table_out2" align="left">
							<form name='form2' method='post' action='/servlet/controller.multiestimate.DamunSubjMailResultServlet'>
				  			<input type='hidden' name='p_process'    value='DamunUserMailResultInsert'>         
				  			<input type='hidden' name='p_grcode'    value='<%=v_grcode%>'>          
				  			<input type='hidden' name='p_subj'    value='<%=v_subj%>'>          
				  			<input type='hidden' name='p_gyear'      value='<%=v_gyear%>'>          
				  			<input type='hidden' name='p_subjseq'    value='<%=v_subjseq%>'>          
				  			<input type='hidden' name='p_damunpapernum'      value='<%=v_damunpapernum%>'>          
				  			<input type='hidden' name='p_subjuserid'      value='<%=v_subjuserid%>'>          
				  			<input type='hidden' name='p_obuserid'      value='<%=v_obuserid%>'>          
				  			<input type='hidden' name='p_mailgubun'      value='<%=v_mailgubun%>'>          
				  			<input type='hidden' name='p_isobserver'      value='<%=v_isobserver%>'>          
				  			<input type='hidden' name='p_damunnums'   value='<%=v_damunnums%>'>          
				  			<input type='hidden' name='p_answers'      value=''>                 
							<input type='hidden' name='p_relation'      value='<%=v_relation%>'>      
						<!--</form>--> 
				  		<tr> 
				    		<td> 
				      		<table cellspacing="1" cellpadding="3" class="table2">
							    <!--<form name="form3" method="post" action="/servlet/controller.multiestimate.DamunSubjResultServlet">-->
				<%   
				if(v_msggbn == 0) {
				        ArrayList    blist = (ArrayList)request.getAttribute("PaperQuestionExampleList");
				    
					for (int i=0; i < blist.size(); i++) {
						v_cnt++;
				        ArrayList list = (ArrayList)blist.get(i);  
				        DataBox dbox = (DataBox)list.get(0);  
				%>
				      <tr>
				      	<td width="20"></td> 
				        <td class="board_title_bg0"><%="["+String.valueOf(i+1)+"]"+ dbox.getString("d_damuntext")%></td>
				      </tr>
				<%      if (dbox.getString("d_damuntype").equals(DamunSubjBean.OBJECT_QUESTION)) {   // 단일선택 
				            for (int j=0; j < list.size(); j++) {
				                dbox  = (DataBox)list.get(j); 
				                if (dbox != null) { %>
				      <tr> 
				        <td width="20"></td> 
				        <td class="board_text4"> 
				        			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_damunnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
				       </tr>
				<%              }
				            }
				        } else if (dbox.getString("d_damuntype").equals(DamunSubjBean.MULTI_QUESTION)) {  // 복수선택
				            for (int j=0; j < list.size(); j++) {
				                dbox  = (DataBox)list.get(j); 
				                if (dbox != null) { %>
				       <tr>
				          <td width="20"></td> 
				        	<td class="board_text4"> 
				        			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="<%=dbox.getInt("d_damunnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
				       </tr>
				<%              } 
				            }
				        } else if (dbox.getString("d_damuntype").equals(DamunSubjBean.SUBJECT_QUESTION)) {  // 서술형  %>
				       <tr>
				          <td width="20"></td>  
				        	<td class="board_text3"> 
				        			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="<%=dbox.getInt("d_damunnum")%>" cols="83" rows="5" class="input"></textarea>
				        	</td>
				       </tr>
				<%
						} else if (dbox.getString("d_damuntype").equals(DamunSubjBean.COMPLEX_QUESTION)) {  // 복합형
				            for (int j=0; j < list.size(); j++) {
				                dbox  = (DataBox)list.get(j); 
				                if (dbox != null) { %>
				       <tr>
				          <td width="20"></td>  
				        	<td class="board_text4"> 
				        			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_damunnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
				        </tr>
				<%          }
				           } 
				%>
				        <tr>
				          <td width="20"></td>  
				        	<td class="board_text4">기타의견</td>
				        </tr>
				        <tr>
				          <td width="20"></td>  
				        	<td class="board_text3"> 
				        			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="<%=dbox.getInt("d_damunnum")%>|C" cols="83" rows="5" class="input"></textarea>
				        	</td>
				        </tr>
				<%				
								
				
				        } else if (dbox.getString("d_damuntype").equals(DamunSubjBean.FSCALE_QUESTION)) {  // 5점척도
				            for (int j=0; j < list.size(); j++) {
				                dbox  = (DataBox)list.get(j); 
				                if (dbox != null) { %>
				        <tr>
				          <td width="20"></td>  
				          <td class="board_text4"> 
				            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_damunnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
				        </tr>
				<%          
				                }
				           } 
				        } else if (dbox.getString("d_damuntype").equals(DamunSubjBean.SSCALE_QUESTION)) {  // 7점척도
				            for (int j=0; j < list.size(); j++) {
				                dbox  = (DataBox)list.get(j); 
				                if (dbox != null) { %>
				        <tr>
				          <td width="20"></td>  
				          <td class="board_text4"> 
				            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_damunnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
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
				          <td width="20"></td>  
				          <td class="board_text3"><%=dbox.getString("d_scalename")%></td>
				        </tr>
				<%
				                }	
				%>
								<tr>
								  <td width="20"></td>  
				          <td class="board_text4"> 
				            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_damunnum")%>|F" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
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
				          <td width="20"></td>  
				          <td class="board_text3"><%=dbox.getString("d_scalename")%></td>
				        </tr>
				<%
				                }	
				%>
				        <tr>
				          <td width="20"></td>  
				          <td class="board_text4"> 
				            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_damunnum")%>|S" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")-5%>. <%=dbox.getString("d_seltext")%></td>
				        </tr>
				<%          
				                }
				           } 
				        } 
					}
				}
				%>
							<input type="hidden" name="p_count" value="<%=v_cnt%>">
				      	</form>
				      </table>
				<!----------------- 다면평가조사 끝 ---------------->
						<br>
	  	    </td>               
	  	  </tr>	
	  	  <tr> 
	  	    <td>
								<!----------------- 제출 버튼 시작 ---------------->
						<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
						    <tr>                
							    <td align="right"><% if(v_msggbn == 0) {%><a href="javascript:whenSubmit()"><img src="/images/user/study/presentation_butt1.gif" alt="제출" width="54" height="20" border="0"></a><%}%></td>
						        <td align="right" width="65"><a href="javascript:self.close()"><img src="/images/user/study/close_butt.gif" alt="닫기" width="54" height="20" border="0"></a></td>
						    </tr>
						</table>
						<!----------------- 제출 버튼 끝 ----------------->
					</td>
	  	  </tr>
	  	  <tr> 
	  	  		<td><img src="/images/admin/research/Mform_bottom.gif" ></td>
	  		</tr>
	  	          <!-- 본문 끝 -->
	  	</TABLE>
		</td>
	</tr>
	<tr height="51">
		<td valign="top"></td>
	</tr>
</TABLE>
</BODY>
</HTML>