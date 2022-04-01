<%
//**********************************************************
//  1. 제      목: 분류별
//  2. 프로그램명: za_ETestGubunResult_L.jsp
//  3. 개      요: 분류별 뷰
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 8. 20
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
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

    String v_gyear		= box.getString("p_gyear"); 
    String v_etesttext	= box.getString("p_etesttext"); 
    String p_print		= box.getString("p_print"); 
  
    ArrayList blist = (ArrayList)request.getAttribute("GubunCodenm");
    Vector    vlist = (Vector)request.getAttribute("GubunResult");  
  
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_study_style1.css" rel="stylesheet" type="text/css">
<script src="/script/cafe_select.js" language="JavaScript"></script>
<link href="/css/user_style_study.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="670" height="100%">
    <tr>
        <td>
        
            <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
                <tr>
                    <td align="center" valign="top" class="body_color">


                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td>
            				    <br>  
                              <!----------------- 타이틀 시작 ----------------->
                              <table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
                                <tr> 
                                  <td><img src="/images/user/etest/result_title.gif"></td>
                                  <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
                                  <td><img src="/images/user/study/gongi_tail.gif"></td>
                                </tr>
                              </table>
                           </td>
                          </tr>
                          
                            <tr> 
                               <td align="center">
                    			<table width="620" border="0" cellpadding="0" cellspacing="0" background="/images/user/etest/test_box_bg.gif">
                                      <tr>
                                        <td><img src="/images/user/etest/test_box_top.gif" ></td>
                                      </tr>
                                      <tr>
                                        <td align="center">
            							<table width="620" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td width="87" rowspan="3"><img src="/images/user/etest/test_img.jpg"></td>
                                              <td width="73" height="33"><img src="/images/user/etest/text_name.gif"></td>
                                              <td width="170" class="tblfont_sindotit"><%=v_etesttext%></td>
                                              <td width="61"><img src="/images/user/etest/text_year.gif"></td>
                                              <td width="70" class="tblfont_gleft2"><%=v_gyear%></td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                      <tr>
                                        <td><img src="/images/user/etest/test_box_bo.gif" ></td>
                                      </tr>
                                  </table>
                               </td>
                            </tr>
                            <tr> 
                              <td height=8></td>
                            </tr>
                                  
                          <tr>
                            <td align=center >
                               <table width="620" border="0" cellspacing="1" cellpadding="0" class=boardskin1_out>

<%
	for (int i=0; i < vlist.size(); i++) {	    
	    if(i%2==0){
%>                    
                                  <!--이름-->
                                  <%if(i!=0){%>
                                  <tr> 
                                    <td class='boardskin1_textn'   colspan='2' height='25'>&nbsp;</td>
                                  </tr>      
                                  <%}%>                              
                                  <tr> 
                                    <td class='boardskin1_textb_q'  colspan='2' height='25'>&nbsp; <b><%=vlist.get(i)%></b></td>
                                  </tr>
                                
<%
        }else{
            
            ArrayList jumsu = (ArrayList)vlist.get(i);
            for(int j=0; j<5; j++) {
%>                                  
                                  <tr> 
                                    <td  class='skin_tb1'><%=blist.get(j)%></td>
                                    <td  width="397"  align="left" class='open_dictionary_text1'>
                                        &nbsp;<img src="/images/admin/research/statistics_line.gif" height="14" width="<%=jumsu.get(j)%>"> <%=jumsu.get(j)%> 점
                                     </td>
                                  </tr>
<%
            }
        }
    }
    
    if(vlist.size()<1){
%>
                                  <tr> 
                                    <td align="center" class='boardskin1_textn'>
                                        응시자가 없습니다.
                                     </td>
                                  </tr>

<%  }%>
                    	
                                </table>
                                        
                            
                            </td>
                          </tr>
                          <tr>
                            <td align="right"> 
                            <br>
                                <table width='620' border="0" cellspacing="0" cellpadding="0" align=center>
                                    <td align=right>
                                    <%if(p_print.equals("1")){%><a href="javascript:window.print();">인쇄</a><%}%>
                                    <a href="javascript:window.close();"><img src="/images/user/study/close_butt.gif" border="0"></a>&nbsp;&nbsp;</td>
                                  </tr>
                                </table>
                             </td>      
                          </tr>
                          <tr>
                            <td>&nbsp;</td>
                          </tr>
                        </table>
                        
                        
                    </td>
                </tr>
            </table>

        </td>
    </tr>
</table>
</body>
</html>

