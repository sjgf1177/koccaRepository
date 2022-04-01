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
div#gudieline div.question { margin-top:40px; margin-bottom:10px; }
div#gudieline div.tip { margin-top:10px; margin-left:14px; color:#828282; }
div#gudieline ul.answer { width:660px; margin-left:14px; }
div#gudieline ul.answer li { float:left; padding-right:50px; margin-top:10px; color:#626262; position:relative; }
div#gudieline ul.answer li input[type='text'] { border:none; width:200px; color:#4f71b0; }
div#gudieline div.centerline { border-top:1px dotted #dddddd; margin-top:40px; }
div#gudieline table.rable { width:646px; margin-left:14px; border:2px solid #777777; border-collapse:collapse; }
div#gudieline table.rable th { height:30px; background-color:#888888; border:1px solid #777777; color:#ffffff; }
div#gudieline table.rable th.subject { color:#323232; background-color:#ffffff; }
div#gudieline table.rable td { text-align:center; border:1px solid #777777; height:24px; }
div#gudieline table.rable td.question { text-align:left; text-indent:5px; }
div#gudieline table.selectcourse { width:646px; margin-left:14px; border-bottom:1px solid #777777; border-top:1px solid #777777; }
div#gudieline table.selectcourse th { border-bottom:1px solid #777777; padding:5px 0px 5px 0px; }
div#gudieline table.selectcourse td { padding:5px 0px 5px 0px; }
div#gudieline div.groupname { font-weight:bold; margin:40px 0px 10px 14px; }
div#gudieline div.usermemo { margin:10px 0px 0px 14px; }
div#gudieline div.usermemo textarea { width:624px; height:60px; border:1px solid #dddddd; padding:10px; }
div#gudieline div.footer { margin-top:30px; margin-bottom:30px; text-align:center; }
div#gudieline div.footer input[type='submit'] { padding:10px 40px 10px 40px; width:300px; border:1px solid #323232; background-color:#626262; color:#ffffff; font-family:"gulim"; font-weight:bold; }
div#gudieline div.event { margin-left:14px; margin-top:40px; background-image:url('/tmp/images/event.gif'); background-repeat:no-repeat; width:644px; height:476px; border:1px solid #dddddd; }
</style>


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

        tmp=$("[name='survey1']:checked").val();
        if(tmp==undefined)
        {
            alert("1번 설문에 답을 하지 않았습니다.");
            return;
        }
        ans=tmp+",";

        // 222
        tmp=$("[name='survey2']:checked").val();
        if(tmp==undefined)
        {
            alert("2번 설문에 답을 하지 않았습니다.");
            return;
        }
        if(tmp=="6")
        {
            tmp=$("[name='survey2t']").val();
            if($.trim(tmp)=="")
            {
                alert("2번 설문에 기타를 선택하시면  직접 입력 하여 주십시오.");
                $("[name='survey2t']").focus();
                return;
            }
        }
        ans+=tmp+",";

        // 333
        tmp=$("[name='survey3']:checked").val();
        if(tmp==undefined)
        {
            alert("3번 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

         // 444
        tmp=$("[name='survey4']:checked").val();
        if(tmp==undefined)
        {
            alert("4번 설문 교육운영의 전체적인 만족도에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
        // 555
        tmp=$("[name='survey5']:checked").val();
        if(tmp==undefined)
        {
            alert("온라인 교육 콘텐츠 수준 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
        // 666
        tmp=$("[name='survey6']:checked").val();
        if(tmp==undefined)
        {
            alert("온라인 교육 콘텐츠의 다양성 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
     // 777
        tmp=$("[name='survey7']:checked").val();
        if(tmp==undefined)
        {
            alert("온라인 교육 콘텐츠의 전문성 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
     // 888
        tmp=$("[name='survey8']:checked").val();
        if(tmp==undefined)
        {
            alert("온라인 교육 콘텐츠의 차별성 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
     // 999
        tmp=$("[name='survey9']:checked").val();
        if(tmp==undefined)
        {
            alert("학습 진행 편의성  설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
     // 101010
        tmp=$("[name='survey10']:checked").val();
        if(tmp==undefined)
        {
            alert("교육운영의 전체적인 만족도 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
     // 11111
        tmp=$("[name='survey11']:checked").val();
        if(tmp==undefined)
        {
            alert("학습 참여 독려, 결과보고 등 회원관리 시스템 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
        
     // 1212121
        tmp=$("[name='survey12']:checked").val();
        if(tmp==undefined)
        {
            alert("고객 상담시 운영자의 친절성 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
     // 1313131
        tmp=$("[name='survey13']:checked").val();
        if(tmp==undefined)
        {
            alert("학습 문의에 대한 답변의 신속성·정확성 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
     // 14
        tmp=$("[name='survey14']:checked").val();
        if(tmp==undefined)
        {
            alert("튜터 활동 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
       
        tmp="";
        // 15
        $("[name='survey15']").each(function(){
            if($(this).attr("checked"))
                tmp+=$(this).val()+"@";
        });
        if(tmp==undefined || tmp=="")
        {
            alert("6번 방송분야 문제에 답을 하지 않았습니다.");
            return;
        }
        tmp=tmp.substr(0,tmp.length-1);
        ans+=tmp+",";
        
        
        tmp=$("[name='survey15t']").val();
        if(tmp=="")
        {
            alert("희망하는 신규과정이에 답을 하지 않았습니다.");
            $("[name='survey15t']").focus();
            return;
        }
        ans+=tmp+",";

        tmp="";
        
        
        // 16
        $("[name='survey16']").each(function(){
            if($(this).attr("checked"))
                tmp+=$(this).val()+"@";
        });
        if(tmp==undefined || tmp=="")
        {
            alert("6번 문화콘텐츠 분야  답을 하지 않았습니다.");
            return;
        }
        tmp=tmp.substr(0,tmp.length-1);
        ans+=tmp+",";
        
        tmp=$("[name='survey16t']").val();
        if(tmp=="")
        {
            alert("희망하는 신규과정이에 답을 하지 않았습니다.");
            $("[name='survey16t']").focus();
            return;
        }
        ans+=tmp+",";

        tmp="";
        
     // 17
        $("[name='survey17']").each(function(){
            if($(this).attr("checked"))
                tmp+=$(this).val()+"@";
        });
        if(tmp==undefined || tmp=="")
        {
            alert("6번 게임분야 문제에 답을 하지 않았습니다.");
            return;
        }
        tmp=tmp.substr(0,tmp.length-1);
        ans+=tmp+",";
        
        tmp=$("[name='survey17t']").val();
        if(tmp=="")
        {
            alert("희망하는 신규과정이에 답을 하지 않았습니다.");
            $("[name='survey17t']").focus();
            return;
        }
        ans+=tmp+",";

        tmp="";
        
        // 18
        tmp=$("[name='survey18']:checked").val();
        if(tmp==undefined)
        {
            alert("상품권 설문에 답을 하지 않았습니다.");
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

<body >
<form name="nform1" method="post" id="nform1" action="/servlet/controller.community.CommunityRiskServlet">
    <input type = "hidden" name = "p_process" value="eventInsert">
    <input type = "hidden" name = "p_ans" >
    <input type = "hidden" name = "p_userid" value="<%=v_userid%>">
    <input type = "hidden" name = "p_gubun_inja" value="<%=v_gubun%>">
<div id="gudieline">
	<div class="pagetitle"><img src="/tmp/images/title.gif" alt="사이버콘텐츠아카데미 교육 서비스 만족도 조사"></div>
	<div class="indate">☞ 설문조사기간 : 2012년 5월 24일(목) ~ 6월 1일(금)</div>
	
	<div class="question">1. 사이버콘텐츠아카데미의 온라인 강의 수강 경험은 몇 번이십니까?</div>
	<ul class="answer">
		<li><input name="survey1" type="radio" value="1"> 없 음</li>
		<li><input name="survey1" type="radio" value="2"> 1~2회</li>
		<li><input name="survey1" type="radio" value="3"> 3~4회</li>
		<li><input name="survey1" type="radio" value="4"> 5회 이상</li>
	</ul>
	
	<div class="question">2. 사이버콘텐츠아카데미를 접하게 된 경위는?</div>
	<ul class="answer">
		<li><input type="radio" name="survey2" value="1"> 오프라인 특강</li>
		<li><input type="radio" name="survey2" value="2"> 포털사이트 검색</li>
		<li><input type="radio" name="survey2" value="3"> 블로그 및 카페 홍보</li>
		<li><input type="radio" name="survey2" value="4"> 신문기사 등 언론 홍보</li>
		<li><input type="radio" name="survey2" value="5"> 주의 권유/지인 추천</li>
		<li><input type="radio" name="survey2" value="6"> 기타 ( <input type="text" name="survey2t"> )</li>
	</ul>
	
	<div class="question">3. 사이버콘텐츠아카데미에 대한 전체적인 만족도는?</div>
	<ul class="answer">
		<li><input type="radio" name="survey3" value="1"> 매우만족</li>
		<li><input type="radio" name="survey3" value="2"> 만족</li>
		<li><input type="radio" name="survey3" value="3"> 보통</li>
		<li><input type="radio" name="survey3" value="4"> 불만</li>
		<li><input type="radio" name="survey3" value="5"> 매우불만</li>
	</ul>
	
	<div class="question">4. 사이버콘텐츠아카데미 온라인 교육 콘텐츠에 대한 만족도는?</div>
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
			<td class="question">교육운영의 전체적인 만족도</td>
			<td><input type="radio" name="survey4" value="1"></td>
			<td><input type="radio" name="survey4" value="2"></td>
			<td><input type="radio" name="survey4" value="3"></td>
			<td><input type="radio" name="survey4" value="4"></td>
			<td><input type="radio" name="survey4" value="5"></td>
		</tr>
		<tr>
			<td class="question">온라인 교육 콘텐츠 수준 (강사진, 내용 등)</td>
			<td><input type="radio" name="survey5" value="1"></td>
			<td><input type="radio" name="survey5" value="2"></td>
			<td><input type="radio" name="survey5" value="3"></td>
			<td><input type="radio" name="survey5" value="4"></td>
			<td><input type="radio" name="survey5" value="5"></td>
		</tr>
		<tr>
			<td class="question">온라인 교육 콘텐츠의 다양성</td>
			<td><input type="radio" name="survey6" value="1"></td>
			<td><input type="radio" name="survey6" value="2"></td>
			<td><input type="radio" name="survey6" value="3"></td>
			<td><input type="radio" name="survey6" value="4"></td>
			<td><input type="radio" name="survey6" value="5"></td>
		</tr>
		<tr>
			<td class="question">온라인 교육 콘텐츠의 전문성</td>
			<td><input type="radio" name="survey7" value="1"></td>
			<td><input type="radio" name="survey7" value="2"></td>
			<td><input type="radio" name="survey7" value="3"></td>
			<td><input type="radio" name="survey7" value="4"></td>
			<td><input type="radio" name="survey7" value="5"></td>
		</tr>
		<tr>
			<td class="question">온라인 교육 콘텐츠의 차별성</td>
			<td><input type="radio" name="survey8" value="1"></td>
			<td><input type="radio" name="survey8" value="2"></td>
			<td><input type="radio" name="survey8" value="3"></td>
			<td><input type="radio" name="survey8" value="4"></td>
			<td><input type="radio" name="survey8" value="5"></td>
		</tr>
		<tr>
			<td class="question">학습 진행 편의성 (강의화면 구성, 음성 등)</td>
			<td><input type="radio" name="survey9" value="1"></td>
			<td><input type="radio" name="survey9" value="2"></td>
			<td><input type="radio" name="survey9" value="3"></td>
			<td><input type="radio" name="survey9" value="4"></td>
			<td><input type="radio" name="survey9" value="5"></td>
		</tr>
	</table>
	
	<div class="question">5. 사이버콘텐츠아카데미의 교육 운영 서비스에 대한 만족도는?</div>
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
			<td class="question">교육운영의 전체적인 만족도</td>
			<td><input type="radio" name="survey10" value="1"></td>
			<td><input type="radio" name="survey10" value="2"></td>
			<td><input type="radio" name="survey10" value="3"></td>
			<td><input type="radio" name="survey10" value="4"></td>
			<td><input type="radio" name="survey10" value="5"></td>
		</tr>
		<tr>
			<td class="question">학습 참여 독려, 결과보고 등 회원관리 시스템</td>
			<td><input type="radio" name="survey11" value="1"></td>
			<td><input type="radio" name="survey11" value="2"></td>
			<td><input type="radio" name="survey11" value="3"></td>
			<td><input type="radio" name="survey11" value="4"></td>
			<td><input type="radio" name="survey11" value="5"></td>
		</tr>
		<tr>
			<td class="question">고객 상담시 운영자의 친절성</td>
			<td><input type="radio" name="survey12" value="1"></td>
			<td><input type="radio" name="survey12" value="2"></td>
			<td><input type="radio" name="survey12" value="3"></td>
			<td><input type="radio" name="survey12" value="4"></td>
			<td><input type="radio" name="survey12" value="5"></td>
		</tr>
		<tr>
			<td class="question">학습 문의에 대한 답변의 신속성·정확성</td>
			<td><input type="radio" name="survey13" value="1"></td>
			<td><input type="radio" name="survey13" value="2"></td>
			<td><input type="radio" name="survey13" value="3"></td>
			<td><input type="radio" name="survey13" value="4"></td>
			<td><input type="radio" name="survey13" value="5"></td>
		</tr>
		<tr>
			<td class="question">튜터 활동</td>
			<td><input type="radio" name="survey14" value="1"></td>
			<td><input type="radio" name="survey14" value="2"></td>
			<td><input type="radio" name="survey14" value="3"></td>
			<td><input type="radio" name="survey14" value="4"></td>
			<td><input type="radio" name="survey14" value="5"></td>
		</tr>
	</table>
	
	<div class="question">6. 향후 사이버콘텐츠아카데미 온라인 강의를 통해 학습하고 싶은 분야와 주제가 있다면?</div>
	<div class="tip">- 본 리스트에서 본인 <u>희망하는 과목 2개</u>를 체크해 주시기 바랍니다.</div>
	<div class="groupname"><방송분야></div>
	<table class="selectcourse" cellpadding="0" cellspacing="0">
		<colgroup><col width="30"><col width="293"><col width="30"><col width="*"></colgroup>
		<tr>
			<th colspan="4">과정</th>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey15" value="1"></td>
			<td>DSLR촬영 실무</td>
			<td class="alignC"><input type="checkbox" name="survey15" value="2"></td>
			<td>Edius 사용법</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey15" value="3"></td>
			<td>Qbase 사용법</td>
			<td class="alignC"><input type="checkbox" name="survey15" value="4"></td>
			<td>Protools 사용법</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey15" value="5"></td>
			<td>조명 실무</td>
			<td class="alignC"></td>
			<td></td>
		</tr>
	</table>
		
	<div class="tip"><b>※ 체크리스트와는 별도로 본인이 희망하는 신규과정이 있다면 작성해 주세요.<BR>
	&nbsp;&nbsp;&nbsp;없으면  "없음"이라고 적어주세요.</b></BR></div>
	
	
	<div class="usermemo"><textarea name="survey15t"></textarea></div>
	
	<div class="groupname"><문화콘텐츠 분야></div>
	<table class="selectcourse" cellpadding="0" cellspacing="0">
		<colgroup><col width="30"><col width="293"><col width="30"><col width="*"></colgroup>
		<tr>
			<th colspan="4">과정</th>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey16" value="1"></td>
			<td>미국드라마의 이해</td>
			<td class="alignC"><input type="checkbox" name="survey16" value="2"></td>
			<td>K-POP성공사례 분석</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey16" value="3"></td>
			<td>전자책 이해/제작/유통</td>
			<td class="alignC"><input type="checkbox" name="survey16" value="4"></td>
			<td>테마파크 기획 및 사례분석</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey16" value="5"></td>
			<td>글로벌 만화시장 분석</td>
			<td class="alignC"><input type="checkbox" name="survey16" value="6"></td>
			<td>뮤지컬과정</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey16" value="7"></td>
			<td>마케터가 알아야할 애니메이션 제작과정</td>
			<td class="alignC"><input type="checkbox" name="survey16" value="8"></td>
			<td>머천다이징 사업 이해/실무</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey16" value="9"></td>
			<td>국내 유아애니메이션 사례</td>
			<td class="alignC"><input type="checkbox" name="survey16" value="10"></td>
			<td>해외 마케팅 인력 양성과정</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey16" value="11"></td>
			<td>피칭 및 pt스킬 업</td>
			<td class="alignC"><input type="checkbox" name="survey16" value="12"></td>
			<td>1인 공작소 과정</td>
		</tr>
	</table>
	
	
	<div class="tip"><b>※ 체크리스트와는 별도로 본인이 희망하는 신규과정이 있다면 작성해 주세요.<BR>
	&nbsp;&nbsp;&nbsp;없으면  "없음"이라고 적어주세요.</b></BR></div>
	
	
	<div class="usermemo"><textarea name="survey16t"></textarea></div>
	
	<div class="groupname"><게임분야></div>
	<table class="selectcourse" cellpadding="0" cellspacing="0">
		<colgroup><col width="30"><col width="293"><col width="30"><col width="*"></colgroup>
		<tr>
			<th colspan="2">과목명</th>
			<th colspan="2">과목명</th>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey17" value="1"></td>
			<td>케쥬얼게임 제작(퍼즐/웹/소셜)</td>
			<td class="alignC"><input type="checkbox" name="survey17" value="2"></td>
			<td>캐릭터 애니메이션 추출/출력</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey17" value="3"></td>
			<td>라이브기획</td>
			<td class="alignC"><input type="checkbox" name="survey17" value="4"></td>
			<td>길 찾기</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey17" value="5"></td>
			<td>게임UI 또는 UX</td>
			<td class="alignC"><input type="checkbox" name="survey17" value="6"></td>
			<td>Direct3D 11과 셰이더 4.0</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey17" value="7"></td>
			<td>게임수학(확률과 통계)</td>
			<td class="alignC"><input type="checkbox" name="survey17" value="8"></td>
			<td>윈도우 서버 관리</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey17" value="9"></td>
			<td>스크립트언어(액션스크립트)</td>
			<td class="alignC"><input type="checkbox" name="survey17" value="10"></td>
			<td>TCP/IP 채팅서버 만들기</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey17" value="11"></td>
			<td>GFX를 활용한 게임인터페이스 제작</td>
			<td class="alignC"><input type="checkbox" name="survey17" value="12"></td>
			<td>P2P로 레이싱게임 만들기</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey17" value="13"></td>
			<td>게임 밸런스 디자인(수치)</td>
			<td class="alignC"><input type="checkbox" name="survey17" value="14"></td>
			<td>MySQL로 실전 DB구축해보기</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey17" value="15"></td>
			<td>에자일 방법론</td>
			<td class="alignC"><input type="checkbox" name="survey17" value="16"></td>
			<td>1000개의 캐릭터 동기화</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey17" value="17"></td>
			<td>게임등급과 저작권</td>
			<td class="alignC"><input type="checkbox" name="survey17" value="18"></td>
			<td>FPS 캐릭터 동기화</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey17" value="19"></td>
			<td>게임인터페이스(GFX)</td>
			<td class="alignC"><input type="checkbox" name="survey17" value="20"></td>
			<td>서버시스템 설계</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey17" value="21"></td>
			<td>게임이펙트 실무기초 강좌</td>
			<td class="alignC"><input type="checkbox" name="survey17" value="22"></td>
			<td>Unity3D 프로그래밍</td>
		</tr>
		<tr>
			<td class="alignC"><input type="checkbox" name="survey17" value="23"></td>
			<td>고급 프로그래밍</td>
			<td class="alignC"></td>
			<td></td>
		</tr>
	</table>
	<div class="tip"><b>※ 체크리스트와는 별도로 본인이 희망하는 신규과정이 있다면 작성해 주세요.<BR>
	&nbsp;&nbsp;&nbsp;없으면  "없음"이라고 적어주세요.</b></BR></div>
	<div class="usermemo"><textarea name="survey17t"></textarea></div>
	<div class="event">
		<table style="width:100%; border-collapse:collapse; margin-top:316px;" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center"><input type="radio" style="margin-left:25px;" name="survey18" value="1"></td>
				<td align="center"><input type="radio" style="margin-right:25px;" name="survey18" value="2"></td>
			</tr>
		</table>
	</div>
	<div class="footer"><a href="javascript:gotoSubmit();" onfocus="this.blur();"><img src="/tmp/images/btn_upload.gif"  border="0" /></a></div>
</div>
</body>
</html>
<%}%>