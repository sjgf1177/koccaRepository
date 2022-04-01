var param1;
var param2;
var param3;
var param4;
var param5;
var param6;
var param7;

function setParam1(p_param){param1 = p_param;}
function setParam2(p_param){param2 = p_param;}
function setParam3(p_param){param3 = p_param;}
function setParam4(p_param){param4 = p_param;}
function setParam5(p_param){param5 = p_param;}
function setParam6(p_param){param6 = p_param;}
function setParam7(p_param){param7 = p_param;}

function object_patch(p_gubun){
	var cont;
  if(p_gubun == "leftmenuA"){
    cont = '<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="232" height="450">'
	  +'<param name="movie" value="'+param1+'">'
	  +'<param name="quality" value="high">'
	  +'<embed src="/image/common/flash/menu_sub5.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="232" height="450">'
	  +'</embed> </object>';
  }else if(p_gubun == "leftmenuC"){
    cont = '<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="232" height="450">'
	  +'<param name="movie" value="'+param1+'">'
	  +'<param name="quality" value="high">'
	  +'<embed src="'+param1+'" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="232" height="450">'
	  +'</embed> </object>';
	  
  }else if(p_gubun == "leftmenuB"){
    cont = '<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"'
		+' codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="'+param2+'"'
		+' height="'+param3+'">'
		+' <param name="movie" value="'+param1+'">'
		+' <param name="quality" value="high">'
		+' <param name=menu value=false>'
		+' <param name=wmode value=transparent>'
		+' <embed src="'+param1+'" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="'+param2+'" height="'+param3+'"></embed>'
		+' </OBJECT>'
  }else if(p_gubun == "leftmenuD"){
    cont = '<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"'
		+' codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="'+param2+'"'
		+' height="'+param3+'">'
		+' <param name="movie" value="'+param1+'">'
		+' <param name="quality" value="high">'
		+' <param name=menu value=false>'
		+' <param name=wmode value=transparent>'
		+' <embed src="'+param4+'" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="'+param2+'" height="'+param3+'"></embed>'
		+' </OBJECT>'
  }else if(p_gubun == "menuapplet_start"){
    cont = '<applet code="MenuDemo" codebase="../" align="baseline"  width="100%" height="27" name="MenuDemo"  MAYSCRIPT>'
  }else if(p_gubun == "obc_menuapplet"){
    cont = '<applet NAME="ICARUS" CODEBASE="/myclasses/OBC" CODE="creTreeApplet.class" WIDTH=170 HEIGHT=480 MAYSCRIPT>'
  }
  else if(p_gubun == "obc_menuapplet2"){
    cont = '<applet NAME="ICARUS" CODEBASE="/myclasses/OBC" CODE="creTreeApplet.class" WIDTH="' + param1 +'" HEIGHT="' + param2 + '" MAYSCRIPT>'
  }else if(p_gubun == "sco_insert"){
  cont  ='<OBJECT classid = "clsid:CAFEEFAC-0014-0001-0001-ABCDEFFEDCBA" codebase = "http://java.sun.com/products/plugin/autodl/jinstall-1_4_1_01-windows-i586.cab#Version=1,4,1,01" WIDTH = "100%" HEIGHT = "180" NAME = "manifestTable" ALIGN = "middle" VSPACE = "0" HSPACE = "0" >'
  	+'<PARAM NAME = CODE VALUE = "JTableApplet.class" >'
  	+'<PARAM NAME = CODEBASE VALUE = "/learn/admin/contents/applet" >'
  	+'<PARAM NAME = NAME VALUE = "manifestTable" >'
  	+'<PARAM NAME = MAYSCRIPT VALUE = "true" >'
  	+'<PARAM NAME = "type" VALUE = "application/x-java-applet;jpi-version=1.4.1_01">'
  	+'<PARAM NAME = "scriptable" VALUE = "false">'
  	+'<PARAM NAME = "filename" VALUE ="'+param1+'">'
  	+'<PARAM NAME = "course_code" VALUE ="'+param2+'">'
  	+'<COMMENT>'
  	+'<EMBED '
  	+'		type = "application/x-java-applet;jpi-version=1.4.1_01" '
  	+'		CODE = "JTableApplet.class"'
  	+'		JAVA_CODEBASE = "/learn/admin/contents/applet"'
  	+'		NAME = "manifestTable"'
  	+'		WIDTH = "100%"'
  	+'		HEIGHT = "180"'
  	+'		ALIGN = "middle"'
  	+'		VSPACE = "0"'
  	+'		HSPACE = "0"'
  	+'		MAYSCRIPT = "true" '
  	+'		filename ="'+param3+'"'
  	+'		course_code ="'+param4+'" '
  	+'		scriptable = false '
  	+'		pluginspage = "http://java.sun.com/products/plugin/index.html#download">'
  	+'		<NOEMBED>'
  	+'		</NOEMBED>'
  	+'</EMBED>'
  	+'</COMMENT>'
        +'</OBJECT>'
  }else if(p_gubun == "namo_editor"){
    cont = '<OBJECT WIDTH=0 HEIGHT=0 CLASSID="clsid:5220cb21-c88d-11cf-b347-00aa00a28331">'
          +'   <PARAM NAME="LPKPath" VALUE="/namo/NamoWec5_Bobs_Consulting.lpk">'
          +'</OBJECT>'
          +'<OBJECT ID="Wec" WIDTH="'+param1+'" HEIGHT="'+param2+'"'
          +'CLASSID="CLSID:DB1A8D98-97E8-4a42-9624-0E4BCD77F109" CODEBASE="/namo/NamoWec.cab#version=5,0,0,69">'
          +'<param name="InitFileURL" value="/namo/namowec.env">'
          +'<param name="InitFileVer" value="1.1">'
          +'</OBJECT>'
  }
  //alert(cont);
  document.write(cont); 
}

function object_namopatch(width, height){
   var cont;
    cont = '<OBJECT WIDTH=0 HEIGHT=0 CLASSID="clsid:5220cb21-c88d-11cf-b347-00aa00a28331">'
          +'   <PARAM NAME="LPKPath" VALUE="/namo/NamoWec5_Bobs_Consulting.lpk">'
          +'</OBJECT>'
          +'<OBJECT ID="Wec" WIDTH="'+width+'" HEIGHT="'+height+'"'
          +'CLASSID="CLSID:DB1A8D98-97E8-4a42-9624-0E4BCD77F109" CODEBASE="/namo/NamoWec.cab#version=5,0,0,69">'
          +'<param name="InitFileURL" value="/namo/namowec.env">'
          +'<param name="InitFileVer" value="1.1">'
          +'</OBJECT>'
  document.write(cont); 
}

function object_ozpatch(ip, port1, port2, gubun){
   var cont;
   //alert("ccccccc");
   if(gubun == "test"){
     cont ='<OBJECT width = "0" height = "0"'
     +'ID="ZTransferX" CLASSID="CLSID:C7C7225A-9476-47AC-B0B0-FF3B79D55E67" '
     +'codebase="http://'+ip+':'+port1+'/OZViewer/ZTransferX.cab#version=1,0,3,2">'
     +'<PARAM NAME="download.Server" VALUE="http://'+ip+'/OZViewer/">'
     +'<PARAM NAME="download.Port" VALUE="'+port1+'">'
     +'<PARAM NAME="download.Instruction" VALUE="ozrviewer.idf">'
     +'<PARAM NAME="install.Base" VALUE="<PROGRAMS>/Forcs">'
     +'<PARAM NAME="install.Namespace" VALUE="AutoEver_Test">'
     +'</OBJECT>'
     +'<OBJECT id = "ozviewer"'
     +'   CLASSID="CLSID:64DA633F-E73B-4344-83BF-48483346CD53" width="900" height="665">'
     +'   <param name="connection.server" value="'+ip+'">'
     +'   <param name="connection.port" value="'+port2+'">'
   }else{
   //alert("1111");
     cont = '<OBJECT width = "0" height = "0"'
     +'ID="ZTransferX" CLASSID="CLSID:C7C7225A-9476-47AC-B0B0-FF3B79D55E67" '
     +'codebase="http://'+ip+'/oz30/OZViewer/ZTransferX.cab#version=1,0,3,2">'
     +'<PARAM NAME="download.Server" VALUE="http://'+ip+'/oz30/OZViewer/">'
     +'<PARAM NAME="download.Port" VALUE="'+port1+'">'
     +'<PARAM NAME="download.Instruction" VALUE="ozrviewer.idf">'
     +'<PARAM NAME="install.Base" VALUE="<PROGRAMS>/Forcs">'
     +'<PARAM NAME="install.Namespace" VALUE="autoeverlms">'
     +'</OBJECT>'
     +'<OBJECT id = "ozviewer"'
     +'   CLASSID="CLSID:64DA633F-E73B-4344-83BF-48483346CD53" width="900" height="665">'
     +'	<param name="connection.servlet" value="http://'+ip+'/oz30/server">'
     //alert("2222");
   }
   //alert(cont);
   
   document.write(cont);
}    

function object_ScriptX()
{
	var cont;
	 cont = '<object id=factory classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="/script/ScriptX.cab#Version=6,1,429,14"> ' 
	 + '</object>';
   document.write(cont);
}

function object_ETNG_FTP(ip, id, pw, data)
{
	var cont;
	 cont = '<OBJECT ID="ETNG_FTP" CLASSID="CLSID:526A654F-760C-49DD-B971-5517ECB98B12"'
		+' CODEBASE="/myclasses/ETNG_FTP.CAB#version=1,0,0,2" width="52" height="22">'
		+' <param name="FtpAddr"	value="' + ip + '">'
		+' <param name="UserID"		value="' + id + '">'
		+' <param name="UserPwd"	value="' + pw + '">'
		+' <param name="MainDir"    value="./contents/scobject/' + data + '">'
		+' </OBJECT>';
   document.write(cont);
}

function object_ALEX_FTP(ip, id, pw, data)
{
	var cont;
	 cont = '<OBJECT ID="ALEX_FTP" CLASSID="CLSID:796DEC5E-5851-4EE8-91D3-72E69BD270BA"'
		+' CODEBASE="/myclasses/ALEX_FTP.CAB#version=1,0,0,3" width="52" height="22">'
		+' <param name="FtpAddr"	value="' + ip + '">'
		+' <param name="UserID"		value="' + id + '">'
		+' <param name="UserPwd"	value="' + pw + '">'
		+' <param name="MainDir"    value="./contents/scobject/' + data + '">'
		+' </OBJECT>';
   document.write(cont);
}

function object_embed(oSrc, oWidth, oHeight)
{
	var cont;
	 cont = '<embed src="' + oSrc + '" width="' + oWidth + '" height="' + oHeight + '"></embed>'
	
   document.write(cont);
}

function object_applet()
{
	var cont;
	 cont = '<applet code="MenuDemo" codebase="../" align="baseline"  width="100%" height="27" name="MenuDemo"  MAYSCRIPT>'

	 document.write(cont);
}

function object_sco(filename, code)
{
	var cont;
	 cont = '<APPLET  WIDTH = "100%" HEIGHT = "180" NAME = "manifestTable" ALIGN = "middle" VSPACE = "0" HSPACE = "0" >'
		+'	<PARAM NAME = CODE VALUE = "JTableApplet.class" >'
		+'	<PARAM NAME = CODEBASE VALUE = "/learn/admin/contents/applet" >'
		+'	<PARAM NAME = CODEBASE VALUE = "/learn/admin/" >'
		+'	<PARAM NAME = NAME VALUE = "manifestTable" >'
		+'	<PARAM NAME = MAYSCRIPT VALUE = "true" >'
		+'	<PARAM NAME = "type" VALUE = "application/x-java-applet;jpi-version=1.4.1_01">'
		+'	<PARAM NAME = "scriptable" VALUE = "false">'
		+'	<PARAM NAME = "filename" VALUE ="' + filename + '">'
		+'	<PARAM NAME = "course_code" VALUE ="' + code + '">'
		+'	</APPLET>'
	 document.write(cont);
}