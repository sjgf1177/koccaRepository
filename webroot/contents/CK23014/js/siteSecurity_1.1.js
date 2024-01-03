/*************
name : siteSecurity.js
************/
(function($){
	var opt,ele;

	$.fn.siteSecurity = function(option){
		ele = $(this);
		opt = option;

		if(opt.exceptionip!=''){
			$.get("http://ipinfo.io", function(res) {
				resconverter(res.ip);
			}, "jsonp");
		}else{
			resconverter(false);
		}
		

		function resconverter(ip){
			if(opt.exceptionip!=ip || ip===false){
			
				if(opt.f12=='y'){
					ele.bind('keydown',function(e){
						if(e.keyCode==123){
							e.preventDefault();
						}
					});
				}
				
				if(opt.rightclick=='y'){
					$(document).bind("contextmenu", function(){return false;});
				}

				if(opt.select=='y'){
					$(document).bind('selectstart',function() {return false;}); 
				}

				if(opt.drag=='y'){
					$(document).bind('dragstart',function(){return false;});	
				}
			}
		}

		
	}
})(jQuery);