function fl_view(flashurl, w, h) {
	document.write("<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://active.macromedia.com/flash9/cabs/swflash.cab#version=9,0,0,0' width='"+w+"' height='"+h+"'>"); 
	document.write("<param name='movie' value='"+flashurl+"'>"); 
	document.write("<param name='wmode' value='transparent'>"); 
	document.write("<param name='play' value='true'>"); 
	document.write("<param name='loop' value='true'>"); 
	document.write("<param name='quality' value='high'>"); 
	document.write("<embed src='"+flashurl+"' play='true' loop='true' quality='high' wmode='transparent' pluginspage='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' width='"+w+"' height='"+h+"'>"); 
	document.write("</embed>"); 
	document.write("</object>"); 
}

function popview(urls,w,h){
	sw = (screen.width);
	sh = (screen.height);
	size = "width="+w+",height="+h+",scrollbars=yes";
	var pop_view = window.open(urls,'',size);
	pop_view.focus();
}

//Áö½ÄÆÑÅä¸® ÅÇ¸Þ´º
function postchg(obj) {
	if (obj == '1') {
		document.getElementById('tab01').src = 'images/factory/tab01over.gif';
		document.getElementById('tab02').src = 'images/factory/tab02.gif';
		document.getElementById('tab03').src = 'images/factory/tab03.gif';
		document.getElementById('tab04').src = 'images/factory/tab04.gif';
		document.getElementById('tab05').src = 'images/factory/tab05.gif';
		//document.getElementById('courseview1').style.display = 'block';
	}
	else if (obj == '2') {
		document.getElementById('tab01').src = 'images/factory/tab01.gif';
		document.getElementById('tab02').src = 'images/factory/tab02over.gif';
		document.getElementById('tab03').src = 'images/factory/tab03.gif';
		document.getElementById('tab04').src = 'images/factory/tab04.gif';
		document.getElementById('tab05').src = 'images/factory/tab05.gif';
	}
	else if (obj == '3') {
		document.getElementById('tab01').src = 'images/factory/tab01.gif';
		document.getElementById('tab02').src = 'images/factory/tab02.gif';
		document.getElementById('tab03').src = 'images/factory/tab03over.gif';
		document.getElementById('tab04').src = 'images/factory/tab04.gif';
		document.getElementById('tab05').src = 'images/factory/tab05.gif';
	}
	else if (obj == '4') {
		document.getElementById('tab01').src = 'images/factory/tab01.gif';
		document.getElementById('tab02').src = 'images/factory/tab02.gif';
		document.getElementById('tab03').src = 'images/factory/tab03.gif';
		document.getElementById('tab04').src = 'images/factory/tab04over.gif';
		document.getElementById('tab05').src = 'images/factory/tab05.gif';
	}
	else if (obj == '5') {
		document.getElementById('tab01').src = 'images/factory/tab01.gif';
		document.getElementById('tab02').src = 'images/factory/tab02.gif';
		document.getElementById('tab03').src = 'images/factory/tab03.gif';
		document.getElementById('tab04').src = 'images/factory/tab04.gif';
		document.getElementById('tab05').src = 'images/factory/tab05over.gif';
	}
}