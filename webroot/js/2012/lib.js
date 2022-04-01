//flash player start
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
//flash player end


//
$(window).load(function() {
	//input 선택&비선택
	var $focuson = '2px solid #1884e8'; 
	var $focusoff = '1px solid #cccccc';
	var regional;
	
	$('div.content').find('input:text,input:file,textarea').not('.idput,.insearch').focus(function() {
		$(this).css('border',$focuson);
	});
	$('div.content').find('input:text,input:file,textarea').not('.idput,.insearch').blur(function() {
		$(this).css('border',$focusoff);
	});
	
	//JqueryUI 달력
	$.datepicker.regional['ko'] = {
   closeText: '닫기',
   prevText: '이전',
   nextText: '다음',
   currentText: '오늘',
   monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
   monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
   dayNames: ['일','월','화','수','목','금','토'],
   dayNamesShort: ['일','월','화','수','목','금','토'],
   dayNamesMin: ['일','월','화','수','목','금','토'],
   weekHeader: 'Wk',
   dateFormat: 'yy-mm-dd',
   firstDay: 0,
   isRTL:false,
   showMonthAfterYear:true,
   yearSuffix: ''};
	$.datepicker.setDefaults( $.datepicker.regional['ko'] );
	$('#startdate').datepicker({ numberOfMonths: 3, showButtonPanel: false });
	$('#enddate').datepicker({ numberOfMonths: 3, showButtonPanel: false });
	$('#learningstart').datepicker({ numberOfMonths: 3, showButtonPanel: false });
	
	//신청과정 전체선택&해제
	
	$('#allcourse').click(function() {
		$(this).attr('checked',$('#allcourse').is(':checked'));
		$('#requestbox').find('input:checkbox').attr('checked',$('#allcourse').is(':checked'));
	});
	
	
	//필수사항 유효성 검사
	$('#groupinsert').submit(function(){
		var returnmsg = $.makeArray($(this).find('input:text').map(function() {
			return $(this);
		}));
		var intxtmax = $(returnmsg).length-1;
		for (i=0; i<=intxtmax; i++) {
			if($(returnmsg)[i].val() == "" && $(returnmsg)[i].attr('class') != 'unqueryform') {
				alert($(returnmsg)[i].attr('title'));
				$(returnmsg)[i].focus();
				return false;
			}
		}
		if(!$(this).find('input:checkbox').is(':checked')) {
			alert('신청과정을 선택해주세요.');
			return false;
		}
	});
	
	//이메일 도메인 트랜스
	var $sel = $('#selectmailtype'); //select ID 값
	var $indomain = $('#inmeildomain'); //입력필드 ID 값
	
	$sel.change(function() {
		val = $sel.val();
		if($(this).val() == "직접입력") {
			$indomain.val('');
			$indomain.attr('readonly', false).css('background-color','#ffffff');
			$indomain.focus();
		} else {
			$indomain.val(val);
			$indomain.attr('readonly', true).css('background-color','#f5f5f5');
		}
	});
	
	//문의 및 제안
	$('#corpinsert').submit(function(){
		var returnmsg = $.makeArray($(this).find('input:text').map(function() {
			return $(this);
		}));
		var intxtmax = $(returnmsg).length-1;
		for (i=0; i<=intxtmax; i++) {
			if($(returnmsg)[i].val() == "" && $(returnmsg)[i].attr('class') != 'unqueryform') {
				alert($(returnmsg)[i].attr('title'));
				$(returnmsg)[i].focus();
				return false;
			}
		}
		var incontact = $(this).find('#incontact');
		if(!incontact.val()) {
			alert('내용을 입력해주세요');
			incontact.focus();
			return false;
		}
	});
});

//popup set start
function pop_view(urls,w,h,sc){
	sw = (screen.width);
	sh = (screen.height);
	size = "width="+w+",height="+h;
	sb = "scrollbars="+sc;
	var pop_view = window.open(urls,'',size,sb);
	pop_view.focus();
}
//popup set end

//sitemap start
function sitemap() {
  var obj = document.getElementById("sitebg"); 
  var overay = document.getElementById("overay"); 
	
  obj.style.width = $(document).width()+'px';
  obj.style.height = $(document).height()+'px';
  overay.style.width = $(document).width()+'px';
  overay.style.height = $(document).height()+'px';
	
	$(document).ready(function() {
		$('#sitebg').fadeTo('nomal',0.8);
		$('#overay').fadeTo('nomal',1);
		$('#header').attr('disabled', true);
		$('#container').attr('disabled', true);
		$('#footer').attr('disabled', true);
	});
} 

function mapclose() {
	$(document).ready(function() {
		$('#sitebg').fadeTo('fast',0);
		$('#overay').fadeTo('fast',0);
		$('#overay').hide('slow');
		$('#sitebg').hide('slow');
		$('#header').attr('disabled', false);
		$('#container').attr('disabled', false);
		$('#footer').attr('disabled', false);
	});
}

$(window).resize(function() {
	$('#sitebg').css('width',$(window).width());
	$('#overay').css('width',$(window).width());
});
//sitemap end


//menu slide start
$(function(){
	
	var maxheight = 50;
	var speedduration = 250;
	
	$(".navi").find('a').mouseover(function() {
		if ($("#submenuline").height() <= maxheight-1) {
			$("#submenuline").animate({ height:'+'+maxheight },speedduration );
		}
		var msgs = $(this).attr('id').substr(3);
		navi(msgs);
	});
	$(".navi").find('a').focus(function() {
		if ($("#submenuline").height() <= maxheight-1) {
			$("#submenuline").animate({ height:'+'+maxheight },speedduration );
		}
		var msgs = $(this).attr('id').substr(3);
		navi(msgs);
	});
	
	$("#container").mouseover(function() {
		if ($("#submenuline").height() >= maxheight-1) {
			$("#submenuline").animate({ height:'-='+maxheight },speedduration );
		}
		navi(0);
	});
	$("#container").focus(function() {
		if ($("#submenuline").height() >= maxheight-1) {
			$("#submenuline").animate({ height:'-='+maxheight },speedduration );
		}
		navi(0);
	});
	
});

function navi(obj){
	for(i=1; i<=7; i++){
		if (obj == "0") {
			document.getElementById("navi"+i).src = "/images/2012/common/menu/menu"+i+".png";
			document.getElementById("submenu"+i).style.display = "none";
		} else {
			if (i == obj) {
				document.getElementById("navi"+obj).src = "/images/2012/common/menu/menu"+obj+"on.png";
				document.getElementById("submenu"+obj).style.display = "block";
			} else {
				document.getElementById("navi"+i).src = "/images/2012/common/menu/menu"+i+".png";
				document.getElementById("submenu"+i).style.display = "none";
			}
		}
	}
}
//menu slide end

//main online tab set start
function onlinetab(obj){
	for(i=1; i<=4; i++){
		if (i == obj) {
			document.getElementById("online"+obj).style.background = "url('/images/2012/main/tabmenu/tabover.gif')";
			document.getElementById("online"+obj).style.color = "#525252";
			document.getElementById("online"+obj).style.fontWeight = "900";
			document.getElementById("online"+obj).style.borderTop = "1px solid #d3d3d3";
			document.getElementById("online"+obj).style.borderLeft = "1px solid #d3d3d3";
			document.getElementById("online"+obj).style.borderRight = "1px solid #d3d3d3";
			document.getElementById("online"+obj).style.borderBottom = "0px";
			document.getElementById("online"+obj).style.letterSpacing = "-1px";
			document.getElementById("onlinelist"+obj).style.display = "block";
		} else {
			document.getElementById("online"+i).style.background = "#ffffff";
			document.getElementById("online"+i).style.color = "#999999";
			document.getElementById("online"+i).style.fontWeight = "400";
			document.getElementById("online"+i).style.borderTop = "1px solid #e5e5e5";
			document.getElementById("online"+i).style.borderLeft = "1px solid #e5e5e5";
			document.getElementById("online"+i).style.borderRight = "1px solid #e5e5e5";
			document.getElementById("online"+i).style.borderBottom = "1px solid #d3d3d3";
			document.getElementById("online"+i).style.letterSpacing = "0";
			document.getElementById("onlinelist"+i).style.display = "none";
		}
	}
}
//main online tab set end


function txtlink(se,obj) {
	if(event.type == "focus") {
		document.getElementById(se+"txtfocus"+obj).style.textDecoration = "underline";
		document.getElementById(se+"txtfocus"+obj).style.fontWeight = "900";
		document.getElementById(se+"txtfocus"+obj).style.letterSpacing = "-1px";
	} else if (event.type == "blur") {
		document.getElementById(se+"txtfocus"+obj).style.textDecoration = "none";
		document.getElementById(se+"txtfocus"+obj).style.fontWeight = "400";
		document.getElementById(se+"txtfocus"+obj).style.letterSpacing = "0px";
	}
}

function boardfocus(se,obj) {
	if(event.type == "focus") {
		document.getElementById(se+"boardfocus"+obj).style.textDecoration = "underline";
		document.getElementById(se+"boardfocus"+obj).style.fontWeight = "900";
	} else if (event.type == "blur") {
		document.getElementById(se+"boardfocus"+obj).style.textDecoration = "none";
		document.getElementById(se+"boardfocus"+obj).style.fontWeight = "900";
	}
}

function imgfocus(se,obj) {
	if(event.type == "focus") {
		document.getElementById(se+"imgfocus"+obj).style.border = "2px solid red";
	} else if (event.type == "blur") {
		document.getElementById(se+"imgfocus"+obj).style.border = "0px";
	}
}

function filetypecheck(obj) {
	exturls = "/images/2012/common/icon/";
	ext = obj.slice(obj.indexOf(".") + 1).toLowerCase();
	if(ext == "hwp") {
		document.getElementById("exticon").src = exturls+"icon_hwp.gif";
	} else if(ext == "doc" || ext == "docx") {
		document.getElementById("exticon").src = exturls+"icon_doc.gif";
	} else if(ext == "xls") {
		document.getElementById("exticon").src = exturls+"icon_xls.gif";
	} else if(ext == "ppt") {
		document.getElementById("exticon").src = exturls+"icon_ppt.gif";
	} else {
		document.getElementById("exticon").src = exturls+"icon_idk.gif";
	}
}



function typechoiceopen(obj){
	if (obj == 1) {
		document.getElementById("typelist").style.display = "block";
	}
}
function searchtype(choicevalue,choicename){
	/*
	if(choicevalue == "주제") {
		changeEK = "adtitle";
	} else if(choicevalue == "내용") {
		changeEK = "adcontent";
	} else if(choicevalue == "작성자") {
		changeEK = "writer";
	} else if(choicevalue == "분류") {
		changeEK = "detailclass";
	}
	
	changeEK = choicevalue;
	*/
	document.getElementById("choicetype").value = choicename;
	document.getElementById("choicename").innerText = choicevalue;
	document.getElementById("typelist").style.display = "none";
	//alert(choicename);
}
function searchtypeclose() {
	document.getElementById("typelist").style.display = "none";
}
function offlinetab(obj){
	for(i=1; i<=4; i++){
		if (i == obj) {
			document.getElementById("offline"+obj).style.background = "url('/images/2012/main/tabmenu/tabover.gif')";
			document.getElementById("offline"+obj).style.color = "#525252";
			document.getElementById("offline"+obj).style.fontWeight = "900";
			document.getElementById("offline"+obj).style.borderTop = "1px solid #d3d3d3";
			document.getElementById("offline"+obj).style.borderLeft = "1px solid #d3d3d3";
			document.getElementById("offline"+obj).style.borderRight = "1px solid #d3d3d3";
			document.getElementById("offline"+obj).style.borderBottom = "0px";
			document.getElementById("offline"+obj).style.letterSpacing = "-1px";
			document.getElementById("offlinelist"+obj).style.display = "block";
		} else {
			document.getElementById("offline"+i).style.background = "#ffffff";
			document.getElementById("offline"+i).style.color = "#999999";
			document.getElementById("offline"+i).style.fontWeight = "400";
			document.getElementById("offline"+i).style.borderTop = "1px solid #e5e5e5";
			document.getElementById("offline"+i).style.borderLeft = "1px solid #e5e5e5";
			document.getElementById("offline"+i).style.borderRight = "1px solid #e5e5e5";
			document.getElementById("offline"+i).style.borderBottom = "1px solid #d3d3d3";
			document.getElementById("offline"+i).style.letterSpacing = "0";
			document.getElementById("offlinelist"+i).style.display = "none";
		}
	}
}

function tabnavi(obj){
	for(i=1; i<=10; i++){
		if (i == obj) {
			document.getElementById("tabnavi"+obj).style.background = "url('/images/2012/sub/bg_tabnavi_on.png')";
			document.getElementById("tabnavi"+obj).style.color = "#6c55bf";
			document.getElementById("tabnavi"+obj).style.fontWeight = "900";
			document.getElementById("tabnavi"+obj).style.fontSize = "14px";
			document.getElementById("tabnavi"+obj).style.lineHeight = "16px";
			document.getElementById("tabnavi"+obj).style.zIndex = "10";
		} else {
			document.getElementById("tabnavi"+i).style.background = "url('/images/2012/sub/bg_tabnavi.png')";
			document.getElementById("tabnavi"+i).style.color = "#ffffff";
			document.getElementById("tabnavi"+i).style.fontWeight = "400";
			document.getElementById("tabnavi"+i).style.fontSize = "12px";
			document.getElementById("tabnavi"+i).style.lineHeight = "14px";
			document.getElementById("tabnavi"+i).style.zIndex = "5";
		}
	}
}

function joinyearcheck(obj){
	if (obj == "on") {
		document.getElementById("year14min").style.display = "block";
		document.getElementById("year14max").style.display = "none";
	} else {
		document.getElementById("year14min").style.display = "none";
		document.getElementById("year14max").style.display = "block";
	}
}

function checktype(obj){
	for(i=1; i<=2; i++){
		if (i == obj) {
			document.getElementById("real"+obj).style.background = "url('/images/2012/sub/page0/tabon.gif')";
			document.getElementById("real"+obj).style.color = "#323232";
			document.getElementById("real"+obj).style.fontWeight = "600";
			document.getElementById("real"+obj).style.fontSize = "14px";
			document.getElementById("checkview"+obj).style.display = "block";
		} else {
			document.getElementById("real"+i).style.background = "url('/images/2012/sub/page0/taboff.gif')";
			document.getElementById("real"+i).style.color = "#888888";
			document.getElementById("real"+i).style.fontWeight = "400";
			document.getElementById("real"+i).style.fontSize = "13px";
			document.getElementById("checkview"+i).style.display = "none";
		}
	}
}

function choicefield(obj){
	for(i=1; i<=100; i++){
		if (document.getElementById("txtfield"+i)) {
			if (i == obj) {
				document.getElementById("txtfield"+obj).style.border = "2px solid #6092e8";
			} else {
				document.getElementById("txtfield"+i).style.border = "1px solid #bbbbbb";
			}
		}
	}
}

function faqview(obj){
	var getEldis = document.getElementById("answer"+obj);
	if(getEldis.style.display == "block") {
		getEldis.style.display = "none";
	} else {
		getEldis.style.display = "block";
	}
}

function faqallview(){
	for(i=0; i<=100; i++){
		if (document.getElementById("answer"+i)) {
			document.getElementById("answer"+i).style.display = "block";
		}
	}
}

function faqallclose(){
	for(i=0; i<=100; i++){
		if (document.getElementById("answer"+i)) {
			document.getElementById("answer"+i).style.display = "none";
		}
	}
}

function passwordchecktype(obj){
	for(i=1; i<=2; i++){
		if (i == obj) {
			document.getElementById("certtype"+obj).style.display = "block";
			document.getElementById("certmsg"+obj).style.display = "block";
			document.form1.gubun.value = obj;
			
		} else {
			document.getElementById("certtype"+i).style.display = "none";
			document.getElementById("certmsg"+i).style.display = "none";
			document.form1.gubun.value = obj;
		}
	}
}

function getlink(urls) {
	document.location.href = urls;
}

function showsite() {
	for(i=1; i<=100; i++){
		var sl = document.getElementById("url"+i);
		if(sl) {
			if (sl.style.display == "none" || sl.style.display == "") {
				sl.style.display = "block";
			} else {
				sl.style.display = "none";
			}
		}
	}
}


function infoopen(obj){
	for(i=1; i<=100; i++){
		if (document.getElementById("info"+i)) {
			if (i == obj) {
				document.getElementById("info"+obj).style.display = "block";
			} else {
				document.getElementById("info"+i).style.display = "none";
			}
		}
	}
}

function infoclose(obj){
	for(i=1; i<=100; i++){
		if (document.getElementById("info"+i)) {
				document.getElementById("info"+i).style.display = "none";
		}
	}
}


var nowZoom = 100; // 현재비율
var maxZoom = 200; // 최대비율
var minZoom = 100; // 최소비율

function zoomIn(){
	if(nowZoom < maxZoom) {
		nowZoom += 10; //25%씩 커진다.
	} else {
		return;
	}
	document.body.style.zoom = nowZoom + "%";
}

function zoomOut(){
	if(nowZoom > minZoom) {
		nowZoom -= 10; //25%씩 작아진다.
	} else {
		return;
	}
	document.body.style.zoom = nowZoom + "%";
}

function logout() {
    document.form1.p_process.value = "gologout";
    document.form1.action          = "/servlet/controller.homepage.LoginServlet";
    document.form1.submit();
}

var photoname = new Array();
//photoname[1] = "1F : S3D Training Center(1)";
//photoname[2] = "1F : S3D Training Center(2)";
//photoname[3] = "1F : S3D Training Center(3)";
//photoname[4] = "1F : S3D Training Center(4)";
//photoname[5] = "2F : S3D 입체편집실";
//photoname[6] = "3F : 회견장(1)";
//photoname[7] = "3F : 회견장(2)";
//photoname[8] = "3F : 회견장(3)";
//photoname[9] = "3F : 회의장(1)";
//photoname[10] = "3F : 회의장(2)";
//photoname[11] = "3F : 회의장(3)";
//photoname[12] = "4F : 디지털영상편집 1 강의실(1)";
//photoname[13] = "4F : 디지털영상편집 1 강의실(2)";
//photoname[14] = "4F : 디지털영상편집 2 강의실(1)";
//photoname[15] = "4F : 디지털영상편집 2 강의실(2)";
//photoname[16] = "4F : 디지털 음향 강의실(1)";
//photoname[17] = "4F : 디지털 음향 강의실(2)";
//photoname[18] = "4F : 모션그래픽 강의실(1)";
//photoname[19] = "4F : 모션그래픽 강의실(2) ";
//photoname[20] = "5F : S3D 공용 강의실(1)";
//photoname[21] = "5F : S3D 공용 강의실(2)";
//photoname[22] = "5F : VFX 강의실";
//photoname[23] = "7F : 게임 그래픽 강의실(1)";
//photoname[24] = "7F : 게임 그래픽 강의실(2)";
//photoname[25] = "7F : 게임 프로그래밍 강의실";
//photoname[26] = "7F : 게임 프로젝트 강의실";
//photoname[27] = "7F : 게임 프로젝트 개발실(1)";
//photoname[28] = "7F : 게임 프로젝트 개발실(2)";
//photoname[29] = "7F : 게임 기획 강의실";
//photoname[30] = "8F : 아카데미 도서관(1)";
//photoname[31] = "8F : 아카데미 도서관(2)";
photoname[1] = "1F : S3D Training Center(1)";
photoname[2] = "1F : S3D Training Center(2)";
photoname[3] = "1F : S3D Training Center(3)";
photoname[4] = "1F : S3D Training Center(4)";
photoname[5] = "2F : S3D Post-Production";
photoname[6] = "2F : S3D Pre-Production";
photoname[7] = "2F : S3D Production";
photoname[8] = "3F : 회견장(1)";
photoname[9] = "3F : 회견장(2)";
photoname[10] = "3F : 회견장(3)";
photoname[11] = "3F : 회의장(1)";
photoname[12] = "3F : 회의장(2)";
photoname[13] = "3F : 회의장(3)";
photoname[14] = "4F : Digital Editing Room I(1)";
photoname[15] = "4F : Digital Editing Room I(2)";
photoname[16] = "4F : Digital Editing Room II(1)";
photoname[17] = "4F : Digital Editing Room II(2)";
photoname[18] = "4F : Digital Editing Room II(3)";
photoname[19] = "4F : Digital Editing Room II(4)";
photoname[20] = "4F : 디지털 음향 강의실(1)";
photoname[21] = "4F : 디지털 음향 강의실(2)";
photoname[22] = "5F : Digital Editing Room";
photoname[23] = "5F : S3D Graphic Room";
photoname[24] = "5F : 멀티미디어강의실";
photoname[25] = "7F : 게임 그래픽 강의실";
photoname[26] = "7F : 게임 디자인 기획";
photoname[27] = "7F : 게임 세미나실";
photoname[28] = "7F : 게임 프로그래밍 강의실";
photoname[29] = "7F : 게임 프로젝트 강의실";
photoname[30] = "7F : 게임 프로젝트 개발실";
photoname[31] = "7F : 기획창작 강의실";
photoname[32] = "7F : 취업 상담실";
photoname[33] = "8F : 상담실";
photoname[34] = "8F : 아카데미 도서관(1)";
photoname[35] = "8F : 아카데미 도서관(2)";
photoname[36] = "8F : 아카데미 도서관(3)";
photoname[37] = "8F : 현업인 직무과정 강의실";
		
$(window).resize(function(){
	var reWidth = $(window).width();
	var reHeight = $(document).height();
	var rableposition = ($(window).scrollTop()+($(window).height())/7 );
	var closeWposition = ($(window).scrollLeft()+($(window).width())/2-43);
	var closeHposition = ($(window).scrollTop()+($(window).height())/1.07);
	$("#facilityview").css({ 'width':reWidth,'height':reHeight });
	$('#viewralbe').css('top',rableposition);
	$('img.viewclose').css('left',closeWposition);
	$('img.viewclose').css('top',closeHposition);
});

function facilityview(callimage){
	$(document).ready(function(){
		$.callimage = eval(callimage);
		var firstWidth = $(window).width();
		var tds = $('.viewimgline').width();
		var firstHeight = $(document).height();
		var rableposition = ($(window).scrollTop()+($(window).height())/7 );
		var imgsize = $('.viewclose').width();
		var closeWposition = ($(window).scrollLeft()+($(window).width())/2-43);
		var closeHposition = ($(window).scrollTop()+($(window).height())/1.07);
		$("#facilityview").css({ 'width':firstWidth,'height':firstHeight });
		$('#facilityview').fadeTo('nomal',0.85);
		$('#viewralbe').fadeTo('fast',1);
		$('#viewralbe').css('top',rableposition);
		$('img.viewclose').css('left',closeWposition);
		$('img.viewclose').css('top',closeHposition);
		$('img.viewclose').fadeTo('fast',1);
		$(".facilityImage").attr('src','/images/2012/sub/page7/facilityImage/'+callimage+'.jpg');
		$(".facilityImage").attr('alt',photoname[callimage]);
		$(".photoname").text(photoname[callimage]);
	});
}

function changefacilityimage(callcommand){
	$(document).ready(function(){		
		if(callcommand == 'next') {
			var vals = eval($.callimage+1);
			if(vals > 37){
				alert("마지막 이미지입니다.");
			} else {
				$.callimage = vals;
				$(".facilityImage").attr('src','/images/2012/sub/page7/facilityImage/'+vals+'.jpg');
				$(".photoname").text(photoname[vals]);
			}
		} else {
			var vals = eval($.callimage-1);
			if(vals < 1){
				alert("이전 이미지가 없습니다.");
			} else {
				$.callimage = vals;
				$(".facilityImage").attr('src','/images/2012/sub/page7/facilityImage/'+vals+'.jpg');
				$(".photoname").text(photoname[vals]);
			}
		}
	});
}

$(document).ready(function(){
	$("img.imageprev").mouseover(function(){
		$(this).attr('src','/images/2012/sub/page7/arrow_left_over.png');
	});
	$("img.imageprev").mouseout(function(){
		$(this).attr('src','/images/2012/sub/page7/arrow_left_normal.png');
	});
	
	$("img.imagenext").mouseover(function(){
		$(this).attr('src','/images/2012/sub/page7/arrow_right_over.png');
	});
	$("img.imagenext").mouseout(function(){
		$(this).attr('src','/images/2012/sub/page7/arrow_right_normal.png');
	});
	$("img.viewclose").click(function(){
		$('#facilityview').hide();
		$('#viewralbe').hide();
		$('img.viewclose').hide();
	});
});