	var dragswitch=0
	var nsx
	var nsy
	var nstemp

	function drag_dropns(name){
		temp=eval(name)
		temp.captureEvents(Event.MOUSEDOWN | Event.MOUSEUP)
		temp.onmousedown=gons
		temp.onmousemove=dragns
		temp.onmouseup=stopns
	}

	function gons(e){
		temp.captureEvents(Event.MOUSEMOVE)
		nsx=e.x
		nsy=e.y
	}
	function dragns(e){
		if (dragswitch==1){
			temp.moveBy(e.x-nsx,e.y-nsy)
			return false
		}
	}

	function stopns(){
		temp.releaseEvents(Event.MOUSEMOVE)
	}

	//drag drop function for IE 4+////
	/////////////////////////////////

	var dragapproved=false

	function drag_dropie(){
		if (dragapproved==true){
			document.all.Div_WinSearch.style.pixelLeft=tempx+event.clientX-iex
			document.all.Div_WinSearch.style.pixelTop=tempy+event.clientY-iey
			//alert(document.all.Div_WinSearch.style.pixelTop);
			//document.all.Div_WinSearch.style.pixelLeft=tempx+event.clientX-iex
			//document.all.Div_WinSearch.style.pixelTop=tempy+event.clientY-iey
			return false
		}
	}

	function initializedragie(){
		iex=event.clientX
		iey=event.clientY
		tempx=Div_WinSearch.style.pixelLeft
		tempy=Div_WinSearch.style.pixelTop
		dragapproved=true
		document.onmousemove=drag_dropie
	}

	if (document.all){
		document.onmouseup=new Function("dragapproved=false")
	}
	function WinSearch_Open(root_path, field_name){
  		Div_WinSearch.style.left = 50;
  		Div_WinSearch.style.top = 50;
		Div_WinSearch.style.visibility = "visible";
		//Div_WinSearch.style.display='';
		//alert("/learn/admin/contents/za_SCO_DirFile_List.jsp?ROOT_PATH="+root_path+"&Return_FieldName="+field_name);
		document.Frame_WinSearch.location.href = "/learn/admin/contents/za_SCO_DirFile_List.jsp?ROOT_PATH="+root_path+"&Return_FieldName="+field_name;
	}
	function WinSearch_Close(){
		//Div_WinSearch.style.display = 'none';
		Div_WinSearch.style.visibility = "hidden";
	}

/**
 * 입력값의 바이트 길이를 리턴
 * Author : Wonyoung Lee
 */
function getByteLength(input) {
    var byteLength = 0;
    for (var inx = 0; inx < input.value.length; inx++) {
        var oneChar = escape(input.value.charAt(inx));
        if ( oneChar.length == 1 ) {
            byteLength ++;
        } else if (oneChar.indexOf("%u") != -1) {
            byteLength += 2;
        } else if (oneChar.indexOf("%") != -1) {
            byteLength += oneChar.length/3;
        }
    }
    return byteLength;
}

/**
 * 입력값의 바이트 길이를 리턴
 * Author : Wonyoung Lee
 */
function getByteValue(input) {
    var byteLength = 0;
    for (var inx = 0; inx < input.length; inx++) {
        var oneChar = escape(input.charAt(inx));
        if ( oneChar.length == 1 ) {
            byteLength ++;
        } else if (oneChar.indexOf("%u") != -1) {
            byteLength += 2;
        } else if (oneChar.indexOf("%") != -1) {
            byteLength += oneChar.length/3;
        }
    }
    return byteLength;
}