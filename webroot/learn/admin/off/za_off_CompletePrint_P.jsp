<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    ArrayList resultList=(ArrayList)request.getAttribute("resultList");
    DataBox box1=(DataBox)resultList.get(0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<TITLE>훈련실시 수료자 명단</TITLE>
<STYLE>
<!--
P.HStyle0, LI.HStyle0, DIV.HStyle0
	{style-name:"바탕글"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0.0pt; font-weight:normal; font-style:normal; color:#000000;}
P.HStyle1, LI.HStyle1, DIV.HStyle1
	{style-name:"본문"; margin-left:15.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0.0pt; font-weight:normal; font-style:normal; color:#000000;}
P.HStyle2, LI.HStyle2, DIV.HStyle2
	{style-name:"개요 1"; margin-left:10.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0.0pt; font-weight:normal; font-style:normal; color:#000000;}
P.HStyle3, LI.HStyle3, DIV.HStyle3
	{style-name:"개요 2"; margin-left:20.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0.0pt; font-weight:normal; font-style:normal; color:#000000;}
P.HStyle4, LI.HStyle4, DIV.HStyle4
	{style-name:"개요 3"; margin-left:30.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0.0pt; font-weight:normal; font-style:normal; color:#000000;}
P.HStyle5, LI.HStyle5, DIV.HStyle5
	{style-name:"개요 4"; margin-left:40.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0.0pt; font-weight:normal; font-style:normal; color:#000000;}
P.HStyle6, LI.HStyle6, DIV.HStyle6
	{style-name:"개요 5"; margin-left:50.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0.0pt; font-weight:normal; font-style:normal; color:#000000;}
P.HStyle7, LI.HStyle7, DIV.HStyle7
	{style-name:"개요 6"; margin-left:60.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0.0pt; font-weight:normal; font-style:normal; color:#000000;}
P.HStyle8, LI.HStyle8, DIV.HStyle8
	{style-name:"개요 7"; margin-left:70.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:바탕; letter-spacing:0.0pt; font-weight:normal; font-style:normal; color:#000000;}
P.HStyle9, LI.HStyle9, DIV.HStyle9
	{style-name:"쪽 번호"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:굴림; letter-spacing:0.0pt; font-weight:normal; font-style:normal; color:#000000;}
P.HStyle10, LI.HStyle10, DIV.HStyle10
	{style-name:"머리말"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:150%; font-size:9.0pt; font-family:굴림; letter-spacing:0.0pt; font-weight:normal; font-style:normal; color:#000000;}
P.HStyle11, LI.HStyle11, DIV.HStyle11
	{style-name:"각주"; margin-left:13.1pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-13.1pt; line-height:130%; font-size:9.0pt; font-family:바탕; letter-spacing:0.5pt; font-weight:normal; font-style:normal; color:#000000;}
P.HStyle12, LI.HStyle12, DIV.HStyle12
	{style-name:"미주"; margin-left:13.1pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-13.1pt; line-height:130%; font-size:9.0pt; font-family:바탕; letter-spacing:0.5pt; font-weight:normal; font-style:normal; color:#000000;}
P.HStyle13, LI.HStyle13, DIV.HStyle13
	{style-name:"메모"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:9.0pt; font-family:굴림; letter-spacing:0.5pt; font-weight:normal; font-style:normal; color:#000000;}
-->
</STYLE>
</HEAD>

<BODY onload="this.print();">

<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:20.0pt;font-family:"굴림체";font-weight:bold;line-height:160%;'>훈련실시 수료자 명단</SPAN></P>
<P CLASS=HStyle0><SPAN STYLE='font-size:13.0pt;font-family:"굴림체";line-height:160%;'>1. 훈련기관명 : 한국콘텐츠진흥원</SPAN></P>
<P CLASS=HStyle0><SPAN STYLE='font-size:13.0pt;font-family:"굴림체";line-height:160%;'>2. 훈련과정명 : <%=box1.getString("d_subjnm")%> </SPAN></P>
<P CLASS=HStyle0><SPAN STYLE='font-size:13.0pt;font-family:"굴림체";line-height:160%;'>3. 훈련기간&nbsp;&nbsp; : <%=box1.getString("d_start_dt")%> ~ <%=box1.getString("d_end_dt")%> (<%=box1.getString("d_days")%>일간/<%=box1.getString("d_sub_time")%>시간)</SPAN></P>
<P CLASS=HStyle0><SPAN STYLE='font-size:13.0pt;font-family:"굴림체";line-height:160%;'>4. 훈련유형&nbsp;&nbsp; : 위탁</SPAN></P>
<P CLASS=HStyle0><SPAN STYLE='font-size:13.0pt;font-family:"굴림체";line-height:160%;'>5. 훈련실시 신고자 명단</SPAN></P>
<P CLASS=HStyle0 STYLE='text-align:center;'>
<TABLE border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse;border:none;'>
<TR>
	<TD width="36" height="42" valign="middle" style='border-left:solid #000000 1.1pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:double #000000 1.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:"굴림체";line-height:160%;'>연번</SPAN></P>
	</TD>
	<TD width="51" height="42" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:double #000000 1.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:"굴림체";line-height:160%;'>성 명</SPAN></P>
	</TD>
	<TD height="42" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:double #000000 1.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:"굴림체";line-height:160%;'>주민등록</SPAN></P>
	<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:"굴림체";line-height:160%;'>번&nbsp;&nbsp;&nbsp; 호</SPAN></P>
	</TD>
	<TD width="68" height="42" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:double #000000 1.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:"굴림체";line-height:160%;'>훈련기간</SPAN></P>
	<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:10.0pt;font-family:"굴림체";letter-spacing:0.4pt;line-height:160%;'>(일/시간)</SPAN></P>
	</TD>
	<TD width="61" height="42" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:double #000000 1.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:"굴림체";letter-spacing:0.8pt;line-height:160%;'>훈 련</SPAN></P>
	<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:10.0pt;font-family:"굴림체";letter-spacing:0.8pt;line-height:160%;'>이수시간</SPAN></P>
	</TD>
	<TD width="53" height="42" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:double #000000 1.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:"굴림체";line-height:160%;'>이수율</SPAN></P>
	<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:"굴림체";line-height:160%;'>(%)</SPAN></P>
	</TD>
	<TD width="145" height="42" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:double #000000 1.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:"굴림체";line-height:160%;'>소속 회사 및</SPAN></P>
	<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:"굴림체";line-height:160%;'>부서 / 직위</SPAN></P>
	</TD>
	<TD width="112" height="42" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:double #000000 1.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:"굴림체";line-height:160%;'>연락처</SPAN></P>
	</TD>
	<TD width="35" height="42" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 1.1pt;border-top:solid #000000 1.1pt;border-bottom:double #000000 1.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
	<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:"굴림체";line-height:160%;'>비고</SPAN></P>
	</TD>
</TR>
    <% if(resultList.size()>0) {
        for(int i=0;i<resultList.size();i++)
        {
            DataBox tmp=(DataBox)resultList.get(i);
            out.print("<tr>");
            out.print("<TD height='42' valign='middle' style='border-left:solid #000000 1.1pt;border-right:solid #000000 0.4pt;border-top:double #000000 1.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>");
            out.print("<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:11.0pt;font-family:'굴림체';line-height:130%;'>"+(i+1)+"</SPAN></P>");
            out.print("</td>");
            out.print("<TD valign='middle' style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:double #000000 1.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>");
            out.print("<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:11.0pt;font-family:'굴림체';line-height:130%;'>"+tmp.getString("d_name")+"</SPAN></P>");
            out.print("</td>");
            out.print("<TD valign='middle' style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:double #000000 1.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>");
            out.print("<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:11.0pt;font-family:'굴림체';line-height:130%;'>"+tmp.getString("d_resno")+"</SPAN></P>");
            out.print("</td>");
            out.print("<TD valign='middle' style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:double #000000 1.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>");
            out.print("<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:'굴림체';line-height:160%;'>"+tmp.getString("d_days")+"일<br>"+ tmp.getString("d_sub_time")+"시간</SPAN></P>");
            out.print("</td>");
            out.print("<TD valign='middle' style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:double #000000 1.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>");
            out.print("<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:'굴림체';line-height:160%;'>"+tmp.getString("d_sub_time")+"시간</SPAN></P>");
            out.print("</td>");
            out.print("<TD valign='middle' style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:double #000000 1.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>");
            out.print("<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:'굴림체';line-height:160%;'>100</SPAN></P>");
            out.print("</td>");
            out.print("<TD  valign='middle' style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:double #000000 1.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>");
            out.print("<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:11.0pt;font-family:'굴림체';line-height:130%;'>"+tmp.getString("d_comptext")+"</SPAN></P>");
            out.print("<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:11.0pt;font-family:'굴림체';line-height:130%;'>"+tmp.getString("d_deptnam")+"</SPAN></P>");
            out.print("</td>");
            out.print("<TD valign='middle' style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:double #000000 1.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>");
            out.print("<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:10.0pt;font-family:'굴림체';line-height:130%;'>"+tmp.getString("d_handphone")+"</SPAN></P>");
            out.print("</td>");
            out.print("<TD valign'middle' style='border-left:solid #000000 0.4pt;border-right:solid #000000 1.1pt;border-top:double #000000 1.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>");
            out.print("<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:11.0pt;font-family:'굴림체';line-height:160%;'>&nbsp;</SPAN></P>");
            out.print("</td>");
            out.print("</tr>");
        }
    }
%>
</TABLE></P>
<P CLASS=HStyle0><SPAN STYLE='font-size:13.0pt;font-family:"굴림체";line-height:160%;'>&nbsp;&nbsp;ㅇ수료인원 : <%=resultList.size()%>명</SPAN></P>
<P CLASS=HStyle0><SPAN STYLE='font-size:13.0pt;font-family:"굴림체";line-height:160%;'>&nbsp;&nbsp;ㅇ미 수료인원 : 0명</SPAN></P>
<P CLASS=HStyle0><SPAN STYLE='font-size:13.0pt;font-family:"굴림체";line-height:160%;'><BR></SPAN></P>
<P CLASS=HStyle0><SPAN STYLE='font-size:13.0pt;font-family:"굴림체";line-height:160%;'>6. 첨&nbsp; 부 : 출석부 및 훈련일지 각 1부.&nbsp; 끝.</SPAN></P>
<P CLASS=HStyle0 style="text-align:center;"><SPAN STYLE='font-size:13.0pt;font-family:"굴림체";line-height:160%;text-align:center;'><img src="/images/admin/button/broadcast.bmp" alt="직인" border="0"></SPAN></P>
</BODY>
</HTML>
