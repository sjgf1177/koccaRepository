var is_init = false;
var has_focus = false;
var close_id = null;
var selectionObj = null;

var default_source = "<HTML>\n<HEAD>\n" 
+ "<style> body { color: #0f0f0f; font-family: 굴림; font-size: 9pt; font-weight: normal; } table { color: #0f0f0f; font-family: 굴림; font-size: 9pt; font-weight: normal; } "
+" \n " 
+ " P {margin:0}\n" 
+ "</STYLE></HEAD><BODY>\n\n"; 


function deactivate_handler()
{
	if( is_init )
		selectionObj.PutSelection();
}

function CSelection()
{
	this.m_selection = null;
	this.GetSelection = get_selection;
	this.PutSelection = put_selection;
}

function get_selection()
{
	if ( this.m_selection != null )
	{ 
		if( this.m_selection.type != "Control" )
		{
			if( ObjEditor.document.body.createTextRange().inRange( this.m_selection ) == true )
				this.m_selection.select();
		}
		else
			this.m_selection.select();
	}
}

function put_selection()
{
	this.m_selection = ObjEditor.document.selection.createRange();
	this.m_selection.type = ObjEditor.document.selection.type;
}


function OnDocumentComplete()
{
	if (ObjEditor.document.readyState != "complete" )
		return true;
	
	if( is_init == false )
	{
		is_init = true;
		//document.all.txtDetail.value = document.all.content.value;
		//document.all.content.value = "";

		ObjEditor.document.designMode="On";
		selectionObj = new CSelection();

        //에디터 이용==================================================================================
        if( document.all.txtDetail.value == "" )

        var src = "";
        if( document.all.edit_div.innerHTML.search( /<html>/gi ) == -1 ) {
            src = default_source.substring(0,220) + ContentsDecode(document.all.edit_div.innerHTML) + "</BODY>\n</HTML>";
        }else {

            src = ContentsDecode(document.all.edit_div.innerHTML);
        }


        ObjEditor.document.open("text/html");
		
		ObjEditor.document.write( src );
        ObjEditor.document.close();
        ObjEditor.document.oncontextmenu = new Function("return false;");

		ObjEditor.focus();
    
    
    }
	return true;
}


function changeEditor() {
    if(document.all.use_editor[0].checked) {

        ObjEditor.document.open("text/html");
		ObjEditor.document.write(document.all.txtDetail.value);
		ObjEditor.document.close();
		ObjEditor.document.oncontextmenu = new Function("return false;");

		document.all.ObjEditor.style.display = "block";
		document.all.toolbar.style.display = "block";
		document.all.txtDetail.style.display = "none";
		selectionObj.m_selection = null;
		document.all.ObjEditor.focus();

    } else {
		document.all.txtDetail.value = ObjEditor.document.all.tags("html")[0].outerHTML;
		document.all.ObjEditor.style.display = "none";
		document.all.toolbar.style.display = "none";
		document.all.txtDetail.style.display = "block";
		document.all.txtDetail.focus();
    }
}
//메뉴버튼처리-----------------------------
function buttonover(td)
{	
//	dynOpacity(globopObject,false);	
	td.borderColorDark = "white";
	td.borderColorLight = "gray";
	td.style.cursor = "hand";
}

function buttonout(td)
{
//	event.srcElement.style.filter='alpha(opacity:' + opIndex + ')';
//	dynOpacity(event.srcElement,true);	
	td.borderColorDark = "#EBEBEB";
	td.borderColorLight = "#EBEBEB";
	td.style.cursor = "auto";
}

function buttondown(td)
{
//	event.srcElement.style.filter='invert()';
//	dynOpacity(event.srcElement,true);	
	td.bgcolor = "gray";
	style="filter:invert();" 
}
//--------------------------------------------


function command(obj)
{
	hide_layer();
    
    if( selectionObj.m_selection != null )
		selectionObj.GetSelection();

	//SELECT BOX 메뉴
    if( obj.tagName == "SELECT" )
	{
		if( obj.id == "font" )
		{
			if( obj.options[obj.selectedIndex].text != "Times" )
			{
				ObjEditor.document.execCommand("FontName", null, obj.options[obj.selectedIndex].value);
			}
			else if( obj.options[obj.selectedIndex].text == "Times" )
			{
				ObjEditor.document.execCommand("FontName", null, "Times New Roman");
			}
			
			obj.options[obj.selectedIndex].selected = true;
		} 
		else if( obj.id == "fontsize" )
		{
			ObjEditor.document.execCommand("FontSize", null, obj.options[obj.selectedIndex].value);
			
			obj.options[obj.selectedIndex].selected = true;
		}
    }
	else 
	{
		if( obj.id == "bold" )
		{
			ObjEditor.document.execCommand("Bold");
		}
		else if( obj.id == "italic" )
		{
			ObjEditor.document.execCommand("Italic");
		}
		else if( obj.id == "underline" )
		{
			ObjEditor.document.execCommand("Underline");
		}
		else if( obj.id == "fontcolor")
		{
            view_table(obj.id);
		}
		else if( obj.id == "fontbgcolor" )
		{
            view_table(obj.id);
		}
		else if( obj.id == "tablebgcolor" )
		{
            view_table(obj.id);
		}
		else if( obj.id == "bgcolor" )
		{
            view_table(obj.id);
        }
		else if( obj.id == "left" )
		{
			ObjEditor.document.execCommand("JustifyLeft");
		}
		else if( obj.id == "center" )
		{
			ObjEditor.document.execCommand("JustifyCenter");
		}
		else if( obj.id == "right" )
		{
			ObjEditor.document.execCommand("JustifyRight");
		}
		else if( obj.id == "numlist" )
		{
			ObjEditor.document.execCommand("InsertOrderedList");
		}
		else if( obj.id == "itemlist" )
		{
			ObjEditor.document.execCommand("InsertUnorderedList");
		}
		else if( obj.id == "outdent" )
		{
			ObjEditor.document.execCommand("Outdent");
		}
		else if( obj.id == "indent" )
		{
			ObjEditor.document.execCommand("Indent");
		}
		else if( obj.id == "table" )
		{
        	if( selectionObj.m_selection == null )
                ObjEditor.focus();

            view_table(obj.id);
		}
		else if( obj.id == "newdoc" )
		{
			ObjEditor.document.open("text/html");
			ObjEditor.document.write(default_source);
			ObjEditor.document.close();
			ObjEditor.document.oncontextmenu = new Function("return false;");
		}
		else if( obj.id == "image" )
		{
        	if( selectionObj.m_selection == null )
                ObjEditor.focus();

            view_table(obj.id);
        }
        else if (obj.id == "link" ) {
            view_table(obj.id);
        }
	}
	
    buttonout(obj);
    document.all.ObjEditor.focus();
}


function view_table(gubun) {
    
    has_focus = false;

	if(gubun == "image") {
        imageDiv.style.left = event.x + document.body.scrollLeft - 10;
        imageDiv.style.top  = event.y + document.body.scrollTop  - 90;
        start_timeout(document.all.imageTable);
        document.all.IMAGE_URL.value = "http://";
        imageDiv.style.visibility = "visible";
		
		//document.all.IMAGE_URL.focus();

    } else if (gubun == "link") {
        urlDiv.style.left = event.clientX + document.body.scrollLeft - 10;
        urlDiv.style.top  = event.clientY + document.body.scrollTop  - 75;
        start_timeout(document.all.urlTable);
        document.all.LINK_URL.value = "http://";
        urlDiv.style.visibility = "visible";
		
		//document.all.LINK_URL.focus();

    } else if (gubun == "table") {
        tableDiv.style.left = event.clientX + document.body.scrollLeft - 10;
        tableDiv.style.top  = event.clientY + document.body.scrollTop  - 115;
        
        document.all.COL_NUM.value = "2";
        document.all.ROW_NUM.value = "3";

        start_timeout(document.all.tableTable);
        tableDiv.style.visibility = "visible";
    } else {
        document.all.colorDiv.innerHTML = tagColor(gubun);
        colorDiv.style.left = event.clientX + document.body.scrollLeft - 10;
        colorDiv.style.top  = event.clientY + document.body.scrollTop  - 130;
        colorDiv.style.visibility = "visible";
        start_timeout(document.all.colorTable);
    }
}



// 색깔관련---------------------------------------------------------------------------------------------------------------------
var colortone = new Array(10);
colortone[0] = new Array('#ffffff','#e5e4e4','#d9d8d8','#c0bdbd','#a7a4a4','#8e8a8b','#827e7f','#767173','#5c585a','#000000');
colortone[1] = new Array('#fefcdf','#fef4c4','#feed9b','#fee573','#ffed43','#f6cc0b','#e0b800','#c9a601','#ad8e00','#8c7301');
colortone[2] = new Array('#ffded3','#ffc4b0','#ff9d7d','#ff7a4e','#ff6600','#e95d00','#d15502','#ba4b01','#a44201','#8d3901');
colortone[3] = new Array('#ffd2d0','#ffbab7','#fe9a95','#ff7a73','#ff483f','#fe2419','#f10b00','#d40a00','#940000','#6d201b');
colortone[4] = new Array('#ffdaed','#ffb7dc','#ffa1d1','#ff84c3','#ff57ac','#fd1289','#ec0078','#d6006d','#bb005f','#9b014f');
colortone[5] = new Array('#fcd6fe','#fbbcff','#f9a1fe','#f784fe','#f564fe','#f546ff','#f328ff','#d801e5','#c001cb','#8f0197');
colortone[6] = new Array('#e2f0fe','#c7e2fe','#add5fe','#92c7fe','#6eb5ff','#48a2ff','#2690fe','#0162f4','#013add','#0021b0');
colortone[7] = new Array('#d3fdff','#acfafd','#7cfaff','#4af7fe','#1de6fe','#01deff','#00cdec','#01b6de','#00a0c2','#0084a0');
colortone[8] = new Array('#edffcf','#dffeaa','#d1fd88','#befa5a','#a8f32a','#8fd80a','#79c101','#3fa701','#307f00','#156200');
colortone[9] = new Array('#d4c89f','#daad88','#c49578','#c2877e','#ac8295','#c0a5c4','#969ac2','#92b7d7','#80adaf','#9ca53b');

function tagColor(flag){
    
    // flag == "forecolor" -> 폰트 색 
    // flag == "backcolor" -> 배경 색
    // flag == "tablebgcolor" ->  표배경색
	
    var str = "";
	
    //str = str + "<html><body marginwidth=0 marginheight=0 topmargin=0 leftmargin=0>";
	str = str + "<table id='colorTable' onmousemove='clear_timeout()' onmouseout='start_timeout(this)' cellpadding=0 cellspacing=0 border=0";

	for (var i=0; i<10; i++){
		str = str + "<tr bgcolor='#ffffff'>";
		for(var j=0; j<10; j++){
			str += "<td onmouseover=this.style.backgroundColor='blue' onmouseout=this.style.backgroundColor=''>";
            str += "<table cellpadding=0 cellspacing=1 border=0>";
            str += "<tr><td bgcolor='" + colortone[i][j] + "' onclick='select_color(\"" + flag + "\", \"" + colortone[i][j] + "\");' width=10 height=10></td></tr>";
            str += "</table>"
            str += "</td>";
		}   
		str = str + "</tr>";
	}       
    str += "</table>";
    
    return str;

}


function select_color(flag, colortone) {

    if(flag=='fontcolor') {

        if( selectionObj.m_selection != null )
            selectionObj.GetSelection();
        ObjEditor.document.execCommand( "ForeColor", false, colortone );

    } else if (flag =='fontbgcolor') {
        
        if( selectionObj.m_selection != null )
            selectionObj.GetSelection();
        ObjEditor.document.execCommand( "BackColor", false, colortone );

    } else if (flag =='bgcolor') {
        
        ObjEditor.document.body.style.backgroundImage = '';
        ObjEditor.document.body.style.backgroundColor = '' + colortone + '';

    } else if (flag =='tablebgcolor') {
        
        if( selectionObj.m_selection != null )
            selectionObj.GetSelection();
        table_color(colortone);
    
    }
    
    close_div(colorDiv);
}

function table_color(bgColor)
{
	if( ObjEditor.document.selection.type == "None" || ObjEditor.document.selection.type == "Text" )
	{
		var selectedTD;
		if( (selectedTD = isintd( ObjEditor.document.selection.createRange().parentElement() ) ) != null )
		{
            selectedTD.bgColor = bgColor;
		}
	}
	else if( ObjEditor.document.selection.type == "Control" )
	{
		if( ObjEditor.document.selection.createRange().item(0).tagName == "TABLE" )
		{
			ObjEditor.document.selection.createRange().item(0).bgColor = bgColor;
		}
	}
}

function isintd(obj)
{
	var i;
	for( i=0 ; i < 100 ; i++ )
	{

        if( obj.tagName == "TD" )
			return obj;
		else if( obj.tagName == "TABLE" || obj.tagName == "BODY" || obj.tagName == null )
			return null;
		else
			obj = obj.parentElement;
	}
	
	return null;
}


//------------------------------------------------------------------------------------------------------------------------------



/**
 *
 */
function make_table() {
	
    var NumRows = document.all.ROW_NUM.value;
	var NumCols = document.all.COL_NUM.value;
    var border  = document.all.BORDER_THICK.options[document.all.BORDER_THICK.options.selectedIndex].value;

    if(gfTrim(NumRows)=='' || gfTrim(NumCols)=='') {
        alert("테이블의 행과 열의 갯수를 입력하세요.");
        document.all.COL_NUM.focus();
        return;
    }
    
    if (isNaN(NumRows) || isNaN(NumCols)) {
        alert("행과 열의 갯수는 숫자이어야 합니다.");
        document.all.COL_NUM.focus();
        return;
    }

	var strTable = "<TABLE border='" + border + "'  width=98% cellspacing=0 bordercolor ='black' style='border-collapse:collapse;'>\n";

	var widthCols = Math.ceil(100/NumCols)-1;
	var CellAttrs = "width='" + widthCols + "%'";
	
	var i = 0, j = 0;
	for( i = 0 ; i < NumRows ; i++ )
	{
		strTable += "<TR>\n";
		for( j = 0 ; j < NumCols ; j++ )
		{
			strTable += "<TD " + CellAttrs + "><DIV>&nbsp;</DIV></TD>\n";
		}		
		strTable += "</TR>\n";
	}
	strTable += "</TABLE>\n";

	var Range = null;
	if( selectionObj.m_selection != null )
	{
		selectionObj.GetSelection();
		Range = selectionObj.m_selection;
	}
	else
	{
		Range = ObjEditor.document.selection.createRange();
		Range.type = ObjEditor.document.selection.type;
	}
	
	if( Range.type != "Control" && ObjEditor.document.body.createTextRange().inRange( Range ) == true )
		Range.pasteHTML( strTable );
	else if( Range.type == "Control" )
	{
		Range.execCommand("Delete");
		ObjEditor.document.selection.createRange().pasteHTML( strTable );
	}

	close_div(tableDiv);

}


/**
 *
 */
function patch_url() {
    var src = document.all.LINK_URL.value;
    var Range = null;
	
    if( selectionObj.m_selection != null )
	{
		selectionObj.GetSelection();
		Range = selectionObj.m_selection;
	}
	else
	{
        Range = ObjEditor.document.selection.createRange();
		Range.type = ObjEditor.document.selection.type;
	}

    if(Range.type != "Control") {
        if(Range.htmlText!='') {
            Range.pasteHTML("<A HREF='"+src+"' target='_blank'>"+Range.htmlText+"</A>");
        }
    }

    close_div(document.all.urlDiv);
}

/**
 *
 */
function insert_image(imgSrc,align) {
    
    //var src = document.all.IMAGE_URL.value;
    var src = "";
    var Range = null;

    src = "<img src=\"" + imgSrc + "\" align='" + align + "' border=0>";
	
    if( selectionObj.m_selection != null )
	{
		selectionObj.GetSelection();
		Range = selectionObj.m_selection;
	}
	else
	{
        Range = ObjEditor.document.selection.createRange();
		Range.type = ObjEditor.document.selection.type;
	}
    
    if( Range.type != "Control" && ObjEditor.document.body.createTextRange().inRange( Range ) == true )
		Range.pasteHTML( src );
	else if( Range.type == "Control" )
	{
		Range.execCommand("Delete");
		ObjEditor.document.selection.createRange().pasteHTML( src );
	}

    close_div(document.all.imageDiv);
}


function insert_movie(param) {
    
    var src = "";
    var Range = null;

    src = "<embed src='"+ param +"' ShowStatusBar='true' autostart='true' >";
	
    if( selectionObj.m_selection != null )
	{
		selectionObj.GetSelection();
		Range = selectionObj.m_selection;
	}
	else
	{
        Range = ObjEditor.document.selection.createRange();
		Range.type = ObjEditor.document.selection.type;
	}
    
    if( Range.type != "Control" && ObjEditor.document.body.createTextRange().inRange( Range ) == true )
		Range.pasteHTML( src );
	else if( Range.type == "Control" )
	{
		Range.execCommand("Delete");
		ObjEditor.document.selection.createRange().pasteHTML( src );
	}

    close_div(document.all.imageDiv);
}


function insert_bg(param) {
	ObjEditor.document.body.style.backgroundImage = "url('"+param+"')";
}

function start_timeout(table)
{
	if( close_id != null )
			clearTimeout(close_id);

	if( table.id == "imageTable" && !has_focus)
	{
        tableDiv.style.visibility = "hidden";
        urlDiv.style.visibility = "hidden";
        colorDiv.style.visibility = "hidden";
		close_id=setTimeout("close_div(document.all.imageDiv)",1500);
	}
	else if( table.id == "urlTable"  && !has_focus)
	{
        tableDiv.style.visibility = "hidden";
		imageDiv.style.visibility = "hidden";
        colorDiv.style.visibility = "hidden";
		close_id=setTimeout("close_div(document.all.urlDiv)",1500);
	}
	else if( table.id == "colorTable")
	{
		
        tableDiv.style.visibility = "hidden";
        imageDiv.style.visibility = "hidden";
		urlDiv.style.visibility = "hidden";
		close_id=setTimeout("close_div(document.all.colorDiv)",1500);
	}
	else if( table.id == "tableTable" && !has_focus)
	{
		imageDiv.style.visibility = "hidden";
		urlDiv.style.visibility = "hidden";
        colorDiv.style.visibility = "hidden";
		close_id=setTimeout("close_div(document.all.tableDiv)",1500);
	}
}

function hide_layer() {
        tableDiv.style.visibility = "hidden";
		imageDiv.style.visibility = "hidden";
		urlDiv.style.visibility = "hidden";
        colorDiv.style.visibility = "hidden";
}

function clear_timeout()
{
	clearTimeout(close_id);
	close_id = null;
}

function close_div(obj)
{
	obj.style.visibility = "hidden";
	clearTimeout(close_id);
}

function gfTrim(inStr){
	if(inStr==null) inStr="";
	while(inStr.substring(0,1)==" "){
		inStr=inStr.substring(1);
	}
	while(inStr.substring(inStr.length-1)==" "){
		inStr=inStr.substring(0,inStr.length-1);
	}
	return inStr;
}

function ContentsDecode(str){
    str = str.replace(/&lt;/gi,"<");
    str = str.replace(/&gt;/gi,">");
    str = str.replace(/&amp;/gi,"&");
	return str;
}

