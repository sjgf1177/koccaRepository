<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box      = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_cmuno     = box.getString("p_cmuno");
    String  s_userid    = box.getSession("userid");
    String  s_username  = box.getSession("name");
%>  
  
<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script type="text/javascript" src="/script/portal/FileButton.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
//첨부관련 스크립트
var myFileButton = new FileButton("imageswap", "imagesrc");
window.onload = function () { 
    myFileButton.run(); //문서로딩 후 한꺼번에 바꿈 
    
} 
//등록
function uf_updateOK() {
	if(!CrossEditor.IsDirty()){ // 크로스에디터 안의 콘텐츠 입력 확인 
        alert("커뮤니티 소개를 입력하여야합니다 !!"); 
        CrossEditor.SetFocusEditor();// 크로스에디터 Focus 이동 
        return; 
    } 

	if(document.form1.p_type_l.value == "ALL"){
        alert("분류를 선택해 주세요.");
        return;
    }
    
    //if(document.form1.p_type_m.value == "ALL"){
    //    alert("중분류를 선택해 주세요.");
    //    return;
    //}

    document.getElementById("p_content").value = CrossEditor.GetBodyValue();

    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "updatemsroomData";
    document.form1.submit();
}

//중복체크페이지로이동
function uf_dupcheck() {
    if(document.form1.p_cmu_nm.value ==''){
       alert('커뮤니티명을 입력하여야합니다.');document.form1.p_cmu_nm.focus();return;
    }
   open_window('cmuDupCheck', '/servlet/controller.community.CommunityPopUpServlet?p_process=dupchkPage&p_cmu_nm='+document.form1.p_cmu_nm.value, 100, 100, 300, 220, 'no', 'no', 'no', 'no', 'no');
  
}

//마스타홈으로이동
function uf_mshomeOK() {
    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "selectmsmainPage";
    document.form1.submit();
}
//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"   enctype = "multipart/form-data" method = "post">
<input type = "hidden" name = "p_process" value="">
<input type = "hidden" name = "p_dupchk" value="<%=box.getStringDefault("p_dupchk","N")%>">
<input type = "hidden" name = "p_cmuno" value="<%=communityBox.getString("d_cmuno")%>">
<input type = "hidden" name = "p_cmuno"      value = "<%=v_cmuno%>">
<input type = "hidden" name = "p_selact" value="">

            <h1><img src="/images/portal/community/tit_07.gif" alt="기본정보변경" /></h1>
<% String v_type_m = communityBox.getString("d_type_m"); %>
<% String v_type_l = communityBox.getString("d_type_l"); 
String v_img_saveFileName = communityBox.getString("d_img_path");%>
            <table class="study_write">
            <!--[if ie]><colgroup><col width="85px" /><col /></colgroup><![endif]-->
            <colgroup><col width="105px" /><col /></colgroup>
            <tr>
                <th class="th"><label for="comname">커뮤니티 명칭</label></th>
                <td colspan="7" class="td"><input name="p_cmu_nm" type="text" id="subject" class="inbox" style="width:97%;" readonly value="<%=communityBox.getString("d_cmu_nm")%>" onkeyup="javascript:document.form1.p_dupchk.value ='N';"/></td>
            </tr>
            <tr>
                <th class="th"><label for="member">회원가입 방식</label></th>
                <td colspan="7" class="td"><input type="radio" name="p_in_method_fg" id="community" class="va_up1" value="1" <%if("1".equals(communityBox.getString("d_in_method_fg")))out.print(" checked");%>/> 신청즉시가입(공개 커뮤니티) <input type="radio" name="p_in_method_fg" id="community" class="va_up1 mg_l15" value="2" <%if("2".equals(communityBox.getString("d_in_method_fg")))out.print(" checked");%>/>  신청후 승인가입(비공개 커뮤니티)</td>
            </tr>
            <tr>
                <th class="th"><label for="comdiv">커뮤니티 분류</label></th>
                <td colspan="7" class="td">
                    <!-- 대분류 -->
                    <kocca_select:select                                       
                    name="p_type_l"                                         
                    sqlNum="0003"                                    
                    param="0052" param3 = "1"            
                    selectedValue="<%= v_type_l %>"  
                    isLoad="true"         
                    type="1"                                                
                    styleClass="va_up1 mg_r20"                                
                    all="true" /> 
                    <!-- 중분류   onChange="changep_type_m('0052', this.value, '2')"
                    <kocca_select:select                                       
                    name="p_type_m"                                         
                    sqlNum="0004"                                    
                    param="0052"  param2 = "<%=v_type_l %>" param3 = "2"           
                    onChange=""
                    selectedValue="<%=v_type_m %>"                    
                    isLoad="true"                                           
                    type="1"                                                
                    styleClass="va_up1"                                
                    all="true" /> -->
                </td>
            </tr>
            <tr>
                <td colspan="8" class="td con">
                    <textarea id="p_content" name="p_content" style="display:none;"><%=StringManager.replace(communityBox.getString("d_intro"), "&", "&amp") %></textarea>
                    <script type="text/javascript">
	                    var contentValue = document.getElementById("p_content").value; // Hidden 값 참조
	                    var CrossEditor = new NamoSE('contents');
	                    CrossEditor.editorStart();
	                    CrossEditor.SetBodyValue(contentValue); // 콘텐츠 내용 에디터 삽입
	                    CrossEditor.ShowToolbar(0, 0);
	                    CrossEditor.ShowToolbar(1, 1);
	                    CrossEditor.SetUISize("650","400");
                    </script>
                </td>
            </tr>
            </table>
            
            <div class="base_coverstory">
                <p class="tit"><img src="/images/portal/community/stit_07_1.gif" alt="커버스토리" /></p>
                <%  String imgSrc = "";   
                    if(communityBox.getString("d_img_path").equals("")) imgSrc = "/images/portal/thumb/thumb_335_200.gif";
                    else imgSrc = "/servlet/controller.library.DownloadServlet?p_savefile="+communityBox.getString("d_img_path");
                %>
                <p class="photo"><img id="img_photo" src="<%=imgSrc %>" alt="커뮤니티대표이미지" width="335px" height="200px"/></p>
                <div class="imgup">
                <img src="/images/portal/community/txt_imgup.gif" alt="이미지올리기" style="float: left; padding: 0 5px 0 0;"/> 
                <jsp:include page="/learn/user/portal/include/singleAttach_include.jsp">
                    <jsp:param name="i_imageFlag"           value="Y" />
                    <jsp:param name="i_realFileName"        value="" />
                    <jsp:param name="i_saveFileName"        value="<%=v_img_saveFileName%>" />
                    <jsp:param name="i_targetImg"           value="img_photo" />
                    <jsp:param name="i_inputFileParamName"  value="p_img_path" />
                    <jsp:param name="i_formName"            value="form1" />
                    <jsp:param name="i_ServletName"         value="CommunityCreateServlet" />
                    <jsp:param name="i_searchOnImagePath"   value="/images/portal/btn/file_search_on.gif" />
                    <jsp:param name="i_searchOffImagePath"  value="/images/portal/btn/file_search.gif" />
                    <jsp:param name="i_style"               value="" />
                </jsp:include>
                </div>
            </div>
            
            <div class="base_layout">
                <p class="tit"><img src="/images/portal/community/stit_07_2.gif" alt="레이아웃 지정" /></p>
                <div class="selbox">
                    <p><input type="radio" name="p_layout_fg" value="1" <%=communityBox.getString("d_layout_fg").equals("1") ? "checked" : "" %> /> Layout 1<br /> <img src="/images/portal/community/layout_1.gif" alt="layout1" /></p>
                    <p><input type="radio" name="p_layout_fg" value="2" <%=communityBox.getString("d_layout_fg").equals("2") ? "checked" : "" %> /> Layout 2<br /> <img src="/images/portal/community/layout_2.gif" alt="layout2" /></p>
                </div>
            </div>
            
            <div class="base_skin">
                <p class="tit"><img src="/images/portal/community/stit_07_3.gif" alt="화면 스킨을 선택해 주세요" /></p>
                <div class="selbox">
                    <p><input type="radio" name="p_html_skin_fg" value="1" <%=communityBox.getString("d_html_skin_fg").equals("1") ? "checked" : "" %> /> <img src="/images/portal/community/skin_1.gif" alt="skin_1" /></p>
                    <p><input type="radio" name="p_html_skin_fg" value="2" <%=communityBox.getString("d_html_skin_fg").equals("2") ? "checked" : "" %> /> <img src="/images/portal/community/skin_2.gif" alt="skin_2" /></p>
                    <p><input type="radio" name="p_html_skin_fg" value="3" <%=communityBox.getString("d_html_skin_fg").equals("3") ? "checked" : "" %> /> <img src="/images/portal/community/skin_3.gif" alt="skin_3" /></p>
                    <p><input type="radio" name="p_html_skin_fg" value="4" <%=communityBox.getString("d_html_skin_fg").equals("4") ? "checked" : "" %> /> <img src="/images/portal/community/skin_4.gif" alt="skin_4" /></p>
                    <p><input type="radio" name="p_html_skin_fg" value="5" <%=communityBox.getString("d_html_skin_fg").equals("5") ? "checked" : "" %> /> <img src="/images/portal/community/skin_5.gif" alt="skin_5" /></p>
                </div>
            </div>
            
            <p class="ag_c"><a href="javascript:uf_updateOK();" class="btn_gr"><span>변경</span></a></p>
            
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료 -->
