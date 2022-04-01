<%
//**********************************************************
//  1. 제      목: PERSONAL SELECT LIST
//  2. 프로그램명: za_PersonalSelect_R.jsp
//  3. 개      요: 개인이력 상세조회
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//  7. 변경 이력:
//      7.1. 항목 추가
//           일자: 2015-03-12
//           내용: 교육그룹명, 교육그룹코드, 모바일용 아이디 추가
//           indent 및 소스 정리는 못하겠음.
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="com.credu.common.GetCodenm" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.FormatDate" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="com.credu.library.StringManager" %>
<%@ page import="com.credu.study.StudyStatusData" %>
<%@ page import="com.credu.system.CodeConfigBean" %>
<%@ page import="com.credu.system.MemberData" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    String IMAGE_PATH = "/member/";

    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if ( box == null ) {
        box = RequestManager.getBox(request);
    }

    DataBox addInfobox = (DataBox)request.getAttribute("addInfo");
    List<DataBox> schoolList = (List<DataBox>) addInfobox.getObject("schoolList");
    List<DataBox> licenseList = (List<DataBox>) addInfobox.getObject("licenseList");
    List<DataBox> careerList = (List<DataBox>) addInfobox.getObject("careerList");
    List<DataBox> yeunsunoList = (List<DataBox>) request.getAttribute("YeunsunoList"); //연수번호

    NumberFormat s_fmt = NumberFormat.getInstance();                //통화단위 ,표시
    int v_totgetpoint = box.getInt("p_getpoint");
    int v_totusepoint = box.getInt("p_usepoint");
    int v_totwaitpoint = box.getInt("p_waitpoint");
    int v_availablepoint = v_totgetpoint - v_totusepoint - v_totwaitpoint;
    String v_availablepoint_text = s_fmt.format(v_availablepoint);

    ArrayList selectHavePointList = null;
    ArrayList selectStoldPointList = null;
    ArrayList list3 = null;

    selectHavePointList = (ArrayList)request.getAttribute("selectHavePointList");       //보유포인트
    selectStoldPointList = (ArrayList)request.getAttribute("selectStoldPointList"); //적립포인트
    list3 = (ArrayList)request.getAttribute("selectUsePointList");      //사용포인트

    String s_gadmin = box.getSession("gadmin");
    String v_process = box.getString("p_process");
    String v_selTab = box.getString("p_selTab");
    String v_search = box.getString("p_search");
    String v_searchtext = box.getString("p_searchtext");
    String v_userid = "";               // ID
    // String v_resno = "";               // 주민번호
    // String v_pwd = "";               // 암호화비밀번호
    String v_eng_name = "";               // 영문이름
    String v_name = "";               // 이름
    String v_membergubunnm = "";               // 회원구분
    String v_registgubunnm = "";               // 가입경로
    String v_email = "";               // email
    String v_birthday = "";               // 생일
    String v_authority = "";               // 권한
    String v_post1 = "";               // 우편1
    String v_post2 = "";               // 우편2
    String v_addr = "";               // 주소
    String v_addr2 = "";               // 주소2
    String v_hometel = "";               // 집전화번호
    String v_handphone = "";               // 휴대전화번호
    String v_comptel = "";               // 직장전화번호
    String v_comp_post1 = "";               // 직장 우편번호1
    String v_comp_post2 = "";               // 직장 우편번호2
    String v_comptext = "";               // 직장명
    String v_comp_addr1 = "";               // 직장주소
    String v_comp_addr2 = "";               // 직장주소2
    String v_jikup = "";               // 직업코드
    String v_degree = "";               // 학력코드
    String v_tel_line = "";               // 내선번호
    String v_comp = "";               // 회사ID
    String v_interest = "";               // 관심사항
    String v_recomid = "";               // 추천자ID
    String v_ismailing = "";               // 메일수신방법코드
    String v_isopening = "";               // 전화공개여부
    String v_islettering = "";               // 뉴스레터수신

    String v_indate = "";               // 생성일
    String v_ldate = "";               // 최종수정일
    String v_lglast = "";               // 최종로그인시간
    String v_lgip = "";               // 로그인IP
    String v_pwd_date = "";               // 패스변경일자
    String v_old_pwd = "";               // 구 패스워드
    String v_ent_date = "";               // 입사일
    String v_grp_ent_date = "";               // 그룹입사일
    String v_sex = "";               // 성별
    String v_photo = "";               // 사진파일 유무
    String v_photo_view = "";               // 사진파일 표시
    String v_state = "";               //사용상태
    String v_validation = "";               //실명인증여부
    String v_registerroutenm = "";
    String vv_grcodenm = "";
    String v_workfieldcd = "";               //종사분야

    //2009.10.29 지역번호, 휴대전화
    String[] arrDcd = {"02", "031", "032", "033", "041", "042", "043", "051", "052", "053", "054", "055", "061", "062", "063", "064", "070", "080"};
    String[] arrScd = {"010", "011", "016", "017", "018", "019"};

    //2009.10.29 게시판 구분
    String v_type = "";               // 게시판구분

    String v_imgColor1 = "black";         //신청과정 이미지색
    String v_imgColor2 = "black";         //수강과정 이미지색
    String v_imgColor3 = "black";         //수료과정 이미지색
    String v_imgColor4 = "black";         //학점이수현황 이미지색
    String v_imgColor5 = "black";         //오프라인 신청과정 이미지색
    String v_imgColor6 = "black";         //오프라인 수료과정 이미지색


    int v_lgcnt = 0;                   // 로그인횟수
    MemberData data = null;
    //DEFINED class&variable END

    data = (MemberData)request.getAttribute("PersonalSelect");
    v_userid = data.getUserid();         // ID
    //v_resno = data.getResno();          // 주민번호

    /*(
    if (data.getResno2().equals("")) {
        v_resno = data.getResno1();                                 // 주민번호1(앞자리)
    } else {
        v_resno = data.getResno1() +'-' + data.getResno2();          // 주민번호1(앞자리) + 주민번호1(뒷자리)
    }

    if(!(v_resno == null || v_resno.equals(""))){
        // v_resno = v_resno.substring(0, 6) + "-" + v_resno.substring(6);
        if (v_resno.length()>6) {
            v_resno = v_resno.substring(0, 6) + "-*******";
        }
    }
    */

    // v_pwd = data.getPwd();            // 암호화비밀번호
    v_name = data.getName();           // 이름
    v_eng_name = data.getEng_name();       //영문이름
    v_membergubunnm = data.getMembergubunnm();  // 회원구분
    v_registgubunnm = data.getRegistgubunnm();  // 가입경로
    v_email = data.getEmail();          // email
    v_authority = data.getAuthority();      // 권한
    v_post1 = data.getPost1();          // 우편1
    v_post2 = data.getPost2();          // 우편2
    v_addr = data.getAddr();           // 주소
    v_addr2 = data.getAddr2();          // 주소2
    v_hometel = data.getHometel();        // 집전화번호
    v_handphone = data.getHandphone();      // 휴대전화번호
    v_ismailing = data.getIsmailing();      // 메일수신방법
    v_indate = data.getIndate();         // 생성일
    v_lglast = data.getLglast();         // 최종로그인시간
    v_lgip = data.getLgip();           // 로그인IP
    v_lgcnt = data.getLgcnt();          // 로그인횟수
    v_comptext = data.getComptext();       // 회사명
    v_comptel = data.getComptel();        // 회사전화번호
    v_comp_post1 = data.getComp_post1();     // 회사 우편번호1
    v_comp_post2 = data.getComp_post2();     // 회사 우편번호2
    v_comp_addr1 = data.getComp_addr1();     // 회사 주소1
    v_comp_addr2 = data.getComp_addr2();     // 회사 주소2
    v_ismailing = data.getIsmailing();      // 이메일수신여부
    v_isopening = data.getIsopening();      // 연락처공개여부
    v_islettering = data.getIslettering();    // 뉴스레터수신여부
    v_jikup = data.getJikup();          // 직업
    v_degree = data.getDegree();         // 학력
    v_state = data.getState();          // 사용상태
    v_validation = data.getValidation();     // 실명인증여부
    v_indate = data.getIndate();         // 실명인증여부
    v_ldate = data.getLdate();      // 실명인증여부
    v_registerroutenm = data.getRegisterroutenm();
    vv_grcodenm = data.getGrcodenm();

    v_workfieldcd = data.getWorkfieldcd(); //종사분야

    //2009.10.29 전화번호 처리, 입력란을 한개에서 세개로 변경
    String v_hometel1 = "";
    String v_hometel2 = "";
    String v_hometel3 = "";
    String v_handphone1 = "";
    String v_handphone2 = "";
    String v_handphone3 = "";
    String v_comptel1 = "";
    String v_comptel2 = "";
    String v_comptel3 = "";

    String[] arrHometel = v_hometel.split("-");
    String[] arrHandphone = v_handphone.split("-");
    String[] arrComptel = v_comptel.split("-");

    //집전화번호 처리
    v_hometel = v_hometel.replace("-","");
    if (v_hometel.length() != 0) {
        if (arrHometel.length == 3) {
            v_hometel1  = arrHometel[0];
            v_hometel2  = arrHometel[1];
            v_hometel3  = arrHometel[2];
        } else if ("02".equals(v_hometel.substring(0,2))) {
            if (v_hometel.length() >= 2) v_hometel1 = v_hometel.substring(0,2);
            if (v_hometel.length() >= 6) {
                v_hometel2  = v_hometel.substring(2,6);
            } else if (v_hometel.length() > 2 && v_hometel.length() <= 6) {
                v_hometel2  = v_hometel.substring(2,v_hometel.length());
            }
            if (v_hometel.length() > 6) v_hometel3  = v_hometel.substring(6);
        } else {
            if (v_hometel.length() >= 3) v_hometel1 = v_hometel.substring(0,3);
            if (v_hometel.length() >= 7) {
                v_hometel2  = v_hometel.substring(3,7);
            } else if (v_hometel.length() > 3 && v_hometel.length() <= 7) {
                v_hometel2  = v_hometel.substring(3,v_hometel.length());
            }
            if (v_hometel.length() > 7) v_hometel3  = v_hometel.substring(7);
        }
    }

    //휴대전화번호 처리
    v_handphone     = v_handphone.replace("-","");
    if (v_handphone.length() != 0) {
        if (arrHandphone.length == 3) {
            v_handphone1    = arrHandphone[0];
            v_handphone2    = arrHandphone[1];
            v_handphone3    = arrHandphone[2];
        } else {
            if (v_handphone.length() >= 3) v_handphone1 = v_handphone.substring(0,3);
            if (v_handphone.length() >= 7) {
                v_handphone2    = v_handphone.substring(3,7);
            } else if (v_handphone.length() > 3 && v_handphone.length() <= 7) {
                v_handphone2    = v_handphone.substring(3,v_handphone.length());
            }
            if (v_handphone.length() > 7) v_handphone3  = v_handphone.substring(7);
        }
    }

    //회사전화번호 처리
    v_comptel   = v_comptel.replace("-","");
    if (v_comptel.length() != 0) {
        if (arrComptel.length == 3) {
            v_comptel1  = arrComptel[0];
            v_comptel2  = arrComptel[1];
            v_comptel3  = arrComptel[2];
        } else if ("02".equals(v_comptel.substring(0,2))) {
            if (v_comptel.length() >= 2) v_comptel1 = v_comptel.substring(0,2);
            if (v_comptel.length() >= 6) {
                v_comptel2  = v_comptel.substring(2,6);
            } else if (v_comptel.length() > 2 && v_comptel.length() <= 6) {
                v_comptel2  = v_comptel.substring(2,v_comptel.length());
            }
            if (v_comptel.length() > 6) v_comptel3  = v_comptel.substring(6);
        } else {
            if (v_comptel.length() >= 3) v_comptel1 = v_comptel.substring(0,3);
            if (v_comptel.length() >= 7) {
                v_comptel2  = v_comptel.substring(3,7);
            } else if (v_comptel.length() > 3 && v_comptel.length() <= 7) {
                v_comptel2  = v_comptel.substring(3,v_comptel.length());
            }
            if (v_comptel.length() > 7) v_comptel3  = v_comptel.substring(7);
        }
    }


    if(v_selTab.equals("propose") || v_selTab.equals("")){
        v_imgColor1 = "blue";
    } else if(v_selTab.equals("education")){
        v_imgColor2 = "blue";
    } else if(v_selTab.equals("graduation")){
        v_imgColor3 = "blue";
    } else if(v_selTab.equals("scorecomplete")){
        v_imgColor4 = "blue";
    }else if(v_selTab.equals("offpropose")){
        v_imgColor5 = "blue";
    }else if(v_selTab.equals("offgraduation")){
        v_imgColor6 = "blue";
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<style>
    
    .tdBorder {border:1px solid;}
</style>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language="JavaScript">
    function select_tab(sel){
        document.form1.p_selTab.value = sel;
        document.form1.p_process.value = "PersonalSelect";
        document.form1.action = "/servlet/controller.study.StudyStatusAdminServlet";
        document.form1.submit();
    }
function whenSimpleSave(type, p_membergubun) {
    var title = "";
    var sqlNum = "";
    var checkSqlNum = "";
    var elseSqlNum = "";
    var subj="",year="",subjseq="";

    switch (type) {
        case 1 : title = "회원구분"; sqlNum="member.membergubun.update"; break;
        case 2 : title = "회원상태"; sqlNum="member.state.update"; break;
        case 3 : title = "연수지명번호"; sqlNum="member.yeunsu.insert"; checkSqlNum="member.yeunsu.exist"; elseSqlNum="member.yeunsu.update";
            subj=$("#pp_subj").val().toUpperCase();year=$("#pp_year").val();subjseq=$("#pp_subjseq").val();
            break;
    }

    if(type==3)
    {
        if(subj=="" || year=="" || subjseq=="")
        {
            alert("과정코드,년도,기수는 반드시 입력 하셔야 합니다.");
            return;
        }

        if(subj.length>7)
        {
            alert("과정코드의 길이는 7자리 이하 입니다.확인해주세요");
            return;
        }

        if(year.length!=4 || subjseq.length!=4)
        {
            alert("년도,차수의 길이는 4자리 입니다.확인해주세요");
            return;
        }
    }
    if(!confirm(title+"을(를) 변경(저장,수정)하시겠습니까?")) {
        return;
    }
    $.post("/servlet/controller.common.AjaxServlet"
            , {   sqlNum:sqlNum,rerurnFunction:"resultSimpleSave", checkSqlNum:checkSqlNum, elseSqlNum:elseSqlNum
                , p_userid:'<%=v_userid%>'
                , p_membergubun : p_membergubun
                , p_yeunsuno : $("#p_yeunsuno").val()
                , p_yeonsunm : $("#p_yeonsunm").val()
                , p_subjcode : subj
                , p_subjseq : subjseq
                , p_year : year
                , p_grcode : '<%= box.getString("s_grcode") %>'
            }
            , function(data) {
                $("#ajaxDiv").html(data);
            });
}
function resultSimpleSave(data) {
    if (data.result=='true') { alert("수정(저장)했습니다.");whenReCall();}
    else alert("수정(저장)이 실패하였습니다.\n"+data.result);
}
//Tab 선택시 동작
function showTab(clickTab)
{
    g_showTab(clickTab, 4);
}

function g_showTab(tabNo, count)
{

    var clickTabNo = new Number(tabNo);
    var tabCount   = new Number(count);

    if( clickTabNo == 0 || tabCount == 0 ) return;

    for(i=1; i<=tabCount; i++)
    {
        div = document.all["tab_"+i];

        if(!div) return;

        if( clickTabNo == i ) {
            // 활성화된 tab 클릭시 return
            if(div.style.display == "") return;
            div.style.display = ""
        }
        else {
            // 선택되지 않는 탭들은 깜빡임 방지.
            if(div.style.display != "none") {
                div.style.display = "none";
            }
        }
    }
}

    function yeunsunoDelete(subj,year,subjseq,userid)
    {
        if(!confirm("정말 삭제 하시겠습니까?")) return;

        //window.self.name = "PersonalSelect";
        document.form1.pp_userid.value     = userid;
        document.form1.p_subj.value    = subj;
        document.form1.p_year.value    = year;
        document.form1.p_subjseq.value = subjseq;

//        document.form1.target = "winSelectView";
//        document.form1.action = "/servlet/controller.study.StudyStatusAdminServlet";
        document.form1.p_process.value = "YeunsunoDelete";
        document.form1.submit();
        //document.form1.target = window.self.name;
    }

    function whenSearch() {
        document.form1.action = "/servlet/controller.study.StudyStatusAdminServlet";
        document.form1.p_process.value = "PersonalSearchPage";
        document.form1.submit();
    }

    function whenReCall()
    {
       document.form1.p_process.value = "PersonalSelect";
       document.form1.action = "/servlet/controller.study.StudyStatusAdminServlet";
       document.form1.submit();

    }
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id="ajaxDiv"></div>
    <form name = "form1" method = "post">
    <input type="hidden" name="p_postgubun" value="<%=v_process%>">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_search" value="<%=v_search%>">
    <input type="hidden" name="p_searchtext" value="<%=v_searchtext%>">
    <input type="hidden" name="p_selTab">
    <input type="hidden" name="p_seq"       value = "">
    <input type="hidden" name="p_types"     value = "">
    <input type="hidden" name="pp_type"     value = "">
    <input type="hidden" name="p_upfilecnt" value = "">
    <input type="hidden" name="p_subj">
    <input type="hidden" name="p_year">
    <input type="hidden" name="p_subjseq">
    <input type="hidden" name="pp_userid">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">

    <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
       <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/study/s_title04.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="right" height="20" >
            <a href="javascript:whenSearch()" border=0 ><img src="/images/admin/button/btn_anotherpeople.gif" border="0"></a>
            </td>
        </tr>
      </table>

      <!----------------- 개인이력 상세조회 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="4" border="0">
        <tr>
          <td colspan="4" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="15%" class="table_title tdBorder"><b>회원구분</b></td>
          <td class="table_02_2 tdBorder" ><%=v_membergubunnm%></td>
          <td class="table_title tdBorder" width="15%"><b>교육그룹</b></td>
          <td class="table_02_2 tdBorder" width="35%"><%= data.getGrcodenm() %> / <%= data.getGrcode() %></td>
        </tr>
        <tr>
          <td width="15%" class="table_title tdBorder"><b>ID</b></td>
          <td class="table_02_2 tdBorder" ><%= v_userid %></td>
          <td class="table_title tdBorder" width="15%"><b>Mobile ID</b></td>
          <td class="table_02_2 tdBorder" width="35%"><%= data.getMobileUserId() %></td>
        </tr>
        <tr>
          <td width="15%" class="table_title tdBorder"><b>성명(한글)</b></td>
          <td class="table_02_2 tdBorder" ><%=v_name%></td>
          <td class="table_title tdBorder" width="15%"><b>성명(영문)</b></td>
          <td class="table_02_2 tdBorder" width="35%">
            <%=v_eng_name%>
          </td>
        </tr>
        <tr>
          <td width="15%" class="table_title tdBorder"><b>이메일</b></td>
          <td class="table_02_2 tdBorder" colspan="3"><%=v_email%></td>
        </tr>
        <tr>
          <td class="table_title tdBorder"><b>우편번호(자택)</b></td>
          <td class="table_02_2 tdBorder" colspan="3">
            <%=v_post1%> - <%=v_post2%>
          </td>
        </tr>
        <tr>
          <td class="table_title tdBorder"><b>주소(자택)</b></td>
          <td class="table_02_2 tdBorder" colspan='3'>
            <%=v_addr%>&nbsp;&nbsp;
          </td>
        </tr>
        <tr>
          <td width="15%" class="table_title tdBorder"><b>우편번호(회사)</b></td>
          <td class="table_02_2 tdBorder" colspan="3">
            <%=v_comp_post1%> - <%=v_comp_post2%>
          </td>
        </tr>
        <tr>
          <td class="table_title tdBorder"><b>주소(회사)</b></td>
          <td class="table_02_2 tdBorder" colspan='3'>
            <%=v_comp_addr1%>&nbsp;&nbsp; <%=v_comp_addr2%>
          </td>
        </tr>

        <tr>
          <td width="15%" class="table_title tdBorder"><b>전화번호(자택)</b></td>
          <td class="table_02_2 tdBorder" >
              <%= v_hometel1 %> - <%=v_hometel2%> - <%=v_hometel3%>
          </td>
          <td class="table_title tdBorder" width="15%"><b>휴대전화</b></td>
          <td class="table_02_2 tdBorder" width="35%">
            <%= v_handphone1 %> - <%=v_handphone2%> - <%=v_handphone3%>
          </td>
        </tr>
        <tr>
          <td width="15%" class="table_title tdBorder"><b>전화번호(회사)</b></td>
          <td class="table_02_2 tdBorder" >
                <%= v_comptel1 %> - <%=v_comptel2%> - <%=v_comptel3%>
          </td>
          <td class="table_title tdBorder" width="15%"><b>회사명</b></td>
          <td class="table_02_2 tdBorder" width="35%"><%=v_comptext%></td>
        </tr>
        <tr>
          <!--<td width="15%" class="table_title tdBorder"><b>최종학력</b></td>
          <td class="table_02_2 tdBorder" >
            <%= CodeConfigBean.getCodeGubunSelect("0069","",1,"p_degree",v_degree,"",3)%>
          </td>
          <td class="table_title tdBorder" width="15%"><b>직업/문광부직업</b></td>
          <td class="table_02_2 tdBorder" width="35%">
              <%= data.getJobnm()   %> &nbsp;/&nbsp; <%= data.getJob_culturenm() %>
              <%=CodeConfigBean.getCodeGubunSelect ("0104","", 1,"p_jikup", v_jikup,"",3) %>
          </td>-->
          <td width="15%" class="table_title tdBorder"><b>직업</b></td>
          <td class="table_02_2 tdBorder" >
<!--            <%=CodeConfigBean.getCodeGubunSelect ("0104","", 1,"p_jikup", v_jikup,"",3) %>-->
            <%=CodeConfigBean.getCodeName("0104",v_jikup) %>
          </td>
          <td class="table_title tdBorder" width="15%"><b>종사분야</b></td>
          <td class="table_02_2 tdBorder" width="35%">
<!--              <%=CodeConfigBean.getCodeGubunSelect ("0103","", 1,"p_workfieldcd",  v_workfieldcd, "",3) %>-->
              <%=CodeConfigBean.getCodeName("0103",v_workfieldcd) %>
          </td>
        </tr>
        <tr>
          <td width="15%" class="table_title tdBorder"><b>메일수신여부</b></td>
          <td class="table_02_2 tdBorder" >
                <input name="p_ismailing" type=radio   value="Y" <%=(v_ismailing.equals("Y"))?"checked":""%>> 수신
                <input name="p_ismailing" type=radio   value="N" <%=(v_ismailing.equals("N"))?"checked":""%>> 수신거부
          </td>
          <td class="table_title tdBorder" width="15%"><b>뉴스레터수신여부</b></td>
          <td class="table_02_2 tdBorder" width="35%">
                <input name="p_islettering" type=radio   value="Y" <%=(v_islettering.equals("Y"))?"checked":""%>> 수신
                <input name="p_islettering" type=radio   value="N" <%=(v_islettering.equals("N"))?"checked":""%>> 수신거부
          </td>
        </tr>
        <tr>
          <td width="15%" class="table_title tdBorder"><b>연락처공개여부</b></td>
          <td class="table_02_2 tdBorder" >
                <input name="p_isopening" type=radio   value="Y" <%=(v_isopening.equals("Y"))?"checked":""%>> 공개
                <input name="p_isopening" type=radio   value="N" <%=(v_isopening.equals("N"))?"checked":""%>> 비공개
          </td>
          <td class="table_title tdBorder" width="15%"><b>회원상태</b></td>
          <td class="table_02_2 tdBorder" width="35%">
                <%=(v_state.equals("Y"))?"정상":"탈퇴" %>
<!--                <SELECT NAME="p_state">-->
<!--                    <option value="Y" <%=(v_state.equals("Y"))?"selected":""%>>정상</option>-->
<!--                    <option value="N" <%=(v_state.equals("N"))?"selected":""%>>탈퇴</option>-->
<!--                </SELECT>-->
          </td>
        </tr>
        <tr>
          <td width="15%" class="table_title tdBorder"><b>가입경로</b></td>
          <td class="table_02_2 tdBorder" ><%=vv_grcodenm %> (<%=v_registerroutenm%>)</td>
          <td class="table_title tdBorder" width="15%"><b>실명인증여부</b></td>
          <td class="table_02_2 tdBorder" width="35%"><%=v_validation%></td>
        </tr>
        <tr>
          <td class="table_title tdBorder">최초가입일</td>
          <td class="table_02_2 tdBorder"><%=FormatDate.getFormatDate(v_indate,"yyyy/MM/dd hh:mm:ss")%></td>
          <td class="table_title tdBorder">최종수정일</td>
          <td class="table_02_2 tdBorder"><%=FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd hh:mm:ss")%></td>
        </tr>
        <tr>
          <td align="center" colspan="5" height="25" bgcolor="#F7F7F7">
     <!----------------- 개인별 상담내역리스트 시작 ----------------->
            <br>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="110">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/images/admin/study/study_blue_img1.gif" rowspan="3" width="5" height="23"></td>
                      <td background="/images/admin/study/study_blue_img3.gif" height="5"></td>
                      <td rowspan="3" background="/images/admin/study/study_blue_img2.gif" width="5" height="23"></td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_blue_img5.gif" align="center" height="16" valign="middle">
                      <b><font color="#FFFFFF">상담내역</font></b>
                      </td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_blue_img4.gif" height="2"></td>
                    </tr>
                  </table>
                </td>
                <td><a href="javascript:insertCounsel()" class="btn_gr"><span>등록</span></a></td>
              </tr>
            </table>
<SCRIPT LANGUAGE="JavaScript">
<!--
// 상담등록
function insertCounsel() {
//        window.self.name = "PersonalSelect";
        document.form1.p_subjyearsubjseq.value= '';
        open_window("openCounsel","","50","50","800","650", false,false,true,true,true);
        document.form1.target = "openCounsel";
        document.form1.action='/servlet/controller.study.StudyStatusAdminServlet';
        document.form1.p_process.value = 'CounselInsertPage';
        document.form1.submit();

//        document.form1.target = window.self.name;
}

// // 상담등록2
function insertCounsel2(subj, year, subjseq) {
//        window.self.name = "PersonalSelect";
        document.form1.p_subjyearsubjseq.value= subj+"/"+year +"/"+subjseq;
        open_window("openCounsel","","50","50","800","650", false,false,true,true,true);
        document.form1.target = "openCounsel";
        document.form1.action='/servlet/controller.study.StudyStatusAdminServlet';
        document.form1.p_process.value = 'CounselInsertPage';
        document.form1.submit();

//        document.form1.target = window.self.name;
}


//상담내역보기
function viewCounsel(no) {
//     window.self.name = "PersonalSelect";
     document.form1.p_no.value= no;
     open_window("openCounsel","","50","50","800","650", false,false,true,true,true);
     document.form1.target = "openCounsel";
     document.form1.action='/servlet/controller.study.StudyStatusAdminServlet';
     document.form1.p_process.value = 'CounselUpdatePage';
     document.form1.submit();

//     document.form1.target = window.self.name;
}



// 상담내역보기
function viewCounsel(no) {
//    window.self.name = "PersonalSelect";
    document.form1.p_no.value= no;
    open_window("openCounsel","","50","50","800","650", false,false,true,true,true);
    document.form1.target = "openCounsel";
    document.form1.action='/servlet/controller.study.StudyStatusAdminServlet';
    document.form1.p_process.value = 'CounselUpdatePage';
    document.form1.submit();

//    document.form1.target = window.self.name;
}

//Q&A상세화면 페이지로 이동
function viewHomeqna(seq, upfilecnt, userid, types, type) {
//    window.self.name = "PersonalSelect";
    open_window("openHomeqna","","50","50","800","650", false,false,true,"yes","yes");
    document.form1.target = "openHomeqna";
    document.form1.action='/servlet/controller.study.StudyStatusAdminServlet';
    document.form1.p_process.value = 'ViewHomeqna';

    document.form1.p_seq.value = seq;
    document.form1.p_userid.value   = userid;
    document.form1.p_types.value    = types;
    document.form1.pp_type.value    = type;
    document.form1.p_upfilecnt.value = upfilecnt;

    document.form1.submit();

//    document.form1.target = window.self.name;
}

//과정질문 상세화면 페이지로 이동
function viewQna(seq,subj,year,subjseq) {
//    window.self.name = "PersonalSelect";
    open_window("openQna","","50","50","800","650", false,false,true,"yes","yes");
    document.form1.target = "openQna";
    document.form1.action='/servlet/controller.study.StudyStatusAdminServlet';
    document.form1.p_process.value = 'ViewQna';

    document.form1.p_seq.value     = seq;
    document.form1.p_subj.value    = subj;
    document.form1.p_year.value    = year;
    document.form1.p_subjseq.value = subjseq;

    document.form1.submit();

//    document.form1.target = window.self.name;
}

//분류 선택
function selectMcode() {
        document.form1.action='/servlet/controller.study.StudyStatusAdminServlet';
        document.form1.p_process.value = 'PersonalSelect';
        document.form1.submit();
}

//레코드분류 선택
function selectType() {
        document.form1.action='/servlet/controller.study.StudyStatusAdminServlet';
        document.form1.p_process.value = 'PersonalSelect';
        document.form1.submit();
}


//페이지 이동
function go(index) {
document.form1.p_action.value = "go";
  document.form1.p_pageno.value = index;
  document.form1.action = "/servlet/controller.study.StudyStatusAdminServlet";
  document.form1.p_process.value = "PersonalSelect";
  document.form1.submit();
}
//페이지 이동
function goPage(pageNum) {
    document.form1.p_action.value = "go";
   document.form1.p_pageno.value = pageNum;
   document.form1.action = "/servlet/controller.study.StudyStatusAdminServlet";
   document.form1.p_process.value = "PersonalSelect";
   document.form1.submit();
}

//-->
</SCRIPT>

<%
    int vc_no = 0;
    String vc_userid = "";
    String vc_cuserid = "";
    String vc_name = "";
    String vc_title = "";
    String vc_ftext = "";
    String vc_ctext = "";
    String vc_mcode = "";
    String vc_mcodenm = "";
    String vc_etime = "";
    String vc_subj = "";
    String vc_year = "";
    String vc_subjseq = "";
    String vc_status = "";
    String vc_sdate = "";
    String vc_ldate = "";
    String vc_gubun = "";
    String vc_status_view = "";
    String vc_sdate_view = "";
    String vc_ldate_view = "";
    String vc_gubun_view = "";

    //2009.10.30 추가
    String vc_type = "";
    String vc_typenm = "";
    int vc_tabseq    = 0;
    String vc_types = "";
    int vc_upfilecnt = 0;


    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
    int v_pageno = box.getInt("p_pageno");
    if (v_pageno == 0)  v_pageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));

    int j = 0;

    String p_mcode = box.getString("s_mcode");
    String p_type = box.getString("s_type");

    String sScriptString = "";
%>

<input type="hidden" name="p_userid"          value="<%=v_searchtext%>">
<input type="hidden" name="p_no"              value="">
<input type="hidden" name="s_grcode"          value="<%= box.getString("s_grcode") %>">
<input type="hidden" name="p_subjyearsubjseq" value="">
<input type="hidden" name="p_pageno"          value="<%=v_pageno%>">
<input type="hidden" name="p_action"          value="">


            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td bgcolor="#636563">
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td bgcolor="#FFFFFF" valign="top">
                      <br>
                      <table class="box_table_out_1" cellspacing="1" cellpadding="5" width="940" >
                        <tr>
                          <td colspan="9" class="table_top_line"></td>
                        </tr>
                        <tr>
                          <td class="table_title tdBorder" width="5%"><b>NO</b></td>
                          <td class="table_title tdBorder"style="padding-left:32px;;" width="10%"><b>
                          <%-- <kocca_select:select name="s_type" sqlNum="counseltype"  param=" " onChange="selectType('go');" attr="분류" selectedValue="<%= p_type %>" isLoad="true" all="none" type="3" styleclass="table_title tdBorder"/> --%>
						  <kocca:selectBox name="s_type" id="oGrcode" optionTitle="-- 전체 --" type="sqlID" sqlID="code.list.counseltype" isLoad="true" />                          
                          </b></td> 
                          <td class="table_title tdBorder" width="47%"><b>제목</b></td>
                          <!--td class="table_title tdBorder" width="10%"><b>작성자</b></td-->
                          <td class="table_title tdBorder" width="12%"><b>등록일</b></td>
                          <td class="table_title tdBorder" width="8%"><b>답변여부</b></td>
                          <!--td class="table_title tdBorder" width="8%"><b>구분</b></td-->
                        </tr>
                      </table>
                      <div style='width:960px;height:230px;overflow:auto;visibility:visible;border:1'>
                      <table class="box_table_out_1" cellspacing="1" cellpadding="5" width="940" >
<%


    ArrayList list1 = (ArrayList)request.getAttribute("counselList");

    for (j=0; j<list1.size();j++) {
        DataBox dbox1   = (DataBox)list1.get(j);
        vc_no = dbox1.getInt("d_no");
        vc_userid = dbox1.getString("d_userid");
        vc_name = dbox1.getString("d_name");
        vc_cuserid = dbox1.getString("d_cuserid");
        vc_title = dbox1.getString("d_title");
        vc_ftext = dbox1.getString("d_ftext");
        vc_ctext = dbox1.getString("d_ctext");
        vc_mcode = dbox1.getString("d_mcode");
        vc_mcodenm = dbox1.getString("d_mcodenm");
        vc_etime = dbox1.getString("d_etime");
        vc_subj = dbox1.getString("d_subj");
        vc_year = dbox1.getString("d_year");
        vc_subjseq = dbox1.getString("d_subjseq");
        vc_sdate = dbox1.getString("d_sdate");
        vc_status = dbox1.getString("d_status");
        vc_ldate = dbox1.getString("d_ldate");
        vc_gubun = dbox1.getString("d_gubun");
        vc_type = dbox1.getString("d_type");
        vc_tabseq = dbox1.getInt("d_tabseq");
        vc_types = dbox1.getString("d_types");
        vc_upfilecnt = dbox1.getInt("d_upfilecnt");


        v_totalpage = dbox1.getInt("d_totalpage");
        v_rowcount = dbox1.getInt("d_rowcount");
        v_dispnum = dbox1.getInt("d_dispnum");

        //vc_type  PQ : 홈페이지, BU : 버그신고, CU : 운영자에게, OO : 1vs1상담, MM : 1vs1상담, OFF : 유선상담, SUBJ : 과정질문방
        if (vc_type.equals("PQ")) {
            vc_typenm = "홈페이지 Q&A";
        } else if (vc_type.equals("BU") || vc_type.equals("CU") || vc_type.equals("OO") || vc_type.equals("MM")) {
            vc_typenm = "1vs1상담";
        } else if (vc_type.equals("OFF")) {
            vc_typenm = "유선상담";
        } else if (vc_type.equals("SUBJ")) {
            vc_typenm = "과정질문방";
        } else {
            vc_typenm = "";
        }

        //vc_status  1 : 미처리, 2 : 처리중, 3 : 완료
        if (vc_status.equals("1")) {
            vc_status_view = "열람전";
        } else if (vc_status.equals("2")) {
            vc_status_view = "답변준비중";
        } else if (vc_status.equals("3")) {
            vc_status_view = "답변완료";
        } else {
            vc_status_view = "";
        }

        // vc_gubun   in : 수신 , out : 발신
        if (vc_gubun.equals("in")) {
            vc_gubun_view = "수신";
        } else if (vc_gubun.equals("out")) {
            vc_gubun_view = "발신";
        }

        vc_sdate_view = FormatDate.getFormatDate(vc_sdate,"yyyy/MM/dd");
        vc_ldate_view = FormatDate.getFormatDate(vc_ldate,"yyyy/MM/dd");

        //상세보기 링크 만들기
        if ("OFF".equals(vc_type)) {
            sScriptString = "viewCounsel('" + vc_no + "');";
        } else if ("PQ".equals(vc_type) || "BU".equals(vc_type) || "CU".equals(vc_type) || "OO".equals(vc_type) || "MM".equals(vc_type)) {
            sScriptString = "viewHomeqna('" + vc_no + "', '" + vc_upfilecnt + "', '" + vc_userid + "', '" + vc_types + "', '" + vc_type + "');";
        } else if ("SUBJ".equals(vc_type)) {
            sScriptString = "viewQna('" + vc_no + "', '" + vc_subj + "', '" + vc_year + "', '" + vc_subjseq + "');";
        }
%>
                        <tr>
                          <td class="table_01 tdBorder"   width="5%"><%=v_dispnum%></td>
                          <td class="table_02_1 tdBorder" width="10%"><%=vc_typenm%></td>
                          <td class="table_02_2 tdBorder" width="47%"><a href="javascript:<%= sScriptString %>"><%=vc_title%></a></td>
                          <!--td class="table_02_1" width="10%"><%//=vc_name%></td-->
                          <td class="table_02_1 tdBorder" width="12%"><%=vc_sdate_view%></td>
                          <td class="table_02_1 tdBorder" width="8%"><%=vc_status_view%></td>
                          <!--td class="table_02_1" width="8%"><%//=vc_gubun_view%></td-->
                        </tr>
<%
    }

    if (j==0){
%>

                        <tr>
                          <td class="table_01" colspan="7">등록된 내용이 없습니다.</td>
                        </tr>
<%
    }
%>
                      </table>
                      <!--table><tr><td height="5"></td></tr></table>
                       <br>
                       <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
                         <tr>
                           <td align="right" valign="absmiddle">
                             <%//= PageUtil.printPageList(v_totalpage, v_pageno, row) %>
                           </td>
                         </tr>
                       </table-->
                      </div>

                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 개인별 상담내역리스트 끝 ----------------->
                        <br>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="110" height="23" align="center" valign="middle">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/images/admin/study/study_<%=v_imgColor1%>_img1.gif" rowspan="3" width="5" height="23"></td>
                      <td background="/images/admin/study/study_<%=v_imgColor1%>_img3.gif" height="5"></td>
                      <td rowspan="3" background="/images/admin/study/study_<%=v_imgColor1%>_img2.gif" width="5" height="23"></td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_<%=v_imgColor1%>_img5.gif" align="center" height="16" valign="middle">
                      <a href="javascript:select_tab('propose')"><b><font color="#FFFFFF">신청과정</font></b></a>
                      </td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_<%=v_imgColor1%>_img4.gif" height="2"></td>
                    </tr>
                  </table>
                </td>
                <td width="2"></td>
                <td width="110">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/images/admin/study/study_<%=v_imgColor2%>_img1.gif" rowspan="3" width="5" height="23"></td>
                      <td background="/images/admin/study/study_<%=v_imgColor2%>_img3.gif" height="5"></td>
                      <td rowspan="3" background="/images/admin/study/study_<%=v_imgColor2%>_img2.gif" width="5" height="23"></td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_<%=v_imgColor2%>_img5.gif" align="center" height="16" valign="middle">
                      <a href="javascript:select_tab('education')"><b><font color="#FFFFFF">수강과정</font></b></a>
                      </td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_<%=v_imgColor2%>_img4.gif" height="2"></td>
                    </tr>
                  </table>
                </td>
                <td width="2"></td>
                <td width="110">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/images/admin/study/study_<%=v_imgColor3%>_img1.gif" rowspan="3" width="5" height="23"></td>
                      <td background="/images/admin/study/study_<%=v_imgColor3%>_img3.gif" height="5"></td>
                      <td rowspan="3" background="/images/admin/study/study_<%=v_imgColor3%>_img2.gif" width="5" height="23"></td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_<%=v_imgColor3%>_img5.gif" align="center" height="16" valign="middle">
                      <a href="javascript:select_tab('graduation')"><b><font color="#FFFFFF">수료과정</font></b></a>
                      </td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_<%=v_imgColor3%>_img4.gif" height="2"></td>
                    </tr>
                  </table>
                </td>
                <td width="2"></td>
                <td width="110">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td background="/images/admin/study/study_<%=v_imgColor5%>_img1.gif" rowspan="3" width="5" height="23"></td>
                          <td background="/images/admin/study/study_<%=v_imgColor5%>_img3.gif" height="5"></td>
                          <td rowspan="3" background="/images/admin/study/study_<%=v_imgColor5%>_img2.gif" width="5" height="23"></td>
                        </tr>
                        <tr>
                          <td background="/images/admin/study/study_<%=v_imgColor5%>_img5.gif" align="center" height="16" valign="middle">
                          <a href="javascript:select_tab('offpropose')"><b><font color="#FFFFFF">(오)신청과정</font></b></a>
                          </td>
                        </tr>
                        <tr>
                          <td background="/images/admin/study/study_<%=v_imgColor5%>_img4.gif" height="2"></td>
                        </tr>
                     </table>
                </td>
                <td width="2"></td>
                <td width="110">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td background="/images/admin/study/study_<%=v_imgColor6%>_img1.gif" rowspan="3" width="5" height="23"></td>
                          <td background="/images/admin/study/study_<%=v_imgColor6%>_img3.gif" height="5"></td>
                          <td rowspan="3" background="/images/admin/study/study_<%=v_imgColor6%>_img2.gif" width="5" height="23"></td>
                        </tr>
                        <tr>
                          <td background="/images/admin/study/study_<%=v_imgColor6%>_img5.gif" align="center" height="16" valign="middle">
                          <a href="javascript:select_tab('offgraduation')"><b><font color="#FFFFFF">(오)수료과정</font></b></a>
                          </td>
                        </tr>
                        <tr>
                          <td background="/images/admin/study/study_<%=v_imgColor6%>_img4.gif" height="2"></td>
                        </tr>
                     </table>
                </td>
                <td>&nbsp;</td>
              </tr>
            </table>


            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td bgcolor="#636563">
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td bgcolor="#FFFFFF" valign="top">

<%  if(v_selTab.equals("propose") || v_selTab.equals("")){      %>      <!---- 신청과정 ---->
<%@ include file="/learn/admin/study/za_PersonalPropose_L.jsp"    %>
<%  }else if(v_selTab.equals("education")){      %>                     <!---- 수강과정 ---->
<%@ include file="/learn/admin/study/za_PersonalEducation_L.jsp"  %>
<%  }else if(v_selTab.equals("graduation")){      %>                    <!---- 수료과정 ---->
<%@ include file="/learn/admin/study/za_PersonalGraduation_L.jsp" %>
<%  }else if(v_selTab.equals("scorecomplete")){   %>                    <!---- 학점이수현황 ---->
<%@ include file="/learn/admin/study/za_PersonalScoreComplete_L.jsp" %>
<%  }else if(v_selTab.equals("offpropose")){   %>                    <!---- 오프라인 신청과정 ---->
<%@ include file="/learn/admin/study/za_PersonalOffPropose_L.jsp" %>
<%  }else if(v_selTab.equals("offgraduation")){   %>                    <!---- 오프라인 수료과정 ---->
<%@ include file="/learn/admin/study/za_PersonalOffGraduation_L.jsp" %>
<%  }   %>
                      </td>
                    </tr>
                    <tr style="display:none;">
                      <td bgcolor="#FFFFFF" valign="top">
    <br>
            <h2><img src="/images/portal/myclass/h2_tit6.gif" alt="보유포인트" class="fl_l" /><p class="category">보유 포인트 : <strong><%= v_availablepoint_text %></strong> Point</p></h2>
            <div id="tab_1">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="110" height="23" align="center" valign="middle">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/images/admin/study/study_blue_img1.gif" rowspan="3" width="5" height="23"></td>
                      <td background="/images/admin/study/study_blue_img3.gif" height="5"></td>
                      <td rowspan="3" background="/images/admin/study/study_blue_img2.gif" width="5" height="23"></td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_blue_img5.gif" align="center" height="16" valign="middle">
                      <a href="javascript:showTab(1)"><b><font color="#FFFFFF">보유포인트</font></b></a>
                      </td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_blue_img4.gif" height="2"></td>
                    </tr>
                  </table>
                </td>
                <td width="2"></td>
                <td width="110">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/images/admin/study/study_black_img1.gif" rowspan="3" width="5" height="23"></td>
                      <td background="/images/admin/study/study_black_img3.gif" height="5"></td>
                      <td rowspan="3" background="/images/admin/study/study_black_img2.gif" width="5" height="23"></td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_black_img5.gif" align="center" height="16" valign="middle">
                      <a href="javascript:showTab(2)"><b><font color="#FFFFFF">적립포인트</font></b></a>
                      </td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_black_img4.gif" height="2"></td>
                    </tr>
                  </table>
                </td>
                <td width="2"></td>
                <td width="110">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/images/admin/study/study_black_img1.gif" rowspan="3" width="5" height="23"></td>
                      <td background="/images/admin/study/study_black_img3.gif" height="5"></td>
                      <td rowspan="3" background="/images/admin/study/study_black_img2.gif" width="5" height="23"></td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_black_img5.gif" align="center" height="16" valign="middle">
                      <a href="javascript:showTab(3)"><b><font color="#FFFFFF">사용포인트</font></b></a>
                      </td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_black_img4.gif" height="2"></td>
                    </tr>
                  </table>
                </td>
                <td width="2"></td>
                <td width="110">
                </td>
                <td>&nbsp;</td>
              </tr>
            </table>

            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td bgcolor="#636563">
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td bgcolor="#FFFFFF" valign="top">
                      <br>
                      <table class="box_table_out_1" cellspacing="1" cellpadding="5" width="940" >
                        <tr>
                          <td colspan="9" class="table_top_line"></td>
                        </tr>
            <tr>
                <td class="table_title tdBorder">변경날자</td>
                <td class="table_title tdBorder">적립포인트</td>
                <td class="table_title tdBorder">사용포인트</td>
                <td class="table_title tdBorder">보유포인트</td>
            </tr>
            <tbody>
<%
    for(int i = 0; i < selectHavePointList.size(); i++) {

        DataBox dbox1 = (DataBox)selectHavePointList.get(i);

        String v_tid            =  dbox1.getString("d_tid");
        String v_getdate        =  dbox1.getString("d_getdate");
        String v_getpoint =  s_fmt.format(dbox1.getInt("d_getpoint"));
        String v_usepoint =  s_fmt.format(dbox1.getInt("d_usepoint"));
        String v_usepoint_text  =  dbox1.getString("d_usepoint");
        String v_lefrpoint =  s_fmt.format(dbox1.getInt("d_lefrpoint"));
        String v_expiredate =  dbox1.getString("d_expiredate");

        v_getdate      = FormatDate.getFormatDate(v_getdate,"yyyy.MM.dd");
%>
            <tr onmouseover="className='over'" onmouseout="className=' '">
                <td class="table_01"><%= v_getdate %></td>
                <td class="table_01"><%= v_getpoint %></td>
                <td class="table_01"><%= "0".equals(v_usepoint) ? v_usepoint_text : v_usepoint %></td>
                <td class="table_01"><strong><%= v_lefrpoint %></strong></td>
            </tr>
<%
    }
%>
<%  if(selectHavePointList.size() == 0 ){%>
            <tr>
                <td height="3" colspan="4" align="center">포인트 내역이 없습니다.</td>
            </tr>
<%  }%>
            </tbody>
            </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>

            </div>
            <div id="tab_2" STYLE="display:none">

            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="110" height="23" align="center" valign="middle">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/images/admin/study/study_black_img1.gif" rowspan="3" width="5" height="23"></td>
                      <td background="/images/admin/study/study_black_img3.gif" height="5"></td>
                      <td rowspan="3" background="/images/admin/study/study_black_img2.gif" width="5" height="23"></td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_black_img5.gif" align="center" height="16" valign="middle">
                      <a href="javascript:showTab(1)"><b><font color="#FFFFFF">보유포인트</font></b></a>
                      </td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_black_img4.gif" height="2"></td>
                    </tr>
                  </table>
                </td>
                <td width="2"></td>
                <td width="110">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/images/admin/study/study_blue_img1.gif" rowspan="3" width="5" height="23"></td>
                      <td background="/images/admin/study/study_blue_img3.gif" height="5"></td>
                      <td rowspan="3" background="/images/admin/study/study_blue_img2.gif" width="5" height="23"></td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_blue_img5.gif" align="center" height="16" valign="middle">
                      <a href="javascript:showTab(2)"><b><font color="#FFFFFF">적립포인트</font></b></a>
                      </td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_blue_img4.gif" height="2"></td>
                    </tr>
                  </table>
                </td>
                <td width="2"></td>
                <td width="110">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/images/admin/study/study_black_img1.gif" rowspan="3" width="5" height="23"></td>
                      <td background="/images/admin/study/study_black_img3.gif" height="5"></td>
                      <td rowspan="3" background="/images/admin/study/study_black_img2.gif" width="5" height="23"></td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_black_img5.gif" align="center" height="16" valign="middle">
                      <a href="javascript:showTab(3)"><b><font color="#FFFFFF">사용포인트</font></b></a>
                      </td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_black_img4.gif" height="2"></td>
                    </tr>
                  </table>
                </td>
                <td width="2"></td>
                <td width="110">
                </td>
                <td>&nbsp;</td>
              </tr>
            </table>

            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td bgcolor="#636563">
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td bgcolor="#FFFFFF" valign="top">
                      <br>
                      <table class="box_table_out_1" cellspacing="1" cellpadding="5" width="940" >
                        <tr>
                          <td colspan="9" class="table_top_line"></td>
                        </tr>
            <tr>
                <td class="table_title tdBorder">적립일자</td>
                <td class="table_title tdBorder">적립포인트</td>
                <td class="table_title tdBorder">적립내용</td>
                <td class="table_title tdBorder">수강료</td>
                <td class="table_title tdBorder"><p>결제금액</td>
            </tr>
            <tbody>
<%
    for(int i = 0; i < selectStoldPointList.size(); i++) {
        DataBox dbox2 = (DataBox)selectStoldPointList.get(i);

        String v_tid            =  dbox2.getString("d_tid");
        String v_getdate        =  dbox2.getString("d_getdate");
        String v_title          =  dbox2.getString("d_title");
        String v_getpoint =  s_fmt.format(dbox2.getInt("d_getpoint"));
        String v_subjprice      =  s_fmt.format(dbox2.getInt("d_subjprice"));
        String v_price          =  s_fmt.format(dbox2.getInt("d_price"));

        v_getdate      = FormatDate.getFormatDate(v_getdate,"yyyy.MM.dd");
%>
            <tr onmouseover="className='over'" onmouseout="className=' '">
                <td class="table_01"><%= v_getdate %></td>
                <td class="table_01"><%= v_getpoint %></td>
                <td class="table_01"><%= v_title %></td>
                <td class="table_01"><%= v_subjprice %></td>
                <td class="table_01"><strong><%= v_price %></strong></td>
            </tr>
<%
    }
%>
<%  if(selectHavePointList.size() == 0 ){%>
            <tr>
                <td height="20" colspan="5" align="center">포인트 내역이 없습니다.</td>
            </tr>
<%  }%>
            </tbody>
            </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>

            </div>
            <div id="tab_3" STYLE="display:none">

            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="110" height="23" align="center" valign="middle">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/images/admin/study/study_black_img1.gif" rowspan="3" width="5" height="23"></td>
                      <td background="/images/admin/study/study_black_img3.gif" height="5"></td>
                      <td rowspan="3" background="/images/admin/study/study_black_img2.gif" width="5" height="23"></td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_black_img5.gif" align="center" height="16" valign="middle">
                      <a href="javascript:showTab(1)"><b><font color="#FFFFFF">보유포인트</font></b></a>
                      </td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_black_img4.gif" height="2"></td>
                    </tr>
                  </table>
                </td>
                <td width="2"></td>
                <td width="110">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/images/admin/study/study_black_img1.gif" rowspan="3" width="5" height="23"></td>
                      <td background="/images/admin/study/study_black_img3.gif" height="5"></td>
                      <td rowspan="3" background="/images/admin/study/study_black_img2.gif" width="5" height="23"></td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_black_img5.gif" align="center" height="16" valign="middle">
                      <a href="javascript:showTab(2)"><b><font color="#FFFFFF">적립포인트</font></b></a>
                      </td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_black_img4.gif" height="2"></td>
                    </tr>
                  </table>
                </td>
                <td width="2"></td>
                <td width="110">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/images/admin/study/study_blue_img1.gif" rowspan="3" width="5" height="23"></td>
                      <td background="/images/admin/study/study_blue_img3.gif" height="5"></td>
                      <td rowspan="3" background="/images/admin/study/study_blue_img2.gif" width="5" height="23"></td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_blue_img5.gif" align="center" height="16" valign="middle">
                      <a href="javascript:showTab(3)"><b><font color="#FFFFFF">사용포인트</font></b></a>
                      </td>
                    </tr>
                    <tr>
                      <td background="/images/admin/study/study_blue_img4.gif" height="2"></td>
                    </tr>
                  </table>
                </td>
                <td width="2"></td>
                <td width="110">
                </td>
                <td>&nbsp;</td>
              </tr>
            </table>

            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td bgcolor="#636563">
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td bgcolor="#FFFFFF" valign="top">
                      <br>
                      <table class="box_table_out_1" cellspacing="1" cellpadding="5" width="940" >
                        <tr>
                          <td colspan="9" class="table_top_line"></td>
                        </tr>
            <tr>
                <td class="table_title tdBorder">사용일자</td>
                <td class="table_title tdBorder">과정명</td>
                <td class="table_title tdBorder">사용포인트</td>
                <td class="table_title tdBorder">수강료</td>
                <td class="table_title tdBorder"><p>결제금액</td>
            </tr>
            <tbody>
<%
    for(int i = 0; i < list3.size(); i++) {

        DataBox dbox3 = (DataBox)list3.get(i);

        String v_tid            =  dbox3.getString("d_tid");
        String v_usedate        =  dbox3.getString("d_usedate");
        String v_title          =  dbox3.getString("d_title");
        String v_usepoint =  s_fmt.format(dbox3.getInt("d_usepoint"));
        String v_subjprice      =  s_fmt.format(dbox3.getInt("d_subjprice"));
        String v_price          =  s_fmt.format(dbox3.getInt("d_price"));

        v_usedate      = FormatDate.getFormatDate(v_usedate,"yyyy.MM.dd");
%>
            <tr onmouseover="className='over'" onmouseout="className=' '">
                <td class="table_01"><%= v_usedate %></td>
                <td class="table_01"><%= v_title %></td>
                <td class="table_01"><%= v_usepoint %></td>
                <td class="table_01"><%= v_subjprice %></td>
                <td class="table_01"><strong><%= v_price %></strong></td>
            </tr>
<%
    }
%>
<%  if(selectHavePointList.size() == 0 ){%>
            <tr>
                <td height="20" colspan="5" align="center">포인트 내역이 없습니다.</td>
            </tr>
<%  }%>
            </tbody>
            </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>

            </div>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_army.gif" alt="병역구분" class="fl_l" /></h3>
            <table class="write">
            <tr>
                <td class="intxt end">
                    <span>
                    <%  if ("".equals(addInfobox.get("d_militarystatus"))) { %>
                    <input type="radio" name="p_militarystatus" value="" checked/>해당없음&nbsp;&nbsp;
                    <%  } else { %>
                        <kocca_select:select sqlNum="0001.v2" name="p_militarystatus" styleClass="mg_l10" param="0093" param2='<%= addInfobox.get("d_militarystatus") %>' onChange="selectMilitary" selectedValue='<%= addInfobox.get("d_militarystatus") %>' isLoad="true" type="2.2"/></span>
                    <%  }   if("00000000000000000001".equals(addInfobox.get("d_militarystatus")))   { %>
                    <span class="mg_l20">
                        복무기간 <%= addInfobox.get("d_militarystart") %> ~ <%= addInfobox.get("d_militaryend") %>
                    </span>
                    <%  } else if ("00000000000000000003".equals(addInfobox.get("d_militarystatus"))) { %>
                    <span class="mg_l15">면제사유 : <%= addInfobox.get("d_militarymemo") %></span>
                    <%  } %>
                </td>
            </tr>
            </table>

<%  if(schoolList!=null && schoolList.size() >0) { %>
            <h3 class="pop_tit mg_b9">교육경험<img src="/images/portal/common/stit_applystep.gif" alt="신청과정" class="fl_l" /></h3>
            <table class="ac_list">
            <colgroup><col width="114px" /><col width="101px" /><col width="107px" /><col /><col width="101px" /></colgroup>
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
<%  for(DataBox schoolListBox : schoolList) { %>
            <tr>
                <td><%= schoolListBox.get("d_schoolname") %></td>
                <td><%= schoolListBox.get("d_status") %></td>
                <td><%= schoolListBox.get("d_send") %></td>
                <td><%= schoolListBox.get("d_major") %></td>
                <td><%= schoolListBox.get("d_place") %></td>
            </tr>
<%  } %>
            </tbody>
            </table>
<%  } %>


            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_trainnum.gif" alt="연수지명번호" class="fl_l" /><p class="btn"><a href="javascript:whenSimpleSave(3,'')" class="btn_gr"><span>등록</span></a></p> </h3>
            <table class="write" id="trainTable">
            <colgroup><col width="10%" /><col width="31%" /><col width="13%" /><col width="20%" /><col width="11%" /><col width="16%" /></colgroup>
            <tr>
                <th>코드입력</th>
                <td>
                    과정코드 <input type="text" style="width:65px;" name="pp_subj" id="pp_subj" maxlength="7">
                    년도 <input type="text" style="width:30px;" name="pp_year" id="pp_year" maxlength="4">
                    차수 <input type="text" style="width:30px;" name="pp_subjseq" id="pp_subjseq" maxlength="4">
                </td>
                <th>연수지명번호</th>
                <td><input type="text" style="width:162px;" name="p_yeunsuno" id="p_yeunsuno"></td>
                <th>소속교육청</th>
                <td class="end"><input type="text" name="p_yeonsunm" id="p_yeonsunm" style="width:100px;"></td>
            </tr>
            </table>
<%  if(yeunsunoList!=null && yeunsunoList.size() >0) { %>
            <table class="box_table_out_1" cellspacing="1" cellpadding="5" width="95%"  style="margin-left:25px">
            <thead>
            <tr>
                <th class="table_title tdBorder">과정명</th>
                <th class="table_title tdBorder" width="250px">연수번호</th>
                <th class="table_title tdBorder" width="160px">담당교육청명</th>
                <th class="table_title tdBorder" width="70px">교육시작</th>
                <th class="table_title tdBorder" width="70px">교육종료</th>
                <th class="table_title tdBorder" width="60px">삭제</th>
            </tr>
            </thead>
<%  for(DataBox yList : yeunsunoList) { %>
            <tr>
                <td class="table_01" style="text-align:left;"><%= yList.get("d_subjnm") %></td>
                <td class="table_01"><%= yList.get("d_YEUNSUNO") %></td>
                <td class="table_01"><%= yList.get("d_YEONSUNM") %></td>
                <td class="table_01"><% if(yList.get("d_EDUSTART") !=null && !yList.get("d_EDUSTART").equals("")) out.print(yList.get("d_EDUSTART").substring(0,4)+"/"+ yList.get("d_EDUSTART").substring(4,6)+"/"+yList.get("d_EDUSTART").substring(6,8)); %></td>
                <td class="table_01"><% if(yList.get("d_EDUEND") !=null && !yList.get("d_EDUEND").equals("")) out.print(yList.get("d_EDUEND").substring(0,4)+"/"+ yList.get("d_EDUEND").substring(4,6)+"/"+yList.get("d_EDUEND").substring(6,8)); %></td>
                <td class="table_01"><img src="/images/admin/portal/stat_0.gif" alt="삭제" style="CURSOR:POINTER" onclick="yeunsunoDelete('<%=yList.get("d_subj")%>','<%=yList.get("d_year")%>','<%=yList.get("d_subjseq")%>','<%=yList.get("d_userid")%>')"></td>
            </tr>
<%  } %>
            </tbody>
            </table>
<%  } %>


<%  if(licenseList!=null && licenseList.size() >0) { %>
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
            <tbody>
<%  for(DataBox licenseListBox : licenseList) { %>
            <tr>
                <td><%= licenseListBox.get("d_licensename") %></td>
                <td><%= licenseListBox.getDate("d_getdate", "-") %></td>
                <td><%= licenseListBox.get("d_publish") %></td>
            </tr>
<%  } %>
            </tbody>
            </table>
<%  } %>

<%  if(careerList!=null && careerList.size() >0) { %>
            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_certificate.gif" alt="자격증" class="fl_l" /></h3>
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
            <tbody id="careerListbox">
<%  for(DataBox careerListbox : careerList) { %>
            <tr>
                <td><%= careerListbox.get("d_jikup") %></td>
                <td><%= careerListbox.get("d_jikjong") %></td>
                <td><%= careerListbox.get("d_officename") %></td>
                <td><%= careerListbox.get("d_position") %></td>
                <td><%= careerListbox.get("d_workstart") %>~<%= careerListbox.get("d_workend") %></td>
                <td><%= careerListbox.get("d_workstatus") %></td>
            </tr>
<%  } %>
            </tbody>
            </table>
<%  } %>

            <h3 class="pop_tit"><img src="/images/portal/common/stit_resume.gif" alt="자기소개서" /></h3>
            <table class="write">
            <colgroup><col width="18%" /><col /></colgroup>
            <tr>
                <th>본인소개</th>
                <td>
                        <div style='width:750px;height:60px;overflow:auto;visibility:visible;border:1'>
                            <%= addInfobox.getHtmlString("d_intro") %>
                        </div>
                <!-- <textarea class="inbox" style="width:97%; height:30px;" readonly><%= addInfobox.get("d_intro") %></textarea> -->
                </td>
            </tr>
            <tr>
                <th>지원동기</th>
                <td>
                        <div style='width:750px;height:60px;overflow:auto;visibility:visible;border:1'>
                            <%= addInfobox.getHtmlString("d_motive") %>
                        </div>
                <!--<textarea class="inbox" style="width:97%; height:30px;" readonly><%= addInfobox.get("d_motive") %></textarea> -->
                </td>
            </tr>
            <tr>
                <th>향후계획</th>
                <td>
                        <div style='width:750px;height:60px;overflow:auto;visibility:visible;border:1'>
                            <%= addInfobox.getHtmlString("d_vision") %>
                        </div>
                <!--<textarea class="inbox" style="width:97%; height:30px;" readonly><%= addInfobox.get("d_vision") %></textarea> -->
                </td>
            </tr>
            <tr>
                <th>기타</th>
                <td>
                        <div style='width:750px;height:60px;overflow:auto;visibility:visible;border:1'>
                            <%= addInfobox.getHtmlString("d_etc") %>
                        </div>
                <!--<textarea class="inbox" style="width:97%; height:30px;" readonly><%= addInfobox.get("d_etc") %></textarea> -->
                </td>
            </tr>
            </table>



                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
      <!----------------- 개인이력 상세조회 끝 ----------------->
          </td>
        </tr>
      </table>

        <br>
      </td>
  </tr>
</table>
</form>

<SCRIPT LANGUAGE="JavaScript">
<!--
    //페이지를 열었을때 학력,직업 select box에 selected 되도록
   // document.form1.p_degree.value = "<%=v_degree%>";

//-->
</SCRIPT>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>

