<%@ page import = "com.credu.library.*" %>                                  
<%                                                                            
    RequestBox box = (RequestBox)request.getAttribute("requestbox");        
    if (box == null) box = RequestManager.getBox(request);                    
    box.setSession("tem_type","GA");                                
    box.setSession("tem_grcode","N000007");                            
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
