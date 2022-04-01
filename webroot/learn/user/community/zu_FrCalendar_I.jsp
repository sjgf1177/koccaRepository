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

    int[]   v_month      ={0,1,2,3,4,5,6,7,8,9,10,11,12};
    int[]   v_day        ={0,1,2,3,4,5,6,7,8,9,10,11,12,13,1,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
    String  v_process    = box.getStringDefault("p_process","movePage");
    String  v_cmuno      = box.getString("p_cmuno");
    String v_if_flag       = box.getStringDefault("p_if_flag","MM");    
    String v_if_mm       = box.getStringDefault("p_if_mm",FormatDate.getDate("yyyyMMddHHmmssSSS").substring(0,6));     
    String v_if_dd       = box.getStringDefault("p_if_dd",FormatDate.getDate("yyyyMMdd"));     
    String v_if_ww       = box.getStringDefault("p_if_ww",""); 
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  content      = box.getString("content");
    String  width        = "550";
    String  height       = "200";
    String  v_brd_fg      = box.getString("p_brd_fg");  
    String  v_thisday    =FormatDate.getDate("yyyyMMddHHmmssSSS");

    ArrayList list       = (ArrayList)request.getAttribute("list");
 
    DataBox dbox = (DataBox)list.get(0);


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
function uf_insertOK() {
    if(document.form1.p_calday_year.value =='0000'||document.form1.p_calday_year.value ==''){
       alert("날자를 입력하여야합니다.");document.form1.p_calday_year.focus();return;
    }
    if(document.form1.p_calday_month.value =='00'||document.form1.p_calday_month.value ==''){
       alert("날자를 입력하여야합니다.");document.form1.p_calday_month.focus();return;
    }
    if(document.form1.p_calday_day.value =='00'||document.form1.p_calday_day.value ==''){
       alert("날자를 입력하여야합니다.");document.form1.p_calday_day.focus();return;
    }
    if(document.form1.p_title.value ==''){
       alert("제목을 입력하여야합니다.");document.form1.p_title.focus();return;
    }

    if(document.form1.p_day_fg.checked==false){
      if(document.form1.p_ftime_hh.value =='00'){
         alert("시간을 입력하여야합니다.");document.form1.p_ftime_hh.focus();return;
      }
      if(document.form1.p_ftime_mm.value =='00'){
         alert("시간을 입력하여야합니다.");document.form1.p_ftime_mm.focus();return;
      }
      if(document.form1.p_ttime_hh.value =='00'){
         alert("시간을 입력하여야합니다.");document.form1.p_ttime_hh.focus();return;
      }
      if(document.form1.p_ttime_mm.value =='00'){
         alert("시간을 입력하여야합니다.");document.form1.p_ttime_mm.focus();return;
      }
    }
    if(document.form1.p_cal_area.value ==''){
       alert("장소를 입력하여야합니다.");document.form1.p_cal_area.focus();return;
    }
    if(document.form1.p_content.value ==''){
       alert("상세내용을 입력하여야합니다.");document.form1.p_content.focus();return;
    }

    if(document.form1.p_loop_fg.checked==false){
      if(document.form1.p_limit_day_year.value =='0000'){
         alert("반복기한을 입력하여야합니다.");document.form1.p_limit_day_year.focus();return;
      }
      if(document.form1.p_limit_day_month.value =='00'){
         alert("반복기한을 입력하여야합니다.");document.form1.p_limit_day_month.focus();return;
      }
      if(document.form1.p_limit_day_day.value =='00'){
         alert("반복기한을 입력하여야합니다.");document.form1.p_limit_day_day.focus();return;
      }
    }

     
    document.form1.action = "/servlet/controller.community.CommunityFrCalendarServlet";
    document.form1.p_process.value = "insertData";
    document.form1.submit();
}
//리스트
function uf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrCalendarServlet";
    document.form1.p_process.value = "movelistPage";
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
<input type = "hidden" name = "p_if_flag"      value = "<%=v_if_flag%>">
<input type = "hidden" name = "p_if_mm"      value = "<%=v_if_mm%>">
<input type = "hidden" name = "p_if_ww"      value = "<%=v_if_ww%>">
<input type = "hidden" name = "p_if_dd"      value = "<%=v_if_dd%>">
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
           <td width="168" valign="top"><a href="/servlet/controller.community.CommunityIndexServlet" onfocus="blur()"><img src="../../images/community/top_img01.jpg" border="0"></a></td>
           <td width="708" valign="top"><img src="../../images/community/topimg.jpg"></td>
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
                    <td valign="bottom" class="title">일정<img src="../../images/community/title_vline.gif" align="absbottom"><strong>일정등록</strong></td>
                   </tr>
                   <tr> 
                    <td height="1" bgcolor="CCCCCC"></td>
                   </tr>
                   <tr> 
                    <td height="10"></td>
                   </tr>
                  </table>
                  <!-- 게시판보기 -->
                  <table width="652" border="2" cellspacing="0"   cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
                   <tr  class="lcolor"><td height="3" colspan="2" class="<%=v_title_color[0]%>"></td></tr>
                   <tr> 
                    <td class="<%=v_title_color[1]%>">공개유무</td>
                    <td class="tbl_gleft"><input type="radio" name="p_security_fg" value="Y" checked>공개 
                                          <input type="radio" name="p_security_fg" value="N">비공개
                    </td>
                   </tr>
                   <tr> 
                    <td class="<%=v_title_color[2]%>">날짜</td>
                    <td class="tbl_gleft"><select name="p_calday_year">
                     <option value="0000">선택</option> 
                     <% for(int i=(Integer.parseInt(v_thisday.substring(0,4)));i<=(Integer.parseInt(v_thisday.substring(0,4))+10);i++){%>
                           <option value="<%=i%>"><%=i%></option>
                     <%}%>
                     </select>년 
                    <select name="p_calday_month">
                     <option value="00">선택</option>  
                     <% for(int i=1;i<=12;i++){%><option value="<%=i%>"><%=i%></option><%}%>
                        </select>월 
                     <select name="p_calday_day" >
                     <option value="00">선택</option>  
                     <% for(int i=1;i<=31;i++){%><option value="<%=i%>"><%=i%></option><%}%>
                        </select>일 
                     </td>
                    </tr>
                    <tr> 
                     <td width="95" class="<%=v_title_color[1]%>">제목</td>
                     <td class="tbl_btit"><input name="p_title" type="text" class="input2" size="60"></td>
                    </tr>
<%  String[] v_time_cd={"00","01","02","03","04","05","06","07","08","09","10","11"
                       ,"12","13","14","15","16","17","18","19","20","21","22","23"};
    String[] v_time_nm={"12","01","02","03","04","05","06","07","08","09","10","11"
                       ,"12","01","02","03","04","05","06","07","08","09","10","11"};
%>
                    <tr> 
                     <td width="95" class="<%=v_title_color[2]%>">시간</td>
                     <td class="tbl_gleft"><select name="p_ftime_hh">
                      <option value="xx">선택</option> 
                      <% for(int i=0;i<=11;i++){%><option value="<%=v_time_cd[i]%>">오전<%=v_time_nm[i]%></option><%}%>
                      <% for(int i=12;i<=23;i++){%><option value="<%=v_time_cd[i]%>">오후<%=v_time_nm[i]%></option><%}%>
                      </select>시
                      <select name="p_ftime_mm">
                      <option value="xx">선택</option> 
                       <% for(int i=0;i<=59;i++){%><option value="<%=i%>"><%=i%></option><%}%>
                      </select>분 ~ 
                      <select name="p_ttime_hh">
                      <option value="xx">선택</option> 
                      <% for(int i=0;i<=11;i++){%><option value="<%=v_time_cd[i]%>">오전<%=v_time_nm[i]%></option><%}%>
                      <% for(int i=12;i<=23;i++){%><option value="<%=v_time_cd[i]%>">오후<%=v_time_nm[i]%></option><%}%>
                      </select>시
                      <select name="p_ttime_mm">
                      <option value="xx">선택</option> 
                       <% for(int i=0;i<=59;i++){%><option value="<%=i%>"><%=i%></option><%}%>
                      </select>분까지
                        <input type="checkbox" name="p_day_fg" value="Y">하루종일</td>
                    </tr>
                    <tr> 
                      <td class="<%=v_title_color[1]%>" >장소</td>
                      <td class="tbl_gleft" ><input name="p_cal_area" type="text" class="input2" size="60"></td>
                    </tr>
                    <tr> 
                      <td class="<%=v_title_color[2]%>" >상세내용</td>
                      <td class="tbl_gleft" > <textarea name="p_content" cols="70" rows="3"></textarea></td>
                    </tr>
                    <tr> 
                      <td class="<%=v_title_color[1]%>" >반복주기</td>
                      <td class="tbl_gleft" ><select name="p_cycle_time"> 
                          <option value=0>반복안함</option>
                     <% for(int i=1;i<=12;i++){%><option value="<%=i%>"><%=i%></option><%}%>
                        </select>년 
                        <select name="p_cycle_time" >
                      <option value="00">선택</option> 
                     <% for(int i=1;i<=12;i++){%><option value="<%=i%>"><%=i%></option><%}%>
                        </select>마다 </td>
                    </tr>
                    <tr> 
                      <td class="<%=v_title_color[2]%>" >반복기한</td>
                      <td class="tbl_gleft" ><select name="p_limit_day_year">
                       <option value="0000">선택</option> 
                     <% for(int i=(Integer.parseInt(v_thisday.substring(0,4))-10);i<=(Integer.parseInt(v_thisday.substring(0,4))+10);i++){%>
                           <option value="<%=i%>"><%=i%></option>
                     <%}%>

                        </select>
                        년 
                        <select name="p_limit_day_month">
                      <option value="00">선택</option> 
                         <% for(int i=1;i<=12;i++){%><option value="<%=i%>"><%=i%></option><%}%>
                        </select>월
                        <select name="p_limit_day_day" >
                      <option value="00">선택</option> 
                         <% for(int i=1;i<=31;i++){%><option value="<%=i%>"><%=i%></option><%}%>
                        </select>일 까지 <strong>또는</strong> 
                        <input type="checkbox" name="p_loop_fg" value="Y">무한반복</td>
                    </tr>
                  </table>
<!-- 버튼 -->
                  <table width="652" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="5"></td>
                    </tr>
                    <tr> 
                      <td align="right"><a href="javascript:uf_listOK();"><img src="../../images/user/button/btn_list.gif"></a><a href="javascript:uf_insertOK();"><img src="../../images/user/button/btn_save.gif" border="0"></a></td>
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
             <tr><td><img src="../../images/community/footer_top.gif"></td></tr>
             <tr><td height="28" align="center" bgcolor="F0F2F0"><img src="../../images/community/footer_copy.gif"></td></tr>
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
