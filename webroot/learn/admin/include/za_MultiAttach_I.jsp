<%@ page import="com.credu.system.CodeAdminBean" %>
<%
    /**************************************************************************
     * �� ������ include�� ���, include�ϴ� ���Ͽ��� ���� �������� ����/�����ؾ� �Ѵ�.
     *
     *int    i_fileLimit        = NoticeAdminBean.getFILE_LIMIT();              // ���� ÷�� ���ϼ�
     *int    gubun              = //��޴� ���а�
     **************************************************************************/
%>

<script type="text/javascript" src="/script/portal/FileButton.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
//÷�ΰ��� ��ũ��Ʈ
var myFileButton = new FileButton("imageswap", "imagesrc");

window.onload = function () { 
    myFileButton.run(); //�����ε� �� �Ѳ����� �ٲ� 

    var div_obj     = document.getElementsByName("divFile");
    var divLen = div_obj.length;
    
    for(var i = 0 ; i < divLen ; i++) {        
        if(i != 0 ) {
            div_obj[i].style.display= "none";
        }
    }
    
} 

// Bean Ŭ������ ���õ� ���� ���� ����
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
        alert("�� �̻� ÷�� �ϽǼ� �����ϴ�. �ִ� ÷�μ� : " + fileMax);
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
                <%  for(int i_i = 1 ; i_i <= i_fileLimit ; i_i ++ ) {%>
                  <div id="divFile" name="divFile">
                  <table border="0"><tr>
                  <td>
                    <input type="text" name="tempFileText<%=i_i%>" id="tempFileText<%=i_i%>" class="inbox fl_l mg_r6" style="width:300px;" readonly/>
                  </td>
                  <td>
                  <input type="file" name="p_file<%=i_i%>" id="p_file<%=i_i%>"  imagesrc="/images/portal/btn/file_search.gif" onchange="document.getElementById('tempFileText<%=i_i%>').value=this.value;" style="vertical-align:7px;width:73px;cursor:pointer;" />
                    <!--<input type="file" name="p_file<%=i_i%>" id="p_file<%=i_i%>" imageswap="true" imagesrc="/images/portal/btn/file_search.gif" onchange="document.getElementById('tempFileText<%=i_i%>').value=this.value;" style="vertical-align:7px;width:0px;cursor:pointer;"/>
                    --><!--img src="/images/portal/btn/file_search.gif" /-->
                  </td>
                  <td width="50px" align="center">
                  <% if ( i_i == 1 ) { %>
                    <a href="javascript:addFile();"><img src="/images/portal/btn/file_add.gif" border=0/></a>
                  <% } else { %>
                    <a href="javascript:delFile(<%=i_i %>);"><img src="/images/portal/btn/file_delete.gif" border=0/></a>
                  <% } %>
                  </td>
                  </tr></table>
                  </div>
                <%} %>
                  
<%
    //*** ���ε�ȭ����� �ڵ�  ///////////////////////////////////////
    ArrayList alist = (ArrayList)CodeAdminBean.getUploadCodeName();
    String s_codenmlist = "";
    for(int a=0; a<alist.size(); a++){
       DataBox data  = (DataBox)alist.get(a);
       s_codenmlist+="."+data.getString("d_codenm");
       if(a<alist.size()-1) s_codenmlist+=",";
    }
    //*** ���ε�ȭ����� �ڵ�  ///////////////////////////////////////   
%>
<%//=s_codenmlist %>
<script language="javascript">  
//���� Ȯ���� ���͸�
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