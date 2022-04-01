<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    DataBox dbox        = (DataBox)request.getAttribute("member");

    String v_name="";
    String v_userid="";
    String v_chk="";
    String v_sw="";
    String v_gubun=box.getString("p_gubun_inja");
    if(dbox != null)
    {
        v_name              = dbox.getString("d_name");
        v_userid            = dbox.getString("d_userid");
        v_chk            = dbox.getString("d_val");  //설문 했는지 여부
        if(!v_chk.equals(""))
        {
            v_sw="1";
            out.print("<script> alert('설문은 한번만 하실수 있습니다. 강제 종료 합니다.');window.close();</script>");
        }
    }
    else
    {
        v_sw="1";
        out.print("<script> alert('회원정보가 없습니다. 강제 종료 합니다.');window.close();</script>");
    }

    if(!v_sw.equals("1"))
    if(1==1)
    {
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<script type='text/javascript' src='http://edu.kocca.or.kr/script/jquery-1.3.2.min.js'></script>
<script type='text/javascript' src='http://edu.kocca.or.kr/script/jquery.form.js'></script>
<title>한국콘텐츠아카데미</title>

<style type="text/css">
<!--
.question {
	font-size: 14px;
	font-weight: bold;
	line-height: 20px;
	color: #ff6600;
}
.subquestion {
	font-size: 12px;
	font-weight: bold;
	line-height: 17px;
	padding-left: 16px
}
.answer {font-size: 14px}

table {
	font-size: 12px;
	line-height: 17px;
}
.style1 {
	font-size: 12px;
	font-weight: bold;
	line-height: 20px;
	color: #ff6600;
}
-->
</style>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
    var sw=false;
    function gotoSubmit()
    {
        var ans="";
        var tmp="";

         // 1
        $("[name='survey1']").each(function(){
            if($(this).attr("checked"))
                tmp+=$(this).val()+"@";
        });
        if(tmp==undefined || tmp=="")
        {
            alert("1번 문제에 답을 하지 않았습니다.");
            return;
        }
        tmp=tmp.substr(0,tmp.length-1);
        ans+=tmp+",";

        tmp="";
        if($.trim($("[name='survey2t']").val())=="")
        {
            alert("2번 문제를 입력 하여 주십시오.");
            $("[name='survey2t']").focus();
            return;
        }
        tmp+=$("[name='survey2t']").val();
        ans+=tmp+",";

        tmp="";
        // 3
        $("[name='survey3']").each(function(){
            if($(this).attr("checked"))
                tmp+=$(this).val()+"@";
        });
        if(tmp==undefined || tmp=="")
        {
            alert("3번 문제에 답을 하지 않았습니다.");
            return;
        }
        tmp=tmp.substr(0,tmp.length-1);
        ans+=tmp+",";

        tmp="";
//        if($.trim($("[name='survey4t']").val())=="")
//        {
//            alert("4번 문제를 입력 하여 주십시오.");
//            $("[name='survey4t']").focus();
//            return;
//        }
        tmp+=$("[name='survey4t']").val();
        ans+=tmp;

        if(!sw)
        {
            $("[name='p_ans']").val(ans);

            document.nform1.submit();
            alert("이벤트 응모가 완료되었습니다\n 감사합니다.");
            window.close();
        }
        sw=true
    }
</script>

</head>

<body >
<form name="nform1" method="post" id="nform1" action="/servlet/controller.community.CommunityRiskServlet">
    <input type = "hidden" name = "p_process" value="eventInsert">
    <input type = "hidden" name = "p_ans" >
    <input type = "hidden" name = "p_userid" value="<%=v_userid%>">
    <input type = "hidden" name = "p_gubun_inja" value="<%=v_gubun%>">
<table width="650" border="0" cellspacing="0" cellpadding="0" style="margin: 0 auto">
<tr>
<td><img src="/tmp/images/survey1.gif" width="650"  /></td>
</tr>
<tr>
<td background="/tmp/images/survey_bg.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td>&nbsp;</td>
<td height="30" class="question">1. 한국콘텐츠진흥원에서는 내년(2011년)에 수강할 수 있는 [방송]관련 과정을 &nbsp;&nbsp;&nbsp;&nbsp;개발할 예정에 있습니다. </td>
<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td width="50">&nbsp;</td>
<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="subquestion">1) 다음 [방송]관련 과정 중에서 관심이 있거나 수강하고 싶은 강좌를 선택해 주시기 <br />
  &nbsp;&nbsp;&nbsp;&nbsp;바랍니다. 설문 결과에 따라 우선 순위를 두어
   개발하여 내년에 서비스 하도록 하겠습<br>&nbsp;&nbsp;&nbsp;&nbsp;니다.<br />
       <span class="style1">* 다음 강좌의 소개를 보고 원하시는 강의를 선택해 주세요.(복수 선택 가능)</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td bgcolor="#FF9900"></td>
              </tr>
</table>
  <table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#CCCCCC">
    <tr>
      <td width="30" align="center" bgcolor="#FFFFFF"><strong>선택</strong></td>
      <td width="120" align="center" bgcolor="#FFFFFF"><strong>강좌명</strong></td>
      <td align="center" bgcolor="#FFFFFF"><strong>강좌내용</strong></td>
      </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="0" /></td>
      <td bgcolor="#FFFFFF">3D 입체영상제작개론</td>
      <td bgcolor="#FFFFFF">3D 입체를 제작하기 위한 입체 문법 등 입문과정</td>
      </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="1" /></td>
      <td bgcolor="#FFFFFF">3D 입체 편집</td>
      <td bgcolor="#FFFFFF">프리미어, 아비드, 파이널컷, 에프트 이펙트를 활용한 입체 편집</td>
      </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="2" /></td>
      <td bgcolor="#FFFFFF">디지털미디어<br />
        워크플로어</td>
      <td bgcolor="#FFFFFF">디지털 환경에 대응할 수 있는 영상 포맷 이해 및 제작 워크플로어</td>
      </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="3" /></td>
      <td bgcolor="#FFFFFF">누크(NUKE)</td>
      <td bgcolor="#FFFFFF">누크를 활용한 VFX 제작 기법</td>
      </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="4" /></td>
      <td bgcolor="#FFFFFF">시모크(SMOKE)</td>
      <td bgcolor="#FFFFFF">스모크를 활용한 VFX 제작 기법</td>
      </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="5" /></td>
      <td bgcolor="#FFFFFF">시네마 4D<br /></td>
      <td bgcolor="#FFFFFF">시네마 4D를 활용한 3D 모션그래픽 활용</td>
      </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="6" /></td>
      <td bgcolor="#FFFFFF">디지털 영상 색보정<br />
(Digital Grading)</td>
      <td bgcolor="#FFFFFF">영상컬러리스트, 디지털 아티스트, 촬영감독 및 비디오 엔지니어를 <br />
        위한 색 보정에 대한 체계적인 이해와 실제적인 응용</td>
      </tr>
  </table></td>
</tr>
</table></td>
<td width="50">&nbsp;</td>
</tr>
<tr>
<td height="30">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td><strong>2) 기타 의견</strong></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
  <tr>
    <td width="30" valign="bottom">&nbsp;</td>
    <td valign="bottom">(
      <input name="survey2t" type="text" id="survey12Text" size="65" />
      )</td>
  </tr>
</table></td>
</tr>
</table></td>
<td>&nbsp;</td>
</tr>
<tr>
<td height="50">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><span class="question">2. 다음 [방송] 관련 과정은 기존에 서비스 되고 있는 강좌입니다. </span></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><table border="0" cellpadding="0" cellspacing="0" width="100%" class="answer">


      <tr>
        <td height="15px"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="a10">
          <tr>
            <td class="table"><p class="subquestion">1) 다음 중에서 개선해야 한다거나 보완해야 한다고 생각하시는 강좌를 선택해주시기<br />
&nbsp;&nbsp;&nbsp;              바랍니다.              (복수  선택 가능)</p></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="a10">
              <tr>
                <td bgcolor="#FF9900"></td>
              </tr>
            </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><strong>선택</strong></td>
                        <td width="111" align="center" style="padding:5px"><strong>강좌명</strong></td>
                        <td width="399" align="center" style="padding:5px"><strong>내용소개</strong></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#666666"></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_1" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey3" type="checkbox" value="0" /></td>
                        <td width="110" style="padding:5px">영상미학</td>
                        <td style="padding:5px">몽타주, 셔레이드, 미장센 등 영상의 시ㆍ공간적 연출기법 및 편집미학 과 관련한 이론 및 영상미학에 대한 실례분석
</td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_1" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey3" type="checkbox" value="1" /></td>
                        <td width="110" style="padding:5px">영상촬영 기본</td>
                        <td style="padding:5px">카메라맨으로서 갖추어야 할 기본 자질과 제작 영역별 지식습득, <br />
                          스튜디오와 ENG 촬영을 위한 하드웨어 이해 및 촬영 기법에 대한 <br />
                          포괄적 이해</td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey3" type="checkbox" value="2" /></td>
                        <td width="110" style="padding:5px">영상제작전문가특강<br />
                          (연출론)</td>
                        <td style="padding:5px">방송영상제작 연출 전문가 특별강좌를 통해 다큐, 드라마, 예능,오락, 영화, 뮤직비디오, 애니메이션 제작 연출론</td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey3" type="checkbox" value="3" /></td>
                        <td width="110" style="padding:5px">영상제작전문가특강<br />
                          (영상론)</td>
                        <td style="padding:5px">디지털환경 변화에 따른 프로덕션, 포스트프로덕션의 카메라, 조명, 미술, 특수촬영, 편집, 음향, 음악 제작 노하우 습득</td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey3" type="checkbox" value="4" /></td>
                        <td width="110" style="padding:5px">미디어교육의 이해</td>
                        <td style="padding:5px">미디어 교육교사로서 필요한 지식 및 실무관련 이론교육</td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey3" type="checkbox" value="5" /></td>
                        <td width="110" style="padding:5px">디지털미디어 <br />
                          스토리텔링</td>
                        <td style="padding:5px">유비쿼터스시대 속 스토리텔링의 발달 개요와 과정 등 기본 데이터 <br />
                          학습과 광고, 테마파크, 게임 등 우리 일상에 있는 스토리텔링의 <br />
                          체계적인 학습을 통해 올바른 디지털 미디어 스토리텔링에 대한 <br />
                          이해 높임</td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey3" type="checkbox" value="6" /></td>
                        <td width="110" style="padding:5px">방송제작실무</td>
                        <td style="padding:5px">방송제작의 기초 이론 및 실례분석을 통한 영상제작의 전반적인 <br />
                          프로세스 습득</td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey3" type="checkbox" value="7" /></td>
                        <td width="110" style="padding:5px">빛의 예술, 방송조명</td>
                        <td style="padding:5px">방송조명과 최신 디지털 영상조명에 맞는 조명기법과 효과 이해하고 <br />
                          기술을 이용한 조명의 예술적 표현이 프로그램에 미치는 영향 <br />
                          이해</td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey3" type="checkbox" value="8" /></td>
                        <td width="110" style="padding:5px">VJ 프로그램제작</td>
                        <td style="padding:5px">방송전문인력 수요에 부응하기 위한 프로그램 기획에서 완성까지의 <br />
                          1인 제작시스템 이해 및 제작능력 배양</td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey3" type="checkbox" value="9" /></td>
                        <td width="110" style="padding:5px">1인 뉴스제작</td>
                        <td style="padding:5px">미디어환경변화에 따른 방송전문인력 수요에 부응하기 위한 1인 <br />
                          미디어개념 및 뉴스제작시스템 이해와 제작능력 배양</td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_3" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey3" type="checkbox" value="10" /></td>
                        <td width="110" style="padding:5px">프로툴즈 음향편집</td>
                        <td style="padding:5px">디지털 음향편집 프로그램 프로툴의 단계별 프로세스 이해</td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_3" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey3" type="checkbox" value="11" /></td>
                        <td width="110" style="padding:5px">어도비 프리미어<br />
                          프로 CS4</td>
                        <td style="padding:5px">Premiere CS4에서 업그레이드 된 Premiere CS5 내용</td>
                      </tr>
                    </table>
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_3" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey3" type="checkbox" value="12" /></td>
                        <td width="110" style="padding:5px">아비드 미디어<br />
                          컴포저 어드밴스</td>
                        <td style="padding:5px">기존 미디어 컴포저 버전에서 업그레이드 된 버전 5.0의 내용</td>
                      </tr>
                    </table>
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_3" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey3" type="checkbox" value="13" /></td>
                        <td width="110" style="padding:5px">애프터 이펙스 CS4</td>
                        <td style="padding:5px">After Effect CS4에서 업그레이드 된 After Effect CS5 내용</td>
                      </tr>
                    </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="a10">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                      </tr>
                  </table></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="15px"></td>
      </tr>
    </table></td>
  </tr>
</table></td>
<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td><strong>2) 기타 의견</strong></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
  <tr>
    <td width="30" valign="bottom">&nbsp;</td>
    <td valign="bottom">(
      <input name="survey4t" type="text" id="survey4tyext1" size="65" />
      )</td>
  </tr>
</table></td>
</tr>
</table></td>
<td>&nbsp;</td>
</tr>
<tr>
<td height="50">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td align="center"><a href="javascript:gotoSubmit();" onfocus="this.blur();"><img src="/tmp/images/survey_but1.gif" width="109" height="30" border="0" /></a></td>
<td>&nbsp;</td>
</tr>
</table></td>
</tr>
<tr>
<td><img src="/tmp/images/survey_bottom.gif" width="650" height="20"/></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
</table>
</form>
</body>
</html>
<%}%>