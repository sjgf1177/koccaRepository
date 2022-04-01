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
	box.put("submenu","8");
%>


<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > <u>시설소개</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/facility.gif" alt="아카데미 시설안내">
			</div>
		</div>
		<!-- div style="margin-top:30px;">
			<script src='/learn/user/2012/portal/aboutus/information_files/swfobject.js'></script>
			<script>
				var flashvars = {
				};
				flashvars.subfolder='/learn/user/2012/portal/aboutus/information_files';
				flashvars.package='/learn/user/2012/portal/aboutus/information_files/data.swf';
				flashvars.loaderBackground='#000000';
				flashvars.loaderInside='#2E2E2E';
				flashvars.loaderContour='#636363';
				flashvars.loaderBar='#44c6f7';
				flashvars.progressOffsetY='0';
				flashvars.viewer='/learn/user/2012/portal/aboutus/information_files/null.v3d';
				var params = {
					menu: 'false',
					scale: 'noScale',
					allowFullscreen: 'true',
					allowScriptAccess: 'always',
					bgcolor: '',
					wmode: 'direct' 
				};
				var attributes = {
					id:'ScenarioPlayer'
				};
				swfobject.embedSWF(
				'/learn/user/2012/portal/aboutus/information_files/Spread3DSimpleViewer.swf', 
				'player', '978', '598', '11.4.0', 
				'/learn/user/2012/portal/aboutus/information_files/expressInstall.swf', 
				flashvars, params, attributes);
				
				$(document).ready(function(){
					$('#centeredDiv').mouseover(function(){
						$('html').css('overflow-y','hidden');
					});
					$('#centeredDiv').mouseout(function(){
						$('html').css('overflow-y','auto');
					});
					$('embed').css({'position':'relative','z-index':'-1'});
					$('object').css({'position':'relative','z-index':'-1'});
				});
			</script>
			<div id='centeredDiv' style="border:1px solid #444444;">
				<div id='player'></div>
			</div>
		</div>
		<div style="font-family:gulim; font-size:12px; color:#424242; border:1px solid #919191; padding:20px; margin-top:20px; line-height:18px;">
			위 외관 화면에 마우스 드래그(<img src="/images/2012/sub/page7/icon_mousetip2.gif">)를 이용, 또는 시설화면 상단 우측의 아이콘을 클릭하여 조작이 가능합니다.<br>
			아래의 층별 시설을 클릭하면 내부 사진을 확인할 수 있습니다.
			
			<p style="margin-top:8px; color:#0f6ad0;">본 안내는 실제와 다소 다를 수 있습니다.</p>
		</div-->
		<br>
			<img src="/images/2012/sub/page7/plant_01.gif" alt="콘텐츠제작 교육 시설">
			<img src="/images/2012/sub/page7/plant_02.gif" alt="시청각 교육 시설">
			<img src="/images/2012/sub/page7/plant_03.gif" alt="영상촬영 등 교육 지원 시설">
			<img src="/images/2012/sub/page7/subject_row.gif" alt="아카데미 층별 안내도" style="margin-top:60px;">
			<style>
				table.rowguide { width:100%; margin-top:20px; }
				table.rowguide th { width:80px; font-family:"malgun gothic","dotum","sans-serif"; font-size:18px; color:#888888; font-weight:400; border-top:1px solid #ebebeb; border-bottom:1px solid #ebebeb; text-align:center; }
				table.rowguide td { font-family:"gulim","dotum","sans-serif"; font-size:12px; color:#424242; padding:20px; border-top:1px solid #cbcbcb; border-bottom:1px solid #cbcbcb; border-left:3px solid #b8b8b8; }
				table.rowguide td p { float:left; height:12px; background-image:url('/images/2012/sub/icon_dot.png'); background-repeat:no-repeat; background-position:left center; padding-left:6px; margin:5px 35px 5px 0px; }
				table.rowguide td dl dd { float:left; height:12px; background-image:url('/images/2012/sub/icon_dot.png'); background-repeat:no-repeat; background-position:left center; padding-left:6px; margin:5px 30px 5px 0px; }
			</style>
			
			<table class="rowguide" cellpadding="0" cellspacing="0">
				<tr bgcolor="#fafafa">
					<th>8F</th>
					<td>
						<dl>
							<dd style="width:150px;"><a href="javascript:facilityview('33');" title="상담실 사진을 확인할 수 있습니다.">상담실</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('34');" title="아카데미 도서관 사진을 확인할 수 있습니다.">아카데미 도서관</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('37');" title="현업인 직무과정 강의실 사진을 확인할 수 있습니다.">현업인 직무과정 강의실</a></dd>
						</dl>
					</td>
				</tr>
				<tr>
					<th>7F</th>
					<td>
						<dl>
							<dd style="width:150px;"><a href="javascript:facilityview('25');" title="게임 그래픽 강의실 사진을 확인할 수 있습니다.">게임 그래픽 강의실</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('26');" title="게임 디자인 기획 사진을 확인할 수 있습니다.">게임 디자인 기획</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('27');" title="게임 세미나실 사진을 확인할 수 있습니다.">게임 세미나실</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('28');" title="게임 프로그래밍 강의실 사진을 확인할 수 있습니다.">게임 프로그래밍 강의실</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('29');" title="게임 프로젝트 강의실 사진을 확인할 수 있습니다.">게임 프로젝트 강의실</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('30');" title="게임 프로젝트 개발실 사진을 확인할 수 있습니다.">게임 프로젝트 개발실</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('31');" title="기획창작 강의실 사진을 확인할 수 있습니다.">기획창작 강의실</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('32');" title="취업 상담실 사진을 확인할 수 있습니다.">취업 상담실</a></dd>
						</dl>
					</td>
				</tr>
				<tr bgcolor="#fafafa">
					<th>5F</th>
					<td>
						<dl>
							<dd style="width:150px;"><a href="javascript:facilityview('22');" title="Digital Editing Room 사진을 확인할 수 있습니다.">Digital Editing Room</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('23');" title="S3D Graphic Room 사진을 확인할 수 있습니다.">S3D Graphic Room</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('24');" title="멀티미디어강의실 사진을 확인할 수 있습니다.">멀티미디어강의실</a></dd>
						</dl>
					</td>
				</tr>
				<tr>
					<th>4F</th>
					<td>
						<dl>
							<dd style="width:150px;"><a href="javascript:facilityview('14');" title="Digital Editing Room I 사진을 확인할 수 있습니다.">Digital Editing Room I</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('16');" title="Digital Editing Room II 사진을 확인할 수 있습니다.">Digital Editing Room II</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('20');" title="디지털 음향 강의실 사진을 확인할 수 있습니다.">디지털 음향 강의실</a></dd>
						</dl>
					</td>
				</tr>
				<tr bgcolor="#fafafa">
					<th>3F</th>
					<td>
						<dl>
							<dd style="width:150px;"><a href="javascript:facilityview('8');" title="회견장 사진을 확인할 수 있습니다.">회견장</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('11');" title="회의장 사진을 확인할 수 있습니다.">회의장</a></dd>
							<dd style="width:150px;">국제회의장</dd>
						</dl>
					</td>
				</tr>
				<tr>
					<th>2F</th>
					<td>
						<dl>
							<dd style="width:150px;"><a href="javascript:facilityview('5');" title="S3D Post-Production 사진을 확인할 수 있습니다.">S3D Post-Production</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('6');" title="S3D Pre-Production 사진을 확인할 수 있습니다.">S3D Pre-Production</a></dd>
							<dd style="width:150px;"><a href="javascript:facilityview('7');" title="S3D Production 사진을 확인할 수 있습니다.">S3D Production</a></dd>
						</dl>
					</td>
				</tr>
				<tr bgcolor="#fafafa">
					<th>1F</th>
					<td>
						<dl style="clear:left;">
							<dd style="width:150px;"><a href="javascript:facilityview('1');" title="S3D Training Center 사진을 확인할 수 있습니다.">S3D Training Center</a></dd>
						</dl>
					</td>
				</tr>
			</table>
	</div>
	
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>