<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<script type="text/javascript" src="/namo_cross/js/namo_scripteditor.js"></script> 
<script type="text/javascript">
function getContent() {
	var contentObj = document.getElementById("p_content"); 
	contentObj.value = CrossEditor.GetBodyValue();
	return contentObj;
}
</script>
</head>
<body leftmargin="0" marginwidth="0" topmargin="0" marginheight="0" >
<table  width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td id="TBL_IFRAME" style="height:470px;">                   
                    <textarea  id="p_content" name="p_content">dddddddddd</textarea>
                    <script type="text/javascript">
                      var CrossEditor = new NamoSE("p_content");
                       CrossEditor.editorStart();
                    </script>
</td></tr>
</table>
</body>
<Script Language="JavaScript">
   document.getElementById("TBL_IFRAME").style.width = parent.document.getElementById("EDITOR_IFRAME").clientWidth;
   parent.document.getElementById("EDITOR_IFRAME").style.height = document.getElementById("TBL_IFRAME").style.height;

</Script>
</html>