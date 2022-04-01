<%
//**********************************************************
//  1. 제	  목: SUBJECT LIST
//  2. 프로그램명: zu_Subject_L.jsp
//  3. 개	  요: 과정조회
//  4. 환	  경: JDK 1.3
//  5. 버	  젼: 1.0
//  6. 작	  성: 2004.01.14
//  7. 수	  정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	String p_specials = box.get("p_specials");
	String p_action = box.get("p_action");
	String type = box.get("type");
    String p_menuid = box.get("menuid"); //방송 01,게임 02,기획 03
    String p_middleclass = box.get("s_mclassnm");  //산업계직무재교육 001,차세대인재양성교육 002, 수탁교육 003
	if (box == null) box = RequestManager.getBox(request);
	List<DataBox> subjList = (List<DataBox>)request.getAttribute("SubjectList");		 // 과정 리스트
	int v_pageno = box.getInt("p_pageno");
	if (v_pageno == 0)  v_pageno = 1;
	int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
	int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
	int v_pagesize = box.getInt("p_pagesize");
	int	 v_totalrowcount =  0;
	
	System.out.println("jsp_gubun::::::::::::::::"+box.getSession("s_gubun"));
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>

<!-- 스크립트영역 -->
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language="JavaScript">
<!--//
// 과정검색 엔터처리
function search_enter(e)  {
	if (e.keyCode =='13'){  subjSearch();  }
}

// 과정검색
function subjSearch() {
	document.form1.p_process.value = "SubjectList";
	document.form1.submit();
}

//과정 내용보기
function whenSubjInfo(subj, year, subjseq, seq){
document.form1.p_subj.value = subj;
document.form1.p_year.value = year;
document.form1.p_subjseq.value = subjseq;
document.form1.p_seq.value = seq;
document.form1.p_process.value = 'SubjectPreviewPage';
document.form1.p_rprocess.value = 'SubjectList';
document.form1.action='/servlet/controller.off.ProposeOffServlet';
document.form1.target = "_self";
document.form1.submit();
}

//전체과정보기
function whenSubjectListAll() {
document.form1.p_process.value = 'SubjectListAll';
document.form1.p_rprocess.value = 'SubjectList';
document.form1.action='/servlet/controller.off.ProposeOffServlet';
document.form1.submit();
}
//수강신청
function whenSubjPropose(subj,year,subjseq, subjnm, seq) {
	if(<%= box.getSession("userid").length()==0 %>) {
		alert("로그인이 필요합니다.");
		return;
	}
	if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")){
	 return;
	}
	var new_Open = window.open("","proposeWindow",'scrollbars=yes,width=800,height=600,resizable=no');
	document.form1.p_process.value = "SubjectEduProposePage";
	document.form1.target = "proposeWindow";
	document.form1.p_subj.value = subj;
	document.form1.p_year.value = year;
	document.form1.p_subjseq.value = subjseq;
	document.form1.p_seq.value = seq;
	document.form1.action = "/servlet/controller.off.ProposeOffServlet";
	document.form1.submit();
	document.form1.target = "_self";
}

//관심버튼 클릭
function fnInterestInsert(subj,year,subjseq,courseyn,subjnm) {

	if (confirm(subjnm + " 과정을 관심과정으로 등록하시곘습니까?")) {
			$.post("/servlet/controller.common.AjaxServlet"
					, {checkSqlNum:"interest.exist", sqlNum:"interest.insert", p_subj:subj,rerurnFunction:"resultInterest"}
					, function(data) {
						$("#ajaxDiv").html(data);
					});
	}
}
function resultInterest(data) {
	if (data.result=='true') alert("관심과정으로 등록했습니다."); 
	else alert("관심과정 등록이 실패하였습니다.\n"+data.result);
}
	//페이지 이동
	function go(index) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = index;
		document.form1.p_process.value = "SubjectList";
		document.form1.submit();
	}
	//페이지 이동
	function selectArea(area,mid) {
		//document.form1.type.value = area;
		document.form1.p_area.value = area;
		if( mid != '' ) {
			document.form1.menuid.value = mid;
		}
		//document.form1.s_mclassnm.value = "";
		document.form1.p_action.value = "go";
		document.form1.p_process.value = "SubjectList";
		document.form1.p_pageno.value = 0;
		document.form1.submit();
	}
	//페이지 이동
	function goPage(pageNum) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = pageNum;
		document.form1.p_process.value = "SubjectList";
		document.form1.submit();
	}
	function pagesize(pageSize) {
		document.form1.target = "_self";
		document.form1.p_pageno.value = 1;
		document.form1.p_pagesize.value = pageSize;
		document.form1.p_process.value = "SubjectList";
		document.form1.submit();
	}
//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<form name="form1" action="/servlet/controller.off.ProposeOffServlet" method="post">
	<input type="hidden" name="type"	value="<%=type%>"/>
	<input type="hidden" name="p_upperclass"	value="<%=type%>"/>
	<input type="hidden" name="p_process"	value="<%=box.get("p_process")%>"/>
	<input type="hidden" name="p_rprocess"	value=""/>
	<input type="hidden" name="p_year"	value=""/>
	<input type="hidden" name="p_subj"	value=""/>
	<input type="hidden" name="p_subjseq"	value=""/>
	<input type="hidden" name="p_seq"	value=""/>
	<input type="hidden" name="gubun" value="2">
	<input type="hidden" name="p_area" id="p_area"	value="<%= box.get("p_area") %>"/>
	<input type="hidden" name="p_action"	value="<%=p_action%>"/>
	<input type="hidden" name="p_pageno"	value="<%=v_pageno%>"/>
	<input type="hidden" name="p_pagesize"  value="<%=v_pagesize%>"/>
	<input type="hidden" name="menuid" value='<%= box.get("menuid") %>'>	
    <input type="hidden" name="p_topuserid"	value=""/>
    <input type="hidden" name="p_toppwd"	value=""/>
	
	<%if( box.getSession("tem_grcode").equals("N000001")) { %>
	<table>   
      <tr>
      	<td>
	
    <!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <% if      ("S01".equals(type)) { %> <td><img src="/images/portal/homepage_renewal/offline/stitle_02.gif" alt="방송영상" /></td><td class="h_road">Home &gt; 오프라인 과정  &gt; <strong>방송영상</strong></td><% }
   else if ("S02".equals(type)) { %> <td><img src="/images/portal/homepage_renewal/offline/stitle_03.gif" alt="게임제작" /></td><td class="h_road">Home &gt; 오프라인 과정  &gt; <strong>게임제작</strong></td><% }
   else if ("S03".equals(type)) { %> <td><img src="/images/portal/homepage_renewal/offline/stitle_04.gif" alt="기획창작" /></td><td class="h_road">Home &gt; 오프라인 과정  &gt; <strong>기획창작</strong></td><% }
   else if ("".equals(type)) { %> <td><img src="/images/portal/homepage_renewal/offline/stitle_01.gif" alt="전체교육과정" /></td><td class="h_road">Home &gt; 오프라인 과정  &gt; <strong>전체교육과정</strong></td><% }
%>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <!--타이틀부분//-->

      <table width="672" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="15"></td>
        </tr>
        <tr>
          <td>
          
          <!--검색부분-->
          <table width="672" border="0" cellpadding="0" cellspacing="1" bgcolor="ADBBD8">
            <tr>
              <td height="34" bgcolor="F0F0F0"><table width="90%" border="0" align="center" cellpadding="3" cellspacing="0">
                <tr>
                  <td><img src="/images/portal/homepage_renewal/common/txt_search_in.gif" alt="과정검색" width="69" height="16" /></td>
                  <td><input name="s_subjnm" type="text" class="input_search" id="s_subjnm" size="77" value="<%= box.get("s_subjnm") %>"/></td>
                  <td><a href="javascript:subjSearch();"><img src="/images/portal/homepage_renewal/common/btn_search_in.jpg" alt="검색" width="46" height="24" /></a></td>
                </tr>
                
              </table></td>
            </tr>
          </table>
          <!--검색부분//-->
          
          </td>
        </tr>
        <tr>
          <td height="15"></td>
        </tr>
      </table>
      
      <!--탭부분-->
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
        <%if (box.get("p_area").equals("")){ %>
          <td width="133"><a href="javascript:selectArea('','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image44','','/images/portal/homepage_renewal/offline/tab_on_01.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_on_01.gif" name="Image44" id="Image44" /></a></td>
          <td width="133"><a href="javascript:selectArea('004','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image45','','/images/portal/homepage_renewal/offline/tab_on_02.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_02.gif" name="Image45" id="Image45" /></a></td>
          <td width="133"><a href="javascript:selectArea('001','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image46','','/images/portal/homepage_renewal/offline/tab_on_03.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_03.gif" name="Image46" id="Image46" /></a></td>
          <td width="133"><a href="javascript:selectArea('002','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image47','','/images/portal/homepage_renewal/offline/tab_on_04.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_04.gif" name="Image47" id="Image47" /></a></td>
          <td width="133"><a href="javascript:selectArea('003','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image48','','/images/portal/homepage_renewal/offline/tab_on_05.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_05.gif" name="Image48" id="Image48" /></a></td>
          <%}else if(box.get("p_area").equals("004")){ %>
          <td width="133"><a href="javascript:selectArea('','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image44','','/images/portal/homepage_renewal/offline/tab_on_01.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_01.gif" name="Image44" id="Image44" /></a></td>
          <td width="133"><a href="javascript:selectArea('004','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image45','','/images/portal/homepage_renewal/offline/tab_on_02.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_on_02.gif" name="Image45" id="Image45" /></a></td>
          <td width="133"><a href="javascript:selectArea('001','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image46','','/images/portal/homepage_renewal/offline/tab_on_03.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_03.gif" name="Image46" id="Image46" /></a></td>
          <td width="133"><a href="javascript:selectArea('002','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image47','','/images/portal/homepage_renewal/offline/tab_on_04.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_04.gif" name="Image47" id="Image47" /></a></td>
          <td width="133"><a href="javascript:selectArea('003','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image48','','/images/portal/homepage_renewal/offline/tab_on_05.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_05.gif" name="Image48" id="Image48" /></a></td>
          <%}else if(box.get("p_area").equals("001")){ %>
          <td width="133"><a href="javascript:selectArea('','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image44','','/images/portal/homepage_renewal/offline/tab_on_01.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_01.gif" name="Image44" id="Image44" /></a></td>
          <td width="133"><a href="javascript:selectArea('004','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image45','','/images/portal/homepage_renewal/offline/tab_on_02.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_02.gif" name="Image45" id="Image45" /></a></td>
          <td width="133"><a href="javascript:selectArea('001','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image46','','/images/portal/homepage_renewal/offline/tab_on_03.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_on_03.gif" name="Image46" id="Image46" /></a></td>
          <td width="133"><a href="javascript:selectArea('002','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image47','','/images/portal/homepage_renewal/offline/tab_on_04.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_04.gif" name="Image47" id="Image47" /></a></td>
          <td width="133"><a href="javascript:selectArea('003','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image48','','/images/portal/homepage_renewal/offline/tab_on_05.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_05.gif" name="Image48" id="Image48" /></a></td>
          <%}else if(box.get("p_area").equals("002")){ %>
          <td width="133"><a href="javascript:selectArea('','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image44','','/images/portal/homepage_renewal/offline/tab_on_01.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_01.gif" name="Image44" id="Image44" /></a></td>
          <td width="133"><a href="javascript:selectArea('004','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image45','','/images/portal/homepage_renewal/offline/tab_on_02.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_02.gif" name="Image45" id="Image45" /></a></td>
          <td width="133"><a href="javascript:selectArea('001','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image46','','/images/portal/homepage_renewal/offline/tab_on_03.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_03.gif" name="Image46" id="Image46" /></a></td>
          <td width="133"><a href="javascript:selectArea('002','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image47','','/images/portal/homepage_renewal/offline/tab_on_04.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_on_04.gif" name="Image47" id="Image47" /></a></td>
          <td width="133"><a href="javascript:selectArea('003','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image48','','/images/portal/homepage_renewal/offline/tab_on_05.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_05.gif" name="Image48" id="Image48" /></a></td>
          <%}else if(box.get("p_area").equals("003")){ %>
          <td width="133"><a href="javascript:selectArea('','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image44','','/images/portal/homepage_renewal/offline/tab_on_01.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_01.gif" name="Image44" id="Image44" /></a></td>
          <td width="133"><a href="javascript:selectArea('004','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image45','','/images/portal/homepage_renewal/offline/tab_on_02.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_02.gif" name="Image45" id="Image45" /></a></td>
          <td width="133"><a href="javascript:selectArea('001','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image46','','/images/portal/homepage_renewal/offline/tab_on_03.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_03.gif" name="Image46" id="Image46" /></a></td>
          <td width="133"><a href="javascript:selectArea('002','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image47','','/images/portal/homepage_renewal/offline/tab_on_04.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_off_04.gif" name="Image47" id="Image47" /></a></td>
          <td width="133"><a href="javascript:selectArea('003','');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image48','','/images/portal/homepage_renewal/offline/tab_on_05.gif',1)"><img src="/images/portal/homepage_renewal/offline/tab_on_05.gif" name="Image48" id="Image48" /></a></td>
          <%} %>
        </tr>
      </table>
      <!--탭부분//-->
      
      <!--테이블부분-->
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <!--<td class="tit_table">분야
          <kocca_select:select name="s_mclassnm" sqlNum="course.0006"  param='<%= type %>'
					onChange='go(1);' attr="분야" selectedValue='<%= box.get("s_mclassnm") %>' isLoad="true" all="true" type="3" styleClass=""/>
		  </td>-->
		  <td class="tit_table">과정명</td>
          <td class="tit_table">교육비</td>
          <td class="tit_table">신청기간</td>
          <td class="tit_table"><a href="javascript:goSort('date')">교육기간</a></td>
          <td class="tit_table_right">수강신청</td>
        </tr>
                
        
        <%	for(DataBox dbox : subjList) { 
    		String biyong = "";
    		String slimit = "";
    		String propyn = dbox.getString("d_propyn");
    		if( dbox.getInt("d_biyong") == 0 ) {
    			biyong = "무료";
    		} else {
    			biyong = dbox.getCommaInt("d_biyong") + "원";
    		}
    		if( dbox.getInt("d_studentlimit") == 0 ) {
    			slimit = "제한없음";
    		} else {
    			slimit = dbox.getCommaInt("d_studentlimit") + "명";
    		}
		%>
        
        <tr>
          <!--<td class="ct_tit_table"><%=dbox.getFromDBTypeString("MIDDLECLASSNM")%></td>-->
          <td class="ct_tit_table_left">
            <table border="0" cellspacing="0" cellpadding="2">
              <tr>
                <td valign="top">
                	<% if(dbox.get("d_isunit").equals("Y")) {%><img src="/images/portal/homepage_renewal/online/icon_org.gif" alt="인증" /> <% } %>
					<% if(dbox.get("d_ishit").equals("Y")) {%><img src="/images/portal/homepage_renewal/online/icon_puple.gif" alt="인기" /> <% } %>
					<% if(dbox.get("d_isnew").equals("Y")) {%><img src="/images/portal/homepage_renewal/online/icon_blue.gif" alt="신규" /> <% } %>
					<% if(dbox.get("d_isrecom").equals("Y")) {%><img src="/images/portal/homepage_renewal/online/icon_green.gif" alt="추천" /> <% } %>
					<!-- 관심과정담기가 필요시 다시 복구<a href="javascript:fnInterestInsert(
									'<%=dbox.get("d_subj")%>',
									'<%=dbox.get("d_subjnm")%>',
									'<%=dbox.get("d_isonoff")%>',
									'<%=dbox.get("d_scupperclass")%>',
									'<%=dbox.get("d_uclassnm")%>')"
									class="board_btn"><span class="pd_l6 pd_r5">관심과정담기</span></a> -->							
                </td>
                <td>
                	<% if(dbox.get("d_areaname").length()>0) out.println("<b>[" + dbox.get("d_areaname") + "]</b>"); %><a href="javascript:whenSubjInfo(
									'<%=dbox.get("d_subj")%>',
									'<%=dbox.get("d_year")%>',
									'<%=dbox.get("d_subjseq")%>',
									'<%=dbox.get("d_seq")%>')"><%=dbox.get("d_subjnm")%></a>
					</a>
                </td>
              </tr>
            </table></td>
          <td class="ct_tit_table"><a href="javascript:goSort('biyong')"><%= biyong %></a></td>
          <td class="ct_tit_table"><%=dbox.getDate("d_propstart", ".").substring(5,10)%>~<%=dbox.getDate("d_propend", ".").substring(5,10)%></td>
          <td class="ct_tit_table"><%=dbox.getDate("d_edustart", ".").substring(5,10)%>~<%=dbox.getDate("d_eduend", ".").substring(5,10)%></td>
          <td class="ct_tit_table_right"> 
          	<%if(dbox.get("d_me_sukang_yn").equals("Y")) {%>신청완료<%	}	else if( dbox.get("d_isprop").equals("Y") ) { %>
			<a href="javascript:whenSubjPropose(
									'<%=dbox.get("d_subj")%>',
									'<%=dbox.get("d_year")%>',
									'<%=dbox.get("d_subjseq")%>',
									'<%=dbox.get("d_subjnm")%>',
									'<%=dbox.get("d_seq")%>')" class="btn_gr mg_t3">
				<img src="/images/portal/homepage_renewal/online/btn_click.gif" alt="신청" />
					<% } else { %>
					    &nbsp;
					<% } %>
			</a>
          </td>
          </td>
        </tr>       
	
	<%	v_totalpage	 = dbox.getInt("d_totalpage");// for 루프 안에 삽입
		v_totalrowcount = dbox.getInt("d_totalrowcount");
		v_rowcount	  = dbox.getInt("d_rowcount");
	}	 %>
        
        <%
	if( subjList.size() < 1) {
%>
		<tr>
			<td colspan="5" align="center">등록된 과정이 없습니다.</td>
		</tr>
<%
	}
%>
        
        <tr>
          <td height="1" colspan="6" bgcolor="CACACA"></td>
        </tr>
        <tr>
          <td height="4" colspan="6" bgcolor="F7F7F7"></td>
        </tr>
      </table>
      
      <!-- total 시작 -->
		 <%= PageUtil.re_printPageSizeListDiv(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
		<!-- total 끝 -->
		
		</td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
      
      			      
		      </tr>		
		</table>
	
	<%}else{ %>	
	
<% if ("S01".equals(type)) { %>			<h2><img src="/images/portal/menu/2/h2_tit1.gif" alt="방송영상" class="fl_l" /><p class="category">Home &gt; 오프라인과정 &gt; <strong>방송영상</strong></p></h2><% }
else if ("S02".equals(type)) { %>			<h2><img src="/images/portal/menu/2/h2_tit2.gif" alt="게임개발" class="fl_l" /><p class="category">Home &gt; 오프라인과정 &gt; <strong>게임제작</strong></p></h2><% }
else if ("S03".equals(type)) { %>			<h2><img src="/images/portal/menu/2/h2_tit3.gif" alt="문화콘텐츠" class="fl_l" /><p class="category">Home &gt; 오프라인과정 &gt; <strong>기획창작</strong></p></h2><% } %>

    <% if(p_menuid.equals("01") && p_middleclass.equals("001")) { %>
        <p class="board_comment">산업계 재직자를 대상으로 미디어 환경 변화에 맞는 콘텐츠 창작역량과 글로벌 경쟁력강화를 목적으로 창작 기술력 향상</p>
    <% } else if(p_menuid.equals("01") && p_middleclass.equals("002")) { %>
        <p class="board_comment">미래콘텐츠 사업을 이끌 청년 리더를 대상으로 창작역량과 디지털 콘텐츠 제작 기술력을 갖춘 대한민국 1%의 창의 인재 양성</p>
    <% } else if(p_menuid.equals("01") && p_middleclass.equals("003")) { %>
        <p class="board_comment">미디어 융합에 따른 직능별 교육 요구와 필요에 부합하는 맞춤형 커리큘럼을 개발하여 위탁사의 자작환경에 부합하는 최상의 교육서비스를 제공하는 고객맞춤형 교육프로그램</p>
    <%}%>
			<p class="searchbox">
				<%--<img src="/images/user/portal/course/serbox_character.gif" alt="과정특성" class="mg_r7" />--%>
				 <%--<kocca_select:select name="s_specials" sqlNum="specials"  param=" "  styleClass="mg_r12"--%>
	<%--onChange="" attr=" " selectedValue="<%= p_specials %>" isLoad="true" all="true" />--%>
				<img src="/images/portal/board/serbox_classtit.gif" alt="과정검색" class="mg_r7" />
				<input type="text" name="s_subjnm" class="inbox" style="width:280px;" value="<%= box.get("s_subjnm") %>" />
				<input type="image" src="/images/portal/btn/btn_serbox_search.gif" class="va_m mg_r12" alt="검색" />
			</p>
			<ul class="tabwrap">
				<li><a href="javascript:selectArea('','');" class="tab_online<%= box.get("type").equals("") ? " tab_online_on" : "" %>"><span>전체</span></a></li>
				<li><a href="javascript:selectArea('S01','01');" class="tab_online<%= box.get("type").equals("S01") ? " tab_online_on" : "" %>"><span>방송영상</span></a></li>
				<li><a href="javascript:selectArea('S02','02');" class="tab_online<%= box.get("type").equals("S02") ? " tab_online_on" : "" %>"><span>게임제작</span></a></li>
				<li><a href="javascript:selectArea('S03','03');" class="tab_online<%= box.get("type").equals("S03") ? " tab_online_on" : "" %>"><span>기획창작</span></a></li>
			</ul>

			<table class="row_list">
			<thead>
			<tr>
			 
				<th>
					<kocca_select:select name="s_mclassnm" sqlNum="course.0006"  param='<%= type %>'
					onChange='go(1);' attr=" 분야" selectedValue='<%= box.get("s_mclassnm") %>' isLoad="true" all="true" type="3" styleClass=""/>
				</th>
				<!--
				<th>
					<kocca_select:select name="s_sclowerclass" sqlNum="course.0004"  param='<%= type.concat("01") %>' param2='<%= box.get("s_mclassnm").trim().length()==0 ? "001" : box.get("s_mclassnm")%>'
					onChange="go(1);" attr=" 난이도" selectedValue='<%= box.get("s_sclowerclass") %>' isLoad="true" all="true" type="3" styleClass=""/>
				</th>
			 -->
				<th>과정명</th>
				<th>교육기간
				<!-- 	<br /><span class="stit">(수강신청기간)</span> -->
				</th>
				<th>수강신청</th>
			</tr>
			</thead>
			<tbody>
<%	
	for(DataBox dbox : subjList) {		%>
			<tr>
			 
				<td class="ta_l"><%=dbox.getFromDBTypeString("MIDDLECLASSNM")%></td>
			<!--	<td><%= dbox.getFromDBTypeString("LOWERCLASSNM")%></td>
			 -->
				<td class="ta_l">
					<% if(dbox.get("d_isunit").equals("Y")) {%><img src="/images/portal/ico/ico_box_witness.gif" alt="인증" /> <% } %>
					<% if(dbox.get("d_ishit").equals("Y")) {%><img src="/images/portal/ico/ico_box_popular.gif" alt="인기" /> <% } %>
					<% if(dbox.get("d_isnew").equals("Y")) {%><img src="/images/portal/ico/ico_box_new.gif" alt="신규" /> <% } %>
					<% if(dbox.get("d_isrecom").equals("Y")) {%><img src="/images/portal/ico/ico_box_recom.gif" alt="추천" /> <% } %>
					<!-- 관심과정담기가 필요시 다시 복구<a href="javascript:fnInterestInsert(
									'<%=dbox.get("d_subj")%>',
									'<%=dbox.get("d_subjnm")%>',
									'<%=dbox.get("d_isonoff")%>',
									'<%=dbox.get("d_scupperclass")%>',
									'<%=dbox.get("d_uclassnm")%>')"
									class="board_btn"><span class="pd_l6 pd_r5">관심과정담기</span></a> -->
									<br />
					<% if(dbox.get("d_areaname").length()>0) out.println("<b>[" + dbox.get("d_areaname") + "]</b>"); %><a href="javascript:whenSubjInfo(
									'<%=dbox.get("d_subj")%>',
									'<%=dbox.get("d_year")%>',
									'<%=dbox.get("d_subjseq")%>',
									'<%=dbox.get("d_seq")%>')"><%=dbox.get("d_subjnm")%></a><br />
					<span class="slist">수강료: <%=dbox.getCommaInt("d_biyong")%>원</span><span class="slist">모집인원: <%= dbox.getCommaInt("d_studentlimit") %>명</span></td>
				<td class="data"><%=dbox.getDate("d_edustart", ".")%>~<%=dbox.getDate("d_eduend", ".")%>
				<!-- 	<br /><span class="sdata"><%=dbox.getDate("d_propstart", ".")%>~<%=dbox.getDate("d_propend", ".")%></span> -->
				</td>
				<td class="end">
                    <% if(dbox.get("d_me_sukang_yn").equals("Y")) {%>
                        신청완료
                    <% } else if( dbox.get("d_isprop").equals("Y") ) { %>
					<a href="javascript:whenSubjPropose(
									'<%=dbox.get("d_subj")%>',
									'<%=dbox.get("d_year")%>',
									'<%=dbox.get("d_subjseq")%>',
									'<%=dbox.get("d_subjnm")%>',
									'<%=dbox.get("d_seq")%>')" class="btn_gr mg_t3"><span>신청</span></a><br/>
					<% } else { %>
					    &nbsp;
					<% } %>
				</td>
			</tr>
<%	v_totalpage	 = dbox.getInt("d_totalpage");// for 루프 안에 삽입
		v_totalrowcount = dbox.getInt("d_totalrowcount");
		v_rowcount	  = dbox.getInt("d_rowcount");
	}	 %>
<%
	if( subjList.size() < 1) {
%>
		<tr>
			<td colspan="5" align="center">등록된 과정이 없습니다.</td>
		</tr>
<%
	}
%>
		</tbody>
			</table>
		<!-- total 시작 -->
		 <%= PageUtil.printPageSizeListDiv(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
		<!-- total 끝 -->
		<%} %>
			</form>
<!-- form 끝 -->
<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->
