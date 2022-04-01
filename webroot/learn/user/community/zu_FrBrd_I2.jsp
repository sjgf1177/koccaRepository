<%
//**********************************************************
//  1. 제      목: 커뮤니티 QnA 
//  2. 프로그램명 : zu_CmuQnA_L.jsp
//  3. 개      요: 커뮤니티 QnA.
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.07.01 
//  7. 수      정: 2005.07.01 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    String  v_cmuno      = box.getString("p_cmuno");
    String  v_menuno     = box.getString("p_menuno");    
    String  v_brdno      = box.getString("p_brdno");     
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");

    int     v_pageno     = box.getInt("p_pageno");
    int     v_rowseq     = box.getInt("p_rowseq");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);


    String  content      = box.getString("content");
    String  width = "630";
    String  height = "200";

    ArrayList list           = (ArrayList)request.getAttribute("list");
    ArrayList viewfaq        =new ArrayList();
    ArrayList viewfaqfile    =new ArrayList();
    ArrayList viewfaqbefore  =new ArrayList();
    ArrayList viewfaqnext    =new ArrayList();
    ArrayList viewfaqreplay  =new ArrayList();
    
    if(list.size() != 0){
       viewfaq       = (ArrayList)list.get(0);  
       viewfaqfile   = (ArrayList)list.get(1);  
       viewfaqbefore = (ArrayList)list.get(2);  
       viewfaqnext   = (ArrayList)list.get(3);  
       viewfaqreplay = (ArrayList)list.get(4);  

    }
 
    DataBox dbox = (DataBox)viewfaq.get(0);
    content=dbox.getString("d_content");

    //커뮤니티 타입정보
    String v_top_display_fg =( new CommunityCreateBean()).getSingleColumn(box.getString("p_cmuno"),"html_skin_fg");
    if(v_top_display_fg.equals("")) v_top_display_fg="1";
    String[] v_title_color={"","",""};
    if("1".equals(v_top_display_fg)){v_title_color[0]="line_color_blue";v_title_color[1]="tbl_dbtit1";v_title_color[2]="tbl_dbtit2";}
    if("2".equals(v_top_display_fg)){v_title_color[0]="line_color_green";v_title_color[1]="tbl_gtit1" ;v_title_color[2]="tbl_gtit2";}
    if("3".equals(v_top_display_fg)){v_title_color[0]="line_color_pink";v_title_color[1]="tbl_ptit1" ;v_title_color[2]="tbl_ptit2";}
    if("4".equals(v_top_display_fg)){v_title_color[0]="line_color_yellow";v_title_color[1]="tbl_ytit1" ;v_title_color[2]="tbl_ytit2";}
    if("5".equals(v_top_display_fg)){v_title_color[0]="line_color_violet";v_title_color[1]="tbl_vtit1" ;v_title_color[2]="tbl_vtit2";}

%>  


<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../css/user_style_community2.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
//등록
function uf_updateOK() {
    if(document.all.use_editor[0].checked) {        
        form1.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    }else {
        form1.content.value = document.all.txtDetail.value;
    }
    if(document.form1.p_title.value ==''){
       alert('제목을 입력하여야합니다.');document.form1.p_title.focus();return;
    }


    if(document.form1.content.value ==''){
       alert('내용을 입력하여야합니다.');document.form1.content.focus();return;
    }
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "updateData";
    document.form1.submit();
}

//리스트
function wf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}


//파일삭제
function uf_deleteFileOK(fileno) {
    document.form1.p_delfileno.value = fileno;
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "deleteFileData";
    document.form1.submit();
}

//-->
</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
<form name = "form1"     enctype = "multipart/form-data"   method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_cmuno"       value="<%=v_cmuno%>">
<input type = "hidden" name = "p_menuno"      value = "<%=v_menuno%>">
<input type = "hidden" name = "p_brdno"       value = "<%=v_brdno%>">


<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
<input type = "hidden" name = "p_searchtext"  value = "<%=v_searchtext%>">
<input type = "hidden" name = "p_select"      value = "<%=v_select%>">

<input type = "hidden" name = "p_lv"          value = "<%=dbox.getInt("d_lv")%>">
<input type = "hidden" name = "p_position"    value = "<%=dbox.getInt("d_position")%>">
<input type = "hidden" name = "p_parent"      value = "<%=dbox.getInt("d_parent")%>">
<input type = "hidden" name = "p_root"        value = "<%=dbox.getInt("d_root")%>">
<input type = "hidden" name = "p_rowseq"      value = "<%=v_rowseq%>">


<input type = "hidden" name = "p_fileno"      value = "">
<input type = "hidden" name = "p_delfileno"   value = "">
<input type = "hidden" name = "p_brd_fg"       value = "<%=box.getString("p_brd_fg")%>">
<table width="900" cellspacing="3" cellpadding="3">
 <tr>
  <td> 
   <table width="890" border="0" cellspacing="0" cellpadding="0">
    <tr> 
     <td align="right"><a href="/servlet/controller.community.CommunityIndexServlet" onFocus="blur()"><img src="../../images/community/sm_home.gif" border="0"></a></td>
    </tr>
   </table>
   <!-- 맨바깥 테두리선 테이블 -->
   <table width="890" border="1" cellspacing="0" cellpadding="5"   style="border-collapse:collapse;" bordercolor="#B7BEC1">
    <tr>
     <td valign="top">
      <!-- 전체 백그라운드 넣는 테이블-->
      <table width="876" border="0" cellspacing="0" cellpadding="0">
       <tr>
        <%if(s_grtype.equals("KOCCA")){%>
			<td valign="top" background="/images/community/back_total.jpg">
		<%}else if(s_grtype.equals("KGDI")){%>
			<td valign="top" background="/images/user/game/community/back_total.jpg"><%}%>
         <!-- top table -->
         <table width="876" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="168" valign="top"><a href="/servlet/controller.community.CommunityIndexServlet" onfocus="blur()">
					<%if(s_grtype.equals("KOCCA")){%><img src="/images/user/kocca/community/top_img01.jpg" border="0"></a></td><%}else if(s_grtype.equals("KGDI")){%><img src="/images/user/game/community/top_img01.jpg" border="0"></a></td><%}%>	
				<td width="708" valign="top">
					<%if(s_grtype.equals("KOCCA")){%><img src="/images/user/kocca/community/topimg.jpg"></td>
					<%}else if(s_grtype.equals("KGDI")){%><img src="/images/user/game/community/topimg.jpg"></td><%}%>	
			</tr>
		</table>
         <!-- center table start -->
         <table width="876" border="0" cellpadding="0" cellspacing="0">
          <tr> 
           <td valign="top" style="padding-left:5px">
            <table width="860" border="0" cellspacing="0" cellpadding="0">
             <tr> 
              <!-- left -->
              <td width="174"  valign="top" background="../../images/community/bg_left.gif" style="padding-left:5px"> 
               <%@ include file="/learn/user/include/topCommunityB.jsp"%>
              </td>
              <!-- center -->
              <td width="686" rowspan="2" align="center" valign="top" background="../../images/community/bg_center.gif" > 
               <table width="686" border="0" cellspacing="0" cellpadding="0" >
                <tr> 
                 <td><img src="../../images/community/center_top.gif"></td>
                </tr>
               </table>
               <table width="686" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                 <td align="center" valign="top"> 
                  <!-- 커뮤니티main center start-->
                  <!-- 전체타이틀 -->
                  <table width="652" border="0" cellspacing="0" cellpadding="0">
                   <tr> 
                    <td height="1" bgcolor="CCCCCC"></td>
                   </tr>
<% 
   String v_tmp_ename="커뮤니티";
   if("2".equals(box.getString("p_brd_fg")))v_tmp_ename="게시판";
   if("1".equals(box.getString("p_brd_fg")))v_tmp_ename="자료실";
   %>

                      <tr><td valign="bottom" class="title"><%=v_tmp_ename%><img src="../../images/community/title_vline.gif" align="absbottom"><strong><%=(new CommunityMsMenuBean()).getSingleColumn1(v_cmuno,v_menuno,"title")%></strong></td></tr>
                   <tr> 
                    <td height="1" bgcolor="CCCCCC"></td>
                   </tr>
                   <tr> 
                    <td height="10"></td>
                   </tr>
                  </table>

                                    <!--Q&A쓰기 -->
                                    <table width="652" border="2" cellspacing="0" 
            cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
                                      <tr  class="lcolor"> 
                                        <td height="3" colspan="2" class="<%=v_title_color[0]%>"></td>
                                      </tr>
                                      <tr> 
                                        <td width="96" class="<%=v_title_color[1]%>">작성자</td>
                                        <td class="tbl_gleft"><%=s_username%></td>
                                      </tr>
                                      <tr> 
                                        <td class="<%=v_title_color[2]%>" >파일첨부</td>
                                        <td valign="top" class="tbl_btit" ><div id="hdiv_MainBg"> 
<%
      for(int i = 0; i < viewfaqfile.size(); i++) {
          DataBox dboxfile = (DataBox)viewfaqfile.get(i);
%>
        <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= dboxfile.getString("d_savefile")%>&p_realfile=<%= dboxfile.getString("d_realfile")%>'><%=dboxfile.getString("d_realfile")%></a>
        <%if(s_userid.equals(dbox.getString("d_userid"))){%><a href="javascript:uf_deleteFileOK(<%= dboxfile.getString("d_fileno")%>);"><img src="../../images/user/button/btn_del_s.gif" border="0"><%}%></a><br>
        <br>
        <input type = "hidden" name = "p_savefile"  value = "<%=dboxfile.getString("d_savefile")%>">
        <input type = "hidden" name = "p_fileno<%=(i+1)%>"    value = "<%=dboxfile.getString("d_fileno")%>">
        <input type = "hidden" name = "p_file<%=(i+1)%>"    value = "">
<%    }
        for(int j=0;j<Integer.parseInt((String)request.getAttribute("fielcnt"))- viewfaqfile.size();j++){
%>
        <input type = "hidden" name = "p_fileno<%=(j+1+viewfaqfile.size())%>"    value = "">
                                            <input name="p_file<%=(j+1+viewfaqfile.size())%>" id="p_file" type="file" class="input" oncontextmenu="return false" onkeydown="return File_CancelKeyInput();" style="IME-MODE: disabled; HEIGHT: 22px" size="45" /><br>
<%      }%>
                                          </div>

                                        </td>
                                      </tr>
                                      <tr> 
                                        <td  class="<%=v_title_color[1]%>" >제목</td>
                                        <td valign="top" class="tbl_btit" ><input name="p_title" type="text" value="<%=dbox.getString("d_title")%>" class="input2" size="92"></td>
                                      </tr>
                                      <tr><td colspan="2" valign="top" class="comm_contents" ><%@ include file="/include/DhtmlEditor.jsp" %></td></tr>
                                    </table>

                  <!-- 버튼 -->
                  <table width="652" border="0" cellspacing="0" cellpadding="0">
                    <tr><td height="5" align="right"></td></tr>
                    <tr><td align="right"><a href="javascript:uf_updateOK();"><img src="../../images/user/button/btn_mod.gif" border="0"></a> 
                        <a href="javascript:wf_listOK();"><img src="../../images/user/button/btn_list.gif" border="0"></a></td>
                   </tr>
                  </table>

                 </td>
                </tr>
                <tr>
                 <td height="10" align="center" valign="top"></td>
                </tr>
               </table>
              </td>
             </tr>
             <tr>
              <td height="100" valign="bottom" background="../../images/community/bg_left2.gif" style="padding-left:5px">
               <%@ include file="/learn/user/include/topCommunityC.jsp"%>
              </td>
             </tr>
            </table>
            <!-- footer table -->
            <table width="860" border="0" cellspacing="0" cellpadding="0">
			  <tr> 
				<td><img src="/images/community/footer_top.gif"></td>
			  </tr>
			  <tr> 
				<td height="28" align="center" bgcolor="F0F2F0">
					<%if(s_grtype.equals("KOCCA")){%><img src="/images/user/kocca/community/footer_copy.gif">
					<%}else if(s_grtype.equals("KGDI")){%><img src="/images/user/game/community/footer_copy.gif"><%}%>
				</td>
			  </tr>
			</table>
           </td>
          </tr>
         </table>
        </td>
       </tr>
      </table>
     </td>
    </tr>
   </table>
  </td>
 </tr>
</table>
</form>



</body>
</html>
