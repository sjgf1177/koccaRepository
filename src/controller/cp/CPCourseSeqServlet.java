//**********************************************************
//  1. 제      목: 과정차수관리 서블릿
//  2. 프로그램명: CPCourseSeqServlet.java
//  3. 개      요: 과정차수관리 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004. 12. 22
//  7. 수      정:
//**********************************************************

package controller.cp;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CPCourseSeqBean;
import com.credu.cp.CPSubjseqData;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
*과정차수관리
*<p>제목:CPCourseSeqServlet.java</p>
*<p>설명:과정차수관리 서블릿</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 이창훈
*@version 1.0
*/
@WebServlet("/servlet/controller.cp.CPCourseSeqServlet")
public class CPCourseSeqServlet extends javax.servlet.http.HttpServlet {
    
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
        RequestBox box = null;
        String v_process = "";
        
        try {

            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

			if(v_process.equals("")){
				box.put("v_process","listPage");
				v_process = "listPage";
			}
			
			if (!AdminUtil.getInstance().checkRWRight("CPCourseSeqServlet", v_process, out, box)) {
				return; 
			}
			
			System.out.println("v_process : " + v_process);
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
                       
            if(ErrorManager.isErrorMessageView()) {     
                box.put("errorout", out);
            }

            if(v_process.equals("insertPage")) {             //등록폼 페이지로
                this.performInsertPage(request, response, box, out);
            }
            else if(v_process.equals("insert")) {             //db등록처리
                this.performInsert(request, response, box, out);
            }else if(v_process.equals("listPage")) {   //과정목록조회
                this.performList(request, response, box, out);
            }else if(v_process.equals("grcomplist")) {   //과정목록조회
            	//System.out.println("dsfjkldsjfkldskjfklsjdfkljsdklfjklsjdfklsjdfkljklsfjkldsjfklsjdfkl");
                this.performGrcompList(request, response, box, out);
            }
            

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

    /**
    과정차수 리스트 조회
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            if (box.getString("p_action").equals("go")) {
	            CPCourseSeqBean cpCourseSeq = new CPCourseSeqBean();            
	            ArrayList list = cpCourseSeq.selectCourseSeqList(box);            
	            request.setAttribute("selectCourseSeqList", list);
            }
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpcourseSeq_l.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "/servlet/controller.cp.CPCourseSeqServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }
    

    /**
    교육그룹별 회사리스트 조회
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performGrcompList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            //if (box.getString("p_action").equals("go")) {
	            CPCourseSeqBean cpCourseSeq = new CPCourseSeqBean();            
	            
	            ArrayList list = cpCourseSeq.selectGrcompList(box);            
	            request.setAttribute("selectGrcompList", list);
            //}
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpGrcomp_l.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "/servlet/controller.cp.CPCourseSeqServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }

    /**
    과정업체차수등록 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */    
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            //CPCourseSeqBean cpcoursebean = new CPCourseSeqBean();            
            //DataBox dbox = cpcoursebean.selectCourseCPseq(box);            
            //request.setAttribute("selectCourseCPseq", dbox);


			CPCourseSeqBean bean = new CPCourseSeqBean();
			CPSubjseqData data = bean.SelectSubjseqData(box);
			request.setAttribute("selectCourseCPseq", data);
			
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpcourseSeq_i.jsp");
            rd.forward(request, response);
         
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    과정업체차수 등록
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */    
	public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            CPCourseSeqBean cpcoursebean = new CPCourseSeqBean();
            
            int isOk = cpcoursebean.UpdateSubjectSeq(box);
            
            String v_msg = "";
            String v_url = "/servlet/controller.cp.CPCourseSeqServlet";
            box.put("p_process", "");
            
            AlertManager alert = new AlertManager();

            if(isOk > 0) {            	
                v_msg = "insert.ok";       
                alert.alertOkMessage(out, v_msg, v_url , box);   
            }
            else {                
                v_msg = "insert.fail";   
                alert.alertFailMessage(out, v_msg); 
            }                                    
            Log.info.println(this, box, v_msg + " on CPCourseSeqServlet");  
                    
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }
               
}
        	
        	