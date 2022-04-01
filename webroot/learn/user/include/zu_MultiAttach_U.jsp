<%
    /**************************************************************************
     * 이 파일을 include할 경우, include하는 파일에서 다음 변수들을 선언/정의해야 한다.
     *Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
     *Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
     *Vector i_fileseqVector     = (Vector)dbox.getObject("d_fileseq");         // 파일 일련번호
     *
     *int    i_fileLimit        = NoticeAdminBean.getFILE_LIMIT();              // 제한 첨부 파일수
     **************************************************************************/
%>

<%
    String styleGubun = "";
    if(gubun != null && gubun.equals("3")) styleGubun = "myclass_write";
    else if(gubun != null && gubun.equals("6")) styleGubun = "study_write";
    else styleGubun = "board_write";
%>

<% int i_fileCnt = i_realfileVector != null ? i_realfileVector.size() : 0 ; %>
<script type="text/javascript" src="/script/portal/FileButton.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    //파일 첨부 관련 스크립트
	var myFileButton = new FileButton("imageswap", "imagesrc");
	
	window.onload = function () { 
	    myFileButton.run(); //문서로딩 후 한꺼번에 바꿈 
	
	    var div_obj     = document.getElementsByName("divFile");
	    var divLen = div_obj.length;

	    var endCnt = <%=i_fileLimit%> - <%=i_fileCnt%>;
	    
	    for(var i = divLen; i > 0 ; i--) {
		    if(i-1 == 0) div_obj[i-1].style.display= "block";
		    else div_obj[i-1].style.display= "none";
	    }

	    if(endCnt == 0){
	        document.getElementById("tempMessage").style.display = "block";
	    }
	} 
    
    var fileMax = <%=i_fileLimit - i_fileCnt %>; // 매 이벤트시 변환되는 최대 첨부가능한 수
    var curFileCnt = <%=i_fileCnt%>;//현재 첨부되어 있는 파일수
    var limitCnt    = <%=i_fileLimit%>; //현재 게시판의 파일첨부 제한수
    var checkedCnt = 0; //체크박스 체크된 파일수
    var minView = 1;

    function calculateAttachmentCnt(){
    	var tempCnt = curFileCnt-checkedCnt; 
    }
    
    function addFile(){

        var div_obj     = document.getElementsByName("divFile");

        var viewCnt     = 0;

        for (var i = 0 ; i < div_obj.length ; i ++) {
            if ( div_obj[i].style.display != "none") {
                viewCnt++;
            }
        }
        
        if (parseInt(viewCnt) >= parseInt(fileMax)){
            alert("더 이상 첨부 하실수 없습니다. 추가 첨부 가능 : " + fileMax + " 개");
            return;
        }

        for (var i = 0 ; i <= fileMax ; i ++) {
            if ( div_obj[i].style.display == "none") {
                
                div_obj[i].style.display = "block";
                var fileObj     = document.getElementById("p_file"+i);
                break;
            }
        }

        //var divFirstButton = document.getElementById("firstDelButton");
        //
        //if( viewCnt > 0) {
        //    //divFirstButton.style.display = "none";
        //}

    }

    function delFile(p) {
      
    	var div_obj     = document.getElementsByName("divFile")[p-1];
        var fileObj     = document.getElementById("p_file"+p);
        var tempObj     = document.getElementsByName("tempFileText")[p-1];
        
        fileObj.select();
        document.selection.clear();
        tempObj.value = "";
        div_obj.innerHTML = div_obj.innerHTML;

        
        div_obj.style.display   = "none";
        if(p == 1){
        	div_obj.style.display   = "block";
        }
    }

    // 삭제 체크 및 첨부 라인의 수에 따른 스크립트 처리
    function checkDel(p){
        var div_obj     = document.getElementsByName("divFile");
        var tempObj     = document.getElementsByName("tempFileText");
        var viewCnt     = 0;

        for (var i = 0 ; i < div_obj.length ; i ++) {
            if ( div_obj[i].style.display != "none") {
                viewCnt++;
            }
        }

        if(p.checked) {
        	if(viewCnt == 0) {
        		div_obj[0].style.display = "block";
        		document.getElementById("tempMessage").style.display = "none";
            }
            fileMax++;
        } else {
            if (parseInt(viewCnt) >= parseInt(fileMax)){
                if(viewCnt == 1){
                	div_obj[0].style.display = "none";
                	document.getElementById("tempMessage").style.display = "block";
                } else {
                    alert("삭제 취소를 위해 첨부파일 라인을 삭제해주세요 \n삭제 필요 갯수 : " + (viewCnt-fileMax+1));
                    p.checked = true;
                    alert(fileMax);
                    return;
                }        
            }
                fileMax--;        
        }
    }

    // 삭제 체크 및 첨부 라인의 수에 따른 스크립트 처리
   //function checkAdd(){
   //    var div_obj     = document.getElementsByName("divFile");
   //    var tempObj     = document.getElementsByName("tempFileText");
   //    var viewCnt     = 0;
   //
   //    for (var i = 0 ; i < div_obj.length ; i ++) {
   //        if ( i !=0 && (tempObj[i].value != "" || div_obj[i].style.display == "block")) {
   //            viewCnt++;
   //        }
   //    }
   //    
   //    if (parseInt(viewCnt) >= parseInt(fileMax)){
   //        alert("삭제 취소를 위해 첨부파일 라인을 삭제해주세요 \n삭제 필요 갯수 : " + (viewCnt-fileMax+1));
   //        return false;
   //    }
   //}

    //파일찾기 클릭 후 내용 변경 후 이벤트
    //function fileChange(p){
    //    var fileObj = document.getElementById("p_file"+p);
    //    var tempObj = document.getElementsByName("tempFileText")[p-1];
    //
    //    tempObj.value = fileObj.value;
    //}
  //-->
    </SCRIPT>
            <table class="<%=styleGubun %> bo_tn">
            <!--[if ie]><colgroup><col width="65px" /><col width="625px" /></colgroup><![endif]-->
            <colgroup><col width="85px" /><col width="625px" /></colgroup>
            <tr class="bgn bon">
                <th class="th"><label for="file">첨부파일</label></th>
                <td class="td pd_ln"><div id="tempMessage" style="display:none;font-style:italic;"> 추가로 파일을 첨부하시려면 삭제를 체크해주세요</div>
                <%  for(int i_i = 1 ; i_i <= i_fileLimit ; i_i ++ ) {%>
                  <div id="divFile" style="padding:5px 0 0 0;">
                  <table border="0"><tr>
                  <td >
                    <input type="text" name="tempFileText" id="tempFileText<%=i_i%>" class="inbox fl_l mg_r6" style="width:300px;" readonly/>
                  </td>
                  <td>
                    <input type="file" name="p_file<%=i_i%>" id="p_file<%=i_i%>" imageswap="true" imagesrc="/images/portal/btn/file_search.gif" onchange="document.getElementById('tempFileText<%=i_i%>').value=this.value;" style="vertical-align:7px;width:0px;cursor:pointer;"/>
                    <!--img src="/images/portal/btn/file_search.gif" /-->
                  </td>
                  <td width="50px" align="center">
                  <% if ( i_i == 1 ) { %>
                    <a href="javascript:addFile();"><img src="/images/portal/btn/file_add.gif" align="absmiddle"/></a>
                  <% } else { %>
                    <a href="javascript:delFile(<%=i_i %>);"><img src="/images/portal/btn/file_delete.gif" align="absmiddle"/></a>
                  <% } %>
                  </td>
                  </tr></table>
                  </div>
                <%} %>
                </td>
            </tr>
            <tr class="file_add">
                <th class="th"></th>
                <td class="td">
                  <% 
                        if( i_realfileVector != null && i_realfileVector.size() > 0 ) { %>
                        ※삭제시 체크를 해주세요.
                        <%
                            for(int i = 0; i < i_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
                            String v_realfile = (String)i_realfileVector.elementAt(i);
                            String v_savefile = (String)i_savefileVector.elementAt(i);
                            String v_fileseq = (String)i_fileseqVector.elementAt(i);
                            
                            if(v_realfile != null && !v_realfile.equals("")) {  %>
                                <p class="file_list">
                                <img src="/images/portal/ico/ico_file.gif" alt="file" class="mg_r6" /><a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a><!--img src="/images/portal/btn/btn_s_close.gif" alt="x" /-->
                                <input type = "checkbox"  name ="p_fileseq<%=i%>" value = "<%= v_fileseq%>" onclick="checkDel(this);"><br>
                                <input type = "hidden" name = "p_realfile<%=i%>"  value = <%=v_realfile%>>
                                <input type = "hidden" name = "p_savefile<%=i%>"  value ="<%=v_savefile%>">
                                </p>
                  <%        }
                          }
                        }
                  %>
                </td>
            </tr>
            </table>
                  
 <input type = "hidden" name="p_upfilecnt"  value = "<%=i_fileCnt%>">
 
 <%
    //*** 업로드화일허용 코드  ///////////////////////////////////////
    ArrayList alist = (ArrayList)CodeAdminBean.getUploadCodeName();
    String s_codenmlist = "";
    for(int a=0; a<alist.size(); a++){
       DataBox data  = (DataBox)alist.get(a);
       s_codenmlist+="."+data.getString("d_codenm");
       if(a<alist.size()-1) s_codenmlist+=",";
    }
    //*** 업로드화일허용 코드  ///////////////////////////////////////   
%>
<%//=s_codenmlist %>
<script language="javascript">  
//파일 확장자 필터링
function limitFile() {
    var ss_codenmlist = "<%=s_codenmlist%>";
    var extArray = ss_codenmlist.split(",");
    var allowSubmit = true;

    for(var j = 0 ; j < <%=i_fileLimit%> ; j++){
        var file = document.getElementById("p_file"+(j+1)).value;
        
        if(file != "") {
            var strAry = file.split(".");
            var strAryLen = strAry.length;

            var ext = "."+strAry[strAryLen-1].toLowerCase();
                             
            for(var i = 0 ; i < extArray.length ; i++) {          
                                   
                if (extArray[i] != ext){ 
                    allowSubmit = false; 
                } else {
                    allowSubmit = true;
                    break;
                }
            }
            if(!allowSubmit) {
                return false;
            }
        }
    }

    return true;
    
}
</script>