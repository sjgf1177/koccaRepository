<%
/**
 * file name : za_DamunSubjResult_E.jsp
 * date      : 2003/10/09
 * programmer:
 * function  : 객관식 설문결과조회(엑셀)
 */
%>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>

<% 
	response.setHeader("Content-Disposition", "inline; filename=za_DamunSubjResult.xls"); 
	response.setHeader("Content-Description", "JSP Generated Data"); 
%> 
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_subjnm    = box.getString("p_subjnm");

    String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

    String  ss_grcode       = box.getString("s_grcode");
	String  ss_gyear       = box.getString("s_gyear");
	int  ss_damunpapernum       = box.getInt("s_damunpapernum");
    String  ss_subjseq    = box.getString("s_subjseq");

    String  v_company        = box.getString("s_company");
    String  v_jikwi       = box.getString("s_jikwi");
    String  v_jikun       = box.getString("s_jikun");
    String  v_workplc       = box.getString("s_workplc");
    
    int     v_replycount  = box.getInt("p_replycount");
    int     v_studentcount= box.getInt("p_studentcount");

    int     v_replyobcount  = box.getInt("p_replyobcount");
    int     v_studentobcount= box.getInt("p_studentobcount");

    double  v_replyrate   = 0;
    if (v_studentcount != 0) {
        v_replyrate = (double)Math.round((double)v_replycount/v_studentcount*100*100)/100;
    }

    int     v_replycount2  = box.getInt("p_replycount2");
    int     v_studentcount2= box.getInt("p_studentcount2");

    int     v_replyobcount2  = box.getInt("p_replyobcount2");
    int     v_studentobcount2= box.getInt("p_studentobcount2");

//    DataBox    dbox = (DataBox)request.getAttribute("DamunPaperData");

DecimalFormat  df = new DecimalFormat("0.00");
    
%>
<html>
<head>
<title>설문결과 객관식 Excel보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script> 
</head>
<body >
                  <!----------------- 엑셀출력 버튼 끝 ----------------->
<%  
  ArrayList    list = (ArrayList)request.getAttribute("DamunResultExcelPage");
    DamunQuestionExampleData data    = null;
    DamunExampleData         subdata = null;

  if((box.getString("p_d_gubun")).equals("1")){
%>
                  <!----------------- 설문분석 시작 ----------------->
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1">
                    <tr>
                     <td colspan="4" class="table_top_line"></td>
                    </tr>
                    <tr>
                      <td class="table_title_01">관찰자 총인원</td>
                      <td class="table_title_01">관찰자 참여인원</td>
                      <td class="table_title_01">대상자 총인원</td>
                      <td class="table_title_01">대상자 참여인원</td>
                    </tr>
                    <tr>
                      <td class="table_02_1"><%=v_studentobcount%></td>
                      <td class="table_02_1"><%=v_replyobcount%></td>
                      <td class="table_02_1"><%=v_studentcount%></td>
                      <td class="table_02_1"><%=v_replycount%></td>
                    </tr>
                </table>
				<br>

<%

    for (int i=0; i < list.size(); i++) {
        data = (DamunQuestionExampleData)list.get(i);
        if (data.getSultype().equals(DamunSubjBean.OBJECT_QUESTION) || data.getSultype().equals(DamunSubjBean.MULTI_QUESTION)) { %>        
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1">     
                    <tr>
                      <td width="5%" class="table_title_01">문제<%=i+1%></td>
                      <td colspan="8" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>        
                    <tr>
                      <td width="5%" class="table_01">No.</td>
                      <td width="29%" style="padding-left=6" class="table_01">보기</td>
                      <td width="34%" class="table_01">그래프</td>
                      <td width="5%" class="table_01">%</td>
					  <td  width="5%" class="table_01">집계</td>
					  <td  width="7%" class="table_01">대상자본인</td>
					  <td  width="5%" class="table_01">상사</td>
					  <td  width="5%" class="table_01">동료</td>
					  <td  width="5%" class="table_01">부하</td>
					</tr>  
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { %>
                    <tr>
                      <td width="5%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td width="34%" class="table_02_2">
                      <table cellspacing="1" cellpadding="0" class="s_table">
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
                      <td width="5%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
					  <td  width="5%" class="table_02_1"><%=subdata.getReplycnt()%></td>
					  <td  width="7%" class="table_02_1"><%=subdata.getSubjectcnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation1cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation2cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation3cnt()%></td>
                    </tr>
<%              }
            }
%>
                 </table> 
				 <br>
<%       } else if (data.getSultype().equals(DamunSubjBean.SUBJECT_QUESTION)) {
          
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1">
                    <tr>
                      <td width="5%" class="table_title_01">문제<%=i+1%></td>
                      <td colspan="5" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1">    
<%          for (int j=0; j < data.getSubjectAnswer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getSubjectAnswer().get(j)%></td>
                    </tr>

<%         }
%>
<%          for (int j=0; j < data.getR1Answer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getR1Answer().get(j)%></td>
                    </tr>

<%         }
%>
<%          for (int j=0; j < data.getR2Answer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getR2Answer().get(j)%></td>
                    </tr>

<%         }
%>
<%          for (int j=0; j < data.getR3Answer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getR3Answer().get(j)%></td>
                    </tr>

<%         }
%>
                 </table> 
				 <br>
<%
           	
       } else if (data.getSultype().equals(DamunSubjBean.COMPLEX_QUESTION)) {
          
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1">              
				    <tr>
                      <td width="5%" class="table_title_01">문제<%=i+1%></td>
                      <td colspan="8" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                    <tr>
                      <td width="5%" class="table_01">No.</td>
                      <td width="29%" style="padding-left=6" class="table_01">보기</td>
                      <td width="34%" class="table_01">그래프</td>
                      <td width="5%" class="table_01">%</td>
					  <td  width="5%" class="table_01">집계</td>
					  <td  width="7%" class="table_01">대상자본인</td>
					  <td  width="5%" class="table_01">상사</td>
					  <td  width="5%" class="table_01">동료</td>
					  <td  width="5%" class="table_01">부하</td>
					</tr>  
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { %>
                    <tr>
                      <td width="5%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td width="34%" class="table_02_2">
                      <table cellspacing="1" cellpadding="0" class="s_table">
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
                      <td width="5%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
					  <td  width="5%" class="table_02_1"><%=subdata.getReplycnt()%></td>
					  <td  width="7%" class="table_02_1"><%=subdata.getSubjectcnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation1cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation2cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation3cnt()%></td>
                    </tr>
<%           } 
           }	
%>
<%          for (int j=0; j < data.getComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
<%          for (int j=0; j < data.getR1ComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getR1ComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
<%          for (int j=0; j < data.getR2ComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getR2ComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
<%          for (int j=0; j < data.getR3ComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getR3ComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
                 </table> 
				 <br>
<%
       } else if (data.getSultype().equals(DamunSubjBean.FSCALE_QUESTION)) {
          
	          double d1 = 0; 
	          double d2 = 0; 
	          double d3 = 0; 
	          double d4 = 0; 
			  int person1 = 0;
			  int person2 = 0;
			  int person3 = 0;
			  int person4 = 0;
			  double v_point1 = 0;
			  double v_point2 = 0;
			  double v_point3 = 0;
			  double v_point4 = 0;
			  double v_point5 = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d1 += (double) (subdata.getSubjectcnt()) * subdata.getSelpoint();
					person1 += subdata.getSubjectcnt();
				}
            }	
        if(person1>0)		v_point1 = d1 / person1;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d2 += (double) (subdata.getRelation1cnt()) * subdata.getSelpoint();
					person2 += subdata.getRelation1cnt();
				}
            }	
               
        if(person2>0)		v_point2 = d2 / person2;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d3 += (double) (subdata.getRelation2cnt()) * subdata.getSelpoint();
					person3 += subdata.getRelation2cnt();
				}
            }	
               
        if(person3>0)		v_point3 = d3 / person3;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d4 += (double) (subdata.getRelation3cnt()) * subdata.getSelpoint();
					person4 += subdata.getRelation3cnt();
				}
            }	
               
        if(person4>0)		v_point4 = d4 / person4;	  

 
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1">        
				    <tr>
                      <td width="5%" class="table_title_01">문제<%=i+1%></td>
                      <td colspan="5" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                    <tr>
                      <td width="40%" style="padding-left=6" class="table_01"></td>
                      <td width="12%" class="table_01">상사진단</td>
                      <td width="12%" class="table_01">동료진단</td>
					  <td  width="12%" class="table_01">부하진단</td>
					  <td  width="12%" class="table_01">관찰자평균</td>
					  <td  width="12%" class="table_01">대상자본인</td>
					</tr>  
                    <tr>   
					  </td>
                      <td width="40%" class="table_02_1"><%=subdata.getScalename()%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point1)%></td>
                    </tr>
                 </table> 
				 <br>
<%
       } else if (data.getSultype().equals(DamunSubjBean.SSCALE_QUESTION)) {
          
	          double d1 = 0; 
	          double d2 = 0; 
	          double d3 = 0; 
	          double d4 = 0; 
			  int person1 = 0;
			  int person2 = 0;
			  int person3 = 0;
			  int person4 = 0;
			  double v_point1 = 0;
			  double v_point2 = 0;
			  double v_point3 = 0;
			  double v_point4 = 0;
			  double v_point5 = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d1 += (double) (subdata.getSubjectcnt()) * subdata.getSelpoint();
					person1 += subdata.getSubjectcnt();
				}
            }	
        if(person1>0)		v_point1 = d1 / person1;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d2 += (double) (subdata.getRelation1cnt()) * subdata.getSelpoint();
					person2 += subdata.getRelation1cnt();
				}
            }	
               
        if(person2>0)		v_point2 = d2 / person2;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d3 += (double) (subdata.getRelation2cnt()) * subdata.getSelpoint();
					person3 += subdata.getRelation2cnt();
				}
            }	
               
        if(person3>0)		v_point3 = d3 / person3;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d4 += (double) (subdata.getRelation3cnt()) * subdata.getSelpoint();
					person4 += subdata.getRelation3cnt();
				}
            }	
               
        if(person4>0)		v_point4 = d4 / person4;	  

 
%>

                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1"> 
				    <tr>
                      <td width="5%" class="table_title_01">문제<%=i+1%></td>
                      <td colspan="6" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                    <tr>
                      <td width="40%" style="padding-left=6" class="table_01"></td>
                      <td width="12%" class="table_01">상사진단</td>
                      <td width="12%" class="table_01">동료진단</td>
					  <td  width="12%" class="table_01">부하진단</td>
					  <td  width="12%" class="table_01">관찰자평균</td>
					  <td  width="12%" class="table_01">대상자본인</td>
					</tr>  
                    <tr>   
					  </td>
                      <td width="40%" class="table_02_1"><%=subdata.getScalename()%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point1)%></td>
                    </tr>
                 </table> 
				 <br>
<%
       } else if (data.getSultype().equals(DamunSubjBean.GSCALE_QUESTION)) {
               
              String before = "";
			  String after = "";
	          double d1 = 0; 
	          double d2 = 0; 
	          double d3 = 0; 
	          double d4 = 0; 
			  int person1 = 0;
			  int person2 = 0;
			  int person3 = 0;
			  int person4 = 0;
			  double v_point1 = 0;
			  double v_point2 = 0;
			  double v_point3 = 0;
			  double v_point4 = 0;
			  double v_point5 = 0;

	          double ad1 = 0; 
	          double ad2 = 0; 
	          double ad3 = 0; 
	          double ad4 = 0; 
			  int aperson1 = 0;
			  int aperson2 = 0;
			  int aperson3 = 0;
			  int aperson4 = 0;
			  double v_apoint1 = 0;
			  double v_apoint2 = 0;
			  double v_apoint3 = 0;
			  double v_apoint4 = 0;
			  double v_apoint5 = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
						before = subdata.getScalename();
					d1 += (double) (subdata.getSubjectcnt()) * subdata.getSelpoint();
					person1 += subdata.getSubjectcnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
						after = subdata.getScalename();            
					ad1 += (double) (subdata.getGSubjectcnt()) * subdata.getSelpoint();
					aperson1 += subdata.getGSubjectcnt();						
					} 
				}
            }	

        if(person1>0)		v_point1 = d1 / person1;	  
        if(aperson1>0)		v_apoint1 = ad1 / aperson1;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
					d2 += (double) (subdata.getRelation1cnt()) * subdata.getSelpoint();
					person2 += subdata.getRelation1cnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
					ad2 += (double) (subdata.getGRelation1cnt()) * subdata.getSelpoint();
					aperson2 += subdata.getGRelation1cnt();						
					} 
				}
            }	
               
        if(person2>0)		v_point2 = d2 / person2;	  
        if(aperson2>0)		v_apoint2 = ad2 / aperson2;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
					d3 += (double) (subdata.getRelation2cnt()) * subdata.getSelpoint();
					person3 += subdata.getRelation2cnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
					ad3 += (double) (subdata.getGRelation2cnt()) * subdata.getSelpoint();
					aperson3 += subdata.getGRelation2cnt();						
					} 
				}
            }	

        if(person3>0)		v_point3 = d3 / person3;	  
        if(aperson3>0)		v_apoint3 = ad3 / aperson3;	  
               
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
					d4 += (double) (subdata.getRelation3cnt()) * subdata.getSelpoint();
					person4 += subdata.getRelation3cnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
					ad4 += (double) (subdata.getGRelation3cnt()) * subdata.getSelpoint();
					aperson4 += subdata.getGRelation3cnt();						
					} 
				}
            }	
               
        if(person4>0)		v_point4 = d4 / person4;	  
        if(aperson4>0)		v_apoint4 = ad4 / aperson4;	  

 
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1">   
				    <tr>
                      <td width="5%" class="table_title_01">문제<%=i+1%></td>
                      <td colspan="6" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                    <tr>
                      <td width="40%" style="padding-left=6" class="table_01"></td>
                      <td width="12%" class="table_01">상사진단</td>
                      <td width="12%" class="table_01">동료진단</td>
					  <td  width="12%" class="table_01">부하진단</td>
					  <td  width="12%" class="table_01">관찰자평균</td>
					  <td  width="12%" class="table_01">대상자본인</td>
					</tr>  
                    <tr>   
					  </td>
                      <td width="40%" class="table_01"><%=before%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point1)%></td>
                    </tr>
                    <tr>   
					  </td>
                      <td width="40%" class="table_01"><%=after%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((ad2+ad3+ad4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint1)%></td>
                    </tr>
                    <tr>   
					  </td>
                      <td width="40%" class="table_01">GAP비교</td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint2-v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint3-v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint4-v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format((v_replyobcount > 0 ? ((ad2+ad3+ad4)/v_replyobcount) : 0)-(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0))%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint1-v_point1)%></td>
                    </tr>
                 </table> 
				 <br>

<%    
 	   } 
    } 
  
  }  else if ((box.getString("p_d_gubun")).equals("2")) {
%>

                  <!----------------- 설문분석 시작 ----------------->
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1">
                    <tr>
                     <td colspan="5" class="table_top_line"></td>
                    </tr>
                    <tr>
                      <td class="table_title_01"></td>
					  <td class="table_title_01">관찰자 총인원</td>
                      <td class="table_title_01">관찰자 참여인원</td>
                      <td class="table_title_01">대상자 총인원</td>
                      <td class="table_title_01">대상자 참여인원</td>
                    </tr>
                    <tr>
                      <td class="table_02_1">사전평가</td>
                      <td class="table_02_1"><%=v_studentobcount%></td>
                      <td class="table_02_1"><%=v_replyobcount%></td>
                      <td class="table_02_1"><%=v_studentcount%></td>
                      <td class="table_02_1"><%=v_replycount%></td>
                    </tr>
                    <tr>
                      <td class="table_02_1">사후평가</td>
                      <td class="table_02_1"><%=v_studentobcount2%></td>
                      <td class="table_02_1"><%=v_replyobcount2%></td>
                      <td class="table_02_1"><%=v_studentcount2%></td>
                      <td class="table_02_1"><%=v_replycount2%></td>
                    </tr>
                </table>
				<br>

<%

    for (int i=0; i < list.size(); i++) {
        data = (DamunQuestionExampleData)list.get(i);
        if (data.getSultype().equals(DamunSubjBean.OBJECT_QUESTION) || data.getSultype().equals(DamunSubjBean.MULTI_QUESTION)) { %>        
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1"> 
                    <tr>
                      <td width="5%" class="table_title_01">문제<%=i+1%></td>
                      <td colspan="8" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>            
                    <tr>
                      <td width="5%" class="table_01">No.</td>
                      <td width="29%" style="padding-left=6" class="table_01">보기</td>
                      <td width="34%" class="table_01">그래프</td>
                      <td width="5%" class="table_01">%</td>
					  <td  width="5%" class="table_01">집계</td>
					  <td  width="7%" class="table_01">대상자본인</td>
					  <td  width="5%" class="table_01">상사</td>
					  <td  width="5%" class="table_01">동료</td>
					  <td  width="5%" class="table_01">부하</td>
					</tr>  
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { %>
                    <tr>
                      <td width="5%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td width="34%" class="table_02_2">
                      <table cellspacing="1" cellpadding="0" class="s_table">
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
                      <td width="5%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
					  <td  width="5%" class="table_02_1"><%=subdata.getReplycnt()%></td>
					  <td  width="7%" class="table_02_1"><%=subdata.getSubjectcnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation1cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation2cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation3cnt()%></td>
                    </tr>
<%              }
            }
%>
                 </table> 
				 <br>
<%       } else if (data.getSultype().equals(DamunSubjBean.SUBJECT_QUESTION)) {
          
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1">
                    <tr>
                      <td width="5%" class="table_title_01">문제<%=i+1%></td>
                      <td colspan="5" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1">    
<%          for (int j=0; j < data.getSubjectAnswer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getSubjectAnswer().get(j)%></td>
                    </tr>

<%         }
%>
<%          for (int j=0; j < data.getR1Answer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getR1Answer().get(j)%></td>
                    </tr>

<%         }
%>
<%          for (int j=0; j < data.getR2Answer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getR2Answer().get(j)%></td>
                    </tr>

<%         }
%>
<%          for (int j=0; j < data.getR3Answer().size(); j++) {
%>
					<tr>
                      <td  colspan="6" style="padding-left=6" class="table_02_2"><%=(String)data.getR3Answer().get(j)%></td>
                    </tr>

<%         }
%>
                 </table> 
				 <br>
<%
           	
       } else if (data.getSultype().equals(DamunSubjBean.COMPLEX_QUESTION)) {
          
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1">           
				    <tr>
                      <td width="5%" class="table_title_01">문제<%=i+1%></td>
                      <td colspan="8" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                    <tr>
                      <td width="5%" class="table_01">No.</td>
                      <td width="29%" style="padding-left=6" class="table_01">보기</td>
                      <td width="34%" class="table_01">그래프</td>
                      <td width="5%" class="table_01">%</td>
					  <td  width="5%" class="table_01">집계</td>
					  <td  width="7%" class="table_01">대상자본인</td>
					  <td  width="5%" class="table_01">상사</td>
					  <td  width="5%" class="table_01">동료</td>
					  <td  width="5%" class="table_01">부하</td>
					</tr>  
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { %>
                    <tr>
                      <td width="5%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td width="34%" class="table_02_2">
                      <table cellspacing="1" cellpadding="0" class="s_table">
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
                      <td width="5%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
					  <td  width="5%" class="table_02_1"><%=subdata.getReplycnt()%></td>
					  <td  width="7%" class="table_02_1"><%=subdata.getSubjectcnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation1cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation2cnt()%></td>
					  <td  width="5%" class="table_02_1"><%=subdata.getRelation3cnt()%></td>
                    </tr>
<%           } 
           }	
%>
<%          for (int j=0; j < data.getComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
<%          for (int j=0; j < data.getR1ComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getR1ComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
<%          for (int j=0; j < data.getR2ComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getR2ComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
<%          for (int j=0; j < data.getR3ComplexAnswer().size(); j++) {
%>
					<tr>
                      <td width="5%" class="table_01"><%if(j==0){%>기타<%}%></td>
                      <td  colspan="5" style="padding-left=6" class="table_02_2"><%=(String)data.getR3ComplexAnswer().get(j)%></td>
                    </tr>
<%
                }
%>
                 </table> 
				 <br>
<%
       } else if (data.getSultype().equals(DamunSubjBean.FSCALE_QUESTION)) {
          
	          double d1 = 0; 
	          double d2 = 0; 
	          double d3 = 0; 
	          double d4 = 0; 
			  int person1 = 0;
			  int person2 = 0;
			  int person3 = 0;
			  int person4 = 0;
			  double v_point1 = 0;
			  double v_point2 = 0;
			  double v_point3 = 0;
			  double v_point4 = 0;
			  double v_point5 = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d1 += (double) (subdata.getSubjectcnt()) * subdata.getSelpoint();
					person1 += subdata.getSubjectcnt();
				}
            }	
        if(person1>0)		v_point1 = d1 / person1;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d2 += (double) (subdata.getRelation1cnt()) * subdata.getSelpoint();
					person2 += subdata.getRelation1cnt();
				}
            }	
               
        if(person2>0)		v_point2 = d2 / person2;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d3 += (double) (subdata.getRelation2cnt()) * subdata.getSelpoint();
					person3 += subdata.getRelation2cnt();
				}
            }	
               
        if(person3>0)		v_point3 = d3 / person3;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d4 += (double) (subdata.getRelation3cnt()) * subdata.getSelpoint();
					person4 += subdata.getRelation3cnt();
				}
            }	
               
        if(person4>0)		v_point4 = d4 / person4;	  

 
%>
         <!--        <table cellspacing="1" cellpadding="0" class="box_table_out" border="1">
                     <tr>
                      <td width="5%" class="table_title_01">문제<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                </table>-->
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1"> 
				    <tr>
                      <td width="5%" class="table_title_01">문제<%=i+1%></td>
                      <td colspan="6" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                    <tr>
                      <td width="40%" style="padding-left=6" class="table_01"></td>
                      <td width="12%" class="table_01">상사진단</td>
                      <td width="12%" class="table_01">동료진단</td>
					  <td  width="12%" class="table_01">부하진단</td>
					  <td  width="12%" class="table_01">관찰자평균</td>
					  <td  width="12%" class="table_01">대상자본인</td>
					</tr>  
                    <tr>   
					  </td>
                      <td width="40%" class="table_02_1"><%=subdata.getScalename()%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point1)%></td>
                    </tr>
                 </table> 
				 <br>
<%
       } else if (data.getSultype().equals(DamunSubjBean.SSCALE_QUESTION)) {
          
	          double d1 = 0; 
	          double d2 = 0; 
	          double d3 = 0; 
	          double d4 = 0; 
			  int person1 = 0;
			  int person2 = 0;
			  int person3 = 0;
			  int person4 = 0;
			  double v_point1 = 0;
			  double v_point2 = 0;
			  double v_point3 = 0;
			  double v_point4 = 0;
			  double v_point5 = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d1 += (double) (subdata.getSubjectcnt()) * subdata.getSelpoint();
					person1 += subdata.getSubjectcnt();
				}
            }	
        if(person1>0)		v_point1 = d1 / person1;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d2 += (double) (subdata.getRelation1cnt()) * subdata.getSelpoint();
					person2 += subdata.getRelation1cnt();
				}
            }	
               
        if(person2>0)		v_point2 = d2 / person2;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d3 += (double) (subdata.getRelation2cnt()) * subdata.getSelpoint();
					person3 += subdata.getRelation2cnt();
				}
            }	
               
        if(person3>0)		v_point3 = d3 / person3;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					d4 += (double) (subdata.getRelation3cnt()) * subdata.getSelpoint();
					person4 += subdata.getRelation3cnt();
				}
            }	
               
        if(person4>0)		v_point4 = d4 / person4;	  

 
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1">              
				    <tr>
                      <td width="5%" class="table_title_01">문제<%=i+1%></td>
                      <td colspan="6" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                    <tr>
                      <td width="40%" style="padding-left=6" class="table_01"></td>
                      <td width="12%" class="table_01">상사진단</td>
                      <td width="12%" class="table_01">동료진단</td>
					  <td  width="12%" class="table_01">부하진단</td>
					  <td  width="12%" class="table_01">관찰자평균</td>
					  <td  width="12%" class="table_01">대상자본인</td>
					</tr>  
                    <tr>   
					  </td>
                      <td width="40%" class="table_02_1"><%=subdata.getScalename()%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point1)%></td>
                    </tr>
                 </table> 
				 <br>
<%
       } else if (data.getSultype().equals(DamunSubjBean.GSCALE_QUESTION)) {
               
              String before = "";
			  String after = "";
	          double d1 = 0; 
	          double d2 = 0; 
	          double d3 = 0; 
	          double d4 = 0; 
			  int person1 = 0;
			  int person2 = 0;
			  int person3 = 0;
			  int person4 = 0;
			  double v_point1 = 0;
			  double v_point2 = 0;
			  double v_point3 = 0;
			  double v_point4 = 0;
			  double v_point5 = 0;

	          double ad1 = 0; 
	          double ad2 = 0; 
	          double ad3 = 0; 
	          double ad4 = 0; 
			  int aperson1 = 0;
			  int aperson2 = 0;
			  int aperson3 = 0;
			  int aperson4 = 0;
			  double v_apoint1 = 0;
			  double v_apoint2 = 0;
			  double v_apoint3 = 0;
			  double v_apoint4 = 0;
			  double v_apoint5 = 0;
      
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
						before = subdata.getScalename();
					d1 += (double) (subdata.getSubjectcnt()) * subdata.getSelpoint();
					person1 += subdata.getSubjectcnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
						after = subdata.getScalename();            
					ad1 += (double) (subdata.getGSubjectcnt()) * subdata.getSelpoint();
					aperson1 += subdata.getGSubjectcnt();						
					} 
				}
            }	

        if(person1>0)		v_point1 = d1 / person1;	  
        if(aperson1>0)		v_apoint1 = ad1 / aperson1;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
					d2 += (double) (subdata.getRelation1cnt()) * subdata.getSelpoint();
					person2 += subdata.getRelation1cnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
					ad2 += (double) (subdata.getGRelation1cnt()) * subdata.getSelpoint();
					aperson2 += subdata.getGRelation1cnt();						
					} 
				}
            }	
               
        if(person2>0)		v_point2 = d2 / person2;	  
        if(aperson2>0)		v_apoint2 = ad2 / aperson2;	  

		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
					d3 += (double) (subdata.getRelation2cnt()) * subdata.getSelpoint();
					person3 += subdata.getRelation2cnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
					ad3 += (double) (subdata.getGRelation2cnt()) * subdata.getSelpoint();
					aperson3 += subdata.getGRelation2cnt();						
					} 
				}
            }	

        if(person3>0)		v_point3 = d3 / person3;	  
        if(aperson3>0)		v_apoint3 = ad3 / aperson3;	  
               
		   for (int j=1; j <= data.size(); j++) {
	            subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { 

					if(subdata.getSelnum() < 6 ) { 
					d4 += (double) (subdata.getRelation3cnt()) * subdata.getSelpoint();
					person4 += subdata.getRelation3cnt();

					}else if(subdata.getSelnum() > 5 && subdata.getSelnum() <= 10){
					ad4 += (double) (subdata.getGRelation3cnt()) * subdata.getSelpoint();
					aperson4 += subdata.getGRelation3cnt();						
					} 
				}
            }	
               
        if(person4>0)		v_point4 = d4 / person4;	  
        if(aperson4>0)		v_apoint4 = ad4 / aperson4;	  

 
%>
                 <table cellspacing="1" cellpadding="0" class="box_table_out" border="1">  
				    <tr>
                      <td width="5%" class="table_title_01">문제<%=i+1%></td>
                      <td colspan="6" style="padding-left:4px" class="table_title_02"><%=data.getSultext()%></td>
                    </tr>
                    <tr>
                      <td width="40%" style="padding-left=6" class="table_01"></td>
                      <td width="12%" class="table_01">상사진단</td>
                      <td width="12%" class="table_01">동료진단</td>
					  <td  width="12%" class="table_01">부하진단</td>
					  <td  width="12%" class="table_01">관찰자평균</td>
					  <td  width="12%" class="table_01">대상자본인</td>
					</tr>  
                    <tr>   
					  </td>
                      <td width="40%" class="table_01"><%=before%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_point1)%></td>
                    </tr>
                    <tr>   
					  </td>
                      <td width="40%" class="table_01"><%=after%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_replyobcount > 0 ? ((ad2+ad3+ad4)/v_replyobcount) : 0)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint1)%></td>
                    </tr>
                    <tr>   
					  </td>
                      <td width="40%" class="table_01">GAP비교</td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint2-v_point2)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint3-v_point3)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint4-v_point4)%></td>
					  <td  width="12%" class="table_02_1"><%=df.format((v_replyobcount > 0 ? ((ad2+ad3+ad4)/v_replyobcount) : 0)-(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0))%></td>
					  <td  width="12%" class="table_02_1"><%=df.format(v_apoint1-v_point1)%></td>
                    </tr>
                 </table> 
				 <br>

<%    
 	   } 
    }   
  
  }
%>

                  <!----------------- 설문분석 끝 ----------------->

</table>
</body>
</html>
