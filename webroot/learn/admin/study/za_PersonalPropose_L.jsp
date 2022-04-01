<%
//**********************************************************
//  1. 제      목: PERSONAL SELECT LIST
//  2. 프로그램명: za_PersonalPropose_L.jsp
//  3. 개      요: 개인 신청과정 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 31
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%
    //DEFINED class&variable START
    String v_grcode     = "";
    String v_grcodenm   = "";
    String v_grseqnm   = "";
    String v_gyear      = "";
    String v_grseq      = "";
    String v_subj       = "";
    String v_subjnm     = "";
    String v_isnewcourse= "";
    String v_course     = "";
    String v_cyear      = "";
    String v_courseseq  = "";
    String v_coursenm   = "";
    String v_subjseq    = "";
    String v_subjseqgr  = "";
    String v_isonoff    = "";
    String v_edustart   = "";
    String v_eduend     = "";
    String v_appdate    = "";
    String v_chkfirst   = "";
    String v_chkfinal   = "";
    String v_onoff_value      = "";
    String v_chkfirst_value   = "";
    String v_chkfinal_value   = "";
    String v_upperclassname        = "";
    String v_isproposeapproval = "";
    String v_proapp_value = "";
    int     v_rowspan    = 0;
    int     i            = 0;
    ArrayList list2      = null;
    //DEFINED class&variable END
    list2 = (ArrayList)request.getAttribute("PersonalProposeList");
%>

 <br>
                        <table class="box_table_out_1" cellspacing="1" cellpadding="5" width="940">
                          <tr>
                            <td colspan="13" class="table_top_line"></td>
                          </tr>
                          <tr>
                            <td class="table_title tdBorder" width="10%"><b>교육그룹</b></td>
                            <td class="table_title tdBorder" width="5%" ><b>년도</b></td>
                            <td class="table_title tdBorder" width="5%" ><b>교육차수</b></td>
                            <td class="table_title tdBorder" width="30%"><font class="text_color04">과정</font></td>
                            <td class="table_title tdBorder" width="5%" ><b>과정차수</b></td>
                            <td class="table_title tdBorder" width="10%"><b>분류</b></td>
                            <td class="table_title tdBorder" width="15%"><b>교육기간</b></td>
                            <td class="table_title tdBorder" width="10%"><b>신청일</b></td>
                            <td class="table_title tdBorder" width="10%"><b>최종승인여부</b></td>
                          </tr>
                      </table>
                      <div style='width:960px;height:230px;overflow:auto;visibility:visible;border:1'>
                      <table class="box_table_out_1" cellspacing="1" cellpadding="5" width="940" >
            <%
                for(i = 0; i < list2.size(); i++) {
                    DataBox dbox    = (DataBox)list2.get(i);

                    v_grcode            = dbox.getString("d_grcode");
                    v_grcodenm          = dbox.getString("d_grcodenm");
                    v_grseqnm           = dbox.getString("d_grseqnm");
                    v_gyear             = dbox.getString("d_gyear");
                    v_grseq             = dbox.getString("d_grseq");
                    v_course            = dbox.getString("d_course");
                    v_cyear             = dbox.getString("d_cyear");
                    v_courseseq         = dbox.getString("d_courseseq");
                    v_coursenm          = dbox.getString("d_coursenm");
                    v_subj              = dbox.getString("d_subj");
                    v_subjnm            = dbox.getString("d_subjnm");
                    v_subjseq           = dbox.getString("d_subjseq");
                    v_subjseqgr         = dbox.getString("d_subjseqgr");
                    v_isonoff           = dbox.getString("d_isonoff");
                    v_edustart          = dbox.getString("d_edustart");
                    v_eduend            = dbox.getString("d_eduend");
                    v_appdate           = dbox.getString("d_appdate");
                    v_chkfirst          = dbox.getString("d_chkfirst");
                    v_chkfinal          = dbox.getString("d_chkfinal");
                    v_upperclassname         = dbox.getString("d_upperclassname");
                    //v_rowspan    = dbox.getString("d_rowspan");

                    v_edustart   = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                    v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");
                    v_appdate    = FormatDate.getFormatDate(v_appdate,"yyyy/MM/dd");



                    //if(v_isonoff.equals("ON"))      {  v_onoff_value  = "사이버과정"; }
                    //else                            {  v_onoff_value  = "집합과정";   }
                    //else if(v_isonoff.equals("OFF")){  v_onoff_value  = "집합과정";   }

                    if(v_chkfirst.equals("Y"))      {  v_chkfirst_value  = "승인";   }
                    else if(v_chkfirst.equals("N"))      {  v_chkfirst_value  = "반려";   }
                    else                            {  v_chkfirst_value  = "미처리"; }
                    
                    if(v_chkfinal.equals("Y"))      {  v_chkfinal_value  = "승인";   }
                    else if(v_chkfinal.equals("N"))      {  v_chkfinal_value  = "반려";   }
                    else                            {  v_chkfinal_value  = "미처리"; }


               %>
                          <tr>
                            <td class="table_01 tdBorder"   width="10%"><%=v_grcodenm%></td>
                            <td class="table_02_1 tdBorder" width="5%" ><%=v_gyear%></td>
                            <td class="table_02_1 tdBorder" width="5%"><%=v_grseq%></td>
                            <td class="table_02_2 tdBorder" width="30%"><a href="javascript:insertCounsel2('<%=v_subj%>', '<%=v_gyear%>', '<%=v_subjseq%>')"><%=v_subjnm%></a></td>
                            <td class="table_02_1 tdBorder" width="5%" ><%=StringManager.cutZero(v_subjseqgr)%>차</td>
                            <td class="table_02_1 tdBorder" width="10%"><%=v_upperclassname%></td>
                            <td class="table_02_1 tdBorder" width="15%"><%=v_edustart%>~<%=v_eduend%></td>
                            <td class="table_02_1 tdBorder" width="10%"><%=v_appdate%></td>
                            <td class="table_02_1 tdBorder" width="10%"><%=v_chkfinal_value%></td>
                          </tr>
               <%
              }
          if(i == 0){ %>
              <tr>
                <td class="table_02_1 tdBorder" colspan="11">등록된 내용이 없습니다</td>
              </tr>
         <%  } %>
                        </table>
                        <!----------------- 신청과정 끝 ----------------->
                        <table>
                          <tr>
                            <td height="5"></td>
                          </tr>
                        </table>
                        </div>