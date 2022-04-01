<%
/**
 * file name : za_Approval_E.jsp
 * date      :
 * programmer:
 * function  : 수강신청 승인 리스트 조회화면
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>
<%	response.setHeader("Content-Disposition", "inline; filename=za_Approval_E.xls");
	  response.setHeader("Content-Description", "JSP Generated Data");
	  
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  v_process  = "listPage";

    String  ss_grcode          = box.getString("s_grcode");           //교육그룹
    String  ss_gyear           = box.getString("s_gyear");            //년도
    String  ss_grseq           = box.getString("s_grseq");            //교육차수
    String  ss_grseqnm         = box.getString("s_grseqnm");          //교육차수명
    String  ss_mastercd        = box.getString("s_mastercd");         //과정&코스
    String  ss_upperclass      = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass     = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass      = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse      = box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq         = box.getString("s_subjseq");          //과정 차수
    String  ss_company         = box.getString("s_company");          //회사
    String  ss_selgubun        = box.getString("s_selgubun");
    String  ss_seltext         = box.getString("s_seltext");
    String  ss_seldept         = box.getString("s_seldept");
    String  ss_edustart        = box.getString("s_start");            //교육시작일
    String  ss_eduend          = box.getString("s_end");              //교육종료일
    String  v_isManagerPropose = box.getString("isManagerPropose");              //교육종료일

    int     v_biyong            = 0;           // 금액
    String  v_biyong_view       = "";          // 화면출력용 금액(,추가)
    int     v_total_biyong      = 0;           // 총금액
    String  v_total_biyong_view = "";           // 화면출력용 총금액(,추가)
    String  v_step      = box.getString("p_step");
    String  v_appstatus = box.getString("p_appstatus");

    String  v_gadmin    = box.getString("s_gadmin");

    String []   stat_k  = {"ALL","B","Y","N"};
    String []   stat_v  = {"전체","미처리","승인","반려"};


    String  v_finalString = ""; //최종승인 select box 태그

    int i=0;
    int l=0;
    int     v_usercnt   = 0;

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
	<tr>
		<td align="center" height="30"><font size="3"><b>수강신청 리스트</b></font></td>
	</tr>
  <tr>
    <td align="center" valign="top">
        <!----------------- 과정기준조회 시작 ----------------->
        <table cellspacing="1" cellpadding="5" border="1">
          <tr>
            <td ><b>과정</b></td>
            <td><b>차수</b></td>
            <td><b>회원분류</b></td>
            <td><b>ID</b></td>
            <td><b>성명</b></td>
            <td><b>EMAIL</b></td>
            <td><b>이동전화</b></td>
            <td><b>입금여부</b></td>
            <td><b>최종승인</b></td>
            <td><b>신청일시</b></td>
            <td><b>주민번호</b></td>
            <td><b>회사명</b></td>
            <td><b>최종학력</b></td>
            <td><b>종사분야</b></td>
            <td><b>부서명</b></td>
            <td><b>실결제액</b></td>
            <td><b>수강료</b></td>
            <td><b>결제일</b></td>
            <td><b>결제방법</b></td>
            <td><b>결제자</b></td>
          </tr>

<%  

        ArrayList  list = (ArrayList)request.getAttribute("ApprovalList");
        System.out.println("list.size() = " + list.size());

        int v_today = Integer.parseInt(FormatDate.getDate("yyyyMMddHH"));  //오늘 날짜 일시 초기화

		String v_subj		= "";
		String v_subjnm		= "";
		String v_membergubun= "";
		String v_userid		= "";
		String v_uname		= "";
		String v_subjseq	= "";
		String v_appdate	= "";		
		String v_chkfinal	= "";
		String v_edustart	= "";
		String v_isclosed	= "";
		String v_scyear		= "";
		String v_billstat	= "";
		String v_tmp_subj	= "";
		String v_iscourseYn	= "";
		String v_course		= "";
		String v_coursenm	= "";
		int		v_subjcnt	= 0;
		
		String v_email		= "";
		String v_handphone  = "";
		String v_hometel	= "";
		String v_comptel	= "";
		
		String v_resno		= "";
		String v_comptext   = "";
		String v_jikupnm	= "";
		String v_workfieldnm	= "";
		String v_degreenm   = "";
        String v_deptnam    = "";

        String v_paymethod ="";
        String v_paymethodnm ="";
        String v_pgauthdate ="";
        String v_inputdate ="";
        String v_buyername ="";
        String v_inputname ="";       

        for (i=0; i<list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);

			if(dbox != null){

				v_biyong            = dbox.getInt("d_biyong");
				v_biyong_view       = NumberFormat.getInstance().format(v_biyong);
				v_total_biyong     += v_biyong;
				v_finalString = "";
				v_subj			= dbox.getString("d_scsubj");
				v_subjnm		= dbox.getString("d_subjnm");
				v_membergubun	= dbox.getString("d_membergubun");
				v_userid		= dbox.getString("d_userid");
				v_uname			= dbox.getString("d_name");
				v_subjseq		= dbox.getString("d_scsubjseq");
				
				v_appdate		= dbox.getString("d_appdate");			
				v_chkfinal		= dbox.getString("d_chkfinal");
				v_edustart		= dbox.getString("d_edustart");
				v_isclosed		= dbox.getString("d_isclosed");
				v_scyear		= dbox.getString("d_scyear");
				v_billstat		= dbox.getString("d_billstat");

				v_course		= dbox.getString("d_course");
				v_coursenm		= dbox.getString("d_coursenm");
				v_iscourseYn	= dbox.getString("d_isbelongcourse");
				v_subjcnt		= dbox.getInt("d_subjcnt");
				
				v_email			= dbox.getString("d_email");
				v_handphone  	= dbox.getString("d_handphone");
				v_hometel		= dbox.getString("d_hometel");
				v_comptel		= dbox.getString("d_comptel");
				
				//v_resno			= dbox.getString("d_resno");
                v_resno			= dbox.getString("d_resno1") + dbox.getString("d_resno2");
				v_comptext  	= dbox.getString("d_comptext");
				v_jikupnm		= dbox.getString("d_jikupnm");
				v_workfieldnm	= dbox.getString("d_workfieldnm");
				v_degreenm		= dbox.getString("d_degreenm"); 
                v_deptnam		= dbox.getString("d_deptnam");

                v_paymethod	     = dbox.getString("d_paymethod");  
			    v_paymethodnm    = dbox.getString("d_paymethodnm");
                v_pgauthdate 	 = dbox.getString("d_pgauthdate");
                v_inputdate		 = dbox.getString("d_inputdate");
                v_buyername		 = dbox.getString("d_buyername");
                v_inputname	     = dbox.getString("d_inputname");

				if (!v_resno.equals("")) { 
				 if (v_resno.length()==13) {
				 	//v_resno = v_resno.substring(0,6) + "-" + v_resno.substring(6,7) + "******";
				 	v_resno = v_resno.substring(0,6) + "-" + v_resno.substring(6,13);
				 } else {
				 	v_resno = "";
				 }
			    }   
				
				//결제 상태
				if(v_billstat.equals(""))
				{
					v_billstat = "미완료";
				}

				//회원구분
				if(v_membergubun.equals("C")) {v_membergubun = "기업";}
				else if(v_membergubun.equals("U")) {v_membergubun = "대학";}
				else  {v_membergubun = "개인";}

				if(v_isclosed.equals("N"))
				{             
					if (v_chkfinal.equals("Y"))   v_finalString = "승인" ;
					else if (v_chkfinal.equals("N"))  v_finalString = "반려";
					else    v_finalString = "미처리";

				}else{  //수료처리 완료된 경우
					v_finalString = "학습종료";

					if (v_chkfinal.equals("B")){
						v_finalString += "(미처리)";
					}else if (v_chkfinal.equals("Y")){
						v_finalString += "(승인)";
					}else if (v_chkfinal.equals("N")){
						v_finalString += "(반려)";
					}

				}
			}
%>
<% if(v_iscourseYn.equals("Y"))
	{ 
		if(l == v_subjcnt) v_tmp_subj = "";
		
		if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
		{
			l = 1;
			
			%>
				
         <tr align="left">
            <td ><%=v_coursenm%></td>
			<td ><%=v_subjseq%></td>
			<!--td ><%//=v_subjnm%></td-->
			<td ><%=v_membergubun%></td>
			<td style='mso-number-format:"\@";'><%=v_userid%></td>
			<td ><%=v_uname%></td>
			<td ><%=v_email%></td>
			<td style='mso-number-format:"\@";'><%=v_handphone%></td>
			<td ><%=v_billstat%></td>
			<td ><%=v_finalString%></td>
			<td ><%=FormatDate.getFormatDate(v_appdate,"yyyy-MM-dd HH:mm:ss")%></td>
			<td style='mso-number-format:"\@";'><%=v_resno		%></td>
			<td ><%=v_comptext  %></td>
			<td ><%=v_degreenm  %></td>
			<td ><%=v_workfieldnm	%></td>
             <td ><%=v_deptnam	%></td>

             <td ><%= dbox.getCommaInt("d_price") %></td>
             <td ><%=dbox.getCommaInt("d_biyong")%></td>
             <td><% if (v_paymethod.equals("Card") || v_paymethod.equals("VCard") || v_paymethod.equals("DirectBank"))  {
					out.print(FormatDate.getFormatDate(v_pgauthdate,"yyyy-MM-dd"));
			      } else if (v_paymethod.equals("BankBook")) {
			   		out.print(FormatDate.getFormatDate(v_inputdate,"yyyy-MM-dd"));
			      }
			    %></td>
             <td ><%=v_paymethodnm%></td>
			<td>
			<% if (v_paymethod.equals("Card") || v_paymethod.equals("VCard") || v_paymethod.equals("DirectBank"))  {
					out.print(v_buyername);
			   } else if (v_paymethod.equals("BankBook")) {
			   		out.println(v_inputname);
			   }
			%>
			</td>

	<%	
			v_tmp_subj = v_course;
		}else{ 
			l++;
		%>
			<!--td ><%//=v_subjnm%></font></td-->
<%		}
	} else { %>

            <td ><%=v_subjnm%></td>			
			<td ><%=v_subjseq%></td>
			<td ><%=v_membergubun%></td>
			<td style='mso-number-format:"\@";'><%=v_userid%></td>
			<td ><%=v_uname%></td>
			<td style='mso-number-format:"\@";'><%=v_email%></td>
			<td style='mso-number-format:"\@";'><%=v_handphone%></td>
			<td ><%=v_billstat%></td>
			<td ><%=v_finalString%></td>
			<td ><%=FormatDate.getFormatDate(v_appdate,"yyyy-MM-dd HH:mm:ss")%></td>
			<td style='mso-number-format:"\@";'><%=v_resno		%></td>
			<td ><%=v_comptext  %></td>
			<td ><%=v_degreenm  %></td>
			<td ><%=v_workfieldnm	%></td>
            <td ><%=v_deptnam	%></td>

             <td ><%= dbox.getCommaInt("d_price") %></td>
             <td ><%=dbox.getCommaInt("d_biyong")%></td>
             <td><% if (v_paymethod.equals("Card") || v_paymethod.equals("VCard") || v_paymethod.equals("DirectBank"))  {
					out.print(FormatDate.getFormatDate(v_pgauthdate,"yyyy-MM-dd"));
			      } else if (v_paymethod.equals("BankBook")) {
			   		out.print(FormatDate.getFormatDate(v_inputdate,"yyyy-MM-dd"));
			      }
			    %></td>
             <td ><%=v_paymethodnm%></td>
			<td>
			<% if (v_paymethod.equals("Card") || v_paymethod.equals("VCard") || v_paymethod.equals("DirectBank"))  {
					out.print(v_buyername);
			   } else if (v_paymethod.equals("BankBook")) {
			   		out.println(v_inputname);
			   }
			%>
			</td>             
            
<% } %>     
                
            </tr>
<%          //}
        }
		if (i == 0)
			{ %>
			<tr>
				<td align="center"  colspan="20">등록된 내용이 없습니다.</td>
			  </tr>
			<%}
    //}

%>
        </table>
      </td>
  </tr>
</table>
</body>
</html>