<%
//**********************************************************
//  1. 제      목: 설문
//  2. 프로그램명: 
//  3. 개      요: 
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
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
            
    ReportingBean bean = new ReportingBean();
    ArrayList    list1 = bean.SelectSulmumList(box);    
%>
subjnm,subjseq,done1,done2,done3,done4,done5,dtwo1,dtwo2,dtwo3,dtwo4,dtwo5,dthree1,dthree2,dthree3,dthree4,dthree5,dfour1,dfour2,dfour3,dfour4,dfour5,dfive1,dfive2,dfive3,dfive4,dfive5,dsix1,dsix2,dsix3,dsix4,dsix5
<%  
    // 1.과정만족도
    int dcode1_1 = 0, dcode1_2 = 0, dcode1_3 = 0, dcode1_4 = 0, dcode1_5 = 0;      
    // 2.내용이해도
    int dcode2_1 = 0, dcode2_2 = 0, dcode2_3 = 0, dcode2_4 = 0, dcode2_5 = 0;
    // 3.과정난이도
    int dcode3_1 = 0, dcode3_2 = 0, dcode3_3 = 0, dcode3_4 = 0, dcode3_5 = 0;  
    // 4.업무활용도
    int dcode4_1 = 0, dcode4_2 = 0, dcode4_3 = 0, dcode4_4 = 0, dcode4_5 = 0;      
    // 5.질문대응
    int dcode5_1 = 0, dcode5_2 = 0, dcode5_3 = 0, dcode5_4 = 0, dcode5_5 = 0;
    // 6.장애대응
    int dcode6_1 = 0, dcode6_2 = 0, dcode6_3 = 0, dcode6_4 = 0, dcode6_5 = 0;  
    //ArrayList list1 = (ArrayList)request.getAttribute("SelectSulmumList"); 	
    DataBox   data  = null;   
    int roofcnt = 0;
    for(int i=0; i<list1.size(); i++){    
        data = (DataBox)list1.get(i);
        String result = "";
        int usercnt      = data.getInt("d_usercnt");
        String s_subj    = data.getString("d_subj");
        String s_subjseq = data.getString("d_subjseq");
        String temp_subj    = s_subj;
        String temp_subjseq = s_subjseq;
        if((temp_subj.equals(s_subj))&&(temp_subjseq.equals(s_subjseq))) {      
            for(int j=1; j<4; j++) {
                if(data.getString("d_dcode"+String.valueOf(j)).equals("1")) {
                    if(j==1)     dcode1_1++;
                    else if(j==2)dcode2_1++;
                    else if(j==3)dcode3_1++;
                    else if(j==4)dcode4_1++;
                    else if(j==5)dcode5_1++;                    
                    else if(j==6)dcode6_1++;                    
                }
                if(data.getString("d_dcode"+String.valueOf(j)).equals("2")) {
                    if(j==1)     dcode1_2++;
                    else if(j==2)dcode2_2++;
                    else if(j==3)dcode3_2++;    
                    else if(j==4)dcode4_2++;
                    else if(j==5)dcode5_2++;                    
                    else if(j==6)dcode6_2++;     
                }
                if(data.getString("d_dcode"+String.valueOf(j)).equals("3")) {
                    if(j==1)     dcode1_3++;
                    else if(j==2)dcode2_3++;
                    else if(j==3)dcode3_3++;     
                    else if(j==4)dcode4_3++;
                    else if(j==5)dcode5_3++;                    
                    else if(j==6)dcode6_3++;                                     
                }
                if(data.getString("d_dcode"+String.valueOf(j)).equals("4")) {
                    if(j==1)     dcode1_4++;
                    else if(j==2)dcode2_4++;
                    else if(j==3)dcode3_4++;     
                    else if(j==4)dcode4_4++;
                    else if(j==5)dcode5_4++;                    
                    else if(j==6)dcode6_4++;                                     
                }
                if(data.getString("d_dcode"+String.valueOf(j)).equals("5")) {
                    if(j==1)     dcode1_5++;
                    else if(j==2)dcode2_5++;
                    else if(j==3)dcode3_5++;              
                    else if(j==4)dcode4_5++;
                    else if(j==5)dcode5_5++;                    
                    else if(j==6)dcode6_5++;                            
                }
            }
            result = data.getString("d_subjnm")+","+Integer.parseInt(data.getString("d_subjseq"));
            result+= ","+ dcode1_1+","+dcode1_2+","+dcode1_3+","+dcode1_4+","+dcode1_5;
            result+= ","+ dcode2_1+","+dcode2_2+","+dcode2_3+","+dcode2_4+","+dcode2_5;
            result+= ","+ dcode3_1+","+dcode3_2+","+dcode3_3+","+dcode3_4+","+dcode3_5;
            result+= ","+ dcode4_1+","+dcode4_2+","+dcode4_3+","+dcode4_4+","+dcode4_5;
            result+= ","+ dcode5_1+","+dcode5_2+","+dcode5_3+","+dcode5_4+","+dcode5_5;
            result+= ","+ dcode6_1+","+dcode6_2+","+dcode6_3+","+dcode6_4+","+dcode6_5;            
            roofcnt++;
        }
        if(roofcnt==usercnt){
%><%=result%>
<%            
             roofcnt=0; 
             dcode1_1=0; dcode1_2=0; dcode1_3=0; dcode1_4=0; dcode1_5=0;
             dcode2_1=0; dcode2_2=0; dcode2_3=0; dcode2_4=0; dcode2_5=0;
             dcode3_1=0; dcode3_2=0; dcode3_3=0; dcode3_4=0; dcode3_5=0;   
             dcode4_1=0; dcode4_2=0; dcode4_3=0; dcode4_4=0; dcode4_5=0;                       
             dcode5_1=0; dcode5_2=0; dcode5_3=0; dcode5_4=0; dcode5_5=0;                       
             dcode6_1=0; dcode6_2=0; dcode6_3=0; dcode6_4=0; dcode6_5=0;                                              
        }
    } // for end
%>
