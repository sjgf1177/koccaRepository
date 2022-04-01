<%
//**********************************************************
//  1. 제     목: 수강신청 정보
//  2. 프로그램명: za_PersonalSelect_R
//  3. 개     요: 오프과정신청조회
//  4. 환     경: JDK 1.3
//  5. 버     젼: 1.0
//  6. 작     성: 2010.04.01
//  7. 수     정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%!
    private final String NULL = "";
    private String chkAcceptResult(ArrayList<DataBox> acceptResultList, String p_acceptResult) {
//      if(p_acceptResult.length() == 2 && (p_acceptResult.charAt(0) == 'N' || p_acceptResult.charAt(1) == 'Y' || p_acceptResult.charAt(1) == 'N')) {
//      if(p_acceptResult.length() == 2 && (p_acceptResult.charAt(0) == 'N' || p_acceptResult.charAt(1) == 'N')) {
            return SelectBoxBean.getSelectedString(acceptResultList, "p_acceptResult", p_acceptResult, NULL);
//      }
//      else
//          return SelectBoxBean.getSelectBoxString(acceptResultList, "p_acceptResult", p_acceptResult, NULL);
    }
%>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    DataBox dbox = (DataBox)request.getAttribute("resultbox");
    List<DataBox> schoolList = (List<DataBox>) dbox.getObject("schoolList");
    List<DataBox> licenseList = (List<DataBox>) dbox.getObject("licenseList");
    List<DataBox> careerList = (List<DataBox>) dbox.getObject("careerList");
    DataBox offApplyInfo = (DataBox)request.getAttribute("offApplyInfo");

    String needinput = dbox.get("d_needinput", "10000000000");
    String[]hp = new String[3];
    int index = 0;
    StringTokenizer st = new StringTokenizer(dbox.get("d_handphone"), "-");
    while(st.hasMoreTokens()) {
        hp[index++] = st.nextToken();
    }

    //DEFINED in relation to select END
    ArrayList list      = null;
    ArrayList<DataBox> acceptResultList = null;

    list = (ArrayList)request.getAttribute("resultList");
    acceptResultList = (ArrayList<DataBox>) request.getAttribute("acceptResultList");

    String v_upperclass = box.get("p_upperclass");
    if (box == null) box = RequestManager.getBox(request);
    int i = 0;
    char[]token = needinput.toCharArray();//"11111111111111".toCharArray();//
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>수강신청</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script type="text/javascript" src="/script/portal/common.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language="JavaScript">
<!--//

function whenCancel() {
    window.close();
}

function whenPrint() {
    var today ="";
    var p_userid = "<%= box.get("p_userid") %>";
    var p_subj = "<%= box.get("p_subj") %>";
    var p_subjseq = "<%= box.get("p_subjseq") %>";
    var p_year = "<%= box.get("p_year") %>";
    var p_process = "personalSelectPrint";

    var url = "/servlet/controller.off.OffApprovalAdminServlet?p_process="+ p_process + "&p_printdate=" + today+ "&p_userid="+p_userid + "&p_subj="+p_subj +"&p_year="+p_year+"&p_subjseq="+p_subjseq;
    window.open(url, "printWindow", "width=700, height=700, scrollbars=yes");
    // open_window("", url, "0","0","700","700");
}

//-->

</script>
</head>
<body id="popup"><!-- popup size : 730* -->
<!-- 스크립트영역 -->
<div id="ajaxDiv"></div>

<!-- 스크립트영역종료 -->

<!-- form 시작 -->
<!--
    <div id="pop_header">
        <h1><img src="/images/portal/online/pop_h1_tit3.gif" alt="수강신청" /></h1>
    </div>
 -->
    <div id="pop_container">
        <div id="contentwrap" style="margin-top:50px">
        <!--
            <dl class="guide_list">
                <dt><img src="/images/portal/popup/tit_guide.gif" alt="GUIDE" /></dt>
                <dd>연락처(핸드폰) 및 이메일은 과정운영에 필수 항목이므로 정확히 입력해 주십시오</dd>
                <dd>미디어 교육교사 양성과정 신청자의 경우 학력, 경력 및 자격사항을 입력하셔야 수강 승인이 가능합니다. </dd>
                <dd>교육확정 안내 e-mail을 받으신 후 교육비를 입금해 주시기 바랍니다.</dd>
            </dl>
         -->
<% if(i < token.length && token[0] == '1') { %>
            <h3 class="pop_tit"><img src="/images/portal/common/stit_applystep.gif" alt="신청과정" /></h3>
            <table class="view_bl">
            <colgroup><col width="18%" /><col width="32%" /><col width="18%" /><col width="32%" /></colgroup>
            <tr>
                <th>사진</th>
                <td colspan="3" class="end">
                    <% if(!dbox.getFromDBTypeString("img_path").equals("")) {%>
                        <img src="<%=dbox.getFromDBTypeString("img_path").replace("\\","/")  %>" alt="" width="118" height="154">
                    <%}%>
                </td>
            </tr>

            <tr>
                <th>과정명</th>
                <td colspan="3" class="end"><strong><%= dbox.get("d_subjnm") %></strong></td>
            </tr>
            <tr>
                <th>분야</th>
                <td><%= dbox.get("d_middleclassnm") %></td>
                <th>차수</th>
                <td class="end"><%= dbox.get("d_subjseq") %>차</td>
            </tr>
            <tr>
                <th>신청자명</th>
                <td>
                    <input type="hidden" name="p_name" value="<%=dbox.get("d_name")%>"/>
                    <%= dbox.get("d_name") %>(<%= dbox.get("d_userid") %>)
                </td>
                <th>성별</th>
                <td class="end">
                    <%= dbox.getString("d_sex") %>
                </td>
            </tr>
            <tr>
                <th>수강신청기간</th>
                <td><%= dbox.getDate("d_propstart", "-") %> ~ <%= dbox.getDate("d_propend", "-") %></td>
                <th>과정교육기간</th>
                <td class="end"><%= dbox.getDate("d_edustart", "-") %> ~ <%= dbox.getDate("d_eduend", "-") %></td>
            </tr>
            <tr>
                <th>휴대전화</th>
                <td class="in"><%= hp[0] == null ? "" : hp[0] %>-<%= hp[1] == null ? "" : hp[1] %>-<%= hp[2] == null ? "" : hp[2] %></td>
                <th>이메일</th>
                <td class="in end"><%= dbox.get("d_email") %></td>
            </tr>
            <tr>
                <th>현소속</th>
                <td><%= dbox.getString("d_comptext") %></td>
                <th>교육비</th>
                <td class="end">
                    <input type="hidden" name="p_biyong" value="<%=dbox.get("d_biyong")%>"/>
                    <%= dbox.getCommaInt("d_biyong") %>원
                </td>
            </tr>

<%
        if(token.length > 8 && token[8] == '1') {
%>
            <tr>
                <th>총 경력기간</th>
                <td colspan="3" class="end">
                    <%=dbox.getString("d_tcareeryear") %>년 <%=dbox.getString("d_tcareermonth") %>개월
                </td>
            </tr>
<%
        }
%>

            </table>
<%
    } if(i < token.length && token[9] == '1') {
%>
            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_apply_info.gif" alt="참가정보" class="fl_l" /></h3>
            <table class="write">
            <colgroup>
                <col width="18%" />
                <col width="16%" />
                <col width="16%" />
                <col width="18%" />
                <col width="17%" />
                <col />
            </colgroup>
            <tr>
                <th>이름(필명)</th>
                <td><%= offApplyInfo.get("d_apply_name") %></td>
                <th>참가세션</th>
                <td>
<%
        String[] applySession = offApplyInfo.get("d_apply_session").split(",");
        for( int l = 0 ; l < applySession.length ; l++) {
            out.print("세션 " + applySession[l]);
            if ( l != applySession.length - 1) {
                out.println(", ");
            }
        }
%>
                </td>
                <th>소속 및 직함</th>
                <td><%= offApplyInfo.get("d_apply_belong_title") %></td>
            </tr>
            </table>

<%
    }

    if(i < token.length && token[10] == '1') {
%>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_channel.gif" alt="개인 온라인 채널" class="fl_l" /></h3>
            <table class="write">
                <colgroup>
                    <col width="18%" />
                    <col width="*" />
                </colgroup>
                <tr>
                    <th>이름(닉네임)</th>
                    <td class="end">
                        닉네임
                    </td>
                </tr>
                <tr>
                    <th>개인 온라인채널<br/>(SNS, 블로그 등)</th>
                    <td class="end">
                        SNS 주소
                    </td>
                </tr>
            </table>

<%
    }

    if(i < token.length && token[1] == '1') {
%>
            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_army.gif" alt="병역구분" class="fl_l" /></h3>
            <table class="write">
            <tr>
                <td class="intxt end">
                    <span class="mg_l20" id="milliDate">
                        복무기간
                            <%= dbox.get("d_militarystart") %>~<%= dbox.get("d_militaryend") %>
                    </span>
                    <span class="mg_l15" id="milliMemo">면제사유 <%= dbox.get("d_militarymemo") %></span>
                </td>
            </tr>
            </table>
<%
    }

    if(i < token.length && token[2] == '1') {
%>
            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_curri.gif" alt="교육경험" class="fl_l" /></h3>
            <table class="ac_list">
            <colgroup><col width="111px" /><col width="101px" /><col width="150px" /><col /><col width="101px" /></colgroup>
            <thead>
            <tr>
                <th>학교명</th>
                <th>졸업구분</th>
                <th>재학기간</th>
                <th>학과/전공</th>
                <th>소재지</th>
            </tr>
            </thead>
            <tbody id="schoolList">
<%  for(DataBox schoolListBox : schoolList) { %>
            <tr id="study<%= schoolListBox.get("d_seq") %>">
                <td><%= schoolListBox.get("d_schoolname") %></td>
                <td><%= schoolListBox.get("d_statusnm") %></td>
                <td><%= schoolListBox.get("d_sstart") %>~<%= schoolListBox.get("d_send") %></td>
                <td><%= schoolListBox.get("d_major") %></td>
                <td><%= schoolListBox.get("d_place") %></td>
            </tr>
<%  } %>
            </tbody>
            </table>
<%  }if(i < token.length && token[3] == '1') {    %>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_certificate.gif" alt="자격증" class="fl_l" /></h3>
            <table class="ac_list">
            <colgroup><col width="221px" /><col width="175px" /><col /></colgroup>
            <thead>
            <tr>
                <th>자격증명</th>
                <th>취득일</th>
                <th>발행처</th>
            </tr>
            </thead>
            <tbody id="licenseList">
<%  for(DataBox licenseListBox : licenseList) { %>
            <tr id="license<%= licenseListBox.get("d_seq") %>">
                <td><%= licenseListBox.get("d_licensename") %></td>
                <td><%= licenseListBox.get("d_getdate") %></td>
                <td><%= licenseListBox.get("d_publish") %></td>
            </tr>
<%  } %>
            </tbody>
            </table>
<%  }if(i < token.length && token[4] == '1') {    %>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_trainnum.gif" alt="연수지명번호" class="fl_l" />
            </h3>
            <table class="write" id="trainTable" <% if(dbox.get("d_yeunsuno").length()==0) out.println("style='display:none'");%>>
            <colgroup><col width="18%" /><col width="32%" /><col width="18%" /><col width="32%" /></colgroup>
            <tr>
                <th>연수지명번호</th>
                <td><%=dbox.get("d_yeunsuno")%></td>
                <th>소속교육청</th>
                <td class="end"><%=dbox.get("d_yeonsunm")%></td>
            </tr>
            </table>

<%  }if(i < token.length && token[5] == '1') {    %>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_career.gif" alt="경력" class="fl_l" /></h3>
            <table class="ac_list">
            <colgroup><col width="67px" /><col width="108px" /><col width="135px" /><col /><col width="150px" /><col width="80px" /></colgroup>
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
            <tbody id="workinfoList">
<%  for(DataBox careerListbox : careerList) { %>
            <tr id="workinfo<%= careerListbox.get("d_seq") %>">
                <td><%= careerListbox.get("d_jikup") %></td>
                <td><%= careerListbox.get("d_jikjong") %></td>
                <td><%= careerListbox.get("d_officename") %></td>
                <td><%= careerListbox.get("d_position") %></td>
                <td><%= careerListbox.get("d_workstart") %>~<%= careerListbox.getStringDefault("d_workend","현재") %></td>
                <td><%= careerListbox.get("d_workstatus") %></td>
            </tr>
<%  } %>
            </tbody>
            </table>
<%  }if(i < token.length && token[6] == '1') {    %>

            <h3 class="pop_tit"><img src="/images/portal/common/stit_resume.gif" alt="자기소개서" /></h3>
            <table class="write">
            <colgroup><col width="18%" /><col /></colgroup>
            <tr>
                <th>본인소개(경력사항)</th>
                <td><%= dbox.get("d_intro").replace("\n", "<BR>") %></td>
            </tr>
            <tr>
                <th>지원동기</th>
                <td><%= dbox.get("d_motive").replace("\n", "<BR>") %></td>
            </tr>
            <%--<tr>--%>
                <%--<th>향후계획</th>--%>
                <%--<td><textarea class="inbox" name="p_vision" id="p_vision" onkeyup="getObjectLength('p_vision')" onchange="getObjectLength('p_vision', 4000)" style="width:87%; height:30px;"><%= dbox.get("d_vision") %></textarea> <font id="p_vision_length" FONT-FAMILY="Tahoma" font-size="10px" color="#CB1F02"></font></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<th>기타</th>--%>
                <%--<td><textarea class="inbox" name="p_etc" id="p_etc" onkeyup="getObjectLength('p_etc')" onchange="getObjectLength('p_etc', 4000)" style="width:87%; height:30px;"><%= dbox.get("d_etc") %></textarea> <font id="p_etc_length" FONT-FAMILY="Tahoma" font-size="10px" color="#CB1F02"></font></td>--%>
            <%--</tr>--%>
            </table>
<%  }   %>
            <!----------------- 과정 관리 시작 ----------------->
        <h3 class="pop_tit mg_b9">신청이력</h3>
        <table class="ac_list">
        <thead>
            <tr>
                <th><b>NO</b></th>
                <th>과정연도</th>
                <th>과정명</th>
                <th>과정차수</th>
                <th>승인상태</th>
                <th>신청일 </th>
                <th>결제상태 </th>
                <th>결제일 </th>
                <th>결제방법 </th>
                <th>취소요청일 </th>
            </tr>
        </thead>
        <tbody>
<%  if(list!= null){
    DataBox data = null;
    String acceptResult = null;
    String v_paymethod = null;
    String v_cancel_btn = null;
    String v_tid = null;
    String v_resultcode = null;
    for (int j=0; j<list.size(); j++) {
        data  = (DataBox)list.get(j);
%>
        <tr id="<%=data.get("d_subjnm")%>">
            <td><%=String.valueOf(j+1)%></td>
            <td><%=data.get("d_year") %></td>
            <td><%=data.get("d_subjnm") %></td>
            <td><%=data.get("d_subjseq") %></td>
            <% if(data.get("d_chkstatus").length() == 2 && data.get("d_chkstatus").charAt(1) == 'Y') { %>
                <td style="color:blue">
            <% } else { %>
                <td>
            <% } %>
                <%=chkAcceptResult(acceptResultList, data.get("d_chkstatus")) %>
            </td>
            <td><%=data.getDate("d_appdate") %> <%= data.getHour("d_appdate") %>:<%= data.getMinute("d_appdate") %></td>
            <td><%=data.get("d_resultcode") %></td>
            <td><%=data.getDate("d_pgauthdate") %> <%= data.getOnlyHourMinute("d_pgauthtime") %></td>
            <td><%=data.get("d_paymethod") %></td>
            <td><%if(!data.getDate("d_canceldate").equals("")) {out.println(data.getDate("d_canceldate"));}else{out.println("-");} %></td>
        </tr>
<%  } %>

        <% if (list.size()==0) {%>
            <tr><td colspan=11>검색된 결과가 없습니다.</td></tr>
        <% } %>
<%} %>
        </table>
        </div>
    </div>
    <div id="pop_footer">
        <p class="f_btn"><a href="javascript:whenCancel();" class="btn_gr"><span>취소</span></a>
        <a href="javascript:whenPrint();" class="btn_gr"><span>출력</span></a></p>
    </div>

</body>
</html>
