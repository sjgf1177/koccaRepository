<%
//**********************************************************
//  1. ��      ��: ȸ������_�������
//  2. ���α׷��� : zu_MemberAgree.jsp
//  3. ��      ��: ȸ������_�������
//  4. ȯ      ��: JDK 1.5
//  5. ��      ��: 1.0
//  6. ��      ��: 
//  7. ��      ��:
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
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	//box.put("leftmenu","02");
	box.put("topmenu","1");
	box.put("submenu","2");
	
	String v_username = box.getString("p_username");
    String v_resno    = box.getString("p_resno");
	String v_resno1    = box.getString("p_resno1");
    String v_resno2    = box.getString("p_resno2");
	String v_process  = box.getString("p_process");
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>


<!-- ��ũ��Ʈ���� -->
<script language="JavaScript" type="text/JavaScript">
<!--//

function openSite(siteurl){
    window.open(siteurl);
}

// ȸ�������Է�ȭ������
function memberJoinAgree(){
	// �������
	if(document.form1.p_agree1.checked == false || document.form1.p_agree2.checked == false){
		alert('�̿����� ������ �ּ���');
		return;
	}else{
		document.form1.action="/servlet/controller.homepage.MainMemberJoinServlet";
        document.form1.p_resno.value   = "<%=v_resno %>";
		document.form1.p_resno1.value   = "<%=v_resno1 %>";
        document.form1.p_resno2.value   = "<%=v_resno2 %>";
        document.form1.p_username.value   ="<%=v_username %>";
		document.form1.p_process.value = "CheckAgree";
		document.form1.submit();
	}
}
//-->
</script>

<!-- form ���� -->
<form name="form1"  method="post" action="">
	<input type="hidden"	name="p_process"	value="" >
    <input type="hidden"	name="p_resno" value="" >
	<input type="hidden"	name="p_resno1" value="" >
    <input type="hidden"	name="p_resno2" value="">
    <input type="hidden"	name="p_username" value="" >  
   
	<div class="content">
	
		<table class="rootinfo" cellpadding="0" cellspacing="0" summary="���� ���̴� �������� ��ġ�Դϴ�">
			<tr>
				<td class="pagetitle"><img src="/images/2012/sub/page0/title/member_join.gif" alt="ȸ������"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="Ȩ"> Ȩ > <u>ȸ������</u></td>
			</tr>
		</table>
		
		<table class="joinform" cellpadding="0" cellspacing="0" summary="����Ʈ �̿��� ���̾ƿ�">
			<colgroup><col width="50%"><col width="*"></colgroup>
			<tr>
				<td>
					<img src="/images/2012/sub/page0/join_step01.gif" alt="�������">
					<div class="terms">
						<img src="/images/2012/sub/page0/subject_terms01.gif" alt="����Ʈ �̿���">
						<textarea class="termstxt" tabindex="171" readonly title="����Ʈ �̿���">��1�� ��Ģ

��1�� ����
1.�� ����� "�ѱ���������ī���� (���� "��������ī����"�̶� �Ѵ�.)���� ��ϴ� �������� �̿����� �� ������ ���� ������ �����մϴ�.
2.ȸ�����Կ� ���� ������ ����� �����Ͻ� �� �����Ͽ� �ֽð�, �� ����� �� ��������ī���̿��� ��ϴ� ��� �����񽺸� �̿��ϴ� ��� ȸ������ ����Ǹ� �̸� �а� �����ϴ� ���� ������ å���Դϴ�.

��2�� ����� ���� �� ȿ�� 
1.�� ����� ������ ������ ȭ�鿡 �Խ��ϰų� ��Ÿ�� ������� ȸ������ ���������ν� ȿ���� �߻��մϴ�.
2.��������ī���̴� �� ����� ������ �� ������, ����� ����� ��1�װ� ���� ������� ���������ν� ȿ���� �߻��մϴ�.
3.ȸ�������̶� ��������ī���̿��� �����ϴ� �����񽺿� �����ϴ� ������ �������� ���ǰ� ������ �������� �ǹ��ϸ�, ��������ī���̴� �������� ���ǰ� ������ �׽� ����� �Բ� �Ǵ� ��� ���� ���� �Ǵ� �Ϻθ� �ٲ� �Ǹ��� �����ϴ�. �̷� ��ȭ�� ���� ���� ȸ���� ���ϰų�, ���� ���� ���ߴٴ� ������ ���Ǹ� ������ �� �����Ƿ� ���Ŀ� ���� �����ñ� ��� �帳�ϴ�.

��3�� ��� �� ��Ģ 
�� ����� ���õ��� ���� ������ ������ű⺻��, ������Ż����, ���ڰŷ� �⺻��, ���ڼ�����, ����ǸŹ� �� ��Ÿ ��������� ������ ���մϴ�.

��4�� ����� ���� 
�� ������� ����ϴ� ����� ���Ǵ� ������ �����ϴ�.
1.ȸ�� : ��������ī������ ������ �̿뿡 ���� ����� ü���� �� 
2.���̵�(ID) : ȸ�� �ĺ��� ȸ���� ���� �̿��� ���Ͽ� ȸ���� �����ϰ� ��������ī���̰� �����ϴ� ���ڿ� ������ ����
3.��й�ȣ : ȸ���� ��Ż��� �ڽ��� ����� ��ȣ�ϱ� ���� ������ ���ڿ� ������ ���� 
4.Ż�� : ȸ���� ���� �̿� ���� �� �̿����� ���� ��Ű�� �ǻ�ǥ�� 
5.�޸� ���� : �ֱ� 3�⵿�� �α��� ���� ���� �̿��ڷ� ������ ȸ���� ����
�޸������ �ش��ϴ� ȸ���� ���������� ��ȣ �� ������ ���Ͽ� �������� �� �н��̷��� ������ �� �ֽ��ϴ�.(��, �޸���� �ϰ� ���� �Ŀ��� ������ ����ϴ� ���̵�� ��������ī���� ����Ʈ�� �簡���� �� ������ ���� �����Ͻñ� �ٶ��ϴ�.)

��2�� ���� �̿��� 

��1�� �̿� ����� ���� 
1.���� ���� ��û �� �� ����� �а� "������" ��ư�� ������ �� ����� �����ϴ� ������ ���ֵ˴ϴ�.
2.�̿����� ȸ�� ���� �Է� �� �ϷḦ �����ϰ� �Ǹ� �����մϴ�.
3.ȸ���� �����Ͽ� ���񽺸� �̿��ϰ��� �ϴ� ����ڴ� ��������ī���̿��� ��û�ϴ� ���νŻ������� �����ؾ� �մϴ�.
4.�̿����� ȸ�� 1�δ� 1���� ID�� ü���ϴ� ���� ��Ģ���� �մϴ�.

��2�� �̿��û 
�̿��û�� �¶��λ��� ���Խ�û ��Ŀ� ���մϴ�.

��3�� �̿��û�� �³� 
ȸ���� ��2�� ��2������ ���� ��� ������ ��Ȯ�� �����Ͽ� �̿��û�� �Ͽ��� �� �³��մϴ�.

��4�� �̿��û�� �� �³� 
1.�����񽺴� ������ �ش��ϴ� �̿��û�� ���Ͽ� ����� �ź��ϰų� ��� �Ŀ��� ȸ������ �������� �ʰ� ȸ�������� ���� �Ǵ� ������ �� �ֽ��ϴ�.
1) �ٸ� ����� ���Ǹ� ����Ͽ� ��û�Ͽ��� ��� 
2) �̿� ��û �� �ʿ䳻���� ������ �����Ͽ� ��û�Ͽ��� ��� 
3) ��ȸ�� �ȳ����� �� ��ǳ����� ������ �������� ��û�Ͽ��� ��� 
4) �ſ������� �̿�� ��ȣ�� ���� ������ �ǰ� �ſ�ҷ��ڷ� ��ϵǾ� �ִ� ��� 5) ��Ÿ �̿��û���� ��å������ �̿�³��� ����� ��� 
2.��������ī���̴� ������ �ش��ϴ� �̿��û�� ���Ͽ� �³� ���� ������ �ؼҵ� ������ �³��� ���� ���� �� �ֽ��ϴ�.
1) ��������ī���̰� ������ ������ ���� ��� 
2) ��������ī������ ����� ������ �ִ� ��� 
3) ��Ÿ ��������ī���̰� �ʿ��ϴٰ� �����Ǵ� ��� 
3.��������ī���̴� �̿��û�� ��,�³� �ǰų� �³��� �����ϴ� ��쿡�� �̸� �̿��û�ڿ��� �˷��� �մϴ�.

��5�� �������� ���� 
ȸ���� �̿� ��û �� ������ ������ ����Ǿ��� ��쿡�� �¶��� ������ �ؾ� �մϴ�.

��6�� ȸ�������� ���� 
1.��������ī���̰� �� ���� ���񽺸� ���Ͽ� Ÿ ����� ����, �μ�, �л�, �պ� �� ȸ���� ������ ������ �� �ִ�.
2.���� ��ǰ�� �����Ǵ� �̺�Ʈ�� ���, ��ǰ ��������� ���ǿ� ���� ��÷�� ��������� ������ �� �ֽ��ϴ�.
3.1��, 2���� ������ �߻��� ��� ��������ī���̴� ȸ������ �ش� ����� �����ؾ� �մϴ�.

��7�� �߰����� ȸ�������� ��� 
��������ī���̴� ȸ���� Ŀ�´�Ƽ ���� �̿� �� �����ϴ� ȸ���� �߰� ������ ������, ������ �����θ� ����մϴ�.

��8�� ������ ���� 
��������ī���̴� ȸ���� ���� �̿� �� ��������ī������ ���� ��� �Ǵ� �������񽺿� ���ؼ��� ���ڿ����̳� ���ſ��� ���� ������� ȸ���鿡�� ������ �� �ֽ��ϴ�.

��9�� ����������ȣ �ǹ� 
1.��������ī���̴� �̿����� ���������� ������ �� �ݵ�� ���� �̿����� ���Ǹ� �޽��ϴ�.
2.���������� ���� ������, ���� �� ��2�� ��6�� �̿��� �뵵�� �̿��ϰų� �̿����� ���� ���� ��3�ڿ��� ����, �н�, ����, ����, ������ �׿� ���� �̿����� ���ؿ� ���� ��� å���� ��������ī���̰� ���ϴ�.
3.���������� ��ȣ �� ��뿡 ���ؼ��� ���ù� �� "��������ī����"�� ��������ó����ħ�� ����˴ϴ�. �ٸ�, "��������ī����"�� ���� ����Ʈ �̿��� ��ũ�� ����Ʈ������ "��������ī����"�� ��������ó����ħ�� ������� �ʽ��ϴ�.

��3�� ���� �̿� 

��1�� ���� �̿� 
���� �̿��� ��������ī������ ������ �Ǵ� ����� Ư���� ������ ���� �� ���߹���, 1�� 24�ð��� ��Ģ���� �մϴ�. ��, �������� �� ���� ������ ���Ͽ� ��������ī���̰� �ʿ��ϴٰ� �����Ǵ� ������ �̸� ������ �� ���񽺰� �Ͻ� ������ �� �ֽ��ϴ�.

��2�� ���� ������ ���� 
��������ī���̴� ���� �׿� �ش��ϴ� ��� ������ ������ ������ �� �ֽ��ϴ�.
1.������ ���� ���� ���Ͽ� �ε����� ��� 
2.������Ż������ ������ �Ⱓ��Ż���ڰ� ������ż��񽺸� �����ϴ� ��� 
3.��Ÿ ���񽺸� ������ �� ���� ������ �߻��� ��� 

��3�� ���������� �Ұ� �� ȸ�� ������ ���� 
1.��������ī���̴� ���񽺿� ������ �뷮�� ������ ���ٰ� �ǴܵǸ� �ʿ信 ���� ȸ���� ���� �� �Ż������� ������ �� �ֽ��ϴ�.
2.��������ī���̴� ���� ��� �Ǵ� ���ȿ� ������ �ִٰ� �ǴܵǴ� ȸ���� ���� �� �Ż������� �������� ���� �˻��� �� �ֽ��ϴ�.
3.��1���� ��쿡 ��������ī���̴� �ش� ������ ������ ���� �Ǵ� ���ڿ����� ���Ͽ� �����մϴ�.

��4�� ���� ��� ���� �� ��� ���� 

��1�� ���� ��� ���� 
1.ȸ���� ������ ��뿡 �־ ���� �� ȣ�� �ش���� �ʵ��� �Ͽ��� �ϸ�, �̿� �ش��ϴ� ��� ���� ����� ������ �� �ֽ��ϴ�.
1) �ٸ� ȸ���� ���̵�(ID)�� ���� ����ϴ� ���� 
2) ���������� �������� �ϰų� ��Ÿ ���������� ���õ� ���� 
3) ������ ǳ��, ��Ÿ ��ȸ������ ���ϴ� ���� 
4) Ÿ���� ������ �Ѽ��ϰų� ����ϴ� ���� 
5) Ÿ���� �������� ���� �Ǹ��� ħ���ϴ� ����
6) ��ŷ���� �Ǵ� ��ǻ�͹��̷����� �������� 
7) Ÿ���� �ǻ翡 ���Ͽ� ������ ���� �� ������ ������ ���������� �����ϴ� ���� 
8) ������ �������� ��� ������ �ְų� �� ����� �ִ� ��ü�� ���� 
9) ��Ÿ ������ɿ� ����Ǵ� ���� 

��2�� ��� ���� 
1.ȸ���� �̿����� �����ϰ��� �ϴ� ������ ������ ���� �Ǵ� ���ڿ����� ���Ͽ� �����ϰ��� �ϴ� ���� 1��������(��, �������� ������������ ��� ������ ���� 2��������) �̸� ��������ī���̿� ��û�Ͽ��� �մϴ�.
2.��������ī���̴� ȸ���� ��4�� ��1���� ������ �����ϰ�, ��������ī���� ������ �Ⱓ �̳��� �̸� �ؼ����� �ƴ��ϴ� ��� ���� �̿����� ������ �� �ֽ��ϴ�.
3.��������ī���̴� ��2�׿� ���� ������ ȸ���� �ٽ� �̿��û�� �ϴ� ��� �����Ⱓ �� �³��� ������ �� �ֽ��ϴ�.

��5�� å�� 

��1�� ȸ���� �ǹ� 
1.ȸ�����̵�(ID) �� ��й�ȣ�� ���� ��� ������ å���� ȸ������ �ֽ��ϴ�.
2.ȸ�����̵�(ID) �� ��й�ȣ�� ��������ī������ �����³� ���̴� �ٸ� ������� �絵, �Ӵ�, �뿩�� �� �����ϴ�.
3.�ڽ��� ȸ�����̵�(ID)�� �����ϰ� ���� ���, ȸ���� �ݵ�� ��������ī���̿� �� ����� �뺸�ؾ� �մϴ�.
4.ȸ���� �̿��û���� ���系�� �� ����� ������ �ִ� ��� ���񽺸� ���Ͽ� �� ������ ��������ī���̿� �����Ͽ��� �մϴ�.
5.ȸ���� �� ��� �� ������ɿ��� ������ ������ �ؼ��ؾ� �մϴ�.

��2�� ��������ī������ �ǹ� 
1.��������ī���̴� ��3�� ��1�� �� ��2������ ���� ��츦 �����ϰ� �� ������� ���� �ٿ� ���� ȸ���� ��û�� ���� ���� �����Ͽ� ���񽺸� �̿��� �� �ֵ��� �մϴ�.
2.��������ī���̴� �� ������� ���� �ٿ� ���� �����, ���������� ���񽺸� ������ �ǹ��� �ֽ��ϴ�. ��, �ε����� ���� ȸ�� ���������� �ս��� �߻����� ���� ��������ī���̴� ���� å������ �ʽ��ϴ�.
3.��������ī���̴� ȸ���� ���νŻ������� ������ �³� ���� Ÿ�ο��� ����, �������� �ʽ��ϴ�.��, ����������ȣ��, ������Ű��ù��� �� ������ɿ� ���� ������� ���� �䱸�� �ִ� ��쿡�� �׷����� �ʽ��ϴ�.
4.��������ī���̴� ȸ�����κ��� ����Ǵ� �ǰ��̳� �Ҹ��� �����ϴٰ� �����Ǵ� ��쿡�� ��� ó���ؾ� �մϴ�. �ٸ� ��� ó���� ����� ��쿡�� ȸ������ �� ������ ó�������� �뺸�Ͽ��� �մϴ�.

��3�� �Խù� �Ǵ� ���빰�� ���� 
��������ī���̴� ������ �Խù� �Ǵ� ���빰�� ��4�� ��1���� ������ ���ݵǰų� ��������ī���� ������ �ԽñⰣ�� �ʰ��ϴ� ��� ���� ������ ���� ���� �̸� ������ �� �ֽ��ϴ�.

��6�� ���ع�� �� ��å���� 

��1�� ���ع�� 
��������ī���̴� ���� �̿�� �����Ͽ� ȸ������ ��� ���ذ� �߻��ϴ��� �� ���ذ� ��������ī������ �ߴ��� ���ǿ� ���� ��츦 �����ϰ� �̿� ���Ͽ� å���� ���� �ʽ��ϴ�.

��2�� ��å���� 
1.��������ī���̴� õ������ �Ǵ� �̿� ���ϴ� �Ұ��׷����� ���Ͽ� ���񽺸� ������ �� ���� ��쿡�� ���� ������ ���� å���� �����˴ϴ�.
2.��������ī���̴� ȸ���� ��å������ ���� ���� �̿��� ��ֿ� ���Ͽ� å���� ���� �ʽ��ϴ�.
3.��������ī���̴� ȸ���� ���񽺸� �̿��Ͽ� ����ϴ� �����̳� ���񽺸� ���Ͽ� ���� �ڷ�� ���� ���ؿ� ���Ͽ� å���� ���� �ʽ��ϴ�.
4.��������ī���̴� ȸ���� ���񽺿� ������ ����, �ڷ�, ����� �ŷڵ�, ��Ȯ�� �� ���뿡 ���Ͽ��� å���� ���� �ʽ��ϴ�.
5.ȸ�� ���̵�(ID)�� ��й�ȣ�� ���� �� �̿���� �����Ƿ� ���Ͽ� �߻��Ǵ� ���� �Ǵ� ��3�ڿ� ���� ������� � ���� å���� ��� ȸ������ �ֽ��ϴ�.
6.ȸ���� ��4�� ��1��, ��Ÿ �� ����� ������ ���������� ���Ͽ� ��������ī���̰� ȸ�� �Ǵ� ��3�ڿ� ���Ͽ� å���� �δ��ϰ� �ǰ�, �̷ν� ��������ī���̿��� ���ذ� �߻��ϰ� �Ǵ� ���, �� ����� ������ ȸ���� ��������ī���̿��� �߻��ϴ� ��� ���ظ� ����Ͽ��� �ϸ�, �� ���طκ��� ��������ī���̸� ��å���Ѿ� �մϴ�.

��7�� Ŀ�´�Ƽ �̿� 

��1�� Ŀ�´�Ƽ �̿� ���� 
1.��������ī���̴� �̿��ڵ��� �ڽ��� �ǰ��� �����ϰ�, ������ ���ɻ縦 ������ �� �ֵ��� Ŀ�´�Ƽ ���񽺸� ��ϰ� �ֽ��ϴ�.
2.���� ������ Ŀ�´�Ƽ �̿��� ������ �� �ִ� �׸���̸�, �̵� �׸񿡸� ���ѵǴ� ���� �ƴմϴ�.
1) Ÿ���� �����̹��� �� ��ǥ ��ǥ�ǰ� ���� �����Ǹ��� �Ѽ�, �ǿ�, ����, �����ϰų� �������ų� �Ǵ� �޸� �����ϴ� ���� 
2) ������, ����, ���� �Ѽ���, ħ����, ����, �󽺷��ų� �Ǵ� �ҹ����� ����, �̸�, �ڷ� �Ǵ� ������ ����, ���, �Խ�, ���� �Ǵ� ������Ű�� ���� 
3) ���������ǹ��� ���Ͽ� ��ȣ�Ǵ� ����Ʈ���� �Ǵ� ��Ÿ �ڷḦ �����ϴ� ������ ���ε� �ϴ� ����, �ٸ� �̿��ڰ� �׿� ���� �Ǹ��� ���� �Ǵ� �����ϴ� ���, �Ǵ� �ʿ��� ���Ǹ� ��� ���� ���� �����մϴ�.
4) Ÿ���� ��ǻ�͸� �ջ��ų �� �ִ� ���̷���, ������ ȭ��, �Ǵ� ��Ÿ ������ ����Ʈ���� �Ǵ� ���α׷��� �����ϴ� �ڷḦ ���ε� �ϴ� ���� 
5) ������� �������� ��ǰ �Ǵ� ���񽺸� ���� �Ǵ� �Ǹ��ϴ� ���� 
6) �ڷ�����, ���׽�Ʈ, �Ƕ�̵� ü�踦 ���ϰų� ����� ������ ������ ���� 
7) �ٸ� Ŀ�´�Ƽ �̿��ڰ� ������ ������ �����ϰ� ������ �� ���� ������ �̿��ڰ� �˰� �ְų� �ո������� �� ������ ���Ǵ� ������ �ٿ�ε��ϴ� ���� 
8) ���ε� �� ���Ͽ� ���Ե� ����Ʈ���� �Ǵ� ��Ÿ �ڷ��� ����ǥ��, ������ �Ǵ� ��Ÿ ������ ���ǻ���, �Ǵ� ��ǥ��, �Ǵ� �� ��ó ���� �ٿ��� �Ǵ� ǥ���� ���� �Ǵ� �����ϴ� ���� 
9) �ٸ� �̿��ڰ� Ŀ�´�Ƽ�� ����ϰų� ���� ���� �����ϰų� ������Ű�� ���� 
10) �������� �� ��ǳ��ӿ� ���ϴ� ������ �����̳�, Ư�� ������ �����������ϰų� ����ϴ� ����, ��Ÿ ���������� ���߽�Ű�� ���� �� ������� ������ �����ϴ� ���� 3. ��������ī���̴� �������� � �����ε� �ƹ��� ���� ���� ���ϰ� Ŀ�´�Ƽ�� �Ϻ� �Ǵ� ���ο� �����ϴ� ���� �����ų �� �ִ� ������ �����մϴ�.

��2�� ��������ī������ å�� 
1.��������ī���̴� Ŀ�´�Ƽ�� �˿��� �ǹ��� ���� �ʽ��ϴ�. �׷���, ��������ī���̴� ����Ǵ� ����, ����, ���� ���� �Ǵ� ������ ��û�� �����ϱ� ���Ͽ� ������ �����ϰų�, �Ǵ� ��������ī������ ������ �緮���� ���� ���� �ڷ��� ���� �Ǵ� �Ϻθ� ���� �Ǵ� �ۺ� �����ϰų�, �Ǵ� �̸� ������ �Ǹ��� �����մϴ�.
2.���ϴ� ��� Ŀ�´�Ƽ ���񽺰� �������� ������� ������ ����� �ƴϸ�, �� ��� �̿����� ����� ���ϰ� ���� ���ϰ� �ٸ� ����� ���Ͽ� ���� �� ������ �����մϴ�. ������ ���� ������ �ĺ��� �� �ִ� �̿��� �Ǵ� �̿����� �ڳ�鿡 ���� ������ Ŀ�´�Ƽ�� ���� ���� �׻� ���Ǹ� ��￩�� �մϴ�. ��������ī���̴� Ŀ�´�Ƽ�� �ִ� ����, �޽��� �Ǵ� ������ �����ϰų� �̸� �������� �ƴ��մϴ�.
3.��������ī���̴� Ŀ�´�Ƽ�� ���� å�� �� �̿��ڰ� Ŀ�´�Ƽ�� ���������ν� �߻��ϴ� ��� ��ǿ� ���� å���� ������ �����մϴ�.

��8�� ��Ÿ 

��1�� ����� ���� 
�̿��� ���� �� �ּ� 7�� ���� ������ �ǹ��� �ְ� �Һ��ڿ��� �Ҹ��� ��� 30�� ���� �����ؾ� �մϴ�.

��2�� ������ �ذ� 
1.��������ī���̿� ȸ���� ���񽺿� �����Ͽ� �߻��� ������ �����ϰ� �ذ��ϱ� ���Ͽ� �ʿ��� ��� ����� �Ͽ��� �մϴ�.
2.��1���� �������� �ұ��ϰ�, �� �������� ���Ͽ� �Ҽ��� ����� ��� �λ�Ҽ۹����� ���ҹ����� ������ ���� �������� �մϴ�.

��Ģ
������ :�� ����� 2009�� 6�� 19�Ϻ��� �����Ǿ����ϴ�.
������ :�� ����� 2011�� 9�� 30�� �����Ǿ����ϴ�.
������ :���������� 2011�� 10�� 1�Ϻ��� �����մϴ�.</textarea>
						<table class="consent" cellspacing="0" cellpadding="0" summary="������� ���̾ƿ�">
							<tr>
								<td class="alignL"><input type="checkbox" tabindex="172" name="p_agree1" id="agreen" class="checkbox" title="����� ���� �ϽŴٸ� ���콺�� üũ���ּ���."><label for="agreen" class="label">�� ����� �����մϴ�</label></td>
							</tr>
						</table>
					</div>
					<div class="terms">
						<img src="/images/2012/sub/page0/subject_terms02.gif" alt="�����ϴ� ���������� �׸�">
						<textarea class="termstxt" readonly tabindex="173" title="�����ϴ� ���������� �׸�">�����ϴ� ���������� �׸�
1.����������ī���̡��� ȸ������, ��Ȱ�� �������, ���� ������ ������ ���� ���� ȸ������ ��� �Ʒ��� ���� ���������� �����ϰ� �ֽ��ϴ�. 

<�Ϲ� ȸ������>
-����, ���̵�, ��й�ȣ, ����ó(��ȭ��ȣ, �޴�����ȣ �� ����), �ּ�(��������), �̸���, ����, ����, ����о�

<�Ǹ�Ȯ�� ȸ������ : �ֹι�ȣ/������ ����>
-����, �ֹε�Ϲ�ȣ(������ ȸ���� �������, ����, ������ ��ȣ), �� 14�� �̸��� �����븮�� ����, ������������ 

<�ܱ���/��ܱ��� �Ϲ� ȸ������>
-���� �ܱ��� ������ : ����, ���ǹ�ȣ Ȥ�� �ܱ��ε�Ϲ�ȣ, ���̵�, ��й�ȣ, ����ó(��ȭ��ȣ, �޴��� ��ȣ �� ����), �����ּ�, ���ǰ����ڴ� ����, �ܱ��ε�Ϲ�ȣ �����ڴ� �� ��, �� 14�� �̸��� �����븮������. ������������ 
-�ؿ� �ܱ��� ������ : ����, ���̵�, ��й�ȣ, ����ó(��ȭ��ȣ, �޴�����ȣ �� ����), �����ּ�, ����, ����, �������, ������������ 


2.��Ű(Cookie)�� ���� ������������

��������ī���̴� �� ���񽺿� �����Ͽ� ������ �� ���� �̿� ���� ������ ���÷� �����ϰ� ã�Ƴ��� ��Ű(cookie)�� ��մϴ�. 
��Ű�� ��������ī���� ����(http://edu.kocca.or.kr)�� ���� �� �ڵ����� ������ ��ǻ�ͷ� ���۵Ǵ� ���� ���� �뷮�� �ؽ�Ʈ ���Ϸμ� �̿����� ��ǻ�� �ϵ��ũ�� ��ȣȭ�Ǿ� ����˴ϴ�.
��������ī���̴� ������ ���� ������ ���� ��Ű�� ����մϴ�. ��������ī���̴� ȸ���� ��ȸ���� ���� ���� ���̳� �ݺ� ��� ���� �� �̿����� �̿� �ൿ�� ���� ������ ����մϴ�. 
��Ű�� ��������ī���� ����(http://edu.kocca.or.kr)���� ID������ ���ؼ��� ID���� ����� ���� ���Ͽ� ���Ǹ�, ��Ÿ �̺�Ʈ�� �������翡�� ������ ���� ����� Ȯ���ϱ� ���ؼ��� ���˴ϴ�. �̷��� ��Ű���� �̿��Ͽ� ���ϰ� �湮�� ��������ī���� ����(http://edu.kocca.or.kr)�� �̿����¸� �ľ������ν� �� �����ϰ� �̿��ϱ� ������ ���񽺸� ����� ������ �� �ְ� �˴ϴ�. 
���ϴ� ��Ű�� ���� ���ñ��� ������ �ֽ��ϴ�. ������ ������������ �ɼ��� ���������ν� ��� ��Ű�� ����ϰų�, ��Ű�� ����� ������ Ȯ���� ��ġ�ų�, ��� ��Ű�� ������ �ź��� �� �ֽ��ϴ�.
��, ��Ű�� ������ �ź��Ͻ� ��� ID���� �α����� �ʿ��� ��������ī���� ����(http://edu.kocca.or.kr)�� �̿뿡 ������ �� �� �ֽ��ϴ�.


3.���� ���� �̿� �������� �Ʒ��� ���� ���� �������� ������ �� �ֽ��ϴ�. 

-�ſ�ī�� ������ : ī����, ī���ȣ �� 
-�޴���ȭ ������ : �̵���ȭ��ȣ, ��Ż�, �������ι�ȣ �� 
-������ü�� : �����, ���¹�ȣ ��</textarea>
					</div>
				</td>
				<td>
					<div class="terms2">
						<img src="/images/2012/sub/page0/subject_terms03.gif" alt="���������� ���� �� �̿� ����">
						<textarea class="termstxt" tabindex="174" readonly title="���������� ���� �� �̿� ����">���������� ���� �� �̿� ����
1.���� ������ ���� ��� ���� �� ���� ������ ���� ������� ������ ����, ��ǰ���, ��������, ���� �� ��� ���� 

2.ȸ������ 
ȸ���� ���� �̿� �� ������ ���� Ȯ������ ���� ����Ȯ��, ���νĺ�, ���� �̿������ ���ΰ� ������, �����ǻ� Ȯ��, ��14�� �̸� �Ƶ� �������� ���� �� ���� �븮�� ���ǿ� �� Ȯ��, ���� ���� �븮�� ����Ȯ��, ���� ������ ���� ��Ϻ���, �Ҹ�ó�� �� �ο�ó��, �������� ����

3.�ű� ���� ���� �� �����á��������� Ȱ�� 
�ű� ���� ���� �� ���� ���� ����, ������� Ư���� ���� ���� ���� �� ���� ����, ������ ��ȿ�� Ȯ��, �̺�Ʈ �� ������ ���� ���� �� ������ȸ ����, ���Ӻ� �ľ�, ȸ ���� ���� �̿뿡 ���� ���</textarea>
					</div>
					<div class="terms2">
						<img src="/images/2012/sub/page0/subject_terms04.gif" alt="���������� ���� �� �̿�Ⱓ">
						<textarea class="termstxt" tabindex="175" readonly title="���������� ���� �� �̿�Ⱓ">���������� ���� �� �̿�Ⱓ
�̿����� ���������� ��Ģ������ ���������� ���� �� �̿������ �޼��Ǹ� ��ü ���� �ı��մϴ�. ��, ������ ������ ���ؼ��� �Ʒ��� ������ ������ �Ⱓ ���� �����մϴ�.

1. ����������ī���̡� ���� ��ħ�� ���� �������� ���� 
- �����̿��� 
���� ���� : ���� �̿� ���� 
���� �Ⱓ : 1�� 

2. ���ù��ɿ� ���� �������� ���� 
���, ���ڻ�ŷ� ����� �Һ��ں�ȣ�� ���� ���� �� ��������� ������ ���Ͽ� ������ �ʿ䰡 �ִ� ��� ����������ī���̡��� ������ɿ��� ���� ������ �Ⱓ ���� ȸ�������� �����մϴ�. �� ��� ����������ī���̡��� �����ϴ� ������ �� ������ �������θ� �̿��ϸ� �����Ⱓ�� �Ʒ��� �����ϴ�.

- ������Ʈ �湮��� 
���� ���� : ��ź�к�ȣ�� 
���� �Ⱓ : 3���� 

- ����Ȯ�ο� ���� ��� 
���� ���� : ������Ÿ� �̿����� �� ������ȣ � ���� ���� 
���� �Ⱓ : 6���� 

- �Һ����� �Ҹ� �Ǵ� ����ó���� ���� ��� 
���� ���� : ���ڻ�ŷ� ����� �Һ��ں�ȣ�� ���� ���� 
���� �Ⱓ : 3�� 

- ��� öȸ � ���� ���
���� ���� : ���ڻ�ŷ� ����� �Һ��ں�ȣ�� ���� ���� 
���� �Ⱓ : 5�� 

- ��ݰ��� �� ��ȭ ���� ���޿� ���� ��� 
���� ���� : ���ڻ�ŷ� ����� �Һ��ں�ȣ�� ���� ���� 
���� �Ⱓ : 5��</textarea>
					</div>
					<div class="terms2">
						<img src="/images/2012/sub/page0/subject_terms05.gif" alt="���������� �� 3�� ���� �� �����Ź">
						<textarea class="termstxt" tabindex="176" readonly title="���������� �� 3�� ���� �� �����Ź">����������ī���̡��� ���� ����� ���ؼ� �Ʒ��� ���� �������� ������ ��Ź�ϰ� ������, ���� ���ɿ� ���� ��Ź��� �� ���������� �����ϰ� ������ �� �ֵ��� �ʿ��� ������ �����ϰ� �ֽ��ϴ�. 

[��Ź��ü] (��)�޳�
[��Ź ���� ����] ���� � �� ������� ����
[���������� ���� �� �̿� �Ⱓ] ȸ��Ż�� �� Ȥ�� ��Ź��� ���� �� ���� 

��������ī���̴� ����������ȣ�� ��15����1��, ��17����1�� �� ��3���� �ٰŷ� ���� ��ȣ�� ���������� ���� ���� �뵵�� �̿��ϰų� �̸� ��3�ڿ��� ������ �� �ֽ��ϴ�.

1. ����ۼ� �� �м����� ���� ������ ���Ͽ� �ʿ��� ���(��, ��������� ����)
2. ����, �� ���� ���������� ������ ���Ͽ� �ܱ����� �Ǵ� �����ⱸ�� �����ϱ� ���Ͽ� �ʿ��� ���
3. ������ ����� ������ ���� �� ������ ���Ͽ� �ʿ��� ���
4. ������ ���Ǿ��� ������ ���Ͽ� �ʿ��� ���</textarea>
						<table class="consent" cellspacing="0" cellpadding="0" summary="��� ���� ���̾ƿ�">
							<tr>
								<td class="alignL"><input type="checkbox" tabindex="177" id="agreen2" name="p_agree2" class="check" title="����� ���� �ϽŴٸ� ���콺�� üũ���ּ���."><label for="agreen2" class="label">�� ����� �����մϴ�.</label></td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		
		
		<table class="allconsent" cellspacing="0" cellpadding="0" summary="��� ���� ����">
			<colgroup><col width="50%"><col width="*"></colgroup>
			<tr>
				<td class="alignR"><a href="javascript:memberJoinAgree();" tabindex="178" title="����� �����Ͻø� �� ��ư�� Ŭ���Ͽ� ���� �ܰ�� �������ּ���."><img src="/images/2012/sub/page0/btn_consent.gif" alt="����� �����մϴ�"></a></td>
				<td class="alignL"><a href="javascript:history.go(-1);" tabindex="179" title="����� �������� �ʰ�, ���� �������� ���ư��ϴ�"><img src="/images/2012/sub/page0/btn_notconsent.gif" alt="����� �������� �ʽ��ϴ�"></a></td>
			</tr>
		</table>	
	</div>
</form>	
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>	