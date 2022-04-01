<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "java.util.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

	String  v_process   = box.getString("p_process");
	String s_userid     = box.getSession("userid");
    String s_usernm     = box.getSession("name");
    String s_email      = box.getSession("email");
    String s_resno      = box.getSession("resno");
    
    s_resno = s_resno.substring(0, 6) + " - ●●●●●●●";
    
    //전문분야 리스트
    List selectListGubun = (List)request.getAttribute("selectListGubun");
    
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script type="text/javascript" src="/script/portal/FileButton.js"></script>
<SCRIPT>
// 우편번호검색
function searchPost(gubun) {
	window.self.name = "PersonalSelect";
	open_window("openPost","","100","100","480","400","","","","yes","");
	document.mform1.target = "openPost";
	document.mform1.action='/servlet/controller.library.PostSearchServlet';
	document.mform1.p_postgubun.value = gubun;
	document.mform1.p_process.value = 'SearchPostOpenPage';
	document.mform1.submit();
	document.mform1.target = window.self.name;
}

function delAddr(no){
	if( no == 1 ){
		document.mform1.p_home_addr2.value = ""
	}else if( no == 2){
		document.mform1.p_comp_addr2.value = ""
	}
}

function receivePost(post1, post2, addr, postgubun){
	if( postgubun == "1" ){
		document.mform1.p_post1.value = post1;
		document.mform1.p_post2.value = post2;
		document.mform1.p_addr1.value = addr;
		document.mform1.p_addr2.focus();
	}else if( postgubun == "2" ){
		document.mform1.p_comp_post1.value = post1;
		document.mform1.p_comp_post2.value = post2;
		document.mform1.p_comp_addr1.value = addr;
		document.mform1.p_comp_addr2.focus();
    }
}


function Submit_Check(){
	var fm = document.mform1;

		if( (fm.p_eng_name.value) == ""){
			alert("영문이름을 입력하세요");
			fm.p_eng_name.focus();return;
		}

		if( (fm.p_comp.value) == ""){
            alert("소속기관명을 입력하세요");
            fm.p_comp.focus();return;
        }

		if( (fm.p_dept.value) == ""){
            alert("소속부서명을 입력하세요");
            fm.p_dept.focus();return;
        }

		if( (fm.p_email.value) == ""){
			alert("Email 주소를 바르게 입력하세요");
			fm.p_email.focus();return;
		}

		// e-mail check
		if(is_mail(fm.p_email.value) == false)
		{
	    	alert("정확한 E-Mail을 입력하십시오.");
	    	fm.p_email.focus();
	    	return;
		}

		if( (fm.p_comp_tel1.value) == "" ){
			alert("직장전화를 입력하세요");
			fm.p_comp_tel1.focus(); 
			return;
		} else if( (fm.p_comp_tel3.value) == "" ){
            alert("직장전화를 입력하세요");
            fm.p_comp_tel2.focus(); 
            return;
        } else if((fm.p_comp_tel3.value) == ""){
            alert("직장전화를 입력하세요");
            fm.p_comp_tel3.focus(); 
            return;
        }

		if( (fm.p_comp_fax1.value) == ""){
            alert("직장팩스를 입력하세요");
            fm.p_comp_fax1.focus(); 
            return;
        } else if( (fm.p_comp_fax2.value) == ""){
            alert("직장팩스를 입력하세요");
            fm.p_comp_fax2.focus(); 
            return;
        } else if( (fm.p_comp_fax3.value) == ""){
            alert("직장팩스를 입력하세요");
            fm.p_comp_fax3.focus(); 
            return;
        }

		if( (fm.p_handphone1.value) == ""){
            alert("핸드폰 번호를 입력하세요");
            fm.p_handphone1.focus(); return;
        } else if( (fm.p_handphone2.value) == "" ){
            alert("핸드폰 번호를 입력하세요");
            fm.p_handphone2.focus(); return;
        } else if((fm.p_handphone3.value) == ""){
            alert("핸드폰 번호를 입력하세요");
            fm.p_handphone3.focus(); return;
        }

		if( (fm.p_post1.value) == ""){
            alert("자택 우편번호를 입력하세요");
            fm.p_post1.focus();
            return;
        }

		if( (fm.p_addr2.value) == ""){
            alert("자택 상세주소를 입력하세요");
            fm.p_addr2.focus();
            return;
        }

		if( (fm.p_home_tel1.value) == ""){
            alert("자택전화번호를 입력하세요");
            fm.p_home_tel1.focus(); 
            return;
        } else if( (fm.p_home_tel2.value) == ""){
            alert("자택전화번호를 입력하세요");
            fm.p_home_tel2.focus(); 
            return;
        } else if( (fm.p_home_tel3.value) == "" ){
            alert("자택전화번호를 입력하세요");
            fm.p_home_tel3.focus(); 
            return;
        }

		if( (fm.p_education.value) == "ALL"){
            alert("학력을 선택하세요");
            fm.p_education.focus();
            return;
        }

		var v_comp_tel = fm.p_comp_tel1.value+'-'+fm.p_comp_tel2.value+'-'+fm.p_comp_tel2.value;
	    var v_comp_fax = fm.p_comp_fax1.value+'-'+fm.p_comp_fax2.value+'-'+fm.p_comp_fax3.value;
	    var v_home_tel = fm.p_home_tel1.value+'-'+fm.p_home_tel2.value+'-'+fm.p_home_tel2.value;
	    var v_handphone = fm.p_handphone1.value+'-'+fm.p_handphone2.value+'-'+fm.p_handphone3.value;

	    var checkObj   = document.getElementsByName("p_special_field");
	    var checkObjLen = checkObj.length;
	    var cnt = 0 
	    
	    for(var i = 0 ; i < checkObjLen ; i++) {
	        if(checkObj[i].checked) {
	            cnt++;
	        }
	    }

	    if(cnt == 0 ) {
	        alert("전문 분야 하나 이상 선택하세요.");
	        return;
	    }
	    
	// 가입
		document.mform1.target="_self";
		document.mform1.action="/servlet/controller.tutor.OffExpertHomePageServlet";
		document.mform1.p_comp_tel.value = v_comp_tel;
        document.mform1.p_comp_fax.value = v_comp_fax;
        document.mform1.p_home_tel.value = v_home_tel;
        document.mform1.p_mobile_phone.value = v_handphone;
		document.mform1.p_process.value="insert";
		document.mform1.submit();

}


//-------------------------------------------------------
	// 이메일 및 이메일형태 ID검사
	//-------------------------------------------------------
	function is_mail(strEmail)
	{
	    /** 금지사항
	     - @가 2개이상
	     - .이 붙어서 나오는 경우
	     -  @.나  .@이 존재하는 경우
	     - 맨처음이.인 경우 **/
	    var regDoNot = /(@.*@)|(\.\.)|(@\.)|(\.@)|(^\.)/;

	    /** 필수사항
	     - @이전에 하나이상의 문자가 있어야 함
	     - @가 하나있어야 함
	     - Domain명에 .이 하나 이상 있어야 함
	     - Domain명의 마지막 문자는 영문자 2~4개이어야 함 **/
	    var regMust = /^[a-zA-Z0-9\-\.\_]+\@[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,4})$/;

	    if ( !regDoNot.test(strEmail) && regMust.test(strEmail) )
	    {
	        return true;
	    }
	    else
	    {
	        return false;
	    }
	}

	function changeSpecialField(gubun){
	    var divObjA    = document.getElementById("A1");
	    var divObjB    = document.getElementById("B1");
	    var checkObj   = document.getElementsByName("p_special_field");
	    var checkObjLen = checkObj.length;
		
	    if(gubun == "A1") {
	    	divObjA.style.display = "block";
	    	divObjB.style.display = "none";
	    } else {
	    	divObjA.style.display = "none";
            divObjB.style.display = "block";
	    }

	    for(var i = 0 ; i < checkObjLen ; i++) {
	        checkObj[i].checked = false;
	    }
	    	
	}
	function viewPage(){
	    document.mform1.p_process.value= "viewPage";
	    document.mform1.action = "/servlet/controller.tutor.OffExpertHomePageServlet";
	    document.mform1.submit();
	}

</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->

<form name="mform1" method="post" action="" enctype="multipart/form-data">
	<input type="hidden"	name="p_process"	value="">
	<input type="hidden"	name="p_resno"		value="">
	<input type="hidden"	name="p_comp_tel">
	<input type="hidden"    name="p_comp_fax">
	<input type="hidden"    name="p_mobile_phone">
	<input type="hidden"    name="p_home_tel">
	<input type="hidden"    name="p_postgubun">

            <h2><img src="/images/portal/information/h2_tit10.gif" alt="전문가등록하기" class="fl_l" /><p class="category">Home > 정보광장 > <strong>전문가등록하기</strong></p></h2>

            <p><img src="/images/portal/information/professional_borimg.gif" alt="콘텐츠진흥원은 전문인력 확보 및 적극적인 활용을 위해 전문가 등록 시스템을 구축하고 있습니다. 탁월한 능력과 역량이 있는 분들의 많은 참여를 바랍니다" /></p>

            <ul class="tabwrap mg_t30">
                <li><a href="javascript:insertPage();" class="tab_information tab_information_on"><span>전문가 등록하기</span></a></li>
                <li><a href="javascript:viewPage();" class="tab_information"><span>전문가 등록기준</span></a></li>
                <li class="board_comment">* 표시는 필수입력 항목입니다.</li>
            </ul>

            <table class="write_white">
            <!--[if ie]><colgroup><col width="135px" /><col width="210px" /><col width="135px" /><col width="210px" /></colgroup><![endif]-->
            <colgroup><col width="120px" /><col width="225px" /><col width="120px" /><col width="225px" /></colgroup>
            <tr>
                <th class="th star">* 등록분야</th>
                <td colspan="3" class="td txt end"><input name="p_gubun" type="radio" value="A1"  onclick="changeSpecialField('A1')" checked/>방송영상<input name="p_gubun" type="radio" value="B1" class="mg_l35" onclick="changeSpecialField('B1')"/>게임개발</td>
            </tr>
            <tr>
                <th class="th star">* 성명</th>
                <td class="td txt"><%=s_usernm %></td>
                <th class="th star">* 주민번호</th>
                <td class="td end txt"><%=s_resno %></td>
            </tr>
            <tr>
                <th class="th star">* 영문이름</th>
                <td colspan="3" class="td end"><input name="p_eng_name" type="text" class="inbox" style="width:170px;" /><strong>영문 25자 이하</strong></td>
            </tr>
            <tr>
                <th class="th star">* 소속기관명</th>
                <td class="td"><input name="p_comp" type="text" class="inbox" style="width:170px;" /></td>
                <th class="th star">* 소속부서명</th>
                <td class="td end"><input name="p_dept" type="text" class="inbox" style="width:170px;" /></td>
            </tr>
            <tr>
                <th class="th">직장주소</th>
                <td colspan="3" class="td end">
                <p><input name="p_comp_post1" type="text" class="inbox" size=3 maxlength=3 readonly/> - <input name="p_comp_post2" type="text" class="inbox" size=3 maxlength=3 readonly/><a href="javascript:searchPost(2);" class="board_btn mg_l10"><span>우편번호찾기</span></a></p>
                <p><input name="p_comp_addr1" type="text" class="inbox" style="width:390px;" /></p>
                <p><input name="p_comp_addr2" type="text" class="inbox" style="width:390px;" /></p>
                </td>
            </tr>
            <tr>
                <th class="th star">* 직장전화</th>
                <td><input name="p_comp_tel1" type="text" class="inbox" maxlength=3 style="width:40px;" /> <input name="p_comp_tel2" type="text" class="inbox" maxlength=4 style="width:40px;" /> <input name="p_comp_tel3" type="text" class="inbox" maxlength=4 style="width:40px;" /></td>
                <th class="th star">* 직장팩스</th>
                <td class="td end"><input name="p_comp_fax1" type="text" class="inbox" style="width:40px;" maxlength=3/> <input name="p_comp_fax2" type="text" class="inbox" style="width:40px;" maxlength=4/> <input name="p_comp_fax3" type="text" class="inbox" style="width:40px;" maxlength=4/></td>
            </tr>
            <tr>
                <th class="th star">* 휴대폰</th>
                <td colspan="3" class="td end"><input name="p_handphone1" type="text" class="inbox" style="width:40px;" maxlength=3/> <input name="p_handphone2" type="text" class="inbox" style="width:40px;" maxlength=4/> <input name="p_handphone3" type="text" class="inbox" style="width:40px;" maxlength=4/></td>
            </tr>
            <tr>
                <th class="th star">* 이메일</th>
                <td><input name="p_email" type="text" class="inbox" style="width:170px;" /></td>
                <th class="th star">* 거주지</th>
                <td class="td end"><input name="p_living_place" type="radio" value="A" />국내<input name="p_living_place" type="radio" value="B" class="mg_l35" />국외</td>
            </tr>
            <tr>
                <th class="th star">* 자택주소</th>
                <td colspan="3" class="td end">
                <p><input name="p_post1" type="text" class="inbox" size=3 maxlength=3 readonly/> - <input name="p_post2" type="text" class="inbox" size=3 maxlength=3 readonly/><a href="javascript:searchPost(1);" class="board_btn mg_l10"><span>우편번호찾기</span></a></p>
                <p><input name="p_addr1" type="text" class="inbox" style="width:390px;" /></p>
                <p><input name="p_addr2" type="text" class="inbox" style="width:390px;" /></p>
                </td>
            </tr>
            <tr>
                <th class="th star">* 자택전화</th>
                <td class="td"><input name="p_home_tel1" type="text" class="inbox" style="width:40px;" maxlength=3/> <input name="p_home_tel2" type="text" class="inbox" style="width:40px;" maxlength=4/> <input name="p_home_tel3" type="text" class="inbox" style="width:40px;" maxlength=4/></td>
                <th class="th">최종학력</th>
                <td class="td end">
                    <kocca_select:select                                       
                    name="p_education"                                         
                    sqlNum="0001"                                    
                    param="0069"             
                    onChange=""                         
                    isLoad="true"                                           
                    type="1"                                                
                    styleClass=""                                
                    all="true" />
                </td>
            </tr>
			<tr>
                <th class="th">전문분야</th>
                <td colspan="3" class="td list end">
				<% 
                if (selectListGubun !=null && selectListGubun.size() > 0 ) {
                    String v_parent_code   = "";
                    String v_parent_name   = "";
                    String v_upper_code    = "";
                    String v_upper_name    = "";
                    String v_code          = "";
                    String v_codenm        = "";
                    int    v_upper_cnt     =  0;
                    int    v_parent_cnt    =  0;
                    List   v_subList       = null;
                    
                    int cnt = 0;
                    
                    String temp_parent     = "";
                    String className        = "";
                    
                    for(int i = 0 ; i < selectListGubun.size() ; i ++) {
                        DataBox gbox    = (DataBox) selectListGubun.get(i);
                        v_parent_code   = gbox.getString("d_parent_code"); 
                        v_parent_name   = gbox.getString("d_parent_name");
                        v_upper_code    = gbox.getString("d_upper_code");
                        v_upper_name    = gbox.getString("d_upper_name");
                        v_parent_cnt    = gbox.getInt("d_parent_cnt");
                        v_subList       = (List)gbox.getObject("d_subList");
                        
                        cnt++;
                         
                         if( !v_parent_code.equals(temp_parent)) { 
                        	 className = "class='first'";
                %>
                <div id = "<%= v_parent_code %>" <%=i == 0 ? "style='display:block;'" : "style='display:none;'"  %>>
                <%         } %>
                    <dl <%=className %>>
                        <dt><%=v_upper_name %>(<%=v_parent_name %>)</dt>
                        <dd>
                            <ul>
                 <% 
                         for (int j = 0 ; j < v_subList.size() ; j++) { 
                             DataBox subBox    = (DataBox) v_subList.get(j);
                            
                             v_code      = subBox.getString("d_code");
                             v_codenm    = subBox.getString("d_codenm");
                %>           
                                <li><input type="checkbox" name="p_special_field" value="<%=v_code %>" ><%=v_codenm %></li>
                <%       }
                         temp_parent = v_parent_code;
                         className = "";
                %>
                            </ul>
                        </dd>
                    </dl>
                <%         if( cnt == v_parent_cnt  || i == selectListGubun.size() -1) {
                             cnt = 0;
                %>
                </div>
                <%         } 
                    }
                }    %>
                </td>
            </tr>
            <tr>
                <th class="th">소개<br /><span>(학력,저서,경력등)</span></th>
                <td colspan="3" class="td end"><textarea name="p_introduce" class="inbox" style="width:95%; height:47px;"></textarea></td>
            </tr>
            <tr>
                <th class="th">사진</th>
                <td colspan="3" class="td end">
                <jsp:include page="/learn/user/portal/include/singleAttach_include.jsp">
                    <jsp:param name="i_imageFlag"           value="N" />
                    <jsp:param name="i_realFileName"        value="" />
                    <jsp:param name="i_saveFileName"        value="" />
                    <jsp:param name="i_targetImg"           value="" />
                    <jsp:param name="i_inputFileParamName"  value="p_photo_file" />
                    <jsp:param name="i_formName"            value="mform1" />
                    <jsp:param name="i_ServletName"         value="OffExpertHomePageServlet" />
                    <jsp:param name="i_searchOnImagePath"   value="/images/portal/btn/file_search_on.gif" />
                    <jsp:param name="i_searchOffImagePath"  value="/images/portal/btn/file_search.gif" />
                    <jsp:param name="i_style"               value="" />
                </jsp:include>
                </td>
            </tr>
            <tr>
                <th class="th">소개파일<span>(이력서)</span></th>
                <td colspan="3" class="td end">
                <div class="imgup">
                <jsp:include page="/learn/user/portal/include/singleAttach_include.jsp">
                    <jsp:param name="i_imageFlag"           value="N" />
                    <jsp:param name="i_realFileName"        value="" />
                    <jsp:param name="i_saveFileName"        value="" />
                    <jsp:param name="i_targetImg"           value="" />
                    <jsp:param name="i_inputFileParamName"  value="p_intro_file" />
                    <jsp:param name="i_formName"            value="mform1" />
                    <jsp:param name="i_ServletName"         value="OffExpertHomePageServlet" />
                    <jsp:param name="i_searchOnImagePath"   value="/images/portal/btn/file_search_on.gif" />
                    <jsp:param name="i_searchOffImagePath"  value="/images/portal/btn/file_search.gif" />
                    <jsp:param name="i_style"               value="" />
                </jsp:include>
                </div>
                </td>
            </tr>
            </table>
            
            <p class="board_btn ag_c mg_t15"><a href="javascript:Submit_Check();" class="btn_violet mg_r7"><span>등록</span></a></p>
            
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->