<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/KoccaPageUtilTaglib" prefix="pu" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />

<c:set var="pageno" value="${param.p_pageno eq null || param.p_pageno eq ''? 1 : param.p_pageno}" />
<c:set var="pagesize" value="${param.p_pagesize eq null || param.p_pagesize eq ''? 10 : param.p_pagesize}" />
<c:set var="creduURL" value="/contents/credu/start.html" />
<script type="text/javascript">
	// 과정검색
	function subjSearch() {
		document.form1.p_process.value = "TotalSubjectList";
		document.form1.submit();
	}

	//과정 내용보기
	function whenSubjInfo(subj,subjnm,courseyn,upperclass,upperclassnm, year, subjseq, menuid){
		document.form1.p_subj.value = subj;
		document.form1.p_subjnm.value = subjnm;
		document.form1.p_iscourseYn.value = courseyn;
		document.form1.p_upperclass.value = upperclass;
		document.form1.p_upperclassnm.value = upperclassnm;
		document.form1.p_year.value = year;
		document.form1.p_subjseq.value = subjseq;
		document.form1.menuid.value = menuid;
		document.form1.p_process.value = 'SubjectPreviewPage';
		document.form1.p_rprocess.value = 'TotalSubjectList';
		document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
		document.form1.target = "_self";
		document.form1.submit();
	}

	//수강신청
	function whenSubjPropose(subj,year,subjseq, subjnm, billYn) {
	
		if('${sessionScope.userid}' == '') {
			alert("로그인이 필요합니다.");
			return;
		}
	
		if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")){
			return;
		}
	    var new_Open = window.open("","proposeWindow",'scrollbars=yes,width=800,height=600,resizable=no');
		document.form1.p_process.value = "SubjectEduProposePage";
		document.form1.target = "proposeWindow";
		document.form1.p_subj.value = subj;
		document.form1.p_year.value = year;
		document.form1.p_subjseq.value = subjseq;
		document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
		document.form1.submit();
		document.form1.target = "_self";
	}

	//관심버튼 클릭
	function fnInterestInsert(subj,year,subjseq,courseyn,subjnm) {
		if (confirm(subjnm + " 과정을 관심과정으로 등록하시겠습니까?")) {
				$.post("/servlet/controller.common.AjaxServlet"
						, {checkSqlNum:"interest.exist", sqlNum:"interest.insert", p_subj:subj,rerurnFunction:"resultInterest"}
						, function(data) {
							$("#ajaxDiv").html(data);
						});
		}
	}

	function resultInterest(data) {
		if (data.result=='true') alert("관심과정으로 등록했습니다."); 
		else alert("관심과정 등록이 실패하였습니다.\n"+data.result);
	}
	
	//페이지 이동
	function go(index) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = index;
		document.form1.submit();
	}
	
	//페이지 이동
	function goPage(pageNum) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = pageNum;
		document.form1.submit();
	}
	function pagesize(pageSize) {
		document.form1.target = "_self";
		document.form1.p_pageno.value = 1;
		document.form1.p_pagesize.value = pageSize;
		document.form1.submit();
	}

	
	var creduGateUrl;
	var creduWin;
	
    //맛보기
    function whenPreShow(url, subj, d_prewidth, d_preheight) {
    	
    	if(d_prewidth == '' || d_prewidth == 0 || d_prewidth == 'undefined' || d_prewidth == null){
    		d_prewidth = 1012;
    	}
    	
    	if(d_preheight == '' || d_preheight == 0 || d_preheight == 'undefined' || d_preheight == null){
    		d_preheight = 677;
    	}
    	
    	if(url.indexOf("ksp.credu.com") > 0 ){
    		creduGateUrl = url;
    		creduWin = window.open("<c:out value="${creduURL }" />", "creduPre", "top=100px,left=100px,width=1000px,height=600px");
    	}else{
	        prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
	        open_window("openShow",url,"100","100",d_prewidth,d_preheight,"","","",true,true);
    	}
    	
    }
    
  	//크레듀 제휴과정 링크정보 가져오기
    function creduURL(){
    	$.ajax({
                type : "post"
            ,   url : "/servlet/controller.contents.EduStart"
            ,   dataType : "json"
            ,   data : {
            	url : creduGateUrl,
            	p_process : "creduSubj"
            }
            ,   success : function(ajaxData) {
            		creduWin.loadswf(ajaxData.videoURL, 945, 540);
            	}
            ,   error :  function(arg1, arg2) {
                    alert("오류가 발생하여 플레이 할 수 없습니다.");
                }
        });
    }
    

    function goSort(tmp) {
        $("[name='p_order']").val(tmp);
		document.form1.p_process.value = "TotalSubjectList";
		document.form1.submit();
	}
</script>
<section>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="subContainer" style="width:100%; padding-left:0;">
                        <div class="sub_section">
                            <div class="sub_contents_header">
                                <span>과정통합검색</span>
                                <div class="linemap_wrap">
                                    <ul>
                                        <li>
                                            <a href="#">
                                                <span>
                                                    <img src="/common/image/home_icon.png" alt="메인">
                                                </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#"><span>학습지원센터</span></a>
                                        </li>
                                        <li>
                                            <a href="#"><span>FAQ</span></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="sub_contents_body">
                                <div class="sub_board_header">
                                   <div class="board_search_box" style="width:100%; text-align: right; margin-bottom:30px;">
                                        <form name="form1" action="/servlet/controller.propose.ProposeCourseServlet" method="post">
											<input type="hidden" name="p_process"	    value="<c:out value="${param.p_process }" />"/>
											<input type="hidden" name="p_rprocess"	    value=""/>
											<input type="hidden" name="p_year"	        value=""/>
											<input type="hidden" name="p_subj"	        value=""/>
											<input type="hidden" name="p_subjseq"	    value=""/>
											<input type="hidden" name="p_subjnm"	    value=""/>
											<input type="hidden" name="p_iscourseYn"	value=""/>
											<input type="hidden" name="p_upperclass"	value=""/>
											<input type="hidden" name="p_upperclassnm"	value=""/>
											<input type="hidden" name="p_action"	    value="<c:out value="${param.p_action }" />"/>
											<input type="hidden" name="p_pageno"	    value="<c:out value="${param.p_pageno }" />"/>
											<input type="hidden" name="p_pagesize"      value="<c:out value="${param.p_pagesize }" />"/>
											<input type="hidden" name="menuid"          value=""/>
											<input type="hidden" name="p_order"         value=""/>
                                            <input type="text" class="board_search" title="검색어를 입력해주세요" style="width:calc(100% - 41px)" name="s_subjnm" value="<c:out value="${param.s_subjnm }" />" onkeypress="javascript:fnKeyPressEnter(event, 'subjSearch');">
                                            <input type="button" class="btn_board_search" value="" onclick="javascript:subjSearch();">
                                        </form>
                                    </div>
                                </div>
                                <div class="sub_thumb_body">
                                	<c:forEach items="${totalSubjectList }" var="list" varStatus="status">
	                                	<div class="thumb_box">
	                                        <div class="thumb_imgBox">
	                                            <img src="<c:out value="${list.d_subjimg }" />" alt="<c:out value="${list.d_subjnm }" />" style="max-width: 200px; max-height: 125.7px;">
	                                        </div>
	                                        <div class="thumb_con">
	                                        	<c:if test="${list.d_isnew eq 'Y' || list.d_isrecom eq 'Y' }">
	                                            <div class="thumb_top_tag">
	                                            	<c:if test="${list.d_isnew eq 'Y' }"><span class="tag_type_new"></span></c:if>
	                                                <c:if test="${list.d_isrecom eq 'Y' }"><span class="tag_type_recommend"></span></c:if>
	                                            </div>
	                                        	</c:if>
	                                            <a href="javascript:whenSubjInfo('<c:out value="${list.d_subj }"/>','<c:out value="${list.d_subjnm }"/>','<c:out value="${list.d_isonoff }"/>','<c:out value="${list.d_scupperclass }"/>','<c:out value="${list.d_uclassnm }"/>','<c:out value="${list.d_scyear }"/>','<c:out value="${list.d_subjseq }"/>','00')"><span class="thumb_title"><c:out value="${list.d_subjnm }" /></span></a>
	                                            <ul class="thumb_con_Info">
	                                                <li class="blue"><span>교육기간</span> : <c:out value="${list.d_edustart }" /> ~ <c:out value="${list.d_eduend }" /></li>
	                                                <li><span>수강신청기간</span> : <c:out value="${list.d_propstart }" /> ~ <c:out value="${list.d_propend }" /></li>
	                                            </ul>
	                                            <div class="thumb_button">
	                                            	<c:choose>
		                                            	<c:when test="${list.d_existpropose }">
		                                            		<a href="javascript:void(0);" class="applycomplete_btn" >신청완료</a>
		                                            	</c:when>
		                                            	<c:otherwise>
		                                            		<c:if test="${list.d_propyn eq 'Y' }">
		                                            			<a href="javascript:whenSubjPropose('<c:out value="${list.d_subj }"/>','<c:out value="${list.d_scyear }"/>','<c:out value="${list.d_subjseq }"/>','<c:out value="${list.d_subjnm }"/>');" class="apply_btn">신청</a>
		                                            		</c:if>
		                                            	</c:otherwise>
	                                            	</c:choose>
	                                                <a href="javascript:whenPreShow('<c:out value="${list.d_preurl }"/>','<c:out value="${list.d_subj }"/>','<c:out value="${list.d_prewidth }"/>','<c:out value="${list.d_preheight }"/>');" class="gustation_btn">맛보기</a>
	                                            </div>
	                                        </div>
	                                    </div>
	                                    <c:set var="totalpage" value="${list.d_totalpage }" />
                                	</c:forEach>
                                	<c:if test="${fn:length(totalSubjectList) <= 0 }">
                                		<div class="thumb_box">
                                			검색된 내용이 없습니다.
	                                    </div>
	                                    <c:set var="totalpage" value="0" />
                                	</c:if>
                                </div>
                                ${pu:typeB_printPageListDiv(totalpage, pageno, pagesize) }
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>


<!-- footer -->
<jsp:include page="/learn/user/typeB/include/newFooterB.jsp" />
<!-- footer -->
</body>
</html>
