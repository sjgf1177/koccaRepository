<%
//**********************************************************
//  1. 제      목: 교육그룹에 과정/코스 연결 화면
//  2. 프로그램명: za_EduGroupSubj.jsp
//  3. 개      요: 교육그룹에 과정/코스 연결 화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004. 11. 11
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    String v_grcode      = box.getString("s_grcode");
    String v_gyear       = box.getString("s_gyear");
    String v_grseq       = box.getString("s_grseq");
    String v_mastercd    = box.getString("p_mastercd");
    String v_masternm    = box.getString("p_masternm");
    String v_proposetype = box.getString("p_proposetype");
    String v_gubun       = box.getStringDefault("p_gubun","ALL");
    String v_upperclass  = box.getStringDefault("p_upperclass","ALL");
    String v_subjects    = box.getString("p_subjects");
    


//    EduGroupData da = (EduGroupData)request.getAttribute("EduGroupData");

    ArrayList list1 = (ArrayList)request.getAttribute("TargetCourseList");
    ArrayList list2 = (ArrayList)request.getAttribute("TargetSubjectList");
    ArrayList list3 = (ArrayList)request.getAttribute("SelectedList");
    //ArrayList list4 = (ArrayList)request.getAttribute("TargetSubjSeqList");
    //out.println(list1.size());
    //out.println(list2.size());
    //out.println(list3.size());
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    <link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">

<!--
function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}



function searchSubject() {
    document.form2.p_selectedcodes.value = '';
    //getSelectedCodes();
    //getSelectedTexts();
    document.form2.p_process.value = 'assignSubjCourse';
    document.form2.submit();
}

function ChooseSubject(form) {
    document.form2.p_selectedcodes.value = '';
    cleanup_option_list(form);
    var func_name="subjectseq_option_for_";
    func_name += form.p_subjects.options[form.p_subjects.selectedIndex].value; 
    func_name += "(form)"; 
    eval(func_name); 
}

function cleanup_option_list(form) { 
    form.p_subjectseq.options.length=0; 
} 

<%
  for (int i=0; i<list2.size(); i++) {
    DataBox dbox  = (DataBox)list2.get(i);
%>
function subjectseq_option_for_<%=dbox.getString("d_subj")%>(form) {
    <%=SelectSubjBean.getMasterSeq(box, dbox.getString("d_subj"), v_grcode, v_gyear, v_grseq, v_mastercd)%>
    if(form.p_subjectseq.options.length==0){
      form.p_subjectseq.options[0] = new Option("대상차수가 없습니다.",""); 
    }
} 
<%  
}
%>


function setSelectedSeqCodes(){
    var boxLength = document.form2.p_subjectseq.length;
    var strValues = "";
    var count = 0;
    for (i = 0; i<boxLength; i++) {
        if (document.form2.p_subjectseq.options[i].selected) {
            if(count==0){
                strValues =  document.form2.p_subjectseq.options[i].value;
            }
            else{
                strValues = strValues + ";" + document.form2.p_subjectseq.options[i].value
            }
            count++;
        }
    }
    document.form2.p_selectedcodes.value = strValues;
}


function SaveChooseSubject(){
    setSelectedSeqCodes();
    if (document.form2.p_subjects.value == "") {
        alert("선택된 과정이 없습니다.");
        return;
    }
    if (document.form2.p_selectedcodes.value == "") {
        alert("선택된 차수가 없습니다.");
        return;
    }
    //alert(document.form2.p_selectedcodes.value);
    document.form2.action="/servlet/controller.propose.MasterSubjServlet"
    document.form2.p_process.value = 'assignSubjCourseSave';
    document.form2.submit();  
}


function CancelChooseSubject(){
    if (chkSelected() <1) {
        alert("삭제 대상을 선택하세요.");
        return;
    }
    document.form2.action="/servlet/controller.propose.MasterSubjServlet"
    document.form2.p_process.value = 'cancelMasterSubj';
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

function mastersubjClose() {
    //window.opener.location.reload();
    self.close()  
}

function init(form){
  alert(form);
}
//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" >
<form name = "form2" method = "post" action="/servlet/controller.propose.MasterSubjServlet">
    <input type="hidden" name="p_process"        value="">
    <input type="hidden" name="s_grcode"         value="<%=v_grcode%>">
    <input type="hidden" name="p_mastercd"       value="<%=v_mastercd%>">
    <input type="hidden" name="p_masternm"       value="<%=v_masternm%>">
    <input type="hidden" name="s_gyear"         value="<%=v_gyear%>">
    <input type="hidden" name="s_grseq"         value="<%=v_grseq%>">
    <input type="hidden" name="p_proposetype"         value="<%=v_proposetype%>">
    <input type="hidden" name="p_selectedcodes"  value="">         <!--선택된과정(p_selsubjs) code Value값이 구분자 ';'구분되어 하나의 문자열로 저장됨-->
    <input type="hidden" name="p_selectedtexts"  value="">         <!--선택된과정(p_selsubjs) code Text값이 구분자 ';'구분되어 하나의 문자열로 저장됨-->
<table width="700" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/propose/tit_master_info.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>        <!----------------- title 끝 ----------------->
        <br>
        <!----------------- 교육그룹  과정지정 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="16%" height="25" class="table_title"><b>교육그룹</b></td>
            <td class="table_02_2" width="81%"> <b><%=GetCodenm.get_grcodenm(v_grcode)%></b></td>
          </tr>
          <tr> 
            <td width="16%" height="25" class="table_title"><b>마스터과정명</b></td>
            <td class="table_02_2" width="81%"><b><%=v_masternm%></b></td>
          </tr>
<!--
          <tr> 
            <td width="16%" height="25" class="table_title"><b>수강신청방식</b></td>
            <td class="table_02_2" width="81%"><b><%=CodeConfigBean.getCodeName("sugang_kind",v_proposetype,1)%></b></td>
          </tr>
-->
          <!--tr>
            <td height="25" class="table_title"><b>코스 선택</b></td>
            <td class="table_02_2"> 
              <select name="p_courses" size=8 style="width:250;" onchange="ChooseCourse();">
<%  //SubjectInfoData data  = null;
    for (int i=0; i<list1.size(); i++) {
        DataBox dbox  = (DataBox)list1.get(i);
        //data  = (SubjectInfoData)list1.get(i); %>
                            <option value="<%=dbox.getString("d_course")%>"><%=dbox.getString("d_coursenm")%>
<%  }  %>
                </select>
            </td>
          </tr-->
          <tr> 
            <td class="table_title" height="25" rowspan=2><b>과정 선택</b></td>
            <td class="table_02_2">
            교육구분
			<%=CodeConfigBean.getCodeGubunSelect ("0004", "", 1, "p_gubun", v_gubun, "onChange='searchSubject()'", 2)%>
<!------------- 과정분류 시작 ------------------------------------>
<%
    SelectSubjBean selectSubj = new SelectSubjBean();
%>
                과정분류
                <select name="p_upperclass" onChange = "javascript:searchSubject()">
                <%
                        ArrayList upperList = selectSubj.getUpperClass(box);
                        for(int k = 0; k < upperList.size(); k++) {
                            DataBox selectbox = (DataBox)upperList.get(k);  
                        %>             
                    <option value = "<%= selectbox.getString("d_upperclass")%>" <% if ((box.getString("p_upperclass").equals(selectbox.getString("d_upperclass")))) out.print("selected"); %>>
                        <%= selectbox.getString("d_classname")%></option>
                <%   }  %>
                </select>
<!------------- 과정분류 끝   ------------------------------------>

            </td>
          </tr>
          <tr>
            <td class="table_02_2"> 
              <table>
                <tr>
                  <td>
                    <select name="p_subjects" size=8 style="width:300;" onchange="ChooseSubject(this.form);">
                    <%  for (int i=0; i<list2.size(); i++) {
                            DataBox dbox  = (DataBox)list2.get(i);
                    %>
                        <!--option value="<%=dbox.getString("d_subj")%>" <%if(v_subjects.equals(dbox.getString("d_subj"))) {out.print("selected");}%>><%=dbox.getString("d_subjnm")%></option-->
                        <option value="<%=dbox.getString("d_subj")%>" ><%=dbox.getString("d_subjnm")%></option>
                    <%  }  %>
                    </select>
                  </td>
                  <td width="10">&nbsp;</td>
                  <td>
                    <select multiple name="p_subjectseq" size=8 style="width:150;">
                      <OPTION>과정을 선택하세요</OPTION>
                    </select>
                  </td>
                </tr>
                <tr><td height="5"></td></tr>
                <tr>
				  <td valign="bottom" colspan="3" align="right"><a href="javascript:SaveChooseSubject()"><img src="/images/admin/button/btn_save.gif" border="0"><a></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr> 
            <td height="25" class="table_title"><b>선택된 과정</b></td>
            <td class="table_02_2" align=center> 
              <table width="470" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td align="right" ><a href="javascript:CancelChooseSubject()"><img src="/images/admin/button/btn_del.gif" border="0"><a></td>
                </tr>
				<tr>
				<td height=6></td>
				</tr>
                <tr>
                  <td>
                    <table width="100%" cellpadding="5" cellspacing="1" class="table_out">
                      <tr> 
                        <td width="5%" height="25" class="table_title"><b>NO</b></td>
                        <td width="60%" class="table_title"><b>과정명</b></td>
                        <td width="20%" class="table_title"><b>과정차수</b></td>
                        <td width="28%" class="table_title"><b>선택</b></td>
                      </tr>
                      <%  
                          String v_subj = "";
                          String v_subjseq = "";
                          String v_subjseqgr = "";
                          String v_subjnm = "";
                          int i = 0;
                          for (i=0; i<list3.size(); i++) {
                              DataBox dbox  = (DataBox)list3.get(i);
                              v_subj    = dbox.getString("d_subj");
                              v_subjseq = dbox.getString("d_subjseq");
                              v_subjseqgr = dbox.getString("d_subjseqgr");
                              v_subjnm  = dbox.getString("d_subjnm");
                       %>
                      <tr> 
                        <td align="center" bgcolor="#EEEEEE" height="25"><%=i+1%></td>
                        <td class="table_02_2" align="center"><%=v_subjnm%></td>
                        <td class="table_02_1" align="center"><%=Integer.parseInt(v_subjseqgr)%>차</td>
                        <td class="table_02_1" align="center"><input type="checkbox" name="p_checks" value="<%=v_subj%>,<%=v_subjseq%>"></td>
                      </tr>
                      <%  }if(i==0){  %>
                      <tr>
                        <td class="table_02_1" colspan="6">등록된 내용이 없습니다.</td>
                      </tr>
                      <%}%>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <!----------------- 교육그룹  과정지정 끝 ----------------->

        <br>
        <!----------------- 저장, 취소 버튼 시작 ----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <!--td align="center"><a href="javascript:whenSubmit()"><img src="/images/admin/course/save1_butt.gif" width="37" height="18" border="0"></a></td-->
            <!--td align="center"><a href="javascript:whenSubmit()">다음단계로</a></td-->
            <td align="center"><a href="javascript:mastersubjClose()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->
        <br>
      </td>
  </tr>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>

</form>
</body>
</html>
