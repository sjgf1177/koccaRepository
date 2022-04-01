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
<title>한국콘텐츠아카데미</title>
<script type='text/javascript' src='http://edu.kocca.or.kr/script/jquery-1.3.2.min.js'></script>
<script type='text/javascript' src='http://edu.kocca.or.kr/script/jquery.form.js'></script>

<style type="text/css">
<!--
.question {
	font-size: 14px;
	font-weight: bold;
}
.answer {font-size: 14px}

.table {
	font-size: 12px
}
.style1 {
	font-size: 13px;
	font-weight: bold;
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

        tmp=$("[name='survey1']:checked").val();
        if(tmp==undefined)
        {
            alert("1번 문제에 답을 하지 않았습니다.");
            return;
        }
        ans=tmp+",";

        // 222
        tmp=$("[name='survey2']:checked").val();
        if(tmp==undefined)
        {
            alert("2번 문제에 답을 하지 않았습니다.");
            return;
        }
        if(tmp=="3")
        {
            tmp=$("[name='survey2t']").val();
            if($.trim(tmp)=="")
            {
                alert("2번 문제에서 기타를 선택하시면 직업군을 직접 입력 하여 주십시오.");
                $("[name='survey2t']").focus();
                return;
            }
        }
        ans+=tmp+",";

        // 333
        tmp=$("[name='survey3']:checked").val();
        if(tmp==undefined)
        {
            alert("3번 문제에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

         // 444
        tmp=$("[name='survey4']:checked").val();
        if(tmp==undefined)
        {
            alert("4번 문제에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        // 555
        tmp=$("[name='survey5t1']").val()+"@";
        tmp+=$("[name='survey5t2']").val()+"@";
        tmp+=$("[name='survey5t3']").val();
        ans+=tmp+",";

         // 666
        tmp=$("[name='survey6']:checked").val();
        if(tmp==undefined)
        {
            alert("6번 문제에 답을 하지 않았습니다.");
            return;
        }
        if(tmp=="4")
        {
            tmp=$("[name='survey6t']").val();
            if($.trim(tmp)=="")
            {
                alert("6번 문제에서 기타를 선택하시면 직업군을 직접 입력 하여 주십시오.");
                $("[name='survey6t']").focus();
                return;
            }
        }
        ans+=tmp+",";

        // 777
        tmp=$("[name='survey7']:checked").val();
        if(tmp==undefined)
        {
            alert("7번 문제에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp="";
         // 888
        $("[name='survey8']").each(function(){
            if($(this).attr("checked"))
                tmp+=$(this).val()+"@";
        });
        if(tmp==undefined || tmp=="")
        {
            alert("8번 문제에 답을 하지 않았습니다.");
            return;
        }
        if(tmp.indexOf("3")!=-1)
        {
            if($.trim($("[name='survey8t']").val())=="")
            {
                alert("8번 문제에서 기타를 선택하시면 직업군을 직접 입력 하여 주십시오.");
                $("[name='survey8t']").focus();
                return;
            }
            tmp+=$("[name='survey8t']").val()+"@";
        }
        tmp=tmp.substr(0,tmp.length-1);
        ans+=tmp+",";

        tmp="";
        // 999
        $("[name='survey9']").each(function(){
            if($(this).attr("checked"))
                tmp+=$(this).val()+"@";
        });
        if(tmp==undefined || tmp=="")
        {
            alert("9번 문제에 답을 하지 않았습니다.");
            return;
        }
        if(tmp.indexOf("3")!=-1)
        {
            if($.trim($("[name='survey9t']").val())=="")
            {
                alert("9번 문제에서 기타를 선택하시면 직업군을 직접 입력 하여 주십시오.");
                $("[name='survey9t']").focus();
                return;
            }
            tmp+=$("[name='survey9t']").val()+"@";
        }
        tmp=tmp.substr(0,tmp.length-1);
        ans+=tmp+",";

        tmp="";
        // 10
        $("[name='survey10']").each(function(){
            if($(this).attr("checked"))
                tmp+=$(this).val()+"@";
        });
        if(tmp==undefined || tmp=="")
        {
            alert("10번 문제에 답을 하지 않았습니다.");
            return;
        }
        tmp=tmp.substr(0,tmp.length-1);
        ans+=tmp+",";

        tmp="";
        // 11
        $("[name='survey11']").each(function(){
            if($(this).attr("checked"))
                tmp+=$(this).val()+"@";
        });
        if(tmp==undefined || tmp=="")
        {
            alert("11번 문제에 답을 하지 않았습니다.");
            return;
        }
        tmp=tmp.substr(0,tmp.length-1);
        ans+=tmp+",";

        tmp="";
        // 12
        $("[name='survey12']").each(function(){
            if($(this).attr("checked"))
                tmp+=$(this).val()+"@";
        });
        if(tmp==undefined || tmp=="")
        {
            alert("12번 문제에 답을 하지 않았습니다.");
            return;
        }
        tmp=tmp.substr(0,tmp.length-1);
        ans+=tmp+",";

        tmp="";
        // 13
        $("[name='survey13']").each(function(){
            if($(this).attr("checked"))
                tmp+=$(this).val()+"@";
        });
        if(tmp==undefined || tmp=="")
        {
            alert("13번 문제에 답을 하지 않았습니다.");
            return;
        }
        tmp=tmp.substr(0,tmp.length-1);
        ans+=tmp+",";

        tmp=$("[name='survey14t']").val();
        if(tmp=="")
        {
            alert("14번 문제에 답을 하지 않았습니다.");
            $("[name='survey14t']").focus();
            return;
        }
        ans+=tmp+",";

        if(!sw)
        {
            tmp=$("[name='survey15t']").val();
            if(tmp=="")
            {
                alert("15번 문제에 답을 하지 않았습니다.");
                $("[name='survey15t']").focus();
                return;
            }
            ans+=tmp;

            $("[name='p_ans']").val(ans);

            document.nform1.submit();
            alert("이벤트 응모가 완료되었습니다\n 감사합니다.");
            window.close();
        }
        sw=true
    }

    function showHide(tbl,classnm)
    {
        $(tbl).show();
        $(tbl).find("table").hide();
        $(".a10").show();
        $(classnm).show();
    }

    function hideShow1(tbl)
    {
        var r=$("#"+tbl).css("display");
        if(r=="none")
            $("#"+tbl).css("display","block");
        else
            $("#"+tbl).css("display","none");
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
<td><img src="/tmp/images/survey_img1.gif" width="650" height="130" /></td>
</tr>
<tr>
<td background="/tmp/images/survey_bg.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td width="50">&nbsp;</td>
<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td><span class="question">1) 교육생님의 연령대는?</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
<tr>
<td width="30" valign="bottom">&nbsp;</td>
<td width="20" valign="bottom"><input name="survey1" type="radio" value="0" /></td>
<td valign="bottom">① 20대</td>
<td width="20" valign="bottom"><input name="survey1" type="radio" value="1" /></td>
<td valign="bottom">② 30대</td>
<td width="20" valign="bottom"><input name="survey1" type="radio" value="2" /></td>
<td valign="bottom">③ 40대</td>
<td width="20" valign="bottom"><input name="survey1" type="radio" value="3" /></td>
<td width="60" valign="bottom">④ 기타</td>
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
<td><span class="question">2) 교육생님의 직업군은?</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
<tr>
<td width="30" valign="bottom">&nbsp;</td>
<td width="20"><input name="survey2" type="radio" value="0" /></td>
<td>① 방송관련 직군</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td><input name="survey2" type="radio" value="1" /></td>
<td>② 문화관련 직군</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td><input name="survey2" type="radio" value="2" /></td>
<td>③ 게임관련 직군</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td><input name="survey2" type="radio" value="3" /></td>
<td>④ 기타 (
<input name="survey2t" type="text" size="50" />
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
<td><span class="question">3) 교육생님의 관심 분야는?</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
<tr>
<td width="30" valign="bottom">&nbsp;</td>
<td width="20" valign="bottom"><input name="survey3" type="radio" value="0" /></td>
<td valign="bottom">① 방송</td>
<td width="20" valign="bottom"><input name="survey3" type="radio" value="1" /></td>
<td valign="bottom">② 문화</td>
<td width="20" valign="bottom"><input name="survey3" type="radio" value="2" /></td>
<td valign="bottom">③ 게임</td>
<td width="20" valign="bottom"><input name="survey3" type="radio" value="3" /></td>
<td width="60" valign="bottom">④ 기타</td>
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
<td><span class="question">4) 사이버콘텐츠아카데미에서 수강한 과목수는?</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
<tr>
<td width="30" valign="bottom">&nbsp;</td>
<td width="20" valign="bottom"><input name="survey4" type="radio" value="0" /></td>
<td valign="bottom">① 1~2과목</td>
<td width="20" valign="bottom"><input name="survey4" type="radio" value="1" /></td>
<td valign="bottom">② 3~5과목</td>
<td width="20" valign="bottom"><input name="survey4" type="radio" value="2" /></td>
<td valign="bottom">③ 6과목 이상</td>
<td width="20" valign="bottom"><input name="survey4" type="radio" value="3" /></td>
<td width="60" valign="bottom">④ 없음</td>
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
<td><span class="question">5) 교육생님이 본원에서 사이버콘텐츠아카데미 수강하신 과정은?(복수선택가능) <br /></span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
<tr>
<td width="30" valign="bottom">&nbsp;</td>
<td >① 문화 분야 (세부 과정명 : <input name="survey5t1" type="text" id="survey5Text1" size="40" />)</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>② 방송 분야 (세부 과정명 :
<input name="survey5t2" type="text" id="survey5Text2" size="40" />
)</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>③ 게임 분야 (세부 과정명 :
<input name="survey5t3" type="text" id="survey5Text3" size="40" />
)</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
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
<td><span class="question">6) 사이버콘텐츠아카데미 과정의 수강하는데 있어 좋은 점은?</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
<tr>
<td width="30" valign="bottom">&nbsp;</td>
<td width="20"><input name="survey6" type="radio" value="0" /></td>
<td>① 과정이 다양함</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td><input name="survey6" type="radio" value="1" /></td>
<td>② 과정 내용이 유익함</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td><input name="survey6" type="radio" value="2" /></td>
<td>③ 실무중심의 내용 구성</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td><input name="survey6" type="radio" value="3" /></td>
<td>④ 강사진의 우수성</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td><input name="survey6" type="radio" value="4" /></td>
<td>⑤ 기타의견 (
<input name="survey6t" type="text" id="survey6Text1" size="50" />
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
<td><span class="question">7) 사이버콘텐츠아카데미 과정을 수강하는데 있어 불편한 점은?</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
<tr>
<td width="30" valign="bottom">&nbsp;</td>
<td width="20"><input name="survey7" type="radio" value="0" /></td>
<td>① 속도가 느림</td>
<td width="20"><input name="survey7" type="radio" value="1" /></td>
<td width="250">② 과정 내용의 노후함</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td><input name="survey7" type="radio" value="2" /></td>
<td>③ 부가자료 부족</td>
<td><input name="survey7" type="radio" value="3" /></td>
<td>④ 최신트랜드를 반영하는 과정 부족</td>
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
<td><span class="question">8) 문화콘텐츠 영역에서 주로 관심 있는 분야는?(복수선택가능)</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
<tr>
<td width="30" valign="bottom">&nbsp;</td>
<td width="20"><input name="survey8" type="checkbox" value="0" /></td>
<td>① 기획/창작 부분</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td><input name="survey8" type="checkbox" value="1" /></td>
<td>② 제작/기술 부분</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td><input name="survey8" type="checkbox" value="2" /></td>
<td>③ 유통/마케팅 부분</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td><input name="survey8" type="checkbox" value="3" /></td>
<td>④ 기타 (
<input name="survey8t" type="text" id="survey8Text1" size="50" />
)</td>
</tr>
</table></td>
</tr>
<tr>
  <td height="30">&nbsp;</td>
</tr>
<tr>
  <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
      <td>&nbsp;</td>
      <td width="10"><img src="/tmp/images/survey_bul1.gif" width="6" height="6" /></td>
      <td><span class="style1">참고</span></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>

    <tr>
      <td width="30">&nbsp;</td>
      <td>&nbsp;</td>
      <td> <strong><img src="/tmp/images/survey_bul2.gif" width="6" height="6" /> 기획/창작 부분 :</strong> 장르를 관통하여 콘텐츠의 기획,창작,사례연구,기초이론 등 학습을 통해 <br />
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;콘텐츠 기획인력의 창의력과 기획력을 향상시키고자 함<br />
        <br />
        <strong><img src="/tmp/images/survey_bul2.gif" width="6" height="6" /> 제작/기술 부분 : </strong>콘텐츠를 제작하기 위해 필요로 하는 프로그램,디자인,그래픽,사운드 등<br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;실무 스킬을 향상시키고자 함<br />
        <br />
        <strong><img src="/tmp/images/survey_bul2.gif" width="6" height="6" /> 마케팅/유통 부분 : </strong>콘텐츠 산업 전반의 국내 및 해외의 콘텐츠 마케팅,유통 및 해외 산업 <br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;동향을 살펴봄으로써 각 장르별 유통 체계의 이해 및 유통채널의 <br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;선순환을 돕고자 함 </td>
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
<td><span class="question">9) 문화콘텐츠 영역에서 신규 과정으로 개발되었으면 하는 부분은?<br />
  &nbsp;&nbsp;&nbsp;&nbsp;(복수선택가능)</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
<tr>
<td width="30" valign="bottom">&nbsp;</td>
<td width="20"><input name="survey9" type="checkbox" value="0" /></td>
<td>① 기획/창작 부분</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td><input name="survey9" type="checkbox" value="1" /></td>
<td>② 제작/기술 부분</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td><input name="survey9" type="checkbox" value="2" /></td>
<td>③ 유통/마케팅 부분</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td valign="bottom">&nbsp;</td>
<td><input name="survey9" type="checkbox" value="3" /></td>
<td>④ 기타 (
<input name="survey9t" type="text" id="survey9Text1" size="50" />
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
<td><span class="question">10) 문화콘텐츠 영역의 경영일반 부분에 해당되는 다음 과정 중 개설되었으면<br />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;하는 강좌는?(복수선택가능)</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
    <td>
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="answer">
            <tr>
                <td>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" >
                        <tr>
                            <td width="30" valign="bottom">&nbsp;</td>
                            <td valign="bottom"><span onclick="showHide($('#tbl1'),'.a10_1')" style="cursor:pointer">① 문화콘텐츠경영</span></td>
                            <td valign="bottom"><span onclick="showHide($('#tbl1'),'.a10_2')" style="cursor:pointer">② 문화콘텐츠산업</span></td>
                            <td width="120" valign="bottom"><span onclick="showHide('#tbl1','.a10_3')" style="cursor:pointer">③ 콘텐츠입문</span></td>
                        </tr>
                    </table>                </td>
            </tr>
            <tr>
              <td height="15px"></td>
            </tr>
            <tr id="tbl1" style="display:none">
              <td height="15px">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="a10">
                <tr>
                  <td>&nbsp;</td>
                  <td class="table">다음 강좌의 소개를 보고 원하시는 강의를 선택해 주세요.</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="40">&nbsp;</td>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="a10">
                    <tr>
                      <td width="50" height="2" bgcolor="#2B599B"></td>
                      <td width="110" bgcolor="#FF9900"></td>
                      <td width="359" bgcolor="#666666"></td>
                      </tr>

                  </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><strong>선택</strong></td>
                        <td width="100" align="center" style="padding:5px"><strong>강좌명</strong></td>
                        <td align="center" style="padding:5px"><strong>내용소개</strong></td>
                      </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#666666"></td>
                        </tr>
                    </table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_1" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey10" type="checkbox" value="0" /></td>
                        <td width="100" style="padding:5px">문화와 경영의 만남</td>
                        <td style="padding:5px">문화현장 곳곳을 취재하여 자신이 경험한 문화예술의 다양하나 모습을 기초로 예술과 예술경영에 대해 대중들이 폭넓게 이해할 수 있도록 함</td>
                      </tr>
                    </table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_1" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey10" type="checkbox" value="1" /></td>
                        <td width="100" style="padding:5px">문화콘텐츠 경영의 이해</td>
                        <td style="padding:5px">문화콘텐츠 산업의 특선을 살펴보고 그 특성에 따른 문화콘텐츠 산업의 전략을 도출함</td>
                      </tr>
                    </table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey10" type="checkbox" value="2" /></td>
                        <td width="100" style="padding:5px">문화자본의 형성</td>
                        <td style="padding:5px">신자유주의가 본격화되기 시작한 1991년대 후반 이후 한국 문화자본의 형성 원리를 파악하고 그본질을 간파함</td>
                      </tr>
                    </table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey10" type="checkbox" value="3" /></td>
                        <td width="100" style="padding:5px">문화콘텐츠 분양 직업에는 어떤 것이 있을까?</td>
                        <td style="padding:5px">모든 문화콘텐츠의 직업세계를 한자리에 모음으로써 통합적인 인식체계를 마련하고자함</td>
                      </tr>
                    </table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey10" type="checkbox" value="4" /></td>
                        <td width="100" style="padding:5px">전통과 문화콘텐츠의 만남</td>
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
                        <td width="40" align="center" style="padding:5px"><input name="survey10" type="checkbox" value="5" /></td>
                        <td width="100" style="padding:5px">세계화와 문화콘텐츠</td>
                        <td style="padding:5px">현재의 시대상황을 문화의 시각으로 진단하고 한국 문화 또는 한국의 생활세계에 바탕한 문화콘텐츠가 지구인의 문화적 문제제기와 문화입맛에 어떻게 기여할 있는지 그 가능성을 모색해봄</td>
                      </tr>
                    </table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey10" type="checkbox" value="6" /></td>
                        <td width="100" style="padding:5px">우리 시대를 대표하는 문화 KeyWord</td>
                        <td style="padding:5px">키워드로 표상된 문화현상이 지닌 현재적 의미와 본질적 의미를 명료하게 제시하고, 나아가 그것이 오늘의 한국 사회에서 어떤 식으로 표출되고 있는지 알아봄</td>
                      </tr>
                    </table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey10" type="checkbox" value="7" /></td>
                        <td width="100" style="padding:5px">브랜드와 문화콘텐츠의 만남</td>
                        <td style="padding:5px">문화,역사,영화 등 다양한 학문 연구자들이 각 연구 분양에서 퍼블릭 도메인 스토리를 찾아 보고 그것을 콘텐츠화하는 방안에 대한 입체적인 아이디어를 제시함</td>
                      </tr>
                    </table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey10" type="checkbox" value="8" /></td>
                        <td width="100" style="padding:5px">지역문화와 문화 콘텐츠</td>
                        <td style="padding:5px">한국향토문화전자대전 편찬을 통해 얻은 다양한 지역문화 디지털 콘텐츠 편찬 방법들을 전문 연구자들은 물론, 지역문화 콘텐츠에 관심이 있는 일반 학습자들과 공유함</td>
                      </tr>
                    </table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_2" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey10" type="checkbox" value="9" /></td>
                        <td width="100" style="padding:5px">엔터테인먼트 산업의 이해</td>
                        <td style="padding:5px">통합적 관점에서 '행복산업'으로서 엔터테인먼트산업을 바라보기 위한 시도를 모색하고, 더 나아가 엔터테인먼트산업의 발전을 위한 근본적인 개선책을 제시함</td>
                      </tr>
                    </table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_3" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey10" type="checkbox" value="10" /></td>
                        <td width="100" style="padding:5px">문화 콘텐츠 유형론</td>
                        <td style="padding:5px">문화콘텐츠를 구성하고 있는 여러 형식의 문화적 내용물들과 기술적 발명품들이 서로 소통하는 모습을 관찰함</td>
                      </tr>
                    </table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table a10_3" style="padding:4px">
                      <tr>
                        <td width="40" align="center" style="padding:5px"><input name="survey10" type="checkbox" value="11" /></td>
                        <td width="100" style="padding:5px">문화비평</td>
                        <td style="padding:5px">문화비평의 정의에서 문화비평의 실제까지, 문화비평의 기원과 좋은 문화비평의 실례를 따져보면서 문화비평이라는 새로운 장르의 정립을 알기 쉽고 재미있게 설명하고자 함</td>
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
        </table>    </td>
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
<td><span class="question">11) 문화콘텐츠 영역의 기획/창작 부분에 해당되는 다음 과정 중 개설되었으면<br />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;하는 강좌는?(복수선택가능)</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
            <tr>
                <td width="30" valign="bottom">&nbsp;</td>
                <td valign="bottom"><span onclick="hideShow1('tbl2')" style="cursor:pointer">① 공연 & 광고기획</span></td>
            </tr>
            <tr id="tbl2" style="display:none">
              <td>&nbsp;</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="table">다음 강좌의 소개를 보고 원하시는 강의를 선택해 주세요.</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="50" height="2" bgcolor="#2B599B"></td>
                        <td width="110" bgcolor="#FF9900"></td>
                        <td width="359" bgcolor="#666666"></td>
                      </tr>
                    </table>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><strong>선택</strong></td>
                          <td width="100" align="center" style="padding:5px"><strong>강좌명</strong></td>
                          <td align="center" style="padding:5px"><strong>내용소개</strong></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#666666"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="0" /></td>
                          <td width="100" style="padding:5px">마케팅 실무와 공연기획</td>
                          <td style="padding:5px">공연예술경영을 처음 접할 때 숙지해야 할 기본적인 개념들과 요소들을 국내 공연예술시작의 사례를 통해 쉽게 설명함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="1" /></td>
                          <td width="100" style="padding:5px">한국의 축제와 문화콘텐츠</td>
                          <td style="padding:5px">우리나라 축제의 다양한 면모를 각 분야의 전문가들이 흥미로운 시각으로 해석하고 콘텐츠 가치를 중심으로 발전방향을 제시해 놓음</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="2" /></td>
                          <td width="100" style="padding:5px">연극과 뮤지컬 연출론</td>
                          <td style="padding:5px">연출의 역사와 이론, 연극 연출의 실제, 뮤지컬 연출의 실제, 연출의 특수문제에 대한 기본적인 내용을 정리함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="3" /></td>
                          <td width="100" style="padding:5px">공연 매니지먼트</td>
                          <td style="padding:5px">공공 문화예술기관 현장에서 경험했던 공연예술 기획 업무를 체계적인 이론으로 정립함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="4" /></td>
                          <td width="100" style="padding:5px">광고 속의 문화 마케팅</td>
                          <td style="padding:5px">광고와 인터넷 홈페이지에 나타난 기업의 커뮤니케이션 전략을 분석하고 분석의 층위를 카피,이미지,내러티브,모델 등으로 세분화 함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
                <td width="30" valign="bottom" height="30px">&nbsp;</td>
                <td valign="bottom"><span onclick="hideShow1('tbl3')" style="cursor:pointer">② 대중문화</span></td>
            </tr>
            <tr id="tbl3" style="display:none">
              <td>&nbsp;</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="table">다음 강좌의 소개를 보고 원하시는 강의를 선택해 주세요.</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="50" height="2" bgcolor="#2B599B"></td>
                        <td width="110" bgcolor="#FF9900"></td>
                        <td width="359" bgcolor="#666666"></td>
                      </tr>
                    </table>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><strong>선택</strong></td>
                          <td width="100" align="center" style="padding:5px"><strong>강좌명</strong></td>
                          <td align="center" style="padding:5px"><strong>내용소개</strong></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#666666"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="5" /></td>
                          <td width="100" style="padding:5px">대중문화의 이해</td>
                          <td style="padding:5px">문화와 대중문화에 대하여 간략히 설명하고 우리나라 대중문화 전반을 설명함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="6" /></td>
                          <td width="100" style="padding:5px">현 시대의 트렌드와 심리의 이해</td>
                          <td style="padding:5px">자본주의 상품 구조와 유행을 통해 대중적 심리를 읽어냄</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="7" /></td>
                          <td width="100" style="padding:5px">문화 다큐</td>
                          <td style="padding:5px">2006년부터 2009년까지 넓게는 한국사회에서의 대중문화, 좁게는 대중문화 분야에서 일어났던 일들을 다룸</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="8" /></td>
                          <td width="100" style="padding:5px">교육 콘텐츠와 대중예술</td>
                          <td style="padding:5px">영화,게임,음악 등을 적극적으로 교육 콘텐츠로 끌고 들어올 수 있는 방안을 소개함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
                <td width="30" valign="bottom" height="30px">&nbsp;</td>
                <td valign="bottom"><span onclick="hideShow1('tbl4')" style="cursor:pointer">③ 모바일 기획</span></td>
            </tr>
            <tr id="tbl4" style="display:none">
              <td>&nbsp;</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="table">다음 강좌의 소개를 보고 원하시는 강의를 선택해 주세요.</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="50" height="2" bgcolor="#2B599B"></td>
                        <td width="110" bgcolor="#FF9900"></td>
                        <td width="359" bgcolor="#666666"></td>
                      </tr>
                    </table>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><strong>선택</strong></td>
                          <td width="100" align="center" style="padding:5px"><strong>강좌명</strong></td>
                          <td align="center" style="padding:5px"><strong>내용소개</strong></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#666666"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="9" /></td>
                          <td width="100" style="padding:5px">스마트 폰과 문화</td>
                          <td style="padding:5px">스마트 폰 열풍에 따른 문화의 생산과 소비 패러다임 변화가 세계 문화시장에 미치는 영향력에 대한 고찰</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="10" /></td>
                          <td width="100" style="padding:5px">스마트폰 어플 기획</td>
                          <td style="padding:5px">안드로이드 어플을 기획하기 전에 알아야 할 다양한 지식 제공</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
                <td width="30" valign="bottom" height="30px">&nbsp;</td>
                <td valign="bottom"><span onclick="hideShow1('tbl5')" style="cursor:pointer">④ 스토리텔링 & 시나리오작법</span></td>
            </tr>
            <tr id="tbl5" style="display:none">
              <td>&nbsp;</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                  <td class="table">&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="table">다음 강좌의 소개를 보고 원하시는 강의를 선택해 주세요.</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="50" height="2" bgcolor="#2B599B"></td>
                        <td width="110" bgcolor="#FF9900"></td>
                        <td width="359" bgcolor="#666666"></td>
                      </tr>
                    </table>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><strong>선택</strong></td>
                          <td width="100" align="center" style="padding:5px"><strong>강좌명</strong></td>
                          <td align="center" style="padding:5px"><strong>내용소개</strong></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#666666"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="11" /></td>
                          <td width="100" style="padding:5px">문화콘텐츠 속의 스토리텔링</td>
                          <td style="padding:5px">문화산업 현장 곳곳에서 벌어지고 있는 '이야기와 문화 콘텐츠의 만남'에 대해 다룸</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="12" /></td>
                          <td width="100" style="padding:5px">만화와 스토리텔링</td>
                          <td style="padding:5px">다양한 영역의 문화콘텐츠를 분석하고 문화정책을 살펴봄으로써 문화콘텐츠의 경쟁력을 높일 수 있는 창의적 융합과 소통의 장을 만들어내고자 함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="13" /></td>
                          <td width="100" style="padding:5px">기획자를 위한 스토리텔링</td>
                          <td style="padding:5px">우리나라 관객들의 사랑을 받아온 작품들을 예로 들어 현장에 바로 적용할 수 있는 법칙을 제안함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="14" /></td>
                          <td width="100" style="padding:5px">글쓰기 전문가 되기</td>
                          <td style="padding:5px">감성적인 언어부터 파격적이고 명쾌한 문장까지 글쓰기 비법을 담음</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="15" /></td>
                          <td width="100" style="padding:5px">글쓰기 비법</td>
                          <td style="padding:5px">글쓰기의 본직과 방법을 소개함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="16" /></td>
                          <td width="100" style="padding:5px">한국형 시나리오 쓰기</td>
                          <td style="padding:5px">우리나라의 설정에 맞는 시나리오 작법을 다양한 예를 통해 흥미롭게 설명함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
                <td width="30" valign="bottom" height="30px">&nbsp;</td>
                <td valign="bottom"><span onclick="hideShow1('tbl6')" style="cursor:pointer">⑤ 애니메이션 & 에듀테인먼트 기획</span></td>
            </tr>
            <tr id="tbl6" style="display:none">
              <td>&nbsp;</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="table">다음 강좌의 소개를 보고 원하시는 강의를 선택해 주세요.</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="50" height="2" bgcolor="#2B599B"></td>
                        <td width="110" bgcolor="#FF9900"></td>
                        <td width="359" bgcolor="#666666"></td>
                      </tr>
                    </table>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><strong>선택</strong></td>
                          <td width="100" align="center" style="padding:5px"><strong>강좌명</strong></td>
                          <td align="center" style="padding:5px"><strong>내용소개</strong></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#666666"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="17" /></td>
                          <td width="100" style="padding:5px">한국 애니메이션의 역사</td>
                          <td style="padding:5px">한국 애니메이션의 역사와 발전 모습의 전체적인 지형도를 그림. 이를 위해 한국 애니메이션 역사의 흐름을 정리하면서 결정적 순간을 중심으로 시기를 구분함.</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="18" /></td>
                          <td width="100" style="padding:5px">에듀테인먼트 콘텐츠 기획</td>
                          <td style="padding:5px">에듀테인먼트의 유형을 출판 에듀테인먼트,디지털 에듀테인먼트,공간체험 에듀테인먼트로 나누어 다양한 콘텐츠 개발 사례를 소개함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
                <td width="30" valign="bottom" height="30px">&nbsp;</td>
                <td valign="bottom"><span onclick="hideShow1('tbl7')" style="cursor:pointer">⑥ 영화 & 음악</span></td>
            </tr>
            <tr id="tbl7" style="display:none">
              <td>&nbsp;</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="table">다음 강좌의 소개를 보고 원하시는 강의를 선택해 주세요.</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="50" height="2" bgcolor="#2B599B"></td>
                        <td width="110" bgcolor="#FF9900"></td>
                        <td width="359" bgcolor="#666666"></td>
                      </tr>
                    </table>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><strong>선택</strong></td>
                          <td width="100" align="center" style="padding:5px"><strong>강좌명</strong></td>
                          <td align="center" style="padding:5px"><strong>내용소개</strong></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#666666"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="19" /></td>
                          <td width="100" style="padding:5px">영화 테마 연구</td>
                          <td style="padding:5px">영화에 담긴 현대사회를 조명한 저술. 영화의 매체적 접근을 시작으로 13가지 테마로 나누어 영화에 나타난 현대사회를 조명함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="20" /></td>
                          <td width="100" style="padding:5px">파리의 시네마 라이프</td>
                          <td style="padding:5px">영화 파리를 함께 즐길 때 얻을 수 있는 재미를 담음. 최근에 파리에서 불고 있는 한국 여화의 붐 현상도 다루어짐</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="21" /></td>
                          <td width="100" style="padding:5px">철학과 대중가요</td>
                          <td style="padding:5px">1930년대 이후 한국인들의 인식에 커다란 영향을 미친 대중가요에 대하여 철학적 해석을 시도</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
                <td width="30" valign="bottom" height="30px">&nbsp;</td>
                <td valign="bottom"><span onclick="hideShow1('tbl8')" style="cursor:pointer">⑦ 전시 & 출판 기획</span></td>
            </tr>
            <tr id="tbl8" style="display:none">
              <td>&nbsp;</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="table">다음 강좌의 소개를 보고 원하시는 강의를 선택해 주세요.</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="50" height="2" bgcolor="#2B599B"></td>
                        <td width="110" bgcolor="#FF9900"></td>
                        <td width="359" bgcolor="#666666"></td>
                      </tr>
                    </table>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><strong>선택</strong></td>
                          <td width="100" align="center" style="padding:5px"><strong>강좌명</strong></td>
                          <td align="center" style="padding:5px"><strong>내용소개</strong></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#666666"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="22" /></td>
                          <td width="100" style="padding:5px">체험형 전시콘텐츠 기획</td>
                          <td style="padding:5px">관객들의 몰입을 부르는 전시에 대해 고려해야할 점을 담음</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey11" type="checkbox" value="23" /></td>
                          <td width="100" style="padding:5px">출판기획의 이해</td>
                          <td style="padding:5px">출판사의 구조에서부터 좋은 책을 고르고 편집을 어떻게 해야 하는지 들 좋은 책을 만들기 위해 무엇을 어떻게 해야 할지에 대한 내용을 알아봄</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table></td>
                </tr>
              </table></td>
            </tr>
        </table>    </td>
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
<td><span class="question">12) 문화콘텐츠 영역의 제작/기술 부분에 해당되는 다음 과정 중 개설되었으면 <br />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;하는 강좌는?(복수선택가능)</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
            <tr>
                <td width="30" valign="bottom">&nbsp;</td>
                <td valign="bottom"><span onclick="hideShow1('tbl9')" style="cursor:pointer">① 문화기술(CT)</span></td>
            </tr>
            <tr id="tbl9" style="display:none">
              <td>&nbsp;</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="table">다음 강좌의 소개를 보고 원하시는 강의를 선택해 주세요.</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="50" height="2" bgcolor="#2B599B"></td>
                        <td width="110" bgcolor="#FF9900"></td>
                        <td width="359" bgcolor="#666666"></td>
                      </tr>
                    </table>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><strong>선택</strong></td>
                          <td width="100" align="center" style="padding:5px"><strong>강좌명</strong></td>
                          <td align="center" style="padding:5px"><strong>내용소개</strong></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#666666"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey12" type="checkbox" value="0" /></td>
                          <td width="100" style="padding:5px">과학기술문화론</td>
                          <td style="padding:5px">과학기술문화에 대한 이론적 탐구에 그치지 않고, 그것의 유.무형적 하부구조를 실증자료에 근거하여 분석적으로 살펴봄</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey12" type="checkbox" value="1" /></td>
                          <td width="100" style="padding:5px">문화기술(CT) 표준화 대상 기술</td>
                          <td style="padding:5px">문화기술에 대한 개념과 관련 이론 설명</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
                <td width="30" valign="bottom" height="30px">&nbsp;</td>
                <td valign="bottom"><span onclick="hideShow1('tbl10')" style="cursor:pointer">② 애니메이션 제작</span></td>
            </tr>
            <tr id="tbl10" style="display:none">
              <td>&nbsp;</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="table">다음 강좌의 소개를 보고 원하시는 강의를 선택해 주세요.</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="50" height="2" bgcolor="#2B599B"></td>
                        <td width="110" bgcolor="#FF9900"></td>
                        <td width="359" bgcolor="#666666"></td>
                      </tr>
                    </table>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><strong>선택</strong></td>
                          <td width="100" align="center" style="padding:5px"><strong>강좌명</strong></td>
                          <td align="center" style="padding:5px"><strong>내용소개</strong></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#666666"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey12" type="checkbox" value="2" /></td>
                          <td width="100" style="padding:5px">발상을 통한 애니메이션 제작</td>
                          <td style="padding:5px">전국에서 수집한 만화.애니메이션 작품 가운데 최고의 완성도를 갖춘 작품을 중심으로, 상황표현과 컷만화에 대한 변하지 않는 이론과 기술 분석</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table></td>
                </tr>
              </table></td>
            </tr>
        </table>    </td>
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
<td><span class="question">13) 문화콘텐츠 영역의 유통/마케팅 부분에 해당되는 다음 과정 중 <br />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;개설되었으면 하는 강좌는?(복수선택가능)</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
            <tr>
                <td width="30" valign="bottom">&nbsp;</td>
                <td valign="bottom"><span onclick="hideShow1('tbl11')" style="cursor:pointer">① 마케팅 일반</span></td>
            </tr>
            <tr id="tbl11" style="display:none">
              <td>&nbsp;</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="table">다음 강좌의 소개를 보고 원하시는 강의를 선택해 주세요.</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="50" height="2" bgcolor="#2B599B"></td>
                        <td width="110" bgcolor="#FF9900"></td>
                        <td width="359" bgcolor="#666666"></td>
                      </tr>
                    </table>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><strong>선택</strong></td>
                          <td width="100" align="center" style="padding:5px"><strong>강좌명</strong></td>
                          <td align="center" style="padding:5px"><strong>내용소개</strong></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#666666"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey13" type="checkbox" value="0" /></td>
                          <td width="100" style="padding:5px">콘텐츠 마케팅</td>
                          <td style="padding:5px">문화산업에서 황금달걀로 급부상한 '콘텐츠' 마케팅에 대해 이야기함, 성공한 문화콘텐츠들의 원소스멀티유즈 전략을 점검함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey13" type="checkbox" value="1" /></td>
                          <td width="100" style="padding:5px">문화기업과 문화마케팅</td>
                          <td style="padding:5px">기업 문화마케팅의 개념에서부터 실제 적용을 가능하게 하는 전략에서부터 현재 문화마케팅을 시행하고 있는 기업들의 사례까지 다양하게 담아냄</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey13" type="checkbox" value="2" /></td>
                          <td width="100" style="padding:5px">시장 발견의 기술</td>
                          <td style="padding:5px">기존시장 확대의 기술 및 미래 유망시장에 대한 견해를 피력하며 성공적인 마케팅에 대해 이야기함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
                <td width="30" valign="bottom" height="30px">&nbsp;</td>
                <td valign="bottom"><span onclick="hideShow1('tbl12')" style="cursor:pointer">② 글로벌 비즈니스</span></td>
            </tr>
            <tr id="tbl12" style="display:none">
              <td>&nbsp;</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="table">다음 강좌의 소개를 보고 원하시는 강의를 선택해 주세요.</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="50" height="2" bgcolor="#2B599B"></td>
                        <td width="110" bgcolor="#FF9900"></td>
                        <td width="359" bgcolor="#666666"></td>
                      </tr>
                    </table>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><strong>선택</strong></td>
                          <td width="100" align="center" style="padding:5px"><strong>강좌명</strong></td>
                          <td align="center" style="padding:5px"><strong>내용소개</strong></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#666666"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey13" type="checkbox" value="3" /></td>
                          <td width="100" style="padding:5px">글로컬문화콘텐츠의 이해</td>
                          <td style="padding:5px">세계적인 문화콘텐츠 기획과 제작의 이론적 토대를 제공할 글로컬문화에 대한 해설가이드</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey13" type="checkbox" value="4" /></td>
                          <td width="100" style="padding:5px">아시아 문화연구</td>
                          <td style="padding:5px">아시아 문화연구에 대해 아시아라는 '문제 틀' 안에 숨겨진 문화정치적인 모순들을 비판적으로 사고하고, 이를 극복하기 위한 실천적인 의제들을 제시함</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey13" type="checkbox" value="5" /></td>
                          <td width="100" style="padding:5px">미국 비주얼 마케팅</td>
                          <td style="padding:5px">미국 뉴욕의 비주얼 마케팅 기법을 다룸
                            소매(Retail) 마케팅: 스토어,백화점,할인점,쇼핑몰,식품점,서점 마케팅
                            문화(Culture) 마케팅: 박물관,미술관,건축,거리,공원,이벤트 롱아일랜드 마케팅</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
                <td width="30" valign="bottom" height="30px">&nbsp;</td>
                <td valign="bottom"><span onclick="hideShow1('tbl13')" style="cursor:pointer">③ 소셜네트워크 & 출판 & 영화 마케팅</span></td>
            </tr>
            <tr id="tbl13" style="display:none">
              <td>&nbsp;</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="table">다음 강좌의 소개를 보고 원하시는 강의를 선택해 주세요.</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="50" height="2" bgcolor="#2B599B"></td>
                        <td width="110" bgcolor="#FF9900"></td>
                        <td width="359" bgcolor="#666666"></td>
                      </tr>
                    </table>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><strong>선택</strong></td>
                          <td width="100" align="center" style="padding:5px"><strong>강좌명</strong></td>
                          <td align="center" style="padding:5px"><strong>내용소개</strong></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#666666"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey13" type="checkbox" value="6" /></td>
                          <td width="100" style="padding:5px">소셜네트워크와 미래혁명</td>
                          <td style="padding:5px">소셜네트워크란 무엇이며 이를 기반으로 한 비즈니스와 산업 전반의 패러다임은 무엇인지 살펴보고, 이를 통해 우리의 미래가 어떠헥 변모할지 보여줌</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table" style="padding:4px">
                        <tr>
                          <td width="40" align="center" style="padding:5px"><input name="survey13" type="checkbox" value="7" /></td>
                          <td width="100" style="padding:5px">출판 마케팅</td>
                          <td style="padding:5px">출판 마케팅 영역에서 중요하다고 생각되는 제목, 독자, 차별적 우위의 확보, 소위 '띠지'라 불리는 프로모션,블로그의 문제를 알아봄</td>
                        </tr>
                      </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30" height="1" bgcolor="#CCCCCC"></td>
                        </tr>
                    </table></td>
                </tr>
              </table></td>
            </tr>
        </table>    </td>
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
<td><span class="question">14) 위 소개된 과정 이 외에 문화콘텐츠 관련 강좌로 신설되었으면 하는 <br />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;구체적인 과목은?</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
<tr>
<td width="30" valign="bottom">&nbsp;</td>
<td valign="bottom">(
<input name="survey14t" type="text" id="survey14Text1" size="65" />
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
<td><span class="question">15) 위의 과목을 선정하게 된 이유는?</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="answer">
<tr>
<td width="30" valign="bottom">&nbsp;</td>
<td valign="bottom">(
<input name="survey15t" type="text" id="survey15Text1" size="65" />
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