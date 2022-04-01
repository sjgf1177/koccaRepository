<%
//**********************************************************
//  1. 제      목: 과정차수별공지사항
//  2. 프로그램명: za_SubjGongAll_I.jsp
//  3. 개      요: 과정차수별공지사항 선택과목등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정은년
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process        = box.getString("p_process");
    String  v_types          = box.getString("p_types");

    String  ss_grcode    = box.getString("s_grcode");        //교육그룹
    String  ss_gyear     = box.getString("s_gyear");         //년도
    String  ss_grseq     = box.getStringDefault("s_grseq","ALL");         //교육차수
    String  ss_uclass    = box.getString("s_uclass");        //과정분류
    String  ss_subjcourse= box.getString("s_subjcourse");    //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");       //과정 차수
    String  ss_isclosed  = box.getString("s_isclosed");      //진행여부
    int v_pageno         = box.getInt("p_pageno");

    Vector  v_checks   	 = box.getVector("p_checks");		 //선택된 과정코드 + 년도 + 과정차수

  	String content 	= "";
  	String width	= "540";
  	String height	= "300";

	String v_chkvalue = "";

	for(int i = 0; i < v_checks.size(); i++) {
		String v_temp = (String)v_checks.elementAt(i);
		v_chkvalue += v_temp;
		if (i < v_checks.size() - 1 ) v_chkvalue += ",";
	}

	StringTokenizer st = new StringTokenizer(v_chkvalue, ",");

	while(st.hasMoreElements()) {
		String token = st.nextToken();

		StringTokenizer st2 = new StringTokenizer(token, "/");

		while(st2.hasMoreElements()) {
			String v_subj 	 = st2.nextToken();
			String v_year 	 = st2.nextToken();
			String v_subjseq = st2.nextToken();

			break;
		}
	}

    String v_title      = "";
    String v_adcontent  = "";

    v_title      = box.getString("p_title");
    v_adcontent  = box.getString("adcontent");

	String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;
%>

<html>
<head>
<title>::: 공지사항 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
    
<SCRIPT LANGUAGE="JavaScript">
    $(document).ready(function(){
        var title='<%=v_title%>';
        var types='<%=v_types%>';
        var opt=$("[name='p_types']");
        var opt_html=$(opt).html();
        $(opt).html("");
        $(opt).append("<option value='T'>선택하세요</option>");
        $(opt).append(opt_html);

        if(title=="")
            $(opt).attr("selectedIndex","0");
        else
        {
            var ascii= types.charCodeAt(0)-("A").charCodeAt(0)+1;
            $("option:eq("+ascii+")",opt).attr("selected","selected");
        }

        $(opt).bind("change",function(){
            document.form1.p_action.value="titleContent";
            document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
            document.form1.p_process.value = "allEditPageAll";
            document.form1.submit();
       });
    });
<!--
     // 문자열 트림
     function fnTrim(chStr) {
         var nStrCheck;
         nStrCheck = chStr.indexOf(" ");
         while (nStrCheck != -1)
         {
             chStr = chStr.replace(" ", "");
             nStrCheck  = chStr.indexOf(" ");
         }
         return chStr;
     }

    // 등록 저장
    function insert_check() {
        if(blankCheck(document.form1.p_title.value)){
            alert("제목을 입력하세요");
            document.form1.p_title.focus();
            return;
        }

        if (document.form1.p_title.value == "") {
            alert("제목을 입력하세요");
            document.form1.p_title.focus();
            return;
        }
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }

		//if(document.all.use_editor[0].checked) {
		//		form1.p_adcontent.value = ObjEditor.document.all.tags("html")[0].outerHTML;
		//}else {
		//		form1.p_adcontent.value = document.all.txtDetail.value;
		//}
        //alert(fnTrim(document.form1.Wec.TextValue));
        //alert(document.form1.Wec.Value);


		document.form1.p_adcontent.value = document.form1.Wec.MIMEValue;
        document.form1.p_adcontent.value.replace("&","&amp;");

        if (document.form1.Wec.TextValue.length < 3) {
            alert("내용을 입력하세요");
            return;
        }

        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "update";
        document.form1.submit();
    }

    // 목록으로
    function list() {
        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "selectAll";
        document.form1.submit();
    }

    // 공지구분
    function selectCategory() {
        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "insertPageAll";
        document.form1.submit();
    }


    // 초기화
    function initPage()
    {
        //document.form1.Wec.TextValue = document.form1.p_adcontent.value;
        document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>";
    }
//-->
</SCRIPT>
</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0"  onload="initPage()">
<form name="form1" method="post" enctype = "multipart/form-data">
    <input type = "hidden" name = "p_process"  value = "<%= v_process %>">

    <input type='hidden' name='s_grcode'     value='<%=ss_grcode%>'>
    <input type='hidden' name='s_gyear'      value='<%=ss_gyear%>'>
    <input type='hidden' name='s_grseq'      value='<%=ss_grseq%>'>
    <input type='hidden' name='s_uclass'     value='<%=ss_uclass%>'>
    <input type='hidden' name='s_subjcourse' value='<%=ss_subjcourse%>'>
    <input type='hidden' name='s_subjseq'    value='<%=ss_subjseq%>'>
    <input type='hidden' name='s_isclosed'   value='<%=ss_isclosed%>'>
	<input type='hidden' name='p_checks'     value='<%=v_chkvalue%>'>
    <input type='hidden' name="p_adcontent"  value='<%=v_adcontent%>'>
    <input type="hidden" name="p_pageno"     value="<%=v_pageno%>">
    <input type="hidden" name="p_oldtypes"     value="<%=v_types%>">
    <input type="hidden" name="p_action">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
      <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/course/co_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

        <br>
        <!----------------- 전체공지 시작 ----------------->
        <table width="97%" class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="4" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" rowspan="2" width="16%" ><b>공지구분</b></td>
            <td class="table_02_2" rowspan="2" width="44%">
              <!-- 공지구분 SELECT BOX  START   -->
              <%= CodeConfigBean.getCodeSelect("noticeCategory","",1,"p_types","","",0,"") %>
              <!-- 공지구분 SELECT BOX  END     -->
            </td>

            <td class="table_title" height="25" width="16%"><b>과정명</b></td>
            <td class="table_title" width="15%"><b>v_subjnm</b></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>교육일수</b></td>
            <td class="table_title"><b>v_edudates</b></td>
          </tr>
          <tr>
            <td class="table_title" ><b>제목</b></td>
            <td class="table_02_2" > <input name="p_title" type="text" class="input" size="98" value="<%=v_title%>"></td>
            <td class="table_title" height="25"><b>교육기간</b></td>
            <td class="table_title"><b>v_eduperiod</b></td>
          </tr>

          <tr>
            <td class="table_title" rowspan="15"><b>내용</b></td>
            <td class="table_02_2" rowspan="15">
                <!-- 나모 Editor  -->
                <p align="center">
                <p align="left">
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">namoActiveInitCompleted("p_adcontent");</script>
                <script language="javascript">object_namoActivepatch('684','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
                </p>
                <!-- 나모 Editor  -->
            </td>
            <td class="table_title" height="25"><b>운영자 이름</b></td>
            <td class="table_title"><b>v_name</b></td>
         </tr>
          <tr>
            <td class="table_title" height="25"><b>운영자 전화</b></td>
            <td class="table_title"><b>v_comptel</b></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>운영자 E-mail</b></td>
            <td class="table_title"><b>v_email</b></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>가중치(진도율)</b></td>
            <td class="table_title"><b>v_wstep</b></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>가중치(중간평가)</b></td>
            <td class="table_title"><b>v_wmtest</b></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>가중치(최종평가)</b></td>
            <td class="table_title"><b>v_wftest</b></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>가중치(형성평가)</b></td>
            <td class="table_title"><b>v_whtest</b></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>가중치(리포트)</b></td>
            <td class="table_title"><b>v_wreport</b></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>가중치(액티비티)</b></td>
            <td class="table_title"><b>v_wact</b></td>
          </tr>
          <tr>
            <td class="table_title" height="25" ><b>수료기준점수</b></td>
            <td class="table_title"><b>v_gradscore</b></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>수료기준진도율</b></td>
            <td class="table_title" ><b>v_gradstep</b></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>수료기준평가</b></td>
            <td class="table_title" ><b>v_gradexam</b></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>수료기준리포트</b></td>
            <td class="table_title" ><b>v_gradreport</b></td>
          </tr>
          <!--tr>
            <td class="table_title" height="25"><b>이수점수</b></td>
            <td class="table_title"><b>v_point</b></td>
          </tr-->
          <tr>
            <td class="table_title" height="25"><b>시작일</b></td>
            <td class="table_title"><b>v_edustart</b></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>종료일</b></td>
            <td class="table_title"><b>v_eduend</b></td>
          </tr>
		  <tr>
			<td class = "table_title" width = "12%" align = "center">첨부파일</td>
			<td class = "table_02_2"  align = "left" colspan="3">
            <%
            int    i_fileLimit         = NoticeAdminBean.getFILE_LIMIT();              // 제한 첨부 파일수
            %>
            <%@ include file="/learn/admin/include/za_MultiAttach_I.jsp" %>
			</td>
		  </tr>
        </table>
        <!----------------- 전체공지 끝 ----------------->


        <br>
        <!----------------- 저장, 취소 버튼 시작 ----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href='javascript:insert_check()'><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
            <td width=5>&nbsp;</td>
            <td align="center"><a href='javascript:list()'><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->

        <br>
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>

</body>
</html>
