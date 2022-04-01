<%
//**********************************************************
//  1. 제      목: COMPLETE RATE LIST
//  2. 프로그램명: za_GoyongReportCourse_E.jsp
//  3. 개      요: 수료율 조회 엑셀
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정은년
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getString("s_grseq");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수
    String  ss_company      = box.getString("s_company");         //회사
    String  ss_report_flag  = box.getString("p_report_flag");     //레포트종류

    String startDate  = FormatDate.getRelativeDate(-60);
    String endDate = FormatDate.getRelativeDate(30);

    String searchStart = FormatDate.getFormatDate(startDate,"yyyyMMdd");
    String searchEnd   = FormatDate.getFormatDate(endDate,"yyyyMMdd");

    String  v_searchStart = box.getStringDefault("p_searchStart",searchStart).replaceAll("-","");
    String  v_searchEnd   = box.getStringDefault("p_searchEnd",searchEnd).replaceAll("-",""); 

    String  v_this_day    =FormatDate.getDate("yyyyMMdd");
    //out.println("ss_grcode      :"+ss_grcode     );
    //out.println("ss_gyear       :"+ss_gyear      );
    //out.println("ss_grseq       :"+ss_grseq      );
    //out.println("ss_grseqnm     :"+ss_grseqnm    );
    //out.println("ss_subjcourse  :"+ss_subjcourse );
    //out.println("ss_subjseq     :"+ss_subjseq    );
    //out.println("ss_company     :"+ss_company    );
    //out.println("ss_report_flag :"+ss_report_flag);
    //out.println("v_searchStart  :"+v_searchStart);
    //out.println("v_searchEnd    :"+v_searchEnd);

%>
<HTML>
<HEAD>
<TITLE></TITLE>
</HEAD>
<BODY leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<!--  뷰어를 다운로드 받는 부분 -->
<!-- 공통파일 시작-->
<%@ include file="/learn/library/oz.jsp"%>
<!-- 공통파일 끝-->
<%if("1".equals(ss_report_flag)){ //과정리스트 %>
   <param name="connection.reportname" value="goyong/course_list.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="course_list">
   <param name="odi.course_list.pcount" value="8">
   <param name="odi.course_list.args1" value="P_GRCODE=<%=ss_grcode%>">
   <param name="odi.course_list.args2" value="P_YEAR=<%=ss_gyear%>">
   <param name="odi.course_list.args3" value="P_GRSEQ=<%=ss_grseq%>">
   <param name="odi.course_list.args4" value="P_SUBJSEQ=<%=ss_subjseq%>">
   <param name="odi.course_list.args5" value="P_SUBJ=<%=ss_subjcourse%>">
   <param name="odi.course_list.args6" value="P_COMP=<%=ss_company%>">
   <param name="odi.course_list.args7" value="P_FDTE=<%=v_searchStart%>00">
   <param name="odi.course_list.args8" value="P_TDTE=<%=v_searchEnd%>23">

<%} else if("2".equals(ss_report_flag)){ //노동부지정신청서류 %>
   <param name="connection.reportname" value="goyong/nodong_fixdoc_list.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="nodong_fixdoc_list">
   <param name="odi.nodong_fixdoc_list.pcount" value="8">
   <param name="odi.nodong_fixdoc_list.args1" value="P_GRCODE=<%=ss_grcode%>">
   <param name="odi.nodong_fixdoc_list.args2" value="P_YEAR=<%=ss_gyear%>">
   <param name="odi.nodong_fixdoc_list.args3" value="P_GRSEQ=<%=ss_grseq%>">
   <param name="odi.nodong_fixdoc_list.args4" value="P_SUBJSEQ=<%=ss_subjseq%>">
   <param name="odi.nodong_fixdoc_list.args5" value="P_SUBJ=<%=ss_subjcourse%>">
   <param name="odi.nodong_fixdoc_list.args6" value="P_COMP=<%=ss_company%>">
   <param name="odi.nodong_fixdoc_list.args7" value="P_FDTE=<%=v_searchStart%>00">
   <param name="odi.nodong_fixdoc_list.args8" value="P_TDTE=<%=v_searchEnd%>23">
<%} else if("3".equals(ss_report_flag)){ //훈련실시신고보고1 %>
   <param name="connection.reportname" value="goyong/trade_list1.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="trade_list1">
   <param name="odi.trade_list1.pcount" value="8">
   <param name="odi.trade_list1.args1" value="P_GRCODE=<%=ss_grcode%>">
   <param name="odi.trade_list1.args2" value="P_YEAR=<%=ss_gyear%>">
   <param name="odi.trade_list1.args3" value="P_GRSEQ=<%=ss_grseq%>">
   <param name="odi.trade_list1.args4" value="P_SUBJSEQ=<%=ss_subjseq%>">
   <param name="odi.trade_list1.args5" value="P_SUBJ=<%=ss_subjcourse%>">
   <param name="odi.trade_list1.args6" value="P_COMP=<%=ss_company%>">
   <param name="odi.trade_list1.args7" value="P_FDTE=<%=v_searchStart%>00">
   <param name="odi.trade_list1.args8" value="P_TDTE=<%=v_searchEnd%>23">
<%} else if("4".equals(ss_report_flag)){ //훈련실시신고보고5 %>
   <param name="connection.reportname" value="goyong/trade_list2.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="trade_list2">
   <param name="odi.trade_list2.pcount" value="8">
   <param name="odi.trade_list2.args1" value="P_GRCODE=<%=ss_grcode%>">
   <param name="odi.trade_list2.args2" value="P_YEAR=<%=ss_gyear%>">
   <param name="odi.trade_list2.args3" value="P_GRSEQ=<%=ss_grseq%>">
   <param name="odi.trade_list2.args4" value="P_SUBJSEQ=<%=ss_subjseq%>">
   <param name="odi.trade_list2.args5" value="P_SUBJ=<%=ss_subjcourse%>">
   <param name="odi.trade_list2.args6" value="P_COMP=<%=ss_company%>">
   <param name="odi.trade_list2.args7" value="P_FDTE=<%=v_searchStart%>00">   
   <param name="odi.trade_list2.args8" value="P_TDTE=<%=v_searchEnd%>23">
<%} else if("5".equals(ss_report_flag)){ //수료생보고1 %>
   <param name="connection.reportname" value="goyong/suryo_repost_list1.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="suryo_repost_list1">
   <param name="odi.suryo_repost_list1.pcount" value="8">
   <param name="odi.suryo_repost_list1.args1" value="P_GRCODE=<%=ss_grcode%>">
   <param name="odi.suryo_repost_list1.args2" value="P_YEAR=<%=ss_gyear%>">
   <param name="odi.suryo_repost_list1.args3" value="P_GRSEQ=<%=ss_grseq%>">
   <param name="odi.suryo_repost_list1.args4" value="P_SUBJSEQ=<%=ss_subjseq%>">
   <param name="odi.suryo_repost_list1.args5" value="P_SUBJ=<%=ss_subjcourse%>">
   <param name="odi.suryo_repost_list1.args6" value="P_COMP=<%=ss_company%>">
   <param name="odi.suryo_repost_list1.args7" value="P_FDTE=<%=v_searchStart%>00">
   <param name="odi.suryo_repost_list1.args8" value="P_TDTE=<%=v_searchEnd%>23">
<%} else if("6".equals(ss_report_flag)){ //수료자보고 %>
   <param name="connection.reportname" value="goyong/suryo_report.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="suryo_report">
   <param name="odi.suryo_report.pcount" value="13">
   <param name="odi.suryo_report.args1" value="P_GRCODE=<%=ss_grcode%>">
   <param name="odi.suryo_report.args2" value="P_YEAR=<%=ss_gyear%>">
   <param name="odi.suryo_report.args3" value="P_GRSEQ=<%=ss_grseq%>">
   <param name="odi.suryo_report.args4" value="P_SUBJSEQ=<%=ss_subjseq%>">
   <param name="odi.suryo_report.args5" value="P_SUBJ=<%=ss_subjcourse%>">
   <param name="odi.suryo_report.args6" value="P_COMP=<%=ss_company%>">
   <param name="odi.suryo_report.args7" value="P_FDTE=<%=v_searchStart%>00">
   <param name="odi.suryo_report.args8" value="P_TDTE=<%=v_searchEnd%>23">
   <param name="odi.suryo_report.args9" value="P_DOCNO=오토에버시스템즈 <%=v_this_day.substring(0,4)%>-<%=v_this_day.substring(4,6)%>-      호">
   <param name="odi.suryo_report.args10" value="P_RECNM=서울강남지방노동사무소장">
   <param name="odi.suryo_report.args11" value="P_MYCMPNM=오토에버시스템즈㈜">
   <param name="odi.suryo_report.args12" value="P_MYCODE=12000-09-0115">
   <param name="odi.suryo_report.args13" value="P_PRESIDENTNM=김  익  교">


<%} else if("7".equals(ss_report_flag)){ //교육수료자명단 %>
   <param name="connection.reportname" value="goyong/suryo_men_repost_list.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="suryo_men_repost_mst_list">
   <param name="odi.suryo_men_repost_mst_list.pcount" value="9">
   <param name="odi.suryo_men_repost_mst_list.args1" value="P_GRCODE=<%=ss_grcode%>">
   <param name="odi.suryo_men_repost_mst_list.args2" value="P_YEAR=<%=ss_gyear%>">
   <param name="odi.suryo_men_repost_mst_list.args3" value="P_GRSEQ=<%=ss_grseq%>">
   <param name="odi.suryo_men_repost_mst_list.args4" value="P_SUBJSEQ=<%=ss_subjseq%>">
   <param name="odi.suryo_men_repost_mst_list.args5" value="P_SUBJ=<%=ss_subjcourse%>">
   <param name="odi.suryo_men_repost_mst_list.args6" value="P_COMP=<%=ss_company%>">
   <param name="odi.suryo_men_repost_mst_list.args7" value="P_FDTE=<%=v_searchStart%>00">
   <param name="odi.suryo_men_repost_mst_list.args8" value="P_TDTE=<%=v_searchEnd%>23">
   <param name="odi.suryo_men_repost_mst_list.args9" value="P_PRESIDENT=김  익  교">
<%} else if("8".equals(ss_report_flag)){ //교육훈련수료증발급대장 %>
   <param name="connection.reportname" value="goyong/suryo_license_print_list.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="suryo_license_print_list">
   <param name="odi.suryo_license_print_list.pcount" value="9">
   <param name="odi.suryo_license_print_list.args1" value="P_GRCODE=<%=ss_grcode%>">
   <param name="odi.suryo_license_print_list.args2" value="P_YEAR=<%=ss_gyear%>">
   <param name="odi.suryo_license_print_list.args3" value="P_GRSEQ=<%=ss_grseq%>">
   <param name="odi.suryo_license_print_list.args4" value="P_SUBJSEQ=<%=ss_subjseq%>">
   <param name="odi.suryo_license_print_list.args5" value="P_SUBJ=<%=ss_subjcourse%>">
   <param name="odi.suryo_license_print_list.args6" value="P_COMP=<%=ss_company%>">
   <param name="odi.suryo_license_print_list.args7" value="P_FDTE=<%=v_searchStart%>00">
   <param name="odi.suryo_license_print_list.args8" value="P_TDTE=<%=v_searchEnd%>23">
<%} else if("9".equals(ss_report_flag)){ //수납확인서 %>
   <param name="connection.reportname" value="goyong/sunap_cmf_list.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="sunap_cmf_list">
   <param name="odi.sunap_cmf_list.pcount" value="11">
   <param name="odi.sunap_cmf_list.args1" value="P_GRCODE=<%=ss_grcode%>">
   <param name="odi.sunap_cmf_list.args2" value="P_YEAR=<%=ss_gyear%>">
   <param name="odi.sunap_cmf_list.args3" value="P_GRSEQ=<%=ss_grseq%>">
   <param name="odi.sunap_cmf_list.args4" value="P_SUBJSEQ=<%=ss_subjseq%>">
   <param name="odi.sunap_cmf_list.args5" value="P_SUBJ=<%=ss_subjcourse%>">
   <param name="odi.sunap_cmf_list.args6" value="P_COMP=<%=ss_company%>">
   <param name="odi.sunap_cmf_list.args7" value="P_FDTE=<%=v_searchStart%>00">
   <param name="odi.sunap_cmf_list.args8" value="P_TDTE=<%=v_searchEnd%>23">
   <param name="odi.sunap_cmf_list.args9" value="P_ADDRESS=서울시 강남구 역삼동 837-36">
   <param name="odi.sunap_cmf_list.args10" value="P_PRESIDENT=김  익  교">
   <param name="odi.sunap_cmf_list.args11" value="P_THISDAY=<%=v_this_day.substring(0,4)%>-<%=v_this_day.substring(4,6)%>-">
<%} else if("10".equals(ss_report_flag)){ //사업자직업능력개발훈련비용 정산내역서 %>
   <param name="connection.reportname" value="goyong/aes_suryo_list1.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="aes_suryo_list1">
   <param name="odi.aes_suryo_list1.pcount" value="14">
   <param name="odi.aes_suryo_list1.args1" value="P_GRCODE=<%=ss_grcode%>">
   <param name="odi.aes_suryo_list1.args2" value="P_YEAR=<%=ss_gyear%>">
   <param name="odi.aes_suryo_list1.args3" value="P_GRSEQ=<%=ss_grseq%>">
   <param name="odi.aes_suryo_list1.args4" value="P_SUBJSEQ=<%=ss_subjseq%>">
   <param name="odi.aes_suryo_list1.args5" value="P_SUBJ=<%=ss_subjcourse%>">
   <param name="odi.aes_suryo_list1.args6" value="P_COMP=<%=ss_company%>">
   <param name="odi.aes_suryo_list1.args7" value="P_FDTE=<%=v_searchStart%>00">
   <param name="odi.aes_suryo_list1.args8" value="P_TDTE=<%=v_searchEnd%>23">
   <param name="odi.aes_suryo_list1.args9" value="P_COMPNM=오토에버시스템즈㈜">
   <param name="odi.aes_suryo_list1.args10" value="P_CEONM=김익교">
   <param name="odi.aes_suryo_list1.args11" value="P_ADDR=서울 강남구 역삼동 837-36 랜드마크타워4층">
   <param name="odi.aes_suryo_list1.args12" value="P_CMP_SCALE=대규모">
   <param name="odi.aes_suryo_list1.args13" value="P_DAMDANG=교육사업팅 강화연">
   <param name="odi.aes_suryo_list1.args14" value="P_TELLINE=02-3458-1457 / 02-3458-1388">

<%} else if("11".equals(ss_report_flag)){ //AES자체교육생 환급액 신청서류 세부 %>
   <param name="connection.reportname" value="goyong/aes_suryo_list2.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="aes_suryo_list2">
   <param name="odi.aes_suryo_list2.pcount" value="8">
   <param name="odi.aes_suryo_list2.args1" value="P_GRCODE=<%=ss_grcode%>">
   <param name="odi.aes_suryo_list2.args2" value="P_YEAR=<%=ss_gyear%>">
   <param name="odi.aes_suryo_list2.args3" value="P_GRSEQ=<%=ss_grseq%>">
   <param name="odi.aes_suryo_list2.args4" value="P_SUBJSEQ=<%=ss_subjseq%>">
   <param name="odi.aes_suryo_list2.args5" value="P_SUBJ=<%=ss_subjcourse%>">
   <param name="odi.aes_suryo_list2.args6" value="P_COMP=<%=ss_company%>">
   <param name="odi.aes_suryo_list2.args7" value="P_FDTE=<%=v_searchStart%>00">
   <param name="odi.aes_suryo_list2.args8" value="P_TDTE=<%=v_searchEnd%>23">

<%}%>
</OBJECT>
</body>
</html>



