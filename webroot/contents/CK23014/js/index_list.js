$(window).load(function(){

    /*
    var chapNameSet=new Array();
    chapNameSet[1]="계약과 협상의 기초";
    chapNameSet[2]="저작권과 라이선스 이해하기 (1)";
    chapNameSet[3]="저작권과 라이선스 이해하기 (2)";
    chapNameSet[4]="IP 확장하는 법";
    chapNameSet[5]="연재와 저작권 이용의 대가 정하기";
	chapNameSet[6]="원고 창작 과정에서 제작사와 원활하게 소통하기";
	chapNameSet[7]="계약의 종료와 분쟁 해결하기";
	*/

    /*
	** 포팅시 index(페이지명) 수정
	*/ 
    var chapNameSet=[
		//['생략','1차시 1페이지','2차시 1페이지'],
        //['생략','1차시 2페이지','2차시 2페이지'], ...
		
        [], // 생략
        ['','인트로','인트로','인트로','인트로','인트로','인트로','인트로'], //1페이지
        ['','들어가기','들어가기','아웃트로','들어가기','들어가기','들어가기','들어가기'],
        ['','만화웹툰 분야 표준계약서','저작권과 라이선스 이해하기(1)','저작권과 라이선스 이해하기(2)','IP 확장하는 법','연재와 저작권 이용의 대가 정하기','원고 창작 과정에서 제작사와 원활하게 소통하기','계약의 종료와 분쟁 해결하기'],
        ['','아웃트로','아웃트로','아웃트로','아웃트로','아웃트로','아웃트로','아웃트로'],


    ]


    //차시목록 생성
    function chapternameSet(){
	
      var lecnt=Math.round((chapNameSet.length-1)/2);
      var chapContent = $("#chapnavigation");
      var chapterStart = chapter.replace('0', '');    // 배열에 사용되는 차시
        console.log('chapterStart:' + chapterStart);

      
      if(chapContent){
        
        chapContent.empty();
        var learningString ="";
        //learningString ="<div id='learningUnit'>"
        //alert(chapNameSet.length);
        for ( var i = 1; i < chapNameSet.length; i++) {
          if(i==1 || i==(lecnt+1)){
            learningString  += "<ul id='learningSection'>";
            
          }
          learningString  += "<li id='learningUnit'>";
        
          /*if(i!=chapter){
            learningString  += "<div><span>"+i+'.'+"</span><a id='ltxt' href=../"+ chapter + '/' + `${i}`.padStart(2,'0') + '.html' +" >"+chapNameSet[i]+"</a></div>";
          }else{
            learningString  += "<div><span class='ltxton'>"+i+'.'+"</span><a class='ltxton' href=../"+ chapter + '/' + `${i}`.padStart(2,'0') + '.html' +" >"+chapNameSet[i]+"</a></div>";
          }*/

          learningString  += "<div><span class='ltxton'>"+i+'.'+"</span><a class='ltxton' href=../"+ chapter + '/' + `${i}`.padStart(2,'0') + '.html' +" >"+chapNameSet[i][chapterStart]+"</a></div>";

          learningString +="</li>";
  
          if(i==lecnt || i==chapNameSet.length){
            learningString  += "</ul>";
          }
        }
        //learningString +="</div>"
        //alert(learningString);
        var indexurl = "<a class='ltxton' href=../"+ chapter + '/' + `${i}`.padStart(2,'0') + '.html' +" >";
        chapContent.append(learningString);
		console.log("index 경로 : " + indexurl);
        
      }
  
    }

    // 차시목록 창
    $('.u_chapter').bind('click',function(){

        var h = $('#chapnavigation').outerHeight();
        chapternameSet();
        if ($(this).is('on')){
			
            $('#chapterNavi').stop().animate({'left':'-999px'},400);
            $(this).removeClass('on');
			
        } else {
			
            $('#chapterNavi').stop().animate({'left':'0'},400);
            $(this).addClass('on');


        }

    });

});

