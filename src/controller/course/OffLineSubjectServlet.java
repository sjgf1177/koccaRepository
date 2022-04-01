//**********************************************************
//1. 제      목: 과정코드 SERVLET
//2. 프로그램명: SubjectServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: anonymous 2003. 07. 15

//7. 수      정: 
//                 
//**********************************************************
package controller.course;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.OffLineSubjectBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

public class OffLineSubjectServlet extends HttpServlet implements Serializable {

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
        RequestBox  box = null;
        String v_process = "";
        
        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);            
            v_process = box.getStringDefault("p_process","listPage");

            System.out.println("v_gadmin : " + box.getSession("gadmin"));
            System.out.println("v_userid : " + box.getSession("userid"));
            System.out.println("v_name : " + box.getSession("name"));

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            ///////////////////////////////////////////////////////////////////
            // 권한 check 루틴 VER 0.2 - 2003.08.10
            //if (!AdminUtil.getInstance().checkRWRight("OffLineSubjectServlet", v_process, out, box)) {
            //    return; 
            //}
            ///////////////////////////////////////////////////////////////////
            if (v_process.equals("listPage")) {                             //in case of 과정 조회 화면
                this.performListPage(request, response, box, out);
            }
			else if (v_process.equals("insertPage"))  					// 등록 페이지로 이동
            {
				this.performInsertPage(request, response, box, out);
            }
            else if (v_process.equals("insert"))  						// 등록할때
            {
				this.performInsert(request, response, box, out);
            }
			else if (v_process.equals("updatePage"))  					// 수정 페이지로 이동
            {
				this.performUpdatePage(request, response, box, out);
            }
            else if (v_process.equals("update"))  						// 수정 할때
            {
				this.performUpdate(request, response, box, out);
            }
			else if (v_process.equals("ifrme"))  						// iframe
            {
				this.performIframe(request, response, box, out);
            }
            
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    오프라인 강좌 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/course/za_OffLineSubject_L.jsp";
                        
            OffLineSubjectBean bean = new OffLineSubjectBean();
            ArrayList list1 = bean.SelectSubjectList(box);
            request.setAttribute("OffLineSubjectList", list1);
                        
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }
	
	 /**
    등록페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_OffLineSubject_I.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
	
	/**
    과정코드 등록 - 사이버
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {   
        try{                
            String v_url  = "/servlet/controller.course.OffLineSubjectServlet";
           
			OffLineSubjectBean bean = new OffLineSubjectBean();
            int isOk = bean.InsertSubject(box);
            
            String v_msg = "";
            box.put("p_process", "listPage");
            
            AlertManager alert = new AlertManager();                        
            if(isOk > 0) {              
                //v_msg = "insert.ok";    
                v_msg = "오프라인 과정이 개설되었습니다.";  
                //box.put("s_action","go"); 
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {                
                //v_msg = "insert.fail";   
                v_msg = "오프라인 과정개설에 실패하였습니다.";   
                alert.alertFailMessage(out, v_msg);   
            }                                          
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }            
    }
	
	/**
	수정페이지로 이동할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

			OffLineSubjectBean bean = new OffLineSubjectBean();

	        DataBox dbox = bean.selectBoard(box);

	        request.setAttribute("selectHomePageBoard", dbox);

	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_OffLineSubject_U.jsp");
	        rd.forward(request, response);

	        Log.info.println(this, box, "Dispatch to /learn/admin/course/za_OffLineSubject_U.jsp");

	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
	    }
	}

	 /**
	수정하여 저장할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	    throws Exception {
	     try {
			 OffLineSubjectBean bean = new OffLineSubjectBean();

	        int isOk = bean.updateBoard(box);

	        String v_msg = "";
	        String v_url  = "/servlet/controller.course.OffLineSubjectServlet";
	        box.put("p_process", "");
	        //      수정 후 해당 리스트 페이지로 돌아가기 위해

	        AlertManager alert = new AlertManager();
//	System.out.println("update"+isOk);
	        if(isOk > 0) {
	            v_msg = "update.ok";
	            alert.alertOkMessage(out, v_msg, v_url , box);
	        }
	        else {
	            v_msg = "update.fail";
	            alert.alertFailMessage(out, v_msg);
	        }

	        Log.info.println(this, box, v_msg + " on OffLineSubjectServlet");
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performUpdate()\r\n" + ex.getMessage());
	    }
	}
	
	/**
	 * 과정 분류
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performIframe(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_IfrmSubj.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSearchSubjClass()\r\n" + ex.getMessage());
        }
    }
	
	
}
