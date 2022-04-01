<%
//**********************************************************
//  1. 제      목: EDUCATION SUBJECT PAGE
//  2. 프로그램명: zu_SulmunNew_L.jsp
//  3. 개      요: 설문
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 7. 20
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","07"); 

     
    ArrayList list1     = null;
    ArrayList list2     = null;
    ArrayList list3     = null;
    ArrayList list4     = null;    
    ArrayList list5     = null;        


    list1 = (ArrayList)request.getAttribute("EducationSubjectList1");  
    list2 = (ArrayList)request.getAttribute("EducationSubjectList2");    
    //list3 = (ArrayList)request.getAttribute("GraduationSubjectList2");  

    list3 = (ArrayList)request.getAttribute("SulmunCommon");   
    list4 = (ArrayList)request.getAttribute("SulmunContents");  
    list5 = (ArrayList)request.getAttribute("SulmunTarget");      

    

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topMyClass.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
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

// Tab 선택시 동작
function showTab(clickTab)
{
	g_showTab(clickTab, 4);
}

function g_showTab(tabNo, count)
{

	var clickTabNo = new Number(tabNo);
	var tabCount   = new Number(count);

	if( clickTabNo == 0 || tabCount == 0 ) return;

	for(i=1; i<=tabCount; i++)
    {
		div = document.all["tab_"+i];

		if(!div) return;

		if( clickTabNo == i ) {
			// 활성화된 tab 클릭시 return
			if(div.style.display == "") return;
			div.style.display = ""
		}
		else {
			// 선택되지 않는 탭들은 깜빡임 방지.
			if(div.style.display != "none") {
				div.style.display = "none";
			}
		}
	}
}

// 콘텐츠 설문응시
function contentsWright(p_subj, p_grcode, p_gyear, p_subjseq, p_sulpapernum, p_sulpapernm, p_sulstart, p_sulend){
        document.form1.p_subj.value = 'CONTENTS';
        document.form1.s_subj.value = p_subj;
        document.form1.p_grcode.value = p_grcode;
        document.form1.p_gyear.value = p_gyear;
        document.form1.p_subjseq.value = p_subjseq;
        document.form1.p_sulpapernum.value = p_sulpapernum;
        document.form1.p_sulpapernm.value = p_sulpapernm;
		document.form1.p_sulstart.value = p_sulstart;
        document.form1.p_sulend.value = p_sulend;
        document.form1.p_listok.value = '0';

        window.self.name = "winSelectView";
        farwindow = window.open("", "openSubjChange", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 680, height = 538, top=0, left=0");
        document.form1.target = "openSubjChange";
        document.form1.action = "/servlet/controller.research.SulmunContentsUserServlet";
        document.form1.p_process.value = "SulmunUserPaperListPage";
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
}

// 과정 설문응시
function contentsWright1(p_subjnm, p_subj, p_grcode, p_gyear, p_subjseq, p_sulpapernum, p_sulpapernm, p_sulstart, p_sulend, p_sulnums){
        document.form1.p_subj.value = "ALL";
        document.form1.s_subj.value = p_subj;   // tz_suleach 들어갈 과정코드
        document.form1.p_subjnm.value = p_subjnm;
        document.form1.p_grcode.value = "ALL";
        document.form1.s_grcode.value = p_grcode;    // tz_suleach 들어갈 교육그룹코드       
        document.form1.p_gyear.value = p_gyear;
        document.form1.p_subjseq.value = p_subjseq;
        document.form1.p_sulpapernum.value = p_sulpapernum;
        document.form1.p_sulpapernm.value = p_sulpapernm;
		document.form1.p_sulstart.value = p_sulstart;
        document.form1.p_sulend.value = p_sulend;
        document.form1.p_sulnums.value = p_sulnums;    // 문제번호    
        document.form1.p_listok.value = '0';

        window.self.name = "winSelectView";
        farwindow = window.open("", "openSubjChange", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 680, height = 538, top=0, left=0");
        document.form1.target = "openSubjChange";
        document.form1.action = "/servlet/controller.research.SulmunSubjUserServlet";
        document.form1.p_process.value = "SulmunUserPaperListPage";
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
}

// 대상자 설문응시
function targetWright(p_subj, p_grcode, p_gyear, p_subjseq, p_sulpapernum, p_sulpapernm, p_sulstart, p_sulend){
        document.form1.p_subj.value = p_subj;
        document.form1.p_grcode.value = p_grcode;
        document.form1.p_gyear.value = p_gyear;
        document.form1.p_subjseq.value = p_subjseq;
        document.form1.p_sulpapernum.value = p_sulpapernum;
        document.form1.p_sulpapernm.value = p_sulpapernm;
		document.form1.p_sulstart.value = p_sulstart;
        document.form1.p_sulend.value = p_sulend;
        document.form1.p_listok.value = '0';

        window.self.name = "winInsertPage";
        farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 680, height = 538, top=0, left=0");
        document.form1.target = "openWinInsert";
        document.form1.action = "/servlet/controller.research.SulmunTargetUserServlet";
        document.form1.p_process.value = "SulmunUserPaperListPage";
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
}



// 일반 설문응시
function commonWright(p_subj, p_grcode, p_sulpapernum){
        document.form1.p_subj.value = p_subj;
        document.form1.p_grcode.value = p_grcode;
        document.form1.p_sulpapernum.value = p_sulpapernum;
     
        window.self.name = "winSelectView";
        farwindow = window.open("", "openSubjChange", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 680, height = 538, top=0, left=0");
        document.form1.target = "openSubjChange";
        document.form1.action = "/servlet/controller.research.SulmunCommonUserServlet";
        document.form1.p_process.value = "SulmunUserPaperListPage";
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
}

function ReloadPage(p_action, tab) {
  //document.form1.p_process.value = 'SulmunUserListPage';
  document.form1.p_process.value = 'SulmunNew';
  document.form1.p_action.value  = p_action;
  document.form1.action = "/servlet/controller.research.SulmunSubjUserServlet";
  document.form1.submit();
  showTab(tab);
}
//-->
</script>



          <!-- ############################## 중심부 시작 ################################### -->          
		    <table width="730" border="0" cellspacing="0" cellpadding="0">
 <form name="form1" method="post" action="/servlet/controller.research.SulmunCommonUserServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"    value="">
        <input type="hidden" name="p_subjnm"    value="">            
        <input type="hidden" name="s_subj"    value="">    
        <input type="hidden" name="p_grcode"  value="">
        <input type="hidden" name="s_grcode"  value="">        
        <input type="hidden" name="p_gyear"  value="">
        <input type="hidden" name="p_subjseq"  value="">
        <input type="hidden" name="p_sulpapernum"  value="">
        <input type="hidden" name="p_sulpapernm"  value="">
        <input type="hidden" name="p_sulstart"  value="">
        <input type="hidden" name="p_sulend"  value="">
        <input type="hidden" name="p_sulnums"  value="">        
        <input type="hidden" name="p_listok"  value="">
              <tr> 
                <td height="20" align="center" valign="top" class="location"> 
                  HOME &gt; 나의 강의실 &gt; 설문</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/myclass/<%=tem_subimgpath%>/tit_poll.jpg"></td>
                    </tr>
                    <tr> 
                      <td height="20"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="474" valign="top"><img src="/images/user/myclass/st_pollist.gif"> 
                      </td>
                      <td width="201" align="right" valign="top">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td colspan="2" class="font_ex">설문명을 클릭하시면 설문에 참가 하실 수 있습니다.</td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td colspan="2"> 
                        <!--<div id="tab_1" >                      
                         구분탭 
                        <table width="675" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="5" colspan="5"></td>
                          </tr>
                          <tr> 
                            <!--<td width="61" valign="top" style="cursor:hand;" onClick="showTab(1);" >
                                <img src="/images/user/myclass/tab_total_on.gif">
                            </td>
                            <td width="61" valign="top" align=left style="cursor:hand;" onClick="showTab(2);" >
                                <img src="/images/user/myclass/tab_course_on.gif" name="Image29" border="0">
                            </td>
                            <td width="61" valign="top"  align=left style="cursor:hand;" onClick="showTab(3);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image30','','/images/user/myclass/tab_general_on.gif',1)"><img src="/images/user/myclass/tab_general.gif" name="Image30" border="0"></a>
                            </td>
                            <td width="74" valign="top"  align=left style="cursor:hand;" onClick="showTab(4);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image31','','/images/user/myclass/tab_contents_on.gif',1)"><img src="/images/user/myclass/tab_contents.gif" name="Image31" border="0"></a>
                            </td>
                            <td width="479" valign="top"  align=left style="cursor:hand;" onClick="showTab(5);">
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image33','','/images/user/myclass/tab_object_on.gif',1)"><img src="/images/user/myclass/tab_object.gif" name="Image33" border="0"></a>
                            </td>
                          </tr>
                          <tr> 
                            <td height="10" colspan="5"></td>
                          </tr>
                        </table>
                        
                        <table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                          <tr  class="lcolor"> 
                            <td height="3" colspan="4" class="linecolor_my"></td>
                          </tr>
                          <tr> 
                            <td width="54" class="tbl_ptit2">설문번호</td>
                            <td width="404" class="tbl_ptit">설문지명</td>
                            <td width="48" class="tbl_ptit2">문제수</td>
                            <td width="133" class="tbl_ptit">설문기간</td>
                          </tr>
                          <tr> 
                            <td class="tbl_grc">10</td>
                            <td class="tbl_bleft"><a href="#">칠월의 설문 입니다.</a></td>
                            <td class="tbl_grc">4</td>
                            <td class="tbl_grc">2005/06/07~2005/07/08</td>
                          </tr>
                          <tr> 
                            <td height="26" class="tbl_grc">9</td>
                            <td height="26" class="tbl_bleft">&nbsp;</td>
                            <td class="tbl_grc">&nbsp;</td>
                            <td class="tbl_grc">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td class="tbl_grc">8</td>
                            <td class="tbl_bleft">&nbsp;</td>
                            <td class="tbl_grc">&nbsp;</td>
                            <td class="tbl_grc">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td class="tbl_grc">&nbsp;</td>
                            <td class="tbl_bleft">&nbsp;</td>
                            <td class="tbl_grc">&nbsp;</td>
                            <td class="tbl_grc">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td class="tbl_grc">&nbsp;</td>
                            <td class="tbl_bleft">&nbsp;</td>
                            <td class="tbl_grc">&nbsp;</td>
                            <td class="tbl_grc">&nbsp;</td>
                          </tr>
                        </table>
                        </div>-->
		                <div id="tab_1" >  
                        <!-- 구분탭 -->
                        <table width="675" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="5" colspan="5"></td>
                          </tr>
                          <tr> 
                            <!--<td width="61" valign="top" style="cursor:hand;" onClick="showTab(1);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image28','','/images/user/myclass/tab_total_on.gif',1)"><img src="/images/user/myclass/tab_total.gif" name="Image28" border="0"></a>
                            </td>-->
                            <td width="61" valign="top" style="cursor:hand;" onClick="showTab(1);" >
                                <img src="/images/user/myclass/tab_course_on.gif" alt="과정" name="Image29" border="0"><!--과정-->
                            </td>
                            <td width="61" valign="top" style="cursor:hand;" onClick="showTab(2);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image30','','/images/user/myclass/tab_general_on.gif',1)"><img src="/images/user/myclass/tab_general.gif" alt="일반"  name="Image30" border="0"></a>
                            </td>
                            <td width="74" valign="top" style="cursor:hand;" onClick="showTab(3);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image31','','/images/user/myclass/tab_contents_on.gif',1)"><img src="/images/user/myclass/tab_contents.gif" alt="콘텐츠"  name="Image31" border="0"></a>
                            </td>
                            <td width="479" valign="top" style="cursor:hand;" onClick="showTab(4);">
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image33','','/images/user/myclass/tab_object_on.gif',1)"><img src="/images/user/myclass/tab_object.gif" alt="대상자"  name="Image33" border="0"></a>
                            </td>
                          </tr>
                          <tr> 
                            <td height="10" colspan="5"></td>
                          </tr>
                        </table>		                                      
                        <!-- 설문리스트table  -->
                        <table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                          <tr  class="lcolor"> 
                            <td height="3" colspan="7" class="linecolor_my"></td>
                          </tr>
                          <tr> 
                            <td width="5%" class="tbl_ptit2">No</td>      
                            <td width="8%" class="tbl_ptit">교육<br>구분</td>
                            <td width="20%" class="tbl_ptit2">과정</td>                                                                                                      
                            <td width="12%" class="tbl_ptit">교육기간</td>                               
                            <td class="tbl_ptit2">설문지명</td>                         
                            <td width="8%" class="tbl_ptit">진도율<br>비교</td>                                    
                            <td width="10%" class="tbl_ptit2">상태</td>
                          </tr>  
                          <%
                          for(int i = 0; i < list1.size(); i++) {

                                DataBox dbox1 = (DataBox)list1.get(i);

                                String v_isonoff       =  dbox1.getString("d_isonoff");
                                String v_subjnm        =  dbox1.getString("d_subjnm");
                                String v_edustart      =  dbox1.getString("d_edustart");
                                String v_eduend        =  dbox1.getString("d_eduend");
                                
                                String v_grcode        =  dbox1.getString("d_grcode");
								String v_subj          =  dbox1.getString("d_subj");
                                String v_year          =  dbox1.getString("d_year");
                                String v_subjseq       =  dbox1.getString("d_subjseq");
                                String v_userid        =  dbox1.getString("d_userid");
                                int    v_tstep         =  dbox1.getInt("d_tstep");    // 진도율
                                String v_sulnums       =  dbox1.getString("d_sulnums"); 
                                
                                int v_sulpapernum      =  dbox1.getInt("d_sulpapernum");
                                String v_sulpapernm    =  dbox1.getString("d_sulpapernm");
                                int v_progress         =  dbox1.getInt("d_progress"); // 설문응시제한진도율
                                int v_eachcnt          =  dbox1.getInt("d_eachcnt");  // 응시여부
                                                                
                                v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                                v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");

								String v_isonoff_value = "";
                              
                               if(v_isonoff.equals("ON"))       {   v_isonoff_value =   "사이버";
                               }else if(v_isonoff.equals("OFF")){  v_isonoff_value =   "집합";     }

                          %>                 
                          <tr> 
                            <td class="tbl_grc"><%=i+1%></td>      
                            <td class="tbl_grc"><%=v_isonoff_value%></td>
                            <td class="tbl_bleft"><%=v_subjnm%></td>   
                            <td class="tbl_grc"><%=v_edustart%><br>~<%=v_eduend%></td>                                                                                                        
                            <td class="tbl_bleft">
                            <%if(v_eachcnt>0){%><!--완료-->
                                <%=v_sulpapernm%>
                            <%}else{%>
                                <%if(v_progress<=v_tstep){%>
                                    <a href="javascript:contentsWright1('<%=v_subjnm%>', '<%=v_subj%>', '<%=v_grcode%>', '<%=v_year%>', '<%=v_subjseq%>', '<%=v_sulpapernum%>', '<%=v_sulpapernm%>', '', '','<%=v_sulnums%>')">
                                    <u><%=v_sulpapernm%></u></a>                                  
                                <%}else{%>
                                    <a href="javascript:alert('설문응시 제한진도율보다 진도율이 부족합니다.');"><u><%=v_sulpapernm%></u></a>                          
                                <%}%>    
                            <%}%>
                            </td>
                            <td class="tbl_grc"><%=v_tstep%>/<%=v_progress%>                       
                            </td>
                            <td  class=tbl_grc><font color="red"><%if(v_eachcnt>0){out.println("완료");}else{out.println("응시가능");}%></font></td>
                          </tr>
<%
                        }
%>                          
                        </table>		                
		                </div>
		                <div id="tab_2" STYLE="display:none">                        
                        <!-- 구분탭 -->
                        <table width="675" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="5" colspan="5"></td>
                          </tr>
                          <tr> 
                            <!--<td width="61" valign="top" style="cursor:hand;" onClick="showTab(1);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image28','','/images/user/myclass/tab_total_on.gif',1)"><img src="/images/user/myclass/tab_total.gif" name="Image28" border="0"></a>
                            </td>-->
                            <td width="61" valign="top" style="cursor:hand;" onClick="showTab(1);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image29','','/images/user/myclass/tab_course_on.gif',1)"><img src="/images/user/myclass/tab_course.gif" alt="과정"  name="Image29" border="0"></a>
                            </td>
                            <td width="61" valign="top" style="cursor:hand;" onClick="showTab(2);" >
                                <img src="/images/user/myclass/tab_general_on.gif" alt="일반"  name="Image30" border="0"><!--일반-->
                            </td>
                            <td width="74" valign="top" style="cursor:hand;" onClick="showTab(3);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image31','','/images/user/myclass/tab_contents_on.gif',1)"><img src="/images/user/myclass/tab_contents.gif" alt="콘텐츠" name="Image31" border="0"></a>
                            </td>
                            <td width="479" valign="top" style="cursor:hand;" onClick="showTab(4);">
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image33','','/images/user/myclass/tab_object_on.gif',1)"><img src="/images/user/myclass/tab_object.gif" alt="대상자"  name="Image33" border="0"></a>
                            </td>
                          </tr>
                          <tr> 
                            <td height="10" colspan="5"></td>
                          </tr>
                        </table>
                        <!-- 설문리스트table -->
                        <table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                          <tr  class="lcolor"> 
                            <td height="3" colspan="6" class="linecolor_my"></td>
                          </tr>
                          <tr> 
                            <td width="5%" class="tbl_ptit2">No</td>
                            <td width="404" class="tbl_ptit">설문지명</td>
                            <td width="150" class="tbl_ptit2">설문기간</td>                            
                            <td width="48" class="tbl_ptit">문제수</td>
                            <td width="10%" class="tbl_ptit2">상태</td>                            
                          </tr>
                          <%
                          for(int i = 0; i < list3.size(); i++) {
                                DataBox dbox3 = (DataBox)list3.get(i);
                                int v_sulpapernum      =  dbox3.getInt("d_sulpapernum");
                                String v_sulpapernm    =  dbox3.getString("d_sulpapernm");
                                String v_sulstart      =  dbox3.getString("d_sulstart");
                                String v_sulend        =  dbox3.getString("d_sulend");
                                int v_totcnt           =  dbox3.getInt("d_totcnt");
                                v_sulstart      = FormatDate.getFormatDate(v_sulstart,"yyyy/MM/dd");
                                v_sulend        = FormatDate.getFormatDate(v_sulend,"yyyy/MM/dd");
                                int v_sulresult = dbox3.getInt("d_sulresult");       // 설문결과여부
                                String v_issul  = dbox3.getString("d_issul");        // 설문가능여부                                
                          %>    
                          <tr> 
                            <td class="tbl_grc"><%=i+1%></td>                                                                                
                            <td  class="tbl_bleft">
                            <%if(v_issul.equals("0")){%><a href="javascript:commonWright('COMMON', '<%=dbox3.getString("d_grcode")%>', '<%=v_sulpapernum%>')"><u><%=String.valueOf(dbox3.getString("d_sulpapernm"))%></u></a>                                                      
                            <%}else{%><%=String.valueOf(dbox3.getString("d_sulpapernm"))%><%}%>                            
                            </td>
                            <td class="tbl_grc"><%=v_sulstart%>~<b><%=v_sulend%></b></td>
                            <td class="tbl_grc"><%=v_totcnt%></td>                                                        
                            <td  class=tbl_grc><font color="red">
                            <%if(v_issul.equals("0")){%>응시가능<%}else{%>완료<%}%>
                            </font></td>                                                    
                          </tr>
                          <%    
							} // for end
                            if(list3.size() == 0){
                          %>
                          <tr>
                            <td height="3" colspan="6" align="center"></td>
                          </tr>
                          <% } %>   
                        </table>
		                </div>
		                <div id="tab_3" STYLE="display:none">                        
                        <!-- 구분탭 -->
                        <table width="675" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="5" colspan="5"></td>
                          </tr>
                          <tr> 
                            <!--<td width="61" valign="top" style="cursor:hand;" onClick="showTab(1);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image28','','/images/user/myclass/tab_total_on.gif',1)"><img src="/images/user/myclass/tab_total.gif" name="Image28" border="0"></a>
                            </td>-->
                            <td width="61" valign="top" style="cursor:hand;" onClick="showTab(1);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image29','','/images/user/myclass/tab_course_on.gif',1)"><img src="/images/user/myclass/tab_course.gif" alt="과정"  name="Image29" border="0"></a>
                            </td>
                            <td width="61" valign="top" style="cursor:hand;" onClick="showTab(2);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image30','','/images/user/myclass/tab_general_on.gif',1)"><img src="/images/user/myclass/tab_general.gif" alt="일반"  name="Image30" border="0"></a>
                            </td>
                            <td width="74" valign="top" style="cursor:hand;" onClick="showTab(3);" >
                                <img src="/images/user/myclass/tab_contents_on.gif" alt="콘텐츠"  name="Image31" border="0"><!--콘텐츠-->
                            </td>
                            <td width="479" valign="top" style="cursor:hand;" onClick="showTab(4);">
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image33','','/images/user/myclass/tab_object_on.gif',1)"><img src="/images/user/myclass/tab_object.gif" alt="대상자"  name="Image33" border="0"></a>
                            </td>
                          </tr>
                          <tr> 
                            <td height="10" colspan="5"></td>
                          </tr>
                        </table>
                        <!-- 설문리스트table  -->
                        <table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                          <tr  class="lcolor"> 
                            <td height="3" colspan="6" class="linecolor_my"></td>
                          </tr>
                          <tr> 
                            <td width="5%" class="tbl_ptit2">No</td>      
                            <td width="10%" class="tbl_ptit">교육구분</td>
                            <td width="25%" class="tbl_ptit2">과정</td>                                                                               
                            <td width="25%" class="tbl_ptit">교육기간</td>                               
                            <td class="tbl_ptit2">설문지명</td>
                            <td width="10%" class="tbl_ptit">상태</td>
                          </tr>
                          <%
                          int count2 = 0;
                          for(int i = 0; i < list4.size(); i++) {

                                DataBox dbox2 = (DataBox)list4.get(i);

                                String v_isonoff       =  dbox2.getString("d_isonoff");
                                String v_subjnm        =  dbox2.getString("d_subjnm");
								String v_subj          =  dbox2.getString("p_subj");
								String v_grcode        =  dbox2.getString("d_grcode");
                                String v_year          =  dbox2.getString("p_year");
                                String v_subjseq       =  dbox2.getString("p_subjseq");
                                int v_sulpapernum      =  dbox2.getInt("d_sulpapernum");
                                String v_sulpapernm    =  dbox2.getString("d_sulpapernm");
                                String v_sulstart      =  dbox2.getString("d_sulstart");
                                String v_sulend        =  dbox2.getString("d_sulend");
                                String v_edustart      =  dbox2.getString("d_edustart");
                                String v_eduend        =  dbox2.getString("d_eduend");
                                v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                                v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");

								String v_isonoff_value = "";
                              
                              if(v_isonoff.equals("ON"))      {   v_isonoff_value =   "사이버";
                              }else if(v_isonoff.equals("OFF")){  v_isonoff_value =   "집합";     }

                          %>
						  <%    if(v_isonoff.equals("ON")) {
							      count2 ++; 
						  %>                          
                          <tr> 
                            <td class="tbl_grc"><%=count2%></td>      
                            <td class="tbl_grc"><%=v_isonoff_value%></td>
                            <td class="tbl_grc"><%=v_subjnm%></td>                                                                               
                            <td class="tbl_grc"><%=v_edustart%>~<%=v_eduend%></td>
                            <td  class="tbl_bleft">
                                <%if(dbox2.getString("d_contentsdata").equals("0")){%>
                                <a href="javascript:contentsWright('<%=v_subj%>', 'ALL', '<%=v_year%>', '<%=v_subjseq%>', '<%=v_sulpapernum%>', '<%=v_sulpapernm%>', '<%=v_sulstart%>', '<%=v_sulend%>')"><u><%=String.valueOf(dbox2.getString("d_sulpapernm"))%></u></a>
                                <%}else{%>
                                <%=String.valueOf(dbox2.getString("d_sulpapernm"))%>
                                <%}%>                            
                            </td>
                            <%if(dbox2.getString("d_contentsdata").equals("0")){%>
                            <td  class=tbl_grc><font color="red">응시가능</font></td>
                            <%}else if(dbox2.getString("d_contentsdata").equals("1")){%>
                            <td  class=tbl_grc><font color="red">완료</font></td>
                            <%}%>                                                      
                          </tr>
                          <%    }
							} // for end
                            if(count2 == 0){
                          %>
                          <tr>
                            <td height="3" colspan="6" align="center"></td>
                          </tr>
                          <% } %>
                        </table>
		                </div>
		                <div id="tab_4" STYLE="display:none">                        
                        <!-- 구분탭 -->
                        <table width="675" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="5" colspan="5"></td>
                          </tr>
                          <tr> 
                            <!--<td width="61" valign="top" style="cursor:hand;" onClick="showTab(1);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image28','','/images/user/myclass/tab_total_on.gif',1)"><img src="/images/user/myclass/tab_total.gif" name="Image28" border="0"></a>
                            </td>-->
                            <td width="61" valign="top" style="cursor:hand;" onClick="showTab(1);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image29','','/images/user/myclass/tab_course_on.gif',1)"><img src="/images/user/myclass/tab_course.gif" alt="과정"  name="Image29" border="0"></a>
                            </td>
                            <td width="61" valign="top" style="cursor:hand;" onClick="showTab(2);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image30','','/images/user/myclass/tab_general_on.gif',1)"><img src="/images/user/myclass/tab_general.gif" alt="일반"  name="Image30" border="0"></a>
                            </td>
                            <td width="74" valign="top" style="cursor:hand;" onClick="showTab(3);" >
                                <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image31','','/images/user/myclass/tab_contents_on.gif',1)"><img src="/images/user/myclass/tab_contents.gif" alt="콘텐츠"  name="Image31" border="0"></a>
                            </td>
                            <td width="479" valign="top" style="cursor:hand;" onClick="showTab(4);">
                                <img src="/images/user/myclass/tab_object_on.gif" alt="대상자"  name="Image33" border="0"><!--대상자-->
                            </td>
                          </tr>
                          <tr> 
                            <td height="10" colspan="5"></td>
                          </tr>
                        </table>
                        <!-- 설문리스트table  -->
                        <table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                          <tr  class="lcolor"> 
                            <td height="3" colspan="6" class="linecolor_my"></td>
                          </tr>
                          <tr> 
                            <td width="5%" class="tbl_ptit2">No</td>
                            <td width="404" class="tbl_ptit">설문지명</td>
                            <td width="150" class="tbl_ptit2">설문기간</td>                            
                            <td width="48" class="tbl_ptit">문제수</td>
                            <td width="10%" class="tbl_ptit2">상태</td>                            
                          </tr>
                          <%
                          for(int i = 0; i < list5.size(); i++) {
                                DataBox dbox2 = (DataBox)list5.get(i);
                                int v_sulpapernum      =  dbox2.getInt("d_sulpapernum");
                                String v_sulpapernm    =  dbox2.getString("d_sulpapernm");
                                String v_sulstart      =  dbox2.getString("d_sulstart");
                                String v_sulend        =  dbox2.getString("d_sulend");
                                int v_totcnt           =  dbox2.getInt("d_totcnt");
                                v_sulstart      = FormatDate.getFormatDate(v_sulstart,"yyyy/MM/dd");
                                v_sulend        = FormatDate.getFormatDate(v_sulend,"yyyy/MM/dd");
                                int v_sulresult = dbox2.getInt("d_sulresult");       // 설문결과여부 (1, 0)
                                String v_issul  = dbox2.getString("d_issul");        // 설문가능여부 (Y, N)                               
                                
                                if ( v_issul.equals("Y")) {
                                	v_issul = "응시가능";
                                } else {
                                	v_issul = "설문종료";
                                }
                         %>    
                          <tr> 
                            <td class="tbl_grc"><%=i+1%></td>                                                                                
                            <td  class="tbl_bleft">
                                <%if(v_sulresult==0){%>
                                    <%if(v_issul.equals("설문종료")){%>
                                    <%=String.valueOf(dbox2.getString("d_sulpapernm"))%>
                                    <%}else{%>
                                    <a href="javascript:targetWright('TARGET', '<%=dbox2.getString("d_grcode")%>',  '<%=dbox2.getString("d_year")%>', '<%=dbox2.getString("d_subjseq")%>', '<%=v_sulpapernum%>', '<%=v_sulpapernm%>', '<%=v_sulstart%>', '<%=v_sulend%>')"><u><%=String.valueOf(dbox2.getString("d_sulpapernm"))%></u></a>
                                    <%}%>                            
                                <%}else{%>
                                    <%=String.valueOf(dbox2.getString("d_sulpapernm"))%>
                                <%}%>                            
                            </td>
                            <td class="tbl_grc"><%=v_sulstart%>~<b><%=v_sulend%></b></td>
                            <td class="tbl_grc"><%=v_totcnt%></td>                                                        
                            <%if(v_sulresult==0){%>
                            <td  class=tbl_grc><font color="red"><%=v_issul%></font></td>
                            <%}else{%>
                            <td  class=tbl_grc><font color="red">완료</font></td>
                            <%}%>                                                      
                          </tr>
                          <%    
							} // for end
                            if(list5.size() == 0){
                          %>
                          <tr>
                            <td height="3" colspan="6" align="center"></td>
                          </tr>
                          <% } %>                          
                        </table>
		                </div>		                		                		                
                        </td>
                    </tr>
                    <tr> 
                      <td height="20" colspan="5"></td>
                    </tr>
                  </table>

                </td>
              </tr>
              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
              <tr> 
                <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
              </tr>
              </form>
            </table>
            <!-- ############################## 중심부 끝 ################################### -->  
            
<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
