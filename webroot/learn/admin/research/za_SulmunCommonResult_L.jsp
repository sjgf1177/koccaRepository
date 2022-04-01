<%
//**********************************************************
//  1. ��      ��: ����
//  2. ���α׷���: za_SulmunCommonResult_L.java
//  3. ��      ��: �Ϲݼ��� - ���������ȸ
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: lyh
//**********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_grcode    = box.getStringDefault("s_grcode","ALL");        //�����׷�

    String  v_subj        = box.getStringDefault("s_subjcourse",  SulmunCommonBean.DEFAULT_SUBJ);
    String  v_gyear       = box.getString("s_gyear");
    String  v_subjseq       = box.getStringDefault("s_subjseq","0001");
    int  v_sulpapernum       = box.getInt("s_sulpapernum");

    String  v_company        = box.getString("s_company");
    String  v_jikwi       = box.getString("s_jikwi");
    String  v_jikun       = box.getString("s_jikun");
    String  v_workplc       = box.getString("s_workplc");

    
    int     v_replycount  = box.getInt("p_replycount");
    int     v_studentcount= box.getInt("p_studentcount");
    double  v_replyrate   = 0;
    if (v_studentcount != 0) {
        v_replyrate = (double)Math.round((double)v_replycount/v_studentcount*100*100)/100;
    }
    
	String  s_gadmin = box.getSession("gadmin");

    String v_tab_color1 = "black";
    String v_tab_color2 = "black";
    String v_tab_color3 = "blue";
	String v_tab_color4 = "black";
	String v_tab_color5 = "black";
	DecimalFormat  df = new DecimalFormat("0.00");
%>
<html>
<head>
<title>�����м�-������</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script> 
<script language="JavaScript">
<!--

// ��ȸ �˻�
function whenSelection(p_action) {
	if (document.form2.s_grcode.value == '----') {
			alert("�����׷��� �����ϼ���.");
			return ;
	}
    if (p_action=="go") {
        if (document.form2.s_gyear.value=="----"){
            alert("������ �����ϼ���");
            return;
        }
		if (document.form2.s_sulpapernum.value=='0'){
            alert("�������� �����ϼ���");
            return;
        }
    }
  document.form2.target = "_self";
  document.form2.p_action.value  = p_action;
  document.form2.p_process.value = 'SulmunResultPage';
  document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
  document.form2.submit();
}

// �������
function goExcel(p_action) {
	if (document.form2.s_grcode.value == '----') {
			alert("�����׷��� �����ϼ���.");
			return ;
	}

    if (document.form2.s_gyear.value=="----"){
        alert("������ �����ϼ���");
        return;
    }
	if (document.form2.s_sulpapernum.value=='0'){
        alert("�������� �����ϼ���");
        return;
    }

    /*
    var v_grcode   = document.form2.s_grcode.value;
    var v_gyear    = document.form2.s_gyear.value;    
    var s_sulpapernum    = document.form2.s_sulpapernum.value;        
    var v_param  = '?p_grcode='+v_grcode+'&p_gyear='+v_gyear+'&p_sulpapernum='+s_sulpapernum; 

    window.self.name = "reporting";
    document.form2.target = "openExcel1";    
    open_window("openExcel1","","100","100","900","600","yes","yes","yes","yes","yes");                 
    document.form2.action='/learn/admin/research/CommonResult_E.jsp'+v_param;                 
    document.form2.submit();
    */

      document.form2.target = '_self';
      document.form2.p_action.value  = p_action;
      document.form2.p_process.value = 'SulmunResultExcelPage';
      document.form2.submit();
}

// �������
function goExcelDetail(p_action) {
	if (document.form2.s_grcode.value == '----') {
			alert("�����׷��� �����ϼ���.");
			return ;
	}

    if (document.form2.s_gyear.value=="----"){
        alert("������ �����ϼ���");
        return;
    }
	if (document.form2.s_sulpapernum.value=='0'){
        alert("�������� �����ϼ���");
        return;
    }

    document.form2.target = '_self';
    document.form2.p_action.value  = p_action;
    document.form2.p_process.value = 'SulmunDetailResultExcelPage';
    document.form2.submit();
}

// �ٸ� ���� �̵�
function changeTabpage(p_gubun) {

	if (p_gubun == "TARGET") {
      document.form2.action = "/servlet/controller.research.SulmunTargetResultServlet";

	} else if (p_gubun == "ALL") {
      document.form2.action = "/servlet/controller.research.SulmunAllResultServlet";
	
	} else if (p_gubun == "REGIST") {
      document.form2.action = "/servlet/controller.research.SulmunRegistResultServlet";   	
	} 
	
	document.form2.p_action.value = 'change';
	document.form2.p_gubun.value = p_gubun;
    document.form2.s_grcode.value = "";
	document.form2.p_process.value = 'SulmunResultPage';
	document.form2.submit();
}

function setCommon(p_subj) {
  var v_test; 
  v_test = getArrayTest(document.form2.all.p_subj.length);
  if (v_test == -1) {
    document.form2.p_subj.value = p_subj;
  } else if (v_test == 0) {
  } else {
    document.form2.p_subj.options[document.form2.p_subj.selectedIndex].value = p_subj;
  }
}
function getArrayTest(p_object) {
  var cnt;
  if (typeof(p_object) == 'undefined') {
    cnt = -1;
  } else {
    if (typeof(p_object) == 'number') {
        cnt = p_object;
    } else {
      cnt = -1;
    }
  }
  return cnt;
}

// ��������м����
function goDetailExcel(p_action) {
	if (document.form2.s_grcode.value == '----') {
			alert("�����׷��� �����ϼ���.");
			return ;
	}
     if (p_action=="go") {
        if (document.form2.s_gyear.value=="----"){
            alert("������ �����ϼ���");
            return;
        }
		if (document.form2.s_sulpapernum.value=='0'){
            alert("�������� �����ϼ���");
            return;
        }
    }

  window.self.name = "SulmunResultPage";     
  farwindow = open_window("openDetailExcel","","100","100","700","400","yes","yes","yes","yes","yes");  
  document.form2.p_action.value  = 'go';
  document.form2.target = "openDetailExcel";               
  document.form2.p_process.value = 'SulmunDetailResultExcelPage';   
  document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
  document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
  document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
  document.form2.submit(); 
  document.form2.target = window.self.name;
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title ���� ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/research/r_title05.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title �� ----------------->
      <br>
      <table cellspacing="0" cellpadding="0" class="table1">
        <tr> 
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color1%>_butt_left"></td>
                <td class="<%=v_tab_color1%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color1%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color1%>_butt_middle"><a href="javascript:changeTabpage('ALL')" class="c">��������</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color1%>_butt_bottom"></td>
              </tr>
            </table>
          </td>

          <td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_left"></td>
                <td class="<%=v_tab_color3%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_middle">�Ϲݼ���</td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color2%>_butt_left"></td>
                <td class="<%=v_tab_color2%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color2%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color2%>_butt_middle"><a href="javascript:changeTabpage('TARGET')" class="c">����ڼ���</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color2%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
		  <td width="85">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_left"></td>
                <td class="<%=v_tab_color4%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_middle"><a href="javascript:changeTabpage('REGIST')" class="c">���԰��</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
   
          <td>&nbsp;</td>
        </tr>
      </table>
      <table cellspacing="0" cellpadding="0" class="table_out">
        <tr>
          <td bgcolor="#636563">
            <table cellspacing="1" cellpadding="0" class="s_table">
              <tr>
                <td bgcolor="#FFFFFF" align="center" valign="top">
                  <br>
                  <!----------------- form ���� ----------------->
                  <table cellspacing="0" cellpadding="1" class="form_table_out">
                  <form name="form2" method="post" action="/servlet/controller.research.SulmunCommonResultServlet">
                    <input type="hidden" name="p_process"  value="">
                    <input type="hidden" name="p_action"   value="">
                	<input type="hidden" name="p_grcode"      value="<%=v_grcode%>">
                	<input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
	                <input type="hidden" name="p_subj"      value="<%=v_subj%>">
	                <input type="hidden" name="p_subjseq"      value="<%=v_subjseq%>">
	                <input type="hidden" name="p_sulpapernum"      value="<%=v_sulpapernum%>">
	                <input type="hidden" name="p_company"      value="<%=v_company%>">
	                <input type="hidden" name="p_jikwi"      value="<%=v_jikwi%>">
	                <input type="hidden" name="p_jikun"      value="<%=v_jikun%>">
	                <input type="hidden" name="p_workplc"      value="<%=v_workplc%>">
                	<input type="hidden" name="p_gubun"       value="">

					<tr>
                      <td bgcolor="#C6C6C6" align="center">
                        <table cellspacing="0" cellpadding="0" class="form_table_bg" >
                          <tr>
                            <td height="7"></td>
                          </tr>
                          <tr>
                            <td align="center">
                              <table cellspacing="0" cellpadding="0" width="99%" class="form_table">
                                <tr>
                                  <td>&nbsp; <font color="red">��</font> 
			                        &nbsp;<%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   �����׷�  -->
						            &nbsp;<font color="red">��</font> <%= SelectEduBean.getDamunGyear(box, true, false )%><!-- getGyear(RequestBox, isChange)   �ش翬��  -->
                                    &nbsp;<font color="red">��</font>  ������<%= SulmunCommonResultBean.getSulpaperSelect (v_grcode, v_gyear, v_subj, "s_sulpapernum", v_sulpapernum, "")%>
                                    &nbsp;<%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
 </form>
                  <!----------------- form �� ----------------->
                  <!----------------- ������� ��ư ���� ----------------->
                  <table cellpadding="0" cellspacing="0" class="table1">
                    <tr>
                      <td align="right"><a href="javascript:goExcel('go')" class="c"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a>
                                          <a href="javascript:goExcelDetail('go')" class="c"><img src="/images/admin/button/btn_excelprint2.gif"  border="0"></a>
                      </td>                      
                    </tr>
                    <tr>
                      <td height="3"></td>
                    </tr>
                  </table>
                  <!----------------- ������� ��ư �� ----------------->
                  <!----------------- �����м� ���� ----------------->
                  <table cellspacing="1" cellpadding="0" class="box_table_out">
                    <tr>
                     <td colspan="5" class="table_top_line"></td>
                    </tr>
                    <tr>
                      <td colspan="5" class="table_title_02">�������ڼ� : <%=v_replycount%>��</td>
                    </tr>
<%  ArrayList    list = (ArrayList)request.getAttribute("SulmunResultList");
    SulmunQuestionExampleData data    = null;
    SulmunExampleData         subdata = null;

	int k = 0;
	int l = 0;

    for (int i=0; i < list.size(); i++) {
        data = (SulmunQuestionExampleData)list.get(i);
        if (data.getSultype().equals(SulmunCommonBean.OBJECT_QUESTION) || data.getSultype().equals(SulmunCommonBean.MULTI_QUESTION)) { %>        
                    <tr>
                      <td class="table_title_01">����<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                    </tr>
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { %>
                    <tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left:6px" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplycnt()%>��</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                      <td width="39%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>%" class="graph">&nbsp;</td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
                    </tr>
<%              }
            }
       } else if (data.getSultype().equals(SulmunCommonBean.SUBJECT_QUESTION)) {
          
%>
                    <tr> 
                      <td class="table_title_01">����<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                    </tr>
<%        int m = 0;   
			for (int j=0; j < data.getSubjectAnswer().size(); j++) {
				if(((data.getSubjectAnswer().size() / v_replycount)*m + (k+1)) == (j+1)) {
					if(m < v_replycount) m++;
%>
					<tr>
                      <td width="7%" class="table_01"></td>
                      <td  colspan="4" style="padding-left:6px" class="table_02_2"><%=(String)data.getSubjectAnswer().get(j)%></td>
                    </tr>

<%           }   
		   }
           k++;
       } else if (data.getSultype().equals(SulmunCommonBean.COMPLEX_QUESTION)) {
          
%>
                    <tr>
                      <td class="table_title_01">����<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                    </tr>
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { %>
                    <tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left:6px" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>��</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                      <td width="39%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>%" class="graph">&nbsp;</td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
                    </tr>
<%           } 
           }	
%>
<%       int n = 0;     //out.println(data.getComplexAnswer().size());
			int etc = 0;
			for (int j=0; j < data.getComplexAnswer().size(); j++) {			
				if(((data.getComplexAnswer().size() / v_replycount)*n + (l+1)) == (j+1)) {   //out.println(((data.getComplexAnswer().size() / v_replycount)*n + (l+1)));
					if(n < v_replycount) n++;
						if( !((String)data.getComplexAnswer().get(j)).equals("")) {
							etc++;
%>
					<tr>
                      <td width="7%" class="table_01"><%if(etc==1){%>��Ÿ<%}%></td>
                      <td  colspan="4" style="padding-left:6px" class="table_02_2"><%=(String)data.getComplexAnswer().get(j)%></td>
                    </tr>
<%
						}
                }   
		   }
           l++;
       } else if (data.getSultype().equals(SulmunCommonBean.FSCALE_QUESTION)) {
          
	          double d = 0; 
			  int person = 0;
			  double v_point = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { 

					d +=  (subdata.getReplycnt()) * subdata.getSelpoint();
					person += subdata.getReplycnt();
				}
            }	
               
		v_point = d / person;	  
%>
                    <tr>
                      <td class="table_title_01">����<%=i+1%></td>
                      <td colspan="3" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
					  <td width="7%" class="table_01"><%if(v_point >=0){%><%=df.format(v_point)%>��<%}%></td>
                    </tr>
<%

		   for (int j=1; j <= data.size(); j++) {

                subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { 
%>

					<tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left:6px" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>��</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                      <td width="39%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>%" class="graph">&nbsp;</td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
                    </tr>
<%           }    
            }	
       } else if (data.getSultype().equals(SulmunCommonBean.SSCALE_QUESTION)) {
          
	          double d = 0; 
			  int person = 0;
			  double v_point = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { 

					d +=  (subdata.getReplycnt()) * subdata.getSelpoint();
					person += subdata.getReplycnt();
				}
            }	
               
		v_point = d / person;	  
%>
                    <tr>
                      <td class="table_title_01">����<%=i+1%></td>
                      <td colspan="3" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
					  <td width="7%" class="table_01"><%if(v_point >=0){%><%=df.format(v_point)%>��<%}%></td>
                    </tr>
<%

		   for (int j=1; j <= data.size(); j++) {

                subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { 
%>

					<tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left:6px" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>��</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                      <td width="39%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>%" class="graph">&nbsp;</td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
                    </tr>
<%           }    
            }	
	   } 
    } %>
                  </table>
				  <br>
                  <!----------------- �����м� �� ----------------->
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>


  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

</table>
</body>
</html>