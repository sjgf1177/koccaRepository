<%
//**********************************************************
//  1. 제      목: 나의 포인트
//  2. 프로그램명: gu_MyPoint_L.jsp
//  3. 개      요: 포인트
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.01
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) box = RequestManager.getBox(request);

	NumberFormat s_fmt = NumberFormat.getInstance();				//통화단위 ,표시

    int		v_totgetpoint	= box.getInt("p_getpoint");       
    int		v_totusepoint	= box.getInt("p_usepoint");       
    int		v_totwaitpoint	= box.getInt("p_waitpoint");       
	int		v_availablepoint = v_totgetpoint - v_totusepoint - v_totwaitpoint;
	String	v_availablepoint_text = s_fmt.format(v_availablepoint); 
	
    ArrayList list1     = null;
    ArrayList list2     = null;
    ArrayList list3     = null;    

    list1 = (ArrayList)request.getAttribute("selectHavePointList");		//보유포인트
    list2 = (ArrayList)request.getAttribute("selectStoldPointList");	//적립포인트
    list3 = (ArrayList)request.getAttribute("selectUsePointList");		//사용포인트
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
// Tab 선택시 동작
function showTab(clickTab)
{
	g_showTab(clickTab, 4);
}

function g_showTab(tabNo, count)
{

	var clickTabNo = new Number(tabNo);
	var tabCount   = new Number(count);

	if( clickTabNo == 0 || tabCount == 0 ) return;

	for(i=1; i<=tabCount; i++)
    {
		div = document.all["tab_"+i];

		if(!div) return;

		if( clickTabNo == i ) {
			// 활성화된 tab 클릭시 return
			if(div.style.display == "") return;
			div.style.display = ""
		}
		else {
			// 선택되지 않는 탭들은 깜빡임 방지.
			if(div.style.display != "none") {
				div.style.display = "none";
			}
		}
	}
}
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
			<h2><img src="/images/portal/myclass/h2_tit6.gif" alt="마이포인트" class="fl_l" /><p class="category">Home > 나의강의실 > <strong>마이포인트</strong></p></h2>
 
			<dl class="mypoint_info">
				<dt><img src="/images/portal/myclass/mypoint_img1.gif" alt="" /></dt>
				<dd>
					<p>마이 포인트 : <strong><%= v_availablepoint_text %></strong> Point</p>
					<ul>
						<li>현재 보유포인트, 포인트 적립내역, 포인트 사용내역을 확인하실 수 있습니다.</li>
						<li>포인트의 유효기간은 적립일로부터 1년입니다</li>
					</ul>
				
				</dd>
			</dl>

            <div id="tab_1" >

			<ul class="tabwrap mg_t35">
				<li><a href="javascript:showTab(1)" class="tab_myclass tab_myclass_on"><span>보유포인트</span></a></li>
				<li><a href="javascript:showTab(2)" class="tab_myclass"><span>적립포인트</span></a></li>
				<li><a href="javascript:showTab(3)" class="tab_myclass"><span>사용포인트</span></a></li>
			</ul>

			<table class="board_list">
			<colgroup><col width="240px" /><col width="150px" /><col width="150px" /><col width="150px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th><p>변경날자</p></th>
				<th><p>적립포인트</p></th>
				<th><p>사용포인트</p></th>
				<th class="end"><p>보유포인트</p></th>
			</tr>
			</thead>
			<tbody>
<%
	for(int i = 0; i < list1.size(); i++) {

		DataBox dbox1 = (DataBox)list1.get(i);

		String	v_tid			=  dbox1.getString("d_tid");
		String	v_getdate		=  dbox1.getString("d_getdate");
		String	v_getpoint		=  s_fmt.format(dbox1.getInt("d_getpoint"));
		String	v_usepoint		=  s_fmt.format(dbox1.getInt("d_usepoint"));
		String	v_usepoint_text	=  dbox1.getString("d_usepoint");
		String	v_lefrpoint		=  s_fmt.format(dbox1.getInt("d_lefrpoint"));
		String	v_expiredate	=  dbox1.getString("d_expiredate");

		v_getdate      = FormatDate.getFormatDate(v_getdate,"yyyy.MM.dd");
%>                 
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td class="num"><%= v_getdate %></td>
				<td class="num ta_r"><%= v_getpoint %></td>
				<td class="num ta_r"><%= "0".equals(v_usepoint) ? v_usepoint_text : v_usepoint %></td>
				<td class="num ta_r"><strong><%= v_lefrpoint %></strong></td>
			</tr>
<%
	}
%>
<%	if(list1.size() == 0 ){%>
       	    <tr>
		    	<td height="3" colspan="4" align="center">포인트 내역이 없습니다.</td>
		    </tr>
<%	}%>
			</tbody>
			</table>

            </div>
		    <div id="tab_2" STYLE="display:none">                        

			<ul class="tabwrap mg_t35">
				<li><a href="javascript:showTab(1)" class="tab_myclass"><span>보유포인트</span></a></li>
				<li><a href="javascript:showTab(2)" class="tab_myclass tab_myclass_on"><span>적립포인트</span></a></li>
				<li><a href="javascript:showTab(3)" class="tab_myclass"><span>사용포인트</span></a></li>
			</ul>

			<table class="board_list">
			<colgroup><col width="110px" /><col width="190px" /><col width="130px" /><col width="130px" /><col width="130px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th><p>적립일자</p></th>
				<th><p>적립포인트</p></th>
				<th><p>적립내용</p></th>
				<th><p>수강료</p></th>
				<th class="end"><p>결제금액</p></th>
			</tr>
			</thead>
			<tbody>
<%
	for(int i = 0; i < list2.size(); i++) {
		DataBox dbox2 = (DataBox)list2.get(i);

		String	v_tid			=  dbox2.getString("d_tid");
		String	v_getdate		=  dbox2.getString("d_getdate");
		String	v_title			=  dbox2.getString("d_title");
		String	v_getpoint		=  s_fmt.format(dbox2.getInt("d_getpoint"));
		String	v_subjprice		=  s_fmt.format(dbox2.getInt("d_subjprice"));
		String	v_price			=  s_fmt.format(dbox2.getInt("d_price"));
	
		v_getdate      = FormatDate.getFormatDate(v_getdate,"yyyy.MM.dd");
%>                 
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td class="num"><%= v_getdate %></td>
				<td class="num ta_r"><%= v_getpoint %></td>
				<td><%= v_title %></td>
				<td class="num ta_r"><%= v_subjprice %></td>
				<td class="num ta_r"><strong><%= v_price %></strong></td>
			</tr>
<%
	}
%>
<%	if(list1.size() == 0 ){%>
       	    <tr>
		    	<td height="20" colspan="5" align="center">포인트 내역이 없습니다.</td>
		    </tr>
<%	}%>
			</tbody>
			</table>

            </div>
            <div id="tab_3" STYLE="display:none">                        

			<ul class="tabwrap mg_t35">
				<li><a href="javascript:showTab(1)" class="tab_myclass"><span>보유포인트</span></a></li>
				<li><a href="javascript:showTab(2)" class="tab_myclass"><span>적립포인트</span></a></li>
				<li><a href="javascript:showTab(3)" class="tab_myclass tab_myclass_on"><span>사용포인트</span></a></li>
			</ul>

			<table class="board_list">
			<colgroup><col width="110px" /><col width="305px" /><col width="95px" /><col width="90px" /><col width="90px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th><p>사용일자</p></th>
				<th><p>과정명</p></th>
				<th><p>사용포인트</p></th>
				<th><p>수강료</p></th>
				<th class="end"><p>결제금액</p></th>
			</tr>
			</thead>
			<tbody>
<%
	for(int i = 0; i < list3.size(); i++) {
		
		DataBox dbox3 = (DataBox)list3.get(i);
	
		String	v_tid			=  dbox3.getString("d_tid");
		String	v_usedate		=  dbox3.getString("d_usedate");
		String	v_title			=  dbox3.getString("d_title");
		String	v_usepoint		=  s_fmt.format(dbox3.getInt("d_usepoint"));
		String	v_subjprice		=  s_fmt.format(dbox3.getInt("d_subjprice"));
		String	v_price			=  s_fmt.format(dbox3.getInt("d_price"));
	
		v_usedate      = FormatDate.getFormatDate(v_usedate,"yyyy.MM.dd");
%>                 
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td class="num"><%= v_usedate %></td>
				<td class="ta_l"><%= v_title %></td>
				<td class="num ta_r"><%= v_usepoint %></td>
				<td class="num ta_r"><%= v_subjprice %></td>
				<td class="num ta_r"><strong><%= v_price %></strong></td>
			</tr>
<%
	}
%>
<%	if(list1.size() == 0 ){%>
       	    <tr>
		    	<td height="20" colspan="5" align="center">포인트 내역이 없습니다.</td>
		    </tr>
<%	}%>
			</tbody>
			</table>

            </div>
	<!-- 		
			<div class="explan_info mg_t45">
				<div class="explan_info_in">
					<dl>
						<dt><img src="/images/portal/myclass/point_t1.gif" alt="포인트 안내" /></dt>
						<dd><img src="/images/portal/myclass/point_info1.gif" alt="한국콘텐츠아카데미의 포인트는 기본포인트, 특별포인트로 구성되며, 포인트의 유효기간은 적립일로부터 1년입니다." /></dd>
					</dl>
					<dl>
						<dt><img src="/images/portal/myclass/point_t2.gif" alt="기본 포인트" /></dt>
						<dd><img src="/images/portal/myclass/point_info2.gif" alt="회원가입시 기본으로 5,000점을 부여하여, KOCCA 방송연수센터의 온라인 과정 중 유료과정을 수강하시면 수강료 결제금액의 10% 를 과정 최종승인과 동시에 적립해드립니다. (포인트로 결제한 금액은 적립되지 않습니다.)" /></dd>
					</dl>
					<dl>
						<dt><img src="/images/portal/myclass/point_t3.gif" alt="특별 포인트" /></dt>
						<dd><img src="/images/portal/myclass/point_info3.gif" alt="향후 진행되는 이벤트 및 교육후기에 채택되신 분에게 특별포인트를 적립해 드립니다." /></dd>
					</dl>
					<dl>
						<dt><img src="/images/portal/myclass/point_t4.gif" alt="포인트 사용" /></dt>
						<dd><img src="/images/portal/myclass/point_info4.gif" alt="온라인 유료과정 수강신청 시, 가용포인트 1,000점 기준으로 사용 가능하며, 먼저 적립된 포인트부터 사용됩니다. (포인트는 교육비 할인으로만 사용되며, 오프라인 과정은 적용되지 않습니다.)" /></dd>
					</dl>
				</div>
			</div>
	 -->
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->