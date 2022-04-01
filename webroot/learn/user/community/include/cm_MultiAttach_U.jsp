<%@ page contentType = "text/html;charset=euc-kr" %>
<%
    /**************************************************************************
     * 이 파일을 include할 경우, include하는 파일에서 다음 변수들을 선언/정의해야 한다.
     *String v_cmuno    // 커뮤니티 번호
     *String v_menuno   // 메뉴 번호
     *String v_brdno    // 보드 번호
     *String v_filecnt  // 현재 게시판의 최대 첨부 파일수
     *int    v_attach_cnt // 현재 게시물의 첨부된 파일수
     **************************************************************************/
%> 

<script type="text/javascript" src="/script/portal/FileButton.js"></script>
<script type='text/javascript' src='/script/portal/prototype-1.6.0.2.js'></script>
<script language="JavaScript">
	
	//첨부관련 스크립트
	var myFileButton = new FileButton("imageswap", "imagesrc");

	function fileButtonInit() { 
	    myFileButton.run(); //문서로딩 후 한꺼번에 바꿈 
	
	    var div_obj     = document.getElementsByName("divFile");
	    var divLen = div_obj.length;
	    
	    for(var i = 0 ; i < divLen ; i++) {
	        if(i != 0 ) {
	            div_obj[i].style.display= "none";
	        }
	    }
	}
    function multiAttachInit(){
        
        var url     = "/servlet/controller.community.CommunityMultiAttachServlet";
        var pars  = "p_process=updatePage"
                  + "&p_cmuno=" + encodeURIComponent("<%=v_cmuno%>")
                  + "&p_menuno=" + encodeURIComponent("<%=v_menuno%>")
                  + "&p_brdno=" + encodeURIComponent("<%=v_brdno%>")
                  + "&p_maxFileCnt=" + encodeURIComponent("<%=v_filecnt%>");
    
        //new Ajax.Updater({success: 'multiAttach_div'}, url, {method : 'post', parameters : pars, onFailure: reportError, onSuccess: resultList});
        new Ajax.Request (
                url,
                {
                    method: 'post',
                    parameters: pars,
                    onComplete:
                    function(xmlHttp){
                	resultList(xmlHttp);
                    }
                }
        );    
    }

    function reportError() {
        alert("error!");
    }

    function resultList(xmlHttp) {
    	var serverData    = xmlHttp.responseText;         // JSon Data 값을 읽는다.

        var multiAttach_div = document.getElementById("multiAttach_div");

        multiAttach_div.innerHTML = serverData;

        //alert(multiAttach_div.innerHTML);
    	
    	fileButtonInit();
    }

    function deleteAttachment(fileno, savefile){
        
        if (confirm("첨부파일을 삭제하시겠습니까?")) {  
              
	    	var url     = "/servlet/controller.community.CommunityMultiAttachServlet";
	        var pars  = "p_process=deleteAttachment"
	        	+ "&p_cmuno=<%=v_cmuno%>"
	        	+ "&p_menuno=<%=v_menuno%>"
	        	+ "&p_brdno=<%=v_brdno%>"
	        	+ "&p_fileno="+fileno
	        	+ "&p_savefile="+savefile;
	    
	                  //+ "&p_cmuno=" + encodeURIComponent("<%=v_cmuno%>")
	                  //+ "&p_menuno=" + encodeURIComponent("<%=v_menuno%>")
	                  //+ "&p_brdno=" + encodeURIComponent("<%=v_brdno%>")
	                  //+ "&p_fileno=" + encodeURIComponent(fileno)
	                  //+ "&p_savefile=" + encodeURIComponent(savefile);

            new Ajax.Request (
                    url,
                    {
                        method: 'post',
                        parameters: pars,
                        onComplete:
                        function(xmlHttp){
                            resultDeleteAttachment(xmlHttp);
                        }
                    }
            );

        }
    }

    function resultDeleteAttachment(xmlHttp) {   
    	
        var serverData    = xmlHttp.responseText;         // JSon Data 값을 읽는다.
        var evalData      = eval('(' + serverData + ')');
        var sList_obj     = evalData.scfList;
        
        var reqState     = evalData.result[0].STATE;

        if (reqState.toLowerCase() != "succ")
        {
            alert("Ajax 통신중 오류가 발생했습니다.");
            return;
        }else{
            alert("첨부파일이 삭제되었습니다.");
            multiAttachInit();
        }
  }   

</script>
<SCRIPT LANGUAGE="JavaScript">
<!--

// Bean 클래스에 명시된 제한 파일 갯수
var fileMax = <%=v_filecnt %>;

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

//-->
</SCRIPT>
<div id="multiAttach_div"></div>
<script language="JavaScript">
window.onload = function () { 
	 multiAttachInit();
}
</script>
