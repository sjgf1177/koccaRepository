<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.dunet.common.util.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String s_userid = (String)request.getSession().getAttribute("userid");
    
    DataBox fileBox = (DataBox) request.getAttribute("fileBox");
    
    Vector i_realfileVector = null;
    Vector i_savefileVector = null;
    Vector i_filenoVector   = null;
    
    if(fileBox != null) {
    	i_realfileVector = (Vector)fileBox.getObject("d_realfileVector");
    	i_savefileVector = (Vector)fileBox.getObject("d_savefileVector");
    	i_filenoVector   = (Vector)fileBox.getObject("d_filenoVector");
    }
    
    int i_maxFileCnt = box.getInt("p_maxFileCnt");                             // 현재 게시판의 최대 파일 첨부수
    int i_fileCnt = i_realfileVector != null ? i_realfileVector.size() : 0 ;   // 현재 게시물의 첨부된 파일수
    
    int i_possibleFileCnt = i_maxFileCnt - i_fileCnt; // '추가' 가능한 파일 수
    
%>
            <table class="study_write bo_tn">
            <!--[if ie]><colgroup><col width="65px" /><col width="605px" /></colgroup><![endif]-->
            <colgroup><col width="85px" /><col width="605px" /></colgroup>
            <tr class="bgn">
                <th class="th"><label for="file">첨부파일</label></th>
                <td class="td pd_ln">
                <%  if( i_realfileVector != null && i_realfileVector.size() > 0) { 
                        for(int i_i = 0; i_i < i_realfileVector.size(); i_i++) {      //     첨부파일 갯수에 따라
            
	                        String v_realfile = (String)i_realfileVector.elementAt(i_i);
	                        String v_savefile = (String)i_savefileVector.elementAt(i_i);
	                        String v_fileno   = (String)i_filenoVector.elementAt(i_i);
                           
	                        if(v_realfile != null && !v_realfile.equals("")) {  %>
                    <img src="/images/portal/ico/ico_file.gif" alt="file" />
                    <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><span><%= v_realfile%></span></a>
                    <a href="javascript:deleteAttachment(<%=v_fileno %>, '<%=StringUtil.replaceAll(v_savefile, "\\", "/") %>');"><img src="/images/portal/btn/btn_s_close.gif" class="va_m" alt="x" /></a>
                    <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
                    <%=i_realfileVector.size()-1 == i_i ? "" : "<br/>" %>
                <%          }   
                        }  %>
                    <input type = "hidden" name="p_upfilecnt"  value = "<%=i_fileCnt%>">
                <%  }
                    if(i_possibleFileCnt > 0){
                        for(int i_i = 1; i_i <= i_possibleFileCnt; i_i++) {      //     첨부파일 갯수에 따라%>
                <div id="divFile">
                    <table border=0 width="100%">
                    <tr><td width="89%">
                    <input type="text" name="tempFileText<%=i_i%>" id="tempFileText<%=i_i%>" class="inbox fl_l mg_r6" style="width:445px;padding:5px 0 0 0;" />
                    <input type="file" name="p_file<%=i_i%>" id="p_file<%=i_i%>" imageswap="true" imagesrc="/images/portal/btn/file_search.gif" onchange="document.getElementById('tempFileText<%=i_i%>').value=this.value;" style="padding:0px 0 0 3;cursor:pointer;" />
                    </td>
                    <td width="11%">
                    <%      if ( i_i == 1 ) { %>
                    <a href="javascript:addFile();" class="board_btn" style="padding:0px 0 0 0;"><span>추가</span></a>
                    <%      } else { %>
                    <a href="javascript:delFile(<%=i_i %>);" class="board_btn" style="padding:0px 0 0 0;"><span>제거</span></a>
                    <%      } %>
                    </td>
                    </tr>
                    </table>
                    </div>
                 <%      }
                    } else {%>
                    <div>추가로 파일을 첨부하기 위해서는 기존 파일을 삭제하셔야 합니다.</div>
                 <% } %> 
                </td>
            </tr>
            </table>
