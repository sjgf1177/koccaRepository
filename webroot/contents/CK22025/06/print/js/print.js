$(document).ready(function() {
    $('.print_btn').on("click",function(){ 
        window.open("./print/print.html","printPop","left=50,top=50,width=800,height=600,scrollbars=yes");
    });

    $(".scriptWrap").on('mousewheel', function(e){ 
        if(e.originalEvent.wheelDelta < 0) { 
            $(".scriptWrap").stop().animate({ scrollTop : '+=33px' },100); 
        }
        else { 
            $(".scriptWrap").stop().animate({ scrollTop : '-=33px' },100); 
        }
        return false; 
    });
});