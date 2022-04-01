<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    DataBox ASP_Edit_Login = (DataBox) request.getAttribute("ASP_Edit_Login");

    String v_userid=ASP_Edit_Login.get("d_userid");
    String v_name=ASP_Edit_Login.get("d_name");
    String v_grcode=ASP_Edit_Login.get("d_grcode");
    
    
    // 모바일 과학고 직원학생 및 학번 입력 변수
    String v_offcie_gbnnm=ASP_Edit_Login.get("d_office_gbnnm");
    String v_cono=ASP_Edit_Login.get("d_cono");
    
//    String v_pw=ASP_Edit_Login.get("d_pwd");

    String [] v_email;
    if (ASP_Edit_Login.get("d_email").indexOf("@") > -1) {
        v_email=ASP_Edit_Login.get("d_email").split("@");
    } else {
        v_email = new String[2];
        v_email[0]= "";
        v_email[1]="";
    }

    String [] v_tel;
    String [] v_moblie;
    if(ASP_Edit_Login.get("d_hometel").equals(""))
    {
        v_tel=new String[3];
        v_tel[0]="";v_tel[1]="";v_tel[2]="";
    }
    else
        v_tel=ASP_Edit_Login.get("d_hometel").split("-");

    if(ASP_Edit_Login.get("d_handphone").equals(""))
    {
        v_moblie=new String[3];
        v_moblie[0]="";v_moblie[1]="";v_moblie[2]="";
    }
    else
        v_moblie=ASP_Edit_Login.get("d_handphone").split("-");

    String v_ismail=ASP_Edit_Login.get("d_ismailing");
    String v_work_plcnm=ASP_Edit_Login.get("d_work_plcnm");
    String v_deptnm=ASP_Edit_Login.get("d_deptnam");

%>
<script type="text/javascript">
    $(document).ready(function(){
        var v_yn="<%=v_ismail%>";
        if(v_yn=="Y")
        {
            $("[name='p_agreed_Yn']").attr("checked","checked");
            $("[name='p_agreed']").val("Y");
        }
        else
        {
            $("[name='p_agreed_Yn']").attr("checked",false);
            $("[name='p_agreed']").val("N");
        }
    });

    function nextOk1()
    {
        var r2=$("[name='p_tel1']").val();
        if(r2=="")
        {
            alert("전화번호의 국번을 입력하여 주십시오.");
            $("[name='p_tel1']").focus();
            return;
        }

        var r3=$("[name='p_tel2']").val();
        if(r3=="")
        {
            alert("전화번호의 번호를 입력하여 주십시오.");
            $("[name='p_tel2']").focus();
            return;
        }

        var r4=$("[name='p_mobil1']").val();
        if(r4=="")
        {
            alert("휴대폰의 번호을 입력하여 주십시오.");
            $("[name='p_mobil1']").focus();
            return;
        }

        var r5=$("[name='p_mobil2']").val();
        if(r5=="")
        {
            alert("휴대폰의 번호를 입력하여 주십시오.");
            $("[name='p_mobil2']").focus();
            return;
        }

        var r6=$("[name='p_email1']").val();
        if(r6=="")
        {
            alert("이메일을 입력하여 주십시오.");
            $("[name='p_email1']").focus();
            return;
        }

        var r7=$("[name='p_email2']").val();
        if(r7=="")
        {
            alert("이메일을 입력하여 주십시오.");
            $("[name='p_email2']").focus();
            return;
        }
/*
        var r8=$("[name='p_organization']").val();
        if(r8=="")
        {
            alert("소속기관을 입력하여 주십시오.");
            $("[name='p_organization']").focus();
            return;
        }

        var r9=$("[name='p_deptnm']").val();
        if(r9=="")
        {
            alert("소속 부서를 입력하여 주십시오.");
            $("[name='p_deptnm']").focus();
            return;
        }
*/
        document.form1.p_process.value = "ASP_Login_Update";
        document.form1.menuid.value = "0";
        document.form1.gubun.value = "";
        <% if (box.getSession("tem_grcode").equals("N000022")) { %>
        	document.form1.action =  "/servlet/controller.homepage.MainServlet";
        <% }  else { %>
        	//document.form1.action = https_host() +  "/servlet/controller.homepage.MainServlet";
        	document.form1.action =  "/servlet/controller.homepage.MainServlet";
        <% } %>
        document.form1.submit();
    }

    function email2change()
    {
        var r=$("[name='p_email20']").val();
        $("[name='p_email2']").val(r);
    }

    function chkYN()
    {
        var r=$("[name='p_agreed_Yn']").attr("checked");

        if(!r)
            $("[name='p_agreed']").val("N");
        else
            $("[name='p_agreed']").val("Y");
    }

    function changePwd()
    {
        var r=$.trim($("[name='p_usepw']").val());
        var r1=$.trim($("[name='p_pw1']").val());
        var r2=$.trim($("[name='p_pw2']").val());

        if(r=="")
        {
            alert("현재 암호를 입력하세요.");
            $("[name='p_usepw']").focus();
            return;
        }

        if(r1.length<8)
        {
            alert("암호의 최소 길이는 8자리 입니다.다시 입력하세요.");
            $("[name='p_pw1']").focus();
            return;
        }

        if(r1=="")
        {
            alert("새로운 암호를 입력하세요.");
            $("[name='p_pw1']").focus();
            return;
        }

        if(r2.length<8)
        {
            alert("암호의 최소 길이는 8자리 입니다.다시 입력하세요.");
            $("[name='p_pw2']").focus();
            return;
        }

        if(r2=="")
        {
            alert("새로운 확인 암호를 다시 입력하세요.");
            $("[name='p_pw2']").focus();
            return;
        }

        if(r1!=r2)
        {
            alert("암호가 서로 일치하지 않습니다.");
            $("[name='p_pw1']").val("");
            $("[name='p_pw2']").val("");
            $("[name='p_pw1']").focus();
            return;
        }

        document.form1.p_process.value = "ASP_Login";
        document.form1.menuid.value = "0";
        document.form1.gubun.value = "100";

        $("[name='form1']").ajaxSubmit({
            url:'/servlet/controller.homepage.MainServlet',
           type:'post',
            data:{},
           success: function(data){
               var r10=$.trim(data);
               if(r10=="0")
                   alert("암호가 일치하지 않거나 없는 데이타 입니다.");
               else
                   alert("암호가 정상적으로 변경 되었습니다.");
            }
        });
    }

    function numbersonly(e,decimal){
        var key;
        var keychar;

        if(window.event){
            key = window.event.keyCode;
        }else if(e){
            key = e.which;
        }else{
            return true;
        }
        keychar = String.fromCharCode(key);

        if((key == null) || (key == 0) || (key == 8) || (key == 9) || (key == 13) || (key == 27)){
            return true;
        }else if((("0123456789").indexOf(keychar) > -1)){
            return true;
        }else if(decimal && (keychar == ".")){
            return true;
        }else{
            return false;
        }
    }
</script>
<table width="690px" border="0" cellspacing="0" cellpadding="0">
        <tr>
        <td>
        	<!--탑이미지영역 시작--->
			<%@ include file="/learn/user/portal/include/topMembership.jsp" %>  

        </td>
        </tr>
        <tr>
        <td>
        <!--메인영역--->
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr><td width="20">&nbsp;</td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="230" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="/images/asp/membership/left_title.gif" width="230" height="38" /></td>
              </tr>
              <tr>
                <td><img src="/images/asp/membership/leftm3_o.gif" width="230" /></td>
              </tr>
            </table></td>
            <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="38" align="left" valign="top"><img src="/images/asp/membership/title_3.gif" alt="개인정보변경"/></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td><img src="/images/asp/membership/stitle_4.gif" alt="회원정보" width="690" height="24" /></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>

             <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="2" bgcolor="#487bb0"></td>
                    <td bgcolor="#487bb0"></td>
                  </tr>
                  <tr>
                    <td width="130" height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b">성명(한글)</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"><%=v_name%>
                        <input type="hidden" name="p_agreed">
                    </td>
                  </tr>
                  <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>아이디</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"><%=v_userid%></td>
                  </tr>
                  
                  <!--  경기 모바일 과학고 학생직원 및 학번 입력  추가 입력-->
                  
                  <% if (v_grcode.equals("N000053")) { %>
                  <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>직원/학생</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px">
                    <select name="p_officegbnnm" >
                      <option value="직원" <% if (v_offcie_gbnnm.equals("직원")) out.print("selected"); %> >직원</option>
                      <option value="학생" <% if (v_offcie_gbnnm.equals("학생")) out.print("selected"); %> >학생</option>
                    </select>                    
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>학번</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"><input name="p_cono" type="text" class="inp_txt_01"  size="40"  value="<%=v_cono%>" /></td>
                  </tr>
                  <% } %>                 
                  
                  
                  <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>현재 비밀번호</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"><input name="p_usepw" type="password" class="inp_txt_01"  size="40" /></td>
                  </tr>
                   <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>새로운 비밀번호</td>
                    <td height="45" align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"><input name="p_pw1" type="password" class="inp_txt_01" size="40" />
                      <br />
                      <span class="lh_20 fs_11 ls">* 8자 이상의 숫자 혹은 영문으로 입력해주세요. 연속된 숫자, 생일, 주민번호 등 알기쉬운 숫자는 피해주세요.</span></td>
                  </tr>
                  <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>새로운 비밀번호 확인</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"><input name="p_pw2" type="password" class="inp_txt_01" size="40" />
                        <img src="/images/asp/membership/btn_pwchange.gif" alt="비밀번호 변경" onclick="changePwd();" style="CURSOR:POINTER">
                    </td>
                  </tr>
                  <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>전화번호</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"> 
                    	<select name="p_tel0" >
	                      <option value="02" <% if (v_tel[0].equals("02")) out.print("selected"); %> >02</option>
	                      <option value="031" <% if (v_tel[0].equals("031")) out.print("selected"); %> >031</option>
	                      <option value="032" <% if (v_tel[0].equals("032")) out.print("selected"); %> >032</option>
	                      <option value="033" <% if (v_tel[0].equals("033")) out.print("selected"); %> >033</option>
	                      <option value="041" <% if (v_tel[0].equals("041")) out.print("selected"); %> >041</option>
	                      <option value="042" <% if (v_tel[0].equals("042")) out.print("selected"); %> >042</option>
	                      <option value="043" <% if (v_tel[0].equals("043")) out.print("selected"); %> >043</option>
	                      <option value="044" <% if (v_tel[0].equals("044")) out.print("selected"); %> >044</option>
	                      <option value="051" <% if (v_tel[0].equals("051")) out.print("selected"); %> >051</option>
	                      <option value="052" <% if (v_tel[0].equals("052")) out.print("selected"); %> >052</option>
	                      <option value="053" <% if (v_tel[0].equals("053")) out.print("selected"); %> >053</option>
	                      <option value="054" <% if (v_tel[0].equals("054")) out.print("selected"); %> >054</option>
	                      <option value="055" <% if (v_tel[0].equals("055")) out.print("selected"); %> >055</option>
	                      <option value="061" <% if (v_tel[0].equals("061")) out.print("selected"); %> >061</option>
	                      <option value="062" <% if (v_tel[0].equals("062")) out.print("selected"); %> >062</option>
	                      <option value="063" <% if (v_tel[0].equals("063")) out.print("selected"); %> >063</option>
	                      <option value="064" <% if (v_tel[0].equals("064")) out.print("selected"); %> >064</option>
                        </select>
                    -
                      <input name="p_tel1" type="text" class="inp_txt_01"  size="15" value="<%=v_tel[1]%>" maxlength="4" style="ime-mode:disabled;" onKeyPress="return numbersonly(event,false)"/>
                      -
                      <input name="p_tel2" type="text" class="inp_txt_01"  size="15" value="<%=v_tel[2]%>" maxlength="4" style="ime-mode:disabled;" onKeyPress="return numbersonly(event,false)"/></td>
                  </tr>
                    <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>휴대폰</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"> <select name="p_mobil0" >
                      <option value="010" <% if (v_moblie[0].equals("010")) out.print("selected"); %> >010</option>
                      <option value="011" <% if (v_moblie[0].equals("011")) out.print("selected"); %> >011</option>
                      <option value="016" <% if (v_moblie[0].equals("016")) out.print("selected"); %> >016</option>
                      <option value="017" <% if (v_moblie[0].equals("017")) out.print("selected"); %> >018</option>
                      <option value="018" <% if (v_moblie[0].equals("018")) out.print("selected"); %> >017</option>
                      <option value="019" <% if (v_moblie[0].equals("019")) out.print("selected"); %> >019</option>
                          </select>
                    -
                      <input name="p_mobil1" type="text" class="inp_txt_01"  size="15" value="<%=v_moblie[1]%>" maxlength="4" style="ime-mode:disabled;" onKeyPress="return numbersonly(event,false)"/>
                      -
                      <input name="p_mobil2" type="text" class="inp_txt_01"  size="15" value="<%=v_moblie[2]%>" maxlength="4" style="ime-mode:disabled;" onKeyPress="return numbersonly(event,false)"/></td>
                  </tr>
                  <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>이메일</td>
                    <td height="33" align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px">
                    	<%if(!v_email[0].equals("") && !v_email[1].equals("")){%>
                    	
                    	<input name="p_email1"  class="inp_txt_01" size="30" value="<%=v_email[0]%>"/>
                    	@
                    	<input name="p_email2"  class="inp_txt_01" id="textfield4" size="30" value="<%=v_email[1]%>"/>                    	
                    	<%}else{ %>
                    		이메일 등록을 원할시 운영자에게 연락바랍니다.(02-2161-0077)
                    	<%} %>                    	
                      <!-- 
                      <input name="p_email1" type="text" class="inp_txt_01" size="30" value="<%=v_email[0]%>"/>
                      @
                      <input name="p_email2" type="text" class="inp_txt_01" id="textfield4" size="30" value="<%=v_email[1]%>"/>
                      <select name="p_email20"  onchange="email2change();">
                          <option value="">직접입력</option>
                          <option value="naver.com" >naver.com</option>
                          <option value="chol.com" >chol.com</option>
                          <option value="dreamwiz.com" >dreamwiz.com</option>
                          <option value="empal.com" >empal.com</option>
                          <option value="freechal.com" >freechal.com</option>
                          <option value="gmail.com" >gmail.com</option>
                          <option value="hanafos.com" >hanafos.com</option>
                          <option value="hanmail.net" >hanmail.net</option>
                          <option value="hanmir.com" >hanmir.com</option>
                          <option value="hitel.net" >hitel.net</option>
                          <option value="hotmail.com" >hotmail.com</option>
                          <option value="korea.com" >korea.com</option>
                          <option value="lycos.co.kr" >lycos.co.kr</option>
                          <option value="nate.com" >nate.com</option>
                          <option value="netian.com" >netian.com</option>
                          <option value="paran.com" >paran.com</option>
                          <option value="yahoo.com" >yahoo.com</option>
                          <option value="yahoo.co.kr" >yahoo.co.kr</option>
                     </select>
                      <br />
                      <span class="lh_20 fs_11 ls">* 한국콘텐츠진흥원에서 제공하는 웹진을 비롯 회원정보 수정관련 필요한 것으로 <br />
                      주로 사용하시는 이메일 주소를 입력해 주세요.</span></td>
                       -->
                  </tr>
                    <!-- 
                    <tr>
                      <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>소속기관</td>
                      <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"><input name="p_organization" type="text" class="inp_txt_01" size="40" value="<%=v_work_plcnm%>"/></td>
                    </tr>
                     -->
                    <tr>
                      <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1"> </span>소속부서</td>
                      <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"><input name="p_deptnm" type="text" class="inp_txt_01" size="40" value="<%=v_deptnm%>"/></td>
                    </tr>
                    <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b">정보수신 여부</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px">
                        <input type="checkbox" name="p_agreed_Yn"  onclick="chkYN();" > 서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신 하겠습니다.
                      </td>
                    </tr>
                      <tr style="display:none">
                    <td align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b">관심분야</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding:10px">
                    <table width="100%" border="0" cellspacing="3" cellpadding="0">
                      <tr>
                        <td width="25%"><input type="checkbox" name=""  /> 애니메이션</td>
                        <td width="25%"><input type="checkbox" name="" /> 일반</td>
                        <td width="25%"><input type="checkbox" name=""  /> 유통 및 서비스</td>
                        <td width="25%"><input type="checkbox" name=""  /> 제작지원</td>
                      </tr>
                      <tr>
                        <td><input type="checkbox" name="" /> 만화</td>
                        <td><input type="checkbox" name="" /> 멀티미디어</td>
                        <td><input type="checkbox" name="" /> 융자/투자</td>
                        <td><input type="checkbox" name="" /> 창작지원</td>
                      </tr>
                      <tr>
                        <td><input type="checkbox" name="" /> 게임</td>
                        <td><input type="checkbox" name="" /> 에듀테인먼트</td>
                        <td><input type="checkbox" name="" /> 감성 및 재현</td>
                        <td><input type="checkbox" name="" /> 장비</td>
                      </tr>
                      <tr>
                        <td><input type="checkbox" name="" /> 캐릭터</td>
                        <td><input type="checkbox" name="" /> 인터넷/모바일</td>
                        <td><input type="checkbox" name="" /> 교육/인력양성</td>
                        <td><input type="checkbox" name="" /> 경영공시</td>
                      </tr>
                      <tr>
                        <td><input type="checkbox" name="" /> 영화</td>
                        <td><input type="checkbox" name=""  /> 문화복지</td>
                        <td><input type="checkbox" name=""  /> 국내외 마케팅</td>
                        <td><input type="checkbox" name=""  /> 기타</td>
                      </tr>
                      <tr>
                        <td><input type="checkbox" name="" /> 방송</td>
                        <td><input type="checkbox" name="" /> 문화원형</td>
                        <td><input type="checkbox" name="" /> 기술개발</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td><input type="checkbox" name="" /> 음악/공연</td>
                        <td><input type="checkbox" name=""  /> 문화유산</td>
                        <td><input type="checkbox" name="" /> 기획 및 시나리오</td>
                        <td>&nbsp;</td>
                      </tr>
                    </table>
                    </td>
                    </tr>
                  </table>
                  </td>
             </tr>
              <tr>
                <td height="50" align="right"><a href="javascript:nextOk1();"><img src="/images/asp/membership/btn_ok2.gif" alt="확인" width="72" height="35" /></a>&nbsp;
                    <a href="javascript:history.go(-1);"><img src="/images/asp/membership/btn_cancel.gif" alt="취소" width="72" height="35" /></a></td>
              </tr>

              <tr>
                <td height="50">&nbsp;</td>
              </tr>
            </table></td>
          </tr>
        </table></td>
        <td width="20">&nbsp;</td>
          </tr></table>

        </td>
        </tr>
    </table>
