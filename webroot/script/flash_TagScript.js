//<!--메뉴-->
function jsFlash(file_path,width,height,menu,sub,sn,sx){
	document.write("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0\" width=\""+width+"\" height=\""+height+"\">");
    document.write("	<param name=\"movie\" value=\""+file_path+"\">");
    document.write("	<param name=\"quality\" value=\"high\">");
	document.write("	<param name=\"wmode\" value=\"transparent\">");
	document.write("	<param name=\"FlashVars\" value=\"menu="+menu+"&sub="+sub+"&sn="+sn+"&sidx="+sx+" \">");
    document.write("	<embed src=\""+file_path+"\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" width=\""+width+"\" height=\""+height+"\">");
    document.write("	</embed>");
  	document.write("</object>");

}