<%
//**********************************************************
//  1. 제      목: Propose Wizard Search LIST
//  2. 프로그램명: za_ProposeWizard_L.jsp
//  3. 개      요: 교육대상자 마법사 실행?page
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 11. 03
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<!--jsp:useBean id = "code" class = "com.credu.system.CodeConfigBean"  scope = "page" /-->

<%
    //DEFINED class&variable START

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    //out.print("box="+box);
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  v_process   = box.getString("p_process");
    String  v_action    = box.getString("p_action");
    ArrayList list      = null;
    ArrayList list1      = null;
    //ProposeWizardBean   wizbean = new ProposeWizardBean();

    String v_subj          = "";
    String v_year          = "";
    String v_subjseq       = "";
    String v_subjseqgr     = "";
    String v_userid        = "";
    String v_subjnm        = "";
    String v_usercono      = "";
    String v_usercomp      = "";
    String v_userjik       = "";
    String v_prodate       = "";
    String v_wantseq1      = "";
    String v_wantseq2      = "";
    String v_wantseq3      = "";
    String v_appcono       = "";
    String v_appid         = "";
    String v_appmail       = "";
    String v_appsubj       = "";
    String v_appyear       = "";
    String v_appsubjseq    = "";
    String v_appsubjseqgr  = "";
    String v_name          = "";
    String v_orga_ename    = "";
    String v_appname       = "";
    String v_apporga_ename = "";
    String v_isupapproval  = "";
    String v_isdoapproval  = "";
    String v_isadmapproval = "";
    String v_mastercd      = "";
          
    
    int     i           =  0;

   //DEFINED class&variable END

    //DEFINED in relation to select START
    /* 회사정보 select Params */

    //String  v_proposetype = box.getStringDefault("p_proposetype","3");   //수강신청방식
    String  v_proposetype = "";   //수강신청방식
    String  v_ongo        = "whenSelection('go')";
    
    //out.println("v_action="+v_action);
    
    /* 교육 및 과정 차수 정보 selected Params*/
    String  ss_grcode     = box.getString("s_grcode");                      //교육그룹
    String  ss_gyear      = box.getString("s_gyear");                       //년도
    String  ss_grseq      = box.getString("s_grseq");                       //교육차수
    String  ss_subjcourse = box.getString("s_subjcourse");                  //과정&코스
    String  ss_mastercd   = box.getString("s_mastercd");                  //과정&코스
    String  ss_subjseq    = box.getString("s_subjseq");                     //과정 차수
    String  ss_company    = box.getString("s_company");                      //회사
    
    //DEFINED in relation to select END
    if (v_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("ApprovalList");
    }
    //
    
    //out.println(list);
%>
<html>
<head>
<title>차수변경승인</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
function whenSelection(p_action) {
  document.form1.p_action.value = p_action;
  if (p_action == 'go') {
    if (chkParam() == false) {
      return;
    }
    
  }
  document.form1.p_process.value = 'listPage';
  //alert(document.form1.p_process.value);
  document.form1.submit();
}

function chkParam() {
  if (document.form1.s_grcode.value == 'ALL'|| document.form1.s_grcode.value == '----') {
    alert("교육그룹을 선택하세요.");
    return false;
  }
  if (document.form1.s_grseq.value == 'ALL') {
    alert("교육차수를 선택하세요.");
    return false;
  }
  //if (document.form1.s_subjcourse.value == 'ALL') {
  //  alert("과정/코스를 선택하세요.");
  //  return false;
  //}
  //if (document.form1.s_subjseq.value == 'ALL') {
  //  alert("차수를 선택하세요.");
  //  return false;
  //}
}

function ChangeApproval(){
  if (chkSelected() < 1) {
    alert('변경승인처리할 대상을 선택하세요');
    return;
  }

  document.form1.p_process.value = 'ChangeApproval';
  document.form1.p_action.value = 'go';
  document.form1.p_action.value = document.form1.p_action.value;
  document.form1.submit();

}

function chkSelected() {
  var selectedcnt = 0;
  if(document.form1.all['p_checks'] == '[object]') {
    if (document.form1.p_checks.length > 0) {
      for (i=0; i<document.form1.p_checks.length; i++) {
        if (document.form1.p_checks[i].checked == true) {
          selectedcnt++;
        }  
      }
    } else {
      if (document.form1.p_checks.checked == true) {
        selectedcnt++;
      }
    }
  }
  return selectedcnt; 
}
function whenAllSelect() {
  if(document.form1.all['p_checks'] == '[object]') {
    if (document.form1.p_checks.length > 0) {
      for (i=0; i<document.form1.p_checks.length; i++) {
        document.form1.p_checks[i].checked = true;
      }
    } else {
      document.form1.p_checks.checked = true;
    }
  } 
}
function whenAllSelectCancel() {
  if(document.form1.all['p_checks'] == '[object]') {
    if (document.form1.p_checks.length > 0) {
      for (i=0; i<document.form1.p_checks.length; i++) {
        document.form1.p_checks[i].checked = false;
      }
    } else {
      document.form1.p_checks.checked = false;
    }
  } 
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<!----------------- form 시작 ----------------->
<form name="form1" method="post" action="/servlet/controller.propose.ChangeSeqApprovalServlet">
<input type="hidden" name="p_process" value="individualAcceptPage">
<input type="hidden" name="p_action"  value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
       <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/propose/p_title11.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
        
      <br>
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">

            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">

                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td width="27%" colspan="2" align="left" valign="middle">
                      	<!------------------- 조건검색 시작 ------------------------->
				        <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
				      </td>
				      <td width="73%" colspan="2">
        			    <%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도  -->
				        <%= SelectEduBean.getGrseq(box, true, true)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
				        <!--input type= "hidden" name="s_grseq" value="0001"-->
				        <%= SelectSubjBean.getUpperClass(box, true, true, true)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
				        <%= SelectSubjBean.getMiddleClass(box, true, true, true)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
				        <%= SelectSubjBean.getLowerClass(box, true, true, true)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
				      </td>
				    </tr>
				    <tr>
				        <td height="5"></td>
				    </tr>
				    <tr>
				      <td align="left" width="20%">
				        <%@ include file="/incmenu/search_subjnm.jsp"%>
				      </td>
				      <td width="63%">
				        <%= SelectSubjBean.getSubj(box, true, true)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
				        <%= SelectSubjBean.getSubjseq(box, true, true)%><!-- getSubjseq(RequestBox, isChange, isALL)    과정차수  -->
				        <%= SelectCompanyBean.getCompany(box, false, true)%><!-- getCompany(RequestBox, isChange, isALL)    회사  -->
				  		<!-------------------- 조건검색 끝 ---------------------------->

    					<input type = "hidden" name="s_start">
    					<input type = "hidden" name="s_end">
                        <td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
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
      <br>
      <!----------------- 아이디 또는 이름 검색, 모두선택.모두선택취소 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td width="799" height="20">
          </td>
          <td width="131" align="right" ><a href="javascript:whenAllSelect()"><img src="/images/admin/button/select1_butt.gif" border="0"></a></td>
		  <td width="8"></td>
		  <td width="8"></td>
          <td width="32" align="right" ><a href="javascript:whenAllSelectCancel()"><img src="/images/admin/button/select_cancel1_butt.gif" border="0"></a></td>
          <td width=8></td>
          <td width="8"></td>
          <td align="right" height="20" width="45"><a href="javascript:ChangeApproval()"><img src="/images/admin/button/btn_checkok.gif" border="0"></a></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 아이디 또는 이름 검색, 모두선택.모두선택취소 버튼 끝 ----------------->
      <!----------------- 직접입과처리 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="7" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" rowspan="2"><b>과정명</b></td>
          <td class="table_title" width="14%" rowspan="2"><b>희망차수</b></td>
          <td class="table_title" width="29%"><b>신청자정보</b></td>
          <td class="table_title" width="8%" rowspan="2"><b>대상자승인</b></td>
          <td class="table_title" width="8%" rowspan="2"><b>신청자승인</b></td>
          <td class="table_title" width="8%" rowspan="2"><b>관리자승인</b></td>
          <td class="table_title" width="6%" rowspan="2"><b>선택</b></td>
        </tr>
        <tr>
          <td class="table_title" ><b>대상자정보</b></td>
        </tr>
<%
        
        if(list != null){
          boolean ispropPossible = false;
          String v_ispropPossible = "";
          Hashtable PropInfoData = null; 
          
          for(i = 0; i<list.size(); i++){
            DataBox dbox = (DataBox)list.get(i);
            
            v_subj          = dbox.getString("d_subj");
            v_year          = dbox.getString("d_year");
            v_subjseq       = dbox.getString("d_subjseq");
            v_subjseqgr     = dbox.getString("d_subjseqgr");
            v_userid        = dbox.getString("d_userid");
            v_subjnm        = dbox.getString("d_subjnm");
            v_usercono      = dbox.getString("d_usercono");
            v_usercomp      = dbox.getString("d_usercomp");
            v_userjik       = dbox.getString("d_userjik");
            v_prodate       = dbox.getString("d_prodate");
            v_wantseq1      = dbox.getString("d_wantseq1");
            v_wantseq2      = dbox.getString("d_wantseq2");
            v_wantseq3      = dbox.getString("d_wantseq3");
            v_appcono       = dbox.getString("d_appcono");
            v_appid         = dbox.getString("d_appid");
            v_appmail       = dbox.getString("d_appmail");
            v_appsubj       = dbox.getString("d_appsubj");
            v_appyear       = dbox.getString("d_appyear");
            v_appsubjseq    = dbox.getString("d_appsubjseq");
            v_appsubjseqgr  = dbox.getString("d_appsubjseqgr");
            v_name          = dbox.getString("d_name");
            v_orga_ename    = dbox.getString("d_orga_ename");
            v_appname       = dbox.getString("d_appname");
            v_apporga_ename = dbox.getString("d_apporga_ename");
            v_isupapproval  = dbox.getString("d_isupapproval");
            v_isdoapproval  = dbox.getString("d_isdoapproval");
            v_isadmapproval = dbox.getString("d_isadmapproval");
            v_mastercd      = dbox.getString("d_mastercd");
        %><tr>
          <td class="table_01" ><b><%=v_subjnm%></b></td>
          <td class="table_02_1" ><b><%=v_wantseq1%>차,<%=v_wantseq2%>차,<%=v_wantseq3%>차</b></td>
          <td class="table_02_1" ><b>

            <table width="195" border="0" cellspacing="1" cellpadding="0" class=table_out>
              <tr>
                <td class=table_02_1  align="center"><%=v_name%></td>
                <td class=table_02_1  align="center"><%=v_orga_ename%></td>
                <td class=table_02_1  align="center"><%=v_subjseqgr%>차</td>
              </tr>
              <tr>
                <td class=table_02_1 align="center"><%=v_appname%></td>
                <td class=table_02_1 align="center"><%=v_apporga_ename%></td>
                <td class=table_02_1 align="center"><%=v_appsubjseqgr%>차</td>
              </tr>
            </table>
          </td>
          <td class="table_02_1" ><b><%if(v_isupapproval.equals("Y")){%>승인 <%}else{%>-<%}%></b></td>
          <td class="table_02_1" ><b><%if(v_isdoapproval.equals("Y")){%>승인 <%}else{%>-<%}%></b></td>
          <td class="table_02_1" ><b><%if(v_isadmapproval.equals("Y")){%>승인 <%}else{%>-<%}%></b></td>
          <td class="table_02_1" ><%if(!v_isadmapproval.equals("Y")){%><input type="checkbox" name="p_checks" value="<%=v_subj%>,<%=v_subjseq%>,<%=v_year%>,<%=v_userid%>,<%=v_appsubj%>,<%=v_appsubjseq%>,<%=v_appyear%>,<%=v_appid%>,<%=v_isupapproval%>,<%=v_isdoapproval%>,Y"><%}else{%>처리완료<%}%></td>
        </tr>
        <%
          }
        }if( i == 0){
        %>
        <tr>
          <td class="table_02_1" colspan="8" height="28">등록된내용이 없습니다.</td>
        </tr>
        <%
        }
        %>
      </table>
      <!----------------- 직접입과처리  끝 ----------------->
      <br>
      <br>
    </td>
  </tr>
  </form>
  <tr><td>

<table>
    <tr><td>
    <%@ include file = "/learn/library/getJspName.jsp" %>
    </td></tr>
</table>

    </td></tr>    

</table>
</body>
</html>