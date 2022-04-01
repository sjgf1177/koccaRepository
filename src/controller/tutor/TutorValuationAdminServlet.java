//*********************************************************
//  1. 제      목: 튜터 평가 관리
//  2. 프로그램명: TutorValuationAdminServlet.java
//  3. 개      요: 튜터 평가 관리 관리자 servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//**********************************************************
package controller.tutor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
import com.credu.tutor.TutorAdminBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.tutor.TutorValuationAdminServlet")
public class TutorValuationAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {    
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
    @SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
//        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
//        int fileupstatus = 0;

  
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
			if (!AdminUtil.getInstance().checkRWRight("TutorValuationAdminServlet", v_process, out, box)) {
				return; 
			}

			///////////////////////////////////////////////////////////////////
			if(v_process.equals("listPage")){                  // 튜터평가관리 리스트
                    this.performListPage(request, response, box, out);
            }else if(v_process.equals("detailView")){                  // 튜터활동 상세조회
                    this.performDetailPage(request, response, box, out);
            }else if(v_process.equals("calcTutorGrade")){                  // 튜터점수저장
                    this.performCalcTutorGrade(request, response, box, out);
            }else if(v_process.equals("listPageExcel")){                  // 엑셀출력
                    this.performListPageExcel(request, response, box, out);
            }else if(v_process.equals("SendFreeMail")){                  // send free mail
                    this.performSendFreeMail(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    튜터평가관리 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            
            TutorAdminBean bean = new TutorAdminBean();
            ArrayList lists = bean.selectTutorGradeList(box);
            request.setAttribute("gradelist", lists);                       

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorValuation_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorList()\r\n" + ex.getMessage());
        }
    }
    /**
    튜터평가관리 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performDetailPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            TutorAdminBean bean = new TutorAdminBean();
            
            DataBox dbox = bean.selectTutorActcnt(box);  //활동 카운트
            request.setAttribute("actcnts", dbox); 
            
            ArrayList list1 = bean.selectTutorActList(box);  //활동 리스트
            request.setAttribute("actlist", list1);
            
            ArrayList list2 = bean.selectTutorLoginList(box);  //로그인 리스트
            request.setAttribute("loginlist", list2);
// 화면변경
//            MailAdminBean bean2 = new MailAdminBean(); // 메일발송리스트
//            ArrayList list3 = bean2.selectUserSendList(box);
             ArrayList list3 = new ArrayList();
            request.setAttribute("maillist", list3);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorActDetail_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDetailPage()\r\n" + ex.getMessage());
        }
    }
    /**
    TUTOR 점수 저장
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performCalcTutorGrade(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);
            TutorAdminBean bean = new TutorAdminBean();

            int isOk = bean.calcTutorGrade(box);
            String v_msg = "";
            String v_url = "/servlet/controller.tutor.TutorValuationAdminServlet";
            box.put("p_process","listPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
            	v_msg = "insert.ok";
            	alert.alertOkMessage(out, v_msg, v_url , box, true, true);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorInsert()\r\n" + ex.getMessage());
        }
    }
    /**
    튜터평가관리 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performListPageExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            
            TutorAdminBean bean = new TutorAdminBean();
            ArrayList lists = bean.selectTutorGradeList(box);
            request.setAttribute("gradelist", lists); 
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorValuation_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorList()\r\n" + ex.getMessage());
        }
    }
    /**
    SEND FREE MAIL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSendFreeMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/freeMailForm.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFreeMail()\r\n" + ex.getMessage());
        }
    }

   

}