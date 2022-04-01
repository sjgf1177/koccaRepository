<%
//**********************************************************
//  1. 제      목: 마스터폼 리스트-베타테스트시스템
//  2. 프로그램명 : za_BetaMasterForm_L.jsp
//  3. 개      요: 마스터폼 리스트 조회화면-베타테스트시스템
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 27
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.beta.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    String  ss_classtype   = box.getStringDefault("s_classtype",  "upper");
    String  ss_upperclass  = box.getStringDefault("s_upperclass", "U001");
    String  ss_middleclass = box.getStringDefault("s_middleclass","M001");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass", "0000");

    String  ss_contenttype = box.getStringDefault("s_contenttype","N");
    String  p_action       = box.getString("p_action");
    String  c_gadmin       = box.getSession("s_gadmin");
    String v_owner = "";
    if(c_gadmin==null || c_gadmin.equals(""))   c_gadmin="A1";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="javascript">
        function whenSelection(action){
            if (action=="go"){
                top.ftop.setPam();
            }
            document.form1.p_action.value = action;
            document.form1.p_process.value="listPage";
            document.form1.submit();
        }

        function changeUpperclass() {
            document.form1.p_process.value="listPage";
            document.form1.p_action.value="";
            document.form1.submit();
        }
        function changeMiddleclass() {
            changeUpperclass();
        }
        function changeLowerclass() {
            changeUpperclass();
        }
        function whenSubmit(){
            document.form1.p_process.value="listPage";
            document.form1.p_action.value="go";
            document.form1.submit();
        }
        function whenChange(subj,contenttype){
            document.form1.p_subj.value=subj;
//            document.form1.s_contenttype.value=contenttype;
            document.form1.p_process.value="updatePage";
            document.form1.submit();
        }
        function whenOrders(ord){
            document.form1.p_process.value="listPage";
            document.form1.p_order.value=ord;
            whenSelection('go');
        }
        function whenViewEdu(url){
            window.open(url,'EduStart','width=800,height=600');
        }
      
        function whenFreeMail() {
      window.self.name = "ProposeMemberList";
      open_window("openFreeMail","","100","100","640","550");  
      document.form1.target = "openFreeMail";
      document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
      document.form1.p_process.value = 'SendFreeMail';
      document.form1.submit();
    }
    </script>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">


      <!----------------- title 시작 ----------------->
     <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/beta/tit_masterform.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

        <br>

        <!----------------- form 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="1" >
          <tr>
            <td bgcolor="#C6C6C6" align="center">
              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="form_table_bg">
                <tr>
                  <td height="7"></td>
                </tr>
                <tr>
                <form name="form1" method="post" action="/servlet/controller.beta.BetaMasterFormServlet">
                    <input type="hidden" name="p_process" value="listPage">
                    <input type="hidden" name="p_action"  value="">
                    <input type="hidden" name="p_subj"    value="">
                    <input type="hidden" name="p_order"   value="subjnm">

                  <td align="center">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                      <tr>
                        <td align="center">
                          	<%= SelectSubjBean.getUpperClass(box, true, true, false)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
			  				<%= SelectSubjBean.getMiddleClass(box, true, true, false)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
			  				<%= SelectSubjBean.getLowerClass(box, true, true, false)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
                          ContentType
                          <select name = "s_contenttype" onChange="javascript:whenSelection('change')">
						    <option value="ALL" <% if (ss_contenttype.equals("ALL")){ %>selected<%}%>>ALL</option>
							<option value="N"   <% if (ss_contenttype.equals("N")){ %>selected<%}%>>NORMAL</option>
							<option value="L"   <% if (ss_contenttype.equals("L")){ %>selected<%}%>>LINK</option>
<%          			if (GetCodenm.get_config("useOBC").equals("Y") || GetCodenm.get_config("useSCORM").equals("Y")) {%>
							<option value="O"   <% if (ss_contenttype.equals("O")){ %>selected<%}%>>OBC</option>
							<option value="S"   <% if (ss_contenttype.equals("S")){ %>selected<%}%>>SCORM</option>
<%          			}%>
                          </select>
                          <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                </form>
                <tr>
                  <td height="7"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <!----------------- form 끝 ----------------->
        <br>
        <!--table width="97%">
            <tr>
                <td align="right">
                <a href="javascript:whenFreeMail()"><img src="/images/admin/button/btn_mail.gif" border="0"></a>
                </td>
            </tr>
        </table-->
        <!----------------- 마스터폼 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="9" class="table_top_line"></td>
          </tr>
          <tr>
            <td height="25" class="table_title" width="5%"><b>NO</b></td>
            <td class="table_title" width="10%"><a href="javascript:whenOrders('subj')" class="b"><b>과정코드</b></a></td>
            <td class="table_title" width="13%"><a href="javascript:whenOrders('dir')" class="b"><b>콘텐츠 위치</b></a></td>
            <td class="table_title"><a href="javascript:whenOrders('subjnm')" class="b"><b>과정명</b></a></td>
            <td class="table_title"><b>콘텐츠타입</b></td>
            <td class="table_title"><b>차시</b></td>
            <td class="table_title" width="8%"><a href="javascript:whenOrders('center')" class="b"><b>중앙정렬</b></a></td>
            <td class="table_title" width="18%"><b>기능</b></td>
          </tr>
<%  if (p_action.equals("go")) {
        ArrayList  list = (ArrayList)request.getAttribute("MasterFormList");
        BetaMasterFormListData data = null;
        String      v_subj="";
		String		v_contenttype="";
        for (int i=0; i<list.size(); i++) {
            data  = (BetaMasterFormListData)list.get(i);
            v_subj = data.getSubj();
            
%>
          <tr>
            <td align="center" bgcolor="#EEEEEE" height="25"><%=list.size()-i%></td>
            <td class="table_02_1"><%=v_subj%></td>
            <td class="table_02_1"><%=data.getDir()%></td>
            <td class="table_02_2" bgcolor="#F7F7F7"><%=data.getSubjnm()%></td>
            <td class="table_02_1"><%=data.getContenttypenm()%></td>
            <td class="table_02_1"><%=data.getCnt_lesson()%></td>
            <td class="table_02_1"><%=data.getIscentered()%></td>
            <td class="table_03_1">
              <table width="95%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td align="center">
                    <a href="javascript:whenChange('<%=v_subj%>','<%=v_contenttype%>')"><img src="/images/admin/button/b_modify.gif"  border="0"></a>
                  </td>
                  
                  <!--<td align="center"><a href="javascript:whenViewPreview('<%=v_subj%>')"><img src="/images/admin/contents/view1_butt.gif" width="48" height="18" border="0"></a></td>-->
<!-- OBC,SCORM일 경우 변경옵션추가 (추후)-->
                </tr>
              </table>
            </td>
          </tr>
<%      }
    }
%>

        </table>
        <!----------------- 마스터폼 관리 끝 ----------------->
        <br>

      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
