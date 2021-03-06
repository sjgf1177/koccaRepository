////////////////////////////////////////////
//
// 페이지 정보 셋팅파일 (차시 페이지수, 과정명, 차시명, 절명, 절메뉴명 등)
// 과정사이즈 : 1280 * 754 버전
/////////////////////////////////////////////
//현재챕터
ichip = location.href.split("/");

var currentChapter = parseInt(ichip[ichip.length-2],10);	
var currentClau=parseInt(ichip[ichip.length-1].split(".html")[0].substr(0,2),10);
var currentClauPage=parseInt(ichip[ichip.length-1].split(".html")[0].substr(4,2),10);

//
var volume = 1;

// 과정명
var subtitle = "문화예술계 성평등ㆍ성폭력 예방교육";


// 차시명
var subject;

// 챕터 갯수
var totalChapNum = 5;

// 모듈 시작 차시(타이틀과 연동) : 이과정에선 적용X
var moduleNum=[1,2,4,6,10,13];

// 현재모듈
var currentModule;
for(var i=moduleNum.length;i>=0;i--){
	if(currentChapter>=moduleNum[i]){
		currentModule=i+1;
		break;
	}
}

// 동영상페이지 번호
var movieClau=3;

// 코드(북마크 구분용)
var bmkCode="2022_KPC_kwafcon_1";

/////////////  페이지정보

var subjectpage = new Array();


  subjectpage[1] =  new Array();
  subjectpage[1][1] = "01";
  subjectpage[1][2] = "01";
  subjectpage[1][3] = "04"; 
  subjectpage[1][4] = "01";
  subjectpage[1][5] = "01";


  for(var i=2;i<=4;i++){
  subjectpage[i] =  new Array();
  subjectpage[i][1] = "01";
  subjectpage[i][2] = "01";
  subjectpage[i][3] = "03"; 
  subjectpage[i][4] = "01";
  subjectpage[i][5] = "01";
  }

  subjectpage[5] =  new Array();
  subjectpage[5][1] = "01";
  subjectpage[5][2] = "01";
  subjectpage[5][3] = "02"; 
  subjectpage[5][4] = "01";



/////////////////////// 차시명

var chapNameSet=new Array();


chapNameSet[1]="우리 사회의 불편한 진실";
chapNameSet[2]="문화예술계 성 차별적 구조와 성희롱·성폭력의 특성";
chapNameSet[3]="문화예술계 성희롱·성폭력 사례와 판례";
chapNameSet[4]="문화예술계 2차 피해 예방과 피해자 지원제도";
chapNameSet[5]="문화예술계 성평등한 창작환경 조성을 위한 약속";


var subject = chapNameSet[currentChapter];


//////////////////////// 절명

var clauNameSet=new Array();

for(var i=1;i<=totalChapNum;i++){
	clauNameSet[i]=new Array();
	clauNameSet[i][1]="Intro";
	clauNameSet[i][2]="Goal";
	clauNameSet[i][3]="Study";
	if(i!=5){
	clauNameSet[i][4]="Quiz";
	clauNameSet[i][5]="Outro";
	}else{
	clauNameSet[i][4]="Outro";
	}
}



var clauNumber=clauNameSet[currentChapter].length-1;

//alert(clauNumber);

//////////////////////  네비 명 (왼쪽 네비에 노출되는 이름, 동영상mp4, 나레이션mp3 파일이름과도 연동됨)


var menuNaviNameSet=new Array();



for(var i=1;i<=totalChapNum;i++){
	menuNaviNameSet[i]=new Array();
	menuNaviNameSet[i][1]="Intro";
	menuNaviNameSet[i][2]="Goal";
	var mvnum=parseInt(subjectpage[i][movieClau],10)
	for(var j=1;j<=mvnum;j++){
		menuNaviNameSet[i][movieClau+j-1]="Study";
	}
		if(i!=5){
	menuNaviNameSet[i][movieClau+mvnum]="Quiz";
	menuNaviNameSet[i][movieClau+mvnum+1]="Outro";
		}else{
	menuNaviNameSet[i][movieClau+mvnum]="Outro";
		}
}

/////////////////////////////////////////////////
// 현재차시 총 페이지수 구하기

function itostr(num) {
        if (num < 10) str = "0";
        else str = "";

        str = str + num;
        return str;
}

var totalPageSet=new Array();

for(i=1;i<subjectpage.length; i++){
	pagenum = 0;
	for(j=1;j<subjectpage[i].length;j++){
		for(k=1;k<=parseInt(subjectpage[i][j],10);k++){
			pagenum++;		
		}
	}
	totalPageSet[i-1]=pagenum;
}

var totalPage = totalPageSet[currentChapter-1];

//////////////////////////////////////////////////
// 현재 차시 현재 페이지 수 구하기

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

////// 쿠키읽기

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

this_info = ichip[ichip.length-1].split(".html")[0];	
sinf = ichip[ichip.length-2]+this_info;

 ichap = parseInt(sinf.charAt(0) + sinf.charAt(1),10);
 iclau = parseInt(sinf.charAt(2) + sinf.charAt(3),10);
 isubject = parseInt(sinf.charAt(4) + sinf.charAt(5),10);
 ipage = parseInt(sinf.charAt(6) + sinf.charAt(7),10);

 curpage = ipage;
totalp = 0;

  for (pi=1; pi<iclau; pi++) {
        totalsubject = parseInt(subjectpage[ichap][pi],10);
		curpage = curpage+totalsubject;
}

var currentPage=curpage;
parent.pageChk(currentChapter, currentPage);