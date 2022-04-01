<%	
//     DataBox Ansbox        = (DataBox)request.getAttribute("selectAns");

	String v_anstitle      = "";
	String v_anscontents   = "";
	String v_ansinuserid   = "";
	String v_ansindate     = "";
	String v_ansname       = "";
	String v_anscategorynm = "";
	
	ArrayList list = (ArrayList)request.getAttribute("selectAns");
	DataBox Ansbox = null;
	if(list.size() != 0) {
	
	    for(int i = 0; i < list.size(); i++) {
	    	Ansbox = (DataBox)list.get(i);
			if(Ansbox != null) {
			    v_ansinuserid          = Ansbox.getString("d_inuserid");
			    v_ansname              = Ansbox.getString("d_name");
			    v_ansindate            = Ansbox.getString("d_indate");
			    v_anstitle             = Ansbox.getString("d_title");
			    v_anscontents          = Ansbox.getString("d_contents");
			    v_anscategorynm        = Ansbox.getString("d_categorynm");
			    
			}
	    }
	}


%>
	<%if( box.getSession("tem_grcode").equals("N000001")) { %>
		<tr>
		<td colspan="8">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			 <tr>
          <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
          <td><img src="/images/portal/homepage_renewal/myclass/sstitle_04.gif"/></td>
          	 </tr>
          	 </table>
          	 </td>
        </tr>
        <tr>
          <td colspan="8" height="1" bgcolor="#7692CB"></td>
        </tr>
        <tr>        	
        	<td colspan="8" class="blue_txt" bgcolor="#F6F6F6"><br/><%= v_anstitle %><span class="h_road"> (<%= FormatDate.getFormatDate(v_ansindate, "yyyy.MM.dd") %>)</span></td>
        </tr>
        <tr>
        	<td colspan="8" bgcolor="#F6F6F6"><br/><%=v_anscontents%><br/></td>
        </tr>
            
            <!-- 파일첨부 시작 -->
            <%
	            if(Ansbox.getObject("d_realfile") != null && !Ansbox.getObject("d_realfile").equals("")){
	                i_realfileVector    = (Vector)Ansbox.getObject("d_realfile");        // 실제 파일명
	                i_savefileVector    = (Vector)Ansbox.getObject("d_savefile");        // 저장 파일명
	                i_boardStyle        = "board_view";                                // 보드 스타일
	            }
            %>
            <%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %>
            <!-- 파일첨부 종료-->
		
		 <tr>
          <td height="1" colspan="8" bgcolor="CACACA"></td>
        </tr>
        <tr>
          <td height="4" colspan="8" bgcolor="F7F7F7"></td>
        </tr>
		
	<%}else{ %>
            <h3><img src="/images/portal/common/stit_inquiryanswer.gif" alt="문의답변" /></h3>
            <dl class="inan">
                <dt><strong><%= v_anstitle %></strong> (<%= FormatDate.getFormatDate(v_ansindate, "yyyy.MM.dd") %>)</dt>
                <dd><%=v_anscontents%></dd>
            </dl>
            
            <!-- 파일첨부 시작 -->
            <%
            	if(Ansbox.getObject("d_realfile") != null && !Ansbox.getObject("d_realfile").equals("")){
	                i_realfileVector    = (Vector)Ansbox.getObject("d_realfile");        // 실제 파일명
	                i_savefileVector    = (Vector)Ansbox.getObject("d_savefile");        // 저장 파일명
	                i_boardStyle        = "board_view";                                // 보드 스타일
            	}
            %>
            <%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %>
            <!-- 파일첨부 종료-->
            <%} %>
