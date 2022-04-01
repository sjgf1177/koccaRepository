<%
//**********************************************************
//  1. ��      ��: EDUCATION SUBJECT PAGE
//  2. ���α׷���: zu_EducationSubject_L.jsp
//  3. ��      ��: ��������
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 8. 27
//  7. ��      ��:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "MyClassBean" class = "com.credu.study.MyClassBean"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","03");                       // �޴� ���̵� ����

    String  v_process   = box.getString("p_process");
    String  v_user_id   = box.getSession("userid");
    String  v_tem_grcode   = box.getSession("tem_grcode");
    String  v_grcode    =  "";
    String  v_gyear     =  "";
    String  v_grseq     =  "";
    String  v_upperclass=  "";
    String  v_isonoff   =  "";
    String  v_course    =  "";
    String  v_cyear     =  "";
    String  v_courseseq =  "";
    String  v_coursenm  =  "";
    String  v_subj      =  "";
    String  v_subjseqgr =  "";
    String  v_year      =  "";
    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_propstart=  "";
    String  v_propend   =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_eduurl    =  "";
    String  v_isnewcourse=  "";
    String  v_chkfirst  =  "";
    String  v_chkfinal  =  "";
    String  v_cancelkind=  "";
    String  v_proposestatus=  "";
    String  v_userid = "";

    String  v_isgraduated= "";
    String  v_subjtarget=  "";
    String  v_isonoff_value="";
    String  v_isgraduated_value="";
    String  v_today     =  FormatDate.getDate("yyyyMMdd");
    String  v_propstart_date = "";
    String  v_propend_date = "";
    String  v_edustart_date = "";
    String  v_eduend_date = "";
    String  v_billstat	= "";
	int		v_paymoney	= 0;
	String	v_paytype = "";
	String	v_paymethod = "";
	String	v_resultcode = "";

    int     i           =  0;
    int     l           =  0;
	int		v_canceldays = 0;

    ArrayList list1     = null;
    //DEFINED class&variable END

    list1 = (ArrayList)request.getAttribute("CancelPossibleList");   //study.MyClassBean.selectEducationSubjectList(box)
%>

<!------- ��� �ΰ�,�޴�,�������� ���� ���� ------------>
<%@ include file="/learn/user/game/include/topApply.jsp"%>
<!------- ��� �ΰ�,�޴�,�������� ���� ��  ------------->

<script language="javascript">
<!--
    // �����˻� ����ó��
    function search_enter(e)  {
        if (e.keyCode =='13'){  subjSearch();  }
    }

    // �����˻�
    function subjSearch() {
        if (document.form1.p_lsearchtext.value=="") {
            alert("�˻�� �Է����ּ���");
            return;
        }
        document.form1.action = "/servlet/controller.propose.MainSubjSearchServlet";   //
        document.form1.p_process.value = "SubjSearch";
        document.form1.submit();
    }

	function whenProposeCancel(userid,subj,year,subjseq, polityYn, course, iscourseyn) {
		if(confirm('������û�� ��� �Ͻðڽ��ϱ�?')){
			if(polityYn == "N")
			{
				document.form1.p_subj.value = subj;
				document.form1.p_year.value = year;
				document.form1.p_subjseq.value = subjseq;
				document.form1.p_course.value = course;
				document.form1.p_iscourseyn.value = iscourseyn;

				document.form1.action='/servlet/controller.study.MyClassServlet';
				document.form1.p_process.value = 'ProposeCancel';
				document.form1.submit();
			}
			else
			{
				var url = "/servlet/controller.polity.RefundmentAdminServlet";
				var new_Open = window.open("","RefundmentInsert","scrollbars=auto,width=400,height=255,resizable=no");

				document.form1.p_subj.value = subj;
				document.form1.p_year.value = year;
				document.form1.p_subjseq.value = subjseq;
				document.form1.p_course.value = course;
				document.form1.p_iscourseyn.value = iscourseyn;

				document.form1.target = "RefundmentInsert";
				document.form1.action=url;
				document.form1.p_process.value = "RefundmentInsertPage";
				document.form1.submit();
			}
		}
	}

//-->
</script>


<form name= "form1" method= "post">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_userid'    >
    <input type='hidden' name='p_subj'      >
    <input type='hidden' name='p_year'      >
    <input type='hidden' name='p_subjseq'   >
    <input type='hidden' name='p_subjnm'    >
    <input type='hidden' name='p_course'>
    <input type='hidden' name='p_iscourseyn'>

		<!-- title -->
		<table width="720" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td height="35" align="right" background="/images/user/game/apply/<%=tem_subimgpath%>/tit_apply_confirm.gif" class="location" ><img src="/images/user/game/common/location_bl.gif">
			  HOME > ������û > ������û Ȯ��/���</td>
		  </tr>
		  <tr>
			<td height="20"></td>
		  </tr>
		</table>
		<table width="720" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td><img src="/images/user/game/apply/applycan_text.gif"></td>
		  </tr>
		  <tr>
			<td height="10">&nbsp;</td>
		  </tr>
		</table>
		<!-- ������ûȮ��/��� -->
		<table width="720" border="2" cellspacing="0" cellpadding="3" style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
		  <tr  class="lcolor">
			<td height="3" colspan="9" class="linecolor_app"></td>
		  </tr>
<%
		if(v_tem_grcode.equals("N000002")) {	//���Ӿ�ī����
%>
		  <tr>
			<td width="142" class="tbl_gtit2" colspan="2">������</td>
			<td width="80"  class="tbl_gtit2">������û�Ⱓ</td>
			<td width="76"  class="tbl_gtit2">�����Ⱓ</td>
			<td width="41"  class="tbl_gtit">�ݾ�<br></td>
			<td width="49"  class="tbl_gtit2">���籸��</td>
			<td width="48"  class="tbl_gtit">�Աݱ���</td>
			<td width="130" class="tbl_gtit">��ұⰣ</td>
			<td width="86"  class="tbl_gtit">������û���</td>
		  </tr>
<%
		} else {
%>
		  <tr>
			<td width="142" class="tbl_gtit2" colspan="2">������</td>
			<td width="80"  class="tbl_gtit2">������û�Ⱓ</td>
			<td width="76"  class="tbl_gtit2">�����Ⱓ</td>
			<td width="130" class="tbl_gtit">��ұⰣ</td>
			<td width="86"  class="tbl_gtit">������û���</td>
		  </tr>
<%
		}
%>
		  <%

		String v_FinalStatusImg = "";
		String v_PolityYn = "";
		String v_refundmentday = "";
		String v_tmp_course	= "";
		String v_iscourseYn	= "";
		int		v_subjcnt = 0;

		if(list1 != null)
		{
			for(i = 0; i < list1.size(); i++)
			{
			   DataBox dbox = (DataBox)list1.get(i);
			   v_subj               = dbox.getString("d_subj");
			   v_subjseq            = dbox.getString("d_subjseq");
			   v_subjseqgr          = dbox.getString("d_subjseqgr");
			   v_year               = dbox.getString("d_year");
			   v_subjnm             = dbox.getString("d_subjnm");
			   v_propstart          = dbox.getString("d_propstart");
			   v_propend            = dbox.getString("d_propend");
			   v_edustart           = dbox.getString("d_edustart");
			   v_eduend             = dbox.getString("d_eduend");
			   v_chkfinal           = dbox.getString("d_chkfinal");
			   v_isonoff            = dbox.getString("d_isonoff");
			   v_userid             = dbox.getString("d_userid");
			   v_cancelkind         = dbox.getString("d_cancelkind");
			   v_canceldays			= dbox.getInt("d_canceldays");
			   v_billstat			= dbox.getString("d_paystat");
			   v_paymoney			= dbox.getInt("d_paymoney");
			   v_paytype			= dbox.getString("d_paytype");
			   v_refundmentday		= dbox.getString("d_refundmentday");

			   v_course				= dbox.getString("d_course");
			   v_coursenm			= dbox.getString("d_coursenm");
			   v_iscourseYn			= dbox.getString("d_isbelongcourse");
			   v_subjcnt			= dbox.getInt("d_subjcnt");

			   v_paymethod			= dbox.getString("d_paymethod");
			   v_resultcode 		= dbox.getString("d_resultcode");

			   v_propstart_date   = FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd");
			   v_propend_date     = FormatDate.getFormatDate(v_propend,"yyyy/MM/dd");
			   v_edustart_date    = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
			   v_eduend_date      = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");

			   if(v_billstat.equals("Y") && v_paymoney > 0)
				{
					v_PolityYn = "Y";
				}
				else
				{
					v_PolityYn = "N";
				}

			   if(v_isonoff.equals("ON"))      {   v_isonoff_value =   "���̹�";
			   }else if(v_isonoff.equals("OFF")){  v_isonoff_value =   "����"; }

			   //��¥ ó��
				if(v_propstart.equals("")){v_propstart = "����";}
				else{v_propstart     = FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd");}

				if(v_propend.equals("")){v_propend = "����";}
				else{v_propend = FormatDate.getFormatDate(v_propend,"yyyy/MM/dd");}

				if(v_edustart.equals("")){v_edustart = "����";}
				else{v_edustart     = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");}

				if(v_eduend.equals("")){v_eduend = "����";}
				else{v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");}

				if(v_isonoff.equals("ON"))      {   v_isonoff_value =   "���̹�";
				}else if(v_isonoff.equals("OFF")){  v_isonoff_value =   "����"; }

				if(v_cancelkind.equals("")){

					if(v_chkfinal.equals("Y") && (Integer.parseInt(v_refundmentday) >= Integer.parseInt(v_today)))
					{
						v_FinalStatusImg = "<a href=\"javascript:whenProposeCancel('"+v_userid+"','"+v_subj+"','"+v_year+"','"+v_subjseq+"','" + v_PolityYn + "','"+ v_course +"','"+ v_iscourseYn + "')\"><img src='/images/user/game/button/b_apply_cancel.gif' border='0'></a>";
					}else if (v_chkfinal.equals("Y")){
						v_FinalStatusImg = "<img src='/images/user/game/button/b_apply_cancel.gif' >";
					}else if (v_chkfinal.equals("N")){
						v_FinalStatusImg = "<img src='/images/user/game/button/btn_ban.gif' >";
					}else if (v_chkfinal.equals("B")){
						v_FinalStatusImg = "<a href=\"javascript:whenProposeCancel('"+v_userid+"','"+v_subj+"','"+v_year+"','"+v_subjseq+"','" + v_PolityYn + "','"+ v_course +"','"+ v_iscourseYn + "')\"><img src='/images/user/game/button/b_apply_cancel.gif' border='0'></a>";
					}else if (v_chkfinal.equals("M")){
						v_FinalStatusImg = "-";
					}
				}else{
					v_FinalStatusImg  = "-";
				}
/*
				if(v_billstat.equals("W"))
				{
					v_billstat = "�Ա�<br>Ȯ����";
				}else if(v_billstat.equals("Y"))
				{
					v_billstat = "�Ա�<br>Ȯ��";
				}
				else if(v_billstat.equals("CW"))
				{
					v_billstat = "ȯ��<br>ó����";
					v_FinalStatusImg = "<img src='/images/user/game/button/b_apply_ing.gif'>";
				}
				else if(v_billstat.equals("CY"))
				{
					v_billstat = "ȯ�ҿϷ�";
					v_FinalStatusImg = "-";
				}
				else
				{
					v_billstat = "���Ա�";
				}

				if(v_paytype.equals("D"))
				{
					v_paytype = "ī��";
				}else
				{
					v_paytype = "������";
				}
*/
				if(v_paymethod.equals("Card") || v_paymethod.equals("VCard")) {
					v_paytype = "�ſ�ī��";
				} else if(v_paymethod.equals("DirectBank")) {
					v_paytype = "������ü";
				} else if(v_paymethod.equals("BankBook")) {
					v_paytype = "������";
				}

				if(v_paymethod.equals("Card") || v_paymethod.equals("VCard") || v_paymethod.equals("DirectBank")) {
					if(v_resultcode.equals("00")) {
						v_billstat = "�Ա�";
					} else {
						v_billstat = "���Ա�";
					}
				} else {
					v_billstat = "��Ȯ��";
				}
%>

                         <%
			if(v_iscourseYn.equals("Y"))
			{
				if(l == v_subjcnt) v_tmp_course = "";
				if(!v_tmp_course.equals(v_course) && !v_tmp_course.equals("000000"))
				{
					l = 1;
					v_tmp_course = v_course;
%>			<tr>
				<td class="tbl_bleft" rowspan="<%=v_subjcnt %>"><%=v_coursenm%><br><%=v_subjseq %>��</td>
				<td class="tbl_bleft" ><%=v_subjnm%></td>
                <td class="tbl_grc" rowspan="<%=v_subjcnt %>"><%= v_propstart %> ~<br><%= v_propend %></td>
				<td class="tbl_grc" ><%= v_edustart %> ~<br><%= v_eduend %></td>
<%		if(v_tem_grcode.equals("N000002")) {	//���Ӿ�ī����	%>
				<td class="tbl_grc" rowspan="<%=v_subjcnt %>"><%=new java.text.DecimalFormat("###,###,##0").format(v_paymoney)%></td>
				<td class="tbl_grc" rowspan="<%=v_subjcnt %>"><%=v_paytype%></td>
				<td class="tbl_grc" rowspan="<%=v_subjcnt %>"><font color="#3366CC"><%=v_billstat%></font></td>
				<td class="tbl_grc" rowspan="<%=v_subjcnt %>">�н������Ϸκ��� <strong><%=v_canceldays%>��</strong></td>
<%		} else {		%>
				<td class="tbl_grc" rowspan="<%=v_subjcnt %>">�н������Ϸκ��� <strong>����</strong></td>
<%		}		%>
				<td class="tbl_grc" rowspan="<%=v_subjcnt %>"><%=v_FinalStatusImg%></td>
			  </tr>

<%				}else{ l++;%>
				<tr>
					<td class="tbl_bleft"><%=v_subjnm%></td>
					<td class="tbl_grc" ><%= v_edustart %> ~<br><%= v_eduend %></td>
				</tr>
<%				}
			} else {
			%>
				<tr>
					<td class="tbl_bleft" colspan="2"><%=v_subjnm%></td>
					<td class="tbl_grc"><%=v_propstart%> ~<br><%=v_propend%></td>
					<td class="tbl_grc"><%=v_edustart%> ~<br><%=v_eduend%></td>
<%		if(v_tem_grcode.equals("N000002")) {	//���Ӿ�ī����	%>
					<td class="tbl_grc"><%=new java.text.DecimalFormat("###,###,##0").format(v_paymoney)%></td>
					<td class="tbl_grc"><%=v_paytype%></td>
					<td class="tbl_grc"><font color="#3366CC"><%=v_billstat%></font></td>
				<td class="tbl_grc" rowspan="<%=v_subjcnt %>">�н������Ϸκ��� <strong><%=v_canceldays%>��</strong></td>
<%		} else {		%>
				<td class="tbl_grc" rowspan="<%=v_subjcnt %>">�н������Ϸκ��� <strong>����</strong></td>
<%		}		%>
					<td class="tbl_grc"><%=v_FinalStatusImg%></td>
			  </tr>
<%			}

	}
		}%>



			  <!--tr>
                            <td class="tbl_bleft"><%=v_subjnm%></td>
                            <td class="tbl_grc"><%=v_edustart%> ~<br><%=v_eduend%></td>
                            <td class="tbl_grc"><%=v_propstart%> ~<br><%=v_propend%></td>
							<td class="tbl_grc"><%=new java.text.DecimalFormat("###,###,##0").format(v_paymoney)%></td>
							<td class="tbl_grc"><%=v_paytype %></td>
							<td class="tbl_grc"><font color="#3366CC"><%=v_billstat%></font></td>
							<td class="tbl_grc">�н������Ϸκ��� <strong><%=v_canceldays%>��</strong></td>
                            <td class="tbl_grc"><%=v_FinalStatusImg%></td>
                          </tr-->
<%
                           if(i == 0){
%>
							  <tr>
								<td bgcolor="FFFFFF" align="center" colspan="9" height="35">��Ұ����� ������ �����ϴ�.</td>
							  </tr>
<%
                           }
%>

		</table>
</form>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->