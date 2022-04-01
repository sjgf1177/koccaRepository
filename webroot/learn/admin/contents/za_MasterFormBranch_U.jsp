<%
/**
 * file name : za_MasterFormBranch_U.jsp
 * date      : 2003/08/18
 * programmer: LeeSuMin
 * function  : 마스터폼 Branch 수정화면
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
    String  c_gadmin    = box.getSession("s_gadmin");
    if(c_gadmin==null || c_gadmin.equals(""))   c_gadmin="A1";

    String  p_process   = box.getString("p_process");
    int     p_cnt_branch= box.getInt("p_cnt_branch");

    MasterFormData  da  =(MasterFormData)request.getAttribute("MasterFormData");
    String  v_ismfbranch    = da.getIsmfbranch();
    String  v_subjnm        = da.getSubjnm();
    String  v_dir           = da.getDir();
    String  v_contenttypetxt= da.getContenttypetxt();
    int     v_cnt_branch    = da.getCnt_branch();

    if(p_cnt_branch>v_cnt_branch)   v_cnt_branch = p_cnt_branch;

    ArrayList       list1 = (ArrayList)request.getAttribute("MfBranchData");
    MfBranchData    data1 = null;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
</head>
<script language="javascript">
function whenBranchCnt(){
    document.f2.p_process.value="updateBranchPage";
    document.f2.submit();
}
function whenSubmit(){
    if(!checkOK())  return;
    document.f2.p_process.value="updateBranch";
    document.f2.submit();
}
function checkOK(){
    var isExist=false;
    for(var i=0;document.f2.length;i++){
        if(document.f2.elements[i].name=="p_sdesc" && document.f2.elements[i].value.length > 0){
            isExist=true;
            break;
        }
    }

    return isExist;
}
function whenDeleteBranch(branch){
    var f_del = false;
    for(var i=0;document.f2.length;i++){
        if(document.f2.elements[i].name=="p_branch" && document.f2.elements[i].value==branch){
            document.f2.elements[(i+1)].value="";
            f_del = true;
        }
        if(f_del && (document.f2.elements[i].name=="p_sdesc") ){
            if( i < (document.f2.length-2) ){
                document.f2.elements[i].value=document.f2.elements[(i+2)].value;
            }else{
                document.f2.elements[i].value="";
            }
        }
    }
}
</script>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">


  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td background="/images/admin/contents/title_bg.gif"><img src="/images/admin/contents/contents_title5.gif" width="206" height="36" border="0"></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>
        <br>
    <%@ include file="/learn/admin/contents/include_MasterFormAdmin.jsp" %>

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td bgcolor="#636563">
              <table width="100%" border="0" cellspacing="1" cellpadding="0">
                <FORM name = "f2" method = "post" action="/servlet/controller.contents.MasterFormServlet">
                    <input type=hidden name=p_process   value="">
                    <input type=hidden name=p_action    value="">
                    <input type=hidden name=p_subj      value="<%=p_subj%>">
                <tr>
                  <td bgcolor="#FFFFFF" align="center" valign="top">
                  <br>
                    <!----------------- 구성할 분기수 시작 ----------------->
                    <table width="99%" class="table_out" cellpadding="0" cellspacing="0">
                      <tr bgcolor="#FFFFFF">
                        <td valign="bottom" height="20" width="195">구성할 분기수 :
                          <input name="p_cnt_branch" type="text" class="input" size="1" value="<%=v_cnt_branch%>">
                        </td>
                        <td><a href="javascript:whenBranchCnt()"><img src="/images/admin/contents/go1_butt.gif" width="33" height="20" border="0"></a></td>
                      </tr>
                      <tr bgcolor="#FFFFFF">
                        <td height="3" colspan="2"></td>
                      </tr>
                    </table>
                    <!----------------- 구성할 분기수 끝 ----------------->
                    <table width="99%" border="0" cellspacing="1" cellpadding="5">
                      <tr>
                        <td colspan="2" class="table_top_line"></td>
                      </tr>
                      <tr>
                        <td width="12%" height="25" class="table_title"><b>분기</b></td>
                        <td class="table_title"><b>분기명</b></td>
                      </tr>
<%          for (int i=0; i<list1.size(); i++) {
                data1  = (MfBranchData)list1.get(i); %>
                      <tr>
                        <td align="center" bgcolor="#EEEEEE" height="25"><%=data1.getBranch()%></td>
                        <td bgcolor="#F7F7F7" align="left">&nbsp;&nbsp;&nbsp;
                            <input type=hidden name="p_branch"  value="<%=data1.getBranch()%>">
                            <input type="text" name="p_sdesc" class="input"value="<%=data1.getSdesc()%>"  size="20" maxlength=20>
                            <a href="javascript:whenDeleteBranch('<%=data1.getBranch()%>')"><img src="/images/admin/course/del1_butt.gif" border=0></a>
                        </td>
                      </tr>
<%          }

            int list_size = 1;
            if (list1 != null)  list_size = list1.size() + 1;

            for (int i=list_size;i<=v_cnt_branch;i++){
            %>
                      <tr>
                        <td align="center" bgcolor="#EEEEEE" height="25"><%=i%></td>
                        <td bgcolor="#F7F7F7" align="left">&nbsp;&nbsp;&nbsp;
                            <input type=hidden name="p_branch"  value="<%=i%>">
                            <input type="text" name="p_sdesc"  class="input" value=""  size="20" maxlength=20>
                        </td>
                      </tr>
            <%
            }
%>
                    </table>
                    <!----------------- 분기정보수정 끝 ----------------->
                    <br>
                    <!----------------- 저장버튼 시작 ----------------->
                    <table width="11%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td align="center">
                            <a href="javascript:whenSubmit()"><img src="/images/admin/contents/save1_butt.gif" width="37" height="18" border="0"></a>
                            <a href="javascript:whenList()"><img src="/images/admin/system/list1_butt.gif"  height="18" border="0"></a>
                        </td>
                      </tr>
                      <tr>
                        <td height="8"></td>
                      </tr>
                    </table>
                    <!----------------- 저장버튼 끝 ----------------->

                  </td>
                </tr>
                </FORM>
              </table>
            </td>
          </tr>
        </table>
        <br>

      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
