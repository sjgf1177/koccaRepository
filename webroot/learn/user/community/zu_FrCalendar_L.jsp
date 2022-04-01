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

    String  v_process      = box.getStringDefault("p_process","movePage");
    String  v_cmuno        = box.getString("p_cmuno");
    String v_if_flag       = box.getStringDefault("p_if_flag","MM");
    String v_if_mm        = box.getStringDefault("p_if_mm",FormatDate.getDate("yyyyMMddHHmmssSSS").substring(0,6));     
    String v_if_dd        = box.getStringDefault("p_if_dd",FormatDate.getDate("yyyyMMdd"));     
    String v_if_ww        = box.getStringDefault("p_if_ww","");     

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  v_thisday    =FormatDate.getDate("yyyyMMddHHmmssSSS");

    ArrayList list       = (ArrayList)request.getAttribute("list");
    CommunityFrCalendarBean benCal = new CommunityFrCalendarBean();
    System.out.println("list.size()   :"+list.size());
    String  v_brd_fg      = box.getString("p_brd_fg");  

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
//조회
function uf_searchOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrCalendarServlet";
    document.form1.p_process.value = "movelistPage";
    document.form1.submit();
}

//등록
function uf_writeOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrCalendarServlet";
    document.form1.p_process.value = "movewritePage";
    document.form1.submit();
}
//월,주,일 페이지이동
function uf_nextbeforeOK(vtext,p_if_flag) {
    document.form1.p_if_flag.value=p_if_flag;

    if(document.form1.p_if_flag.value=='MM'){
       document.form1.p_if_mm.value=vtext;
    } else if(document.form1.p_if_flag.value=='WW'){
       document.form1.p_if_ww.value=vtext;
    } else if(document.form1.p_if_flag.value=='DD'){
       document.form1.p_if_dd.value=vtext;
    }

    document.form1.action = "/servlet/controller.community.CommunityFrCalendarServlet";
    document.form1.p_process.value = "movelistPage";
    document.form1.submit();
}

//보기페이지로이동
function uf_viewOK(calno) {
       document.form1.p_calno.value=calno;
    document.form1.action = "/servlet/controller.community.CommunityFrCalendarServlet";
    document.form1.p_process.value = "moveviewPage";
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
<input type = "hidden" name = "p_if_mm"      value = "<%=v_if_mm%>">
<input type = "hidden" name = "p_if_ww"      value = "<%=v_if_ww%>">
<input type = "hidden" name = "p_if_dd"      value = "<%=v_if_dd%>">

<input type = "hidden" name = "p_if_flag"      value = "<%=v_if_flag%>">
<input type = "hidden" name = "p_brd_fg"       value = "<%=box.getString("p_brd_fg")%>">
<input type = "hidden" name = "p_calno"      value = "">

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
                   <tr><td height="1" bgcolor="CCCCCC"></td></tr>
                   <tr> 
                    <td>
                     <table width="243" border="0" cellspacing="0" cellpadding="0">
                      <tr><td valign="bottom" class="title">일정<img src="../../images/community/title_vline.gif" align="absbottom"><strong>커뮤니티 일정 </strong></td></tr>
                     </table>
                    </td>
                   </tr>
                   <tr><td height="1" bgcolor="CCCCCC"></td></tr>
                   <tr><td height="10"></td></tr>
                  </table>

<% if("MM".equals(v_if_flag)){
     String v_bef_month ="";
     String v_aft_month ="";
     if(Integer.parseInt(v_if_mm.substring(2))==1){
       v_bef_month = String.valueOf(Integer.parseInt(v_if_mm.substring(0,4))-1)+"12";
     } else {
       v_bef_month = String.valueOf(Integer.parseInt(v_if_mm)-1);
     }

     if(Integer.parseInt(v_if_mm.substring(2))==13){
       v_aft_month = String.valueOf(Integer.parseInt(v_if_mm.substring(0,4))+1)+"01";
     } else {
       v_aft_month = String.valueOf(Integer.parseInt(v_if_mm)+1);
     }

     Calendar cal = Calendar.getInstance();
     cal.set(Calendar.YEAR, Integer.parseInt(v_if_mm.substring(0,4))); 
     cal.set(Calendar.MONTH, Integer.parseInt(v_if_mm.substring(4,6)) - 1); 
     cal.set(Calendar.DATE, Integer.parseInt(v_thisday.substring(6,8))); 
     String v_day_tmp =v_if_mm.substring(0,6)+v_thisday.substring(6,8);

     String v_week_tmp=v_if_mm.substring(0,6)+String.valueOf(cal.get(Calendar.WEEK_OF_MONTH )   );

 %>
                  <!-- 버튼 -->
                  <table width="652" border="0" cellspacing="0" cellpadding="0">
                   <tr> 
                    <td width="507"> <a href="javascript:uf_writeOK();"><img src="../../images/user/button/btn_dayr.gif" border="0" ></a> 
                     <a href="javascript:uf_nextbeforeOK(<%=v_day_tmp%>,'DD');"><img src="../../images/user/button/btn_day.gif" border="0"></a> 
                     <a href="javascript:uf_nextbeforeOK(<%=v_week_tmp%>,'WW');"><img src="../../images/user/button/btn_week.gif" border="0"></a> 
                     <a href="javascript:uf_nextbeforeOK(<%=v_if_mm.substring(0,6)%>,'MM');"><img src="../../images/user/button/btn_month.gif" border="0"></a> 
                    </td>
                    <td width="145" align=right><a href="javascript:uf_nextbeforeOK(<%=v_bef_month%>,'MM');"><img src="../../images/user/button/a_pre.gif" border="0" align="absmiddle"></a><strong><%=v_if_mm.substring(0,4)+"년"+v_if_mm.substring(4)+"월"%>
                      </strong><a href="javascript:uf_nextbeforeOK(<%=v_aft_month%>,'MM');"><img src="../../images/user/button/a_next.gif" border="0" align="absmiddle"></a></td>
                    </tr>
                    <tr><td height="2" colspan="2"></td></tr>
                   </table>

                   <!-- 게시판보기 -->
                   <table width="652" border="2" cellspacing="0"       cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
                    <tr  class="lcolor"><td height="3" colspan="7" class="<%=v_title_color[0]%>"></td></tr>
                    <tr> 
                     <td class="<%=v_title_color[2]%>">일</td>
                     <td width="85" valign="top" class="<%=v_title_color[1]%>">월</td>
                     <td width="84" class="<%=v_title_color[2]%>">화</td>
                     <td width="84" class="<%=v_title_color[1]%>">수</td>
                     <td width="84" class="<%=v_title_color[2]%>">목</td>
                     <td width="84" class="<%=v_title_color[1]%>">금</td>
                     <td width="85" class="<%=v_title_color[2]%>">토</td>
                    </tr>
<%
   //0.일요일 1.월요일 2.화요일 3.수요일 4.목요일 5.금요일 6.토요일                      

   String v_month =v_if_mm.substring(0,6);
   int iSeven     =0;
   int iDayWeek   =0;
   int iWeekCnt   =0;


   for ( int i =1; i <=32;i++){
      Date tmpDate = new Date();
      tmpDate.setYear(Integer.parseInt(v_month.substring(0,4))-1900);
      tmpDate.setMonth(Integer.parseInt(v_month.substring(4,6))-1);
      tmpDate.setDate(i);

      iDayWeek =tmpDate.getDay();
      String v_fixDay =v_month+benCal.LPAD(String.valueOf(i),2,'0');
      if (i>26 && benCal.dateValid(v_month+benCal.LPAD(String.valueOf(i),2,'0')) ==false){
         
        if (iSeven!=7){
            for(int j=0;j<=7- iSeven;j++){ %>
                     <td width="86" height="90" valign="top" class="tbl_grc"> &nbsp;</td>
<%          }%>
          </tr>    
<%      }
        break;
      }
    iSeven++;
    if (iSeven > 7) iSeven=1;
%>


<% if(iDayWeek==0){%>
       <tr> 
<% }%>
<%
    if (i == 1){  //1일인경우
System.out.println("iDayWeek  :"+iDayWeek);
        for(int j=0;j< iDayWeek;j++){ iSeven++;%>
                     <td width="86" height="90" valign="top"  class="tbl_grc"> &nbsp;</td>
       
<%      }
   }%>
<%
   String v_bgcolor="";
   String v_fontcolor="";
   if(iDayWeek==0){
      v_bgcolor ="bgcolor=#FFF4F4";
      v_fontcolor ="color=#CC3300";
   } else if(iDayWeek==6){
      v_bgcolor ="bgcolor=#F1F5FA";
      v_fontcolor ="";
   }
%>
<%
  String v_daytext="";
  for(int k = 0; k < list.size(); k++) {
      DataBox dbox = (DataBox)list.get(k);
      if("Y".equals(dbox.getString("d_loop_fg"))){
        if(Long.parseLong(v_fixDay) >= Long.parseLong(dbox.getString("d_calday"))){
          if("Y".equals(dbox.getString("d_day_fg"))){
            v_daytext += "<a href=javascript:uf_viewOK("+dbox.getString("d_calno")+");>"+"00:00"+"~"+"24:00"+"&nbsp;"+dbox.getString("d_title")+"</a><br>";
          } else {
            String v_tmp_ftime=dbox.getString("d_ftime").substring(0,2)+":"+dbox.getString("d_ftime").substring(2);
            String v_tmp_ttime=dbox.getString("d_ttime").substring(0,2)+":"+dbox.getString("d_ttime").substring(2);

            v_daytext += "<a href=javascript:uf_viewOK("+dbox.getString("d_calno")+");>"+v_tmp_ftime+"~"+v_tmp_ttime+"&nbsp;"+dbox.getString("d_title")+"</a><br>";

          }
        }
      } else {
         if(Long.parseLong(v_fixDay) >=Long.parseLong(dbox.getString("d_calday"))&&Long.parseLong(v_fixDay) <= Long.parseLong(dbox.getString("d_limit_day"))){
          if("Y".equals(dbox.getString("d_day_fg"))){
            v_daytext += "<a href=javascript:uf_viewOK("+dbox.getString("d_calno")+");>"+"00:00"+"~"+"24:00"+"&nbsp;"+dbox.getString("d_title")+"</a><br>";
          } else {
            String v_tmp_ftime=dbox.getString("d_ftime").substring(0,2)+":"+dbox.getString("d_ftime").substring(2);
            String v_tmp_ttime=dbox.getString("d_ttime").substring(0,2)+":"+dbox.getString("d_ttime").substring(2);

            v_daytext += "<a href=javascript:uf_viewOK("+dbox.getString("d_calno")+");>"+v_tmp_ftime+"~"+v_tmp_ttime+"&nbsp;"+dbox.getString("d_title")+"</a><br>";

          }
         }
      }
  }
%>
                     <td width="86" height="90" valign="top" <%=v_bgcolor%> class="tbl_grc"> 
                      <table width="82" border="0" cellspacing="0" cellpadding="0">
                       <tr><td><font <%=v_fontcolor%>><strong><%=i%></strong></font></td></tr>
                       <tr> 
                        <td valign="top">
                         <div style='width:87px;height:80px;overflow:auto;scrollbar-3dlight-color:#B5B5B5;scrollbar-arrow-color:#B5B5B5;scrollbar-darkshadow-color:#FFFFFF;scrollbar-face-color:#FFFFFF;scrollbar-highlight-color:#FFFFFFc;scrollbar-shadow-color:#B5B5B5;'>
                          <table width="70" border="0" cellspacing="0" cellpadding="0">
                           <tr>
                            <td><%=v_daytext%><td>
                           </tr>
                          </table>
                         </div>
                        </td>
                       </tr>
                      </table>
                     </td>
<%  if(iDayWeek ==6){%>
          </tr>
<%  }%>


<%
   }
%>
                    </table>
<%}%>










<% if("WW".equals(v_if_flag)){
     String v_bef_week ="";
     String v_aft_week ="";
     Calendar cal = Calendar.getInstance(); 
 
     cal.set(Calendar.YEAR, Integer.parseInt(v_if_ww.substring(0,4))); 
     cal.set(Calendar.MONTH, Integer.parseInt(v_if_ww.substring(4,6)) - 1); 
 
     cal.set(Calendar.WEEK_OF_MONTH, Integer.parseInt(v_if_ww.substring(6))); 
     cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); 
     int startDay = cal.get(Calendar.DAY_OF_MONTH); 
     String sfDay =  String.valueOf(cal.get(Calendar.YEAR))+benCal.LPAD(String.valueOf(cal.get(Calendar.MONTH)+1),2,'0')+benCal.LPAD(String.valueOf(startDay),2,'0');

     cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY); 
     int endDay = cal.get(Calendar.DAY_OF_MONTH); 
     String stDay =  String.valueOf(cal.get(Calendar.YEAR))+benCal.LPAD(String.valueOf(cal.get(Calendar.MONTH)+1),2,'0')+benCal.LPAD(String.valueOf(endDay),2,'0');

     String v_tmp_f_day = FormatDate.getDateAdd(sfDay, "yyyyMMdd","date",-1);
     String v_tmp_t_day = FormatDate.getDateAdd(stDay, "yyyyMMdd","date",1);


     Calendar cal1 = Calendar.getInstance(); 
     cal1.set(Calendar.YEAR, Integer.parseInt(v_tmp_f_day.substring(0,4))); 
     cal1.set(Calendar.MONTH, Integer.parseInt(v_tmp_f_day.substring(4,6)) - 1); 
     cal1.set(Calendar.DATE, Integer.parseInt(v_tmp_f_day.substring(6)));  
     v_bef_week   = v_tmp_f_day.substring(0,6)+ String.valueOf(cal1.get(Calendar.WEEK_OF_MONTH ));

     Calendar cal2 = Calendar.getInstance(); 
     cal2.set(Calendar.YEAR, Integer.parseInt(v_tmp_t_day.substring(0,4))); 
     cal2.set(Calendar.MONTH, Integer.parseInt(v_tmp_t_day.substring(4,6)) - 1); 
     cal2.set(Calendar.DATE, Integer.parseInt(v_tmp_t_day.substring(6)));  
     v_aft_week   = v_tmp_t_day.substring(0,6)+ String.valueOf(cal2.get(Calendar.WEEK_OF_MONTH ));




 %>
                  <!-- 버튼 -->
                  <table width="652" border="0" cellspacing="0" cellpadding="0">
                   <tr> 
                    <td width="487"> <a href="javascript:uf_writeOK();"><img src="../../images/user/button/btn_dayr.gif" border="0" ></a> 
                     <a href="javascript:uf_nextbeforeOK(<%=v_if_dd%>,'DD');"><img src="../../images/user/button/btn_day.gif" border="0"></a> 
                     <a href="javascript:uf_nextbeforeOK(<%=v_if_ww%>,'WW');"><img src="../../images/user/button/btn_week.gif" border="0"></a> 
                     <a href="javascript:uf_nextbeforeOK(<%=v_if_mm.substring(0,6)%>,'MM');"><img src="../../images/user/button/btn_month.gif" border="0"></a> 
                    </td>
                    <td width="165" align=right><a href="javascript:uf_nextbeforeOK(<%=v_bef_week%>,'WW');"><img src="../../images/user/button/a_pre.gif" border="0" align="absmiddle"></a><strong><%=v_if_ww.substring(0,4)+"년"+v_if_ww.substring(4,6)+"월"+" "+v_if_ww.substring(6)+"번째주"%>
                      </strong><a href="javascript:uf_nextbeforeOK(<%=v_aft_week%>,'WW');"><img src="../../images/user/button/a_next.gif" border="0" align="absmiddle"></a></td>
                    </tr>
                    <tr><td height="2" colspan="2"></td></tr>
                   </table>


                    <!-- 게시판보기 -->
                    <table width="652" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
                     <tr  class="lcolor"><td height="3" colspan="2" class="<%=v_title_color[0]%>"></td></tr>
                     <tr> 
                      <td class="<%=v_title_color[2]%>">요일(날짜)</td>
                      <td class="<%=v_title_color[2]%>">내용</td>
                     </tr>
<%
  String v_daytext="";
  String tmpDay="";
  String v_ary_day[]={"","","","","","",""};
  int v_loop_var=0;
  for (int i=1;i<=7;i++){

       if(i==1){
          tmpDay =sfDay;
       } else {
          tmpDay =FormatDate.getDateAdd(tmpDay, "yyyyMMdd","date",1);
       }
       System.out.println(tmpDay);
       v_ary_day[i-1]=tmpDay;
       v_loop_var++;
       if(tmpDay.equals(stDay)) break;

  } 

  String[] v_txt  = null;
  v_txt= new String[v_loop_var];
  for(int j=0;j<v_loop_var;j++)v_txt[j]="";


  System.out.println(">....##############  :"+list.size());
  for(int k = 0; k < list.size(); k++) {
      DataBox dbox = (DataBox)list.get(k);
       for(int kk=0;kk<v_loop_var;kk++){
           v_daytext="";
           if("Y".equals(dbox.getString("d_loop_fg"))){
             if(Long.parseLong(v_ary_day[kk]) >= Long.parseLong(dbox.getString("d_calday"))){
               System.out.println("1    ...."+v_ary_day[kk]+"    :   "+dbox.getString("d_calday")+"  :"+dbox.getString("d_limit_day"));
               if("Y".equals(dbox.getString("d_day_fg"))){
                  v_daytext = "00:00"+"~"+"24:00"+"&nbsp;"+dbox.getString("d_title")+"<br>";
                  v_txt[kk]+="<a href=javascript:uf_viewOK("+dbox.getString("d_calno")+");>"+v_daytext+"</a>";
               } else {
                 String v_tmp_ftime=dbox.getString("d_ftime").substring(0,2)+":"+dbox.getString("d_ftime").substring(2);
                 String v_tmp_ttime=dbox.getString("d_ttime").substring(0,2)+":"+dbox.getString("d_ttime").substring(2);
                 v_daytext = v_tmp_ftime+"~"+v_tmp_ttime+"&nbsp;"+dbox.getString("d_title")+"<br>";
                 v_txt[kk]+="<a href=javascript:uf_viewOK("+dbox.getString("d_calno")+");>"+v_daytext+"</a>";
               }
             }
           } else {
               System.out.println("2    ...."+v_ary_day[kk]+"    :   "+dbox.getString("d_calday")+"  :"+dbox.getString("d_limit_day")+"  :"+  dbox.getString("d_title"));
              if(Long.parseLong(v_ary_day[kk]) >=Long.parseLong(dbox.getString("d_calday"))&&Long.parseLong(v_ary_day[kk]) <= Long.parseLong(dbox.getString("d_limit_day"))){
               if("Y".equals(dbox.getString("d_day_fg"))){
                 v_daytext = "00:00"+"~"+"24:00"+"&nbsp;"+dbox.getString("d_title")+"<br>";
                 v_txt[kk]+="<a href=javascript:uf_viewOK("+dbox.getString("d_calno")+");>"+v_daytext+"</a>";
               } else {
                 String v_tmp_ftime=dbox.getString("d_ftime").substring(0,2)+":"+dbox.getString("d_ftime").substring(2);
                 String v_tmp_ttime=dbox.getString("d_ttime").substring(0,2)+":"+dbox.getString("d_ttime").substring(2);
                 v_daytext = v_tmp_ftime+"~"+v_tmp_ttime+"&nbsp;"+dbox.getString("d_title")+"<br>";
                 v_txt[kk]+="<a href=javascript:uf_viewOK("+dbox.getString("d_calno")+");>"+v_daytext+"</a>";
               }
              }
           }
       }
  }
%>
<% 
    String v_week_tmp_nm[]={"","일","월","화","수","목","금","토"};
    for(int j=0;j<v_loop_var;j++){
     Calendar cal3 = Calendar.getInstance(); 
     cal3.set(Calendar.YEAR, Integer.parseInt(v_ary_day[j].substring(0,4))); 
     cal3.set(Calendar.MONTH, Integer.parseInt(v_ary_day[j].substring(4,6)) - 1); 
     cal3.set(Calendar.DATE, Integer.parseInt(v_ary_day[j].substring(6)));  
     int iyoil =cal3.get(Calendar.DAY_OF_WEEK );
     if(iyoil==1){
%>
                    <tr> 
                      <td width="105" class="tbl_gc_back"><font color=#CC3300><strong><%=v_week_tmp_nm[iyoil]%> 
                        (<%=FormatDate.getFormatDate(v_ary_day[j],"yyyy-MM-dd")%>)</strong></font></td>
                      <td bgcolor="#FFF4F4" class="tbl_gleft"><%=v_txt[j]%></td>
                     </tr>
<%  } else if(iyoil==7){%>
                    <tr> 
                      <td width="105" class="tbl_gc_back"><strong><%=v_week_tmp_nm[iyoil]%> 
                        (<%=FormatDate.getFormatDate(v_ary_day[j],"yyyy-MM-dd")%>)</strong></td>
                      <td bgcolor="#FFF4F4" class="tbl_gleft"><%=v_txt[j]%></td>
                     </tr>

<%  } else{%>
                    <tr> 
                      <td width="105" class="tbl_gc_back"><%=v_week_tmp_nm[iyoil]%> 
                        (<%=FormatDate.getFormatDate(v_ary_day[j],"yyyy-MM-dd")%>)</td>
                      <td bgcolor="#FFF4F4" class="tbl_gleft"><%=v_txt[j]%></td>
                     </tr>

<% }%>
<%}%>

                    </table>
<%}%>











<% if("DD".equals(v_if_flag)){
     String v_bef_day ="";
     String v_aft_day ="";
     v_bef_day = FormatDate.getDateAdd(v_if_dd, "yyyyMMdd","date",-1);
     v_aft_day = FormatDate.getDateAdd(v_if_dd, "yyyyMMdd","date",1);


     Calendar cal = Calendar.getInstance();
     cal.set(Calendar.YEAR, Integer.parseInt(v_if_dd.substring(0,4))); 
     cal.set(Calendar.MONTH, Integer.parseInt(v_if_dd.substring(4,6)) - 1); 
     cal.set(Calendar.DATE, Integer.parseInt(v_thisday.substring(6,8))); 
     String v_day_tmp =v_if_mm.substring(0,6)+v_thisday.substring(6,8);

     String v_week_tmp=v_if_mm.substring(0,6)+String.valueOf(cal.get(Calendar.WEEK_OF_MONTH )   );

 %>
                  <!-- 버튼 -->
                  <table width="652" border="0" cellspacing="0" cellpadding="0">
                   <tr> 
                    <td width="507"> <a href="javascript:uf_writeOK();"><img src="../../images/user/button/btn_dayr.gif" border="0" ></a> 
                     <a href="javascript:uf_nextbeforeOK(<%=v_if_dd%>,'DD');"><img src="../../images/user/button/btn_day.gif" border="0"></a> 
                     <a href="javascript:uf_nextbeforeOK(<%=v_week_tmp%>,'WW');"><img src="../../images/user/button/btn_week.gif" border="0"></a> 
                     <a href="javascript:uf_nextbeforeOK(<%=v_if_mm%>,'MM');"><img src="../../images/user/button/btn_month.gif" border="0"></a> 
                    </td>
                    <td width="145" align=right><a href="javascript:uf_nextbeforeOK(<%=v_bef_day%>,'DD');"><img src="../../images/user/button/a_pre.gif" border="0" align="absmiddle"></a><strong><%=v_if_dd.substring(0,4)+"년"+v_if_dd.substring(4,6)+"월"+v_if_dd.substring(6)+"일"%>
                      </strong><a href="javascript:uf_nextbeforeOK(<%=v_aft_day%>,'DD');"><img src="../../images/user/button/a_next.gif" border="0" align="absmiddle"></a></td>
                    </tr>
                    <tr><td height="2" colspan="2"></td></tr>
                   </table>

                    <!-- 게시판보기 -->
                    <table width="652" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
                     <tr  class="lcolor"><td height="3" colspan="2" class="<%=v_title_color[0]%>"></td></tr>
<%
  String v_daytext="";
  String[] v_time_cd={"00","01","02","03","04","05","06","07","08","09","10","11"
                     ,"12","13","14","15","16","17","18","19","20","21","22","23"};
  String[] v_time_fix={"12","01","02","03","04","05","06","07","08","09","10","11"
                      ,"12","01","02","03","04","05","06","07","08","09","10","11"};
  String[] v_time_nm={"","","","","","","","","","","","","","","","","","","","","","","",""};

  for(int k = 0; k < list.size(); k++) {
      DataBox dbox = (DataBox)list.get(k);
      System.out.println(dbox.getString("d_ftime")+":"+dbox.getString("d_ttime"));
      if("Y".equals(dbox.getString("d_loop_fg"))){
        if(Long.parseLong(v_if_dd) >= Long.parseLong(dbox.getString("d_calday"))){
          if("Y".equals(dbox.getString("d_day_fg"))){
             v_daytext = "<a href=javascript:uf_viewOK("+dbox.getString("d_calno")+");>"+"00:00"+"~"+"24:00"+"&nbsp;"+dbox.getString("d_title")+"</a><br>";
             for(int kk=0;kk<24;kk++){
                v_time_nm[kk] += v_daytext;
             }
          } else {
            String v_tmp_ftime=dbox.getString("d_ftime").substring(0,2)+":"+dbox.getString("d_ftime").substring(2);
            String v_tmp_ttime=dbox.getString("d_ttime").substring(0,2)+":"+dbox.getString("d_ttime").substring(2);
            v_daytext = "<a href=javascript:uf_viewOK("+dbox.getString("d_calno")+");>"+v_tmp_ftime+"~"+v_tmp_ttime+"&nbsp;"+dbox.getString("d_title")+"</a><br>";
            v_time_nm[Integer.parseInt(v_tmp_ftime.substring(0,2))]+=v_daytext;
          }
        }
      } else {
         if(Long.parseLong(v_if_dd) >=Long.parseLong(dbox.getString("d_calday"))&&Long.parseLong(v_if_dd) <= Long.parseLong(dbox.getString("d_limit_day"))){
          if("Y".equals(dbox.getString("d_day_fg"))){
            v_daytext = "<a href=javascript:uf_viewOK("+dbox.getString("d_calno")+");>"+"00:00"+"~"+"24:00"+"&nbsp;"+dbox.getString("d_title")+"</a><br>";
             for(int kk=0;kk<24;kk++)v_time_nm[kk]+= v_daytext;

          } else {
            String v_tmp_ftime=dbox.getString("d_ftime").substring(0,2)+":"+dbox.getString("d_ftime").substring(2);
            String v_tmp_ttime=dbox.getString("d_ttime").substring(0,2)+":"+dbox.getString("d_ttime").substring(2);
            v_daytext = "<a href=javascript:uf_viewOK("+dbox.getString("d_calno")+");>"+v_tmp_ftime+"~"+v_tmp_ttime+"&nbsp;"+dbox.getString("d_title")+"</a><br>";
            v_time_nm[Integer.parseInt(v_tmp_ftime.substring(0,2))] +=v_daytext;
          }
         }
      }
  }
%>
<% 
    for(int j=0;j<24;j++){
     String v_cls=v_title_color[1];
     if(j%2==0)v_cls=v_title_color[2];
%>
                     <tr> 
                      <td class="<%=v_cls%>" width=100><%if(Integer.parseInt(v_time_cd[j])>11){out.print("오후");} else{out.print("오전");}%><%=v_time_fix[j]%></td>
                      <td class="tbl_gleft"><%=v_time_nm[j]%></td>
                     </tr>
<%}%>
                    </table>
<%}%>

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
