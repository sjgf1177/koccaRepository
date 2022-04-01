<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "com.credu.library.RequestBox" %>

<%
    String visualFlashGubun = box.getSession("s_gubun").equals("99") ? "7" : box.getSession("s_gubun") ;

%>

<div class="sub_img">
	<div class="sub_text">
		<%if(mainType.equals("A")){%>
			<%if (visualFlashGubun.equals("5")) {%>
				<p class="sub_text_title"><span style="font-size: 15px;font-weight: 600;line-height: 36px;color:#fff;">콘텐츠아카데미</span><span style="font-size: 22px;font-weight: 600;margin-left: 5px;letter-spacing: -3px;color:#fff;">열린강좌</span></p>
				<p class="sub_text_explain" style="color:#fff">열린강좌는 여러분들께서 직접 참여할 수 있는 다양한 부가콘텐츠를 제공하고 있습니다.<br>분야별로 다양한 주제의 콘텐츠를 경험해 보세요.</p>
			
			<%} else {%>
				<p class="sub_text_title"><span style="font-size: 15px;font-weight: 600;line-height: 36px;color:#fff;">콘텐츠아카데미</span><span style="font-size: 22px;font-weight: 600;margin-left: 5px;letter-spacing: -3px;color:#fff;">온라인강좌</span></p>
				<p class="sub_text_explain" style="color:#fff">온라인과정은 방송영상, 게임개발, 문화콘텐츠 관련 콘텐츠를 웹기반 교육시스템을 통해<br>시간과 공간의 제약없이 누구나 교육을 받을 수 있는 '열린'공간입니다.</p>
			<%}%>
		<%}else{%>
			<%if (visualFlashGubun.equals("5")) {%>
				<p class="sub_text_title"><span style="font-size: 15px;font-weight: 600;line-height: 36px;">콘텐츠아카데미</span><span style="font-size: 25px;font-weight: 600;margin-left: 8px;">열린강좌</span></p>
				<p class="sub_text_explain" >열린강좌는 여러분들께서 직접 참여할 수 있는 다양한 부가콘텐츠를 제공하고 있습니다.<br>분야별로 다양한 주제의 콘텐츠를 경험해 보세요.</p>
			
			<%} else {%>
				<p class="sub_text_title"><span style="font-size: 15px;font-weight: 600;line-height: 36px;">콘텐츠아카데미</span><span style="font-size: 25px;font-weight: 600;margin-left: 8px;">온라인강좌</span></p>
				<p class="sub_text_explain" >온라인과정은 방송영상, 게임개발, 문화콘텐츠 관련 콘텐츠를 웹기반 교육시스템을 통해<br>시간과 공간의 제약없이 누구나 교육을 받을 수 있는 '열린'공간입니다.</p>
			<%}%>
		<%}%>
	</div>
</div>