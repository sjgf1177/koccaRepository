<%
/**
 * file name : za_Finish_E.jsp
 * date      : 2005/10
 * programmer:
 * function  : 수료처리 리스트 및 처리
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>


<%
	response.setHeader("Content-Disposition", "inline; filename=FinishList.xls");   
	//response.setHeader("Content-Description", "JSP Generated Data");
	
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getStringDefault("s_grseq","ALL");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수
	  String  v_action       = box.getString("p_action");

   	ArrayList list = null;
   	list = (ArrayList)request.getAttribute("CompleteListExcel");
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td>
      <table border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" bgcolor="#FFFFFF">

            <!----------------- 집합과정 개인별 수료처리 시작 ----------------->
			<table border="1" cellspacing="1" cellpadding="5" class="table_out">
          <tr> 
            <td width="5%" class="table_title" rowspan="2">No</td>
            <td class="table_title" rowspan="2">과정명</a></td>            
            <td width="10%" class="table_title" rowspan="2">학습기간</td>
 			      <td width="7%" class="table_title" rowspan="2">총강사수</td>
						<td width="5%" class="table_title" rowspan="2">입과<br>인원</td>
						<td width="5%" class="table_title" rowspan="2">수료<br>인원</td>
						<td width="7%" class="table_title" rowspan="2">강사<br>접속횟수</td>
						<td width="7%" class="table_title" rowspan="2">본인게시건수<br>(공지/자료/토론)</td>
						<td width="7%" class="table_title" colspan="2">리포트</td>
						<td width="7%" class="table_title" colspan="2">Q & A</td>
						<td width="6%" class="table_title" rowspan="2">강사료</td>
            <td width="7%" class="table_title" rowspan="2">수료<br>처리일</td>
          </tr>
          <tr> 
            <td class="table_title">제출</td>
            <td class="table_title">미평가</td>            
            <td class="table_title">질의</td>
            <td class="table_title">미응답</td>
          </tr>
<%                                    
	        for( i = 0 ; i < list.size() ; i++ ){
					DataBox dbox = (DataBox)list.get(i);		  
							
					v_subj = dbox.getString("d_subj");  
					v_subjnm = dbox.getString("d_subjnm");  
					v_year = dbox.getString("d_year");
					v_subjseq = dbox.getString("d_subjseq");
					v_seq = dbox.getInt("d_seq");
					v_tuuserid = dbox.getString("d_tuserid");
					v_grayncnt  = dbox.getInt("d_grayncnt");
					v_isclosed  = dbox.getString("d_isclosed");
					v_edustart  = dbox.getString("d_edustart");
					v_eduend  = dbox.getString("d_eduend");
					
					if(v_isclosed.equals("Y")){
							v_grayntxt = String.valueOf(v_grayncnt);
					}else{
					        v_grayntxt = "교육중";
				    }
	%>
			        <tr height="30"> 
			            <td class="table_02_1"><%=list.size()-i%></td>
			            <td class="table_02_2"><%=dbox.getString("d_subjnm")%></td>
									<td class="table_02_1"><%=FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")+" ~ "+FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")%></td>
			            <td class="table_02_1"><%=dbox.getString("d_ctutorcnt")%></td>
			            <td class="table_02_1"><%=dbox.getString("d_stucnt")%></td>
			            <td class="table_02_1"><%=v_grayntxt%></td>
			            <td class="table_02_1"><%=dbox.getString("d_logincnt")%></td>
			            <td class="table_02_1"><%=dbox.getInt("d_gongcnt")+dbox.getInt("d_datacnt")+dbox.getInt("d_toroncnt")+dbox.getInt("d_torontpcnt")%></td>
			            <td class="table_02_1"><%=dbox.getString("d_repcnt")%></td>
			            <td class="table_02_1"><a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>');"><%=dbox.getString("d_noscorecnt")%><a></td>
			            <td class="table_02_1"><%=dbox.getString("d_qcnt")%></td>
									<td class="table_02_1">
										<a href="javascript:whenQnaList('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>');"><%=dbox.getString("d_noanscnt")%><a></td>
			            <td class="table_02_1"><%=dbox.getString("d_pay")%></td>
			            <td class="table_02_1"><%=v_isclosed.equals("Y")?FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyy/MM/dd"):""%></td><!--수료처리일 칼럼이 없어 LDATE 결과 가져옴-->
			         </tr>
	<%
         }
         %>
			</table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
