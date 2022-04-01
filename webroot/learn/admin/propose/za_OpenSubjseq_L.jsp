<%
/**
 * file name : za_OpenSubjseq_L.jsp
 * date      : 2004.03.18
 * programmer: 
 * function  : 과정차수에 지정한 교육주관정보 선택
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.propose.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
RequestBox box = null;
box = (RequestBox)request.getAttribute("requestbox");
if (box == null) {
    box = RequestManager.getBox(request);
}

ArrayList list      = null;

String v_userid     = "";
String v_resno      = "";
String v_name       = "";
String v_sex        = "";
String v_email      = "";
String v_cono       = "";
String v_comp       = "";
String v_ismailing  = "";
String v_jikun      = "";
String v_jikunnm    = "";
String v_jikup      = "";
String v_jikupnm    = "";
String v_jikwi      = "";
String v_jikwinm    = "";
String v_compnm     = "";
String v_companynm  = "";
String v_gpmnm      = "";
String v_resno1     = "";
String v_resno2     = "";
String v_isTarget   = "";


int     i           =  0;

String  ss_company    = box.getString("s_company");      //검색분류별 검색내용
String  ss_grcomp     = box.getString("s_grcomp");
String  ss_jikun      = box.getString("s_jikun");      //검색분류별 검색내용
String  ss_jikwi      = box.getString("s_jikwi");      //검색분류별 검색내용
String  ss_userid     = box.getString("p_userid");     //사번
String  ss_name       = box.getString("p_name");       //이름
String  ss_compnm     = box.getString("p_compnm");     //부서명
String  ss_action     = box.getString("p_action");
String  ss_fix_gubun  = box.getStringDefault("s_fix_gubun","direct");

String  ss_grcode     = box.getString("s_grcode");
String  ss_grseq    = box.getString("s_grseq");
//String  ss_grseq      = "0001";
String  ss_gyear      = box.getString("s_gyear");
String  ss_subjcourse = box.getString("s_subjcourse");
String  ss_mastercd   = box.getString("s_mastercd");
String  ss_masternm   = box.getString("s_masternm");
String  ss_subjseq    = box.getString("s_subjseq");
String  ss_subjnm     = box.getString("s_subjnm");
String  ss_grcodenm   = box.getString("s_grcodenm");
String  ss_gyearnm    = box.getString("s_gyearnm");

if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("selectList");
}

int db_count = 0;
ProposeWizardBean probean = new ProposeWizardBean();
//ProposeWizardBean probean = null;
db_count = probean.edutargetCount(box);

%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
</head>
<script language="javascript">
<!--

    function whenSelected(){
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.propose.ProposeWizardServlet';
        document.form1.p_process.value = 'UpFile';
        document.form1.submit();
    }
    
    
    function whenSelection(p_action) {
            document.form1.p_action.value = p_action;
            ff = document.form1;
            if (p_action == 'go'& ff.p_userid.value == '' && ff.p_name.value == '' && ff.s_grcomp.value == 'ALL' && ff.s_jikwi.value == 'ALL') {
              alert("검색조건을 한가지이상 입력해 주세요.");
              return;
            }

			document.form1.s_company.value = ff.s_grcomp.value;
            document.form1.p_action.value = p_action;
            document.form1.target = "_self";
            document.form1.action='/servlet/controller.propose.ProposeWizardServlet';
            document.form1.p_process.value = 'memSearch';
            document.form1.submit();
    }
    
    function whenSave(p_action) {
        //alert(document.form1.p_checks.value);
        //return;
        if (chkSelected() <1) {
            alert("대상자를 선택하세요.");
            return;
        }
        
        //if(p_action=='go'){
        //    if(!chkParams()){ return; }
        //    document.form1.p_grcode.focus();
        //}
        document.form1.p_action.value = p_action;
        document.form1.target = "_parent";
        document.form1.action='/servlet/controller.propose.ProposeWizardServlet';
        document.form1.p_process.value = 'insert';
        document.form1.submit();
    }
    
    //function chkParams(){
    //    ff = document.form1;
    //    if(ff.p_userid.value == '' && ff.p_name.value == '' && ff.p_compnm.value == ''  && ff.s_jikwi.value == 'ALL'){
    //        alert('검색조건을 한가지이상 입력해 주세요.');
    //        return false;
    //    }
    //    return true;
    //}
    
    function chkSelected() {
          var selectedcnt = 0;
          if(document.form1.all['p_checks'] == '[object]') {
            if (document.form1.p_checks.length > 0) {
              for (i=0; i<document.form1.p_checks.length; i++) {
                if (document.form1.p_checks[i].checked == true) {
                  selectedcnt++;
                }
              }
            } else {
              if (document.form1.p_checks.checked == true) {
                selectedcnt++;
              }
            }
          }
          return selectedcnt;
        }
        
    function whenAllSelect() {
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            document.form1.p_checks[i].checked = true;
          }
        } else {
          document.form1.p_checks.checked = true;
        }
      }
    }

    function whenAllSelectCancel() {
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            document.form1.p_checks[i].checked = false;
          }
        } else {
          document.form1.p_checks.checked = false;
        }
      }
    }
-->
</script>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<center>

<form name="form1" method="post" action="/servlet/controller.propose.ProposeWizardServlet">
<input type="hidden" name="p_process"    value="">
<input type="hidden" name="s_grcode"     value="<%=ss_grcode%>">
<input type="hidden" name="s_grseq"     value="<%=ss_grseq%>">
<input type="hidden" name="s_gyear"       value="<%=ss_gyear%>">      
<input type="hidden" name="s_subjcourse" value="<%=ss_subjcourse%>">
<input type="hidden" name="s_mastercd" value="<%=ss_mastercd%>">
<input type="hidden" name="s_masternm" value="<%=ss_masternm%>">
<input type="hidden" name="s_subjseq"     value="<%=ss_subjseq%>">
<input type="hidden" name="s_subjnm"    value="<%=ss_subjnm%>">
<input type="hidden" name="s_grcodenm"    value="<%=ss_grcodenm%>">
<input type="hidden" name="s_gyearnm"    value="<%=ss_gyearnm%>">
<input type="hidden" name="p_action"     value="<%=ss_action%>">
<!--input type="hidden" name="s_total"     value=""-->
<input type="hidden" name="s_company"     value="">

<table width="97%" border="0" cellspacing="0" cellpadding="0" height="550">
  <tr>
    <td align="center" valign="top">
      <!----------------- 교육차수 검색 title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/propose/p_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  <!-------------------타이틀 끝-------------------------->
      <!----------------- 교육대상자 선정 방식 ----------------->
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <select name="s_fix_gubun" class="input" align="left" onChange="whenSelected()">
              <option value='direct' <%if(ss_fix_gubun.equals("direct")){out.print("selected");}%>>직접입력</option>
              <option value='upfile' <%if(ss_fix_gubun.equals("upfile")){out.print("selected");}%>>파일업로드</option>
            </select>
          </td>
        </tr>
		<tr><td height=6></td></tr>
      </table>
      <!----------------- 검색조건 입력박스 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg">
              <tr>
                <td height="7" width="99%">
                </td>
              </tr>
              <tr>
                <td align="center" width="99%" valign="middle">
				  <table width="99%" border="0" cellspacing="0" cellpadding="0" class="form_table">
                    <tr>
                      <td>
                        성명&nbsp;:&nbsp;<input type="text" name="p_name"   value="<%=ss_name%>" size="10">
                      </td>
                      <td>
                        ID&nbsp;:&nbsp;<input type="text" name="p_userid"   value="<%=ss_userid%>" size="10" >
                      </td>
                      <td></td>
                      <td></td>
                    </tr>
                    <tr><td height="7"></td></tr>
                    <tr>
                      <td>
					    <%=SelectCompanyBean.getGrcomp(box, false, true)%><!-- getGyear(RequestBox, isChange)   교육그룹별회사 -->
                        <!--<%= SelectCompanyBean.getCompany(box, true, true)%> --> <!-- getCompany(RequestBox, isChange, isALL)    회사  -->
                      </td>
                      <td>
                        <%= SelectCompanyBean.getJikwi(box, false, true)%>  <!-- getCompany(RequestBox, isChange, isALL)    직위  -->
                      </td>
                      <td>
                        &nbsp;&nbsp;<%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                      </td>
                    </tr>
                    <tr><td height="7"></td></tr>
                    <tr>
                      <td>
                      </td>
                      <td>
                        <!--<%= SelectCompanyBean.getDept(box, true, true)%> --> <!-- getCompany(RequestBox, isChange, isALL)    회사  -->
                      </td>
                      <td>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="7" width="99%"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
      <!-----------------  검색조건 입력박스 끝 ----------------->
      <!--------------------  마스터과정정보 ------------------------>
      <table cellspacing="0" cellpadding="1" class="form_table_out">
      <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center" valign="middle">
                
                  <table width="97%" border="0" cellpadding="0" cellspacing="0" class="form_table">
                  <tr>
                    <td>
                       <b>교육그룹</b> : <%=ss_grcodenm%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>연도</b> : <%=ss_gyearnm%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                       <b>마스터과정명</b> : <%=ss_masternm%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>선정교육대상인원</b> : <%=db_count%>
                    </td>
                  </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="7"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
      <!--------------------  마스터과정정보끝 ------------------------>
      
      <table width="97%" border="0" cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td height="20">
            <table width="30%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><input type="text" name="p_test01" maxlength="0" size="1"  style="font-size:0; text-decoration:none; border-style:none;" readonly></td>
              </tr>
            </table>
          </td>
          <td align="right" width="64"><a href="javascript:whenAllSelect()"><img src="/images/admin/button/select1_butt.gif"  border="0"></a></td>
		  <td width=8></td>
          <td align="right" width="87"><a href="javascript:whenAllSelectCancel()"><img src="/images/admin/button/select_cancel1_butt.gif" border="0"></a></td>
		  <td width=8></td>
          <td align="right" width="43"><a href="javascript:whenSave('<%=ss_action%>')"><img src="/images/admin/button/btn_save.gif" border="0" align=absmiddle></a></td>
        </tr>
        <tr>
          <td height="3"></td>
		  <td height="3"></td>
		  <td height="3"></td>
		  <td height="3"></td>
		  <td height="3"></td>
        </tr>
      </table>
      <table width="90%" border="0" cellspacing="0" cellpadding="0">
        <tr><td></td></tr>
      </table>
      <!----------------- 테이블 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> <td colspan="11" class="table_top_line"></td></tr>
        <tr>
          <td class="table_title" width="6%"><b>No</b></td>
          <td class="table_title" width="25%"><b>회사</b></td>
		  <td class="table_title" width="10%"><b>ID</b></td>
          <td class="table_title" width="18%"><b>성명</b></td>
          <td class="table_title" width="25%"><b>부서</b></td>
          <!--td class="table_title" width="20%"><b>주민번호</b></td-->
          <td class="table_title" width="10%"><b>직위</b></td>
          <td class="table_title" width="6%"><b>선택</b></td>
        </tr>
        
        <%
        int v_total =0;
    if(list != null) {
    //out.println("list="+list);
        v_total = list.size();
        
        for(i = 0; i < v_total; i++) {
            DataBox dbox = (DataBox)list.get(i);
            v_userid     = dbox.getString("d_userid"); 
            v_resno      = dbox.getString("d_resno");
            v_name       = dbox.getString("d_name");
            v_sex        = dbox.getString("d_sex");
            v_email      = dbox.getString("d_email");
            v_cono       = dbox.getString("d_cono");
            v_comp       = dbox.getString("d_comp");
            v_ismailing  = dbox.getString("d_ismailing");
            v_jikun      = dbox.getString("d_jikun");
            v_jikunnm    = dbox.getString("d_jikunnm");
            v_jikup      = dbox.getString("d_jikup");
            v_jikupnm    = dbox.getString("d_jikupnm");
            v_jikwi      = dbox.getString("d_jikwi");
            v_jikwinm    = dbox.getString("d_jikwinm");
            v_compnm     = dbox.getString("d_compnm");
            v_companynm  = dbox.getString("d_companynm");
            v_gpmnm      = dbox.getString("d_gpmnm");
            v_isTarget   = dbox.getString("d_istarget");
            if(v_resno.length() ==13){
                v_resno1    = StringManager.substring(v_resno, 0,6);
                v_resno2    = StringManager.substring(v_resno, 6);
            }
        %>
        <tr>
            <td class="table_01"><%=i+1%></td>
            <td class="table_02_1"><%=v_companynm%></td>
            <td class="table_02_1"><%=v_userid%></td>
            <td class="table_02_1"><%=v_name%></td>
            <td class="table_02_1"><%=v_compnm%></td>
            <!--td class="table_02_1">
            <%
                if(v_sex.equals("0")){  out.println("남");
                }else{  out.println("여");}
            %>    <%=v_sex%>      
            </td-->
            <!--td class="table_02_1"><%=v_resno1%>-<%//=v_resno2%>*******</td-->
            <td class="table_02_1"><%=v_jikwinm%></td>
            <td class="table_02_1">
            <%if(!v_isTarget.equals("Y")){%>
              <input type="checkbox" name="p_checks" value="<%=v_userid%>">
            <%}else{out.println("선정");}%>
            </td>
          </tr>
          <%
        }
    }else { 
%>
         <tr><td align="center" bgcolor="#F7F7F7" height="50" colspan="10">등록된 내용이 없습니다</td></tr>

<%
    }
%>
      </table>
      <!----------------- 테이블 끝 ----------------->
<br><br>
      <!----------------- 닫기 버튼 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td align="center" height="20"><a href="javascript:window.close();"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 닫기 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %> 

</center>
</body>
</html>
