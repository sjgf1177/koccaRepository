<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	
	String  v_process  = box.getString("p_process");
	
	String s_userid   = box.getSession("userid");
	String s_username = box.getSession("name");
	
	HashMap<String, String> upperMap = null;    // ��з� �� �޴�ID ���� �� HashMap<upperlcass, menuid>
	
	upperMap   = ClassifySubjectBean.getMenuId(box);
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>

<script language="javascript">

	function selectArea( val ) {
		document.form1.tabid.value = val;
		document.form1.p_process.value = "edu";
		document.form1.action = "/servlet/controller.homepage.HomePageAboutUsServlet";
		document.form1.submit();
	}

</script>

<form name="form1" method="post">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "gubun" value = "7">
    <input type = "hidden" name = "menuid">
    <input type = "hidden" name = "tabid" value="<%= box.get("tabid") %>">
    
<% if      ("1".equals(box.get("tabid")) || "".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="��������ȳ�"     class="fl_l" /><p class="category">Home &gt; ��ī���̼Ұ� &gt; <strong>����������缺����</strong></p></h2><% }
   else if ("2".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="��������ȳ�"     class="fl_l" /><p class="category">Home &gt; ��ī���̼Ұ� &gt; <strong>����������米��</strong></p></h2><% }
   else if ("3".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="��������ȳ�"     class="fl_l" /><p class="category">Home &gt; ��ī���̼Ұ� &gt; <strong>�¶��α���</strong></p></h2><% }
   else if ("4".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="��������ȳ�" 	  class="fl_l" /><p class="category">Home &gt; ��ī���̼Ұ� &gt; <strong>��Ź����</strong></p></h2><% }
   else if ("5".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="��������ȳ�"     class="fl_l" /><p class="category">Home &gt; ��ī���̼Ұ� &gt; <strong>������������</strong></p></h2><% } 
%>
    
    <ul class="tabwrap">
		<li><a href="javascript:selectArea('1');" class="tab_online<%= box.get("tabid").equals("1") || box.get("tabid").equals("") ? " tab_online_on" : "" %>"><span>����������缺����</span></a></li>
		<li><a href="javascript:selectArea('2');" class="tab_online<%= box.get("tabid").equals("2") ? " tab_online_on" : "" %>"><span>����������米��</span></a></li>
		<li><a href="javascript:selectArea('3');" class="tab_online<%= box.get("tabid").equals("3") ? " tab_online_on" : "" %>"><span>�¶��α���</span></a></li>
		<li><a href="javascript:selectArea('4');" class="tab_online<%= box.get("tabid").equals("4") ? " tab_online_on" : "" %>"><span>��Ź����</span></a></li>
		<li><a href="javascript:selectArea('5');" class="tab_online<%= box.get("tabid").equals("5") ? " tab_online_on" : "" %>"><span>������������</span></a></li>
	</ul>
	
	<table class="row_list">
        <tbody>
          <tr></tr>
        </tbody>
        <colgroup>
        <col width="100%" />
        </colgroup>
      </table>
    
<table width="690"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20">&nbsp;</td>
  </tr>
  <tr>
    <td>������ ���� �缺������ �̷� ������ ����� �̲� û�� ������ ������� ������ â�ۿ����� ������ ������ ���� ������� <br />
    ���� ���ѹα� 1%�� â�� ����缺�� ���� ��ȭü���������� �������� �����Ǹ� ������� ���� �������� ��Ǵ� ����<br />
    �缺���α׷��Դϴ�.</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_01.gif" alt="����" class="tit" /></td>
  </tr>
  <tr>
    <td class="pd_l15">- ����� : ���ѹα� ������ �о��� 1%�� �۷ι� â�� ���� �缺 <br />
      - ������� : ������ �����а� ������, ������ ���úо� ������ <br />
      - ����� : 5~10���� / �ߤ���� ���� � <br />
    - ������&nbsp; : 50~100���� </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_02.gif" alt="����" class="tit" /></td>
  </tr>
  <tr>
    <td class="pd_l15">- ������ ���ۺо�&nbsp; ��� ���� ����, �ǹ��� �ٽ� ����Ʈ �缺 <br />
      - 3D��ü, ����, ��ȹâ��, �����Ͻ� �� �������о� â�� ���� �η� �缺 <br />
      - ������ �ְ� ������ Ȱ��, ���� ����������� Job-matching ���� ���� <br />
    - ������ ����� ����Ʈ û�� �η� ���� ü�� ���� <br /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_03.gif" alt="����" class="tit" /></td>
  </tr>
  <tr>
    <td height="25" class="pd_l15"><strong>�� ���� ��û/ ���� -&gt; �� �������� � -&gt; �� ���������� -&gt; �� ��� ����</strong></td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong>���� ��û/����</strong></td>
  </tr>
  <tr>
    <td class="pd_l25">- ���� ��û/���� <br />      - ��û��� : �¶��� ��û <br />
      - ���⼭�� : (����)���������� <br />      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(����)��Ʈ������, ���úо� �ڰ��� <br />
      - �������� : 1�� �����ɻ� -&gt;2�� ������ ���� ���� <br />
      - ���߹�� : ������ ���� ����� ���� �ɻ�����ȸ ���� <br />      &nbsp; &bull; 1�� �����ɻ� : ��������, �о���ȹ, ���úо� ��� �� �ɻ� <br />      &nbsp; &bull; 2�� ������ ���� ���� : �����о� ��������, ���, �о���ȹ �� �ɻ� <br /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong>�������� � </strong></td>
  </tr>
  <tr>
    <td class="pd_l25"> - ������� : ���� �� �ǽ�����, �������� �ǽ� ����, ������Ʈ ���� <br />
      - �������� : ���úо� �ǹ� �ɷ� ��� �߽��� Ŀ��ŧ�� ���� <br />
      - �������� : ����ǹ�������, �а������� ������ ����, � <br />
      - �����ý��� �� ��� : ���� �ְ��� ������ ���۽ý��� �� ���� ��� Ȱ���� <br />
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���� �ǹ� �߽��� ���� � <br />
    - ���������� : ���� �ǹ� ���� �� �̷� ��ȭ�� ���� ���, ����, �̷� ���� <br /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong>�������</strong></td>
  </tr>
  <tr>
    <td class="pd_l25"><p>        - ���� ����� �켱 ��� ��ȸ ���� <br />
      - ������ ���ۻ�� ������&nbsp; ������� ���α׷� � <br />
      - ä��ڶ�ȸ, ����ȸ(����Ÿ ��) ��ǰ ���ø� ���� ��� ���� <br />
      - ������ DB ������ ���� ü���� ��� ���� � <br />
    </p></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_04.gif" alt="������ �ȳ�" class="tit" /></td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong> ������ü �� ī�����</strong></td>
  </tr>
  <tr>
    <td class="pd_l25"> - ������ü : �츮���� 1005-500-541698 / ������ : �ѱ������������<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(�Ա�Ȯ�� �� ���� ����� ��꼭 �߱�)<br />
- ī����� : �ѱ������� ��ī���� Ȩ������(http://edu.kocca.or.kr)���� �¶��� ����<br /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_05.gif" alt="�������� ����" class="tit" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&bull; ��ۿ���о� : �� 02-2161-0072 / e-mail : offline@kocca.kr / Fax : 02-2161-0078 <br />  &bull; �������ۺо� : �� 02-3219-6541 / e-mail : offline@kocca.kr / Fax : 02-3219-6501 <br />  &bull; ��������ȹ�о� : �� 02-3219-6545 / e-mail : offline@kocca.kr / Fax : 02-3219-6501
<p></p></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>

</form>
<!-- form �� -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->

