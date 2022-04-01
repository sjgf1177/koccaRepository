<%
//**********************************************************
//  1. 제      목: 개인정보취급방침
//  2. 프로그램명 : zu_Personal.jsp
//  3. 개      요: 개인정보취급방침
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	box.put("topmenu","7");
	box.put("submenu","1");
%>


<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="한국콘텐츠진흥원장님의 인사말입니다">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > <u>진흥원장 인사말</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/title_greeting.png" alt="진흥원장 인사말">
			</div>
		</div>
		<table class="detail" cellpadding="0" cellspacing="0" summary="인사말입니다">
			<colgroup><col width="269"><col width="*"><col width="325"></colgroup>
			<tr>
				<td class="photo"><img src="/images/2012/sub/page7/photo_ledger.gif" alt="한국콘텐츠진흥원장 홍상표"></td>
				<td class="text1">
					<b>한국콘텐츠아카데미</b>는 문화와 산업, 방송과 통신, 콘텐츠 장르
					간 융합이라는 시대 요구에 부응해 기존에 장르별로 운영되어
					오던 국내 최고 수준의 <<span>방송아카데미</span>>, <<span>기획창작아카데미</span>>, 
					<<span>게임아카데미</span>>를 하나로 통합해 만든 국내 유일의 온/오프라
					인 콘텐츠 전문 교육기관입니다.<br><br>
					
					지금 세계는 정보사회를 거쳐 창조산업사회로 들어서고 있습니
					다.  창의, 감성, 재능 등의 가치가 핵심이 되는 콘텐츠 산업은
					고부가가치형 산업으로서 가파른 수출 증가율을 보이는 성장력
					높은 미래 국가 성장 동력입니다.<br><br>
					
					특히 3D입체영상, CG, HDTV 등의 미디어 융합콘텐츠 분야는
					앞으로 고성장과 함께 높은 고용 유발효과가 예상되는 분야
					입니다. 콘텐츠 산업 현장에서는 이에 따라 기대감이 점차
					커지고 있으며, 더불어 융합형 창작인재 육성은 더욱 강조되고
					있는 현실입니다.
				</td>
				<td class="text2">
					콘텐츠의 경쟁력은 전적으로 창직자의 창의 능력에서 비롯
					됩니다. 글로벌 경쟁력을 갖춘 콘텐츠의 기획과 제작, 해외
					시장 진출은 모두 우수한 창작인력 기반위에 창출되는 것으로
					고도의 창작 역량과 디지털제작 기술력을 갖춘 창의 인재를
					양성하기 위한 체계적인 교육과 인프라는 콘텐츠 산업에
					있어서 가장 중요한 요소입니다.<br><br>
					
					한국콘텐츠아카데미는 국내외 최고의 강사진 구성과 풍부한
					교육 노하우, 국내 최고의 디지털 제작교육인프라를 바탕으로
					차세대 창의인재육성, 산업계 직무 재교육, 온라인교육과정
					운영을 통해 콘텐츠산업계 공적 연수기관으로서 역할과 기능
					수행을 위해 노력할 것입니다.<br><br>
					
					여러분의 변함없는 관심과 성원을 부탁드리겠습니다.
					<p><img src="/images/2012/sub/page7/sign.gif" alt="한국콘텐츠진흥원장 홍상표"></p>
				</td>
			</tr>
		</table>
	</div>
	
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>