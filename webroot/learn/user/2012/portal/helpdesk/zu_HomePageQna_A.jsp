
<%	
    DataBox Ansbox        = (DataBox)request.getAttribute("selectAns");
    
	String v_anstitle      = "";
	String v_anscontents   = "";
	String v_ansinuserid   = "";
	String v_ansindate     = "";
	String v_ansname       = "";
	String v_anscategorynm = "";
	
	
	if(Ansbox != null) {
	    v_ansinuserid          = Ansbox.getString("d_inuserid");
	    v_ansname              = Ansbox.getString("d_name");
	    v_ansindate            = Ansbox.getString("d_indate");
	    v_anstitle             = Ansbox.getString("d_title");
	    v_anscontents          = Ansbox.getString("d_contents");
	    v_anscategorynm        = Ansbox.getString("d_categorynm");
	    
	}

%>
	<div class="viewanswer" id="skip02">
			<table class="answer" cellpadding="0" cellspacing="0">
				<colgroup><col width="70"><col width="*"></colgroup>
				<tr>
					<th>
						<h2>Answer</h2>
						<h6><%= FormatDate.getFormatDate(v_ansindate, "yyyy.MM.dd") %></h6>
					</th>
					<td>
						<b><%= v_anstitle %></b><br>
						<br>
						<%=v_anscontents%>
					</td>
					<td></td>
				</tr>
			</table>
		
        	
            <!-- 파일첨부 시작 -->
            <%
                i_realfileVector    = (Vector)Ansbox.getObject("d_realfile");        // 실제 파일명
                i_savefileVector    = (Vector)Ansbox.getObject("d_savefile");        // 저장 파일명
                i_boardStyle        = "board_view";                                // 보드 스타일
            %>
            <%@ include file="/learn/user/include/zu_MultiAttach_2012_R.jsp" %>
            <!-- 파일첨부 종료-->
		
		 </div>