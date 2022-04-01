<%
/**
 * file name : za_SulmunSubjResult_E.jsp
 * date      : 2003/10/09
 * programmer:
 * function  : 객관식 설문결과조회(엑셀)
 */
%>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>

<% 
	response.setHeader("Content-Disposition", "inline; filename=za_SulmunSubjResult.xls"); 
	response.setHeader("Content-Description", "JSP Generated Data"); 
%> 
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_grcode        = box.getString("p_grcode");
	String  v_subj        = box.getString("p_subj");
    String  v_subjseq        = box.getString("p_subjseq");
    String  v_gyear       = box.getString("p_gyear");
    String  v_grseq       = box.getString("p_grseq");

    String  v_company     = box.getString("p_company");
    String  v_jikwi       = box.getString("p_jikwi");
    String  v_jikun       = box.getString("p_jikun");
    String  v_workplc       = box.getString("p_workplc");
    
    int     v_replycount  = box.getInt("p_replycount");
    int     v_studentcount= box.getInt("p_studentcount");
    double  v_replyrate   = 0;
    if (v_studentcount != 0) {
        v_replyrate = (double)Math.round((double)v_replycount/v_studentcount*100*100)/100;
    }
    
	DecimalFormat  df = new DecimalFormat("0.00");
%>
<html>
<head>
<title>설문결과 객관식 Excel보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body >
      총응답자수 : <%=v_replycount%>명 / 전체수강자 : <%=v_studentcount%>명 / 설문응시율 : <%=df.format(v_replyrate)%>%
      <table width=600 border=1>
<%  ArrayList    list = (ArrayList)request.getAttribute("SulmunResultExcelPage");
    SulmunQuestionExampleData data    = null;
    SulmunExampleData         subdata = null;

	int k = 0;
	int l = 0;

    for (int i=0; i < list.size(); i++) {
        data = (SulmunQuestionExampleData)list.get(i);
        if (data.getSultype().equals("1") || data.getSultype().equals("2")) { %>        
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td colspan="3" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                    </tr>
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { %>
                    <tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                   </tr>
<%              }
            }
       } else if (data.getSultype().equals(SulmunSubjBean.SUBJECT_QUESTION)) {
          
%>
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td colspan="3" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                    </tr>
<%        int m = 0;   
			for (int j=0; j < data.getSubjectAnswer().size(); j++) {
				if(((data.getSubjectAnswer().size() / v_replycount)*m + (k+1)) == (j+1)) {   
					if(m < v_replycount) m++;
%>
					<tr>
                      <td width="7%" class="table_01"></td>
                      <td  colspan="3" style="padding-left=6" class="table_02_2"><%=(String)data.getSubjectAnswer().get(j)%></td>
                    </tr>

<%           }   
		   }
           k++;	
       } else if (data.getSultype().equals(SulmunSubjBean.COMPLEX_QUESTION)) {
          
%>
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td colspan="3" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                    </tr>
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { %>
                    <tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                    </tr>
<%           } 
           }	
%>
<%       int n = 0;    
			int etc = 0;
			for (int j=0; j < data.getComplexAnswer().size(); j++) {			
				if(((data.getComplexAnswer().size() / v_replycount)*n + (l+1)) == (j+1)) {   
					if(n < v_replycount) n++;
						if( !((String)data.getComplexAnswer().get(j)).equals("")) {
							etc++;
%>
					<tr>
                      <td width="7%" class="table_01"><%if(etc==1){%>기타<%}%></td>
                      <td  colspan="3" style="padding-left=6" class="table_02_2"><%=(String)data.getComplexAnswer().get(j)%></td>
                    </tr>
<%
					}
                }   
		   }
           l++;
       } else if (data.getSultype().equals(SulmunSubjBean.FSCALE_QUESTION)) {
          
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
%>
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td colspan="3" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
					  <td width="7%" class="table_01"><%if(v_point >=0){%><%=df.format(v_point)%>점<%}%></td>
                    </tr>
<%

		   for (int j=1; j <= data.size(); j++) {

                subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { 
%>

					<tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                      <td width="39%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>" class="graph">&nbsp;</td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
                    </tr>
<%           }    
            }	
       } else if (data.getSultype().equals(SulmunSubjBean.SSCALE_QUESTION)) {
          
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
%>
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td colspan="3" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
					  <td width="7%" class="table_01"><%if(v_point >=0){%><%=df.format(v_point)%>점<%}%></td>
                    </tr>
<%

		   for (int j=1; j <= data.size(); j++) {

                subdata  = (SulmunExampleData)data.get(j); 
                if (subdata != null) { 
%>

					<tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                      <td width="39%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>" class="graph">&nbsp;</td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
                    </tr>
<%           }    
            }	
	   } 
    } %>
      </table>

</table>
</body>
</html>
