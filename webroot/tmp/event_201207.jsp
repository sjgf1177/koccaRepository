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
        v_name   = dbox.getString("d_name");
        v_userid = dbox.getString("d_userid");
        v_chk    = dbox.getString("d_val");  //설문 했는지 여부
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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>KOCCA 한국콘텐츠아카데미 - 지식팩토리</title>
<meta http-equiv="content-type" content="text/html;charset=euc-kr">
<meta http-equiv="Content-Style-Type" content="text/css">
<script type='text/javascript' src='http://edu.kocca.or.kr/script/jquery-1.3.2.min.js'></script>
<script type='text/javascript' src='http://edu.kocca.or.kr/script/jquery.form.js'></script>


<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
    var sw=false;
    function gotoSubmit()
    {
        var ans="";
        var tmp="";

        tmp=$("[name='survey1']:checked").val();
        if(tmp==undefined)
        {
            alert("1번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans=tmp+",";

        // 222
        tmp=$("[name='survey2']:checked").val();
        if(tmp==undefined)
        {
            alert("2번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey3']:checked").val();
        if(tmp==undefined)
        {
            alert("3번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey4']:checked").val();
        if(tmp==undefined)
        {
            alert("4번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey5']:checked").val();
        if(tmp==undefined)
        {
            alert("5번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey6']:checked").val();
        if(tmp==undefined)
        {
            alert("6번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey7']:checked").val();
        if(tmp==undefined)
        {
            alert("7번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey8']:checked").val();
        if(tmp==undefined)
        {
            alert("8번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey9']:checked").val();
        if(tmp==undefined)
        {
            alert("9번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey10']:checked").val();
        if(tmp==undefined)
        {
            alert("10번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey11']:checked").val();
        if(tmp==undefined)
        {
            alert("11번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey12']:checked").val();
        if(tmp==undefined)
        {
            alert("12번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey13']:checked").val();
        if(tmp==undefined)
        {
            alert("13번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey14']:checked").val();
        if(tmp==undefined)
        {
            alert("14번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey15']:checked").val();
        if(tmp==undefined)
        {
            alert("15번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey16']:checked").val();
        if(tmp==undefined)
        {
            alert("16번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey17']:checked").val();
        if(tmp==undefined)
        {
            alert("17번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey18']:checked").val();
        if(tmp==undefined)
        {
            alert("18번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey19']:checked").val();
        if(tmp==undefined)
        {
            alert("19번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey20']:checked").val();
        if(tmp==undefined)
        {
            alert("20번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey21']:checked").val();
        if(tmp==undefined)
        {
            alert("21번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey22']:checked").val();
        if(tmp==undefined)
        {
            alert("22번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey23']:checked").val();
        if(tmp==undefined)
        {
            alert("23번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey24']:checked").val();
        if(tmp==undefined)
        {
            alert("24번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey25']:checked").val();
        if(tmp==undefined)
        {
            alert("25번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";        
       
        // 18
        tmp=$("[name='survey26']:checked").val();
        if(tmp==undefined)
        {
            alert("26번째 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp;      
        

        if(!sw)
        {          
            $("[name='p_ans']").val(ans);

            document.nform1.submit();
            alert("설문이 제출 되었습니다\n 감사합니다.");
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

<style>
* { font-family:"batang","dotum","arial","sans-self"; font-size:12px; color:#323232; overflow-x:hidden; overflow-y:auto; padding:0px; margin:0px; }
ul,ol,li { list-style:none; }
form { margin:0; padding:0; }
input[type='radio'] { margin:0px; padding:0px; }
.alignL { text-align:left; }
.alignC { text-align:center; }
.alignR { text-align:right; }

div#gudieline { width:660px; height:100%; margin:0 auto; border:1px solid #dddddd; padding:20px; }
div#gudieline div.pagetitle { text-align:center; }
div#gudieline div.indate { text-align:right; margin-top:10px; }
div#gudieline div.question { margin-top:40px; margin-bottom:10px; font-weight:bold; font-size:14px; }
div#gudieline div.tip { margin-top:10px; margin-left:14px; color:#828282; }
div#gudieline ul.answer { width:660px; margin-left:14px; }
div#gudieline ul.answer li { float:left; padding-right:50px; margin-top:10px; color:#626262; position:relative; }
div#gudieline ul.answer li input[type='text'] { border:none; width:200px; color:#4f71b0; }
div#gudieline div.centerline { border-top:1px dotted #dddddd; margin-top:40px; }
div#gudieline table.rable { width:640px; margin-left:20px; border:2px solid #777777; border-collapse:collapse; }
div#gudieline table.rable th { height:30px; background-color:#888888; border:1px solid #777777; color:#ffffff; }
div#gudieline table.rable th.subject { color:#323232; background-color:#ffffff; }
div#gudieline table.rable td { text-align:center; border:1px solid #777777;  padding-top:6px; padding-bottom:6px; }
div#gudieline table.rable td.question { text-align:left; padding-left:5px; }
div#gudieline table.selectcourse { width:646px; margin-left:14px; border-bottom:1px solid #777777; border-top:1px solid #777777; }
div#gudieline table.selectcourse th { border-bottom:1px solid #777777; padding:5px 0px 5px 0px; }
div#gudieline table.selectcourse td { padding:5px 0px 5px 0px; }
div#gudieline div.groupname { font-weight:bold; margin:40px 0px 10px 14px; }
div#gudieline div.usermemo { margin:10px 0px 0px 14px; }
div#gudieline div.usermemo textarea { width:624px; height:60px; border:1px solid #dddddd; padding:10px; }
div#gudieline div.footer { margin-top:30px; margin-bottom:30px; text-align:center; }
div#gudieline div.footer input[type='submit'] { padding:10px 40px 10px 40px; width:300px; border:1px solid #323232; background-color:#626262; color:#ffffff; font-family:"gulim"; font-weight:bold; }
div#gudieline div.event { margin-left:14px; margin-top:40px; background-image:url('/tmp/images/event2.gif'); background-repeat:no-repeat; width:644px; height:476px; border:1px solid #dddddd; }
</style>

<body>
<form name="nform1" method="post" id="nform1" action="/servlet/controller.community.CommunityRiskServlet">
    <input type = "hidden" name = "p_process" value="eventInsert">
    <input type = "hidden" name = "p_ans" >
    <input type = "hidden" name = "p_userid" value="<%=v_userid%>">
    <input type = "hidden" name = "p_gubun_inja" value="<%=v_gubun%>">
<div id="gudieline">    
	<div class="pagetitle"><img src="/tmp/images/title2.gif" alt="사이버콘텐츠아카데미 교육 서비스 만족도 조사"></div>
	<div class="indate">☞ 설문조사기간 : 2012년 7월 2일(월) ~ 7월 15일(금)</div>
	
	<div class="question">1. 온라인 교육과정 부문</div>
	<table class="rable" cellpadding="0" cellspacing="0">
		<colgroup><col width="*"><col width="60"><col width="60"><col width="60"><col width="60"><col width="60"></colgroup>
		<tr>
			<th class="subject">구분</th>
			<th>매우만족</th>
			<th>만족</th>
			<th>보통</th>
			<th>불만</th>
			<th>매우불만</th>
		</tr>
		<tr>
			<td class="question">학습적 성취에 도움이 되는가?</td>				
			<td><input name="survey1" type="radio" value="1" ></td>
			<td><input name="survey1" type="radio" value="2" ></td>
			<td><input name="survey1" type="radio" value="3" ></td>
			<td><input name="survey1" type="radio" value="4" ></td>
			<td><input name="survey1" type="radio" value="5" ></td>			
		</tr>
		<tr>
			<td class="question">다양한 교육콘텐츠를 보유하고 있는가?</td>
			<td><input name="survey2" value="1"  type="radio"></td>
			<td><input name="survey2" value="2" type="radio"></td>
			<td><input name="survey2" value="3" type="radio"></td>
			<td><input name="survey2" value="4" type="radio"></td>
			<td><input name="survey2" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">강사는 교육에 필요한 지식과 강의능력을 갖추고 있는가?</td>
			<td><input name="survey3" value="1" type="radio"></td>
			<td><input name="survey3" value="2" type="radio"></td>
			<td><input name="survey3" value="3" type="radio"></td>
			<td><input name="survey3" value="4" type="radio"></td>
			<td><input name="survey3" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">강사진이 관련분야의 전문가로 구성되어 있는가?</td>
			<td><input name="survey4" value="1"  type="radio"></td>
			<td><input name="survey4" value="2" type="radio"></td>
			<td><input name="survey4" value="3" type="radio"></td>
			<td><input name="survey4" value="4" type="radio"></td>
			<td><input name="survey4" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">학습자의 교육수요가 반영되었는가?</td>
			<td><input name="survey5" value="1"  type="radio"></td>
			<td><input name="survey5" value="2" type="radio"></td>
			<td><input name="survey5" value="3" type="radio"></td>
			<td><input name="survey5" value="4" type="radio"></td>
			<td><input name="survey5" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">학습자의 개선요구가 반영되었는가?</td>
			<td><input name="survey6" value="1" type="radio"></td>
			<td><input name="survey6" value="2" type="radio"></td>
			<td><input name="survey6" value="3" type="radio"></td>
			<td><input name="survey6" value="4" type="radio"></td>
			<td><input name="survey6" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">온라인 교육콘텐츠에 대한 전반적인 만족도는?</td>
			<td><input name="survey7" value="1" type="radio"></td>
			<td><input name="survey7" value="2" type="radio"></td>
			<td><input name="survey7" value="3" type="radio"></td>
			<td><input name="survey7" value="4" type="radio"></td>
			<td><input name="survey7" value="5" type="radio"></td>
		</tr>
	</table>
	
	<div class="question">2. 교육운영 서비스 부문</div>
	<table class="rable" cellpadding="0" cellspacing="0">
		<colgroup><col width="*"><col width="60"><col width="60"><col width="60"><col width="60"><col width="60"></colgroup>
		<tr>
			<th class="subject">구분</th>
			<th>매우만족</th>
			<th>만족</th>
			<th>보통</th>
			<th>불만</th>
			<th>매우불만</th>
		</tr>
		<tr>
			<td class="question">질의응답 및 VOC에 대한 신속한 응대가 이루어 지는가?</td>
			<td><input name="survey8" value="1"  type="radio"></td>
			<td><input name="survey8" value="2" type="radio"></td>
			<td><input name="survey8" value="3" type="radio"></td>
			<td><input name="survey8" value="4" type="radio"></td>
			<td><input name="survey8" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">도움말, 질의답변, FAQ 등 사용자 편의를 위한 메뉴를 제공하고 있는가?</td>
			<td><input name="survey9" value="1" type="radio"></td>
			<td><input name="survey9" value="2" type="radio"></td>
			<td><input name="survey9" value="3" type="radio"></td>
			<td><input name="survey9" value="4" type="radio"></td>
			<td><input name="survey9" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">수강생의 질문에 성실하게 답변하는 등 요구사항을 이해하려고 노력하는가?</td>
			<td><input name="survey10" value="1" type="radio"></td>
			<td><input name="survey10" value="2" type="radio"></td>
			<td><input name="survey10" value="3" type="radio"></td>
			<td><input name="survey10" value="4" type="radio"></td>
			<td><input name="survey10" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">교육운영에 대한 전반적인 만족도는?</td>
			<td><input name="survey11" value="1" type="radio"></td>
			<td><input name="survey11" value="2" type="radio"></td>
			<td><input name="survey11" value="3" type="radio"></td>
			<td><input name="survey11" value="4" type="radio"></td>
			<td><input name="survey11" value="5" type="radio"></td>
		</tr>
	</table>
	
	<div class="question">3. 웹페이지 운영 부문</div>
	<table class="rable" cellpadding="0" cellspacing="0">
		<colgroup><col width="*"><col width="60"><col width="60"><col width="60"><col width="60"><col width="60"></colgroup>
		<tr>
			<th class="subject">구분</th>
			<th>매우만족</th>
			<th>만족</th>
			<th>보통</th>
			<th>불만</th>
			<th>매우불만</th>
		</tr>
		<tr>
			<td class="question">사이트에 상업광고나 불필요한 내용 등이 없는가?</td>
			<td><input name="survey12" value="1" type="radio"></td>
			<td><input name="survey12" value="2" type="radio"></td>
			<td><input name="survey12" value="3" type="radio"></td>
			<td><input name="survey12" value="4" type="radio"></td>
			<td><input name="survey12" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">접속, 페이지이동, 학습 중 에러가 없으며 안정적인가?</td>
			<td><input name="survey13" value="1" type="radio"></td>
			<td><input name="survey13" value="2" type="radio"></td>
			<td><input name="survey13" value="3" type="radio"></td>
			<td><input name="survey13" value="4" type="radio"></td>
			<td><input name="survey13" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">사이트 메뉴는 교육에 적합하게 배치되어 있는가?</td>
			<td><input name="survey14" value="1" type="radio"></td>
			<td><input name="survey14" value="2" type="radio"></td>
			<td><input name="survey14" value="3" type="radio"></td>
			<td><input name="survey14" value="4" type="radio"></td>
			<td><input name="survey14" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">웹페이지에 대한 전반적인 만족도는?</td>
			<td><input name="survey15" value="1" type="radio"></td>
			<td><input name="survey15" value="2" type="radio"></td>
			<td><input name="survey15" value="3" type="radio"></td>
			<td><input name="survey15" value="4" type="radio"></td>
			<td><input name="survey15" value="5" type="radio"></td>
		</tr>
	</table>
	
	<div class="question">4. 사이버콘텐츠아카데미의 사회적 기여 부문</div>
	<table class="rable" cellpadding="0" cellspacing="0">
		<colgroup><col width="*"><col width="60"><col width="60"><col width="60"><col width="60"><col width="60"></colgroup>
		<tr>
			<th class="subject">구분</th>
			<th>매우만족</th>
			<th>만족</th>
			<th>보통</th>
			<th>불만</th>
			<th>매우불만</th>
		</tr>
		<tr>
			<td class="question">사이트를 상업적 목적으로 활용하지 않는가?</td>
			<td><input name="survey16" value="1" type="radio"></td>
			<td><input name="survey16" value="2" type="radio"></td>
			<td><input name="survey16" value="3" type="radio"></td>
			<td><input name="survey16" value="4" type="radio"></td>
			<td><input name="survey16" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">콘텐츠인재양성에 기여하는가?</td>
			<td><input name="survey17" value="1" type="radio"></td>
			<td><input name="survey17" value="2" type="radio"></td>
			<td><input name="survey17" value="3" type="radio"></td>
			<td><input name="survey17" value="4" type="radio"></td>
			<td><input name="survey17" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">개인정보 보호에 철저한가?</td>
			<td><input name="survey18" value="1" type="radio"></td>
			<td><input name="survey18" value="2" type="radio"></td>
			<td><input name="survey18" value="3" type="radio"></td>
			<td><input name="survey18" value="4" type="radio"></td>
			<td><input name="survey18" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">전반적인 공익추구에 대한 평가는?</td>
			<td><input name="survey19" value="1" type="radio"></td>
			<td><input name="survey19" value="2" type="radio"></td>
			<td><input name="survey19" value="3" type="radio"></td>
			<td><input name="survey19" value="4" type="radio"></td>
			<td><input name="survey19" value="5" type="radio"></td>
		</tr>
	</table>
	
	<div class="question">5. 사이버콘텐츠아카데미의 브랜드 평가 부문</div>
	<table class="rable" cellpadding="0" cellspacing="0">
		<colgroup><col width="*"><col width="60"><col width="60"><col width="60"><col width="60"><col width="60"></colgroup>
		<tr>
			<th class="subject">구분</th>
			<th>매우만족</th>
			<th>만족</th>
			<th>보통</th>
			<th>불만</th>
			<th>매우불만</th>
		</tr>
		<tr>
			<td class="question">온라인 교육서비스에 대해 전반적으로 만족하는가?</td>
			<td><input name="survey20" value="1" type="radio"></td>
			<td><input name="survey20" value="2" type="radio"></td>
			<td><input name="survey20" value="3" type="radio"></td>
			<td><input name="survey20" value="4" type="radio"></td>
			<td><input name="survey20" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">학습효과에 만족하는가?</td>
			<td><input name="survey21" value="1" type="radio"></td>
			<td><input name="survey21" value="2" type="radio"></td>
			<td><input name="survey21" value="3" type="radio"></td>
			<td><input name="survey21" value="4" type="radio"></td>
			<td><input name="survey21" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">교육방향에 대해 만족하는가?</td>
			<td><input name="survey22" value="1" type="radio"></td>
			<td><input name="survey22" value="2" type="radio"></td>
			<td><input name="survey22" value="3" type="radio"></td>
			<td><input name="survey22" value="4" type="radio"></td>
			<td><input name="survey22" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">사이버콘텐츠아카데미를 믿고 지지하겠는가?</td>
			<td><input name="survey23" value="1" type="radio"></td>
			<td><input name="survey23" value="2" type="radio"></td>
			<td><input name="survey23" value="3" type="radio"></td>
			<td><input name="survey23" value="4" type="radio"></td>
			<td><input name="survey23" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">한국콘텐츠진흥원의 이미지에 도움이 되는가?</td>
			<td><input name="survey24" value="1" type="radio"></td>
			<td><input name="survey24" value="2" type="radio"></td>
			<td><input name="survey24" value="3" type="radio"></td>
			<td><input name="survey24" value="4" type="radio"></td>
			<td><input name="survey24" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">콘텐츠 환경과 종사자들에게 도움이 되는가?</td>
			<td><input name="survey25" value="1" type="radio"></td>
			<td><input name="survey25" value="2" type="radio"></td>
			<td><input name="survey25" value="3" type="radio"></td>
			<td><input name="survey25" value="4" type="radio"></td>
			<td><input name="survey25" value="5" type="radio"></td>
		</tr>
		<tr>
			<td class="question">대한민국 콘텐츠산업 발전에 도움이 되는가?</td>
			<td><input name="survey26" value="1" type="radio"></td>
			<td><input name="survey26" value="2" type="radio"></td>
			<td><input name="survey26" value="3" type="radio"></td>
			<td><input name="survey26" value="4" type="radio"></td>
			<td><input name="survey26" value="5" type="radio"></td>
		</tr>
	</table>
	
	
	<div class="footer"><a href="javascript:gotoSubmit();" onfocus="this.blur();"><img src="/tmp/images/confirm2.gif" border="0" ></a></div>
</div>
</form>
</body>
</html>
<%}%>