//config
var pageInfo	= new Array();  // 페이지 정보
pageInfo[1]	= {tPage:5, popup:"", interactive:"",quiz:0,slider:0};
pageInfo[2]	= {tPage:5, popup:"", interactive:"",quiz:0,slider:0};
pageInfo[3]	= {tPage:5, popup:"", interactive:"",quiz:0,slider:0};
pageInfo[4]	= {tPage:5, popup:"", interactive:"",quiz:0,slider:0};
pageInfo[5]	= {tPage:5, popup:"", interactive:"",quiz:0,slider:0};
pageInfo[6]	= {tPage:5, popup:"", interactive:"",quiz:0,slider:0};

// interactive: 상호작용페이지 ex) interactive:"7,9,10"

// pageInfo[차시명][페이지수] = {inner: "팝업내페이지개수", link:"링크", down: ""}
//pageInfo[1][7] = {inner: "", link:"http://www.socialenterprise.or.kr/index.do", down:""};

var bolPorted = true; // 포팅여부
var totalUiHide = true; // 전체 ui 숨기기 체크
var bolPLock = false; // 메뉴 잠금 여부
var bolTLock = false; // 제어바 잠금 여부
var subjectCode = "CB22005"; // 과정코드
var strInitial = "1-1_";                // 과정 이니셜
var strYear = '2021';			         	// 제작 년도
var strMaking = 'STORM';		      	// 제작회사
var strClient = 'kocca';		    // 발주처
var menuType  = "page"  // 메뉴타입 ("page : 페이지이동  time : 시간이동")
var scriptVersion = "scroll"  // 자막 버전 ("scroll : 스크롤 자막  twoLine : 두줄 자막")
var scriptType = "jump"  // 자막 검색 타입 ("jump : 화면이동  search : 자막검색")
var strChapter = getURL(1); // 현재 차시(문자)
var strPage = getURL(0);    // 현재 페이지(문자)
var osType = getOSType();                 // 사용 os
var browserType =  getBrowserType();      // 사용 browser
var mobileOS = (osType == "iPhone OS" || osType =="Android OS");
var curChapter = parseInt(strChapter); // 현재 차시(정수)
var curPage = parseInt(strPage);    // 현재 페이지(정수)
var totalPage = pageInfo[curChapter].tPage; //전체 페이지 수
var mediaSpd = 1;         // 비디오 초기 속도
var lastIndex = 200;      // 탭 시작 번호
var contentScale = 1;     // 콘텐츠 스케일 최대 1
var quizPage = false;     // 현재 페이지가 퀴즈(상호작용)페이지인지 체크
var itrArr = pageInfo[curChapter].interactive.split(","); // 상호작용 페이지
var fullBol = false;
var itrBol = false; //상호작용 페이지 여부
var bolLock;
for(var i=0; i<= itrArr.length; i++){
  var tmpItr = itrArr[i] * 1;
  if(tmpItr == curPage) itrBol = true;
}
var popArr = pageInfo[curChapter].popup.split(","); // 내부팝업 페이지
var popBol = false; //내부팝업 페이지 여부
for(var j=0; j<= popArr.length; j++){
  var tmpPop = popArr[j] * 1;
  if(tmpPop == curPage) popBol = true;
}
var uiHide = false; //ui 숨기기 체크
var contentZoomScale = 1;