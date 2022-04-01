<%
//**********************************************************
//  1. 제      목: 과정차수별공지사항
//  2. 프로그램명: za_SubjGongAll_L.jsp
//  3. 개      요: 과정차수별공지사항전체 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 23
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");

    String  v_grcode    = "";
    String  v_grcodenm  = "";
    String  v_gyear     = "";
    String  v_grseq     = "";
    String  v_course    = "";
    String  v_cyear     = "";
    String  v_courseseq = "";
    String  v_coursenm  = "";
    String  v_subj      = "";
    String  v_year      = "";
    String  v_subjseq   = "";
    String  v_subjseqgr = "";
    String  v_subjnm    = "";
    String  v_isonoff   = "";
    String  v_edustart  = "";
    String  v_eduend    = "";
    String  v_isgoyong  = "";


    String  v_subjtypenm  = "";
    String  v_isnewcourse = "";
    int     v_rowspan     =  0;
    int     i = 0;


    //DEFINED in relation to select START
    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getString("s_grseq");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수
    String  ss_isclosed     = box.getString("s_isclosed");        //진행여부

//    String  v_ongo      = "whenSelection('selectAll')";
    //DEFINED in relation to select END

    int    v_codecnt   = StringManager.toInt(box.getString("p_typescnt"));       // 공지구분갯수
    
    String  ss_action    = box.getString("s_action");
    
    ArrayList list1      = null;
    ArrayList list2      = null;
    //if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list1    = (ArrayList)request.getAttribute("selectList1");         // 공지구분리스트
        list2    = (ArrayList)request.getAttribute("selectList2");         // 과정차수별 리스트
    //}
    
    System.out.println("ss_action = " + ss_action);


    String   v_codenm       = "";  // 공지구분명
    String   v_typescnt_org = "";  
    String[] v_typescnt     = new String[v_codecnt];
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    function whenSelection(p_action) {
    
    

      document.form1.p_process.value = 'selectPre';

if (p_action=='go') {
      if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
        alert("교육그룹을 선택하세요.");
        return ;
      }
      top.ftop.setPam();
      document.form1.p_process.value = 'selectAll';
}
      //if (p_action=='go') {}

      document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
      document.form1.submit();
    }

    // 선택공지등록
    function insert() {
        if(document.form1.all['p_checks'] == '[object]') {
            var v_chkcnt = 0;
            
            if (document.form1.p_checks.checked) {
                v_chkcnt=1;
            }
            
            if (document.form1.p_checks.length > 0) {
              for (i=0; i<document.form1.p_checks.length; i++) {
                if (document.form1.p_checks[i].checked == true)
                    v_chkcnt+=1;
              }
            }  
              if (v_chkcnt==0) {
                alert('공지할 과정이 없습니다.');
                return;           
              }
            
        } else {
            alert('공지할 과정이 없습니다.');
            return;
        }
            
        //if (document.form1.p_process.value == '') {
        //  alert("공지를 등록할 과정을 검색해주세요");
        //  return;
        //}
        
        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "insertPageAll";
        document.form1.submit();
    }

    // 선택차수별 공지사항리스트
    function listSub(subj,year,subjseq,subjnm) {
        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "select";

        document.form1.p_subj.value    = subj;
        //document.form1.s_subjcourse.value    = subj;
        document.form1.p_year.value    = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_hsubjnm.value = subjnm;

        document.form1.submit();
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

function whenOrder(column) {
    if (document.form1.p_orderType.value == " asc") {
        document.form1.p_orderType.value = " desc";
    } else {
        document.form1.p_orderType.value = " asc";
    }

    document.form1.p_orderColumn.value = column;
    
    whenSelection("go");

}

-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
    <form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_subj"    value="">
    <input type="hidden" name="p_year"    value="">
    <input type="hidden" name="p_subjseq" value="">
    <input type="hidden" name="p_hsubjnm" value="">
    
    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->   
    
    <input type="hidden" name="p_orderColumn">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>
        <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">

            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">

                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td width="27%" colspan="2" align="left" valign="middle">
                        <!------------------- 조건검색 시작 ------------------------->
                         <table cellspacing="0" cellpadding="0" width="99%">
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0" width="99%">
                                        <tr>
                                            <td width="25%"><font color="red">★</font> 
                                            <!-- 교육그룹 시작 -->
                                                <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
                                            <!-- 교육그룹 종료 -->
                                            </td>
                                            <td width="75%">
                                            <!-- 교육년도, 분류 시작 -->
                                                <%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도  -->
                                                <%= SelectEduBean.getGrseq(box, true, true)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
                                                <!--input type="hidden" name="s_grseq"  value="0001"-->
                                                &nbsp;<%= SelectSubjBean.getUpperClass(box, true, true, true)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
                                                &nbsp;<%= SelectSubjBean.getMiddleClass(box, true, true, true)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
                                                &nbsp;<%= SelectSubjBean.getLowerClass(box, true, true, true)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
                                            <!-- 교육년도, 분류 끝 -->
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td height="3"></td>
                            </tr>
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0" width="99%">
                                        <tr>
                                            <td width="65%">&nbsp;&nbsp;&nbsp;
                                            <!-- 과정,차수,회사 시작 -->
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                <%= SelectSubjBean.getSubj(box, true, true)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                                                &nbsp;<%= SelectSubjBean.getSubjseq(box, false, true)%><!-- getSubjseq(RequestBox, isChange, isALL)    과정차수  -->
                                                <%//= SelectCompanyBean.getCompany(box, true, true)%><!-- getCompany(RequestBox, isChange, isALL)    회사  -->
                                            <!-- 과정,차수,회사 종료 -->
                                            </td>
                                            <td  width="10%" align="right"> 
                                                <%@ include file="/learn/admin/include/za_GoButton.jsp" %><!-- whenSelection('go') -->
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <!-------------------- 조건검색 끝 ---------------------------->
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="9"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!----------------- form 끝 ----------------->
          <br>

        <!----------------- 선택전체공지  버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr> 
            <td align="right" ><a href="javascript:insert()"><img src="/images/admin/button/btn_selectgongi.gif" border="0"></a></td>
          </tr>
          <tr> 
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 선택전체공지 버튼 끝 ----------------->

        <!----------------- 공지사항관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="13" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="5%" height="50" rowspan="2" class="table_title"><b>NO</b></td>
            <td width="26%" rowspan="2" class="table_title"><a href="javascript:whenOrder('subjnm')" class="e">과정</a></td>
            <td width="5%" rowspan="2" class="table_title"><a href="javascript:whenOrder('subjseqgr')" class="e">차수</a></td>
            <td width="6%" rowspan="2" class="table_title"><a href="javascript:whenOrder('isonoff')" class="e">구분</a></td>
            <td width="17%" rowspan="2" class="table_title"><a href="javascript:whenOrder('edustart')" class="e">교육기간</a></td>
            <td height="22" colspan="8" class="table_title"><b>공지구분</b></td>
            <td width="4%" rowspan="2" class="table_title"><%@ include file="/learn/admin/include/za_checkboxall.jsp" %></td>
          </tr>
          <tr>         
            <td width="4%" class="table_title">입과</td>
            <td width="4%" class="table_title">안내</td>
            <td width="5%" class="table_title">리포트</td>
            <td width="4%" class="table_title">수료</td>
            <td width="6%" class="table_title">마지막주</td>
            <td width="6%" class="table_title">과정종료</td>
            <td width="4%" class="table_title">기d타</td>
            <td width="4%" class="table_title">전체</td>              
            <%
      // 권한 리스트
//if (ss_action.equals("go")) {    //go button 선택시만 list 출력
       for(i = 0; i < list1.size(); i++) {

                CodeData data1  = (CodeData)list1.get(i);
                v_codenm    = data1.getCodenm();
%>
            <!--td class="table_title" height="25"><b><%//=v_codenm%></b></td-->
            <%
        }
%>
          </tr>
          <%
            for(i = 0; i < list2.size(); i++) {
                SubjGongData data2= (SubjGongData)list2.get(i);

                v_grcode    = data2.getGrcode();
                v_grcodenm  = data2.getGrcodenm();
                v_gyear     = data2.getGyear();
                v_grseq     = data2.getGrseq();
                v_course    = data2.getCourse();
                v_cyear     = data2.getCyear();
                v_courseseq = data2.getCourseseq();
                v_coursenm  = data2.getCoursenm();
                v_subj      = data2.getSubj();
                v_year      = data2.getYear();
                v_subjseq   = data2.getSubjseq();
                v_subjseqgr = data2.getSubjseqgr();
                v_subjnm    = data2.getSubjnm();
                v_isonoff   = data2.getIsonoff();
                v_edustart  = data2.getEdustart();
                v_eduend    = data2.getEduend();



                v_subjtypenm  = data2.getSubjtypenm();
                v_rowspan     = data2.getRowspan();
                v_isnewcourse = data2.getIsnewcourse();

%>
          <tr> 
            <td class="table_01"><%= i+1 %></td>
            <%  if(v_course.equals("000000")){   %>
            <!-- 과정인 경우 -->
            <td class="table_02_2"><a href="javascript:listSub('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%=v_subjnm%>')"  class="b"><%=v_subjnm%></a></td>
            <% }else if(v_isnewcourse.equals("Y")){ %>
            <!-- 코스이면서 이전 코스와 일치하지 않는경우 -->
            <!--td class="table_02_1" rowspan="<%//=v_rowspan%>"> 
              <font class="text_color03">[코스] <%//=v_coursenm%><%//=v_courseseq%></font> </td-->
            <% } %>
            <%  if(!v_course.equals("000000")){   %>
            <!-- 코스인 경우 -->
            <!--td class="table_02_1"><font class="text_color04"><%//=v_subjnm%></font></td-->
            <% } %>
            <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
            <td class="table_02_1"><%=v_subjtypenm%></td>
            <td class="table_02_1"><%= FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd") %> ~ <%= FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd") %></td>
            
            <%
           // 권한 리스트
           if(v_codecnt > 0) {
                v_typescnt_org  = data2.getTypescnt();

               StringTokenizer st = new StringTokenizer(v_typescnt_org,"/");
               int j = 0;

               // 과정차수별에 대한 각각의 구분별 공지 갯수 배열에 저장
               while (st.hasMoreElements()) {
                   v_typescnt[j] = StringManager.chkNull((String)st.nextToken());
                   j++;

               }

               // 위치 지정을 위한 구분값 (맨앞숫자 한자리) 제거
               for (j = 0;j<v_codecnt;j++){
                  v_typescnt[j] = StringManager.rightstring(v_typescnt[j], v_typescnt[j].length()-1);
               }

               // 해당 권한 체크박스에 세팅
               for (int k=0; k < v_codecnt ; k++) {
                   v_typescnt[k] = StringManager.chkNull(v_typescnt[k]);
                   if (v_typescnt[k].equals("")) v_typescnt[k] = "0";

%>
            <td class="table_02_1"><%=v_typescnt[k]%></td>
            <%
                }
            }
%>          <td class="table_02_1">
                <input name="p_checks" type="checkbox" value="<%=v_subj + "/" + v_gyear + "/" + v_subjseq%>"></td>
          </tr>
          <%
        }
//}//go

//if (ss_action.equals("go")) {    //go button 선택시만 list 출력
          if(v_process.equals("selectAll") && i == 0){ %>
          <tr> 
            <td class="table_02_1" colspan="<%= (v_codecnt + 9) %>">등록된 내용이 없습니다</td>
          </tr>
          <%  } 
//} //go%>
        </table>
        <!----------------- 공지사항관리 끝 ----------------->
        <br>
     </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
