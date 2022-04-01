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

    String  v_process    = box.getStringDefault("p_process","movememberactivityPage");
    String  v_cmuno      = box.getString("p_cmuno");//마스터 관련페이지는 무조건 들어가야함.
    

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);


    String  content      = box.getString("content");
    String  width = "630";
    String  height = "150";
    String  v_realfile ="";
    String  v_savefile="";
    ArrayList list       = (ArrayList)request.getAttribute("list");
    if(list.size()>0){
        DataBox dbox = (DataBox)list.get(0);
        content=dbox.getString("d_contents");
        v_realfile=dbox.getString("d_realfile");
        v_savefile=dbox.getString("d_savefile");

    }

%>  
<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//저장
function uf_insertOK(pageNum) {
    if(!CrossEditor.IsDirty()){ // 크로스에디터 안의 콘텐츠 입력 확인 
        alert(" 에디터에 내용을 입력해 주세요 !!"); 
        CrossEditor.SetFocusEditor();// 크로스에디터 Focus 이동 
        return; 
    } 

   document.getElementById("p_content").value = CrossEditor.GetBodyValue();

    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "insertprData";
    document.form1.submit();
}

//파일삭제
function uf_deleteFileOK(pageNum) {
    if(document.all.use_editor[0].checked) {        
        form1.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    }else {
        form1.content.value = document.all.txtDetail.value;
    }

    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "deleteprfileData";
    document.form1.submit();
}


//리스트
function uf_listOK() {
    document.form1.action          = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "selectmsmainPage";
    document.form1.submit();
}

//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"   enctype = "multipart/form-data"   method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_cmuno"      value = "<%=v_cmuno%>">
<input type = "hidden" name = "p_pageno"      value = "">

            <h1><img src="/images/portal/community/tit_06.gif" alt="홍보요청" /></h1>

            <table class="study_write">
             <!--[if ie]><colgroup><col width="85px" /><col width="520" /></colgroup><![endif]-->
            <colgroup><col width="105px" /><col width="420px" /></colgroup>
            
            <tr>
                <th class="th va_t"><label for="comname">이미지</label></th>
                <%    if(v_savefile.length()>0){%>
                <td class="td in"><img id="img_photo" src="/servlet/controller.library.DownloadServlet?p_savefile=<%=v_savefile%>" width="110" height="110" /></td>
                <%    } else {%>
                <td class="td in"><img id="img_photo" src="/images/portal/thumb/thumb_110.gif" width="110" height="110" /></td>
                <%    } %>
            </tr>
            <tr>
                <th class="th"><label for="comname">이미지 추가</label></th>
                <td class="td">
                    <jsp:include page="/learn/user/portal/include/singleAttach_include.jsp">
	                    <jsp:param name="i_imageFlag"           value="Y" />
	                    <jsp:param name="i_realFileName"        value="" />
	                    <jsp:param name="i_saveFileName"        value="<%=v_savefile%>" />
	                    <jsp:param name="i_targetImg"           value="img_photo" />
	                    <jsp:param name="i_inputFileParamName"  value="p_savefile" />
	                    <jsp:param name="i_formName"            value="form1" />
	                    <jsp:param name="i_ServletName"         value="CommunityMasterServlet" />
	                    <jsp:param name="i_searchOnImagePath"   value="/images/portal/btn/file_search_on.gif" />
	                    <jsp:param name="i_searchOffImagePath"  value="/images/portal/btn/file_search.gif" />
	                    <jsp:param name="i_style"               value="" />
	                </jsp:include>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="td con">
                    <textarea id="p_content" name="p_content" style="display:none"><%=StringManager.replace(content, "&", "&amp") %></textarea>
                    <script type="text/javascript">
                        var CrossEditor = new NamoSE("contents");
                        var contentValue = document.getElementById("p_content").value; // Hidden 값 참조
                        CrossEditor.editorStart();
                        CrossEditor.SetUISize("650","400");
                        CrossEditor.SetBodyValue(contentValue); // 콘텐츠 내용 에디터 삽입
                    </script>
                </td>
            </tr>
            </table>

            <p class="board_btn"><a href="javascript:uf_insertOK();" class="btn_gr"><span>등록</span></a></p>

</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료 -->