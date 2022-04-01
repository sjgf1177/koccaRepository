<%
//**********************************************************
//  1. 제      목: 마스터과정관리
//  2. 프로그램명: za_MasterMain_L.jsp
//  3. 개      요: 마스터과정 조회화면
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
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    ArrayList list = null;

    String v_mastercd    = "";
    String v_masternm    = "";
    String v_proposetype = "";
    String v_isedutarget = "";
    String v_sugangnm     = "";
    String v_grcode      = "";
    String v_gyear       = "";
    int    v_relatedsubj = 0;
    
    String s_userid  = box.getSession("userid");
    String s_name    = box.getSession("name");
    String v_process = box.getString("p_process");            //교육차수
    
    /* 교육 및 과정 차수 정보 selected Params*/
    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_grseq     = box.getString("s_grseq");            //교육차수
    String  ss_action    = box.getString("p_action");            //교육차수
    
    
    if(ss_action.equals("go")){
      list = (ArrayList)request.getAttribute("MasterSubjList");
    }
    //out.println("list==>>"+list);
    //out.println("ss_action="+ss_action.equals("go"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
var isChecked = false;

function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}

//마스터과정추가
function insertMSTcode(){
    if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
          alert("교육그룹을 선택하세요.");
          return ;
    }
    if (document.form1.s_grseq.value == 'ALL' || document.form1.s_grseq.value == '----') {
        alert("교육차수를 선택하세요.");
        return;
    }

    document.form1.p_process.value="insertPage";
    document.form1.action = '/servlet/controller.propose.MasterSubjServlet';
    document.form1.submit();
}

//조회
function whenSelection(p_action) {
        document.form1.p_action.value = p_action;
        if (p_action == 'go') {
        //alert(document.form1.s_grcode.value);
            if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
                  alert("교육그룹을 선택하세요.");
                  return ;
            }
            if (document.form1.s_grseq.value == 'ALL' || document.form1.s_grseq.value == '----') {
                alert("교육차수를 선택하세요.");
                return;
            }
            top.ftop.setPam();
        }
        document.form1.p_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action = '/servlet/controller.propose.MasterSubjServlet';
        document.form1.p_process.value = 'listPage';
        document.form1.submit();
    }


//과정등록
function assignSubjCourse(mastercd, masternm, grcode, gyear, grseq, proposetype){

        window.self.name = "winSelectGrsubj";
        farwindow = window.open("", "openWinGrsubj", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 700, height = 640, top=0, left=0");
        document.form2.target = "openWinGrsubj";
        document.form2.action = "/servlet/controller.propose.MasterSubjServlet";
        document.form2.p_process.value="assignSubjCourse";
        document.form2.p_mastercd.value=mastercd;
        document.form2.p_masternm.value=masternm;
        document.form2.s_grcode.value=grcode;
        document.form2.s_gyear.value=gyear;
        document.form2.s_grseq.value=grseq;
        document.form2.p_proposetype.value=proposetype;
        document.form2.submit();

        farwindow.window.focus();
        document.form2.target = window.self.name;
        
        //alert(document.form2.target);
}

//삭제
function whenDelete(mastercd, grcode, gyear, grseq){
    if(!confirm("하위과정정보와 대상자까지 모두삭제 됩니다.\n삭제하시겠습니까?")){
       return;
    }
    document.form2.p_process.value="deletemaster";
    document.form2.action = "/servlet/controller.propose.MasterSubjServlet";
    document.form2.p_mastercd.value=mastercd;
    document.form2.s_grcode.value=grcode;
    document.form2.s_gyear.value=gyear;
    document.form2.s_grseq.value=grseq;
    document.form2.p_action.value = 'go';
    //alert(document.form2.s_gyear.value);
    document.form2.submit();
    //document.location=url;
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form2" method="post" action="/servlet/controller.propose.MasterSubjServlet">
    <input type=hidden name=p_process value="">
    <input type=hidden name=p_action value="<%=ss_action%>">
    <input type=hidden name=s_grcode  value="<%=ss_grcode%>">
    <input type=hidden name=s_gyear  value="<%=ss_gyear%>">
    <input type=hidden name=s_grseq  value="<%=ss_grseq%>">
    <input type=hidden name=p_mastercd  value="">
    <input type=hidden name=p_masternm  value="">
    <input type=hidden name=p_proposetype  value="<%=v_proposetype%>">
</form> 

<form name="form1" method="post">
<input type=hidden name=p_process value="">
<input type=hidden name=p_action value="<%=ss_action%>">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
       <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/propose/p_title12.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->


        <br>
       <!----------------- form 시작 ----------------->
        <table class="form_table_out" border="0" cellspacing="0" cellpadding="1">
          <tr>
            <td align="center">

              <table class="form_table_bg" cellspacing="0" cellpadding="0" >
                <tr><td height="7"></td></tr>
                <tr>
                  <td align="center">

                    <table class="form_table" cellspacing="0" cellpadding="0" width="99%" >
                      <tr>
                        <td><!-- 과정관련 select boxs -->
                            <font color="red">★</font> <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹(s_grcode)  -->
                            <font color="red">★</font> <%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도 (s_gyear)-->
                            <font color="red">★</font> <%=  SelectEduBean.getGrseq(box, true, false)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
                            &nbsp;&nbsp;&nbsp;<%@ include file="/learn/admin/include/za_GoButton.jsp" %><!-- javascript:whenSelection('go')-->
                            <!--a href="javascript:whenSelSubjInfo('<%%>','','','')"><img src="images/admin/propose/go1_butt.gif" border="0" align=absmiddle></a-->&nbsp;&nbsp;

                        </td>
                      </tr>
                    </table>

                  </td>
                </tr>
                <tr><td height="7"></td></tr>
              </table>

            </td>
          </tr>
        </table>
        <!----------------- form 끝 ----------------->
        <br>
        <br>




        <!----------------- 교육그룹추가, 모두선택, 메일전송 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr> 
            <td align="right" height="20"><a href="javascript:insertMSTcode()"><img src="/images/admin/button/btn_masterplus.gif" border="0"></a></td>
            <!--
            <td align="right" width="65"><a href="javascript:selectAll()"><img src="/images/admin/course/select1_butt.gif" width="58" height="18" border="0"></a></td>
            <td align="right" width="65"><a href="javascript:sendMail()"><img src="/images/admin/course/mail1_butt.gif" width="58" height="18" border="0"></a></td>
            -->
          </tr>
          <tr> 
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 교육그룹추가, 모두선택, 메일전송 버튼 끝 ----------------->






        <!----------------- 교육그룹 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="6">
          <tr> 
            <td colspan="6" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="5%" height="25" class="table_title"><b>NO</b></td>
            <td width="35%" class="table_title"><b>마스터과정명</b></td>
            <!--<td width="20%" class="table_title"><b>수강신청방식</b></td>-->
            <!--<td width="20%" class="table_title"><b>대상자지정여부</b></td>-->
            <td width="20%" class="table_title"><b>하위연관과정수</b></td>
            <td class="table_title"><b>기능</b></td>
            <!--<td class="table_title"><b>선택</b></td>-->
          </tr>
<%
    
    int v_total=0;
    int i = 0;
    if(list != null){
    v_total = list.size();
    //out.println("v_total="+v_total);
        for(i=0;i<v_total;i++){
            DataBox dbox  = (DataBox)list.get(i);
            v_mastercd    = dbox.getString("d_mastercd");
            v_masternm    = dbox.getString("d_masternm");
            v_proposetype = dbox.getString("d_proposetype");
            v_isedutarget = dbox.getString("d_isedutarget");
            v_sugangnm    = CodeConfigBean.getCodeName("sugang_kind",v_proposetype,1);
            v_grcode      = dbox.getString("d_grcode");
            v_gyear       = dbox.getString("d_gyear");
            v_relatedsubj = dbox.getInt("d_cnt");
        
%>
              <tr> 
                <td align="center" bgcolor="#EEEEEE" height="25"><%=i+1%></td>
                <td class="table_02_2" align="center"><%=v_masternm%></td>
                <!--<td class="table_02_1" align="center"><%=v_sugangnm%></td>-->
                <!--<td class="table_02_1" align="center"><%=v_isedutarget%></td>-->
                <td class="table_02_1" align="center"><%=v_relatedsubj%></td>
                <td class="table_02_1" align="center">
                  <table  border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center"><a href="javascript:assignSubjCourse('<%=v_mastercd%>','<%=v_masternm%>','<%=v_grcode%>','<%=v_gyear%>','<%=ss_grseq%>','<%=v_proposetype%>')"><img src="/images/admin/button/b_courseapply.gif" border="0"></a></td>
                      <td width=8></td>
                      <td align="center"><a href="javascript:whenDelete('<%=v_mastercd%>','<%=v_grcode%>','<%=v_gyear%>','<%=ss_grseq%>')"><img src="/images/admin/button/b_delete.gif" border="0"></a></td>
                    </tr>
                  </table>
                </td>
                <!--<td class="table_02_1" align="center">
                  <input type="checkbox" name="checkbox" value="checkbox" checked>
                </td>
                -->
              </tr>
<%      }if(i==0){  %>

              <tr><td class="table_02_1" colspan="11">등록된 내용이 없습니다.</td></tr>
<%
        }
    }
%>

        </table>
        <!----------------- 교육그룹끝 ----------------->
        <br>
      </td>
  </tr>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

</table>

</form>
</body>
</html>