<%
//**********************************************************
//  1. 제      목: PROJECT QUESTION ADMIN LIST
//  2. 프로그램명: za_ProjectQuestionsAdmin_L.jsp
//  3. 개      요: 리포트 출제 관리
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
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process		= box.getString("p_process");
	String  v_searchtext	= box.getString("p_searchtext");  
    String  v_year			= box.getString("p_year");

    String  v_subj			= "";
    String  v_subjnm		= "";
	String  v_year1			= "";
	String  v_subjseq		= "";
    String  v_isonoff		= "";
    String  v_onoff_value	= "";  
	int 	v_projseqcnt	= 0;
	int     v_ordseqcnt		= 0;   
    int     v_rowspan		= 0;    
    int     i				= 0;
    ArrayList list1			= null;
    //DEFINED class&variable END
    String  ss_action    = box.getString("s_action");
        
    if(ss_action.equals("go")){    //go button 선택시만 list 출력
        list1 = (ArrayList)request.getAttribute("projectQuestionsAList");
    }    
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

	int t_year = Integer.parseInt(FormatDate.getDate("yyyy"))-1;

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    function whenSelection(ss_action) {
       if (ss_action=="go")	{
			if (document.form1.p_cp.value == "----"){
				alert("교육업체를 선택하세요!");
				return;
			}
            top.ftop.setPam();
      }
      document.form1.s_action.value = ss_action;
      document.form1.action='/servlet/controller.cp.BPProjectAdminServlet';
      document.form1.p_process.value = 'ProjectQuestionsAdmin';      
      document.form1.submit();
    }
    
    // 리포트 리스트 보기 
    function projectlist_select(subj,subjnm,year,subjseq) {
        document.form1.action='/servlet/controller.cp.BPProjectAdminServlet';
        document.form1.p_process.value = 'ProjectQuestionsList';
        document.form1.p_subj.value = subj;        
        document.form1.p_subjnm.value = subjnm;        
        document.form1.p_year1.value = year;     
        document.form1.p_subjseq.value = subjseq;  
        document.form1.submit();
    }
    
        function whenOrder(column) {
	if (document.form1.p_orderType.value == " asc") {
		document.form1.p_orderType.value = " desc";
	} else {
		document.form1.p_orderType.value = " asc";
	}

    document.form1.s_action.value = "go";

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
    <input type="hidden" name="p_year1">
    <input type="hidden" name="p_subjseq">
    
    <input type="hidden" name="p_orderColumn">
	<input type="hidden" name="p_orderType" value="<%=v_orderType%>">              

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/beta/tit_report.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>    
      <!----------------- title 끝 ----------------->
        <br>
            <!-- 교육그룹, 년도 시작 -->
        <table width="97%" class="form_table_out" cellspacing="0" cellpadding="1">
          <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table width="97%" cellspacing="0" cellpadding="0" class="form_table_bg">
              <tr>
                <td height="7"></td>
              </tr>
			  <tr>
                <td align="center">
				  <table border="0" cellspacing="0" cellpadding="0">
					<tr>
					  <td>개설년도</td>
					  <td>&nbsp; <%= CPCommonBean.getYear(box, true, true, t_year+"","p_year")%><!-- getCompany(RequestBox, isChange, isALL)    연도  --></td>
					  <td>&nbsp;</td><td  align="right">&nbsp;</td>
					  <td>교육업체</td> 
					  <td> &nbsp;<%= CPCommonBean.getCP(box, true, true)%><!-- getCompany(RequestBox, isChange, isALL)    교육기관  --></td>
					  <td width=20></td>
					  <td>과정명</td>
					  <td width=6></td>
					  <td><input name="p_searchtext" type="text" class="input" style="width:180" value="<%=v_searchtext %>"></td>
					  <td width=6></td>
					  <td><a href="javascript:whenSelection('go');"><img src="/images/admin/button/b_go.gif"  border="0"></a></td>
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
            <td colspan="7" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="5%" class="table_title""><b>NO</b></td>          
            <td width="10%" class="table_title"><a href="javascript:whenOrder('isonoff')" class="e">구분</a></td>          
            <td class="table_title"><a href="javascript:whenOrder('subjnm')" class="e">과정</a></td>
            <td width="10%" class="table_title" width="9%"><a href="javascript:whenOrder('subjseq')" class="e">과정차수</a></td>
            <td width="15%" class="table_title" width="15%"><b>리포트 list 조회</b></td>
            <td width="10%" class="table_title" width="10%"><a href="javascript:whenOrder('projseqcnt')" class="e">리포트<br>셋트 갯수</a></td>
            <td width="10%" class="table_title" width="10%"><a href="javascript:whenOrder('ordseqcnt')" class="e">리포트<br>문제 갯수</a></td>
          </tr>
          <% 
            if(ss_action.equals("go")){    //go button 선택시만 list 출력
                for(i = 0; i < list1.size(); i++) {
                    BPProjectData data= (BPProjectData)list1.get(i);
                    v_subj          = data.getSubj();     
                    v_subjnm        = data.getSubjnm(); 
                    v_year1	        = data.getYear(); 
                    v_subjseq       = data.getSubjseq(); 
					v_projseqcnt    = data.getProjseqcnt();
					v_ordseqcnt     = data.getOrdseqcnt();
                    v_isonoff       = data.getIsonoff();  
                    if(v_isonoff.equals("ON"))      {  v_onoff_value  = "사이버과정"; }
                    else if(v_isonoff.equals("OFF")){  v_onoff_value  = "집합과정";   } 
               %>
          <tr> 
            <td class="table_01"><%= list1.size() - i %></td>
            <td class="table_02_1"><%=v_onoff_value%></td>           
            <td class="table_02_2"> <%=v_subjnm%> </td>
            <td class="table_02_1"><%=v_subjseq%></td>
            <td class="table_03_1"> <a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year1%>','<%=v_subjseq%>')"><img src="/images/admin/button/b_reportlist.gif" border="0"></a> 
            </td>
            <td class="table_02_1"><%=v_projseqcnt%></td>
            <td class="table_02_1"><%=v_ordseqcnt%></td>
          </tr>
          <%             
              } 
          }  
          if(ss_action.equals("go") && i == 0){ %>
          <tr> 
            <td class="table_02_1" colspan="7">등록된 내용이 없습니다</td>
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
