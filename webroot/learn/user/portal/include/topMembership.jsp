<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "com.credu.library.RequestBox" %>

<%

	String mainType=box.getSession("tem_main_type");  //메인 화면 타입
	
	boolean newAsp = mainType.equals("A") || mainType.equals("B") ? true : false;
%>

<%if(newAsp){%>
	<div class="sub_img">
		<div class="sub_text">
			<%if(mainType.equals("A")){%>
				<p class="sub_text_title"><span style="font-size: 15px;font-weight: 600;line-height: 36px;letter-spacing:-3px;color:#fff;">콘텐츠아카데미</span><span style="font-size: 22px;font-weight: 600;margin-left: 5px;letter-spacing: -3px;color:#fff;">Membership</span></p>
				<p class="sub_text_explain" style="color:#fff;">Membership 에서는 한국콘텐츠아카데미를 이용하시는 분들께<br>다양한 혜택을 제공하고자 회원서비스를 실시하고 있습니다.</p>
			<%}else{%>
				<p class="sub_text_title"><span style="font-size: 15px;font-weight: 600;line-height: 36px;">콘텐츠아카데미</span><span style="font-size: 25px;font-weight: 600;margin-left: 8px;">Membership</span></p>
				<p class="sub_text_explain" >Membership 에서는 한국콘텐츠아카데미를 이용하시는 분들께 다양한 혜택을 제공하고자<br> 회원서비스를 실시하고 있습니다.</p>
			<%}%>
		</div>
	</div>

<%}else{%>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="20">&nbsp;</td>
			<td><img src="/images/asp/membership/topimg.gif" width="920" height="135" /></td>
			<td width="20">&nbsp;</td>
		</tr>
	</table>
<%}%>
