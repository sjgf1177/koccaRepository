<%
//**********************************************************
//  1. 제      목: PERSONAL SELECT LIST
//  2. 프로그램명: za_PersonalPropose_L.jsp
//  3. 개      요: 개인 신청과정 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 31
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%
    //DEFINED class&variable START
    String v_grcode     = "";
    String v_grcodenm   = "";
    String v_grseqnm   = "";
    String v_gyear      = "";
    String v_grseq      = "";
    String v_subj       = "";
    String v_subjnm     = "";
    String v_isnewcourse= "";
    String v_course     = "";
    String v_cyear      = "";
    String v_courseseq  = "";
    String v_coursenm   = "";
    String v_subjseq    = "";
    String v_subjseqgr  = "";
    String v_isonoff    = "";
    String v_edustart   = "";
    String v_eduend     = "";
    String v_appdate    = "";
    String v_chkfirst   = "";
    String v_chkfinal   = "";
    String v_onoff_value      = "";
    String v_chkfirst_value   = "";
    String v_chkfinal_value   = "";
    String v_upperclassname        = "";
    String v_isproposeapproval = "";
    String v_proapp_value = "";
    int     v_rowspan    = 0;
    int     i            = 0;
    ArrayList list2      = null;
    
    String  v_upperclass		= "";
    String  v_upperclassnm		= "";
    String  v_year      		= "";
    String  v_kind      		= "1";
    String  v_isgraduated		= "";
    String  v_isgraduated_txt	= "";
    String  v_stustatus 		= "";
    String  v_stustatusnm		= "";
    double	v_score				= 0d;
	String  v_isterm			= "";
    //DEFINED class&variable END
    list2 = (ArrayList)request.getAttribute("PersonalOffGraduationList");
%>

 <br>
                        <table class="box_table_out_1" cellspacing="1" cellpadding="5" width="940">
                          <tr>
                            <td colspan="13" class="table_top_line"></td>
                          </tr>
                          <tr>
                          	<td class="table_title" width="10%"><b>분류</b></td>
                            <td class="table_title" width="40%"><b>과정명</b></td>
                            <td class="table_title" width="30%" ><b>교육기간</b></td>
                            <td class="table_title" width="10%" ><b>성적</b></td>                            
                            <td class="table_title" width="10%"><b>상태</b></td>
                            </tr>
                      </table>
                      <div style='width:960px;height:230px;overflow:auto;visibility:visible;border:1'>
                      <table class="box_table_out_1" cellspacing="1" cellpadding="5" width="940" >
            <%
            for(i = 0; i < list2.size(); i++) {
    			DataBox dbox = (DataBox)list2.get(i);

    			v_subj        	= dbox.getString("d_subj");
    			v_year        	= dbox.getString("d_year");
    			v_subjseq     	= dbox.getString("d_subjseq");
    			v_subjnm      	= dbox.getString("d_subjnm");
    			v_edustart    	= dbox.getString("d_edustart");
    			v_eduend      	= dbox.getString("d_eduend");
    			v_isgraduated 	= dbox.getString("d_isgraduated");
    			v_stustatus 	= dbox.getString("d_stustatus");
    			v_stustatusnm 	= dbox.getString("d_stustatusnm");
    			v_upperclassnm 	= dbox.getString("d_upperclassnm");
                v_score			= dbox.getDouble("d_score");
    			v_isterm 	    = dbox.getString("d_isterm");
                
    			v_edustart		= FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
    			v_eduend		= FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");

    			if ("Y".equals(v_isterm)) {
    				v_isgraduated_txt = v_stustatusnm;
    			} else {
    				if(v_isgraduated.equals("Y"))       v_isgraduated_txt = "수료";
    				else if(v_isgraduated.equals("N"))  v_isgraduated_txt = "미수료";
    			}                       {  v_chkfinal_value  = "미처리"; }


               %>
                          <tr>
                            <td class="table_02_1" width="10%" ><%=v_upperclassnm %></td>
                            <td class="table_02_1" width="40%" ><%=v_subjnm %></td>
                            <td class="table_02_1" width="30%"><%= v_edustart %>~ <%= v_eduend %></td>
                            <td class="table_02_1" width="10%"><%if ("N".equals(v_isterm)) {  %>
					<%= v_score %>
				<%}else{%>
					학기별
				<%}%></td>                            
                            <td class="table_02_1" width="10%"><%= v_isgraduated_txt %>
					          	<%if(v_isgraduated_txt.equals("")){%>
					          		&nbsp;
					          	<%} %></td>
                          </tr>
               <%
              }
          if(i == 0){ %>
              <tr>
                <td class="table_02_1" colspan="11">등록된 내용이 없습니다</td>
              </tr>
         <%  } %>
                        </table>
                        <!----------------- 신청과정 끝 ----------------->
                        <table>
                          <tr>
                            <td height="5"></td>
                          </tr>
                        </table>
                        </div>