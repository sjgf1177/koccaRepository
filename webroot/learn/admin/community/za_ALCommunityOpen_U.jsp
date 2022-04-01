<%
//**********************************************************
//  1. 제      목: COMMUNITY update PAGE
//  2. 프로그램명: za_ALCommunityOpen_U.jsp
//  3. 개      요: Action Learning 커뮤니티 수정 page
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: mscho 2003. 7. 14
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");    
    String  v_user_id   = box.getSession("userid");    
    String  v_user_name = box.getSession("username"); 
    String  v_process   = box.getString("p_process");  
    String  v_commId    = box.getString("p_commId"); 
    String  v_subj      = box.getString("p_subj");  
    String  v_year      = box.getString("p_year");  
    String  v_subjseq   = box.getString("p_subjseq");      
    String  v_sdesc     =  "";
    String  v_categoryId=   "01";
    String  v_master    =   "";
    String  v_intros    =   "";
    String  v_topics    =   "";
    String  v_isPublic  =   "";
    String  v_isPermit  =   "";
    String  p_process   =   "ActionLearningUpdate";    
    //DEFINED class&variable END                     
    
    DataBox dbox  = (DataBox)request.getAttribute("actionLearningUpdatePage");

    v_sdesc         = dbox.getString("d_sdesc");
    v_categoryId    = dbox.getString("d_categoryid");
    v_master        = dbox.getString("d_master");
    v_intros        = dbox.getString("d_intros");
    v_topics        = dbox.getString("d_topics");
    v_isPublic      = dbox.getString("d_ispublic");
    v_isPermit      = dbox.getString("d_ispermit");      
%>
<html>
<head>
<title>▒▒▒▒▒ 쌍용자동차 사이버 연수원 ▒▒▒▒▒</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
    <script language = "javascript" src = "/script/cresys_lib.js"></script>
	<script language = "javascript">
	<!--
        function open_window(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable)
        {
            toolbar_str = toolbar ? 'yes' : 'no';
            menubar_str = menubar ? 'yes' : 'no';
            statusbar_str = statusbar ? 'yes' : 'no';
            scrollbar_str = scrollbar ? 'yes' : 'no';
            resizable_str = resizable ? 'yes' : 'no';
            window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
        }		
	   function cancel() {
                document.form1.action = "/servlet/controller.community.CommunityAdminServlet";
                document.form1.p_process.value = "ActionLearningList";      
                document.form1.submit();	
            }                        
            function insert() { 
                if (blankCheck(document.form1.p_masterID.value)) {
                    alert("시샵을 입력하세요!");
                    document.form1.p_masterID.focus();
                    return;
                }  
                if (blankCheck(document.form1.p_sdesc.value)) {
                    alert("커뮤니티 이름을 입력하세요!");
                    document.form1.p_sdesc.focus();
                    return;
                }   
                if (blankCheck(document.form1.p_intros.value)) {
                    alert("커뮤니티 소개를 입력하세요!");
                    document.form1.p_intros.focus();
                    return;
                }           
                if (blankCheck(document.form1.p_topics.value)) {
                    alert("커뮤니티 내용을 입력하세요!");
                    document.form1.p_topics.focus();
                    return;
                }	                                                
                if (realsize(document.form1.p_topics.value) > 1000) {
                    alert("커뮤니티 내용은 한글기준 1000자를 초과하지 못합니다.");
                    document.form1.p_topics.focus();
                    return;
                }
                document.form1.action = "/servlet/controller.community.CommunityAdminServlet";   
                document.form1.p_process.value = "ActionLearningUpdate";
                document.form1.submit();
            }
    function master_search() {
        window.self.name = "winSelectView";
        open_window("openWinView","","100","100","540","350");       
        document.form1.target = "openWinView";
        document.form1.action = "/servlet/controller.community.CommunityAdminServlet";
        document.form1.p_process.value = "SearchMasterOpenPage";
        document.form1.submit();
        document.form1.target = window.self.name;
    }            
     -->       
	</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" rightmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_user_id"   value = "<%= v_user_id %>">
    <input type = "hidden" name = "p_user_name" value = "<%= v_user_name %>">
    <input type = "hidden" name = "p_process"   value = "<%= v_process %>">  
    <input type = "hidden" name = "p_subj"      value = "<%= v_subj %>">  
    <input type = "hidden" name = "p_year"      value = "<%= v_year %>">  
    <input type = "hidden" name = "p_subjseq"   value = "<%= v_subjseq %>">
    <input type = "hidden" name = "p_commId"    value = "<%= v_commId %>">
    <input type = "hidden" name = "p_masterName">  
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="../../../images/admin/community/tit_comm_05.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <br>
      <br>
        <!-----------------동호회 생성 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="20%" class="table_title"><b>신청자</b></td>
            <td width="80%" class="table_02_2">관리자</td>
          </tr>
          <tr> 
            <td class="table_title"><strong>시샵선정</strong></td>
            <td class="table_02_2">
              <input name="p_masterID" type="text" class="input" value="<%=v_master%>" readonly>
              <a href="javascript:master_search()"><img src="/images/admin/community/membersearch_butt.gif" height="18" border="0"></a>
              </td>
          </tr>
          <tr> 
            <td class="table_title"><strong>카테고리</strong></td>
            <td class="table_02_2"> 
            <%=CodeConfigBean.getCodeSelect("communityCategory","p_categoryId",v_categoryId)%>
            </td>
          </tr>
          <tr> 
            <td class="table_title"><strong>학습 동호회 제목</strong></td>
            <td class="table_02_2">
            <input class="ms" size="50" maxlength="20" name="p_sdesc" value="<%=v_sdesc%>">
            </td>
          </tr>
          <tr> 
            <td class="table_title"><strong>학습 동호회 소개</strong></td>
            <td class="table_02_2">
            <input class="ms" size="50" maxlength="20" name="p_intros" value="<%=v_intros%>">
            </td>
          </tr>
          <tr> 
            <td class="table_title">
            <table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td align="center"><strong>학습 동호회 내용</strong></td>
                </tr>
              </table>
            </td>
            <td class="table_02_2">
            <textarea name="p_topics" cols="60" rows="15" class="ms"><%=v_topics%></textarea>
            </td>
          </tr>
          <tr> 
            <td height="25" colspan="2" align="center" bgcolor="#e9e9e9">
            <table width="97%" height="25" border="0" cellpadding="0" cellspacing="0">
                <tr> 
                  <td valign="absmiddle"><strong>공개여부 :</strong> 
                    <input type="radio" name="p_isPublic" value='Y' <%if(v_isPublic.equals("Y")){%><%="checked"%><%}%>>
                    공개 
                    <input type="radio" name="p_isPublic" value='N' <%if(v_isPublic.equals("N")){%><%="checked"%><%}%>>
                    비공개</td>
                </tr>
              </table></td>
          </tr>
        </table>
        <!----------------- 동호회 생성 끝 ----------------->
        <br>
        <br>
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="right" class="ms"><a href="javascript:insert()"><img src="/images/admin/community/confirm_butt.gif" width="37" height="18" border="0"></a></td>
            <td width="1%" align="center" class="ms">&nbsp;</td>
            <td align="left" class="ms"><a href="javascript:cancel()"><img src="/images/admin/community/cancel2_butt.gif" width="37" height="18" border="0"></a></td>
          </tr>
        </table> </td>
  </tr>
</table>


<%@ include file = "/jsp/library/getJspName.jsp" %>
</form>
</body>
</html>
