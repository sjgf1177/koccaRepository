/////////////////////////////////////////////////////////////////////
//
// 페이지 내용 정의 js 파일 (퀴즈는 quizText.js에서 정의)
//
///////////////////////////////////////////////////////////////////////



///////////////////////// 강사소개 (사용하지 않음)

var lecturerDesc="- 네오교육연구소 대표<br>- 임팩트그룹코리아 이사<br>- MIT컨설팅그룹 교육 컨설턴트<br>- 주요 강의분야 : <br>&nbsp; 기획력과 보고력, 프레젠테이션, 협상, 상담 기법 등";

//var lecturerDesc1="";
//var lecturerDesc2="평창동계올림픽조직위(글로벌매너, 국제매너, 친절서비스 강의)<br>포스코 인재개발원(긍정조직 활성화, 긍정리더십 강의)<br>새마을금고 인재개발원(변화주도리더십, 목표비전만들기 강의)<br>한국교원대학교(교장자격연수 학교장의 갈등관리 강의)<br>한국생산성본부(리더십, 고객접점 커뮤니케이션 강의)<br>IBK 기업은행(전직원 스마트 친절서비스 강의)<br>청와대 경호실 경호팀(긍정리더십 강의)";

var lecturerName="송 민 규";
	
	
//////////////////////  학습목표, 주제  ///////////////////////////

/* 본 과정은 영상으로 해당 내용 대체함 */
// 학습내용 (최대 4개)
var subjectNameSet=new Array();


subjectNameSet[1]=new Array();
subjectNameSet[1][1]="위드 코로나 이후 변화된 사회";
subjectNameSet[1][2]="위드 코로나 이후 변화되는 세계경제의 트렌드";
subjectNameSet[1][3]="위드 코로나 이후 한국경제의 트렌드";


// 학습목표 (최대 4개)

var targetNameSet=new Array();

targetNameSet[1]=new Array();
targetNameSet[1][1]="위드 코로나 이후 변화된 사회 모습을 파악할 수 있다.";
targetNameSet[1][2]="위드 코로나 이후 변화되는 세계경제의 트렌드를 파악할 수 있다.";
targetNameSet[1][3]="위드 코로나 이후 한국경제의 트렌드를 파악할 수 있다.";



////////////////////////  의견입력(사용하지 않음) ///////////////////////////


var opinionList=new Array();

opinionList[1]="업무를 수행하면서 겪게 되는 문제를 해결하기 위해서 가장 먼저 해야 하는 일은<br>자신이나 자신이 속한 조직의 문제를 인식하는 일입니다. <br>최근 자신과 혹은 조직에서 어떤 문제가 발생 했는지 생각해봅시다.";
opinionList[2]="현재 우리 조직을 더 결속력 있게 만들려면 어떻게 해야 할 지 생각해봅시다.";
opinionList[3]="내가 혹은 우리 조직이 발상의 전환을 통해 문제 해결을 했던 사례가 있다면<br>이야기 해 보세요.";
opinionList[4]="최근 일어난 문제해결 과정에 발생했던 방해요소가 무엇인지 생각해보세요.";
opinionList[5]="내가 속한 조직에서 창의적 사고를 발휘하여 문제를 해결했던 사례를 생각해보세요.";

opinionList[6]="“우리의 성공적인 대학생활”을 주제로 자유 연상법을 활용하여 A4지 한 면을 채워보세요.";
opinionList[7]="논리적 사고를 통해 타인을 설득했던 경험에 대해 논의해보세요.";
opinionList[8]=""; 
opinionList[9]="현재 본인이 맡고 있는 조직에서, 혹은 대인관계에서 겪고 있는 문제 상황 한 가지를 <br>선정해서 논리적인 사고의 과정을 만들어 보세요.";
opinionList[10]="이 수업을 받기 전 비판적 사고에 대한 본인의 오해와 수업 이후 깨달은 바에 대해<br>논의해 보세요.";

opinionList[11]="사소한 일을 그냥 지나치지 않으며 의문을 가지고 지속적으로 관심을 가졌던<br>경험에 대해 논의해 보세요.";
opinionList[12]="본인이 문제처리능력을 발휘하여 성공적으로 문제를 해결한 경우와 <br>성공적으로 문제를 해결하지 못한 경우에 대해 생각해 보세요.";
opinionList[13]="최근 본인의 생활 중 문제라고 인식되는 부분에 대해 생각해보고 <br>해결해야 할 문제들을 분석하여 명확히 하는 과정을 적용해 보세요.";
opinionList[14]="본인이 과거에 수행했던 혹은 해결했던 문제에 대해서 평가하고, 피드백하여<br>조금 더 나은 개선점을 만들어냈던 사례를 생각해 보세요.";
opinionList[15]="";

/////////////////////// 정리하기 (사용하지 않음) ///////////////////

// Part (정리하기 페이지 단위) -> Section -> TITLE , DESC 
 

// 정리 타이틀
var summaryTitle=new Array();

summaryTitle[1]=new Array();
summaryTitle[1][1]="";
summaryTitle[1][2]="";

summaryTitle[2]=new Array();
summaryTitle[2][1]="";
summaryTitle[2][2]="";

summaryTitle[3]=new Array();
summaryTitle[3][1]="문제의 유형";
summaryTitle[3][2]="분석적 사고";
summaryTitle[3][3]="발상의 전환";
summaryTitle[3][4]="내·외부자원의 효과적 활용";

summaryTitle[4]=new Array();
summaryTitle[4][1]="문제해결의 장애요소";
summaryTitle[4][2]="문제해결방법의 종류";

summaryTitle[5]=new Array();
summaryTitle[5][1]="창의적 사고의 정의";
summaryTitle[5][2]="창의적 사고의 특징";

summaryTitle[6]=new Array();
summaryTitle[6][1]="자유연상법(브레인 스토밍)";
summaryTitle[6][2]="강제연상법(체크리스트)";
summaryTitle[6][3]="비교발상법(NM법, 시네틱스(Synectics)법)";

summaryTitle[7]=new Array();
summaryTitle[7][1]="논리적 사고";
summaryTitle[7][2]="논리적 사고의 요소";

summaryTitle[9]=new Array();
summaryTitle[9][1]="논리적 사고의 개발방법";
summaryTitle[9][2]="피라미드 방법";
summaryTitle[9][3]="So What 방법";

summaryTitle[10]=new Array();
summaryTitle[10][1]="비판적 사고";
summaryTitle[10][2]="비판적 사고에서 요구되는 태도";
summaryTitle[10][3]="비판적 사고의 방법";

summaryTitle[11]=new Array();
summaryTitle[11][1]="논증의 오류";
summaryTitle[11][2]="문제의식과 고정관념의 타파";

summaryTitle[12]=new Array();
summaryTitle[12][1]="";
summaryTitle[12][2]="1) 문제 인식";
summaryTitle[12][3]="2) 문제 도출";
summaryTitle[12][4]="3) 원인 분석";
summaryTitle[12][5]="4) 해결안 개발";
summaryTitle[12][6]="5) 실행 및 평가";

summaryTitle[13]=new Array();
summaryTitle[13][1]="문제인식";
summaryTitle[13][2]="문제도출";

summaryTitle[14]=new Array();
summaryTitle[14][1]="원인분석";
summaryTitle[14][2]="해결안 개발";
summaryTitle[14][3]="실행 및 평가";


// 정리  내용

var summaryDesc=new Array();

summaryDesc[1]=new Array();
summaryDesc[1][1]="- 업무를 수행함에 있어서 <span>답을 요구하는 질문이나 의논하여 해결해야 되는 사항</span>을 의미<br><br>- 해결하기를 원하지만 실제로 해결해야 하는 방법을 모르고 있는 상태나<br>&nbsp;&nbsp;얻고자 하는 해답이 있지만 그 해답을 얻는데 필요한 일련의 행동을 알지 못한 상태";
summaryDesc[1][2]="<br>imgFile:c11.png";

summaryDesc[2]=new Array();
summaryDesc[2][1]="<br>imgFile:c21.png";
summaryDesc[2][2]="<span>문제해결</span>이란 목표와 현상을 분석하고, 이 분석 결과를 토대로 과제를 도출하여<br>최적의 해결책을 찾아 실행, 평가해 가는 활동을 의미하며<br>문제해결 과정을 통해 <span>조직, 고객, 자신</span>의 세 가지 측면에서 도움을 줄 수 있음";

summaryDesc[3]=new Array();
summaryDesc[3][1]="현재 당면하고 있는 문제와 그 해결방법에 그치는 것이 아니라<br><span>그 문제와 해결방안이 상위 시스템과 어떻게 연결되어 있는지를 생각</span>하는 것";
summaryDesc[3][2]="<span>전체를 각각의 요소로 나누어 그 요소의 의미를 도출한 다음 우선순위를 부여</span>하고<br>구체적인 문제해결방법을 실행";
summaryDesc[3][3]="기존에 가지고 있는 <span>인식의 틀을 전환하여 새로운 관점</span>에서 바라보는 사고 지향";
summaryDesc[3][4]="문제해결 시 <span>필요한 기술, 재료, 방법, 사람 등 자원 확보 계획을 수립</span>하고 내·외부자원을 활용";

summaryDesc[4]=new Array();
summaryDesc[4][1]="- 효과적인 문제해결을 위한 장애요소로 고정관념에 얽매이는 경우<br>- 쉽게 떠오르는 단순한 정보에 의지하는 경우<br>- 너무 많은 정보를 수집하려고 노력하는 경우";
summaryDesc[4][2]="- 소프트 어프로치 : 직접적 표현이 아닌, 무언가를 시사하거나 암시를 통하여<br><span class='tab'>&#9;&#9;&#9;</span>&nbsp;&nbsp;&nbsp;&nbsp;의사를 전달하고 기분을 서로 통하게 함으로써 문제해결을<br><span class='tab'>&#9;&#9;&#9;</span>&nbsp;&nbsp;&nbsp;&nbsp;도모하는 방법<br>- 하드 어프로치 : 서로의 생각을 주장하고, 논쟁이나 협상을 통해<br><span class='tab'>&#9;&#9;&#9;</span>서로의 의견을 조정해가는 문제해결 방법<br>- 퍼실리테이션 : 깊이 있는 커뮤니케이션을 통해 서로의 문제점을 이해하고<br><span class='tab'>&#9;&#9;&#9;</span>공감함으로써 창조적으로 문제를 해결하는 방법";

summaryDesc[5]=new Array();
summaryDesc[5][1]="- 창의적 사고란 당면한 문제를 해결하기 위해 이미 알고 있는 경험지식을 해체하여<br>새로운 아이디어를 다시 도출하는 것으로, 개인이 가지고 있는 경험과 지식을 통해<br>새로운 가치 있는 아이디어로 다시 결합함으로써 참신한 아이디어를 산출하는 힘이다.";
summaryDesc[5][2]="- 창의적 사고란 정보와 정보의 조합이다.<br>- 창의적 사고는 사회나 개인에게 새로운 가치를 창출한다.<br>- 창의적 사고는 창조적인 가능성이다.";

summaryDesc[6]=new Array();
summaryDesc[6][1]="- 어떤 생각에서 다른 생각을 계속해서 떠올리는 작용을 통해<br>&nbsp;&nbsp;어떤 주제에서 생각나는 것을 계속해서 열거해 나가는 방법";
summaryDesc[6][2]="- 각종 힌트에서 강제적으로 연결 지어서 발상하는 방법";
summaryDesc[6][3]="- 주제와 본질적으로 닮은 것을 힌트로 하여 새로운 아이디어를 얻는 방법<br>- 이 때 본질적으로 닮은 것은 단순히 겉만 닮은 것이 아니고<br>&nbsp;&nbsp;힌트와 주제가 본질적으로 닮았다는 의미";

summaryDesc[7]=new Array();
summaryDesc[7][1]="- 논리적 사고는 사고의 전개에 있어서 전후의 관계가 일치하고 있는가를 살피고, <br>&nbsp;&nbsp;아이디어를 평가하는 능력을 의미한다.<br>- 이러한 논리적 사고는 다른 사람을 공감시켜 움직일 수 있게 하며, <br>&nbsp;&nbsp;짧은 시간에 헤매지 않고 사고할 수 있게 한다.<br>- 또한 행동을 하기 전에 생각을 먼저 하게 하며, 주위를 설득하는 일이 훨씬 쉬워진다.";
summaryDesc[7][2]="- 생각하는 습관<br>- 상대 논리의 구조화<br>- 구체적인 생각<br>- 타인에 대한 이해<br>- 설득";

summaryDesc[9]=new Array();
summaryDesc[9][1]="- 상위 개념으로부터 하위 개념을 분류해 나가는 방법과<br>&nbsp;&nbsp;하위 개념으로부터 상위 개념을 만들어가는 두 가지 방법이 있다.<br>- 논리적 사고를 위해서는 상,하위 개념 간에 비약이나 오류가 없어야 한다.";
summaryDesc[9][2]="- 하위의 사실이나 현상부터 사고함으로써 상위의 주장을 만들어가는 방법";
summaryDesc[9][3]="- “그래서 무엇이지?”하고 자문자답하는 의미로,<br>&nbsp;&nbsp;눈앞에 있는 정보로부터 의미를 찾아내어, 가치 있는 정보를 이끌어 내는 사고로서<br>&nbsp;&nbsp;단어나 제언만으로 표현하는 것이 아니라, 주어와 술어가 있는 글로 표현함으로써<br>&nbsp;&nbsp;“어떻게 될 것인가?”, “어떻게 해야 한다”라는 내용이 포함되어야 한다.";

summaryDesc[10]=new Array();
summaryDesc[10][1]="- 어떤 주제나 주장 등에 대해서 적극적으로 분석하고, 종합하여 평가하는 능동적인 사고<br>- 어떤 논증, 추론, 증거, 가치를 표현한 사례를 타당한 것으로 수용할 것인가 아니면<br>&nbsp;&nbsp;불합리한 것으로 거절할 것인가에 대한 결정을 내릴 때 요구되는 사고력";
summaryDesc[10][2]="- 지적 호기심, 객관성, 개방성, 융통성, 지적 회의성, 지적 정직성, 체계성, 지속성,<br>&nbsp;&nbsp;결단성, 다른 관점에 대한 존중";
summaryDesc[10][3]="- 첫 번째 : 결론 확인<br>- 두 번째 : 전제의 확인<br>- 세 번째 : 전제의 수용성, 관련성, 충분성 확인<br>- 네 번째 : 반박 가능성 확인<br>";

summaryDesc[11]=new Array();
summaryDesc[11][1]="- 순환논증의 오류, 결합의 오류와 분해의 오류, 흑백사고의 오류, 감정의 오류,<br>&nbsp;&nbsp;인신공격의 오류, 피장파장의 오류, 성급한 일반화의 오류 등";
summaryDesc[11][2]="- 문제의식을 가지고 있다면 주변에서 발생하는 사소한 일에서도 정보를 수집할 수 있으며,<br>&nbsp;&nbsp;이러한 정보를 통해서 새로운 아이디어를 끊임없이 생산해 낼 수 있다. <br>- 문제의식은 당장 눈앞의 문제를 자신의 문제로 여기고 진지하게 다룰 생각이 없는 한<br>&nbsp;&nbsp;절대로 답을 얻을 수 없다. 따라서 자신이 지니고 있는 문제와 목적을 확실하고 정확하게<br>&nbsp;&nbsp;파악하는 것이 비판적인 사고의 시작이다.<br>- 문제의식을 가졌다면 다음으로 필요한 것이 지각의 폭을 넓히는 일이다.<br>&nbsp;&nbsp;지각의 폭을 넓히는 일은 정보에 대한 개방성을 가지고 편견을 갖지 않는 것으로,<br>&nbsp;&nbsp;고정관념을 타파하는 일이 중요하다. <br>&nbsp;&nbsp;고정관념은 사물을 바로 보는 시각에 영향을 줄 수 있으며, 일방적인 평가를 내리기 쉽다.";

summaryDesc[12]=new Array();
summaryDesc[12][1]="- 목표와 현상을 분석하고, 이 분석결과를 토대로 문제를 도출하여<br>&nbsp;&nbsp;최적의 해결책을 찾아 실행, 평가해 가는 활동을 할 수 있는 능력";
summaryDesc[12][2]="해결해야 할 전체 문제를 파악하여 우선순위를 정하고, <br>선정문제에 대한 목표를 명확히 하는 단계";
summaryDesc[12][3]="선정된 문제를 분석하여 해결해야 할 것이 무엇인지를 명확히 하는 단계";
summaryDesc[12][4]="파악된 핵심문제에 대한 분석을 통해 근본 원인을 도출하는 단계";
summaryDesc[12][5]="문제로부터 도출된 근본원인을 효과적으로 해결할 수 있는 최적의 해결방안을 수립하는 단계";
summaryDesc[12][6]="해결안 개발을 통해 만들어진 실행계획을 실제 상황에 적용하는 활동으로<br>당초 장애가 되는 문제의 원인들을 해결안을 사용하여 제거하는 단계";

summaryDesc[13]=new Array();
summaryDesc[13][1]="- 문제해결과정 중 “ What ”을 결정하는 단계로, 해결해야 할 전체 문제를 파악하여<br>&nbsp;&nbsp;우선순위를 정하고, 선정문제에 대한 목표를 명확히 하는 절차를 거치며, <br>&nbsp;&nbsp;환경 분석, 주요 과제도출, 과제 선정의 절차를 통해 수행한다.<br>- 주로 사용하는 방법으로는 3C분석과 SWOT 분석이 있다.";
summaryDesc[13][2]="- 선정된 문제를 분석하여 해결해야 할 것이 무엇인지를 명확히 하는 단계로<br>&nbsp;&nbsp;현상에 대하여 문제를 분해하여 인과관계 및 구조를 파악하는 단계이다. <br>- 이러한 문제 도출 단계는 문제구조 파악, 핵심문제 선정의 절차를 거쳐 수행한다.";

summaryDesc[13]=new Array();
summaryDesc[13][1]="- 문제해결과정 중 “ What ”을 결정하는 단계로, 해결해야 할 전체 문제를 파악하여<br>&nbsp;&nbsp;우선순위를 정하고, 선정문제에 대한 목표를 명확히 하는 절차를 거치며, <br>&nbsp;&nbsp;환경 분석, 주요 과제도출, 과제 선정의 절차를 통해 수행한다.<br>- 주로 사용하는 방법으로는 3C분석과 SWOT 분석이 있다.";
summaryDesc[13][2]="- 선정된 문제를 분석하여 해결해야 할 것이 무엇인지를 명확히 하는 단계로<br>&nbsp;&nbsp;현상에 대하여 문제를 분해하여 인과관계 및 구조를 파악하는 단계이다. <br>- 이러한 문제 도출 단계는 문제구조 파악, 핵심문제 선정의 절차를 거쳐 수행한다.";

summaryDesc[14]=new Array();
summaryDesc[14][1]="- 파악된 핵심문제에 대한 분석을 통해 근본 원인을 도출해 내는 단계이다.<br>- 원인 분석은 Issue분석, Data 분석, 원인 파악의 절차로 진행되며, <br>&nbsp;&nbsp;핵심 이슈에 대한 가설을 설정한 후, 가설 검증을 위해 필요한 데이터를 수집, 분석하여<br>&nbsp;&nbsp;문제의 근본원인을 도출해 나가는 것이다.";
summaryDesc[14][2]="- 문제로부터 도출된 근본원인을 효과적으로 해결할 수 있는 최적의 해결방안을 수립하는<br>&nbsp;&nbsp;단계로서 해결안 도출, 해결안 평가 및 최적안 선정의 절차로 진행된다.";
summaryDesc[14][3]="- 해결안 개발을 통해 만들어진 실행계획을 실제 상황에 적용하는 활동으로<br>&nbsp;&nbsp;당초 장애가 되는 문제의 원인들을 해결안을 사용하여 제거해 나가는 단계로서<br>&nbsp;&nbsp;실행계획 수립, 실행, Follow-up의 절차로 진행된다.";


// 각 섹션별 속하는 페이지 번호
var descPage=new Array();
descPage[1]=[1,2];
descPage[2]=[1,2];
descPage[3]=[1,1,1,1];
descPage[4]=[1,1];
descPage[5]=[1,1];
descPage[6]=[1,1,1];
descPage[7]=[1,1];
descPage[9]=[1,1,1];
descPage[10]=[1,1,1];
descPage[11]=[1,1];
descPage[12]=[1,2,2,2,3,3];
descPage[13]=[1,1];
descPage[14]=[1,1,1];

// 파트(페이지) 타이틀

var partTitle=new Array();

partTitle[1]=new Array();
partTitle[1][1]="문제의 정의";
partTitle[1][2]="문제의 분류";

partTitle[2]=new Array();
partTitle[2][1]="문제의 유형";
partTitle[2][2]="문제의 분류";

partTitle[3]=new Array();
partTitle[3][1]="전략적 사고, 분석적 사고, 발상의 전환";

partTitle[4]=new Array();
partTitle[4][1]="문제해결의 장애요소, 문제해결방법";

partTitle[5]=new Array();
partTitle[5][1]="창의적 사고의 정의와 특징";

partTitle[6]=new Array();
partTitle[6][1]="자유연상법, 강제연상법, 비교발상법";

partTitle[7]=new Array();
partTitle[7][1]="논리적 사고";

partTitle[9]=new Array();
partTitle[9][1]="논리적 사고의 개발방법";

partTitle[10]=new Array();
partTitle[10][1]="비판적 사고";

partTitle[11]=new Array();
partTitle[11][1]="비판적 사고의 개발방법";

partTitle[12]=new Array();
partTitle[12][1]="문제처리능력";
partTitle[12][2]="문제해결절차";
partTitle[12][3]="문제해결절차";

partTitle[13]=new Array();
partTitle[13][1]="문제인식과 도출";

partTitle[14]=new Array();
partTitle[14][1]="원인분석과 실행평가";



//////////// 나레이션  (해당과정은 사용하지 않음)  ////////////////////////////////

// 나레이션 자막

var captionSet=new Array();

for(var i=1;i<=totalChapNum;i++){
	captionSet[i]=[
                  "" //"1번 페이지 나레이션 내용입니다."
                  , "본 과정을 통해 여러분은 다음과 같은 학습목표를 달성할 수 있습니다. <br>꼼꼼히 살펴보시고 효과적인 학습이 이루어질 수 있도록 준비하세요. "
                  , i+"차시 동영상"
                  , "오늘 배운 내용과 관련하여 제시된 문제에 대한 여러분의 생각을 적어보세요."
                  , "지금까지 학습한 내용을 퀴즈를 통해 확인해 보겠습니다. 스타트 버튼을 클릭해 보세요. ."
                  , "요약정리 내용을 토대로 오늘 배운 내용을 다시 한번 정리해보세요. 또한, 상단에 프린트 버튼을 클릭하여 정리내용을 출력해 보세요."
                  , ""              
              ];
}



