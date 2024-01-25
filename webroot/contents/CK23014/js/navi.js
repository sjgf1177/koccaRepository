
///////////////////////// HTML 설정 ///////////////////////////////////////////////////////////////////////
var topTitle = "콘텐츠 타이틀 명 "; // 브라우저 타이틀
//document.title = topTitle;

var buttonclick_key;

var prefix_page_move = "0";

var File_length = "2"; // 파일길이
var Dir_length ="2";   // 디렉토리 길이
var File_makeName = ".html"; // 확장자

var first_cut = Number(File_length) + 3
var secon_cut = first_cut - 2

var content_url2 = document.URL.split(File_makeName)[0];
var content_dir = Number(content_url2.substring(content_url2.length-first_cut,content_url2.length-secon_cut));
var content_pag = Number(content_url2.substring(content_url2.length-2,content_url2.length));




function itostr(num) {
if (num < 10) str = "0";
else str = "";
str = str + num;
return str;
}


///////////////////////// 영상경로 설정 ///////////////////////////////////////////////////////////////////////
var nowIndex = document.URL.split(File_makeName)[0];
var chapter = nowIndex.substring(nowIndex.length-5,nowIndex.length-3); // 차시
var page = nowIndex.substring(nowIndex.length-2,nowIndex.length); // 페이지



/*
** 포팅시 수정
*/
var subjectCode = "CK23014"; // 과정코드

//var mURL = "../mp4"; //로컬
var mURL = "https://edu_kocca.ecn.cdn.infralab.net/kocca/mp4/onlineclass"; //서버 영상경로
var mp4_Name = mURL +"/" + subjectCode + "/" + chapter+ "/" + chapter + "_" + page + ".mp4"; // 01/01_01.mp4
//var mp4_Name = mURL +"/" + subjectCode + "/" + chapter+ "/" +  page + ".mp4"; // 01/01.mp4
//var mp4_Name = mURL +"/" + subjectCode + "/" + chapter + "_" + page + ".mp4"; // 01_01.mp4
//var mp4_Name = mURL +"/" + subjectCode + "/" + chapter + ".mp4";  // 01.mp4
console.log("영상경로 : " + mp4_Name);



///////////////////////// 차시별 페이지 설정 ///////////////////////////////////////////////////////////////////////

/*
** 포팅시 수정
*/
var tot_page = new Array();
// EX) 총 5페이지 = 05  / 총 10페이지 = 10 
// 1차시 10페이지 = tot_page[차시번호]="페이지수";
tot_page[1]="04";
tot_page[2]="04";
tot_page[3]="04";
tot_page[4]="04";
tot_page[5]="04";
tot_page[6]="04";
tot_page[7]="04";
























//페이지 이동 버튼 생성
function page_movebtn(){
	
	//이전페이지 이동 버튼
	$(".jp-controls").prepend("<a href='javascript:prev(" + page + ");' class='pagePrev motoggle tooltip' title='이전'><span class='sub_tooltip'>이전</span></a>");
	//다음페이지 이동 버튼
	$(".jp-cur-forward.tooltip").after("<a href='javascript:;' class='nextFunc pageNext motoggle tooltip' data-page='" + page + "'title='다음'><span class='sub_tooltip'>다음</span></a>");
};

page_movebtn();

/*****************************************************************************************/ 
// 페이지 이동 함수
/*****************************************************************************************/
var nextEnd = true; //false 진도제어 기능 적용, true 진도제어 기능 해제


function next(n){
	var m = n;
	
	if ( m == tot_page[content_dir] )
	{	
		alert("마지막 페이지 입니다.");
		
	}
	else
	{
		next_page = content_pag + 1
		//location.href = prefix_page_move + itostr(next_page) + File_makeName; // 001.html
		location.href =  itostr(next_page) + File_makeName; // 01.html
	}
}

function prev(n){
	if( n == 1 )
	{
		alert("처음 페이지 입니다.");
	}
	else
	{
		prev_page = content_pag - 1
		//location.href = prefix_page_move + itostr(prev_page) + File_makeName; // 001.html
		location.href = itostr(prev_page) + File_makeName; // 01.html
	}
}

if(nextEnd == true){ $(".jp-limit").hide();}; //프로그래스바 제어

//다음페이지 버튼
$('.nextFunc').bind('click',function(){
	
	var mypage = parseInt($(this).attr('data-page'));
	if (nextEnd == true){
		next(mypage);
		
	} else {
		alert("학습을 완료해주세요.");
		return false;
	}

});



/*****************************************************************************************/ 
// 인덱스 페이지 이동함수
/*****************************************************************************************/
function Menu_num (n){
	//alert("Move")
	if (n < 10){
		url = "0" + n + ".html";
	}else{
		url = n + ".html";
		}
	location.href = url;
}
//페이지
function page_num(n){
	document.fvar.setVariable("frame_num", n);
}

// 차시이동
function next_chp(n){
		urlChap = "../"+ n +"/index.htm"
		location.href = urlChap;

}




$(window).load(function(){

/*****************************************************************************************/ 
//페이징번호 설정
/*****************************************************************************************/
	function pageing(p){

		if(chapter < 10) {
			var strchapter = chapter.substring(1); // 10차시 미만일때
			
		} else {
			var strchapter = chapter;
		}
		
		var totalpage = tot_page[strchapter];
		
		//alert(strchapter);
		$("#paging").append("<span><strong>"+ page +"</strong>"+" / "+ totalpage +"</span>");
		
	};
	pageing();

});