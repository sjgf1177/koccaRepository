<%
/**
 * file name : za_ETestPreview.jsp
 * date      : 2003/09/12
 * programmer:
 * function  : 평가문제지 미리보기
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.etest.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    DataBox    dbox0 = (DataBox)request.getAttribute("ETestPaperData");

	String v_etestsubj     = dbox0.getString("d_etestsubj"); 
	String v_etestcode     = dbox0.getString("d_etestcode"); 
    String v_gyear     = dbox0.getString("d_year"); 
    int v_etestcnt = dbox0.getInt("d_etestcnt"); 
    int v_etestpoint = dbox0.getInt("d_etestpoint"); 
    int    v_etesttime  =  dbox0.getInt("d_etesttime"); 
    String v_etestext = dbox0.getString("d_etesttext"); 
//    int    v_etestnum  =  p_etestnum;
 
	
    String v_urldir = conf.getProperty("url.upload")+"etest/"; //jkh 0224 수정
//    String v_urldir = "/upload/etest/";
//  String v_urldir = "/upload/exam/";
%>
<html>
<head>
<title>ETest 미리보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<!--<link rel="stylesheet" href="/css/user_style_research.css" type="text/css">-->
<link href="../../../css/user_style_study.css" rel="stylesheet" type="text/css">

         <SCRIPT LANGUAGE='JavaScript'>                                           
          <!--                                                                     
                                                       
             function init() {                                            
                    this.moveTo(0,0);                                   
                    this.resizeTo(screen.availWidth,screen.availHeight);                                   
             }                                            
                                                       
             var limit =  <%=v_etesttime%>+':01'                                                   
                                                                                             
             if (document.images){                                            
                var parselimit=limit.split(':')                                     
                parselimit=parselimit[0]*60+parselimit[1]*1                                    
             }                                        
             function begintimer(){                                        
                 if (!document.images)                                    
                     return                                
                 if (parselimit==1){                                    
                    // whenAutoSubmit();                                
                 }else{                                     
                     parselimit-=1                               
                     curmin=Math.floor(parselimit/60)                                
                     cursec=parselimit%60                                
                     if (curmin!=0)                                
                          curtime=curmin+'분 '+cursec+'초 '                           
                     else                                
                          curtime='0 : '+cursec                           
                     numberCountdown.innerText=curtime                                 
                     setTimeout('begintimer()',1000)                                
                 }                                     
             }                                        
         -->                                                          
         </SCRIPT>              
</head>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onLoad='init();begintimer();'>
<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="670" height="100%">
    <tr>
        <td>
            <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
                <tr>
                    <td align="center" valign="top" class="body_color">

<!----------------- 외곽   ---------------->                    
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<!----------------- 타이틀   ---------------->
  <tr>
    <td >  &nbsp;  &nbsp; &nbsp; <img src="../../../images/user/etest/eTest_pop01.gif" ></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
<!----------------- 타이틀 끝  ---------------->
  <tr>
    <td align="center">
      <table border="0" cellpadding="0" cellspacing="0" width="630"  align="center">
        <tr> 
          <td valign="top" align="center">
		  <table width="630" border="0" cellpadding="0" cellspacing="0" background="../../../images/user/etest/test_box_bg.gif">
              <tr>
                <td><img src="../../../images/user/etest/test_box_top.gif" ></td>
              </tr>
              <tr>
                <td align="center">
				<table width="600" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="87" rowspan="5"><img src="../../../images/user/etest/test_img.jpg"></td>
                      <td width="73" height="33"><img src="../../../images/user/etest/text_name.gif"></td>
                      <td width="226" class="tblfont_sindotit"><%=v_etestext%></td>
                      <td width="61"><img src="../../../images/user/etest/text_year.gif"></td>
                      <td width="153" class="tblfont_gleft2"><%=v_gyear%></td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="4" background="../../../images/user/etest/stu_box_vline2.gif"></td>
                    </tr>
                    <tr> 
                      <td colspan="5" class="tblfont_gleft2">시험시간 -  <b><%=v_etesttime%>분 00초</b></td>
                    </tr>
                    <tr> 
                      <td colspan="5" class="tblfont_gleft2">남은시간 - <b><font color="red"><span id="numberCountdown"></span></font></b></td>
                    </tr>      
                    <tr> 
                      <td colspan="5" class="tblfont_gleft2">문제수 - <b><%=v_etestcnt%></b></td>
                    </tr>                                                                                          
                  </table>
                  </td>
              </tr>
              <tr>
                <td><img src="../../../images/user/etest/test_box_bo.gif" ></td>
              </tr>
          </table>
          </td>
        </tr>
        <tr> 
          <td height=8>
          <table width="100%" border="0" cellspacing="5" cellpadding="10">
              <tr> 
                <td class="tblfont_gleft2"><b><> 시험시간이 경과하면 자동으로 시험지가 제출되오니 유의하시기 바랍니다. <br> &nbsp; &nbsp;  주관식 문제의 경우 답을 적으실 때 띄어쓰기를 하지 마십시오. </b></td>
              </tr>
            </table>
          </td>
        </tr>        
        <tr> 
          <td>     
          
            <!----------------- 학습평가 시작 ---------------->
            <table width="630" border="0" cellspacing="1" cellpadding="0" class=boardskin1_out>   
              <tr> 
                <td> 
                  <table cellspacing="1" cellpadding="3" class="table2">
            <% 
                    ArrayList    blist = (ArrayList)request.getAttribute("PaperQuestionExampleList");
                
            	for (int i=0; i < blist.size(); i++) {
                    ArrayList list = (ArrayList)blist.get(i);  
                    DataBox dbox = (DataBox)list.get(0);  
            %>
                    <tr> 
                      <td class="board_title_bg0"><%="["+String.valueOf(i+1)+"]" + dbox.getString("d_etesttext")%></td>
                    </tr>
            
            
            <!-- 문제관련 이미지, 음성, 동영상 파일 시작 -->
            <%      if (!dbox.getString("d_saveimage").equals("")) { %> 
                    <tr> 
                      <td bgcolor="#FFFFFF"> 
                        <table cellspacing="0" cellpadding="0" class="img_table_out">
                          <tr> 
                            <td> 
                              <table cellspacing="2" cellpadding="2">
                                <tr> 
                                  <td class="img_table" height="26"><img src='<%=v_urldir+dbox.getString("d_saveimage")%>'></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
            <%      }  %>  
            
            <%      if (!dbox.getString("d_saveaudio").equals("")) { %> 
                    <tr> 
                      <td class="board_text4"> <img src="/images/user/study/voice_icon1.gif" width="20" height="17" border="0"><a href="<%=v_urldir+dbox.getString("d_saveaudio")%>"><%=dbox.getString("d_realaudio")%></a></td>
                    </tr>
            <%      }  %>  
            
            <%      if (!dbox.getString("d_savemovie").equals("")) { %> 
                    <tr> 
                      <td class="board_text3"> <img src="/images/user/study/meida_icon1.gif" width="20" height="17" border="0"><a href="<%=v_urldir+dbox.getString("d_savemovie")%>"><%=dbox.getString("d_realmovie")%></a></td>
                    </tr>
            <%      }  %>        
            
            <!-- 문제관련 이미지, 음성, 동영상 파일 끝 -->
            
            <%      if (dbox.getString("d_etesttype").equals(ETestBean.OBJECT_QUESTION)) {   // 단일선택 
                        for (int j=0; j < list.size(); j++) {
                            dbox  = (DataBox)list.get(j); 
                            if (dbox != null) { %>
                    <tr> 
                      <td class="board_text4"> 
                        <input type="radio" name="<%=dbox.getInt("d_etestnum")%>" value="<%=dbox.getInt("d_selnum")%>" class='inputradio'><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
                    </tr>
            <%              } 
                        }
                    } else if (dbox.getString("d_etesttype").equals(ETestBean.SUBJECT_QUESTION)) {  // 주관식  %>
                    <tr> 
                      <td class="board_text3"> 
                        <textarea name="<%=dbox.getInt("d_sulnum")%>" cols="80" rows="5" class="input"></textarea>
                      </td>
                    </tr>
            <%             
                        
                    } else if (dbox.getString("d_etesttype").equals(ETestBean.MULTI_QUESTION)) {  // 다답식
                        for (int j=0; j < list.size(); j++) {
                            dbox  = (DataBox)list.get(j); 
                            if (dbox != null) { %>
                    <tr> 
                      <td class="board_text4"> 
                        <input type="checkbox" name="<%=dbox.getInt("d_sulnum")%>" value="<%=dbox.getInt("d_selnum")%>" class='inputradio'><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
                    </tr>
            
            <%         }
            		}
            	 }
            	} %>
                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 학습평가 끝 ---------------->
        <br>
        <!----------------- 제출 버튼 시작 ---------------->
        <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
        	<tr>                
        		<td align='right'><img src='/images/admin/button/btn_presentation.gif'  border='0'></td>
                <td align='right' width='65'><a href='javascript:self.close()'><img src='/images/admin/button/btn_close.gif'  border='0'></a></td>
        	</tr>
        </table>
        <!----------------- 제출 버튼 끝 ----------------->
        <br>

                    </td>
                </tr>
            </table>

        </td>
    </tr>
</table>
<!----------------- 외곽   ---------------->  
                    </td>
                </tr>
            </table>

        </td>
    </tr>
</table>

<br>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
