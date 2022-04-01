<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    DataBox tutorInfo = (DataBox)request.getAttribute("tutorInfo");
    ArrayList tutorSubjLIst=(ArrayList)request.getAttribute("tutorSubjLIst");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/cresys_lib.js'></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
    $(document).ready(function(){
    });
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr><td height="10px"></td></tr><tr>
    <td align="center" valign="top" width="100%">
        <!----------------- 공지사항 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td width="20%" class="table_title"><strong>성명</strong></td>
            <td height="25" class="table_02_2"> <%=tutorInfo.getString("d_name")%> ( <%=tutorInfo.getString("d_userid")%> ) </td>
            <td class="table_title"><strong>주민등록번호</strong></td>
            <td height="25" class="table_02_2"><%=tutorInfo.getString("d_resno")%></td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>소속</strong></td>
            <td height="25" class="table_02_2"><%=tutorInfo.getString("d_comp")%></td>
            <td height="25" class="table_title" ><strong>직위</strong></td>
            <td height="25" class="table_02_2"><%=tutorInfo.getString("d_jik")%></td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>핸드폰</strong></td>
            <td height="25" class="table_02_2"><%=tutorInfo.getString("d_handphone")%></td>
            <td height="25" class="table_title" ><strong>이메일</strong></td>
            <td height="25" class="table_02_2"><%=tutorInfo.getString("d_email")%></td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>학력</strong></td>
            <td height="25" class="table_02_2"><%=tutorInfo.getString("d_academic")%></td>
            <td height="25" class="table_title" ><strong>전문분야</strong></td>
            <td height="25" class="table_02_2"><%=tutorInfo.getString("d_professional")%></td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>세부분야</strong></td>
            <td height="25" class="table_02_2" colspan="3"><%=tutorInfo.getString("d_major")%></td>
          </tr>
        </table>
    </td></tr>
    <tr><td height="10px"></td></tr>
    <tr><td align="center" valign="top" width="100%">
        <table cellspacing="1" cellpadding="5" class="table_out" width="100%">
          <tr> 
            <td colspan="6" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="20px">No</td>
            <td class="table_title" >강좌명</td>
            <td class="table_title" width="80px">교육대상</td>
			<td class="table_title" >교육기간</td>
			<td class="table_title" width="70px">강의날짜</td>
			<td class="table_title" width="55px">강의시간</td>
          </tr>
<%
            if (tutorSubjLIst.size()>0) {
	         	for(int i = 0 ; i < tutorSubjLIst.size() ; i++ ){
					DataBox dbox = (DataBox)tutorSubjLIst.get(i);
							
					String v_subj		= dbox.getString("d_subj");
					String v_year		= dbox.getString("d_year");
					String v_subjseq	= dbox.getString("d_subjseq");
					String v_subjnm	    = dbox.getString("d_subjnm");
					String v_sub_title	= dbox.getString("d_sub_title");
					String v_edustart	= dbox.getString("d_edustart").substring(0,4)+"/"+dbox.getString("d_edustart").substring(4,6)+"/"+dbox.getString("d_edustart").substring(6,8);
					String v_eduend	    = dbox.getString("d_eduend").substring(0,4)+"/"+dbox.getString("d_eduend").substring(4,6)+"/"+dbox.getString("d_eduend").substring(6,8);
                    String v_sub_dt="";
                     if(!dbox.getString("d_sub_dt").equals(""))
					    v_sub_dt		= dbox.getString("d_sub_dt").substring(0,4)+"/"+dbox.getString("d_sub_dt").substring(4,6)+"/"+dbox.getString("d_sub_dt").substring(6,8);
                    String v_sub_time	= dbox.getString("d_sub_time");
					String v_sub_target	= dbox.getString("d_sub_target");
	%>
			        <tr> 
			            <td class="table_02_1"><%=i+1%></td>
			            <td class="table_02_1"><%=v_sub_title%></td>
						<td class="table_02_1"><%=v_sub_target%></td>
			            <td class="table_02_1"><%=v_edustart%> ~ <%=v_eduend%></td>
			            <td class="table_02_1"><%=v_sub_dt%></td>
			            <td class="table_02_1"><%=v_sub_time%></td>
			         </tr>
<%					
	            }		   
         } else{
%>
              <tr> 
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="6">등록된 내용이 없습니다</td>
              </tr>
<% } %>
			</table>
        <br>
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="javascript:self.close();"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
          </tr>
        </table>
      </td>
    </tr>
</table>
</body>
</html>

