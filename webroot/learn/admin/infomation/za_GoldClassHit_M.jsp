<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.infomation.*" %>


<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%

/*
* 프로그램 영역
*/
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  s_gadmin = box.getSession("gadmin");
    String  v_gadmin = StringManager.substring(s_gadmin, 0, 1);
    String  isAll = !v_gadmin.equals("A") ? "false" : "true";
    String  hityn =box.getString("p_hit_yn");
    String  recomyn = box.getString("p_recom_yn");

    ArrayList allOpenclassList = (ArrayList)request.getAttribute("allOpenclassList");
    ArrayList hitOpenClassList = (ArrayList)request.getAttribute("hitOpenClassList");

    DataBox dbox = null;

%>

<html>
<head>
<title>열린강좌 관리 | 인기과정 관리 | 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/admin_style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">

<style type="text/css">
    button{display:inline-block; padding:2px 4px 0px 4px; margin-bottom:0; font-size:12px; font-weight:bold; text-align:center; white-space:nowrap; vertical-align:middle; border-style: solid 1px; }
    button.btn_reg {width:80px; height:24px; background-color:#efefef; color:#000000; border-color:#808080; border-width:1px;}
    button.btn_basic {width:80px; height:20px; background-color:#efefff; color:#000000; border-color:#808090; border-width:1px;}
</style>

<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript">
<!--

    /**
     * 선택강좌 추가하기
     */
    function fnSendToClassify() {
        if ( ($("#oOpenClass option:selected").length) == 0) {
            alert("추가할 과정을 선택하세요.");
            return false;
        }

        $("#oOpenClass option:selected").each( function() {
            $("#oClassifedOpenClass").append($(this));
            // $("#oClassifedOpenClass").prepend("<option value=\"" + $(this).val() + "\">" +  $(this).text()  + "</option>");
            // $(this).remove();
        });

        $("#oOpenClass").css("width", "400px");
        $("#oClassifedOpenClass").css("width", "400px");
    }

    /**
     * 선택강좌 제거하기
     */
    function fnRemoveFromClassify() {
        if ( ($("#oClassifedOpenClass option:selected").length) ==0 ) {
            alert("제거할 과정을 선택하세요.");
            return false;
        }

        $("#oClassifedOpenClass option:selected").each( function() {
            $("#oOpenClass").append($(this));
        });
        $("#oOpenClass").css("width", "400px");
        $("#oClassifedOpenClass").css("width", "400px");
    }

    /**
    * 인기강좌 등록하기
    **/
    function fnSaveOpenClassList() {
        $("#oClassifedOpenClass > option").attr("selected", true);
        
        if( $("#oHit_yn").val() =="Y"){
            if ( ($("#oClassifedOpenClass option:selected").length) >30 ) {
                alert("등록된과정이 30개를 초과하였습니다.");
                return false;
            }
        }

        var param = $("#oOpenClassResisterForm").serialize();

        $.ajax({
                type : "post"
            ,   url : "/servlet/controller.infomation.GoldClassAdminServlet"
            ,   dataType : "json"
            ,   data : param
            ,   success : function (data) {
                    if(data.resultCnt > 0) {
                        alert("등록되었습니다.");
                    }
                }
            ,   complete : function(arg1, arg2) {
                }
            ,   error : function(arg1, arg2) {
            }
        });
    }

    function fnWindowClose() {
		//alert('취소하였습니다.');
	    window.close();
	}
//-->
</script>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form id="oOpenClassResisterForm" name="openClassResisterForm" method="post">
    <input type="hidden" id="oProcess"  name="p_process"  value="saveOpenClassList" />
    <input type="hidden" id="oHit_yn"   name="p_hit_yn"  value="<%=hityn%>" />
    <input type="hidden" id="oRecom_yn" name="p_recom_yn"  value="<%=recomyn%>" />

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top" colspan="2">
            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td style="font-size:14pt; font-weight:bold; padding-top:16px;">열린강좌 관리</td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->
            <br/>

            <!-- tab 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="30%" style="font-size:12pt; font-weight:bold;">
                        <% if(hityn.equals("Y")){%>인기강좌
                        <%}else{%> 추천강좌
                        <%}%>
                    </td>
                    <td width="70%">&nbsp;</td>
                </tr>
                <tr>
                    <td height="2" colspan="4" bgcolor="#6699CC"></td>
                </tr>
            </table>
            <!-- tab 끝 //-->


            <!-- 등록 화면 시작 //-->
            <table cellspacing="1" cellpadding="5" class="table_out">
                <colgroup>
                    <col width="45%;" />
                    <col width="10%;" />
                    <col width="*;" />
                </colgroup>
                <tr>
                    <td colspan="3" class="table_top_line"></td>
                </tr>
                <tr>
                    <td class="table_title">전체 강좌 (<%= allOpenclassList.size() %> 과정)  </td>
                    <td class="table_title">보내기/<br/>제거하기</td>
                    <td class="table_title">등록된 강좌 (<%= hitOpenClassList.size() %> 과정)</td>
                </tr>
                <tr>
                    <td class="table_01">
                        <select multiple id="oOpenClass" name="openClass" size="20" style="font-size:10pt; width:400px; background-color:#efefef;" ondblclick="fnSendToClassify();">
<%
        if ( allOpenclassList.size() > 0 ) {
            for( int i = 0 ; i < allOpenclassList.size() ; i++ ){
                dbox = (DataBox)allOpenclassList.get(i);
%>
                            <option value="<%= dbox.getString("d_seq") %>"><%= dbox.getString("d_lecnm") %></option>
<%
            }
        } else {
%>
                            <option value="">선택할 수 있는 과정이 없습니다.</option>
<%
        }
%>
                        </select>
                    </td>
                    <td class="table_02_1">
                        <a id="oCancelBtn" name="cancelBtn" class="btn_basic" onclick="fnSendToClassify();" >&gt;&gt;</a><br/><br/>
                        <a id="oCancelBtn" name="cancelBtn" class="btn_basic" onclick="fnRemoveFromClassify();" >&lt;&lt;</a>
                    </td>
                    <td class="table_01">
                        <select multiple id="oClassifedOpenClass" name="classifedOpenClass" size="20" style="font-size:10pt; width:400px; background-color:#ffffb3;" ondblclick="fnRemoveFromClassify();">
<%
        if ( hitOpenClassList.size() > 0 ) {
            for( int i = 0 ; i < hitOpenClassList.size() ; i++ ){
                dbox = (DataBox)hitOpenClassList.get(i);
%>
                            <option value="<%= dbox.getString("d_seq") %>"><%= dbox.getString("d_lecnm") %></option>
<%
            }
        }
%>
                        </select>
                    </td>
                </tr>
            </table>

            <br/>
            <br/>
            <a id="oRegisterBtn" name="registerBtn" class="btn_reg" onclick="fnSaveOpenClassList();" ><img src="/images/admin/button/btn_save.gif"></a>&nbsp;&nbsp;
            <a id="oCancelBtn" name="cancelBtn" class="btn_reg" onclick="fnWindowClose()();" ><img src="/images/admin/button/btn_close.gif"></a>

        </td>
    </tr>
</table>
</form>

</body>
</html>