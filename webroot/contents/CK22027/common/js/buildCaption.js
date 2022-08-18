var captionToggle = "close";

$(document).ready(function(){
	createCaption();
});

function createCaption(){
	var html = '';
	html += '<div id="caption-wrap">';
	html += '<div id="caption-txt-wrap"></div>';
	html += '<button id="close-caption"><div id="close-caption-icon"></div></button>';
	html += '</div>';
	$("#captionUI").html(html);
	var _caption = captionInfo[(now_page-1)].caption;
	_caption = _caption.replaceAll('|','<br/>');
	$("#caption-txt-wrap").html('<div id="caption-txt">'+_caption+'</div>');
	$("#caption-txt").mCustomScrollbar({theme:"light-3"});
	$("#close-caption").on({
		mouseover : function(){
			var _id = $(this).attr('id');
			$(this).css({"transform":"rotate(180deg)","transition":"all ease 0.5s"}); 
			// $("#"+_id+"-icon").css({"background-position-x":"-"+$("#"+_id+"-icon").width()+"px"});
		},
		mouseout : function(){
			var _id = $(this).attr('id');
			$(this).css({"transform":"rotate(0deg)","transition":"all ease 0.5s"});
			// $("#"+_id+"-icon").css({"background-position-x":"0px"});
		},
		click : function(){
			caption_view();
		}
	});
	mobileBtnSet();
}

function caption_view(){
	if(captionToggle == "close"){
		captionToggle = "open";
		$("#captionUI").show();
		var _temp = ($("#caption-wrap").height()+$("#navigationUI").height())*-1;
		$("#caption-wrap").stop().animate({"margin-top" : "-138px"},300);
		// $("#caption").css({"background-position-x":"-"+$("#caption").width()+"px"});
	}else{
		captionToggle = "close";
		$("#caption-wrap").stop().animate({"margin-top":"0px"},300,function(){
			$("#captionUI").hide();
		});
		// $("#caption").css({"background-position-x":"0px"});
	}
}