<%
//**********************************************************
//  1. 제      목: 외부학습
//  2. 프로그램명: EduStart.jspup
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
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
	String domain = request.getServerName();
    String v_url        = "";
    String yeslearn_url = conf.getProperty("yeslearn.url.value");

    String field1   = box.getString("FIELD1");
    String field2   = box.getString("FIELD2");
    String field3   = box.getString("FIELD3");
    String field4   = box.getString("FIELD4");
    String field5   = box.getString("FIELD5");
    String field99  = box.getString("FIELD99");
    String field100 = box.getString("FIELD100");
    String p_iurl   = box.getString("p_iurl");
    String p_eduurl   = box.getString("eduurl");
    String isoutsourcing   = box.getString("isoutsourcing");
    String v_subj     = box.getString("p_subj");
    String v_year     = box.getString("p_year");
    String v_subjseq  = box.getString("p_subjseq");
    String v_isinfo  = box.getString("p_isinfo");
	String v_gadmin   = box.getSession("gadmin");

    MasterFormData  da  =(MasterFormData)request.getAttribute("MasterFormData");
    int     v_width         = 0;
    int     v_height        = 0;

    //외주과정
    if (isoutsourcing.equals("Y")) {
//        v_url = p_eduurl;
//        v_url += "?FIELD1=" + field1 + "&FIELD2=" + field2 + "&FIELD3=" + field3 + "&FIELD4=" + field4 + "&FIELD99=" + field99+"&FIELD100="+field100;
		  v_url = EduEtc1Bean.getCpEduurl(field1, v_subj, v_year, v_subjseq, v_gadmin);
    } else {
        v_url = yeslearn_url + "/esp_letslearnSet.asp";
        v_url += "?FIELD1=" + field1 + "&FIELD2=" + field2 + "&FIELD3=" + field3 + "&FIELD4=" + field4 + "&FIELD5=" + field5 + "&FIELD99=" + field99+"&FIELD100="+field100+"&FIELD101="+domain;
//        v_width         = da.getWidth(); 
//        v_height        = da.getHeight();
    }

    String progress = "";
    SubjGongAdminBean gongbean = new SubjGongAdminBean();
    progress = gongbean.getProgress2(box);

		SulmunAllPaperBean sulbean = new SulmunAllPaperBean();
		int sulprogress = sulbean.getSulProgress(v_subj, v_year, v_subjseq);
		int sulresult = sulbean.getSulResult(v_subj, v_year, v_subjseq);

    EduStartBean bean = new EduStartBean();
    
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function resizeWindow() {
        w = <%=v_width%>;
        h = <%=v_height%>;
        var v_w = '', v_h='';
        
        if (w==99999)   v_w = screen.availWidth;
        else if(w==0)   v_w = '1024';
        else            v_w = w;
        if (h==99999)   v_h = screen.availHeight;
        else if(h==0)   v_h = screen.availHeight;
        else            v_h = h;
        
//        if (parseInt(navigator.appVersion)>3) {
           top.moveTo(0,0);
           top.resizeTo(v_w, v_h);
//        }
    }

resizeWindow();

/*
var delay=2;

var timer;
var clock=delay;


function fn_delay(){
	if (clock>0) {
		clock--;
		timer=setTimeout("fn_delay()",1000);
	} else {
<%  if (!isoutsourcing.equals("Y")) { %>
    resizeWindow();
<% } %>

	}
}

fn_delay();
*/
// 정보동의

function openInfomation() {
    window.open('/learn/user/homepage/zu_student_notice.jsp?p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>','openTong','width=550,height=630,scrollbars=no,resizable=no');

    // 설문 팝업 
    function goSulpaper() {
        var url = "/servlet/controller.study.StudySulmunServlet?&p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>&out=Y";
       window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width=800,height=600 ").focus();        
    }

<%
   // 처음로긴시 : 0, 동의후 : 1
       int isOk = bean.firstCheck(box);
       if (isOk == 0) {
%>
<%if(!v_isinfo.equals("N")){%>
           openInfomation();
<%}%>
<%
      }
%>
// 설문 팝업


<%if(Double.parseDouble(progress)>=sulprogress && sulprogress > 0 && sulresult == 0){%>
    goSulpaper();
<%}%>

    document.location = '<%=v_url%>';
//-->
</SCRIPT>