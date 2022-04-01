<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>

<link rel="stylesheet" href="/css/asp_type1/class.css" type="text/css" />
<script language="javascript" src="/script/flash_TagScript.js"></script>

<script type="text/javascript">
    function existsID()
    {
        document.form1.p_process.value = "ASP_Login";
        document.form1.menuid.value = "0";
        document.form1.gubun.value = "2";
        document.form1.action = "/servlet/controller.homepage.MainServlet";
        document.form1.submit();
    }

    function fnmember()
    {
        var id=$.trim($("[name='p_name']").val());
        if(id=="")
        {
            alert("이름을 입력하여 주십시오.");
            $("[name='p_name']").focus();
            return;
        }

        var res1=$.trim($("[name='p_resno1']").val());
        var res2=$.trim($("[name='p_resno2']").val());
        if(!regcheck(res1+res2))
        {
            $("[name='p_resno1']").val("");
            $("[name='p_resno2']").val("");
            $("[name='p_resno1']").focus();
            return;
        }
        if(res1+res2=="")
        {
            alert("주빈등록번호를 입력하여 주십시오.");
            $("[name='res1']").focus();
            return;
        }

        document.form1.p_process.value = "ASP_Login";
        document.form1.menuid.value = "0";
        document.form1.gubun.value = "110";

        $("[name='form1']").ajaxSubmit({
            url:'/servlet/controller.homepage.MainServlet',
            contentType : "application/x-www-form-urlencoded;charset=UTF-8",
           type:'post',
            data:{},
           success: function(data){
               var r1=$.trim(data);
               if(r1=="1")
               {
                   $("#div_Area").text("");
                   $("#div_Area").css("color","#FF0000");      //   ID 가 존재
                   $("#div_Area").text("이미 가입된 회원 입니다.");
               }
               else
               {
                   existsID();
               }
            }
        });
    }
</script>


<table width="100%" border="0" cellspacing="0" cellpadding="0">
       <tr>
    <td><table width="960" border="0" cellpadding="0" cellspacing="0">
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
                    <td><img src="/images/asp/membership/join1_o.gif" width="178" height="91" /></td>
                    <td><img src="/images/asp/membership/join2_a.gif" width="177" height="91" /></td>
                    <td><img src="/images/asp/membership/join3_a.gif" width="176" height="91" /></td>
                    <td><img src="/images/asp/membership/join4_a.gif" width="159" height="91" /></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td><img src="/images/asp/membership/stitle_1.gif" width="690" height="24" /></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td><table width="482" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><img src="/images/asp/membership/joinimg_01.gif" alt="실명확인"  width="482" height="55" /></td>
                  </tr>
                  <tr >
                    <td height="63" background="/images/asp/membership/joinimg_02.gif" id="div_Area">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>&nbsp;</td>
                        <td width="350"><table border="0" cellspacing="5" cellpadding="0">
                          <tr>
                            <td align="right">이름</td>
                            <td align="left"><input name="p_name" type="text" class="inp_txt_01" id="textfield" size="40" />
                            </td>
                          </tr>
                          <tr>
                            <td width="80" align="right">주민등록번호</td>
                            <td align="left"><input name="p_resno1" type="text" class="inp_txt_01" id="textfield2" size="18" maxlength="6"/>
                                -
                                <input name="p_resno2" type="password" class="inp_txt_01" id="textfield3" size="18" maxlength="7"/>
                                <input name="p_toppwd" type="hidden" class="inp_txt_01"  size="18" />
                                </td>
                          </tr>
                        </table></td>
                        <td width="73"><a href="javascript:fnmember()"><img src="/images/asp/membership/btn_ok.gif" alt="확인" width="73" height="49" /></a></td>
                        <td>&nbsp;</td>
                      </tr>
                    </table>
                    </td>
                  </tr>
                  <tr>
                    <td><img src="/images/asp/membership/joinimg_03.gif" width="482" height="80" /></td>
                  </tr>
                </table></td>
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
      </td>
  </tr>
</table>