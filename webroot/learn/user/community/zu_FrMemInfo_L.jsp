<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process      = box.getStringDefault("p_process","movememberactivityPage");
    String  v_cmuno      = box.getString("p_cmuno");//마스터 관련페이지는 무조건 들어가야함.
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

	String  v_close_fg	 = box.getString("d_close_fg");

    int v_dispnum    = 0;
    int v_totalpage  = 0;
    int v_rowcount   = 1;
    int v_upfilecnt  = 0;
    int v_pageno             = box.getInt("p_pageno");
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));

    ArrayList list       = (ArrayList)request.getAttribute("list");
	// 탈퇴한 회원정보는 가져오지 않는다.
%>  
  
<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//페이지이동
function goPage(pageNum) {
    document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.community.CommunityFrMemberServlet";
    document.form1.p_process.value = "moveMemInfoData";
    document.form1.submit();
}

//조회
function uf_searchOK(pageNum) {
    if(document.form1.p_searchtext.value ==''){
       alert('검색어를 입력하여야합니다.');document.form1.p_searchtext.focus();return;
    }

    document.form1.p_pageno.value  = pageNum;
    document.form1.action          = "/servlet/controller.community.CommunityFrMemberServlet";
    document.form1.p_process.value = "moveMemInfoData";
    document.form1.submit();
}

//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_cmuno"      value = "<%=v_cmuno%>">
<input type = "hidden" name = "p_pageno"      value = "">

                  <!-- 전체타이틀 -->
                  <table width="652" border="0" cellspacing="0" cellpadding="0">
                   <tr> 
                    <td height="1" bgcolor="CCCCCC"></td>
                   </tr>
                   <tr> 
                    <td>
                     <table width="407" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                       <td valign="bottom" class="title"><strong>커뮤니티 회원정보</strong> <img src="../../images/community/title_vline.gif" align="absbottom"></td>
                      </tr>
                     </table>
                    </td>
                   </tr>
                   <tr> 
                    <td height="1" bgcolor="CCCCCC"></td>
                   </tr>
                   <tr> 
                    <td height="10"></td>
                   </tr>
                  </table>
                  <!-- 게시판 리스트 -->
                  <table width="652" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                   <tr  class="lcolor"><td height="3" colspan="6"></td></tr>
                   <tr> 
                     <td width="25%" >성명</td>
                     <td width="25%" >이메일</td>
                     <td width="12%" >등급</td>
                     <td width="15%" >전화번호</td>
                     <td width="13%" >가입일</td>
                   </tr>

<%  if(list.size() != 0){
    for(int i = 0; i < list.size(); i++) {
        DataBox dbox = (DataBox)list.get(i);
        v_totalpage  = dbox.getInt("d_totalpage");

        String v_title  =dbox.getString("d_title");
        String v_name   =dbox.getString("d_kor_name");

        if (!v_searchtext.equals("")&&v_select.equals("userid")) {
            v_title = StringManager.replace(dbox.getString("d_userid"), v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
        } else if(!v_searchtext.equals("")&&v_select.equals("kor_name")){
            v_name = StringManager.replace(dbox.getString("d_kor_name"), v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
        }

%>

                   <tr>
                    <td  <%if((i/2)==0){out.print("class='tbl_gleft'");}else{out.print("class='tbl_gleft_back'");}%>class="tbl_gleft"><%=v_name%>&nbsp; <%=dbox.getString("d_jikupnm")%>/<%=dbox.getString("d_jikwinm")%>&nbsp;<%=dbox.getString("d_deptnam")%></td>
                    <td  <%if((i/2)==0){out.print("class='tbl_gleft'");}else{out.print("class='tbl_gleft_back'");}%>class="tbl_gleft"><%=dbox.getString("d_email")%></td>
                    <td  <%if((i/2)==0){out.print("class='tbl_grc'");  }else{out.print("class='tbl_gc_back'");}%>class="tbl_grc"><%=dbox.getString("d_grade_kor_nm")%></td>
                    <td  <%if((i/2)==0){out.print("class='tbl_gleft'");  }else{out.print("class='tbl_gleft_back'");}%>class="tbl_grc"><%=dbox.getString("d_office_tel")%></td>
                    <td  <%if((i/2)==0){out.print("class='tbl_grc'");  }else{out.print("class='tbl_gc_back'");}%>class="tbl_grc"><%=FormatDate.getFormatDate(dbox.getString("d_license_dte"), "yyyy/MM/dd")%></td>
                   </tr>
<%    }
    } else {%>
                   <tr> 
                     <td align="center" class="tbl_grc" colspan=6>활동중인 회원이 존재하지 않습니다..</td>
                   </tr>
<% }%>
                  </table>

                              <%= CommunityQnABean.printPageList(v_totalpage, v_pageno, row) %>
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료 -->
