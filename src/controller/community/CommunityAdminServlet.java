//*********************************************************
//  1. 제      목: COMMUNITY ADMIN SERVLET
//  2. 프로그램명: CommunityAdminServlet.java
//  3. 개      요: 커뮤니티 관리자 servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.02.12
//  7. 수      정:
//**********************************************************
package controller.community;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.credu.community.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;

public class CommunityAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {    
    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet    
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }    
    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
			//System.out.println("v_process : " + v_process);
			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("CommunityAdminServlet", v_process, out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////     			
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			if(v_process.equals("permitList")){             //in case of community permit list
                    this.performPermitList(request, response, box, out);
            }
    	    else if(v_process.equals("permitSelect")){      //in case of community permit select
                    this.performPermitSelect(request, response, box, out);
            }
    	    else if(v_process.equals("permitHandling")){    //in case of community permit handling
                    this.performPermitHandling(request, response, box, out);
            }            
            else if(v_process.equals("superiorList")){      //in case of superior community list
                    this.performSuperiorList(request, response, box, out);
            }
            else if(v_process.equals("superiorHandling")){  //in case of superior community handling
                    this.performSuperiorHandling(request, response, box, out);
            }            
            else if(v_process.equals("ActionLearningList")){//in case of ActionLearning community list
                this.performActionLearningList(request, response, box, out);
            }
            else if(v_process.equals("ActionLearningInsertPage")){//in case of ActionLearning community insert page
                this.performActionLearningInsertPage(request, response, box, out);
            }
            else if(v_process.equals("ActionLearningInsert")){//in case of ActionLearning community insert
                this.performActionLearningInsert(request, response, box, out);
            }
            else if(v_process.equals("ActionLearningUpdatePage")){//in case of ActionLearning community update page
                this.performActionLearningUpdatePage(request, response, box, out);
            }
            else if(v_process.equals("ActionLearningUpdate")){//in case of ActionLearning community update
                this.performActionLearningUpdate(request, response, box, out);
            }
            else if(v_process.equals("SearchMasterOpenPage")){//in case of ActionLearning Search Master
                this.performSearchMasterOpenPage(request, response, box, out);
            }            
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    COMMUNITY PERMIT LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performPermitList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            CommunityAdminBean bean = new CommunityAdminBean();
            ArrayList list = bean.selectPermitList(box);
            
            request.setAttribute("permitList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_CommunityPermit_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("permitList()\r\n" + ex.getMessage());
        }
    }

    /**
    COMMUNITY PERMIT SELECT
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performPermitSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CommunityAdminBean bean = new CommunityAdminBean();
            //CommunityData data = bean.selectPermit(box);
            DataBox dbox = bean.selectPermit(box);
            request.setAttribute("permitSelect", dbox);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_CommunityPermit_R.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("permitSelect()\r\n" + ex.getMessage());
        }
    }

    /**
    COMMUNITY PERMIT HANDLING
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performPermitHandling(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CommunityAdminBean bean = new CommunityAdminBean();
            int isOk = bean.handlingPermit(box);
            
            String v_msg = "";                     
            String v_url = "/servlet/controller.community.CommunityAdminServlet";
            box.put("p_process","permitList");
            
            AlertManager alert = new AlertManager();
                        
            if(isOk > 0) {            	
            	v_msg = "update.ok";       
            	alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {                
                v_msg = "update.fail";   
                alert.alertFailMessage(out, v_msg); 
            }            
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("permitHandling()\r\n" + ex.getMessage());
        }
    }

    /**
    COMMUNITY SUPERIOR LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSuperiorList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CommunityAdminBean bean = new CommunityAdminBean();
            ArrayList list = bean.selectSuperiorList(box);
            
            request.setAttribute("superiorList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_SuperiorCommunity_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("superiorList()\r\n" + ex.getMessage());
        }
    }
    
    /**
    COMMUNITY SUPERIOR HANDLING
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSuperiorHandling(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CommunityAdminBean bean = new CommunityAdminBean();
            int isOk = bean.handlingSuperior(box);
            
            String v_msg = "";                     
            String v_url = "/servlet/controller.community.CommunityAdminServlet";
            box.put("p_process","superiorList");
            
            AlertManager alert = new AlertManager();
                        
            if(isOk > 0) {            	
            	v_msg = "update.ok";       
            	alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {                
                v_msg = "update.fail";   
                alert.alertFailMessage(out, v_msg); 
            }            
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("superiorHandling()\r\n" + ex.getMessage());
        }
    }    

    /**
    COMMUNITY ACTIONLEARNING LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performActionLearningList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CommunityAdminBean bean = new CommunityAdminBean();
            ArrayList list = bean.selectActionLearningList(box);

            request.setAttribute("actionLearningList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_ActionLearning_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("actionLearningList()\r\n" + ex.getMessage());
        }
    }

    /**
    COMMUNITY ACTIONLEARNING INSERT PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performActionLearningInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_ALCommunityOpen_I.jsp"); 
            rd.forward(request, response);                                              
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("actionLearningInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    COMMUNITY ACTIONLEARNING INSERT
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performActionLearningInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CommunityAdminBean bean = new CommunityAdminBean();

            int isOk = bean.insertActionLearning(box);  //TZ_COMMUNITY,TZ_COMMUNITY_MEMBER,TZ_SUBJ_ASSOCIATION insert
            String v_msg = "";                     
            String v_url = "/servlet/controller.community.CommunityAdminServlet";
            box.put("p_process","ActionLearningList");
            
            AlertManager alert = new AlertManager();
                        
            if(isOk > 0) {
            	v_msg = "insert.ok";       
            	alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {                
                v_msg = "insert.fail";   
                alert.alertFailMessage(out, v_msg); 
            } 
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("actionLearningInsert()\r\n" + ex.getMessage());
        }
    }        

    /**
    COMMUNITY ACTIONLEARNING UPDATE PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performActionLearningUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다            
            CommunityAdminBean bean = new CommunityAdminBean();
			DataBox dbox = bean.selectActionLearning(box);
            request.setAttribute("actionLearningUpdatePage", dbox);            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_ALCommunityOpen_U.jsp"); 
            rd.forward(request, response);                                              
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("actionLearningUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
    COMMUNITY ACTIONLEARNING UPDATE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performActionLearningUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CommunityAdminBean bean = new CommunityAdminBean();

            int isOk = bean.updateActionLearning(box);  //TZ_COMMUNITY,TZ_COMMUNITY_MEMBER,TZ_SUBJ_ASSOCIATION update
            
            String v_msg = "";                     
            String v_url = "/servlet/controller.community.CommunityAdminServlet";
            box.put("p_process","ActionLearningList");
            
            AlertManager alert = new AlertManager();
                        
            if(isOk > 0) {            	
            	v_msg = "update.ok";       
            	alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {                
                v_msg = "update.fail";   
                alert.alertFailMessage(out, v_msg); 
            }     
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("actionLearningUpdate()\r\n" + ex.getMessage());
        }
    }            

    /**
    MASTER SEARCH PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSearchMasterOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다       
            CommunityAdminBean bean = new CommunityAdminBean();
            ArrayList list = bean.selectSearchMaster(box);

            request.setAttribute("searchMaster", list);                 
               
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_ALMasterSearch.jsp"); 
            rd.forward(request, response);  
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("searchMasterOpenPage()\r\n" + ex.getMessage());
        }
    }    
}