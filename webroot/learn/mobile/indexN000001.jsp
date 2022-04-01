<%@ page import = "com.credu.library.*" %>                                  
<%                                                                            
    RequestBox box = (RequestBox)request.getAttribute("requestbox");        
    if (box == null) box = RequestManager.getBox(request);                    
    box.setSession("tem_type","KA");                                
    box.setSession("tem_grcode","N000001");                            
%>                                                                            
<html>                                                                        
<head>                                                                        
<Script Language='JavaScript'>                                                
<!--                                                                          
    //document.location.replace("/servlet/controller.homepage.MainServlet");  
	document.location.replace("/servlet/controller.mobile.main.MainServlet");  
//-->                                                                         
</Script>                                                                     
</head>                                                                       
<body>                                                                        
</body>                                                                       
</html>                                                                       
 