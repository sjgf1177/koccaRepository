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
<title>KOCCA 한국콘텐츠아카데미 - 설문조사</title>
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
div#gudieline div.indate { text-align:left; margin-top:30px; line-height:18px; border:1px solid #bbbbbb; padding:10px; }
div#gudieline div.msg { margin-top:10px; text-align:right; color:#d96620; }
div#gudieline div.grouptitle { font-size:16px; font-weight:bold; margin-top:40px; margin-bottom:-20px; }
div#gudieline div.question { margin-top:40px; margin-bottom:10px; margin-left:20px; line-height:18px; }
div#gudieline div.tip { padding:50px; font-size:16px; color:#525252; font-weight:bold; text-align:center; }
div#gudieline ul.answer { width:660px; margin-left:44px; }
div#gudieline ul.answer li { float:left; padding-right:50px; margin-top:10px; color:#626262; position:relative; }
div#gudieline ul.answer li input[type='text'] { border:none; width:200px; color:#4f71b0; border-bottom:1px dotted #bbbbbb; }
div#gudieline div.question input[type='text'].date { border:none; border-bottom:1px dotted #bbbbbb; width:40px; text-align:center; }
div#gudieline div.question input[type='text'].num { width:40px; text-align:center; border:0px; }
div#gudieline ul.answer li input[type='text'].typein { width:180px; text-align:center; border:none; border-bottom:1px dotted #bbbbbb; }
div#gudieline ul.answer li input[type='text'].long { width:560px; text-align:left; border:none; border-bottom:1px dotted #bbbbbb; }
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
div#gudieline div.usermemo textarea { margin-left:38px; width:540px; height:60px; border:1px solid #dddddd; padding:10px; font-family:"gulim"; }
div#gudieline div.footer { margin-bottom:30px; text-align:center; }
div#gudieline div.footer input[type='submit'] { padding:10px 40px 10px 40px; width:300px; border:1px solid #323232; background-color:#626262; color:#ffffff; font-family:"gulim"; font-weight:bold; }
div#gudieline div.event { margin-left:14px; margin-top:40px; background-image:url('images/event.gif'); background-repeat:no-repeat; width:644px; height:476px; border:1px solid #dddddd; }
div#gudieline table.inabout { width:660px; margin-top:20px; }
div#gudieline table.inabout th { border:1px solid #bbbbbb; background-color:#eeeeee; }
div#gudieline table.inabout td { border:1px solid #bbbbbb; padding-top:8px; padding-bottom:8px; }
div#gudieline table.inabout td input[type='text'] { width:90%; border:0px; padding-left:5px; }
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
    
        tmp=$("[name='survey1']").val();
        if(tmp=="")
        {
            alert("이름을 입력하여 주시십시요.");
            return;
        }
        ans=tmp+",";

        // 222
        tmp=$("[name='survey2']").val();
        if(tmp=="")
        {
            alert("핸드폰번호를 입력하여 주시십시요.");
            return;
        }      
        ans+=tmp+","; 

        // 333
        tmp=$("[name='survey3']:checked").val();
        if(tmp==undefined)
        {
            alert("성별에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

         // 444
        tmp=$("[name='survey4']:checked").val();
        if(tmp==undefined)
        {
            alert("업무에 답을 하지 않았습니다.");
            return;
        }
        if(tmp=="4")
        {
            tmp=$("[name='survey4t']").val();
            if($.trim(tmp)=="")
            {
                alert("업무에 기타를 선택하시면  직접 입력 하여 주십시오.");
                $("[name='survey4t']").focus();
                return;
            }
        }
        ans+=tmp+",";
        
        
        // 555
        tmp=$("[name='survey5']").val();
        tmp1=$("[name='survey5t']").val();
        if(tmp=="" && tmp1=="")
        {
            alert(" 해당 분야 경력에 답을 하지 않았습니다.");
            return;
        }
        tmp+="@"+tmp1;
        ans+=tmp+",";
        
        // 666
        tmp=$("[name='survey6']:checked").val();
        if(tmp==undefined)
        {
            alert(" 온라인 강의 수강 경험에 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
     // 777 삭제
        //tmp=$("[name='survey7']").val();
        //if(tmp=="")
        //{
        //    alert("사이버 콘텐츠 아카데미 온라인 강의 수강 경험에 답을 하지 않았습니다.");
        //    return;
        // }
        //ans+=tmp+",";
        
     // 888
        tmp=$("[name='survey8']:checked").val();
        if(tmp==undefined)
        {
            alert(" 사이버콘텐츠아카데미는 쉽게 찾을 수 있었다 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
     // 999
        tmp=$("[name='survey9']:checked").val();
        if(tmp==undefined)
        {
            alert("수강신청을 할 때 나의 커리어 골과 나의 현재 수준에 적합한 교육과정을 쉽게 찾을 수 있었다  설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
     // 101010
        //tmp=$("[name='survey10']:checked").val();
        //if(tmp==undefined)
        //{
        //    alert("현재 수강하고 있는 또는 수강했던 사이버과정은 나의 커리어골과 현재 나의 수준에 적합한 과정이다 설문에 답을 하지 않았습니다.");
        //    return;
        //}
        //ans+=tmp+",";
        
     // 11111
        tmp=$("[name='survey11']:checked").val();
        if(tmp==undefined)
        {
            alert("사이버과정의 운영체계(서버, 운영 프로그램 등)는 안정되어있다 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
        
     // 1212121
        tmp=$("[name='survey12']:checked").val();
        if(tmp==undefined)
        {
            alert("사이버과정의 학습시 지원체계(튜터, Q&A)는 좋았다 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";
        
     // 1313131
        tmp=$("[name='survey13']:checked").val();
        if(tmp==undefined)
        {
            alert("나는 콘텐츠 분야를 공구하고 싶은 친구들에게 사이버콘텐츠아카데미를 권하고 싶다 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey17']").val();
        ans+=tmp+",";        
        
     // 14
        //tmp=$("[name='survey14']:checked").val();
        //if(tmp==undefined)
        //{
        //    alert("사이버콘텐츠아카데미는 나의 커리어골에 맞는 경력개발을 위한 개발 경로를 제시해주고 있어 편리하다 설문에 답을 하지 않았습니다.");
        //    return;
        // }
        //ans+=tmp+",";

        //tmp=$("[name='survey15']:checked").val();
        //if(tmp==undefined)
        //{
        //    alert("나는 꼭 콘텐츠 분야를 공부하고 싶은 친구들에게 사이버콘텐츠아카데미를 권하고 싶다 설문에 답을 하지 않았습니다.");
        //    return;
        //}
        //ans+=tmp+",";

        //tmp=$("[name='survey16']:checked").val();
        //if(tmp==undefined)
        //{
        //    alert("나는 사이버콘텐츠아카데미 과정을 스마트기기로 수강할 수 있다면 교육과정 이수율이 더 높아질 거라고 생각한다 설문에 답을 하지 않았습니다.");
        //    return;
        // }
        //ans+=tmp+",";

        //tmp=$("[name='survey17']").val();
        //if(tmp=="")
        //{
        //    alert("나는 콘텐츠 분야를 공부하고 싶은 친구들에게 사이버과정으로는 사이버콘텐츠아카데미 외에도 이곳을 권하고 싶다 설문에 답을 하지 않았습니다.");
        //    return;
        //}
        //ans+=tmp+",";

        tmp=$("[name='survey18']").val();
        if(tmp=="")
        {
            alert("본 교육과정을 이수하면 이러한 혜택을 주었으면 좋겠다 설문에 답을 하지 않았습니다.");
            return;
        }
        ans+=tmp+",";        
       
        
        tmp=$("[name='survey19']:checked").val();
        if(tmp==undefined)
        {
            alert("사이버콘텐츠아카데미의 교육내용은 콘텐츠산업의 최신의 트렌드와 기술이 반영된 내용들이다. 답을 하지 않았습니다.");            
            return;
        }
        ans+=tmp+",";


        tmp=$("[name='survey20']:checked").val();
        if(tmp==undefined)
        {
            alert("사이버콘텐츠아카데미의 교육내용은 이해하기가 쉽다 설문에 답을 하지 않았습니다.");            
            return;
        }
        ans+=tmp+",";

        tmp=$("[name='survey21']:checked").val();
        if(tmp==undefined)
        {
            alert("사이버콘텐츠아카데미의 교육내용은 현장에서 활용하기가 용이하다 설문에 답을 하지 않았습니다");            
            return;
        }
        ans+=tmp+",";
        

        tmp=$("[name='survey22']:checked").val();
        if(tmp==undefined)
        {
            alert("사이버콘텐츠아카데미는 다양한 교육콘텐츠를 보유하고 있다 설문에 답을 하지 않았습니다");            
            return;
        }
        ans+=tmp+",";

        tmp="";
        tmp=$("[name='survey23']").val();
        tmp1=$("[name='survey23t']").val(); 
        tmp2=$("[name='survey23tt']").val();         
        if(tmp=="")
        {        	
            alert("사이버콘텐츠아카데미에서 향후 아래와 같은 교육과정들이 개설되었으면 좋겠다 설문에 답을 하지 않았습니다");            
            return;
        }
        tmp+="@"+tmp1+"@"+ tmp2;
        ans+=tmp+",";

        tmp=$("[name='survey24']").val();
        if(tmp=="")
        {
            alert("사이버과정의 교육환경 개선을 위한 의견을 자유롭게 기술하여 주십시오 설문에 답을 하지 않았습니다");            
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

<body>
<form name="nform1" method="post" id="nform1" action="/servlet/controller.community.CommunityRiskServlet">
    <input type = "hidden" name = "p_process" value="eventInsert">
    <input type = "hidden" name = "p_ans" >
    <input type = "hidden" name = "p_userid" value="<%=v_userid%>">
    <input type = "hidden" name = "p_gubun_inja" value="<%=v_gubun%>">    
    
 
<div id="gudieline">
	<div class="pagetitle"><img src="/tmp/images/title1.gif" alt="사이버콘텐츠아카데미 교육 서비스 만족도 조사"></div>
	<div class="indate">
		본 조사는 사이버콘텐츠아카데미의 학습자들이 효과적으로 본인에게 적합한 교육과정을 찾고, 쉽고 재미있게 학습하며, 
		현장에서의 활용도가 높은 교육환경을 구축하기 위해 학습자들의 관점에서 사이버교육환경 개선에 대한 요구를 조사, 발췌함에 목적이 있습니다. 
		각 질문에 대한 솔직한 응답 부탁드립니다.
	</div>
	<table class="inabout" cellpadding="0" cellspacing="0">
		<colgroup><col width="100"><col width=""><col width="100"><col width=""></colgroup>
		<tr>
			<th>성명</th>
			<td><input type="text" name="survey1"></td>
			<th>핸드폰번호</th>
			<td><input type="text" name="survey2"></td>
		</tr>
	</table>
	<div class="msg">※ 본 조사에 응답하신 모든 내용은 연구목적으로만 이용됩니다.</div>
	
	<div class="centerline"></div>
	
	<div class="grouptitle">1. 학습자 정보</div>
	<div class="question">Q1. 귀하의 성별은?</div>
	<ul class="answer">
		<li><input name="survey3" type="radio" value="1"> 남</li>
		<li><input name="survey3" type="radio" value="2"> 여</li>
	</ul>
	
	<div class="question">Q2. 귀하의 업무(전공) 분야는?</div>
	<ul class="answer">
		<li><input type="radio" name="survey4" value="1"> 방송분야</li>
		<li><input type="radio" name="survey4" value="2"> 게임분야</li>
		<li><input type="radio" name="survey4" value="3"> 문화분야 (영화,캐릭터,음악,만화,애니 등등) &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</li>
		<li><input type="radio" name="survey4" value="4"> 기타 ( <input name="survey4t" type="text"> )</li>
	</ul>
	
	<div class="question">Q3. 귀하의 해당 분야 경력은? &nbsp; &nbsp; <input type="text" name="survey5" class="date">년 &nbsp; &nbsp; <input name="survey5t" type="text" class="date">개월</div>
	
	<div class="question">Q4. 사이버콘텐츠아카데미 온라인 강의 수강 경험은 몇 번이십니까?</div>
	<ul class="answer">
		<li><input type="radio" name="survey6" value="1"> 1회</li>
		<li><input type="radio" name="survey6" value="2"> 2~3회</li>
		<li><input type="radio" name="survey6" value="3"> 4회 이상</li>
	</ul>
		
	<div class="centerline"></div>
	
	<div class="grouptitle">2. 교육운영 측면</div>
	<div class="question">Q1. 웹상에서 사이버콘텐츠아카데미는 쉽게 찾을 수 있었다.</div>
	<ul class="answer">
		<li><input type="radio" name="survey8" value="1"> 매우 아니다</li>
		<li><input type="radio" name="survey8" value="2"> 아니다</li>
		<li><input type="radio" name="survey8" value="3"> 보통</li>
		<li><input type="radio" name="survey8" value="4"> 그렇다</li>
		<li><input type="radio" name="survey8" value="5"> 매우 그렇다</li>
	</ul>
	
	<div class="question">Q2. 수강신청을 할 때 나의 커리어 골과 나의 현재 수준에 적합한 교육과정을 쉽게 찾을 수 있었다.</div>
	<ul class="answer">
		<li><input type="radio" name="survey9" value="1"> 매우 아니다</li>
		<li><input type="radio" name="survey9" value="2"> 아니다</li>
		<li><input type="radio" name="survey9" value="3"> 보통</li>
		<li><input type="radio" name="survey9" value="4"> 그렇다</li>
		<li><input type="radio" name="survey9" value="5"> 매우 그렇다</li>
	</ul>
	
	<div class="question">Q3. 사이버 과정의 운영 체계(서버, 운영 프로그램 등)는 안정되었다.</div>
	<ul class="answer">
		<li><input type="radio" name="survey11" value="1"> 매우 아니다</li>
		<li><input type="radio" name="survey11" value="2"> 아니다</li>
		<li><input type="radio" name="survey11" value="3"> 보통</li>
		<li><input type="radio" name="survey11" value="4"> 그렇다</li>
		<li><input type="radio" name="survey11" value="5"> 매우 그렇다</li>
	</ul>
	
	<div class="question">Q4. 사이버과정 학습 시 지원체계(튜터, Q&A 등)는 좋았다.</div>
	<ul class="answer">
		<li><input type="radio" name="survey12" value="1"> 매우 아니다</li>
		<li><input type="radio" name="survey12" value="2"> 아니다</li>
		<li><input type="radio" name="survey12" value="3"> 보통</li>
		<li><input type="radio" name="survey12" value="4"> 그렇다</li>
		<li><input type="radio" name="survey12" value="5"> 매우 그렇다</li>
	</ul>
	
	<div class="question">Q5. 나는 콘텐츠 분야를 공부하고 싶은 친구들에게 사이버 콘텐츠아카데미를 권하고 싶다.</div>
	<ul class="answer">
		<li><input type="radio" name="survey13" value="1"> 매우 아니다</li>
		<li><input type="radio" name="survey13" value="2"> 아니다</li>
		<li><input type="radio" name="survey13" value="3"> 보통</li>
		<li><input type="radio" name="survey13" value="4"> 그렇다</li>
		<li><input type="radio" name="survey13" value="5"> 매우 그렇다</li>
	</ul>
	<ul class="answer">
		<li>→ 위의 질문에 추가적으로 본 아카데미 외에 친구들에게 권하고 싶은 또 다른 사이버과정이 있으면 국내외 상관없이 기술하여 주십시오.</li>
	</ul>
	<ul class="answer">
		<li>( <input type="text" name="survey17" class="long"> )</li>
	</ul>
	
	<div class="question">Q6. 본 교육과정을 이수하면 이러한 혜택을 주었으면 좋겠다.</div>
	<ul class="answer">
		<li>( <input type="text" name="survey18" class="long"> )</li>
	</ul>
	
	<div class="centerline"></div>
	
	<div class="grouptitle">3. 교육콘텐츠 측면</div>
	<div class="question">Q1. 사이버콘텐츠아카데미의 교육내용은 콘텐츠산업의 최신의 트렌드와 기술이 반영된 내용들이다.</div>
	<ul class="answer">
		<li><input type="radio" name="survey19" value="1"> 매우 아니다</li>
		<li><input type="radio" name="survey19" value="2"> 아니다</li>
		<li><input type="radio" name="survey19" value="3"> 보통</li>
		<li><input type="radio" name="survey19" value="4"> 그렇다</li>
		<li><input type="radio" name="survey19" value="5"> 매우 그렇다</li>
	</ul>
	
	<div class="question">Q2. 사이버콘텐츠아카데미의 교육내용은 이해하기가 쉽다.</div>
	<ul class="answer">
		<li><input type="radio" name="survey20" value="1"> 매우 아니다</li>
		<li><input type="radio" name="survey20" value="2"> 아니다</li>
		<li><input type="radio" name="survey20" value="3"> 보통</li>
		<li><input type="radio" name="survey20" value="4"> 그렇다</li>
		<li><input type="radio" name="survey20" value="5"> 매우 그렇다</li>
	</ul>
	
	<div class="question">Q3. 사이버콘텐츠아카데미의 교육내용은 현장에서 활용하기가 용이하다.</div>
	<ul class="answer">
		<li><input type="radio" name="survey21" value="1"> 매우 아니다</li>
		<li><input type="radio" name="survey21" value="2"> 아니다</li>
		<li><input type="radio" name="survey21" value="3"> 보통</li>
		<li><input type="radio" name="survey21" value="4"> 그렇다</li>
		<li><input type="radio" name="survey21" value="5"> 매우 그렇다</li>
	</ul>
	
	<div class="question">Q4. 사이버콘텐츠아카데미는 다양한 교육콘텐츠를 보유하고 있다.</div>
	<ul class="answer">
		<li><input type="radio" name="survey22" value="1"> 매우 아니다</li>
		<li><input type="radio" name="survey22" value="2"> 아니다</li>
		<li><input type="radio" name="survey22" value="3"> 보통</li>
		<li><input type="radio" name="survey22" value="4"> 그렇다</li>
		<li><input type="radio" name="survey22" value="5"> 매우 그렇다</li>
	</ul>
	
	<div class="question">Q5. 사이버콘텐츠아카데미에서 향후 아래와 같은 교육과정들이 개설되었으면 좋겠다. (3가지)</div>
	<ul class="answer">
		<li>( <input type="text" class="typein" name="survey23"> , <input type="text" name="survey23t" class="typein"> , <input type="text" name="survey23tt" class="typein"> )</li>
	</ul>
	
	<div class="question">Q6. 사이버과정의 교육환경 개선을 위한 의견을 자유롭게 기술하여 주십시오.</div>
	<ul class="answer">
		<li>( <input type="text" name="survey24" class="long"> )</li>
	</ul>

	<div class="tip">- 감사합니다 -</div>

	<div class="footer"><a href="javascript:gotoSubmit();" onfocus="this.blur();"><img src="/tmp/images/btn_confirm.gif" value="설문제출"></a></div>
</div>
</form>
</body>
</html>    
 
<%}%>