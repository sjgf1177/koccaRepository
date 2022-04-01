<%
//**********************************************************
//  1. 제      목: PROJECT SUBMIT ADMIN LIST
//  2. 프로그램명: za_ProjectSubmitAdmin_L.jsp
//  3. 개      요: 리포트 평가 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");             
    String  v_subj      = "";
    String  v_year      = "";
    String  v_subjseq   = "";
	String  v_subjseqgr   = "";
    String  v_isclosed  = "";
    String  v_subjnm    = "";
    String v_isnewcourse =""; 
    String  v_course    = "";    
    String  v_cyear     = "";
    String  v_courseseq = "";          
    String  v_isonoff   = "";
    String  v_pjcnt     = "";
    String  v_micnt     = "";    
    String  v_coursenm  = "";   
    String  v_closed_value = "";
    String  v_onoff_value  = "";
    int     v_rowspan   =  0;       
    int     i            = 0;
    ArrayList list1      = null; 
    //DEFINED class&variable END

    //DEFINED in relation to select START
    
	String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_grseq     = box.getStringDefault("s_grseq","ALL");            //교육차수
    String  ss_grseqnm   = box.getString("s_grseqnm");          //교육차수명
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse= box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");          //과정 차수


    String  ss_action    = box.getString("s_action");
    String  v_ongo      = "whenSelection('go')";       

    //DEFINED in relation to select END
        
    if(ss_action.equals("go")){    //go button 선택시만 list 출력
        list1 = (ArrayList)request.getAttribute("projectSubmitAList");      
    }    
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    
    
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
$(document).ready(function(){
	$("#p_propstart1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_propstart1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
    function whenSelection(ss_action) {
      if (ss_action=="go")	{
//            if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
  //              alert("교육주관을 선택하세요.");
    //            return ;
      //      }
            top.ftop.setPam();
      }
//임시      document.form1.s_action.value = ss_action;
      document.form1.action='/servlet/controller.study.ProjectAdminServlet';
      document.form1.p_process.value = 'ProjectSubmitTutor';      
      document.form1.submit();
    }
    
    function projectlist_select(subj,subjnm,year,subjseq) {
        document.form1.action='/servlet/controller.study.ProjectAdminServlet';
        document.form1.p_process.value = 'ProjectSubmitList';
        document.form1.p_subj.value = subj;        
        document.form1.p_subjnm.value = subjnm;        
        document.form1.p_year.value = year;     
        document.form1.p_subjseq.value = subjseq;  
        document.form1.submit();
    }
    
  function whenOrder(column) {
	if (document.form1.p_orderType.value == " asc") {
		document.form1.p_orderType.value = " desc";
	} else {
		document.form1.p_orderType.value = " asc";
	}

// 임시    document.form1.s_action.value = "go";

    document.form1.p_orderColumn.value = column;
    whenSelection("go");
}
-->    	
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
    <form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->    
    <input type="hidden" name="p_subj">
    <input type="hidden" name="p_subjnm">
    <input type="hidden" name="p_year">
    <input type="hidden" name="p_subjseq">
    
    <input type="hidden" name="p_orderColumn">
	<input type="hidden" name="p_orderType" value="<%=v_orderType%>">              

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title08.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>      
      <!----------------- title 끝 ----------------->
        <br>
            <!-- 교육그룹, 년도 시작 -->
        <table class="form_table_out" cellspacing="0" cellpadding="1">
          <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg">
              <tr>
                <td height="7" width="99%"></td>
              </tr>
              <tr>
                <td align="center" width="99%" valign="middle">
                  <table width="99%" cellspacing="0" cellpadding="0" class="form_table">
                    <tr>
                      <td>
                      
						<!------------------- 조건검색 시작 ------------------------->
				  		 <table cellspacing="0" cellpadding="0" width="99%">
				  		 	<tr>
				  		 		<td>
				  		 			<table border="0" cellspacing="0" cellpadding="0" width="99%">
				  		 				<tr>
										  <td width="45%" colspan="2">학습기간
											<input name="p_propstart1" id="p_propstart1" type="text" class="datepicker_input1" size="10" value=""> 일 ~ 
											<input name="p_propstart1" id="p_propstart1" type="text" class="datepicker_input1" size="10" value=""> 일
										   </td>
				  		 					<td width="45%">
											<!-- 교육주관 시작 -->
				  								<!--<%= SelectEduBean.getGrcode(box, true, true)%>--> <!-- getGrcode(RequestBox, isChange, isALL)   교육주관  -->
											<!-- 교육주관 종료 -->
				  							</td>
				  							<!-- <td width="75%">
											<!-- 교육년도, 분류 시작 ->
				  								<%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도  -->
				  								<!-- <input type="hidden" name="s_grseq"  value="0001">
				  								&nbsp;<%= SelectSubjBean.getUpperClass(box, true, true, true)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
				  								<!--&nbsp;<%= SelectSubjBean.getMiddleClass(box, true, true, true)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
				  								<!--&nbsp;<%= SelectSubjBean.getLowerClass(box, true, true, true)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
											<!-- 교육년도, 분류 끝 ->
				  							</td> -->
				  						</tr>
				  					</table>
								</td>
							</tr>
							<tr>
								<td height="3"></td>
							</tr>
							<tr>
								<td>
									<table border="0" cellspacing="0" cellpadding="0" width="99%">
										<tr>
											<td width="65%">
											<!-- 과정,차수,회사 시작 -->
				  								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<%= SelectSubjBean.getSubj(box, true, true)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
				  								&nbsp;<%= SelectSubjBean.getSubjseq(box, false, true)%><!-- getSubjseq(RequestBox, isChange, isALL)    과정차수  -->
											<!-- 과정,차수,회사 종료 -->
				  							</td>
				  							<td  width="10%" align="right">	
				  				    			<%@ include file="/learn/admin/include/za_GoButton.jsp" %><!-- whenSelection('go') -->
				  				    		</td>
				  				 		</tr>
				  				 	</table>
				  				</td>
				  			</tr>
				  		</table>
				  		<!-------------------- 조건검색 끝 ---------------------------->
					  </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="7" width="99%"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!-- 교육그룹, 년도 끝 -->   
        <br>
        <br>

        <!----------------- Activity 제출관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="10" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="5%" class="table_title"><b>NO</b></td>
            <td class="table_title"><a href="javascript:whenOrder('grcode')" class="e">교육그룹</a></td>
            <td class="table_title"><a href="javascript:whenOrder('gyear')" class="e">교육년도</a></td>
            <td class="table_title"><a href="javascript:whenOrder('subjnm')" class="e">회사명</a></td>
            <td class="table_title"><a href="javascript:whenOrder('subjnm')" class="e">학습기간</a></td>
            <td class="table_title"><a href="javascript:whenOrder('subjnm')" class="e">과정</a></td>
            <td class="table_title"><a href="javascript:whenOrder('pjcnt')" class="e">제출개수</a></td>
            <td class="table_title" width="15%"><b>리포트 list 조회</b></td>
            <td class="table_title"><a href="javascript:whenOrder('micnt')" class="e">미채점</a></td>
            <td class="table_title" width="10%"><b>교육상태</b></td>
          </tr>
          <% 
            if(ss_action.equals("go")){    //go button 선택시만 list 출력
                for(i = 0; i < list1.size(); i++) {
                    ProjectData data= (ProjectData)list1.get(i);
                    v_course        = data.getCourse();
                    v_cyear         = data.getCyear();
                    v_courseseq     = data.getCourseseq();
                    v_coursenm      = data.getCoursenm();                          
                    v_subj      = data.getSubj();     
                    v_year      = data.getYear();     
                    v_subjseq   = data.getSubjseq();  
					v_subjseqgr   = data.getSubjseqgr();  
                    v_isclosed  = data.getIsclosed(); 
                    v_subjnm    = data.getSubjnm();   
                    v_isonoff   = data.getIsonoff();  
                    v_pjcnt     = data.getPjcnt();  
                    v_micnt     = data.getMicnt();  
                    v_rowspan   = data.getRowspan();
                    v_isnewcourse=data.getIsnewcourse();
                    if(v_isclosed.equals("Y"))      {  v_closed_value = "완료";   }
                    else                            {  v_closed_value = "진행중"; }
                    //else if(v_isclosed.equals("N")) {  v_closed_value = "진행중"; }                    
                    if(v_isonoff.equals("ON"))      {  v_onoff_value  = "사이버과정"; }
                    else                            {  v_onoff_value  = "집합과정";   } 
                    //else if(v_isonoff.equals("OFF")){  v_onoff_value  = "집합과정";   } 
               %>
          <tr> 
            <td class="table_01"><%= i+1 %></td>
            <td class="table_02_1"></td>
            <td class="table_02_1"></td>
            <td class="table_02_1">현대자동차</td>
            <td class="table_02_1">2005.01.01~2005-01.31</td>
            <%  if(v_course.equals("000000")){   %>
            <!-- 과정인 경우 -->
            <td class="table_02_1"> <%=v_subjnm%> </td>
            <% }else if(v_isnewcourse.equals("Y")){ %>
            <!-- 코스이면서 이전 코스와 일치하지 않는경우 -->
            <!--td class="table_02_1" rowspan="<%=v_rowspan%>">
                 <font class="text_color03">[코스] <%=v_coursenm%><%=v_courseseq%></font>
                 </td-->
            <% } %>
            <%  if(!v_course.equals("000000")){   %>
            <!-- 코스인 경우 -->
            <!--td class="table_02_1">
                <a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>')" class="b"><%=v_subjnm%></a>
                </td-->
            <% } %>
            <td class="table_02_1"><%=v_pjcnt%>개</td>
            <td class="table_02_1"> <a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>')"><img src="/images/admin/button/b_reportlist.gif" border="0"></a> 
            </td>
            <td class="table_02_1"><%=v_micnt%>개</td>
            <td class="table_02_1"><%=v_closed_value%></td>
          </tr>
          <% 
              } 
          }  
          if(ss_action.equals("go") && i == 0){ %>
          <tr> 
            <td class="table_02_1" colspan="8">등록된 내용이 없습니다</td>
          </tr>
          <%  } %>
        </table>
        <!----------------- Activity 제출관리 끝 ----------------->
        <br>
     </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>    
</table>
</form>

</body>
</html>
