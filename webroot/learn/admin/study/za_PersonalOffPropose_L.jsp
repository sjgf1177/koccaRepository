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
    
    String	v_tid			= "";
	String	v_year			= "";
    String	v_seq			= "";
    String	v_classname		= "";
	String	v_refundabledate= "";
    String	v_refundableyn	= "";
    String	v_refundyn		= "";
    String	v_canceldate	= "";
    String	v_paymethod		= "";
    String	v_accept_state	= "";
    String	v_rank			= "";
    String	v_cancelableyn	= "";
    String	v_refunddate	= "";
    double	v_biyong		= 0;
    
    ArrayList list2      = null;
    //DEFINED class&variable END
    list2 = (ArrayList)request.getAttribute("PersonalOffProposeList");
%>

 <br>
                        <table class="box_table_out_1" cellspacing="1" cellpadding="5" width="940">
                          <tr>
                            <td colspan="13" class="table_top_line"></td>
                          </tr>
                          <tr>
                            <td class="table_title" width="40%"><b>과정명</b></td>
                            <td class="table_title" width="10%" ><b>교육비</b></td>
                            <td class="table_title" width="20%" ><b>교육기간</b></td>
                            <td class="table_title" width="10%"><b>수강신청일</b></td>
                            <td class="table_title" width="10%" ><b>승인상태<br/>결제상태</b></td>
                            <td class="table_title" width="10%"><b>비고</b></td>
                          </tr>
                      </table>
                      <div style='width:960px;height:230px;overflow:auto;visibility:visible;border:1'>
                      <table class="box_table_out_1" cellspacing="1" cellpadding="5" width="940" >
            <%
            for(i = 0; i < list2.size(); i++)
			{
				DataBox dbox = (DataBox)list2.get(i);
			   
				v_tid			= dbox.getString("d_tid");
				v_subj			= dbox.getString("d_subj");
				v_year			= dbox.getString("d_year");
				v_subjseq		= dbox.getString("d_subjseq");
				v_seq			= dbox.getString("d_seq");
				v_subjnm		= dbox.getString("d_subjnm");
				v_edustart		= dbox.getString("d_edustart");
				v_eduend		= dbox.getString("d_eduend");
				v_appdate		= dbox.getString("d_appdate");
				v_classname		= dbox.getString("d_classname");
				v_refundabledate= dbox.getString("d_refundabledate");
				v_refundableyn	= dbox.getString("d_refundableyn");
				v_refundyn		= dbox.getString("d_refundyn");
				v_canceldate	= dbox.getString("d_canceldate");
				v_paymethod		= dbox.getString("d_paymethod");
				v_chkfirst		= dbox.getString("d_chkfirst");
				v_chkfinal		= dbox.getString("d_chkfinal");
				v_rank			= dbox.getString("d_rank");
				v_cancelableyn	= dbox.getString("d_cancelableyn");
				v_refunddate	= dbox.getString("d_refunddate");
				v_rowspan		= dbox.getInt("d_rowspan");
				v_biyong		= dbox.getDouble("d_biyong");

				v_edustart		= FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
				v_eduend		= FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");
				v_appdate		= FormatDate.getFormatDate(v_appdate,"yyyy.MM.dd");
				v_refundabledate= FormatDate.getFormatDate(v_refundabledate,"yyyy.MM.dd");
				v_canceldate	= FormatDate.getFormatDate(v_canceldate,"yyyy.MM.dd");
				v_refunddate	= FormatDate.getFormatDate(v_refunddate,"yyyy.MM.dd");
				
				if ("U".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
//					v_accept_state	= "발표대기";
					v_accept_state	= "미처리";
				} else if ("Y".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
//					v_accept_state	= "예비합격";
					v_accept_state	= "1차승인";
				} else if ("Y".equals(v_chkfirst) && "N".equals(v_chkfinal)) {
					v_accept_state	= "최종불합격";
				} else if ("Y".equals(v_chkfirst) && "Y".equals(v_chkfinal)) {
//					v_accept_state	= "합격";
					v_accept_state	= "최종승인";
				} else if ("N".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
//					v_accept_state	= "불합격";
					v_accept_state	= "미승인";
				}


               %>
                          <tr>
                            <td class="table_02_2"   width="40%"><%=v_subjnm%></td>
                            <td class="table_02_1" width="10%" ><%if(v_biyong == 0){%>무료<%}else{%><%= new java.text.DecimalFormat("###,###,##0").format(v_biyong) %><%} %></td>
                            <td class="table_02_1" width="20%"><%= v_edustart %> ~ <%= v_eduend %></td>
                            <td class="table_02_1" width="10%"><%if(!v_appdate.equals("")){%><%= v_appdate %><%}else{ %>&nbsp;<%} %></td>
                            <td class="table_02_1" width="10%" ><%if(!v_accept_state.equals("")){ %><%= v_accept_state %><%}else{ %>&nbsp;<%} %><br />
							          <%
											if ("".equals(v_tid) && "N".equals(v_refundyn) && "Y".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
							%>
							                  미결제
							<%
											} else if (!"".equals(v_tid) && "N".equals(v_refundyn)) {
							%>
							                  결제완료
							<%
											} else if ("Y".equals(v_refundyn)) {
							%>
							                  환불완료
							<%
												}
							%>
				          	</td>
				          	<td class="table_02_1" width="10%">
				          		<%
								            if (!"".equals(v_tid) && "N".equals(v_refundyn) && "Y".equals(v_refundableyn)) {
								            	if (!"".equals(dbox.getString("d_canceldate"))) {
								%>
								             <span class="fc_96"> 취소요청일</span><br/><%= v_canceldate %>
								<%                	
												} else {
														if ("Y".equals(v_cancelableyn)) {
								%>
								              <a href="javascript:cancelPropose('<%= v_tid %>','<%= v_paymethod %>')" class="board_btn2">
								              	<img src="/images/portal/homepage_renewal/myclass/btn_off_cancel.jpg" />
								              </a>
								<%
														} else {
								%>
											  <a href="javascript:cancelApply('<%= v_tid %>','<%= v_paymethod %>')" class="board_btn2">
											  	<img src="/images/portal/homepage_renewal/myclass/btn_off_cancel_y.jpg" />
											  </a>
								<%
														}
												}
								            } else if ("".equals(v_tid) && "N".equals(v_refundyn) && "Y".equals(v_refundableyn)) {
								%>
								              <a href="javascript:cancelApplyFirst('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_seq %>')" class="board_btn2">
								              	<img src="/images/portal/homepage_renewal/myclass/btn_off_cancel.jpg" />
								              </a><br>
								              <a href="javascript:updatePropose('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_subjnm %>')" class="board_btn2">
								              	<img src="/images/portal/homepage_renewal/myclass/btn_off_info.jpg" />
								              </a>
								<%
								        	} else {
								        		if ("Y".equals(v_refundyn)) {
								%>
										  <span class="fc_96">취소일</span><br/><span class="ff_t fs_s"><%= v_refunddate %></span>
								<%
								        		}else{%>
								        		&nbsp;<%}
								        	}
								%>
				          	</td>
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