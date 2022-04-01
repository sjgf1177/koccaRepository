/**
 * 정규과정 상세 화면으로 이동한다.
 * @param type : app / web 구분
 * @param subj : 정규과정 코드
 * @param year : 교육년도
 * @param subjSeq : 정규과정별 과정 차수
 */
function fnViewOnlineClassDetail(type, subj, year, subjseq) {
    var url = "/servlet/controller.mobile.onlineclass.OnlineClassDetailServlet?process=selectOnlineClassDetail&subj=" + subj + "&year=" + year + "&subjseq=" + subjseq;

    fnMoveNavi( 2, url, "");
}
