<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
         <title>JQGrid Special</title>                  
       
    	 <script src="/js/assets/gridJs/jquery.jqGrid.min.js" type="text/javascript"></script>  
         <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-4.4.1/js/jquery.jqGrid.src.js"></script>
         <link href="/js/assets/gridJs/ui.jqgrid.css" rel="stylesheet" type="text/css" /> 
         <script src="/js/assets/gridJs/grid.locale-en.js"  type="text/javascript"></script> 
         <script type="text/javascript" src="/js/assets/gridJs/contents.js"  charset="utf-8"></script> 
</head>
<body>
     <div class="gridJ" style="width:1000px"><table id="gridList" class="scroll" ></table></div>
     <div id="pager"></div> 
<%@ include file = "/learn/library/getJspName.jsp" %>    
</body>
</html>