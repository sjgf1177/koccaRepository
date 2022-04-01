<%@ page import = "com.credu.library.*" %>                                  
<%                                                                             
    RequestBox box = (RequestBox)request.getAttribute("requestbox");        
    if (box == null) box = RequestManager.getBox(request);   
    if(!request.getServerName().equals("game.connect.or.kr"))
    {
	    box.setSession("tem_type","KA");                                
	    box.setSession("tem_grcode","N000001");
    }
    else
    {
	    box.setSession("tem_type","GA");                                
	    box.setSession("tem_grcode","N000002");
    }
   
%>                                                                            
<html>                                                                        
<head>                                                                        
<Script Language='JavaScript'>                                                
<!--                                                                          
    document.location.replace("/servlet/controller.homepage.MainServlet");  
//-->                                                                         
</Script>                                                                     
</head>                                                                       
<body>                                                                        
</body>                                                                       
</html>                    