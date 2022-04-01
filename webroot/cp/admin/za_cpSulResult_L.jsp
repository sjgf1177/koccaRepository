<%
//**********************************************************
//  1. 제      목: 업체설문 레포트
//  2. 프로그램명: za_cpSulResult_L.jsp
//  3. 개      요:
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 10. 1
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.cp.*" %>
<jsp:useBean id="conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    int v_sulpapernum = box.getInt("s_sulpapernum");
    String v_year = box.getString("p_year");
    DBConnectionManager connMgr = null;
    //out.println("v_sulpapernum==>>>"+v_sulpapernum+"<br>");
    CPSulmunBean bean = new CPSulmunBean();
    ArrayList    list1 = bean.SelectSulmunUserList(box);
    int total = 0;
    int i = 0;
    connMgr = new DBConnectionManager();

    StringTokenizer st1 = null;
    StringTokenizer st2 = null;
    String v_sulnum = "";
    String v_answernum = "";
    //int v_sulpoint  = 0;
    
    try{
%>
sulgubun,sultext,cpnm,selpoint
<%
    for(i = 0 ; i<list1.size(); i++){

      DataBox dbox = (DataBox)list1.get(i);
      String v_cpnm    = dbox.getString("d_cpnm");
      String v_userid  = dbox.getString("d_userid");
      String v_sulnums = dbox.getString("d_sulnums");
      String v_answers = dbox.getString("d_answers");

      st1 = new StringTokenizer(v_sulnums,",");
      st2 = new StringTokenizer(v_answers,",");
      
      while (st1.hasMoreElements()) {
        v_sulnum = (String)st1.nextToken();
        v_answernum = (String)st2.nextToken();

        DataBox dbox1 = bean.SelectSulAnswerInfo(connMgr, v_year, v_sulnum, v_answernum);
        String v_sultext = dbox1.getString("d_sultext");
        String v_gubuntxt = dbox1.getString("d_gubuntxt");
        int v_selpoint = dbox1.getInt("d_selpoint");
%><%=v_gubuntxt%>,<%=v_sulnum%>. <%=v_sultext%>,<%=v_cpnm%>,<%=v_selpoint%>
<%
      }
    }
   }catch(Exception ex){
      ErrorManager.getErrorStackTrace(ex);
      throw new Exception(ex.getMessage());
    }finally{
      if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
    }

%>
