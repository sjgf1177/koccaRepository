<%@ page contentType="text/html;charset=MS949"%>
<%@ page errorPage="/learn/library/error.jsp"%>

<%
/*
	<jsp:include page="/learn/user/portal/include/singleAttach_include.jsp">
		<jsp:param name="i_imageFlag"           value="Y" />                                    <!-- 이미지 미리보기 여부(Y/N) -->
		<jsp:param name="i_targetImg"           value="img_photo" />                            <!--  i_imageFlag 이 'Y' 일때의 img 오브젝트 id 명시-->
		<jsp:param name="i_realFileName"        value="" />                                     <!-- 실제 파일명 명시 -->    
		<jsp:param name="i_saveFileName"        value="" />                                     <!-- 저장 파일명 명시 -->                        
		<jsp:param name="i_inputFileParamName"  value="p_img_path" />                           <!-- Input File의 name 파라미터 명 -->
		<jsp:param name="i_formName"            value="form1" />                                <!-- Form 이름 -->
		<jsp:param name="i_ServletName"         value="CommunityCreateServlet" />               <!-- 저장 서블릿 이름 -->
		<jsp:param name="i_searchOnImagePath"   value="/images/portal/btn/file_search_on.gif" /><!-- 찾아보기 버튼 마우스 오버 이미지 경로 -->
		<jsp:param name="i_searchOffImagePath"  value="/images/portal/btn/file_search.gif" />   <!-- 찾아보기 버튼 마우스 아웃 이미지 경로-->
		<jsp:param name="i_style"               value="" />                                     <!-- 스타일 명시(버튼 위치 관련) -->
		<jsp:param name="i_year"                value="" />                                     <!-- 과정년도 -->
        <jsp:param name="i_subj"                value="" />                                     <!-- 과정코드-->
	</jsp:include>                                                           

*/
%>

<%@ page import="com.dunet.common.util.UploadUtil"%>
<%@ page import="com.dunet.common.util.StringUtil"%>

<%
    // 전달값 초기화
	String  i_imageFlag            = StringUtil.getString(request.getParameter("i_imageFlag"));
	String  i_targetImg            = StringUtil.getString(request.getParameter("i_targetImg"));
	String  i_realFileName         = StringUtil.getString(request.getParameter("i_realFileName"));
	String  i_saveFileName         = StringUtil.getString(request.getParameter("i_saveFileName"));
	String  i_inputFileParamName   = StringUtil.getString(request.getParameter("i_inputFileParamName"));
	String  i_formName             = StringUtil.getString(request.getParameter("i_formName"));
	String  i_ServletName          = StringUtil.getString(request.getParameter("i_ServletName"));
	String  i_searchOnImagePath    = StringUtil.getString(request.getParameter("i_searchOnImagePath"));
	String  i_searchOffImagePath   = StringUtil.getString(request.getParameter("i_searchOffImagePath"));
	String  i_style                = StringUtil.getString(request.getParameter("i_style"));
	String  i_year                 = StringUtil.getString(request.getParameter("i_year")); 
	String  i_subj                 = StringUtil.getString(request.getParameter("i_subj")); 
	String  i_fileSize             = StringUtil.getString(request.getParameter("i_fileSize"));

	// 미전달시 기본값 설정
	if (i_imageFlag.equals(""))            i_imageFlag = "N";
	if (i_formName.equals(""))             i_formName = "form1";
	if (i_ServletName.equals(""))          i_ServletName = "HomePageQna";
	if (i_searchOnImagePath.equals(""))    i_searchOnImagePath = "/images/portal/btn/file_search_on.gif";
	if (i_searchOffImagePath.equals(""))   i_searchOffImagePath = "/images/portal/btn/file_search.gif";
	if (i_style.equals(""))                i_style = "vertical-align:10px;";
	if (i_fileSize.equals(""))             i_fileSize = "0";

	
	UploadUtil uploadUtil = new UploadUtil();
	
	String sRelativeDir = uploadUtil.getRelativePath(i_ServletName, i_year, i_subj); 

	//업로드 경로 수정
	sRelativeDir = StringUtil.replaceAll(sRelativeDir, '\\', '/');
	
	
	//실제파일명 존재시 명시
	StringBuffer i_sUploadFileHtml = new StringBuffer();
	if(!i_realFileName.equals("")){
		i_sUploadFileHtml.append("&nbsp;&nbsp;"); 
		i_sUploadFileHtml.append("<img src=\"/images/portal/ico/ico_file.gif\" alt=\"file\" class=\"mg_r6\" />");
		i_sUploadFileHtml.append("<a href = \"/servlet/controller.library.DownloadServlet?p_savefile=").append(i_saveFileName).append("&p_realfile="+i_realFileName+"\">").append(i_realFileName).append(" (").append(StringUtil.setString(i_fileSize)).append(" Bytes)</a>");
		i_sUploadFileHtml.append("<span id=\"span_del\" name=\"span_del\" onclick=\"").append(i_inputFileParamName).append("_fnFileDel(this)\"> <img src=\"/images/portal/btn/btn_s_close.gif\" alt=\"x\" style='cursor:pointer;' align='absmiddle'/></span>");
	}
%>

<div id="<%=i_inputFileParamName%>_div_attachMain" style="float: left; padding: 0 5px 0 0;">
    <span id="<%=i_inputFileParamName%>_div_inputFile" style="float: left; padding: 0 5px 0 0;"> 
    <input type="file" name="<%=i_inputFileParamName%>" id="<%=i_inputFileParamName%>"
	       imageswap="true" imagesrc="<%=i_searchOffImagePath%>"
	       onchange="<%=i_inputFileParamName%>_fnFileFocus();"
	       style="<%=i_style%>float:left;padding:0 5px 0 0;cursor:pointer;"
	       onmouseover="this.parentNode.style.backgroundImage='url(<%=i_searchOnImagePath%>)';"
	       onmouseout="this.parentNode.style.backgroundImage='url(<%=i_searchOffImagePath%>)';" />
    </span> 
    <a href="javascript:<%=i_inputFileParamName%>_fnFileReset();" class="board_btn" style="float: left; padding: 0 0px 0 0;">
	<span>초기화</span></a>
    <span id="<%=i_inputFileParamName%>_div_tempFileNm">
    <%=i_sUploadFileHtml.toString()%>
    </span> 
    <span id="<%=i_inputFileParamName%>_div_attach"> 
        <input type="hidden" name="<%=i_inputFileParamName%>_status"    id="<%=i_inputFileParamName%>_status" value="old"> 
        <input type="hidden" name="p_inputFileParamName"                value="<%=i_inputFileParamName%>"> 
        <input type="hidden" name="<%=i_inputFileParamName%>_realfile"  id="<%=i_inputFileParamName%>_realfile"     value="<%=i_realFileName%>">
        <input type="hidden" name="<%=i_inputFileParamName%>_savefile"  id="<%=i_inputFileParamName%>_savefile"     value="<%=i_saveFileName%>">
        <input type="hidden" name="<%=i_inputFileParamName%>_filevalue" id="<%=i_inputFileParamName%>_filevalue"    value=""> 
        <input type="hidden" name="<%=i_inputFileParamName%>_temppath"  id="<%=i_inputFileParamName%>_temppath"     value="">
        <input type="hidden" name="<%=i_inputFileParamName%>_filesize"  id="<%=i_inputFileParamName%>_filesize"     value="<%=i_fileSize %>">  
    </span> 
    
    <IFRAME ID="<%=i_inputFileParamName%>_IFRAME_THUB" NAME="<%=i_inputFileParamName%>_IFRAME_THUB" SRC="about:blank"
	       WIDTH="0" HEIGHT="0" FRAMEBORDER="0" MARGINHEIGHT="0" MARGINWIDTH="0"
	       SCROLLING="NO" style="display: none;" ></IFRAME> 
	
	<input type="hidden" name="<%=i_inputFileParamName%>_servletnm" value = "<%=i_ServletName %>"/> 
	<input type="hidden" name="<%=i_inputFileParamName%>_relativedir" id="<%=i_inputFileParamName%>_relativedir" value="<%=sRelativeDir%>" />

<script language="JavaScript">
<!--

    var <%=i_inputFileParamName%>_initAttachHtml ="";                       

    var <%=i_inputFileParamName%>_frm     = document.<%=i_formName%>;         // 현재 form 네임
    
    var <%=i_inputFileParamName%>_tempDivInnerHtml    = "";                   // 첨부파일 초기화용 Inner HTML            
    var <%=i_inputFileParamName%>_tempUploadPath      = "<%=sRelativeDir%>";  // 임시 업로드 경로
    var <%=i_inputFileParamName%>_tempImgSrc          = "";                   // 첨부파일 초기화용 Img SRC

    
    <%// 타겟 이미지 경로가 있으면 초기화 시 돌아갈 경로를 설정한다.
    if (!i_targetImg.equals("")) {%>
    var <%=i_inputFileParamName%>_img_obj = document.getElementById("<%=i_targetImg%>");  
    <%=i_inputFileParamName%>_tempImgSrc = <%=i_inputFileParamName%>_img_obj.src;            // 변경 전 이미지 src를 임시 파라미터에 저장
    <%}%>

    // 첨부파일 추가
    function <%=i_inputFileParamName%>_fnAddAttach()
    {
        alert("첨부파일 추가. 기능이 없습니다.");return;
    }

    // 파일 업로드중 오류 발생시
    function <%=i_inputFileParamName%>_fnFileUploadError()
    {
        //fnFileReset(att_idx);
        var fileNm_obj      = document.getElementById("<%=i_inputFileParamName%>_div_inputFile");
        var img_obj         = document.getElementById("<%=i_inputFileParamName%>_div_fileName");

        // 로딩중 이미지 복구
        <%=i_inputFileParamName%>_fileNm_obj.style.display   = "block";
        <%=i_inputFileParamName%>_img_obj.style.display      = "none";
        <%=i_inputFileParamName%>_bFileDownYN         = false;
    }

    // 삭제
    function <%=i_inputFileParamName%>_fnFileDel(obj)
    {
        var idx             = <%=i_inputFileParamName%>_getIndex(obj);
        var status_obj      = document.getElementsByName("<%=i_inputFileParamName%>_status");
        
        if ( status_obj[idx].value == "old" )
        {
        	<%=i_inputFileParamName%>_fnDeleteFileAdd(idx);
        }

        <%=i_inputFileParamName%>_fnDelAttach();
    }

    // 첨부파일 삭제
    function <%=i_inputFileParamName%>_fnDelAttach( )
    {
        var div_tempFileNm      = document.getElementById("<%=i_inputFileParamName%>_div_tempFileNm");
        var status_obj          = document.getElementById("<%=i_inputFileParamName%>_status");
        var saveFileName_obj    = document.getElementById("<%=i_inputFileParamName%>_savefile");
        var realFileName_obj    = document.getElementById("<%=i_inputFileParamName%>_realfile");
        var fileValue_obj       = document.getElementById("<%=i_inputFileParamName%>_filevalue");
        var tempPath_obj        = document.getElementById("<%=i_inputFileParamName%>_temppath");
        
        div_tempFileNm.innerHTML = "";
        status_obj.value        = "old";
        saveFileName_obj.value  = "";
        realFileName_obj.value  = "";
        fileValue_obj.value     = "";
        tempPath_obj.value      = "";
        
        <%if (!i_targetImg.equals("")) {%>
        //<%=i_inputFileParamName%>_img_obj.src   = <%=i_inputFileParamName%>_tempImgSrc;
        <%=i_inputFileParamName%>_img_obj.src   = "/images/portal/thumb/thumb_335_200.gif";
        <%}%>
        
    }

    // 첨부파일 초기화
    function <%=i_inputFileParamName%>_fnFileReset()
    {
        var bRtn = confirm("모든 첨부파일 변경사항이 초기화됩니다. 실행하시겠습니까?");
        if(bRtn) {
            document.getElementById("<%=i_inputFileParamName%>_div_attachMain").innerHTML = <%=i_inputFileParamName%>_initAttachHtml;
            <%if (!i_targetImg.equals("")) {%>
            <%=i_inputFileParamName%>_img_obj.src   = <%=i_inputFileParamName%>_tempImgSrc;
            <%}%>
        }

        myFileButton.run();
    }

    // 찾아보기 버튼을 클릭했는지 여부
    var <%=i_inputFileParamName%>_bFileDownYN     = false;
    var <%=i_inputFileParamName%>_att_idx         = 0;

    // 
    var <%=i_inputFileParamName%>_search_obj;

    // 찾아보기 버튼을 클릭한 후 포커스가 오면 업로드를 시킨다
    function <%=i_inputFileParamName%>_fnFileFocus(obj)
    {
        if (!<%=i_inputFileParamName%>_bFileDownYN)
        {
        	<%=i_inputFileParamName%>_bFileDownYN     = true;
        	<%=i_inputFileParamName%>_fnAttachNormal();
        }
        else
        {
            alert("파일을 업로드 하는 중입니다. 잠시 후에 다시 시도하세요.");
            return;
        }
    }


    // 첨부파일 임시폴더에 저장
    function <%=i_inputFileParamName%>_fnAttachNormal()
    {
    	<%=i_inputFileParamName%>_search_obj      = document.getElementById("<%=i_inputFileParamName%>");

        // 파일명 체크시 파일경로는 제외하고 체크한다
        var sCheckFileName  = <%=i_inputFileParamName%>_search_obj.value;
            sCheckFileName  = sCheckFileName.substr(sCheckFileName.lastIndexOf("/") + 1, sCheckFileName.length);

        if (sCheckFileName.indexOf(',') > -1)
        {
            alert("Special character \",\" You can not attach files that contain.");
            return;
        }
        if (sCheckFileName.indexOf('\'') > -1)
        {
            alert ("Special character \"'\" You can not attach files that contain.");
            return;
        }
        // input file의 값이 없으면 false(단 여기서 메세지 박스는 보여주지 않는다(취소버튼을 클릭했을 경우에 해당됨)
        if (<%=i_inputFileParamName%>_frm["<%=i_inputFileParamName%>"].value == "") return;

        // 로딩중 표시
        <%=i_inputFileParamName%>_setImageLoadding();
        
        var oldTarget   = <%=i_inputFileParamName%>_frm.target;

        var param = "?p_servletnm=<%=i_ServletName%>"
                  + "&p_paramnm=<%=i_inputFileParamName%>"
                  + "&p_real_filename="+sCheckFileName
                  + "&p_fileseq=0"
                  + "&p_year=<%=i_year%>"
                  + "&p_subj=<%=i_subj%>"
                  + "&p_inputFileParamName=<%=i_inputFileParamName%>";
        
        <%=i_inputFileParamName%>_frm.action      = "/learn/user/portal/include/singleAttach_save.jsp"+param;
        <%=i_inputFileParamName%>_frm.target      = "<%=i_inputFileParamName%>_IFRAME_THUB";
        <%=i_inputFileParamName%>_frm.encoding    = "multipart/form-data";
        <%=i_inputFileParamName%>_frm.submit();
        
        <%=i_inputFileParamName%>_frm.target      = oldTarget;
    }

    // 파일 업로드 완료후
    function <%=i_inputFileParamName%>_setUploadFile( obj )
    {
        var divAttachObj    = document.getElementById("<%=i_inputFileParamName%>_div_attach");

        var status_obj       = document.getElementById("<%=i_inputFileParamName%>_status");
        var saveFileName_obj  = document.getElementById("<%=i_inputFileParamName%>_savefile");
        var realFileName_obj  = document.getElementById("<%=i_inputFileParamName%>_realfile");
        var fileValue_obj     = document.getElementById("<%=i_inputFileParamName%>_filevalue");
        var tempPath_obj      = document.getElementById("<%=i_inputFileParamName%>_temppath");
        var fileSize_obj      = document.getElementById("<%=i_inputFileParamName%>_filesize");

        if(status_obj.value != null && status_obj.value == "old" && saveFileName_obj.value != ""){
        	<%=i_inputFileParamName%>_fnDeleteFileAdd();
        }

        // 로딩중 이미지 복구
        <%if (!i_targetImg.equals("")) {%>
        
        <%=i_inputFileParamName%>_img_obj.src   = obj.sWebUploadTempPath+obj.sUploadNm;

        <%}%>
        
        <%if (i_targetImg.equals("")) {%>
        var divTempFileNm       = document.getElementById("<%=i_inputFileParamName%>_div_tempFileNm");
        
        divTempFileNm.innerHTML  = "&nbsp;&nbsp;"; 
        divTempFileNm.innerHTML += "<img src=\"/images/portal/ico/ico_file.gif\" alt=\"file\" class=\"mg_r6\" />";
        divTempFileNm.innerHTML += "<a href = \"/servlet/controller.library.DownloadServlet?p_savefile="+obj.sWebUploadTempPath+obj.sUploadNm+"&p_realfile="+obj.sRealFileName+"\">"+obj.sRealFileName+" ("+numberFormatComma(obj.nFileSize)+" Bytes)</a>";
        divTempFileNm.innerHTML += "<span id=\"span_del\" name=\"span_del\" onclick=\"<%=i_inputFileParamName%>_fnFileDel(this)\"> <img src=\"/images/portal/btn/btn_s_close.gif\" alt=\"x\" style='cursor:pointer;' align='absmiddle'/></span>";
        <%} %>
        
        status_obj.value           = "new";
        saveFileName_obj.value     = obj.sUploadNm;
        realFileName_obj.value     = obj.sRealFileName;
        fileValue_obj.value        = obj.value;
        tempPath_obj.value         = obj.sUploadTempPath+obj.sUploadNm;
        fileSize_obj.value         = obj.nFileSize;
        
        <%=i_inputFileParamName%>_bFileDownYN  = false;

        // Input file 초기화
        var divInputFile = document.getElementById("<%=i_inputFileParamName%>_div_inputFile");
        var fileObj = document.getElementById("<%=i_inputFileParamName%>");
        fileObj.select();
        document.execCommand('Delete', false, null);
        divInputFile.innerHTML = divInputFile.innerHTML;
    }

    // 삭제파일 목록 추가
    function <%=i_inputFileParamName%>_fnDeleteFileAdd()
    {
        var divAttach           = document.getElementById("<%=i_inputFileParamName%>_div_attachMain");
        var saveFileName_obj    = document.getElementById("<%=i_inputFileParamName%>_savefile");
        var saveFileName        = saveFileName_obj.value;

        var oInput  = document.createElement("<input type='hidden' name='p_del_savefile' value='" + saveFileName + "'>");

        divAttach.insertAdjacentElement("afterBegin", oInput);
    }

    
    // 이미지 셋팅
    function <%=i_inputFileParamName%>_setImageLoadding()
    {
        <%if (!i_targetImg.equals("")) {%>

        <%=i_inputFileParamName%>_img_obj.src   = "/images/portal/common/ajax-loader1.gif";
        <%}%>
        <%if (i_targetImg.equals("")) {%>
        var divTempFileNm       = document.getElementById("<%=i_inputFileParamName%>_div_tempFileNm");
        divTempFileNm.innerHTML = "<img src='/images/portal/common/ajax-loader.gif' height='10px' width='30px'/>";
        <%}%>

    }

 // index 값 가져오기
    function <%=i_inputFileParamName%>_getIndex(obj)
    {
        var idx     = -1;
        var obj_arr = document.getElementsByName(obj.id);
        var len     = obj_arr.length;
        
        for (var i = 0; i < len ; i++ )
        {
            if (obj == obj_arr[i])
            {
                idx     = i;
                break;
            }
        }
        return idx;
    }

//-->
</script></div>
<script type="text/javascript" src="/script/portal/FileButton.js"></script>
<script language="JavaScript">
// 초기화 에 필요한 Input file 관련 innerHtml 을 백업함
<%=i_inputFileParamName%>_initAttachHtml = document.getElementById("<%=i_inputFileParamName%>_div_attachMain").innerHTML;

// 모든 찾아보기 버튼을 이미지로 변환
var myFileButton = new FileButton("imageswap", "imagesrc");
window.onload = function () {

    myFileButton.run(); //문서로딩 후 한꺼번에 바꿈

}
</script>
