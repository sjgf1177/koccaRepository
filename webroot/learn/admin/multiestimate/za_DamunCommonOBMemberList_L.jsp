<%
/**
 * file name : za_DamunCommonOBMemberList_L.jsp
 * date      : 2003/09/03
 * programmer:
 * function  : 다면평가 관찰자 보기
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
   
	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

    String v_reloadlist = box.getString("p_reloadlist");    

    String  v_asgnnm    = box.getString("p_asgnnm");
    String  v_jikupnm    = box.getString("p_jikupnm");
    String  v_cono    = box.getString("p_cono");
    String  v_name    = box.getString("p_name");
    String  v_subjuserid    = box.getString("p_subjuserid");

    String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

    String  ss_grcode       = box.getString("s_grcode");
	String  ss_gyear       = box.getString("s_gyear");
	int  ss_damunpapernum       = box.getInt("s_damunpapernum");
    String  ss_subjseq    = box.getString("s_subjseq");
 
%>
<html>
<head>
<title>다면평가 대상자 화면</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function insertOB() {

  var chkselected = chkSelected();
  if (chkselected < 1) {
    alert('관찰자를 선택하세요');
    return;
  }

  document.form2.p_process.value = 'DamunOBMemberInsert';
  document.form2.p_reloadlist.value = 'true';
  document.form2.p_action.value  = 'insert';
  document.form2.submit();
}
function chkSelected() {
  var selectedcnt = 0;
  if(document.form2.all['p_checks'] == '[object]') {
    if (document.form2.p_checks.length > 0) {
      for (i=0; i<document.form2.p_checks.length; i++) {
        if (document.form2.p_checks[i].checked == true) {
	      selectedcnt++;
	    }  
      }
    } else {
      if (document.form2.p_checks.checked == true) {
	    selectedcnt++;
      }
    }
  }
  return selectedcnt; 
}
<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage();
<% } %>
function onload() {
  window.self.close();
}

function whenAllSelect() {
  if(document.form2.all['p_checks'] == '[object]') {
    if (document.form2.p_checks.length > 0) {
      for (i=0; i<document.form2.p_checks.length; i++) {
        document.form2.p_checks[i].checked = true;
      }
    } else {
      document.form2.p_checks.checked = true;
    }
  } 
}
function whenAllSelectCancel() {
  if(document.form2.all['p_checks'] == '[object]') {
    if (document.form2.p_checks.length > 0) {
      for (i=0; i<document.form2.p_checks.length; i++) {
        document.form2.p_checks[i].checked = false;
      }
    } else {
      document.form2.p_checks.checked = false;
    }
  } 
}

function chkeckall(){
    if(document.form2.p_chkeckall.checked){
      whenAllSelect();
    }
    else{
      whenAllSelectCancel();
    }
}
//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" <%=v_onload%>>
<table width="770" border="0" cellspacing="0" cellpadding="0" height="580">
  <tr>
    <td align="center" valign="top">

	    <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/multiestimate/m_title09.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	    <!----------------- title 끝 ----------------->
	    <br>
      <!----------------- 개인별 등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="open_table_out">
      <form name="form2" method="post" action="/servlet/controller.multiestimate.DamunCommonMemberServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="s_subjuserid"       value="<%=v_subjuserid%>">
        <input type="hidden" name="s_grcode"       value="<%=ss_grcode%>">
        <input type="hidden" name="s_subj"       value="<%=ss_subjcourse%>">
        <input type="hidden" name="s_subjcourse"       value="<%=ss_subjcourse%>">
        <input type="hidden" name="s_gyear"       value="<%=ss_gyear%>">
        <input type="hidden" name="s_subjseq"    value="<%=ss_subjseq%>">
        <input type="hidden" name="s_upperclass"       value="<%=ss_upperclass%>">
        <input type="hidden" name="s_damunpapernum"       value="<%=ss_damunpapernum%>">
        <input type="hidden" name="p_subjuserid"       value="<%=v_subjuserid%>">
		<input type="hidden" name="p_grcode"       value="<%=ss_grcode%>">
        <input type="hidden" name="p_subj"       value="<%=ss_subjcourse%>">
        <input type="hidden" name="p_gyear"       value="<%=ss_gyear%>">
        <input type="hidden" name="p_subjseq"    value="<%=ss_subjseq%>">
        <input type="hidden" name="p_upperclass"       value="<%=ss_upperclass%>">
        <input type="hidden" name="p_damunpapernum"       value="<%=ss_damunpapernum%>">
		<input type="hidden" name="p_asgnnm"     value="<%=v_asgnnm%>">
		<input type="hidden" name="p_jikupnm"     value="<%=v_jikupnm%>">
		<input type="hidden" name="p_cono"     value="<%=v_cono%>">
		<input type="hidden" name="p_name"     value="<%=v_name%>">
        <input type="hidden" name="p_reloadlist" value="">

      <!----------------- 개인별 등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="open_table_out">
        <tr> 
          <td colspan="7" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title">사번</td>
          <td class="table_title">성명</td>
          <td class="table_title">직위</td>
		  <td class="table_title">소속</td>
		  <td class="table_title">근무지</td>
		  <td class="table_title">이메일</td>
          <td class="table_title">전화번호</td>
        </tr>
<%  
	DataBox dbox0 = (DataBox)request.getAttribute("DamunPaperData");

           String v_ing = "";
		   int v_update = 0;
		   int v_gubun = 0;

		   String v_sulstart1 = "";
           String v_sulend1 = "";
           v_sulstart1 = FormatDate.getFormatDate(dbox0.getString("d_fdamunstart"),"yyyy-MM-dd");
	       v_sulend1 = FormatDate.getFormatDate(dbox0.getString("d_fdamunend"),"yyyy-MM-dd");

		   String v_sulstart2 = "";
           String v_sulend2 = "";
           v_sulstart2 = FormatDate.getFormatDate(dbox0.getString("d_sdamunstart"),"yyyy-MM-dd");
	       v_sulend2 = FormatDate.getFormatDate(dbox0.getString("d_sdamunend"),"yyyy-MM-dd");

		   if(dbox0.getInt("d_damunpapernum") > 0){
          
		   long v_fstart = Long.parseLong(dbox0.getString("d_fdamunstart"));
           long v_fend = Long.parseLong(dbox0.getString("d_fdamunend"));
              
            if (dbox0.getString("d_d_gubun").equals("1")){
                  v_gubun = 1; 

                 java.util.Date d_now = new java.util.Date();
                 String d_year = String.valueOf(d_now.getYear()+1900);
				 String d_month = String.valueOf(d_now.getMonth()+1);
				 String d_day = String.valueOf(d_now.getDate());

                 if(d_month.length() == 1){
				        d_month = "0" + d_month; 
				 }
				 if (d_day.length() == 1){
				        d_day = "0" + d_day; 				 
				 }
		         long v_now = Long.parseLong(d_year+d_month+d_day); 

		         if (v_fstart > v_now){
		            v_ing = "<font color='red'>다면평가 진행 전입니다.</font>"; 
					v_update = 1;
		         } else if (v_now > v_fend){
		            v_ing = "다면평가 진행이 완료되었습니다.";
					v_update = 3;
		         } else if (v_fstart <= v_now && v_now <= v_fend){
		            v_ing = "다면평가가 진행 중입니다"; 
					v_update = 2;
		         }
	         } else if (dbox0.getString("d_d_gubun").equals("2")){
			     v_gubun = 2;
			     long v_sstart = Long.parseLong(dbox0.getString("d_sdamunstart"));
                 long v_send = Long.parseLong(dbox0.getString("d_sdamunend"));		 

                 java.util.Date d_now = new java.util.Date();
                 String d_year = String.valueOf(d_now.getYear()+1900);
				 String d_month = String.valueOf(d_now.getMonth()+1);
				 String d_day = String.valueOf(d_now.getDate());

                 if(d_month.length() == 1){
				        d_month = "0" + d_month; 
				 }
				 if (d_day.length() == 1){
				        d_day = "0" + d_day; 				 
				 }
		         long v_now = Long.parseLong(d_year+d_month+d_day); 

		         if (v_fstart > v_now){
		            v_ing = "<font color='red'>다면평가 진행 전입니다.</font>"; 
					v_update = 1;
		         } else if (v_now > v_send){
		            v_ing = "사후 다면평가 진행이 완료되었습니다.";
					v_update = 5;
		         } else if (v_fstart <= v_now && v_now <= v_fend){
		            v_ing = "사전 다면평가가 진행 중입니다"; 
					v_update = 2;
		         } else if (v_fend <= v_now || v_now < v_sstart){
		            v_ing = "사후 다면평가 진행 전입니다"; 
					v_update = 3;
		         } else if (v_sstart <= v_now || v_now < v_send){
		            v_ing = "사후 다면평가가 진행 중입니다"; 
					v_update = 4;
		         }
			 }			 
		   }

    ArrayList blist = (ArrayList)request.getAttribute("DamunMemberList");
    
	if(blist.size()>0){
      ArrayList list1 = (ArrayList)blist.get(0);
        if(list1.size()>0){ 
			for (int i=0; i<list1.size(); i++) {
                DataBox dbox  = (DataBox)list1.get(i); %>
        <tr>
          <td class="table_01"><%=dbox.getString("d_userid")%></td>
          <td class="table_02_1"><%=dbox.getString("d_name")%></td>
          <td class="table_02_1"><%=dbox.getString("d_jikwinm")%></td>
		  <td class="table_02_1"><%=dbox.getString("d_asgnnm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_work_plcnm")%></td>
		  <td class="table_02_1"><%=dbox.getString("d_email")%></td>
          <td class="table_02_1"><%=dbox.getString("d_comptel")%></td>
        </tr>
<%     
			}
 	  }
	}
%>

      </table>
        <!----------------- 찾아보기 끝 ----------------->

        <br>
      <!----------------- 개인별 등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="open_table_out">
        <tr> 
          <td colspan="9" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="5%" class="table_title" rowspan="2">NO</td>
          <td class="table_title" rowspan="2"><input type="checkbox" name="p_chkeckall" onClick="javascript:chkeckall()"></td>
		  <td class="table_title" colspan="2">관계</td>
		  <td class="table_title" rowspan="2">사번</td>
          <td class="table_title" rowspan="2">성명</td>
          <td class="table_title" rowspan="2">직위</td>
		  <td class="table_title" rowspan="2">근무지</td>
		  <td class="table_title" rowspan="2">전화번호</td>
        </tr>
        <tr>
          <td class="table_title">분류</td>
          <td class="table_title">선택</td>
		</tr>
<%  
	if(blist.size()>0){

      int count = 0;
      int v_flag = 0;
	  Vector v_userids = new Vector();
	  Vector v_relations = new Vector();
      ArrayList list2 = (ArrayList)blist.get(1);  
	  if(list2.size()>0){
	     v_flag = 1;
		 for (int p =0; p<list2.size(); p++){
		    DataBox dbox  = (DataBox)list2.get(p);
			v_userids.add(dbox.getString("d_obuserid"));
			v_relations.add(dbox.getString("d_relation"));
		 }
	  }

      ArrayList list3 = (ArrayList)blist.get(2);
        if(list3.size()>0){ 
			for (int i=0; i<list3.size(); i++) {
				count++;
                DataBox dbox  = (DataBox)list3.get(i);

                String v_relation = dbox.getString("d_relation");
                String v_obuserid = dbox.getString("d_userid");
				String v_checked = "";
				if(v_flag > 0){
                   for (int y=0; y<v_userids.size(); y++){
				      if(v_obuserid.equals((String)v_userids.get(y))){
					     v_checked = " checked ";
				         v_relation = (String)v_relations.get(y);     
						 break;
					  }
				   }				   
				}
%>
        <tr>
		  <td width="5%" class="table_01"><%=count%></td>
          <td class="table_02_1"><!--      20050215  lee 수정 --->
            <input type="checkbox" name="p_checks" value="<%=dbox.getString("d_userid") + "|" + count%>" <%=v_checked%>><!--       --->
          </td>
          <td class="table_02_1">부서장</td>
          <td class="table_02_1">
		  <SELECT name="p_relation<%=count%>"> 
            <option value=1>상사</option> 
            <option value=2>동료</option> 
            <option value=3>부하</option> 
		  <script>document.form2.p_relation<%=count%>.value = <%=v_relation%>;</script>
          </SELECT> 
		  </td>
          <td class="table_02_1"><%=dbox.getString("d_userid")%></td>
          <td class="table_02_1"><%=dbox.getString("d_name")%></td>
          <td class="table_02_1"><%=dbox.getString("d_jikwinm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_work_plcnm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_comptel")%></td>
		  <input type="hidden" name="p_relation" value="<%=v_relation%>">
        </tr>
<%      }
	  }
%>
<%  
   for (int k=3; k<blist.size(); k++){
      ArrayList list4 = (ArrayList)blist.get(k);
        if(list4.size()>0){ 
			for (int i=0; i<list4.size(); i++) {
				count++;
                DataBox dbox  = (DataBox)list4.get(i); 

				String v_relationnm = "";
				if (dbox.getString("d_relation").equals("1")){
				    v_relationnm = "상사";
				} else if (dbox.getString("d_relation").equals("2")){
				    v_relationnm = "동료";
				} else if (dbox.getString("d_relation").equals("3")){
				    v_relationnm = "부하";
				}

                String v_relation = dbox.getString("d_relation");
                String v_obuserid = dbox.getString("d_userid");
				String v_checked = "";
				if(v_flag > 0){
                   for (int y=0; y<v_userids.size(); y++){
				      if(v_obuserid.equals((String)v_userids.get(y))){
					     v_checked = " checked ";
				         v_relation = (String)v_relations.get(y);     
						 break;
					  }
				   }				   
				}
%>
        <tr>
		  <td width="5%" class="table_01"><%=count%></td>
		  <td class="table_02_1"><!--      20050215  lee 수정 --->
            <input type="checkbox" name="p_checks" value="<%=dbox.getString("d_userid") + "|" + count%>" <%=v_checked%>><!--       --->
          </td>
          <td class="table_02_1"><%=v_relationnm%></td>
          <td class="table_02_1">
		  <SELECT name="p_relation<%=count%>"> 
            <option value=1>상사</option> 
            <option value=2>동료</option> 
            <option value=3>부하</option> 
		  <script>document.form2.p_relation<%=count%>.value = <%=v_relation%>;</script>
          </SELECT> 
		  </td>
          <td class="table_02_1"><%=dbox.getString("d_userid")%></td>
          <td class="table_02_1"><%=dbox.getString("d_name")%></td>
          <td class="table_02_1"><%=dbox.getString("d_jikwinm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_work_plcnm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_comptel")%></td>
		  <input type="hidden" name="p_relation" value="<%=v_relation%>">
        </tr>
<%      }
	  }
  }
}
%>
       </form>   
      </table>
	  <br>
      <!----------------- 개인별 등록 끝 ----------------->
      <!----------------- 등록, 닫기 버튼 시작 ----------------->
      <table cellspacing="0" cellpadding="0" class="table1">
<%
     if(v_update == 1 || v_update == 2){
%>
        <tr> 
		  <td align="center">&nbsp;</td>
          <td align="right"><a href="javascript:insertOB()"><img src="/images/admin/button/btn_apply.gif" border="0"></a>
		  <a href="javascript:self.close()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
        </tr>
<%
     }else{	
%>
        <tr> 
		  <td align="center">&nbsp;</td>
          <td align="right"><a href="javascript:self.close()"><img src="/images/admin/button/btn_checkok.gif" border="0"></a></td>
        </tr>
<%
    }	
%>
      </table>
      <!----------------- 등록, 닫기 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
<!----------------- form 끝 ----------------->
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
