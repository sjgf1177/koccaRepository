	<%
		ArrayList listRec = (ArrayList)request.getAttribute("selectListRecTop");
		ArrayList listReply = (ArrayList)request.getAttribute("selectListReplyTop");
		String vv_inuserid = "", vv_title ="",  vv_types="" ;
		
		int vv_seq = 0, vv_readcnt = 0,   vv_upfilecnt=0, j =0 , k=0;
		
	%>

		<div class="contentR">
			<!-- 우측 고정 콘텐츠 시작 -->
			<div class="quickicon">
				<div class="groupname"><img src="/images/factory/right_title01.gif" alt="학습지원안내"></div>
				<table class="inicon" cellpadding="0" cellspacing="0">
					<tr>
						<td><a href="javascript:menuForward(4,'01');"></a><img src="/images/factory/quickicon_01.gif" alt="FAQ"></a></td>
						<td><a href="javascript:menuForward(4,'02');"><img src="/images/factory/quickicon_02.gif" alt="Q&A"></a></td>
						<td><a href="javascript:menuForward(4,'09');"><img src="/images/factory/quickicon_03.gif" alt="1:1문의"></a></td>
					</tr>
					<tr>
						<td><a href="javascript:menuForward(4,'03');"><img src="/images/factory/quickicon_04.gif" alt="S/W다운로드"></a></td>
						<td><a href="javascript:menuForward(4,'05');"><img src="/images/factory/quickicon_05.gif" alt="학습환경 도우미"></a></td>
						<td><a href="javascript:menuForward(4,'10');"><img src="/images/factory/quickicon_06.gif" alt="교재안내"></a></td>
					</tr>
				</table>
			</div>
			<div class="inpost">
				<div class="groupname"><img src="/images/factory/right_title02.gif" alt="추천 많은글"></div>
				<table class="recompost" cellpadding="0" cellspacing="0">
					<colgroup><col width="18"><col width="*"></colgroup>
					<!-- 추천 많은 글 베스트 10개 목록 시작 -->
					
					<%
				        if(listRec.size() != 0){
				            for(j = 0; j < 10; j++) {
				            DataBox dbox3 = (DataBox)listRec.get(j);			                   
			                   vv_inuserid   = dbox3.getString("d_inuserid");			                   			                   
			                   vv_title      = dbox3.getString("d_title");
			                   vv_seq        = dbox3.getInt   ("d_seq");
			                   vv_types      = dbox3.getString("d_types");			                   
			                   vv_upfilecnt  = dbox3.getInt   ("d_filecnt");
			                   
			                   vv_title = 	StringUtil.getByteString(vv_title, 30);
			                  	                
					%>
				
				<tr>
					<td><img src="/images/factory/num<%=j+1%>.gif" id="neon1"></td>
					<td><a href="javascript:select('<%=vv_seq%>','<%=vv_upfilecnt%>','<%=vv_inuserid%>','<%=vv_types%>')"><%=vv_title%></a></td>
				</tr>
					
		  			<%} }%>
  					
					<!--<tr>
						<td><img src="/images/factory/num2.gif" id="neon2"></td>
						<td><a href="#" onmouseover="neon2.src='/images/factory/num2over.gif'" onmouseout="neon2.src='/images/factory/num2.gif'">방송교육과정 강사님들의 친절한 답...</a></td>
					</tr>
				--></table>
				<div class="centerline"></div>
				<div class="groupname"><img src="/images/factory/right_title03.gif" alt="최근글"></div>
				<table class="latestpost" cellpadding="0" cellspacing="0">
					<colgroup><col width="14"><col width="*"></colgroup>
					<!-- 목록 반복 구간 시작 -->
					
					<%
				        if(listReply.size() != 0){
				            for(k = 0; k < 10; k++) {
				            DataBox dbox2 = (DataBox)listReply.get(k);			                   
			                   vv_inuserid   = dbox2.getString("d_inuserid");			                   			                   
			                   vv_title      = dbox2.getString("d_title");
			                   vv_seq        = dbox2.getInt   ("d_seq");
			                   vv_types      = dbox2.getString("d_types");			                   
			                   vv_upfilecnt  = dbox2.getInt   ("d_filecnt");
			                   vv_title = 	StringUtil.getByteString(vv_title, 30);
			                  	                
					%>
				
				<tr>
					<td><img src="/images/factory/num<%=k+1%>.gif" id="neon1"></td>
					<td><a href="javascript:select('<%=vv_seq%>','<%=vv_upfilecnt%>','<%=vv_inuserid%>','<%=vv_types%>')"><%=vv_title%></a></td>
				</tr>
					
		  			<%} }%>
		  			
					<!--<tr>
						<td class="alignC"><img src="/images/factory/icon_ring.gif"></td>
						<td class="alignL"><a href="#">방송교육과정 강사님들의 친절한 답...</a></td>
					</tr>
					<tr>
						<td class="alignC"><img src="/images/factory/icon_ring.gif"></td>
						<td class="alignL"><a href="#">방송교육과정 강사님들의 친절한 답...</a></td>
					</tr>
					<tr>
						<td class="alignC"><img src="/images/factory/icon_ring.gif"></td>
						<td class="alignL"><a href="#">방송교육과정 강사님들의 친절한 답...</a></td>
					</tr>
					<tr>
						<td class="alignC"><img src="/images/factory/icon_ring.gif"></td>
						<td class="alignL"><a href="#">방송교육과정 강사님들의 친절한 답...</a></td>
					</tr>
					<tr>
						<td class="alignC"><img src="/images/factory/icon_ring.gif"></td>
						<td class="alignL"><a href="#">방송교육과정 강사님들의 친절한 답...</a></td>
					</tr>
					--><!-- 목록 반복 구간 끝 -->
				</table>
				<div class="centerline"></div>
				<div class="alignL"><img src="/images/factory/show_customer.gif" alt="고객문의"></div>
			</div>
			<!-- 우측 고정 콘텐츠 끝 -->
		</div>
		