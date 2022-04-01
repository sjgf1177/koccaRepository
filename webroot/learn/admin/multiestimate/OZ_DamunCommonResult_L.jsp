<%
/**
 * file name : OZ_DamunCommonResult_L.jsp
 * date      : 2003/08/26
 * programmer:
 * function  : 객관식 설문결과조회
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    String  v_subjnm    = box.getString("p_subjnm");

    String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

    String  ss_grcode       = box.getString("s_grcode");
	String  ss_gyear       = box.getString("s_gyear");
	String  ss_grseq       = box.getString("s_grseq");
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

    DataBox    dbox = (DataBox)request.getAttribute("DamunPaperData");
    
	DecimalFormat  df = new DecimalFormat("0.00");
	DecimalFormat  df2 = new DecimalFormat("0.0");
%>문제번호|문제명|척도명|상사진단|동료진단|부하진단|관찰자평균|대상자본인
<%  box.put("p_action",  "go" );
    box.put("p_grcode",  ss_grcode );
    box.put("p_gyear",   ss_gyear ); 
    box.put("p_grseq",   ss_grseq );     
    box.put("p_subjcourse", ss_subjcourse );       
    box.put("p_subj",       ss_subjcourse );    
    box.put("p_subjseq",    ss_subjseq );
    box.put("p_damunpapernum", String.valueOf(ss_damunpapernum) ); 	

	DamunSubjResultBean bean = new DamunSubjResultBean();
	ArrayList list = bean.SelectObectResultList(box);
	ArrayList list2 = bean.SelectObectResultList2(box);

//  ArrayList    list = (ArrayList)request.getAttribute("DamunResultList");
//  ArrayList    list2 = (ArrayList)request.getAttribute("DamunResultList2");
    DamunQuestionExampleData data    = null;
    DamunExampleData         subdata = null;

	DamunQuestionExampleData data2    = null;
    DamunExampleData         subdata2 = null;
    for (int i=0; i < list.size(); i++) {
        data = (DamunQuestionExampleData)list.get(i);
	//	data2 = (DamunQuestionExampleData)list2.get(i);
		if (data.getSultype().equals(DamunSubjBean.OBJECT_QUESTION) || data.getSultype().equals(DamunSubjBean.MULTI_QUESTION)) { %>      <!--단일,복수-->  
<%=i+1%>|<%=data.getSultext()%>
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (DamunExampleData)data.get(j); 
		//		subdata2  = (DamunExampleData)data2.get(j); 

                if (subdata != null) { %>
<%=subdata.getSelnum()%>|<%=subdata.getSeltext()%>|>
<%if ((int)subdata.getReplyrate()==0) {%>&nbsp
<%} else { %><%=(int)subdata.getReplyrate()%>% <%}%>|
|<%=(int)subdata.getReplyrate()%>%|<%=subdata.getReplycnt()%>|<%=subdata.getSubjectcnt()%>|<%=subdata.getRelation1cnt()%>|<%=subdata.getRelation2cnt()%>
|<%=subdata.getRelation3cnt()%>
<%              }
            }
%>
<%       } else if (data.getSultype().equals(DamunSubjBean.SUBJECT_QUESTION)) {  //서술형    
%><%=i+1%><%=data.getSultext()%> 
<%          for (int j=0; j < data.getSubjectAnswer().size(); j++) {
%>
<%=(String)data.getSubjectAnswer().get(j)%>
<%         }
%>
<%          for (int j=0; j < data.getR1Answer().size(); j++) {
%>
<%=(String)data.getR1Answer().get(j)%>
<%         }
%>
<%          for (int j=0; j < data.getR2Answer().size(); j++) {
%>
<%=(String)data.getR2Answer().get(j)%>
<%         }
%>
<%          for (int j=0; j < data.getR3Answer().size(); j++) {
%>
<%=(String)data.getR3Answer().get(j)%>
<%         }
%>
<%
       } else if (data.getSultype().equals(DamunSubjBean.COMPLEX_QUESTION)) { //복합형
          
%><%=i+1%><%=data.getSultext()%> 
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (DamunExampleData)data.get(j); 
                if (subdata != null) { %>
<%=subdata.getSelnum()%>|<%=subdata.getSeltext()%>|
<%                  if ((int)subdata.getReplyrate()==0) { %>
&nbsp;
<%                  } else { %>
<%=(int)subdata.getReplyrate()%>%|
<%                  } %>
<%=subdata.getReplyrate()%>%|<%=subdata.getReplycnt()%>|<%=subdata.getSubjectcnt()%>|<%=subdata.getRelation1cnt()%>|<%=subdata.getRelation2cnt()%>|<%=subdata.getRelation3cnt()%>
<%           } 
           }	
%>
<%          for (int j=0; j < data.getComplexAnswer().size(); j++) {
%>
<%if(j==0){%>기타<%}%>
|<%=(String)data.getComplexAnswer().get(j)%>
<%
                }
%>
<%          for (int j=0; j < data.getR1ComplexAnswer().size(); j++) {
%>
<%if(j==0){%>기타<%}%><%=(String)data.getR1ComplexAnswer().get(j)%>
<%
                }
%>
<%          for (int j=0; j < data.getR2ComplexAnswer().size(); j++) {
%>
<%if(j==0){%>기타<%}%>|<%=(String)data.getR2ComplexAnswer().get(j)%>|
<%
                }
%>
<%          for (int j=0; j < data.getR3ComplexAnswer().size(); j++) {
%>
<%if(j==0){%>기타<%}%>|<%=(String)data.getR3ComplexAnswer().get(j)%>
<%
                }
%>
<%
       } else if (data.getSultype().equals(DamunSubjBean.FSCALE_QUESTION)) { //5점척도
          
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
%><%=i+1%>|<%=data.getSultext()%>|<%=subdata.getScalename()%>|<%=df.format(v_point2)%>|<%=df.format(v_point3)%>|<%=df.format(v_point4)%>|<%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%>|<%=df.format(v_point1)%>
<%
       } else if (data.getSultype().equals(DamunSubjBean.SSCALE_QUESTION)) { // 7점척도
          
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
%><%=i+1%>|<%=data.getSultext()%>|<%=subdata.getScalename()%>|<%=df.format(v_point2)%>|<%=df.format(v_point3)%>|<%=df.format(v_point4)%>|<%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%>|<%=df.format(v_point1)%>
<%
       } else if (data.getSultype().equals(DamunSubjBean.GSCALE_QUESTION)) { // GAP비교               
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

 
%><%=i+1%>|<%=data.getSultext()%>|<%=subdata.getScalename()%>|<%=df.format(v_point2)%>|<%=df.format(v_point3)%>|<%=df.format(v_point4)%>|<%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%>|<%=df.format(v_point1)%>
|<%=before%>|<%=df.format(v_point2)%>|<%=df.format(v_point3)%>|<%=df.format(v_point4)%>|<%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%>|<%=df.format(v_point1)%>
|<%=before%>|<%=df.format(v_point2)%>|<%=df.format(v_point3)%>|<%=df.format(v_point4)%>|<%=df.format(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0)%>|<%=df.format(v_point1)%>
GAP비교|<%=df.format(v_apoint2-v_point2)%>|<%=df.format(v_apoint3-v_point3)%>|<%=df.format(v_apoint4-v_point4)%>|<%=df.format((v_replyobcount > 0 ? ((ad2+ad3+ad4)/v_replyobcount) : 0)-(v_replyobcount > 0 ? ((d2+d3+d4)/v_replyobcount) : 0))%>|<%=df.format(v_apoint1-v_point1)%>
<%     
 	  
    } 
  }  
%>
