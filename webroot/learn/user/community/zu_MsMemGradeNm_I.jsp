<%
//**********************************************************
//  1. 제      목: 커뮤니티 회원가입
//  2. 프로그램명 : zu_MsMemJoin_I.jsp
//  3. 개      요: 커뮤니티 회원가입.
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.07.01 
//  7. 수      정: 2005.07.01 
//***********************************************************
%>
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

    String  v_process      = box.getStringDefault("p_process","gomsmemberPop");
    String  v_cmuno      = box.getString("p_cmuno");//마스터 관련페이지는 무조건 들어가야함.
    

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

    ArrayList list       = (ArrayList)request.getAttribute("list");
    ArrayList gradeList  = (ArrayList)request.getAttribute("gradeList");

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
//리스트로이동
function uf_listOK() {
    document.form1.action          = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "selectmsmainPage";
    document.form1.submit();
}


//저장
function uf_saveOK() {
   var rowcnt   =<%=list.size()%>;
   var userid   ="";
   var kor_name ="";
   var email    ="";
   var cnt      =0;

   if(rowcnt>0){
      if(rowcnt ==1){
        if(document.form1.p_kor_nm.value ==''){
           alert('등급명을 입력하여야합니다.');document.form1.p_kor_nm.focus();return;
        }
      } else {
        for(var i=0;i<rowcnt;i++){
            if(document.form1.p_kor_nm[i].value ==''){
               alert('등급명을 입력하여야합니다.');document.form1.p_kor_nm[i].focus();return;
            }
        }
      }
   }

   document.form1.action          = "/servlet/controller.community.CommunityMasterServlet";
   document.form1.p_process.value = "insertmsmembergradenmData";
   document.form1.submit();
}

//-->
</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_cmuno"      value = "<%=v_cmuno%>">
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
              <td width="686" rowspan="2" valign="top" background="../../images/community/bg_center.gif" >
               <table width="686" border="0" cellspacing="0" cellpadding="0" >
                <tr>
                 <td><img src="../../images/community/center_top.gif"></td>
                </tr>
               </table>
               <table width="686" border="0" cellspacing="0" cellpadding="0">
                <tr>
                 <td align="center" valign="top">

                  <!-- 전체타이틀 -->
                  <table width="652" border="0" cellspacing="0" cellpadding="0">
                   <tr>
                    <td height="1" bgcolor="CCCCCC"></td>
                   </tr>
                   <tr>
                    <td>
                     <table width="407" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                       <td width="407" valign="bottom" class="title">마스터관리<img src="../../images/community/title_vline.gif" align="absbottom"><strong>등급명</strong></td>
                      </tr>
                     </table>
                    </td>
                   </tr>
                   <tr><td height="1" bgcolor="CCCCCC"></td></tr>
                   <tr><td height="10"></td></tr>
                  </table>
                  <table width="652" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
                   <tr  class="lcolor"><td height="3" colspan="2" class="<%=v_title_color[0]%>"></td></tr>
                   <tr> 
                    <td width="130" class="<%=v_title_color[2]%>"><strong>기본명칭</strong></td>
                    <td width="502" class="<%=v_title_color[2]%>" ><strong>수정명칭</strong> </td>
                   </tr>
<%  
    for(int i = 0; i < list.size(); i++) {
        DataBox dbox = (DataBox)list.get(i);
     String v_cls=v_title_color[1];
     if(i%2==0)v_cls=v_title_color[2];
%>
          
                   <tr> 
                     <td class="<%=v_cls%>" ><%=dbox.getString("d_codenm")%></td>

<%
        for(int j = 0; j < gradeList.size(); j++) {
            DataBox dbox1 = (DataBox)gradeList.get(j);

            if(dbox.getString("d_code").equals(dbox1.getString("d_grcode"))){
%>
                      <input name="p_grcode" type="hidden" class="input2" size="10" value="<%=dbox1.getString("d_grcode")%>">
                     <td class="tbl_comleft" ><input name="p_kor_nm" type="text" class="input2" size="20" maxlength=40 value="<%=dbox1.getString("d_kor_nm")%>"> </td>
<%          }
       }%>
                   </tr>

<%
   }
%>
                 </table>
                 <!-- 버튼 -->
                 <table width="652" border="0" cellspacing="0" cellpadding="0">
                  <tr><td height="5" align="right"></td></tr>
                  <tr> 
                   <td align="right"><a href="javascript:uf_saveOK();"><img src="../../images/user/button/btn_mod.gif" border="0" align="absmiddle"></a> 
                       <a href="javascript:uf_listOK();"><img src="../../images/user/button/btn_cancel.gif" border="0" align="absmiddle"></a> 
                   </td>
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
