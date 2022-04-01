<%
//**********************************************************
//  1. 제      목: 교육차수 리스트 조회화면
//  2. 프로그램명: za_Grseq_L.jsp
//  3. 개      요: 교육차수 리스트 조회화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 11
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_grseq     = box.getString("s_grseq");            //교육차수
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_subjcourse= box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");          //과정 차수
    //DEFINED in relation to select END

    String  v_grseq = box.getString("p_mgrseq");
    String  v_subj = box.getString("p_subj");
    String  v_subjnm = box.getString("p_subjnm");
    
    ArrayList  list = (ArrayList)request.getAttribute("GrseqDetailList");
    
    
    String  v_propstart = "";
    String  v_propend   = "";
    String  v_edustart  = "";
    String  v_eduend    = "";
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">

function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}

//교육차수 리스트 화면으로
function goSubjectListPage() {
  
  document.form1.p_process.value  = 'listPage';
  document.form1.p_action.value = "go";
  document.form1.submit();
}


//과정차수 추가(화면 디스플레이)
function whenAddSeq() {
    document.all.addseq.style.display='';
}

//과정차수 추가 (실제)
function whenSeqSave() {
    var deny_pattern = /[^(0-9)]/;

    if(deny_pattern.test(document.form1.p_seqcnt.value)) {
        alert("숫자만 입력할 수 있습니다.");
        document.form1.p_seqcnt.focus();
        return;
    }

    if (confirm(document.form1.p_seqcnt.value + "개의 과정차수를 추가하시겠습니까?")) {
        document.form1.p_process.value = "addSubjSeq";
        document.form1.submit();
    }
}

// 과정차수 추가 취소(화면 디스플레이)
function whenSeqCancel() {
    document.all.addseq.style.display='none';
}

//과정차수 삭제처리
function delSubj(subj,year,subjseq,stoldcnt,propcnt){
/*
    if (stoldcnt>0) {
        alert("이수처리취소 후 다시 시도하세요.");
        return;
    }
*/
    if (propcnt==0) {
        if(confirm("과정차수 정보를 삭제하시겠습니까?"))  {
            setHiddenVals("delSubj",'<%=ss_gyear%>','<%=v_grseq%>',subj,year,subjseq);
            document.form1.submit();            
        }
    } else {
        alert('신청 또는 학습자가 있어 삭제할 수 없습니다.');
        return;
    }
    

}


// 히든값 정의
function setHiddenVals(process, gyear,grseq,subjcourse,year,seq){
    document.form1.p_action.value = "go";
    document.form1.p_process.value= process;
    document.form1.p_mgyear.value=gyear;
    document.form1.p_mgrseq.value=grseq;
    document.form1.p_msubjcourse.value=subjcourse;
    document.form1.p_myear.value=year;
    document.form1.p_mseq.value=seq;
}

// 차수 상세정보 팝업
function whenSubjseq(grcode,gyear,grseq,subj,year,subjseq,isonoff){
    var url=document.form1.action+'?p_process=updateSubjseqPage&s_grcode='+grcode+'&s_gyear='+gyear+'&s_grseq='+grseq+'&p_subj='+subj+'&p_year='+year+'&p_subjseq='+subjseq+'&s_upperclass=<%=ss_upperclass%>&s_middleclass=<%=ss_middleclass%>&s_subjcourse=<%=ss_subjcourse%>&p_isonoff='+isonoff;
    window.open(url,'insert','resizable=yes,scrollbars=yes,status=yes,width=800,height=600');
}

//정렬 순서
function whenOrder(column) {
    if (document.form1.p_orderType.value == " asc") {
        document.form1.p_orderType.value = " desc";
    } else {
        document.form1.p_orderType.value = " asc";
    }

    document.form1.p_orderColumn.value = column;
    document.form1.submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/c_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>
        
        
        
        <!----------------- form 시작 ----------------->
        <table width=970 border="0" cellspacing="0" cellpadding="1">
          <tr> 
            <td bgcolor="#C6C6C6" align="center"> 
              <table  width=100% border="0" cellspacing="0" cellpadding="0" class="table_02_1">
                <tr> 
                  <td height="7"></td>
                </tr>
                <tr> 
                  <td align="center" valign="middle"> 


                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <form name="form1" method="post" action="/servlet/controller.course.GrseqServlet">
        <input type="hidden" name="p_process" value="listDetailPage">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"  value="<%=v_subj%>">
        <input type="hidden" name="p_subjnm"  value="<%=v_subjnm%>">
        
        <input type="hidden" name="s_grcode"        value="<%=ss_grcode%>">     <!--in relation to select-->
        <input type="hidden" name="s_gyear"         value="<%=ss_gyear%>">      <!--in relation to select-->
        <input type="hidden" name="s_grseq"         value="<%=ss_grseq%>">      <!--in relation to select-->
        <input type="hidden" name="s_upperclass"    value="<%=ss_upperclass%>"> <!--in relation to select-->
        <input type="hidden" name="s_middleclass"   value="<%=ss_middleclass%>"><!--in relation to select-->
        <input type="hidden" name="s_subjcourse"    value="<%=ss_subjcourse%>"> <!--in relation to select-->
        <input type="hidden" name="s_subjseq"       value="<%=ss_subjseq%>">    <!--in relation to select-->
    
        <input type="hidden" name="p_mgyear" value="">
        <input type="hidden" name="p_mgrseq" value="<%=v_grseq%>">
        <input type="hidden" name="p_msubjcourse" value="">
        <input type="hidden" name="p_myear" value="">
        <input type="hidden" name="p_mseq" value="">
        
        <input type="hidden" name="p_orderColumn">
        <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
        
                      <tr> 
                        <td align=left>
                          &nbsp;과정 : <%=v_subjnm%> &nbsp;&nbsp;<%=ss_gyear%>년도
            </td>
                        <td></td>
                      </tr>
                    
                    </table>

                  </td>
                </tr>
                <tr> 
                  <td height="7" ></td>
                </tr>
              </table>



            </td>
          </tr>
        </table>
        <!----------------- form 끝 ----------------->
        <br>
        <!----------------- 교육차수추가 버튼 시작 ----------------->
        <table width="970" border="0" cellpadding="0" cellspacing="0">
          <tr>
          <td width="612" height="20" align="right">&nbsp; </td>
            
          <td width="286" height="20" align="right" > 
          <table width="100%" id="addseq" style="display:none;">
          <tr>
                <td>* 추가할 차수 
                  <input type='text' name='p_seqcnt' class='input' maxlength='2' size='4' value='1'>개 </td>
          <td><a href='javascript:whenSeqSave();'><img src="/images/admin/button/btn_save.gif" align="absmiddle" border=0></a> </td> 
          <td><a href='javascript:whenSeqCancel();'><img src="/images/admin/button/btn_cancel.gif" align="absmiddle" border=0></a></td></tr>
          </table>
          </td>
              
          <td width="72" height="20" align="right"> <a href="javascript:whenAddSeq();"><img src="/images/admin/button/btn_chasuadd.gif" align="absmiddle" border="0"></a> 
          </td>
          </tr>
          <tr> 
            <td height="3"></td>
          </tr>
        </table>
        
      <!----------------- 교육차수추가 버튼 끝 ----------------->
      <!----------------- 교육차수 관리 시작 ----------------->
      <table width="970" class="table_out" cellspacing="1" cellpadding="5">
        <tr> 
          <td colspan="16" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title"><a href="javascript:whenOrder('subjseqgr')" class="e">차수</a></td>
          <td class="table_title"><a href="javascript:whenOrder('studentlimit')" class="e">정원</a></td>
          <td class="table_title"><a href="javascript:whenOrder('propstart')" class="e">신청 시작일</a></td>
          <td class="table_title"><a href="javascript:whenOrder('propend')" class="e">신청 종료일</a></td>
          <td class="table_title"><a href="javascript:whenOrder('edustart')" class="e">교육시작일</a></td>
          <td class="table_title"><a href="javascript:whenOrder('eduend')" class="e">교육종료일</a></td>
          <td class="table_title"><b>상태</b></td>
          <td class="table_title"><a href="javascript:whenOrder('proposecnt')" class="e">신청</a></td>
          <td class="table_title"><a href="javascript:whenOrder('proposecnt')" class="e">취소</a></td>
          <td class="table_title"><a href="javascript:whenOrder('studentcnt')" class="e">승인</a></td>
          <td class="table_title"><a href="javascript:whenOrder('stoldcnt')" class="e">수료<br></a></td>
          <td class="table_title"><b>수정</b></td>
          <td class="table_title"><b>삭제</b></td>
        </tr>
        <%   
        for (int i=0; i<list.size(); i++) {
            DataBox dbox = (DataBox)list.get(i); 
            
            v_propstart = StringManager.chkNull(dbox.getString("d_propstart"));
            v_propend   = StringManager.chkNull(dbox.getString("d_propend"));
            v_edustart  = StringManager.chkNull(dbox.getString("d_edustart"));
            v_eduend    = StringManager.chkNull(dbox.getString("d_eduend"));
            
%>
        <tr height=25> 
          <td class="table_02_1" align="center"> <%=StringManager.cutZero(dbox.getString("d_subjseqgr"))%></td>
          <td class="table_02_1" align="center"><%=dbox.getInt("d_studentlimit")%></td>
          <td class="table_02_1" align="center"><%= FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd") %></td>
          <td class="table_02_1" align="center"><%= FormatDate.getFormatDate(v_propend,"yyyy/MM/dd") %></td>
          <td class="table_02_1" align="center"><%= FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd") %></td>
          <td class="table_02_1" align="center"><%= FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd") %></td>
          <td class="table_02_1" align="center">
          <%int v_today = Integer.parseInt(FormatDate.getDate("yyyyMMddHH"));
          
            if(!v_edustart.equals("")&&v_edustart.length() == 10){
                if (v_today >= Integer.parseInt(v_edustart) && v_today <= Integer.parseInt(v_eduend)) {
                    //교육시작일과 교육종료일 사이이면 - 교육중
                    out.print("교육중");
                } else if (v_today < Integer.parseInt(v_edustart)) {
                    //교육 시작일 이전이면 - 교육전
                    out.print("교육전");
                } else if (v_today > Integer.parseInt(v_eduend)) {
                    //교육종료일 이후면 - 완료
                    out.print("완료");
                }
            }

          %>
          </td>
          <td class="table_02_1" align="center"><%=dbox.getInt("d_proposecnt")%></td>
          <td class="table_02_1" align="center"><%=dbox.getInt("d_cancelcnt")%></td>
          <td class="table_02_1" align="center"><%=dbox.getInt("d_studentcnt")%></td>
          <td class="table_02_1" align="center"><%=dbox.getInt("d_stoldcnt")%></td>

          <td class="table_03_1" align="center"><a href="javascript:whenSubjseq('<%=ss_grcode%>','<%=ss_gyear%>','<%=v_grseq%>','<%=v_subj%>','<%=ss_gyear%>','<%=dbox.getString("d_subjseq")%>','<%=dbox.getString("isonoff")%>')"><img src="/images/admin/button/b_modify.gif" border=0></a></td>
          <td class="table_03_1" align="center"><a href="javascript:delSubj('<%=v_subj%>','<%=dbox.getString("d_year")%>','<%=dbox.getString("d_subjseq")%>','<%=dbox.getInt("d_stoldcnt")%>',<%=dbox.getInt("d_proposecnt")%>)"> 
            <img src="/images/admin/button/b_delete.gif" border="0"></a></td>
        </tr>
        <%  } %>
      </table>
      <!----------------- 교육차수 관리 끝 ----------------->
      <br>
      <!----------------- 저장, 삭제, 리스트 버튼 시작 ----------------->
      <table  border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="center">&nbsp;</td>
          <td align="center"><a href="javascript:goSubjectListPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 저장, 삭제, 리스트 버튼 끝 ----------------->
      </td>
  </tr>
    <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     
</table>
  </form>    
</body>
</html>
