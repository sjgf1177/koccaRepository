function Folder(folderDescription, hreference, open_check){ //constructor 
	//----------------------------------------------------------
	this.open_check=open_check	 //해당 폴더를 열어주기 위해서
	//----------------------------------------------------------
	
	//constant data 
	this.desc = folderDescription 
	this.hreference = hreference 
	this.id = -1   
	this.navObj = 0
	this.iconImg = 0  
	this.nodeImg = 0  
	this.isLastNode = 0 
	this.firstNode = 0
    	
	//dynamic data			
	//----------------------------------------------------------
	//"false" 일때 폴더가 열림

	if (open_check == "1"){
		this.isOpen = false
	}else{
		this.isOpen = true 
	}
	//----------------------------------------------------------

	if(this.hreference)
		this.iconSrc = "/images/user/foldertree/obj_1001.gif"   
	else
		this.iconSrc = "/images/user/foldertree/lesson_1001.gif"

	this.children = new Array 
	this.nChildren = 0 

	//methods 
	this.initialize = initializeFolder 
	this.setState = setStateFolder 	
			
	this.addChild = addChild 
	this.createIndex = createEntryIndex 
	this.hide = hideFolder 
	this.display = display 
	this.renderOb = drawFolder 
	this.totalHeight = totalHeight 
	this.subEntries = folderSubEntries 
	this.outputLink = outputFolderLink 
} 


function Folder_1(folderDescription, hreference, open_check){ //constructor 
	//----------------------------------------------------------
	this.open_check=open_check	 //해당 폴더를 열어주기 위해서
	//----------------------------------------------------------
	
	//constant data 
	this.desc = folderDescription 
	this.hreference = hreference 
	this.id = -1   
	this.navObj = 0
	this.iconImg = 0  
	this.nodeImg = 0  
	this.isLastNode = 0 
	this.firstNode = 0
    	
	//dynamic data			
	//----------------------------------------------------------
	//"false" 일때 폴더가 열림

	if (open_check == "1"){
		this.isOpen = false
	}else{
		this.isOpen = true 
	}
	//----------------------------------------------------------

	if(this.hreference)
		this.iconSrc = "/images/user/foldertree/obj_1001.gif"   
	else
		this.iconSrc = "/images/user/foldertree/lesson_1001.gif"

	this.children = new Array 
	this.nChildren = 0 

	//methods 
	this.initialize = initializeFolder 
	this.setState = setStateFolder_1 	
			
	this.addChild = addChild 
	this.createIndex = createEntryIndex 
	this.hide = hideFolder 
	this.display = display 
	this.renderOb = drawFolder 
	this.totalHeight = totalHeight 
	this.subEntries = folderSubEntries 
	this.outputLink = outputFolderLink 
} 


function setStateFolder(isOpen){ 
	var subEntries 
	var totalHeight 
	var fIt = 0 
	var i=0 

	if (isOpen == this.isOpen) return 

	if (browserVersion == 2){ 
		totalHeight = 0 
		for (i=0; i < this.nChildren; i++) 
			totalHeight = totalHeight + this.children[i].navObj.clip.height 

		subEntries = this.subEntries() 

		if (this.isOpen) 
			totalHeight = 0 - totalHeight 

		for (fIt = this.id + subEntries + 1; fIt < nEntries; fIt++) 
			indexOfEntries[fIt].navObj.moveBy(0, totalHeight) 
	}  
	this.isOpen = isOpen 

	propagateChangesInState(this) 
} 

function setStateFolder_1(isOpen){ 
	var subEntries 
	var totalHeight 
	var fIt = 0 
	var i=0 

	if (isOpen == this.isOpen) return 

	if (browserVersion == 2){ 
		totalHeight = 0 
		for (i=0; i < this.nChildren; i++) 
			totalHeight = totalHeight + this.children[i].navObj.clip.height 

		subEntries = this.subEntries() 

		if (this.isOpen) 
			totalHeight = 0 - totalHeight 

		for (fIt = this.id + subEntries + 1; fIt < nEntries; fIt++) 
			indexOfEntries[fIt].navObj.moveBy(0, totalHeight) 
	}  
	this.isOpen = isOpen 
    
	propagateChangesInState_1(this) 
} 


function propagateChangesInState(folder) {   
	var i=0
 
	if (folder.isOpen){ 
		if (folder.nodeImg) 
			if (folder.isLastNode)
				folder.nodeImg.src = "/images/user/foldertree/ftv2mlastnode.gif" 
			else 
				folder.nodeImg.src = "/images/user/foldertree/ftv2mnode.gif" 
        

		if(folder.hreference){
			folder.iconImg.src = "/images/user/foldertree/obj_1001.gif" 
		} else {
				folder.iconImg.src = "/images/user/foldertree/lesson_1001.gif" 
		}

		for (i=0; i<folder.nChildren; i++) 
			folder.children[i].display() 
		
	} else { 
		if (folder.nodeImg) 
			if (folder.isLastNode) 
				folder.nodeImg.src = "/images/user/foldertree/ftv2plastnode.gif" 
			else 
				folder.nodeImg.src = "/images/user/foldertree/ftv2pnode.gif" 

		if(folder.hreference)
			folder.iconImg.src = "/images/user/foldertree/obj_1001.gif" 
		else
			folder.iconImg.src = "/images/user/foldertree/lesson_1001.gif" 

		for (i=0; i<folder.nChildren; i++) 
			folder.children[i].hide() 
	}  
} 

function propagateChangesInState_1(folder) {   
	var i=0

    folder_count++;  // 폴더의 위치 확인용 카운트		
	tree_folder_count = folder.nChildren + 2;  // 처음 열었을 때, 각 아이템을 눌렀을때 
		folder_init = folder.nChildren + 1;  

	if (folder.isOpen){ 
		if (folder.nodeImg) 
			if (folder.isLastNode)
				folder.nodeImg.src = "/images/user/foldertree/ftv2mlastnode.gif" 
			else 
				folder.nodeImg.src = "/images/user/foldertree/ftv2mnode.gif" 
        

		if(folder.hreference){
			folder.iconImg.src = "/images/user/foldertree/obj_1001.gif" 
		} else {   
				folder.iconImg.src = "/images/user/foldertree/module_1001.gif" 
		}

		for (i=0; i<folder.nChildren; i++) 
			folder.children[i].display() 
		
	} else { 
		if (folder.nodeImg) 
			if (folder.isLastNode) 
				folder.nodeImg.src = "/images/user/foldertree/ftv2plastnode.gif" 
			else 
				folder.nodeImg.src = "/images/user/foldertree/ftv2pnode.gif" 

		if(folder.hreference)
			folder.iconImg.src = "/images/user/foldertree/obj_1001.gif" 
		else
			folder.iconImg.src = "/images/user/foldertree/lesson_1001.gif" 

		for (i=0; i<folder.nChildren; i++) 
			folder.children[i].hide() 
	}  
} 

function hideFolder() { 
	if (browserVersion == 1) { 
		if (this.navObj.style.display == "none") return 
		this.navObj.style.display = "none" 
	} else { 
		if (this.navObj.visibility == "hiden") return 
		this.navObj.visibility = "hiden" 
	} 
   
	this.setState(0) 
} 

function initializeFolder(level, lastNode, leftSide) { 
	
	var j=0 
	var i=0 
	var numberOfFolders 
	var numberOfDocs 
	var nc 

	nc = this.nChildren 

	this.createIndex() 

	var auxEv = "" 

	if (browserVersion > 0)
		auxEv = "<a href='javascript:clickOnNode("+this.id+")'>" 
	else
		auxEv = "<a>" 


	if (level>0) 
		if (lastNode){ //the last 'brother' in the children array 
			this.renderOb(leftSide + auxEv + "<img name='nodeIcon" + this.id + "' src='/images/user/foldertree/ftv2mlastnode.gif' border=0 align=absmiddle></a>") 
			leftSide = leftSide + "<img src='/images/user/foldertree/ftv2blank.gif' align=absmiddle>"  
			this.isLastNode = 1 
		} else { 
			this.renderOb(leftSide + auxEv + "<img name='nodeIcon" + this.id + "' src='/images/user/foldertree/ftv2mnode.gif' border=0 align=absmiddle></a>") 
			leftSide = leftSide + "<img src='/images/user/foldertree/ftv2vertline.gif' align=absmiddle>" 
			this.isLastNode = 0 
		} 
	else 
		this.renderOb("") 

	if (nc > 0) { 
		level = level + 1 
		for (i=0 ; i < this.nChildren; i++)  { 
			if (i == this.nChildren-1) 
				this.children[i].initialize(level, 1, leftSide) 
			else 
				this.children[i].initialize(level, 0, leftSide) 
		} 
	} 
} 

function drawFolder(leftSide) { 
	if (browserVersion == 2) { 
		if (!doc.yPos) 
		doc.yPos=8

		doc.write("<layer id='folder" + this.id + "' top=" + (doc.yPos+78) + " visibility=hiden>") 
	} 

	doc.write("<table ") 
	if (browserVersion == 1) 
		doc.write(" id='folder" + this.id + "' style='position:block;' ") 
	doc.write(" border=0 cellspacing=0 cellpadding=0>") 
	doc.write("<tr><td>") 
	doc.write(leftSide) 
	this.outputLink() 
	doc.write("<img name='folderIcon" + this.id + "' ") 
	doc.write("src='" + this.iconSrc+"' border=0 align=absmiddle></a>") 
	doc.write("</td><td valign=middle nowrap>") 
	if (USETEXTLINKS) { 
		this.outputLink() 
		doc.write(this.desc + "</a>") 
	} else 
		doc.write(this.desc) 
	doc.write("</td>")  
	doc.write("</table>") 

	if (browserVersion == 2) { 
		doc.write("</layer>") 
	} 

	if (browserVersion == 1) { 
		this.navObj = doc.all["folder"+this.id] 
		this.iconImg = doc.all["folderIcon"+this.id] 
		this.nodeImg = doc.all["nodeIcon"+this.id] 
	} else if (browserVersion == 2) { 
		this.navObj = doc.layers["folder"+this.id] 
		this.iconImg = this.navObj.document.images["folderIcon"+this.id] 
		this.nodeImg = this.navObj.document.images["nodeIcon"+this.id] 
		doc.yPos=doc.yPos+this.navObj.clip.height 
	} 
} 

// 이분이 제목 클릭하면 컨텐츠 뜨는 부분..
function outputFolderLink() { 
	if (this.hreference) { 
		if(this.hreference != ""){
			doc.write("<a href=\"" + this.hreference + "\"")	// TARGET=\"FrameContent\" 
			if (browserVersion > 0) 
				doc.write(" onClick='clickOnFolder("+this.id+")'") 
			doc.write(">") 
		}else
			doc.write("<a>") 
	} else 
		doc.write("<a>") 
//  doc.write("<a href='javascript:clickOnFolder("+this.id+")'>")   
} 

function addChild(childNode) { 
	this.children[this.nChildren] = childNode 
	this.nChildren++ 
	return childNode 
} 

function folderSubEntries() { 
	var i = 0 
	var se = this.nChildren 

	for (i=0; i < this.nChildren; i++){ 
		if (this.children[i].children) //is a folder 
		se = se + this.children[i].subEntries() 
	} 

	return se 
} 


// Definition of class Item (a document or link inside a Folder) 
// ************************************************************* 

function Item(depthNo, itemDescription, itemLink, jindo){ // Constructor 
	// constant data 
	this.desc = itemDescription 
	this.link = itemLink 
	this.depth = depthNo;
	this.id = -1 //initialized in initalize() 
	this.navObj = 0 //initialized in render() 
	this.iconImg = 0 //initialized in render()  	
	if (jindo==1){  // 진도완료
		this.iconSrc = "/images/user/foldertree/obj_f.gif" 
	} else if (jindo == 2){  // 현재 노드
		this.iconSrc = "/images/user/foldertree/edu.gif" 
	}else { // 일반 형태
		this.iconSrc = "/images/user/foldertree/obj_1001.gif" 
	}

	this.iconSrc2 = "/images/user/foldertree/lesson_1001.gif" 

	// methods 
	this.initialize = initializeItem 
	this.createIndex = createEntryIndex 
	this.hide = hideItem 
	this.display = display 
	this.renderOb = drawItem 
	this.totalHeight = totalHeight 
} 

function Item2(itemDescription, itemLink){ // Constructor 
	// constant data 
	this.desc = itemDescription 
	this.link = itemLink 
	this.id = -1 //initialized in initalize() 
	this.navObj = 0 //initialized in render() 
	this.iconImg = 0 //initialized in render() 
	this.iconSrc = "/images/user/foldertree/ftv2link.gif" 

	// methods 
	this.initialize = initializeItem 
	this.createIndex = createEntryIndex 
	this.hide = hideItem 
	this.display = display 
	this.renderOb = drawItem 
	this.totalHeight = totalHeight 
}

function hideItem() { 
	if (browserVersion == 1) { 
		if (this.navObj.style.display == "none") return 
		this.navObj.style.display = "none" 
	} else { 
		if (this.navObj.visibility == "hiden") return 
		this.navObj.visibility = "hiden" 
	}     
} 

function initializeItem(level, lastNode, leftSide) {  
	this.createIndex() 

	if (level>0) 
		if (lastNode){ //the last 'brother' in the children array 
			this.renderOb(leftSide + "<img src='/images/user/foldertree/ftv2lastnode.gif' align=absmiddle>") 
			leftSide = leftSide + "<img src='/images/user/foldertree/ftv2blank.gif' align=absmiddle>"  
		} else { 
			this.renderOb(leftSide + "<img src='/images/user/foldertree/ftv2node.gif' align=absmiddle>") 
			leftSide = leftSide + "<img src='/images/user/foldertree/ftv2vertline.gif' align=absmiddle>" 
		} 
	else 
		this.renderOb("")   
} 

function drawItem(leftSide) { 
	if (browserVersion == 2) 
		doc.write("<layer id='item" + this.id + "' top=" + (doc.yPos+78) + " visibility=hiden>") 

	doc.write("<table ") 
	if (browserVersion == 1) 
		doc.write(" id='item" + this.id + "' style='position:block;' ") 
	doc.write(" border=0 cellspacing=0 cellpadding=0>") 
	doc.write("<tr><td>") 
	doc.write(leftSide) 

	if(this.depth > 2){
		for(idx = 2; idx < this.depth; idx++){
			doc.write("<img src='/images/user/foldertree/ftv2depthnode.gif' border=0 align=absmiddle>") 
		}
	}

	if(this.link){	 /* 문서에 링크가 있으면 아이콘에 링크, 없으면 링크없음, 링크있으면 문서, 없으면 폴더로 가정 */
		doc.write("<a href=\"" + this.link + "\">") 
		doc.write("<img id='itemIcon"+this.id+"' ") 
		doc.write("src='"+this.iconSrc+"' border=0 align=absmiddle>") 
		doc.write("</a>") 
	}else{
		doc.write("<img id='itemIcon"+this.id+"' ") 
		doc.write("src='"+this.iconSrc2+"' border=0 align=absmiddle>") 
	}

	doc.write("</td><td valign=middle nowrap>") 
	if (USETEXTLINKS){
		if(this.link){ /* 문서에 링크가 있으면 아이콘에 링크, 없으면 링크없음 */
			doc.write("<a href=\"" + this.link + "\" >" + this.desc + "</a>") 
		}else{
			doc.write(this.desc) 
		}
	}else{
		doc.write(this.desc) 
	}
	doc.write("</table>") 

	if (browserVersion == 2) 
		doc.write("</layer>") 

	if (browserVersion == 1) { 
		this.navObj = doc.all["item"+this.id] 
		this.iconImg = doc.all["itemIcon"+this.id] 
	} else if (browserVersion == 2) { 
		this.navObj = doc.layers["item"+this.id] 
		this.iconImg = this.navObj.document.images["itemIcon"+this.id] 
		doc.yPos=doc.yPos+this.navObj.clip.height 
	} 
} 


// Methods common to both objects (pseudo-inheritance) 
// ******************************************************** 

function display() { 
	if (browserVersion == 1) 
		this.navObj.style.display = "block" 
	else 
		this.navObj.visibility = "show" 
} 

function createEntryIndex() { 
	this.id = nEntries 
	indexOfEntries[nEntries] = this 
	nEntries++ 
} 

// total height of subEntries open 
function totalHeight(){ //used with browserVersion == 2 
	var h = this.navObj.clip.height 
	var i = 0 

	if (this.isOpen) //is a folder and _is_ open 
		for (i=0 ; i < this.nChildren; i++)  
			h = h + this.children[i].totalHeight() 

	return h 
} 


// Events 
// ********************************************************* 

function clickOnFolder(folderId) {
	var clicked = indexOfEntries[folderId] 

	if (!clicked.isOpen) 
		clickOnNode(folderId) 

	return  

	if (clicked.isSelected) return 
} 

function clickOnNode(folderId) {
	var clickedFolder = 0 
	var state = 0 

	clickedFolder = indexOfEntries[folderId] 
	state = clickedFolder.isOpen 

	clickedFolder.setState(!state) //open<->close  
} 

function initializeDocument() { 
	if (doc.all) 
		browserVersion = 1 //IE4   
	else 
		if (doc.layers) 
			browserVersion = 2 //NS4 
	else 
		browserVersion = 0 //other 

	foldersTree.initialize(0, 1, "") 
	foldersTree.display()

	if (browserVersion > 0) { 
		doc.write("<layer top="+indexOfEntries[nEntries-1].navObj.top+">&nbsp;</layer>") 

		// close the whole tree 
		clickOnNode(0) 
		// open the root folder 
		clickOnNode(0) 
	} 
} 

// Auxiliary Functions for Folder-Treee backward compatibility 
// ********************************************************* 

function gFld(description, hreference, open_check) { 
	folder = new Folder(description, hreference, open_check) 
	return folder 
} 

function gFld_1(description, hreference, open_check) { 
	folder = new Folder_1(description, hreference, open_check) 
	return folder 
} 

function gLnk(target, linkDepth, description, linkData, jindo) { 
	fullLink = "" 
	
	if (target==0) { 
		if(linkData != ""){
			fullLink = ""+linkData+""// target=\"detail\""
		}
		linkItem = new Item(linkDepth, description, fullLink, jindo)     
	} else { 
		if (target==1){ 
			if(linkData != ""){
				fullLink = "'http://"+linkData+"' target=ebody" 
			}
			linkItem = new Item(linkDepth, description, fullLink, jindo) 

		}else {
			if(target==2){
				if(linkData != ""){
					fullLink = "'"+linkData+"' target=\"main\"" 
				}
				linkItem = new Item2(linkDepth, description, fullLink)
			}else {
				if(linkData != ""){
					fullLink = "'http://"+linkData+"' target=ebody" 
				}
				linkItem = new Item2(linkDepth, description, fullLink)	
			}
		}
	} 

	//linkItem = new Item(description, fullLink)   
	return linkItem 
} 

function insFld(parentFolder, childFolder) { 
	return parentFolder.addChild(childFolder) 
} 

function insDoc(parentFolder, document) { 
	parentFolder.addChild(document) 
} 

// Global variables 
// **************** 
USETEXTLINKS = 1 
indexOfEntries = new Array 
nEntries = 0 
doc = document 
browserVersion = 0 
////////////////////////////////
folder_count=0;
tree_folder_count=0;
folder_init=0;
click_count=0;
 
///////////////////////////////////////////////////////////
// 타임체크
//////////////////////////////////////////////////////////

var cnt = 0;
var Interval_id;
var Complete_Time = 0;

function timer(){
	cnt = cnt + 1;
	frm.timer.value=cnt

	if(Complete_Time > -1 && cnt >= Complete_Time){	// 기본학습시간이 0일때는 1분이 되는 순간 자동 저장..., 남은 시간이 0인 경우엔 -1이 되므로 제외됨
		Complete_Time	= 0;
		end_time();
		Tree_Reload();
	}
}

//새로운 객체의 학습 시작시 호출
function start_time(tot_time, sum_time){
	var chkForm = parent.FrameLeft.frm;
	var flag = chkForm.TimerFlag.value;
	if(flag=="1"){
		var rest_time = tot_time - sum_time;
		if((tot_time == 0 && sum_time == 0) || (tot_time > 0 && rest_time > 0)){	// 기본학습시간이 0이고, 전혀 학습안된 경우나, 기본학습시간이 있고 남은 시간이 있을 경우 시간셋팅 (0포함됨)
			Complete_Time = rest_time;
		}else{
			Complete_Time = -1;
		}

		cnt=0;
		chkForm.timer.value=cnt;
		Interval_id=setInterval("timer()",60000);	// 60초마다
	//	Interval_id=setInterval("timer()",1000);	// 1초마다 --- 테스트용
	}else{
		clearInterval(Interval_id);
	}
}
var path_name= ""

function end_time(){
	var chkForm = parent.FrameLeft.frm;
	var flag = chkForm.TimerFlag.value;
	if(flag=="1"){
		clearInterval(Interval_id);
		return chkForm.timer.value;
	}else{
		return 0;
	}
}

//////////////////////////////////////////////////////////////
//함수
/////////////////////////////////////////////////////////////
var vv_url ="";
var vv_target ="";
var vv_oid ="";
var vv_lesson ="";
var vv_limitjindo ="";


function gopass(){	
	setTimeout("goCurSco()",1000);	
}

function goCurSco(){
	parent.etree.f1.p_oid.value=parent.etree.f1.p_oid.value;
    parent.etree.f1.p_lesson.value=parent.etree.f1.p_lesson.value;
    parent.etree.f1.submit();
}

function DoNothing(){	
	alert("아직 해당학습을 진행하실 수 없습니다.");	
	return;
}

function StudyObject_Pre(){

    if (parseInt(vv_limitjindo) == 2 )
	{
		alert("1일 학습 제한 진도를 넘었습니다. \n 다음에 학습하시기 바랍니다.");
		history.back();
		return;
	}
	parent.ebody.location.href="/learn/user/contents/z_ApiFrame.jsp?p_oid="+vv_oid+"&p_lesson="+vv_lesson+"&url="+vv_url;

	parent.etree.f1.p_oid.value=vv_oid;
    parent.etree.f1.p_lesson.value=vv_lesson;
    parent.etree.f1.submit();
}

function StudyObject(url, target, oid, lesson, limitjindo){
    
	vv_url = url;
	vv_target = target;
	vv_oid = oid;
	vv_lesson = lesson;
	vv_limitjindo = limitjindo;

	parent.ebody.location.href="/learn/user/contents/z_Loading.html";
	setTimeout("StudyObject_Pre()",500);	
}


function goNext(){    
	//setTimeout("goNextPre()",1000);

    limitjindo = parent.etree.f1.limitjindo.value;
	p_next_jindo_bl =  parent.etree.f1.p_next_jindo_bl.value;
	
	if (limitjindo ==2)
	{
		if ((p_next_jindo_bl == "") || (p_next_jindo_bl == "incomplete") )
		{
			alert("1일 학습 제한 진도를 넘었습니다. \n 다음에 학습하시기 바랍니다.");		
			return;
		}
	}
		
	nextoid = parent.etree.f1.p_nextoid.value;
	nextlesson = parent.etree.f1.p_nextlesson.value;
	nexturl = parent.etree.f1.p_nexturl.value;

	if (nextoid == "")
	{
		alert("학습 종료가 되었습니다.");
		return;
	} else {
		parent.ebody.location.href="/learn/user/contents/z_Loading.html";
		setTimeout("goNextSco()",1000);	
	}	
}

function goBack(){

	limitjindo = parent.etree.f1.limitjindo.value;
	p_pre_jindo_bl =  parent.etree.f1.p_pre_jindo_bl.value;
	
	if (limitjindo ==2)
	{
		if ((p_pre_jindo_bl == "") || (p_pre_jindo_bl == "incomplete") )
		{
			alert("1일 학습 제한 진도를 넘었습니다. \n 다음에 학습하시기 바랍니다.");		
			return;
		}
	}

	setTimeout("goBackSco()",1000);	
}

function goNextPre(){
	
}

function goNextSco(){
	
	nextoid = parent.etree.f1.p_nextoid.value;
	nextlesson = parent.etree.f1.p_nextlesson.value;
	nexturl = parent.etree.f1.p_nexturl.value;

	if (nextoid == "")
	{
		alert("다음 컨텐츠가 없습니다.");
		return;
	} else {
		parent.ebody.location.href="/learn/user/contents/z_ApiFrame.jsp?p_oid="+nextoid+"&p_lesson="+nextlesson+"&url="+nexturl;

		parent.etree.f1.p_oid.value=nextoid;
		parent.etree.f1.p_lesson.value=nextlesson;
		parent.etree.f1.submit();
	}
}

function goBackSco(){
	backoid = parent.etree.f1.p_backoid.value;
	backlesson = parent.etree.f1.p_backlesson.value;
	backurl = parent.etree.f1.p_backurl.value;

	if (backoid == "")
	{
		alert("이전 컨텐츠가 없습니다.");
		return;
	} else {
		parent.ebody.location.href="/learn/user/contents/z_ApiFrame.jsp?p_oid="+backoid+"&p_lesson="+backlesson+"&url="+backurl;

		parent.etree.f1.p_oid.value=backoid;
		parent.etree.f1.p_lesson.value=backlesson;
		parent.etree.f1.submit();
	}
}


function bookmark_first(url, oid, lesson){

	parent.ebody.location.href="/learn/user/contents/z_ApiFrame.jsp?p_oid="+oid+"&p_lesson="+lesson+"&url="+url;

	parent.etree.f1.p_oid.value=oid;
    parent.etree.f1.p_lesson.value=lesson;
    parent.etree.f1.submit();
}


