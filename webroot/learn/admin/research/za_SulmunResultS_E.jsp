<%
/**
 * file name : za_SulmunResultS_L.jsp
 * date      : 2003/08/26
 * programmer:
 * function  : 주관식 설문결과조회(엑셀)
 */
%>
<%@ page language="java" contentType="application/vnd.ms-excel; name=My_Excel; Charset=KSC5601" %> 
<% 
	response.setHeader("Content-Disposition", "inline; filename=za_SulmunResultS.xls"); 
	response.setHeader("Content-Description", "JSP Generated Data"); 
%> 
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_subj        = box.getStringDefault("p_subj",  SulmunBean.DEFAULT_SUBJ);
    String  v_gyear       = box.getString("p_gyear");
    String  v_grseq       = box.getString("p_grseq");

    int     v_sulpapernum = box.getInt("p_sulpapernum");

    String  v_grpcomp     = box.getString("p_grpcomp");
    String  v_gpm         = box.getString("p_gpm");
    String  v_dept        = box.getString("p_dept");
    String  v_jikun       = box.getString("p_jikun");
    String  v_jikup       = box.getString("p_jikup");

    int     v_replycount  = box.getInt("p_replycount");
    int     v_studentcount= box.getInt("p_studentcount");
    double  v_replyrate   = 0;
    if (v_studentcount != 0) {
        v_replyrate = (double)Math.round((double)v_replycount/v_studentcount*100*100)/100;
    }
    
    String  v_f_gubun   = "";
    if (v_subj.equals("COMMON") || v_subj.equals("WHOLE")) {
      v_f_gubun = v_subj;
    } else {
      v_f_gubun = "SUBJ";
    }
    
	String  v_grcode = "";
	if (v_subj.equals("WHOLE")) {
      v_grcode      = SulmunBean.DEFAULT_GRCODE;
	} else {
      v_grcode      = box.getStringDefault("p_grcode",SulmunBean.DEFAULT_GRCODE);
	}
    
    String  v_uclass    = box.getString("p_uclass");
    String  v_sulyear   = box.getString("p_sulyear");
    
	  String  s_gadmin = box.getSession("gadmin");
%>
<html>
<head>
<title>설문결과 주관식 Excel보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body >
총응답자수 : <%=v_replycount%>명 / 전체수강자 : <%=v_studentcount%>명 / 설문응시율 : <%=v_replyrate%>% / 설문지번호 : <%=v_sulpapernum%>
      <table width=600 border=1>
<%  ArrayList    list = (ArrayList)request.getAttribute("SResultList");
    QuestionSubjectAnswerData  data    = null;
    SubjectAnswerData          subdata = null;
    for (int i=0; i < list.size(); i++) {
        data = (QuestionSubjectAnswerData)list.get(i);
        if (data != null) {  %>
          <tr>
            <td colspan="3" >문제<%=data.getSulnum()%></td>
            <td ><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
          </tr>
<%          for (int j=0; j < data.size(); j++) {
                subdata  = (SubjectAnswerData)data.get(j); %>
          <tr>
            <td ><%=(j+1)%></td>
            <td ><%=subdata.getUserid()%></td>
            <td ><%=subdata.getName()%></td>
            <td ><%=subdata.getAnstext()%></td>
          </tr>
<%          }
        }
    } %>
        </table>

</table>
</body>
</html>
