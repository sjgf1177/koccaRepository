<%@ page import = "com.credu.library.*" %>                                  
<%                                                                            
    RequestBox box = (RequestBox)request.getAttribute("requestbox");        
    if (box == null) box = RequestManager.getBox(request);                    
    box.setSession("tem_type","GA");                                
    box.setSession("tem_grcode","N000002");                            
   
%>                                                                            
<html>                                                                        
<head>                                                                        


<!--script language=javascript>
    function setCookie (name,value,path,domain,secure) {
       var expires = new Date();
       expires.setMonth(expires.getMonth() + 1)

      document.cookie = name + "=" + escape (value) +				//ƒÌ≈∞¿˙¿Â
        ((expires) ? "; expires=" + expires.toGMTString() : "") +
        ((path) ? "; path=" + path : "") +
        ((domain) ? "; domain=" + domain : "") +
        ((secure) ? "; secure" : "");
    }

    setCookie("GAME_POPUP", "http://game.connect.or.kr/learn/user/contents/z_EduStart_popup.jsp?p_url=", "", "", null);
    setCookie("GAME_GO", "http://game.connect.or.kr/learn/user/contents/z_EduStart_go.jsp", "", "", null);
    setCookie("GAME_CLOSE", "http://game.connect.or.kr/learn/user/contents/z_EduStart_close.jsp", "", "", null);
    setCookie("GAME_MAIN", "http://game.connect.or.kr/learn/user/contents/z_EduStart_main.jsp", "", "", null);

	</script-->


<Script Language='JavaScript'>                                                
<!--                                                                          
    document.location.replace("/servlet/controller.homepage.MainServlet");  
//-->                                                                         
</Script>                                                                     
</head>                                                                       
<body>                                                                        
</body>                                                                       
</html>                                                                       
