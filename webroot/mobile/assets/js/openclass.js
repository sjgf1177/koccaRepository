/**
 * 열린강좌 상세 화면으로 이동한다.
 * @param type : app / web 구분
 * @param seq : 열린강좌 일련번호
 * @param prevPage : 이전 페이지 정보. 열린강좌 상세 화면의 경우 접근 경로가 3가지가 있음. 이를 구별하기 위한 값
 */
function fnOpenClassViewDetail(type, seq, prevPage, etcArg1, etcArg2) {
    var url = "/servlet/controller.mobile.openclass.OpenClassViewServlet";
    var param = "?process=openClassViewDetail&seq=" + seq;

    if ( prevPage == "popular" || prevPage == "category" ) {
        param += "&prevPage=" + prevPage;

    } else if ( prevPage == "theme" ) {
        param += "&prevPage=" + prevPage + "&themeNm=" + etcArg1;

    } else if ( prevPage == "categoryDetail" ) {
        param += "&prevPage=" + prevPage + "&lectureCls=" + etcArg1 + "&lectureClsNm=" + etcArg2;

    }

    fnMoveNavi( type, url + param );
}
