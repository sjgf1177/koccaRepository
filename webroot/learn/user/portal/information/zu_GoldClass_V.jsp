<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.dunet.common.util.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String v_vodurl = "";   // 내용
    String v_width_s = "";   // 창넓이
    String v_height_s = "";   // 창높이
    String v_vod_path = "";

    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
        v_vodurl = dbox.getString("d_vodurl");
        v_width_s = dbox.getString("d_width_s");
        v_height_s = dbox.getString("d_height_s");
        v_vod_path = dbox.getString("d_vod_path");
    }

    String v_server = conf.getProperty("kocca.url.value");

    v_server = v_server.replace("http://", "mms://");
        
    if(v_vodurl.endsWith("mp4")){
//     	v_vodurl = v_vodurl.replaceAll("/contents/openclass/mp4/", "http://vod.kbrainc.com/kbrain/kocca/mobile/openclass/");
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<title>학습창</title>
<script type="text/javascript" src="/js/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="/js/jqueryui/1.9.2/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="Content-Language" content="euc-kr">
<meta http-equiv="imagetoolbar" content="false">
<meta name="robots" content="all">
<meta name="MSSmartTagsPreventParsing" content="true">
<meta name="description" content="">
<link href="//vjs.zencdn.net/5.0.2/video-js.css" rel="stylesheet" >
<style>
    html { padding:0px; margin:0px; }
    body { height:100%; width:100%; margin:0px; padding:0px; background-color: black;}
    iframe@ebody .body { margin:0; padding:0; }
    iframe { margin:0px; padding:0px; vertical-align:bottom; }
        
    .video-js .vjs-big-play-button {top : calc(100% - <%=(Integer.parseInt(v_height_s) / 2) + 20 %>px);left : calc(100% - <%=(Integer.parseInt(v_width_s) / 2) + 43 %>px);}
</style>

<script type="text/javascript">
	window.resizeTo(<%= v_width_s %>, <%= v_height_s %> + 88);
    
    // 크레듀 제휴과정 링크정보 가져오기
    function creduURL(){
    	$.ajax({
	            type : "post"
	        ,   url : "/servlet/controller.contents.EduStart"
	        ,   dataType : "json"
	        ,   data : {
	        	url : "<%=v_vod_path%>",
	        	p_process : "creduSubj"
	        }
	        ,   success : function(ajaxData) {
	        		ebody.loadswf(ajaxData.videoURL, 910, 540);
	        	}
	        ,   error :  function(arg1, arg2) {
	                alert("오류가 발생하여 플레이 할 수 없습니다.");
	            }
	    });
    }
    
    $(document).ready(function() {
        $(document).bind("contextmenu", function(e){
            return false;
        });
    }); 
     
    document.oncontextmenu=function(){return false;} // 우클릭 방지
    document.onselectstart=function(){return false;} // 드래그 방지
    document.ondragstart=function(){return false;} // 선택 방지
    document.onmousedown=function(){return false;}    
</script>
</head>
<body>
<% if(v_vodurl.endsWith("mp4")){ %>
	<script src="//vjs.zencdn.net/5.0.2/video.js" type="text/javascript"></script>
    <video id="video" class="video-js vjs-default-skin" autoplay controls preload="none" width="<%=Integer.parseInt(v_width_s)-18 %>" height="<%=Integer.parseInt(v_height_s)%>" data-setup="1">
    	<source src="<%=v_vodurl %>" type="video/mp4" >
    	<embed src="<%=v_vodurl %>" autoplay="true" width="<%=Integer.parseInt(v_width_s)-10 %>" height="<%=Integer.parseInt(v_height_s)%>" />
    </video>
<% }else{ %>
    <iframe src="<%=v_vodurl%>" name="ebody" style="width:<%=v_width_s%>px; height:<%=Integer.parseInt(v_height_s)+88%>px;" scrolling="no" frameborder="NO" border="0" framespacing="0" allowfullscreen="true"></iframe>
<% } %>
</body>
</html>