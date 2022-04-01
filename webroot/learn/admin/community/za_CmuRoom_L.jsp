<%
//**********************************************************
//  1. 제      목: 커뮤니티 리스트
//  2. 프로그램명 : za_CmuRoom_L.jsp
//  3. 개      요: 공지사항 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성:  2003. 7. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    String  v_if_close_fg   = box.getStringDefault("p_if_close_fg","1");
	// '1'은  승인된 커뮤니티    '4' 폐쇄신청 커뮤니티 
	// '0'은 커뮤니티 신청상태,, '3'은 폐쇄된 커뮤니티
    String  v_orderby    = box.getStringDefault("p_orderby","cmu_nm");

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");


    int v_dispnum    = 0; // 총게시물수
    int v_totalpage  = 0;
    int v_rowcount   = 1;
    int v_upfilecnt  = 0;
    int v_pageno             = box.getInt("p_pageno");
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));


    ArrayList list       = (ArrayList)request.getAttribute("list");
   
%>
<html>
<head>
<title>eIBIs</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

//페이지이동
function goPage(pageNum, close_fg) {
    document.form1.p_pageno.value = pageNum;
	document.form1.p_if_close_fg.value = close_fg;
    document.form1.action = "/servlet/controller.community.CommunityAdminRoomServlet";
    document.form1.p_process.value = "listPage";
    document.form1.submit();
}

function goPage(pageNum) {
    document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.community.CommunityAdminRoomServlet";
    document.form1.p_process.value = "listPage";
    document.form1.submit();
}

//검색
function uf_searchOK(pageNum) {
    if(document.form1.p_searchtext.value ==''){
       alert('검색어를 입력하여야합니다.');document.form1.p_searchtext.focus();return;
    }

    document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.community.CommunityAdminRoomServlet";
    document.form1.p_process.value = "listPage";
    document.form1.submit();
}

//보기페이지로이동
function uf_viewOK(cmuno,rowseq,v_close_fg) {

	if(v_close_fg == "3"){
		alert(" 폐쇄중입니다 "); return;
	}else{

    document.form1.p_cmuno.value = cmuno;
    document.form1.action = "/servlet/controller.community.CommunityAdminRoomServlet";
    document.form1.p_process.value = "viewPage";
    document.form1.submit();
	}
}

//인기커뮤니티 저장
function uf_HoldOK() {
//	document.form1.p_cmuno.value = cmuno;
//	var v_cmu = [, , , , , , , , ] ;
//	v_cmu += cmuno;
//	alert(' 벡터 cmu '+v_cmu);

   document.form1.action = "/servlet/controller.community.CommunityAdminRoomServlet";
   document.form1.p_process.value = "holdData";
   document.form1.submit();
}

//폐쇄커뮤니티 저장
function uf_CloseOK() {

    document.form1.action = "/servlet/controller.community.CommunityAdminRoomServlet";
    document.form1.p_process.value = "frCloseData";
    document.form1.submit();
}
// 선택정렬
function whenOrder(Column){
	document.form1.p_orderby.value=Column;
	alert(' 정렬하러갑세 !!  '+document.form1.p_orderby.value);

    document.form1.action = "/servlet/controller.community.CommunityAdminRoomServlet";
	document.form1.p_process.value = "listPage";
	document.form1.submit();
}

//승인 및 거부
function uf_acceptOK(p_close_fg) {

    if(uf_cbxCnt()<1){
        alert("선택된 자료가 없습니다.");return;
    }
   //open_window('adminSendMail', '/servlet/controller.community.CommunityAdminRoomServlet?p_process=sendmailPage&p_token_cmuno='+uf_checkval()+'&p_close_fg='+p_close_fg, 100, 100, 810, 480, 'no', 'no', 'no', 'no', 'no');

	document.form1.action = "/servlet/controller.community.CommunityAdminRoomServlet";
	document.form1.p_close_fg.value = p_close_fg;
	document.form1.p_token_cmuno.value = uf_checkval();
    document.form1.p_process.value = "acceptData";
    
	document.form1.submit();
}

function uf_allCheck(){
   var rowcnt = <%=list.size()%>;
   if(rowcnt>0){
      if(document.form1.allcheck.checked == true){
          if(rowcnt ==1){
             document.form1.p_cbx_cmuno.checked=true;
          } else {
             for(var i=0;i<rowcnt;i++){
                document.form1.p_cbx_cmuno[i].checked=true;
             }  
          }
      }else {
          if(rowcnt ==1){
             document.form1.p_cbx_cmuno.checked=false;
          } else {
             for(var i=0;i<rowcnt;i++){
                document.form1.p_cbx_cmuno[i].checked=false;
             }  
          }

      }
   }
}

function uf_checkval() {
   var rowcnt = <%=list.size()%>;
   var token_cmuno='';

   if(rowcnt>0){
          if(rowcnt ==1){
             if(document.form1.p_cbx_cmuno.checked==true){
                token_cmuno =token_cmuno +document.form1.p_cbx_cmuno.value+"/";
             }
          } else {
             for(var i=0;i<rowcnt;i++){
                 if(document.form1.p_cbx_cmuno[i].checked==true){
                    token_cmuno =token_cmuno +document.form1.p_cbx_cmuno[i].value+"/";
                 }
             }  
          }
   }
   return token_cmuno;
}

function uf_cbxCnt() {
   var rowcnt = <%=list.size()%>;
   var userid="";
   var kor_name="";
   var email="";
   var cnt=0;

   if(rowcnt>0){
          if(rowcnt ==1){
             if(document.form1.p_cbx_cmuno.checked==true){
                 cnt++;
             }
          } else {
             for(var i=0;i<rowcnt;i++){
                 if(document.form1.p_cbx_cmuno[i].checked==true){
                     cnt++;
                 }
             }  
          }
   }
   return cnt;
}

//검색
	function whenGrtype(p_action) {
	  document.form1.action='/servlet/controller.community.CommunityAdminRoomServlet';
	  document.form1.p_process.value = 'listPage';    
	  document.form1.submit();
	}

	// 검색조건 체크
	function chkParam() {
	  if (document.form1.p_grtype.value == '' || document.form1.p_grtype.value == '----' || document.form1.p_grtype.value == '') {
		alert("교육그룹을 선택하세요.");
		return false;
	  }
	}
//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_pageno"      value = "">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_upfilecnt"   value = "">
<input type = "hidden" name = "p_token_cmuno" value = "">
<input type = "hidden" name = "p_close_fg"    value = "">
<input type = "hidden" name = "p_cmuno"       value="">
<input type = "hidden" name = "p_rowseq"      value="">
<input type = "hidden" name = "p_orderby"     value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
 <tr>
  <td align="center" valign="top">
   <!----------------- title 시작 ----------------->
   <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
    <tr> 
     <td><img src="../../../images/admin/homepage/tit_comm_man.gif" ></td>
     <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
    </tr>
   </table>
   <!----------------- title 끝 ----------------->
   <br>
   <!-----------------  테이블시작 ----------------->
   <br> 
   <table width="97%" border="0" cellpadding="0" cellspacing="0" >
    <tr> 
	 <td align="left"><!-- 
		<% //if (StringManager.substring(box.getSession("gadmin"), 0, 1).equals("A")) { %> 
				<%=CodeConfigBean.getCodeGubunSelect("0060","",1,"p_grtype",box.getString("p_grtype"),"onChange=\"whenGrtype()\"",3) %>
			<%// } else  {                                                   %>
					<%//=CodeConfigBean.getCodeName("grtype",box.getSession("grtype")) %>
					<input type="hidden" name="p_grtype" value="<%=box.getSession("grtype")%>">
			 <% //} %> -->
	 </td>
     <td align="right">&nbsp;
	 <input type=radio name="p_if_close_fg" value="0" <% if("0".equals(v_if_close_fg))out.print(" checked");%> onclick="javascript:goPage(1,0);">신청
     
     <input type=radio name="p_if_close_fg" value="1" <% if("1".equals(v_if_close_fg))out.print(" checked");%>  onclick="javascript:goPage(1,1);">승인

	 <input type=radio name="p_if_close_fg" value="4" <% if("4".equals(v_if_close_fg))out.print(" checked");%>  onclick="javascript:goPage(1,4);">폐쇄신청

	 <input type=radio name="p_if_close_fg" value="5" <% if("5".equals(v_if_close_fg))out.print(" checked");%>  onclick="javascript:goPage(1,5);">전체커뮤니티

     &nbsp;&nbsp;&nbsp;
     </td>
     <!--td width="79"><img src="../../images/community/sear_su.gif"></td>
     <td width="90">
	   <select name="p_orderby" onchange="javascript:goPage(1);">
         <option value="cmu_nm"      <%if("cmu_nm".equals(v_orderby)) out.print(" selected");%>>커뮤니티명</option>
         <option value="kor_name"    <%if("kor_name".equals(v_orderby)) out.print(" selected");%>>시샵</option>
         <option value="accept_dte"  <%if("accept_dte".equals(v_orderby)) out.print(" selected");%>>개설일자</option>
		<% if("5".equals(v_if_close_fg)){ %>
		 <option value="close_fg"  <%if("close_fg".equals(v_orderby)) out.print(" selected");%>>커뮤니티상태</option>
		<% }%>
       </select></td-->
    </tr>
    <tr bgcolor="#FFFFFF"> 
     <td height="5" colspan="2"></td>
    </tr>
   </table>

   <table class="table_out" cellspacing="1" cellpadding="5">
    <tr><td colspan="8" class="table_top_line"></td></tr>
	<tr> 
	<%-- 05.12.07 이나연 추가 --%>
	<%if("5".equals(v_if_close_fg)){%>
	  <td width="5%" class="table_title"><b>NO</b></td>
      <td width="23%" class="table_title"><a href="javascript:whenOrder('cmu_nm')"><b>커뮤니티 명</b></td>
      <td width="10%" height="25" class="table_title"><a href="javascript:whenOrder('register_userid')"><strong>방장</strong></td>
      <td width="21%" class="table_title"><a href="javascript:whenOrder('type_l')"><strong>분류</strong></td>
      <td width="8%" class="table_title"><a href="javascript:whenOrder('member_cnt')"><strong>회원수</strong></td>
      <td width="10%" class="table_title"><a href="javascript:whenOrder('close_dte')"><strong>폐쇄일자</strong><strong></strong></td>
      <td width="10%" class="table_title"><a href="javascript:whenOrder('accept_dte')"><strong>승인일자</strong><strong></strong></td>
      <td width="15%" class="table_title"><a href="javascript:whenOrder('close_fg')"><strong>커뮤니티상태</strong><strong></strong></td>
	<%}else{%>
      <td width="3%" class="table_title"><input type="checkbox" name="allcheck" value="checkbox" onclick="javascript:uf_allCheck();"></td>
      <td width="37%" class="table_title"><a href="javascript:whenOrder('cmu_nm')"><b>커뮤니티 명</b></td>
      <td width="10%" height="25" class="table_title"><a href="javascript:whenOrder('register_userid')"><strong>방장</strong></td>
      <td width="21%" class="table_title"><a href="javascript:whenOrder('type_l')"><strong>분류</strong></td>
      <td width="8%" class="table_title"><a href="javascript:whenOrder('member_cnt')"><strong>회원수</strong></td>
      <td width="7%" class="table_title"><a href="javascript:whenOrder('register_dte')"><strong>신청일자</strong><strong></strong></td>
      <td width="7%" class="table_title"><a href="javascript:whenOrder('accepr_dte')"><strong>승인일자</strong><strong></strong></td>
      <td width="7%" class="table_title"><a href="javascript:whenOrder('hold_fg')"><strong>인기커뮤니티</strong><strong></strong></td>
		  <% }%>
    </tr>
<%  if(list.size() != 0){
       for(int i = 0; i < list.size(); i++) {
           DataBox dbox = (DataBox)list.get(i);
		   v_totalpage = dbox.getInt("d_totalpage");
		   v_dispnum   = dbox.getInt("d_dispnum");

           String v_cmu_nm			 = dbox.getString("d_cmu_nm");
           String v_intro			 = dbox.getString("d_intro");
           String v_kor_name		 = dbox.getString("d_kor_name");
		   String v_cmuno			 = dbox.getString("d_cmuno");
		   String v_date			 = dbox.getString("d_accept_dte");
   		   String v_accept_userid	 = dbox.getString("d_accept_userid");
		   String v_display_fg="공개";

		   if( 0<v_dispnum ){  // Paging ERROR 

           if("1".equals(dbox.getString("d_display_fg"))) v_display_fg="공개";
           if("2".equals(dbox.getString("d_display_fg"))) v_display_fg="비공개";

           if (!v_searchtext.equals("")&&v_select.equals("cmu_nm")) {
               v_cmu_nm = StringManager.replace(dbox.getString("d_cmu_nm"), v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
           } else if(!v_searchtext.equals("")&&v_select.equals("intro")){
               v_intro = StringManager.replace(dbox.getString("d_intro"), v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
           } else if(!v_searchtext.equals("")&&v_select.equals("kor_name")){
               v_kor_name = StringManager.replace(dbox.getString("d_kor_name"), v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
           }
		   // 05.12.07 이나연 추가 _ 전체커뮤니티 상태보기
		   String v_close_fg="개설";
		  
		   if("1".equals(dbox.getString("d_close_fg"))) v_close_fg="개설";
   		   if("0".equals(dbox.getString("d_close_fg"))) v_close_fg="승인신청중";
   		   if("4".equals(dbox.getString("d_close_fg"))) v_close_fg="폐쇄신청중";
   		   if("3".equals(dbox.getString("d_close_fg"))){
			   if(v_date.equals("")){
					v_close_fg="승인취소";					
			   } else {
				    v_close_fg="폐쇄완료";
			   }
		   }

%>

    <tr>
	<%//-- 05.12.07 이나연 추가 --%>
	<%if("5".equals(v_if_close_fg)){%>
      <td align="center" bgcolor="#EEEEEE" height="25"><%=v_dispnum%></td>
      <td  class="table_02_2"><a href="javascript:uf_viewOK('<%=v_cmuno%>','1','<%=dbox.getString("d_close_fg")%>')"><%=v_cmu_nm%></td>
      <td align="center" class="table_02_1"><%=v_kor_name%> </td>
      <td align="center" class="table_02_1"><%=dbox.getString("d_type_l_nm")%> ==> <%=dbox.getString("d_type_m_nm")%></td>
      <td align="center" class="table_02_1"><%=dbox.getString("d_member_cnt")%> 명</td>
      <td align="center" class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_close_dte"), "yyyy/MM/dd")%></td>
      <td align="center" class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_accept_dte"), "yyyy/MM/dd")%></td>
		  <!-- 커뮤니티 상태 -->
      <td align="center" class="table_02_1"><%=v_close_fg%></td>
	<%}else{%> 
      <td align="center" bgcolor="#EEEEEE" height="25"><input type="checkbox" name="p_cbx_cmuno" value="<%=dbox.getString("d_cmuno")%>"></td>
      <td  class="table_02_2"><a href="javascript:uf_viewOK('<%=v_cmuno%>','1','<%=dbox.getString("d_close_fg")%>')"><%=v_cmu_nm%></a></td>
      <td align="center" class="table_02_1"><%=v_kor_name%> </td>
      <td align="center" class="table_02_1"><%=dbox.getString("d_type_l_nm")%>--><%=dbox.getString("d_type_m_nm")%></td>
      <td align="center" class="table_02_1"><%=dbox.getString("d_member_cnt")%> 명</td>
      <td align="center" class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_register_dte"), "yyyy/MM/dd")%></td>
      <td align="center" class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_accept_dte"), "yyyy/MM/dd")%></td>
      <td align="center" class="table_02_1"><select name="p_hold_fg">
      <option value="1"   <%if("1".equals(dbox.getString("d_hold_fg"))) out.print(" selected");%>>YES</option>
      <option value="0" <%if("0".equals(dbox.getString("d_hold_fg"))) out.print(" selected");%>>NO</option>
                              </select>      
	  <input type="hidden" name="p_all_cmuno" value="<%=dbox.getString("d_cmuno")%>"></td>
	<%}%>

    </tr>
<%		}
	}// for구문
 }else{				%>
	 <tr>
		<td colspan="8" class="table_02_1">해당 자료가 없습니다.</td>
	 </tr>
<% } %>
   </table> 
   <!----------------- 테이블 끝 ----------------->

   <!----------------- total 시작 ----------------->
   <br>
   <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width=80% align="right" valign="absmiddle"> 
        <%= PageUtil.printPageList(v_totalpage, v_pageno, 10) %>
      </td>
<%   if (list.size() >0){%>
<% if("0".equals(v_if_close_fg)){%>
	  <!-- 신청 -->
      <td width=20% align=right><a href="javascript:uf_acceptOK(1);"><img src="/images/admin/button/btn_approve.gif" border = "0"></a> 
      <a href="javascript:uf_acceptOK(4);"><img src="/images/admin/button/btn_ap_cancel.gif" border = "0"></a></td>

<% }else if("4".equals(v_if_close_fg)){%>
	<!-- 폐쇄신청 -->
	<td width=20% align=right><a href="javascript:uf_acceptOK(3);"><img src="/images/admin/button/btn_ap_cancel.gif" border = "0"></a> 
    <a href="javascript:uf_CloseOK();"><img src="/images/admin/button/btn_apply.gif" border = "0"></a> 
    </td>
<% }else if("1".equals(v_if_close_fg)) {%>
	<!-- 승인 커뮤니티 경우 버튼 -->
      <td width=20% align=right><a href="javascript:uf_acceptOK(3);"><img src="/images/admin/button/btn_ap_cancel.gif" border = "0"></a> 
      <a href="javascript:uf_HoldOK();"><img src="/images/admin/button/btn_apply.gif" border = "0"></a> 
      </td>
<% }else{}%>
	<!-- 폐쇄 커뮤니티 보기 -->
      <!--td width=20% align=right><a href="javascript:uf_acceptOK(3);"><img src="/images/admin/button/btn_ap_cancel.gif" border = "0"></a> 
      <a href="javascript:uf_HoldOK();"><img src="/images/admin/button/btn_apply.gif" border = "0"></a> 
      </td-->
<%   }%>
    </tr>
   </table>
   <!----------------- total 끝 ----------------->
   <br>
  </td>
 </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
