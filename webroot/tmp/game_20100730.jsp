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
<td><img src="/tmp/images/survey_img10.gif" width="650" height="130" /></td>
</tr>
<tr>
<td background="/tmp/images/survey_bg.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td>&nbsp;</td>
<td height="30" class="question">1. 한국콘텐츠진흥원에서는 내년(2011년)에 수강할 수 있는 [게임]관련 과정을 &nbsp;&nbsp;&nbsp;&nbsp;개발할 예정에 있습니다. </td>
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
<td class="subquestion">1) 다음 [게임]관련 과정 중에서 관심이 있거나 수강하고 싶은 강좌를 선택해 주시기 <br />&nbsp;&nbsp;&nbsp;&nbsp;바랍니다. 설문 결과에 따라 우선 순위를 두어
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
      <td width="89" align="center" bgcolor="#FFFFFF"><strong>강좌명</strong></td>
      <td width="139" align="center" bgcolor="#FFFFFF"><strong>강좌내용</strong></td>
      <td width="255" align="center" bgcolor="#FFFFFF"><strong>취지</strong></td>
    </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="0" /></td>
      <td bgcolor="#FFFFFF">아이폰, 아이패드 용 게임 제작</td>
      <td bgcolor="#FFFFFF">아이폰, 아이패드 용 게임 개발 환경의 이해 및 최신 OS(4)에 기반한 게임 제작법</td>
      <td rowspan="2" bgcolor="#FFFFFF">아이폰, 안드로이드폰을 기반으로 한 스마트폰 게임이  새로운 수익을 창출하는 시장으로
        성장하게 되었고 경쟁력 있는 개인 개발자가
        기업과 경쟁할 수 있는 환경이 조성되었다. <br />
        이러한 게임개발 환경의 변화에 부응하여 국내 개발자의 경쟁력을 높여 세계 게임산업계로의 진출 및 경쟁할 수  있도록 게임 개발 지식을 
        온라인 콘텐츠로 제공하려 한다. </td>
    </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="1" /></td>
      <td bgcolor="#FFFFFF">안드로이드폰용 게임 제작</td>
      <td bgcolor="#FFFFFF">안드로이드폰용 게임 개발 환경 이해 및 최신 OS(2.2)에 기반한 게임 제작법</td>
      </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="2" /></td>
      <td bgcolor="#FFFFFF">소셜 네트워크 게임(SNG)<br />
        기획과 개발</td>
      <td bgcolor="#FFFFFF">소셜 네트워크 게임 개발 특성에 따른 기획 접근
        방법과 싸이월드, 다음, 네이버 등 다양한
        플랫폼에 기반한 소셜
        네트워크 게임 제작법</td>
      <td bgcolor="#FFFFFF">최근 세계적으로 소셜 네트워크 게임이 크게
        인기를 얻고 있는 상황에서 많은 게임개발사가 새로운  시장에 앞다투어 진입하고 있다. 또한 국내 게임 산업계에서는 개발 경험자가 거의
        없는 상황에서도 신속하게  대응하고 있다. <br />
        하지만 소셜 네트워크 게임 개발에 대한
        노하우가 공개되지 않고 있어 시장에 신규로
        진입하고자  하는 개발자는 어려움을 겪을
        수밖에 없는 현실이다. 이러한 게임산업의 지도 변화에 부응하여 기획 및  접근 방법과 구현
        기술에 대한 노하우를 온라인 콘텐츠로
        제공하려 한다. </td>
    </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="3" /></td>
      <td bgcolor="#FFFFFF">게임 기획 및 그래픽 실무자를 위한 게임 기술 이해</td>
      <td bgcolor="#FFFFFF">복잡한 게임 기술을 게임 기획자와 그래픽
        실무자가 이해하기 쉽도록 개념적 설명을 통한 게임 기술의  이해</td>
      <td bgcolor="#FFFFFF">기획자와 그래픽 실무자는 복잡한 게임 기술에 대해 충분히 이해하고 게임개발에 접근해야
        하지만  프로그래밍 언어를 알아야 한다는
        진입장벽으로 인해 그렇지 못한 경우가 많다. 물론 프로그래밍 실습을  통하는 것이 기술을
        이해하는데 보다 확실한 방법이긴 하지만
        반드시 그래야 하는 것은 아니다. 그래서
        프로그래밍 비전공자의 기술 이해에 대한
        진입장벽을 낮추기 위해 게임 기술의 개념적
        설명을 온라인 콘텐츠로 제공하려 한다. </td>
    </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="4" /></td>
      <td bgcolor="#FFFFFF">UDK 활용 방법</td>
      <td bgcolor="#FFFFFF">UDK를 활용하여
        스크립트와 툴 사용법을 통한 게임 제작 방법</td>
      <td bgcolor="#FFFFFF">세계에서 가장 유명한 상업용 게임엔진 중
        하나인 언리얼 엔진의 개발 툴킷인 UDK가
        무료로 배포됨에 따라 전 세계 개발사 및 교육기관에서 수요가 급증하고 있다. 하지만 이러한 UDK의 사용법에 대한 노하우가 수요에 비해
        크게  공개되지 않고 있는 상황에서 국내 개발자의 개발 능력 향상을 위해 UDK를 통해 게임을 제작할 수 있는  노하우를 온라인
        콘텐츠로 제공하려 한다. </td>
    </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="5" /></td>
      <td bgcolor="#FFFFFF">게임에 대한 <br />
        전문적 분석 <br />
        방법</td>
      <td bgcolor="#FFFFFF">쉐이더 프로그래머, 레벨디자이너 등 국내 각 분야 전문가들이 해당 분야의 게임 요소를 분석하는
        노하우 및 기술 소개</td>
      <td bgcolor="#FFFFFF">레벨 디자이너 등 각 분야 전문가들은 게임을 개발할 때 기존 게임을 참고하여 발전시켜
        게임을  개발하곤 한다. 이러한 각 분야
        전문가들은 초보자와 다르게 해당 분야의 개발 동향, 구현 난이도 및 완성도, 기술의 발전
        방향을 종합적으로 분석할 수  있는 능력이
        있다. 이러한 각 분야 전문가들의 분석 노하우를 공개하여 게임을 보다 정밀하게 분석하는
        방법을 온라인 콘텐츠로 제공하려 한다.</td>
    </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="6" /></td>
      <td bgcolor="#FFFFFF">게임 심리학</td>
      <td bgcolor="#FFFFFF">게임을 개발할 때
        고려해야 하는 심리학적 이론을 통해 게임을
        디자인 하는 심리학적 방법 소개</td>
      <td bgcolor="#FFFFFF">대부분의 게임 개발자는 플레이어의 심리적
        반응을 예측하여 게임을 디자인한다. 게임
        플레이어의 심리 반응과 현상에 대한 심리학적 연구는 활발하게 진행 중이지만 명확하게
        밝혀진 이론은 드문  편이다. 만약 게임
        개발자가 게임 플레이어의 심리 반응을 예측하는 이론적 근거를 알고 있다면 한층 재미있는  게임을 개발할 수 있다고 판단하여 이러한 게임개발과 관련된 심리학적 고찰을
        온라인 콘텐츠로 제공하려 한다.</td>
    </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input name="survey1" type="checkbox" value="7" /></td>
      <td bgcolor="#FFFFFF">GFX를 통한인터페이스 제작방법</td>
      <td bgcolor="#FFFFFF">GFX를 통해 인터페이스를 제작하는 방법 소개</td>
      <td bgcolor="#FFFFFF">GFX는 어도비 플래시와 완벽하게 연동된다는 장점으로 전 세계적으로 가장 유명한
        인터페이스 제작 미들웨어  중 하나이다. 특히 플래시의 액션 스크립트만으로 인터페이스를 개발할 수 있어 국내에서도 많은 게임 개발사가 GFX를 도입했다. 이러한 GFX에  대한 개발 노하우를 제공하기 위해 온라인 콘텐츠를
        개발하려 한다.</td>
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
    <td><span class="question">2. 다음 [게임] 관련 과정은 기존에 서비스 되고 있는 강좌입니다. </span></td>
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
                        <td width="110" style="padding:5px">아이디어 발상</td>
                        <td style="padding:5px">창작과 아이디어 개발능력, 게임 및 엔터테인먼트 사업기획서<br>작성능력, 프리젠테이션 능력과 사고의  전환과 발상 방법</td>
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
                        <td width="110" style="padding:5px">3D Max 이해하기</td>
                        <td style="padding:5px">3D Max의 구성요소와 주요 기능들에 대한 이해를 바탕으로<br>기초적인  기능 사용 방법</td>
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
                        <td width="110" style="padding:5px">3D 그래픽 제작 <br />
                          실무</td>
                        <td style="padding:5px">3D Max를 통해 3D 그래픽  제작과정과 주요 포인트 소개</td>
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
                        <td width="110" style="padding:5px">3D 프로그래밍1</td>
                        <td style="padding:5px">3D 프로그래밍을 하기 위한 기초용어와 수학에 대한 소개</td>
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
                        <td width="110" style="padding:5px">게임수학</td>
                        <td style="padding:5px">콘텐츠와 문화원형의 개념이 결합된 문화원형콘텐츠에 대한 개념적 연구</td>
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
                        <td width="110" style="padding:5px">DiretX1</td>
                        <td style="padding:5px">컴퓨터 내부에 수를 표현하는 방법과 처리 방법, 개발에 많이<br>사용되는 도형과 선형대수에 대한 이해</td>
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
                        <td width="110" style="padding:5px">DiretX2</td>
                        <td style="padding:5px">슈팅게임을 제작할 수 있는 DiretX의 기본개념과 2D 게임그래픽 기법</td>
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
                        <td width="110" style="padding:5px">게임 디자인1</td>
                        <td style="padding:5px">Direct3D에 관한 기본적인 내용과 2D 그래픽 표현방법을 통해<br>액션게임  제작</td>
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
                        <td width="110" style="padding:5px">게임 캐릭터 <br />
                          디자인1</td>
                        <td style="padding:5px">세부적인 게임의 시스템과 요소들을 디자인하는  방법을 통해 실제<br>작업에 응용</td>
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
                        <td width="110" style="padding:5px">게임툴프로그래밍</td>
                        <td style="padding:5px">캐릭터와 캐릭터디자인의 개념과 캐릭터디자인을 각 종 매체에 적용 <br />
                          방법</td>
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
                        <td width="110" style="padding:5px">MOD 게임제작 <br />
                          및 활용</td>
                        <td style="padding:5px">스프라이트 툴을 제작하면서 대상과 요구사항을 분석 정의 및 제작한 <br />
                          툴을 통해 간단한 게임 제작</td>
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
                        <td width="110" style="padding:5px">문화비평</td>
                        <td style="padding:5px">MOD 게임 정의, MOD 게임이  가져야 할 주요 요소, 제작과정<br>사례 등 MOD 게임에 대한  기초지식 소개</td>
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