<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import="java.util.*"%>
<%@ page import="com.credu.system.CodeAdminBean"%>
<%@ page import="com.credu.homepage.*"%>
<%@ page import="com.credu.library.*"%>
<%
    /**************************************************************************
     * 이 파일을 include할 경우, include하는 파일에서 다음 변수들을 선언/정의해야 한다.
     *
     *int    i_fileLimit        = NoticeAdminBean.getFILE_LIMIT();              // 제한 첨부 파일수
     *int    gubun              = //대메뉴 구분값
     **************************************************************************/
%>

<script type="text/javascript" src="/script/portal/FileButton.js"></script>
<SCRIPT LANGUAGE="JavaScript">
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
var fileMax = <%=i_fileLimit %>;

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
    //document.selection.clear();
    
    var $file = $("#p_file"+p);
    var clone = $file.clone();
    $file.before(clone).remove();
    
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

</SCRIPT>

<%
for(int i_i = 1 ; i_i <= i_fileLimit ; i_i ++ ) {
%>
		<div id="divFile" name="divFile">
			<input type="hidden" name="tempFileText<%=i_i%>" id="tempFileText<%=i_i%>" readonly />
			<input type="file" name="p_file<%=i_i%>" id="p_file<%=i_i%>" onchange="document.getElementById('tempFileText<%=i_i%>').value=this.value;" style="display:inline ;" />
			<% if ( i_i == 1 ) { %> <a href="javascript:addFile();">
				<img src="/images/portal/btn/file_add.gif" border=0 /></a>
			<% } else { %>
				<a href="javascript:delFile(<%=i_i %>);"><img src="/images/portal/btn/file_delete.gif" border=0 /></a>
			<% } %>
		</div>
<%
}

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