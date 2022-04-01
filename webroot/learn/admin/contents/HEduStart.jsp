<%
//**********************************************************
//  1. 제      목: 외부학습
//  2. 프로그램명: EduStart.jsp
//  3. 개      요: 외부학습
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
%>

<HTML>
<HEAD>
<TITLE> </TITLE>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
</HEAD>

<BODY>
<%
    // 세션 세팅
    String v_userip = request.getRemoteAddr();

    box.put("p_userid", box.getString("FIELD1"));
    box.put("p_userip", v_userip);

    LoginBean lgbean = new LoginBean();
    int isOk1 = lgbean.ssologin(box);
    if (isOk1 != 1) {
        out.println("<SCRIPT LANGUAGE=\"JavaScript\">");
        out.println("<!--                          ");
        out.println("alert('유저 정보가 없습니다.');  ");
        out.println("self.close();                 ");
        out.println("//-->                         ");
        out.println("</SCRIPT>                     ");
    } else {

        String v_url        = "";

        String field1   = box.getString("FIELD1");      // USERID
        String field2   = box.getString("FIELD2");      // 년도
        String field3   = box.getString("FIELD3");      // 과정코드

        box.put("FIELD4", CodeConfigBean.addZero(Integer.parseInt(box.getString("FIELD4")), 4));
        String field4   = box.getString("FIELD4");      // 과정차수
        String field5   = box.getString("FIELD5");      //
        String field99  = box.getString("FIELD99");
        String field100 = box.getString("FIELD100");

        String v_user_id       = field1;
        String v_eduurl        = "";
        String v_isoutsourcing = "";
        String v_subj          = "";
        String v_year          = "";
        String v_subjseq       = "";
        String v_contenttype   = "";
        String v_cpsubj        = "";
        String v_cpsubjseq     = "";
        String v_aesseq        = "";
        String v_company       = "";
        v_company     = box.getSession("comp");

        GrseqBean grbean = new GrseqBean();
        DataBox dbox = grbean.getSubjInfo(box);

        if (dbox == null) {
            out.println("<SCRIPT LANGUAGE=\"JavaScript\">");
            out.println("<!--                            ");
            out.println("alert('과정 정보가 없습니다.');    ");
            out.println("self.close();                   ");
            out.println("//-->                           ");
            out.println("</SCRIPT>                       ");
        } else {
            v_eduurl      = dbox.getString("d_eduurl");
            v_isoutsourcing = dbox.getString("d_isoutsourcing");
            v_subj        = dbox.getString("d_subj");
            v_year        = dbox.getString("d_year");
            v_subjseq     = dbox.getString("d_subjseq");
            v_contenttype = dbox.getString("d_contenttype");
            v_cpsubj      = dbox.getString("d_cpsubj");
            v_cpsubjseq   = dbox.getString("d_cpsubjseq");
            v_aesseq      = dbox.getString("d_aesseq");


            int     v_width         = 0;
            int     v_height        = 0;

            v_url = "/servlet/controller.contents.EduStart";

            box.setSession("s_subj",    v_subj);
            box.setSession("s_year",    v_year);
            box.setSession("s_subjseq", v_subjseq);

            //학습권한 얻기 (Y/P/N)
            String  v_eduauth = "";

            if (field100.equals("Y")) {
                box.setSession("s_eduauth","P");    //교육간 권한 세션 설정(P:콘텐츠 조회만 가능, 입력불가)
                v_eduauth = "P";
            } else {
                box.setSession("s_eduauth",v_eduauth);  //교육간 권한 세션 설정(P:콘텐츠 조회만 가능, 입력불가)
                v_eduauth = EduEtc1Bean.get_eduAuth(box);
            }


    if(v_eduauth.equals("N")){
%>
    <script>
    alert("[<%=box.getSession("gadmin")%>][<%=box.getSession("s_subj")%>]죄송합니다. 학습권한이 없으므로 학습창을 닫습니다.\n\n문의사항은 운영자에게 연락하여 주십시오");
    self.close();
    </script>
<%
    }

%>

<form name ='form1' method='post' action=''>
    <input type='hidden' name = 'p_process'   value = ''>
    <input type='hidden' name = 'contenttype' value = '<%=v_contenttype%>'>
    <input type='hidden' name = 'p_subj'      value = '<%=v_subj%>'>
    <input type='hidden' name = 'p_year'      value = '<%=v_year%>'>
    <input type='hidden' name = 'p_subjseq'   value = '<%=v_subjseq%>'>
    <input type='hidden' name = 'FIELD1'      value = '<%=v_user_id%>'>
    <input type='hidden' name = 'FIELD2'      value = '<%=v_year%>'>
    <input type='hidden' name = 'FIELD3'      value = '<%=v_cpsubj%>'>
    <input type='hidden' name = 'FIELD4'      value = '<%=v_cpsubjseq%>'>
    <input type='hidden' name = 'FIELD5'      value = '<%=v_aesseq%>'>
    <input type='hidden' name = 'FIELD99'     value = '<%=v_company%>'>
    <input type='hidden' name = 'FIELD100'    value = 'N'>

</form>

<SCRIPT LANGUAGE="JavaScript">
<!--

// 공지사항
function studyOpen1() {
    open_window('openGong','/servlet/controller.study.SubjGongStudyServlet?p_process=yeslist&p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>',0,0,800,600,false,false,true,true,true);
}

studyOpen1();

//학습창
function goEdu() {
    document.form1.target = "_self"
    document.form1.p_process.value   = "main";
    document.form1.action            = "/servlet/controller.contents.EduStart";
    document.form1.submit();
}

goEdu();
//-->
</SCRIPT>


<%

        }   // 과정정보가 없을경우
    }       // 유저 정보가 없을경우
%>

</BODY>
</HTML>