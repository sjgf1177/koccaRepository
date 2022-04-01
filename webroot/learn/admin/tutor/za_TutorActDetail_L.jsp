<%
//**********************************************************
//  1. 제      목: 강사활동 상세조회
//  2. 프로그램명: za_TutorActDetail_L.jsp
//  3. 개      요: 강사활동 상세조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 김수진
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    int v_pageno        = box.getInt("p_pageno");

    String  ss_grcode    = box.getString("s_grcode");        //교육그룹
    String  ss_gyear     = box.getString("s_gyear");         //년도
    String  ss_grseq     = box.getString("s_grseq");         //교육차수
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse= box.getString("s_subjcourse");    //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");       //과정 차수
    String  ss_edustart  = box.getString("s_start");            //교육시작일
    String  ss_eduend    = box.getString("s_end");              //교육종료일

    String v_subj = box.getString("p_subj");
    String v_year = box.getString("p_year");
    String v_subjseq = box.getString("p_subjseq");
    String v_userid = box.getString("p_userid");
    String v_subjnm = box.getString("p_subjnm");
    String v_name = box.getString("p_name");
    String v_isclosed = box.getString("p_isclosed");


    DataBox dboxcnt =  (DataBox)request.getAttribute("actcnts");  //활동 카운트
    ArrayList list1 =  (ArrayList)request.getAttribute("actlist");  //활동 리스트
    ArrayList list2 =  (ArrayList)request.getAttribute("loginlist");  //로그인 리스트
    ArrayList list3 =  (ArrayList)request.getAttribute("maillist");  //메일발송 리스트

    String v_malevel = "";
    int v_logcnt   = 0;
	int v_gongcnt  = 0;
	int v_qnacnt   = 0;
	int v_datacnt  = 0;
	int v_toroncnt = 0;
	int v_total    = 0;

    if(dboxcnt!=null)
    {
        v_malevel = dboxcnt.getString("d_malevel");
        v_logcnt   = dboxcnt.getInt("d_logincnt");
        v_gongcnt  = dboxcnt.getInt("d_gongcnt");
        v_qnacnt   = dboxcnt.getInt("d_qnacnt");
        v_datacnt  = dboxcnt.getInt("d_datacnt");
        v_toroncnt = dboxcnt.getInt("d_toroncnt") + dboxcnt.getInt("d_torontpcnt");
        v_total    = v_gongcnt + v_qnacnt + v_datacnt + v_toroncnt;
    }

	int i = 0;
%>
<html>
<head><title>강사활동 상세조회</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
//저장할때
function whenSave(isclosed){
//	var malevel = document.form1.p_malevel.options[document.form1.p_malevel.selectedIndex].value;
	var malevel = document.form1.p_malevel.value;

	if(isclosed == "N"){
		alert("교육중에는 강사를 평가할 수 없습니다.");
		return;
	}

	if(malevel == ""){
		alert("등급을 선택해 주세요");
		return;
	}

	document.form1.action = '/servlet/controller.tutor.TutorValuationAdminServlet';
    document.form1.p_process.value = 'calcTutorGrade';
    document.form1.submit();
}
//상세정보
function whenDetail(gubun, tpcode,seq,lesson) {
        window.self.name = "open";
        open_window("opensel","","100","100","600","400","N","yes","yes","yes","yes");
        document.form1.target = "opensel";

        var v_action = "", v_process="";
        if(gubun == "qna"){
        	v_action = '/servlet/controller.study.QnaAdminServlet';
        	v_process = 'QnaDetail';
        }else if(gubun == "torontp"){
            v_action = "/servlet/controller.study.ToronAdminServlet";
        	v_process = "TopicSelect";
        }else if(gubun == "toron"){
            v_action = "/servlet/controller.study.ToronAdminServlet";
        	v_process = "ToronSelect";
        }else if(gubun == "gong"){
            v_action =  "/servlet/controller.course.SubjGongAdminServlet";
        	v_process = "selectView";
        }else if(gubun == "data"){
            v_action = "/servlet/controller.study.StudyAdminDataServlet";
        	v_process = "select";
        }

        document.form1.action = v_action;
        document.form1.p_process.value = v_process;
				document.form1.p_lesson.value = lesson;
        document.form1.p_seq.value = seq;
        document.form1.p_qyear.value = document.form1.p_year.value;
        document.form1.p_qsubjseq.value =  document.form1.p_subjseq.value;
        document.form1.p_tpcode.vlaue = tpcode
        document.form1.submit();
}

</SCRIPT>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
<form name = "form1" method = "post">
<input type="hidden" name="p_process"   value="<%=v_process%>">
<input type="hidden" name="s_grcode"    value="<%=ss_grcode%>" >
<input type="hidden" name="s_gyear"      value="<%=ss_gyear%>" >
<input type="hidden" name="s_grseq"      value="<%=ss_grseq%>" >
<input type="hidden" name="s_upperclass" value="<%=ss_upperclass%>" >
<input type="hidden" name="s_subjcourse" value="<%=ss_subjcourse%>" >
<input type="hidden" name="s_subjseq"    value="<%=ss_subjseq%>" >
<input type="hidden" name="s_action"     value="go">
<input type="hidden" name="p_subj"    value="<%=v_subj%>">
<input type="hidden" name="p_year"    value="<%=v_year%>">
<input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
<input type="hidden" name="p_userid"  value="<%=v_userid%>">

	<input type="hidden" name="p_lesson"   value="">
	<input type="hidden" name="p_seq"   value="">
	<input type="hidden" name="p_qyear"   value="">
	<input type="hidden" name="p_qsubjseq"   value="">
	<input type="hidden" name="p_tpcode"   value="">


  <table width="700" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">


   <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
            <td><img src="/images/admin/tutor/tit_tutor_testman.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
           <!----------------- title 시작 ----------------->
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class=sub_title><img src="/images/admin/common/icon.gif"> 강사 활동 상세조회
          </td>
        </tr>
      </table>
      <!----------------- title 끝 --------------- -->
      <br>

      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="3" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">

            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">

                  <table cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td align="center" valign="middle">
                        [ <%=v_subjnm%> : <%=v_name%> ]
			        </td>
                    </tr>
                  </table>


                </td>
              </tr>
              <tr>
                <td height="9"></td>
              </tr>

            </table>
          </td>
        </tr>
      </table>
      <!----------------- form 끝 ----------------->
      <br>
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="right" height="20">&nbsp;</td>
            <td align="right" height="20">&nbsp;평가
			<input type="radio" name="p_malevel" value="A" <% if(v_malevel.equals("A")){  %>checked <%}%>>A
			<input type="radio" name="p_malevel" value="B" <% if(v_malevel.equals("B")){  %>checked <%}%>>B
			<input type="radio" name="p_malevel" value="C" <% if(v_malevel.equals("C")){  %>checked <%}%>>C
			<input type="radio" name="p_malevel" value="D" <% if(v_malevel.equals("D")){  %>checked <%}%>>D
			<input type="radio" name="p_malevel" value="F" <% if(v_malevel.equals("F")){  %>checked <%}%>>F
			<!--select name="p_malevel">
            	<option value="" <% if(v_malevel.equals("")){  %>selected <%}%>>미채점</option>
            	<option value="A" <% if(v_malevel.equals("A")){%>selected <%}%>>A</option>
            	<option value="B" <% if(v_malevel.equals("B")){%>selected <%}%>>B</option>
            	<option value="C" <% if(v_malevel.equals("C")){%>selected <%}%>>C</option>
            	<option value="D" <% if(v_malevel.equals("D")){%>selected <%}%>>D</option>
            	<option value="F" <% if(v_malevel.equals("F")){%>selected <%}%>>F</option>
            </select-->
           <a href="javascript:whenSave('<%=v_isclosed%>');">  <img src="/images/admin/button/btn_save.gif" border="0" align="absmiddle"></a>
          </td>
        </tr>
      </table>
      <br>

      <!----------------- 입과명단조회 시작 ----------------->
      <table  width="700" cellspacing="1" cellpadding="5" class="table_out">
          <tr>
            <td class="table_title">접속횟수</td>
            <td class="table_title">공지</td>
            <td class="table_title">Q&A</td>
            <td class="table_title">자료실</td>
            <td class="table_title">토론실</td>
            <td class="table_title">합계</td>
          </tr>
          <tr>
          	<td class="table_02_1"><%=v_logcnt  %></td>
          	<td class="table_02_1"><%=v_gongcnt %></td>
          	<td class="table_02_1"><%=v_qnacnt  %></td>
          	<td class="table_02_1"><%=v_datacnt %></td>
          	<td class="table_02_1"><%=v_toroncnt%></td>
          	<td class="table_02_1"><%=v_total   %></td>
          </tr>
      </table>
<br>
      <table   width="700" cellspacing="1" cellpadding="1" class="table_out">
      <tr>
            <td class="table_title_02"> ■ 상세활동 내역</td></tr>
      <tr><td class="table_02_1">
      <div style='width:680px;height:170px;overflow:auto;visibility:visible;border:1 '>
       <table   width="100%" cellspacing="1" cellpadding="1" class="table_out">
       <tr>
            <td class="table_title" width="10%">분류</td>
            <td class="table_title" width="45%">제목</td>
            <td class="table_title" width="15%">작성일</td>
            <td class="table_title" width="10%">조회수</td>

          </tr>

          <%
          if(list1 != null){
            for(i=0; i<list1.size() ; i++ ) {
          		DataBox dbox = (DataBox) list1.get(i);
          %>
          <tr>
            <td class="table_02_1" ><%=dbox.getString("d_gubunnm")%></td>
            <td class="table_02_1" >
               	    <!-- <a href="javascript:whenDetail('<%=dbox.getString("d_gubun")%>','<%=dbox.getString("d_tpcode")%>','<%=dbox.getString("d_seq")%>','<%=dbox.getString("d_lesson")%>');">-->
               	    <%=dbox.getString("d_title")%></td>
            <td class="table_02_1" ><%=FormatDate.getFormatDate(dbox.getString("d_addate"),"yyyy/MM/dd")%></td>
            <td class="table_02_1" ><%=dbox.getString("d_cnt")%></td>

         </tr>
          <%
          	  }
           }

           if(i == 0){
		  %>
		   			<tr><td class="table_02_1" colspan=6 >등록된 정보가 없습니다.</td></tr>
		  <%  }  %>

      </table>
      </div>
      </td>
      </tr>
      <tr>
            <td class="table_title_02"> ■ 접속정보</td></tr>
      <tr><td class="table_02_1">
      <div style='width:680px;height:170px;overflow:auto;visibility:visible;border:1 '>
       <table   width="100%" cellspacing="1" cellpadding="1" class="table_out">
       <tr>
            <td class="table_title" width="50">No</td>
            <td class="table_title" width="200">로그인</td>
            <td class="table_title" width="200" >로그아웃</td>
            <td class="table_title" width="150" >IP</td>
            <td class="table_title" width="100" >시간</td>
          </tr>
          <%
              DecimalFormat df = new DecimalFormat("000000");
              String dtime = "";
              if(list2 != null){
          		for( i=0 ; i<list2.size() ; i++ ){
          			DataBox dbox = (DataBox) list2.get(i);
          			//out.println(dbox.getString("d_dtime"));

          			dtime = df.format(dbox.getInt("d_dtime"));

          			%>
		          <tr>
		            <td class="table_02_1" ><%=list2.size()-i%></td>
		            <td class="table_02_1" ><%=FormatDate.getFormatDate(dbox.getString("d_login"),"yyyy-MM-dd HH:mm ss")%></td>
		            <td class="table_02_1" ><%=FormatDate.getFormatDate(dbox.getString("d_logout"),"yyyy-MM-dd HH:mm ss")%></td>
		            <td class="table_02_1" ><%=dbox.getString("d_loginip")%></td>
		            <td class="table_02_1" ><%=FormatDate.getFormatTime(dtime, "HH:mm:ss")%></td>
		         </tr>
		  <%     }
		      }

		      if(i == 0){
		  %>
		   			<tr><td class="table_02_1" colspan=6 >등록된 정보가 없습니다.</td></tr>
		  <%  }  %>

      </table>
      </div>
      </td>
      </tr>
      <!--tr>
            <td class="table_title_02"> ■ 메일발송 내역</td></tr>
      <tr><td class="table_02_1">
      <div style='width:680px;height:170px;overflow:auto;visibility:visible;border:1 '>
       <table   width="100%" cellspacing="1" cellpadding="1" class="table_out">
       <tr>
            <td class="table_title" width="50">No</td>
            <td class="table_title" width="510">제목</td>
            <td class="table_title" width="70" >발신일</td>
            <td class="table_title" width="70" >수신인원수</td>
          </tr>
          <%
              if(list3 != null){
          		for( i=0 ; i<list3.size() ; i++ ){
          			DataBox dbox = (DataBox) list3.get(i);
          			%>
		          <tr>
		            <td class="table_02_1" ><%=list3.size()-i%></td>
		            <td class="table_02_1" ><%=dbox.getString("d_title")%></td>
		            <td class="table_02_1" ><%=FormatDate.getFormatDate(dbox.getString("d_sdate"),"yyyy-MM-dd")%></td>
		            <td class="table_02_1" ><%=dbox.getString("d_rcnt")%></td>
		         </tr>
		  <%     }
		      }

		      if(i == 0){
		  %>
		   			<tr><td class="table_02_1" colspan=6 >등록된 정보가 없습니다.</td></tr>
		  <%  }  %>

      </table>
      </div>
      </td>
      </tr-->
      </table>
      <!----------------- 입과명단 조회 끝 ----------------->
     <br>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
