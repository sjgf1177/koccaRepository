<%@ page contentType = "text/html;charset=euc-kr" %>
<%
    /**************************************************************************
     * 이 파일을 include할 경우, include하는 파일에서 다음 변수들을 선언/정의해야 한다.
     *Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
     *Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
     *Vector i_fileseqVector     = (Vector)dbox.getObject("d_fileseq");         // 파일 일련번호
     *
     **************************************************************************/
%> 


<%  int i_fileCnt = Integer.parseInt(request.getParameter("i_filecnt")); // 최대 파일 첨부수 %>

<script type="text/javascript" src="/script/portal/FileButton.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
//첨부관련 스크립트
var myFileButton = new FileButton("imageswap", "imagesrc");

window.onload = function () { 
    myFileButton.run(); //문서로딩 후 한꺼번에 바꿈 

    var div_obj     = document.getElementsByName("divFile");
    var divLen = div_obj.length;
    
    for(var i = 0 ; i < divLen ; i++) {
        if(i != 0 ) {
            div_obj[i].style.display= "none";
        }
    }
} 

// Bean 클래스에 명시된 제한 파일 갯수
var fileMax = <%=i_fileCnt %>;

function addFile(){

    var div_obj     = document.getElementsByName("divFile");

    var viewCnt     = 0;

    for (var i = 0 ; i < div_obj.length ; i ++) {
        if ( div_obj[i].style.display != "none") {
            viewCnt++;
        }
    }
    
    if (parseInt(viewCnt) >= parseInt(fileMax)){
        alert("더 이상 첨부 하실수 없습니다. 최대 첨부수 : " + fileMax);
        return;
    }

    for (var i = 0 ; i <= fileMax ; i ++) {
        if ( div_obj[i].style.display == "none") {
            
            div_obj[i].style.display = "block";
            var fileObj     = document.getElementById("p_file"+i);
            break;
        }
    }
}

function delFile(p) {
 
    var div_obj     = document.getElementsByName("divFile")[p-1];
    var fileObj     = document.getElementById("p_file"+p);
    var tempObj     = document.getElementById("tempFileText"+p);
    
    fileObj.select();
    document.selection.clear();
    tempObj.value = "";
    div_obj.innerHTML = div_obj.innerHTML;
    
    div_obj.style.display   = "none";
}

function searchFile(idx){
    var fileObj    = document.getElementById("p_file"+idx);
    var tempObj    = document.getElementById("tempFileText"+idx);

    //tempObj.value = fileObj.value;
}

function changeFile(idx){
    var fileObj    = document.getElementById("p_file"+idx);
    var tempObj    = document.getElementById("tempFileText"+idx);

    tempObj.value = fileObj.value;
}
function fileover(imgs) {
    imgs.imagesrc = imgs.imagesrc.replace(".gif", "on.gif");
}
function fileout(imgs) {
    imgs.imagesrc = imgs.imagesrc.replace("on.gif", ".gif");
}
//-->
</SCRIPT>

            <%  if( i_fileCnt > 0) { %>
            <table class="study_write bo_tn">
            <!--[if ie]><colgroup><col width="65px" /><col width="605px" /></colgroup><![endif]-->
            <colgroup><col width="85px" /><col width="605px" /></colgroup>
            <tr class="bgn">
                <th class="th"><label for="file">첨부파일</label></th>
                <td class="td pd_ln">
            <%      for(int i_i = 1; i_i < i_fileCnt; i_i++) {      //     첨부파일 갯수에 따라%>
                    <div id="divFile">
                    <table border=0 width="100%">
                    <tr><td width="89%">
                    <input type="text" name="tempFileText<%=i_i%>" id="tempFileText<%=i_i%>" class="inbox fl_l mg_r6" style="width:445px;padding:5px 0 0 0;"/>
                    <input type="file" name="p_file<%=i_i%>" id="p_file<%=i_i%>" imageswap="true" imagesrc="/images/portal/btn/file_search.gif" onchange="document.getElementById('tempFileText<%=i_i%>').value=this.value;" style="padding:0px 0 0 3;cursor:pointer;" onmouseover="fileover(this)" onmouseout="fileout(this)"/>
                    </td>
                    <td width="11%">
                    <% if ( i_i == 1 ) { %>
                    <a href="javascript:addFile();" class="board_btn" style="padding:0px 0 0 0;"><span>추가</span></a>
                    <% } else { %>
                    <a href="javascript:delFile(<%=i_i %>);" class="board_btn" style="padding:0px 0 0 0;"><span>제거</span></a>
                    <% } %>
                    </td>
                    </tr>
                    </table>
                    </div>
            <%     }  %>
                </td>
            </tr>
            </table>
 <%        }%>
 
 
                
            
 
                    