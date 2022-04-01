//**********************************************************
//1. 제      목: 학습시간제약조건 SERVLET
//2. 프로그램명: EdutimeControlServlet.java
//3. 개      요: 학습시간제약조건 SERVLET
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성: S.W.Kang 2005. 03. 02
//7. 수      정: 
//                 
//**********************************************************
package controller.system;

import java.io.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.credu.library.*;
import com.credu.system.*;
import com.credu.course.*;
import com.credu.common.*;

@WebServlet("/servlet/controller.system.EdutimeControlServlet")
public class EdutimeControlServlet extends HttpServlet implements Serializable {

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
			v_process = box.getStringDefault("p_process","ListPage");

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}

			///////////////////////////////////////////////////////////////////
			// 권한 check 루틴 VER 0.2 - 2003.08.10
			if (!AdminUtil.getInstance().checkRWRight("EdutimeControlServlet", v_process, out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////
			if (v_process.equals("ListPage") || v_process.equals("")) { 		//학습시간제약 설정 현황
				this.performListPage(request, response, box, out);
			} else if (v_process.equals("saveInfo") || v_process.equals("")) {  //학습시간제약 설정 저장
				this.performSaveInfo(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
    예산정보 설정 및 수강신청 현황 조회
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/system/za_EduTimeControl_L.jsp";

            EdutimeControlBean bean = new EdutimeControlBean();
    //        DataBox dbox = bean.SelectEdutimeInfo(box);
    //        request.setAttribute("EdutimeInfo", dbox);

   //         ArrayList list = bean.SelectStudentList(box);
    //        request.setAttribute("StudentList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_EduTimeControl_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

	/**
    학습시간제약 저장
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performSaveInfo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.system.EdutimeControlServlet";

            EdutimeControlBean bean = new EdutimeControlBean();

    /*        int isOk = bean.updateEduTimeInfo(box);

            String v_msg = "";
            box.put("p_process", "ListPage");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "save.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                v_msg = "save.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on performSaveInfo");*/
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSaveInfo()\r\n" + ex.getMessage());
        }
    }
}
