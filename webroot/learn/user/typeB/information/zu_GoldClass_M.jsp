<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/KoccaPageUtilTaglib" prefix="pu" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />
<c:set var="pageno" value="${param.pageNo eq null || param.pageNo eq ''? 1 : param.pageNo}" />
<c:set var="pagesize" value="${param.p_pagesize eq null || param.p_pagesize eq ''? 10 : param.p_pagesize}" />

<script type="text/javascript">
function openGoldClass(seq,w,h) {
    if (w.length>0) {
        window.open("", "ViewVod", "top=0, left=0, width="+w+", height="+h+", status=no, resizable=no");

    } else {
        window.open("", "ViewVod", "top=0, left=0, width=1008, height=570, status=no, resizable=no");
    }
    document.form1.action="/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.p_process.value = "popUpVod";
    document.form1.p_seq.value = seq;
    document.form1.target="ViewVod";
    document.form1.submit();
    document.form1.target="_self";
}

//상세화면 페이지로 이동
function viewContent(seq) {
    document.form1.p_prePage.value = "Main";
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.target="_self";
    document.form1.p_process.value = "selectView";
    document.form1.p_seq.value = seq;
    document.form1.submit();
}


//페이지 이동
function goPage(pageNum) {
     document.form1.pageNo.value = pageNum;
     document.form1.target="_self";
     document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
     document.form1.p_process.value = "mainPage";
     document.form1.submit();
}

function goReply(seq) {

    window.open("about:blank", "replyList", "top=0, left=0, width=637, height=530, status=no, resizable=no");

    document.form1.action="/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.p_process.value = "insertReplyPage";
    document.form1.p_seq.value = seq;
    document.form1.target="replyList";
    document.form1.submit();

    document.form1.target="_self";
}

function fnSelect(){
	document.form1.action="/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.p_process.value = "mainPage";
    document.form1.pageNo.value = "1";
    document.form1.submit();
}
</script>
<section class="container d-flex">
	<div class=""></div>
	<div class="subContainer">
		<div class="sub_section">
			<div class="sub_contents_body">
				<div class="sub_board_header">
					<jsp:include page="/learn/user/typeB/include_left/left_5.jsp">
						<jsp:param value="${param.menuid }" name="left_active"/>
					</jsp:include>
					<div class="list_title">
						<span>열린강좌는 무료로 제공되는 공개강좌로 재미와 깊이가 함께 존재합니다.</span>
						<span>훌륭한 명사의 강좌를 만나보실 수 있으며, 누구나 참여할 수 있는 열린 교육공간입니다.</span>
					</div>
					<div class="d-flex justify-content-lg-between">
						<div class="board_search_box">
							<form name="form1" method="post">
								<input type="hidden" name="p_process"   value="" />
								<input type="hidden" name="p_seq"       value="" />
								<input type="hidden" name="p_prePage"   value="" />
								<input type="hidden" name="pageNo"	value="<c:out value="${param.pageNo }" />"/>
								<input type="hidden" name="p_pagesize"  value="<c:out value="${param.p_pagesize }" />"/>
								<input type="hidden" name="p_lecture_cls" value="<c:out value="${param.p_lecture_cls eq '' || param.p_lecture_cls eq null? 'ALL' : param.p_lecture_cls}" />" />
								<input type="hidden" name="gubun" value="${param.gubun }" />
								<input type="hidden" name="menuid" value="${param.menuid }" />
								<div class="input-group mb-3">
									<input type="text" name="p_searchtext" id="p_searchtext" class="form-control input board_search" value="<c:out value="${param.p_searchtext }" />" title="검색어를 입력해주세요." placeholder="검색어를 입력해주세요." onkeypress="if(event.keyCode==13) {javascript:fnSelect()(); return false;}">
									<input type="button" class="btn btn-outline-secondary input btn_board_search" value="" onclick="javascript:fnSelect();">
								</div>
							</form>
						</div>
						<ul class="radio-row-box">
							<li class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1">
								<label class="form-check-label" for="inlineRadio1">전체</label>
							</li>
							<li class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2">
								<label class="form-check-label" for="inlineRadio2">신청가능</label>
							</li>
							<li class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio3" value="option3">
								<label class="form-check-label" for="inlineRadio3">마감</label>
							</li>
						</ul>
					</div>
				</div>

				<div class="sub_thumb_body d-flex justify-content-between flex-wrap">
					<c:forEach items="${openClassList }" var="list" varStatus="status">
						<div class="thumb_box card">
							<a href="javascript:viewContent('<c:out value="${list.d_seq }" />');" class="thumb_imgBox">
								<img src="<c:out value="${list.d_vodimg }" />" alt="<c:out value="${list.d_lecnm }" />">
							</a>
							<div class="card-body thumb_con">
								<a href="javascript:viewContent('<c:out value="${list.d_seq }" />');" class="card-title"><span class="thumb_title textline2"><c:out value="${list.d_lecnm }" /></span></a>
								<ul class="thumb_con_Info card-text">
									<li>
										<c:forEach begin="1" end="${list.d_checkpoin }" step="1">
											<img src="/common/image/ico_star_on.png" alt="별점">
										</c:forEach>
										<c:forEach begin="${list.d_checkpoin + 1 }" end="5" step="1">
											<img src="/common/image/ico_star_off.png" alt="별점">
										</c:forEach>
									</li>
									<li><span><c:out value="${list.d_tutornm }" /> · 총 <c:out value="${list.d_lectime }" />초 · 조회수 <c:out value="${list.d_viewcnt }" /></span></li>

								</ul>
								<div class="thumb_button">
									<a href="javascript:openGoldClass('<c:out value="${list.d_seq }" />', '<c:out value="${list.d_d_width_s }" />','<c:out value="${list.d_height_s }" />');" class="apply_btn btn btn-purple" style="margin-top:15px;">강좌보기</a>
									<a href="javascript:goReply('<c:out value="${list.d_seq }" />');" class="command_btn btn btn-outline-secondary" style="margin-top:15px;">의견달기</a>
								</div>
							</div>
						</div>
						<c:set var="totalpage" value="${list.d_totalpage }" />
					</c:forEach>
					<c:if test="${fn:length(openClassList) <= 0 }">
						<p class="text-center">
							등록된 열린강좌 내역이 없습니다.
						</p>
						<c:set var="totalpage" value="0" />
					</c:if>
				</div>
				${pu:typeB_printPageListDiv(totalpage, pageno, pagesize) }
			</div>
		</div>
	</div>
</section>

<!-- footer -->
<jsp:include page="/learn/user/typeB/include/newFooterB.jsp" />
<!-- footer -->
</body>
</html>