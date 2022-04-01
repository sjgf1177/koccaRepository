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
	box.put("submenu","3");
%>


<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="���� ������� �������� ��ġ�Դϴ�">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="Ȩ"> Ȩ > ��ī���� �Ұ� > ������� > <u>��� ���ε༭ ����</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/business.gif" alt="��������ȳ�">
			</div>
		</div><br>
		<dl class="skipcontent">
			<dt>���� �ٷΰ���</dt>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="������"><a href="#skip01" tabindex="121" title="��� ���ε༭ ������ ����� �̵��մϴ�">����</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="������"><a href="#skip02" tabindex="122" title="��� ���ε༭ ������ ��������� �̵��մϴ�">�����</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="������"><a href="#skip03" tabindex="123" title="��� ���ε༭ ������ �������� �̵��մϴ�">����</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="������"><a href="#skip04" tabindex="124" title="��� ���ε༭ ������ �������� ���Ƿ� �̵��մϴ�">�������� ����</a></dd>
		</dl>
		<ul class="category">
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=1" tabindex="171" title="������ ����缺 ������ ���� �Ұ��մϴ�"><img src="/images/2012/sub/page7/tabmenu/business01.png" alt="������ ����缺 ����"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=2" tabindex="172" title="������ ���� �米���� ���� �Ұ��մϴ�"><img src="/images/2012/sub/page7/tabmenu/business02.png" alt="����� ���� �米��"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=3" tabindex="173" title="�¶��� ������ ���� �Ұ��մϴ�"><img src="/images/2012/sub/page7/tabmenu/business03.png" alt="�¶��� ����"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=4" tabindex="174" title="���� ���������� ���� �Ұ��մϴ�"><img src="/images/2012/sub/page7/tabmenu/business04.png" alt="���� ��������"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=5" tabindex="175" title="3D��ü ������ �����η� �缺�� ���� �Ұ��մϴ�"><img src="/images/2012/sub/page7/tabmenu/business05.png" alt="3D��ü ������ �����η� �缺"></a></li>-->
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=6" tabindex="176" title="â������ �缺����� ���� �Ұ��մϴ�"><img src="/images/2012/sub/page7/tabmenu/business06.png" alt="â������ �缺���"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=7" tabindex="177" title="��� ���ε༭ ���� ���� �Ұ��մϴ�"><img src="/images/2012/sub/page7/tabmenu/business07on.png" alt="��� ���ε༭ ����"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=8" tabindex="178" title="���ӱ��� ����ڰ� ������ ���� �Ұ��մϴ�"><img src="/images/2012/sub/page7/tabmenu/business08.png" alt="���ӱ��� ����ڰ� ����"></a></li>-->
		</ul>
		<div class="txtpage">
			<h1>��� ���ε༭ ����</h1>
			<h4>���ε༭������ ����������� �ٽ��帣�� ����� �۷ι� ����� ��ȭ�� ���� â���� ��� ���ε༭ �缺�� ���� ������ۻ���ȸ�� �����Ͽ� ��ϴ� �������α׷��Դϴ�.</h4>
			<h3 id="skip01"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="������ ���ư���"><a href="#" tabindex="181" title="���� ȭ����ġ�� ���� ������ �ø��ϴ�">������ ���ư���</a></p>����</h3>
			<h6>
				<p>����� : ����� �۷ι� ����� ��ȭ�� ���� â���� ��� ���ε༭ �缺</p>
				<p>������� : ������ε༭ �о߿� ��ɰ� ���� �ִ� ��, �����а� ����(����)��</p>
				<p>����� : ������ε༭���� 2�� �� �(���� 20��)</p>
			</h6>
			<br>
			<h3 id="skip02"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="������ ���ư���"><a href="#" tabindex="182" title="���� ȭ����ġ�� ���� ������ �ø��ϴ�">������ ���ư���</a></p>�����</h3>
			<h6>
				<p>�۷ι� ���� ����� ��ȭ�� ��ۿ������� �䱸�� �ݿ��� â���� ������� ���ε༭ �缺, ����</p>
				<p>��������� ��ȹ, ���۰���, ������ �� ������� ȯ�� ��ȭ�� ���� ������ �۷ι� ������ ���� ������ε༭ �����η� �缺</p>
				<p>��󸶺оߺ� �������� �����Ͽ� �ǹ������� �����ġ �� �ܰ躰 ���Ӱ��� ����</p>
				<p>������ ��������� ���� ������ۻ� ��Ʈ��ũ �</p>
			</h6>
			<h3 id="skip03"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="������ ���ư���"><a href="#" tabindex="183" title="���� ȭ����ġ�� ���� ������ �ø��ϴ�">������ ���ư���</a></p>����</h3>
			<h6>
				<p>
					<b>���� ��û/����</b><br>
					- ��û��� : �¶��� ��û(<a href="http://www.dramapd.kr" tabindex="184" target="_blank" title="�¶��� ��û Ȩ�������� �̵��մϴ� (��â)">www.dramapd.kr</a>)<br>
					- ���߱��� : �����ɻ� �� �����ɻ縦 ���� ������ �ڰݱ��ؿ� ������ ������ ����<br>
				</p>
				<br>
				<p>
					<b>�������� �</b><br>
					- ������� : ���� �� �ǽ�����, �������� �ǽ����� �� ������ Ŀ��ŧ���� ���� ���� ���� �߽��� ���� �<br>
					- �������� : ���� �ǹ�������, �а� ������ ��<br>
					- ������� : �ѱ�������ε༭���� ���ǽ�(������ ���̵� KGIT 4��)
				</p>
			</h6>
			<h3 id="skip04"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="������ ���ư���"><a href="#" tabindex="185" title="���� ȭ����ġ�� ���� ������ �ø��ϴ�">������ ���ư���</a></p>�������� ����</h3>
			<h6>
				<p>��ǥ : 02-2161-0072</p>
			</h6>
		</div>
	</div>
	<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>