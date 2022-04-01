<%
/**
 * file name : za_ProposeCancelMember_E.jsp
 * date      :
 * programmer:
 * function  : 수강생 관리 리스트 조회화면
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%--<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>--%>
<%
    response.setHeader("Content-Disposition", "attachment; filename=za_ProposeCancelMember_E.xls");
    response.setHeader("Content-Description", "JSP Generated Data");
	  
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    int v_pageno        = box.getInt("p_pageno");
    String  v_grseq     =  "";
    String  v_grseqnm   =  "";
    String  v_course    =  "";
    String  v_cyear     =  "";
    String  v_courseseq =  "";
    String  v_coursenm  =  "";
    String  v_subj      =  "";
    String  v_year      =  "";
    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_subjseqgr =  "";
    String  v_userid    =  "";
    String  v_name      =  "";
    String  v_canceldate=  "";
    String  v_cancelkind=  "";
    String  v_reason    =  "";
    String  v_email     =  "";
    String  v_ismailing =  "";
    String  v_isnewcourse= "";
    String  v_cancelkind_value  =  "";
    String  v_isonoff   =  "";
    String  v_isonoff_value="";

    int     v_totalpage =  0;
    int     v_rowcount  =  0;
    int     v_rowspan   =  0;
    int     v_total     =  0;
    int     i           =  0;
    int     j           =  0;
    int     k           =  0;
    String v_paymoney   = "";		// 신청금액
    String v_paystat	= "";		// 환불 상태

	String v_membergubun = "";
	String v_dispnum	= "";
	
	String	v_isbelongcourse = "";
	String	v_tmp_course	= "";

    ArrayList list      = null;

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    String  v_orderColumn  = box.getString("p_orderColumn");           //정렬할 컬럼명

    String  ss_grcode    = box.getString("s_grcode");        //교육그룹
    String  ss_gyear     = box.getString("s_gyear");         //년도
    String  ss_grseq     = box.getString("s_grseq");         //교육차수
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse= box.getString("s_subjcourse");    //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");       //과정 차수
    String  ss_selgubun  = box.getString("s_selgubun");
    String  ss_seltext   = box.getString("s_seltext");
    String  ss_seldept   = box.getString("s_seldept");
    String  ss_edustart  = box.getString("s_start");            //교육시작일
    String  ss_eduend    = box.getString("s_end");              //교육종료일

    String  ss_action    = box.getString("s_action");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr"></meta>
</head>
    <body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
        <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
        <tr>
            <td align="center" height="30"><font size="3"><b>입과/반려 취소 리스트</b></font></td>
        </tr>
        <tr>
            <td align="center" valign="top">
                <table cellspacing="1" cellpadding="5" border="1">
                    <tr>
                        <td width="5%"><b>No</b></td>
                        <td width="10%"><b>교육차수</b></td>
                        <td width="14%"><b>과정</b></td>
                        <td width="13%"><b>회원구분</b></td>
                        <td width="5%"><b>ID</b></td>
                        <td width="7%"><b>성명</b></td>
                        <td width="5%"><b>과정<br>차수</b></td>
                        <td width="5%"><b>구분</b></td>
                        <td width="8%">환불금</td>
                        <td width="5%">환불<br>상태</td>
                        <td width="7%"><b>취소일</b></td>
                        <td width="7%"><b>취소구분</b></td>
                        <td width="7%"><b>취소사유</b></td>
                    </tr>
                    <%
                    list = (ArrayList)request.getAttribute("ProposeCancelMemberExcel");
                    for(i = 0; i < list.size(); i++)
                    {
                        DataBox dbox = (DataBox)list.get(i);

                        v_grseq         = dbox.getString("d_grseq");
                        v_grseqnm       = dbox.getString("d_grseqnm");
                        v_course        = dbox.getString("d_course");
                        v_cyear         = dbox.getString("d_cyear");
                        v_courseseq     = dbox.getString("d_courseseq");
                        v_coursenm      = dbox.getString("d_coursenm");
                        v_subj          = dbox.getString("d_subj");
                        v_year          = dbox.getString("d_year");
                        v_subjnm        = dbox.getString("d_subjnm");
                        v_subjseq       = dbox.getString("d_subjseq");
                        v_subjseqgr     = dbox.getString("d_subjseqgr");
                        v_userid        = dbox.getString("d_userid");
                        v_name          = dbox.getString("d_name");
                        v_email         = dbox.getString("d_email");
                        v_ismailing     = dbox.getString("d_ismailing");
                        v_isnewcourse   = dbox.getString("d_isnewcourse");
                        v_isonoff       = dbox.getString("d_isonoff");
                        v_membergubun	= dbox.getString("d_membergubun");
                        v_dispnum		= dbox.getString("d_dispnum");
                        v_totalpage     = dbox.getInt("d_totalpage");
                        v_rowcount      = dbox.getInt("d_rowcount");
                        v_canceldate    = dbox.getString("d_canceldate");
                        v_cancelkind    = dbox.getString("d_cancelkind");
                        v_reason        = dbox.getString("d_reason");
                        v_paymoney	    = dbox.getString("d_paymoney");
                        v_paystat	    = dbox.getString("d_paystat	");
                        v_isbelongcourse = dbox.getString("d_isbelongcourse");

                        if(v_isonoff.equals("ON")){     v_isonoff_value="사이버";   }
                        else                      {     v_isonoff_value="집합";     }
                        v_canceldate    = FormatDate.getFormatDate(v_canceldate,"yyyy/MM/dd");
                        if(v_cancelkind.equals("P"))      {  v_cancelkind_value = "본인취소";    }
                        else if(v_cancelkind.equals("F")) {  v_cancelkind_value = "운영자취소";  }
                        else if(v_cancelkind.equals("D")) {  v_cancelkind_value = "운영자삭제";  }

                        //회원구분
                        if(v_membergubun.equals("C")) {v_membergubun = "기업";}
                        else if(v_membergubun.equals("U")) {v_membergubun = "대학";}
                        else  {v_membergubun = "개인";}

                        //환불 상태
                        if(v_paystat.equals("CW")) {v_paystat = "환불중";}
                        else if(v_paystat.equals("CY")) {v_paystat = "완료";}
                        else  {v_paystat = "미완료";}

                        if(v_isbelongcourse.equals("Y"))
                        {
                            //if(k == v_subjcnt) v_tmp_subj = "";

                            if(!v_tmp_course.equals(v_course) && !v_tmp_course.equals("000000"))
                            {
                                k = 1;
                                v_tmp_course = v_course;
                                j++;
                                out.print("<tr align='left'>");
                                out.print("<td>"+j+"</td>");
                                out.print("<td>"+v_grseqnm+"</td>");
                                out.print("<td>"+v_coursenm+"</td>");
                                out.print("<td>"+v_membergubun+"</td>");
                                out.print("<td>"+v_userid+"</td>");
                                out.print("<td>"+v_name+"</td>");
                                out.print("<td>"+StringManager.cutZero(v_subjseqgr)+"</td>");
                                out.print("<td>"+v_isonoff_value+"</td>");
                                out.print("<td>"+v_paymoney+"</td>");
                                out.print("<td>"+v_paystat+"</td>");
                                out.print("<td>"+v_canceldate+"</td>");
                                out.print("<td>"+v_cancelkind_value+"</td>");
                                out.print("<td>"+v_reason+"</td>");
                                out.print("</tr>");
                            }
                        }
                        else
                        {
                            k++;
                            j++;
                            out.print("<tr align='left'>");
                            out.print("<td>"+v_dispnum+"</td>");
                            out.print("<td>"+v_grseqnm+"</td>");
                            out.print("<td>"+v_subjnm+"</td>");
                            out.print("<td>"+v_membergubun+"</td>");
                            out.print("<td>"+v_userid+"</td>");
                            out.print("<td>"+v_name+"</td>");
                            out.print("<td>"+StringManager.cutZero(v_subjseqgr)+"</td>");
                            out.print("<td>"+v_isonoff_value+"</td>");
                            out.print("<td>"+v_paymoney+"</td>");
                            out.print("<td>"+v_paystat+"</td>");
                            out.print("<td>"+v_canceldate+"</td>");
                            out.print("<td>"+v_cancelkind_value+"</td>");
                            out.print("<td>"+v_reason+"</td>");
                            out.print("</tr>");
                        }
                    }
                    if(i == 0 && ss_action.equals("go"))
                    {
                    out.print("<tr>");
                    out.print("<td align=\"center\" bgcolor=\"#F7F7F7\" height=\"50\" colspan=\"13\">등록된 내용이 없습니다</td>");
                    out.print("</tr>");
                    } %>
                </table>
            </td>
        </tr>
    </table>
    <script type="text/javascript">
        window.close();
    </script>
    </body>
</html>