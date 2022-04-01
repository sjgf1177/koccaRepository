<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    DataBox box1 = (DataBox)request.getAttribute("resultData");
	String v_subj=box.getString("p_subj");
    String v_year=box.getString("p_year");
    String v_subjseq=box.getString("p_subjseq");
    String v_seq1=box.getString("p_seq1");
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

//저장
    function insert_check() {

        var r=$.trim($("[name='p_input_title']").val());
        if(r=="")
        {
            alert("강좌명을 입력하여 주십시오.");
            $("[name='p_input_title']").focus();
            return;
        }
        r=$.trim($("[name='p_input_content']").val());
        if(r=="")
        {
            alert("강좌내용을 입력하여 주십시오.");
            $("[name='p_input_content']").focus();
            return;
        }
        r=$.trim($("[name='p_input_sum_time']").val());
        if(r=="")
        {
            alert("총강의시간을 입력하여 주십시오.");
            $("[name='p_input_sum_time']").focus();
            return;
        }
        r=$.trim($("[name='p_input_year']").val());
        if(r=="")
        {
            alert("강의년도를 입력하여 주십시오.");
            $("[name='p_input_year']").focus();
            return;
        }
        if(r.length!=4)
        {
            alert("강의년도의 자릿수는 4자리 입니다.");
            $("[name='p_input_year']").focus();
            return;
        }
        document.form1.target=opener.name;
        document.form1.p_process.value = "teachDetailUpdate";
        document.form1.submit();
        self.close();
    }
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name="form1" method="post" enctype="multipart/form-data" action="/servlet/controller.off.OffGrseqAdminServlet">
    <input type = "hidden" name="p_process"    value = "teachDetailUpdate">
    <input type = "hidden" name="p_subj"   value = "<%=v_subj%>">
    <input type = "hidden" name="p_year"    value = "<%=v_year%>">
    <input type = "hidden" name="p_subjseq"    value = "<%=v_subjseq%>">
    <input type = "hidden" name="p_seq1"    value = "<%=v_seq1%>">
<% if(box1!=null) {%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr><td height="10px"></td></tr>
    <td align="center" valign="top">
        <!----------------- 공지사항 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td width="20%" class="table_title"><strong>강의날짜</strong></td>
            <td height="25" class="table_02_2">
                <input name="p_input_year" type="text" size="4" value="<%=box1.getString("d_sub_dt").substring(0,4)%>">/
                <select name="p_input_month">
                    <option value="01" <%= box1.getString("d_sub_dt").substring(4,6).equals("01") ? "selected":"" %>>1</option>
                    <option value="02" <%= box1.getString("d_sub_dt").substring(4,6).equals("02") ? "selected":"" %>>2</option>
                    <option value="03" <%= box1.getString("d_sub_dt").substring(4,6).equals("03") ? "selected":"" %>>3</option>
                    <option value="04" <%= box1.getString("d_sub_dt").substring(4,6).equals("04") ? "selected":"" %>>4</option>
                    <option value="05" <%= box1.getString("d_sub_dt").substring(4,6).equals("05") ? "selected":"" %>>5</option>
                    <option value="06" <%= box1.getString("d_sub_dt").substring(4,6).equals("06") ? "selected":"" %>>6</option>
                    <option value="07" <%= box1.getString("d_sub_dt").substring(4,6).equals("07") ? "selected":"" %>>7</option>
                    <option value="08" <%= box1.getString("d_sub_dt").substring(4,6).equals("08") ? "selected":"" %>>8</option>
                    <option value="09" <%= box1.getString("d_sub_dt").substring(4,6).equals("09") ? "selected":"" %>>9</option>
                    <option value="10" <%= box1.getString("d_sub_dt").substring(4,6).equals("10") ? "selected":"" %>>10</option>
                    <option value="11" <%= box1.getString("d_sub_dt").substring(4,6).equals("11") ? "selected":"" %>>11</option>
                    <option value="12" <%= box1.getString("d_sub_dt").substring(4,6).equals("12") ? "selected":"" %>>12</option>
                </select>
                /
                <select name="p_input_day">
                    <% for(int i=1;i<32;i++)
                        {
                            String tmp="";
                            String sel="";

                            if(i<10)
                                tmp="0"+i;
                            else
                                tmp=Integer.toString(i);

                            if(tmp.equals(box1.getString("d_sub_dt").substring(6,8)))
                                sel="selected";

                            out.print("<option value='"+tmp+"' "+sel+" >"+i+"</option>");
                        }
                    %>
                </select>
            </td>
          </tr>
          <tr>
            <td class="table_title"><strong>강의시작시간</strong></td>
            <td height="25" class="table_02_2">
                <select name="p_input_start_hour">
                    <% for(int i=1;i<25;i++)
                        {
                            String tmp="";
                            String sel="";

                            if(i<10)
                                tmp="0"+i;
                            else
                                tmp=Integer.toString(i);
                            if(tmp.equals(box1.getString("d_sub_start_time").substring(0,2)))
                                sel="selected";

                            out.print("<option value='"+tmp+"' "+sel+" >"+i+"</option>");
                        }
                    %>
                </select>
                :
                <select name="p_input_start_min">
                    <% for(int i=0;i<60;i++)
                        {
                            String tmp="";
                            String sel="";

                            if(i<10)
                                tmp="0"+i;
                            else
                                tmp=Integer.toString(i);
                            if(tmp.equals(box1.getString("d_sub_start_time").substring(2,4)))
                                sel="selected";

                            out.print("<option value='"+tmp+"' "+sel+" >"+i+"</option>");
                        }
                    %>
                </select>
                총강의시간 :  <input name="p_input_sum_time" size="4" maxlength="4" value="<%=box1.getString("d_sub_time")%>">

            </td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>강좌명</strong></td>
            <td height="25" class="table_02_2"><input name="p_input_title" size="60" value="<%=box1.getString("d_sub_title")%>"></td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>강좌안내</strong></td>
            <td height="25" class="table_02_2"><textarea rows="6" cols="40" name="p_input_content"><%=box1.getString("d_sub_content")%></textarea> </td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>교육대상</strong></td>
            <td height="25" class="table_02_2"><input name="p_input_target" size="60" value="<%=box1.getString("d_sub_target")%>"></td>
          </tr>
        </table>
        <br>
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="javascript:insert_check()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
            <td align="center"><a href="javascript:self.close();"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
      </td>
  </tr>
</table>
<% } else { %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr><td height="10px"></td></tr>
    <td align="center" valign="top">
        <!----------------- 공지사항 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td width="20%" class="table_title"><strong>강의날짜</strong></td>
            <td height="25" class="table_02_2">
                <input name="p_input_year" type="text" size="4">/
                <select name="p_input_month">
                    <option value="01" >1</option>
                    <option value="02" >2</option>
                    <option value="03" >3</option>
                    <option value="04" >4</option>
                    <option value="05" >5</option>
                    <option value="06" >6</option>
                    <option value="07" >7</option>
                    <option value="08" >8</option>
                    <option value="09" >9</option>
                    <option value="10" >10</option>
                    <option value="11" >11</option>
                    <option value="12" >12</option>
                </select>
                /
                <select name="p_input_day">
                    <% for(int i=1;i<32;i++)
                        {
                            String tmp="";
                            String sel="";

                            if(i<10)
                                tmp="0"+i;
                            else
                                tmp=Integer.toString(i);

                            out.print("<option value='"+tmp+"'>"+i+"</option>");
                        }
                    %>
                </select>
            </td>
          </tr>
          <tr>
            <td class="table_title"><strong>강의시작시간</strong></td>
            <td height="25" class="table_02_2">
                <select name="p_input_start_hour">
                    <% for(int i=1;i<25;i++)
                        {
                            String tmp="";
                            String sel="";

                            if(i<10)
                                tmp="0"+i;
                            else
                                tmp=Integer.toString(i);

                            out.print("<option value='"+tmp+"'>"+i+"</option>");
                        }
                    %>
                </select>
                :
                <select name="p_input_start_min">
                    <% for(int i=0;i<60;i++)
                        {
                            String tmp="";
                            String sel="";

                            if(i<10)
                                tmp="0"+i;
                            else
                                tmp=Integer.toString(i);

                            out.print("<option value='"+tmp+"'>"+i+"</option>");
                        }
                    %>
                </select>
                총강의시간 :  <input name="p_input_sum_time" size="4" maxlength="4">

            </td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>강좌명</strong></td>
            <td height="25" class="table_02_2"><input name="p_input_title" size="60" ></td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>강좌안내</strong></td>
            <td height="25" class="table_02_2"><textarea rows="6" cols="40" name="p_input_content"></textarea> </td>
          </tr>
        </table>
        <br>
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="javascript:insert_check()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
            <td align="center"><a href="javascript:self.close();"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
      </td>
  </tr>
</table>
<%}%>
</form>
</body>
</html>

