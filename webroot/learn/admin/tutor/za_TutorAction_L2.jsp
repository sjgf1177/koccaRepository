<%
//**********************************************************
//  1. 제      목: 강사활동관리
//  2. 프로그램명: za_TutorAction_L.jsp 
//  3. 개      요: 강사평가관리 조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.tutor.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    int     v_pageno        = box.getInt("p_pageno");
    int     v_pagesize      = box.getInt("p_pagesize");
    int     v_totalrowcount =  0;
    int     v_dispnum       = 0;           // 총게시물수
    int     v_totalpage     = 0;           // 게시물총페이지수         
    String  v_grseq     =  "";
    String  v_course    =  "";	
    String  v_cyear     =  "";
    String  v_courseseq =  "";
    String  v_coursenm  =  "";
    String  v_subj      =  "";
    String  v_year      =  "";
    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_propstart =  "";
    String  v_propend   =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_isonoff   =  "";
    String  v_isonoff_value=""; 
    String  v_isnewcourse= "";    
	int		v_seq		=	0;
    int     v_studentlimit =  0;    
    int     v_rowcount  =  0;    
    int     v_rowspan   =  0;                    
    int     v_total     =  0;   
    int     i           =  0;
    int     v_stucnt    =  0;
    int     v_comcnt    =  0;
    int     v_cancnt    =  0;
    int		v_grayncnt	= 0;
	int     v_totalstucnt      =  0;
    int     v_totalcomcnt      =  0;
    int     v_totalcancnt      =  0;
    String	v_grayntxt = "";;
    String  v_tuuserid = "";
    String	v_isclosed = box.getString("p_isclosed");
    
    String  ss_action    = box.getString("s_action");

	String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
 
	ArrayList list      = null;
    //DEFINED class&variable END

    if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("actionlist");
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script> 
<SCRIPT LANGUAGE="JavaScript">
<!--
		//select box 선택시
    function whenSelection(p_action) { 
        document.form1.s_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.tutor.TutorActionAdminServlet';
        document.form1.p_process.value = 'listPage2';  
        document.form1.submit();
    }
    
    //정렬 선택시
    function whenOrder(column) {
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.tutor.TutorActionAdminServlet';
        document.form1.p_process.value = 'listPage2';   
        document.form1.p_orderColumn.value = column;
        document.form1.submit();
    }    
		
		//내용 상세보기
    function whenTutorDetail(subj, year, subjseq, userid, name, subjnm){
    	window.self.name = "TutorActionDetail";     
        open_window("openTutor","","100","100","730","700","N","yes","yes","yes","yes");  
        document.form1.target = "openTutor";               
        document.form1.action = '/servlet/controller.tutor.TutorValuationAdminServlet';
        document.form1.p_process.value = 'detailView';
        document.form1.p_subj.value = subj;
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_userid.value = userid;
        document.form1.p_name.value = name;
        document.form1.p_subjnm.value = subjnm;
        document.form1.submit();
    }
	
	//질문리스트
	function whenQnaList(subj, year, subjseq) {    	  
		window.self.name = "open";     
		open_window("opensel","","100","0","1000","600","N","yes","yes","yes","yes");  
		document.form1.target = "opensel";               
				   
		document.form1.action='/servlet/controller.tutor.TutorAdminMainServlet';	
		document.form1.p_process.value = 'QnaListPage';
		document.form1.p_subj.value = subj;
		document.form1.p_year.value = year;
		document.form1.p_subjseq.value = subjseq;
//		document.form1.p_seq.value = seq;
		document.form1.submit();
	}
	
	//레포트 리스트
	function projectlist_select(subj,subjnm,year,subjseq) {
		document.form1.action='/servlet/controller.tutor.TutorAdminMainServlet';
		document.form1.target = "_self";
		document.form1.p_process.value = 'ProjectDetailList';
		document.form1.p_subj.value = subj;        
		document.form1.p_subjnm.value = subjnm;        
		document.form1.p_year.value = year;     
		document.form1.p_subjseq.value = subjseq;  
		document.form1.submit();
	}
	
	//정렬
    function whenOrder(column) {
		if (document.form1.p_orderType.value == " asc") {
			document.form1.p_orderType.value = " desc";
		} else {
			document.form1.p_orderType.value = " asc";
		}

        document.form1.target = "_self";
        document.form1.action='/servlet/controller.tutor.TutorActionAdminServlet';
        document.form1.p_process.value = 'listPage2';   
        document.form1.p_orderColumn.value = column;
        document.form1.submit();
    }    

    function go(index) {
        document.form1.p_pageno.value = index;
        document.form1.action = "/servlet/controller.tutor.TutorActionAdminServlet";
        document.form1.p_process.value = "listPag2e";
        document.form1.submit();
   }
   function goPage(pageNum) {
        document.form1.p_pageno.value = pageNum;
        document.form1.action = "/servlet/controller.tutor.TutorActionAdminServlet";
        document.form1.p_process.value = "listPage2";
        document.form1.submit();
   }

    function pagesize(pageSize) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_pagesize.value = pageSize;
        document.form1.action = '/servlet/controller.tutor.TutorActionAdminServlet';
        document.form1.p_process.value = "listPage2";
        document.form1.submit();
    }

-->    	
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
    <input type="hidden" name="p_process" value="">
     <input type="hidden" name="p_pageno" value="<%=v_pageno %>">    
    <input type="hidden" name="p_pagesize" value="<%=v_pagesize %>"> 
    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->   
    <input type = "hidden" name="p_subj">
		<input type = "hidden" name="p_year">
		<input type = "hidden" name="p_subjseq">
		<input type = "hidden" name="p_userid">
		<input type = "hidden" name="p_subjnm">
		<input type = "hidden" name="p_name">
		<input type = "hidden" name="p_seq">
  	<input type = "hidden" name="p_orderColumn">
		<input type = "hidden" name="p_orderType" value="<%=v_orderType%>">
	
	
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="/images/admin/tutor/tit_new_act.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 --------------- -->
      <br>

      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td align="center" valign="middle">
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
				    <tr>
					  <td width="90%" height="50">
						<input type="radio" name="p_isclosed" value="" <%=v_isclosed.equals("")?"checked":""%>>전체
						<input type="radio" name="p_isclosed" value="Y" <%=v_isclosed.equals("Y")?"checked":""%>>교육종료
						<input type="radio" name="p_isclosed" value="N" <%=v_isclosed.equals("N")?"checked":""%>>교육중
					  </td>	
            	      <td width="10%" align="right"><a href="javascript:whenSelection('go')"><img src="/images/admin/button/b_go.gif"  border="0" align=absmiddle></a></td>
					</tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!----------------- form 끝 ----------------->
      <br>

      <!----------------- 엑셀출력 버튼 시작 ----------------->
      <!--table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td align="right">&nbsp;</td>
			<td align="right" valign="top" width="88">
			<a href="javascript:whenFreeMail()"><img src="http://localhost:9999/images/admin/button/btn_mail.gif" border="0"></a>
			</td>            
			<td align="right" valign="top" width="88">
			<a href="javascript:whenExcel()"><img src="/images/admin/complete/btn_excelprint.gif" border="0"></a>
			</td>
			<td align="right" valign="top" width="88">
			<a href="javascript:whenAllSelect()"><img src="/images/admin/button/btn_allcheck.gif" border="0"></a>
			</td>  
			<td align="right" valign="top" width="88">
			<a href="javascript:whenAllSelectCancel()"><img src="/images/admin/button/btn_allcheckout.gif" border="0"></a></td>
        </tr>
      </table-->
      <!----------------- 엑셀출력 버튼 끝 ----------------->
      <!----------------- 입과명단조회 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
          <tr> 
<!--            <td colspan="19" class="table_top_line"></td>-->
			<td colspan="18" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="5%" class="table_title" rowspan="2">No</td>
            <td class="table_title" rowspan="2"><a href="javascript:whenOrder('c.subjnm')" class="e">과정명</a></td>            
            <td width="10%" class="table_title" rowspan="2"><a href="javascript:whenOrder('c.edustart')" class="e">학습기간</a></td>
 			      <td width="7%" class="table_title" rowspan="2"><a href="javascript:whenOrder('ctutorcnt')" class="e">총강사수</a></td>
						<td width="5%" class="table_title" rowspan="2"><a href="javascript:whenOrder('stucnt')" class="e">입과<br>인원</a></td>
						<td width="5%" class="table_title" rowspan="2"><a href="javascript:whenOrder('grayncnt')" class="e">수료<br>인원</a></td>
						<td width="7%" class="table_title" rowspan="2"><a href="javascript:whenOrder('logincnt')" class="e">강사<br>접속횟수</a></td>
						<td width="7%" class="table_title" rowspan="2">본인게시건수<br>(공지/자료/토론)</td>
						<td width="7%" class="table_title" colspan="2">리포트</td>
						<td width="7%" class="table_title" colspan="2">Q & A</td>
<!--						<td width="6%" class="table_title" rowspan="2"><a href="javascript:whenOrder('grayncnt')" class="e">강사료</td>-->
            <td width="7%" class="table_title" rowspan="2"><a href="javascript:whenOrder('ldate')" class="e">수료<br>처리일</a></td>
          </tr>
          <tr> 
            <td class="table_title"><a href="javascript:whenOrder('repcnt')" class="e">제출</a></td>
            <td class="table_title"><a href="javascript:whenOrder('noscorecnt')" class="e">미평가</a></td>            
            <td class="table_title"><a href="javascript:whenOrder('qcnt')" class="e">질의</a></td>
            <td class="table_title"><a href="javascript:whenOrder('qcnt')" class="e">미응답</a></td>
          </tr>

<%                          
            if (ss_action.equals("go")) {    //go button 선택시만 list 출력
		
                          
	         	for( i = 0 ; i < list.size() ; i++ ){
					DataBox dbox = (DataBox)list.get(i);		  
							
					v_subj = dbox.getString("d_subj");  
					v_subjnm = dbox.getString("d_subjnm");  
					v_year = dbox.getString("d_year");
					v_subjseq = dbox.getString("d_subjseq");
					v_seq = dbox.getInt("d_seq");
					v_tuuserid = dbox.getString("d_tuserid");
					v_grayncnt  = dbox.getInt("d_grayncnt");
					v_isclosed  = dbox.getString("d_isclosed");
					v_edustart  = dbox.getString("d_edustart");
					v_eduend  = dbox.getString("d_eduend");
					
					if(v_isclosed.equals("Y")){
							v_grayntxt = String.valueOf(v_grayncnt);
					}else{
					        v_grayntxt = "교육중";
				    }
					
					v_dispnum   = dbox.getInt("d_dispnum");
                    v_totalpage = dbox.getInt("d_totalpage");
                    v_totalrowcount = dbox.getInt("d_totalrowcount");
					
					
	%>
			        <tr height="30"> 
			            <td class="table_02_1"><%=v_dispnum%></td>
			            <td class="table_02_2"><%=dbox.getString("d_subjnm")%></td>
									<td class="table_02_1"><%=FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")+" ~ "+FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")%></td>
			            <td class="table_02_1"><%=dbox.getString("d_ctutorcnt")%></td>
			            <td class="table_02_1"><%=dbox.getString("d_stucnt")%></td>
			            <td class="table_02_1"><%=v_grayntxt%></td>
			            <td class="table_02_1"><%=dbox.getString("d_logincnt")%></td>
			            <td class="table_02_1"><%=dbox.getInt("d_gongcnt")+dbox.getInt("d_datacnt")+dbox.getInt("d_toroncnt")+dbox.getInt("d_torontpcnt")%></td>
			            <td class="table_02_1"><%=dbox.getString("d_repcnt")%></td>
			            <td class="table_02_1"><a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>');"><%=dbox.getString("d_noscorecnt")%><a></td>
			            <td class="table_02_1"><%=dbox.getString("d_qcnt")%></td>
									<td class="table_02_1">
										<a href="javascript:whenQnaList('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>');"><%=dbox.getString("d_noanscnt")%><a></td>
<!--			            <td class="table_02_1"><%=dbox.getString("d_pay")%></td>-->
			            <td class="table_02_1"><%=v_isclosed.equals("Y")?FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyy/MM/dd"):""%></td><!--수료처리일 칼럼이 없어 LDATE 결과 가져옴-->
			         </tr>
	<%
					
	            }
		   
         }
         if(i == 0 && ss_action.equals("go")){ %>
              <tr> 
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="19">등록된 내용이 없습니다</td>
              </tr>
                <% } else{ %>   </table> <% } %>
                
                
               <%  if(i > 0){ %>
                <table class="table1" cellspacing="0" cellpadding="0">
                  <tr> 
                    <td align="center"><%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %></td>
                  </tr>
                </table>  
                <br>        
                <% } %>  
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
