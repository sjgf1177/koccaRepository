<%
//**********************************************************
//  1. 제      목: 마스터폼 리스트
//  2. 프로그램명 : za_MasterForm_L.jsp
//  3. 개      요: 마스터폼 리스트 조회화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 24
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  ss_classtype   = box.getStringDefault("s_classtype",  "upper");
    String  ss_upperclass  = box.getStringDefault("s_upperclass", "ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass", "ALL");
    String  ss_area		   = box.getStringDefault("s_area", "ALL");

//    String  ss_contenttype = box.getStringDefault("s_contenttype","ALL");
    String  ss_contenttype = box.getStringDefault("s_contenttype","ALL");
    String  p_action       = box.getString("p_action");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language="javascript">

        // 선택시
        function whenSelection(action){
            if (action=="go"){
                top.ftop.setPam();
            }
            document.form1.p_action.value = action;
            document.form1.p_process.value="listPage";
            document.form1.submit();
        }

        // 대분류 선택시
        function changeUpperclass() {
            document.form1.p_process.value="listPage";
            document.form1.p_action.value="";
            document.form1.submit();
        }
        // 중분류 선택시
        function changeMiddleclass() {
            changeUpperclass();
        }
        // 소분류 선택시
        function changeLowerclass() {
            changeUpperclass();
        }
        // 선택
        function whenSubmit(){
            document.form1.p_process.value="listPage";
            document.form1.p_action.value="go";
            document.form1.submit();
        }
        // 수정시
        function whenChange(subj){
            document.form1.p_subj.value=subj;
            document.form1.p_process.value="updatePage";
            document.form1.submit();
        }
        //정렬
        function whenOrders(ord){
            document.form1.p_process.value="listPage";
            document.form1.p_order.value=ord;
            whenSelection('go');
        }
        // 학습창 미리보기
        function whenViewEdu(url){
        	document.form1.lessonReurl.value = url;
			document.form1.lessonReiurl.value = "";
            window.open(url,'EduStart','width=800,height=600');
        }

        // 콘텐츠위치
        function whenDir(subj) {
            document.form1.p_subj.value=subj;

            window.self.name = "winContents";
            farwindow = window.open("", "openDir", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 600, height = 600, top=0, left=0");
            document.form1.target = "openDir";
            document.form1.action = "/servlet/controller.contents.MasterFormServlet";
            document.form1.p_process.value="contentscolocateview";
            document.form1.submit();

            farwindow.window.focus();
            document.form1.target = window.self.name;

        }

// 예스런 미리보기
function studyOpen(url) {

   if (url == '') {
	   alert("정보가 없습니다");
   } else {
	   //open_window('openApp',url,0,0,0,0,false,false,true,true,true);
	   open_window('openApp',url,0,0,1024,768,false,false,true,true,true);
   }

}

function studyOpenRe() {
	document.form1.lessonRepopup.value = "1";
	var rurl = document.form1.lessonReurl.value;
	var riurl = document.form1.lessonReiurl.value;
	setTimeout("whenViewEdu('"+rurl+"');" ,100);
}

//select box 설정
$(function() {
	$("#oUpperclass").bind("change", function(){
        var param = "type=sqlID&sqlID=selectBox.subjAttMiddleList&param=" + $(this).val();
        $.ajaxSetup({cache:false});
        $.ajax({
                type : "get"
            ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
            ,   dataType : "json"
            ,   data : param
            ,   success : fnSetMiddleClass
            ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                }
            ,   error :  function(arg1, arg2) {
                    // alert("error : " + arg1);
                }
        });
    });

    $("#oMiddleclass").bind("change", function(){
        var param = "type=sqlID&sqlID=selectBox.subjAttLowerList&param=" + $("#oUpperclass").val() + "," + $(this).val();
        $.ajaxSetup({cache:false});
        $.ajax({
                type : "get"
            ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
            ,   dataType : "json"
            ,   data : param
            ,   success : fnSetLowerClass
            ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                }
            ,   error :  function(arg1, arg2) {
                    // alert("error : " + arg1);
                }
        });
    });
});

/**
 * 검색조건에서 대분류 선택시 callback function
 */
function fnSetMiddleClass( result ) {
    $("#oMiddleclass").empty();
    $("#oMiddleclass").append("<option value=\"\">== 전체 ==</option>");
    $("#oLowerclass").empty();
    $("#oLowerclass").append("<option value=\"\">== 전체 ==</option>");

    if ( result.selectBoxList.length > 0 ) {
        $.each( result.selectBoxList, function() {
            $("#oMiddleclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
        });

    }
}


/**
 * 검색조건에서 중분류 선택시 callback function
 */
function fnSetLowerClass( result ) {
    $("#oLowerclass").empty();
    $("#oLowerclass").append("<option value=\"\">== 전체 ==</option>");

    if ( result.selectBoxList.length > 0 ) {
        $.each( result.selectBoxList, function() {
            $("#oLowerclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
        });

    }
}

    </script>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">


      <!----------------- title 시작 ----------------->
     <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/course/c_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

        <br>

        <!----------------- form 시작 ----------------->
                <form name="form1" method="post" action="/servlet/controller.contents.MasterFormServlet">
                <!-- 학습창 다시띄우기용 1:다시 안띄우기  2:다시띄우기(학습컨텐츠에서 닫았을경우) -->
				    <input type='hidden' name='lessonRepopup' value="1">
				    <input type='hidden' name='lessonReurl' value="">
				    <input type='hidden' name='lessonReiurl' value="">
                
        <table width="97%" border="0" cellspacing="0" cellpadding="1" >
          <tr>
            <td bgcolor="#C6C6C6" align="center">
              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="form_table_bg">
                <tr>
                  <td height="7"></td>
                </tr>
                <tr>
                    <input type="hidden" name="p_process" value="listPage">
                    <input type="hidden" name="p_action"  value="">
                    <input type="hidden" name="p_subj"    value="">
                    <input type="hidden" name="p_order"   value="subjnm">

                  <td align="center">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                      <tr>
                        <td align="left" valign="middle">&nbsp;
<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>	
	분류 <kocca:selectBox name="s_area" optionTitle="== 전체 ==" type="sqlID" sqlID="code.list.0001" param="0101"  selectedValue="<%= ss_area %>" isLoad="true" />&nbsp;&nbsp;
	대분류 
	<kocca:selectBox name="s_upperclass" id="oUpperclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttUpperList" selectedValue="<%= ss_upperclass %>" isLoad="true" />
	중분류 
<%
    if ( !ss_middleclass.equals("") ) {
%>
									<kocca:selectBox  name="s_middleclass" id="oMiddleclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttMiddleList" param="<%= ss_upperclass %>" selectedValue="<%= ss_middleclass %>" isLoad="true" />
<%
    } else {
%>
                                    <select name="s_middleclass" id="oMiddleclass">
                                    	<option value="">== 전체 ==</option>
                                    </select>
<%
    }
%>	
	소분류
<%
    if ( !ss_lowerclass.equals("") ) {
%>
                                    <kocca:selectBox name="s_lowerclass" id="oLowerclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttLowerList" param="<%= ss_upperclass + ',' + ss_middleclass %>" selectedValue="<%= ss_lowerclass %>" isLoad="true" />
<%
    } else {
%>
                                    <select name="s_lowerclass" id="oLowerclass">
                                        <option value="">== 전체 ==</option>
                                    </select>
<%
    }
%>	
	
                          </td>
                         </tr>
                        <tr><td  height="5px"></td></tr>
                        <tr>
                            <td  align="left" valign="middle" style="padding-left:8px">
                          ContentType
                          <select name = "s_contenttype" onChange="javascript:whenSelection('go')">
                            <option value="ALL" <% if (ss_contenttype.equals("ALL")){ %>selected<%}%>>ALL</option>
                            <option value="M"   <% if (ss_contenttype.equals("M")){ %>selected<%}%>>NORMAL(OLD)</option>
                            <option value="N"   <% if (ss_contenttype.equals("N")){ %>selected<%}%>>NORMAL(NEW)</option>
                            <option value="L"   <% if (ss_contenttype.equals("L")){ %>selected<%}%>>LINK</option>
<%                      if (GetCodenm.get_config("useOBC").equals("Y") || GetCodenm.get_config("useSCORM").equals("Y")) {%>
                            <option value="O"   <% if (ss_contenttype.equals("O")){ %>selected<%}%>>OBC</option>
                            <option value="S"   <% if (ss_contenttype.equals("S")){ %>selected<%}%>>SCORM</option>
<%                      }%>
                          </select>
                          과정명 <input id="inputbox" name="s_inputbox">
                          </td>
                          <td width="10%" align="center">
                          <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td height="7"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
                </form>
        <!----------------- form 끝 ----------------->
        <br>

        <!----------------- 마스터폼 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="9" class="table_top_line"></td>
          </tr>
          <tr>
            <td height="25" class="table_title" width="5%"><b>NO</b></td>
            <td class="table_title" width="10%"><a href="javascript:whenOrders('subj')" class="b"><b>과정코드</b></a></td>
            <td class="table_title" width="13%"><a href="javascript:whenOrders('dir')" class="b"><b>콘텐츠 위치</b></a></td>
            <td class="table_title"><a href="javascript:whenOrders('subjnm')" class="b"><b>과정명</b></a></td>
            <td class="table_title"><b>콘텐츠타입</b></td>
            <td class="table_title"><b>차시</b></td>
            <td class="table_title" width="8%"><a href="javascript:whenOrders('center')" class="b"><b>중앙정렬</b></a></td>
            <td class="table_title" width="18%"><b>기능</b></td>
          </tr>
<%  if (p_action.equals("go")) {
        ArrayList  list = (ArrayList)request.getAttribute("MasterFormList");
        MasterFormListData data = null;
        String      v_subj="";
        for (int i=0; i<list.size(); i++) {
            data  = (MasterFormListData)list.get(i);
            v_subj = data.getSubj();

%>
          <tr>
            <td align="center" bgcolor="#EEEEEE" height="25"><%=i+1%></td>
            <td class="table_02_1"><%=v_subj%></td>
            <td class="table_02_1"><% if(data.getContenttype().equals("S")) {%><a href="javascript:whenDir('<%=v_subj%>')"><img src="/images/admin/button/b_contents.gif" border=0></a><%} else { %> <%=data.getDir()%> <% } %></td>
            <td bgcolor="#F7F7F7"><%=data.getSubjnm()%></td>
            <td class="table_02_1"><%=data.getContenttypenm()%></td>
            <td class="table_02_1"><%=data.getCnt_lesson()%></td>
            <td class="table_02_1"><%=data.getIscentered()%></td>
            <td class="table_03_1">
              <table width="95%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td align="center">

<%        if (data.getIsoutsourcing().equals("N")) {              %>
                    <a href="javascript:whenChange('<%=v_subj%>')"><img src="/images/admin/button/b_modify.gif"  border="0"></a>
<%        }                                                %>
                  </td>
                  <td align="center">
<%        if (data.getIsoutsourcing().equals("N"))  {
	String results = null;
	if (data.getEduurl().equals("")){
			results = "/servlet/controller.contents.EduStart?p_year=2000&p_subjseq=0001&p_subj="+v_subj;
	}else{
		results = data.getEduurl();
	}
	%>
                    <a href="javascript:whenViewEdu('<%=results%>')"><img src="/images/admin/button/b_preview.gif"  border="0"></a>
<%        }                                                       %>
                  </td>
                  <!--<td align="center"><a href="javascript:whenViewPreview('<%=v_subj%>')"><img src="/images/admin/contents/view1_butt.gif" width="48" height="18" border="0"></a></td>-->
<!-- OBC,SCORM일 경우 변경옵션추가 (추후)-->
                </tr>
              </table>
            </td>
          </tr>
<%      }
    }
%>

        </table>
        <!----------------- 마스터폼 관리 끝 ----------------->
        <br>

      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
