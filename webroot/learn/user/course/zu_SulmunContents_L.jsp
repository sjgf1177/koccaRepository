<%@ page language="java" contentType="text/html; charset=euc-kr" %>
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

    String  v_searchtext = box.getString("s_searchtext");
	String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");

    int v_row= Integer.parseInt(conf.getProperty("page.bulletin.row"));

    int v_seq = 0;
    int v_levels = 0;
    int v_cnt = 0;
    int i     = 0;

    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;

    int v_pageno    = box.getInt("p_pageno");
	if (v_pageno == 0) v_pageno = 1;
    
    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");

    String v_class1     = "";
    String v_class2     = "";
    
    ArrayList list = (ArrayList)request.getAttribute("SulmunResultList");
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<link rel="stylesheet" href="/css/style_course.css" type="text/css">
<script src="/script/cafe_select.js" language="JavaScript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function changeTabpage(p_gubun) {

    if (p_gubun == "SUBJ") {
      document.form.action = "/servlet/controller.research.SulmunSubjResultServlet";
	
	} 
	  document.form.p_process.value = "SulmunEachResultPage";
	  document.form.p_pageno.value = '1';
	  document.form.p_row.value = '10';
      document.form.submit();     
}

function whenSelection(p_action) {

	 document.form.action = "/servlet/controller.research.SulmunContentsResultServlet";
     document.form.p_process.value = 'SulmunEachResultPage';
	 document.form.submit();		
}

function go(index) {
         document.form.p_pageno.value = index;
	 document.form.action = "/servlet/controller.research.SulmunContentsResultServlet";
     document.form.p_process.value = 'SulmunEachResultPage';
         document.form.submit();
}
function goPage(pageNum) {
         document.form.p_pageno.value = pageNum;
	 document.form.action = "/servlet/controller.research.SulmunContentsResultServlet";
     document.form.p_process.value = 'SulmunEachResultPage';
         document.form.submit();
}
function select(p_subj) {
  document.form.target = "_self";
  document.form.p_process.value = "ContentResearch";
  document.form.p_subj.value = p_subj;
  document.form.action = "/servlet/controller.research.SulmunContentsResultServlet";
  document.form.submit();
}
function press_enter(e) { 
	if (e.keyCode =='13'){  whenSelection('go');  }
}
//-->
</script>

</HEAD>

<BODY topmargin="0" leftmargin="0">
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
										<tr>
											<td>
												<TABLE cellpadding="0" cellspacing="0" border="0">
													<tr>
														<td><IMG src="/images/common/title_bullet.gif" border="0"></td>
														<td><IMG src="/images/user/course/title_course_07.gif" alt="설문결과보기" border="0"></td>
													</tr>
												</TABLE>
											</td>
										</tr>
										<tr height="1">
											<td bgcolor="E5E5E5"></td>
										</tr>
										<tr height="12">
											<td></td>
										</tr>
 <form name="form" method="post" action="/servlet/controller.research.SulmunSubjResultServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"    value="">
        <input type="hidden" name="p_gyear"  value="">
        <input type="hidden" name="p_subjseq"  value="">
        <input type = "hidden" name = "p_pageno"    value = "<%=v_pageno%>">
        <input type = "hidden" name = "p_row"    value = "<%=v_row%>">

										<tr height="35">
											<td bgcolor="F7F3EF" style="padding-left:5">
												<TABLE cellpadding="0" cellspacing="0" border="0">
														<tr>
														<td><A HREF="javascript:changeTabpage('SUBJ')"><IMG src="/images/user/course/tab_sulmun01.gif" alt="과정설문" border="0" name="img32" onMouseOver="document.all.img32.src='/images/user/course/tab_sulmun01_on.gif'" onMouseOut="document.all.img32.src='/images/user/course/tab_sulmun01.gif'"></A></td>
														<td><IMG src="/images/user/myhome/myhome_img07.gif" border="0"></td>
														<td><IMG src="/images/user/course/tab_sulmun02_on.gif" alt="콘텐츠평가" border="0"></td>
														</tr>
												</TABLE>
											</td>
										</tr>
										<tr height="11">
											<td></td>
										</tr>
										<tr>
                      <td align="left"> <TABLE  border="0" cellpadding="0" cellspacing="0">
                          <tr>
						    <td width=12></td>
				<td  width=60><%= SelectSubjBean.getUpperClass(box, true, true, false)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  --></td>
                <td width=12></td>
                <td  width=60><%= SelectSubjBean.getMiddleClass(box, true, true, false)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  --></td>
						  </tr>
                        </TABLE></td>
										</tr>
										<tr>
                      <td align="right"> <TABLE  border="0" cellpadding="0" cellspacing="0">
                          <tr>
						    <td class=boardtitle>과정명검색 : </td>
							<td style="padding-left:6"><input name="s_searchtext" type="text" class="input" style="width:188" value="<%=v_searchtext%>" onkeypress="press_enter(event)"></td>
                            <td style="padding-left:6"><A HREF="javascript:whenSelection('go')"><IMG src="/images/common/bt_y_search2.gif" border="0"></A></td>
						  </tr>
                        </TABLE></td>
										</tr>

										<tr height="4">
											<td></td>
										</tr>
										<tr>
											<td>
												<!-- 게시판 시작 -->
												<TABLE cellpadding="0" cellspacing="0" border="0" width="735">
													<tr align="center">
														<td width="3"><IMG src="/images/board/board_course_left.gif" border="0"></td>
														<td class="boardbgimg" width="42"><span class=boardtitle>번호</span></td>
														<td class="boardbgimg" width="1"></td>
                            							<td class="boardbgimg" width="245" ><span class=boardtitle>과정명</span></td>
														<td class="boardbgimg" width="1"></td>
														<td class="boardbgimg" width="79"><span class=boardtitle>참여인원</span></td>
														<td class="boardbgimg" width="1"></td>
														<td class="boardbgimg" width="89" ><span class=boardtitle>학습자추천</span></td>
														<td class="boardbgimg" width="1"></td>
														<td class="boardbgimg" width="69" ><span class=boardtitle>평점</span></td>
														<td class="boardbgimg" width="1"></td>
														<td class="boardbgimg" width="89" ><span class=boardtitle>최초진행연도</span></td>
														<td width="3"><IMG src="/images/board/board_course_right.gif" border="0"></td>
													</tr>
<%
		for(i = 0; i < list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);
			String v_subj    = dbox.getString("d_subj");
			String v_sulpapernm    = dbox.getString("d_sulpapernm");
			String v_subjnm      = dbox.getString("d_subjnm");
			String v_replycount      = dbox.getString("d_replycount");
			String v_indate      = dbox.getString("d_indate");
			String v_average      = dbox.getString("d_average");
			double s_average = Double.parseDouble(v_average);
			v_dispnum   = dbox.getInt("d_dispnum");
			v_totalpage = dbox.getInt("d_totalpage");
			v_rowcount  = dbox.getInt("d_rowcount");


		// 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
		if (!v_searchtext.equals("")) {
				v_subjnm = StringManager.replace(v_subjnm, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
		}
			if(i%2 == 0){   
				v_class1 = "board_text1";
				v_class2 = "board_text3";
			}else{
				v_class1 = "board_text2";
				v_class2 = "board_text4";
			}
            
			String v_star = ""; 
			if(s_average >= 0 && s_average <=1){
			    v_star = "★";
			}else if(s_average > 1 && s_average <=2){
			    v_star = "★★";
			}else if(s_average > 2 && s_average <=3){
			    v_star = "★★★";
			}else if(s_average > 3 && s_average <=4){
			    v_star = "★★★★";
			}else if(s_average > 4 && s_average <=5){
			    v_star = "★★★★★";
			}

%>
													<tr height="9">
														<td></td>
													</tr>
													<tr align="center">
														<td></td>
														<td><%=v_dispnum%></td>
														<td width="1"><IMG src="/images/board/board_ml.gif" border="0"></td>
														<td align="left" style="padding-left:7"><a href="javascript:select('<%=v_subj%>')"><%=v_subjnm%></a></td>
														<td width="1"><IMG src="/images/board/board_ml.gif" border="0"></td>
														<td><%=v_replycount%></td>
														<td width="1"><IMG src="/images/board/board_ml.gif" border="0"></td>
														<td align="left">&nbsp;&nbsp;&nbsp;<span class=star><font color="red"><%=v_star%></font></span></td>
														<td width="1"><IMG src="/images/board/board_ml.gif" border="0"></td>
														<td><%=v_average%></td>
														<td width="1"><IMG src="/images/board/board_ml.gif" border="0"></td>
														<td><%=v_indate%></td>
														<td></td>
													</tr>
													<tr height="1">
														<td bgcolor="E5E5E5" colspan="15"></td>
													</tr>
<%  } if(i == 0){   %>    
                    <tr align="center">
                      <td style="padding-left:7" colspan="15" align="center">해당 내용이 존재하지 않습니다.</td>
                    </tr>    
<%    } %>
													<tr height="8">
														<td background="/images/board/board_bottom.gif" colspan="15"></td>
													</tr>
												</TABLE>
												<!-- 게시판 끝 -->
												<!-- 페이징&검색 시작 -->
												<TABLE cellpadding="0" cellspacing="0" border="0" width="735">
													<tr height="10">
														<td></td>
													</tr>
													<tr>
														<td align="center"><%= PageUtil.printPageList(v_totalpage, v_pageno, v_row) %></td>
													</tr>
													<tr height="10">
														<td></td>
													</tr>
												</TABLE>
												<!-- 페이징&검색 끝 -->
											</td>
										</tr>
									</table> </td>
							</tr>
						</TABLE>
						<!-- 본문 끝 -->
					</td>
					</form>
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