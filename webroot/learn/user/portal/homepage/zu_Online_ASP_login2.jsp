<%@ page import="com.credu.library.RequestBox" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
%>
<script type="text/javascript">
    var idExists=false;
    var emailExists=false;
    function nextOk()
    {
    	var r0=$("[name='p_kor_name']").val();
    	if(r0=="")
    	{
			alert("이름을 입력하여 주십시오.");
			$("[name='p_name']").focus();
			return;
    	}    	

    	var b1=$("[name='p_birth_year']").val();    	        
    	var b2=$("[name='p_birth_month']").val();
    	var b3=$("[name='p_birth_day']").val();
    	if(b1=="" || b1.length < 4 || b2=="" || b2.length < 2 || b3=="" || b3.length < 2)
    	{
			alert("생년월일을 입력해주십시오.");
			return;
    	}

    	var s=$('input[name="p_sex"]:checked').val();
    	if(!$("[name]='p_sex'").is(':checked')) {
    		alert("성별을 선택하십시오.");
    		return;
    	}
    	
        if(!idExists)
        {
            alert("아이디 중복확인을 하시기 바랍니다.");
            return;
        }

        var r=$("[name='p_pw1']").val();
        if(r=="")
        {
            alert("암호를 입력하여 주십시오.");
            $("[name='p_pw1']").focus();
            return;
        }

        if(r.length<8)
        {
            alert("암호는 8자리 이상이어야 합니다.");
            $("[name='p_pw1']").focus();
            return;
        }

        var r1=$("[name='p_pw2']").val();
        if(r1=="")
        {
            alert("암호를 입력하여 주십시오.");
            $("[name='p_pw2']").focus();
            return;
        }

        if(r1.length<8)
        {
            alert("암호는 8자리 이상이어야 합니다.");
            $("[name='p_pw2']").focus();
            return;
        }

        if(r!=r1)
        {
            alert("암호가 일치 하지 않습니다.");
            return;
        }

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
            alert("휴대폰의 국번을 입력하여 주십시오.");
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

        if(!emailExists)
        {
            alert("이메일 중복확인을 하시기 바랍니다.");
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
        document.form1.p_process.value = "ASP_Login_Insert";
        document.form1.menuid.value = "0";
        document.form1.gubun.value = "4";
        document.form1.action = "/servlet/controller.homepage.MainServlet";
        document.form1.submit();
    }
    
    function email2change()
    {
        var r=$("[name='p_email20']").val();
        $("[name='p_email2']").val(r);
    }

    function existsId()
    {
        var r=$.trim($("[name='p_id']").val());
        if(r=="")
        {
            alert("아이디를 입력하여 주십시오.");
            return;
        }

        if(r.length<6 || r.length>16)
        {
            alert("아이디는 총 6~16자로만 조합이 되어야 합니다.");
            return;
        }

        var fm=document.form1;
    	var chk_num = fm.p_id.value.search(/[0-9]/g);
        var chk_lower = fm.p_id.value.search(/[a-z]/ig);
        if (!/^[a-z0-9]{6,16}$/.test(fm.p_id.value) ||  (chk_num < 0 || chk_lower< 0) ){
            alert("아이디는 영문 소문자, 숫자를 혼합하여 6-16자리로 입력해 주세요.");
            fm.p_id.value;
            fm.p_id.focus();
            return;
        }

        document.form1.p_process.value = "ASP_Login";
        document.form1.menuid.value = "0";
        document.form1.gubun.value = "20";

        $("[name='form1']").ajaxSubmit({
            url:'/servlet/controller.homepage.MainServlet',
           type:'post',
            data:{userId:r},
           success: function(data){
               var r1=$.trim(data);
               $("#div_Area").text("");
               idExists=false;
               if(r1=="0")
               {
                   $("#div_Area").css("color","#FF0000");      //   ID 가 존재
                   $("#div_Area").text("사용할수 없는 아이디 입니다.");
               }
               else
               {
                   idExists=true;
                   $("#div_Area").css("color","#0000FF");      //   ID 없음
                   $("#div_Area").text("사용할수 있는 아이디 입니다.");
               }
            }
        });
    }

    function existsEmail()
    {
        var r=$.trim($("[name='p_email1']").val());
        var r2=$.trim($("[name='p_email2']").val());
        var r3= r+"@"+r2;
        if(r=="" || r2=="")
        {
            alert("이메일을 입력하여 주십시오.");
            return;
        }        

        document.form1.p_process.value = "ASP_Login";
        document.form1.menuid.value = "0";
        document.form1.gubun.value = "25";

        $("[name='form1']").ajaxSubmit({
            url:'/servlet/controller.homepage.MainServlet',
           type:'post',
            data:{userEmail:r3},
           success: function(data){
               var r1=$.trim(data);
               $("#div_Area").text("");
               emailExists=false;
               if(r1=="0")
               {
                   $("#div_Area2").css("color","#FF0000");      //   ID 가 존재
                   $("#div_Area2").text("사용할수 없는 이메일 입니다.");
               }
               else
               {
                   emailExists=true;
                   $("#div_Area2").css("color","#0000FF");      //   ID 없음
                   $("#div_Area2").text("사용할수 있는 이메일 입니다.");
               }
            }
        });
    }

    function chkYN()
    {
        var r=$("[name='p_agreed']").val();
        if(r=="Y")
            $("[name='p_agreed']").val("N");
        else
            $("[name='p_agreed']").val("Y");
    }

    function onlyHangul()
    {
        if(((event.keyCode<12592)||(event.keyCode>12687))) {
            alert("한글만 입력가능합니다");            
            event.returnValue=false;
        }else{
			event.returnValue=true;
        }
    }

    function onlyNumber()
    {
    	if((event.keyCode<48)||(event.keyCode>57))
 		{
			alert("숫자만 입력 가능합니다.");
			event.returnValue=false;	
        }else{
        	event.returnValue=true;
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
                <td><img src="/images/asp/membership/leftm1_o.gif" width="230" height="41" /></td>
              </tr>
              <tr>
                <td><a href="javascript:menuForwardLoginAndPwFind('10')" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image25','','/images/asp/membership/leftm2_o.gif',1)"><img src="/images/asp/membership/leftm2_a.gif" name="Image25" width="230" border="0" id="Image25" /></a></td>
              </tr>
            </table></td>
            <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="38" align="left" valign="top"><img src="/images/asp/membership/title_1.gif" width="84" height="31" /></td>
              </tr>
              <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <!--<td><img src="/images/asp/membership/join1_a.gif" width="178" height="91" /></td>-->
                    <td><img src="/images/asp/membership/join2_a.gif" width="245" height="91" /></td>
                    <td><img src="/images/asp/membership/join3_o.gif" width="245" height="91" /></td>
                    <td><img src="/images/asp/membership/join4_a.gif" width="199" height="91" /></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td><img src="/images/asp/membership/stitle_4.gif" width="690" height="24" /></td>
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
                    <td width="130" height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>성명(한글)</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px">
                    	<input type="text" class="inp_txt_01" name="p_kor_name" onkeypress="onlyHangul();"/>
                    	<!--
                    	<%=box.getSession("name")%>
                        <input type="hidden" name="p_name" value="<%=box.getSession("name")%>">
                        <input type="hidden" name="p_resno" value="<%=box.getSession("resno")%>">
                        -->
                    </td>
                  </tr>
                  <tr>
                    <td width="130" height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>생년월일</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px">
                    	<input type="text" class="inp_txt_01" name="p_birth_year" size="5" maxlength="4" onkeypress="onlyNumber();" style="IME-MODE: disabled"/>년                    	
                    	<input type="text" class="inp_txt_01" name="p_birth_month" size="2" maxlength="2" onkeypress="onlyNumber();" style="IME-MODE: disabled"/>월
                    	<input type="text" class="inp_txt_01" name="p_birth_day" size="2" maxlength="2" onkeypress="onlyNumber();" style="IME-MODE: disabled"/>일
                    	 &nbsp;예 (1972년 01월01일)
                    </td>
                  </tr>
                  <tr>
                    <td width="130" height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>성별</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px">
                    	<input name="p_sex" type="radio" value="1" id="p_sex"  />남자 
					 	<input name="p_sex" type="radio" value="2" id="p_sex"  />여자
                    </td>
                  </tr>
                  <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>아이디</td>
                    <td height="45" align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"><p>
                      <input name="p_id" type="text" class="inp_txt_01" maxlength="16" size="40" onkeypress="idExists=false;"/>
                      <a href="javascript:existsId();"><img src="/images/asp/membership/btn_idcheck.gif" alt="중복확인" width="49" height="17" align="absmiddle" /></a><span id="div_Area" style="width:200px;margin-left:10px"></span></p>
                      <p class="lh_20 fs_11 ls">* 아이디는 6~16자의 영문, 숫자의 조합으로 만들 수 있습니다.</p></td>
                  </tr>
                   <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>비밀번호</td>
                    <td height="45" align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"><input name="p_pw1" type="password" class="inp_txt_01"  size="40"/>
                      <br />
                      <span class="lh_20 fs_11 ls">* 8자 이상의 숫자 혹은 영문으로 입력해주세요. 연속된 숫자, 생일, 주민번호 등 알기쉬운 숫자는 피해주세요.</span></td>
                  </tr>
                  <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>비밀번호 확인</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"><input name="p_pw2" type="password" class="inp_txt_01" size="40" /></td>
                  </tr>
                  <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>전화번호</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px">
                    	<select name="p_tel0" >
	                      <option value="02">02</option>
		                  <option value="031">031</option>
		                  <option value="032">032</option>
		                  <option value="033">033</option>
		                  <option value="041">041</option>
		                  <option value="042">042</option>
		                  <option value="043">043</option>
		                  <option value="044">044</option>
		                  <option value="051">051</option>
		                  <option value="052">052</option>
		                  <option value="053">053</option>  
		                  <option value="054">054</option>
		                  <option value="055">055</option>  
		                  <option value="061">061</option>  
		                  <option value="062">062</option>
		                  <option value="063">063</option>  
		                  <option value="064">064</option>
		                  <option value="070">070</option>  
                        </select>
                    -
                      <input name="p_tel1" type="text" maxlength="4" class="inp_txt_01"  size="15" onkeypress="onlyNumber();" style="IME-MODE: disabled"/>
                      -
                      <input name="p_tel2" type="text" maxlength="4"class="inp_txt_01"  size="15" onkeypress="onlyNumber();" style="IME-MODE: disabled"/></td>
                  </tr>
                    <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>휴대폰</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"> <select name="p_mobil0" >
                      <option>010</option>
                      <option>011</option>
                      <option>016</option>
                      <option>018</option>
                      <option>017</option>
                      <option>019</option>
                          </select>
                    -
                      <input name="p_mobil1" type="text" maxlength="4" class="inp_txt_01"  size="15" onkeypress="onlyNumber();" style="IME-MODE: disabled"/>
                      -
                      <input name="p_mobil2" type="text" maxlength="4" class="inp_txt_01"  size="15" onkeypress="onlyNumber();" style="IME-MODE: disabled"/></td>
                  </tr>
                  <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>이메일</td>
                    <td height="65" align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"><input name="p_email1" type="text" class="inp_txt_01" size="30" onkeypress="emailExists=false;"/>
                      @
                      <input name="p_email2" type="text" class="inp_txt_01" id="textfield4" size="30" />
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
                      <span class="lh_20 fs_11 ls"><a href="javascript:existsEmail();"><img src="/images/asp/membership/btn_idcheck.gif" alt="중복확인" width="49" height="17" align="absmiddle" /></a><span id="div_Area2" style="width:200px;margin-left:10px"></span><br/>
                      * 한국콘텐츠진흥원에서 제공하는 웹진을 비롯 회원정보 수정관련 필요한 것으로 <br />
                      주로 사용하시는 이메일 주소를 입력해 주세요.</span></td>
                  </tr>
                    <tr>
                    <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b">정보수신 여부</td>
                    <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px">
                        <input type="checkbox" name="p_agreed"  value="N" onclick="chkYN();" /> 서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신 하겠습니다.
                      </td>
                    </tr>
                    <!--
                    <tr>
                      <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b"><span class="fc_red1">* </span>소속기관</td>
                      <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"><input name="p_organization" type="text" class="inp_txt_01" size="40" /></td>
                    </tr>
                    -->
                    <tr>
                      <td height="33" align="left" bgcolor="#f5f5f5" style="border-bottom:1px solid #c6cfd9;border-right:1px solid #c6cfd9;padding-left:10px" class="b">소속부서</td>
                      <td align="left" style="border-bottom:1px solid #c6cfd9;padding-left:10px"><input name="p_deptnm" type="text" class="inp_txt_01" size="40" /></td>
                    </tr>
                      <tr style="display: none;">
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
                <td height="50" align="right"><a href="javascript:nextOk()"><img src="/images/asp/membership/btn_ok2.gif" alt="확인" width="72" height="35" /></a>&nbsp;<a href="javascript:history.go(-1);"><img src="/images/asp/membership/btn_cancel.gif" alt="취소" width="72" height="35" /></a></td>
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
