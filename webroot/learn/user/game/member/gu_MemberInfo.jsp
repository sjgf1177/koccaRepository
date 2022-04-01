<%
//**********************************************************
//  1. 제      목: 회원정보수정
//  2. 프로그램명 : gu_MemberInfo.jsp
//  3. 개      요: 회원정보수정
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String s_userid= box.getSession("userid");

    String v_name     = box.getSession("p_name");
    String v_resno    = box.getString("p_resno");
    String v_userid   = box.getSession("p_userid");
    String v_process  = box.getString("p_process");
    String v_isEvent = box.getSession("isevent");

	String v_post1_value = "";
	String v_post2_value = "";
	String v_compost1_value = "";
	String v_compost2_value = "";

    //ArrayList list1   = (ArrayList)request.getAttribute("memberView");

    DataBox dbox  = (DataBox)request.getAttribute("memberView");

    String v_comptext = box.getSession("tem_grcode");
%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topMember.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT>

// 우편번호검색
function searchPost(gubun) {
    window.self.name = "PersonalSelect";
    open_window("openPost","","100","100","417","400","","","","yes","");
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
        document.mform1.p_home_addr1.value = addr;
        document.mform1.p_home_addr2.focus();
    }else if( postgubun == "2" ){
        document.mform1.p_comp_post1.value = post1;
        document.mform1.p_comp_post2.value = post2;
        document.mform1.p_comp_addr1.value = addr;
        document.mform1.p_comp_addr2.focus();
    }
}

function Submit_Check(){
    var fm=document.mform1;
    var v_handphone = fm.p_handphone1.value+'-'+fm.p_handphone2.value+'-'+fm.p_handphone3.value;
/*
        if( (fm.p_userid.value) == "" ){
            alert("아이디를 바르게 입력하세요");
            fm.p_userid.focus();return;
        }
 */
        // 비밀번호 길이 제한
    var pass = document.mform1.p_pwd.value;
    var p_pwd_len = pass.length;

        if(p_pwd_len < 0){
            alert("비밀번호를 입력해주세요..");
            return;
        }else{
            if(p_pwd_len < 3 && p_pwd_len > 13){
                alert("비밀번호를 4자이상 12자 이하로 다시 입력해주세요..");
                fm.p_pwd.value ="";
                fm.p_pwd.focus();
                return;
            }
        }

        if( (fm.p_pwd2.value) == ""){
            alert("비밀번호확인을 입력하세요");
            fm.p_pwd2.focus();return;
        }

        if(fm.p_pwd.value != fm.p_pwd2.value){
            alert("비밀번호가 다르게 입력되었습니다.");
            fm.p_pwd.value ="";
            fm.p_pwd2.value ="";
            fm.p_pwd.focus();
            return;
        }
/*
        if( (fm.p_kor_name.value) == ""){
            alert("이름을 입력하세요");
            fm.p_kor_name.focus();return;
        }
 */
// 주소 (둘중하나만 입력)
    var v_post      = fm.p_post1.value + fm.p_post2.value;
    var v_comp_post = fm.p_comp_post1.value + fm.p_comp_post2.value;
    var v_home_addr = fm.p_home_addr1.value + fm.p_home_addr2.value;
    var v_comp_addr = fm.p_comp_addr1.value + fm.p_comp_addr2.value;

        if( (v_post) == "" && (v_comp_post) == "" ){
            alert("우편번호를 입력하세요");
            fm.p_post1.focus(); return;

            if( v_post != ""){
                if( v_home_addr == "" ){
                    if( (fm.p_home_addr1.value) == "" ){
                        alert("주소를 입력하세요");
                        fm.p_home_addr1.focus();    return;
                    }else if( (fm.p_home_addr2.value) == "" ){
                        alert("주소를 입력하세요");
                        fm.p_home_addr2.focus();    return;
                    }
                }
            }else if( v_comp_post != "" ){
                if( v_comp_addr == "" ){
                    if( (fm.p_comp_addr1.value) == "" ){
                        alert("주소를 입력하세요");
                        fm.p_comp_addr1.focus();    return;
                    }else if( (fm.p_comp_addr2.value) == "" ){
                        alert("주소를 입력하세요");
                        fm.p_comp_addr2.focus();    return;
                    }
                }
            }
        }

        if( (v_home_addr) == "" && (v_comp_addr) == "" ){
            alert("직장주소/집주소 중 하나는 꼭 입력하셔야만 됩니다."); return;
        }

        // -->

        if( (fm.p_email.value) == ""){
            alert("Email 주소를 바르게 입력하세요");
            fm.p_email.focus();return;
        }
		<% if(!v_comptext.equals("N000017")) {	//장애인고용촉진공단 제외 %>
		if( (fm.p_comptext.value) == ""){
			alert("소속을 입력하세요");
			fm.p_comptext.focus(); return;
		}
		<% } %>
        //
        if(mform1.p_jikup.options.selectedIndex == ""){
            alert("직업을 선택하세요");  return;
        }
        if(mform1.p_degree.options.selectedIndex == ""){
            alert("최종학력을 선택하세요");  return;
        }

            if( (fm.p_handphone1.value) == "" ){
                alert("핸드폰 번호를 입력하세요");
                fm.p_handphone1.focus(); return;
            }else if( (fm.p_handphone2.value) == "" || fm.p_handphone2.length > 5 ){
                alert("핸드폰 번호를 바르게 입력하세요");
                fm.p_handphone2.focus(); return;
            }else if( (fm.p_handphone3.value) == "" || fm.p_handphone3.length > 5 ){
                alert("핸드폰 번호를 입력하세요");
                fm.p_handphone3.focus(); return;
            }

        if( (fm.p_hometel.value) == "" && (fm.p_comptel.value) == "" ){
            alert("2개중 연락처 1개는 반드시 기재바랍니다."); return;
            if( (fm.p_hometel.length) > 12 ){
                alert("연락처를 바르게 입력하세요");
            }
            if( (fm.p_comptel.length) > 12 ){
                alert("연락처를 바르게 입력하세요");
            }
        }

    // 가입
    	document.mform1.target="_self";
        document.mform1.action="/servlet/controller.homepage.MemberInfoServlet";
        document.mform1.p_handphone.value = v_handphone;
        document.mform1.p_process.value="memberUpdate";
        document.mform1.submit();

}

	// 이벤트회원 인증체크
	function is_authcheck() {
		var form = document.mform1;
		if(!form.p_iseventchk.value) {
			alert("인증번호를 입력하세요!");
			return;
		}
		form.target="authifr";
		form.action="/learn/user/game/member/gu_MemberAuthChk.jsp";
		form.submit();
	}

	// 추천회원 인증체크
	function is_usrcheck() {
		var form = document.mform1;
		if(!form.p_ruserid.value) {
			alert("추천아이디를 입력하세요!");
			return;
		}
		form.target="authifr";
		form.action="/servlet/controller.homepage.EventInfoServlet?p_process=eventRecom&p_userid="+form.p_userid.value+"&p_ruserid="+form.p_ruserid.value+"&p_message=Y";
		form.submit();
	}

</SCRIPT>
        <!-- title -->
        <div style="width:720px;height:35px;margin-left:0px;margin-bottom:20px;background-image:url('/images/user/game/member/<%=tem_subimgpath%>/tit_member_info.gif');text-align:right;padding:19px 25px 0px 0px;font-size:11px;color:#000000;">
            <img src="/images/user/game/member/blt01.gif"> Home &gt; 개인정보변경
        </div>
        <!-- //title -->

        <div style="margin-left:27px;text-align:left;">
            <img src="/images/user/game/member/join_i03.gif" ><br>
            <img src="/images/user/game/member/text_img_mem.gif"><br>
            <br>
        </div>


<form name="mform1" method="post" action="">
    <input type="hidden"    name="p_process"    value="<%=v_process%>">
    <input type="hidden"    name="p_userid"     value="<%=s_userid%>">
    <input type="hidden"    name="p_resno"      value="<%=v_resno%>">
    <input type="hidden"    name="p_handphone"  value="">
    <input type="hidden"    name="p_postgubun"  value="">
    <input type="hidden"    name="p_chk" >

<%
    //for (int i=0; i<list1.size(); i++) {
        //DataBox dbox = (DataBox)list1.get(i);
        String v_degree    = dbox.getString("d_degree");
        String v_jikup     = dbox.getString("d_jikup");
        String v_handphone = dbox.getString("d_handphone");

        String kukbun      = "";
        String middle      = "";
        String bunho       = "";

        if(v_handphone.length() == 3)
		{
			kukbun	= v_handphone;
            middle	= "";
            bunho	= "";
		}
		else if(v_handphone.length() == 10)
		{
			kukbun	= v_handphone.substring(0,3);
            middle	= v_handphone.substring(3,6);
            bunho	= v_handphone.substring(6,10);
		}
		else if(v_handphone.length() == 11)
		{
			kukbun	= v_handphone.substring(0,3);
            middle	= v_handphone.substring(3,7);
            bunho	= v_handphone.substring(7,11);
		}
		else
		{
			StringTokenizer stz= new StringTokenizer(v_handphone,"-");

			while(stz.hasMoreElements()){
				kukbun	= stz.nextToken();
				middle	= stz.nextToken();
				bunho	= stz.nextToken();
			}
		}

		if(dbox.getString("d_post1").length() == 7) v_post1_value = dbox.getString("d_post1").substring(0,3);
		else v_post1_value = dbox.getString("d_post1");
		if(dbox.getString("d_post2").length() == 7) v_post2_value = dbox.getString("d_post2").substring(4,7);
		else v_post2_value = dbox.getString("d_post2");

		if(dbox.getString("d_comp_post1").length() == 7) v_compost1_value = dbox.getString("d_comp_post1").substring(0,3);
		else v_compost1_value = dbox.getString("d_comp_post1");
		if(dbox.getString("d_comp_post2").length() == 7) v_compost2_value = dbox.getString("d_comp_post2").substring(4,7);
		else v_compost2_value = dbox.getString("d_comp_post2");
%>
            <img src="/images/user/game/member/join3_st01.gif" vspace="0"><br>
            <table cellpadding=0 cellspacing=0 width=720>
            <col width=65><col width=65><col width=175><col width=95><col width=290>
            <tr height=1><td class=lineColor colspan=5></td></tr>
            <tr height=26>

                <td style="padding-left:10;" colspan=2><img src="/images/user/game/member/blt02.gif" align=absmiddle> 아이디</td>
                <td colspan=3 style="font-size:11px;color:#6d5952;"><%=s_userid%>
                </td>

            </tr>
            <tr height=1><td class=lineColor colspan=5></td></tr>
            <tr height=26>
                <td style=padding-left:10; colspan=2><img src="/images/user/game/member/blt02.gif" align=absmiddle> 비밀번호</td>
                <td colspan=3 style=font-size:11px;color:#6d5952;>
                    <input name="p_pwd" type=password class=input2 style=width:175 value="<%=dbox.getString("d_pwd")%>">
                </td>
            </tr>
            <tr height=1><td class=lineColor colspan=5></td></tr>
            <tr height=26>
                <td style=padding-left:10; colspan=2><img src="/images/user/game/member/blt02.gif" align=absmiddle> 비밀번호확인</td>
                <td colspan=3>
                    <input name="p_pwd2" type=password class=input2 style=width:175 value="<%=dbox.getString("d_pwd")%>">
                </td>
            </tr>
            <tr height=1><td class=lineColor colspan=5></td></tr>
            <tr height=26>
                <td style=padding-left:10; colspan=2><img src="/images/user/game/member/blt02.gif" align=absmiddle> 이름(한글)</td>
                <td style=font-size:11px><%=dbox.getString("d_name")%>
                </td>
                <td align=center>이름(영문)</td>
                <td style=font-size:11px>
                    <input name="p_eng_name" type=text class=input2 style=width:175 value="<%=dbox.getString("d_eng_name")%>">
                </td>
            </tr>
            <tr height=1><td class=lineColor colspan=5></td></tr>
            <tr height=4><td colspan=5></td></tr>
            <tr height=26>
                <td style="padding-left:10;border-right:1px solid #dcd8d7" rowspan=4><img src="/images/user/game/member/blt02.gif" align=absmiddle> 주소</td>
                <td align=center>우편번호</td>
                <td style=font-size:11px;color:#6d5952; colspan=3>
                    <input name="p_post1" type=text class=input2 style=width:80 readonly value="<%=v_post1_value%>">
                    -
                    <input name="p_post2" type=text class=input2 style=width:80 readonly value="<%=v_post2_value%>">
                    <a href="javascript:searchPost(1)"><img src="/images/user/game/button/btn_zipcode.gif" align=absmiddle></a>
                    직장주소/집주소 중 하나는 꼭 입력하셔야만 됩니다.
                </td>
            </tr>
            <tr height=26>
                <td align=center>자택주소</td>
                <td colspan=3>
                    <input name="p_home_addr1" type=text class=input2 style=width:265 readonly value="<%=dbox.getString("d_addr")%>">
                    <input name="p_home_addr2" type=text class=input2 style=width:185 value="<%=dbox.getString("d_addr2")%>">
                    <a href="javascript:delAddr(1)"><img src="/images/user/game/button/btn_deladd.gif"></a>
                </td>
            </tr>
            <tr height=26>
                <td align=center>우편번호</td>
                <td colspan=3>
                    <input name="p_comp_post1" type=text class=input2 style=width:80 readonly value="<%=v_compost1_value%>">
                    -
                    <input name="p_comp_post2" type=text class=input2 style=width:80 readonly value="<%=v_compost2_value%>">
                    <a href="javascript:searchPost(2)"><img src="/images/user/game/button/btn_zipcode.gif"></a>
                </td>
            </tr>
            <tr height=26>
                <td align=center>직장주소</td>
                <td colspan=3>
                    <input name="p_comp_addr1" type=text class=input2 style=width:265 readonly value="<%=dbox.getString("d_comp_addr1")%>">
                    <input name="p_comp_addr2" type=text class=input2 style=width:185 value="<%=dbox.getString("d_comp_addr2")%>">
                    <a href="javascript:delAddr(2)"><img src="/images/user/game/button/btn_deladd.gif"></a>
                </td>
            </tr>
            <tr height=4><td colspan=5></td></tr>
            <tr height=1><td class=lineColor colspan=5></td></tr>
            <tr height=26>
                <td style=padding-left:10; colspan=2><img src="/images/user/game/member/blt02.gif" align=absmiddle> E-Mail</td>
                <td colspan=3>
                    <input name="p_email" type=text class=input2 style=width:175 value="<%=dbox.getString("d_email")%>">
                </td>
            </tr>
            <% if(v_comptext.equals("N000017")) {	//장애인고용촉진공단 %>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			<tr height=26>
				<td style=padding-left:10; colspan=2><img src="/images/user/game/member/blt02.gif" align=absmiddle> 소속센터</td>
				<td colspan=3>
					<select name="p_comptext" style="width:80;" >
					<option value="없음" <% if(dbox.getString("d_comptext").equals("없음")) { out.print("selected"); } %>>없음</option>
					<option value="대구센터" <% if(dbox.getString("d_comptext").equals("대구센터")) { out.print("selected"); } %>>대구</option>
					<option value="대전센터" <% if(dbox.getString("d_comptext").equals("대전센터")) { out.print("selected"); } %>>대전</option>
					<option value="부산센터" <% if(dbox.getString("d_comptext").equals("부산센터")) { out.print("selected"); } %>>부산</option>
					<option value="일산센터" <% if(dbox.getString("d_comptext").equals("일산센터")) { out.print("selected"); } %>>일산</option>
					<option value="전남센터" <% if(dbox.getString("d_comptext").equals("전남센터")) { out.print("selected"); } %>>전남</option>
					</select>
					&nbsp;
					<font color=red>※한국장애인고용촉진공단 직업능력개발센터 학생은 소속 센터를 선택하시기 바랍니다.</font>
				</td>
			</tr>
			<% } else { %>
            <tr height=1><td class=lineColor colspan=5></td></tr>
            <tr height=26>
                <td style=padding-left:10; colspan=2><img src="/images/user/game/member/blt02.gif" align=absmiddle> 소속</td>
                <td colspan=3>
                    <input name="p_comptext" type=text class=input2 style=width:150 value="<%=dbox.getString("d_comptext")%>">
                    <font color=red>※ 대학 및 특화고 회원님들은 반드시 정확한 학교명을 넣어주시기 바랍니다.</font>
                </td>
            </tr>
			<% } %>
            <tr height=1><td class=lineColor colspan=5></td></tr>
            <tr height=26>
                <td style=padding-left:10; colspan=2><img src="/images/user/game/member/blt02.gif" align=absmiddle> 최종학력</td>
                <td colspan=3 style="width:100;">
                    <%= CodeConfigBean.getCodeGubunSelect("0069","",1,"p_degree",v_degree,"",3)%>
                </td>
            </tr>
            <tr height=1><td class=lineColor colspan=5></td></tr>
            <tr height=26>
                <td style=padding-left:10; colspan=2><img src="/images/user/game/member/blt02.gif" align=absmiddle> 직업</td>
                <td colspan=3 style="width:100;">
                    <%= CodeConfigBean.getCodeGubunSelect("0070","",1,"p_jikup",v_jikup,"",3)%>
                </td>
            </tr>
            <tr height=1><td class=lineColor colspan=5></td></tr>
            <tr height=26>
                <td style=padding-left:10; colspan=2><img src="/images/user/game/member/blt02.gif" align=absmiddle>핸드폰</td>
                <td colspan=3>
                    <select name="p_handphone1" style="width:80;" >
                    <option value="">== 선택 ==</option>
                    <option value="010" <%if(kukbun.equals("010")){out.println("selected");}%>>010</option>
                    <option value="011" <%if(kukbun.equals("011")){out.println("selected");}%>>011</option>
                    <option value="016" <%if(kukbun.equals("016")){out.println("selected");}%>>016</option>
                    <option value="017" <%if(kukbun.equals("017")){out.println("selected");}%>>017</option>
                    <option value="018" <%if(kukbun.equals("018")){out.println("selected");}%>>018</option>
                    <option value="019" <%if(kukbun.equals("019")){out.println("selected");}%>>019</option>
                    </select>
                    -
                    <input name="p_handphone2" type=text class=input2 style=width:100 value="<%=middle%>">
                    -
                    <input name="p_handphone3"type=text class=input2 style=width:100 value="<%=bunho%>">
                </td>
            </tr>
            <tr height=1><td class=lineColor colspan=5></td></tr>
            <tr height=4><td colspan=5></td></tr>
            <tr height=26>
                <td style="padding-left:10;border-right:1px solid #dcd8d7" rowspan=2><img src="/images/user/game/member/blt02.gif" align=absmiddle> 전화</td>
                <td align=center>자택</td>
                <td colspan=2>
                    <input name="p_hometel" type=text class=input2 style=width:175 value="<%=dbox.getString("d_hometel")%>">
                </td>
                <td style=font-size:11px;color:#6d5952; rowspan=2>
                    2개중 연락처 1개는 반드시 기재바랍니다.<br>
                    예)02-540-0000<br>
                </td>
            </tr>
            <tr height=26>
                <td align=center>직장</td>
                <td colspan=2>
                    <input name="p_comptel" type=text class=input2 style=width:175 value="<%=dbox.getString("d_comptel")%>">
                </td>
            </tr>
            <tr height=4><td colspan=5></td></tr>
            <tr height=1><td class=lineColor colspan=5></td></tr>
            </table>

            <table width="720" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td><img src="/images/user/game/member/box_t.gif" width="720" height="15"></td>
                </tr>
                <tr>
                  <td><table width="720" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="100" height="28" style="padding-left:15;">메일수신여부</td>
                        <td>
                            <%if( (dbox.getString("d_ismailing")) != null ){
                                    String v_ismailing = dbox.getString("d_ismailing");%>
                            <input name="p_ismailing" type=radio value="Y" <%if(v_ismailing.equals("Y")){out.println("checked");}%>> 수신
                            <input name="p_ismailing" type=radio value="N" <%if(v_ismailing.equals("N")){out.println("checked");}%>> 수신거부
                            <%}%>
                        </td>
                      </tr>
                      <tr height=1><td class=lineColor colspan=5></td></tr>
                      <tr>
                        <td height="28"  style=padding-left:15;>뉴스레터수신여부</td>
                        <td>
                            <%if( (dbox.getString("d_islettering")) != null ){
                                    String v_islettering = dbox.getString("d_islettering");%>
                            <input name="p_islettering" type=radio   value="Y" <%if(v_islettering.equals("Y")){out.println("checked");}%>> 수신
                            <input name="p_islettering" type=radio   value="N" <%if(v_islettering.equals("N")){out.println("checked");}%>> 수신거부
                            <%}%>
                        </td>
                      </tr>
                      <tr height=1><td class=lineColor colspan=5></td></tr>
                      <tr>
                        <td height="28"  style=padding-left:15;>연락처공개여부</td>
                        <td>
                            <%if( (dbox.getString("d_isopening")) != null ){
                                    String v_isopening = dbox.getString("d_isopening");%>
                            <input name="p_isopening" type=radio   value="Y" <%if(v_isopening.equals("Y")){out.println("checked");}%> > 공개
                            <input name="p_isopening" type=radio   value="N" <%if(v_isopening.equals("N")){out.println("checked");}%> > 비공개
                            <%}%>
                        </td>
                        <td width="387" style="font-size:11px;color:#6d5952;">(학습자
                          조회시 동료학생들에게 전화번호,email, 주소를 공개합니다.)
                        </td>
                      </tr>
					  <tr height=1><td class=lineColor colspan=5></td></tr>
					  <tr>
						<td height="28"  style=padding-left:15;>인증번호입력</td>
						<td>
						  <table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							 <td>
							 <input name="p_iseventchk" type=password class=input2 style=width:90 value="">
							 <input name="p_isevent" type=hidden value="N">
							 </td>
							 <td><a href="javascript:is_authcheck();"><img src="/images/user/game/button/btn_dcauth.jpg" border=0></a></td>
							 </tr>
						  </table>
						</td>
						<td width="440" style="font-size:11px;color:#6d5952;">(안내 자료를 통해 발급 된 인증번호를 입력하시기 바랍니다)
						</td>
					   </tr>
					  <!--
 					  <tr height=1><td class=lineColor colspan=5></td></tr>
					  <tr>
						<td height="28"  style=padding-left:15;>추천인입력</td>
						<td>
						  <table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							 <td><input name="p_ruserid" type=text class=input2 style=width:90 value=""></td>
							 <td><a href="javascript:is_usrcheck();"><img src="/images/user/game/button/recom_btn.jpg" border=0></a></td>
							 </tr>
						  </table>
						</td>
						<td width="440" style="font-size:11px;color:#6d5952;">(추천하신 분의 아이디를 입력하시기 바랍니다)
						</td>
					  </tr>
					  -->
                    </table></td>
                </tr>
                <tr>
                  <td><img src="/images/user/game/member/box_bo.gif" width="720" height="15"></td>
                </tr>
              </table>
<%
    //}
%>
              <table width="720" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="12"></td>
                </tr>
                <tr>
                  <td><div align="center">
                    <a href="javascript:Submit_Check()"><img src="/images/user/game/button/btn_info.gif" border="0"></a>&nbsp;&nbsp;
                    <a href="/servlet/controller.homepage.MainServlet"><img src="/images/user/game/button/btn_info_cancel.gif" border="0"></a>
                  </div></td>
                </tr>
              </table>
          </div>
        <br>
        <br>
    </form>
    <iframe name="authifr" width=0 height=0 frameborder=0></iframe>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
