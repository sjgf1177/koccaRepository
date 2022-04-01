<%
//**********************************************************
//  1. 제      목: 과정 조회화면
//  2. 프로그램명: za_Subject_L.jsp
//  3. 개      요: 과정 조회화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 12
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.text.NumberFormat" %>

<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "com.credu.library.FormatDate" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%@ page import = "com.credu.common.GetCodenm" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    DataBox dbox = (DataBox)request.getAttribute("memberInfo");
    DataBox addInfobox = (DataBox)request.getAttribute("addInfo");
    List<DataBox> schoolList = (List<DataBox>) addInfobox.getObject("schoolList");
    List<DataBox> licenseList = (List<DataBox>) addInfobox.getObject("licenseList");
    List<DataBox> careerList = (List<DataBox>) addInfobox.getObject("careerList");
    List<DataBox> trainingINFOList = (List<DataBox>) addInfobox.getObject("trainingINFOList");

    NumberFormat s_fmt = NumberFormat.getInstance();                //통화단위 ,표시
    int v_totgetpoint = box.getInt("p_getpoint");
    int v_totusepoint = box.getInt("p_usepoint");
    int v_totwaitpoint = box.getInt("p_waitpoint");
    int v_availablepoint = v_totgetpoint - v_totusepoint - v_totwaitpoint;
    String v_availablepoint_text = s_fmt.format(v_availablepoint);

    ArrayList list1 = null;
    ArrayList list2 = null;
    ArrayList list3 = null;

    list1 = (ArrayList)request.getAttribute("selectHavePointList");     //보유포인트
    list2 = (ArrayList)request.getAttribute("selectStoldPointList");    //적립포인트
    list3 = (ArrayList)request.getAttribute("selectUsePointList");      //사용포인트

    String p_process = box.getString("p_process");
    if(p_process.equals("MemberModify")) {
        out.print("<script>alert('수정 되었습니다');self.close();opener.whenSelection('go');</script>");
    }
%>
<html>
<head>
<title>회원검색</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<style>
    .coursePopup { display:none; width:332px; height:160px; background:#fff; color:#000; position:fixed; top:277px; left:338px; text-align:center; border:2px solid #000; }
    #oMobileInfo { position:absolute; top:34px; left:14px; width:96%; overflow:hidden;}

    .d_center { text-align:center;}
    .d_left { text-align:left; }
</style>

<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>

<script type="text/javascript">
<!--
	$(document).ready(function(){
		
		// 이메일 수신여부 변경
		$('#p_ismailing').change(fnChangeMailing);
	});

    function modify() {
        var form = document.form1;
        //form.target = opener;
        form.action = "/servlet/controller.system.MemberSearchServlet";
        form.p_process.value = "MemberModify";
        form.submit();
        //self.close();
    }

    /**
     * 회원 정보 변경
     * @param type - 
     * @param p_membergubun - 
     */
    function fnUpdateMemberInfo(type, p_membergubun) {
        var title = "";
        var sqlNum = "";
        switch (type) {
            case 1 : title = "회원구분"; sqlNum="member.membergubun.update"; break;
            case 2 : title = "회원상태"; sqlNum="member.state.update"; break;
            case 3 : title = "회원이동전화번호"; sqlNum="memberhandphone.update"; break;
            case 4 : title = "회원이메일주소"; sqlNum="memberemail.update"; break;
        }

        if(!confirm(title+"을 변경하시겠습니까?")) {
            return;
        }

        $.post("/servlet/controller.common.AjaxServlet",
            {
                sqlNum : sqlNum,rerurnFunction: "resultSimpleSave",
                p_userid : '<%= dbox.getString("d_userid") %>',
                p_membergubun : p_membergubun,
                p_grcode : '<%= box.getString("p_grcode") %>',
                p_handphone : $("#oNewMobileNo1").val() + "-" + $("#oNewMobileNo2").val() + "-" + $("#oNewMobileNo3").val(),
                p_email : $("#oNewEmail1").val() + "@" + $("#oNewEmail2").val()
            },
            function(data) {
                $("#ajaxDiv").html(data);
                if ( $("#oNewMobileNo1").val() != "" ) {
                    $("#oUserHandphone").text($("#oNewMobileNo1").val() + "-" + $("#oNewMobileNo2").val() + "-" + $("#oNewMobileNo3").val());
                    $("#oNewMobileNo1").val("");
                    $("#oNewMobileNo2").val("");
                    $("#oNewMobileNo3").val("");
                }
                if ( $("#oNewEmail1").val() != "" ) {
                	$("#oUserEmail").text($("#oNewEmail1").val() + "@" + $("#oNewEmail2").val());
                    $("#oNewEmail1").val("");
                    $("#oNewEmail2").val("");
                }
            }
        );
    }

    function resultSimpleSave(data) {
        if (data.result == "true") {
            alert("수정했습니다.");
        } else {
            alert("수정이 실패하였습니다.\n" + data.result);
        }
    }

    //Tab 선택시 동작
    function showTab(clickTab) {
        g_showTab(clickTab, 4);
    }

    function g_showTab(tabNo, count) {

        var clickTabNo = new Number(tabNo);
        var tabCount   = new Number(count);

        if( clickTabNo == 0 || tabCount == 0 ) return;

        for(i=1; i<=tabCount; i++) {
            div = document.all["tab_"+i];

            if(!div) return;

            if( clickTabNo == i ) {
                // 활성화된 tab 클릭시 return
                if(div.style.display == "") return;
                div.style.display = ""
            } else {
                // 선택되지 않는 탭들은 깜빡임 방지.
                if(div.style.display != "none") {
                    div.style.display = "none";
                }
            }
        }
    }

    /**
     * SMS 전송
     */
    function fnSendSMS() {
        ff = document.form1;
        ff.p_touch.value = "09";

        window.self.name = "memberList";
        open_window("openSMS","","100","100","620","354");
        document.form1.target = "openSMS";
        document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
        document.form1.p_process.value = 'SendSMS';
        document.form1.submit();
    }

    /**
     * 비밀번호 초기화
     */
    function fnResetPassword() {
        var form = document.form1;
        //form.target = opener;
        form.action = "/servlet/controller.system.MemberSearchServlet";
        form.p_process.value = "ResetUserPwd";
        form.submit();
    }

    function fnOpenMobileInfoPopup() {
        $("#oMobileInfoPopup").show();
    }

    function fnCloseMobileInfoPopup() {
        $("#oMobileInfoPopup").hide();
    }

    function fnChangeUserMobile() {
        if ($("#oNewMobileNo1").val() === "" || $("#oNewMobileNo2").val() === "" || $("#oNewMobileNo3").val() === "" ) {
            alert("이동전화번호를 입력하세요.");
            return;
        } else {
            fnUpdateMemberInfo(3, '');
            fnCloseMobileInfoPopup();
        }
    }
    
    function fnOpenEmailInfoPopup() {
        $("#oEmailInfoPopup").show();
    }

    function fnCloseEmailInfoPopup() {
        $("#oEmailInfoPopup").hide();
    }

    function fnChangeUserEmail() {
        if ($("#oNewEmail1").val() === "" || $("#oNewEmail2").val() === "" ) {
            alert("에메일 주소를 입력하세요.");
            return;
        } else {
            fnUpdateMemberInfo(4, '');
            fnCloseEmailInfoPopup();
        }
    }
    
    // 이메일 수신여부 변경
    function fnChangeMailing(){
    	if(confirm("메일수신여부를 변경하시겠습니까?")){
    		var form = document.form1;
            form.action = "/servlet/controller.system.MemberSearchServlet";
            form.p_process.value = "ChangeUserMailing";
            form.submit();
    	}else{
    		$('#p_ismailing').val($('#p_ismailing').val() == "Y" ? "N" : "Y");
    	}
    }
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<div id="ajaxDiv"></div>

<form name="form1" method="post">
    <input type="hidden" name="p_userid" value="<%=dbox.getString("d_userid")%>" />
    <input type="hidden" name="p_process" />
    <input type="hidden" name="p_touch" />
    <input type="hidden" name="p_checks" value="<%=dbox.getString("d_userid")%>" />
    <input type="hidden" name="s_grcode" value="<%=box.getString("p_grcode")%>" />

<table width="720" border="0" cellspacing="0" cellpadding="0" height="400">
    <tr>
        <td align="center" valign="top">
            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td><img src="/images/admin/system/unite_title18.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->
            <br />

            <!-- 소제목 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
                    <td class="sub_title">개인정보조회</td>
                </tr>
                <tr>
                    <td height=6></td>
                    <td height=6></td>
                </tr>
            </table>
            <!-- -소제목 끝-- //-->

            <!-- 회원 정보 시작 //-->
            <table border="0" cellspacing="1" cellpadding="0" class="table_out" style="border:1px solid;">
                <tr>
                    <td colspan="4" class="table_top_line"></td>
                </tr>
                <tr>
                    <td class="table_title">회원구분</td>
                    <td class="table_02_2" colspan="3">
                        <kocca_select:select sqlNum="0001" name="p_membergubun" param="0029"    selectedValue='<%= dbox.get("d_membergubun") %>' isLoad="true" onChange="fnUpdateMemberInfo(1, this.value)" type="1" all="none"/>
                    </td>
                </tr>
                <tr>
                    <td class="table_title">아이디</td>
                    <td class="table_02_2"><%=dbox.getString("d_userid")%></td>
                    <td class="table_title">비밀번호</td>
                    <td class="table_02_2"><a href="javascript:fnResetPassword();">비밀번호초기화</a> ****</td>
                </tr>
                <tr>
                    <td class="table_title">성명(한글)</td>
                    <td class="table_02_2"><%=dbox.getString("d_name")%></td>
                    <td class="table_title">성명(영문)</td>
                    <td class="table_02_2"><%=dbox.getString("d_eng_name")%></td>
                </tr>
                <tr>
                    <td class="table_title">이메일</td>
                    <td class="table_02_2">
                    <span id="oUserEmail"><%=dbox.getString("d_email")%></span>
                        <a href="javascript:fnOpenEmailInfoPopup();"><span>메일주소 변경</span></a>
                    </td>
                    <td class="table_title">주민등록번호</td>
                    <td class="table_02_2">
            <%
                    String v_resno = dbox.getString("d_resno");
                    String v_resno_disp = "";
                    if (v_resno != null) {
                        if (v_resno.length() == 14) {
                            v_resno_disp = v_resno.substring(0,7) + v_resno.substring(7,8) + "******";
                        }
                    }
            %>
                        <%=v_resno%>
                    </td>
                </tr>
                <tr>
                    <td width="20%" class="table_title">우편번호(자택)</td>
                    <td colspan="3" class="table_02_2"><%=dbox.getString("d_post1")%>-<%=dbox.getString("d_post2")%></td>
                </tr>
                <tr>
                    <td width="20%" class="table_title">주소(자택)</td>
                    <td colspan="3" class="table_02_2"><%=dbox.getString("d_addr")%> <%=dbox.getString("d_addr2")%></td>
                </tr>
                <tr>
                    <td width="20%" class="table_title">우편번호(회사)</td>
                    <td colspan="3" class="table_02_2"><%=dbox.getString("d_comp_post1")%>-<%=dbox.getString("d_comp_post2")%></td>
                </tr>
                <tr>
                    <td width="20%" class="table_title">주소(회사)</td>
                    <td colspan="3" class="table_02_2"><%=dbox.getString("d_comp_addr1")%> <%=dbox.getString("d_comp_addr2")%></td>
                </tr>
                <tr>
                    <td class="table_title">자택전화번호</td>
                    <td class="table_02_2"><%=dbox.getString("d_hometel")%></td>
                    <td class="table_title">이동전화번호</td>
                    <td class="table_02_2">
                        <span id="oUserHandphone"><%=dbox.getString("d_handphone")%></span>
                        <a href="javascript:fnSendSMS();"><img src="/images/admin/button/btn_sms.gif" border="0" valign="bottom"></a>
                        <br/>
                        <br/>
                        <a class="board_btn" href="javascript:fnOpenMobileInfoPopup();"><span>휴대폰번호 변경</span></a>
                    </td>
                </tr>
                <tr>
                    <td class="table_title">회사전화번호</td>
                    <td class="table_02_2"><%=dbox.getString("d_comptel")%></td>
                    <td class="table_title">소속</td>
                    <td class="table_02_2"><%=dbox.getString("d_comptext")%></td>
                </tr>
                <tr>
                    <td class="table_title">최종학력</td>
                    <td class="table_02_2"><%=GetCodenm.get_codenm("0069", dbox.getString("d_degree"))%></td>
                    <td class="table_title">직업</td>
                    <td class="table_02_2"><%=GetCodenm.get_codenm("0070", dbox.getString("d_jikup"))%></td>
                </tr>
                <tr>
                    <td class="table_title">메일수신여부</td>
                    <td class="table_02_2">
                    	<%-- <%if (dbox.getString("d_ismailing").equals("N")){%>수신거부<%}else{%>수신<%}%> --%>
                    	<select id="p_ismailing" name="p_ismailing">
                    		<option value="Y" <%="Y".equals(dbox.getString("d_ismailing")) ? "selected" : ""%>>수신</option>
                    		<option value="N" <%="N".equals(dbox.getString("d_ismailing")) ? "selected" : ""%>>수신거부</option>
                    	</select>
                    </td>
                    <td class="table_title">뉴스레터수신여부</td>
                    <td class="table_02_2"><%if (dbox.getString("d_islettering").equals("Y")){%>수신<%}else{%>수신거부<%}%></td>
                </tr>
                <tr>
                    <td class="table_title">연락처공개여부</td>
                    <td class="table_02_2"><%if (dbox.getString("d_isopening").equals("Y")){%>공개<%}else{%>비공개<%}%></td>
                    <td class="table_title">회원상태</td>
                    <td class="table_02_2">
                        <select name="p_state" onChange="fnUpdateMemberInfo(2, this.value)">
                            <option value="Y" <% if(dbox.getString("d_statecd").equals("Y")){ out.print("selected"); }%>>정상</option>
                            <option value="N" <% if(dbox.getString("d_statecd").equals("N")){ out.print("selected"); }%>>탈퇴</option>
                        </select>
                    </td>
                </tr>
                <!--
                <tr>
                    <td class="table_title">가입경로</td>
                    <td class="table_02_2"><%=GetCodenm.get_codenm("0060", dbox.getString("d_registgubun"))%></td>
                    <td class="table_title">실명인증여부</td>
                    <td class="table_02_2"><%if (dbox.getInt("d_validation")==1){%>인증<%}else{%>미인증<%}%></td>
                </tr>
                -->
                <tr>
                    <td class="table_title">최초가입일</td>
                    <td class="table_02_2"><%=FormatDate.getFormatDate(dbox.getString("d_indate"),"yyyy/MM/dd hh:mm:ss")%></td>
                    <td class="table_title">최종수정일</td>
                    <td class="table_02_2"><%=FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyy/MM/dd hh:mm:ss")%></td>
                </tr>
            </table>
            <!-- 회원 정보 끝 //-->
        </td>
    </tr>
    <tr style="display: none;">
        <td>
            <br />
            <br />
            <h2><img src="/images/portal/myclass/h2_tit6.gif" alt="보유포인트" class="fl_l" /><p class="category">보유 포인트 : <strong><%= v_availablepoint_text %></strong> Point</p></h2>
            <div id="tab_1" >
            <ul class="tabwrap mg_t35">
                <li><a href="javascript:showTab(1)" class="tab_myclass tab_myclass_on"><span>보유포인트</span></a></li>
                <li><a href="javascript:showTab(2)" class="tab_myclass"><span>적립포인트</span></a></li>
                <li><a href="javascript:showTab(3)" class="tab_myclass"><span>사용포인트</span></a></li>
            </ul>

            <table class="board_list">
                <colgroup>
                    <col width="240px" />
                    <col width="150px" />
                    <col width="150px" />
                    <col width="150px" />
                </colgroup>
                <thead>
                    <tr class="myclass">
                        <th><p>변경날자</p></th>
                        <th><p>적립포인트</p></th>
                        <th><p>사용포인트</p></th>
                        <th class="end"><p>보유포인트</p></th>
                    </tr>
                </thead>
                <tbody>
<%
    for(int i = 0; i < list1.size(); i++) {

        DataBox dbox1 = (DataBox)list1.get(i);

        String v_tid = dbox1.getString("d_tid");
        String v_getdate = dbox1.getString("d_getdate");
        String v_getpoint = s_fmt.format(dbox1.getInt("d_getpoint"));
        String v_usepoint = s_fmt.format(dbox1.getInt("d_usepoint"));
        String v_usepoint_text =  dbox1.getString("d_usepoint");
        String v_lefrpoint = s_fmt.format(dbox1.getInt("d_lefrpoint"));
        String v_expiredate = dbox1.getString("d_expiredate");

        v_getdate = FormatDate.getFormatDate(v_getdate,"yyyy.MM.dd");
%>
                    <tr onmouseover="className='over'" onmouseout="className=' '">
                        <td class="num"><%= v_getdate %></td>
                        <td class="num ta_r"><%= v_getpoint %></td>
                        <td class="num ta_r"><%= "0".equals(v_usepoint) ? v_usepoint_text : v_usepoint %></td>
                        <td class="num ta_r"><strong><%= v_lefrpoint %></strong></td>
                    </tr>
<%
    }

    if(list1.size() == 0 ) {
%>
                    <tr>
                        <td height="3" colspan="4" align="center">포인트 내역이 없습니다.</td>
                    </tr>
<%  }%>
                </tbody>
            </table>

            </div>
            <div id="tab_2" STYLE="display:none">

            <ul class="tabwrap mg_t35">
                <li><a href="javascript:showTab(1)" class="tab_myclass"><span>보유포인트</span></a></li>
                <li><a href="javascript:showTab(2)" class="tab_myclass tab_myclass_on"><span>적립포인트</span></a></li>
                <li><a href="javascript:showTab(3)" class="tab_myclass"><span>사용포인트</span></a></li>
            </ul>

            <table class="board_list">
                <colgroup>
                    <col width="110px" />
                    <col width="190px" />
                    <col width="130px" />
                    <col width="130px" />
                    <col width="130px" />
                </colgroup>
                <thead>
                <tr class="myclass">
                    <th><p>적립일자</p></th>
                    <th><p>적립포인트</p></th>
                    <th><p>적립내용</p></th>
                    <th><p>수강료</p></th>
                    <th class="end"><p>결제금액</p></th>
                </tr>
                </thead>
                <tbody>
<%
    for(int i = 0; i < list2.size(); i++) {
        DataBox dbox2 = (DataBox)list2.get(i);

        String v_tid = dbox2.getString("d_tid");
        String v_getdate = dbox2.getString("d_getdate");
        String v_title = dbox2.getString("d_title");
        String v_getpoint = s_fmt.format(dbox2.getInt("d_getpoint"));
        String v_subjprice = s_fmt.format(dbox2.getInt("d_subjprice"));
        String v_price = s_fmt.format(dbox2.getInt("d_price"));

        v_getdate = FormatDate.getFormatDate(v_getdate,"yyyy.MM.dd");
%>
                    <tr onmouseover="className='over'" onmouseout="className=' '">
                        <td class="num"><%= v_getdate %></td>
                        <td class="num ta_r"><%= v_getpoint %></td>
                        <td><%= v_title %></td>
                        <td class="num ta_r"><%= v_subjprice %></td>
                        <td class="num ta_r"><strong><%= v_price %></strong></td>
                    </tr>
<%
    }


    if(list1.size() == 0 ){%>
                    <tr>
                        <td height="20" colspan="5" align="center">포인트 내역이 없습니다.</td>
                    </tr>
<%  }%>
                </tbody>
            </table>

            </div>
            <div id="tab_3" STYLE="display:none">

            <ul class="tabwrap mg_t35">
                <li><a href="javascript:showTab(1)" class="tab_myclass"><span>보유포인트</span></a></li>
                <li><a href="javascript:showTab(2)" class="tab_myclass"><span>적립포인트</span></a></li>
                <li><a href="javascript:showTab(3)" class="tab_myclass tab_myclass_on"><span>사용포인트</span></a></li>
            </ul>

            <table class="board_list">
                <colgroup>
                    <col width="110px" />
                    <col width="305px" />
                    <col width="95px" />
                    <col width="90px" />
                    <col width="90px" />
                </colgroup>
                <thead>
                <tr class="myclass">
                    <th><p>사용일자</p></th>
                    <th><p>과정명</p></th>
                    <th><p>사용포인트</p></th>
                    <th><p>수강료</p></th>
                    <th class="end"><p>결제금액</p></th>
                </tr>
                </thead>
                <tbody>
<%
    for(int i = 0; i < list3.size(); i++) {

        DataBox dbox3 = (DataBox)list3.get(i);

        String v_tid = dbox3.getString("d_tid");
        String v_usedate = dbox3.getString("d_usedate");
        String v_title = dbox3.getString("d_title");
        String v_usepoint = s_fmt.format(dbox3.getInt("d_usepoint"));
        String v_subjprice = s_fmt.format(dbox3.getInt("d_subjprice"));
        String v_price = s_fmt.format(dbox3.getInt("d_price"));

        v_usedate = FormatDate.getFormatDate(v_usedate,"yyyy.MM.dd");
%>
                    <tr onmouseover="className='over'" onmouseout="className=' '">
                        <td class="num"><%= v_usedate %></td>
                        <td class="ta_l"><%= v_title %></td>
                        <td class="num ta_r"><%= v_usepoint %></td>
                        <td class="num ta_r"><%= v_subjprice %></td>
                        <td class="num ta_r"><strong><%= v_price %></strong></td>
                    </tr>
<%
    }

    if(list1.size() == 0 ){%>
                    <tr>
                        <td height="20" colspan="5" align="center">포인트 내역이 없습니다.</td>
                    </tr>
<%
    }
%>
                </tbody>
            </table>

            </div>

        </td>
    </tr>
    <tr style="display: none;">
        <td>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_army.gif" alt="병역구분" class="fl_l" /></h3>
            <table class="write">
                <tr>
                    <td class="intxt end">
                        <span><input type="radio" name="p_militarystatus" value="" checked/>해당없음&nbsp;&nbsp;<kocca_select:select sqlNum="0001" name="p_militarystatus" styleClass="mg_l10" param="0093" selectedValue='<%= addInfobox.get("d_militarystatus") %>' isLoad="true" type="2.2"/></span>
                        <span class="mg_l20">
                            복무기간 <%= addInfobox.get("d_militarystart") %> ~ <%= addInfobox.get("d_militaryend") %>
                        </span>
                        <span class="mg_l15">면제사유 : <%= addInfobox.get("d_militarymemo") %></span>
                    </td>
                </tr>
            </table>

<%
    if(schoolList!=null && schoolList.size() >0) {
%>
            <h3 class="pop_tit mg_b9">교육경험<img src="/images/portal/common/stit_applystep.gif" alt="신청과정" class="fl_l" /></h3>
            <table class="ac_list">
                <colgroup>
                    <col width="114px" />
                    <col width="101px" />
                    <col width="107px" />
                    <col />
                    <col width="101px" />
                </colgroup>
                <thead>
                    <tr>
                        <th>학교명</th>
                        <th>졸업구분</th>
                        <th>졸업(예정)일</th>
                        <th>학과/전공</th>
                        <th>소재지</th>
                    </tr>
                </thead>
                <tbody>
<%
        for(DataBox schoolListBox : schoolList) {
%>
                    <tr>
                        <td><%= schoolListBox.get("d_schoolname") %></td>
                        <td><%= schoolListBox.get("d_status") %></td>
                        <td><%= schoolListBox.get("d_send") %></td>
                        <td><%= schoolListBox.get("d_major") %></td>
                        <td><%= schoolListBox.get("d_place") %></td>
                    </tr>
<%
        }
%>
                </tbody>
            </table>
<%
    }
%>


<%
    if(schoolList!=null && schoolList.size() >0) {
%>
            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_certificate.gif" alt="자격증" class="fl_l" /></h3>
            <table class="ac_list">
                <colgroup>
                    <col width="221px" />
                    <col width="175px" />
                    <col />
                </colgroup>
                <thead>
                    <tr>
                        <th>자격증명</th>
                        <th>취득일</th>
                        <th>발행처</th>
                    </tr>
                </thead>
                <tbody>
<%
        for(DataBox licenseListBox : licenseList) {
%>
                    <tr>
                        <td><%= licenseListBox.get("d_licensename") %></td>
                        <td><%= licenseListBox.getDate("d_getdate", "-") %></td>
                        <td><%= licenseListBox.get("d_publish") %></td>
                    </tr>
<%
        }
%>
                </tbody>
            </table>
<%
    }

    if(trainingINFOList!=null && trainingINFOList.size() >0) {
%>
            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_trainnum.gif" alt="연수지명번호" class="fl_l" /></h3>
            <table class="ac_list">
                <colgroup>
                    <col width="221px" />
                    <col width="175px" />
                    <col />
                </colgroup>
                <thead>
                    <tr>
                        <th>과정명</th>
                        <th>연수지명번호</th>
                        <th>소속교육청</th>
                    </tr>
                </thead>
                <tbody>
<%
        for(DataBox trainingINFOListBox : trainingINFOList) {
%>
                    <tr>
                        <td><%= trainingINFOListBox.get("d_subjnm") %></td>
                        <td><%= trainingINFOListBox.get("d_trainingnum") %></td>
                        <td><%= trainingINFOListBox.get("d_fromoffice") %></td>
                    </tr>
<%
        }
%>
                </tbody>
            </table>
<%
    }

    if(careerList!=null && careerList.size() >0) {
%>
            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_certificate.gif" alt="자격증" class="fl_l" /></h3>
            <table class="ac_list">
                <colgroup>
                    <col width="67px" />
                    <col width="108px" />
                    <col width="135px" />
                    <col />
                    <col width="150px" />
                    <col width="80px" />
                </colgroup>
                <thead>
                <tr>
                    <th>직업</th>
                    <th>직종</th>
                    <th>회사명</th>
                    <th>직무/직급</th>
                    <th>재직기간</th>
                    <th>재직구분 </th>
                </tr>
                </thead>
                <tbody id="careerListbox">
<%
        for(DataBox careerListbox : careerList) {
%>
                    <tr>
                        <td><%= careerListbox.get("d_jikup") %></td>
                        <td><%= careerListbox.get("d_jikjong") %></td>
                        <td><%= careerListbox.get("d_officename") %></td>
                        <td><%= careerListbox.get("d_position") %></td>
                        <td><%= careerListbox.getDate("d_workstart", ".") %>~<%= careerListbox.getDate("d_workend", ".") %></td>
                        <td><%= careerListbox.get("d_workstatus") %></td>
                    </tr>
<%
        }
%>
                </tbody>
            </table>
<%
    }
%>

            <h3 class="pop_tit"><img src="/images/portal/common/stit_resume.gif" alt="자기소개서" /></h3>
            <table class="write">
                <colgroup>
                    <col width="18%" />
                    <col />
                </colgroup>
                <tr>
                    <th>본인소개</th>
                    <td><textarea class="inbox" style="width:97%; height:30px;" readonly><%= addInfobox.get("d_intro") %></textarea></td>
                </tr>
                <tr>
                    <th>지원동기</th>
                    <td><textarea class="inbox" style="width:97%; height:30px;" readonly><%= addInfobox.get("d_motive") %></textarea></td>
                </tr>
                <tr>
                    <th>향후계획</th>
                    <td><textarea class="inbox" style="width:97%; height:30px;" readonly><%= addInfobox.get("d_vision") %></textarea></td>
                </tr>
                <tr>
                    <th>기타</th>
                    <td><textarea class="inbox" style="width:97%; height:30px;" readonly><%= addInfobox.get("d_etc") %></textarea></td>
                </tr>
            </table>
            <br />
            <!-- 버튼 시작 //-->
            <table cellpadding="0" cellspacing="0" border="0" width="97%">
                <tr>
                    <td bgcolor="FFFFFF"><IMG src width="0" height="0"></td>
                    <td width="120">
                        <a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif"  border=0></a>
                    </td>
                </tr>
            </table>
            <!-- 버튼 끝 //-->
        </td>
    </tr>
    <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>

<div id="oMobileInfoPopup" class="coursePopup">
    <div style="padding: 8px 0 0 15px; font-weight:bold; text-align:left;">이동전화번호 변경</div>
    <div id="oMobileInfo">
        <form id="oUserMobileInfoForm" name="userMobileInfoForm" method="post" onsubmit="return false;">
            <input type="hidden" id="oUserid" name="userid" value="<%= dbox.getString("d_userid") %>" />
        <table border="0" cellpadding="0" cellspacing="0" class="searchTable" style="width:96%;">
            <colgroup>
                <col style="width:40%;" />
                <col style="width:60%;" />
            </colgroup>
            <tr>
                <th scope="cols">현재 이동전화번호</th>
                <td style="text-align:left; padding:8px;">
                    <%= dbox.getString("d_handphone") %>
                </td>
            </tr>
            <tr>                    
                <th scope="cols">새 이동전화번호</th>
                <td style="text-align:left; padding:8px;">
                    <input type="text" id="oNewMobileNo1" name="newMobileNo1" style="width:30px;" maxlength="3" value="" onkeyup="numeric_chk(this);" /> -
                    <input type="text" id="oNewMobileNo2" name="newMobileNo2" style="width:40px;" maxlength="4" value="" onkeyup="numeric_chk(this);" /> -
                    <input type="text" id="oNewMobileNo3" name="newMobileNo3" style="width:40px;" maxlength="4" value="" onkeyup="numeric_chk(this);" />
                </td>
            </tr>
            <tr>
                <td style="text-align:center;" colspan="2">
                    <a href="javascript:fnChangeUserMobile();"><img src="/images/admin/button/btn_modify.gif" border="0" /></a>
                    <a href="javascript:fnCloseMobileInfoPopup();"><img src="/images/admin/button/btn_close.gif" border="0" /></a>
                </td>
            </tr>
        </table>
        </form>
    </div>
</div>

<div id="oEmailInfoPopup" class="coursePopup">
    <div style="padding: 8px 0 0 15px; font-weight:bold; text-align:left;">메일주소 변경</div>
    <div id="oEmailInfo">
        <form id="oUserEmailInfoForm" name="userEmailInfoForm" method="post" onsubmit="return false;">
            <input type="hidden" id="oUserid" name="userid" value="<%= dbox.getString("d_userid") %>" />
        <table border="0" cellpadding="0" cellspacing="0" class="searchTable" style="width:96%;">
            <colgroup>
                <col style="width:40%;" />
                <col style="width:60%;" />
            </colgroup>
            <tr>
                <th scope="cols">현재 메일주소</th>
                <td style="text-align:left; padding:8px;">
                    <%= dbox.getString("d_email") %>
                </td>
            </tr>
            <tr>                    
                <th scope="cols">새 메일주소</th>
                <td style="text-align:left; padding:8px;">
                    <input type="text" id="oNewEmail1" name="newEmail1" style="width:70px;" value=""  /> @
                    <input type="text" id="oNewEmail2" name="newEmail2" style="width:80px;" value=""  /> 
                    
                </td>
            </tr>
            <tr>
                <td style="text-align:center;" colspan="2">
                    <a href="javascript:fnChangeUserEmail();"><img src="/images/admin/button/btn_modify.gif" border="0" /></a>
                    <a href="javascript:fnCloseEmailInfoPopup();"><img src="/images/admin/button/btn_close.gif" border="0" /></a>
                </td>
            </tr>
        </table>
        </form>
    </div>
</div>


</body>
</html>
