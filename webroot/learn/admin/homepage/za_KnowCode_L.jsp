<%
/**
 * file name : za_KnowCode.jsp
 * date      : 2005/9/01
 * programmer:
 * function  : 지식코드분류 조회화면
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    
    if(box.getString("s_grcode").equals(""))
        box.put("s_grcode", box.getString("p_grcode"));         
    
    String  v_grcode    = box.getStringDefault("s_grcode","ALL");    //교육그룹
    String  ss_grcode   = box.getString("s_grcode");                 //교육그룹
    String  v_action    = box.getString("p_action");       
    String  v_process   = box.getString("p_process");
    
    

        
    String   v_fmtstr = "<a href=\"javascript:updateSalesCodePage(''{0}'', ''{1}'', ''{2}'', ''{3}'')\" class=''e''>{4}</a>";
    Object[] v_arguments = {"", "", "", "", ""};
    String   v_updatelink = "";
	String   v_lowerclassname = "";
%>
<html>
<head>
<title>지식코드분류</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
// 코드등록
function insertSalesCodePage() {
    window.self.name = "winClassifySelectList";
    window.open("", "openSalesCodeInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=no,copyhistory=no, width=585, height=500, top=0, left=0");
    document.form2.target = "openSalesCodeInsert";
    document.form2.action = "/servlet/controller.homepage.KnowBoardServlet";
    document.form2.p_process.value = "insertPage";
    document.form2.submit();
}

// 코드수정
function updateSalesCodePage(upperclass, middleclass, lowerclass, classname) {
    window.self.name = "winClassifySelectList";
    window.open("", "openSalesCodeUpdate", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width=585, height=220, top=0, left=0");
    document.form2.target = "openSalesCodeUpdate";
    document.form2.action = "/servlet/controller.homepage.KnowBoardServlet";
    document.form2.p_process.value    = "updatePage";
    document.form2.p_upperclass.value  = upperclass;
    document.form2.p_middleclass.value = middleclass;
    document.form2.p_lowerclass.value  = lowerclass;
    document.form2.p_classname.value   = classname;
    document.form2.submit();
}

// 선택조회
function whenSelection(p_action) {
	v_grcode = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;
	
	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}

     document.form1.p_process.value = 'KonwCodeList';
     document.form1.p_action.value  = p_action;
     document.form1.p_grcode.value  = v_grcode;
	 document.form1.submit();	
}

// 새창에서 처리후 화면 RELOAD됨
/*function ReloadPage(p_action) {
     document.form2.p_process.value = 'KonwCodeList';
     document.form2.p_action.value  = 'go';
     document.form2.p_grcode.value  = '<%=v_grcode%>';
	 document.form2.submit();	
}*/
// 화면 리로드
function ReloadPage(p_action) {
  document.form1.p_process.value = 'KonwCodeList';
  document.form1.p_action.value  = p_action;
  document.form1.submit();
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/homepage/tit_knowcode.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

      <!----------------- form 시작 ----------------->
      <table class="form_table_out" cellspacing="0" cellpadding="1">
        <form name="form1" method="post" action="/servlet/controller.homepage.KnowBoardServlet">
          <input type="hidden" name="p_action"  value="<%=v_action%>">      
          <input type="hidden" name="p_process" value="">        
          <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">             
        <tr>
          <td align="center">
            <table class="form_table_bg" cellspacing="0" cellpadding="0">
			  <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
                  <table class="form_table" border=0 cellspacing="0" cellpadding="0" width="99%">
                    <tr>
                      <td><font color="red">★</font> 
                         <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
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
        </form>
      </table>
      <!----------------- form 끝 ----------------->
      <br>
      <br>      
      <!----------------- 추가 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" height="20"><a href="javascript:insertSalesCodePage()"><img src="/images/admin/button/btn_add.gif" border="0"></a></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <br>      
      <!----------------- 추가 버튼 끝 ----------------->
      <table width="97%" border="0" cellspacing="1" cellpadding="5" class="table_out">
      <form name="form2" method="post" action="/servlet/controller.homepage.KnowBoardServlet">
          <input type="hidden" name="p_action"  value="<%=v_action%>">      
          <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">      
          <input type="hidden" name="p_process"     value="">
          <input type="hidden" name="p_upperclass"  value="">
          <input type="hidden" name="p_middleclass" value="">
          <input type="hidden" name="p_lowerclass"  value="">
          <input type="hidden" name="p_classname"   value="">

        <tr>
          <td colspan="7" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="7%" class="table_title"><b>NO</b></td>
          <td class="table_title" colspan="2" width="30%"><b>대분류</b></td>
          <!--td class="table_title" colspan="2" width="30%"><b>중분류</b></td-->
          <td class="table_title" colspan="2" width="30%"><b>소분류</b></td>
        </tr>
<%
if(v_action.equals("go")){
    DataBox dbox  = null;
    ArrayList list = (ArrayList)request.getAttribute("KonwCodeList");
    
    for (int i=0; i<list.size(); i++) {
        dbox  = (DataBox)list.get(i);

        v_arguments[0] = dbox.getString("d_upperclass");
        v_arguments[1] ="000";
        v_arguments[2] ="000"; 

        %>
        <tr>
          <td align="center" class="table_01"><%=String.valueOf(i+1)%></td>
<%
          if (dbox.getInt("d_upperrowspannum") > 0) {
            v_arguments[3] = dbox.getString("d_upperclassname");
            v_arguments[4] = dbox.getString("d_upperclass");
            v_updatelink = MessageFormat.format(v_fmtstr, v_arguments);  
            
%>
          <td align="center" class="table_02_1" rowspan="<%=dbox.getInt("d_upperrowspannum")%>"><%=v_updatelink%></td>
          <td align="center" class="table_02_1" rowspan="<%=dbox.getInt("d_upperrowspannum")%>"><%=dbox.getString("d_upperclassname")%></td>
<%
        }

        v_arguments[1] = dbox.getString("d_middleclass");
        v_arguments[2] = "000";
        if (dbox.getInt("d_middlerowspannum") > 0) {
            if (dbox.getString("d_middleclass").equals("000")) {
                v_updatelink = "";
            } else {
                v_arguments[3] = dbox.getString("d_middleclassname");
                v_arguments[4] = dbox.getString("d_middleclass");
                v_updatelink = MessageFormat.format(v_fmtstr, v_arguments);
            } %>
          <td align="center" class="table_02_1" width="10%" rowspan="<%=dbox.getInt("d_middlerowspannum")%>"><%=v_updatelink%></td>
          <td align="center" class="table_02_1" width="20%" rowspan="<%=dbox.getInt("d_middlerowspannum")%>"><%=dbox.getString("d_middleclassname")%></td>
<% 
        }

        v_arguments[2] = dbox.getString("d_lowerclass");
        if (dbox.getString("d_lowerclass").equals("000")) {
            v_updatelink = "";
			v_lowerclassname = "";
        } else {
            v_arguments[3] = dbox.getString("d_lowerclassname");
            v_arguments[4] = dbox.getString("d_lowerclass");
            v_updatelink = MessageFormat.format(v_fmtstr, v_arguments);
			v_lowerclassname = dbox.getString("d_lowerclassname");
        } %>
          <!--td align="center" class="table_02_1" width="10%" ><%=v_updatelink%></td>
          <td align="center" class="table_02_1" width="20%" ><%=v_lowerclassname%><br>
          
          </td-->
        </tr>
<%  
    }
    if(list.size()<1){
 %>
         <tr>
          <td align="center" class="table_02_1"  colspan="7" >등록된 내용이 없습니다.</td>
        </tr>
 <%
    }


}    
 %>
 
         </form>
      </table>
      <br>
    </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>