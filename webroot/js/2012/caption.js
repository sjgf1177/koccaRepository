var _gaq = _gaq || [];
//_gaq.push(['_setAccount', 'UA-24782208-1']);
_gaq.push(['_setAccount', 'UA-33104445-1']);
_gaq.push(['_trackPageview']);

(function() {
  var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
  ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
  var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
})();

function goSubIndex() {    
    if(document.getElementById('p_project').value == ""){
        alert("년도를 선택해주세요");
        return;
    }
        
    window.open("/servlet/controller.community.CommunityIndexServlet?p_process=selectmyindex&p_cmuno="+document.getElementById('p_project').value, "openWinCommunity", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,resizable=no, width = 920, height = 660, top=0, left=0");
}
$(function() {
  $("a").focus(function(e) {             
	  this.t = this.title;             
	  this.title = "";
	  $("body").append("<div id='tooltip'>" + this.t + "</div>");
	  //alert(this.t);
  });
  $("a").blur(function(e) {             
	  this.title = this.t;
	  $("#tooltip").remove();                   
	});
	$("a").mouseover(function(e) {             
	  this.t = this.title;             
	  this.title = "";
	  $("body").append("<div id='tooltip'>" + this.t + "</div>");
	  //alert(this.t);
  });
  $("a").mouseout(function(e) {
	  this.title = this.t;
	  setTimeout(mousestll,2000);    
	});
	$("button").mouseover(function(e) {
	  this.t = this.title;             
	  this.title = "";
	  $("body").append("<div id='tooltip'>" + this.t + "</div>");
	  //alert(this.t);
  });
  $("button").mouseout(function(e) {             
	  this.title = this.t;
	  setTimeout(mousestll,2000);                
	});
	function mousestll() {
		$("#tooltip").remove();
	}
	$("input").focus(function(e) {             
	  this.t = this.title;             
	  this.title = "";
	  $("body").append("<div id='tooltip'>" + this.t + "</div>");
	  //alert(this.t);
  });
  $("input").blur(function(e) {             
	  this.title = this.t;
	  $("#tooltip").remove();                   
	});
	$("button").focus(function(e) {             
	  this.t = this.title;             
	  this.title = "";
	  $("body").append("<div id='tooltip'>" + this.t + "</div>");
	  //alert(this.t);
  });
  $("button").blur(function(e) {             
	  this.title = this.t;
	  $("#tooltip").remove();                   
	});
});