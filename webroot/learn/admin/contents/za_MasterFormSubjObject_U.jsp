<%
/**
 * file name : za_MasterFormSubjObject_U.jsp
 * date      : 2003/10/22
 * programmer: LeeSuMin
 * function  : 마스터폼 Lesson에 Object지정 화면
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  p_subj      = box.getString("p_subj");
    String  p_process   = box.getString("p_process");
    String  p_module    = box.getString("p_module");
    String  p_lesson    = box.getString("p_lesson");

    MasterFormData  da  =(MasterFormData)request.getAttribute("MasterFormData");

%>
    <html>
    <head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    <link rel="stylesheet" href="/css/admin_style.css" type="text/css">
    <script language="JavaScript" src="/script/default.js"></script>
    </head>
    <script language="javascript">
        function whenSubmit(){
            var ostr="";
            for(var i=0;i<document.f1.p_obj.length;i++){
                ostr +=document.f1.p_obj.options[i].value;
            }
            document.ff.p_objstr.value=ostr;
            document.ff.submit();
        }
        function  delObj(){
            if(document.f1.p_obj.selectedIndex==null){
                alert("삭제할 Object를 선택하십시오.");
                return;
            }
            var i = document.f1.p_obj.selectedIndex;

            for(var j=0; j<document.f1.p_obj.length; j++){
                if (i==j)
                    document.f1.p_obj.options[j] = null;
            }

        }

        function  moveTo(valn){
            var seled = document.f1.p_obj.selectedIndex;
            var goidx = seled + valn;

            if(seled==0 && valn == -1){
                alert("cannot go up..");
                return;
            }else if(seled==document.f1.p_obj.length-1 && valn == 1){
                alert("cannot go down..");
                return;
            }else{
                var sel_text  = document.f1.p_obj.options[seled].text;
                var sel_value = document.f1.p_obj.options[seled].value;
                document.f1.p_obj.options[seled].value = document.f1.p_obj.options[goidx].value;
                document.f1.p_obj.options[seled].text  = document.f1.p_obj.options[goidx].text;
                document.f1.p_obj.options[goidx].value = sel_value;
                document.f1.p_obj.options[goidx].text  = sel_text;
                document.f1.p_obj.options[goidx].selected=true;
            }

        }

        function  receive_obj(oid,sdesc){

            for(var i=0;i<document.f1.p_obj.length;i++){
                if (document.f1.p_obj.options[i].value == oid){
                    alert("이미 존재합니다");
                    return;
                }
            }

            document.f1.p_obj.options[i] = new Option("["+oid+"] "+sdesc,oid,true,true);



        }

        function whenList(){
            var url="/servlet/controller.contents.MasterFormServlet?p_process=updateLessonPage";
            url+="&p_subj=<%=p_subj%>&p_lesson=<%=p_lesson%>";
            top.opener.location=url;
            top.close();
        }
    </script>
    <body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
    <table width="95%" border="0" cellspacing="0" cellpadding="0">
        <FORM name=ff method=post action="/servlet/controller.contents.MasterFormServlet">
            <input type=hidden name=p_process value="subjObjectSave">
            <input type=hidden name=p_subj    value="<%=p_subj%>">
            <input type=hidden name=p_module  value="<%=p_module%>">
            <input type=hidden name=p_lesson  value="<%=p_lesson%>">
            <input type=hidden name=p_objstr  value="">
        </FORM>
      <tr>
        <td align="center" valign="top">
          <!----------------- title 시작 ----------------->
          <table width="97%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td background="/images/admin/community/title_bg.gif"><img src="/images/admin/contents/contents_title12.gif" width="131" height="36" border="0"></td>
            </tr>
          </table>
          <!----------------- title 끝 ----------------->
          <br>
            <!----------------- 설문첨부 시작 ----------------->
            <table width="97%" cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <Td align=right>
                        <!----------------- 테이블 시작 ----------------->
                        <table width="400" cellspacing="0" cellpadding="0" border="0">
                            <tr>
                                <td>
                                    <table cellspacing="1" cellpadding="5" class="table_out">
                                        <tr>
                                            <td colspan="11" class="table_top_line"></td>
                                        </tr>
                                        <tr>
                                            <td class="table_title" width="30%"><b>과정</b></td>
                                            <td class="table_02_2"><%=p_subj%>-<%=da.getSubjnm()%></td>
                                        </tr>
                                        <tr>
                                            <td class="table_title" width="30%">Lesson</td>
                                            <td class="table_02_2"><%=p_lesson%></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <!----------------- 테이블 끝 ----------------->
                    </td>
                    <FORM name=f1>
                    <td width="10%" valign=top>
                        <select size ="7" style="border: 1px solid rgb(99,99,99)"  align='center' name='p_obj' multiple>
<%
    ArrayList       list1 = (ArrayList)request.getAttribute("MfSubjObject");
    MfLessonDataSub  d = null;
out.println("test1");
    if (list1 != null){
        for(int i=0; i<list1.size(); i++){
            d = (MfLessonDataSub)list1.get(i);
            %><option value="<%=d.getOid()%>">[<%=d.getOid()%>] <%=d.getSdesc()%></option><%
        }
    }

%>
                        </select>
                    </td>
                    </FORM>
                    <Td width="25%" valign=middle>
                    <a href="javascript:moveTo(-1)"><img src="/images/admin/common/up.gif" width="15" height="15" border="0" align=absmiddle></a> 위로<Br><img src=../images/admin/blank.gif height="6"><Br>
                    <a href="javascript:moveTo(1)"><img src="/images/admin/common/down.gif" width="15" height="15" border="0" align=absmiddle></a> 아래로<Br><img src=../images/admin/blank.gif height="6"><Br>
                    <a href="javascript:delObj()"><img src="/images/admin/common/minus.gif" width="15" height="15" border="0" align=absmiddle></a> 제거</td>
                </tr>
            </table>
            <!----------------- 설문첨부 끝 ----------------->
          <br>
          <!----------------- 저장,취소 버튼 시작 ----------------->
          <table cellpadding="0" cellspacing="0" class="table1">
            <tr>
              <td align="center" height="20">
              <a href='javascript:whenSubmit()'><img src="/images/admin/button/btn_save.gif"  border="0"></a>
              <a href='javascript:whenList()'><img src="/images/admin/button/btn_cancel.gif"  border="0"></a></td>
            </tr>
            <tr>
              <td height="3"></td>
            </tr>
          </table>
          <!----------------- 저장,취소 버튼 끝 ----------------->
          <br>
        </td>
      </tr>
    </table>
    </body>
    </html>
<%@ include file = "/learn/library/getJspName.jsp" %>
