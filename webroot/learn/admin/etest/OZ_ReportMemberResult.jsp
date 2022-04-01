<%
//**********************************************************
//  1. 제      목: e-test
//  2. 프로그램명: OZ_ReportMemberResult.jsp
//  3. 개      요: 
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 9. 30
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
<%@ page import = "com.credu.etest.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  p_etestsubj = box.getString("p_etestsubj");
    String  p_year = box.getString("p_year");
      
    box.put("p_action", "go");
    box.put("p_etestsubj", p_etestsubj);
    box.put("p_year", p_year);
    
    ETestResultBean bean = new ETestResultBean();
    Vector    vlist = bean.ReportMemberResult(box);
        
    DataBox data    = null;    
    Vector  vect_etestnum = null;  
    Vector  vect_answer   = null;     
    Vector  vect_corrected= null;      
    int etestsize     = Integer.parseInt((String)vlist.elementAt(0));  // 문제갯수  
    ArrayList alist = (ArrayList)vlist.elementAt(1); // 데이타
    ArrayList blist = (ArrayList)vlist.elementAt(2); // 문제    
    ArrayList clist = (ArrayList)vlist.elementAt(3); // 본인답    
    ArrayList dlist = (ArrayList)vlist.elementAt(4); // 정답여부    
        
%>e-test코드|e-test명|e-test문제지번호|ID|성명|직위명|점수|문제|본인답|정답여부
<% for(int i=0; i<alist.size(); i++){   
        data  = (DataBox)alist.get(i);
        vect_etestnum = (Vector)blist.get(i); 
        vect_answer   = (Vector)clist.get(i);        
        vect_corrected= (Vector)dlist.get(i);         
        for(int j=0; j<etestsize; j++){
%><%=data.getString("d_etestcode")%>|<%=data.getString("d_etesttext")%>|<%=data.getString("d_etestnum")%>|<%=data.getString("d_userid")%>|<%=data.getString("d_name")%>|<%=data.getString("d_jikwinm")%>|<%=data.getInt("d_score")%>|<%=vect_etestnum.get(j)%>|<%=vect_answer.get(j)%>|<%=vect_corrected.get(j)%>
<%}%><%}%>

        