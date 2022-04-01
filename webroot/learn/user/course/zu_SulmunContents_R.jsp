<%
//**********************************************************
//  1. 제      목: SUBJECT PREVIEW PAGE
//  2. 프로그램명: zu_SubjectPreview.jsp
//  3. 개      요: 과정안내
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 01.12
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process      = box.getString("p_process");
    String  v_rprocess     = box.getString("p_rprocess");
    String  v_subj         = box.getString("p_subj");
    String  v_subjnm       = box.getString("p_subjnm");
    String  v_isonoff      = box.getString("p_isonoff");
    String  v_upperclassnm = box.getString("p_upperclassnm");
    String  v_upperclass   = box.getString("p_upperclass");
    String  v_select       = box.getString("p_select");
    String  v_actionurl    = box.getString("p_actionurl");
    String  v_lsearchsubject = box.getString("p_lsearchsubject");

    
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<link rel="stylesheet" href="/css/style_course.css" type="text/css">
<script src="/script/cafe_select.js" language="JavaScript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
//-->
</script>
</HEAD>

<BODY leftmargin="0" topmargin="0">

<TABLE cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
	<tr class="tableheight1" valign="top">
		<td background="/images/common/bg_course.gif">
			<TABLE cellpadding="0" cellspacing="0" border="0" width="950">
				<tr class="tableheight2">
					<td>
						<!-- TOP 시작 -->
						<%@include file="/incmenu/top.jsp"%>
						<!-- TOP 끝 -->
					</td>
				</tr>
				<tr class="tableheight3">
					<td style="BORDER-LEFT:#FFFFFF 10px solid">
						<!-- 메인 플래시 시작 -->
						<%@include file="/incmenu/course.jsp"%>
						<!-- 메인 플래시 끝 -->
					</td>
				</tr>
				<tr class="tableheight4">
					<td style="BORDER-LEFT:#FFFFFF 10px solid">
						<!-- 메뉴바 시작 -->
						<%@include file="/incmenu/menu.jsp"%>
						<!-- 메뉴바 끝 -->
					</td>
				</tr>
			</TABLE>
		</td>
	</tr>
	<tr valign="top">
		<td>
			<TABLE cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
				<tr valign="top">
					<td width="200" background="/images/common/left_bg.gif">
						<!-- 왼쪽 메뉴 시작 -->
						<%@include file="/incmenu/left_course.jsp"%>
						<!-- 왼쪽 메뉴 끝 -->
					</td>
					<td>
						<!-- 검색 시작 -->
						<!--%@include file="/incmenu/search_myhome.jsp"%-->
						<jsp:include page="/incmenu/search_main.jsp" flush="true">
							<jsp:param name="GUBUN" value="EDUCATION"/>
						</jsp:include>
						<!-- 검색 끝 -->
						<!-- 본문 시작 -->
                      <TABLE cellpadding="0" cellspacing="0" border="0" width="735">
                      
                        <tr> 
                          <td class="bodypd"> 
                            <!-- 현재 위치 시작 -->
                               <TABLE cellpadding="0" cellspacing="0" border="0" width="100%">
                                 <tr>
                                    <td align="right" class="location"><img src="/images/common/bullet_location.gif"><a href="http://www.hkhrd.com"> HOME</a> > <a href="http://www.hkhrd.com/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">수강신청/안내</a> > 설문결과보기 </td>
                                 </tr>
                                 <tr height="3">
                                    <td></td>
                                 </tr>
                               </table>
                            <!-- 현재 위치 끝 -->
                            <TABLE cellpadding="0" cellspacing="0" border="0" width="735">
                            <form name="form1" method="post" enctype = "multipart/form-data">
                              <input type='hidden' name='p_process'>
                              
                              <input type='hidden' name='p_isonoff' value='<%=v_isonoff%>'>
                              <input type='hidden' name='p_subj' value='<%=v_subj%>'>
                              <input type='hidden' name='p_subjnm' value='<%=v_subjnm%>'>
                              <input type='hidden' name='p_select' value='<%=v_select%>'>
                              <input type='hidden' name='p_upperclass' value='<%=v_upperclass%>'>
                              <input type='hidden' name='p_upperclassnm' value='<%=v_upperclassnm%>'>
                              <input type='hidden' name='p_lsearchsubject' value='<%=v_lsearchsubject%>'>
                              <input type='hidden' name='p_actionurl' value='<%=v_actionurl%>'>
                              <input type='hidden' name='p_rprocess' value='<%=v_rprocess%>'>
                              
                              <tr> 
                                <td> <TABLE cellpadding="0" cellspacing="0" border="0">
                                    <tr> 
                                      <td><IMG src="/images/common/title_bullet.gif" border="0"></td>
                                      <td><img src="/images/user/course/title_course_07.gif" alt="설문결과보기"></td>
                                    </tr>
                                  </TABLE></td>
                              </tr>
                              <tr height="1"> 
                                <td bgcolor="E5E5E5"></td>
                              </tr>
                              <tr height="20"> 
                                <td></td>
                              </tr>
                              <tr> 
                                <td> 
                                  <TABLE cellpadding="0" cellspacing="0" border="0" width="735">
                                    <tr> 
                                      <td><table width="735" border="0" cellspacing="0" cellpadding="0">
                                          <tr> 
                                            <td width=10 style=padding-left:6px><img src="/images/user/course/bullet_03.gif" ></td>
                                            <td  class=guidetitle>콘텐츠평가</td>
                                          </tr>
                                        </table></td>
                                    </tr>
                      			  <tr> 
                                      <td class=dirtext>문항당 5점만점기준입니다.</td>
                                    </tr>
                                    <tr> 
                                      <td>
									    <table border="0" cellpadding="0" cellspacing="0" width="735">
                                          <tr> 
                                            <td class="boardskin1_color" height="3"></td>
                                          </tr>
                                          <tr> 
                                            <td height="2"></td>
                                          </tr>
                                          <tr> 
                                            <td><table width="735" border="0" cellspacing="1" cellpadding="2"  class="boardskin1_out">
                                                <tr> 
                                                  <td colspan="2" class=boardskin1_textb>문항</td>
                                                  <td width="75" class=boardskin1_textb>평점</td>
                                                </tr>
<%  
    ArrayList    list = (ArrayList)request.getAttribute("SulmunResultList");
    SulmunQuestionExampleData data    = null;
    SulmunExampleData         subdata = null;
    double v_total = 0;
	int v_row = 0;
	int v_flag = 0;

    for (int i=0; i < list.size(); i++) {
        data = (SulmunQuestionExampleData)list.get(i);

	          double d = 0; 
			  int person = 0;
			  double v_point = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { 

					d +=  (subdata.getReplycnt()) * subdata.getSelpoint();
					person += subdata.getReplycnt();
				}
            }	
               
		v_point = d / person;	  
               
		v_total += v_point;

		if(data.getDistcode().equals("11")){
		     v_row = 5;
		} else if(data.getDistcode().equals("12")){
		     v_row = 5;
		} else if(data.getDistcode().equals("13")){
		     v_row = 5;
		} else if(data.getDistcode().equals("14")){
		     v_row = 5;
		} else if(data.getDistcode().equals("15")){
		     v_row = 5;
		} else if(data.getDistcode().equals("16")){
		     v_row = 10;
			 v_flag++;
		} else if(data.getDistcode().equals("17")){
		     v_row = 5;
		}

        if( (i%5) == 0 && (v_flag) != 6 ){
%>
												<tr> 
                                                  <td width="154" rowspan="<%=v_row%>" class=boardskin1_textn_b><%=data.getDistcodenm()%></td>
                                                  <td width="490" class=boardskin1_textn_l><%=data.getSultext()%><br></td>
                                                  <td class=boardskin1_textn><%if(v_point >=0){%><%=v_point%>점<%}else{%>0.0점<%}%></td>
                                                </tr>
<%
	    }else{
%>
												<tr> 
                                                  <td width="490" class=boardskin1_textn_l><%=data.getSultext()%><br></td>
                                                  <td class=boardskin1_textn><%if(v_point >=0){%><%=v_point%>점<%}else{%>0.0점<%}%></td>
                                                </tr>
<%
        }
    }	
    
	double v_average = v_total / list.size();

	DecimalFormat  df = new DecimalFormat("0.0");
%>

                                              </table>
                                            </td>
                                          </tr>
                                        </table></td>
                                    </tr>
                                    <tr> 
                                      <td height=4></td>
                                    </tr>
                                    <tr> 
                                      <td><table width="735" border="0" cellspacing="1" cellpadding="2" class="boardskin1_out">
                                          <tr> 
                                            <td width="153" class=skin_tb1> 총점</td>
                                            <td width="218" class=boardskin1_textn><%if(v_total >=0){%><%=v_total%>점<%}else{%>0.0점<%}%></td>
                                            <td width="119" class=skin_tb1>평균점수</td>
                                            <td width="224" class=boardskin1_textn><%if(v_average >=0){%><%=df.format(v_average)%>점<%}else{%>0.0점<%}%></td>
                                          </tr>
                                        </table></td>
                                    </tr>
                                    <tr> 
                                      <td class="boardskin1_bottom"></td>
                                    </tr>
                                    <tr> 
                                      <td>&nbsp;</td>
                                    </tr>
                                    <tr> 
                                      <td align="center"><a href="javascript:history.back()"><img src="/images/user/course/btn_list.gif"  alt="목록보기" border="0"></a></td>
                                    </tr>
                                    <tr> 
                                      <td>&nbsp;</td>
                                    </tr>
                                  </TABLE>
                                </td>
                              </tr>
                            </table></td>
                        </tr>
                        </form>
                      </TABLE>
						<!-- 본문 끝 -->
					</td>
				</tr>
			</TABLE>
		</td>
	</tr>
	<tr height="51">
		<td valign="top">
			<!-- FOOTER 시작 -->
			<jsp:include page="/incmenu/footer.jsp" flush="true">
				<jsp:param name="sUrl" value="<%=request.getServletPath().toString()%>"/>
			</jsp:include>
			<!-- FOOTER 끝 -->
		</td>
	</tr>
</TABLE>
</BODY>
</HTML>
</BODY>
</HTML>