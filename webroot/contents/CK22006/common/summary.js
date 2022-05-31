/////////// 정리하기 /////////////////////////

// 구현 스크립트
/*
snum
stitle
sumContents
*/

$(document).ready(function(){

	var curPageNum=1;
	var totalPageNum=pageView[currentChapter][pageView[currentChapter].length-1];

	//alert(pageView[currentChapter][pageView[currentChapter].length-1]);

	/*alert("totalPage:"+totalPageNum);*/

	$("#btnPrevsummary").click(function(){
		curPageNum=curPageNum-1;
		summaryView(curPageNum)
	});
	
	$("#btnNextsummary").click(function(){
		curPageNum=curPageNum+1;
		summaryView(curPageNum);
	});

	bNavSetting();
	
	var imgDefaultURL="../common/img/contentimg/";

	// 정리페이지 보여주기
	function summaryView(num){

		var subTag ="";	

		var cnt=0; 
		var mbimgcnt;

		for(var i=1;i<=pageView[currentChapter].length;i++){
			if(pageView[currentChapter][i-1]==num){
				if(cnt!=0){
					/*subTag=subTag+"</div>"*/
				}
				subTag=subTag+"<div id='sec'>";
				subTag=subTag+"<div id='secTitlenum'>"+i+"</div><div id='secTitle'> "+summaryTitle[currentChapter][i]+"</div>";
				/* imgFile: 을 만나면 이미지로 치환 */
				var repstr=summaryDesc[currentChapter][i];
				mbimgcnt=0;
				var repstrm=repstr.replace( /:mb:/g, function(){
					mbimgcnt++;
					return "<div id='mbimg'>"+mbimgcnt+"</div>";
				});
				//var repstrm=repstr.replace( /:mb:/g, '<div id="mbimg"></div>');
				var repstrs=repstrm.replace( /:sb:/g, '<div id="sbimg"></div>');
				var repstr1=repstrs.replace( /imgFile:/g, '<img src="'+imgDefaultURL+'');
				var repstr2=repstr1.replace( /.png/gi, '.png">');
			
				subTag=subTag+"<div id='secDesc'> "+repstr2+"</div></div>";
				//subTag=subTag+"<div id='secDesc'> "+summaryDesc[currentChapter][i]+"</div></div>";
			}
		}
		//alert(subTag);


		$("#snum").empty();
		$("#snum").append(num);
		
		//$("#stitle").empty();
		//$("#stitle").append(partTitle[currentChapter][num]);

		$("#sumContents").empty();
		$("#sumContents").append(subTag);

		// 하단 셋팅
				
		$("#txtCurPage").empty();
		$("#txtCurPage").append(curPageNum);

		$("#txtTotalPage").empty();
		$("#txtTotalPage").append(totalPageNum);
		
		bNavSetting();
		
	}


	function bNavSetting(){
		if(totalPageNum==1){
			$("#btnCenter").attr('style','visibility:hidden');
		}

		if(curPageNum == 1){
			$("#btnPrevsummary").attr('style','visibility:hidden');
		}else{
			$("#btnPrevsummary").attr('style','visibility:visible');
		}

		if(curPageNum ==  totalPageNum){
			$("#btnNextsummary").attr('style','visibility:hidden');
		}else{
			$("#btnNextsummary").attr('style','visibility:visible');
		}
	}


	
	summaryView(curPageNum);

	// 인쇄 관련
	var downloadUrl = "../down/down_"+itostr(currentChapter)+".zip";
	var printUrl = "../down/down_"+itostr(currentChapter)+".pdf";
	var isBrowserType = 0; //0:desktop, 1:Android, 2:Ios



	$("#printBtn").click(function(){
		
		/*
		
		var printTag="<div id='printWindow'>";
		var curpage=0;

		for(var i=1;i<=pageView[currentChapter].length;i++){
			if(pageView[currentChapter][i-1]!=curpage){
				curpage=curpage+1;
				printTag=printTag+"<div id='snum'>"+curpage+"</div><div id='stitle'>"+partTitle[currentChapter][curpage]+"</div>"
			}		
				printTag=printTag+"<div id='sec'>";
				printTag=printTag+"<div id='secTitle'> "+summaryTitle[currentChapter][i]+"</div>";
				printTag=printTag+"<div id='secDesc'> "+summaryDesc[currentChapter][i]+"</div></div>";
			
		}
		printTag=printTag+"</div>"
		
		$("#printArea").empty();
		$("#printArea").append(printTag);

		//printPage('printArea') // 하단 팝업 인쇄방식(작동안함)
		setTimeout(function(){ 
		var prWindow = document.getElementById("printWindow");
		prWindow.print(); 
			}, 1000);

*/
	
		if(isBrowserType != 0){
			alert("모바일에서는 지원하지 않습니다.");
			return;
		}
		
		var browserType = getBrowserType();
		if(browserType.indexOf("IE") != -1){ //IE
			$("#printArea").empty();
			$("#printArea").append('<object id="pdfWindow" width="0" height="0" type="application/pdf" data="'+printUrl+'" > </object>');
			
			setTimeout(function(){ 
					var pdfWindow = document.getElementById("pdfWindow");
					pdfWindow.print(); 
			}, 1000);
		}else if(browserType.indexOf("Firefox") != -1){ //Firefox
			
			var popOption = "width=700, height=700, resizable=no, scrollbars=no, status=no,toolbar=no,location=no;";    
			var pdfWindow = window.open(printUrl,"",popOption);
			setTimeout(function(){
				pdfWindow.print();
			}, 1000);
			
		}else { //chrome, safari, etc
			$("#printArea").empty();
			$("#printArea").append("<iframe id='pdfWindow' name='pdfWindow' width='0' height='0' name='pdf' hidden='true' style='display:none;'></iframe>");
			
			var pdf = window.open(printUrl, "pdfWindow");
			setTimeout(function(){
				var pdfWindow = document.getElementById("pdfWindow");
				//alert(pdfWindow);
				//pdfWindow.focus();
				//pdfWindow.print();
				pdfWindow.contentWindow.focus();
				pdfWindow.contentWindow.print();
			}, 1000);
		}
	
	});
	

	/*
	$("#downBtn").click(function(){
		if(isBrowserType != 0){
			alert("모바일에서는 지원하지 않습니다.");
			return;
		}
		
		var browserType = getBrowserType();
		if(browserType.indexOf("IE") != -1){ //IE
	
			var winObject = null;
			var settings ='toolbar=0,directories=0,status=no,menubar=0,scrollbars=auto,resizable=no,height=200,width=300,left=0,top=0';
			var url =downloadUrl;
			winObject = window.open("../down/down.htm?va="+url+"", "down", settings);

		}else{
			
		$("#printArea").empty();
		$("#printArea").append("<iframe id='pdfWindow' name='pdfWindow' width='0' height='0' name='pdf' hidden='true' style='display:none;'></iframe>");
		
	
		var pdf = window.open(printUrl, "pdfWindow");
		pdf.location.href = downloadUrl;
		}
	}
	
*/



});


function printPage(id)
{
   var html="<html>";
   html+= document.getElementById(id).innerHTML;

   html+="</html>";

   var printWin = window.open('','','left=0,top=0,width=1,height=1,toolbar=0,scrollbars=0,status  =0');
   printWin.document.write(html);
   printWin.document.close();
   printWin.focus();
   printWin.print();
   printWin.close();
}

