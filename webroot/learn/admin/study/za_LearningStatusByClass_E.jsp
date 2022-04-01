<%
//**********************************************************
//  1. 제      목: LEARNING STATUS EXCEL BY CLASS
//  2. 프로그램명: za_LearningStatusByClass_E.jsp
//  3. 개      요: 클래스별 학습현황
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.1.28
//  7. 수      정:
//***********************************************************
%>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_userid    = box.getSession("userid");
    String  s_gadmin    = box.getSession("gadmin");

    String  ss_grcode     = box.getStringDefault("s_grcode","ALL");    //교육그룹
    String  ss_gyear      = box.getStringDefault("s_gyear","ALL");     //년도
    String  ss_grseq      = box.getStringDefault("s_grseq","ALL");     //교육차수
    String  ss_uclass     = box.getStringDefault("s_upperclass","ALL");    //과정분류
    String  ss_mclass     = box.getStringDefault("s_middleclass","ALL");    //과정분류
    String  ss_lclass     = box.getStringDefault("s_lowerclass","ALL");    //과정분류
    String  ss_subjcourse =box.getStringDefault("s_subjcourse","ALL");//과정&코스
    String  ss_subjseq    = box.getStringDefault("s_subjseq","ALL");   //과정 차수

    String v_param = "";

    if (!ss_grcode.equals("ALL"))     v_param+= " and C.grcode = "+SQLString.Format(ss_grcode);
    v_param+= "  and C.gyear = "+SQLString.Format(ss_gyear);
    if (!ss_grseq.equals("ALL"))      v_param+= " and C.grseq = "+SQLString.Format(ss_grseq);
    if (!ss_uclass.equals("ALL"))     v_param+= " and C.scupperclass = "+SQLString.Format(ss_uclass);
    if (!ss_mclass.equals("ALL"))     v_param+= " and C.scmiddleclass = "+SQLString.Format(ss_mclass);
    if (!ss_lclass.equals("ALL"))     v_param+= " and C.sclowerclass = "+SQLString.Format(ss_lclass);
    if (!ss_subjcourse.equals("ALL")) v_param+= " and C.scsubj = "+SQLString.Format(ss_subjcourse);
    if (!ss_subjseq.equals("ALL"))    v_param+= " and C.scsubjseq = "+SQLString.Format(ss_subjseq);

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    String  v_orderColumn  = box.getString("p_orderColumn");           //정렬할 컬럼명

	String order = "";
    if (v_orderColumn.equals("subj"))     v_orderColumn ="c.subj";
    if (v_orderColumn.equals("userid"))   v_orderColumn ="b.userid";
    if (v_orderColumn.equals("name"))     v_orderColumn ="b.name";
    if (v_orderColumn.equals("compnm1"))  v_orderColumn ="get_compnm(b.comp,2,2)";
    if (v_orderColumn.equals("compnm2"))  v_orderColumn ="get_deptnm(b.deptnam, b.userid)";
    if (v_orderColumn.equals("jiknm"))    v_orderColumn ="get_jikwinm(b.jikwi,b.comp)";

    if(v_orderColumn.equals("")) {
        order = " order by C.subj, C.year, C.subjseq";
    } else {
        order = " order by " + v_orderColumn + v_orderType;
    }
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
   <param name="connection.reportname" value="study/LearningStatusByClass.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="LearningStatusByClass">
   <param name="odi.LearningStatusByClass.pcount" value="2">
   <param name="odi.LearningStatusByClass.args1" value="condition=<%=v_param%>">
   <param name="odi.LearningStatusByClass.args2" value="order=<%=order%>">
</OBJECT>


</body>
</HTML>

