<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.beta.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_subjnm    = box.getString("p_subjnm");

    String  v_subj= box.getString("p_subj");

 //   DataBox dbox = (DataBox)request.getAttribute("ETestMasterData");

%>
<html>
<head>
<title>콘텐츠 일괄 등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script type="text/javascript" src="/js/jquery/1.11.1/jquery-1.11.1.min.js"></script>
<SCRIPT LANGUAGE="JavaScript">

	$(document).ready(function(){
		
		$("input[name='p_excelInsert']").on("click", function() {
			fnCmdSelectView($(this).val());
		});
	});
		
	
	function fnCmdSelectView($this){
		
		$(".subjlesson").hide();
		$(".subtitle").hide();
		
		$("."+$this).show();
	}
	
    function insert_check(process)
    {
        if (document.form1.p_file.value.length > 0 ){
            var data = document.form1.p_file.value;
            data = data.toUpperCase(data);
            
            if (data.indexOf(".XLS") < 0 || data.indexOf(".XLSX") > 0) {
                alert("DB로 입력되는 파일종류는 xls 파일만 가능합니다.");
                return;
            }
        }

        document.form1.action = "/servlet/controller.contents.MasterFormServlet";
        document.form1.p_process.value = process;
        document.form1.submit();
    }
    
    
    
    
    
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<!----------------- form 시작 ----------------->
<form name="form1" id="form1" method="post" enctype="multipart/form-data">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="s_subj"       value="<%=v_subj%>">

<table width="90%" border="0" cellspacing="0" cellpadding="0" style="padding-left:10px">
    <tr>
        <td height="10px" colspan="2"></td>
    </tr>
    <tr>
        <td width="1%" class="sub_title"><img src="/images/admin/common/icon.gif" />차시 엑셀 등록</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td height="10px" colspan="2"></td>
    </tr>
    <tr>
        <td class="sub_title" colspan="2">
        	<input type="radio" name="p_excelInsert" id="p_excelLessonInsert" value="subjlesson" /><label for="p_excelLessonInsert">일괄등록</label>&nbsp;&nbsp;&nbsp;
        	<input type="radio" name="p_excelInsert" id="p_excelSubtitleInsert" value="subtitle" checked="checked" /><label for="p_excelSubtitleInsert">자막등록</label>
        </td>
    </tr>
    <tr>
		<td>
			<input type="file" name="p_file" id="p_file" class="input" value="" />
		</td>
    </tr>
    <tr>
        <td height="7px" colspan="2"></td>
    </tr>
    <tr>
        <td align="right" colspan="2"><a href="javascript:insert_check('excelInsertToDB')"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
    </tr>
    <tr>
        <td colspan="2">
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="subjlesson" style="display: none;">
                <tr>
                    <td class=dir_txt ><b><font size="3">(주의사항)</font></b></td>
                </tr>
                <tr>
                    <td height="8"></td>
                </tr>
                <tr>
                    <td height="20" class=dir_txt >1. 엑셀파일 저장시 엑셀 형식으로 저장하여 사용할 것</td>
                </tr>
                <tr>
                    <td height="20" class=dir_txt >2. 엑셀 파일 목록에서 과정별로 업로드 가능(과정코드 필수입력).</td>
                </tr>
                <tr>
                    <td height="20" class=dir_txt >3. 엑셀 파일 첫줄에 차시명,모듈(두자리),과정코드,콘텐츠위치(생략가능하나 자리수는 존재해야됨),강사ID로 작성해서 올려주세요.</td>
                </tr>
                <tr>
                    <td height="20" class=dir_txt >4. 엑셀 파일은 버전 2003 이하로 저장해서 사용할것(2007에서 2003으로 다른이름저장도 가능).</td>
                </tr>
            </table>
            
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="subtitle">
                <tr>
                    <td class=dir_txt ><b><font size="3">(주의사항)</font></b></td>
                </tr>
                <tr>
                    <td height="8">&nbsp;</td>
                </tr>
                <tr>
                    <td height="40" class=dir_txt >1. 샘플양식을 받아 사용할 것(확장자는 .xls만 사용) &nbsp;<a href="/upload/subjlesson/koccaSubtitle_Sample.xls"><b>샘플양식 다운로드</b></a>
                    	<br />&nbsp;&nbsp;&nbsp;<u>샘플 양식 형태가 아닌 경우 치명적인 오류가 발생할 수 있습니다.</u>	
                    </td>
                </tr>
                <tr>
                    <td height="20" class=dir_txt >2. 엑셀 파일 목록에서 과정별로 업로드 가능(과정코드, 모듈, 차시코드 필수입력).</td>
                </tr>
                <tr>
                    <td height="20" class=dir_txt >3. 반드시 차시가 등록된 상태에서 자막 등록해야 함.</td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form>
<!----------------- form 끝 ----------------->
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>

