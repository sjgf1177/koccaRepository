// 메뉴 이름 셋팅

var pages=[];
	var tempurl;
	var pagenum = 0;

	for(var j=1;j<subjectpage[currentChapter].length;j++){
		for(var k=1;k<=parseInt(subjectpage[currentChapter][j],10);k++){
			pagenum++;
			var tempurl=itostr(j)+"01"+itostr(k)+".html";				
			pages.push({pg:pagenum, url:tempurl, category:clauNameSet[currentChapter][j], text:menuNaviNameSet[currentChapter][pagenum]});
		}
	}




var thisMain = this;
var indexOpenBtn;
var indexCloseBtn;
var indexTarget;
var indexStatus="close";

var MenuController = function(){
	var menuContent = $("#menu");
	if(menuContent){
		menuContent.empty();
		
		//메뉴구성
		var menuString ="";
		//var menuString = "<div id='menu_close'><input type='image' src='../common/img/menu/indexOpen.png' id='menu_close_btn'></div>";
		var currentCategory = "";
		/*
		for ( var i = 0; i < pages.length; i++) {
			if(typeof(pages[i].category) == 'undefined')
				continue;
			
			// 카테고리 설정
			if(currentCategory != pages[i].category) {
				if(currentCategory != "")
					menuString += "</ul>";
				currentCategory = pages[i].category;
				
				if(isCurrentCategory(i, currentPage) == true)
					menuString += "<ul><a class='selected2'";
				else
					menuString += "<ul><a";
				menuString += " href='#' onclick='goPageJump("+i+")'>" + pages[i].category + "</a>";
			}
			
			// 페이지 설정
			if(isCurrentPage(i, currentPage) == true)
				menuString += "<li class='selected'><a";
			else
				menuString += "<li><a";
			menuString += " href='#' onclick='goPageJump("+i+")'>" + pages[i].text + "</a><li>";
		}
		menuString += "</ul>";
		*/

var menuIconImageName=[];

			for ( var i = 0; i < pages.length; i++) {
				// 동영상 페이지 중복제거
				var prevText;
			var nextText;
			if(i!=0){
			prevText=pages[i-1].text;
			}else{
			prevText="";
			}
			if(i!=pages.length-1){
			nextText=pages[i+1].text;
			}else{
			nextText="";
			}


			if(prevText!=pages[i].text && i!=pages.length){
				menuIconImageName.push("btn"+menuNaviNameSet[currentChapter][i+1]);
					if(!isCurrentCategory(i, currentPage)  ){
				menuString += "<div id='menuIcon'><img src='../common/img/menu/n"+clauNumber+"/btn"+menuNaviNameSet[currentChapter][i+1]+".png' onclick='goPageJump("+i+")' class='mbtn'></div>";	
				}else{
				menuString += "<div id='menuIcon'><img src='../common/img/menu/n"+clauNumber+"/btn"+menuNaviNameSet[currentChapter][i+1]+"Over.png' onclick='goPageJump("+i+")' class='mbtn'></div>";	
				}
			}

			}			
		
		//alert(menuIconImageName);

			menuContent.append("<div id='menu_activated'>" + menuString + "</div><div id='menu_close'><input type='image' src='../common/img/menu/indexOpen.png' id='menu_close_btn'></div>");

		//menuContent.append("<div id='menu_activated'>" + menuString + "</div><div id='menu_close'><input type='image' src='../common/img/menu/indexOpen.png' id='menu_close_btn'></div>");


		

	$(".mbtn").each(function(index) {
		//if(!isCurrentCategory(index, currentPage)){
			$(this).mouseover(function() {
	     $(this).attr("src","../common/img/menu/n"+clauNumber+"/"+menuIconImageName[index]+"Over.png");
		});
		$(this).mouseout(function() {
			if(currentClau!=index+1){
		  $(this).attr("src","../common/img/menu/n"+clauNumber+"/"+menuIconImageName[index]+".png");
			}
		});
	//	}
	});
	
	// add
	$("#menu_activated").css("background-image","url(../common/img/menu/n"+clauNumber+"/indexbg.png)"); 		
	if(clauNumber==5){
	$("#menu").css("width","188px");
	$("#menu").css("height","409px");
	$("#menu_close").css("left","145px");
	$("#menu_activated").css("height","409px");
	}else if(clauNumber==6){
	$("#menu").css("width","228px");
	$("#menu").css("height","489px");
	$("#menu_close").css("left","185px");
	$("#menu_activated").css("height","489px");
	}else{
	$("#menu").css("width","188px");
	$("#menu").css("height","374px");
	$("#menu_close").css("left","145px");
	$("#menu_activated").css("height","340px");
	}
	}

	
	//페이지 로딩 1초후 메뉴 자동 숨기기
	//setTimeout(function () {$('#SideMenu #menu_button_close').trigger('click');} , 1000);
};



function isCurrentCategory(index, currentPage) {
	var currentCategory = "";
	for(var i = currentPage - 1; i > -1; i--) {
		if(typeof(pages[i].category) == 'undefined')
			continue;
		else {
			currentCategory = pages[i].category;
			break;
		}
	}
	
	for(var j = index; j > -1; j--) {
		if(typeof(pages[j].category) == 'undefined')
			continue;
		else {
			if(pages[j].category == currentCategory)
				return true;
			else
				return false;
		}
	}
	
	return false;
}

function isCurrentPage(index, currentPage) {
	var maxPage = currentPage - 1;
	
	if(index == maxPage) {
		return true;
	}
	else if (maxPage < index)
		return false;
	else { // if (index < currentPage)
		var countFarFromCurrentPage = 0;
		for(var i = index; i < currentPage; i++) {
			if(typeof(pages[i].text) == 'undefined')
				continue;
			else
				countFarFromCurrentPage++;
		}
		
	}
	if(countFarFromCurrentPage > 1)
		return false;
	return true;
}




function menuInit(){
	var menuController = new MenuController();
	
	indexOpenBtn = document.getElementById("menu_open_btn");
	indexCloseBtn = document.getElementById("menu_close_btn");
	myHandler(indexCloseBtn, "click", _indexCloseAc);
	myHandler(indexOpenBtn, "click", _indexOpenAc);
	
	indexTarget=document.getElementById("menu");
	if(indexTarget){
		indexTarget.style.display="none";
	}
	
	initTitles();
}

function myHandler(target,str,fn){

	if(target){
		if(target.addEventListener){
			//alert("target.addEventListener");
			target.addEventListener(str,fn,false);
		}else{
			//alert("click");
			if(str=="click"){
				target.onclick=fn;
			}
		}
	}	
}

function _indexOpenAc(){
	indexTarget=document.getElementById("menu");
	if(indexTarget){
		$("#menu").css("display", "block"); //edit
		indexOpenBtn.style.display="none";
		if(indexTarget){
			indexTarget.style.display="block";
			indexTarget.style.left=-210+"px";
		}
		//linear
		_moveIndex(indexTarget,0,"linear","open");
	}
}

function _indexCloseAc(){
	indexTarget=document.getElementById("menu");
	if(indexTarget){
		//indexTarget.style.display="none";
		if(indexTarget){
			//indexTarget.style.display="block";
			//indexTarget.style.left=0+"px";
		}
		// 퀴즈 메뉴 안겹치게 하기 위해서 z-index삭제
			$("#container").css("z-index", "");
		_moveIndex(indexTarget,-210,"linear","close");
	}
}

function _moveIndex(target,n,v,str) {
	indexStatus = str;
	JSTweener.addTween(menu.style, {
		time: 0.3,
		//<option value="linear">linear</options>
      	//<option value="easeInQuad">easeInQuad</options>
		transition: v,
		left: n,
		onComplete: _moveComplete
		
		// top: 300
	});
}

function _moveComplete(){
//	alert("_moveComplete : " + indexStatus);
	if(indexStatus=="close"){
		indexOpenBtn.style.display="block";
	
		$("#menu").css("display", "none"); //edit
	}else{
		indexOpenBtn.style.display="none";
		$("#container").css("z-index", "100");
	}
}

// 과정명, 차시명 설정
function initTitles() {
	// 차시명
//	$("#course").empty();
	//$("#course").append("<span>" + subject + "</span>");
	//$("#course").css({'background':'url(../common/img/title/chapterTitle'+currentChapter +'.png)', 'background-repeat' : 'no-repeat', 'background-position':'left top'}); 
	// 과정명
	/*
	$("#chap").empty();
	$("#chap").css({'background':'url(../common/img/title/contentsTitle.png)', 'background-repeat' : 'no-repeat', 'background-position':'right top'}); 
	*/
	//$("#chap").append(subtitle);
}