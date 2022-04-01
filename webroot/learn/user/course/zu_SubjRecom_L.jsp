<%
//**********************************************************
//  1. 제      목: 과정추천 리스트
//  2. 프로그램명: zu_SubjRecom_L.jsp
//  3. 개      요: 추천과정 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 8. 25
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process         = box.getString("p_process");

    String  v_upperclass      =  "";
    String  v_upperclassnm    =  "";
    String  v_isonoff         =  "";
    String  v_course          =  "";
    String  v_cyear           =  "";
    String  v_courseseq       =  "";
    String  v_coursenm        =  "";
    String  v_subj            =  "";
    String  v_year            =  "";
    String  v_subjnm          =  "";
    String  v_subjseq         =  "";
    String  v_propend    =  "";
    String  v_edustart        =  "";
    String  v_eduend          =  "";
    String  v_preurl          =  "";
    String  v_proposetype     =  "";
    String  v_classnm         =  "";
    String v_isnewcourse      =  "";
    String  v_isnewupperclass =  "";
    String  v_proposeyn       =  "";
    String  v_isonoff_value   =  "";
    String  v_prop_start      =  "";
    String  v_prop_end        =  "N";
    String  v_prop_img        =  "";
    String  v_owner           = "";
	String  v_eduend_check    = "";
    int     v_studentlimit    =  0;
    int     v_stucnt          =  0;
    int     v_rowspan         =  0;
    int     v_rowspan_cnt     =  0;
    int     v_total     =  0;
    int     i                 =  0;
    int     v_possiblecnt     = 0;
    int     v_totcnt          = 0;

    ProposeCourseBean bean = new ProposeCourseBean();
    String  s_userid = box.getSession("userid");
    String  s_jikwi  = box.getSession("jikwi");
    String  v_prop_check = "";
    ArrayList list   = (ArrayList)request.getAttribute("SubjectList");
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
//-->
<!--
 function whenSelect(){
     //alert("111111");
     document.form1.target = "_self";
     document.form1.action='/servlet/controller.propose.MainSubjSearchServlet';
     document.form1.p_process.value = 'SubjRecomList';
     document.form1.submit();
 }

function whenSubjInfo(subj,subjnm,isonoff,upperclass,upperclassnm,process){
    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value = subjnm;
    document.form1.p_isonoff.value = isonoff;
    document.form1.p_upperclass.value = upperclass;
    document.form1.p_upperclassnm.value = upperclassnm;
    document.form1.p_process.value = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'SubjRecomList';
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet?leftmenu=1';
    document.form1.submit();
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

						<!-- 검색 끝 -->
						<!-- 본문 시작 -->
                     
						<TABLE cellpadding="0" cellspacing="0" border="0" width="735">
							<tr>
								
                <td class="bodypd"> 
                  <!-- 현재 위치 시작 -->
                  <TABLE cellpadding="0" cellspacing="0" border="0" width="100%">
    								    <tr>
    								        <td align="right" class="location"><img src="/images/common/bullet_location.gif"> <a href="http://www.hkhrd.com">HOME</a> > <a href="http://www.hkhrd.com/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">수강신청/안내</a> > BEST과정</td>
    								    </tr>
    								    <tr height="3">
    								        <td></td>
    								    </tr>
    								</table>
                  <!-- 현재 위치 끝 -->
                  <TABLE cellpadding="0" cellspacing="0" border="0" width="735">
        <tr> 
          <td> <TABLE cellpadding="0" cellspacing="0" border="0">
              <tr> 
                <td><IMG src="/images/common/title_bullet.gif" border="0"></td>
                <td><img src="/images/user/course/title_course_03.gif" alt="BEST과정"></td>
              </tr>
            </TABLE></td>
        </tr>
        <tr height="1"> 
          <td bgcolor="E5E5E5"></td>
        </tr>
        <tr> 
          <td height="20"></td>
        </tr>
        <tr>
          <td  align="right" >
          <table cellpadding="0" cellspacing="0" border="0">
          <form name="form1" method="post">
          <input type='hidden' name='p_subj'>
          <input type='hidden' name='p_subjnm'>
          <input type='hidden' name='p_isonoff'>
          <input type='hidden' name='p_upperclass'>
          <input type='hidden' name='p_upperclassnm'>
          <input type='hidden' name='p_process'>
          <input type='hidden' name='p_actionurl' value='controller.propose.MainSubjSearchServlet?topmenu=1&leftmenu=4'>
          <input type='hidden' name='p_rprocess'>
            <td><img src="/images/user/course/years_t.gif"></td>
            <td style="padding-left:3">
              <%
              
                  SelectEduBean selectEdu = new SelectEduBean();
                  String  gyear    = box.getStringDefault("gyear",FormatDate.getDate("yyyy"));
                  
                  String yearStr  = "";
                  yearStr = "<select name='gyear' onChange=whenSelect() style='border-style:solid;border-width: 1px 1px 1px 1px;border-color:cccccc;color:333333;font-size:9pt;background-color:none;width : 65px;height:19px;font-size:9pt;'>";
                  
                  ArrayList gyearList = selectEdu.getGyear(box);
                  for(int k = 0; k < gyearList.size(); k++) {
                    DataBox dbox = (DataBox)gyearList.get(k);
                    yearStr += "<option value = '"+ dbox.getString("d_gyear")+"' ";
                    if (gyear.equals(dbox.getString("d_gyear"))) {
                      yearStr += " selected";
                    }
                    yearStr += " >";
                    yearStr += dbox.getString("d_gyear");
                    yearStr += "</option>";
                  }
                  yearStr += "</select>";
                  //out.print(yearStr);
              %>
              <script>
              NV_write("<%=yearStr%>",10);
              </script>
              <!--검색년도 select box END -->
              </td>
              <td style="padding-left:6"><A HREF="javascript:whenSelect()"><IMG src="/images/common/bt_y_search2.gif" border="0"></A></td>
			  <td width='8'>&nbsp;</td>
            </tr>
            </form>
          </table>  
        </td>
        </tr>
		<tr>
		<td height=4></td>
		</tr>
              <tr> 
                <td><table border="0" cellpadding="0" cellspacing="0" width="735">
                    <tr> 
                      <td class="boardskin1_color" height="3"></td>
                    </tr>
                    <tr> 
                      <td height="2"></td>
                    </tr>
                    <tr> 
                      <td><table width="735" border="0" cellspacing="1" cellpadding="2" class="boardskin1_out">
                          <tr> 
                            <td width="113" class=boardskin1_textb>구분</td>
                            <td width="115" class=boardskin1_textb>분류</td>
                            <td width="379" class=boardskin1_textb>과정명</td>
                            <td width="107" class=boardskin1_textb>총개설차수</td>
                          </tr>
                          <%
            v_total = list.size();
            for(i = 0; i < v_total; i++) {
            DataBox dbox  = (DataBox)list.get(i);
              
              v_isonoff     =dbox.getString("d_isonoff");
              v_subj        =dbox.getString("d_subj");
              v_upperclass  =dbox.getString("d_upperclass");
              v_classnm     =dbox.getString("d_classname");
              v_subjnm      =dbox.getString("d_subjnm");
              v_possiblecnt = dbox.getInt("d_possiblecnt");
              v_totcnt = dbox.getInt("d_totcnt");
            %>
                          <tr> 
                            <td class=boardskin1_textn><%if(v_isonoff.equals("ON")){out.print("사이버");}else if(v_isonoff.equals("OFF")){out.print("집합");}%></td>
                            <td class=boardskin1_textn><%=v_classnm%></td>
                            <%if (v_possiblecnt == 0){%>
                              <td class=boardskin1_textn_l><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>','<%=v_upperclass%>','<%=v_classnm%>','SubjectPreviewPage')"><%=v_subjnm%></a></td>
                            <%}
                            else{
                            %>
                              <td class=link2 bgcolor=#FFFFFF><img src="/images/user/course/bullet_ani.gif"><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>','<%=v_upperclass%>','<%=v_classnm%>','SubjectPreviewPage')"><%=v_subjnm%></a></td>
                            <%}%>
                            <td class=boardskin1_textn><%=v_totcnt%></td>
                          </tr>
            <%}%>
            <%           if (v_total == 0 ) {                 %>
                                          <tr> 
                                              <td colspan="5" class=boardskin1_textn>검색된 과정이 없습니다.</td>
                                          </tr>
             <%           }                 %>

                        </table></td>
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
                <td>&nbsp;</td>
              </tr>
            </TABLE> </td>
							</tr>
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