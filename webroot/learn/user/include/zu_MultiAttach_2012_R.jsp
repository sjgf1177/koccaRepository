<%
    /**************************************************************************
     * 이 파일을 include할 경우, include하는 파일에서 다음 변수들을 선언/정의해야 한다.
     *Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
     *Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
     *Vector i_fileseqVector     = (Vector)dbox.getObject("d_fileseq");         // 파일 일련번호
     *String i_boardStyle        = "";                                          // 보드 스타일
     *
     **************************************************************************/
%> 

<%{
	
	String sFileExt ="";
	String imgExt ="";
    String v_tabseqche = box.getStringDefault("p_tabseq", "0000");
%>
			<ul class="fileload">
            <%{
                int i_fileCnt = i_realfileVector != null ? i_realfileVector.size() : 0 ; 
           		if( i_realfileVector != null && i_realfileVector.size() > 0) { 
            		if(i_realfileVector != null && !i_realfileVector.equals("") ){
            			if(!v_tabseqche.equals("2262")){
							for(int i_i = 0; i_i < i_realfileVector.size(); i_i++) {      //     첨부파일 갯수에 따라
		                        String v_realfile = (String)i_realfileVector.elementAt(i_i);
	    	                    String v_savefile = (String)i_savefileVector.elementAt(i_i);      
	
	        	                if(v_realfile != null && !v_realfile.equals("")) { 
	            	            	sFileExt = 	v_savefile.substring(v_savefile.lastIndexOf(".")+1);
	
	            	            	if (sFileExt.equals("hwp")) {
	                        			imgExt = "<img src='/images/2012/common/icon/icon_hwp.gif' id='exticon' align='absmiddle' alt='한글문서'>";
	                        		} else if (sFileExt.equals("doc")) {
		                        		imgExt = "<img src='/images/2012/common/icon/icon_doc.gif' id='exticon' align='absmiddle' alt='워드문서'>";
	    	                    	} else if (sFileExt.equals("ppt")) {
	        	                		imgExt = "<img src='/images/2012/common/icon/icon_ppt.gif' id='exticon' align='absmiddle' alt='프레젠테이션'>";
	            	            	} else if (sFileExt.equals("xls")) {
	                	        		imgExt = "<img src='/images/2012/common/icon/icon_xls.gif' id='exticon' align='absmiddle' alt='엑셀'>";
	                    	    	} else {
	                        			imgExt = "<img src='/images/2012/common/icon/icon_idk.gif' id='exticon' align='absmiddle' alt='알 수 없는 파일형식'>";
	                        		}%>
					<li><%=imgExt%><a href='http://edu.kocca.or.kr/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a></li>
	                <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
		                    	<%}
							}
            			}else{
            				for(int i_i = 1; i_i < i_realfileVector.size(); i_i++) {      //     첨부파일 갯수에 따라
		                        String v_realfile = (String)i_realfileVector.elementAt(i_i);
	    	                    String v_savefile = (String)i_savefileVector.elementAt(i_i);      
	
	        	                if(v_realfile != null && !v_realfile.equals("")) { 
	            	            	sFileExt = 	v_savefile.substring(v_savefile.lastIndexOf(".")+1);
	
	            	            	if (sFileExt.equals("hwp")) {
	                        			imgExt = "<img src='/images/2012/common/icon/icon_hwp.gif' id='exticon' align='absmiddle' alt='한글문서'>";
	                        		} else if (sFileExt.equals("doc")) {
		                        		imgExt = "<img src='/images/2012/common/icon/icon_doc.gif' id='exticon' align='absmiddle' alt='워드문서'>";
	    	                    	} else if (sFileExt.equals("ppt")) {
	        	                		imgExt = "<img src='/images/2012/common/icon/icon_ppt.gif' id='exticon' align='absmiddle' alt='프레젠테이션'>";
	            	            	} else if (sFileExt.equals("xls")) {
	                	        		imgExt = "<img src='/images/2012/common/icon/icon_xls.gif' id='exticon' align='absmiddle' alt='엑셀'>";
	                    	    	} else {
	                        			imgExt = "<img src='/images/2012/common/icon/icon_idk.gif' id='exticon' align='absmiddle' alt='알 수 없는 파일형식'>";
	                        		}%>
					<li><%=imgExt%><a href='http://edu.kocca.or.kr/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a></li>
	                <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
		                    	<%}
							}
            			}
					}   
				}
			}
		}%>
 		</ul> 
 