<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page errorPage="/learn/library/error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.credu.homepage.*"%>
<%@ page import="com.credu.library.*"%>
<%@ page import="com.credu.common.*"%>
<%@ page import="com.credu.system.*"%>
<%@ page import="com.dunet.common.util.*"%>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select"%>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
	RequestBox box = (RequestBox) request.getAttribute("requestbox");
	if (box == null)
		box = RequestManager.getBox(request);
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
	
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="form1" method="post">
	<input type="hidden" name="p_process" value="" />

	<h2 class="subTit">인사말</h2>
	<div id="subCont">
		<div>
			<div>
				<div style="float: left">
					<img src="/images/2013/common/bar.jpg" alt="bar" />
				</div>
				<div style="padding-left: 15px; font-size: 20px; font-weight: bold">
					한국콘텐츠아카데미는<br />한국콘텐츠진흥원의 콘텐츠전문인력양성기관입니다.
				</div>
			</div>
			<div style="width: 530px; float: left; padding: 40px 16px; font-size: 14px;">


				<p>
					한국콘텐츠아카데미는 <br />창의/융합/감성 등의 키워드를 핵심가치로<br />콘텐츠 산업 현장에서 필요한 융합형
					창의인재를 양성하기 위해 노력하고 있습니다.
				</p>
				<br /> <br />
				<p>
					콘텐츠의 경쟁력은 창작자의 창의능력에서 비롯됩니다.<br />우수한 창작역량과 융합기술력을 갖춘 인재를 양성하기
					위해서는<br />체계적인 교육 커리큘럼과 교육인프라가 필요합니다.
				</p>
				<br /> <br /> 
				<p>
					한국콘텐츠아카데미는<br />국내외 최고 강사진의 풍부한 교육 노하우, 국내 최고의 콘텐츠 제작교육 인프라를 바탕으로<br />콘텐츠
					산업 예비인력교육, 현업인 직무교육, 전략지역 해외연수 및 온라인 교육과정 등을 운영하고 있습니다.<br />이를 통해
					글로벌 인재 양성을 위한 콘텐츠 산업 공적 연수기관으로서의 역할과 기능 수행에 최선을 다하겠습니다.
				</p>
				<br /> <br /><p>여러분의 변함없는 관심과 성원을 부탁드리겠습니다.</p> <br /> <br />
				<!-- <span style="font-weight:bold">한국콘텐츠진흥원장 송성각</span> -->
			</div>
			<!-- <div style="float:right; padding-top:20px;">
                <img src="/images/2013/introduce/img_director.jpg" alt="한국콘텐츠진흥원장 송성각 사진" />
            </div>  -->
		</div>
		<!-- //greeting -->

	</div>
	<!-- //#subCont -->

</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->