<%
//**********************************************************
//  1. 제      목: 커뮤니티 설문조사 
//  2. 프로그램명 : zu_CmuQnA_L.jsp
//  3. 개      요: 커뮤니티 설문조사.
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
    String  v_process    = box.getString("p_process");
    String  v_cmuno      = box.getString("p_cmuno");
    String  v_pollno     = box.getString("p_pollno");
    String  v_questno    = box.getString("p_questno");
    String  v_questnotmp = box.getString("p_questnotmp");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    String  v_brd_fg     = box.getString("p_brd_fg");  
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

    if(v_questnotmp.length()>0)v_questno=v_questnotmp;
    int     v_pageno     = box.getInt("p_pageno");
    int v_userpollcnt    = Integer.parseInt(String.valueOf(request.getAttribute("userpollcnt")));
    ArrayList list       = (ArrayList)request.getAttribute("list");
    ArrayList list1      = new ArrayList();
    ArrayList list2      = new ArrayList();
    ArrayList list3		 = new ArrayList();
    ArrayList list4		 = new ArrayList();

    String  v_title      = "";
    String  v_fdte       = "";
	String  v_tdte       = "";
    String  v_background = "";
    String  v_str_fg     = "";
    String  v_make_nm    = "";
    String  v_email      = "";
    if(list.size() != 0){
       list1       = (ArrayList)list.get(0);  //리스트
       list2       = (ArrayList)list.get(1);  //항목
       list3       = (ArrayList)list.get(2);  //상세필드
       list4       = (ArrayList)list.get(3);  //응답결과

    }
    if(list1.size() != 0){
      DataBox dbox1 = (DataBox)list1.get(0);

    v_title      = dbox1.getString("d_title");
    v_fdte       = dbox1.getString("d_fdte");
    v_tdte       = dbox1.getString("d_tdte");

	//v_fdte   = FormatDate.getFormatDate(v_fdte,"yyyy/MM/dd");
   // v_tdte   = FormatDate.getFormatDate(v_tdte,"yyyy/MM/dd");

    v_background = dbox1.getString("d_background");
    v_make_nm    = dbox1.getString("d_name")+"&nbsp;"+dbox1.getString("d_jikupnm")+"/"+dbox1.getString("d_jikwinm")+"&nbsp;"+dbox1.getString("d_deptnam");
    v_str_fg     = dbox1.getString("d_str_fg");
    v_email      = dbox1.getString("d_email");
    }

    //사용자정보
    String  v_user_grade="";
    ArrayList listUser        = (ArrayList)request.getAttribute("listUser");
    if(listUser.size()>0){
      DataBox dboxRoom = (DataBox)listUser.get(0);
      v_user_grade = dboxRoom.getString("d_grade");
    }


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
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript" type="text/JavaScript">
$(document).ready(function(){
	$("#p_fdte").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_tdte").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--

//등록
function uf_insertOK() {
	var k = 0;
	var st_date = make_date(document.form1.p_fdte.value);
    var ed_date = make_date(document.form1.p_tdte.value);

    if(document.form1.p_title.value ==''){
       alert('제목을 입력하여야합니다.');document.form1.p_title.focus();return;
    }

		if(blankCheck(document.form1.p_fdte.value)){
			alert("설문 시작일을 입력하세요.");	return;
		}else {
			alert('시작일 '+st_date+' 입니다');
		}
    
        if(blankCheck(document.form1.p_tdte.value)){
			alert("설문 마감일을 입력하세요.");	return;
        }else{
			alert('종료일 '+ed_date+' 입니다');
		}

        if(st_date > ed_date){
			alert("polling 시작일이 마감일보다 큽니다.");return;
        }
   
	for(var i = 0; i < 10; i++)
	{
		if(document.form1.p_field_name[i].value.length > 0)
		{
			k++;
		}
		else
		{
			if( i <= 8 )
			{

			 if(document.form1.p_field_name[i+1].value.length >= 1)
				{
					alert((i + 1) + "번째 항목을 입력해주세요.");
					document.form1.p_field_name[i].focus();
					return;
				}

			}
		}
	}

	if(document.form1.p_re_gen_fg[3].checked != true && document.form1.p_re_gen_fg[4].checked != true)
	{
		if(k < 2 ) 
		{
			alert("보기항목은 2개 이상입력해 주세요.");
		}
	}

	document.form1.p_started.value  = st_date;
    document.form1.p_ended.value    = ed_date;
    document.form1.p_fieldcount.value  = k;

	document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "updateData";
    document.form1.submit();

}



//리스트페이지로이동
function wf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "movelistPage";
    document.form1.submit();
}

//삭제
function wf_deleteOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "deletefieldData";
    document.form1.submit();
}

//예시 페이지로 이동
function uf_msSamplePage() {

   open_window('movesamplePage', '/servlet/controller.community.CommunityFrPollServlet?p_process=movesamplePage', 100, 100, 670, 480, 'no', 'no', 'no', 'no', 'no');
  
}

//-->
</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0">

<form name = "form1"    enctype = "multipart/form-data"    method = "post">

<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_cmuno"       value="<%=v_cmuno%>">
<input type = "hidden" name = "p_pollno"      value="<%=v_pollno%>">
<input type = "hidden" name = "p_questno"     value="<%=v_questno%>">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_rowseq"      value = "">
<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
<input type = "hidden" name = "p_searchtext"  value = "<%=v_searchtext%>">
<input type = "hidden" name = "p_select"      value = "<%=v_select%>">
<input type = "hidden" name = "p_brd_fg"      value = "<%=box.getString("p_brd_fg")%>">
<input type = "hidden" name = "p_started"		  value= "">
<input type = "hidden" name = "p_ended"		  value= "">
<input type = "hidden" name = "p_fieldcount"   >

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
                   <tr> 
                    <td class="title"><strong>커뮤니티 설문수정<img src="../../images/community/title_vline.gif" align="absbottom"></strong></td>
                   </tr>
                   <tr> 
                    <td height="1" bgcolor="CCCCCC"></td>
                   </tr>
                   <tr> 
                    <td height="10"></td>
                   </tr>
                  </table>

                  <!-- 게시판보기 -->
                  <table width="652" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
                   <tr  class="lcolor"><td height="3" colspan="3" class="<%=v_title_color[0]%>"></td></tr>
                   <tr> 
                    <td width="85" class="<%=v_title_color[1]%>">제목</td>
                    <td colspan="2" class="tbl_btit"><input name="p_title" type="text" class="input2" size="60" value="<%=v_title%>"></td>
                   </tr>
                   <tr> 
                    <td width="85" class="<%=v_title_color[2]%>">작성자</td>
                    <td colspan="2" class="tbl_gleft"><%=v_make_nm%></td>
                   </tr>
                   <tr> 
                    <td class="<%=v_title_color[1]%>" >조사기간</td>
                    <td colspan="2" class="tbl_gleft" >
						<input name="p_fdte" id="p_fdte" type="text" class="datepicker_input1" size="10" value="<%=v_fdte%>">
						~ 
						<input name="p_tdte" id="p_tdte" type="text" class="datepicker_input1" size="10" value="<%=v_tdte%>"> 
                    </td>
                   </tr>
                   <tr> 
                    <td class="<%=v_title_color[2]%>" >설문배경</td>
                    <td colspan="2" class="tbl_gleft" ><textarea name="p_background" cols="70" rows="3"><%=v_background%></textarea></td>
                   </tr>
<%

   //for(int i=0;i<list2.size();){
	  
      DataBox dbox2 = (DataBox)list2.get(0);
     // if(dbox2.getString("d_questno").equals(v_questno)) continue;
%>

                   <tr> 
                   <td class="<%=v_title_color[1]%>" > 문항 </td>
                   <td colspan="2" class="tbl_gleft" >
					<input name="p_need_question" value="<%=dbox2.getString("d_need_question")%>" type="text" class="input2" size="60"></td>
                   </tr>
                   <tr> 
                     <td class="<%=v_title_color[2]%>" >문항유형</td>
                     <td colspan="2" class="tbl_gleft" ><input type="radio" name="p_need_fg" value="1"  <%if("1".equals(dbox2.getString("d_need_fg")))out.print(" checked");%>>필수항목 
                       <input type="radio" name="p_need_fg" value="2"   <%if("1".equals(dbox2.getString("d_need_fg")))out.print(" checked");%>>일반항목</td>
                   </tr>
                    <tr> 
                      <td rowspan="2" class="<%=v_title_color[1]%>" >답변유형(일반)</td>
                      <td colspan="2" class="tbl_gleft" ><input type="radio" name="p_re_gen_fg" value="1"   <%if("1".equals(dbox2.getString("d_re_gen_fg")))out.print(" checked");%>>선택형(Single Select) 
                        <input type="radio" name="p_re_gen_fg" value="2"  <%if("2".equals(dbox2.getString("d_re_gen_fg")))out.print(" checked");%>>복수 응답형(Two Select) 
                        <input type="radio" name="p_re_gen_fg" value="3"  <%if("3".equals(dbox2.getString("d_re_gen_fg")))out.print(" checked");%>>복수 응답형(Multi Select)
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="2" class="tbl_gleft" ><input type="radio" name="p_re_gen_fg" value="4"  <%if("4".equals(dbox2.getString("d_re_gen_fg")))out.print(" checked");%>>주관식 단답형(Text Input) 
                        <input type="radio" name="p_re_gen_fg" value="5"  <%if("5".equals(dbox2.getString("d_re_gen_fg")))out.print(" checked");%>>주관식 복수 응답형(Multi Text Input)</td>
                    </tr>

                    <tr> 
                      <td rowspan="3" class="<%=v_title_color[2]%>" >답변항목</td>
                      <td class="tbl_gleft" colspan=2 >
                       <table width="100%" border="2" cellspacing="0" cellpadding="1"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">                      
                        <tr>
                         <td width=50 align=center>항목</td>
                         <td  align=center >질&nbsp;&nbsp;&nbsp;&nbsp;문</td>
                         <td width=50 align=center>항목</td>
                         <td  align=center>질&nbsp;&nbsp;&nbsp;&nbsp;문</td>
                        </tr>
<%
     int v_eqcnt=0;
     String[] v_tmp_fieldno={"","","","","","","","","","","1"};
     String[] v_tmp_fieldnm={"","","","","","","","","","",""};
     String   v_tmp_etc_fg ="N";
     for(int j=0;j<list3.size();j++){
        DataBox dbox3 = (DataBox)list3.get(j);
        if(dbox2.getInt("d_questno")!=dbox3.getInt("d_questno")) continue;
        if("Y".equals(dbox3.getString("d_etc_fg"))){
          v_tmp_fieldno[10]=dbox3.getString("d_fieldno");
          v_tmp_fieldnm[10]=dbox3.getString("d_field_name");
          v_tmp_etc_fg=dbox3.getString("d_etc_fg");
          continue;
        }

        v_tmp_fieldno[v_eqcnt]=dbox3.getString("d_fieldno");
        v_tmp_fieldnm[v_eqcnt]=dbox3.getString("d_field_name");
        v_eqcnt++; 
     }
%>
                        <tr>
                         <td width=50>항목1</td>
                         <td class="tbl_gleft" ><input type=hidden name="p_fieldno" value="<%=v_tmp_fieldno[0]%>"><input name="p_field_name" value="<%=v_tmp_fieldnm[0]%>" type="text" class="input2" size="30" maxlength=100></td>
                         <td width=50>항목2</td>                                                                                                                      
                         <td class="tbl_gleft" ><input type=hidden name="p_fieldno" value="<%=v_tmp_fieldno[1]%>"><input name="p_field_name" value="<%=v_tmp_fieldnm[1]%>"  type="text" class="input2" size="30" maxlength=100></td>
                        </tr>                                                                                   <tr>                                                                                                                                                                                                                                                                                                            
                         <td width=50>항목3</td>                                                                                                                      
                         <td class="tbl_gleft" ><input type=hidden name="p_fieldno" value="<%=v_tmp_fieldno[2]%>"><input name="p_field_name" value="<%=v_tmp_fieldnm[2]%>"  type="text" class="input2" size="30" maxlength=100></td>
                         <td width=50>항목4</td>                                                                                                                      
                         <td class="tbl_gleft" ><input type=hidden name="p_fieldno" value="<%=v_tmp_fieldno[3]%>"><input name="p_field_name" value="<%=v_tmp_fieldnm[3]%>"  type="text" class="input2" size="30" maxlength=100></td>
                        </tr>                                                                                                                                         
                        <tr>                                                                                                                                          
                         <td width=50>항목5</td>                                                                                                                      
                         <td class="tbl_gleft" ><input type=hidden name="p_fieldno" value="<%=v_tmp_fieldno[4]%>"><input name="p_field_name" value="<%=v_tmp_fieldnm[4]%>"  type="text" class="input2" size="30" maxlength=100></td>
                         <td width=50>항목6</td>                                                                                                                      
                         <td class="tbl_gleft" ><input type=hidden name="p_fieldno" value="<%=v_tmp_fieldno[5]%>"><input name="p_field_name" value="<%=v_tmp_fieldnm[5]%>"  type="text" class="input2" size="30" maxlength=100></td>
                        </tr>                                                                                                                                         
                        <tr>                                                                                                                                          
                         <td width=50>항목7</td>                                                                                                                      
                         <td class="tbl_gleft" ><input type=hidden name="p_fieldno" value="<%=v_tmp_fieldno[6]%>"><input name="p_field_name" value="<%=v_tmp_fieldnm[6]%>"  type="text" class="input2" size="30" maxlength=100></td>
                         <td width=50>항목8</td>                                                                                                                      
                         <td class="tbl_gleft" ><input type=hidden name="p_fieldno" value="<%=v_tmp_fieldno[7]%>"><input name="p_field_name" value="<%=v_tmp_fieldnm[7]%>"  type="text" class="input2" size="30" maxlength=100></td>
                        </tr>                                                                                                                                         
                        <tr>                                                                                                                                          
                         <td width=50>항목9</td>                                                                                                                      
                         <td class="tbl_gleft" ><input type=hidden name="p_fieldno" value="<%=v_tmp_fieldno[8]%>"><input name="p_field_name" value="<%=v_tmp_fieldnm[8]%>"  type="text" class="input2" size="30" maxlength=100></td>
                         <td width=50>항목10</td>                                                                                                                     
                         <td class="tbl_gleft" ><input type=hidden name="p_fieldno" value="<%=v_tmp_fieldno[9]%>"><input name="p_field_name" value="<%=v_tmp_fieldnm[9]%>"  type="text" class="input2" size="30" maxlength=100></td>
                        </tr>

                       </table>
                      </td> 
                    </tr>
                    <tr> 
                      <td class="tbl_gleft" >기타 의견란 사용여부 체크:</td>
                      <td class="tbl_gleft" ><input type=hidden name="p_etcno" value="<%=v_tmp_fieldno[10]%>"><input type="checkbox" name="p_etc_fg" value="Y" <%if("Y".equals(v_tmp_etc_fg))out.print(" checked");%>></td>
                    </tr>
                    <tr> 
                      <td class="tbl_gleft" >기타 의견란에 사용할 표시이름:</td>
                      <td class="tbl_gleft" ><input name="p_etc_nm" value="<%=v_tmp_fieldnm[10]%>" type="text" class="input2" size="50"></td>
                    </tr>
<!--%}%-->
                  </table>
 <!-- 버튼 -->
                  <table width="652" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="5"></td>
                    </tr>
                    <tr> 
                      <td align="right">
					  <!-- 수정등록 -->
					  <a href="javascript:uf_insertOK();"><img src="../../images/user/button/btn_save.gif" ></a> 
                      <!-- 리스트로 -->
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
