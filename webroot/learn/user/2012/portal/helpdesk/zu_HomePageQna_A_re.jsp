
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

		<tr>
          <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
          <td><img src="/images/portal/homepage_renewal/myclass/sstitle_04.gif"/></td>
        </tr>
        <tr>        	
        	<td colspan="8" class="blue_txt">%= v_anstitle %><span class="h_road"> (<%= FormatDate.getFormatDate(v_ansindate, "yyyy.MM.dd") %>)</span></td>
        </tr>
        <tr>
        	<td colspan="8"><%=v_anscontents%></td>
        </tr>
            
            <!-- 파일첨부 시작 -->
            <%
                i_realfileVector    = (Vector)Ansbox.getObject("d_realfile");        // 실제 파일명
                i_savefileVector    = (Vector)Ansbox.getObject("d_savefile");        // 저장 파일명
                i_boardStyle        = "board_view";                                // 보드 스타일
            %>
            <%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %>
            <!-- 파일첨부 종료-->
		
		 <tr>
          <td height="1" colspan="8" bgcolor="CACACA"></td>
        </tr>
        <tr>
          <td height="4" colspan="8" bgcolor="F7F7F7"></td>
        </tr>
		
	
