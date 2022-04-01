<%
//**********************************************************
//  1. 제      목: 분석 > 학습자 경로분석
//  2. 프로그램명:  za_Route_E.jsp
//  3. 개      요: 학습자경로 분석
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성:
//***********************************************************
%>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page contentType = "text/html;charset=euc-kr" %> 
<%
    response.setHeader("Content-Disposition", "attachment; filename=za_Route_E.xls");   
    response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_grcode        = box.getStringDefault("s_grcode","ALL");        //교육그룹
    String  v_subj          = box.getString("s_subjcourse");
    String  v_gyear         = box.getString("s_gyear");
    String  v_subjseq       = box.getString("s_subjseq");
    int     v_sulpapernum   = box.getInt("s_sulpapernum");
    String  v_action    = box.getString("p_action");
    int i = 0 ;

    int     v_replycnt      = 0;
    double  v_replyrate     = 0;
    DecimalFormat  df = new DecimalFormat("0.00");


    String v_comlcnt  = "";
    String v_ncomlcnt = "";
    int v_educnt_i = 0; //교육인원
    int v_educnt_total = 0; //교육인원 총합
    int v_comlcnt_i = 0;    //수료인원
    int v_comlcnt_total = 0;    //수료인원 총합
    int v_ncomlcnt_i = 0;   //미수료 인원
    int v_ncomlcnt_total = 0;   //미수료인원 총합
    double v_suryopercen = 0;   //수료율
    double v_suryopercen_total = 0; //수료율 총합
    double re1 = 0;

    // 개인별
    ArrayList list1      = null;
    DataBox data1        = null;
    list1 = (ArrayList)request.getAttribute("ResultMemberList");

    // 수료율
    ArrayList studentlist = null;

    if ( v_action.equals("go") ) {
        ArrayList textlist = (ArrayList)request.getAttribute("ResultTextList");
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body >
      <!----------------- 성적별 학습현황 시작 ----------------->
      <table cellspacing="1" cellpadding="5" border="1">
        <tr>
          <td>N0</td>
          <td>가입경로</td>
          <td>교육인원</td>
          <td>수료인원</td>
          <td>미수료인원</td>
          <td>수료율</td>
        </tr>

<%  
        ArrayList    list     = (ArrayList)request.getAttribute("SulmunResultList");
        ArrayList    textlist = (ArrayList)request.getAttribute("ResultTextList");

        String v_answers  = "";
        String v_seltext  = "";
        int v_suryo       = 0;
        int v_nosuryo     = 0;
        int v_total       = 0;

        if( list.size() != 0 ){

           int suryo          =0;
           int nosuryo        =0;
           int total          =0;
           double suryopercen =0;

        /*select seltext, selnum from tz_sulsel where subj='REGIST' and grcode = 'N000001' and sulnum = '1' */
            for ( i=0; i < textlist.size(); i++) {
                DataBox t_dbox = (DataBox)textlist.get(i);

                String t_seltext = t_dbox.getString("d_seltext");
                String t_selnum  = t_dbox.getString("d_selnum");

                suryo = 0;    nosuryo = 0;    total = 0;      suryopercen = 0;

                for (int j=0; j < list.size(); j++) {
                    DataBox dbox = (DataBox)list.get(j);

                     v_answers = dbox.getString("d_answers");
                     v_seltext = dbox.getString("d_seltext");
                     v_suryo   = dbox.getInt("d_comlcnt");
                     v_nosuryo = dbox.getInt("d_ncomlcnt");
                     v_total   = v_suryo+ v_nosuryo;

                     v_suryopercen = ((v_total - v_nosuryo) /(double)v_total) * 100;

                    if( t_selnum.equals(v_answers) ){

                      suryo       = v_suryo;
                      nosuryo     = v_nosuryo;
                      total       = v_suryo + v_nosuryo;
                      suryopercen = v_suryopercen;
%>

<%
                    }
                }
%>

        <tr>
          <td><%=i+1%></td>
          <td><%=t_seltext%></td>
          <td><%=total%>명</td>
          <td><%=suryo%>명</td>
          <td><%=nosuryo%>명</td>
          <td><%=suryopercen%>%</td>
        </tr>

<%  
            }
        }

        if( list.size() == 0 ){
%>
                    <tr height="35">
                        <td colspan="6"> 경로분석에 대한 평가가 없습니다. </td>
                    </tr>
<%  
    }
%>
      </table>

</body>
</html>
