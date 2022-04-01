<%
//**********************************************************
//  1. 제      목: 과정별 상담내역 보기
//  2. 프로그램명: za_PersonalCounsel_L
//  3. 개      요: 과정별 상담내역 보기(팝업)
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String v_resno      = "";  //주민등록번호
    String v_name       = "";  //이름
    String v_email      = "";  //이메일
    String v_hometel    = "";  //집전화
    String v_pwd        = "";  //비밀번호
    String v_userid     = "";  //id
    String v_handphone  = "";
	String v_membergubunnm = "";	//회원구분명
	String v_grcode = "";	//  그룹코드

    MemberData data = (MemberData)request.getAttribute("SelectMemberInfo");
    //v_resno      = data.getResno();
    v_resno      = data.getResno1()+data.getResno2();
    v_name       = data.getName();
    v_email      = data.getEmail();
    v_hometel    = data.getHometel();
    v_handphone  = data.getHandphone();
    v_pwd        = data.getPwd();
    v_userid     = data.getUserid();
	v_membergubunnm =  data.getMembergubunnm();
	v_grcode     = data.getGrcode();
	

    //v_resno = v_resno.substring(0, 6) + "-" + v_resno.substring(6);
    //v_resno = v_resno.substring(0, 6) + "-*******";
    
    //if(v_resno.length() > 12){
    //  v_resno = v_resno.substring(0, 6) + "-*******";
    //}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/hhi_lib.js"></SCRIPT>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
// 상담등록
function insertCounsel() {
        document.form1.action='/servlet/controller.study.CounselAdminServlet';
        document.form1.p_process.value = 'CounselInsertPage';
        document.form1.submit();
}

// 상담내역보기
function viewCounsel(no) {
        document.form1.p_no.value = no;
        document.form1.action='/servlet/controller.study.CounselAdminServlet';
        document.form1.p_process.value = 'CounselUpdatePage';
        document.form1.submit();

}

//분류 선택
function selectMcode() {
        document.form1.action='/servlet/controller.study.CounselAdminServlet';
        document.form1.p_process.value = 'CounselListPage';
        document.form1.submit();
}

//-->
</SCRIPT>

</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="800" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td align="left">
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
          <tr>

          <td align="center" bgcolor="#FFFFFF">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="../../../images/admin/study/tit_counsel.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
            <br>

            <table width="100%" cellspacing="1" cellpadding="5" class="open_table_out" border="0">
              <tr>
                <td colspan="4" class="table_top_line"></td>
              </tr>
              <tr>
                <td width="22%" class="table_title" width="22%" >ID</td>
                <td width="28%" class="table_02_2"  width="28%" ><%=v_userid%></td>
                <td width="22%" class="table_title" width="22%">비밀번호</td>
                <td width="28%" class="table_02_2"  width="28%">*****<!-- %=v_pwd%--></td>
              </tr>
              <tr>
                <td width="22%" class="table_title" width="22%" >성명</td>
                <td width="28%" class="table_02_2"  width="28%" ><%=v_name%></td>
                <td width="22%" class="table_title" width="22%">회원구분</td>
                <td width="28%" class="table_02_2"  width="28%"><%=v_membergubunnm%></td>
              </tr>
              <tr>
                <td width="22%" class="table_title">핸드폰</td>
                <td width="28%" class="table_02_2"><%=v_handphone%></td>
                <td width="22%" class="table_title">E-mail</td>
                <td width="28%" class="table_02_2"><%=v_email%></td>
              </tr>
            </table>

<%
    int    vc_no = 0;
    String p_subj     = box.getString("p_subj");
    String p_mcode    = box.getString("s_mcode");
    String vc_userid  = box.getString("p_userid");
    String vc_cuserid = "";
    String vc_name    = "";
    String vc_title   = "";
    String vc_ftext   = "";
    String vc_ctext   = "";
    String vc_mcode   = "";
    String vc_mcodenm = "";
    String vc_etime   = "";
    String vc_subj    = "";
    String vc_year    = "";
    String vc_subjseq = "";
    String vc_status  = "";
    String vc_sdate   = "";
    String vc_ldate   = "";
    String vc_gubun   = "";
    String vc_status_view = "";
    String vc_sdate_view  = "";
    String vc_ldate_view  = "";
    String vc_gubun_view  = "";
    int i = 0;

    ArrayList list1 = (ArrayList)request.getAttribute("counselList");
%>

<form name="form1" method = "post">
  <input type="hidden" name="p_process" value="<%=v_process%>">
  <input type="hidden" name="p_userid" value="<%=vc_userid%>">
  <input type="hidden" name="p_subj" value="<%=p_subj%>">
  <input type="hidden" name="p_no"     value="">
  <input type="hidden" name="s_grcode"     value="<%=v_grcode%>">

                        <table width="97%" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td align="left" width="80%" >분류&nbsp;&nbsp;
                                <%=CodeConfigBean.getCodeGubunSelect (CounselAdminBean.COUNSEL_KIND, "", 1, "s_mcode", p_mcode,"onChange= 'javascript:selectMcode()'",  2)%>
                            </td>
                            <td align="right" height="20" >
                            <a href="javascript:insertCounsel()"><img src="/images/admin/button/btn_counsel_insert.gif" border="0" alt="상담등록"></a>
                            </td>
                        </tr>
                        </table>

                      <div style='width:760px;height:370px;overflow:auto;visibility:visible;border:1 '>
                      <table class="box_table_out" cellspacing="1" cellpadding="5">
                        <tr>
                          <td colspan="9" class="table_top_line"></td>
                        </tr>
                        <tr>
                          <td class="table_title" ><b>NO</b></td>
                          <td class="table_title" ><b>분류</b></td>
                          <td class="table_title" ><b>내역</b></td>
                          <td class="table_title" ><b>작성자</b></td>
                          <td class="table_title" ><b>처리상태</b></td>
                          <td class="table_title" ><b>상담일자</b></td>
                          <td class="table_title" ><b>구분</b></td>
                        </tr>
<%

    for (i=0; i<list1.size();i++) {
        DataBox dbox1   = (DataBox)list1.get(i);
        vc_no      = dbox1.getInt("d_no");
        vc_userid  = dbox1.getString("d_userid");
        vc_name    = dbox1.getString("d_name");
        vc_cuserid = dbox1.getString("d_cuserid");
        vc_title   = dbox1.getString("d_title");
        vc_ftext   = dbox1.getString("d_ftext");
        vc_ctext   = dbox1.getString("d_ctext");
        vc_mcode   = dbox1.getString("d_mcode");
        vc_mcodenm = dbox1.getString("d_mcodenm");
        vc_etime   = dbox1.getString("d_etime");
        vc_subj    = dbox1.getString("d_subj");
        vc_year    = dbox1.getString("d_year");
        vc_subjseq = dbox1.getString("d_subjseq");
        vc_sdate   = dbox1.getString("d_sdate");
        vc_status  = dbox1.getString("d_status");
        vc_ldate   = dbox1.getString("d_ldate");
        vc_gubun   = dbox1.getString("d_gubun");

        //vc_status  1 : 미처리, 2 : 처리중, 3 : 완료
        if (vc_status.equals("1")) {
            vc_status_view = "미처리";
        } else if (vc_status.equals("2")) {
            vc_status_view = "처리중";
        } else {
            vc_status_view = "완료";
        }

        // vc_gubun   in : 수신 , out : 발신
        if (vc_gubun.equals("in")) {
            vc_gubun_view = "수신";
        } else if (vc_gubun.equals("out")) {
            vc_gubun_view = "발신";
        }

        vc_sdate_view = FormatDate.getFormatDate(vc_sdate,"yyyy/MM/dd");
        vc_ldate_view = FormatDate.getFormatDate(vc_ldate,"yyyy/MM/dd");
%>
                        <tr>
                          <td class="table_01"><%=list1.size()-i%></td>
                          <td class="table_02_1"><%=vc_mcodenm%></td>
                          <td class="table_02_1"><a href="javascript:viewCounsel('<%=vc_no%>')"><%=vc_title%></a></td>
                          <td class="table_02_1"><%=vc_name%></td>
                          <td class="table_02_1"><%=vc_status_view%></td>
                          <td class="table_02_1"><%=vc_sdate_view%></td>
                          <td class="table_02_1"><%=vc_gubun_view%></td>
                        </tr>
<%
    }

    if (i==0){
%>

                        <tr>
                          <td class="table_01" colspan="7">등록된 내용이 없습니다.</td>
                        </tr>
<%
    }
%>
                      </table>
                      </div>




            <!----------------- 닫기 버튼 시작 ----------------->
            <table cellspacing="0" cellpadding="0" class="open_table1">
              <tr>
                <td align="right" style="padding-top=10">
                <a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a>
                </td>
              </tr>
            </table>
            <!----------------- 닫기 버튼 끝 ----------------->
              <br>




          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
