<%
/**
 * file name : zu_SulmunMailPaper_L.jsp
 * date      : 2003/09/12
 * programmer:
 * function  : 설문 응시
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    int v_cnt = 0;
	String v_msg = "";
	int v_msggbn = 0;
	String v_sulstart = "";
	String v_sulend = "";
	
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

    String v_userid = box.getString("p_userid");
			
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

			long v_fstart = Long.parseLong(dbox0.getString("d_sulstart"));
			long v_fend = Long.parseLong(dbox0.getString("d_sulend"));

			 if (v_fstart > v_now){
				v_msg = "설문기간 이전입니다. 설문기간내에 오셔서 응답하여주시면 감사하겠습니다."; 
				v_msggbn = 1;
			 }
			 else if (v_fend < v_now){
				v_msg = "설문기간이 완료되었습니다."; 
				v_msggbn = 2;
			 }
			
			v_sulstart = Long.toString(v_fstart);
			v_sulend = Long.toString(v_fend);

//	ArrayList mlist = (ArrayList)request.getAttribute("DamunMemberData");
//         DataBox mdbox = (DataBox)mlist.get(0);

%>
<HTML>
<HEAD>
<TITLE>설문 응시</TITLE>
<link rel="stylesheet" href="/css/mail_style.css" type="text/css">
<script src="/script/cafe_select.js" language="JavaScript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function onload() {
  window.self.close();
}
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

    for(i=0;i<document.form3.length;i++) {
		c_name  = document.form3.elements[i].name;
		c_value = document.form3.elements[i].value;
		if (i == 0) {
		  b_name=c_name;
		} else {
		  if (c_name != b_name && eval(c_name.indexOf("|")) == -1) {
			answercnt++;
			//alert("temp" + b_type + temp.length);
			if (b_type=="text" && temp.length > 1) {
			  replycnt++;//alert("replycnt1" + replycnt);
			}
			else if(b_type!="text" && temp.length > 0) {
				replycnt++;//alert("replycnt2" + replycnt);
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
			  if (eval(document.form3.elements[i+1].name.indexOf("|")) > 0) {
				  temp  = "";
				  textarr = document.form3.elements[i+1].value.split(",");
				  for(var j=0; j<textarr.length; j++) {
					temp = temp + textarr[j];
				  }
				  temp = c_value+':'+temp+" ";//alert("temp1" + temp);
			  }
			  else {
				  temp = c_value;//alert("temp2" + temp);
			  }
		  }
		} else if (document.form3.elements[i].type=="text"||document.form3.elements[i].type=="textarea") {
			if(eval(document.form3.elements[i].name.indexOf("|")) == -1) {
				b_type="text";
				temp  = "";
				textarr = c_value.split(",");
				for(var j=0; j<textarr.length; j++) {
					temp = temp + textarr[j];
				}
				temp = temp + " ";
			}
		}
  } 
  //alert("temp2" + b_type + temp.length);
	if (b_type=="text" && temp.length > 1) {
	  replycnt++;//alert("replycnt1" + replycnt);
	}
	else if(b_type!="text" && temp.length > 0) {
		replycnt++;//alert("replycnt2" + replycnt);
	}
  answercnt++;
  if (answercnt==1) {
    result = temp;    
  } else {
    result = result + ","+ temp + ",";
  }  
 
  //alert("result" + result);
  //alert("replycnt" + replycnt);
  //alert("answercnt" + answercnt);
  if (replycnt < answercnt) {
    alert("응답하지 않은 문제가 있습니다.모든 문제에 응답해주시길 바랍니다.");
    return;
  }
  document.form2.p_answers.value = result;
  document.form2.p_process.value = "SulmunUserResultInsert";
  document.form2.submit();
}
//-->
</script>
</HEAD>

<BODY topmargin="0" leftmargin="0" <%=v_onload%>>
<form name="form2" method="post" action="/servlet/controller.research.SulmunTargetMailResultServlet">
    <input type="hidden" name="p_grcode"    value="<%=v_grcode%>">
    <input type="hidden" name="p_subj"    value="<%=v_subj%>">
    <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
    <input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
    <input type="hidden" name="p_sulpapernum"      value="<%=v_sulpapernum%>">
    <input type="hidden" name="p_userid"      value="<%=v_userid%>">
    <input type="hidden" name="p_sulnums"      value="<%=v_sulnums%>">
    <input type="hidden" name="p_answers"      value="">
    <input type="hidden" name="p_process"      value="">
</form>
<TABLE cellpadding="0" cellspacing="0" border="0" width="640">
	<tr> 
    	<td><img src="/images/admin/research/Mform_top.gif" ></td>
  </tr>
	<tr> 
  	<td class=sulmun_bg height=8>&nbsp;</td>
  </tr>
  <tr> 
   	<td align="center" class=sulmun_bg>
     	<table width="600" border="0" cellspacing="1" cellpadding="0" background-color="DCDCDB">
       	<tr> 
       		<td width=10 style=padding-left:6><img src="/images/user/myhome/bullet_03.gif" ></td>
       		<td  class=guidetitle> &nbsp;설문지명   :   <%=v_sulpapernm%></td>
       	</tr>
     	</table>
		<table width="600" align="center" cellpadding="2" cellspacing="1" >
		  <tr>
			<td width="20"></td>
			<td style=padding-left:6>
			<br>
		<!--	본 설문은 평상시 <%//=v_content2%>의 행동양식을 알아보기 위한 것입니다.<br><br>
			솔직하게 답변해 주시기 바라며 <%//=v_content2%>의 업무수행시 행동을 떠올리면서 평소의 행동을<br> 가장 잘 나타내고 있는 것에 체크해 주시기 바랍니다.<br>
			본 설문조사는 인사상 어떠한 영향도 미치지 않으며, 교육목적으로만 활용될 예정입니다. 감사합니다.<br>-->
			</td>
		</tr>      
        <tr> 
          <td align="center" colspan="2">
        <!--    <table width="600" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width=10 style=padding-left:6><img src="/images/user/myhome/bullet_03.gif" ></td>
                <td  class=guidetitle>조사 대상자 정보 ( 대상자와의 관계 : <%//=v_relationnm%>)</td>
              </tr>
            </table>-->
          </td>
        </tr>       
		<tr> 
          <td height=15 align="center" colspan="2">
            <table width="600" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width=10 style=padding-left:6><img src="/images/user/myhome/bullet_03.gif" ></td>
                <td  class=guidetitle> &nbsp;설문기간 : (<%=FormatDate.getFormatDate(v_sulstart,"yyyy-MM-dd")%> ~ <%=FormatDate.getFormatDate(v_sulend,"yyyy-MM-dd")%>)</td>
              </tr>
            </table><br>
<% 
		if(v_msggbn > 0) {	%>
			<table width="600" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td style=padding-left:6><font size=2 color="red">&nbsp;&nbsp;<%=v_msg%></font></td>
              </tr>
            </table>
<%	}	%>
          </td>
        </tr>
        <tr> 
          <td align="center" colspan="2">
						<table cellspacing="0" cellpadding="0" class="open_board_table_out2" align="left">
							<form name='form3' method='post' action='/servlet/controller.research.SulmunTargetMailResultServlet'>
				  		<tr> 
				    		<td> 
				      		<table width="600" align="center" cellpadding="2" cellspacing="1" >
				<%   
				if(v_msggbn == 0) {
				        ArrayList    blist = (ArrayList)request.getAttribute("PaperQuestionExampleList");
				    
					for (int i=0; i < blist.size(); i++) {
						v_cnt++;
				        ArrayList list = (ArrayList)blist.get(i);  
				        DataBox dbox = (DataBox)list.get(0);  
				%>
				      <tr>
				        <td width="646" class="sulmun_num"><%="["+String.valueOf(i+1)+"]"+ dbox.getString("d_sultext")%></td>
				      </tr>
				<%      if (dbox.getString("d_sultype").equals(SulmunTargetBean.OBJECT_QUESTION)) {   // 단일선택 
				            for (int j=0; j < list.size(); j++) {
				                dbox  = (DataBox)list.get(j); 
				                if (dbox != null) { %>
				      <tr> 
				        <td class="board_text4"> 
				        			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
				       </tr>
				<%              }
				            }
				        } else if (dbox.getString("d_sultype").equals(SulmunTargetBean.MULTI_QUESTION)) {  // 복수선택
				            for (int j=0; j < list.size(); j++) {
				                dbox  = (DataBox)list.get(j); 
				                if (dbox != null) { %>
				       <tr>
				        	<td class="board_text4"> 
				        			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
				       </tr>
				<%              } 
				            }
				        } else if (dbox.getString("d_sultype").equals(SulmunTargetBean.SUBJECT_QUESTION)) {  // 서술형  %>
				       <tr>
				        	<td> 
				        			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="<%=dbox.getInt("d_sulnum")%>" rows="5"  cols="78" class="input" maxlength="200"></textarea>
				        	</td>
				       </tr>
				<%
						} else if (dbox.getString("d_sultype").equals(SulmunTargetBean.COMPLEX_QUESTION)) {  // 복합형
				            for (int j=0; j < list.size(); j++) {
				                dbox  = (DataBox)list.get(j); 
				                if (dbox != null) { %>
				       <tr>
				        	<td class="board_text4"> 
				        			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
				        </tr>
				<%          }
				           } 
				%>
				        <tr>
				        	<td class="board_text4">기타의견</td>
				        </tr>
				        <tr>
				        	<td class="board_text3"> 
				        			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="<%=dbox.getInt("d_sulnum")%>|C" cols="83" rows="5" class="input"></textarea>
				        	</td>
				        </tr>
				<%				
								
				
				        } else if (dbox.getString("d_sultype").equals(SulmunTargetBean.FSCALE_QUESTION)) {  // 5점척도
				            for (int j=0; j < list.size(); j++) {
				                dbox  = (DataBox)list.get(j); 
				                if (dbox != null) { %>
				        <tr>
				          <td class="board_text4"> 
				            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
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
				            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>"><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
				        </tr>
				<%          }
				           } 
				        } 
					}
				}
				%>
						<!--	<input type="hidden" name="p_count" value="<%=v_cnt%>">-->
				      	</form>
				      </table>
				<!----------------- 설문조사 끝 ---------------->
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
<%@ include file = "/learn/library/getJspName.jsp" %>
</BODY>
</HTML>