<%@ page import="com.credu.library.DataBox" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%
    DataBox dbox        = (DataBox)request.getAttribute("member");

    String v_name="";
    String v_userid="";
    String v_chk="";
    String v_sw="";
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
    {
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>한국콘텐츠아카데미</title>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type='text/javascript' src='/script/jquery.form.js'></script>

<style type="text/css">
    body {
        margin-left: 0px;
        margin-top: 0px;
        margin-right: 0px;
        margin-bottom: 0px;
    }
    .style1 {
        color: #333333;
        font-size: 12px;
        line-height: 15pt;
    }
    .style3 {
        color: #333333;
        font-weight: bold;
        font-size: 12px;
    }
    .style4 {
        color: #333333;
        font-size: 12px;
    }    
</style>

<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
    var question="";
    question+="교육 내용이 업무에 도움이 된다교육 커리큘럼이 체계적이다교육 내용(난이도 등)이 적정하다<span style='color:red'>[유료교육 수강생만 응답]</span><br>교육 내용에 비해 교육비용이 적정하다";
    question+="교육 내용(콘텐츠)이 전문적이다<span style='color:red'>[강사의 강의 내용이 있는 경우만 응답]</span><br>강사의 강의 스킬이 우수하다(강의 기법)교육생의 니즈에 맞는 과정개발을 위해<br>노력한다온라인 사용 환경 개선을 위해<br>노력한다";
//    question+="고객님께서는 한국콘텐츠진흥원의 <b>온라인<br>교육 관련 업무처리 절차와 직원의 태도</b>에 대해 전반적으로 얼마나</br>만족하셨습니까?";
    question+="교육 신청 방법 및 절차가 간편하다교육 관련 업무 처리가 정해진 기간 내에<br>수행된다교육 관련 공지사항을 잘 전달한다도움말, 질의답변, FAQ 등 사용자 편의를 위한 메뉴를 제공한다수강생의 요구사항을 수렴하기 위하여<br>노력한다";
    question+="담당튜터는 학습독려 및 상담을 잘 한다온라인 교육 사이트는 접속이 잘 된다온라인 교육 사이트 메뉴는 이용하기 편리하게 구성되어 있다온라인 교육 사이트는 원하는 메뉴를<br>클릭했을 때 페이지 이동 속도가 빠르다";
    question+="온라인 교육 사이트는 끊어지거나 에러가 없이 안정적이다색상, 글씨체 등 온라인 교육 사이트 디자인이 세련되었다한 화면에 제시되는 강의 내용의 양은 적정한 수준이다";
//    question+="고객님께서는 공익기관으로서 한국콘텐츠진흥원의 사회적 책임과 역할 수행에  대해 얼마나  만족하십니까?";
//    question+="<b>[무료 교육 수강생에게만 질문]<b><br>한국콘텐츠진흥원은 온라인 교육 사이트을 상업적인 목적으로 활용하지 않는다한국콘텐츠진흥원의 온라인 교육사업은 콘텐츠산업 인재양성에 기여한다";
//    question+="한국콘텐츠진흥원의 온라인 교육사업은 콘텐츠산업의  전반적인 발전에 기여한다교육 과정 중 취득한 고객의 정보 보호를 위하여 노력한다";
    var question1="고객님께서는 앞서 평가해 주신 측면(교육내용, 직원의 응대, 이용절차, 시설 환경 등)을 모두 고려할 때 한국콘텐츠진흥원의 <b>온라인 교육 서비스에 대해 전반적으로 얼마나 만족하셨습니까?</b>";
    question1+="고객님께서 들이신 시간, 노력, 비용 등을 고려하여 기대하셨던 것에  비해  한국콘텐츠진흥원의 <b>온라인 교육서비스에 얼마나 만족하셨습니까?</b>";
    question1+="교육 내용, 직원의 응대, 이용절차, 시설 환경, 공공적 측면 등을 모두 고려할 때, 고객님께서는 온라인 교육 서비스를 받으시면서  한국콘텐츠진흥원에 대해 <b>더 좋게 생각하게 되었습니까?</b>";
    
    var nowPage=1; //현재 페이지
    var pageView=5; //페이지당 표현할 수
    var totalPage=Math.ceil((question.split("").length+1)/pageView); //총 페이지 수
    var ans= new Array(question.split("").length+1); //답을 저장 하는 변수
    var btn_sw=false;
    var presentSelect="";
    
    $(document).ready(function(){
        for(var ii=0;ii<ans.length;ii++)
        {
            ans[ii]=-1;
        }

        displayState();
        addQuestion();
    });

    //이전 페이지로 이동
    function btn_Pre()
    {
        if(nowPage==1)
        {
            alert("첫 페이지 입니다.");
            return;
        }
        nowPage--;
        contentClear();
        addQuestion();
        displayState();
        ansRestore();
    }

    function next2()
    {
        for(var i=21;i<24;i++)
        {
            if(ans[i]==undefined || ans[i]==null || ans[i]==-1)
            {
                alert(i+"번째 문항을 선택하지 않았습니다");
                return;
            }
        }
        if(presentSelect=="")
        {
            alert("기프티콘을 선택 하여 주십시오");
            return;
        }

        insert();
        presentSave();
    }

    //다음 페이지로 이동
    function btn_Next()
    {
        if(nowPage==(totalPage-1))
        {
            for(var i=16;i<21;i++)
            {
                if(ans[i]==undefined || ans[i]==null || ans[i]==-1)
                {
                    alert(i+"번째 문항을 선택하지 않았습니다");
                    return;
                }
            }
               $(".a1b1").hide();
               $(".a1b3").show();
    //            alert("마지막 페이지 입니다.");
            return;
        }

        var start=nowPage*pageView-pageView+1;
        var end=nowPage*pageView+1;

        for(var i=start;i<end;i++)
        {
            if(i==4 || i==6)
            { }
            else
            {
                if(ans[i]==undefined || ans[i]==null || ans[i]==-1)
                {
                    alert(i+"번째 문항을 선택하지 않았습니다");
                    return;
                }
            }
        }

        nowPage++;
        contentClear();
        addQuestion();
        displayState();
        ansRestore();
    }

    //문제 만들기
    function addQuestion()
    {
        var tr=document.createElement("tr");
        var questionSplit= question.split("");
        var start=nowPage*pageView-pageView;
        var end=nowPage*pageView;
        
        for(var i=start;i<end;i++)
        {
            var td=document.createElement("td");
            $(td).attr("height","70px");
            $(td).attr("align","center");
            $(td).attr("width","20px");
            $(td).css("backgroundColor","e8f1f5");
            $(td).attr("class","style3");
            $(td).text((i+1));
            $(tr).append(td);
            td=document.createElement("td");
            $(td).attr("width","10px");
            $(td).css("backgroundColor","fbfbfb");
            $(tr).append(td);
            td=document.createElement("td");
            $(td).attr("align","left");
            $(td).attr("width","250px");
            $(td).css("backgroundColor","fbfbfb");
            $(td).attr("class","style1");

            if(questionSplit[i]!=null)
            {
                $(td).html(questionSplit[i]);
                $(tr).append(td);
                for(var j=0;j<7;j++)
                {
                    td=document.createElement("td");
                    $(td).attr("align","center");

                    var opt=document.createElement("<input name='a"+(i+1)+"' type='radio' onclick='ansSave("+(i+1)+","+j+")'/>");
                    $(td).append(opt);
                    $(tr).append(td);
                }

                $("#content").append(tr);
                tr=document.createElement("tr");
            }
        }
    }

    //본문 내용 깨끗이
    function contentClear()
    {
        $("#content").html("");
    }

    //현재 상태를 화면에 표시
    function displayState()
    {
        $("#questionAndTotal").text(nowPage+" / "+ 5);
    }

    // int1: 문제번호 int2: 선택한 객체 순서
    function ansSave(int1,int2)
    {
        ans[int1]=int2;
    }

    //이전 혹은 다음 버튼을 클릭시 해당 페이지에 한번이라도 답을 체크 하였다면 원복 시키는 함수
    function ansRestore()
    {
        var start=nowPage*pageView-pageView;
        var end=nowPage*pageView+1;

        for(var i=start;i<end;i++)
        {
            if(ans[i]!=undefined && ans[i]!=null && ans[i]!=-1)
            {
                var obj=$("[name='a"+i+"']").parent().parent();
                $("input:eq("+ans[i]+")",obj).attr("checked","checked");
            }
        }
    }

    //글등록
    function insert() {
        var ansTmp="";

//        if(!chkVal())
//            return;

        if(btn_sw)
            return;

        for(var i=1;i<ans.length;i++)
        {
            ansTmp+=ans[i]+",";
        }

        ansTmp=ansTmp.substr(0,ansTmp.length-1);

        btn_sw=true;
        $("#nform1").ajaxSubmit({
           url:'/servlet/controller.community.CommunityRiskServlet',
           type:'post',
            data:{p_ans:ansTmp},
           success: function(){
//                $(".a1b1").hide();
//                $(".a1b2").show();
            }
        });
    }

    function presentSave()
    {
        if(presentSelect=="")
        {
            alert("기프티콘을 선택 하여 주십시오");
            return;
        }
        
        $.ajax({
           url:'/servlet/controller.community.CommunityRiskServlet',
           type:'post',
            data:{p_ans:presentSelect,p_process:'eventPresent',p_userid:'<%=v_userid%>'},
           success: function(){
               alert("설문이 완료되었습니다.\n 참여해주셔서 감사합니다");
               window.close();
            }
        });
    }

    function savePresent(str)
    {
        presentSelect=str;
    }

    //답을 다 선택 했는지 여부
    function chkVal()
    {
        var chk=false;
        var len=question.split("").length;
                
        for(var i=1;i<len;i++)
        {
            if(ans[i]==undefined && ans[i]==null)
            {
                alert(i+" 번 답을 선택 하지 않았습니다.");
                return chk;
            }
        }

        return true;
    }
</SCRIPT>

<body >
<form name="nform1" method="post" id="nform1">
    <input type = "hidden" name = "p_process" value="eventInsert">
    <input type = "hidden" name = "p_userid" value="<%=v_userid%>">

    <table border="0" cellpadding="0" cellspacing="0" width="850px" style="margin:0 auto;" class="a1b1">
        <tr>
            <td><img src="/tmp/images/top.gif" width="850" height="85" /></td>
          </tr>

        <tr>
            <td align="center" valign="top" background="/tmp/images/bg.gif"><br><br>
                <table width="810" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="30">&nbsp;</td>
                      </tr>
                      <tr class="a1b1">
                        <td>
                            <table width="810" border="0" cellpadding="0" cellspacing="1" bgcolor="0096ce">
                          <tr>
                            <td bgcolor="#FFFFFF">
                                <table width="808" border="0" cellspacing="0" cellpadding="0">
                              <tr >
                                <td height="48" colspan="3" align="center" background="/tmp/images/table_top_bg.gif" class="style3">평가요소</td>
                                <td align="center" background="/tmp/images/table_top_bg.gif" class="style4">전혀그렇지않다<br />(매우불만족)</td>
                                <td align="center" background="/tmp/images/table_top_bg.gif"><span class="style4">그렇지않다<br />(불만족)</span></td>
                                <td align="center" background="/tmp/images/table_top_bg.gif"><span class="style4">그렇지않은편이다<br />(약간불만족)</span></td>
                                <td align="center" background="/tmp/images/table_top_bg.gif"><span class="style4">반반이다<br />(보통)</span></td>
                                <td align="center" background="/tmp/images/table_top_bg.gif"><span class="style4">그런편이다<br />(약간만족)</span></td>
                                <td align="center" background="/tmp/images/table_top_bg.gif"><span class="style4">그렇다<br />(만족)</span></td>
                                <td align="center" background="/tmp/images/table_top_bg.gif"><span class="style4">정말그렇다<br />(매우만족)</span></td>
                              </tr>
                              <tbody id="content">
                              </tbody>
                            </table></td>
                          </tr>
                        </table></td>
                      </tr>
                      <tr>
                        <td><table width="810" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="382" height="50">&nbsp;</td>
                            <td width="43"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image2','','/tmp/images/btn_ok_o.gif',1)"></a></td>
                            <td align="right" class="a1b1"><span class="style1" id="questionAndTotal" ></span></td>
                            <td width="75" align="right" class="style1 a1b1" >
                                <img src="/tmp/images/btn_next.gif" name="Image4" width="43" height="24" border="0" id="Image4" style="CURSOR:POINTER" onclick="btn_Next()"/>
                            </td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                    <br />
                    <br /></td>
                  </tr>
                  <tr>
                    <td height="1" bgcolor="d2d2d2"></td>
                  </tr>
    </table>


    <table width="850" border="0" align="center" cellpadding="0" cellspacing="0" class="a1b3" style="margin:0 auto;display:none">
        <tr>
            <td><img src="/tmp/images/top.gif" width="850" height="85" /></td>
        </tr>
        <tr>
            <td align="center" valign="top" background="/tmp/images/bg.gif"><br />
        <br />
            <table width="810" border="0" cellspacing="0" cellpadding="0">
            <tr>
            <td height="50" align="left"><span class="style1"><b>21.</b> 회원님께서는 앞서 평가해 주신 측면(교육내용, 직원의 응대, 이용절차, 시설 환경 등)을 모두
            고려할 때 한국콘텐츠진흥원의 <br />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>온라인 교육 서비스에 대해 전반적으로 얼마나  만족하셨습니까?</b></span></td>
            </tr>
            <tr>
            <td><table width="810" border="0" cellpadding="8" cellspacing="1" bgcolor="c7dae2">
            <tr>
            <td align="center" bgcolor="e8f1f5"><span class="style3">매우 불만족</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">불만족</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">약간 불만족</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">보통</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">약간 만족 </span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">만족</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">매우 만족 </span></td>
            </tr>
            <tr>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a24" onclick="ansSave(21,0)"> </td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a24" onclick="ansSave(21,1)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a24" onclick="ansSave(21,2)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a24" onclick="ansSave(21,3)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a24" onclick="ansSave(21,4)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a24" onclick="ansSave(21,5)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a24" onclick="ansSave(21,6)"></td>
            </tr>
            </table>          </td>
            </tr>
            <tr>
            <td height="30">&nbsp;</td>
            </tr>
            <tr>
            <td height="30" align="left"><span class="style1"><b>22.</b> 회원님께서 들이신 <b>시간, 노력, 비용 등을 고려하여 기대하셨던 것에  비해</b> 한국콘텐츠진흥원의 온라인 교육서비스에 얼마나 <br />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;만족하셨습니까?
            </span></td>
            </tr>
            <tr>
            <td><table width="810" border="0" cellpadding="8" cellspacing="1" bgcolor="c7dae2">
            <tr>
            <td align="center" bgcolor="e8f1f5"><span class="style3">매우 불만족</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">불만족</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">약간 불만족</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">보통</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">약간 만족 </span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">만족</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">매우 만족 </span></td>
            </tr>
            <tr>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a25" onclick="ansSave(22,0)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a25" onclick="ansSave(22,1)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a25" onclick="ansSave(22,2)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a25" onclick="ansSave(22,3)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a25" onclick="ansSave(22,4)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a25" onclick="ansSave(22,5)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a25" onclick="ansSave(22,6)"></td>
            </tr>
            </table></td>
            </tr>
            <tr>
            <td height="30">&nbsp;</td>
            </tr>
            <tr>
            <td height="30" align="left"><span class="style1"><b>23.</b> 교육 내용, 직원의 응대, 이용절차, 시설 환경, 공공적 측면 등을 모두 고려할 때, 회원님께서는 온라인 교육 서비스를 받으시면서  <br />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;한국콘텐츠진흥원에 대해 <b>더 좋게 생각하게 되었습니까?</b></span></td>
            </tr>
            <tr>
            <td><table width="810" border="0" cellpadding="8" cellspacing="1" bgcolor="c7dae2">
            <tr>
            <td align="center" bgcolor="e8f1f5"><span class="style3">매우 불만족</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">불만족</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">약간 불만족</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">보통</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">약간 만족 </span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">만족</span></td>
            <td align="center" bgcolor="e8f1f5"><span class="style3">매우 만족 </span></td>
            </tr>
            <tr>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a26" onclick="ansSave(23,0)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a26" onclick="ansSave(23,1)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a26" onclick="ansSave(23,2)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a26" onclick="ansSave(23,3)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a26" onclick="ansSave(23,4)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a26" onclick="ansSave(23,5)"></td>
            <td align="center" bgcolor="#FFFFFF"><input type="radio" name="a26" onclick="ansSave(23,6)"></td>
            </tr>
            </table>
            </td>
            </tr>
                <tr>
                    <td height="15px"></td>
                </tr>
            <tr>
                <td colspan="7" align="right"><span style=" color: #333333;font-size: 12px;line-height: 15pt;"> 5 / 5 </span></td>
            </tr>
            <tr>
                <td height="15px"></td>
            </tr>
        </table>
    </td>
        </tr>
        <tr>
            <td align="center" valign="top" background="/tmp/images/bg.gif">
                <table width="810" border="0" cellpadding="8" cellspacing="1" bgcolor="c7dae2">

          <tr>
            <td align="center" bgcolor="#FFFFFF">
                <table width="95%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="50" colspan="4" align="left"><p class="style1 style5">※ 기프티콘 발송을 위해 원하시는 상품을 선택하여  주시기 바랍니다.(7월 29일(목) 발송 예정)<br />
                &nbsp;&nbsp;&nbsp;&nbsp;상품 수령을 위하여 개인정보를 확인해주시기 바랍니다.</p></td>
                </tr>

              <tr>
                <td width="25%" align="center"><table width="180" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align="center"><img src="/tmp/images/gift_baskin.gif" width="150" height="150" /></td>
                  </tr>
                  <tr>
                    <td height="40" align="center" class="style3">베스킨라빈스 교환권<br />
                      (1만원)</td>
                  </tr>
                  <tr>
                      <td><input type="radio" name="p_present" onclick="savePresent('0')"></td>
                  </tr>
                  <tr>
                    <td height="30" align="center">&nbsp;</td>
                  </tr>
                </table></td>
                <td width="25%" align="center"><table width="180" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align="center"><img src="/tmp/images/gift_dunkin.gif" width="150" height="150" /></td>
                  </tr>
                  <tr>
                    <td height="40" align="center" class="style3">던킨 교환권<br />
                      (1만원)</td>
                  </tr>
                    <tr>
                      <td><input type="radio" name="p_present" onclick="savePresent('1')"></td>
                  </tr>
                  <tr>
                    <td height="30" align="center">&nbsp;</td>
                  </tr>
                </table></td>
                <td width="25%" align="center"><table width="180" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align="center"><img src="/tmp/images/gift_tous.gif" width="150" height="150" /></td>
                  </tr>
                  <tr>
                    <td height="40" align="center" class="style3">뚜레쥬르 교환권<br />
                      (1만원)</td>
                  </tr>
                    <tr>
                      <td><input type="radio" name="p_present" onclick="savePresent('2')"></td>
                  </tr>
                  <tr>
                    <td height="30" align="center">&nbsp;</td>
                  </tr>
                </table></td>
                <td width="25%" align="center"><table width="180" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align="center"><img src="/tmp/images/gift_oil.gif" width="150" height="150" /></td>
                  </tr>
                  <tr>
                    <td height="40" align="center" class="style3">SK 주유권<br />
                      (1만원)</td>
                  </tr>
                    <tr>
                      <td><input type="radio" name="p_present" onclick="savePresent('3')"></td>
                  </tr>
                  <tr>
                    <td height="30" align="center">&nbsp;</td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td height="40" colspan="4" align="left" class="style1"></td>
                </tr>
                <tr>
                    <td align="center" colspan="4"> <img src="/tmp/images/btn_ok_o.gif" name="Image4"  border="0" style="CURSOR:POINTER" onclick="next2()"/>
                    </td>
                </tr>
            </table>
            </td>
        </tr>
        <tr>
            <td height="1" bgcolor="d2d2d2"></td>
          </tr>
        </table>
     </td>
        </tr>
        </table>   
</form>
</body>
</html>
<%}%>