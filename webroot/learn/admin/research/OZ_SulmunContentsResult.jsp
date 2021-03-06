<%
//**********************************************************
//  1. 제      목: 설문
//  2. 프로그램명: OZ_SulmunContentsResult_L.jsp
//  3. 개      요: 콘텐츠설문 - 설문결과조회
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 6. 30
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
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_grcode    = box.getStringDefault("s_grcode","ALL");        //교육그룹

    String  v_subj        = box.getStringDefault("s_subjcourse",  SulmunContentsBean.DEFAULT_SUBJ);
    String  v_gyear       = box.getString("s_gyear");
    String  v_subjseq       = box.getString("s_subjseq");

    String  v_company        = box.getString("s_company");
    String  v_jikwi       = box.getString("s_jikwi");
    String  v_jikun       = box.getString("s_jikun");
    String  v_workplc       = box.getString("s_workplc");

    String  s_gadmin = box.getSession("gadmin");

    String v_tab_color1 = "black";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
    String v_tab_color4 = "blue";
    String v_tab_color5 = "black";
    DecimalFormat  df = new DecimalFormat("0.00");
%>
총응답자수|설문번호|문제내용|설문상세번호|상세설명|명수|퍼센트|점수
<%
    box.put("p_action", "go" );
    box.put("s_grcode",v_grcode );
    box.put("s_gyear",v_gyear ); 
    box.put("s_subjcourse",v_subj );       
    box.put("s_subj",   v_subj );    
    box.put("s_subjseq",v_subjseq );

    SulmunContentsResultBean bean = new SulmunContentsResultBean();
    ArrayList    list = bean.SelectObectResultList(box);
    int     v_replycount  = box.getInt("p_replycount");
    SulmunQuestionExampleData data    = null;
    SulmunExampleData         subdata = null;   
    for (int i=0; i < list.size(); i++) {
        data = (SulmunQuestionExampleData)list.get(i);
        if (data.getSultype().equals(SulmunContentsBean.OBJECT_QUESTION) || data.getSultype().equals(SulmunContentsBean.MULTI_QUESTION)) {//단일,복수

          for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) { %><%=v_replycount%>|<%=subdata.getSulnum()%>|<%=data.getSultext()%>|<%=subdata.getSelnum()%>|<%=subdata.getSeltext()%>|<%=subdata.getReplycnt()%>|<%=subdata.getReplyrate()%>
<%              }
            }
       } else if (data.getSultype().equals(SulmunContentsBean.SUBJECT_QUESTION)) {


       } else if (data.getSultype().equals(SulmunContentsBean.COMPLEX_QUESTION)) {

          for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) { %><%=v_replycount%>|<%=subdata.getSulnum()%>|<%=data.getSultext()%>|<%=subdata.getSelnum()%>|<%=subdata.getSeltext()%>|<%=subdata.getReplycnt()%>|<%=subdata.getReplyrate()%><%           }
           }

%><%=v_replycount%>|<%=subdata.getSulnum()%>|<%=data.getSultext()%>||기타|<%=data.getComplexAnswer().size()%>|0|0
<%

       } else if (data.getSultype().equals(SulmunContentsBean.FSCALE_QUESTION)) {//5점척도
              double d = 0;
              int person = 0;
              double v_point = 0;

           for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {        
                    d +=  (subdata.getReplycnt()) * subdata.getSelpoint();
                    person += subdata.getReplycnt();
                }
            }

        v_point = d / person;

           for (int j=1; j <= data.size(); j++) {

                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {
%><%=v_replycount%>|<%=subdata.getSulnum()%>|<%=data.getSultext()%>|<%=subdata.getSelnum()%>|<%=subdata.getSeltext()%>|<%=subdata.getReplycnt()%>|<%=subdata.getReplyrate()%>|<%if(v_point >=0){%><%=df.format(v_point)%><%}%>
<%           }
            }
       } else if (data.getSultype().equals(SulmunContentsBean.SSCALE_QUESTION)) {

              double d = 0;
              int person = 0;
              double v_point = 0;

           for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {

                    d +=  (subdata.getReplycnt()) * subdata.getSelpoint();
                    person += subdata.getReplycnt();
                }
            }

        v_point = d / person;

           for (int j=1; j <= data.size(); j++) {

                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {
%><%=v_replycount%>|<%=subdata.getSulnum()%>|<%=data.getSultext()%>|<%=subdata.getSelnum()%>|<%=subdata.getSeltext()%>|<%=subdata.getReplycnt()%>|<%=subdata.getReplyrate()%>|<%if(v_point >=0){%><%=df.format(v_point)%><%}%>
<%           }
            }
       }
    } %>

