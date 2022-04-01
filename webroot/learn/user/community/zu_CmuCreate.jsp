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
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
	
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

	int j = 0;

%>  

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--

//커뮤니티 생성
function uf_insertOK() {

	//if(!CrossEditor.IsDirty()){ // 크로스에디터 안의 콘텐츠 입력 확인 
    //    alert("커뮤니티 소개를 입력하여야합니다 !!"); 
    //    CrossEditor.SetFocusEditor();// 크로스에디터 Focus 이동 
    //    return false; 
    //} 

    if(document.form1.p_type_l.value == "ALL"){
        alert("분류를 선택해 주세요.");
        return;
    }
    
    //if(document.form1.p_type_m.value == "ALL"){
    //    alert("중분류를 선택해 주세요.");
    //    return;
    //}

    document.getElementById("p_content").value = CrossEditor.GetBodyValue();

    if(document.form1.p_dupchk.value =='N'){
       alert('커뮤니티 중복체크를 하여야합니다.');document.form1.p_cmu_nm.focus();return;
    }

    document.form1.action = "/servlet/controller.community.CommunityCreateServlet";
    document.form1.p_process.value = "insertData";
    document.form1.submit();
}

//커뮤니티 중복체크
function uf_dupcheck() {
    if(document.form1.p_cmu_nm.value ==''){
       alert('커뮤니티명을 입력하여야합니다.');document.form1.p_cmu_nm.focus();return;
    }
   open_window('cmuDupCheck', '/servlet/controller.community.CommunityPopUpServlet?p_process=dupchkPage&p_cmu_nm='+document.form1.p_cmu_nm.value, 100, 100, 490, 250, 'no', 'no', 'no', 'no', 'no');
  
}
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"   enctype = "multipart/form-data" method = "post">
	<input type = "hidden" name = "p_process" value="">
	<input type = "hidden" name = "p_dupchk"  value="N">

            <h2><img src="/images/portal/community/h2_tit2.gif" alt="커뮤니티만들기" class="fl_l" /><p class="category">Home > 커뮤니티 > <strong>커뮤니티만들기</strong></p></h2>

            <p><img src="/images/portal/community/tit_07.gif" alt="기본정보변경" /></p>
            
            <table class="study_write mg_t22">
            <!--[if ie]><colgroup><col width="85px" /><col /></colgroup><![endif]-->
            <colgroup><col width="105px" /><col /></colgroup>
            <tr>
                <th class="th"><label for="comname">커뮤니티 명칭</label></th>
                <td colspan="7" class="td">
                    <input type="text" name="p_cmu_nm" id="subject" class="inbox" style="width:85%;" onkeyup="javascript:document.form1.p_dupchk.value ='N';" onkeypress="if(event.keyCode==13){return false;};"/>
                    <!--a href="javascript:uf_dupcheck();"><img src="/images/user/button/btn_addconfirm.gif" width="65" height="21" align="absmiddle"></a-->
                    <a href="javascript:uf_dupcheck();" class="board_btn"><span>중복검사</span></a> 
                </td>
            </tr>
            <tr>
                <th class="th"><label for="member">회원가입 방식</label></th>
                <td colspan="7" class="td"><input type="radio" name="p_in_method_fg" id="community" class="va_up1" value="1" checked /> 신청즉시가입(공개 커뮤니티)
                    <input type="radio" name="p_in_method_fg" id="community" class="va_up1 mg_l15" value="2" />  신청후 승인가입(비공개 커뮤니티)</td>
            </tr>
            <tr>
                <th class="th"><label for="comdiv">커뮤니티 분류</label></th>
                <td colspan="7" class="td">
                    <!-- 대분류 --> 
                    <kocca_select:select                                       
                    name="p_type_l"                                         
                    sqlNum="0003"                                    
                    param="0052" param3 = "1"            
                    isLoad="true"                                           
                    type="1"                                                
                    styleClass="va_up1 mg_r20"                                
                    all="true" />
                    <!-- 중분류 onChange="changep_type_m('0052', this.value, '2')"
                    <kocca_select:select                                       
                    name="p_type_m"                                         
                    sqlNum="0004"                                    
                    param="0052"  param2 = " " param3 = "2"           
                    onChange=""                         
                    isLoad="true"                                           
                    type="1"                                                
                    styleClass="va_up1"                                
                    all="true" /> -->
                </td>
            </tr>
            <tr>
                <td colspan="8" class="td con">
                    <textarea  id="p_content" name="p_content" style="display:none;"></textarea>
					<script type="text/javascript">
					    var CrossEditor = new NamoSE("contents");
					    CrossEditor.editorStart();
					    CrossEditor.ShowToolbar(0, 0);
                        CrossEditor.ShowToolbar(1, 1);
					</script>
                </td>
            </tr>
            </table>

            <div class="base_coverstory">
                <p class="tit"><img src="/images/portal/community/stit_07_1.gif" alt="커버스토리" /></p>
                <p class="photo"><img id="img_photo" src="/images/portal/thumb/thumb_335_200.gif" alt="커뮤니티대표이미지" width="335" height="200" /></p>
                <div class="imgup" style="height:20px;">
                <img src="/images/portal/community/txt_imgup.gif" alt="이미지올리기" style="float:left;padding:3px 5px 0 0"/>
                <jsp:include page="/learn/user/portal/include/singleAttach_include.jsp">
                    <jsp:param name="i_imageFlag"           value="Y" />
                    <jsp:param name="i_realFileName"        value="" />
                    <jsp:param name="i_saveFileName"        value="" />
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
                    <p><input type="radio" name="p_layout_fg" value="1" checked /> Layout 1<br /> <img src="/images/portal/community/layout_1.gif" alt="layout1" /></p>
                    <p><input type="radio" name="p_layout_fg" value="2" /> Layout 2<br /> <img src="/images/portal/community/layout_2.gif" alt="layout2" /></p>
                </div>
            </div>
            
            <div class="base_skin">
                <p class="tit"><img src="/images/portal/community/stit_07_3.gif" alt="화면 스킨을 선택해 주세요" /></p>
                <div class="selbox">
                    <p><input type="radio" name="p_html_skin_fg" value="1" checked /> <img src="/images/portal/community/skin_1.gif" alt="skin_1" /></p>
                    <p><input type="radio" name="p_html_skin_fg" value="2"/> <img src="/images/portal/community/skin_2.gif" alt="skin_2" /></p>
                    <p><input type="radio" name="p_html_skin_fg" value="3"/> <img src="/images/portal/community/skin_3.gif" alt="skin_3" /></p>
                    <p><input type="radio" name="p_html_skin_fg" value="4"/> <img src="/images/portal/community/skin_4.gif" alt="skin_4" /></p>
                    <p><input type="radio" name="p_html_skin_fg" value="5"/> <img src="/images/portal/community/skin_5.gif" alt="skin_5" /></p>
                </div>
            </div>

            <p class="ag_c"><a href="javascript:uf_insertOK();" class="btn_gr"><span>만들기</span></a> <a href="javascript:alert('메인으로');" class="btn_gr"><span>취소</span></a></p>
</form>            
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->